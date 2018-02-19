package es.altair.springhibernate.dao;

import java.util.List;

import es.altair.springhibernate.bean.Usuarios;

public interface UsuariosDao {

	boolean validarUsuario(Usuarios usu);

	int insertar(Usuarios usu);

	Usuarios comprobarUsuario(String usuario, String password);

	
	List<Usuarios> listarUsuarios();

	void borrarUsuario(int idUsuario);
	Usuarios usuarioPorId(int idUsuario);

	Usuarios usuarioLoginId(int idUsuario);

	void Editar(int idUsuario, String nombre, String email, int telefono, String direccion, int tipo);
}
