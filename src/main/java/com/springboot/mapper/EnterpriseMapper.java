package com.springboot.mapper;

import com.springboot.domain.TpEnterprise;
import com.springboot.domain.TpPersonal;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;

/**
 * Created by Administrator on 2017/7/12.
 */

@Mapper
public interface EnterpriseMapper {

    @Insert("insert into tp_enterprise(city, industry, name, tel, password) " +
            "values(#{city}, #{industry}, #{name}, #{tel}, #{password})")
    @SelectKey(statement="SELECT LAST_INSERT_ID()",keyProperty="id",before=false,resultType=Integer.class)
    void insertEnterprise(TpEnterprise enterprise);
}
