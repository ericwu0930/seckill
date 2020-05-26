package io.github.ericwu0930.service.model;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author erichwu
 * @date 2020/5/26
 */
public class PromoModel {
    private Integer id;
    private String promoName;
    private DateTime startDate;
    private Integer itemId;
    private BigDecimal promoItemPrice;
}
