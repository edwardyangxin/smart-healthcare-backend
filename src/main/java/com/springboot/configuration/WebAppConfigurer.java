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
        registry.addInterceptor(new CommonInterceptor()).excludePathPatterns("/TM/TJ/login").excludePathPatterns("/TM/MZ/login");
     /*   registry.addInterceptor(new CommonInterceptor())
                .addPathPatterns("/TM/TJ/selectAllPatients")
                .addPathPatterns("/TM/TJ/selectOnePatient/{id}")
                .addPathPatterns("/TM/TJ/newPatient")
                .addPathPatterns("/TM/TJ/updatePatient")
                .addPathPatterns("/TM/TJ/newXTask")
                .addPathPatterns("/TM/TJ/files/{filename:.+}")
                .addPathPatterns("/TM/TJ/uploadFile");
               // .addPathPatterns("/translate");*/
        super.addInterceptors(registry);
    }
}

