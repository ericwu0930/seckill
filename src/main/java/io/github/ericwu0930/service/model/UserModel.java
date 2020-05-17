package io.github.ericwu0930.service.model;

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
public class UserModel {
    private Integer id;
    private String name;
    private Byte gender;
    private Integer age;
    private String telephone;
    private String registerMode;
    private String thirdPartyId;

    private String encrptPassword;
}
