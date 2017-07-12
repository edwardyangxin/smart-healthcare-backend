package com.springboot.mapper;

import com.springboot.domain.TpPersonal;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;

/**
 * Created by Administrator on 2017/7/12.
 */

@Mapper
public interface PersonalMapper {

    @Insert("insert into tp_personal(real_Name, user_Name, password, tel, email) " +
            "values(#{realName}, #{userName}, #{password}, #{tel}, #{email})")
    @SelectKey(statement="SELECT LAST_INSERT_ID()",keyProperty="id",before=false,resultType=Integer.class)
    void insertPerson(TpPersonal person);

}
