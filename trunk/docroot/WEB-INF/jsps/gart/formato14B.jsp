<%@include file="/WEB-INF/jsps/gart/ext/extFormato14B.jsp"%>

<portlet:actionURL var="accionURL" name="actionNormal">
	<portlet:param name="action" value="cargar" />
</portlet:actionURL>

<link href="/fise-projects-portlet/css/tablas.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/fise-projects-portlet/js/fise.js"></script>

<script type="text/javascript">
$(document).ready(function () {	
	formato14B.init();
});
</script>

<style>
</style>

<form:form method="POST" modelAttribute="formato14BCBean"  
	style="padding: 17px; padding-top: 0px;" action="${accionURL}" enctype="multipart/form-data" >

	<input type="hidden" id="<portlet:namespace/>codEmpresaSes" value="${model.codEmpresa}" />
	<input type="hidden" id="<portlet:namespace/>anioPresSes" value="${model.anoPres}" />	
	<input type="hidden" id="<portlet:namespace/>mesPresSes" value="${model.mesPres}" />	
	<input type="hidden" id="<portlet:namespace/>anioIniVigSes" value="${model.anoIniVig}" />	
	<input type="hidden" id="<portlet:namespace/>anioFinVigSes" value="${model.anoFinVig}" />	
	<input type="hidden" id="<portlet:namespace/>etapaSes" value="${model.etapa}" />	
	
	<input type="hidden" id="<portlet:namespace/>mensajeError" value="${model.mensajeError}" />
	<input type="hidden" id="<portlet:namespace/>mensajeInfo" value="${model.mensajeInfo}" />

	<input type="hidden" id="<portlet:namespace/>Estado" value="" />
	<input type="hidden" id="<portlet:namespace/>flag" value="${model.flag}" />

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
												<form:select path="codEmpresaB" cssClass="select" cssStyle="width: 375px;">

													<c:if test="${formato14BCBean.admin}">
														<form:option value="">-Todos-</form:option>
													</c:if>
													<form:options items="${formato14BCBean.listaEmpresas}"  itemLabel="dscEmpresa" itemValue="codEmpresa"/>
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
													<form:options items="${formato14BCBean.listaMes}"/>
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
													<form:options items="${formato14BCBean.listaMes}"/>
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
											<td><input name="<portlet:namespace/>buscarFormato" id="<portlet:namespace/>buscarFormato" type="button"
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
												<input type="button" class="net-button-small" id="<portlet:namespace/>crearFormato"
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
	
				<div id="<portlet:namespace/>div_formato" class="net-frame-border" style="display: none;">
					<input type="hidden" id="<portlet:namespace/>etapaEdit" value="" />
					<table border="0" width="100%">
						<tr>
							<td>
								<fieldset class="">
	
									<table class="" border="0" width="100%">
										<tr class="filete-bottom">
											<td><output class="net-titulo">FORMATO FISE-14B:
												</output>COSTOS ESTÁNDARES DE IMPLEMENTACIÓN</td>
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
															<form:select path="codigoEmpresa" cssClass="select" cssStyle="width: 375px;" >
																<form:options items="${formato14BCBean.listaEmpresas}"  itemLabel="dscEmpresa" itemValue="codEmpresa"/>
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
																<table style="width: 100%;" border="0">
																	<tr>
																		<td colspan="5"><output class="net-titulo">Mes, Ano y Etapa de Presentación:</output></td>
																	</tr>
																	<tr>
																		<td colspan="5">
																			<form:select path="periodoEnvio" cssClass="select" cssStyle="width: 300px;">
																				<c:forEach items="${listaPeriodoEnvio}" var="periodo">
																					<option value="${periodo.codigoItem}">${periodo.descripcionItem}</option>
																				</c:forEach>
																		</form:select></td>
																	</tr>
																</table>
															</fieldset>
														</td>
														<td width="10%">
															<form:input path="flagPeriodoEjecucion" cssStyle="display:none;" />
														</td>
														<td width="45%">
															<div id="<portlet:namespace/>divPeriodoEjecucion" style="display: none;">
																<fieldset class="net-frame-border">
																	<table>
																		<tr>
																			<td colspan="5"><output class="net-titulo">Periodo de vigencia</output></td>
																		</tr>
																		<tr>
																			<td width="110px">Año Inicio Vigencia:</td>
																			<td>
																				<form:input path="anioInicioVigencia" style="width: 50px" maxlength="4" />
																			</td>
																			<td width="10px"></td>
																			<td width="110px">Año Fin Vigencia:</td>
																			<td>
																				<form:input path="anioFinVigencia" style="width: 50px" maxlength="4" />	
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
																	<td colspan="3" align="center"><output class="net-titulo">Grupo de Beneficiarios según Sector de distribución típico</output></td>
																</tr>
																<tr>
																	<td width="100px" align="center"><output class="net-titulo">Rural</output></td>
																	<td width="100px" align="center"><output class="net-titulo">Urbano Provincias</output></td>
																	<td width="100px" align="center"><output class="net-titulo">Urbano Lima</output></td>
																</tr>
															
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
														<td colspan="4">1. Impresión de Vales</td>
													</tr>
													<tr>
														<td colspan="4"></td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.1 Impresión vales de descuento de clientes EDE</td>
														<td align="center">
															<form:input path="impValDesctoEdeR" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="impValDesctoEdeP" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="impValDesctoEdeL" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.2 Impresión  vales de descuento de clientes no EDE</td>
														<td align="center">
															<form:input path="impValDesctoNoEdeR" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="impValDesctoNoEdeP" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="impValDesctoNoEdeL" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.3 Costo Total Impresión</td>
														<td align="center">
															<form:input path="costoTotalImpR" cssClass="" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
														<td align="center">
															<form:input path="costoTotalImpP" cssClass="" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
														<td align="center">
															<form:input path="costoTotalImpL" cssClass="" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.4 Numero de  Vales Impresos</td>
														<td align="center">
															<form:input path="nroValesImpR" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="nroValesImpP" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="nroValesImpL" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.5 Costo Unitario por Impresión de Vales</td>
														<td align="center">
															<form:input path="costoUnitImpValesR" cssClass="" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
														<td align="center">
															<form:input path="costoUnitImpValesP" cssClass="" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
														<td align="center">
															<form:input path="costoUnitImpValesL" cssClass="" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
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
														<td colspan="4"></td>
													</tr>
													<tr>
														<td colspan="4">2. Reparto de Vales a domicilio</td>
													</tr>
													<tr>
														<td colspan="4"></td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.1 Costo Total Reparto de vales de descuento a clientes de las EDE</td>
														<td align="center">
															<form:input path="costoTotalValDesctoR" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="costoTotalValDesctoP" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="costoTotalValDesctoL" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.2 Número de Vales Repartidos</td>
														<td align="center">
															<form:input path="nroValesReptR" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="nroValesReptP" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="nroValesReptL" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.3 Costo Unitario por Reparto de Vales a Domicilio</td>
														<td align="center">
															<form:input path="costoUnitReptValesR" cssClass="" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
														<td align="center">
															<form:input path="costoUnitReptValesP" cssClass="" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
														<td align="center">
															<form:input path="costoUnitReptValesL" cssClass="" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
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
														<td colspan="4"></td>
													</tr>
													<tr>
														<td colspan="4">3. Entrega de Vales en la EDE</td>
													</tr>
													<tr>
														<td colspan="4"></td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.1 Costo Total Reparto de vales en Oficinas de la EDE o Centros Autorizados</td>
														<td align="center">
															<form:input path="costoTotalValOficR" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="costoTotalValOficP" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="costoTotalValOficL" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.2 Número de Vales Entregados</td>
														<td align="center">
															<form:input path="nroValesEntrR" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="nroValesEntrP" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="nroValesEntrL" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.3 Costo Unitario por Entrega de Vales en la EDE</td>
														<td align="center">
															<form:input path="costoUnitEntrValesR" cssClass="" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
														<td align="center">
															<form:input path="costoUnitEntrValesP" cssClass="" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
														<td align="center">
															<form:input path="costoUnitEntrValesL" cssClass="" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
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
														<td colspan="4"></td>
													</tr>
													<tr>
														<td colspan="4">4. Canje y Liquidación de Vales Físicos</td>
													</tr>
													<tr>
														<td colspan="4"></td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.1 Costo por Enviar padrón de Vales para canje a los Centros Autorizados de Canje</td>
														<td align="center">
															<form:input path="costoEnvPadronR" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="costoEnvPadronP" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="costoEnvPadronL" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.2 Número de Vales Físicos Emitidos</td>
														<td align="center">
															<form:input path="nroValesFisR" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="nroValesFisP" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="nroValesFisL" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.3 Costo Unitario por Canje y Liquidación de Vales Físicos</td>
														<td align="center">
															<form:input path="costoUnitLiqR" cssClass="" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
														<td align="center">
															<form:input path="costoUnitLiqP" cssClass="" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
														<td align="center">
															<form:input path="costoUnitLiqL" cssClass="" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
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
														<td colspan="4"></td>
													</tr>
													<tr>
														<td colspan="4">5. Canje y Liquidación de Vales Digitales</td>
													</tr>
													<tr>
														<td colspan="4"></td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5.1 Costo Unitario por Canje de Vales Digitales mediante Banca Celular</td>
														<td align="center">
															<form:input path="costoUnitValesDigitR" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="costoUnitValesDigitP" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="costoUnitValesDigitL" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
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
														<td colspan="4"></td>
													</tr>
													<tr>
														<td colspan="4">6. Atención de Solicitudes, Consultas y/o Reclamos</td>
													</tr>
													<tr>
														<td colspan="4"></td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6.1 Costo por Atención de Solicitudes</td>
														<td align="center">
															<form:input path="costoAtenSolicR" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="costoAtenSolicP" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="costoAtenSolicL" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6.2 Costo por Atención de Consultas y/o Reclamos</td>
														<td align="center">
															<form:input path="costoAtenConsR" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="costoAtenConsP" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="costoAtenConsL" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6.3 Costo Total de Atenciones</td>
														<td align="center">
															<form:input path="costoTotalAtenR" cssClass="" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
														<td align="center">
															<form:input path="costoTotalAtenP" cssClass="" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
														<td align="center">
															<form:input path="costoTotalAtenL" cssClass="" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6.4 Número Total de Atenciones</td>
														<td align="center">
															<form:input path="nroTotalAtenR" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="nroTotalAtenP" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="nroTotalAtenL" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6.5 Costo Unitario por Atención</td>
														<td align="center">
															<form:input path="costoUnitAtenR" cssClass="" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
														<td align="center">
															<form:input path="costoUnitAtenP" cssClass="" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
														<td align="center">
															<form:input path="costoUnitAtenL" cssClass="" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
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
														<td colspan="4"></td>
													</tr>
													<tr>
														<td colspan="4">7. Gestión Administrativa</td>
													</tr>
													<tr>
														<td colspan="4"></td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;7.1 Costo de Personal</td>
														<td align="center">
															<form:input path="costoPersonalR" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="costoPersonalP" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="costoPersonalL" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;7.2 Capacitación de los Agentes Autorizados de GLP</td>
														<td align="center">
															<form:input path="capacAgentR" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="capacAgentP" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="capacAgentL" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;7.3 Útiles y materiales de oficina</td>
														<td align="center">
															<form:input path="utilMatOficR" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="utilMatOficP" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="utilMatOficL" cssClass="target fise-editable" cssStyle="width: 100px; text-align: right;"/>
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;7.4 Costo Total por Gestión Administrativa</td>
														<td align="center">
															<form:input path="costoTotalGestR" cssClass="" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
														<td align="center">
															<form:input path="costoTotalGestP" cssClass="" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
														<td align="center">
															<form:input path="costoTotalGestL" cssClass="" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
													</tr>

													
													<tr height="10px">
														<td colspan="4"></td>
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
																		<fieldset id="<portlet:namespace/>panelCargaArchivos"
																			class="net-frame-border">
																			<legend>Subir de: </legend>
																			<table style="width: 100%">
																				<tr>
																					<td width="50%" align="center">
																						<input type="button" class="net-button-small" id="<portlet:namespace/>cargaExcel"
																							name="<portlet:namespace/>cargaExcel" value="EXCEL" />
																					</td>
																					<td width="50%" align="center">
																						<input type="button" class="net-button-small" id="<portlet:namespace/>cargaTxt"
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
																				<td width="16%" align="center">
																					<input type="button" class="boton" name="<portlet:namespace/>reportePdf" style="display: none;"
																						id="<portlet:namespace/>reportePdf" class="button net-button-small" value="Imprimir PDF" />
																				</td>
																				<td width="16%" align="center">
																					<input type="button" class="boton" name="<portlet:namespace/>reporteExcel" style="display: none;"
																						id="<portlet:namespace/>reporteExcel" class="button net-button-small" value="Exportar excel" />
																				</td>
																				<td width="17%" align="center">
																					<input type="button" class="net-button-small" id="<portlet:namespace/>guardarFormato"
																						name="<portlet:namespace/>guardarFormato" value="Grabar" />
																				</td>
																				<td width="17%" align="center">
																					<input type="button" class="net-button-small" id="<portlet:namespace/>validacionFormato"
																						name="<portlet:namespace/>validacionFormato" value="Validación" />
																				</td>
																				<td width="17%" align="center">
																					<input type="button" class="net-button-small" id="<portlet:namespace/>envioDefinitivo"
																						name="<portlet:namespace/>envioDefinitivo" value="Envío Def." />
																				</td>
																				<td width="17%" align="center">
																					<input type="button" class="net-button-small" id="<portlet:namespace/>regresarFormato" 
																						name="<portlet:namespace/>regresarFormato" value="Regresar" />
																				</td>
																			</tr>
																		</table>
	
																	</td>
																</tr>
															</table> <input type="hidden" id="<portlet:namespace/>flagCarga" name="flagCarga" value="" style="display: none;" />
	
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
				<div id="<portlet:namespace/>dialog-form-cargaExcel" class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable"
						style="display: none; z-index: 1002; position: absolute; width: 400px;">
					<div class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix">
						<span class="ui-dialog-title" id="ui-dialog-title-dialog-form-carga"> Cargar archivo excel </span> 
						<a href="#" class="ui-dialog-titlebar-close ui-corner-all" role="button" onclick="formato14B.regresarFormularioCargaExcel();"> 
							<span class="ui-icon ui-icon-closethick">close</span>
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
	
					<div class="ui-dialog-buttonpane ui-widget-content ui-helper-clearfix">
						<div class="ui-dialog-buttonset">
							<input type="button" class="net-button-small" name="<portlet:namespace/>cargarFormatoExcel"
								id="<portlet:namespace/>cargarFormatoExcel" value="Cargar" /> 
							<input type="button" class="net-button-small" name="<portlet:namespace/>cerrarFormatoExcel"
								id="<portlet:namespace/>cerrarFormatoExcel" value="Cerrar" onclick="formato14B.regresarFormularioCargaExcel();" />
						</div>
					</div>
				</div>
				<!-- prueba fin -->
	
				<!-- prueba inicio -->
				<div id="<portlet:namespace/>dialog-form-cargaTexto" class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable"
						style="display: none; z-index: 1002; position: absolute; width: 400px;">
					<div class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix">
						<span class="ui-dialog-title" id="ui-dialog-title-dialog-form-carga"> Cargar archivo de texto </span> 
						<a href="#" class="ui-dialog-titlebar-close ui-corner-all" role="button" onclick="formato14B.regresarFormularioCargaTexto();"> 
						<span class="ui-icon ui-icon-closethick" onmouseover="ui-state-hover">close</span>
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
	
	
					<div class="ui-dialog-buttonpane ui-widget-content ui-helper-clearfix">
						<div class="ui-dialog-buttonset">
							<input type="button" class="net-button-small" name="<portlet:namespace/>cargarFormatoTexto"
								id="<portlet:namespace/>cargarFormatoTexto" value="Cargar" /> 
							<input type="button" class="net-button-small" name="<portlet:namespace/>cerrarFormatoTexto"
								id="<portlet:namespace/>cerrarFormatoTexto" value="Cerrar" onclick="formato14B.regresarFormularioCargaTexto();" />
						</div>
					</div>
				</div>
				<!-- prueba fin -->
	
				<div id="<portlet:namespace/>dialog-form-error" class="net-frame-border" style="display: none; background: #fff;" title=" Errores de archivo de carga ">
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
	
				<!-- fin -->
	
			</div>
		</div>
	</div>
	
	
	<div id="<portlet:namespace/>dialog-message" title="Osinergmin">
		<p>
			<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;">
			</span>
			<label id="<portlet:namespace/>dialog-message-content">Datos grabados exit&oacute;samente.</label>
		</p>	
	</div>
	
	
	<div id="<portlet:namespace/>dialog-confirm" title="Confirmar acci&oacute;n">
		<p>	
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
			<label id="<portlet:namespace/>dialog-confirm-content">¿Está seguro?</label>
		</p>
	</div>`
	
	<div id="<portlet:namespace/>dialog-confirm-envio" title="Confirmar acci&oacute;n">
		<p>	
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
			<label id="<portlet:namespace/>dialog-confirm-envio-content">¿Está seguro?</label>
		</p>
	</div>
	
	<div id="<portlet:namespace/>dialog-message-report" title="Osinergmin">
		<p>
			<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;">
			</span>
			<label id="<portlet:namespace/>dialog-message-report-content">Datos grabados exit&oacute;samente.</label>
		</p>	
	</div>

	<div id="<portlet:namespace/>divOverlay" class="ui-widget-overlay" style="display:none;width: 100%; height: 100%; z-index: 1001;">
	</div>
	
</form:form>