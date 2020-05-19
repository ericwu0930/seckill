package io.github.ericwu0930.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * @author erichwu
 * @date 2020/5/19
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemVO {
    private Integer id;

    private String title;

    private BigDecimal price;

    private Integer stock;

    private String description;

    private Integer sals;

    private String imgUrl;
}
