package com.springboot.controller;

import com.springboot.domain.TpPersonal;
import com.springboot.mapper.EnterpriseMapper;
import com.springboot.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/7/11.
 */
@Controller
public class IndexController {


    @Autowired
    public IndexService indexService;

    @Autowired
    public void setIndexService(IndexService indexService) {
        this.indexService = indexService;
    }

    @ResponseBody
    @RequestMapping(value = "/insertPerson", method = RequestMethod.POST)
    public String insertPerson(TpPersonal person) {

        TpPersonal person1 = new TpPersonal();
        person1.setUserName("张三");
        person1.setEmail("1234566@qq.com");
        person1.setPassword("123123");
        person1.setRealName("李四");
        person1.setTel("15866591655");
        indexService.insertPerson(person1);

        return "执行完毕！";
    }

}
