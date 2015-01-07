<%@include file="/WEB-INF/jsps/gart/ext/extLiquidacion.jsp"%>


<link href="/fise-projects-portlet/css/tablas.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/fise-projects-portlet/js/fise.js"></script>



<script type="text/javascript">
$(document).ready(function () {	
	liquidacionVar.init();
});
</script>
           
           
<form:form method="POST" style="padding:17px;padding-top:0px;" 
            modelAttribute="liquidacionBean">
            
            
            	
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
											<td colspan="2"><output class="net-titulo">Criterios
													de Búsqueda:</output></td>
										</tr>
										<tr height="10px">
											<td colspan="2"></td>
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
										</tr>
										<tr height="10px">
											<td colspan="2"></td>
										</tr>
										<tr>										
											<td><output>Grupo Información:</output></td>
											<td>												
												 <form:select path="grupoInfBusq" cssClass="select" cssStyle="width: 200px;">															
												    <form:options items="${liquidacionBean.listaGrupoInf}"  itemLabel="descripcion" itemValue="idGrupoInformacion"/>
												</form:select>													
											</td>		
																													
										</tr>
										<tr height="10px">
											<td colspan="2"></td>
										</tr>
										<tr>										
											<td><output>Distribuidora Eléctrica:</output></td>
											<td>
												<form:select path="codEmpresaBusq" cssClass="select" cssStyle="width: 375px;">												
													<form:option value="TODO">-Todos-</form:option>
													<form:options items="${liquidacionBean.listaEmpresas}"  itemLabel="dscEmpresa" itemValue="codEmpresa"/>
												</form:select>
											</td>																											
										</tr>
										<tr height="10px">
											<td colspan="2"></td>
										</tr>
										
										<tr>										
											<td></td>											
											<td style="aling:right"><input name="<portlet:namespace/>btnBuscarLiquidacion"
												id="<portlet:namespace/>btnBuscarLiquidacion" type="button"
												class="net-button-small" value="Buscar" style="aling:center" />
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
										<td align="right">	
										    <input type="button" class="net-button-small"
											      id="<portlet:namespace/>btnGenerarEtapa"
											      name="<portlet:namespace/>btnGenerarEtapa" value="" />										
										</td>
										<td align="center">											
										   <input type="button" class="net-button-small"
											      id="<portlet:namespace/>btnLiquidar"
											      name="<portlet:namespace/>btnLiquidar" value="" />											
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
				
				
				 <!-- DIV PARA BUSQUEDA MOTIVOS DE LIQUIDACION  -->	
			
				<div id="<portlet:namespace/>div_buscar_motivo" class="net-frame-listado" style="display: none;">
				
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
											<td colspan="6"><output class="net-titulo">Detalle de la Liquidación</output>
											</td>
										</tr>
										<tr height="10px">
											<td colspan="6"></td>
										</tr>
										<tr>
											<td><label style="font-size: 12px; font-weight:bold">Distribuidora Eléctrica:</label>
											</td>
											<td>											  
											  <output id="empresaMotivo"></output>									  
											</td>
											
											<td><label style="font-size: 12px; font-weight:bold">Formato:</label></td>
											<td>
											  <output id="formatoMotivo"></output>	
											</td>
											
											<td><label style="font-size: 12px; font-weight:bold">Etapa Origen:</label></td>
											<td>
											  <output id="etapaOrigenMotivo"></output>	
											</td>
											
										</tr>										
										<tr height="10px">
											<td colspan="6"></td>
										</tr>
										
										<tr>
											<td><label style="font-size: 12px; font-weight:bold">Año Declarado:</label></td>
											<td>											 	
											  <output id="anioMotivo"></output> 	
											</td>
											
											<td><label style="font-size: 12px; font-weight:bold">Mes Declarado:</label></td>
											<td>
											  <output id="mesMotivo"></output> 	
											</td>
											
											<td><label style="font-size: 12px; font-weight:bold">Año Ejecución:</label></td>
											<td>
											  <output id="anioEjecMotivo"></output> 	
											</td>
											
										</tr>
										
																			
										<tr height="10px">
											<td colspan="6"></td>
										</tr>
										
										<tr>
											<td><label style="font-size: 12px; font-weight:bold">Mes Ejecución:</label></td>
											<td>											 	
											  <output id="mesEjecMotivo"></output> 	
											</td>
											
											<td><label style="font-size: 12px; font-weight:bold">Año Inicio Vigencia:</label></td>
											<td>
											  <output id="anioInicioVigMotivo"></output> 	
											</td>
											
											<td><label style="font-size: 12px; font-weight:bold">Año Fin Vigencia:</label></td>
											<td>
											  <output id="anioFinVigMotivo"></output> 	
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
								<table id="<portlet:namespace/>grid_resultado_busqueda_motivos" style="width:100%"> 
								</table>
								<div id="<portlet:namespace/>paginador_resultado_busqueda_motivos"></div>
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
										<td align="right" width="90px">
											<div id="d_opc_crear_motivo">
												<input type="button" class="net-button-small"
													id="<portlet:namespace/>btnNuevoMotivo"
													name="<portlet:namespace/>btnNuevoMotivo" value="Agregar" />
											</div>
										</td>
										<td align="right" width="90px">	
										    <div id="d_opc_regresar_liquidacion">
												<input type="button" class="net-button-small"
													id="<portlet:namespace/>btnRegresarLiquidacion"
													name="<portlet:namespace/>btnRegresarLiquidacion" value="Regresar" />
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
								
				<!-- FIN DE DIV  PARA BUSQUEDA MOTIVOS DE LIQUIDACION-->	
				
				<!-- DIV PARA NUEVO REGISTRO DEL MOTIVO DE LA LIQUIDACION -->	
				
				<div id="<portlet:namespace/>div_nuevo_motivo" class="net-frame-border" style="display: none;">			
					
					<table border="0" width="100%">
						<tr>
							<td>
								<fieldset class="">
	
									<table class="" border="0" width="100%">
									   <tr class="filete-bottom">
											<td><output class="net-titulo">MOTIVO LIQUIDACIÓN</output>
											</td>
										</tr>
										<tr height="10px">
											<td></td>
										</tr>					
										<tr>
									     <td>											
										   <table style="width: 100%;" border="0">
											  <tr>										   
											   <td><label style="font-size: 12px; font-weight: bold">Descripción:</label>
											   </td>
											   <td>
												 <form:textarea path="descMotivo" cols="30" rows="4" onkeypress="return soloLetras(event)"/>  
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
														  <input type="button" class="net-button-small" id="<portlet:namespace/>actualizarMotivoLiq"
															   name="<portlet:namespace/>actualizarMotivoLiq" value="Actualizar" style="display: none;"/>
																												
														<input type="button" class="net-button-small" id="<portlet:namespace/>guardarMotivoLiq"
															   name="<portlet:namespace/>guardarMotivoLiq" value="Grabar" />									   
													 </td>													
													 <td width="17%" align="center">
														<input type="button" class="net-button-small" id="<portlet:namespace/>regresarMotivoLiq" 
															  name="<portlet:namespace/>regresarMotivoLiq" value="Regresar" />
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
				
				
								
            </div>
       </div>       
   </div>
   
   
     <!-- DIALOGO PARA MOSTRAR OBSERVACIONES -->	
		<div id="<portlet:namespace/>dialog-form-observacion" class="net-frame-border"
					style="display: none; background: #fff;"
					title=" Listado de Observaciones ">
					<fieldset class="net-frame-border">
						<table id="<portlet:namespace/>grid_observacion" width="100%">
						</table>
						<div id="<portlet:namespace/>pager_observacion"></div>
					</fieldset>
					<br>
		</div>	
		
		<div id="<portlet:namespace/>dialog-form-observacion12" class="net-frame-border"
					style="display: none; background: #fff;"
					title=" Listado de Observaciones ">
					<fieldset class="net-frame-border">
						<table id="<portlet:namespace/>grid_observacion12" width="100%">
						</table>
						<div id="<portlet:namespace/>pager_observacion12"></div>
					</fieldset>
					<br>
		</div>	
		
		<div id="<portlet:namespace/>dialog-form-observacion13" class="net-frame-border"
					style="display: none; background: #fff;"
					title=" Listado de Observaciones ">
					<fieldset class="net-frame-border">
						<table id="<portlet:namespace/>grid_observacion13" width="100%">
						</table>
						<div id="<portlet:namespace/>pager_observacion13"></div>
					</fieldset>
					<br>
		</div>	
	<!--FIN DIALOGO PARA MOSTRAR OBSERVACIONES -->	

   <!-- DIVS PARA MENSAJES -->
   
   	<div id="<portlet:namespace/>dialog-message-liquidacion" title="Mensaje de Informaci&oacute;n">
		<p>
			<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;">
			</span>
			<label id="<portlet:namespace/>dialog-message-content-liquidacion">Datos eliminados exit&oacute;samente.</label>
		</p>	
	</div>
	
	<div id="<portlet:namespace/>dialog-confirm-eliminar" title="Mensaje de Confirmaci&oacute;n">
		<p>	
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
			<label id="<portlet:namespace/>dialog-confirm-content-eliminar">¿Está seguro?</label>
		</p>
	</div>
	
	<!--  -->
	<div id="<portlet:namespace/>dialog-confirm-establecer" title="Mensaje de Confirmaci&oacute;n">
		<p>	
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
			<label id="<portlet:namespace/>dialog-confirm-content-establecer">¿Está seguro?</label>
		</p>
	</div>
	
	<div id="<portlet:namespace/>dialog-confirm-liquidar" title="Mensaje de Confirmaci&oacute;n">
		<p>	
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
			<label id="<portlet:namespace/>dialog-confirm-content-liquidar">¿Está seguro?</label>
		</p>
	</div>
		
		
		<!-- HIDENN -->
	<form:input path="itemMotivo" cssStyle="display:none;" />
	<form:input path="coMotivo" cssStyle="display:none;" />		
	
</form:form>