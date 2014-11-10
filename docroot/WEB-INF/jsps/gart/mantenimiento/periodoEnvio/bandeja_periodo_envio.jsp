<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<link href="/fise-projects-portlet/css/tablas.css" rel="stylesheet" type="text/css">


<style>
.inputText-dashed {
	border-style: dashed; 
	border-color: black;
	width:100px;
}
</style>
<form:form id="form_bndj_periodo" name="form_bndj_periodo"  method="POST" style="padding:17px;padding-top:0px;"  enctype="multipart/form-data" modelAttribute="periodoEnvioCommand">	
<div id="d_filtro" class="net-frame-listado"> 
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
													<form:select path="codEmpresa" id="s_empresa_b" name="s_empresa_b" style="width:375px;" class="select"  >
							   							<c:if test="${esAdministrador}">
														<form:option value="">-Todos-</form:option>
													</c:if>
													<form:options items="${periodoEnvioCommand.listaEmpresas}"  itemLabel="dscEmpresa" itemValue="codEmpresa"/>
														
													</form:select>
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
													<form:input path="anioInicio" type="text" name="i_anio_d" id="i_anio_d" style="width:50px;text-align:right;" maxlength="4" />
												</td>
												<td>
													<output>Mes:</output>
												</td>
												<td>
													<form:select path="mesInicio" id="s_mes_d" name="s_mes_d"  class="select" style="width:104px;" >
														<form:option value="">-Seleccione-</form:option>
													<form:options items="${periodoEnvioCommand.listaMes}"/>
													</form:select>
												</td>
												<td>
													<output>Hasta año:</output>
												</td>
												<td>
													<form:input path="anioFin" type="text" name="i_anio_h" id="i_anio_h" style="width:50px;text-align:right;" maxlength="4" />
												</td>
												<td>
													<output>Mes:</output>
												</td>
												<td>
													<form:select path="mesFin" id="s_mes_h" name="s_mes_h" class="select" style="width:104px;" >
														<form:option value="">-Seleccione-</form:option>
													<form:options items="${periodoEnvioCommand.listaMes}"/>
													</form:select>
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
													<form:select path="etapa" id="s_etapa" name="s_etapa" style="width:140px;" class="select" >
														<!-- <option value="">-Seleccione-</option> -->
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
												<td>
													<input name="<portlet:namespace/>buscarPeriodoEnvio" id="<portlet:namespace/>buscarPeriodoEnvio" type="button" class="net-button-small" value="Buscar" style="aling:center"/>
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
														<input type="button" class="net-button-small" id="<portlet:namespace/>nuevoPeriodoEnvio" name="<portlet:namespace/>nuevoPeriodoEnvio"  value="Nuevo" />
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