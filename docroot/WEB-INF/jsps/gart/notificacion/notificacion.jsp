<%@include file="/WEB-INF/jsps/gart/ext/extNotificacion.jsp"%>


<link href="/fise-projects-portlet/css/tablas.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/fise-projects-portlet/js/fise.js"></script>



<script type="text/javascript">
$(document).ready(function () {	
	notificarValidar.init();
});
</script>
           
           
<form:form method="POST" style="padding:17px;padding-top:0px;" 
            modelAttribute="notificacionBean">
            
            
            	
<div id="d_filtro" class="net-frame-listado"> 
		<div id="d_filtro">
			<div id="div_contenido" >
			
			  <!-- DIV PARA BUSQUEDA  -->	
			
				<div id="<portlet:namespace/>div_buscar" class="net-frame-listado">
				
			     <table style="width: 100%;" border="0">
						<tr>
							<td>
								<!-- <output class="net-titulo">Situación actual de la declaración de Gastos</output> -->
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
											<td colspan="3"><output class="net-titulo">Criterios
													de Búsqueda:</output></td>
										</tr>
										<tr height="10px">
											<td colspan="4"></td>
										</tr>
										<tr>
											<td><output>Distribuidora Eléctrica:</output></td>
											<td>
												<form:select path="codEmpresaBusq" cssClass="select" cssStyle="width: 375px;">												
													<form:options items="${notificacionBean.listaEmpresas}"  itemLabel="dscEmpresa" itemValue="codEmpresa"/>
												</form:select>
											</td>
											
											<td><output>Etapa:</output></td>
											<td>
												<form:select path="etapaBusq" cssClass="select" cssStyle="width: 220px;">
													<form:option value="SOLICITUD">SOLICITUD</form:option>
													<form:option value="LEV.OBS">LEVANTAMIENTO OBSERVACIONES</form:option>
													<%-- <form:option value="RECONSIDERACION">RECONSIDERACION</form:option>
													<form:option value="RECONOCIDO">RECONOCIDO</form:option> --%>
												</form:select>
											</td>							
										</tr>
										<tr height="10px">
											<td colspan="4"></td>
										</tr>
										<tr>
										   <td><output>Periodicidad:</output></td>											
										    <td>
												<input type="radio"	name="optionFormato"
												       id="rbtMensual" value="MENSUAL" checked="true"/>Mensual
												 &nbsp;&nbsp;&nbsp;	
												 
												<input type="radio"	name="optionFormato"
												       id="rbtBienal" value="BIENAL"/>Bienal	
											</td>		
										
											<td><output>Grupo Información:</output></td>
											<td>												
												 <form:select path="grupoInfBusq" cssClass="select" cssStyle="width: 200px;">															
												    <form:options items="${notificacionBean.listaGrupoInf}"  itemLabel="descripcion" itemValue="idGrupoInformacion"/>
												</form:select>													
											</td>		
																													
										</tr>
										<tr height="10px">
											<td colspan="4"></td>
										</tr>
										
										<tr>
											<td></td>
											<td></td>												
											<td style="aling:right"><input name="<portlet:namespace/>btnBuscarNotificacion"
												id="<portlet:namespace/>btnBuscarNotificacion" type="button"
												class="net-button-small" value="Buscar" style="aling:center" />
											</td>
											<td style="aling:right"><input name="<portlet:namespace/>btnProcesarNotificacion"
												id="<portlet:namespace/>btnProcesarNotificacion" type="button"
												class="net-button-small" value="Procesar Validaci&oacute;n" style="aling:center" />
											</td>
										</tr>
									</table>
								</fieldset>
							</td>
						</tr>
						<tr height="10px">
							<td></td>
						</tr>
						<tr>
							<td>
								<table id="<portlet:namespace/>grid_resultado_busqueda">
								</table>
								<div id="<portlet:namespace/>paginador_resultado_busqueda"></div>
							</td>
						</tr>
						<tr height="10px">
							<td></td>
						</tr>
						<tr>
							<td>
								<table style="width: 100%;" border="0">
									<tr>
										<td></td>										
										<td align="right" width="90px">
											<div id="d_opc_notificar">
												<input type="button" class="net-button-small"
													id="<portlet:namespace/>btnNotificarValidar"
													name="<portlet:namespace/>btnNotificarValidar" value="Notificar" />
											</div>
										</td>
										<td align="right" width="90px">											
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr height="10px">
							<td></td>
						</tr>					
					</table>			
				</div>								
				<!-- FIN DE DIV  PARA BUSQUEDA -->	
				
				
				
				<!-- DIV PARA PRESENTAR LOS DETALLE DEL FORMATO SELECCIONADO -->
				
				<div id="<portlet:namespace/>div_detalle_formato" class="net-frame-listado" style="display: none;"> 
				
			     <table style="width: 100%;" border="0">
						<tr>
							<td>								
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
										    <td colspan="6">
											    <input id="tituloDetalleFormato" readonly="readonly" style="border:none; width:100%; 
											       background:#efefef;font-weight:bold; font-size: 12px"  class="net-titulo">
										    </td>
										</tr>
										<tr height="10px">
											<td colspan="6"></td>
										</tr>
										<tr>
											<td style="width:160px"><label style="font-size: 12px; font-weight:bold">Distribuidora Eléctrica:</label>
											</td>
											<td>											  
											  <input id="empresaDetalle" readonly="readonly" style="border:none; background:#efefef;">										  					  
											</td>
											
											<td style="width:160px"><label style="font-size: 12px; font-weight:bold">Formato:</label></td>
											<td>											  
											  <input id="formatoDetalle" readonly="readonly" style="border:none; background:#efefef;">
											</td>
											
											<td style="width:160px"><label style="font-size: 12px; font-weight:bold">Etapa:</label></td>
											<td>											 
											  <input id="etapaDetalle" readonly="readonly" style="border:none; background:#efefef;">	
											</td>
											
									 	</tr>		
										<tr height="10px">
											<td colspan="6"></td>
										</tr>
										
										<tr>
											<td><label style="font-size: 12px; font-weight:bold">Año Declarado:</label></td>
											<td>										  
											  <input id="anioDetalle" readonly="readonly" style="border:none; background:#efefef;">	 	
											</td>
											
											<td><label style="font-size: 12px; font-weight:bold">Mes Declarado:</label></td>
											<td>										  
											  <input id="mesDetalle" readonly="readonly" style="border:none; background:#efefef;">	 	
											</td>
											
											<td><label style="font-size: 12px; font-weight:bold">Año Ejecución:</label></td>
											<td>										 
											  <input id="anioEjecDetalle" readonly="readonly" style="border:none; background:#efefef;">	 	 	
											</td>
											
										</tr>
										
										<tr height="10px">
											<td colspan="6"></td>
										</tr>
										
										
										<tr>
											<td><label style="font-size: 12px; font-weight:bold">Mes Ejecución:</label></td>
											<td>						 
											  <input id="mesEjecDetalle" readonly="readonly" style="border:none; background:#efefef;"> 	
											</td>
											
											<td><label style="font-size: 12px; font-weight:bold">Año Inicio Vigencia:</label></td>
											<td>											 
											  <input id="anioInicioVigDetalle" readonly="readonly" style="border:none; background:#efefef;">	
											</td>
											
											<td><label style="font-size: 12px; font-weight:bold">Año Fin Vigencia:</label></td>
											<td>											  
											  <input id="anioFinVigDetalle" readonly="readonly" style="border:none; background:#efefef;">	
											</td>
											
										</tr>		
										
										<tr height="10px">
											<td colspan="6"></td>
										</tr>
																		
									</table>
								</fieldset>
							</td>
						</tr>
						<tr height="10px">
							<td></td>
						</tr>
						<tr>
							<td>
							    <div id="<portlet:namespace/>div_grilla_F12AB" style="display: none;"> 
							    
							    <table id="<portlet:namespace/>grid_resultado_busqueda_detalle_12AB">
								</table>
								<div id="<portlet:namespace/>paginador_resultado_busqueda_detalle_12AB"></div>
							    
							    </div>
							    
							    <div id="<portlet:namespace/>div_grilla_F12CD" style="display: none;">
							    
							    <table id="<portlet:namespace/>grid_resultado_busqueda_detalle_12CD">
								</table>
								<div id="<portlet:namespace/>paginador_resultado_busqueda_detalle_12CD"></div>
							    
							    </div>
							    
							    <div id="<portlet:namespace/>div_grilla_F13A" style="display: none;">
							    
							    <table id="<portlet:namespace/>grid_resultado_busqueda_detalle_13A">
								</table>
								<div id="<portlet:namespace/>paginador_resultado_busqueda_detalle_13A"></div>
							    
							    </div>
							    
							    <div id="<portlet:namespace/>div_grilla_F14AB" style="display: none;">
							    
							    <table id="<portlet:namespace/>grid_resultado_busqueda_detalle_14AB">
								</table>
								<div id="<portlet:namespace/>paginador_resultado_busqueda_detalle_14AB"></div>
							    
							    </div>
							    
							    <div id="<portlet:namespace/>div_grilla_F14C" style="display: none;">
							    
							    <table id="<portlet:namespace/>grid_resultado_busqueda_detalle_14C">
								</table>
								<div id="<portlet:namespace/>paginador_resultado_busqueda_detalle_14C"></div>
							    
							    </div>
								
							</td>
						</tr>
						<tr height="10px">
							<td></td>
						</tr>
						
						<tr>
							<td>
								<table style="width: 100%;" border="0">
									<tr>										
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>										
										<td align="right" width="90px">	
										    <div id="d_opc_regresar_busqueda">
												<input type="button" class="net-button-small"
													id="<portlet:namespace/>btnRegresarBusqueda"
													name="<portlet:namespace/>btnRegresarBusqueda" value="Regresar" />
											</div>										
										</td>
									</tr>
								</table>
							</td>
						</tr>
						
						<tr height="10px">
							<td></td>
						</tr>
																
					</table>			
				</div>								
				<!-- FIN DIV PARA PRESENTAR LOS DETALLE DEL FORMATO SELECCIONADO -->	
				
						
				
				<!-- DIV PARA PRESENTAR LAS OBS.  POR CADA DETALLE SELECCIONADO -->
				
				<div id="<portlet:namespace/>div_detalle_observaciones" class="net-frame-listado" style="display: none;"> 
				
			     <table style="width: 100%;" border="0">
						<tr>
							<td>								
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
											<td colspan="6">											
											<input id="tituloDetObs" readonly="readonly" style="border:none; width:100%; 
											       background:#efefef;font-weight:bold; font-size: 12px"  class="net-titulo">
											</td>
										</tr>
										<tr height="10px">
											<td colspan="6"></td>
										</tr>
										<tr>
											<td style="width:160px"><label style="font-size: 12px; font-weight:bold">Distribuidora Eléctrica:</label>
											</td>
											<td>											  
											  <input id="empresaObs" readonly="readonly" style="border:none; background:#efefef;">										  					  
											</td>
											
											<td style="width:160px"><label style="font-size: 12px; font-weight:bold">Año Declarado:</label></td>
											<td>											  
											  <input id="anioObs" readonly="readonly" style="border:none; background:#efefef;">
											</td>
											
											<td style="width:160px"><label style="font-size: 12px; font-weight:bold">Mes Declarado:</label></td>
											<td>											 
											  <input id="mesObs" readonly="readonly" style="border:none; background:#efefef;">	
											</td>
											
											<td><label style="font-size: 12px; font-weight:bold">Etapa:</label></td>
											<td>										  
											  <input id="etapaObs" readonly="readonly" style="border:none; background:#efefef;">	 	
											</td>
											
										</tr>										
										<tr height="10px">
											<td colspan="6"></td>
										</tr>
										
										<tr>
											<td><label style="font-size: 12px; font-weight:bold">Año Ejecución:</label></td>
											<td>										  
											  <input id="anioEjecObs" readonly="readonly" style="border:none; background:#efefef;">	 	
											</td>
											
											<td><label style="font-size: 12px; font-weight:bold">Mes Ejecución:</label></td>
											<td>										  
											  <input id="mesEjecObs" readonly="readonly" style="border:none; background:#efefef;">	 	
											</td>
											
											<td><label style="font-size: 12px; font-weight:bold">Año Inicio Vigencia:</label></td>
											<td>										  
											  <input id="AnioIniVigObs" readonly="readonly" style="border:none; background:#efefef;">	 	
											</td>
											
											<td><label style="font-size: 12px; font-weight:bold">Año Fin Vigencia:</label></td>
											<td>										 
											  <input id="AnioFinVigObs" readonly="readonly" style="border:none; background:#efefef;">	 	 	
											</td>
											
										</tr>
										
																			
										<tr height="10px">
											<td colspan="6"></td>
										</tr>
										
										<tr>
											<td><label style="font-size: 12px; font-weight:bold">Zona:</label></td>
											<td>						 
											  <input id="zonaObs" readonly="readonly" style="border:none; background:#efefef;"> 	
											</td>
											
											<td><label style="font-size: 12px; font-weight:bold">Etapa Ejecución:</label></td>
											<td>											 
											  <input id="etapaEjecObs" readonly="readonly" style="border:none; background:#efefef;">	
											</td>
											
											<td><label style="font-size: 12px; font-weight:bold">Num. Item Etapa:</label></td>
											<td>											  
											  <input id="itemEtapaObs" readonly="readonly" style="border:none; background:#efefef;">	
											</td>
											
											<td><label style="font-size: 12px; font-weight:bold">Codigo Ubigeo:</label></td>
											<td>											  
											  <input id="ubigeoObs" readonly="readonly" style="border:none; background:#efefef;">	
											</td>
											
										</tr>	
										
										<tr height="10px">
											<td colspan="6"></td>
										</tr>
										
										<tr>
											<td><label style="font-size: 12px; font-weight:bold">Sector Típico:</label></td>
											<td>						 
											  <input id="sectorObs" readonly="readonly" style="border:none; background:#efefef;"> 	
											</td>
											
											<td><label style="font-size: 12px; font-weight:bold">Tipo Personal:</label></td>
											<td>											 
											  <input id="tipoPersonalObs" readonly="readonly" style="border:none; background:#efefef;">	
											</td>
											
											<td></td>
											<td>											  
											 
											</td>
											
											<td></td>
											<td>										  
											 
											</td>
											
										</tr>					
											
																										
									</table>
								</fieldset>
							</td>
						</tr>
						<tr height="10px">
							<td></td>
						</tr>
						<tr>
							<td>
							    <div id="<portlet:namespace/>div_grilla_obs_12AB_14AB" style="display: none;"> 
							    
							    <table id="<portlet:namespace/>grid_resultado_obs_12AB_14AB">
								</table>
								<div id="<portlet:namespace/>paginador_resultado_obs_12AB_14AB"></div>
							    
							    </div>
							    
							    <div id="<portlet:namespace/>div_grilla_obs_F12CD" style="display: none;">
							    
							    <table id="<portlet:namespace/>grid_resultado_obs_12CD">
								</table>
								<div id="<portlet:namespace/>paginador_resultado_obs_12CD"></div>
							    
							    </div>
							    
							    <div id="<portlet:namespace/>div_grilla_obs_F13A" style="display: none;">
							    
							    <table id="<portlet:namespace/>grid_resultado_obs_13A">
								</table>
								<div id="<portlet:namespace/>paginador_resultado_obs_13A"></div>
							    
							    </div>	   
							    
							    <div id="<portlet:namespace/>div_grilla_obs_F14C" style="display: none;">
							    
							    <table id="<portlet:namespace/>grid_resultado_obs_14C">
								</table>
								<div id="<portlet:namespace/>paginador_resultado_obs_14C"></div>
							    
							    </div>
								
							</td>
						</tr>
						<tr height="10px">
							<td></td>
						</tr>
						
						<tr>
							<td>
								<table style="width: 100%;" border="0">
									<tr>	
									    <td align="center" width="90px">											
												<input type="button" class="net-button-small"
													id="<portlet:namespace/>btnNuevoObservacion"
													name="<portlet:namespace/>btnNuevoObservacion" value="Agregar Obs. Manual" />
											
										</td>
										<td align="center" width="90px">											
												<input type="button" class="net-button-small"
													id="<portlet:namespace/>btnNuevoObservacionExist"
													name="<portlet:namespace/>btnNuevoObservacionExist" value="Agregar Obs. Existente" />
											
										</td>													
										 <td align="center" width="90px">									    
												<input type="button" class="net-button-small"
													id="<portlet:namespace/>btnRegresarDetalle"
													name="<portlet:namespace/>btnRegresarDetalle" value="Regresar" />																				
										</td>
									</tr>
								</table>
							</td>
						</tr>
						
						<tr height="10px">
							<td></td>
						</tr>
																
					</table>			
				</div>								
				<!-- FIN DIV PARA PRESENTAR LAS OBS.  POR CADA DETALLE SELECCIONADO -->
				
								
								
				<!-- DIV PARA NUEVO REGISTRO DE UNA OBSERVACION MANUAL-->	
				
				<div id="<portlet:namespace/>div_nuevo_observacion" class="net-frame-border" style="display: none;">			
					
					<table border="0" width="100%">
						<tr>
							<td>
								<fieldset class="">
	
									<table class="" border="0" width="100%">
									   <tr class="filete-bottom">
											<td><output class="net-titulo">
											      Ingreso de Observaciones Manuales</output></td>
										</tr>
										<tr height="10px">
											<td></td>
										</tr>					
										<tr>
									     <td>											
										   <table style="width: 100%;" border="0">
											  <tr>										   
											   <td><label style="font-size: 12px; font-weight: bold">Descripción Observación:</label>
											   </td>
											   <td>
												 <form:textarea path="desObservacion" cols="80" rows="4" />  
											   </td>								   
										     </tr>						
										  </table>								 								
										 </td>
										</tr>																										
										<tr height="10px" class="filete-bottom">
										  <td></td> 
										</tr>																																														
										<tr class="filete-top">
										<tr height="10px">
										   <td></td> 
										</tr>	
										<tr>
										  <td>
											<table style="width: 100%">
											  <tr>												
												 <td>
												  <table style="width: 100%">
												    <tr>													  
													 <td width="17%" align="center">													  
														  <input type="button" class="net-button-small" id="<portlet:namespace/>btnActualizarObservacion"
															   name="<portlet:namespace/>btnActualizarObservacion" value="Actualizar" style="display: none;"/>
																												
														<input type="button" class="net-button-small" id="<portlet:namespace/>btnGrabarObservacion"
															   name="<portlet:namespace/>btnGrabarObservacion" value="Grabar" />									   
													 </td>													
													 <td width="17%" align="center">
														<input type="button" class="net-button-small" id="<portlet:namespace/>btnRegresarObs" 
															  name="<portlet:namespace/>btnRegresarObs" value="Regresar" />
													 </td>
												 </tr>
											  </table>
											</td>
											</tr>
									    </table> 
										</td>
										</tr>
										<tr height="10px" class="filete-bottom">
											<td></td>
										</tr>
										<tr height="10px">
										 <td></td>
										</tr>							
									</table>	
								</fieldset>
							</td>
						</tr>
						<tr>
						 <td></td>
						</tr>
					</table>	
				</div>				
				
				<!-- FIN DIV PARA NUEVO REGISTRO -->
				
				
				<!-- DIV PARA NUEVO REGISTRO DE UNA OBSERVACION EXISTENTE EN EL CATALOGO-->	
				
				<div id="<portlet:namespace/>div_nuevo_observacion_existente" class="net-frame-listado" style="display: none;">			
								
			      <table style="width: 100%;" border="0">
						<tr>
							<td>
								
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
											<td colspan="4"><output class="net-titulo">Criterios
													de Búsqueda:</output>
											</td>
										</tr>
										<tr height="10px">
											<td colspan="4"></td>
										</tr>
										<tr>
											<td><output>Id Observación:</output></td>
											<td>
											 <form:input path="idObsBusq" size="10"/>		
											</td>
											
											<td><output>Descripción:</output></td>
											<td>
											 <form:input path="descObsBusq" size="30"/>		
											</td>
											
										</tr>										
										<tr height="10px">
											<td colspan="4"></td>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td></td>											
											<td align="right"><input name="<portlet:namespace/>btnBuscarObsExist"
												id="<portlet:namespace/>btnBuscarObsExist" type="button"
												class="net-button-small" value="Buscar" />
											</td>
										</tr>
									</table>
								</fieldset>
							</td>
						</tr>
						<tr height="10px">
							<td></td>
						</tr>
						<tr>
							<td>
								<table id="<portlet:namespace/>grid_resultado_busqueda_obs_exist">
								</table>
								<div id="<portlet:namespace/>paginador_resultado_busqueda_obs_exist"></div>
							</td>
						</tr>
						<tr height="10px">
							<td></td>
						</tr>
						<tr>
							<td>
								<table style="width: 100%;" border="0">
									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td align="right" width="90px">											
												<input type="button" class="net-button-small"
													id="<portlet:namespace/>btnRegresarObsExist"
													name="<portlet:namespace/>btnRegresarObsExist" value="Regresar" />											
										</td>
										<td align="right" width="90px">											
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr height="10px">
							<td></td>
						</tr>
					</table>				
				</div>				
				
				<!-- FIN DIV PARA NUEVO REGISTRO DE UNA OBSERVACION EXISTENTE EN EL CATALOGO-->						
				
								
            </div>
       </div>       
   </div>
   
   
     <!-- DIALOGO PARA MOSTRAR OBSERVACIONES -->	
		<div id="<portlet:namespace/>dialog-form-observacion" class="net-frame-border"
					style="display: none; background: #fff;"
					title=" Resultados de validación ">
					<fieldset class="net-frame-border">
						<table id="<portlet:namespace/>grid_observacion" width="100%">
						</table>
						<div id="<portlet:namespace/>pager_observacion"></div>
					</fieldset>
					<br>
		</div>	
		
		<div id="<portlet:namespace/>dialog-form-observacion12" class="net-frame-border"
					style="display: none; background: #fff;"
					title=" Resultados de validación ">
					<fieldset class="net-frame-border">
						<table id="<portlet:namespace/>grid_observacion12" width="100%">
						</table>
						<div id="<portlet:namespace/>pager_observacion12"></div>
					</fieldset>
					<br>
		</div>	
		
		<div id="<portlet:namespace/>dialog-form-observacion13" class="net-frame-border"
					style="display: none; background: #fff;"
					title=" Resultados de validación ">
					<fieldset class="net-frame-border">
						<table id="<portlet:namespace/>grid_observacion13" width="100%">
						</table>
						<div id="<portlet:namespace/>pager_observacion13"></div>
					</fieldset>
					<br>
		</div>	
	<!--FIN DIALOGO PARA MOSTRAR OBSERVACIONES -->	

   <!-- DIVS PARA MENSAJES -->
   
    <!-- DIALOGO PARA INFORMACION -->
   
   	<div id="<portlet:namespace/>dialog-message-notificacion" title="Mensaje de &Eacute;xito">
		<p>
			<img src="/fise-projects-portlet/images/success.png" style="float:left; margin:20px 25px 20px 5px;">	
			<br/>
			<label class="labelCentrado" id="<portlet:namespace/>dialog-message-content-notificacion">Datos notificados exit&oacute;samente.</label>
		</p>	
	</div>
	
	<!-- DIALOGO PARA CONFIRMACION -->
	
	<div id="<portlet:namespace/>dialog-confirm_notificacion" title="Mensaje de Confirmaci&oacute;n">
		<p>	
			<img src="/fise-projects-portlet/images/confirm.png" style="float:left; margin:20px 25px 20px 5px;">	
			<br/>
			<label class="labelCentrado" id="<portlet:namespace/>dialog-confirm-content_notificacion">¿Está seguro?</label>
		</p>
	</div>
	
	
	<div id="<portlet:namespace/>dialog-confirm" title="Mensaje de Confirmaci&oacute;n">
		<p>	
			<img src="/fise-projects-portlet/images/confirm.png" style="float:left; margin:20px 25px 20px 5px;">	
			<br/>
			<label class="labelCentrado" id="<portlet:namespace/>dialog-confirm-content">¿Está seguro?</label>
		</p>
	</div>
	
	<div id="<portlet:namespace/>dialog-confirm_observacion" title="Mensaje de Confirmaci&oacute;n">
		<p>	
			<img src="/fise-projects-portlet/images/confirm.png" style="float:left; margin:20px 25px 20px 5px;">	
			<br/>
			<label class="labelCentrado" id="<portlet:namespace/>dialog-confirm-content_observacion">¿Está seguro?</label>
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
		
	<!-- HIDEN PARA MANTENER LA PK DE LOS DETALLES DE CADA FORMATO -->	
	<input id="hiddenCodEmpresa" style="display: none"> 
	<input id="hiddenAnioPres" style="display: none"> 
	<input id="hiddenMesPres" style="display: none"> 
	<input id="hiddenEtapa" style="display: none"> 
	<input id="hiddenAnioEjec" style="display: none"> 
	<input id="hiddenMesEjec" style="display: none"> 
	<input id="hiddenAnioIniVig" style="display: none"> 
	<input id="hiddenAnioFinVig" style="display: none"> 
	<input id="hiddenEtapaEjec" style="display: none"> 
	<input id="hiddenIdZona" style="display: none"> 
	<input id="hiddenIdPersonal" style="display: none"> 
	<input id="hiddenItemEtapa" style="display: none"> 
	<input id="hiddenCodUbigeo" style="display: none"> 
	<input id="hiddenCodSector" style="display: none"> 	
	
	<input id="hiddenTipoBusqueda" style="display: none">
	
	<input id="hiddenIdObservacion" style="display: none"> 	 	
	
	
</form:form>