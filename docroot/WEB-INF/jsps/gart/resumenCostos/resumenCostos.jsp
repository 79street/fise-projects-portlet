<%@include file="/WEB-INF/jsps/gart/ext/extResumenCostos.jsp"%>


<link href="/fise-projects-portlet/css/tablas.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/fise-projects-portlet/js/fise.js"></script>



<script type="text/javascript">
$(document).ready(function () {	
	resumenCosto.init();
});
</script>
           
           
<form:form method="POST" style="padding:17px;padding-top:0px;" 
            modelAttribute="resumenCostoBean">
            
            
            	
<div id="d_filtro" class="net-frame-listado"> 
		<div id="d_filtro">
			<div id="div_contenido" >
			
			    <!-- DIV PARA BUSQUEDA  -->
			  			
				<div id="<portlet:namespace/>div_buscar" class="net-frame-listado" >
				
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
												    <form:options items="${resumenCostoBean.listaGrupoInf}"  itemLabel="descripcion" itemValue="idGrupoInformacion"/>
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
													<form:option value="TODOS">-Todos-</form:option>
													<form:options items="${resumenCostoBean.listaEmpresas}"  itemLabel="dscEmpresa" itemValue="codEmpresa"/>
												</form:select>
											</td>
																																				
										</tr>
										<tr height="10px">
											<td colspan="2"></td>
										</tr>
										
										<tr>
										    <td >										  										   
											</td>										
											<td> 
											 <div id="<portlet:namespace/>div_F14A"> 
											     <input name="<portlet:namespace/>btnF14A"
												   id="<portlet:namespace/>btnF14A" type="button"
												   class="net-button-small" value="F14A" style="aling:center" />
											   </div>
											   
											   <div id="<portlet:namespace/>div_F12A">										     
											     <input name="<portlet:namespace/>btnF12A"
												   id="<portlet:namespace/>btnF12A" type="button"
												   class="net-button-small" value="F12A" style="aling:center" />
												   
												    &nbsp;&nbsp;&nbsp;	
											     <input name="<portlet:namespace/>btnF12B"
												   id="<portlet:namespace/>btnF12B" type="button"
												   class="net-button-small" value="F12B" style="aling:center" />
												   
											   </div>										   
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
				
				<!-- FIN DE DIV  PARA BUSQUEDA -->			
								
            </div>
       </div>       
</div> 

   <!-- DIVS PARA MENSAJES -->
   
   	<div id="<portlet:namespace/>dialog-message-resumen_costos" title="Mensaje de Informaci&oacute;n">
		<p>
			<img src="/fise-projects-portlet/images/success.png" style="float:left; margin:0 25px 10px 0;">	
			<label id="<portlet:namespace/>dialog-message-content-resumen_costos">Datos eliminados exit&oacute;samente.</label>
		</p>	
	</div>
	
	<div id="<portlet:namespace/>dialog-confirm-eliminar" title="Mensaje de Confirmaci&oacute;n">
		<p>	
			<img src="/fise-projects-portlet/images/confirm.png" style="float:left; margin:0 25px 10px 0;">	
			<label id="<portlet:namespace/>dialog-confirm-content-eliminar">¿Está seguro?</label>
		</p>
	</div>
	
	<!-- DIALOGO PARA ALERTAR DE VALIDACION -->
	
	<div id="<portlet:namespace/>dialog-alert" title="Mensaje de Validaci&oacute;n">
		<p>	
			<img src="/fise-projects-portlet/images/warning.png" style="float:left; margin:0 25px 10px 0;">
			<label id="<portlet:namespace/>dialog-alert-content">Debe Ingresar..</label>
		</p>
	</div>	
	
	<!-- DIALOGO PARA ERRORES -->
	
	<div id="<portlet:namespace/>dialog-error" title="Mensaje de Error">
		<p>	
			<img src="/fise-projects-portlet/images/error.png" style="float:left; margin:0 25px 10px 0;">
			<label id="<portlet:namespace/>dialog-error-content">Error..</label>
		</p>
	</div>	
	
	<!-- DIALOGO PARA INFORMAR UN MENSAJE -->
	
	<div id="<portlet:namespace/>dialog-info" title="Mensaje de Informaci&oacute;n">
		<p>	
			<img src="/fise-projects-portlet/images/info.png" style="float:left; margin:0 25px 10px 0;">
			<label id="<portlet:namespace/>dialog-info-content">Error..</label>
		</p>
	</div>	
		
		
	
</form:form>