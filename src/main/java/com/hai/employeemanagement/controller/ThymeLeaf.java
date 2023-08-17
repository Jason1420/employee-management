package com.hai.employeemanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class ThymeLeaf {
    @RequestMapping("/{name}")
    public String hello(@PathVariable("name") String name, Model model){
        model.addAttribute("message",name);
        return "index";
    }

    @RequestMapping("/")
    public String csstest( Model model){
        return "add-css-and-js-demo";
    }
}
