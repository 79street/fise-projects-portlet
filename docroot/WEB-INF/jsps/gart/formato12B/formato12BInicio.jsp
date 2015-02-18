<%@include file="/WEB-INF/jsps/gart/ext/extFormato12B.jsp"%>
<link href="/fise-projects-portlet/css/tablas.css" rel="stylesheet" type="text/css">

<portlet:renderURL var="urlNew" ><portlet:param name="action" value="newFormato"/></portlet:renderURL>
<portlet:renderURL var="urlView" ><portlet:param name="action" value="viewFormato"/></portlet:renderURL>

<script type="text/javascript">
$(document).ready(function () {	
	formato12B.loadInit('${urlNew}','${urlView}');
});
</script>
<form:form method="POST" modelAttribute="formato12BGartCommand" id="frmBusqueda">

	<div id="d_listado" class="net-frame-listado">
		<div id="d_filtro">
			<div id="div_contenido">
				
				<!-- PASO 1 Busqueda -->
				<div id="div_home" class="net-frame-listado">
	
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
											<td colspan="7">
												<form:select path="codEmpresaBusqueda" cssClass="select" cssStyle="width: 375px;">

													<c:if test="${esAdministrador}">
														<form:option value="">-Todos-</form:option>
													</c:if>
													<form:options items="${formato12BGartCommand.listaEmpresas}"  itemLabel="dscEmpresa" itemValue="codEmpresa"/>
												</form:select>
											</td>
										</tr>
										<tr height="10px">
											<td colspan="8"></td>
										</tr>
										<tr>
											<td><output>Desde Año Decl.:</output></td>
											<td>
												<form:input path="anioInicio" cssClass="" cssStyle="width: 50px; text-align: right;" maxlength="4" />
											</td>
											<td><output>Mes Decl.:</output></td>
											<td>
												<form:select path="mesInicio" cssClass="select" cssStyle="width: 104px;">
													<form:option value="">-Seleccione-</form:option>
													<form:options items="${formato12BGartCommand.listaMes}"/>
												</form:select>
												
											</td>
											<td><output>Hasta Año Decl.:</output></td>
											<td>
												<form:input path="anioFin" cssClass="" cssStyle="width: 50px; text-align: right;" maxlength="4" />
											</td>
											<td><output>Mes Decl.:</output></td>
											<td>
												<form:select path="mesFin" cssClass="select" cssStyle="width: 104px;">
													<form:option value="">-Seleccione-</form:option>
													<form:options items="${formato12BGartCommand.listaMes}"/>
												</form:select>
											</td>
										</tr>
										<tr height="10px">
											<td colspan="8"></td>
										</tr>
										<tr>
											<td><output>Etapa:</output></td>
											<td colspan="7">
												<form:select path="etapaBusqueda" cssClass="select" cssStyle="width: 220px;">
													<form:option value="SOLICITUD">SOLICITUD</form:option>
													<form:option value="LEV.OBS">LEVANTAMIENTO OBSERVACIONES</form:option>													
													<form:option value="RECONOCIDO">RECONOCIDO</form:option>
													<form:option value="RECONSIDERACION">RECONSIDERACION</form:option>
												</form:select>
											</td>
										</tr>
										<tr height="10px">
											<td colspan="8"></td>
										</tr>
										<tr>
											
											<td colspan="8"  align="right"><input name="<portlet:namespace/>buscarFormato"
												id="<portlet:namespace/>buscarFormato" type="button"
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
								<table id="<portlet:namespace/>grid_formato">
								</table>
								<div id="<portlet:namespace/>pager_formato"></div>
							</td>
						</tr>
						<tr height="10px">
							<td></td>
						</tr>
						<tr>
							<td>
								<table style="width: 100%;" border="0">
									<tr>
									<td>
											<%-- <input type="button" class="net-button-small" id="<portlet:namespace/>excelFormato" name="<portlet:namespace/>excelFormato"  value="Excel" /> --%>
										</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										
										<td align="right" width="90px" >
											<div id="d_opc_crear">
												<input type="button" class="net-button-small"
													id="<portlet:namespace/>newFormato"
													name="<portlet:namespace/>newFormato" value="Nuevo" />
											</div>
										</td>
										<td align="right" width="90px">
											<%-- <input type="button" class="net-button-small" id="<portlet:namespace/>salirFormato" name="<portlet:namespace/>salirFormato"  value="Salir" /> --%>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						
					</table>
	
				</div>
			</div>
		</div>
	</div>
	<div id="dlgConfirmacion"></div>
	<div id="dialogMessageGeneralInicio" title="Mensaje de &Eacute;xito">
		<p>
			<!-- <span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;">
			</span> -->
			<img src="/fise-projects-portlet/images/success.png" style="float:left; margin:20px 25px 20px 5px;">
			<br/>
			<label class="labelCentrado" id="lblMessageInicio">Datos grabados exit&oacute;samente.</label>
		</p>	
	</div>
	
	<!--  -->
	<div id="<portlet:namespace/>dialog-message-confirm" title="Mensaje de Confirmaci&oacute;n">
		<p>
			<img src="/fise-projects-portlet/images/confirm.png" style="float:left; margin:20px 25px 20px 5px;">
			<br/>
			<label class="labelCentrado" id="<portlet:namespace/>dialog-message-confirm-content">Datos grabados exit&oacute;samente.</label>
		</p>	
	</div>
	
	<div id="<portlet:namespace/>dialog-message-info" title="Mensaje de Informaci&oacute;n">
		<p>
			<img src="/fise-projects-portlet/images/info.png" style="float:left; margin:20px 25px 20px 5px;">
			<br/>
			<label class="labelCentrado" id="<portlet:namespace/>dialog-message-info-content">Datos grabados exit&oacute;samente.</label>
		</p>	
	</div>
	
	<div id="<portlet:namespace/>dialog-message-warning" title="Mensaje de Advertencia">
		<p>
			<img src="/fise-projects-portlet/images/warning.png" style="float:left; margin:20px 25px 20px 5px;">
			<br/>
			<label class="labelCentrado" id="<portlet:namespace/>dialog-message-warning-content">Datos grabados exit&oacute;samente.</label>
		</p>	
	</div>
	
	<div id="<portlet:namespace/>dialog-message-error" title="Mensaje de Error">
		<p>
			<img src="/fise-projects-portlet/images/error.png" style="float:left; margin:20px 25px 20px 5px;">
			<br/>
			<label class="labelCentrado" id="<portlet:namespace/>dialog-message-error-content">Datos grabados exit&oacute;samente.</label>
		</p>	
	</div>
	<!--  -->
	
</form:form>