package javiercl.jcomponentes.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javiercl.jcomponentes.model.Marca;

import jakarta.transaction.Transactional;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Serializable> {

	@Bean
	public abstract List<Marca> findAll();
	public abstract Marca findById(int id);
	public abstract Marca findByNombre(String userName);
	
	@Transactional
	public abstract void delete(Marca m);
	@Transactional
	public abstract void deleteById(int id);
	
	@Transactional
	public abstract Marca save (Marca m);
}
