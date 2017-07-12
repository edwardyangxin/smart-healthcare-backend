package com.springboot.service.impl;


import com.springboot.domain.TpEnterprise;
import com.springboot.domain.TpPersonal;
import com.springboot.domain.TpServiceProvider;
import com.springboot.mapper.EnterpriseMapper;
import com.springboot.mapper.PersonalMapper;
import com.springboot.mapper.ServiceProviderMapper;
import com.springboot.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/7/12.
 */

@Service
public class IndexServiceImpl implements IndexService {

    private PersonalMapper personalMapper;
    private EnterpriseMapper enterpriseMapper;
    private ServiceProviderMapper serviceProviderMapper;

    @Autowired
    public void setPersonalMapper(PersonalMapper personalMapper) {
        this.personalMapper = personalMapper;
    }

    @Autowired
    public void setEnterpriseMapper(EnterpriseMapper enterpriseMapper){
        this.enterpriseMapper = enterpriseMapper;
    }

    @Autowired
    public void setServiceProviderMapper(ServiceProviderMapper serviceProviderMapper){
        this.serviceProviderMapper = serviceProviderMapper;
    }

    @Override
    public void insertPerson(TpPersonal person) {
        personalMapper.insertPerson(person);

    }

    @Override
    public void insertEnterprise(TpEnterprise enterprise){
        enterpriseMapper.insertEnterprise(enterprise);
    }

    @Override
    public void insertServiceProvider(TpServiceProvider serviceProvider){
        serviceProviderMapper.insertServiceProvider(serviceProvider);
    }
}
