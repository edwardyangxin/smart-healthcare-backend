package com.springboot.mapper;


import com.springboot.domain.TpFile;
import com.springboot.domain.TpPersonInfo;
import com.springboot.domain.TpPersonal;
import com.springboot.dto.*;
import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * Created by Administrator on 2017/7/12.
 */

@Mapper
public interface PersonalMapper {

    @Insert("insert into tp_personal(name, password, email, active_code, status) " +
            "values(#{name}, #{password}, #{email}, #{activeCode}, #{status})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    void insertPerson(TpPersonal tpPersonal);

    @Select("select * from tp_personal where name=#{name}")
    @Results({
            @Result(column = "real_name", property = "realName"),
            @Result(column = "icon_address", property = "iconAddress"),
            @Result(column = "active_code", property = "activeCode")
    })
    TpPersonal selectByName(@Param("name") String name);

    @Select("select * from tp_personal where real_name=#{realName}")
    @Results({
            @Result(column = "real_name", property = "realName"),
            @Result(column = "icon_address", property = "iconAddress"),
            @Result(column = "active_code", property = "activeCode")
    })
    TpPersonal selectByRealName(@Param("realName") String realName);

    @Update("update tp_personal set password=#{newPassword} where real_name =#{realName}")
    void resetPass(PersonalResetPass personalResetPass);

    @Update("update tp_personal set password = #{password} where name = #{name}")
    void updatePassword(Password password);

    @Update("update tp_personal set real_Name = #{realName},tel= #{tel},email = #{email} where name =#{name}")
    void updatePersonByName(Personal person);

    @Update("update tp_personal set status = #{status} where name = #{name}")
    void updateStatus(TpPersonal tpPersonal);

    @Insert("insert into tp_person_info(name, address, age, city, education, email, salary_range, working_years, " +
            "project_experience, introduce, language, translate_type, industry, uuid, tel, cooperation_type, work_type, register_time) " +
            "values(#{name}, #{address}, #{age}, #{city}, #{education}, #{email}, #{salaryRange}, #{workingYears}, " +
            "#{projectExperience}, #{introduce}, #{language}, #{translateType}, #{industry}, #{uuid}, #{tel}, #{cooperationType}, #{workType}, #{registerTime})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    void newInfo(TpPersonInfo tpPersonInfo);

    @Update("update tp_person_info set address = #{address}, age = #{age}, city = #{city}, education = #{education}, " +
            "email = #{email}, salary_range = #{salaryRange}, working_years = #{workingYears}, project_experience = #{projectExperience}, " +
            "introduce = #{introduce}, language = #{language}, translate_type = #{translateType}, industry = #{industry}, tel = #{tel}, " +
            "cooperation_type = #{cooperationType}, work_type = #{workType}, click_amount = #{clickAmount}, register_time = #{registerTime} where id = #{id}")
    void updateInfoById(TpPersonInfo tpPersonInfo);

    @Update("update tp_person_info set click_amount = #{clickAmount}, stars = #{stars} where id = #{id}")
    void addClickAmount(TpPersonInfo tpPersonInfo);

    @Update("UPDATE tp_person_info SET tp_person_info.icon_address = (SELECT tp_file.picture_path FROM tp_file where name= #{name}) where name = #{name}")
    void addIconAddress(TpPersonInfo tpPersonInfo);

    @Delete("delete from tp_person_info where id = #{id}")
    Integer delInfo(Integer id);

    @Select("select * from tp_person_info where ((id=#{id}) or (#{id} is null)) and ((name=#{name}) or (#{name} is null)) " +
            "and ((city=#{city}) or (#{city} is null)) and ((language=#{language}) or (#{language} is null))" +
            " and ((translate_type=#{translateType}) or (#{translateType} is null)) and ((industry=#{industry}) or (#{industry} is null)) " +
            "and ((education=#{education}) or (#{education} is null)) and ((cooperation_type=#{cooperationType}) or (#{cooperationType} is null)) " +
            "and ((work_type=#{workType}) or (#{workType} is null))")
    @Results({
            @Result(column = "cooperation_type", property = "cooperationType"),
            @Result(column = "translate_type", property = "translateType"),
            @Result(column = "work_type", property = "workType"),
            @Result(column = "salary_range", property = "salaryRange"),
            @Result(column = "working_years", property = "workingYears"),
            @Result(column = "project_experience", property = "projectExperience"),
            @Result(column = "register_time", property = "registerTime"),
            @Result(column = "icon_address", property = "iconAddress"),
            @Result(column = "click_amount", property = "clickAmount")
    })
    List<TpPersonInfo> selectInfos(PersonInfo personInfo);

    @Select("select * from tp_person_info where id=#{id}")
    @Results({
            @Result(column = "cooperation_type", property = "cooperationType"),
            @Result(column = "translate_type", property = "translateType"),
            @Result(column = "work_type", property = "workType"),
            @Result(column = "salary_range", property = "salaryRange"),
            @Result(column = "working_years", property = "workingYears"),
            @Result(column = "project_experience", property = "projectExperience"),
            @Result(column = "register_time", property = "registerTime"),
            @Result(column = "icon_address", property = "iconAddress"),
            @Result(column = "click_amount", property = "clickAmount")
    })
    TpPersonInfo selectInfoById(PersonInfo personInfo);

    @Select("select* from tp_person_info order by register_time desc limit #{amount}")
    @Results({
            @Result(column = "cooperation_type", property = "cooperationType"),
            @Result(column = "translate_type", property = "translateType"),
            @Result(column = "work_type", property = "workType"),
            @Result(column = "salary_range", property = "salaryRange"),
            @Result(column = "working_years", property = "workingYears"),
            @Result(column = "project_experience", property = "projectExperience"),
            @Result(column = "register_time", property = "registerTime"),
            @Result(column = "icon_address", property = "iconAddress"),
            @Result(column = "click_amount", property = "clickAmount")
    })
    List<TpPersonInfo> selectLatest(@Param("amount") Integer amount);

    @Select("select* from tp_file where name = #{name}")
    @Results({
            @Result(column = "file_name", property = "fileName"),
            @Result(column = "file_path", property = "filePath"),
            @Result(column = "picture_name", property = "pictureName"),
            @Result(column = "picture_path", property = "picturePath")
    })
    TpFile selectTpFileByName(TpPersonInfo tpPersonInfo);

    @Insert("insert into tp_file(name, picture_path, uuid) " +
            "values(#{name}, #{picturePath}, #{uuid})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    void newTpFile(TpFile tpFile);
}
