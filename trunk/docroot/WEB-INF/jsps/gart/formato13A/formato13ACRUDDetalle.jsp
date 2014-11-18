<%@include file="/WEB-INF/jsps/gart/ext/extFormato13A.jsp"%>

<link href="/fise-projects-portlet/css/tablas.css" rel="stylesheet"
	type="text/css">

<portlet:actionURL var="urlGuardarDetalle">
	<portlet:param name="action" value="guardarDetalle"/>
</portlet:actionURL>

<portlet:renderURL var="urlRegresarDetalle">
<%-- 	<portlet:param name="action" value="nuevo"/> --%>
	  <portlet:param name="action" value="view"/>
</portlet:renderURL>

<script type="text/javascript">
$(document).ready(function () {	
	formato13A.initCRUDDetalle('${crud}','${urlGuardarDetalle}','${urlRegresarDetalle}');
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
											<td width="45%">
												<fieldset class="net-frame-border">
													<table>
														<tr>
															<td>Distribuidora Eléctrica:</td>
															<td>
															<form:input path="descEmpresa" readonly="true"/>
															<form:hidden path="codEmpresa" />
															</td>
															<td><output class="net-titulo">Periodo a declarar</output></td>
															<td>
																<input type="text" value="${formato13AGartCommand.mesPresentacion}/${formato13AGartCommand.anioPresentacion } - ${formato13AGartCommand.etapa}" readonly="true" />
																<form:hidden path="peridoDeclaracion" />
																<form:hidden path="anioPresentacion" />
																<form:hidden path="mesPresentacion" />
																<form:hidden path="etapa" />
															</td>
															<td><output class="net-titulo">Año Inicio Vigencia:</output></td>
															<td>
																<form:input path="anioInicioVigencia" readonly="${readonlyFlagPeriodo}" />
															</td>
															<td><output class="net-titulo">Año fin Vigencia:</output></td>
															<td>
																<form:input path="anioFinVigencia" readonly="${readonlyFlagPeriodo}" />
															</td>
														</tr>
													</table>
												</fieldset>
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
														<td style="width: 30%;">
															<fieldset class="net-frame-border" > 
															    <legend>Periodo de Alta</legend>
																<table> 
																	<tbody >																		 
																		<tr> 
																			<td width="40px">Año:</td> 
																			<td>
																				<form:input path="anioAlta" cssStyle="width:50px" maxlength="4" readonly="${readonlyEdit}"/>																			
																			</td>
									   										<td width="5px"></td>
									   										<td width="40px">Mes:</td>
									   										<td>
									   											<form:select path="mesAlta" cssClass="select" cssStyle="width: 104px;" disabled="${readonlyEdit}">
																					<form:option value="">-Seleccione-</form:option>
																					<form:options items="${formato13AGartCommand.listaMes}"/>
																				</form:select>
									   										</td>
									   									</tr>
									   								</tbody></table>
									   							</fieldset>													
														</td>
														<td style="width: 70%;">
															<fieldset class="net-frame-border" >
															<legend>Ubigeo</legend> 
																 <table> 
																	<tbody >																		 
																		<tr> 
																			<td width="40px">Departamento:</td> 
																			<td>
																				<form:select path="codDepartamento" cssClass="select" cssStyle="width: 104px;" disabled="${readonly}">
																					<form:option value="">-Seleccione-</form:option>
																					<form:options items="${formato13AGartCommand.listaDepartamentos}" itemLabel="nomUbigeo" itemValue="codUbigeo"/>
																				</form:select>																			
																			</td>
									   										<td width="5px"></td>
									   										<td width="40px">Provincia:</td>
									   										<td>
									   											<form:select path="codProvincia" cssClass="select" cssStyle="width: 104px;" disabled="${readonly}">
																					<form:option value="">-Seleccione-</form:option>																					
																				</form:select>
									   										</td>
									   										<td width="5px"></td>
									   										<td width="40px">Distrito:</td>
									   										<td>
									   											<form:select path="codDistrito" cssClass="select" cssStyle="width: 104px;" disabled="${readonly}">
																					<form:option value="">-Seleccione-</form:option>																					
																				</form:select>
									   										</td>
									   										<td width="5px"></td>
									   										<td width="40px">Localidad:</td>
									   										<td>
									   											<form:input path="localidad" readonly="${readonlyEdit}"/>
									   										</td>
									   									</tr>
									   								</tbody></table>
															</fieldset>
														</td>
													</tr>
													<tr>
														<td colspan="4">
														<fieldset class="net-frame-border" >
															<legend>Beneficiarios Potenciales</legend> 
															<table id="<portlet:namespace/>grid_formato_declaracion" style="width: 100%;" border="0">
																<thead>																	
																	<tr>
																		<th>ST-1</th>
																		<th>ST-2</th>
																		<th>ST-3</th>
																		<th>ST-4</th>
																		<th>ST-5</th>
																		<th>ST-6</th>
																		<th>ST-SER</th>
																		<th>Especial</th>
																		<th>Total</th>
																		<th>Zona de Beneficiarios</th>
																	</tr>
																</thead>
																<tbody>
																	<tr>
																		<td><form:input path="st1" cssClass="target" cssStyle="width:50px; text-align: right;" maxlength="4" readonly="${readonlyEdit}"/></td>
																		<td><form:input path="st2" cssClass="target" cssStyle="width:50px; text-align: right;" maxlength="4" readonly="${readonlyEdit}"/></td>
																		<td><form:input path="st3" cssClass="target" cssStyle="width:50px; text-align: right;" maxlength="4" readonly="${readonlyEdit}"/></td>
																		<td><form:input path="st4" cssClass="target" cssStyle="width:50px; text-align: right;" maxlength="4" readonly="${readonlyEdit}"/></td>
																		<td><form:input path="st5" cssClass="target" cssStyle="width:50px; text-align: right;" maxlength="4" readonly="${readonlyEdit}"/></td>
																		<td><form:input path="st6" cssClass="target" cssStyle="width:50px; text-align: right;" maxlength="4" readonly="${readonlyEdit}"/></td>
																		<td><form:input path="stser" cssClass="target" cssStyle="width:50px; text-align: right;" maxlength="4" readonly="${readonlyEdit}"/></td>
																		<td><form:input path="stesp" cssClass="target" cssStyle="width:50px; text-align: right;" maxlength="4" readonly="${readonlyEdit}"/></td>
																		<%-- <td><form:input path="total" cssStyle="width:50px" maxlength="4" readonly="${readonly}"/></td> --%>
																		<td><form:input path="total" cssStyle="width:50px; text-align: right;" maxlength="4" readonly="true"/></td>
																		<td>
																			<form:select path="idZonaBenef" cssClass="select" cssStyle="width: 104px;" disabled="${readonly}">
																					<form:option value="">-Seleccione-</form:option>
																					<form:options items="${formato13AGartCommand.listaZonasBenef}"/>
																			</form:select>
																		</td>
																	</tr>
																</tbody>
															</table>
														</fieldset>
														</td>
													</tr>
													<tr>
														<td colspan="4">
															<fieldset class="net-frame-border" >
																<legend>Sede de atencion</legend> 
																<table style="width: 100%;" border="0">
																	<tr>
																		<td><form:input path="nombreSede" readonly="${readonlyEdit}"/></td>
																	</tr>
																</table>
															</fieldset>
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
																	<td>
																		<table style="width: 100%">
																			<tr>
																				<c:if test="${not readonlyEdit}">	
																					<td width="17%" align="center"><input
																						type="button" class="net-button-small"
																						id="<portlet:namespace/>guardarDetalle"
																						name="<portlet:namespace/>guardarDetalle"
																						value="Grabar" /></td>
																				</c:if>
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