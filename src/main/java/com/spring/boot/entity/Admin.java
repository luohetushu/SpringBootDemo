package com.spring.boot.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ApiModel(description = "管理员实体类")
public class Admin {

    @ApiModelProperty(value = "管理员id", position = 1)
    private String aid;

    @NotNull
    @Size(min = 2, max = 20, message = "管理员姓名长度在2～20之间")
    @ApiModelProperty(value = "管理员姓名", position = 2)
    private String name;

    @NotNull
    @Size(min = 3, max = 12, message = "管理员密码长度在3～12之间")
    @ApiModelProperty(value = "管理员密码", position = 3)
    private String password;

}
