<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects />

<script type="text/javascript" src="/fise-projects-portlet/js/fise.js"></script>

<script type="text/javascript">
var formato12D= {
	
	/**********INICIO**********/
	
	codEmpresaBusqueda:null,
	
	anioDesde:null,
	mesDesde:null,
	anioHasta:null,
	mesHasta:null,
	
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
	
	//addd
	dialogMessageInfo:null,
	dialogMessageInfoContent:null,
	dialogMessageWarning:null,
	dialogMessageWarningContent:null,
	
	//mensajes
	mensajeObteniendoDatos:null,
	mensajeCargando:null,
	mensajeEliminando:null,
	//
	portletID:null,
	
	/**********CRUD**********/
	flagCarga:null,
	divInformacion:null,
	
	msgTransaccion:null,
	
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
	
	labelEstado:null,
	labelGrupoInfo:null,
	estado:null,
	grupoInfo:null,
	
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
	//dialogs
	dialogMessage:null,
	dialogMessageContent:null,
	
	dialogError:null,
	
	dialogMessageDetalle:null,
	dialogObservacion:null,
	dialogMessageReport:null,
	dialogConfirmEnvio:null,
	dialogMessageDetalleContent:null,
	dialogMessageReportContent:null,
	dialogConfirmEnvioContent:null,
	dialogConfirmDetalle:null,
	dialogConfirmDetalleContent:null,
	
	//addd
	dialogMessageInfoCrud:null,
	dialogMessageInfoCrudContent:null,
	dialogMessageWarningCrud:null,
	dialogMessageWarningCrudContent:null,
	dialogMessageErrorCrud:null,
	dialogMessageErrorCrudContent:null,
	
	//mensajes
	mensajeObteniendoDatosDetalle:null,
	mensajeCargandoDetalle:null,
	mensajeEliminandoDetalle:null,
	
	tipoOperacion:null,
	
	/**********DETALLE CRUD**********/
	
	//valores constantes para edelnor y luz del sur
	cod_empresa_edelnor:null,
	cod_empresa_luz_sur:null,
	//
	msgTransaccionDetalle:null,
	botonGuardarDetalle:null,
	
	flagPeriodoDetalle:null,
	
	codDepa:null,
	codProv:null,
	codDist:null,
	
	codDepartamento:null,
	codProvincia:null,
	codDistrito:null,
	
	codDepartamentoHidden:null,
	codProvinciaHidden:null,
	codDistritoHidden:null,
	
	descDepa:null,
	descProv:null,
	descDist:null,
	
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
	localidadDetalle:null,
	idZonaBenefDetalle:null,
	cuentaContableDetalle:null,
	gastoDetalle:null,
	tipoGastoDetalle:null,
	tipoDocDetalle:null,
	rucEmpresaDetalle:null,
	serieDocDetalle:null,
	nroDocDetalle:null,
	fechaAutoDetalle:null,
	nroDocAutoDetalle:null,
	cantidadDetalle:null,
	costoUnitarioDetalle:null,
	totalDetalle:null,
	//
	anoEjecucionHiddenDetalle:null,
	mesEjecucionHiddenDetalle:null,
	etapaEjecucionHiddenDetalle:null,
	
	botonAnadirFormato:null,
	botonRegresarBusqueda:null,
	//mensajes
	mensajeGuardandoDetalle:null,
	
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
		this.formBusqueda=$('#formato12DCBean');
		this.botonCrearFormato=$('#<portlet:namespace/>crearFormato');
		this.divOverlay=$("#<portlet:namespace/>divOverlay");
		this.mensajeObteniendoDatos='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Obteniendo Datos </h3>';
		this.urlACrud=urlView;
		this.urlDelete='<portlet:resourceURL id="deleteCabecera" />';
		this.dialogConfirm=$("#<portlet:namespace/>dialog-confirm");
		this.dialogConfirmContent=$("#<portlet:namespace/>dialog-confirm-content");
		
		this.codEmpresaBusqueda=$('#codEmpresaB');
		
		this.anioDesde=$('#anioDesde');
		this.mesDesde=$('#mesDesde');
		this.anioHasta=$('#anioHasta');
		this.mesHasta=$('#mesHasta');
		
		this.dialogMessageInfo=$("#<portlet:namespace/>dialog-message-info");
		this.dialogMessageInfoContent=$("#<portlet:namespace/>dialog-message-info-content");
		this.dialogMessageWarning=$("#<portlet:namespace/>dialog-message-warning");
		this.dialogMessageWarningContent=$("#<portlet:namespace/>dialog-message-warning-content");
		
		formato12D.initDialogs();
		
		formato12D.botonCrearFormato.click(function() {
			formato12D.blockUI();
			location.href=urlNuevo+'&codEmpresa='+formato12D.codEmpresaBusqueda.val();
		});
		formato12D.botonBuscar.click(function() {
			formato12D.buscarFormatos();
		});
		
		formato12D.buildGridsBusqueda();
		formato12D.botonBuscar.trigger('click');
	},
	
	initCRUD : function(operacion,urlAnadirFormato,urlRegresarBusqueda){
		this.portletID='<%=PortalUtil.getPortletId(renderRequest)%>';
		
		this.flagCarga=$('#<portlet:namespace/>flagCarga');
		this.divInformacion=$("#<portlet:namespace/>divInformacion");
		
		this.msgTransaccion=$("#msgTransaccion");
		
		this.urlCargaDeclaracion='<portlet:resourceURL id="cargaPeriodoDeclaracion" />';
		
		this.urlCargaPeriodo='<portlet:resourceURL id="cargaPeriodo" />';
		this.urlCargaFlagPeriodo='<portlet:resourceURL id="cargaFlagPeriodo" />';
		
		this.urlBusquedaDetalle='<portlet:resourceURL id="busquedaDetalle" />';
		this.formNuevo=$('#formato12DCBean');
		
		this.tipoOperacion=$('#tipoOperacion');
		
		this.codEmpresa=$('#codigoEmpresa');
		this.periodoEnvio=$('#periodoEnvio');
		//
		this.anoPresentacion=$('#anioPresentacion');
		this.mesPresentacion=$('#mesPresentacion');
		this.etapa=$('#etapa');
		
		this.estado=$('#descEstado');
		this.grupoInfo=$('#descGrupoInformacion');
		this.labelEstado=$('#o_descEstado');
		this.labelGrupoInfo=$('#o_descGrupoInformacion');
		
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
		this.dialogError=$("#<portlet:namespace/>dialog-form-error");
		
		this.dialogObservacion=$("#<portlet:namespace/>dialog-form-observacion");
		this.dialogMessageReport=$("#<portlet:namespace/>dialog-message-report");
		this.dialogMessageReportContent=$("#<portlet:namespace/>dialog-message-report-content");
		this.dialogConfirmEnvio=$("#<portlet:namespace/>dialog-confirm-envio");
		this.dialogConfirmEnvioContent=$("#<portlet:namespace/>dialog-confirm-envio-content");
		this.dialogConfirmDetalle=$("#<portlet:namespace/>dialog-confirm-detalle");
		this.dialogConfirmDetalleContent=$("#<portlet:namespace/>dialog-confirm-detalle-content");
		
		this.dialogMessage=$("#<portlet:namespace/>dialog-message");
		this.dialogMessageContent=$("#<portlet:namespace/>dialog-message-content");
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
		
		this.dialogMessageInfoCrud=$("#<portlet:namespace/>dialog-message-info-crud");
		this.dialogMessageInfoCrudContent=$("#<portlet:namespace/>dialog-message-info-content-crud");
		this.dialogMessageWarningCrud=$("#<portlet:namespace/>dialog-message-warning-crud");
		this.dialogMessageWarningCrudContent=$("#<portlet:namespace/>dialog-message-warning-content-crud");
		this.dialogMessageErrorCrud=$("#<portlet:namespace/>dialog-message-error-crud");
		this.dialogMessageErrorCrudContent=$("#<portlet:namespace/>dialog-message-error-content-crud");
		
		formato12D.initDialogsCRUD();
		
		formato12D.btnExcel.click(function() {formato12D.<portlet:namespace/>showUploadExcel();});
		formato12D.btnCargarFormatoExcel.click(function() {formato12D.<portlet:namespace/>cargarFormatoExcel();});
		formato12D.btnTxt.click(function() {formato12D.<portlet:namespace/>showUploadTxt();});
		formato12D.btnCargarFormatoTexto.click(function() {formato12D.<portlet:namespace/>cargarFormatoTxt();});
		
		formato12D.buildGridsImplementacion();
		formato12D.buildGridsMensual();
		//para modo view
		formato12D.buildGridsImplementacionView();
		formato12D.buildGridsMensualView();
		
		this.buildGridsObservacion();
		
		if(operacion=='CREATE'){
			
			formato12D.tipoOperacion.val(operacion);
			
			formato12D.flagCarga.val('0');//iniciamos la carga de excel o txt
			formato12D.divInformacion.hide();
			
			formato12D.codEmpresa.change(function(){formato12D.<portlet:namespace/>loadPeriodo('');});
			formato12D.periodoEnvio.change(function(){formato12D.<portlet:namespace/>loadPeriodo(this.value);});
			
			formato12D.<portlet:namespace/>loadPeriodo('');
			
			formato12D.botonAnadirFormato.click(function(){
				formato12D.blockUI();
				//--formato12D.formNuevo.attr('action',urlAnadirFormato+'&codigoEmpresa='+formato12D.codEmpresa.val()+'&periodoEnvio='+formato12D.periodoEnvio.val()+'&strip=0').removeAttr('enctype').submit();
				formato12D.formNuevo.attr('action',urlAnadirFormato+'&strip=0').removeAttr('enctype').submit();
			});
			formato12D.botonRegresarBusqueda.click(function(){
				formato12D.blockUI();
				location.href=urlRegresarBusqueda;
			});

			//mostramos el mensaje de informacion
			if( formato12D.msgTransaccion.val()=='OK' ){
				var addhtml='La carga del archivo se realizó satisfactoriamente';
				formato12D.dialogMessageContent.html(addhtml);
				formato12D.dialogMessage.dialog("open");
			}else if( formato12D.msgTransaccion.val()=='ERROR' ){
				formato12D.dialogError.dialog( "open" );
			}
			
		}if(operacion=='READ'){
			
			formato12D.tipoOperacion=operacion;
			formato12D.buscarDetalles();
			
			formato12D.divInformacion.show();
			formato12D.labelEstado.val(formato12D.estado.val());
			formato12D.labelGrupoInfo.val(formato12D.grupoInfo.val());
			
			formato12D.botonReportePdf.click(function() {formato12D.<portlet:namespace/>mostrarReportePdf();});
			formato12D.botonReporteExcel.click(function() {formato12D.<portlet:namespace/>mostrarReporteExcel();});
			
			formato12D.botonActaEnvio.click(function() {formato12D.<portlet:namespace/>mostrarReporteActaEnvio();});
			
			formato12D.botonRegresarBusqueda.click(function(){
				formato12D.blockUI();
				location.href=urlRegresarBusqueda;
			});
			
			formato12D.construirPeriodoEnvio(formato12D.anoPresentacion.val(), formato12D.mesPresentacion.val(), formato12D.etapa.val());
	
		}if(operacion=='UPDATE'){
			
			formato12D.tipoOperacion.val(operacion);
			
			formato12D.buscarDetalles();
			
			formato12D.flagCarga.val('1');//iniciamos la carga de excel o txt
			formato12D.divInformacion.show();
			formato12D.labelEstado.val(formato12D.estado.val());
			formato12D.labelGrupoInfo.val(formato12D.grupoInfo.val());

            formato12D.botonAnadirFormato.click(function(){
				formato12D.blockUI();
				//formato12D.formNuevo.attr('action',urlAnadirFormato+'&codigoEmpresa='+formato12D.codEmpresa.val()+'&periodoEnvio='+formato12D.periodoEnvio.val()+'&strip=0').removeAttr('enctype').submit();--
				formato12D.formNuevo.attr('action',urlAnadirFormato+'&strip=0').removeAttr('enctype').submit();
			});
			
			formato12D.botonRegresarBusqueda.click(function(){
				formato12D.blockUI();
				location.href=urlRegresarBusqueda;
			});
			
			formato12D.construirPeriodoEnvio(formato12D.anoPresentacion.val(), formato12D.mesPresentacion.val(), formato12D.etapa.val());
			
			//validacion y envio definitivo
			formato12D.botonValidacion.click(function() {formato12D.<portlet:namespace/>validacionFormato();});
			formato12D.botonEnvioDefinitivo.click(function() {formato12D.confirmarEnvioDefinitivo();});

			//mostramos el mensaje de informacion
			if( formato12D.msgTransaccion.val()=='OK' ){
				var addhtml='La carga del archivo se realizó satisfactoriamente';
				formato12D.dialogMessageContent.html(addhtml);
				formato12D.dialogMessage.dialog("open");
			}else if( formato12D.msgTransaccion.val()=='ERROR' ){
				formato12D.dialogError.dialog( "open" );
			}
			
		}
		
		
	},
	initCRUDDetalle : function(operacion,urlGuardarDetalle,urlRegresarDetalle,urlRegresarNuevo){
		this.formDetalle=$("#formato12DCBean");
		
		this.flagPeriodoDetalle=$("#flagPeriodoEjecucion");
		this.msgTransaccionDetalle=$("#msgTransaccionDetalle");
		
		//
		this.codDepa=$("select[name='codDepartamento']");
		this.codProv=$("select[name='codProvincia']");
		this.codDist=$("select[name='codDistrito']");
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
		this.localidadDetalle=$('#localidad');
		this.idZonaBenefDetalle=$('#zonaBenef');
		this.cuentaContableDetalle=$('#codCuentaContable');
		this.gastoDetalle=$('#gasto');
		this.tipoGastoDetalle=$('#tipoGasto');
		this.tipoDocDetalle=$('#tipoDocumento');
		this.rucEmpresaDetalle=$('#rucEmpresa');
		this.serieDocDetalle=$('#serieDocumento');
		this.nroDocDetalle=$('#nroDocumento');
		this.fechaAutoDetalle=$('#fechaAutorizacionString');
		this.nroDocAutoDetalle=$('#nroDocAutorizacion');
		this.cantidadDetalle=$('#cantidad');
		this.costoUnitarioDetalle=$('#costoUnitario');
		this.totalDetalle=$('#totalGeneral');
		
		this.codDepartamento=$('#codDepartamento');
		this.codProvincia=$('#codProvincia');
		this.codDistrito=$('#codDistrito');

		//
		this.codDepartamentoHidden=$('#codDepartamentoHidden');
		this.codProvinciaHidden=$('#codProvinciaHidden');
		this.codDistritoHidden=$('#codDistritoHidden');
		
		this.descDepa=$('#descDepartamento');
		this.descProv=$('#descProvincia');
		this.descDist=$('#descDistrito');

		this.anoEjecucionHiddenDetalle=$('#anoEjecucionHidden');
		this.mesEjecucionHiddenDetalle=$('#mesEjecucionHidden');
		this.etapaEjecucionHiddenDetalle=$('#etapaEjecucionHidden');
		
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
		
		formato12D.initDialogsCRUDDetalle();
		
		$('input.target[type=text]').on('change', function(){
			formato12D.calculoTotal();
		});
		
		<c:if test="${crud =='CREATE'}">
			//	
			formato12D.codDepa.change(function(){
				formato12D.listarProvincias(formato12D.codDepa.val());
			});
			formato12D.codProv.change(function(){
				formato12D.listarDistritos(formato12D.codProv.val());
			});
			//
			
			formato12D.botonGuardarDetalle.click(function(){
				if( formato12D.validarFormatoDetalle() ){
					//--formato12D.formDetalle.attr('action',urlGuardarDetalle+'&crud='+operacion+'&codEmpresa='+formato12D.codEmpresaDetalle.val()+'&anoEjecucionHidden='+formato12D.anoEjecucionDetalle.val()+'&mesEjecucionHidden='+formato12D.mesEjecucionDetalle.val()+'&etapaEjecucionHidden='+formato12D.etapaEjecucionDetalle.val()).submit();
					//guardamos los valores
					formato12D.anoEjecucionHiddenDetalle.val(formato12D.anoEjecucionDetalle.val());
					formato12D.mesEjecucionHiddenDetalle.val(formato12D.mesEjecucionDetalle.val());
					formato12D.etapaEjecucionHiddenDetalle.val(formato12D.etapaEjecucionDetalle.val());
					formato12D.formDetalle.attr('action',urlGuardarDetalle+'&crud='+operacion+'&codEmpresa='+formato12D.codEmpresaDetalle.val()).submit();
				}
			});
			
			botonRegresarDetalle.click(function(){
				formato12D.blockUI();
				//--location.href=urlRegresarDetalle+'&crud='+operacion+'&codEmpresa='+formato12D.codEmpresaDetalle.val()+'&periodoEnvio='+formato12D.periodoEnvioDetalle.val()+'&anioPresentacion='+formato12D.anoPresentacionDetalle.val()+'&mesPresentacion='+formato12D.mesPresentacionDetalle.val()+'&etapa='+formato12D.etapaDetalle.val()+'&tipo=1';
				location.href=urlRegresarNuevo+'&codEmpresa='+formato12D.codEmpresaDetalle.val();
			});
			
			formato12D.construirPeriodoEnvio(formato12D.anoPresentacionDetalle.val(), formato12D.mesPresentacionDetalle.val(), formato12D.etapaDetalle.val());
			formato12D.anoEjecucionDetalle.val(formato12D.anoPresentacionDetalle.val());
			formato12D.mesEjecucionDetalle.val(formato12D.mesPresentacionDetalle.val());
		
			formato12D.soloNumerosEnteros();
			formato12D.soloNumerosDecimales();
			
			formato12D.iniciamosValores();
			
			formato12D.mostrarPeriodoEjecucion();
			formato12D.estiloEdicionDetalle();
			
			//mostramos el mensaje de informacion
			if( formato12D.msgTransaccionDetalle.val()=='OK' ){
				var addhtml='El Detalle de los Gastos se guardó satisfactoriamente';
				formato12D.dialogMessageDetalleContent.html(addhtml);
				formato12D.dialogMessageDetalle.dialog("open");
			}else if( formato12D.msgTransaccionDetalle.val()=='ERROR' ){
				var addhtml='Se produjo un error al guardar el Detalle de Gastos';
				formato12D.dialogMessageErrorDetalleContent.html(addhtml);
				formato12D.dialogMessageErrorDetalle.dialog("open");
			}
			
		</c:if>
		
		<c:if test="${crud =='READ'}">
			botonRegresarDetalle.click(function(){
				formato12D.blockUI();
				location.href=urlRegresarDetalle+'&crud='+operacion+'&codEmpresa='+formato12D.codEmpresaDetalle.val()+'&periodoEnvio='+formato12D.periodoEnvioDetalle.val()+'&anioPresentacion='+formato12D.anoPresentacionDetalle.val()+'&mesPresentacion='+formato12D.mesPresentacionDetalle.val()+'&etapa='+formato12D.etapaDetalle.val()+'&tipo=0';
				//location.href=urlRegresarDetalle+'&crud='+operacion+'&'+formato12D.formDetalle.serialize()+'&tipo=0';
			});
			
			formato12D.construirPeriodoEnvio(formato12D.anoPresentacionDetalle.val(), formato12D.mesPresentacionDetalle.val(), formato12D.etapaDetalle.val());
			formato12D.anoEjecucionDetalle.val(formato12D.anoEjecucionHiddenDetalle.val());
			formato12D.mesEjecucionDetalle.val(formato12D.mesEjecucionHiddenDetalle.val());
			formato12D.etapaEjecucionDetalle.val(formato12D.etapaEjecucionHiddenDetalle.val());
			
			formato12D.codDepa.val(formato12D.codDepartamentoHidden.val());
			//formato12D.construirDepartamento(formato12D.codDepartamentoHidden.val(), formato12D.descDepa.val());
			formato12D.construirProvincia(formato12D.codProvinciaHidden.val(), formato12D.descProv.val());
			formato12D.construirDistrito(formato12D.codDistritoHidden.val(), formato12D.descDist.val());

			formato12D.formularioCompletarDecimales();
			
			formato12D.quitarEstiloEdicionPKDetalle();
			formato12D.quitarEstiloEdicionDetalle();
			
		</c:if>
		
		<c:if test="${crud =='READCREATEUPDATE'}">
			botonRegresarDetalle.click(function(){
				formato12D.blockUI();
				location.href=urlRegresarDetalle+'&crud='+operacion+'&codEmpresa='+formato12D.codEmpresaDetalle.val()+'&periodoEnvio='+formato12D.periodoEnvioDetalle.val()+'&anioPresentacion='+formato12D.anoPresentacionDetalle.val()+'&mesPresentacion='+formato12D.mesPresentacionDetalle.val()+'&etapa='+formato12D.etapaDetalle.val()+'&tipo=1';
				//location.href=urlRegresarDetalle+'&crud='+operacion+'&'+formato12D.formDetalle.serialize()+'&tipo=0';
			});
			formato12D.construirPeriodoEnvio(formato12D.anoPresentacionDetalle.val(), formato12D.mesPresentacionDetalle.val(), formato12D.etapaDetalle.val());
			formato12D.anoEjecucionDetalle.val(formato12D.anoEjecucionHiddenDetalle.val());
			formato12D.mesEjecucionDetalle.val(formato12D.mesEjecucionHiddenDetalle.val());
			formato12D.etapaEjecucionDetalle.val(formato12D.etapaEjecucionHiddenDetalle.val());
			
			formato12D.codDepa.val(formato12D.codDepartamentoHidden.val());
			//formato12D.construirDepartamento(formato12D.codDepartamentoHidden.val(), formato12D.descDepa.val());
			formato12D.construirProvincia(formato12D.codProvinciaHidden.val(), formato12D.descProv.val());
			formato12D.construirDistrito(formato12D.codDistritoHidden.val(), formato12D.descDist.val());
			
			formato12D.formularioCompletarDecimales();
			
			formato12D.quitarEstiloEdicionPKDetalle();
			formato12D.quitarEstiloEdicionDetalle();
			
		</c:if>
		
		<c:if test="${crud =='UPDATE'}">
		
			//
			formato12D.codDepa.change(function(){
				formato12D.listarProvincias(formato12D.codDepa.val());
			});
			formato12D.codProv.change(function(){
				formato12D.listarDistritos(formato12D.codProv.val());
			});
			//
		
			formato12D.botonGuardarDetalle.click(function(){
				if( formato12D.validarFormatoDetalle() ){
					//---formato12D.formDetalle.attr('action',urlGuardarDetalle+'&crud='+operacion+'&codEmpresa='+formato12D.codEmpresaDetalle.val()+'&anoEjecucionHidden='+formato12D.anoEjecucionDetalle.val()+'&mesEjecucionHidden='+formato12D.mesEjecucionDetalle.val()+'&etapaEjecucionHidden='+formato12D.etapaEjecucionDetalle.val()).submit();
					//guardamos los valores
					formato12D.anoEjecucionHiddenDetalle.val(formato12D.anoEjecucionDetalle.val());
					formato12D.mesEjecucionHiddenDetalle.val(formato12D.mesEjecucionDetalle.val());
					formato12D.etapaEjecucionHiddenDetalle.val(formato12D.etapaEjecucionDetalle.val());
					formato12D.formDetalle.attr('action',urlGuardarDetalle+'&crud='+operacion+'&codEmpresa='+formato12D.codEmpresaDetalle.val()).submit();
					//---formato12D.formDetalle.attr('action',urlGuardarDetalle+'&crud='+operacion).submit();
				}
			});
		
			botonRegresarDetalle.click(function(){
				formato12D.blockUI();
				location.href=urlRegresarDetalle+'&crud='+operacion+'&codEmpresa='+formato12D.codEmpresaDetalle.val()+'&periodoEnvio='+formato12D.periodoEnvioDetalle.val()+'&anioPresentacion='+formato12D.anoPresentacionDetalle.val()+'&mesPresentacion='+formato12D.mesPresentacionDetalle.val()+'&etapa='+formato12D.etapaDetalle.val()+'&tipo=1';
			});
			
			formato12D.construirPeriodoEnvio(formato12D.anoPresentacionDetalle.val(), formato12D.mesPresentacionDetalle.val(), formato12D.etapaDetalle.val());
			formato12D.anoEjecucionDetalle.val(formato12D.anoEjecucionHiddenDetalle.val());
			formato12D.mesEjecucionDetalle.val(formato12D.mesEjecucionHiddenDetalle.val());
			formato12D.etapaEjecucionDetalle.val(formato12D.etapaEjecucionHiddenDetalle.val());
			
			formato12D.codDepartamento.val(formato12D.codDepartamentoHidden.val());
			formato12D.listarProvinciasEdit(formato12D.codDepartamentoHidden.val(),formato12D.codProvinciaHidden.val(),formato12D.codDistritoHidden.val());
			//formato12D.listarDistritosEdit(formato12D.codProvinciaOrigenHidden.val(),formato12D.codDistritoOrigenHidden.val(),'0');
			
			formato12D.soloNumerosEnteros();
			formato12D.soloNumerosDecimales();
			
			formato12D.formularioCompletarDecimales();
			
			formato12D.mostrarPeriodoEjecucion();
			formato12D.estiloEdicionDetalle();
			
			//mostramos el mensaje de informacion
			if( formato12D.msgTransaccionDetalle.val()=='OK' ){
				var addhtml='El Detalle de los Gastos se guardó satisfactoriamente';
				formato12D.dialogMessageDetalleContent.html(addhtml);
				formato12D.dialogMessageDetalle.dialog("open");
			}else if( formato12D.msgTransaccionDetalle.val()=='ERROR' ){
				var addhtml='Se produjo un error al guardar el Detalle de Gastos';
				formato12D.dialogMessageErrorDetalleContent.html(addhtml);
				formato12D.dialogMessageErrorDetalle.dialog("open");
			}
			
		</c:if>
		
	},
	/**ESTRUCTURA DE TABLAS DE BUSQUEDA**/
	buildGridsBusqueda : function () {	
		formato12D.tablaResultados.jqGrid({
		   datatype: "local",
	       colNames: ['Dist. Eléct.','Año Decl.','Mes Decl.','Etapa','Grupo de Informaición','Estado','Ver','Editar','Eliminar','','',''],
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
				pager: formato12D.paginadoResultados,
			    viewrecords: true,
			   	caption: "Resultado(s) de la búsqueda",
			    sortorder: "asc",	   	    	   	   
	       gridComplete: function(){
	      		var ids = formato12D.tablaResultados.jqGrid('getDataIDs');
	      		for(var i=0;i < ids.length;i++){
	      			var cl = ids[i];
	      			var ret = formato12D.tablaResultados.jqGrid('getRowData',cl); 
					
	      			view = "<a href='"+formato12D.urlACrud+"&codEmpresa="+ret.codEmpresa+"&anioPresentacion="+ret.anoPresentacion+"&mesPresentacion="+ret.mesPresentacion+"&etapa="+ret.etapa+"&descGrupoInformacion="+ret.grupoInfo+"&descEstado="+ret.estado+"&tipo=0' ><img border='0' title='Ver' src='/net-theme/images/img-net/file.png'  align='center' /></a> ";
	      			edit = "<a href='#'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png'  align='center' onclick=\"formato12D.confirmarEditCabecera('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.etapa+"','"+ret.flagOperacion+"','"+ret.grupoInfo+"','"+ret.estado+"');\" /></a> ";
					elem = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"formato12D.confirmarEliminarCabecera('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.etapa+"','"+ret.flagOperacion+"');\" /></a> ";
	      			
	      			formato12D.tablaResultados.jqGrid('setRowData',ids[i],{view:view});
	      			formato12D.tablaResultados.jqGrid('setRowData',ids[i],{edit:edit});
	      			formato12D.tablaResultados.jqGrid('setRowData',ids[i],{elim:elem});
	      		}
	      }
	  	});
		formato12D.tablaResultados.jqGrid('navGrid',formato12D.paginadoResultados,{add:false,edit:false,del:false,search: false,refresh: false});	
		formato12D.tablaResultados.jqGrid('navButtonAdd',formato12D.paginadoResultados,{
		       caption:"Exportar a Excel",
		       buttonicon: "ui-icon-bookmark",
		       onClickButton : function () {
		           location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';
		       } 
		}); 
	},
	buildGridsImplementacion : function () {	
		formato12D.tablaImplementacion.jqGrid({
		   datatype: "local",
	       colNames: ['Año Ejec.','Mes Ejec.','Item','Cód. Ubigeo','Localidad','Zona Benef.','Cta. Contable','Gasto','Tipo gasto','Tipo Doc.','RUC','Serie Doc.','Nro. Doc.','Fecha autor.','Nro. doc. Autor.','Cantidad','Cto. Unitario','Ver','Editar','Eliminar','','','','','',''],
	       colModel: [
					{ name: 'anoEjecucion', index: 'anoEjecucion', width: 60},
					{ name: 'descMesEjecucion', index: 'descMesEjecucion', width: 80},
					{ name: 'item', index: 'item', width: 40},
					{ name: 'ubigeo', index: 'ubigeo', width: 120, align:'left'},
					{ name: 'localidad', index: 'localidad', width: 120, align:'left'},
					{ name: 'descZonaBenef', index: 'descZonaBenef', width: 120, align:'left'},
					{ name: 'cuentaContable', index: 'cuentaContable', width: 80, align:'left'},
					{ name: 'gasto', index: 'gasto', width: 120, align:'left'},
					{ name: 'tipoGasto', index: 'tipoGasto', width: 40, align:'left'},
					{ name: 'tipoDoc', index: 'tipoDoc', width: 40, align:'left'},
					{ name: 'ruc', index: 'ruc', width: 80, align:'right'},
					{ name: 'serieDoc', index: 'serieDoc', width: 80},
					{ name: 'nroDoc', index: 'nroDoc', width: 60},
					{ name: 'fechaAuto', index: 'fechaAuto', width: 60},
					{ name: 'nroDocAuto', index: 'nroDocAuto', width: 60},
					{ name: 'cantidad', index: 'cantidad', width: 60},
					{ name: 'costoUnitario', index: 'costoUnitario', width: 100},
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
				pager: formato12D.paginadoImplementacion,
			    viewrecords: true,
			   	caption: "Actividades Extraordinarias de Implementación",
			    sortorder: "asc",	   	    	   	   
	       gridComplete: function(){
	    	   AUI().use('liferay-portlet-url', function(A) {
	    		      var ids = formato12D.tablaImplementacion.jqGrid('getDataIDs');
	    		      
		  	      		for(var i=0;i < ids.length;i++){
		  	      			var cl = ids[i];
		  	      			var ret = formato12D.tablaImplementacion.jqGrid('getRowData',cl);   
		  	      			//VIEW
		  	      			var urlView=Liferay.PortletURL.createRenderURL();
		  	      			urlView.setParameter("action", "detalle");
		  	      			urlView.setParameter("crud", "READCREATEUPDATE");
		  	      			urlView.setParameter("msg", "DONE");
		  	      			urlView.setParameter("codigoEmpresaDetalle", ret.codEmpresa);
		  	      			urlView.setParameter("periodoEnvioDetalle", ret.anoPresentacion+completarCerosIzq(ret.mesPresentacion,2)+ret.etapa);
		  	      			urlView.setParameter("anoPresentacionDetalle", ret.anoPresentacion);
		  	      			urlView.setParameter("mesPresentacionDetalle", ret.mesPresentacion);
		  	      			urlView.setParameter("etapaDetalle", ret.etapa);
		  	    			urlView.setParameter("anoEjecucionDetalle", ret.anoEjecucion);
		  	    			urlView.setParameter("mesEjecucionDetalle", ret.mesEjecucion);
		  	    			urlView.setParameter("etapaEjecucionDetalle", ret.etapaEjecucion);
		  	    			urlView.setParameter("itemDetalle", ret.item);
		  	    			urlView.setPortletId(formato12D.portletID);
		  	      			//EDIT
		  	      			var urlEdit=Liferay.PortletURL.createRenderURL();
					  	    urlEdit.setParameter("action", "detalle");
					  	    urlEdit.setParameter("crud", "UPDATE");
					  	  	urlEdit.setParameter("msg", "DONE");
					  	  	urlEdit.setParameter("codigoEmpresaDetalle", ret.codEmpresa);
					  	  	urlEdit.setParameter("periodoEnvioDetalle", ret.anoPresentacion+completarCerosIzq(ret.mesPresentacion,2)+ret.etapa);
					  		urlEdit.setParameter("anoPresentacionDetalle", ret.anoPresentacion);
					  		urlEdit.setParameter("mesPresentacionDetalle", ret.mesPresentacion);
					  		urlEdit.setParameter("etapaDetalle", ret.etapa);
					  		urlEdit.setParameter("anoEjecucionDetalle", ret.anoEjecucion);
					  		urlEdit.setParameter("mesEjecucionDetalle", ret.mesEjecucion);
					  		urlEdit.setParameter("etapaEjecucionDetalle", ret.etapaEjecucion);
					  		urlEdit.setParameter("itemDetalle", ret.item);
					  	  	urlEdit.setPortletId(formato12D.portletID);
		  	      			
		  	      			view = "<a href='"+urlView+"'><img border='0' title='Ver' src='/net-theme/images/img-net/file.png'  align='center'  /></a> ";
		  	      			edit = "<a href='"+urlEdit+"'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png'  align='center'  /></a> ";
		  	      			
		  	      			elem = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"formato12D.confirmarEliminarDetalle('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.etapa+"','"+ret.anoEjecucion+"','"+ret.mesEjecucion+"','"+ret.etapaEjecucion+"','"+ret.item+"');\" /></a> ";              			
		  	      			formato12D.tablaImplementacion.jqGrid('setRowData',ids[i],{view:view});
		  	      			formato12D.tablaImplementacion.jqGrid('setRowData',ids[i],{edit:edit});
		  	      			formato12D.tablaImplementacion.jqGrid('setRowData',ids[i],{elim:elem});
		  	      		}
	    		   });	
	      }
	  	});
		formato12D.tablaImplementacion.jqGrid('navGrid',formato12D.paginadoImplementacion,{add:false,edit:false,del:false,search: false,refresh: false});	
		formato12D.tablaImplementacion.jqGrid('navButtonAdd',formato12D.paginadoImplementacion,{
		       caption:"Exportar a Excel",
		       buttonicon: "ui-icon-bookmark",
		       onClickButton : function () {
		           location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus"+"?etapaEjec=0")%>';
		       } 
		}); 
	},
	buildGridsMensual : function () {	
		formato12D.tablaMensual.jqGrid({
		   datatype: "local",
	       colNames: ['Año Ejec.','Mes Ejec.','Item','Cód. Ubigeo','Localidad','Zona Benef.','Cta. Contable','Gasto','Tipo gasto','Tipo Doc.','RUC','Serie Doc.','Nro. Doc.','Fecha autor.','Nro. doc. Autor.','Cantidad','Cto. Unitario','Ver','Editar','Eliminar','','','','','',''],
	       colModel: [
					{ name: 'anoEjecucion', index: 'anoEjecucion', width: 60},
					{ name: 'descMesEjecucion', index: 'descMesEjecucion', width: 80},
					{ name: 'item', index: 'item', width: 40},
					{ name: 'ubigeo', index: 'ubigeo', width: 120, align:'left'},
					{ name: 'localidad', index: 'localidad', width: 120, align:'left'},
					{ name: 'descZonaBenef', index: 'descZonaBenef', width: 120, align:'left'},
					{ name: 'cuentaContable', index: 'cuentaContable', width: 80, align:'left'},
					{ name: 'gasto', index: 'gasto', width: 120, align:'left'},
					{ name: 'tipoGasto', index: 'tipoGasto', width: 40, align:'left'},
					{ name: 'tipoDoc', index: 'tipoDoc', width: 40, align:'left'},
					{ name: 'ruc', index: 'ruc', width: 80, align:'right'},
					{ name: 'serieDoc', index: 'serieDoc', width: 80},
					{ name: 'nroDoc', index: 'nroDoc', width: 60},
					{ name: 'fechaAuto', index: 'fechaAuto', width: 60},
					{ name: 'nroDocAuto', index: 'nroDocAuto', width: 60},
					{ name: 'cantidad', index: 'cantidad', width: 60},
					{ name: 'costoUnitario', index: 'costoUnitario', width: 100},
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
				pager: formato12D.paginadoMensual,
			    viewrecords: true,
			   	caption: "Actividades Extraordinarias Operativas",
			    sortorder: "asc",	   	    	   	   
	       gridComplete: function(){
	    	   AUI().use('liferay-portlet-url', function(A) {
	    		      var ids = formato12D.tablaMensual.jqGrid('getDataIDs');
	    		      
		  	      		for(var i=0;i < ids.length;i++){
		  	      			var cl = ids[i];
		  	      			var ret = formato12D.tablaMensual.jqGrid('getRowData',cl);   
		  	      			//VIEW
		  	      			var urlView=Liferay.PortletURL.createRenderURL();
		  	      			urlView.setParameter("action", "detalle");
		  	      			urlView.setParameter("crud", "READCREATEUPDATE");
		  	      			urlView.setParameter("msg", "DONE");
			  	      		urlView.setParameter("codigoEmpresaDetalle", ret.codEmpresa);
			  	      		urlView.setParameter("periodoEnvioDetalle", ret.anoPresentacion+completarCerosIzq(ret.mesPresentacion,2)+ret.etapa);
		  	      			urlView.setParameter("anoPresentacionDetalle", ret.anoPresentacion);
		  	      			urlView.setParameter("mesPresentacionDetalle", ret.mesPresentacion);
		  	      			urlView.setParameter("etapaDetalle", ret.etapa);
		  	    			urlView.setParameter("anoEjecucionDetalle", ret.anoEjecucion);
		  	    			urlView.setParameter("mesEjecucionDetalle", ret.mesEjecucion);
		  	    			urlView.setParameter("etapaEjecucionDetalle", ret.etapaEjecucion);
		  	    			urlView.setParameter("itemDetalle", ret.item);
		  	      			urlView.setPortletId(formato12D.portletID);
		  	      			//EDIT
		  	      			var urlEdit=Liferay.PortletURL.createRenderURL();
					  	    urlEdit.setParameter("action", "detalle");
					  	    urlEdit.setParameter("crud", "UPDATE");
					  	  	urlEdit.setParameter("msg", "DONE");
					  	  	urlEdit.setParameter("codigoEmpresaDetalle", ret.codEmpresa);
					  	 	urlEdit.setParameter("periodoEnvioDetalle", ret.anoPresentacion+completarCerosIzq(ret.mesPresentacion,2)+ret.etapa);
					  		urlEdit.setParameter("anoPresentacionDetalle", ret.anoPresentacion);
					  		urlEdit.setParameter("mesPresentacionDetalle", ret.mesPresentacion);
					  		urlEdit.setParameter("etapaDetalle", ret.etapa);
					  		urlEdit.setParameter("anoEjecucionDetalle", ret.anoEjecucion);
					  		urlEdit.setParameter("mesEjecucionDetalle", ret.mesEjecucion);
					  		urlEdit.setParameter("etapaEjecucionDetalle", ret.etapaEjecucion);
					  		urlEdit.setParameter("itemDetalle", ret.item);
					  	  	urlEdit.setPortletId(formato12D.portletID);
		  	      			
		  	      			view = "<a href='"+urlView+"'><img border='0' title='Ver' src='/net-theme/images/img-net/file.png'  align='center'  /></a> ";
		  	      			edit = "<a href='"+urlEdit+"'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png'  align='center'  /></a> ";
		  	      			
		  	      			elem = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"formato12D.confirmarEliminarDetalle('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.etapa+"','"+ret.anoEjecucion+"','"+ret.mesEjecucion+"','"+ret.etapaEjecucion+"','"+ret.item+"');\" /></a> ";              			
		  	      			formato12D.tablaMensual.jqGrid('setRowData',ids[i],{view:view});
		  	      			formato12D.tablaMensual.jqGrid('setRowData',ids[i],{edit:edit});
		  	      			formato12D.tablaMensual.jqGrid('setRowData',ids[i],{elim:elem});
		  	      		}
	    		   });	
	      }
	  	});
		formato12D.tablaMensual.jqGrid('navGrid',formato12D.paginadoMensual,{add:false,edit:false,del:false,search: false,refresh: false});	
		formato12D.tablaMensual.jqGrid('navButtonAdd',formato12D.paginadoMensual,{
		       caption:"Exportar a Excel",
		       buttonicon: "ui-icon-bookmark",
		       onClickButton : function () {
		    	   location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus"+"?etapaEjec=1")%>';
		       } 
		}); 
	},
	//VIEW
	buildGridsImplementacionView : function () {	
		formato12D.tablaImplementacionView.jqGrid({
		   datatype: "local",
		   colNames: ['Año Ejec.','Mes Ejec.','Item','Cód. Ubigeo','Localidad','Zona Benef.','Cta. Contable','Gasto','Tipo gasto','Tipo Doc.','RUC','Serie Doc.','Nro. Doc.','Fecha autor.','Nro. doc. Autor.','Cantidad','Cto. Unitario','Ver','','','','','',''],
	       colModel: [
					{ name: 'anoEjecucion', index: 'anoEjecucion', width: 60},
					{ name: 'descMesEjecucion', index: 'descMesEjecucion', width: 80},
					{ name: 'item', index: 'item', width: 40},
					{ name: 'ubigeo', index: 'ubigeo', width: 120, align:'left'},
					{ name: 'localidad', index: 'localidad', width: 120, align:'left'},
					{ name: 'descZonaBenef', index: 'descZonaBenef', width: 120, align:'left'},
					{ name: 'cuentaContable', index: 'cuentaContable', width: 80, align:'left'},
					{ name: 'gasto', index: 'gasto', width: 120, align:'left'},
					{ name: 'tipoGasto', index: 'tipoGasto', width: 40, align:'left'},
					{ name: 'tipoDoc', index: 'tipoDoc', width: 40, align:'left'},
					{ name: 'ruc', index: 'ruc', width: 80, align:'right'},
					{ name: 'serieDoc', index: 'serieDoc', width: 80},
					{ name: 'nroDoc', index: 'nroDoc', width: 60},
					{ name: 'fechaAuto', index: 'fechaAuto', width: 60},
					{ name: 'nroDocAuto', index: 'nroDocAuto', width: 60},
					{ name: 'cantidad', index: 'cantidad', width: 60},
					{ name: 'costoUnitario', index: 'costoUnitario', width: 100},
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
				pager: formato12D.paginadoImplementacionView,
			    viewrecords: true,
			   	caption: "Actividades Extraordinarias de Implementación",
			    sortorder: "asc",	   	    	   	  
	       gridComplete: function(){
	    	   AUI().use('liferay-portlet-url', function(A) {
	    		      var ids = formato12D.tablaImplementacionView.jqGrid('getDataIDs');
	    		      
		  	      		for(var i=0;i < ids.length;i++){
		  	      			var cl = ids[i];
		  	      			var ret = formato12D.tablaImplementacionView.jqGrid('getRowData',cl);   
		  	      			var urlView=Liferay.PortletURL.createRenderURL();
		  	      		
		  	      			urlView.setParameter("action", "detalle");
		  	      			urlView.setParameter("crud", "READ");
		  	      			urlView.setParameter("msg", "DONE");
			  	      		urlView.setParameter("codigoEmpresaDetalle", ret.codEmpresa);
			  	      		urlView.setParameter("periodoEnvioDetalle", ret.anoPresentacion+completarCerosIzq(ret.mesPresentacion,2)+ret.etapa);
		  	      			urlView.setParameter("anoPresentacionDetalle", ret.anoPresentacion);
		  	      			urlView.setParameter("mesPresentacionDetalle", ret.mesPresentacion);
		  	      			urlView.setParameter("etapaDetalle", ret.etapa);
		  	    			urlView.setParameter("anoEjecucionDetalle", ret.anoEjecucion);
		  	    			urlView.setParameter("mesEjecucionDetalle", ret.mesEjecucion);
		  	    			urlView.setParameter("etapaEjecucionDetalle", ret.etapaEjecucion);
		  	    			urlView.setParameter("itemDetalle", ret.item);
		  	      			urlView.setPortletId(formato12D.portletID);
		  	      			
		  	      			
		  	      			view = "<a href='"+urlView+"'><img border='0' title='Ver' src='/net-theme/images/img-net/file.png'  align='center'  /></a> ";
		  	      			formato12D.tablaImplementacionView.jqGrid('setRowData',ids[i],{view:view});
		  	      		}
	    		   });	
	      }
	  	});
		formato12D.tablaImplementacionView.jqGrid('navGrid',formato12D.paginadoImplementacionView,{add:false,edit:false,del:false,search: false,refresh: false});	
		formato12D.tablaImplementacionView.jqGrid('navButtonAdd',formato12D.paginadoImplementacionView,{
		       caption:"Exportar a Excel",
		       buttonicon: "ui-icon-bookmark",
		       onClickButton : function () {
		    	   location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus"+"?etapaEjec=0")%>';
		       } 
		}); 
	},
	buildGridsMensualView : function () {	
		formato12D.tablaMensualView.jqGrid({
		   datatype: "local",
		   colNames: ['Año Ejec.','Mes Ejec.','Item','Cód. Ubigeo','Localidad','Zona Benef.','Cta. Contable','Gasto','Tipo gasto','Tipo Doc.','RUC','Serie Doc.','Nro. Doc.','Fecha autor.','Nro. doc. Autor.','Cantidad','Cto. Unitario','Ver','','','','','',''],
	       colModel: [
					{ name: 'anoEjecucion', index: 'anoEjecucion', width: 60},
					{ name: 'descMesEjecucion', index: 'descMesEjecucion', width: 80},
					{ name: 'item', index: 'item', width: 40},
					{ name: 'ubigeo', index: 'ubigeo', width: 120, align:'left'},
					{ name: 'localidad', index: 'localidad', width: 120, align:'left'},
					{ name: 'descZonaBenef', index: 'descZonaBenef', width: 120, align:'left'},
					{ name: 'cuentaContable', index: 'cuentaContable', width: 80, align:'left'},
					{ name: 'gasto', index: 'gasto', width: 120, align:'left'},
					{ name: 'tipoGasto', index: 'tipoGasto', width: 40, align:'left'},
					{ name: 'tipoDoc', index: 'tipoDoc', width: 40, align:'left'},
					{ name: 'ruc', index: 'ruc', width: 80, align:'right'},
					{ name: 'serieDoc', index: 'serieDoc', width: 80},
					{ name: 'nroDoc', index: 'nroDoc', width: 60},
					{ name: 'fechaAuto', index: 'fechaAuto', width: 60},
					{ name: 'nroDocAuto', index: 'nroDocAuto', width: 60},
					{ name: 'cantidad', index: 'cantidad', width: 60},
					{ name: 'costoUnitario', index: 'costoUnitario', width: 100},
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
				pager: formato12D.paginadoMensualView,
			    viewrecords: true,
			   	caption: "Actividades Extraordinarias Operativas",
			    sortorder: "asc",	   	    	   	   
	       gridComplete: function(){
	    	   AUI().use('liferay-portlet-url', function(A) {
	    		      var ids = formato12D.tablaMensualView.jqGrid('getDataIDs');
	    		      
		  	      		for(var i=0;i < ids.length;i++){
		  	      			var cl = ids[i];
		  	      			var ret = formato12D.tablaMensualView.jqGrid('getRowData',cl);   
		  	      			var urlView=Liferay.PortletURL.createRenderURL();
		  	      		
		  	      			urlView.setParameter("action", "detalle");
		  	      			urlView.setParameter("crud", "READ");
		  	      			urlView.setParameter("msg", "DONE");
			  	      		urlView.setParameter("codigoEmpresaDetalle", ret.codEmpresa);
			  	      		urlView.setParameter("periodoEnvioDetalle", ret.anoPresentacion+completarCerosIzq(ret.mesPresentacion,2)+ret.etapa);
		  	      			urlView.setParameter("anoPresentacionDetalle", ret.anoPresentacion);
		  	      			urlView.setParameter("mesPresentacionDetalle", ret.mesPresentacion);
		  	      			urlView.setParameter("etapaDetalle", ret.etapa);
		  	    			urlView.setParameter("anoEjecucionDetalle", ret.anoEjecucion);
		  	    			urlView.setParameter("mesEjecucionDetalle", ret.mesEjecucion);
		  	    			urlView.setParameter("etapaEjecucionDetalle", ret.etapaEjecucion);
		  	    			urlView.setParameter("itemDetalle", ret.item);
		  	      			urlView.setPortletId(formato12D.portletID);
		  	      			
		  	      			
		  	      			view = "<a href='"+urlView+"'><img border='0' title='Ver' src='/net-theme/images/img-net/file.png'  align='center'  /></a> ";
		  	      			formato12D.tablaMensualView.jqGrid('setRowData',ids[i],{view:view});
		  	      		}
	    		   });	
	      }
	  	});
		formato12D.tablaMensualView.jqGrid('navGrid',formato12D.paginadoMensualView,{add:false,edit:false,del:false,search: false,refresh: false});	
		formato12D.tablaMensualView.jqGrid('navButtonAdd',formato12D.paginadoMensualView,{
		       caption:"Exportar a Excel",
		       buttonicon: "ui-icon-bookmark",
		       onClickButton : function () {
		           location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus"+"?etapaEjec=1")%>';
		       } 
		}); 
	},
	/***BUSQUEDA Y CRUD***/
	buscarFormatos : function () {	

		if( formato12D.validarBusqueda() ){
			jQuery.ajax({			
				url: formato12D.urlBusqueda+'&'+formato12D.formBusqueda.serialize(),
				type: 'post',
				dataType: 'json',
				beforeSend:function(){
					formato12D.blockUI();
				},				
				success: function(gridData) {					
					formato12D.tablaResultados.clearGridData(true);
					formato12D.tablaResultados.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
					formato12D.tablaResultados[0].refreshIndex();
					formato12D.unblockUI();
				},error : function(){
					alert("Error de conexión.");
					formato12D.unblockUI();
				}
			});
		}

	},
	validarBusqueda : function(){
		if(formato12D.anioDesde.val().length != '' ) {		  
			  var numstr = trim(formato12D.anioDesde.val());
			  if (isNaN(numstr) || numstr.length<4 || parseFloat(numstr)<1900){
				  //alert('Ingrese un año desde válido');
				  formato12D.dialogMessageWarningContent.html('Debe ingresar un Año Declarado Desde válido');
				  formato12D.dialogMessageWarning.dialog("open");
				  formato12D.anioDesde.focus();
				  return false;
			  }
		  }
		  if(formato12D.anioHasta.val().length != '' ) {		  
			  var numstr = trim(formato12D.anioHasta.val());
			  if (isNaN(numstr) || numstr.length<4 || parseFloat(numstr)<1900){
				  //alert('Ingrese un año hasta válido');
				  formato12D.dialogMessageWarningContent.html('Debe ingresar un Año Declarado Hasta válido');
				  formato12D.dialogMessageWarning.dialog("open");
				  formato12D.anioHasta.focus();
				  return false;
			  }
		  }
		  if(formato12D.anioDesde.val().length != '' && formato12D.anioHasta.val().length != '' ) {
			  if( parseFloat(formato12D.anioDesde.val()) > parseFloat(formato12D.anioHasta.val()) ){
					//alert('El año desde no puede exceder al año hasta');
					formato12D.dialogMessageWarningContent.html('El Año Declarado Desde no puede exceder al Año Declarado Hasta');
					formato12D.dialogMessageWarning.dialog("open");
					return false;
			  }
		  }
		 return true; 
	},
	<portlet:namespace/>loadPeriodo : function(valPeriodo){
		jQuery.ajax({
				url: formato12D.urlCargaPeriodo+'&'+formato12D.formNuevo.serialize(),
				type: 'post',
				dataType: 'json',
				success: function(data) {		
					dwr.util.removeAllOptions("periodoEnvio");
					dwr.util.addOptions("periodoEnvio", data,"codigoItem","descripcionItem");
					if( valPeriodo.length!='' ){
						dwr.util.setValue("periodoEnvio", valPeriodo);
					}
					/*
					formato12D.<portlet:namespace/>loadCargaFlagPeriodo();
					//validar lima edelnor y luz del sur
					if(formato12D.cod_empresa_edelnor.val()==formato12D.f_empresa.val() || formato12D.cod_empresa_luz_sur.val()==formato12D.f_empresa.val()){
						formato12D.habilitarLima();										
					}else{
						formato12D.deshabilitarLima();
					}
					*/
					formato12D.unblockUI();
				},error : function(){
					alert("Error de conexión.");
					formato12D.unblockUI();
				}
		});
	},
	mostrarPeriodoEjecucion : function(){
		//if( formato12D.flagPeriodoDetalle.val()==true ){
		if( formato12D.flagPeriodoDetalle.val()=='false' ){
			formato12D.estiloEdicionPKDetalle();
		}else{  
			formato12D.quitarEstiloEdicionPKDetalle();
		}
	},
	buscarDetalles : function () {	
        return jQuery.ajax({			
					url: formato12D.urlBusquedaDetalle+'&'+formato12D.formNuevo.serialize(),
					type: 'post',
					dataType: 'json',
					beforeSend:function(){
						$.blockUI({ message: formato12D.mensajeObteniendoDatos });
						//formato12D.blockUI();
					},				
					success: function(gridData) {					
						<c:if test="${crud =='READ'}">
							//
							formato12D.tablaImplementacionView.clearGridData(true);
							formato12D.tablaImplementacionView.jqGrid('setGridParam', {data: gridData.implementacion}).trigger('reloadGrid');
							formato12D.tablaImplementacionView[0].refreshIndex();
							//
							formato12D.tablaMensualView.clearGridData(true);
							formato12D.tablaMensualView.jqGrid('setGridParam', {data: gridData.operativa}).trigger('reloadGrid');
							formato12D.tablaMensualView[0].refreshIndex();
							//
						</c:if>
						<c:if test="${crud =='UPDATE' || crud =='CREATE' }">
							//	
							formato12D.tablaImplementacion.clearGridData(true);
							formato12D.tablaImplementacion.jqGrid('setGridParam', {data: gridData.implementacion}).trigger('reloadGrid');
							formato12D.tablaImplementacion[0].refreshIndex();
							//
							formato12D.tablaMensual.clearGridData(true);
							formato12D.tablaMensual.jqGrid('setGridParam', {data: gridData.operativa}).trigger('reloadGrid');
							formato12D.tablaMensual[0].refreshIndex();
							//
						</c:if>
						formato12D.unblockUI();
					},error : function(){
						alert("Error de conexión.");
						formato12D.unblockUI();
					}
			});
	},
	listarProvincias : function (codDepartamento) {	
		jQuery.ajax({			
					url: formato12D.urlProvincias,
					type: 'post',
					dataType: 'json',
					data:{
						codDepartamento:codDepartamento
					},
					beforeSend:function(){
						formato12D.blockUI();
					},				
					success: function(data) {	
			
						dwr.util.removeAllOptions("codProvincia");
						dwr.util.addOptions("codProvincia", data,"codigoItem","descripcionItem");
						formato12D.limpiarDistritos();
						
						formato12D.unblockUI();
					},error : function(){
						alert("Error de conexión.");
						formato12D.unblockUI();
					}
			});
	},
	listarProvinciasEdit : function (codDepartamento,provSelected,distSelected) {	
		jQuery.ajax({			
					url: formato12D.urlProvincias,
					type: 'post',
					dataType: 'json',
					data:{
						codDepartamento:codDepartamento
					},
					beforeSend:function(){
						formato12D.blockUI();
					},				
					success: function(data) {	

						dwr.util.removeAllOptions("codProvincia");
						dwr.util.addOptions("codProvincia", data,"codigoItem","descripcionItem");
						dwr.util.setValue("codProvincia", provSelected);
						formato12D.limpiarDistritos();
						
						formato12D.listarDistritosEdit(provSelected, distSelected);
						
						formato12D.unblockUI();
					},error : function(){
						alert("Error de conexión.");
						formato12D.unblockUI();
					}
			});
	},
	listarDistritos : function (codProvincia) {	
		jQuery.ajax({			
					url: formato12D.urlDistritos,
					type: 'post',
					dataType: 'json',
					data:{
						codProvincia:codProvincia
					},
					beforeSend:function(){
						formato12D.blockUI();
					},				
					success: function(data) {			

						dwr.util.removeAllOptions("codDistrito");
						dwr.util.addOptions("codDistrito", data,"codigoItem","descripcionItem");
						
						formato12D.unblockUI();
					},error : function(){
						alert("Error de conexión.");
						formato12D.unblockUI();
					}
			});
	},
	listarDistritosEdit : function (codProvincia,distSelected) {	
		jQuery.ajax({			
					url: formato12D.urlDistritos,
					type: 'post',
					dataType: 'json',
					data:{
						codProvincia:codProvincia
					},
					beforeSend:function(){
						formato12D.blockUI();
					},				
					success: function(data) {			

						dwr.util.removeAllOptions("codDistrito");
						dwr.util.addOptions("codDistrito", data,"codigoItem","descripcionItem");
						dwr.util.setValue("codDistrito", distSelected);
						
						formato12D.unblockUI();
					},error : function(){
						alert("Error de conexión.");
						formato12D.unblockUI();
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

	construirPeriodoEnvio : function(anoPresentacion,mesPresentacion,etapa){
		dwr.util.removeAllOptions("periodoEnvio");
		var codigo=''+anoPresentacion+completarCerosIzq(mesPresentacion,2)+etapa;
		var descripcion=formato12D.mostrarDescripcionPeriodo(anoPresentacion,mesPresentacion,etapa);
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
	construirDepartamento : function(codDepartamento,descDepartamento){
		dwr.util.removeAllOptions("codDepartamento");
		//var codigo=codDepartamento;
		//var descripcion=descDepartamento;
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
	validarArchivoCarga : function() {		
		if(formato12D.codEmpresa.val().length == '' ) { 	
			//alert('Seleccione una Distribuidora Eléctrica para proceder con la carga de archivo'); 
			formato12D.dialogMessageWarningCrudContent.html('Seleccione una Distribuidora Eléctrica para proceder con la carga del archivo');
			formato12D.dialogMessageWarningCrud.dialog("open");
			formato12D.codEmpresa.focus();
			return false; 
		}
		if(formato12D.periodoEnvio==null || formato12D.periodoEnvio.val().length == '' ) {		  
			//alert('Debe seleccionar el periodo a declarar');
			formato12D.dialogMessageWarningCrudContent.html('Debe seleccionar el Periodo a Declarar para proceder con la carga del archivo');
			formato12D.dialogMessageWarningCrud.dialog("open");
			formato12D.periodoEnvio.focus();
			return false; 
		}
		return true; 
	},
	//
	<portlet:namespace/>showUploadExcel : function(){
		
		if (formato12D.validarArchivoCarga()){
			if( formato12D.flagCarga.val()=='0' ){//proviene de archivos nuevos
				formato12D.flagCarga.val('2');//para cargar archivos excel
			}else if( formato12D.flagCarga.val()=='1' ){//proviene de archivos modificados
				formato12D.flagCarga.val('3');//para cargar archivos excel
			}
			
			formato12D.divOverlay.show();
			formato12D.dialogCargaExcel.show();
			formato12D.dialogCargaExcel.show();
			formato12D.dialogCargaExcel.css({ 
		        'left': ($(window).width() / 2 - formato12D.dialogCargaExcel.width() / 2) + 'px', 
		        'top': ($(window).height()  - formato12D.dialogCargaExcel.height() ) + 'px'
		    });
			formato12D.iniciarMensajeExcel();
		}

	},
	
	closeDialogCargaExcel : function(){
		formato12D.iniciarMensajeExcel();
		formato12D.dialogCargaExcel.hide();
		formato12D.divOverlay.hide();   
	},
	
	<portlet:namespace/>cargarFormatoExcel : function(){
		var nameFile=$("#archivoExcel").val();
		var isSubmit=true;
		
		$("#msjFileExcel").html("");
		if(typeof (nameFile) == "undefined" || nameFile.length==0){
			isSubmit=false;
			$("#msjFileExcel").html("Debe seleccionar un archivo");
		}else{
			var extension=nameFile.substr(nameFile.indexOf(".")+1,nameFile.length);				
			if(extension == 'xls' || extension == 'xlsx'){
				isSubmit=true;
				$("#msjFileExcel").html("");
			}else{
				isSubmit=false;
				$("#msjFileExcel").html("Archivo inválido");
			}
			//isSubmit=true;
			//$("#msjFileExcel").html("");
		}
		if(isSubmit){
			formato12D.formNuevo.submit();
		}
		//formato12D.formNuevo.submit();
		//formato12D.formNuevo.attr().submit();
	},
	
	<portlet:namespace/>showUploadTxt : function(){
		
		if (formato12D.validarArchivoCarga()){
			if( formato12D.flagCarga.val()=='0' ){//proviene de un archivo nuevo
				formato12D.flagCarga.val('4');//para cargar archivos texto
			}else if( formato12D.flagCarga.val()=='1' ){//proviene de un archivo modificado
				formato12D.flagCarga.val('5');//para archivos texto
			}
			
			formato12D.divOverlay.show();
			formato12D.dialogCargaTexto.show();
			formato12D.dialogCargaTexto.css({ 
		        'left': ($(window).width() / 2 - formato12D.dialogCargaTexto.width() / 2) + 'px', 
		        'top': ($(window).height()  - formato12D.dialogCargaTexto.height() ) + 'px'
		    });
			formato12D.iniciarMensajeTxt();
		}
	
	},
	
	closeDialogCargaTxt : function(){
		formato12D.iniciarMensajeTxt();
		formato12D.dialogCargaTexto.hide();
		formato12D.divOverlay.hide();   
	},

	<portlet:namespace/>cargarFormatoTxt : function(){
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
				$("#msjFileTxt").html("Archivo inválido");
			}
			//isSubmit=true;
			//$("#msjFileTxt").html("");
		}
		if(isSubmit){
			formato12D.formNuevo.submit();
		}
		//formato12D.formNuevo.submit();
		//formato12D.formNuevo.attr().submit();
	},
	
	iniciarMensajeExcel : function(){
		$("#msjFileExcel").html("");
	},
	iniciarMensajeTxt : function(){
		$("#msjFileTxt").html("");
	},
	
	emptySelectObject: function(){
		var jsonVacio='[{"descripcionItem":"--Seleccione--","codigoItem":""}]';
		return eval("(" + jsonVacio + ")");
	},
	//
	limpiarProvincias:function(){
		dwr.util.removeAllOptions("codProvincia");
		dwr.util.addOptions("codProvincia", formato12D.emptySelectObject(),"codigoItem","descripcionItem");
	},
	limpiarDistritos:function(){
		dwr.util.removeAllOptions("codDistrito");
		dwr.util.addOptions("codDistrito", formato12D.emptySelectObject(),"codigoItem","descripcionItem");
	},
	
	//calculo total
	calculoTotal : function(){
		var cantidad;
		var costoUnitario;
		var total;
		cantidad=formato12D.cantidadDetalle.val();
		costoUnitario=formato12D.costoUnitarioDetalle.val();
		console.debug(''+cantidad+','+costoUnitario);
		total=parseFloat(cantidad)*parseFloat(costoUnitario);
		total=redondeo(total,2);
		formato12D.totalDetalle.val(parseFloat(total));
		
		formato12D.formularioCompletarDecimales();
	},
	//MOSTRAR REPORTE
	<portlet:namespace/>mostrarReportePdf : function(){
		var form = formato12D.formNuevo;
		form.removeAttr('enctype');
		
		jQuery.ajax({
			url : formato12D.urlReporte+'&'+form.serialize(),
			type : 'post',
			dataType : 'json',
			data : {
				<portlet:namespace />nombreReporte: 'formato12D',
				<portlet:namespace />nombreArchivo: 'formato12D',
				<portlet:namespace />tipoArchivo: '0'//PDF
			},
			success : function(gridData) {
				formato12D.verReporte();
			},error : function(){
				alert("Error de conexión.");
				formato12D.unblockUI();
			}
		});
	},
	<portlet:namespace/>mostrarReporteExcel : function(){
		var form = formato12D.formNuevo;
		form.removeAttr('enctype');
		jQuery.ajax({
			url : formato12D.urlReporte+'&'+form.serialize(),
			type : 'post',
			dataType : 'json',
			data : {
				<portlet:namespace />nombreReporte: 'formato12D',
				<portlet:namespace />nombreArchivo: 'formato12D',
				<portlet:namespace />tipoArchivo: '1'//XLS
			},
			success : function(gridData) {
				formato12D.verReporte();
			},error : function(){
				alert("Error de conexión.");
				formato12D.unblockUI();
			}
		});
	},
	verReporte : function(){
		window.open('<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ViewReport")%>','_newtab');
	},
	//procesos de validacion y envio definitivo
	<portlet:namespace/>validacionFormato : function(){
		var form = formato12D.formNuevo;
		form.removeAttr('enctype');
		jQuery.ajax({
			url: formato12D.urlValidacion+'&'+form.serialize(),
			type : 'post',
			dataType : 'json',
			/*data : {
				<portlet:namespace />codEmpresa: formato12D.f_empresa.val(),
				<portlet:namespace />periodoEnvio: formato12D.f_periodoEnvio.val(),
				<portlet:namespace />anoInicioVigencia: $('#anioInicioVigencia').val(),
				<portlet:namespace />anoFinVigencia: $('#anioFinVigencia').val()
			},*/
			success : function(data) {
				if( data!=null ){
					formato12D.dialogObservacion.dialog("open");
					formato12D.tablaObservacion.clearGridData(true);
					formato12D.tablaObservacion.jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
					formato12D.tablaObservacion[0].refreshIndex();
					formato12D.unblockUI();
				}

			},error : function(){
				alert("Error de conexión.");
				formato12D.unblockUI();
			}
		});
	},
	<portlet:namespace/>mostrarReporteValidacion : function(){
		var form = formato12D.formNuevo;
		form.removeAttr('enctype');
		jQuery.ajax({
			url: formato12D.urlReporteValidacion+'&'+form.serialize(),
			type : 'post',
			dataType : 'json',
			data : {
				//<portlet:namespace />periodoEnvio: formato12D.f_periodoEnvio.val(),
				<portlet:namespace />nombreReporte: 'validacion12',
				<portlet:namespace />nombreArchivo: 'validacion12',
				<portlet:namespace />tipoArchivo: '0'//PDF
				//anioInicioVigencia:formato12D.txtInicioVig.val(),
				//anioFinVigencia:formato12D.txtFinVig.val() //PDF
			},
			success : function(gridData) {
				formato12D.verReporte();
			},error : function(){
				alert("Error de conexión.");
				formato12D.unblockUI();
			}
		});
	},
	confirmarEnvioDefinitivo : function(){	
		var addhtml='¿Está seguro que desea realizar el Envío Definitivo para el Formato 12D?';
		formato12D.dialogConfirmEnvioContent.html(addhtml);
		formato12D.dialogConfirmEnvio.dialog("open");
	},
	<portlet:namespace/>envioDefinitivo : function(){
		var form = formato12D.formNuevo;
		form.removeAttr('enctype');
		jQuery.ajax({
			url: formato12D.urlEnvioDefinitivo+'&'+form.serialize(),
			type : 'post',
			dataType : 'json',
			data : {
				//<portlet:namespace />codEmpresa: formato12D.f_empresa.val(),
				//<portlet:namespace />periodoEnvio: formato12D.f_periodoEnvio.val(),
				//<portlet:namespace />anoInicioVigencia: $('#anioInicioVigencia').val(),
				//<portlet:namespace />anoFinVigencia: $('#anioFinVigencia').val(),
				<portlet:namespace />nombreReporte: 'formato12D',
				<portlet:namespace />nombreArchivo: 'formato12D',
				<portlet:namespace />tipoArchivo: '0'//PDF
			},
			success : function(data) {
				if(data.resultado == "OK"){
					var addhtml='El Envío Definitivo se realizó satisfactoriamente a los siguientes correos electrónicos: '+data.correo;					
					formato12D.dialogMessageReportContent.html(addhtml);
					formato12D.dialogMessageReport.dialog("open");
					formato12D.unblockUI();			
				}else if(data.resultado == "EMAIL"){						
					var addhtmEmail = data.correo;					
					formato12D.dialogMessageErrorCrudContent.html(addhtmEmail);
					formato12D.dialogMessageErrorCrud.dialog("open");
					formato12D.unblockUI();
				}else{								
					var addhtmError='Error al realizar el Envío Definitivo del Formato 12D.';					
					formato12D.dialogMessageErrorCrudContent.html(addhtmError);
					formato12D.dialogMessageErrorCrud.dialog("open");
					formato12D.unblockUI();
				}					
			},error : function(){
				alert("Error de conexión.");
				formato12D.unblockUI();
			}
		});
	},
	buildGridsObservacion : function () {	
		formato12D.tablaObservacion.jqGrid({
		   datatype: "local",
	       colNames: ['Etapa ejecución','Nro. item etapa','Código','Descripción'],
	       colModel: [
						{ name: 'descEtapaEjecucion', index: 'descEtapaEjecucion', width: 100 ,align: 'left'},
						{ name: 'nroItemEtapa', index: 'nroItemEtapa', width: 70 ,align: 'left'},
						{ name: 'codigo', index: 'codigo', width: 50 ,align: 'center'},
		                { name: 'descripcion', index: 'descripcion', width: 420 ,align: 'left'}               
			   	    ],
		   	 multiselect: false,
				rowNum:10,
			   	rowList:[10,20,50],
				height: 'auto',
			   	autowidth: true,
				rownumbers: true,
				pager: formato12D.paginadoObservacion,
			    viewrecords: true,
			   	sortorder: "asc"
	  	});
		formato12D.tablaObservacion.jqGrid('navGrid',formato12D.paginadoObservacion,{add:false,edit:false,del:false,search: false,refresh: false});	
		formato12D.tablaObservacion.jqGrid('navButtonAdd',formato12D.paginadoObservacion,{
		       caption:"Exportar a Excel",
		       buttonicon: "ui-icon-bookmark",
		       onClickButton : function () {
		           location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';
		       } 
		}); 
		formato12D.tablaObservacion.jqGrid('navButtonAdd',formato12D.paginadoObservacion,{
		       caption:"Exportar a Pdf",
		       buttonicon: "ui-icon-bookmark",
		       onClickButton : function () {
		    	   formato12D.<portlet:namespace/>mostrarReporteValidacion();
		       } 
		});
	},
	<portlet:namespace/>mostrarReporteEnvioDefinitivo : function(){
		var form = formato12D.formNuevo;
		form.removeAttr('enctype');
		jQuery.ajax({
			url: formato12D.urlReporteEnvioDefinitivo+'&'+form.serialize(),
			type : 'post',
			dataType : 'json',
			data : {
				<portlet:namespace />tipoArchivo: '2'//PDF+concatenado
			},
			success : function(gridData) {
				formato12D.verReporte();
			},error : function(){
				alert("Error de conexión.");
				formato12D.unblockUI();
			}
		});
	},
	<portlet:namespace/>mostrarReporteActaEnvio : function(){
		if(formato12D.labelEstado.val()=='Enviado'){
			var form = formato12D.formNuevo;
			form.removeAttr('enctype');
			jQuery.ajax({
				url: formato12D.urlReporteActaEnvio+'&'+form.serialize(),
				type : 'post',
				dataType : 'json',
				data : {
					/*<portlet:namespace />codEmpresa: formato12D.f_empresa.val(),
					<portlet:namespace />periodoEnvio: formato12D.f_periodoEnvio.val(),
					<portlet:namespace />anoInicioVigencia: $('#anioInicioVigencia').val(),
					<portlet:namespace />anoFinVigencia: $('#anioFinVigencia').val(),*/
					<portlet:namespace />tipoArchivo: '0'//PDF
				},
				success : function(gridData) {
					formato12D.verReporte();
				},error : function(){
					alert("Error de conexión.");
					formato12D.unblockUI();
				}
			});
		}else{
			//alert("Primero debe realizar el envío definitivo");
			formato12D.dialogMessageInfoCrudContent.html('Primero debe realizar el Envío Definitivo del Formato 12D');
			formato12D.dialogMessageInfoCrud.dialog("open");
		}
	},
	//inicializar dialogs
	initDialogs : function(){	
		formato12D.dialogConfirm.dialog({
			modal: true,
			height: 200,
			width: 450,	
			autoOpen: false,
			buttons: {
				"Si": function() {
					formato12D.eliminarFormatoCabecera(cod_Empresa_cabecera,ano_Presentacion_cabecera,mes_Presentacion_cabecera,cod_Etapa_cabecera);
					$(this).dialog("close");
				},
				"No": function() {				
					$(this).dialog("close");
				}
			}
		});
		//addd
		formato12D.dialogMessageInfo.dialog({
			modal: true,
			autoOpen: false,
			width: 450,
			buttons: {
				Ok: function() {
					$( this ).dialog("close");
				}
			}
		});
		formato12D.dialogMessageWarning.dialog({
			modal: true,
			autoOpen: false,
			width: 450,
			buttons: {
				Ok: function() {
					$( this ).dialog("close");
				}
			}
		});
	},
	initDialogsCRUD : function(){	
		formato12D.dialogMessageReport.dialog({
			modal: true,
			autoOpen: false,
			width: 450,
			buttons: {
				'Ver Acta': function() {
					formato12D.<portlet:namespace/>mostrarReporteEnvioDefinitivo();
					$(this).dialog("close");
					formato12D.botonRegresarBusqueda.trigger('click');
				},
				Ok: function() {
					$(this).dialog("close");
					formato12D.botonRegresarBusqueda.trigger('click');
				}
			},
			close: function(event,ui){
				formato12D.botonRegresarBusqueda.trigger('click');
	       	}
		});
		formato12D.dialogConfirmEnvio.dialog({
			modal: true,
			height: 200,
			width: 450,	
			autoOpen: false,
			buttons: {
				"Si": function() {
					formato12D.<portlet:namespace/>envioDefinitivo();
					$(this).dialog("close");
				},
				"No": function() {				
					$(this).dialog("close");
				}
			}
		});
		formato12D.dialogConfirmDetalle.dialog({
			modal: true,
			height: 200,
			width: 450,		
			autoOpen: false,
			buttons: {
				"Si": function() {
					formato12D.eliminarFormatoDetalle(cod_Empresa,ano_Presentacion,mes_Presentacion,cod_Etapa,ano_Ejecucion,mes_Ejecucion,etapa_Ejecucion,item_Etapa);
					$(this).dialog("close");
				},
				"No": function() {				
					$(this).dialog("close");
				}
			}
		});
		formato12D.dialogObservacion.dialog({
			modal: true,
			width: 750,
			autoOpen: false,
			buttons: {
				Cerrar: function() {
					$(this).dialog("close");
				}
			}
		});
		formato12D.dialogError.dialog({
			modal: true,
			width: 750,
			autoOpen: false,
			buttons: {
				Cerrar: function() {
					$(this).dialog("close");
				}
			}
		});
		formato12D.dialogMessage.dialog({
			modal: true,
			autoOpen: false,
			width: 450,
			buttons: {
				Ok: function() {
					$( this ).dialog("close");
				}
			}
		});
		//addd
		formato12D.dialogMessageInfoCrud.dialog({
			modal: true,
			autoOpen: false,
			width: 450,
			buttons: {
				Ok: function() {
					$( this ).dialog("close");
				}
			}
		});
		formato12D.dialogMessageWarningCrud.dialog({
			modal: true,
			autoOpen: false,
			width: 450,
			buttons: {
				Ok: function() {
					$( this ).dialog("close");
				}
			}
		});
		formato12D.dialogMessageErrorCrud.dialog({
			modal: true,
			autoOpen: false,
			width: 450,
			buttons: {
				Ok: function() {
					$( this ).dialog("close");
				}
			}
		});
	},
	initDialogsCRUDDetalle : function(){	
		formato12D.dialogMessageDetalle.dialog({
			modal: true,
			autoOpen: false,
			width: 450,
			buttons: {
				Ok: function() {
					$( this ).dialog("close");
				}
			}
		});
		//addd
		formato12D.dialogMessageWarningDetalle.dialog({
			modal: true,
			autoOpen: false,
			width: 450,
			buttons: {
				Ok: function() {
					$( this ).dialog("close");
				}
			}
		});
		formato12D.dialogMessageErrorDetalle.dialog({
			modal: true,
			autoOpen: false,
			width: 450,
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
			if( etapa=='RECONOCIDO'  &&  admin=='false' ){
				process = false;
			}
			if(process){
				var addhtml='¿Está seguro que desea eliminar el registro seleccionado?';
				formato12D.dialogConfirmContent.html(addhtml);
				formato12D.dialogConfirm.dialog("open");
				cod_Empresa_cabecera=codEmpresa;
				ano_Presentacion_cabecera=anoPresentacion;
				mes_Presentacion_cabecera=mesPresentacion;
				cod_Etapa_cabecera=etapa;
			}else{
				//alert(" No tiene autorización para realizar esta operación");
				formato12D.dialogMessageInfoContent.html('No tiene autorización para realizar esta acción');
				formato12D.dialogMessageInfo.dialog("open");
			}
		}else if(flagOperacion=='CERRADO'){
			//alert(" Está fuera de plazo");		
			formato12D.dialogMessageInfoContent.html('El plazo para realizar esta acción se encuentra cerrado');
			formato12D.dialogMessageInfo.dialog("open");
		}else{
			//alert("El formato ya fue enviado a OSINERGMIN-GART");	
			formato12D.dialogMessageInfoContent.html('El formato ya fue enviado a OSINERGMIN-GART');
			formato12D.dialogMessageInfo.dialog("open");
		}
	},
	confirmarEliminarDetalle : function(codEmpresa,anoPresentacion,mesPresentacion,etapa,anoEjecucion,mesEjecucion,etapaEjecucion,item){
		var addhtml='¿Está seguro que desea eliminar el registro seleccionado?';
		formato12D.dialogConfirmDetalleContent.html(addhtml);
		formato12D.dialogConfirmDetalle.dialog("open");
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
		//$.blockUI({ message: formato12D.mensajeEliminando });
		var form = formato12D.formNuevo;
		form.removeAttr('enctype');
		jQuery.ajax({
			url: formato12D.urlDeleteDetalle+'&'+form.serialize(),
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
					//formato12D.dialogMessageContent.html(addhtml2);
					//formato12D.dialogMessage.dialog("open");
					formato12D.buscarDetalles();
					formato12D.unblockUI();
				}
				else{
					//alert("Error al eliminar el registro");
					formato12D.dialogMessageErrorCrudContent.html('Error al eliminar el registro');
					formato12D.dialogMessageErrorCrud.dialog("open");
					formato12D.unblockUI();
				}
			},error : function(){
				alert("Error de conexión.");
				formato12D.unblockUI();
			}
		});
	},
	eliminarFormatoCabecera : function(cod_Empresa_cabecera,ano_Presentacion_cabecera,mes_Presentacion_cabecera,cod_Etapa_cabecera){			
		//$.blockUI({ message: formato12D.mensajeEliminando });
		//alert(cod_Empresa_cabecera+','+ano_Presentacion_cabecera+','+mes_Presentacion_cabecera+','+cod_Etapa_cabecera);
		jQuery.ajax({
			url: formato12D.urlDelete+'&'+formato12D.formBusqueda.serialize(),
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
					//formato12D.dialogMessageContent.html(addhtml2);
					//formato12D.dialogMessage.dialog("open");
					formato12D.buscarFormatos();
					formato12D.unblockUI();
				}
				else{
					//alert("Error al eliminar el registro");
					formato12D.dialogMessageErrorContent.html('Error al eliminar el registro');
					formato12D.dialogMessageError.dialog("open");
					formato12D.unblockUI();
				}
			},error : function(){
				alert("Error de conexión.");
				formato12D.unblockUI();
			}
		});
	},
	confirmarEditCabecera : function(codEmpresa,anoPresentacion,mesPresentacion,etapa,flagOperacion,grupoInfo,estado){
		var admin = '${esAdministrador}';
		if(flagOperacion=='ABIERTO'){
			var process=true;
			if( etapa=='RECONOCIDO'  &&  admin=='false' ){
				process = false;
			}
			if(process){
				location.href=formato12D.urlACrud+'&codEmpresa='+codEmpresa+'&anioPresentacion='+anoPresentacion+'&mesPresentacion='+mesPresentacion+'&etapa='+etapa+'&descGrupoInformacion='+grupoInfo+'&descEstado='+estado+'&tipo=1';
			}else{
				//alert(" No tiene autorización para realizar esta operación");
				formato12D.dialogMessageErrorCrudContent.html('No tiene autorización para realizar esta operación');
				formato12D.dialogMessageErrorCrud.dialog("open");
			}
		}else if(flagOperacion=='CERRADO'){
			//alert(" Está fuera de plazo");	
			formato12D.dialogMessageInfoContent.html('Está fuera de plazo');
			formato12D.dialogMessageInfo.dialog("open");
		}else{
			//alert("El formato ya fue enviado a OSINERGMIN-GART");
			formato12D.dialogMessageInfoContent.html('El formato ya fue enviado a OSINERGMIN-GART');
			formato12D.dialogMessageInfo.dialog("open");
		}
	},
	/***VALIDACIONES**/
	validarFormatoDetalle : function(){
		if( formato12D.codEmpresaDetalle.val().length=='' ){
			//alert('Seleccione una Distribuidora Eléctrica'); 
			formato12D.dialogMessageWarningDetalleContent.html('Debe seleccionar una Distribuidora Eléctrica');
			formato12D.dialogMessageWarningDetalle.dialog("open");
			formato12D.codEmpresaDetalle.val().focus();
		    return false;
		}
		/*if(formato12D.periodoEnvioDetalle.val().length == '' ) {		  
			//alert('Debe ingresar el periodo de presentación');
			formato12D.dialogMessageWarningDetalleContent.html('Debe ingresar el periodo de presentación');
			formato12D.dialogMessageWarningDetalle.dialog("open");
			formato12D.periodoEnvioDetalle.val().focus();
   			return false; 
 		}*/
		if(formato12D.periodoEnvioDetalle==null || formato12D.periodoEnvioDetalle.val().length == '' ) {		  
			//alert('Debe seleccionar el periodo a declarar');
			formato12D.dialogMessageWarningDetalleContent.html('Debe ingresar el Periodo a Declarar');
			formato12D.dialogMessageWarningDetalle.dialog("open");
			formato12D.periodoEnvio.focus();
			return false; 
		}
		if(formato12D.anoEjecucionDetalle.val().length == '' ) {		  
		    //alert('Debe ingresar el año de ejecución');
		    formato12D.dialogMessageWarningDetalleContent.html('Debe ingresar el Año de Ejecución');
			formato12D.dialogMessageWarningDetalle.dialog("open");
		    formato12D.anoEjecucionDetalle.focus();
		    return false; 
	  	}else{
		  	var numstr = trim(formato12D.anoEjecucionDetalle.val());
		 	 if (isNaN(numstr) || numstr.length<4 || parseFloat(numstr)<1900){
			  	//alert('Ingrese un año de ejecución válido');
			  	formato12D.dialogMessageWarningDetalleContent.html('Ingrese un Año de Ejecución válido');
				formato12D.dialogMessageWarningDetalle.dialog("open");
			  	return false;
		  	}
	 	 }
		if(formato12D.mesEjecucionDetalle.val().length == '' ) {		  
		   //alert('Debe seleccionar el mes de ejecución');
		   formato12D.dialogMessageWarningDetalleContent.html('Debe seleccionar el Mes de Ejecución');
		   formato12D.dialogMessageWarningDetalle.dialog("open");
		   formato12D.mesEjecucionDetalle.focus();
		   return false; 
		}
		if(formato12D.etapaEjecucionDetalle.val().length == '' ) {		  
		   //alert('Debe ingresar la etapa de ejecución');
		   formato12D.dialogMessageWarningDetalleContent.html('Debe ingresar la Etapa de Ejecución');
		   formato12D.dialogMessageWarningDetalle.dialog("open");
		   formato12D.etapaEjecucionDetalle.focus();
		   return false; 
		}
		//validamos ubigeo 
		if(formato12D.codDepa.val().length != '' ) {
			if(formato12D.codProv.val().length == '' ) {		  
			   //alert('Debe seleccionar la provincia');
			   formato12D.dialogMessageWarningDetalleContent.html('Debe seleccionar la Provincia');
			   formato12D.dialogMessageWarningDetalle.dialog("open");
			   formato12D.codProv.focus();
			   return false; 
			}else{
				if(formato12D.codDist.val().length == '' ) {		  
				   //alert('Debe seleccionar el distrito');
				   formato12D.dialogMessageWarningDetalleContent.html('Debe seleccionar el Distrito');
					formato12D.dialogMessageWarningDetalle.dialog("open");
				   formato12D.codDist.focus();
				   return false; 
				}
			}
		}
		if(formato12D.idZonaBenefDetalle.val().length == '' ) {		  
		   //alert('Debe seleccionar la Zona Beneficiario');
		   formato12D.dialogMessageWarningDetalleContent.html('Debe seleccionar la Zona Beneficiario');
			formato12D.dialogMessageWarningDetalle.dialog("open");
		   formato12D.idZonaBenefDetalle.focus();
		   return false; 
		}else{
			if(formato12D.cod_empresa_edelnor.val()!=formato12D.codEmpresaDetalle.val() && formato12D.cod_empresa_luz_sur.val()!=formato12D.codEmpresaDetalle.val()){
				if(formato12D.idZonaBenefDetalle.val() == 3 ) {//RURAL
					//alert('No puede seleccionar la Zona Beneficiario Lima para la Distribuidora Eléctrica');
					formato12D.dialogMessageWarningDetalleContent.html('No puede seleccionar la Zona Beneficiario Lima para la Distribuidora Eléctrica');
					formato12D.dialogMessageWarningDetalle.dialog("open");
					formato12D.idZonaBenefDetalle.focus();
				   	return false;
				}
			}
		}
		//validamos si escogemos un tipo de documento
		if(formato12D.tipoDocDetalle.val().length != '' ) {
			if(formato12D.rucEmpresaDetalle.val().length == '' ) {		  
			   //alert('Debe ingresar el RUC de la empresa');
			   formato12D.dialogMessageWarningDetalleContent.html('Debe ingresar el RUC de la empresa');
				formato12D.dialogMessageWarningDetalle.dialog("open");
			   formato12D.rucEmpresaDetalle.focus();
			   return false; 
			}
			if(formato12D.serieDocDetalle.val().length == '' ) {		  
			   //alert('Debe ingresar la serie de documento de referencia');
			   formato12D.dialogMessageWarningDetalleContent.html('Debe ingresar la Serie de Documento de Referencia');
				formato12D.dialogMessageWarningDetalle.dialog("open");
			   formato12D.serieDocDetalle.focus();
			   return false; 
			}
			if(formato12D.nroDocDetalle.val().length == '' ) {		  
			   //alert('Debe ingresar el nro. documento de referencia');
			   formato12D.dialogMessageWarningDetalleContent.html('Debe ingresar el Nro. Documento de Referencia');
				formato12D.dialogMessageWarningDetalle.dialog("open");
			   formato12D.nroDocDetalle.focus();
			   return false; 
			}
		}
		//
		return true; 
	},
	soloNumerosEnteros : function(){
		formato12D.cantidadDetalle.attr("onkeypress","return soloNumerosDecimales(event, 1, 'cantidad',6,0)");
	},
	soloNumerosDecimales : function(){
		formato12D.costoUnitarioDetalle.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costoUnitario',7,2)");
	},
	//
	iniciamosValores : function(){
		formato12D.costoUnitarioDetalle.val('0.00');
		formato12D.totalDetalle.val('0.00');
	},
	formularioCompletarDecimales : function(){
		completarDecimal('costoUnitario',2);
		completarDecimal('totalGeneral',2);
	},
	estiloEdicionPKDetalle : function(){
		formato12D.anoEjecucionDetalle.addClass("fise-editable");
		//formato12D.mesEjecucionDetalle.addClass("fise-editable");
	},
	estiloEdicionDetalle : function(){
		formato12D.localidadDetalle.addClass("fise-editable");
		formato12D.cuentaContableDetalle.addClass("fise-editable");
		formato12D.gastoDetalle.addClass("fise-editable");
		formato12D.rucEmpresaDetalle.addClass("fise-editable");
		formato12D.serieDocDetalle.addClass("fise-editable");
		formato12D.nroDocDetalle.addClass("fise-editable");
		formato12D.fechaAutoDetalle.addClass("fise-editable");
		formato12D.nroDocAutoDetalle.addClass("fise-editable");
		formato12D.cantidadDetalle.addClass("fise-editable");
		formato12D.costoUnitarioDetalle.addClass("fise-editable");
	},
	//quitar estilos
	quitarEstiloEdicionPKDetalle : function(){
		formato12D.anoEjecucionDetalle.removeClass("fise-editable");
		//formato12D.mesEjecucionDetalle.removeClass("fise-editable");
	},
	quitarEstiloEdicionDetalle : function(){
		formato12D.localidadDetalle.removeClass("fise-editable");
		formato12D.cuentaContableDetalle.removeClass("fise-editable");
		formato12D.gastoDetalle.removeClass("fise-editable");
		formato12D.rucEmpresaDetalle.removeClass("fise-editable");
		formato12D.serieDocDetalle.removeClass("fise-editable");
		formato12D.nroDocDetalle.removeClass("fise-editable");
		formato12D.fechaAutoDetalle.removeClass("fise-editable");
		formato12D.nroDocAutoDetalle.removeClass("fise-editable");
		formato12D.cantidadDetalle.removeClass("fise-editable");
		formato12D.costoUnitarioDetalle.removeClass("fise-editable");	
	}
};



</script>