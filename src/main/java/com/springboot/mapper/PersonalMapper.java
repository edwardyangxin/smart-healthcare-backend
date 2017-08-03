package com.springboot.mapper;


import com.springboot.domain.TpPersonInfo;
import com.springboot.domain.TpPersonal;
import com.springboot.dto.Password;
import com.springboot.dto.Personal;
import com.springboot.dto.PersonalResetPass;
import com.springboot.dto.SelectPersonInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * Created by Administrator on 2017/7/12.
 */

@Mapper
public interface PersonalMapper {

    @Insert("insert into tp_personal(real_Name, name, password, tel, email) " +
            "values(#{realName}, #{name}, #{password}, #{tel}, #{email})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    void insertPerson(TpPersonal person);

    @Select("select * from tp_personal where name=#{name}")
    @Results({
            @Result(column = "real_name", property = "realName")
    })
    TpPersonal selectByName(@Param("name") String name);

    @Select("select * from tp_personal where real_name=#{realName}")
    @Results({
            @Result(column = "real_name", property = "realName")
    })
    TpPersonal selectByRealName(@Param("realName") String realName);

    @Update("update tp_personal set password=#{newPassword} where real_name =#{realName}")
    void resetPass(PersonalResetPass personalResetPass);

    @Update("update tp_personal set password = #{password} where name = #{name}")
    void updatePassword(Password password);

    @Update("update tp_personal set real_Name = #{realName},tel= #{tel},email = #{email} where name =#{name}")
    void updatePersonByName(Personal person);


    @Insert("insert into tp_person_info(name, address, age, city, education, email, salary_range, working_years, project_experience, introduce, language, specialty, tel, cooperation_type, register_time) " +
            "values(#{name}, #{address}, #{age}, #{city}, #{education}, #{email}, #{salaryRange}, #{workingYears}, #{projectExperience}, #{introduce}, #{language}, #{specialty}, #{tel}, #{cooperationType}, #{registerTime})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    void newInfo(TpPersonInfo tpPersonInfo);

    @Select("select * from tp_person_info where ((id=#{id}) or (#{id} is null)) and ((name=#{name}) or (#{name} is null)) and ((language=#{language}) or (#{language} is null)) and ((specialty=#{specialty}) or (#{specialty} is null)) and ((education=#{education}) or (#{education} is null)) and ((cooperation_type=#{cooperationType}) or (#{cooperationType} is null))")
    @Results({
            @Result(column = "cooperation_type", property = "cooperationType"),
            @Result(column = "salary_range", property = "salaryRange"),
            @Result(column = "working_years", property = "workingYears"),
            @Result(column = "project_experience", property = "projectExperience"),
            @Result(column = "register_time", property = "registerTime")
    })
    List<TpPersonInfo> selectInfos(SelectPersonInfo selectPersonInfo);

    @Select("select* from tp_person_info order by register_time desc limit 10")
    @Results({
            @Result(column = "cooperation_type", property = "cooperationType"),
            @Result(column = "salary_range", property = "salaryRange"),
            @Result(column = "working_years", property = "workingYears"),
            @Result(column = "project_experience", property = "projectExperience"),
            @Result(column = "register_time", property = "registerTime")
    })
    List<TpPersonInfo> selectLatest();

}
