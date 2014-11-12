<%
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/WEB-INF/jsps/gart/ext/extFormato12A.jsp"%>
<portlet:defineObjects />
<portlet:actionURL var="accionURL" name="actionNormal">  
	<portlet:param name="action" value="cargar" />
</portlet:actionURL>

<link href="/fise-projects-portlet/css/tablas.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/fise-projects-portlet/js/fise.js"></script>

<style>
.inputText-dashed {
	border-style: dashed; 
	border-color: black;
	width:100px;
}
</style>

<form id="form-formatofise12a" name="form-formatofise12a"  method="POST" style="padding:17px;padding-top:0px;" action="${accionURL}"  enctype="multipart/form-data" >	

	<input type="hidden" id="codEmpresaSes" value="${model.codEmpresa}" />
	<input type="hidden" id="anioPresSes" value="${model.anoPres}" />	
	<input type="hidden" id="mesPresSes" value="${model.mesPres}" />	
	<input type="hidden" id="anioEjecSes" value="${model.anoEjec}" />	
	<input type="hidden" id="mesEjecSes" value="${model.mesEjec}" />	
	<input type="hidden" id="etapaSes" value="${model.etapa}" />	
	<!-- valores por defecto -->
	<input type="hidden" id="anioDesdeSes" value="${model.anioDesde}" />
	<input type="hidden" id="mesDesdeSes" value="${model.mesDesde}" />
	<input type="hidden" id="anioHastaSes" value="${model.anioHasta}" />
	<input type="hidden" id="mesHastaSes" value="${model.mesHasta}" />
	<input type="hidden" id="codEtapaSes" value="${model.codEtapa}" />
	
	<input type="hidden" id="mensajeError" value="${model.mensajeError}" />
	<input type="hidden" id="mensajeInfo" value="${model.mensajeInfo}" />

	<input type="hidden" id="Estado" value="" />	
	<input type="hidden" id="flag" value="${model.flag}" />
	
	

	<div id="d_listado" class="net-frame-listado"> 
		<div id="d_filtro">
			<div id="div_contenido" >
				
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
												<td>
													<output>Empresa:</output>
												</td>
												<td colspan="7">
													<select id="s_empresa_b" name="s_empresa_b" style="width:375px;" class="select"  >
							   							<!-- <option value="">-Seleccione-</option> -->
														<c:forEach items="${listaEmpresa}" var="emp">																
															<option value="${emp.codEmpresa}">${emp.dscEmpresa}</option>
														</c:forEach>
													</select>
												</td>
											</tr>
											<tr height="10px">
												<td colspan="8"></td>
											</tr>
											<tr>
												<td>
													<output>Desde año:</output>
												</td>
												<td>
													<input type="text" name="i_anio_d" id="i_anio_d" style="width:50px;text-align:right;" maxlength="4"  >
												</td>
												<td>
													<output>Mes:</output>
												</td>
												<td>
													<select id="s_mes_d" name="s_mes_d"  class="select" style="width:104px;" >
														<c:forEach items="${listaEmpresa}" var="emp">																
															<option value="${emp.codEmpresa}">${emp.dscEmpresa}</option>
														</c:forEach>
													</select>
												</td>
												<td>
													<output>Hasta año:</output>
												</td>
												<td>
													<input type="text" name="i_anio_h" id="i_anio_h" style="width:50px;text-align:right;" maxlength="4" >
												</td>
												<td>
													<output>Mes:</output>
												</td>
												<td>
													<select id="s_mes_h" name="s_mes_h" class="select" style="width:104px;" >
														<option value="">-Seleccione-</option>
														<c:forEach items="${listaMes}" var="mes">																
															<option value="${mes.key}">${mes.value}</option>
														</c:forEach>
													</select>
												</td>
											</tr>
											<tr height="10px">
												<td colspan="8"></td>
											</tr>
											<tr>
												<td>
													<output>Etapa:</output>
												</td>
												<td colspan="7">
													<select id="s_etapa" name="s_etapa" style="width:140px;" class="select" >
														<!-- <option value="">-Seleccione-</option> -->
														<option value="SOLICITUD">SOLICITUD</option>
														<option value="LEV.OBS">LEV.OBS</option>
														<option value="RECONSIDERACION">RECONSIDERACION</option>
														<option value="RECONOCIDO">RECONOCIDO</option>
													</select>
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
												<td>
													<input name="<portlet:namespace/>buscarFormato" id="<portlet:namespace/>buscarFormato" type="button" class="net-button-small" value="Buscar" style="aling:center"/>
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
									<table id="grid_formato">
									</table>
									<div id="pager_formato">
									</div>
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
													<div id="d_opc_crear" >
														<input type="button" class="net-button-small" id="<portlet:namespace/>crearFormato" name="<portlet:namespace/>crearFormato"  value="Nuevo" />
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
					
					<div id="div_formato"  class="net-frame-border" style="display:none;">
						<input type="hidden" id="etapaEdit" value="" />
						<table border="0" width="100%">
							<tr>
								<td>
									<fieldset class="">
									
										<table class="" border="0" width="100%" >
									   		<tr class="filete-bottom">
									   			<td>
									   				<output class="net-titulo">FORMATO FISE-12A: </output>
									   				 Remisión de Gastos Operativos - Implementación
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
									   							<select id="s_empresa" name="s_empresa" style="width:375px;" class="select" >
									   							<!-- <option value="">-Seleccione-</option> -->
																<c:forEach items="${listaEmpresaNew}" var="emp">																
																	<option value="${emp.codEmpresa}">${emp.dscEmpresa}</option>
																</c:forEach>
															</select>
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
									   											<output class="net-titulo">Mes, Ano y Etapa de Presentación:</output>
									   										</td>
									   									</tr>
									   									<tr>
									   										<td colspan="5">
									   											<select id="s_periodoenvio_present" name="s_periodoenvio_present" class="select" style="width:300px;" >
																					<c:forEach items="${listaPeriodoEnvio}" var="periodo">																
																						<option value="${periodo.codigoItem}">${periodo.descripcionItem}</option>
																					</c:forEach>
																				</select>
									   										</td>
									   									</tr>
									   									<%-- <tr>
									   										<td width="40px">Año:</td>
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
									   						<td width="10%">
									   							<input type="hidden" id="flagPeriodoEjecucion" value="" />
									   							<div id="divPrueba" style="display:none;">
									   								hola mundo
									   							</div>
									   						</td>
									   						<td width="45%">
									   							<div id="divPeriodoEjecucion" style="display:none;" >
									   								<fieldset class="net-frame-border" >
										   								<table>
										   									<tr>
										   										<td colspan="5">
										   											<output class="net-titulo">Periodo a ejecución</output>
										   										</td>
										   									</tr>
										   									<tr>
										   										<td width="40px">Año:</td>
										   										<td>
										   											<input type="text" name="i_anioejecuc" id="i_anioejecuc" style="width:50px" maxlength="4" >
										   										</td>
										   										<td width="10px" ></td>
										   										<td width="40px">Mes:</td>
										   										<td>
										   											<select id="s_mes_ejecuc" name="s_mes_ejecuc" class="select" style="width:104px;" >
																						<option value="">-Seleccione-</option>
																						<c:forEach items="${listaMes}" var="mes">																
																							<option value="${mes.key}">${mes.value}</option>
																						</c:forEach>
																					</select>
										   										</td>
										   									</tr>
										   								</table>
										   							</fieldset>
									   							</div>
									   							
									   							
									   							<c:if test="${flagMostrarPeriodoEjecucion=='S' }">
									   								<fieldset class="net-frame-border" >
										   								<table>
										   									<tr>
										   										<td colspan="5">
										   											<output class="net-titulo">Periodo a ejecución</output>
										   										</td>
										   									</tr>
										   									<tr>
										   										<td width="40px">Año:</td>
										   										<td>
										   											<input type="text" name="i_anioejecuc" id="i_anioejecuc" style="width:50px" maxlength="4" >
										   										</td>
										   										<td width="10px" ></td>
										   										<td width="40px">Mes:</td>
										   										<td>
										   											<select id="s_mes_ejecuc" name="s_mes_ejecuc" class="select" style="width:104px;" >
																						<option value="">-Seleccione-</option>
																						<c:forEach items="${listaMes}" var="mes">																
																							<option value="${mes.key}">${mes.value}</option>
																						</c:forEach>
																					</select>
										   										</td>
										   									</tr>
										   								</table>
										   							</fieldset>
									   							</c:if>
									   							
									   						</td>
									   					</tr>
									   				</table>
									   			</td>
									   		</tr>
									   		<tr height="10px"  class="filete-bottom">
												<td></td>
											</tr>
											<tr height="10px">
												<td></td>
											</tr>
									   		<tr>
									   			<td>
									   				<table class="" style="width: 100%;" border="0">
									   				
									   					<tr>
									   						<td width="300px">
									   							<output class="net-titulo">Actividades</output>
									   						</td>
									   						<td colspan="3">
									   							<table style="width: 100%;" border="0" >
									   								<tr>
									   									<td colspan="3" align="center" >
									   										<output class="net-titulo">Grupo de Beneficiarios según  Sector de distribución típico</output>
									   									</td>
									   								</tr>
									   								<tr>
									   									<td width="100px" align="center" >
									   										<output class="net-titulo">Rural</output>
									   									</td>
									   									<td width="100px" align="center" >
									   										<output class="net-titulo">Urbano Provincias</output>
									   									</td>
									   									<td width="100px" align="center" >
									   										<output class="net-titulo">Urbano Lima</output>
									   									</td>
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
									   					<tr height="10px" >
									   						<td colspan="4">
									   						</td>
									   					</tr>
									   					<tr height="10px" class="filete-top">
									   						<td colspan="4">
									   						</td>
									   					</tr>
									   					<tr>
									   						<td colspan="4">
									   						1. Empadronamiento
									   						</td>
									   					</tr>
									   					<tr>
									   						<td colspan="4">
									   						</td>
									   					</tr>
									   					<tr>
									   						<td>
									   						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.1 Número de Empadronados
									   						</td>
									   						<td align="center">
									   							<input type="text" class="fise-editable" name="i_nroEmpad_r" id="i_nroEmpad_r" style="width:100px;"  >
									   						</td>
									   						<td align="center">
									   							<input type="text" class="fise-editable" name="i_nroEmpad_p" id="i_nroEmpad_p" style="width:100px;" >
									   						</td>
									   						<td align="center">
									   							<input type="text" class="fise-editable" name="i_nroEmpad_l" id="i_nroEmpad_l" style="width:100px;"  >
									   						</td>
									   					</tr>
									   					<tr>
									   						<td>
									   						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.2 Costo Estándar Unitario
									   						</td>
									   						<td align="center">
									   							<input type="text" name="i_costoUnitEmpad_r" id="i_costoUnitEmpad_r" style="width:100px;" >
									   						</td>
									   						<td align="center">
									   							<input type="text" name="i_costoUnitEmpad_p" id="i_costoUnitEmpad_p" style="width:100px;" >
									   						</td>
									   						<td align="center">
									   							<input type="text" name="i_costoUnitEmpad_l" id="i_costoUnitEmpad_l" style="width:100px;" >
									   						</td>
									   					</tr>
									   					<tr>
									   						<td>
									   						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.3 Costo Total Empadronamiento
									   						</td>
									   						<td align="center">
									   							<input type="text" name="i_costoTotalEmpad_r" id="i_costoTotalEmpad_r" class="inputText-dashed">
									   						</td>
									   						<td align="center">
									   							<input type="text" name="i_costoTotalEmpad_r" id="i_costoTotalEmpad_p" class="inputText-dashed" >
									   						</td>
									   						<td align="center">
									   							<input type="text" name="i_costoTotalEmpad_r" id="i_costoTotalEmpad_l" class="inputText-dashed" >
									   						</td>
									   					</tr>
									   					<tr>
									   						<td colspan="4">
									   						</td>
									   					</tr>
									   					<tr>
									   						<td colspan="4">
									   						2. Gestión de Red Agentes GLP
									   						</td>
									   					</tr>
									   					<tr>
									   						<td colspan="4">
									   						</td>
									   					</tr>
									   					<tr>
									   						<td>
									   						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.1 Número de Agentes Autorizados GLP
									   						</td>
									   						<td align="center">
									   							<input type="text" class="fise-editable" name="i_nroAgentGlp_r" id="i_nroAgentGlp_r" style="width:100px;" >
									   						</td>
									   						<td align="center">
									   							<input type="text" class="fise-editable" name="i_nroAgentGlp_p" id="i_nroAgentGlp_p" style="width:100px;" >
									   						</td>
									   						<td align="center">
									   							<input type="text" class="fise-editable" name="i_nroAgentGlp_l" id="i_nroAgentGlp_l" style="width:100px;" >
									   						</td>
									   					</tr>
									   					<tr>
									   						<td>
									   						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.2 Costo Estándar Unitario
									   						</td>
									   						<td align="center">
									   							<input type="text" name="i_costoUnitAgent_r" id="i_costoUnitAgent_r" style="width:100px;" >
									   						</td>
									   						<td align="center">
									   							<input type="text" name="i_costoUnitAgent_p" id="i_costoUnitAgent_p" style="width:100px;" >
									   						</td>
									   						<td align="center">
									   							<input type="text" name="i_costoUnitAgent_l" id="i_costoUnitAgent_l" style="width:100px;" >
									   						</td>
									   					</tr>
									   					<tr>
									   						<td>
									   						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.3 Costo Total Gestión de Red de Agentes GLP
									   						</td>
									   						<td align="center">
									   							<input type="text" name="i_costoTotalAgent_r" id="i_costoTotalAgent_r" class="inputText-dashed" >
									   						</td>
									   						<td align="center">
									   							<input type="text" name="i_costoTotalAgent_p" id="i_costoTotalAgent_p" class="inputText-dashed" >
									   						</td>
									   						<td align="center">
									   							<input type="text" name="i_costoTotalAgent_l" id="i_costoTotalAgent_l" class="inputText-dashed" >
									   						</td>
									   					</tr>
									   					<tr>
									   						<td colspan="4">
									   						</td>
									   					</tr>
									   					<tr>
									   						<td>
									   						3. Desplazamiento de Personal
									   						</td>
									   						<td align="center">
									   							<input type="text" class="fise-editable" name="i_despPersonal_r" id="i_despPersonal_r" style="width:100px;" >
									   						</td>
									   						<td align="center">
									   							<input type="text" class="fise-editable" name="i_despPersonal_p" id="i_despPersonal_p" style="width:100px;" >
									   						</td>
									   						<td align="center">
									   							<input type="text" class="fise-editable" name="i_despPersonal_l" id="i_despPersonal_l" style="width:100px;" >
									   						</td>
									   					</tr>
									   					<tr>
									   						<td>
									   						4. Actividades Extraordinarias
									   						</td>
									   						<td align="center">
									   							<input type="text" class="fise-editable" name="i_activExtraord_r" id="i_activExtraord_r" style="width:100px;" >
									   						</td>
									   						<td align="center">
									   							<input type="text" class="fise-editable" name="i_activExtraord_p" id="i_activExtraord_p" style="width:100px;" >
									   						</td>
									   						<td align="center">
									   							<input type="text" class="fise-editable" name="i_activExtraord_l" id="i_activExtraord_l" style="width:100px;" >
									   						</td>
									   					</tr>
									   					<tr height="10px">
									   						<td colspan="4">
									   						</td>
									   					</tr>
									   					<tr class="filete-top">
									   						<td colspan="4">
									   						</td>
									   					</tr>
									   					<tr height="10px">
									   						<td colspan="4">
									   						</td>
									   					</tr>
									   					<tr>
									   						<td colspan="4">
									   						5. Importe a reconocer a la EDE
									   						</td>
									   					</tr>
									   					<tr>
									   						<td>
									   						Por Empadronamiento
									   						</td>
									   						<td align="center">
									   							<input type="text" name="i_importeEmpad" id="i_importeEmpad" class="inputText-dashed" >
									   						</td>
									   						<td>
									   						</td>
									   						<td>
									   						</td>
									   					</tr>
									   					<tr>
									   						<td>
									   						Por Gestión de Red de Agentes GLP
									   						</td>
									   						<td align="center">
									   							<input type="text" name="i_importeAgent" id="i_importeAgent" class="inputText-dashed" >
									   						</td>
									   						<td>
									   						</td>
									   						<td>
									   						</td>
									   					</tr>
									   					<tr>
									   						<td>
									   						Por Desplazamiento de Personal
									   						</td>
									   						<td align="center">
									   							<input type="text" name="i_importeDespPersonal" id="i_importeDespPersonal" class="inputText-dashed" >
									   						</td>
									   						<td>
									   						</td>
									   						<td>
									   						</td>
									   					</tr>
									   					<tr>
									   						<td>
									   						Por Actividades Extraordinarias
									   						</td>
									   						<td align="center">
									   							<input type="text" name="i_importeActivExtraord" id="i_importeActivExtraord" class="inputText-dashed" >
									   						</td>
									   						<td>
									   						</td>
									   						<td>
									   						</td>
									   					</tr>
									   					<tr>
									   						<td>
									   						6. Total General a Reconocer
									   						</td>
									   						<td align="center">
									   							<input type="text" name="i_totalGeneral" id="i_totalGeneral" class="inputText-dashed" >
									   						</td>
									   						<td>
									   						</td>
									   						<td>
									   						</td>
									   					</tr>
									   					<tr height="10px">
															<td colspan="4">
															<!-- luego eliminar -->
															
															<!-- fin eliminar -->
															</td>
														</tr>
									   					<tr class="filete-top">
									   						<td colspan="4">
									   							<table style="width:100%">
									   								<tr>
									   									<td width="25%">
									   										<fieldset id="panelCargaArchivos"  class="net-frame-border">
									   											<legend>Subir de:  </legend>		
									   											<table style="width:100%">
									   												<tr>
									   													<td width="50%" align="center">
									   														<input type="button" class="net-button-small"   id="<portlet:namespace/>cargaExcel" name="<portlet:namespace/>cargaExcel" value="EXCEL" />
									   													</td>
									   													<td width="50%" align="center">
									   														<input type="button" class="net-button-small"   id="<portlet:namespace/>cargaTxt" name="<portlet:namespace/>cargaTxt" value="TXT" />
									   													</td>
									   												</tr>
									   											</table>
									   										</fieldset>
									   									</td>
									   									<td width="10%">
									   									</td>
									   									<td width="65%">
									   										<table style="width:100%">
								   												<tr>
								   													<td width="16%" align="center">
								   														<input type="button" class="boton" name="<portlet:namespace/>reportePdf" style="display:none;" 
								   															id="<portlet:namespace/>reportePdf" class="button net-button-small"  value="Imprimir PDF"/>
								   													</td>
								   													<td width="16%" align="center">
								   														<input type="button" class="boton" name="<portlet:namespace/>reporteExcel" style="display:none;" 
								   															id="<portlet:namespace/>reporteExcel" class="button net-button-small"  value="Exportar excel"/>
								   													</td>
								   													<td width="17%" align="center">
								   														<input type="button" class="net-button-small"   id="<portlet:namespace/>guardarFormato" name="<portlet:namespace/>guardarFormato" value="Grabar" />
								   													</td>
								   													<td width="17%" align="center">
								   														<input type="button" class="net-button-small"   id="<portlet:namespace/>validacionFormato" name="<portlet:namespace/>validacionFormato" value="Validación" />
								   													</td>
								   													<td width="17%" align="center">
								   														<input type="button" class="net-button-small"   id="<portlet:namespace/>envioDefinitivo" name="<portlet:namespace/>envioDefinitivo" value="Envío Def." />
								   													</td>
								   													<td width="17%" align="center">
								   														<input type="button" class="net-button-small" id="<portlet:namespace/>regresarFormato" name="<portlet:namespace/>regresarFormato" value="Regresar" />
								   													</td>
								   												</tr>
									   											</table>
									   										
									   									</td>
									   								</tr>
									   							</table>
									   						
									   							<input type="hidden" id="flagCarga" name="flagCarga" value="" style="display:none;"  />
									   							
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
					<div id="dialog-form-cargaExcel" class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable"  
						style="display: none;z-index:1002;position:absolute;width:400px;" >
						<div class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix">
						<span class="ui-dialog-title" id="ui-dialog-title-dialog-form-carga"> Cargar archivo excel </span>
							<a href="#" class="ui-dialog-titlebar-close ui-corner-all" role="button" onclick="regresarFormularioCargaExcel();">
								<span class="ui-icon ui-icon-closethick">close</span>
							</a>
						
						</div>
					
						<div class="ui-dialog-content ui-widget-content" > 
							<!--tabla-->
							
							<fieldset class="">
							<table style="width:100%;">
								<tr>
									<td></td>
								</tr>
								<tr>
									<td>Archivo:</td>
									<td>
										<input  type="file" id="archivoExcel"name="archivoExcel"/>
									</td>
								</tr>
							</table>
						</fieldset>
							
							
						</div>

						<div class="ui-dialog-buttonpane ui-widget-content ui-helper-clearfix">
							<div class="ui-dialog-buttonset">
								<input type="button" class="net-button-small" name="<portlet:namespace/>cargarFormatoExcel" id="<portlet:namespace/>cargarFormatoExcel" value="Cargar"/>
								<input type="button" class="net-button-small" name="<portlet:namespace/>cerrarFormatoExcel" 
									id="<portlet:namespace/>cerrarFormatoExcel" value="Cerrar" onclick="regresarFormularioCargaExcel();"/>
							</div>
						</div>
					</div>
					<!-- prueba fin -->
					
					<!-- prueba inicio -->
					<div id="dialog-form-cargaTexto" class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable" 
						style="display: none;z-index:1002;position:absolute;width:400px;" >
						<div class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix">
							<span class="ui-dialog-title" id="ui-dialog-title-dialog-form-carga"> Cargar archivo de texto </span>
							<a href="#" class="ui-dialog-titlebar-close ui-corner-all" role="button" onclick="regresarFormularioCargaTexto();">
								<span class="ui-icon ui-icon-closethick" onmouseover="ui-state-hover">close</span>
							</a>
						</div>
						<div class="ui-dialog-content ui-widget-content"  > 
							<!--tabla-->
							
							<fieldset class="">
							<table style="width:100%;">
								<tr>
									<td></td>
								</tr>
								<tr>
									<td>Archivo:</td>
									<td>
										<input  type="file" id="archivoTxt"name="archivoTxt"/>
									</td>
								</tr>
							</table>
						</fieldset>
							
							
						</div>
						

						<div class="ui-dialog-buttonpane ui-widget-content ui-helper-clearfix">
							<div class="ui-dialog-buttonset">
								<input type="button" class="net-button-small" name="<portlet:namespace/>cargarFormatoTexto" id="<portlet:namespace/>cargarFormatoTexto" value="Cargar"/>
								<input type="button" class="net-button-small" name="<portlet:namespace/>cerrarFormatoTexto" 
									id="<portlet:namespace/>cerrarFormatoTexto" value="Cerrar" onclick="regresarFormularioCargaTexto();"/>
							</div>
						</div>
					</div>
					<!-- prueba fin -->
					
					<div id="dialog-form-error" class="net-frame-border" style="display:none;background:#fff;" title=" Errores de archivo de carga ">				
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
					
					<div id="dialog-form-observacion" class="net-frame-border" style="display:none;background:#fff;" title=" Resultados de validación ">				
						<fieldset class="net-frame-border">							
							<%-- <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tabla">
								<tr class="titulo_tabla">
				            		<td width="40">Nro.</td>
				            		<td width="80">Grupo Zona</td>
				            		<td width="378" height="37">Descripción</td>
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
				            		<td width="378" height="37">Descripción</td>
							   </tr>
						      </thead>
						      <tbody>
							    <tr>
								  
							    </tr>
						     </tbody>
				        </table> -->
				        <table id="grid_observacion" width="100%">
							</table>
				        <div id="pager_observacion">
						</div>
						</fieldset>
						<br>
					</div>			
					
					<!-- fin -->

				</div>
			</div>
		</div>


	<div id="dialog-message" title="Osinergmin">
		<p>
			<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;">
			</span>
			<label id="dialog-message-content">Datos grabados exit&oacute;samente.</label>
		</p>	
	</div>
	
	
	<div id="dialog-confirm" title="Confirmar acci&oacute;n">
		<p>	
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
			<label id="dialog-confirm-content">¿Está seguro?</label>
		</p>
	</div>`
	
	<div id="dialog-confirm-envio" title="Confirmar acci&oacute;n">
		<p>	
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
			<label id="dialog-confirm-envio-content">¿Está seguro?</label>
		</p>
	</div>
	
	<div id="dialog-message-report" title="Osinergmin">
		<p>
			<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;">
			</span>
			<label id="dialog-message-report-content">Datos grabados exit&oacute;samente.</label>
		</p>	
	</div>

	<div id="divOverlay" class="ui-widget-overlay" style="display:none;width: 100%; height: 100%; z-index: 1001;">
	</div>

</form> 
