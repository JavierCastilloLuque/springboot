package com.example.demo.contraladores;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.demo.modelos.Usuaria;
import com.example.demo.modelos.UsuarioTipo;
import com.example.demo.repositorio.TipoUsuarioRepositorio;
import com.example.demo.repositorio.usuariaRepositorio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping("/usuaria")
@Tag(name="Usuarias", description="La lista de usuarias")
public class UsuariaController {

    private final TipoUsuarioRepositorio tipoUsuarioRepositorio;
	
	
	@Autowired
	usuariaRepositorio usuRep;
	@Autowired
	TipoUsuarioRepositorio tipoUsuRep;

    UsuariaController(TipoUsuarioRepositorio tipoUsuarioRepositorio) {
        this.tipoUsuarioRepositorio = tipoUsuarioRepositorio;
    }
	
	@GetMapping("/obtener")
	@Operation(summary="Obtener todos los usuarios")
	@ApiResponses({
		@ApiResponse(responseCode="200", description="usuarias encontradas"),
		@ApiResponse(responseCode="404", description="usuarias no encontradas")
		})
	public List<DTO> getUsuarios(){
		List<DTO> listaUsariosDTO=new ArrayList<DTO>();
		List<Usuaria> usuarios=usuRep.findAll();
		UsuarioTipo ut;
		for(Usuaria u:usuarios) {
			DTO dtoUsuaria=new DTO();
			dtoUsuaria.put("id", u.getId());
			dtoUsuaria.put("nombre", u.getNombre());
			dtoUsuaria.put("fecha_nac",u.getFechaNac().toString());
			if(u.getFechaElim() !=null)
				dtoUsuaria.put("fecha_elim",u.getFechaElim().toString());
			else 
				dtoUsuaria.put("fecha_elim",new Date(0));
			dtoUsuaria.put("idDeRol", u.getUsuarioTipo().getId());
			dtoUsuaria.put("Rol", u.getUsuarioTipo().getRol());
			listaUsariosDTO.add(dtoUsuaria);	
		}
	return listaUsariosDTO;
	}
	
	@GetMapping(path="/obtener1get")
	public DTO getUsuario2(@RequestParam int id) {
		DTO dtoUsuaria = new DTO();
		Usuaria u = usuRep.findById(id);
		if (u != null) {
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
		return dtoUsuaria;
	}
	
	@DeleteMapping(path="/eliminar1")
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
	@Operation(summary="Añadir una usuaria")
	@ApiResponses({
		@ApiResponse(responseCode="200", description="usuarias encontradas"),
		@ApiResponse(responseCode="404", description="usuarias no encontradas"),
		@ApiResponse(responseCode="500", description="Error")
		})
	@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description="Json con el id de la usuaria",
			required=true,
			content=@Content(
				mediaType="application/json",
				examples=@ExampleObject(value="{"
						+ "\"id\":1,"
						+ "\"fechaElim\":\"2027-11-10\","
						+ "\"fechaNac\":\"2025-08-03\","
						+ "\"img\":\"prueba\","
						+ "\"nombre\":\"prueba\","
						+ "\"username\":\"pruebauser\","
						+ "\"pass\":\"pruebapass\","
						+ "\"rol\":1"
						+ "}"
				)
			)
		)
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
	@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description="Json con el id de la usuaria",
			required=true,
			content=@Content(
				mediaType="application/json",
				examples=@ExampleObject(value="{\"id\":1}")
			)
		)
	public DTO getUsuaurio(@RequestBody DTO soloid, HttpServletRequest request)
	{  
	DTO dtoUsuaria=new DTO();
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
