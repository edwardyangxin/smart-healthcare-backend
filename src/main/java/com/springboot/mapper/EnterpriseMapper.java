package com.springboot.mapper;

import com.springboot.domain.TpEnterprise;
import com.springboot.dto.Enterprise;
import com.springboot.dto.Password;
import org.apache.ibatis.annotations.*;


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

    @Update("update tp_enterprise set city = #{city},tel= #{tel},industry = #{industry} where name =#{name}")
    void updateEnterpriseByName(Enterprise enterprise);

    @Update("update tp_enterprise set password = #{password} where name = #{name}")
    void updateEnterprisePassByName(Password password);

}
