package es.altair.springhibernate.controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.altair.springhibernate.dao.ViajesDao;



@Controller
@RequestMapping("/myImage")
public class ImageController {
	
	@Autowired
	private ViajesDao viajesDAO;

	@RequestMapping(value = "/imageDisplay", method = RequestMethod.GET)
	public void showImage(@RequestParam("id") String id, HttpServletResponse response, HttpServletRequest request)
			throws ServletException, IOException {

		Blob imagen = viajesDAO.obtenerPortadaPorId(Integer.parseInt(request.getParameter("id")));
		
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		try {
			response.getOutputStream().write(imagen.getBytes(1, (int) imagen.length()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.getOutputStream().close();
	}
}