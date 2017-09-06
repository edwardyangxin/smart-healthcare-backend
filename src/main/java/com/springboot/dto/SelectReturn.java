package com.springboot.dto;

import lombok.Data;

import java.util.Date;

/**
 * Created by liuyongg on 5/9/2017.
 */

@Data
public class SelectReturn {

    private Integer id;

    private String name;

    private String salaryRange;

    private String language;

    private Date registerTime;

    private String projectTitle;

}
