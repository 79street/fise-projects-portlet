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
		
		//valores hidden
		//valores hidden
	    procesoEstado:null,
	    etapaEdit:null,
		
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
		f_costoPromRural:null,f_costoPromUrbProv:null,f_costoPromUrbLima:null,f_numTotal:null,	
		
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
			
			//valores hidden
			this.procesoEstado=$('#<portlet:namespace/>Estado');
		    this.etapaEdit=$('#<portlet:namespace/>etapaEdit');
			
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
			this.f_numTotal=$('#numTotal');	
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
			formato14C.cargaInicial();
			//eventos por defecto
			//formato14C.botonBuscar.trigger('click');
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
		      			view = "<a href='#'><img border='0' title='View' src='/net-theme/images/img-net/file.png'  align='center' onclick=\"formato14C.verFormato14C('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoIniVigencia+"','"+ret.anoFinVigencia+"','"+ret.etapa+"');\" /></a> ";
		      			edit = "<a href='#'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png'  align='center' onclick=\"formato14C.editarFormato14C('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoIniVigencia+"','"+ret.anoFinVigencia+"','"+ret.etapa+"');\" /></a> ";
		      			elim = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"formato14C.confirmarEliminar14C('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoIniVigencia+"','"+ret.anoFinVigencia+"','"+ret.etapa+"');\" /></a> ";              			
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
		//function para la carga inicial del formulario
		cargaInicial : function(){
			 formato14C.inicializarFormulario();			
			//SE CARGA VARIABLES EN SESION PARA MOSTRAR LOS PANELES DE NUEVO O EDICION MANEJADOS
			 /*var codEmpSes = formato14C.codEmpresaSes.val();
			 var anioPresSes = formato14C.anioPresSes.val();
			 var mesPresSes = formato14C.mesPresSes.val();
			 var anioIniVigSes = formato14C.anioIniVigSes.val();
			 var anioFinVigSes = formato14C.anioFinVigSes.val();
			 var etapaSes = formato14C.etapaSes.val();*/
			 //
			 //var flag = formato14C.flag.val();
			/* if( flag=='N' ){//solo ocurre cuando hay un error en la carga de formularios, sino se muestra el proceso normal
				 formato14C.inicializarFormulario();
				 formato14C.mostrarUltimoFormato();
				 formato14C.f_empresa.val(codEmpSes);
				 if( formato14C.f_periodoEnvio.val()=='S' ){
					 $("#anioInicioVigencia").val(anioIniVigSes);
					 $("#anioFinVigencia").val(anioFinVigSes);
				}else{
					$("#anioInicioVigencia").val(anioPresSes);
					 $("#anioFinVigencia").val(anioPresSes);
				}
				formato14C.botonValidacion.css('display','none');
				formato14C.<portlet:namespace/>loadPeriodo(anioPresSes+completarCerosIzq(mesPresSes,2)+etapaSes);
			}else{
				//alert(codEmpSes+','+anioPresSes+','+mesPresSes+','+anioEjeSes+','+mesEjeSes+','+etapaSes);
				 if(codEmpSes != '' && anioPresSes != '' && mesPresSes != '' && anioIniVigSes != '' && anioFinVigSes != '' && etapaSes != ''){
				 	 editarFormato(codEmpSes, anioPresSes, mesPresSes, anioIniVigSes, anioFinVigSes, etapaSes);
				 }
			 }	*/		 
			/* var mensajeInfo = formato14C.mensajeInfo.val();
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
			 }*/
			 //limpiar variables
			/* formato14C.codEmpresaSes.val('');
			 formato14C.anioPresSes.val('');
			 formato14C.mesPresSes.val('');
			 formato14C.anioIniVigSes.val('');
			 formato14C.anioFinVigSes.val('');
			 formato14C.etapaSes.val('');*/
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
			formato14C.inicializarFormulario();
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
			formato14C.<portlet:namespace/>loadPeriodo('');
		},
		//Function para Visualizar los datos del formulario
		verFormato14C : function(codEmpresa,anoPresentacion,mesPresentacion,anoIniVigencia,anoFinVigencia,etapa){	
			$.blockUI({ message: formato14C.mensajeObteniendoDatos });
			jQuery.ajax({
					url: formato14C.urlCrud+'&'+formato14C.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {
					   <portlet:namespace />tipo: "VIEW",
					   <portlet:namespace />codEmpresa: codEmpresa,
					   <portlet:namespace />anioPres: anoPresentacion,
					   <portlet:namespace />mesPres: mesPresentacion,
					   <portlet:namespace />anioInicioVig: anoIniVigencia,
					   <portlet:namespace />anioFinVig: anoFinVigencia,
					   <portlet:namespace />etapa: etapa
						},
					success: function(data) {				
						if (data.resultado == "OK"){
						/*	formato14C.procesoEstado.val("UPDATE");
							formato14C.etapaEdit.val(etapa);
							formato14C.divFormato.show();
							formato14C.divHome.hide();
							dwr.util.removeAllOptions("periodoEnvio");
							dwr.util.addOptions("periodoEnvio", data.periodoEnvio,"codigoItem","descripcionItem");
							formato14C.FillEditformato(data.formato);
							formato14C.deshabilitarCamposView();
							formato14C.initBlockUI();*/
						}
						else{
							alert("Error al recuperar los datos del registro seleccionado");
							formato14C.initBlockUI();
						}
					},error : function(){
						alert("Error de conexión.");
						formato14C.initBlockUI();
					}
			});	
		},
		//Function para editar los datos del formulario
		editarFormato14C : function(codEmpresa,anoPresentacion,mesPresentacion,anoIniVigencia,anoFinVigencia,etapa){	
			$.blockUI({ message: formato14C.mensajeObteniendoDatos });
			jQuery.ajax({
					url: formato14C.urlCrud+'&'+formato14C.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {
					   <portlet:namespace />tipo: "EDITAR",
					   <portlet:namespace />codEmpresa: codEmpresa,
					   <portlet:namespace />anioPres: anoPresentacion,
					   <portlet:namespace />mesPres: mesPresentacion,
					   <portlet:namespace />anioInicioVig: anoIniVigencia,
					   <portlet:namespace />anioFinVig: anoFinVigencia,
					   <portlet:namespace />etapa: etapa
						},
					success: function(data) {				
						if (data.resultado == "OK"){
							formato14C.procesoEstado.val("UPDATE");
							formato14C.etapaEdit.val(etapa);
							formato14C.divNuevo.show();
							formato14C.divBuscar.hide();
							dwr.util.removeAllOptions("periodoEnvio");//id del select(combo) remover sus valores
							dwr.util.addOptions("periodoEnvio", data.periodoEnvio,"codigoItem","descripcionItem");//llena el selec(combo)
							//formato14C.FillEditformato(data.formato);
							formato14C.initBlockUI();
						}
						else{
							alert("Error al recuperar los datos del registro seleccionado");
							formato14C.initBlockUI();
						}
					},error : function(){
						alert("Error de conexión.");
						formato14C.initBlockUI();
					}
			});	
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
			//formato14C.f_costTotalRCoord.val('0.00');
			
			formato14C.f_canDRSupe.val('0');
			formato14C.f_costDRSupe.val('0.00');
			formato14C.f_canIRSupe.val('0');
			formato14C.f_costIRSupe.val('0.00');
			//formato14C.f_costTotalRSupe.val('0.00');
			
			formato14C.f_canDRGest.val('0');
			formato14C.f_costDRGest.val('0.00');
			formato14C.f_canIRGest.val('0');
			formato14C.f_costIRGest.val('0.00');
			//formato14C.f_costTotalRGest.val('0.00');			
			
			formato14C.f_canDRAsist.val('0');
			formato14C.f_costDRAsist.val('0.00');
			formato14C.f_canIRAsist.val('0');
			formato14C.f_costIRAsist.val('0.00');
			//formato14C.f_costTotalRAsist.val('0.00');
			
			//PROVINCIA
			formato14C.f_canDPCoord.val('0');
			formato14C.f_costDPCoord.val('0.00');
			formato14C.f_canIPCoord.val('0');
			formato14C.f_costIPCoord.val('0.00');
			//formato14C.f_costTotalPCoord.val('0.00');
			
			formato14C.f_canDPSupe.val('0');
			formato14C.f_costDPSupe.val('0.00');
			formato14C.f_canIPSupe.val('0');
			formato14C.f_costIPSupe.val('0.00');
			//formato14C.f_costTotalPSupe.val('0.00');
			
			formato14C.f_canDPGest.val('0');
			formato14C.f_costDPGest.val('0.00');
			formato14C.f_canIPGest.val('0');
			formato14C.f_costIPGest.val('0.00');
			//formato14C.f_costTotalPGest.val('0.00');			
			
			formato14C.f_canDPAsist.val('0');
			formato14C.f_costDPAsist.val('0.00');
			formato14C.f_canIPAsist.val('0');
			formato14C.f_costIPAsist.val('0.00');
			//formato14C.f_costTotalPAsist.val('0.00');
			
			//LIMA
			formato14C.f_canDLCoord.val('0');
			formato14C.f_costDLCoord.val('0.00');
			formato14C.f_canILCoord.val('0');
			formato14C.f_costILCoord.val('0.00');
			//formato14C.f_costTotalLCoord.val('0.00');
			
			formato14C.f_canDLSupe.val('0');
			formato14C.f_costDLSupe.val('0.00');
			formato14C.f_canILSupe.val('0');
			formato14C.f_costILSupe.val('0.00');
			//formato14C.f_costTotalLSupe.val('0.00');
			
			formato14C.f_canDLGest.val('0');
			formato14C.f_costDLGest.val('0.00');
			formato14C.f_canILGest.val('0');
			formato14C.f_costILGest.val('0.00');
			//formato14C.f_costTotalLGest.val('0.00');			
			
			formato14C.f_canDLAsist.val('0');
			formato14C.f_costDLAsist.val('0.00');
			formato14C.f_canILAsist.val('0');
			formato14C.f_costILAsist.val('0.00');
			//formato14C.f_costTotalLAsist.val('0.00');
			
			
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
			formato14C.f_numRural.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numRural',7,0)");
			formato14C.f_numUrbProv.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numUrbProv',7,0)");
			formato14C.f_numUrbLima.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numUrbLima',7,0)");
			
			/**detalle**/
			//RURAL
			formato14C.f_canDRCoord.attr("onkeypress","return soloNumerosDecimales(event, 1, 'canDRCoord',7,0)");
			formato14C.f_canIRCoord.attr("onkeypress","return soloNumerosDecimales(event, 1, 'canIRCoord',7,0)");
			
			formato14C.f_canDRSupe.attr("onkeypress","return soloNumerosDecimales(event, 1, 'canDRSupe',7,0)");
			formato14C.f_canIRSupe.attr("onkeypress","return soloNumerosDecimales(event, 1, 'canIRSupe',7,0)");
			
			formato14C.f_canDRGest.attr("onkeypress","return soloNumerosDecimales(event, 1, 'canDRGest',7,0)");
			formato14C.f_canIRGest.attr("onkeypress","return soloNumerosDecimales(event, 1, 'canIRGest',7,0)");
			
			formato14C.f_canDRAsist.attr("onkeypress","return soloNumerosDecimales(event, 1, 'canDRAsist',7,0)");
			formato14C.f_canIRAsist.attr("onkeypress","return soloNumerosDecimales(event, 1, 'canIRAsist',7,0)");
			
			//PROVINCIAS
			formato14C.f_canDPCoord.attr("onkeypress","return soloNumerosDecimales(event, 1, 'canDPCoord',7,0)");
			formato14C.f_canIPCoord.attr("onkeypress","return soloNumerosDecimales(event, 1, 'canIPCoord',7,0)");
			
			formato14C.f_canDPSupe.attr("onkeypress","return soloNumerosDecimales(event, 1, 'canDPSupe',7,0)");
			formato14C.f_canIPSupe.attr("onkeypress","return soloNumerosDecimales(event, 1, 'canIPSupe',7,0)");
			
			formato14C.f_canDPGest.attr("onkeypress","return soloNumerosDecimales(event, 1, 'canDPGest',7,0)");
			formato14C.f_canIPGest.attr("onkeypress","return soloNumerosDecimales(event, 1, 'canIPGest',7,0)");
			
			formato14C.f_canDPAsist.attr("onkeypress","return soloNumerosDecimales(event, 1, 'canDPAsist',7,0)");
			formato14C.f_canIPAsist.attr("onkeypress","return soloNumerosDecimales(event, 1, 'canIPAsist',7,0)");
			
			//LIMA
			formato14C.f_canDLCoord.attr("onkeypress","return soloNumerosDecimales(event, 1, 'canDLCoord',7,0)");
			formato14C.f_canILCoord.attr("onkeypress","return soloNumerosDecimales(event, 1, 'canILCoord',7,0)");
			
			formato14C.f_canDLSupe.attr("onkeypress","return soloNumerosDecimales(event, 1, 'canDLSupe',7,0)");
			formato14C.f_canILSupe.attr("onkeypress","return soloNumerosDecimales(event, 1, 'canILSupe',7,0)");
			
			formato14C.f_canDLGest.attr("onkeypress","return soloNumerosDecimales(event, 1, 'canDLGest',7,0)");
			formato14C.f_canILGest.attr("onkeypress","return soloNumerosDecimales(event, 1, 'canILGest',7,0)");
			
			formato14C.f_canDLAsist.attr("onkeypress","return soloNumerosDecimales(event, 1, 'canDLAsist',7,0)");
			formato14C.f_canILAsist.attr("onkeypress","return soloNumerosDecimales(event, 1, 'canILAsist',7,0)");
			
		},
		//function para validar solo numeros decimales
		soloNumerosDecimales : function(){
			/**cabecera*/
			formato14C.f_costoPromRural.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costoPromRural',7,2)");
			formato14C.f_costoPromUrbProv.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costoPromUrbProv',7,2)");
			formato14C.f_costoPromUrbLima.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costoPromUrbLima',7,2)");
			/**detalle*/
			//RURAL
			formato14C.f_costDRCoord.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costDRCoord',7,2)");
			formato14C.f_costIRCoord.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costIRCoord',7,2)");
			
			formato14C.f_costDRSupe.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costDRSupe',7,2)");
			formato14C.f_costIRSupe.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costIRSupe',7,2)");
			
			formato14C.f_costDRGest.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costDRGest',7,2)");
			formato14C.f_costIRGest.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costIRGest',7,2)");
			
			formato14C.f_costDRAsist.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costDRAsist',7,2)");
			formato14C.f_costIRAsist.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costIRAsist',7,2)");
			
			//PROVINCIA
			formato14C.f_costDPCoord.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costDPCoord',7,2)");
			formato14C.f_costIPCoord.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costIPCoord',7,2)");
			
			formato14C.f_costDPSupe.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costDPSupe',7,2)");
			formato14C.f_costIPSupe.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costIPSupe',7,2)");
			
			formato14C.f_costDPGest.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costDPGest',7,2)");
			formato14C.f_costIPGest.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costIPGest',7,2)");
			
			formato14C.f_costDPAsist.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costDPAsist',7,2)");
			formato14C.f_costIPAsist.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costIPAsist',7,2)");
			
			//LIMA
			formato14C.f_costDLCoord.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costDLCoord',7,2)");
			formato14C.f_costILCoord.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costILCoord',7,2)");
			
			formato14C.f_costDLSupe.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costDLSupe',7,2)");
			formato14C.f_costILSupe.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costILSupe',7,2)");
			
			formato14C.f_costDLGest.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costDLGest',7,2)");
			formato14C.f_costILGest.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costILGest',7,2)");
			
			formato14C.f_costDLAsist.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costDLAsist',7,2)");
			formato14C.f_costILAsist.attr("onkeypress","return soloNumerosDecimales(event, 2, 'costILAsist',7,2)");
		},
		//function para realizar los calculos totales
		calculoTotal : function(){
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
			
			
			//llamando a la funcion para completar decimales si en caso falta completar
			formato14C.formularioCompletarDecimales();
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
			completarDecimal('costoPromRural',2);
			completarDecimal('costoPromUrbProv',2);
			completarDecimal('costoPromUrbLima',2);
			/**detalle*/
			//RURAL
			completarDecimal('costDRCoord',2);
			completarDecimal('costIRCoord',2);
			
			completarDecimal('costTotalRCoord',2);
			
			completarDecimal('costDRSupe',2);
			completarDecimal('costIRSupe',2);
			
			completarDecimal('costTotalRSupe',2);
			
			completarDecimal('costDRGest',2);
			completarDecimal('costIRGest',2);
			
			completarDecimal('costTotalRGest',2);
			
			completarDecimal('costDRAsist',2);
			completarDecimal('costIRAsist',2);	
			
			completarDecimal('costTotalRAsist',2);
			
			//PROVINCIA
			completarDecimal('costDPCoord',2);
			completarDecimal('costIPCoord',2);
			
			completarDecimal('costTotalPCoord',2);
			
			completarDecimal('costDPSupe',2);
			completarDecimal('costIPSupe',2);
			
			completarDecimal('costTotalPSupe',2);
			
			completarDecimal('costDPGest',2);
			completarDecimal('costIPGest',2);
			
			completarDecimal('costTotalPGest',2);
			
			completarDecimal('costDPAsist',2);
			completarDecimal('costIPAsist',2);
			
			completarDecimal('costTotalPAsist',2);
			
			//LIMA
			completarDecimal('costDLCoord',2);
			completarDecimal('costILCoord',2);
			
			completarDecimal('costTotalLCoord',2);
			
			completarDecimal('costDLSupe',2);
			completarDecimal('costILSupe',2);
			
			completarDecimal('costTotalLSupe',2);
			
			completarDecimal('costDLGest',2);
			completarDecimal('costILGest',2);
			
			completarDecimal('costTotalLGest',2);
			
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
						//<portlet:namespace />flagPeriodo: formato14C.f_flagPeriodo.val(),
						//<portlet:namespace />anoInicioVigencia: $('#anioInicioVigencia').val(),
						//<portlet:namespace />anoFinVigencia: $('#anioFinVigencia').val(),						
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