package com.spring.boot.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data  // @Data: 自动为所有字段添加@ToString, @EqualsAndHashCode, @Getter方法，为非final字段添加@Setter,和@RequiredArgsConstructor
@NoArgsConstructor // 自动生成无参构造方法
@ApiModel(description = "用户实体类")
public class User {

    @ApiModelProperty(value = "用户id", required = true, position = 1)
    private long uid;

    @NotNull
    @Size(min = 2, max = 10, message = "用户名长度在2～10之间")
    @ApiModelProperty(value = "用户名", required = true, position = 2)
    private String name;

    @NotNull
    @Min(18)
    @Max(99)
    @ApiModelProperty(value = "用户年龄", position = 3)
    private int age;

    @NotNull
    @Email
    @ApiModelProperty(value = "用户邮箱", position = 4)
    private String email;

    public User(long uid, @NotNull @Size(min = 2, max = 5, message = "用户名长度在2～5之间") String name,
                @NotNull @Min(18) @Max(99) int age, @NotNull @Email String email) {
        this.uid = uid;
        this.name = name;
        this.age = age;
        this.email = email;
    }
}
