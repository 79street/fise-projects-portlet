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
<portlet:defineObjects />


<script type="text/javascript">
var data_funcion = []; 

$(document).ready(function () {	
	 buildGrids();
	 buidGridsObservacion();
	 $("#<portlet:namespace/>crearFormato").click(function() {<portlet:namespace/>crearFormato();});
	 $("#<portlet:namespace/>guardarFormato").click(function() {<portlet:namespace/>guardarFormato();});
	 $("#<portlet:namespace/>regresarFormato").click(function(){<portlet:namespace/>regresar();});
	 $("#<portlet:namespace/>buscarFormato").click(function() {<portlet:namespace/>buscar();});
	 $("#<portlet:namespace/>cargarFormatoExcel").click(function() {<portlet:namespace/>cargarFormatoExcel();});
	 $("#<portlet:namespace/>cargarFormatoTexto").click(function() {<portlet:namespace/>cargarFormatoTexto();});
	 $("#s_empresa").change(function(){cargarPeriodoYCostos(this.value,'');});
	 $("#s_periodoenvio_present").change(function(){cargarPeriodoYCostos('',this.value);});
	 $("#<portlet:namespace/>cargaExcel").click(function() {<portlet:namespace/>mostrarFormularioCargaExcel();});
	 $("#<portlet:namespace/>cargaTxt").click(function() {<portlet:namespace/>mostrarFormularioCargaTexto();});
	 $("#<portlet:namespace/>reportePdf").click(function() {<portlet:namespace/>mostrarReportePdf();});
	 $("#<portlet:namespace/>reporteExcel").click(function() {<portlet:namespace/>mostrarReporteExcel();});
	 $("#<portlet:namespace/>validacionFormato").click(function() {<portlet:namespace/>validacionFormato();});
	 //$("#<portlet:namespace/>reporteValidacion").click(function() {<portlet:namespace/>mostrarReporteValidacion();});
	 $("#<portlet:namespace/>envioDefinitivo").click(function() {confirmarEnvioDefinitivo();});
	 initDialogs();
	 //initBlockUI();		
	 //SE CARGA VARIABLES EN SESION PARA MOSTRAR LOS PANELES DE NUEVO O EDICION MANEJADOS
	 var codEmpSes = $("#codEmpresaSes").val();
	 var anioPresSes = $("#anioPresSes").val();
	 var mesPresSes = $("#mesPresSes").val();
	 var anioEjeSes = $("#anioEjecSes").val();
	 var mesEjeSes = $("#mesEjecSes").val();
	 var etapaSes = $("#etapaSes").val();
	 //
	 var flag = $("#flag").val();
	 //alert('flagdecarga:'+flag);
	 if( $('#flag').val()=='N' ){//solo ocurre cuando hay un error en la carga de formularios, sino se muestra el proceso normal
		 inicializarFormulario();
		 mostrarUltimoFormato();
		 $("#s_empresa").val(codEmpSes);
		 //$("#s_periodoenvio_present").val(anioPresSes+completarCerosIzq(mesPresSes,2)+etapaSes);
		 if( $('#flagPeriodoEjecucion').val()=='S' ){
			 $("#i_anioejecuc").val(anioEjeSes);
			 $("#s_mes_ejecuc").val(mesEjeSes);
		}else{
			$("#i_anioejecuc").val(anioPresSes);
			 $("#s_mes_ejecuc").val(mesPresSes);
		}
		$('#<portlet:namespace/>validacionFormato').css('display','none');
		cargarPeriodoYCostos('',anioPresSes+completarCerosIzq(mesPresSes,2)+etapaSes);
	 }else{
		//alert(codEmpSes+','+anioPresSes+','+mesPresSes+','+anioEjeSes+','+mesEjeSes+','+etapaSes);
		 if(codEmpSes != '' && anioPresSes != '' && mesPresSes != '' && anioEjeSes != '' && mesEjeSes != '' && etapaSes != ''){
		 	 editarFormato(codEmpSes, anioPresSes, mesPresSes, anioEjeSes, mesEjeSes, etapaSes);
		 }
	 }
	 
	 
	 //SE CARGA VALORES POR DEFECTO PARA LA BUSQUEDA
	 $("#i_anio_d").val($("#anioDesdeSes").val());
	 $("#s_mes_d").val(parseInt($("#mesDesdeSes").val()));
	 $("#i_anio_h").val($("#anioHastaSes").val());
	 $("#s_mes_h").val(parseInt($("#mesHastaSes").val()));
	 $("#s_etapa").val($("#codEtapaSes").val());
	 <portlet:namespace/>buscar();
	 var mensajeInfo = $("#mensajeInfo").val();
	 var mensajeError = $("#mensajeError").val();
	 //SE MUESTRAN LOS MENSAJES DE ERROR PARA LA CARGA DE LOS ARCHIVOS
	 if(mensajeError!=''){
		 /*if(codEmpSes != '' && anioPresSes != '' && mesPresSes != '' && anioEjeSes == '' && mesEjeSes == '' && etapaSes == ''){
			 inicializarFormulario();
			 mostrarUltimoFormato();
			 $("#s_empresa").val(codEmpSes);
			 $("#s_periodoenvio_present").val(anioPresSes+completarCerosIzq(mesPresSes,2)+etapaSes);
		}*/
		 //se muestra el panel de errores si se produce en la carga de archivos
		$("#dialog-form-error").dialog( "open" );
	}else{
		//Se muestra el mensaje de informacion exitosa
		 if(mensajeInfo!=''){
			$("#dialog-message-content").html(mensajeInfo);
			$("#dialog-message").dialog( "open" );			
		 }
	 }
	 //limpiar variables
	 $("#codEmpresaSes").val('');
	 $("#anioPresSes").val('');
	 $("#mesPresSes").val('');
	 $("#anioEjecSes").val('');
	 $("#mesEjecSes").val('');
	 $("#etapaSes").val('');
	 
	 initBlockUI();	
});

////////VALIDACIONES
function inicializarFormulario(){
	$('#s_empresa').val('');
	$('#s_periodoenvio_present').val('');
	if( $('#flagPeriodoEjecucion').val()=='S' ){
		$('#i_anioejecuc').val('').css('text-align','right');
		$('#s_mes_ejecuc').val('');
		$('#i_anioejecuc').attr("disabled",false);
		$('#s_mes_ejecuc').attr("disabled",false);
	}
	//
	$('#i_nroEmpad_r').val('0').css('text-align','right');
	$('#i_costoUnitEmpad_r').val('0').css('text-align','right');
	$('#i_costoTotalEmpad_r').val('0.00').css('text-align','right');
	$('#i_nroEmpad_p').val('0').css('text-align','right');
	$('#i_costoUnitEmpad_p').val('0').css('text-align','right');
	$('#i_costoTotalEmpad_p').val('0.00').css('text-align','right');
	$('#i_nroEmpad_l').val('0').css('text-align','right');
	$('#i_costoUnitEmpad_l').val('0').css('text-align','right');
	$('#i_costoTotalEmpad_l').val('0.00').css('text-align','right');
	//
	$('#i_nroAgentGlp_r').val('0').css('text-align','right');
	$('#i_costoUnitAgent_r').val('0').css('text-align','right');
	$('#i_costoTotalAgent_r').val('0.00').css('text-align','right');
	$('#i_nroAgentGlp_p').val('0').css('text-align','right');
	$('#i_costoUnitAgent_p').val('0').css('text-align','right');
	$('#i_costoTotalAgent_p').val('0.00').css('text-align','right');
	$('#i_nroAgentGlp_l').val('0').css('text-align','right');
	$('#i_costoUnitAgent_l').val('0').css('text-align','right');
	$('#i_costoTotalAgent_l').val('0.00').css('text-align','right');
	//
	$('#i_despPersonal_r').val('0.00').css('text-align','right');
	$('#i_activExtraord_r').val('0.00').css('text-align','right');
	$('#i_despPersonal_p').val('0.00').css('text-align','right');
	$('#i_activExtraord_p').val('0.00').css('text-align','right');
	$('#i_despPersonal_l').val('0.00').css('text-align','right');
	$('#i_activExtraord_l').val('0.00').css('text-align','right');
	//
	$('#i_importeEmpad').val('0.00').css('text-align','right');
	$('#i_importeAgent').val('0.00').css('text-align','right');
	$('#i_importeDespPersonal').val('0.00').css('text-align','right');
	$('#i_importeActivExtraord').val('0.00').css('text-align','right');
	$('#i_totalGeneral').val('0.00').css('text-align','right');
	//
	realizarCalculoCampos();
	//quitamos los componentes deshabilitados
	$('#s_empresa').attr("disabled",false);
	$('#s_periodoenvio_present').attr("disabled",false);
	//
	deshabilitarCampos();
	soloNumerosEnteros();
}
function soloNumerosEnteros(){
	$('#i_nroEmpad_r').attr("onkeypress","return soloNumerosDecimales(event, 1, 'i_nroEmpad_r',7,0)");
	$('#i_nroEmpad_p').attr("onkeypress","return soloNumerosDecimales(event, 1, 'i_nroEmpad_p',7,0)");
	$('#i_nroEmpad_l').attr("onkeypress","return soloNumerosDecimales(event, 1, 'i_nroEmpad_l',7,0)");
	$('#i_nroAgentGlp_r').attr("onkeypress","return soloNumerosDecimales(event, 1, 'i_nroAgentGlp_r',7,0)");
	$('#i_nroAgentGlp_p').attr("onkeypress","return soloNumerosDecimales(event, 1, 'i_nroAgentGlp_p',7,0)");
	$('#i_nroAgentGlp_l').attr("onkeypress","return soloNumerosDecimales(event, 1, 'i_nroAgentGlp_l',7,0)");
}
function realizarCalculoCampos(){
	$('#i_nroEmpad_r').attr("onchange","calculoTotal()");
	$('#i_costoUnitEmpad_r').attr("onchange","calculoTotal()");
	$('#i_nroEmpad_p').attr("onchange","calculoTotal()");
	$('#i_costoUnitEmpad_p').attr("onchange","calculoTotal()");
	$('#i_nroEmpad_l').attr("onchange","calculoTotal()");
	$('#i_costoUnitEmpad_l').attr("onchange","calculoTotal()");
	//
	$('#i_nroAgentGlp_r').attr("onchange","calculoTotal()");
	$('#i_costoUnitAgent_r').attr("onchange","calculoTotal()");
	$('#i_nroAgentGlp_p').attr("onchange","calculoTotal()");
	$('#i_costoUnitAgent_p').attr("onchange","calculoTotal()");
	$('#i_nroAgentGlp_l').attr("onchange","calculoTotal()");
	$('#i_costoUnitAgent_l').attr("onchange","calculoTotal()");
	//
	$('#i_despPersonal_r').attr("onchange","calculoTotal()");
	$('#i_activExtraord_r').attr("onchange","calculoTotal()");
	$('#i_despPersonal_p').attr("onchange","calculoTotal()");
	$('#i_activExtraord_p').attr("onchange","calculoTotal()");
	$('#i_despPersonal_l').attr("onchange","calculoTotal()");
	$('#i_activExtraord_l').attr("onchange","calculoTotal()");
}
function deshabilitarCampos(){
	//completamos los decimales
	$('#i_costoUnitEmpad_r').attr("disabled",true);
	$('#i_costoUnitEmpad_p').attr("disabled",true);
	$('#i_costoUnitEmpad_l').attr("disabled",true);
	
	$('#i_costoTotalEmpad_r').attr("disabled",true);
	$('#i_costoTotalEmpad_p').attr("disabled",true);
	$('#i_costoTotalEmpad_l').attr("disabled",true);
	//
	$('#i_costoUnitAgent_r').attr("disabled",true);
	$('#i_costoUnitAgent_p').attr("disabled",true);
	$('#i_costoUnitAgent_l').attr("disabled",true);
	
	$('#i_costoTotalAgent_r').attr("disabled",true);
	$('#i_costoTotalAgent_p').attr("disabled",true);
	$('#i_costoTotalAgent_l').attr("disabled",true);
	//
	$('#i_importeEmpad').attr("disabled",true);
	$('#i_importeAgent').attr("disabled",true);
	$('#i_importeDespPersonal').attr("disabled",true);
	$('#i_importeActivExtraord').attr("disabled",true);
	//
	$('#i_totalGeneral').attr("disabled",true);
	//
}
function removerDeshabilitados(){
	$('#s_empresa').removeAttr("disabled");
	$('#s_periodoenvio_present').removeAttr("disabled");
	if( $('#flagPeriodoEjecucion').val()=='S' ){
		$('#i_anioejecuc').removeAttr("disabled");
		$('#s_mes_ejecuc').removeAttr("disabled");
	}
	//
	$('#i_nroEmpad_r').removeAttr("disabled");
	$('#i_costoUnitEmpad_r').removeAttr("disabled");
	$('#i_costoTotalEmpad_r').removeAttr("disabled");
	$('#i_nroAgentGlp_r').removeAttr("disabled");
	$('#i_costoUnitAgent_r').removeAttr("disabled");
	$('#i_costoTotalAgent_r').removeAttr("disabled");
	$('#i_despPersonal_r').removeAttr("disabled");
	$('#i_activExtraord_r').removeAttr("disabled");
	//
	$('#i_nroEmpad_p').removeAttr("disabled");
	$('#i_costoUnitEmpad_p').removeAttr("disabled");
	$('#i_costoTotalEmpad_p').removeAttr("disabled");
	$('#i_nroAgentGlp_p').removeAttr("disabled");
	$('#i_costoUnitAgent_p').removeAttr("disabled");
	$('#i_costoTotalAgent_p').removeAttr("disabled");
	$('#i_despPersonal_p').removeAttr("disabled");
	$('#i_activExtraord_p').removeAttr("disabled");
	//
	$('#i_nroEmpad_l').removeAttr("disabled");
	$('#i_costoUnitEmpad_l').removeAttr("disabled");
	$('#i_costoTotalEmpad_l').removeAttr("disabled");
	$('#i_nroAgentGlp_l').removeAttr("disabled");
	$('#i_costoUnitAgent_l').removeAttr("disabled");
	$('#i_costoTotalAgent_l').removeAttr("disabled");
	$('#i_despPersonal_l').removeAttr("disabled");
	$('#i_activExtraord_l').removeAttr("disabled");
	//
	$('#i_importeEmpad').removeAttr("disabled");
	$('#i_importeAgent').removeAttr("disabled");
	$('#i_importeDespPersonal').removeAttr("disabled");
	$('#i_importeActivExtraord').removeAttr("disabled");
	$('#i_totalGeneral').removeAttr("disabled");
}
function totalEmpadronamientoRural(){
	var nroEmpad;
	var costoUnit;
	var totalEmpad;
	nroEmpad = $('#i_nroEmpad_r').val();
	costoUnit = $('#i_costoUnitEmpad_r').val();
	totalEmpad = parseFloat(nroEmpad)*parseFloat(costoUnit);
	totalEmpad = redondeo(totalEmpad,2);
	$('#i_costoTotalEmpad_r').val(parseFloat(totalEmpad));
}
function totalEmpadronamientoProvincia(){
	var nroEmpad;
	var costoUnit;
	var totalEmpad;
	nroEmpad = $('#i_nroEmpad_p').val();
	costoUnit = $('#i_costoUnitEmpad_p').val();
	totalEmpad = parseFloat(nroEmpad)*parseFloat(costoUnit);
	totalEmpad = redondeo(totalEmpad,2);
	$('#i_costoTotalEmpad_p').val(parseFloat(totalEmpad));
}
function totalEmpadronamientoLima(){
	var nroEmpad;
	var costoUnit;
	var totalEmpad;
	nroEmpad = $('#i_nroEmpad_l').val();
	costoUnit = $('#i_costoUnitEmpad_l').val();
	totalEmpad = parseFloat(nroEmpad)*parseFloat(costoUnit);
	totalEmpad = redondeo(totalEmpad,2);
	$('#i_costoTotalEmpad_l').val(parseFloat(totalEmpad));
}
//
function totalRedAgentesRural(){
	var nroAgent;
	var costoUnit;
	var totalAgent;
	nroAgent = $('#i_nroAgentGlp_r').val();
	costoUnit = $('#i_costoUnitAgent_r').val();
	totalAgent = parseFloat(nroAgent)*parseFloat(costoUnit);
	totalAgent = redondeo(totalAgent,2);
	$('#i_costoTotalAgent_r').val(parseFloat(totalAgent));
}
function totalRedAgentesProvincia(){
	var nroAgent;
	var costoUnit;
	var totalAgent;
	nroAgent = $('#i_nroAgentGlp_p').val();
	costoUnit = $('#i_costoUnitAgent_p').val();
	totalAgent = parseFloat(nroAgent)*parseFloat(costoUnit);
	totalAgent = redondeo(totalAgent,2);
	$('#i_costoTotalAgent_p').val(parseFloat(totalAgent));
}
function totalRedAgentesLima(){
	var nroAgent;
	var costoUnit;
	var totalAgent;
	nroAgent = $('#i_nroAgentGlp_l').val();
	costoUnit = $('#i_costoUnitAgent_l').val();
	totalAgent = parseFloat(nroAgent)*parseFloat(costoUnit);
	totalAgent = redondeo(totalAgent,2);
	$('#i_costoTotalAgent_l').val(parseFloat(totalAgent));
}
//
function totalImportes(){
	var importEmpadR;
	var importAgentR;
	var importDespR;
	var importActivR;
	//
	var importEmpadP;
	var importAgentP;
	var importDespP;
	var importActivP;
	//
	var importEmpadL;
	var importAgentL;
	var importDespL;
	var importActivL;
	//
	var totalImportEmpad;
	var totalImportAgent;
	var totalImportDesp;
	var totalImportActiv;
	//
	importEmpadR = $('#i_costoTotalEmpad_r').val();
	importAgentR = $('#i_costoTotalAgent_r').val();
	importDespR = $('#i_despPersonal_r').val();
	importActivR = $('#i_activExtraord_r').val();
	//
	importEmpadP = $('#i_costoTotalEmpad_p').val();
	importAgentP = $('#i_costoTotalAgent_p').val();
	importDespP = $('#i_despPersonal_p').val();
	importActivP = $('#i_activExtraord_p').val();
	//
	importEmpadL = $('#i_costoTotalEmpad_l').val();
	importAgentL = $('#i_costoTotalAgent_l').val();
	importDespL = $('#i_despPersonal_l').val();
	importActivL = $('#i_activExtraord_l').val();
	//
	totalImportEmpad = parseFloat(importEmpadR)+parseFloat(importEmpadP)+parseFloat(importEmpadL);
	totalImportAgent = parseFloat(importAgentR)+parseFloat(importAgentP)+parseFloat(importAgentL);
	totalImportDesp = parseFloat(importDespR)+parseFloat(importDespP)+parseFloat(importDespL);
	totalImportActiv = parseFloat(importActivR)+parseFloat(importActivP)+parseFloat(importActivL);
	//
	totalImportEmpad = redondeo(totalImportEmpad,2);
	totalImportAgent = redondeo(totalImportAgent,2);
	totalImportDesp = redondeo(totalImportDesp,2);
	totalImportActiv = redondeo(totalImportActiv,2);
	
	$('#i_importeEmpad').val(parseFloat(totalImportEmpad));
	$('#i_importeAgent').val(parseFloat(totalImportAgent));
	$('#i_importeDespPersonal').val(parseFloat(totalImportDesp));
	$('#i_importeActivExtraord').val(parseFloat(totalImportActiv));
}
//
function totalGeneral(){
	var importEmpadR;
	var importAgentR;
	var importDespR;
	var importActivR;
	//
	var importEmpadP;
	var importAgentP;
	var importDespP;
	var importActivP;
	//
	var importEmpadL;
	var importAgentL;
	var importDespL;
	var importActivL;
	//
	var importEmpad;
	var importAgent;
	var importDesp;
	var importActiv;
	//
	var totalGeneral;
	//
	importEmpadR = $('#i_costoTotalEmpad_r').val();
	importAgentR = $('#i_costoTotalAgent_r').val();
	importDespR = $('#i_despPersonal_r').val();
	importActivR = $('#i_activExtraord_r').val();
	//
	importEmpadP = $('#i_costoTotalEmpad_p').val();
	importAgentP = $('#i_costoTotalAgent_p').val();
	importDespP = $('#i_despPersonal_p').val();
	importActivP = $('#i_activExtraord_p').val();
	//
	importEmpadL = $('#i_costoTotalEmpad_l').val();
	importAgentL = $('#i_costoTotalAgent_l').val();
	importDespL = $('#i_despPersonal_l').val();
	importActivL = $('#i_activExtraord_l').val();
	//
	importEmpad=parseFloat(importEmpadR)+parseFloat(importEmpadP)+parseFloat(importEmpadL);
	importAgent=parseFloat(importAgentR)+parseFloat(importAgentP)+parseFloat(importAgentL);
	importDesp=parseFloat(importDespR)+parseFloat(importDespP)+parseFloat(importDespL);
	importActiv=parseFloat(importActivR)+parseFloat(importActivP)+parseFloat(importActivL);
	//
	/*importEmpad = redondeo(importEmpad,2);
	importAgent = redondeo(importAgent,2);
	importDesp = redondeo(importDesp,2);
	importActiv = redondeo(importActiv,2);*/
	
	totalGeneral = parseFloat(importEmpad)+parseFloat(importAgent)+parseFloat(importDesp)+parseFloat(importActiv);
	totalGeneral = redondeo(totalGeneral,2);
	
	$('#i_totalGeneral').val(totalGeneral);
}
function calculoTotal(){
	totalEmpadronamientoRural();
	totalEmpadronamientoProvincia();
	totalEmpadronamientoLima();
	//
	totalRedAgentesRural();
	totalRedAgentesProvincia();
	totalRedAgentesLima();
	//
	totalImportes();
	totalGeneral();
	//completar nulos
	$('#i_nroEmpad_r').attr("onkeypress","return soloNumerosDecimales(event, 1, 'i_nroEmpad_r',7,0)");
	$('#i_nroEmpad_p').attr("onkeypress","return soloNumerosDecimales(event, 1, 'i_nroEmpad_p',7,0)");
	$('#i_nroEmpad_l').attr("onkeypress","return soloNumerosDecimales(event, 1, 'i_nroEmpad_l',7,0)");
	$('#i_nroAgentGlp_r').attr("onkeypress","return soloNumerosDecimales(event, 1, 'i_nroAgentGlp_r',7,0)");
	$('#i_nroAgentGlp_p').attr("onkeypress","return soloNumerosDecimales(event, 1, 'i_nroAgentGlp_p',7,0)");
	$('#i_nroAgentGlp_l').attr("onkeypress","return soloNumerosDecimales(event, 1, 'i_nroAgentGlp_l',7,0)");
	completarBlanco('i_nroEmpad_r');
	completarBlanco('i_nroEmpad_p');
	completarBlanco('i_nroEmpad_l');
	completarBlanco('i_nroAgentGlp_r');
	completarBlanco('i_nroAgentGlp_p');
	completarBlanco('i_nroAgentGlp_l');
	//completar decimales
	formularioCompletarDecimales();

}
function formularioCompletarDecimales(){
	completarDecimal('i_costoUnitEmpad_r',2);
	completarDecimal('i_costoUnitEmpad_p',2);
	completarDecimal('i_costoUnitEmpad_l',2);
	completarDecimal('i_costoTotalEmpad_r',2);
	completarDecimal('i_costoTotalEmpad_p',2);
	completarDecimal('i_costoTotalEmpad_l',2);
	completarDecimal('i_costoUnitAgent_r',2);
	completarDecimal('i_costoUnitAgent_p',2);
	completarDecimal('i_costoUnitAgent_l',2);
	completarDecimal('i_costoTotalAgent_r',2);
	completarDecimal('i_costoTotalAgent_p',2);
	completarDecimal('i_costoTotalAgent_l',2);
	completarDecimal('i_despPersonal_r',2);
	completarDecimal('i_despPersonal_p',2);
	completarDecimal('i_despPersonal_l',2);
	completarDecimal('i_activExtraord_r',2);
	completarDecimal('i_activExtraord_p',2);
	completarDecimal('i_activExtraord_l',2);
	//
	completarDecimal('i_importeEmpad',2);
	completarDecimal('i_importeAgent',2);
	completarDecimal('i_importeDespPersonal',2);
	completarDecimal('i_importeActivExtraord',2);
	
	completarDecimal('i_totalGeneral',2);
}

function <portlet:namespace/>regresar(){
	$("#Estado").val("");
	$("#etapaEdit").val("");
	$("#div_formato").hide();
	$("#div_home").show();		
	//
	removerDeshabilitados();
	//se visualizan los componentes escondidos
	$('#<portlet:namespace/>reportePdf').css('display','none');
	$('#<portlet:namespace/>reporteExcel').css('display','none');
	$('#<portlet:namespace/>guardarFormato').css('display','');
	$('#panelCargaArchivos').css('display','');
	$('#<portlet:namespace/>validacionFormato').css('display','');
	$('#<portlet:namespace/>envioDefinitivo').css('display','');
	<portlet:namespace/>buscar();
}
function validarBusqueda() {		
	  if($('#i_anio_d').val().length != '' ) {		  
		  var numstr = trim($('#i_anio_d').val());
		  if (isNaN(numstr) || numstr.length<4 || parseFloat(numstr)<1900){
			  alert('Ingrese un a�o desde v�lido');
			  document.getElementById('i_anio_d').focus();
			  return false;
		  }
	  }
	  if($('#i_anio_h').val().length != '' ) {		  
		  var numstr = trim($('#i_anio_h').val());
		  if (isNaN(numstr) || numstr.length<4 || parseFloat(numstr)<1900){
			  alert('Ingrese un a�o hasta v�lido');
			  document.getElementById('i_anio_h').focus();
			  return false;
		  }
	  }
	  if($('#i_anio_d').val().length != '' && $('#i_anio_h').val().length != '' ) {
		  if( parseFloat($('#i_anio_d').val()) > parseFloat($('#i_anio_h').val()) ){
				alert('El a�o desde no puede exceder al a�o hasta');
				return false;
		  }
	  }
	  
	  if($('#s_etapa').val().length == '' ) { 	    
		    alert('Seleccione una etapa');
		    document.getElementById('s_etapa').focus();
		    return false; 
	  }
	 
	  return true; 
	}
	
function validarFormulario() {		
	  if($('#s_empresa').val().length == '' ) { 	
	    alert('Seleccione una empresa'); 
	    document.getElementById('s_empresa').focus();
	    return false; 
	  }
	  if($('#s_periodoenvio_present').val().length == '' ) {		  
		    alert('Debe ingresar el periodo de presentacion');
		    document.getElementById('s_periodoenvio_present').focus();
		    return false; 
	  }
	  if( $('#flagPeriodoEjecucion').val()=='S' ){
		  if($('#i_anioejecuc').val().length == '' ) {		  
			    alert('Debe ingresar el a�o de ejecucion');
			    document.getElementById('i_anioejecuc').focus();
			    return false; 
		  }else{
			  var numstr = trim($('#i_anioejecuc').val());
			  if (isNaN(numstr) || numstr.length<4 || parseFloat(numstr)<1900){
				  alert('Ingrese un a�o de ejecucion v�lido');
				  return false;
			  }
		  }
		  if($('#s_mes_ejecuc').val().length == '' ) {		  
			    alert('Debe ingresar el mes de ejecucion');
			    document.getElementById('s_mes_ejecuc').focus();
			    return false; 
		  }
	  }
	  //valores de formulario
	  if($('#i_nroEmpad_r').val().length == '' ) {		  
		    alert('Debe ingresar el n�mero de empadronados para Rural');
		    document.getElementById('i_nroEmpad_r').focus();
		    return false; 
	  }
	  /*if($('#i_costoUnitEmpad_r').val().length == '' ) {		  
		    alert('Debe ingresar el costo unitario de empadronados para Rural');
		    document.getElementById('i_costoUnitEmpad_r').focus();
		    return false; 
	  }*/
	  if($('#i_nroAgentGlp_r').val().length == '' ) {		  
		    alert('Debe ingresar el numero de agentes para Rural');
		    document.getElementById('i_nroAgentGlp_r').focus();
		    return false; 
	  }
	  /*if($('#i_costoUnitAgent_r').val().length == '' ) {		  
		    alert('Debe ingresar el costo unitario de agentes para Rural');
		    document.getElementById('i_costoUnitAgent_r').focus();
		    return false; 
	  }*/
	  /*if($('#i_despPersonal_r').val().length == '' ) {		  
		    alert('Debe ingresar el monto de descripcion de personal para Rural');
		    document.getElementById('i_despPersonal_r').focus();
		    return false; 
	  }
	  if($('#i_activExtraord_r').val().length == '' ) {		  
		    alert('Debe ingresar el monto de actividades extraordinarias para Rural');
		    document.getElementById('i_activExtraord_r').focus();
		    return false; 
	  }*/
	  ////////////////////
	  if($('#i_nroEmpad_p').val().length == '' ) {		  
		    alert('Debe ingresar el n�mero de empadronados para Provincia');
		    document.getElementById('i_nroEmpad_p').focus();
		    return false; 
	  }
	  /*if($('#i_costoUnitEmpad_p').val().length == '' ) {		  
		    alert('Debe ingresar el costo unitario de empadronados para Provincia');
		    document.getElementById('i_costoUnitEmpad_p').focus();
		    return false; 
	  }*/
	  if($('#i_nroAgentGlp_p').val().length == '' ) {		  
		    alert('Debe ingresar el numero de agentes para Provincia');
		    document.getElementById('i_nroAgentGlp_p').focus();
		    return false; 
	  }
	  /*if($('#i_costoUnitAgent_p').val().length == '' ) {		  
		    alert('Debe ingresar el costo unitario de agentes para Provincia');
		    document.getElementById('i_costoUnitAgent_p').focus();
		    return false; 
	  }
	  if($('#i_despPersonal_p').val().length == '' ) {		  
		    alert('Debe ingresar el monto de descripcion de personal para Provincia');
		    document.getElementById('i_despPersonal_p').focus();
		    return false; 
	  }
	  if($('#i_activExtraord_p').val().length == '' ) {		  
		    alert('Debe ingresar el monto de actividades extraordinarias para Provincia');
		    document.getElementById('i_activExtraord_p').focus();
		    return false; 
	  }*/
	  ///////////
	  if($('#i_nroEmpad_l').val().length == '' ) {		  
		    alert('Debe ingresar el n�mero de empadronados para Lima');
		    document.getElementById('i_nroEmpad_l').focus();
		    return false; 
	  }
	  /*if($('#i_costoUnitEmpad_l').val().length == '' ) {		  
		    alert('Debe ingresar el costo unitario de empadronados para Lima');
		    document.getElementById('i_costoUnitEmpad_l').focus();
		    return false; 
	  }*/
	  if($('#i_nroAgentGlp_l').val().length == '' ) {		  
		    alert('Debe ingresar el numero de agentes para Lima');
		    document.getElementById('i_nroAgentGlp_l').focus();
		    return false; 
	  }
	  /*if($('#i_costoUnitAgent_l').val().length == '' ) {		  
		    alert('Debe ingresar el costo unitario de agentes para Lima');
		    document.getElementById('i_costoUnitAgent_l').focus();
		    return false; 
	  }
	  if($('#i_despPersonal_l').val().length == '' ) {		  
		    alert('Debe ingresar el monto de descripcion de personal para Lima');
		    document.getElementById('i_despPersonal_l').focus();
		    return false; 
	  }
	  if($('#i_activExtraord_l').val().length == '' ) {		  
		    alert('Debe ingresar el monto de actividades extraordinarias para Lima');
		    document.getElementById('i_activExtraord_l').focus();
		    return false; 
	  }*/
	  //
	  return true; 
	}
function validarArchivoCarga() {		
  if($('#s_empresa').val().length == '' ) { 	
    alert('Seleccione una empresa para proceder con la carga de archivo'); 
    document.getElementById('s_empresa').focus();
    return false; 
  }
  if($('#s_periodoenvio_present').val().length == '' ) {		  
	    alert('Debe ingresar el periodo de presentaci�n');
	    document.getElementById('s_periodoenvio_present').focus();
	    return false; 
  }

  return true; 
}
	
function initBlockUI(){
	$(document).ajaxStop($.unblockUI); 		
}
function initDialogs(){	
	$( "#dialog-message" ).dialog({
		modal: true,
		autoOpen: false,
		buttons: {
			Ok: function() {
				$( this ).dialog( "close" );
			}
		}
	});
	$( "#dialog-message-report" ).dialog({
		modal: true,
		autoOpen: false,
		buttons: {
			'Imprimir Pdf': function() {
				<portlet:namespace/>mostrarReporteEnvioDefinitivo();
				$( this ).dialog("close");
			},
			Ok: function() {
				$( this ).dialog("close");
			}
		}
	});
	$("#dialog-confirm").dialog({
		modal: true,
		height: 200,
		width: 400,			
		autoOpen: false,
		buttons: {
			"Si": function() {
				eliminarFormato(codEmpresa,ano_Presentacion,mes_Presentacion,ano_Ejecucion,mes_Ejecucion,codEtapa);
				$( this ).dialog( "close" );
			},
			"No": function() {				
				$( this ).dialog( "close" );
			}
		}
	});
	$("#dialog-confirm-envio").dialog({
		modal: true,
		height: 200,
		width: 400,			
		autoOpen: false,
		buttons: {
			"Si": function() {
				<portlet:namespace/>envioDefinitivo();
				$( this ).dialog( "close" );
			},
			"No": function() {				
				$( this ).dialog( "close" );
			}
		}
	});
	$( "#dialog-form-carga" ).dialog({
		autoOpen: false,
		height: 240,
		width: 400,
		modal: true,
		buttons: {
			"Aceptar": function() {
				//Agregarfuncion();
				//Limpiar_FormFuncion();
				$( this ).dialog( "close" );					  				
			},
			"Cerrar": function() {
					//Limpiar_FormFuncion();
					$( this ).dialog( "close" );
			}
		},
		close: function() {
			//Limpiar_FormFuncion();
		}
	});
	$( "#dialog-form-error" ).dialog({
		modal: true,
		width: 700,
		autoOpen: false,
		buttons: {
			Ok: function() {
				$( this ).dialog( "close" );
			}
		}
	});
	$( "#dialog-form-observacion" ).dialog({
		modal: true,
		width: 700,
		autoOpen: false,
		buttons: {
			Cerrar: function() {
				$( this ).dialog( "close" );
			}
		}
	});
}
///////////////
function <portlet:namespace/>buscar() {	
	if (validarBusqueda()) {	
		$.blockUI({ message: '<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Cargando </h3>' });
		jQuery.ajax({			
				url: '<portlet:resourceURL id="grid" />',
				type: 'post',
				dataType: 'json',
				data: {							
					<portlet:namespace />s_empresa_b: $('#s_empresa_b').val(),
					<portlet:namespace />i_anio_d: $('#i_anio_d').val(),
					<portlet:namespace />s_mes_d: $('#s_mes_d').val(),
					<portlet:namespace />i_anio_h: $('#i_anio_h').val(),
					<portlet:namespace />s_mes_h: $('#s_mes_h').val(),
					<portlet:namespace />s_etapa: $('#s_etapa').val()
					},					
				success: function(gridData) {					
					$('#grid_formato').clearGridData(true);
					$('#grid_formato').jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
					$("#grid_formato")[0].refreshIndex();
					initBlockUI();
				},error : function(){
					alert("Error de conexi�n.");
					initBlockUI();
				}
		});
	}
}
function buildGrids() {	
	jQuery("#grid_formato").jqGrid({
		datatype: "local",
       colNames: ['Empresa','A�o Pres.','Mes Pres.','A�o Ejec.','Mes Ejec.','Grupo Inf','Estado','Visualizar','Editar','Anular','','','',''],
       colModel: [
				{ name: 'descEmpresa', index: 'descEmpresa', width: 50},
               { name: 'anoPresentacion', index: 'anoPresentacion', width: 30 },   
               { name: 'descMesPresentacion', index: 'descMesPresentacion', width: 30},
               { name: 'anoEjecucion', index: 'anoPresentacion', width: 30 },   
               { name: 'descMesEjecucion', index: 'descMesEjecucion', width: 30},
               { name: 'grupoInfo', index: 'anoEjecucion', width: 50},
               { name: 'estado', index: 'estado', width: 50,align:'center'},
               { name: 'view', index: 'view', width: 20,align:'center' },
               { name: 'edit', index: 'edit', width: 20,align:'center' },
               { name: 'elim', index: 'elim', width: 20,align:'center' },
               { name: 'codEmpresa', index: 'codEmpresa', hidden: true},
               { name: 'mesPresentacion', index: 'mesPresentacion', hidden: true},
               { name: 'mesEjecucion', index: 'mesEjecucion', hidden: true},
               { name: 'etapa', index: 'etapa', hidden: true}
	   	    ],
	   	 multiselect: false,
			rowNum:10,
		   	rowList:[10,20,50],
			height: 200,
		   	autowidth: true,
			rownumbers: true,
			shrinkToFit:true,
			pager: '#pager_formato',
		    viewrecords: true,
		   	caption: "Formatos",
		    sortorder: "asc",	   	    	   	   
       gridComplete: function(){
      		var ids = jQuery("#grid_formato").jqGrid('getDataIDs');
      		for(var i=0;i < ids.length;i++){
      			var cl = ids[i];
      			var ret = jQuery("#grid_formato").jqGrid('getRowData',cl);           
      			view = "<a href='#'><img border='0' title='View' src='/net-theme/images/img-net/file.png'  align='center' onclick=\"verFormato('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoEjecucion+"','"+ret.mesEjecucion+"','"+ret.etapa+"');\" /></a> ";
      			edit = "<a href='#'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png'  align='center' onclick=\"editarFormato('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoEjecucion+"','"+ret.mesEjecucion+"','"+ret.etapa+"');\" /></a> ";
      			elem = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"confirmarEliminar('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoEjecucion+"','"+ret.mesEjecucion+"','"+ret.etapa+"');\" /></a> ";              			
      			jQuery("#grid_formato").jqGrid('setRowData',ids[i],{view:view});
      			jQuery("#grid_formato").jqGrid('setRowData',ids[i],{edit:edit});
      			jQuery("#grid_formato").jqGrid('setRowData',ids[i],{elim:elem});
      		}
      }
  	});
	jQuery("#grid_formato").jqGrid('navGrid','#pager_formato',{add:false,edit:false,del:false,search: false,refresh: false});	
	jQuery("#grid_formato").jqGrid('navButtonAdd','#pager_formato',{
	       caption:"Exportar a Excel",
	       buttonicon: "ui-icon-bookmark",
	       onClickButton : function () {
	           location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';
	       } 
	}); 
}
function confirmarEliminar(cod_empresa,anoPresentacion,mesPresentacion,anoEjecucion,mesEjecucion,etapa){	
	var addhtml='�Est� seguro que desea eliminar el registro seleccionado?';
	$("#dialog-confirm-content").html(addhtml);		 
	$("#dialog-confirm").dialog("open");
	codEmpresa=cod_empresa;
	ano_Presentacion=anoPresentacion;
	mes_Presentacion=mesPresentacion;
	ano_Ejecucion=anoEjecucion;
	mes_Ejecucion=mesEjecucion;
	codEtapa=etapa;
}
function eliminarFormato(codEmpresa,ano_Presentacion,mes_Presentacion,ano_Ejecucion,mes_Ejecucion,codEtapa){			
	$.blockUI({ message: '<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Eliminando </h3>' });
	jQuery.ajax({
		url: '<portlet:resourceURL id="crud" />',
		type: 'post',
		dataType: 'json',
		data: {
		   <portlet:namespace />tipo: "DELETE",
		   <portlet:namespace />codEmpresa: codEmpresa,
		   <portlet:namespace />anoPresentacion: ano_Presentacion,
		   <portlet:namespace />mesPresentacion: mes_Presentacion,
		   <portlet:namespace />anoEjecucion: ano_Ejecucion,
		   <portlet:namespace />mesEjecucion: mes_Ejecucion,
		   <portlet:namespace />etapa: codEtapa
			},
		success: function(data) {
			if (data.resultado == "OK"){
				var addhtml2='Registro eliminado con �xito';					
				$("#dialog-message-content").html(addhtml2);
				$("#dialog-message").dialog( "open" );					
				<portlet:namespace/>buscar();
				initBlockUI();
			}
			else{
				alert("Error al eliminar el registro");
				initBlockUI();
			}
		},error : function(){
			alert("Error de conexi�n.");
			initBlockUI();
		}
});
}
function <portlet:namespace/>crearFormato(){	
	inicializarFormulario();
	data_funcion = [];
	$('#Estado').val('SAVE');
	$("#etapaEdit").val("");
	$("#div_formato").show();
	$("#div_home").hide();
	$('#flagCarga').val('0');
	//
	if( $('#flagPeriodoEjecucion').val()=='S' ){
		 $("#i_anioejecuc").val($("#anioDesdeSes").val());
		 $("#s_mes_ejecuc").val(parseInt($("#mesDesdeSes").val()));
	}
	$('#<portlet:namespace/>validacionFormato').css('display','none');
	cargarPeriodoYCostos('','');
}
function mostrarUltimoFormato(){	
	$('#Estado').val('SAVE');
	$("#etapaEdit").val("");
	$("#div_formato").show();
	$("#div_home").hide();
	$('#flagCarga').val('0');
}
function verFormato(codEmpresa,anoPresentacion,mesPresentacion,anoEjecucion,mesEjecucion,etapa){	
	$.blockUI({ message: '<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Obteniendo Datos </h3>' });
	jQuery.ajax({
			url: '<portlet:resourceURL id="crud" />',
			type: 'post',
			dataType: 'json',
			data: {
			   <portlet:namespace />tipo: "GET",
			   <portlet:namespace />codEmpresa: codEmpresa,
			   <portlet:namespace />anoPresentacion: anoPresentacion,
			   <portlet:namespace />mesPresentacion: mesPresentacion,
			   <portlet:namespace />anoEjecucion: anoEjecucion,
			   <portlet:namespace />mesEjecucion: mesEjecucion,
			   <portlet:namespace />etapa: etapa
				},
			success: function(data) {				
				if (data.resultado == "OK"){
					$("#Estado").val("UPDATE");
					$("#etapaEdit").val(etapa);
					$("#div_formato").show();
					$("#div_home").hide();
					dwr.util.removeAllOptions("s_periodoenvio_present");
					dwr.util.addOptions("s_periodoenvio_present", data.periodoEnvio,"codigoItem","descripcionItem");
					FillEditformato(data.formato);
					deshabiliarControlerView();
					initBlockUI();
				}
				else{
					alert("Error al recuperar los datos del registro seleccionado");
					initBlockUI();
				}
			},error : function(){
				alert("Error de conexi�n.");
				initBlockUI();
			}
	});	
}
function editarFormato(codEmpresa,anoPresentacion,mesPresentacion,anoEjecucion,mesEjecucion,etapa){	
	$.blockUI({ message: '<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Obteniendo Datos </h3>' });
	jQuery.ajax({
			url: '<portlet:resourceURL id="crud" />',
			type: 'post',
			dataType: 'json',
			data: {
			   <portlet:namespace />tipo: "GET",
			   <portlet:namespace />codEmpresa: codEmpresa,
			   <portlet:namespace />anoPresentacion: anoPresentacion,
			   <portlet:namespace />mesPresentacion: mesPresentacion,
			   <portlet:namespace />anoEjecucion: anoEjecucion,
			   <portlet:namespace />mesEjecucion: mesEjecucion,
			   <portlet:namespace />etapa: etapa
				},
			success: function(data) {				
				if (data.resultado == "OK"){
					$("#Estado").val("UPDATE");
					$("#etapaEdit").val(etapa);
					//se deja el formulario activo
					$("#div_formato").show();
					$("#div_home").hide();
					dwr.util.removeAllOptions("s_periodoenvio_present");
					dwr.util.addOptions("s_periodoenvio_present", data.periodoEnvio,"codigoItem","descripcionItem");
					FillEditformato(data.formato);
					initBlockUI();
				}
				else{
					alert("Error al recuperar los datos del registro seleccionado");
					initBlockUI();
				}
			},error : function(){
				alert("Error de conexi�n.");
				initBlockUI();
			}
	});	
}
function FillEditformato(row){
	$('#s_empresa').val(row.codEmpresa);
	//seteamos el concatenado
	$('#s_periodoenvio_present').val(''+row.anoPresentacion+completarCerosIzq(row.mesPresentacion,2)+row.etapa);
	
	
	dwr.util.removeAllOptions("s_periodoenvio_present");
	var codigo=''+row.anoPresentacion+completarCerosIzq(row.mesPresentacion,2)+row.etapa;
	var descripcion=formato12A.mostrarDescripcionPeriodo(row.anoPresentacion, row.mesPresentacion, row.etapa);
	var dataPeriodo = [{codigoItem:codigo, descripcionItem:descripcion}];   
	dwr.util.addOptions("s_periodoenvio_present", dataPeriodo,"codigoItem","descripcionItem");
	
	$('#flagPeriodoEjecucion').val(row.flagPeriodoEjecucion);
	
	if( $('#flagPeriodoEjecucion').val()=='S' ){
		$('#i_anioejecuc').val(row.anoEjecucion).css('text-align','right');
		$('#s_mes_ejecuc').val(row.mesEjecucion);
		$('#i_anioejecuc').attr("disabled",true);
		$('#s_mes_ejecuc').attr("disabled",true);
	}
	$("#etapaEdit").val(row.etapa);
	$('#i_nroEmpad_r').val(row.nroEmpadR).css('text-align','right');
	$('#i_costoUnitEmpad_r').val(row.costoUnitEmpadR).css('text-align','right');
	$('#i_costoTotalEmpad_r').css('text-align','right');
	$('#i_nroAgentGlp_r').val(row.nroAgentR).css('text-align','right');
	$('#i_costoUnitAgent_r').val(row.costoUnitAgentR).css('text-align','right');
	$('#i_costoTotalAgent_r').css('text-align','right');
	$('#i_despPersonal_r').val(row.desplPersonalR).css('text-align','right');
	$('#i_activExtraord_r').val(row.activExtraordR).css('text-align','right');
	
	$('#i_nroEmpad_p').val(row.nroEmpadP).css('text-align','right');
	$('#i_costoUnitEmpad_p').val(row.costoUnitEmpadP).css('text-align','right');
	$('#i_costoTotalEmpad_p').css('text-align','right');
	$('#i_nroAgentGlp_p').val(row.nroAgentP).css('text-align','right');
	$('#i_costoUnitAgent_p').val(row.costoUnitAgentP).css('text-align','right');
	$('#i_costoTotalAgent_p').css('text-align','right');
	$('#i_despPersonal_p').val(row.desplPersonalP).css('text-align','right');
	$('#i_activExtraord_p').val(row.activExtraordP).css('text-align','right');
	
	$('#i_nroEmpad_l').val(row.nroEmpadL).css('text-align','right');
	$('#i_costoUnitEmpad_l').val(row.costoUnitEmpadL).css('text-align','right');
	$('#i_costoTotalEmpad_l').css('text-align','right');
	$('#i_nroAgentGlp_l').val(row.nroAgentL).css('text-align','right');
	$('#i_costoUnitAgent_l').val(row.costoUnitAgentL).css('text-align','right');
	$('#i_costoTotalAgent_l').css('text-align','right');
	$('#i_despPersonal_l').val(row.desplPersonalL).css('text-align','right');
	$('#i_activExtraord_l').val(row.activExtraordL).css('text-align','right');
	
	$('#i_importeEmpad').css('text-align','right');
	$('#i_importeAgent').css('text-align','right');
	$('#i_importeDespPersonal').css('text-align','right');
	$('#i_importeActivExtraord').css('text-align','right');
	
	$('#i_totalGeneral').css('text-align','right');
	//
	$('#s_empresa').attr("disabled",true);
	$('#s_periodoenvio_present').attr("disabled",true);
	//
	realizarCalculoCampos();
	deshabilitarCampos();
	//
	totalEmpadronamientoRural();
	totalEmpadronamientoProvincia();
	totalEmpadronamientoLima();
	//
	totalRedAgentesRural();
	totalRedAgentesProvincia();
	totalRedAgentesLima();
	//
	totalImportes();
	$('#i_totalGeneral').val(row.totalGeneral);
	
	soloNumerosEnteros();
	formularioCompletarDecimales();
	$('#flagCarga').val('1');
	mostrarPeriodoEjecucion();
}
function deshabiliarControlerView(){
	$('#i_nroEmpad_r').attr("disabled",true);
	$('#i_nroEmpad_p').attr("disabled",true);
	$('#i_nroEmpad_l').attr("disabled",true);
	
	$('#i_nroAgentGlp_r').attr("disabled",true);
	$('#i_nroAgentGlp_p').attr("disabled",true);
	$('#i_nroAgentGlp_l').attr("disabled",true);
	
	$('#i_despPersonal_r').attr("disabled",true);
	$('#i_despPersonal_p').attr("disabled",true);
	$('#i_despPersonal_l').attr("disabled",true);
	
	$('#i_activExtraord_r').attr("disabled",true);
	$('#i_activExtraord_p').attr("disabled",true);
	$('#i_activExtraord_l').attr("disabled",true);
	
	$('#<portlet:namespace/>reportePdf').css('display','');
	$('#<portlet:namespace/>reporteExcel').css('display','');
	$('#<portlet:namespace/>guardarFormato').css('display','none');
	$('#panelCargaArchivos').css('display','none');
	$('#<portlet:namespace/>validacionFormato').css('display','none');
	$('#<portlet:namespace/>envioDefinitivo').css('display','none');
	
}
//////CRUD
function <portlet:namespace/>guardarFormato(){
	if (validarFormulario()){
		$.blockUI({ message: '<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Guardando Datos </h3>' });
		 jQuery.ajax({
			url: '<portlet:resourceURL id="crud" />',
			type: 'post',
			dataType: 'json',
			data: {
				<portlet:namespace />tipo: $("#Estado").val(),
				<portlet:namespace />codEmpresa: $('#s_empresa').val(),
				<portlet:namespace />periodoEnvio: $('#s_periodoenvio_present').val(),
				<portlet:namespace />flagPeriodoEjecucion: $('#flagPeriodoEjecucion').val(),
				<portlet:namespace />anoEjecucion: $('#i_anioejecuc').val(),
				<portlet:namespace />mesEjecucion: $('#s_mes_ejecuc').val(),
				<portlet:namespace />etapa: $('#etapaEdit').val(),
				<portlet:namespace />nroEmpadR: $('#i_nroEmpad_r').val(),
				<portlet:namespace />costoUnitEmpadR: $('#i_costoUnitEmpad_r').val(),
				<portlet:namespace />nroAgentR: $('#i_nroAgentGlp_r').val(),
				<portlet:namespace />costoUnitAgentR: $('#i_costoUnitAgent_r').val(),
				<portlet:namespace />despPersonalR: $('#i_despPersonal_r').val(),
				<portlet:namespace />activExtraordR: $('#i_activExtraord_r').val(),
				//
				<portlet:namespace />nroEmpadP: $('#i_nroEmpad_p').val(),
				<portlet:namespace />costoUnitEmpadP: $('#i_costoUnitEmpad_p').val(),
				<portlet:namespace />nroAgentP: $('#i_nroAgentGlp_p').val(),
				<portlet:namespace />costoUnitAgentP: $('#i_costoUnitAgent_p').val(),
				<portlet:namespace />despPersonalP: $('#i_despPersonal_p').val(),
				<portlet:namespace />activExtraordP: $('#i_activExtraord_p').val(),
				//
				<portlet:namespace />nroEmpadL: $('#i_nroEmpad_l').val(),
				<portlet:namespace />costoUnitEmpadL: $('#i_costoUnitEmpad_l').val(),
				<portlet:namespace />nroAgentL: $('#i_nroAgentGlp_l').val(),
				<portlet:namespace />costoUnitAgentL: $('#i_costoUnitAgent_l').val(),
				<portlet:namespace />despPersonalL: $('#i_despPersonal_l').val(),
				<portlet:namespace />activExtraordL: $('#i_activExtraord_l').val()
				},
			success: function(data) {			
				if (data.resultado == "OK"){				
					var addhtml2='Datos guardados satisfactoriamente';
					$("#dialog-message-content").html(addhtml2);
					$("#dialog-message").dialog( "open" );
					//limpiar();		
					$('#flagCarga').val('1');//inicializamos el flag de carga cuando editamos el archivo antes de cargar archivos
					mostrarFormularioModificado();
					initBlockUI();
				}
				else if(data.resultado == "Error"){				
					var addhtml2='Se produjo un error al guardar los datos: '+data.mensaje;
					$("#dialog-message-content").html(addhtml2);
					$("#dialog-message").dialog( "open" );
					//<portlet:namespace/>filtrar();
					mostrarUltimoFormato();
					initBlockUI();
				}
			},error : function(){
				alert("Error de conexi�n.");
				initBlockUI();
			}
		});
	   	//se deja el formulario activo
		$("#div_formato").hide();
		$("#div_home").show();
	}
	//
	 
	 
}
function mostrarFormularioModificado(){
	var codEmpM = $("#s_empresa").val();
	 var anioPresM = $("#s_periodoenvio_present").val().substring(0,4);
	 var mesPresM = $("#s_periodoenvio_present").val().substring(4,6);
	 var anioEjeM;
	 var mesEjeM;
	 if( $('#flagPeriodoEjecucion').val()=='S' ){
		 anioEjeM = $("#i_anioejecuc").val();
		 mesEjeM = $("#s_mes_ejecuc").val();
	 }else{
		 anioEjeM = anioPresM;
		 mesEjeM = mesPresM;
	 }
	 var etapaM = "SOLICITUD";
	 //var etapaM = $("#s_periodoenvio_present").val().substring(6,$("#s_periodoenvio_present").val().length);
	 if( $('#flagCarga').val()=='0' ){
		 mostrarUltimoFormato();
	 }else{
		//alert(codEmpM+','+anioPresM+','+mesPresM+','+anioEjeM+','+mesEjeM+','+etapaM);
		 if(codEmpM != '' && anioPresM != '' && mesPresM != '' && anioEjeM != '' && mesEjeM != '' && etapaM != ''){
		 	 editarFormato(codEmpM, anioPresM, mesPresM, anioEjeM, mesEjeM, etapaM);
		 }
	 }
	 $('#<portlet:namespace/>validacionFormato').css('display','');
}
function cargarPeriodoYCostos(valCodEmpresa, valPeriodo){
	<portlet:namespace/>loadPeriodo(valPeriodo);
}

function <portlet:namespace/>loadPeriodo(valPeriodo) {
	jQuery.ajax({
			url: '<portlet:resourceURL id="request_data" />',
			type: 'post',
			dataType: 'json',
			data: {
				<portlet:namespace />s_empresa: $('#s_empresa').val(),
				<portlet:namespace />s_periodoenvio_present: $('#s_periodoenvio_present').val()
				},
			success: function(data) {		
				dwr.util.removeAllOptions("s_periodoenvio_present");
				dwr.util.addOptions("s_periodoenvio_present", data,"codigoItem","descripcionItem");
				if( valPeriodo.length!='' ){
					dwr.util.setValue("s_periodoenvio_present", valPeriodo);
				}
				<portlet:namespace/>loadCostosUnitarios();
			},error : function(){
				alert("Error de conexi�n.");
				initBlockUI();
			}
	});
	
}

function recargarPeriodoEjecucion(){
	var ano;
	var mes;
	if( $('#s_periodoenvio_present').val() != null ){
		ano = $('#s_periodoenvio_present').val().substring(0,4);
		mes = $('#s_periodoenvio_present').val().substring(4,6);
		if( $('#flagPeriodoEjecucion').val()=='S' ){
			$('#i_anioejecuc').val(ano);
			$('#s_mes_ejecuc').val(parseFloat(mes));
		}
	}
}
function <portlet:namespace/>loadCostosUnitarios() {
	jQuery.ajax({
			url: '<portlet:resourceURL id="request_data2" />',
			type: 'post',
			dataType: 'json',
			data: {
				<portlet:namespace />s_empresa: $('#s_empresa').val(),
				<portlet:namespace />s_periodoenvio_present: $('#s_periodoenvio_present').val()
				},
			success: function(data) {				
				dwr.util.setValue("i_costoUnitEmpad_r", data.costoEmpR);
				dwr.util.setValue("i_costoUnitAgent_r", data.costoAgentR);
				dwr.util.setValue("i_costoUnitEmpad_p", data.costoEmpP);
				dwr.util.setValue("i_costoUnitAgent_p", data.costoAgentP);
				dwr.util.setValue("i_costoUnitEmpad_l", data.costoEmpL);
				dwr.util.setValue("i_costoUnitAgent_l", data.costoAgentL);
				//
				dwr.util.setValue("flagPeriodoEjecucion", data.flagPeriodoEjecucion);
				recargarPeriodoEjecucion();
				mostrarPeriodoEjecucion();
				//---initBlockUI();
			},error : function(){
				alert("Error de conexi�n.");
				initBlockUI();
			}
	});	 
	
}
function mostrarPeriodoEjecucion(){
	if( $('#flagPeriodoEjecucion').val()=='S' ){
		$('#divPeriodoEjecucion').show();  
	}else{
		$('#divPeriodoEjecucion').hide();  
	}
}

//////CARGAR ARCHIVO EXCEL
function <portlet:namespace/>cargarFormatoExcel(){
	var frm = document.getElementById('form-formatofise12a');
	frm.submit();
}
function <portlet:namespace/>cargarFormatoTexto(){
	var frm = document.getElementById('form-formatofise12a');
	frm.submit();
}

function <portlet:namespace/>mostrarFormularioCargaExcel(){
	if (validarArchivoCarga()){
		if( $('#flagCarga').val()=='0' ){
			$('#flagCarga').val('2');
		}else if( $('#flagCarga').val()=='1' ){
			$('#flagCarga').val('3');
		}
	    $('#divOverlay').show();
	    $("#dialog-form-cargaExcel").show();
	    $("#dialog-form-cargaExcel").css({ 
	        'left': ($(window).width() / 2 - $("#dialog-form-cargaExcel").width() / 2) + 'px', 
	        'top': ($(window).height()  - $("#dialog-form-cargaExcel").height() ) + 'px'
	    });
	}
}
function regresarFormularioCargaExcel(){
	$('#flagCarga').val('');
	$("#dialog-form-cargaExcel").hide();
	$('#divOverlay').hide();   
}
function <portlet:namespace/>mostrarFormularioCargaTexto(){
	if (validarArchivoCarga()){
		if( $('#flagCarga').val()=='0' ){
			$('#flagCarga').val('4');
		}else if( $('#flagCarga').val()=='1' ){
			$('#flagCarga').val('5');
		}
	    $('#divOverlay').show();
	    $("#dialog-form-cargaTexto").show();
	    $("#dialog-form-cargaTexto").css({ 
	        'left': ($(window).width() / 2 - $("#dialog-form-cargaTexto").width() / 2) + 'px', 
	        'top': ($(window).height() - $("#dialog-form-cargaTexto").height() ) + 'px'
	    });
	}
}
function regresarFormularioCargaTexto(){
	$('#flagCarga').val('');
	$("#dialog-form-cargaTexto").hide();
	$('#divOverlay').hide();   
}
function <portlet:namespace/>mostrarReportePdf(){
	jQuery.ajax({
		url : '<portlet:resourceURL id="reporte" />',
		type : 'post',
		dataType : 'json',
		data : {
			<portlet:namespace />codEmpresa: $('#s_empresa').val(),
			<portlet:namespace />periodoEnvio: $('#s_periodoenvio_present').val(),
			<portlet:namespace />flagPeriodoEjecucion: $('#flagPeriodoEjecucion').val(),
			<portlet:namespace />anoEjecucion: $('#i_anioejecuc').val(),
			<portlet:namespace />mesEjecucion: $('#s_mes_ejecuc').val(),
			<portlet:namespace />etapa: $('#etapaEdit').val(),
			<portlet:namespace />nombreReporte: 'formato12A',
			<portlet:namespace />nombreArchivo: 'formato12A',
			<portlet:namespace />tipoArchivo: '0'//PDF
		},
		success : function(gridData) {
			verReporte();
		},error : function(){
			alert("Error de conexi�n.");
			initBlockUI();
		}
	});
}
function <portlet:namespace/>mostrarReporteExcel(){
	jQuery.ajax({
		url : '<portlet:resourceURL id="reporte" />',
		type : 'post',
		dataType : 'json',
		data : {
			<portlet:namespace />codEmpresa: $('#s_empresa').val(),
			<portlet:namespace />periodoEnvio: $('#s_periodoenvio_present').val(),
			<portlet:namespace />flagPeriodoEjecucion: $('#flagPeriodoEjecucion').val(),
			<portlet:namespace />anoEjecucion: $('#i_anioejecuc').val(),
			<portlet:namespace />mesEjecucion: $('#s_mes_ejecuc').val(),
			<portlet:namespace />etapa: $('#etapaEdit').val(),
			<portlet:namespace />nombreReporte: 'formato12A',
			<portlet:namespace />nombreArchivo: 'formato12A',
			<portlet:namespace />tipoArchivo: '1'//XLS
		},
		success : function(gridData) {
			//alert('entro');
			verReporte();
		},error : function(){
			alert("Error de conexi�n.");
			initBlockUI();
		}
	});
}
function verReporte(){
	window.open('<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ViewReport")%>','_newtab');
	<%-- location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ViewReport")%>'; --%>
}

//validar formato
function <portlet:namespace/>validacionFormato(){
	jQuery.ajax({
		url : '<portlet:resourceURL id="validacion" />',
		type : 'post',
		dataType : 'json',
		data : {
			<portlet:namespace />codEmpresa: $('#s_empresa').val(),
			<portlet:namespace />periodoEnvio: $('#s_periodoenvio_present').val(),
			<portlet:namespace />flagPeriodoEjecucion: $('#flagPeriodoEjecucion').val(),
			<portlet:namespace />anoEjecucion: $('#i_anioejecuc').val(),
			<portlet:namespace />mesEjecucion: $('#s_mes_ejecuc').val()
		},
		success : function(data) {

			if( data!=null ){
				$("#dialog-form-observacion").dialog( "open" );

				    $('#grid_observacion').clearGridData(true);
					$('#grid_observacion').jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
					$("#grid_observacion")[0].refreshIndex();

				 initBlockUI();
			}

		},error : function(){
			alert("Error de conexi�n.");
			initBlockUI();
		}
	});
}
function buidGridsObservacion(){
	 jQuery("#grid_observacion").jqGrid({
			datatype: "local",
			 colNames: ['Grupo Zona','C�digo','Descripci�n'],
		        colModel: [
						{ name: 'descZonaBenef', index: 'descZonaBenef', width: 150 ,align: 'left'},
						{ name: 'codigo', index: 'codigo', width: 50 ,align: 'center'},
		                { name: 'descripcion', index: 'descripcion', width: 420 ,align: 'left'}               
			   	    ],
		   	 multiselect: false,
				rowNum:10,
			   	rowList:[10,20,50],
				height: 'auto',
			   	autowidth: true,
			   	//width:'100%',
				rownumbers: true,
			    viewrecords: true,
			    pager:"pager_observacion",
			   	sortorder: "asc"//,
	   	});
	 jQuery("#grid_observacion").jqGrid('navGrid','#pager_observacion',{add:false,edit:false,del:false,search: false,refresh: false});	
		jQuery("#grid_observacion").jqGrid('navButtonAdd','#pager_observacion',{
		       caption:"Exportar a Excel",
		       buttonicon: "ui-icon-bookmark",
		       onClickButton : function () {
		           location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';
		       } 
		});
		jQuery("#grid_observacion").jqGrid('navButtonAdd','#pager_observacion',{
		       caption:"Exportar a Pdf",
		       buttonicon: "ui-icon-bookmark",
		       onClickButton : function () {
		    	   <portlet:namespace/>mostrarReporteValidacion();
		       } 
		});
}
function <portlet:namespace/>mostrarReporteValidacion(){
	jQuery.ajax({
		url : '<portlet:resourceURL id="reporteValidacion" />',
		type : 'post',
		dataType : 'json',
		data : {
			<portlet:namespace />nombreReporte: 'validacion',
			<portlet:namespace />nombreArchivo: 'validacion',
			<portlet:namespace />tipoArchivo: '0'//PDF
		},
		success : function(gridData) {
			verReporte();
		},error : function(){
			alert("Error de conexi�n.");
			initBlockUI();
		}
	});
}
function confirmarEnvioDefinitivo(){	
	var addhtml='�Est� seguro que desea realizar el env�o definitivo?';
	$("#dialog-confirm-envio-content").html(addhtml);		 
	$("#dialog-confirm-envio").dialog("open");
}
function <portlet:namespace/>envioDefinitivo(){
	jQuery.ajax({
		url : '<portlet:resourceURL id="envioDefinitivo" />',
		type : 'post',
		dataType : 'json',
		data : {
			<portlet:namespace />codEmpresa: $('#s_empresa').val(),
			<portlet:namespace />periodoEnvio: $('#s_periodoenvio_present').val(),
			<portlet:namespace />flagPeriodoEjecucion: $('#flagPeriodoEjecucion').val(),
			<portlet:namespace />anoEjecucion: $('#i_anioejecuc').val(),
			<portlet:namespace />mesEjecucion: $('#s_mes_ejecuc').val(),
			<portlet:namespace />nombreReporte: 'formato12A',
			<portlet:namespace />nombreArchivo: 'formato12A',
			<portlet:namespace />tipoArchivo: '0'//PDF
		},
		success : function(gridData) {
			var addhtml='Se realiz� el env�o satisfactoriamente';					
			$("#dialog-message-report-content").html(addhtml);
			$("#dialog-message-report").dialog( "open" );					
			initBlockUI();
		},error : function(){
			alert("Error de conexi�n.");
			initBlockUI();
		}
	});
}
function mostrarDescripcionPeriodo(anio,mes,etapa){
	  var monthNames = ["Ene","Feb","Mar","Abr","May","Jun","Jul","Ago","Sep","Oct","Nov","Dic"];
	  var descripcionPeriodo;
	  //alert(monthNames[mes-1]);
	  descripcionPeriodo=''+monthNames[mes-1]+'-'+anio+' / '+etapa;
	  //alert(descripcionPeriodo);
	  return descripcionPeriodo;
}
function <portlet:namespace/>mostrarReporteEnvioDefinitivo(){
	jQuery.ajax({
		url : '<portlet:resourceURL id="reporteEnvioDefinitivo" />',
		type : 'post',
		dataType : 'json',
		data : {
			<portlet:namespace />tipoArchivo: '2'//PDF+concatenado
		},
		success : function(gridData) {
			verReporte();
		},error : function(){
			alert("Error de conexi�n.");
			initBlockUI();
		}
	});
}
</script>

