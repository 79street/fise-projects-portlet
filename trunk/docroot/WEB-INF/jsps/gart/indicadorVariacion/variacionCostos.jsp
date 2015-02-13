<%@include file="/WEB-INF/jsps/gart/ext/extVariacionCostos.jsp"%>

<link href="/fise-projects-portlet/css/tablas.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/fise-projects-portlet/js/fise.js"></script>

<!-- <script type="text/javascript" src="/fise-projects-portlet/js/jquery/jquery-1.11.0.min.js"></script> -->
<script type="text/javascript" src="/fise-projects-portlet/js/third-party/jqplot/JQuery.jqplot.min.js"></script>
<script type="text/javascript" src="/fise-projects-portlet/js/third-party/jqplot/jqplot.barRenderer.min.js"></script>
<script type="text/javascript" src="/fise-projects-portlet/js/third-party/jqplot/jqplot.categoryAxisRenderer.min.js"></script>
<script type="text/javascript" src="/fise-projects-portlet/js/third-party/jqplot/jqplot.pointLabels.min.js"></script>
<script type="text/javascript" src="/fise-projects-portlet/js/third-party/jqplot/jqplot.dateAxisRenderer.min.js"></script>
<script type="text/javascript" src="/fise-projects-portlet/js/third-party/jqplot/jqplot.canvasTextRenderer.min.js"></script>
<script type="text/javascript" src="/fise-projects-portlet/js/third-party/jqplot/jqplot.canvasOverlay.min.js"></script>
<script type="text/javascript" src="/fise-projects-portlet/js/third-party/jqplot/jqplot.canvasAxisTickRenderer.min.js"></script>
<script type="text/javascript" src="/fise-projects-portlet/js/third-party/jqplot/jqplot.canvasAxisLabelRenderer.min.js"></script>
<script type="text/javascript" src="/fise-projects-portlet/js/third-party/jqplot/jqplot.enhancedLegendRenderer.min.js"></script>
<script type="text/javascript" src="/fise-projects-portlet/js/third-party/jqplot/jqplot.highlighter.min.js"></script>
<link rel="stylesheet" type="text/css" href="/fise-projects-portlet/css/third-party/jquery.jqplot.min.css" />

<script type="text/javascript" src="/fise-projects-portlet/js/jsPDF/jspdf.min.js"></script>

<script type="text/javascript">
$(document).ready(function () {	
	variacionCostos.init();
});
</script>
           

           
<form:form method="POST" style="padding:17px;padding-top:0px;" modelAttribute="variacionCostosBean">
            
             	
<div id="d_filtro" class="net-frame-listado"> 
		<div id="d_filtro">
			<div id="div_contenido" >
			
			  <!-- DIV PARA BUSQUEDA  -->	
			
				<div id="<portlet:namespace/>div_buscar" class="net-frame-listado">
				
			     <table style="width: 100%;" border="0">
						<tr>
							<td colspan="2">
								<!-- <output class="net-titulo">Situación actual de la declaración de Gastos</output> -->
							</td>
						</tr>
						<tr height="10px">
							<td colspan="2"></td>
						</tr>
						<tr>
							<td class="filete" colspan="2">
								<fieldset class="">
									<table class="" style="width: 100%;" border="0">
										<tr>
											<td colspan="3"><output class="net-titulo">Parámetros:</output></td>
										</tr>
										<tr height="10px">
											<td colspan="4"></td>
										</tr>
										<tr>
											<td><output>Grupo de Información:</output></td>
											<td>
												 <form:select path="grupoInfoBusq" cssClass="select" cssStyle="width: 200px;">															
												    <form:options items="${variacionCostosBean.listaGrupoInfo}"  itemLabel="descripcion" itemValue="idGrupoInformacion"/>
												</form:select>	
											</td>
											
											<td><output>Formato:</output></td>
											<td>
												<form:select path="formatoBusq" cssClass="select" cssStyle="width: 200px;">
													<form:option value="F14A">F14A</form:option>
													<form:option value="F14B">F14B</form:option>
												</form:select>
											</td>							
										</tr>
										<tr height="10px">
											<td colspan="4"></td>
										</tr>
										<tr>
										   <td><output>Zona:</output></td>											
										    <td>
												<form:select path="zonaBusq" cssClass="select" cssStyle="width: 200px;">
													<form:option value="R">Rural</form:option>
													<form:option value="P">Urbano Provincias</form:option>
													<form:option value="L">Urbano Lima</form:option>
												</form:select>
											</td>		
										
											<td><output>Concepto:</output></td>
											<td>												
												 <form:select path="conceptoBusq" cssClass="select" cssStyle="width: 373px;">	
												</form:select>													
											</td>		
																													
										</tr>
										<tr height="10px">
											<td colspan="4"></td>
										</tr>
										
										<tr>
										   <td><output>Etapa:</output></td>											
										    <td>
												<form:select path="etapaBusq" cssClass="select" cssStyle="width: 200px;">
													<form:option value="HISTORICO">HISTÓRICO</form:option>
													<form:option value="SOLICITUD">SOLICITUD</form:option>
													<form:option value="LEV.OBS">LEVANTAMIENTO DE OBSERVACIONES</form:option>
													<form:option value="ESTABLECIDO">ESTABLECIDO</form:option>
													
												</form:select>
											</td>		
										
											<td colspan="2">										
											</td>		
																													
										</tr>
										<tr height="10px">
											<td colspan="4"></td>
										</tr>
										
										<tr>
											<td></td>
											<td></td>	
											<td></td>												
											<td style="aling:right"><input name="<portlet:namespace/>btnGenerar"
												id="<portlet:namespace/>btnGenerar" type="button"
												class="net-button-small" value="Generar" style="align:center" />
											</td>
										</tr>
									</table>
								</fieldset>
							</td>
						</tr>
						<tr height="10px">
							<td colspan="2"></td>
						</tr>
						<tr>
							<td align="center" colspan="2">
								<!-- nuevo div para mostrar la grafica -->
								<div id="divDispersionHid" style="overflow: auto; width: 100%; height:400px; padding: 4px;">
					            	<div id="chkDispersionHid" style="width:1000px; height:400px;">
					            	</div>
				           		</div>
								
								<div id="chartImgDiv" style="display:none; direction:ltr; height:400px; width:1000px"></div>
								
								<div>
								</div>
							</td>
						</tr>
						<tr height="10px">
							<td align="left" width="110px" >
								<input id="exportarPdf" type="button" value="Exportar a PDF" style="display:none;" />
							</td>
							<td align="left" >
								<input id="exportarExcel" type="button" value="Exportar a Excel" style="display:block;" />
							</td>
						</tr>
						<tr height="10px">
							<td colspan="2"></td>
						</tr>			
					</table>			
				</div>								
				<!-- FIN DE DIV  PARA BUSQUEDA -->	
								
            </div>
       </div>       
   </div>

   <!-- DIVS PARA MENSAJES -->
   
    <!-- DIALOGO PARA INFORMACION -->
   
   	<div id="<portlet:namespace/>dialog-message-notificacion" title="Mensaje de Informaci&oacute;n">
		<p>
			<img src="/fise-projects-portlet/images/success.png" style="float:left; margin:20px 25px 20px 5px;">	
			<br/>
			<label class="labelCentrado" id="<portlet:namespace/>dialog-message-content-notificacion">Datos notificados exit&oacute;samente.</label>
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