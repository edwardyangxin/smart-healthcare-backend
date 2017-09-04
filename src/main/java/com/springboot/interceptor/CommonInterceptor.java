//package com.springboot.interceptor;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.springboot.tools.ResultUtil;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//public class CommonInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//        System.out.println("CommonInterceptor >> 在请求处理之前进行调用（Controller方法调用之前）,判断个人用户是否登陆");
//        String personName = (String)httpServletRequest.getSession().getAttribute("personName");
//        String  uuid = (String)httpServletRequest.getSession().getAttribute("personUuid");
//        if (null == personName||null==uuid){
//            httpServletResponse.setHeader("Content-type", "text/html;charset=UTF-8");
//            httpServletResponse.setCharacterEncoding("UTF-8");
//            httpServletResponse.setContentType("application/json");
//            ObjectMapper mapper = new ObjectMapper();
//            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            httpServletResponse.getWriter().write(mapper.writeValueAsString(ResultUtil.error("个人用户未登录，请先登录！")));
//            return false;
//        }
//        return true;// 只有返回true才会继续向下执行，返回false取消当前请求
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//
//    }
//}
