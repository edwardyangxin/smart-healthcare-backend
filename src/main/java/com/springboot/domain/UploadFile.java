package com.springboot.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@ConfigurationProperties(prefix = "smart.upload")
public class UploadFile{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String filePath;

    private String fileName;
}



