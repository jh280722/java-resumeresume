package com.rere;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class Controller {
    @GetMapping(value = "/")
    public ModelAndView indexView(ModelAndView indexView) {
        indexView.setViewName("index");
        return indexView;
    }
}
