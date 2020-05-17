package io.github.ericwu0930.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NegativeOrZero;
import java.math.BigDecimal;

/**
 * @author erichwu
 * @date 2020/5/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemModel {
    private Integer id;

    private String title;

    private BigDecimal price;

    private Integer stock;

    private String description;

    private Integer sals;

    private String imgUrl;

}
