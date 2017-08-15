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

    TpEnterprise selectByName(String name);

    String insertEnterprise(TpEnterprise enterprise);

    String updateEnterpriseByName(Enterprise enterprise);

    String updateEnterprisePassByName(Password password);

    String resetEnterprisePass(EnterpriseResetPass enterpriseResetPass);

    String newProject(TpEnterpriseProject tpEnterpriseProject);

    String delProject(EnterpriseProject enterpriseProject);

    List<TpEnterpriseProject> selectProjects(EnterpriseProject enterpriseProject);

    TpEnterpriseProject selectProjectById(EnterpriseProject enterpriseProject);

    List<TpEnterpriseProject> selectLatest();

    void sendMail(CheckMail checkMail) throws Exception;

    String emailCheck(CheckMail checkMail);



}
