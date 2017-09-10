package com.springboot.mapper;

import com.springboot.domain.PatientHistory;
import com.springboot.domain.User;
import com.springboot.domain.XRayTask;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SmartHealthcareMapper {

    /*通过name查询一个用户*/
    @Select("select * from user where name=#{name}")
    @Results({
            @Result(column = "user_type", property = "userType")
    })
    User selectUserByName(@Param("name") String name);

    /*通过id查询一张病历*/
    @Select("select * from patient_history where id = #{id}")
    @Results({
            @Result(column = "patient_name", property = "patientName"),
            @Result(column = "job_history", property = "jobHistory"),
            @Result(column = "medical_history", property = "medicalHistory"),
            @Result(column = "dust_age", property = "dustAge"),
            @Result(column = "dust_property", property = "dustProperty"),
            @Result(column = "created_by", property = "createdBy"),
            @Result(column = "created_on", property = "createdOn")
    })
    PatientHistory selectPatientHistoryById(Integer id);

    /*根据前台显示查询病历表（id/姓名/性别/联系电话/接尘工龄/粉尘性质/就医时间/）*/
    @Select("select id,patient_name,sex,tel,dust_age,dust_property,created_on  from patient_history where created_by=#{createdBy}")
    @Results({
            @Result(column = "patient_name", property = "patientName"),
            @Result(column = "dust_age", property = "dustAge"),
            @Result(column = "created_on", property = "createdOn"),
    })
    List<PatientHistory> selectPatientHistories(Integer createdBy);

    /*新建病历*/
    @Insert("insert into patient_history(patient_name, sex,age, pid, tel, job, job_history, medical_history, dust_age, dust_property, created_by, created_on)" +
            "values(#{patientName},#{sex}, #{age}, #{pid}, #{tel}, #{job}, #{jobHistory},#{medicalHistory}, #{dustAge}, #{dustProperty}, #{createdBy}, #{createdOn})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    @Results({
            @Result(column = "patient_name", property = "patientName"),
            @Result(column = "job_history", property = "jobHistory"),
            @Result(column = "medical_history", property = "medicalHistory"),
            @Result(column = "dust_age", property = "dustAge"),
            @Result(column = "dust_property", property = "dustProperty"),
            @Result(column = "created_by", property = "createdBy"),
            @Result(column = "created_on", property = "createdOn")
    })
    void insertPatientHistory(PatientHistory patientHistory);

    /*修改病历*/
    @Update("update patient_history set patient_name= #{patientName},sex= #{sex},age = #{age},pid = #{pid},tel = #{tel}, job = #{job},job_history = #{jobHistory}," +
            "medical_history = #{medicalHistory},dust_age = #{dustAge},dust_property = #{dustProperty},where id =#{id}")
    void updatePatientHistoryById(PatientHistory patientHistory);

    /*增加胸片审查任务*/
    @Insert("insert into xray_task(created_by, created_on,patient_history_id, expert_id, review_result, review_comment, analysis_result, status, x_ray_id)" +
            "values(#{createdBy},#{createdOn}, #{patientHistoryId}, #{expertId}, #{reviewResult}, #{reviewComment}, #{analysisResult},#{status}, #{xRayId}")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    @Results({
            @Result(column = "created_by", property = "createdBy"),
            @Result(column = "created_on", property = "createdOn"),
            @Result(column = "patient_history_id", property = "patientHistoryId"),
            @Result(column = "expert_id", property = "expertId"),
            @Result(column = "review_result", property = "reviewResult"),
            @Result(column = "review_comment", property = "reviewComment"),
            @Result(column = "analysis_result", property = "analysisResult"),
            @Result(column = "x_ray_id", property = "xRayId")
    })
    void insertXrayTask(XRayTask xRayTask);
}
