package io.github.ericwu0930.service.impl;

import com.fasterxml.jackson.databind.util.BeanUtil;
import io.github.ericwu0930.dao.ItemMapper;
import io.github.ericwu0930.dao.ItemStockMapper;
import io.github.ericwu0930.error.BusinessException;
import io.github.ericwu0930.error.EmBusinessError;
import io.github.ericwu0930.pojo.Item;
import io.github.ericwu0930.pojo.ItemStock;
import io.github.ericwu0930.service.ItemService;
import io.github.ericwu0930.service.PromoService;
import io.github.ericwu0930.service.model.ItemModel;
import io.github.ericwu0930.service.model.PromoModel;
import io.github.ericwu0930.validator.ValidationResult;
import io.github.ericwu0930.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author erichwu
 * @date 2020/5/17
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemStockMapper itemStockMapper;

    @Autowired
    private PromoService promoService;


    private Item convertItemFromItemModel(ItemModel itemModel){
        if(itemModel==null)
            return null;
        Item item = new Item();
        BeanUtils.copyProperties(itemModel,item);
        // price 格式不一样 double-> BigDecimal
        item.setPrice(itemModel.getPrice().doubleValue());
        return item;
    }

    private ItemStock convertItemStockFromItemModel(ItemModel itemModel){
        if(itemModel==null)
            return null;
        ItemStock itemStock=new ItemStock();
        itemStock.setItemId(itemModel.getId());
        itemStock.setStock(itemModel.getStock());
        return itemStock;
    }

    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {
        // 校验入参
        ValidationResult validate = validator.validate(itemModel);

        if(validate.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,validate.getErrMsg());
        }
        // 转化ItermModel-> dataObjcet
        Item item= convertItemFromItemModel(itemModel);

        // 写入数据库
        itemMapper.insertSelective(item);
        itemModel.setId(item.getId());

        ItemStock itemStock=convertItemStockFromItemModel(itemModel);

        itemStockMapper.insertSelective(itemStock);
        // 返回创建完成的对象
        return this.getItemById(itemModel.getId());
    }

    @Override
    public List<ItemModel> listItem() {
        List<Item> items = itemMapper.listItem();
        List<ItemModel> collect = items.stream().map(t -> {
            ItemStock itemStock = itemStockMapper.selectByItemId(t.getId());
            return convertModelFromDataObject(t, itemStock);
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        Item item=itemMapper.selectByPrimaryKey(id);
        if(item==null)
            return null;
         // 操作获得库存数量
        ItemStock itemStock = itemStockMapper.selectByItemId(item.getId());

        ItemModel itemModel=convertModelFromDataObject(item,itemStock);

        PromoModel promoModel = promoService.getPromoByItemId(item.getId());
        if(promoModel!=null&&promoModel.getStatus()!=3)
            itemModel.setPromoModel(promoModel);
        return itemModel;

    }

    @Override
    @Transactional
    public boolean decreaseStock(Integer itemId, Integer amount) {
        int i = itemStockMapper.decreaseStock(itemId, amount);
        return i!=0;
    }

    private  ItemModel convertModelFromDataObject(Item item,ItemStock itemStock){
        ItemModel itemModel=new ItemModel();
        BeanUtils.copyProperties(item,itemModel);
        itemModel.setPrice(new BigDecimal(item.getPrice()));
        itemModel.setStock(itemStock.getStock());
        return itemModel;
    }
}
