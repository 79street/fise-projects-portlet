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
												<form:select path="etapaBusq" cssClass="select" cssStyle="width: 140px;">
													<form:option value="SOLICITUD">SOLICITUD</form:option>
													<form:option value="LEV.OBS">LEV.OBS</form:option>
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
												class="net-button-small" value="Procesar" style="aling:center" />
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
   
   	<div id="<portlet:namespace/>dialog-message-notificacion" title="Osinergmin">
		<p>
			<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;">
			</span>
			<label id="<portlet:namespace/>dialog-message-content-notificacion">Datos notificados exit&oacute;samente.</label>
		</p>	
	</div>
	
	<div id="<portlet:namespace/>dialog-confirm_notificacion" title="Confirmar acci&oacute;n">
		<p>	
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
			<label id="<portlet:namespace/>dialog-confirm-content_notificacion">¿Está seguro?</label>
		</p>
	</div>`	
	
	
	<div id="<portlet:namespace/>dialog-confirm" title="Confirmar acci&oacute;n">
		<p>	
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
			<label id="<portlet:namespace/>dialog-confirm-content">¿Está seguro?</label>
		</p>
	</div>`	
		
	
</form:form>