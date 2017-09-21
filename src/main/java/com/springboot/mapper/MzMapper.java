package com.springboot.mapper;

import com.springboot.domain.MzMedicalHistory;
import com.springboot.domain.MzPatientHistory;
import com.springboot.domain.MzXrayTask;
import com.springboot.domain.User;
import com.springboot.dto.MzTaskDTO;
import com.springboot.dto.MzTasksDTO;
import com.springboot.dto.MzXRayTaskDTO;
import com.springboot.dto.Pid;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MzMapper {

    /*通过name查询一个用户*/
    @Select("select * from user where name=#{name}")
    @Results({
            @Result(column = "user_type", property = "userType")
    })
    User selectUserByName(@Param("name") String name);

    /*通过id查询一张病历*/
    @Select("select * from mz_patient_history where id = #{id} and created_by = #{createdBy}")
    @Results({
            @Result(column = "patient_name", property = "patientName"),
            @Result(column = "job_history", property = "jobHistory"),
            @Result(column = "medical_history", property = "medicalHistory"),
            @Result(column = "dust_age", property = "dustAge"),
            @Result(column = "dust_property", property = "dustProperty"),
            @Result(column = "created_on", property = "createdOn")
    })
    MzPatientHistory selectMzPatientHistoryById(@Param("id") Integer id, @Param("createdBy") Integer createdBy);

    /*根据前台显示查询病历表（id/姓名/性别/联系电话/接尘工龄/粉尘性质/就医时间/）*/
    @Select("select id,patient_name,sex,tel,dust_age,dust_property,created_on  from mz_patient_history where created_by=#{createdBy}")
    @Results({
            @Result(column = "patient_name", property = "patientName"),
            @Result(column = "dust_age", property = "dustAge"),
            @Result(column = "created_on", property = "createdOn"),
    })
    List<MzPatientHistory> selectMzPatientHistories(Integer createdBy);

    /*新建病历*/
    @Insert("insert into mz_patient_history(patient_name, sex, pid, tel, job, job_history, medical_history, dust_age, dust_property, created_by, created_on)" +
            "values(#{patientName},#{sex}, #{pid}, #{tel}, #{job}, #{jobHistory},#{medicalHistory}, #{dustAge}, #{dustProperty}, #{createdBy}, #{createdOn})")
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
    void insertMzPatientHistory(MzPatientHistory mzPatientHistory);

    /*修改病历*/
    @Update("update mz_patient_history set patient_name= #{patientName},sex= #{sex},pid = #{pid},tel = #{tel}, job = #{job},job_history = #{jobHistory}," +
            "medical_history = #{medicalHistory},dust_age = #{dustAge},dust_property = #{dustProperty} where id =#{id} and created_by =#{createdBy}")
    @Results({
            @Result(column = "patient_name", property = "patientName"),
            @Result(column = "job_history", property = "jobHistory"),
            @Result(column = "medical_history", property = "medicalHistory"),
            @Result(column = "dust_age", property = "dustAge"),
            @Result(column = "dust_property", property = "dustProperty"),
            @Result(column = "created_by", property = "createdBy"),
            @Result(column = "created_on", property = "createdOn")
    })
    void updateMzPatientHistoryById(MzPatientHistory mzPatientHistory);

    /*增加胸片审查任务*/
    @Insert("insert into mz_xray_task(created_by, created_on,patient_history_id, expert_id, review_result, review_comment, analysis_result, status, x_ray_id)" +
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
    void insertMzXrayTask(MzXrayTask mzXrayTask);

    /* 是否需要院外医生*/
    @Update("update mz_xray_task set need= #{need} where id =#{id}")
    void updateMzXrayTaskNeedById(@Param("id") Integer id, @Param("need") Boolean need);

    /*修改胸片审查任务表的review_result、review_comment、analysis_result字段*/
    @Update("update mz_xray_task set review_result= #{reviewResult},review_comment= #{reviewComment},analysis_result = #{analysisResult} where id =#{id}")
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
    void updateMzXrayTaskById(MzXrayTask mzXrayTask);


    /*修改胸片审查任务表的review_result、review_comment、analysis_result字段*/
    @Update("update mz_xray_task set outreview_result= #{outreviewResult},outreview_comment= #{outreviewComment} where id =#{id}")
    @Results({
            @Result(column = "outexpert_id", property = "outexpertId"),
            @Result(column = "outreview_result", property = "outreviewResult"),
            @Result(column = "outreview_comment", property = "outreviewComment")
    })
    void updateMzXrayTaskOutById(MzXrayTask mzXrayTask);

    /*通过id查询一张胸片审查任务表*/
    @Select("select xt.review_result,xt.analysis_result,xt.review_comment,xt.x_ray_id," +
            " ph.patient_name,ph.dust_age " +
            " from mz_xray_task xt" +
            " left join mz_patient_history ph on xt.patient_history_id = ph.id  where xt.id = #{id}")
    @Results({
            @Result(column = "patient_name", property = "patientName"),
            @Result(column = "dust_age", property = "dustAge"),
            @Result(column = "analysis_result", property = "analysisResult"),
            @Result(column = "review_result", property = "reviewResult"),
            @Result(column = "review_comment", property = "reviewComment"),
            @Result(column = "x_ray_id", property = "xRayId")
    })
    MzTaskDTO selectMzXrayTaskByIds(@Param("id") Integer id);

    @Select("select * from mz_medical_history where patient_history_id = #{patientHistoryId} order by start_time desc")
    @Results({
            @Result(column = "patient_history_id", property = "patientHistoryId"),
            @Result(column = "start_time", property = "startTime"),
            @Result(column = "end_time", property = "endTime")
    })
    List<MzMedicalHistory> selectMzMedicalHistoryByPatientId(@Param("patientHistoryId")Integer patientHistoryId);

    /*查询所有胸片审查任务（显示所有字段）*/
    @Select("select xt.id as task_id,ph.patient_name,u.name,xt.review_result,xt.analysis_result,ph.id as pid,xt.status" +
            " from mz_xray_task xt" +
            " left join mz_patient_history ph on xt.patient_history_id = ph.id" +
            " left join user u on ph.created_by = u.id order by xt.created_on desc")
    @Results({
            @Result(column = "task_id", property = "taskId"),
            @Result(column = "patient_name", property = "patientName"),
            @Result(column = "review_result", property = "reviewResult"),
            @Result(column = "analysis_result", property = "analysisResult")
    })
    List<MzTasksDTO> selectMzXrayTasks();

    /*通过id查询一张胸片审查任务表*/
    @Select("select * from mz_xray_task where id = #{id}")
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
    MzXrayTask selectMzXrayTaskById(@Param("id") Integer id);


    /*查询所有院外医生胸片审查任务（显示所有字段）*/
    @Select("select * from mz_xray_task where need = true  order by created_on desc")
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
    List<MzXrayTask> selectAllMzOutExpertTasks();

    /*院外专家修改（提交插入）任务表*/
    @Update("update mz_xray_task set outreview_comment= #{outreviewComment},outreview_result=#{outreviewCesult} where id =#{id}")
    @Results({
            @Result(column = "outreview_comment", property = "outreviewComment"),
            @Result(column = "outreview_result", property = "outreviewCesult")
    })
    void updateOneMzOutExpertTask(MzXrayTask mzXrayTask);

    @Select("select pid from mz_patient_history where pid = #{pid}")
    List<Pid> selectByPid(Pid pid);

    @Insert({
            "<script>",
            "insert into mz_medical_history (description, patient_history_id,start_time,end_time)"+
                    "values "+
                    "<foreach collection='mzMedicalHistories' item='dmo' separator=','>"+
                    "(#{dmo.description,jdbcType=VARCHAR},#{patientHistoryId},#{dmo.startTime},#{dmo.endTime})"+
                    "</foreach>"+
                    "</script>"
    })
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    @Results({
            @Result(column = "patient_history_id", property = "patientHistoryId"),
            @Result(column = "start_time", property = "startTime"),
            @Result(column = "end_time", property = "endTime"),
    })
    int insertMzMedicalHistories(@Param("mzMedicalHistories") List<MzMedicalHistory> mzMedicalHistories, @Param("patientHistoryId")Integer patientHistoryId);



    @Delete("delete from mz_medical_history where patient_history_id=#{patientHistoryId}")
    @Results({
            @Result(column = "patient_history_id", property = "patientHistoryId"),
            @Result(column = "start_time", property = "startTime"),
            @Result(column = "end_time", property = "endTime")
    })
    Integer deleteMzMedicalHistory(@Param("patientHistoryId")Integer patientHistoryId);


    @Select("select xt.outreview_result,xt.outreview_comment,xt.status,xt.review_comment,xt.analysis_result,xt.created_on,xt.review_result,xt.review_comment," +
            "uf.file_name,u.name as created_by,u1.name as expert_id,u2.name as outexpert_id " +
            "from mz_xray_task xt " +
            "left join upload_file uf on xt.x_ray_id = uf.id " +
            "left join user u on xt.created_by = u.id " +
            "left join user u1 on xt.expert_id = u1.id " +
            "left join user u2 on xt.outexpert_id = u2.id " +
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
            @Result(column = "file_name", property = "fileName"),
            @Result(column = "outexpert_id", property = "outexpertId"),
            @Result(column = "outreview_result", property = "outreviewResult"),
            @Result(column = "outreview_comment", property = "outreviewComment")

    })
    List<MzXRayTaskDTO> selectMzXRayTasksByPationId(@Param("patientHistoryId") Integer patientHistoryId, @Param("createdBy") Integer createdBy);

}
