package com.springboot.service.impl;


import com.springboot.domain.TpPersonal;
import com.springboot.mapper.PersonalMapper;
import com.springboot.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/7/12.
 */
@Service
public class IndexServiceImpl implements IndexService {

    private PersonalMapper personalMapper;

    @Autowired
    public void setPersonalMapper(PersonalMapper personalMapper) {
        this.personalMapper = personalMapper;
    }

    @Override
    public void insertPerson(TpPersonal person) {
        personalMapper.insertPerson(person);
        return ;
    }
}
