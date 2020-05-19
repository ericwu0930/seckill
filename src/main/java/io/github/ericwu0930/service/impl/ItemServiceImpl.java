package io.github.ericwu0930.service.impl;

import com.fasterxml.jackson.databind.util.BeanUtil;
import io.github.ericwu0930.dao.ItemMapper;
import io.github.ericwu0930.dao.ItemStockMapper;
import io.github.ericwu0930.error.BusinessException;
import io.github.ericwu0930.error.EmBusinessError;
import io.github.ericwu0930.pojo.Item;
import io.github.ericwu0930.pojo.ItemStock;
import io.github.ericwu0930.service.ItemService;
import io.github.ericwu0930.service.model.ItemModel;
import io.github.ericwu0930.validator.ValidationResult;
import io.github.ericwu0930.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

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
        return null;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        Item item=itemMapper.selectByPrimaryKey(id);
        if(item==null)
            return null;
         // 操作获得库存数量
        ItemStock itemStock = itemStockMapper.selectByItemId(id);

        ItemModel itemModel=convertModelFromDataObject(item,itemStock);

        return itemModel;

    }

    private  ItemModel convertModelFromDataObject(Item item,ItemStock itemStock){
        ItemModel itemModel=new ItemModel();
        BeanUtils.copyProperties(item,itemModel);
        itemModel.setPrice(new BigDecimal(item.getPrice()));
        itemModel.setStock(itemStock.getStock());
        return itemModel;
    }
}
