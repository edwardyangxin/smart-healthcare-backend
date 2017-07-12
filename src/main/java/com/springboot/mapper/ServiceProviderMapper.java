package com.springboot.mapper;

import com.springboot.domain.TpPersonal;
import com.springboot.domain.TpServiceProvider;
import org.apache.ibatis.annotations.*;

/**
 * Created by Administrator on 2017/7/12.
 */

@Mapper
public interface ServiceProviderMapper {

    @Insert("insert into tp_service_provider(city, enterprise_id, user_id, name, password)" +
            " values(#{city}, #{enterpriseId}, #{userId}, #{name}, #{password})")
    @SelectKey(statement="SELECT LAST_INSERT_ID()",keyProperty="id",before=false,resultType=Integer.class)
    void insertServiceProvider(TpServiceProvider serviceProvider);

    @Select("select * from tp_service_provider where name=#{name}")
    TpServiceProvider selectByName(@Param("name") String name);
}
