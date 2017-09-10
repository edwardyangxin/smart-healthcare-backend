package com.springboot.mapper;

import com.springboot.domain.UploadFile;
import org.apache.ibatis.annotations.*;


@Mapper
public interface SmartFileUploadMapper {

    /*上传文件*/
    @Insert("insert into upload_file(file_path, file_name)" +
            "values(#{filePath},#{fileName}")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    @Results({
            @Result(column = "file_path", property = "filePath"),
            @Result(column = "file_name", property = "fileName"),
    })
    void insertUploadFile(UploadFile uploadFile);

    /*通过ID查询文件*/
    @Select("select * from upload_file where id = #{id}")
    @Results({
            @Result(column = "file_path", property = "filePath"),
            @Result(column = "file_name", property = "fileName"),
    })
    UploadFile selectUploadFileById(Integer id);
}
