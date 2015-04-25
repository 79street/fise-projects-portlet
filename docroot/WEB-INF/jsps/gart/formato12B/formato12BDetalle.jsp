<%@include file="/WEB-INF/jsps/gart/ext/extFormato12B.jsp"%>
<link href="/fise-projects-portlet/css/tablas.css" rel="stylesheet" type="text/css">


<portlet:renderURL var="urlNew">
	<portlet:param name="action" value="newFormato" />
</portlet:renderURL>
<portlet:actionURL var="accionURL" name="actionNormal">
	<portlet:param name="action" value="uploadFile" />
</portlet:actionURL>
<portlet:renderURL var="urlBack" />


<script type="text/javascript" src="/fise-projects-portlet/js/fise.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		
		formato12B.loadInitDetalle('${urlBack}');
		
		var error = '${error}';
		if (error.length > 0) {
			formato12B.lblMessage.html(error);
			formato12B.dialogMessageGeneral.dialog("open");
			
		}
	});
</script>
<form:form method="POST" modelAttribute="formato12BGartCommand"
	id="frmAddUpdate" action="${accionURL}" enctype="multipart/form-data">
	
	<input type="hidden" id="msgTransaccion" name="msgTransaccion" value="${msgTrans}" />
	
	<form:input path="tipoOperacion" type="hidden" id="txtOperacion" />
	<div id="d_listado" class="net-frame-listado">
		<div id="d_filtro">
			<div id="div_contenido">
				<div id="div_formato" class="net-frame-border">
					<table border="0" width="100%">
						<tr>
							<td height="20px;"></td>
						</tr>
						<tr>
							<td>
								<fieldset class="">
									<table class="" border="0" width="100%">
										<tr class="filete-bottom">
											<td><output class="net-titulo">FORMATO
													FISE-12B: </output> Remisión de Gastos Operativos - Mensual</td>
										</tr>
										<tr height="10px">
											<td></td>
										</tr>
										<tr>
											<td>
												<div id="divgrupoestado" style="display: none;">
													<fieldset class="net-frame-border">
														<table>
															<tr>
																<td><output class="net-titulo" id="lblGrupo"
																		style="font-size: 12px; font-weight: bold;">Grupo
																		de Información</output></td>
																<td style="text-align: center;">
																	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																	
																	 <output id="outTxtGrupo">${formato12BGartCommand.descGrupo}</output>
																	<form:input path="descGrupo" id="txtDescGrup" type="hidden"/>
																	

																</td>
																<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
																<td><output class="net-titulo" id="lblEstado"
																		style="font-size: 12px; font-weight: bold;">Estado</output>

																</td>

																<td style="text-align: center;">
																	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
																	 <output id="outTxtEstado">${formato12BGartCommand.descEstado}</output>
																	<form:input path="descEstado" id="txtDescEst" type="hidden"/>
																	
                                                                   
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
														<td><form:select path="codEmpresa" cssClass="select" cssStyle="width: 375px;" id="cmbEmpresa" disabled="false">
																<c:forEach items="${formato12BGartCommand.listaEmpresas}" var="emp">
																	<form:option value="${emp.codEmpresa}">${emp.dscEmpresa}</form:option>
																</c:forEach>
															</form:select> 
															<form:input path="descEmpresa" type="hidden"  />  
															<form:input path="codEmpresaHidden" type="hidden" /></td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td height="10px;"></td>
										</tr>
										<tr>
											<td><table class="" style="width: 100%;" border="0">

													<tr>
														<td width="45%">
															<fieldset class="net-frame-border">
																<table>
																	<tr>
																		<td colspan="5"><output class="net-titulo">Periodo
																				a declarar:</output></td>


																	</tr>
																	<tr>
																		<td colspan="5">
																		    <form:input path="peridoDeclaracionHidden" type="hidden"  />
																		    <form:select path="peridoDeclaracion" cssClass="select" cssStyle="width: 300px;" id="cmbPeriodo" disabled="false">
																				<c:forEach items="${formato12BGartCommand.listaPeriodo}" var="item">
																					<form:option value="${item.codigoItem}">${item.descripcionItem}</form:option>
																				</c:forEach>
																			</form:select> 
																			<input id="txtPeriodo" value="${formato12BGartCommand.descMes} - ${formato12BGartCommand.anoPresentacion} / ${formato12BGartCommand.etapa}"
																		       	type="text" style="display: none; border-radius: 7px; width: 200px;" disabled="disabled" /> 
																		       	<form:input path="mesPresentacion" type="hidden" /> 
																		       	<form:input path="anoPresentacion" type="hidden" /> 
																				<form:input path="etapa" type="hidden" /> 
																				
																				<form:input path="idGrupoInfo" type="hidden" /> 
																				<form:input path="etapaFinal" type="hidden" /> 
																				
																				<form:input path="flagPeriodoEjecucion" type="hidden" /> 
																				
																				
																		</td>
																	</tr>
																</table>
															</fieldset>
														</td>
														<td width="45%">

															<fieldset class="net-frame-border">
																<table>
																	<tr>
																		<td colspan="5"><output class="net-titulo">Periodo
																				de ejecución</output></td>
																	</tr>
																	<tr>
																		<td width="110px">Año de ejecución:</td>
																		<td><form:input path="anoEjecucionGasto" onBlur="formato12B.cargaPeriodoYGrupo();"
																				style="width: 50px" maxlength="4" /> <input
																			id="txtanoEjecucionGasto" type="hidden"
																			value="${formato12BGartCommand.anoEjecucionGasto}"
																			style="width: 50px" maxlength="4" disabled="disabled" />
																		</td>
																		<td width="10px"></td>
																		<td width="110px">Mes de ejecución:</td>
																		<td><!--<form:input path="mesEjecucionGasto" style="width: 50px" maxlength="4" /> -->
																			<form:select path="mesEjecucionGasto" id="cmbMesEjecucion" cssClass="select" cssStyle="width: 104px;" disabled="false" onChange="formato12B.cargaPeriodoYGrupo();" >
													                          <form:options items="${formato12BGartCommand.listaMes}" />
												                            </form:select>
																			<input id="txtmesEjecucionGasto" type="hidden" value="${formato12BGartCommand.mesEjecucionGasto}" />
																		</td>
																	</tr>
																</table>
															</fieldset>


														</td>


													</tr>
												</table></td>
										</tr>
									</table>
								</fieldset>
							</td>
						</tr>
						<tr height="10px" class="filete-bottom">
							<td></td>
						</tr>
						<tr>
							<td height="10px"></td>
						</tr>
						<tr>
							<td>
								<table class="" style="width: 100%;" border="0">

									<tr>
										<td width="450px" style="padding-top: 10px:"><output
												class="net-titulo">Actividades</output></td>
										<td colspan="3" style="padding-top: 10px:">
											<table style="width: 100%; border-color: #E2E2E4;" border="1">
												<tr>
													<td colspan="3" align="center"><output
															class="net-titulo">Grupo de Beneficiarios según
															Zona</output></td>
												</tr>
												<tr>
													<td width="100px" align="center"><output
															class="net-titulo">Rural</output></td>
													<td width="100px" align="center"><output
															class="net-titulo">Urbano Provincias</output></td>
													<td width="100px" align="center"><output
															class="net-titulo">Urbano Lima</output></td>
												</tr>

											</table>
										</td>
									</tr>
									<tr>
										<td colspan="4" height="10px"></td>
									</tr>
									<tr height="10px" class="filete-top" style="padding-top: 10px:">
										<td colspan="4"></td>
									</tr>
									<tr style="padding-top: 10px:">
										<td colspan="4">1. Impresión de Vales</td>
									</tr>
									<tr>
										<td colspan="4"></td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.1 Número de Vales
											Impresos</td>
										<td align="center"><form:input path="numeroValesImpreso" onkeypress="formato12B.validateInputTextNumber('numeroValesImpreso')"
												disabled="false" cssClass="target"
												onblur="formato12B.loadCostoTotatByInput(formato12B.txtTotalImpresionVale,formato12B.txtnroValesImpreso,formato12B.txtEtndrUnitValeImpre,'costoTotalImpresionVale','costoTotalImpresionVale','porImpresionVales');"
												cssStyle="width: 100px; text-align: right;" /></td>
										<td align="center"><form:input onkeypress="formato12B.validateInputTextNumber('numeroValesImpresoProv')"
												path="numeroValesImpresoProv" cssClass="target "
												disabled="false"
												onblur="formato12B.loadCostoTotatByInput(formato12B.txtTotalImpresionValeProv,formato12B.txtnroValesImpresoProv,formato12B.txtEtndrUnitValeImpreProv,'costoTotalImpresionValeProv','costoTotalImpresionVale','porImpresionVales');"
												cssStyle="width: 100px; text-align: right;" /></td>
										<td align="center"><form:input onkeypress="formato12B.validateInputTextNumber('numeroValesImpresoLim')"
												path="numeroValesImpresoLim" cssClass="target"
												disabled="false"
												onblur="formato12B.loadCostoTotatByInput(formato12B.txtTotalImpresionValeLim,formato12B.txtnroValesImpresoLim,formato12B.txtEtndrUnitValeImpreLim,'costoTotalImpresionValeLim','costoTotalImpresionVale','porImpresionVales');"
												cssStyle="width: 100px; text-align: right;" /></td>
									</tr>
									<tr>
										<td colspan="4" height="3px"></td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.2 Costo Estándar
											Unitario</td>
										<td align="center"><form:input type="hidden"
												path="costoEstandarUnitValeImpre" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoEstandarUnitValeImpre" class=""
											value="${formato12BGartCommand.costoEstandarUnitValeImpre}"
											type="text" disabled="disabled"/></td> 
										<td align="center"><form:input type="hidden"
												path="costoEstandarUnitValeImpreProv" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoEstandarUnitValeImpreProv"
											value="${formato12BGartCommand.costoEstandarUnitValeImpreProv}"
											type="text" disabled="disabled" /></td>
										<td align="center"><form:input type="hidden"
												path="costoEstandarUnitValeImpreLim" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoEstandarUnitValeImpreLim"
											value="${formato12BGartCommand.costoEstandarUnitValeImpreLim}"
											type="text" disabled="disabled" /></td>
									</tr>
									<tr>
										<td colspan="4" height="3px"></td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.3 Costo Total
											Impresión de Vales</td>
										<td align="center"><form:input type="hidden"
												path="costoTotalImpresionVale" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoTotalImpresionVale"
											value="${formato12BGartCommand.costoTotalImpresionVale}"
											type="text" disabled="disabled" /></td>
										<td align="center"><form:input type="hidden"
												path="costoTotalImpresionValeProv" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoTotalImpresionValeProv"
											value="${formato12BGartCommand.costoTotalImpresionValeProv}"
											type="text" disabled="disabled" /></td>
										<td align="center"><form:input type="hidden"
												path="costoTotalImpresionValeLim" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoTotalImpresionValeLim"
											value="${formato12BGartCommand.costoTotalImpresionValeLim}"
											type="text" disabled="disabled" /></td>
									</tr>
									<tr>
										<td colspan="4" height="10px"></td>
									</tr>

									<tr class="filete-top">
										<td colspan="4"></td>
									</tr>
									<tr>
										<td colspan="4" height="10px"></td>
									</tr>

									<tr style="padding-top: 10px; padding-bottom: 10px;">
										<td colspan="4">2. Reparto de Vales a domicilio</td>
									</tr>
									<tr>
										<td colspan="4"></td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.1 Número de Vales
											repartidos a domicilio</td>
										<td align="center"><form:input
												path="numeroValesRepartidosDomi" cssClass="target"
												disabled="false" onkeypress="formato12B.validateInputTextNumber('numeroValesRepartidosDomi')"
												onblur="formato12B.loadCostoTotatByInput(formato12B.txtTotalRepartoValesDomi,formato12B.txtnroValesRepartidosDomi,formato12B.txtEtndrUnitValeRepar,'costoTotalRepartoValesDomi','costoTotalRepartoValesDomi','porRepartoDom');"
												cssStyle="width: 100px; text-align: right;" /></td>
										<td align="center"><form:input
												path="numeroValesRepartidosDomiProv" cssClass="target"
												disabled="false" onkeypress="formato12B.validateInputTextNumber('numeroValesRepartidosDomiProv')"
												onblur="formato12B.loadCostoTotatByInput(formato12B.txtTotalRepartoValesDomiProv,formato12B.txtnroValesRepartidosDomiProv,formato12B.txtEtndrUnitValeReparProv,'costoTotalRepartoValesDomiProv','costoTotalRepartoValesDomi','porRepartoDom');"
												cssStyle="width: 100px; text-align: right;" /></td>
										<td align="center"><form:input
												path="numeroValesRepartidosDomiLim" cssClass="target"
												disabled="false" onkeypress="formato12B.validateInputTextNumber('numeroValesRepartidosDomiLim')"
												onblur="formato12B.loadCostoTotatByInput(formato12B.txtTotalRepartoValesDomiLim,formato12B.txtnroValesRepartidosDomiLim,formato12B.txtEtndrUnitValeReparLim,'costoTotalRepartoValesDomiLim','costoTotalRepartoValesDomi','porRepartoDom');"
												cssStyle="width: 100px; text-align: right;" /></td>
									</tr>
									<tr>
										<td colspan="4" height="3px"></td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.2 Costo Estándar
											Unitario</td>
										<td align="center"><form:input type="hidden"
												path="costoEstandarUnitValeRepar" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoEstandarUnitValeRepar"
											value="${formato12BGartCommand.costoEstandarUnitValeRepar}"
											type="text" disabled="disabled" /></td>
										<td align="center"><form:input type="hidden"
												path="costoEstandarUnitValeReparProv" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoEstandarUnitValeReparProv"
											value="${formato12BGartCommand.costoEstandarUnitValeReparProv}"
											type="text" disabled="disabled" /></td>
										<td align="center"><form:input type="hidden"
												path="costoEstandarUnitValeReparLim" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoEstandarUnitValeReparLim"
											value="${formato12BGartCommand.costoEstandarUnitValeReparLim}"
											type="text" disabled="disabled" /></td>
									</tr>
									<tr>
										<td colspan="4" height="3px"></td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.3 Costo Total Reparto
											de Vales a domicilio</td>
										<td align="center"><form:input type="hidden"
												path="costoTotalRepartoValesDomi" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoTotalRepartoValesDomi"
											value="${formato12BGartCommand.costoTotalRepartoValesDomi}"
											type="text" disabled="disabled" /></td>
										<td align="center"><form:input type="hidden"
												path="costoTotalRepartoValesDomiProv" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoTotalRepartoValesDomiProv"
											value="${formato12BGartCommand.costoTotalRepartoValesDomiProv}"
											type="text" disabled="disabled" /></td>
										<td align="center"><form:input type="hidden"
												path="costoTotalRepartoValesDomiLim" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoTotalRepartoValesDomiLim"
											value="${formato12BGartCommand.costoTotalRepartoValesDomiLim}"
											type="text" disabled="disabled" /></td>
									</tr>

									<tr>
										<td colspan="4" height="10px"></td>
									</tr>
									<tr class="filete-top" style="padding-top: 10px:">
										<td colspan="4"></td>
									</tr>
									<tr>
										<td colspan="4" height="10px"></td>
									</tr>

									<tr style="padding-top: 10px:">
										<td colspan="4">3. Entrega de Vales en la Distribuidora
											Eléctrica</td>
									</tr>
									<tr>
										<td colspan="4"></td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.1 Número de Vales
											entregados en la Distribuidora Eléctrica <br />
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;o
											Centros Autorizados
										</td>
										<td align="center"><form:input
												path="numeroValesEntregadoDisEl" cssClass="target"
												disabled="false" onkeypress="formato12B.validateInputTextNumber('numeroValesEntregadoDisEl')"
												onblur="formato12B.loadCostoTotatByInput(formato12B.txtTotalEntregaValDisEl,formato12B.txtnroValesEntregadoDisEl,formato12B.txtEtndrUnitValDisEl,'costoTotalEntregaValDisEl','costoTotalEntregaValDisEl','porEntregaValesDE');"
												cssStyle="width: 100px; text-align: right;" /></td>
										<td align="center"><form:input
												path="numeroValesEntregadoDisElProv" cssClass="target"
												disabled="false" onkeypress="formato12B.validateInputTextNumber('numeroValesEntregadoDisElProv')"
												onblur="formato12B.loadCostoTotatByInput(formato12B.txtTotalEntregaValDisElProv,formato12B.txtnroValesEntregadoDisElProv,formato12B.txtEtndrUnitValDisEl,'costoTotalEntregaValDisElProv','costoTotalEntregaValDisEl','porEntregaValesDE');"
												cssStyle="width: 100px; text-align: right;" /></td>
										<td align="center"><form:input
												path="numeroValesEntregadoDisElLim" cssClass="target"
												disabled="false" onkeypress="formato12B.validateInputTextNumber('numeroValesEntregadoDisElLim')"
												onblur="formato12B.loadCostoTotatByInput(formato12B.txtTotalEntregaValDisElLim,formato12B.txtnroValesEntregadoDisElLim,formato12B.txtEtndrUnitValDisEl,'costoTotalEntregaValDisElLim','costoTotalEntregaValDisEl','porEntregaValesDE');"
												cssStyle="width: 100px; text-align: right;" /></td>
									</tr>
									<tr>
										<td colspan="4" height="3px"></td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.2 Costo Estándar
											Unitario</td>
										<td align="center"><form:input type="hidden"
												path="costoEstandarUnitValDisEl" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoEstandarUnitValDisEl"
											value="${formato12BGartCommand.costoEstandarUnitValDisEl}"
											type="text" disabled="disabled" /></td>
										<td align="center"><form:input type="hidden"
												path="costoEstandarUnitValDisElProv" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoEstandarUnitValDisElProv"
											value="${formato12BGartCommand.costoEstandarUnitValDisElProv}"
											type="text" disabled="disabled" /></td>
										<td align="center"><form:input type="hidden"
												path="costoEstandarUnitValDisElLim" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoEstandarUnitValDisElLim"
											value="${formato12BGartCommand.costoEstandarUnitValDisElLim}"
											type="text" disabled="disabled" /></td>
									</tr>
									<tr>
										<td colspan="4" height="3px"></td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.3 Costo Total Entrega
											de Vales en la Distribuidora Eléctrica</td>
										<td align="center"><form:input type="hidden"
												path="costoTotalEntregaValDisEl" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoTotalEntregaValDisEl"
											value="${formato12BGartCommand.costoTotalEntregaValDisEl}"
											type="text" disabled="disabled" /></td>
										<td align="center"><form:input type="hidden"
												path="costoTotalEntregaValDisElProv" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoTotalEntregaValDisElProv"
											value="${formato12BGartCommand.costoTotalEntregaValDisElProv}"
											type="text" disabled="disabled" /></td>
										<td align="center"><form:input type="hidden"
												path="costoTotalEntregaValDisElLim" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoTotalEntregaValDisElLim"
											value="${formato12BGartCommand.costoTotalEntregaValDisElLim}"
											type="text" disabled="disabled" /></td>
									</tr>

									<tr>
										<td colspan="4" height="10px"></td>
									</tr>
									<tr class="filete-top" style="padding-top: 10px:">
										<td colspan="4"></td>
									</tr>
									<tr>
										<td colspan="4" height="10px"></td>
									</tr>

									<tr style="padding-top: 10px:">
										<td colspan="4">4. Canje y Liquidación de Vales Físicos</td>
									</tr>
									<tr>
										<td colspan="4"></td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.1 Número de Vales
											Físicos Canjeados</td>
										<td align="center"><form:input
												path="numeroValesFisicosCanjeados" cssClass="target"
												disabled="false" onkeypress="formato12B.validateInputTextNumber('numeroValesFisicosCanjeados')"
												onblur="formato12B.loadCostoTotatByInput(formato12B.txtTotalCanjeLiqValeFis,formato12B.txtnroValesFisicosCanjeados,formato12B.txtEtndrUnitValFiCan,'costoTotalCanjeLiqValeFis','costoTotalCanjeLiqValeFis','porValesFisicos');"
												cssStyle="width: 100px; text-align: right;" /></td>
										<td align="center"><form:input
												path="numeroValesFisicosCanjeadosProv" cssClass="target"
												disabled="false" onkeypress="formato12B.validateInputTextNumber('numeroValesFisicosCanjeadosProv')"
												onblur="formato12B.loadCostoTotatByInput(formato12B.txtTotalCanjeLiqValeFisProv,formato12B.txtnroValesFisicosCanjeadosProv,formato12B.txtEtndrUnitValFiCanProv,'costoTotalCanjeLiqValeFisProv','costoTotalCanjeLiqValeFis','porValesFisicos');"
												cssStyle="width: 100px; text-align: right;" /></td>
										<td align="center"><form:input
												path="numeroValesFisicosCanjeadosLim" cssClass="target"
												disabled="false" onkeypress="formato12B.validateInputTextNumber('numeroValesFisicosCanjeadosLim')"
												onblur="formato12B.loadCostoTotatByInput(formato12B.txtTotalCanjeLiqValeFisLim,formato12B.txtnroValesFisicosCanjeadosLim,formato12B.txtEtndrUnitValFiCanLim,'costoTotalCanjeLiqValeFisLim','costoTotalCanjeLiqValeFis','porValesFisicos');"
												cssStyle="width: 100px; text-align: right;" /></td>
									</tr>
									<tr>
										<td colspan="4" height="3px"></td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.2 Costo Estándar
											Unitario</td>
										<td align="center"><form:input type="hidden"
												path="costoEstandarUnitValFiCan" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoEstandarUnitValFiCan"
											value="${formato12BGartCommand.costoEstandarUnitValFiCan}"
											type="text" disabled="disabled" /></td>
										<td align="center"><form:input type="hidden"
												path="costoEstandarUnitValFiCanProv" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoEstandarUnitValFiCanProv"
											value="${formato12BGartCommand.costoEstandarUnitValFiCanProv}"
											type="text" disabled="disabled" /></td>
										<td align="center"><form:input type="hidden"
												path="costoEstandarUnitValFiCanLim" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoEstandarUnitValFiCanLim"
											value="${formato12BGartCommand.costoEstandarUnitValFiCanLim}"
											type="text" disabled="disabled" /></td>
									</tr>
									<tr>
										<td colspan="4" height="3px"></td>
									</tr>

									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.3 Costo Total Canje y
											Liquidación de Vales Físicos</td>
										<td align="center"><form:input type="hidden"
												path="costoTotalCanjeLiqValeFis" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoTotalCanjeLiqValeFis"
											value="${formato12BGartCommand.costoTotalCanjeLiqValeFis}"
											type="text" disabled="disabled" /></td>
										<td align="center"><form:input type="hidden"
												path="costoTotalCanjeLiqValeFisProv" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoTotalCanjeLiqValeFisProv"
											value="${formato12BGartCommand.costoTotalCanjeLiqValeFisProv}"
											type="text" disabled="disabled" /></td>
										<td align="center"><form:input type="hidden"
												path="costoTotalCanjeLiqValeFisLim" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoTotalCanjeLiqValeFisLim"
											value="${formato12BGartCommand.costoTotalCanjeLiqValeFisLim}"
											type="text" disabled="disabled" /></td>
									</tr>

									<tr>
										<td colspan="4" height="10px"></td>
									</tr>
									<tr class="filete-top" style="padding-top: 10px:">
										<td colspan="4"></td>
									</tr>

									<tr>
										<td colspan="4" height="10px"></td>
									</tr>
									<tr style="padding-top: 10px:">
										<td colspan="4">5. Canje y Liquidación de Vales Digitales</td>
									</tr>
									<tr>
										<td colspan="4"></td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5.1 Número de Vales
											Digitales Canjeados</td>
										<td align="center"><form:input
												path="numeroValesDigitalCanjeados" cssClass="target"
												disabled="false" onkeypress="formato12B.validateInputTextNumber('numeroValesDigitalCanjeados')"
												onblur="formato12B.loadCostoTotatByInput(formato12B.txtTotalCanjeLiqValeDig,formato12B.txtnroValesDigitalCanjeados,formato12B.txtEtndrUnitValDgCan,'costoTotalCanjeLiqValeDig','costoTotalCanjeLiqValeDig','porValesDigitales');"
												cssStyle="width: 100px; text-align: right;" /></td>
										<td align="center"><form:input
												path="numeroValesDigitalCanjeadosProv" cssClass="target"
												disabled="false" onkeypress="formato12B.validateInputTextNumber('numeroValesDigitalCanjeadosProv')"
												onblur="formato12B.loadCostoTotatByInput(formato12B.txtTotalCanjeLiqValeDigProv,formato12B.txtnroValesDigitalCanjeadosProv,formato12B.txtEtndrUnitValDgCanProv,'costoTotalCanjeLiqValeDigProv','costoTotalCanjeLiqValeDig','porValesDigitales');"
												cssStyle="width: 100px; text-align: right;" /></td>
										<td align="center"><form:input
												path="numeroValesDigitalCanjeadosLim" cssClass="target"
												disabled="false" onkeypress="formato12B.validateInputTextNumber('numeroValesDigitalCanjeadosLim')"
												onblur="formato12B.loadCostoTotatByInput(formato12B.txtTotalCanjeLiqValeDigLim,formato12B.txtnroValesDigitalCanjeadosLim,formato12B.txtEtndrUnitValDgCanLim,'costoTotalCanjeLiqValeDigLim','costoTotalCanjeLiqValeDig','porValesDigitales');"
												cssStyle="width: 100px; text-align: right;" /></td>
									</tr>
									<tr>
										<td colspan="4" height="3px"></td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5.2 Costo Estándar
											Unitario</td>
										<td align="center"><form:input type="hidden"
												path="costoEstandarUnitValDgCan" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoEstandarUnitValDgCan"
											value="${formato12BGartCommand.costoEstandarUnitValDgCan}"
											type="text" disabled="disabled" /></td>
										<td align="center"><form:input type="hidden"
												path="costoEstandarUnitValDgCanProv" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoEstandarUnitValDgCanProv"
											value="${formato12BGartCommand.costoEstandarUnitValDgCanProv}"
											type="text" disabled="disabled" /></td>
										<td align="center"><form:input type="hidden"
												path="costoEstandarUnitValDgCanLim" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoEstandarUnitValDgCanLim"
											value="${formato12BGartCommand.costoEstandarUnitValDgCanLim}"
											type="text" disabled="disabled" /></td>
									</tr>
									<tr>
										<td colspan="4" height="3px"></td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5.3 Costo Total Canje y
											Liquidación de Vales Digitales</td>
										<td align="center"><form:input type="hidden"
												path="costoTotalCanjeLiqValeDig" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoTotalCanjeLiqValeDig"
											value="${formato12BGartCommand.costoTotalCanjeLiqValeDig}"
											type="text" disabled="disabled" /></td>
										<td align="center"><form:input type="hidden"
												path="costoTotalCanjeLiqValeDigProv" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoTotalCanjeLiqValeDigProv"
											value="${formato12BGartCommand.costoTotalCanjeLiqValeDigProv}"
											type="text" disabled="disabled" /></td>
										<td align="center"><form:input type="hidden"
												path="costoTotalCanjeLiqValeDigLim" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoTotalCanjeLiqValeDigLim"
											value="${formato12BGartCommand.costoTotalCanjeLiqValeDigLim}"
											type="text" disabled="disabled" /></td>
									</tr>
									<tr>
										<td colspan="4" height="10px"></td>
									</tr>

									<tr class="filete-top" style="padding-top: 10px:">
										<td colspan="4"></td>
									</tr>

									<tr>
										<td colspan="4" height="10px"></td>
									</tr>
									<tr style="padding-top: 10px:">
										<td colspan="4">6. Atención de Solicitudes, Consultas y
											Reclamos</td>
									</tr>
									<tr>
										<td colspan="4"></td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6.1 Número de
											Atenciones</td>
										<td align="center"><form:input path="numeroAtenciones"
												disabled="false" cssClass="target" onkeypress="formato12B.validateInputTextNumber('numeroAtenciones')"
												onblur="formato12B.loadCostoTotatByInput(formato12B.txtTotalAtencionConsRecl,formato12B.txtnroAtenciones,formato12B.txtEtndrUnitAtencion,'costoTotalAtencionConsRecl','costoTotalAtencionConsRecl','porAtencionReclamos');"
												cssStyle="width: 100px; text-align: right;" /></td>
										<td align="center"><form:input
												path="numeroAtencionesProv" cssClass="target"
												disabled="false" onkeypress="formato12B.validateInputTextNumber('numeroAtencionesProv')"
												onblur="formato12B.loadCostoTotatByInput(formato12B.txtTotalAtencionConsReclProv,formato12B.txtnroAtencionesProv,formato12B.txtEtndrUnitAtencionProv,'costoTotalAtencionConsReclProv','costoTotalAtencionConsRecl','porAtencionReclamos');"
												cssStyle="width: 100px; text-align: right;" /></td>
										<td align="center"><form:input path="numeroAtencionesLim"
												disabled="false" cssClass="target" onkeypress="formato12B.validateInputTextNumber('numeroAtencionesLim')"
												onblur="formato12B.loadCostoTotatByInput(formato12B.txtTotalAtencionConsReclLim,formato12B.txtnroAtencionesLim,formato12B.txtEtndrUnitAtencionLim,'costoTotalAtencionConsReclLim','costoTotalAtencionConsRecl','porAtencionReclamos');"
												cssStyle="width: 100px; text-align: right;" /></td>
									</tr>
									<tr>
										<td colspan="4" height="3px"></td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6.2 Costo Estándar
											Unitario</td>
										<td align="center"><form:input type="hidden"
												path="costoEstandarUnitAtencion" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoEstandarUnitAtencion"
											value="${formato12BGartCommand.costoEstandarUnitAtencion}"
											type="text" disabled="disabled" /></td>
										<td align="center"><form:input type="hidden"
												path="costoEstandarUnitAtencionProv" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoEstandarUnitAtencionProv"
											value="${formato12BGartCommand.costoEstandarUnitAtencionProv}"
											type="text" disabled="disabled" /></td>
										<td align="center"><form:input type="hidden"
												path="costoEstandarUnitAtencionLim" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoEstandarUnitAtencionLim"
											value="${formato12BGartCommand.costoEstandarUnitAtencionLim}"
											type="text" disabled="disabled" /></td>
									</tr>
									<tr>
										<td colspan="4" height="3px"></td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6.3 Costo Total de
											Atenciones de Consultas y Reclamos</td>
										<td align="center"><form:input type="hidden"
												path="costoTotalAtencionConsRecl" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoTotalAtencionConsRecl"
											value="${formato12BGartCommand.costoTotalAtencionConsRecl}"
											type="text" disabled="disabled" /></td>
										<td align="center"><form:input type="hidden"
												path="costoTotalAtencionConsReclProv" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoTotalAtencionConsReclProv"
											value="${formato12BGartCommand.costoTotalAtencionConsReclProv}"
											type="text" disabled="disabled" /></td>
										<td align="center"><form:input type="hidden"
												path="costoTotalAtencionConsReclLim" /> <input
											style="width: 100px; text-align: right;"
											id="txtcostoTotalAtencionConsReclLim"
											value="${formato12BGartCommand.costoTotalAtencionConsReclLim}"
											type="text" disabled="disabled" /></td>
									</tr>

									<tr>
										<td colspan="4" height="10px"></td>
									</tr>


									<tr class="filete-top" style="padding-top: 10px:">
										<td colspan="4"></td>
									</tr>

									<tr>
										<td colspan="4" height="10px"></td>
									</tr>

									<tr style="padding-top: 10px:">
										<td>7. Gestión Administrativa</td>
										<td align="center"><form:input  path="totalGestionAdministrativa" onkeypress="formato12B.validateInputTextDecimal('totalGestionAdministrativa')" onblur="formato12B.loadGestion('porGestionAdm','totalGestionAdministrativa')" cssClass="target" disabled="true" cssStyle="width: 100px; text-align: right;" maxlength="12" /></td>
										<td align="center"><form:input  path="totalGestionAdministrativaProv" onkeypress="formato12B.validateInputTextDecimal('totalGestionAdministrativaProv')" onblur="formato12B.loadGestion('porGestionAdm','totalGestionAdministrativa')" cssClass="target" disabled="true" cssStyle="width: 100px; text-align: right;" maxlength="12"/></td>
										<td align="center"><form:input  path="totalGestionAdministrativaLim" onkeypress="formato12B.validateInputTextDecimal('totalGestionAdministrativaLim')" onblur="formato12B.loadGestion('porGestionAdm','totalGestionAdministrativa')" cssClass="target" disabled="true" cssStyle="width: 100px; text-align: right;" maxlength="12"/></td>
									</tr>
									<tr>
										<td colspan="4" height="10px"></td>
									</tr>
									<tr class="filete-top" style="padding-top: 10px:">
										<td colspan="4"></td>
									</tr>
									<tr>
										<td colspan="4" height="10px"></td>
									</tr>
									<tr style="padding-top: 10px:">
										<td>8. Desplazamiento de Personal</td>
										<td align="center"><form:input  onkeypress="formato12B.validateInputTextDecimal('totalDesplazamientoPersonal')"
												path="totalDesplazamientoPersonal" cssClass="target"   onblur="formato12B.loadGestion('porDesplazamientoPers','totalDesplazamientoPersonal')" 
												disabled="false" cssStyle="width: 100px; text-align: right;" maxlength="12"/></td>
										<td align="center"><form:input  onkeypress="formato12B.validateInputTextDecimal('totalDesplazamientoPersonalProv')"
												path="totalDesplazamientoPersonalProv" cssClass="target"  onblur="formato12B.loadGestion('porDesplazamientoPers','totalDesplazamientoPersonal')" 
												disabled="false" cssStyle="width: 100px; text-align: right;" maxlength="12" /></td>
										<td align="center"><form:input  onkeypress="formato12B.validateInputTextDecimal('totalDesplazamientoPersonalLim')"
												path="totalDesplazamientoPersonalLim" cssClass="target"  onblur="formato12B.loadGestion('porDesplazamientoPers','totalDesplazamientoPersonal')" 
												disabled="false" cssStyle="width: 100px; text-align: right;" maxlength="12" /></td>
									</tr>
									<tr>
										<td colspan="4" height="10px"></td>
									</tr>
									<tr class="filete-top" style="padding-top: 10px:">
										<td colspan="4"></td>
									</tr>
									<tr>
										<td colspan="4" height="10px"></td>
									</tr>
									<tr style="padding-top: 10px:">
										<td>9. Actividades Extraordinarias</td>
										<td align="center"><form:input  onkeypress="formato12B.validateInputTextDecimal('totalActividadesExtraord')"
												path="totalActividadesExtraord" cssClass="target"  onblur="formato12B.loadGestion('porActividadExtra','totalActividadesExtraord')" 
												disabled="false" cssStyle="width: 100px; text-align: right;" maxlength="12" /></td>
										<td align="center"><form:input  onkeypress="formato12B.validateInputTextDecimal('totalActividadesExtraordProv')"
												path="totalActividadesExtraordProv" cssClass="target"  onblur="formato12B.loadGestion('porActividadExtra','totalActividadesExtraord')" 
												disabled="false" cssStyle="width: 100px; text-align: right;" maxlength="12" /></td>
										<td align="center"><form:input  onkeypress="formato12B.validateInputTextDecimal('totalActividadesExtraordLim')"
												path="totalActividadesExtraordLim" cssClass="target"  onblur="formato12B.loadGestion('porActividadExtra','totalActividadesExtraord')" 
												disabled="false" cssStyle="width: 100px; text-align: right;" maxlength="12" /></td>
									</tr>
									<tr>
										<td colspan="4" height="10px"></td>
									</tr>
									<tr class="filete-top" style="padding-top: 10px:">
										<td colspan="4"></td>
									</tr>
									<tr>
										<td colspan="4" height="10px"></td>
									</tr>
									<tr style="padding-top: 10px:">
										<td>10. Importe a reconocer a la Distribuidora Eléctrica</td>
										<td align="center">Total</td>
										<td align="center"></td>
										<td align="center"></td>
									
									</tr>
									<tr>
										<td colspan="4" height="5px"></td>
									</tr>
									<tr style="padding-top: 10px:">
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Por Impresión de Vales (1.3)</td>
										<td align="center"><form:input  path="porImpresionVales" cssClass="target" disabled="true" cssStyle="width: 100px; text-align: right;" /></td>
										<td align="center"></td>
										<td align="center"></td>
									
									</tr>
									<tr>
										<td colspan="4" height="5px"></td>
									</tr>
									<tr style="padding-top: 10px:">
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Por Reparto de Vales a domicilio (2.3)</td>
										<td align="center"><form:input  path="porRepartoDom" cssClass="target" disabled="true" cssStyle="width: 100px; text-align: right;" /></td>
										<td align="center"></td>
										<td align="center"></td>
									
									</tr>
									<tr>
										<td colspan="4" height="5px"></td>
									</tr>
									<tr style="padding-top: 10px:">
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Por Entrega de Vales en la Distribuidora Eléctrica (3.3)</td>
										<td align="center"><form:input  path="porEntregaValesDE" cssClass="target" disabled="true" cssStyle="width: 100px; text-align: right;" /></td>
										<td align="center"></td>
										<td align="center"></td>
									
									</tr>
									<tr>
										<td colspan="4" height="5px"></td>
									</tr>
									<tr style="padding-top: 10px:">
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Por Canje y Liquidación de Vales Físicos (4.3)</td>
										<td align="center"><form:input  path="porValesFisicos" cssClass="target" disabled="true" cssStyle="width: 100px; text-align: right;" /></td>
										<td align="center"></td>
										<td align="center"></td>
									
									</tr>
									<tr>
										<td colspan="4" height="5px"></td>
									</tr>
									<tr style="padding-top: 10px:">
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Por Canje y Liquidación de Vales Digitales (5.3)</td>
										<td align="center"><form:input  path="porValesDigitales" cssClass="target" disabled="true" cssStyle="width: 100px; text-align: right;" /></td>
										<td align="center"></td>
										<td align="center"></td>
									
									</tr>
									<tr>
										<td colspan="4" height="5px"></td>
									</tr>
									<tr style="padding-top: 10px:">
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Por Atención de Soluciones,Consultas y Reclamos (6.3)</td>
										<td align="center"><form:input  path="porAtencionReclamos" cssClass="target" disabled="true" cssStyle="width: 100px; text-align: right;" /></td>
										<td align="center"></td>
										<td align="center"></td>
									
									</tr>
									<tr>
										<td colspan="4" height="5px"></td>
									</tr>
									<tr style="padding-top: 10px:">
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Por Gestión Administrativa (7)</td>
										<td align="center"><form:input  path="porGestionAdm" cssClass="target" disabled="true" cssStyle="width: 100px; text-align: right;" /></td>
										<td align="center"></td>
										<td align="center"></td>
									
									</tr>
									<tr>
										<td colspan="4" height="5px"></td>
									</tr>
									<tr style="padding-top: 10px:">
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Por Desplazamiento de Personal  (8)</td>
										<td align="center"><form:input  path="porDesplazamientoPers" cssClass="target" disabled="true" cssStyle="width: 100px; text-align: right;" /></td>
										<td align="center"></td>
										<td align="center"></td>
									
									</tr>
									<tr>
										<td colspan="4" height="5px"></td>
									</tr>
									<tr style="padding-top: 10px:">
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Por Actividades Extraordinarias  (9)</td>
										<td align="center"><form:input  path="porActividadExtra" cssClass="target" disabled="true" cssStyle="width: 100px; text-align: right;" /></td>
										<td align="center"></td>
										<td align="center"></td>
									
									</tr>
									<tr>
										<td colspan="4" height="5px"></td>
									</tr>
									<tr style="padding-top: 10px:">
										<td>11. Total General a Reconocer </td>
										<td align="center"><form:input  path="totalGeneralReconocer" cssClass="target" disabled="true" cssStyle="width: 100px; text-align: right;" /></td>
										<td align="center"></td>
										<td align="center"></td>
									
									</tr>
									<tr>
										<td colspan="4" height="10px"></td>
									</tr>
									<tr class="filete-top" style="padding-top: 10px:">
										<td colspan="4"></td>
									</tr>
									<tr>
										<td colspan="4" height="10px"></td>
									</tr>

									<tr style="padding-top: 10px:">
										<td colspan="4">
											<table style="width: 100%">
												<tr>
													<td width="25%">
														<div id="divLoadFile" style="display: block;">
															<fieldset id="<portlet:namespace/>panelCargaArchivos"
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
																			name="<portlet:namespace/>cargaTxt" value="TXT" /></td>
																	</tr>
																</table>
															</fieldset>

														</div>

													</td>
													<td width="10%"></td>
													<td width="65%">
														<table style="width: 100%">
															<tr>
																<td width="13%" align="center"><input type="button"
																	class="boton" name="btnPdf" id="btnPdf"
																	style="display: block;" class="button net-button-small"
																	value="Imprimir PDF" /></td>
																<td width="13%" align="center"><input type="button"
																	class="boton" name="btnExpExcel" id="btnExpExcel"
																	style="display: block;" class="button net-button-small"
																	value="Exportar excel" /></td>
																<td width="13%" align="center"><input type="button"
																	class="boton" name="btnActaEnvio" id="btnActaEnvio"
																	style="display: block;" class="button net-button-small"
																	value="Acta de envío" /></td>
																<td width="16%" align="center"><input type="button"
																	style="display: block;" class="net-button-small"
																	id="<portlet:namespace/>guardarFormato"
																	name="<portlet:namespace/>guardarFormato"
																	value="Grabar" /></td>
																<td width="15%" align="center"><input type="button"
																	style="display: block;" class="net-button-small"
																	name="btnValidacion" id="btnValidacion"
																	value="Validación" /></td>
																<td width="15%" align="center"><input type="button"
																	style="display: block;" class="net-button-small"
																	name="btnEnvioDefinitivo" id="btnEnvioDefinitivo"
																	value="Envío Def." /></td>
																<td width="15%" align="center"><input type="button"
																	style="display: block;" class="net-button-small"
																	id="<portlet:namespace/>regresarFormato"
																	name="<portlet:namespace/>regresarFormato"
																	value="Regresar" /></td>
															</tr>
														</table>

													</td>
												</tr>
											</table>

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
				</div>
			</div>
		</div>
	</div>


	<div id="dlgLoadFile"
		class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable"
		style="display: none; z-index: 1002; position: absolute; width: 400px;">
		<div
			class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix">
			<span class="ui-dialog-title" id="ui-dialog-title-dialog-form-carga">
				Cargar archivo  </span> <a href="#"
				class="ui-dialog-titlebar-close ui-corner-all" role="button"
				onclick="formato12B.closeLoadFile();"> <span
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
					<td><form:input type="hidden" id="txtTypeFile"  path="typeFile"/></td>
						<td>Archivo:</td>
						<td><input type="file" id="archivoExcel" name="archivoExcel" /></td>
					</tr>
					<tr>
						<td height="10px;"></td>
					</tr>
					<tr>
						<td colspan="3"><span id="msjUploadFile" style="color: red;"></span></td>
					</tr>
				</table>
			</fieldset>


		</div>
        <div class="ui-resizable-handle ui-resizable-n" style="z-index: 1000;"></div>
        <div class="ui-resizable-handle ui-resizable-e" style="z-index: 1000;"></div>
        <div class="ui-resizable-handle ui-resizable-s" style="z-index: 1000;"></div>
        <div class="ui-resizable-handle ui-resizable-w" style="z-index: 1000;"></div>
        <div class="ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se ui-icon-grip-diagonal-se" style="z-index: 1000;"></div>
		<div class="ui-resizable-handle ui-resizable-sw" style="z-index: 1000;"></div>
		<div class="ui-resizable-handle ui-resizable-ne" style="z-index: 1000;"></div>
		<div class="ui-resizable-handle ui-resizable-nw" style="z-index: 1000;"></div>
		
		
		<div class="ui-dialog-buttonpane ui-widget-content ui-helper-clearfix">
			<div class="ui-dialog-buttonset">
				<input type="button" class="net-button-small"
					name="<portlet:namespace/>uploadExcel" id="btnUploadExcel"
					value="Cargar" /> <input type="button" class="net-button-small"
					name="<portlet:namespace/>cerrarFormatoExcel"
					id="<portlet:namespace/>cerrarFormatoExcel" value="Cerrar"
					onclick="formato12B.closeLoadFile();" />
			</div>
		</div>
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
	
	
	<div id="dialogConfirmEnvio" title="Mensaje de Confirmaci&oacute;n">
		<p> 
		<!-- <span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>  -->
		<img src="/fise-projects-portlet/images/confirm.png" style="float:left; margin:20px 25px 20px 5px;">
		<br/>
		<label class="labelCentrado" id="lblConfirmEnvioContent">¿Está seguro?</label>
		</p>
	</div>
	<div id="dialogMessageReport" title="Mensaje de &Eacute;xito">
		<p>
			<!-- <span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;">
			</span> -->
			<img src="/fise-projects-portlet/images/success.png" style="float:left; margin:20px 25px 20px 5px;">
			<br/>
			<label class="labelCentrado" id="lblMessageReportContent">Datos grabados exit&oacute;samente.</label>
		</p>	
	</div>
	
	<div id="dialogMessageGeneral" title="Mensaje de &Eacute;xito">
		<p>
			<!-- <span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;">
			</span> -->
			<img src="/fise-projects-portlet/images/success.png" style="float:left; margin:20px 25px 20px 5px;">
			<br/>
			<label class="labelCentrado" id="lblMessage">Datos grabados exit&oacute;samente.</label>
		</p>	
	</div>
	
	<!--  -->
	<div id="<portlet:namespace/>dialog-message-info-detalle" title="Mensaje de &Eacute;xito">
		<p>
			<img src="/fise-projects-portlet/images/info.png" style="float:left; margin:20px 25px 20px 5px;">
			<br/>
			<label class="labelCentrado" id="<portlet:namespace/>dialog-message-info-content-detalle">Datos grabados exit&oacute;samente.</label>
		</p>	
	</div>
	
	<div id="<portlet:namespace/>dialog-message-warning-detalle" title="Mensaje de Advertencia">
		<p>
			<img src="/fise-projects-portlet/images/warning.png" style="float:left; margin:20px 25px 20px 5px;">
			<br/>
			<label class="labelCentrado" id="<portlet:namespace/>dialog-message-warning-content-detalle">Datos grabados exit&oacute;samente.</label>
		</p>	
	</div>
	
	<div id="<portlet:namespace/>dialog-message-error-detalle" title="Mensaje de Error">
		<p>
			<img src="/fise-projects-portlet/images/error.png" style="float:left; margin:20px 25px 20px 5px;">
			<br/>
			<label class="labelCentrado" id="<portlet:namespace/>dialog-message-error-content-detalle">Datos grabados exit&oacute;samente.</label>
		</p>	
	</div>
	<!--  -->
	
	<div id="<portlet:namespace/>divOverlay" class="ui-widget-overlay" style="display: none; width: 100%; height: 100%; z-index: 1001;"></div>
	
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

</form:form>