package com.springboot.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by liuyongg on 7/8/2017.
 */
@Data
@Entity
public class TpFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String picturePath;
    private String filePath;
    private String pictureName;
    private String fileName;
    private int age;
}
