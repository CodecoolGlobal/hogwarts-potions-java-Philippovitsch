package com.codecool.hogwarts_potions.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {

    @GetMapping
    public String displayIndexPage() {
        return "index";
    }
}
