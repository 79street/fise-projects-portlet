<%@include file="/WEB-INF/jsps/gart/ext/extPeriodoEnvio.jsp"%>


<link href="/fise-projects-portlet/css/tablas.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/fise-projects-portlet/js/fise.js"></script>



<script type="text/javascript">
$(document).ready(function () {	
	periodoEnvio.init();
});
</script>
           
           
<form:form method="POST" style="padding:17px;padding-top:0px;" 
            modelAttribute="periodoEnvioBean">
            
            
            	
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
											<td colspan="8"><output class="net-titulo">Criterios
													de Búsqueda:</output></td>
										</tr>
										<tr height="10px">
											<td colspan="8"></td>
										</tr>
										<tr>
											<td><output>Distribuidora Eléctrica:</output></td>
											<td colspan="6">
												<form:select path="codEmpresaBusq" cssClass="select" cssStyle="width: 375px;">

													<c:if test="${periodoEnvioBean.admin}">
														<form:option value="">-Todos-</form:option>
													</c:if>
													<form:options items="${periodoEnvioBean.listaEmpresas}"  itemLabel="dscEmpresa" itemValue="codEmpresa"/>
												</form:select>
											</td>
										</tr>
										<tr height="10px">
											<td colspan="8"></td>
										</tr>
										<tr>
											<td><output>Año/Mes Declarado:</output></td>
											<td>
												<form:input path="anioDesde" cssStyle="width: 50px; text-align: right;" maxlength="4"/>
											</td>
											<td><output>Mes:</output></td>
											<td>
												<form:select path="mesDesde" cssClass="select" cssStyle="width: 110px;">
													<form:option value="">-Seleccione-</form:option>
													<form:options items="${periodoEnvioBean.listaMes}"/>
												</form:select>
												
											</td>
											<td><output>Formato:</output></td>
											<td>
												<form:select path="formatoBusq" cssClass="select" cssStyle="width: 100px;">
													<form:option value="F12A">F12A</form:option>
													<form:option value="F12B">F12B</form:option>
													<form:option value="F12C">F12C</form:option>
													<form:option value="F12D">F12D</form:option>
													<form:option value="F13A">F13A</form:option>
													<form:option value="F14A">F14A</form:option>
													<form:option value="F14B">F14B</form:option>
													<form:option value="F14C">F14C</form:option>
												</form:select>
											</td>
											<td><output>Estado:</output></td>
											<td>
												<form:select path="estadoBusq" cssClass="select" cssStyle="width: 100px;">
													<form:option value="V">Vigente</form:option>
													<form:option value="A">Anulado</form:option>													
												</form:select>
											</td>
										</tr>
										<tr height="10px">
											<td colspan="8"></td>
										</tr>
										<tr>
											<td><output>Etapa:</output></td>
											<td>
												<form:select path="etapaBusq" cssClass="select" cssStyle="width: 140px;">
													<form:option value="SOLICITUD">SOLICITUD</form:option>
													<form:option value="LEV.OBS">LEV.OBS</form:option>													
													<form:option value="RECONOCIDO">RECONOCIDO</form:option>
													<form:option value="ESTABLECIDO">ESTABLECIDO</form:option>
													<form:option value="RECONSIDERACION">RECONSIDERACION</form:option>
												</form:select>
											</td>
											<td><output>Habilitado Envio:</output></td>
											<td colspan="4">
												<form:select path="flagEnvioBusq" cssClass="select" cssStyle="width: 110px;">
													<form:option value="0">Todos</form:option>
													<form:option value="1">SI</form:option>
													<form:option value="2">NO</form:option>													
												</form:select>
											</td>
										</tr>
										<tr height="10px">
											<td colspan="8"></td>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td><input name="<portlet:namespace/>btnBuscarPerdioEnvio"
												id="<portlet:namespace/>btnBuscarPerdioEnvio" type="button"
												class="net-button-small" value="Buscar" style="aling: center" />
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
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td align="right" width="90px">
											<div id="d_opc_crear">
												<input type="button" class="net-button-small"
													id="<portlet:namespace/>btnNuevoPeridoEnvio"
													name="<portlet:namespace/>btnNuevoPeridoEnvio" value="Nuevo" />
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
				
				<!-- DIV PARA NUEVO REGISTRO -->	
				
				<div id="<portlet:namespace/>div_nuevo" class="net-frame-border" style="display: none;">			
					
					<table border="0" width="100%">
						<tr>
							<td>
								<fieldset class="">	                               
									<table class="" border="0" width="100%">
									   <tr class="filete-bottom">
											<td><output class="net-titulo">DETALLE DE CONTROL DE REMISIÓN</output>
											</td>
										</tr>
										<tr height="10px">
											<td></td>
										</tr>					
										<tr>
									     <td>											
										   <table style="width: 100%;" border="0">
											  <tr>
											    <td><label style="font-size: 12px; font-weight: bold">Distribuidora	Eléctrica:</label>
												</td>
											    <td>
											       <form:select path="codEmpresa" cssClass="select" cssStyle="width: 200px;">
													 <form:options items="${periodoEnvioBean.listaEmpresas}"
																   itemLabel="dscEmpresa" itemValue="codEmpresa" />
												  </form:select>
											   </td>
											   
											   <td><label style="font-size: 12px; font-weight: bold">Año Decl.:</label>
											   </td>
											   <td>
												<form:input path="anioPres"  cssStyle="width: 50px; text-align: right;" maxlength="4" onKeyPress="return isNumeric(event)"/>
											   </td>
											   
											   <td><label style="font-size: 12px; font-weight: bold">Mes Decl.:</label>
											   </td>
											   <td>
												 <form:select path="mesPres" cssClass="select" cssStyle="width: 104px;">
													<form:option value="">-Seleccione-</form:option>
													<form:options items="${periodoEnvioBean.listaMes}"/>
												 </form:select>
											   </td>
											   
										     </tr>
										     
										     <tr height="10px">
										      <td colspan="6"></td> 
										     </tr>
										     
										     <tr>
											   <td><label style="font-size: 12px; font-weight: bold">Formato:</label>
											   </td>
											   <td>
												 <form:select path="formato" cssClass="select" cssStyle="width: 100px;">
													<form:option value="F12A">F12A</form:option>
													<form:option value="F12B">F12B</form:option>
													<form:option value="F12C">F12C</form:option>
													<form:option value="F12D">F12D</form:option>
													<form:option value="F13A">F13A</form:option>
													<form:option value="F14A">F14A</form:option>
													<form:option value="F14B">F14B</form:option>
													<form:option value="F14C">F14C</form:option>
												</form:select>
											   </td>
											   
											   <td><label style="font-size: 12px; font-weight: bold">Etapa:</label>
											   </td>
											   <td>
												<form:select path="etapa" cssClass="select" cssStyle="width: 140px;">																																								
												</form:select>
											   </td>
											   
											   <td><label style="font-size: 12px; font-weight: bold">Estado:</label>
											   </td>
											   <td>
												<form:select path="estado" cssClass="select" cssStyle="width: 104px;">
													<form:option value="V">Vigente</form:option>
													<form:option value="A">Anulado</form:option>													
												</form:select>
											   </td>
											   
											</tr>
											
											<tr height="10px">
										      <td colspan="6"></td> 
										     </tr>
										     									
											<tr>
											  <td><label style="font-size: 12px; font-weight: bold">Desde:(dd/mm/aaaa)</label>
											  </td>
											  <td>											   
											    <form:input path="desde" /> 								
											  </td>
											
											  <td><label style="font-size: 12px; font-weight: bold">Hasta: (dd/mm/aaaa)</label>
											  </td>
											  <td>											  										 
											   <form:input path="hasta" />								    												
											  </td>
											  
											  <td><label style="font-size: 12px; font-weight: bold">Fecha Ampliada: (dd/mm/aaaa)</label>
											  </td>
											  <td>											  										 
											   <form:input path="fechaAmpl" cssStyle="width: 104px;"/>								    												
											  </td>
											  								
											</tr>
											
											 <tr height="10px">
										      <td colspan="6"></td> 
										     </tr>
																						
											<tr>
											 <td><label style="font-size: 12px; font-weight: bold">Envío Def. con Obs:</label>
											 </td>
											 <td>											 
											     <input type="radio"	name="flagEnvioObs"
												       id="rbtEnvioSi" value="S" checked="true"/>SI
												 &nbsp;&nbsp;&nbsp;	
												 
												<input type="radio"	name="flagEnvioObs"
												       id="rbtEnvioNo" value="N" />NO	
											     										
											 </td>
											 
											 <td><label style="font-size: 12px; font-weight: bold">Habilita Año/Mes Ejecución(F12):</label>
											 </td>
											 <td>											
												<input type="radio"	name="flagAnioMesEjec"
												       id="rbtMesSi" value="S" checked="true"/>SI
												 &nbsp;&nbsp;&nbsp;	
												 
												<input type="radio"	name="flagAnioMesEjec"
												       id="rbtMesNo" value="N" />NO						 
											  							
											 </td>	
											 
											 <td><label style="font-size: 12px; font-weight: bold">Habilita Costos(F14C):</label>
											 </td>
											 <td width="190px;"> 												     
											     <input type="radio"	name="flagHabCostos"
												        id="rbtAmbos" value="A" checked="true"/>Ambos
												 &nbsp;&nbsp;&nbsp;													 
												<input type="radio"	name="flagHabCostos"
												       id="rbtDirecto" value="D" />Directo
												 &nbsp;&nbsp;&nbsp;	
												<input type="radio"	name="flagHabCostos"
												       id="rbtIndirecto" value="I"/>Indirecto																					
											 </td>	
											 
											</tr>
											
											 <tr height="10px">
										      <td colspan="6"></td> 
										     </tr>
											
											<tr> 											
											 <td>
											     <label style="font-size: 12px; font-weight: bold">Año Ini. Vig.:</label>
											 </td>
											 <td>
											    <form:input path="anoIniVigencia"  cssStyle="width: 50px; text-align: right;" maxlength="4" onKeyPress="return isNumeric(event)"/>									
											 </td>
											 
											 <td>
											     <label style="font-size: 12px; font-weight: bold">Año Fin Vig.:</label>
											 </td>
											 <td>
											   <form:input path="anoFinVigencia"  cssStyle="width: 50px; text-align: right;" maxlength="4" onKeyPress="return isNumeric(event)"/>								
											 </td>											 
											 
											 <td>
											     <label style="font-size: 12px; font-weight: bold">Días a notificar antes de cierre:</label>
											  </td>
											  <td>											   
											     <form:input path="diasNotifCierre"  cssStyle="width: 50px; text-align: right;" maxlength="2" onKeyPress="return isNumeric(event)"/>											
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
														  <input type="button" class="net-button-small" id="<portlet:namespace/>actualizarPeriodoEnvio"
															   name="<portlet:namespace/>actualizarPeriodoEnvio" value="Actualizar" style="display: none;"/>
																												
														<input type="button" class="net-button-small" id="<portlet:namespace/>guardarPeriodoEnvio"
															   name="<portlet:namespace/>guardarPeriodoEnvio" value="Grabar" />									   
													 </td>													
													 <td width="17%" align="center">
														<input type="button" class="net-button-small" id="<portlet:namespace/>regresarPeriodoEnvio" 
															  name="<portlet:namespace/>regresarPeriodoEnvio" value="Regresar" />
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

   <!-- DIVS PARA MENSAJES -->
	
	<div id="<portlet:namespace/>dialog-message-grabar" title="Mensaje de &Eacute;xito">
		<p>
			<img src="/fise-projects-portlet/images/success.png" style="float:left; margin:20px 25px 20px 5px;">	
			<br/>
			<label class="labelCentrado" id="<portlet:namespace/>dialog-message-content-grabar">Datos grabados exit&oacute;samente.</label>
		</p>	
	</div>
	
	<div id="<portlet:namespace/>dialog-confirm" title="Mensaje de Confirmaci&oacute;n">
		<p>	
			<img src="/fise-projects-portlet/images/confirm.png" style="float:left; margin:20px 25px 20px 5px;">	
			<br/>
			<label class="labelCentrado" id="<portlet:namespace/>dialog-confirm-content">¿Está seguro?</label>
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
	
	
	<!-- HIDENN -->
	<form:input path="secuencial" cssStyle="display:none;" />	


</form:form>