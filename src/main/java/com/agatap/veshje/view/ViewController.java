package com.agatap.veshje.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping({"", "/", "/index", "/index.html"})
    public String displayHomepage() {
        return "index";
    }

    @GetMapping({"/login", "/login.html"})
    public String displayLogin() {
        return "login";
    }
}
