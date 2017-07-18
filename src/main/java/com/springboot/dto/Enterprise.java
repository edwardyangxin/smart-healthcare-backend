package com.springboot.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by liuyongg on 18/7/2017.
 */
@Data
public class Enterprise {

    private String name;

    @NumberFormat
    @NotEmpty(message = "电话号码不能为空！")
    @NotNull(message = "电话号码不能为空！")
    private String tel;

    @NotEmpty(message = "所在行业不能为空！")
    @NotNull(message = "所在行业不能为空！")
    private String industry;

    @NotEmpty(message = "所在城市不能为空！")
    @NotNull(message = "所在城市不能为空！")
    private String city;
}
