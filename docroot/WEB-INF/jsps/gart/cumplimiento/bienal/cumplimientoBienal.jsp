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
	 $("#<portlet:namespace/>reportePdf").click(function() {<portlet:namespace/>mostrarReportePdf();});
	 $("#<portlet:namespace/>reporteExcel").click(function() {<portlet:namespace/>mostrarReporteExcel();});
 	//initBlockUI();	
 	initDialogs();
});

function <portlet:namespace/>mostrarReportePdf(){
	if(<portlet:namespace />validarCumplimientoBienal()){
		jQuery.ajax({
			url : '<portlet:resourceURL id="reporte" />',
			type : 'post',
			dataType : 'json',
			data : {
				<portlet:namespace />periodo: $("#<portlet:namespace/>s_periodo_cump").val(),
				<portlet:namespace />tipoArchivo: '0'//PDF
			},
			success : function(gridData) {
				verReporte();
			}
		});	
	}	
}

function <portlet:namespace/>mostrarReporteExcel(){
	if(<portlet:namespace />validarCumplimientoBienal()){
		jQuery.ajax({
			url : '<portlet:resourceURL id="reporte" />',
			type : 'post',
			dataType : 'json',
			data : {
				<portlet:namespace />periodo: $("#<portlet:namespace/>s_periodo_cump").val(),
				<portlet:namespace />tipoArchivo: '1'//XLS
			},
			success : function(gridData) {
				verReporte();
			}
		});	
	}	
}

function <portlet:namespace />validarCumplimientoBienal(){
	console.debug('entrando a validar formulario');
	if($("#<portlet:namespace/>s_periodo_cump").val()==null || 
			$("#<portlet:namespace/>s_periodo_cump").val()==''){		
		//alert('Debe seleccionar un Periodo.'); 	
		$("#<portlet:namespace/>dialog-alert-content").html('Debe seleccionar un Periodo');
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
}
//////////
</script>

<style>

.inputText-dashed {
	border-style: dashed; 
	border-color: black;
	width:100px;
}
</style>

<form id="form-cumplimiento-bienal" name="form-cumplimiento-bienal"  method="POST" style="padding:17px;padding-top:0px;" action="${accionURL}"  >	

	<div id="d_listado" class="net-frame-listado"> 
		<div id="d_filtro">
			<div id="div_contenido" >
				
				<div id="<portlet:namespace/>div_home" class="net-frame-listado">
						
					<table style="width: 100%;" border="0">
						<tr>
							<td>
								<output class="net-titulo">Reporte de Cumplimiento Bienal</output>
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
											<td colspan="8"></td>
										</tr>
										<tr>
											<td>
												<output>Periodo:</output>
											</td>
											<td colspan="7">
												<select id="<portlet:namespace/>s_periodo_cump" name="<portlet:namespace/>s_periodo_cump" style="width:375px;" class="select"  >
						   							<c:forEach items="${listaPeriodo}" var="periodo">																
														<option value="${periodo.codigoItem}">${periodo.descripcionItem}</option>
													</c:forEach>
												</select>
											</td>
										</tr>
										<tr height="10px">
											<td colspan="8"></td>
										</tr>
										<tr>
											<td colspan="8" align="center">
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

</form> 
