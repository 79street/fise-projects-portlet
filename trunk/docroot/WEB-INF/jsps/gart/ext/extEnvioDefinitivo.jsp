<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects />

<script type="text/javascript">

var envioDefinitivoGlobal= {
		
	    //decalaracion de los elementos del html	
		formCommand : null,	
		
		//dialogos	
		dialogMessage:null,
		dialogMessageContent:null,
		dialogConfirm:null,//para realizar el envio
		dialogConfirmContent:null,//para mostrar la confirmacion del envio		
		//dialogObservacion:null,
		
		dialogValidacion:null,
		dialogValidacionContent:null,
		dialogError:null,
		dialogErrorContent:null,
		dialogInfo:null,
		dialogInfoContent:null,
		
		
		//mensajes		
		mensajeEnvio:null,	
		mensajeReporte:null,
		
		//urls
		urlBusqueda: null,	    
	    urlCargaGrupoInf:null,
	    //urlVerObservaciones:null,		
		//urlReporteObservaciones:null,
		urlEnvioDefinitivo:null,
		urlReporteEnvioDefinitivo:null,
		urlVerFormatos:null,
		
		
		//botones		
		botonBuscar:null,		
		botonEnvioDefinitivo:null,
		
		//varibales de busqueda
		i_codEmpresaBusq:null,
		i_grupoInfBusq:null,
		i_etapaBusq:null,
		i_tipoBienal:null,
		i_tipoMensual:null,
		
		//variables 
		f_empresa:null,
		f_flagExisteData:null,
		
		//grillas
		tablaResultados:null,
		paginadoResultados:null,	
		//tablaObservacion:null,	
		//paginadoObservacion:null,
		
		
		init : function() {
			
			this.formCommand=$('#envioDefinitivoBean');	
			
			
			//dialogos
			this.dialogMessage=$("#<portlet:namespace/>dialog-message-envio");
			this.dialogMessageContent=$("#<portlet:namespace/>dialog-message-content-envio");
			this.dialogConfirm=$("#<portlet:namespace/>dialog-confirm_envio");//para notificar
			this.dialogConfirmContent=$("#<portlet:namespace/>dialog-confirm-content_envio");//para notificar
			//this.dialogObservacion=$("#<portlet:namespace/>dialog-form-observacion");		
			
			this.dialogValidacion=$("#<portlet:namespace/>dialog-alert");	
			this.dialogValidacionContent=$("#<portlet:namespace/>dialog-alert-content");
			this.dialogError=$("#<portlet:namespace/>dialog-error");
			this.dialogErrorContent=$("#<portlet:namespace/>dialog-error-content");	
            this.dialogInfo=$("#<portlet:namespace/>dialog-info");
			this.dialogInfoContent=$("#<portlet:namespace/>dialog-info-content");
			
			//mensajes						
			this.mensajeEnvio='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Procesando Envío General Definitivo </h3>';			
			this.mensajeReporte='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Obteniendo el Reporte </h3>';
			
			//urls
			this.urlBusqueda='<portlet:resourceURL id="busquedaEnvioDefinitivo" />';					
			this.urlEnvioDefinitivo='<portlet:resourceURL id="envioDefinitivoGeneral" />';
			this.urlCargaGrupoInf='<portlet:resourceURL id="cargarGrupoInformacion" />';
			//this.urlVerObservaciones='<portlet:resourceURL id="verObservacionesValidacion" />';
			//this.urlReporteObservaciones='<portlet:resourceURL id="reporteValidacionNotificacion" />';
			this.urlReporteEnvioDefinitivo='<portlet:resourceURL id="reporteEnvioDefinitivo" />';
			this.urlVerFormatos='<portlet:resourceURL id="verFormatosReporteEnvio" />'; 
			
			
			//botones
			this.botonBuscar=$("#<portlet:namespace/>btnBuscarEnvioDefinitivo");		
			this.botonEnvioDefinitivo=$("#<portlet:namespace/>btnEnvioDefinitivo");				
			
			//variables de busqueda
			this.i_codEmpresaBusq=$('#codEmpresaBusq');
			this.i_grupoInfBusq=$('#grupoInfBusq');	
			this.i_etapaBusq=$('#etapaBusq');	
			this.i_tipoBienal=$('#rbtBienal');
			this.i_tipoMensual=$('#rbtMensual');
			
			this.f_flagExisteData=$('#flagExiteData');		
			
			//grillas			
			this.tablaResultados=$("#<portlet:namespace/>grid_resultado_busqueda");
			this.paginadoResultados='#<portlet:namespace/>paginador_resultado_busqueda';
			this.buildGrids();	//cargar el modelo de la grilla			
			
			//this.tablaObservacion=$("#<portlet:namespace/>grid_observacion");			
			//this.paginadoObservacion='#<portlet:namespace/>pager_observacion';
			//this.buildGridsObservacion();
			
			
			
			
			
			//llamado a la funciones de cada boton
			envioDefinitivoGlobal.botonBuscar.click(function() {			
				envioDefinitivoGlobal.buscarEnvioDefinitivo();
			});
			
			
			envioDefinitivoGlobal.botonEnvioDefinitivo.click(function() {			
				envioDefinitivoGlobal.confirmarEnvioDefinitivo();
			});
			
			
			envioDefinitivoGlobal.initDialogs();
		    
		    
			envioDefinitivoGlobal.i_tipoBienal.change(function(){
				envioDefinitivoGlobal.<portlet:namespace/>loadGrupoInformacion();
			});
			
			envioDefinitivoGlobal.i_tipoMensual.change(function(){
				envioDefinitivoGlobal.<portlet:namespace/>loadGrupoInformacion();
			});
		    
		    //eventos por defecto	
		    
			//envioDefinitivoGlobal.botonBuscar.trigger('click');
			//envioDefinitivoGlobal.initBlockUI();
		},
		//funcion para armar el modelo de la grilla para el resultado
		buildGrids : function () {	
			envioDefinitivoGlobal.tablaResultados.jqGrid({
			   datatype: "local",
		       colNames: ['Dist. Eléct.','Formato','Año Decl.','Mes Decl.','Año Ejec.','Mes Ejec.','Año Ini. Vig.','Año Fin Vig.','Etapa','Estado','Ver','','',''],
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
		               { name: 'estado', index: 'estado', width: 20,align:'center' }, 
		               { name: 'ver', index: 'ver', width: 20,align:'center' },
		               /*{ name: 'elim', index: 'elim', width: 20,align:'center' },*/	              
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
					pager: envioDefinitivoGlobal.paginadoResultados,
				    viewrecords: true,
				   	caption: "Resultado(s) de la búsqueda",
				    sortorder: "asc",	   	    	   	   
		         gridComplete: function(){
		      		var ids = envioDefinitivoGlobal.tablaResultados.jqGrid('getDataIDs');
		      		for(var i=0;i < ids.length;i++){
		      			var cl = ids[i];
		      			var ret = envioDefinitivoGlobal.tablaResultados.jqGrid('getRowData',cl);	    			
		      			ver = "<a href='#'><img border='0' title='Ver' src='/net-theme/images/img-net/file.png' align='center' onclick=\"envioDefinitivoGlobal.mostrarFormatosReporte('"+ret.codEmpresa+"','"+ret.anioPres+"','"+ret.mesPres+"','"+ret.anioEjec+"','"+ret.mesEjec+"','"+ret.anioIniVig+"','"+ret.anioFinVig+"','"+ret.etapa+"','"+ret.formato+"');\" /></a> ";
		      			envioDefinitivoGlobal.tablaResultados.jqGrid('setRowData',ids[i],{ver:ver});		    
		      		}
		      } 
		  	});
			envioDefinitivoGlobal.tablaResultados.jqGrid('navGrid',envioDefinitivoGlobal.paginadoResultados,{add:false,edit:false,del:false,search: false,refresh: false});				
		},
		
		//Modelo de la grilla para mostrar Observaciones
		<%-- buildGridsObservacion : function () {	
		   envioDefinitivoGlobal.tablaObservacion.jqGrid({
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
				pager: envioDefinitivoGlobal.paginadoObservacion,
			    viewrecords: true,			   
			    sortorder: "asc"
		  	});
		    envioDefinitivoGlobal.tablaObservacion.jqGrid('navGrid',envioDefinitivoGlobal.paginadoObservacion,{add:false,edit:false,del:false,search: false,refresh: false});	
		    envioDefinitivoGlobal.tablaObservacion.jqGrid('navButtonAdd',envioDefinitivoGlobal.paginadoObservacion,{
			       caption:"Exportar a Excel",
			       buttonicon: "ui-icon-bookmark",
			       onClickButton : function () {
			            location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>'; 
			       } 
			}); 
		    envioDefinitivoGlobal.tablaObservacion.jqGrid('navButtonAdd',envioDefinitivoGlobal.paginadoObservacion,{
			       caption:"Exportar a Pdf",
			       buttonicon: "ui-icon-bookmark",
			       onClickButton : function () {
			    	   envioDefinitivoGlobal.<portlet:namespace/>mostrarReporteObservaciones();
			       } 
			});
		}, --%>
		
		//funcion para buscar
		buscarEnvioDefinitivo : function () {	
			console.debug("entranado a buscar function");
			envioDefinitivoGlobal.blockUI();
			jQuery.ajax({			
					url: envioDefinitivoGlobal.urlBusqueda+'&'+envioDefinitivoGlobal.formCommand.serialize(),
					type: 'post',
					dataType: 'json',				
					success: function(gridData) {					
						envioDefinitivoGlobal.tablaResultados.clearGridData(true);
						envioDefinitivoGlobal.tablaResultados.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
						envioDefinitivoGlobal.tablaResultados[0].refreshIndex();
						envioDefinitivoGlobal.initBlockUI();
						console.debug("resultado de la busqueda: "+gridData);
						if(gridData!=''){
							envioDefinitivoGlobal.f_flagExisteData.val('SI');	
						}						
					},error : function(){
						var addhtmError='Error de conexión.';					
						envioDefinitivoGlobal.dialogErrorContent.html(addhtmError);
						envioDefinitivoGlobal.dialogError.dialog("open");
						envioDefinitivoGlobal.initBlockUI();
					}
				});			
		},			
		//function para el evento onchange en empresa para cargar el periodo
		<portlet:namespace/>loadGrupoInformacion : function(){	
			console.debug("entranado a cargar grupoInfo");
			jQuery.ajax({
					url: envioDefinitivoGlobal.urlCargaGrupoInf+'&'+envioDefinitivoGlobal.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					success: function(data) {		
						dwr.util.removeAllOptions("grupoInfBusq");
						dwr.util.addOptions("grupoInfBusq", data,"codigoItem","descripcionItem");														
					},error : function(){
						var addhtmError='Error de conexión.';					
						envioDefinitivoGlobal.dialogErrorContent.html(addhtmError);
						envioDefinitivoGlobal.dialogError.dialog("open");
						envioDefinitivoGlobal.initBlockUI();
					}
			});
		},	
		
		mostrarFormatosReporte : function(cod_Empresa,anio_Pres,mes_Pres,anio_Ejec,mes_Ejec,anio_IniVig,anio_FinVig,cod_etapa,cod_formato){
			console.debug("entranado a  ver el reporte del formato");
			$.blockUI({ message: envioDefinitivoGlobal.mensajeReporte});
			jQuery.ajax({
				url: envioDefinitivoGlobal.urlVerFormatos+'&'+envioDefinitivoGlobal.formCommand.serialize(),
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
					if(data.resultado=='OK'){
						envioDefinitivoGlobal.verReporteEnvio();	
						envioDefinitivoGlobal.initBlockUI();
					}else{						
						var addhtmError='Error al mostrar el reporte del registro seleccionado.';					
						envioDefinitivoGlobal.dialogErrorContent.html(addhtmError);
						envioDefinitivoGlobal.dialogError.dialog("open");	
						envioDefinitivoGlobal.initBlockUI();
					}
				},error : function(){
					var addhtmError='Error de conexión.';					
					envioDefinitivoGlobal.dialogErrorContent.html(addhtmError);
					envioDefinitivoGlobal.dialogError.dialog("open");
					envioDefinitivoGlobal.initBlockUI();
				}
			});
		}, 		
		
		
		/* verObservaciones : function(cod_Empresa,anio_Pres,mes_Pres,anio_Ejec,mes_Ejec,anio_IniVig,anio_FinVig,cod_etapa,cod_formato){
			jQuery.ajax({
				url: envioDefinitivoGlobal.urlVerObservaciones+'&'+envioDefinitivoGlobal.formCommand.serialize(),
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
						envioDefinitivoGlobal.dialogObservacion.dialog("open");
						envioDefinitivoGlobal.tablaObservacion.clearGridData(true);
						envioDefinitivoGlobal.tablaObservacion.jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
						envioDefinitivoGlobal.tablaObservacion[0].refreshIndex();
						envioDefinitivoGlobal.initBlockUI();
					}else{
						alert("Error al realizar la validación");
						envioDefinitivoGlobal.initBlockUI();	
					}
				},error : function(){
					alert("Error de conexión.");
					envioDefinitivoGlobal.initBlockUI();
				}
			});
		}, */		
		
		/* <portlet:namespace/>mostrarReporteObservaciones : function(){
			jQuery.ajax({
				url: envioDefinitivoGlobal.urlReporteObservaciones+'&'+envioDefinitivoGlobal.formCommand.serialize(),
				type : 'post',
				dataType : 'json',
				data : {					
					<portlet:namespace />nombreReporte: 'validacion',
					<portlet:namespace />nombreArchivo: 'validacion',
					<portlet:namespace />tipoArchivo: '0'//PDF
				},
				success : function(gridData) {
					if(gridData!=null){
						envioDefinitivoGlobal.verReporteObservaciones();	
					}else{
						alert("Error al mostrar el reporte");
						envioDefinitivoGlobal.initBlockUI();
					}
					
				},error : function(){
					alert("Error de conexión.");
					envioDefinitivoGlobal.initBlockUI();
				}
			});
		}, */	
					
		/**Function para confirmar si quiere realizar el envio definitivo.*/
		confirmarEnvioDefinitivo : function(){
			console.debug("entranado a confirmar envio: "+envioDefinitivoGlobal.f_flagExisteData.val());			
			if(envioDefinitivoGlobal.f_flagExisteData.val()=='SI'){
				var addhtml='¿Está seguro que desea realizar el Envío General Definitivo de el(los) Formato(s) presentado(s) en el Resultado de la búsqueda?';
				envioDefinitivoGlobal.dialogConfirmContent.html(addhtml);
				envioDefinitivoGlobal.dialogConfirm.dialog("open");	
			}else{			
				var addhtmInfo='No existe información para los criterios de búsqueda seleccionados. Realice una nueva búsqueda.';					
				envioDefinitivoGlobal.dialogInfoContent.html(addhtmInfo);
				envioDefinitivoGlobal.dialogInfo.dialog("open");		
				envioDefinitivoGlobal.initBlockUI();	
			}			
		},
		/**Function para  envio definitivo una vez hecha la confirmacion*/
		procesarEnvioDefinitivo : function(){
			console.debug("entranado a envio definitivo ");			
			var desGrupo = document.getElementById('grupoInfBusq')[document.getElementById('grupoInfBusq').selectedIndex].innerHTML;
			console.debug("desc grupo inf. seleccionado:  "+desGrupo);
			$.blockUI({ message: envioDefinitivoGlobal.mensajeEnvio});
			jQuery.ajax({
				url: envioDefinitivoGlobal.urlEnvioDefinitivo+'&'+envioDefinitivoGlobal.formCommand.serialize(),
				type: 'post',
				dataType: 'json',
				data: {				
				     <portlet:namespace />codEmpresa: envioDefinitivoGlobal.i_codEmpresaBusq.val(),
				     <portlet:namespace />descGrupoInf: desGrupo
					},
				success: function(data) {
					if(data.resultado == "OK"){
						var addhtml2='El Envío General Definitivo se realizó satisfactoriamente a los siguientes correos electrónicos: '+data.Correo; 
						envioDefinitivoGlobal.dialogMessageContent.html(addhtml2);
						envioDefinitivoGlobal.dialogMessage.dialog("open");					
						envioDefinitivoGlobal.initBlockUI();
						envioDefinitivoGlobal.botonBuscar.trigger('click');
					}else if(data.resultado == "ENVIADO"){						
						var addhtmInfo='Este grupo de formatos ya ha sido enviado.';					
						envioDefinitivoGlobal.dialogInfoContent.html(addhtmInfo);
						envioDefinitivoGlobal.dialogInfo.dialog("open");
						envioDefinitivoGlobal.initBlockUI();
						envioDefinitivoGlobal.botonBuscar.trigger('click');
					}else if(data.resultado == "NO_DATOS"){						
						var addhtmInfo='No existe ninguna lista para realizar el Envío General Definitivo. Realice una nueva búsqueda.';					
						envioDefinitivoGlobal.dialogInfoContent.html(addhtmInfo);
						envioDefinitivoGlobal.dialogInfo.dialog("open");
						envioDefinitivoGlobal.initBlockUI();	
					}else if(data.resultado == "EMAIL"){						
						var addhtmEmail = data.correo;					
						envioDefinitivoGlobal.dialogErrorContent.html(addhtmEmail);
						envioDefinitivoGlobal.dialogError.dialog("open");		
						envioDefinitivoGlobal.botonBuscar.trigger('click');
						envioDefinitivoGlobal.initBlockUI();	
					}else if(data.resultado == "CERRADO"){
						var addhtmInfo=data.mensaje;					
						envioDefinitivoGlobal.dialogInfoContent.html(addhtmInfo);
						envioDefinitivoGlobal.dialogInfo.dialog("open");	
						envioDefinitivoGlobal.botonBuscar.trigger('click');
						envioDefinitivoGlobal.initBlockUI();	
					}else if(data.resultado == "OBSERVACION"){						
						var addhtmInfo='No se puede realizar el Envío Definitivo debido a que uno de los Formatos tiene observaciones, primero debe subsanar las observaciones.';					
						envioDefinitivoGlobal.dialogInfoContent.html(addhtmInfo);
						envioDefinitivoGlobal.dialogInfo.dialog("open");
						envioDefinitivoGlobal.initBlockUI();	
					}else if(data.resultado == "ERROR"){						
						var addhtmError='Error al realizar el Envío General Definitivo de los formatos presentados.';					
						envioDefinitivoGlobal.dialogErrorContent.html(addhtmError);
						envioDefinitivoGlobal.dialogError.dialog("open");	
						envioDefinitivoGlobal.botonBuscar.trigger('click');
						envioDefinitivoGlobal.initBlockUI();
					}
				},error : function(){
					var addhtmError='Error de conexión.';					
					envioDefinitivoGlobal.dialogErrorContent.html(addhtmError);
					envioDefinitivoGlobal.dialogError.dialog("open");
					envioDefinitivoGlobal.initBlockUI();
				}
			});
		},		
		//funcion para ver el reporte de envio definitivo
		<portlet:namespace/>mostrarReporteEnvioDefinitivo : function(){
			jQuery.ajax({
				url: envioDefinitivoGlobal.urlReporteEnvioDefinitivo+'&'+envioDefinitivoGlobal.formCommand.serialize(),
				type : 'post',
				dataType : 'json',
				data : {
					<portlet:namespace />tipoArchivo: '2'//PDF+concatenado
				},
				success : function(gridData) {
					envioDefinitivoGlobal.verReporteEnvio();
				},error : function(){
					var addhtmError='Error de conexión.';					
					envioDefinitivoGlobal.dialogErrorContent.html(addhtmError);
					envioDefinitivoGlobal.dialogError.dialog("open");
					envioDefinitivoGlobal.initBlockUI();
				}
			});
		},		
		//funcion para ver reporte acta envio en una nueva pestaña
		verReporteEnvio : function(){
			window.open('<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ViewReport")%>','_newtab');
		}, 	
		
		//DIALOGOS
		initDialogs : function(){		
			//dialogo para realizar el envio
			envioDefinitivoGlobal.dialogConfirm.dialog({
				modal: true,
				height: 200,
				width: 450,
				autoOpen: false,
				buttons: {
					"Si": function() {
						envioDefinitivoGlobal.procesarEnvioDefinitivo();
						$( this ).dialog("close");
					},
					"No": function() {				
						$( this ).dialog("close");
					}
				}
			});
			
			envioDefinitivoGlobal.dialogMessage.dialog({
				modal: true,
				autoOpen: false,
				width: 450,
				buttons: {
					'Ver Acta': function() {
						envioDefinitivoGlobal.<portlet:namespace/>mostrarReporteEnvioDefinitivo();
						$( this ).dialog("close");							   
					},
					Ok: function() {
						$( this ).dialog("close");
					}
				}
			});	
			
			envioDefinitivoGlobal.dialogValidacion.dialog({
				modal: true,
				autoOpen: false,
				width: 450,		
				buttons: {
					Aceptar: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			envioDefinitivoGlobal.dialogError.dialog({
				modal: true,
				autoOpen: false,
				width: 450,		
				buttons: {
					Aceptar: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			envioDefinitivoGlobal.dialogInfo.dialog({
				modal: true,
				autoOpen: false,
				width: 450,		
				buttons: {
					OK: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			/* envioDefinitivoGlobal.dialogObservacion.dialog({
				modal: true,
				width: 700,
				autoOpen: false,
				buttons: {
					Cerrar: function() {
						$( this ).dialog("close");
					}
				}
			}); */
			
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