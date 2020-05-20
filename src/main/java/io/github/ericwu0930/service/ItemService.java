package io.github.ericwu0930.service;

import io.github.ericwu0930.error.BusinessException;
import io.github.ericwu0930.service.model.ItemModel;

import java.util.List;

/**
 * @author erichwu
 * @date 2020/5/17
 */
public interface ItemService {
    // 创建商品
    ItemModel createItem(ItemModel itemModel) throws BusinessException;

    // 商品列表浏览
    List<ItemModel> listItem();

    // 商品详情浏览
    ItemModel getItemById(Integer id);

}
