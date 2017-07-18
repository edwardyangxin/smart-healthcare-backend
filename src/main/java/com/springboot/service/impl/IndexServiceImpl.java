package com.springboot.service.impl;


import com.springboot.service.IndexService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/7/12.
 */

@Service
public class IndexServiceImpl implements IndexService {

    @Override
    public String logout(HttpSession session) {
        session.removeAttribute("name");
        return "redirect:/index";
    }

}
