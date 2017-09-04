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

    //根据amount的值查询企业最新发布的几条信息
    Result<List<TpEnterpriseProject>> selectEnterpriseInfoLatestAmount(Integer amount);

    //根据session中的UUID，去查询企业用户自身信息
    Result<TpEnterprise> selectEnterpriseByName(HttpSession session);

    //完善修改企业信息
    Result updateEnterpriseByName(TpEnterprise tpEnterprise, HttpSession session);

    //企业发布消息
    Result newEnterpriseProject(TpEnterpriseProject tpEnterpriseProject, HttpSession session);

    //修改企业已发布的项目信息
    Result updateEnterpriseProjectById(TpEnterpriseProject tpEnterpriseProject, HttpSession session);

    //删除企业已发布的项目信息
    Result deleteEnterpriseProject(Integer id,HttpSession session);

    TpEnterprise selectAllByName(String name);





    List<TpEnterpriseProject> selectProjects(EnterpriseProject enterpriseProject);

    TpEnterpriseProject selectProjectById(EnterpriseProject enterpriseProject);



    void sendMail(CheckMail checkMail) throws Exception;

    String emailCheck(CheckMail checkMail);


}
