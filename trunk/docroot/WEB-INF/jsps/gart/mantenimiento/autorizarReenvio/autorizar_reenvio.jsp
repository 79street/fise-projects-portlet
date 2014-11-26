<%@include file="/WEB-INF/jsps/gart/ext/extAutorizarReenvio.jsp"%>


<link href="/fise-projects-portlet/css/tablas.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/fise-projects-portlet/js/fise.js"></script>



<script type="text/javascript">
$(document).ready(function () {	
	autorizarReenvio.init();
});
</script>
           
           
<form:form method="POST" style="padding:17px;padding-top:0px;" 
            modelAttribute="autorizarReenvioBean">
            
            
            	
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
											<td><output>Empresa:</output></td>
											<td>
												<form:select path="codEmpresaBusq" cssClass="select" cssStyle="width: 375px;">

													<c:if test="${autorizarReenvioBean.admin}">
														<form:option value="">-Todos-</form:option>
													</c:if>
													<form:options items="${autorizarReenvioBean.listaEmpresas}"  itemLabel="dscEmpresa" itemValue="codEmpresa"/>
												</form:select>
											</td>
											
											<td><output>Formato:</output></td>
											<td>
												<form:select path="formatoBusq" cssClass="select" cssStyle="width: 100px;">
													<form:option value="F12A">F12A</form:option>
													<form:option value="F12B">F12B</form:option>
													<form:option value="F12C">F12C</form:option>
													<form:option value="F12D">F12D</form:option>
													<form:option value="F13A">F13A</form:option>
													<form:option value="F14A">F14A</form:option>
													<form:option value="F14B">F14B</form:option>
													<form:option value="F14C">F14C</form:option>
												</form:select>
											</td>		
										</tr>
										<tr height="10px">
											<td colspan="4"></td>
										</tr>
										<tr>
											<td><output>Año:</output></td>
											<td>
												<form:input path="anioPresBusq" cssStyle="width: 50px; text-align: right;" maxlength="4"/>
											</td>
											
											<td><output>Mes:</output></td>
											<td>
												<form:select path="mesPresBusq" cssClass="select" cssStyle="width: 104px;">
													<form:option value="">-Seleccione-</form:option>
													<form:options items="${autorizarReenvioBean.listaMes}"/>
												</form:select>
												
											</td>																				
										</tr>
										<tr height="10px">
											<td colspan="4"></td>
										</tr>
										<tr>
											<td><output>Etapa:</output></td>
											<td>
												<form:select path="etapaBusq" cssClass="select" cssStyle="width: 140px;">
													<form:option value="SOLICITUD">SOLICITUD</form:option>
													<form:option value="LEV.OBS">LEV.OBS</form:option>
													<form:option value="RECONSIDERACION">RECONSIDERACION</form:option>
													<form:option value="RECONOCIDO">RECONOCIDO</form:option>
												</form:select>
											</td>
											<td></td>
											<td></td>											
										</tr>
										<tr height="10px">
											<td colspan="4"></td>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td style="aling:right"><input name="<portlet:namespace/>btnBuscarFormatoReenvio"
												id="<portlet:namespace/>btnBuscarFormatoReenvio" type="button"
												class="net-button-small" value="Buscar" style="aling:center" />
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
					</table>			
				</div>								
				<!-- FIN DE DIV  PARA BUSQUEDA -->					
            </div>
       </div>       
   </div>

   <!-- DIVS PARA MENSAJES -->
   
   	<div id="<portlet:namespace/>dialog-message-reenvio" title="Osinergmin">
		<p>
			<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;">
			</span>
			<label id="<portlet:namespace/>dialog-message-content-reenvio">Datos reenviados exit&oacute;samente.</label>
		</p>	
	</div>
	
	<div id="<portlet:namespace/>dialog-confirm" title="Confirmar acci&oacute;n">
		<p>	
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
			<label id="<portlet:namespace/>dialog-confirm-content">¿Está seguro?</label>
		</p>
	</div>`		
	
</form:form>