package com.example.demo.contraladores;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modelos.Usuaria;
import com.example.demo.modelos.UsuarioTipo;
import com.example.demo.repositorio.TipoUsuarioRepositorio;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/tipousuario")
public class TipoUsuarioController {
	
	@Autowired
	TipoUsuarioRepositorio tipoUsuRep;
	
	@PostMapping(path="/anadir1", consumes= MediaType.APPLICATION_JSON_VALUE)
	public DTO insertUsuario(@RequestBody DTO datos, HttpServletRequest request) {
		DTO respuesta=new DTO();
		
		UsuarioTipo ut = new UsuarioTipo();
		
		ut.setId(Integer.parseInt(datos.get("id").toString()));
		ut.setRol(datos.get("rol").toString());
		
		tipoUsuRep.save(ut);
		tipoUsuRep.flush();
		
		respuesta.put("borrado", "ok");
		
		return respuesta;
	}
	
}
