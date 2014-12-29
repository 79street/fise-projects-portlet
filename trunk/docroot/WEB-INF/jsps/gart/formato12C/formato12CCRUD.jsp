<%@include file="/WEB-INF/jsps/gart/ext/extFormato12C.jsp"%>


<portlet:renderURL var="urlNuevo">
	<portlet:param name="action" value="nuevo" />
</portlet:renderURL>
<portlet:actionURL var="accionURL" name="actionNormal">
	<portlet:param name="action" value="uploadFile" />
</portlet:actionURL>


<link href="/fise-projects-portlet/css/tablas.css" rel="stylesheet" type="text/css">
<portlet:actionURL var="urlAnadirFormato">
	<portlet:param name="action" value="nuevoDetalle" />
</portlet:actionURL>

<portlet:renderURL var="urlRegresarBusqueda" />

<script type="text/javascript">
$(document).ready(function () {	
	formato12C.initCRUD('${crud}', '${urlAnadirFormato}','${urlRegresarBusqueda}');
});
</script>


<form:form  method="POST" modelAttribute="formato12CCBean" action="${accionURL}" enctype="multipart/form-data">

	<input type="hidden" id="msgTransaccion" name="msgTransaccion" value="${msgTrans}" />

	<form:hidden path="codigoEmpresaHidden" />
	<form:hidden path="anioPresentacion" />
	<form:hidden path="mesPresentacion" />
	<form:hidden path="etapa" />
	<form:hidden path="periodoEnvioHidden" />
	<form:hidden path="tipoOperacion" />
	
	<form:hidden path="descGrupoInformacion" />
	<form:hidden path="descEstado" />
	
	<input type="hidden" id="<portlet:namespace/>flagCarga" name="<portlet:namespace/>flagCarga" value="" style="display: none;" />
   
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
											<td><output class="net-titulo">FORMATO FISE-12C: </output> Detalle de Gastos de Desplazamiento de Personal</td>
										</tr>
										<tr height="10px">
											<td></td>
										</tr>
										<tr>
										  <td>
											<div id="<portlet:namespace/>divInformacion">
												<fieldset class="net-frame-border">
												  <table>
													 <tr> 
														<td>
														  <label style="font-size: 12px; font-weight:bold">Grupo de Información:</label>
														</td>														
														<td style="text-align: center;">
														  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														  <output id="o_descGrupoInformacion" ></output>
													   </td>
													   <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
													   <td>
														  <label style="font-size: 12px; font-weight:bold">Estado:</label>
														</td>
														
														<td style="text-align: center;">
														  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														  <output id="o_descEstado"></output>														 
													    </td>
													 </tr>													
												</table>
											   </fieldset>
										     </div>										
										  </td>
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
															<form:select path="codigoEmpresa" cssClass="select" cssStyle="width: 375px;" disabled="${formato12CCBean.readOnly}" >
																<form:options items="${formato12CCBean.listaEmpresas}" itemLabel="dscEmpresa" itemValue="codEmpresa" />
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
																		<td colspan="5" >
																			<output class="net-titulo">Periodo a declarar</output>
																		</td>
																		<td colspan="5" style="padding-left: 20px;">
																			<!-- <output class="net-titulo" style="display: none;" id="lblGrupo">Grupo de Información</output> -->
																		</td>
																		<td colspan="5" style="padding-left: 20px;">
																			<!-- <output class="net-titulo" style="display: none;" id="lblEstado">Estado</output> -->
																		</td>
																	</tr>
																	<tr>
																		<td colspan="5">
																		   <form:select path="periodoEnvio" cssClass="select" cssStyle="width: 300px;" disabled="${formato12CCBean.readOnly}" >
																				<c:forEach items="${formato12CCBean.listaPeriodo}" var="item">
																					<form:option value="${item.codigoItem}">${item.descripcionItem}</form:option>
																				</c:forEach>
																			</form:select> 
																		</td>
																		 <td colspan="5" style="padding-left: 15px;">
																		 	<%-- <form:input path="descGrupoInformacion" style="display: none;border-radius: 7px;"  disabled="${formato12CCBean.readOnly}" /> --%>
																		 </td>
																		 <td colspan="5" style="padding-left: 15px;">
																		 	<%-- <form:input path="descEstado" style="display: none;border-radius: 7px;"  disabled="${formato12CCBean.readOnly}" /> --%>
																		 </td>
																	</tr>
																</table>
															</fieldset>
														</td>
														
														<td width="45%" style="padding-left: 15px;">
															<%-- <div id="<portlet:namespace/>divPeriodoEjecucion"  >
																<fieldset class="net-frame-border" style="padding-left: 15px;">
																	<table>
																		<tr>
																			<td colspan="5"><output class="net-titulo">Año Inicio de Vigencia</output></td>
																			<td colspan="5"><output class="net-titulo">Año Fin de Vigencia</output></td>
																		</tr>
																		<tr>
																			
																			<td colspan="5">
																				<form:input path="anioInicioVigencia" style="width: 50px;" disabled="${formato12CCBean.readOnly}" />
																			</td>
																			<td colspan="5">
																				<form:input path="anioFinVigencia" style="width: 50px;" disabled="${formato12CCBean.readOnly}" />	
																			</td>
																		</tr>
																	</table>
																</fieldset>
															</div>  --%>
	
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr height="10px">
											<td></td>
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
																<table id="<portlet:namespace/>grid_formato_implementacionView">
																</table>
																<div id="<portlet:namespace/>pager_formato_implementacionView"></div>
															</c:if>
															<c:if test="${crud =='UPDATE' || crud =='CREATE'}">
																<table id="<portlet:namespace/>grid_formato_implementacion">
																</table>
																<div id="<portlet:namespace/>pager_formato_implementacion"></div>
															</c:if>
														</td>
													</tr>
													<tr height="10px">
														<td colspan="4">
														</td>
													</tr>
													<tr>
														<td colspan="4">
															<c:if test="${crud =='READ'}">
																<table id="<portlet:namespace/>grid_formato_mensualView">
																</table>
																<div id="<portlet:namespace/>pager_formato_mensualView"></div>
															</c:if>
															<c:if test="${crud =='UPDATE' || crud =='CREATE'}">
																<table id="<portlet:namespace/>grid_formato_mensual">
																</table>
																<div id="<portlet:namespace/>pager_formato_mensual"></div>
															</c:if>
														</td>
													</tr>
													<tr height="10px">
														<td colspan="4">
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
																					<td width="16%" align="center">
																						<input type="button" class="boton" name="<portlet:namespace/>reportePdf"
																						id="<portlet:namespace/>reportePdf" class="button net-button-small" value="Imprimir PDF" />
																					</td>
																					<td width="16%" align="center">
																						<input type="button" class="boton" name="<portlet:namespace/>reporteExcel"
																						id="<portlet:namespace/>reporteExcel" class="button net-button-small" value="Exportar excel" />
																					</td>
																						
																					<td width="16%" align="center">
																						<input type="button" class="boton" name="<portlet:namespace/>reporteActaEnvio"
																						id="<portlet:namespace/>reporteActaEnvio" class="button net-button-small" value="Acta de envío" />
																					</td>
																				</c:if>

																				<c:if test="${not readonly}">
																					<td width="17%" align="center">
																						<input type="button" class="net-button-small" id="<portlet:namespace/>anadirFormato"
																						name="<portlet:namespace/>anadirFormato" value="Añadir" />
																					</td>
																					<td width="17%" align="center">
																						<input type="button" class="net-button-small" id="<portlet:namespace/>validacionFormato"
																						name="<portlet:namespace/>validacionFormato" value="Validación" />
																					</td>
																					<td width="17%" align="center">
																						<input type="button" class="net-button-small" id="<portlet:namespace/>envioDefinitivo"
																						name="<portlet:namespace/>envioDefinitivo" value="Envío Def." />
																					</td>
																				</c:if>
																				<td width="17%" align="center">
																					<input type="button" class="net-button-small" id="<portlet:namespace/>regresarBusqueda"
																					name="<portlet:namespace/>regresarBusqueda" value="Regresar" />
																				</td>
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
								onclick="formato12C.closeDialogCargaExcel();"> <span
								class="ui-icon ui-icon-closethick">close</span>
							</a>
						</div>

						<div class="ui-dialog-content ui-widget-content">
							<!--tabla-->

							<fieldset class="">
								<table style="width: 100%;">
									<tr>
										<td colspan="2"></td>
									</tr>
									<tr>
										<td>Archivo:</td>
										<td><input type="file" id="archivoExcel" onclick="formato12C.iniciarMensajeExcel();"
											name="archivoExcel" /></td>
									</tr>
									<tr>
										<td colspan="2" height="10px;"></td>
									</tr>
									<tr>
										<td colspan="2"><span id="msjFileExcel" style="color: red;"></span></td>
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
									onclick="formato12C.closeDialogCargaExcel();" />
							</div>
						</div>
					</div>
					
					<!-- dialogo upload txt -->

					<div id="<portlet:namespace/>dialog-form-cargaTxt"
						class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable"
						style="display: none; z-index: 1002; position: absolute; width: 400px;">
						<div
							class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix">
							<span class="ui-dialog-title"
								id="ui-dialog-title-dialog-form-carga"> Cargar archivo de
								texto </span> <a href="#" class="ui-dialog-titlebar-close ui-corner-all"
								role="button" onclick="formato12C.closeDialogCargaTxt();"> <span
								class="ui-icon ui-icon-closethick">close</span>
							</a>
						</div>
	
						<div class="ui-dialog-content ui-widget-content">
							<!--tabla-->
	
							<fieldset class="">
								<table style="width: 100%;">
									<tr>
										<td colspan="2"></td>
									</tr>
									<tr>
										<td>Archivo:</td>
										<td><input type="file" id="archivoTxt" name="archivoTxt" onclick="formato12C.iniciarMensajeTxt();" /></td>
									</tr>
									<tr>
										<td colspan="2" height="10px;"></td>
									</tr>
									<tr>
										<td colspan="2"><span id="msjFileTxt" style="color: red;"></span></td>
									</tr>
								</table>
							</fieldset>
	
	
						</div>
	
						<div
							class="ui-dialog-buttonpane ui-widget-content ui-helper-clearfix">
							<div class="ui-dialog-buttonset">
								<input type="button" class="net-button-small"
									name="<portlet:namespace/>cargarFormatoTxt"
									id="<portlet:namespace/>cargarFormatoTxt" value="Cargar" /> <input
									type="button" class="net-button-small"
									name="<portlet:namespace/>cerrarFormatoTxt"
									id="<portlet:namespace/>cerrarFormatoTxt" value="Cerrar"
									onclick="formato12C.closeDialogCargaTxt();" />
							</div>
						</div>
					</div>
					
				</c:if>

			</div>
		</div>
	</div>



	<div id="<portlet:namespace/>divOverlay" class="ui-widget-overlay"
		style="display: none; width: 100%; height: 100%; z-index: 1001;">

	</div>
	
	<div id="<portlet:namespace/>dialog-form-error" class="net-frame-border" style="display:none;background:#fff;" title=" Errores de archivo de carga ">				
		<fieldset class="net-frame-border">							
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tabla">
				<tr class="titulo_tabla">
            		<td width="40">Nro.</td>
            		<td width="378" height="37">Descripción</td>
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
		title="Mensaje de Confirmaci&oacute;n">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span> <label
				id="<portlet:namespace/>dialog-confirm-envio-content">¿Está
				seguro?</label>
		</p>
	</div>
	
	<div id="<portlet:namespace/>dialog-confirm-detalle"
		title="Mensaje de Confirmaci&oacute;n">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span> <label
				id="<portlet:namespace/>dialog-confirm-detalle-content">¿Está
				seguro?</label>
		</p>
	</div>
	

	<div id="<portlet:namespace/>dialog-message-report" title="Mensaje de Informaci&oacute;n">
		<p>
			<span class="ui-icon ui-icon-circle-check"
				style="float: left; margin: 0 7px 50px 0;"> </span> <label
				id="<portlet:namespace/>dialog-message-report-content">Datos
				grabados exit&oacute;samente.</label>
		</p>
	</div>
	
	<div id="<portlet:namespace/>dialog-message" title="Mensaje de Informaci&oacute;n">
		<p>
			<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;">
			</span>
			<label id="<portlet:namespace/>dialog-message-content">Datos grabados exit&oacute;samente.</label>
		</p>	
	</div>

</form:form>