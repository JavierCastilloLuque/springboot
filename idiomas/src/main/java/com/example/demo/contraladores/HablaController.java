package com.example.demo.contraladores;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.autoconfigure.JacksonProperties.Datatype;
import org.springframework.boot.web.server.Cookie;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.jwtSecurity.AutenticadorJWT;
import com.example.demo.modelos.Habla;
import com.example.demo.modelos.Usuaria;
import com.example.demo.modelos.UsuarioTipo;
import com.example.demo.repositorio.HablaRepositorio;
import com.example.demo.repositorio.TipoUsuarioRepositorio;
import com.example.demo.repositorio.usuariaRepositorio;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping("/habla")
public class HablaController {

	
    private final usuariaRepositorio usuariaRepositorio;
	
	
	@Autowired
	HablaRepositorio hablaRep;
	@Autowired
	usuariaRepositorio usuRep;
	
    HablaController(usuariaRepositorio usuariaRepositorio) {
        this.usuariaRepositorio = usuariaRepositorio;
    }
	
	@GetMapping("/all")
	public List<DTO> findAll(){
		List<DTO> listaHablaDTO = new ArrayList<DTO>();
		List<Habla> hablas = hablaRep.findAll();
		for(Habla h : hablas) {
			DTO dtoHabla = new DTO();
			dtoHabla.put("id", h.getId());
			dtoHabla.put("usuario", h.getUsuaria());
			dtoHabla.put("idioma",h.getIdioma());
			listaHablaDTO.add(dtoHabla);
		}
	return listaHablaDTO;
	}
	
	@GetMapping(path="/")
	public DTO findById(@RequestParam int id) {
		DTO dtoHabla = new DTO();
		Habla h = hablaRep.findById(id);
		if (h != null) {
			dtoHabla.put("id", h.getId());
			dtoHabla.put("usuario", h.getUsuaria());
			dtoHabla.put("idioma",h.getIdioma());
		}
		else dtoHabla.put("result","fail");
		return dtoHabla;
	}
	
	@GetMapping("/usuaria")
	public List<DTO> findByUsuaria(Usuaria u){
		List<DTO> listaHablaDTO = new ArrayList<DTO>();
		List<Habla> hablas = hablaRep.findByUsuaria(u);
		for(Habla h : hablas) {
			DTO dtoHabla = new DTO();
			dtoHabla.put("id", h.getId());
			dtoHabla.put("usuario", h.getUsuaria());
			dtoHabla.put("idioma",h.getIdioma());
			listaHablaDTO.add(dtoHabla);
		}
	return listaHablaDTO;
	}
	
	@GetMapping("/quehablaautenticado")
	public List<DTO> getIdiomasAutenticado(HttpServletRequest request){
		List<DTO> listaHablaDTO = new ArrayList<DTO>();
		
		jakarta.servlet.http.Cookie[] c = request.getCookies();
		int idUsuarioAutenticado=-1;
		for( jakarta.servlet.http.Cookie co:c) {
			if(co.getName().equals("jwt"))
				idUsuarioAutenticado=AutenticadorJWT.getIdUsuarioDesdeJWT(co.getValue());
		}
		Usuaria u=usuariaRepositorio.findById(idUsuarioAutenticado);
		
		if (u != null) {
			List<Habla> hablas = hablaRep.findByUsuaria(u);
			for(Habla h : hablas) {
				DTO dtoHabla = new DTO();
				dtoHabla.put("id", h.getId());
				dtoHabla.put("usuario", h.getUsuaria());
				dtoHabla.put("idioma",h.getIdioma());
				listaHablaDTO.add(dtoHabla);
			}
		}
		

	return listaHablaDTO;
	}
	
	
	/*
	@DeleteMapping(path="/habla/eliminar")
	public DTO deleteUsuario(@RequestBody DTO soloid, HttpServletRequest request) {
		DTO respuesta=new DTO();
		Usuaria u=usuRep.findById(Integer.parseInt(soloid.get("id").toString()));
		 if (u!=null) {
			 usuRep.delete(u);
			 respuesta.put("borrado", "ok");
		 }
		 else respuesta.put("borrado","fail");
		 return respuesta;
	}
	
	@PostMapping(path="/anadir1", consumes= MediaType.APPLICATION_JSON_VALUE)
	public void insertUsuario(@RequestBody DatosAltaUsuario datos, HttpServletRequest request) {
		Usuaria u = new Usuaria();
		
		u.setId(datos.id);
		u.setFechaElim(datos.fechaElim);
		u.setFechaNac(datos.fechaNac);
		u.setImg(DatatypeConverter.parseBase64Binary(datos.img));
		u.setNombre(datos.nombre);
		u.setPass(datos.pass);
		u.setUsername(datos.username);
		u.setUsuarioTipo(tipoUsuarioRepositorio.findById(datos.rol));
		
		usuRep.save(u);
	}
	
	@PostMapping(path="/obtener1", consumes= MediaType.APPLICATION_JSON_VALUE)
	public DTO getUsuaurio(@RequestBody DTO soloid, HttpServletRequest request)
	{  DTO dtoUsuaria=new DTO();
	Usuaria u=usuRep.findById(Integer.parseInt(soloid.get("id").toString()));
	 if (u!=null) {
			dtoUsuaria.put("id", u.getId());
			dtoUsuaria.put("nombre", u.getNombre());
			dtoUsuaria.put("fecha_nac",u.getFechaNac().toString());
			if(u.getFechaElim() !=null)
				dtoUsuaria.put("fecha_elim",u.getFechaElim().toString());
			else 
				dtoUsuaria.put("fecha_elim",new Date(0));
			dtoUsuaria.put("idDeRol", u.getUsuarioTipo().getId());
			dtoUsuaria.put("Rol", u.getUsuarioTipo().getRol());	 
	 }
	 else dtoUsuaria.put("result","fail");
	return dtoUsuaria;}
	
	@PostMapping(path="/autentica", consumes= MediaType.APPLICATION_JSON_VALUE)
	public DTO autenticaUsuario(@RequestBody DatosAutenticarUsuario datos, HttpServletRequest request, HttpServletResponse response) {
		DTO dto = new DTO();
		dto.put("result", "fail");
		Usuaria usuarioAutenticado = usuRep.findByUsernameAndPass(datos.username, datos.pass);
		if (usuarioAutenticado != null ) {
			dto.put("result", "ok");
			dto.put("jwt", AutenticadorJWT.codificaJWT(usuarioAutenticado));
			jakarta.servlet.http.Cookie cookie = new jakarta.servlet.http.Cookie("jwt", AutenticadorJWT.codificaJWT(usuarioAutenticado));
			cookie.setMaxAge(-1);
			response.addCookie(cookie);
		}
		
		return dto;
	}
	
	@GetMapping(path="/quieneres")
	public DTO getAutenticado(HttpServletRequest request) {
		DTO dtoUsuario=new DTO();
		dtoUsuario.put("result", "fail");
		jakarta.servlet.http.Cookie[] c=request.getCookies();
		int idUsuarioAutenticado=-1;
		for( jakarta.servlet.http.Cookie co:c) {
			if(co.getName().equals("jwt"))
				idUsuarioAutenticado=AutenticadorJWT.getIdUsuarioDesdeJWT(co.getValue());
		}
		Usuaria u=usuRep.findById(idUsuarioAutenticado);
		if (u!=null) {
			dtoUsuario.put("id", u.getId());
			dtoUsuario.put("nombre", u.getNombre());
			dtoUsuario.put("fecha_nac",u.getFechaNac().toString());
			if(u.getFechaElim() !=null)
				dtoUsuario.put("fecha_elim",u.getFechaElim().toString());
			else 
				dtoUsuario.put("fecha_elim",new Date(0));
			dtoUsuario.put("idDeRol", u.getUsuarioTipo().getId());
			dtoUsuario.put("Rol", u.getUsuarioTipo().getRol());	 
		}
		return dtoUsuario;
	}
	*/
	
	static class DatosAutenticarUsuario{
		String username;
		String pass;
		
		public DatosAutenticarUsuario(String username, String pass) {
			super();
			this.username = username;
			this.pass = pass;
		}
	}
	
	static class DatosAltaUsuario {
		int id;
		Date fechaElim;
		Date fechaNac;
		String img;
		String nombre;
		String username;
		String pass;
		int rol;

		public DatosAltaUsuario(int id, Date fechaElim, Date fechaNac, String img, String nombre, String username,
				String pass, int rol) {
			this.id = id;
			this.fechaElim = fechaElim;
			this.fechaNac = fechaNac;
			this.img = img;
			this.nombre = nombre;
			this.username = username;
			this.pass = pass;
			this.rol = rol;
		}

	}
}
