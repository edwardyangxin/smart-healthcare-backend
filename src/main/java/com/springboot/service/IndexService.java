package com.springboot.service;


import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/7/12.
 */
public interface IndexService {

    String logout(HttpSession session);

}
