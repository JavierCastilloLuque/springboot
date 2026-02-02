package com.example.demo.repositorio;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelos.Habla;
import com.example.demo.modelos.Idioma;
import com.example.demo.modelos.Usuaria;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

public interface HablaRepositorio extends JpaRepository<Habla, Serializable> {
	@Bean
	public abstract List<Habla> findAll();
	public abstract Habla findById(int id);
	public abstract List<Habla> findByUsuaria(Usuaria u);
	public abstract List<Usuaria> findById(Idioma i);
	
	@Transactional
	public abstract void deleteById(int id);
	
	@Transactional
	public abstract Habla save(Habla h);
}
