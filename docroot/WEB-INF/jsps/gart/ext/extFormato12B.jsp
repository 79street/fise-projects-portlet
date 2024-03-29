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
		urlLoadCostosUnitariosFijadosEdit:null,
		
		urlGrupoInformacion:null,
		urlGrupoInformacionEdit:null,
		
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
       
        msgTransaccion:null,
        
        dialogError:null,
        
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
        
        anoPresentacionDetalle:null,
        mesPresentacionDetalle:null,
        
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
			
			this.dialogMessageGeneralInicial=$("#dialogMessageGeneralInicio");
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
			});
			
		}, 
		
		validateInputAnio : function(inicio,fin){//validateInputAnio
			
			if( inicio.val()=='' ){
				 formato12B.dialogMessageWarningContent.html("Debe ingresar un A�o Declarado Desde");
				 formato12B.dialogMessageWarning.dialog("open");
				 return false; 
			}
			if( fin.val()=='' ){
				 formato12B.dialogMessageWarningContent.html("Debe ingresar un A�o Declarado Hasta");
				 formato12B.dialogMessageWarning.dialog("open");
				 return false; 
			}
			
			 if((inicio.val().length>0 && inicio.val().length<4 )|| (fin.val().length>0 && fin.val().length<4)){	    		
				 formato12B.dialogMessageWarningContent.html("Debe ingresar un A�o Declarado Desde v�lido");
				 formato12B.dialogMessageWarning.dialog("open");
				 return false; 
	    		
	    	 }else if(fin.val().length>0){
	    		 if(parseFloat(inicio.val().length>0?inicio.val():'0')>parseFloat(fin.val())){	    		
					 formato12B.dialogMessageWarningContent.html("El A�o Declarado Desde no puede exceder al A�o Declarado Hasta");
					 formato12B.dialogMessageWarning.dialog("open");
					 return false; 
	    		 }	    		 
	    	 }
		
			 if(parseFloat(inicio.val().length>0?inicio.val():'0')==parseFloat(fin.val().length>0?fin.val():'0')){
				 var mesinicio=$("#mesInicio").val();
					var mesfin=$("#mesFin").val();
				
					if(parseFloat(mesfin)<parseFloat(mesinicio)){						
						 formato12B.dialogMessageWarningContent.html("El Mes Declarado Desde no puede exceder al Mes Declarado Hasta");
						 formato12B.dialogMessageWarning.dialog("open");
						 return false; 
					}   		
	    	 }
	  	 return true; 
     },
     
     
     
     buildGridsBusqueda : function () {	
			formato12B.tablaBusqueda.jqGrid({
				   datatype: "local",
			       colNames: ['Dist. El�ct.','A�o Decl.','Mes Decl.','Etapa','Grupo de Informaci�n','Estado','Ver','Editar','Eliminar','','','','','','',''],
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
						height: 225,
					   	autowidth: true,
						rownumbers: true,
						shrinkToFit:true,
						pager: formato12B.paginadoBusqueda,
					    viewrecords: true,
					   	caption: "Resultado(s) de la b�squeda",
					    sortorder: "asc",	   	    	   	   
			       gridComplete: function(){
			      		var ids = formato12B.tablaBusqueda.jqGrid('getDataIDs');
			      		for(var i=0;i < ids.length;i++){
			      			var cl = ids[i];
			      			var ret = formato12B.tablaBusqueda.jqGrid('getRowData',cl); 
			      			view = "<a href='#'><img border='0' title='Ver' src='/net-theme/images/img-net/file.png'  align='center' onclick=\"formato12B.showConfirmacion('"+ret.codEmpresa+"','"+ret.mesPresentacion+"','"+ret.anoPresentacion+"','"+ret.etapa+"','"+ret.descMes+"','"+ret.estadoEnvio+"','"+ret.estadoProceso+"','"+ret.mesEjecucionGasto+"','"+ret.descMesEjec+"','"+ret.anoEjecucionGasto+"','0');\" /></a> ";
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
				    	   var ids = formato12B.tablaBusqueda.jqGrid('getDataIDs');
					       if(ids!=0){
					    		  location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';   
					       }else{
					    	var addhtmInfo='No existe informaci�n para exportar a Excel';				    	
					    	formato12B.dialogMessageInfoContent.html(addhtmInfo);
							formato12B.dialogMessageInfo.dialog("open");
					       }		           
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
						alert("Error de conexi�n.");
						formato12B.unblockUI();
					}
			});
			}           
        },
       
		
		loadInitDetalle:function(urlBack){
			
			this.urlRetornar=urlBack;
			this.urlLoadPeriodo='<portlet:resourceURL id="loadPeriodoDeclaracion" />';
			this.urlLoadCostosUnitarios='<portlet:resourceURL id="loadCostoUnitario" />';
			//cambios elozano
			this.urlLoadCostosUnitariosFijadosEdit='<portlet:resourceURL id="loadCostoUnitarioFijadoEditar" />';
			//fin de cambio
			this.msgTransaccion=$("#msgTransaccion");
			
			this.urlGrupoInformacion='<portlet:resourceURL id="loadGrupoInformacion" />';
			this.urlGrupoInformacionEdit='<portlet:resourceURL id="loadGrupoInformacionEdit" />';
			
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
			
			this.btnBack=$("#<portlet:namespace/>regresarFormato");
			
			this.cmbPeriodo=$('#cmbPeriodo');
			this.txtEmpresa=$('#txtDescEmp');
			this.txtPeriodo=$('#txtPeriodo');
			this.divgrupoestado=$('#divgrupoestado');
			
			
			this.txtAnioEjec=$('#txtanoEjecucionGasto');		
			this.txtAnioEjecCommand=$('#anoEjecucionGasto');		
			
			this.cmbMesEjecucion=$('#cmbMesEjecucion');
			this.txtMesEjechidden=$('#txtmesEjecucionGasto');
			 
			this.anoPresentacionDetalle=$('#anoPresentacion');
			this.mesPresentacionDetalle=$('#Presentacion');
			 
			this.txtnroValesImpreso=$('#numeroValesImpreso');
			this.txtnroValesImpresoProv=$('#numeroValesImpresoProv');
			this.txtnroValesImpresoLim=$('#numeroValesImpresoLim');
			this.txtTotalImpresionVale=$('#txtcostoTotalImpresionVale');
			this.txtTotalImpresionValeProv=$('#txtcostoTotalImpresionValeProv');
			this.txtTotalImpresionValeLim=$('#txtcostoTotalImpresionValeLim');	
			
			this.txtnroValesRepartidosDomi=$('#numeroValesRepartidosDomi');
			this.txtnroValesRepartidosDomiProv=$('#numeroValesRepartidosDomiProv');
			this.txtnroValesRepartidosDomiLim=$('#numeroValesRepartidosDomiLim');
			this.txtTotalRepartoValesDomi=$('#txtcostoTotalRepartoValesDomi');
			this.txtTotalRepartoValesDomiProv=$('#txtcostoTotalRepartoValesDomiProv');
			this.txtTotalRepartoValesDomiLim=$('#txtcostoTotalRepartoValesDomiLim');		
			
			this.txtnroValesEntregadoDisEl=$('#numeroValesEntregadoDisEl');
			this.txtnroValesEntregadoDisElProv=$('#numeroValesEntregadoDisElProv');
			this.txtnroValesEntregadoDisElLim=$('#numeroValesEntregadoDisElLim');			
			this.txtTotalEntregaValDisEl=$('#txtcostoTotalEntregaValDisEl');
			this.txtTotalEntregaValDisElProv=$('#txtcostoTotalEntregaValDisElProv');
			this.txtTotalEntregaValDisElLim=$('#txtcostoTotalEntregaValDisElLim');
			
			this.txtnroValesFisicosCanjeados=$('#numeroValesFisicosCanjeados');
			this.txtnroValesFisicosCanjeadosProv=$('#numeroValesFisicosCanjeadosProv');
			this.txtnroValesFisicosCanjeadosLim=$('#numeroValesFisicosCanjeadosLim');			
			this.txtTotalCanjeLiqValeFis=$('#txtcostoTotalCanjeLiqValeFis');
			this.txtTotalCanjeLiqValeFisProv=$('#txtcostoTotalCanjeLiqValeFisProv');
			this.txtTotalCanjeLiqValeFisLim=$('#txtcostoTotalCanjeLiqValeFisLim');		
			
		   this.txtnroValesDigitalCanjeados=$('#numeroValesDigitalCanjeados');
		   this.txtnroValesDigitalCanjeadosProv=$('#numeroValesDigitalCanjeadosProv');
		   this.txtnroValesDigitalCanjeadosLim=$('#numeroValesDigitalCanjeadosLim');			
		   this.txtTotalCanjeLiqValeDig=$('#txtcostoTotalCanjeLiqValeDig');
		   this.txtTotalCanjeLiqValeDigProv=$('#txtcostoTotalCanjeLiqValeDigProv');
		   this.txtTotalCanjeLiqValeDigLim=$('#txtcostoTotalCanjeLiqValeDigLim');
					
		   this.txtnroAtenciones=$('#numeroAtenciones');
		   this.txtnroAtencionesProv=$('#numeroAtencionesProv');
		   this.txtnroAtencionesLim=$('#numeroAtencionesLim');		  
		   this.txtTotalAtencionConsRecl=$('#txtcostoTotalAtencionConsRecl');
           this.txtTotalAtencionConsReclProv=$('#txtcostoTotalAtencionConsReclProv');
           this.txtTotalAtencionConsReclLim=$('#txtcostoTotalAtencionConsReclLim');
           
           /////////////////////COSTOS UNITARIOS////////////////////////////           
          //costos unitarios de impresion de vales
           this.txtEtndrUnitValeImpre=$('#costoEstandarUnitValeImpre');
		   this.txtEtndrUnitValeImpreProv=$('#costoEstandarUnitValeImpreProv');
		   this.txtEtndrUnitValeImpreLim=$('#costoEstandarUnitValeImpreLim');          
          //costos unitarios de reparto a domicilio
		   this.txtEtndrUnitValeRepar=$('#costoEstandarUnitValeRepar');
		   this.txtEtndrUnitValeReparProv=$('#costoEstandarUnitValeReparProv');
		   this.txtEtndrUnitValeReparLim=$('#costoEstandarUnitValeReparLim');           
           //costos unitarios en la distribuidora electrica
		   this.txtEtndrUnitValDisEl=$('#costoEstandarUnitValDisEl');
		   this.txtEtndrUnitValDisElProv=$('#costoEstandarUnitValDisElProv');
		   this.txtEtndrUnitValDisElLim=$('#costoEstandarUnitValDisElLim');           
          //costos unitarios vales fisicos
		   this.txtEtndrUnitValFiCan=$('#costoEstandarUnitValFiCan');
		   this.txtEtndrUnitValFiCanProv=$('#costoEstandarUnitValFiCanProv');
		   this.txtEtndrUnitValFiCanLim=$('#costoEstandarUnitValFiCanLim');
           //costos unitarios vales digitales
		   this.txtEtndrUnitValDgCan=$('#costoEstandarUnitValDgCan');
		   this.txtEtndrUnitValDgCanProv=$('#costoEstandarUnitValDgCanProv');
		   this.txtEtndrUnitValDgCanLim=$('#costoEstandarUnitValDgCanLim');
           //costos unitarios atencion de solicitudes
           this.txtEtndrUnitAtencion=$('#costoEstandarUnitAtencion');
		   this.txtEtndrUnitAtencionProv=$('#costoEstandarUnitAtencionProv');
		   this.txtEtndrUnitAtencionLim=$('#costoEstandarUnitAtencionLim');           
		   //costos unitarios de gestion administrativa
           this.txtTotalGestionAdministrativa=$('#totalGestionAdministrativa');
           this.txtTotalGestionAdministrativaProv=$('#totalGestionAdministrativaProv');
           this.txtTotalGestionAdministrativaLim=$('#totalGestionAdministrativaLim');
           ///////////////////////////FIN DE COSTOS UNITARIOS////////////////////////           
           
           
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
           
           
           this.dialogError=$("#<portlet:namespace/>dialog-form-error");
            
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
				console.debug("Entrando a aperacion 0 ");//nuevo
			 	formato12B.cmbPeriodo.append($("<option></option>").attr("value",$("#peridoDeclaracionHidden").val()).text(formato12B.txtPeriodo.val())); 
				formato12B.showCamposOculto();
				formato12B.btnReportePdf.click(function() {formato12B.viewReporte('0');});
				formato12B.btnReporteExpExcel.click(function() {formato12B.viewReporte('1');});
				formato12B.btnReporteActaEnvio.click(function() {formato12B.showActaEnvio();});
				
				formato12B.llenarDatosEditarView();
				
				formato12B.loadTotales($("#codEmpresaHidden").val());
				
			}else if(formato12B.tpOperacion.val()=='1'){
				console.debug("Entrando a aperacion 1 ");//editar y carga de exel nuevo				
				formato12B.cmbPeriodo.append($("<option></option>").attr("value",$("#peridoDeclaracionHidden").val()).text(formato12B.txtPeriodo.val())); 
				formato12B.showCamposOculto();				
				formato12B.eventButtons(formato12B.tpOperacion.val());				
				formato12B.updateStyleClassInput(formato12B.tpOperacion.val(),formato12B.cmbCodEmpresa.val().trim());			
				
				formato12B.buildGridsObservacion();
				
				formato12B.llenarDatosEditarView();//cambio elozano carga data de editar			
				
				formato12B.loadTotales($("#codEmpresaHidden").val());
				
				$('#<portlet:namespace/>guardarFormato').val('Actualizar');
				
				$('#etapaFinal').val('');
				
				//update
				if( formato12B.msgTransaccion.val()=='OK' ){
					var addhtml='La carga de informaci�n del Formato 12B se realiz� satisfactoriamente';
					formato12B.lblMessage.html(addhtml);
					formato12B.dialogMessageGeneral.dialog("open");
				}else if( formato12B.msgTransaccion.val()=='ERROR' ){
					formato12B.dialogError.dialog( "open" );
				}
			
			}else if(formato12B.tpOperacion.val()=='2'){
				console.debug("Entrando a aperacion 2 ");
				
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
				
				formato12B.loadTotales(formato12B.cmbCodEmpresa.val());				
				
				formato12B.camposEditablesInicialiarCeros();//para inicializar en ceros los campos editables
				
				//create
				if( formato12B.msgTransaccion.val()=='OK' ){
					var addhtml='La carga de informaci�n del Formato 12B se realiz� satisfactoriamente';
					formato12B.lblMessage.html(addhtml);
					formato12B.dialogMessageGeneral.dialog("open");
				}else if( formato12B.msgTransaccion.val()=='ERROR' ){
					formato12B.dialogError.dialog( "open" );
				}		
			}
		},
		
		
		eventButtons:function (tipo){
			formato12B.divLoadFile.css("display","block");
			formato12B.btnGuardar.css("display","block");
			
			formato12B.btnShowLoadExcel.click(function() {
				if( formato12B.validarGrupoInformacion() ){
					if( formato12B.validarUltimaEtapa() ){
						formato12B.showLoadFile('1');
					}
					
				}
			});//excel
			
			
			formato12B.btnShowLoadTxt.click(function() {
				if( formato12B.validarGrupoInformacionTxt() ){
					if( formato12B.validarUltimaEtapa() ){
						formato12B.showLoadFile('2');
					}
					
				}	
			});//txt
			
			formato12B.btnUploadExcel.click(function() {formato12B.uploadFile(formato12B.txtTypeFile.val());});
			
			formato12B.btnGuardar.click(function(){
				if( formato12B.validarGrupoInformacion() ){
					if( formato12B.validarUltimaEtapa() ){
						formato12B.save();
					}
					
				}
			});			
		
			if(tipo == '1'){//editar
				formato12B.btnValidacion.css("display","block");
				formato12B.btnEnvioDefinitivo.css("display","block");
				formato12B.btnValidacion.click(function() {formato12B.showValidacion();});
				formato12B.btnEnvioDefinitivo.click(function() {formato12B.confirmarEnvioDefinitivo();});
		
				$('#<portlet:namespace/>guardarFormato').val('Actualizar');
			
			}else if(tipo == '2'){//nuevo
				formato12B.btnValidacion.css("display","none");
				formato12B.btnEnvioDefinitivo.css("display","none");
			}			
		},
		
		
	  //cambios elozano function para inicializar en ceros los campos editables (NUEVO FORMATO)
	   camposEditablesInicialiarCeros:function(){			   
		   $("#numeroValesImpreso").val('0');
		   $("#numeroValesImpresoProv").val('0');
		   $("#numeroValesRepartidosDomi").val('0');
		   $("#numeroValesRepartidosDomiProv").val('0');
		   $("#numeroValesEntregadoDisEl").val('0');
		   $("#numeroValesEntregadoDisElProv").val('0');
		   $("#numeroValesFisicosCanjeados").val('0');
		   $("#numeroValesFisicosCanjeadosProv").val('0');
		   $("#numeroValesDigitalCanjeados").val('0');
		   $("#numeroValesDigitalCanjeadosProv").val('0');		   
		   $("#numeroAtenciones").val('0');		   
		   $("#numeroAtencionesProv").val('0');   
		   $("#totalDesplazamientoPersonal").val('0.00');
		   $("#totalDesplazamientoPersonalProv").val('0.00');
		   $("#totalActividadesExtraord").val('0.00');
		   $("#totalActividadesExtraordProv").val('0.00');	   
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
				formato12B.txtTotalDesplazamientoPersonal.prop('disabled', false);
				formato12B.txtTotalDesplazamientoPersonalProv.prop('disabled', false);
				formato12B.txtTotalActividadesExtraord.prop('disabled', false);
				formato12B.txtTotalActividadesExtraordProv.prop('disabled', false);
				
				if(formato12B.cmbCodEmpresa.val().trim()=='EDLN' || 
						formato12B.cmbCodEmpresa.val().trim()=='LDS'){
					
					formato12B.txtnroValesImpresoLim.prop('disabled', false);
					formato12B.txtnroValesRepartidosDomiLim.prop('disabled', false);
					formato12B.txtnroValesEntregadoDisElLim.prop('disabled', false);
					formato12B.txtnroValesFisicosCanjeadosLim.prop('disabled', false);
					formato12B.txtnroValesDigitalCanjeadosLim.prop('disabled', false);
					formato12B.txtnroAtencionesLim.prop('disabled', false);					
					formato12B.txtTotalDesplazamientoPersonalLim.prop('disabled', false);
					formato12B.txtTotalActividadesExtraordLim.prop('disabled', false);
					
				}else{
					formato12B.txtnroValesImpresoLim.prop('disabled', true);
					formato12B.txtnroValesRepartidosDomiLim.prop('disabled', true);
					formato12B.txtnroValesEntregadoDisElLim.prop('disabled', true);
					formato12B.txtnroValesFisicosCanjeadosLim.prop('disabled', true);
					formato12B.txtnroValesDigitalCanjeadosLim.prop('disabled', true);
					formato12B.txtnroAtencionesLim.prop('disabled', true);					
					formato12B.txtTotalDesplazamientoPersonalLim.prop('disabled', true);
					formato12B.txtTotalActividadesExtraordLim.prop('disabled', true);
				}		
				    var impre=formato12B.txtnroValesImpresoLim.val();
				    var repar=formato12B.txtnroValesRepartidosDomiLim.val();
					var entre=formato12B.txtnroValesEntregadoDisElLim.val();
					var fisic=formato12B.txtnroValesFisicosCanjeadosLim.val();
					var digi=formato12B.txtnroValesDigitalCanjeadosLim.val();
					var aten=formato12B.txtnroAtencionesLim.val();				
					var despl=formato12B.txtTotalDesplazamientoPersonalLim.val();
					var act=formato12B.txtTotalActividadesExtraordLim.val();					 
					formato12B.txtnroValesImpresoLim.val(impre.length>0?impre:'');
					formato12B.txtnroValesRepartidosDomiLim.val(repar.length>0?repar:'');
					formato12B.txtnroValesEntregadoDisElLim.val(entre.length>0?entre:'');
					formato12B.txtnroValesFisicosCanjeadosLim.val(fisic.length>0?fisic:'');
					formato12B.txtnroValesDigitalCanjeadosLim.val(digi.length>0?digi:'');
					formato12B.txtnroAtencionesLim.val(aten.length>0?aten:'');					
					formato12B.txtTotalDesplazamientoPersonalLim.val(despl.length>0?despl:'');
					formato12B.txtTotalActividadesExtraordLim.val(act.length>0?act:'');
					
					//POR VEFICIAR COSTOS UNITARIOS
					formato12B.txtEtndrUnitValeImpreLim.val(formato12B.txtEtndrUnitValeImpreLim.val().length>0?formato12B.txtEtndrUnitValeImpreLim.val():'');					 
					formato12B.txtEtndrUnitValeReparLim.val(formato12B.txtEtndrUnitValeReparLim.val().length>0?formato12B.txtEtndrUnitValeReparLim.val():'');	 
					formato12B.txtEtndrUnitValDisElLim.val(formato12B.txtEtndrUnitValDisElLim.val().length>0?formato12B.txtEtndrUnitValDisElLim.val():''); 
					formato12B.txtEtndrUnitValFiCanLim.val(formato12B.txtEtndrUnitValFiCanLim.val().length>0?formato12B.txtEtndrUnitValFiCanLim.val():''); 
					formato12B.txtEtndrUnitValDgCanLim.val(formato12B.txtEtndrUnitValDgCanLim.val().length>0?formato12B.txtEtndrUnitValDgCanLim.val():'');					 
					formato12B.txtEtndrUnitAtencionLim.val(formato12B.txtEtndrUnitAtencionLim.val().length>0?formato12B.txtEtndrUnitAtencionLim.val():'');
					formato12B.txtTotalGestionAdministrativaLim.val(formato12B.txtTotalGestionAdministrativaLim.val().length>0?formato12B.txtTotalGestionAdministrativaLim.val():'');	
			}		
		},
		
		
		clearCampoLima :function(){
			
			formato12B.txtnroValesImpresoLim.val('0');
			formato12B.txtnroValesRepartidosDomiLim.val('0');
			formato12B.txtnroValesEntregadoDisElLim.val('0');
			formato12B.txtnroValesFisicosCanjeadosLim.val('0');
			formato12B.txtnroValesDigitalCanjeadosLim.val('0');
			formato12B.txtnroAtencionesLim.val('0');		
			formato12B.txtTotalDesplazamientoPersonalLim.val('0.00');
			formato12B.txtTotalActividadesExtraordLim.val('0.00');	
			formato12B.txtTotalImpresionValeLim.val('0.00');
			formato12B.txtTotalRepartoValesDomiLim.val('0.00');	
			formato12B.txtTotalEntregaValDisElLim.val('0.00');
			formato12B.txtTotalCanjeLiqValeFisLim.val('0.00');
			formato12B.txtTotalCanjeLiqValeDigLim.val('0.00');
			formato12B.txtTotalAtencionConsReclLim.val('0.00');			
		    
			//COSTOS UNITARIOS
			formato12B.txtEtndrUnitValeImpreLim.val('0.00');	 
			formato12B.txtEtndrUnitValeReparLim.val('0.00'); 
			formato12B.txtEtndrUnitValDisElLim.val('0.00'); 
			formato12B.txtEtndrUnitValFiCanLim.val('0.00');	 
			formato12B.txtEtndrUnitValDgCanLim.val('0.00');	 
			formato12B.txtEtndrUnitAtencionLim.val('0.00');		 
			formato12B.txtTotalGestionAdministrativaLim.val('0.00');		  		 
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
					alert("Error de conexi�n.");
					formato12B.unblockUI();
				}
			});
		},
		
		
		loadCostosUnitarios : function(){
			console.debug("Entrando a obtener los costos unitarios");
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
					
					formato12B.unblockUI();
					
					//llenado campos con los costos unitarios
					formato12B.loadDataCostoUnitario(data);
					
					formato12B.<portlet:namespace/>loadGrupoInformacion();
					
				},error : function(){
					alert("Error de conexi�n.");
					formato12B.unblockUI();
				}
			});
		},
		
		//cambios elozano-- obtener los costos fijados cuando editamos el formato 
		 loadCostosUnitariosFijadosEditar : function(){
			console.debug("Entrando a obtener los costos unitarios fijamos al editar un formato");			
			return jQuery.ajax({
				url: formato12B.urlLoadCostosUnitariosFijadosEdit+'&'+formato12B.formNewEdit.serialize(),
				contentType: "application/json; charset=utf-8",
                async : false,
				type: 'post',
				dataType: 'json',
				beforeSend:function(){
					formato12B.blockUI();
				},
				success: function(data) {									
				    formato12B.loadDataCostoUnitarioHiddenEditar(data);		
				    formato12B.unblockUI();
				},error : function(){
					alert("Error de conexi�n.");
					formato12B.unblockUI();
				}
			});
		},
		
		
		<portlet:namespace/>loadGrupoInformacion : function() {
			console.debug("Entrando a loadGrupoInf");
			jQuery.ajax({
				url: formato12B.urlGrupoInformacion+'&'+formato12B.formNewEdit.serialize(),
					type: 'post',
					dataType: 'json',
					success: function(data) {						
						dwr.util.setValue("flagPeriodoEjecucion", data.flagPeriodoEjecucion);	
						dwr.util.setValue("hiddenFlagCostoEstandar", data.flagEditarCostosUnit);
						dwr.util.setValue("idGrupoInfo", data.idGrupoInfo);						
						dwr.util.setValue("etapaFinal", data.etapaFinal);						
						formato12B.mostrarPeriodoEjecucion();	
						
						//cmabios elozano para mostrar o ocultar costos unitarios segun estado
						formato12B.mostrarEditarCostosUnitariosEstado();
						
					},error : function(){
						alert("Error de conexi�n.");
						formato12B.unblockUI();
					}
			});
		},
		cargaPeriodoYGrupo : function(){			
			formato12B.<portlet:namespace/>loadGrupoInformacionEdit();
		},
		
		
		<portlet:namespace/>loadGrupoInformacionEdit : function() {
			console.debug("Entrando a loadgrupo inf editar");
			jQuery.ajax({
				url: formato12B.urlGrupoInformacionEdit+'&'+formato12B.formNewEdit.serialize(),
					type: 'post',
					dataType: 'json',
					success: function(data) {							
						dwr.util.setValue("flagPeriodoEjecucion", data.flagPeriodoEjecucion);						
						dwr.util.setValue("idGrupoInfo", data.idGrupoInfo);						
						dwr.util.setValue("etapaFinal", data.etapaFinal);						
						formato12B.mostrarPeriodoEjecucion();						
					},error : function(){
						alert("Error de conexi�n.");
						formato12B.unblockUI();
					}
			});
		},
		
		
		validarGrupoInformacion : function(){			
			if( $('#idGrupoInfo').val()=='0' ){				
				formato12B.dialogMessageWarningDetalleContent.html('Primero debe crear el Grupo de Informaci�n Mensual para el A�o y Mes a declarar');
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;
			}
			return true;
		},		
		
		validarGrupoInformacionTxt : function(){			
			if( $('#idGrupoInfo').val()=='0' ){				
				formato12B.dialogMessageWarningDetalleContent.html('Primero debe crear el Grupo de Informaci�n Mensual para el A�o y Mes a declarar');
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;
			}
			
			if( parseFloat(formato12B.txtAnioEjec.val())*100 + parseFloat(formato12B.cmbMesEjecucion.val()) > parseFloat(formato12B.cmbPeriodo.val().substring(0,4))*100 + parseFloat(formato12B.cmbPeriodo.val().substring(4,6)) ){
				formato12B.dialogMessageWarningDetalleContent.html("El Periodo de Ejecuci�n no puede ser mayor al Periodo a Declarar");
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;
			}
			
			return true;
		},
		
		validarUltimaEtapa : function(){
			if( $('#etapaFinal').val()=='SI' ){				
				formato12B.dialogMessageWarningDetalleContent.html('No se puede realizar esta operaci�n debido a que el Formato se encuentra en una etapa avanzada');
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;
			}
			return true;
		},		
		
		loadDataCostoUnitario: function(data){
			console.debug("Entrando a llenar campos de los costos unitarios");
			if(data!=null && data.length>0){
				
				formato12B.inicializarValoresCostos();
				
				$.each(data, function (i, item) {					 
					 
					if(item.idZonaBenef == '1'){		 
						 
						 formato12B.txtEtndrUnitValeImpre.val(item.costoUnitarioImpresionVales);					 
						 formato12B.txtEtndrUnitValeRepar.val(item.costoUnitReprtoValeDomici);					 
						 formato12B.txtEtndrUnitValDisEl.val(item.costoUnitEntregaValDisEl);						 
						 formato12B.txtEtndrUnitValFiCan.val(item.costoUnitCanjeLiqValFisi);				 
						 formato12B.txtEtndrUnitValDgCan.val(item.costoUnitCanjeValDigital);						 
						 formato12B.txtEtndrUnitAtencion.val(item.costoUnitarioPorAtencion);	
						 formato12B.txtTotalGestionAdministrativa.val(item.costoUnitarioGestionAdm.toFixed(2));					 		 
						 
						 $('#hiddenCostoUIVR').val(item.costoUnitarioImpresionVales);					
						 $('#hiddenCostoURDR').val(item.costoUnitReprtoValeDomici);					 
						 $('#hiddenCostoUDER').val(item.costoUnitEntregaValDisEl);					
						 $('#hiddenCostoUVFR').val(item.costoUnitCanjeLiqValFisi);				 
						 $('#hiddenCostoUVDR').val(item.costoUnitCanjeValDigital);					 
						 $('#hiddenCostoUASR').val(item.costoUnitarioPorAtencion);	
						 $('#hiddenCostoUGAR').val(item.costoUnitarioGestionAdm.toFixed(2));
						 
					 }else if(item.idZonaBenef == '2'){					
						 
						 formato12B.txtEtndrUnitValeImpreProv.val(item.costoUnitarioImpresionVales);					 
						 formato12B.txtEtndrUnitValeReparProv.val(item.costoUnitReprtoValeDomici); 
						 formato12B.txtEtndrUnitValDisElProv.val(item.costoUnitEntregaValDisEl);						 
						 formato12B.txtEtndrUnitValFiCanProv.val(item.costoUnitCanjeLiqValFisi);						 
						 formato12B.txtEtndrUnitValDgCanProv.val(item.costoUnitCanjeValDigital);						 
						 formato12B.txtEtndrUnitAtencionProv.val(item.costoUnitarioPorAtencion);						
						 formato12B.txtTotalGestionAdministrativaProv.val(item.costoUnitarioGestionAdm.toFixed(2));									 
						 
						 $('#hiddenCostoUIVP').val(item.costoUnitarioImpresionVales);					 
						 $('#hiddenCostoURDP').val(item.costoUnitReprtoValeDomici);						
						 $('#hiddenCostoUDEP').val(item.costoUnitEntregaValDisEl);	
						 console.debug("Costo unitrio dist electrica provincia nuevo:  "+$('#hiddenCostoUDEP').val());
						 $('#hiddenCostoUVFP').val(item.costoUnitCanjeLiqValFisi);					 
						 $('#hiddenCostoUVDP').val(item.costoUnitCanjeValDigital);						 
						 $('#hiddenCostoUASP').val(item.costoUnitarioPorAtencion);
						 $('#hiddenCostoUGAP').val(item.costoUnitarioGestionAdm.toFixed(2));	
					 }					
					 if(item.codEmpresa.trim() == 'EDLN' || item.codEmpresa.trim() == 'LDS'){
						 
						 if(item.idZonaBenef == '3'){						
							 
							 formato12B.txtEtndrUnitValeImpreLim.val(item.costoUnitarioImpresionVales);						 
							 formato12B.txtEtndrUnitValeReparLim.val(item.costoUnitReprtoValeDomici);						 
							 formato12B.txtEtndrUnitValDisElLim.val(item.costoUnitEntregaValDisEl);						 
							 formato12B.txtEtndrUnitValFiCanLim.val(item.costoUnitCanjeLiqValFisi);							 
							 formato12B.txtEtndrUnitValDgCanLim.val(item.costoUnitCanjeValDigital); 
							 formato12B.txtEtndrUnitAtencionLim.val(item.costoUnitarioPorAtencion);							 
							 formato12B.txtTotalGestionAdministrativaLim.val(item.costoUnitarioGestionAdm.toFixed(2));										 
							 
							 $('#hiddenCostoUIVL').val(item.costoUnitarioImpresionVales);				
							 $('#hiddenCostoURDL').val(item.costoUnitReprtoValeDomici);							
							 $('#hiddenCostoUDEL').val(item.costoUnitEntregaValDisEl);							 
							 $('#hiddenCostoUVFL').val(item.costoUnitCanjeLiqValFisi);							 
							 $('#hiddenCostoUVDL').val(item.costoUnitCanjeValDigital);					
							 $('#hiddenCostoUASL').val(item.costoUnitarioPorAtencion);
							 $('#hiddenCostoUGAL').val(item.costoUnitarioGestionAdm.toFixed(2));	
						 }
					 }           
	             });
			}else{	
				     //RURAL
					 formato12B.txtEtndrUnitValeImpre.val("0.00");					 
					 formato12B.txtEtndrUnitValeRepar.val("0.00");				 
					 formato12B.txtEtndrUnitValDisEl.val("0.00");		 
					 formato12B.txtEtndrUnitValFiCan.val("0.00");					 
					 formato12B.txtEtndrUnitValDgCan.val("0.00");					 
					 formato12B.txtEtndrUnitAtencion.val("0.00");			
					 formato12B.txtTotalGestionAdministrativa.val("0.00");
					 
					 $('#hiddenCostoUIVR').val("0.00");					
					 $('#hiddenCostoURDR').val("0.00");					 
					 $('#hiddenCostoUDER').val("0.00");					
					 $('#hiddenCostoUVFR').val("0.00");				 
					 $('#hiddenCostoUVDR').val("0.00");					 
					 $('#hiddenCostoUASR').val("0.00");	
					 $('#hiddenCostoUGAR').val("0.00");				 
					 
					 //PROVINCIA					 
					 formato12B.txtEtndrUnitValeImpreProv.val("0.00");					 
					 formato12B.txtEtndrUnitValeReparProv.val("0.00");					 
					 formato12B.txtEtndrUnitValDisElProv.val("0.00");					 
					 formato12B.txtEtndrUnitValFiCanProv.val("0.00");					 
					 formato12B.txtEtndrUnitValDgCanProv.val("0.00");					 
					 formato12B.txtEtndrUnitAtencionProv.val("0.00");				
					 formato12B.txtTotalGestionAdministrativaProv.val("0.00");		 
					
					 $('#hiddenCostoUIVP').val("0.00");					 
					 $('#hiddenCostoURDP').val("0.00");					 
					 $('#hiddenCostoUDEP').val("0.00");						 
					 $('#hiddenCostoUVFP').val("0.00");					 
					 $('#hiddenCostoUVDP').val("0.00");						 
					 $('#hiddenCostoUASP').val("0.00");
					 $('#hiddenCostoUGAP').val("0.00");			 
					 
					 if(formato12B.cmbCodEmpresa.val().trim()=='EDLN' || 
							 formato12B.cmbCodEmpresa.val().trim()=='LDS'){
						 
						 formato12B.txtEtndrUnitValeImpreLim.val("0.00");						 
						 formato12B.txtEtndrUnitValeReparLim.val("0.00");	 
						 formato12B.txtEtndrUnitValDisElLim.val("0.00");		 
						 formato12B.txtEtndrUnitValFiCanLim.val("0.00");						 
						 formato12B.txtEtndrUnitValDgCanLim.val("0.00");					 
						 formato12B.txtEtndrUnitAtencionLim.val("0.00");			
						 formato12B.txtTotalGestionAdministrativaLim.val("0.00");
						 
						 $('#hiddenCostoUIVL').val("0.00");				
						 $('#hiddenCostoURDL').val("0.00");							
						 $('#hiddenCostoUDEL').val("0.00");							 
						 $('#hiddenCostoUVFL').val("0.00");							 
						 $('#hiddenCostoUVDL').val("0.00");					
						 $('#hiddenCostoUASL').val("0.00");
						 $('#hiddenCostoUGAL').val("0.00");					 
						 
					 }			
					formato12B.dialogMessageInfoDetalleContent.html("No existe Costos Est�ndares Establecidos en el Formato 14B para la Distribuidora El�ctrica y Periodo a Declarar seleccionado");
					formato12B.dialogMessageInfoDetalle.dialog("open");
			}
			formato12B.loadCostoTotal(formato12B.cmbCodEmpresa.val());			
		},	
		
		
		//cambios elozano para setear data a los campos hidden de costos unitarios al editar un formato
		loadDataCostoUnitarioHiddenEditar: function(data){
			console.debug("Entrando a llenar campos de los costos unitarios hidden al editar formato");
			
			if(data!=null && data.length>0){			
				$.each(data, function (i, item) {		 
					 
					if(item.idZonaBenef == '1'){						 
						 $('#hiddenCostoUIVR').val(item.costoUnitarioImpresionVales);					
						 $('#hiddenCostoURDR').val(item.costoUnitReprtoValeDomici);					 
						 $('#hiddenCostoUDER').val(item.costoUnitEntregaValDisEl);					
						 $('#hiddenCostoUVFR').val(item.costoUnitCanjeLiqValFisi);				 
						 $('#hiddenCostoUVDR').val(item.costoUnitCanjeValDigital);					 
						 $('#hiddenCostoUASR').val(item.costoUnitarioPorAtencion);	
						 $('#hiddenCostoUGAR').val(item.costoUnitarioGestionAdm.toFixed(2));
						 
					 }else if(item.idZonaBenef == '2'){						
						 $('#hiddenCostoUIVP').val(item.costoUnitarioImpresionVales);					 
						 $('#hiddenCostoURDP').val(item.costoUnitReprtoValeDomici);								 
						 $('#hiddenCostoUDEP').val(item.costoUnitEntregaValDisEl);
						 console.debug("Costo unitrio dist electrica provincia editar:  "+$('#hiddenCostoUDEP').val());
						 $('#hiddenCostoUVFP').val(item.costoUnitCanjeLiqValFisi);					 
						 $('#hiddenCostoUVDP').val(item.costoUnitCanjeValDigital);						 
						 $('#hiddenCostoUASP').val(item.costoUnitarioPorAtencion);
						 $('#hiddenCostoUGAP').val(item.costoUnitarioGestionAdm.toFixed(2));	
					 }					
					 if(item.codEmpresa.trim() == 'EDLN' || item.codEmpresa.trim() == 'LDS'){
						 
						 if(item.idZonaBenef == '3'){							
							 $('#hiddenCostoUIVL').val(item.costoUnitarioImpresionVales);				
							 $('#hiddenCostoURDL').val(item.costoUnitReprtoValeDomici);							
							 $('#hiddenCostoUDEL').val(item.costoUnitEntregaValDisEl);							 
							 $('#hiddenCostoUVFL').val(item.costoUnitCanjeLiqValFisi);							 
							 $('#hiddenCostoUVDL').val(item.costoUnitCanjeValDigital);					
							 $('#hiddenCostoUASL').val(item.costoUnitarioPorAtencion);
							 $('#hiddenCostoUGAL').val(item.costoUnitarioGestionAdm.toFixed(2));	
						 }
					 }           
	             });//fin del for recorrido
				
			}else{					 
					 $('#hiddenCostoUIVR').val("0.00");					
					 $('#hiddenCostoURDR').val("0.00");					 
					 $('#hiddenCostoUDER').val("0.00");					
					 $('#hiddenCostoUVFR').val("0.00");				 
					 $('#hiddenCostoUVDR').val("0.00");					 
					 $('#hiddenCostoUASR').val("0.00");	
					 $('#hiddenCostoUGAR').val("0.00");				 
				
					 $('#hiddenCostoUIVP').val("0.00");					 
					 $('#hiddenCostoURDP').val("0.00");					 
					 $('#hiddenCostoUDEP').val("0.00");						 
					 $('#hiddenCostoUVFP').val("0.00");					 
					 $('#hiddenCostoUVDP').val("0.00");						 
					 $('#hiddenCostoUASP').val("0.00");
					 $('#hiddenCostoUGAP').val("0.00");			 
					 
					 if(formato12B.cmbCodEmpresa.val().trim()=='EDLN' || 
							 formato12B.cmbCodEmpresa.val().trim()=='LDS'){						 
						 $('#hiddenCostoUIVL').val("0.00");				
						 $('#hiddenCostoURDL').val("0.00");							
						 $('#hiddenCostoUDEL').val("0.00");							 
						 $('#hiddenCostoUVFL').val("0.00");							 
						 $('#hiddenCostoUVDL').val("0.00");					
						 $('#hiddenCostoUASL').val("0.00");
						 $('#hiddenCostoUGAL').val("0.00");					 
						 
					 }			
			}//fin del else data cero					
		},	
		
		
		
		
		
		
		
		
		
		inicializarValoresCostos:function(){
			console.debug("inicializando todos los unitarios con cero");
			
			 formato12B.txtEtndrUnitValeImpre.val("0.00");			
			 formato12B.txtEtndrUnitValeRepar.val("0.00");			
			 formato12B.txtEtndrUnitValDisEl.val("0.00");			
			 formato12B.txtEtndrUnitValFiCan.val("0.00");			
			 formato12B.txtEtndrUnitValDgCan.val("0.00");			
			 formato12B.txtEtndrUnitAtencion.val("0.00");			
			 formato12B.txtTotalGestionAdministrativa.val("0.00");
			 			 
			 formato12B.txtEtndrUnitValeImpreProv.val("0.00");			 
			 formato12B.txtEtndrUnitValeReparProv.val("0.00");			 
			 formato12B.txtEtndrUnitValDisElProv.val("0.00");			 
			 formato12B.txtEtndrUnitValFiCanProv.val("0.00");			 
			 formato12B.txtEtndrUnitValDgCanProv.val("0.00");			 
			 formato12B.txtEtndrUnitAtencionProv.val("0.00");		
			 formato12B.txtTotalGestionAdministrativaProv.val("0.00");		
			 				
			 formato12B.txtEtndrUnitValeImpreLim.val("0.00");			
			 formato12B.txtEtndrUnitValeReparLim.val("0.00");			
			 formato12B.txtEtndrUnitValDisElLim.val("0.00");			
			 formato12B.txtEtndrUnitValFiCanLim.val("0.00");			
			 formato12B.txtEtndrUnitValDgCanLim.val("0.00");			
			 formato12B.txtEtndrUnitAtencionLim.val("0.00");			
			 formato12B.txtTotalGestionAdministrativaLim.val("0.00");		
		},
		
		
		loadCostoTotal:function(emp){
			console.debug("Entrando a costos load totales");
			
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
			
			console.debug("costo reparto a domicilio:   "+formato12B.txtnroValesRepartidosDomi.val()*formato12B.txtEtndrUnitValeRepar.val());
			console.debug("costo reparto a domicilio:   "+parseFloat(formato12B.txtTotalRepartoValesDomi.val()).toFixed(2));
			
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
			console.debug("Entrando a load totales");
			
			if(emp.trim() =='EDLN' || emp.trim() =='LDS'){				
				console.debug("Entrando a load totales lima");
				
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
						
				$('#porGestionAdm').val(parseFloat($('#totalGestionAdministrativa').val().length>0?$('#totalGestionAdministrativa').val():'0')
			               +parseFloat($('#totalGestionAdministrativaProv').val().length>0?$('#totalGestionAdministrativaProv').val():'0'));
	
	            $('#porDesplazamientoPers').val(parseFloat($('#totalDesplazamientoPersonal').val().length>0?$('#totalDesplazamientoPersonal').val():'0')
			             +parseFloat($('#totalDesplazamientoPersonalProv').val().length>0?$('#totalDesplazamientoPersonalProv').val():'0'));
	
	            $('#porActividadExtra').val(parseFloat($('#totalActividadesExtraord').val().length>0?$('#totalActividadesExtraord').val():'0')
			            +parseFloat($('#totalActividadesExtraordProv').val().length>0?$('#totalActividadesExtraordProv').val():'0'));
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
			 if(formato12B.cmbCodEmpresa.val().trim()=='EDLN' || 
					 formato12B.cmbCodEmpresa.val().trim()=='LDS'){
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
			$('#totalGeneralReconocer').val(Number($('#totalGeneralReconocer').val()).toFixed(2));
		},		
		
		
		loadCostoTotatByInput:function (total,nro,costo,idhidden,idstandar,idtotal){
			
			console.debug("Entrando a costo total de cada input total:  "+total.val());			
			
			total.val((nro.val()!=null && nro.length>0)?(nro.val()*costo.val()):'0.00');
			var resultado = Math.round(total.val()*100)/100;
			total.val(resultado.toFixed(2));
			
			var indexpunto = total.val().indexOf('.');			
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
			 var totalTOri = $("#"+idtotal).val();
			 var totalT= (parseFloat((totalTOri!=null && totalTOri.length>0)?totalTOri:'0.00')).toFixed(2); 			
			
			if(totalT.length>0){
				$("#"+idtotal).val(totalT.substring(0,totalT.indexOf('.')+3));					
			}else{
				$("#"+idtotal).val(totalT);	
			}		
             formato12B.loadTotalReconocer();	
		},
		
		
		showConfirmacion : function (emp,mes,anio,etapa,desmes,stdEnvio,std,mesEjec,desmesEjec,anioEjec,tipo) {		
			var admin = '${esAdministrador}';
			var process = true;
			var show=true;
			var numModal=2;//modsl aviso
			var msj='El formato ya fue enviado a OSINERGMIN-GART';
			switch(tipo) {
				case '0':{//vista
					show=false;
					formato12B.blockUI();
					location.href=formato12B.urlViewFormato+'&codEmpresa='+emp+'&anoPresentacion='+anio+'&mesPresentacion='+mes+'&etapa='+etapa+'&desmes='+desmes+'&tipoOperacion='+tipo+'&mesEjecucionGasto='+mesEjec+'&descMesEjec='+desmesEjec+'&anoEjecucionGasto='+anioEjec+'&'+formato12B.formBusqueda.serialize();
				}break;
				case '1':{//edit
					if(std =='ABIERTO'){//1enviado 0=x enviar
						if( etapa=='RECONOCIDO'  &&  admin=='false' ){
							process = false;
						}
						if(process){
							show=false;//no muestra modal
							formato12B.blockUI();
							location.href=formato12B.urlViewFormato+'&codEmpresa='+emp+'&anoPresentacion='+anio+'&mesPresentacion='+mes+'&etapa='+etapa+'&desmes='+desmes+'&tipoOperacion='+tipo+'&mesEjecucionGasto='+mesEjec+'&descMesEjec='+desmesEjec+'&anoEjecucionGasto='+anioEjec+'&'+formato12B.formBusqueda.serialize();
						}else{
							show=false;
					   		msj=" No tiene autorizaci�n para realizar esta acci�n";
						}
					 }else if(std=='CERRADO'){
						show=false;
						msj=" El plazo para realizar esta acci�n se encuentra cerrado";
					 }
				}break;
				case '3':{//eliminar
					if(std=='ABIERTO'){//1enviado 0=x enviar
						if( etapa=='RECONOCIDO'  &&  admin=='false' ){
							process = false;
						}
						if(process){
							show=true;
							msj='�Est� seguro que desea eliminar el registro seleccionado?';
							numModal=1;
						}else{
							show=false;
					   		msj=" No tiene autorizaci�n para realizar esta acci�n";
						}
				    }else if(std=='CERRADO'){
				    	show=false;
				    	msj=" El plazo para realizar esta acci�n se encuentra cerrado";
					}
				 }break;
			}
			
            if(show){				
				if(numModal == 1){					
					formato12B.dialogMessageConfirmContent.html(msj);
					formato12B.dialogMessageConfirm.dialog("open");
					codEmp = emp;
					mesPre = mes;
					anioPre= anio;
					codEtapa = etapa;
					mesEjecu= mesEjec;
					anioEjecu = anioEjec;					
				}else{					
					formato12B.dialogMessageInfoContent.html(msj);
					formato12B.dialogMessageInfo.dialog("open");
				}	
			}
		},	
		
		
		showLoadFile:function(tipoFile){
			
				formato12B.txtTypeFile.val(tipoFile);
				formato12B.divOverlay.show();
			    formato12B.dlgLoadFile.show();
			    formato12B.dlgLoadFile.draggable();
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
					if(extension == 'xls' || extension == 'xlsx' || extension == 'XLS'|| extension == 'XLSX'){
						isSubmit=true;
					}else{
						isSubmit=false;
						$("#msjUploadFile").html("Archivo inv�lido");
					}
					
				}else if(tipoFile == '2'){
					if(extension == 'txt' ){
						isSubmit=true;
					}else{
						isSubmit=false;
						$("#msjUploadFile").html("Archivo inv�lido");
					}
				}
			}
			
			if(isSubmit){
				 formato12B.formNewEdit.submit(); 
			}	
		},
		
		///////////////////////cambios elozano//////////////////////////		
		//funcion para editar campos de costos unitarios
		mostrarCamposCostosUnitarios: function(){			
			formato12B.txtEtndrUnitValeImpre.removeAttr("disabled");
			formato12B.txtEtndrUnitValeImpreProv.removeAttr("disabled");
			formato12B.txtEtndrUnitValeRepar.removeAttr("disabled");
			formato12B.txtEtndrUnitValeReparProv.removeAttr("disabled");
			formato12B.txtEtndrUnitValDisEl.removeAttr("disabled");
			formato12B.txtEtndrUnitValDisElProv.removeAttr("disabled");
			formato12B.txtEtndrUnitValFiCan.removeAttr("disabled");
			formato12B.txtEtndrUnitValFiCanProv.removeAttr("disabled");
			formato12B.txtEtndrUnitValDgCan.removeAttr("disabled");
			formato12B.txtEtndrUnitValDgCanProv.removeAttr("disabled");
			formato12B.txtEtndrUnitAtencion.removeAttr("disabled");
			formato12B.txtEtndrUnitAtencionProv.removeAttr("disabled");
			formato12B.txtTotalGestionAdministrativa.removeAttr("disabled");
			formato12B.txtTotalGestionAdministrativaProv.removeAttr("disabled");
			console.debug("cod empresa al editaro ocultar costos unitarios: "+formato12B.cmbCodEmpresa.val());
			if(formato12B.cmbCodEmpresa.val().trim()=='EDLN' || formato12B.cmbCodEmpresa.val().trim()=='LDS'){			
				formato12B.txtEtndrUnitValeImpreLim.removeAttr("disabled");
				formato12B.txtEtndrUnitValeReparLim.removeAttr("disabled");
				formato12B.txtEtndrUnitValDisElLim.removeAttr("disabled");
				formato12B.txtEtndrUnitValFiCanLim.removeAttr("disabled");
				formato12B.txtEtndrUnitValDgCanLim.removeAttr("disabled");
				formato12B.txtEtndrUnitAtencionLim.removeAttr("disabled");			
				formato12B.txtTotalGestionAdministrativaLim.removeAttr("disabled");
			}else{
				formato12B.txtEtndrUnitValeImpreLim.prop('disabled', true);
				formato12B.txtEtndrUnitValeReparLim.prop('disabled', true);
				formato12B.txtEtndrUnitValDisElLim.prop('disabled', true);
				formato12B.txtEtndrUnitValFiCanLim.prop('disabled', true);
				formato12B.txtEtndrUnitValDgCanLim.prop('disabled', true);
				formato12B.txtEtndrUnitAtencionLim.prop('disabled', true);			
				formato12B.txtTotalGestionAdministrativaLim.prop('disabled', true);
			}
		},
		
		//funcion para no editar campos de costos unitarios
		ocultarCamposCostosUnitarios: function(){				
			formato12B.txtEtndrUnitValeImpre.prop('disabled', true);
			formato12B.txtEtndrUnitValeImpreProv.prop('disabled', true);
			formato12B.txtEtndrUnitValeRepar.prop('disabled', true);
			formato12B.txtEtndrUnitValeReparProv.prop('disabled', true);
			formato12B.txtEtndrUnitValDisEl.prop('disabled', true);
			formato12B.txtEtndrUnitValDisElProv.prop('disabled', true);
			formato12B.txtEtndrUnitValFiCan.prop('disabled', true);
			formato12B.txtEtndrUnitValFiCanProv.prop('disabled', true);
			formato12B.txtEtndrUnitValDgCan.prop('disabled', true);
			formato12B.txtEtndrUnitValDgCanProv.prop('disabled', true);
			formato12B.txtEtndrUnitAtencion.prop('disabled', true);
			formato12B.txtEtndrUnitAtencionProv.prop('disabled', true);
			formato12B.txtTotalGestionAdministrativa.prop('disabled', true);
			formato12B.txtTotalGestionAdministrativaProv.prop('disabled', true);
			console.debug("cod empresa al editaro ocultar costos unitarios: "+formato12B.cmbCodEmpresa.val());			
			if(formato12B.cmbCodEmpresa.val().trim()=='EDLN' || formato12B.cmbCodEmpresa.val().trim()=='LDS'){
				formato12B.txtEtndrUnitValeImpreLim.prop('disabled', true);
				formato12B.txtEtndrUnitValeReparLim.prop('disabled', true);
				formato12B.txtEtndrUnitValDisElLim.prop('disabled', true);
				formato12B.txtEtndrUnitValFiCanLim.prop('disabled', true);
				formato12B.txtEtndrUnitValDgCanLim.prop('disabled', true);
				formato12B.txtEtndrUnitAtencionLim.prop('disabled', true);
				formato12B.txtTotalGestionAdministrativaLim.prop('disabled', true);
			}else{
				formato12B.txtEtndrUnitValeImpreLim.prop('disabled', true);
				formato12B.txtEtndrUnitValeReparLim.prop('disabled', true);
				formato12B.txtEtndrUnitValDisElLim.prop('disabled', true);
				formato12B.txtEtndrUnitValFiCanLim.prop('disabled', true);
				formato12B.txtEtndrUnitValDgCanLim.prop('disabled', true);
				formato12B.txtEtndrUnitAtencionLim.prop('disabled', true);
				formato12B.txtTotalGestionAdministrativaLim.prop('disabled', true);
			}
				
		},
		
		//fin de cambios elozano
		
		
		showCamposOculto: function(){			
			formato12B.divgrupoestado.css("display","block");
			formato12B.cmbCodEmpresa.prop('disabled', true);
			formato12B.cmbPeriodo.prop('disabled', true);		
			formato12B.txtAnioEjec.prop("type","text");			
			formato12B.txtAnioEjec.prop("type","hidden");			
			formato12B.txtAnioEjecCommand.prop('disabled', true);
			formato12B.cmbMesEjecucion.prop('disabled', true);		
		},
		
		
		mostrarPeriodoEjecucion : function(){
			if( $('#flagPeriodoEjecucion').val()=='S' ){
				formato12B.txtAnioEjecCommand.removeAttr("disabled");
				formato12B.cmbMesEjecucion.removeAttr("disabled");
				formato12B.txtAnioEjecCommand.addClass("fise-editable");
			}else{
				formato12B.txtAnioEjecCommand.attr("disabled",true);
				formato12B.cmbMesEjecucion.attr("disabled",true);
				formato12B.txtAnioEjecCommand.removeClass("fise-editable");
			}
		},
		
		//cambios elozano para mostrar o ocultar costos unitarios
		mostrarEditarCostosUnitariosEstado : function(){
			if($('#hiddenFlagCostoEstandar').val()=='S' ){
				formato12B.mostrarCamposCostosUnitarios();
			}else{
				formato12B.ocultarCamposCostosUnitarios();
			}
		},
		
		
		
		validateSave :function(tipoOperacion){			
			var codemp = formato12B.cmbCodEmpresa.val();				
			var flagLima = '0';
			var flagEditCosto = false;			
			if(tipoOperacion=='1'){//edit
				codemp=$("#codEmpresaHidden").val();
			}
			if(codemp == 'EDLN' || codemp.trim() == 'LDS'){
				flagLima='1';	
			}			
			if($('#hiddenFlagCostoEstandar').val()=='S'){
				flagEditCosto = true;	
			}	
			
			console.debug("Costo unitrio dist electrica provincia al validar:  "+$('#hiddenCostoUDEP').val());
			
			if(codemp.length == '') { 					
				var addhtmAlert='Seleccione una Distribuidora El�ctrica.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
			  	return false; 
			}			
			//Rural
			else if(formato12B.txtnroValesImpreso.val().length==''){
				var addhtmAlert='Debe ingresar el N�mero de Vales Impresos de la Zona Rural.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(flagEditCosto && formato12B.txtEtndrUnitValeImpre.val().length==''){
				var addhtmAlert='Debe ingresar el Costo Est�ndar Unitario de Vales Impresos de la Zona Rural.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(formato12B.txtnroValesRepartidosDomi.val().length==''){
				var addhtmAlert='Debe ingresar el N�mero de Vales repartido a domicilio de la Zona Rural.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(flagEditCosto && formato12B.txtEtndrUnitValeRepar.val().length==''){
				var addhtmAlert='Debe ingresar el Costo Est�ndar Unitario de Vales repartido a domicilio de la Zona Rural.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(formato12B.txtnroValesEntregadoDisEl.val().length==''){
				var addhtmAlert='Debe ingresar el N�mero de Vales entregados en la Distribuidora El�ctrica de la Zona Rural.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(flagEditCosto && formato12B.txtEtndrUnitValDisEl.val().length==''){
				var addhtmAlert='Debe ingresar el Costo Est�ndar Unitario de Vales entregados en la Distribuidora El�ctrica de la Zona Rural.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(formato12B.txtnroValesFisicosCanjeados.val().length==''){
				var addhtmAlert='Debe ingresar el N�mero de Vales F�sicos Canjeados de la Zona Rural.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(flagEditCosto && formato12B.txtEtndrUnitValFiCan.val().length==''){
				var addhtmAlert='Debe ingresar el Costo Est�ndar Unitario de Vales F�sicos Canjeados de la Zona Rural.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(formato12B.txtnroValesDigitalCanjeados.val().length==''){
				var addhtmAlert='Debe ingresar el N�mero de Vales Digitales Canjeados de la Zona Rural.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(flagEditCosto && formato12B.txtEtndrUnitValDgCan.val().length==''){
				var addhtmAlert='Debe ingresar el Costo Est�ndar Unitario de Vales Digitales Canjeados de la Zona Rural.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(formato12B.txtnroAtenciones.val().length==''){
				var addhtmAlert='Debe ingresar el N�mero de Atenciones de la Zona Rural.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(flagEditCosto && formato12B.txtEtndrUnitAtencion.val().length==''){
				var addhtmAlert='Debe ingresar el Costo Est�ndar Unitario de Atenciones de la Zona Rural.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(flagEditCosto && formato12B.txtTotalGestionAdministrativa.val().length==''){
				var addhtmAlert='Debe ingresar Gesti�n Administrativa de la Zona Rural.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(formato12B.txtTotalDesplazamientoPersonal.val().length==''){
				var addhtmAlert='Debe ingresar Desplazamiento de Personal de la Zona Rural.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(formato12B.txtTotalActividadesExtraord.val().length==''){
				var addhtmAlert='Debe ingresar  Actividades Extraordinarias de la Zona Rural.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}
			//Provincia
			else if(formato12B.txtnroValesImpresoProv.val().length==''){
				var addhtmAlert='Debe ingresar el N�mero de Vales Impresos de la Zona Urbano Provincia.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;  
			}else if(flagEditCosto && formato12B.txtEtndrUnitValeImpreProv.val().length==''){
				var addhtmAlert='Debe ingresar el Costo Est�ndar Unitario de Vales Impresos de la Zona Urbano Provincia.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;  
			}else if(formato12B.txtnroValesRepartidosDomiProv.val().length==''){
				var addhtmAlert='Debe ingresar el N�mero de Vales repartido a domicilio de la Zona Urbano Provincia.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(flagEditCosto && formato12B.txtEtndrUnitValeReparProv.val().length==''){
				var addhtmAlert='Debe ingresar el Costo Est�ndar Unitario de Vales repartido a domicilio de la Zona Urbano Provincia.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(formato12B.txtnroValesEntregadoDisElProv.val().length==''){
				var addhtmAlert='Debe ingresar el N�mero de Vales entregados en la Distribuidora El�ctrica de la Zona Urbano Provincia.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;
			}else if(flagEditCosto && formato12B.txtEtndrUnitValDisElProv.val().length==''){
				var addhtmAlert='Debe ingresar el Costo Est�ndar Unitario de Vales entregados en la Distribuidora El�ctrica de la Zona Urbano Provincia.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;
			}else if(formato12B.txtnroValesFisicosCanjeadosProv.val().length==''){
				var addhtmAlert='Debe ingresar el N�mero de Vales F�sicos Canjeados de la Zona Urbano Provincia.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(flagEditCosto && formato12B.txtEtndrUnitValFiCanProv.val().length==''){
				var addhtmAlert='Debe ingresar el Costo Est�ndar Unitario de Vales F�sicos Canjeados de la Zona Urbano Provincia.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(formato12B.txtnroValesDigitalCanjeadosProv.val().length==''){
				var addhtmAlert='Debe ingresar el N�mero de Vales Digitales Canjeados de la Zona Urbano Provincia.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(flagEditCosto && formato12B.txtEtndrUnitValDgCanProv.val().length==''){
				var addhtmAlert='Debe ingresar el Costo Est�ndar Unitario de Vales Digitales Canjeados de la Zona Urbano Provincia.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(formato12B.txtnroAtencionesProv.val().length==''){
				var addhtmAlert='Debe ingresar el N�mero de Atenciones de la Zona Urbano Provincia.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(flagEditCosto && formato12B.txtEtndrUnitAtencionProv.val().length==''){
				var addhtmAlert='Debe ingresar el Costo Est�ndar Unitario de Atenciones de la Zona Urbano Provincia.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(flagEditCosto && formato12B.txtTotalGestionAdministrativaProv.val().length==''){
				var addhtmAlert='Debe ingresar Gesti�n Administrativa de la Zona Urbano Provincia.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(formato12B.txtTotalDesplazamientoPersonalProv.val().length==''){
				var addhtmAlert='Debe ingresar Desplazamiento de Personal de la Zona Urbano Provincia.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(formato12B.txtTotalActividadesExtraordProv.val().length==''){
				var addhtmAlert='Debe ingresar Actividades Extraordinarias de la Zona Urbano Provincia.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;
			}
			//LIMA				
			else if(formato12B.txtnroValesImpresoLim.val().length=='' && flagLima=='1'){
				var addhtmAlert='Debe ingresar el N�mero de Vales Impresos de la Zona Urbano Lima.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;  
			}else if(flagEditCosto && formato12B.txtEtndrUnitValeImpreLim.val().length=='' && flagLima=='1'){
				var addhtmAlert='Debe ingresar el Costo Est�ndar Unitario de Vales Impresos de la Zona Urbano Lima.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;  
			}else if(formato12B.txtnroValesRepartidosDomiLim.val().length=='' && flagLima=='1'){
				var addhtmAlert='Debe ingresar el N�mero de Vales repartido a domicilio de la Zona Urbano Lima.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(flagEditCosto && formato12B.txtEtndrUnitValeReparLim.val().length=='' && flagLima=='1'){
				var addhtmAlert='Debe ingresar el Costo Est�ndar Unitario de Vales repartido a domicilio de la Zona Urbano Lima.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(formato12B.txtnroValesEntregadoDisElLim.val().length=='' && flagLima=='1'){
				var addhtmAlert='Debe ingresar el N�mero de Vales entregados en la Distribuidora El�ctrica de la Zona Urbano Lima.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;
			}else if(flagEditCosto && formato12B.txtEtndrUnitValDisElLim.val().length=='' && flagLima=='1'){
				var addhtmAlert='Debe ingresar el Costo Est�ndar Unitario de Vales entregados en la Distribuidora El�ctrica de la Zona Urbano Lima.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;
			}else if(formato12B.txtnroValesFisicosCanjeadosLim.val().length=='' && flagLima=='1'){
				var addhtmAlert='Debe ingresar el N�mero de Vales F�sicos Canjeados de la Zona Urbano Lima.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(flagEditCosto && formato12B.txtEtndrUnitValFiCanLim.val().length=='' && flagLima=='1'){
				var addhtmAlert='Debe ingresar el Costo Est�ndar Unitario de Vales F�sicos Canjeados de la Zona Urbano Lima.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(formato12B.txtnroValesDigitalCanjeadosLim.val().length=='' && flagLima=='1'){
				var addhtmAlert='Debe ingresar el N�mero de Vales Digitales Canjeados de la Zona Urbano Lima.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(flagEditCosto && formato12B.txtEtndrUnitValDgCanLim.val().length=='' && flagLima=='1'){
				var addhtmAlert='Debe ingresar el Costo Est�ndar Unitario de Vales Digitales Canjeados de la Zona Urbano Lima.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(formato12B.txtnroAtencionesLim.val().length=='' && flagLima=='1'){
				var addhtmAlert='Debe ingresar el N�mero de Atenciones de la Zona Urbano Lima.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(flagEditCosto && formato12B.txtEtndrUnitAtencionLim.val().length=='' && flagLima=='1'){
				var addhtmAlert='Debe ingresar el Costo Est�ndar Unitario de Atenciones de la Zona Urbano Lima.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(flagEditCosto && formato12B.txtTotalGestionAdministrativaLim.val().length=='' && flagLima=='1'){
				var addhtmAlert='Debe ingresar Gesti�n Administrativa de la Zona Urbano Lima.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(formato12B.txtTotalDesplazamientoPersonalLim.val().length=='' && flagLima=='1'){
				var addhtmAlert='Debe ingresar Desplazamiento de Personal de la Zona Urbano Lima.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false; 
			}else if(formato12B.txtTotalActividadesExtraordLim.val().length=='' && flagLima=='1'){
				var addhtmAlert='Debe ingresar Actividades Extraordinarias de la Zona Urbano Lima.';					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;
			}
			
			//validacion de los costos estandares unitarios que no sean mayores a los extraidos del formato 14B
			else if(flagEditCosto && (parseFloat(formato12B.txtEtndrUnitValeImpre.val())>parseFloat($('#hiddenCostoUIVR').val()))){			  
				var addhtmAlert='El Costo Est�ndar Unitario de Impresi�n de Vales para Rural no debe ser mayor a '+$('#hiddenCostoUIVR').val();					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;  
			}else if(flagEditCosto && (parseFloat(formato12B.txtEtndrUnitValeImpreProv.val())>parseFloat($('#hiddenCostoUIVP').val()))){			  
				var addhtmAlert='El Costo Est�ndar Unitario de Impresi�n de Vales para Provincia no debe ser mayor a '+$('#hiddenCostoUIVP').val();					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;  
			}else if(flagEditCosto && flagLima=='1' && (parseFloat(formato12B.txtEtndrUnitValeImpreLim.val())>parseFloat($('#hiddenCostoUIVL').val()))){			  
				var addhtmAlert='El Costo Est�ndar Unitario de Impresi�n de Vales para Lima no debe ser mayor a '+$('#hiddenCostoUIVL').val();					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;  
			}	
			
			else if(flagEditCosto && (parseFloat(formato12B.txtEtndrUnitValeRepar.val())>parseFloat($('#hiddenCostoURDR').val()))){			  
				var addhtmAlert='El Costo Est�ndar Unitario de Reparto de Vales a Domicilio para Rural no debe ser mayor a '+$('#hiddenCostoURDR').val();					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;  
			}else if(flagEditCosto && (parseFloat(formato12B.txtEtndrUnitValeReparProv.val())>parseFloat($('#hiddenCostoURDP').val()))){			  
				var addhtmAlert='El Costo Est�ndar Unitario de Reparto de Vales a Domicilio para Provincia no debe ser mayor a '+$('#hiddenCostoURDP').val();					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;  
			}else if(flagEditCosto && flagLima=='1' && (parseFloat(formato12B.txtEtndrUnitValeReparLim.val())>parseFloat($('#hiddenCostoURDL').val()))){			  
				var addhtmAlert='El Costo Est�ndar Unitario de Reparto de Vales a Domicilio para Lima no debe ser mayor a '+$('#hiddenCostoURDL').val();					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;  
			}
						
			else if(flagEditCosto && (parseFloat(formato12B.txtEtndrUnitValDisEl.val())>parseFloat($('#hiddenCostoUDER').val()))){			  
				var addhtmAlert='El Costo Est�ndar Unitario de Entrega de Vales en la Distribuidora El�ctrica para Rural no debe ser mayor a '+$('#hiddenCostoUDER').val();					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;  
			}else if(flagEditCosto && (parseFloat(formato12B.txtEtndrUnitValDisElProv.val())>parseFloat($('#hiddenCostoUDEP').val()))){			  
				var addhtmAlert='El Costo Est�ndar Unitario de Entrega de Vales en la Distribuidora El�ctrica para Provincia no debe ser mayor a '+$('#hiddenCostoUDEP').val();					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;  
			}else if(flagEditCosto && flagLima=='1' && (parseFloat(formato12B.txtEtndrUnitValDisElLim.val())>parseFloat($('#hiddenCostoUDEL').val()))){			  
				var addhtmAlert='El Costo Est�ndar Unitario de Entrega de Vales en la Distribuidora El�ctrica para Lima no debe ser mayor a '+$('#hiddenCostoUDEL').val();					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;  
			}
			
			else if(flagEditCosto && (parseFloat(formato12B.txtEtndrUnitValFiCan.val())>parseFloat($('#hiddenCostoUVFR').val()))){			  
				var addhtmAlert='El Costo Est�ndar Unitario de Canje y Liquidaci�n de Vales F�sicos para Rural no debe ser mayor a '+$('#hiddenCostoUVFR').val();					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;  
			}else if(flagEditCosto && (parseFloat(formato12B.txtEtndrUnitValFiCanProv.val())>parseFloat($('#hiddenCostoUVFP').val()))){			  
				var addhtmAlert='El Costo Est�ndar Unitario de Canje y Liquidaci�n de Vales F�sicos para Provincia no debe ser mayor a '+$('#hiddenCostoUVFP').val();					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;  
			}else if(flagEditCosto && flagLima=='1' && (parseFloat(formato12B.txtEtndrUnitValFiCanLim.val())>parseFloat($('#hiddenCostoUVFL').val()))){			  
				var addhtmAlert='El Costo Est�ndar Unitario de Canje y Liquidaci�n de Vales F�sicos para Lima no debe ser mayor a '+$('#hiddenCostoUVFL').val();					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;  
			}
			
			else if(flagEditCosto && (parseFloat(formato12B.txtEtndrUnitValDgCan.val())>parseFloat($('#hiddenCostoUVDR').val()))){			  
				var addhtmAlert='El Costo Est�ndar Unitario de Canje y Liquidaci�n de Vales Digitales para Rural no debe ser mayor a '+$('#hiddenCostoUVDR').val();					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;  
			}else if(flagEditCosto && (parseFloat(formato12B.txtEtndrUnitValDgCanProv.val())>parseFloat($('#hiddenCostoUVDP').val()))){			  
				var addhtmAlert='El Costo Est�ndar Unitario de Canje y Liquidaci�n de Vales Digitales para Provincia no debe ser mayor a '+$('#hiddenCostoUVDP').val();					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;  
			}else if(flagEditCosto && flagLima=='1' && (parseFloat(formato12B.txtEtndrUnitValDgCanLim.val())>parseFloat($('#hiddenCostoUVDL').val()))){			  
				var addhtmAlert='El Costo Est�ndar Unitario de Canje y Liquidaci�n de Vales Digitales para Lima no debe ser mayor a '+$('#hiddenCostoUVDL').val();					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;  
			}
			
			else if(flagEditCosto && (parseFloat(formato12B.txtEtndrUnitAtencion.val())>parseFloat($('#hiddenCostoUASR').val()))){			  
				var addhtmAlert='El Costo Est�ndar Unitario de Atenci�n de Solicitudes para Rural no debe ser mayor a '+$('#hiddenCostoUASR').val();					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;  
			}else if(flagEditCosto && (parseFloat(formato12B.txtEtndrUnitAtencionProv.val())>parseFloat($('#hiddenCostoUASP').val()))){			  
				var addhtmAlert='El Costo Est�ndar Unitario de Atenci�n de Solicitudes para Provincia no debe ser mayor a '+$('#hiddenCostoUASP').val();					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;  
			}else if(flagEditCosto && flagLima=='1' && (parseFloat(formato12B.txtEtndrUnitAtencionLim.val())>parseFloat($('#hiddenCostoUASL').val()))){			  
				var addhtmAlert='El Costo Est�ndar Unitario de Atenci�n de Solicitudes para Lima no debe ser mayor a '+$('#hiddenCostoUASL').val();					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;  
			}	
			
			else if(flagEditCosto && (parseFloat(formato12B.txtTotalGestionAdministrativa.val())>parseFloat($('#hiddenCostoUGAR').val()))){			  
				var addhtmAlert='El Costo de Gesti�n Administrativa para Rural no debe ser mayor a '+$('#hiddenCostoUGAR').val();					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;  
			}else if(flagEditCosto && (parseFloat(formato12B.txtTotalGestionAdministrativaProv.val())>parseFloat($('#hiddenCostoUGAP').val()))){			  
				var addhtmAlert='El Costo de Gesti�n Administrativa para Provincia no debe ser mayor a '+$('#hiddenCostoUGAP').val();					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;  
			}else if(flagEditCosto && flagLima=='1' && (parseFloat(formato12B.txtTotalGestionAdministrativaLim.val())>parseFloat($('#hiddenCostoUGAL').val()))){			  
				var addhtmAlert='El Costo de Gesti�n Administrativa para Lima no debe ser mayor a '+$('#hiddenCostoUGAL').val();					
				formato12B.dialogMessageWarningDetalleContent.html(addhtmAlert);
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;  
			}
			else{
				return true;	
			}			
		},
		
		//funcion para guardar y actualizar datos del formato
		save : function(){	
			console.debug("Entrando a guardar datos");
			var form = formato12B.formNewEdit.serialize();
			if(formato12B.validateSave(formato12B.tpOperacion.val())){				
				jQuery.ajax({	
					url: formato12B.urlSave,					
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
										
										//bloque para mostrar cuando se graba un formato
										formato12B.btnValidacion.css("display","block");
										formato12B.btnEnvioDefinitivo.css("display","block");
										formato12B.btnValidacion.click(function() {formato12B.showValidacion();});
										formato12B.btnEnvioDefinitivo.click(function() {formato12B.confirmarEnvioDefinitivo();});
										
										formato12B.lblMessage.html("El Formato 12B se guard� satisfactoriamente");
										formato12B.dialogMessageGeneral.dialog("open");
									}else{
										formato12B.tpOperacion.val('1');										
										formato12B.lblMessage.html("El Formato 12B se actualiz� satisfactoriamente");
										formato12B.dialogMessageGeneral.dialog("open");
									}						
									
									$('#<portlet:namespace/>guardarFormato').val('Actualizar');
									
								}else if(item.msg == "-1"){									
									formato12B.dialogMessageErrorDetalleContent.html("El Formato ya existe para la Distribuidora El�ctrica y Periodo a Declarar seleccionado");
									formato12B.dialogMessageErrorDetalle.dialog("open");									
								}else if(item.msg == "-2"){									
									formato12B.dialogMessageErrorDetalleContent.html("Error al obtener usuario");
									formato12B.dialogMessageErrorDetalle.dialog("open");									
								}else if(item.msg == "-3"){									
									formato12B.dialogMessageErrorDetalleContent.html("Error al obtener terminal");
									formato12B.dialogMessageErrorDetalle.dialog("open");									
								}else if(item.msg == "-4"){									
									formato12B.dialogMessageErrorDetalleContent.html("Se produjo un error al guardar los datos");
									formato12B.dialogMessageErrorDetalle.dialog("open");									
								}
							});
						}
						formato12B.unblockUI();
					},error : function(){
						alert("Error de conexi�n.");
						formato12B.unblockUI();
					}
			   });
			}			
		},
		
		validarFormulario : function(){
			if(formato12B.txtAnioEjec.val().length == '' ) {		  
				formato12B.dialogMessageWarningDetalleContent.html('Debe ingresar el A�o de Ejecuci�n');
				formato12B.dialogMessageWarningDetalle.dialog("open");
				formato12B.txtAnioEjec.focus();
			    return false; 
		  	}else{
			  	var numstr = trim(formato12B.txtAnioEjec.val());
			 	 if (isNaN(numstr) || numstr.length<4 || parseFloat(numstr)<1900){
			 		formato12B.dialogMessageWarningDetalleContent.html('Debe Ingresar un A�o de Ejecuci�n v�lido');
			 		formato12B.dialogMessageWarningDetalle.dialog("open");
				  	return false;
			  	}
		 	 }
			if(formato12B.cmbMesEjecucion.val().length == '' ) {		  
				formato12B.dialogMessageWarningDetalleContent.html('Debe ingresar el Mes de Ejecuci�n');
				formato12B.dialogMessageWarningDetalle.dialog("open");
				formato12B.cmbMesEjecucion.focus();
			   return false; 
			}
			
			if( parseFloat(formato12B.txtAnioEjec.val())*100 + parseFloat(formato12B.cmbMesEjecucion.val()) > parseFloat(formato12B.cmbPeriodo.val().substring(0,4))*100 + parseFloat(formato12B.cmbPeriodo.val().substring(4,6)) ){
				formato12B.dialogMessageWarningDetalleContent.html("El Periodo de Ejecuci�n no puede ser mayor al Periodo a Declarar");
				formato12B.dialogMessageWarningDetalle.dialog("open");
				return false;
			}
			return true;
		},
		
		
		deleteFormato:function(emp,mes,anio,etapa,mesEjec,anioEjec){
		
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
						formato12B.lblMessageInicial.html("El registro seleccionado del Formato 12B se elimin� satisfactoriamente.");
						formato12B.dialogMessageGeneralInicial.dialog("open");					
					}else if(data == '-1'){						
						formato12B.dialogMessageErrorContent.html("Error al eliminar registro");
						formato12B.dialogMessageError.dialog("open");						
					}
					formato12B.searchFormato();					
					formato12B.unblockUI();
				},error : function(){
					alert("Error de conexi�n.");
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
					alert("Error de conexi�n.");
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
			    	   var ids = formato12B.tablaObservacion.jqGrid('getDataIDs');
				       if(ids!=0){
				    		  location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';   
				       }else{
				    	var addhtmInfo='No existe informaci�n para exportar a Excel';				    	
				    	formato12B.dialogMessageInfoContent.html(addhtmInfo);
						formato12B.dialogMessageInfo.dialog("open");
				       }		           
			       } 
			}); 
			formato12B.tablaObservacion.jqGrid('navButtonAdd',formato12B.paginadoObservacion,{
			       caption:"Exportar a Pdf",
			       buttonicon: "ui-icon-bookmark",
			       onClickButton : function () {
			    	   var ids = formato12B.tablaObservacion.jqGrid('getDataIDs');
				       if(ids!=0){
				    	   formato12B.showReporteValidacion();   
				       }else{
				    	var addhtmInfo='No existe informaci�n para exportar a Excel';				    	
				    	formato12B.dialogMessageInfoContent.html(addhtmInfo);
						formato12B.dialogMessageInfo.dialog("open");
				       }		    	  
			       } 
			});
		},
		
		showValidacion : function(){
			jQuery.ajax({
				url: formato12B.urlValidacion+'&'+formato12B.formNewEdit.serialize(),
				type : 'post',
				dataType : 'json',	
				success : function(gridData) {
					if( gridData!=null ){
						formato12B.dialogObservacion.dialog("open");
						formato12B.tablaObservacion.clearGridData(true);
						formato12B.tablaObservacion.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
						formato12B.tablaObservacion[0].refreshIndex();
						formato12B.initBlockUI();
					}

				},error : function(){
					alert("Error de conexi�n.");
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
					alert("Error de conexi�n.");
					formato12B.initBlockUI();
				}
			});
		},
		
		confirmarEnvioDefinitivo : function(){	
			var addhtml='�Est� seguro que desea realizar el Env�o Definitivo para el Formato 12B?';
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
				success : function(data) {
					if(data.resultado == "OK"){
						var addhtml='El Env�o Definitivo se realiz� satisfactoriamente a los siguientes correos electr�nicos: '+data.correo;
						formato12B.lblMessageReportContent.html(addhtml);
						formato12B.dialogMessageReport.dialog("open");
						formato12B.initBlockUI();					
					}else if(data.resultado == "EMAIL"){						
						var addhtmEmail = data.correo;						
						formato12B.dialogMessageErrorDetalleContent.html(addhtmEmail);
						formato12B.dialogMessageErrorDetalle.dialog("open");
						formato12B.initBlockUI();
					}else if(data.resultado == "OBSERVACION"){						
						var addhtmObs = 'No se puede relizar el Env�o Definitivo del Formato 12B, primero debe subsanar las observaciones.';				
						formato12B.dialogMessageInfoContent.html(addhtmObs);
						formato12B.dialogMessageInfo.dialog("open");				
						formato12B.initBlockUI();
					}else{						
						var addhtmError='Error al realizar el Envio Definitivo del Formato 12B.';					
						formato12B.dialogMessageErrorDetalleContent.html(addhtmError);
						formato12B.dialogMessageErrorDetalle.dialog("open");						
						formato12B.initBlockUI();
					}				
				},error : function(){
					alert("Error de conexi�n.");
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
						alert("Error de conexi�n.");
						formato12B.initBlockUI();
					}
				});
			}else{				
			    formato12B.dialogMessageInfoDetalleContent.html("Primero debe realizar el Env�o Definitivo para el Formato 12B");
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
			formato12B.txtTotalDesplazamientoPersonalLim.addClass("fise-editable");
			formato12B.txtTotalActividadesExtraordLim.addClass("fise-editable");
		}else{			
			formato12B.txtnroValesImpresoLim.removeClass("fise-editable");
			formato12B.txtnroValesRepartidosDomiLim.removeClass("fise-editable");
			formato12B.txtnroValesEntregadoDisElLim.removeClass("fise-editable");
			formato12B.txtnroValesFisicosCanjeadosLim.removeClass("fise-editable");
			formato12B.txtnroValesDigitalCanjeadosLim.removeClass("fise-editable");
			formato12B.txtnroAtencionesLim.removeClass("fise-editable");			
			formato12B.txtTotalDesplazamientoPersonalLim.removeClass("fise-editable");
			formato12B.txtTotalActividadesExtraordLim.removeClass("fise-editable");		
		}
			
		},initDialogsInit : function(){	
				formato12B.dialogMessageGeneralInicial.dialog({
				modal: true,
				height: 200,
				width: 450,
				autoOpen: false,
				buttons: {
					Cerrar: function() {
						$( this ).dialog("close");
					}
				}
			});

            //add
			formato12B.dialogMessageConfirm.dialog({
				modal: true,
				autoOpen: false,
				width: 450,
				buttons: {
					"Si": function() {						
						formato12B.deleteFormato(codEmp, mesPre, anioPre, codEtapa,mesEjecu,anioEjecu);
						$(this).dialog("close");
					},
					"No": function() {				
						$(this).dialog("close");
					}
				}
			});
			formato12B.dialogMessageInfo.dialog({
				modal: true,
				autoOpen: false,
				width: 450,
				buttons: {
					Ok: function() {
						$( this ).dialog("close");
					}
				}
			});
			formato12B.dialogMessageWarning.dialog({
				modal: true,
				autoOpen: false,
				width: 450,
				buttons: {
					Ok: function() {
						$( this ).dialog("close");
					}
				}
			});
			formato12B.dialogMessageError.dialog({
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
					formato12B.btnBack.trigger("click");
				},error : function(){
					alert("Error de conexi�n.");
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
			formato12B.dialogError.dialog({
				modal: true,
				width: 750,
				autoOpen: false,
				buttons: {
					Cerrar: function() {
						$(this).dialog("close");
					}
				}
			});
			formato12B.dialogMessageReport.dialog({
				modal: true,
				autoOpen: false,
				width: 450,
				buttons: {
					'Ver Acta': function() {
						formato12B.mostrarReporteEnvioDefinitivo();						
						formato12B.dialogMessageReport.hide();
						formato12B.btnBuscar.trigger('click');
					},
					Ok: function() {						
						formato12B.dialogMessageReport.hide();
						formato12B.btnBack.trigger("click");
						formato12B.btnBuscar.trigger('click');
					}
				},
				close: function(event,ui){					
					formato12B.btnBack.trigger("click");
					formato12B.btnBuscar.trigger('click');
		       	}
			});
			
			formato12B.dialogConfirmEnvio.dialog({
				modal: true,
				height: 200,
				width: 450,			
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
				width: 450,
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
				width: 450,
				buttons: {
					Ok: function() {
						$( this ).dialog("close");
					}
				}
			});
			formato12B.dialogMessageWarningDetalle.dialog({
				modal: true,
				autoOpen: false,
				width: 450,
				buttons: {
					Ok: function() {
						$( this ).dialog("close");
					}
				}
			});
			formato12B.dialogMessageErrorDetalle.dialog({
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
		
		//funcion para valida el ingreso de numeros enteros
		validateInputTextNumber:function(id){
			$("#"+id).keyup(function () {
			    if (!this.value.match(/^([0-9]{0,10})$/)) {
			        this.value = this.value.replace(/[^0-9]/g, '').substring(0,10);
			    }
			});	
		},
		
		//funcion para valida el ingreso de numeros decimales
		validateInputTextDecimal:function(id){
			$("#"+id).keyup(function (event) {
			   $(this).val($(this).val().replace(/[^0-9\.]/g,''));			  
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
		},
			
						
		llenarDatosEditarView : function(){
			
			formato12B.txtnroValesImpreso.attr('value',(formato12B.txtnroValesImpreso.val()!=''?formato12B.txtnroValesImpreso.val():'0'));
			formato12B.txtnroValesImpresoProv.attr('value',(formato12B.txtnroValesImpresoProv.val()!=''?formato12B.txtnroValesImpresoProv.val():'0'));
			formato12B.txtnroValesImpresoLim.attr('value',(formato12B.txtnroValesImpresoLim.val()!=''?formato12B.txtnroValesImpresoLim.val():'0'));			
			formato12B.txtTotalImpresionVale.attr('value',(formato12B.txtTotalImpresionVale.val()!=''?formato12B.txtTotalImpresionVale.val():'0.00'));
			formato12B.txtTotalImpresionValeProv.attr('value',(formato12B.txtTotalImpresionValeProv.val()!=''?formato12B.txtTotalImpresionValeProv.val():'0.00'));
			formato12B.txtTotalImpresionValeLim.attr('value',(formato12B.txtTotalImpresionValeLim.val()!=''?formato12B.txtTotalImpresionValeLim.val():'0.00'));
					
			formato12B.txtnroValesRepartidosDomi.attr('value',(formato12B.txtnroValesRepartidosDomi.val()!=''?formato12B.txtnroValesRepartidosDomi.val():'0'));
			formato12B.txtnroValesRepartidosDomiProv.attr('value',(formato12B.txtnroValesRepartidosDomiProv.val()!=''?formato12B.txtnroValesRepartidosDomiProv.val():'0'));
			formato12B.txtnroValesRepartidosDomiLim.attr('value',(formato12B.txtnroValesRepartidosDomiLim.val()!=''?formato12B.txtnroValesRepartidosDomiLim.val():'0'));
			formato12B.txtTotalRepartoValesDomi.attr('value',(formato12B.txtTotalRepartoValesDomi.val()!=''?formato12B.txtTotalRepartoValesDomi.val():'0.00'));
			formato12B.txtTotalRepartoValesDomiProv.attr('value',(formato12B.txtTotalRepartoValesDomiProv.val()!=''?formato12B.txtTotalRepartoValesDomiProv.val():'0.00'));
			formato12B.txtTotalRepartoValesDomiLim.attr('value',(formato12B.txtTotalRepartoValesDomiLim.val()!=''?formato12B.txtTotalRepartoValesDomiLim.val():'0.00'));
				
			formato12B.txtnroValesEntregadoDisEl.attr('value',(formato12B.txtnroValesEntregadoDisEl.val()!=''?formato12B.txtnroValesEntregadoDisEl.val():'0'));
			formato12B.txtnroValesEntregadoDisElProv.attr('value',(formato12B.txtnroValesEntregadoDisElProv.val()!=''?formato12B.txtnroValesEntregadoDisElProv.val():'0'));
			formato12B.txtnroValesEntregadoDisElLim.attr('value',(formato12B.txtnroValesEntregadoDisElLim.val()!=''?formato12B.txtnroValesEntregadoDisElLim.val():'0'));
			formato12B.txtTotalEntregaValDisEl.attr('value',(formato12B.txtTotalEntregaValDisEl.val()!=''?formato12B.txtTotalEntregaValDisEl.val():'0.00'));
			formato12B.txtTotalEntregaValDisElProv.attr('value',(formato12B.txtTotalEntregaValDisElProv.val()!=''?formato12B.txtTotalEntregaValDisElProv.val():'0.00'));
			formato12B.txtTotalEntregaValDisElLim.attr('value',(formato12B.txtTotalEntregaValDisElLim.val()!=''?formato12B.txtTotalEntregaValDisElLim.val():'0.00'));
					
			formato12B.txtnroValesFisicosCanjeados.attr('value',(formato12B.txtnroValesFisicosCanjeados.val()!=''?formato12B.txtnroValesFisicosCanjeados.val():'0'));
			formato12B.txtnroValesFisicosCanjeadosProv.attr('value',(formato12B.txtnroValesFisicosCanjeadosProv.val()!=''?formato12B.txtnroValesFisicosCanjeadosProv.val():'0'));
			formato12B.txtnroValesFisicosCanjeadosLim.attr('value',(formato12B.txtnroValesFisicosCanjeadosLim.val()!=''?formato12B.txtnroValesFisicosCanjeadosLim.val():'0'));
			formato12B.txtTotalCanjeLiqValeFis.attr('value',(formato12B.txtTotalCanjeLiqValeFis.val()!=''?formato12B.txtTotalCanjeLiqValeFis.val():'0.00'));
			formato12B.txtTotalCanjeLiqValeFisProv.attr('value',(formato12B.txtTotalCanjeLiqValeFisProv.val()!=''?formato12B.txtTotalCanjeLiqValeFisProv.val():'0.00'));
			formato12B.txtTotalCanjeLiqValeFisLim.attr('value',(formato12B.txtTotalCanjeLiqValeFisLim.val()!=''?formato12B.txtTotalCanjeLiqValeFisLim.val():'0.00'));
				
			formato12B.txtnroValesDigitalCanjeados.attr('value',(formato12B.txtnroValesDigitalCanjeados.val()!=''?formato12B.txtnroValesDigitalCanjeados.val():'0'));
			formato12B.txtnroValesDigitalCanjeadosProv.attr('value',(formato12B.txtnroValesDigitalCanjeadosProv.val()!=''?formato12B.txtnroValesDigitalCanjeadosProv.val():'0'));
			formato12B.txtnroValesDigitalCanjeadosLim.attr('value',(formato12B.txtnroValesDigitalCanjeadosLim.val()!=''?formato12B.txtnroValesDigitalCanjeadosLim.val():'0'));
			formato12B.txtTotalCanjeLiqValeDig.attr('value',(formato12B.txtTotalCanjeLiqValeDig.val()!=''?formato12B.txtTotalCanjeLiqValeDig.val():'0.00'));
			formato12B.txtTotalCanjeLiqValeDigProv.attr('value',(formato12B.txtTotalCanjeLiqValeDigProv.val()!=''?formato12B.txtTotalCanjeLiqValeDigProv.val():'0.00'));
			formato12B.txtTotalCanjeLiqValeDigLim.attr('value',(formato12B.txtTotalCanjeLiqValeDigLim.val()!=''?formato12B.txtTotalCanjeLiqValeDigLim.val():'0.00'));
				
			formato12B.txtnroAtenciones.attr('value',(formato12B.txtnroAtenciones.val()!=''?formato12B.txtnroAtenciones.val():'0'));
			formato12B.txtnroAtencionesProv.attr('value',(formato12B.txtnroAtencionesProv.val()!=''?formato12B.txtnroAtencionesProv.val():'0'));
			formato12B.txtnroAtencionesLim.attr('value',(formato12B.txtnroAtencionesLim.val()!=''?formato12B.txtnroAtencionesLim.val():'0'));
			formato12B.txtTotalAtencionConsRecl.attr('value',(formato12B.txtTotalAtencionConsRecl.val()!=''?formato12B.txtTotalAtencionConsRecl.val():'0.00'));
			formato12B.txtTotalAtencionConsReclProv.attr('value',(formato12B.txtTotalAtencionConsReclProv.val()!=''?formato12B.txtTotalAtencionConsReclProv.val():'0.00'));
			formato12B.txtTotalAtencionConsReclLim.attr('value',(formato12B.txtTotalAtencionConsReclLim.val()!=''?formato12B.txtTotalAtencionConsReclLim.val():'0.00'));
      			
			formato12B.txtTotalDesplazamientoPersonal.attr('value',(formato12B.txtTotalDesplazamientoPersonal.val()!=''?formato12B.txtTotalDesplazamientoPersonal.val():'0.00'));
			formato12B.txtTotalDesplazamientoPersonalProv.attr('value',(formato12B.txtTotalDesplazamientoPersonalProv.val()!=''?formato12B.txtTotalDesplazamientoPersonalProv.val():'0.00'));
			formato12B.txtTotalDesplazamientoPersonalLim.attr('value',(formato12B.txtTotalDesplazamientoPersonalLim.val()!=''?formato12B.txtTotalDesplazamientoPersonalLim.val():'0.00'));
			formato12B.txtTotalActividadesExtraord.attr('value',(formato12B.txtTotalActividadesExtraord.val()!=''?formato12B.txtTotalActividadesExtraord.val():'0.00'));
			formato12B.txtTotalActividadesExtraordProv.attr('value',(formato12B.txtTotalActividadesExtraordProv.val()!=''?formato12B.txtTotalActividadesExtraordProv.val():'0.00'));
			formato12B.txtTotalActividadesExtraordLim.attr('value',(formato12B.txtTotalActividadesExtraordLim.val()!=''?formato12B.txtTotalActividadesExtraordLim.val():'0.00'));
			
			//////////COSTOS UNITARIOS///////////////////////////////
			console.debug("Costo unitario impresion vales rural al editar:  "+formato12B.txtEtndrUnitValeImpre.val());
			formato12B.txtEtndrUnitValeImpre.attr('value',(formato12B.txtEtndrUnitValeImpre.val()!=''?formato12B.txtEtndrUnitValeImpre.val():'0.00'));
			formato12B.txtEtndrUnitValeImpreProv.attr('value',(formato12B.txtEtndrUnitValeImpreProv.val()!=''?formato12B.txtEtndrUnitValeImpreProv.val():'0.00'));
			formato12B.txtEtndrUnitValeImpreLim.attr('value',(formato12B.txtEtndrUnitValeImpreLim.val()!=''?formato12B.txtEtndrUnitValeImpreLim.val():'0.00'));
				
			formato12B.txtEtndrUnitValeRepar.attr('value',(formato12B.txtEtndrUnitValeRepar.val()!=''?formato12B.txtEtndrUnitValeRepar.val():'0.00'));
			formato12B.txtEtndrUnitValeReparProv.attr('value',(formato12B.txtEtndrUnitValeReparProv.val()!=''?formato12B.txtEtndrUnitValeReparProv.val():'0.00'));
			formato12B.txtEtndrUnitValeReparLim.attr('value',(formato12B.txtEtndrUnitValeReparLim.val()!=''?formato12B.txtEtndrUnitValeReparLim.val():'0.00'));
						
			formato12B.txtEtndrUnitValDisEl.attr('value',(formato12B.txtEtndrUnitValDisEl.val()!=''?formato12B.txtEtndrUnitValDisEl.val():'0.00'));
			formato12B.txtEtndrUnitValDisElProv.attr('value',(formato12B.txtEtndrUnitValDisElProv.val()!=''?formato12B.txtEtndrUnitValDisElProv.val():'0.00'));
			formato12B.txtEtndrUnitValDisElLim.attr('value',(formato12B.txtEtndrUnitValDisElLim.val()!=''?formato12B.txtEtndrUnitValDisElLim.val():'0.00'));
			
			formato12B.txtEtndrUnitValFiCan.attr('value',(formato12B.txtEtndrUnitValFiCan.val()!=''?formato12B.txtEtndrUnitValFiCan.val():'0.00'));
			formato12B.txtEtndrUnitValFiCanProv.attr('value',(formato12B.txtEtndrUnitValFiCanProv.val()!=''?formato12B.txtEtndrUnitValFiCanProv.val():'0.00'));
			formato12B.txtEtndrUnitValFiCanLim.attr('value',(formato12B.txtEtndrUnitValFiCanLim.val()!=''?formato12B.txtEtndrUnitValFiCanLim.val():'0.00'));
			
			formato12B.txtEtndrUnitValDgCan.attr('value',(formato12B.txtEtndrUnitValDgCan.val()!=''?formato12B.txtEtndrUnitValDgCan.val():'0.00'));
			formato12B.txtEtndrUnitValDgCanProv.attr('value',(formato12B.txtEtndrUnitValDgCanProv.val()!=''?formato12B.txtEtndrUnitValDgCanProv.val():'0.00'));
			formato12B.txtEtndrUnitValDgCanLim.attr('value',(formato12B.txtEtndrUnitValDgCanLim.val()!=''?formato12B.txtEtndrUnitValDgCanLim.val():'0.00'));
			
			formato12B.txtEtndrUnitAtencion.attr('value',(formato12B.txtEtndrUnitAtencion.val()!=''?formato12B.txtEtndrUnitAtencion.val():'0.00'));
			formato12B.txtEtndrUnitAtencionProv.attr('value',(formato12B.txtEtndrUnitAtencionProv.val()!=''?formato12B.txtEtndrUnitAtencionProv.val():'0.00'));
			formato12B.txtEtndrUnitAtencionLim.attr('value',(formato12B.txtEtndrUnitAtencionLim.val()!=''?formato12B.txtEtndrUnitAtencionLim.val():'0.00'));
			
			formato12B.txtTotalGestionAdministrativa.attr('value',(formato12B.txtTotalGestionAdministrativa.val()!=''?formato12B.txtTotalGestionAdministrativa.val():'0.00'));
			formato12B.txtTotalGestionAdministrativaProv.attr('value',(formato12B.txtTotalGestionAdministrativaProv.val()!=''?formato12B.txtTotalGestionAdministrativaProv.val():'0.00'));
			formato12B.txtTotalGestionAdministrativaLim.attr('value',(formato12B.txtTotalGestionAdministrativaLim.val()!=''?formato12B.txtTotalGestionAdministrativaLim.val():'0.00'));
			/////////////////////FIN COSTOS UNITARIOS	
			
			/* //cambios elozano
			 $('#hiddenCostoUIVR').val(formato12B.txtEtndrUnitValeImpre.val());					
			 $('#hiddenCostoURDR').val(formato12B.txtEtndrUnitValeRepar.val());					 
			 $('#hiddenCostoUDER').val(formato12B.txtEtndrUnitValDisEl.val());					
			 $('#hiddenCostoUVFR').val(formato12B.txtEtndrUnitValFiCan.val());				 
			 $('#hiddenCostoUVDR').val(formato12B.txtEtndrUnitValDgCan.val());					 
			 $('#hiddenCostoUASR').val(formato12B.txtEtndrUnitAtencion.val());	
			 $('#hiddenCostoUGAR').val(formato12B.txtTotalGestionAdministrativa.val());	 
			
			 $('#hiddenCostoUIVP').val(formato12B.txtEtndrUnitValeImpreProv.val());					 
			 $('#hiddenCostoURDP').val(formato12B.txtEtndrUnitValeReparProv.val());
			 console.debug("Costo unitario de dist electrica provincia:  "+formato12B.txtEtndrUnitValDisElProv.val());
			 $('#hiddenCostoUDEP').val(formato12B.txtEtndrUnitValDisElProv.val());						 
			 $('#hiddenCostoUVFP').val(formato12B.txtEtndrUnitValFiCanProv.val());					 
			 $('#hiddenCostoUVDP').val(formato12B.txtEtndrUnitValDgCanProv.val());						 
			 $('#hiddenCostoUASP').val(formato12B.txtEtndrUnitAtencionProv.val());
			 $('#hiddenCostoUGAP').val(formato12B.txtTotalGestionAdministrativaProv.val());			
			
			 $('#hiddenCostoUIVL').val(formato12B.txtEtndrUnitValeImpreLim.val());				
			 $('#hiddenCostoURDL').val(formato12B.txtEtndrUnitValeReparLim.val());							
			 $('#hiddenCostoUDEL').val(formato12B.txtEtndrUnitValDisElLim.val());							 
			 $('#hiddenCostoUVFL').val(formato12B.txtEtndrUnitValFiCanLim.val());							 
			 $('#hiddenCostoUVDL').val(formato12B.txtEtndrUnitValDgCanLim.val());					
			 $('#hiddenCostoUASL').val(formato12B.txtEtndrUnitAtencionLim.val());
			 $('#hiddenCostoUGAL').val(formato12B.txtTotalGestionAdministrativaLim.val());	*/					 
			 
			 //costos totales
			 $('#costoTotalImpresionVale').val(formato12B.txtTotalImpresionVale.val());
			 $('#costoTotalImpresionValeProv').val(formato12B.txtTotalImpresionValeProv.val());
			 $('#costoTotalImpresionValeLim').val(formato12B.txtTotalImpresionValeLim.val());
			
			 $('#costoTotalRepartoValesDomi').val(formato12B.txtTotalRepartoValesDomi.val());
			 $('#costoTotalRepartoValesDomiProv').val(formato12B.txtTotalRepartoValesDomiProv.val());
			 $('#costoTotalRepartoValesDomiLim').val(formato12B.txtTotalRepartoValesDomiLim.val());
				
			 $('#costoTotalEntregaValDisEl').val(formato12B.txtTotalEntregaValDisEl.val());
		     $('#costoTotalEntregaValDisElProv').val(formato12B.txtTotalEntregaValDisElProv.val());
			 $('#costoTotalEntregaValDisElLim').val(formato12B.txtTotalEntregaValDisElLim.val());
				
			 $('#costoTotalCanjeLiqValeFis').val(formato12B.txtTotalCanjeLiqValeFis.val());
		     $('#costoTotalCanjeLiqValeFisProv').val(formato12B.txtTotalCanjeLiqValeFisProv.val());
		     $('#costoTotalCanjeLiqValeFisLim').val(formato12B.txtTotalCanjeLiqValeFisLim.val());
				
			 $('#costoTotalCanjeLiqValeDig').val(formato12B.txtTotalCanjeLiqValeDig.val());
			 $('#costoTotalCanjeLiqValeDigProv').val(formato12B.txtTotalCanjeLiqValeDigProv.val());
			 $('#costoTotalCanjeLiqValeDigLim').val(formato12B.txtTotalCanjeLiqValeDigLim.val());
				
			 $('#costoTotalAtencionConsRecl').val(formato12B.txtTotalAtencionConsRecl.val());
			 $('#costoTotalAtencionConsReclProv').val(formato12B.txtTotalAtencionConsReclProv.val());
			 $('#costoTotalAtencionConsReclLim').val(formato12B.txtTotalAtencionConsReclLim.val());	
			 
			 //cambios elozano para editar costos unitarios
			 console.debug("Flag editar costos al editar o visualizar formato 12B:  "+$('#hiddenFlagCostoEstandar').val());
			 console.debug("tipo de operacion :  "+formato12B.tpOperacion.val());
			 if(formato12B.tpOperacion.val()=='1'){
				 formato12B.mostrarEditarCostosUnitariosEstado(); 
				 formato12B.loadCostosUnitariosFijadosEditar(); 
			 }			 
		}	
	};
	
	
</script>