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
		
		
		init : function() {
			
			this.formCommand=$('#notificacionBean');	
			
			
			//dialogos
			this.dialogMessage=$("#<portlet:namespace/>dialog-message-notificacion");
			this.dialogMessageContent=$("#<portlet:namespace/>dialog-message-content-notificacion");
			this.dialogConfirm=$("#<portlet:namespace/>dialog-confirm_notificacion");//para notificar
			this.dialogConfirmContent=$("#<portlet:namespace/>dialog-confirm-content_notificacion");//para notificar
			this.dialogObservacion=$("#<portlet:namespace/>dialog-form-observacion");	
			this.dialogConfirmEliminar=$("#<portlet:namespace/>dialog-confirm");//para elimiar
			this.dialogConfirmContentEliminar=$("#<portlet:namespace/>dialog-confirm-content");//para notificar
			this.dialogMessageEliminar=$("#<portlet:namespace/>dialog-message-notificacion");
			
			
			
			//mensajes						
			this.mensajeNotificar='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Realizando Notificacion </h3>';			
			this.mensajeProcesando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Procesando Datos </h3>';
			this.mensajeEliminando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Eliminando </h3>';
			
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
		       colNames: ['Empresa.','Formato.','Año Pres.','Mes Pres.','Año Ejec.','Mes Ejec.','Año Ini. Vig.','Año Fin Vig.','Etapa','Ver','Anular','','',''],
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
				   	caption: "Notificacion",
				    sortorder: "asc",	   	    	   	   
		       gridComplete: function(){
		      		var ids = notificarValidar.tablaResultados.jqGrid('getDataIDs');
		      		for(var i=0;i < ids.length;i++){
		      			var cl = ids[i];
		      			var ret = notificarValidar.tablaResultados.jqGrid('getRowData',cl);	      			
		      			/* notificar = "<a href='#'><img border='0' title='Notificar' src='/net-theme/images/img-net/lock.png' align='center' onclick=\"notificar.confirmarnotificar('"+ret.codEmpresa+"','"+ret.anioPres+"','"+ret.mesPres+"');\" /></a> "; */
		      			ver = "<a href='#'><img border='0' title='Ver' src='/net-theme/images/img-net/file.png' align='center' onclick=\"notificarValidar.verObservaciones('"+ret.codEmpresa+"','"+ret.anioPres+"','"+ret.mesPres+"','"+ret.anioEjec+"','"+ret.mesEjec+"','"+ret.anioIniVig+"','"+ret.anioFinVig+"','"+ret.etapa+"','"+ret.formato+"');\" /></a> ";
		      			elim = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"notificarValidar.confirmarEliminarNotificacion('"+ret.codEmpresa+"','"+ret.anioPres+"','"+ret.mesPres+"','"+ret.anioEjec+"','"+ret.mesEjec+"','"+ret.anioIniVig+"','"+ret.anioFinVig+"','"+ret.etapa+"','"+ret.formato+"');\" /></a> ";
		      			/* notificarValidar.tablaResultados.jqGrid('setRowData',ids[i],{notificar:notificar}); */
		      			notificarValidar.tablaResultados.jqGrid('setRowData',ids[i],{ver:ver});
		      			notificarValidar.tablaResultados.jqGrid('setRowData',ids[i],{elim:elim});
		      		}
		      }
		  	});
			notificarValidar.tablaResultados.jqGrid('navGrid',notificarValidar.paginadoResultados,{add:false,edit:false,del:false,search: false,refresh: false});				
		},
		
		//Modelo de la grilla para mostrar Observaciones
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
							alert("Error de conexión.");
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
						alert("Error de conexión.");
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
					    	alert("No existe ningún registro para procesar los datos");
					    	notificarValidar.botonBuscar.trigger('click');
							notificarValidar.initBlockUI();	
					    }else{
							alert("Error al procesar los datos");
							notificarValidar.initBlockUI();
						}
					},error : function(){
						alert("Error de conexión.");
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
						notificarValidar.dialogObservacion.dialog("open");
						notificarValidar.tablaObservacion.clearGridData(true);
						notificarValidar.tablaObservacion.jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
						notificarValidar.tablaObservacion[0].refreshIndex();
						notificarValidar.initBlockUI();
					}else{
						alert("Error al realizar la validación");
						notificarValidar.initBlockUI();	
					}
				},error : function(){
					alert("Error de conexión.");
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
						alert("Error al mostrar el reporte");
						notificarValidar.initBlockUI();
					}
					
				},error : function(){
					alert("Error de conexión.");
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
			var addhtml='¿Está seguro que desea realizar la notificación?';
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
						var addhtml2='Se realizo la notificación a la empresa';					
						notificarValidar.dialogMessageContent.html(addhtml2);
						notificarValidar.dialogMessage.dialog("open");					
						notificarValidar.initBlockUI();
					}else if(data.resultado == "NO_DATOS"){
						alert("No existe ninguna lista pra realizar la notificación");
						notificarValidar.initBlockUI();	
					}else if(data.mensaje == "Mensaje"){
						alert(data.respuesta);
						notificarValidar.initBlockUI();	
					}else if(data.resultado == "ERROR"){
						alert("Error al realizar la notificación");
						notificarValidar.initBlockUI();
					}
				},error : function(){
					alert("Error de conexión.");
					notificarValidar.initBlockUI();
				}
			});
		},
		
		/**Function para confirmar si quiere eliminar el registro o no*/
		confirmarEliminarNotificacion : function(codEmpresa,anioPres,mesPres,anioEjec,mesEjec,anioIniVig,anioFinVig,codetapa,codformato){	
				var addhtml='¿Está seguro que desea eliminar el registro seleccionado?';
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
						var addhtml2='Registro eliminado con éxito';					
						notificarValidar.dialogMessageContent.html(addhtml2);
						notificarValidar.dialogMessageEliminar.dialog("open");
						notificarValidar.buscarNotificacion();
						notificarValidar.initBlockUI();
					}
					else{
						alert("Error al eliminar el registro");
						notificarValidar.initBlockUI();
					}
				},error : function(){
					alert("Error de conexión.");
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
				width: 400,			
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
				width: 400,			
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
				buttons: {
					Ok: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			notificarValidar.dialogMessageEliminar.dialog({
				modal: true,
				autoOpen: false,
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