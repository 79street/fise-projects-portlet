<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@page import="com.liferay.portal.kernel.util.PrefsPropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects />

<script type="text/javascript">
var formato14A= {
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
	//adds messages
	dialogMessageInfo:null,
	dialogMessageInfoContent:null,
	dialogMessageWarning:null,
	dialogMessageWarningContent:null,
	dialogMessageError:null,
	dialogMessageErrorContent:null,
	
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
	f_sumEmpadDifR:null,f_totalEmpadR:null,f_impEsqInvR:null,f_impDeclJuradaR:null,f_impFichaVerifR:null,f_repEsqInvR:null,f_verifInfoR:null,f_elabArchBenefR:null,f_digitFichaR:null,
	f_totalDifIniProgR:null,f_impVolanteR:	null,f_impAficheR:null,f_repFolletoR:null,f_spotPublicTvR:null,f_spotPublicRadioR:null,f_nroBenefR:null,f_costoUnitEmpadR:null,
	f_totalAgentR:null,f_promConvR:null,f_regFirmaConvR:null,f_impBandR:null,f_nroAgentR:null,f_costoUnitAgentR:null,
	//PROVINCIA
	f_sumEmpadDifP:null,f_totalEmpadP:null,f_impEsqInvP:null,f_impDeclJuradaP:null,f_impFichaVerifP:null,f_repEsqInvP:null,f_verifInfoP:null,f_elabArchBenefP:null,f_digitFichaP:null,
	f_totalDifIniProgP:null,f_impVolanteP:	null,f_impAficheP:null,f_repFolletoP:null,f_spotPublicTvP:null,f_spotPublicRadioP:null,f_nroBenefP:null,f_costoUnitEmpadP:null,
	f_totalAgentP:null,f_promConvP:null,f_regFirmaConvP:null,f_impBandP:null,f_nroAgentP:null,f_costoUnitAgentP:null,
	//LMA
	f_sumEmpadDifL:null,f_totalEmpadL:null,f_impEsqInvL:null,f_impDeclJuradaL:null,f_impFichaVerifL:null,f_repEsqInvL:null,f_verifInfoL:null,f_elabArchBenefL:null,f_digitFichaL:null,
	f_totalDifIniProgL:null,f_impVolanteL:	null,f_impAficheL:null,f_repFolletoL:null,f_spotPublicTvL:null,f_spotPublicRadioL:null,f_nroBenefL:null,f_costoUnitEmpadL:null,
	f_totalAgentL:null,f_promConvL:null,f_regFirmaConvL:null,f_impBandL:null,f_nroAgentL:null,f_costoUnitAgentL:null,
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
		
		this.emailConfigured='<%=PrefsPropsUtil.getString(PropsKeys.MAIL_SESSION_MAIL_SMTP_USER)%>';
		
		//valores constantes para edelnor y luz del sur
		this.cod_empresa_edelnor=$("#codEdelnor");
		this.cod_empresa_luz_sur=$("#codLuzSur");
		//
		this.formCommand=$('#formato14ACBean');
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
		
		//add message
		this.dialogMessageInfo=$("#<portlet:namespace/>dialog-message-info");
		this.dialogMessageInfoContent=$("#<portlet:namespace/>dialog-message-info-content");
		this.dialogMessageWarning=$("#<portlet:namespace/>dialog-message-warning");
		this.dialogMessageWarningContent=$("#<portlet:namespace/>dialog-message-warning-content");
		this.dialogMessageError=$("#<portlet:namespace/>dialog-message-error");
		this.dialogMessageErrorContent=$("#<portlet:namespace/>dialog-message-error-content");
		
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
		this.f_sumEmpadDifR=$('#sumEmpadDifusionR');
		this.f_totalEmpadR=$('#totalEmpadR');
		this.f_impEsqInvR=$('#imprEsqInvitR');
		this.f_impDeclJuradaR=$('#imprDeclaJuradaR');
		this.f_impFichaVerifR=$('#imprFichaVerifR');
		this.f_repEsqInvR=$('#repartoEsqInvitR');
		this.f_verifInfoR=$('#verifInfoR');
		this.f_elabArchBenefR=$('#elabArchivoBenefR');
		this.f_digitFichaR=$('#digitFichaBenefR');
		this.f_totalDifIniProgR=$('#totalDifIniProgR');
		this.f_impVolanteR=$('#imprVolantesR');
		this.f_impAficheR=$('#imprAfichesR');
		this.f_repFolletoR=$('#repFolletosR');
		this.f_spotPublicTvR=$('#spotPublTvR');
		this.f_spotPublicRadioR=$('#spotPublRadioR');
		this.f_nroBenefR=$('#nroBenefEmpadR');
		this.f_costoUnitEmpadR=$('#costoUnitEmpadR');
		this.f_totalAgentR=$('#totalCostoAgentR');
		this.f_promConvR=$('#promConvAgentR');
		this.f_regFirmaConvR=$('#regConvAgentR');
		this.f_impBandR=$('#impEntrBandR');
		this.f_nroAgentR=$('#nroAgentR');
		this.f_costoUnitAgentR=$('#costoUnitAgentR');
		//
		this.f_sumEmpadDifP=$('#sumEmpadDifusionP');
		this.f_totalEmpadP=$('#totalEmpadP');
		this.f_impEsqInvP=$('#imprEsqInvitP');
		this.f_impDeclJuradaP=$('#imprDeclaJuradaP');
		this.f_impFichaVerifP=$('#imprFichaVerifP');
		this.f_repEsqInvP=$('#repartoEsqInvitP');
		this.f_verifInfoP=$('#verifInfoP');
		this.f_elabArchBenefP=$('#elabArchivoBenefP');
		this.f_digitFichaP=$('#digitFichaBenefP');
		this.f_totalDifIniProgP=$('#totalDifIniProgP');
		this.f_impVolanteP=$('#imprVolantesP');
		this.f_impAficheP=$('#imprAfichesP');
		this.f_repFolletoP=$('#repFolletosP');
		this.f_spotPublicTvP=$('#spotPublTvP');
		this.f_spotPublicRadioP=$('#spotPublRadioP');
		this.f_nroBenefP=$('#nroBenefEmpadP');
		this.f_costoUnitEmpadP=$('#costoUnitEmpadP');
		this.f_totalAgentP=$('#totalCostoAgentP');
		this.f_promConvP=$('#promConvAgentP');
		this.f_regFirmaConvP=$('#regConvAgentP');
		this.f_impBandP=$('#impEntrBandP');
		this.f_nroAgentP=$('#nroAgentP');
		this.f_costoUnitAgentP=$('#costoUnitAgentP');
		//
		this.f_sumEmpadDifL=$('#sumEmpadDifusionL');
		this.f_totalEmpadL=$('#totalEmpadL');
		this.f_impEsqInvL=$('#imprEsqInvitL');
		this.f_impDeclJuradaL=$('#imprDeclaJuradaL');
		this.f_impFichaVerifL=$('#imprFichaVerifL');
		this.f_repEsqInvL=$('#repartoEsqInvitL');
		this.f_verifInfoL=$('#verifInfoL');
		this.f_elabArchBenefL=$('#elabArchivoBenefL');
		this.f_digitFichaL=$('#digitFichaBenefL');
		this.f_totalDifIniProgL=$('#totalDifIniProgL');
		this.f_impVolanteL=$('#imprVolantesL');
		this.f_impAficheL=$('#imprAfichesL');
		this.f_repFolletoL=$('#repFolletosL');
		this.f_spotPublicTvL=$('#spotPublTvL');
		this.f_spotPublicRadioL=$('#spotPublRadioL');
		this.f_nroBenefL=$('#nroBenefEmpadL');
		this.f_costoUnitEmpadL=$('#costoUnitEmpadL');
		this.f_totalAgentL=$('#totalCostoAgentL');
		this.f_promConvL=$('#promConvAgentL');
		this.f_regFirmaConvL=$('#regConvAgentL');
		this.f_impBandL=$('#impEntrBandL');
		this.f_nroAgentL=$('#nroAgentL');
		this.f_costoUnitAgentL=$('#costoUnitAgentL');
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
		formato14A.botonNuevo.click(function() {formato14A.<portlet:namespace/>crearFormato();});
		formato14A.botonBuscar.click(function() {formato14A.buscar();});
		formato14A.botonGrabar.click(function() {
			if(formato14A.validarGrupoInformacion()){
				if(formato14A.validarUltimaEtapaF14A()){
					formato14A.<portlet:namespace/>guardarFormato();
				}				
			}
		});
		formato14A.botonRegresar.click(function() {formato14A.<portlet:namespace/>regresar();});
		formato14A.f_empresa.change(function(){formato14A.<portlet:namespace/>loadPeriodo('');});
		formato14A.f_periodoEnvio.change(function(){formato14A.<portlet:namespace/>loadPeriodo(this.value);});
		//cargaarchivos
		formato14A.botonCargaExcel.click(function() {
			if(formato14A.validarGrupoInformacion() ){
				if(formato14A.validarUltimaEtapaF14A()){
					formato14A.<portlet:namespace/>mostrarFormularioCargaExcel();
				}				
			}
		});
		formato14A.botonCargaTxt.click(function() {
			if( formato14A.validarGrupoInformacion() ){
				if(formato14A.validarUltimaEtapaF14A()){
					formato14A.<portlet:namespace/>mostrarFormularioCargaTexto();	
				}				
			}
		});
		formato14A.botonCargarFormatoExcel.click(function() {formato14A.<portlet:namespace/>cargarFormatoExcel();});
		formato14A.botonCargarFormatoTexto.click(function() {formato14A.<portlet:namespace/>cargarFormatoTexto();});
		//
		formato14A.botonReportePdf.click(function() {formato14A.<portlet:namespace/>mostrarReportePdf();});
		formato14A.botonReporteExcel.click(function() {formato14A.<portlet:namespace/>mostrarReporteExcel();});
		formato14A.botonValidacion.click(function() {formato14A.<portlet:namespace/>validacionFormato();});
		formato14A.botonEnvioDefinitivo.click(function() {formato14A.confirmarEnvioDefinitivo();});
		//
		formato14A.botonActaEnvio.click(function() {formato14A.<portlet:namespace/>mostrarReporteActaEnvio();});
		
		formato14A.initDialogs();
		formato14A.cargaInicial();
		//eventos por defecto
		formato14A.botonBuscar.trigger('click');
		formato14A.initBlockUI();
		//$('input[type=text].target').on('change', function(){
		$('input.target[type=text]').on('change', function(){
			formato14A.calculoTotal();
		});
	},
	cargaInicial : function(){
		//SE CARGA VARIABLES EN SESION PARA MOSTRAR LOS PANELES DE NUEVO O EDICION MANEJADOS
		 var codEmpSes = formato14A.codEmpresaSes.val();
		 var anioPresSes = formato14A.anioPresSes.val();
		 var mesPresSes = formato14A.mesPresSes.val();
		 var anioIniVigSes = formato14A.anioIniVigSes.val();
		 var anioFinVigSes = formato14A.anioFinVigSes.val();
		 var etapaSes = formato14A.etapaSes.val();
		 var flagOpera = 'ABIERTO';
		 //
		 
		 var flag = formato14A.flag.val();
		 //alert('hola mundo '+flag);
		 if( flag=='N' ){//solo ocurre cuando hay un error en la carga de formularios, sino se muestra el proceso normal
			 formato14A.inicializarFormulario();
			 formato14A.mostrarUltimoFormato();
			 formato14A.f_empresa.val(codEmpSes);
			 if( formato14A.f_periodoEnvio.val()=='S' ){
				 $("#anioInicioVigencia").val(anioIniVigSes);
				 $("#anioFinVigencia").val(anioFinVigSes);
			}else{
				$("#anioInicioVigencia").val(anioPresSes);
				 $("#anioFinVigencia").val(anioPresSes);
			}
			formato14A.botonValidacion.css('display','none');
			formato14A.botonEnvioDefinitivo.css('display','none');
			formato14A.<portlet:namespace/>loadPeriodo(anioPresSes+completarCerosIzq(mesPresSes,2)+etapaSes);
		}else{
			//alert(codEmpSes+','+anioPresSes+','+mesPresSes+','+anioIniVigSes+','+anioFinVigSes+','+etapaSes);
			 if(codEmpSes != '' && anioPresSes != '' && mesPresSes != '' && anioIniVigSes != '' && anioFinVigSes != '' && etapaSes != ''){
				 formato14A.editarFormato(codEmpSes, anioPresSes, mesPresSes, anioIniVigSes, anioFinVigSes, etapaSes,flagOpera);
			 }
		 }
		 //SE CARGA VALORES POR DEFECTO PARA LA BUSQUEDA
		 /*formato14A.i_anioDesde.val(formato14A.anioDesdeSes.val());
		 formato14A.i_mesDesde.val(formato14A.mesDesdeSes.val());
		 formato14A.i_anioHasta.val(formato14A.anioHastaSes.val());
		 formato14A.i_mesHasta.val(formato14A.mesHastaSes.val());
		 formato14A.i_etapaB.val(formato14A.codEtapaSes.val());*/
		 
		 var mensajeInfo = formato14A.mensajeInfo.val();
		 var mensajeError = formato14A.mensajeError.val();
		 //SE MUESTRAN LOS MENSAJES DE ERROR PARA LA CARGA DE LOS ARCHIVOS
		 if(mensajeError!=''){
			 //se muestra el panel de errores si se produce en la carga de archivos
			formato14A.dialogError.dialog("open");
		}else{
			//Se muestra el mensaje de informacion exitosa
			 if(mensajeInfo!=''){
				formato14A.dialogMessageContent.html(mensajeInfo);
				formato14A.dialogMessage.dialog("open");			
			 }
		 }
		 //limpiar variables
		 formato14A.codEmpresaSes.val('');
		 formato14A.anioPresSes.val('');
		 formato14A.mesPresSes.val('');
		 formato14A.anioIniVigSes.val('');
		 formato14A.anioFinVigSes.val('');
		 formato14A.etapaSes.val('');
	},
	buildGrids : function () {	
		formato14A.tablaResultados.jqGrid({
		   datatype: "local",
	       colNames: ['Dist. El�ct.','A�o Decl.','Mes Decl.','A�o Ini. Vig.','A�o Fin Vig.','Grupo de Informaci�n','Estado','Ver','Editar','Eliminar','','','',''],
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
				height: 225,
			   	autowidth: true,
				rownumbers: true,
				shrinkToFit:true,
				pager: formato14A.paginadoResultados,
			    viewrecords: true,
			   	caption: "Resultado(s) de la b�squeda",
			    sortorder: "asc",	   	    	   	   
	       gridComplete: function(){
	      		var ids = formato14A.tablaResultados.jqGrid('getDataIDs');
	      		for(var i=0;i < ids.length;i++){
	      			var cl = ids[i];
	      			var ret = formato14A.tablaResultados.jqGrid('getRowData',cl);           
	      			view = "<a href='#'><img border='0' title='Ver' src='/net-theme/images/img-net/file.png'  align='center' onclick=\"formato14A.verFormato('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoIniVigencia+"','"+ret.anoFinVigencia+"','"+ret.etapa+"');\" /></a> ";
	      			edit = "<a href='#'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png'  align='center' onclick=\"formato14A.editarFormato('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoIniVigencia+"','"+ret.anoFinVigencia+"','"+ret.etapa+"','"+ret.flagOperacion+"');\" /></a> ";
	      			elem = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"formato14A.confirmarEliminar('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoIniVigencia+"','"+ret.anoFinVigencia+"','"+ret.etapa+"','"+ret.flagOperacion+"');\" /></a> ";              			
	      			formato14A.tablaResultados.jqGrid('setRowData',ids[i],{view:view});
	      			formato14A.tablaResultados.jqGrid('setRowData',ids[i],{edit:edit});
	      			formato14A.tablaResultados.jqGrid('setRowData',ids[i],{elim:elem});
	      		}
	      }
	  	});
		formato14A.tablaResultados.jqGrid('navGrid',formato14A.paginadoResultados,{add:false,edit:false,del:false,search: false,refresh: false});	
		formato14A.tablaResultados.jqGrid('navButtonAdd',formato14A.paginadoResultados,{
		       caption:"Exportar a Excel",
		       buttonicon: "ui-icon-bookmark",
		       onClickButton : function () {
		    	   var ids = formato14A.tablaResultados.jqGrid('getDataIDs');
			       if(ids!=0){
			    	   location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';   
			       }else{			    	
			    	formato14A.dialogMessageInfoContent.html('No existe informaci�n para exportar a Excel');
					formato14A.dialogMessageInfo.dialog("open");
			       }    	   
		       } 
		}); 
	},
	buscar : function () {
		if(formato14A.validarBusqueda()){
			formato14A.blockUI();
			jQuery.ajax({			
					url: formato14A.urlBusqueda+'&'+formato14A.formCommand.serialize(),
					type: 'post',
					dataType: 'json',				
					success: function(gridData) {					
						formato14A.tablaResultados.clearGridData(true);
						formato14A.tablaResultados.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
						formato14A.tablaResultados[0].refreshIndex();
						formato14A.initBlockUI();
					},error : function(){
						alert("Error de conexi�n.");
						formato14A.initBlockUI();
					}
			});
		}
	},
	validarBusqueda : function(){
		if(formato14A.i_anioDesde.val().length != '' ) {		  
			  var numstr = trim(formato14A.i_anioDesde.val());
			  if (isNaN(numstr) || numstr.length<4 || parseFloat(numstr)<1900){
				  //alert('Ingrese un a�o desde v�lido');
				  formato14A.dialogMessageWarningContent.html('Debe ingresar un A�o Declarado Desde v�lido');
				  formato14A.dialogMessageWarning.dialog("open");
				 // formato14A.i_anioDesde.focus();
				  cod_focus=formato14A.i_anioDesde;
				  return false;
			  }
		  }else if( formato14A.i_anioDesde.val() == '' ){
			  formato14A.dialogMessageWarningContent.html('Debe ingresar un A�o Declarado Desde');
			  formato14A.dialogMessageWarning.dialog("open");
			  //formato14A.i_anioDesde.focus();
			  cod_focus=formato14A.i_anioDesde;
			  return false;
		  }
		  if(formato14A.i_anioHasta.val().length != '' ) {		  
			  var numstr = trim(formato14A.i_anioHasta.val());
			  if (isNaN(numstr) || numstr.length<4 || parseFloat(numstr)<1900){
				  //alert('Ingrese un a�o hasta v�lido');
				  formato14A.dialogMessageWarningContent.html('Debe ingresar un A�o Declarado Hasta v�lido');
				  formato14A.dialogMessageWarning.dialog("open");
				  //formato14A.i_anioHasta.focus();
				  cod_focus=formato14A.i_anioHasta;
				  return false;
			  }
		  }else if( formato14A.i_anioHasta.val() == '' ){
			  formato14A.dialogMessageWarningContent.html('Debe ingresar un A�o Declarado Hasta');
			  formato14A.dialogMessageWarning.dialog("open");
			  //formato14A.i_anioHasta.focus();
			  cod_focus=formato14A.i_anioHasta;
			  return false;
		  }
		  if(formato14A.i_anioDesde.val().length != '' && formato14A.i_anioHasta.val().length != '' ) {
			  if( parseFloat(formato14A.i_anioDesde.val()) > parseFloat(formato14A.i_anioHasta.val()) ){
					//alert('El a�o desde no puede exceder al a�o hasta');
					formato14A.dialogMessageWarningContent.html('El A�o Declarado Desde no puede exceder al A�o Declarado Hasta');
					formato14A.dialogMessageWarning.dialog("open");
					cod_focus=formato14A.i_anioDesde;
					return false;
			  }
		  }
		  if(formato14A.i_etapaB.val().length == '' ) { 	    
			    //alert('Seleccione una etapa');
			    formato14A.dialogMessageWarningContent.html('Debe seleccionar una Etapa');
				formato14A.dialogMessageWarning.dialog("open");
			   // formato14A.i_etapaB.focus();
			    cod_focus=formato14A.i_etapaB;
			    return false; 
		  }
		 return true; 
	},
	<portlet:namespace/>crearFormato :  function(){	
		formato14A.inicializarFormulario();
		formato14A.procesoEstado.val('SAVE');
		formato14A.etapaEdit.val("");
		formato14A.divFormato.show();
		formato14A.divHome.hide();
		formato14A.flagCarga.val('0');
		//
		/*if( formato14A.f_flagPeriodo.val()=='S' ){
			//poner valores guardadose en sesion
			$("#anioInicioVigencia").val(formato14A.i_anioDesde.val());
			$("#anioFinVigencia").val(formato14A.i_anioDesde.val());
		}*/
		$('#<portlet:namespace/>guardarFormato').val('Grabar');
		
		formato14A.botonValidacion.css('display','none');
		formato14A.botonEnvioDefinitivo.css('display','none');
		formato14A.<portlet:namespace/>loadPeriodo('');
		
		formato14A.estiloEdicionRural();
		formato14A.estiloEdicionProvincia();
	},
	<portlet:namespace/>regresar : function(){
		formato14A.procesoEstado.val('');
		formato14A.etapaEdit.val("");
		formato14A.divFormato.hide();
		formato14A.divHome.show();		
		//
		formato14A.removerDeshabilitados();
		//se visualizan los componentes escondidos
		formato14A.botonReportePdf.css('display','none');
		formato14A.botonReporteExcel.css('display','none');
		//
		formato14A.botonActaEnvio.css('display','none');
		//
		formato14A.botonGrabar.css('display','');
		formato14A.botonValidacion.css('display','');
		formato14A.botonEnvioDefinitivo.css('display','');
		
		formato14A.panelCargaArchivo.css('display','');
		
		//limpiamos los anios de vigencia
		$('#anioInicioVigencia').val('0');
		$('#anioFinVigencia').val('0');

		formato14A.botonBuscar.trigger('click');
	},
	inicializarFormulario : function(){
		
		formato14A.divInformacion.hide();
		
		if(formato14A.i_codEmpresaB.val()!=''){
			formato14A.f_empresa.val(formato14A.i_codEmpresaB.val());
		}else{
			formato14A.f_empresa.val('');
		}	
		
		
		/*if( formato14A.f_flagPeriodo.val()=='S' ){
			$('#anioInicioVigencia').val('');
			$('#anioFinVigencia').val('');
			$('#anioInicioVigencia').attr("disabled",false);
			$('#anioFinVigencia').attr("disabled",false);
		}*/
		
		//validar lima edelnor y luz del sur
		if(formato14A.cod_empresa_edelnor.val()==formato14A.f_empresa.val() || formato14A.cod_empresa_luz_sur.val()==formato14A.f_empresa.val()){
			formato14A.habilitarLima();										
		}else{
			formato14A.deshabilitarLima();
		}
		
		//RURAL
		formato14A.f_sumEmpadDifR.val('0.00');
		formato14A.f_totalEmpadR.val('0.00');
		formato14A.f_impEsqInvR.val('0.00');
		formato14A.f_impDeclJuradaR.val('0.00');
		formato14A.f_impFichaVerifR.val('0.00');
		formato14A.f_repEsqInvR.val('0.00');
		formato14A.f_verifInfoR.val('0.00');
		formato14A.f_elabArchBenefR.val('0.00');
		formato14A.f_digitFichaR.val('0.00');
		formato14A.f_totalDifIniProgR.val('0.00');
		formato14A.f_impVolanteR.val('0.00');
		formato14A.f_impAficheR.val('0.00');
		formato14A.f_repFolletoR.val('0.00');
		formato14A.f_spotPublicTvR.val('0.00');
		formato14A.f_spotPublicRadioR.val('0.00');
		formato14A.f_nroBenefR.val('0');
		formato14A.f_costoUnitEmpadR.val('0.00');
		formato14A.f_totalAgentR.val('0.00');
		formato14A.f_promConvR.val('0.00');
		formato14A.f_regFirmaConvR.val('0.00');
		formato14A.f_impBandR.val('0.00');
		formato14A.f_nroAgentR.val('0');
		formato14A.f_costoUnitAgentR.val('0.00');
		//PROVINCIA
		formato14A.f_sumEmpadDifP.val('0.00');
		formato14A.f_totalEmpadP.val('0.00');
		formato14A.f_impEsqInvP.val('0.00');
		formato14A.f_impDeclJuradaP.val('0.00');
		formato14A.f_impFichaVerifP.val('0.00');
		formato14A.f_repEsqInvP.val('0.00');
		formato14A.f_verifInfoP.val('0.00');
		formato14A.f_elabArchBenefP.val('0.00');
		formato14A.f_digitFichaP.val('0.00');
		formato14A.f_totalDifIniProgP.val('0.00');
		formato14A.f_impVolanteP.val('0.00');
		formato14A.f_impAficheP.val('0.00');
		formato14A.f_repFolletoP.val('0.00');
		formato14A.f_spotPublicTvP.val('0.00');
		formato14A.f_spotPublicRadioP.val('0.00');
		formato14A.f_nroBenefP.val('0');
		formato14A.f_costoUnitEmpadP.val('0.00');
		formato14A.f_totalAgentP.val('0.00');
		formato14A.f_promConvP.val('0.00');
		formato14A.f_regFirmaConvP.val('0.00');
		formato14A.f_impBandP.val('0.00');
		formato14A.f_nroAgentP.val('0');
		formato14A.f_costoUnitAgentP.val('0.00');
		//LIMA
		formato14A.f_sumEmpadDifL.val('0.00');
		formato14A.f_totalEmpadL.val('0.00');
		formato14A.f_impEsqInvL.val('0.00');
		formato14A.f_impDeclJuradaL.val('0.00');
		formato14A.f_impFichaVerifL.val('0.00');
		formato14A.f_repEsqInvL.val('0.00');
		formato14A.f_verifInfoL.val('0.00');
		formato14A.f_elabArchBenefL.val('0.00');
		formato14A.f_digitFichaL.val('0.00');
		formato14A.f_totalDifIniProgL.val('0.00');
		formato14A.f_impVolanteL.val('0.00');
		formato14A.f_impAficheL.val('0.00');
		formato14A.f_repFolletoL.val('0.00');
		formato14A.f_spotPublicTvL.val('0.00');
		formato14A.f_spotPublicRadioL.val('0.00');
		formato14A.f_nroBenefL.val('0');
		formato14A.f_costoUnitEmpadL.val('0.00');
		formato14A.f_totalAgentL.val('0.00');
		formato14A.f_promConvL.val('0.00');
		formato14A.f_regFirmaConvL.val('0.00');
		formato14A.f_impBandL.val('0.00');
		formato14A.f_nroAgentL.val('0');
		formato14A.f_costoUnitAgentL.val('0.00');
		
		//quitamos los componentes deshabilitados
		formato14A.f_empresa.attr("disabled",false);
		formato14A.f_periodoEnvio.attr("disabled",false);
		//
		//formato14A.deshabilitarCampos();
		//
		formato14A.soloNumerosEnteros();
		formato14A.soloNumerosDecimales();
	},
	removerDeshabilitados : function(){
		formato14A.f_empresa.removeAttr("disabled");
		formato14A.f_periodoEnvio.removeAttr("disabled");
		//if( formato14A.f_flagPeriodo.val()=='S' ){
		$('#anioInicioVigencia').removeAttr("disabled");
		$('#anioFinVigencia').removeAttr("disabled");
		//}
		//RURAL
		formato14A.f_impEsqInvR.removeAttr("disabled");
		formato14A.f_impDeclJuradaR.removeAttr("disabled");
		formato14A.f_impFichaVerifR.removeAttr("disabled");
		formato14A.f_repEsqInvR.removeAttr("disabled");
		formato14A.f_verifInfoR.removeAttr("disabled");
		formato14A.f_elabArchBenefR.removeAttr("disabled");
		formato14A.f_digitFichaR.removeAttr("disabled");
		formato14A.f_impVolanteR.removeAttr("disabled");
		formato14A.f_impAficheR.removeAttr("disabled");
		formato14A.f_repFolletoR.removeAttr("disabled");
		formato14A.f_spotPublicTvR.removeAttr("disabled");
		formato14A.f_spotPublicRadioR.removeAttr("disabled");
		formato14A.f_nroBenefR.removeAttr("disabled");
		formato14A.f_promConvR.removeAttr("disabled");
		formato14A.f_regFirmaConvR.removeAttr("disabled");
		formato14A.f_impBandR.removeAttr("disabled");
		formato14A.f_nroAgentR.removeAttr("disabled");
		//PROVINCIA
		formato14A.f_impEsqInvP.removeAttr("disabled");
		formato14A.f_impDeclJuradaP.removeAttr("disabled");
		formato14A.f_impFichaVerifP.removeAttr("disabled");
		formato14A.f_repEsqInvP.removeAttr("disabled");
		formato14A.f_verifInfoP.removeAttr("disabled");
		formato14A.f_elabArchBenefP.removeAttr("disabled");
		formato14A.f_digitFichaP.removeAttr("disabled");
		formato14A.f_impVolanteP.removeAttr("disabled");
		formato14A.f_impAficheP.removeAttr("disabled");
		formato14A.f_repFolletoP.removeAttr("disabled");
		formato14A.f_spotPublicTvP.removeAttr("disabled");
		formato14A.f_spotPublicRadioP.removeAttr("disabled");
		formato14A.f_nroBenefP.removeAttr("disabled");
		formato14A.f_promConvP.removeAttr("disabled");
		formato14A.f_regFirmaConvP.removeAttr("disabled");
		formato14A.f_impBandP.removeAttr("disabled");
		formato14A.f_nroAgentP.removeAttr("disabled");
		//LIMA
		formato14A.f_impEsqInvL.removeAttr("disabled");
		formato14A.f_impDeclJuradaL.removeAttr("disabled");
		formato14A.f_impFichaVerifL.removeAttr("disabled");
		formato14A.f_repEsqInvL.removeAttr("disabled");
		formato14A.f_verifInfoL.removeAttr("disabled");
		formato14A.f_elabArchBenefL.removeAttr("disabled");
		formato14A.f_digitFichaL.removeAttr("disabled");
		formato14A.f_impVolanteL.removeAttr("disabled");
		formato14A.f_impAficheL.removeAttr("disabled");
		formato14A.f_repFolletoL.removeAttr("disabled");
		formato14A.f_spotPublicTvL.removeAttr("disabled");
		formato14A.f_spotPublicRadioL.removeAttr("disabled");
		formato14A.f_nroBenefL.removeAttr("disabled");
		formato14A.f_promConvL.removeAttr("disabled");
		formato14A.f_regFirmaConvL.removeAttr("disabled");
		formato14A.f_impBandL.removeAttr("disabled");
		formato14A.f_nroAgentL.removeAttr("disabled");
	},
	deshabilitarCamposView : function(){
		$('#anioInicioVigencia').attr("disabled",true);
		$('#anioFinVigencia').attr("disabled",true);
		//RURAL
		formato14A.f_impEsqInvR.attr("disabled",true);
		formato14A.f_impDeclJuradaR.attr("disabled",true);
		formato14A.f_impFichaVerifR.attr("disabled",true);
		formato14A.f_repEsqInvR.attr("disabled",true);
		formato14A.f_verifInfoR.attr("disabled",true);
		formato14A.f_elabArchBenefR.attr("disabled",true);
		formato14A.f_digitFichaR.attr("disabled",true);
		formato14A.f_impVolanteR.attr("disabled",true);
		formato14A.f_impAficheR.attr("disabled",true);
		formato14A.f_repFolletoR.attr("disabled",true);
		formato14A.f_spotPublicTvR.attr("disabled",true);
		formato14A.f_spotPublicRadioR.attr("disabled",true);
		formato14A.f_nroBenefR.attr("disabled",true);
		formato14A.f_promConvR.attr("disabled",true);
		formato14A.f_regFirmaConvR.attr("disabled",true);
		formato14A.f_impBandR.attr("disabled",true);
		formato14A.f_nroAgentR.attr("disabled",true);
		//PROVINCIA
		formato14A.f_impEsqInvP.attr("disabled",true);
		formato14A.f_impDeclJuradaP.attr("disabled",true);
		formato14A.f_impFichaVerifP.attr("disabled",true);
		formato14A.f_repEsqInvP.attr("disabled",true);
		formato14A.f_verifInfoP.attr("disabled",true);
		formato14A.f_elabArchBenefP.attr("disabled",true);
		formato14A.f_digitFichaP.attr("disabled",true);
		formato14A.f_impVolanteP.attr("disabled",true);
		formato14A.f_impAficheP.attr("disabled",true);
		formato14A.f_repFolletoP.attr("disabled",true);
		formato14A.f_spotPublicTvP.attr("disabled",true);
		formato14A.f_spotPublicRadioP.attr("disabled",true);
		formato14A.f_nroBenefP.attr("disabled",true);
		formato14A.f_promConvP.attr("disabled",true);
		formato14A.f_regFirmaConvP.attr("disabled",true);
		formato14A.f_impBandP.attr("disabled",true);
		formato14A.f_nroAgentP.attr("disabled",true);
		//LIMA
		formato14A.f_impEsqInvL.attr("disabled",true);
		formato14A.f_impDeclJuradaL.attr("disabled",true);
		formato14A.f_impFichaVerifL.attr("disabled",true);
		formato14A.f_repEsqInvL.attr("disabled",true);
		formato14A.f_verifInfoL.attr("disabled",true);
		formato14A.f_elabArchBenefL.attr("disabled",true);
		formato14A.f_digitFichaL.attr("disabled",true);
		formato14A.f_impVolanteL.attr("disabled",true);
		formato14A.f_impAficheL.attr("disabled",true);
		formato14A.f_repFolletoL.attr("disabled",true);
		formato14A.f_spotPublicTvL.attr("disabled",true);
		formato14A.f_spotPublicRadioL.attr("disabled",true);
		formato14A.f_nroBenefL.attr("disabled",true);
		formato14A.f_promConvL.attr("disabled",true);
		formato14A.f_regFirmaConvL.attr("disabled",true);
		formato14A.f_impBandL.attr("disabled",true);
		formato14A.f_nroAgentL.attr("disabled",true);
		//
		formato14A.botonReportePdf.css('display','');
		formato14A.botonReporteExcel.css('display','');
		//
		formato14A.botonActaEnvio.css('display','');
		//
		formato14A.botonGrabar.css('display','none');
		formato14A.botonValidacion.css('display','none');
		formato14A.botonEnvioDefinitivo.css('display','none');
		//
		formato14A.panelCargaArchivo.css('display','none');
		
		formato14A.quitarEstiloEdicionCabecera();
		formato14A.quitarEstiloEdicionRural();
		formato14A.quitarEstiloEdicionProvincia();
		formato14A.quitarEstiloEdicionLima();
	},
	//CALCULO RURAL
	sumaEmpadDifusionRural : function(){
		var totalEmpad;
		var totalDifusion;
		var sumaEmpadDifusion;
		totalEmpad=formato14A.f_totalEmpadR.val();
		totalDifusion=formato14A.f_totalDifIniProgR.val();
		sumaEmpadDifusion = parseFloat(totalEmpad)+parseFloat(totalDifusion);
		//redondeo a 2, depende de la logica de negocio
		sumaEmpadDifusion = redondeo(sumaEmpadDifusion, 2);
		formato14A.f_sumEmpadDifR.val(parseFloat(sumaEmpadDifusion));	
	},
	totalEmpadronamientoRural : function(){
		var impresionEsquela;
		var impresionDeclaJurada;
		var impresionFichas;
		var repartoEsquela;
		var verifInformacion;
		var elabArchivo;
		var digitacionFichas;
		var totalEmpadronamiento;
		impresionEsquela = formato14A.f_impEsqInvR.val();
		impresionDeclaJurada = formato14A.f_impDeclJuradaR.val();
		impresionFichas = formato14A.f_impFichaVerifR.val();
		repartoEsquela = formato14A.f_repEsqInvR.val();
		verifInformacion = formato14A.f_verifInfoR.val();
		elabArchivo = formato14A.f_elabArchBenefR.val();
		digitacionFichas = formato14A.f_digitFichaR.val();
		//
		totalEmpadronamiento = parseFloat(impresionEsquela)+parseFloat(impresionDeclaJurada)+parseFloat(impresionFichas)+parseFloat(repartoEsquela)
			+parseFloat(verifInformacion)+parseFloat(elabArchivo)+parseFloat(digitacionFichas);
		totalEmpadronamiento = redondeo(totalEmpadronamiento, 2);
		//alert(totalEmpadronamiento);
		formato14A.f_totalEmpadR.val(parseFloat(totalEmpadronamiento));
	},
	totalDifusionRural : function(){
		var impresionVolantes;
		var impresionAfiches;
		var repartoFolletos;
		var spotTv;
		var spotRadio;
		var totalDifusion;
		impresionVolantes = formato14A.f_impVolanteR.val();
		impresionAfiches = formato14A.f_impAficheR.val();
		repartoFolletos = formato14A.f_repFolletoR.val();
		spotTv = formato14A.f_spotPublicTvR.val();
		spotRadio = formato14A.f_spotPublicRadioR.val();
		//
		totalDifusion = parseFloat(impresionVolantes)+parseFloat(impresionAfiches)+parseFloat(repartoFolletos)+parseFloat(spotTv)+parseFloat(spotRadio);
		totalDifusion = redondeo(totalDifusion, 2);
		formato14A.f_totalDifIniProgR.val(parseFloat(totalDifusion));
	},
	costoUnitarioEmpadRural : function(){
		var sumaEmpadDifusion;
		var nroBenefi;
		var costoUnitEmpadronamiento;
		sumaEmpadDifusion = formato14A.f_sumEmpadDifR.val();
		nroBenefi = formato14A.f_nroBenefR.val();
		//
		if( nroBenefi!=null && nroBenefi!=0 ){
			costoUnitEmpadronamiento=parseFloat(sumaEmpadDifusion)/parseFloat(nroBenefi);
			costoUnitEmpadronamiento=redondeo(costoUnitEmpadronamiento, 2);
		}else{
			costoUnitEmpadronamiento='0.00';
		}
		formato14A.f_costoUnitEmpadR.val(parseFloat(costoUnitEmpadronamiento));
	},
	totalGestionAgentesRural : function(){
		var promConvenio;
		var registroConvenio;
		var impresionBanderola;
		var totalGestionAgentes;
		promConvenio = formato14A.f_promConvR.val();
		registroConvenio = formato14A.f_regFirmaConvR.val();
		impresionBanderola = formato14A.f_impBandR.val();
		//
		totalGestionAgentes = parseFloat(promConvenio)+parseFloat(registroConvenio)+parseFloat(impresionBanderola);
		totalGestionAgentes = redondeo(totalGestionAgentes, 2);
		formato14A.f_totalAgentR.val(parseFloat(totalGestionAgentes));
	},
	costoUnitarioAgentesRural : function(){
		var totalAgentes;
		var nroAgentes;
		var costoUnitAgentes;
		totalAgentes = formato14A.f_totalAgentR.val();
		nroAgentes = formato14A.f_nroAgentR.val();
		//
		if( nroAgentes!=null && nroAgentes!=0 ){
			costoUnitAgentes=parseFloat(totalAgentes)/parseFloat(nroAgentes);
			costoUnitAgentes=redondeo(costoUnitAgentes, 2);
		}else{
			costoUnitAgentes='0.00';
		}
		formato14A.f_costoUnitAgentR.val(parseFloat(costoUnitAgentes));
	},
	//CALCULO PROVINCIA
	sumaEmpadDifusionProvincia : function(){
		var totalEmpad;
		var totalDifusion;
		var sumaEmpadDifusion;
		totalEmpad=formato14A.f_totalEmpadP.val();
		totalDifusion=formato14A.f_totalDifIniProgP.val();
		sumaEmpadDifusion = parseFloat(totalEmpad)+parseFloat(totalDifusion);
		//redondeo a 2, depende de la logica de negocio
		sumaEmpadDifusion = redondeo(sumaEmpadDifusion, 2);
		formato14A.f_sumEmpadDifP.val(parseFloat(sumaEmpadDifusion));	
	},
	totalEmpadronamientoProvincia : function(){
		var impresionEsquela;
		var impresionDeclaJurada;
		var impresionFichas;
		var repartoEsquela;
		var verifInformacion;
		var elabArchivo;
		var digitacionFichas;
		var totalEmpadronamiento;
		impresionEsquela = formato14A.f_impEsqInvP.val();
		impresionDeclaJurada = formato14A.f_impDeclJuradaP.val();
		impresionFichas = formato14A.f_impFichaVerifP.val();
		repartoEsquela = formato14A.f_repEsqInvP.val();
		verifInformacion = formato14A.f_verifInfoP.val();
		elabArchivo = formato14A.f_elabArchBenefP.val();
		digitacionFichas = formato14A.f_digitFichaP.val();
		//
		totalEmpadronamiento = parseFloat(impresionEsquela)+parseFloat(impresionDeclaJurada)+parseFloat(impresionFichas)+parseFloat(repartoEsquela)
			+parseFloat(verifInformacion)+parseFloat(elabArchivo)+parseFloat(digitacionFichas);
		totalEmpadronamiento = redondeo(totalEmpadronamiento, 2);
		formato14A.f_totalEmpadP.val(parseFloat(totalEmpadronamiento));
	},
	totalDifusionProvincia : function(){
		var impresionVolantes;
		var impresionAfiches;
		var repartoFolletos;
		var spotTv;
		var spotRadio;
		var totalDifusion;
		impresionVolantes = formato14A.f_impVolanteP.val();
		impresionAfiches = formato14A.f_impAficheP.val();
		repartoFolletos = formato14A.f_repFolletoP.val();
		spotTv = formato14A.f_spotPublicTvP.val();
		spotRadio = formato14A.f_spotPublicRadioP.val();
		//
		totalDifusion = parseFloat(impresionVolantes)+parseFloat(impresionAfiches)+parseFloat(repartoFolletos)+parseFloat(spotTv)+parseFloat(spotRadio);
		totalDifusion = redondeo(totalDifusion, 2);
		formato14A.f_totalDifIniProgP.val(parseFloat(totalDifusion));
	},
	costoUnitarioEmpadProvincia : function(){
		var sumaEmpadDifusion;
		var nroBenefi;
		var costoUnitEmpadronamiento;
		sumaEmpadDifusion = formato14A.f_sumEmpadDifP.val();
		nroBenefi = formato14A.f_nroBenefP.val();
		//
		if( nroBenefi!=null && nroBenefi!=0 ){
			costoUnitEmpadronamiento=parseFloat(sumaEmpadDifusion)/parseFloat(nroBenefi);
			costoUnitEmpadronamiento=redondeo(costoUnitEmpadronamiento, 2);
		}else{
			costoUnitEmpadronamiento='0.00';
		}
		formato14A.f_costoUnitEmpadP.val(parseFloat(costoUnitEmpadronamiento));
	},
	totalGestionAgentesProvincia : function(){
		var promConvenio;
		var registroConvenio;
		var impresionBanderola;
		var totalGestionAgentes;
		promConvenio = formato14A.f_promConvP.val();
		registroConvenio = formato14A.f_regFirmaConvP.val();
		impresionBanderola = formato14A.f_impBandP.val();
		//
		totalGestionAgentes = parseFloat(promConvenio)+parseFloat(registroConvenio)+parseFloat(impresionBanderola);
		totalGestionAgentes = redondeo(totalGestionAgentes, 2);
		formato14A.f_totalAgentP.val(parseFloat(totalGestionAgentes));
	},
	costoUnitarioAgentesProvincia : function(){
		var totalAgentes;
		var nroAgentes;
		var costoUnitAgentes;
		totalAgentes = formato14A.f_totalAgentP.val();
		nroAgentes = formato14A.f_nroAgentP.val();
		//
		if( nroAgentes!=null && nroAgentes!=0 ){
			costoUnitAgentes=parseFloat(totalAgentes)/parseFloat(nroAgentes);
			costoUnitAgentes=redondeo(costoUnitAgentes, 2);
		}else{
			costoUnitAgentes='0.00';
		}
		formato14A.f_costoUnitAgentP.val(parseFloat(costoUnitAgentes));
	},
	//CALCULO LIMA
	sumaEmpadDifusionLima : function(){
		var totalEmpad;
		var totalDifusion;
		var sumaEmpadDifusion;
		totalEmpad=formato14A.f_totalEmpadL.val();
		totalDifusion=formato14A.f_totalDifIniProgL.val();
		sumaEmpadDifusion = parseFloat(totalEmpad)+parseFloat(totalDifusion);
		//redondeo a 2, depende de la logica de negocio
		sumaEmpadDifusion = redondeo(sumaEmpadDifusion, 2);
		formato14A.f_sumEmpadDifL.val(parseFloat(sumaEmpadDifusion));	
	},
	totalEmpadronamientoLima : function(){
		var impresionEsquela;
		var impresionDeclaJurada;
		var impresionFichas;
		var repartoEsquela;
		var verifInformacion;
		var elabArchivo;
		var digitacionFichas;
		var totalEmpadronamiento;
		impresionEsquela = formato14A.f_impEsqInvL.val();
		impresionDeclaJurada = formato14A.f_impDeclJuradaL.val();
		impresionFichas = formato14A.f_impFichaVerifL.val();
		repartoEsquela = formato14A.f_repEsqInvL.val();
		verifInformacion = formato14A.f_verifInfoL.val();
		elabArchivo = formato14A.f_elabArchBenefL.val();
		digitacionFichas = formato14A.f_digitFichaL.val();
		//
		totalEmpadronamiento = parseFloat(impresionEsquela)+parseFloat(impresionDeclaJurada)+parseFloat(impresionFichas)+parseFloat(repartoEsquela)
			+parseFloat(verifInformacion)+parseFloat(elabArchivo)+parseFloat(digitacionFichas);
		totalEmpadronamiento = redondeo(totalEmpadronamiento, 2);
		formato14A.f_totalEmpadL.val(parseFloat(totalEmpadronamiento));
	},
	totalDifusionLima : function(){
		var impresionVolantes;
		var impresionAfiches;
		var repartoFolletos;
		var spotTv;
		var spotRadio;
		var totalDifusion;
		impresionVolantes = formato14A.f_impVolanteL.val();
		impresionAfiches = formato14A.f_impAficheL.val();
		repartoFolletos = formato14A.f_repFolletoL.val();
		spotTv = formato14A.f_spotPublicTvL.val();
		spotRadio = formato14A.f_spotPublicRadioL.val();
		//
		totalDifusion = parseFloat(impresionVolantes)+parseFloat(impresionAfiches)+parseFloat(repartoFolletos)+parseFloat(spotTv)+parseFloat(spotRadio);
		totalDifusion = redondeo(totalDifusion, 2);
		formato14A.f_totalDifIniProgL.val(parseFloat(totalDifusion));
	},
	costoUnitarioEmpadLima : function(){
		var sumaEmpadDifusion;
		var nroBenefi;
		var costoUnitEmpadronamiento;
		sumaEmpadDifusion = formato14A.f_sumEmpadDifL.val();
		nroBenefi = formato14A.f_nroBenefL.val();
		//
		if( nroBenefi!=null && nroBenefi!=0 ){
			costoUnitEmpadronamiento=parseFloat(sumaEmpadDifusion)/parseFloat(nroBenefi);
			costoUnitEmpadronamiento=redondeo(costoUnitEmpadronamiento, 2);
		}else{
			costoUnitEmpadronamiento='0.00';
		}
		formato14A.f_costoUnitEmpadL.val(parseFloat(costoUnitEmpadronamiento));
	},
	totalGestionAgentesLima : function(){
		var promConvenio;
		var registroConvenio;
		var impresionBanderola;
		var totalGestionAgentes;
		promConvenio = formato14A.f_promConvL.val();
		registroConvenio = formato14A.f_regFirmaConvL.val();
		impresionBanderola = formato14A.f_impBandL.val();
		//
		totalGestionAgentes = parseFloat(promConvenio)+parseFloat(registroConvenio)+parseFloat(impresionBanderola);
		totalGestionAgentes = redondeo(totalGestionAgentes, 2);
		formato14A.f_totalAgentL.val(parseFloat(totalGestionAgentes));
	},
	costoUnitarioAgentesLima : function(){
		var totalAgentes;
		var nroAgentes;
		var costoUnitAgentes;
		totalAgentes = formato14A.f_totalAgentL.val();
		nroAgentes = formato14A.f_nroAgentL.val();
		//
		if( nroAgentes!=null && nroAgentes!=0 ){
			costoUnitAgentes=parseFloat(totalAgentes)/parseFloat(nroAgentes);
			costoUnitAgentes=redondeo(costoUnitAgentes, 2);
		}else{
			costoUnitAgentes='0.00';
		}
		formato14A.f_costoUnitAgentL.val(parseFloat(costoUnitAgentes));
	},
	calculoTotal : function(){
		formato14A.totalEmpadronamientoRural();
		formato14A.totalEmpadronamientoProvincia();
		formato14A.totalEmpadronamientoLima();
		
		formato14A.totalDifusionRural();
		formato14A.totalDifusionProvincia();
		formato14A.totalDifusionLima();
		
		formato14A.sumaEmpadDifusionRural();
		formato14A.sumaEmpadDifusionProvincia();
		formato14A.sumaEmpadDifusionLima();
		
		formato14A.costoUnitarioEmpadRural();
		formato14A.costoUnitarioEmpadProvincia();
		formato14A.costoUnitarioEmpadLima();
		//
		formato14A.totalGestionAgentesRural();
		formato14A.totalGestionAgentesProvincia();
		formato14A.totalGestionAgentesLima();
		
		formato14A.costoUnitarioAgentesRural();
		formato14A.costoUnitarioAgentesProvincia();
		formato14A.costoUnitarioAgentesLima();
		
		formato14A.formularioCompletarDecimales();
	},
	soloNumerosEnteros : function(){
		formato14A.f_nroBenefR.attr("onkeypress","return soloNumerosEntero(event, 1, 'nroBenefEmpadR',7,0)");
		formato14A.f_nroAgentR.attr("onkeypress","return soloNumerosEntero(event, 1, 'nroAgentR',7,0)");
		formato14A.f_nroBenefP.attr("onkeypress","return soloNumerosEntero(event, 1, 'nroBenefEmpadP',7,0)");
		formato14A.f_nroAgentP.attr("onkeypress","return soloNumerosEntero(event, 1, 'nroAgentP',7,0)");
		formato14A.f_nroBenefL.attr("onkeypress","return soloNumerosEntero(event, 1, 'nroBenefEmpadL',7,0)");
		formato14A.f_nroAgentL.attr("onkeypress","return soloNumerosEntero(event, 1, 'nroAgentL',7,0)");
	},
	soloNumerosDecimales : function(){
		//RURAL
		formato14A.f_impEsqInvR.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'imprEsqInvitR',7,2)");
		formato14A.f_impDeclJuradaR.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'imprDeclaJuradaR',7,2)");
		formato14A.f_impFichaVerifR.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'imprFichaVerifR',7,2)");
		formato14A.f_repEsqInvR.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'repartoEsqInvitR',7,2)");
		formato14A.f_verifInfoR.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'verifInfoR',7,2)");
		formato14A.f_elabArchBenefR.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'elabArchivoBenefR',7,2)");
		formato14A.f_digitFichaR.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'digitFichaBenefR',7,2)");
		formato14A.f_impVolanteR.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'imprVolantesR',7,2)");
		formato14A.f_impAficheR.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'imprAfichesR',7,2)");
		formato14A.f_repFolletoR.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'repFolletosR',7,2)");
		formato14A.f_spotPublicTvR.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'spotPublTvR',7,2)");
		formato14A.f_spotPublicRadioR.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'spotPublRadioR',7,2)");
		formato14A.f_promConvR.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'promConvAgentR',7,2)");
		formato14A.f_regFirmaConvR.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'regConvAgentR',7,2)");
		formato14A.f_impBandR.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'impEntrBandR',7,2)");
		//PROVINCIA
		formato14A.f_impEsqInvP.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'imprEsqInvitP',7,2)");
		formato14A.f_impDeclJuradaP.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'imprDeclaJuradaP',7,2)");
		formato14A.f_impFichaVerifP.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'imprFichaVerifP',7,2)");
		formato14A.f_repEsqInvP.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'repartoEsqInvitP',7,2)");
		formato14A.f_verifInfoP.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'verifInfoP',7,2)");
		formato14A.f_elabArchBenefP.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'elabArchivoBenefP',7,2)");
		formato14A.f_digitFichaP.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'digitFichaBenefP',7,2)");
		formato14A.f_impVolanteP.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'imprVolantesP',7,2)");
		formato14A.f_impAficheP.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'imprAfichesP',7,2)");
		formato14A.f_repFolletoP.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'repFolletosP',7,2)");
		formato14A.f_spotPublicTvP.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'spotPublTvP',7,2)");
		formato14A.f_spotPublicRadioP.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'spotPublRadioP',7,2)");
		formato14A.f_promConvP.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'promConvAgentP',7,2)");
		formato14A.f_regFirmaConvP.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'regConvAgentP',7,2)");
		formato14A.f_impBandP.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'impEntrBandP',7,2)");
		//LIMA
		formato14A.f_impEsqInvL.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'imprEsqInvitL',7,2)");
		formato14A.f_impDeclJuradaL.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'imprDeclaJuradaL',7,2)");
		formato14A.f_impFichaVerifL.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'imprFichaVerifL',7,2)");
		formato14A.f_repEsqInvL.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'repartoEsqInvitL',7,2)");
		formato14A.f_verifInfoL.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'verifInfoL',7,2)");
		formato14A.f_elabArchBenefL.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'elabArchivoBenefL',7,2)");
		formato14A.f_digitFichaL.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'digitFichaBenefL',7,2)");
		formato14A.f_impVolanteL.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'imprVolantesL',7,2)");
		formato14A.f_impAficheL.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'imprAfichesL',7,2)");
		formato14A.f_repFolletoL.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'repFolletosL',7,2)");
		formato14A.f_spotPublicTvL.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'spotPublTvL',7,2)");
		formato14A.f_spotPublicRadioL.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'spotPublRadioL',7,2)");
		formato14A.f_promConvL.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'promConvAgentL',7,2)");
		formato14A.f_regFirmaConvL.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'regConvAgentL',7,2)");
		formato14A.f_impBandL.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'impEntrBandL',7,2)");
	},
	formularioCompletarDecimales : function(){
		//RURAL
		completarDecimal('sumEmpadDifusionR',2);
		completarDecimal('totalEmpadR',2);
		completarDecimal('imprEsqInvitR',2);
		completarDecimal('imprDeclaJuradaR',2);
		completarDecimal('imprFichaVerifR',2);
		completarDecimal('repartoEsqInvitR',2);
		completarDecimal('verifInfoR',2);
		completarDecimal('elabArchivoBenefR',2);
		completarDecimal('digitFichaBenefR',2);
		completarDecimal('totalDifIniProgR',2);
		completarDecimal('imprVolantesR',2);
		completarDecimal('imprAfichesR',2);
		completarDecimal('repFolletosR',2);
		completarDecimal('spotPublTvR',2);
		completarDecimal('spotPublRadioR',2);
		completarDecimal('costoUnitEmpadR',2);
		//
		completarDecimal('totalCostoAgentR',2);
		completarDecimal('promConvAgentR',2);
		completarDecimal('regConvAgentR',2);
		completarDecimal('impEntrBandR',2);
		completarDecimal('costoUnitAgentR',2);
		//PROVINCIA
		completarDecimal('sumEmpadDifusionP',2);
		completarDecimal('totalEmpadP',2);
		completarDecimal('imprEsqInvitP',2);
		completarDecimal('imprDeclaJuradaP',2);
		completarDecimal('imprFichaVerifP',2);
		completarDecimal('repartoEsqInvitP',2);
		completarDecimal('verifInfoP',2);
		completarDecimal('elabArchivoBenefP',2);
		completarDecimal('digitFichaBenefP',2);
		completarDecimal('totalDifIniProgP',2);
		completarDecimal('imprVolantesP',2);
		completarDecimal('imprAfichesP',2);
		completarDecimal('repFolletosP',2);
		completarDecimal('spotPublTvP',2);
		completarDecimal('spotPublRadioP',2);
		completarDecimal('costoUnitEmpadP',2);
		//
		completarDecimal('totalCostoAgentP',2);
		completarDecimal('promConvAgentP',2);
		completarDecimal('regConvAgentP',2);
		completarDecimal('impEntrBandP',2);
		completarDecimal('costoUnitAgentP',2);
		//LIMA
		completarDecimal('sumEmpadDifusionL',2);
		completarDecimal('totalEmpadL',2);
		completarDecimal('imprEsqInvitL',2);
		completarDecimal('imprDeclaJuradaL',2);
		completarDecimal('imprFichaVerifL',2);
		completarDecimal('repartoEsqInvitL',2);
		completarDecimal('verifInfoL',2);
		completarDecimal('elabArchivoBenefL',2);
		completarDecimal('digitFichaBenefL',2);
		completarDecimal('totalDifIniProgL',2);
		completarDecimal('imprVolantesL',2);
		completarDecimal('imprAfichesL',2);
		completarDecimal('repFolletosL',2);
		completarDecimal('spotPublTvL',2);
		completarDecimal('spotPublRadioL',2);
		completarDecimal('costoUnitEmpadL',2);
		//
		completarDecimal('totalCostoAgentL',2);
		completarDecimal('promConvAgentL',2);
		completarDecimal('regConvAgentL',2);
		completarDecimal('impEntrBandL',2);
		completarDecimal('costoUnitAgentL',2);
	},
	<portlet:namespace/>loadPeriodo : function(valPeriodo){
		jQuery.ajax({
				url: formato14A.urlCargaPeriodo+'&'+formato14A.formCommand.serialize(),
				type: 'post',
				dataType: 'json',
				success: function(data) {		
					dwr.util.removeAllOptions("periodoEnvio");
					dwr.util.addOptions("periodoEnvio", data,"codigoItem","descripcionItem");
					if( valPeriodo.length!='' ){
						dwr.util.setValue("periodoEnvio", valPeriodo);
					}
					formato14A.<portlet:namespace/>loadCargaFlagPeriodo();
					//validar lima edelnor y luz del sur
					if(formato14A.cod_empresa_edelnor.val()==formato14A.f_empresa.val() || formato14A.cod_empresa_luz_sur.val()==formato14A.f_empresa.val()){
						formato14A.habilitarLima();										
					}else{
						formato14A.deshabilitarLima();
					}
				},error : function(){
					alert("Error de conexi�n.");
					formato14A.initBlockUI();
				}
		});
	},
	<portlet:namespace/>loadCargaFlagPeriodo : function() {
		jQuery.ajax({
			url: formato14A.urlCargaFlagPeriodo+'&'+formato14A.formCommand.serialize(),
				type: 'post',
				dataType: 'json',
				success: function(data) {				
					dwr.util.setValue("flagPeriodoEjecucion", data.flagPeriodoEjecucion);
					//cargar los valores obtenidos de ano inicio y fin de vigencia
					dwr.util.setValue("anioInicioVigencia", data.anioInicioVigencia);
					dwr.util.setValue("anioFinVigencia", data.anioFinVigencia);
					//
					dwr.util.setValue("idGrupoInfo", data.idGrupoInfo);
					
					dwr.util.setValue("etapaFinal", data.etapaFinal);		
					
					formato14A.recargarPeriodoEjecucion(data.anioInicioVigencia,data.anioFinVigencia);
					formato14A.mostrarPeriodoEjecucion();
				},error : function(){
					alert("Error de conexi�n.");
					formato14A.initBlockUI();
				}
		});
	},
	validarGrupoInformacion : function(){
		if( $('#idGrupoInfo').val()=='0' ){
			//alert('Seleccione una Distribuidora El�ctrica'); 
			formato14A.dialogMessageWarningContent.html('Primero debe crear el Grupo de Informaci�n Bienal para el A�o y Mes a declarar');
			formato14A.dialogMessageWarning.dialog("open");
			cod_focus=$('#idGrupoInfo');
			return false;
		}
		return true;
	},
	//
	mostrarPeriodoEjecucion : function(){
		if( formato14A.f_flagPeriodo.val()=='S' ){
			/*$('#anioInicioVigencia').removeAttr("disabled");
			$('#anioFinVigencia').removeAttr("disabled");
			//formato14A.divPeriodoEjecucion.show(); 
			formato14A.estiloEdicionCabecera();*/
			$('#anioInicioVigencia').attr("disabled",true);
			$('#anioFinVigencia').attr("disabled",true);
			formato14A.quitarEstiloEdicionCabecera();
		}else{
			$('#anioInicioVigencia').attr("disabled",true);
			$('#anioFinVigencia').attr("disabled",true);
			//formato14A.divPeriodoEjecucion.hide();  
			formato14A.quitarEstiloEdicionCabecera();
		}
	},
	recargarPeriodoEjecucion : function(valAnioInicio,valAnioFin){
		/*var anoInicio;
		var anoFin;
		if( formato14A.f_periodoEnvio.val() != null ){
			anoInicio = formato14A.f_periodoEnvio.val().substring(0,4);
			anoFin = formato14A.f_periodoEnvio.val().substring(0,4);
			if( formato14A.f_flagPeriodo.val()=='S' ){
				$('#anioInicioVigencia').val(anoInicio);
				$('#anioFinVigencia').val(anoFin);
			}
		}*/
		$('#anioInicioVigencia').val(valAnioInicio);
		$('#anioFinVigencia').val(valAnioFin);
	},
	//FORMULARIOS DE VIEW Y EDICION
	verFormato : function(codEmpresa,anoPresentacion,mesPresentacion,anoIniVigencia,anoFinVigencia,etapa){	
		$.blockUI({ message: formato14A.mensajeObteniendoDatos });
		jQuery.ajax({
				url: formato14A.urlCrud+'&'+formato14A.formCommand.serialize(),
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
						formato14A.procesoEstado.val("UPDATE");
						formato14A.etapaEdit.val(etapa);
						formato14A.divFormato.show();
						formato14A.divHome.hide();
						formato14A.divInformacion.show();
						//dwr.util.removeAllOptions("periodoEnvio");
						//dwr.util.addOptions("periodoEnvio", data.periodoEnvio,"codigoItem","descripcionItem");
						formato14A.FillEditformato(data.formato);
						formato14A.deshabilitarCamposView();
						formato14A.initBlockUI();
					}
					else{
						//alert("Error al recuperar los datos del registro seleccionado");
						formato14A.dialogMessageErrorContent.html('Error al recuperar los datos del registro seleccionado');
						formato14A.dialogMessageError.dialog("open");
						formato14A.initBlockUI();
					}
				},error : function(){
					alert("Error de conexi�n.");
					formato14A.initBlockUI();
				}
		});	
	},
	editarFormato : function(codEmpresa,anoPresentacion,mesPresentacion,anoIniVigencia,anoFinVigencia,etapa,flagOperacion){	
		var admin = '${formato14ACBean.admin}';
		if(flagOperacion=='ABIERTO'){
			//control para tipo de usuario
			var process=true;
			if( etapa=='ESTABLECIDO'  &&  admin=='false' ){
				process = false;
			}
			if(process){
				$.blockUI({ message: formato14A.mensajeObteniendoDatos });
				jQuery.ajax({
						url: formato14A.urlCrud+'&'+formato14A.formCommand.serialize(),
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
								formato14A.procesoEstado.val("UPDATE");
								formato14A.etapaEdit.val(etapa);
								formato14A.divFormato.show();
								formato14A.divHome.hide();
								formato14A.divInformacion.show();
								//dwr.util.removeAllOptions("periodoEnvio");
								//dwr.util.addOptions("periodoEnvio", data.periodoEnvio,"codigoItem","descripcionItem");
								formato14A.FillEditformato(data.formato);
								
								$('#<portlet:namespace/>guardarFormato').val('Actualizar');
								
								formato14A.estiloEdicionRural();
								formato14A.estiloEdicionProvincia();
								
								//validar lima edelnor y luz del sur
								if(formato14A.cod_empresa_edelnor.val()==formato14A.f_empresa.val() || formato14A.cod_empresa_luz_sur.val()==formato14A.f_empresa.val()){
									formato14A.habilitarLima();										
								}else{
									formato14A.deshabilitarLima();
								}
								$('#etapaFinal').val('');//limpiamos la variabel al momento de editar para que no bloquee la etapa final del formato
								formato14A.initBlockUI();
							}
							else{
								//alert("Error al recuperar los datos del registro seleccionado");
								formato14A.dialogMessageErrorContent.html('Error al recuperar los datos del registro seleccionado');
								formato14A.dialogMessageError.dialog("open");
								formato14A.initBlockUI();
							}
						},error : function(){
							alert("Error de conexi�n.");
							formato14A.initBlockUI();
						}
				});
			}else{
				//alert(" No tiene autorizaci�n para realizar esta operaci�n");
				formato14A.dialogMessageInfoContent.html('No tiene autorizaci�n para realizar esta acci�n');
				formato14A.dialogMessageInfo.dialog("open");
			}

		}else if(flagOperacion=='CERRADO'){
			//alert(" Est� fuera de plazo");
			formato14A.dialogMessageInfoContent.html('El plazo para realizar esta acci�n se encuentra cerrado');
			formato14A.dialogMessageInfo.dialog("open");
		}else{
			//alert("El formato ya fue enviado a OSINERGMIN-GART");	
			formato14A.dialogMessageInfoContent.html('El formato ya fue enviado a OSINERGMIN-GART');
			formato14A.dialogMessageInfo.dialog("open");
		}
	},
	FillEditformato : function(row){
		
		formato14A.f_empresa.val(trim(row.codEmpresa));
		//seteamos el concatenado
		formato14A.f_periodoEnvio.val(''+row.anoPresentacion+completarCerosIzq(row.mesPresentacion,2)+row.etapa);
		
		//seteamos el grupo y estado
		$('#descGrupoInformacion').val(row.grupoInfo);
		$('#descEstado').val(row.estado);	
		
		//seteamos el periodo envio, cargado de base de datos
		dwr.util.removeAllOptions("periodoEnvio");
		var codigo=''+row.anoPresentacion+completarCerosIzq(row.mesPresentacion,2)+row.etapa;
		var descripcion=formato14A.mostrarDescripcionPeriodo(row.anoPresentacion, row.mesPresentacion, row.etapa);
   		
		//alert(formato14A.mostrarDescripcionPeriodo(row.anoPresentacion, row.mesPresentacion, row.etapa));
		var dataPeriodo = [{codigoItem:codigo, descripcionItem:descripcion}];   
   		dwr.util.addOptions("periodoEnvio", dataPeriodo,"codigoItem","descripcionItem");
		//
		formato14A.f_flagPeriodo.val(row.flagPeriodoEjecucion);
		
		$('#anioInicioVigencia').val(row.anoIniVigencia);
		$('#anioFinVigencia').val(row.anoFinVigencia);
		
		//setamos los hidden
		$('#anioInicioVigenciaHidden').val(row.anoIniVigencia);
		$('#anioFinVigenciaHidden').val(row.anoFinVigencia);
		
		
		/*if( formato14A.f_flagPeriodo.val()=='S' ){
			$('#anioInicioVigencia').val(row.anoIniVigencia);
			$('#anioFinVigencia').val(row.anoFinVigencia);
			$('#anioInicioVigencia').attr("disabled",true);
			$('#anioFinVigencia').attr("disabled",true);
		}*/
		formato14A.etapaEdit.val(row.etapa);
		//RURAL
		//formato14A.f_sumEmpadDifR.val(row.);
		//alert(row.totalEmpadR);
		formato14A.f_totalEmpadR.val(row.totalEmpadR);
		formato14A.f_impEsqInvR.val(row.impEsqInvR);
		formato14A.f_impDeclJuradaR.val(row.impDeclJuradaR);
		formato14A.f_impFichaVerifR.val(row.impFichaVerifR);
		formato14A.f_repEsqInvR.val(row.repEsqInvR);
		formato14A.f_verifInfoR.val(row.verifInfoR);
		formato14A.f_elabArchBenefR.val(row.elabArchBenefR);
		formato14A.f_digitFichaR.val(row.digitFichaBenefR);
		formato14A.f_totalDifIniProgR.val(row.totalDifusionIniProgR);
		formato14A.f_impVolanteR.val(row.impVolanteR);
		formato14A.f_impAficheR.val(row.impAficheR);
		formato14A.f_repFolletoR.val(row.repFolletoBenefR);
		formato14A.f_spotPublicTvR.val(row.spotPublicTvR);
		formato14A.f_spotPublicRadioR.val(row.spotPublicRadioR);
		formato14A.f_nroBenefR.val(row.nroBenefEmpadR);
		formato14A.f_costoUnitEmpadR.val(row.costoUnitEmpadR);
		formato14A.f_totalAgentR.val(row.totalCostoAgentR);
		formato14A.f_promConvR.val(row.promConvAgentR);
		formato14A.f_regFirmaConvR.val(row.regFirmaConvR);
		formato14A.f_impBandR.val(row.impEntregaBandR);
		formato14A.f_nroAgentR.val(row.nroAgentR);
		formato14A.f_costoUnitAgentR.val(row.costoUnitAgentR);
		//PROVINCIA
		formato14A.f_totalEmpadP.val(row.totalEmpadP);
		formato14A.f_impEsqInvP.val(row.impEsqInvP);
		formato14A.f_impDeclJuradaP.val(row.impDeclJuradaP);
		formato14A.f_impFichaVerifP.val(row.impFichaVerifP);
		formato14A.f_repEsqInvP.val(row.repEsqInvP);
		formato14A.f_verifInfoP.val(row.verifInfoP);
		formato14A.f_elabArchBenefP.val(row.elabArchBenefP);
		formato14A.f_digitFichaP.val(row.digitFichaBenefP);
		formato14A.f_totalDifIniProgP.val(row.totalDifusionIniProgP);
		formato14A.f_impVolanteP.val(row.impVolanteP);
		formato14A.f_impAficheP.val(row.impAficheP);
		formato14A.f_repFolletoP.val(row.repFolletoBenefP);
		formato14A.f_spotPublicTvP.val(row.spotPublicTvP);
		formato14A.f_spotPublicRadioP.val(row.spotPublicRadioP);
		formato14A.f_nroBenefP.val(row.nroBenefEmpadP);
		formato14A.f_costoUnitEmpadP.val(row.costoUnitEmpadP);
		formato14A.f_totalAgentP.val(row.totalCostoAgentP);
		formato14A.f_promConvP.val(row.promConvAgentP);
		formato14A.f_regFirmaConvP.val(row.regFirmaConvP);
		formato14A.f_impBandP.val(row.impEntregaBandP);
		formato14A.f_nroAgentP.val(row.nroAgentP);
		formato14A.f_costoUnitAgentP.val(row.costoUnitAgentP);
		//LIMA
		formato14A.f_totalEmpadL.val(row.totalEmpadL);
		formato14A.f_impEsqInvL.val(row.impEsqInvL);
		formato14A.f_impDeclJuradaL.val(row.impDeclJuradaL);
		formato14A.f_impFichaVerifL.val(row.impFichaVerifL);
		formato14A.f_repEsqInvL.val(row.repEsqInvL);
		formato14A.f_verifInfoL.val(row.verifInfoL);
		formato14A.f_elabArchBenefL.val(row.elabArchBenefL);
		formato14A.f_digitFichaL.val(row.digitFichaBenefL);
		formato14A.f_totalDifIniProgL.val(row.totalDifusionIniProgL);
		formato14A.f_impVolanteL.val(row.impVolanteL);
		formato14A.f_impAficheL.val(row.impAficheL);
		formato14A.f_repFolletoL.val(row.repFolletoBenefL);
		formato14A.f_spotPublicTvL.val(row.spotPublicTvL);
		formato14A.f_spotPublicRadioL.val(row.spotPublicRadioL);
		formato14A.f_nroBenefL.val(row.nroBenefEmpadL);
		formato14A.f_costoUnitEmpadL.val(row.costoUnitEmpadL);
		formato14A.f_totalAgentL.val(row.totalCostoAgentL);
		formato14A.f_promConvL.val(row.promConvAgentL);
		formato14A.f_regFirmaConvL.val(row.regFirmaConvL);
		formato14A.f_impBandL.val(row.impEntregaBandL);
		formato14A.f_nroAgentL.val(row.nroAgentL);
		formato14A.f_costoUnitAgentL.val(row.costoUnitAgentL);
		
		formato14A.f_empresa.attr("disabled",true);
		formato14A.f_periodoEnvio.attr("disabled",true);
		$('#anioInicioVigencia').attr("disabled",true);
		$('#anioFinVigencia').attr("disabled",true);
		formato14A.quitarEstiloEdicionCabecera();
		//
		formato14A.calculoTotal();
		
		formato14A.soloNumerosEnteros();
		formato14A.soloNumerosDecimales();
		formato14A.formularioCompletarDecimales();
		
		formato14A.flagCarga.val('1');//inicializamos el flag de carga cuando editamos el archivo antes de cargar archivos
		
		//formato14A.mostrarPeriodoEjecucion();
	},
//////CRUD
	<portlet:namespace/>guardarFormato : function(){
		if (formato14A.validarFormulario()){
			$.blockUI({ message: formato14A.mensajeGuardando });
			 jQuery.ajax({
				 url: formato14A.urlCrud+'&'+formato14A.formCommand.serialize(),
				type: 'post',
				dataType: 'json',
				data: {
					<portlet:namespace />tipo: formato14A.procesoEstado.val(),
					<portlet:namespace />codEmpresa: formato14A.f_empresa.val(),
					<portlet:namespace />periodoEnvio: formato14A.f_periodoEnvio.val(),
					<portlet:namespace />flagPeriodo: formato14A.f_flagPeriodo.val(),
					<portlet:namespace />anoInicioVigencia: $('#anioInicioVigencia').val(),
					<portlet:namespace />anoFinVigencia: $('#anioFinVigencia').val(),
					<portlet:namespace />etapa: formato14A.etapaEdit.val()
					},
				success: function(data) {			
					if (data.resultado == "OK1"){				
						var addhtml2='El Formato 14A se guard� satisfactoriamente';
						formato14A.dialogMessageContent.html(addhtml2);
						formato14A.dialogMessage.dialog("open");
						formato14A.flagCarga.val('1');//inicializamos el flag de carga cuando editamos el archivo antes de cargar archivos
						formato14A.mostrarFormularioModificado();
						formato14A.initBlockUI();
					}else if (data.resultado == "OK2"){				
						var addhtml2='El Formato 14A se actualiz� satisfactoriamente';
						formato14A.dialogMessageContent.html(addhtml2);
						formato14A.dialogMessage.dialog("open");
						formato14A.flagCarga.val('1');//inicializamos el flag de carga cuando editamos el archivo antes de cargar archivos
						formato14A.mostrarFormularioModificado();
						formato14A.initBlockUI();
					}
					else if(data.resultado == "Error"){				
						var addhtml2=data.mensaje;
						//var addhtml2='Se produjo un error al guardar los datos del Formato 14A';
						formato14A.dialogMessageErrorContent.html(addhtml2);
						formato14A.dialogMessageError.dialog("open");
						formato14A.mostrarUltimoFormato();
						formato14A.initBlockUI();
					}
				},error : function(){
					alert("Error de conexi�n.");
					formato14A.initBlockUI();
				}
			});
		   	//se deja el formulario activo
			formato14A.divFormato.hide();
			formato14A.divHome.show();
		}
	},
	confirmarEliminar : function(codEmpresa,anoPresentacion,mesPresentacion,anoIniVigencia,anoFinVigencia,etapa,flagOperacion){
		var admin = '${formato14ACBean.admin}';
		console.debug(admin);
		if(flagOperacion=='ABIERTO'){
			//control para tipo de usuario
			var process=true;
			if( etapa=='ESTABLECIDO'  &&  admin=='false' ){
				process = false;
			}
			if(process){
				var addhtml='�Est� seguro que desea eliminar el registro seleccionado?';
				formato14A.dialogConfirmContent.html(addhtml);
				formato14A.dialogConfirm.dialog("open");
				cod_Empresa=codEmpresa;
				ano_Presentacion=anoPresentacion;
				mes_Presentacion=mesPresentacion;
				ano_Inicio_Vigencia=anoIniVigencia;
				ano_Fin_Vigencia=anoFinVigencia;
				cod_Etapa=etapa;
			}else{
				//alert(" No tiene autorizaci�n para realizar esta operaci�n");
				formato14A.dialogMessageInfoContent.html('No tiene autorizaci�n para realizar esta acci�n');
				formato14A.dialogMessageInfo.dialog("open");
			}

		}else if(flagOperacion=='CERRADO'){
			//alert(" Est� fuera de plazo");
			formato14A.dialogMessageInfoContent.html('El plazo para realizar esta acci�n se encuentra cerrado');
			formato14A.dialogMessageInfo.dialog("open");
		}else{
			//alert("El formato ya fue enviado a OSINERGMIN-GART");
			formato14A.dialogMessageInfoContent.html('El formato ya fue enviado a OSINERGMIN-GART');
			formato14A.dialogMessageInfo.dialog("open");
		}
	},
	eliminarFormato : function(cod_Empresa,ano_Presentacion,mes_Presentacion,ano_Inicio_Vigencia,ano_Fin_Vigencia,cod_Etapa){			
		$.blockUI({ message: formato14A.mensajeEliminando });
		jQuery.ajax({
			url: formato14A.urlCrud+'&'+formato14A.formCommand.serialize(),
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
					var addhtml2='El registro seleccionado para el Formato 14A se elimin� satisfactoriamente';					
					formato14A.dialogMessageContent.html(addhtml2);
					formato14A.dialogMessage.dialog("open");
					formato14A.buscar();
					formato14A.initBlockUI();
				}
				else{
					//alert("Error al eliminar el registro");
					formato14A.dialogMessageErrorContent.html('Error al eliminar el registro');
					formato14A.dialogMessageError.dialog("open");
					formato14A.initBlockUI();
				}
			},error : function(){
				alert("Error de conexi�n.");
				formato14A.initBlockUI();
			}
		});
	},
	mostrarFormularioModificado : function(){
		var codEmpM = formato14A.f_empresa.val();
		var anioPresM = formato14A.f_periodoEnvio.val().substring(0,4);
		var mesPresM = formato14A.f_periodoEnvio.val().substring(4,6);
		/*var anioIniVigM;
		var anioFinVigM;
		 if( formato14A.f_flagPeriodo.val()=='S' ){
			 anioIniVigM = $("#anioInicioVigencia").val();
			 anioFinVigM = $("#anioFinVigencia").val();
		 }else{
			 anioIniVigM = anioPresM;
			 anioFinVigM = anioPresM;
		 }*/
		 var flagOpera = 'ABIERTO';
		 
		 $('#<portlet:namespace/>guardarFormato').val('Actualizar');
		 
		 var anioIniVigM = $("#anioInicioVigencia").val();
		 var anioFinVigM = $("#anioFinVigencia").val();
		 //var etapaM = "SOLICITUD";
		 var etapaM = formato14A.f_periodoEnvio.val().substring(6,formato14A.f_periodoEnvio.val().length);
		 if( formato14A.flagCarga.val()=='0' ){
			 formato14A.mostrarUltimoFormato();
		 }else{
			//alert(codEmpM+','+anioPresM+','+mesPresM+','+anioIniVigM+','+anioFinVigM+','+etapaM);
			 if(codEmpM != '' && anioPresM != '' && mesPresM != '' && anioIniVigM != '' && anioFinVigM != '' && etapaM != ''){
			 	 formato14A.editarFormato(codEmpM, anioPresM, mesPresM, anioIniVigM, anioFinVigM, etapaM,flagOpera);
			 }
		 }
		 formato14A.botonValidacion.css('display','');
		 formato14A.botonEnvioDefinitivo.css('display','');
	},
	mostrarUltimoFormato : function(){	
		formato14A.procesoEstado.val('SAVE');
		formato14A.etapaEdit.val("");
		formato14A.divFormato.show();
		formato14A.divHome.hide();
		formato14A.flagCarga.val('0');
		//
		$('#<portlet:namespace/>guardarFormato').val('Grabar');
		
		formato14A.estiloEdicionRural();
		formato14A.estiloEdicionProvincia();
	},
	validarFormulario : function() {		
		
		if(formato14A.f_empresa.val().length == '' ) { 	
			//alert('Seleccione una Distribuidora El�ctrica'); 
			formato14A.dialogMessageWarningContent.html('Debe seleccionar una Distribuidora El�ctrica');
			formato14A.dialogMessageWarning.dialog("open");
			//formato14A.f_empresa.focus();
			cod_focus=formato14A.f_empresa;
		  	return false; 
		}
		/*if(formato14A.f_periodoEnvio.val().length == '' ) {		  
			//alert('Debe ingresar el periodo de presentaci�n');
			formato14A.dialogMessageWarningContent.html('Debe ingresar el periodo de presentaci�n');
			formato14A.dialogMessageWarning.dialog("open");
			formato14A.f_periodoEnvio.focus();
			return false; 
		}*/
		if(formato14A.f_periodoEnvio==null || formato14A.f_periodoEnvio.val().length == '' ) {		  
			//alert('Debe seleccionar el periodo a declarar');
			formato14A.dialogMessageWarningContent.html('Debe ingresar el Periodo a Declarar');
			formato14A.dialogMessageWarning.dialog("open");
			//formato14A.f_periodoEnvio.focus();
			cod_focus=formato14A.f_periodoEnvio;
			return false; 
		}
		if( formato14A.f_flagPeriodo.val()=='S' ){
			if($('#anioInicioVigencia').val().length == '' ) {		  
				//alert('Debe ingresar el a�o de inicio de vigencia');
				formato14A.dialogMessageWarningContent.html('Debe ingresar el A�o de Inicio de Vigencia');
				formato14A.dialogMessageWarning.dialog("open");
				//$('#anioInicioVigencia').focus();
				cod_focus=$('#anioInicioVigencia');
				return false; 
			}else{
				var numstr = trim($('#anioInicioVigencia').val());
				if (isNaN(numstr) || numstr.length<4 || parseFloat(numstr)<1900){
					//alert('Ingrese un a�o de inicio de inicio de vigencia v�lido');
					formato14A.dialogMessageWarningContent.html('Debe Ingresar un A�o de Inicio de Vigencia v�lido');
					formato14A.dialogMessageWarning.dialog("open");
					cod_focus=$('#anioInicioVigencia');
					return false;
			 	}
		 	}
			if($('#anioFinVigencia').val().length == '' ) {		  
				//alert('Debe ingresar el a�o de fin de vigencia');
				formato14A.dialogMessageWarningContent.html('Debe ingresar el A�o de Fin de Vigencia');
				formato14A.dialogMessageWarning.dialog("open");
				//$('#anioFinVigencia').focus();
				cod_focus=$('#anioFinVigencia');
				return false; 
			}else{
				var numstr = trim($('#anioFinVigencia').val());
				if (isNaN(numstr) || numstr.length<4 || parseFloat(numstr)<1900){
					//alert('Ingrese un a�o de fin de vigencia v�lido');
					formato14A.dialogMessageWarningContent.html('Debe ingresar un A�o de Fin de Vigencia v�lido');
					formato14A.dialogMessageWarning.dialog("open");
					cod_focus=$('#anioFinVigencia');
					return false;
				}
		 	}
		}
		//valores de formulario
		if(formato14A.f_nroBenefR.val().length == '' ) {		  
			//alert('Debe ingresar el n�mero de empadronados para Rural');
			formato14A.dialogMessageWarningContent.html('Debe ingresar el N�mero de Empadronados para la Zona Rural');
			formato14A.dialogMessageWarning.dialog("open");
			//formato14A.f_nroBenefR.focus();
			cod_focus=formato14A.f_nroBenefR;
			return false; 
		}
		if(formato14A.f_nroAgentR.val().length == '' ) {		  
			//alert('Debe ingresar el n�mero de agentes para Rural');
			formato14A.dialogMessageWarningContent.html('Debe ingresar el N�mero de Agentes para la Zona Rural');
			formato14A.dialogMessageWarning.dialog("open");
			//formato14A.f_nroAgentR.focus();
			cod_focus=formato14A.f_nroAgentR;
			return false; 
		}
		////////////////////
		if(formato14A.f_nroBenefP.val().length == '' ) {		  
			//alert('Debe ingresar el n�mero de empadronados para Provincia');
			formato14A.dialogMessageWarningContent.html('Debe ingresar el N�mero de Empadronados para la Zona Urbano Provincias');
			formato14A.dialogMessageWarning.dialog("open");
			//formato14A.f_nroBenefP.focus();
			cod_focus=formato14A.f_nroBenefP;
			return false; 
		}
		if(formato14A.f_nroAgentP.val().length == '' ) {		  
			//alert('Debe ingresar el n�mero de agentes para Provincia');
			formato14A.dialogMessageWarningContent.html('Debe ingresar el N�mero de Agentes para la Zona Urbano Provincias');
			formato14A.dialogMessageWarning.dialog("open");
			//formato14A.f_nroAgentP.focus();
			cod_focus=formato14A.f_nroAgentP;
			return false; 
		}
		///////////////////
		if(formato14A.f_nroBenefL.val().length == '' ) {		  
			//alert('Debe ingresar el n�mero de empadronados para Lima');
			formato14A.dialogMessageWarningContent.html('Debe ingresar el N�mero de Empadronados para la Zona Lima');
			formato14A.dialogMessageWarning.dialog("open");
			//formato14A.f_nroBenefL.focus();
			cod_focus=formato14A.f_nroBenefL;
			return false; 
		}
		if(formato14A.f_nroAgentL.val().length == '' ) {		  
			//alert('Debe ingresar el n�mero de agentes para Lima');
			formato14A.dialogMessageWarningContent.html('Debe ingresar el N�mero de Agentes para la Zona Lima');
			formato14A.dialogMessageWarning.dialog("open");
			//formato14A.f_nroAgentL.focus();
			cod_focus=formato14A.f_nroAgentL;
			return false; 
		}
		//validacion para no grabacion de ceros
		if( formato14A.f_impEsqInvR.val()=='0.00' && formato14A.f_impDeclJuradaR.val()=='0.00' && formato14A.f_impFichaVerifR.val()=='0.00' 
			&& formato14A.f_repEsqInvR.val()=='0.00' && formato14A.f_verifInfoR.val()=='0.00' && formato14A.f_elabArchBenefR.val()=='0.00' 
			&& formato14A.f_digitFichaR.val()=='0.00' && formato14A.f_impVolanteR.val()=='0.00' && formato14A.f_impAficheR.val()=='0.00'
			&& formato14A.f_repFolletoR.val()=='0.00' && formato14A.f_spotPublicTvR.val()=='0.00' && formato14A.f_spotPublicRadioR.val()=='0.00'
			&& formato14A.f_nroBenefR.val()=='0' && formato14A.f_promConvR.val()=='0.00' && formato14A.f_regFirmaConvR.val()=='0.00' 
			&& formato14A.f_impBandR.val()=='0.00' && formato14A.f_nroAgentR.val()=='0'
			&&//	
			formato14A.f_impEsqInvP.val()=='0.00' && formato14A.f_impDeclJuradaP.val()=='0.00' && formato14A.f_impFichaVerifP.val()=='0.00' 
			&& formato14A.f_repEsqInvP.val()=='0.00' && formato14A.f_verifInfoP.val()=='0.00' && formato14A.f_elabArchBenefP.val()=='0.00' 
			&& formato14A.f_digitFichaP.val()=='0.00' && formato14A.f_impVolanteP.val()=='0.00' && formato14A.f_impAficheP.val()=='0.00'
			&& formato14A.f_repFolletoP.val()=='0.00' && formato14A.f_spotPublicTvP.val()=='0.00' && formato14A.f_spotPublicRadioP.val()=='0.00'
			&& formato14A.f_nroBenefP.val()=='0' && formato14A.f_promConvP.val()=='0.00' && formato14A.f_regFirmaConvP.val()=='0.00' 
			&& formato14A.f_impBandP.val()=='0.00' && formato14A.f_nroAgentP.val()=='0'
			&&//		
			formato14A.f_impEsqInvL.val()=='0.00' && formato14A.f_impDeclJuradaL.val()=='0.00' && formato14A.f_impFichaVerifL.val()=='0.00' 
			&& formato14A.f_repEsqInvL.val()=='0.00' && formato14A.f_verifInfoL.val()=='0.00' && formato14A.f_elabArchBenefL.val()=='0.00' 
			&& formato14A.f_digitFichaL.val()=='0.00' && formato14A.f_impVolanteL.val()=='0.00' && formato14A.f_impAficheL.val()=='0.00'
			&& formato14A.f_repFolletoL.val()=='0.00' && formato14A.f_spotPublicTvL.val()=='0.00' && formato14A.f_spotPublicRadioL.val()=='0.00'
			&& formato14A.f_nroBenefL.val()=='0' && formato14A.f_promConvL.val()=='0.00' && formato14A.f_regFirmaConvL.val()=='0.00' 
			&& formato14A.f_impBandL.val()=='0.00' && formato14A.f_nroAgentL.val()=='0'
		
		){
			formato14A.dialogMessageWarningContent.html('Debe ingresar al menos un valor para poder guardar un formato');
			formato14A.dialogMessageWarning.dialog("open");
			cod_focus=formato14A.f_impEsqInvR;
			return false; 
		}
		
		return true; 
	},
	validarArchivoCarga : function() {		
		
		if(formato14A.f_empresa.val().length == '' ) { 	
			//alert('Seleccione una Distribuidora El�ctrica para proceder con la carga de archivo'); 
			formato14A.dialogMessageWarningContent.html('Debe seleccionar una Distribuidora El�ctrica para proceder con la carga del archivo');
			formato14A.dialogMessageWarning.dialog("open");
			//formato14A.f_empresa.focus();
			cod_focus=formato14A.f_empresa;
			return false; 
		}
		if(formato14A.f_periodoEnvio==null || formato14A.f_periodoEnvio.val().length == '' ) {		  
			//alert('Debe seleccionar el periodo a declarar');
			formato14A.dialogMessageWarningContent.html('Debe seleccionar el Periodo a Declarar para proceder con la carga del archivo');
			formato14A.dialogMessageWarning.dialog("open");
			//formato14A.f_periodoEnvio.focus();
			cod_focus=formato14A.f_periodoEnvio;
			return false; 
		}
		return true; 
	},
	////
	<portlet:namespace/>cargarFormatoExcel : function(){
		var frm = document.getElementById('formato14ACBean');
		var nameFile=$("#archivoExcel").val();
		var isSubmit=true;
		
		$("#msjFileExcel").html("");
		if(typeof (nameFile) == "undefined" || nameFile.length==0){
			isSubmit=false;
			$("#msjFileExcel").html("Debe seleccionar un archivo");
		}else{
			var extension=nameFile.substr(nameFile.indexOf(".")+1,nameFile.length);				
			if(extension == 'xls' || extension == 'xlsx' || extension == 'XLS'|| extension == 'XLSX'){
				isSubmit=true;
				$("#msjFileExcel").html("");
			}else{
				isSubmit=false;
				$("#msjFileExcel").html("Archivo inv�lido");
			}
			//isSubmit=true;
			//$("#msjFileExcel").html("");
		}
		if(isSubmit){
			frm.submit();
		}
		//frm.submit();
	},
	<portlet:namespace/>cargarFormatoTexto : function(){
		var frm = document.getElementById('formato14ACBean');
		var nameFile=$("#archivoTxt").val();
		var isSubmit=true;
		
		$("#msjFileTxt").html("");
		if(typeof (nameFile) == "undefined" || nameFile.length==0){
			isSubmit=false;
			$("#msjFileTxt").html("Debe seleccionar un archivo");
		}else{
			var extension=nameFile.substr(nameFile.indexOf(".")+1,nameFile.length);				
			if(extension == 'txt'){
				isSubmit=true;
				$("#msjFileTxt").html("");
			}else{
				isSubmit=false;
				$("#msjFileTxt").html("Archivo inv�lido");
			}
			//isSubmit=true;
			//$("#msjFileTxt").html("");
		}
		if(isSubmit){
			frm.submit();
		}
		//frm.submit();
	},
	<portlet:namespace/>mostrarFormularioCargaExcel : function(){
		if (formato14A.validarArchivoCarga()){
			if( formato14A.flagCarga.val()=='0' ){//proviene de archivos nuevos
				formato14A.flagCarga.val('2');//para cargar archivos excel
			}else if( formato14A.flagCarga.val()=='1' ){//proviene de archivos modificados
				formato14A.flagCarga.val('3');//para cargar archivos excel
			}
			formato14A.divOverlay.show();
		    formato14A.dialogCargaExcel.show();
		    formato14A.dialogCargaExcel.draggable();
		    formato14A.dialogCargaExcel.css({ 
		        'left': ($(window).width() / 2 - formato14A.dialogCargaExcel.width() / 2) + 'px', 
		        'top': ($(window).height()  - formato14A.dialogCargaExcel.height() ) + 'px'
		    });
		    formato14A.iniciarMensajeExcel();
		}
	},
	regresarFormularioCargaExcel : function(){
		formato14A.flagCarga.val('');
		formato14A.iniciarMensajeExcel();
		formato14A.dialogCargaExcel.hide();
		formato14A.divOverlay.hide();   
	},
	<portlet:namespace/>mostrarFormularioCargaTexto : function(){
		if (formato14A.validarArchivoCarga()){
			if( formato14A.flagCarga.val()=='0' ){//proviene de un archivo nuevo
				formato14A.flagCarga.val('4');//para cargar archivos texto
			}else if( formato14A.flagCarga.val()=='1' ){//proviene de un archivo modificado
				formato14A.flagCarga.val('5');//para archivos texto
			}
			formato14A.divOverlay.show();
			formato14A.dialogCargaTexto.show();
			formato14A.dialogCargaTexto.draggable();
			formato14A.dialogCargaTexto.css({ 
		        'left': ($(window).width() / 2 - formato14A.dialogCargaTexto.width() / 2) + 'px', 
		        'top': ($(window).height() - formato14A.dialogCargaTexto.height() ) + 'px'
		    });
			formato14A.iniciarMensajeTxt();
		}
	},
	regresarFormularioCargaTexto : function(){
		formato14A.flagCarga.val('');
		formato14A.iniciarMensajeTxt();
		formato14A.dialogCargaTexto.hide();
		formato14A.divOverlay.hide();   
	},
	iniciarMensajeExcel : function(){
		$("#msjFileExcel").html("");
	},
	iniciarMensajeTxt : function(){
		$("#msjFileTxt").html("");
	},
	<portlet:namespace/>mostrarReportePdf : function(){
		jQuery.ajax({
			url : formato14A.urlReporte+'&'+formato14A.formCommand.serialize(),
			type : 'post',
			dataType : 'json',
			data : {
				<portlet:namespace />codEmpresa: formato14A.f_empresa.val(),
				<portlet:namespace />periodoEnvio: formato14A.f_periodoEnvio.val(),
				<portlet:namespace />anoInicioVigencia: $('#anioInicioVigencia').val(),
				<portlet:namespace />anoFinVigencia: $('#anioFinVigencia').val(),
				<portlet:namespace />nombreReporte: 'formato14A',
				<portlet:namespace />nombreArchivo: 'formato14A',
				<portlet:namespace />tipoArchivo: '0'//PDF
			},
			success : function(gridData) {
				formato14A.verReporte();
			},error : function(){
				alert("Error de conexi�n.");
				formato14A.initBlockUI();
			}
		});
	},
	<portlet:namespace/>mostrarReporteExcel : function(){
		jQuery.ajax({
			url : formato14A.urlReporte+'&'+formato14A.formCommand.serialize(),
			type : 'post',
			dataType : 'json',
			data : {
				<portlet:namespace />codEmpresa: formato14A.f_empresa.val(),
				<portlet:namespace />periodoEnvio: formato14A.f_periodoEnvio.val(),
				<portlet:namespace />anoInicioVigencia: $('#anioInicioVigencia').val(),
				<portlet:namespace />anoFinVigencia: $('#anioFinVigencia').val(),
				<portlet:namespace />nombreReporte: 'formato14A',
				<portlet:namespace />nombreArchivo: 'formato14A',
				<portlet:namespace />tipoArchivo: '1'//XLS
			},
			success : function(gridData) {
				//alert('entro');
				formato14A.verReporte();
			},error : function(){
				alert("Error de conexi�n.");
				formato14A.initBlockUI();
			}
		});
	},
	verReporte : function(){
		window.open('<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ViewReport")%>','_newtab');
	},
	<portlet:namespace/>validacionFormato : function(){
		jQuery.ajax({
			url: formato14A.urlValidacion+'&'+formato14A.formCommand.serialize(),
			type : 'post',
			dataType : 'json',
			data : {
				<portlet:namespace />codEmpresa: formato14A.f_empresa.val(),
				<portlet:namespace />periodoEnvio: formato14A.f_periodoEnvio.val(),
				<portlet:namespace />anoInicioVigencia: $('#anioInicioVigencia').val(),
				<portlet:namespace />anoFinVigencia: $('#anioFinVigencia').val()
			},
			success : function(data) {
				if( data!=null ){
					formato14A.dialogObservacion.dialog("open");
					formato14A.tablaObservacion.clearGridData(true);
					formato14A.tablaObservacion.jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
					formato14A.tablaObservacion[0].refreshIndex();
					formato14A.initBlockUI();
				}

			},error : function(){
				alert("Error de conexi�n.");
				formato14A.initBlockUI();
			}
		});
	},
	<portlet:namespace/>mostrarReporteValidacion : function(){
		jQuery.ajax({
			url: formato14A.urlReporteValidacion+'&'+formato14A.formCommand.serialize(),
			type : 'post',
			dataType : 'json',
			data : {
				<portlet:namespace />codEmpresa: formato14A.f_empresa.val(),
				<portlet:namespace />periodoEnvio: formato14A.f_periodoEnvio.val(),
				<portlet:namespace />nombreReporte: 'validacion14',
				<portlet:namespace />nombreArchivo: 'validacion14',
				<portlet:namespace />tipoArchivo: '0'//PDF
			},
			success : function(gridData) {
				formato14A.verReporte();
			},error : function(){
				alert("Error de conexi�n.");
				formato14A.initBlockUI();
			}
		});
	},
	confirmarEnvioDefinitivo : function(){	
		var addhtml='�Est� seguro que desea realizar el Env�o Definitivo para el Formato 14A?';
		formato14A.dialogConfirmEnvioContent.html(addhtml);
		formato14A.dialogConfirmEnvio.dialog("open");
	},
	<portlet:namespace/>envioDefinitivo : function(){
		jQuery.ajax({
			url: formato14A.urlEnvioDefinitivo+'&'+formato14A.formCommand.serialize(),
			type : 'post',
			dataType : 'json',
			data : {
				<portlet:namespace />codEmpresa: formato14A.f_empresa.val(),
				<portlet:namespace />periodoEnvio: formato14A.f_periodoEnvio.val(),
				<portlet:namespace />anoInicioVigencia: $('#anioInicioVigencia').val(),
				<portlet:namespace />anoFinVigencia: $('#anioFinVigencia').val(),
				<portlet:namespace />nombreReporte: 'formato14A',
				<portlet:namespace />nombreArchivo: 'formato14A',
				<portlet:namespace />tipoArchivo: '0'//PDF
			},			
			success : function(data) {
				if(data.resultado == "OK"){
					var addhtml='El Env�o Definitivo se realiz� satisfactoriamente a los siguientes correos electr�nicos: '+data.correo;					
					formato14A.dialogMessageReportContent.html(addhtml);
					formato14A.dialogMessageReport.dialog("open");
					formato14A.initBlockUI();					
				}else if(data.resultado == "EMAIL"){						
					var addhtmEmail = data.correo;					
					formato14A.dialogMessageErrorContent.html(addhtmEmail);
					formato14A.dialogMessageError.dialog("open");
					formato14A.initBlockUI();
				}else if(data.resultado == "OBSERVACION"){						
					var addhtmObs = 'No se puede relizar el Env�o Definitivo del Formato 14A, primero debe subsanar las observaciones.';				
					formato14A.dialogMessageInfoContent.html(addhtmObs);
					formato14A.dialogMessageInfo.dialog("open");
					formato14A.initBlockUI();
				}else{								
					var addhtmError='Error al realizar el Env�o Definitivo del Formato 14A.';					
					formato14A.dialogMessageErrorContent.html(addhtmError);
					formato14A.dialogMessageError.dialog("open");
					formato14A.initBlockUI();
				}			
			},error : function(){
				alert("Error de conexi�n.");
				formato14A.initBlockUI();
			}
		});
	},
	buildGridsObservacion : function () {	
		formato14A.tablaObservacion.jqGrid({
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
				rownumbers: true,
				//shrinkToFit:true,
				pager: formato14A.paginadoObservacion,
			    viewrecords: true,
			   	//caption: "Formatos",
			    sortorder: "asc"
	  	});
		formato14A.tablaObservacion.jqGrid('navGrid',formato14A.paginadoObservacion,{add:false,edit:false,del:false,search: false,refresh: false});	
		formato14A.tablaObservacion.jqGrid('navButtonAdd',formato14A.paginadoObservacion,{
		       caption:"Exportar a Excel",
		       buttonicon: "ui-icon-bookmark",
		       onClickButton : function () {
		    	   var ids = formato14A.tablaObservacion.jqGrid('getDataIDs');
			       if(ids!=0){
			    	   location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';   
			       }else{			    	
			    	formato14A.dialogMessageInfoContent.html('No existe informaci�n para exportar a Excel');
					formato14A.dialogMessageInfo.dialog("open");
			       }   	    	   
		       } 
		}); 
		formato14A.tablaObservacion.jqGrid('navButtonAdd',formato14A.paginadoObservacion,{
		       caption:"Exportar a Pdf",
		       buttonicon: "ui-icon-bookmark",
		       onClickButton : function () {
		    	   var ids = formato14A.tablaObservacion.jqGrid('getDataIDs');
			       if(ids!=0){
			    	   formato14A.<portlet:namespace/>mostrarReporteValidacion();   
			       }else{			    	
			    	formato14A.dialogMessageInfoContent.html('No existe informaci�n para exportar a Pdf');
					formato14A.dialogMessageInfo.dialog("open");
			       }     	   
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
			url: formato14A.urlReporteEnvioDefinitivo+'&'+formato14A.formCommand.serialize(),
			type : 'post',
			dataType : 'json',
			data : {
				<portlet:namespace />tipoArchivo: '2'//PDF+concatenado
			},
			success : function(gridData) {
				formato14A.verReporte();
			},error : function(){
				alert("Error de conexi�n.");
				formato14A.initBlockUI();
			}
		});
	},
	<portlet:namespace/>mostrarReporteActaEnvio : function(){
		var estado = $('#descEstado').val();	
		if(estado=='Enviado'){
			jQuery.ajax({
				url: formato14A.urlReporteActaEnvio+'&'+formato14A.formCommand.serialize(),
				type : 'post',
				dataType : 'json',
				data : {
					<portlet:namespace />codEmpresa: formato14A.f_empresa.val(),
					<portlet:namespace />periodoEnvio: formato14A.f_periodoEnvio.val(),
					<portlet:namespace />anoInicioVigencia: $('#anioInicioVigencia').val(),
					<portlet:namespace />anoFinVigencia: $('#anioFinVigencia').val(),
					<portlet:namespace />tipoArchivo: '0'//PDF
				},
				success : function(gridData) {
					formato14A.verReporte();
				},error : function(){
					alert("Error de conexi�n.");
					formato14A.initBlockUI();
				}
			});
		}else{
			//alert("Primero debe realizar el env�o definitivo");
			formato14A.dialogMessageInfoContent.html('Primero debe realizar el Env�o Definitivo del Formato 14A');
			formato14A.dialogMessageInfo.dialog("open");
		}
	},
	//funcion para desabiliar campos lima
	deshabilitarLima : function(){
		//LIMA
		formato14A.f_impEsqInvL.attr("disabled",true);
		formato14A.f_impDeclJuradaL.attr("disabled",true);
		formato14A.f_impFichaVerifL.attr("disabled",true);
		formato14A.f_repEsqInvL.attr("disabled",true);
		formato14A.f_verifInfoL.attr("disabled",true);
		formato14A.f_elabArchBenefL.attr("disabled",true);
		formato14A.f_digitFichaL.attr("disabled",true);
		formato14A.f_impVolanteL.attr("disabled",true);
		formato14A.f_impAficheL.attr("disabled",true);
		formato14A.f_repFolletoL.attr("disabled",true);
		formato14A.f_spotPublicTvL.attr("disabled",true);
		formato14A.f_spotPublicRadioL.attr("disabled",true);
		formato14A.f_nroBenefL.attr("disabled",true);
		formato14A.f_promConvL.attr("disabled",true);
		formato14A.f_regFirmaConvL.attr("disabled",true);
		formato14A.f_impBandL.attr("disabled",true);
		formato14A.f_nroAgentL.attr("disabled",true);
		
		formato14A.quitarEstiloEdicionLima();
	},
	//funcion para habilitar campos lima
	habilitarLima : function(){
		formato14A.f_impEsqInvL.removeAttr("disabled");
		formato14A.f_impDeclJuradaL.removeAttr("disabled");
		formato14A.f_impFichaVerifL.removeAttr("disabled");
		formato14A.f_repEsqInvL.removeAttr("disabled");
		formato14A.f_verifInfoL.removeAttr("disabled");
		formato14A.f_elabArchBenefL.removeAttr("disabled");
		formato14A.f_digitFichaL.removeAttr("disabled");
		formato14A.f_impVolanteL.removeAttr("disabled");
		formato14A.f_impAficheL.removeAttr("disabled");
		formato14A.f_repFolletoL.removeAttr("disabled");
		formato14A.f_spotPublicTvL.removeAttr("disabled");
		formato14A.f_spotPublicRadioL.removeAttr("disabled");
		formato14A.f_nroBenefL.removeAttr("disabled");
		formato14A.f_promConvL.removeAttr("disabled");
		formato14A.f_regFirmaConvL.removeAttr("disabled");
		formato14A.f_impBandL.removeAttr("disabled");
		formato14A.f_nroAgentL.removeAttr("disabled");
		
		formato14A.estiloEdicionLima();
	},
	//poner estilos de edicion para cada columna
	estiloEdicionCabecera : function(){
		$('#anioInicioVigencia').addClass("fise-editable");
		$('#anioFinVigencia').addClass("fise-editable");
	},
	estiloEdicionRural : function(){
		formato14A.f_impEsqInvR.addClass("fise-editable");
		formato14A.f_impDeclJuradaR.addClass("fise-editable");
		formato14A.f_impFichaVerifR.addClass("fise-editable");
		formato14A.f_repEsqInvR.addClass("fise-editable");
		formato14A.f_verifInfoR.addClass("fise-editable");
		formato14A.f_elabArchBenefR.addClass("fise-editable");
		formato14A.f_digitFichaR.addClass("fise-editable");
		formato14A.f_impVolanteR.addClass("fise-editable");
		formato14A.f_impAficheR.addClass("fise-editable");
		formato14A.f_repFolletoR.addClass("fise-editable");
		formato14A.f_spotPublicTvR.addClass("fise-editable");
		formato14A.f_spotPublicRadioR.addClass("fise-editable");
		formato14A.f_nroBenefR.addClass("fise-editable");
		formato14A.f_promConvR.addClass("fise-editable");
		formato14A.f_regFirmaConvR.addClass("fise-editable");
		formato14A.f_impBandR.addClass("fise-editable");
		formato14A.f_nroAgentR.addClass("fise-editable");
	},
	estiloEdicionProvincia : function(){
		formato14A.f_impEsqInvP.addClass("fise-editable");
		formato14A.f_impDeclJuradaP.addClass("fise-editable");
		formato14A.f_impFichaVerifP.addClass("fise-editable");
		formato14A.f_repEsqInvP.addClass("fise-editable");
		formato14A.f_verifInfoP.addClass("fise-editable");
		formato14A.f_elabArchBenefP.addClass("fise-editable");
		formato14A.f_digitFichaP.addClass("fise-editable");
		formato14A.f_impVolanteP.addClass("fise-editable");
		formato14A.f_impAficheP.addClass("fise-editable");
		formato14A.f_repFolletoP.addClass("fise-editable");
		formato14A.f_spotPublicTvP.addClass("fise-editable");
		formato14A.f_spotPublicRadioP.addClass("fise-editable");
		formato14A.f_nroBenefP.addClass("fise-editable");
		formato14A.f_promConvP.addClass("fise-editable");
		formato14A.f_regFirmaConvP.addClass("fise-editable");
		formato14A.f_impBandP.addClass("fise-editable");
		formato14A.f_nroAgentP.addClass("fise-editable");
	},
	estiloEdicionLima : function(){
		formato14A.f_impEsqInvL.addClass("fise-editable");
		formato14A.f_impDeclJuradaL.addClass("fise-editable");
		formato14A.f_impFichaVerifL.addClass("fise-editable");
		formato14A.f_repEsqInvL.addClass("fise-editable");
		formato14A.f_verifInfoL.addClass("fise-editable");
		formato14A.f_elabArchBenefL.addClass("fise-editable");
		formato14A.f_digitFichaL.addClass("fise-editable");
		formato14A.f_impVolanteL.addClass("fise-editable");
		formato14A.f_impAficheL.addClass("fise-editable");
		formato14A.f_repFolletoL.addClass("fise-editable");
		formato14A.f_spotPublicTvL.addClass("fise-editable");
		formato14A.f_spotPublicRadioL.addClass("fise-editable");
		formato14A.f_nroBenefL.addClass("fise-editable");
		formato14A.f_promConvL.addClass("fise-editable");
		formato14A.f_regFirmaConvL.addClass("fise-editable");
		formato14A.f_impBandL.addClass("fise-editable");
		formato14A.f_nroAgentL.addClass("fise-editable");
	},
	//quitar estilos
	quitarEstiloEdicionCabecera : function(){
		$('#anioInicioVigencia').removeClass("fise-editable");
		$('#anioFinVigencia').removeClass("fise-editable");
	},
	quitarEstiloEdicionRural : function(){
		formato14A.f_impEsqInvR.removeClass("fise-editable");
		formato14A.f_impDeclJuradaR.removeClass("fise-editable");
		formato14A.f_impFichaVerifR.removeClass("fise-editable");
		formato14A.f_repEsqInvR.removeClass("fise-editable");
		formato14A.f_verifInfoR.removeClass("fise-editable");
		formato14A.f_elabArchBenefR.removeClass("fise-editable");
		formato14A.f_digitFichaR.removeClass("fise-editable");
		formato14A.f_impVolanteR.removeClass("fise-editable");
		formato14A.f_impAficheR.removeClass("fise-editable");
		formato14A.f_repFolletoR.removeClass("fise-editable");
		formato14A.f_spotPublicTvR.removeClass("fise-editable");
		formato14A.f_spotPublicRadioR.removeClass("fise-editable");
		formato14A.f_nroBenefR.removeClass("fise-editable");
		formato14A.f_promConvR.removeClass("fise-editable");
		formato14A.f_regFirmaConvR.removeClass("fise-editable");
		formato14A.f_impBandR.removeClass("fise-editable");
		formato14A.f_nroAgentR.removeClass("fise-editable");
	},
	quitarEstiloEdicionProvincia : function(){
		formato14A.f_impEsqInvP.removeClass("fise-editable");
		formato14A.f_impDeclJuradaP.removeClass("fise-editable");
		formato14A.f_impFichaVerifP.removeClass("fise-editable");
		formato14A.f_repEsqInvP.removeClass("fise-editable");
		formato14A.f_verifInfoP.removeClass("fise-editable");
		formato14A.f_elabArchBenefP.removeClass("fise-editable");
		formato14A.f_digitFichaP.removeClass("fise-editable");
		formato14A.f_impVolanteP.removeClass("fise-editable");
		formato14A.f_impAficheP.removeClass("fise-editable");
		formato14A.f_repFolletoP.removeClass("fise-editable");
		formato14A.f_spotPublicTvP.removeClass("fise-editable");
		formato14A.f_spotPublicRadioP.removeClass("fise-editable");
		formato14A.f_nroBenefP.removeClass("fise-editable");
		formato14A.f_promConvP.removeClass("fise-editable");
		formato14A.f_regFirmaConvP.removeClass("fise-editable");
		formato14A.f_impBandP.removeClass("fise-editable");
		formato14A.f_nroAgentP.removeClass("fise-editable");
	},
	quitarEstiloEdicionLima : function(){
		formato14A.f_impEsqInvL.removeClass("fise-editable");
		formato14A.f_impDeclJuradaL.removeClass("fise-editable");
		formato14A.f_impFichaVerifL.removeClass("fise-editable");
		formato14A.f_repEsqInvL.removeClass("fise-editable");
		formato14A.f_verifInfoL.removeClass("fise-editable");
		formato14A.f_elabArchBenefL.removeClass("fise-editable");
		formato14A.f_digitFichaL.removeClass("fise-editable");
		formato14A.f_impVolanteL.removeClass("fise-editable");
		formato14A.f_impAficheL.removeClass("fise-editable");
		formato14A.f_repFolletoL.removeClass("fise-editable");
		formato14A.f_spotPublicTvL.removeClass("fise-editable");
		formato14A.f_spotPublicRadioL.removeClass("fise-editable");
		formato14A.f_nroBenefL.removeClass("fise-editable");
		formato14A.f_promConvL.removeClass("fise-editable");
		formato14A.f_regFirmaConvL.removeClass("fise-editable");
		formato14A.f_impBandL.removeClass("fise-editable");
		formato14A.f_nroAgentL.removeClass("fise-editable");
	},
	
	//funcion para poner focus al momento de validar y campo
	ponerFocus : function(cadena){		
		cadena.focus();
	},
		
	//funcion para verificar la ultima etapa del formato		
	validarUltimaEtapaF14A : function(){
		var valorObtenido = $('#etapaFinal').val();	
		console.debug("Entro a etapa final: "+valorObtenido);			
		if(valorObtenido=='SI'){
			var addhtmAlert='No se puede realizar esta operaci�n debido a que el Formato se encuentra en una etapa avanzada.';					
			formato14A.dialogMessageWarningContent.html(addhtmAlert);
			formato14A.dialogMessageWarning.dialog("open");			
			cod_focus = $('#etapaFinal');		   
			return false;
			
		}else{			
			return true;				
		}			
	},		
	
	
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
		formato14A.dialogMessage.dialog({
			modal: true,
			autoOpen: false,
			width: 450,
			buttons: {
				Ok: function() {
					$( this ).dialog("close");
				}
			}
		});
		formato14A.dialogMessageReport.dialog({
			modal: true,
			autoOpen: false,
			width: 450,
			buttons: {
				'Ver Acta': function() {
					formato14A.<portlet:namespace/>mostrarReporteEnvioDefinitivo();
					$( this ).dialog("close");
					formato14A.divFormato.hide();
					formato14A.divHome.show();
					formato14A.botonBuscar.trigger('click');
				},
				Ok: function() {
					$( this ).dialog("close");
					formato14A.divFormato.hide();
					formato14A.divHome.show();
					formato14A.botonBuscar.trigger('click');
				}
			},
		 	close: function(event,ui){
				formato14A.divFormato.hide();
				formato14A.divHome.show();
				formato14A.botonBuscar.trigger('click');
	       	}
		});
		formato14A.dialogConfirm.dialog({
			modal: true,
			height: 200,
			width: 450,
			autoOpen: false,
			buttons: {
				"Si": function() {
					formato14A.eliminarFormato(cod_Empresa,ano_Presentacion,mes_Presentacion,ano_Inicio_Vigencia,ano_Fin_Vigencia,cod_Etapa);
					$( this ).dialog("close");
				},
				"No": function() {				
					$( this ).dialog("close");
				}
			}
		});
		formato14A.dialogConfirmEnvio.dialog({
			modal: true,
			height: 200,
			width: 450,			
			autoOpen: false,
			buttons: {
				"Si": function() {
					formato14A.<portlet:namespace/>envioDefinitivo();
					$( this ).dialog("close");
				},
				"No": function() {				
					$( this ).dialog("close");
				}
			}
		});
		formato14A.dialogCarga.dialog({
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
		formato14A.dialogError.dialog({
			modal: true,
			width: 700,
			autoOpen: false,
			buttons: {
				Ok: function() {
					$( this ).dialog("close");
				}
			}
		});
		formato14A.dialogObservacion.dialog({
			modal: true,
			width: 700,
			autoOpen: false,
			buttons: {
				Cerrar: function() {
					$( this ).dialog("close");
				}
			}
		});
		//addd
		formato14A.dialogMessageInfo.dialog({
			modal: true,
			autoOpen: false,
			width: 450,
			buttons: {
				Ok: function() {
					$( this ).dialog("close");
				}
			}
		});
		formato14A.dialogMessageWarning.dialog({
			modal: true,
			autoOpen: false,
			width: 450,
			buttons: {
				Ok: function() {
					formato14A.ponerFocus(cod_focus);
					$( this ).dialog("close");
				}
			}
		});
		formato14A.dialogMessageError.dialog({
			modal: true,
			autoOpen: false,
			width: 450,
			buttons: {
				Ok: function() {
					$( this ).dialog("close");
				}
			}
		});
	}
};
</script>