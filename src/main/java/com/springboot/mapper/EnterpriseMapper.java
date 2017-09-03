package com.springboot.mapper;

import com.springboot.domain.TpEnterprise;
import com.springboot.domain.TpEnterpriseProject;
import com.springboot.domain.TpFile;
import com.springboot.dto.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */

@Mapper
public interface EnterpriseMapper {

    @Insert("insert into tp_enterprise(name, password, email, active_code,status) " +
            "values (#{name}, #{password}, #{email}, #{activeCode}, #{status})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    void insertEnterprise(TpEnterprise tpEnterprise);

    @Insert("insert into tp_enterprise(name, password, email, uuid, status) " +
            "values (#{name}, #{password}, #{email}, #{uuid}, #{status})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    void newEnterprise(Register register);

    @Select("select name,contact,tel,city from tp_enterprise where uuid=#{uuid}")
    TpEnterprise selectEnterpriseByName(String uuid);


    @Select("select password,status,uuid  from tp_enterprise where name=#{name}")
    @Results({
            @Result(column = "active_code", property = "activeCode"),
            @Result(column = "business_license", property = "businessLicense"),
            @Result(column = "legal_representative", property = "legalRepresentative"),
            @Result(column = "icon_address", property = "iconAddress"),
    })
    LoginReturn selectByName(@Param("name") String name);


    @Select("select * from tp_enterprise where name=#{name}")
    @Results({
            @Result(column = "active_code", property = "activeCode"),
            @Result(column = "business_license", property = "businessLicense"),
            @Result(column = "legal_representative", property = "legalRepresentative"),
            @Result(column = "icon_address", property = "iconAddress"),
    })
    TpEnterprise selectAllByName(@Param("name") String name);

    @Update("update tp_enterprise set city = #{city},tel= #{tel},business_license = #{businessLicense},contact = #{contact},industry = #{industry},legal_representative = #{legalRepresentative}," +
            "email = #{email} where uuid =#{uuid}")
    @Results({
            @Result(column = "business_license", property = "businessLicense"),
            @Result(column = "legal_representative", property = "legalRepresentative"),
            @Result(column = "icon_address", property = "iconAddress"),
    })
    void updateEnterpriseByName(TpEnterprise tpEnterprise);

    @Update("update tp_enterprise set password = #{password} where name = #{name}")
    void updateEnterprisePass(Password password);

    @Update("update tp_enterprise set password = #{newPassword} where name = #{name}")
    void resetPass(EnterpriseResetPass enterpriseResetPass);

    @Insert("insert into tp_enterprise_project(language, service_provider,contact, tel, email, city, address, introduce, cooperation_type, industry, requirement, treatment, register_time, work_type, project_title, company_name, translate_type)" +
            "values(#{language},#{serviceProvider}, #{contact}, #{tel}, #{email}, #{city}, #{address},#{introduce}, #{cooperationType}, #{industry}, #{requirement}, #{treatment}, #{registerTime}, #{workType}, #{projectTitle}, #{companyName}, #{translateType})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    @Results({
            @Result(column = "cooperation_type", property = "cooperationType"),
            @Result(column = "work_type", property = "workType"),
            @Result(column = "register_time", property = "registerTime"),
            @Result(column = "project_title", property = "projectTitle"),
            @Result(column = "company_name", property = "companyName"),
            @Result(column = "translate_type", property = "translateType"),
            @Result(column = "service_provider",property = "serviceProvider"),
            @Result(column = "click_amount", property = "clickAmount"),
            @Result(column = "icon_address", property = "iconAddress")
    })
    void newProject(TpEnterpriseProject tpEnterpriseProject);

    @Update("update tp_enterprise_project set language= #{language},contact= #{contact},tel = #{tel},email = #{email},city = #{city}, address = #{address},introduce = #{introduce},cooperation_type = #{cooperationType},industry = #{industry},requirement = #{requirement},treatment = #{treatment},work_type = #{workType},project_title = #{projectTitle},project_title = #{projectTitle} where id =#{id}")
    void updateEnterpriseProjectById(TpEnterpriseProject tpEnterpriseProject);

    @Select("select * from tp_enterprise_project where((language=#{language}) or (#{language} is null)) and ((tel=#{tel}) or (#{tel} is null)) and ((address=#{address}) or (#{address}) is null) and ((requirement=#{requirement}) or (#{requirement} is null)) and ((treatment=#{treatment}) or (#{treatment} is null)) and ((work_type=#{workType}) or (#{workType} is null)) ")
    @Results({
            @Result(column = "cooperation_type", property = "cooperationType"),
            @Result(column = "work_type", property = "workType"),
            @Result(column = "register_time", property = "registerTime"),
            @Result(column = "project_title", property = "projectTitle"),
            @Result(column = "company_name", property = "companyName"),
            @Result(column = "translate_type", property = "translateType"),
            @Result(column = "click_amount", property = "clickAmount"),
            @Result(column = "icon_address", property = "iconAddress")
    })
    List<TpEnterpriseProject> selectProjects(EnterpriseProject enterpriseProject);

    @Select("select * from tp_enterprise_project where service_provider=false order by register_time desc limit #{amount}")
    @Results({
            @Result(column = "cooperation_type", property = "cooperationType"),
            @Result(column = "work_type", property = "workType"),
            @Result(column = "register_time", property = "registerTime"),
            @Result(column = "project_title", property = "projectTitle"),
            @Result(column = "company_name", property = "companyName"),
            @Result(column = "translate_type", property = "translateType"),
            @Result(column = "click_amount", property = "clickAmount"),
            @Result(column = "icon_address", property = "iconAddress")
    })
    List<TpEnterpriseProject> selectEnterpriseInfoLatestAmount(@Param("amount") Integer amount);

    @Select("select * from tp_enterprise_project where id = #{id}")
    @Results({
            @Result(column = "cooperation_type", property = "cooperationType"),
            @Result(column = "work_type", property = "workType"),
            @Result(column = "register_time", property = "registerTime"),
            @Result(column = "project_title", property = "projectTitle"),
            @Result(column = "company_name", property = "companyName"),
            @Result(column = "translate_type", property = "translateType"),
            @Result(column = "click_amount", property = "clickAmount"),
            @Result(column = "icon_address", property = "iconAddress")
    })
    TpEnterpriseProject selectProjectById(Integer id);

    @Delete("delete from tp_enterprise_project where id = #{id} and uuid=#{uuid}")
    Integer deleteEnterpriseProject(Integer id,String uuid);

    @Update("update tp_enterprise_project set click_amount = #{clickAmount}, stars = #{stars} where id = #{id}")
    void addClickAmount(TpEnterpriseProject tpEnterpriseProject);

    @Update("update tp_enterprise set status = #{status} where name = #{name}")
    void updateStatus(TpEnterprise tpEnterprise);

    @Select("select* from tp_file where name = #{companyName}")
    @Results({
            @Result(column = "file_name", property = "fileName"),
            @Result(column = "file_path", property = "filePath"),
            @Result(column = "picture_name", property = "pictureName"),
            @Result(column = "picture_path", property = "picturePath")
    })
    TpFile selectTpFileByName(TpEnterpriseProject tpEnterpriseProject);

}
