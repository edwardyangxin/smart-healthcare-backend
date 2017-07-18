package com.springboot.service;

import com.springboot.domain.TpServiceProvider;
import com.springboot.dto.Password;
import com.springboot.dto.ServiceProvider;

/**
 * Created by Administrator on 2017/7/12.
 */
public interface ServiceProviderService {
    String login(String name, String password);
    TpServiceProvider selectByName(String name);
    String insertServiceProvider(TpServiceProvider serviceProvider);
    String updateServiceProviderByName(ServiceProvider serviceProvider);
    String updateServiceProviderPassByName(Password password);

}
