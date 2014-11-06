<%@include file="/WEB-INF/jsps/gart/ext/extFormato14C.jsp"%>

<portlet:actionURL var="accionURL" name="actionNormal">
	<portlet:param name="action" value="cargar" />
</portlet:actionURL>

<link href="/fise-projects-portlet/css/tablas.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/fise-projects-portlet/js/fise.js"></script>

<script type="text/javascript">
$(document).ready(function () {	
	formato14C.init();
});
</script>

<form:form method="POST" modelAttribute="formato14CBean">

   <!-- VALORES HIDDEN -->
   <input type="hidden" id="estadoCrudF14C" value="" />



	<div id="d_listado" class="net-frame-listado">
		<div id="d_filtro">
			<div id="div_contenido">
	
	
	            <!-- DIV PARA BUSQUEDA  -->	
	            
				<div id="<portlet:namespace/>div_buscar" class="net-frame-listado">
	
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
												<form:select path="codEmpresaBusq" cssClass="select" cssStyle="width: 375px;">

													<c:if test="${formato14CBean.admin}">
														<form:option value="">-Todos-</form:option>
													</c:if>
													<form:options items="${formato14CBean.listaEmpresas}"  itemLabel="dscEmpresa" itemValue="codEmpresa"/>
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
													<form:options items="${formato14CBean.listaMes}"/>
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
													<form:options items="${formato14CBean.listaMes}"/>
												</form:select>
											</td>
										</tr>
										<tr height="10px">
											<td colspan="8"></td>
										</tr>
										<tr>
											<td><output>Etapa:</output></td>
											<td colspan="7">
												<form:select path="etapaBusq" cssClass="select" cssStyle="width: 140px;">
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
											<td><input name="<portlet:namespace/>btnBuscarF14C"
												id="<portlet:namespace/>btnBuscarF14C" type="button"
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
								<table id="<portlet:namespace/>grid_resultado_busqueda">
								</table>
								<div id="<portlet:namespace/>paginador_resultado_busqueda"></div>
							</td>
						</tr>
						<tr height="10px">
							<td></td>
						</tr>
						<tr>
							<td>
								<table style="width: 100%;" border="0">
									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td align="right" width="90px">
											<div id="d_opc_crear">
												<input type="button" class="net-button-small"
													id="<portlet:namespace/>btnNuevoF14C"
													name="<portlet:namespace/>btnNuevoF14C" value="Nuevo" />
											</div>
										</td>
										<td align="right" width="90px">											
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
				<!-- FIN DE DIV  PARA BUSQUEDA -->
				
			
			 <!-- DIV PARA NUEVO REGISTRO -->	
			 
				<div id="<portlet:namespace/>div_nuevo" class="net-frame-border" style="display: none;">			
					
					<table border="0" width="100%">
						<tr>
							<td>
								<fieldset class="">
	
									<table class="" border="0" width="100%">
										<tr class="filete-bottom">
											<td><output class="net-titulo">FORMATO FISE-14C:
												</output>COSTOS ESTÁNDARES DE IMPLEMENTACIÓN
											</td>
										</tr>
										<tr height="10px">
											<td></td>
										</tr>
										<tr>
											<td>
											 <table class="" style="width: 50%;" border="0">
												  <tr>
												   <td> <label style="font-size: 12px; font-weight:bold">Distribuidora Eléctrica:</label></td>
												   <td colspan="2">
												      <form:select path="codEmpresa" cssClass="select" cssStyle="width: 375px;" >
																<form:options items="${formato14CBean.listaEmpresas}"  itemLabel="dscEmpresa" itemValue="codEmpresa"/>
													  </form:select>
												  </td>	  
												  </tr>	
												  <tr>
												   <td> <label style="font-size: 12px; font-weight:bold">Año/Mes</label></td>
												   <td colspan="2">
												     <form:select path="periodoEnvio" cssClass="select" cssStyle="width: 300px;">
														<c:forEach items="${listaPeriodoEnvio}" var="periodo">
														     <option value="${periodo.codigoItem}">${periodo.descripcionItem}</option>
														</c:forEach>
													 </form:select>
												   
												   </td>
												       										       												  
												  </tr>	
												   <tr>
												   <td> <label style="font-size: 12px; font-weight:bold">Rubro</label></td>
												   <td colspan="2"><label>Costos Estándar</label></td> 	  
												  </tr>	
												   <tr>
												   <td> <label style="font-size: 12px; font-weight:bold">Frecuencia</label></td>
												   <td colspan="2"><label>Bianual</label></td>	   
												  </tr>	
											  </table>											
											</td>
										</tr>
										<tr height="10px" class="filete-bottom">
										  <td colspan="2"></td> 
										</tr>
										<tr>
										  <td colspan="2"></td> 
										</tr>	
										<tr>
										  <td colspan="2"></td> 
										</tr>									
										<tr>
										<td>
											<table class="" style="width: 100%;" border="0">
											    <tr>
													<td colspan="2" style="text-align:center"> 
													 <label style="font-size: 12px; font-weight:bold">Actividades</label>
													</td>
													<td colspan="5" style="text-align:center"> 
													 <label style="font-size: 12px; font-weight:bold">Zona Rural</label>
													</td>
													<td colspan="5" style="text-align:center"> 
													 <label style="font-size: 12px; font-weight:bold">Zona Urbano Provincias</label>
													</td>	
													<td colspan="5" style="text-align:center"> 
													 <label style="font-size: 12px; font-weight:bold">Zona Urbano Lima</label>
													</td>
													<!-- COSTO TOTAL -->
													<td colspan="2" style="text-align:center"> 
													 <label style="font-size: 12px; font-weight:bold">TOTAL</label>
													</td>	
																	
												</tr>
												<tr>
													<td colspan="2" style="text-align:center"> 			
													</td>
													<!-- RURAL -->
													<td colspan="2" style="text-align:center"> 
													 <label style="font-size: 12px; font-weight:bold">Costo Directo</label>
													</td>
													<td colspan="2" style="text-align:center"> 
													 <label style="font-size: 12px; font-weight:bold">Costo Indirecto</label>
													</td>	
													<td style="text-align:center"> 			 
													</td>	
													<!-- URBANO PROVINCIAS -->
													<td colspan="2" style="text-align:center"> 
													 <label style="font-size: 12px; font-weight:bold">Costo Directo</label>
													</td>
													<td colspan="2" style="text-align:center"> 
													 <label style="font-size: 12px; font-weight:bold">Costo Indirecto</label>
													</td>	
													<td style="text-align:center"> 			 
													</td>
													<!-- URBANO LIMA -->
													<td colspan="2" style="text-align:center"> 
													 <label style="font-size: 12px; font-weight:bold">Costo Directo</label>
													</td>
													<td colspan="2" style="text-align:center"> 
													 <label style="font-size: 12px; font-weight:bold">Costo Indirecto</label>
													</td>	
													<td style="text-align:center"> 			 
													</td>
													<!-- COSTO TOTAL -->		
													<td  colspan="2" style="text-align:center"> 			
													</td>				
												</tr>
												<tr>
													<td colspan="2"> 			
													</td>
													<!-- RURAL -->
												    <td style="text-align:center"> 
													 <label>Cantidad</label>
													</td>
													<td style="text-align:center"> 
													 <label>Costo S/.</label>
													</td>
													<td style="text-align:center"> 
													 <label>Cantidad</label>
													</td>	
													<td style="text-align:center"> 
													 <label>Costo S/.</label>
													</td>
													<td style="text-align:center"> 
													 <label>Costo Total</label>
													</td>
													<!-- URBANA PROVINCIAS -->
													<td style="text-align:center"> 
													 <label>Cantidad</label>
													</td>
													<td style="text-align:center"> 
													 <label>Costo S/.</label>
													</td>	
													<td style="text-align:center"> 
													 <label>Cantidad</label>
													</td>
													<td style="text-align:center"> 
													 <label>Costo S/.</label>
													</td>
													<td style="text-align:center"> 
													 <label>Costo Total</label>
													</td>
													<!-- URBANA LIMA -->
													<td style="text-align:center"> 
													 <label>Cantidad</label>
													</td>
													<td style="text-align:center"> 
													 <label>Costo S/.</label>
													</td>	
													<td style="text-align:center"> 
													 <label>Cantidad</label>
													</td>
													<td style="text-align:center"> 
													 <label>Costo S/.</label>
													</td>
													<td style="text-align:center"> 
													 <label>Costo Total</label>
													</td>
													<!-- TOTAL -->
													<td style="text-align:center"> 
													 <label>Cantidad</label>
													</td>
													<td style="text-align:center"> 
													 <label>Costo S/.</label>
													</td>
																									
												</tr>
												<tr>
													<td colspan="2"> 
													 <label style="font-size: 12px; font-weight:bold">1  Datos Generales</label>
													</td>
													<!-- RURAL -->
													<td style="text-align:center;display:none" colspan="5"></td> 
													<!-- URBANA PROVINCIAS -->
													<td style="text-align:center;display:none" colspan="5"></td>
													<!-- URBANA LIMA -->
													<td style="text-align:center;display:none" colspan="5"></td>				 		
												</tr>
												<tr>
													<td> 
													 <label style="margin-left:0.5cm;">1.1 Nombre de la Sede: </label>
													</td>	
													<td style="text-align:left">
													    <form:input path="nombreSede"/>
													</td>
													<!-- RURAL --> 
													<td style="text-align:center;display:none" colspan="5"></td>
													<!-- URBANA PROVINCIAS --> 	
													<td style="text-align:center;display:none" colspan="5"></td>
													<!-- URBANA LIMA --> 	
													<td style="text-align:center;display:none" colspan="5"></td>	
													<!-- TOTAL -->
													<td style="text-align:center;display:none" colspan="2"></td>					
												</tr>
												<tr>
													<td colspan="2">
													 <label style="margin-left: 0.5cm;">1.2 Número Total Beneficiarios Empadronados a Diciembre del periodo anterior</label>
													</td>
													<!-- RURAL -->
													<td style="text-align:center">
													   <form:input id="numRural" path="numRural" size="5" cssClass="target" cssStyle="text-align: right;"/>
													</td>
													<td style="text-align:center" colspan="4"></td> 
													<!-- URBANA PROVINCIAS -->
													<td style="text-align:center">
													   <form:input id="numUrbProv" path="numUrbProv" size="5" cssClass="target" cssStyle="text-align: right;"/>
													</td>
													<td style="text-align:center" colspan="4"></td> 
													<!-- URBANA LIMA -->
													<td style="text-align:center"><form:input id="numUrbLima" path="numUrbLima" size="5" cssClass="target" cssStyle="text-align: right;"/></td>
													<td style="text-align:center" colspan="4"></td> 	
													<!-- TOTAL -->
													<td style="text-align:center"><form:input id="numTotal" path="numTotal" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td>
													<td style="text-align:center;display:none"></td>	 				
												</tr>
												<tr>
													<td colspan="2">
													 <label style="font-size: 12px; font-weight:bold">2 Costo por Gestion del Personal</label>
													</td>
													<!-- RURAL -->
													<td style="text-align:center;display:none" colspan="5"></td> 
													<!-- URBANA PROVINCIAS -->
													<td style="text-align:center;display:none" colspan="5"></td> 
													<!-- URBANA LIMA -->
													<td style="text-align:center;display:none" colspan="5"></td> 
													<!--TOTAL-->
													<td style="text-align:center;display:none" colspan="2"></td> 
																
												</tr>
												<tr>
													<td colspan="2">
													 <label  style="margin-left: 0.5cm;">2.1  Costo de Coordinador</label> 
													</td>
													<!-- RURAL -->
													<td style="text-align:center"><form:input id="canDRCoord" path="canDRCoord" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="costDRCoord" path="costDRCoord" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="canIRCoord" path="canIRCoord" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="costIRCoord" path="costIRCoord" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="costTotalRCoord" path="costTotalRCoord" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td>
													<!-- URBANA PROVINCIAS -->
													<td style="text-align:center"><form:input id="canDPCoord" path="canDPCoord" size="5" cssClass="target" cssStyle="text-align: right;" /></td> 
													<td style="text-align:center"><form:input id="costDPCoord" path="costDPCoord" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="canIPCoord" path="canIPCoord" size="5" cssClass="target" cssStyle="text-align: right;" /></td>
													<td style="text-align:center"><form:input id="costIPCoord" path="costIPCoord" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="costTotalPCoord" path="costTotalPCoord" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td>
													<!-- URBANA LIMA -->
													<td style="text-align:center"><form:input id="canDLCoord" path="canDLCoord" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="costDLCoord" path="costDLCoord" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="canILCoord" path="canILCoord" size="5" cssClass="target" cssStyle="text-align: right;"/></td>
													<td style="text-align:center"><form:input id="costILCoord" path="costILCoord" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="costTotalLCoord" path="costTotalLCoord" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td>
													<!-- TOTAL -->	
													<td style="text-align:center"><form:input id="canTotalCoord" path="canTotalCoord" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td>
													<td style="text-align:center"><form:input id="costTotalCoord" path="costTotalCoord" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td>	
												</tr>
												<tr>
													<td colspan="2">
													 <label style="margin-left: 0.5cm;">2.2  Costo de Supervisor</label>
													</td>
													<!-- RURAL -->
													<td style="text-align:center"><form:input id="canDRSupe" path="canDRSupe" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="costDRSupe" path="costDRSupe" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="canIRSupe" path="canIRSupe" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="costIRSupe" path="costIRSupe" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="costTotalRSupe" path="costTotalRSupe" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td>	
													<!-- URBANA PROVINCIAS -->	
													<td style="text-align:center"><form:input id="canDPSupe" path="canDPSupe" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="costDPSupe" path="costDPSupe" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="canIPSupe" path="canIPSupe" size="5" cssClass="target" cssStyle="text-align: right;" /></td> 
													<td style="text-align:center"><form:input id="costIPSupe" path="costIPSupe" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="costTotalPSupe" path="costTotalPSupe" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td>
													<!-- URBANA LIMA -->	
													<td style="text-align:center"><form:input id="canDLSupe" path="canDLSupe" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="costDLSupe" path="costDLSupe" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="canILSupe" path="canILSupe" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="costILSupe" path="costILSupe" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="costTotalLSupe" path="costTotalLSupe" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td>	
													<!-- TOTAL -->	
													<td style="text-align:center"><form:input id="canTotalSupe" path="canTotalSupe" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td>
													<td style="text-align:center"><form:input id="costTotalSupe" path="costTotalSupe" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td>	
												</tr>
												<tr>
													<td colspan="2">
													 <label style="margin-left: 0.5cm;">2.3  Costo de Gestor</label>
													</td>
													<!-- RURAL -->
													<td style="text-align:center"><form:input id="canDRGest" path="canDRGest" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="costDRGest" path="costDRGest" size="5" cssClass="target" cssStyle="text-align: right;"/></td>
													<td style="text-align:center"><form:input id="canIRGest" path="canIRGest" size="5" cssClass="target" cssStyle="text-align: right;"/></td>  
													<td style="text-align:center"><form:input id="costIRGest" path="costIRGest" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="costTotalRGest" path="costTotalRGest" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td>
													<!-- URBANA PROVINCIAS -->
													<td style="text-align:center"><form:input id="canDPGest" path="canDPGest" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="costDPGest" path="costDPGest" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="canIPGest" path="canIPGest" size="5" cssClass="target" cssStyle="text-align: right;"/></td>
													<td style="text-align:center"><form:input id="costIPGest" path="costIPGest" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="costTotalPGest" path="costTotalPGest" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td>
													<!-- URBANA LIMA -->
													<td style="text-align:center"><form:input id="canDLGest" path="canDLGest" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="costDLGest" path="costDLGest" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="canILGest" path="canILGest" size="5" cssClass="target" cssStyle="text-align: right;"/></td>
													<td style="text-align:center"><form:input id="costILGest" path="costILGest" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="costTotalLGest" path="costTotalLGest" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td>	
													<!-- TOTAL -->	
													<td style="text-align:center"><form:input id="canTotalGest" path="canTotalGest" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td>
													<td style="text-align:center"><form:input id="costTotalGest" path="costTotalGest" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td>		
												</tr>
												<tr>
													<td colspan="2">
													 <label style="margin-left: 0.5cm;">2.4  Costo de Asistente y/o Auxiliar</label>
													</td>
													<!-- RURAL -->
													<td style="text-align:center"><form:input id="canDRAsist" path="canDRAsist" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="costDRAsist" path="costDRAsist" size="5" cssClass="target" cssStyle="text-align: right;"/></td>
													<td style="text-align:center"><form:input id="canIRAsist" path="canIRAsist" size="5" cssClass="target" cssStyle="text-align: right;"/></td>  
													<td style="text-align:center"><form:input id="costIRAsist" path="costIRAsist" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="costTotalRAsist" path="costTotalRAsist" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td>	
													<!-- URBANA PROVINCIAS -->	
													<td style="text-align:center"><form:input id="canDPAsist" path="canDPAsist" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="costDPAsist" path="costDPAsist" size="5" cssClass="target" cssStyle="text-align: right;"/></td>
													<td style="text-align:center"><form:input id="canIPAsist" path="canIPAsist" size="5" cssClass="target" cssStyle="text-align: right;"/></td>  
													<td style="text-align:center"><form:input id="costIPAsist" path="costIPAsist" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="costTotalPAsist" path="costTotalPAsist" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td>
													<!-- URBANA LIMA -->	
													<td style="text-align:center"><form:input id="canDLAsist" path="canDLAsist" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="costDLAsist" path="costDLAsist" size="5" cssClass="target" cssStyle="text-align: right;"/></td>
													<td style="text-align:center"><form:input id="canILAsist" path="canILAsist" size="5" cssClass="target" cssStyle="text-align: right;"/></td>  
													<td style="text-align:center"><form:input id="costILAsist" path="costILAsist" size="5" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="costTotalLAsist" path="costTotalLAsist" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td>	
													<!-- TOTAL -->	
													<td style="text-align:center"><form:input id="canTotalAsist" path="canTotalAsist" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td>
													<td style="text-align:center"><form:input id="costTotalAsist" path="costTotalAsist" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td>			
												</tr>
												<tr>
													<td colspan="2">
													 <label style="margin-left: 0.5cm;font-weight:bold">2.5  Costo Total por Gestion de Personal (2.1)+(2.2)+(2.3)+(2.4)</label>
													</td>
													<!-- RURAL -->
													<td style="text-align:center"><form:input id="canDRGP" path="canDRGP" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="costDRGP" path="costDRGP" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="canIRGP" path="canIRGP" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="costIRGP" path="costIRGP" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td>
													<td style="text-align:center"><form:input id="costTotalRGP" path="costTotalRGP" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td>
													<!-- URBANA PROVINCIAS -->
													<td style="text-align:center"><form:input id="canDPGP" path="canDPGP" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="costDPGP" path="costDPGP" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="canIPGP" path="canIPGP" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="costIPGP" path="costIPGP" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td>
													<td style="text-align:center"><form:input id="costTotalPGP" path="costTotalPGP" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td>	
													<!-- URBANA LIMA -->
													<td style="text-align:center"><form:input id="canDLGP" path="canDLGP" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="costDLGP" path="costDLGP" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="canILGP" path="canILGP" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td> 
													<td style="text-align:center"><form:input id="costILGP" path="costILGP" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td>
													<td style="text-align:center"><form:input id="costTotalLGP" path="costTotalLGP" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td>	
													<!-- TOTAL -->	
													<td style="text-align:center"><form:input id="canTotalGP" path="canTotalGP" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td>
													<td style="text-align:center"><form:input id="costTotalGP" path="costTotalGP" size="5" disabled="true" cssClass="target" cssStyle="text-align: right;"/></td>				
												</tr>
												<tr>
													<td colspan="2"> 
													 <label style="font-size: 12px; font-weight:bold">3  Costo promedio mensual por utiles y materiales de oficina</label>
													</td>
													<!-- RURAL -->
													<td style="text-align:center"></td>
													<td style="text-align:center"><form:input id="costoPromRural" path="costoPromRural" size="5" cssClass="target" cssStyle="text-align: right;"/></td>
													<td style="text-align:center"></td>
													<td style="text-align:center"></td>
													<td style="text-align:center"></td>
													<!-- URBANA PROVINCIAS --> 
													<td style="text-align:center"></td>
													<td style="text-align:center"><form:input id="costoPromUrbProv" path="costoPromUrbProv" size="5" cssClass="target" cssStyle="text-align: right;"/></td>
													<td style="text-align:center"></td>
													<td style="text-align:center"></td>
													<td style="text-align:center"></td>
													<!-- URBANA LIMA --> 
													<td style="text-align:center"></td>
													<td style="text-align:center"><form:input id="costoPromUrbLima" path="costoPromUrbLima" size="5" cssClass="target" cssStyle="text-align: right;"/></td>
													<td style="text-align:center"></td>
													<td style="text-align:center"></td>
													<td style="text-align:center"></td>
													<!-- TOTAL --> 
													<td style="text-align:center"></td>
													<td style="text-align:center"><form:input id="costTotalPromedio" path="costTotalPromedio" disabled="true" size="5" cssClass="target" cssStyle="text-align: right;"/></td>
																					
												</tr>
											</table> 	
										</td>
										</tr>
											
										<tr>
										<td colspan="2"></td>
										</tr>
										<tr>
										<td colspan="2"></td>
										</tr>
																				
										<tr class="filete-top">
										  <td colspan="2">
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
														<input type="button" class="boton" name="<portlet:namespace/>reportePdfF14C" style="display: none;"
																id="<portlet:namespace/>reportePdfF14C" class="button net-button-small" value="Imprimir PDF" />
													  </td>
													  <td width="16%" align="center">
														 <input type="button" class="boton" name="<portlet:namespace/>reporteExcelF14C" style="display: none;"
																id="<portlet:namespace/>reporteExcelF14C" class="button net-button-small" value="Exportar excel" />
													 </td>
													<td width="17%" align="center">													  
														  <input type="button" class="net-button-small" id="<portlet:namespace/>actualizarFormatoF14C"
															   name="<portlet:namespace/>actualizarFormatoF14C" value="Actualizar" style="display: none;"/>
																												
														<input type="button" class="net-button-small" id="<portlet:namespace/>guardarFormatoF14C"
															   name="<portlet:namespace/>guardarFormatoF14C" value="Grabar" />									   
													</td>
													<td width="17%" align="center">
														<input type="button" class="net-button-small" id="<portlet:namespace/>validacionFormatoF14C"
																name="<portlet:namespace/>validacionFormatoF14C" value="Validación" />
													</td>
													<td width="17%" align="center">
														<input type="button" class="net-button-small" id="<portlet:namespace/>envioDefinitivoF14C"
																name="<portlet:namespace/>envioDefinitivoF14C" value="Envío Def." />
													</td>
													<td width="17%" align="center">
														<input type="button" class="net-button-small" id="<portlet:namespace/>regresarFormatoF14C" 
															  name="<portlet:namespace/>regresarFormatoF14C" value="Regresar" />
													</td>
												 </tr>
											  </table>
	
											</td>
											</tr>
									    </table> 
									        <input type="hidden" id="<portlet:namespace/>flagCarga" name="flagCarga" value="" style="display: none;" />
	
										</td>
										</tr>
										<tr height="10px" class="filete-bottom">
											<td colspan="2"></td>
										</tr>
										<tr height="10px">
										 <td colspan="2"></td>
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
			 <!-- FIN DE DIV PARA NUEVO REGISTRO -->
					
			</div>
		</div>
	</div>
	
	
	<!-- DIVS PARA MENSAJES -->
	
	<div id="<portlet:namespace/>dialog-message-grabar" title="Osinergmin">
		<p>
			<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;">
			</span>
			<label id="<portlet:namespace/>dialog-message-content-grabar">Datos grabados exit&oacute;samente.</label>
		</p>	
	</div>
	
	
	
	
	
	
	
	
</form:form>