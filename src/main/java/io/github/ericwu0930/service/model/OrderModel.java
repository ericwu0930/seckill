package io.github.ericwu0930.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author erichwu
 * @date 2020/5/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderModel {
    private String id;

    // 购买时商品单价
    private BigDecimal orderPrice;

    private Integer userId;

    private Integer itemId;

    private Integer amount;

    private BigDecimal orderAmount;
}
