package com.example.demo.repositorio;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.demo.modelos.Usuaria;

import jakarta.transaction.Transactional;

@Repository
public interface usuariaRepositorio extends JpaRepository<Usuaria, Serializable> {

	@Bean
	public abstract List<Usuaria> findAll();
	public abstract Usuaria findById(int id);
	public abstract Usuaria findByNombre(String userName);
	public abstract Usuaria findByUsernameAndPass(String username, String pass);
	
	@Transactional
	public abstract void delete(Usuaria u);
	@Transactional
	public abstract void deleteById(int id);
	
	@Transactional
	public abstract Usuaria save (Usuaria u);
}
