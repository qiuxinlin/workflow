package com.scxys.activiti.controller.uiController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面controller
 */
@Controller
@RequestMapping("activiti")
public class UiController {

    @RequestMapping("/toDiagram")
    public String toDiagram(String name, String description, Model model) {
        model.addAttribute("name",name);
        model.addAttribute("description",description);
        //modelAndView.setViewName("/businessProcessManager/html/diagram.html");
        return "businessProcessManager/html/diagram";
    }
}
