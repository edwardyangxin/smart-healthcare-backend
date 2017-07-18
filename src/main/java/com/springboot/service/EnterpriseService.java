package com.springboot.service;

import com.springboot.domain.TpEnterprise;
import com.springboot.dto.Enterprise;
import com.springboot.dto.Password;

/**
 * Created by Administrator on 2017/7/12.
 */
public interface EnterpriseService {

    String login(String name, String password);
    TpEnterprise selectByName(String name);
    String insertEnterprise(TpEnterprise enterprise);
    String updateEnterpriseByName(Enterprise enterprise);
    String updateEnterprisePassByName(Password password);

}
