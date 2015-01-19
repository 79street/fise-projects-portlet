<%@include file="/WEB-INF/jsps/gart/ext/extGrupoInformacion.jsp"%>


<link href="/fise-projects-portlet/css/tablas.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/fise-projects-portlet/js/fise.js"></script>



<script type="text/javascript">
$(document).ready(function () {	
	fiseGrupoInformacion.init();
});
</script>
           
           
<form:form method="POST" style="padding:17px;padding-top:0px;" 
            modelAttribute="grupoInformacionBean">
            
            
            	
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
											<td colspan="4"><output class="net-titulo">Criterios
													de Búsqueda:</output>
											</td>
										</tr>
										
										<tr height="10px">
											<td colspan="4"></td>
										</tr>
										
										<tr>		
											<td><output>Descripción:</output></td>
											<td>
											 <form:input path="descripcionBusq" size="30" maxlength="50"/>	 	
											</td>
											
											<td></td>
											<td></td>
																						
										</tr>	
										
										<tr height="10px">
											<td colspan="4"></td>
										</tr>
										<tr>		
											<td><output>Periodicidad:</output></td>
											<td>
											   <input type="radio"	name="tipoBusq"
												       id="rbtMensualBusq" value="MENSUAL" checked="true"/>Mensual
											    &nbsp;&nbsp;&nbsp;	
												 
												<input type="radio"	name="tipoBusq"
												       id="rbtBienalBusq" value="BIENAL"/>Bienal	 	
											</td>
											
											<td><output>Estado:</output></td>
											<td>
											    <input type="radio"	name="estadoBusq"
												       id="rbtActivoBusq" value="1" checked="true"/>Activo
												 &nbsp;&nbsp;&nbsp;	
												 
												<input type="radio"	name="estadoBusq"
												       id="rbtInactivoBusq" value="0"/>Inactivo	 	
											</td>
											
										</tr>
																			
										<tr height="10px">
											<td colspan="4"></td>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td></td>											
											<td style="aling:right">
											   <input name="<portlet:namespace/>btnBuscarGrupoInf"
												id="<portlet:namespace/>btnBuscarGrupoInf" type="button"
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
										<td align="right" width="90px">
											<div id="d_opc_crear">
												<input type="button" class="net-button-small"
													id="<portlet:namespace/>btnNuevaGrupoInf"
													name="<portlet:namespace/>btnNuevaGrupoInf" value="Nuevo"/>
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
											<td><output class="net-titulo">GRUPO INFORMACIÓN</output>
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
												 <form:textarea path="descripcion" cols="30" rows="4" onkeypress="return soloLetras(event)"/>  
											   </td>								   
										     </tr>
										     
										     <tr height="10px">
											   <td colspan="4"></td> 
										     </tr>	
										     
										     <tr>									   
											   <td><label style="font-size: 12px; font-weight: bold">Periodicidad:</label>
											   </td>
											   <td>
												  <input type="radio"	name="tipo"
												       id="rbtMensual" value="MENSUAL" checked="true"/>Mensual
											       &nbsp;&nbsp;&nbsp;	
												 
												  <input type="radio"	name="tipo"
												       id="rbtBienal" value="BIENAL"/>Bienal	
											   </td>
											   <td><label style="font-size: 12px; font-weight: bold">Estado:</label>
											   </td>
											   <td>
												 <input type="radio"	name="estado"
												       id="rbtActivo" value="1" checked="true"/>Activo
												   &nbsp;&nbsp;&nbsp;	
												 
												 <input type="radio"	name="estado"
												       id="rbtInactivo" value="0"/>Inactivo	 	 
											   </td>										   								   
										     </tr>
										     <tr height="10px">
											   <td colspan="4"></td> 
										     </tr>
										     
										     <tr>
										       <td><label style="font-size: 12px; font-weight: bold">Año Decl.:</label>
											   </td>
											   <td>
												<form:input path="anioPres"  cssStyle="width: 50px; text-align: right;" maxlength="4" onblur="isNumeric(this)"/>
											   </td>
											   
											   <td><label style="font-size: 12px; font-weight: bold">Mes Decl.:</label>
											   </td>
											   <td>
												 <form:select path="mesPres" cssClass="select" cssStyle="width: 104px;">
													<form:option value="">-Seleccione-</form:option>
													<form:options items="${grupoInformacionBean.listaMes}"/>
												 </form:select>
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
														  <input type="button" class="net-button-small" id="<portlet:namespace/>actualizarGrupoInf"
															   name="<portlet:namespace/>actualizarGrupoInf" value="Actualizar" style="display: none;"/>
																												
														<input type="button" class="net-button-small" id="<portlet:namespace/>guardarGrupoInf"
															   name="<portlet:namespace/>guardarGrupoInf" value="Grabar" />									   
													 </td>													
													 <td width="17%" align="center">
														<input type="button" class="net-button-small" id="<portlet:namespace/>regresarGrupoInf" 
															  name="<portlet:namespace/>regresarGrupoInf" value="Regresar" />
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
	
	<div id="<portlet:namespace/>dialog-message-grabar" title="Mensaje de Informaci&oacute;n">
		<p>
			<img src="/fise-projects-portlet/images/success.png" style="float:left; margin:20px 25px 20px 5px;">
			<br/>
			<label id="<portlet:namespace/>dialog-message-content-grabar">Datos grabados exit&oacute;samente.</label>
		</p>	
	</div>
	
	<div id="<portlet:namespace/>dialog-confirm" title="Mensaje de Confirmaci&oacute;n">
		<p>	
			<img src="/fise-projects-portlet/images/confirm.png" style="float:left; margin:20px 25px 20px 5px;">
			<br/>
			<label id="<portlet:namespace/>dialog-confirm-content">¿Está seguro?</label>
		</p>
	</div>
	
	<!-- DIALOGO PARA ALERTAR DE VALIDACION -->
	
	<div id="<portlet:namespace/>dialog-alert" title="Mensaje de Validaci&oacute;n">
		<p>	
			<img src="/fise-projects-portlet/images/warning.png" style="float:left; margin:20px 25px 20px 5px;">
			<br/>
			<label id="<portlet:namespace/>dialog-alert-content">Debe Ingresar..</label>
		</p>
	</div>	
	
	<!-- DIALOGO PARA ERRORES -->
	
	<div id="<portlet:namespace/>dialog-error" title="Mensaje de Error">
		<p>	
			<img src="/fise-projects-portlet/images/error.png" style="float:left; margin:20px 25px 20px 5px;">
			<br/>
			<label id="<portlet:namespace/>dialog-error-content">Error..</label>
		</p>
	</div>	
	
	<!-- DIALOGO PARA INFORMAR UN MENSAJE -->
	
	<div id="<portlet:namespace/>dialog-info" title="Mensaje de Informaci&oacute;n">
		<p>	
			<img src="/fise-projects-portlet/images/info.png" style="float:left; margin:20px 25px 20px 5px;">
			<br/>
			<label id="<portlet:namespace/>dialog-info-content">Error..</label>
		</p>
	</div>
	
	
	<!-- HIDENN -->
	<form:input path="idGrupoInf" cssStyle="display:none;" />	

</form:form>