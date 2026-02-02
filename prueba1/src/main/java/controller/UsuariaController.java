package controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import modelo.Usuaria;
import modelo.UsuarioTipo;
import repositorio.usuariaRepositorio;

@CrossOrigin
@RestController
@RequestMapping("/usuaria")
public class UsuariaController {
	@Autowired
	usuariaRepositorio usuRep;
	@GetMapping("/obtener")
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
}
