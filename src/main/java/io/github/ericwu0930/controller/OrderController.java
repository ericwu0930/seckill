package io.github.ericwu0930.controller;

import io.github.ericwu0930.error.BusinessException;
import io.github.ericwu0930.response.CommonReturnType;
import io.github.ericwu0930.service.OrderService;
import io.github.ericwu0930.service.model.OrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author erichwu
 * @date 2020/5/24
 */
@Controller("order")
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class OrderController extends BaseController{

    @Autowired
    private OrderService orderService;

    public CommonReturnType createOrder(@RequestParam(name="itemId")Integer itemId,
                                        @RequestParam(name="amount")Integer amount,
                                        @RequestParam(name="promoId",required = false)Integer promoId ) throws BusinessException {
        OrderModel order = orderService.createOrder(null, itemId, promoId, amount);
        return CommonReturnType.create(order);
    }
}
