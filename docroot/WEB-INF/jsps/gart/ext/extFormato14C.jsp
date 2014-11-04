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
		
		//dialogos	
		dialogMessage:null,
		//dialogConfirm:null,
		//dialogConfirmEnvio:null,
		//dialogCarga:null,
		//dialogError:null,
		//dialogObservacion:null,
		dialogMessageContent:null,
		//dialogConfirmContent:null,
		//dialogConfirmEnvioContent:null,
		//dialogCargaExcel:null,
		//dialogCargaTexto:null,
		
		//mensajes
		mensajeCargando:null,
		mensajeObteniendoDatos:null,
		mensajeEliminando:null,
		mensajeGuardando:null,			
		
		//urls
		urlBusqueda: null,
		urlCargaPeriodo:null,		
		urlCrud:null,
		
		//botones
		botonBuscar:null,
		botonNuevo:null,
		botonRegresar:null,
		botonReportePdf:null,
		botonReporteExcel:null,
		botonGrabar:null,
		botonValidacion:null,
		botonEnvioDefinitivo:null,
		
		//varibales de busqueda
		i_codEmpresaBusq:null,
		i_anioDesde:null,
		i_mesDesde:null,
		i_anioHasta:null,
		i_mesHasta:null,
		i_etapaBusq:null,
		
		//varibales para nuevo y editar
	    /*Cabecera*/
		f_empresa:null,f_periodoEnvio:null,f_nombreSede:null,f_numRural:null,f_numUrbProv:null,f_numUrbLima:null,
		f_costoPromRural:null,f_costoPromUrbProv:null,f_costoPromUrbLima:null,	
		/*Detalle*/
		/*RURAL*/
		f_canDRCoord:null,f_costDRCoord:null,f_canIRCoord:null, f_costIRCoord:null, f_canDRSupe:null,f_costDRSupe:null,
		f_canIRSupe:null,f_costIRSupe:null,f_canDRGest:null,f_costDRGest:null,f_canIRGest:null,f_costIRGest:null,
		f_canDRAsist:null,f_costDRAsist:null,f_canIRAsist:null,f_costIRAsist:null,
		/*PROVINCIAS*/
		f_canDPCoord:null,f_costDPCoord:null,f_canIPCoord:null,	f_costIPCoord:null,f_canDPSupe:null,f_costDPSupe:null,
		f_canIPSupe:null,f_costIPSupe:null,f_canDPGest:null,f_costDPGest:null,f_canIPGest:null,f_costIPGest:null,
		f_canDPAsist:null,f_costDPAsist:null,f_canIPAsist:null,	f_costIPAsist:null,
		/*LIMA*/
		f_canDLCoord:null,f_costDLCoord:null,f_canILCoord:null,f_costILCoord:null,f_canDLSupe:null,f_costDLSupe:null,f_canILSupe:null,
		f_costILSupe:null,f_canDLGest:null,f_costDLGest:null,f_canILGest:null,f_costILGest:null,f_canDLAsist:null,f_costDLAsist:null,
		f_canILAsist:null,	f_costILAsist:null,		
		
		//grillas
		tablaResultados:null,
		paginadoResultados:null,
				
				
		//cargar los id de los elementos del html		
		init : function(){
			this.formCommand=$('#formato14CBean'); 
			//divs
			this.divBuscar=$("#<portlet:namespace/>div_buscar");
			this.divNuevo=$("#<portlet:namespace/>div_nuevo");
			
			//dialogos		
			this.dialogMessage=$("#<portlet:namespace/>dialog-message-grabar");//para guardar
			//this.dialogConfirm=$("#<portlet:namespace/>dialog-confirm");
			//this.dialogConfirmEnvio=$("#<portlet:namespace/>dialog-confirm-envio");
			//this.dialogCarga=$("#<portlet:namespace/>dialog-form-carga");
			//this.dialogError=$("#<portlet:namespace/>dialog-form-error");
			//this.dialogObservacion=$("#<portlet:namespace/>dialog-form-observacion");
			this.dialogMessageContent=$("#<portlet:namespace/>dialog-message-content-grabar");//para guardar
			//this.dialogConfirmContent=$("#<portlet:namespace/>dialog-confirm-content");
			//this.dialogConfirmEnvioContent=$("#<portlet:namespace/>dialog-confirm-envio-content");
			//this.dialogCargaExcel=$("#<portlet:namespace/>dialog-form-cargaExcel");
			//this.dialogCargaTexto=$("#<portlet:namespace/>dialog-form-cargaTexto");
		
			//mensajes			
			this.mensajeCargando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Cargando </h3>';
			this.mensajeObteniendoDatos='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Obteniendo Datos </h3>';
			this.mensajeEliminando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Eliminando </h3>';
			this.mensajeGuardando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Guardando Datos </h3>';
			
			//urls
			this.urlBusqueda='<portlet:resourceURL id="busqueda" />';
			this.urlCargaPeriodo='<portlet:resourceURL id="cargaPeriodo" />';
			this.urlCrud='<portlet:resourceURL id="crud" />';
			
			//botones
			this.botonBuscar=$("#<portlet:namespace/>btnBuscarF14C");
			this.botonNuevo=$("#<portlet:namespace/>btnNuevoF14C");
			this.botonRegresar=$("#<portlet:namespace/>regresarFormatoF14C");
			this.botonReportePdf=$("#<portlet:namespace/>reportePdfF14C");
			this.botonReporteExcel=$("#<portlet:namespace/>reporteExcelF14C");
			this.botonGrabar=$("#<portlet:namespace/>guardarFormatoF14C");
			this.botonValidacion=$("#<portlet:namespace/>validacionFormatoF14C");
			this.botonEnvioDefinitivo=$("#<portlet:namespace/>envioDefinitivoF14C");
			
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
			this.f_nombreSede=$('#nombreSede');
			this.f_numRural=$('#numRural');
			this.f_numUrbProv=$('#numUrbProv');
			this.f_numUrbLima=$('#numUrbLima');
			this.f_costoPromRural=$('#costoPromRural');
			this.f_costoPromUrbProv=$('#costoPromUrbProv');
			this.f_costoPromUrbLima=$('#costoPromUrbLima');	
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
			
			
			//grillas			
			this.tablaResultados=$("#<portlet:namespace/>grid_resultado_busqueda");
			this.paginadoResultados='#<portlet:namespace/>paginador_resultado_busqueda';
			this.buildGrids();	//cargar el modelo de la grilla	
			
			
			//llamado a la funcion buscar
			formato14C.botonBuscar.click(function() {
				formato14C.buscar();
			});			
			//llamado a la funcion nuevo
			formato14C.botonNuevo.click(function() {
				formato14C.<portlet:namespace/>nuevoFormato14C();
		    });
			//llamando funcion guardar
			formato14C.botonGrabar.click(function() {
				formato14C.<portlet:namespace/>guardarFormato14C();
			});
			//llamando funcion regresar
		    formato14C.botonRegresar.click(function() {
		    	formato14C.<portlet:namespace/>regresarF14C();
		    });			
			//eventos 		
			formato14C.f_empresa.change(function(){
				formato14C.<portlet:namespace/>loadPeriodo('');
			});
			formato14C.f_periodoEnvio.change(function(){
				formato14C.<portlet:namespace/>loadPeriodo(this.value);
			});
			
			formato14C.initDialogs();
			//eventos por defecto
			//formato14C.botonBuscar.trigger('click');
			formato14C.initBlockUI();
		},
		//funcion para armar el modelo de la grilla para el resultado
		buildGrids : function () {	
			formato14C.tablaResultados.jqGrid({
			   datatype: "local",
		       colNames: ['Empresa','Año Pres.','Mes Pres.','Año Ini. Vig.','Año Fin Vig.','Grupo Inf','Visualizar','Editar','Anular','Estado','','',''],
		       colModel: [
					   { name: 'descEmpresa', index: 'descEmpresa', width: 50},
		               { name: 'anoPresentacion', index: 'anoPresentacion', width: 30 },   
		               { name: 'descMesPresentacion', index: 'descMesPresentacion', width: 30},
		               { name: 'anoIniVigencia', index: 'anoIniVigencia', width: 30 },   
		               { name: 'anoFinVigencia', index: 'anoFinVigencia', width: 30},
		               { name: 'grupoInfo', index: 'grupoInfo', width: 50},
		               { name: 'view', index: 'view', width: 20,align:'center' },
		               { name: 'edit', index: 'edit', width: 20,align:'center' },
		               { name: 'elim', index: 'elim', width: 20,align:'center' },
		               { name: 'estado', index: 'estado', width: 50,align:'center'},
		               { name: 'codEmpresa', index: 'codEmpresa', hidden: true},
		               { name: 'mesPresentacion', index: 'mesPresentacion', hidden: true},
		               { name: 'etapa', index: 'etapa', hidden: true}
			   	    ],
			   	 multiselect: false,
					rowNum:10,
				   	rowList:[10,20,50],
					height: 200,
				   	autowidth: true,
					rownumbers: true,
					shrinkToFit:true,
					pager: formato14C.paginadoResultados,
				    viewrecords: true,
				   	caption: "Formatos",
				    sortorder: "asc",	   	    	   	   
		       gridComplete: function(){
		      		var ids = formato14C.tablaResultados.jqGrid('getDataIDs');
		      		for(var i=0;i < ids.length;i++){
		      			var cl = ids[i];
		      			var ret = formato14C.tablaResultados.jqGrid('getRowData',cl);           
		      			view = "<a href='#'><img border='0' title='View' src='/net-theme/images/img-net/file.png'  align='center' onclick=\"formato14C.verFormato('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoIniVigencia+"','"+ret.anoFinVigencia+"','"+ret.etapa+"');\" /></a> ";
		      			edit = "<a href='#'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png'  align='center' onclick=\"formato14C.editarFormato('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoIniVigencia+"','"+ret.anoFinVigencia+"','"+ret.etapa+"');\" /></a> ";
		      			elim = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"formato14C.confirmarEliminar('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoIniVigencia+"','"+ret.anoFinVigencia+"','"+ret.etapa+"');\" /></a> ";              			
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
			       <%--   location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>'; --%> 
			       } 
			}); 
		},
		//funcion para buscar
		buscar : function () {
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
							alert("Error de conexión.");
							formato14C.initBlockUI();
						}
				});
			}
		},
		//funcion para validar datos para la busqueda
		validarBusqueda : function(){			
			if(formato14C.i_anioDesde.val().length != '' || formato14C.i_anioHasta.val().length != '') {		  
				if(formato14C.i_anioDesde.val().length == ''){					 
				     alert('Por favor, Ingrese un año desde');
				     formato14C.i_anioDesde.focus();
				     return false;		
				} else if(formato14C.i_anioHasta.val().length == '' ) {
				     alert('Por favor, Ingrese un año hasta');
					 formato14C.i_anioHasta.focus();
					 return false;
				}else if (isNaN(trim(formato14C.i_anioDesde.val())) || 
						formato14C.i_anioDesde.val().length<4 || 
						parseFloat(trim(formato14C.i_anioDesde.val()))<1900){
				     alert('Ingrese un año desde válido');
				     formato14C.i_anioDesde.focus();
				     return false;		
				}else if (isNaN(trim(formato14C.i_anioHasta.val())) || 
						formato14C.i_anioDesde.val().length<4 || 
						parseFloat(trim(formato14C.i_anioHasta.val()))<1900){
				     alert('Ingrese un año hasta válido');
				     formato14C.i_anioHasta.focus();
				     return false;					 
			   }else if(parseFloat(formato14C.i_anioDesde.val()) > parseFloat(formato14C.i_anioHasta.val()) ){
				    alert('El año desde no puede exceder al año hasta');
				    return false;					 
			  }else{
				  return true;
			  }
		   }else if(formato14C.i_etapaBusq.val().length == '' ) { 	    
				    alert('Seleccione una etapa');
				    formato14C.i_etapaB.focus();
				    return false; 
		   }else{
			   return true;   
		   }		 
		},
		//funcion para nuevo registro
		<portlet:namespace/>nuevoFormato14C : function(){	
			//alert('entro a crear');
			/*formato14C.inicializarFormulario();*/
			//data_funcion = [];
			//$('#Estado').val('SAVE');
			//$("#etapaEdit").val("");
			formato14C.divNuevo.show();
			formato14C.divBuscar.hide();		
			//$('#flagCarga').val('0');
			//
			//if( $('#flagPeriodoEjecucion').val()=='S' ){
			//	 $("#i_anioejecuc").val($("#anioDesdeSes").val());
			//	 $("#s_mes_ejecuc").val(parseInt($("#mesDesdeSes").val()));
			//}
			//$('#<portlet:namespace/>validacionFormato').css('display','none');
			//cargarPeriodoYCostos('','');
		},
		<portlet:namespace/>regresar : function(){
			//$("#Estado").val("");
			//$("#etapaEdit").val("");
			formato14C.divNuevo.hide();
			formato14C.divBuscar.show();		
			//
			//removerDeshabilitados();
			//se visualizan los componentes escondidos
			/*$('#<portlet:namespace/>reportePdf').css('display','none');
			$('#<portlet:namespace/>reporteExcel').css('display','none');
			$('#<portlet:namespace/>guardarFormato').css('display','');
			$('#panelCargaArchivos').css('display','');
			$('#<portlet:namespace/>validacionFormato').css('display','');
			$('#<portlet:namespace/>envioDefinitivo').css('display','');*/
			formato14C.botonBuscar.trigger('click');
			//<portlet:namespace/>buscar();
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
						//formato14C.<portlet:namespace/>loadCargaFlagPeriodo();
					},error : function(){
						alert("Error de conexión.");
						formato14C.initBlockUI();
					}
			});
		},
		<portlet:namespace/>loadCargaFlagPeriodo : function() {
			jQuery.ajax({
				url: formato14C.urlCargaFlagPeriodo+'&'+formato14C.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					success: function(data) {				
						dwr.util.setValue("flagPeriodoEjecucion", data.flagPeriodoEjecucion);
						formato14C.recargarPeriodoEjecucion();
						formato14C.mostrarPeriodoEjecucion();
					},error : function(){
						alert("Error de conexión.");
						formato14C.initBlockUI();
					}
			});
		},
		//function para inicializar el formulario
		inicializarFormulario : function(){
			formato14C.f_empresa.val('');
			/*if( formato14C.f_flagPeriodo.val()=='S' ){
				$('#anioInicioVigencia').val('');
				$('#anioFinVigencia').val('');
				$('#anioInicioVigencia').attr("disabled",false);
				$('#anioFinVigencia').attr("disabled",false);
			}*/
			/**cabecera*/
			formato14C.f_numRuralval('0');
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
			formato14C.f_costTotalRCoord.val('0.00');
			
			formato14C.f_canDRSupe.val('0');
			formato14C.f_costDRSupe.val('0.00');
			formato14C.f_canIRSupe.val('0');
			formato14C.f_costIRSupe.val('0.00');
			formato14C.f_costTotalRSupe.val('0.00');
			
			formato14C.f_canDRGest.val('0');
			formato14C.f_costDRGest.val('0.00');
			formato14C.f_canIRGest.val('0');
			formato14C.f_costIRGest.val('0.00');
			formato14C.f_costTotalRGest.val('0.00');			
			
			formato14C.f_canDRAsist.val('0');
			formato14C.f_costDRAsist.val('0.00');
			formato14C.f_canIRAsist.val('0');
			formato14C.f_costIRAsist.val('0.00');
			formato14C.f_costTotalRAsist.val('0.00');
			
			//PROVINCIA
			formato14C.f_canDPCoord.val('0');
			formato14C.f_costDPCoord.val('0.00');
			formato14C.f_canIPCoord.val('0');
			formato14C.f_costIPCoord.val('0.00');
			formato14C.f_costTotalPCoord.val('0.00');
			
			formato14C.f_canDPSupe.val('0');
			formato14C.f_costDPSupe.val('0.00');
			formato14C.f_canIPSupe.val('0');
			formato14C.f_costIPSupe.val('0.00');
			formato14C.f_costTotalPSupe.val('0.00');
			
			formato14C.f_canDPGest.val('0');
			formato14C.f_costDPGest.val('0.00');
			formato14C.f_canIPGest.val('0');
			formato14C.f_costIPGest.val('0.00');
			formato14C.f_costTotalPGest.val('0.00');			
			
			formato14C.f_canDPAsist.val('0');
			formato14C.f_costDPAsist.val('0.00');
			formato14C.f_canIPAsist.val('0');
			formato14C.f_costIPAsist.val('0.00');
			formato14C.f_costTotalPAsist.val('0.00');
			
			//LIMA
			formato14C.f_canDLCoord.val('0');
			formato14C.f_costDLCoord.val('0.00');
			formato14C.f_canILCoord.val('0');
			formato14C.f_costILCoord.val('0.00');
			formato14C.f_costTotalLCoord.val('0.00');
			
			formato14C.f_canDLSupe.val('0');
			formato14C.f_costDLSupe.val('0.00');
			formato14C.f_canILSupe.val('0');
			formato14C.f_costILSupe.val('0.00');
			formato14C.f_costTotalLSupe.val('0.00');
			
			formato14C.f_canDLGest.val('0');
			formato14C.f_costDLGest.val('0.00');
			formato14C.f_canILGest.val('0');
			formato14C.f_costILGest.val('0.00');
			formato14C.f_costTotalLGest.val('0.00');			
			
			formato14C.f_canDLAsist.val('0');
			formato14C.f_costDLAsist.val('0.00');
			formato14C.f_canILAsist.val('0');
			formato14C.f_costILAsist.val('0.00');
			formato14C.f_costTotalLAsist.val('0.00');
			
			
			//quitamos los componentes deshabilitados
			formato14C.f_empresa.attr("disabled",false);
			formato14C.f_periodoEnvio.attr("disabled",false);
			//
			//formato14C.deshabilitarCampos();
			//
			formato14C.soloNumerosEnteros();
			formato14C.soloNumerosDecimales();
		},		
		//Funcion para CRUD
		<portlet:namespace/>guardarFormato14C : function(){
			if (formato14C.validarFormulario()){
				$.blockUI({ message: formato14C.mensajeGuardando });
				 jQuery.ajax({
					 url: formato14C.urlCrud+'&'+formato14C.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {
						//<portlet:namespace />tipo: formato14C.procesoEstado.val(),
						<portlet:namespace />codEmpresa: formato14C.f_empresa.val(),//codigo empresa
						<portlet:namespace />periodoEnvio: formato14C.f_periodoEnvio.val(),//periodo envio
						//<portlet:namespace />flagPeriodo: formato14C.f_flagPeriodo.val(),
						//<portlet:namespace />anoInicioVigencia: $('#anioInicioVigencia').val(),
						//<portlet:namespace />anoFinVigencia: $('#anioFinVigencia').val(),
						//<portlet:namespace />etapa: formato14C.etapaEdit.val()
						},
					success: function(data) {			
						if (data.resultado == "OK"){				
							var addhtml2='Datos guardados satisfactoriamente';
							formato14C.dialogMessageContent.html(addhtml2);
							formato14C.dialogMessage.dialog("open");
							//---mostrarFormularioModificado();
							formato14C.initBlockUI();
						}
						else if(data.resultado == "Error"){				
							var addhtml2='Se produjo un error al guardar los datos: '+data.mensaje;
							formato14C.dialogMessageContent.html(addhtml2);
							formato14C.dialogMessage.dialog("open");
							//---mostrarUltimoFormato();
							formato14C.initBlockUI();
						}
					},error : function(){
						alert("Error de conexión.");
						formato14C.initBlockUI();
					}
				});
			   	//se deja el formulario activo
				formato14C.divNuevo.hide();
				formato14C.divBuscar.show();
			}
		},	
		//function para validad ingreso de datos
		validarFormulario : function() {		
			if(formato14C.f_empresa.val().length == '' ) { 	
				alert('Seleccione una empresa'); 
				formato14C.f_empresa.focus();
			  	return false; 
			}else if(formato14C.f_periodoEnvio.val().length == '' ) {		  
				alert('Debe ingresar periodo de presentacion');
				formato14C.f_periodoEnvio.focus();
				return false; 
			}
			/*else if( formato14C.f_flagPeriodo.val()=='S' ){
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
			} */
			else if(formato14C.f_nombreSede.val().length == '' ) {		  
				alert('Debe ingresar nombre de la sede');
				formato14C.f_nombreSede.focus();
				return false; 
			}else if(formato14C.f_numRural.val().length == '' ) {		  
				alert('Debe ingresar el numero de beneficiarios de la zona rural');
				formato14C.f_numRural.focus();
				return false; 
			}else if(formato14C.f_numUrbProv.val().length == '' ) {		  
				alert('Debe ingresar el numero de beneficiarios de la zona urbana-provincias');
				formato14C.f_numUrbProv.focus();
				return false; 
			}else if(formato14C.f_numUrbLima.val().length == '' ) {		  
				alert('Debe ingresar el numero de beneficiarios de la zona urbana-lima');
				formato14C.f_numUrbLima.focus();
				return false; 
			}else if(formato14C.f_costoPromRural.val().length == '' ) {		  
				alert('Debe ingresar el costo promedio de la zona rural');
				formato14C.f_costoPromRural.focus();
				return false; 
			}else if(formato14C.f_costoPromUrbProv.val().length == '' ) {		  
				alert('Debe ingresar el costo promedio de la zona urbana-provincias');
				formato14C.f_costoPromUrbProv.focus();
				return false; 
			}else if(formato14C.f_costoPromUrbLima.val().length == '' ) {		  
				alert('Debe ingresar el costo promedio de la zona urbana-lima');
				formato14C.f_costoPromUrbLima.focus();
				return false; 
			}
			/*Detalle*/		
			/*RURAL*/
			else if(formato14C.f_canDRCoord.val().length == '' ) {		  
				alert('Debe ingresar cantidad costo directo coordinador de la zona rural');
				formato14C.f_canDRCoord.focus();
				return false; 
			}else if(formato14C.f_costDRCoord.val().length == '' ) {		  
				alert('Debe ingresar costo directo coordinador de la zona rural');
				formato14C.f_costDRCoord.focus();
				return false; 
			}else if(formato14C.f_canIRCoord.val().length == '' ) {		  
				alert('Debe ingresar cantidad costo indirecto coordinador de la zona rural');
				formato14C.f_canIRCoord.focus();
				return false; 
			}else if(formato14C.f_costIRCoord.val().length == '' ) {		  
				alert('Debe ingresar costo indirecto coordinador de la zona rural');
				formato14C.f_costIRCoord.focus();
				return false; 
			}else if(formato14C.f_canDRSupe.val().length == '' ) {		  
				alert('Debe ingresar cantidad costo directo supervisor de la zona rural');
				formato14C.f_canDRSupe.focus();
				return false; 
			}else if(formato14C.f_costDRSupe.val().length == '' ) {		  
				alert('Debe ingresar costo directo supervisor de la zona rural');
				formato14C.f_costDRSupe.focus();
				return false; 
			}else if(formato14C.f_canIRSupe.val().length == '' ) {		  
				alert('Debe ingresar cantidad costo indirecto supervisor de la zona rural');
				formato14C.f_canIRSupe.focus();
				return false; 
			}else if(formato14C.f_costIRSupe.val().length == '' ) {		  
				alert('Debe ingresar costo indirecto supervisor de la zona rural');
				formato14C.f_costIRSupe.focus();
				return false; 
			}else if(formato14C.f_canDRGest.val().length == '' ) {		  
				alert('Debe ingresar cantidad costo directo gestor de la zona rural');
				formato14C.f_canDRGest.focus();
				return false; 
			}else if(formato14C.f_costDRGest.val().length == '' ) {		  
				alert('Debe ingresar costo directo gestor de la zona rural');
				formato14C.f_costDRGest.focus();
				return false; 
			}else if(formato14C.f_canIRGest.val().length == '' ) {		  
				alert('Debe ingresar cantidad costo indirecto gestor de la zona rural');
				formato14C.f_canIRGest.focus();
				return false; 
			}else if(formato14C.f_costIRGest.val().length == '' ) {		  
				alert('Debe ingresar costo indirecto gestor de la zona rural');
				formato14C.f_costIRGest.focus();
				return false; 
			}else if(formato14C.f_canDRAsist.val().length == '' ) {		  
				alert('Debe ingresar cantidad costo directo asistente de la zona rural');
				formato14C.f_canDRAsist.focus();
				return false; 
			}else if(formato14C.f_costDRAsist.val().length == '' ) {		  
				alert('Debe ingresar  costo directo asistente de la zona rural');
				formato14C.f_costDRAsist.focus();
				return false; 
			}else if(formato14C.f_canIRAsist.val().length == '' ) {		  
				alert('Debe ingresar cantidad costo indirecto asistente de la zona rural');
				formato14C.f_canIRAsist.focus();
				return false; 
			}else if(formato14C.f_costIRAsist.val().length == '' ) {		  
				alert('Debe ingresar  costo indirecto asistente de la zona rural');
				formato14C.f_costIRAsist.focus();
				return false; 
			}				
			/*PROVINCIAS*/
			else if(formato14C.f_canDPCoord.val().length == '' ) {		  
				alert('Debe ingresar  cantidad costo directo coordinador de la zona urbana-provincias');
				formato14C.f_canDPCoord.focus();
				return false; 
			}else if(formato14C.f_costDPCoord.val().length == '' ) {		  
				alert('Debe ingresar  costo directo coordinador de la zona urbana-provincias');
				formato14C.f_costDPCoord.focus();
				return false; 
			}else if(formato14C.f_canIPCoord.val().length == '' ) {		  
				alert('Debe ingresar  cantidad costo indirecto coordinador de la zona urbana-provincias');
				formato14C.f_canIPCoord.focus();
				return false; 
			}else if(formato14C.f_costIPCoord.val().length == '' ) {		  
				alert('Debe ingresar  costo indirecto coordinador de la zona urbana-provincias');
				formato14C.f_costIPCoord.focus();
				return false; 
			}else if(formato14C.f_canDPSupe.val().length == '' ) {		  
				alert('Debe ingresar cantidad costo directo supervisor de la zona urbana-provincias');
				formato14C.f_canDPSupe.focus();
				return false; 
			}else if(formato14C.f_costDPSupe.val().length == '' ) {		  
				alert('Debe ingresar  costo directo supervisor de la zona urbana-provincias');
				formato14C.f_costDPSupe.focus();
				return false; 
			}else if(formato14C.f_canIPSupe.val().length == '' ) {		  
				alert('Debe ingresar cantidad costo indirecto supervisor de la zona urbana-provincias');
				formato14C.f_canIPSupe.focus();
				return false; 
			}else if(formato14C.f_costIPSupe.val().length == '' ) {		  
				alert('Debe ingresar cantidad costo directo supervisor de la zona urbana-provincias');
				formato14C.f_costIPSupe.focus();
				return false; 
			}else if(formato14C.f_canDPGest.val().length == '' ) {		  
				alert('Debe ingresar cantidad costo directo gestor de la zona urbana-provincias');
				formato14C.f_canDPGest.focus();
				return false; 
			}else if(formato14C.f_costDPGest.val().length == '' ) {		  
				alert('Debe ingresar  costo directo gestor de la zona urbana-provincias');
				formato14C.f_costDPGest.focus();
				return false; 
			}else if(formato14C.f_canIPGest.val().length == '' ) {		  
				alert('Debe ingresar cantidad costo indirecto gestor de la zona urbana-provincias');
				formato14C.f_canIPGest.focus();
				return false; 
			}else if(formato14C.f_costIPGest.val().length == '' ) {		  
				alert('Debe ingresar  costo indirecto gestor de la zona urbana-provincias');
				formato14C.f_costIPGest.focus();
				return false; 
			}else if(formato14C.f_canDPAsist.val().length == '' ) {		  
				alert('Debe ingresar cantiad costo directo asistente de la zona urbana-provincias');
				formato14C.f_canDPAsist.focus();
				return false; 
			}else if(formato14C.f_costDPAsist.val().length == '' ) {		  
				alert('Debe ingresar  costo directo asistente de la zona urbana-provincias');
				formato14C.f_costDPAsist.focus();
				return false; 
			}else if(formato14C.f_canIPAsist.val().length == '' ) {		  
				alert('Debe ingresar cantidad costo indirecto asistente de la zona urbana-provincias');
				formato14C.f_canIPAsist.focus();
				return false; 
			}else if(formato14C.f_costIPAsist.val().length == '' ) {		  
				alert('Debe ingresar  costo indirecto asistente de la zona urbana-provincias');
				formato14C.f_costIPAsist.focus();
				return false; 
			}			
			/*LIMA*/
			else if(formato14C.f_canDLCoord.val().length == '' ) {		  
				alert('Debe ingresar cantidad costo directo coordinador de la zona urbana-lima');
				formato14C.f_canDLCoord.focus();
				return false; 
			}else if(formato14C.f_costDLCoord.val().length == '' ) {		  
				alert('Debe ingresar costo directo coordinador de la zona urbana-lima');
				formato14C.f_costDLCoord.focus();
				return false; 
			}else if(formato14C.f_canILCoord.val().length == '' ) {		  
				alert('Debe ingresar cantidad costo indirecto coordinador de la zona urbana-lima');
				formato14C.f_canILCoord.focus();
				return false; 
			}else if(formato14C.f_costILCoord.val().length == '' ) {		  
				alert('Debe ingresar  costo indirecto coordinador de la zona urbana-lima');
				formato14C.f_costILCoord.focus();
				return false; 
			}else if(formato14C.f_canDLSupe.val().length == '' ) {		  
				alert('Debe ingresar cantidad costo directo supervisor de la zona urbana-lima');
				formato14C.f_canDLSupe.focus();
				return false; 
			}else if(formato14C.f_costDLSupe.val().length == '' ) {		  
				alert('Debe ingresar  costo directo supervisor de la zona urbana-lima');
				formato14C.f_costDLSupe.focus();
				return false; 
			}else if(formato14C.f_canILSupe.val().length == '' ) {		  
				alert('Debe ingresar cantidad costo indirecto supervisor de la zona urbana-lima');
				formato14C.f_canILSupe.focus();
				return false; 
			}else if(formato14C.f_costILSupe.val().length == '' ) {		  
				alert('Debe ingresar  costo indirecto supervisor de la zona urbana-lima');
				formato14C.f_costILSupe.focus();
				return false; 
			}else if(formato14C.f_canDLGest.val().length == '' ) {		  
				alert('Debe ingresar cantidad costo directo gestor de la zona urbana-lima');
				formato14C.f_canDLGest.focus();
				return false; 
			}else if(formato14C.f_costDLGest.val().length == '' ) {		  
				alert('Debe ingresar  costo directo gestor de la zona urbana-lima');
				formato14C.f_costDLGest.focus();
				return false; 
			}else if(formato14C.f_canILGest.val().length == '' ) {		  
				alert('Debe ingresar cantidad costo indirecto gestor de la zona urbana-lima');
				formato14C.f_canILGest.focus();
				return false; 
			}else if(formato14C.f_costILGest.val().length == '' ) {		  
				alert('Debe ingresar  costo indirecto gestor de la zona urbana-lima');
				formato14C.f_costILGest.focus();
				return false; 
			}else if(formato14C.f_canDLAsist.val().length == '' ) {		  
				alert('Debe ingresar cantidad costo directo asistente de la zona urbana-lima');
				formato14C.f_canDLAsist.focus();
				return false; 
			}else if(formato14C.f_costDLAsist.val().length == '' ) {		  
				alert('Debe ingresar costo directo asistente de la zona urbana-lima');
				formato14C.f_costDLAsist.focus();
				return false; 
			}else if(formato14C.f_canILAsist.val().length == '' ) {		  
				alert('Debe ingresar cantidad costo indirecto asistente de la zona urbana-lima');
				formato14C.f_canILAsist.focus();
				return false; 
			}else if(formato14C.f_costILAsist.val().length == '' ) {		  
				alert('Debe ingresar  costo indirecto asistente de la zona urbana-lima');
				formato14C.f_costILAsist.focus();
				return false; 
			}else{
				return true; 	
			}			
		},
		//function para validar solo numeros y solo decimales
		
		
		//funcion para regresar
		<portlet:namespace/>regresarF14C : function(){
			//formato14C.procesoEstado.val('');
			//formato14C.etapaEdit.val("");
			formato14C.divNuevo.hide();
			formato14C.divBuscar.show();		
			//
			//formato14C.removerDeshabilitados();
			//se visualizan los componentes escondidos
			formato14C.botonReportePdf.css('display','none');
			formato14C.botonReporteExcel.css('display','none');
			formato14C.botonGrabar.css('display','');
			formato14C.botonValidacion.css('display','');
			formato14C.botonEnvioDefinitivo.css('display','');
			
			//formato14C.panelCargaArchivo.css('display','');

			//formato14C.botonBuscar.trigger('click');
		},
		//DIALOGOS
		initDialogs : function(){	
			formato14C.dialogMessage.dialog({
				modal: true,
				autoOpen: false,
				buttons: {
					Ok: function() {
						$( this ).dialog("close");
					}
				}
			});
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
		}
	};
	
	
	
	
	
	
	
	
</script>