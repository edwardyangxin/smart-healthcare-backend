package com.springboot.configuration;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.springboot.domain.KaptchaProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Created by Administrator on 2017/7/13.
 */
@Configuration
@EnableConfigurationProperties(KaptchaProperties.class)
public class KaptchaAutoConfiguration {

    @Autowired
    private KaptchaProperties kaptchaProperties;

    @Bean(name = "kaptchaProducer")
    public DefaultKaptcha getKaptchaBean() {

        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty(Constants.KAPTCHA_BORDER, kaptchaProperties.getBorder());
        properties.setProperty(Constants.KAPTCHA_BORDER_COLOR, kaptchaProperties.getBorderColor());
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, kaptchaProperties.getTextProducerFontColor());
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, kaptchaProperties.getTextProducerFontSize());
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES, kaptchaProperties.getTextProducerFontNames());
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, kaptchaProperties.getTextProducerCharLength());
        properties.setProperty(Constants.KAPTCHA_IMAGE_WIDTH, kaptchaProperties.getImageWidth());
        properties.setProperty(Constants.KAPTCHA_IMAGE_HEIGHT, kaptchaProperties.getImageHeight());
        properties.setProperty(Constants.KAPTCHA_NOISE_COLOR, kaptchaProperties.getNoiseColor());
        //properties.setProperty(Constants.KAPTCHA_NOISE_IMPL, kaptchaProperties.getNoiseImpl());//去掉干扰线
        properties.setProperty(Constants.KAPTCHA_OBSCURIFICATOR_IMPL, kaptchaProperties.getObscurificatorImpl());//阴影渲染效果
        properties.setProperty(Constants.KAPTCHA_SESSION_KEY, kaptchaProperties.getSessionKey());
        properties.setProperty(Constants.KAPTCHA_SESSION_DATE, kaptchaProperties.getSessionDate());
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

}