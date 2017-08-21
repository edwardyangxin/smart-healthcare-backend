package com.springboot.mapper;

import com.springboot.domain.TpEnterpriseProject;
import com.springboot.domain.TpPersonInfo;
import com.springboot.domain.TpServiceProvider;
import com.springboot.dto.Password;
import com.springboot.dto.PersonInfo;
import com.springboot.dto.ServiceProvider;
import com.springboot.dto.ServiceProviderResetPass;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */

@Mapper
public interface ServiceProviderMapper {

    @Insert("insert into tp_service_provider(name, password, tel, email, active_code, status)" +
            " values(#{name}, #{password}, #{tel}, #{email}, #{activeCode}, #{status})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    void insertServiceProvider(TpServiceProvider tpserviceProvider);

    @Select("select * from tp_service_provider where name=#{name}")
    @Results({
            @Result(id = true, column = "description", property = "description"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "icon_address", property = "iconAddress"),
            @Result(column = "enterprise_id", property = "enterpriseId"),
            @Result(column = "active_code", property = "activeCode"),
            @Result(column = "business_license", property = "businessLicense"),
            @Result(column = "legal_representative", property = "legalRepresentative")
    })
    TpServiceProvider selectByName(@Param("name") String name);

    @Update("update tp_service_provider set city = #{city},contact=#{contact},tel=#{tel}  where name =#{name}")
    void updateServiceProviderByName(ServiceProvider serviceProvider);

    @Update("update tp_service_provider set password = #{password} where name = #{name}")
    void updateServiceProviderPass(Password password);

    @Update("update tp_service_provider set password=#{newPassword} where name =#{name}")
    void resetPass(ServiceProviderResetPass serviceProviderResetPass);

    @Insert("insert into tp_person_info(name,service_provider,address, age, city, education, email, salary_range, working_years, " +
            "project_experience, introduce, language, specialty, tel, cooperation_type, work_type, register_time) " +
            "values(#{name},#{serviceProvider}, #{address}, #{age}, #{city}, #{education}, #{email}, #{salaryRange}, #{workingYears}, " +
            "#{projectExperience}, #{introduce}, #{language}, #{specialty}, #{tel}, #{cooperationType}, #{workType}, #{registerTime})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    void newPersonInfo(TpPersonInfo tpPersonInfo);

    @Insert("insert into tp_enterprise_project(language,service_provider, contact, tel, email, city, address, introduce, cooperation_type, industry, requirement, treatment, register_time, work_type, project_title, company_name, translate_type)" +
            "values(#{language},#{serviceProvider}, #{contact}, #{tel}, #{email}, #{city}, #{address},#{introduce}, #{cooperationType}, #{industry}, #{requirement}, #{treatment}, #{registerTime}, #{workType}, #{projectTitle}, #{companyName}, #{translateType})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    void newEnterpriseInfo(TpEnterpriseProject tpEnterpriseProject);

    @Select("select * from tp_person_info where service_provider=true order by register_time desc limit 10")
    List<TpPersonInfo> selectInfoLatest();

    @Select("select * from tp_enterprise_project where service_provider=true order by register_time desc limit 10")
    List<TpEnterpriseProject>  selectProjectLatest();

    @Update("update tp_service_provider set status = #{status} where name = #{name}")
    void updateStatus(TpServiceProvider tpServiceProvider);

    @Update("UPDATE tp_person_info SET tp_person_info.icon_address = (SELECT tp_file.picture_path FROM tp_file where name= #{name}) where name = #{name}")
    void addIconAddress(TpPersonInfo tpPersonInfo);

    @Update("UPDATE tp_enterprise_project SET tp_enterprise_project.icon_address = (SELECT tp_file.picture_path FROM tp_file where name= #{companyName}) where company_name = #{companyName}")
    void addEnterpriseIconAddress(TpEnterpriseProject tpEnterpriseProject);
}


