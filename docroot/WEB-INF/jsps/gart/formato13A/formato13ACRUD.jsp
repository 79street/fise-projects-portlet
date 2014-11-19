<%@include file="/WEB-INF/jsps/gart/ext/extFormato13A.jsp"%>


<portlet:renderURL var="urlNuevo">
	<portlet:param name="action" value="nuevo" />
</portlet:renderURL>
<portlet:actionURL var="accionURL" name="actionNormal">
	<portlet:param name="action" value="uploadFile" />
</portlet:actionURL>


<link href="/fise-projects-portlet/css/tablas.css" rel="stylesheet"
	type="text/css">
<portlet:actionURL var="urlAnadirFormato">
	<portlet:param name="action" value="nuevoDetalle" />
</portlet:actionURL>

<portlet:renderURL var="urlRegresarBusqueda" />

<script type="text/javascript">
	$(document).ready(
			function() {
				//alert("aqui en ready");
				var error = '${error}';
				
				if (error.length > 0) {
				    alert(error);
				}
				
				formato13A.initCRUD('${crud}', '${urlAnadirFormato}','${urlRegresarBusqueda}');

			});
</script>


<form:form  method="POST"
	modelAttribute="formato13AGartCommand" action="${accionURL}"
	enctype="multipart/form-data">
	<form:input path="tipoOperacion" type="hidden" />


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
											<td><output class="net-titulo">FORMATO
													FISE-13A: </output> Remisión de Gastos Operativos - Implementación</td>
										</tr>
										<tr height="10px">
											<td></td>
										</tr>
										<tr>
											<td>
												<table class="" style="width: 100%;" border="0">
													<tr>
														<td>Distribuidora Eléctrica:</td>
														<td><form:select path="codEmpresa" cssClass="select"
																cssStyle="width: 375px;"
																disabled="${formato13AGartCommand.readOnly}">
																<form:options
																	items="${formato13AGartCommand.listaEmpresas}"
																	itemLabel="dscEmpresa" itemValue="codEmpresa" />
															</form:select></td>
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
																		<td colspan="5"><form:select
																				path="peridoDeclaracion" cssClass="select"
																				cssStyle="width: 300px;">

																				<c:forEach
																					items="${formato13AGartCommand.listaPeriodo}"
																					var="item">
																					<form:option value="${item.codigoItem}">${item.descripcionItem}</form:option>
																				</c:forEach>

																			</form:select> <form:input path="descripcionPeriodo"
																				id="txtperiodo" type="text" style="display: none;"
																				disabled="${formato13AGartCommand.readOnly}" /></td>
																	</tr>
																</table>
															</fieldset>
														</td>
														<td width="10%"><input type="hidden"
															id="flagPeriodoEjecucion" value="" /></td>
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
																			<td><select id="s_mes_ejecuc"
																				name="s_mes_ejecuc" class="select"
																				style="width: 104px;">
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
															<c:if test="${crud =='READ'}">
																<table id="<portlet:namespace/>grid_formato_declaracionView">
																</table>
																<div id="<portlet:namespace/>pager_formato_declaracionView"></div>
															</c:if>
															<c:if test="${crud =='UPDATE' || crud =='CREATE'}">
																<table id="<portlet:namespace/>grid_formato_declaracion">
																</table>
																<div id="<portlet:namespace/>pager_formato_declaracion"></div>
															</c:if>
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
																	<td width="25%"><c:if test="${not readonly}">
																			<fieldset id="panelCargaArchivos"
																				class="net-frame-border">
																				<legend>Subir de: </legend>
																				<table style="width: 100%">
																					<tr>
																						<td width="50%" align="center"><input
																							type="button" class="net-button-small"
																							id="<portlet:namespace/>showDialogUploadExcel"
																							name="<portlet:namespace/>showDialogUploadExcel"
																							value="EXCEL" /></td>
																						<td width="50%" align="center"><input
																							type="button" class="net-button-small"
																							id="<portlet:namespace/>showDialogUploadTxt"
																							name="<portlet:namespace/>showDialogUploadTxt"
																							value="TXT" /></td>
																					</tr>
																				</table>
																			</fieldset>
																		</c:if></td>
																	<td width="10%"></td>
																	<td width="65%">
																		<table style="width: 100%">
																			<tr>
																				<c:if test="${readonly}">
																					<td width="16%" align="center"><input
																						type="button" class="boton"
																						name="<portlet:namespace/>reportePdf"
																						id="<portlet:namespace/>reportePdf"
																						class="button net-button-small"
																						value="Imprimir PDF" /></td>
																					<td width="16%" align="center"><input
																						type="button" class="boton"
																						name="<portlet:namespace/>reporteExcel"
																						id="<portlet:namespace/>reporteExcel"
																						class="button net-button-small"
																						value="Exportar excel" /></td>
																				</c:if>

																				<td width="17%" align="center"><input
																					style="display: none;" type="button"
																					class="net-button-small"
																					id="<portlet:namespace/>anadirFormato"
																					name="<portlet:namespace/>anadirFormato"
																					value="Añadir" /></td>


																				<td width="17%" align="center"><input
																					style="display: block;" type="button"
																					class="net-button-small"
																					id="<portlet:namespace/>guardarFormato"
																					name="<portlet:namespace/>guardarFormato"
																					value="Guardar" /></td>

																				<c:if test="${not readonly}">


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

																				</c:if>


																				<td width="17%" align="center"><input
																					type="button" class="net-button-small"
																					id="<portlet:namespace/>regresarBusqueda"
																					name="<portlet:namespace/>regresarBusqueda"
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

				<c:if test="${not readonly}">
					<div id="<portlet:namespace/>dialog-form-cargaExcel"
						class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable"
						style="display: none; z-index: 1002; position: absolute; width: 400px;">
						<div
							class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix">
							<span class="ui-dialog-title"
								id="ui-dialog-title-dialog-form-carga"> Cargar archivo
								excel </span> <a href="#"
								class="ui-dialog-titlebar-close ui-corner-all" role="button"
								onclick="formato13A.closeDialogCargaExcel();"> <span
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
									id="<portlet:namespace/>cargarFormatoExcel" value="Cargar" />
								<input type="button" class="net-button-small"
									name="<portlet:namespace/>cerrarFormatoExcel"
									id="<portlet:namespace/>cerrarFormatoExcel" value="Cerrar"
									onclick="formato13A.closeDialogCargaExcel();" />
							</div>
						</div>
					</div>
				</c:if>
				<!-- dialogo upload txt -->

				<div id="<portlet:namespace/>Formato13AGartController"
					class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable"
					style="display: none; z-index: 1002; position: absolute; width: 400px;">
					<div
						class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix">
						<span class="ui-dialog-title"
							id="ui-dialog-title-dialog-form-carga"> Cargar archivo
							excel </span> <a href="#" class="ui-dialog-titlebar-close ui-corner-all"
							role="button" onclick="formato13A.closeDialogCargaTxt();"> <span
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
									<td><input type="file" id="archivoTxt" name="archivoTxt" /></td>
								</tr>
							</table>
						</fieldset>


					</div>

					<div
						class="ui-dialog-buttonpane ui-widget-content ui-helper-clearfix">
						<div class="ui-dialog-buttonset">
							<input type="button" class="net-button-small"
								name="<portlet:namespace/>cargarFormatoTxt"
								id="<portlet:namespace/>cargarFormatoTxtl" value="Cargar" /> <input
								type="button" class="net-button-small"
								name="<portlet:namespace/>cerrarFormatoTxt"
								id="<portlet:namespace/>cerrarFormatoTxt" value="Cerrar"
								onclick="formato13A.closeDialogCargaTxt();" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>



	<div id="<portlet:namespace/>divOverlay" class="ui-widget-overlay"
		style="display: none; width: 100%; height: 100%; z-index: 1001;">

	</div>

	
	<div id="<portlet:namespace/>dialog-form-observacion"
		class="net-frame-border" style="display: none; background: #fff;"
		title=" Resultados de validación ">
		<fieldset class="net-frame-border">
			<table id="<portlet:namespace/>grid_observacion" width="100%">
			</table>
			<div id="<portlet:namespace/>pager_observacion"></div>
		</fieldset>
		<br>
	</div>

	<div id="<portlet:namespace/>dialog-confirm-envio"
		title="Confirmar acci&oacute;n">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span> <label
				id="<portlet:namespace/>dialog-confirm-envio-content">¿Está
				seguro?</label>
		</p>
	</div>
	
	<div id="<portlet:namespace/>dialog-confirm-detalle"
		title="Confirmar acci&oacute;n">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span> <label
				id="<portlet:namespace/>dialog-confirm-detalle-content">¿Está
				seguro?</label>
		</p>
	</div>
	

	<div id="<portlet:namespace/>dialog-message-report" title="Osinergmin">
		<p>
			<span class="ui-icon ui-icon-circle-check"
				style="float: left; margin: 0 7px 50px 0;"> </span> <label
				id="<portlet:namespace/>dialog-message-report-content">Datos
				grabados exit&oacute;samente.</label>
		</p>
	</div>

</form:form>