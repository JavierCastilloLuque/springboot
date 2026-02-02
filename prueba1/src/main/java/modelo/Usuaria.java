package modelo;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the usuaria database table.
 * 
 */
@Entity
@NamedQuery(name="Usuaria.findAll", query="SELECT u FROM Usuaria u")
public class Usuaria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_elim")
	private Date fechaElim;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_nac")
	private Date fechaNac;

	@Lob
	private byte[] img;

	private String nombre;

	private String pass;

	private String username;

	//bi-directional many-to-one association to Habla
	@OneToMany(mappedBy="usuaria")
	private List<Habla> hablas;

	//bi-directional many-to-one association to UsuarioTipo
	@ManyToOne
	@JoinColumn(name="id_tipo_usuario")
	private UsuarioTipo usuarioTipo;

	public Usuaria() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFechaElim() {
		return this.fechaElim;
	}

	public void setFechaElim(Date fechaElim) {
		this.fechaElim = fechaElim;
	}

	public Date getFechaNac() {
		return this.fechaNac;
	}

	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}

	public byte[] getImg() {
		return this.img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPass() {
		return this.pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Habla> getHablas() {
		return this.hablas;
	}

	public void setHablas(List<Habla> hablas) {
		this.hablas = hablas;
	}

	public Habla addHabla(Habla habla) {
		getHablas().add(habla);
		habla.setUsuaria(this);

		return habla;
	}

	public Habla removeHabla(Habla habla) {
		getHablas().remove(habla);
		habla.setUsuaria(null);

		return habla;
	}

	public UsuarioTipo getUsuarioTipo() {
		return this.usuarioTipo;
	}

	public void setUsuarioTipo(UsuarioTipo usuarioTipo) {
		this.usuarioTipo = usuarioTipo;
	}

}