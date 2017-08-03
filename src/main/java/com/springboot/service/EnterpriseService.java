package com.springboot.service;

import com.springboot.domain.TpEnterprise;
import com.springboot.domain.TpEnterpriseProject;
import com.springboot.dto.Enterprise;
import com.springboot.dto.EnterpriseResetPass;
import com.springboot.dto.Password;
import com.springboot.dto.SelectEnterpriseProject;

import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */
public interface EnterpriseService {

    String login(String name, String password);
    TpEnterprise selectByName(String name);
    String insertEnterprise(TpEnterprise enterprise);
    String updateEnterpriseByName(Enterprise enterprise);
    String updateEnterprisePassByName(Password password);
    String resetEnterprisePass(EnterpriseResetPass enterpriseResetPass);
    String newProject(TpEnterpriseProject tpEnterpriseProject);
    List<TpEnterpriseProject> selectProjects(SelectEnterpriseProject selectEnterpriseProject);
    List<TpEnterpriseProject> selectLatest();
}
