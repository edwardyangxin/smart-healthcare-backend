package com.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;



/**
 * Created by Administrator on 2017/7/11.
 */
@Controller
public class IndexController {

    @Autowired
    private Producer kaptchaProducer;
    /**
     *获取验证码图片
     *
     */
    @RequestMapping(value="/getKaptchaImage",method= RequestMethod.GET)
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
    @RequestMapping(value="https://my.oschina.net/checkKaptcha",method=RequestMethod.GET)
    public void checkKaptcha(String clientCode,HttpServletRequest request, HttpServletResponse response) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        HttpSession session = request.getSession();
        String serverCode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
//        if(clientCode != null && clientCode.equalsIgnoreCase(serverCode)){
//            mapper.writeValue(response.getOutputStream(), new ReturnData(0));
//        }else{
//            mapper.writeValue(response.getOutputStream(), new ReturnData(2004));
//        }

    }
}

