package es.altair.springhibernate.dao;

import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import es.altair.springhibernate.bean.Usuarios;
import es.altair.springhibernate.bean.Viajes;


import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;


@Repository
public class ViajesDaoImp implements ViajesDao{

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@Override
	@Transactional
	public List<Viajes> listarTodosViajes() {
		List<Viajes>viajes=new ArrayList<Viajes>();
		Session sesion=sessionFactory.getCurrentSession();
		viajes = sesion.createQuery("FROM Viajes").list();
		return viajes;
	}

	@Override
	@Transactional
	public Blob obtenerPortadaPorId(int idViaje) {
		Blob image = null;
		Session session = sessionFactory.getCurrentSession();
		image = (Blob) session.createQuery("SELECT v.portada FROM Viajes v WHERE v.idViaje=:id")
				.setParameter("id", idViaje).uniqueResult();
		return image;
	}

	@Transactional
	@Override
	public java.sql.Blob obtenerBlobFromFile(byte[] bs) throws HibernateException, IOException {
		return Hibernate.getLobCreator(sessionFactory.getCurrentSession()).createBlob(bs);
	}
	
	@Override
	@Transactional
	public boolean AñadirBilletes(int idUsuario, int idViaje) {
		boolean comprado=true;
		Session sesion=sessionFactory.getCurrentSession();
		Usuarios usu=new Usuarios();
		Viajes viaje=new Viajes();
		
		int hayBillete=0;
		try {

			hayBillete=(Integer) sesion.createSQLQuery("select idViaje from billetes where idViaje=:idViaje and idUsuario=:idUsuario").setParameter("idViaje", idViaje).setParameter("idUsuario", idUsuario).uniqueResult();

		} catch (Exception e) {

		} finally {
			
		}
		if(hayBillete>0)
			comprado=false;
		
		try {
			usu=(Usuarios)sesion.createQuery("from Usuarios  where idUsuario=:id").setParameter("id", idUsuario).uniqueResult();
			viaje=(Viajes)sesion.createQuery("from Viajes  where idViaje=:id").setParameter("id", idViaje).uniqueResult();
		} catch (Exception e) {

		} finally {
		}
		
		viaje.getUsuarios().add(usu);
		usu.getViajes().add(viaje);


		sesion.persist(viaje);

		
		return comprado;
	}


	@Override
	@Transactional
	public void borrarViaje(int idViaje) {
		Session sesion=sessionFactory.getCurrentSession();
		sesion.createQuery("delete from Viajes where idViaje=:i").setParameter("i", idViaje).executeUpdate();	
	}

	@Override
	@Transactional
	public List<Viajes> listarViajesUsuario(int id) {
		List<Viajes>viajes=new ArrayList<Viajes>();
		Session sesion=sessionFactory.getCurrentSession();
	
		List<Integer> idViajes=sesion.createSQLQuery("select distinct idViaje from billetes where idUsuario=:i").setParameter("i", id).list();
		for (Integer integer : idViajes) {
			System.out.println(integer);
			viajes.add((Viajes) sesion.createQuery("FROM Viajes where idViaje=:id").setParameter("id", integer).uniqueResult());
		}
		return viajes;
	}

	@Override
	@Transactional
	public void cancelarViaje(int idViaje, int idUsuario) {
		Session sesion=sessionFactory.getCurrentSession();
		Usuarios usu=new Usuarios();
		Viajes viaje=new Viajes();

		usu=(Usuarios)sesion.createQuery("from Usuarios  where idUsuario=:id").setParameter("id", idUsuario).uniqueResult();
		viaje=(Viajes)sesion.createQuery("from Viajes  where idViaje=:id").setParameter("id", idViaje).uniqueResult();

		viaje.getUsuarios().remove(usu);
		usu.getViajes().remove(viaje);
		sesion.persist(viaje);
				
	}
	@Override
	@Transactional
	public void agregarViaje(Viajes viaje) {
		Session sesion=sessionFactory.getCurrentSession();
		sesion.persist(viaje);
	}

}
