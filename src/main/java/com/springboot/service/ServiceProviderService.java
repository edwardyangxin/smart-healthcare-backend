package com.springboot.service;

import com.springboot.domain.TpServiceProvider;

/**
 * Created by Administrator on 2017/7/12.
 */
public interface ServiceProviderService {
    String login(String name, String password);
    TpServiceProvider selectByName(String name);
}
