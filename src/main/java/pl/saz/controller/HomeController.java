package pl.saz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by maciej on 01.05.18.
 */
@Controller
public class HomeController {

    @RequestMapping(value="/warehouseApp")
    public String index() {
        return "index.html";
    }

    @RequestMapping(value="/")
    public String index2() {
        return "main.html";
    }

}
