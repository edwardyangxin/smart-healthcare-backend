package com.springboot.service;

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

    List<TpEnterpriseProject> selectLatest();

    void sendMail(CheckMail checkMail) throws Exception;

    String emailCheck(CheckMail checkMail);


}
