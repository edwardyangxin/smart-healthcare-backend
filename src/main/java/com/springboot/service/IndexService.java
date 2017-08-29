package com.springboot.service;


import com.springboot.domain.Result;
import com.springboot.dto.Register;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/7/12.
 */
public interface IndexService {

    String logout(HttpSession session);

    Result insertUser(Register register, HttpServletRequest request, HttpServletResponse response);

   // Result login(Login login,HttpServletRequest request);

}
