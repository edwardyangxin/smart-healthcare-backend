package com.springboot.service;

import com.springboot.domain.Result;
import com.springboot.domain.TpEnterpriseProject;
import com.springboot.domain.TpPersonInfo;
import com.springboot.domain.TpServiceProvider;
import com.springboot.dto.CheckMail;
import com.springboot.dto.Password;
import com.springboot.dto.ResetPass;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */
public interface ServiceProviderService {

    //根据session中的UUID，去查询供应商用户自身信息
    Result<TpServiceProvider> selectServiceProviderByName(HttpSession session);

    //完善修改供应商信息
    Result updateServiceProviderByName(TpServiceProvider tpServiceProvider,HttpSession session);

    //根据amount的值查询供应商最新发布的几条“个人信息”
    Result<List<TpPersonInfo>> selectPersonInfoLatestAmount(Integer amount);

    //根据amount的值查询供应商最新发布的几条“企业信息”
    Result<List<TpEnterpriseProject>> selectEnterpriseInfoLatestAmount(Integer amount);

    //供应商发布个人信息
    Result newServiceProviderPersonInfo(TpPersonInfo tpPersonInfo,HttpSession session);

   //供应商发布企业信息
    Result newServiceProviderEnterpriseInfo(TpEnterpriseProject tpEnterpriseProject,HttpSession session);
    
    //根据id删除一条供应商已发布的个人信息
    Result delPersonPro(Integer id, HttpSession session);

    //根据id删除一条供应商已发布的企业信息
    Result delEnterprisePro(Integer id, HttpSession session);

    //修改供应商密码
    Result updateServiceProviderPass(Password password, HttpSession session);

    //修改供应商发布的个人信息
    Result updateServicePersonPro(TpPersonInfo tpPersonInfo,HttpSession session);

    //修改供应商发布的企业信息
    Result updateServiceEnterprisePro(TpEnterpriseProject tpEnterpriseProject,HttpSession session);

    //找回供应商密码
    Result resetServiceProviderPass(ResetPass resetPass);

    //供应商用户查询已发布的个人信息
    Result selectServicePersonAllPro(HttpSession session);

    //供应商用户查询已发布的企业信息
    Result selectServiceEnterAllPro(HttpSession session);

    TpServiceProvider selectAllByName(String name);

    void sendMail(CheckMail checkMail) throws Exception;

    String emailCheck(CheckMail checkMail);
}
