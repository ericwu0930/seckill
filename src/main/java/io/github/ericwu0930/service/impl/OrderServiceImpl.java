package io.github.ericwu0930.service.impl;

import io.github.ericwu0930.dao.OrderMapper;
import io.github.ericwu0930.dao.SequenceMapper;
import io.github.ericwu0930.error.BusinessException;
import io.github.ericwu0930.error.EmBusinessError;
import io.github.ericwu0930.pojo.Order;
import io.github.ericwu0930.pojo.Sequence;
import io.github.ericwu0930.service.ItemService;
import io.github.ericwu0930.service.OrderService;
import io.github.ericwu0930.service.UserService;
import io.github.ericwu0930.service.model.ItemModel;
import io.github.ericwu0930.service.model.OrderModel;
import io.github.ericwu0930.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

/**
 * @author erichwu
 * @date 2020/5/24
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SequenceMapper sequenceMapper;

    @Autowired
    private OrderService orderService;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BusinessException {
        // 1. 校验下单状态 用户是否合法，购买数量是否正确
        ItemModel itemById = itemService.getItemById(itemId);
        if(itemById==null)
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"商品信息不存在");
        UserModel userById = userService.getUserById(userId);
        if(userById==null)
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户信息不存在");
        if(amount<=0||amount>99)
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"订单数量不正确");
        if(promoId!=null){
            // (1) 校验对应活动是否存在这个适用商品
            if(promoId!=itemById.getPromoModel().getId()){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"活动信息异常");
            }else if(itemById.getPromoModel().getStatus()!=2){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"活动还未开始");
            }
        }
        // 2. 落单减库存
        if (!itemService.decreaseStock(itemId,amount)) {
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH,"");
        }
        // 3. 订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        if(promoId!=null){
            orderModel.setItemPrice(itemById.getPromoModel().getPromoItemPrice());
        }else{
            orderModel.setItemPrice(itemById.getPrice());
        }
        orderModel.setOrderPrice(orderModel.getItemPrice().multiply(new BigDecimal(amount)));
        orderModel.setAmount(amount);
        orderModel.setPromoId(promoId);
        // 生成交易订单号
        orderModel.setId(orderService.generateOrderNO());
        // 4. 返回前端
        Order order = convertFromOrderModel(orderModel);
        orderMapper.insertSelective(order);
        return orderModel;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String generateOrderNO(){
        // 订单号16位
        // 前8位时间信息，年月日
        LocalDateTime now=LocalDateTime.now();
        now.format(DateTimeFormatter.ISO_DATE).replace("-","");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(now);
        // 中间6位自增序列
        Sequence order_info = sequenceMapper.getSequenceByName("order_info");
        Integer currentValue = order_info.getCurrentValue();
        order_info.setCurrentValue(order_info.getStep()+order_info.getCurrentValue());
        sequenceMapper.updateByPrimaryKeySelective(order_info);
        String sequenceStr=String.valueOf(currentValue);
        for(int i=0;i<6-sequenceStr.length();++i){
            stringBuilder.append(0);
        }
        stringBuilder.append(sequenceStr);
        // 最后两位分库分表为
        stringBuilder.append("00");
        return stringBuilder.toString();
    }

    private Order convertFromOrderModel(OrderModel orderModel){
        if(orderModel==null)
            return null;
        Order order = new Order();
        BeanUtils.copyProperties(orderModel,order);
        order.setItemPrice(orderModel.getItemPrice().doubleValue());
        order.setOrderPrice(orderModel.getOrderPrice().doubleValue());
        return order;
    }
}
