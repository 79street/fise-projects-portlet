<%@include file="/WEB-INF/jsps/gart/ext/extResumenCostosActividad.jsp"%>


<link href="/fise-projects-portlet/css/tablas.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/fise-projects-portlet/js/fise.js"></script>



<script type="text/javascript">
$(document).ready(function () {	
	resumenCostoActiv.init();
});
</script>
           
           
<form:form method="POST" style="padding:17px;padding-top:0px;" 
            modelAttribute="resumenCostoActividadBean">
            
            
            	
<div id="d_filtro" class="net-frame-listado"> 
		<div id="d_filtro">
			<div id="div_contenido" >
			
			    <!-- DIV PARA BUSQUEDA  -->
			  			
				<div id="<portlet:namespace/>div_buscar" class="net-frame-listado" >
				
			     <table style="width: 100%;" border="0">
						<tr>
							<td>
								<!-- <output class="net-titulo">Situaci�n actual de la declaraci�n de Gastos</output> -->
							</td>
						</tr>
						<tr height="10px">
							<td></td>
						</tr>
						<tr>
							<td class="filete">
								<fieldset class="">
									<table class="" style="width: 100%;" border="0">
										<tr>
											<td colspan="2"><output class="net-titulo">Criterios
													de B�squeda:</output></td>
										</tr>
										<tr height="10px">
											<td colspan="2"></td>
										</tr>
																				
										<tr height="10px">
											<td colspan="2"></td>
										</tr>
										<tr>										
											<td style="width:220px"><output>Grupo Informaci�n:</output></td>
											<td>												
												 <form:select path="grupoInfBusq" cssClass="select" cssStyle="width: 200px;">															
												    <form:options items="${resumenCostoActividadBean.listaGrupoInf}"  itemLabel="descripcion" itemValue="idGrupoInformacion"/>
												</form:select>													
											</td>	
																									
										</tr>
										<tr height="10px">
											<td colspan="2"></td>
										</tr>
										<tr>										
											<td><output>Distribuidora El�ctrica:</output></td>
											<td>
												<form:select path="codEmpresaBusq" cssClass="select" cssStyle="width: 375px;">							
													<form:option value="TODOS">-Todos-</form:option>
													<form:options items="${resumenCostoActividadBean.listaEmpresas}"  itemLabel="dscEmpresa" itemValue="codEmpresa"/>
												</form:select>
											</td>
																																		
										</tr>
										<tr height="10px">
											<td colspan="2"></td>
										</tr>
										
										<tr>										
											<td><output>Tipo de Formato de Exportaci�n:</output></td>
											<td>
												<input type="radio"	name="formatoExportar"
												       id="rbtPdf" value="PDF" checked="true"/>PDF
												 &nbsp;&nbsp;&nbsp;	
												 
												<input type="radio"	name="formatoExportar"
												       id="rbtExcel" value="EXCEL" />EXCEL	
											</td>						
																																	
										</tr>
										
										<tr height="10px">
											<td colspan="2"></td>
										</tr>	
										
										<tr>
										   <td></td>												
											<td>
											   <input name="<portlet:namespace/>btnGenerarF14AB"
														 id="<portlet:namespace/>btnGenerarF14AB" type="button"
														 class="net-button-small" value="Generar" style="aling:center" />
											</td>																																																
										</tr>
										
										<tr height="10px">
											<td colspan="2"></td>
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
				
				<!-- FIN DE DIV  PARA BUSQUEDA -->			
								
            </div>
       </div>       
</div> 

   <!-- DIVS PARA MENSAJES -->
   
   	<div id="<portlet:namespace/>dialog-message-resumen_costos_actividad" title="Mensaje de &Eacute;xito">
		<p>
			<img src="/fise-projects-portlet/images/success.png" style="float:left; margin:20px 25px 20px 5px;">	
			<br/>
			<label class="labelCentrado" id="<portlet:namespace/>dialog-message-content-resumen_costos_actividad">Datos eliminados exit&oacute;samente.</label>
		</p>	
	</div>
		
	
	<!-- DIALOGO PARA ALERTAR DE VALIDACION -->
	
	<div id="<portlet:namespace/>dialog-alert" title="Mensaje de Validaci&oacute;n">
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
		
		
	
</form:form>