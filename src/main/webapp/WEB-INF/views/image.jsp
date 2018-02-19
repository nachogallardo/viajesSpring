<%@page import="java.io.OutputStream"%>
<%@page import="es.altair.springhibernate.dao.ViajesDaoImp"%>
<%@page import="es.altair.springhibernate.dao.ViajesDao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%><%
try {
	String idViaje = request.getParameter("imag");
	ViajesDao vDAO = new ViajesDaoImp();
	byte[] imagen = vDAO.obtenerPortadaPorId(Integer.parseInt(idViaje));
	try {
		if (imagen != null) {
			response.setContentType("image/jpeg");
			response.setHeader("Content-Disposition", "attachment");
			OutputStream o = response.getOutputStream();
			o.write(imagen);
			o.flush();
			o.close();
			return;
		}
	} catch (IllegalStateException e) {	
	}
} catch (Exception e) {	
}
%>