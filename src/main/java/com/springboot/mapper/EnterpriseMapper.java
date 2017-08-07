package com.springboot.mapper;

import com.springboot.domain.TpEnterprise;
import com.springboot.domain.TpEnterpriseProject;
import com.springboot.dto.Enterprise;
import com.springboot.dto.EnterpriseResetPass;
import com.springboot.dto.Password;
import com.springboot.dto.SelectEnterpriseProject;
import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * Created by Administrator on 2017/7/12.
 */

@Mapper
public interface EnterpriseMapper {
    @Select("select * from tp_enterprise where name=#{name}")
    TpEnterprise selectByName(@Param("name") String name);

    @Insert("insert into tp_enterprise(city, industry, name, tel, password) " +
            "values(#{city}, #{industry}, #{name}, #{tel}, #{password})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    void insertEnterprise(TpEnterprise enterprise);

    @Update("update tp_enterprise set city = #{city},tel= #{tel},industry = #{industry} where name =#{name}")
    void updateEnterpriseByName(Enterprise enterprise);

    @Update("update tp_enterprise set password = #{password} where name = #{name}")
    void updateEnterprisePassByName(Password password);

    @Update("update tp_enterprise set password = #{newPassword} where name = #{name}")
    void resetPass(EnterpriseResetPass enterpriseResetPass);

    @Insert("insert into tp_enterprise_project(language, contact, tel, email, city, address, introduce, cooperation_type, sex, industry, requirement, treatment, qq, release_time, work_time)" +
            "values(#{language}, #{contact}, #{tel}, #{email}, #{city}, #{address},#{introduce}, #{cooperationType}, #{sex}, #{industry}, #{requirement}, #{treatment}, #{qq}, #{releaseTime}, #{workTime})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    void newProject(TpEnterpriseProject tpEnterpriseProject);

    @Select("select * from tp_enterprise_project where((language=#{language}) or (#{language} is null)) and ((tel=#{tel}) or (#{tel} is null)) and ((address=#{address}) or (#{address}) is null) and ((requirement=#{requirement}) or (#{requirement} is null)) and ((treatment=#{treatment}) or (#{treatment} is null)) and ((work_time=#{workTime}) or (#{workTime} is null))")
    @Results({
            @Result(column = "work_time", property = "workTime")
    })
    List<TpEnterpriseProject> selectProjects(SelectEnterpriseProject selectEnterpriseProject);

    @Select("select * from tp_enterprise_project order by release_time desc limit 10")
    @Results({
            @Result(column = "cooperation_type", property = "cooperationType"),
            @Result(column = "work_time", property = "workTime"),
            @Result(column = "release_time", property = "releaseTime"),
    })
    List<TpEnterpriseProject> selectLatest();
}
