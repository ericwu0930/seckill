package io.github.ericwu0930.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NegativeOrZero;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotBlank(message = "商品名称不能为空")
    private String title;

    @NotNull(message = "商品价格不能为空")
    @Min(value=0,message = "商品价格必须大于0")
    private BigDecimal price;

    @NotNull(message = "库存不能不填写")
    private Integer stock;

    @NotBlank(message = "商品信息不能为空")
    private String description;

    private Integer sals;

    @NotBlank(message = "图片信息不能为空")
    private String imgUrl;

}
