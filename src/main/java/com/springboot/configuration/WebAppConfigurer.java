package com.springboot.configuration;

import com.springboot.interceptor.CommonInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        // registry.addInterceptor(new CommonInterceptor()).excludePathPatterns("/translate/login").excludePathPatterns("/translate/register").excludePathPatterns("/translate/getKaptchaImage");
        registry.addInterceptor(new CommonInterceptor())
                .addPathPatterns("/translate/personal/delPersonInfo/{id}")
                .addPathPatterns("/translate/personal/modifyPass")
                .addPathPatterns("/translate/personal/personPro")
                .addPathPatterns("/translate/personal/selectInfoById")
                .addPathPatterns("/translate/personal/selectPersonal");
               // .addPathPatterns("/translate");
        super.addInterceptors(registry);
    }
}

