package io.github.ericwu0930.service;

import io.github.ericwu0930.error.BusinessException;
import io.github.ericwu0930.service.model.OrderModel;

/**
 * @author erichwu
 * @date 2020/5/24
 */
public interface OrderService {
    OrderModel createOrder(Integer userId,Integer itemId,Integer amount) throws BusinessException;
    String generateOrderNO();
}
