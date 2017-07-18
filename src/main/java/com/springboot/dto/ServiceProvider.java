package com.springboot.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by liuyongg on 18/7/2017.
 */
@Data
public class ServiceProvider {

    private String name;

    @NotEmpty(message = "所在城市不能为空！")
    @NotNull(message = "所在城市不能为空！")
    private String city;



}
