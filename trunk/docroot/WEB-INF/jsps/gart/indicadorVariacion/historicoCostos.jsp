<%@include file="/WEB-INF/jsps/gart/ext/extHistoricoCostos.jsp"%>

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
<link rel="stylesheet" type="text/css" href="/fise-projects-portlet/css/third-party/jquery.jqplot.min.css" />

<script type="text/javascript" src="/fise-projects-portlet/js/jsPDF/jspdf.min.js"></script>

<script type="text/javascript">
$(document).ready(function () {	
	historicoCostos.init();	
});
</script>
           

           
<form:form method="POST" style="padding:17px;padding-top:0px;" modelAttribute="historicoCostosBean">
            
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
											<td><output>Distribuidora Eléctrica:</output></td>
											<td>
												 <form:select path="codEmpresaBusq" cssClass="select" cssStyle="width: 300px;">	
												 	<form:option value="NAC">Nacional</form:option>														
												    <form:options items="${historicoCostosBean.listaEmpresas}"  itemLabel="dscEmpresa" itemValue="codEmpresa"/>
												</form:select>	
											</td>
											
											<td><output>Formato:</output></td>
											<td>
												<form:select path="formatoBusq" cssClass="select" cssStyle="width: 200px;">
													<form:option value="F12A">F12A</form:option>
													<form:option value="F12B">F12B</form:option>
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
								<div id="divDispersionHid" style="overflow: auto; width: 100%; height:550px; padding: 4px;">
									<label id="<portlet:namespace/>titulo-imagen" style="color: #666; font-family: 'Trebuchet MS',Arial,Helvetica,sans-serif; font-size: 1.2em;"></label>
					            	<div id="chkDispersionHid" style="width:1000px; height:480px;">
					            	</div>
				           		</div>
				           		
				           		<input id="<portlet:namespace/>titulo1-imagen" style="display:none" />
				           		<input id="<portlet:namespace/>titulo2-imagen" style="display:none" />
								
								<div id="chartImgDiv" style="display:none; direction:ltr; height:480px; width:1000px"></div>
								
								<div>
								</div>
							</td>
						</tr>
						<tr height="10px">
							<td align="left" width="110px" >
								<input id="exportarPdf" type="button" value="Exportar a PDF" style="display:none;" />
							</td>
							<td align="left">
								<input id="exportarExcel" type="button" value="Exportar a Excel" style="display:none;" />
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