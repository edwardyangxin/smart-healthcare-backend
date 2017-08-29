package com.springboot.service;

import com.springboot.domain.Result;
import com.springboot.domain.TpEnterpriseProject;
import com.springboot.domain.TpPersonInfo;
import com.springboot.domain.TpServiceProvider;
import com.springboot.dto.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */
public interface ServiceProviderService {

    TpServiceProvider selectAllByName(String name);

    String updateServiceProviderByName(ServiceProvider serviceProvider);

    String updateServiceProviderPass(Password password, HttpSession session);

    String resetServiceProviderPass(ServiceProviderResetPass serviceProviderResetPass);

    String newPersonInfo(TpPersonInfo tpPersonInfo,HttpSession session);

    String newEnterpriseInfo(TpEnterpriseProject tpEnterpriseProject,HttpSession session);

    //根据amount的值查询供应商最新发布的几条“个人信息”
    Result<List<TpPersonInfo>> selectPersonInfoLatestAmount(Integer amount);

    //根据amount的值查询供应商最新发布的几条“企业信息”
    Result<List<TpEnterpriseProject>> selectEnterpriseInfoLatestAmount(Integer amount);

    void sendMail(CheckMail checkMail) throws Exception;

    String emailCheck(CheckMail checkMail);
}
