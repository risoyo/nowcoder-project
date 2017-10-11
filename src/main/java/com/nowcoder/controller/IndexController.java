package com.nowcoder.controller;

import com.nowcoder.aspect.LogAspect;
import com.nowcoder.model.User;
import com.nowcoder.service.WendaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by nowcoder on 2016/7/10.
 */
//@Controller
public class IndexController {
//    @RequestMapping(path = {"/", "index"}, method = {RequestMethod.GET})//响应的url,如此语句下,就是响应****/与****/index
//    @ResponseBody //定义操作函数主体,有这一句表示返回值是个文本,不是一个模板
//    public String index(HttpSession httpSession) {
//        return "hello world" + httpSession.getAttribute("msg");
//    }
    @RequestMapping(path = {"/", "index"}, method = {RequestMethod.GET})//响应的url,如此语句下,就是响应****/与****/index
    public String index() {
        return "index";
    }

    @RequestMapping(path = {"/profile/{group}/{userId}"}, method = {RequestMethod.GET})
//响应所有如****/profile/112类的,112为userId
    @ResponseBody
    public String profile(@PathVariable("userId") int uesrId,//将userId捕获为int类型的变量userId
                          @PathVariable("group") String group,
                          @RequestParam(value = "type", required = false) String type,//解析参数,****?type=2这种,捕获参数的值,加入required = false就变成非必须的
                          @RequestParam(value = "key", defaultValue = "1") int key) //设置默认值,注意默认值类型
    {
        return String.format("hello %s,%d,%s,%d", group, uesrId, type, key);
    }


    @RequestMapping(path = {"/vm"}, method = {RequestMethod.GET})
    public String template(Model model) {//Model是Spring框架中的参数变量
        model.addAttribute("value1", "valuevalue");//基础变量
        List<String> color = Arrays.asList("RED", "BLUE");//list变量
        model.addAttribute("color", color);
        return "home";
    }

    @RequestMapping(path = {"/request"}, method = {RequestMethod.GET})
    @ResponseBody
    public String request(Model model,
                          HttpServletResponse response,
                          HttpServletRequest request,
                          HttpSession httpSession,
                          @CookieValue("JSESSIONID") String sessionId) {
        StringBuilder sb = new StringBuilder();
        sb.append("COOKIEVALUE:" + sessionId);
        sb.append(request.getMethod() + "<br>");//调用的方法
        sb.append(request.getQueryString() + "<br>");//参数,如type=2
        sb.append(request.getRequestURI() + "<br>");
        sb.append(request.getPathInfo() + "<br>");//请求的URL
        response.addHeader("nowcoder", "hello");//向response中添加一些信息
        response.addCookie(new Cookie("username", "nowcoder"));//较为重要,下发cookie
        return sb.toString();
    }

    @RequestMapping(path = {"/admin"}, method = {RequestMethod.GET})//响应的url,如此语句下,就是响应****/与****/index
    @ResponseBody //定义操作函数主体,有这一句表示返回值是个文本,不是一个模板
    public String admin(@RequestParam("key") String key) {
        if("admin".equals(key)){
            return "hello admin";
        }
        throw new IllegalArgumentException("参数异常");
    }

    @ExceptionHandler()//异常处理模块
    @ResponseBody
    public String error(Exception e){
        return "error:" + e.getMessage();
    }

    @RequestMapping(path = {"/redirect/{code}"}, method = {RequestMethod.GET})
    public String redirect(@PathVariable("code") int code,
                           HttpSession httpSession) {
        httpSession.setAttribute("msg","jump from redirect,code: "+code);
        return "redirect:/";
    }






}
