package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SampleContoroller {

	@GetMapping("/sample")
    public String getSamplePage(Model model) {
        return "sample";
    }

	@GetMapping("/rink")
    public String getRink(Model model) {
        return "rink";
    }
}