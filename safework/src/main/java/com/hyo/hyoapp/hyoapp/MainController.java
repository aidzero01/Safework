package com.hyo.hyoapp.hyoapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class MainController {
    
    @GetMapping("/")
    public String root() {
        return "redirect:/question/list";
    }
    
}
