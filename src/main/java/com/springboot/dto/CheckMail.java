package com.springboot.dto;

import lombok.Data;

@Data
public class CheckMail {
    private String name;
    private String activeCode;
    private Boolean status;
}
