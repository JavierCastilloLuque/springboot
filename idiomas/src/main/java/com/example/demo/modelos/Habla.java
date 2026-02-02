package com.example.demo.modelos;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the habla database table.
 * 
 */
@Entity
@NamedQuery(name="Habla.findAll", query="SELECT h FROM Habla h")
public class Habla implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	//bi-directional many-to-one association to Usuaria
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuaria usuaria;

	//bi-directional many-to-one association to Idioma
	@ManyToOne
	@JoinColumn(name="id_idioma")
	private Idioma idioma;

	public Habla() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Usuaria getUsuaria() {
		return this.usuaria;
	}

	public void setUsuaria(Usuaria usuaria) {
		this.usuaria = usuaria;
	}

	public Idioma getIdioma() {
		return this.idioma;
	}

	public void setIdioma(Idioma idioma) {
		this.idioma = idioma;
	}

}