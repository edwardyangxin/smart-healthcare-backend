package com.springboot.mapper;


import com.springboot.domain.TpPersonal;
import org.apache.ibatis.annotations.*;


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
            @Result(id = true, column = "real_name", property = "realName")
    })
    TpPersonal selectByName(@Param("name") String name);

    @Update("update tp_personal set password = #{password} where name = #{name}")
    void updatePassword(TpPersonal tpPersonal);

}
