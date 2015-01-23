<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects />

<script type="text/javascript">

var notificarValidar= {
		
	    //decalaracion de los elementos del html	
		formCommand : null,	
		
		//dialogos	
		dialogMessage:null,
		dialogMessageContent:null,
		dialogConfirm:null,//para realizar la notificacion
		dialogConfirmContent:null,//para mostrar la confirmacion al notificar
		dialogConfirmEliminar:null,//para mostrar al eliminar un registro
		dialogObservacion:null,
		dialogMessageEliminar:null,
		dialogConfirmContentEliminar:null,
		dialogObservacion12:null,
		dialogObservacion13:null,
		
		dialogValidacion:null,
		dialogValidacionContent:null,
		dialogError:null,
		dialogErrorContent:null,
		dialogInfo:null,
		dialogInfoContent:null,
		
		//observacion manual
		dialogEliminarObs:null,
		dialogEliminarObsContent:null,
		
		//mensajes		
		mensajeNotificar:null,
		mensajeProcesando:null,
		mensajeEliminando:null,
		
		mensajeGuardando:null,
		mensajeActualizando:null,
		mensajeEliminandoObs:null,
		
		//divs cambios observacion  manual
		divBuscar:null,	
		divDetalle:null,
		divListaObs:null,
		divNuevoObs:null,
		
		//div grillas
		divGrillaF12AB:null,
		divGrillaF12CD:null,
		divGrillaF13A:null,
		divGrillaF14AB:null,
		divGrillaF14C:null,
		//observaciones
		divGrillaObsF14AB:null,//formatos 12A 12B 14A 14B
		divGrillaObsF12CD:null,
		divGrillaObsF13A:null,		
		divGrillaObsF14C:null,
		
		
		
		//urls
		urlBusqueda: null,			
	    urlProcesar:null,
	    urlCargaGrupoInf:null,
	    urlVerObservaciones:null,		
		urlReporteObservaciones:null,
		urlNotificar:null,
		urlEliminar:null,		
		//cambios observacion  manual
		urlBusquedaDetalle: null,			
	    urlBuscarListaObs:null,
	    urlGrabarObs:null,	    
	    urlEliminarObs:null,	   
	    urlActualizarObs:null,	
		
		
		//botones		
		botonBuscar:null,
		botonProcesar:null,
		botonNotificar:null,
		//cambios observacion  manual
		botonAgregarObs:null,
		botonGrabarObs:null,
		botonActualizarObs:null,
		botonRegresarDetalle:null,
		botonRegresarListaObs:null,
		botonRegresarNuevoObs:null,
		
		
		//varibales de busqueda
		i_codEmpresaBusq:null,
		i_grupoInfBusq:null,
		i_etapaBusq:null,
		i_tipoBienal:null,
		i_tipoMensual:null,
		
		//variables 
		f_empresa:null,
		f_descObservacion:null,
		
		//grillas
		tablaResultados:null,
		paginadoResultados:null,	
		tablaObservacion:null,	
		paginadoObservacion:null,
		
		tablaObservacion12:null,	
		paginadoObservacion12:null,
		
		tablaObservacion13:null,	
		paginadoObservacion13:null,
		
		//cambios observacion  manual
		tablaResultadosDetalle:null,
		paginadoResultadosDetalle:null,		
		tablaResultadosObsFomato:null,
		paginadoResultadosObsFormato:null,
		
		
		
		init : function() {
			
			this.formCommand=$('#notificacionBean');	
			
			
			//dialogos
			this.dialogMessage=$("#<portlet:namespace/>dialog-message-notificacion");
			this.dialogMessageContent=$("#<portlet:namespace/>dialog-message-content-notificacion");
			this.dialogConfirm=$("#<portlet:namespace/>dialog-confirm_notificacion");//para notificar
			this.dialogConfirmContent=$("#<portlet:namespace/>dialog-confirm-content_notificacion");//para notificar
			this.dialogObservacion=$("#<portlet:namespace/>dialog-form-observacion");	
			this.dialogObservacion12=$("#<portlet:namespace/>dialog-form-observacion12");	
			this.dialogObservacion13=$("#<portlet:namespace/>dialog-form-observacion13");	
			this.dialogConfirmEliminar=$("#<portlet:namespace/>dialog-confirm");//para elimiar
			this.dialogConfirmContentEliminar=$("#<portlet:namespace/>dialog-confirm-content");//para notificar
			this.dialogMessageEliminar=$("#<portlet:namespace/>dialog-message-notificacion");
			
			this.dialogValidacion=$("#<portlet:namespace/>dialog-alert");	
			this.dialogValidacionContent=$("#<portlet:namespace/>dialog-alert-content");
			this.dialogError=$("#<portlet:namespace/>dialog-error");
			this.dialogErrorContent=$("#<portlet:namespace/>dialog-error-content");	
			this.dialogInfo=$("#<portlet:namespace/>dialog-info");
			this.dialogInfoContent=$("#<portlet:namespace/>dialog-info-content");
			//observacion manual 		
			this.dialogEliminarObs=$("#<portlet:namespace/>dialog-confirm_observacion");
			this.dialogEliminarObsContent=$("#<portlet:namespace/>dialog-confirm-content_observacion");
			
			//divs cambios para observacion manual
			this.divBuscar=$("#<portlet:namespace/>div_buscar"); 
			this.divDetalle=$("#<portlet:namespace/>div_detalle_formato");
			this.divListaObs=$("#<portlet:namespace/>div_detalle_observaciones");
			this.divNuevoObs=$("#<portlet:namespace/>div_nuevo_observacion");
			
			
			//div grillas
			this.divGrillaF12AB=$("#<portlet:namespace/>div_grilla_F12AB"); 
			this.divGrillaF12CD=$("#<portlet:namespace/>div_grilla_F12CD");
			this.divGrillaF13A=$("#<portlet:namespace/>div_grilla_F13A"); 
			this.divGrillaF14AB=$("#<portlet:namespace/>div_grilla_F14AB");
			this.divGrillaF14C=$("#<portlet:namespace/>div_grilla_F14C"); 
			//observaciones
			this.divGrillaObsF14AB=$("#<portlet:namespace/>div_grilla_obs_12AB_14AB");
			this.divGrillaObsF12CD=$("#<portlet:namespace/>div_grilla_obs_F12CD"); 
			this.divGrillaObsF13A=$("#<portlet:namespace/>div_grilla_obs_F13A");
			this.divGrillaObsF14C=$("#<portlet:namespace/>div_grilla_obs_F14C"); 
			
			
			//mensajes						
			this.mensajeNotificar='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Procesando Notificación </h3>';			
			this.mensajeProcesando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Procesando Datos </h3>';
			this.mensajeEliminando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Excluyendo </h3>';
			
			//this.mensajeEliminando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Eliminando </h3>';
			this.mensajeGuardando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Guardando Datos </h3>';
			this.mensajeActualizando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Actualizando Datos </h3>';
			this.mensajeEliminandoObs='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Eliminando </h3>';
			
			
			//urls
			this.urlBusqueda='<portlet:resourceURL id="busquedaNotificacion" />';					
			this.urlProcesar='<portlet:resourceURL id="procesarNotificacion" />';
			this.urlCargaGrupoInf='<portlet:resourceURL id="cargarGrupoInformacion" />';
			this.urlVerObservaciones='<portlet:resourceURL id="verObservacionesValidacion" />';
			this.urlReporteObservaciones='<portlet:resourceURL id="reporteValidacionNotificacion" />';
			this.urlNotificar='<portlet:resourceURL id="notificarValidacion" />';
			this.urlEliminar='<portlet:resourceURL id="eliminarNotificacion" />';
			
			//cambios pra observaciones manuales
			this.urlBusquedaDetalle='<portlet:resourceURL id="verDetalleFormatos" />';
			this.urlBuscarListaObs='<portlet:resourceURL id="verListaObservacionesDetalle" />';
			this.urlGrabarObs='<portlet:resourceURL id="grabarObservacionManual" />';
			this.urlEliminarObs='<portlet:resourceURL id="eliminarObservacionManual" />';			
			this.urlActualizarObs='<portlet:resourceURL id="actualizarObservacionManual" />';	
			
			
			
			//botones
			this.botonBuscar=$("#<portlet:namespace/>btnBuscarNotificacion");	
			this.botonProcesar=$("#<portlet:namespace/>btnProcesarNotificacion");	
			this.botonNotificar=$("#<portlet:namespace/>btnNotificarValidar");				
			
			//cambios pra observaciones manuales
			this.botonAgregarObs=$("#<portlet:namespace/>btnNuevoObservacion");	
			this.botonGrabarObs=$("#<portlet:namespace/>btnGrabarObservacion");	
			this.botonActualizarObs=$("#<portlet:namespace/>btnActualizarObservacion");			
			this.botonRegresarDetalle=$("#<portlet:namespace/>btnRegresarBusqueda");	
			this.botonRegresarListaObs=$("#<portlet:namespace/>btnRegresarDetalle");	
			this.botonRegresarNuevoObs=$("#<portlet:namespace/>btnRegresarObs");
			
			
			//variables de busqueda
			this.i_codEmpresaBusq=$('#codEmpresaBusq');
			this.i_grupoInfBusq=$('#grupoInfBusq');	
			this.i_etapaBusq=$('#etapaBusq');	
			this.i_tipoBienal=$('#rbtBienal');
			this.i_tipoMensual=$('#rbtMensual');
			
			this.f_descObservacion=$('#desObservacion');
			
			
			//grillas			
			this.tablaResultados=$("#<portlet:namespace/>grid_resultado_busqueda");
			this.paginadoResultados='#<portlet:namespace/>paginador_resultado_busqueda';
			this.buildGrids();	//cargar el modelo de la grilla			
			
			this.tablaObservacion=$("#<portlet:namespace/>grid_observacion");			
			this.paginadoObservacion='#<portlet:namespace/>pager_observacion';
			this.buildGridsObservacion();
			
			this.tablaObservacion12=$("#<portlet:namespace/>grid_observacion12");			
			this.paginadoObservacion12='#<portlet:namespace/>pager_observacion12';		
			this.buildGridsObservacion12();
			
			this.tablaObservacion13=$("#<portlet:namespace/>grid_observacion13");			
			this.paginadoObservacion13='#<portlet:namespace/>pager_observacion13';		
			this.buildGridsObservacion13();
			
			//cambios observacion  manual
			this.tablaResultadosDetalleF12AB=$("#<portlet:namespace/>grid_resultado_busqueda_detalle_12AB");
			this.paginadoResultadosDetalleF12AB='#<portlet:namespace/>paginador_resultado_busqueda_detalle_12AB';
			this.buildGridsDetalleF12AB();	//cargar el modelo de la grilla	
			
			this.tablaResultadosDetalleF12CD=$("#<portlet:namespace/>grid_resultado_busqueda_detalle_12CD");
			this.paginadoResultadosDetalleF12CD='#<portlet:namespace/>paginador_resultado_busqueda_detalle_12CD';
			this.buildGridsDetalleF12CD();	//cargar el modelo de la grilla	
			
			
			this.tablaResultadosDetalleF13A=$("#<portlet:namespace/>grid_resultado_busqueda_detalle_13A");
			this.paginadoResultadosDetalleF13A='#<portlet:namespace/>paginador_resultado_busqueda_detalle_13A';
			this.buildGridsDetalleF13A();	//cargar el modelo de la grilla	
			
			this.tablaResultadosDetalleF14AB=$("#<portlet:namespace/>grid_resultado_busqueda_detalle_14AB");
			this.paginadoResultadosDetalleF14AB='#<portlet:namespace/>paginador_resultado_busqueda_detalle_14AB';
			this.buildGridsDetalleF14AB();	//cargar el modelo de la grilla	
			
			this.tablaResultadosDetalleF14C=$("#<portlet:namespace/>grid_resultado_busqueda_detalle_14C");
			this.paginadoResultadosDetalleF14C='#<portlet:namespace/>paginador_resultado_busqueda_detalle_14C';
			this.buildGridsDetalleF14C();	//cargar el modelo de la grilla	
			
			//observaciones ingreso manuales
			this.tablaObsF12ABF14AB=$("#<portlet:namespace/>grid_resultado_obs_12AB_14AB");
			this.paginadoObsF12ABF14AB='#<portlet:namespace/>paginador_resultado_obs_12AB_14AB';
			this.buildGridsObsF12ABF14AB();	//cargar el modelo de la grilla			
			
			this.tablaObsF12CD=$("#<portlet:namespace/>grid_resultado_obs_12CD");			
			this.paginadoObsF12CD='#<portlet:namespace/>paginador_resultado_obs_12CD';
			this.buildGridsObsF12CD();
			
			this.tablaObsF13A=$("#<portlet:namespace/>grid_resultado_obs_13A");			
			this.paginadoObsF13A='#<portlet:namespace/>paginador_resultado_obs_13A';		
			this.buildGridsObsF13A();
			
			this.tablaObsF14C=$("#<portlet:namespace/>grid_resultado_obs_14C");			
			this.paginadoObsF14C='#<portlet:namespace/>paginador_resultado_obs_14C';		
			this.buildGridsObsF14C();
			
					
			
			//llamado a la funciones de cada boton
			notificarValidar.botonBuscar.click(function() {			
				notificarValidar.buscarNotificacion();
			});
			
			notificarValidar.botonProcesar.click(function() {			
				notificarValidar.procesarNotificacion();
			});
			
			notificarValidar.botonNotificar.click(function() {			
				notificarValidar.confirmarNotificacion();
			});
			
			//cambios para observacion manual
			notificarValidar.botonRegresarDetalle.click(function() {
				notificarValidar.<portlet:namespace/>regresarBusqueda();
		    });	
			
			notificarValidar.botonRegresarListaObs.click(function() {
				notificarValidar.<portlet:namespace/>regresarDetalle();
		    });				
			
			notificarValidar.botonAgregarObs.click(function() {
				notificarValidar.<portlet:namespace/>agregarObservacion();
		    });	
			
			notificarValidar.botonRegresarNuevoObs.click(function() {
				notificarValidar.<portlet:namespace/>regresarListaObs();
		    });	
			
			notificarValidar.botonGrabarObs.click(function() {
				notificarValidar.<portlet:namespace/>guardarObsManual();
		    });	
			
			notificarValidar.botonActualizarObs.click(function() {
				notificarValidar.<portlet:namespace/>actualizarObsManual();
		    });		
			
			
			notificarValidar.initDialogs();
		    
		    
			notificarValidar.i_tipoBienal.change(function(){
				notificarValidar.<portlet:namespace/>loadGrupoInformacion();
			});
			
			notificarValidar.i_tipoMensual.change(function(){
				notificarValidar.<portlet:namespace/>loadGrupoInformacion();
			});
		    
		    //eventos por defecto			    
			notificarValidar.botonBuscar.trigger('click');
		    notificarValidar.initBlockUI();
		},
		
		
		//funcion para armar el modelo de la grilla para el resultado
		buildGrids : function () {	
			notificarValidar.tablaResultados.jqGrid({
			   datatype: "local",
		       colNames: ['Dist. Eléct.','Formato.','Año Decl.','Mes Decl.','Año Ejec.','Mes Ejec.','Año Ini. Vig.','Año Fin Vig.','Etapa','Ver','Excluir','Obs. Manual','','',''],
		       colModel: [
                       { name: 'desEmpresa', index: 'desEmpresa', width: 50},				   
					   { name: 'formato', index: 'formato', width: 20,align:'center'},
		               { name: 'anioPres', index: 'anioPres', width: 20,align:'center' },  
		               { name: 'desMes', index: 'desMes', width: 30,align:'center'},
		               { name: 'anioEjec', index: 'anioEjec', width: 20,align:'center' },   
		               { name: 'desMesEje', index: 'desMesEje', width: 30,align:'center'},
		               { name: 'anioIniVig', index: 'anioIniVig', width: 20,align:'center' },   
		               { name: 'anioFinVig', index: 'anioFinVig', width: 20,align:'center'},
		               { name: 'etapa', index: 'etapa', width: 30},	  	              
		              /*  { name: 'notificar', index: 'notificar', width: 20,align:'center' }, */
		               { name: 'ver', index: 'ver', width: 20,align:'center' },
		               { name: 'elim', index: 'elim', width: 20,align:'center' },
		               { name: 'obs', index: 'obs', width: 30,align:'center' },	
		               { name: 'codEmpresa', index: 'codEmpresa', hidden: true},
		               { name: 'mesPres', index: 'mesPres', hidden: true},             
		               { name: 'mesEjec', index: 'mesEjec', hidden: true}
		               
			   	    ],
			   	 multiselect: false,
					rowNum:10,
				   	rowList:[10,20,50],
					height: 225,
				   	autowidth: true,
					rownumbers: true,
					shrinkToFit:true,
					pager: notificarValidar.paginadoResultados,
				    viewrecords: true,
				   	caption: "Resultado(s) de la búsqueda",
				    sortorder: "asc",	   	    	   	   
		       gridComplete: function(){
		      		var ids = notificarValidar.tablaResultados.jqGrid('getDataIDs');
		      		for(var i=0;i < ids.length;i++){
		      			var cl = ids[i];
		      			var ret = notificarValidar.tablaResultados.jqGrid('getRowData',cl);	      			
		      			/* notificar = "<a href='#'><img border='0' title='Notificar' src='/net-theme/images/img-net/lock.png' align='center' onclick=\"notificar.confirmarnotificar('"+ret.codEmpresa+"','"+ret.anioPres+"','"+ret.mesPres+"');\" /></a> "; */
		      			ver = "<a href='#'><img border='0' title='Ver' src='/net-theme/images/img-net/file.png' align='center' onclick=\"notificarValidar.verObservaciones('"+ret.codEmpresa+"','"+ret.anioPres+"','"+ret.mesPres+"','"+ret.anioEjec+"','"+ret.mesEjec+"','"+ret.anioIniVig+"','"+ret.anioFinVig+"','"+ret.etapa+"','"+ret.formato+"');\" /></a> ";
		      			elim = "<a href='#'><img border='0' title='Excluir' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"notificarValidar.confirmarEliminarNotificacion('"+ret.codEmpresa+"','"+ret.anioPres+"','"+ret.mesPres+"','"+ret.anioEjec+"','"+ret.mesEjec+"','"+ret.anioIniVig+"','"+ret.anioFinVig+"','"+ret.etapa+"','"+ret.formato+"');\" /></a> ";
		      			obs = "<a href='#'><img border='0' title='Obs. de Ingreso Manual' src='/net-theme/images/img-net/form-edit.png'  align='center' onclick=\"notificarValidar.mostrarDetalleFormato('"+ret.codEmpresa+"','"+ret.anioPres+"','"+ret.mesPres+"','"+ret.anioEjec+"','"+ret.mesEjec+"','"+ret.anioIniVig+"','"+ret.anioFinVig+"','"+ret.etapa+"','"+ret.formato+"','"+ret.desEmpresa+"','"+ret.desMes+"','"+ret.desMesEje+"');\" /></a> ";
		      			/* notificarValidar.tablaResultados.jqGrid('setRowData',ids[i],{notificar:notificar}); */
		      			notificarValidar.tablaResultados.jqGrid('setRowData',ids[i],{ver:ver});
		      			notificarValidar.tablaResultados.jqGrid('setRowData',ids[i],{elim:elim});
		      			notificarValidar.tablaResultados.jqGrid('setRowData',ids[i],{obs:obs});
		      		}
		      }
		  	});
			notificarValidar.tablaResultados.jqGrid('navGrid',notificarValidar.paginadoResultados,{add:false,edit:false,del:false,search: false,refresh: false});				
		},
		
		//Modelo de la grilla para mostrar Observaciones formatos 12A,12B,14A,14B y 14C
		buildGridsObservacion : function () {	
		   notificarValidar.tablaObservacion.jqGrid({
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
				pager: notificarValidar.paginadoObservacion,
			    viewrecords: true,
			   	//caption: "Formatos",
			    sortorder: "asc"
		  	});
		    notificarValidar.tablaObservacion.jqGrid('navGrid',notificarValidar.paginadoObservacion,{add:false,edit:false,del:false,search: false,refresh: false});	
		    notificarValidar.tablaObservacion.jqGrid('navButtonAdd',notificarValidar.paginadoObservacion,{
			       caption:"Exportar a Excel",
			       buttonicon: "ui-icon-bookmark",
			       onClickButton : function () {
			            location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>'; 
			       } 
			}); 
		    notificarValidar.tablaObservacion.jqGrid('navButtonAdd',notificarValidar.paginadoObservacion,{
			       caption:"Exportar a Pdf",
			       buttonicon: "ui-icon-bookmark",
			       onClickButton : function () {
			    	   notificarValidar.<portlet:namespace/>mostrarReporteObservaciones();
			       } 
			});
		},
		
		//Modelo de la grilla para mostrar Observaciones formatos 12C,12D
		buildGridsObservacion12 : function () {	
		   notificarValidar.tablaObservacion12.jqGrid({
		   datatype: "local",
		   colNames: ['Etapa ejecución','Nro. item etapa','Código','Descripción'],
	       colModel: [
						{ name: 'descEtapaEjecucion', index: 'descEtapaEjecucion', width: 100 ,align: 'left'},
						{ name: 'nroItemEtapa', index: 'nroItemEtapa', width: 75 ,align: 'left'},
						{ name: 'codigo', index: 'codigo', width: 50 ,align: 'center'},
		                { name: 'descripcion', index: 'descripcion', width: 430 ,align: 'left'}               
			   	    ],
		   	 multiselect: false,
				rowNum:10,
			   	rowList:[10,20,50],
				height: 'auto',
			   	autowidth: true,
				rownumbers: true,
				//shrinkToFit:true,
				pager: notificarValidar.paginadoObservacion12,
			    viewrecords: true,
			   	//caption: "Formatos",
			    sortorder: "asc"
		  	});
		    notificarValidar.tablaObservacion12.jqGrid('navGrid',notificarValidar.paginadoObservacion12,{add:false,edit:false,del:false,search: false,refresh: false});	
		    notificarValidar.tablaObservacion12.jqGrid('navButtonAdd',notificarValidar.paginadoObservacion12,{
			       caption:"Exportar a Excel",
			       buttonicon: "ui-icon-bookmark",
			       onClickButton : function () {
			            location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>'; 
			       } 
			}); 
		    notificarValidar.tablaObservacion12.jqGrid('navButtonAdd',notificarValidar.paginadoObservacion12,{
			       caption:"Exportar a Pdf",
			       buttonicon: "ui-icon-bookmark",
			       onClickButton : function () {
			    	   notificarValidar.<portlet:namespace/>mostrarReporteObservaciones();
			       } 
			});
		},
		
		//Modelo de la grilla para mostrar Observaciones formatos 13A
		buildGridsObservacion13 : function () {	
		   notificarValidar.tablaObservacion13.jqGrid({
		   datatype: "local",
		   colNames: ['Sector Típico','Grupo Zona','Código','Descripción'],
	       colModel: [
						{ name: 'descSectorTipico', index: 'descSectorTipico', width: 80 ,align: 'left'},
						{ name: 'descZonaBenef', index: 'descZonaBenef', width: 100 ,align: 'left'},
						{ name: 'codigo', index: 'codigo', width: 50 ,align: 'center'},
		                { name: 'descripcion', index: 'descripcion', width: 430 ,align: 'left'}               
			   	    ],
		   	 multiselect: false,
				rowNum:10,
			   	rowList:[10,20,50],
				height: 'auto',
			   	autowidth: true,
				rownumbers: true,
				//shrinkToFit:true,
				pager: notificarValidar.paginadoObservacion13,
			    viewrecords: true,
			   	//caption: "Formatos",
			    sortorder: "asc"
		  	});
		    notificarValidar.tablaObservacion13.jqGrid('navGrid',notificarValidar.paginadoObservacion13,{add:false,edit:false,del:false,search: false,refresh: false});	
		    notificarValidar.tablaObservacion13.jqGrid('navButtonAdd',notificarValidar.paginadoObservacion13,{
			       caption:"Exportar a Excel",
			       buttonicon: "ui-icon-bookmark",
			       onClickButton : function () {
			            location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>'; 
			       } 
			}); 
		    notificarValidar.tablaObservacion13.jqGrid('navButtonAdd',notificarValidar.paginadoObservacion13,{
			       caption:"Exportar a Pdf",
			       buttonicon: "ui-icon-bookmark",
			       onClickButton : function () {
			    	   notificarValidar.<portlet:namespace/>mostrarReporteObservaciones();
			       } 
			});
		},
		
		
		/*cambios para ingresar observaciones manuales**/
		
		//funcion para armar el modelo de la grilla para el resultado de detalle del formato
		buildGridsDetalleF12AB : function () {
			var ancho = notificarValidar.divBuscar.width();
			notificarValidar.tablaResultadosDetalleF12AB.jqGrid({
			   datatype: "local",
		       colNames: ['Dist. Eléct.','Año Decl.','Mes Decl.','Año Ejec.','Mes Ejec.','Etapa','Zona','Agregar Obs. Manual','','','',''],
		       colModel: [
                       { name: 'desEmpresa', index: 'desEmpresa', width: 50},					  
		               { name: 'anioPres', index: 'anioPres', width: 20,align:'center' },  
		               { name: 'desMes', index: 'desMes', width: 30,align:'center'},
		               { name: 'anioEjec', index: 'anioEjec', width: 20,align:'center' },   
		               { name: 'desMesEje', index: 'desMesEje', width: 30,align:'center'},		              
		               { name: 'etapa', index: 'etapa', width: 30},	 
		               { name: 'desZona', index: 'desZona', width: 30},	   
		               { name: 'agregarObs', index: 'agregarObs', width: 30,align:'center' },	
		               { name: 'codEmpresa', index: 'codEmpresa', hidden: true},
		               { name: 'mesPres', index: 'mesPres', hidden: true},             
		               { name: 'mesEjec', index: 'mesEjec', hidden: true},
		               { name: 'idZona', index: 'idZona', hidden: true}
			   	    ],
			   	 multiselect: false,
					rowNum:10,
				   	rowList:[10,20,50],
					height: 225,
				   //	autowidth: true,
					rownumbers: true,
					//shrinkToFit:true,
					width:ancho ,
					pager: notificarValidar.paginadoResultadosDetalleF12AB,
				    viewrecords: true,
				   	caption: "Resultado(s) del detalle del Formato seleccionado.",
				    sortorder: "asc",	   	    	   	   
		       gridComplete: function(){
		      		var ids = notificarValidar.tablaResultadosDetalleF12AB.jqGrid('getDataIDs');
		      		for(var i=0;i < ids.length;i++){
		      			var cl = ids[i];
		      			var ret = notificarValidar.tablaResultadosDetalleF12AB.jqGrid('getRowData',cl);	      			
		      			agregarObs = "<a href='#'><img border='0' title='Agregar Obs. Manuales' src='/net-theme/images/img-net/file-add.png'  align='center' onclick=\"notificarValidar.buscarObsF12AB('"+ret.codEmpresa+"','"+ret.anioPres+"','"+ret.mesPres+"','"+ret.anioEjec+"','"+ret.mesEjec+"','"+ret.etapa+"','"+ret.idZona+"');\" /></a> ";
		      			notificarValidar.tablaResultadosDetalleF12AB.jqGrid('setRowData',ids[i],{agregarObs:agregarObs});
		      		}
		      }
		  	});
			notificarValidar.tablaResultadosDetalleF12AB.jqGrid('navGrid',notificarValidar.paginadoResultadosDetalleF12AB,{add:false,edit:false,del:false,search: false,refresh: false});				
		},
		
		buildGridsDetalleF12CD : function () {
			var ancho = notificarValidar.divBuscar.width();
			notificarValidar.tablaResultadosDetalleF12CD.jqGrid({
			   datatype: "local",
		       colNames: ['Dist. Eléct.','Año Decl.','Mes Decl.','Etapa','Año Ejec.','Mes Ejec.','Etapa Ejec.','Num. Item Etapa.','Agregar Obs. Manual','','','',''],
		       colModel: [
                       { name: 'desEmpresa', index: 'desEmpresa', width: 50},					  
		               { name: 'anioPres', index: 'anioPres', width: 20,align:'center' },  
		               { name: 'desMes', index: 'desMes', width: 30,align:'center'},
		               { name: 'etapa', index: 'etapa', width: 30},	 
		               { name: 'anioEjec', index: 'anioEjec', width: 20,align:'center' },   
		               { name: 'desMesEje', index: 'desMesEje', width: 30,align:'center'},
		               { name: 'desEstapaEjec', index: 'desEstapaEjec', width: 20,align:'center' },   
		               { name: 'itemEtapa', index: 'itemEtapa', width: 20,align:'center'},              
		               { name: 'agregarObs', index: 'agregarObs', width: 30,align:'center' },	
		               { name: 'codEmpresa', index: 'codEmpresa', hidden: true},
		               { name: 'mesPres', index: 'mesPres', hidden: true},             
		               { name: 'mesEjec', index: 'mesEjec', hidden: true},
		               { name: 'etapaEjec', index: 'etapaEjec', hidden: true}	
			   	    ],
			   	 multiselect: false,
					rowNum:10,
				   	rowList:[10,20,50],
					height: 225,
				   //	autowidth: true,
					rownumbers: true,
					//shrinkToFit:true,
					width:ancho ,
					pager: notificarValidar.paginadoResultadosDetalleF12CD,
				    viewrecords: true,
					caption: "Resultado(s) del detalle del Formato seleccionado.",
				    sortorder: "asc",	   	    	   	   
		       gridComplete: function(){
		      		var ids = notificarValidar.tablaResultadosDetalleF12CD.jqGrid('getDataIDs');
		      		for(var i=0;i < ids.length;i++){
		      			var cl = ids[i];
		      			var ret = notificarValidar.tablaResultadosDetalleF12CD.jqGrid('getRowData',cl);	      			
		      			agregarObs = "<a href='#'><img border='0' title='Agregar Obs. Manuales' src='/net-theme/images/img-net/file-add.png'  align='center' onclick=\"notificarValidar.buscarObsF12CD('"+ret.codEmpresa+"','"+ret.anioPres+"','"+ret.mesPres+"','"+ret.anioEjec+"','"+ret.mesEjec+"','"+ret.etapa+"','"+ret.etapaEjec+"','"+ret.itemEtapa+"');\" /></a> ";
		      			notificarValidar.tablaResultadosDetalleF12CD.jqGrid('setRowData',ids[i],{agregarObs:agregarObs});
		      		}
		      }
		  	});
			notificarValidar.tablaResultadosDetalleF12CD.jqGrid('navGrid',notificarValidar.paginadoResultadosDetalleF12CD,{add:false,edit:false,del:false,search: false,refresh: false});				
		},
		
		buildGridsDetalleF13A : function () {
			var ancho = notificarValidar.divBuscar.width();
			notificarValidar.tablaResultadosDetalleF13A.jqGrid({
			   datatype: "local",
		       colNames: ['Dist. Eléct.','Año Decl.','Mes Decl.','Cod. Ubigeo','Sector Típico','Zona','Etapa','Agregar Obs. Manual','','','',''],
		       colModel: [
                       { name: 'desEmpresa', index: 'desEmpresa', width: 50},					  
		               { name: 'anioPres', index: 'anioPres', width: 20,align:'center' },  
		               { name: 'desMes', index: 'desMes', width: 30,align:'center'},
		               { name: 'codUbigeo', index: 'codUbigeo', width: 20,align:'center' },   
		               { name: 'desSector', index: 'desSector', width: 30,align:'center'},
		               { name: 'desZona', index: 'desZona', width: 30},	   
		               { name: 'etapa', index: 'etapa', width: 30},		               
		               { name: 'agregarObs', index: 'agregarObs', width: 30,align:'center' },	
		               { name: 'codEmpresa', index: 'codEmpresa', hidden: true},
		               { name: 'mesPres', index: 'mesPres', hidden: true},             
		               { name: 'codSector', index: 'codSector', hidden: true},
		               { name: 'idZona', index: 'idZona', hidden: true}	
			   	    ],
			   	 multiselect: false,
					rowNum:10,
				   	rowList:[10,20,50],
					height: 225,
				   //	autowidth: true,
					rownumbers: true,
					//shrinkToFit:true,
					width:ancho ,
					pager: notificarValidar.paginadoResultadosDetalleF13A,
				    viewrecords: true,
					caption: "Resultado(s) del detalle del Formato seleccionado.",
				    sortorder: "asc",	   	    	   	   
		       gridComplete: function(){
		      		var ids = notificarValidar.tablaResultadosDetalleF13A.jqGrid('getDataIDs');
		      		for(var i=0;i < ids.length;i++){
		      			var cl = ids[i];
		      			var ret = notificarValidar.tablaResultadosDetalleF13A.jqGrid('getRowData',cl);	      			
		      			agregarObs = "<a href='#'><img border='0' title='Agregar Obs. Manuales' src='/net-theme/images/img-net/file-add.png'  align='center' onclick=\"notificarValidar.buscarObsF13A('"+ret.codEmpresa+"','"+ret.anioPres+"','"+ret.mesPres+"','"+ret.etapa+"','"+ret.codUbigeo+"','"+ret.codSector+"','"+ret.idZona+"');\" /></a> ";
		      			notificarValidar.tablaResultadosDetalleF13A.jqGrid('setRowData',ids[i],{agregarObs:agregarObs});
		      		}
		      }
		  	});
			notificarValidar.tablaResultadosDetalleF13A.jqGrid('navGrid',notificarValidar.paginadoResultadosDetalleF13A,{add:false,edit:false,del:false,search: false,refresh: false});				
		},
		
		buildGridsDetalleF14AB : function () {
			var ancho = notificarValidar.divBuscar.width();
			notificarValidar.tablaResultadosDetalleF14AB.jqGrid({
			   datatype: "local",
		       colNames: ['Dist. Eléct.','Año Decl.','Mes Decl.','Año Ini. Vig.','Año Fin Vig.','Etapa','Zona','Agregar Obs. Manual','','',''],
		       colModel: [
                       { name: 'desEmpresa', index: 'desEmpresa', width: 50},					  
		               { name: 'anioPres', index: 'anioPres', width: 20,align:'center' },  
		               { name: 'desMes', index: 'desMes', width: 30,align:'center'},		              
		               { name: 'anioIniVig', index: 'anioIniVig', width: 20,align:'center' },   
		               { name: 'anioFinVig', index: 'anioFinVig', width: 20,align:'center'},
		               { name: 'etapa', index: 'etapa', width: 30},	 
		               { name: 'desZona', index: 'desZona', width: 30},	   
		               { name: 'agregarObs', index: 'agregarObs', width: 30,align:'center' },	
		               { name: 'codEmpresa', index: 'codEmpresa', hidden: true},
		               { name: 'mesPres', index: 'mesPres', hidden: true},             
		               { name: 'idZona', index: 'idZona', hidden: true}		               
			   	    ],
			   	 multiselect: false,
					rowNum:10,
				   	rowList:[10,20,50],
					height: 225,
				   //	autowidth: true,
					rownumbers: true,
					//shrinkToFit:true,
					width:ancho ,
					pager: notificarValidar.paginadoResultadosDetalleF14AB,
				    viewrecords: true,
				    caption: "Resultado(s) del detalle del Formato seleccionado.",
				    sortorder: "asc",	   	    	   	   
		       gridComplete: function(){
		      		var ids = notificarValidar.tablaResultadosDetalleF14AB.jqGrid('getDataIDs');
		      		for(var i=0;i < ids.length;i++){
		      			var cl = ids[i];
		      			var ret = notificarValidar.tablaResultadosDetalleF14AB.jqGrid('getRowData',cl);	      			
		      			agregarObs = "<a href='#'><img border='0' title='Agregar Obs. Manuales' src='/net-theme/images/img-net/file-add.png'  align='center' onclick=\"notificarValidar.buscarObsF14AB('"+ret.codEmpresa+"','"+ret.anioPres+"','"+ret.mesPres+"','"+ret.anioIniVig+"','"+ret.anioFinVig+"','"+ret.etapa+"','"+ret.idZona+"');\" /></a> ";
		      			notificarValidar.tablaResultadosDetalleF14AB.jqGrid('setRowData',ids[i],{agregarObs:agregarObs});
		      		}
		      }
		  	});
			notificarValidar.tablaResultadosDetalleF14AB.jqGrid('navGrid',notificarValidar.paginadoResultadosDetalleF14AB,{add:false,edit:false,del:false,search: false,refresh: false});				
		},
		
		buildGridsDetalleF14C : function () {
			var ancho = notificarValidar.divBuscar.width();
			notificarValidar.tablaResultadosDetalleF14C.jqGrid({
			   datatype: "local",
		       colNames: ['Dist. Eléct.','Año Decl.','Mes Decl.','Año Ini. Vig.','Año Fin Vig.','Etapa','Zona','Tipo Personal','Agregar Obs. Manual','','','',''],
		       colModel: [
                       { name: 'desEmpresa', index: 'desEmpresa', width: 50},					  
		               { name: 'anioPres', index: 'anioPres', width: 20,align:'center' },  
		               { name: 'desMes', index: 'desMes', width: 30,align:'center'},
		               { name: 'anioIniVig', index: 'anioIniVig', width: 20,align:'center' },   
		               { name: 'anioFinVig', index: 'anioFinVig', width: 20,align:'center'},
		               { name: 'etapa', index: 'etapa', width: 30},	 
		               { name: 'desZona', index: 'desZona', width: 30},	
		               { name: 'desPersonal', index: 'desPersonal', width: 30,align:'center' },   
		               { name: 'agregarObs', index: 'agregarObs', width: 30,align:'center' },	
		               { name: 'codEmpresa', index: 'codEmpresa', hidden: true},
		               { name: 'mesPres', index: 'mesPres', hidden: true},             
		               { name: 'idZona', index: 'idZona', hidden: true},
		               { name: 'idPersonal', index: 'idPersonal', hidden: true}
		               
			   	    ],
			   	 multiselect: false,
					rowNum:10,
				   	rowList:[10,20,50],
					height: 225,
				   //	autowidth: true,
					rownumbers: true,
					//shrinkToFit:true,
					width:ancho ,
					pager: notificarValidar.paginadoResultadosDetalleF14C,
				    viewrecords: true,
				    caption: "Resultado(s) del detalle del Formato seleccionado.",
				    sortorder: "asc",	   	    	   	   
		       gridComplete: function(){
		      		var ids = notificarValidar.tablaResultadosDetalleF14C.jqGrid('getDataIDs');
		      		for(var i=0;i < ids.length;i++){
		      			var cl = ids[i];
		      			var ret = notificarValidar.tablaResultadosDetalleF14C.jqGrid('getRowData',cl);	      			
		      			agregarObs = "<a href='#'><img border='0' title='Agregar Obs. Manuales' src='/net-theme/images/img-net/file-add.png'  align='center' onclick=\"notificarValidar.buscarObsF14C('"+ret.codEmpresa+"','"+ret.anioPres+"','"+ret.mesPres+"','"+ret.anioIniVig+"','"+ret.anioFinVig+"','"+ret.etapa+"','"+ret.idZona+"','"+ret.idPersonal+"');\" /></a> ";
		      			notificarValidar.tablaResultadosDetalleF14C.jqGrid('setRowData',ids[i],{agregarObs:agregarObs});
		      		}
		      }
		  	});
			notificarValidar.tablaResultadosDetalleF14C.jqGrid('navGrid',notificarValidar.paginadoResultadosDetalleF14C,{add:false,edit:false,del:false,search: false,refresh: false});				
		},
		
		
		//Modelo de la grilla para mostrar Observaciones formatos 12A,12B,14A,14B y 14C				
		buildGridsObsF12ABF14AB : function () {
			var ancho = notificarValidar.divBuscar.width();
			notificarValidar.tablaObsF12ABF14AB.jqGrid({
			   datatype: "local",
		       colNames: ['Codigo','Grupo Zona','Descripción','Editar','Eliminar','','',''],
		       colModel: [
                       { name: 'idObservacion', index: 'idObservacion', width: 30},					  
		               { name: 'desZona', index: 'desZona', width: 30 },  
		               { name: 'desObservacion', index: 'desObservacion', width: 150},	              
		               { name: 'edit', index: 'edit', width: 20,align:'center' },	 
		               { name: 'elim', index: 'elim', width: 20,align:'center' },
		               { name: 'origenObs', index: 'origenObs', hidden: true},
		               { name: 'itemObs', index: 'itemObs', hidden: true},             
		               { name: 'idZona', index: 'idZona', hidden: true}	               
			   	    ],
			   	 multiselect: false,
					rowNum:10,
				   	rowList:[10,20,50],
					height: 225,
				   //	autowidth: true,
					rownumbers: true,
					//shrinkToFit:true,
					width:ancho ,
					pager: notificarValidar.paginadoObsF12ABF14AB,
				    viewrecords: true,
				    caption: "Lista de Observaciones",
				    sortorder: "asc",	   	    	   	   
		       gridComplete: function(){
		      		var ids = notificarValidar.tablaObsF12ABF14AB.jqGrid('getDataIDs');
		      		for(var i=0;i < ids.length;i++){
		      			var cl = ids[i];
		      			var ret = notificarValidar.tablaObsF12ABF14AB.jqGrid('getRowData',cl);	      			
		      			edit = "<a href='#'><img border='0' title='Editar.' src='/net-theme/images/img-net/edit.png'  align='center' onclick=\"notificarValidar.editarObservacionManual('"+ret.idObservacion+"','"+ret.desObservacion+"','"+ret.origenObs+"');\" /></a> ";
		      			elim = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"notificarValidar.confirmarEliminarObs('"+ret.idObservacion+"','"+ret.itemObs+"','"+ret.origenObs+"');\" /></a> ";
		      			notificarValidar.tablaObsF12ABF14AB.jqGrid('setRowData',ids[i],{edit:edit});
		      			notificarValidar.tablaObsF12ABF14AB.jqGrid('setRowData',ids[i],{elim:elim});
		      		}
		      }
		  	});
			notificarValidar.tablaObsF12ABF14AB.jqGrid('navGrid',notificarValidar.paginadoObsF12ABF14AB,{add:false,edit:false,del:false,search: false,refresh: false});				
		},
		
		buildGridsObsF12CD : function () {
			var ancho = notificarValidar.divBuscar.width();
			notificarValidar.tablaObsF12CD.jqGrid({
			   datatype: "local",
		       colNames: ['Codigo','Etapa ejecución','Nro. item etapa','Descripción','Editar','Eliminar','',''],
		       colModel: [
                       { name: 'idObservacion', index: 'idObservacion', width: 30},		               
		               { name: 'desEstapaEjec', index: 'desEstapaEjec', width: 30},
		               { name: 'itemEtapa', index: 'itemEtapa', width: 20},
		               { name: 'desObservacion', index: 'desObservacion', width: 150},	              
		               { name: 'edit', index: 'edit', width: 20,align:'center' },	 
		               { name: 'elim', index: 'elim', width: 20,align:'center' },
		               { name: 'origenObs', index: 'origenObs', hidden: true},
		               { name: 'itemObs', index: 'itemObs', hidden: true}                          
			   	    ],
			   	 multiselect: false,
					rowNum:10,
				   	rowList:[10,20,50],
					height: 225,
				   //	autowidth: true,
					rownumbers: true,
					//shrinkToFit:true,
					width:ancho ,
					pager: notificarValidar.paginadoObsF12CD,
				    viewrecords: true,
				    caption: "Lista de Observaciones",
				    sortorder: "asc",	   	    	   	   
		       gridComplete: function(){
		      		var ids = notificarValidar.tablaObsF12CD.jqGrid('getDataIDs');
		      		for(var i=0;i < ids.length;i++){
		      			var cl = ids[i];
		      			var ret = notificarValidar.tablaObsF12CD.jqGrid('getRowData',cl);	      			
		      			edit = "<a href='#'><img border='0' title='Editar.' src='/net-theme/images/img-net/edit.png'  align='center' onclick=\"notificarValidar.editarObservacionManual('"+ret.idObservacion+"','"+ret.desObservacion+"','"+ret.origenObs+"');\" /></a> ";
		      			elim = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"notificarValidar.confirmarEliminarObs('"+ret.idObservacion+"','"+ret.itemObs+"','"+ret.origenObs+"');\" /></a> ";
		      			notificarValidar.tablaObsF12CD.jqGrid('setRowData',ids[i],{edit:edit});
		      			notificarValidar.tablaObsF12CD.jqGrid('setRowData',ids[i],{elim:elim});
		      		}
		      }
		  	});
			notificarValidar.tablaObsF12CD.jqGrid('navGrid',notificarValidar.paginadoObsF12CD,{add:false,edit:false,del:false,search: false,refresh: false});				
		},
		
		buildGridsObsF13A : function () {
			var ancho = notificarValidar.divBuscar.width();
			notificarValidar.tablaObsF13A.jqGrid({
			   datatype: "local",
		       colNames: ['Codigo','Grupo Zona','Sector Típico','Cod. Ubigeo','Descripción','Editar','Eliminar','','',''],
		       colModel: [
                       { name: 'idObservacion', index: 'idObservacion', width: 30},					  
		               { name: 'desZona', index: 'desZona', width: 30 }, 
		               { name: 'desSector', index: 'desSector', width: 30},
		               { name: 'codUbigeo', index: 'codUbigeo', width: 20},
		               { name: 'desObservacion', index: 'desObservacion', width: 150},	              
		               { name: 'edit', index: 'edit', width: 20,align:'center' },	 
		               { name: 'elim', index: 'elim', width: 20,align:'center' },
		               { name: 'origenObs', index: 'origenObs', hidden: true},
		               { name: 'itemObs', index: 'itemObs', hidden: true},            
		               { name: 'idZona', index: 'idZona', hidden: true}	               
			   	    ],
			   	 multiselect: false,
					rowNum:10,
				   	rowList:[10,20,50],
					height: 225,
				   //	autowidth: true,
					rownumbers: true,
					//shrinkToFit:true,
					width:ancho ,
					pager: notificarValidar.paginadoObsF13A,
				    viewrecords: true,
				    caption: "Lista de Observaciones",
				    sortorder: "asc",	   	    	   	   
		       gridComplete: function(){
		      		var ids = notificarValidar.tablaObsF13A.jqGrid('getDataIDs');
		      		for(var i=0;i < ids.length;i++){
		      			var cl = ids[i];
		      			var ret = notificarValidar.tablaObsF13A.jqGrid('getRowData',cl);	      			
		      			edit = "<a href='#'><img border='0' title='Editar.' src='/net-theme/images/img-net/edit.png'  align='center' onclick=\"notificarValidar.editarObservacionManual('"+ret.idObservacion+"','"+ret.desObservacion+"','"+ret.origenObs+"');\" /></a> ";
		      			elim = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"notificarValidar.confirmarEliminarObs('"+ret.idObservacion+"','"+ret.itemObs+"','"+ret.origenObs+"');\" /></a> ";
		      			notificarValidar.tablaObsF13A.jqGrid('setRowData',ids[i],{edit:edit});
		      			notificarValidar.tablaObsF13A.jqGrid('setRowData',ids[i],{elim:elim});
		      		}
		      }
		  	});
			notificarValidar.tablaObsF13A.jqGrid('navGrid',notificarValidar.paginadoObsF13A,{add:false,edit:false,del:false,search: false,refresh: false});				
		},
		
		buildGridsObsF14C : function () {
			var ancho = notificarValidar.divBuscar.width();
			notificarValidar.tablaObsF14C.jqGrid({
			   datatype: "local",
		       colNames: ['Codigo','Grupo Zona','Tipo Personal','Descripción','Editar','Eliminar','','',''],
		       colModel: [
                       { name: 'idObservacion', index: 'idObservacion', width: 30},					  
		               { name: 'desZona', index: 'desZona', width: 30 },  
		               { name: 'desPersonal', index: 'desPersonal', width: 40 },  
		               { name: 'desObservacion', index: 'desObservacion', width: 150},	              
		               { name: 'edit', index: 'edit', width: 20,align:'center' },	 
		               { name: 'elim', index: 'elim', width: 20,align:'center' },
		               { name: 'origenObs', index: 'origenObs', hidden: true},
		               { name: 'itemObs', index: 'itemObs', hidden: true},            
		               { name: 'idZona', index: 'idZona', hidden: true}	               
			   	    ],
			   	 multiselect: false,
					rowNum:10,
				   	rowList:[10,20,50],
					height: 225,
				   //	autowidth: true,
					rownumbers: true,
					//shrinkToFit:true,
					width:ancho ,
					pager: notificarValidar.paginadoObsF14C,
				    viewrecords: true,
				    caption: "Lista de Observaciones",
				    sortorder: "asc",	   	    	   	   
		       gridComplete: function(){
		      		var ids = notificarValidar.tablaObsF14C.jqGrid('getDataIDs');
		      		for(var i=0;i < ids.length;i++){
		      			var cl = ids[i];
		      			var ret = notificarValidar.tablaObsF14C.jqGrid('getRowData',cl);	      			
		      			edit = "<a href='#'><img border='0' title='Editar.' src='/net-theme/images/img-net/edit.png'  align='center' onclick=\"notificarValidar.editarObservacionManual('"+ret.idObservacion+"','"+ret.desObservacion+"','"+ret.origenObs+"');\" /></a> ";
		      			elim = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"notificarValidar.confirmarEliminarObs('"+ret.idObservacion+"','"+ret.itemObs+"','"+ret.origenObs+"');\" /></a> ";
		      			notificarValidar.tablaObsF14C.jqGrid('setRowData',ids[i],{edit:edit});
		      			notificarValidar.tablaObsF14C.jqGrid('setRowData',ids[i],{elim:elim});
		      		}
		      }
		  	});
			notificarValidar.tablaObsF14C.jqGrid('navGrid',notificarValidar.paginadoObsF14C,{add:false,edit:false,del:false,search: false,refresh: false});				
		},		
		
		/*fin cambios para ingresar observaciones manuales**/	
		
		
		
		//funcion para buscar
		buscarNotificacion : function () {	
			console.debug("entranado a buscar function");
			notificarValidar.blockUI();
			jQuery.ajax({			
					url: notificarValidar.urlBusqueda+'&'+notificarValidar.formCommand.serialize(),
					type: 'post',
					dataType: 'json',				
					success: function(gridData) {					
						notificarValidar.tablaResultados.clearGridData(true);
						notificarValidar.tablaResultados.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
						notificarValidar.tablaResultados[0].refreshIndex();
						notificarValidar.initBlockUI();
					},error : function(){
						var addhtmError='Error de conexión.';					
						notificarValidar.dialogErrorContent.html(addhtmError);
						notificarValidar.dialogError.dialog("open");
						notificarValidar.initBlockUI();
					}
				});			
		},			
		//function para el evento onchange en empresa para cargar el periodo
		<portlet:namespace/>loadGrupoInformacion : function(){	
			console.debug("entranado a cargar grupoInfo");
			jQuery.ajax({
					url: notificarValidar.urlCargaGrupoInf+'&'+notificarValidar.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					success: function(data) {		
						dwr.util.removeAllOptions("grupoInfBusq");
						dwr.util.addOptions("grupoInfBusq", data,"codigoItem","descripcionItem");														
					},error : function(){
						var addhtmError='Error de conexión.';					
						notificarValidar.dialogErrorContent.html(addhtmError);
						notificarValidar.dialogError.dialog("open");
						notificarValidar.initBlockUI();
					}
			});
		},		
		//Function para procesar notificacion
		procesarNotificacion : function(){	
			console.debug("entrando a procesar notificacion ");			
			$.blockUI({ message: notificarValidar.mensajeProcesando});			 
			jQuery.ajax({
					url: notificarValidar.urlProcesar+'&'+notificarValidar.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {							
						 <portlet:namespace />codEmpresaBusq: notificarValidar.i_codEmpresaBusq.val()						   						  
					},
					success: function(data) {				
						if (data.resultado == 'OK'){
							var addhtml2='Datos procesados satisfactoriamente';
							notificarValidar.dialogMessageContent.html(addhtml2);
							notificarValidar.dialogMessage.dialog("open");
							notificarValidar.botonBuscar.trigger('click');
							notificarValidar.initBlockUI();					
					    }else if(data.resultado == 'NINGUNO'){
					    	var addhtmNinguno='No existe ningún registro para procesar los datos';					
							notificarValidar.dialogInfoContent.html(addhtmNinguno);
							notificarValidar.dialogInfo.dialog("open");					    	
					    	notificarValidar.botonBuscar.trigger('click');
							notificarValidar.initBlockUI();	
					    }else{
					    	var addhtmError='Error al procesar los datos';					
							notificarValidar.dialogErrorContent.html(addhtmError);
							notificarValidar.dialogError.dialog("open");								
							notificarValidar.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						notificarValidar.dialogErrorContent.html(addhtmError);
						notificarValidar.dialogError.dialog("open");
						notificarValidar.initBlockUI();
					}
				});			
		},		
		
		/**Function para  autorizar la autorizacion del reenvion una vez hecha la confirmacion*/
		verObservaciones : function(cod_Empresa,anio_Pres,mes_Pres,anio_Ejec,mes_Ejec,anio_IniVig,anio_FinVig,cod_etapa,cod_formato){
			jQuery.ajax({
				url: notificarValidar.urlVerObservaciones+'&'+notificarValidar.formCommand.serialize(),
				type : 'post',
				dataType : 'json',
				data : {
					   <portlet:namespace />codEmpresa: cod_Empresa,
					   <portlet:namespace />anioPres: anio_Pres,
					   <portlet:namespace />mesPres: mes_Pres,
					   <portlet:namespace />etapa: cod_etapa,
					   <portlet:namespace />anioEjec: anio_Ejec,
					   <portlet:namespace />mesEjec: mes_Ejec,
					   <portlet:namespace />anioIniVig: anio_IniVig,
					   <portlet:namespace />anioFinVig: anio_FinVig,
					   <portlet:namespace />formato: cod_formato
				},
				success : function(data) {
					if( data!=null ){					
						console.debug("formato al lanzar el modelo de las observaciones: "+cod_formato);
						if(cod_formato=='F12C' || cod_formato=='F12D'){
							console.debug("entrando en formato 12C y 12D");
							notificarValidar.dialogObservacion12.dialog("open");
							notificarValidar.tablaObservacion12.clearGridData(true);
							notificarValidar.tablaObservacion12.jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
							notificarValidar.tablaObservacion12[0].refreshIndex();
							notificarValidar.initBlockUI();	
						}else if(cod_formato=='F13A'){
							console.debug("entrando en formato13A ");
							notificarValidar.dialogObservacion13.dialog("open");
							notificarValidar.tablaObservacion13.clearGridData(true);
							notificarValidar.tablaObservacion13.jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
							notificarValidar.tablaObservacion13[0].refreshIndex();
							notificarValidar.initBlockUI();	
						}else{
							notificarValidar.dialogObservacion.dialog("open");
							notificarValidar.tablaObservacion.clearGridData(true);
							notificarValidar.tablaObservacion.jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
							notificarValidar.tablaObservacion[0].refreshIndex();
							notificarValidar.initBlockUI();	
						}				
					}else{
						var addhtmError='Error al realizar la validación.';					
						notificarValidar.dialogErrorContent.html(addhtmError);
						notificarValidar.dialogError.dialog("open");						
						notificarValidar.initBlockUI();	
					}
				},error : function(){
					var addhtmError='Error de conexión.';					
					notificarValidar.dialogErrorContent.html(addhtmError);
					notificarValidar.dialogError.dialog("open");
					notificarValidar.initBlockUI();
				}
			});
		},
		
		//funcion para ver reporte de las observaciones
		<portlet:namespace/>mostrarReporteObservaciones : function(){
			jQuery.ajax({
				url: notificarValidar.urlReporteObservaciones+'&'+notificarValidar.formCommand.serialize(),
				type : 'post',
				dataType : 'json',
				data : {					
					<portlet:namespace />nombreReporte: 'validacion',
					<portlet:namespace />nombreArchivo: 'validacion',
					<portlet:namespace />tipoArchivo: '0'//PDF
				},
				success : function(gridData) {
					if(gridData!=null){
						notificarValidar.verReporteObservaciones();	
					}else{
						var addhtmError='Error al mostrar el reporte';					
						notificarValidar.dialogErrorContent.html(addhtmError);
						notificarValidar.dialogError.dialog("open");						
						notificarValidar.initBlockUI();
					}
					
				},error : function(){
					var addhtmError='Error de conexión.';					
					notificarValidar.dialogErrorContent.html(addhtmError);
					notificarValidar.dialogError.dialog("open");
					notificarValidar.initBlockUI();
				}
			});
		},
		
		//funcion para ver reposrte en una nueva pestaña
		verReporteObservaciones : function(){
			window.open('<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ViewReport")%>','_newtab');
		},
					
		/**Function para confirmar si quiere notificar la validacion de obs.*/
		confirmarNotificacion : function(){
			console.debug("entranado a confirmar notificación");
			var addhtml='¿Está seguro que desea Notificar a los usuarios de la Distribuidora Eléctrica?';
			notificarValidar.dialogConfirmContent.html(addhtml);
			notificarValidar.dialogConfirm.dialog("open");
		},
		/**Function para  autorizar la autorizacion del reenvion una vez hecha la confirmacion*/
		notificarValidacionDefinitivo : function(){
			console.debug("entranado a notificar validacion ");			
			var desGrupo = document.getElementById('grupoInfBusq')[document.getElementById('grupoInfBusq').selectedIndex].innerHTML;
			console.debug("desc grupo inf. seleccionado:  "+desGrupo);
			$.blockUI({ message: notificarValidar.mensajeNotificar});
			jQuery.ajax({
				url: notificarValidar.urlNotificar+'&'+notificarValidar.formCommand.serialize(),
				type: 'post',
				dataType: 'json',
				data: {				
				     <portlet:namespace />codEmpresa: notificarValidar.i_codEmpresaBusq.val(),
				     <portlet:namespace />descGrupoInf: desGrupo	
					},
				success: function(data) {
					if(data.resultado == "OK"){
						var addhtml2='Se realizó la Notificación a los siguientes correos electrónicos: '+data.correo; 					
						notificarValidar.dialogMessageContent.html(addhtml2);
						notificarValidar.dialogMessage.dialog("open");					
						notificarValidar.initBlockUI();
					}else if(data.resultado == "NO_DATOS"){						
						var addhtmInfo='No existe ninguna lista pra realizar la Notificación';					
						notificarValidar.dialogInfoContent.html(addhtmInfo);
						notificarValidar.dialogInfo.dialog("open");						
						notificarValidar.initBlockUI();
					}else if(data.resultado == "EMAIL"){
						var addhtmEmail= data.correo;					
						notificarValidar.dialogErrorContent.html(addhtmEmail);
						notificarValidar.dialogError.dialog("open");							
						notificarValidar.initBlockUI();	
					}else if(data.resultado == "Mensaje"){
						var addhtmError='Error al realizar el cambio de etapa.';					
						notificarValidar.dialogErrorContent.html(addhtmError);
						notificarValidar.dialogError.dialog("open");					
						notificarValidar.initBlockUI();	
					}else if(data.resultado == "ERROR"){
						var addhtmError='Error al realizar la Notificación.';					
						notificarValidar.dialogErrorContent.html(addhtmError);
						notificarValidar.dialogError.dialog("open");					
						notificarValidar.initBlockUI();
					}
				},error : function(){
					var addhtmError='Error de conexión.';					
					notificarValidar.dialogErrorContent.html(addhtmError);
					notificarValidar.dialogError.dialog("open");
					notificarValidar.initBlockUI();
				}
			});
		},
		
		/**Function para confirmar si quiere eliminar el registro o no*/
		confirmarEliminarNotificacion : function(codEmpresa,anioPres,mesPres,anioEjec,mesEjec,anioIniVig,anioFinVig,codetapa,codformato){	
				var addhtml='¿Está seguro que desea excluir el registro seleccionado?';
				notificarValidar.dialogConfirmContentEliminar.html(addhtml);
				notificarValidar.dialogConfirmEliminar.dialog("open");			
				cod_Empresa=codEmpresa;
				anio_Pres=anioPres;
				mes_Pres=mesPres;
				anio_Ejec=anioEjec;
				mes_Ejec=mesEjec;
				anio_IniVig=anioIniVig;
				anio_FinVig=anioFinVig;
				cod_etapa=codetapa;
				cod_formato =codformato;
		},
		/**Function para  eliminar el registro una vez hecho la confirmacion*/
		eliminarNotificacion : function(cod_Empresa,anio_Pres,mes_Pres,anio_Ejec,mes_Ejec,anio_IniVig,anio_FinVig,cod_etapa,cod_formato){			
			console.debug("entranado a eliminar notificacion : "+cod_Empresa);
			$.blockUI({ message: notificarValidar.mensajeEliminando});
			jQuery.ajax({
				url: notificarValidar.urlEliminar+'&'+notificarValidar.formCommand.serialize(),
				type: 'post',
				dataType: 'json',
				data: {				
				   <portlet:namespace />codEmpresa: cod_Empresa,
				   <portlet:namespace />anioPres: anio_Pres,
				   <portlet:namespace />mesPres: mes_Pres,
				   <portlet:namespace />anioEjec: anio_Ejec,
				   <portlet:namespace />mesEjec: mes_Ejec,
				   <portlet:namespace />anioIniVig: anio_IniVig,
				   <portlet:namespace />anioFinVig: anio_FinVig,
				   <portlet:namespace />etapa: cod_etapa,
				   <portlet:namespace />formato: cod_formato				   
					},
				success: function(data) {
					if (data.resultado == "OK"){
						var addhtml2='Registro excluido satisfactoriamente';					
						notificarValidar.dialogMessageContent.html(addhtml2);
						notificarValidar.dialogMessageEliminar.dialog("open");
						notificarValidar.buscarNotificacion();
						notificarValidar.initBlockUI();
					}
					else{
						var addhtmError='Error al eliminar el registro seleccionado';					
						notificarValidar.dialogErrorContent.html(addhtmError);
						notificarValidar.dialogError.dialog("open");						
						notificarValidar.initBlockUI();
					}
				},error : function(){
					var addhtmError='Error de conexión.';					
					notificarValidar.dialogErrorContent.html(addhtmError);
					notificarValidar.dialogError.dialog("open");
					notificarValidar.initBlockUI();
				}
			});
		},
		
		//cambios en agregar observacion manual		
		buscarDetalleFormato : function (cod_empresa,anio_pres,mes_pres,anio_ejec,mes_ejec,anio_inicio,anio_fin,etapa,formato) {	
			console.debug("entranado a buscar detalle de formato");
			notificarValidar.blockUI();
			jQuery.ajax({			
					url: notificarValidar.urlBusquedaDetalle+'&'+notificarValidar.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data : {
						   <portlet:namespace />codEmpresa: cod_empresa,	
						   <portlet:namespace />anioPres: anio_pres,	
						   <portlet:namespace />mesPres: mes_pres,	
						   <portlet:namespace />formato: formato,	
						   <portlet:namespace />etapa: etapa,	
						   <portlet:namespace />anioEjec: anio_ejec,	
						   <portlet:namespace />mesEjec: mes_ejec,	
						   <portlet:namespace />anioIniVig: anio_inicio,	
						   <portlet:namespace />anioFinVig: anio_fin	
					},
					success: function(gridData) {
						if(formato =='F12A' || formato =='F12B'){
							console.debug('entro a la grilla formato 12AB');
							notificarValidar.tablaResultadosDetalleF12AB.clearGridData(true);
							notificarValidar.tablaResultadosDetalleF12AB.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
							notificarValidar.tablaResultadosDetalleF12AB[0].refreshIndex();
							//juego de divs
							notificarValidar.divGrillaF12AB.show();
							notificarValidar.divGrillaF12CD.hide();	
							notificarValidar.divGrillaF13A.hide();
							notificarValidar.divGrillaF14AB.hide();	
							notificarValidar.divGrillaF14C.hide();						
							notificarValidar.initBlockUI();	
						}else if(formato =='F12C' || formato =='F12D'){
							console.debug('entro a la grilla formato 12CD');
							notificarValidar.tablaResultadosDetalleF12CD.clearGridData(true);
							notificarValidar.tablaResultadosDetalleF12CD.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
							notificarValidar.tablaResultadosDetalleF12CD[0].refreshIndex();
							//juego de divs
							notificarValidar.divGrillaF12AB.hide();
							notificarValidar.divGrillaF12CD.show();	
							notificarValidar.divGrillaF13A.hide();
							notificarValidar.divGrillaF14AB.hide();	
							notificarValidar.divGrillaF14C.hide();	
							notificarValidar.initBlockUI();	
						}else if(formato =='F13A'){
							console.debug('entro a la grilla formato 13A');
							notificarValidar.tablaResultadosDetalleF13A.clearGridData(true);
							notificarValidar.tablaResultadosDetalleF13A.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
							notificarValidar.tablaResultadosDetalleF13A[0].refreshIndex();
							//juego de divs
							notificarValidar.divGrillaF12AB.hide();
							notificarValidar.divGrillaF12CD.hide();	
							notificarValidar.divGrillaF13A.show();
							notificarValidar.divGrillaF14AB.hide();	
							notificarValidar.divGrillaF14C.hide();	
							notificarValidar.initBlockUI();	
						}else if(formato =='F14A' || formato =='F14B'){
							console.debug('entro a la grilla formato 14AB');
							notificarValidar.tablaResultadosDetalleF14AB.clearGridData(true);
							notificarValidar.tablaResultadosDetalleF14AB.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
							notificarValidar.tablaResultadosDetalleF14AB[0].refreshIndex();
							//juego de divs
							notificarValidar.divGrillaF12AB.hide();
							notificarValidar.divGrillaF12CD.hide();	
							notificarValidar.divGrillaF13A.hide();
							notificarValidar.divGrillaF14AB.show();	
							notificarValidar.divGrillaF14C.hide();	
							notificarValidar.initBlockUI();	
						}else if(formato =='F14C'){
							console.debug('entro a la grilla formato 14C');
							notificarValidar.tablaResultadosDetalleF14C.clearGridData(true);
							notificarValidar.tablaResultadosDetalleF14C.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
							notificarValidar.tablaResultadosDetalleF14C[0].refreshIndex();
							//juego de divs
							notificarValidar.divGrillaF12AB.hide();
							notificarValidar.divGrillaF12CD.hide();	
							notificarValidar.divGrillaF13A.hide();
							notificarValidar.divGrillaF14AB.hide();	
							notificarValidar.divGrillaF14C.show();	
							notificarValidar.initBlockUI();	
						}						
					},error : function(){
						var addhtmError='Error de conexión.';					
						notificarValidar.dialogErrorContent.html(addhtmError);
						notificarValidar.dialogError.dialog("open");
						notificarValidar.initBlockUI();
					}
				});			
		},		
		//funcion  para mostrar detalle de los formatos
		mostrarDetalleFormato : function(cod_empresa,anio_pres,mes_pres,anio_ejec,mes_ejec,anio_inicio,anio_fin,etapa,formato,desEmpresa,desMes,desMesEje){	
				$('#empresaDetalle').val(desEmpresa);
				$('#anioDetalle').val(anio_pres);
				$('#mesDetalle').val(desMes);
				$('#anioEjecDetalle').val(anio_ejec);
				$('#mesEjecDetalle').val(desMesEje);
				$('#anioInicioVigDetalle').val(anio_inicio);
				$('#anioFinVigDetalle').val(anio_fin);
				$('#etapaDetalle').val(etapa);
				$('#formatoDetalle').val(formato);						
				notificarValidar.divDetalle.show();
				notificarValidar.divBuscar.hide();		
				notificarValidar.buscarDetalleFormato(cod_empresa,anio_pres,mes_pres,anio_ejec,mes_ejec,anio_inicio,anio_fin,etapa,formato);	
		},	
		
		//funcion para mostrar la lista de observaciones por detalle de formato		
		buscarObsF12AB : function (cod_empresa,anio_pres,mes_pres,anio_ejec,mes_ejec,etapa,id_zona) {	
			console.debug("entranado a buscar observaciones de formato 12AB");
			notificarValidar.blockUI();
			jQuery.ajax({			
					url: notificarValidar.urlBuscarListaObs+'&'+notificarValidar.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data : {
						   <portlet:namespace />codEmpresa: cod_empresa,	
						   <portlet:namespace />anioPres: anio_pres,	
						   <portlet:namespace />mesPres: mes_pres,	
						   <portlet:namespace />formato: $('#formatoDetalle').val(),	
						   <portlet:namespace />etapa: etapa,	
						   <portlet:namespace />anioEjec: anio_ejec,	
						   <portlet:namespace />mesEjec: mes_ejec,	
						   <portlet:namespace />idZona: id_zona						  
					},
					success: function(gridData) {						
							notificarValidar.tablaObsF12ABF14AB.clearGridData(true);
							notificarValidar.tablaObsF12ABF14AB.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
							notificarValidar.tablaObsF12ABF14AB[0].refreshIndex();
							//juego de divs grilla
							notificarValidar.divGrillaObsF14AB.show();
							notificarValidar.divGrillaObsF12CD.hide();	
							notificarValidar.divGrillaObsF13A.hide();
							notificarValidar.divGrillaObsF14C.hide();
							//juego de div de las secciones
							notificarValidar.divDetalle.hide();
			                notificarValidar.divBuscar.hide();	
			                notificarValidar.divListaObs.show();
			                notificarValidar.divNuevoObs.hide();
							//respaldar su pk
							$('#hiddenCodEmpresa').val(cod_empresa);
				            $('#hiddenAnioPres').val(anio_pres);
				            $('#hiddenMesPres').val(mes_pres);
				            $('#hiddenEtapa').val(etapa);
				            $('#hiddenAnioEjec').val(anio_ejec);
				            $('#hiddenMesEjec').val(mes_ejec);
				            $('#hiddenIdZona').val(id_zona);
				            $('#hiddenTipoBusqueda').val('0');//valor para saber que vine del F12A y F12B					            
							notificarValidar.initBlockUI();							
					},error : function(){
						var addhtmError='Error de conexión.';					
						notificarValidar.dialogErrorContent.html(addhtmError);
						notificarValidar.dialogError.dialog("open");
						notificarValidar.initBlockUI();
					}
				});			
		   },
		   
		  buscarObsF12CD : function (cod_empresa,anio_pres,mes_pres,anio_ejec,mes_ejec,etapa,etapa_ejec,num_item) {	
				console.debug("entranado a buscar observaciones de formato 12CD");
				notificarValidar.blockUI();
				jQuery.ajax({			
						url: notificarValidar.urlBuscarListaObs+'&'+notificarValidar.formCommand.serialize(),
						type: 'post',
						dataType: 'json',
						data : {
							   <portlet:namespace />codEmpresa: cod_empresa,	
							   <portlet:namespace />anioPres: anio_pres,	
							   <portlet:namespace />mesPres: mes_pres,	
							   <portlet:namespace />formato: $('#formatoDetalle').val(),	
							   <portlet:namespace />etapa: etapa,	
							   <portlet:namespace />anioEjec: anio_ejec,	
							   <portlet:namespace />mesEjec: mes_ejec,	
							   <portlet:namespace />etapaEjec: etapa_ejec,
							   <portlet:namespace />itemEtapa: num_item
							   
						},
						success: function(gridData) {						
								notificarValidar.tablaObsF12CD.clearGridData(true);
								notificarValidar.tablaObsF12CD.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
								notificarValidar.tablaObsF12CD[0].refreshIndex();
								//juego de divs
								notificarValidar.divGrillaObsF14AB.hide();
								notificarValidar.divGrillaObsF12CD.show();	
								notificarValidar.divGrillaObsF13A.hide();
								notificarValidar.divGrillaObsF14C.hide();
								//juego de div de las secciones
								notificarValidar.divDetalle.hide();
				                notificarValidar.divBuscar.hide();	
				                notificarValidar.divListaObs.show();
				                notificarValidar.divNuevoObs.hide();
								//respaldar su pk
								$('#hiddenCodEmpresa').val(cod_empresa);
					            $('#hiddenAnioPres').val(anio_pres);
					            $('#hiddenMesPres').val(mes_pres);
					            $('#hiddenEtapa').val(etapa);
					            $('#hiddenAnioEjec').val(anio_ejec);
					            $('#hiddenMesEjec').val(mes_ejec);
					            $('#hiddenEtapaEjec').val(etapa_ejec);
					            $('#hiddenItemEtapa').val(num_item);
					            $('#hiddenTipoBusqueda').val('1');//valor para saber que vine del F12C y F12D		
								notificarValidar.initBlockUI();						
						},error : function(){
							var addhtmError='Error de conexión.';					
							notificarValidar.dialogErrorContent.html(addhtmError);
							notificarValidar.dialogError.dialog("open");
							notificarValidar.initBlockUI();
						}
					});			
			   },
		
			  buscarObsF13A : function (cod_empresa,anio_pres,mes_pres,etapa,cod_ubigeo,cod_sector,id_zona) {	
					console.debug("entranado a buscar observaciones de formato 13A");
					notificarValidar.blockUI();
					jQuery.ajax({			
							url: notificarValidar.urlBuscarListaObs+'&'+notificarValidar.formCommand.serialize(),
							type: 'post',
							dataType: 'json',
							data : {
								   <portlet:namespace />codEmpresa: cod_empresa,	
								   <portlet:namespace />anioPres: anio_pres,	
								   <portlet:namespace />mesPres: mes_pres,	
								   <portlet:namespace />formato: $('#formatoDetalle').val(),	
								   <portlet:namespace />etapa: etapa,	
								   <portlet:namespace />codUbigeo: cod_ubigeo,	
								   <portlet:namespace />codSector: cod_sector,	
								   <portlet:namespace />idZona: id_zona						  
							},
							success: function(gridData) {						
									notificarValidar.tablaObsF13A.clearGridData(true);
									notificarValidar.tablaObsF13A.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
									notificarValidar.tablaObsF13A[0].refreshIndex();
									//juego de divs
									notificarValidar.divGrillaObsF14AB.hide();
									notificarValidar.divGrillaObsF12CD.hide();	
									notificarValidar.divGrillaObsF13A.show();
									notificarValidar.divGrillaObsF14C.hide();
									//juego de div de las secciones
									notificarValidar.divDetalle.hide();
					                notificarValidar.divBuscar.hide();	
					                notificarValidar.divListaObs.show();
					                notificarValidar.divNuevoObs.hide();
									//respaldar su pk
									$('#hiddenCodEmpresa').val(cod_empresa);
						            $('#hiddenAnioPres').val(anio_pres);
						            $('#hiddenMesPres').val(mes_pres);
						            $('#hiddenEtapa').val(etapa);
						            $('#hiddenCodUbigeo').val(cod_ubigeo);
						            $('#hiddenCodSector').val(cod_sector);
						            $('#hiddenIdZona').val(id_zona);						            
						            $('#hiddenTipoBusqueda').val('2');//valor para saber que vine del F13A	
									notificarValidar.initBlockUI();							
							},error : function(){
								var addhtmError='Error de conexión.';					
								notificarValidar.dialogErrorContent.html(addhtmError);
								notificarValidar.dialogError.dialog("open");
								notificarValidar.initBlockUI();
							}
						});			
				  },
		
				 buscarObsF14AB : function (cod_empresa,anio_pres,mes_pres,anio_inicio,anio_fin,etapa,id_zona) {	
						console.debug("entranado a buscar observaciones de formato 14AB");
						notificarValidar.blockUI();
						jQuery.ajax({			
								url: notificarValidar.urlBuscarListaObs+'&'+notificarValidar.formCommand.serialize(),
								type: 'post',
								dataType: 'json',
								data : {
									   <portlet:namespace />codEmpresa: cod_empresa,	
									   <portlet:namespace />anioPres: anio_pres,	
									   <portlet:namespace />mesPres: mes_pres,	
									   <portlet:namespace />formato: $('#formatoDetalle').val(),	
									   <portlet:namespace />etapa: etapa,	
									   <portlet:namespace />anioIniVig: anio_inicio,	
									   <portlet:namespace />anioFinVig: anio_fin,		
									   <portlet:namespace />idZona: id_zona						  
								},
								success: function(gridData) {						
										notificarValidar.tablaObsF12ABF14AB.clearGridData(true);
										notificarValidar.tablaObsF12ABF14AB.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
										notificarValidar.tablaObsF12ABF14AB[0].refreshIndex();
										//juego de divs
										notificarValidar.divGrillaObsF14AB.show();
										notificarValidar.divGrillaObsF12CD.hide();	
										notificarValidar.divGrillaObsF13A.hide();
										notificarValidar.divGrillaObsF14C.hide();
										//juego de div de las secciones
										notificarValidar.divDetalle.hide();
						                notificarValidar.divBuscar.hide();	
						                notificarValidar.divListaObs.show();
						                notificarValidar.divNuevoObs.hide();
										//respaldar su pk
										$('#hiddenCodEmpresa').val(cod_empresa);
							            $('#hiddenAnioPres').val(anio_pres);
							            $('#hiddenMesPres').val(mes_pres);
							            $('#hiddenEtapa').val(etapa);
							            $('#hiddenAnioIniVig').val(anio_inicio);
							            $('#hiddenAnioFinVig').val(anio_fin);
							            $('#hiddenIdZona').val(id_zona);						            
							            $('#hiddenTipoBusqueda').val('3');//valor para saber que vine del F14A y F14B		
										notificarValidar.initBlockUI();							
								},error : function(){
									var addhtmError='Error de conexión.';					
									notificarValidar.dialogErrorContent.html(addhtmError);
									notificarValidar.dialogError.dialog("open");
									notificarValidar.initBlockUI();
								}
							});			
					   },
					   buscarObsF14C : function (cod_empresa,anio_pres,mes_pres,anio_inicio,anio_fin,etapa,id_zona,id_personal) {	
							console.debug("entranado a buscar observaciones de formato 14C");
							notificarValidar.blockUI();
							jQuery.ajax({			
									url: notificarValidar.urlBuscarListaObs+'&'+notificarValidar.formCommand.serialize(),
									type: 'post',
									dataType: 'json',
									data : {
										   <portlet:namespace />codEmpresa: cod_empresa,	
										   <portlet:namespace />anioPres: anio_pres,	
										   <portlet:namespace />mesPres: mes_pres,	
										   <portlet:namespace />formato: $('#formatoDetalle').val(),	
										   <portlet:namespace />etapa: etapa,	
										   <portlet:namespace />anioIniVig: anio_inicio,	
										   <portlet:namespace />anioFinVig: anio_fin,		
										   <portlet:namespace />idZona: id_zona,
										   <portlet:namespace />idPersonal: id_personal
									},
									success: function(gridData) {						
											notificarValidar.tablaObsF14C.clearGridData(true);
											notificarValidar.tablaObsF14C.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
											notificarValidar.tablaObsF14C[0].refreshIndex();
											//juego de divs
											notificarValidar.divGrillaObsF14AB.hide();
											notificarValidar.divGrillaObsF12CD.hide();	
											notificarValidar.divGrillaObsF13A.hide();
											notificarValidar.divGrillaObsF14C.show();
											//juego de div de las secciones
											notificarValidar.divDetalle.hide();
							                notificarValidar.divBuscar.hide();	
							                notificarValidar.divListaObs.show();
							                notificarValidar.divNuevoObs.hide();
											//respaldar su pk
											$('#hiddenCodEmpresa').val(cod_empresa);
								            $('#hiddenAnioPres').val(anio_pres);
								            $('#hiddenMesPres').val(mes_pres);
								            $('#hiddenEtapa').val(etapa);
								            $('#hiddenAnioIniVig').val(anio_inicio);
								            $('#hiddenAnioFinVig').val(anio_fin);
								            $('#hiddenIdZona').val(id_zona);
								            $('#hiddenIdPersonal').val(id_personal);
								            $('#hiddenTipoBusqueda').val('4');//valor para saber que vine del F14C	
											notificarValidar.initBlockUI();							
									},error : function(){
										var addhtmError='Error de conexión.';					
										notificarValidar.dialogErrorContent.html(addhtmError);
										notificarValidar.dialogError.dialog("open");
										notificarValidar.initBlockUI();
									}
								});			
						   },
						   
						   
		//funcion para regresar a busqueda desde el detalle del formato
		<portlet:namespace/>agregarObservacion : function(){			
			notificarValidar.divDetalle.hide();
			notificarValidar.divBuscar.hide();	
			notificarValidar.divListaObs.hide();
			notificarValidar.divNuevoObs.show();
			notificarValidar.f_descObservacion.val('');
			$('#<portlet:namespace/>btnGrabarObservacion').css('display','block');
			$('#<portlet:namespace/>btnActualizarObservacion').css('display','none');
	    },	    
	    
	  //Function para editar una observacion
		editarObservacionManual : function(cod_obs,descripcion,origen){	
			console.debug("entrando a editar "+cod_obs);	
			if(origen=='M'){
				notificarValidar.f_descObservacion.val(descripcion);
				notificarValidar.divDetalle.hide();
				notificarValidar.divBuscar.hide();	
				notificarValidar.divListaObs.hide();
				notificarValidar.divNuevoObs.show();
				$('#hiddenIdObservacion').val(cod_obs);
				$('#<portlet:namespace/>btnGrabarObservacion').css('display','none');
				$('#<portlet:namespace/>btnActualizarObservacion').css('display','block');
			}else{
				var addhtmInfo='La Observación no puede ser editada ha sido generado de manera automática.';					
				notificarValidar.dialogInfoContent.html(addhtmInfo);
				notificarValidar.dialogInfo.dialog("open");	
				notificarValidar.initBlockUI();	
			}		
		},
	    
		//Funcion para Grabar nueva observacion manual
		<portlet:namespace/>guardarObsManual: function(){
			if (notificarValidar.validarFormulario()){
				$.blockUI({ message: notificarValidar.mensajeGuardando});
				 jQuery.ajax({
					 url: notificarValidar.urlGrabarObs+'&'+notificarValidar.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {	  
						   <portlet:namespace />codEmpresa: $('#hiddenCodEmpresa').val(),	
						   <portlet:namespace />anioPres: $('#hiddenAnioPres').val(),	
						   <portlet:namespace />mesPres: $('#hiddenMesPres').val(),					  
						   <portlet:namespace />etapa: $('#hiddenEtapa').val(),	
						   <portlet:namespace />anioEjec: $('#hiddenAnioEjec').val(),	
						   <portlet:namespace />mesEjec: $('#hiddenMesEjec').val(),	
						   <portlet:namespace />anioIniVig: $('#hiddenAnioIniVig').val(),	
						   <portlet:namespace />anioFinVig: $('#hiddenAnioFinVig').val(),						   
						   <portlet:namespace />formato: $('#formatoDetalle').val(),						  
						   <portlet:namespace />etapaEjec: $('#hiddenEtapaEjec').val(),
						   <portlet:namespace />itemEtapa: $('#hiddenItemEtapa').val(),
						   <portlet:namespace />codUbigeo: $('#hiddenCodUbigeo').val(),	
						   <portlet:namespace />codSector: $('#hiddenCodSector').val(),	
						   <portlet:namespace />idZona: $('#hiddenIdZona').val(),
						   <portlet:namespace />idPersonal: $('#hiddenIdPersonal').val(),						  
						   <portlet:namespace />desObservacion: notificarValidar.f_descObservacion.val()
						},
					    success: function(data) {			
						if (data.resultado == "OK"){				
							var addhtml2='La Observación se guardó satisfactoriamente';							
							notificarValidar.dialogMessageContent.html(addhtml2);
							notificarValidar.dialogMessage.dialog("open");						
							var tipoBusq =  $('#hiddenTipoBusqueda').val();
							if(tipoBusq=='0'){								
								notificarValidar.buscarObsF12AB($('#hiddenCodEmpresa').val(),
										$('#hiddenAnioPres').val(),	$('#hiddenMesPres').val(),
										$('#hiddenAnioEjec').val(),	$('#hiddenMesEjec').val(),
										$('#hiddenEtapa').val(), $('#hiddenIdZona').val());	
							}else if(tipoBusq=='1'){
								notificarValidar.buscarObsF12CD($('#hiddenCodEmpresa').val(),
										$('#hiddenAnioPres').val(),$('#hiddenMesPres').val(),
										$('#hiddenAnioEjec').val(),	$('#hiddenMesEjec').val(),
										$('#hiddenEtapa').val(),$('#hiddenEtapaEjec').val(),
										$('#hiddenItemEtapa').val());
							}else if(tipoBusq=='2'){
								notificarValidar.buscarObsF13A($('#hiddenCodEmpresa').val(),
										$('#hiddenAnioPres').val(),	$('#hiddenMesPres').val(),
										$('#hiddenEtapa').val(),$('#hiddenCodUbigeo').val(),
										$('#hiddenCodSector').val(),$('#hiddenIdZona').val());	
							}else if(tipoBusq=='3'){
								notificarValidar.buscarObsF14AB($('#hiddenCodEmpresa').val(),
										$('#hiddenAnioPres').val(),	$('#hiddenMesPres').val(),
										$('#hiddenAnioIniVig').val(),$('#hiddenAnioFinVig').val(),
										$('#hiddenEtapa').val(),$('#hiddenIdZona').val());
							}else if(tipoBusq=='4'){								
								notificarValidar.buscarObsF14C($('#hiddenCodEmpresa').val(),
										$('#hiddenAnioPres').val(),	$('#hiddenMesPres').val(),
										$('#hiddenAnioIniVig').val(),$('#hiddenAnioFinVig').val(),
										$('#hiddenEtapa').val(),$('#hiddenIdZona').val(),
										$('#hiddenIdPersonal').val());
							}						
							$('#<portlet:namespace/>btnGrabarObservacion').css('display','none');
							$('#<portlet:namespace/>btnActualizarObservacion').css('display','block');
							notificarValidar.initBlockUI();								
						}else{								
							var addhtmError='Se produjo un error al guardar la Observación.';					
							notificarValidar.dialogErrorContent.html(addhtmError);
							notificarValidar.dialogError.dialog("open");						
							notificarValidar.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						notificarValidar.dialogErrorContent.html(addhtmError);
						notificarValidar.dialogError.dialog("open");
						notificarValidar.initBlockUI();
					}
				});			
			}
		},
	    	
		//Funcion para actualizar nueva observacion manual
		<portlet:namespace/>actualizarObsManual: function(){
			if (notificarValidar.validarFormulario()){
				$.blockUI({ message: notificarValidar.mensajeActualizando});
				 jQuery.ajax({
					 url: notificarValidar.urlActualizarObs+'&'+notificarValidar.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {	  
						   <portlet:namespace />idObservacion: $('#hiddenIdObservacion').val(),					   						  
						   <portlet:namespace />desObservacion: notificarValidar.f_descObservacion.val()
						},
					    success: function(data) {			
						if (data.resultado == "OK"){				
							var addhtml2='La Observación se actualizó satisfactoriamente';							
							notificarValidar.dialogMessageContent.html(addhtml2);
							notificarValidar.dialogMessage.dialog("open");	
							var tipoBusq =  $('#hiddenTipoBusqueda').val();
							if(tipoBusq=='0'){								
								notificarValidar.buscarObsF12AB($('#hiddenCodEmpresa').val(),
										$('#hiddenAnioPres').val(),	$('#hiddenMesPres').val(),
										$('#hiddenAnioEjec').val(),	$('#hiddenMesEjec').val(),
										$('#hiddenEtapa').val(), $('#hiddenIdZona').val());	
							}else if(tipoBusq=='1'){
								notificarValidar.buscarObsF12CD($('#hiddenCodEmpresa').val(),
										$('#hiddenAnioPres').val(),$('#hiddenMesPres').val(),
										$('#hiddenAnioEjec').val(),	$('#hiddenMesEjec').val(),
										$('#hiddenEtapa').val(),$('#hiddenEtapaEjec').val(),
										$('#hiddenItemEtapa').val());
							}else if(tipoBusq=='2'){
								notificarValidar.buscarObsF13A($('#hiddenCodEmpresa').val(),
										$('#hiddenAnioPres').val(),	$('#hiddenMesPres').val(),
										$('#hiddenEtapa').val(),$('#hiddenCodUbigeo').val(),
										$('#hiddenCodSector').val(),$('#hiddenIdZona').val());	
							}else if(tipoBusq=='3'){
								notificarValidar.buscarObsF14AB($('#hiddenCodEmpresa').val(),
										$('#hiddenAnioPres').val(),	$('#hiddenMesPres').val(),
										$('#hiddenAnioIniVig').val(),$('#hiddenAnioFinVig').val(),
										$('#hiddenEtapa').val(),$('#hiddenIdZona').val());
							}else if(tipoBusq=='4'){								
								notificarValidar.buscarObsF14C($('#hiddenCodEmpresa').val(),
										$('#hiddenAnioPres').val(),	$('#hiddenMesPres').val(),
										$('#hiddenAnioIniVig').val(),$('#hiddenAnioFinVig').val(),
										$('#hiddenEtapa').val(),$('#hiddenIdZona').val(),
										$('#hiddenIdPersonal').val());
							}			
							notificarValidar.initBlockUI();								
						}else{								
							var addhtmError='Se produjo un error al actualizar la Observación.';					
							notificarValidar.dialogErrorContent.html(addhtmError);
							notificarValidar.dialogError.dialog("open");						
							notificarValidar.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						notificarValidar.dialogErrorContent.html(addhtmError);
						notificarValidar.dialogError.dialog("open");
						notificarValidar.initBlockUI();
					}
				});			
			}
		},
		
		//funcion para validar ingreso de datos del motivo de liquidacion
		validarFormulario : function() {
			console.debug("tamaño de descripcion:  "+notificarValidar.f_descObservacion.val().length);
			if(notificarValidar.f_descObservacion.val().length == ''){			
				var addhtmAlert='Debe ingresar una Descripción de la Observación.';					
				notificarValidar.dialogValidacionContent.html(addhtmAlert);
				notificarValidar.dialogValidacion.dialog("open");		
				notificarValidar.f_descObservacion.focus();
			  	return false; 
			}else if(notificarValidar.f_descObservacion.val().length > 499){				
				var addhtmAlert='La descripción de la Observación no debe exceder a los 500 caracteres.';					
				notificarValidar.dialogValidacionContent.html(addhtmAlert);
				notificarValidar.dialogValidacion.dialog("open");	
				notificarValidar.f_descObservacion.focus();
			  	return false; 
			}else{
				return true;
			}		
		},		
		/**Function para confirmar si quiere eliminar la observacion manual o no*/
		confirmarEliminarObs : function(cod_observacion,item_observacion,origen_obs){
			console.debug("entranado a eliminar observacion confirmar:  "+item_observacion);
			if(origen_obs=='M'){
				var addhtml='¿Está seguro que desea eliminar la Observación seleccionada?';
				notificarValidar.dialogEliminarObsContent.html(addhtml);
				notificarValidar.dialogEliminarObs.dialog("open");	
				codObservacion=cod_observacion;
				itemObservacion=item_observacion;				
			}else{
				var addhtmInfo='La Observación no puede ser eliminada ha sido generado de manera automática.';					
				notificarValidar.dialogInfoContent.html(addhtmInfo);
				notificarValidar.dialogInfo.dialog("open");	
				notificarValidar.initBlockUI();	
			}		
		},
		/**Function para  eliminar la obsercacion manual una vez hecho la confirmacion*/
		eliminarObservacion : function(codObservacion,itemObservacion){
			console.debug("entranado a eliminar:  "+itemObservacion);
			$.blockUI({ message: notificarValidar.mensajeEliminandoObs});
			jQuery.ajax({
				url: notificarValidar.urlEliminarObs+'&'+notificarValidar.formCommand.serialize(),
				type: 'post',
				dataType: 'json',
				data: {
					   <portlet:namespace />codEmpresa: $('#hiddenCodEmpresa').val(),	
					   <portlet:namespace />anioPres: $('#hiddenAnioPres').val(),	
					   <portlet:namespace />mesPres: $('#hiddenMesPres').val(),					  
					   <portlet:namespace />etapa: $('#hiddenEtapa').val(),	
					   <portlet:namespace />anioEjec: $('#hiddenAnioEjec').val(),	
					   <portlet:namespace />mesEjec: $('#hiddenMesEjec').val(),	
					   <portlet:namespace />anioIniVig: $('#hiddenAnioIniVig').val(),	
					   <portlet:namespace />anioFinVig: $('#hiddenAnioFinVig').val(),						   
					   <portlet:namespace />formato: $('#formatoDetalle').val(),						  
					   <portlet:namespace />etapaEjec: $('#hiddenEtapaEjec').val(),
					   <portlet:namespace />itemEtapa: $('#hiddenItemEtapa').val(),
					   <portlet:namespace />codUbigeo: $('#hiddenCodUbigeo').val(),	
					   <portlet:namespace />codSector: $('#hiddenCodSector').val(),	
					   <portlet:namespace />idZona: $('#hiddenIdZona').val(),
					   <portlet:namespace />idPersonal: $('#hiddenIdPersonal').val(),				   
					   <portlet:namespace />idObservacion: codObservacion,
					   <portlet:namespace />itemObs: itemObservacion				  
					},
				success: function(data) {
					if (data.resultado == "OK"){
						var addhtml2='La Observación fue eliminada satisfactoriamente';					
						notificarValidar.dialogMessageContent.html(addhtml2);
						notificarValidar.dialogMessage.dialog("open");
						var tipoBusq =  $('#hiddenTipoBusqueda').val();
						if(tipoBusq=='0'){								
							notificarValidar.buscarObsF12AB($('#hiddenCodEmpresa').val(),
									$('#hiddenAnioPres').val(),	$('#hiddenMesPres').val(),
									$('#hiddenAnioEjec').val(),	$('#hiddenMesEjec').val(),
									$('#hiddenEtapa').val(), $('#hiddenIdZona').val());	
						}else if(tipoBusq=='1'){
							notificarValidar.buscarObsF12CD($('#hiddenCodEmpresa').val(),
									$('#hiddenAnioPres').val(),$('#hiddenMesPres').val(),
									$('#hiddenAnioEjec').val(),	$('#hiddenMesEjec').val(),
									$('#hiddenEtapa').val(),$('#hiddenEtapaEjec').val(),
									$('#hiddenItemEtapa').val());
						}else if(tipoBusq=='2'){
							notificarValidar.buscarObsF13A($('#hiddenCodEmpresa').val(),
									$('#hiddenAnioPres').val(),	$('#hiddenMesPres').val(),
									$('#hiddenEtapa').val(),$('#hiddenCodUbigeo').val(),
									$('#hiddenCodSector').val(),$('#hiddenIdZona').val());	
						}else if(tipoBusq=='3'){
							notificarValidar.buscarObsF14AB($('#hiddenCodEmpresa').val(),
									$('#hiddenAnioPres').val(),	$('#hiddenMesPres').val(),
									$('#hiddenAnioIniVig').val(),$('#hiddenAnioFinVig').val(),
									$('#hiddenEtapa').val(),$('#hiddenIdZona').val());
						}else if(tipoBusq=='4'){								
							notificarValidar.buscarObsF14C($('#hiddenCodEmpresa').val(),
									$('#hiddenAnioPres').val(),	$('#hiddenMesPres').val(),
									$('#hiddenAnioIniVig').val(),$('#hiddenAnioFinVig').val(),
									$('#hiddenEtapa').val(),$('#hiddenIdZona').val(),
									$('#hiddenIdPersonal').val());
						}						
						notificarValidar.initBlockUI();
					}
					else{						
						var addhtmError='Error al eliminar la Observación seleccionada.';					
						notificarValidar.dialogErrorContent.html(addhtmError);
						notificarValidar.dialogError.dialog("open");
						notificarValidar.initBlockUI();
					}
				},error : function(){
					var addhtmError='Error de conexión.';					
					notificarValidar.dialogErrorContent.html(addhtmError);
					notificarValidar.dialogError.dialog("open");
					notificarValidar.initBlockUI();
				}
			});
		},
		
	    
		//funcion para regresar a busqueda desde el detalle del formato
		<portlet:namespace/>regresarBusqueda : function(){			
			notificarValidar.divDetalle.hide();
			notificarValidar.divBuscar.show();	
			notificarValidar.divListaObs.hide();
			notificarValidar.divNuevoObs.hide();	
		},
		
		//funcion para regresar a busqueda desde el detalle del formato
		<portlet:namespace/>regresarDetalle : function(){			
			notificarValidar.divDetalle.show();
			notificarValidar.divBuscar.hide();
			notificarValidar.divListaObs.hide();
			notificarValidar.divNuevoObs.hide();	
		},	
		
		//funcion para regresar a la lista de obs desde un nuevo registro o una actualizacion del registro
		<portlet:namespace/>regresarListaObs : function(){			
			notificarValidar.divDetalle.hide();
			notificarValidar.divBuscar.hide();
			notificarValidar.divListaObs.show();
			notificarValidar.divNuevoObs.hide();	
		},		
		
		//funcion para regresar a busqueda desde el detalle del formato
		<portlet:namespace/>regresarObservaciones : function(){			
			notificarValidar.divDetalle.hide();
			notificarValidar.divBuscar.hide();
			notificarValidar.divListaObs.show();
			notificarValidar.divNuevoObs.hide();	
		},	
		
		//DIALOGOS
		initDialogs : function(){		
			//dialogo para realizar la notificacion de la validacion
			notificarValidar.dialogConfirm.dialog({
				modal: true,
				height: 200,
				width: 450,			
				autoOpen: false,
				buttons: {
					"Si": function() {
						notificarValidar.notificarValidacionDefinitivo();
						$( this ).dialog("close");
					},
					"No": function() {				
						$( this ).dialog("close");
					}
				}
			});	
			
			//dialogo para eliminar registro
			notificarValidar.dialogConfirmEliminar.dialog({
				modal: true,
				height: 200,
				width: 450,			
				autoOpen: false,
				buttons: {
					"Si": function() {
						notificarValidar.eliminarNotificacion(cod_Empresa,anio_Pres,mes_Pres,anio_Ejec,mes_Ejec,anio_IniVig,anio_FinVig,cod_etapa,cod_formato);
						$( this ).dialog("close");
					},
					"No": function() {				
						$( this ).dialog("close");
					}
				}
			});	
			
			notificarValidar.dialogMessage.dialog({
				modal: true,
				autoOpen: false,
				width: 450,
				buttons: {
					Ok: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			notificarValidar.dialogMessageEliminar.dialog({
				modal: true,
				autoOpen: false,
				width: 450,
				buttons: {
					Ok: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			notificarValidar.dialogObservacion.dialog({
				modal: true,
				width: 700,
				autoOpen: false,
				buttons: {
					Cerrar: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			notificarValidar.dialogObservacion12.dialog({
				modal: true,
				width: 740,
				autoOpen: false,
				buttons: {
					Cerrar: function() {
						$( this ).dialog("close");
					}
				}
			}); 
		 
			notificarValidar.dialogObservacion13.dialog({
				modal: true,
				width: 730,
				autoOpen: false,
				buttons: {
					Cerrar: function() {
						$( this ).dialog("close");
					}
				}
			}); 
			
			notificarValidar.dialogValidacion.dialog({
				modal: true,
				autoOpen: false,
				width: 450,
				buttons: {
					Aceptar: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			notificarValidar.dialogError.dialog({
				modal: true,
				autoOpen: false,
				width: 450,
				buttons: {
					Aceptar: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			notificarValidar.dialogInfo.dialog({
				modal: true,
				autoOpen: false,
				width: 450,
				buttons: {
					OK: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			//observacion maunal
			//dialogo para eliminar observacion seleccionada			
			notificarValidar.dialogEliminarObs.dialog({
				modal: true,
				height: 200,
				width: 450,			
				autoOpen: false,
				buttons: {
					"Si": function() {
						notificarValidar.eliminarObservacion(codObservacion,itemObservacion);
						$( this ).dialog("close");
					},
					"No": function() {				
						$( this ).dialog("close");
					}
				}
			});	
			
			
		}, /***fin de inicializar los dialogos**/	
		
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