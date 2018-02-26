package es.altair.springhibernate.controller;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;

import javax.imageio.ImageIO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import es.altair.springhibernate.bean.Usuarios;
import es.altair.springhibernate.bean.Viajes;
import es.altair.springhibernate.dao.UsuariosDao;
import es.altair.springhibernate.dao.ViajesDao;


@Controller
public class ViajesController {
	@Autowired
	private ViajesDao viajesDao;
	@Autowired
	private UsuariosDao usuariosDao;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public ModelAndView inicio(@RequestParam(value="fallo",required=false,defaultValue="") String fallo,@RequestParam(value="mensaje",required=false,defaultValue="") String mensaje,Model model) {
		model.addAttribute("mensaje",mensaje);
		model.addAttribute("fallo",fallo);
		model.addAttribute("listaViajes",viajesDao.listarTodosViajes());			
		return new ModelAndView("index","usuario",new Usuarios());
	}
	
	
	@RequestMapping(value="/borrarViaje",method=RequestMethod.GET)
	public String borrarViajes(Model model,HttpSession sesion) {
		if(sesion.getAttribute("usuLogeado")==null) {
			return "redirect:/";
		}
		model.addAttribute("usuLogeado",sesion.getAttribute("usuLogeado"));
		model.addAttribute("listaViajes",viajesDao.listarTodosViajes());			
		return "borrarViajes";
	}
	
	@RequestMapping(value="/BorrarViaje",method=RequestMethod.GET)
	public String BorrarViajes(Model model,HttpServletResponse response, HttpServletRequest request,HttpSession sesion) {
		if(sesion.getAttribute("usuLogeado")==null) {
			return "redirect:/";
		}
		model.addAttribute("usuLogeado",sesion.getAttribute("usuLogeado"));
		int id=Integer.parseInt(request.getParameter("idViaje"));	
		viajesDao.borrarViaje(id);
		return "redirect:/borrarViaje";
	}
	
	
	@RequestMapping(value="/agregarViaje",method=RequestMethod.GET)
	public String agregarOtroViaje(@RequestParam(value="info",required=false,defaultValue="") String info,Model model,HttpSession sesion) {
		
		if(sesion.getAttribute("usuLogeado")==null) {
			return "redirect:/";
		}
		model.addAttribute("info",info);
		
		model.addAttribute("usuLogeado",sesion.getAttribute("usuLogeado"));
		model.addAttribute("viaje",new Viajes());
		return"agregarViajes";
	}
	
	
	@RequestMapping(value="/agregarNuevoV", method=RequestMethod.POST)
	public String agregarNuevoV(@ModelAttribute Viajes v,Model model,@RequestParam("file") MultipartFile file,HttpSession sesion) {
		if(sesion.getAttribute("usuLogeado")==null) {
			return "redirect:/";
		}
		model.addAttribute("usuLogeado",sesion.getAttribute("usuLogeado"));
		Blob blob;
		try {
			BufferedImage imageBuffer = ImageIO.read(file.getInputStream());
			Image tmp = imageBuffer.getScaledInstance(640, 640, BufferedImage.SCALE_FAST);
			BufferedImage buffered = new BufferedImage(640, 640, BufferedImage.TYPE_INT_RGB);
			buffered.getGraphics().drawImage(tmp, 0, 0, null);

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(buffered, "jpg", os);

			blob = viajesDao.obtenerBlobFromFile(os.toByteArray());
			v.setPortada(blob);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		viajesDao.agregarViaje(v);
		
		return "redirect:/agregarViaje?info=Viaje agregado";
	
	}
	
	
	@RequestMapping(value="/editarOtroUsuario",method=RequestMethod.GET)
	public ModelAndView editarOtroUsuario(@RequestParam("idUsuario") String idUsuario,Model model, HttpServletResponse response, HttpServletRequest request,HttpSession sesion) {
		if(sesion.getAttribute("usuLogeado")==null) {
			return new ModelAndView("index","usuario",new Usuarios());
		}
		model.addAttribute("usuLogeado",sesion.getAttribute("usuLogeado"));
		int id=Integer.parseInt(request.getParameter("idUsuario"));		
		Usuarios usu=usuariosDao.usuarioPorId(id);
		return new ModelAndView("editarOtroPerfil","usuario",usu);
	}
	
	@RequestMapping(value="/editarOtroPerfil",method= RequestMethod.POST)
	public String editaOtroPerfil(@ModelAttribute Usuarios usuario,Model model,HttpSession sesion) {
		if(sesion.getAttribute("usuLogeado")==null) {
			return "redirect:/";
		}
		model.addAttribute("usuLogeado",sesion.getAttribute("usuLogeado"));
		usuariosDao.Editar(usuario.getIdUsuario(),usuario.getNombre(),usuario.getEmail(),usuario.getTelefono(),usuario.getDireccion(),usuario.getTipoUsuario());
		if (!usuariosDao.validarUsuario(usuario)) {
			
		    return "redirect:/administrador";
		}else {
		  return "redirect:/administrador";
		}
	}
	
	
	@RequestMapping(value="/borrarUsuario",method=RequestMethod.GET)
	public String borrarUsuario(@RequestParam("idUsuario") String idUsuario, Model model,HttpServletResponse response, HttpServletRequest request,HttpSession sesion) {
		if(sesion.getAttribute("usuLogeado")==null) {
			return "redirect:/";
		}
		model.addAttribute("usuLogeado",sesion.getAttribute("usuLogeado"));
		int id=Integer.parseInt(request.getParameter("idUsuario"));		
		
		usuariosDao.borrarUsuario(id);
		return "redirect:/administrador";
	}
	
	@RequestMapping(value="/cancelarViaje",method=RequestMethod.GET)
	public String cancelarViaje(@RequestParam("idViaje") String idViaje,Model model, HttpServletResponse response, HttpServletRequest request,HttpSession sesion) {
		if(sesion.getAttribute("usuLogeado")==null) {
			return "redirect:/";
		}
		model.addAttribute("usuLogeado",sesion.getAttribute("usuLogeado"));
		int id=Integer.parseInt(request.getParameter("idViaje"));		
		int idUsuario=((Usuarios)sesion.getAttribute("usuLogeado")).getIdUsuario();
		viajesDao.cancelarViaje(id,idUsuario);
		return "redirect:/gestionar";
	}

	@RequestMapping(value="/comprarViaje",method=RequestMethod.GET)
	public String compraViaje(@RequestParam("idViaje") String idViaje,Model model, HttpServletResponse response, HttpServletRequest request,HttpSession sesion) {
		if(sesion.getAttribute("usuLogeado")==null) {
			return "redirect:/";
		}
		model.addAttribute("usuLogeado",sesion.getAttribute("usuLogeado"));
		int id=Integer.parseInt(request.getParameter("idViaje"));		
		int idUsuario=((Usuarios)sesion.getAttribute("usuLogeado")).getIdUsuario();
		
		if(viajesDao.AñadirBilletes(idUsuario,id)) {
			return "redirect:/usuario?infoCompra=El viaje se ha comprado.";
		}else {
			return "redirect:/usuario?infoCompra=El viaje ya estaba comprado.";
		}	
	}

	
	@RequestMapping(value="/inicioSesion", method=RequestMethod.POST)
	public String login(@ModelAttribute Usuarios usu,HttpSession sesion) {
		usu=usuariosDao.comprobarUsuario(usu.getNombre(), usu.getContrasenia());
		
		if (usu!=null) {
			// Usuario correcto
			// Poner al usuario en sesión
		
			sesion.setAttribute("usuLogeado", usu);
			
			switch (usu.getTipoUsuario()) {
			case 2:
				// Usuario Normal
				return "redirect:/usuario";
				
			case 1:
				// Administrador
				return "redirect:/administrador";
				
		} 
		}
			// Error Usuario
		return "redirect:/?fallo=Usuario y/o Password Incorrecto";
				
	}
	
	
	@RequestMapping(value="/editaPerfil",method= RequestMethod.POST)
	public String editaPerfil(@ModelAttribute Usuarios usu,Model model,HttpSession sesion) {
		if(sesion.getAttribute("usuLogeado")==null) {
			return "redirect:/";
		}
		model.addAttribute("usuLogeado",sesion.getAttribute("usuLogeado"));
		usuariosDao.Editar(usu.getIdUsuario(),usu.getNombre(),usu.getEmail(),usu.getTelefono(),usu.getDireccion(),2);
		sesion.setAttribute("usuLogeado", usu);
		if (usuariosDao.validarUsuario(usu)) {
			
		    return "redirect:/editar?info=Usuario Editado";
		}else {
		  return "redirect:/editar";
		}
	}
	
	@RequestMapping(value="/editar",method= RequestMethod.GET)
	public ModelAndView editar(@RequestParam(value="info",required=false,defaultValue="") String info,Model model,HttpSession sesion) {
		if(sesion.getAttribute("usuLogeado")==null) {
			return new ModelAndView("index","usuario",new Usuarios());
		}
		model.addAttribute("info",info);
		model.addAttribute("usuLogeado",sesion.getAttribute("usuLogeado"));
		return new ModelAndView("editarPerfil","usuario",sesion.getAttribute("usuLogeado"));
	}
	
	
	@RequestMapping(value="/editarAdmin",method= RequestMethod.GET)
	public ModelAndView editarAdmin(@RequestParam(value="info",required=false,defaultValue="") String info,Model model,HttpSession sesion) {
		if(sesion.getAttribute("usuLogeado")==null) {
			return new ModelAndView("index","usuario",new Usuarios());
		}
		model.addAttribute("info",info);
		model.addAttribute("usuLogeado",sesion.getAttribute("usuLogeado"));
		return new ModelAndView("editarPerfilAdmin","usuario",sesion.getAttribute("usuLogeado"));
	}
	
	@RequestMapping(value="/editaPerfilAdministrador",method= RequestMethod.POST)
	public String editarPerfilAdmin(@ModelAttribute Usuarios usu,Model model,HttpSession sesion) {
		if(sesion.getAttribute("usuLogeado")==null) {
			return "redirect:/";
		}
		model.addAttribute("usuLogeado",sesion.getAttribute("usuLogeado"));
		usuariosDao.Editar(usu.getIdUsuario(),usu.getNombre(),usu.getEmail(),usu.getTelefono(),usu.getDireccion(),1);
		sesion.setAttribute("usuLogeado", usu);
		if (usuariosDao.validarUsuario(usu)) {
			
		    return "redirect:/editarAdmin?info=Usuario Editado";
		}else {
		  return "redirect:/editarAdmin";
		}
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout(HttpSession sesion) {
		sesion.setAttribute("usuLogeado", null);
		return "redirect:/";
	}
	
	
	
	@RequestMapping(value="/usuario", method=RequestMethod.GET)
	public String usuarioNormal(@RequestParam(value="infoCompra",required=false,defaultValue="") String infoCompra,Model model,HttpSession sesion) {
		if(sesion.getAttribute("usuLogeado")==null) {
			return "redirect:/";
		}
		model.addAttribute("infoCompra",infoCompra);
		model.addAttribute("listaViajes",viajesDao.listarTodosViajes());
		model.addAttribute("usuLogeado",sesion.getAttribute("usuLogeado"));
		return "usuario";
	}
	@RequestMapping(value="/administrador", method=RequestMethod.GET)
	public String administrador(Model model,HttpSession sesion) {
		if(sesion.getAttribute("usuLogeado")==null) {
			return "redirect:/";
		}
		model.addAttribute("usuLogeado",sesion.getAttribute("usuLogeado"));
		model.addAttribute("listaUsuarios",usuariosDao.listarUsuarios());	
		return "administrador";
	}
	
	@RequestMapping(value="/gestionar", method=RequestMethod.GET)
	public String gestionarViajes(Model model,HttpSession sesion) {
		if(sesion.getAttribute("usuLogeado")==null) {
			return "redirect:/";
		}
		model.addAttribute("usuLogeado",sesion.getAttribute("usuLogeado"));
		model.addAttribute("listaViajesId",viajesDao.listarViajesUsuario(((Usuarios)sesion.getAttribute("usuLogeado")).getIdUsuario()));	
		return "gestionarViajes";
	}
	
	@RequestMapping(value="/addUsuario", method=RequestMethod.POST)
	public String addUsuario(@ModelAttribute Usuarios usu) {
	
		int filas = 0;
		String msg = "";		
		
		if (!usuariosDao.validarUsuario(usu)) {
			filas = usuariosDao.insertar(usu);
			if (filas == 1) {
				msg = "Usuario Registrado";
				
				return "redirect:/?mensaje="+msg;
			}
			else {
				msg = "Error al Registrar al Usuario";
				
				return "redirect:/?mensaje="+msg;
			}
		} else {
			msg = "Nombre de usuario ya registrado. Inténtelo con otro";
			
			return "redirect:/?mensaje="+msg;
		}
	}
}
