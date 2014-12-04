<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects />

<script type="text/javascript" src="/fise-projects-portlet/js/fise.js"></script>

<script type="text/javascript">
var formato12C= {
	
	/**********INICIO**********/
	
	tablaResultados:null,
	paginadoResultados:null,
	//url
	urlBusqueda: null,
	urlDelete:null,
	//botones
	botonBuscar:null,
	//form
	formBusqueda: null,
	//div
	dialogConfirm:null,
	dialogConfirmContent:null,
	//mensajes
	mensajeObteniendoDatos:null,
	mensajeCargando:null,
	mensajeEliminando:null,
	//
	portletID:null,
	
	/**********CRUD**********/
	//implementacion
	tablaImplementacion:null,
	paginadoImplementacion:null,
	tablaImplementacionView:null,
	paginadoImplementacionView:null,
	//mensual
	tablaMensual:null,
	paginadoMensual:null,
	tablaMensualView:null,
	paginadoMensualView:null,
	//observacion
	tablaObservacion:null,
	paginadoObservacion:null,
	//url
	urlBusquedaDetalle: null,
	urlCargaDeclaracion: null,
	
	urlCargaPeriodo: null,
	urlCargaFlagPeriodo: null,
	
	urlProvincias:null,
	urlDistritos:null,
	urlReporte:null,
	urlValidacion:null,
	urlReporteValidacion:null,
	urlEnvioDefinitivo:null,
	urlReporteEnvioDefinitivo:null,
	urlDeleteDetalle:null,
	urlReporteActaEnvio:null,
	//botones
	botonReportePdf:null,
	botonReporteExcel:null,
	botonValidacion:null,
	botonEnvioDefinitivo:null,
	botonActaEnvio:null,
	botonCrearFormato : null,
	
	//form
	formNuevo : null,
	formDetalle : null,
	
	codEmpresa : null,
	peridoDeclaracion : null,
	anoPresentacion : null,
	mesPresentacion : null,
	etapa : null,
	
	urlACrud:null,
	
	divPeriodoVigencia:null,
	
	//carga excel
	btnExcel:null,
	dialogCargaExcel:null,
	btnCargarFormatoExcel:null,
	//carga txt
	btnTxt:null,
	dialogCargaTexto:null,
	btnCargarFormatoTexto:null,
	//div
	divOverlay:null,
	//dialogs
	dialogObservacion:null,
	dialogMessageReport:null,
	dialogConfirmEnvio:null,
	dialogMessageReportContent:null,
	dialogConfirmEnvioContent:null,
	dialogConfirmDetalle:null,
	dialogConfirmDetalleContent:null,
	//mensajes
	mensajeObteniendoDatosDetalle:null,
	mensajeCargandoDetalle:null,
	mensajeEliminandoDetalle:null,
	
	/**********DETALLE CRUD**********/
	botonGuardarDetalle:null,
	
	codDepaOrigen:null,
	codProvOrigen:null,
	codDistOrigen:null,
	codDepaDestino:null,
	codProvDestino:null,
	codDistDestino:null,
	
	codDepartamentoOrigenHidden:null,
	codProvinciaOrigenHidden:null,
	codDistritoOrigenHidden:null,
	codDepartamentoDestinoHidden:null,
	codProvinciaDestinoHidden:null,
	codDistritoDestinoHidden:null,
	
	descDepaOrigen:null,
	descProvOrigen:null,
	descDistOrigen:null,
	descDepaDestino:null,
	descProvDestino:null,
	descDistDestino:null,
	
	//detalleCRUD
	codEmpresaDetalle:null,
	anoPresentacionDetalle:null,
	mesPresentacionDetalle:null,
	anoEjecucionDetalle:null,
	mesEjecucionDetalle:null,
	etapaDetalle:null,
	periodoEnvioDetalle:null,
	//valores 
	etapaEjecucionDetalle:null,
	localidadOrigenDetalle:null,
	localidadDestinoDetalle:null,
	idZonaBenefDetalle:null,
	cuentaContableDetalle:null,
	actividadDetalle:null,
	tipoDocDetalle:null,
	rucEmpresaDetalle:null,
	serieDocDetalle:null,
	nroDocDetalle:null,
	nroDiasDetalle:null,
	montoAlimentacionDetalle:null,
	montoAlojamientoDetalle:null,
	montoMovilidadDetalle:null,
	totalDetalle:null,
	//
	anoEjecucionHiddenDetalle:null,
	mesEjecucionHiddenDetalle:null,
	etapaEjecucionHiddenDetalle:null,
	
	botonAnadirFormato:null,
	botonRegresarBusqueda:null,
	//mensajes
	mensajeGuardandoDetalle:null,
	 
	init : function(urlNuevo,urlView,urlEdit){
		
		this.tablaResultados=$("#<portlet:namespace/>grid_formato");
		this.paginadoResultados='#<portlet:namespace/>pager_formato';
		this.urlBusqueda='<portlet:resourceURL id="busqueda" />';
		this.botonBuscar=$("#<portlet:namespace/>buscarFormato");
		this.formBusqueda=$('#formato12CCBean');
		this.botonCrearFormato=$('#<portlet:namespace/>crearFormato');
		this.divOverlay=$("#<portlet:namespace/>divOverlay");
		this.mensajeObteniendoDatos='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Obteniendo Datos </h3>';
		this.urlACrud=urlView;
		this.urlDelete='<portlet:resourceURL id="deleteCabecera" />';
		this.dialogConfirm=$("#<portlet:namespace/>dialog-confirm");
		this.dialogConfirmContent=$("#<portlet:namespace/>dialog-confirm-content");
		
		formato12C.initDialogs();
		
		formato12C.botonCrearFormato.click(function() {
			formato12C.blockUI();
			location.href=urlNuevo;
		});
		formato12C.botonBuscar.click(function() {
			formato12C.buscarFormatos();
		});
		
		formato12C.buildGridsBusqueda();
		formato12C.botonBuscar.trigger('click');
	},
	
	initCRUD : function(operacion,urlAnadirFormato,urlRegresarBusqueda){
		this.portletID='<%=PortalUtil.getPortletId(renderRequest)%>';
		this.urlCargaDeclaracion='<portlet:resourceURL id="cargaPeriodoDeclaracion" />';
		
		this.urlCargaPeriodo='<portlet:resourceURL id="cargaPeriodo" />';
		this.urlCargaFlagPeriodo='<portlet:resourceURL id="cargaFlagPeriodo" />';
		
		this.urlBusquedaDetalle='<portlet:resourceURL id="busquedaDetalle" />';
		this.formNuevo=$('#formato12CCBean');
		
		this.codEmpresa=$('#codigoEmpresa');
		this.periodoEnvio=$('#periodoEnvio');
		//
		this.anoPresentacion=$('#anioPresentacion');
		this.mesPresentacion=$('#mesPresentacion');
		this.etapa=$('#etapa');
		
		this.tablaImplementacion=$("#<portlet:namespace/>grid_formato_implementacion");
		this.paginadoImplementacion='#<portlet:namespace/>pager_formato_implementacion';
		this.tablaMensual=$("#<portlet:namespace/>grid_formato_mensual");
		this.paginadoMensual='#<portlet:namespace/>pager_formato_mensual';
		//view
		this.tablaImplementacionView=$("#<portlet:namespace/>grid_formato_implementacionView");
		this.paginadoImplementacionView='#<portlet:namespace/>pager_formato_implementacionView';
		this.tablaMensualView=$("#<portlet:namespace/>grid_formato_mensualView");
		this.paginadoMensualView='#<portlet:namespace/>pager_formato_mensualView';
		
		this.tablaObservacion=$("#<portlet:namespace/>grid_observacion");
		this.paginadoObservacion='#<portlet:namespace/>pager_observacion';
		//
		this.botonAnadirFormato=$('#<portlet:namespace/>anadirFormato');

		this.botonRegresarBusqueda=$('#<portlet:namespace/>regresarBusqueda');
		
		this.divPeriodoVigencia = $('#divVigencia');
		
		//carga excel
		this.btnExcel=$('#<portlet:namespace/>showDialogUploadExcel');
		this.dialogCargaExcel=$("#<portlet:namespace/>dialog-form-cargaExcel");
		this.btnCargarFormatoExcel=$('#<portlet:namespace/>cargarFormatoExcel');
		//carga txt
		this.btnTxt=$('#<portlet:namespace/>showDialogUploadTxt');
		this.dialogCargaTexto=$("#<portlet:namespace/>dialog-form-cargaTxt");
		this.btnCargarFormatoTexto=$('#<portlet:namespace/>cargarFormatoTxt');
		//divoverlay
		this.divOverlay=$("#<portlet:namespace/>divOverlay");
		//dialogs
		this.dialogObservacion=$("#<portlet:namespace/>dialog-form-observacion");
		this.dialogMessageReport=$("#<portlet:namespace/>dialog-message-report");
		this.dialogMessageReportContent=$("#<portlet:namespace/>dialog-message-report-content");
		this.dialogConfirmEnvio=$("#<portlet:namespace/>dialog-confirm-envio");
		this.dialogConfirmEnvioContent=$("#<portlet:namespace/>dialog-confirm-envio-content");
		this.dialogConfirmDetalle=$("#<portlet:namespace/>dialog-confirm-detalle");
		this.dialogConfirmDetalleContent=$("#<portlet:namespace/>dialog-confirm-detalle-content");
		//reportes
		this.urlReporte='<portlet:resourceURL id="reporte" />';
		this.botonReportePdf=$("#<portlet:namespace/>reportePdf");
		this.botonReporteExcel=$("#<portlet:namespace/>reporteExcel");
		//validacion
		this.urlValidacion='<portlet:resourceURL id="validacion" />';
		this.urlReporteValidacion='<portlet:resourceURL id="reporteValidacion" />';
		this.botonValidacion=$("#<portlet:namespace/>validacionFormato");
		//enviodefinito
		this.urlEnvioDefinitivo='<portlet:resourceURL id="envioDefinitivo" />';
		this.urlReporteEnvioDefinitivo='<portlet:resourceURL id="reporteEnvioDefinitivo" />';
		this.botonEnvioDefinitivo=$("#<portlet:namespace/>envioDefinitivo");
		//acta envio
		this.urlReporteActaEnvio='<portlet:resourceURL id="reporteActaEnvioView" />';
		this.botonActaEnvio=$("#<portlet:namespace/>reporteActaEnvio");
		//eliminar
		this.mensajeEliminandoDetalle='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Eliminando </h3>';
		this.urlDeleteDetalle='<portlet:resourceURL id="deleteDetalle" />';
		
		
		formato12C.initDialogsCRUD();
		
		formato12C.btnExcel.click(function() {formato12C.<portlet:namespace/>showUploadExcel();});
		formato12C.btnCargarFormatoExcel.click(function() {formato12C.<portlet:namespace/>cargarFormatoExcel();});
		formato12C.btnTxt.click(function() {formato12C.<portlet:namespace/>showUploadTxt();});
		
		formato12C.buildGridsImplementacion();
		formato12C.buildGridsMensual();
		//para modo view
		formato12C.buildGridsImplementacionView();
		formato12C.buildGridsMensualView();
		
		this.buildGridsObservacion();
		
		if(operacion=='CREATE'){
			
			formato12C.tipoOperacion=operacion;
			/*formato12C.codEmpresa.change(function(){
				$.when(formato12C.cargarPeriodoDeclaracion()).then(formato12C.buscarDetalles);
			});
			formato12C.codEmpresa.trigger('change');*/
			
			
			formato12C.codEmpresa.change(function(){formato12C.<portlet:namespace/>loadPeriodo('');});
			formato12C.periodoEnvio.change(function(){formato12C.<portlet:namespace/>loadPeriodo(this.value);});
			
			formato12C.<portlet:namespace/>loadPeriodo('');
			
			formato12C.botonAnadirFormato.click(function(){
				formato12C.blockUI();
				//--formato12C.formNuevo.attr('action',urlAnadirFormato+'&codigoEmpresa='+formato12C.codEmpresa.val()+'&periodoEnvio='+formato12C.periodoEnvio.val()+'&strip=0').removeAttr('enctype').submit();
				formato12C.formNuevo.attr('action',urlAnadirFormato+'&strip=0').removeAttr('enctype').submit();
			});
			formato12C.botonRegresarBusqueda.click(function(){
				formato12C.blockUI();
				location.href=urlRegresarBusqueda;
			});

			
		}if(operacion=='READ'){
			
			formato12C.tipoOperacion=operacion;
			formato12C.buscarDetalles();
			formato12C.botonReportePdf.click(function() {formato12C.<portlet:namespace/>mostrarReportePdf();});
			formato12C.botonReporteExcel.click(function() {formato12C.<portlet:namespace/>mostrarReporteExcel();});
			
			formato12C.botonActaEnvio.click(function() {formato12C.<portlet:namespace/>mostrarReporteActaEnvio();});
			
			formato12C.botonRegresarBusqueda.click(function(){
				formato12C.blockUI();
				location.href=urlRegresarBusqueda;
			});
			//formato12C.botonAnadirFormato.css("display","none");
			
			formato12C.construirPeriodoEnvio(formato12C.anoPresentacion.val(), formato12C.mesPresentacion.val(), formato12C.etapa.val());
	
		}if(operacion=='UPDATE'){
			
			formato12C.buscarDetalles();
			//formato12C.botonAnadirFormato.css("display","block");

            formato12C.botonAnadirFormato.click(function(){
				formato12C.blockUI();
				//formato12C.formNuevo.attr('action',urlAnadirFormato+'&codigoEmpresa='+formato12C.codEmpresa.val()+'&periodoEnvio='+formato12C.periodoEnvio.val()+'&strip=0').removeAttr('enctype').submit();--
				formato12C.formNuevo.attr('action',urlAnadirFormato+'&strip=0').removeAttr('enctype').submit();
			});
			
			formato12C.botonRegresarBusqueda.click(function(){
				formato12C.blockUI();
				location.href=urlRegresarBusqueda;
			});
			
			formato12C.construirPeriodoEnvio(formato12C.anoPresentacion.val(), formato12C.mesPresentacion.val(), formato12C.etapa.val());
			
			//validacion y envio definitivo
			formato12C.botonValidacion.click(function() {formato12C.<portlet:namespace/>validacionFormato();});
			formato12C.botonEnvioDefinitivo.click(function() {formato12C.confirmarEnvioDefinitivo();});

			
		}
		
		
	},
	initCRUDDetalle : function(operacion,urlGuardarDetalle,urlRegresarDetalle){
		this.formDetalle=$("#formato12CCBean");
		//origen
		this.codDepaOrigen=$("select[name='codDepartamentoOrigen']");
		this.codProvOrigen=$("select[name='codProvinciaOrigen']");
		this.codDistOrigen=$("select[name='codDistritoOrigen']");
		//destino
		this.codDepaDestino=$("select[name='codDepartamentoDestino']");
		this.codProvDestino=$("select[name='codProvinciaDestino']");
		this.codDistDestino=$("select[name='codDistritoDestino']");
		//
		this.urlProvincias='<portlet:resourceURL id="provincias" />';
		this.urlDistritos='<portlet:resourceURL id="distritos" />';
		this.botonGuardarDetalle=$('#<portlet:namespace/>guardarDetalle');
		var botonRegresarDetalle=$('#<portlet:namespace/>regresarFormato');
		//valores de cabecera
		this.codEmpresaDetalle=$('#codigoEmpresa');
		this.anoPresentacionDetalle=$('#anioPresentacion');
		this.mesPresentacionDetalle=$('#mesPresentacion');
		this.etapaDetalle=$('#etapa');
		this.periodoEnvioDetalle=$('#periodoEnvio');
		this.anoEjecucionDetalle=$('#anioEjecucion');
		this.mesEjecucionDetalle=$('#mesEjecucion');
		//valores 
		this.etapaEjecucionDetalle=$('#etapaEjecucion');
		this.localidadOrigenDetalle=$('#localidadOrigen');
		this.localidadDestinoDetalle=$('#localidadDestino');
		this.idZonaBenefDetalle=$('#zonaBenef');
		this.cuentaContableDetalle=$('#codCuentaContable');
		this.actividadDetalle=$('#actividad');
		this.tipoDocDetalle=$('#tipoDocumento');
		this.rucEmpresaDetalle=$('#rucEmpresa');
		this.serieDocDetalle=$('#serieDocumento');
		this.nroDocDetalle=$('#nroDocumento');
		this.nroDiasDetalle=$('#nroDias');
		this.montoAlimentacionDetalle=$('#montoAlimentacion');
		this.montoAlojamientoDetalle=$('#montoAlojamiento');
		this.montoMovilidadDetalle=$('#montoMovilidad');
		this.totalDetalle=$('#totalGeneral');
		
		//origen
		this.codDepartamentoOrigenHidden=$('codDepartamentoOrigenHidden');
		this.codProvinciaOrigenHidden=$('codProvinciaOrigenHidden');
		this.codDistritoOrigenHidden=$('codDistritoOrigenHidden');
		
		this.descDepaOrigen=$('descDepartamentoOrigen');
		this.descProvOrigen=$('descProvinciaOrigen');
		this.descDistOrigen=$('descDistritoOrigen');
		//destino
		this.codDepartamentoDestinoHidden=$('codDepartamentoDestinoHidden');
		this.codProvinciaDestinoHidden=$('codProvinciaDestinoHidden');
		this.codDistritoDestinoHidden=$('codDistritoDestinoHidden');
		
		this.descDepaDestino=$('descDepartamentoDestino');
		this.descProvDestino=$('descProvinciaDestino');
		this.descDistDestino=$('descDistritoDestino');

		this.anoEjecucionHiddenDetalle=$('#anoEjecucionHidden');
		this.mesEjecucionHiddenDetalle=$('#mesEjecucionHidden');
		this.etapaEjecucionHiddenDetalle=$('#etapaEjecucionHidden');
		
		$('input.target[type=text]').on('change', function(){
			formato12C.calculoTotal();
		});
		
		<c:if test="${crud =='CREATE'}">
			//origen	
			formato12C.codDepaOrigen.change(function(){
				formato12C.listarProvincias(formato12C.codDepaOrigen.val(),'0');
			});
			formato12C.codProvOrigen.change(function(){
				formato12C.listarDistritos(formato12C.codProvOrigen.val(),'0');
			});
			//destino
			formato12C.codDepaDestino.change(function(){
				formato12C.listarProvincias(formato12C.codDepaDestino.val(),'1');
			});
			formato12C.codProvDestino.change(function(){
				formato12C.listarDistritos(formato12C.codProvDestino.val(),'1');
			});
			//
			
			formato12C.botonGuardarDetalle.click(function(){
				if( formato12C.validarFormatoDetalle() ){
					//--formato12C.formDetalle.attr('action',urlGuardarDetalle+'&crud='+operacion+'&codEmpresa='+formato12C.codEmpresaDetalle.val()+'&anoEjecucionHidden='+formato12C.anoEjecucionDetalle.val()+'&mesEjecucionHidden='+formato12C.mesEjecucionDetalle.val()+'&etapaEjecucionHidden='+formato12C.etapaEjecucionDetalle.val()).submit();
					//guardamos los valores
					formato12C.anoEjecucionHiddenDetalle.val(formato12C.anoEjecucionDetalle.val());
					formato12C.mesEjecucionHiddenDetalle.val(formato12C.mesEjecucionDetalle.val());
					formato12C.etapaEjecucionHiddenDetalle.val(formato12C.etapaEjecucionDetalle.val());
					formato12C.formDetalle.attr('action',urlGuardarDetalle+'&crud='+operacion+'&codEmpresa='+formato12C.codEmpresaDetalle.val()).submit();
				}
			});
			
			botonRegresarDetalle.click(function(){
				formato12C.blockUI();
				location.href=urlRegresarDetalle+'&crud='+operacion+'&codEmpresa='+formato12C.codEmpresaDetalle.val()+'&periodoEnvio='+formato12C.periodoEnvioDetalle.val()+'&anioPresentacion='+formato12C.anoPresentacionDetalle.val()+'&mesPresentacion='+formato12C.mesPresentacionDetalle.val()+'&etapa='+formato12C.etapaDetalle.val()+'&tipo=1';
			});
			
			formato12C.construirPeriodoEnvio(formato12C.anoPresentacionDetalle.val(), formato12C.mesPresentacionDetalle.val(), formato12C.etapaDetalle.val());
			formato12C.anoEjecucionDetalle.val(formato12C.anoPresentacionDetalle.val());
			formato12C.mesEjecucionDetalle.val(formato12C.mesPresentacionDetalle.val());
		
			formato12C.soloNumerosDecimales();
			
		</c:if>
		
		<c:if test="${crud =='READ'}">
			botonRegresarDetalle.click(function(){
				formato12C.blockUI();
				location.href=urlRegresarDetalle+'&crud='+operacion+'&codEmpresa='+formato12C.codEmpresaDetalle.val()+'&periodoEnvio='+formato12C.periodoEnvioDetalle.val()+'&anioPresentacion='+formato12C.anoPresentacionDetalle.val()+'&mesPresentacion='+formato12C.mesPresentacionDetalle.val()+'&etapa='+formato12C.etapaDetalle.val()+'&tipo=0';
				//location.href=urlRegresarDetalle+'&crud='+operacion+'&'+formato12C.formDetalle.serialize()+'&tipo=0';
			});
			
			formato12C.construirPeriodoEnvio(formato12C.anoPresentacionDetalle.val(), formato12C.mesPresentacionDetalle.val(), formato12C.etapaDetalle.val());
			formato12C.anoEjecucionDetalle.val(formato12C.anoEjecucionHiddenDetalle.val());
			formato12C.mesEjecucionDetalle.val(formato12C.mesEjecucionHiddenDetalle.val());
			formato12C.etapaEjecucionDetalle.val(formato12C.etapaEjecucionHiddenDetalle.val());
			
			/*console.debug(formato12C.codDepartamentoOrigenHidden.val());
			console.debug(formato12C.codProvinciaOrigenHidden.val());
			console.debug(formato12C.codDistritoOrigenHidden.val());
			console.debug(formato12C.codDepartamentoDestinoHidden.val());
			console.debug(formato12C.codProvinciaDestinoHidden.val());
			console.debug(formato12C.codDistritoDestinoHidden.val());
			
			formato12C.codDepaOrigen.val(formato12C.codDepartamentoOrigenHidden.val());
			//formato12C.construirDepartamentoOrigen(formato12C.codDepartamentoOrigenHidden.val(), formato12C.descDepaOrigen.val());
			formato12C.construirProvinciaOrigen(formato12C.codProvinciaOrigenHidden.val(), formato12C.descProvOrigen.val());
			formato12C.construirDistritoOrigen(formato12C.codDistritoOrigenHidden.val(), formato12C.descDistOrigen.val());
			
			formato12C.codDepaDestino.val(formato12C.codDepartamentoDestinoHidden.val());
			//formato12C.construirDepartamentoDestino(formato12C.codDepartamentoDestinoHidden.val(), formato12C.descDepaDestino.val());
			formato12C.construirProvinciaDestino(formato12C.codProvinciaDestinoHidden.val(), formato12C.descProvDestino.val());
			formato12C.construirDistritoDestino(formato12C.codDistritoDestinoHidden.val(), formato12C.descDistDestino.val());*/
			
		</c:if>
		
		<c:if test="${crud =='READCREATEUPDATE'}">
			botonRegresarDetalle.click(function(){
				formato12C.blockUI();
				location.href=urlRegresarDetalle+'&crud='+operacion+'&codEmpresa='+formato12C.codEmpresaDetalle.val()+'&periodoEnvio='+formato12C.periodoEnvioDetalle.val()+'&anioPresentacion='+formato12C.anoPresentacionDetalle.val()+'&mesPresentacion='+formato12C.mesPresentacionDetalle.val()+'&etapa='+formato12C.etapaDetalle.val()+'&tipo=1';
				//location.href=urlRegresarDetalle+'&crud='+operacion+'&'+formato12C.formDetalle.serialize()+'&tipo=0';
			});
			formato12C.construirPeriodoEnvio(formato12C.anoPresentacionDetalle.val(), formato12C.mesPresentacionDetalle.val(), formato12C.etapaDetalle.val());
			formato12C.anoEjecucionDetalle.val(formato12C.anoEjecucionHiddenDetalle.val());
			formato12C.mesEjecucionDetalle.val(formato12C.mesEjecucionHiddenDetalle.val());
			formato12C.etapaEjecucionDetalle.val(formato12C.etapaEjecucionHiddenDetalle.val());
			
			/*console.debug(formato12C.codDepartamentoOrigenHidden.val());
			console.debug(formato12C.codProvinciaOrigenHidden.val());
			console.debug(formato12C.codDistritoOrigenHidden.val());
			console.debug(formato12C.codDepartamentoDestinoHidden.val());
			console.debug(formato12C.codProvinciaDestinoHidden.val());
			console.debug(formato12C.codDistritoDestinoHidden.val());
			
			formato12C.codDepaOrigen.val(formato12C.codDepartamentoOrigenHidden.val());
			//formato12C.construirDepartamentoOrigen(formato12C.codDepartamentoOrigenHidden.val(), formato12C.descDepaOrigen.val());
			formato12C.construirProvinciaOrigen(formato12C.codProvinciaOrigenHidden.val(), formato12C.descProvOrigen.val());
			formato12C.construirDistritoOrigen(formato12C.codDistritoOrigenHidden.val(), formato12C.descDistOrigen.val());
			
			formato12C.codDepaDestino.val(formato12C.codDepartamentoDestinoHidden.val());
			//formato12C.construirDepartamentoDestino(formato12C.codDepartamentoDestinoHidden.val(), formato12C.descDepaDestino.val());
			formato12C.construirProvinciaDestino(formato12C.codProvinciaDestinoHidden.val(), formato12C.descProvDestino.val());
			formato12C.construirDistritoDestino(formato12C.codDistritoDestinoHidden.val(), formato12C.descDistDestino.val());*/
			
		</c:if>
		
		<c:if test="${crud =='UPDATE'}">
		
			formato12C.botonGuardarDetalle.click(function(){
				if( formato12C.validarFormatoDetalle() ){
					//---formato12C.formDetalle.attr('action',urlGuardarDetalle+'&crud='+operacion+'&codEmpresa='+formato12C.codEmpresaDetalle.val()+'&anoEjecucionHidden='+formato12C.anoEjecucionDetalle.val()+'&mesEjecucionHidden='+formato12C.mesEjecucionDetalle.val()+'&etapaEjecucionHidden='+formato12C.etapaEjecucionDetalle.val()).submit();
					//guardamos los valores
					formato12C.anoEjecucionHiddenDetalle.val(formato12C.anoEjecucionDetalle.val());
					formato12C.mesEjecucionHiddenDetalle.val(formato12C.mesEjecucionDetalle.val());
					formato12C.etapaEjecucionHiddenDetalle.val(formato12C.etapaEjecucionDetalle.val());
					formato12C.formDetalle.attr('action',urlGuardarDetalle+'&crud='+operacion+'&codEmpresa='+formato12C.codEmpresaDetalle.val()).submit();
					//---formato12C.formDetalle.attr('action',urlGuardarDetalle+'&crud='+operacion).submit();
				}
			});
		
			botonRegresarDetalle.click(function(){
				formato12C.blockUI();
				location.href=urlRegresarDetalle+'&crud='+operacion+'&codEmpresa='+formato12C.codEmpresaDetalle.val()+'&periodoEnvio='+formato12C.periodoEnvioDetalle.val()+'&anioPresentacion='+formato12C.anoPresentacionDetalle.val()+'&mesPresentacion='+formato12C.mesPresentacionDetalle.val()+'&etapa='+formato12C.etapaDetalle.val()+'&tipo=1';
			});
			
			formato12C.construirPeriodoEnvio(formato12C.anoPresentacionDetalle.val(), formato12C.mesPresentacionDetalle.val(), formato12C.etapaDetalle.val());
			formato12C.anoEjecucionDetalle.val(formato12C.anoEjecucionHiddenDetalle.val());
			formato12C.mesEjecucionDetalle.val(formato12C.mesEjecucionHiddenDetalle.val());
			formato12C.etapaEjecucionDetalle.val(formato12C.etapaEjecucionHiddenDetalle.val());
			
			formato12C.soloNumerosDecimales();
		</c:if>
		
	},
	/**ESTRUCTURA DE TABLAS DE BUSQUEDA**/
	buildGridsBusqueda : function () {	
		formato12C.tablaResultados.jqGrid({
		   datatype: "local",
	       colNames: ['Empresa','Año Pres.','Mes Pres.','Etapa','Grupo de Informaiòn','Estado','Visualizar','Editar','Anular','','',''],
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
	               { name: 'flagOperacion', index: 'flagOperacion', hidden: true}
		   	    ],
		   	 multiselect: false,
				rowNum:10,
			   	rowList:[10,20,50],
				height: 200,
			   	autowidth: true,
				rownumbers: true,
				shrinkToFit:true,
				pager: formato12C.paginadoResultados,
			    viewrecords: true,
			   	caption: "Formatos",
			    sortorder: "asc",	   	    	   	   
	       gridComplete: function(){
	      		var ids = formato12C.tablaResultados.jqGrid('getDataIDs');
	      		for(var i=0;i < ids.length;i++){
	      			var cl = ids[i];
	      			var ret = formato12C.tablaResultados.jqGrid('getRowData',cl); 
					
	      			view = "<a href='"+formato12C.urlACrud+"&codEmpresa="+ret.codEmpresa+"&anioPresentacion="+ret.anoPresentacion+"&mesPresentacion="+ret.mesPresentacion+"&etapa="+ret.etapa+"&tipo=0' ><img border='0' title='View' src='/net-theme/images/img-net/file.png'  align='center' /></a> ";
	      			edit = "<a href='#'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png'  align='center' onclick=\"formato12C.confirmarEditCabecera('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.etapa+"','"+ret.flagOperacion+"');\" /></a> ";
					elem = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"formato12C.confirmarEliminarCabecera('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.etapa+"','"+ret.flagOperacion+"');\" /></a> ";
	      			
	      			formato12C.tablaResultados.jqGrid('setRowData',ids[i],{view:view});
	      			formato12C.tablaResultados.jqGrid('setRowData',ids[i],{edit:edit});
	      			formato12C.tablaResultados.jqGrid('setRowData',ids[i],{elim:elem});
	      		}
	      }
	  	});
		formato12C.tablaResultados.jqGrid('navGrid',formato12C.paginadoResultados,{add:false,edit:false,del:false,search: false,refresh: false});	
		formato12C.tablaResultados.jqGrid('navButtonAdd',formato12C.paginadoResultados,{
		       caption:"Exportar a Excel",
		       buttonicon: "ui-icon-bookmark",
		       onClickButton : function () {
		           location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';
		       } 
		}); 
	},
	buildGridsImplementacion : function () {	
		formato12C.tablaImplementacion.jqGrid({
		   datatype: "local",
	       colNames: ['Año Ejec.','Mes Ejec.','Etapa Ejec.','Item','Cod. Ubigeo Origen','Localidad Origen','Cod. Ubigeo Destino','Localidad Destino','Zona Benef.','Cta. Contable','Actividad','Tipo Doc.','RUC','Serie Doc.','Nro. Doc.','Días','Alimentación','Alojamiento','Movilidad','Visualizar','Editar','Anular','','','','','',''],
	       colModel: [
					{ name: 'anoEjecucion', index: 'anoEjecucion', width: 60},
					{ name: 'descMesEjecucion', index: 'descMesEjecucion', width: 80},
					{ name: 'descEtapaEjecucion', index: 'descEtapaEjecucion', width: 100},
					{ name: 'item', index: 'item', width: 40},
					{ name: 'ubigeoOrigen', index: 'ubigeoOrigen', width: 120, align:'left'},
					{ name: 'localidadOrigen', index: 'localidadOrigen', width: 120, align:'left'},
					{ name: 'ubigeoDestino', index: 'ubigeoDestino', width: 120, align:'left'},
					{ name: 'localidadDestino', index: 'localidadDestino', width: 120, align:'left'},
					{ name: 'descZonaBenef', index: 'descZonaBenef', width: 120, align:'left'},
					{ name: 'cuentaContable', index: 'cuentaContable', width: 80, align:'left'},
					{ name: 'actividad', index: 'actividad', width: 120, align:'left'},
					{ name: 'tipoDoc', index: 'tipoDoc', width: 40, align:'left'},
					{ name: 'ruc', index: 'ruc', width: 80, align:'right'},
					{ name: 'serieDoc', index: 'serieDoc', width: 80},
					{ name: 'nroDoc', index: 'nroDoc', width: 60},
					{ name: 'nroDias', index: 'nroDias', width: 40},
					{ name: 'alimentacion', index: 'alimentacion', width: 100, align:'right'},
					{ name: 'alojamiento', index: 'alojamiento', width: 100, align:'right'},
					{ name: 'movilidad', index: 'movilidad', width: 100, align:'right'},
					{ name: 'view', index: 'view', width: 20,align:'center' },
					{ name: 'edit', index: 'edit', width: 20,align:'center' },
					{ name: 'elim', index: 'elim', width: 20,align:'center' },
					{ name: 'codEmpresa', index: 'codEmpresa', hidden: true},
					{ name: 'anoPresentacion', index: 'anoPresentacion', hidden: true },   
					{ name: 'mesPresentacion', index: 'mesPresentacion', hidden: true},
					{ name: 'etapa', index: 'etapa',hidden: true},
					{ name: 'mesEjecucion', index: 'mesEjecucion', hidden: true},
					{ name: 'etapaEjecucion', index: 'etapaEjecucion',hidden: true}
		   	    ],
		   	 multiselect: false,
				rowNum:10,
			   	rowList:[10,20,50],
				height: 200,
			   	autowidth: true,
				rownumbers: true,
				shrinkToFit:true,
				pager: formato12C.paginadoImplementacion,
			    viewrecords: true,
			   	caption: "Desplazamiento de Personal Implementación",
			    sortorder: "asc",	   	    	   	   
	       gridComplete: function(){
	    	   AUI().use('liferay-portlet-url', function(A) {
	    		      var ids = formato12C.tablaImplementacion.jqGrid('getDataIDs');
	    		      
		  	      		for(var i=0;i < ids.length;i++){
		  	      			var cl = ids[i];
		  	      			var ret = formato12C.tablaImplementacion.jqGrid('getRowData',cl);   
		  	      			//VIEW
		  	      			var urlView=Liferay.PortletURL.createRenderURL();
		  	      			urlView.setParameter("action", "detalle");
		  	      			urlView.setParameter("crud", "READCREATEUPDATE");
		  	      			urlView.setParameter("codigoEmpresaDetalle", ret.codEmpresa);
		  	      			urlView.setParameter("periodoEnvioDetalle", ret.anoPresentacion+completarCerosIzq(ret.mesPresentacion,2)+ret.etapa);
		  	      			urlView.setParameter("anoPresentacionDetalle", ret.anoPresentacion);
		  	      			urlView.setParameter("mesPresentacionDetalle", ret.mesPresentacion);
		  	      			urlView.setParameter("etapaDetalle", ret.etapa);
		  	    			urlView.setParameter("anoEjecucionDetalle", ret.anoEjecucion);
		  	    			urlView.setParameter("mesEjecucionDetalle", ret.mesEjecucion);
		  	    			urlView.setParameter("etapaEjecucionDetalle", ret.etapaEjecucion);
		  	    			urlView.setParameter("itemDetalle", ret.item);
		  	    			urlView.setPortletId(formato12C.portletID);
		  	      			//EDIT
		  	      			var urlEdit=Liferay.PortletURL.createRenderURL();
					  	    urlEdit.setParameter("action", "detalle");
					  	    urlEdit.setParameter("crud", "UPDATE");
					  	  	urlEdit.setParameter("codigoEmpresaDetalle", ret.codEmpresa);
					  	  	urlEdit.setParameter("periodoEnvioDetalle", ret.anoPresentacion+completarCerosIzq(ret.mesPresentacion,2)+ret.etapa);
					  		urlEdit.setParameter("anoPresentacionDetalle", ret.anoPresentacion);
					  		urlEdit.setParameter("mesPresentacionDetalle", ret.mesPresentacion);
					  		urlEdit.setParameter("etapaDetalle", ret.etapa);
					  		urlEdit.setParameter("anoEjecucionDetalle", ret.anoEjecucion);
					  		urlEdit.setParameter("mesEjecucionDetalle", ret.mesEjecucion);
					  		urlEdit.setParameter("etapaEjecucionDetalle", ret.etapaEjecucion);
					  		urlEdit.setParameter("itemDetalle", ret.item);
					  	  	urlEdit.setPortletId(formato12C.portletID);
		  	      			
		  	      			view = "<a href='"+urlView+"'><img border='0' title='View' src='/net-theme/images/img-net/file.png'  align='center'  /></a> ";
		  	      			edit = "<a href='"+urlEdit+"'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png'  align='center'  /></a> ";
		  	      			
		  	      			elem = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"formato12C.confirmarEliminarDetalle('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.etapa+"','"+ret.anoEjecucion+"','"+ret.mesEjecucion+"','"+ret.etapaEjecucion+"','"+ret.item+"');\" /></a> ";              			
		  	      			formato12C.tablaImplementacion.jqGrid('setRowData',ids[i],{view:view});
		  	      			formato12C.tablaImplementacion.jqGrid('setRowData',ids[i],{edit:edit});
		  	      			formato12C.tablaImplementacion.jqGrid('setRowData',ids[i],{elim:elem});
		  	      		}
	    		   });	
	      }
	  	});
		formato12C.tablaImplementacion.jqGrid('navGrid',formato12C.paginadoImplementacion,{add:false,edit:false,del:false,search: false,refresh: false});	
		formato12C.tablaImplementacion.jqGrid('navButtonAdd',formato12C.paginadoImplementacion,{
		       caption:"Exportar a Excel",
		       buttonicon: "ui-icon-bookmark",
		       onClickButton : function () {
		           location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';
		       } 
		}); 
	},
	buildGridsMensual : function () {	
		formato12C.tablaMensual.jqGrid({
		   datatype: "local",
	       colNames: ['Año Ejec.','Mes Ejec.','Etapa Ejec.','Item','Cod. Ubigeo Origen','Localidad Origen','Cod. Ubigeo Destino','Localidad Destino','Zona Benef.','Cta. Contable','Actividad','Tipo Doc.','RUC','Serie Doc.','Nro. Doc.','Días','Alimentación','Alojamiento','Movilidad','Visualizar','Editar','Anular','','','','','',''],
	       colModel: [
					{ name: 'anoEjecucion', index: 'anoEjecucion', width: 60},
					{ name: 'descMesEjecucion', index: 'descMesEjecucion', width: 80},
					{ name: 'descEtapaEjecucion', index: 'descEtapaEjecucion', width: 100},
					{ name: 'item', index: 'item', width: 40},
					{ name: 'ubigeoOrigen', index: 'ubigeoOrigen', width: 120, align:'left'},
					{ name: 'localidadOrigen', index: 'localidadOrigen', width: 120, align:'left'},
					{ name: 'ubigeoDestino', index: 'ubigeoDestino', width: 120, align:'left'},
					{ name: 'localidadDestino', index: 'localidadDestino', width: 120, align:'left'},
					{ name: 'descZonaBenef', index: 'descZonaBenef', width: 120, align:'left'},
					{ name: 'cuentaContable', index: 'cuentaContable', width: 80, align:'left'},
					{ name: 'actividad', index: 'actividad', width: 120, align:'left'},
					{ name: 'tipoDoc', index: 'tipoDoc', width: 40, align:'left'},
					{ name: 'ruc', index: 'ruc', width: 80, align:'right'},
					{ name: 'serieDoc', index: 'serieDoc', width: 80},
					{ name: 'nroDoc', index: 'nroDoc', width: 60},
					{ name: 'nroDias', index: 'nroDias', width: 40},
					{ name: 'alimentacion', index: 'alimentacion', width: 100, align:'right'},
					{ name: 'alojamiento', index: 'alojamiento', width: 100, align:'right'},
					{ name: 'movilidad', index: 'movilidad', width: 100, align:'right'},
					{ name: 'view', index: 'view', width: 20,align:'center' },
					{ name: 'edit', index: 'edit', width: 20,align:'center' },
					{ name: 'elim', index: 'elim', width: 20,align:'center' },
					{ name: 'codEmpresa', index: 'codEmpresa', hidden: true},
					{ name: 'anoPresentacion', index: 'anoPresentacion', hidden: true },   
					{ name: 'mesPresentacion', index: 'mesPresentacion', hidden: true},
					{ name: 'etapa', index: 'etapa',hidden: true},
					{ name: 'mesEjecucion', index: 'mesEjecucion', hidden: true},
					{ name: 'etapaEjecucion', index: 'etapaEjecucion',hidden: true}
		   	    ],
		   	 multiselect: false,
				rowNum:10,
			   	rowList:[10,20,50],
				height: 200,
			   	autowidth: true,
				rownumbers: true,
				shrinkToFit:true,
				pager: formato12C.paginadoMensual,
			    viewrecords: true,
			   	caption: "Desplazamiento de Personal Mensual",
			    sortorder: "asc",	   	    	   	   
	       gridComplete: function(){
	    	   AUI().use('liferay-portlet-url', function(A) {
	    		      var ids = formato12C.tablaMensual.jqGrid('getDataIDs');
	    		      
		  	      		for(var i=0;i < ids.length;i++){
		  	      			var cl = ids[i];
		  	      			var ret = formato12C.tablaMensual.jqGrid('getRowData',cl);   
		  	      			//VIEW
		  	      			var urlView=Liferay.PortletURL.createRenderURL();
		  	      			urlView.setParameter("action", "detalle");
		  	      			urlView.setParameter("crud", "READCREATEUPDATE");
			  	      		urlView.setParameter("codigoEmpresaDetalle", ret.codEmpresa);
			  	      		urlView.setParameter("periodoEnvioDetalle", ret.anoPresentacion+completarCerosIzq(ret.mesPresentacion,2)+ret.etapa);
		  	      			urlView.setParameter("anoPresentacionDetalle", ret.anoPresentacion);
		  	      			urlView.setParameter("mesPresentacionDetalle", ret.mesPresentacion);
		  	      			urlView.setParameter("etapaDetalle", ret.etapa);
		  	    			urlView.setParameter("anoEjecucionDetalle", ret.anoEjecucion);
		  	    			urlView.setParameter("mesEjecucionDetalle", ret.mesEjecucion);
		  	    			urlView.setParameter("etapaEjecucionDetalle", ret.etapaEjecucion);
		  	    			urlView.setParameter("itemDetalle", ret.item);
		  	      			urlView.setPortletId(formato12C.portletID);
		  	      			//EDIT
		  	      			var urlEdit=Liferay.PortletURL.createRenderURL();
					  	    urlEdit.setParameter("action", "detalle");
					  	    urlEdit.setParameter("crud", "UPDATE");
					  	  	urlEdit.setParameter("codigoEmpresaDetalle", ret.codEmpresa);
					  	 	urlEdit.setParameter("periodoEnvioDetalle", ret.anoPresentacion+completarCerosIzq(ret.mesPresentacion,2)+ret.etapa);
					  		urlEdit.setParameter("anoPresentacionDetalle", ret.anoPresentacion);
					  		urlEdit.setParameter("mesPresentacionDetalle", ret.mesPresentacion);
					  		urlEdit.setParameter("etapaDetalle", ret.etapa);
					  		urlEdit.setParameter("anoEjecucionDetalle", ret.anoEjecucion);
					  		urlEdit.setParameter("mesEjecucionDetalle", ret.mesEjecucion);
					  		urlEdit.setParameter("etapaEjecucionDetalle", ret.etapaEjecucion);
					  		urlEdit.setParameter("itemDetalle", ret.item);
					  	  	urlEdit.setPortletId(formato12C.portletID);
		  	      			
		  	      			view = "<a href='"+urlView+"'><img border='0' title='View' src='/net-theme/images/img-net/file.png'  align='center'  /></a> ";
		  	      			edit = "<a href='"+urlEdit+"'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png'  align='center'  /></a> ";
		  	      			
		  	      			elem = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"formato12C.confirmarEliminarDetalle('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.etapa+"','"+ret.anoEjecucion+"','"+ret.mesEjecucion+"','"+ret.etapaEjecucion+"','"+ret.item+"');\" /></a> ";              			
		  	      			formato12C.tablaMensual.jqGrid('setRowData',ids[i],{view:view});
		  	      			formato12C.tablaMensual.jqGrid('setRowData',ids[i],{edit:edit});
		  	      			formato12C.tablaMensual.jqGrid('setRowData',ids[i],{elim:elem});
		  	      		}
	    		   });	
	      }
	  	});
		formato12C.tablaMensual.jqGrid('navGrid',formato12C.paginadoMensual,{add:false,edit:false,del:false,search: false,refresh: false});	
		formato12C.tablaMensual.jqGrid('navButtonAdd',formato12C.paginadoMensual,{
		       caption:"Exportar a Excel",
		       buttonicon: "ui-icon-bookmark",
		       onClickButton : function () {
		           location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';
		       } 
		}); 
	},
	//VIEW
	buildGridsImplementacionView : function () {	
		formato12C.tablaImplementacionView.jqGrid({
		   datatype: "local",
		   colNames: ['Año Ejec.','Mes Ejec.','Etapa Ejec.','Item','Cod. Ubigeo Origen','Localidad Origen','Cod. Ubigeo Destino','Localidad Destino','Zona Benef.','Cta. Contable','Actividad','Tipo Doc.','RUC','Serie Doc.','Nro. Doc.','Días','Alimentación','Alojamiento','Movilidad','Visualizar','','','','','',''],
	       colModel: [
					{ name: 'anoEjecucion', index: 'anoEjecucion', width: 60},
					{ name: 'descMesEjecucion', index: 'descMesEjecucion', width: 80},
					{ name: 'descEtapaEjecucion', index: 'descEtapaEjecucion', width: 100},
					{ name: 'item', index: 'item', width: 40},
					{ name: 'ubigeoOrigen', index: 'ubigeoOrigen', width: 120, align:'left'},
					{ name: 'localidadOrigen', index: 'localidadOrigen', width: 120, align:'left'},
					{ name: 'ubigeoDestino', index: 'ubigeoDestino', width: 120, align:'left'},
					{ name: 'localidadDestino', index: 'localidadDestino', width: 120, align:'left'},
					{ name: 'descZonaBenef', index: 'descZonaBenef', width: 120, align:'left'},
					{ name: 'cuentaContable', index: 'cuentaContable', width: 80, align:'left'},
					{ name: 'actividad', index: 'actividad', width: 120, align:'left'},
					{ name: 'tipoDoc', index: 'tipoDoc', width: 40, align:'left'},
					{ name: 'ruc', index: 'ruc', width: 80, align:'right'},
					{ name: 'serieDoc', index: 'serieDoc', width: 80},
					{ name: 'nroDoc', index: 'nroDoc', width: 60},
					{ name: 'nroDias', index: 'nroDias', width: 40},
					{ name: 'alimentacion', index: 'alimentacion', width: 100, align:'right'},
					{ name: 'alojamiento', index: 'alojamiento', width: 100, align:'right'},
					{ name: 'movilidad', index: 'movilidad', width: 100, align:'right'},
					{ name: 'view', index: 'view', width: 20,align:'center' },
					{ name: 'codEmpresa', index: 'codEmpresa', hidden: true},
					{ name: 'anoPresentacion', index: 'anoPresentacion', hidden: true },   
					{ name: 'mesPresentacion', index: 'mesPresentacion', hidden: true},
					{ name: 'etapa', index: 'etapa',hidden: true},
					{ name: 'mesEjecucion', index: 'mesEjecucion', hidden: true},
					{ name: 'etapaEjecucion', index: 'etapaEjecucion',hidden: true}
		   	    ],
		   	 multiselect: false,
				rowNum:10,
			   	rowList:[10,20,50],
				height: 200,
			   	autowidth: true,
				rownumbers: true,
				shrinkToFit:true,
				pager: formato12C.paginadoImplementacionView,
			    viewrecords: true,
			   	caption: "Desplazamiento de Personal Implementación",
			    sortorder: "asc",	   	    	   	  
	       gridComplete: function(){
	    	   AUI().use('liferay-portlet-url', function(A) {
	    		      var ids = formato12C.tablaImplementacionView.jqGrid('getDataIDs');
	    		      
		  	      		for(var i=0;i < ids.length;i++){
		  	      			var cl = ids[i];
		  	      			var ret = formato12C.tablaImplementacionView.jqGrid('getRowData',cl);   
		  	      			var urlView=Liferay.PortletURL.createRenderURL();
		  	      		
		  	      			urlView.setParameter("action", "detalle");
		  	      			urlView.setParameter("crud", "READ");
			  	      		urlView.setParameter("codigoEmpresaDetalle", ret.codEmpresa);
			  	      		urlView.setParameter("periodoEnvioDetalle", ret.anoPresentacion+completarCerosIzq(ret.mesPresentacion,2)+ret.etapa);
		  	      			urlView.setParameter("anoPresentacionDetalle", ret.anoPresentacion);
		  	      			urlView.setParameter("mesPresentacionDetalle", ret.mesPresentacion);
		  	      			urlView.setParameter("etapaDetalle", ret.etapa);
		  	    			urlView.setParameter("anoEjecucionDetalle", ret.anoEjecucion);
		  	    			urlView.setParameter("mesEjecucionDetalle", ret.mesEjecucion);
		  	    			urlView.setParameter("etapaEjecucionDetalle", ret.etapaEjecucion);
		  	    			urlView.setParameter("itemDetalle", ret.item);
		  	      			urlView.setPortletId(formato12C.portletID);
		  	      			
		  	      			
		  	      			view = "<a href='"+urlView+"'><img border='0' title='View' src='/net-theme/images/img-net/file.png'  align='center'  /></a> ";
		  	      			formato12C.tablaImplementacionView.jqGrid('setRowData',ids[i],{view:view});
		  	      		}
	    		   });	
	      }
	  	});
		formato12C.tablaImplementacionView.jqGrid('navGrid',formato12C.paginadoImplementacionView,{add:false,edit:false,del:false,search: false,refresh: false});	
		formato12C.tablaImplementacionView.jqGrid('navButtonAdd',formato12C.paginadoImplementacionView,{
		       caption:"Exportar a Excel",
		       buttonicon: "ui-icon-bookmark",
		       onClickButton : function () {
		           location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';
		       } 
		}); 
	},
	buildGridsMensualView : function () {	
		formato12C.tablaMensualView.jqGrid({
		   datatype: "local",
		   colNames: ['Año Ejec.','Mes Ejec.','Etapa Ejec.','Item','Cod. Ubigeo Origen','Localidad Origen','Cod. Ubigeo Destino','Localidad Destino','Zona Benef.','Cta. Contable','Actividad','Tipo Doc.','RUC','Serie Doc.','Nro. Doc.','Días','Alimentación','Alojamiento','Movilidad','Visualizar','','','','','',''],
	       colModel: [
					{ name: 'anoEjecucion', index: 'anoEjecucion', width: 60},
					{ name: 'descMesEjecucion', index: 'descMesEjecucion', width: 80},
					{ name: 'descEtapaEjecucion', index: 'descEtapaEjecucion', width: 100},
					{ name: 'item', index: 'item', width: 40},
					{ name: 'ubigeoOrigen', index: 'ubigeoOrigen', width: 120, align:'left'},
					{ name: 'localidadOrigen', index: 'localidadOrigen', width: 120, align:'left'},
					{ name: 'ubigeoDestino', index: 'ubigeoDestino', width: 120, align:'left'},
					{ name: 'localidadDestino', index: 'localidadDestino', width: 120, align:'left'},
					{ name: 'descZonaBenef', index: 'descZonaBenef', width: 120, align:'left'},
					{ name: 'cuentaContable', index: 'cuentaContable', width: 80, align:'left'},
					{ name: 'actividad', index: 'actividad', width: 120, align:'left'},
					{ name: 'tipoDoc', index: 'tipoDoc', width: 40, align:'left'},
					{ name: 'ruc', index: 'ruc', width: 80, align:'right'},
					{ name: 'serieDoc', index: 'serieDoc', width: 80},
					{ name: 'nroDoc', index: 'nroDoc', width: 60},
					{ name: 'nroDias', index: 'nroDias', width: 40},
					{ name: 'alimentacion', index: 'alimentacion', width: 100, align:'right'},
					{ name: 'alojamiento', index: 'alojamiento', width: 100, align:'right'},
					{ name: 'movilidad', index: 'movilidad', width: 100, align:'right'},
					{ name: 'view', index: 'view', width: 20,align:'center' },
					{ name: 'codEmpresa', index: 'codEmpresa', hidden: true},
					{ name: 'anoPresentacion', index: 'anoPresentacion', hidden: true },   
					{ name: 'mesPresentacion', index: 'mesPresentacion', hidden: true},
					{ name: 'etapa', index: 'etapa',hidden: true},
					{ name: 'mesEjecucion', index: 'mesEjecucion', hidden: true},
					{ name: 'etapaEjecucion', index: 'etapaEjecucion',hidden: true}
		   	    ],
		   	 multiselect: false,
				rowNum:10,
			   	rowList:[10,20,50],
				height: 200,
			   	autowidth: true,
				rownumbers: true,
				shrinkToFit:true,
				pager: formato12C.paginadoMensualView,
			    viewrecords: true,
			   	caption: "Desplazamiento de Personal Mensual",
			    sortorder: "asc",	   	    	   	   
	       gridComplete: function(){
	    	   AUI().use('liferay-portlet-url', function(A) {
	    		      var ids = formato12C.tablaMensualView.jqGrid('getDataIDs');
	    		      
		  	      		for(var i=0;i < ids.length;i++){
		  	      			var cl = ids[i];
		  	      			var ret = formato12C.tablaMensualView.jqGrid('getRowData',cl);   
		  	      			var urlView=Liferay.PortletURL.createRenderURL();
		  	      		
		  	      			urlView.setParameter("action", "detalle");
		  	      			urlView.setParameter("crud", "READ");
			  	      		urlView.setParameter("codigoEmpresaDetalle", ret.codEmpresa);
			  	      		urlView.setParameter("periodoEnvioDetalle", ret.anoPresentacion+completarCerosIzq(ret.mesPresentacion,2)+ret.etapa);
		  	      			urlView.setParameter("anoPresentacionDetalle", ret.anoPresentacion);
		  	      			urlView.setParameter("mesPresentacionDetalle", ret.mesPresentacion);
		  	      			urlView.setParameter("etapaDetalle", ret.etapa);
		  	    			urlView.setParameter("anoEjecucionDetalle", ret.anoEjecucion);
		  	    			urlView.setParameter("mesEjecucionDetalle", ret.mesEjecucion);
		  	    			urlView.setParameter("etapaEjecucionDetalle", ret.etapaEjecucion);
		  	    			urlView.setParameter("itemDetalle", ret.item);
		  	      			urlView.setPortletId(formato12C.portletID);
		  	      			
		  	      			
		  	      			view = "<a href='"+urlView+"'><img border='0' title='View' src='/net-theme/images/img-net/file.png'  align='center'  /></a> ";
		  	      			formato12C.tablaMensualView.jqGrid('setRowData',ids[i],{view:view});
		  	      		}
	    		   });	
	      }
	  	});
		formato12C.tablaMensualView.jqGrid('navGrid',formato12C.paginadoMensualView,{add:false,edit:false,del:false,search: false,refresh: false});	
		formato12C.tablaMensualView.jqGrid('navButtonAdd',formato12C.paginadoMensualView,{
		       caption:"Exportar a Excel",
		       buttonicon: "ui-icon-bookmark",
		       onClickButton : function () {
		           location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';
		       } 
		}); 
	},
	/***BUSQUEDA Y CRUD***/
	buscarFormatos : function () {	

		jQuery.ajax({			
					url: formato12C.urlBusqueda+'&'+formato12C.formBusqueda.serialize(),
					type: 'post',
					dataType: 'json',
					beforeSend:function(){
						formato12C.blockUI();
					},				
					success: function(gridData) {					
						formato12C.tablaResultados.clearGridData(true);
						formato12C.tablaResultados.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
						formato12C.tablaResultados[0].refreshIndex();
						formato12C.unblockUI();
					},error : function(){
						alert("Error de conexión.");
						formato12C.unblockUI();
					}
			});


	},
	<portlet:namespace/>loadPeriodo : function(valPeriodo){
		jQuery.ajax({
				url: formato12C.urlCargaPeriodo+'&'+formato12C.formNuevo.serialize(),
				type: 'post',
				dataType: 'json',
				success: function(data) {		
					dwr.util.removeAllOptions("periodoEnvio");
					dwr.util.addOptions("periodoEnvio", data,"codigoItem","descripcionItem");
					if( valPeriodo.length!='' ){
						dwr.util.setValue("periodoEnvio", valPeriodo);
					}
					/*formato12C.<portlet:namespace/>loadCargaFlagPeriodo();
					
					//validar lima edelnor y luz del sur
					if(formato12C.cod_empresa_edelnor.val()==formato12C.f_empresa.val() || formato12C.cod_empresa_luz_sur.val()==formato12C.f_empresa.val()){
						formato12C.habilitarLima();										
					}else{
						formato12C.deshabilitarLima();
					}
					*/
					formato12C.unblockUI();
				},error : function(){
					alert("Error de conexión.");
					formato12C.unblockUI();
				}
		});
	},
	buscarDetalles : function () {	
        return jQuery.ajax({			
					url: formato12C.urlBusquedaDetalle+'&'+formato12C.formNuevo.serialize(),
					type: 'post',
					dataType: 'json',
					beforeSend:function(){
						$.blockUI({ message: formato12C.mensajeObteniendoDatos });
						//formato12C.blockUI();
					},				
					success: function(gridData) {					
						<c:if test="${crud =='READ'}">
							//
							formato12C.tablaImplementacionView.clearGridData(true);
							formato12C.tablaImplementacionView.jqGrid('setGridParam', {data: gridData.implementacion}).trigger('reloadGrid');
							formato12C.tablaImplementacionView[0].refreshIndex();
							//
							formato12C.tablaMensualView.clearGridData(true);
							formato12C.tablaMensualView.jqGrid('setGridParam', {data: gridData.operativa}).trigger('reloadGrid');
							formato12C.tablaMensualView[0].refreshIndex();
							//
						</c:if>
						<c:if test="${crud =='UPDATE' || crud =='CREATE' }">
							//	
							formato12C.tablaImplementacion.clearGridData(true);
							formato12C.tablaImplementacion.jqGrid('setGridParam', {data: gridData.implementacion}).trigger('reloadGrid');
							formato12C.tablaImplementacion[0].refreshIndex();
							//
							formato12C.tablaMensual.clearGridData(true);
							formato12C.tablaMensual.jqGrid('setGridParam', {data: gridData.operativa}).trigger('reloadGrid');
							formato12C.tablaMensual[0].refreshIndex();
							//
						</c:if>
						formato12C.unblockUI();
					},error : function(){
						alert("Error de conexión.");
						formato12C.unblockUI();
					}
			});
	},
	listarProvincias : function (codDepartamento,fuente) {	
		// fuente: '0' : ORIGEN
		// fuente: '1' : DESTINO
		jQuery.ajax({			
					url: formato12C.urlProvincias,
					type: 'post',
					dataType: 'json',
					data:{
						codDepartamento:codDepartamento
					},
					beforeSend:function(){
						formato12C.blockUI();
					},				
					success: function(data) {	
						
						if(fuente=='0'){
							dwr.util.removeAllOptions("codProvinciaOrigen");
							dwr.util.addOptions("codProvinciaOrigen", data,"codigoItem","descripcionItem");
							formato12C.limpiarDistritosOrigen();
						}else if(fuente=='1'){
							dwr.util.removeAllOptions("codProvinciaDestino");
							dwr.util.addOptions("codProvinciaDestino", data,"codigoItem","descripcionItem");
							formato12C.limpiarDistritosDestino();
						}
						
						formato12C.unblockUI();
					},error : function(){
						alert("Error de conexión.");
						formato12C.unblockUI();
					}
			});


	},
	listarDistritos : function (codProvincia,fuente) {	
		// fuente: '0' : ORIGEN
		// fuente: '1' : DESTINO
		jQuery.ajax({			
					url: formato12C.urlDistritos,
					type: 'post',
					dataType: 'json',
					data:{
						codProvincia:codProvincia
					},
					beforeSend:function(){
						formato12C.blockUI();
					},				
					success: function(data) {			
						
						if(fuente=='0'){
							dwr.util.removeAllOptions("codDistritoOrigen");
							dwr.util.addOptions("codDistritoOrigen", data,"codigoItem","descripcionItem");
						}else if(fuente=='1'){
							dwr.util.removeAllOptions("codDistritoDestino");
							dwr.util.addOptions("codDistritoDestino", data,"codigoItem","descripcionItem");
						}
						
						
						formato12C.unblockUI();
					},error : function(){
						alert("Error de conexión.");
						formato12C.unblockUI();
					}
			});


	},
	/***BLOQUEOS Y DESBLOQUEOS***/
	blockUI : function(){
		$.blockUI({ message: '<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Cargando </h3>' });
	},
	unblockUI : function(){
		$.unblockUI();
	},
	/***OTROS***/
	recargarPeriodoEjecucion : function(valAnioInicio,valAnioFin){
		$('#anioInicioVigencia').val(valAnioInicio);
		$('#anioFinVigencia').val(valAnioFin);
	},
	mostrarPeriodoEjecucion : function(){
		$('#anioInicioVigencia').attr("disabled",true);
		$('#anioFinVigencia').attr("disabled",true);
		//formato14A.quitarEstiloEdicionCabecera();
	},
	construirPeriodoEnvio : function(anoPresentacion,mesPresentacion,etapa){
		dwr.util.removeAllOptions("periodoEnvio");
		var codigo=''+anoPresentacion+completarCerosIzq(mesPresentacion,2)+etapa;
		var descripcion=formato12C.mostrarDescripcionPeriodo(anoPresentacion,mesPresentacion,etapa);
   		var dataPeriodo = [{codigoItem:codigo, descripcionItem:descripcion}];   
   		dwr.util.addOptions("periodoEnvio", dataPeriodo,"codigoItem","descripcionItem");
	},
	mostrarDescripcionPeriodo : function(anio,mes,etapa){
		  var monthNames = ["Ene","Feb","Mar","Abr","May","Jun","Jul","Ago","Sep","Oct","Nov","Dic"];
		  var descripcionPeriodo;
		  //alert(monthNames[mes-1]);
		  descripcionPeriodo=''+monthNames[mes-1]+'-'+anio+' / '+etapa;
		  //alert(descripcionPeriodo);
		  return descripcionPeriodo;
	},
	//departamentos provincias distritos
	construirDepartamentoOrigen : function(codDepartamento,descDepartamento){
		dwr.util.removeAllOptions("codDepartamentoOrigen");
		//var codigo=codDepartamento;
		//var descripcion=descDepartamento;
   		var dataPeriodo = [{codigoItem:codDepartamento, descripcionItem:descDepartamento}];   
   		dwr.util.addOptions("codDepartamentoOrigen", dataPeriodo,"codigoItem","descripcionItem");
	},
	construirProvinciaOrigen : function(codProvincia,descProvincia){
		dwr.util.removeAllOptions("codProvinciaOrigen");
		var codigo=''+codProvincia;
		var descripcion=''+descProvincia;
   		var dataPeriodo = [{codigoItem:codigo, descripcionItem:descripcion}];   
   		dwr.util.addOptions("codProvinciaOrigen", dataPeriodo,"codigoItem","descripcionItem");
	},
	construirDistritoOrigen : function(codDistrito,descDistrito){
		dwr.util.removeAllOptions("codDistritoOrigen");
		var codigo=''+codDistrito;
		var descripcion=''+descDistrito;
   		var dataPeriodo = [{codigoItem:codigo, descripcionItem:descripcion}];   
   		dwr.util.addOptions("codDistritoOrigen", dataPeriodo,"codigoItem","descripcionItem");
	},
	//
	construirDepartamentoDestino : function(codDepartamento,descDepartamento){
		dwr.util.removeAllOptions("codDepartamentoDestino");
		//var codigo=codDepartamento;
		//var descripcion=descDepartamento;
   		var dataPeriodo = [{codigoItem:codDepartamento, descripcionItem:descDepartamento}];   
   		dwr.util.addOptions("codDepartamentoDestino", dataPeriodo,"codigoItem","descripcionItem");
	},
	construirProvinciaDestino : function(codProvincia,descProvincia){
		dwr.util.removeAllOptions("codProvinciaDestino");
		var codigo=''+codProvincia;
		var descripcion=''+descProvincia;
		var dataPeriodo = [{codigoItem:codigo, descripcionItem:descripcion}];   
   		dwr.util.addOptions("codProvinciaDestino", dataPeriodo,"codigoItem","descripcionItem");
	},
	construirDistritoDestino : function(codDistrito,descDistrito){
		dwr.util.removeAllOptions("codDistritoDestino");
		var codigo=''+codDistrito;
		var descripcion=''+descDistrito;
		var dataPeriodo = [{codigoItem:codigo, descripcionItem:descripcion}];   
   		dwr.util.addOptions("codDistritoDestino", dataPeriodo,"codigoItem","descripcionItem");
	},
	//
	<portlet:namespace/>showUploadExcel : function(){
		formato12C.divOverlay.show();
		formato12C.dialogCargaExcel.show();
		formato12C.dialogCargaExcel.show();
		formato12C.dialogCargaExcel.css({ 
	        'left': ($(window).width() / 2 - formato12C.dialogCargaExcel.width() / 2) + 'px', 
	        'top': ($(window).height()  - formato12C.dialogCargaExcel.height() ) + 'px'
	    });
	},
	
	closeDialogCargaExcel : function(){
		formato12C.dialogCargaExcel.hide();
		formato12C.divOverlay.hide();   
	},
	
	<portlet:namespace/>cargarFormatoExcel : function(){
		formato12C.formNuevo.submit();
	},
	
	<portlet:namespace/>showUploadTxt : function(){
		formato12C.divOverlay.show();
		formato12C.dialogCargaTexto.show();
		formato12C.dialogCargaTexto.css({ 
	        'left': ($(window).width() / 2 - formato12C.dialogCargaTexto.width() / 2) + 'px', 
	        'top': ($(window).height()  - formato12C.dialogCargaTexto.height() ) + 'px'
	    });
	},
	
	closeDialogCargaTxt : function(){
		formato12C.dialogCargaTexto.hide();
		formato12C.divOverlay.hide();   
	},

	
	
	emptySelectObject: function(){
		var jsonVacio='[{"descripcionItem":"--Seleccione--","codigoItem":""}]';
		return eval("(" + jsonVacio + ")");
	},
	//origen
	limpiarProvinciasOrigen:function(){
		dwr.util.removeAllOptions("codProvinciaOrigen");
		dwr.util.addOptions("codProvinciaOrigen", formato12C.emptySelectObject(),"codigoItem","descripcionItem");
	},
	limpiarDistritosOrigen:function(){
		dwr.util.removeAllOptions("codDistritoOrigen");
		dwr.util.addOptions("codDistritoOrigen", formato12C.emptySelectObject(),"codigoItem","descripcionItem");
	},
	//destino
	limpiarProvinciasDestino:function(){
		dwr.util.removeAllOptions("codProvinciaDestino");
		dwr.util.addOptions("codProvinciaDestino", formato12C.emptySelectObject(),"codigoItem","descripcionItem");
	},
	limpiarDistritosDestino:function(){
		dwr.util.removeAllOptions("codDistritoDestino");
		dwr.util.addOptions("codDistritoDestino", formato12C.emptySelectObject(),"codigoItem","descripcionItem");
	},
	
	//calculo total
	calculoTotal : function(){
		var alimentacion;
		var alojamiento;
		var movilidad;
		var total;
		alimentacion=formato12C.montoAlimentacionDetalle.val();
		alojamiento=formato12C.montoAlojamientoDetalle.val();
		movilidad=formato12C.montoMovilidadDetalle.val();
		console.debug(''+alimentacion+','+alojamiento+','+movilidad);
		total=parseFloat(alimentacion)+parseFloat(alojamiento)+parseFloat(movilidad);
		formato12C.totalDetalle.val(parseFloat(total));
	},
	//MOSTRAR REPORTE
	<portlet:namespace/>mostrarReportePdf : function(){
		var form = formato12C.formNuevo;
		form.removeAttr('enctype');
		
		jQuery.ajax({
			url : formato12C.urlReporte+'&'+form.serialize(),
			type : 'post',
			dataType : 'json',
			data : {
				<portlet:namespace />nombreReporte: 'formato12C',
				<portlet:namespace />nombreArchivo: 'formato12C',
				<portlet:namespace />tipoArchivo: '0'//PDF
			},
			success : function(gridData) {
				formato12C.verReporte();
			},error : function(){
				alert("Error de conexión.");
				formato12C.unblockUI();
			}
		});
	},
	<portlet:namespace/>mostrarReporteExcel : function(){
		var form = formato12C.formNuevo;
		form.removeAttr('enctype');
		jQuery.ajax({
			url : formato12C.urlReporte+'&'+form.serialize(),
			type : 'post',
			dataType : 'json',
			data : {
				<portlet:namespace />nombreReporte: 'formato12C',
				<portlet:namespace />nombreArchivo: 'formato12C',
				<portlet:namespace />tipoArchivo: '1'//XLS
			},
			success : function(gridData) {
				formato12C.verReporte();
			},error : function(){
				alert("Error de conexión.");
				formato12C.unblockUI();
			}
		});
	},
	verReporte : function(){
		window.open('<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ViewReport")%>','_newtab');
	},
	//procesos de validacion y envio definitivo
	<portlet:namespace/>validacionFormato : function(){
		var form = formato12C.formNuevo;
		form.removeAttr('enctype');
		jQuery.ajax({
			url: formato12C.urlValidacion+'&'+form.serialize(),
			type : 'post',
			dataType : 'json',
			/*data : {
				<portlet:namespace />codEmpresa: formato12C.f_empresa.val(),
				<portlet:namespace />periodoEnvio: formato12C.f_periodoEnvio.val(),
				<portlet:namespace />anoInicioVigencia: $('#anioInicioVigencia').val(),
				<portlet:namespace />anoFinVigencia: $('#anioFinVigencia').val()
			},*/
			success : function(data) {
				if( data!=null ){
					formato12C.dialogObservacion.dialog("open");
					formato12C.tablaObservacion.clearGridData(true);
					formato12C.tablaObservacion.jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
					formato12C.tablaObservacion[0].refreshIndex();
					formato12C.unblockUI();
				}

			},error : function(){
				alert("Error de conexión.");
				formato12C.unblockUI();
			}
		});
	},
	<portlet:namespace/>mostrarReporteValidacion : function(){
		var form = formato12C.formNuevo;
		form.removeAttr('enctype');
		jQuery.ajax({
			url: formato12C.urlReporteValidacion+'&'+form.serialize(),
			type : 'post',
			dataType : 'json',
			data : {
				//<portlet:namespace />periodoEnvio: formato12C.f_periodoEnvio.val(),
				<portlet:namespace />nombreReporte: 'validacion13',
				<portlet:namespace />nombreArchivo: 'validacion13',
				<portlet:namespace />tipoArchivo: '0',
				anioInicioVigencia:formato12C.txtInicioVig.val(),
				anioFinVigencia:formato12C.txtFinVig.val() //PDF
			},
			success : function(gridData) {
				formato12C.verReporte();
			},error : function(){
				alert("Error de conexión.");
				formato12C.unblockUI();
			}
		});
	},
	confirmarEnvioDefinitivo : function(){	
		var addhtml='¿Está seguro que desea realizar el envío definitivo?';
		formato12C.dialogConfirmEnvioContent.html(addhtml);
		formato12C.dialogConfirmEnvio.dialog("open");
	},
	<portlet:namespace/>envioDefinitivo : function(){
		var form = formato12C.formNuevo;
		form.removeAttr('enctype');
		jQuery.ajax({
			url: formato12C.urlEnvioDefinitivo+'&'+form.serialize(),
			type : 'post',
			dataType : 'json',
			data : {
				//<portlet:namespace />codEmpresa: formato12C.f_empresa.val(),
				//<portlet:namespace />periodoEnvio: formato12C.f_periodoEnvio.val(),
				//<portlet:namespace />anoInicioVigencia: $('#anioInicioVigencia').val(),
				//<portlet:namespace />anoFinVigencia: $('#anioFinVigencia').val(),
				<portlet:namespace />nombreReporte: 'formato12C',
				<portlet:namespace />nombreArchivo: 'formato12C',
				<portlet:namespace />tipoArchivo: '0'//PDF
			},
			success : function(gridData) {
				var addhtml='Se realizó el envío satisfactoriamente el envío.';
				formato12C.dialogMessageReportContent.html(addhtml);
				formato12C.dialogMessageReport.dialog("open");
				formato12C.unblockUI();
			},error : function(){
				alert("Error de conexión.");
				formato12C.unblockUI();
			}
		});
	},
	buildGridsObservacion : function () {	
		formato12C.tablaObservacion.jqGrid({
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
				pager: formato12C.paginadoObservacion,
			    viewrecords: true,
			   	sortorder: "asc"
	  	});
		formato12C.tablaObservacion.jqGrid('navGrid',formato12C.paginadoObservacion,{add:false,edit:false,del:false,search: false,refresh: false});	
		formato12C.tablaObservacion.jqGrid('navButtonAdd',formato12C.paginadoObservacion,{
		       caption:"Exportar a Excel",
		       buttonicon: "ui-icon-bookmark",
		       onClickButton : function () {
		           location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';
		       } 
		}); 
		formato12C.tablaObservacion.jqGrid('navButtonAdd',formato12C.paginadoObservacion,{
		       caption:"Exportar a Pdf",
		       buttonicon: "ui-icon-bookmark",
		       onClickButton : function () {
		    	   formato12C.<portlet:namespace/>mostrarReporteValidacion();
		       } 
		});
	},
	<portlet:namespace/>mostrarReporteEnvioDefinitivo : function(){
		var form = formato12C.formNuevo;
		form.removeAttr('enctype');
		jQuery.ajax({
			url: formato12C.urlReporteEnvioDefinitivo+'&'+form.serialize(),
			type : 'post',
			dataType : 'json',
			data : {
				<portlet:namespace />tipoArchivo: '2'//PDF+concatenado
			},
			success : function(gridData) {
				formato12C.verReporte();
			},error : function(){
				alert("Error de conexión.");
				formato12C.unblockUI();
			}
		});
	},
	<portlet:namespace/>mostrarReporteActaEnvio : function(){
		if(formato12C.txtEstado.val()=='Enviado'){
			var form = formato12C.formNuevo;
			form.removeAttr('enctype');
			jQuery.ajax({
				url: formato12C.urlReporteActaEnvio+'&'+form.serialize(),
				type : 'post',
				dataType : 'json',
				data : {
					/*<portlet:namespace />codEmpresa: formato12C.f_empresa.val(),
					<portlet:namespace />periodoEnvio: formato12C.f_periodoEnvio.val(),
					<portlet:namespace />anoInicioVigencia: $('#anioInicioVigencia').val(),
					<portlet:namespace />anoFinVigencia: $('#anioFinVigencia').val(),*/
					<portlet:namespace />tipoArchivo: '0'//PDF
				},
				success : function(gridData) {
					formato12C.verReporte();
				},error : function(){
					alert("Error de conexión.");
					formato12C.unblockUI();
				}
			});
		}else{
			alert("Primero debe realizar el envío definitivo");
		}
	},
	//inicializar dialogs
	initDialogs : function(){	
		formato12C.dialogConfirm.dialog({
			modal: true,
			height: 200,
			width: 400,			
			autoOpen: false,
			buttons: {
				"Si": function() {
					formato12C.eliminarFormatoCabecera(cod_Empresa_cabecera,ano_Presentacion_cabecera,mes_Presentacion_cabecera,cod_Etapa_cabecera);
					$(this).dialog("close");
				},
				"No": function() {				
					$(this).dialog("close");
				}
			}
		});
	},
	initDialogsCRUD : function(){	
		formato12C.dialogMessageReport.dialog({
			modal: true,
			autoOpen: false,
			buttons: {
				'Imprimir Pdf': function() {
					formato12C.<portlet:namespace/>mostrarReporteEnvioDefinitivo();
					$(this).dialog("close");
					formato12C.botonRegresarBusqueda.trigger('click');
				},
				Ok: function() {
					$(this).dialog("close");
					formato12C.botonRegresarBusqueda.trigger('click');
				}
			},
			close: function(event,ui){
				formato12C.botonRegresarBusqueda.trigger('click');
	       	}
		});
		formato12C.dialogConfirmEnvio.dialog({
			modal: true,
			height: 200,
			width: 400,			
			autoOpen: false,
			buttons: {
				"Si": function() {
					formato12C.<portlet:namespace/>envioDefinitivo();
					$(this).dialog("close");
				},
				"No": function() {				
					$(this).dialog("close");
				}
			}
		});
		formato12C.dialogConfirmDetalle.dialog({
			modal: true,
			height: 200,
			width: 400,			
			autoOpen: false,
			buttons: {
				"Si": function() {
					formato12C.eliminarFormatoDetalle(cod_Empresa,ano_Presentacion,mes_Presentacion,cod_Etapa,ano_Ejecucion,mes_Ejecucion,etapa_Ejecucion,item_Etapa);
					$(this).dialog("close");
				},
				"No": function() {				
					$(this).dialog("close");
				}
			}
		});
		formato12C.dialogObservacion.dialog({
			modal: true,
			width: 750,
			autoOpen: false,
			buttons: {
				Cerrar: function() {
					$(this).dialog("close");
				}
			}
		});
	},
	//otros
	confirmarEliminarCabecera : function(codEmpresa,anoPresentacion,mesPresentacion,etapa,flagOperacion){
		if(flagOperacion=='ABIERTO'){
			var addhtml='¿Está seguro que desea eliminar el registro seleccionado?';
			formato12C.dialogConfirmContent.html(addhtml);
			formato12C.dialogConfirm.dialog("open");
			cod_Empresa_cabecera=codEmpresa;
			ano_Presentacion_cabecera=anoPresentacion;
			mes_Presentacion_cabecera=mesPresentacion;
			cod_Etapa_cabecera=etapa;
		}else if(flagOperacion=='CERRADO'){
			alert(" No esta habilitado para realizar esta operacion");		
		}else{
			alert("El formato ya fue enviado a OSINERGMIN-GART");	
		}
	},
	confirmarEliminarDetalle : function(codEmpresa,anoPresentacion,mesPresentacion,etapa,anoEjecucion,mesEjecucion,etapaEjecucion,item){
		var addhtml='¿Está seguro que desea eliminar el registro seleccionado?';
		formato12C.dialogConfirmDetalleContent.html(addhtml);
		formato12C.dialogConfirmDetalle.dialog("open");
		cod_Empresa=codEmpresa;
		ano_Presentacion=anoPresentacion;
		mes_Presentacion=mesPresentacion;
		cod_Etapa=etapa;
		ano_Ejecucion=anoEjecucion;
		mes_Ejecucion=mesEjecucion;
		etapa_Ejecucion=etapaEjecucion;
		item_Etapa=item;
	},
	eliminarFormatoDetalle : function(cod_Empresa,ano_Presentacion,mes_Presentacion,cod_Etapa,ano_Ejecucion,mes_Ejecucion,etapa_Ejecucion,item_Etapa){			
		//$.blockUI({ message: formato12C.mensajeEliminando });
		var form = formato12C.formNuevo;
		form.removeAttr('enctype');
		jQuery.ajax({
			url: formato12C.urlDeleteDetalle+'&'+form.serialize(),
			type: 'post',
			dataType: 'json',
			data: {
			   <portlet:namespace />anoEjecucion: ano_Ejecucion,
			   <portlet:namespace />mesEjecucion: mes_Ejecucion,
			   <portlet:namespace />etapaEjecucion: etapa_Ejecucion,
			   <portlet:namespace />item: item_Etapa
				},
			success: function(data) {
				if (data.resultado == "OK"){
					//var addhtml2='Registro eliminado con éxito';					
					//formato12C.dialogMessageContent.html(addhtml2);
					//formato12C.dialogMessage.dialog("open");
					formato12C.buscarDetalles();
					formato12C.unblockUI();
				}
				else{
					alert("Error al eliminar el registro");
					formato12C.unblockUI();
				}
			},error : function(){
				alert("Error de conexión.");
				formato12C.unblockUI();
			}
		});
	},
	eliminarFormatoCabecera : function(cod_Empresa_cabecera,ano_Presentacion_cabecera,mes_Presentacion_cabecera,cod_Etapa_cabecera){			
		//$.blockUI({ message: formato12C.mensajeEliminando });
		//alert(cod_Empresa_cabecera+','+ano_Presentacion_cabecera+','+mes_Presentacion_cabecera+','+cod_Etapa_cabecera);
		jQuery.ajax({
			url: formato12C.urlDelete+'&'+formato12C.formBusqueda.serialize(),
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
					//formato12C.dialogMessageContent.html(addhtml2);
					//formato12C.dialogMessage.dialog("open");
					formato12C.buscarFormatos();
					formato12C.unblockUI();
				}
				else{
					alert("Error al eliminar el registro");
					formato12C.unblockUI();
				}
			},error : function(){
				alert("Error de conexión.");
				formato12C.unblockUI();
			}
		});
	},
	confirmarEditCabecera : function(codEmpresa,anoPresentacion,mesPresentacion,etapa,flagOperacion){
		if(flagOperacion=='ABIERTO'){
			location.href=formato12C.urlACrud+'&codEmpresa='+codEmpresa+'&anioPresentacion='+anoPresentacion+'&mesPresentacion='+mesPresentacion+'&etapa='+etapa+'&tipo=1';
		}else if(flagOperacion=='CERRADO'){
			alert(" No esta habilitado para realizar esta operacion");		
		}else{
			alert("El formato ya fue enviado a OSINERGMIN-GART");	
		}
	},
	/***VALIDACIONES**/
	validarFormatoDetalle : function(){
		if( formato12C.codEmpresaDetalle.val().length=='' ){
			alert('Seleccione una empresa'); 
			formato12C.codEmpresaDetalle.val().focus();
		    return false;
		}
		if(formato12C.periodoEnvioDetalle.val().length == '' ) {		  
			alert('Debe ingresar el periodo de presentacion');
			formato12C.periodoEnvioDetalle.val().focus();
   			return false; 
 		}
		if(formato12C.anoEjecucionDetalle.val().length == '' ) {		  
		    alert('Debe ingresar el año de ejecucion');
		    formato12C.anoEjecucionDetalle.focus();
		    return false; 
	  	}else{
		  	var numstr = trim(formato12C.anoEjecucionDetalle.val());
		 	 if (isNaN(numstr) || numstr.length<4 || parseFloat(numstr)<1900){
			  	alert('Ingrese un año de ejecucion válido');
			  	return false;
		  	}
	 	 }
		if(formato12C.mesEjecucionDetalle.val().length == '' ) {		  
		   alert('Debe seleccionar el mes de ejecucion');
		   formato12C.mesEjecucionDetalle.focus();
		   return false; 
		}
		if(formato12C.etapaEjecucionDetalle.val().length == '' ) {		  
		   alert('Debe ingresar la etapa de ejecución');
		   formato12C.etapaEjecucionDetalle.focus();
		   return false; 
		}
		//validamos ubigeo origen
		if(formato12C.codDepaOrigen.val().length != '' ) {
			if(formato12C.codProvOrigen.val().length == '' ) {		  
			   alert('Debe seleccionar la provincia de origen');
			   formato12C.codProvOrigen.focus();
			   return false; 
			}else{
				if(formato12C.codDistOrigen.val().length == '' ) {		  
				   alert('Debe seleccionar el distrito de origen');
				   formato12C.codDistOrigen.focus();
				   return false; 
				}
			}
		}
		//validamos ubigeo destino
		if(formato12C.codDepaDestino.val().length != '' ) {
			if(formato12C.codProvDestino.val().length == '' ) {		  
			   alert('Debe seleccionar la provincia de destino');
			   formato12C.codProvDestino.focus();
			   return false; 
			}else{
				if(formato12C.codDistDestino.val().length == '' ) {		  
				   alert('Debe seleccionar el distrito de destino');
				   formato12C.codDistDestino.focus();
				   return false; 
				}
			}
		}
		if(formato12C.idZonaBenefDetalle.val().length == '' ) {		  
		   alert('Debe seleccionar la Zona Beneficiario');
		   formato12C.etapaEjecucionDetalle.focus();
		   return false; 
		}
		//validamos si escogemos un tipo de documento
		if(formato12C.tipoDocDetalle.val().length != '' ) {
			if(formato12C.rucEmpresaDetalle.val().length == '' ) {		  
			   alert('Debe ingresar el RUC de la empresa');
			   formato12C.rucEmpresaDetalle.focus();
			   return false; 
			}
			if(formato12C.serieDocDetalle.val().length == '' ) {		  
			   alert('Debe ingresar la serie de documento de referencia');
			   formato12C.serieDocDetalle.focus();
			   return false; 
			}
			if(formato12C.nroDocDetalle.val().length == '' ) {		  
			   alert('Debe ingresar el nro. documento de referencia');
			   formato12C.nroDocDetalle.focus();
			   return false; 
			}
		}
		//
		return true; 
	},
	soloNumerosDecimales : function(){
		formato12C.montoAlimentacionDetalle.attr("onkeypress","return soloNumerosDecimales(event, 2, 'montoAlimentacion',7,2)");
		formato12C.montoAlojamientoDetalle.attr("onkeypress","return soloNumerosDecimales(event, 2, 'montoAlojamiento',7,2)");
		formato12C.montoMovilidadDetalle.attr("onkeypress","return soloNumerosDecimales(event, 2, 'montoMovilidad',7,2)");
	}
};



</script>