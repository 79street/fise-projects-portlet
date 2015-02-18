<%@page import="javax.portlet.PortletSession"%>
<%
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects />

<portlet:actionURL var="accionURL" name="actionNormal">  
	<portlet:param name="action" value="cargar" />
</portlet:actionURL>

<link href="/fise-projects-portlet/css/tablas.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
var data_funcion = []; 

$(document).ready(function () {	
	 $("#<portlet:namespace/>reportePdf").click(function() {
		 <portlet:namespace/>mostrarReportePdf();
	 });
	 $("#<portlet:namespace/>reporteExcel").click(function() {
		 <portlet:namespace/>mostrarReporteExcel();
	 }); 	
 	initDialogs();
});

function <portlet:namespace/>mostrarReportePdf(){
	if(<portlet:namespace />validarCumplimientoMensual()){
		jQuery.ajax({
			url : '<portlet:resourceURL id="reporteMensual" />',
			type : 'post',
			dataType : 'json',
			data : {
				<portlet:namespace />grupoInf: $("#<portlet:namespace/>grupoInfBusq").val(),
				<portlet:namespace />etapa: $("#<portlet:namespace/>etapa").val(),
				<portlet:namespace />tipoArchivo: '0'//PDF
			},
			success : function(data) {
				if(data.resultado=='OK'){
					verReporte();
				}else if(data.resultado=='VACIO'){							
					$("#<portlet:namespace/>dialog-info-content").html('No existe ningún registro con los criterios seleccionados');
					$("#<portlet:namespace/>dialog-info").dialog( "open" );
			   }else{
				    $("#<portlet:namespace/>dialog-error-content").html('Error al mostrar Reporte de Cumplimiento Mensual');
					$("#<portlet:namespace/>dialog-error").dialog( "open" );  
			   }
			}
		});	
	}	
}

function <portlet:namespace/>mostrarReporteExcel(){
	if(<portlet:namespace />validarCumplimientoMensual()){
		jQuery.ajax({
			url : '<portlet:resourceURL id="reporteMensual" />',
			type : 'post',
			dataType : 'json',
			data : {
				<portlet:namespace />grupoInf: $("#<portlet:namespace/>grupoInfBusq").val(),
				<portlet:namespace />etapa: $("#<portlet:namespace/>etapa").val(),
				<portlet:namespace />tipoArchivo: '1'//XLS
			},
			success : function(data) {
				if(data.resultado=='OK'){
					verReporte();
				}else if(data.resultado=='VACIO'){							
					$("#<portlet:namespace/>dialog-info-content").html('No existe ningún registro con los criterios seleccionados');
					$("#<portlet:namespace/>dialog-info").dialog( "open" );
			   }else{
				    $("#<portlet:namespace/>dialog-error-content").html('Error al mostrar Reporte de Cumplimiento Mensual');
					$("#<portlet:namespace/>dialog-error").dialog( "open" );  
			   }			
			}
		});
	}	
}

function <portlet:namespace />validarCumplimientoMensual(){
	console.debug('entrando a validar formulario');
	if($("#<portlet:namespace/>grupoInfBusq").val()==null || 
			$("#<portlet:namespace/>grupoInfBusq").val()==''){			
		$("#<portlet:namespace/>dialog-alert-content").html('Debe seleccionar un Grupo de Información');
		$("#<portlet:namespace/>dialog-alert").dialog( "open" );
	 	return false;
	}else if($("#<portlet:namespace/>etapa").val()==null || 
			$("#<portlet:namespace/>etapa").val()==''){			
		$("#<portlet:namespace/>dialog-alert-content").html('Debe seleccionar una Etapa');
		$("#<portlet:namespace/>dialog-alert").dialog( "open" );
	 	return false;
	}else{
		return true;
	}
} 

function verReporte(){
	window.open('<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ViewReport")%>','_newtab');
}

function initDialogs(){	
	$( "#<portlet:namespace/>dialog-alert" ).dialog({
		modal: true,
		width: 450,	
		autoOpen: false,
		buttons: {
			Ok: function() {
				$( this ).dialog( "close" );
			}
		}
	});
	
	$( "#<portlet:namespace/>dialog-error" ).dialog({
		modal: true,
		width: 450,	
		autoOpen: false,
		buttons: {
			Ok: function() {
				$( this ).dialog( "close" );
			}
		}
	});
	
	$( "#<portlet:namespace/>dialog-info" ).dialog({
		modal: true,
		width: 450,	
		autoOpen: false,
		buttons: {
			Ok: function() {
				$( this ).dialog( "close" );
			}
		}
	});
}

</script>


<style>

.inputText-dashed {
	border-style: dashed; 
	border-color: black;
	width:100px;
}
</style>


<form id="form-cumplimiento" name="form-cumplimiento"  method="POST" style="padding:17px;padding-top:0px;" 
      action="${accionURL}"  >	

	<div id="d_listado" class="net-frame-listado"> 
		<div id="d_filtro">
			<div id="div_contenido" >
				
				<div id="<portlet:namespace/>div_home" class="net-frame-listado">
						
					<table style="width: 100%;" border="0">
						<tr>
							<td>
								<output class="net-titulo">Reporte de Cumplimiento Mensual</output>
							</td>
						</tr>
						<tr height="10px">
							<td></td>
						</tr>
						<tr>
							<td class="filete">
								<fieldset class="">
									<table class="" style="width: 100%;" border="0">
										
										<tr height="10px">
											<td colspan="2"></td>
										</tr>
										<tr>
											<td style="width: 180px">
												<output>Grupo Información:</output>
											</td>
											<td>
												<select id="<portlet:namespace/>grupoInfBusq" name="<portlet:namespace/>grupoInfBusq" class="select" style="width:200px;" >
						   							<c:forEach items="${listaGrupoInf}" var="grupo">																
														<option value="${grupo.idGrupoInformacion}">${grupo.descripcion}</option>
													</c:forEach>
												</select>												
											</td>
										</tr>
										<tr height="10px">
											<td colspan="2"></td>
										</tr>
										<tr>
											<td><output>Etapa:</output></td>
											<td>
											    <select id="<portlet:namespace/>etapa" name="<portlet:namespace/>etapa" class="select" style="width:150px;" >						   																							
													<option value="SOLICITUD">SOLICITUD</option>
													<option value="LEV.OBS">LEVANTAMIENTO OBSERVACIONES</option>
													<option value="RECONOCIDO">RECONOCIDO</option>													
													<option value="RECONSIDERACION">RECONSIDERACION</option>		
												</select>	
												
											</td>											
										</tr>
										
										<tr height="10px">
											<td colspan="2"></td>
										</tr>
										
										<tr>
										   <td>											
										   </td>
											<td>
												<input type="button" class="boton" name="<portlet:namespace/>reportePdf" 
															id="<portlet:namespace/>reportePdf" class="button net-button-small"  value="PDF"/>
												<input type="button" class="boton" name="<portlet:namespace/>reporteExcel" 
															id="<portlet:namespace/>reporteExcel" class="button net-button-small"  value="XLS"/>
											</td>
										</tr>
									</table>
								</fieldset>
							</td>
						</tr>
						<tr height="10px">
							<td></td>
						</tr>
						
					</table>
   						
				</div>


				</div>
			</div>
		</div>
		
		
    <!-- DIALOGO PARA ALERTAR DE VALIDACION -->
	
	<div id="<portlet:namespace/>dialog-alert" title="Mensaje de Validaci&oacute;n" style="display: none;"> 
		<p>						
			<img src="/fise-projects-portlet/images/warning.png" style="float:left; margin:20px 25px 20px 5px;">
			<br/>
			<label class="labelCentrado" id="<portlet:namespace/>dialog-alert-content">Debe Ingresar..</label>
		</p>
	</div>
	
	<!-- DIALOGO PARA ERRORES -->
	
	<div id="<portlet:namespace/>dialog-error" title="Mensaje de Error">
		<p>	
			<img src="/fise-projects-portlet/images/error.png" style="float:left; margin:20px 25px 20px 5px;">
			<br/>
			<label class="labelCentrado" id="<portlet:namespace/>dialog-error-content">Error..</label>
		</p>
	</div>	
	
	<!-- DIALOGO PARA INFORMAR UN MENSAJE -->
	
	<div id="<portlet:namespace/>dialog-info" title="Mensaje de Informaci&oacute;n">
		<p>	
			<img src="/fise-projects-portlet/images/info.png" style="float:left; margin:20px 25px 20px 5px;">
			<br/>
			<label class="labelCentrado" id="<portlet:namespace/>dialog-info-content">Error..</label>
		</p>
	</div>	

</form> 
