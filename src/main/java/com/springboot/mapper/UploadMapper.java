package com.springboot.mapper;

import com.springboot.domain.TpFile;
import org.apache.ibatis.annotations.*;


/**
 * Created by Administrator on 2017/7/12.
 */

@Mapper
public interface UploadMapper {

    @Update("update Tp_File set picture_path= #{picturePath}, picture_name= #{pictureName} where uuid = #{uuid}")
    @Results({
            @Result(column = "picture_path", property = "picturePath"),
            @Result(column = "picture_name", property = "pictureName"),
    })
    void updatePicture(TpFile file);

    @Update("update Tp_File set file_path = #{filePath}, file_name=#{fileName} where uuid = #{uuid}")
    @Results({
            @Result(column = "file_path", property = "filePath"),
            @Result(column = "file_name", property = "fileName")
    })
    void updateFile(TpFile file);

    @Select("select * from Tp_File where uuid=#{uuid}")
    @Results({
            @Result(column = "picture_name", property = "pictureName"),
            @Result(column = "file_name", property = "fileName")
    })
    TpFile findFile(String name);
}
