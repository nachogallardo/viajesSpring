<!DOCTYPE html>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="es.altair.springhibernate.dao.UsuariosDaoImp"%>
<%@page import="es.altair.springhibernate.dao.UsuariosDao"%>
<%@page import="es.altair.springhibernate.bean.Usuarios"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<html lang="en">
<head>
<meta charset="utf-8">

<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<meta name="description" content="">

<meta name="author" content="">





<title>Usuario</title>

<link rel="icon" href="<c:url value="/resources/images/favicon.ico"/>">
<!-- Bootstrap core CSS -->
<link href="<c:url value="/resources/dist/css/bootstrap.min.css"/>" rel="stylesheet">

<!--Fonts-->
<link
	href="https://fonts.googleapis.com/css?family=Montserrat:300,300i,400,400i,500,500i,600,600i,700,700i"
	rel="stylesheet">

<!-- Icons -->
<link href="<c:url value="/resources/css/font-awesome.css"/>" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet">
</head>
<body>
	<div class="container-fluid" id="wrapper">
		<div class="row">
			<nav
				class="sidebar col-xs-12 col-sm-4 col-lg-3 col-xl-2 bg-faded sidebar-style-1">
				<h1 class="site-title">
					<a href="#"><em class="fa fa-rocket"></em> Viajes</a>
				</h1>

				<a href="#menu-toggle" class="btn btn-default" id="menu-toggle"><em
					class="fa fa-bars"></em></a>

				<ul class="nav nav-pills flex-column sidebar-nav">
					<li class="nav-item"><a class="nav-link active"
						href="<c:url value="administrador"/>"><em class="fa fa-user-circle mr-1"></em>
							Editar Usuarios <span class="sr-only">(current)</span></a></li>
					<li class="nav-item"><a class="nav-link"
						href="<c:url value="/editarAdmin"/>"><em class="fa fa-cog mr-1"></em>
							Editar Perfil</a></li>
					<li class="nav-item"><a class="nav-link"
						href="<c:url value="/agregarViaje"/>"><em class="fa fa-plane mr-1"></em>
							Agregar Viajes</a></li>
					<li class="nav-item"><a class="nav-link"
						href="<c:url value="/borrarViaje"/>"><em class="fa fa-plane mr-1"></em>
							Borrar Viajes</a></li>

				</ul>

				<a href="<c:url value="/logout"/>" class="logout-button"><em
					class="fa fa-power-off"></em> Cerrar Sesion</a>
			</nav>

			<main
				class="col-xs-12 col-sm-8 offset-sm-4 col-lg-9 offset-lg-3 col-xl-10 offset-xl-2 pt-3 pl-4">
			<header class="page-header row justify-center">
				<div class="col-md-6 col-lg-8">
					<h1 class="float-left text-center text-md-left">
						Bienvenido
						<%=((Usuarios) session.getAttribute("usuLogeado")).getNombre()%></h1>
				</div>

				<div
					class="dropdown user-dropdown col-md-6 col-lg-4 text-center text-md-right">
					<a class="btn btn-stripped dropdown-toggle" href="#"
						id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true"
						aria-expanded="false">


						<div class="username mt-1">
							<h4 class="mb-1"><%=((Usuarios) session.getAttribute("usuLogeado")).getNombre()%></h4>

							<h6 class="text-muted">Opciones</h6>
						</div>
					</a>

					<div class="dropdown-menu dropdown-menu-right"
						style="margin-right: 1.5rem;" aria-labelledby="dropdownMenuLink">
						<a class="dropdown-item" href="<c:url value="/editarAdmin"/>"><em
							class="fa fa-cog mr-1"></em> Editar Perfil</a> <a
							class="dropdown-item" href="<c:url value="/logout"/>"><em
							class="fa fa-power-off mr-1"></em> Cerrar Sesion</a>
					</div>
				</div>

				<div class="clear"></div>
			</header>


			<div class="section">
				<div class="container">
					<div class="row">
						<div class="col-md-12">
							<table class="table table-hover table-striped">
								<tbody>
									<c:forEach items="${listaUsuarios}" var="u">
									<tr>
										<td><img
											src="http://pingendo.github.io/pingendo-bootstrap/assets/user_placeholder.png"
											class="img-circle" width="60"></td>
										<td>
											<h4>
												<b>${u.nombre }</b>
											</h4>
											<p>${u.direccion }</p>
										</td>

										<td>
											<h4>
												<b>${u.telefono }</b>
											</h4> <a href="#"> ${u.email }</a>
										</td>
										<td>
										<a data-toggle="modal" data-target="#borrarUsuario${u.idUsuario }">
												<button class="btn btn-outline-danger" value="left"
													type="button">
													<i class="fa fa-fw s fa-remove"></i>Eliminar
												</button>
										</a></td>
										<td><a
											href="<c:url value="/editarOtroUsuario?idUsuario=${u.idUsuario }"/>">
												<button class="btn btn-outline-info" value="right"
													type="button">
													<i class="fa fa-fw fa-cog"></i>Configurar
												</button>
										</a></td>



										<div class="modal fade"
											id="borrarUsuario${u.idUsuario }" tabindex="-1"
											role="dialog" aria-labelledby="exampleModalLabel"
											aria-hidden="true">
											<div class="modal-dialog" role="document">
												<div class="modal-content">
													<div class="modal-header">
														<h5 class="modal-title" id="exampleModalLabel">Borrar
															Usuario</h5>
														<button type="button" class="close" data-dismiss="modal"
															aria-label="Close">
															<span aria-hidden="true">&times;</span>
														</button>
													</div>
													<div class="modal-body">�Est�s seguro de borrar el
														usuario?</div>
													<div class="modal-footer">
														<button type="button" class="btn btn-secondary"
															data-dismiss="modal">No</button>
														<button type="button" class="btn btn-primary"
															onclick="location.href='<c:url value="/borrarUsuario?idUsuario=${u.idUsuario }"/>'">S�</button>
													</div>
												</div>
											</div>
										</div>

									</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>


			
		</div>
	</div>









	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="<c:url value="/resources/js/jquery-3.2.1.min.js"/>"></script>
	<script src="<c:url value="/resources/dist/js/bootstrap.min.js"/>"></script>

	<script src="<c:url value="/resources/js/chart.min.js"/>"></script>
	<script src="<c:url value="/resources/js/chart-data.js"/>"></script>
	<script src="<c:url value="/resources/js/easypiechart.js"/>"></script>
	<script src="<c:url value="/resources/js/easypiechart-data.js"/>"></script>
	<script src="<c:url value="/resources/js/bootstrap-datepicker.js"/>"></script>
	<script src="<c:url value="/resources/js/custom.js"/>"></script>
	<script>
		window.onload = function() {
			var chart1 = document.getElementById("line-chart").getContext("2d");
			window.myLine = new Chart(chart1).Line(lineChartData, {
				responsive : true,
				scaleLineColor : "rgba(0,0,0,.2)",
				scaleGridLineColor : "rgba(0,0,0,.05)",
				scaleFontColor : "#c5c7cc"
			});
		};
	</script>

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"
		integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb"
		crossorigin="anonymous"></script>

</body>
</html>