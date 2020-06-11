package io.github.ericwu0930.service;

import io.github.ericwu0930.error.BusinessException;
import io.github.ericwu0930.service.model.OrderModel;

/**
 * @author erichwu
 * @date 2020/5/24
 */
public interface OrderService {
    // 1. 通过前端url上传过来秒杀活动id，然后下单接口内校验对应id是否属于对应商品且活动是否已经开始（使用）
    // 2. 直接在下单接口内判断对应的商品是否存在秒杀活动，若存在进行中的则以秒杀价格下单
    OrderModel createOrder(Integer userId,Integer itemId,Integer promoId, Integer amount) throws BusinessException;
    String generateOrderNO();
}
