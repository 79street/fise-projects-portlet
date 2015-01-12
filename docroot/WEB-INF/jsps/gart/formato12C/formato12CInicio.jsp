<%@include file="/WEB-INF/jsps/gart/ext/extFormato12C.jsp"%>

<portlet:renderURL var="urlNuevo">
    <portlet:param name="action" value="nuevo"/>
</portlet:renderURL>

<portlet:renderURL var="urlView">
    <portlet:param name="action" value="viewedit"/>
</portlet:renderURL>

<portlet:renderURL var="urlEdit">
    <portlet:param name="action" value="edit"/>
</portlet:renderURL>

<link href="/fise-projects-portlet/css/tablas.css" rel="stylesheet"
	type="text/css">

<script type="text/javascript">
$(document).ready(function () {	
	formato12C.init('${urlNuevo}','${urlView}','${urlEdit}');
});
</script>

<form:form method="POST" modelAttribute="formato12CCBean">

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
											<td colspan="8">
												<output class="net-titulo">Criterios de Búsqueda:</output>
											</td>
										</tr>
										<tr height="10px">
											<td colspan="8"></td>
										</tr>
										<tr>
											<td><output>Distribuidora Eléctrica:</output></td>
											<td colspan="7">
												<form:select path="codEmpresaB" cssClass="select" cssStyle="width: 375px;">
													<c:if test="${esAdministrador}">
														<form:option value="">-Todos-</form:option>
													</c:if>
													<form:options items="${formato12CCBean.listaEmpresas}"  itemLabel="dscEmpresa" itemValue="codEmpresa"/>
												</form:select>
											</td>
										</tr>
										<tr height="10px">
											<td colspan="8"></td>
										</tr>
										<tr>
											<td>
												<output>Desde Año Decl.:</output></td>
											<td>
												<form:input path="anioDesde" cssClass="" cssStyle="width: 50px; text-align: right;" maxlength="4" onkeypress="return soloNumerosDecimales(event, 1, 'anioDesde',4,0)" />
											</td>
											<td><output>Mes Decl.:</output></td>
											<td>
												<form:select path="mesDesde" cssClass="select" cssStyle="width: 104px;">
													<form:option value="">-Seleccione-</form:option>
													<form:options items="${formato12CCBean.listaMes}"/>
												</form:select>
												
											</td>
											<td>
												<output>Hasta Año Decl.:</output></td>
											<td>
												<form:input path="anioHasta" cssClass="" cssStyle="width: 50px; text-align: right;" maxlength="4" onkeypress="return soloNumerosDecimales(event, 1, 'anioHasta',4,0)" />
											</td>
											<td><output>Mes Decl.:</output></td>
											<td>
												<form:select path="mesHasta" cssClass="select" cssStyle="width: 104px;">
													<form:option value="">-Seleccione-</form:option>
													<form:options items="${formato12CCBean.listaMes}"/>
												</form:select>
											</td>
										</tr>
										<tr height="10px">
											<td colspan="8"></td>
										</tr>
										<tr>
											<td><output>Etapa:</output></td>
											<td colspan="7">
												<form:select path="etapaB" cssClass="select" cssStyle="width: 140px;">
													<form:option value="SOLICITUD">SOLICITUD</form:option>
													<form:option value="LEV.OBS">LEV.OBS</form:option>
													<form:option value="RECONSIDERACION">RECONSIDERACION</form:option>
													<form:option value="RECONOCIDO">RECONOCIDO</form:option>
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
											<td><input name="<portlet:namespace/>buscarFormato"
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
										<td align="right" width="90px">
											<div id="d_opc_crear">
												<input type="button" class="net-button-small"
													id="<portlet:namespace/>crearFormato"
													name="<portlet:namespace/>crearFormato" value="Nuevo" />
											</div>
										</td>
										<td align="right" width="90px">
											<%-- <input type="button" class="net-button-small" id="<portlet:namespace/>salirFormato" name="<portlet:namespace/>salirFormato"  value="Salir" /> --%>
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
			</div>
		</div>
	</div>

	
	<div id="<portlet:namespace/>dialog-confirm" title="Mensaje de Confirmaci&oacute;n">
		<p>	
			<!-- <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span> -->
			<img src="fise-projects-portlet/images/confirm.png" style="float:left; margin:0 25px 10px 0;">
			<label id="<portlet:namespace/>dialog-confirm-content">¿Está seguro?</label>
		</p>
	</div>
	
	<!--  -->
	<div id="<portlet:namespace/>dialog-message-info" title="Mensaje de Informaci&oacute;n">
		<p>
			<img src="/fise-projects-portlet/images/info.png" style="float:left; margin:0 25px 10px 0;">
			<label id="dialog-message-info-content">Datos grabados exit&oacute;samente.</label>
		</p>	
	</div>
	
	<div id="<portlet:namespace/>dialog-message-warning" title="Mensaje de Advertencia">
		<p>
			<img src="/fise-projects-portlet/images/warning.png" style="float:left; margin:0 25px 10px 0;">
			<label id="dialog-message-warning-content">Datos grabados exit&oacute;samente.</label>
		</p>	
	</div>
	<!--  -->
	
</form:form>