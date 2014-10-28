<%@include file="/WEB-INF/jsps/gart/ext/extFormato13A.jsp"%>

<portlet:actionURL var="accionURL" name="actionNormal">
	<portlet:param name="action" value="cargar" />
</portlet:actionURL>

<link href="/fise-projects-portlet/css/tablas.css" rel="stylesheet"
	type="text/css">

<script type="text/javascript">
$(document).ready(function () {	
	formato13A.init();
});
</script>

<style>
</style>

<form id="form-formatofise13a" name="form-formatofise13a" method="POST"
	style="padding: 17px; padding-top: 0px;" action="${accionURL}">


</form>

<form:form method="POST" modelAttribute="formato13AGartCommand">

	<div id="d_listado" class="net-frame-listado">
		<div id="d_filtro">
			<div id="div_contenido">
	
				<div id="div_home" class="net-frame-listado">
	
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
											<td colspan="8"><output class="net-titulo">Criterios
													de B�squeda:</output></td>
										</tr>
										<tr height="10px">
											<td colspan="8"></td>
										</tr>
										<tr>
											<td><output>Empresa:</output></td>
											<td colspan="7">
												<form:select path="codEmpresa" cssClass="select" cssStyle="width: 375px;">

													<c:if test="${esAdministrador}">
														<form:option value="">-Todos-</form:option>
													</c:if>
													<form:options items="${formato13AGartCommand.listaEmpresas}"  itemLabel="dscEmpresa" itemValue="codEmpresa"/>
												</form:select>
											</td>
										</tr>
										<tr height="10px">
											<td colspan="8"></td>
										</tr>
										<tr>
											<td><output>Desde a�o:</output></td>
											<td>
												<form:input path="anioInicio" cssClass="" cssStyle="width: 50px; text-align: right;" maxlength="4"/>
											</td>
											<td><output>Mes:</output></td>
											<td>
												<form:select path="mesInicio" cssClass="select" cssStyle="width: 104px;">
													<form:option value="">-Seleccione-</form:option>
													<form:options items="${formato13AGartCommand.listaMes}"/>
												</form:select>
												
											</td>
											<td><output>Hasta a�o:</output></td>
											<td>
												<form:input path="anioFin" cssClass="" cssStyle="width: 50px; text-align: right;" maxlength="4"/>
											</td>
											<td><output>Mes:</output></td>
											<td>
												<form:select path="mesFin" cssClass="select" cssStyle="width: 104px;">
													<form:option value="">-Seleccione-</form:option>
													<form:options items="${formato13AGartCommand.listaMes}"/>
												</form:select>
											</td>
										</tr>
										<tr height="10px">
											<td colspan="8"></td>
										</tr>
										<tr>
											<td><output>Etapa:</output></td>
											<td colspan="7">
												<form:select path="etapa" cssClass="select" cssStyle="width: 140px;">
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
	
				<!-- vamos a formar el div que contenga el formato de creacion -->
	
				<div id="div_formato" class="net-frame-border" style="display: none;">
					<input type="hidden" id="etapaEdit" value="" />
					<table border="0" width="100%">
						<tr>
							<td>
								<fieldset class="">
	
									<table class="" border="0" width="100%">
										<tr class="filete-bottom">
											<td><output class="net-titulo">FORMATO FISE-12A:
												</output> Remisi�n de Gastos Operativos - Implementaci�n</td>
										</tr>
										<tr height="10px">
											<td></td>
										</tr>
										<tr>
											<td>
												<table class="" style="width: 100%;" border="0">
													<tr>
														<td>Distribuidora El�ctrica:</td>
														<td><select id="s_empresa" name="s_empresa"
															style="width: 375px;" class="select">
																<!-- <option value="">-Seleccione-</option> -->
																<c:forEach items="${listaEmpresaNew}" var="emp">
																	<option value="${emp.codEmpresa}">${emp.dscEmpresa}</option>
																</c:forEach>
														</select></td>
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
																		<td colspan="5"><select
																			id="s_periodoenvio_present"
																			name="s_periodoenvio_present" class="select"
																			style="width: 300px;">
																				<c:forEach items="${listaPeriodoEnvio}" var="periodo">
																					<option value="${periodo.codigoItem}">${periodo.descripcionItem}</option>
																				</c:forEach>
																		</select></td>
																	</tr>
																	<%-- <tr>
										   										<td width="40px">A�o:</td>
										   										<td>
										   											<input type="text" name="i_aniopresent" id="i_aniopresent" style="width:50px;" maxlength="4" >
										   										</td>
										   										<td width="10px" ></td>
										   										<td width="40px">Mes:</td>
										   										<td>
										   											<select id="s_mes_present" name="s_mes_present" class="select" style="width:104px;" >
																						<option value="">-Seleccione-</option>
																						<c:forEach items="${listaMes}" var="mes">																
																							<option value="${mes.key}">${mes.value}</option>
																						</c:forEach>
																					</select>
										   										</td>
										   									</tr> --%>
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
																					a ejecuci�n</output></td>
																		</tr>
																		<tr>
																			<td width="40px">A�o:</td>
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
															</div> <c:if test="${flagMostrarPeriodoEjecucion=='S' }">
																<fieldset class="net-frame-border">
																	<table>
																		<tr>
																			<td colspan="5"><output class="net-titulo">Periodo
																					a ejecuci�n</output></td>
																		</tr>
																		<tr>
																			<td width="40px">A�o:</td>
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
															</c:if>
	
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
														<td width="300px"><output class="net-titulo">Actividades</output>
														</td>
														<td colspan="3">
															<table style="width: 100%;" border="0">
																<tr>
																	<td colspan="3" align="center"><output
																			class="net-titulo">Grupo de Beneficiarios
																			seg�n Sector de distribuci�n t�pico</output></td>
																</tr>
																<tr>
																	<td width="100px" align="center"><output
																			class="net-titulo">Rural</output></td>
																	<td width="100px" align="center"><output
																			class="net-titulo">Urbano Provincias</output></td>
																	<td width="100px" align="center"><output
																			class="net-titulo">Urbano Lima</output></td>
																</tr>
																<!--
										   								<tr>
										   									<td colspan="3">
										   										<select id="s_zonabenef" name="s_zonabenef" >
													   								<c:forEach items="${listaZonaBenef}" var="zona">																
																						<option value="${zona.idZonaBenef}">${zona.descripcion}</option>
																					</c:forEach>
																				</select>
										   									</td>
										   								</tr>
										   								-->
															</table>
														</td>
													</tr>
													<tr height="10px">
														<td colspan="4"></td>
													</tr>
													<tr height="10px" class="filete-top">
														<td colspan="4"></td>
													</tr>
													<tr>
														<td colspan="4">1. Empadronamiento</td>
													</tr>
													<tr>
														<td colspan="4"></td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.1 N�mero de
															Empadronados</td>
														<td align="center"><input type="text"
															name="i_nroEmpad_r" id="i_nroEmpad_r"
															style="width: 100px;"></td>
														<td align="center"><input type="text"
															name="i_nroEmpad_p" id="i_nroEmpad_p"
															style="width: 100px;"></td>
														<td align="center"><input type="text"
															name="i_nroEmpad_l" id="i_nroEmpad_l"
															style="width: 100px;"></td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.2 Costo Est�ndar
															Unitario</td>
														<td align="center"><input type="text"
															name="i_costoUnitEmpad_r" id="i_costoUnitEmpad_r"
															style="width: 100px;"></td>
														<td align="center"><input type="text"
															name="i_costoUnitEmpad_p" id="i_costoUnitEmpad_p"
															style="width: 100px;"></td>
														<td align="center"><input type="text"
															name="i_costoUnitEmpad_l" id="i_costoUnitEmpad_l"
															style="width: 100px;"></td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.3 Costo Total
															Empadronamiento</td>
														<td align="center"><input type="text"
															name="i_costoTotalEmpad_r" id="i_costoTotalEmpad_r"
															class="inputText-dashed"></td>
														<td align="center"><input type="text"
															name="i_costoTotalEmpad_r" id="i_costoTotalEmpad_p"
															class="inputText-dashed"></td>
														<td align="center"><input type="text"
															name="i_costoTotalEmpad_r" id="i_costoTotalEmpad_l"
															class="inputText-dashed"></td>
													</tr>
													<tr>
														<td colspan="4"></td>
													</tr>
													<tr>
														<td colspan="4">2. Gesti�n de Red Agentes GLP</td>
													</tr>
													<tr>
														<td colspan="4"></td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.1 N�mero de
															Agentes Autorizados GLP</td>
														<td align="center"><input type="text"
															name="i_nroAgentGlp_r" id="i_nroAgentGlp_r"
															style="width: 100px;"></td>
														<td align="center"><input type="text"
															name="i_nroAgentGlp_p" id="i_nroAgentGlp_p"
															style="width: 100px;"></td>
														<td align="center"><input type="text"
															name="i_nroAgentGlp_l" id="i_nroAgentGlp_l"
															style="width: 100px;"></td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.2 Costo Est�ndar
															Unitario</td>
														<td align="center"><input type="text"
															name="i_costoUnitAgent_r" id="i_costoUnitAgent_r"
															style="width: 100px;"></td>
														<td align="center"><input type="text"
															name="i_costoUnitAgent_p" id="i_costoUnitAgent_p"
															style="width: 100px;"></td>
														<td align="center"><input type="text"
															name="i_costoUnitAgent_l" id="i_costoUnitAgent_l"
															style="width: 100px;"></td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.3 Costo Total
															Gesti�n de Red de Agentes GLP</td>
														<td align="center"><input type="text"
															name="i_costoTotalAgent_r" id="i_costoTotalAgent_r"
															class="inputText-dashed"></td>
														<td align="center"><input type="text"
															name="i_costoTotalAgent_p" id="i_costoTotalAgent_p"
															class="inputText-dashed"></td>
														<td align="center"><input type="text"
															name="i_costoTotalAgent_l" id="i_costoTotalAgent_l"
															class="inputText-dashed"></td>
													</tr>
													<tr>
														<td colspan="4"></td>
													</tr>
													<tr>
														<td>3. Desplazamiento de Personal</td>
														<td align="center"><input type="text"
															name="i_despPersonal_r" id="i_despPersonal_r"
															style="width: 100px;"></td>
														<td align="center"><input type="text"
															name="i_despPersonal_p" id="i_despPersonal_p"
															style="width: 100px;"></td>
														<td align="center"><input type="text"
															name="i_despPersonal_l" id="i_despPersonal_l"
															style="width: 100px;"></td>
													</tr>
													<tr>
														<td>4. Actividades Extraordinarias</td>
														<td align="center"><input type="text"
															name="i_activExtraord_r" id="i_activExtraord_r"
															style="width: 100px;"></td>
														<td align="center"><input type="text"
															name="i_activExtraord_p" id="i_activExtraord_p"
															style="width: 100px;"></td>
														<td align="center"><input type="text"
															name="i_activExtraord_l" id="i_activExtraord_l"
															style="width: 100px;"></td>
													</tr>
													<tr height="10px">
														<td colspan="4"></td>
													</tr>
													<tr class="filete-top">
														<td colspan="4"></td>
													</tr>
													<tr height="10px">
														<td colspan="4"></td>
													</tr>
													<tr>
														<td colspan="4">5. Importe a reconocer a la EDE</td>
													</tr>
													<tr>
														<td>Por Empadronamiento</td>
														<td align="center"><input type="text"
															name="i_importeEmpad" id="i_importeEmpad"
															class="inputText-dashed"></td>
														<td></td>
														<td></td>
													</tr>
													<tr>
														<td>Por Gesti�n de Red de Agentes GLP</td>
														<td align="center"><input type="text"
															name="i_importeAgent" id="i_importeAgent"
															class="inputText-dashed"></td>
														<td></td>
														<td></td>
													</tr>
													<tr>
														<td>Por Desplazamiento de Personal</td>
														<td align="center"><input type="text"
															name="i_importeDespPersonal" id="i_importeDespPersonal"
															class="inputText-dashed"></td>
														<td></td>
														<td></td>
													</tr>
													<tr>
														<td>Por Actividades Extraordinarias</td>
														<td align="center"><input type="text"
															name="i_importeActivExtraord" id="i_importeActivExtraord"
															class="inputText-dashed"></td>
														<td></td>
														<td></td>
													</tr>
													<tr>
														<td>6. Total General a Reconocer</td>
														<td align="center"><input type="text"
															name="i_totalGeneral" id="i_totalGeneral"
															class="inputText-dashed"></td>
														<td></td>
														<td></td>
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
																					value="Validaci�n" /></td>
																				<td width="17%" align="center"><input
																					type="button" class="net-button-small"
																					id="<portlet:namespace/>envioDefinitivo"
																					name="<portlet:namespace/>envioDefinitivo"
																					value="Env�o Def." /></td>
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
	
				<!-- prueba inicio -->
				<div id="dialog-form-cargaExcel"
					class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable"
					style="display: none; z-index: 1002; position: absolute; width: 400px;">
					<div
						class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix">
						<span class="ui-dialog-title"
							id="ui-dialog-title-dialog-form-carga"> Cargar archivo
							excel </span> <a href="#" class="ui-dialog-titlebar-close ui-corner-all"
							role="button" onclick="regresarFormularioCargaExcel();"> <span
							class="ui-icon ui-icon-closethick">close</span>
						</a>
	
					</div>
	
					<div class="ui-dialog-content ui-widget-content">
						<!--tabla-->
	
						<fieldset class="">
							<table style="width: 100%;">
								<tr>
									<td></td>
								</tr>
								<tr>
									<td>Archivo:</td>
									<td><input type="file" id="archivoExcel"
										name="archivoExcel" /></td>
								</tr>
							</table>
						</fieldset>
	
	
					</div>
	
					<div
						class="ui-dialog-buttonpane ui-widget-content ui-helper-clearfix">
						<div class="ui-dialog-buttonset">
							<input type="button" class="net-button-small"
								name="<portlet:namespace/>cargarFormatoExcel"
								id="<portlet:namespace/>cargarFormatoExcel" value="Cargar" /> <input
								type="button" class="net-button-small"
								name="<portlet:namespace/>cerrarFormatoExcel"
								id="<portlet:namespace/>cerrarFormatoExcel" value="Cerrar"
								onclick="regresarFormularioCargaExcel();" />
						</div>
					</div>
				</div>
				<!-- prueba fin -->
	
				<!-- prueba inicio -->
				<div id="dialog-form-cargaTexto"
					class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable"
					style="display: none; z-index: 1002; position: absolute; width: 400px;">
					<div
						class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix">
						<span class="ui-dialog-title"
							id="ui-dialog-title-dialog-form-carga"> Cargar archivo de
							texto </span> <a href="#" class="ui-dialog-titlebar-close ui-corner-all"
							role="button" onclick="regresarFormularioCargaTexto();"> <span
							class="ui-icon ui-icon-closethick" onmouseover="ui-state-hover">close</span>
						</a>
					</div>
					<div class="ui-dialog-content ui-widget-content">
						<!--tabla-->
	
						<fieldset class="">
							<table style="width: 100%;">
								<tr>
									<td></td>
								</tr>
								<tr>
									<td>Archivo:</td>
									<td><input type="file" id="archivoTxt" name="archivoTxt" />
									</td>
								</tr>
							</table>
						</fieldset>
	
	
					</div>
	
	
					<div
						class="ui-dialog-buttonpane ui-widget-content ui-helper-clearfix">
						<div class="ui-dialog-buttonset">
							<input type="button" class="net-button-small"
								name="<portlet:namespace/>cargarFormatoTexto"
								id="<portlet:namespace/>cargarFormatoTexto" value="Cargar" /> <input
								type="button" class="net-button-small"
								name="<portlet:namespace/>cerrarFormatoTexto"
								id="<portlet:namespace/>cerrarFormatoTexto" value="Cerrar"
								onclick="regresarFormularioCargaTexto();" />
						</div>
					</div>
				</div>
				<!-- prueba fin -->
	
				<div id="dialog-form-error" class="net-frame-border"
					style="display: none; background: #fff;"
					title=" Errores de archivo de carga ">
					<fieldset class="net-frame-border">
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="tabla">
							<tr class="titulo_tabla">
								<td width="40">Nro.</td>
								<td width="378" height="37">Descripci�n</td>
							</tr>
							<c:forEach items="${listaError}" var="error" varStatus="status">
								<tr class="detalleTablaContenido">
									<td align="center">${status.count}</td>
									<td align="left">${error.descripcion}</td>
								</tr>
							</c:forEach>
						</table>
					</fieldset>
					<br>
				</div>
	
				<div id="dialog-form-observacion" class="net-frame-border"
					style="display: none; background: #fff;"
					title=" Resultados de validaci�n ">
					<fieldset class="net-frame-border">
						<%-- <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tabla">
									<tr class="titulo_tabla">
					            		<td width="40">Nro.</td>
					            		<td width="80">Grupo Zona</td>
					            		<td width="378" height="37">Descripci�n</td>
					            	</tr>
			                 		<c:forEach items="${listaObservaciones}" var="error" varStatus="status">															
									<tr class="detalleTablaContenido">
				                    	<td align="center">${status.count}</td> 
				                    	<td align="left">${error.codigo}</td>
				                    	<td align="left">${error.descripcion}</td>     
				                 	 </tr>				
									</c:forEach>            
			                	</table> --%>
						<!-- <table cellpadding="0" cellspacing="0" border="0" id="tblLista" width="100%">
							       <thead>
								    <tr>
									   <td width="40">Nro.</td>
					            		<td width="80">Grupo Zona</td>
					            		<td width="378" height="37">Descripci�n</td>
								   </tr>
							      </thead>
							      <tbody>
								    <tr>
									  
								    </tr>
							     </tbody>
					        </table> -->
						<table id="grid_observacion" width="100%">
						</table>
						<div id="pager_observacion"></div>
					</fieldset>
					<br>
				</div>
	
				<!-- fin -->
	
			</div>
		</div>
	</div>
</form:form>