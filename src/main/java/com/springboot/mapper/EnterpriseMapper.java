package com.springboot.mapper;

import com.springboot.domain.TpEnterprise;
import com.springboot.domain.TpEnterpriseProject;
import com.springboot.domain.TpFile;
import com.springboot.domain.TpPersonInfo;
import com.springboot.dto.Enterprise;
import com.springboot.dto.EnterpriseResetPass;
import com.springboot.dto.Password;
import com.springboot.dto.EnterpriseProject;
import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * Created by Administrator on 2017/7/12.
 */

@Mapper
public interface EnterpriseMapper {

    @Insert("insert into tp_enterprise(name, password, tel, email, active_code,status) " +
            "values #{name}, #{password}, #{tel}, #{email}, #{activeCode}, #{status})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    void insertEnterprise(TpEnterprise enterprise);

    @Select("select * from tp_enterprise where name=#{name}")
    @Results({
            @Result(column = "active_code", property = "activeCode")
    })
    TpEnterprise selectByName(@Param("name") String name);

//    @Insert("insert into tp_enterprise(city, industry, name, tel, password) " +
//            "values(#{city}, #{industry}, #{name}, #{tel}, #{password})")
//    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
//    void insertEnterprise(TpEnterprise enterprise);

    @Update("update tp_enterprise set city = #{city},tel= #{tel},industry = #{industry} where name =#{name}")
    void updateEnterpriseByName(Enterprise enterprise);

    @Update("update tp_enterprise set password = #{password} where name = #{name}")
    void updateEnterprisePassByName(Password password);

    @Update("update tp_enterprise set password = #{newPassword} where name = #{name}")
    void resetPass(EnterpriseResetPass enterpriseResetPass);

    @Insert("insert into tp_enterprise_project(language, contact, tel, email, city, address, introduce, cooperation_type, industry, requirement, treatment, register_time, work_type, project_title, company_name, translate_type)" +
            "values(#{language}, #{contact}, #{tel}, #{email}, #{city}, #{address},#{introduce}, #{cooperationType}, #{industry}, #{requirement}, #{treatment}, #{registerTime}, #{workType}, #{projectTitle}, #{companyName}, #{translateType})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    void newProject(TpEnterpriseProject tpEnterpriseProject);

    @Select("select * from tp_enterprise_project where((language=#{language}) or (#{language} is null)) and ((tel=#{tel}) or (#{tel} is null)) and ((address=#{address}) or (#{address}) is null) and ((requirement=#{requirement}) or (#{requirement} is null)) and ((treatment=#{treatment}) or (#{treatment} is null)) and ((work_type=#{workType}) or (#{workType} is null))")
    @Results({
            @Result(column = "work_type", property = "workType")
    })
    List<TpEnterpriseProject> selectProjects(EnterpriseProject enterpriseProject);

    @Select("select * from tp_enterprise_project order by register_time desc limit 4")
    @Results({
            @Result(column = "cooperation_type", property = "cooperationType"),
            @Result(column = "work_type", property = "workType"),
            @Result(column = "register_time", property = "registerTime"),
    })
    List<TpEnterpriseProject> selectLatest();

    @Select("select * from tp_enterprise_project where id = #{id}")
    @Results({
            @Result(column = "cooperation_type", property = "cooperationType"),
            @Result(column = "work_type", property = "workType"),
            @Result(column = "registerTime", property = "register_time")
    })
    TpEnterpriseProject selectProjectById(EnterpriseProject enterpriseProject);

    @Delete("delete from tp_enterprise_project where id = #{id}")
    Integer delProject(Integer id);

    @Update("update tp_enterprise_project set click_amount = #{clickAmount}, stars = #{stars} where id = #{id}")
    void addClickAmount(TpEnterpriseProject tpEnterpriseProject);

    @Update("update tp_enterprise set status = #{status} where name = #{name}")
    void updateStatus(TpEnterprise tpEnterprise);

    @Select("select* from tp_file where name = #{name}")
    @Results({
            @Result(column = "file_name", property = "fileName"),
            @Result(column = "file_path", property = "filePath"),
            @Result(column = "picture_name", property = "pictureName"),
            @Result(column = "picture_path", property = "picturePath")
    })
    TpFile selectTpFileByName(TpEnterpriseProject tpEnterpriseProject);

}
