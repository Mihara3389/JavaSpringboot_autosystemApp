package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TopContoroller {

	@GetMapping("/top")
    public String getTopPage(Model model) {
        return "top";
    }

	@GetMapping("/rink")
    public String getRink(Model model) {
        return "rink";
    }
}