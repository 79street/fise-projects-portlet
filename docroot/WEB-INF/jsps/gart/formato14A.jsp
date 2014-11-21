<%@include file="/WEB-INF/jsps/gart/ext/extFormato14A.jsp"%>

<portlet:actionURL var="accionURL" name="actionNormal">
	<portlet:param name="action" value="cargar" />

</portlet:actionURL>


<link href="/fise-projects-portlet/css/tablas.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/fise-projects-portlet/js/fise.js"></script>

<script type="text/javascript">
$(document).ready(function () {	
	formato14A.init();
});
</script>

<style>
</style>

<form:form method="POST" modelAttribute="formato14ACBean"  style="padding: 17px; padding-top: 0px;" action="${accionURL}" enctype="multipart/form-data" >

	<input type="hidden" id="<portlet:namespace/>codEmpresaSes" value="${model.codEmpresa}" />
	<input type="hidden" id="<portlet:namespace/>anioPresSes" value="${model.anoPres}" />	
	<input type="hidden" id="<portlet:namespace/>mesPresSes" value="${model.mesPres}" />	
	<input type="hidden" id="<portlet:namespace/>anioIniVigSes" value="${model.anoIniVig}" />	
	<input type="hidden" id="<portlet:namespace/>anioFinVigSes" value="${model.anoFinVig}" />	
	<input type="hidden" id="<portlet:namespace/>etapaSes" value="${model.etapa}" />	
	<!-- valores por defecto -->
<%-- 	<input type="hidden" id="<portlet:namespace/>anioDesdeSes" value="${model.anioDesde}" />
	<input type="hidden" id="<portlet:namespace/>mesDesdeSes" value="${model.mesDesde}" />
	<input type="hidden" id="<portlet:namespace/>anioHastaSes" value="${model.anioHasta}" />
	<input type="hidden" id="<portlet:namespace/>mesHastaSes" value="${model.mesHasta}" />
	<input type="hidden" id="<portlet:namespace/>codEtapaSes" value="${model.codEtapa}" /> --%>
	
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

													<c:if test="${formato14ACBean.admin}">
														<form:option value="">-Todos-</form:option>
													</c:if>
													<form:options items="${formato14ACBean.listaEmpresas}"  itemLabel="dscEmpresa" itemValue="codEmpresa"/>
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
													<form:options items="${formato14ACBean.listaMes}"/>
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
													<form:options items="${formato14ACBean.listaMes}"/>
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
											<td><output class="net-titulo">FORMATO FISE-14A:
												</output>COSTOS ESTÁNDARES DE IMPLEMENTACIÓN</td>
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
														  <output id="descGrupoInformacion" ></output>
													   </td>
													   <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
													   <td>
														  <label style="font-size: 12px; font-weight:bold">Estado:</label>
														</td>
														
														<td style="text-align: center;">
														  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														  <output id="descEstado"></output>														 
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
															<form:select path="codigoEmpresa" cssClass="select" cssStyle="width: 375px;" >
																<form:options items="${formato14ACBean.listaEmpresas}"  itemLabel="dscEmpresa" itemValue="codEmpresa"/>
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
															<div id="<portlet:namespace/>divPeriodoEjecucion" >
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
														<td width="450px"><output class="net-titulo">Actividades</output>
														</td>
														<td colspan="3">
															<table style="width: 100%;" border="0">
																<tr>
																	<td colspan="3" align="center"><output class="net-titulo">Grupo de Beneficiarios según Zona</output></td>
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
														<td colspan="4">1. Costo de Empadronamiento</td>
													</tr>
													<tr>
														<td colspan="4"></td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.1 Empadronamiento</td>
														<td align="center">
															<form:input path="sumEmpadDifusionR" cssClass="" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
														<td align="center">
															<form:input path="sumEmpadDifusionP" cssClass="" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
														<td align="center">
															<form:input path="sumEmpadDifusionL" cssClass="" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															1.1.1 Empadronamiento</td>
														<td align="center">
															<form:input path="totalEmpadR" cssClass="inputText-dashed" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
														<td align="center">
															<form:input path="totalEmpadP" cssClass="inputText-dashed" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
														<td align="center">
															<form:input path="totalEmpadL" cssClass="inputText-dashed" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															1.1.1.1 Impresión de Esquela de Invitación</td>
														<td align="center">
															<form:input path="imprEsqInvitR" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="imprEsqInvitP" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="imprEsqInvitL" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															1.1.1.2 Impresión de Declaraciones Juradas	</td>
														<td align="center">
															<form:input path="imprDeclaJuradaR" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="imprDeclaJuradaP" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
														<form:input path="imprDeclaJuradaL" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															1.1.1.3 Impresión de Fichas de Verificación</td>
														<td align="center">
															<form:input path="imprFichaVerifR" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="imprFichaVerifP" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="imprFichaVerifL" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															1.1.1.4 Reparto de esquela de invitación, en forma conjunta con el recibo
															<br/>
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															de luz del Potencial Beneficiario
															</td>
														<td align="center">
															<form:input path="repartoEsqInvitR" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="repartoEsqInvitP" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="repartoEsqInvitL" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															1.1.1.5 Verificación de la Información</td>
														<td align="center">
															<form:input path="verifInfoR" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="verifInfoP" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="verifInfoL" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															1.1.1.6 Elaboración del archivo del beneficiario</td>
														<td align="center">
															<form:input path="elabArchivoBenefR" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="elabArchivoBenefP" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="elabArchivoBenefL" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															1.1.1.7 Digitación de Fichas de Beneficiarios</td>
														<td align="center">
															<form:input path="digitFichaBenefR" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="digitFichaBenefP" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="digitFichaBenefL" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															1.1.2 Difusión de Inicio del Programa FISE</td>
														<td align="center">
															<form:input path="totalDifIniProgR" cssClass="inputText-dashed" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
														<td align="center">
															<form:input path="totalDifIniProgP" cssClass="inputText-dashed" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
														<td align="center">
															<form:input path="totalDifIniProgL" cssClass="inputText-dashed" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															1.1.2.1 Impresión de Volantes</td>
														<td align="center">
															<form:input path="imprVolantesR" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="imprVolantesP" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="imprVolantesL" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															1.1.2.2 Impresión de Afiches</td>
														<td align="center">
															<form:input path="imprAfichesR" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="imprAfichesP" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="imprAfichesL" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															1.1.2.3 Reparto de folletos a los Potenciales Beneficiarios, con el recibo 
															<br/>
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															de luz del beneficiario
															</td>
														<td align="center">
															<form:input path="repFolletosR" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="repFolletosP" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="repFolletosL" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															1.1.2.4 Spot Publicitario en TV</td>
														<td align="center">
															<form:input path="spotPublTvR" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="spotPublTvP" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="spotPublTvL" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															1.1.2.5 Spot Publicitario en Radio</td>
														<td align="center">
															<form:input path="spotPublRadioR" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="spotPublRadioP" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="spotPublRadioL" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.2 Número de Beneficiarios empadronados en el mes de diciembre</td>
														<td align="center">
															<form:input path="nroBenefEmpadR" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="nroBenefEmpadP" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="nroBenefEmpadL" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.3 Costo Unitario por Empadronamiento</td>
														<td align="center">
															<form:input path="costoUnitEmpadR" cssClass="inputText-dashed" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
														<td align="center">
															<form:input path="costoUnitEmpadP" cssClass="inputText-dashed" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
														<td align="center">
															<form:input path="costoUnitEmpadL" cssClass="inputText-dashed" cssStyle="width: 100px; text-align: right;" disabled="true" />
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
														<td colspan="4">2. Costo de Gestión de Red de Agentes GLP</td>
													</tr>
													<tr>
														<td colspan="4"></td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.1 Costo de Gestión de Red de Agentes GLP</td>
														<td align="center">
															<form:input path="totalCostoAgentR" cssClass="inputText-dashed" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
														<td align="center">
															<form:input path="totalCostoAgentP" cssClass="inputText-dashed" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
														<td align="center">
															<form:input path="totalCostoAgentL" cssClass="inputText-dashed" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															2.1.1 Promoción de convenios con Agentes Autorizados de GLP</td>
														<td align="center">
															<form:input path="promConvAgentR" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="promConvAgentP" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="promConvAgentL" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															2.1.2 Registro y Firma de convenios con Agentes Autorizados de GLP</td>
														<td align="center">
															<form:input path="regConvAgentR" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="regConvAgentP" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="regConvAgentL" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															2.1.3 Impresión y entrega de banderola y/o banner</td>
														<td align="center">
															<form:input path="impEntrBandR" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="impEntrBandP" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="impEntrBandL" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.2 Número de Agentes</td>
														<td align="center">
															<form:input path="nroAgentR" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="nroAgentP" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
														<td align="center">
															<form:input path="nroAgentL" cssClass="target" cssStyle="width: 100px; text-align: right;"/>
														</td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.3 Costo Unitario por Agente GLP</td>
														<td align="center">
															<form:input path="costoUnitAgentR" cssClass="inputText-dashed" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
														<td align="center">
															<form:input path="costoUnitAgentP" cssClass="inputText-dashed" cssStyle="width: 100px; text-align: right;" disabled="true" />
														</td>
														<td align="center">
															<form:input path="costoUnitAgentL" cssClass="inputText-dashed" cssStyle="width: 100px; text-align: right;" disabled="true" />
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
																				<td width="13%" align="center">
																					<input type="button" class="boton" name="<portlet:namespace/>reportePdf" style="display: none;"
																						id="<portlet:namespace/>reportePdf" class="button net-button-small" value="Imprimir PDF" />
																				</td>
																				<td width="13%" align="center">
																					<input type="button" class="boton" name="<portlet:namespace/>reporteExcel" style="display: none;"
																						id="<portlet:namespace/>reporteExcel" class="button net-button-small" value="Exportar excel" />
																				</td>
																				<td width="13%" align="center">
																					<input type="button" class="boton" name="<portlet:namespace/>reporteActaEnvio" style="display: none;"
																						id="<portlet:namespace/>reporteActaEnvio" class="button net-button-small" value="Acta de envío" />
																				</td>
																				<td width="16%" align="center">
																					<input type="button" class="net-button-small" id="<portlet:namespace/>guardarFormato"
																						name="<portlet:namespace/>guardarFormato" value="Grabar" />
																				</td>
																				<td width="15%" align="center">
																					<input type="button" class="net-button-small" id="<portlet:namespace/>validacionFormato"
																						name="<portlet:namespace/>validacionFormato" value="Validación" />
																				</td>
																				<td width="15%" align="center">
																					<input type="button" class="net-button-small" id="<portlet:namespace/>envioDefinitivo"
																						name="<portlet:namespace/>envioDefinitivo" value="Envío Def." />
																				</td>
																				<td width="15%" align="center">
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
						<a href="#" class="ui-dialog-titlebar-close ui-corner-all" role="button" onclick="formato14A.regresarFormularioCargaExcel();"> 
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
								id="<portlet:namespace/>cerrarFormatoExcel" value="Cerrar" onclick="formato14A.regresarFormularioCargaExcel();" />
						</div>
					</div>
				</div>
				<!-- prueba fin -->
	
				<!-- prueba inicio -->
				<div id="<portlet:namespace/>dialog-form-cargaTexto" class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable"
						style="display: none; z-index: 1002; position: absolute; width: 400px;">
					<div class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix">
						<span class="ui-dialog-title" id="ui-dialog-title-dialog-form-carga"> Cargar archivo de texto </span> 
						<a href="#" class="ui-dialog-titlebar-close ui-corner-all" role="button" onclick="formato14A.regresarFormularioCargaTexto();"> 
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
								id="<portlet:namespace/>cerrarFormatoTexto" value="Cerrar" onclick="formato14A.regresarFormularioCargaTexto();" />
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
	</div>
	
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
	
	<form:input path="codEdelnor" cssStyle="display:none;" />	 
	<form:input path="codLuzSur" cssStyle="display:none;" />
	
</form:form>