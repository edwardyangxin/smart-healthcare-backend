package com.springboot.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Administrator on 2017/7/13.
 */

@Data
@ConfigurationProperties(prefix = "kaptcha")
public class KaptchaProperties {
    private String border;
    private String borderColor;
    private String textProducerFontColor;
    private String textProducerFontSize;
    private String textProducerFontNames;
    private String textProducerCharLength;
    private String imageWidth;
    private String imageHeight;
    private String noiseColor;
    private String noiseImpl;
    private String obscurificatorImpl;
    private String sessionKey;
    private String sessionDate;
}