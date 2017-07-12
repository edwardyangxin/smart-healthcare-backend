package com.springboot.mapper;

import com.springboot.domain.TpEnterprise;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by Administrator on 2017/7/12.
 */
@Mapper
public interface EnterpriseMapper {
    @Select("select * from tp_enterprise where name=#{name}")
    TpEnterprise selectByName(@Param("name") String name);

}
