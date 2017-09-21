package com.springboot.mapper;

import com.springboot.domain.MedicalHistory;
import com.springboot.domain.PatientHistory;
import com.springboot.domain.User;
import com.springboot.domain.XRayTask;
import com.springboot.dto.Pid;
import com.springboot.dto.TjTaskDTO;
import com.springboot.dto.TjTasksDTO;
import com.springboot.dto.XRayTaskDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TjMapper {

    /*通过name查询一个用户*/
    @Select("select * from user where name=#{name}")
    @Results({
            @Result(column = "user_type", property = "userType")
    })
    User selectUserByName(@Param("name") String name);

    /*通过id查询一张病历*/
    @Select("select * from patient_history where id = #{id} and created_by = #{createdBy}")
    @Results({
            @Result(column = "patient_name", property = "patientName"),
            @Result(column = "job_history", property = "jobHistory"),
            @Result(column = "medical_history", property = "medicalHistory"),
            @Result(column = "dust_age", property = "dustAge"),
            @Result(column = "dust_property", property = "dustProperty"),
            @Result(column = "created_on", property = "createdOn")
    })
    PatientHistory selectPatientHistoryById(@Param("id") Integer id, @Param("createdBy") Integer createdBy);

    /*根据前台显示查询病历表（id/姓名/性别/联系电话/接尘工龄/粉尘性质/就医时间/）*/
    @Select("select id,patient_name,sex,tel,dust_age,dust_property,created_on  from patient_history where created_by=#{createdBy}")
    @Results({
            @Result(column = "patient_name", property = "patientName"),
            @Result(column = "dust_age", property = "dustAge"),
            @Result(column = "created_on", property = "createdOn")
    })
    List<PatientHistory> selectPatientHistories(Integer createdBy);

    /*新建病历*/
    @Insert("insert into patient_history(patient_name, sex,birthday, pid, tel, job, job_history, dust_age, dust_property, created_by, created_on)" +
            "values(#{patientName},#{sex}, #{birthday}, #{pid}, #{tel}, #{job}, #{jobHistory}, #{dustAge}, #{dustProperty}, #{createdBy}, #{createdOn})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    @Results({
            @Result(column = "patient_name", property = "patientName"),
            @Result(column = "job_history", property = "jobHistory"),
            @Result(column = "dust_age", property = "dustAge"),
            @Result(column = "dust_property", property = "dustProperty"),
            @Result(column = "created_by", property = "createdBy"),
            @Result(column = "created_on", property = "createdOn")
    })
    void insertPatientHistory(PatientHistory patientHistory);

    /*修改病历*/
    @Update("update patient_history set patient_name= #{patientName},sex= #{sex},pid = #{pid},tel = #{tel}, job = #{job},job_history = #{jobHistory}," +
            "dust_age = #{dustAge},dust_property = #{dustProperty} where id =#{id} and created_by =#{createdBy}")
    @Results({
            @Result(column = "patient_name", property = "patientName"),
            @Result(column = "job_history", property = "jobHistory"),
            @Result(column = "dust_age", property = "dustAge"),
            @Result(column = "dust_property", property = "dustProperty"),
            @Result(column = "created_by", property = "createdBy"),
            @Result(column = "created_on", property = "createdOn")
    })
    void updatePatientHistoryById(PatientHistory patientHistory);

    /*增加胸片审查任务*/
    @Insert("insert into xray_task(created_by, created_on,patient_history_id, expert_id, review_result, review_comment, analysis_result, status, x_ray_id)" +
            "values(#{createdBy},#{createdOn}, #{patientHistoryId}, #{expertId}, #{reviewResult}, #{reviewComment}, #{analysisResult},#{status}, #{xRayId}) ")
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

    /*修改胸片审查任务表的review_result、review_comment、analysis_result字段*/
    @Update("update xray_task set review_result= #{reviewResult},review_comment= #{reviewComment},analysis_result = #{analysisResult} where id =#{id}")
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
    void updateXRayTaskById(XRayTask xRayTask);

    /*通过id查询一张胸片审查任务表*/
    @Select("select xt.review_result,xt.analysis_result,xt.review_comment,xt.x_ray_id," +
            " ph.patient_name,ph.dust_age " +
            " from xray_task xt" +
            " left join patient_history ph on xt.patient_history_id = ph.id  where xt.id = #{id}")
    //@ResultMap("com.springboot.mapper.TjMapper.allResults")
    @Results({
            @Result(column = "patient_name", property = "patientName"),
            @Result(column = "dust_age", property = "dustAge"),
            @Result(column = "analysis_result", property = "analysisResult"),
            @Result(column = "review_result", property = "reviewResult"),
            @Result(column = "review_comment", property = "reviewComment"),
            @Result(column = "x_ray_id", property = "xRayId")
    })
    TjTaskDTO selectXRayTaskById(@Param("id") Integer id);


    @Select("select xt.analysis_result,xt.created_on,xt.review_result,xt.review_comment,uf.file_name,u.name as created_by,u1.name as expert_id " +
            "from xray_task xt " +
            "left join upload_file uf on xt.x_ray_id = uf.id " +
            "left join user u on xt.created_by = u.id " +
            "left join user u1 on xt.expert_id = u1.id " +
            "where patient_history_id=#{patientHistoryId} and created_by=#{createdBy} " +
            "order by created_on desc")
    @Results({
            @Result(column = "analysis_result", property = "analysisResult"),
            @Result(column = "created_by", property = "createdBy"),
            @Result(column = "created_on", property = "createdOn"),
            @Result(column = "expert_id", property = "expertId"),
            @Result(column = "patient_history_id", property = "patientHistoryId"),
            @Result(column = "review_result", property = "reviewResult"),
            @Result(column = "review_comment", property = "reviewComment"),
            @Result(column = "x_ray_id", property = "xRayId"),
            @Result(column = "file_name", property = "fileName")

    })
    List<XRayTaskDTO> selectXRayTasksByPationId(@Param("patientHistoryId") Integer patientHistoryId, @Param("createdBy") Integer createdBy);


    @Select("select xt.id as task_id,ph.patient_name,u.name,xt.review_result,xt.analysis_result,ph.id as pid,xt.status" +
            " from xray_task xt" +
            " left join patient_history ph on xt.patient_history_id = ph.id" +
            " left join user u on ph.created_by = u.id order by xt.created_on desc")
    @Results({
            @Result(column = "task_id", property = "taskId"),
            @Result(column = "patient_name", property = "patientName"),
            @Result(column = "review_result", property = "reviewResult"),
            @Result(column = "analysis_result", property = "analysisResult")
    })
    List<TjTasksDTO> selectXRayTasks();


    @Select("select pid from patient_history where pid = #{pid}")
    List<Pid> selectByPid(Pid pid);

    @Select("select * from medical_history where patient_history_id = #{patientHistoryId} order by start_time desc")
    @Results({
            @Result(column = "patient_history_id", property = "patientHistoryId"),
            @Result(column = "start_time", property = "startTime"),
            @Result(column = "end_time", property = "endTime")
    })
    List<MedicalHistory> selectMedicalHistoryByPatientId(@Param("patientHistoryId") Integer patientHistoryId);


    @Delete("delete from medical_history where patient_history_id=#{patientHistoryId}")
    @Results({
            @Result(column = "patient_history_id", property = "patientHistoryId"),
            @Result(column = "start_time", property = "startTime"),
            @Result(column = "end_time", property = "endTime")
    })
    Integer deleteMedicalHistory(@Param("patientHistoryId") Integer patientHistoryId);

    @Insert({
            "<script>",
            "insert into medical_history (description, patient_history_id,start_time,end_time)" +
                    "values " +
                    "<foreach collection='medicalHistories' item='dmo' separator=','>" +
                    "(#{dmo.description,jdbcType=VARCHAR},#{patientHistoryId},#{dmo.startTime},#{dmo.endTime})" +
                    "</foreach>" +
                    "</script>"
    })
    @Results({
            @Result(column = "patient_history_id", property = "patientHistoryId"),
            @Result(column = "start_time", property = "startTime"),
            @Result(column = "end_time", property = "endTime"),
    })
    int insertMedicalHistory(@Param("medicalHistories") List<MedicalHistory> medicalHistories, @Param("patientHistoryId") Integer patientHistoryId);


}

