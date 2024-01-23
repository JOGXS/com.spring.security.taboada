package com.taboada.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
//@RestController
//@RequestMapping("/formularios")
public class HomeController {

	@GetMapping("/")
    public String mostrarController() {
        return "login"; // Corrección aquí
    }
	
    @GetMapping("/dashboard")
    public String mostrarDashboard() {
        return "dashboard"; // Corrección aquí
    }
/*
    @RequestMapping(value = "/formLogin", method = RequestMethod.GET)
    public String mostrarLogin() {
        return "formLogin";
    }*/
}
