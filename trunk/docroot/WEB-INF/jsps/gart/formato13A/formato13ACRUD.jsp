<%@include file="/WEB-INF/jsps/gart/ext/extFormato13A.jsp"%>

<portlet:renderURL var="urlNuevo">
<portlet:param name="action" value="nuevo"/>
</portlet:renderURL>

<link href="/fise-projects-portlet/css/tablas.css" rel="stylesheet"
	type="text/css">

<script type="text/javascript">
$(document).ready(function () {	
	formato13A.initCRUD('${crud}');
});
</script>

<form:form method="POST" modelAttribute="formato13AGartCommand">

	<div id="d_listado" class="net-frame-listado">
		<div id="d_filtro">
			<div id="div_contenido">
				
				<!-- CREACION -->
	
				<div id="div_formato" class="net-frame-border">
					<input type="hidden" id="etapaEdit" value="" />
					<table border="0" width="100%">
						<tr>
							<td>
								<fieldset class="">
	
									<table class="" border="0" width="100%">
										<tr class="filete-bottom">
											<td><output class="net-titulo">FORMATO FISE-13A:
												</output> Remisión de Gastos Operativos - Implementación</td>
										</tr>
										<tr height="10px">
											<td></td>
										</tr>
										<tr>
											<td>
												<table class="" style="width: 100%;" border="0">
													<tr>
														<td>Distribuidora Eléctrica:</td>
														<td>
														<form:select path="codEmpresa" cssClass="select" cssStyle="width: 375px;">
															<form:options items="${formato13AGartCommand.listaEmpresas}"  itemLabel="dscEmpresa" itemValue="codEmpresa"/>
														</form:select>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr height="10px">
											<td></td>
										</tr>
										<tr>
											<td>
												<table class="" style="width: 100%;" border="0">
													<tr>
														<td width="45%">
															<fieldset class="net-frame-border">
																<table>
																	<tr>
																		<td colspan="5"><output class="net-titulo">Periodo
																				a declarar</output></td>
																	</tr>
																	<tr>
																		<td colspan="5">
																		<form:select path="peridoDeclaracion" cssClass="select" cssStyle="width: 300px;">
																			
																		</form:select>
																		</td>
																	</tr>
																</table>
															</fieldset>
														</td>
														<td width="10%"><input type="hidden"
															id="flagPeriodoEjecucion" value="" />
															<div id="divPrueba" style="display: none;">hola
																mundo</div></td>
														<td width="45%">
															<div id="divPeriodoEjecucion" style="display: none;">
																<fieldset class="net-frame-border">
																	<table>
																		<tr>
																			<td colspan="5"><output class="net-titulo">Periodo
																					a ejecución</output></td>
																		</tr>
																		<tr>
																			<td width="40px">Año:</td>
																			<td><input type="text" name="i_anioejecuc"
																				id="i_anioejecuc" style="width: 50px" maxlength="4">
																			</td>
																			<td width="10px"></td>
																			<td width="40px">Mes:</td>
																			<td><select id="s_mes_ejecuc" name="s_mes_ejecuc"
																				class="select" style="width: 104px;">
																					<option value="">-Seleccione-</option>
																					<c:forEach items="${listaMes}" var="mes">
																						<option value="${mes.key}">${mes.value}</option>
																					</c:forEach>
																			</select></td>
																		</tr>
																	</table>
																</fieldset>
															</div> 	
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
										<tr>
											<td>
												<table class="" style="width: 100%;" border="0">
												
													<tr>
														<td colspan="4">
															<table id="<portlet:namespace/>grid_formato_declaracion">
															</table>
															<div id="<portlet:namespace/>pager_formato_declaracion"></div>
														</td>
													</tr>
													<tr height="10px">
														<td colspan="4">
															<!-- luego eliminar --> <!-- fin eliminar -->
														</td>
													</tr>
													<tr class="filete-top">
														<td colspan="4">
															<table style="width: 100%">
																<tr>
																	<td width="25%">
																		<fieldset id="panelCargaArchivos"
																			class="net-frame-border">
																			<legend>Subir de: </legend>
																			<table style="width: 100%">
																				<tr>
																					<td width="50%" align="center"><input
																						type="button" class="net-button-small"
																						id="<portlet:namespace/>cargaExcel"
																						name="<portlet:namespace/>cargaExcel" value="EXCEL" />
																					</td>
																					<td width="50%" align="center"><input
																						type="button" class="net-button-small"
																						id="<portlet:namespace/>cargaTxt"
																						name="<portlet:namespace/>cargaTxt" value="TXT" />
																					</td>
																				</tr>
																			</table>
																		</fieldset>
																	</td>
																	<td width="10%"></td>
																	<td width="65%">
																		<table style="width: 100%">
																			<tr>
																				<td width="16%" align="center"><input
																					type="button" class="boton"
																					name="<portlet:namespace/>reportePdf"
																					style="display: none;"
																					id="<portlet:namespace/>reportePdf"
																					class="button net-button-small" value="Imprimir PDF" />
																				</td>
																				<td width="16%" align="center"><input
																					type="button" class="boton"
																					name="<portlet:namespace/>reporteExcel"
																					style="display: none;"
																					id="<portlet:namespace/>reporteExcel"
																					class="button net-button-small"
																					value="Exportar excel" /></td>
																				<td width="17%" align="center"><input
																					type="button" class="net-button-small"
																					id="<portlet:namespace/>guardarFormato"
																					name="<portlet:namespace/>guardarFormato"
																					value="Grabar" /></td>
																				<td width="17%" align="center"><input
																					type="button" class="net-button-small"
																					id="<portlet:namespace/>validacionFormato"
																					name="<portlet:namespace/>validacionFormato"
																					value="Validación" /></td>
																				<td width="17%" align="center"><input
																					type="button" class="net-button-small"
																					id="<portlet:namespace/>envioDefinitivo"
																					name="<portlet:namespace/>envioDefinitivo"
																					value="Envío Def." /></td>
																				<td width="17%" align="center"><input
																					type="button" class="net-button-small"
																					id="<portlet:namespace/>regresarFormato"
																					name="<portlet:namespace/>regresarFormato"
																					value="Regresar" /></td>
																			</tr>
																		</table>
	
																	</td>
																</tr>
															</table> <input type="hidden" id="flagCarga" name="flagCarga"
															value="" style="display: none;" />
	
														</td>
													</tr>
													<tr height="10px" class="filete-bottom">
														<td colspan="4"></td>
													</tr>
													<tr height="10px">
														<td colspan="4"></td>
													</tr>
	
												</table>
											</td>
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
			</div>
		</div>
	</div>
</form:form>