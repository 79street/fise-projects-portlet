<%@include file="/WEB-INF/jsps/gart/ext/extCargoFijo.jsp"%>


<link href="/fise-projects-portlet/css/tablas.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/fise-projects-portlet/js/fise.js"></script>



<script type="text/javascript">
$(document).ready(function () {	
	fiseCargoFijo.init();
});

</script>
           
           
<form:form method="POST" style="padding:17px;padding-top:0px;" 
            modelAttribute="fiseCargoFijoBean">
            
            
            	
<div id="d_filtro" class="net-frame-listado"> 
		<div id="d_filtro">
			<div id="div_contenido" >
			
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
											<td colspan="4"><output class="net-titulo">Criterios
													de Búsqueda:</output></td>
										</tr>
										
										<tr height="10px">
											<td colspan="4"></td>
										</tr>
										
										<tr>
											<td><output>Distribuidora Eléctrica:</output></td>
											<td colspan="3">
												<form:select path="codEmpresaBusq" cssClass="select" cssStyle="width: 375px;">

													<c:if test="${fiseCargoFijoBean.admin}">
														<form:option value="">-Todos-</form:option>
													</c:if>
													<form:options items="${fiseCargoFijoBean.listaEmpresas}"  itemLabel="dscEmpresa" itemValue="codEmpresa"/>
												</form:select>
											</td>
										</tr>
										
										<tr height="10px">
											<td colspan="4"></td>
										</tr>
										
										<tr>
											<td><output>Año Repo.:</output></td>											
											<td>
												<form:input path="anioRepBusq" cssStyle="width: 50px; text-align: right;" maxlength="4"/>
											</td>
											
											<td><output>Mes Repo.:</output></td>
											<td>
												<form:select path="mesRepBusq" cssClass="select" cssStyle="width: 104px;">
													<form:option value="">-Seleccione-</form:option>
													<form:options items="${fiseCargoFijoBean.listaMes}"/>
												</form:select>												
											</td>
																						
										</tr>					
										
										<tr height="10px">
											<td colspan="4"></td>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td></td>											
											<td>
											   <input name="<portlet:namespace/>btnBuscarCargosFijos"
												id="<portlet:namespace/>btnBuscarCargosFijos" type="button"
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
										<td align="center" width="90px">
											<div id="d_opc_crear">
												<input type="button" class="net-button-small"
													id="<portlet:namespace/>btnNuevoCargoFijo"
													name="<portlet:namespace/>btnNuevoCargoFijo" value="Nuevo" />
											</div>
										</td>	
										<td></td>										
																			
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
											<td><output class="net-titulo">FISE CARGO FIJO</output>
											</td>
										</tr>
										<tr height="10px">
											<td></td>
										</tr>					
										<tr>
									     <td>											
										   <table style="width: 100%;" border="0">
											 
											  <tr>
											    <td><label style="font-size: 12px; font-weight: bold">Distribuidora	Eléctrica:</label>
												</td>
											    <td>
											       <form:select path="codigoEmpresa" cssClass="select" cssStyle="width: 200px;">
											         <form:option value="">-Seleccione-</form:option>
													 <form:options items="${fiseCargoFijoBean.listaEmpresas}"
																   itemLabel="dscEmpresa" itemValue="codEmpresa" />
												  </form:select>
											   </td>
											   
											   <td><label style="font-size: 12px; font-weight: bold">Año Repo.:</label>
											   </td>
											   <td>
												<form:input path="anioReporte"  cssStyle="width: 50px; text-align: right;" maxlength="4"/>
											   </td>
											   
											   <td><label style="font-size: 12px; font-weight: bold">Mes Repo.:</label>
											   </td>
											   <td>
												 <form:select path="mesReporte" cssClass="select" cssStyle="width: 104px;">	
												    <form:option value="">-Seleccione-</form:option>												
													<form:options items="${fiseCargoFijoBean.listaMes}"/>
												 </form:select>
											   </td>
											   
										     </tr>
										     
										     <tr height="10px">
										      <td colspan="6"></td> 
										     </tr>
										     
										     <tr>
											   <td><label style="font-size: 12px; font-weight: bold">Número Usu. Benef.:</label>
											   </td>
											   <td>
												 <form:input path="numUsuBenef"  cssStyle="width: 50px; text-align: right;"/>
											   </td>
											   
											   <td><label style="font-size: 12px; font-weight: bold">Número Usu. Emp.:</label>
											   </td>
											   <td>
												 <form:input path="numUsuEmp"  cssStyle="width: 50px; text-align: right;"/>
											   </td>
											   
											   <td><label style="font-size: 12px; font-weight: bold">Número Vales Físcos Emi.:</label>
											   </td>
											   <td>
												 <form:input path="numValFEmi"  cssStyle="width: 50px; text-align: right;"/>
											   </td>
											   
											</tr>
											
											<tr height="10px">
										      <td colspan="6"></td> 
										     </tr>
										     									
											<tr>
											
											  <td><label style="font-size: 12px; font-weight: bold">Número Vales Físcos Canj.:</label>
											   </td>
											  <td>
												 <form:input path="numValFCan"  cssStyle="width: 50px; text-align: right;"/>
											  </td>
											
											 <td><label style="font-size: 12px; font-weight: bold">Número Vales Digitales Emi.:</label>
											   </td>
											  <td>
												 <form:input path="numValDEmi"  cssStyle="width: 50px; text-align: right;"/>
											  </td>
											  
											 <td><label style="font-size: 12px; font-weight: bold">Número Vales Digitales Canj.:</label>
											   </td>
											  <td>
												 <form:input path="numValDCan"  cssStyle="width: 50px; text-align: right;"/>
											  </td>
											  								
											</tr>
											
											 <tr height="10px">
										      <td colspan="6"></td> 
										     </tr>
																						
											<tr>
											
											  <td><label style="font-size: 12px; font-weight: bold">Número Agentes:</label>
											   </td>
											  <td>
												 <form:input path="numAgente"  cssStyle="width: 50px; text-align: right;"/>
											  </td>
											 
											  <td><label style="font-size: 12px; font-weight: bold">Monto Transf. Canje:</label>
											   </td>
											  <td>
												 <form:input path="montoCanje"  cssStyle="width: 50px; text-align: right;"/>
											  </td>
											  
											  <td><label style="font-size: 12px; font-weight: bold">Monto Fijo Mes:</label>
											   </td>
											  <td>
												 <form:input path="montoMes"  cssStyle="width: 50px; text-align: right;"/>
											  </td>						
											 
											</tr>
											
											 <tr height="10px">
										      <td colspan="6"></td> 
										     </tr>
											
											<tr> 
											
											 <td><label style="font-size: 12px; font-weight: bold">Fecha Informe Sustento:(dd/mm/aaaa)</label>
											  </td>
											  <td>											  										 
											   <form:input path="fechaSustento" cssStyle="text-align: right;"/>								    												
											  </td>
											
											 <td><label style="font-size: 12px; font-weight: bold">Número Doc. Informe Sustento:</label>
											   </td>
											  <td>
												 <form:input path="numDoc"  cssStyle="width: 50px; text-align: right;" onblur="isNumeric(this)"/> 
											  </td>
											  
											  <td><label style="font-size: 12px; font-weight: bold">Fecha Recepción Info.(dd/mm/aaaa):</label>
											  </td>
											  <td>				    
											  
											    <form:input path="fechaRecepcion" cssStyle="text-align: right;" />
												 							    												
											  
											  </td>		
											  										 											 
											</tr>
																																				  					
											<tr height="10px">
										      <td colspan="6"></td> 
										     </tr>
											
											<tr> 
											
											 <td><label style="font-size: 12px; font-weight: bold">Número Doc. Recepción Info.:</label>
											   </td>
											  <td>
												 <form:input path="numDocRecepcion"  cssStyle="width: 50px; text-align: right;" onblur="isNumeric(this)"/>
											  </td>
											
											 <td><label style="font-size: 12px; font-weight: bold">IGV:</label>
											   </td>
											  <td>
												 <form:input path="igv"  cssStyle="width: 50px; text-align: right;"/>
											  </td>
											  
											 <td><label style="font-size: 12px; font-weight: bold">Aplica IGV:</label>
											 </td>
											 <td>												     
											     <input type="radio"	name="aplicaIgv"
												        id="rbtIgvSI" value="1" checked="true"/>SI
												 &nbsp;&nbsp;&nbsp;													 
												<input type="radio"	name="aplicaIgv"
												       id="rbtIgvNO" value="0" />NO																																
											 </td>		
											  										 											 
											</tr>	
											
											<tr height="10px">
										      <td colspan="6"></td> 
										     </tr>
											
											<tr> 
											
											 <td><label style="font-size: 12px; font-weight: bold">Estado:</label>
											 </td>
											 <td>												     
											     <input type="radio"	name="estado"
												        id="rbtActivo" value="1" checked="true"/>Activo
												 &nbsp;&nbsp;&nbsp;													 
												<input type="radio"	name="estado"
												       id="rbtInactivo" value="0" />Inactivo																																
											 </td>	
											
											 <td><label style="font-size: 12px; font-weight: bold">Glosa:</label>
											   </td>
											  <td>
												 <form:textarea path="gloza" cols="30" rows="4" onkeypress="return soloLetras(event)"/> 
											  </td>
											  
											 <td>
											 </td>
											 <td>									 																														
											 </td>		
											  										 											 
											</tr>	
											
										  </table>										  								 								
										 </td>										 
										</tr>
																																				
										<tr height="10px" class="filete-bottom">
										  <td></td> 
										</tr>
																																																								
										<tr class="filete-top">
										<tr height="10px">
										   <td></td> 
										</tr>
											
										<tr>
										  <td>
											<table style="width: 100%">
											  <tr>												
												 <td>
												  <table style="width: 100%">
												    <tr>													  
													 <td width="17%" align="center">													  
														  <input type="button" class="net-button-small" id="<portlet:namespace/>actualizarCargoFijo"
															   name="<portlet:namespace/>actualizarCargoFijo" value="Actualizar" style="display: none;"/>
																												
														<input type="button" class="net-button-small" id="<portlet:namespace/>guardarCargoFijo"
															   name="<portlet:namespace/>guardarCargoFijo" value="Grabar" />									   
													 </td>													
													 <td width="17%" align="center">
														<input type="button" class="net-button-small" id="<portlet:namespace/>regresarCargoFijo" 
															  name="<portlet:namespace/>regresarCargoFijo" value="Regresar" />
													 </td>
												 </tr>
											  </table>
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
									</table>	
								</fieldset>
							</td>
						</tr>
						<tr>
						 <td></td>
						</tr>
					</table>	
				</div>				
				
				<!-- FIN DIV PARA NUEVO REGISTRO -->	
				
				
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
	
	<div id="<portlet:namespace/>dialog-confirm" title="Confirmar acci&oacute;n">
		<p>	
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
			<label id="<portlet:namespace/>dialog-confirm-content">¿Está seguro?</label>
		</p>
	</div>	
	
 
	 
</form:form>