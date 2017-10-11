package com.nowcoder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
        @RequestMapping(path = {"/", "index"}, method = {RequestMethod.GET})//响应的url,如此语句下,就是响应****/与****/index
    public String index() {
        return "index";
    }
}
