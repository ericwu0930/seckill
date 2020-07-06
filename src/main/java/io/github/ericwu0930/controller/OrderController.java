package io.github.ericwu0930.controller;

import io.github.ericwu0930.error.BusinessException;
import io.github.ericwu0930.response.CommonReturnType;
import io.github.ericwu0930.service.OrderService;
import io.github.ericwu0930.service.model.OrderModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value="/createorder",method={RequestMethod.POST},consumes={CONTENT_TYPE_FORMED})
    public CommonReturnType createOrder(@RequestParam(name="itemId")Integer itemId,
                                        @RequestParam(name="amount")Integer amount,
                                        @RequestParam(name="promoId",required = false)Integer promoId ) throws BusinessException {
        String token = httpServletRequest.getParameterMap().get("token")[0];
        if(StringUtils.isEmpty(token))
            throw new BusinessException()
        OrderModel order = orderService.createOrder(null, itemId, promoId, amount);
        return CommonReturnType.create(order);
    }
}
