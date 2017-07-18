package com.springboot.mapper;

import com.springboot.domain.TpServiceProvider;
import com.springboot.dto.Password;
import com.springboot.dto.ServiceProvider;
import org.apache.ibatis.annotations.*;

/**
 * Created by Administrator on 2017/7/12.
 */

@Mapper
public interface ServiceProviderMapper {

    @Insert("insert into tp_service_provider(city, enterprise_id, user_id, name, password)" +
            " values(#{city}, #{enterpriseId}, #{userId}, #{name}, #{password})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    void insertServiceProvider(TpServiceProvider serviceProvider);

    @Select("select * from tp_service_provider where name=#{name}")
    @Results({
            @Result(id = true, column = "description", property = "description"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "enterprise_id", property = "enterpriseId")
    })
    TpServiceProvider selectByName(@Param("name") String name);

    @Update("update tp_service_provider set city = #{city}  where name =#{name}")
    void updateServiceProviderByName(ServiceProvider serviceProvider);

    @Update("update tp_service_provider set password = #{password} where name = #{name}")
    void updateServiceProviderPassByName(Password password);


}
