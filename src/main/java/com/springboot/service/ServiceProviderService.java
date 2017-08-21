package com.springboot.service;

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
    String login(Login login, HttpSession session);

    TpServiceProvider selectByName(String name);

    String insertServiceProvider(TpServiceProvider serviceProvider);

    String updateServiceProviderByName(ServiceProvider serviceProvider);

    String updateServiceProviderPass(Password password, HttpSession session);

    String resetServiceProviderPass(ServiceProviderResetPass serviceProviderResetPass);

    String newPersonInfo(TpPersonInfo tpPersonInfo,HttpSession session);

    String newEnterpriseInfo(TpEnterpriseProject tpEnterpriseProject,HttpSession session);

    List<TpPersonInfo> selectInfoLatest();

    List<TpEnterpriseProject> selectProjectLatest();


    void sendMail(CheckMail checkMail) throws Exception;

    String emailCheck(CheckMail checkMail);
}
