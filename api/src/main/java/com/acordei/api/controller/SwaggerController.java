package com.acordei.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SwaggerController {
    @RequestMapping(value="/", method= RequestMethod.GET, headers = "Accept=text/html")
    public String docs(){
        return "index.html";
    }
}
