package es.altair.springhibernate.dao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.util.List;

import org.hibernate.HibernateException;



import es.altair.springhibernate.bean.Viajes;

public interface ViajesDao {
	public List<Viajes> listarTodosViajes();
	Blob obtenerPortadaPorId(int idViaje);
	public boolean AñadirBilletes(int idUsuario, int idViaje);
	public void borrarViaje(int idViaje);
	List<Viajes> listarViajesUsuario(int id);
	public void cancelarViaje(int idViaje, int idUsuario);
	Blob obtenerBlobFromFile(byte[] bs) throws HibernateException, IOException;
	public void agregarViaje(Viajes viaje);
}
