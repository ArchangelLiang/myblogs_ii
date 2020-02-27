package com.gwg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/total")
public class TotalController {

   /* @Autowired
    private TotalService totalService;*/

    @GetMapping("/toTotalPage")
    public String toTotalPage(){
        return "total";
    }

}
