package com.springboot.controller;

import com.springboot.domain.TpEnterprise;
import com.springboot.domain.TpPersonal;
import com.springboot.domain.TpServiceProvider;
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
    @RequestMapping(value = "/registePerson", method = RequestMethod.POST)
    public String insertPerson(TpPersonal person) {
        indexService.insertPerson(person);
        return "执行完毕！";
    }

    @ResponseBody
    @RequestMapping(value = "/registeEnterprise", method = RequestMethod.POST)
    public String insertEnterprise(TpEnterprise enterprise) {
        indexService.insertEnterprise(enterprise);
        return "执行完毕！";
    }

    @ResponseBody
    @RequestMapping(value = "/registeServiceProvider", method = RequestMethod.POST)
    public String insertServiceProvider(TpServiceProvider serviceProvider) {
        indexService.insertServiceProvider(serviceProvider);
        return "执行完毕！";
    }
}
