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
								<!-- <output class="net-titulo">Situaci�n actual de la declaraci�n de Gastos</output> -->
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
													de B�squeda:</output></td>
										</tr>
										
										<tr height="10px">
											<td colspan="4"></td>
										</tr>
										
										<tr>
											<td><output>Distribuidora El�ctrica:</output></td>
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
											<td><output>A�o Reportado:</output></td>											
											<td>
												<form:input path="anioRepBusq" cssStyle="width: 50px; text-align: right;" maxlength="4"/>
											</td>
											
											<td><output>Mes Reportado:</output></td>
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
											<td><output class="net-titulo">DETALLE DE DATOS DEL PROYECTO FISE</output>
											</td>
										</tr>
										<tr height="10px">
											<td></td>
										</tr>					
										<tr>
									     <td>											
										   <table style="width: 100%;" border="0">
											 
											  <tr>
											    <td><label style="font-size: 12px; font-weight: bold">Distribuidora	El�ctrica:</label>
												</td>
											    <td>
											       <form:select path="codigoEmpresa" cssClass="select" cssStyle="width: 200px;">
											         <form:option value="">-Seleccione-</form:option>
													 <form:options items="${fiseCargoFijoBean.listaEmpresas}"
																   itemLabel="dscEmpresa" itemValue="codEmpresa" />
												  </form:select>
											   </td>
											   
											   <td><label style="font-size: 12px; font-weight: bold">A�o Reportado:</label>
											   </td>
											   <td>
												<form:input path="anioReporte"  cssStyle="width: 50px; text-align: right;" maxlength="4"/>
											   </td>
											   
											   <td><label style="font-size: 12px; font-weight: bold">Mes Reportado:</label>
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
										       <td colspan="6"> 
										         <table style="width: 50%;" border="0">
										          <tr>
										           <td></td>
										           <td width="130px" align="center"><label style="font-size: 12px; font-weight: bold">Rural</label></td>
											       <td width="130px" align="center"><label style="font-size: 12px; font-weight: bold">Urbano Provincias</label></td>
											       <td width="130px" align="center"><label style="font-size: 12px; font-weight: bold">Urbano Lima</label></td>
											       								          
										          </tr>	
										          
										          <tr height="10px">
										           <td colspan="4"></td> 
											      </tr>
											     
											      <tr>
											        <td><label style="font-size: 12px; font-weight: bold">N�mero Usuarios Beneficiarios:</label></td>
											        <td align="center">
													 <form:input path="numUsuBenefR"  cssStyle="width: 65px; text-align: right;"/>
												    </td>
												    <td align="center">
													 <form:input path="numUsuBenefP"  cssStyle="width: 65px; text-align: right;"/>
												    </td>
												    <td align="center">
													 <form:input path="numUsuBenefL"  cssStyle="width: 65px; text-align: right;"/>
												    </td>											  
											     </tr>								     
											     
											     <tr height="10px">
											      <td colspan="4"></td> 
											     </tr>
											     
											     <tr>
											      <td><label style="font-size: 12px; font-weight: bold">N�mero Usuarios Empadronados:</label></td>
											      <td align="center">
													 <form:input path="numUsuEmpR"  cssStyle="width: 65px; text-align: right;"/>
												  </td>
												  <td align="center">
													 <form:input path="numUsuEmpP"  cssStyle="width: 65px; text-align: right;"/>
												  </td>
												  <td align="center">
													 <form:input path="numUsuEmpL"  cssStyle="width: 65px; text-align: right;"/>
												  </td>												  											  
											     </tr>		
											     
											     <tr height="10px">
											      <td colspan="4"></td> 
											     </tr>
											     
											     <tr>
											      <td><label style="font-size: 12px; font-weight: bold">N�mero Vales F�sicos Emitidos:</label></td>
											      <td align="center">
													 <form:input path="numValFEmiR"  cssStyle="width: 65px; text-align: right;"/>
												  </td>
												  <td align="center">
													 <form:input path="numValFEmiP"  cssStyle="width: 65px; text-align: right;"/>
												  </td>
												  <td align="center">
													 <form:input path="numValFEmiL"  cssStyle="width: 65px; text-align: right;"/>
												  </td>		 						  
												  
											     </tr>	
											     
											     <tr height="10px">
											      <td colspan="4"></td> 
											     </tr>
											     
											     <tr>
											      <td><label style="font-size: 12px; font-weight: bold">N�mero Vales Digitales Emitidos:</label></td>
											      <td align="center">
													 <form:input path="numValDEmiR"  cssStyle="width: 65px; text-align: right;"/>
												  </td>
												  <td align="center">
													 <form:input path="numValDEmiP"  cssStyle="width: 65px; text-align: right;"/>
												  </td>
												  <td align="center">
													 <form:input path="numValDEmiL"  cssStyle="width: 65px; text-align: right;"/>
												  </td>
												  												 
											     </tr>	
											     
											     <tr height="10px">
											      <td colspan="4"></td> 
											     </tr>
											     
											     <tr>
											      <td><label style="font-size: 12px; font-weight: bold">N�mero Vales F�sicos Canjeados:</label></td>
											      <td align="center">
													 <form:input path="numValFCanR"  cssStyle="width: 65px; text-align: right;"/>
												  </td>
												  <td align="center">
													 <form:input path="numValFCanP"  cssStyle="width: 65px; text-align: right;"/>
												  </td>
												  <td align="center">
													 <form:input path="numValFCanL"  cssStyle="width: 65px; text-align: right;"/>
												  </td>
												 					  
											     </tr>	
											     
											     <tr height="10px">
											      <td colspan="4"></td> 
											     </tr>
											     
											     <tr>
											      <td><label style="font-size: 12px; font-weight: bold">N�mero Vales Digitales Canjeados:</label></td>
											      <td align="center">
													 <form:input path="numValDCanR"  cssStyle="width: 65px; text-align: right;"/>
												  </td>
												  <td align="center">
													 <form:input path="numValDCanP"  cssStyle="width: 65px; text-align: right;"/>
												  </td>
												  <td align="center">
													 <form:input path="numValDCanL"  cssStyle="width: 65px; text-align: right;"/>
												  </td>		 					  
												  
											     </tr>
											     
											     <tr height="10px">
											      <td colspan="4"></td> 
											     </tr>
											     
											     <tr>
											      <td><label style="font-size: 12px; font-weight: bold">N�mero Agentes:</label></td>
											      <td align="center">
													 <form:input path="numAgenteR"  cssStyle="width: 65px; text-align: right;" />
												  </td>
												  <td align="center">
													 <form:input path="numAgenteP"  cssStyle="width: 65px; text-align: right;"/>
												  </td>
												  <td align="center">
													 <form:input path="numAgenteL"  cssStyle="width: 65px; text-align: right;"/>
												  </td>									 						  
												  
											     </tr>	
											     
											     <tr height="10px">
											      <td colspan="4"></td> 
											     </tr>
											     
											     <tr>
											      <td><label style="font-size: 12px; font-weight: bold">Cargo Fijo del Mes:</label></td>
											      <td align="center">
													 <form:input path="montoMesR"  cssStyle="width: 65px; text-align: right;"/>
												  </td>
												  <td align="center">
													 <form:input path="montoMesP"  cssStyle="width: 65px; text-align: right;"/>
												  </td>
												  <td align="center">
													 <form:input path="montoMesL"  cssStyle="width: 65px; text-align: right;"/>
												  </td>								  			 					  
												  
											     </tr>	          
										          								        
										         </table>						       
										       </td>								      
										     </tr>								     
										     
										     <tr height="10px">
										      <td colspan="6"></td> 
										     </tr>										
											
											<tr> 
											
											  <td><label style="font-size: 12px; font-weight: bold">Monto Transferido x Canje:</label>
											   </td>
											  <td>
												 <form:input path="montoCanje"  cssStyle="width: 65px; text-align: right;"/>
											  </td>
											
											 <td width="150px"> 
											    <label style="font-size: 12px; font-weight: bold">Fecha Informe Sustento:(dd/mm/aaaa)</label>
											  </td>
											  <td>											  										 
											   <form:input path="fechaSustento" cssStyle="text-align: right;" size="8"/>								    												
											  </td>										 
											  
											  <td width="200px">
											     <label style="font-size: 12px; font-weight: bold">Fecha Recepci�n Informaci�n:(dd/mm/aaaa)</label>
											  </td>
											  <td>									  
											    <form:input path="fechaRecepcion" cssStyle="text-align: right;" size="8" />			 							    												
											  
											  </td>		
											  										 											 
											</tr>
																																				  					
											<tr height="10px">
										      <td colspan="6"></td> 
										     </tr>
											
											<tr> 
											
											 <td><label style="font-size: 12px; font-weight: bold">N�mero Doc. Recepci�n Informe.:</label>
											   </td>
											  <td>
												 <form:input path="numDocRecepcion"  cssStyle="width: 65px; text-align: right;" onKeyPress="return isNumeric(event)" maxlength="13"/>
											  </td>
											  
											  <td><label style="font-size: 12px; font-weight: bold">N�mero Doc. Informe Sustento:</label>
											   </td>
											  <td>
												 <form:input path="numDoc"  cssStyle="width: 65px; text-align: right;"  onKeyPress="return isNumeric(event)" maxlength="13"/> 
											  </td>
											
											 <td><label style="font-size: 12px; font-weight: bold">IGV S/.:</label>
											   </td>
											  <td>
												 <form:input path="igv"  cssStyle="width: 65px; text-align: right;"/>
											  </td>										 
											  										 											 
											</tr>	
											
											<tr height="10px">
										      <td colspan="6"></td> 
										     </tr>
											
											<tr> 
											
											<td><label style="font-size: 12px; font-weight: bold">Aplica IGV:</label>
											 </td>
											 <td>												     
											     <input type="radio"	name="aplicaIgv"
												        id="rbtIgvSI" value="1" checked="true"/>SI
												 &nbsp;&nbsp;&nbsp;													 
												<input type="radio"	name="aplicaIgv"
												       id="rbtIgvNO" value="0" />NO																																
											 </td>		
											
											 <td><label style="font-size: 12px; font-weight: bold">Estado:</label>
											 </td>
											 <td width="120px">												     
											     <input type="radio"	name="estado"
												        id="rbtActivo" value="1" checked="true"/>Activo
												 &nbsp;&nbsp;&nbsp;													 
												<input type="radio"	name="estado"
												       id="rbtInactivo" value="0" />Inactivo																																
											 </td>	
											
											 <td><label style="font-size: 12px; font-weight: bold">Glosa:</label>
											   </td>
											  <td>
												 <form:textarea path="gloza" cols="30" rows="4" /> 
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
														<input type="button" class="boton" name="<portlet:namespace/>reportePdfCargoFijos" style="display: none;"
																id="<portlet:namespace/>reportePdfCargoFijos" class="button net-button-small" value="Imprimir PDF" />
													  </td>
													  <td width="17%" align="center">
														 <input type="button" class="boton" name="<portlet:namespace/>reporteExcelCargoFijos" style="display: none;"
																id="<portlet:namespace/>reporteExcelCargoFijos" class="button net-button-small" value="Exportar excel" />
													 </td>
												    													  
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
	
	<div id="<portlet:namespace/>dialog-message-grabar" title="Mensaje de &Eacute;xito">
		<p>
			<img src="/fise-projects-portlet/images/success.png" style="float:left; margin:20px 25px 20px 5px;">	
			<br/>
			<label class="labelCentrado" id="<portlet:namespace/>dialog-message-content-grabar">Datos grabados exit&oacute;samente.</label>
		</p>	
	</div>
	
	<div id="<portlet:namespace/>dialog-confirm" title="Mensaje de Confirmaci&oacute;n">
		<p>	
			<img src="/fise-projects-portlet/images/confirm.png" style="float:left; margin:20px 25px 20px 5px;">
			<br/>	
			<label class="labelCentrado" id="<portlet:namespace/>dialog-confirm-content">�Est� seguro?</label>
		</p>
	</div>
	
	<!-- DIALOGO PARA ALERTAR DE VALIDACION -->
	
	<div id="<portlet:namespace/>dialog-alert" title="Mensaje de Validaci&oacute;n">
		<p>	
			<img src="/fise-projects-portlet/images/warning.png" style="float:left; margin:20px 25px 20px 5px;">
			<br/>
			<label class="labelCentrado" id="<portlet:namespace/>dialog-alert-content">Debe Ingresar..</label>
		</p>
	</div>	
	
	<!-- DIALOGO PARA ERRORES -->
	
	<div id="<portlet:namespace/>dialog-error" title="Mensaje de Error">
		<p>	
			<img src="/fise-projects-portlet/images/error.png" style="float:left; margin:20px 25px 20px 5px;">
			<br/>
			<label class="labelCentrado" id="<portlet:namespace/>dialog-error-content">Error..</label>
		</p>
	</div>	
	
	<!-- DIALOGO PARA INFORMAR UN MENSAJE -->
	
	<div id="<portlet:namespace/>dialog-info" title="Mensaje de Informaci&oacute;n">
		<p>	
			<img src="/fise-projects-portlet/images/info.png" style="float:left; margin:20px 25px 20px 5px;">
			<br/>
			<label class="labelCentrado" id="<portlet:namespace/>dialog-info-content">Error..</label>
		</p>
	</div>
	
 
	 
</form:form>