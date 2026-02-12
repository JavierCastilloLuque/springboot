package javiercl.jcomponentes.model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipo_componente database table.
 * 
 */
@Entity
@Table(name="tipo_componente")
@NamedQuery(name="TipoComponente.findAll", query="SELECT t FROM TipoComponente t")
public class TipoComponente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String nombre;

	//bi-directional many-to-one association to Componente
	@OneToMany(mappedBy="tipoComponente")
	private List<Componente> componentes;

	public TipoComponente() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Componente> getComponentes() {
		return this.componentes;
	}

	public void setComponentes(List<Componente> componentes) {
		this.componentes = componentes;
	}

	public Componente addComponente(Componente componente) {
		getComponentes().add(componente);
		componente.setTipoComponente(this);

		return componente;
	}

	public Componente removeComponente(Componente componente) {
		getComponentes().remove(componente);
		componente.setTipoComponente(null);

		return componente;
	}

}