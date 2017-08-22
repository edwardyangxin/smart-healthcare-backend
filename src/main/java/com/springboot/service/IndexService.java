package com.springboot.service;


import com.springboot.domain.TpFile;
import com.springboot.dto.Register;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/7/12.
 */
public interface IndexService {

    String logout(HttpSession session);

    String insertPerson(Register register, TpFile tpFile, HttpServletRequest request, HttpServletResponse response);

}
