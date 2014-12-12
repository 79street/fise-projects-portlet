<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@page import="com.liferay.portal.kernel.util.PrefsPropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<portlet:defineObjects />

<script type="text/javascript" src="/fise-projects-portlet/js/fise.js"></script>
<script type="text/javascript">
	var formato12B = {
		
		formBusqueda: null,	
		tablaBusqueda:null,
		paginadoBusqueda:null,
		urlBusqueda: null,
		btnBuscar:null,
		btnNew:null,
		
		tpOperacion:null,
		formNewEdit: null,	
		cmbCodEmpresa:null,
		cmbPeriodo:null,
		txtEmpresa:null,
		txtPeriodo:null,
		divgrupoestado:null,
		
		
		urlLoadPeriodo:null,
		urlLoadCostosUnitarios:null,
		dlgLoadFile:null,
		divOverlay:null,
		btnShowLoadExcel:null,
        btnUploadExcel:null,
        dlgConfirmacion:null,
        
        urlViewFormato:null,
        btnGuardar: null,
        urlSave:null,
        urlDeleteFormato:null,
        btnBack: null,
        
        txtnroValesImpreso:null,
		txtEtndrUnitValeImpre:null,
        txtTotalImpresionVale:null,
		txtnroValesRepartidosDomi:null,
		txtEtndrUnitValeRepar:null,
        txtTotalRepartoValesDomi:null,
		txtnroValesEntregadoDisEl:null,
		txtEtndrUnitValDisEl:null,
        txtTotalEntregaValDisEl:null,
		txtnroValesFisicosCanjeados:null,
		txtEtndrUnitValFiCan:null,
        txtTotalCanjeLiqValeFis:null,
		txtnroValesDigitalCanjeados:null,
		txtEtndrUnitValDgCan:null,
        txtTotalCanjeLiqValeDig:null,
		txtnroAtenciones:null,
		txtEtndrUnitAtencion:null,
        txtTotalAtencionConsRecl:null,
		
		 txtnroValesImpresoProv:null,
		txtEtndrUnitValeImpreProv:null,
        txtTotalImpresionValeProv:null,
        txtnroValesRepartidosDomiProv:null,
		txtEtndrUnitValeReparProv:null,
        txtTotalRepartoValesDomiProv:null,
        txtnroValesEntregadoDisElProv:null,
		txtEtndrUnitValDisElProv:null,
		 txtTotalEntregaValDisElProv:null,
        txtnroValesFisicosCanjeadosProv:null,
		txtEtndrUnitValFiCanProv:null,
		txtTotalCanjeLiqValeFisProv:null,
        txtnroValesDigitalCanjeadosProv:null,
		txtEtndrUnitValDgCanProv:null,
		txtTotalCanjeLiqValeDigProv:null,
        txtnroAtencionesProv:null,
		txtEtndrUnitAtencionProv:null,
        txtTotalAtencionConsReclProv:null,
		
		txtnroValesImpresoLim:null,
		txtEtndrUnitValeImpreLim:null,
        txtTotalImpresionValeLim:null,
        txtnroValesRepartidosDomiLim:null,
		 txtEtndrUnitValeReparLim:null,
        txtTotalRepartoValesDomiLim:null,
        txtnroValesEntregadoDisElLim:null,
		 txtEtndrUnitValDisElLim:null,
        txtTotalEntregaValDisElLim:null,
        txtnroValesFisicosCanjeadosLim:null,
		txtEtndrUnitValFiCanLim:null,
        txtTotalCanjeLiqValeFisLim:null,
        txtnroValesDigitalCanjeadosLim:null,
		txtEtndrUnitValDgCanLim:null,
        txtTotalCanjeLiqValeDigLim:null,
        txtnroAtencionesLim:null,
		txtEtndrUnitAtencionLim:null,
        txtTotalAtencionConsReclLim:null,
        
        txtTotalGestionAdministrativa:null,
		txtTotalGestionAdministrativaProv:null,
        txtTotalGestionAdministrativaLim:null,
        txtTotalDesplazamientoPersonal:null,
		 txtTotalDesplazamientoPersonalProv:null,
        txtTotalDesplazamientoPersonalLim:null,
        txtTotalActividadesExtraord:null,
		 txtTotalActividadesExtraordProv:null,
        txtTotalActividadesExtraordLim:null,
      
        
        divLoadFile:null,
        btnReportePdf:null,
        btnReporteExpExcel:null,
        btnReporteActaEnvio:null,
        btnValidacion:null,
        btnEnvioDefinitivo:null,
        
        urlViewReporte:null,
        urlReporteActaEnvio:null,
        urlValidacion:null,
        urlReporteValidacion:null,
        urlEnvioDefinitivo:null,
        urlReporteEnvioDefinitivo:null,
        
        txtAnioEjec:null,
        txtAnioEjecCommand:null,
       
        
        
        dialogObservacion:null,
        tablaObservacion:null,
        paginadoObservacion:null,
        
        lblConfirmEnvioContent:null,
        dialogConfirmEnvio:null,
        lblMessageReportContent:null,
        dialogMessageReport:null,
        emailConfigured:null,
      
        urlRetornar:null,
        dialogMessageGeneral:null,
        lblMessage:null,
       
        cmbMesEjecucion:null,
        txtMesEjechidden:null,
        
        dialogMessageGeneralInicial:null,
        lblMessageInicial:null,
        
        blockUI : function() {
			$.blockUI({
						message : '<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Cargando </h3>'
					});
		},
		unblockUI : function() {
			$.unblockUI();
		},
		
		loadInit:function(urlNew,urlView){
			
			this.formBusqueda=$('#frmBusqueda');
			this.tablaBusqueda=$("#<portlet:namespace/>grid_formato");
			this.paginadoBusqueda='#<portlet:namespace/>pager_formato';
			this.urlBusqueda='<portlet:resourceURL id="searchFormats" />';
			this.btnBuscar=$("#<portlet:namespace/>buscarFormato");
			this.btnNew=$("#<portlet:namespace/>newFormato");
			this.dlgConfirmacion=$("#dlgConfirmacion");
			this.urlDeleteFormato='<portlet:resourceURL id="deleteFormato"/>';
			
			this. dialogMessageGeneralInicial=$("#dialogMessageGeneralInicio");
			this.lblMessageInicial=$("#lblMessageInicio");
			
			formato12B.initDialogsInit();
			this.urlViewFormato=urlView;
			formato12B.btnBuscar.click(function() {
				formato12B.searchFormato();
			});
			
			formato12B.buildGridsBusqueda();
			formato12B.btnBuscar.trigger('click');
			
			formato12B.btnNew.click(function() {
				formato12B.blockUI();
				location.href=urlNew;
			});
			
		},buildGridsBusqueda : function () {	
			formato12B.tablaBusqueda.jqGrid({
				   datatype: "local",
			       colNames: ['Empresa','Año Pres.','Mes Pres.','Etapa','Grupo de Información','Estado','Visualizar','Editar','Anular','','','','','','',''],
			       colModel: [
							{ name: 'descEmpresa', index: 'descEmpresa', width: 50},
			               { name: 'anoPresentacion', index: 'anoPresentacion', width: 30 },   
			               { name: 'descMes', index: 'descMes', width: 30},
			               { name: 'etapa', index: 'etapa',width: 50},
			               { name: 'descGrupo', index: 'descGrupo', width: 50},
			               { name: 'descEstado', index: 'descEstado', width: 50},
			               { name: 'view', index: 'view', width: 20,align:'center' },
			               { name: 'edit', index: 'edit', width: 20,align:'center' },
			               { name: 'elim', index: 'elim', width: 20,align:'center' },
			               { name: 'codEmpresa', index: 'codEmpresa', hidden: true},
			               { name: 'mesPresentacion', index: 'mesPresentacion', hidden: true},
			               { name: 'estadoEnvio', index: 'estadoEnvio', hidden: true},
			               { name: 'estadoProceso', index: 'estadoProceso', hidden: true},
			               { name: 'mesEjecucionGasto', index: 'mesEjecucionGasto', hidden: true},
			               { name: 'descMesEjec', index: 'descMesEjec', hidden: true},
			               { name: 'anoEjecucionGasto', index: 'anoEjecucionGasto', hidden: true}
				   	    ],
				   	 multiselect: false,
						rowNum:10,
					   	rowList:[10,20,50],
						height: 200,
					   	autowidth: true,
						rownumbers: true,
						shrinkToFit:true,
						pager: formato12B.paginadoBusqueda,
					    viewrecords: true,
					   	caption: "Formatos",
					    sortorder: "asc",	   	    	   	   
			       gridComplete: function(){
			      		var ids = formato12B.tablaBusqueda.jqGrid('getDataIDs');
			      		for(var i=0;i < ids.length;i++){
			      			var cl = ids[i];
			      			var ret = formato12B.tablaBusqueda.jqGrid('getRowData',cl); 
			      			view = "<a href='#'><img border='0' title='View' src='/net-theme/images/img-net/file.png'  align='center' onclick=\"formato12B.showConfirmacion('"+ret.codEmpresa+"','"+ret.mesPresentacion+"','"+ret.anoPresentacion+"','"+ret.etapa+"','"+ret.descMes+"','"+ret.estadoEnvio+"','"+ret.estadoProceso+"','"+ret.mesEjecucionGasto+"','"+ret.descMesEjec+"','"+ret.anoEjecucionGasto+"','0');\" /></a> ";
			      			edit = "<a href='#'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png'  align='center' onclick=\"formato12B.showConfirmacion('"+ret.codEmpresa+"','"+ret.mesPresentacion+"','"+ret.anoPresentacion+"','"+ret.etapa+"','"+ret.descMes+"','"+ret.estadoEnvio+"','"+ret.estadoProceso+"','"+ret.mesEjecucionGasto+"','"+ret.descMesEjec+"','"+ret.anoEjecucionGasto+"','1');\" /></a> ";
			      			elem = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"formato12B.showConfirmacion('"+ret.codEmpresa+"','"+ret.mesPresentacion+"','"+ret.anoPresentacion+"','"+ret.etapa+"','"+ret.descMes+"','"+ret.estadoEnvio+"','"+ret.estadoProceso+"','"+ret.mesEjecucionGasto+"','"+ret.descMesEjec+"','"+ret.anoEjecucionGasto+"','3');\" /></a> ";
			      			
			      			
			      			formato12B.tablaBusqueda.jqGrid('setRowData',ids[i],{view:view});
			      			formato12B.tablaBusqueda.jqGrid('setRowData',ids[i],{edit:edit});
			      			formato12B.tablaBusqueda.jqGrid('setRowData',ids[i],{elim:elem});
			      		}
			      }
			  	});
				formato12B.tablaBusqueda.jqGrid('navGrid',formato12B.paginadoBusqueda,{add:false,edit:false,del:false,search: false,refresh: false});	
				formato12B.tablaBusqueda.jqGrid('navButtonAdd',formato12B.paginadoBusqueda,{
				       caption:"Exportar a Excel",
				       buttonicon: "ui-icon-bookmark",
				       onClickButton : function () {
				           location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';
				       } 
				}); 
			},
		searchFormato : function () {	
            jQuery.ajax({			
						url: formato12B.urlBusqueda+'&'+formato12B.formBusqueda.serialize(),
						dataType: "json",
                        contentType: "application/json; charset=utf-8",
		                type: 'post',
						beforeSend:function(){
							formato12B.blockUI();
						},				
						success: function(gridData) {
							if(gridData!=null){
								formato12B.tablaBusqueda.clearGridData(true);
								formato12B.tablaBusqueda.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
								formato12B.tablaBusqueda[0].refreshIndex();
							}
							
							formato12B.unblockUI();
						},error : function(){
							alert("Error de conexión.");
							formato12B.unblockUI();
						}
				});
        },
		
		loadInitDetalle:function(urlBack){
			
			this.urlRetornar=urlBack;
			this.urlLoadPeriodo='<portlet:resourceURL id="loadPeriodoDeclaracion" />';
			this.urlLoadCostosUnitarios='<portlet:resourceURL id="loadCostoUnitario" />';
			this.cmbCodEmpresa=$('#cmbEmpresa');
			
			this.formNewEdit=$('#frmAddUpdate');
			this.dlgLoadFile=$('#dlgLoadFile');
			this.divOverlay=$("#<portlet:namespace/>divOverlay");
			
			this.btnShowLoadExcel=$('#<portlet:namespace/>cargaExcel');
			this.tpOperacion=$('#txtOperacion');
			this.btnUploadExcel=$('#btnUploadExcel');
			this.btnGuardar=$('#<portlet:namespace/>guardarFormato');
			this.urlSave='<portlet:resourceURL id="saveNuevoFormato"/>';
			
			
			this.btnBack=$("#<portlet:namespace/>regresarFormato");
			
			this.cmbPeriodo=$('#cmbPeriodo');
			this.txtEmpresa=$('#txtDescEmp');
			this.txtPeriodo=$('#txtPeriodo');
			this.divgrupoestado=$('#divgrupoestado');
			
			
			this.txtAnioEjec=$('#txtanoEjecucionGasto');
		//	this.txtMesEjec=$('#txtmesEjecucionGasto');
			this.txtAnioEjecCommand=$('#anoEjecucionGasto');
		//	this. txtMesEjecCommnad=$('#mesEjecucionGasto');
			
			 this.cmbMesEjecucion=$('#cmbMesEjecucion');
			 this.txtMesEjechidden=$('#txtmesEjecucionGasto');
			
			this.txtnroValesImpreso=$('#numeroValesImpreso');
			this.txtnroValesImpresoProv=$('#numeroValesImpresoProv');
			this.txtnroValesImpresoLim=$('#numeroValesImpresoLim');
			this.txtEtndrUnitValeImpre=$('#txtcostoEstandarUnitValeImpre');
			this.txtEtndrUnitValeImpreProv=$('#txtcostoEstandarUnitValeImpreProv');
			this.txtEtndrUnitValeImpreLim=$('#txtcostoEstandarUnitValeImpreLim');
			this.txtTotalImpresionVale=$('#txtcostoTotalImpresionVale');
			this.txtTotalImpresionValeProv=$('#txtcostoTotalImpresionValeProv');
			this.txtTotalImpresionValeLim=$('#txtcostoTotalImpresionValeLim');
			
			
			this.txtnroValesRepartidosDomi=$('#numeroValesRepartidosDomi');
			this.txtnroValesRepartidosDomiProv=$('#numeroValesRepartidosDomiProv');
			this.txtnroValesRepartidosDomiLim=$('#numeroValesRepartidosDomiLim');
			this.txtTotalRepartoValesDomi=$('#txtcostoTotalRepartoValesDomi');
			this.txtTotalRepartoValesDomiProv=$('#txtcostoTotalRepartoValesDomiProv');
			this.txtTotalRepartoValesDomiLim=$('#txtcostoTotalRepartoValesDomiLim');
			this.txtEtndrUnitValeRepar=$('#txtcostoEstandarUnitValeRepar');
			this.txtEtndrUnitValeReparProv=$('#txtcostoEstandarUnitValeReparProv');
			this.txtEtndrUnitValeReparLim=$('#txtcostoEstandarUnitValeReparLim');
			
			this.txtnroValesEntregadoDisEl=$('#numeroValesEntregadoDisEl');
			 this.txtnroValesEntregadoDisElProv=$('#numeroValesEntregadoDisElProv');
			  this.txtnroValesEntregadoDisElLim=$('#numeroValesEntregadoDisElLim');
			  this.txtEtndrUnitValDisEl=$('#txtcostoEstandarUnitValDisEl');
			  this.txtEtndrUnitValDisElProv=$('#txtcostoEstandarUnitValDisElProv');
			  this.txtEtndrUnitValDisElLim=$('#txtcostoEstandarUnitValDisElLim');
			this.txtTotalEntregaValDisEl=$('#txtcostoTotalEntregaValDisEl');
			this.txtTotalEntregaValDisElProv=$('#txtcostoTotalEntregaValDisElProv');
			this.txtTotalEntregaValDisElLim=$('#txtcostoTotalEntregaValDisElLim');
			
			this.txtnroValesFisicosCanjeados=$('#numeroValesFisicosCanjeados');
			this.txtnroValesFisicosCanjeadosProv=$('#numeroValesFisicosCanjeadosProv');
			this.txtnroValesFisicosCanjeadosLim=$('#numeroValesFisicosCanjeadosLim');
			this.txtEtndrUnitValFiCan=$('#txtcostoEstandarUnitValFiCan');
			this.txtEtndrUnitValFiCanProv=$('#txtcostoEstandarUnitValFiCanProv');
			this.txtEtndrUnitValFiCanLim=$('#txtcostoEstandarUnitValFiCanLim');
			this.txtTotalCanjeLiqValeFis=$('#txtcostoTotalCanjeLiqValeFis');
			this.txtTotalCanjeLiqValeFisProv=$('#txtcostoTotalCanjeLiqValeFisProv');
			this.txtTotalCanjeLiqValeFisLim=$('#txtcostoTotalCanjeLiqValeFisLim');
			
			
			this.txtnroValesDigitalCanjeados=$('#numeroValesDigitalCanjeados');
			this.txtnroValesDigitalCanjeadosProv=$('#numeroValesDigitalCanjeadosProv');
			this.txtnroValesDigitalCanjeadosLim=$('#numeroValesDigitalCanjeadosLim');
			this.txtEtndrUnitValDgCan=$('#txtcostoEstandarUnitValDgCan');
			this.txtEtndrUnitValDgCanProv=$('#txtcostoEstandarUnitValDgCanProv');
			this.txtEtndrUnitValDgCanLim=$('#txtcostoEstandarUnitValDgCanLim');
			this.txtTotalCanjeLiqValeDig=$('#txtcostoTotalCanjeLiqValeDig');
			this.txtTotalCanjeLiqValeDigProv=$('#txtcostoTotalCanjeLiqValeDigProv');
			this.txtTotalCanjeLiqValeDigLim=$('#txtcostoTotalCanjeLiqValeDigLim');
			
			
			this.txtnroAtenciones=$('#numeroAtenciones');
			this.txtnroAtencionesProv=$('#numeroAtencionesProv');
			this.txtnroAtencionesLim=$('#numeroAtencionesLim');
			 this.txtEtndrUnitAtencion=$('#txtcostoEstandarUnitAtencion');
			 this.txtEtndrUnitAtencionProv=$('#txtcostoEstandarUnitAtencionProv');
			 this.txtEtndrUnitAtencionLim=$('#txtcostoEstandarUnitAtencionLim');
			this.txtTotalAtencionConsRecl=$('#txtcostoTotalAtencionConsRecl');
           this.txtTotalAtencionConsReclProv=$('#txtcostoTotalAtencionConsReclProv');
           this.txtTotalAtencionConsReclLim=$('#txtcostoTotalAtencionConsReclLim');
           
           this.txtTotalGestionAdministrativa=$('#totalGestionAdministrativa');
           this.txtTotalGestionAdministrativaProv=$('#totalGestionAdministrativaProv');
           this.txtTotalGestionAdministrativaLim=$('#totalGestionAdministrativaLim');
           this.txtTotalDesplazamientoPersonal=$('#totalDesplazamientoPersonal');
           this.txtTotalDesplazamientoPersonalProv=$('#totalDesplazamientoPersonalProv');
           this.txtTotalDesplazamientoPersonalLim=$('#totalDesplazamientoPersonalLim');
           this.txtTotalActividadesExtraord=$('#totalActividadesExtraord');
           this.txtTotalActividadesExtraordProv=$('#totalActividadesExtraordProv');
           this.txtTotalActividadesExtraordLim=$('#totalActividadesExtraordLim');
			
           this.divLoadFile=$('#divLoadFile');
           this.btnReportePdf=$('#btnPdf');
           this.btnReporteExpExcel=$('#btnExpExcel');
           this.btnReporteActaEnvio=$('#btnActaEnvio');
           this.btnValidacion=$('#btnValidacion');
           this.btnEnvioDefinitivo=$('#btnEnvioDefinitivo');
           
          
           
           this.urlViewReporte='<portlet:resourceURL id="showReporte" />';
          this.urlReporteActaEnvio='<portlet:resourceURL id="showReporteActaEnvio" />';
           this.urlValidacion='<portlet:resourceURL id="showValidacion" />';
           this.urlEnvioDefinitivo='<portlet:resourceURL id="envioDefinitivo" />';
           this.urlReporteValidacion='<portlet:resourceURL id="showReporteValidacion" />';
           this.urlReporteEnvioDefinitivo='<portlet:resourceURL id="showRptEnvioDefinitivo" />';
           
            
         this.dialogObservacion=$("#<portlet:namespace/>dialog-form-observacion");   
         this.tablaObservacion=$("#<portlet:namespace/>grid_observacion");
         this.paginadoObservacion='#<portlet:namespace/>pager_observacion';
         
         
         this.lblConfirmEnvioContent=$("#lblConfirmEnvioContent");   
         this.dialogConfirmEnvio=$("#dialogConfirmEnvio");   
         this.lblMessageReportContent=$("#lblMessageReportContent");   
         this.dialogMessageReport=$("#dialogMessageReport");   
         this.emailConfigured='<%=PrefsPropsUtil.getString(PropsKeys.MAIL_SESSION_MAIL_SMTP_USER)%>';
         
         this.dialogMessageGeneral=$("#dialogMessageGeneral");
         this.lblMessage=$("#lblMessage");
         
          formato12B.initDialogs();
 		
            formato12B.btnBack.click(function(){
				formato12B.blockUI();
				location.href=urlBack;
			});
            formato12B.showHiddenButtons(formato12B.tpOperacion.val());
           
			
            
			if(formato12B.tpOperacion.val()=='0'){
				formato12B.cmbCodEmpresa.change(function(){
					formato12B.loadPeriodoDeclaracion(formato12B.tpOperacion.val());
				});
				formato12B.cmbCodEmpresa.trigger('change');
				formato12B.showCamposOculto();
				formato12B.btnReportePdf.click(function() {formato12B.viewReporte('0');});
				formato12B.btnReporteExpExcel.click(function() {formato12B.viewReporte('1');});
				formato12B.btnReporteActaEnvio.click(function() {formato12B.showActaEnvio();});
				
				formato12B.loadTotales($("#codEmpresaHidden").val());
				
			}else if(formato12B.tpOperacion.val()=='1'){
				formato12B.cmbCodEmpresa.change(function(){
					formato12B.loadPeriodoDeclaracion(formato12B.tpOperacion.val());
				});
				formato12B.cmbCodEmpresa.trigger('change');
				
				formato12B.showCamposOculto();
				formato12B.eventButtons(formato12B.tpOperacion.val());
				formato12B.updateStyleClassInput(formato12B.tpOperacion.val(),formato12B.cmbCodEmpresa.val());
				formato12B.buildGridsObservacion();
				formato12B.loadTotales($("#codEmpresaHidden").val());
			
			}else if(formato12B.tpOperacion.val()=='2'){
				formato12B.cmbCodEmpresa.change(function(){
					$.when(formato12B.loadPeriodoDeclaracion(formato12B.tpOperacion.val())).then(formato12B.loadCostosUnitarios());
				});
				formato12B.cmbCodEmpresa.trigger('change');
				formato12B.cmbPeriodo.change(function(){
					formato12B.loadCostosUnitarios();
				});
				formato12B.eventButtons(formato12B.tpOperacion.val());
				formato12B.updateStyleClassInput(formato12B.tpOperacion.val(),formato12B.cmbCodEmpresa.val());
				
				formato12B.loadTotales(formato12B.cmbCodEmpresa.val());
			}
			
		
			
		},
		eventButtons:function (tipo){
			formato12B.divLoadFile.css("display","block");
			formato12B.btnGuardar.css("display","block");
			
			formato12B.btnShowLoadExcel.click(function() {formato12B.showLoadFile('1');});
			formato12B.btnUploadExcel.click(function() {formato12B.uploadFile('1');});
			formato12B.btnGuardar.click(function(){formato12B.save();});
		
			if(tipo == '1'){//editar
				formato12B.btnValidacion.css("display","block");
				formato12B.btnEnvioDefinitivo.css("display","block");
				formato12B.btnValidacion.click(function() {formato12B.showValidacion();});
				formato12B.btnEnvioDefinitivo.click(function() {formato12B.confirmarEnvioDefinitivo();});
			
			}else if(tipo == '2'){//nuevo
				formato12B.btnValidacion.css("display","none");
				formato12B.btnEnvioDefinitivo.css("display","none");
			}
			
		},
	   showHiddenButtons:function(tipo){
			
			if(tipo == '0'){
				formato12B.divLoadFile.css("display","none");
				formato12B.btnValidacion.css("display","none");
				formato12B.btnEnvioDefinitivo.css("display","none");
				formato12B.btnGuardar.css("display","none");
				formato12B.btnReporteExpExcel.css("display","block");
				formato12B.btnReporteActaEnvio.css("display","block");
				formato12B.btnReportePdf.css("display","block");
				
				formato12B.txtnroValesImpreso.prop('disabled', true);
				formato12B.txtnroValesImpresoProv.prop('disabled', true);
				formato12B.txtnroValesImpresoLim.prop('disabled', true);
				formato12B.txtnroValesRepartidosDomi.prop('disabled', true);
				formato12B.txtnroValesRepartidosDomiProv.prop('disabled', true);
				formato12B.txtnroValesRepartidosDomiLim.prop('disabled', true);
				formato12B.txtnroValesEntregadoDisEl.prop('disabled', true);
				formato12B.txtnroValesEntregadoDisElProv.prop('disabled', true);
				formato12B.txtnroValesEntregadoDisElLim.prop('disabled', true);
				formato12B.txtnroValesFisicosCanjeados.prop('disabled', true);
				formato12B.txtnroValesFisicosCanjeadosProv.prop('disabled', true);
				formato12B.txtnroValesFisicosCanjeadosLim.prop('disabled', true);
				formato12B.txtnroValesDigitalCanjeados.prop('disabled', true);
				formato12B.txtnroValesDigitalCanjeadosProv.prop('disabled', true);
				formato12B.txtnroValesDigitalCanjeadosLim.prop('disabled', true);
				formato12B.txtnroAtenciones.prop('disabled', true);
				formato12B.txtnroAtencionesProv.prop('disabled', true);
				formato12B.txtnroAtencionesLim.prop('disabled', true);
				
				formato12B.txtTotalGestionAdministrativa.prop('disabled', true);
				formato12B.txtTotalGestionAdministrativaProv.prop('disabled', true);
				formato12B.txtTotalGestionAdministrativaLim.prop('disabled', true);
				formato12B.txtTotalDesplazamientoPersonal.prop('disabled', true);
				formato12B.txtTotalDesplazamientoPersonalProv.prop('disabled', true);
				formato12B.txtTotalDesplazamientoPersonalLim.prop('disabled', true);
				formato12B.txtTotalActividadesExtraord.prop('disabled', true);
				formato12B.txtTotalActividadesExtraordProv.prop('disabled', true);
				formato12B.txtTotalActividadesExtraordLim.prop('disabled', true);
			
				
				
				
			}else{
				
				formato12B.btnReporteExpExcel.css("display","none");
				formato12B.btnReporteActaEnvio.css("display","none");
				formato12B.btnReportePdf.css("display","none");
				
				
				formato12B.txtnroValesImpreso.prop('disabled', false);
				formato12B.txtnroValesImpresoProv.prop('disabled', false);
				formato12B.txtnroValesRepartidosDomi.prop('disabled', false);
				formato12B.txtnroValesRepartidosDomiProv.prop('disabled', false);
				formato12B.txtnroValesEntregadoDisEl.prop('disabled', false);
				formato12B.txtnroValesEntregadoDisElProv.prop('disabled', false);
				formato12B.txtnroValesFisicosCanjeados.prop('disabled', false);
				formato12B.txtnroValesFisicosCanjeadosProv.prop('disabled', false);
				formato12B.txtnroValesDigitalCanjeados.prop('disabled', false);
				formato12B.txtnroValesDigitalCanjeadosProv.prop('disabled', false);
				formato12B.txtnroAtenciones.prop('disabled', false);
				formato12B.txtnroAtencionesProv.prop('disabled', false);
				formato12B.txtTotalGestionAdministrativa.prop('disabled', false);
				formato12B.txtTotalGestionAdministrativaProv.prop('disabled', false);
				formato12B.txtTotalDesplazamientoPersonal.prop('disabled', false);
				formato12B.txtTotalDesplazamientoPersonalProv.prop('disabled', false);
				formato12B.txtTotalActividadesExtraord.prop('disabled', false);
				formato12B.txtTotalActividadesExtraordProv.prop('disabled', false);
				
				if(formato12B.cmbCodEmpresa.val()=='EDLN' || formato12B.cmbCodEmpresa.val()=='LDS'){
					formato12B.txtnroValesImpresoLim.prop('disabled', false);
					formato12B.txtnroValesRepartidosDomiLim.prop('disabled', false);
					formato12B.txtnroValesEntregadoDisElLim.prop('disabled', false);
					formato12B.txtnroValesFisicosCanjeadosLim.prop('disabled', false);
					formato12B.txtnroValesDigitalCanjeadosLim.prop('disabled', false);
					formato12B.txtnroAtencionesLim.prop('disabled', false);
					formato12B.txtTotalGestionAdministrativaLim.prop('disabled', false);
					formato12B.txtTotalDesplazamientoPersonalLim.prop('disabled', false);
					formato12B.txtTotalActividadesExtraordLim.prop('disabled', false);
				
				}else{
					formato12B.txtnroValesImpresoLim.prop('disabled', true);
					formato12B.txtnroValesRepartidosDomiLim.prop('disabled', true);
					formato12B.txtnroValesEntregadoDisElLim.prop('disabled', true);
					formato12B.txtnroValesFisicosCanjeadosLim.prop('disabled', true);
					formato12B.txtnroValesDigitalCanjeadosLim.prop('disabled', true);
					formato12B.txtnroAtencionesLim.prop('disabled', true);
					formato12B.txtTotalGestionAdministrativaLim.prop('disabled', true);
					formato12B.txtTotalDesplazamientoPersonalLim.prop('disabled', true);
					formato12B.txtTotalActividadesExtraordLim.prop('disabled', true);
				}

			
				
			}
			
			
		},
		loadPeriodoDeclaracion : function(tipo){
			return jQuery.ajax({
				url: formato12B.urlLoadPeriodo+'&'+formato12B.formNewEdit.serialize(),
				contentType: "application/json; charset=utf-8",
                async : false,
				type: 'post',
				dataType: 'json',
				beforeSend:function(){
					formato12B.blockUI();
				},
				success: function(data) {		
					dwr.util.removeAllOptions("cmbPeriodo");
					dwr.util.addOptions("cmbPeriodo", data,"codigoItem","descripcionItem");
					if(tipo =='0' || tipo =='1'){
						formato12B.cmbPeriodo.val($("#peridoDeclaracionHidden").val());	
					}
					
					formato12B.unblockUI();
				},error : function(){
					alert("Error de conexión.");
					formato12B.unblockUI();
				}
			});
		},
		
		
		loadCostosUnitarios : function(){
			var periodo=formato12B.cmbPeriodo.val();
			var anioPres=periodo.substring(0,4);
			var mesPres=periodo.substring(4,6);
			
			return jQuery.ajax({
				url: formato12B.urlLoadCostosUnitarios+'&'+formato12B.formNewEdit.serialize(),
				contentType: "application/json; charset=utf-8",
                async : false,
				type: 'post',
				dataType: 'json',
				beforeSend:function(){
					formato12B.blockUI();
				},
				success: function(data) {		
					
					formato12B.txtAnioEjec.val(anioPres);
					formato12B.txtAnioEjecCommand.val(anioPres);
					formato12B.cmbMesEjecucion.val(parseInt(mesPres));
					formato12B.txtMesEjechidden.val(parseInt(mesPres));
					//formato12B.txtMesEjec.val(mesPres);
					//formato12B.txtMesEjecCommnad.val(mesPres);
					
					formato12B.unblockUI();
					formato12B.loadDataCostoUnitario(data);
					
				},error : function(){
					alert("Error de conexión.");
					formato12B.unblockUI();
				}
			});
		},
		loadDataCostoUnitario: function(data){
			if(data!=null && data.length>0){
				$.each(data, function (i, item) {
					
					 if(item.idZonaBenef == '1'){
						 formato12B.txtEtndrUnitValeImpre.val(item.costoUnitarioImpresionVales);
						 $('#costoEstandarUnitValeImpre').val(item.costoUnitarioImpresionVales);
						 
						 formato12B.txtEtndrUnitValeRepar.val(item.costoUnitReprtoValeDomici);
						 $('#costoEstandarUnitValeRepar').val(item.costoUnitReprtoValeDomici);
						 
						 formato12B.txtEtndrUnitValDisEl.val(item.costoUnitEntregaValDisEl);
						 $('#costoEstandarUnitValDisEl').val(item.costoUnitEntregaValDisEl);
						 
						 formato12B.txtEtndrUnitValFiCan.val(item.costoUnitCanjeLiqValFisi);
						 $('#costoEstandarUnitValFiCan').val(item.costoUnitCanjeLiqValFisi);
						 
						 formato12B.txtEtndrUnitValDgCan.val(item.costoUnitCanjeValDigital);
						 $('#costoEstandarUnitValDgCan').val(item.costoUnitCanjeValDigital);
						 
						 formato12B.txtEtndrUnitAtencion.val(item.costoUnitarioPorAtencion);
						 $('#costoEstandarUnitAtencion').val(item.costoUnitarioPorAtencion);
						 
					 }else if(item.idZonaBenef == '2'){
						 formato12B.txtEtndrUnitValeImpreProv.val(item.costoUnitarioImpresionVales);
						 $('#costoEstandarUnitValeImpreProv').val(item.costoUnitarioImpresionVales);
						 
						 formato12B.txtEtndrUnitValeReparProv.val(item.costoUnitReprtoValeDomici);
						 $('#costoEstandarUnitValeReparProv').val(item.costoUnitReprtoValeDomici);
						 
						 formato12B.txtEtndrUnitValDisElProv.val(item.costoUnitEntregaValDisEl);
						 $('#costoEstandarUnitValDisElProv').val(item.costoUnitEntregaValDisEl);
						 
						 formato12B.txtEtndrUnitValFiCanProv.val(item.costoUnitCanjeLiqValFisi);
						 $('#costoEstandarUnitValFiCanProv').val(item.costoUnitCanjeLiqValFisi);
						 
						 formato12B.txtEtndrUnitValDgCanProv.val(item.costoUnitCanjeValDigital);
						 $('#costoEstandarUnitValDgCanProv').val(item.costoUnitCanjeValDigital);
						 
						 formato12B.txtEtndrUnitAtencionProv.val(item.costoUnitarioPorAtencion);
						 $('#costoEstandarUnitAtencionProv').val(item.costoUnitarioPorAtencion);
						 
					 }
					 if(item.codEmpresa == 'EDLN' || item.codEmpresa == 'LDS'){
						 if(item.idZonaBenef == '3'){
							 formato12B.txtEtndrUnitValeImpreLim.val(item.costoUnitarioImpresionVales);
							 $('#costoEstandarUnitValeImpreLim').val(item.costoUnitarioImpresionVales);
							 
							 formato12B.txtEtndrUnitValeReparLim.val(item.costoUnitReprtoValeDomici);
							 $('#costoEstandarUnitValeReparLim').val(item.costoUnitReprtoValeDomici);
							 
							 formato12B.txtEtndrUnitValDisElLim.val(item.costoUnitEntregaValDisEl);
							 $('#costoEstandarUnitValDisElLim').val(item.costoUnitEntregaValDisEl);
							 
							 formato12B.txtEtndrUnitValFiCanLim.val(item.costoUnitCanjeLiqValFisi);
							 $('#costoEstandarUnitValFiCanLim').val(item.costoUnitCanjeLiqValFisi);
							 
							 formato12B.txtEtndrUnitValDgCanLim.val(item.costoUnitCanjeValDigital);
							 $('#costoEstandarUnitValDgCanLim').val(item.costoUnitCanjeValDigital);
							 
							 formato12B.txtEtndrUnitAtencionLim.val(item.costoUnitarioPorAtencion);
							 $('#costoEstandarUnitAtencionLim').val(item.costoUnitarioPorAtencion);
							 
						 }
					 }
					 
					 
	               
	             });
			}else{
				 
					 formato12B.txtEtndrUnitValeImpre.val("0");
					 $('#costoEstandarUnitValeImpre').val("0");
					 
					 formato12B.txtEtndrUnitValeRepar.val("0");
					 $('#costoEstandarUnitValeRepar').val("0");
					 
					 formato12B.txtEtndrUnitValDisEl.val("0");
					 $('#costoEstandarUnitValDisEl').val("0");
					 
					 formato12B.txtEtndrUnitValFiCan.val("0");
					 $('#costoEstandarUnitValFiCan').val("0");
					 
					 formato12B.txtEtndrUnitValDgCan.val("0");
					 $('#costoEstandarUnitValDgCan').val("0");
					 
					 formato12B.txtEtndrUnitAtencion.val("0");
					 $('#costoEstandarUnitAtencion').val("0");
					 
					 formato12B.txtEtndrUnitValeImpreProv.val("0");
					 $('#costoEstandarUnitValeImpreProv').val("0");
					 
					 formato12B.txtEtndrUnitValeReparProv.val("0");
					 $('#costoEstandarUnitValeReparProv').val("0");
					 
					 formato12B.txtEtndrUnitValDisElProv.val("0");
					 $('#costoEstandarUnitValDisElProv').val("0");
					 
					 formato12B.txtEtndrUnitValFiCanProv.val("0");
					 $('#costoEstandarUnitValFiCanProv').val("0");
					 
					 formato12B.txtEtndrUnitValDgCanProv.val("0");
					 $('#costoEstandarUnitValDgCanProv').val("0");
					 
					 formato12B.txtEtndrUnitAtencionProv.val("0");
					 $('#costoEstandarUnitAtencionProv').val("0");
					 				
					 
					 if(formato12B.cmbCodEmpresa.val()=='EDLN' || formato12B.cmbCodEmpresa.val()=='LDS'){
						 formato12B.txtEtndrUnitValeImpreLim.val("0");
						 $('#costoEstandarUnitValeImpreLim').val("0");
						 
						 formato12B.txtEtndrUnitValeReparLim.val("0");
						 $('#costoEstandarUnitValeReparLim').val("0");
						 
						 formato12B.txtEtndrUnitValDisElLim.val("0");
						 $('#costoEstandarUnitValDisElLim').val("0");
						 
						 formato12B.txtEtndrUnitValFiCanLim.val("0");
						 $('#costoEstandarUnitValFiCanLim').val("0");
						 
						 formato12B.txtEtndrUnitValDgCanLim.val("0");
						 $('#costoEstandarUnitValDgCanLim').val("0");
						 
						 formato12B.txtEtndrUnitAtencionLim.val("0");
						 $('#costoEstandarUnitAtencionLim').val("0");
					 }
					
					 
					formato12B.lblMessage.html("El periodo seleccionado no tiene informacion de costos unitarios en el formato 14B");
					formato12B.dialogMessageGeneral.dialog("open");
			}
			formato12B.loadCostoTotal(formato12B.cmbCodEmpresa.val());
			
		},
		loadCostoTotal:function(emp){
			formato12B.txtTotalImpresionVale.val((formato12B.txtnroValesImpreso.val()!=null && formato12B.txtnroValesImpreso.length>0)?(formato12B.txtnroValesImpreso.val()*formato12B.txtEtndrUnitValeImpre.val()):'0');
			formato12B.txtTotalImpresionValeProv.val((formato12B.txtnroValesImpresoProv.val()!=null && formato12B.txtnroValesImpresoProv.length>0)?(formato12B.txtnroValesImpresoProv.val()*formato12B.txtEtndrUnitValeImpreProv.val()):'0');
			formato12B.txtTotalImpresionVale.val(redondeo(formato12B.txtTotalImpresionVale.val(), 2));
			formato12B.txtTotalImpresionValeProv.val(redondeo(formato12B.txtTotalImpresionValeProv.val(), 2));
			$('#costoTotalImpresionVale').val(formato12B.txtTotalImpresionVale.val());
			$('#costoTotalImpresionValeProv').val(formato12B.txtTotalImpresionValeProv.val());
		
			formato12B.txtTotalRepartoValesDomi.val((formato12B.txtnroValesRepartidosDomi.val()!=null && formato12B.txtnroValesRepartidosDomi.length>0)?(formato12B.txtnroValesRepartidosDomi.val()*formato12B.txtEtndrUnitValeRepar.val()):'0');
			formato12B.txtTotalRepartoValesDomiProv.val((formato12B.txtnroValesRepartidosDomiProv.val()!=null && formato12B.txtnroValesRepartidosDomiProv.length>0)?(formato12B.txtnroValesRepartidosDomiProv.val()*formato12B.txtEtndrUnitValeReparProv.val()):'0');
			formato12B.txtTotalRepartoValesDomi.val(redondeo(formato12B.txtTotalRepartoValesDomi.val(), 2));
			formato12B.txtTotalRepartoValesDomiProv.val(redondeo(formato12B.txtTotalRepartoValesDomiProv.val(), 2));
			$('#costoTotalRepartoValesDomi').val(formato12B.txtTotalRepartoValesDomi.val());
			$('#costoTotalRepartoValesDomiProv').val(formato12B.txtTotalRepartoValesDomiProv.val());
			
		    formato12B.txtTotalEntregaValDisEl.val((formato12B.txtnroValesEntregadoDisEl.val()!=null && formato12B.txtnroValesEntregadoDisEl.length>0)?(formato12B.txtnroValesEntregadoDisEl.val()*formato12B.txtEtndrUnitValDisEl.val()):'0');
			formato12B.txtTotalEntregaValDisElProv.val((formato12B.txtnroValesEntregadoDisElProv.val()!=null && formato12B.txtnroValesEntregadoDisElProv.length>0)?(formato12B.txtnroValesEntregadoDisElProv.val()*formato12B.txtEtndrUnitValDisElProv.val()):'0');
			formato12B.txtTotalEntregaValDisEl.val(redondeo(formato12B.txtTotalEntregaValDisEl.val(), 2));
			formato12B.txtTotalEntregaValDisElProv.val(redondeo(formato12B.txtTotalEntregaValDisElProv.val(), 2));
			$('#costoTotalEntregaValDisEl').val(formato12B.txtTotalEntregaValDisEl.val());
			$('#costoTotalEntregaValDisElProv').val(formato12B.txtTotalEntregaValDisElProv.val());
			
		    formato12B.txtTotalCanjeLiqValeFis.val((formato12B.txtnroValesFisicosCanjeados.val()!=null && formato12B.txtnroValesFisicosCanjeados.length>0)?(formato12B.txtnroValesFisicosCanjeados.val()*formato12B.txtEtndrUnitValFiCan.val()):'0');
			formato12B.txtTotalCanjeLiqValeFisProv.val((formato12B.txtnroValesFisicosCanjeadosProv.val()!=null && formato12B.txtnroValesFisicosCanjeadosProv.length>0)?(formato12B.txtnroValesFisicosCanjeadosProv.val()*formato12B.txtEtndrUnitValFiCanProv.val()):'0');
			formato12B.txtTotalCanjeLiqValeFis.val(redondeo(formato12B.txtTotalCanjeLiqValeFis.val(), 2));
			formato12B.txtTotalCanjeLiqValeFisProv.val(redondeo(formato12B.txtTotalCanjeLiqValeFisProv.val(), 2));
			$('#costoTotalCanjeLiqValeFis').val(formato12B.txtTotalCanjeLiqValeFis.val());
			$('#costoTotalCanjeLiqValeFisProv').val(formato12B.txtTotalCanjeLiqValeFisProv.val());
			
			formato12B.txtTotalCanjeLiqValeDig.val((formato12B.txtnroValesDigitalCanjeados.val()!=null && formato12B.txtnroValesDigitalCanjeados.length>0)?(formato12B.txtnroValesDigitalCanjeados.val()*formato12B.txtEtndrUnitValDgCan.val()):'0');
			formato12B.txtTotalCanjeLiqValeDigProv.val((formato12B.txtnroValesDigitalCanjeadosProv.val()!=null && formato12B.txtnroValesDigitalCanjeadosProv.length>0)?(formato12B.txtnroValesDigitalCanjeadosProv.val()*formato12B.txtEtndrUnitValDgCanProv.val()):'0');
			formato12B.txtTotalCanjeLiqValeDig.val(redondeo(formato12B.txtTotalCanjeLiqValeDig.val(), 2));
			formato12B.txtTotalCanjeLiqValeDigProv.val(redondeo(formato12B.txtTotalCanjeLiqValeDigProv.val(), 2));
			$('#costoTotalCanjeLiqValeDig').val(formato12B.txtTotalCanjeLiqValeDig.val());
			$('#costoTotalCanjeLiqValeDigProv').val(formato12B.txtTotalCanjeLiqValeDigProv.val());
			
			formato12B.txtTotalAtencionConsRecl.val((formato12B.txtnroAtenciones.val()!=null && formato12B.txtnroAtenciones.length>0)?(formato12B.txtnroAtenciones.val()*formato12B.txtEtndrUnitAtencion.val()):'0');
			formato12B.txtTotalAtencionConsReclProv.val((formato12B.txtnroAtencionesProv.val()!=null && formato12B.txtnroAtencionesProv.length>0)?(formato12B.txtnroAtencionesProv.val()*formato12B.txtEtndrUnitAtencionProv.val()):'0');
			formato12B.txtTotalAtencionConsRecl.val(redondeo(formato12B.txtTotalAtencionConsRecl.val(), 2));
			formato12B.txtTotalAtencionConsReclProv.val(redondeo(formato12B.txtTotalAtencionConsReclProv.val(), 2));
			$('#costoTotalAtencionConsRecl').val(formato12B.txtTotalAtencionConsRecl.val());
			$('#costoTotalAtencionConsReclProv').val(formato12B.txtTotalAtencionConsReclProv.val());
			
		
			if(emp =='EDLN' || emp =='LDS'){
				formato12B.txtTotalImpresionValeLim.val((formato12B.txtnroValesImpresoLim.val()!=null && formato12B.txtnroValesImpresoLim.length>0)?(formato12B.txtnroValesImpresoLim.val()*formato12B.txtEtndrUnitValeImpreLim.val()):'0');
			    formato12B.txtTotalRepartoValesDomiLim.val((formato12B.txtnroValesRepartidosDomiLim.val()!=null && formato12B.txtnroValesRepartidosDomiLim.length>0)?(formato12B.txtnroValesRepartidosDomiLim.val()*formato12B.txtEtndrUnitValeReparLim.val()):'0');
			    formato12B.txtTotalEntregaValDisElLim.val((formato12B.txtnroValesEntregadoDisElLim.val()!=null && formato12B.txtnroValesEntregadoDisElLim.length>0)?(formato12B.txtnroValesEntregadoDisElLim.val()*formato12B.txtEtndrUnitValDisElLim.val()):'0');
			    formato12B.txtTotalCanjeLiqValeFisLim.val((formato12B.txtnroValesFisicosCanjeadosLim.val()!=null && formato12B.txtnroValesFisicosCanjeadosLim.length>0)?(formato12B.txtnroValesFisicosCanjeadosLim.val()*formato12B.txtEtndrUnitValFiCanLim.val()):'0');
			    formato12B.txtTotalCanjeLiqValeDigLim.val((formato12B.txtnroValesDigitalCanjeadosLim.val()!=null && formato12B.txtnroValesDigitalCanjeadosLim.length>0)?(formato12B.txtnroValesDigitalCanjeadosLim.val()*formato12B.txtEtndrUnitValDgCanLim.val()):'0');
			    formato12B.txtTotalAtencionConsReclLim.val((formato12B.txtnroAtencionesLim.val()!=null && formato12B.txtnroAtencionesLim.length>0)?(formato12B.txtnroAtencionesLim.val()*formato12B.txtEtndrUnitAtencionLim.val()):'0');
			    
			    formato12B.txtTotalImpresionValeLim.val(redondeo(formato12B.txtTotalImpresionValeLim.val(), 2));
				formato12B.txtTotalRepartoValesDomiLim.val(redondeo(formato12B.txtTotalRepartoValesDomiLim.val(), 2));
				formato12B.txtTotalEntregaValDisElLim.val(redondeo(formato12B.txtTotalEntregaValDisElLim.val(), 2));
				formato12B.txtTotalCanjeLiqValeFisLim.val(redondeo(formato12B.txtTotalCanjeLiqValeFisLim.val(), 2));
				formato12B.txtTotalCanjeLiqValeDigLim.val(redondeo(formato12B.txtTotalCanjeLiqValeDigLim.val(), 2));
				formato12B.txtTotalAtencionConsReclLim.val(redondeo(formato12B.txtTotalAtencionConsReclLim.val(), 2));
				
			    
			    $('#costoTotalImpresionValeLim').val(formato12B.txtTotalImpresionValeLim.val());
			    $('#costoTotalRepartoValesDomiLim').val(formato12B.txtTotalRepartoValesDomiLim.val());
			    $('#costoTotalEntregaValDisElLim').val(formato12B.txtTotalEntregaValDisElLim.val());
			   $('#costoTotalCanjeLiqValeFisLim').val(formato12B.txtTotalCanjeLiqValeFisLim.val());
			   $('#costoTotalCanjeLiqValeDigLim').val(formato12B.txtTotalCanjeLiqValeDigLim.val());
			   $('#costoTotalAtencionConsReclLim').val(formato12B.txtTotalAtencionConsReclLim.val());
			}
			
		    formato12B.loadTotales(emp);
		},
		loadTotales : function(emp){
			
			if(emp =='EDLN' || emp =='LDS'){
				$('#porImpresionVales').val(Number($('#costoTotalImpresionVale').val())+Number($('#costoTotalImpresionValeProv').val())+Number($('#costoTotalImpresionValeLim').val()));
				$('#porRepartoDom').val(Number($('#costoTotalRepartoValesDomi').val())+Number($('#costoTotalRepartoValesDomiProv').val())+Number($('#costoTotalRepartoValesDomiLim').val()));
				$('#porEntregaValesDE').val(Number($('#costoTotalEntregaValDisEl').val())+Number($('#costoTotalEntregaValDisElProv').val())+Number($('#costoTotalEntregaValDisElLim').val()));
				$('#porValesFisicos').val(Number($('#costoTotalCanjeLiqValeFis').val())+Number($('#costoTotalCanjeLiqValeFisProv').val())+Number($('#costoTotalCanjeLiqValeFisLim').val()));
				$('#porValesDigitales').val(Number($('#costoTotalCanjeLiqValeDig').val())+Number($('#costoTotalCanjeLiqValeDigProv').val())+Number($('#costoTotalCanjeLiqValeDigLim').val()));
				$('#porAtencionReclamos').val(Number($('#costoTotalAtencionConsRecl').val())+Number($('#costoTotalAtencionConsReclProv').val())+Number($('#costoTotalAtencionConsReclLim').val()));
				$('#porGestionAdm').val(Number($('#totalGestionAdministrativa').val())+Number($('#totalGestionAdministrativaProv').val())+Number($('#totalGestionAdministrativaLim').val()));
				$('#porDesplazamientoPers').val(Number($('#totalDesplazamientoPersonal').val())+Number($('#totalDesplazamientoPersonalProv').val())+Number($('#totalDesplazamientoPersonalLim').val()));
				$('#porActividadExtra').val(Number($('#totalActividadesExtraord').val())+Number($('#totalActividadesExtraordProv').val())+Number($('#totalActividadesExtraordLim').val()));
			
			}else{
				
				$('#porImpresionVales').val(Number($('#costoTotalImpresionVale').val())+Number($('#costoTotalImpresionValeProv').val()));
				$('#porRepartoDom').val(Number($('#costoTotalRepartoValesDomi').val())+Number($('#costoTotalRepartoValesDomiProv').val()));
				$('#porEntregaValesDE').val(Number($('#costoTotalEntregaValDisEl').val())+Number($('#costoTotalEntregaValDisElProv').val()));
				$('#porValesFisicos').val(Number($('#costoTotalCanjeLiqValeFis').val())+Number($('#costoTotalCanjeLiqValeFisProv').val()));
				$('#porValesDigitales').val(Number($('#costoTotalCanjeLiqValeDig').val())+Number($('#costoTotalCanjeLiqValeDigProv').val()));
				$('#porAtencionReclamos').val(Number($('#costoTotalAtencionConsRecl').val())+Number($('#costoTotalAtencionConsReclProv').val()));
				$('#porGestionAdm').val(Number($('#totalGestionAdministrativa').val())+Number($('#totalGestionAdministrativaProv').val()));
				$('#porDesplazamientoPers').val(Number($('#totalDesplazamientoPersonal').val())+Number($('#totalDesplazamientoPersonalProv').val()));
				$('#porActividadExtra').val(Number($('#totalActividadesExtraord').val())+Number($('#totalActividadesExtraordProv').val()));
			}
			
			$('#porImpresionVales').val(redondeo($('#porImpresionVales').val(), 2));
			$('#porRepartoDom').val(redondeo($('#porRepartoDom').val(), 2));	
			$('#porEntregaValesDE').val(redondeo($('#porEntregaValesDE').val(), 2));
			$('#porValesFisicos').val(redondeo($('#porValesFisicos').val(), 2));
			$('#porValesDigitales').val(redondeo($('#porValesDigitales').val(), 2));
			$('#porAtencionReclamos').val(redondeo($('#porAtencionReclamos').val(), 2));
			$('#porGestionAdm').val(redondeo($('#porGestionAdm').val(), 2));
			$('#porDesplazamientoPers').val(redondeo($('#porDesplazamientoPers').val(), 2));
			$('#porActividadExtra').val(redondeo($('#porActividadExtra').val(), 2));
			formato12B.loadTotalReconocer();
		
		},
		loadGestion:function(idpor,idinput){
			
			 if(formato12B.cmbCodEmpresa.val()=='EDLN' || formato12B.cmbCodEmpresa.val()=='LDS'){
				 $("#"+idpor).val(Number($("#"+idinput).val())+Number($("#"+idinput+"Prov").val())+Number($("#"+idinput+"Lim").val())); 
			 }else{
				 $("#"+idpor).val(Number($("#"+idinput).val())+Number($("#"+idinput+"Prov").val()));
			 }
			$("#"+idpor).val(redondeo( $("#"+idpor).val(), 2));
			formato12B.loadTotalReconocer();
		},
		loadTotalReconocer : function(){
			$('#totalGeneralReconocer').val(Number($('#porImpresionVales').val())+Number($('#porRepartoDom').val())+Number($('#porEntregaValesDE').val())+
					Number($('#porValesFisicos').val())+Number($('#porValesDigitales').val())+Number($('#porAtencionReclamos').val())+Number($('#porGestionAdm').val())+
							Number($('#porDesplazamientoPers').val())+Number($('#porActividadExtra').val()));	
			$('#totalGeneralReconocer').val(redondeo( $('#totalGeneralReconocer').val(), 2));
		},
		loadCostoTotatByInput:function (total,nro,costo,idhidden,idstandar,idtotal){
			total.val((nro.val()!=null && nro.length>0)?(nro.val()*costo.val()):'0');
			$("#"+idhidden).val(total.val());
			 if(formato12B.cmbCodEmpresa.val()=='EDLN' || formato12B.cmbCodEmpresa.val()=='LDS'){
				 $("#"+idtotal).val(Number($("#"+idstandar).val())+Number($("#"+idstandar+"Prov").val())+Number($("#"+idstandar+"Lim").val()));
			 }else{
				 $("#"+idtotal).val(Number($("#"+idstandar).val())+Number($("#"+idstandar+"Prov").val()));
			 }
			 $("#"+idtotal).val(redondeo( $("#"+idtotal).val(), 2));
             formato12B.loadTotalReconocer();
			
			 //alert($("#"+idstandar).val()+"/"+$("#"+idstandar+"Prov").val()+"/"+$("#"+idstandar+"Lim").val());
			//alert(total.val()+"/"+nro.val()+"/"+costo.val()+"/"+idtotal+"/"+idhidden);
			
			//var new_num = num.toFixed(2);
		},
		showConfirmacion : function (emp,mes,anio,etapa,desmes,stdEnvio,std,mesEjec,desmesEjec,anioEjec,tipo) {	
			//alert(emp+"/"+mes+"/"+anio+"/"+etapa+"/"+desmes+"/"+stdEnvio+"/"+std+"/"+mesEjec+"/"+desmesEjec+"/"+anioEjec+"/"+tipo);
			var show=true;
			var msj='El formato ya fue enviado a OSINERGMIN-GART';
			switch(tipo) {
			case '0':{//vista
				show=false;
				formato12B.blockUI();
				location.href=formato12B.urlViewFormato+'&codEmpresa='+emp+'&anioPresentacion='+anio+'&mesPresentacion='+mes+'&etapa='+etapa+'&desmes='+desmes+'&tipoOperacion='+tipo+'&mesEjecucionGasto='+mesEjec+'&descMesEjec='+desmesEjec+'&anoEjecucionGasto='+anioEjec;
			}break;
			case '1':{//edit
			       if(std =='ABIERTO'){//1enviado 0=x enviar
			    	  show=false;//no muestra modal
			    	  formato12B.blockUI();
					  location.href=formato12B.urlViewFormato+'&codEmpresa='+emp+'&anioPresentacion='+anio+'&mesPresentacion='+mes+'&etapa='+etapa+'&desmes='+desmes+'&tipoOperacion='+tipo+'&mesEjecucionGasto='+mesEjec+'&descMesEjec='+desmesEjec+'&anoEjecucionGasto='+anioEjec;
			       }
			}break;
			case '3':{//eliminar
				if(std=='ABIERTO'){//1enviado 0=x enviar
			     msj='¿Está seguro que desea eliminar el registro seleccionado?';
			    }else{
			    	show=false;
			    	alert(msj);
			    }
			 }break;
			}
			
            if(show){
				formato12B.dlgConfirmacion.html(msj);
				if(tipo == '1'){
					titulo="Aviso";
					formato12B.dlgConfirmacion.dialog({ 
				     	title:titulo,
				     	height:150,
						width: 400,	
				         modal: true,
				         buttons:{
				        	 "Aceptar":function(){
				        		 $( this ).dialog( "close" );
				        	}
				         },
				         open: function(event, ui) {$(".ui-dialog-titlebar-close").show(); }
				     }).load();
				}else{
					formato12B.dlgConfirmacion.dialog({ 
				     	title:"Eliminar formato",
				     	height:150,
						width: 400,	
				         modal: true,
				         buttons:{
				        	 "Aceptar":function(){
				        		 $( this ).dialog( "close" );
				        		 formato12B.deleteFormato(emp, mes, anio, etapa, formato12B.dlgConfirmacion);
				        		 
				        	 },
				        	 "Cancelar":function(){
				        		 $( this ).dialog( "close" );
				        		
				        	 }
				         },
				         open: function(event, ui) {$(".ui-dialog-titlebar-close").show(); }
				     }).load();
					
				}
				
			}
           

		},
		showLoadFile:function(tipoFile){
			
			formato12B.divOverlay.show();
		    formato12B.dlgLoadFile.show();
		    formato12B.dlgLoadFile.css({ 
		        'left': ($(window).width() / 3 - formato12B.dlgLoadFile.width() / 3) + 'px', 
		        'top': ($(window).height()  - formato12B.dlgLoadFile.height() ) + 'px'
		    });
			
			
			
		},
		closeLoadFile : function(){
			formato12B.dlgLoadFile.hide();
			formato12B.divOverlay.hide();   
		},
		uploadFile : function(tipoFile){
			 formato12B.formNewEdit.submit(); 
		},
		showCamposOculto: function(){
			
			formato12B.divgrupoestado.css("display","block");
			formato12B.cmbCodEmpresa.prop('disabled', true);
			formato12B.cmbPeriodo.prop('disabled', true);
		
			formato12B.txtAnioEjec.prop("type","text");
			formato12B.txtAnioEjecCommand.prop("type","hidden");
			
			//formato12B.txtMesEjecCommnad.prop("type","hidden");
			//formato12B.txtMesEjec.prop("type","text");
			formato12B.cmbMesEjecucion.prop('disabled', true);
			
			
			
			
			
		},
		save : function(){	
			var form = formato12B.formNewEdit.serialize();
			jQuery.ajax({	
				url: formato12B.urlSave,
				cache : false,
				async : false,
				type: 'post',
				data : form,
				dataType : "json",
				beforeSend:function(){
					formato12B.blockUI();
				},				
				success: function(data) {
					if(data!=null && data.length>0){
						$.each(data, function (i, item) {
							
						   if(item.msg == "1"){
								if(formato12B.tpOperacion.val()== '2'){
									formato12B.tpOperacion.val('1');
									
									$("#codEmpresaHidden").val(item.codEmpresaHidden);
									$("#peridoDeclaracionHidden").val(item.peridoDeclaracionHidden);
									$("#outTxtGrupo").val(item.descGrupo);
									$("#outTxtEstado").val(item.descEstado);
									$("#txtDescGrup").val(item.descGrupo);
									$("#txtDescEst").val(item.descEstado);
									$("#anoEjecucionGasto").val(item.anoEjecucionGasto);
									$("#mesEjecucionGasto").val(item.mesEjecucionGasto);
									$("#mesPresentacion").val(item.mesPresentacion);
									$("#anoPresentacion").val(item.anoPresentacion);
									$("#etapa").val(item.etapa);
									
									formato12B.showCamposOculto();
								}else{
									formato12B.tpOperacion.val('1');
								}
								formato12B.lblMessage.html("Se registro correctamente");
								formato12B.dialogMessageGeneral.dialog("open");
								
							}else if(item.msg == "-1"){
								formato12B.lblMessage.html("El formato ya existe para esa empresa para ese periodo");
								formato12B.dialogMessageGeneral.dialog("open");
								
							}else if(item.msg == "-2"){
								formato12B.lblMessage.html("Error al obtener usuario");
								formato12B.dialogMessageGeneral.dialog("open");
								
							}else if(item.msg == "-3"){
								formato12B.lblMessage.html("Error al obtener terminal");
								formato12B.dialogMessageGeneral.dialog("open");
								
							}else if(item.msg == "-4"){
								formato12B.lblMessage.html("Error al registrar");
								formato12B.dialogMessageGeneral.dialog("open");
								
							}
						});
					}
					formato12B.unblockUI();
				},error : function(){
					alert("Error de conexión.");
					formato12B.unblockUI();
				}
		});
			
		},
		
		deleteFormato:function(emp,mes,anio,etapa,dlg){
		
			jQuery.ajax({	
				url: formato12B.urlDeleteFormato,
				type: 'post',
				data : {"codEmpresa":emp,
					"mespres":mes,
					"aniopres":anio,
					"etapa":etapa,
				},
				dataType : "text",
				beforeSend:function(){
					formato12B.blockUI();
				},				
				success: function(data) {					
					if(data == '1' ){
						//formato12B.btnBuscar.trigger('click');
						formato12B.lblMessageInicial.html("Se Elimino correctamente");
						formato12B.dialogMessageGeneralInicial.dialog("open");
						
						
					}else if(data == '-1'){
						formato12B.lblMessageInicial.html("Error al eliminar");
						formato12B.dialogMessageGeneralInicial.dialog("open");
						
					}
					formato12B.searchFormato();
					//formato12B.btnBuscar.trigger('click');
					formato12B.unblockUI();
				},error : function(){
					alert("Error de conexión.");
					formato12B.unblockUI();
				}
		});
		},
		viewReporte:function(tipo){
		
			jQuery.ajax({
				url : formato12B.urlViewReporte+'&'+formato12B.formNewEdit.serialize(),
				type : 'post',
				
				dataType : 'json',
				data : {
					"nombreReporte": 'formato12B',
					"nombreArchivo": 'formato12B',
					"tipoReporte": tipo
					
				},
				success : function(gridData) {
					formato12B.verReporte();
				},error : function(){
					alert("Error de conexión.");
					formato12B.initBlockUI();
				}
			});
		},
        
		verReporte : function(){
			window.open('<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ViewReport")%>','_newtab');
		},

        viewReporteEnvio:function(){
			
		},
		
		buildGridsObservacion : function () {	
			formato12B.tablaObservacion.jqGrid({
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
					pager: formato12B.paginadoObservacion,
				    viewrecords: true,
				   	//caption: "Formatos",
				    sortorder: "asc"
		  	});
			formato12B.tablaObservacion.jqGrid('navGrid',formato12B.paginadoObservacion,{add:false,edit:false,del:false,search: false,refresh: false});	
			formato12B.tablaObservacion.jqGrid('navButtonAdd',formato12B.paginadoObservacion,{
			       caption:"Exportar a Excel",
			       buttonicon: "ui-icon-bookmark",
			       onClickButton : function () {
			           location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';
			       } 
			}); 
			formato12B.tablaObservacion.jqGrid('navButtonAdd',formato12B.paginadoObservacion,{
			       caption:"Exportar a Pdf",
			       buttonicon: "ui-icon-bookmark",
			       onClickButton : function () {
			    	   formato12B.showReporteValidacion();
			       } 
			});
		},
		showValidacion : function(){
			jQuery.ajax({
				url: formato12B.urlValidacion+'&'+formato12B.formNewEdit.serialize(),
				type : 'post',
				dataType : 'json',
				contentType: "application/json; charset=utf-8",
                async : false,
					
				success : function(data) {
					if( data!=null ){
						formato12B.dialogObservacion.dialog("open");
						formato12B.tablaObservacion.clearGridData(true);
						formato12B.tablaObservacion.jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
						formato12B.tablaObservacion[0].refreshIndex();
						formato12B.initBlockUI();
					}

				},error : function(){
					alert("Error de conexión.");
					formato12B.initBlockUI();
				}
			});
		},
		showReporteValidacion : function(){
		jQuery.ajax({
				url: formato12B.urlReporteValidacion+'&'+formato12B.formNewEdit.serialize(),
				type : 'post',
				dataType : 'json',
				data : {
					<portlet:namespace />nombreReporte: 'validacion',
					<portlet:namespace />nombreArchivo: 'validacion',
					<portlet:namespace />tipoArchivo: '0'//PDF
				},
				success : function(gridData) {
					formato12B.verReporte();
				},error : function(){
					alert("Error de conexión.");
					formato12B.initBlockUI();
				}
			});
		},
		confirmarEnvioDefinitivo : function(){	
			var addhtml='¿Está seguro que desea realizar el envío definitivo?';
			formato12B.lblConfirmEnvioContent.html(addhtml);
			formato12B.dialogConfirmEnvio.dialog("open");
		},
		envioDefinitivo : function(){
			jQuery.ajax({
				url: formato12B.urlEnvioDefinitivo+'&'+formato12B.formNewEdit.serialize(),
				type : 'post',
				dataType : 'json',
				data : {
					<portlet:namespace />nombreReporte: 'formato12B',
					<portlet:namespace />nombreArchivo: 'formato12B',
					<portlet:namespace />tipoArchivo: '0'//PDF
				},
				success : function(gridData) {
					var addhtml='Se realizó el envío satisfactoriamente al correo '+formato12B.emailConfigured;
					formato12B.lblMessageReportContent.html(addhtml);
					formato12B.dialogMessageReport.dialog("open");
					formato12B.initBlockUI();
				},error : function(){
					alert("Error de conexión.");
					formato12B.initBlockUI();
				}
			});
		},
		showActaEnvio : function(){
			var estado = $('#txtDescEst').val();	
			if(estado=='Enviado'){
				jQuery.ajax({
					url: formato12B.urlReporteActaEnvio+'&'+formato12B.formNewEdit.serialize(),
					type : 'post',
					dataType : 'json',
					data : {
						<portlet:namespace />tipoArchivo: '0'//PDF
					},
					success : function(gridData) {
						formato12B.verReporte();
					},error : function(){
						alert("Error de conexión.");
						formato12B.initBlockUI();
					}
				});
			}else{
				alert("Primero debe realizar el envío definitivo");
			}
		},
		updateStyleClassInput:function (tipo,emp){
			
			formato12B.txtnroValesImpreso.addClass("fise-editable");
			formato12B.txtnroValesImpresoProv.addClass("fise-editable");
			formato12B.txtnroValesRepartidosDomi.addClass("fise-editable");
			formato12B.txtnroValesRepartidosDomiProv.addClass("fise-editable");
			formato12B.txtnroValesEntregadoDisEl.addClass("fise-editable");
			formato12B.txtnroValesEntregadoDisElProv.addClass("fise-editable");
			formato12B.txtnroValesFisicosCanjeados.addClass("fise-editable");
			formato12B.txtnroValesFisicosCanjeadosProv.addClass("fise-editable");
			formato12B.txtnroValesDigitalCanjeados.addClass("fise-editable");
			formato12B.txtnroValesDigitalCanjeadosProv.addClass("fise-editable");
			formato12B.txtnroAtenciones.addClass("fise-editable");
			formato12B.txtnroAtencionesProv.addClass("fise-editable");
			formato12B.txtTotalGestionAdministrativa.addClass("fise-editable");
			formato12B.txtTotalGestionAdministrativaProv.addClass("fise-editable");
			formato12B.txtTotalDesplazamientoPersonal.addClass("fise-editable");
			formato12B.txtTotalDesplazamientoPersonalProv.addClass("fise-editable");
			formato12B.txtTotalActividadesExtraord.addClass("fise-editable");
			formato12B.txtTotalActividadesExtraordProv.addClass("fise-editable");
			
		if(emp=='EDLN' || emp=='LDS'){
			formato12B.txtnroValesImpresoLim.addClass("fise-editable");
			formato12B.txtnroValesRepartidosDomiLim.addClass("fise-editable");
			formato12B.txtnroValesEntregadoDisElLim.addClass("fise-editable");
			formato12B.txtnroValesFisicosCanjeadosLim.addClass("fise-editable");
			formato12B.txtnroValesDigitalCanjeadosLim.addClass("fise-editable");
			formato12B.txtnroAtencionesLim.addClass("fise-editable");
			formato12B.txtTotalGestionAdministrativaLim.addClass("fise-editable");
			formato12B.txtTotalDesplazamientoPersonalLim.addClass("fise-editable");
			formato12B.txtTotalActividadesExtraordLim.addClass("fise-editable");
		}
			
		},initDialogsInit : function(){	
				formato12B.dialogMessageGeneralInicial.dialog({
				modal: true,
				height: 120,
				width: 400,
				autoOpen: false,
				buttons: {
					Cerrar: function() {
						$( this ).dialog("close");
					}
				}
			});
		},
		mostrarReporteEnvioDefinitivo : function(){
			jQuery.ajax({
				url: formato12B.urlReporteEnvioDefinitivo+'&'+formato12B.formNewEdit.serialize(),
				type : 'post',
				dataType : 'json',
				data : {
					<portlet:namespace />tipoArchivo: '2'//PDF+concatenado
				},
				success : function(gridData) {
					formato12B.verReporte();
				},error : function(){
					alert("Error de conexión.");
					formato12B.initBlockUI();
				}
			});
		},
		initDialogs : function(){	
			formato12B.dialogObservacion.dialog({
				modal: true,
				width: 700,
				autoOpen: false,
				buttons: {
					Cerrar: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			formato12B.dialogMessageReport.dialog({
				modal: true,
				autoOpen: false,
				buttons: {
					'Imprimir Pdf': function() {
						formato12B.mostrarReporteEnvioDefinitivo();
						$( this ).dialog("close");
						formato12B.urlRetornar;
						formato12B.btnBuscar.trigger('click');
					},
					Ok: function() {
						$( this ).dialog("close");
						formato12B.urlRetornar;
						formato12B.btnBuscar.trigger('click');
					}
				},
				close: function(event,ui){
					formato12B.urlRetornar;
					formato12B.btnBuscar.trigger('click');
		       	}
			});
			
			formato12B.dialogConfirmEnvio.dialog({
				modal: true,
				height: 200,
				width: 400,			
				autoOpen: false,
				buttons: {
					"Si": function() {
						formato12B.envioDefinitivo();
						$( this ).dialog("close");
					},
					"No": function() {				
						$( this ).dialog("close");
					}
				}
			});
			
			formato12B.dialogMessageGeneral.dialog({
				modal: true,
				height: 120,
				width: 400,
				autoOpen: false,
				buttons: {
					Cerrar: function() {
						$( this ).dialog("close");
					}
				}
			});
		},
		validateInputTextNumber:function(id){
			$("#"+id).keyup(function () {
			    if (!this.value.match(/^([0-9]{0,10})$/)) {
			        this.value = this.value.replace(/[^0-9]/g, '').substring(0,10);
			    }
			});	
		},
		validateInputTextDecimal:function(id){
			$("#"+id).keyup(function (event) {
			   $(this).val($(this).val().replace(/[^0-9\.]/g,''));
		          if ((event.which != 46 || $(this).val().indexOf('.') != -1) && (event.which < 48 || event.which > 57)) {
		                event.preventDefault();
		            };
			});
			
		}
		
	};
</script>