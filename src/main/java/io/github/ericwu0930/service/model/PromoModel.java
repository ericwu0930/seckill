package io.github.ericwu0930.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author erichwu
 * @date 2020/5/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromoModel {
    private Integer id;
    private String promoName;
    private DateTime startTime;
    private Integer itemId;
    private BigDecimal promoItemPrice;
    private DateTime endTime;
    // 1:还未开始 2:进行汇总 3:已经结束
    private Integer status;
}
