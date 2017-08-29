package com.springboot.service;

import com.springboot.domain.Result;
import com.springboot.domain.TpEnterprise;
import com.springboot.domain.TpEnterpriseProject;
import com.springboot.dto.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */
public interface EnterpriseService {

    String login(Login login, HttpSession session);

    TpEnterprise selectEnterpriseByName(HttpSession session);

    TpEnterprise selectByName(String name);

    String insertEnterprise(TpEnterprise enterprise);

    String updateEnterpriseByName(TpEnterprise tpEnterprise, HttpSession session);

    String updateEnterprisePass(Password password, HttpSession session);

    String resetEnterprisePass(EnterpriseResetPass enterpriseResetPass);

    String newProject(TpEnterpriseProject tpEnterpriseProject, HttpSession session);

    String updateEnterpriseProjectById(TpEnterpriseProject tpEnterpriseProject, HttpSession session);

    String delProject(EnterpriseProject enterpriseProject);

    List<TpEnterpriseProject> selectProjects(EnterpriseProject enterpriseProject);

    TpEnterpriseProject selectProjectById(EnterpriseProject enterpriseProject);

    //根据amount的值查询企业最新发布的几条信息
    Result<List<TpEnterpriseProject>> selectEnterpriseInfoLatestAmount(Integer amount);

    void sendMail(CheckMail checkMail) throws Exception;

    String emailCheck(CheckMail checkMail);


}
