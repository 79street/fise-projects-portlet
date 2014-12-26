<%@page import="com.liferay.portal.kernel.util.PrefsPropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects />

<script type="text/javascript">
var formato14B= {
	tablaResultados:null,
	tablaObservacion:null,
	paginadoResultados:null,
	paginadoObservacion:null,
	//
	urlBusqueda: null,
	urlCargaPeriodo:null,
	urlCargaFlagPeriodo:null,
	urlCrud:null,
	urlReporte:null,
	urlValidacion:null,
	urlReporteValidacion:null,
	urlEnvioDefinitivo:null,
	urlReporteEnvioDefinitivo:null,
	urlReporteActaEnvio:null,
	//
	mensajeCargando:null,
	mensajeObteniendoDatos:null,
	mensajeEliminando:null,
	mensajeGuardando:null,
	//valores hidden
	procesoEstado:null,
	etapaEdit:null,
	flagCarga:null,
	//valores en sesion, verificar si estas variables se guardan en el bean para no usarlas
	codEmpresaSes:null,
	anioPresSes:null,
	mesPresSes:null,
	anioIniVigSes:null,
	anioFinVigSes:null,
	etapaSes:null,
	//
	anioDesdeSes:null,
	mesDesdeSes:null,
	anioHastaSes:null,
	mesHastaSes:null,
	codEtapaSes:null,
	//
	mensajeError:null,
	mensajeInfo:null,
	flag:null,
	
	emailConfigured:null,
	
	//valores constantes para edelnor y luz del sur
	cod_empresa_edelnor:null,
	cod_empresa_luz_sur:null,
	
	//
	formCommand: null,
	botonBuscar:null,
	botonNuevo:null,
	botonRegresar:null,
	botonReportePdf:null,
	botonReporteExcel:null,
	botonGrabar:null,
	botonValidacion:null,
	botonEnvioDefinitivo:null,
	//
	botonActaEnvio:null,
	//cargarchivos
	botonCargaExcel:null,
	botonCargaTxt:null,
	botonCargarFormatoExcel:null,
	botonCargarFormatoTexto:null,
	//
	panelCargaArchivo:null,
	//
	i_codEmpresaB:null,
	i_anioDesde:null,
	i_mesDesde:null,
	i_anioHasta:null,
	i_mesHasta:null,
	i_etapaB:null,
	//dialogs
	dialogMessage:null,
	dialogMessageReport:null,
	dialogConfirm:null,
	dialogConfirmEnvio:null,
	dialogCarga:null,
	dialogError:null,
	dialogObservacion:null,
	dialogMessageContent:null,
	dialogMessageReportContent:null,
	dialogConfirmContent:null,
	dialogConfirmEnvioContent:null,
	dialogCargaExcel:null,
	dialogCargaTexto:null,
	//divs
	divHome:null,
	divFormato:null,
	divPeriodoEjecucion:null,
	divOverlay:null,
	divInformacion:null,
	//formulario
	f_empresa:null,
	f_periodoEnvio:null,
	f_flagPeriodo:null,
	//RURAL
	f_impValEdeR:null,f_impValNoEdeR:null,f_costoTotalImpR:null,f_nroValesImpR:null,f_costoUnitImpValesR:null,f_costoTotalValR:null,f_nroValesReptR:null,f_costoUnitReptValesR:null,
	f_costoTotalValOficR:null,f_nroValesEntrR:null,f_costoUnitEntrValesR:null,f_costoEnvPadronR:null,f_nroValesFisR:null,f_costoUnitLiqR:null,f_costoUnitDigitR:null,f_costoAtenSolicR:null,
	f_costoAtenConsR:null,f_costoTotalAtenR:null,f_nroTotalAtenR:null,f_costoUnitAtenR:null,f_costoPersonalR:null,f_capacAgentR:null,f_utilMatOficR:null,f_costoTotalGestR:null,
	//PROVINCIA
	f_impValEdeP:null,f_impValNoEdeP:null,f_costoTotalImpP:null,f_nroValesImpP:null,f_costoUnitImpValesP:null,f_costoTotalValP:null,f_nroValesReptP:null,f_costoUnitReptValesP:null,
	f_costoTotalValOficP:null,f_nroValesEntrP:null,f_costoUnitEntrValesP:null,f_costoEnvPadronP:null,f_nroValesFisP:null,f_costoUnitLiqP:null,f_costoUnitDigitP:null,f_costoAtenSolicP:null,
	f_costoAtenConsP:null,f_costoTotalAtenP:null,f_nroTotalAtenP:null,f_costoUnitAtenP:null,f_costoPersonalP:null,f_capacAgentP:null,f_utilMatOficP:null,f_costoTotalGestP:null,
	//LIMA
	f_impValEdeL:null,f_impValNoEdeL:null,f_costoTotalImpL:null,f_nroValesImpL:null,f_costoUnitImpValesL:null,f_costoTotalValL:null,f_nroValesReptL:null,f_costoUnitReptValesL:null,
	f_costoTotalValOficL:null,f_nroValesEntrL:null,f_costoUnitEntrValesL:null,f_costoEnvPadronL:null,f_nroValesFisL:null,f_costoUnitLiqL:null,f_costoUnitDigitL:null,f_costoAtenSolicL:null,
	f_costoAtenConsL:null,f_costoTotalAtenL:null,f_nroTotalAtenL:null,f_costoUnitAtenL:null,f_costoPersonalL:null,f_capacAgentL:null,f_utilMatOficL:null,f_costoTotalGestL:null,
	//
	init : function(){
		this.tablaResultados=$("#<portlet:namespace/>grid_formato");
		this.tablaObservacion=$("#<portlet:namespace/>grid_observacion");
		this.paginadoResultados='#<portlet:namespace/>pager_formato';
		this.paginadoObservacion='#<portlet:namespace/>pager_observacion';
		//
		this.urlBusqueda='<portlet:resourceURL id="busqueda" />';
		this.urlCargaPeriodo='<portlet:resourceURL id="cargaPeriodo" />';
		this.urlCargaFlagPeriodo='<portlet:resourceURL id="cargaFlagPeriodo" />';
		this.urlCrud='<portlet:resourceURL id="crud" />';
		this.urlReporte='<portlet:resourceURL id="reporte" />';
		this.urlValidacion='<portlet:resourceURL id="validacion" />';
		this.urlReporteValidacion='<portlet:resourceURL id="reporteValidacion" />';
		this.urlEnvioDefinitivo='<portlet:resourceURL id="envioDefinitivo" />';
		this.urlReporteEnvioDefinitivo='<portlet:resourceURL id="reporteEnvioDefinitivo" />';
		this.urlReporteActaEnvio='<portlet:resourceURL id="reporteActaEnvioView" />';
		//mensajes
		this.mensajeCargando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Cargando </h3>';
		this.mensajeObteniendoDatos='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Obteniendo Datos </h3>';
		this.mensajeEliminando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Eliminando </h3>';
		this.mensajeGuardando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Guardando Datos </h3>';
		//valores hidden utilitarios
		this.procesoEstado=$('#<portlet:namespace/>Estado');
		this.etapaEdit=$('#<portlet:namespace/>etapaEdit');
		this.flagCarga=$('#<portlet:namespace/>flagCarga');
		this.codEmpresaSes=$('#<portlet:namespace/>codEmpresaSes');
		this.anioPresSes=$('#<portlet:namespace/>anioPresSes');
		this.mesPresSes=$('#<portlet:namespace/>mesPresSes');
		this.anioIniVigSes=$('#<portlet:namespace/>anioIniVigSes');
		this.anioFinVigSes=$('#<portlet:namespace/>anioFinVigSes');
		this.etapaSes=$('#<portlet:namespace/>etapaSes');
		this.anioDesdeSes=$('#<portlet:namespace/>anioDesdeSes');
		this.mesDesdeSes=$('#<portlet:namespace/>mesDesdeSes');
		this.anioHastaSes=$('#<portlet:namespace/>anioHastaSes');
		this.mesHastaSes=$('#<portlet:namespace/>mesHastaSes');
		this.codEtapaSes=$('#<portlet:namespace/>codEtapaSes');
		this.mensajeError=$('#<portlet:namespace/>mensajeError');
		this.mensajeInfo=$('#<portlet:namespace/>mensajeInfo');
		this.flag=$('#<portlet:namespace/>flag');//solo para controlar los errores al subir archivos excel o de texto
		//
		this.emailConfigured='<%=PrefsPropsUtil.getString(PropsKeys.MAIL_SESSION_MAIL_SMTP_USER)%>';
		
		//valores constantes para edelnor y luz del sur
		this.cod_empresa_edelnor=$("#codEdelnor");
		this.cod_empresa_luz_sur=$("#codLuzSur");
		
		this.formCommand=$('#formato14BCBean');
		//
		this.i_codEmpresaB=$('#codEmpresaB');
		this.i_anioDesde=$('#anioDesde');
		this.i_mesDesde=$('#mesDesde');
		this.i_anioHasta=$('#anioHasta');
		this.i_mesHasta=$('#mesHasta');
		this.i_etapaB=$('#etapaB');
		//carga de dialogs id
		this.dialogMessage=$("#<portlet:namespace/>dialog-message");
		this.dialogConfirm=$("#<portlet:namespace/>dialog-confirm");
		this.dialogConfirmEnvio=$("#<portlet:namespace/>dialog-confirm-envio");
		this.dialogCarga=$("#<portlet:namespace/>dialog-form-carga");
		this.dialogError=$("#<portlet:namespace/>dialog-form-error");
		this.dialogObservacion=$("#<portlet:namespace/>dialog-form-observacion");
		this.dialogMessageContent=$("#<portlet:namespace/>dialog-message-content");
		this.dialogConfirmContent=$("#<portlet:namespace/>dialog-confirm-content");
		this.dialogConfirmEnvioContent=$("#<portlet:namespace/>dialog-confirm-envio-content");
		this.dialogCargaExcel=$("#<portlet:namespace/>dialog-form-cargaExcel");
		this.dialogCargaTexto=$("#<portlet:namespace/>dialog-form-cargaTexto");
		//
		this.dialogMessageReport=$("#<portlet:namespace/>dialog-message-report");
		this.dialogMessageReportContent=$("#<portlet:namespace/>dialog-message-report-content");
		//divs
		this.divHome=$("#<portlet:namespace/>div_home");
		this.divFormato=$("#<portlet:namespace/>div_formato");
		this.divPeriodoEjecucion=$("#<portlet:namespace/>divPeriodoEjecucion");
		this.divOverlay=$("#<portlet:namespace/>divOverlay");
		this.divInformacion=$("#<portlet:namespace/>divInformacion");
		//formulario
		this.f_empresa=$('#codigoEmpresa');
		this.f_periodoEnvio=$('#periodoEnvio');
		this.f_flagPeriodo=$("#flagPeriodoEjecucion");
		//
		this.f_impValEdeR=$('#impValDesctoEdeR');
		this.f_impValNoEdeR=$('#impValDesctoNoEdeR');
		this.f_costoTotalImpR=$('#costoTotalImpR');
		this.f_nroValesImpR=$('#nroValesImpR');
		this.f_costoUnitImpValesR=$('#costoUnitImpValesR');
		this.f_costoTotalValR=$('#costoTotalValDesctoR');
		this.f_nroValesReptR=$('#nroValesReptR');
		this.f_costoUnitReptValesR=$('#costoUnitReptValesR');
		this.f_costoTotalValOficR=$('#costoTotalValOficR');
		this.f_nroValesEntrR=$('#nroValesEntrR');
		this.f_costoUnitEntrValesR=$('#costoUnitEntrValesR');
		this.f_costoEnvPadronR=$('#costoEnvPadronR');
		this.f_nroValesFisR=$('#nroValesFisR');
		this.f_costoUnitLiqR=$('#costoUnitLiqR');
		this.f_costoUnitDigitR=$('#costoUnitValesDigitR');
		this.f_costoAtenSolicR=$('#costoAtenSolicR');
		this.f_costoAtenConsR=$('#costoAtenConsR');
		this.f_costoTotalAtenR=$('#costoTotalAtenR');
		this.f_nroTotalAtenR=$('#nroTotalAtenR');
		this.f_costoUnitAtenR=$('#costoUnitAtenR');
		this.f_costoPersonalR=$('#costoPersonalR');
		this.f_capacAgentR=$('#capacAgentR');
		this.f_utilMatOficR=$('#utilMatOficR');
		this.f_costoTotalGestR=$('#costoTotalGestR');
		//
		this.f_impValEdeP=$('#impValDesctoEdeP');
		this.f_impValNoEdeP=$('#impValDesctoNoEdeP');
		this.f_costoTotalImpP=$('#costoTotalImpP');
		this.f_nroValesImpP=$('#nroValesImpP');
		this.f_costoUnitImpValesP=$('#costoUnitImpValesP');
		this.f_costoTotalValP=$('#costoTotalValDesctoP');
		this.f_nroValesReptP=$('#nroValesReptP');
		this.f_costoUnitReptValesP=$('#costoUnitReptValesP');
		this.f_costoTotalValOficP=$('#costoTotalValOficP');
		this.f_nroValesEntrP=$('#nroValesEntrP');
		this.f_costoUnitEntrValesP=$('#costoUnitEntrValesP');
		this.f_costoEnvPadronP=$('#costoEnvPadronP');
		this.f_nroValesFisP=$('#nroValesFisP');
		this.f_costoUnitLiqP=$('#costoUnitLiqP');
		this.f_costoUnitDigitP=$('#costoUnitValesDigitP');
		this.f_costoAtenSolicP=$('#costoAtenSolicP');
		this.f_costoAtenConsP=$('#costoAtenConsP');
		this.f_costoTotalAtenP=$('#costoTotalAtenP');
		this.f_nroTotalAtenP=$('#nroTotalAtenP');
		this.f_costoUnitAtenP=$('#costoUnitAtenP');
		this.f_costoPersonalP=$('#costoPersonalP');
		this.f_capacAgentP=$('#capacAgentP');
		this.f_utilMatOficP=$('#utilMatOficP');
		this.f_costoTotalGestP=$('#costoTotalGestP');
		//
		this.f_impValEdeL=$('#impValDesctoEdeL');
		this.f_impValNoEdeL=$('#impValDesctoNoEdeL');
		this.f_costoTotalImpL=$('#costoTotalImpL');
		this.f_nroValesImpL=$('#nroValesImpL');
		this.f_costoUnitImpValesL=$('#costoUnitImpValesL');
		this.f_costoTotalValL=$('#costoTotalValDesctoL');
		this.f_nroValesReptL=$('#nroValesReptL');
		this.f_costoUnitReptValesL=$('#costoUnitReptValesL');
		this.f_costoTotalValOficL=$('#costoTotalValOficL');
		this.f_nroValesEntrL=$('#nroValesEntrL');
		this.f_costoUnitEntrValesL=$('#costoUnitEntrValesL');
		this.f_costoEnvPadronL=$('#costoEnvPadronL');
		this.f_nroValesFisL=$('#nroValesFisL');
		this.f_costoUnitLiqL=$('#costoUnitLiqL');
		this.f_costoUnitDigitL=$('#costoUnitValesDigitL');
		this.f_costoAtenSolicL=$('#costoAtenSolicL');
		this.f_costoAtenConsL=$('#costoAtenConsL');
		this.f_costoTotalAtenL=$('#costoTotalAtenL');
		this.f_nroTotalAtenL=$('#nroTotalAtenL');
		this.f_costoUnitAtenL=$('#costoUnitAtenL');
		this.f_costoPersonalL=$('#costoPersonalL');
		this.f_capacAgentL=$('#capacAgentL');
		this.f_utilMatOficL=$('#utilMatOficL');
		this.f_costoTotalGestL=$('#costoTotalGestL');
		//
		this.buildGrids();
		this.buildGridsObservacion();
		this.botonNuevo=$("#<portlet:namespace/>crearFormato");
		this.botonBuscar=$("#<portlet:namespace/>buscarFormato");	
		this.botonRegresar=$("#<portlet:namespace/>regresarFormato");
		this.botonReportePdf=$("#<portlet:namespace/>reportePdf");
		this.botonReporteExcel=$("#<portlet:namespace/>reporteExcel");
		this.botonGrabar=$("#<portlet:namespace/>guardarFormato");
		this.botonValidacion=$("#<portlet:namespace/>validacionFormato");
		this.botonEnvioDefinitivo=$("#<portlet:namespace/>envioDefinitivo");
		//
		this.botonActaEnvio=$("#<portlet:namespace/>reporteActaEnvio");
		//cargaarchivos
		this.botonCargaExcel=$("#<portlet:namespace/>cargaExcel");
		this.botonCargaTxt=$("#<portlet:namespace/>cargaTxt");
		this.botonCargarFormatoExcel=$("#<portlet:namespace/>cargarFormatoExcel");
		this.botonCargarFormatoTexto=$("#<portlet:namespace/>cargarFormatoTexto");
		//
		this.panelCargaArchivo=$("#<portlet:namespace/>panelCargaArchivos");
		//eventos
		formato14B.botonNuevo.click(function() {formato14B.<portlet:namespace/>crearFormato();});
		formato14B.botonBuscar.click(function() {formato14B.buscar();});
		formato14B.botonGrabar.click(function() {formato14B.<portlet:namespace/>guardarFormato();});
		formato14B.botonRegresar.click(function() {formato14B.<portlet:namespace/>regresar();});
		formato14B.f_empresa.change(function(){formato14B.<portlet:namespace/>loadPeriodo('');});
		formato14B.f_periodoEnvio.change(function(){formato14B.<portlet:namespace/>loadPeriodo(this.value);});
		//cargaarchivos
		formato14B.botonCargaExcel.click(function() {formato14B.<portlet:namespace/>mostrarFormularioCargaExcel();});
		formato14B.botonCargaTxt.click(function() {formato14B.<portlet:namespace/>mostrarFormularioCargaTexto();});
		formato14B.botonCargarFormatoExcel.click(function() {formato14B.<portlet:namespace/>cargarFormatoExcel();});
		formato14B.botonCargarFormatoTexto.click(function() {formato14B.<portlet:namespace/>cargarFormatoTexto();});
		//
		formato14B.botonReportePdf.click(function() {formato14B.<portlet:namespace/>mostrarReportePdf();});
		formato14B.botonReporteExcel.click(function() {formato14B.<portlet:namespace/>mostrarReporteExcel();});
		formato14B.botonValidacion.click(function() {formato14B.<portlet:namespace/>validacionFormato();});
		formato14B.botonEnvioDefinitivo.click(function() {formato14B.confirmarEnvioDefinitivo();});
		//
		formato14B.botonActaEnvio.click(function() {formato14B.<portlet:namespace/>mostrarReporteActaEnvio();});
		
		formato14B.initDialogs();
		formato14B.cargaInicial();
		//eventos por defecto
		formato14B.botonBuscar.trigger('click');
		formato14B.initBlockUI();
		//$('input[type=text].target').on('change', function(){
		$('input.target[type=text]').on('change', function(){
			formato14B.calculoTotal();
		});
	},
	cargaInicial : function(){
		//SE CARGA VARIABLES EN SESION PARA MOSTRAR LOS PANELES DE NUEVO O EDICION MANEJADOS
		 var codEmpSes = formato14B.codEmpresaSes.val();
		 var anioPresSes = formato14B.anioPresSes.val();
		 var mesPresSes = formato14B.mesPresSes.val();
		 var anioIniVigSes = formato14B.anioIniVigSes.val();
		 var anioFinVigSes = formato14B.anioFinVigSes.val();
		 var etapaSes = formato14B.etapaSes.val();
		 var flagOpera = 'ABIERTO';
		 //
		 
		 var flag = formato14B.flag.val();
		 //alert('hola mundo '+flag);
		 if( flag=='N' ){//solo ocurre cuando hay un error en la carga de formularios, sino se muestra el proceso normal
			 formato14B.inicializarFormulario();
			 formato14B.mostrarUltimoFormato();
			 formato14B.f_empresa.val(codEmpSes);
			 if( formato14B.f_periodoEnvio.val()=='S' ){
				 $("#anioInicioVigencia").val(anioIniVigSes);
				 $("#anioFinVigencia").val(anioFinVigSes);
			}else{
				$("#anioInicioVigencia").val(anioPresSes);
				 $("#anioFinVigencia").val(anioPresSes);
			}
			 formato14B.botonValidacion.css('display','none');
			 formato14B.<portlet:namespace/>loadPeriodo(anioPresSes+completarCerosIzq(mesPresSes,2)+etapaSes);
		}else{
			//alert(codEmpSes+','+anioPresSes+','+mesPresSes+','+anioIniVigSes+','+anioFinVigSes+','+etapaSes);
			 if(codEmpSes != '' && anioPresSes != '' && mesPresSes != '' && anioIniVigSes != '' && anioFinVigSes != '' && etapaSes != ''){
				 formato14B.editarFormato(codEmpSes, anioPresSes, mesPresSes, anioIniVigSes, anioFinVigSes, etapaSes,flagOpera);
			 }
		 }
		 var mensajeInfo = formato14B.mensajeInfo.val();
		 var mensajeError = formato14B.mensajeError.val();
		 //SE MUESTRAN LOS MENSAJES DE ERROR PARA LA CARGA DE LOS ARCHIVOS
		 if(mensajeError!=''){
			 //se muestra el panel de errores si se produce en la carga de archivos
			formato14B.dialogError.dialog("open");
		}else{
			//Se muestra el mensaje de informacion exitosa
			 if(mensajeInfo!=''){
				 formato14B.dialogMessageContent.html(mensajeInfo);
				 formato14B.dialogMessage.dialog("open");			
			 }
		 }
		 //limpiar variables
		 formato14B.codEmpresaSes.val('');
		 formato14B.anioPresSes.val('');
		 formato14B.mesPresSes.val('');
		 formato14B.anioIniVigSes.val('');
		 formato14B.anioFinVigSes.val('');
		 formato14B.etapaSes.val('');
	},
	buildGrids : function () {	
		formato14B.tablaResultados.jqGrid({
		   datatype: "local",
	       colNames: ['Empresa','Año Pres.','Mes Pres.','Año Ini. Vig.','Año Fin Vig.','Grupo de Información','Estado','Visualizar','Editar','Anular','','','',''],
	       colModel: [
					{ name: 'descEmpresa', index: 'descEmpresa', width: 50},
	               { name: 'anoPresentacion', index: 'anoPresentacion', width: 30 },   
	               { name: 'descMesPresentacion', index: 'descMesPresentacion', width: 30},
	               { name: 'anoIniVigencia', index: 'anoIniVigencia', width: 30 },   
	               { name: 'anoFinVigencia', index: 'anoFinVigencia', width: 30},
	               { name: 'grupoInfo', index: 'grupoInfo', width: 50},
	               { name: 'estado', index: 'estado', width: 50,align:'center'},
	               { name: 'view', index: 'view', width: 20,align:'center' },
	               { name: 'edit', index: 'edit', width: 20,align:'center' },
	               { name: 'elim', index: 'elim', width: 20,align:'center' },
	               { name: 'codEmpresa', index: 'codEmpresa', hidden: true},
	               { name: 'mesPresentacion', index: 'mesPresentacion', hidden: true},
	               { name: 'etapa', index: 'etapa', hidden: true},
	               { name: 'flagOperacion', index: 'flagOperacion', hidden: true}
		   	    ],
		   	 multiselect: false,
				rowNum:10,
			   	rowList:[10,20,50],
				height: 200,
			   	autowidth: true,
				rownumbers: true,
				shrinkToFit:true,
				pager: formato14B.paginadoResultados,
			    viewrecords: true,
			   	caption: "Formatos",
			    sortorder: "asc",	   	    	   	   
	       gridComplete: function(){
	      		var ids = formato14B.tablaResultados.jqGrid('getDataIDs');
	      		for(var i=0;i < ids.length;i++){
	      			var cl = ids[i];
	      			var ret = formato14B.tablaResultados.jqGrid('getRowData',cl);           
	      			view = "<a href='#'><img border='0' title='View' src='/net-theme/images/img-net/file.png'  align='center' onclick=\"formato14B.verFormato('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoIniVigencia+"','"+ret.anoFinVigencia+"','"+ret.etapa+"');\" /></a> ";
	      			edit = "<a href='#'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png'  align='center' onclick=\"formato14B.editarFormato('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoIniVigencia+"','"+ret.anoFinVigencia+"','"+ret.etapa+"','"+ret.flagOperacion+"');\" /></a> ";
	      			elem = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"formato14B.confirmarEliminar('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoIniVigencia+"','"+ret.anoFinVigencia+"','"+ret.etapa+"','"+ret.flagOperacion+"');\" /></a> ";              			
	      			formato14B.tablaResultados.jqGrid('setRowData',ids[i],{view:view});
	      			formato14B.tablaResultados.jqGrid('setRowData',ids[i],{edit:edit});
	      			formato14B.tablaResultados.jqGrid('setRowData',ids[i],{elim:elem});
	      		}
	      }
	  	});
		formato14B.tablaResultados.jqGrid('navGrid',formato14B.paginadoResultados,{add:false,edit:false,del:false,search: false,refresh: false});	
		formato14B.tablaResultados.jqGrid('navButtonAdd',formato14B.paginadoResultados,{
		       caption:"Exportar a Excel",
		       buttonicon: "ui-icon-bookmark",
		       onClickButton : function () {
		           location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';
		       } 
		}); 
	},
	buscar : function () {
		if(formato14B.validarBusqueda()){
			formato14B.blockUI();
			jQuery.ajax({			
					url: formato14B.urlBusqueda+'&'+formato14B.formCommand.serialize(),
					type: 'post',
					dataType: 'json',				
					success: function(gridData) {					
						formato14B.tablaResultados.clearGridData(true);
						formato14B.tablaResultados.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
						formato14B.tablaResultados[0].refreshIndex();
						formato14B.initBlockUI();
					},error : function(){
						alert("Error de conexión.");
						formato14B.initBlockUI();
					}
			});
		}
	},
	validarBusqueda : function(){
		if(formato14B.i_anioDesde.val().length != '' ) {		  
			  var numstr = trim(formato14B.i_anioDesde.val());
			  if (isNaN(numstr) || numstr.length<4 || parseFloat(numstr)<1900){
				  alert('Ingrese un año desde válido');
				  formato14B.i_anioDesde.focus();
				  return false;
			  }
		  }
		  if(formato14B.i_anioHasta.val().length != '' ) {		  
			  var numstr = trim(formato14B.i_anioHasta.val());
			  if (isNaN(numstr) || numstr.length<4 || parseFloat(numstr)<1900){
				  alert('Ingrese un año hasta válido');
				  formato14B.i_anioHasta.focus();
				  return false;
			  }
		  }
		  if(formato14B.i_anioDesde.val().length != '' && formato14B.i_anioHasta.val().length != '' ) {
			  if( parseFloat(formato14B.i_anioDesde.val()) > parseFloat(formato14B.i_anioHasta.val()) ){
					alert('El año desde no puede exceder al año hasta');
					return false;
			  }
		  }
		  if(formato14B.i_etapaB.val().length == '' ) { 	    
			    alert('Seleccione una etapa');
			    formato14B.i_etapaB.focus();
			    return false; 
		  }
		 return true; 
	},
	<portlet:namespace/>crearFormato :  function(){	
		formato14B.inicializarFormulario();
		formato14B.procesoEstado.val('SAVE');
		formato14B.etapaEdit.val("");
		formato14B.divFormato.show();
		formato14B.divHome.hide();
		formato14B.flagCarga.val('0');
		//
		/*if( formato14B.f_flagPeriodo.val()=='S' ){
			//poner valores guardadose en sesion
			$("#anioInicioVigencia").val(formato14B.i_anioDesde.val());
			$("#anioFinVigencia").val(formato14B.i_anioDesde.val());
		}*/
		formato14B.botonValidacion.css('display','none');
		formato14B.<portlet:namespace/>loadPeriodo('');
		
		formato14B.estiloEdicionRural();
		formato14B.estiloEdicionProvincia();
		
	},
	<portlet:namespace/>regresar : function(){
		formato14B.procesoEstado.val('');
		formato14B.etapaEdit.val("");
		formato14B.divFormato.hide();
		formato14B.divHome.show();		
		//
		formato14B.removerDeshabilitados();
		//se visualizan los componentes escondidos
		formato14B.botonReportePdf.css('display','none');
		formato14B.botonReporteExcel.css('display','none');
		//
		formato14B.botonActaEnvio.css('display','none');
		//
		formato14B.botonGrabar.css('display','');
		formato14B.botonValidacion.css('display','');
		formato14B.botonEnvioDefinitivo.css('display','');
		
		formato14B.panelCargaArchivo.css('display','');

		//limpiamos los anios de vigencia
		$('#anioInicioVigencia').val('0');
		$('#anioFinVigencia').val('0');
		
		formato14B.botonBuscar.trigger('click');
	},
	inicializarFormulario : function(){
		
		formato14B.divInformacion.hide();
		
		formato14B.f_empresa.val('');
		/*if( formato14B.f_flagPeriodo.val()=='S' ){
			$('#anioInicioVigencia').val('');
			$('#anioFinVigencia').val('');
			$('#anioInicioVigencia').attr("disabled",false);
			$('#anioFinVigencia').attr("disabled",false);
		}*/
		
		//validar lima edelnor y luz del sur
		if(formato14B.cod_empresa_edelnor.val()==formato14B.f_empresa.val() || formato14B.cod_empresa_luz_sur.val()==formato14B.f_empresa.val()){
			formato14B.habilitarLima();										
		}else{
			formato14B.deshabilitarLima();
		}
		
		//RURAL
		formato14B.f_impValEdeR.val('0.00');
		formato14B.f_impValNoEdeR.val('0.00');
		formato14B.f_costoTotalImpR.val('0.00');
		formato14B.f_nroValesImpR.val('0');
		formato14B.f_costoUnitImpValesR.val('0.00');
		formato14B.f_costoTotalValR.val('0.00');
		formato14B.f_nroValesReptR.val('0');
		formato14B.f_costoUnitReptValesR.val('0.00');
		formato14B.f_costoTotalValOficR.val('0.00');
		formato14B.f_nroValesEntrR.val('0');
		formato14B.f_costoUnitEntrValesR.val('0.00');
		formato14B.f_costoEnvPadronR.val('0.00');
		formato14B.f_nroValesFisR.val('0');
		formato14B.f_costoUnitLiqR.val('0.00');
		formato14B.f_costoUnitDigitR.val('0.00');
		formato14B.f_costoAtenSolicR.val('0.00');
		formato14B.f_costoAtenConsR.val('0.00');
		formato14B.f_costoTotalAtenR.val('0.00');
		formato14B.f_nroTotalAtenR.val('0');
		formato14B.f_costoUnitAtenR.val('0.00');
		formato14B.f_costoPersonalR.val('0.00');
		formato14B.f_capacAgentR.val('0.00');
		formato14B.f_utilMatOficR.val('0.00');
		formato14B.f_costoTotalGestR.val('0.00');
		//PROVINCIA
		formato14B.f_impValEdeP.val('0.00');
		formato14B.f_impValNoEdeP.val('0.00');
		formato14B.f_costoTotalImpP.val('0.00');
		formato14B.f_nroValesImpP.val('0');
		formato14B.f_costoUnitImpValesP.val('0.00');
		formato14B.f_costoTotalValP.val('0.00');
		formato14B.f_nroValesReptP.val('0');
		formato14B.f_costoUnitReptValesP.val('0.00');
		formato14B.f_costoTotalValOficP.val('0.00');
		formato14B.f_nroValesEntrP.val('0');
		formato14B.f_costoUnitEntrValesP.val('0.00');
		formato14B.f_costoEnvPadronP.val('0.00');
		formato14B.f_nroValesFisP.val('0');
		formato14B.f_costoUnitLiqP.val('0.00');
		formato14B.f_costoUnitDigitP.val('0.00');
		formato14B.f_costoAtenSolicP.val('0.00');
		formato14B.f_costoAtenConsP.val('0.00');
		formato14B.f_costoTotalAtenP.val('0.00');
		formato14B.f_nroTotalAtenP.val('0');
		formato14B.f_costoUnitAtenP.val('0.00');
		formato14B.f_costoPersonalP.val('0.00');
		formato14B.f_capacAgentP.val('0.00');
		formato14B.f_utilMatOficP.val('0.00');
		formato14B.f_costoTotalGestP.val('0.00');
		//LIMA
		formato14B.f_impValEdeL.val('0.00');
		formato14B.f_impValNoEdeL.val('0.00');
		formato14B.f_costoTotalImpL.val('0.00');
		formato14B.f_nroValesImpL.val('0');
		formato14B.f_costoUnitImpValesL.val('0.00');
		formato14B.f_costoTotalValL.val('0.00');
		formato14B.f_nroValesReptL.val('0');
		formato14B.f_costoUnitReptValesL.val('0.00');
		formato14B.f_costoTotalValOficL.val('0.00');
		formato14B.f_nroValesEntrL.val('0');
		formato14B.f_costoUnitEntrValesL.val('0.00');
		formato14B.f_costoEnvPadronL.val('0.00');
		formato14B.f_nroValesFisL.val('0');
		formato14B.f_costoUnitLiqL.val('0.00');
		formato14B.f_costoUnitDigitL.val('0.00');
		formato14B.f_costoAtenSolicL.val('0.00');
		formato14B.f_costoAtenConsL.val('0.00');
		formato14B.f_costoTotalAtenL.val('0.00');
		formato14B.f_nroTotalAtenL.val('0');
		formato14B.f_costoUnitAtenL.val('0.00');
		formato14B.f_costoPersonalL.val('0.00');
		formato14B.f_capacAgentL.val('0.00');
		formato14B.f_utilMatOficL.val('0.00');
		formato14B.f_costoTotalGestL.val('0.00');
		
		//quitamos los componentes deshabilitados
		formato14B.f_empresa.attr("disabled",false);
		formato14B.f_periodoEnvio.attr("disabled",false);
		//
		//formato14B.deshabilitarCampos();
		//
		formato14B.soloNumerosEnteros();
		formato14B.soloNumerosDecimales();
	},
	removerDeshabilitados : function(){
		formato14B.f_empresa.removeAttr("disabled");
		formato14B.f_periodoEnvio.removeAttr("disabled");
		//if( formato14B.f_flagPeriodo.val()=='S' ){
		$('#anioInicioVigencia').removeAttr("disabled");
		$('#anioFinVigencia').removeAttr("disabled");
		//}
		//RURAL
		formato14B.f_impValEdeR.removeAttr("disabled");
		formato14B.f_impValNoEdeR.removeAttr("disabled");
		formato14B.f_nroValesImpR.removeAttr("disabled");
		formato14B.f_costoTotalValR.removeAttr("disabled");
		formato14B.f_nroValesReptR.removeAttr("disabled");
		formato14B.f_costoTotalValOficR.removeAttr("disabled");
		formato14B.f_nroValesEntrR.removeAttr("disabled");
		formato14B.f_costoEnvPadronR.removeAttr("disabled");
		formato14B.f_nroValesFisR.removeAttr("disabled");
		formato14B.f_costoUnitDigitR.removeAttr("disabled");
		formato14B.f_costoAtenSolicR.removeAttr("disabled");
		formato14B.f_costoAtenConsR.removeAttr("disabled");
		formato14B.f_nroTotalAtenR.removeAttr("disabled");
		formato14B.f_costoPersonalR.removeAttr("disabled");
		formato14B.f_capacAgentR.removeAttr("disabled");
		formato14B.f_utilMatOficR.removeAttr("disabled");
		//PROVINCIA
		formato14B.f_impValEdeP.removeAttr("disabled");
		formato14B.f_impValNoEdeP.removeAttr("disabled");
		formato14B.f_nroValesImpP.removeAttr("disabled");
		formato14B.f_costoTotalValP.removeAttr("disabled");
		formato14B.f_nroValesReptP.removeAttr("disabled");
		formato14B.f_costoTotalValOficP.removeAttr("disabled");
		formato14B.f_nroValesEntrP.removeAttr("disabled");
		formato14B.f_costoEnvPadronP.removeAttr("disabled");
		formato14B.f_nroValesFisP.removeAttr("disabled");
		formato14B.f_costoUnitDigitP.removeAttr("disabled");
		formato14B.f_costoAtenSolicP.removeAttr("disabled");
		formato14B.f_costoAtenConsP.removeAttr("disabled");
		formato14B.f_nroTotalAtenP.removeAttr("disabled");
		formato14B.f_costoPersonalP.removeAttr("disabled");
		formato14B.f_capacAgentP.removeAttr("disabled");
		formato14B.f_utilMatOficP.removeAttr("disabled");
		//LIMA
		formato14B.f_impValEdeL.removeAttr("disabled");
		formato14B.f_impValNoEdeL.removeAttr("disabled");
		formato14B.f_nroValesImpL.removeAttr("disabled");
		formato14B.f_costoTotalValL.removeAttr("disabled");
		formato14B.f_nroValesReptL.removeAttr("disabled");
		formato14B.f_costoTotalValOficL.removeAttr("disabled");
		formato14B.f_nroValesEntrL.removeAttr("disabled");
		formato14B.f_costoEnvPadronL.removeAttr("disabled");
		formato14B.f_nroValesFisL.removeAttr("disabled");
		formato14B.f_costoUnitDigitL.removeAttr("disabled");
		formato14B.f_costoAtenSolicL.removeAttr("disabled");
		formato14B.f_costoAtenConsL.removeAttr("disabled");
		formato14B.f_nroTotalAtenL.removeAttr("disabled");
		formato14B.f_costoPersonalL.removeAttr("disabled");
		formato14B.f_capacAgentL.removeAttr("disabled");
		formato14B.f_utilMatOficL.removeAttr("disabled");
	},
	deshabilitarCamposView : function(){
		$('#anioInicioVigencia').attr("disabled",true);
		$('#anioFinVigencia').attr("disabled",true);
		//RURAL
		formato14B.f_impValEdeR.attr("disabled",true);
		formato14B.f_impValNoEdeR.attr("disabled",true);
		formato14B.f_nroValesImpR.attr("disabled",true);
		formato14B.f_costoTotalValR.attr("disabled",true);
		formato14B.f_nroValesReptR.attr("disabled",true);
		formato14B.f_costoTotalValOficR.attr("disabled",true);
		formato14B.f_nroValesEntrR.attr("disabled",true);
		formato14B.f_costoEnvPadronR.attr("disabled",true);
		formato14B.f_nroValesFisR.attr("disabled",true);
		formato14B.f_costoUnitDigitR.attr("disabled",true);
		formato14B.f_costoAtenSolicR.attr("disabled",true);
		formato14B.f_costoAtenConsR.attr("disabled",true);
		formato14B.f_nroTotalAtenR.attr("disabled",true);
		formato14B.f_costoPersonalR.attr("disabled",true);
		formato14B.f_capacAgentR.attr("disabled",true);
		formato14B.f_utilMatOficR.attr("disabled",true);
		//PROVINCIA
		formato14B.f_impValEdeP.attr("disabled",true);
		formato14B.f_impValNoEdeP.attr("disabled",true);
		formato14B.f_nroValesImpP.attr("disabled",true);
		formato14B.f_costoTotalValP.attr("disabled",true);
		formato14B.f_nroValesReptP.attr("disabled",true);
		formato14B.f_costoTotalValOficP.attr("disabled",true);
		formato14B.f_nroValesEntrP.attr("disabled",true);
		formato14B.f_costoEnvPadronP.attr("disabled",true);
		formato14B.f_nroValesFisP.attr("disabled",true);
		formato14B.f_costoUnitDigitP.attr("disabled",true);
		formato14B.f_costoAtenSolicP.attr("disabled",true);
		formato14B.f_costoAtenConsP.attr("disabled",true);
		formato14B.f_nroTotalAtenP.attr("disabled",true);
		formato14B.f_costoPersonalP.attr("disabled",true);
		formato14B.f_capacAgentP.attr("disabled",true);
		formato14B.f_utilMatOficP.attr("disabled",true);
		//LIMA
		formato14B.f_impValEdeL.attr("disabled",true);
		formato14B.f_impValNoEdeL.attr("disabled",true);
		formato14B.f_nroValesImpL.attr("disabled",true);
		formato14B.f_costoTotalValL.attr("disabled",true);
		formato14B.f_nroValesReptL.attr("disabled",true);
		formato14B.f_costoTotalValOficL.attr("disabled",true);
		formato14B.f_nroValesEntrL.attr("disabled",true);
		formato14B.f_costoEnvPadronL.attr("disabled",true);
		formato14B.f_nroValesFisL.attr("disabled",true);
		formato14B.f_costoUnitDigitL.attr("disabled",true);
		formato14B.f_costoAtenSolicL.attr("disabled",true);
		formato14B.f_costoAtenConsL.attr("disabled",true);
		formato14B.f_nroTotalAtenL.attr("disabled",true);
		formato14B.f_costoPersonalL.attr("disabled",true);
		formato14B.f_capacAgentL.attr("disabled",true);
		formato14B.f_utilMatOficL.attr("disabled",true);
		//
		formato14B.botonReportePdf.css('display','');
		formato14B.botonReporteExcel.css('display','');
		//
		formato14B.botonActaEnvio.css('display','');
		//
		formato14B.botonGrabar.css('display','none');
		formato14B.botonValidacion.css('display','none');
		formato14B.botonEnvioDefinitivo.css('display','none');
		//
		formato14B.panelCargaArchivo.css('display','none');
		
		formato14B.quitarEstiloEdicionCabecera();
		formato14B.quitarEstiloEdicionRural();
		formato14B.quitarEstiloEdicionProvincia();
		formato14B.quitarEstiloEdicionLima();
	},
	//CALCULO RURAL
	totalImpresionRural : function(){
		var impresionValesEde;
		var impresionValesNoEde;
		var totalImpresion;
		impresionValesEde = formato14B.f_impValEdeR.val();
		impresionValesNoEde = formato14B.f_impValNoEdeR.val();
		//
		totalImpresion = parseFloat(impresionValesEde)+parseFloat(impresionValesNoEde);
		totalImpresion = redondeo(totalImpresion, 2);
		//alert(totalEmpadronamiento);
		formato14B.f_costoTotalImpR.val(parseFloat(totalImpresion));
	},
	costoUnitarioImpresionRural : function(){
		var totalImpresion;
		var nroValesImpresion;
		var costoUnitImpresion;
		totalImpresion = formato14B.f_costoTotalImpR.val();
		nroValesImpresion = formato14B.f_nroValesImpR.val();
		//
		if( nroValesImpresion!=null && nroValesImpresion!=0 ){
			costoUnitImpresion=parseFloat(totalImpresion)/parseFloat(nroValesImpresion);
			costoUnitImpresion=redondeo(costoUnitImpresion, 2);
		}else{
			costoUnitImpresion='0.00';
		}
		formato14B.f_costoUnitImpValesR.val(parseFloat(costoUnitImpresion));
	},
	costoUnitarioRepartoValesRural : function(){
		var totalRepartoVales;
		var nroValesRepartidos;
		var costoUnitRepartoVales;
		totalRepartoVales = formato14B.f_costoTotalValR.val();
		nroValesRepartidos = formato14B.f_nroValesReptR.val();
		//
		if( nroValesRepartidos!=null && nroValesRepartidos!=0 ){
			costoUnitRepartoVales=parseFloat(totalRepartoVales)/parseFloat(nroValesRepartidos);
			costoUnitRepartoVales=redondeo(costoUnitRepartoVales, 2);
		}else{
			costoUnitRepartoVales='0.00';
		}
		formato14B.f_costoUnitReptValesR.val(parseFloat(costoUnitRepartoVales));
	},
	costoUnitarioEntregaValesRural : function(){
		var totalEntregaVales;
		var nroValesEntregados;
		var costoUnitEntregaVales;
		totalEntregaVales = formato14B.f_costoTotalValOficR.val();
		nroValesEntregados = formato14B.f_nroValesEntrR.val();
		//
		if( nroValesEntregados!=null && nroValesEntregados!=0 ){
			costoUnitEntregaVales=parseFloat(totalEntregaVales)/parseFloat(nroValesEntregados);
			costoUnitEntregaVales=redondeo(costoUnitEntregaVales, 2);
		}else{
			costoUnitEntregaVales='0.00';
		}
		formato14B.f_costoUnitEntrValesR.val(parseFloat(costoUnitEntregaVales));
	},
	costoUnitarioLiquidValesRural : function(){
		var totalEnvioPadronVales;
		var nroValesFisicos;
		var costoUnitLiquidVales;
		totalEnvioPadronVales = formato14B.f_costoEnvPadronR.val();
		nroValesFisicos = formato14B.f_nroValesFisR.val();
		//
		if( nroValesFisicos!=null && nroValesFisicos!=0 ){
			costoUnitLiquidVales=parseFloat(totalEnvioPadronVales)/parseFloat(nroValesFisicos);
			costoUnitLiquidVales=redondeo(costoUnitLiquidVales, 2);
		}else{
			costoUnitLiquidVales='0.00';
		}
		formato14B.f_costoUnitLiqR.val(parseFloat(costoUnitLiquidVales));
	},
	totalAtencionRural : function(){
		var atencionSolic;
		var atencionConsultas;
		var totalAtenciones;
		atencionSolic = formato14B.f_costoAtenSolicR.val();
		atencionConsultas = formato14B.f_costoAtenConsR.val();
		//
		totalAtenciones = parseFloat(atencionSolic)+parseFloat(atencionConsultas);
		totalAtenciones = redondeo(totalAtenciones, 2);
		//alert(totalEmpadronamiento);
		formato14B.f_costoTotalAtenR.val(parseFloat(totalAtenciones));
	},
	costoUnitarioAtencionRural : function(){
		var totalAtencion;
		var nroAtencion;
		var costoUnitAtencion;
		totalAtencion = formato14B.f_costoTotalAtenR.val();
		nroAtencion = formato14B.f_nroTotalAtenR.val();
		//
		if( nroAtencion!=null && nroAtencion!=0 ){
			costoUnitAtencion=parseFloat(totalAtencion)/parseFloat(nroAtencion);
			costoUnitAtencion=redondeo(costoUnitAtencion, 2);
		}else{
			costoUnitAtencion='0.00';
		}
		formato14B.f_costoUnitAtenR.val(parseFloat(costoUnitAtencion));
	},
	totalGestionAdministrativaRural : function(){
		var costoPersonal;
		var capacitacionAgentes;
		var utilesOficina;
		var totalGestionAdministrativa;
		costoPersonal = formato14B.f_costoPersonalR.val();
		capacitacionAgentes = formato14B.f_capacAgentR.val();
		utilesOficina = formato14B.f_utilMatOficR.val();
		//
		totalGestionAdministrativa = parseFloat(costoPersonal)+parseFloat(capacitacionAgentes)+parseFloat(utilesOficina);
		totalGestionAdministrativa = redondeo(totalGestionAdministrativa, 2);
		//alert(totalEmpadronamiento);
		formato14B.f_costoTotalGestR.val(parseFloat(totalGestionAdministrativa));
	},
	//CALCULO PROVINCIA
	totalImpresionProvincia : function(){
		var impresionValesEde;
		var impresionValesNoEde;
		var totalImpresion;
		impresionValesEde = formato14B.f_impValEdeP.val();
		impresionValesNoEde = formato14B.f_impValNoEdeP.val();
		//
		totalImpresion = parseFloat(impresionValesEde)+parseFloat(impresionValesNoEde);
		totalImpresion = redondeo(totalImpresion, 2);
		//alert(totalEmpadronamiento);
		formato14B.f_costoTotalImpP.val(parseFloat(totalImpresion));
	},
	costoUnitarioImpresionProvincia : function(){
		var totalImpresion;
		var nroValesImpresion;
		var costoUnitImpresion;
		totalImpresion = formato14B.f_costoTotalImpP.val();
		nroValesImpresion = formato14B.f_nroValesImpP.val();
		//
		if( nroValesImpresion!=null && nroValesImpresion!=0 ){
			costoUnitImpresion=parseFloat(totalImpresion)/parseFloat(nroValesImpresion);
			costoUnitImpresion=redondeo(costoUnitImpresion, 2);
		}else{
			costoUnitImpresion='0.00';
		}
		formato14B.f_costoUnitImpValesP.val(parseFloat(costoUnitImpresion));
	},
	costoUnitarioRepartoValesProvincia : function(){
		var totalRepartoVales;
		var nroValesRepartidos;
		var costoUnitRepartoVales;
		totalRepartoVales = formato14B.f_costoTotalValP.val();
		nroValesRepartidos = formato14B.f_nroValesReptP.val();
		//
		if( nroValesRepartidos!=null && nroValesRepartidos!=0 ){
			costoUnitRepartoVales=parseFloat(totalRepartoVales)/parseFloat(nroValesRepartidos);
			costoUnitRepartoVales=redondeo(costoUnitRepartoVales, 2);
		}else{
			costoUnitRepartoVales='0.00';
		}
		formato14B.f_costoUnitReptValesP.val(parseFloat(costoUnitRepartoVales));
	},
	costoUnitarioEntregaValesProvincia : function(){
		var totalEntregaVales;
		var nroValesEntregados;
		var costoUnitEntregaVales;
		totalEntregaVales = formato14B.f_costoTotalValOficP.val();
		nroValesEntregados = formato14B.f_nroValesEntrP.val();
		//
		if( nroValesEntregados!=null && nroValesEntregados!=0 ){
			costoUnitEntregaVales=parseFloat(totalEntregaVales)/parseFloat(nroValesEntregados);
			costoUnitEntregaVales=redondeo(costoUnitEntregaVales, 2);
		}else{
			costoUnitEntregaVales='0.00';
		}
		formato14B.f_costoUnitEntrValesP.val(parseFloat(costoUnitEntregaVales));
	},
	costoUnitarioLiquidValesProvincia : function(){
		var totalEnvioPadronVales;
		var nroValesFisicos;
		var costoUnitLiquidVales;
		totalEnvioPadronVales = formato14B.f_costoEnvPadronP.val();
		nroValesFisicos = formato14B.f_nroValesFisP.val();
		//
		if( nroValesFisicos!=null && nroValesFisicos!=0 ){
			costoUnitLiquidVales=parseFloat(totalEnvioPadronVales)/parseFloat(nroValesFisicos);
			costoUnitLiquidVales=redondeo(costoUnitLiquidVales, 2);
		}else{
			costoUnitLiquidVales='0.00';
		}
		formato14B.f_costoUnitLiqP.val(parseFloat(costoUnitLiquidVales));
	},
	totalAtencionProvincia : function(){
		var atencionSolic;
		var atencionConsultas;
		var totalAtenciones;
		atencionSolic = formato14B.f_costoAtenSolicP.val();
		atencionConsultas = formato14B.f_costoAtenConsP.val();
		//
		totalAtenciones = parseFloat(atencionSolic)+parseFloat(atencionConsultas);
		totalAtenciones = redondeo(totalAtenciones, 2);
		//alert(totalEmpadronamiento);
		formato14B.f_costoTotalAtenP.val(parseFloat(totalAtenciones));
	},
	costoUnitarioAtencionProvincia : function(){
		var totalAtencion;
		var nroAtencion;
		var costoUnitAtencion;
		totalAtencion = formato14B.f_costoTotalAtenP.val();
		nroAtencion = formato14B.f_nroTotalAtenP.val();
		//
		if( nroAtencion!=null && nroAtencion!=0 ){
			costoUnitAtencion=parseFloat(totalAtencion)/parseFloat(nroAtencion);
			costoUnitAtencion=redondeo(costoUnitAtencion, 2);
		}else{
			costoUnitAtencion='0.00';
		}
		formato14B.f_costoUnitAtenP.val(parseFloat(costoUnitAtencion));
	},
	totalGestionAdministrativaProvincia : function(){
		var costoPersonal;
		var capacitacionAgentes;
		var utilesOficina;
		var totalGestionAdministrativa;
		costoPersonal = formato14B.f_costoPersonalP.val();
		capacitacionAgentes = formato14B.f_capacAgentP.val();
		utilesOficina = formato14B.f_utilMatOficP.val();
		//
		totalGestionAdministrativa = parseFloat(costoPersonal)+parseFloat(capacitacionAgentes)+parseFloat(utilesOficina);
		totalGestionAdministrativa = redondeo(totalGestionAdministrativa, 2);
		//alert(totalEmpadronamiento);
		formato14B.f_costoTotalGestP.val(parseFloat(totalGestionAdministrativa));
	},
	//CALCULO LIMA
	totalImpresionLima : function(){
		var impresionValesEde;
		var impresionValesNoEde;
		var totalImpresion;
		impresionValesEde = formato14B.f_impValEdeL.val();
		impresionValesNoEde = formato14B.f_impValNoEdeL.val();
		//
		totalImpresion = parseFloat(impresionValesEde)+parseFloat(impresionValesNoEde);
		totalImpresion = redondeo(totalImpresion, 2);
		//alert(totalEmpadronamiento);
		formato14B.f_costoTotalImpL.val(parseFloat(totalImpresion));
	},
	costoUnitarioImpresionLima : function(){
		var totalImpresion;
		var nroValesImpresion;
		var costoUnitImpresion;
		totalImpresion = formato14B.f_costoTotalImpL.val();
		nroValesImpresion = formato14B.f_nroValesImpL.val();
		//
		if( nroValesImpresion!=null && nroValesImpresion!=0 ){
			costoUnitImpresion=parseFloat(totalImpresion)/parseFloat(nroValesImpresion);
			costoUnitImpresion=redondeo(costoUnitImpresion, 2);
		}else{
			costoUnitImpresion='0.00';
		}
		formato14B.f_costoUnitImpValesL.val(parseFloat(costoUnitImpresion));
	},
	costoUnitarioRepartoValesLima : function(){
		var totalRepartoVales;
		var nroValesRepartidos;
		var costoUnitRepartoVales;
		totalRepartoVales = formato14B.f_costoTotalValL.val();
		nroValesRepartidos = formato14B.f_nroValesReptL.val();
		//
		if( nroValesRepartidos!=null && nroValesRepartidos!=0 ){
			costoUnitRepartoVales=parseFloat(totalRepartoVales)/parseFloat(nroValesRepartidos);
			costoUnitRepartoVales=redondeo(costoUnitRepartoVales, 2);
		}else{
			costoUnitRepartoVales='0.00';
		}
		formato14B.f_costoUnitReptValesL.val(parseFloat(costoUnitRepartoVales));
	},
	costoUnitarioEntregaValesLima : function(){
		var totalEntregaVales;
		var nroValesEntregados;
		var costoUnitEntregaVales;
		totalEntregaVales = formato14B.f_costoTotalValOficL.val();
		nroValesEntregados = formato14B.f_nroValesEntrL.val();
		//
		if( nroValesEntregados!=null && nroValesEntregados!=0 ){
			costoUnitEntregaVales=parseFloat(totalEntregaVales)/parseFloat(nroValesEntregados);
			costoUnitEntregaVales=redondeo(costoUnitEntregaVales, 2);
		}else{
			costoUnitEntregaVales='0.00';
		}
		formato14B.f_costoUnitEntrValesL.val(parseFloat(costoUnitEntregaVales));
	},
	costoUnitarioLiquidValesLima : function(){
		var totalEnvioPadronVales;
		var nroValesFisicos;
		var costoUnitLiquidVales;
		totalEnvioPadronVales = formato14B.f_costoEnvPadronL.val();
		nroValesFisicos = formato14B.f_nroValesFisL.val();
		//
		if( nroValesFisicos!=null && nroValesFisicos!=0 ){
			costoUnitLiquidVales=parseFloat(totalEnvioPadronVales)/parseFloat(nroValesFisicos);
			costoUnitLiquidVales=redondeo(costoUnitLiquidVales, 2);
		}else{
			costoUnitLiquidVales='0.00';
		}
		formato14B.f_costoUnitLiqL.val(parseFloat(costoUnitLiquidVales));
	},
	totalAtencionLima : function(){
		var atencionSolic;
		var atencionConsultas;
		var totalAtenciones;
		atencionSolic = formato14B.f_costoAtenSolicL.val();
		atencionConsultas = formato14B.f_costoAtenConsL.val();
		//
		totalAtenciones = parseFloat(atencionSolic)+parseFloat(atencionConsultas);
		totalAtenciones = redondeo(totalAtenciones, 2);
		//alert(totalEmpadronamiento);
		formato14B.f_costoTotalAtenL.val(parseFloat(totalAtenciones));
	},
	costoUnitarioAtencionLima : function(){
		var totalAtencion;
		var nroAtencion;
		var costoUnitAtencion;
		totalAtencion = formato14B.f_costoTotalAtenL.val();
		nroAtencion = formato14B.f_nroTotalAtenL.val();
		//
		if( nroAtencion!=null && nroAtencion!=0 ){
			costoUnitAtencion=parseFloat(totalAtencion)/parseFloat(nroAtencion);
			costoUnitAtencion=redondeo(costoUnitAtencion, 2);
		}else{
			costoUnitAtencion='0.00';
		}
		formato14B.f_costoUnitAtenL.val(parseFloat(costoUnitAtencion));
	},
	totalGestionAdministrativaLima : function(){
		var costoPersonal;
		var capacitacionAgentes;
		var utilesOficina;
		var totalGestionAdministrativa;
		costoPersonal = formato14B.f_costoPersonalL.val();
		capacitacionAgentes = formato14B.f_capacAgentL.val();
		utilesOficina = formato14B.f_utilMatOficL.val();
		//
		totalGestionAdministrativa = parseFloat(costoPersonal)+parseFloat(capacitacionAgentes)+parseFloat(utilesOficina);
		totalGestionAdministrativa = redondeo(totalGestionAdministrativa, 2);
		//alert(totalEmpadronamiento);
		formato14B.f_costoTotalGestL.val(parseFloat(totalGestionAdministrativa));
	},
	//
	calculoTotal : function(){
		
		formato14B.totalImpresionRural();
		formato14B.totalImpresionProvincia();
		formato14B.totalImpresionLima();
		
		formato14B.costoUnitarioImpresionRural();
		formato14B.costoUnitarioImpresionProvincia();
		formato14B.costoUnitarioImpresionLima();
		
		formato14B.costoUnitarioRepartoValesRural();
		formato14B.costoUnitarioRepartoValesProvincia();
		formato14B.costoUnitarioRepartoValesLima();
		
		formato14B.costoUnitarioEntregaValesRural();
		formato14B.costoUnitarioEntregaValesProvincia();
		formato14B.costoUnitarioEntregaValesLima();
		
		formato14B.costoUnitarioLiquidValesRural();
		formato14B.costoUnitarioLiquidValesProvincia();
		formato14B.costoUnitarioLiquidValesLima();
		
		formato14B.totalAtencionRural();
		formato14B.totalAtencionProvincia();
		formato14B.totalAtencionLima();
		
		formato14B.costoUnitarioAtencionRural();
		formato14B.costoUnitarioAtencionProvincia();
		formato14B.costoUnitarioAtencionLima();
		
		formato14B.totalGestionAdministrativaRural();
		formato14B.totalGestionAdministrativaProvincia();
		formato14B.totalGestionAdministrativaLima();
	
		formato14B.formularioCompletarDecimales();
	},
	soloNumerosEnteros : function(){
		formato14B.f_nroValesImpR.attr("onkeypress","return soloNumerosDecimales(event, 1, 'nroValesImpR',7,0)");
		formato14B.f_nroValesReptR.attr("onkeypress","return soloNumerosDecimales(event, 1, 'nroValesReptR',7,0)");
		formato14B.f_nroValesEntrR.attr("onkeypress","return soloNumerosDecimales(event, 1, 'nroValesEntrR',7,0)");
		formato14B.f_nroValesFisR.attr("onkeypress","return soloNumerosDecimales(event, 1, 'nroValesFisR',7,0)");
		formato14B.f_nroTotalAtenR.attr("onkeypress","return soloNumerosDecimales(event, 1, 'nroTotalAtenR',7,0)");
		
		formato14B.f_nroValesImpP.attr("onkeypress","return soloNumerosDecimales(event, 1, 'nroValesImpP',7,0)");
		formato14B.f_nroValesReptP.attr("onkeypress","return soloNumerosDecimales(event, 1, 'nroValesReptP',7,0)");
		formato14B.f_nroValesEntrP.attr("onkeypress","return soloNumerosDecimales(event, 1, 'nroValesEntrP',7,0)");
		formato14B.f_nroValesFisP.attr("onkeypress","return soloNumerosDecimales(event, 1, 'nroValesFisP',7,0)");
		formato14B.f_nroTotalAtenP.attr("onkeypress","return soloNumerosDecimales(event, 1, 'nroTotalAtenP',7,0)");
		
		formato14B.f_nroValesImpL.attr("onkeypress","return soloNumerosDecimales(event, 1, 'nroValesImpL',7,0)");
		formato14B.f_nroValesReptL.attr("onkeypress","return soloNumerosDecimales(event, 1, 'nroValesReptL',7,0)");
		formato14B.f_nroValesEntrL.attr("onkeypress","return soloNumerosDecimales(event, 1, 'nroValesEntrL',7,0)");
		formato14B.f_nroValesFisL.attr("onkeypress","return soloNumerosDecimales(event, 1, 'nroValesFisL',7,0)");
		formato14B.f_nroTotalAtenL.attr("onkeypress","return soloNumerosDecimales(event, 1, 'nroTotalAtenL',7,0)");
		
	},
	soloNumerosDecimales : function(){
		//RURAL
		formato14B.f_impValEdeR.attr("onkeypress","return soloNumerosDecimales(event, 2, 'impValDesctoEdeR',7,2)");
		formato14B.f_impValNoEdeR.attr("onkeypress","return soloNumerosDecimales(event, 2, 'impValDesctoNoEdeR',7,2)");
		formato14B.f_costoTotalValR.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costoTotalValDesctoR',7,2)");
		formato14B.f_costoTotalValOficR.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costoTotalValOficR',7,2)");
		formato14B.f_costoEnvPadronR.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costoEnvPadronR',7,2)");
		formato14B.f_costoUnitDigitR.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costoUnitValesDigitR',7,2)");
		formato14B.f_costoAtenSolicR.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costoAtenSolicR',7,2)");
		formato14B.f_costoAtenConsR.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costoAtenConsR',7,2)");
		formato14B.f_costoPersonalR.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costoPersonalR',7,2)");
		formato14B.f_capacAgentR.attr("onkeypress","return soloNumerosDecimales(event, 2, 'capacAgentR',7,2)");
		formato14B.f_utilMatOficR.attr("onkeypress","return soloNumerosDecimales(event, 2, 'utilMatOficR',7,2)");
		//PROVINCIA
		formato14B.f_impValEdeP.attr("onkeypress","return soloNumerosDecimales(event, 2, 'impValDesctoEdeP',7,2)");
		formato14B.f_impValNoEdeP.attr("onkeypress","return soloNumerosDecimales(event, 2, 'impValDesctoNoEdeP',7,2)");
		formato14B.f_costoTotalValP.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costoTotalValDesctoP',7,2)");
		formato14B.f_costoTotalValOficP.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costoTotalValOficP',7,2)");
		formato14B.f_costoEnvPadronP.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costoEnvPadronP',7,2)");
		formato14B.f_costoUnitDigitP.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costoUnitValesDigitP',7,2)");
		formato14B.f_costoAtenSolicP.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costoAtenSolicP',7,2)");
		formato14B.f_costoAtenConsP.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costoAtenConsP',7,2)");
		formato14B.f_costoPersonalP.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costoPersonalP',7,2)");
		formato14B.f_capacAgentP.attr("onkeypress","return soloNumerosDecimales(event, 2, 'capacAgentP',7,2)");
		formato14B.f_utilMatOficP.attr("onkeypress","return soloNumerosDecimales(event, 2, 'utilMatOficP',7,2)");
		//LIMA
		formato14B.f_impValEdeL.attr("onkeypress","return soloNumerosDecimales(event, 2, 'impValDesctoEdeL',7,2)");
		formato14B.f_impValNoEdeL.attr("onkeypress","return soloNumerosDecimales(event, 2, 'impValDesctoNoEdeL',7,2)");
		formato14B.f_costoTotalValL.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costoTotalValDesctoL',7,2)");
		formato14B.f_costoTotalValOficL.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costoTotalValOficL',7,2)");
		formato14B.f_costoEnvPadronL.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costoEnvPadronL',7,2)");
		formato14B.f_costoUnitDigitL.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costoUnitValesDigitL',7,2)");
		formato14B.f_costoAtenSolicL.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costoAtenSolicL',7,2)");
		formato14B.f_costoAtenConsL.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costoAtenConsL',7,2)");
		formato14B.f_costoPersonalL.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costoPersonalL',7,2)");
		formato14B.f_capacAgentL.attr("onkeypress","return soloNumerosDecimales(event, 2, 'capacAgentL',7,2)");
		formato14B.f_utilMatOficL.attr("onkeypress","return soloNumerosDecimales(event, 2, 'utilMatOficL',7,2)");
	},
	formularioCompletarDecimales : function(){
		//RURAL
		completarDecimal('impValDesctoEdeR',2);
		completarDecimal('impValDesctoNoEdeR',2);
		completarDecimal('costoTotalImpR',2);
		completarDecimal('costoUnitImpValesR',2);
		completarDecimal('costoTotalValDesctoR',2);
		completarDecimal('costoUnitReptValesR',2);
		completarDecimal('costoTotalValOficR',2);
		completarDecimal('costoUnitEntrValesR',2);
		completarDecimal('costoEnvPadronR',2);
		completarDecimal('costoUnitLiqR',2);
		completarDecimal('costoUnitValesDigitR',2);
		completarDecimal('costoAtenSolicR',2);
		completarDecimal('costoAtenConsR',2);
		completarDecimal('costoTotalAtenR',2);
		completarDecimal('costoUnitAtenR',2);
		completarDecimal('costoPersonalR',2);
		completarDecimal('capacAgentR',2);
		completarDecimal('utilMatOficR',2);
		completarDecimal('costoTotalGestR',2);
		//PROVINCIA
		completarDecimal('impValDesctoEdeP',2);
		completarDecimal('impValDesctoNoEdeP',2);
		completarDecimal('costoTotalImpP',2);
		completarDecimal('costoUnitImpValesP',2);
		completarDecimal('costoTotalValDesctoP',2);
		completarDecimal('costoUnitReptValesP',2);
		completarDecimal('costoTotalValOficP',2);
		completarDecimal('costoUnitEntrValesP',2);
		completarDecimal('costoEnvPadronP',2);
		completarDecimal('costoUnitLiqP',2);
		completarDecimal('costoUnitValesDigitP',2);
		completarDecimal('costoAtenSolicP',2);
		completarDecimal('costoAtenConsP',2);
		completarDecimal('costoTotalAtenP',2);
		completarDecimal('costoUnitAtenP',2);
		completarDecimal('costoPersonalP',2);
		completarDecimal('capacAgentP',2);
		completarDecimal('utilMatOficP',2);
		completarDecimal('costoTotalGestP',2);
		//LIMA
		completarDecimal('impValDesctoEdeL',2);
		completarDecimal('impValDesctoNoEdeL',2);
		completarDecimal('costoTotalImpL',2);
		completarDecimal('costoUnitImpValesL',2);
		completarDecimal('costoTotalValDesctoL',2);
		completarDecimal('costoUnitReptValesL',2);
		completarDecimal('costoTotalValOficL',2);
		completarDecimal('costoUnitEntrValesL',2);
		completarDecimal('costoEnvPadronL',2);
		completarDecimal('costoUnitLiqL',2);
		completarDecimal('costoUnitValesDigitL',2);
		completarDecimal('costoAtenSolicL',2);
		completarDecimal('costoAtenConsL',2);
		completarDecimal('costoTotalAtenL',2);
		completarDecimal('costoUnitAtenL',2);
		completarDecimal('costoPersonalL',2);
		completarDecimal('capacAgentL',2);
		completarDecimal('utilMatOficL',2);
		completarDecimal('costoTotalGestL',2);
	},
	<portlet:namespace/>loadPeriodo : function(valPeriodo){
		jQuery.ajax({
				url: formato14B.urlCargaPeriodo+'&'+formato14B.formCommand.serialize(),
				type: 'post',
				dataType: 'json',
				success: function(data) {		
					dwr.util.removeAllOptions("periodoEnvio");
					dwr.util.addOptions("periodoEnvio", data,"codigoItem","descripcionItem");
					if( valPeriodo.length!='' ){
						dwr.util.setValue("periodoEnvio", valPeriodo);
					}
					formato14B.<portlet:namespace/>loadCargaFlagPeriodo();
					//validar lima edelnor y luz del sur
					if(formato14B.cod_empresa_edelnor.val()==formato14B.f_empresa.val() || formato14B.cod_empresa_luz_sur.val()==formato14B.f_empresa.val()){
						formato14B.habilitarLima();										
					}else{
						formato14B.deshabilitarLima();
					}
				},error : function(){
					alert("Error de conexión.");
					formato14B.initBlockUI();
				}
		});
	},
	<portlet:namespace/>loadCargaFlagPeriodo : function() {
		jQuery.ajax({
			url: formato14B.urlCargaFlagPeriodo+'&'+formato14B.formCommand.serialize(),
				type: 'post',
				dataType: 'json',
				success: function(data) {				
					dwr.util.setValue("flagPeriodoEjecucion", data.flagPeriodoEjecucion);
					//cargar los valores obtenidos de ano inicio y fin de vigencia
					dwr.util.setValue("anioInicioVigencia", data.anioInicioVigencia);
					dwr.util.setValue("anioFinVigencia", data.anioFinVigencia);
					//
					formato14B.recargarPeriodoEjecucion(data.anioInicioVigencia,data.anioFinVigencia);
					formato14B.mostrarPeriodoEjecucion();
				},error : function(){
					alert("Error de conexión.");
					formato14B.initBlockUI();
				}
		});
	},
	mostrarPeriodoEjecucion : function(){
		//ahora lo usamos para mostrar o no los componentes
		//alert(formato14B.f_flagPeriodo.val());
		if( formato14B.f_flagPeriodo.val()=='S' ){
			/*$('#anioInicioVigencia').removeAttr("disabled");
			$('#anioFinVigencia').removeAttr("disabled");
			//formato14B.divPeriodoEjecucion.show();  
			formato14B.estiloEdicionCabecera();*/
			$('#anioInicioVigencia').attr("disabled",true);
			$('#anioFinVigencia').attr("disabled",true);
			formato14B.quitarEstiloEdicionCabecera();
		}else{
			$('#anioInicioVigencia').attr("disabled",true);
			$('#anioFinVigencia').attr("disabled",true);
			//formato14B.divPeriodoEjecucion.hide();
			formato14B.quitarEstiloEdicionCabecera();
		}
	},
	recargarPeriodoEjecucion : function(valAnioInicio,valAnioFin){
		/*var anoInicio;
		var anoFin;
		if( formato14B.f_periodoEnvio.val() != null ){
			anoInicio = formato14B.f_periodoEnvio.val().substring(0,4);
			anoFin = formato14B.f_periodoEnvio.val().substring(0,4);
			if( formato14B.f_flagPeriodo.val()=='S' ){
				$('#anioInicioVigencia').val(anoInicio);
				$('#anioFinVigencia').val(anoFin);
			}
		}*/
		$('#anioInicioVigencia').val(valAnioInicio);
		$('#anioFinVigencia').val(valAnioFin);
	},
	//FORMULARIOS DE VIEW Y EDICION
	verFormato : function(codEmpresa,anoPresentacion,mesPresentacion,anoIniVigencia,anoFinVigencia,etapa){	
		$.blockUI({ message: formato14B.mensajeObteniendoDatos });
		jQuery.ajax({
				url: formato14B.urlCrud+'&'+formato14B.formCommand.serialize(),
				type: 'post',
				dataType: 'json',
				data: {
				   <portlet:namespace />tipo: "GET",
				   <portlet:namespace />codEmpresa: codEmpresa,
				   <portlet:namespace />anoPresentacion: anoPresentacion,
				   <portlet:namespace />mesPresentacion: mesPresentacion,
				   <portlet:namespace />anoIniVigencia: anoIniVigencia,
				   <portlet:namespace />anoFinVigencia: anoFinVigencia,
				   <portlet:namespace />etapa: etapa
					},
				success: function(data) {				
					if (data.resultado == "OK"){
						formato14B.procesoEstado.val("UPDATE");
						formato14B.etapaEdit.val(etapa);
						formato14B.divFormato.show();
						formato14B.divHome.hide();
						formato14B.divInformacion.show();
						//dwr.util.removeAllOptions("periodoEnvio");
						//dwr.util.addOptions("periodoEnvio", data.periodoEnvio,"codigoItem","descripcionItem");
						formato14B.FillEditformato(data.formato);
						formato14B.deshabilitarCamposView();
						formato14B.initBlockUI();
					}
					else{
						alert("Error al recuperar los datos del registro seleccionado");
						formato14B.initBlockUI();
					}
				},error : function(){
					alert("Error de conexión.");
					formato14B.initBlockUI();
				}
		});	
	},
	editarFormato : function(codEmpresa,anoPresentacion,mesPresentacion,anoIniVigencia,anoFinVigencia,etapa,flagOperacion){	
		var admin = '${formato14BCBean.admin}';
		if(flagOperacion=='ABIERTO'){
			//control para tipo de usuario
			var process=true;
			if( etapa=='ESTABLECIDO' || !admin ){
				process = false;
			}
			if(process){
				$.blockUI({ message: formato14B.mensajeObteniendoDatos });
				jQuery.ajax({
						url: formato14B.urlCrud+'&'+formato14B.formCommand.serialize(),
						type: 'post',
						dataType: 'json',
						data: {
						   <portlet:namespace />tipo: "GET",
						   <portlet:namespace />codEmpresa: codEmpresa,
						   <portlet:namespace />anoPresentacion: anoPresentacion,
						   <portlet:namespace />mesPresentacion: mesPresentacion,
						   <portlet:namespace />anoIniVigencia: anoIniVigencia,
						   <portlet:namespace />anoFinVigencia: anoFinVigencia,
						   <portlet:namespace />etapa: etapa
							},
						success: function(data) {				
							if (data.resultado == "OK"){
								formato14B.procesoEstado.val("UPDATE");
								formato14B.etapaEdit.val(etapa);
								formato14B.divFormato.show();
								formato14B.divHome.hide();
								formato14B.divInformacion.show();
								//dwr.util.removeAllOptions("periodoEnvio");
								//dwr.util.addOptions("periodoEnvio", data.periodoEnvio,"codigoItem","descripcionItem");
								formato14B.FillEditformato(data.formato);
								
								formato14B.estiloEdicionRural();
								formato14B.estiloEdicionProvincia();
								
								//validar lima edelnor y luz del sur
								if(formato14B.cod_empresa_edelnor.val()==formato14B.f_empresa.val() || formato14B.cod_empresa_luz_sur.val()==formato14B.f_empresa.val()){
									formato14B.habilitarLima();										
								}else{
									formato14B.deshabilitarLima();
								}
								formato14B.initBlockUI();
							}
							else{
								alert("Error al recuperar los datos del registro seleccionado");
								formato14B.initBlockUI();
							}
						},error : function(){
							alert("Error de conexión.");
							formato14B.initBlockUI();
						}
				});
			}else{
				alert(" No tiene autorización para realizar esta operación");
			}	
			
		}else if(flagOperacion=='CERRADO'){
			alert(" Está fuera de plazo");
		}else{
			alert("El formato ya fue enviado a OSINERGMIN-GART");	
		}
	},
	FillEditformato : function(row){
		
		formato14B.f_empresa.val(trim(row.codEmpresa));
		//seteamos el concatenado
		formato14B.f_periodoEnvio.val(''+row.anoPresentacion+completarCerosIzq(row.mesPresentacion,2)+row.etapa);
		
		//seteamos el grupo y estado
		$('#descGrupoInformacion').val(row.grupoInfo);
		$('#descEstado').val(row.estado);	
		
		//seteamos el periodo envio, cargado de base de datos
		dwr.util.removeAllOptions("periodoEnvio");
		var codigo=''+row.anoPresentacion+completarCerosIzq(row.mesPresentacion,2)+row.etapa;
		var descripcion=formato14B.mostrarDescripcionPeriodo(row.anoPresentacion, row.mesPresentacion, row.etapa);
   		
		//alert(formato14B.mostrarDescripcionPeriodo(row.anoPresentacion, row.mesPresentacion, row.etapa));
		var dataPeriodo = [{codigoItem:codigo, descripcionItem:descripcion}];   
   		dwr.util.addOptions("periodoEnvio", dataPeriodo,"codigoItem","descripcionItem");
		//
		formato14B.f_flagPeriodo.val(row.flagPeriodoEjecucion);
		$('#anioInicioVigencia').val(row.anoIniVigencia);
		$('#anioFinVigencia').val(row.anoFinVigencia);
		
		//formato14B.mostrarPeriodoEjecucion();
		
		/*if( formato14B.f_flagPeriodo.val()=='S' ){
			$('#anioInicioVigencia').attr("disabled",true);
			$('#anioFinVigencia').attr("disabled",true);
		}*/
		formato14B.etapaEdit.val(row.etapa);
		//RURAL
		formato14B.f_impValEdeR.val(row.impValEdeR);
		formato14B.f_impValNoEdeR.val(row.impValNoEdeR);
		formato14B.f_costoTotalImpR.val(row.costoTotalImpR);
		formato14B.f_nroValesImpR.val(row.nroValesImpR);
		formato14B.f_costoUnitImpValesR.val(row.costoUnitImpValesR);
		formato14B.f_costoTotalValR.val(row.costoTotalValR);
		formato14B.f_nroValesReptR.val(row.nroValesReptR);
		formato14B.f_costoUnitReptValesR.val(row.costoUnitReptValesR);
		formato14B.f_costoTotalValOficR.val(row.costoTotalValOficR);
		formato14B.f_nroValesEntrR.val(row.nroValesEntrR);
		formato14B.f_costoUnitEntrValesR.val(row.costoUnitEntrValesR);
		formato14B.f_costoEnvPadronR.val(row.costoEnvPadronR);
		formato14B.f_nroValesFisR.val(row.nroValesFisR);
		formato14B.f_costoUnitLiqR.val(row.costoUnitLiqR);
		formato14B.f_costoUnitDigitR.val(row.costoUnitDigitR);
		formato14B.f_costoAtenSolicR.val(row.costoAtenSolicR);
		formato14B.f_costoAtenConsR.val(row.costoAtenConsR);
		formato14B.f_costoTotalAtenR.val(row.costoTotalAtenR);
		formato14B.f_nroTotalAtenR.val(row.nroTotalAtenR);
		formato14B.f_costoUnitAtenR.val(row.costoUnitAtenR);
		formato14B.f_costoPersonalR.val(row.costoPersonalR);
		formato14B.f_capacAgentR.val(row.capacAgentR);
		formato14B.f_utilMatOficR.val(row.utilMatOficR);
		formato14B.f_costoTotalGestR.val(row.costoTotalGestR);
		//PROVINCIA
		formato14B.f_impValEdeP.val(row.impValEdeP);
		formato14B.f_impValNoEdeP.val(row.impValNoEdeP);
		formato14B.f_costoTotalImpP.val(row.costoTotalImpP);
		formato14B.f_nroValesImpP.val(row.nroValesImpP);
		formato14B.f_costoUnitImpValesP.val(row.costoUnitImpValesP);
		formato14B.f_costoTotalValP.val(row.costoTotalValP);
		formato14B.f_nroValesReptP.val(row.nroValesReptP);
		formato14B.f_costoUnitReptValesP.val(row.costoUnitReptValesP);
		formato14B.f_costoTotalValOficP.val(row.costoTotalValOficP);
		formato14B.f_nroValesEntrP.val(row.nroValesEntrP);
		formato14B.f_costoUnitEntrValesP.val(row.costoUnitEntrValesP);
		formato14B.f_costoEnvPadronP.val(row.costoEnvPadronP);
		formato14B.f_nroValesFisP.val(row.nroValesFisP);
		formato14B.f_costoUnitLiqP.val(row.costoUnitLiqP);
		formato14B.f_costoUnitDigitP.val(row.costoUnitDigitP);
		formato14B.f_costoAtenSolicP.val(row.costoAtenSolicP);
		formato14B.f_costoAtenConsP.val(row.costoAtenConsP);
		formato14B.f_costoTotalAtenP.val(row.costoTotalAtenP);
		formato14B.f_nroTotalAtenP.val(row.nroTotalAtenP);
		formato14B.f_costoUnitAtenP.val(row.costoUnitAtenP);
		formato14B.f_costoPersonalP.val(row.costoPersonalP);
		formato14B.f_capacAgentP.val(row.capacAgentP);
		formato14B.f_utilMatOficP.val(row.utilMatOficP);
		formato14B.f_costoTotalGestP.val(row.costoTotalGestP);
		//LIMA
		formato14B.f_impValEdeL.val(row.impValEdeL);
		formato14B.f_impValNoEdeL.val(row.impValNoEdeL);
		formato14B.f_costoTotalImpL.val(row.costoTotalImpL);
		formato14B.f_nroValesImpL.val(row.nroValesImpL);
		formato14B.f_costoUnitImpValesL.val(row.costoUnitImpValesL);
		formato14B.f_costoTotalValL.val(row.costoTotalValL);
		formato14B.f_nroValesReptL.val(row.nroValesReptL);
		formato14B.f_costoUnitReptValesL.val(row.costoUnitReptValesL);
		formato14B.f_costoTotalValOficL.val(row.costoTotalValOficL);
		formato14B.f_nroValesEntrL.val(row.nroValesEntrL);
		formato14B.f_costoUnitEntrValesL.val(row.costoUnitEntrValesL);
		formato14B.f_costoEnvPadronL.val(row.costoEnvPadronL);
		formato14B.f_nroValesFisL.val(row.nroValesFisL);
		formato14B.f_costoUnitLiqL.val(row.costoUnitLiqL);
		formato14B.f_costoUnitDigitL.val(row.costoUnitDigitL);
		formato14B.f_costoAtenSolicL.val(row.costoAtenSolicL);
		formato14B.f_costoAtenConsL.val(row.costoAtenConsL);
		formato14B.f_costoTotalAtenL.val(row.costoTotalAtenL);
		formato14B.f_nroTotalAtenL.val(row.nroTotalAtenL);
		formato14B.f_costoUnitAtenL.val(row.costoUnitAtenL);
		formato14B.f_costoPersonalL.val(row.costoPersonalL);
		formato14B.f_capacAgentL.val(row.capacAgentL);
		formato14B.f_utilMatOficL.val(row.utilMatOficL);
		formato14B.f_costoTotalGestL.val(row.costoTotalGestL);
		
		formato14B.f_empresa.attr("disabled",true);
		formato14B.f_periodoEnvio.attr("disabled",true);
		$('#anioInicioVigencia').attr("disabled",true);
		$('#anioFinVigencia').attr("disabled",true);
		formato14B.quitarEstiloEdicionCabecera();
		//
		formato14B.calculoTotal();
		
		formato14B.soloNumerosEnteros();
		formato14B.soloNumerosDecimales();
		formato14B.formularioCompletarDecimales();
		
		formato14B.flagCarga.val('1');//inicializamos el flag de carga cuando editamos el archivo antes de cargar archivos
		//alert(formato14B.flagCarga.val());
		//formato14B.mostrarPeriodoEjecucion();
	},
//////CRUD
	<portlet:namespace/>guardarFormato : function(){
		if (formato14B.validarFormulario()){
			$.blockUI({ message: formato14B.mensajeGuardando });
			 jQuery.ajax({
				 url: formato14B.urlCrud+'&'+formato14B.formCommand.serialize(),
				type: 'post',
				dataType: 'json',
				data: {
					<portlet:namespace />tipo: formato14B.procesoEstado.val(),
					<portlet:namespace />codEmpresa: formato14B.f_empresa.val(),
					<portlet:namespace />periodoEnvio: formato14B.f_periodoEnvio.val(),
					<portlet:namespace />flagPeriodo: formato14B.f_flagPeriodo.val(),
					<portlet:namespace />anoInicioVigencia: $('#anioInicioVigencia').val(),
					<portlet:namespace />anoFinVigencia: $('#anioFinVigencia').val(),
					<portlet:namespace />etapa: formato14B.etapaEdit.val()
					},
				success: function(data) {			
					if (data.resultado == "OK"){				
						var addhtml2='Datos guardados satisfactoriamente';
						formato14B.dialogMessageContent.html(addhtml2);
						formato14B.dialogMessage.dialog("open");
						formato14B.flagCarga.val('1');//inicializamos el flag de carga cuando editamos el archivo antes de cargar archivos
						formato14B.mostrarFormularioModificado();
						formato14B.initBlockUI();
					}
					else if(data.resultado == "Error"){				
						var addhtml2='Se produjo un error al guardar los datos: '+data.mensaje;
						formato14B.dialogMessageContent.html(addhtml2);
						formato14B.dialogMessage.dialog("open");
						formato14B.mostrarUltimoFormato();
						formato14B.initBlockUI();
					}
				},error : function(){
					alert("Error de conexión.");
					formato14B.initBlockUI();
				}
			});
		   	//se deja el formulario activo
			formato14B.divFormato.hide();
			formato14B.divHome.show();
		}
	},
	confirmarEliminar : function(codEmpresa,anoPresentacion,mesPresentacion,anoIniVigencia,anoFinVigencia,etapa,flagOperacion){
		var admin = '${formato14BCBean.admin}';
		if(flagOperacion=='ABIERTO'){
			//control para tipo de usuario
			var process=true;
			if( etapa=='ESTABLECIDO' || !admin ){
				process = false;
			}
			if(process){
				var addhtml='¿Está seguro que desea eliminar el registro seleccionado?';
				formato14B.dialogConfirmContent.html(addhtml);
				formato14B.dialogConfirm.dialog("open");
				cod_Empresa=codEmpresa;
				ano_Presentacion=anoPresentacion;
				mes_Presentacion=mesPresentacion;
				ano_Inicio_Vigencia=anoIniVigencia;
				ano_Fin_Vigencia=anoFinVigencia;
				cod_Etapa=etapa;
			}else{
				alert(" No tiene autorización para realizar esta operación");
			}
			
		}else if(flagOperacion=='CERRADO'){
			alert(" Está fuera de plazo");
		}else{
			alert("El formato ya fue enviado a OSINERGMIN-GART");	
		}
	},
	eliminarFormato : function(cod_Empresa,ano_Presentacion,mes_Presentacion,ano_Inicio_Vigencia,ano_Fin_Vigencia,cod_Etapa){			
		$.blockUI({ message: formato14B.mensajeEliminando });
		jQuery.ajax({
			url: formato14B.urlCrud+'&'+formato14B.formCommand.serialize(),
			type: 'post',
			dataType: 'json',
			data: {
			   <portlet:namespace />tipo: "DELETE",
			   <portlet:namespace />codEmpresa: cod_Empresa,
			   <portlet:namespace />anoPresentacion: ano_Presentacion,
			   <portlet:namespace />mesPresentacion: mes_Presentacion,
			   <portlet:namespace />anoInicioVigencia: ano_Inicio_Vigencia,
			   <portlet:namespace />anoFinVigencia: ano_Fin_Vigencia,
			   <portlet:namespace />etapa: cod_Etapa
				},
			success: function(data) {
				if (data.resultado == "OK"){
					var addhtml2='Registro eliminado con éxito';					
					formato14B.dialogMessageContent.html(addhtml2);
					formato14B.dialogMessage.dialog("open");
					formato14B.buscar();
					formato14B.initBlockUI();
				}
				else{
					alert("Error al eliminar el registro");
					formato14B.initBlockUI();
				}
			},error : function(){
				alert("Error de conexión.");
				formato14B.initBlockUI();
			}
		});
	},
	mostrarFormularioModificado : function(){
		var codEmpM = formato14B.f_empresa.val();
		var anioPresM = formato14B.f_periodoEnvio.val().substring(0,4);
		var mesPresM = formato14B.f_periodoEnvio.val().substring(4,6);
		/*var anioIniVigM;
		var anioFinVigM;
		 if( formato14B.f_flagPeriodo.val()=='S' ){
			 anioIniVigM = $("#anioInicioVigencia").val();
			 anioFinVigM = $("#anioFinVigencia").val();
		 }else{
			 anioIniVigM = anioPresM;
			 anioFinVigM = anioPresM;
		 }*/
		 var flagOpera = 'ABIERTO';
		 
		 var anioIniVigM = $("#anioInicioVigencia").val();
		 var anioFinVigM = $("#anioFinVigencia").val();
		 //var etapaM = "SOLICITUD";
		 var etapaM = formato14B.f_periodoEnvio.val().substring(6,formato14B.f_periodoEnvio.val().length);
		 //alert(formato14B.flagCarga.val());
		 if( formato14B.flagCarga.val()=='0' ){
			 formato14B.mostrarUltimoFormato();
		 }else{
			//alert(codEmpM+','+anioPresM+','+mesPresM+','+anioIniVigM+','+anioFinVigM+','+etapaM);
			 if(codEmpM != '' && anioPresM != '' && mesPresM != '' && anioIniVigM != '' && anioFinVigM != '' && etapaM != ''){
			 	 formato14B.editarFormato(codEmpM, anioPresM, mesPresM, anioIniVigM, anioFinVigM, etapaM,flagOpera);
			 }
		 }
		 formato14B.botonValidacion.css('display','');
	},
	mostrarUltimoFormato : function(){	
		formato14B.procesoEstado.val('SAVE');
		formato14B.etapaEdit.val("");
		formato14B.divFormato.show();
		formato14B.divHome.hide();
		formato14B.flagCarga.val('0');
		//
		formato14B.estiloEdicionRural();
		formato14B.estiloEdicionProvincia();
	},
	validarFormulario : function() {		
		if(formato14B.f_empresa.val().length == '' ) { 	
			alert('Seleccione una empresa'); 
			formato14B.f_empresa.focus();
		  	return false; 
		}
		if(formato14B.f_periodoEnvio.val().length == '' ) {		  
			alert('Debe ingresar el periodo de presentacion');
			formato14B.f_periodoEnvio.focus();
			return false; 
		}
		if( formato14B.f_flagPeriodo.val()=='S' ){
			if($('#anioInicioVigencia').val().length == '' ) {		  
				alert('Debe ingresar el año de inicio de vigencia');
				$('#anioInicioVigencia').focus();
				return false; 
			}else{
				var numstr = trim($('#anioInicioVigencia').val());
				if (isNaN(numstr) || numstr.length<4 || parseFloat(numstr)<1900){
					alert('Ingrese un año de inicio de inicio de vigencia válido');
					return false;
			 	}
		 	}
			if($('#anioFinVigencia').val().length == '' ) {		  
				alert('Debe ingresar el año de fin de vigencia');
				$('#anioFinVigencia').focus();
				return false; 
			}else{
				var numstr = trim($('#anioFinVigencia').val());
				if (isNaN(numstr) || numstr.length<4 || parseFloat(numstr)<1900){
					alert('Ingrese un año de fin de vigencia válido');
					return false;
				}
		 	}
		}
		//valores de formulario
		if(formato14B.f_nroValesImpR.val().length == '' ) {		  
			alert('Debe ingresar el número de vales impresos para Rural');
			formato14B.f_nroBenefR.focus();
			return false; 
		}
		if(formato14B.f_nroValesReptR.val().length == '' ) {		  
			alert('Debe ingresar el número de vales repartidos para Rural');
			formato14B.f_nroBenefR.focus();
			return false; 
		}
		if(formato14B.f_nroValesEntrR.val().length == '' ) {		  
			alert('Debe ingresar el número de vales entregados para Rural');
			formato14B.f_nroBenefR.focus();
			return false; 
		}
		if(formato14B.f_nroValesFisR.val().length == '' ) {		  
			alert('Debe ingresar el número de vales físicos para Rural');
			formato14B.f_nroBenefR.focus();
			return false; 
		}
		if(formato14B.f_nroTotalAtenR.val().length == '' ) {		  
			alert('Debe ingresar el número total de atenciones para Rural');
			formato14B.f_nroBenefR.focus();
			return false; 
		}
		////////////////////
		if(formato14B.f_nroValesImpP.val().length == '' ) {		  
			alert('Debe ingresar el número de vales impresos para Provincia');
			formato14B.f_nroBenefP.focus();
			return false; 
		}
		if(formato14B.f_nroValesReptP.val().length == '' ) {		  
			alert('Debe ingresar el número de vales repartidos para Provincia');
			formato14B.f_nroBenefP.focus();
			return false; 
		}
		if(formato14B.f_nroValesEntrP.val().length == '' ) {		  
			alert('Debe ingresar el número de vales entregados para Provincia');
			formato14B.f_nroBenefP.focus();
			return false; 
		}
		if(formato14B.f_nroValesFisP.val().length == '' ) {		  
			alert('Debe ingresar el número de vales físicos para Provincia');
			formato14B.f_nroBenefP.focus();
			return false; 
		}
		if(formato14B.f_nroTotalAtenP.val().length == '' ) {		  
			alert('Debe ingresar el número total de atenciones para Provincia');
			formato14B.f_nroBenefP.focus();
			return false; 
		}
		///////////////////
		if(formato14B.f_nroValesImpL.val().length == '' ) {		  
			alert('Debe ingresar el número de vales impresos para Lima');
			formato14B.f_nroBenefL.focus();
			return false; 
		}
		if(formato14B.f_nroValesReptL.val().length == '' ) {		  
			alert('Debe ingresar el número de vales repartidos para Lima');
			formato14B.f_nroBenefL.focus();
			return false; 
		}
		if(formato14B.f_nroValesEntrL.val().length == '' ) {		  
			alert('Debe ingresar el número de vales entregados para Lima');
			formato14B.f_nroBenefL.focus();
			return false; 
		}
		if(formato14B.f_nroValesFisL.val().length == '' ) {		  
			alert('Debe ingresar el número de vales físicos para Lima');
			formato14B.f_nroBenefL.focus();
			return false; 
		}
		if(formato14B.f_nroTotalAtenL.val().length == '' ) {		  
			alert('Debe ingresar el número total de atenciones para Lima');
			formato14B.f_nroBenefL.focus();
			return false; 
		}
		return true; 
	},
	validarArchivoCarga : function() {		
		if(formato14B.f_empresa.val().length == '' ) { 	
			alert('Seleccione una empresa para proceder con la carga de archivo'); 
			formato14B.f_empresa.focus();
			return false; 
		}
		if( formato14B.f_periodoEnvio==null || formato14B.f_periodoEnvio.val().length == '' ) {		  
			alert('Debe seleccionar el periodo a declarar');
			formato14B.f_periodoEnvio.focus();
			return false; 
		}
		return true; 
	},
	////
	<portlet:namespace/>cargarFormatoExcel : function(){
		var frm = document.getElementById('formato14BCBean');
		var nameFile=$("#archivoExcel").val();
		var isSubmit=true;
		
		$("#msjFileExcel").html("");
		if(typeof (nameFile) == "undefined" || nameFile.length==0){
			isSubmit=false;
			$("#msjFileExcel").html("Debe seleccionar un archivo");
		}else{
			isSubmit=true;
			$("#msjFileExcel").html("");
		}
		if(isSubmit){
			frm.submit();
		}
		//frm.submit();
	},
	<portlet:namespace/>cargarFormatoTexto : function(){
		var frm = document.getElementById('formato14BCBean');
		var nameFile=$("#archivoTxt").val();
		var isSubmit=true;
		
		$("#msjFileTxt").html("");
		if(typeof (nameFile) == "undefined" || nameFile.length==0){
			isSubmit=false;
			$("#msjFileTxt").html("Debe seleccionar un archivo");
		}else{
			isSubmit=true;
			$("#msjFileTxt").html("");
		}
		if(isSubmit){
			frm.submit();
		}
		//frm.submit();
	},
	<portlet:namespace/>mostrarFormularioCargaExcel : function(){
		if (formato14B.validarArchivoCarga()){
			if( formato14B.flagCarga.val()=='0' ){//proviene de archivos nuevos
				formato14B.flagCarga.val('2');//para cargar archivos excel
			}else if( formato14B.flagCarga.val()=='1' ){//proviene de archivos modificados
				formato14B.flagCarga.val('3');//para cargar archivos excel
			}
			formato14B.divOverlay.show();
		    formato14B.dialogCargaExcel.show();
		    formato14B.dialogCargaExcel.show();
		    formato14B.dialogCargaExcel.css({ 
		        'left': ($(window).width() / 2 - formato14B.dialogCargaExcel.width() / 2) + 'px', 
		        'top': ($(window).height()  - formato14B.dialogCargaExcel.height() ) + 'px'
		    });
		    formato14B.iniciarMensajeExcel();
		}
	},
	regresarFormularioCargaExcel : function(){
		formato14B.flagCarga.val('');
		formato14B.iniciarMensajeExcel();
		formato14B.dialogCargaExcel.hide();
		formato14B.divOverlay.hide();   
	},
	<portlet:namespace/>mostrarFormularioCargaTexto : function(){
		if (formato14B.validarArchivoCarga()){
			if( formato14B.flagCarga.val()=='0' ){//proviene de un archivo nuevo
				formato14B.flagCarga.val('4');//para cargar archivos texto
			}else if( formato14B.flagCarga.val()=='1' ){//proviene de un archivo modificado
				formato14B.flagCarga.val('5');//para archivos texto
			}
			formato14B.divOverlay.show();
			formato14B.dialogCargaTexto.show();
			formato14B.dialogCargaTexto.css({ 
		        'left': ($(window).width() / 2 - formato14B.dialogCargaTexto.width() / 2) + 'px', 
		        'top': ($(window).height() - formato14B.dialogCargaTexto.height() ) + 'px'
		    });
			formato14B.iniciarMensajeTxt();
		}
	},
	regresarFormularioCargaTexto : function(){
		formato14B.flagCarga.val('');
		formato14B.iniciarMensajeTxt();
		formato14B.dialogCargaTexto.hide();
		formato14B.divOverlay.hide();   
	},
	iniciarMensajeExcel : function(){
		$("#msjFileExcel").html("");
	},
	iniciarMensajeTxt : function(){
		$("#msjFileTxt").html("");
	},
	<portlet:namespace/>mostrarReportePdf : function(){
		jQuery.ajax({
			url : formato14B.urlReporte+'&'+formato14B.formCommand.serialize(),
			type : 'post',
			dataType : 'json',
			data : {
				<portlet:namespace />codEmpresa: formato14B.f_empresa.val(),
				<portlet:namespace />periodoEnvio: formato14B.f_periodoEnvio.val(),
				<portlet:namespace />anoInicioVigencia: $('#anioInicioVigencia').val(),
				<portlet:namespace />anoFinVigencia: $('#anioFinVigencia').val(),
				<portlet:namespace />nombreReporte: 'formato14B',
				<portlet:namespace />nombreArchivo: 'formato14B',
				<portlet:namespace />tipoArchivo: '0'//PDF
			},
			success : function(gridData) {
				formato14B.verReporte();
			},error : function(){
				alert("Error de conexión.");
				formato14B.initBlockUI();
			}
		});
	},
	<portlet:namespace/>mostrarReporteExcel : function(){
		jQuery.ajax({
			url : formato14B.urlReporte+'&'+formato14B.formCommand.serialize(),
			type : 'post',
			dataType : 'json',
			data : {
				<portlet:namespace />codEmpresa: formato14B.f_empresa.val(),
				<portlet:namespace />periodoEnvio: formato14B.f_periodoEnvio.val(),
				<portlet:namespace />anoInicioVigencia: $('#anioInicioVigencia').val(),
				<portlet:namespace />anoFinVigencia: $('#anioFinVigencia').val(),
				<portlet:namespace />nombreReporte: 'formato14B',
				<portlet:namespace />nombreArchivo: 'formato14B',
				<portlet:namespace />tipoArchivo: '1'//XLS
			},
			success : function(gridData) {
				//alert('entro');
				formato14B.verReporte();
			},error : function(){
				alert("Error de conexión.");
				formato14B.initBlockUI();
			}
		});
	},
	verReporte : function(){
		window.open('<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ViewReport")%>','_newtab');
	},
	<portlet:namespace/>validacionFormato : function(){
		jQuery.ajax({
			url: formato14B.urlValidacion+'&'+formato14B.formCommand.serialize(),
			type : 'post',
			dataType : 'json',
			data : {
				<portlet:namespace />codEmpresa: formato14B.f_empresa.val(),
				<portlet:namespace />periodoEnvio: formato14B.f_periodoEnvio.val(),
				<portlet:namespace />anoInicioVigencia: $('#anioInicioVigencia').val(),
				<portlet:namespace />anoFinVigencia: $('#anioFinVigencia').val()
			},
			success : function(data) {
				if( data!=null ){
					formato14B.dialogObservacion.dialog("open");
					formato14B.tablaObservacion.clearGridData(true);
					formato14B.tablaObservacion.jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
					formato14B.tablaObservacion[0].refreshIndex();
					formato14B.initBlockUI();
				}

			},error : function(){
				alert("Error de conexión.");
				formato14B.initBlockUI();
			}
		});
	},
	<portlet:namespace/>mostrarReporteValidacion : function(){
		jQuery.ajax({
			url: formato14B.urlReporteValidacion+'&'+formato14B.formCommand.serialize(),
			type : 'post',
			dataType : 'json',
			data : {
				<portlet:namespace />codEmpresa: formato14B.f_empresa.val(),
				<portlet:namespace />periodoEnvio: formato14B.f_periodoEnvio.val(),
				<portlet:namespace />nombreReporte: 'validacion',
				<portlet:namespace />nombreArchivo: 'validacion',
				<portlet:namespace />tipoArchivo: '0'//PDF
			},
			success : function(gridData) {
				formato14B.verReporte();
			},error : function(){
				alert("Error de conexión.");
				formato14B.initBlockUI();
			}
		});
	},
	confirmarEnvioDefinitivo : function(){	
		var addhtml='¿Está seguro que desea realizar el envío definitivo?';
		formato14B.dialogConfirmEnvioContent.html(addhtml);
		formato14B.dialogConfirmEnvio.dialog("open");
	},
	<portlet:namespace/>envioDefinitivo : function(){
		jQuery.ajax({
			url: formato14B.urlEnvioDefinitivo+'&'+formato14B.formCommand.serialize(),
			type : 'post',
			dataType : 'json',
			data : {
				<portlet:namespace />codEmpresa: formato14B.f_empresa.val(),
				<portlet:namespace />periodoEnvio: formato14B.f_periodoEnvio.val(),
				<portlet:namespace />anoInicioVigencia: $('#anioInicioVigencia').val(),
				<portlet:namespace />anoFinVigencia: $('#anioFinVigencia').val(),
				<portlet:namespace />nombreReporte: 'formato14B',
				<portlet:namespace />nombreArchivo: 'formato14B',
				<portlet:namespace />tipoArchivo: '0'//PDF
			},
			success : function(gridData) {
				var addhtml='Se realizó el envío satisfactoriamente al correo '+formato14B.emailConfigured;
				formato14B.dialogMessageReportContent.html(addhtml);
				formato14B.dialogMessageReport.dialog("open");
				formato14B.initBlockUI();
			},error : function(){
				alert("Error de conexión.");
				formato14B.initBlockUI();
			}
		});
	},
	buildGridsObservacion : function () {	
		formato14B.tablaObservacion.jqGrid({
		   datatype: "local",
	       colNames: ['Grupo Zona','Código','Descripción'],
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
				rownumbers: true,
				//shrinkToFit:true,
				pager: formato14B.paginadoObservacion,
			    viewrecords: true,
			   	//caption: "Formatos",
			    sortorder: "asc"
	  	});
		formato14B.tablaObservacion.jqGrid('navGrid',formato14B.paginadoObservacion,{add:false,edit:false,del:false,search: false,refresh: false});	
		formato14B.tablaObservacion.jqGrid('navButtonAdd',formato14B.paginadoObservacion,{
		       caption:"Exportar a Excel",
		       buttonicon: "ui-icon-bookmark",
		       onClickButton : function () {
		           location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';
		       } 
		}); 
		formato14B.tablaObservacion.jqGrid('navButtonAdd',formato14B.paginadoObservacion,{
		       caption:"Exportar a Pdf",
		       buttonicon: "ui-icon-bookmark",
		       onClickButton : function () {
		    	   formato14B.<portlet:namespace/>mostrarReporteValidacion();
		       } 
		});
	},
	//
	mostrarDescripcionPeriodo : function(anio,mes,etapa){
		  var monthNames = ["Ene","Feb","Mar","Abr","May","Jun","Jul","Ago","Sep","Oct","Nov","Dic"];
		  var descripcionPeriodo;
		  //alert(monthNames[mes-1]);
		  descripcionPeriodo=''+monthNames[mes-1]+'-'+anio+' / '+etapa;
		  //alert(descripcionPeriodo);
		  return descripcionPeriodo;
	},
	<portlet:namespace/>mostrarReporteEnvioDefinitivo : function(){
		jQuery.ajax({
			url: formato14B.urlReporteEnvioDefinitivo+'&'+formato14B.formCommand.serialize(),
			type : 'post',
			dataType : 'json',
			data : {
				<portlet:namespace />tipoArchivo: '2'//PDF+concatenado
			},
			success : function(gridData) {
				formato14B.verReporte();
			},error : function(){
				alert("Error de conexión.");
				formato14B.initBlockUI();
			}
		});
	},
	<portlet:namespace/>mostrarReporteActaEnvio : function(){
		var estado = $('#descEstado').val();	
		if(estado=='Enviado'){
			jQuery.ajax({
				url: formato14B.urlReporteActaEnvio+'&'+formato14B.formCommand.serialize(),
				type : 'post',
				dataType : 'json',
				data : {
					<portlet:namespace />codEmpresa: formato14B.f_empresa.val(),
					<portlet:namespace />periodoEnvio: formato14B.f_periodoEnvio.val(),
					<portlet:namespace />anoInicioVigencia: $('#anioInicioVigencia').val(),
					<portlet:namespace />anoFinVigencia: $('#anioFinVigencia').val(),
					<portlet:namespace />tipoArchivo: '0'//PDF
				},
				success : function(gridData) {
					formato14B.verReporte();
				},error : function(){
					alert("Error de conexión.");
					formato14B.initBlockUI();
				}
			});
		}else{
			alert("Primero debe realizar el envío definitivo");
		}
	},
	//funcion para desabiliar campos lima
	deshabilitarLima : function(){
		//LIMA
		formato14B.f_impValEdeL.attr("disabled",true);
		formato14B.f_impValNoEdeL.attr("disabled",true);
		formato14B.f_nroValesImpL.attr("disabled",true);
		formato14B.f_costoTotalValL.attr("disabled",true);
		formato14B.f_nroValesReptL.attr("disabled",true);
		formato14B.f_costoTotalValOficL.attr("disabled",true);
		formato14B.f_nroValesEntrL.attr("disabled",true);
		formato14B.f_costoEnvPadronL.attr("disabled",true);
		formato14B.f_nroValesFisL.attr("disabled",true);
		formato14B.f_costoUnitDigitL.attr("disabled",true);
		formato14B.f_costoAtenSolicL.attr("disabled",true);
		formato14B.f_costoAtenConsL.attr("disabled",true);
		formato14B.f_nroTotalAtenL.attr("disabled",true);
		formato14B.f_costoPersonalL.attr("disabled",true);
		formato14B.f_capacAgentL.attr("disabled",true);
		formato14B.f_utilMatOficL.attr("disabled",true);
		
		formato14B.quitarEstiloEdicionLima();
	},
	//funcion para habilitar campos lima
	habilitarLima : function(){
		formato14B.f_impValEdeL.removeAttr("disabled");
		formato14B.f_impValNoEdeL.removeAttr("disabled");
		formato14B.f_nroValesImpL.removeAttr("disabled");
		formato14B.f_costoTotalValL.removeAttr("disabled");
		formato14B.f_nroValesReptL.removeAttr("disabled");
		formato14B.f_costoTotalValOficL.removeAttr("disabled");
		formato14B.f_nroValesEntrL.removeAttr("disabled");
		formato14B.f_costoEnvPadronL.removeAttr("disabled");
		formato14B.f_nroValesFisL.removeAttr("disabled");
		formato14B.f_costoUnitDigitL.removeAttr("disabled");
		formato14B.f_costoAtenSolicL.removeAttr("disabled");
		formato14B.f_costoAtenConsL.removeAttr("disabled");
		formato14B.f_nroTotalAtenL.removeAttr("disabled");
		formato14B.f_costoPersonalL.removeAttr("disabled");
		formato14B.f_capacAgentL.removeAttr("disabled");
		formato14B.f_utilMatOficL.removeAttr("disabled");
		
		formato14B.estiloEdicionLima();
	},
	//poner estilos de edicion para cada columna
	estiloEdicionCabecera : function(){
		$('#anioInicioVigencia').addClass("fise-editable");
		$('#anioFinVigencia').addClass("fise-editable");
	},
	estiloEdicionRural : function(){
		formato14B.f_impValEdeR.addClass("fise-editable");
		formato14B.f_impValNoEdeR.addClass("fise-editable");
		formato14B.f_nroValesImpR.addClass("fise-editable");
		formato14B.f_costoTotalValR.addClass("fise-editable");
		formato14B.f_nroValesReptR.addClass("fise-editable");
		formato14B.f_costoTotalValOficR.addClass("fise-editable");
		formato14B.f_nroValesEntrR.addClass("fise-editable");
		formato14B.f_costoEnvPadronR.addClass("fise-editable");
		formato14B.f_nroValesFisR.addClass("fise-editable");
		formato14B.f_costoUnitDigitR.addClass("fise-editable");
		formato14B.f_costoAtenSolicR.addClass("fise-editable");
		formato14B.f_costoAtenConsR.addClass("fise-editable");
		formato14B.f_nroTotalAtenR.addClass("fise-editable");
		formato14B.f_costoPersonalR.addClass("fise-editable");
		formato14B.f_capacAgentR.addClass("fise-editable");
		formato14B.f_utilMatOficR.addClass("fise-editable");
	},
	estiloEdicionProvincia : function(){
		formato14B.f_impValEdeP.addClass("fise-editable");
		formato14B.f_impValNoEdeP.addClass("fise-editable");
		formato14B.f_nroValesImpP.addClass("fise-editable");
		formato14B.f_costoTotalValP.addClass("fise-editable");
		formato14B.f_nroValesReptP.addClass("fise-editable");
		formato14B.f_costoTotalValOficP.addClass("fise-editable");
		formato14B.f_nroValesEntrP.addClass("fise-editable");
		formato14B.f_costoEnvPadronP.addClass("fise-editable");
		formato14B.f_nroValesFisP.addClass("fise-editable");
		formato14B.f_costoUnitDigitP.addClass("fise-editable");
		formato14B.f_costoAtenSolicP.addClass("fise-editable");
		formato14B.f_costoAtenConsP.addClass("fise-editable");
		formato14B.f_nroTotalAtenP.addClass("fise-editable");
		formato14B.f_costoPersonalP.addClass("fise-editable");
		formato14B.f_capacAgentP.addClass("fise-editable");
		formato14B.f_utilMatOficP.addClass("fise-editable");
	},
	estiloEdicionLima : function(){
		formato14B.f_impValEdeL.addClass("fise-editable");
		formato14B.f_impValNoEdeL.addClass("fise-editable");
		formato14B.f_nroValesImpL.addClass("fise-editable");
		formato14B.f_costoTotalValL.addClass("fise-editable");
		formato14B.f_nroValesReptL.addClass("fise-editable");
		formato14B.f_costoTotalValOficL.addClass("fise-editable");
		formato14B.f_nroValesEntrL.addClass("fise-editable");
		formato14B.f_costoEnvPadronL.addClass("fise-editable");
		formato14B.f_nroValesFisL.addClass("fise-editable");
		formato14B.f_costoUnitDigitL.addClass("fise-editable");
		formato14B.f_costoAtenSolicL.addClass("fise-editable");
		formato14B.f_costoAtenConsL.addClass("fise-editable");
		formato14B.f_nroTotalAtenL.addClass("fise-editable");
		formato14B.f_costoPersonalL.addClass("fise-editable");
		formato14B.f_capacAgentL.addClass("fise-editable");
		formato14B.f_utilMatOficL.addClass("fise-editable");
	},
	//quitar estilos
	quitarEstiloEdicionCabecera : function(){
		$('#anioInicioVigencia').removeClass("fise-editable");
		$('#anioFinVigencia').removeClass("fise-editable");
	},
	quitarEstiloEdicionRural : function(){
		formato14B.f_impValEdeR.removeClass("fise-editable");
		formato14B.f_impValNoEdeR.removeClass("fise-editable");
		formato14B.f_nroValesImpR.removeClass("fise-editable");
		formato14B.f_costoTotalValR.removeClass("fise-editable");
		formato14B.f_nroValesReptR.removeClass("fise-editable");
		formato14B.f_costoTotalValOficR.removeClass("fise-editable");
		formato14B.f_nroValesEntrR.removeClass("fise-editable");
		formato14B.f_costoEnvPadronR.removeClass("fise-editable");
		formato14B.f_nroValesFisR.removeClass("fise-editable");
		formato14B.f_costoUnitDigitR.removeClass("fise-editable");
		formato14B.f_costoAtenSolicR.removeClass("fise-editable");
		formato14B.f_costoAtenConsR.removeClass("fise-editable");
		formato14B.f_nroTotalAtenR.removeClass("fise-editable");
		formato14B.f_costoPersonalR.removeClass("fise-editable");
		formato14B.f_capacAgentR.removeClass("fise-editable");
		formato14B.f_utilMatOficR.removeClass("fise-editable");
	},
	quitarEstiloEdicionProvincia : function(){
		formato14B.f_impValEdeP.removeClass("fise-editable");
		formato14B.f_impValNoEdeP.removeClass("fise-editable");
		formato14B.f_nroValesImpP.removeClass("fise-editable");
		formato14B.f_costoTotalValP.removeClass("fise-editable");
		formato14B.f_nroValesReptP.removeClass("fise-editable");
		formato14B.f_costoTotalValOficP.removeClass("fise-editable");
		formato14B.f_nroValesEntrP.removeClass("fise-editable");
		formato14B.f_costoEnvPadronP.removeClass("fise-editable");
		formato14B.f_nroValesFisP.removeClass("fise-editable");
		formato14B.f_costoUnitDigitP.removeClass("fise-editable");
		formato14B.f_costoAtenSolicP.removeClass("fise-editable");
		formato14B.f_costoAtenConsP.removeClass("fise-editable");
		formato14B.f_nroTotalAtenP.removeClass("fise-editable");
		formato14B.f_costoPersonalP.removeClass("fise-editable");
		formato14B.f_capacAgentP.removeClass("fise-editable");
		formato14B.f_utilMatOficP.removeClass("fise-editable");
	},
	quitarEstiloEdicionLima : function(){
		formato14B.f_impValEdeL.removeClass("fise-editable");
		formato14B.f_impValNoEdeL.removeClass("fise-editable");
		formato14B.f_nroValesImpL.removeClass("fise-editable");
		formato14B.f_costoTotalValL.removeClass("fise-editable");
		formato14B.f_nroValesReptL.removeClass("fise-editable");
		formato14B.f_costoTotalValOficL.removeClass("fise-editable");
		formato14B.f_nroValesEntrL.removeClass("fise-editable");
		formato14B.f_costoEnvPadronL.removeClass("fise-editable");
		formato14B.f_nroValesFisL.removeClass("fise-editable");
		formato14B.f_costoUnitDigitL.removeClass("fise-editable");
		formato14B.f_costoAtenSolicL.removeClass("fise-editable");
		formato14B.f_costoAtenConsL.removeClass("fise-editable");
		formato14B.f_nroTotalAtenL.removeClass("fise-editable");
		formato14B.f_costoPersonalL.removeClass("fise-editable");
		formato14B.f_capacAgentL.removeClass("fise-editable");
		formato14B.f_utilMatOficL.removeClass("fise-editable");
	},
	//
	blockUI : function(){
		$.blockUI({ message: '<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Cargando </h3>' });
	},
	unblockUI : function(){
		$.unblockUI();
	},
	initBlockUI : function(){
		$(document).ajaxStop($.unblockUI); 		
	},
	initDialogs : function(){	
		formato14B.dialogMessage.dialog({
			modal: true,
			autoOpen: false,
			buttons: {
				Ok: function() {
					$( this ).dialog("close");
				}
			}
		});
		formato14B.dialogMessageReport.dialog({
			modal: true,
			autoOpen: false,
			buttons: {
				'Ver Acta': function() {
					formato14B.<portlet:namespace/>mostrarReporteEnvioDefinitivo();
					$( this ).dialog("close");
					formato14B.divFormato.hide();
					formato14B.divHome.show();
					formato14B.botonBuscar.trigger('click');
				},
				Ok: function() {
					$( this ).dialog("close");
					formato14B.divFormato.hide();
					formato14B.divHome.show();
					formato14B.botonBuscar.trigger('click');
				}
			},
			close: function(event,ui){
				formato14B.divFormato.hide();
				formato14B.divHome.show();
				formato14B.botonBuscar.trigger('click');
	       	}
		});
		formato14B.dialogConfirm.dialog({
			modal: true,
			height: 200,
			width: 400,			
			autoOpen: false,
			buttons: {
				"Si": function() {
					formato14B.eliminarFormato(cod_Empresa,ano_Presentacion,mes_Presentacion,ano_Inicio_Vigencia,ano_Fin_Vigencia,cod_Etapa);
					$( this ).dialog("close");
				},
				"No": function() {				
					$( this ).dialog("close");
				}
			}
		});
		formato14B.dialogConfirmEnvio.dialog({
			modal: true,
			height: 200,
			width: 400,			
			autoOpen: false,
			buttons: {
				"Si": function() {
					formato14B.<portlet:namespace/>envioDefinitivo();
					$( this ).dialog("close");
				},
				"No": function() {				
					$( this ).dialog("close");
				}
			}
		});
		formato14B.dialogCarga.dialog({
			autoOpen: false,
			height: 240,
			width: 400,
			modal: true,
			buttons: {
				"Aceptar": function() {
					$( this ).dialog("close");					  				
				},
				"Cerrar": function() {
					$( this ).dialog("close");
				}
			},
			close: function() {
			}
		});
		formato14B.dialogError.dialog({
			modal: true,
			width: 700,
			autoOpen: false,
			buttons: {
				Ok: function() {
					$( this ).dialog("close");
				}
			}
		});
		formato14B.dialogObservacion.dialog({
			modal: true,
			width: 700,
			autoOpen: false,
			buttons: {
				Cerrar: function() {
					$( this ).dialog("close");
				}
			}
		});
	}
};
</script>