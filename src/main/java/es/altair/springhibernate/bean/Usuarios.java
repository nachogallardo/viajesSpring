package es.altair.springhibernate.bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
@Entity
@Table(name="usuarios")
public class Usuarios implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idUsuario;
	private String nombre;
	private String contrasenia;
	private String email;
	private int telefono;
	private String direccion;
	private int tipoUsuario;
	
	@ManyToMany(mappedBy="usuarios")
	private Set<Viajes> viajes = new HashSet<Viajes>();
	
	public Usuarios() {
		super();
	}
	
	
	public Usuarios(String nombre, String contrasenia, String email, int telefono, String direccion, int tipoUsuario) {
		super();
		this.nombre = nombre;
		this.contrasenia = contrasenia;
		this.email = email;
		this.telefono = telefono;
		this.direccion = direccion;
		this.tipoUsuario = tipoUsuario;
	}







	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getContrasenia() {
		return contrasenia;
	}
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getTelefono() {
		return telefono;
	}
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public int getTipoUsuario() {
		return tipoUsuario;
	}
	public void setTipoUsuario(int tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	

	public Set<Viajes> getViajes() {
		return viajes;
	}


	public void setViajes(Set<Viajes> viajes) {
		this.viajes = viajes;
	}


	@Override
	public String toString() {
		return "Usuarios [idUsuario=" + idUsuario + ", nombre=" + nombre + ", contrasenia=" + contrasenia + ", email="
				+ email + ", telefono=" + telefono + ", direccion=" + direccion + ", tipoUsuario=" + tipoUsuario + "]";
	}
	
	
	
}
