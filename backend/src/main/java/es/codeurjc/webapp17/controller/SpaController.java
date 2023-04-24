package es.codeurjc.webapp17.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpaController {
    @RequestMapping(value = "/new/{path:[^\\.]*}")
    public String redirect() {
        return "forward:/new/index.html";
    }

    @RequestMapping(value = "/assets/{file}")
    public String redirectAssets(@PathVariable String file) {
        return "forward:/new/assets/"+file;
    }

    @RequestMapping(value = "/new/admin/{path:[^\\.]*}")
    public String redirectadmin() {
        return "forward:/new/index.html";
    }

    @RequestMapping(value = "/new/")
    public String redirectBase() {
        return "forward:/new/index.html";
    }

    @RequestMapping(value = "/new")
    public String redirectBaseMin() {
        return "forward:/new/index.html";
    }
}
