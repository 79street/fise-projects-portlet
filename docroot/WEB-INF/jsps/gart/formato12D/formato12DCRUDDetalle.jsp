<%@include file="/WEB-INF/jsps/gart/ext/extFormato12D.jsp"%>

<link href="/fise-projects-portlet/css/tablas.css" rel="stylesheet"
	type="text/css">

<portlet:actionURL var="urlGuardarDetalle">
	<portlet:param name="action" value="guardarDetalle"/>
</portlet:actionURL>

<portlet:renderURL var="urlRegresarDetalle">
	  <portlet:param name="action" value="viewedit"/>
</portlet:renderURL>

<portlet:renderURL var="urlRegresarNuevo">
	<portlet:param name="action" value="nuevo"/>
</portlet:renderURL>

<script type="text/javascript">
$(document).ready(function () {	
	formato12D.initCRUDDetalle('${crud}','${urlGuardarDetalle}','${urlRegresarDetalle}','${urlRegresarNuevo}');
});
$(function() {
	$( ".datepicker" ).datepicker({
	      changeDay: true,
		changeMonth: true,
	      changeYear: true,
	      showOn: "button",
	      buttonImage: "/net-theme/images/img-net/calendar.png",
	      buttonImageOnly: true,
	      yearRange: '-100:+0'
	    });
});
</script>

<form:form method="POST" modelAttribute="formato12DCBean">

	<input type="hidden" id="msgTransaccionDetalle" name="msgTransaccionDetalle" value="${msg}" />

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
											<td><output class="net-titulo">FORMATO FISE-12D: 
												</output> Detalle de Gastos por Actividades Extraordinarias</td>
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
								   							<form:select path="codigoEmpresa" cssClass="select" cssStyle="width: 375px;" disabled="true" >
																<form:options items="${formato12DCBean.listaEmpresas}" itemLabel="dscEmpresa" itemValue="codEmpresa" />
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
								   							<fieldset class="net-frame-border" >
								   								<table>
								   									<tr>
								   										<td colspan="5">
								   											<output class="net-titulo">Periodo a declarar:</output>
								   										</td>
								   									</tr>
								   									<tr>
								   										<td colspan="5">
								   											<form:select path="periodoEnvio" cssClass="select" cssStyle="width: 300px;" disabled="true" >
																				<c:forEach items="${formato12DCBean.listaPeriodoEnvio}" var="item">
																					<form:option value="${item.codigoItem}">${item.descripcionItem}</form:option>
																				</c:forEach>
																			</form:select> 
																			<form:hidden path="anioPresentacion" />
																			<form:hidden path="mesPresentacion" />
																			<form:hidden path="etapa" />
																			<form:hidden path="nroItemEtapa" />
																			
																			<form:hidden path="codDepartamentoHidden"  />	
																			<form:hidden path="descDepartamento"  />	
																			<form:hidden path="codProvinciaHidden"  />
																			<form:hidden path="descProvincia"  />
																			<form:hidden path="codDistritoHidden"  />	
																			<form:hidden path="descDistrito"  />
															
								   										</td>
								   									</tr>
								   								</table>
								   							</fieldset>
								   						</td>
								   						<td width="10%">
								   							<input type="hidden" id="flagPeriodoEjecucion" value="${readonlyFlagPeriodo}" />
								   						</td>
								   						<td width="45%">
								   							<div id="divPeriodoEjecucion" >
								   								<fieldset class="net-frame-border" >
									   								<table>
									   									<tr>
									   										<td colspan="5">
									   											<output class="net-titulo">Periodo de ejecución</output>
									   										</td>
									   									</tr>
									   									<tr>
									   										<td width="40px">Año:</td>
									   										<td>
									   											<form:input path="anioEjecucion" style="width: 50px" maxlength="4" disabled="${readonlyFlagPeriodo}" onkeypress="return soloNumerosDecimales(event, 1, 'anioEjecucion',4,0)" />
									   										</td>
									   										<td width="10px" ></td>
									   										<td width="40px">Mes:</td>
									   										<td>
									   											<form:select path="mesEjecucion" cssClass="select" cssStyle="width: 104px;" disabled="${readonlyFlagPeriodo}" >
										   											<c:forEach items="${formato12DCBean.listaMes}" var="mes">																
																						<option value="${mes.key}">${mes.value}</option>
																					</c:forEach>
																				</form:select>
									   										</td>
									   									</tr>
									   								</table>
									   							</fieldset>
								   							</div>
								   							
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
														<td width="120px">Etapa ejecución:</td>
														<td>
															<form:select path="etapaEjecucion" cssClass="select" cssStyle="width: 200px;" disabled="${readonly}">
																<form:option value="">-Seleccione-</form:option>
																<form:options items="${formato12DCBean.listaEtapaEjecucion}"/>
															</form:select>
															
															<form:hidden path="anoEjecucionHidden"  />
															<form:hidden path="mesEjecucionHidden"   />
															<form:hidden path="etapaEjecucionHidden"   />
															
															
														</td>
														<td colspan="9"></td>
													
													</tr>
													<tr>
														<td width="120px">Departamento:</td> 
														<td>
															<form:select path="codDepartamento" cssClass="select" cssStyle="width: 200px;" disabled="${readonlyEdit}">
																<form:option value="">-Seleccione-</form:option>
																<form:options items="${formato12DCBean.listaDepartamentos}" itemLabel="nomUbigeo" itemValue="codUbigeo"/>
															</form:select>			
															
																														
														</td>
				   										<td width="10px"></td>
				   										<td width="120px">Provincia:</td>
				   										<td>
				   											<form:select path="codProvincia" cssClass="select" cssStyle="width: 200px;" disabled="${readonlyEdit}">
																<form:option value="">-Seleccione-</form:option>																					
															</form:select>
															
															
				   										</td>
				   										<td width="10px"></td>
				   										<td width="120px">Distrito:</td>
				   										<td>
				   											<form:select path="codDistrito" cssClass="select" cssStyle="width: 200px;" disabled="${readonlyEdit}">
																<form:option value="">-Seleccione-</form:option>																					
															</form:select>
															
															
				   										</td>
				   										<td width="10px"></td>
				   										<td width="120px">Localidad:</td>
				   										<td>
				   											<form:input path="localidad" disabled="${readonlyEdit}" maxlength="50" onkeypress="return soloLetras(event);" />
				   										</td>
													</tr>
													
													<tr>
														<td width="120px">Zona Beneficiario:</td> 
														<td>
															<form:select path="zonaBenef" cssClass="select" cssStyle="width: 200px;" disabled="${readonlyEdit}">
																<form:option value="">-Seleccione-</form:option>
																<form:options items="${formato12DCBean.listaZonaBenef}"/>
															</form:select>																			
														</td>
				   										<td width="10px"></td>
				   										<td width="120px">Cuenta contable:</td>
				   										<td>
				   											<form:input path="codCuentaContable" disabled="${readonlyEdit}" maxlength="30" onkeypress="return soloLetras(event);"  />
				   										</td>
				   										<td width="10px"></td>
				   										<td width="120px">Gasto:</td>
				   										<td>
				   											<form:input path="gasto" disabled="${readonlyEdit}" maxlength="250" />
				   										</td>
				   										<td width="10px"></td>
				   										<td width="120px">Tipo gasto:</td>
				   										<td>
				   											<form:select path="tipoGasto" cssClass="select" cssStyle="width: 200px;" disabled="${readonlyEdit}">
																<form:option value="">-Seleccione-</form:option>
																<form:options items="${formato12DCBean.listaTipoGasto}" />
															</form:select>	
				   										</td>
													</tr>
													
													<tr>
														<td width="120px">Tipo doc. Referencia:</td> 
														<td>
															<form:select path="tipoDocumento" cssClass="select" cssStyle="width: 200px;" disabled="${readonlyEdit}">
																<form:option value="">-Seleccione-</form:option>
																<form:options items="${formato12DCBean.listaTipoDocumento}" />
															</form:select>																			
														</td>
				   										<td width="10px"></td>
				   										<td width="120px">RUC empresa:</td>
				   										<td>
				   											<form:input path="rucEmpresa" disabled="${readonlyEdit}" maxlength="11" onkeypress="return soloNumerosDecimales(event, 1, 'rucEmpresa',11,0)" />
				   										</td>
				   										<td width="10px"></td>
				   										<td width="120px">Serie doc. Referencia:</td>
				   										<td>
				   											<form:input path="serieDocumento" disabled="${readonlyEdit}" maxlength="6" onkeypress="return soloLetras(event);" />
				   										</td>
				   										<td width="10px"></td>
				   										<td width="120px">Nro. doc. Referencia:</td>
				   										<td>
				   											<form:input path="nroDocumento" disabled="${readonlyEdit}" maxlength="20" onkeypress="return soloNumerosDecimales(event, 1, 'nroDocumento',20,0)" />
				   										</td>
													</tr>
													<tr>
														<td width="120px">Fecha autor.:</td>
				   										<td>
				   											<form:input path="fechaAutorizacionString" disabled="${readonlyEdit}" cssStyle="text-align: right;"  cssClass="${readonlyEdit==true?'':'datepicker'}" maxlength="10" />
				   										</td>
				   										<td width="10px"></td>
				   										<td width="120px">Nro. doc. autor.:</td>
				   										<td>
				   											<form:input path="nroDocAutorizacion" disabled="${readonlyEdit}" cssStyle="text-align: right;" maxlength="20" onkeypress="return soloNumerosDecimales(event, 1, 'nroDocAutorizacion',20,0)" />
				   										</td>
				   										<td width="10px"></td>
				   										<td width="120px">Cantidad:</td>
				   										<td>
				   											<form:input path="cantidad" cssClass="target" disabled="${readonlyEdit}" cssStyle="text-align: right;" />
				   										</td>
				   										<td width="10px"></td>
				   										<td width="120px">Costo unitario:</td>
				   										<td>
				   											<form:input path="costoUnitario" cssClass="target" disabled="${readonlyEdit}" cssStyle="text-align: right;" />
				   										</td>
													</tr>
													<tr>
														<td width="120px">Total:</td> 
														<td>
															<form:input path="totalGeneral" disabled="true" cssStyle="text-align: right;" />																			
														</td>
													</tr>
													
													
													

											
													<tr height="10px">
														<td colspan="11">
															<!-- luego eliminar --> <!-- fin eliminar -->
														</td>
													</tr>
													<tr class="filete-top">
														<td colspan="11">
															<table style="width: 100%">
																<tr>																	
																	<td>
																		<table style="width: 100%">
																			<tr>
																				<c:if test="${not readonlyEdit}">	
																					<td width="17%" align="center">
																						<input type="button" class="net-button-small" id="<portlet:namespace/>guardarDetalle"
																						name="<portlet:namespace/>guardarDetalle" value="Grabar" />
																					</td>
																				</c:if>
																				<td width="17%" align="center">
																					<input type="button" class="net-button-small" id="<portlet:namespace/>regresarFormato"
																						name="<portlet:namespace/>regresarFormato" value="Regresar" />
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
														<td colspan="11"></td>
													</tr>
													<tr height="10px">
														<td colspan="11"></td>
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
	
	<div id="<portlet:namespace/>dialog-message-detalle" title="Mensaje de Informaci&oacute;n">
		<p>
			<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;">
			</span>
			<label id="<portlet:namespace/>dialog-message-detalle-content">Datos grabados exit&oacute;samente.</label>
		</p>	
	</div>
	
	<form:input path="codEdelnor" cssStyle="display:none;" />	 
	<form:input path="codLuzSur" cssStyle="display:none;" />
	
</form:form>