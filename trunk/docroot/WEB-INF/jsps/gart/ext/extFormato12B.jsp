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
		txtAnioInicio:null,
		txtAnioFin:null,
		
		tpOperacion:null,
		formNewEdit: null,	
		cmbCodEmpresa:null,
		cmbPeriodo:null,
		txtEmpresa:null,
		txtPeriodo:null,
		divgrupoestado:null,
		
		dialogMessageConfirm:null,
		dialogMessageConfirmContent:null,
		dialogMessageInfo:null,
		dialogMessageInfoContent:null,
		dialogMessageWarning:null,
		dialogMessageWarningContent:null,
		dialogMessageError:null,
		dialogMessageErrorContent:null,
		
		urlLoadPeriodo:null,
		urlLoadCostosUnitarios:null,
		dlgLoadFile:null,
		divOverlay:null,
		btnShowLoadExcel:null,
        btnUploadExcel:null,
        btnShowLoadTxt:null,
        txtTypeFile:null,
       // btnUploadTxt:null,
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
        
        dialogMessageInfoDetalle:null,
		dialogMessageInfoDetalleContent:null,
		dialogMessageWarningDetalle:null,
		dialogMessageWarningDetalleContent:null,
		dialogMessageErrorDetalle:null,
		dialogMessageErrorDetalleContent:null,
        
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
			this.txtAnioInicio=$("#anioInicio");
			this.txtAnioFin=$("#anioFin");
			
			this.dialogMessageConfirm=$("#<portlet:namespace/>dialog-message-confirm");
			this.dialogMessageConfirmContent=$("#<portlet:namespace/>dialog-message-confirm-content");
			this.dialogMessageInfo=$("#<portlet:namespace/>dialog-message-info");
			this.dialogMessageInfoContent=$("#<portlet:namespace/>dialog-message-info-content");
			this.dialogMessageWarning=$("#<portlet:namespace/>dialog-message-warning");
			this.dialogMessageWarningContent=$("#<portlet:namespace/>dialog-message-warning-content");
			this.dialogMessageError=$("#<portlet:namespace/>dialog-message-error");
			this.dialogMessageErrorContent=$("#<portlet:namespace/>dialog-message-error-content");
			
			formato12B.initDialogsInit();
			this.urlViewFormato=urlView;
			formato12B.btnBuscar.click(function() {
				formato12B.searchFormato();
			});
			
			formato12B.buildGridsBusqueda();
			formato12B.btnBuscar.trigger('click');
			
			formato12B.btnNew.click(function() {
				formato12B.blockUI();
				location.href=urlNew+'&'+formato12B.formBusqueda.serialize();
				
				//formato13A.formNuevo.attr('action',urlAnadirFormato+'&codEmpresa='+formato13A.codEmpresa.val()+'&peridoDeclaracion='+formato13A.peridoDeclaracion.val()+'&strip=0').removeAttr('enctype').submit();
			});
			
		}, 
		
		validateInputAnio : function(inicio,fin){//validateInputAnio
			
			
			 if((inicio.val().length>0 && inicio.val().length<4 )|| (fin.val().length>0 && fin.val().length<4)){
	    		 //formato12B.lblMessageInicial.html("El año debe contener 4 dígitos");
				 //formato12B.dialogMessageGeneralInicial.dialog("open");
				 formato12B.dialogMessageWarningContent.html("El año debe contener 4 dígitos");
				 formato12B.dialogMessageWarning.dialog("open");
				 return false; 
	    		
	    	 }else if(fin.val().length>0){
	    		 if(parseFloat(inicio.val().length>0?inicio.val():'0')>parseFloat(fin.val())){
	    			
	    			 //formato12B.lblMessageInicial.html("El año final debe ser mayor o igual al inicial ");
					 //formato12B.dialogMessageGeneralInicial.dialog("open"); 
					 formato12B.dialogMessageWarningContent.html("El año final debe ser mayor o igual al inicial");
					 formato12B.dialogMessageWarning.dialog("open");
					 return false; 
	    		 }
	    		 
	    	 }
		
			 if(parseFloat(inicio.val().length>0?inicio.val():'0')==parseFloat(fin.val().length>0?fin.val():'0')){
				 var mesinicio=$("#mesInicio").val();
					var mesfin=$("#mesFin").val();
				
					if(parseFloat(mesfin)<parseFloat(mesinicio)){
						 //formato12B.lblMessageInicial.html("El mes final debe ser posterior al mes inicial");
						 //formato12B.dialogMessageGeneralInicial.dialog("open"); 
						 formato12B.dialogMessageWarningContent.html("El mes final debe ser posterior al mes inicial");
						 formato12B.dialogMessageWarning.dialog("open");
						 return false; 
					}
	    		
	    		
	    	 }
	  	 return true; 
     },
     buildGridsBusqueda : function () {	
			formato12B.tablaBusqueda.jqGrid({
				   datatype: "local",
			       colNames: ['Dist. Eléct.','Año Decl.','Mes Decl.','Etapa','Grupo de Información','Estado','Visualizar','Editar','Eliminar','','','','','','',''],
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
					   	caption: "Resultado(s) de la búsqueda",
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
			
			if(formato12B.validateInputAnio(formato12B.txtAnioInicio,formato12B.txtAnioFin)){
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
			}
            
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
			
			this.btnShowLoadTxt=$('#<portlet:namespace/>cargaTxt');
			this.txtTypeFile=$("#txtTypeFile");
			//this.btnUploadTxt=$('#btnUploadTxt');
			
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
         
         this.dialogMessageInfoDetalle=$("#<portlet:namespace/>dialog-message-info-detalle");
 		 this.dialogMessageInfoDetalleContent=$("#<portlet:namespace/>dialog-message-info-content-detalle");
 		 this.dialogMessageWarningDetalle=$("#<portlet:namespace/>dialog-message-warning-detalle");
 		 this.dialogMessageWarningDetalleContent=$("#<portlet:namespace/>dialog-message-warning-content-detalle");
 		 this.dialogMessageErrorDetalle=$("#<portlet:namespace/>dialog-message-error-detalle");
		 this.dialogMessageErrorDetalleContent=$("#<portlet:namespace/>dialog-message-error-content-detalle");
         
          formato12B.initDialogs();
 		
            formato12B.btnBack.click(function(){
				formato12B.blockUI();
				location.href=urlBack;
			});
            formato12B.showHiddenButtons(formato12B.tpOperacion.val(),'1');
           
			
            
			if(formato12B.tpOperacion.val()=='0'){
			 	formato12B.cmbPeriodo.append($("<option></option>").attr("value",$("#peridoDeclaracionHidden").val()).text(formato12B.txtPeriodo.val())); 
				formato12B.showCamposOculto();
				formato12B.btnReportePdf.click(function() {formato12B.viewReporte('0');});
				formato12B.btnReporteExpExcel.click(function() {formato12B.viewReporte('1');});
				formato12B.btnReporteActaEnvio.click(function() {formato12B.showActaEnvio();});
				formato12B.loadTotales($("#codEmpresaHidden").val());
				
			}else if(formato12B.tpOperacion.val()=='1'){
				formato12B.cmbPeriodo.append($("<option></option>").attr("value",$("#peridoDeclaracionHidden").val()).text(formato12B.txtPeriodo.val())); 
				formato12B.showCamposOculto();
				formato12B.eventButtons(formato12B.tpOperacion.val());
				formato12B.updateStyleClassInput(formato12B.tpOperacion.val(),formato12B.cmbCodEmpresa.val().trim());
				formato12B.buildGridsObservacion();
				formato12B.loadTotales($("#codEmpresaHidden").val());
			
			}else if(formato12B.tpOperacion.val()=='2'){
				formato12B.cmbCodEmpresa.change(function(){
					formato12B.updateStyleClassInput(formato12B.tpOperacion.val(),formato12B.cmbCodEmpresa.val().trim());
					formato12B.clearCampoLima();
					$.when(formato12B.loadPeriodoDeclaracion(formato12B.tpOperacion.val())).then(formato12B.loadCostosUnitarios());
					formato12B.showHiddenButtons(formato12B.tpOperacion.val(),'2');
				});
				formato12B.cmbCodEmpresa.trigger('change');
				formato12B.cmbPeriodo.change(function(){
					formato12B.updateStyleClassInput(formato12B.tpOperacion.val(),formato12B.cmbCodEmpresa.val().trim());
					formato12B.clearCampoLima();
					formato12B.loadCostosUnitarios();
					formato12B.showHiddenButtons(formato12B.tpOperacion.val(),'2');
				});
				formato12B.eventButtons(formato12B.tpOperacion.val());
			//	formato12B.updateStyleClassInput(formato12B.tpOperacion.val(),formato12B.cmbCodEmpresa.val().trim());
				
				formato12B.loadTotales(formato12B.cmbCodEmpresa.val());
			}
			
		
			
		},
		eventButtons:function (tipo){
			formato12B.divLoadFile.css("display","block");
			formato12B.btnGuardar.css("display","block");
			
			formato12B.btnShowLoadExcel.click(function() {formato12B.showLoadFile('1');});//excel
			formato12B.btnShowLoadTxt.click(function() {formato12B.showLoadFile('2');});//txt
			formato12B.btnUploadExcel.click(function() {formato12B.uploadFile(formato12B.txtTypeFile.val());});
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
	   showHiddenButtons:function(tipo,isclear){
			
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
				
				if(formato12B.cmbCodEmpresa.val().trim()=='EDLN' || formato12B.cmbCodEmpresa.val().trim()=='LDS'){
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
				
				
					var impre=formato12B.txtnroValesImpresoLim.val();
					 var repar=formato12B.txtnroValesRepartidosDomiLim.val();
					 var entre=formato12B.txtnroValesEntregadoDisElLim.val();
					 var fisic=formato12B.txtnroValesFisicosCanjeadosLim.val();
					 var digi=formato12B.txtnroValesDigitalCanjeadosLim.val();
					 var aten=formato12B.txtnroAtencionesLim.val();
					 var gestion=formato12B.txtTotalGestionAdministrativaLim.val();
					 var despl=formato12B.txtTotalDesplazamientoPersonalLim.val();
					 var act=formato12B.txtTotalActividadesExtraordLim.val();
					 
					formato12B.txtnroValesImpresoLim.val(impre.length>0?impre:'');
					formato12B.txtnroValesRepartidosDomiLim.val(repar.length>0?repar:'');
					formato12B.txtnroValesEntregadoDisElLim.val(entre.length>0?entre:'');
					formato12B.txtnroValesFisicosCanjeadosLim.val(fisic.length>0?fisic:'');
					formato12B.txtnroValesDigitalCanjeadosLim.val(digi.length>0?digi:'');
					formato12B.txtnroAtencionesLim.val(aten.length>0?aten:'');
					formato12B.txtTotalGestionAdministrativaLim.val(gestion.length>0?gestion:'');
					formato12B.txtTotalDesplazamientoPersonalLim.val(despl.length>0?despl:'');
					formato12B.txtTotalActividadesExtraordLim.val(act.length>0?act:'');
					
					
					formato12B.txtEtndrUnitValeImpreLim.val(formato12B.txtEtndrUnitValeImpreLim.val().length>0?formato12B.txtEtndrUnitValeImpreLim.val():'');
					 $('#costoEstandarUnitValeImpreLim').val(formato12B.txtEtndrUnitValeImpreLim.val());
					 
					 formato12B.txtEtndrUnitValeReparLim.val(formato12B.txtEtndrUnitValeReparLim.val().length>0?formato12B.txtEtndrUnitValeReparLim.val():'');
					 $('#costoEstandarUnitValeReparLim').val(formato12B.txtEtndrUnitValeReparLim.val());
					 
					 formato12B.txtEtndrUnitValDisElLim.val(formato12B.txtEtndrUnitValDisElLim.val().length>0?formato12B.txtEtndrUnitValDisElLim.val():'');
					 $('#costoEstandarUnitValDisElLim').val(formato12B.txtEtndrUnitValDisElLim.val());
					 
					 formato12B.txtEtndrUnitValFiCanLim.val(formato12B.txtEtndrUnitValFiCanLim.val().length>0?formato12B.txtEtndrUnitValFiCanLim.val():'');
					 $('#costoEstandarUnitValFiCanLim').val(formato12B.txtEtndrUnitValFiCanLim.val());
					 
					 formato12B.txtEtndrUnitValDgCanLim.val(formato12B.txtEtndrUnitValDgCanLim.val().length>0?formato12B.txtEtndrUnitValDgCanLim.val():'');
					 $('#costoEstandarUnitValDgCanLim').val(formato12B.txtEtndrUnitValDgCanLim.val());
					 
					 formato12B.txtEtndrUnitAtencionLim.val(formato12B.txtEtndrUnitAtencionLim.val().length>0?formato12B.txtEtndrUnitAtencionLim.val():'');
					 $('#costoEstandarUnitAtencionLim').val(formato12B.txtEtndrUnitAtencionLim.val());
				

			
				
			}
			
			
		},
		clearCampoLima :function(){
			
			formato12B.txtnroValesImpresoLim.val('');
			formato12B.txtnroValesRepartidosDomiLim.val('');
			formato12B.txtnroValesEntregadoDisElLim.val('');
			formato12B.txtnroValesFisicosCanjeadosLim.val('');
			formato12B.txtnroValesDigitalCanjeadosLim.val('');
			formato12B.txtnroAtencionesLim.val('');
			formato12B.txtTotalGestionAdministrativaLim.val('');
			formato12B.txtTotalDesplazamientoPersonalLim.val('');
			formato12B.txtTotalActividadesExtraordLim.val('');
			
			
			formato12B.txtEtndrUnitValeImpreLim.val('');
			 $('#costoEstandarUnitValeImpreLim').val(formato12B.txtEtndrUnitValeImpreLim.val());
			 
			 formato12B.txtEtndrUnitValeReparLim.val('');
			 $('#costoEstandarUnitValeReparLim').val(formato12B.txtEtndrUnitValeReparLim.val());
			 
			 formato12B.txtEtndrUnitValDisElLim.val('');
			 $('#costoEstandarUnitValDisElLim').val(formato12B.txtEtndrUnitValDisElLim.val());
			 
			 formato12B.txtEtndrUnitValFiCanLim.val('');
			 $('#costoEstandarUnitValFiCanLim').val(formato12B.txtEtndrUnitValFiCanLim.val());
			 
			 formato12B.txtEtndrUnitValDgCanLim.val('');
			 $('#costoEstandarUnitValDgCanLim').val(formato12B.txtEtndrUnitValDgCanLim.val());
			 
			 formato12B.txtEtndrUnitAtencionLim.val('');
			 $('#costoEstandarUnitAtencionLim').val(formato12B.txtEtndrUnitAtencionLim.val());
			 
			
			 formato12B.txtTotalImpresionValeLim.val('');
			 formato12B.txtTotalRepartoValesDomiLim.val('');	
			 formato12B.txtTotalEntregaValDisElLim.val('');
			 formato12B.txtTotalCanjeLiqValeFisLim.val('');
			 formato12B.txtTotalCanjeLiqValeDigLim.val('');
			 formato12B.txtTotalAtencionConsReclLim.val('');
			 $('#totalGestionAdministrativaLim').val('');
	$('#totalDesplazamientoPersonalLim').val('');
	$('#totalActividadesExtraordLim').val('');

			 
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
			var anioPres;
			var mesPres;
			if(periodo!=null && periodo.length>0){
				 anioPres=periodo.substring(0,4);
				 mesPres=periodo.substring(4,6);
			}
			
			
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
					
					 if(item.codEmpresa.trim() == 'EDLN' || item.codEmpresa.trim() == 'LDS'){
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
					 				
					 
					 if(formato12B.cmbCodEmpresa.val().trim()=='EDLN' || formato12B.cmbCodEmpresa.val().trim()=='LDS'){
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
					
					 
					//formato12B.lblMessage.html("No existe costos estándares establecidos en el Formato 14B para la Distribuidora Eléctrica y Periodo a declara seleccionado");
					//formato12B.dialogMessageGeneral.dialog("open");
					formato12B.dialogMessageInfoDetalleContent.html("No existe costos estándares establecidos en el Formato 14B para la Distribuidora Eléctrica y Periodo a declara seleccionado");
					formato12B.dialogMessageInfoDetalle.dialog("open");
			}
			formato12B.loadCostoTotal(formato12B.cmbCodEmpresa.val());
			
		},
		loadCostoTotal:function(emp){
			formato12B.txtTotalImpresionVale.val((formato12B.txtnroValesImpreso.val()!=null && formato12B.txtnroValesImpreso.length>0)?(formato12B.txtnroValesImpreso.val()*formato12B.txtEtndrUnitValeImpre.val()):'0.00');
			formato12B.txtTotalImpresionValeProv.val((formato12B.txtnroValesImpresoProv.val()!=null && formato12B.txtnroValesImpresoProv.length>0)?(formato12B.txtnroValesImpresoProv.val()*formato12B.txtEtndrUnitValeImpreProv.val()):'0.00');
			formato12B.txtTotalImpresionVale.val(parseFloat(formato12B.txtTotalImpresionVale.val()).toFixed(2));
			formato12B.txtTotalImpresionValeProv.val(parseFloat(formato12B.txtTotalImpresionValeProv.val()).toFixed(2));
			$('#costoTotalImpresionVale').val(formato12B.txtTotalImpresionVale.val());
			$('#costoTotalImpresionValeProv').val(formato12B.txtTotalImpresionValeProv.val());
		
			formato12B.txtTotalRepartoValesDomi.val((formato12B.txtnroValesRepartidosDomi.val()!=null && formato12B.txtnroValesRepartidosDomi.length>0)?(formato12B.txtnroValesRepartidosDomi.val()*formato12B.txtEtndrUnitValeRepar.val()):'0.00');
			formato12B.txtTotalRepartoValesDomiProv.val((formato12B.txtnroValesRepartidosDomiProv.val()!=null && formato12B.txtnroValesRepartidosDomiProv.length>0)?(formato12B.txtnroValesRepartidosDomiProv.val()*formato12B.txtEtndrUnitValeReparProv.val()):'0.00');
			formato12B.txtTotalRepartoValesDomi.val(parseFloat(formato12B.txtTotalRepartoValesDomi.val()).toFixed(2));
			formato12B.txtTotalRepartoValesDomiProv.val(parseFloat(formato12B.txtTotalRepartoValesDomiProv.val()).toFixed(2));
			$('#costoTotalRepartoValesDomi').val(formato12B.txtTotalRepartoValesDomi.val());
			$('#costoTotalRepartoValesDomiProv').val(formato12B.txtTotalRepartoValesDomiProv.val());
			
		    formato12B.txtTotalEntregaValDisEl.val((formato12B.txtnroValesEntregadoDisEl.val()!=null && formato12B.txtnroValesEntregadoDisEl.length>0)?(formato12B.txtnroValesEntregadoDisEl.val()*formato12B.txtEtndrUnitValDisEl.val()):'0.00');
			formato12B.txtTotalEntregaValDisElProv.val((formato12B.txtnroValesEntregadoDisElProv.val()!=null && formato12B.txtnroValesEntregadoDisElProv.length>0)?(formato12B.txtnroValesEntregadoDisElProv.val()*formato12B.txtEtndrUnitValDisElProv.val()):'0.00');
			formato12B.txtTotalEntregaValDisEl.val(parseFloat(formato12B.txtTotalEntregaValDisEl.val()).toFixed(2));
			formato12B.txtTotalEntregaValDisElProv.val(parseFloat(formato12B.txtTotalEntregaValDisElProv.val()).toFixed(2));
			$('#costoTotalEntregaValDisEl').val(formato12B.txtTotalEntregaValDisEl.val());
			$('#costoTotalEntregaValDisElProv').val(formato12B.txtTotalEntregaValDisElProv.val());
			
		    formato12B.txtTotalCanjeLiqValeFis.val((formato12B.txtnroValesFisicosCanjeados.val()!=null && formato12B.txtnroValesFisicosCanjeados.length>0)?(formato12B.txtnroValesFisicosCanjeados.val()*formato12B.txtEtndrUnitValFiCan.val()):'0.00');
			formato12B.txtTotalCanjeLiqValeFisProv.val((formato12B.txtnroValesFisicosCanjeadosProv.val()!=null && formato12B.txtnroValesFisicosCanjeadosProv.length>0)?(formato12B.txtnroValesFisicosCanjeadosProv.val()*formato12B.txtEtndrUnitValFiCanProv.val()):'0.00');
			formato12B.txtTotalCanjeLiqValeFis.val(parseFloat(formato12B.txtTotalCanjeLiqValeFis.val()).toFixed(2));
			formato12B.txtTotalCanjeLiqValeFisProv.val(parseFloat(formato12B.txtTotalCanjeLiqValeFisProv.val()).toFixed(2));
			$('#costoTotalCanjeLiqValeFis').val(formato12B.txtTotalCanjeLiqValeFis.val());
			$('#costoTotalCanjeLiqValeFisProv').val(formato12B.txtTotalCanjeLiqValeFisProv.val());
			
			formato12B.txtTotalCanjeLiqValeDig.val((formato12B.txtnroValesDigitalCanjeados.val()!=null && formato12B.txtnroValesDigitalCanjeados.length>0)?(formato12B.txtnroValesDigitalCanjeados.val()*formato12B.txtEtndrUnitValDgCan.val()):'0');
			formato12B.txtTotalCanjeLiqValeDigProv.val((formato12B.txtnroValesDigitalCanjeadosProv.val()!=null && formato12B.txtnroValesDigitalCanjeadosProv.length>0)?(formato12B.txtnroValesDigitalCanjeadosProv.val()*formato12B.txtEtndrUnitValDgCanProv.val()):'0.00');
			formato12B.txtTotalCanjeLiqValeDig.val(parseFloat(formato12B.txtTotalCanjeLiqValeDig.val()).toFixed(2));
			formato12B.txtTotalCanjeLiqValeDigProv.val(parseFloat(formato12B.txtTotalCanjeLiqValeDigProv.val()).toFixed(2));
			$('#costoTotalCanjeLiqValeDig').val(formato12B.txtTotalCanjeLiqValeDig.val());
			$('#costoTotalCanjeLiqValeDigProv').val(formato12B.txtTotalCanjeLiqValeDigProv.val());
			
			formato12B.txtTotalAtencionConsRecl.val((formato12B.txtnroAtenciones.val()!=null && formato12B.txtnroAtenciones.length>0)?(formato12B.txtnroAtenciones.val()*formato12B.txtEtndrUnitAtencion.val()):'0.00');
			formato12B.txtTotalAtencionConsReclProv.val((formato12B.txtnroAtencionesProv.val()!=null && formato12B.txtnroAtencionesProv.length>0)?(formato12B.txtnroAtencionesProv.val()*formato12B.txtEtndrUnitAtencionProv.val()):'0.00');
			formato12B.txtTotalAtencionConsRecl.val(parseFloat(formato12B.txtTotalAtencionConsRecl.val()).toFixed(2));
			formato12B.txtTotalAtencionConsReclProv.val(parseFloat(formato12B.txtTotalAtencionConsReclProv.val()).toFixed(2));
			$('#costoTotalAtencionConsRecl').val(formato12B.txtTotalAtencionConsRecl.val());
			$('#costoTotalAtencionConsReclProv').val(formato12B.txtTotalAtencionConsReclProv.val());
			
		
			if(emp.trim() =='EDLN' || emp.trim() =='LDS'){
				formato12B.txtTotalImpresionValeLim.val((formato12B.txtnroValesImpresoLim.val()!=null && formato12B.txtnroValesImpresoLim.length>0)?(formato12B.txtnroValesImpresoLim.val()*formato12B.txtEtndrUnitValeImpreLim.val()):'0.00');
			    formato12B.txtTotalRepartoValesDomiLim.val((formato12B.txtnroValesRepartidosDomiLim.val()!=null && formato12B.txtnroValesRepartidosDomiLim.length>0)?(formato12B.txtnroValesRepartidosDomiLim.val()*formato12B.txtEtndrUnitValeReparLim.val()):'0.00');
			    formato12B.txtTotalEntregaValDisElLim.val((formato12B.txtnroValesEntregadoDisElLim.val()!=null && formato12B.txtnroValesEntregadoDisElLim.length>0)?(formato12B.txtnroValesEntregadoDisElLim.val()*formato12B.txtEtndrUnitValDisElLim.val()):'0.00');
			    formato12B.txtTotalCanjeLiqValeFisLim.val((formato12B.txtnroValesFisicosCanjeadosLim.val()!=null && formato12B.txtnroValesFisicosCanjeadosLim.length>0)?(formato12B.txtnroValesFisicosCanjeadosLim.val()*formato12B.txtEtndrUnitValFiCanLim.val()):'0.00');
			    formato12B.txtTotalCanjeLiqValeDigLim.val((formato12B.txtnroValesDigitalCanjeadosLim.val()!=null && formato12B.txtnroValesDigitalCanjeadosLim.length>0)?(formato12B.txtnroValesDigitalCanjeadosLim.val()*formato12B.txtEtndrUnitValDgCanLim.val()):'0.00');
			    formato12B.txtTotalAtencionConsReclLim.val((formato12B.txtnroAtencionesLim.val()!=null && formato12B.txtnroAtencionesLim.length>0)?(formato12B.txtnroAtencionesLim.val()*formato12B.txtEtndrUnitAtencionLim.val()):'0.00');
			    
			    formato12B.txtTotalImpresionValeLim.val(parseFloat(formato12B.txtTotalImpresionValeLim.val()).toFixed(2));
				formato12B.txtTotalRepartoValesDomiLim.val(parseFloat(formato12B.txtTotalRepartoValesDomiLim.val()).toFixed(2));
				formato12B.txtTotalEntregaValDisElLim.val(parseFloat(formato12B.txtTotalEntregaValDisElLim.val()).toFixed(2));
				formato12B.txtTotalCanjeLiqValeFisLim.val(parseFloat(formato12B.txtTotalCanjeLiqValeFisLim.val()).toFixed(2));
				formato12B.txtTotalCanjeLiqValeDigLim.val(parseFloat(formato12B.txtTotalCanjeLiqValeDigLim.val()).toFixed(2));
				formato12B.txtTotalAtencionConsReclLim.val(parseFloat(formato12B.txtTotalAtencionConsReclLim.val()).toFixed(2));
				
			    
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
			 
			if(emp.trim() =='EDLN' || emp.trim() =='LDS'){
				$('#porImpresionVales').val(parseFloat($('#costoTotalImpresionVale').val())+parseFloat($('#costoTotalImpresionValeProv').val())+parseFloat($('#costoTotalImpresionValeLim').val()));
				$('#porRepartoDom').val(parseFloat($('#costoTotalRepartoValesDomi').val())+parseFloat($('#costoTotalRepartoValesDomiProv').val())+parseFloat($('#costoTotalRepartoValesDomiLim').val()));
				$('#porEntregaValesDE').val(parseFloat($('#costoTotalEntregaValDisEl').val())+parseFloat($('#costoTotalEntregaValDisElProv').val())+parseFloat($('#costoTotalEntregaValDisElLim').val()));
				$('#porValesFisicos').val(parseFloat($('#costoTotalCanjeLiqValeFis').val())+parseFloat($('#costoTotalCanjeLiqValeFisProv').val())+parseFloat($('#costoTotalCanjeLiqValeFisLim').val()));
				$('#porValesDigitales').val(parseFloat($('#costoTotalCanjeLiqValeDig').val())+parseFloat($('#costoTotalCanjeLiqValeDigProv').val())+parseFloat($('#costoTotalCanjeLiqValeDigLim').val()));
				$('#porAtencionReclamos').val(parseFloat($('#costoTotalAtencionConsRecl').val())+parseFloat($('#costoTotalAtencionConsReclProv').val())+parseFloat($('#costoTotalAtencionConsReclLim').val()));
				
				$('#porGestionAdm').val(parseFloat($('#totalGestionAdministrativa').val().length>0?$('#totalGestionAdministrativa').val():'0')
						               +parseFloat($('#totalGestionAdministrativaProv').val().length>0?$('#totalGestionAdministrativaProv').val():'0') 
						               +parseFloat($('#totalGestionAdministrativaLim').val().length>0?$('#totalGestionAdministrativaLim').val():'0'));
				
				$('#porDesplazamientoPers').val(parseFloat($('#totalDesplazamientoPersonal').val().length>0?$('#totalDesplazamientoPersonal').val():'0')
						+parseFloat($('#totalDesplazamientoPersonalProv').val().length>0?$('#totalDesplazamientoPersonalProv').val():'0')
						+parseFloat($('#totalDesplazamientoPersonalLim').val().length>0?$('#totalDesplazamientoPersonalLim').val():'0'));
				
				$('#porActividadExtra').val(parseFloat($('#totalActividadesExtraord').val().length>0?$('#totalActividadesExtraord').val():'0')
						+parseFloat($('#totalActividadesExtraordProv').val().length>0?$('#totalActividadesExtraordProv').val():'0')
						+parseFloat($('#totalActividadesExtraordLim').val().length>0?$('#totalActividadesExtraordLim').val():'0'));
			
			}else{
				
				$('#porImpresionVales').val(parseFloat($('#costoTotalImpresionVale').val())+parseFloat($('#costoTotalImpresionValeProv').val()));
				$('#porRepartoDom').val(parseFloat($('#costoTotalRepartoValesDomi').val())+parseFloat($('#costoTotalRepartoValesDomiProv').val()));
				$('#porEntregaValesDE').val(parseFloat($('#costoTotalEntregaValDisEl').val())+parseFloat($('#costoTotalEntregaValDisElProv').val()));
				$('#porValesFisicos').val(parseFloat($('#costoTotalCanjeLiqValeFis').val())+parseFloat($('#costoTotalCanjeLiqValeFisProv').val()));
				$('#porValesDigitales').val(parseFloat($('#costoTotalCanjeLiqValeDig').val())+parseFloat($('#costoTotalCanjeLiqValeDigProv').val()));
				$('#porAtencionReclamos').val(parseFloat($('#costoTotalAtencionConsRecl').val())+parseFloat($('#costoTotalAtencionConsReclProv').val()));
				//$('#porGestionAdm').val(parseFloat($('#totalGestionAdministrativa').val())+parseFloat($('#totalGestionAdministrativaProv').val()));
				//$('#porDesplazamientoPers').val(parseFloat($('#totalDesplazamientoPersonal').val())+parseFloat($('#totalDesplazamientoPersonalProv').val()));
				//$('#porActividadExtra').val(parseFloat($('#totalActividadesExtraord').val())+parseFloat($('#totalActividadesExtraordProv').val()));
			
			
				$('#porGestionAdm').val(parseFloat($('#totalGestionAdministrativa').val().length>0?$('#totalGestionAdministrativa').val():'0')
			               +parseFloat($('#totalGestionAdministrativaProv').val().length>0?$('#totalGestionAdministrativaProv').val():'0'));
	
	$('#porDesplazamientoPers').val(parseFloat($('#totalDesplazamientoPersonal').val().length>0?$('#totalDesplazamientoPersonal').val():'0')
			+parseFloat($('#totalDesplazamientoPersonalProv').val().length>0?$('#totalDesplazamientoPersonalProv').val():'0'));
	
	$('#porActividadExtra').val(parseFloat($('#totalActividadesExtraord').val().length>0?$('#totalActividadesExtraord').val():'0')
			+parseFloat($('#totalActividadesExtraordProv').val().length>0?$('#totalActividadesExtraord').val():'0'));

			
			}
			
			
			
			$('#porImpresionVales').val(parseFloat($('#porImpresionVales').val()).toFixed(2));
			$('#porRepartoDom').val(parseFloat($('#porRepartoDom').val()).toFixed(2));	
			$('#porEntregaValesDE').val(parseFloat($('#porEntregaValesDE').val()).toFixed(2));
			$('#porValesFisicos').val(parseFloat($('#porValesFisicos').val()).toFixed(2) );
			$('#porValesDigitales').val(parseFloat($('#porValesDigitales').val()).toFixed(2));
			$('#porAtencionReclamos').val(parseFloat($('#porAtencionReclamos').val()).toFixed(2));
			$('#porGestionAdm').val(parseFloat($('#porGestionAdm').val()).toFixed(2));
			$('#porDesplazamientoPers').val(parseFloat($('#porDesplazamientoPers').val()).toFixed(2));
			$('#porActividadExtra').val(parseFloat($('#porActividadExtra').val()).toFixed(2));
			formato12B.loadTotalReconocer();
		
		},
		loadGestion:function(idpor,idinput){
			 var value=$("#"+idinput).val();
			 var valueProv=$("#"+idinput+"Prov").val();
			 var valueLim=$("#"+idinput+"Lim").val();
			 
			 if(value.length>0){
				 var numdec=value.length-(value.indexOf('.')+1);
            	 if(numdec==0){
            		 $("#"+idinput).val(value.substring(0, (value.indexOf('.')+1))+'00'); 
            	}
			 } if(valueProv.length>0){
				 var numdec=valueProv.length-(valueProv.indexOf('.')+1);
            	 if(numdec==0){
            		 $("#"+idinput+"Prov").val(valueProv.substring(0, (valueProv.indexOf('.')+1))+'00'); 
            	}
			 } if(valueLim.length>0){
				 var numdec=valueLim.length-(valueLim.indexOf('.')+1);
            	 if(numdec==0){
            		 $("#"+idinput+"Lim").val(valueLim.substring(0, (valueLim.indexOf('.')+1))+'00'); 
            	}
			 }
			
			 
			 if(formato12B.cmbCodEmpresa.val().trim()=='EDLN' || formato12B.cmbCodEmpresa.val().trim()=='LDS'){
				 $("#"+idpor).val(parseFloat(value.length>0?value:'0.0')+parseFloat(valueProv.length>0?valueProv:'0.0')+parseFloat(valueLim.length>0?valueLim:'0.0')); 
			 }else{
				 $("#"+idpor).val(parseFloat(value.length>0?value:'0.0')+parseFloat(valueProv.length>0?valueProv:'0.0'));
			 }
		
			$("#"+idpor).val(parseFloat($("#"+idpor).val()).toFixed(2));
			formato12B.loadTotalReconocer();
		},
		loadTotalReconocer : function(){
			$('#totalGeneralReconocer').val(Number($('#porImpresionVales').val())+Number($('#porRepartoDom').val())+Number($('#porEntregaValesDE').val())+
					Number($('#porValesFisicos').val())+Number($('#porValesDigitales').val())+Number($('#porAtencionReclamos').val())+Number($('#porGestionAdm').val())+
					Number($('#porDesplazamientoPers').val())+Number($('#porActividadExtra').val()));	
			//$('#totalGeneralReconocer').val(redondeo( $('#totalGeneralReconocer').val(), 2));
			$('#totalGeneralReconocer').val(Number($('#totalGeneralReconocer').val()).toFixed(2));
		},
		loadCostoTotatByInput:function (total,nro,costo,idhidden,idstandar,idtotal){
			total.val((nro.val()!=null && nro.length>0)?(nro.val()*costo.val()):'0.00');
			//alert(nro.val() +"*"+costo.val()+"=" +total.val());
			
			var indexpunto=total.val().indexOf('.');
			
			if(indexpunto > 0){
				total.val(total.val().substring(0,indexpunto+3));	
			}
			
			
			$("#"+idhidden).val(total.val());
			
			 
			var value=$("#"+idstandar).val();
			 var valueProv=$("#"+idstandar+"Prov").val();
			 var valueLim=$("#"+idstandar+"Lim").val();
			
			 if(formato12B.cmbCodEmpresa.val().trim()=='EDLN' || formato12B.cmbCodEmpresa.val().trim()=='LDS'){
				 $("#"+idtotal).val(parseFloat(value.length>0?value:'0.00')+parseFloat(valueProv.length>0?valueProv:'0.00')+parseFloat(valueLim.length>0?valueLim:'0.00'));
			 }else{
				 $("#"+idtotal).val(parseFloat(value.length>0?value:'0.00')+parseFloat(valueProv.length>0?valueProv:'0.00'));
			 }
			 
			 
			var totalT=$("#"+idtotal).val();
			
			if(totalT.length>0){
				$("#"+idtotal).val(totalT.substring(0,totalT.indexOf('.')+3));	
			}else{
				$("#"+idtotal).val(totalT);	
			}
			 
			
             formato12B.loadTotalReconocer();
			
		
		},
		showConfirmacion : function (emp,mes,anio,etapa,desmes,stdEnvio,std,mesEjec,desmesEjec,anioEjec,tipo) {	
			//alert(emp+"/"+mes+"/"+anio+"/"+etapa+"/"+desmes+"/"+stdEnvio+"/"+std+"/"+mesEjec+"/"+desmesEjec+"/"+anioEjec+"/"+tipo);
			var admin = '${esAdministrador}';
			var process = true;
			var show=true;
			var numModal=2;//modsl aviso
			var msj='El formato ya fue enviado a OSINERGMIN-GART';
			switch(tipo) {
				case '0':{//vista
					show=false;
					formato12B.blockUI();
					location.href=formato12B.urlViewFormato+'&codEmpresa='+emp+'&anioPresentacion='+anio+'&mesPresentacion='+mes+'&etapa='+etapa+'&desmes='+desmes+'&tipoOperacion='+tipo+'&mesEjecucionGasto='+mesEjec+'&descMesEjec='+desmesEjec+'&anoEjecucionGasto='+anioEjec+'&'+formato12B.formBusqueda.serialize();
				}break;
				case '1':{//edit
					if(std =='ABIERTO'){//1enviado 0=x enviar
						if( etapa=='RECONOCIDO' || !admin ){
							process = false;
						}
						if(process){
							show=false;//no muestra modal
							formato12B.blockUI();
							location.href=formato12B.urlViewFormato+'&codEmpresa='+emp+'&anioPresentacion='+anio+'&mesPresentacion='+mes+'&etapa='+etapa+'&desmes='+desmes+'&tipoOperacion='+tipo+'&mesEjecucionGasto='+mesEjec+'&descMesEjec='+desmesEjec+'&anoEjecucionGasto='+anioEjec+'&'+formato12B.formBusqueda.serialize();
						}else{
							show=false;
					   		msj=" No tiene autorización para realizar esta operación";
						}
					 }else if(std=='CERRADO'){
						show=false;
						msj=" Está fuera de plazo";
					 }
				}break;
				case '3':{//eliminar
					if(std=='ABIERTO'){//1enviado 0=x enviar
						if( etapa=='RECONOCIDO' || !admin ){
							process = false;
						}
						if(process){
							show=true;
							msj='¿Está seguro que desea eliminar el registro seleccionado?';
							numModal=1;
						}else{
							show=false;
					   		msj=" No tiene autorización para realizar esta operación";
						}
				    }else if(std=='CERRADO'){
				    	show=false;
				    	msj=" Está fuera de plazo";
					}
				 }break;
			}
			
            if(show){
				formato12B.dlgConfirmacion.html(msj);
				if(numModal == 1){
					formato12B.dlgConfirmacion.dialog({ 
				     	title:"Mensaje de Confirmaci&oacute;n",
				     	height:150,
						width: 500,	
				         modal: true,
				         buttons:{
				        	 "Si":function(){
				        		 $( this ).dialog( "close" );
				        		 formato12B.deleteFormato(emp, mes, anio, etapa,mesEjec,anioEjec, formato12B.dlgConfirmacion);
				        		 //emp,mes,anio,etapa,desmes,stdEnvio,std,mesEjec,desmesEjec,anioEjec,tipo
				        	 },
				        	 "No":function(){
				        		 $( this ).dialog( "close" );
				        		
				        	 }
				         },
				         open: function(event, ui) {$(".ui-dialog-titlebar-close").show(); }
				     }).load();
				}else{
					/*titulo="Mensaje de Informaci&oacute;n";
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
				     }).load();*/
					formato12B.dialogMessageInfoContent.html(msj);
					formato12B.dialogMessageInfo.dialog("open");
				}
				
				
			}
           

		},
		showLoadFile:function(tipoFile){
			formato12B.txtTypeFile.val(tipoFile);
			formato12B.divOverlay.show();
		    formato12B.dlgLoadFile.show();
		    formato12B.dlgLoadFile.css({ 
		        'left': ($(window).width() / 3 - formato12B.dlgLoadFile.width() / 3) + 'px', 
		        'top': ($(window).height()  - formato12B.dlgLoadFile.height() ) + 'px'
		    });
		    $("#msjUploadFile").html("");
			
			
		},
		closeLoadFile : function(){
			formato12B.dlgLoadFile.hide();
			formato12B.divOverlay.hide();   
		},
		uploadFile : function(tipoFile){
			var nameFile=$("#archivoExcel").val();
			var isSubmit=true;
			
			$("#msjUploadFile").html("");
			if(typeof (nameFile) == "undefined" || nameFile.length==0){
				
				isSubmit=false;
				$("#msjUploadFile").html("Debe seleccionar un archivo");
			}else{
				var extension=nameFile.substr(nameFile.indexOf(".")+1,nameFile.length);
				
				if(tipoFile == '1'){
					if(extension == 'xls' || extension == 'xlsx'){
						isSubmit=true;
					}else{
						isSubmit=false;
						$("#msjUploadFile").html("Archivo inválido");
					}
					
				}else if(tipoFile == '2'){
					if(extension == 'txt' ){
						isSubmit=true;
					}else{
						isSubmit=false;
						$("#msjUploadFile").html("Archivo inválido");
					}
				}
			}
			
			if(isSubmit){
				 formato12B.formNewEdit.submit(); 
			}
			 
			
		},
		showCamposOculto: function(){
			
			formato12B.divgrupoestado.css("display","block");
			formato12B.cmbCodEmpresa.prop('disabled', true);
			formato12B.cmbPeriodo.prop('disabled', true);
		
			formato12B.txtAnioEjec.prop("type","text");
			formato12B.txtAnioEjecCommand.prop("type","hidden");
			formato12B.cmbMesEjecucion.prop('disabled', true);
			
		},
		
		validateSave :function(tipoOperacion){
			var codemp=formato12B.cmbCodEmpresa.val();
			
			if(tipoOperacion=='1'){//edit
				codemp=$("#codEmpresaHidden").val();
			}
			var countR=0;
			var countP=0;
			
			if(formato12B.txtnroValesImpreso.val().length>0){countR++;}
			if(formato12B.txtnroValesRepartidosDomi.val().length>0){countR++;}
			if(formato12B.txtnroValesEntregadoDisEl.val().length>0){countR++;}
			if(formato12B.txtnroValesFisicosCanjeados.val().length>0){countR++;}
			if(formato12B.txtnroValesDigitalCanjeados.val().length>0){countR++;}
			if(formato12B.txtnroAtenciones.val().length>0){countR++;}
			if(formato12B.txtTotalGestionAdministrativa.val().length>0){countR++;}
			if(formato12B.txtTotalDesplazamientoPersonal.val().length>0){countR++;}
			if(formato12B.txtTotalActividadesExtraord.val().length>0){countR++;}
			
			if(formato12B.txtnroValesImpresoProv.val().length){countP++;}
			if(formato12B.txtnroValesRepartidosDomiProv.val().length){countP++;}
			if(formato12B.txtnroValesEntregadoDisElProv.val().length){countP++;}
			if(formato12B.txtnroValesFisicosCanjeadosProv.val().length){countP++;}
			if(formato12B.txtnroValesDigitalCanjeadosProv.val().length){countP++;}
			if(formato12B.txtnroAtencionesProv.val().length){countP++;}
			if(formato12B.txtTotalGestionAdministrativaProv.val().length){countP++;}
			if(formato12B.txtTotalDesplazamientoPersonalProv.val().length){countP++;}
			if(formato12B.txtTotalActividadesExtraordProv.val().length){countP++;}
			
			if(codemp == 'EDLN' || codemp == 'LDS'){
				var countL=0;
				if(formato12B.txtnroValesImpresoLim.val().length){countL++;}
				if(formato12B.txtnroValesRepartidosDomiLim.val().length){countL++;}
				if(formato12B.txtnroValesEntregadoDisElLim.val().length){countL++;}
				if(formato12B.txtnroValesFisicosCanjeadosLim.val().length){countL++;}
				if(formato12B.txtnroValesDigitalCanjeadosLim.val().length){countL++;}
				if(formato12B.txtnroAtencionesLim.val().length){countL++;}
				if(formato12B.txtTotalGestionAdministrativaLim.val().length){countL++;}
				if(formato12B.txtTotalDesplazamientoPersonalLim.val().length){countL++;}
				if(formato12B.txtTotalActividadesExtraordLim.val().length){countL++;}
				
				if(countR>0 && countP>0 && countL>0 ){
					return true;
				}
			}else{
				if(countR>0 && countP>0 ){
					return true;
				}
				
			}
			
			return false;
		},
		save : function(){	
			var form = formato12B.formNewEdit.serialize();
			if(formato12B.validateSave(formato12B.tpOperacion.val())){
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
									formato12B.lblMessage.html("Datos guardados satisfactoriamente");
									formato12B.dialogMessageGeneral.dialog("open");
									
								}else if(item.msg == "-1"){
									//formato12B.lblMessage.html("El formato ya existe para la empresa y periodo seleccionado");
									//formato12B.dialogMessageGeneral.dialog("open");
									formato12B.dialogMessageErrorDetalleContent.html("El formato ya existe para la empresa y periodo seleccionado");
									formato12B.dialogMessageErrorDetalle.dialog("open");
									
								}else if(item.msg == "-2"){
									//formato12B.lblMessage.html("Error al obtener usuario");
									//formato12B.dialogMessageGeneral.dialog("open");
									formato12B.dialogMessageErrorDetalleContent.html("Error al obtener usuario");
									formato12B.dialogMessageErrorDetalle.dialog("open");
									
								}else if(item.msg == "-3"){
									//formato12B.lblMessage.html("Error al obtener terminal");
									//formato12B.dialogMessageGeneral.dialog("open");
									formato12B.dialogMessageErrorDetalleContent.html("Error al obtener terminal");
									formato12B.dialogMessageErrorDetalle.dialog("open");
									
								}else if(item.msg == "-4"){
									//formato12B.lblMessage.html("Se produjo un error al guardar los datos");
									//formato12B.dialogMessageGeneral.dialog("open");
									formato12B.dialogMessageErrorDetalleContent.html("Se produjo un error al guardar los datos");
									formato12B.dialogMessageErrorDetalle.dialog("open");
									
								}
							});
						}
						formato12B.unblockUI();
					},error : function(){
						alert("Error de conexión.");
						formato12B.unblockUI();
					}
			});
			}else{
				//formato12B.lblMessage.html("Debe al menos registrar un campo por zona beneficiaria");
				//formato12B.dialogMessageGeneral.dialog("open");
				formato12B.dialogMessageWarningDetalleContent.html("Debe al menos registrar un campo por zona beneficiaria");
				formato12B.dialogMessageWarningDetalle.dialog("open");
			}
			
			
		},
		
		deleteFormato:function(emp,mes,anio,etapa,mesEjec,anioEjec,dlg){
		
			jQuery.ajax({	
				url: formato12B.urlDeleteFormato,
				type: 'post',
				data : {"codEmpresa":emp,
					"mesPresentacion":mes,
					"anoPresentacion":anio,
					"etapa":etapa,
					"mesEjecucionGasto":mesEjec,
					"anoEjecucionGasto":anioEjec,
				},
				dataType : "text",
				beforeSend:function(){
					formato12B.blockUI();
				},				
				success: function(data) {		
					
					if(data == '1' ){
						//formato12B.btnBuscar.trigger('click');
						formato12B.lblMessageInicial.html("Registro eliminado con éxito");
						formato12B.dialogMessageGeneralInicial.dialog("open");
						
						
					}else if(data == '-1'){
						//formato12B.lblMessageInicial.html("Error al eliminar registro");
						//formato12B.dialogMessageGeneralInicial.dialog("open");
						formato12B.dialogMessageErrorContent.html("Error al eliminar registro");
						formato12B.dialogMessageError.dialog("open");
						
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
				//formato12B.lblMessage.html("Primero debe realizar el envío definitivo");
			    //formato12B.dialogMessageGeneral.dialog("open");
			    formato12B.dialogMessageInfoDetalleContent.html("Primero debe realizar el envío definitivo");
				formato12B.dialogMessageInfoDetalle.dialog("open");
				
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
			
		if(emp == 'EDLN' || emp == 'LDS'){
			formato12B.txtnroValesImpresoLim.addClass("fise-editable");
			formato12B.txtnroValesRepartidosDomiLim.addClass("fise-editable");
			formato12B.txtnroValesEntregadoDisElLim.addClass("fise-editable");
			formato12B.txtnroValesFisicosCanjeadosLim.addClass("fise-editable");
			formato12B.txtnroValesDigitalCanjeadosLim.addClass("fise-editable");
			formato12B.txtnroAtencionesLim.addClass("fise-editable");
			formato12B.txtTotalGestionAdministrativaLim.addClass("fise-editable");
			formato12B.txtTotalDesplazamientoPersonalLim.addClass("fise-editable");
			formato12B.txtTotalActividadesExtraordLim.addClass("fise-editable");
		}else{
			
			formato12B.txtnroValesImpresoLim.removeClass("fise-editable");
			formato12B.txtnroValesRepartidosDomiLim.removeClass("fise-editable");
			formato12B.txtnroValesEntregadoDisElLim.removeClass("fise-editable");
			formato12B.txtnroValesFisicosCanjeadosLim.removeClass("fise-editable");
			formato12B.txtnroValesDigitalCanjeadosLim.removeClass("fise-editable");
			formato12B.txtnroAtencionesLim.removeClass("fise-editable");
			formato12B.txtTotalGestionAdministrativaLim.removeClass("fise-editable");
			formato12B.txtTotalDesplazamientoPersonalLim.removeClass("fise-editable");
			formato12B.txtTotalActividadesExtraordLim.removeClass("fise-editable");
			
			/*formato12B.txtnroValesImpresoLim.addClass("target");
			formato12B.txtnroValesRepartidosDomiLim.addClass("target");
			formato12B.txtnroValesEntregadoDisElLim.addClass("target");
			formato12B.txtnroValesFisicosCanjeadosLim.addClass("target");
			formato12B.txtnroValesDigitalCanjeadosLim.addClass("target");
			formato12B.txtnroAtencionesLim.addClass("target");
			formato12B.txtTotalGestionAdministrativaLim.addClass("target");
			formato12B.txtTotalDesplazamientoPersonalLim.addClass("target");
			formato12B.txtTotalActividadesExtraordLim.addClass("target");*/
			
		}
			
		},initDialogsInit : function(){	
				formato12B.dialogMessageGeneralInicial.dialog({
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
			formato12B.dialogMessageConfirm.dialog({
				modal: true,
				autoOpen: false,
				width: 500,
				buttons: {
					Ok: function() {
						$( this ).dialog("close");
					}
				}
			});
			formato12B.dialogMessageInfo.dialog({
				modal: true,
				autoOpen: false,
				width: 500,
				buttons: {
					Ok: function() {
						$( this ).dialog("close");
					}
				}
			});
			formato12B.dialogMessageWarning.dialog({
				modal: true,
				autoOpen: false,
				width: 500,
				buttons: {
					Ok: function() {
						$( this ).dialog("close");
					}
				}
			});
			formato12B.dialogMessageError.dialog({
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
			formato12B.dialogMessageInfoDetalle.dialog({
				modal: true,
				autoOpen: false,
				width: 500,
				buttons: {
					Ok: function() {
						$( this ).dialog("close");
					}
				}
			});
			formato12B.dialogMessageWarningDetalle.dialog({
				modal: true,
				autoOpen: false,
				width: 500,
				buttons: {
					Ok: function() {
						$( this ).dialog("close");
					}
				}
			});
			formato12B.dialogMessageErrorDetalle.dialog({
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
			  // $(this).val($(this).val().replace(/^\s*-?\d+(\.\d{1,2})?\s*$/g,''));
		          if ((event.which != 46 || $(this).val().indexOf('.') != -1) && (event.which < 48 || event.which > 57)) {
		                event.preventDefault();
		            };
		            var value=$(this).val();
		            if(value.length>0){
		            	var numdec=value.length-(value.indexOf('.')+1);
		            	 if(numdec>2 && value.indexOf('.')!=-1){
		            		$(this).val(value.substring(0, value.indexOf('.')+3)); 
		            	}
		            }
		            
		               
		            
			});
			
		}
		
	};
</script>