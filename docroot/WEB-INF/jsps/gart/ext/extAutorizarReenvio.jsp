<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects />

<script type="text/javascript">

var autorizarReenvio= {
		
	    //decalaracion de los elementos del html	
		formCommand : null,	
		
		//dialogos	
		dialogMessage:null,
		dialogMessageContent:null,
		dialogConfirm:null,//para autorizar reenvio registro
		dialogConfirmContent:null,//para mostrar la confirmacion al reenviar el registro
		
		dialogError:null,
		dialogErrorContent:null,
		
		//mensajes		
		mensajeReenvio:null,		
		
		//urls
		urlBusqueda: null,			
	    urlReenvio:null,
		
		//botones		
		botonBuscar:null,				
		
		//varibales de busqueda
		i_codEmpresaBusq:null,
		i_anioPresBusq:null,
		i_mesPresBusq:null,	
		i_formatoBusq:null,
		i_etapaBusq:null,		
		
		//grillas
		tablaResultados:null,
		paginadoResultados:null,	
		
		
		
		init : function() {
			
			this.formCommand=$('#autorizarReenvioBean');		
			
			
			//dialogos
			this.dialogMessage=$("#<portlet:namespace/>dialog-message-reenvio");
			this.dialogMessageContent=$("#<portlet:namespace/>dialog-message-content-reenvio");
			this.dialogConfirm=$("#<portlet:namespace/>dialog-confirm");//para reenviar
			this.dialogConfirmContent=$("#<portlet:namespace/>dialog-confirm-content");//para reenviar
			
			this.dialogError=$("#<portlet:namespace/>dialog-error");
			this.dialogErrorContent=$("#<portlet:namespace/>dialog-error-content");	
			
			//mensajes						
			this.mensajeReenvio='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Procesando Reenvio </h3>';			
			
			//urls
			this.urlBusqueda='<portlet:resourceURL id="busquedaReenvio" />';					
			this.urlReenvio='<portlet:resourceURL id="autorizarReenvioDefinitivo" />';
			
			//botones
			this.botonBuscar=$("#<portlet:namespace/>btnBuscarFormatoReenvio");				
			
			//variables de busqueda
			this.i_codEmpresaBusq=$('#codEmpresaBusq');
			this.i_anioPresBusq=$('#anioPresBusq');
			this.i_mesPresBusq=$('#mesPresBusq');
			this.i_formatoBusq=$('#formatoBusq');
			this.i_etapaBusq=$('#etapaBusq');			
			
			//grillas			
			this.tablaResultados=$("#<portlet:namespace/>grid_resultado_busqueda");
			this.paginadoResultados='#<portlet:namespace/>paginador_resultado_busqueda';
			
			this.buildGrids();	//cargar el modelo de la grilla	
			
			
			
			//llamado a la funciones de cada boton
			autorizarReenvio.botonBuscar.click(function() {
				autorizarReenvio.buscarAutorizarReenvio();
			});			    
		    
		    autorizarReenvio.initDialogs();
		    
		    //eventos por defecto	
		    
			autorizarReenvio.botonBuscar.trigger('click');
			autorizarReenvio.initBlockUI();
		},
		//funcion para armar el modelo de la grilla para el resultado
		buildGrids : function () {	
			autorizarReenvio.tablaResultados.jqGrid({
			   datatype: "local",
		       colNames: ['Dist. Eléct.','Formato.','Año Decl.','Mes Decl.','Etapa','Estado','Aut.Reenvio','','','','','',''],
		       colModel: [
                       { name: 'desEmpresa', index: 'desEmpresa', width: 80},					   
					   { name: 'formato', index: 'formato', width: 30},
		               { name: 'anioPres', index: 'anioPres', width: 30 },   
		               { name: 'desMes', index: 'desMes', width: 30},	   
		               { name: 'etapa', index: 'etapa', width: 30},	     
		               { name: 'estado', index: 'estado', width: 20},
		               { name: 'autorizar', index: 'autorizar', width: 20,align:'center' },
		               { name: 'codEmpresa', index: 'codEmpresa', hidden: true},
		               { name: 'mesPres', index: 'mesPres', hidden: true},		              
		               { name: 'anioEjec', index: 'anioEjec', hidden: true},
		               { name: 'mesEjec', index: 'mesEjec', hidden: true},
		               { name: 'anioIniVig', index: 'mesPres', hidden: true},		              
		               { name: 'anioFinVig', index: 'anioFinVig', hidden: true}
		               
			   	    ],
			   	 multiselect: false,
					rowNum:10,
				   	rowList:[10,20,50],
					height: 200,
				   	autowidth: true,
					rownumbers: true,
					shrinkToFit:true,
					pager: autorizarReenvio.paginadoResultados,
				    viewrecords: true,
				   	caption: "Resultado(s) de la búsqueda",
				    sortorder: "asc",	   	    	   	   
		       gridComplete: function(){
		      		var ids = autorizarReenvio.tablaResultados.jqGrid('getDataIDs');
		      		for(var i=0;i < ids.length;i++){
		      			var cl = ids[i];
		      			var ret = autorizarReenvio.tablaResultados.jqGrid('getRowData',cl);	      			
		      			autorizar = "<a href='#'><img border='0' title='Aut. Reenvio' src='/net-theme/images/img-net/lock.png' align='center' onclick=\"autorizarReenvio.confirmarAutorizarReenvio('"+ret.codEmpresa+"','"+ret.anioPres+"','"+ret.mesPres+"','"+ret.anioEjec+"','"+ret.mesEjec+"','"+ret.anioIniVig+"','"+ret.anioFinVig+"','"+ret.etapa+"','"+ret.formato+"');\" /></a> ";
		      			autorizarReenvio.tablaResultados.jqGrid('setRowData',ids[i],{autorizar:autorizar});	      			
		      		}
		      }
		  	});
			autorizarReenvio.tablaResultados.jqGrid('navGrid',autorizarReenvio.paginadoResultados,{add:false,edit:false,del:false,search: false,refresh: false});				
		},
		
		//funcion para buscar
		buscarAutorizarReenvio : function () {		
			autorizarReenvio.blockUI();
			jQuery.ajax({			
					url: autorizarReenvio.urlBusqueda+'&'+autorizarReenvio.formCommand.serialize(),
					type: 'post',
					dataType: 'json',				
					success: function(gridData) {					
							autorizarReenvio.tablaResultados.clearGridData(true);
							autorizarReenvio.tablaResultados.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
							autorizarReenvio.tablaResultados[0].refreshIndex();
							autorizarReenvio.initBlockUI();
					},error : function(){
						var addhtmError='Error de conexión.';					
						autorizarReenvio.dialogErrorContent.html(addhtmError);
						autorizarReenvio.dialogError.dialog("open");
						autorizarReenvio.initBlockUI();
					}
				});			
		},			
		
		/**Function para confirmar si quiere autorizar el reenvio*/
		confirmarAutorizarReenvio : function(codEmpresa,anioPres,mesPres,anioEjec,mesEjec,anioIniVig,anioFinVig,etapa,formato){
			console.debug("entranado a confirmar reenvio:  "+codEmpresa);
			var addhtml='¿Está seguro que desea autorizar el reenvio del registro seleccionado.?';
			autorizarReenvio.dialogConfirmContent.html(addhtml);
			autorizarReenvio.dialogConfirm.dialog("open");				
			cod_Empresa=codEmpresa;
			anio_Pres =anioPres;
			mes_Pres=mesPres;
			anio_Ejec =anioEjec;
			mes_Ejec =mesEjec;
			anio_IniVig=anioIniVig;
			anio_FinVig =anioFinVig;
			cod_etapa =etapa;
			cod_formato =formato;
		},
		/**Function para  autorizar la autorizacion del reenvion una vez hecha la confirmacion*/
		autorizarReenvioDefinitivo : function(cod_Empresa,anio_Pres,mes_Pres,anio_Ejec,mes_Ejec,anio_IniVig,anio_FinVig,cod_etapa,cod_formato){
			console.debug("entranado a reeenvio definitivo:  "+cod_Empresa);
			$.blockUI({ message: autorizarReenvio.mensajeReenvio });
			jQuery.ajax({
				url: autorizarReenvio.urlReenvio+'&'+autorizarReenvio.formCommand.serialize(),
				type: 'post',
				dataType: 'json',
				data: {				
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
				success: function(data) {
					if (data.resultado == "OK"){
						var addhtml2='El Registro se reenvió con exito.';					
						autorizarReenvio.dialogMessageContent.html(addhtml2);
					    autorizarReenvio.dialogMessage.dialog("open");						
						autorizarReenvio.buscarAutorizarReenvio();
						autorizarReenvio.initBlockUI();
					}
					else{					
						var addhtmError='Error al autorizar reenvio del registro seleccionado.';					
						autorizarReenvio.dialogErrorContent.html(addhtmError);
						autorizarReenvio.dialogError.dialog("open");	
						autorizarReenvio.initBlockUI();
					}
				},error : function(){
					var addhtmError='Error de conexión.';					
					autorizarReenvio.dialogErrorContent.html(addhtmError);
					autorizarReenvio.dialogError.dialog("open");
					autorizarReenvio.initBlockUI();
				}
			});
		},		
		//DIALOGOS
		initDialogs : function(){		
			//dialogo para autorizar reenvio registro
			autorizarReenvio.dialogConfirm.dialog({
				modal: true,
				height: 200,
				width: 500,			
				autoOpen: false,
				buttons: {
					"Si": function() {
						autorizarReenvio.autorizarReenvioDefinitivo(cod_Empresa,anio_Pres,mes_Pres,anio_Ejec,mes_Ejec,anio_IniVig,anio_FinVig,cod_etapa,cod_formato);
						$( this ).dialog("close");
					},
					"No": function() {				
						$( this ).dialog("close");
					}
				}
			});	
			
			autorizarReenvio.dialogMessage.dialog({
				modal: true,
				autoOpen: false,
				buttons: {
					Ok: function() {
						$( this ).dialog("close");
					}
				}
			});		
		
			
			autorizarReenvio.dialogError.dialog({
				modal: true,
				autoOpen: false,
				width: 500,		
				buttons: {
					Aceptar: function() {
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