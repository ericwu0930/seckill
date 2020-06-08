package io.github.ericwu0930.controller;

import io.github.ericwu0930.controller.vo.ItemVO;
import io.github.ericwu0930.error.BusinessException;
import io.github.ericwu0930.response.CommonReturnType;
import io.github.ericwu0930.service.ItemService;
import io.github.ericwu0930.service.model.ItemModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author erichwu
 * @date 2020/5/19
 */
@Controller
@RequestMapping("/item")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class ItemController extends BaseController{

    @Autowired
    private ItemService itemService;


    @ResponseBody
    @RequestMapping(value="/create",method={RequestMethod.POST},consumes={CONTENT_TYPE_FORMED})
    // 创建商品的controller
    public CommonReturnType createItem(@RequestParam(name="title")String title,
                                       @RequestParam(name="price") BigDecimal price,
                                       @RequestParam(name="stock") Integer stock,
                                       @RequestParam(name="imgUrl")String imgUrl,
                                       @RequestParam(name="description") String description) throws BusinessException {
         // 封装service请求用来创建商品
        ItemModel itemModel=new ItemModel();
        itemModel.setTitle(title);
        itemModel.setDescription(description);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgUrl);

        itemService.createItem(itemModel);
        ItemVO itemVO = convertVOFromModel(itemModel);
        return CommonReturnType.create(itemVO);
    }

    @ResponseBody
    @RequestMapping(value="/list",method = {RequestMethod.GET})
    public CommonReturnType listItem(){
        List<ItemModel> itemModels = itemService.listItem();
        List<ItemVO> collect = itemModels.stream().map(t -> {
            ItemVO itemVO = convertVOFromModel(t);
            return itemVO;
        }).collect(Collectors.toList());
        return CommonReturnType.create(collect);
    }


    @ResponseBody
    @RequestMapping(value="/get",method={RequestMethod.GET})
    public CommonReturnType getItem(@RequestParam(name="id")Integer id){
        ItemModel itemModel=itemService.getItemById(id);

        ItemVO itemVO = convertVOFromModel(itemModel);

        return CommonReturnType.create(itemVO);

    }


    private ItemVO convertVOFromModel(ItemModel itemModel){
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel,itemVO);
        if(itemModel.getPromoModel()!=null){
            itemVO.setPromoStatus(itemModel.getPromoModel().getStatus());
            itemVO.setPromoId(itemModel.getPromoModel().getId());
            itemVO.setStartTime(itemModel.getPromoModel().getStartTime());
            itemVO.setEndTime(itemModel.getPromoModel().getEndTime());
        }
        return itemVO;
    }
}
