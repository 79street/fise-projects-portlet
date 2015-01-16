<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@page import="com.liferay.portal.kernel.util.PrefsPropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects />

<script type="text/javascript" src="/fise-projects-portlet/js/fise.js"></script>

<script type="text/javascript">
var formato13A= {
	tablaResultados:null,
	paginadoResultados:null,
	tablaDeclaracion:null,
	paginadoDeclaracion:null,
	//add
	tablaDeclaracionView:null,
	paginadoDeclaracionView:null,
	tablaObservacion:null,
	paginadoObservacion:null,
	//
	//URL
	urlBusqueda: null,
	urlBusquedaDetalle: null,
	urlCargaDeclaracion: null,
	urlProvincias:null,
	urlDistritos:null,
	urlReporte:null,
	//add
	urlValidacion:null,
	urlReporteValidacion:null,
	urlEnvioDefinitivo:null,
	urlReporteEnvioDefinitivo:null,
	urlDelete:null,
	urlDeleteDetalle:null,
	
	urlReporteActaEnvio:null,
	//
	
	//addd
	dialogMessageInfo:null,
	dialogMessageInfoContent:null,
	dialogMessageWarning:null,
	dialogMessageWarningContent:null,
	
	botonReportePdf:null,
	botonReporteExcel:null,
	//add
	botonValidacion:null,
	botonEnvioDefinitivo:null,
	botonActaEnvio:null,
	//
	
	botonBuscar:null,
	formBusqueda: null,
	formNuevo : null,
	formDetalle : null,
	botonCrearFormato : null,
	botonGuardarDetalle:null,
	codEmpresa : null,
	peridoDeclaracion : null,
	portletID:null,
	mensajeObteniendoDatos:null,
	urlACrud:null,
	command:null,
	
	btnGuardarCabecera:null,
	urlGuardarCabecera:null,
	dlgConfirmacion:null,
	txtPeriodo:null,
	txtGrupo:null,
	txtEstado:null,
	lblGrupo:null,
	lblEstado:null,
	divPeriodoVigencia:null,
	txtInicioVig:null,
	txtFinVig:null,
	
	labelEstado:null,
	labelGrupoInfo:null,
	estado:null,
	grupoInfo:null,
	
	divInformacion:null,
	
	btnExcel:null,
	dialogCargaExcel:null,
	btnCargarFormatoExcel:null,
	
	btnTxt:null,
	dialogCargaTexto:null,
	btnCargarFormatoTexto:null,
	
	divOverlay:null,
	//add
	dialogObservacion:null,
	dialogMessageReport:null,
	dialogConfirmEnvio:null,
	dialogMessageReportContent:null,
	dialogConfirmEnvioContent:null,
	dialogConfirmDetalle:null,
	dialogConfirmDetalleContent:null,
	dialogConfirm:null,
	dialogConfirmContent:null,
	
	//addd
	dialogMessageInfoCrud:null,
	dialogMessageInfoCrudContent:null,
	dialogMessageWarningCrud:null,
	dialogMessageWarningCrudContent:null,
	dialogMessageErrorCrud:null,
	dialogMessageErrorCrudContent:null,
	//

	codDepa:null,
	codProv:null,
	codDist:null,
	
	//detalleCRUD
	//valores constantes para edelnor y luz del sur
	cod_empresa_edelnor:null,
	cod_empresa_luz_sur:null,
	//
	
	flagPeriodoDetalle:null,
	
	msgTransaccionDetalle:null,
	
	codDepartamentoHidden:null,
	codProvinciaHidden:null,
	codDistritoHidden:null,
	
	descDepa:null,
	descProv:null,
	descDist:null,
	
	codEmpresaDetalle:null,
	anoPresentacionDetalle:null,
	mesPresentacionDetalle:null,
	etapaDetalle:null,
	
	anoIniVigenciaDetalle:null,
	anoFinVigenciaDetalle:null,
	anoAltaDetalle:null,
	mesAltaDetalle:null,
	localidadDetalle:null,
	sedeDetalle:null,
	
	//valores sector tipico
	st1Detalle:null,
	st2Detalle:null,
	st3Detalle:null,
	st4Detalle:null,
	st5Detalle:null,
	st6Detalle:null,
	stserDetalle:null,
	stespDetalle:null,
	sttotalDetalle:null,
	//
	idZonaBenefDetalle:null,
	mensajeEliminandoDetalle:null,
	
	 botonAnadirFormato:null,
	 botonRegresarBusqueda:null,
	 
	 emailConfigured:null,
	 
	 lblMessageInicial:null,
	 dialogMessageGeneralInicial:null,
	 txtAnioInicio:null,
	 txtAnioFin:null,
	 dialogMessageGeneral:null,
     lblMessage:null,
     
     
	 dialogMessageDetalle:null,
	 dialogMessageDetalleContent:null,
	 
	//addd
	dialogMessageWarningDetalle:null,
	dialogMessageWarningDetalleContent:null,
	dialogMessageErrorDetalle:null,
	dialogMessageErrorDetalleContent:null,
	 
	init : function(urlNuevo,urlView,urlEdit){
		
		this.tablaResultados=$("#<portlet:namespace/>grid_formato");
		this.paginadoResultados='#<portlet:namespace/>pager_formato';
		this.urlBusqueda='<portlet:resourceURL id="busqueda" />';
		this.botonBuscar=$("#<portlet:namespace/>buscarFormato");
		this.formBusqueda=$('#formato13AGartCommand');
		this.botonCrearFormato=$('#<portlet:namespace/>crearFormato');
		this.dlgConfirmacion=$("#<portlet:namespace/>divDlgDelete");
		this.divOverlay=$("#<portlet:namespace/>divOverlay");
		this.mensajeObteniendoDatos='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Obteniendo Datos </h3>';
		//this.urlACrud='<portlet:resourceURL id="getData" />';
		this.command=$('#formato13AGartCommand');
		this.urlACrud=urlView;
		this.urlDelete='<portlet:resourceURL id="deleteCabecera" />';
		
		this.dialogConfirm=$("#<portlet:namespace/>dialog-confirm");
		this.dialogConfirmContent=$("#<portlet:namespace/>dialog-confirm-content");
		
		
		this. dialogMessageGeneralInicial=$("#dialogMessageGeneralInicio");
		this.lblMessageInicial=$("#lblMessageInicio");
		this.txtAnioInicio=$("#anioInicio");
		this.txtAnioFin=$("#anioFin");
		
		this.dialogMessageInfo=$("#<portlet:namespace/>dialog-message-info");
		this.dialogMessageInfoContent=$("#<portlet:namespace/>dialog-message-info-content");
		this.dialogMessageWarning=$("#<portlet:namespace/>dialog-message-warning");
		this.dialogMessageWarningContent=$("#<portlet:namespace/>dialog-message-warning-content");
		
		
		formato13A.initDialogs();
		
		formato13A.botonCrearFormato.click(function() {
			formato13A.blockUI();
			
			location.href=urlNuevo;
		});
		
		formato13A.botonBuscar.click(function() {
			formato13A.buscarFormatos();
		});
		
		formato13A.buildGridsBusqueda();
		formato13A.botonBuscar.trigger('click');
		
		
		
	},
	validateInputAnioTxt : function(inicio,fin){
		 if((inicio.val().length>0 && inicio.val().length<4 )|| (fin.val().length>0 && fin.val().length<4)){
			 //formato13A.lblMessageInicial.html("El año debe contener 4 dígitos");
			 //formato13A.dialogMessageGeneralInicial.dialog("open");
			 formato13A.dialogMessageWarningContent.html("Debe ingresar un Año Declarado desde válido");
			 formato13A.dialogMessageWarning.dialog("open");
   		
   	 }else if(inicio.val().length>0){
   		 if(parseFloat(fin.val())<parseFloat(inicio.val())){
   			//formato13A.lblMessageInicial.html("El año final debe ser mayor o igual al inicial");
   			//formato13A.dialogMessageGeneralInicial.dialog("open");
   			formato13A.dialogMessageWarningContent.html("El Año Declarado desde no puede exceder al año declarado hasta");
			formato13A.dialogMessageWarning.dialog("open");
   		 }
   	 }else if(fin.val().length>0){
   		 if(parseFloat(inicio.val())>parseFloat(fin.val())){
   			//formato13A.lblMessageInicial.html("El año inicial debe ser menor o igual al final");
   			//formato13A.dialogMessageGeneralInicial.dialog("open");
   			formato13A.dialogMessageWarningContent.html("El Año Declarado desde no puede exceder al año declarado hasta");
			formato13A.dialogMessageWarning.dialog("open");
   		 }
   		 
   	 }
		//var n=formato13A.validateInputAnio(inicio,fin);
	},
	validateInputAnio : function(inicio,fin){//validateInputAnio
		 if((inicio.val().length>0 && inicio.val().length<4 )|| (fin.val().length>0 && fin.val().length<4)){
			 //formato13A.lblMessageInicial.html("El año debe contener 4 dígitos");
			 //formato13A.dialogMessageGeneralInicial.dialog("open");
			 formato13A.dialogMessageWarningContent.html("Debe ingresar un Año Declarado desde válido");
			 formato13A.dialogMessageWarning.dialog("open");
			 return false; 
   		
   	 }else if(inicio.val().length>0){
   		 if(parseFloat(fin.val())<parseFloat(inicio.val())){
   			 fin.focus();
   			//formato13A.lblMessageInicial.html("El año final debe ser mayor o igual al inicial");
   			//formato13A.dialogMessageGeneralInicial.dialog("open"); 
   			formato13A.dialogMessageWarningContent.html("El Año Declarado Desde no puede exceder al Año Declarado Hasta");
			formato13A.dialogMessageWarning.dialog("open");
				 return false; 
   		 }
   	 }else if(fin.val().length>0){
   		 if(parseFloat(inicio.val())>parseFloat(fin.val())){
   			 inicio.focus();
   			//formato13A.lblMessageInicial.html("El año inicial debe ser menor o igual al final");
   			//formato13A.dialogMessageGeneralInicial.dialog("open"); 
   			formato13A.dialogMessageWarningContent.html("El Año Declarado Desde no puede exceder al Año Declarado Hasta");
			formato13A.dialogMessageWarning.dialog("open");
				 return false; 
   		 }
   		 
   	 }
  
	 return true; 
},
	initCRUD : function(operacion,urlAnadirFormato,urlRegresarBusqueda){
		this.portletID='<%=PortalUtil.getPortletId(renderRequest)%>';
		this.urlCargaDeclaracion='<portlet:resourceURL id="cargaPeriodoDeclaracion" />';
		this.urlBusquedaDetalle='<portlet:resourceURL id="busquedaDetalle" />';
		this.formNuevo=$('#formato13AGartCommand');
		this.codEmpresa=$('#codEmpresa');
		this.peridoDeclaracion=$('#peridoDeclaracion');
		this.tablaDeclaracion=$("#<portlet:namespace/>grid_formato_declaracion");
		this.paginadoDeclaracion='#<portlet:namespace/>pager_formato_declaracion';

		this.divInformacion=$("#<portlet:namespace/>divInformacion");
		
		this.emailConfigured='<%=PrefsPropsUtil.getString(PropsKeys.MAIL_SESSION_MAIL_SMTP_USER)%>';
		
		//add
		this.tablaDeclaracionView=$("#<portlet:namespace/>grid_formato_declaracionView");
		this.paginadoDeclaracionView='#<portlet:namespace/>pager_formato_declaracionView';
		this.tablaObservacion=$("#<portlet:namespace/>grid_observacion");
		this.paginadoObservacion='#<portlet:namespace/>pager_observacion';
		//
		this.botonAnadirFormato=$('#<portlet:namespace/>anadirFormato');

		this.botonRegresarBusqueda=$('#<portlet:namespace/>regresarBusqueda');
		
		this.btnGuardarCabecera=$('#<portlet:namespace/>guardarFormato');
		//this.urlGuardarCabecera='<portlet:actionURL name="saveNuevoFormato"/>';
		this.urlGuardarCabecera='<portlet:resourceURL id="saveNuevoFormato"/>';
		
		this.txtPeriodo = $('#txtperiodo');
		this.txtGrupo = $('#txtgrupo');
		this.txtEstado = $('#txtestado');
		this.lblGrupo= $('#lblGrupo');
		this.lblEstado= $('#lblEstado');
		this.divPeriodoVigencia = $('#divVigencia');
		
		this.txtInicioVig = $('#txtinicioVig');
		this.txtFinVig = $('#txtfinVig');
		
		this.estado=$('#descestado');
		this.grupoInfo=$('#descGrupoInformacion');
		this.labelEstado=$('#o_descestado');
		this.labelGrupoInfo=$('#o_descGrupoInformacion');
		
		this.btnExcel=$('#<portlet:namespace/>showDialogUploadExcel');
		this.dialogCargaExcel=$("#<portlet:namespace/>dialog-form-cargaExcel");
		this.btnCargarFormatoExcel=$('#<portlet:namespace/>cargarFormatoExcel');
		this.divOverlay=$("#<portlet:namespace/>divOverlay");
		//add
		this.dialogObservacion=$("#<portlet:namespace/>dialog-form-observacion");
		this.dialogMessageReport=$("#<portlet:namespace/>dialog-message-report");
		this.dialogMessageReportContent=$("#<portlet:namespace/>dialog-message-report-content");
		this.dialogConfirmEnvio=$("#<portlet:namespace/>dialog-confirm-envio");
		this.dialogConfirmEnvioContent=$("#<portlet:namespace/>dialog-confirm-envio-content");
		this.dialogConfirmDetalle=$("#<portlet:namespace/>dialog-confirm-detalle");
		this.dialogConfirmDetalleContent=$("#<portlet:namespace/>dialog-confirm-detalle-content");
		//
		
		this.btnTxt=$('#<portlet:namespace/>showDialogUploadTxt');
		this.dialogCargaTexto=$("#<portlet:namespace/>dialog-form-cargaTxt");
		this.btnCargarFormatoTexto=$('#<portlet:namespace/>cargarFormatoTxt');
		
		this.urlReporte='<portlet:resourceURL id="reporte" />';
		this.botonReportePdf=$("#<portlet:namespace/>reportePdf");
		this.botonReporteExcel=$("#<portlet:namespace/>reporteExcel");
		//add
		this.urlValidacion='<portlet:resourceURL id="validacion" />';
		this.urlReporteValidacion='<portlet:resourceURL id="reporteValidacion" />';
		this.urlEnvioDefinitivo='<portlet:resourceURL id="envioDefinitivo" />';
		this.urlReporteEnvioDefinitivo='<portlet:resourceURL id="reporteEnvioDefinitivo" />';
		this.urlReporteActaEnvio='<portlet:resourceURL id="reporteActaEnvioView" />';
		
		this.botonActaEnvio=$("#<portlet:namespace/>reporteActaEnvio");
		
		this.botonValidacion=$("#<portlet:namespace/>validacionFormato");
		this.botonEnvioDefinitivo=$("#<portlet:namespace/>envioDefinitivo");
		//
		this.mensajeEliminandoDetalle='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Eliminando </h3>';
		this.urlDeleteDetalle='<portlet:resourceURL id="deleteDetalle" />';
		
		 this.dialogMessageGeneral=$("#dialogMessageGeneral");
         this.lblMessage=$("#lblMessage");
         
         this.dialogMessageInfoCrud=$("#<portlet:namespace/>dialog-message-info-crud");
 		this.dialogMessageInfoCrudContent=$("#<portlet:namespace/>dialog-message-info-content-crud");
 		this.dialogMessageWarningCrud=$("#<portlet:namespace/>dialog-message-warning-crud");
 		this.dialogMessageWarningCrudContent=$("#<portlet:namespace/>dialog-message-warning-content-crud");
 		this.dialogMessageErrorCrud=$("#<portlet:namespace/>dialog-message-error-crud");
 		this.dialogMessageErrorCrudContent=$("#<portlet:namespace/>dialog-message-error-content-crud");
		
		formato13A.initDialogsCRUD();
		
		formato13A.btnExcel.click(function() {formato13A.<portlet:namespace/>showUploadExcel();});
		formato13A.btnCargarFormatoExcel.click(function() {formato13A.<portlet:namespace/>cargarFormatoExcel();});
		formato13A.btnTxt.click(function() {formato13A.<portlet:namespace/>showUploadTxt();});
		formato13A.btnCargarFormatoTexto.click(function() {formato13A.<portlet:namespace/>cargarFormatoTxt();});
		
		formato13A.btnGuardarCabecera.click(function(){formato13A.savecabecera();});
		
		formato13A.buildGridsDeclaracion();
		//para modo view
		formato13A.buildGridsDeclaracionView();
		
		this.buildGridsObservacion();
		
	
		
		
		if(operacion=='CREATE'){
			formato13A.tipoOperacion=operacion;
			formato13A.codEmpresa.change(function(){
				$.when(formato13A.cargarPeriodoDeclaracion()).then(formato13A.buscarDetalles);
			});
			
			formato13A.codEmpresa.trigger('change');

			formato13A.botonAnadirFormato.click(function(){
				formato13A.blockUI();
				//---formato13A.formNuevo.attr('action',urlAnadirFormato+'&codEmpresa='+formato13A.codEmpresa.val()+'&peridoDeclaracion='+formato13A.peridoDeclaracion.val()+'&strip=0').removeAttr('enctype').submit();
				formato13A.formNuevo.attr('action',urlAnadirFormato+'&strip=0').removeAttr('enctype').submit();
			});
			
			formato13A.divInformacion.hide();
			
			formato13A.botonRegresarBusqueda.click(function(){
				formato13A.blockUI();
				location.href=urlRegresarBusqueda;
			});
			

		}if(operacion=='READ'){
			
			formato13A.tipoOperacion=operacion;
			formato13A.showCamposOculto();
			formato13A.buscarDetalles();
			formato13A.botonReportePdf.click(function() {formato13A.<portlet:namespace/>mostrarReportePdf();});
			formato13A.botonReporteExcel.click(function() {formato13A.<portlet:namespace/>mostrarReporteExcel();});
			
			formato13A.botonActaEnvio.click(function() {formato13A.<portlet:namespace/>mostrarReporteActaEnvio();});
			
			formato13A.botonRegresarBusqueda.click(function(){
				formato13A.blockUI();
				location.href=urlRegresarBusqueda;
			});
			
			formato13A.divInformacion.show();
			formato13A.labelEstado.val(formato13A.estado.val());
			formato13A.labelGrupoInfo.val(formato13A.grupoInfo.val());
			
			//formato13A.botonAnadirFormato.css("display","none");
			//formato13A.btnGuardarCabecera.css("display","none");
			
		
			
		}if(operacion=='UPDATE'){
			
			formato13A.showCamposOculto();
			formato13A.buscarDetalles();
			//formato13A.botonAnadirFormato.css("display","block");
			//formato13A.btnGuardarCabecera.css("display","none");
			

            formato13A.botonAnadirFormato.click(function(){

				formato13A.blockUI();
				//--formato13A.formNuevo.attr('action',urlAnadirFormato+'&codEmpresa='+formato13A.codEmpresa.val()+'&peridoDeclaracion='+formato13A.peridoDeclaracion.val()+'&strip=0').removeAttr('enctype').submit();
				formato13A.formNuevo.attr('action',urlAnadirFormato+'&strip=0').removeAttr('enctype').submit();
			});
			
			formato13A.botonRegresarBusqueda.click(function(){
				formato13A.blockUI();
				location.href=urlRegresarBusqueda;
			});
			
			formato13A.divInformacion.show();
			formato13A.labelEstado.val(formato13A.estado.val());
			formato13A.labelGrupoInfo.val(formato13A.grupoInfo.val());
			
			//validacion y envio definitivo
			formato13A.botonValidacion.click(function() {formato13A.<portlet:namespace/>validacionFormato();});
			formato13A.botonEnvioDefinitivo.click(function() {formato13A.confirmarEnvioDefinitivo();});
			
		}
		
		
	},
	
	initCRUDDetalle : function(operacion,urlGuardarDetalle,urlRegresarDetalle,urlRegresarNuevo){
		this.formDetalle=$("#formato13AGartCommand");
		
		this.flagPeriodoDetalle=$("#flagPeriodoEjecucion");
		this.msgTransaccionDetalle=$("#msgTransaccionDetalle");
		
		this.codDepa=$("select[name='codDepartamento']");
		this.codProv=$("select[name='codProvincia']");
		this.codDist=$("select[name='codDistrito']");
		
		//
		this.codDepartamentoHidden=$('#codDepartamentoHidden');
		this.codProvinciaHidden=$('#codProvinciaHidden');
		this.codDistritoHidden=$('#codDistritoHidden');
		
		this.descDepa=$('#descDepartamento');
		this.descProv=$('#descProvincia');
		this.descDist=$('#descDistrito');
		
		this.urlProvincias='<portlet:resourceURL id="provincias" />';
		this.urlDistritos='<portlet:resourceURL id="distritos" />';
		this.botonGuardarDetalle=$('#<portlet:namespace/>guardarDetalle');
		var botonRegresarDetalle=$('#<portlet:namespace/>regresarFormato');
		//valores de cabecera
		this.codEmpresaDetalle=$('#codEmpresa');
		this.anoPresentacionDetalle=$('#anioPresentacion');
		this.mesPresentacionDetalle=$('#mesPresentacion');
		this.etapaDetalle=$('#etapa');
		
		this.anoIniVigenciaDetalle=$('#anioInicioVigencia');
		this.anoFinVigenciaDetalle=$('#anioFinVigencia');
		this.anoAltaDetalle=$('#anioAlta');
		this.mesAltaDetalle=$('#mesAlta');
		this.localidadDetalle=$('#localidad');
		this.sedeDetalle=$('#nombreSede');
		
		//valores de sector tipico
		this.st1Detalle=$('#st1');
		this.st2Detalle=$('#st2');
		this.st3Detalle=$('#st3');
		this.st4Detalle=$('#st4');
		this.st5Detalle=$('#st5');
		this.st6Detalle=$('#st6');
		this.stserDetalle=$('#stser');
		this.stespDetalle=$('#stesp');
		this.sttotalDetalle=$('#total');
		//
		this.idZonaBenefDetalle=$('#idZonaBenef');
		
		//valores constantes para edelnor y luz del sur
		this.cod_empresa_edelnor=$("#codEdelnor");
		this.cod_empresa_luz_sur=$("#codLuzSur");
		//
		
		//dialogs
		this.dialogMessageDetalle=$("#<portlet:namespace/>dialog-message-detalle");
		this.dialogMessageDetalleContent=$("#<portlet:namespace/>dialog-message-detalle-content");
		
		this.dialogMessageWarningDetalle=$("#<portlet:namespace/>dialog-message-warning-detalle");
		this.dialogMessageWarningDetalleContent=$("#<portlet:namespace/>dialog-message-warning-content-detalle");
		this.dialogMessageErrorDetalle=$("#<portlet:namespace/>dialog-message-error-detalle");
		this.dialogMessageErrorDetalleContent=$("#<portlet:namespace/>dialog-message-error-content-detalle");
		
		formato13A.initDialogsCRUDDetalle();
		
		$('input.target[type=text]').on('change', function(){
			formato13A.calculoTotal();
		});
		
		<c:if test="${crud =='CREATE'}">
			formato13A.codDepa.change(function(){
				formato13A.listarProvincias(formato13A.codDepa.val());
			});
			
			formato13A.codProv.change(function(){
				formato13A.listarDistritos(formato13A.codProv.val());
			});
			
			formato13A.botonGuardarDetalle.click(function(){
				
				if( formato13A.validarFormatoDetalle() ){
					formato13A.formDetalle.attr('action',urlGuardarDetalle+'&crud='+operacion).submit();
				}
				
			});
			
			botonRegresarDetalle.click(function(){
				formato13A.blockUI();
				//--location.href=urlRegresarDetalle+'&crud='+operacion+'&codEmpresa='+formato13A.codEmpresaDetalle.val()+'&anioPresentacion='+formato13A.anoPresentacionDetalle.val()+'&mesPresentacion='+formato13A.mesPresentacionDetalle.val()+'&etapa='+formato13A.etapaDetalle.val()+'&tipo=1';
				location.href=urlRegresarNuevo+'&codEmpresa='+formato13A.codEmpresaDetalle.val()+'&anioPresentacion='+formato13A.anoPresentacionDetalle.val()+'&mesPresentacion='+formato13A.mesPresentacionDetalle.val()+'&etapa='+formato13A.etapaDetalle.val();
			});
			
			formato13A.codDepa.val(formato13A.codDepartamentoHidden.val());
			formato13A.listarProvinciasEdit(formato13A.codDepartamentoHidden.val(),formato13A.codProvinciaHidden.val(),formato13A.codDistritoHidden.val());
			
			formato13A.soloNumerosEnteros();
			
			formato13A.mostrarPeriodoEjecucion();
			formato13A.estiloEdicionDetalle();
			
			formato13A.calculoTotal();
			
			//mostramos el mensaje de informacion
			if( formato13A.msgTransaccionDetalle.val()=='OK' ){
				var addhtml='El Detalle de Localidades de la Zona de Beneficiarios seleccionado se guardó satisfactoriamente';
				formato13A.dialogMessageDetalleContent.html(addhtml);
				formato13A.dialogMessageDetalle.dialog("open");
			}else if( formato13A.msgTransaccionDetalle.val()=='ERROR1' ){
				var addhtml='Ya existe un Detalle de Localidades para la Zona, Periodo y Distribuidora Eléctrica seleccionada';
				formato13A.dialogMessageErrorDetalleContent.html(addhtml);
				formato13A.dialogMessageErrorDetalle.dialog("open");
			}else if( formato13A.msgTransaccionDetalle.val()=='ERROR2' ){
				var addhtml='Se produjo un error al guardar el Detalle de Localidades para la Zona';
				formato13A.dialogMessageErrorDetalleContent.html(addhtml);
				formato13A.dialogMessageErrorDetalle.dialog("open");
			}

		</c:if>
		
		<c:if test="${crud =='READ'}">
			botonRegresarDetalle.click(function(){
				formato13A.blockUI();
				location.href=urlRegresarDetalle+'&crud='+operacion+'&codEmpresa='+formato13A.codEmpresaDetalle.val()+'&anioPresentacion='+formato13A.anoPresentacionDetalle.val()+'&mesPresentacion='+formato13A.mesPresentacionDetalle.val()+'&etapa='+formato13A.etapaDetalle.val()+'&tipo=0';
				//location.href=urlRegresarDetalle+'&crud='+operacion+'&'+formato13A.formDetalle.serialize()+'&tipo=0';
			});
			
			formato13A.codDepa.val(formato13A.codDepartamentoHidden.val());
			formato13A.construirProvincia(formato13A.codProvinciaHidden.val(), formato13A.descProv.val());
			formato13A.construirDistrito(formato13A.codDistritoHidden.val(), formato13A.descDist.val());
			
			formato13A.quitarEstiloEdicionVigenciaDetalle();
			formato13A.quitarEstiloEdicionDetalle();
			
		</c:if>
		
		<c:if test="${crud =='READCREATEUPDATE'}">
			botonRegresarDetalle.click(function(){
				formato13A.blockUI();
				location.href=urlRegresarDetalle+'&crud='+operacion+'&codEmpresa='+formato13A.codEmpresaDetalle.val()+'&anioPresentacion='+formato13A.anoPresentacionDetalle.val()+'&mesPresentacion='+formato13A.mesPresentacionDetalle.val()+'&etapa='+formato13A.etapaDetalle.val()+'&tipo=1';
				//location.href=urlRegresarDetalle+'&crud='+operacion+'&'+formato13A.formDetalle.serialize()+'&tipo=0';
			});
			
			formato13A.codDepa.val(formato13A.codDepartamentoHidden.val());
			formato13A.construirProvincia(formato13A.codProvinciaHidden.val(), formato13A.descProv.val());
			formato13A.construirDistrito(formato13A.codDistritoHidden.val(), formato13A.descDist.val());
			
			formato13A.quitarEstiloEdicionVigenciaDetalle();
			formato13A.quitarEstiloEdicionDetalle();
			
		</c:if>
		
		<c:if test="${crud =='UPDATE'}">
		
			formato13A.botonGuardarDetalle.click(function(){
				
				if( formato13A.validarFormatoDetalle() ){
					formato13A.formDetalle.attr('action',urlGuardarDetalle+'&crud='+operacion+'&idZonaBenef='+formato13A.idZonaBenefDetalle.val()).submit();
				}
				
			});
		
			botonRegresarDetalle.click(function(){
				formato13A.blockUI();
				location.href=urlRegresarDetalle+'&crud='+operacion+'&codEmpresa='+formato13A.codEmpresaDetalle.val()+'&anioPresentacion='+formato13A.anoPresentacionDetalle.val()+'&mesPresentacion='+formato13A.mesPresentacionDetalle.val()+'&etapa='+formato13A.etapaDetalle.val()+'&tipo=1';
				//location.href=urlRegresarDetalle+'&crud='+operacion+'&'+formato13A.formDetalle.serialize()+'&tipo=1';
			});
			
			formato13A.codDepa.val(formato13A.codDepartamentoHidden.val());
			formato13A.listarProvinciasEdit(formato13A.codDepartamentoHidden.val(),formato13A.codProvinciaHidden.val(),formato13A.codDistritoHidden.val());
			
			formato13A.soloNumerosEnteros();
			
			formato13A.mostrarPeriodoEjecucion();
			formato13A.estiloEdicionDetalle();
			
			//mostramos el mensaje de informacion
			if( formato13A.msgTransaccionDetalle.val()=='OK' ){
				var addhtml='El Detalle de Localidades de la Zona de Beneficiarios seleccionado se guardó satisfactoriamente';
				formato13A.dialogMessageDetalleContent.html(addhtml);
				formato13A.dialogMessageDetalle.dialog("open");
			}else if( formato13A.msgTransaccionDetalle.val()=='ERROR1' ){
				var addhtml='Ya existe un Detalle de Localidades para la Zona, Periodo y Distribuidora Eléctrica seleccionada';
				formato13A.dialogMessageErrorDetalleContent.html(addhtml);
				formato13A.dialogMessageErrorDetalle.dialog("open");
			}else if( formato13A.msgTransaccionDetalle.val()=='ERROR2' ){
				var addhtml='Se produjo un error al guardar el Detalle de Localidades para la Zona';
				formato13A.dialogMessageErrorDetalleContent.html(addhtml);
				formato13A.dialogMessageErrorDetalle.dialog("open");
			}
			
		</c:if>
		
		/* botonRegresarDetalle.click(function(){
			formato13A.blockUI();
			location.href=urlRegresarDetalle+'&crud='+operacion+'&'+formato13A.formDetalle.serialize();
		}); */
		
	},
	buildGridsBusqueda : function () {	
		formato13A.tablaResultados.jqGrid({
		   datatype: "local",
	       colNames: ['Dist. Eléct.','Año Decl.','Mes Decl.','Etapa','Grupo de Información','Estado','Visualizar','Editar','Eliminar','','','','',''],
	       colModel: [
					{ name: 'descEmpresa', index: 'descEmpresa', width: 50},
	               { name: 'anoPresentacion', index: 'anoPresentacion', width: 30 },   
	               { name: 'descMesPresentacion', index: 'descMesPresentacion', width: 30},
	               { name: 'etapa', index: 'etapa',width: 50},
	               { name: 'grupoInfo', index: 'grupoInfo', width: 50},
	               { name: 'estado', index: 'estado', width: 50},
	               { name: 'view', index: 'view', width: 20,align:'center' },
	               { name: 'edit', index: 'edit', width: 20,align:'center' },
	               { name: 'elim', index: 'elim', width: 20,align:'center' },
	               { name: 'codEmpresa', index: 'codEmpresa', hidden: true},
	               { name: 'mesPresentacion', index: 'mesPresentacion', hidden: true},
	               { name: 'flagOperacion', index: 'flagOperacion', hidden: true},
	               { name: 'anioInicioVigencia', index: 'anioInicioVigencia', hidden: true},
	               { name: 'anioFinVigencia', index: 'anioFinVigencia', hidden: true}
		   	    ],
		   	 multiselect: false,
				rowNum:10,
			   	rowList:[10,20,50],
				height: 200,
			   	autowidth: true,
				rownumbers: true,
				shrinkToFit:true,
				pager: formato13A.paginadoResultados,
			    viewrecords: true,
			   	caption: "Resultado(s) de la búsqueda",
			    sortorder: "asc",	   	    	   	   
	       gridComplete: function(){
	      		var ids = formato13A.tablaResultados.jqGrid('getDataIDs');
	      		for(var i=0;i < ids.length;i++){
	      			var cl = ids[i];
	      			var ret = formato13A.tablaResultados.jqGrid('getRowData',cl); 
	      			

	      			view = "<a href='"+formato13A.urlACrud+"&codEmpresa="+ret.codEmpresa+"&anioPresentacion="+ret.anoPresentacion+"&mesPresentacion="+ret.mesPresentacion+"&etapa="+ret.etapa+"&descripcionPeriodo="+ret.descripcionPeriodo+"&descGrupoInformacion="+ret.grupoInfo+"&descestado="+ret.estado+"&anioInicioVigencia="+ret.anioInicioVigencia+"&anioFinVigencia="+ret.anioFinVigencia+"&tipo=0' ><img border='0' title='View' src='/net-theme/images/img-net/file.png'  align='center' /></a> ";
	      			//edit = "<a href='"+formato13A.urlACrud+"&codEmpresa="+ret.codEmpresa+"&anioPresentacion="+ret.anoPresentacion+"&mesPresentacion="+ret.mesPresentacion+"&etapa="+ret.etapa+"&descripcionPeriodo="+ret.descripcionPeriodo+"&tipo=1'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png'  align='center' /></a> ";
	      			
	      			edit = "<a href='#'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png'  align='center' onclick=\"formato13A.confirmarEditCabecera('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.etapa+"','"+ret.descripcionPeriodo+"','"+ret.grupoInfo+"','"+ret.estado+"','"+ret.anioInicioVigencia+"','"+ret.anioFinVigencia+"','"+ret.flagOperacion+"');\" /></a> ";
	      			
	      			//elem = "<a href='#' onclick=\"formato13A.showconfirmacion('ss','ss')\"><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center'  /></a> ";              			

	      			elem = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"formato13A.confirmarEliminarCabecera('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.etapa+"','"+ret.flagOperacion+"');\" /></a> ";
	      			
	      			formato13A.tablaResultados.jqGrid('setRowData',ids[i],{view:view});
	      			formato13A.tablaResultados.jqGrid('setRowData',ids[i],{edit:edit});
	      			formato13A.tablaResultados.jqGrid('setRowData',ids[i],{elim:elem});
	      		}
	      }
	  	});
		formato13A.tablaResultados.jqGrid('navGrid',formato13A.paginadoResultados,{add:false,edit:false,del:false,search: false,refresh: false});	
		formato13A.tablaResultados.jqGrid('navButtonAdd',formato13A.paginadoResultados,{
		       caption:"Exportar a Excel",
		       buttonicon: "ui-icon-bookmark",
		       onClickButton : function () {
		           location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';
		       } 
		}); 
	},
	buscarFormatos : function () {	
 if(formato13A.validateInputAnio(formato13A.txtAnioInicio,formato13A.txtAnioFin)){
	 jQuery.ajax({			
			url: formato13A.urlBusqueda+'&'+formato13A.formBusqueda.serialize(),
			type: 'post',
			dataType: 'json',
			beforeSend:function(){
				formato13A.blockUI();
			},				
			success: function(gridData) {					
				formato13A.tablaResultados.clearGridData(true);
				formato13A.tablaResultados.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
				formato13A.tablaResultados[0].refreshIndex();
				formato13A.unblockUI();
			},error : function(){
				alert("Error de conexión.");
				formato13A.unblockUI();
			}
	}); 
 }
		


	},
	blockUI : function(){
		$.blockUI({ message: '<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Cargando </h3>' });
	},
	unblockUI : function(){
		$.unblockUI();
	},
	cargarPeriodoDeclaracion : function(){
		return jQuery.ajax({
			url: formato13A.urlCargaDeclaracion,
			type: 'post',
			dataType: 'json',
			beforeSend:function(){
				formato13A.blockUI();
			},
			data: {
				codEmpresa: formato13A.codEmpresa.val(),
				anoPresentacion: formato13A.peridoDeclaracion.val()
				},
			success: function(data) {		
				dwr.util.removeAllOptions("peridoDeclaracion");
				dwr.util.addOptions("peridoDeclaracion", data,"codigoItem","descripcionItem");
				
				formato13A.unblockUI();
			},error : function(){
				alert("Error de conexión.");
				formato13A.unblockUI();
			}
		});
	},
	buildGridsDeclaracion : function () {	
		formato13A.tablaDeclaracion.jqGrid({
		   datatype: "local",
	       colNames: ['Año / Mes Alta','Cód. Ubigeo','Localidad','ST-1','ST-2','ST-3','ST-4','ST-5','ST-6','ST-SER','Especial','Total','Zona Benef.','Sede que Atiende','Visualizar','Editar','Eliminar','','','','',''],
	       colModel: [
					{ name: 'descAnioMesAlta', index: 'descAnioMesAlta', width: 70},
		            { name: 'codUbigeo', index: 'codUbigeo', width: 50},
		            { name: 'descripcionLocalidad', index: 'descripcionLocalidad', width: 50},
	                { name: 'st1', index: 'st1', width: 20, align:'right'},
	                { name: 'st2', index: 'st2', width: 20, align:'right'},
	                { name: 'st3', index: 'st3', width: 20, align:'right'},
	                { name: 'st4', index: 'st4', width: 20, align:'right'},
	                { name: 'st5', index: 'st5', width: 20, align:'right'},
	                { name: 'st6', index: 'st6', width: 20, align:'right'},
	                { name: 'stserv', index: 'stserv', width: 20, align:'right'},
	                { name: 'stesp', index: 'stesp', width: 20, align:'right'},
	                { name: 'total', index: 'total', width: 30, align:'right'},
	                { name: 'descZonaBenef', index: 'descZonaBenef', width: 70},
	                { name: 'nombreSedeAtiende', index: 'nombreSedeAtiende', width: 80},
	               { name: 'view', index: 'view', width: 20,align:'center' },
	               { name: 'edit', index: 'edit', width: 20,align:'center' },
	               { name: 'elim', index: 'elim', width: 20,align:'center' },
	               { name: 'codEmpresa', index: 'codEmpresa', hidden: true},
	               { name: 'mesPresentacion', index: 'mesPresentacion', hidden: true},
	               { name: 'anoPresentacion', index: 'anoPresentacion', hidden: true },   
	               { name: 'etapa', index: 'etapa',hidden: true},
	               { name: 'idZonaBenef', index: 'idZonaBenef', hidden: true}
		   	    ],
		   	 multiselect: false,
				rowNum:10,
			   	rowList:[10,20,50],
				height: 200,
			   	autowidth: true,
				rownumbers: true,
				shrinkToFit:true,
				pager: formato13A.paginadoDeclaracion,
			    viewrecords: true,
			   	caption: "Formatos declarados",
			    sortorder: "asc",	   	    	   	   
	       gridComplete: function(){
	    	   AUI().use('liferay-portlet-url', function(A) {
	    		      var ids = formato13A.tablaDeclaracion.jqGrid('getDataIDs');
	    		      
		  	      		for(var i=0;i < ids.length;i++){
		  	      			var cl = ids[i];
		  	      			var ret = formato13A.tablaDeclaracion.jqGrid('getRowData',cl);   
		  	      			//VIEW
		  	      			var urlView=Liferay.PortletURL.createRenderURL();
		  	      			urlView.setParameter("action", "detalle");
		  	      			urlView.setParameter("crud", "READCREATEUPDATE");
		  	      			urlView.setParameter("msg", "DONE");
		  	      			urlView.setParameter("codEmpresa", ret.codEmpresa);
		  	      			urlView.setParameter("periodoDeclaracion", ret.anoPresentacion+completarCerosIzq(ret.mesPresentacion,2)+ret.etapa);
		  	      			urlView.setParameter("idZonaBenef", ret.idZonaBenef);
		  	      			urlView.setParameter("codUbigeo", ret.codUbigeo);
		  	      			urlView.setPortletId(formato13A.portletID);
		  	      			//EDIT
		  	      			var urlEdit=Liferay.PortletURL.createRenderURL();
					  	    urlEdit.setParameter("action", "detalle");
					  	    urlEdit.setParameter("crud", "UPDATE");
					  	  	urlEdit.setParameter("msg", "DONE");
					  	    urlEdit.setParameter("codEmpresa", ret.codEmpresa);
					  	    urlEdit.setParameter("periodoDeclaracion", ret.anoPresentacion+completarCerosIzq(ret.mesPresentacion,2)+ret.etapa);
					  	  	urlEdit.setParameter("idZonaBenef", ret.idZonaBenef);
					  	  	urlEdit.setParameter("codUbigeo", ret.codUbigeo);
					  	  	urlEdit.setPortletId(formato13A.portletID);
		  	      			
		  	      			view = "<a href='"+urlView+"'><img border='0' title='View' src='/net-theme/images/img-net/file.png'  align='center'  /></a> ";
		  	      			edit = "<a href='"+urlEdit+"'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png'  align='center'  /></a> ";
		  	      			
		  	      			elem = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"formato13A.confirmarEliminarDetalle('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.etapa+"','"+ret.codUbigeo+"','"+ret.idZonaBenef+"');\" /></a> ";              			
		  	      			formato13A.tablaDeclaracion.jqGrid('setRowData',ids[i],{view:view});
		  	      			formato13A.tablaDeclaracion.jqGrid('setRowData',ids[i],{edit:edit});
		  	      			formato13A.tablaDeclaracion.jqGrid('setRowData',ids[i],{elim:elem});
		  	      		}
	    		   });	
	      }
	  	});
		formato13A.tablaDeclaracion.jqGrid('navGrid',formato13A.paginadoDeclaracion,{add:false,edit:false,del:false,search: false,refresh: false});	
		formato13A.tablaDeclaracion.jqGrid('navButtonAdd',formato13A.paginadoDeclaracion,{
		       caption:"Exportar a Excel",
		       buttonicon: "ui-icon-bookmark",
		       onClickButton : function () {
		           location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';
		       } 
		}); 
	},
	//VIEW
	buildGridsDeclaracionView : function () {	
		formato13A.tablaDeclaracionView.jqGrid({
		   datatype: "local",
	       colNames: ['Año / Mes Alta','Cód. Ubigeo','Localidad','ST-1','ST-2','ST-3','ST-4','ST-5','ST-6','ST-SER','Especial','Total','Zona Benef.','Sede que Atiende','Visualizar','','','','',''],
	       colModel: [
					{ name: 'descAnioMesAlta', index: 'descAnioMesAlta', width: 70},
		            { name: 'codUbigeo', index: 'codUbigeo', width: 50},
		            { name: 'descripcionLocalidad', index: 'descripcionLocalidad', width: 50},
	                { name: 'st1', index: 'st1', width: 20, align:'right'},
	                { name: 'st2', index: 'st2', width: 20, align:'right'},
	                { name: 'st3', index: 'st3', width: 20, align:'right'},
	                { name: 'st4', index: 'st4', width: 20, align:'right'},
	                { name: 'st5', index: 'st5', width: 20, align:'right'},
	                { name: 'st6', index: 'st6', width: 20, align:'right'},
	                { name: 'stserv', index: 'stserv', width: 20, align:'right'},
	                { name: 'stesp', index: 'stesp', width: 20, align:'right'},
	                { name: 'total', index: 'total', width: 30, align:'right'},
	                { name: 'descZonaBenef', index: 'descZonaBenef', width: 70},
	                { name: 'nombreSedeAtiende', index: 'nombreSedeAtiende', width: 80},
	               { name: 'view', index: 'view', width: 20,align:'center' },
	               { name: 'codEmpresa', index: 'codEmpresa', hidden: true},
	               { name: 'mesPresentacion', index: 'mesPresentacion', hidden: true},
	               { name: 'anoPresentacion', index: 'anoPresentacion', hidden: true },   
	               { name: 'etapa', index: 'etapa',hidden: true},
	               { name: 'idZonaBenef', index: 'idZonaBenef', hidden: true}
		   	    ],
		   	 multiselect: false,
				rowNum:10,
			   	rowList:[10,20,50],
				height: 200,
			   	autowidth: true,
				rownumbers: true,
				shrinkToFit:true,
				pager: formato13A.paginadoDeclaracionView,
			    viewrecords: true,
			   	caption: "Formatos declarados",
			    sortorder: "asc",	   	    	   	   
	       gridComplete: function(){
	    	   AUI().use('liferay-portlet-url', function(A) {
	    		      var ids = formato13A.tablaDeclaracionView.jqGrid('getDataIDs');
	    		      
		  	      		for(var i=0;i < ids.length;i++){
		  	      			var cl = ids[i];
		  	      			var ret = formato13A.tablaDeclaracionView.jqGrid('getRowData',cl);   
		  	      			var urlView=Liferay.PortletURL.createRenderURL();
		  	      		
		  	      			urlView.setParameter("action", "detalle");
		  	      			urlView.setParameter("crud", "READ");
		  	      			urlView.setParameter("msg", "DONE");
		  	      			urlView.setParameter("codEmpresa", ret.codEmpresa);
		  	      			urlView.setParameter("periodoDeclaracion", ret.anoPresentacion+completarCerosIzq(ret.mesPresentacion,2)+ret.etapa);
		  	      			urlView.setParameter("idZonaBenef", ret.idZonaBenef);
		  	      			urlView.setParameter("codUbigeo", ret.codUbigeo);
		  	      			urlView.setPortletId(formato13A.portletID);
		  	      			
		  	      			
		  	      			view = "<a href='"+urlView+"'><img border='0' title='View' src='/net-theme/images/img-net/file.png'  align='center'  /></a> ";
		  	      			formato13A.tablaDeclaracionView.jqGrid('setRowData',ids[i],{view:view});
		  	      		}
	    		   });	
	      }
	  	});
		formato13A.tablaDeclaracionView.jqGrid('navGrid',formato13A.paginadoDeclaracionView,{add:false,edit:false,del:false,search: false,refresh: false});	
		formato13A.tablaDeclaracionView.jqGrid('navButtonAdd',formato13A.paginadoDeclaracionView,{
		       caption:"Exportar a Excel",
		       buttonicon: "ui-icon-bookmark",
		       onClickButton : function () {
		           location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';
		       } 
		}); 
	},
	
	buscarDetalles : function () {	
           
		return jQuery.ajax({			
					url: formato13A.urlBusquedaDetalle+'&'+formato13A.formNuevo.serialize(),
					type: 'post',
					dataType: 'json',
					beforeSend:function(){
						formato13A.blockUI();
					},				
					success: function(gridData) {					
						<c:if test="${crud =='READ'}">
							formato13A.tablaDeclaracionView.clearGridData(true);
							formato13A.tablaDeclaracionView.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
							formato13A.tablaDeclaracionView[0].refreshIndex();
						</c:if>
						<c:if test="${crud =='UPDATE' || crud =='CREATE' }">
							formato13A.tablaDeclaracion.clearGridData(true);
							formato13A.tablaDeclaracion.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
							formato13A.tablaDeclaracion[0].refreshIndex();
						</c:if>
						formato13A.unblockUI();
					},error : function(){
						alert("Error de conexión.");
						formato13A.unblockUI();
					}
			});



	},
	<portlet:namespace/>showUploadExcel : function(){
		formato13A.divOverlay.show();
		formato13A.dialogCargaExcel.show();
		formato13A.dialogCargaExcel.css({ 
	        'left': ($(window).width() / 2 - formato13A.dialogCargaExcel.width() / 2) + 'px', 
	        'top': ($(window).height()  - formato13A.dialogCargaExcel.height() ) + 'px'
	    });
	},
	
	closeDialogCargaExcel : function(){
		formato13A.dialogCargaExcel.hide();
		formato13A.divOverlay.hide();   
	},
	
	<portlet:namespace/>cargarFormatoExcel : function(){
		var nameFile=$("#archivoExcel").val();
		var isSubmit=true;
		
		$("#msjUploadFile").html("");
		if(typeof (nameFile) == "undefined" || nameFile.length==0){
			
			isSubmit=false;
			$("#msjUploadFile").html("Debe seleccionar un archivo");
		}else{
			var extension=nameFile.substr(nameFile.indexOf(".")+1,nameFile.length);
			
			
				if(extension == 'xls' || extension == 'xlsx'){
					isSubmit=true;
				}else{
					isSubmit=false;
					$("#msjUploadFile").html("Archivo inválido");
				}
				
			
			
		}
		
		if(isSubmit){
			formato13A.formNuevo.submit();
		}
		
		
	},
	
	<portlet:namespace/>showUploadTxt : function(){
		formato13A.divOverlay.show();
		formato13A.dialogCargaTexto.show();
		formato13A.dialogCargaTexto.css({ 
	        'left': ($(window).width() / 2 - formato13A.dialogCargaTexto.width() / 2) + 'px', 
	        'top': ($(window).height()  - formato13A.dialogCargaTexto.height() ) + 'px'
	    });
	},
	
	closeDialogCargaTxt : function(){
		formato13A.dialogCargaTexto.hide();
		formato13A.divOverlay.hide();   
	},

	<portlet:namespace/>cargarFormatoTxt : function(){
		var nameFile=$("#archivoTxt").val();
		var isSubmit=true;
		
		$("#msjUploadFiletxt").html("");
		if(typeof (nameFile) == "undefined" || nameFile.length==0){
			
			isSubmit=false;
			$("#msjUploadFiletxt").html("Debe seleccionar un archivo");
		}else{
			var extension=nameFile.substr(nameFile.indexOf(".")+1,nameFile.length);
			
			if(extension == 'txt' ){
				isSubmit=true;
			}else{
				isSubmit=false;
				$("#msjUploadFiletxt").html("Archivo inválido");
			}
				
			
			
		}
		
		if(isSubmit){
			formato13A.formNuevo.submit();
		}
		
		
	},
	
	listarProvincias : function (codDepartamento) {	
		jQuery.ajax({			
					url: formato13A.urlProvincias,
					type: 'post',
					dataType: 'json',
					data:{
						codDepartamento:codDepartamento
					},
					beforeSend:function(){
						formato13A.blockUI();
					},				
					success: function(data) {					
						dwr.util.removeAllOptions("codProvincia");
						dwr.util.addOptions("codProvincia", data,"codigoItem","descripcionItem");
						formato13A.limpiarDistritos();
						formato13A.unblockUI();
					},error : function(){
						alert("Error de conexión.");
						formato13A.unblockUI();
					}
			});


	},
	listarProvinciasEdit : function (codDepartamento,provSelected,distSelected) {	
		jQuery.ajax({			
					url: formato13A.urlProvincias,
					type: 'post',
					dataType: 'json',
					data:{
						codDepartamento:codDepartamento
					},
					beforeSend:function(){
						formato13A.blockUI();
					},				
					success: function(data) {	

						dwr.util.removeAllOptions("codProvincia");
						dwr.util.addOptions("codProvincia", data,"codigoItem","descripcionItem");
						dwr.util.setValue("codProvincia", provSelected);
						formato13A.limpiarDistritos();
						
						formato13A.listarDistritosEdit(provSelected, distSelected);
						
						formato13A.unblockUI();
					},error : function(){
						alert("Error de conexión.");
						formato13A.unblockUI();
					}
			});
	},
	listarDistritos : function (codProvincia) {	
		jQuery.ajax({			
					url: formato13A.urlDistritos,
					type: 'post',
					dataType: 'json',
					data:{
						codProvincia:codProvincia
					},
					beforeSend:function(){
						formato13A.blockUI();
					},				
					success: function(data) {					
						dwr.util.removeAllOptions("codDistrito");
						dwr.util.addOptions("codDistrito", data,"codigoItem","descripcionItem");
						formato13A.unblockUI();
					},error : function(){
						alert("Error de conexión.");
						formato13A.unblockUI();
					}
			});


	},
	listarDistritosEdit : function (codProvincia,distSelected) {	
		jQuery.ajax({			
					url: formato13A.urlDistritos,
					type: 'post',
					dataType: 'json',
					data:{
						codProvincia:codProvincia
					},
					beforeSend:function(){
						formato13A.blockUI();
					},				
					success: function(data) {			

						dwr.util.removeAllOptions("codDistrito");
						dwr.util.addOptions("codDistrito", data,"codigoItem","descripcionItem");
						dwr.util.setValue("codDistrito", distSelected);
						
						formato13A.unblockUI();
					},error : function(){
						alert("Error de conexión.");
						formato13A.unblockUI();
					}
			});


	},
	emptySelectObject: function(){
		var jsonVacio='[{"descripcionItem":"--Seleccione--","codigoItem":""}]';
		return eval("(" + jsonVacio + ")");
	},
	limpiarProvincias:function(){
		dwr.util.removeAllOptions("codProvincia");
		dwr.util.addOptions("codProvincia", formato13A.emptySelectObject(),"codigoItem","descripcionItem");
	},
	limpiarDistritos:function(){
		dwr.util.removeAllOptions("codDistrito");
		dwr.util.addOptions("codDistrito", formato13A.emptySelectObject(),"codigoItem","descripcionItem");
	},
	
	//FORMULARIOS DE VIEW Y EDICION
	verFormato : function(codEmpresa,anoPresentacion,mesPresentacion,etapa,tipo){	
		$.blockUI({ message: formato13A.mensajeObteniendoDatos });
		location.href=formato13A.urlACrud+'&codEmpresa='+codEmpresa+'&anoPresentacion='+anoPresentacion+'&mesPresentacion='+mesPresentacion+'&etapa='+etapa+'&tipo='+tipo;
		
	},

	savecabecera : function(){	
		var form = $('#formato13AGartCommand').serialize();
		jQuery.ajax({	
			url: formato13A.urlGuardarCabecera,
			cache : false,
			async : false,
			type: 'post',
			data : form,
			dataType : "text",
			beforeSend:function(){
				formato13A.blockUI();
			},				
			success: function(data) {					
				if(data == '1'){
					formato13A.codEmpresa.attr("disabled", true);
					formato13A.peridoDeclaracion.attr("disabled", true);
					formato13A.showCamposOculto();
					//formato13A.botonAnadirFormato.css("display","block");
					//formato13A.btnGuardarCabecera.css("display","none");
					
					formato13A.dialogMessageGeneral.dialog("open");
					
					formato13A.buscarDetalles();
				}else if(data == '-1'){
					formato13A.lblMessage.html("El formato ya existe para esa Distribuidora Eléctrica para ese periodo");
					formato13A.dialogMessageGeneral.dialog("open");
					
				}else if(data == '-2'){
					formato13A.lblMessage.html("Error al obtener usuario");
					formato13A.dialogMessageGeneral.dialog("open");
					
				}else if(data == '-3'){
					formato13A.lblMessage.html("Error al obtener terminal");
					formato13A.dialogMessageGeneral.dialog("open");
				
				}else if(data == '-4'){
					formato13A.lblMessage.html("Error al registrar");
					formato13A.dialogMessageGeneral.dialog("open");
					
				}
				
				
				formato13A.unblockUI();
			},error : function(){
				alert("Error de conexión.");
				formato13A.unblockUI();
			}
	});
		
	},
	
	showconfirmacion:function(msj,valor){
	
	$("#estado_proceso").html("Está seguro de eliminar formato");
		formato13A.dlgConfirmacion.dialog({ 
	     	title:"Confirmacion",
	     	height: 200,
			width: 200,	
	         modal: true,
	         buttons:{
	        	 "Si":function(){
	        		 $( this ).dialog( "close" );
	        		
	        	 },
	        	 "No":function(){
	        		 $( this ).dialog( "close" );
	        		
	        	 }
	         },
	         open: function(event, ui) {$(".ui-dialog-titlebar-close").show(); }
	     }).load();
	},


	//calculo total
	calculoTotal : function(){
		var st1;
		var st2;
		var st3;
		var st4;
		var st5;
		var st6;
		var stser;
		var stesp;
		var total;
		st1=formato13A.st1Detalle.val();
		st2=formato13A.st2Detalle.val();
		st3=formato13A.st3Detalle.val();
		st4=formato13A.st4Detalle.val();
		st5=formato13A.st5Detalle.val();
		st6=formato13A.st6Detalle.val();
		stser=formato13A.stserDetalle.val();
		stesp=formato13A.stespDetalle.val();
		console.debug(''+st1+','+st2+','+st3+','+st4+','+st5+','+st6+','+stser+','+stesp);
		total=parseFloat(st1)+parseFloat(st2)+parseFloat(st3)+parseFloat(st4)+parseFloat(st5)+parseFloat(st6)+parseFloat(stser)+parseFloat(stesp);
		formato13A.sttotalDetalle.val(parseFloat(total));
	},
	//MOSTRAR REPORTE
	<portlet:namespace/>mostrarReportePdf : function(){
		var form = formato13A.formNuevo;
		form.removeAttr('enctype');
		
		jQuery.ajax({
			url : formato13A.urlReporte+'&'+form.serialize(),
			type : 'post',
			dataType : 'json',
			data : {
				<portlet:namespace />nombreReporte: 'formato13A',
				<portlet:namespace />nombreArchivo: 'formato13A',
				<portlet:namespace />tipoArchivo: '0'//PDF
			},
			success : function(gridData) {
				formato13A.verReporte();
			},error : function(){
				alert("Error de conexión.");
				formato13A.unblockUI();
			}
		});
	},
	<portlet:namespace/>mostrarReporteExcel : function(){
		var form = formato13A.formNuevo;
		form.removeAttr('enctype');
		jQuery.ajax({
			url : formato13A.urlReporte+'&'+form.serialize(),
			type : 'post',
			dataType : 'json',
			data : {
				<portlet:namespace />nombreReporte: 'formato13A',
				<portlet:namespace />nombreArchivo: 'formato13A',
				<portlet:namespace />tipoArchivo: '1'//XLS
			},
			success : function(gridData) {
				formato13A.verReporte();
			},error : function(){
				alert("Error de conexión.");
				formato13A.unblockUI();
			}
		});
	},
	verReporte : function(){
		window.open('<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ViewReport")%>','_newtab');
	},
	//procesos de validacion y envio definitivo
	<portlet:namespace/>validacionFormato : function(){
		var form = formato13A.formNuevo;
		form.removeAttr('enctype');
		jQuery.ajax({
			url: formato13A.urlValidacion+'&'+form.serialize(),
			type : 'post',
			dataType : 'json',
			/*data : {
				<portlet:namespace />codEmpresa: formato13A.f_empresa.val(),
				<portlet:namespace />periodoEnvio: formato13A.f_periodoEnvio.val(),
				<portlet:namespace />anoInicioVigencia: $('#anioInicioVigencia').val(),
				<portlet:namespace />anoFinVigencia: $('#anioFinVigencia').val()
			},*/
			success : function(data) {
				if( data!=null ){
					formato13A.dialogObservacion.dialog("open");
					formato13A.tablaObservacion.clearGridData(true);
					formato13A.tablaObservacion.jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
					formato13A.tablaObservacion[0].refreshIndex();
					formato13A.unblockUI();
				}

			},error : function(){
				alert("Error de conexión.");
				formato13A.unblockUI();
			}
		});
	},
	<portlet:namespace/>mostrarReporteValidacion : function(){
		var form = formato13A.formNuevo;
		form.removeAttr('enctype');
		jQuery.ajax({
			url: formato13A.urlReporteValidacion+'&'+form.serialize(),
			type : 'post',
			dataType : 'json',
			data : {
				//<portlet:namespace />periodoEnvio: formato13A.f_periodoEnvio.val(),
				<portlet:namespace />nombreReporte: 'validacion13',
				<portlet:namespace />nombreArchivo: 'validacion13',
				<portlet:namespace />tipoArchivo: '0',
				anioInicioVigencia:formato13A.txtInicioVig.val(),
				anioFinVigencia:formato13A.txtFinVig.val() //PDF
			},
			success : function(gridData) {
				formato13A.verReporte();
			},error : function(){
				alert("Error de conexión.");
				formato13A.unblockUI();
			}
		});
	},
	confirmarEnvioDefinitivo : function(){	
		var addhtml='¿Está seguro que desea realizar el Envío Definitivo del Formato 13A?';
		formato13A.dialogConfirmEnvioContent.html(addhtml);
		formato13A.dialogConfirmEnvio.dialog("open");
	},
	<portlet:namespace/>envioDefinitivo : function(){
		var form = formato13A.formNuevo;
		form.removeAttr('enctype');
		jQuery.ajax({
			url: formato13A.urlEnvioDefinitivo+'&'+form.serialize(),
			type : 'post',
			dataType : 'json',
			data : {
				//<portlet:namespace />codEmpresa: formato13A.f_empresa.val(),
				//<portlet:namespace />periodoEnvio: formato13A.f_periodoEnvio.val(),
				//<portlet:namespace />anoInicioVigencia: $('#anioInicioVigencia').val(),
				//<portlet:namespace />anoFinVigencia: $('#anioFinVigencia').val(),
				<portlet:namespace />nombreReporte: 'formato13A',
				<portlet:namespace />nombreArchivo: 'formato13A',
				<portlet:namespace />tipoArchivo: '0'//PDF
			},
			success : function(gridData) {
				var addhtml='El Envío Definitivo se realizó satisfactoriamente al correo electrónico '+formato13A.emailConfigured;
				formato13A.dialogMessageReportContent.html(addhtml);
				formato13A.dialogMessageReport.dialog("open");
				formato13A.unblockUI();
			},error : function(){
				alert("Error de conexión.");
				formato13A.unblockUI();
			}
		});
	},
	buildGridsObservacion : function () {	
		formato13A.tablaObservacion.jqGrid({
		   datatype: "local",
	       colNames: ['Sector Típico','Grupo Zona','Código','Descripción'],
	       colModel: [
						{ name: 'descSectorTipico', index: 'descSectorTipico', width: 80 ,align: 'left'},
						{ name: 'descZonaBenef', index: 'descZonaBenef', width: 100 ,align: 'left'},
						{ name: 'codigo', index: 'codigo', width: 50 ,align: 'center'},
		                { name: 'descripcion', index: 'descripcion', width: 420 ,align: 'left'}               
			   	    ],
		   	 multiselect: false,
				rowNum:10,
			   	rowList:[10,20,50],
				height: 'auto',
			   	autowidth: true,
				rownumbers: true,
				pager: formato13A.paginadoObservacion,
			    viewrecords: true,
			   	sortorder: "asc"
	  	});
		formato13A.tablaObservacion.jqGrid('navGrid',formato13A.paginadoObservacion,{add:false,edit:false,del:false,search: false,refresh: false});	
		formato13A.tablaObservacion.jqGrid('navButtonAdd',formato13A.paginadoObservacion,{
		       caption:"Exportar a Excel",
		       buttonicon: "ui-icon-bookmark",
		       onClickButton : function () {
		           location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';
		       } 
		}); 
		formato13A.tablaObservacion.jqGrid('navButtonAdd',formato13A.paginadoObservacion,{
		       caption:"Exportar a Pdf",
		       buttonicon: "ui-icon-bookmark",
		       onClickButton : function () {
		    	   formato13A.<portlet:namespace/>mostrarReporteValidacion();
		       } 
		});
	},
	<portlet:namespace/>mostrarReporteEnvioDefinitivo : function(){
		var form = formato13A.formNuevo;
		form.removeAttr('enctype');
		jQuery.ajax({
			url: formato13A.urlReporteEnvioDefinitivo+'&'+form.serialize(),
			type : 'post',
			dataType : 'json',
			data : {
				<portlet:namespace />tipoArchivo: '2'//PDF+concatenado
			},
			success : function(gridData) {
				formato13A.verReporte();
			},error : function(){
				alert("Error de conexión.");
				formato13A.unblockUI();
			}
		});
	},
	<portlet:namespace/>mostrarReporteActaEnvio : function(){
		if(formato13A.labelEstado.val()=='Enviado'){
		//if(formato13A.txtEstado.val()=='Enviado'){
			var form = formato13A.formNuevo;
			form.removeAttr('enctype');
			jQuery.ajax({
				url: formato13A.urlReporteActaEnvio+'&'+form.serialize(),
				type : 'post',
				dataType : 'json',
				data : {
					/*<portlet:namespace />codEmpresa: formato13A.f_empresa.val(),
					<portlet:namespace />periodoEnvio: formato13A.f_periodoEnvio.val(),
					<portlet:namespace />anoInicioVigencia: $('#anioInicioVigencia').val(),
					<portlet:namespace />anoFinVigencia: $('#anioFinVigencia').val(),*/
					<portlet:namespace />tipoArchivo: '0'//PDF
				},
				success : function(gridData) {
					formato13A.verReporte();
				},error : function(){
					alert("Error de conexión.");
					formato13A.unblockUI();
				}
			});
		}else{
			//alert("Primero debe realizar el envío definitivo");
			formato13A.dialogMessageInfoCrudContent.html("Primero debe realizar el Envío Definitivo del Formato 13A");
			formato13A.dialogMessageInfoCrud.dialog("open");
		}
	},
	//inicializar dialogs
	initDialogs : function(){	
		formato13A.dialogMessageGeneralInicial.dialog({
			modal: true,
			height: 200,
			width: 500,
			autoOpen: false,
			buttons: {
				Cerrar: function() {
					$( this ).dialog("close");
				}
			}
		});
		formato13A.dialogConfirm.dialog({
			modal: true,
			height: 200,
			width: 500,
			autoOpen: false,
			buttons: {
				"Si": function() {
					formato13A.eliminarFormatoCabecera(cod_Empresa_cabecera,ano_Presentacion_cabecera,mes_Presentacion_cabecera,cod_Etapa_cabecera);
					$(this).dialog("close");
				},
				"No": function() {				
					$(this).dialog("close");
				}
			}
		});
		//addd
		formato13A.dialogMessageInfo.dialog({
			modal: true,
			autoOpen: false,
			width: 500,
			buttons: {
				Ok: function() {
					$( this ).dialog("close");
				}
			}
		});
		formato13A.dialogMessageWarning.dialog({
			modal: true,
			autoOpen: false,
			width: 500,
			buttons: {
				Ok: function() {
					$( this ).dialog("close");
				}
			}
		});
	},
	initDialogsCRUD : function(){	
		formato13A.dialogMessageReport.dialog({
			modal: true,
			width: 500,
			autoOpen: false,
			buttons: {
				'Ver Acta': function() {
					formato13A.<portlet:namespace/>mostrarReporteEnvioDefinitivo();
					$(this).dialog("close");
					formato13A.botonRegresarBusqueda.trigger('click');
				},
				Ok: function() {
					$(this).dialog("close");
					formato13A.botonRegresarBusqueda.trigger('click');
				}
			},
			close: function(event,ui){
				formato13A.botonRegresarBusqueda.trigger('click');
	       	}
		});
		formato13A.dialogConfirmEnvio.dialog({
			modal: true,
			height: 200,
			width: 500,		
			autoOpen: false,
			buttons: {
				"Si": function() {
					formato13A.<portlet:namespace/>envioDefinitivo();
					$(this).dialog("close");
				},
				"No": function() {				
					$(this).dialog("close");
				}
			}
		});
		formato13A.dialogConfirmDetalle.dialog({
			modal: true,
			height: 200,
			width: 500,		
			autoOpen: false,
			buttons: {
				"Si": function() {
					formato13A.eliminarFormatoDetalle(cod_Empresa,ano_Presentacion,mes_Presentacion,cod_Etapa,cod_Ubigeo,id_ZonaBenef);
					$(this).dialog("close");
				},
				"No": function() {				
					$(this).dialog("close");
				}
			}
		});
		formato13A.dialogObservacion.dialog({
			modal: true,
			width: 750,
			autoOpen: false,
			buttons: {
				Cerrar: function() {
					$(this).dialog("close");
				}
			}
		});
		
		
		formato13A.dialogMessageGeneral.dialog({
			modal: true,
			height: 200,
			width: 500,
			autoOpen: false,
			buttons: {
				Cerrar: function() {
					$( this ).dialog("close");
				}
			}
		});
		//addd
		formato13A.dialogMessageInfoCrud.dialog({
			modal: true,
			autoOpen: false,
			width: 500,
			buttons: {
				Ok: function() {
					$( this ).dialog("close");
				}
			}
		});
		formato13A.dialogMessageWarningCrud.dialog({
			modal: true,
			autoOpen: false,
			width: 500,
			buttons: {
				Ok: function() {
					$( this ).dialog("close");
				}
			}
		});
		formato13A.dialogMessageErrorCrud.dialog({
			modal: true,
			autoOpen: false,
			width: 500,
			buttons: {
				Ok: function() {
					$( this ).dialog("close");
				}
			}
		});
	},
	initDialogsCRUDDetalle : function(){	
		formato13A.dialogMessageDetalle.dialog({
			modal: true,
			autoOpen: false,
			width: 500,
			buttons: {
				Ok: function() {
					$( this ).dialog("close");
				}
			}
		});
		//addd
		formato13A.dialogMessageWarningDetalle.dialog({
			modal: true,
			autoOpen: false,
			width: 500,
			buttons: {
				Ok: function() {
					$( this ).dialog("close");
				}
			}
		});
		formato13A.dialogMessageErrorDetalle.dialog({
			modal: true,
			autoOpen: false,
			width: 500,
			buttons: {
				Ok: function() {
					$( this ).dialog("close");
				}
			}
		});
	},
	//otros
	confirmarEliminarCabecera : function(codEmpresa,anoPresentacion,mesPresentacion,etapa,flagOperacion){
		var admin = '${esAdministrador}';
		if(flagOperacion=='ABIERTO'){
			var process=true;
			if( etapa=='ESTABLECIDO' && !admin ){
				process = false;
			}
			if(process){
				var addhtml='¿Está seguro que desea eliminar el registro seleccionado?';
				formato13A.dialogConfirmContent.html(addhtml);
				formato13A.dialogConfirm.dialog("open");
				cod_Empresa_cabecera=codEmpresa;
				ano_Presentacion_cabecera=anoPresentacion;
				mes_Presentacion_cabecera=mesPresentacion;
				cod_Etapa_cabecera=etapa;
			}else{
				//alert(" No tiene autorización para realizar esta operación");
				formato13A.dialogMessageInfoContent.html("No tiene autorización para realizar esta acción");
				formato13A.dialogMessageInfo.dialog("open");
			}
		}else if(flagOperacion=='CERRADO'){
			//alert(" Está fuera de plazo");
			formato13A.dialogMessageInfoContent.html("El plazo para realizar esta acción se encuentra cerrado");
			formato13A.dialogMessageInfo.dialog("open");
		}else{
			//alert("El formato ya fue enviado a OSINERGMIN-GART");
			formato13A.dialogMessageInfoContent.html("El formato ya fue enviado a OSINERGMIN-GART");
			formato13A.dialogMessageInfo.dialog("open");
		}
	},
	confirmarEliminarDetalle : function(codEmpresa,anoPresentacion,mesPresentacion,etapa,codUbigeo,idZonaBenef){
		var addhtml='¿Está seguro que desea eliminar el registro seleccionado?';
		formato13A.dialogConfirmDetalleContent.html(addhtml);
		formato13A.dialogConfirmDetalle.dialog("open");
		cod_Empresa=codEmpresa;
		ano_Presentacion=anoPresentacion;
		mes_Presentacion=mesPresentacion;
		cod_Etapa=etapa;
		cod_Ubigeo=codUbigeo;
		id_ZonaBenef=idZonaBenef;
	},
	eliminarFormatoDetalle : function(cod_Empresa,ano_Presentacion,mes_Presentacion,cod_Etapa,cod_Ubigeo,id_ZonaBenef){			
		//$.blockUI({ message: formato13A.mensajeEliminando });
		var form = formato13A.formNuevo;
		form.removeAttr('enctype');
		jQuery.ajax({
			url: formato13A.urlDeleteDetalle+'&'+form.serialize(),
			type: 'post',
			dataType: 'json',
			data: {
			   <portlet:namespace />codUbigeo: cod_Ubigeo,
			   <portlet:namespace />idZonaBenef: id_ZonaBenef
				},
			success: function(data) {
				if (data.resultado == "OK"){
					//var addhtml2='Registro eliminado con éxito';					
					//formato13A.dialogMessageContent.html(addhtml2);
					//formato13A.dialogMessage.dialog("open");
					formato13A.buscarDetalles();
					formato13A.unblockUI();
				}
				else{
					//alert("Error al eliminar el registro");
					formato13A.dialogMessageErrorCrudContent.html("Error al eliminar el registro");
					formato13A.dialogMessageErrorCrud.dialog("open");
					formato13A.unblockUI();
				}
			},error : function(){
				alert("Error de conexión.");
				formato13A.unblockUI();
			}
		});
	},
	eliminarFormatoCabecera : function(cod_Empresa_cabecera,ano_Presentacion_cabecera,mes_Presentacion_cabecera,cod_Etapa_cabecera){			
		//$.blockUI({ message: formato13A.mensajeEliminando });
		//alert(cod_Empresa_cabecera+','+ano_Presentacion_cabecera+','+mes_Presentacion_cabecera+','+cod_Etapa_cabecera);
		jQuery.ajax({
			url: formato13A.urlDelete+'&'+formato13A.formBusqueda.serialize(),
			type: 'post',
			dataType: 'json',
			data: {
			   <portlet:namespace />codigoEmpresa: cod_Empresa_cabecera,
			   <portlet:namespace />anoPresentacion: ano_Presentacion_cabecera,
			   <portlet:namespace />mesPresentacion: mes_Presentacion_cabecera,
			   <portlet:namespace />codEtapa: cod_Etapa_cabecera
				},
			success: function(data) {
				if (data.resultado == "OK"){
					//var addhtml2='Registro eliminado con éxito';					
					//formato13A.dialogMessageContent.html(addhtml2);
					//formato13A.dialogMessage.dialog("open");
					formato13A.buscarFormatos();
					formato13A.unblockUI();
				}
				else{
					//alert("Error al eliminar el registro");
					formato13A.dialogMessageErrorCrudContent.html("Error al eliminar el registro");
					formato13A.dialogMessageErrorCrud.dialog("open");
					formato13A.unblockUI();
				}
			},error : function(){
				alert("Error de conexión.");
				formato13A.unblockUI();
			}
		});
	},
	confirmarEditCabecera : function(codEmpresa,anoPresentacion,mesPresentacion,etapa,descripcionPeriodo,descGrupo,descEstado,anioInicioVigencia,anioFinVigencia,flagOperacion){
		//alert(flagOperacion+'-'+(flagOperacion=='ABIERTO'));
		var admin = '${esAdministrador}';
		if(flagOperacion=='ABIERTO'){//"&anioInicioVigencia="+ret.anioInicioVigencia+"&anioFinVigencia="+ret.anioFinVigencia
			var process=true;
			if( etapa=='ESTABLECIDO' || !admin ){
				process = false;
			}
			if(process){
				location.href=formato13A.urlACrud+'&codEmpresa='+codEmpresa+'&anioPresentacion='+anoPresentacion+'&mesPresentacion='+mesPresentacion+'&etapa='+etapa+'&descripcionPeriodo='+descripcionPeriodo+'&descGrupoInformacion='+descGrupo+'&descestado='+descEstado+'&anioInicioVigencia='+anioInicioVigencia+'&anioFinVigencia='+anioFinVigencia+'&tipo=1';	
			}else{
				//alert(" No tiene autorización para realizar esta operación");
				formato13A.dialogMessageInfoContent.html("No tiene autorización para realizar esta operación");
				formato13A.dialogMessageInfo.dialog("open");
			}
			
		}else if(flagOperacion=='CERRADO'){
			//alert(" Está fuera de plazo");
			formato13A.dialogMessageInfoContent.html("El plazo para realizar esta acción se encuentra cerrado.");
			formato13A.dialogMessageInfo.dialog("open");
		}else{
			//alert("El formato ya fue enviado a OSINERGMIN-GART");
			formato13A.dialogMessageInfoContent.html("El formato ya fue enviado a OSINERGMIN-GART");
			formato13A.dialogMessageInfo.dialog("open");
		}
	},
	showCamposOculto: function(){
		formato13A.peridoDeclaracion.remove();
		formato13A.txtPeriodo.css("display","block");
		formato13A.txtGrupo.css("display","block");
		formato13A.txtEstado.css("display","block");
		
		formato13A.lblGrupo.css("display","block");
		formato13A.lblEstado.css("display","block");
		formato13A.divPeriodoVigencia.css("display","block");
		
	},
	validateInputTextNumber:function(id){
		$("#"+id).keyup(function () {
		    if (!this.value.match(/^([0-9]{0,10})$/)) {
		        this.value = this.value.replace(/[^0-9]/g, '').substring(0,10);
		    }
		});	
	},
	//add
	//departamentos provincias distritos
	construirDepartamento : function(codDepartamento,descDepartamento){
		dwr.util.removeAllOptions("codDepartamento");
		var dataPeriodo = [{codigoItem:codDepartamento, descripcionItem:descDepartamento}];   
   		dwr.util.addOptions("codDepartamento", dataPeriodo,"codigoItem","descripcionItem");
	},
	construirProvincia : function(codProvincia,descProvincia){
		dwr.util.removeAllOptions("codProvincia");
		var codigo=''+codProvincia;
		var descripcion=''+descProvincia;
   		var dataPeriodo = [{codigoItem:codigo, descripcionItem:descripcion}];   
   		dwr.util.addOptions("codProvincia", dataPeriodo,"codigoItem","descripcionItem");
	},
	construirDistrito : function(codDistrito,descDistrito){
		dwr.util.removeAllOptions("codDistrito");
		var codigo=''+codDistrito;
		var descripcion=''+descDistrito;
   		var dataPeriodo = [{codigoItem:codigo, descripcionItem:descripcion}];   
   		dwr.util.addOptions("codDistrito", dataPeriodo,"codigoItem","descripcionItem");
	},
	mostrarPeriodoEjecucion : function(){
		//if( formato12D.flagPeriodoDetalle.val()==true ){
		if( formato13A.flagPeriodoDetalle.val()=='false' ){
			formato13A.estiloEdicionVigenciaDetalle();
		}else{  
			formato13A.quitarEstiloEdicionVigenciaDetalle();
		}
	},
	//estilos
	estiloEdicionVigenciaDetalle : function(){
		formato13A.anoIniVigenciaDetalle.addClass("fise-editable");
		formato13A.anoFinVigenciaDetalle.addClass("fise-editable");
	},
	estiloEdicionDetalle : function(){
		formato13A.anoAltaDetalle.addClass("fise-editable");
		formato13A.localidadDetalle.addClass("fise-editable");
		formato13A.st1Detalle.addClass("fise-editable");
		formato13A.st2Detalle.addClass("fise-editable");
		formato13A.st3Detalle.addClass("fise-editable");
		formato13A.st4Detalle.addClass("fise-editable");
		formato13A.st5Detalle.addClass("fise-editable");
		formato13A.st6Detalle.addClass("fise-editable");
		formato13A.stserDetalle.addClass("fise-editable");
		formato13A.stespDetalle.addClass("fise-editable");
		formato13A.sedeDetalle.addClass("fise-editable");
	},
	//quitar estilos
	quitarEstiloEdicionVigenciaDetalle : function(){
		formato13A.anoIniVigenciaDetalle.removeClass("fise-editable");
		formato13A.anoFinVigenciaDetalle.removeClass("fise-editable");
	},
	quitarEstiloEdicionDetalle : function(){
		formato13A.anoAltaDetalle.removeClass("fise-editable");
		formato13A.localidadDetalle.removeClass("fise-editable");
		formato13A.st1Detalle.removeClass("fise-editable");
		formato13A.st2Detalle.removeClass("fise-editable");
		formato13A.st3Detalle.removeClass("fise-editable");
		formato13A.st4Detalle.removeClass("fise-editable");
		formato13A.st5Detalle.removeClass("fise-editable");
		formato13A.st6Detalle.removeClass("fise-editable");
		formato13A.stserDetalle.removeClass("fise-editable");
		formato13A.stespDetalle.removeClass("fise-editable");
		formato13A.sedeDetalle.removeClass("fise-editable");
	},
	//
	/***VALIDACIONES**/
	validarFormatoDetalle : function(){
		
		if(formato13A.anoIniVigenciaDetalle.val().length == '' ) {		  
		    //alert('Debe ingresar el año inicio vigencia');
		    formato13A.dialogMessageWarningDetalleContent.html("Debe ingresar el Año Inicio Vigencia");
			formato13A.dialogMessageWarningDetalle.dialog("open");
		    formato13A.anoIniVigenciaDetalle.focus();
		    return false; 
	  	}else{
		  	var numstr = trim(formato13A.anoIniVigenciaDetalle.val());
		 	 if (isNaN(numstr) || numstr.length<4 || parseFloat(numstr)<1900){
			  	//alert('Ingrese un año inicio vigencia válido');
			  	formato13A.dialogMessageWarningDetalleContent.html("Ingrese un Año Inicio Vigencia válido");
				formato13A.dialogMessageWarningDetalle.dialog("open");
			  	return false;
		  	}
	 	 }
		if(formato13A.anoFinVigenciaDetalle.val().length == '' ) {		  
		    //alert('Debe ingresar el año fin vigencia');
		    formato13A.dialogMessageWarningDetalleContent.html("Debe ingresar el Año Fin Vigencia");
			formato13A.dialogMessageWarningDetalle.dialog("open");
		    formato13A.anoFinVigenciaDetalle.focus();
		    return false; 
	  	}else{
		  	var numstr = trim(formato13A.anoFinVigenciaDetalle.val());
		 	 if (isNaN(numstr) || numstr.length<4 || parseFloat(numstr)<1900){
			  	//alert('Ingrese un año fin vigencia válido');
			  	formato13A.dialogMessageWarningDetalleContent.html("Debe ingresar un Año Fin Vigencia válido");
				formato13A.dialogMessageWarningDetalle.dialog("open");
			  	return false;
		  	}
	 	 }
		//
		if(formato13A.anoAltaDetalle.val().length != '' ) {		  
		   	var numstr = trim(formato13A.anoAltaDetalle.val());
		 	 if (isNaN(numstr) || numstr.length<4 || parseFloat(numstr)<1900){
			  	//alert('Ingrese un año alta válido');
			  	formato13A.dialogMessageWarningDetalleContent.html("Debe ingresar un Año Alta válido");
				formato13A.dialogMessageWarningDetalle.dialog("open");
			  	return false;
		  	}
	 	 }
		//periodo de alta
		if( parseFloat(formato13A.anoAltaDetalle.val())*100 + parseFloat(formato13A.mesAltaDetalle.val()) > parseFloat(formato13A.anoPresentacionDetalle.val())*100 + parseFloat(formato13A.mesPresentacionDetalle.val()) ){
			//alert('El periodo de alta no puede ser mayor al periodo a declarar');
			formato13A.dialogMessageWarningDetalleContent.html("El Periodo de Alta no puede ser mayor al periodo a declarar");
			formato13A.dialogMessageWarningDetalle.dialog("open");
			return false;
		}
		
		//anio alta
		/*if( parseFloat(formato13A.anoAltaDetalle.val()) < parseFloat(formato13A.anoPresentacionDetalle.val()) ){
		alert('El año alta no puede ser menor al año presentación del periodo a declarar');
		return false;
		}*/
		//mes alta
		/*if( parseFloat(formato13A.mesAltaDetalle.val()) < parseFloat(formato13A.mesPresentacionDetalle.val()) ){
		alert('El mes alta no puede ser menor al mes presentación del periodo a declarar');
		return false;
		}*/
		//validamos ubigeo 
		if(formato13A.codDepa.val().length != '' ) {
			if(formato13A.codProv.val().length == '' ) {		  
			   //alert('Debe seleccionar la provincia');
			   formato13A.dialogMessageWarningDetalleContent.html("Debe seleccionar la Provincia");
				formato13A.dialogMessageWarningDetalle.dialog("open");
			   formato13A.codProv.focus();
			   return false; 
			}else{
				if(formato13A.codDist.val().length == '' ) {		  
				   //alert('Debe seleccionar el distrito');
				   formato13A.dialogMessageWarningDetalleContent.html("Debe seleccionar el Distrito");
					formato13A.dialogMessageWarningDetalle.dialog("open");
				   formato13A.codDist.focus();
				   return false; 
				}
			}
		}else{
			//alert('Debe seleccionar el departamento');
			formato13A.dialogMessageWarningDetalleContent.html("Debe seleccionar el Departamento");
			formato13A.dialogMessageWarningDetalle.dialog("open");
			formato13A.codDepa.focus();
			return false;
		}
		
		if(formato13A.idZonaBenefDetalle.val().length == '' ) {
			//alert('Debe seleccionar la Zona Beneficiario');
			formato13A.dialogMessageWarningDetalleContent.html("Debe seleccionar la Zona Beneficiario");
			formato13A.dialogMessageWarningDetalle.dialog("open");
		    formato13A.idZonaBenefDetalle.focus();
		   	return false; 
		}else{
			if(formato13A.cod_empresa_edelnor.val()!=formato13A.codEmpresaDetalle.val() && formato13A.cod_empresa_luz_sur.val()!=formato13A.codEmpresaDetalle.val()){
				if(formato13A.idZonaBenefDetalle.val() == 3 ) {//LIMA
					//alert('No puede seleccionar la Zona Beneficiario Lima para la Distribuidora Eléctrica');
					formato13A.dialogMessageWarningDetalleContent.html("No puede seleccionar la Zona Beneficiario Lima para la Distribuidora Eléctrica seleccionada");
					formato13A.dialogMessageWarningDetalle.dialog("open");
				    formato13A.idZonaBenefDetalle.focus();
				   	return false;
				}
			}
		}
		//validacion para que ingrese valores
		if( formato13A.st1Detalle.val()==0 && 
				formato13A.st2Detalle.val()==0 && 
				formato13A.st3Detalle.val()==0 && 
				formato13A.st4Detalle.val()==0 && 
				formato13A.st5Detalle.val()==0 &&
				formato13A.st6Detalle.val()==0 &&
				formato13A.stserDetalle.val()==0 &&
				formato13A.stespDetalle.val()==0 ){
			formato13A.dialogMessageWarningDetalleContent.html("Debe ingresar al menos una cantidad para los Sectores típicos");
			formato13A.dialogMessageWarningDetalle.dialog("open");
		    formato13A.idZonaBenefDetalle.focus();
		    return false;
		}
		
		//
		return true; 
	},
	soloNumerosEnteros : function(){
		formato13A.st1Detalle.attr("onkeypress","return soloNumerosDecimales(event, 1, 'st1',6,0)");
		formato13A.st2Detalle.attr("onkeypress","return soloNumerosDecimales(event, 1, 'st2',6,0)");
		formato13A.st3Detalle.attr("onkeypress","return soloNumerosDecimales(event, 1, 'st3',6,0)");
		formato13A.st4Detalle.attr("onkeypress","return soloNumerosDecimales(event, 1, 'st4',6,0)");
		formato13A.st5Detalle.attr("onkeypress","return soloNumerosDecimales(event, 1, 'st5',6,0)");
		formato13A.st6Detalle.attr("onkeypress","return soloNumerosDecimales(event, 1, 'st6',6,0)");
		formato13A.stserDetalle.attr("onkeypress","return soloNumerosDecimales(event, 1, 'stser',6,0)");
		formato13A.stespDetalle.attr("onkeypress","return soloNumerosDecimales(event, 1, 'stesp',6,0)");
	}
};



</script>