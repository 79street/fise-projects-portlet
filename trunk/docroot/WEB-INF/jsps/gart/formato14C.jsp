<%@include file="/WEB-INF/jsps/gart/ext/extFormato14C.jsp"%>

<portlet:actionURL var="accionURL" name="actionNormal">
	<portlet:param name="action" value="cargar" />
</portlet:actionURL>

<link href="/fise-projects-portlet/css/tablas.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/fise-projects-portlet/js/fise.js"></script>

<script type="text/javascript">
$(document).ready(function () {	
	formato14C.init();
});
</script>

<form:form method="POST" modelAttribute="formato14CBean">

	<div id="d_listado" class="net-frame-listado">
		<div id="d_filtro">
			<div id="div_contenido">
	
				<div id="<portlet:namespace/>div_home" class="net-frame-listado">
	
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
											<td><output>Empresa:</output></td>
											<td colspan="7">
												<form:select path="codEmpresaBusq" cssClass="select" cssStyle="width: 375px;">

													<c:if test="${formato14CBean.admin}">
														<form:option value="">-Todos-</form:option>
													</c:if>
													<form:options items="${formato14CBean.listaEmpresas}"  itemLabel="dscEmpresa" itemValue="codEmpresa"/>
												</form:select>
											</td>
										</tr>
										<tr height="10px">
											<td colspan="8"></td>
										</tr>
										<tr>
											<td><output>Desde año:</output></td>
											<td>
												<form:input path="anioDesde" cssClass="" cssStyle="width: 50px; text-align: right;" maxlength="4"/>
											</td>
											<td><output>Mes:</output></td>
											<td>
												<form:select path="mesDesde" cssClass="select" cssStyle="width: 104px;">
													<form:option value="">-Seleccione-</form:option>
													<form:options items="${formato14CBean.listaMes}"/>
												</form:select>
												
											</td>
											<td><output>Hasta año:</output></td>
											<td>
												<form:input path="anioHasta" cssClass="" cssStyle="width: 50px; text-align: right;" maxlength="4"/>
											</td>
											<td><output>Mes:</output></td>
											<td>
												<form:select path="mesHasta" cssClass="select" cssStyle="width: 104px;">
													<form:option value="">-Seleccione-</form:option>
													<form:options items="${formato14CBean.listaMes}"/>
												</form:select>
											</td>
										</tr>
										<tr height="10px">
											<td colspan="8"></td>
										</tr>
										<tr>
											<td><output>Etapa:</output></td>
											<td colspan="7">
												<form:select path="etapaBusq" cssClass="select" cssStyle="width: 140px;">
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
</form:form>