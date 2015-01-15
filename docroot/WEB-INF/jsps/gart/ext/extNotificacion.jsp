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
		
		//mensajes		
		mensajeNotificar:null,
		mensajeProcesando:null,
		mensajeEliminando:null,
		
		//urls
		urlBusqueda: null,			
	    urlProcesar:null,
	    urlCargaGrupoInf:null,
	    urlVerObservaciones:null,		
		urlReporteObservaciones:null,
		urlNotificar:null,
		urlEliminar:null,
		
		//botones		
		botonBuscar:null,
		botonProcesar:null,
		botonNotificar:null,
		
		//varibales de busqueda
		i_codEmpresaBusq:null,
		i_grupoInfBusq:null,
		i_etapaBusq:null,
		i_tipoBienal:null,
		i_tipoMensual:null,
		
		//variables 
		f_empresa:null,
		
		//grillas
		tablaResultados:null,
		paginadoResultados:null,	
		tablaObservacion:null,	
		paginadoObservacion:null,
		
		tablaObservacion12:null,	
		paginadoObservacion12:null,
		
		tablaObservacion13:null,	
		paginadoObservacion13:null,
		
		
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
			
			//mensajes						
			this.mensajeNotificar='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Procesando Notificación </h3>';			
			this.mensajeProcesando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Procesando Datos </h3>';
			this.mensajeEliminando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Excluyendo </h3>';
			
			//urls
			this.urlBusqueda='<portlet:resourceURL id="busquedaNotificacion" />';					
			this.urlProcesar='<portlet:resourceURL id="procesarNotificacion" />';
			this.urlCargaGrupoInf='<portlet:resourceURL id="cargarGrupoInformacion" />';
			this.urlVerObservaciones='<portlet:resourceURL id="verObservacionesValidacion" />';
			this.urlReporteObservaciones='<portlet:resourceURL id="reporteValidacionNotificacion" />';
			this.urlNotificar='<portlet:resourceURL id="notificarValidacion" />';
			this.urlEliminar='<portlet:resourceURL id="eliminarNotificacion" />';
			
			//botones
			this.botonBuscar=$("#<portlet:namespace/>btnBuscarNotificacion");	
			this.botonProcesar=$("#<portlet:namespace/>btnProcesarNotificacion");	
			this.botonNotificar=$("#<portlet:namespace/>btnNotificarValidar");				
			
			//variables de busqueda
			this.i_codEmpresaBusq=$('#codEmpresaBusq');
			this.i_grupoInfBusq=$('#grupoInfBusq');	
			this.i_etapaBusq=$('#etapaBusq');	
			this.i_tipoBienal=$('#rbtBienal');
			this.i_tipoMensual=$('#rbtMensual');
			
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
			
			
			notificarValidar.initDialogs();
		    
		    
			notificarValidar.i_tipoBienal.change(function(){
				notificarValidar.<portlet:namespace/>loadGrupoInformacion();
			});
			
			notificarValidar.i_tipoMensual.change(function(){
				notificarValidar.<portlet:namespace/>loadGrupoInformacion();
			});
		    
		    //eventos por defecto	
		    
			//notificarValidar.botonBuscar.trigger('click');
			//notificarValidar.initBlockUI();
		},
		//funcion para armar el modelo de la grilla para el resultado
		buildGrids : function () {	
			notificarValidar.tablaResultados.jqGrid({
			   datatype: "local",
		       colNames: ['Dist. Eléct.','Formato.','Año Decl.','Mes Decl.','Año Ejec.','Mes Ejec.','Año Ini. Vig.','Año Fin Vig.','Etapa','Ver','Excluir','','',''],
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
		               { name: 'codEmpresa', index: 'codEmpresa', hidden: true},
		               { name: 'mesPres', index: 'mesPres', hidden: true},             
		               { name: 'mesEjec', index: 'mesEjec', hidden: true}
		               
			   	    ],
			   	 multiselect: false,
					rowNum:10,
				   	rowList:[10,20,50],
					height: 200,
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
		      			/* notificarValidar.tablaResultados.jqGrid('setRowData',ids[i],{notificar:notificar}); */
		      			notificarValidar.tablaResultados.jqGrid('setRowData',ids[i],{ver:ver});
		      			notificarValidar.tablaResultados.jqGrid('setRowData',ids[i],{elim:elim});
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
			var addhtml='¿Está seguro que desea Notificar a los usuarios de las Distribuidoras Eléctricas.?';
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
						var addhtml2='Se realizó la Notificación a los siguientes correos electrónicos: '+data.Correo; 					
						notificarValidar.dialogMessageContent.html(addhtml2);
						notificarValidar.dialogMessage.dialog("open");					
						notificarValidar.initBlockUI();
					}else if(data.resultado == "NO_DATOS"){						
						var addhtmInfo='No existe ninguna lista pra realizar la Notificación';					
						notificarValidar.dialogInfoContent.html(addhtmInfo);
						notificarValidar.dialogInfo.dialog("open");						
						notificarValidar.initBlockUI();	
					}else if(data.resultado == "EMAIL"){
						var addhtmEmail=data.Correo;					
						notificarValidar.dialogInfoContent.html(addhtmEmail);
						notificarValidar.dialogInfo.dialog("open");							
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
			console.debug("entranado a eliminar notificacion ");
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
		
		//DIALOGOS
		initDialogs : function(){		
			//dialogo para realizar la notificacion de la validacion
			notificarValidar.dialogConfirm.dialog({
				modal: true,
				height: 200,
				width: 500,			
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
				width: 500,			
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
				width: 500,
				buttons: {
					Ok: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			notificarValidar.dialogMessageEliminar.dialog({
				modal: true,
				autoOpen: false,
				width: 500,
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
				width: 500,
				buttons: {
					Aceptar: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			notificarValidar.dialogError.dialog({
				modal: true,
				autoOpen: false,
				width: 500,
				buttons: {
					Aceptar: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			notificarValidar.dialogInfo.dialog({
				modal: true,
				autoOpen: false,
				width: 500,
				buttons: {
					OK: function() {
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