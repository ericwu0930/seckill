package io.github.ericwu0930.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author erichwu
 * @date 2020/5/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
    private Integer id;
    private String name;
    private Byte gender;
    private Integer age;
}
