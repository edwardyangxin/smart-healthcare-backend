package com.springboot.mapper;


import com.springboot.domain.TpPersonal;
import com.springboot.dto.Password;
import com.springboot.dto.Personal;
import com.springboot.dto.PersonalResetPass;
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
            @Result(column = "real_name",property = "realName")
    })
    TpPersonal selectByName(@Param("name") String name);

    @Select("select * from tp_personal where real_name=#{realName}")
    @Results({
            @Result(column = "real_name",property = "realName")
    })
    TpPersonal selectByRealName(@Param("realName") String realName);

    @Update("update tp_personal set password=#{newPassword} where real_name =#{realName}")
    void resetPass(PersonalResetPass personalResetPass);

    @Update("update tp_personal set password = #{password} where name = #{name}")
    void updatePassword(Password password);


    @Update("update tp_personal set real_Name = #{realName},tel= #{tel},email = #{email} where name =#{name}")
    void updatePersonByName(Personal person);
}
