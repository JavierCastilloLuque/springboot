package com.example.demo.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins="http://localhost:8080")
@Controller
@RequestMapping("/ruta1")
public class controlador {
	@GetMapping("/obtener")
	public String getIdiomas() {
		return "hola";
	}
}
