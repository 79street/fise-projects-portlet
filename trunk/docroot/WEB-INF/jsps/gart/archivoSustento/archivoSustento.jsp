<%@include file="/WEB-INF/jsps/gart/ext/extArchivoSustento.jsp"%>


<link href="/fise-projects-portlet/css/tablas.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/fise-projects-portlet/js/fise.js"></script>



<script type="text/javascript">
$(document).ready(function () {	
	archivoSustentoVar.init();
});
</script>
           
           
<form:form method="POST" style="padding:17px;padding-top:0px;" 
            modelAttribute="archivoSustentoBean">
            
            
            	
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
										    <td style="width: 180px;"><output>Periodicidad:</output></td>											
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
												<form:select path="grupoInfBusq" cssClass="select" cssStyle="width: 220px;">															
												    <form:options items="${archivoSustentoBean.listaGrupoInf}"  itemLabel="descripcion" itemValue="idGrupoInformacion"/>
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
													<form:options items="${archivoSustentoBean.listaEmpresas}"  itemLabel="dscEmpresa" itemValue="codEmpresa"/>
												</form:select>
											</td>																											
										</tr>
										<tr height="10px">
											<td colspan="2"></td>
										</tr>
										
										<tr>
											<td><output>Etapa:</output></td>
											<td>
												<form:select path="etapaBusq" cssClass="select" cssStyle="width: 220px;">
													<form:option value="SOLICITUD">SOLICITUD</form:option>
													<form:option value="LEV.OBS">LEVANTAMIENTO OBSERVACIONES</form:option>											
												</form:select>
											</td>
										</tr>
										<tr height="10px">
											<td colspan="2"></td>
										</tr>										
										<tr>										
											<td></td>											
											<td style="aling:right"><input name="<portlet:namespace/>btnBuscarFormatos"
												id="<portlet:namespace/>btnBuscarFormatos" type="button"
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
					</table>			
				</div>								
				
				<!-- FIN DE DIV  PARA BUSQUEDA -->			
								
            </div>
       </div>       
</div>
   
   
               <!-- DIV PARA BUSQUEDA DE ARCHIVOS DE SUSTENTO POR CADA FORMATO SELECCIONADO  -->	
			
				<div id="<portlet:namespace/>div_buscar_archivos" class="net-frame-border" style="display: none;">
				
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
											<td colspan="6">											
											<input id="tituloBusquedaArchivo" readonly="readonly" style="border:none; width:100%; 
											       background:#efefef;font-weight:bold; font-size: 14px"  class="net-titulo">
											</td>
										</tr>
										<tr height="10px">
											<td colspan="6"></td>
										</tr>
										<tr>
											<td style="width:160px"><label style="font-size: 12px; font-weight:bold">Distribuidora Eléctrica:</label>
											</td>
											<td>											  
											  <input id="empresaArchivo" readonly="readonly" style="border:none; background:#efefef;">										  					  
											</td>
											
											<td style="width:160px"><label style="font-size: 12px; font-weight:bold">Formato:</label></td>
											<td>											  
											  <input id="formatoArchivo" readonly="readonly" style="border:none; background:#efefef;">
											</td>
											
											<td style="width:160px"><label style="font-size: 12px; font-weight:bold">Etapa:</label></td>
											<td>											 
											  <input id="etapaArchivo" readonly="readonly" style="border:none; background:#efefef;">	
											</td>
											
										</tr>										
										<tr height="10px">
											<td colspan="6"></td>
										</tr>
										
										<tr>
											<td><label style="font-size: 12px; font-weight:bold">Año Declarado:</label></td>
											<td>										  
											  <input id="anioArchivo" readonly="readonly" style="border:none; background:#efefef;">	 	
											</td>
											
											<td><label style="font-size: 12px; font-weight:bold">Mes Declarado:</label></td>
											<td>										  
											  <input id="mesArchivo" readonly="readonly" style="border:none; background:#efefef;">	 	
											</td>
											
											<td><label style="font-size: 12px; font-weight:bold">Año Ejecución:</label></td>
											<td>										 
											  <input id="anioEjecArchivo" readonly="readonly" style="border:none; background:#efefef;">	 	 	
											</td>
											
										</tr>
										
																			
										<tr height="10px">
											<td colspan="6"></td>
										</tr>
										
										<tr>
											<td><label style="font-size: 12px; font-weight:bold">Mes Ejecución:</label></td>
											<td>						 
											  <input id="mesEjecArchivo" readonly="readonly" style="border:none; background:#efefef;"> 	
											</td>
											
											<td><label style="font-size: 12px; font-weight:bold">Año Inicio Vigencia:</label></td>
											<td>											 
											  <input id="anioInicioVigArchivo" readonly="readonly" style="border:none; background:#efefef;">	
											</td>
											
											<td><label style="font-size: 12px; font-weight:bold">Año Fin Vigencia:</label></td>
											<td>											  
											  <input id="anioFinVigArchivo" readonly="readonly" style="border:none; background:#efefef;">	
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
							  <fieldset class="net-frame-border">
								<table id="<portlet:namespace/>grid_resultado_busqueda_archivos"> 
								</table>
								<div id="<portlet:namespace/>paginador_resultado_busqueda_archivos"></div>
						      </fieldset>
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
													id="<portlet:namespace/>btnNuevoArchivoSustento"
													name="<portlet:namespace/>btnNuevoArchivoSustento" value="Agregar" />
											</div>
										</td>
										<td align="right" width="90px">	
										    <div id="d_opc_regresar_liquidacion">
												<input type="button" class="net-button-small"
													id="<portlet:namespace/>btnRegresarBusqFormatos"
													name="<portlet:namespace/>btnRegresarBusqFormatos" value="Regresar" />
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
                	
				
		     <!-- DIALOGO PARA SUBIR ARCHIVOS DE SUSTENTO -->			 
			 <div id="<portlet:namespace/>dialog-form-cargaArchivos" class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable"
						style="display: none; z-index: 1002; position: absolute; width: 400px;">
					<div class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix">
						<span class="ui-dialog-title" id="ui-dialog-title-dialog-form-carga"> Cargar archivo de Sustento </span> 
						<a href="#" class="ui-dialog-titlebar-close ui-corner-all" role="button" onclick="archivoSustentoVar.regresarFormularioCargaArchivos();"> 
							<span class="ui-icon ui-icon-closethick">close</span>
						</a>
					</div>	
					<div class="ui-dialog-content ui-widget-content">
						<fieldset class="">						
							 <table style="width: 100%;">
								<tr>
									<td></td>
								</tr>
								<tr>								
								   <td>Archivo:</td>
								<td>
								    <input type="file" id="archivoSustento" name="archivoSustento" /></td>
								</tr>
								<tr>
									<td height="10px;"></td>
								</tr>
								<tr>
									<td colspan="3"><span id="msjUploadFile" style="color: red;"></span></td>
								</tr>
							</table>							
						</fieldset>
					</div>
	
					<div class="ui-dialog-buttonpane ui-widget-content ui-helper-clearfix">
						<div class="ui-dialog-buttonset">
							<input type="button" class="net-button-small" name="<portlet:namespace/>cargarArchivoSustento"
								id="<portlet:namespace/>cargarArchivoSustento" value="Cargar" /> 
							<input type="button" class="net-button-small" name="<portlet:namespace/>cerrarFormatoExcel"
								id="<portlet:namespace/>cerrarCargarArchivoSustento" value="Cerrar" onclick="archivoSustentoVar.regresarFormularioCargaArchivos();" />
						</div>
					</div>
				</div>
				
			    <!-- FIN DIALOGO PARA SUBIR ARCHIVOS DE SUSTENTO -->
			
			  		
         <div id="<portlet:namespace/>divOverlay" class="ui-widget-overlay" style="display:none;width: 100%; height: 100%; z-index: 1001;">
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
   
   	<div id="<portlet:namespace/>dialog-message-archivos" title="Mensaje de &Eacute;xito">
		<p>
			<img src="/fise-projects-portlet/images/success.png" style="float:left; margin:20px 25px 20px 5px;">
			<br/>			
			<label class="labelCentrado" id="<portlet:namespace/>dialog-message-content-archivos">Archivos subidos exit&oacute;samente.</label>
		</p>	
	</div>
	
	<div id="<portlet:namespace/>dialog-confirm-eliminar" title="Mensaje de Confirmaci&oacute;n">
		<p>	
			<img src="/fise-projects-portlet/images/confirm.png" style="float:left; margin:20px 25px 20px 5px;">	
			<br/>
			<label class="labelCentrado" id="<portlet:namespace/>dialog-confirm-content-eliminar">¿Está seguro?</label>
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
			<label class="labelCentrado" id="<portlet:namespace/>dialog-info-content">Info..</label>
		</p>
	</div>
			
			
	
</form:form>