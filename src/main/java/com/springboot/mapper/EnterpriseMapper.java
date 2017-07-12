package com.springboot.mapper;

import com.springboot.domain.TpEnterprise;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

/**
 * Created by Administrator on 2017/7/12.
 */

@Mapper
public interface EnterpriseMapper {
    @Select("select * from tp_enterprise where name=#{name}")
    TpEnterprise selectByName(@Param("name") String name);

    @Insert("insert into tp_enterprise(city, industry, name, tel, password) " +
            "values(#{city}, #{industry}, #{name}, #{tel}, #{password})")
    @SelectKey(statement="SELECT LAST_INSERT_ID()",keyProperty="id",before=false,resultType=Integer.class)
    void insertEnterprise(TpEnterprise enterprise);
}
