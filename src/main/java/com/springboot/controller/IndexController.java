package com.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.springboot.domain.TpFile;
import com.springboot.dto.Register;
import com.springboot.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.util.List;


/**
 * Created by Administrator on 2017/7/11.
 */
@RequestMapping(value = "translate")
@Controller
public class IndexController {

    private Producer kaptchaProducer;
    private IndexService indexService;

    @Autowired
    public IndexController(Producer kaptchaProducer, IndexService indexService) {
        this.kaptchaProducer = kaptchaProducer;
        this.indexService = indexService;
    }

    @ResponseBody
    @RequestMapping(value = "/index")
    public String index() {
        return "主页";
    }

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String insertPerson(@Valid @RequestBody Register register, TpFile tpFile, HttpServletRequest request, HttpServletResponse response, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                return error.getDefaultMessage();
            }
        }
        return indexService.insertPerson(register, tpFile, request, response);
    }

    @RequestMapping(value = "/logout")
    public String serviceProviderLogout(HttpSession session) {
        return indexService.logout(session);
    }

    /**
     * 获取验证码图片
     */
    @RequestMapping(value = "/getKaptchaImage", method = RequestMethod.GET)
    public ModelAndView getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        response.setDateHeader("Expires", 0);

        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");

        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");

        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");

        // return a jpeg
        response.setContentType("image/jpeg");

        // create the text for the image
        String capText = kaptchaProducer.createText();

        // store the text in the session
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);

        // create the image with the text
        BufferedImage bi = kaptchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();

        // write the data out
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
        return null;
    }

    /**
     * 检验图片验证码
     */
    @ResponseBody
    @RequestMapping(value = "/checkKaptcha", method = RequestMethod.GET)
    public String checkKaptcha(String clientCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        HttpSession session = request.getSession();
        String serverCode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        if (clientCode != null && clientCode.equalsIgnoreCase(serverCode)) {
            return "success";
        } else {
            return "Kaptcha_error";
        }

    }
}

