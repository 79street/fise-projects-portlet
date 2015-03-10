<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects />

<script type="text/javascript">

var formato14C= {
		//decalaracion de los elementos del html	
		formCommand: null,
		//divs
		divBuscar:null,	
		divNuevo:null,	
		divOverlay:null,
		divInformacion:null,
		
		//dialogos	
		dialogMessage:null,//para guardar y actualizar
		dialogMessageContent:null,//para mostrar mensajes al guardar y actualizar 
		dialogConfirm:null,//para eliminar registro
		dialogConfirmContent:null,//para mostrar la confirmacion al eliminar el registro
		dialogConfirmEnvio:null,
		dialogCarga:null,
		dialogError:null,
		dialogObservacion:null,	
		dialogConfirmEnvioContent:null,
		dialogCargaExcel:null,
		dialogCargaTexto:null,
		dialogMessageReport:null,
		
		dialogValidacion:null,
		dialogValidacionContent:null,
		dialogErrorF14C:null,
		dialogErrorContentF14C:null,
		dialogInfo:null,
		dialogInfoContent:null,
		
		//mensajes
		mensajeCargando:null,
		mensajeObteniendoDatos:null,
		mensajeEliminando:null,
		mensajeGuardando:null,	
		mensajeActualizando:null,	
		
		mensajeError:null,
		mensajeInfo:null,
		flag:null,
		flagCosto:null,
		
		//valores constantes para edelnor y luz del sur
		cod_empresa_edelnor:null,
		cod_empresa_luz_sur:null,
		
		//valores hidden
	    estadoCrud:null,//flag que indica que proceso es nuevo o actualizar    
	    flagCarga:null,
	    codEmpresaSes:null,
		anioPresSes:null,
		mesPresSes:null,
		anioIniVigSes:null,
		anioFinVigSes:null,
		etapaSes:null,
	    
		//urls
		urlBusqueda: null,
		urlCargaPeriodo:null,
		urlCargaFlagPeriodo:null,
		urlGrabar:null,
		urlActualizar:null,
		urlEditarView:null,		
		urlEliminar:null,
		urlValidacion:null,		
		urlReporteValidacion:null,
		urlReporte:null,//url para reporte cuando visualizo el formato		
		urlEnvioDefinitivo:null,		
		urlReporteEnvioDefinitivo:null,
		urlReporteActaEnvio:null,		
		
		//botones		
		botonBuscar:null,
		botonNuevo:null,
		botonRegresar:null,
		botonReportePdf:null,
		botonReporteExcel:null,
		botonGrabar:null,
		botonActualizar:null,		
		botonValidacion:null,
		botonEnvioDefinitivo:null,
		
		botonActaEnvio:null,
		
		botonCargaExcel:null,
		botonCargaTxt:null,
		botonCargarFormatoExcel:null,
		botonCargaTxt:null,	
		botonCargarFormatoTexto:null,
		
		//panel de carga de exel y text
		panelCargaArchivo:null,		
		
		//varibales de busqueda
		i_codEmpresaBusq:null,
		i_anioDesde:null,
		i_mesDesde:null,
		i_anioHasta:null,
		i_mesHasta:null,
		i_etapaBusq:null,
		
		//varibales para nuevo y editar
	    /*Cabecera*/
		f_empresa:null,f_periodoEnvio:null,f_flagPeriodo:null,f_nombreSede:null,f_numRural:null,f_numUrbProv:null,f_numUrbLima:null,
		f_costoPromRural:null,f_costoPromUrbProv:null,f_costoPromUrbLima:null,f_numTotal:null,	
		f_anoIniVigencia:null,f_anoFinVigencia:null,
		/*Detalle*/		
		/*RURAL*/
		f_canDRCoord:null,f_costDRCoord:null,f_canIRCoord:null, f_costIRCoord:null, f_canDRSupe:null,f_costDRSupe:null,
		f_canIRSupe:null,f_costIRSupe:null,f_canDRGest:null,f_costDRGest:null,f_canIRGest:null,f_costIRGest:null,
		f_canDRAsist:null,f_costDRAsist:null,f_canIRAsist:null,f_costIRAsist:null,
		//no editables
		f_canDRGP:null,f_costDRGP:null,f_canIRGP:null,f_costIRGP:null,f_costTotalRGP:null,
		f_costTotalRCoord:null,f_costTotalRSupe:null,f_costTotalRGest:null,f_costTotalRAsist:null,
		
		/*PROVINCIAS*/
		f_canDPCoord:null,f_costDPCoord:null,f_canIPCoord:null,	f_costIPCoord:null,f_canDPSupe:null,f_costDPSupe:null,
		f_canIPSupe:null,f_costIPSupe:null,f_canDPGest:null,f_costDPGest:null,f_canIPGest:null,f_costIPGest:null,
		f_canDPAsist:null,f_costDPAsist:null,f_canIPAsist:null,	f_costIPAsist:null,
		//no editables
		f_canDPGP:null,f_costDPGP:null,f_canIPGP:null,f_costIPGP:null,f_costTotalPGP:null,
		f_costTotalPCoord:null,f_costTotalPSupe:null,f_costTotalPGest:null,f_costTotalPAsist:null,
		
		/*LIMA*/
		f_canDLCoord:null,f_costDLCoord:null,f_canILCoord:null,f_costILCoord:null,f_canDLSupe:null,f_costDLSupe:null,f_canILSupe:null,
		f_costILSupe:null,f_canDLGest:null,f_costDLGest:null,f_canILGest:null,f_costILGest:null,f_canDLAsist:null,f_costDLAsist:null,
		f_canILAsist:null,	f_costILAsist:null,		
		//no editables
		f_canDLGP:null,f_costDLGP:null,f_canILGP:null,f_costILGP:null,f_costTotalLGP:null,
		f_costTotalLCoord:null,f_costTotalLSupe:null,f_costTotalLGest:null,f_costTotalLAsist:null,
		
		/*TOTAL*/
		//no editables
		f_canTotalCoord:null,f_costTotalCoord:null,f_canTotalSupe:null,f_costTotalSupe:null,
		f_canTotalGest:null,f_costTotalGest:null,f_canTotalAsist:null,f_costTotalAsist:null,
		f_canTotalGP:null,f_costTotalGP:null,f_costTotalPromedio:null,
		
		//grillas
		tablaResultados:null,
		paginadoResultados:null,
		tablaObservacion:null,		
		paginadoObservacion:null,
				
		//cargar los id de los elementos del html		
		init : function(){
			this.formCommand=$('#formato14CBean'); 
			//divs
			this.divBuscar=$("#<portlet:namespace/>div_buscar");
			this.divNuevo=$("#<portlet:namespace/>div_nuevo");
			this.divOverlay=$("#<portlet:namespace/>divOverlay");
			this.divInformacion=$("#<portlet:namespace/>divInformacion");
			
			//dialogos		
			this.dialogMessage=$("#<portlet:namespace/>dialog-message-grabar");//para guardar y actualizar
			this.dialogMessageContent=$("#<portlet:namespace/>dialog-message-content-grabar");//para guardar y actualizar
			this.dialogConfirm=$("#<portlet:namespace/>dialog-confirm");//para eliminar
			this.dialogConfirmContent=$("#<portlet:namespace/>dialog-confirm-content");//para eliminar
			this.dialogConfirmEnvio=$("#<portlet:namespace/>dialog-confirm-envio");
			this.dialogCarga=$("#<portlet:namespace/>dialog-form-carga");
			this.dialogError=$("#<portlet:namespace/>dialog-form-error");
			this.dialogObservacion=$("#<portlet:namespace/>dialog-form-observacion");		
			this.dialogConfirmEnvioContent=$("#<portlet:namespace/>dialog-confirm-envio-content");
			this.dialogCargaExcel=$("#<portlet:namespace/>dialog-form-cargaExcel");
			this.dialogCargaTexto=$("#<portlet:namespace/>dialog-form-cargaTexto");
			this.dialogMessageReport=$("#<portlet:namespace/>dialog-message-report");
			
			this.dialogValidacion=$("#<portlet:namespace/>dialog-alert");	
			this.dialogValidacionContent=$("#<portlet:namespace/>dialog-alert-content");
			this.dialogErrorF14C=$("#<portlet:namespace/>dialog-error");
			this.dialogErrorContentF14C=$("#<portlet:namespace/>dialog-error-content");	
            this.dialogInfo=$("#<portlet:namespace/>dialog-info");
			this.dialogInfoContent=$("#<portlet:namespace/>dialog-info-content");				
		
			//mensajes			
			this.mensajeCargando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Cargando </h3>';
			this.mensajeObteniendoDatos='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Obteniendo Datos </h3>';
			this.mensajeEliminando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Eliminando </h3>';
			this.mensajeGuardando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Guardando Datos </h3>';
			this.mensajeActualizando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Actualizando Datos </h3>';
			
			this.mensajeError=$('#<portlet:namespace/>mensajeError');
			this.mensajeInfo=$('#<portlet:namespace/>mensajeInfo');
			this.flag=$('#<portlet:namespace/>flag');//solo para controlar los errores al subir archivos excel o de texto
			this.flagCosto=$("#flagCosto");//flag para controlar el costo directo e indirecto
			
			//valores constantes para edelnor y luz del sur
			this.cod_empresa_edelnor=$("#codEdelnor");
			this.cod_empresa_luz_sur=$("#codLuzSur");
			
			
			//valores hidden
			this.estadoCrud=$('#estadoCrudF14C'); 
			this.flagCarga=$('#<portlet:namespace/>flagCarga');
			this.codEmpresaSes=$('#<portlet:namespace/>codEmpresaSes');
			this.anioPresSes=$('#<portlet:namespace/>anioPresSes');
			this.mesPresSes=$('#<portlet:namespace/>mesPresSes');
			this.anioIniVigSes=$('#<portlet:namespace/>anioIniVigSes');
			this.anioFinVigSes=$('#<portlet:namespace/>anioFinVigSes');
			this.etapaSes=$('#<portlet:namespace/>etapaSes');
			
			//urls
			this.urlBusqueda='<portlet:resourceURL id="busquedaF14C" />';
			this.urlCargaPeriodo='<portlet:resourceURL id="cargaPeriodoF14C" />';
			this.urlCargaFlagPeriodo='<portlet:resourceURL id="cargaFlagPeriodoF14C" />';
			this.urlGrabar='<portlet:resourceURL id="grabarF14C" />';			
			this.urlActualizar='<portlet:resourceURL id="actualizarF14C" />';
			this.urlEditarView='<portlet:resourceURL id="editarViewF14C" />';			
			this.urlEliminar='<portlet:resourceURL id="eliminarF14C" />';
			this.urlValidacion='<portlet:resourceURL id="validacionF14C" />';
			this.urlReporteValidacion='<portlet:resourceURL id="reporteValidacionF14C" />';
			this.urlReporte='<portlet:resourceURL id="reporteF14C" />';	
			this.urlEnvioDefinitivo='<portlet:resourceURL id="envioDefinitivoF14C" />';			
			this.urlReporteEnvioDefinitivo='<portlet:resourceURL id="reporteEnvioDefinitivoF14C" />';
			this.urlReporteActaEnvio='<portlet:resourceURL id="reporteActaEnvioView" />';			
			
			//botones
			this.botonBuscar=$("#<portlet:namespace/>btnBuscarF14C");
			this.botonNuevo=$("#<portlet:namespace/>btnNuevoF14C");
			this.botonRegresar=$("#<portlet:namespace/>regresarFormatoF14C");
			this.botonReportePdf=$("#<portlet:namespace/>reportePdfF14C");
			this.botonReporteExcel=$("#<portlet:namespace/>reporteExcelF14C");
			this.botonGrabar=$("#<portlet:namespace/>guardarFormatoF14C");
			this.botonActualizar=$("#<portlet:namespace/>actualizarFormatoF14C");
			this.botonValidacion=$("#<portlet:namespace/>validacionFormatoF14C");
			this.botonEnvioDefinitivo=$("#<portlet:namespace/>envioDefinitivoF14C");
			
			this.botonActaEnvio=$("#<portlet:namespace/>reporteActaEnvio");
			
			this.botonCargaExcel=$("#<portlet:namespace/>cargaExcelF14C");
			this.botonCargaTxt=$("#<portlet:namespace/>cargaTxtF14C");
			this.botonCargarFormatoExcel=$("#<portlet:namespace/>cargarFormatoExcel");		
			this.botonCargarFormatoTexto=$("#<portlet:namespace/>cargarFormatoTexto");
			
			//panel de carga de exel y text
			this.panelCargaArchivo=$("#<portlet:namespace/>panelCargaArchivosF14C");
			
			//variables de busqueda
			this.i_codEmpresaBusq=$('#codEmpresaBusq');
			this.i_anioDesde=$('#anioDesde');
			this.i_mesDesde=$('#mesDesde');
			this.i_anioHasta=$('#anioHasta');
			this.i_mesHasta=$('#mesHasta');
			this.i_etapaBusq=$('#etapaBusq');
			
			//variables para nuevo
			 /*Cabecera*/
			this.f_empresa=$('#codEmpresa');
			this.f_periodoEnvio=$('#periodoEnvio');	
			this.f_flagPeriodo=$("#flagPeriodoEjecucion");
			this.f_nombreSede=$('#nombreSede');
			this.f_numRural=$('#numRural');
			this.f_numUrbProv=$('#numUrbProv');
			this.f_numUrbLima=$('#numUrbLima');
			this.f_costoPromRural=$('#costoPromRural');
			this.f_costoPromUrbProv=$('#costoPromUrbProv');
			this.f_costoPromUrbLima=$('#costoPromUrbLima');	
			this.f_numTotal=$('#numTotal');	
			this.f_anoIniVigencia=$('#anoIniVigencia');	
			this.f_anoFinVigencia=$('#anoFinVigencia');	
			/*Detalle*/
			/*RURAL*/
			this.f_canDRCoord=$('#canDRCoord');
			this.f_costDRCoord=$('#costDRCoord');
			this.f_canIRCoord=$('#canIRCoord');
			this.f_costIRCoord=$('#costIRCoord');
			this.f_canDRSupe=$('#canDRSupe'); 
			this.f_costDRSupe=$('#costDRSupe');
			this.f_canIRSupe=$('#canIRSupe');
			this.f_costIRSupe=$('#costIRSupe');
			this.f_canDRGest=$('#canDRGest');
			this.f_costDRGest=$('#costDRGest');
			this.f_canIRGest=$('#canIRGest');
			this.f_costIRGest=$('#costIRGest');
			this.f_canDRAsist=$('#canDRAsist');
			this.f_costDRAsist=$('#costDRAsist');
			this.f_canIRAsist=$('#canIRAsist');
			this.f_costIRAsist=$('#costIRAsist');
			//no editables
			this.f_canDRGP=$('#canDRGP');
			this.f_costDRGP=$('#costDRGP');
			this.f_canIRGP=$('#canIRGP');
			this.f_costIRGP=$('#costIRGP');
			this.f_costTotalRGP=$('#costTotalRGP');
			this.f_costTotalRCoord=$('#costTotalRCoord');
			this.f_costTotalRSupe=$('#costTotalRSupe');
			this.f_costTotalRGest=$('#costTotalRGest');
			this.f_costTotalRAsist=$('#costTotalRAsist');
			/*PROVINCIAS*/
			this.f_canDPCoord=$('#canDPCoord');
			this.f_costDPCoord=$('#costDPCoord');
			this.f_canIPCoord=$('#canIPCoord');
			this.f_costIPCoord=$('#costIPCoord');
			this.f_canDPSupe=$('#canDPSupe');
			this.f_costDPSupe=$('#costDPSupe');
			this.f_canIPSupe=$('#canIPSupe');
			this.f_costIPSupe=$('#costIPSupe');
			this.f_canDPGest=$('#canDPGest');
			this.f_costDPGest=$('#costDPGest');
			this.f_canIPGest=$('#canIPGest');
			this.f_costIPGest=$('#costIPGest');
			this.f_canDPAsist=$('#canDPAsist');
			this.f_costDPAsist=$('#costDPAsist');
			this.f_canIPAsist=$('#canIPAsist');
			this.f_costIPAsist=$('#costIPAsist');
			//no editables
			this.f_canDPGP=$('#canDPGP');
			this.f_costDPGP=$('#costDPGP');
			this.f_canIPGP=$('#canIPGP');
			this.f_costIPGP=$('#costIPGP');
			this.f_costTotalPGP=$('#costTotalPGP');
			this.f_costTotalPCoord=$('#costTotalPCoord');
			this.f_costTotalPSupe=$('#costTotalPSupe');
			this.f_costTotalPGest=$('#costTotalPGest');
			this.f_costTotalPAsist=$('#costTotalPAsist');
			/*LIMA*/
			this.f_canDLCoord=$('#canDLCoord');
			this.f_costDLCoord=$('#costDLCoord');
			this.f_canILCoord=$('#canILCoord');
			this.f_costILCoord=$('#costILCoord');
			this.f_canDLSupe=$('#canDLSupe');
			this.f_costDLSupe=$('#costDLSupe');
			this.f_canILSupe=$('#canILSupe');
			this.f_costILSupe=$('#costILSupe');
			this.f_canDLGest=$('#canDLGest');
			this.f_costDLGest=$('#costDLGest');
			this.f_canILGest=$('#canILGest');
			this.f_costILGest=$('#costILGest');
			this.f_canDLAsist=$('#canDLAsist');
			this.f_costDLAsist=$('#costDLAsist');
			this.f_canILAsist=$('#canILAsist');
			this.f_costILAsist=$('#costILAsist');	
			//no editables
			this.f_canDLGP=$('#canDLGP');
			this.f_costDLGP=$('#costDLGP');
			this.f_canILGP=$('#canILGP');
			this.f_costILGP=$('#costILGP');
			this.f_costTotalLGP=$('#costTotalLGP');
			this.f_costTotalLCoord=$('#costTotalLCoord');
			this.f_costTotalLSupe=$('#costTotalLSupe');
			this.f_costTotalLGest=$('#costTotalLGest');
			this.f_costTotalLAsist=$('#costTotalLAsist');
			
			/*TOTAL*/
			//no editables
			this.f_canTotalCoord=$('#canTotalCoord');
			this.f_costTotalCoord=$('#costTotalCoord');
			this.f_canTotalSupe=$('#canTotalSupe');
			this.f_costTotalSupe=$('#costTotalSupe');
			this.f_canTotalGest=$('#canTotalGest');
			this.f_costTotalGest=$('#costTotalGest');
			this.f_canTotalAsist=$('#canTotalAsist');
			this.f_costTotalAsist=$('#costTotalAsist');
			this.f_canTotalGP=$('#canTotalGP');
			this.f_costTotalGP=$('#costTotalGP');
			this.f_costTotalPromedio=$('#costTotalPromedio');	
			
			//grillas			
			this.tablaResultados=$("#<portlet:namespace/>grid_resultado_busqueda");
			this.paginadoResultados='#<portlet:namespace/>paginador_resultado_busqueda';
			this.buildGrids();	//cargar el modelo de la grilla	
			this.tablaObservacion=$("#<portlet:namespace/>grid_observacion");			
			this.paginadoObservacion='#<portlet:namespace/>pager_observacion';
			this.buildGridsObservacion();			
			
			//llamado a la funciones de cada boton
			formato14C.botonBuscar.click(function() {
				formato14C.buscarF14C();
			});			
			
			formato14C.botonNuevo.click(function() {
				formato14C.<portlet:namespace/>nuevoFormato14C();
		    });
			
			formato14C.botonGrabar.click(function() {				
				if(formato14C.validarGrupoInformacion()){
					if(formato14C.validarUltimaEtapaF14C()){
						formato14C.<portlet:namespace/>guardarFormato14C();	
					}					
				}	 
			});
			
			formato14C.botonActualizar.click(function() {
				formato14C.<portlet:namespace/>actualizarFormato14C();
			});
			
			formato14C.botonValidacion.click(function() {
				formato14C.<portlet:namespace/>validacionFormato14C();
			});
			
			formato14C.botonEnvioDefinitivo.click(function() {
				formato14C.confirmarEnvioDefinitivoF14C();
			});
			
			formato14C.botonActaEnvio.click(function() {
				formato14C.<portlet:namespace/>mostrarReporteActaEnvio();
			});
			
			formato14C.botonReportePdf.click(function() {
				formato14C.<portlet:namespace/>mostrarReportePdfF14C();
			});
			
			formato14C.botonReporteExcel.click(function() {
				formato14C.<portlet:namespace/>mostrarReporteExcelF14C();
			});
			
		    formato14C.botonRegresar.click(function() {
		    	formato14C.<portlet:namespace/>regresarF14C();
		    });			
		    
		    //botones para carga de archivos
		    formato14C.botonCargaExcel.click(function() {
		    	if(formato14C.validarGrupoInformacion()){
		    		if(formato14C.validarUltimaEtapaF14C() ){
		    			formato14C.<portlet:namespace/>mostrarFormularioCargaExcel();	
		    		}	    		
		    	}
		    });
		    
		    formato14C.botonCargaTxt.click(function() {
		    	if(formato14C.validarGrupoInformacion()){
		    		if(formato14C.validarUltimaEtapaF14C()){
		    			formato14C.<portlet:namespace/>mostrarFormularioCargaTexto();	
		    		}	    		
		    	}
		    });
		    
		    formato14C.botonCargarFormatoExcel.click(function() {
		    	formato14C.<portlet:namespace/>cargarFormatoExcel();
		    });
		    		  
			formato14C.botonCargarFormatoTexto.click(function() {
				formato14C.<portlet:namespace/>cargarFormatoTexto();
			});
		    
		    
		    
			//eventos 		
			formato14C.f_empresa.change(function(){
				formato14C.<portlet:namespace/>loadPeriodo('');
			});
			formato14C.f_periodoEnvio.change(function(){
				formato14C.<portlet:namespace/>loadPeriodo(this.value);
			});
			
			formato14C.initDialogs();
			formato14C.cargaInicial();
			
			//eventos por defecto
			formato14C.botonBuscar.trigger('click');
			formato14C.initBlockUI();
			
			//calcular los totales
			$('input.target[type=text]').on('change', function(){
				formato14C.calculoTotal();
			});
		},
		//funcion para armar el modelo de la grilla para el resultado
		buildGrids : function () {	
			formato14C.tablaResultados.jqGrid({
			   datatype: "local",
		       colNames: ['Dist. Eléct.','Año Decl.','Mes Decl.','Año Ini. Vig.','Año Fin Vig.','Grupo Inf','Estado','Ver','Editar','Eliminar','','','',''],
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
					pager: formato14C.paginadoResultados,
				    viewrecords: true,
				   	caption: "Resultado(s) de la búsqueda",
				    sortorder: "asc",	   	    	   	   
		       gridComplete: function(){
		      		var ids = formato14C.tablaResultados.jqGrid('getDataIDs');
		      		for(var i=0;i < ids.length;i++){
		      			var cl = ids[i];
		      			var ret = formato14C.tablaResultados.jqGrid('getRowData',cl);           
		      			view = "<a href='#'><img border='0' title='Ver' src='/net-theme/images/img-net/file.png'  align='center' onclick=\"formato14C.verFormato14C('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoIniVigencia+"','"+ret.anoFinVigencia+"','"+ret.etapa+"');\" /></a> ";
		      			edit = "<a href='#'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png'  align='center' onclick=\"formato14C.editarFormato14C('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoIniVigencia+"','"+ret.anoFinVigencia+"','"+ret.etapa+"','"+ret.flagOperacion+"');\" /></a> ";
		      			elim = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"formato14C.confirmarEliminarF14C('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoIniVigencia+"','"+ret.anoFinVigencia+"','"+ret.etapa+"','"+ret.flagOperacion+"');\" /></a> ";              			
		      			formato14C.tablaResultados.jqGrid('setRowData',ids[i],{view:view});
		      			formato14C.tablaResultados.jqGrid('setRowData',ids[i],{edit:edit});
		      			formato14C.tablaResultados.jqGrid('setRowData',ids[i],{elim:elim});
		      		}
		      }
		  	});
			formato14C.tablaResultados.jqGrid('navGrid',formato14C.paginadoResultados,{add:false,edit:false,del:false,search: false,refresh: false});	
			formato14C.tablaResultados.jqGrid('navButtonAdd',formato14C.paginadoResultados,{
			       caption:"Exportar a Excel",
			       buttonicon: "ui-icon-bookmark",
			       onClickButton : function () {			          
			    	   var ids = formato14C.tablaResultados.jqGrid('getDataIDs');
				       if(ids!=0){
				    		  location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';   
				       }else{
				    	var addhtmInfo='No existe información para exportar a Excel';				
						formato14C.dialogInfoContent.html(addhtmInfo);
						formato14C.dialogInfo.dialog("open");
				       }
			       } 
			}); 
		},
		//Modelo de la grilla para mostrar Observaciones
		buildGridsObservacion : function () {	
		formato14C.tablaObservacion.jqGrid({
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
				pager: formato14C.paginadoObservacion,
			    viewrecords: true,
			   	//caption: "Formatos",
			    sortorder: "asc"
		  	});
			formato14C.tablaObservacion.jqGrid('navGrid',formato14C.paginadoObservacion,{add:false,edit:false,del:false,search: false,refresh: false});	
			formato14C.tablaObservacion.jqGrid('navButtonAdd',formato14C.paginadoObservacion,{
			       caption:"Exportar a Excel",
			       buttonicon: "ui-icon-bookmark",
			       onClickButton : function () {
			    	   var ids = formato14C.tablaObservacion.jqGrid('getDataIDs');
				       if(ids!=0){
				    		  location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';   
				       }else{
				    	var addhtmInfo='No existe información para exportar a Excel';				
						formato14C.dialogInfoContent.html(addhtmInfo);
						formato14C.dialogInfo.dialog("open");
				       }  		            
			       } 
			}); 
			formato14C.tablaObservacion.jqGrid('navButtonAdd',formato14C.paginadoObservacion,{
			       caption:"Exportar a Pdf",
			       buttonicon: "ui-icon-bookmark",
			       onClickButton : function () {
			    	   var ids = formato14C.tablaObservacion.jqGrid('getDataIDs');
				       if(ids!=0){
				    	   formato14C.<portlet:namespace/>mostrarReporteValidacionF14C();
				       }else{
				    	var addhtmInfo='No existe información para exportar a Pdf';				
						formato14C.dialogInfoContent.html(addhtmInfo);
						formato14C.dialogInfo.dialog("open");
				       }  
			    	   
			       } 
			});
		},		
	   //function para la carga inicial del formulario
		cargaInicial : function(){				
			//SE CARGA VARIABLES EN SESION PARA MOSTRAR LOS PANELES DE NUEVO O EDICION MANEJADOS CARAGA EXEL Y TEXT
			 var codEmpSes = formato14C.codEmpresaSes.val();
			 var anioPresSes = formato14C.anioPresSes.val();
			 var mesPresSes = formato14C.mesPresSes.val();
			 var anioIniVigSes = formato14C.anioIniVigSes.val();
			 var anioFinVigSes = formato14C.anioFinVigSes.val();
			 var etapaSes = formato14C.etapaSes.val();
			 var flagOpera = 'ABIERTO';
			
			 var flag = formato14C.flag.val();
			 if( flag=='N' ){//solo ocurre cuando hay un error en la carga de formularios, sino se muestra el proceso normal
				 formato14C.inicializarFormulario();		
				 formato14C.divNuevo.show();
			     formato14C.divBuscar.hide();	
				 formato14C.flagCarga.val('0');
				 formato14C.f_empresa.val(codEmpSes);
				 console.debug("flag periodo ejecucion error al cargar archivo nuevo registro: "+formato14C.f_periodoEnvio.val());
				/*if( formato14C.f_periodoEnvio.val()=='S' ){
					 $("#anoIniVigencia").val(anioIniVigSes);
					 $("#anoFinVigencia").val(anioFinVigSes);
				}else{
					$("#anoIniVigencia").val(anioPresSes); 
					$("#anoFinVigencia").val(anioPresSes);
				}*/
				formato14C.botonValidacion.css('display','none');
				formato14C.<portlet:namespace/>loadPeriodo(anioPresSes+completarCerosIzq(mesPresSes,2)+etapaSes);
				formato14C.f_anoIniVigencia.removeClass("fise-editable");
				formato14C.f_anoFinVigencia.removeClass("fise-editable");
				//mantener datos en sesion
				if(formato14C.codEmpresaSes.val()!=''){
					formato14C.i_codEmpresaBusq.val(formato14C.codEmpresaSes.val());
				}else{
					formato14C.i_codEmpresaBusq.val('');
				}
			}else{			
				 if(codEmpSes != '' && anioPresSes != '' && mesPresSes != '' && anioIniVigSes != '' && anioFinVigSes != '' && etapaSes != ''){
					 formato14C.editarFormato14C(codEmpSes, anioPresSes, mesPresSes, anioIniVigSes, anioFinVigSes, etapaSes,flagOpera);
				 }
				//mantener datos en sesion
				 if(formato14C.codEmpresaSes.val()!=''){
						formato14C.i_codEmpresaBusq.val(formato14C.codEmpresaSes.val());
				 }else{
						formato14C.i_codEmpresaBusq.val('');
				 }
			 }	 
			 var mensajeInfo = formato14C.mensajeInfo.val();
			 var mensajeError = formato14C.mensajeError.val();
			 //SE MUESTRAN LOS MENSAJES DE ERROR PARA LA CARGA DE LOS ARCHIVOS
			 if(mensajeError!=''){
				 //se muestra el panel de errores si se produce en la carga de archivos
				formato14C.dialogError.dialog("open");
			}else{
				//Se muestra el mensaje de informacion exitosa
				 if(mensajeInfo!=''){
					formato14C.dialogMessageContent.html(mensajeInfo);
					formato14C.dialogMessage.dialog("open");			
				 }
			 }
			 //limpiar variables
			 formato14C.codEmpresaSes.val('');
			 formato14C.anioPresSes.val('');
			 formato14C.mesPresSes.val('');
			 formato14C.anioIniVigSes.val('');
			 formato14C.anioFinVigSes.val('');
			 formato14C.etapaSes.val('');
		},
		//funcion para buscar
		buscarF14C : function () {
			if(formato14C.validarBusqueda()){
				formato14C.blockUI();
				jQuery.ajax({			
						url: formato14C.urlBusqueda+'&'+formato14C.formCommand.serialize(),
						type: 'post',
						dataType: 'json',				
						success: function(gridData) {					
							formato14C.tablaResultados.clearGridData(true);
							formato14C.tablaResultados.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
							formato14C.tablaResultados[0].refreshIndex();
							formato14C.initBlockUI();
						},error : function(){
							var addhtmError='Error de conexión.';					
							formato14C.dialogErrorContentF14C.html(addhtmError);
							formato14C.dialogErrorF14C.dialog("open");
							formato14C.initBlockUI();
						}
				});
			}
		},
		//funcion para validar datos para la busqueda
		validarBusqueda : function(){			
			if(formato14C.i_anioDesde.val().length != '' || formato14C.i_anioHasta.val().length != '') {		  
				if(formato14C.i_anioDesde.val().length == ''){				    
				     var addhtmAlert='Por favor, Ingrese un Año Declarado Desde.';					
				     formato14C.dialogValidacionContent.html(addhtmAlert);
					 formato14C.dialogValidacion.dialog("open");
				     //formato14C.i_anioDesde.focus();
				     cod_focus=formato14C.i_anioDesde;
				     return false;		
				} else if(formato14C.i_anioHasta.val().length == '' ) {				   
				     var addhtmAlert='Por favor, Ingrese un Año Declarado Hasta.';					
				     formato14C.dialogValidacionContent.html(addhtmAlert);
					 formato14C.dialogValidacion.dialog("open");
					 //formato14C.i_anioHasta.focus();
					 cod_focus=formato14C.i_anioHasta;
					 return false;
				}else if (isNaN(trim(formato14C.i_anioDesde.val())) || 
						formato14C.i_anioDesde.val().length<4 || 
						parseFloat(trim(formato14C.i_anioDesde.val()))<1900){				    
				     var addhtmAlert='Ingrese un Año Declarado Desde válido.';					
				     formato14C.dialogValidacionContent.html(addhtmAlert);
					 formato14C.dialogValidacion.dialog("open");
				    // formato14C.i_anioDesde.focus();
				     cod_focus=formato14C.i_anioDesde;
				     return false;		
				}else if (isNaN(trim(formato14C.i_anioHasta.val())) || 
						formato14C.i_anioDesde.val().length<4 || 
						parseFloat(trim(formato14C.i_anioHasta.val()))<1900){				     
				     var addhtmAlert='Ingrese un Año Declarado Hasta válido.';					
				     formato14C.dialogValidacionContent.html(addhtmAlert);
					 formato14C.dialogValidacion.dialog("open");
				    // formato14C.i_anioHasta.focus();
				     cod_focus=formato14C.i_anioHasta;
				     return false;					 
			   }else if(parseFloat(formato14C.i_anioDesde.val()) > parseFloat(formato14C.i_anioHasta.val()) ){				   
				     var addhtmAlert='El Año Declarado Desde no puede exceder al Año Declarado Hasta.';					
				     formato14C.dialogValidacionContent.html(addhtmAlert);
					 formato14C.dialogValidacion.dialog("open");
					 cod_focus=formato14C.i_anioDesde;
				    return false;					 
			  }else{
				  return true;
			  }
		   }else if(formato14C.i_etapaBusq.val().length == '' ) { 			    
				    var addhtmAlert='Seleccione una Etapa.';					
				    formato14C.dialogValidacionContent.html(addhtmAlert);
					formato14C.dialogValidacion.dialog("open");
				   // formato14C.i_etapaB.focus();
				    cod_focus=formato14C.i_etapaB;
				    return false; 
		   }else{
			   return true;   
		   }		 
		},
		//funcion para nuevo registro
		<portlet:namespace/>nuevoFormato14C : function(){			
			formato14C.inicializarFormulario();				
			formato14C.divNuevo.show();
			formato14C.divBuscar.hide();		
			formato14C.flagCarga.val('0');				
			console.debug("flag periodo ejecucion boton nuevo formato:  "+formato14C.f_flagPeriodo.val());
			/*if(formato14C.f_flagPeriodo.val()=='S' ){
				//poner valores guardadose en sesion
				$("#anoIniVigencia").val(formato14C.i_anioDesde.val());
				$("#anoFinVigencia").val(formato14C.i_anioDesde.val());
			}*/
			$('#<portlet:namespace/>guardarFormatoF14C').css('display','block');
			$('#<portlet:namespace/>actualizarFormatoF14C').css('display','none');
			
			$('#<portlet:namespace/>validacionFormatoF14C').css('display','none');		
			$('#<portlet:namespace/>envioDefinitivoF14C').css('display','none');
			
			formato14C.<portlet:namespace/>loadPeriodo('');
			formato14C.verElementosEditar();//activar los elementos que se desabilitados al momento de editar			
			//ESTILOS
			//CABECERA			
			formato14C.f_nombreSede.addClass("fise-editable");
			formato14C.f_numRural.addClass("fise-editable");
			formato14C.f_numUrbProv.addClass("fise-editable");			
			formato14C.f_costoPromRural.addClass("fise-editable");		
			formato14C.f_costoPromUrbProv.addClass("fise-editable");
			formato14C.f_anoIniVigencia.removeClass("fise-editable");
			formato14C.f_anoFinVigencia.removeClass("fise-editable");
		},
		//Function para Visualizar los datos del formulario		
		verFormato14C : function(codEmpresa,anoPresentacion,mesPresentacion,anoIniVigencia,anoFinVigencia,etapa){	
			$.blockUI({ message: formato14C.mensajeObteniendoDatos });
			jQuery.ajax({
					url: formato14C.urlEditarView+'&'+formato14C.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {						  
					   <portlet:namespace />codEmpresa: codEmpresa,
					   <portlet:namespace />anioPres: anoPresentacion,
					   <portlet:namespace />mesPres: mesPresentacion,
					   <portlet:namespace />anoIniVigencia: anoIniVigencia,
					   <portlet:namespace />anoFinVigencia: anoFinVigencia,
					   <portlet:namespace />etapa: etapa
						},
					success: function(data) {
					    if (data != null){															
							formato14C.divNuevo.show();
							formato14C.divBuscar.hide();						
						    formato14C.llenarDatosEditar(data);
						    formato14C.divInformacion.show();
							formato14C.initBlockUI();
							formato14C.deshabilitarCamposView();							
				        }						
						else{							
							var addhtmError='Error al visualizar los datos del registro seleccionado.';					
							formato14C.dialogErrorContentF14C.html(addhtmError);
							formato14C.dialogErrorF14C.dialog("open");	
							formato14C.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						formato14C.dialogErrorContentF14C.html(addhtmError);
						formato14C.dialogErrorF14C.dialog("open");
						formato14C.initBlockUI();
					}
			});	
		},
		//Function para editar los datos del formulario
		editarFormato14C : function(codEmpresa,anoPresentacion,mesPresentacion,anoIniVigencia,anoFinVigencia,etapa,flagOperacion){	
			console.debug("flag operacion editar : "+flagOperacion);			 
			var admin = '${formato14CBean.admin}';
			if(flagOperacion=='ABIERTO'){
				var process=true;
				if( etapa=='ESTABLECIDO'  &&  admin=='false' ){
					process = false;
				}
				if(process){
					$.blockUI({ message: formato14C.mensajeObteniendoDatos });			 
					jQuery.ajax({
							url: formato14C.urlEditarView+'&'+formato14C.formCommand.serialize(),
							type: 'post',
							dataType: 'json',
							data: {							
							   <portlet:namespace />codEmpresa: codEmpresa,
							   <portlet:namespace />anioPres: anoPresentacion,
							   <portlet:namespace />mesPres: mesPresentacion,
							   <portlet:namespace />anoIniVigencia: anoIniVigencia,
							   <portlet:namespace />anoFinVigencia: anoFinVigencia,
							   <portlet:namespace />etapa: etapa						  
							},
							success: function(data) {				
								if (data != null){															
									formato14C.divNuevo.show();
									formato14C.divBuscar.hide();							
								    formato14C.llenarDatosEditar(data);
								    formato14C.divInformacion.show();
									formato14C.initBlockUI();
									formato14C.ocultarElementosEditar();
									$('#<portlet:namespace/>guardarFormatoF14C').css('display','none');
									$('#<portlet:namespace/>actualizarFormatoF14C').css('display','block');	
									
									$('#<portlet:namespace/>validacionFormatoF14C').css('display','block');		
									$('#<portlet:namespace/>envioDefinitivoF14C').css('display','block');
									//ESTILOS
									//CABECERA			
									formato14C.f_nombreSede.addClass("fise-editable");
									formato14C.f_numRural.addClass("fise-editable");
									formato14C.f_numUrbProv.addClass("fise-editable");			
									formato14C.f_costoPromRural.addClass("fise-editable");		
									formato14C.f_costoPromUrbProv.addClass("fise-editable");
									
									formato14C.f_anoIniVigencia.removeClass("fise-editable");
									formato14C.f_anoFinVigencia.removeClass("fise-editable");
									$('#etapaFinal').val('');//limpiamos la variabel al momento de editar para que no bloquee la etapa final del formato
						         }
								else{									
									var addhtmError='Error al recuperar los datos del registro seleccionado.';					
									formato14C.dialogErrorContentF14C.html(addhtmError);
									formato14C.dialogErrorF14C.dialog("open");
									formato14C.initBlockUI();
								}
							},error : function(){
								var addhtmError='Error de conexión.';					
								formato14C.dialogErrorContentF14C.html(addhtmError);
								formato14C.dialogErrorF14C.dialog("open");
								formato14C.initBlockUI();
							}
					});			
				}else{					
					var addhtmInfo='No tiene autorización para realizar esta acción';				
					formato14C.dialogInfoContent.html(addhtmInfo);
					formato14C.dialogInfo.dialog("open");		
				}				
			}else if(flagOperacion=='CERRADO'){				
				var addhtmInfo='El plazo para realizar esta acción se encuentra cerrado';				
				formato14C.dialogInfoContent.html(addhtmInfo);
				formato14C.dialogInfo.dialog("open");	
			}else{				
				var addhtmInfo='El registro seleccionado del Formato 14C ya fue enviado a OSINERGMIN-GART.';				
				formato14C.dialogInfoContent.html(addhtmInfo);
				formato14C.dialogInfo.dialog("open");	
			}	
		},
		//funcion  para llenar los campos para editar
		llenarDatosEditar : function(bean){	
			$('#grupoInformacion').val(bean.grupoInformacion);
			$('#estado').val(bean.estado);			
			/*cabecera*/
			formato14C.f_empresa.val(bean.codEmpresa.trim());
			formato14C.f_nombreSede.val(bean.nombreSede);
			formato14C.f_numRural.val(bean.numRural);
			formato14C.f_numUrbProv.val(bean.numUrbProv);
			formato14C.f_numUrbLima.val(bean.numUrbLima);
			formato14C.f_costoPromRural.val(bean.costoPromRural);
			formato14C.f_costoPromUrbProv.val(bean.costoPromUrbProv);
			formato14C.f_costoPromUrbLima.val(bean.costoPromUrbLima);
			//seteamos el combo de periodo de envio			
			dwr.util.removeAllOptions("periodoEnvio");
			var dataPeriodo = [{codigoItem:bean.periodoEnvio, descripcionItem:bean.desperiodoEnvio}];			
			dwr.util.addOptions("periodoEnvio", dataPeriodo,"codigoItem","descripcionItem");
			formato14C.f_anoIniVigencia.val(bean.anoIniVigencia);
			formato14C.f_anoFinVigencia.val(bean.anoFinVigencia);	
			
			//setamos los hidden
			$('#anioInicioVigenciaHidden').val(bean.anoIniVigencia);
			$('#anioFinVigenciaHidden').val(bean.anoFinVigencia);
			
			/**RURAL***/	
			formato14C.f_canDRCoord.val(bean.canDRCoord);
			formato14C.f_costDRCoord.val(bean.costDRCoord);
			formato14C.f_canIRCoord.val(bean.canIRCoord);
			formato14C.f_costIRCoord.val(bean.costIRCoord);
			
			formato14C.f_canDRSupe.val(bean.canDRSupe);
			formato14C.f_costDRSupe.val(bean.costDRSupe);
			formato14C.f_canIRSupe.val(bean.canIRSupe);
			formato14C.f_costIRSupe.val(bean.costIRSupe);
			
			formato14C.f_canDRGest.val(bean.canDRGest);
			formato14C.f_costDRGest.val(bean.costDRGest);
			formato14C.f_canIRGest.val(bean.canIRGest);
			formato14C.f_costIRGest.val(bean.costIRGest);
			
			formato14C.f_canDRAsist.val(bean.canDRAsist);
			formato14C.f_costDRAsist.val(bean.costDRAsist);
			formato14C.f_canIRAsist.val(bean.canIRAsist);
			formato14C.f_costIRAsist.val(bean.costIRAsist);
			
			/**PROVINCIAS*/
			formato14C.f_canDPCoord.val(bean.canDPCoord);
			formato14C.f_costDPCoord.val(bean.costDPCoord);
			formato14C.f_canIPCoord.val(bean.canIPCoord);
			formato14C.f_costIPCoord.val(bean.costIPCoord);
			
			formato14C.f_canDPSupe.val(bean.canDPSupe);
			formato14C.f_costDPSupe.val(bean.costDPSupe);
			formato14C.f_canIPSupe.val(bean.canIPSupe);
			formato14C.f_costIPSupe.val(bean.costIPSupe);
			
			formato14C.f_canDPGest.val(bean.canDPGest);
			formato14C.f_costDPGest.val(bean.costDPGest);
			formato14C.f_canIPGest.val(bean.canIPGest);
			formato14C.f_costIPGest.val(bean.costIPGest);
			
			formato14C.f_canDPAsist.val(bean.canDPAsist);
			formato14C.f_costDPAsist.val(bean.costDPAsist);
			formato14C.f_canIPAsist.val(bean.canIPAsist);
			formato14C.f_costIPAsist.val(bean.costIPAsist);
			
			/**LIMA***/
			formato14C.f_canDLCoord.val(bean.canDLCoord);
			formato14C.f_costDLCoord.val(bean.costDLCoord);
			formato14C.f_canILCoord.val(bean.canILCoord);
			formato14C.f_costILCoord.val(bean.costILCoord);
			
			formato14C.f_canDLSupe.val(bean.canDLSupe);
			formato14C.f_costDLSupe.val(bean.costDLSupe);
			formato14C.f_canILSupe.val(bean.canILSupe);
			formato14C.f_costILSupe.val(bean.costILSupe);
			
			formato14C.f_canDLGest.val(bean.canDLGest);
			formato14C.f_costDLGest.val(bean.costDLGest);
			formato14C.f_canILGest.val(bean.canILGest);
			formato14C.f_costILGest.val(bean.costILGest);
			
			formato14C.f_canDLAsist.val(bean.canDLAsist);
			formato14C.f_costDLAsist.val(bean.costDLAsist);
			formato14C.f_canILAsist.val(bean.canILAsist);
			formato14C.f_costILAsist.val(bean.costILAsist);
			
	        //llamando a las funciones para realizar los calculos
	        formato14C.formularioCompletarDecimales();
			formato14C.calculoTotal();			
			formato14C.soloNumerosEnteros();
			formato14C.soloNumerosDecimales();			
			
			formato14C.flagCarga.val('1');//inicializamos el flag de carga cuando editamos el archivo antes de cargar archivos
			/**para controlar los costos directos e indirectos*/
			console.debug("flag costo al momento de editar: "+bean.flagCosto);
			var flagCostoLima = bean.flagCosto;
			if(bean.flagCosto=='D'){
				formato14C.habilitarCostosDirectos();
				formato14C.deshabilitarCostosIndirectos();				
			}else if(bean.flagCosto=='I'){
				formato14C.habilitarCostosIndirectos();
				formato14C.deshabilitarCostosDirectos();	
			}else{
				console.debug("Ambos costos D e I activos");
				formato14C.habilitarCostosIndirectos();
				formato14C.habilitarCostosDirectos();	
			}	
			/***verificar si la empresa es edelnor o luz del sur*/
			console.debug("codigo empresa edelnor constante al llenar datos editar o visualizar: "+formato14C.cod_empresa_edelnor.val());
			console.debug("codigo empresa luz sur constante al llenar datos editar o visualizar: "+formato14C.cod_empresa_luz_sur.val());
			console.debug("codigo empresa seleccionado al llenar datos editar o visualizar: "+formato14C.f_empresa.val());
			if(formato14C.cod_empresa_edelnor.val()==formato14C.f_empresa.val() || 
					formato14C.cod_empresa_luz_sur.val()==formato14C.f_empresa.val()){
				formato14C.habilitarLima(flagCostoLima);										
			}else{
				formato14C.deshabilitarLima();
			}			
		},
		ocultarElementosEditar : function(){	
			$('#codEmpresa').attr("disabled",true);
			$('#periodoEnvio').attr("disabled",true);
			$('#anoIniVigencia').attr("disabled",true);
			$('#anoFinVigencia').attr("disabled",true);
		},
        verElementosEditar : function(){	
        	$('#codEmpresa').attr("disabled",false);
        	$('#periodoEnvio').attr("disabled",false);
        	$('#anoIniVigencia').attr("disabled",true);
			$('#anoFinVigencia').attr("disabled",true);
		},		
		//function para el evento onchange en empresa para cargar el periodo
		<portlet:namespace/>loadPeriodo : function(valPeriodo){		
			jQuery.ajax({
					url: formato14C.urlCargaPeriodo+'&'+formato14C.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					success: function(data) {		
						dwr.util.removeAllOptions("periodoEnvio");
						dwr.util.addOptions("periodoEnvio", data,"codigoItem","descripcionItem");
						if( valPeriodo.length!='' ){
							dwr.util.setValue("periodoEnvio", valPeriodo);
						}
						formato14C.<portlet:namespace/>loadCargaFlagPeriodo();										
					},error : function(){
						var addhtmError='Error de conexión.';					
						formato14C.dialogErrorContentF14C.html(addhtmError);
						formato14C.dialogErrorF14C.dialog("open");
						formato14C.initBlockUI();
					}
			});
		},
		/**Function para confirmar si quiere eliminar el registro o no*/
		confirmarEliminarF14C : function(codEmpresa,anoPresentacion,mesPresentacion,anoIniVigencia,anoFinVigencia,etapa,flagOperacion){	
			var admin = '${formato14CBean.admin}';
			if(flagOperacion=='ABIERTO'){
				var process=true;
				if( etapa=='ESTABLECIDO'  &&  admin=='false' ){
					process = false;
				}
				if(process){
					var addhtml='¿Está seguro que desea eliminar el registro del Formato 14C seleccionado?';
					formato14C.dialogConfirmContent.html(addhtml);
					formato14C.dialogConfirm.dialog("open");
					cod_Empresa=codEmpresa;
					ano_Presentacion=anoPresentacion;
					mes_Presentacion=mesPresentacion;
					ano_Inicio_Vigencia=anoIniVigencia;
					ano_Fin_Vigencia=anoFinVigencia;
					cod_Etapa=etapa;	
				}else{					
					var addhtmInfo='No tiene autorización para realizar esta acción';				
					formato14C.dialogInfoContent.html(addhtmInfo);
					formato14C.dialogInfo.dialog("open");	
				}			
			}else if(flagOperacion=='CERRADO'){				
				var addhtmInfo='El plazo para realizar esta acción se encuentra cerrado';				
				formato14C.dialogInfoContent.html(addhtmInfo);
				formato14C.dialogInfo.dialog("open");	
			}else{				
				var addhtmInfo='El registro seleccionado del Formato 14C ya fue enviado a OSINERGMIN-GART.';				
				formato14C.dialogInfoContent.html(addhtmInfo);
				formato14C.dialogInfo.dialog("open");	
			}			
		},
		/**Function para  eliminar el registro una vez hecho la confirmacion*/
		eliminarFormatoF14C : function(cod_Empresa,ano_Presentacion,mes_Presentacion,ano_Inicio_Vigencia,ano_Fin_Vigencia,cod_Etapa){			
			$.blockUI({ message: formato14C.mensajeEliminando });
			jQuery.ajax({
				url: formato14C.urlEliminar+'&'+formato14C.formCommand.serialize(),
				type: 'post',
				dataType: 'json',
				data: {				
				   <portlet:namespace />codEmpresa: cod_Empresa,
				   <portlet:namespace />anioPres: ano_Presentacion,
				   <portlet:namespace />mesPres: mes_Presentacion,
				   <portlet:namespace />anoIniVigencia: ano_Inicio_Vigencia,
				   <portlet:namespace />anoFinVigencia: ano_Fin_Vigencia,
				   <portlet:namespace />etapa: cod_Etapa
					},
				success: function(data) {
					if (data.resultado == "OK"){
						var addhtml2='El registro seleccionado del Formato 14C se eliminó satisfactoriamente.';					
						formato14C.dialogMessageContent.html(addhtml2);
						formato14C.dialogMessage.dialog("open");
						formato14C.buscarF14C();
						formato14C.initBlockUI();
					}
					else{						
						var addhtmError='Error al eliminar el registro seleccionado del Formato 14C.';					
						formato14C.dialogErrorContentF14C.html(addhtmError);
						formato14C.dialogErrorF14C.dialog("open");
						formato14C.initBlockUI();
					}
				},error : function(){
					var addhtmError='Error de conexión.';					
					formato14C.dialogErrorContentF14C.html(addhtmError);
					formato14C.dialogErrorF14C.dialog("open");
					formato14C.initBlockUI();
				}
			});
		},
		/*funcion para cargar el flag del periodo**/
		<portlet:namespace/>loadCargaFlagPeriodo : function() {
			jQuery.ajax({
				url: formato14C.urlCargaFlagPeriodo+'&'+formato14C.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					success: function(data) {
						//todas estos id de variables estan declaradas en en bean
						dwr.util.setValue("flagPeriodoEjecucion", data.flagPeriodoEjecucion);
						dwr.util.setValue("flagCosto", data.flagCosto);
						dwr.util.setValue("anoIniVigencia", data.anoIniVigencia);
						dwr.util.setValue("anoFinVigencia", data.anoFinVigencia);
						console.debug("flag periodo ejecucion al cargar desde el controller: "+data.flagPeriodoEjecucion);
						console.debug("flag costos al cargar desde el controller: "+data.flagCosto);
						
						dwr.util.setValue("idGrupoInfo", data.idGrupoInfo);
						dwr.util.setValue("etapaFinal", data.etapaFinal);
						console.debug("Entro a poner valor a etapa final del formato al cargar flag periodo");
						//formato14C.recargarPeriodoEjecucion();
						
						//formato14C.mostrarPeriodoEjecucion();
					
						/**para controlar los costos directos e indirectos*/
						var flagCostoLima = formato14C.flagCosto.val();
						if(formato14C.flagCosto.val()=='D'){
							formato14C.habilitarCostosDirectos();
							formato14C.deshabilitarCostosIndirectos();				
						}else if(formato14C.flagCosto.val()=='I'){
							formato14C.habilitarCostosIndirectos();
							formato14C.deshabilitarCostosDirectos();	
						}else{
							console.debug("Ambos costos D e I activos");
							formato14C.habilitarCostosIndirectos();
							formato14C.habilitarCostosDirectos();	
						}
						//verifico si es edelnor o luz del sur
						console.debug("codigo empresa edelnor constante al cargar flag periodo: "+formato14C.cod_empresa_edelnor.val());
						console.debug("codigo empresa luz sur constante al cargar flag periodo: "+formato14C.cod_empresa_luz_sur.val());
						console.debug("codigo empresa seleccionado al cargar flag periodo: "+formato14C.f_empresa.val());
						if(formato14C.cod_empresa_edelnor.val()==formato14C.f_empresa.val() || 
								formato14C.cod_empresa_luz_sur.val()==formato14C.f_empresa.val()){
							formato14C.habilitarLima(flagCostoLima);										
						}else{
							formato14C.deshabilitarLima();
						}						
					},error : function(){
						var addhtmError='Error de conexión.';					
						formato14C.dialogErrorContentF14C.html(addhtmError);
						formato14C.dialogErrorF14C.dialog("open");
						formato14C.initBlockUI();
					}
			});
		},
		validarGrupoInformacion : function(){
			if( $('#idGrupoInfo').val()=='0' ){
				//alert('Seleccione una Distribuidora Eléctrica'); 
				formato14C.dialogValidacionContent.html('Primero debe crear el Grupo de Información Bienal para el Año y Mes a declarar');
				formato14C.dialogValidacion.dialog("open");
				cod_focus=$('#idGrupoInfo');			
				return false;
			}
			return true;
		},
		//
		mostrarPeriodoEjecucion : function(){
			console.debug("flag periodo ejecucion al mostrar:  "+formato14C.f_flagPeriodo.val());
			console.debug("flag costo al mostrar:  "+formato14C.flagCosto.val());
			if(formato14C.f_flagPeriodo.val()=='S' ){			
				$('#anoIniVigencia').removeAttr("disabled");
				$('#anoIniVigencia').addClass("fise-editable");
				$('#anoFinVigencia').removeAttr("disabled");			
				$('#anoFinVigencia').addClass("fise-editable");			
			}else{			
				$('#anoIniVigencia').attr("disabled",true);
				$('#anoIniVigencia').removeClass("fise-editable");
				$('#anoFinVigencia').attr("disabled",true);	
				$('#anoFinVigencia').removeClass("fise-editable");
			}			
		},
		recargarPeriodoEjecucion : function(){
			var anoInicio;
			var anoFin;
			if(formato14C.f_periodoEnvio.val() != null){
				anoInicio = formato14C.f_periodoEnvio.val().substring(0,4);
				anoFin = formato14C.f_periodoEnvio.val().substring(0,4);
				console.debug("flag periodo ejecucion al recargar:  "+formato14C.f_flagPeriodo.val());
				console.debug("flag costo al recargar:  "+formato14C.flagCosto.val());
				if(formato14C.f_flagPeriodo.val()=='S' ){
					$('#anoIniVigencia').val(anoInicio);
					$('#anoIniVigencia').addClass("fise-editable");
					$('#anoFinVigencia').val(anoFin);
					$('#anoFinVigencia').addClass("fise-editable");	
				}else{				
					$('#anoIniVigencia').attr("disabled",true);
					$('#anoIniVigencia').removeClass("fise-editable");
					$('#anoFinVigencia').attr("disabled",true);
					$('#anoFinVigencia').removeClass("fise-editable");
				}		
			}
		},
		
		//function para inicializar el formulario
		inicializarFormulario : function(){	
			//para mantener datos en sesion al momento de regresar a la busqueda	
			if(formato14C.i_codEmpresaBusq.val()!=''){
				formato14C.f_empresa.val(formato14C.i_codEmpresaBusq.val());
			}else{
				formato14C.f_empresa.val('');
			}			
			formato14C.f_nombreSede.val('');
			formato14C.divInformacion.hide();			
			console.debug("flag periodo ejecucion al inicializar formulario:  "+formato14C.f_flagPeriodo.val());
			console.debug("flag costo al iniciar formulario:  "+formato14C.flagCosto.val());
			$('#anoIniVigencia').val('');
			$('#anoFinVigencia').val('');			
				
			/**para controlar los costos directos e indirectos*/
			var flagCostoLima = formato14C.flagCosto.val();
			if(formato14C.flagCosto.val()=='D'){
				formato14C.habilitarCostosDirectos();
				formato14C.deshabilitarCostosIndirectos();				
			}else if(formato14C.flagCosto.val()=='I'){
				formato14C.habilitarCostosIndirectos();
				formato14C.deshabilitarCostosDirectos();	
			}else{
				console.debug("Ambos costos D e I activos");
				formato14C.habilitarCostosIndirectos();
				formato14C.habilitarCostosDirectos();	
			}	
			/**para verificar si es edelnor o luz del sur*/
			console.debug("codigo empresa edelnor constante al iniciar formaulario: "+formato14C.cod_empresa_edelnor.val());
			console.debug("codigo empresa luz sur constante al iniciar formaulario: "+formato14C.cod_empresa_luz_sur.val());
			console.debug("codigo empresa seleccionado al iniciar formulario: "+formato14C.f_empresa.val());
			if(formato14C.cod_empresa_edelnor.val()==formato14C.f_empresa.val() || 
					formato14C.cod_empresa_luz_sur.val()==formato14C.f_empresa.val()){
				formato14C.habilitarLima(flagCostoLima);										
			}else{
				formato14C.deshabilitarLima();
			}		
			
			
			formato14C.f_numRural.val('0');
			formato14C.f_numUrbProv.val('0');
			formato14C.f_numUrbLima.val('0');
			formato14C.f_costoPromRural.val('0.00');
			formato14C.f_costoPromUrbProv.val('0.00');
		    formato14C.f_costoPromUrbLima.val('0.00');
			/**detalle*/
			//RURAL
			formato14C.f_canDRCoord.val('0');
			formato14C.f_costDRCoord.val('0.00');
			formato14C.f_canIRCoord.val('0');
			formato14C.f_costIRCoord.val('0.00');
			//no editables
			formato14C.f_canDRGP.val('0');
			formato14C.f_costDRGP.val('0.00');
			formato14C.f_canIRGP.val('0');
			formato14C.f_costIRGP.val('0.00');
			formato14C.f_costTotalRGP.val('0.00');
			formato14C.f_costTotalRCoord.val('0.00');
			formato14C.f_costTotalRSupe.val('0.00');
			formato14C.f_costTotalRGest.val('0.00');
			formato14C.f_costTotalRAsist.val('0.00');
			//fin de no editables
			
			formato14C.f_canDRSupe.val('0');
			formato14C.f_costDRSupe.val('0.00');
			formato14C.f_canIRSupe.val('0');
			formato14C.f_costIRSupe.val('0.00');		
			
			formato14C.f_canDRGest.val('0');
			formato14C.f_costDRGest.val('0.00');
			formato14C.f_canIRGest.val('0');
			formato14C.f_costIRGest.val('0.00');
		
			
			formato14C.f_canDRAsist.val('0');
			formato14C.f_costDRAsist.val('0.00');
			formato14C.f_canIRAsist.val('0');
			formato14C.f_costIRAsist.val('0.00');
			
			
			//PROVINCIA
			formato14C.f_canDPCoord.val('0');
			formato14C.f_costDPCoord.val('0.00');
			formato14C.f_canIPCoord.val('0');
			formato14C.f_costIPCoord.val('0.00');
			
			
			formato14C.f_canDPSupe.val('0');
			formato14C.f_costDPSupe.val('0.00');
			formato14C.f_canIPSupe.val('0');
			formato14C.f_costIPSupe.val('0.00');
			
			
			formato14C.f_canDPGest.val('0');
			formato14C.f_costDPGest.val('0.00');
			formato14C.f_canIPGest.val('0');
			formato14C.f_costIPGest.val('0.00');
					
			
			formato14C.f_canDPAsist.val('0');
			formato14C.f_costDPAsist.val('0.00');
			formato14C.f_canIPAsist.val('0');
			formato14C.f_costIPAsist.val('0.00');
			
			//no editables
			formato14C.f_canDPGP.val('0');
			formato14C.f_costDPGP.val('0.00');
			formato14C.f_canIPGP.val('0');
			formato14C.f_costIPGP.val('0.00');
			formato14C.f_costTotalPGP.val('0.00');
			formato14C.f_costTotalPCoord.val('0.00');
			formato14C.f_costTotalPSupe.val('0.00');
			formato14C.f_costTotalPGest.val('0.00');
			formato14C.f_costTotalPAsist.val('0.00');
			//fin de no editables
			
			//LIMA
			formato14C.f_canDLCoord.val('0');
			formato14C.f_costDLCoord.val('0.00');
			formato14C.f_canILCoord.val('0');
			formato14C.f_costILCoord.val('0.00');			
			
			formato14C.f_canDLSupe.val('0');
			formato14C.f_costDLSupe.val('0.00');
			formato14C.f_canILSupe.val('0');
			formato14C.f_costILSupe.val('0.00');		
			
			formato14C.f_canDLGest.val('0');
			formato14C.f_costDLGest.val('0.00');
			formato14C.f_canILGest.val('0');
			formato14C.f_costILGest.val('0.00');		
			
			formato14C.f_canDLAsist.val('0');
			formato14C.f_costDLAsist.val('0.00');
			formato14C.f_canILAsist.val('0');
			formato14C.f_costILAsist.val('0.00');			
			//no editables
			formato14C.f_canDLGP.val('0');		
			formato14C.f_costDLGP.val('0.00');		
			formato14C.f_canILGP.val('0');		
			formato14C.f_costILGP.val('0.00');		
			formato14C.f_costTotalLGP.val('0.00');		
			formato14C.f_costTotalLCoord.val('0.00');		
			formato14C.f_costTotalLSupe.val('0.00');		
			formato14C.f_costTotalLGest.val('0.00');		
			formato14C.f_costTotalLAsist.val('0.00');		
			//fin de no editables
			
			//no editables totales
			formato14C.f_numTotal.val('0');	
			formato14C.f_canTotalCoord.val('0');	
			formato14C.f_costTotalCoord.val('0.00');	
			formato14C.f_canTotalSupe.val('0');	
			formato14C.f_costTotalSupe.val('0.00');	
			formato14C.f_canTotalGest.val('0');	
			formato14C.f_costTotalGest.val('0.00');	
			formato14C.f_canTotalAsist.val('0');	
			formato14C.f_costTotalAsist.val('0.00');	
			formato14C.f_canTotalGP.val('0');	
			formato14C.f_costTotalGP.val('0.00');	
			formato14C.f_costTotalPromedio.val('0.00');	
			//fin de no editables
			
			//quitamos los componentes deshabilitados
			formato14C.f_empresa.attr("disabled",false);
			formato14C.f_periodoEnvio.attr("disabled",false);
			//
			//formato14C.deshabilitarCampos();
			//
			formato14C.soloNumerosEnteros();
			formato14C.soloNumerosDecimales();
		},		
		//function para validar solo numeros enteros
		soloNumerosEnteros : function(){
			/***cabecera**/
			formato14C.f_numRural.attr("onkeypress","return soloNumerosEntero(event, 1, 'numRural',7,0)");
			formato14C.f_numUrbProv.attr("onkeypress","return soloNumerosEntero(event, 1, 'numUrbProv',7,0)");
			formato14C.f_numUrbLima.attr("onkeypress","return soloNumerosEntero(event, 1, 'numUrbLima',7,0)");
			
			/**detalle**/
			//RURAL
			formato14C.f_canDRCoord.attr("onkeypress","return soloNumerosEntero(event, 1, 'canDRCoord',7,0)");
			formato14C.f_canIRCoord.attr("onkeypress","return soloNumerosEntero(event, 1, 'canIRCoord',7,0)");
			
			formato14C.f_canDRSupe.attr("onkeypress","return soloNumerosEntero(event, 1, 'canDRSupe',7,0)");
			formato14C.f_canIRSupe.attr("onkeypress","return soloNumerosEntero(event, 1, 'canIRSupe',7,0)");
			
			formato14C.f_canDRGest.attr("onkeypress","return soloNumerosEntero(event, 1, 'canDRGest',7,0)");
			formato14C.f_canIRGest.attr("onkeypress","return soloNumerosEntero(event, 1, 'canIRGest',7,0)");
			
			formato14C.f_canDRAsist.attr("onkeypress","return soloNumerosEntero(event, 1, 'canDRAsist',7,0)");
			formato14C.f_canIRAsist.attr("onkeypress","return soloNumerosEntero(event, 1, 'canIRAsist',7,0)");
			
			//PROVINCIAS
			formato14C.f_canDPCoord.attr("onkeypress","return soloNumerosEntero(event, 1, 'canDPCoord',7,0)");
			formato14C.f_canIPCoord.attr("onkeypress","return soloNumerosEntero(event, 1, 'canIPCoord',7,0)");
			
			formato14C.f_canDPSupe.attr("onkeypress","return soloNumerosEntero(event, 1, 'canDPSupe',7,0)");
			formato14C.f_canIPSupe.attr("onkeypress","return soloNumerosEntero(event, 1, 'canIPSupe',7,0)");
			
			formato14C.f_canDPGest.attr("onkeypress","return soloNumerosEntero(event, 1, 'canDPGest',7,0)");
			formato14C.f_canIPGest.attr("onkeypress","return soloNumerosEntero(event, 1, 'canIPGest',7,0)");
			
			formato14C.f_canDPAsist.attr("onkeypress","return soloNumerosEntero(event, 1, 'canDPAsist',7,0)");
			formato14C.f_canIPAsist.attr("onkeypress","return soloNumerosEntero(event, 1, 'canIPAsist',7,0)");
			
			//LIMA
			formato14C.f_canDLCoord.attr("onkeypress","return soloNumerosEntero(event, 1, 'canDLCoord',7,0)");
			formato14C.f_canILCoord.attr("onkeypress","return soloNumerosEntero(event, 1, 'canILCoord',7,0)");
			
			formato14C.f_canDLSupe.attr("onkeypress","return soloNumerosEntero(event, 1, 'canDLSupe',7,0)");
			formato14C.f_canILSupe.attr("onkeypress","return soloNumerosEntero(event, 1, 'canILSupe',7,0)");
			
			formato14C.f_canDLGest.attr("onkeypress","return soloNumerosEntero(event, 1, 'canDLGest',7,0)");
			formato14C.f_canILGest.attr("onkeypress","return soloNumerosEntero(event, 1, 'canILGest',7,0)");
			
			formato14C.f_canDLAsist.attr("onkeypress","return soloNumerosEntero(event, 1, 'canDLAsist',7,0)");
			formato14C.f_canILAsist.attr("onkeypress","return soloNumerosEntero(event, 1, 'canILAsist',7,0)");
			
		},
		//function para validar solo numeros decimales
		soloNumerosDecimales : function(){
			/**cabecera*/
			formato14C.f_costoPromRural.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'costoPromRural',7,2)");
			formato14C.f_costoPromUrbProv.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'costoPromUrbProv',7,2)");
			formato14C.f_costoPromUrbLima.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'costoPromUrbLima',7,2)");
			/**detalle*/
			//RURAL
			formato14C.f_costDRCoord.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'costDRCoord',7,2)");
			formato14C.f_costIRCoord.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'costIRCoord',7,2)");
			
			formato14C.f_costDRSupe.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'costDRSupe',7,2)");
			formato14C.f_costIRSupe.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'costIRSupe',7,2)");
			
			formato14C.f_costDRGest.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'costDRGest',7,2)");
			formato14C.f_costIRGest.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'costIRGest',7,2)");
			
			formato14C.f_costDRAsist.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'costDRAsist',7,2)");
			formato14C.f_costIRAsist.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'costIRAsist',7,2)");
			
			//PROVINCIA
			formato14C.f_costDPCoord.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'costDPCoord',7,2)");
			formato14C.f_costIPCoord.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'costIPCoord',7,2)");
			
			formato14C.f_costDPSupe.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'costDPSupe',7,2)");
			formato14C.f_costIPSupe.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'costIPSupe',7,2)");
			
			formato14C.f_costDPGest.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'costDPGest',7,2)");
			formato14C.f_costIPGest.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'costIPGest',7,2)");
			
			formato14C.f_costDPAsist.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'costDPAsist',7,2)");
			formato14C.f_costIPAsist.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'costIPAsist',7,2)");
			
			//LIMA
			formato14C.f_costDLCoord.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'costDLCoord',7,2)");
			formato14C.f_costILCoord.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'costILCoord',7,2)");
			
			formato14C.f_costDLSupe.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'costDLSupe',7,2)");
			formato14C.f_costILSupe.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'costILSupe',7,2)");
			
			formato14C.f_costDLGest.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'costDLGest',7,2)");
			formato14C.f_costILGest.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'costILGest',7,2)");
			
			formato14C.f_costDLAsist.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'costDLAsist',7,2)");
			formato14C.f_costILAsist.attr("onKeyUp","return soloNumerosDecimales(event, 2, 'costILAsist',7,2)");
		},
		//function para realizar los calculos totales
		calculoTotal : function(){
			formato14C.formularioCompletarDecimales();
			//RURAL
			formato14C.totalCantidadCostoDirectoRural();
			formato14C.totalCostoDirectoRural();
			formato14C.totalCantidadCostoIndirectoRural();			
			formato14C.totalCostoIndirectoRural();
			
			formato14C.totalCostoRuralCoord();
			formato14C.totalCostoRuralSupe();
			formato14C.totalCostoRuralGest();
			formato14C.totalCostoRuralAsist();
			
			formato14C.totalCostoRuralGeneral();
			
			//PROVINCIAS
			formato14C.totalCantidadCostoDirectoProv();
			formato14C.totalCostoDirectoProv();
			formato14C.totalCantidadCostoIndirectoProv();			
			formato14C.totalCostoIndirectoProv();
			
			formato14C.totalCostoProvCoord();
			formato14C.totalCostoProvSupe();
			formato14C.totalCostoProvGest();
			formato14C.totalCostoProvAsist();
			
			formato14C.totalCostoProvGeneral();			
			
			//LIMA
			formato14C.totalCantidadCostoDirectoLima();
			formato14C.totalCostoDirectoLima();
			formato14C.totalCantidadCostoIndirectoLima();			
			formato14C.totalCostoIndirectoLima();
			
			formato14C.totalCostoLimaCoord();
			formato14C.totalCostoLimaSupe();
			formato14C.totalCostoLimaGest();
			formato14C.totalCostoLimaAsist();
			
			formato14C.totalCostoLimaGeneral();
			
			//TOTALES
			
			formato14C.totalCantidadGeneralCoord();
			formato14C.totalCantidadGeneralSupe();
			formato14C.totalCantidadGeneralGest();
			formato14C.totalCantidadGeneralAsist();	
			
			formato14C.totalCostoGeneralCoord();
			formato14C.totalCostoGeneralSupe();
			formato14C.totalCostoGeneralGest();
			formato14C.totalCostoGeneralAsist();
			
			formato14C.totalCantidadGeneralPersonal();
			formato14C.totalCostoGeneralPersonal();	
			
			formato14C.totalCantidadGeneralBenefiarios();
			formato14C.totalCostoPromedioGeneral();		
		},
		/*******CALCULOS DE TOTALES RURAL*******/
		//total de la cantidad de gestion de personal para el costo directo zona rural
		totalCantidadCostoDirectoRural : function(){
			var cantidadCoordDR;
			var cantidadSupeDR;
			var cantidadGestDR;
			var cantidadAsistDR;		
			var totalCantidadDR;
			var totalCantidadDRRedondeo;
			cantidadCoordDR = formato14C.f_canDRCoord.val();
			cantidadSupeDR = formato14C.f_canDRSupe.val();
			cantidadGestDR = formato14C.f_canDRGest.val();
			cantidadAsistDR = formato14C.f_canDRAsist.val();
		
			totalCantidadDR = parseFloat(cantidadCoordDR)+parseFloat(cantidadSupeDR)+parseFloat(cantidadGestDR)+parseFloat(cantidadAsistDR);				
			totalCantidadDRRedondeo = redondeo(totalCantidadDR, 2);			
			formato14C.f_canDRGP.val(parseFloat(totalCantidadDRRedondeo));
		},
		//total de costo directo de gestion de personal para zona rural
		totalCostoDirectoRural : function(){
			var costoCoordDR;
			var costoSupeDR;
			var costoGestDR;
			var costoAsistDR;			
			var costoTotalDR;
			var costoTotalDRRedondeo;			
			costoCoordDR = formato14C.f_costDRCoord.val();
			costoSupeDR = formato14C.f_costDRSupe.val();
			costoGestDR = formato14C.f_costDRGest.val();
			costoAsistDR = formato14C.f_costDRAsist.val();
		
			costoTotalDR = parseFloat(costoCoordDR)+parseFloat(costoSupeDR)+parseFloat(costoGestDR)+parseFloat(costoAsistDR);
			costoTotalDRRedondeo = redondeo(costoTotalDR, 2);
			formato14C.f_costDRGP.val(parseFloat(costoTotalDRRedondeo));
		},
		//total de la cantidad de gestion de personal para el costo indirecto zona rural
		totalCantidadCostoIndirectoRural : function(){
			var cantidadCoordIR;
			var cantidadSupeIR;
			var cantidadGestIR;
			var cantidadAsistIR;		
			var totalCantidadIR;
			var totalCantidadIRRedondeo;
			cantidadCoordIR = formato14C.f_canIRCoord.val();
			cantidadSupeIR = formato14C.f_canIRSupe.val();
			cantidadGestIR = formato14C.f_canIRGest.val();
			cantidadAsistIR = formato14C.f_canIRAsist.val();
		
			totalCantidadIR = parseFloat(cantidadCoordIR)+parseFloat(cantidadSupeIR)+parseFloat(cantidadGestIR)+parseFloat(cantidadAsistIR);				
			totalCantidadIRRedondeo = redondeo(totalCantidadIR, 2);			
			formato14C.f_canIRGP.val(parseFloat(totalCantidadIRRedondeo));
		},
		//total de costo indirecto de gestion de personal para zona rural
		totalCostoIndirectoRural : function(){
			var costoCoordIR;
			var costoSupeIR;
			var costoGestIR;
			var costoAsistIR;			
			var costoTotalIR;
			var costoTotalIRRedondeo;			
			costoCoordIR = formato14C.f_costIRCoord.val();
			costoSupeIR = formato14C.f_costIRSupe.val();
			costoGestIR = formato14C.f_costIRGest.val();
			costoAsistIR = formato14C.f_costIRAsist.val();
		
			costoTotalIR = parseFloat(costoCoordIR)+parseFloat(costoSupeIR)+parseFloat(costoGestIR)+parseFloat(costoAsistIR);
			costoTotalIRRedondeo = redondeo(costoTotalIR, 2);
			formato14C.f_costIRGP.val(parseFloat(costoTotalIRRedondeo));
		},
		//total costo rural para coordinador
		totalCostoRuralCoord : function(){
			var cantCoordDR;
			var costCoordDR;
			var cantCoordIR;
			var costCoordIR;	
			var costoTotalCoord;
			var costoTotalCoordRedondeo;
			cantCoordDR = formato14C.f_canDRCoord.val();
			costCoordDR = formato14C.f_costDRCoord.val();
			cantCoordIR = formato14C.f_canIRCoord.val();
			costCoordIR = formato14C.f_costIRCoord.val();
		
			costoTotalCoord = (parseFloat(cantCoordDR)*parseFloat(costCoordDR)) + (parseFloat(cantCoordIR)*parseFloat(costCoordIR));				
			costoTotalCoordRedondeo = redondeo(costoTotalCoord, 2);			
			formato14C.f_costTotalRCoord.val(parseFloat(costoTotalCoordRedondeo));
		},
		//total costo rural para supervisor
		totalCostoRuralSupe : function(){
			var cantSupeDR;
			var costSupeDR;
			var cantSupeIR;
			var costSupeIR;	
			var costoTotalSupe;
			var costoTotalSupeRedondeo;
			cantSupeDR = formato14C.f_canDRSupe.val();
			costSupeDR = formato14C.f_costDRSupe.val();
			cantSupeIR = formato14C.f_canIRSupe.val();
			costSupeIR = formato14C.f_costIRSupe.val();
		
			costoTotalSupe = (parseFloat(cantSupeDR)*parseFloat(costSupeDR)) + (parseFloat(cantSupeIR)*parseFloat(costSupeIR));				
			costoTotalSupeRedondeo = redondeo(costoTotalSupe, 2);			
			formato14C.f_costTotalRSupe.val(parseFloat(costoTotalSupeRedondeo));
		},
		//total costo rural para gestor
		totalCostoRuralGest : function(){
			var cantGestDR;
			var costGestDR;
			var cantGestIR;
			var costGestIR;	
			var costoTotalGest;
			var costoTotalGestRedondeo;
			cantGestDR = formato14C.f_canDRGest.val();
			costGestDR = formato14C.f_costDRGest.val();
			cantGestIR = formato14C.f_canIRGest.val();
			costGestIR = formato14C.f_costIRGest.val();
		
			costoTotalGest = (parseFloat(cantGestDR)*parseFloat(costGestDR)) + (parseFloat(cantGestIR)*parseFloat(costGestIR));				
			costoTotalGestRedondeo = redondeo(costoTotalGest, 2);			
			formato14C.f_costTotalRGest.val(parseFloat(costoTotalGestRedondeo));
		},
		//total costo rural para asistente
		totalCostoRuralAsist : function(){
			var cantAsistDR;
			var costAsistDR;
			var cantAsistIR;
			var costAsistIR;	
			var costoTotalAsist;
			var costoTotalAsistRedondeo;
			cantAsistDR = formato14C.f_canDRAsist.val();
			costAsistDR = formato14C.f_costDRAsist.val();
			cantAsistIR = formato14C.f_canIRAsist.val();
			costAsistIR = formato14C.f_costIRAsist.val();
		
			costoTotalAsist = (parseFloat(cantAsistDR)*parseFloat(costAsistDR)) + (parseFloat(cantAsistIR)*parseFloat(costAsistIR));				
			costoTotalAsistRedondeo = redondeo(costoTotalAsist, 2);			
			formato14C.f_costTotalRAsist.val(parseFloat(costoTotalAsistRedondeo));
		},
		//total costo para toda la zona rural
		totalCostoRuralGeneral : function(){
			var costTotalCoordR;
			var costTotalSupeR;
			var costTotalGestR;
			var costTotalAsistR;	
			var costoTotalCoordRural;
			var costoTotalCoordRuralRedondeo;
			costTotalCoordR = formato14C.f_costTotalRCoord.val();
			costTotalSupeR = formato14C.f_costTotalRSupe.val();
			costTotalGestR = formato14C.f_costTotalRGest.val();
			costTotalAsistR = formato14C.f_costTotalRAsist.val();
		
			costoTotalCoordRural = parseFloat(costTotalCoordR)+ parseFloat(costTotalSupeR) + parseFloat(costTotalGestR) + parseFloat(costTotalAsistR);				
			costoTotalCoordRuralRedondeo = redondeo(costoTotalCoordRural, 2);			
			formato14C.f_costTotalRGP.val(parseFloat(costoTotalCoordRuralRedondeo));
		},
		/*******CALCULOS DE TOTALES PROVINCIAS*******/
		//total de la cantidad de gestion de personal para el costo directo zona provincias
		totalCantidadCostoDirectoProv : function(){
			var cantidadCoordDP;
			var cantidadSupeDP;
			var cantidadGestDP;
			var cantidadAsistDP;		
			var totalCantidadDP;
			var totalCantidadDPRedondeo;
			cantidadCoordDP = formato14C.f_canDPCoord.val();
			cantidadSupeDP = formato14C.f_canDPSupe.val();
			cantidadGestDP = formato14C.f_canDPGest.val();
			cantidadAsistDP = formato14C.f_canDPAsist.val();
		
			totalCantidadDP = parseFloat(cantidadCoordDP)+parseFloat(cantidadSupeDP)+parseFloat(cantidadGestDP)+parseFloat(cantidadAsistDP);				
			totalCantidadDPRedondeo = redondeo(totalCantidadDP, 2);			
			formato14C.f_canDPGP.val(parseFloat(totalCantidadDPRedondeo));
		},
		//total de costo directo de gestion de personal para zona provincias
		totalCostoDirectoProv : function(){
			var costoCoordDP;
			var costoSupeDP;
			var costoGestDP;
			var costoAsistDP;			
			var costoTotalDP;
			var costoTotalDPRedondeo;			
			costoCoordDP = formato14C.f_costDPCoord.val();
			costoSupeDP = formato14C.f_costDPSupe.val();
			costoGestDP = formato14C.f_costDPGest.val();
			costoAsistDP = formato14C.f_costDPAsist.val();
		
			costoTotalDP = parseFloat(costoCoordDP)+parseFloat(costoSupeDP)+parseFloat(costoGestDP)+parseFloat(costoAsistDP);
			costoTotalDPRedondeo = redondeo(costoTotalDP, 2);
			formato14C.f_costDPGP.val(parseFloat(costoTotalDPRedondeo));
		},
		//total de la cantidad de gestion de personal para el costo indirecto zona provincias
		totalCantidadCostoIndirectoProv : function(){
			var cantidadCoordIP;
			var cantidadSupeIP;
			var cantidadGestIP;
			var cantidadAsistIP;		
			var totalCantidadIP;
			var totalCantidadIPRedondeo;
			cantidadCoordIP = formato14C.f_canIPCoord.val();
			cantidadSupeIP = formato14C.f_canIPSupe.val();
			cantidadGestIP = formato14C.f_canIPGest.val();
			cantidadAsistIP = formato14C.f_canIPAsist.val();
		
			totalCantidadIP = parseFloat(cantidadCoordIP)+parseFloat(cantidadSupeIP)+parseFloat(cantidadGestIP)+parseFloat(cantidadAsistIP);				
			totalCantidadIPRedondeo = redondeo(totalCantidadIP, 2);			
			formato14C.f_canIPGP.val(parseFloat(totalCantidadIPRedondeo));
		},
		//total de costo indirecto de gestion de personal para zona provincias
		totalCostoIndirectoProv : function(){
			var costoCoordIP;
			var costoSupeIP;
			var costoGestIP;
			var costoAsistIP;			
			var costoTotalIP;
			var costoTotalIPRedondeo;			
			costoCoordIP = formato14C.f_costIPCoord.val();
			costoSupeIP = formato14C.f_costIPSupe.val();
			costoGestIP = formato14C.f_costIPGest.val();
			costoAsistIP = formato14C.f_costIPAsist.val();
		
			costoTotalIP = parseFloat(costoCoordIP)+parseFloat(costoSupeIP)+parseFloat(costoGestIP)+parseFloat(costoAsistIP);
			costoTotalIPRedondeo = redondeo(costoTotalIP, 2);
			formato14C.f_costIPGP.val(parseFloat(costoTotalIPRedondeo));
		},
		//total costo provincias para coordinador
		totalCostoProvCoord : function(){
			var cantCoordDP;
			var costCoordDP;
			var cantCoordIP;
			var costCoordIP;	
			var costoTotalCoord;
			var costoTotalCoorDPedondeo;
			cantCoordDP = formato14C.f_canDPCoord.val();
			costCoordDP = formato14C.f_costDPCoord.val();
			cantCoordIP = formato14C.f_canIPCoord.val();
			costCoordIP = formato14C.f_costIPCoord.val();
		
			costoTotalCoord = (parseFloat(cantCoordDP)*parseFloat(costCoordDP)) + (parseFloat(cantCoordIP)*parseFloat(costCoordIP));				
			costoTotalCoorDPedondeo = redondeo(costoTotalCoord, 2);			
			formato14C.f_costTotalPCoord.val(parseFloat(costoTotalCoorDPedondeo));
		},
		//total costo provincias para supervisor
		totalCostoProvSupe : function(){
			var cantSupeDP;
			var costSupeDP;
			var cantSupeIP;
			var costSupeIP;	
			var costoTotalSupe;
			var costoTotalSupeRedondeo;
			cantSupeDP = formato14C.f_canDPSupe.val();
			costSupeDP = formato14C.f_costDPSupe.val();
			cantSupeIP = formato14C.f_canIPSupe.val();
			costSupeIP = formato14C.f_costIPSupe.val();
		
			costoTotalSupe = (parseFloat(cantSupeDP)*parseFloat(costSupeDP)) + (parseFloat(cantSupeIP)*parseFloat(costSupeIP));				
			costoTotalSupeRedondeo = redondeo(costoTotalSupe, 2);			
			formato14C.f_costTotalPSupe.val(parseFloat(costoTotalSupeRedondeo));
		},
		//total costo provincias para gestor
		totalCostoProvGest : function(){
			var cantGestDP;
			var costGestDP;
			var cantGestIP;
			var costGestIP;	
			var costoTotalGest;
			var costoTotalGestRedondeo;
			cantGestDP = formato14C.f_canDPGest.val();
			costGestDP = formato14C.f_costDPGest.val();
			cantGestIP = formato14C.f_canIPGest.val();
			costGestIP = formato14C.f_costIPGest.val();
		
			costoTotalGest = (parseFloat(cantGestDP)*parseFloat(costGestDP)) + (parseFloat(cantGestIP)*parseFloat(costGestIP));				
			costoTotalGestRedondeo = redondeo(costoTotalGest, 2);			
			formato14C.f_costTotalPGest.val(parseFloat(costoTotalGestRedondeo));
		},
		//total costo rural para asistente
		totalCostoProvAsist : function(){
			var cantAsistDP;
			var costAsistDP;
			var cantAsistIP;
			var costAsistIP;	
			var costoTotalAsist;
			var costoTotalAsistRedondeo;
			cantAsistDP = formato14C.f_canDPAsist.val();
			costAsistDP = formato14C.f_costDPAsist.val();
			cantAsistIP = formato14C.f_canIPAsist.val();
			costAsistIP = formato14C.f_costIPAsist.val();
		
			costoTotalAsist = (parseFloat(cantAsistDP)*parseFloat(costAsistDP)) + (parseFloat(cantAsistIP)*parseFloat(costAsistIP));				
			costoTotalAsistRedondeo = redondeo(costoTotalAsist, 2);			
			formato14C.f_costTotalPAsist.val(parseFloat(costoTotalAsistRedondeo));
		},
		//total costo para toda la zona provincias
		totalCostoProvGeneral : function(){
			var costTotalCoordP;
			var costTotalSupeP;
			var costTotalGestP;
			var costTotalAsistP;	
			var costoTotalCoordProv;
			var costoTotalCoordProvRedondeo;
			costTotalCoordP = formato14C.f_costTotalPCoord.val();
			costTotalSupeP = formato14C.f_costTotalPSupe.val();
			costTotalGestP = formato14C.f_costTotalPGest.val();
			costTotalAsistP = formato14C.f_costTotalPAsist.val();
		
			costoTotalCoordProv = parseFloat(costTotalCoordP)+ parseFloat(costTotalSupeP) + parseFloat(costTotalGestP) + parseFloat(costTotalAsistP);				
			costoTotalCoordProvRedondeo = redondeo(costoTotalCoordProv, 2);			
			formato14C.f_costTotalPGP.val(parseFloat(costoTotalCoordProvRedondeo));
		},
		
		/*******CALCULOS DE TOTALES LIMA*******/
		//total de la cantidad de gestion de personal para el costo directo zona lima
		totalCantidadCostoDirectoLima : function(){
			var cantidadCoordDL;
			var cantidadSupeDL;
			var cantidadGestDL;
			var cantidadAsistDL;		
			var totalCantidadDL;
			var totalCantidadDLRedondeo;
			cantidadCoordDL = formato14C.f_canDLCoord.val();
			cantidadSupeDL = formato14C.f_canDLSupe.val();
			cantidadGestDL = formato14C.f_canDLGest.val();
			cantidadAsistDL = formato14C.f_canDLAsist.val();
		
			totalCantidadDL = parseFloat(cantidadCoordDL)+parseFloat(cantidadSupeDL)+parseFloat(cantidadGestDL)+parseFloat(cantidadAsistDL);				
			totalCantidadDLRedondeo = redondeo(totalCantidadDL, 2);			
			formato14C.f_canDLGP.val(parseFloat(totalCantidadDLRedondeo));
		},
		//total de costo directo de gestion de personal para zona lima
		totalCostoDirectoLima : function(){
			var costoCoordDL;
			var costoSupeDL;
			var costoGestDL;
			var costoAsistDL;			
			var costoTotalDL;
			var costoTotalDLRedondeo;			
			costoCoordDL = formato14C.f_costDLCoord.val();
			costoSupeDL = formato14C.f_costDLSupe.val();
			costoGestDL = formato14C.f_costDLGest.val();
			costoAsistDL = formato14C.f_costDLAsist.val();
		
			costoTotalDL = parseFloat(costoCoordDL)+parseFloat(costoSupeDL)+parseFloat(costoGestDL)+parseFloat(costoAsistDL);
			costoTotalDLRedondeo = redondeo(costoTotalDL, 2);
			formato14C.f_costDLGP.val(parseFloat(costoTotalDLRedondeo));
		},
		//total de la cantidad de gestion de personal para el costo indirecto zona lima
		totalCantidadCostoIndirectoLima : function(){
			var cantidadCoordIL;
			var cantidadSupeIL;
			var cantidadGestIL;
			var cantidadAsistIL;		
			var totalCantidadIL;
			var totalCantidadILRedondeo;
			cantidadCoordIL = formato14C.f_canILCoord.val();
			cantidadSupeIL = formato14C.f_canILSupe.val();
			cantidadGestIL = formato14C.f_canILGest.val();
			cantidadAsistIL = formato14C.f_canILAsist.val();
		
			totalCantidadIL = parseFloat(cantidadCoordIL)+parseFloat(cantidadSupeIL)+parseFloat(cantidadGestIL)+parseFloat(cantidadAsistIL);				
			totalCantidadILRedondeo = redondeo(totalCantidadIL, 2);			
			formato14C.f_canILGP.val(parseFloat(totalCantidadILRedondeo));
		},
		//total de costo indirecto de gestion de personal para zona lima
		totalCostoIndirectoLima: function(){
			var costoCoordIL;
			var costoSupeIL;
			var costoGestIL;
			var costoAsistIL;			
			var costoTotalIL;
			var costoTotalILRedondeo;			
			costoCoordIL = formato14C.f_costILCoord.val();
			costoSupeIL = formato14C.f_costILSupe.val();
			costoGestIL = formato14C.f_costILGest.val();
			costoAsistIL = formato14C.f_costILAsist.val();
		
			costoTotalIL = parseFloat(costoCoordIL)+parseFloat(costoSupeIL)+parseFloat(costoGestIL)+parseFloat(costoAsistIL);
			costoTotalILRedondeo = redondeo(costoTotalIL, 2);
			formato14C.f_costILGP.val(parseFloat(costoTotalILRedondeo));
		},
		//total costo lima para coordinador
		totalCostoLimaCoord : function(){
			var cantCoordDL;
			var costCoordDL;
			var cantCoordIL;
			var costCoordIL;	
			var costoTotalCoord;
			var costoTotalCoorDLedondeo;
			cantCoordDL = formato14C.f_canDLCoord.val();
			costCoordDL = formato14C.f_costDLCoord.val();
			cantCoordIL = formato14C.f_canILCoord.val();
			costCoordIL = formato14C.f_costILCoord.val();
		
			costoTotalCoord = (parseFloat(cantCoordDL)*parseFloat(costCoordDL)) + (parseFloat(cantCoordIL)*parseFloat(costCoordIL));				
			costoTotalCoorDLedondeo = redondeo(costoTotalCoord, 2);			
			formato14C.f_costTotalLCoord.val(parseFloat(costoTotalCoorDLedondeo));
		},
		//total costo lima para supervisor
		totalCostoLimaSupe : function(){
			var cantSupeDL;
			var costSupeDL;
			var cantSupeIL;
			var costSupeIL;	
			var costoTotalSupe;
			var costoTotalSupeRedondeo;
			cantSupeDL = formato14C.f_canDLSupe.val();
			costSupeDL = formato14C.f_costDLSupe.val();
			cantSupeIL = formato14C.f_canILSupe.val();
			costSupeIL = formato14C.f_costILSupe.val();
		
			costoTotalSupe = (parseFloat(cantSupeDL)*parseFloat(costSupeDL)) + (parseFloat(cantSupeIL)*parseFloat(costSupeIL));				
			costoTotalSupeRedondeo = redondeo(costoTotalSupe, 2);			
			formato14C.f_costTotalLSupe.val(parseFloat(costoTotalSupeRedondeo));
		},
		//total costo lima para gestor
		totalCostoLimaGest : function(){
			var cantGestDL;
			var costGestDL;
			var cantGestIL;
			var costGestIL;	
			var costoTotalGest;
			var costoTotalGestRedondeo;
			cantGestDL = formato14C.f_canDLGest.val();
			costGestDL = formato14C.f_costDLGest.val();
			cantGestIL = formato14C.f_canILGest.val();
			costGestIL = formato14C.f_costILGest.val();
		
			costoTotalGest = (parseFloat(cantGestDL)*parseFloat(costGestDL)) + (parseFloat(cantGestIL)*parseFloat(costGestIL));				
			costoTotalGestRedondeo = redondeo(costoTotalGest, 2);			
			formato14C.f_costTotalLGest.val(parseFloat(costoTotalGestRedondeo));
		},
		//total costo lima para asistente
		totalCostoLimaAsist : function(){
			var cantAsistDL;
			var costAsistDL;
			var cantAsistIL;
			var costAsistIL;	
			var costoTotalAsist;
			var costoTotalAsistRedondeo;
			cantAsistDL = formato14C.f_canDLAsist.val();
			costAsistDL = formato14C.f_costDLAsist.val();
			cantAsistIL = formato14C.f_canILAsist.val();
			costAsistIL = formato14C.f_costILAsist.val();
		
			costoTotalAsist = (parseFloat(cantAsistDL)*parseFloat(costAsistDL)) + (parseFloat(cantAsistIL)*parseFloat(costAsistIL));				
			costoTotalAsistRedondeo = redondeo(costoTotalAsist, 2);			
			formato14C.f_costTotalLAsist.val(parseFloat(costoTotalAsistRedondeo));
		},
		//total costo para toda la zona lima
		totalCostoLimaGeneral : function(){
			var costTotalCoordL;
			var costTotalSupeL;
			var costTotalGestL;
			var costTotalAsistL;	
			var costoTotalCoordLima;
			var costoTotalCoordLimaRedondeo;
			costTotalCoordL = formato14C.f_costTotalLCoord.val();
			costTotalSupeL = formato14C.f_costTotalLSupe.val();
			costTotalGestL = formato14C.f_costTotalLGest.val();
			costTotalAsistL = formato14C.f_costTotalLAsist.val();
		
			costoTotalCoordLima = parseFloat(costTotalCoordL)+ parseFloat(costTotalSupeL) + parseFloat(costTotalGestL) + parseFloat(costTotalAsistL);				
			costoTotalCoordLimaRedondeo = redondeo(costoTotalCoordLima, 2);			
			formato14C.f_costTotalLGP.val(parseFloat(costoTotalCoordLimaRedondeo));
		},
		/***TOTALES DE CANTIDADES GENERAL DE LAS TRES ZONAS***/
		totalCantidadGeneralCoord : function(){
			var cantTotalCoordDR;
			var cantTotalCoordIR;
			var cantTotalCoordDP;
			var cantTotalCoordIP;	
			var cantTotalCoordDL;
			var cantTotalCoordIL;	
			
			var cantTotalCoordG;
			var cantTotalCoordGRedondeo;			
			cantTotalCoordDR = formato14C.f_canDRCoord.val();
			cantTotalCoordIR = formato14C.f_canIRCoord.val();
			cantTotalCoordDP = formato14C.f_canDPCoord.val();
			cantTotalCoordIP = formato14C.f_canIPCoord.val();
			cantTotalCoordDL = formato14C.f_canDLCoord.val();
			cantTotalCoordIL = formato14C.f_canILCoord.val();
		
			cantTotalCoordG = parseFloat(cantTotalCoordDR)+ parseFloat(cantTotalCoordIR) + parseFloat(cantTotalCoordDP) + parseFloat(cantTotalCoordIP)+ parseFloat(cantTotalCoordDL) + parseFloat(cantTotalCoordIL);				
			cantTotalCoordGRedondeo = redondeo(cantTotalCoordG, 2);			
			formato14C.f_canTotalCoord.val(parseFloat(cantTotalCoordGRedondeo));
		},
		totalCantidadGeneralSupe : function(){
			var cantTotalSupeDR;
			var cantTotalSupeIR;
			var cantTotalSupeDP;
			var cantTotalSupeIP;	
			var cantTotalSupeDL;
			var cantTotalSupeIL;	
			
			var cantTotalSupeG;
			var cantTotalSupeGRedondeo;			
			cantTotalSupeDR = formato14C.f_canDRSupe.val();
			cantTotalSupeIR = formato14C.f_canIRSupe.val();
			cantTotalSupeDP = formato14C.f_canDPSupe.val();
			cantTotalSupeIP = formato14C.f_canIPSupe.val();
			cantTotalSupeDL = formato14C.f_canDLSupe.val();
			cantTotalSupeIL = formato14C.f_canILSupe.val();
		
			cantTotalSupeG = parseFloat(cantTotalSupeDR)+ parseFloat(cantTotalSupeIR) + parseFloat(cantTotalSupeDP) + parseFloat(cantTotalSupeIP)+ parseFloat(cantTotalSupeDL) + parseFloat(cantTotalSupeIL);				
			cantTotalSupeGRedondeo = redondeo(cantTotalSupeG, 2);			
			formato14C.f_canTotalSupe.val(parseFloat(cantTotalSupeGRedondeo));
		},
		totalCantidadGeneralGest : function(){
			var cantTotalGestDR;
			var cantTotalGestIR;
			var cantTotalGestDP;
			var cantTotalGestIP;	
			var cantTotalGestDL;
			var cantTotalGestIL;	
			
			var cantTotalGestG;
			var cantTotalGestGRedondeo;			
			cantTotalGestDR = formato14C.f_canDRGest.val();
			cantTotalGestIR = formato14C.f_canIRGest.val();
			cantTotalGestDP = formato14C.f_canDPGest.val();
			cantTotalGestIP = formato14C.f_canIPGest.val();
			cantTotalGestDL = formato14C.f_canDLGest.val();
			cantTotalGestIL = formato14C.f_canILGest.val();
		
			cantTotalGestG = parseFloat(cantTotalGestDR)+ parseFloat(cantTotalGestIR) + parseFloat(cantTotalGestDP) + parseFloat(cantTotalGestIP)+ parseFloat(cantTotalGestDL) + parseFloat(cantTotalGestIL);				
			cantTotalGestGRedondeo = redondeo(cantTotalGestG, 2);			
			formato14C.f_canTotalGest.val(parseFloat(cantTotalGestGRedondeo));
		},
		totalCantidadGeneralAsist : function(){
			var cantTotalAsistDR;
			var cantTotalAsistIR;
			var cantTotalAsistDP;
			var cantTotalAsistIP;	
			var cantTotalAsistDL;
			var cantTotalAsistIL;	
			
			var cantTotalAsistG;
			var cantTotalAsistGRedondeo;			
			cantTotalAsistDR = formato14C.f_canDRAsist.val();
			cantTotalAsistIR = formato14C.f_canIRAsist.val();
			cantTotalAsistDP = formato14C.f_canDPAsist.val();
			cantTotalAsistIP = formato14C.f_canIPAsist.val();
			cantTotalAsistDL = formato14C.f_canDLAsist.val();
			cantTotalAsistIL = formato14C.f_canILAsist.val();
		
			cantTotalAsistG = parseFloat(cantTotalAsistDR)+ parseFloat(cantTotalAsistIR) + parseFloat(cantTotalAsistDP) + parseFloat(cantTotalAsistIP)+ parseFloat(cantTotalAsistDL) + parseFloat(cantTotalAsistIL);				
			cantTotalAsistGRedondeo = redondeo(cantTotalAsistG, 2);			
			formato14C.f_canTotalAsist.val(parseFloat(cantTotalAsistGRedondeo));
		},
		/***TOTALES DE CANTIDADES GENERAL DE LAS CUATRO TIPO DE PERSONAL***/
		totalCantidadGeneralPersonal : function(){
			var cantTotalCoordP;
			var cantTotalSupeP;
			var cantTotalGestP;
			var cantTotalAsistP;	
						
			var cantTotalP;
			var cantTotalPRedondeo;			
			cantTotalCoordP = formato14C.f_canTotalCoord.val();
			cantTotalSupeP = formato14C.f_canTotalSupe.val();
			cantTotalGestP = formato14C.f_canTotalGest.val();
			cantTotalAsistP = formato14C.f_canTotalAsist.val();
					
			cantTotalP = parseFloat(cantTotalCoordP)+ parseFloat(cantTotalSupeP) + parseFloat(cantTotalGestP) + parseFloat(cantTotalAsistP);				
			cantTotalPRedondeo = redondeo(cantTotalP, 2);			
			formato14C.f_canTotalGP.val(parseFloat(cantTotalPRedondeo));
		},
		
		/***TOTALES DE COSTOS GENERAL DE LAS TRES ZONAS***/
		totalCostoGeneralCoord : function(){
			var costoTotalCoordR;			
			var costoTotalCoordP;
			var costoTotalCoordL;
			
			var costoTotalCoordG;
			var costoTotalCoordGRedondeo;			
			costoTotalCoordR = formato14C.f_costTotalRCoord.val();
			costoTotalCoordP = formato14C.f_costTotalPCoord.val();
			costoTotalCoordL = formato14C.f_costTotalLCoord.val();
				
			costoTotalCoordG = parseFloat(costoTotalCoordR)+ parseFloat(costoTotalCoordP) + parseFloat(costoTotalCoordL);				
			costoTotalCoordGRedondeo = redondeo(costoTotalCoordG, 2);			
			formato14C.f_costTotalCoord.val(parseFloat(costoTotalCoordGRedondeo));
		},
		totalCostoGeneralSupe : function(){
			var costoTotalSupeR;			
			var costoTotalSupeP;
			var costoTotalSupeL;
			
			var costoTotalSupeG;
			var costoTotalSupeGRedondeo;			
			costoTotalSupeR = formato14C.f_costTotalRSupe.val();
			costoTotalSupeP = formato14C.f_costTotalPSupe.val();
			costoTotalSupeL = formato14C.f_costTotalLSupe.val();
				
			costoTotalSupeG = parseFloat(costoTotalSupeR)+ parseFloat(costoTotalSupeP) + parseFloat(costoTotalSupeL);				
			costoTotalSupeGRedondeo = redondeo(costoTotalSupeG, 2);			
			formato14C.f_costTotalSupe.val(parseFloat(costoTotalSupeGRedondeo));
		},
		totalCostoGeneralGest : function(){
			var costoTotalGestR;			
			var costoTotalGestP;
			var costoTotalGestL;
			
			var costoTotalGestG;
			var costoTotalGestGRedondeo;			
			costoTotalGestR = formato14C.f_costTotalRGest.val();
			costoTotalGestP = formato14C.f_costTotalPGest.val();
			costoTotalGestL = formato14C.f_costTotalLGest.val();
				
			costoTotalGestG = parseFloat(costoTotalGestR)+ parseFloat(costoTotalGestP) + parseFloat(costoTotalGestL);				
			costoTotalGestGRedondeo = redondeo(costoTotalGestG, 2);			
			formato14C.f_costTotalGest.val(parseFloat(costoTotalGestGRedondeo));
		},
		totalCostoGeneralAsist : function(){
			var costoTotalAsistR;			
			var costoTotalAsistP;
			var costoTotalAsistL;
			
			var costoTotalAsistG;
			var costoTotalAsistGRedondeo;			
			costoTotalAsistR = formato14C.f_costTotalRAsist.val();
			costoTotalAsistP = formato14C.f_costTotalPAsist.val();
			costoTotalAsistL = formato14C.f_costTotalLAsist.val();
				
			costoTotalAsistG = parseFloat(costoTotalAsistR)+ parseFloat(costoTotalAsistP) + parseFloat(costoTotalAsistL);				
			costoTotalAsistGRedondeo = redondeo(costoTotalAsistG, 2);			
			formato14C.f_costTotalAsist.val(parseFloat(costoTotalAsistGRedondeo));
		},
		/***TOTALES DE COSTO GENERAL DE LAS CUATRO TIPO DE PERSONAL***/
		totalCostoGeneralPersonal : function(){
			var costTotalCoordP;
			var costTotalSupeP;
			var costTotalGestP;
			var costTotalAsistP;	
						
			var costTotalP;
			var costTotalPRedondeo;			
			costTotalCoordP = formato14C.f_costTotalCoord.val();
			costTotalSupeP = formato14C.f_costTotalSupe.val();
			costTotalGestP = formato14C.f_costTotalGest.val();
			costTotalAsistP = formato14C.f_costTotalAsist.val();
					
			costTotalP = parseFloat(costTotalCoordP)+ parseFloat(costTotalSupeP) + parseFloat(costTotalGestP) + parseFloat(costTotalAsistP);				
			costTotalPRedondeo = redondeo(costTotalP, 2);			
			formato14C.f_costTotalGP.val(parseFloat(costTotalPRedondeo));
		},		
		
		/**FUNCION PARA CALCULAR LA CANTIDAD TOTAL DE LA CABECERA*/
		totalCantidadGeneralBenefiarios : function(){
			var cantidadTotalR;			
			var cantidadTotalP;
			var cantidadTotalL;
			
			var cantidadTotalG;
			var cantidadTotalGRedondeo;			
			cantidadTotalR = formato14C.f_numRural.val();
			cantidadTotalP = formato14C.f_numUrbProv.val();
			cantidadTotalL = formato14C.f_numUrbLima.val();
				
			cantidadTotalG = parseFloat(cantidadTotalR)+ parseFloat(cantidadTotalP) + parseFloat(cantidadTotalL);				
			cantidadTotalGRedondeo = redondeo(cantidadTotalG, 2);			
			formato14C.f_numTotal.val(parseFloat(cantidadTotalGRedondeo));
		},
		/**FUNCION PARA CALCULAR EL COSTO PROMEDIO DE GESTION DE PERSONAL GENERAL*/
		totalCostoPromedioGeneral : function(){
			var costoTotalR;			
			var costoTotalP;
			var costoTotalL;
			
			var costoTotalG;
			var costoTotalGRedondeo;			
			costoTotalR = formato14C.f_costoPromRural.val();
			costoTotalP = formato14C.f_costoPromUrbProv.val();
			costoTotalL = formato14C.f_costoPromUrbLima.val();
				
			costoTotalG = parseFloat(costoTotalR)+ parseFloat(costoTotalP) + parseFloat(costoTotalL);				
			costoTotalGRedondeo = redondeo(costoTotalG, 2);			
			formato14C.f_costTotalPromedio.val(parseFloat(costoTotalGRedondeo));
		},	
		//function para completar decimales
		formularioCompletarDecimales : function(){
			/**cabecera*/
			completarNumeroBlanco('numRural');
			completarNumeroBlanco('numUrbProv');
			completarNumeroBlanco('numUrbLima');
			completarDecimal('costoPromRural',2);
			completarDecimal('costoPromUrbProv',2);
			completarDecimal('costoPromUrbLima',2);
			/**detalle*/
			//RURAL
			completarNumeroBlanco('canDRCoord');
			completarNumeroBlanco('canIRCoord');			
			completarDecimal('costDRCoord',2);
			completarDecimal('costIRCoord',2);
			
			completarDecimal('costTotalRCoord',2);
			
			completarNumeroBlanco('canDRSupe');
			completarNumeroBlanco('canIRSupe');
			completarDecimal('costDRSupe',2);
			completarDecimal('costIRSupe',2);
			
			completarDecimal('costTotalRSupe',2);
			
			completarNumeroBlanco('canDRGest');
			completarNumeroBlanco('canIRGest');
			completarDecimal('costDRGest',2);
			completarDecimal('costIRGest',2);
			
			completarDecimal('costTotalRGest',2);
			
			completarNumeroBlanco('canDRAsist');
			completarNumeroBlanco('canIRAsist');
			completarDecimal('costDRAsist',2);
			completarDecimal('costIRAsist',2);	
			
			completarDecimal('costTotalRAsist',2);
			
			//PROVINCIA
			completarNumeroBlanco('canDPCoord');
			completarNumeroBlanco('canIPCoord');	
			completarDecimal('costDPCoord',2);
			completarDecimal('costIPCoord',2);
			
			completarDecimal('costTotalPCoord',2);
			
			completarNumeroBlanco('canDPSupe');
			completarNumeroBlanco('canIPSupe');
			completarDecimal('costDPSupe',2);
			completarDecimal('costIPSupe',2);
			
			completarDecimal('costTotalPSupe',2);
			
			completarNumeroBlanco('canDPGest');
			completarNumeroBlanco('canIPGest');
			completarDecimal('costDPGest',2);
			completarDecimal('costIPGest',2);
			
			completarDecimal('costTotalPGest',2);
			
			completarNumeroBlanco('canDPAsist');
			completarNumeroBlanco('canIPAsist');
			completarDecimal('costDPAsist',2);
			completarDecimal('costIPAsist',2);
			
			completarDecimal('costTotalPAsist',2);
			
			//LIMA
			completarNumeroBlanco('canDLCoord');
			completarNumeroBlanco('canILCoord');	
			completarDecimal('costDLCoord',2);
			completarDecimal('costILCoord',2);
			
			completarDecimal('costTotalLCoord',2);
			
			completarNumeroBlanco('canDLSupe');
			completarNumeroBlanco('canILSupe');
			completarDecimal('costDLSupe',2);
			completarDecimal('costILSupe',2);
			
			completarDecimal('costTotalLSupe',2);
			
			completarNumeroBlanco('canDLGest');
			completarNumeroBlanco('canILGest');
			completarDecimal('costDLGest',2);
			completarDecimal('costILGest',2);
			
			completarDecimal('costTotalLGest',2);
			
			completarNumeroBlanco('canDLAsist');
			completarNumeroBlanco('canILAsist');
			completarDecimal('costDLAsist',2);
			completarDecimal('costILAsist',2);
			
			completarDecimal('costTotalLAsist',2);
			
			//para costo promedio
			completarDecimal('costDRGP',2);
			completarDecimal('costIRGP',2);
			completarDecimal('costTotalRGP',2);
			
			completarDecimal('costDPGP',2);
			completarDecimal('costIPGP',2);
			completarDecimal('costTotalPGP',2);
			
			completarDecimal('costDLGP',2);
			completarDecimal('costILGP',2);
			completarDecimal('costTotalLGP',2);
			
			completarDecimal('costTotalGP',2);			
			
			//totales
			completarDecimal('costTotalCoord',2);
			completarDecimal('costTotalSupe',2);
			completarDecimal('costTotalGest',2);
			completarDecimal('costTotalAsist',2);
			
			//
			completarDecimal('costTotalPromedio',2);
		},
		//Funcion para Grabar nuevo registro
		<portlet:namespace/>guardarFormato14C : function(){
			console.debug("entranado a grabar formato 14C ");
			if (formato14C.validarFormulario()){
				$.blockUI({ message: formato14C.mensajeGuardando });
				 jQuery.ajax({
					 url: formato14C.urlGrabar+'&'+formato14C.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {
						//<portlet:namespace />tipo: formato14C.procesoEstado.val(),						
						<portlet:namespace />flagPeriodoEjecucion: formato14C.f_flagPeriodo.val(),
						<portlet:namespace />anoIniVigencia: $('#anoIniVigencia').val(),
						<portlet:namespace />anoFinVigencia: $('#anoFinVigencia').val(),
						<portlet:namespace />flagCosto: formato14C.flagCosto.val()						
						},
					success: function(data) {			
						if (data.resultado == "OK"){				
							var addhtml2='El Formato 14C se guardó satisfactoriamente.';
							formato14C.dialogMessageContent.html(addhtml2);
							formato14C.dialogMessage.dialog("open");							
							formato14C.initBlockUI();							
							$('#<portlet:namespace/>guardarFormatoF14C').css('display','none');
							$('#<portlet:namespace/>actualizarFormatoF14C').css('display','block');
							
							$('#<portlet:namespace/>validacionFormatoF14C').css('display','block');		
							$('#<portlet:namespace/>envioDefinitivoF14C').css('display','block');
							
						}else if(data.resultado == "Duplicado"){				
							var addhtml2='El Formato ya existe para la Distribuidora Eléctrica y Periodo a declarar seleccionado';
							formato14C.dialogInfoContent.html(addhtml2);
							formato14C.dialogInfo.dialog("open");						
							formato14C.initBlockUI();
						}else if(data.resultado == "Error"){							
							var addhtmError='Se produjo un error al guardar el Formato 14C.';					
							formato14C.dialogErrorContentF14C.html(addhtmError);
							formato14C.dialogErrorF14C.dialog("open");					
							formato14C.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						formato14C.dialogErrorContentF14C.html(addhtmError);
						formato14C.dialogErrorF14C.dialog("open");
						formato14C.initBlockUI();
					}
				});			
			}
		},
		//Funcion para actualizar un registro
		<portlet:namespace/>actualizarFormato14C : function(){
			if (formato14C.validarFormulario()){
				$.blockUI({ message: formato14C.mensajeActualizando });
				 jQuery.ajax({
					 url: formato14C.urlActualizar+'&'+formato14C.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {
						<portlet:namespace />codEmpresa: formato14C.f_empresa.val(),
						<portlet:namespace />periodoEnvio: formato14C.f_periodoEnvio.val(),
						<portlet:namespace />flagPeriodoEjecucion: formato14C.f_flagPeriodo.val(),
						<portlet:namespace />anoIniVigencia: $('#anoIniVigencia').val(),
						<portlet:namespace />anoFinVigencia: $('#anoFinVigencia').val(),
						<portlet:namespace />flagCosto: formato14C.flagCosto.val()	
						},
					success: function(data) {			
						if (data.resultado == "OK"){				
							var addhtml2='El Formato 14C se actualizó satisfactoriamente.';
							formato14C.dialogMessageContent.html(addhtml2);
							formato14C.dialogMessage.dialog("open");						
							formato14C.initBlockUI();
							//regreso a la busqueda
							//formato14C.divNuevo.hide();
							//formato14C.divBuscar.show();		
						}else if(data.resultado == "Error"){						
							var addhtmError='Se produjo un error al actualizar el Formato 14C.';					
							formato14C.dialogErrorContentF14C.html(addhtmError);
							formato14C.dialogErrorF14C.dialog("open");							
							formato14C.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						formato14C.dialogErrorContentF14C.html(addhtmError);
						formato14C.dialogErrorF14C.dialog("open");
						formato14C.initBlockUI();
					}
				});						
			}
		},	
		//function para validad ingreso de datos
		validarFormulario : function() {			
			var flagPeriodo = formato14C.f_flagPeriodo.val();
			var anioInicio =$('#anoIniVigencia').val();
			var anioFin = $('#anoFinVigencia').val();
			console.debug("flag periodo al validar:  "+flagPeriodo);
			if(formato14C.f_empresa.val().length == '' ) { 					
				var addhtmAlert='Seleccione una Distribuidora Eléctrica.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_empresa.focus();
				cod_focus=formato14C.f_empresa;
			  	return false; 
			}else if(formato14C.f_periodoEnvio.val()==null || 
					formato14C.f_periodoEnvio.val().length == '' ) {			
				var addhtmAlert='No existe un Periodo Declarado para la Distribuidora Eléctrica seleccionada.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_periodoEnvio.focus();
				cod_focus=formato14C.f_periodoEnvio;
				return false; 
			}else if(flagPeriodo=='S' && anioInicio.length == ''){			
				var addhtmAlert='Debe ingresar el Año de Inicio de Vigencia.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//$('#anoIniVigencia').focus();
				cod_focus=$('#anoIniVigencia');
				return false; 				
			}else if(flagPeriodo=='S' && anioFin.length == ''){				
				var addhtmAlert='Debe ingresar el Año de Fin de Vigencia.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//$('#anoFinVigencia').focus();
				cod_focus=$('#anoFinVigencia');
				return false; 		
		    }else if(flagPeriodo=='S' && !validarAnioInicioVig(anioInicio)){			
				var addhtmAlert='Debe ingresar el Año de Inicio de Vigencia válido.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//anioInicio.focus();
				cod_focus=anioInicio;
				return false; 				
			}else if(flagPeriodo=='S' && !validarAnioFinVig(anioFin)){					
				var addhtmAlert='Debe ingresar el Año de Fin de Vigencia válido.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//anioFin.focus();
				cod_focus=anioFin;
				return false; 		
		    }else if(formato14C.f_nombreSede.val().length != '' && !validarLetra(formato14C.f_nombreSede.val())) {				
				var addhtmAlert='Debe ingresar Nombre de la Sede válido.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_numRural.focus();
				cod_focus=formato14C.f_nombreSede;
				return false; 
			}else if(formato14C.f_numRural.val().length == '' ) {				
				var addhtmAlert='Debe ingresar el Número de Beneficiarios de la Zona Rural.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_numRural.focus();
				cod_focus=formato14C.f_numRural;
				return false; 
			}else if(formato14C.f_numUrbProv.val().length == '' ) {					
				var addhtmAlert='Debe ingresar el Número de Beneficiarios de la Zona Urbano Provincias.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_numUrbProv.focus();
				cod_focus=formato14C.f_numUrbProv;
				return false; 
			}else if(formato14C.f_numUrbLima.val().length == '' ) {					
				var addhtmAlert='Debe ingresar el Número de Beneficiarios de la Zona Urbano Lima.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_numUrbLima.focus();
				cod_focus=formato14C.f_numUrbLima;
				return false; 
			}else if(formato14C.f_costoPromRural.val().length == '' ) {					
				var addhtmAlert='Debe ingresar el Costo Promedio de la Zona Rural.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_costoPromRural.focus();
				cod_focus=formato14C.f_costoPromRural;
				return false; 
			}else if(formato14C.f_costoPromUrbProv.val().length == '' ) {				
				var addhtmAlert='Debe ingresar el Costo Promedio de la Zona Urbano Provincias.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_costoPromUrbProv.focus();
				cod_focus=formato14C.f_costoPromUrbProv;
				return false; 
			}else if(formato14C.f_costoPromUrbLima.val().length == '' ) {				
				var addhtmAlert='Debe ingresar el costo promedio de la Zona Urbano Lima.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_costoPromUrbLima.focus();
				cod_focus=formato14C.f_costoPromUrbLima;
				return false; 
			}
			/*Detalle*/		
			/*RURAL*/
			else if(formato14C.f_canDRCoord.val().length == '' ) {					
				var addhtmAlert='Debe ingresar cantidad Costo Directo Coordinador de la Zona Rural.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_canDRCoord.focus();
				cod_focus=formato14C.f_canDRCoord;
				return false; 
			}else if(formato14C.f_costDRCoord.val().length == '' ) {				
				var addhtmAlert='Debe ingresar Costo Directo Coordinador de la Zona Rural.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_costDRCoord.focus();
				cod_focus=formato14C.f_costDRCoord;
				return false; 
			}else if(formato14C.f_canIRCoord.val().length == '' ) {		 			
				var addhtmAlert='Debe ingresar cantidad Costo Indirecto Coordinador de la Zona Rural.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_canIRCoord.focus();
				cod_focus=formato14C.f_canIRCoord;
				return false; 
			}else if(formato14C.f_costIRCoord.val().length == '' ) {				
				var addhtmAlert='Debe ingresar Costo Indirecto Coordinador de la Zona Rural.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_costIRCoord.focus();
				cod_focus=formato14C.f_costIRCoord;
				return false; 
			}else if(formato14C.f_canDRSupe.val().length == '' ) {				
				var addhtmAlert='Debe ingresar cantidad Costo Directo Supervisor de la Zona Rural.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_canDRSupe.focus();
				cod_focus=formato14C.f_canDRSupe;
				return false; 
			}else if(formato14C.f_costDRSupe.val().length == '' ) {				
				var addhtmAlert='Debe ingresar Costo Directo Supervisor de la Zona Rural.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_costDRSupe.focus();
				cod_focus=formato14C.f_costDRSupe;
				return false; 
			}else if(formato14C.f_canIRSupe.val().length == '' ) {					
				var addhtmAlert='Debe ingresar cantidad Costo Indirecto Supervisor de la Zona Rural.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_canIRSupe.focus();
				cod_focus=formato14C.f_canIRSupe;
				return false; 
			}else if(formato14C.f_costIRSupe.val().length == '' ) {					
				var addhtmAlert='Debe ingresar Costo Indirecto Supervisor de la Zona Rural.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_costIRSupe.focus();
				cod_focus=formato14C.f_costIRSupe;
				return false; 
			}else if(formato14C.f_canDRGest.val().length == '' ) {					
				var addhtmAlert='Debe ingresar cantidad Costo Directo Gestor de la Zona Rural.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_canDRGest.focus();
				cod_focus=formato14C.f_canDRGest;
				return false; 
			}else if(formato14C.f_costDRGest.val().length == '' ) {		  			
				var addhtmAlert='Debe ingresar Costo Directo Gestor de la Zona Rural.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_costDRGest.focus();
				cod_focus=formato14C.f_costDRGest;
				return false; 
			}else if(formato14C.f_canIRGest.val().length == '' ) {					
				var addhtmAlert='Debe ingresar cantidad Costo Indirecto Gestor de la Zona Rural.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_canIRGest.focus();
				cod_focus=formato14C.f_canIRGest;
				return false; 
			}else if(formato14C.f_costIRGest.val().length == '' ) {						
				var addhtmAlert='Debe ingresar Costo Indirecto Gestor de la Zona Rural.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_costIRGest.focus();
				cod_focus=formato14C.f_costIRGest;
				return false; 
			}else if(formato14C.f_canDRAsist.val().length == '' ) {					
				var addhtmAlert='Debe ingresar cantidad Costo Directo Asistente de la Zona Rural.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_canDRAsist.focus();
				cod_focus=formato14C.f_canDRAsist;
				return false; 
			}else if(formato14C.f_costDRAsist.val().length == '' ) {				
				var addhtmAlert='Debe ingresar  Costo Directo Asistente de la Zona Rural.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_costDRAsist.focus();
				cod_focus=formato14C.f_costDRAsist;
				return false; 
			}else if(formato14C.f_canIRAsist.val().length == '' ) {				
				var addhtmAlert='Debe ingresar cantidad Costo Indirecto Asistente de la Zona Rural.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_canIRAsist.focus();
				cod_focus=formato14C.f_canIRAsist;
				return false; 
			}else if(formato14C.f_costIRAsist.val().length == '' ) {				
				var addhtmAlert='Debe ingresar  Costo Indirecto Asistente de la Zona Rural.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_costIRAsist.focus();
				cod_focus=formato14C.f_costIRAsist;
				return false; 
			}				
			/*PROVINCIAS*/
			else if(formato14C.f_canDPCoord.val().length == '' ) {					
				var addhtmAlert='Debe ingresar  cantidad Costo Directo Coordinador de la Zona Urbano Provincias.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_canDPCoord.focus();
				cod_focus=formato14C.f_canDPCoord;
				return false; 
			}else if(formato14C.f_costDPCoord.val().length == '' ) {			
				var addhtmAlert='Debe ingresar  Costo Directo Coordinador de la Zona Urbano Provincias.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_costDPCoord.focus();
				cod_focus=formato14C.f_costDPCoord;
				return false; 
			}else if(formato14C.f_canIPCoord.val().length == '' ) {				
				var addhtmAlert='Debe ingresar  cantidad Costo Indirecto Coordinador de la Zona Urbano Provincias.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_canIPCoord.focus();
				cod_focus=formato14C.f_canIPCoord;
				return false; 
			}else if(formato14C.f_costIPCoord.val().length == '' ) {					
				var addhtmAlert='Debe ingresar  Costo Indirecto Coordinador de la Zona Urbano Provincias.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_costIPCoord.focus();
				cod_focus=formato14C.f_costIPCoord;
				return false; 
			}else if(formato14C.f_canDPSupe.val().length == '' ) {					
				var addhtmAlert='Debe ingresar cantidad Costo Directo Supervisor de la Zona Urbano Provincias.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_canDPSupe.focus();
				cod_focus=formato14C.f_canDPSupe;
				return false; 
			}else if(formato14C.f_costDPSupe.val().length == '' ) {			
				var addhtmAlert='Debe ingresar  Costo Indirecto Coordinador de la Zona Urbano Provincias.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_costDPSupe.focus();
				cod_focus=formato14C.f_costDPSupe;
				return false; 
			}else if(formato14C.f_canIPSupe.val().length == '' ) {				
				var addhtmAlert='Debe ingresar cantidad Costo Indirecto Supervisor de la Zona Urbano Provincias.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_canIPSupe.focus();
				cod_focus=formato14C.f_canIPSupe;
				return false; 
			}else if(formato14C.f_costIPSupe.val().length == '' ) {					
				var addhtmAlert='Debe ingresar cantidad Costo Indirecto Coordinador de la Zona Urbano Provincias.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_costIPSupe.focus();
				cod_focus=formato14C.f_costIPSupe;
				return false; 
			}else if(formato14C.f_canDPGest.val().length == '' ) {					
				var addhtmAlert='Debe ingresar cantidad Costo Directo Gestor de la Zona Urbano Provincias.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_canDPGest.focus();
				cod_focus=formato14C.f_canDPGest;
				return false; 
			}else if(formato14C.f_costDPGest.val().length == '' ) {					
				var addhtmAlert='Debe ingresar  Costo Directo Gestor de la Zona Urbano Provincias.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_costDPGest.focus();
				cod_focus=formato14C.f_costDPGest;
				return false; 
			}else if(formato14C.f_canIPGest.val().length == '' ) {				
				var addhtmAlert='Debe ingresar cantidad Costo Indirecto Gestor de la Zona Urbano Provincias.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_canIPGest.focus();
				cod_focus=formato14C.f_canIPGest;
				return false; 
			}else if(formato14C.f_costIPGest.val().length == '' ) {				
				var addhtmAlert='Debe ingresar  Costo Indirecto Gestor de la Zona Urbano Provincias.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_costIPGest.focus();
				cod_focus=formato14C.f_costIPGest;
				return false; 
			}else if(formato14C.f_canDPAsist.val().length == '' ) {		  			
				var addhtmAlert='Debe ingresar cantiad Costo Directo Asistente de la Zona Urbano Provincias.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_canDPAsist.focus();
				cod_focus=formato14C.f_canDPAsist;
				return false; 
			}else if(formato14C.f_costDPAsist.val().length == '' ) {					
				var addhtmAlert='Debe ingresar  Costo Indirecto Gestor de la Zona Urbano Provincias.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_costDPAsist.focus();
				cod_focus=formato14C.f_costDPAsist;
				return false; 
			}else if(formato14C.f_canIPAsist.val().length == '' ) {					
				var addhtmAlert='Debe ingresar cantidad Costo Indirecto Asistente de la Zona Urbano Provincias.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_canIPAsist.focus();
				cod_focus=formato14C.f_canIPAsist;
				return false; 
			}else if(formato14C.f_costIPAsist.val().length == '' ) {				
				var addhtmAlert='Debe ingresar  Costo Indirecto Asistente de la Zona Urbano Provincias.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_costIPAsist.focus();
				cod_focus=formato14C.f_costIPAsist;
				return false; 
			}			
			/*LIMA*/
			else if(formato14C.f_canDLCoord.val().length == '' ) {					
				var addhtmAlert='Debe ingresar cantidad Costo Directo Coordinador de la Zona Urbano Lima.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_canDLCoord.focus();
				cod_focus=formato14C.f_canDLCoord;
				return false; 
			}else if(formato14C.f_costDLCoord.val().length == '' ) {				
				var addhtmAlert='Debe ingresar Costo Directo Coordinador de la Zona Urbano Lima.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_costDLCoord.focus();
				cod_focus=formato14C.f_costDLCoord;
				return false; 
			}else if(formato14C.f_canILCoord.val().length == '' ) {					
				var addhtmAlert='Debe ingresar cantidad Costo Indirecto Coordinador de la Zona Urbano Lima.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_canILCoord.focus();
				cod_focus=formato14C.f_canILCoord;
				return false; 
			}else if(formato14C.f_costILCoord.val().length == '' ) {				
				var addhtmAlert='Debe ingresar  Costo Indirecto Coordinador de la Zona Urbano Lima.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_costILCoord.focus();
				cod_focus=formato14C.f_costILCoord;
				return false; 
			}else if(formato14C.f_canDLSupe.val().length == '' ) {				
				var addhtmAlert='Debe ingresar cantidad Costo Indirecto Coordinador de la Zona Urbano Lima.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_canDLSupe.focus();
				cod_focus=formato14C.f_canDLSupe;
				return false; 
			}else if(formato14C.f_costDLSupe.val().length == '' ) {						
				var addhtmAlert='Debe ingresar  Costo Indirecto Coordinador de la Zona Urbano Lima.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_costDLSupe.focus();
				cod_focus=formato14C.f_costDLSupe;
				return false; 
			}else if(formato14C.f_canILSupe.val().length == '' ) {				
				var addhtmAlert='Debe ingresar cantidad Costo Indirecto Coordinador de la Zona Urbano Lima.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_canILSupe.focus();
				cod_focus=formato14C.f_canILSupe;
				return false; 
			}else if(formato14C.f_costILSupe.val().length == '' ) {					
				var addhtmAlert='Debe ingresar  Costo Indirecto Coordinador de la Zona Urbano Lima.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_costILSupe.focus();
				cod_focus=formato14C.f_costILSupe;
				return false; 
			}else if(formato14C.f_canDLGest.val().length == '' ) {					
				var addhtmAlert='Debe ingresar cantidad Costo Directo Gestor de la Zona Urbano Lima.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_canDLGest.focus();
				cod_focus=formato14C.f_canDLGest;
				return false; 
			}else if(formato14C.f_costDLGest.val().length == '' ) {					
				var addhtmAlert='Debe ingresar  Costo Directo Gestor de la Zona Urbano Lima.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_costDLGest.focus();
				cod_focus=formato14C.f_costDLGest;
				return false; 
			}else if(formato14C.f_canILGest.val().length == '' ) {					
				var addhtmAlert='Debe ingresar cantidad Costo Indirecto Gestor de la Zona Urbano Lima.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_canILGest.focus();
				cod_focus=formato14C.f_canILGest;
				return false; 
			}else if(formato14C.f_costILGest.val().length == '' ) {				
				var addhtmAlert='Debe ingresar  Costo Indirecto Gestor de la Zona Urbano Lima.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_costILGest.focus();
				cod_focus=formato14C.f_costILGest;
				return false; 
			}else if(formato14C.f_canDLAsist.val().length == '' ) {		  
				var addhtmAlert='Debe ingresar cantidad Costo Indirecto Gestor de la Zona Urbano Lima.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_canDLAsist.focus();
				cod_focus=formato14C.f_canDLAsist;
				return false; 
			}else if(formato14C.f_costDLAsist.val().length == '' ) {		  
				var addhtmAlert='Debe ingresar Costo Indirecto Gestor de la Zona Urbano Lima.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_costDLAsist.focus();
				cod_focus=formato14C.f_costDLAsist;
				return false; 
			}else if(formato14C.f_canILAsist.val().length == '' ) {			
				var addhtmAlert='Debe ingresar cantidad Costo Indirecto Asistente de la Zona Urbano Lima';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_canILAsist.focus();
				cod_focus=formato14C.f_canILAsist;
				return false; 
			}else if(formato14C.f_costILAsist.val().length == '' ) {			
				var addhtmAlert='DDebe ingresar  Costo Indirecto Asistente de la Zona Urbano Lima.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_costILAsist.focus();
				cod_focus=formato14C.f_costILAsist;
				return false; 
			}else{
				return true; 	
			}			
		},		
		//funcion para regresar
		<portlet:namespace/>regresarF14C : function(){			
			formato14C.divNuevo.hide();
			formato14C.divBuscar.show();	
			
			formato14C.habilitarCamposView();
				
			formato14C.botonBuscar.trigger('click');
		},
		//Funcion para deshabilitar los campos  en la visualizacion opcion ver
		deshabilitarCamposView : function(){
			//cabecera
			$('#codEmpresa').attr("disabled",true);
			$('#periodoEnvio').attr("disabled",true);
			$('#anoIniVigencia').attr("disabled",true);			
			$('#anoFinVigencia').attr("disabled",true);
			///ESTILOS
			$('#anoIniVigencia').removeClass("fise-editable");
			$('#anoFinVigencia').removeClass("fise-editable");
			
			formato14C.f_nombreSede.attr("disabled",true);
			formato14C.f_numRural.attr("disabled",true);
			formato14C.f_numUrbProv.attr("disabled",true);
			formato14C.f_numUrbLima.attr("disabled",true);
			formato14C.f_costoPromRural.attr("disabled",true);			
			formato14C.f_costoPromUrbProv.attr("disabled",true);
			formato14C.f_costoPromUrbLima.attr("disabled",true);
			//ESTILOS
			formato14C.f_nombreSede.removeClass("fise-editable");
			formato14C.f_numRural.removeClass("fise-editable");
			formato14C.f_numUrbProv.removeClass("fise-editable");
			formato14C.f_numUrbLima.removeClass("fise-editable");
			formato14C.f_costoPromRural.removeClass("fise-editable");			
			formato14C.f_costoPromUrbProv.removeClass("fise-editable");
			formato14C.f_costoPromUrbLima.removeClass("fise-editable");
			
			
			//RURAL
			formato14C.f_canDRCoord.attr("disabled",true);
			formato14C.f_costDRCoord.attr("disabled",true);
			formato14C.f_canIRCoord.attr("disabled",true);
			formato14C.f_costIRCoord.attr("disabled",true);
			
			formato14C.f_canDRSupe.attr("disabled",true);
			formato14C.f_costDRSupe.attr("disabled",true);
			formato14C.f_canIRSupe.attr("disabled",true);
			formato14C.f_costIRSupe.attr("disabled",true);
			
			formato14C.f_canDRGest.attr("disabled",true);
			formato14C.f_costDRGest.attr("disabled",true);
			formato14C.f_canIRGest.attr("disabled",true);
			formato14C.f_costIRGest.attr("disabled",true);
			
			formato14C.f_canDRAsist.attr("disabled",true);
			formato14C.f_costDRAsist.attr("disabled",true);
			formato14C.f_canIRAsist.attr("disabled",true);
			formato14C.f_costIRAsist.attr("disabled",true);		
			
			//PROVINCIA
			formato14C.f_canDPCoord.attr("disabled",true);
			formato14C.f_costDPCoord.attr("disabled",true);
			formato14C.f_canIPCoord.attr("disabled",true);
			formato14C.f_costIPCoord.attr("disabled",true);
			
			formato14C.f_canDPSupe.attr("disabled",true);
			formato14C.f_costDPSupe.attr("disabled",true);
			formato14C.f_canIPSupe.attr("disabled",true);
			formato14C.f_costIPSupe.attr("disabled",true);
			
			formato14C.f_canDPGest.attr("disabled",true);
			formato14C.f_costDPGest.attr("disabled",true);
			formato14C.f_canIPGest.attr("disabled",true);
			formato14C.f_costIPGest.attr("disabled",true);
			
			formato14C.f_canDPAsist.attr("disabled",true);
			formato14C.f_costDPAsist.attr("disabled",true);
			formato14C.f_canIPAsist.attr("disabled",true);
			formato14C.f_costIPAsist.attr("disabled",true);	
			
			//LIMA
			formato14C.f_canDLCoord.attr("disabled",true);
			formato14C.f_costDLCoord.attr("disabled",true);
			formato14C.f_canILCoord.attr("disabled",true);
			formato14C.f_costILCoord.attr("disabled",true);
			
			formato14C.f_canDLSupe.attr("disabled",true);
			formato14C.f_costDLSupe.attr("disabled",true);
			formato14C.f_canILSupe.attr("disabled",true);
			formato14C.f_costILSupe.attr("disabled",true);
			
			formato14C.f_canDLGest.attr("disabled",true);
			formato14C.f_costDLGest.attr("disabled",true);
			formato14C.f_canILGest.attr("disabled",true);
			formato14C.f_costILGest.attr("disabled",true);
			
			formato14C.f_canDLAsist.attr("disabled",true);
			formato14C.f_costDLAsist.attr("disabled",true);
			formato14C.f_canILAsist.attr("disabled",true);
			formato14C.f_costILAsist.attr("disabled",true);	
			
			//ESTILOS
			formato14C.f_canDRCoord.removeClass("fise-editable");
			formato14C.f_costDRCoord.removeClass("fise-editable");
			formato14C.f_canIRCoord.removeClass("fise-editable");
			formato14C.f_costIRCoord.removeClass("fise-editable");
			
			formato14C.f_canDRSupe.removeClass("fise-editable");
			formato14C.f_costDRSupe.removeClass("fise-editable");
			formato14C.f_canIRSupe.removeClass("fise-editable");
			formato14C.f_costIRSupe.removeClass("fise-editable");
			
			formato14C.f_canDRGest.removeClass("fise-editable");
			formato14C.f_costDRGest.removeClass("fise-editable");
			formato14C.f_canIRGest.removeClass("fise-editable");
			formato14C.f_costIRGest.removeClass("fise-editable");
			
			formato14C.f_canDRAsist.removeClass("fise-editable");
			formato14C.f_costDRAsist.removeClass("fise-editable");
			formato14C.f_canIRAsist.removeClass("fise-editable");
			formato14C.f_costIRAsist.removeClass("fise-editable");		
			
			//PROVINCIA
			formato14C.f_canDPCoord.removeClass("fise-editable");
			formato14C.f_costDPCoord.removeClass("fise-editable");
			formato14C.f_canIPCoord.removeClass("fise-editable");
			formato14C.f_costIPCoord.removeClass("fise-editable");
			
			formato14C.f_canDPSupe.removeClass("fise-editable");
			formato14C.f_costDPSupe.removeClass("fise-editable");
			formato14C.f_canIPSupe.removeClass("fise-editable");
			formato14C.f_costIPSupe.removeClass("fise-editable");
			
			formato14C.f_canDPGest.removeClass("fise-editable");
			formato14C.f_costDPGest.removeClass("fise-editable");
			formato14C.f_canIPGest.removeClass("fise-editable");
			formato14C.f_costIPGest.removeClass("fise-editable");
			
			formato14C.f_canDPAsist.removeClass("fise-editable");
			formato14C.f_costDPAsist.removeClass("fise-editable");
			formato14C.f_canIPAsist.removeClass("fise-editable");
			formato14C.f_costIPAsist.removeClass("fise-editable");	
			
			//LIMA
			formato14C.f_canDLCoord.removeClass("fise-editable");
			formato14C.f_costDLCoord.removeClass("fise-editable");
			formato14C.f_canILCoord.removeClass("fise-editable");
			formato14C.f_costILCoord.removeClass("fise-editable");
			
			formato14C.f_canDLSupe.removeClass("fise-editable");
			formato14C.f_costDLSupe.removeClass("fise-editable");
			formato14C.f_canILSupe.removeClass("fise-editable");
			formato14C.f_costILSupe.removeClass("fise-editable");
			
			formato14C.f_canDLGest.removeClass("fise-editable");
			formato14C.f_costDLGest.removeClass("fise-editable");
			formato14C.f_canILGest.removeClass("fise-editable");
			formato14C.f_costILGest.removeClass("fise-editable");
			
			formato14C.f_canDLAsist.removeClass("fise-editable");
			formato14C.f_costDLAsist.removeClass("fise-editable");
			formato14C.f_canILAsist.removeClass("fise-editable");
			formato14C.f_costILAsist.removeClass("fise-editable");	
			
			
			//botones
			formato14C.botonReportePdf.css('display','');
			formato14C.botonReporteExcel.css('display','');
			
			formato14C.botonActaEnvio.css('display','');
			
			formato14C.botonGrabar.css('display','none');
			formato14C.botonValidacion.css('display','none');
			formato14C.botonEnvioDefinitivo.css('display','none');
			formato14C.botonActualizar.css('display','none');
			//panel de carga de exel y text
			formato14C.panelCargaArchivo.css('display','none');
		},
		//Funcion para habilitar los campos que se desabilitan en la visualizacion opcion ver
		habilitarCamposView : function(){
			//cabecera
			$('#codEmpresa').removeAttr("disabled");
			$('#periodoEnvio').removeAttr("disabled");
			$('#anoIniVigencia').removeAttr("disabled");
			$('#anoFinVigencia').removeAttr("disabled");
			//ESTILOS
			$('#anoIniVigencia').addClass("fise-editable");
			$('#anoFinVigencia').addClass("fise-editable");
			
			/*if( formato14C.f_flagPeriodo.val()=='S' ){
				$('#anoIniVigencia').removeAttr("disabled");
				$('#anoFinVigencia').removeAttr("disabled");
			}*/
			formato14C.f_nombreSede.removeAttr("disabled");
			formato14C.f_numRural.removeAttr("disabled");
			formato14C.f_numUrbProv.removeAttr("disabled");
			formato14C.f_numUrbLima.removeAttr("disabled");
			formato14C.f_costoPromRural.removeAttr("disabled");		
			formato14C.f_costoPromUrbProv.removeAttr("disabled");
			formato14C.f_costoPromUrbLima.removeAttr("disabled");
			
			//ESTILOS
			formato14C.f_nombreSede.addClass("fise-editable");
			formato14C.f_numRural.addClass("fise-editable");
			formato14C.f_numUrbProv.addClass("fise-editable");
			formato14C.f_numUrbLima.addClass("fise-editable");
			formato14C.f_costoPromRural.addClass("fise-editable");		
			formato14C.f_costoPromUrbProv.addClass("fise-editable");
			formato14C.f_costoPromUrbLima.addClass("fise-editable");
			
			//RURAL
			formato14C.f_canDRCoord.removeAttr("disabled");
			formato14C.f_costDRCoord.removeAttr("disabled");
			formato14C.f_canIRCoord.removeAttr("disabled");
			formato14C.f_costIRCoord.removeAttr("disabled");
			
			formato14C.f_canDRSupe.removeAttr("disabled");
			formato14C.f_costDRSupe.removeAttr("disabled");
			formato14C.f_canIRSupe.removeAttr("disabled");
			formato14C.f_costIRSupe.removeAttr("disabled");
			
			formato14C.f_canDRGest.removeAttr("disabled");
			formato14C.f_costDRGest.removeAttr("disabled");
			formato14C.f_canIRGest.removeAttr("disabled");
			formato14C.f_costIRGest.removeAttr("disabled");
			
			formato14C.f_canDRAsist.removeAttr("disabled");
			formato14C.f_costDRAsist.removeAttr("disabled");
			formato14C.f_canIRAsist.removeAttr("disabled");
			formato14C.f_costIRAsist.removeAttr("disabled");	
			
			//PROVINCIA
			formato14C.f_canDPCoord.removeAttr("disabled");
			formato14C.f_costDPCoord.removeAttr("disabled");
			formato14C.f_canIPCoord.removeAttr("disabled");
			formato14C.f_costIPCoord.removeAttr("disabled");
			
			formato14C.f_canDPSupe.removeAttr("disabled");
			formato14C.f_costDPSupe.removeAttr("disabled");
			formato14C.f_canIPSupe.removeAttr("disabled");
			formato14C.f_costIPSupe.removeAttr("disabled");
			
			formato14C.f_canDPGest.removeAttr("disabled");
			formato14C.f_costDPGest.removeAttr("disabled");
			formato14C.f_canIPGest.removeAttr("disabled");
			formato14C.f_costIPGest.removeAttr("disabled");
			
			formato14C.f_canDPAsist.removeAttr("disabled");
			formato14C.f_costDPAsist.removeAttr("disabled");
			formato14C.f_canIPAsist.removeAttr("disabled");
			formato14C.f_costIPAsist.removeAttr("disabled");	
			
			//LIMA
			formato14C.f_canDLCoord.removeAttr("disabled");
			formato14C.f_costDLCoord.removeAttr("disabled");
			formato14C.f_canILCoord.removeAttr("disabled");
			formato14C.f_costILCoord.removeAttr("disabled");
			
			formato14C.f_canDLSupe.removeAttr("disabled");
			formato14C.f_costDLSupe.removeAttr("disabled");
			formato14C.f_canILSupe.removeAttr("disabled");
			formato14C.f_costILSupe.removeAttr("disabled");
			
			formato14C.f_canDLGest.removeAttr("disabled");
			formato14C.f_costDLGest.removeAttr("disabled");
			formato14C.f_canILGest.removeAttr("disabled");
			formato14C.f_costILGest.removeAttr("disabled");
			
			formato14C.f_canDLAsist.removeAttr("disabled");
			formato14C.f_costDLAsist.removeAttr("disabled");
			formato14C.f_canILAsist.removeAttr("disabled");
			formato14C.f_costILAsist.removeAttr("disabled");
			
			//ESTILOS
			formato14C.f_canDRCoord.addClass("fise-editable");
			formato14C.f_costDRCoord.addClass("fise-editable");
			formato14C.f_canIRCoord.addClass("fise-editable");
			formato14C.f_costIRCoord.addClass("fise-editable");
			
			formato14C.f_canDRSupe.addClass("fise-editable");
			formato14C.f_costDRSupe.addClass("fise-editable");
			formato14C.f_canIRSupe.addClass("fise-editable");
			formato14C.f_costIRSupe.addClass("fise-editable");
			
			formato14C.f_canDRGest.addClass("fise-editable");
			formato14C.f_costDRGest.addClass("fise-editable");
			formato14C.f_canIRGest.addClass("fise-editable");
			formato14C.f_costIRGest.addClass("fise-editable");
			
			formato14C.f_canDRAsist.addClass("fise-editable");
			formato14C.f_costDRAsist.addClass("fise-editable");
			formato14C.f_canIRAsist.addClass("fise-editable");
			formato14C.f_costIRAsist.addClass("fise-editable");	
			
			//PROVINCIA
			formato14C.f_canDPCoord.addClass("fise-editable");
			formato14C.f_costDPCoord.addClass("fise-editable");
			formato14C.f_canIPCoord.addClass("fise-editable");
			formato14C.f_costIPCoord.addClass("fise-editable");
			
			formato14C.f_canDPSupe.addClass("fise-editable");
			formato14C.f_costDPSupe.addClass("fise-editable");
			formato14C.f_canIPSupe.addClass("fise-editable");
			formato14C.f_costIPSupe.addClass("fise-editable");
			
			formato14C.f_canDPGest.addClass("fise-editable");
			formato14C.f_costDPGest.addClass("fise-editable");
			formato14C.f_canIPGest.addClass("fise-editable");
			formato14C.f_costIPGest.addClass("fise-editable");
			
			formato14C.f_canDPAsist.addClass("fise-editable");
			formato14C.f_costDPAsist.addClass("fise-editable");
			formato14C.f_canIPAsist.addClass("fise-editable");
			formato14C.f_costIPAsist.addClass("fise-editable");	
			
			//LIMA
			formato14C.f_canDLCoord.addClass("fise-editable");
			formato14C.f_costDLCoord.addClass("fise-editable");
			formato14C.f_canILCoord.addClass("fise-editable");
			formato14C.f_costILCoord.addClass("fise-editable");
			
			formato14C.f_canDLSupe.addClass("fise-editable");
			formato14C.f_costDLSupe.addClass("fise-editable");
			formato14C.f_canILSupe.addClass("fise-editable");
			formato14C.f_costILSupe.addClass("fise-editable");
			
			formato14C.f_canDLGest.addClass("fise-editable");
			formato14C.f_costDLGest.addClass("fise-editable");
			formato14C.f_canILGest.addClass("fise-editable");
			formato14C.f_costILGest.addClass("fise-editable");
			
			formato14C.f_canDLAsist.addClass("fise-editable");
			formato14C.f_costDLAsist.addClass("fise-editable");
			formato14C.f_canILAsist.addClass("fise-editable");
			formato14C.f_costILAsist.addClass("fise-editable");	
			
			//botones
			formato14C.botonReportePdf.css('display','none');
			formato14C.botonReporteExcel.css('display','none');
			
			formato14C.botonActaEnvio.css('display','none');
			
			formato14C.botonGrabar.css('display','');
			formato14C.botonValidacion.css('display','');
			formato14C.botonEnvioDefinitivo.css('display','');
			formato14C.botonActualizar.css('display','none');
			
			//panel de carga de exel y text
			formato14C.panelCargaArchivo.css('display','');
		},
		<portlet:namespace/>validacionFormato14C : function(){
			jQuery.ajax({
				url: formato14C.urlValidacion+'&'+formato14C.formCommand.serialize(),
				type : 'post',
				dataType : 'json',
				data : {
					<portlet:namespace />codEmpresa: formato14C.f_empresa.val(),
					<portlet:namespace />periodoEnvio: formato14C.f_periodoEnvio.val(),
					<portlet:namespace />anoIniVigencia: $('#anoIniVigencia').val(),
					<portlet:namespace />anoFinVigencia: $('#anoFinVigencia').val() 
				},
				success : function(data) {
					if( data!=null ){
						formato14C.dialogObservacion.dialog("open");
						formato14C.tablaObservacion.clearGridData(true);
						formato14C.tablaObservacion.jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
						formato14C.tablaObservacion[0].refreshIndex();
						formato14C.initBlockUI();
					}else{						
						var addhtmError='Error al realizar la validación del Formato 14C.';					
						formato14C.dialogErrorContentF14C.html(addhtmError);
						formato14C.dialogErrorF14C.dialog("open");	
						formato14C.initBlockUI();	
					}
				},error : function(){
					var addhtmError='Error de conexión.';					
					formato14C.dialogErrorContentF14C.html(addhtmError);
					formato14C.dialogErrorF14C.dialog("open");
					formato14C.initBlockUI();
				}
			});
		},
		 //funcion para ver reporte de las observaciones
		<portlet:namespace/>mostrarReporteValidacionF14C : function(){
			jQuery.ajax({
				url: formato14C.urlReporteValidacion+'&'+formato14C.formCommand.serialize(),
				type : 'post',
				dataType : 'json',
				data : {
					<portlet:namespace />codEmpresa: formato14C.f_empresa.val(),
					<portlet:namespace />periodoEnvio: formato14C.f_periodoEnvio.val(),					
					<portlet:namespace />nombreReporte: 'validacion14',
					<portlet:namespace />nombreArchivo: 'validacion14',
					<portlet:namespace />tipoArchivo: '0' //PDF
				},
				success : function(gridData) {
					if(gridData!=null){
						formato14C.verReporteF14C();	
					}else{						
						var addhtmError='Error al mostrar el reporte del Formato 14C.';					
						formato14C.dialogErrorContentF14C.html(addhtmError);
						formato14C.dialogErrorF14C.dialog("open");	
						formato14C.initBlockUI();
					}
					
				},error : function(){
					var addhtmError='Error de conexión.';					
					formato14C.dialogErrorContentF14C.html(addhtmError);
					formato14C.dialogErrorF14C.dialog("open");
					formato14C.initBlockUI();
				}
			});
		},
		//funcion para ver el reporte de envio definitivo
		<portlet:namespace/>mostrarReporteEnvioDefinitivoF14C : function(){
			jQuery.ajax({
				url: formato14C.urlReporteEnvioDefinitivo+'&'+formato14C.formCommand.serialize(),
				type : 'post',
				dataType : 'json',
				data : {
					<portlet:namespace />tipoArchivo: '2'//PDF+concatenado
				},
				success : function(gridData) {
					formato14C.verReporteF14C();
				},error : function(){
					var addhtmError='Error de conexión.';					
					formato14C.dialogErrorContentF14C.html(addhtmError);
					formato14C.dialogErrorF14C.dialog("open");
					formato14C.initBlockUI();
				}
			});
		},		
		//funcion para ver reposrte en una nueva pestaña
		verReporteF14C : function(){
			window.open('<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ViewReport")%>','_newtab');
		},
		//funcion para confirmar envio denifitivo
		confirmarEnvioDefinitivoF14C : function(){	
			var addhtml='¿Está seguro que desea realizar el Envío Definitivo del Formato 14C?';
			formato14C.dialogConfirmEnvioContent.html(addhtml);
			formato14C.dialogConfirmEnvio.dialog("open");
		},
		//funcion para envio definitivo		
		<portlet:namespace/>envioDefinitivoF14C : function(){			
			jQuery.ajax({
				url: formato14C.urlEnvioDefinitivo+'&'+formato14C.formCommand.serialize(),
				type : 'post',
				dataType : 'json',
				data : {
					 <portlet:namespace />codEmpresa: formato14C.f_empresa.val(),
					<portlet:namespace />periodoEnvio: formato14C.f_periodoEnvio.val(),
					<portlet:namespace />anoIniVigencia: $('#anoIniVigencia').val(),
					<portlet:namespace />anoFinVigencia: $('#anoFinVigencia').val(), 
					<portlet:namespace />nombreReporte: 'formato14C',
					<portlet:namespace />nombreArchivo: 'formato14C',
					<portlet:namespace />tipoArchivo: '0'//PDF
				},
				success : function(data) {
					if(data.resultado == "OK"){
						var addhtml='El Envío Definitivo se realizó satisfactoriamente a los siguientes correos electrónicos: '+data.correo;					
						//formato14C.dialogMessageContent.html(addhtml);
						$("#<portlet:namespace/>dialog-message-report-content").html(addhtml);
						formato14C.dialogMessageReport.dialog("open");
						formato14C.initBlockUI();					
					}else if(data.resultado == "EMAIL"){						
						var addhtmEmail = data.correo;				
						formato14C.dialogErrorContentF14C.html(addhtmEmail);
						formato14C.dialogErrorF14C.dialog("open");					
						formato14C.initBlockUI();	
					}else if(data.resultado == "OBSERVACION"){						
						var addhtmObs = 'No se puede relizar el Envío Definitivo del Formato 14C, primero debe subsanar las observaciones.';				
						formato14C.dialogInfoContent.html(addhtmObs);
						formato14C.dialogInfo.dialog("open");					
						formato14C.initBlockUI();	
					}else{						
						var addhtmError='Error al realizar el Envío Definitivo del Formato 14C.';					
						formato14C.dialogErrorContentF14C.html(addhtmError);
						formato14C.dialogErrorF14C.dialog("open");	
						formato14C.initBlockUI();
					}								
				},error : function(){
					var addhtmError='Error de conexión.';					
					formato14C.dialogErrorContentF14C.html(addhtmError);
					formato14C.dialogErrorF14C.dialog("open");
					formato14C.initBlockUI();
				}
			});
		},
		//funcion para mostrar reporte en pdf cuando se visualiza
		<portlet:namespace/>mostrarReportePdfF14C : function(){
			jQuery.ajax({
				url : formato14C.urlReporte+'&'+formato14C.formCommand.serialize(),
				type : 'post',
				dataType : 'json',
				data : {
					<portlet:namespace />codEmpresa: formato14C.f_empresa.val(),
					<portlet:namespace />periodoEnvio: formato14C.f_periodoEnvio.val(),
					<portlet:namespace />anoIniVigencia: $('#anoIniVigencia').val(),
					<portlet:namespace />anoFinVigencia: $('#anoFinVigencia').val(),
					<portlet:namespace />nombreReporte: 'formato14C',
					<portlet:namespace />nombreArchivo: 'formato14C',
					<portlet:namespace />tipoArchivo: '0'//PDF
				},
				success : function(gridData) {
					if(gridData!=null){
						formato14C.verReporteF14C();	
					}else{					
						var addhtmError='Error al mostrar el reporte del  Formato 14C.';					
						formato14C.dialogErrorContentF14C.html(addhtmError);
						formato14C.dialogErrorF14C.dialog("open");
						formato14C.initBlockUI();	
					}					
				},error : function(){
					var addhtmError='Error de conexión.';					
					formato14C.dialogErrorContentF14C.html(addhtmError);
					formato14C.dialogErrorF14C.dialog("open");
					formato14C.initBlockUI();
				}
			});
		},
		//funcion para mostrar reporte en exel cuando se visualiza
		<portlet:namespace/>mostrarReporteExcelF14C : function(){
			jQuery.ajax({
				url : formato14C.urlReporte+'&'+formato14C.formCommand.serialize(),
				type : 'post',
				dataType : 'json',
				data : {
					<portlet:namespace />codEmpresa: formato14C.f_empresa.val(),
					<portlet:namespace />periodoEnvio: formato14C.f_periodoEnvio.val(),
					<portlet:namespace />anoIniVigencia: $('#anoIniVigencia').val(),
					<portlet:namespace />anoFinVigencia: $('#anoFinVigencia').val(), 
					<portlet:namespace />nombreReporte: 'formato14C',
					<portlet:namespace />nombreArchivo: 'formato14C',
					<portlet:namespace />tipoArchivo: '1'//XLS
				},
				success : function(gridData) {
					if(gridData!=null){
						formato14C.verReporteF14C();	
					}else{						
						var addhtmError='Error al mostrar el reporte del Formato 14C.';					
						formato14C.dialogErrorContentF14C.html(addhtmError);
						formato14C.dialogErrorF14C.dialog("open");
						formato14C.initBlockUI();		
					}					
				},error : function(){
					var addhtmError='Error de conexión.';					
					formato14C.dialogErrorContentF14C.html(addhtmError);
					formato14C.dialogErrorF14C.dialog("open");
					formato14C.initBlockUI();
				}
			});
		},
		<portlet:namespace/>mostrarFormularioCargaExcel : function(){
			if (formato14C.validarArchivoCarga()){
				if( formato14C.flagCarga.val()=='0' ){//proviene de archivos nuevos
					formato14C.flagCarga.val('2');//para cargar archivos excel
				}else if( formato14C.flagCarga.val()=='1' ){//proviene de archivos modificados
					formato14C.flagCarga.val('3');//para cargar archivos excel
				}
				formato14C.divOverlay.show();
			    formato14C.dialogCargaExcel.show();			   
			    formato14C.dialogCargaExcel.draggable();
			    formato14C.dialogCargaExcel.css({ 
			        'left': ($(window).width() / 2 - formato14C.dialogCargaExcel.width() / 2) + 'px', 
			        'top': ($(window).height()  - formato14C.dialogCargaExcel.height() ) + 'px'
			    });
			}
		},		
		validarArchivoCarga : function() {		
			
			if(formato14C.f_empresa.val()==null || 
					formato14C.f_empresa.val().length == '' ) { 			
				var addhtmAlert='Seleccione una Distribuidora Eléctrica para proceder con la carga de archivo.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_empresa.focus();
				cod_focus=formato14C.f_empresa;
				return false; 
			}
			if(formato14C.f_periodoEnvio.val()==null || 
					formato14C.f_periodoEnvio.val().length == '' ) {					
				var addhtmAlert='Debe ingresar el Periodo a Declarar.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");
				//formato14C.f_periodoEnvio.focus();
				cod_focus=formato14C.f_periodoEnvio;
				return false; 
			}
			return true; 
		},
		regresarFormularioCargaExcel : function(){
			formato14C.flagCarga.val('');
			formato14C.dialogCargaExcel.hide();
			formato14C.divOverlay.hide();   
			$("#msjUploadFileExel").html("");			
		},
		
		<portlet:namespace/>cargarFormatoExcel : function(){
			var frm = document.getElementById('formato14CBean');
			
			var nameFile=$("#archivoExcel").val();
			var isSubmit=true;
			
			$("#msjUploadFileExel").html("");			
			if(typeof (nameFile) == "undefined" || nameFile.length==0){				
				isSubmit=false;
				$("#msjUploadFileExel").html("Debe seleccionar un archivo");
			}else{
				var extension=nameFile.substr(nameFile.indexOf(".")+1,nameFile.length);				
				if(extension == 'xls' || extension == 'xlsx'){
					isSubmit=true;
				}else{
					isSubmit=false;
					$("#msjUploadFileExel").html("Archivo inválido");
				}				
			}			
			if(isSubmit){
				frm.submit();
			}			
		},
		
		<portlet:namespace/>mostrarFormularioCargaTexto : function(){
			if (formato14C.validarArchivoCarga()){
				if( formato14C.flagCarga.val()=='0' ){//proviene de un archivo nuevo
					formato14C.flagCarga.val('4');//para cargar archivos texto
				}else if( formato14C.flagCarga.val()=='1' ){//proviene de un archivo modificado
					formato14C.flagCarga.val('5');//para archivos texto
				}
				formato14C.divOverlay.show();
				formato14C.dialogCargaTexto.show();
				formato14C.dialogCargaTexto.draggable();
				formato14C.dialogCargaTexto.css({ 
			        'left': ($(window).width() / 2 - formato14C.dialogCargaTexto.width() / 2) + 'px', 
			        'top': ($(window).height() - formato14C.dialogCargaTexto.height() ) + 'px'
			    });
			}
		},
		regresarFormularioCargaTexto : function(){
			formato14C.flagCarga.val('');
			formato14C.dialogCargaTexto.hide();
			formato14C.divOverlay.hide();  
			$("#msjUploadFileText").html("");			
		},
		
		<portlet:namespace/>cargarFormatoTexto : function(){
			var frm = document.getElementById('formato14CBean');	
			
			var nameFile=$("#archivoTxt").val();
			var isSubmit=true;
			
			$("#msjUploadFileText").html("");			
			if(typeof (nameFile) == "undefined" || nameFile.length==0){				
				isSubmit=false;
				$("#msjUploadFileText").html("Debe seleccionar un archivo");
			}else{
				var extension=nameFile.substr(nameFile.indexOf(".")+1,nameFile.length);				
				if(extension == 'txt'){
					isSubmit=true;
				}else{
					isSubmit=false;
					$("#msjUploadFileText").html("Archivo inválido");
				}				
			}			
			if(isSubmit){
				frm.submit();
			}	
		},
		//funcion para desabiliar campos lima
		deshabilitarLima : function(){
			//cabecera		
			formato14C.f_numUrbLima.attr("disabled",true);	
			formato14C.f_costoPromUrbLima.attr("disabled",true);		
			//LIMA
			formato14C.f_canDLCoord.attr("disabled",true);
			formato14C.f_costDLCoord.attr("disabled",true);
			formato14C.f_canILCoord.attr("disabled",true);
			formato14C.f_costILCoord.attr("disabled",true);
			
			formato14C.f_canDLSupe.attr("disabled",true);
			formato14C.f_costDLSupe.attr("disabled",true);
			formato14C.f_canILSupe.attr("disabled",true);
			formato14C.f_costILSupe.attr("disabled",true);
			
			formato14C.f_canDLGest.attr("disabled",true);
			formato14C.f_costDLGest.attr("disabled",true);
			formato14C.f_canILGest.attr("disabled",true);
			formato14C.f_costILGest.attr("disabled",true);
			
			formato14C.f_canDLAsist.attr("disabled",true);
			formato14C.f_costDLAsist.attr("disabled",true);
			formato14C.f_canILAsist.attr("disabled",true);
			formato14C.f_costILAsist.attr("disabled",true);	
			
			//ESTILOS
			//cabecera		
			formato14C.f_numUrbLima.removeClass("fise-editable");	
			formato14C.f_costoPromUrbLima.removeClass("fise-editable");		
			//LIMA
			formato14C.f_canDLCoord.removeClass("fise-editable");
			formato14C.f_costDLCoord.removeClass("fise-editable");
			formato14C.f_canILCoord.removeClass("fise-editable");
			formato14C.f_costILCoord.removeClass("fise-editable");
			
			formato14C.f_canDLSupe.removeClass("fise-editable");
			formato14C.f_costDLSupe.removeClass("fise-editable");
			formato14C.f_canILSupe.removeClass("fise-editable");
			formato14C.f_costILSupe.removeClass("fise-editable");
			
			formato14C.f_canDLGest.removeClass("fise-editable");
			formato14C.f_costDLGest.removeClass("fise-editable");
			formato14C.f_canILGest.removeClass("fise-editable");
			formato14C.f_costILGest.removeClass("fise-editable");
			
			formato14C.f_canDLAsist.removeClass("fise-editable");
			formato14C.f_costDLAsist.removeClass("fise-editable");
			formato14C.f_canILAsist.removeClass("fise-editable");
			formato14C.f_costILAsist.removeClass("fise-editable");	
			
			
		},
		//funcion para habilitar campos lima
		habilitarLima : function(flag){
			//cabecera		
			formato14C.f_numUrbLima.removeAttr("disabled");			
			formato14C.f_costoPromUrbLima.removeAttr("disabled");	
			//ESTILOS
			//cabecera		
			formato14C.f_numUrbLima.addClass("fise-editable");			
			formato14C.f_costoPromUrbLima.addClass("fise-editable");
			
			if(flag=='D'){
				formato14C.f_canDLCoord.removeAttr("disabled");
				formato14C.f_costDLCoord.removeAttr("disabled");
				formato14C.f_canDLSupe.removeAttr("disabled");
				formato14C.f_costDLSupe.removeAttr("disabled");
				formato14C.f_canDLGest.removeAttr("disabled");
				formato14C.f_costDLGest.removeAttr("disabled");
				formato14C.f_canDLAsist.removeAttr("disabled");
				formato14C.f_costDLAsist.removeAttr("disabled");
				//ESTILOS
				formato14C.f_canDLCoord.addClass("fise-editable");
				formato14C.f_costDLCoord.addClass("fise-editable");
				formato14C.f_canDLSupe.addClass("fise-editable");
				formato14C.f_costDLSupe.addClass("fise-editable");
				formato14C.f_canDLGest.addClass("fise-editable");
				formato14C.f_costDLGest.addClass("fise-editable");
				formato14C.f_canDLAsist.addClass("fise-editable");
				formato14C.f_costDLAsist.addClass("fise-editable");
			}else if(flag=='I'){
				formato14C.f_canILCoord.removeAttr("disabled");
				formato14C.f_costILCoord.removeAttr("disabled");
				formato14C.f_canILSupe.removeAttr("disabled");
				formato14C.f_costILSupe.removeAttr("disabled");
				formato14C.f_canILGest.removeAttr("disabled");
				formato14C.f_costILGest.removeAttr("disabled");
				formato14C.f_canILAsist.removeAttr("disabled");
				formato14C.f_costILAsist.removeAttr("disabled");
				//ESTILOS
				formato14C.f_canILCoord.addClass("fise-editable");
				formato14C.f_costILCoord.addClass("fise-editable");
				formato14C.f_canILSupe.addClass("fise-editable");
				formato14C.f_costILSupe.addClass("fise-editable");
				formato14C.f_canILGest.addClass("fise-editable");
				formato14C.f_costILGest.addClass("fise-editable");
				formato14C.f_canILAsist.addClass("fise-editable");
				formato14C.f_costILAsist.addClass("fise-editable");
			}else{				
				//LIMA
				formato14C.f_canDLCoord.removeAttr("disabled");
				formato14C.f_costDLCoord.removeAttr("disabled");	
				formato14C.f_canILCoord.removeAttr("disabled");
				formato14C.f_costILCoord.removeAttr("disabled");
				
				formato14C.f_canDLSupe.removeAttr("disabled");
				formato14C.f_costDLSupe.removeAttr("disabled");
				formato14C.f_canILSupe.removeAttr("disabled");
				formato14C.f_costILSupe.removeAttr("disabled");
				
				formato14C.f_canDLGest.removeAttr("disabled");
				formato14C.f_costDLGest.removeAttr("disabled");
				formato14C.f_canILGest.removeAttr("disabled");
				formato14C.f_costILGest.removeAttr("disabled");
				
				formato14C.f_canDLAsist.removeAttr("disabled");
				formato14C.f_costDLAsist.removeAttr("disabled");
				formato14C.f_canILAsist.removeAttr("disabled");
				formato14C.f_costILAsist.removeAttr("disabled");	
				
				//ESTILOS						
				//LIMA
				formato14C.f_canDLCoord.addClass("fise-editable");
				formato14C.f_costDLCoord.addClass("fise-editable");
				formato14C.f_canILCoord.addClass("fise-editable");
				formato14C.f_costILCoord.addClass("fise-editable");
				
				formato14C.f_canDLSupe.addClass("fise-editable");
				formato14C.f_costDLSupe.addClass("fise-editable");
				formato14C.f_canILSupe.addClass("fise-editable");
				formato14C.f_costILSupe.addClass("fise-editable");
				
				formato14C.f_canDLGest.addClass("fise-editable");
				formato14C.f_costDLGest.addClass("fise-editable");
				formato14C.f_canILGest.addClass("fise-editable");
				formato14C.f_costILGest.addClass("fise-editable");
				
				formato14C.f_canDLAsist.addClass("fise-editable");
				formato14C.f_costDLAsist.addClass("fise-editable");
				formato14C.f_canILAsist.addClass("fise-editable");
				formato14C.f_costILAsist.addClass("fise-editable");	
			}				
		},
		
		//Funcion para deshabilitar costos directos
		deshabilitarCostosDirectos : function(){
			//RURAL
			formato14C.f_canDRCoord.attr("disabled",true);
			formato14C.f_costDRCoord.attr("disabled",true);		
			
			formato14C.f_canDRSupe.attr("disabled",true);
			formato14C.f_costDRSupe.attr("disabled",true);			
			
			formato14C.f_canDRGest.attr("disabled",true);
			formato14C.f_costDRGest.attr("disabled",true);
			
			formato14C.f_canDRAsist.attr("disabled",true);
			formato14C.f_costDRAsist.attr("disabled",true);			
			
			//PROVINCIA
			formato14C.f_canDPCoord.attr("disabled",true);
			formato14C.f_costDPCoord.attr("disabled",true);			
			
			formato14C.f_canDPSupe.attr("disabled",true);
			formato14C.f_costDPSupe.attr("disabled",true);			
			
			formato14C.f_canDPGest.attr("disabled",true);
			formato14C.f_costDPGest.attr("disabled",true);			
			
			formato14C.f_canDPAsist.attr("disabled",true);
			formato14C.f_costDPAsist.attr("disabled",true);			
			
			//LIMA
			formato14C.f_canDLCoord.attr("disabled",true);
			formato14C.f_costDLCoord.attr("disabled",true);
			
			formato14C.f_canDLSupe.attr("disabled",true);
			formato14C.f_costDLSupe.attr("disabled",true);			
			
			formato14C.f_canDLGest.attr("disabled",true);
			formato14C.f_costDLGest.attr("disabled",true);			
			
			formato14C.f_canDLAsist.attr("disabled",true);
			formato14C.f_costDLAsist.attr("disabled",true);	
			
			//ESTILOS
			//RURAL
			formato14C.f_canDRCoord.removeClass("fise-editable");
			formato14C.f_costDRCoord.removeClass("fise-editable");		
			
			formato14C.f_canDRSupe.removeClass("fise-editable");
			formato14C.f_costDRSupe.removeClass("fise-editable");			
			
			formato14C.f_canDRGest.removeClass("fise-editable");
			formato14C.f_costDRGest.removeClass("fise-editable");
			
			formato14C.f_canDRAsist.removeClass("fise-editable");
			formato14C.f_costDRAsist.removeClass("fise-editable");			
			
			//PROVINCIA
			formato14C.f_canDPCoord.removeClass("fise-editable");
			formato14C.f_costDPCoord.removeClass("fise-editable");			
			
			formato14C.f_canDPSupe.removeClass("fise-editable");
			formato14C.f_costDPSupe.removeClass("fise-editable");			
			
			formato14C.f_canDPGest.removeClass("fise-editable");
			formato14C.f_costDPGest.removeClass("fise-editable");			
			
			formato14C.f_canDPAsist.removeClass("fise-editable");
			formato14C.f_costDPAsist.removeClass("fise-editable");			
			
			//LIMA
			formato14C.f_canDLCoord.removeClass("fise-editable");
			formato14C.f_costDLCoord.removeClass("fise-editable");
			
			formato14C.f_canDLSupe.removeClass("fise-editable");
			formato14C.f_costDLSupe.removeClass("fise-editable");			
			
			formato14C.f_canDLGest.removeClass("fise-editable");
			formato14C.f_costDLGest.removeClass("fise-editable");			
			
			formato14C.f_canDLAsist.removeClass("fise-editable");
			formato14C.f_costDLAsist.removeClass("fise-editable");
			
			
		},
		//Funcion costos directos 
		habilitarCostosDirectos : function(){			
			//RURAL
			formato14C.f_canDRCoord.removeAttr("disabled");
			formato14C.f_costDRCoord.removeAttr("disabled");			
			
			formato14C.f_canDRSupe.removeAttr("disabled");
			formato14C.f_costDRSupe.removeAttr("disabled");			
			
			formato14C.f_canDRGest.removeAttr("disabled");
			formato14C.f_costDRGest.removeAttr("disabled");			
			
			formato14C.f_canDRAsist.removeAttr("disabled");
			formato14C.f_costDRAsist.removeAttr("disabled");			
			
			//PROVINCIA
			formato14C.f_canDPCoord.removeAttr("disabled");
			formato14C.f_costDPCoord.removeAttr("disabled");			
			
			formato14C.f_canDPSupe.removeAttr("disabled");
			formato14C.f_costDPSupe.removeAttr("disabled");			
			
			formato14C.f_canDPGest.removeAttr("disabled");
			formato14C.f_costDPGest.removeAttr("disabled");			
			
			formato14C.f_canDPAsist.removeAttr("disabled");
			formato14C.f_costDPAsist.removeAttr("disabled");		
			
			//LIMA
			formato14C.f_canDLCoord.removeAttr("disabled");
			formato14C.f_costDLCoord.removeAttr("disabled");			
			
			formato14C.f_canDLSupe.removeAttr("disabled");
			formato14C.f_costDLSupe.removeAttr("disabled");			
			
			formato14C.f_canDLGest.removeAttr("disabled");
			formato14C.f_costDLGest.removeAttr("disabled");			
			
			formato14C.f_canDLAsist.removeAttr("disabled");
			formato14C.f_costDLAsist.removeAttr("disabled");
			
			//ESTILOS
			//RURAL
			formato14C.f_canDRCoord.addClass("fise-editable");
			formato14C.f_costDRCoord.addClass("fise-editable");			
			
			formato14C.f_canDRSupe.addClass("fise-editable");
			formato14C.f_costDRSupe.addClass("fise-editable");			
			
			formato14C.f_canDRGest.addClass("fise-editable");
			formato14C.f_costDRGest.addClass("fise-editable");			
			
			formato14C.f_canDRAsist.addClass("fise-editable");
			formato14C.f_costDRAsist.addClass("fise-editable");			
			
			//PROVINCIA
			formato14C.f_canDPCoord.addClass("fise-editable");
			formato14C.f_costDPCoord.addClass("fise-editable");			
			
			formato14C.f_canDPSupe.addClass("fise-editable");
			formato14C.f_costDPSupe.addClass("fise-editable");			
			
			formato14C.f_canDPGest.addClass("fise-editable");
			formato14C.f_costDPGest.addClass("fise-editable");			
			
			formato14C.f_canDPAsist.addClass("fise-editable");
			formato14C.f_costDPAsist.addClass("fise-editable");		
			
			//LIMA
			formato14C.f_canDLCoord.addClass("fise-editable");
			formato14C.f_costDLCoord.addClass("fise-editable");			
			
			formato14C.f_canDLSupe.addClass("fise-editable");
			formato14C.f_costDLSupe.addClass("fise-editable");			
			
			formato14C.f_canDLGest.addClass("fise-editable");
			formato14C.f_costDLGest.addClass("fise-editable");			
			
			formato14C.f_canDLAsist.addClass("fise-editable");
			formato14C.f_costDLAsist.addClass("fise-editable");
			
		},
		
		//Funcion para deshabilitar costo indirectos
		deshabilitarCostosIndirectos : function(){		
			//RURAL			
			formato14C.f_canIRCoord.attr("disabled",true);
			formato14C.f_costIRCoord.attr("disabled",true);		
			
			formato14C.f_canIRSupe.attr("disabled",true);
			formato14C.f_costIRSupe.attr("disabled",true);		
			
			formato14C.f_canIRGest.attr("disabled",true);
			formato14C.f_costIRGest.attr("disabled",true);			
			
			formato14C.f_canIRAsist.attr("disabled",true);
			formato14C.f_costIRAsist.attr("disabled",true);		
			
			//PROVINCIA			
			formato14C.f_canIPCoord.attr("disabled",true);
			formato14C.f_costIPCoord.attr("disabled",true);	
			
			formato14C.f_canIPSupe.attr("disabled",true);
			formato14C.f_costIPSupe.attr("disabled",true);		
			
			formato14C.f_canIPGest.attr("disabled",true);
			formato14C.f_costIPGest.attr("disabled",true);		
			
			formato14C.f_canIPAsist.attr("disabled",true);
			formato14C.f_costIPAsist.attr("disabled",true);	
			
			//LIMA			
			formato14C.f_canILCoord.attr("disabled",true);
			formato14C.f_costILCoord.attr("disabled",true);
						
			formato14C.f_canILSupe.attr("disabled",true);
			formato14C.f_costILSupe.attr("disabled",true);
						
			formato14C.f_canILGest.attr("disabled",true);
			formato14C.f_costILGest.attr("disabled",true);
						
			formato14C.f_canILAsist.attr("disabled",true);
			formato14C.f_costILAsist.attr("disabled",true);	
			
			//ESTILOS		
			
			//RURAL			
			formato14C.f_canIRCoord.removeClass("fise-editable");
			formato14C.f_costIRCoord.removeClass("fise-editable");		
			
			formato14C.f_canIRSupe.removeClass("fise-editable");
			formato14C.f_costIRSupe.removeClass("fise-editable");		
			
			formato14C.f_canIRGest.removeClass("fise-editable");
			formato14C.f_costIRGest.removeClass("fise-editable");			
			
			formato14C.f_canIRAsist.removeClass("fise-editable");
			formato14C.f_costIRAsist.removeClass("fise-editable");		
			
			//PROVINCIA			
			formato14C.f_canIPCoord.removeClass("fise-editable");
			formato14C.f_costIPCoord.removeClass("fise-editable");	
			
			formato14C.f_canIPSupe.removeClass("fise-editable");
			formato14C.f_costIPSupe.removeClass("fise-editable");		
			
			formato14C.f_canIPGest.removeClass("fise-editable");
			formato14C.f_costIPGest.removeClass("fise-editable");		
			
			formato14C.f_canIPAsist.removeClass("fise-editable");
			formato14C.f_costIPAsist.removeClass("fise-editable");	
			
			//LIMA			
			formato14C.f_canILCoord.removeClass("fise-editable");
			formato14C.f_costILCoord.removeClass("fise-editable");
						
			formato14C.f_canILSupe.removeClass("fise-editable");
			formato14C.f_costILSupe.removeClass("fise-editable");
						
			formato14C.f_canILGest.removeClass("fise-editable");
			formato14C.f_costILGest.removeClass("fise-editable");
						
			formato14C.f_canILAsist.removeClass("fise-editable");
			formato14C.f_costILAsist.removeClass("fise-editable");	
			
		},
		//Funcion para habilitar los campos que se desabilitan en la visualizacion opcion ver
		habilitarCostosIndirectos : function(){			
		    //RURAL			
			formato14C.f_canIRCoord.removeAttr("disabled");
			formato14C.f_costIRCoord.removeAttr("disabled");			
			
			formato14C.f_canIRSupe.removeAttr("disabled");
			formato14C.f_costIRSupe.removeAttr("disabled");			
			
			formato14C.f_canIRGest.removeAttr("disabled");
			formato14C.f_costIRGest.removeAttr("disabled");			
			
			formato14C.f_canIRAsist.removeAttr("disabled");
			formato14C.f_costIRAsist.removeAttr("disabled");	
			
			//PROVINCIA			
			formato14C.f_canIPCoord.removeAttr("disabled");
			formato14C.f_costIPCoord.removeAttr("disabled");			
			
			formato14C.f_canIPSupe.removeAttr("disabled");
			formato14C.f_costIPSupe.removeAttr("disabled");			
			
			formato14C.f_canIPGest.removeAttr("disabled");
			formato14C.f_costIPGest.removeAttr("disabled");		
			
			formato14C.f_canIPAsist.removeAttr("disabled");
			formato14C.f_costIPAsist.removeAttr("disabled");	
			
			//LIMA			
			formato14C.f_canILCoord.removeAttr("disabled");
			formato14C.f_costILCoord.removeAttr("disabled");
			
			formato14C.f_canILSupe.removeAttr("disabled");
			formato14C.f_costILSupe.removeAttr("disabled");			
			
			formato14C.f_canILGest.removeAttr("disabled");
			formato14C.f_costILGest.removeAttr("disabled");			
			
			formato14C.f_canILAsist.removeAttr("disabled");
			formato14C.f_costILAsist.removeAttr("disabled");	
			
			//ESTILOS			
			
			//RURAL			
			formato14C.f_canIRCoord.addClass("fise-editable");
			formato14C.f_costIRCoord.addClass("fise-editable");			
			
			formato14C.f_canIRSupe.addClass("fise-editable");
			formato14C.f_costIRSupe.addClass("fise-editable");			
			
			formato14C.f_canIRGest.addClass("fise-editable");
			formato14C.f_costIRGest.addClass("fise-editable");			
			
			formato14C.f_canIRAsist.addClass("fise-editable");
			formato14C.f_costIRAsist.addClass("fise-editable");	
			
			//PROVINCIA			
			formato14C.f_canIPCoord.addClass("fise-editable");
			formato14C.f_costIPCoord.addClass("fise-editable");			
			
			formato14C.f_canIPSupe.addClass("fise-editable");
			formato14C.f_costIPSupe.addClass("fise-editable");			
			
			formato14C.f_canIPGest.addClass("fise-editable");
			formato14C.f_costIPGest.addClass("fise-editable");		
			
			formato14C.f_canIPAsist.addClass("fise-editable");
			formato14C.f_costIPAsist.addClass("fise-editable");	
			
			//LIMA			
			formato14C.f_canILCoord.addClass("fise-editable");
			formato14C.f_costILCoord.addClass("fise-editable");
			
			formato14C.f_canILSupe.addClass("fise-editable");
			formato14C.f_costILSupe.addClass("fise-editable");			
			
			formato14C.f_canILGest.addClass("fise-editable");
			formato14C.f_costILGest.addClass("fise-editable");			
			
			formato14C.f_canILAsist.addClass("fise-editable");
			formato14C.f_costILAsist.addClass("fise-editable");
			
		},
		<portlet:namespace/>mostrarReporteActaEnvio : function(){
			var estado = $('#estado').val();	
			if(estado=='Enviado'){
				jQuery.ajax({
					url: formato14C.urlReporteActaEnvio+'&'+formato14C.formCommand.serialize(),
					type : 'post',
					dataType : 'json',
					data : {
						<portlet:namespace />codEmpresa: formato14C.f_empresa.val(),
						<portlet:namespace />periodoEnvio: formato14C.f_periodoEnvio.val(),
						<portlet:namespace />anoIniVigencia: $('#anoIniVigencia').val(),
						<portlet:namespace />anoFinVigencia: $('#anoFinVigencia').val(),
						<portlet:namespace />tipoArchivo: '0'//PDF
					},
					success : function(gridData) {
						formato14C.verReporteF14C();
					},error : function(){						
						var addhtmError='Error de conexión.';					
						formato14C.dialogErrorContentF14C.html(addhtmError);
						formato14C.dialogErrorF14C.dialog("open");
						formato14C.initBlockUI();
					}
				});
			}else{				
				var addhtmError='Primero debe realizar el envío definitivo del Formato 14C.';					
				formato14C.dialogInfoContent.html(addhtmError);
				formato14C.dialogInfo.dialog("open");
				cod_focus= $('#idGrupoInfo');		
			}
		},
		
		//funcion para poner focus al momento de validar y campo
		ponerFocus : function(cadena){		
			cadena.focus();
			
		},	
		
		//funcion para verificar la ultima etapa del formato		
		validarUltimaEtapaF14C : function(){
			var valorObtenido = $('#etapaFinal').val();	
			console.debug("Entro a etapa final: "+valorObtenido);			
			if(valorObtenido=='SI'){
				var addhtmAlert='No se puede realizar esta operación debido a que el Formato se encuentra en una etapa avanzada.';					
				formato14C.dialogValidacionContent.html(addhtmAlert);
				formato14C.dialogValidacion.dialog("open");				
				cod_focus = $('#etapaFinal');			   
				return false;				
			}else{				
				return true;				
			}			
		},		
		
		
		//funcion cargar la imagen al momento de buscar
		blockUI : function(){
			$.blockUI({ message: '<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Cargando </h3>' });
		},
		//funcion para bloquear la pantalla cunado esta buscando
		unblockUI : function(){
			$.unblockUI();
		},
		//funcion para desbloquear la pantalla cunado esta buscando
		initBlockUI : function(){
			$(document).ajaxStop($.unblockUI); 		
		},
		//DIALOGOS
		initDialogs : function(){	
			formato14C.dialogMessage.dialog({
				modal: true,
				autoOpen: false,
				width: 450,		
				buttons: {
					Ok: function() {
						$( this ).dialog("close");
					}
				}
			});
			//dialogo para eliminar registro
			formato14C.dialogConfirm.dialog({
				modal: true,
				height: 200,
				width: 450,			
				autoOpen: false,
				buttons: {
					"Si": function() {
						formato14C.eliminarFormatoF14C(cod_Empresa,ano_Presentacion,mes_Presentacion,ano_Inicio_Vigencia,ano_Fin_Vigencia,cod_Etapa);
						$( this ).dialog("close");
					},
					"No": function() {				
						$( this ).dialog("close");
					}
				}
			});	
			formato14C.dialogError.dialog({
				modal: true,
				width: 700,
				autoOpen: false,
				buttons: {
					Ok: function() {
						$( this ).dialog("close");
					}
				}
			});
			formato14C.dialogObservacion.dialog({
				modal: true,
				width: 700,
				autoOpen: false,
				buttons: {
					Cerrar: function() {
						$( this ).dialog("close");
					}
				}
			});
			formato14C.dialogConfirmEnvio.dialog({
				modal: true,
				height: 200,
				width: 450,			
				autoOpen: false,
				buttons: {
					"Si": function() {
						formato14C.<portlet:namespace/>envioDefinitivoF14C();
						$( this ).dialog("close");
					},
					"No": function() {				
						$( this ).dialog("close");
					}
				}
			});
			formato14C.dialogCarga.dialog({
				autoOpen: false,
				height: 240,
				width: 450,
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
			//dialogo para en envio del reporte definitivo
			formato14C.dialogMessageReport.dialog({
				modal: true,
				autoOpen: false,
				width: 450,	
				buttons: {
					'Ver Acta': function() {
						formato14C.<portlet:namespace/>mostrarReporteEnvioDefinitivoF14C();
						$( this ).dialog("close");
						formato14C.divNuevo.hide();
					    formato14C.divBuscar.show();
					    formato14C.botonBuscar.trigger('click');
					},
					Ok: function() {
						$( this ).dialog("close");
						formato14C.divNuevo.hide();
					    formato14C.divBuscar.show();
					    formato14C.botonBuscar.trigger('click');
					}
				},
			    close: function(event,ui){
			    	formato14C.divNuevo.hide();
				    formato14C.divBuscar.show();
				    formato14C.botonBuscar.trigger('click');
		       }
			});
			
			formato14C.dialogValidacion.dialog({
				modal: true,
				autoOpen: false,
				width: 450,		
				buttons: {
					OK: function() {
						formato14C.ponerFocus(cod_focus);
						$( this ).dialog("close");
					}
				}
			});
			
			formato14C.dialogErrorF14C.dialog({
				modal: true,
				autoOpen: false,
				width: 450,		
				buttons: {
					OK: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			formato14C.dialogInfo.dialog({
				modal: true,
				autoOpen: false,
				width: 450,		
				buttons: {
					OK: function() {
						$( this ).dialog("close");
					}
				}
			});
			
		} /***fin de inicializar los dialogos**/	
		
	};//fin general de la pagina
	
</script>