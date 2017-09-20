package com.scxys.activiti.controller.uiController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 页面controller
 */
@Controller
@RequestMapping(value = "activiti")
public class UiController {

    @RequestMapping(value = "/toDiagram")
    public ModelAndView toDiagram(String name) {
        System.out.println(name);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("name",name);
        modelAndView.setViewName("businessProcessManager/html/diagram");
        return modelAndView;
    }
}
