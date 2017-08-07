package com.springboot.service;

import com.springboot.domain.TpServiceProvider;
import com.springboot.domain.TpServiceProviderInfo;
import com.springboot.dto.Password;
import com.springboot.dto.SelectServiceProviderInfo;
import com.springboot.dto.ServiceProvider;

import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */
public interface ServiceProviderService {
    String login(String name, String password);
    TpServiceProvider selectByName(String name);
    String insertServiceProvider(TpServiceProvider serviceProvider);
    String updateServiceProviderByName(ServiceProvider serviceProvider);
    String updateServiceProviderPassByName(Password password);
    String newInfo(TpServiceProviderInfo tpPersonInfo);
    List<TpServiceProviderInfo> selectInfos(SelectServiceProviderInfo selectServiceProviderInfo);
    List<TpServiceProviderInfo> selectLatest();
}
