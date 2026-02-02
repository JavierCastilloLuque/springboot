package com.example.demo.repositorio;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelos.Usuaria;
import com.example.demo.modelos.UsuarioTipo;

import jakarta.transaction.Transactional;

public interface TipoUsuarioRepositorio extends JpaRepository<UsuarioTipo, Serializable> {
	@Bean
	public abstract List<UsuarioTipo> findAll();
	public abstract UsuarioTipo findById(int id);
	
	@Transactional
	public abstract void deleteById(int id);
	
	@Transactional
	public abstract UsuarioTipo save(UsuarioTipo u);
}
