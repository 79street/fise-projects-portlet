<%@include file="/WEB-INF/jsps/gart/ext/extParametros.jsp"%>


<link href="/fise-projects-portlet/css/tablas.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/fise-projects-portlet/js/fise.js"></script>



<script type="text/javascript">
$(document).ready(function () {	
	fiseParametros.init();
});
</script>
           
           
<form:form method="POST" style="padding:17px;padding-top:0px;" 
            modelAttribute="fiseParametroBean">
            
            
            	
<div id="d_filtro" class="net-frame-listado"> 
		<div id="d_filtro">
			<div id="div_contenido" >
			
			  <!-- DIV PARA BUSQUEDA  -->	
			
				<div id="<portlet:namespace/>div_buscar" class="net-frame-listado">
				
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
											<td colspan="4"><output class="net-titulo">Criterios
													de B�squeda:</output>
											</td>
										</tr>
										<tr height="10px">
											<td colspan="4"></td>
										</tr>
										<tr>
											<td><output>C�digo:</output></td>
											<td>
											 <form:input path="codigoBusq" size="20"/>		
											</td>
											
											<td><output>Nombre:</output></td>
											<td>
											 <form:input path="nombreBusq" size="50"/>		
											</td>
											
										</tr>										
										<tr height="10px">
											<td colspan="4"></td>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td></td>											
											<td><input name="<portlet:namespace/>btnBuscarParametros"
												id="<portlet:namespace/>btnBuscarParametros" type="button"
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
													id="<portlet:namespace/>btnNuevoParametro"
													name="<portlet:namespace/>btnNuevoParametro" value="Nuevo" />
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
											<td><output class="net-titulo">PAR�METROS</output>
											</td>
										</tr>
										<tr height="10px">
											<td></td>
										</tr>					
										<tr>
									     <td>											
										   <table style="width: 100%;" border="0">
											  <tr>											  
											    <td><label style="font-size: 12px; font-weight: bold">C�digo:</label>
												</td>
											    <td>
											      <form:input path="codigo" size="30" maxlength="30" onblur="this.value=this.value.toUpperCase();" /> 
											   </td>
											 </tr>
											 <tr>
											 	<td><label style="font-size: 12px; font-weight: bold">Nombre:</label>
											   </td>
											   <td>
												 <form:textarea path="nombre" cols="30" rows="2" style="width:600px;" />  
											   </td>
											 </tr>		
											 <tr>											  
											    <td><label style="font-size: 12px; font-weight: bold">Valor:</label>
												</td>
											    <td>
											      <form:input path="valor" size="50" maxlength="30" /> 
											   </td>
											 </tr>				
											 <tr>											  
											    <td><label style="font-size: 12px; font-weight: bold">Orden:</label>
												</td>
											    <td>
											      <form:input path="orden" size="20" maxlength="5"  /> 
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
														  <input type="button" class="net-button-small" id="<portlet:namespace/>actualizarfiseParametro"
															   name="<portlet:namespace/>actualizarfiseParametro" value="Actualizar" style="display: none;"/>
																												
														<input type="button" class="net-button-small" id="<portlet:namespace/>guardarfiseParametro"
															   name="<portlet:namespace/>guardarfiseParametro" value="Grabar" />									   
													 </td>													
													 <td width="17%" align="center">
														<input type="button" class="net-button-small" id="<portlet:namespace/>regresarfiseParametro" 
															  name="<portlet:namespace/>regresarfiseParametro" value="Regresar" />
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
			<label class="labelCentrado" id="<portlet:namespace/>dialog-confirm-content">�Est� seguro?</label>
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