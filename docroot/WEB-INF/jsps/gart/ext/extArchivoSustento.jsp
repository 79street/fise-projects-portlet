<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects />

<script type="text/javascript">

var archivoSustentoVar= {
		
	    //decalaracion de los elementos del html	
		formCommand : null,	
		
		//divs
		divBuscar:null,	
		divBuscarArch:null,
		
		
		//dialogos	
		dialogMessage:null,
		dialogMessageContent:null,	
		
		
		dialogConfirm:null,//para eliminar archivos
		dialogConfirmContent:null,//para mostrar la confirmacion de eliminar archivos		
		
		dialogObservacion:null,
		dialogObservacion12:null,
		dialogObservacion13:null,			
		
		dialogValidacion:null,
		dialogValidacionContent:null,
		dialogError:null,
		dialogErrorContent:null,
		dialogInfo:null,
		dialogInfoContent:null,	
		
		dialogCargaArchivos:null,
		divOverlay:null,
		
		//mensajes		
		mensajeEliminar:null,		
		mensajeReporte:null,	
		mensajeObteniendoDatos:null,		
		mensajeGuardando:null,	
		mensajeReemplazando:null,
		
		//urls
		urlBusqueda: null,	    
	    urlCargaGrupoInf:null,	    
	    urlEliminar:null,	   
	    urlVerObservaciones:null,
	    urlVerFormatos:null,  	
		
	    urlBusquedaArchivos: null,	    
	    
		//botones		
		botonBuscar:null,			
		botonRegresarFormatos:null,	
		botonNuevoArchivo:null,
		
		botonCargarArchivoSustento:null,
		
		//varibales de busqueda
		i_codEmpresaBusq:null,
		i_grupoInfBusq:null,		
		i_tipoBienal:null,
		i_tipoMensual:null,	
		i_etapaBusq:null,
		
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
		
		tablaResultadosArchivos:null,
		paginadoResultadosArchivos:null,
		
		
		init : function() {
			
			this.formCommand=$('#archivoSustentoBean');	
			
			//divs
			this.divBuscar=$("#<portlet:namespace/>div_buscar");
			this.divBuscarArch=$("#<portlet:namespace/>div_buscar_archivos");		
			
			
			//dialogos
			this.dialogMessage=$("#<portlet:namespace/>dialog-message-archivos");
			this.dialogMessageContent=$("#<portlet:namespace/>dialog-message-content-archivos");
			
			this.dialogConfirm=$("#<portlet:namespace/>dialog-confirm-eliminar");//para elimar archivos
			this.dialogConfirmContent=$("#<portlet:namespace/>dialog-confirm-content-eliminar");//para eliminar archivos
			
			this.dialogObservacion=$("#<portlet:namespace/>dialog-form-observacion");	
			this.dialogObservacion12=$("#<portlet:namespace/>dialog-form-observacion12");
			this.dialogObservacion13=$("#<portlet:namespace/>dialog-form-observacion13");			
			
			this.dialogValidacion=$("#<portlet:namespace/>dialog-alert");	
			this.dialogValidacionContent=$("#<portlet:namespace/>dialog-alert-content");
			this.dialogError=$("#<portlet:namespace/>dialog-error");
			this.dialogErrorContent=$("#<portlet:namespace/>dialog-error-content");	
            this.dialogInfo=$("#<portlet:namespace/>dialog-info");
			this.dialogInfoContent=$("#<portlet:namespace/>dialog-info-content");
			
			this.dialogCargaArchivos=$("#<portlet:namespace/>dialog-form-cargaArchivos");
			this.divOverlay=$("#<portlet:namespace/>divOverlay");
			
			//mensajes						
			this.mensajeEliminar='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Eliminando Archivo </h3>';
			this.mensajeReporte='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Obteniendo el Reporte </h3>';
			this.mensajeObteniendoDatos='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Obteniendo Archivos </h3>';
			this.mensajeGuardando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Guardando Archivo </h3>';
			this.mensajeReemplazando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Reemplazando Archivo </h3>';
						
			//urls
			this.urlBusqueda='<portlet:resourceURL id="busquedaFormatos" />';					
			this.urlEliminar='<portlet:resourceURL id="eliminarArchivosSustento" />';
			this.urlCargaGrupoInf='<portlet:resourceURL id="cargarGrupoInformacion" />';			
			this.urlVerObservaciones='<portlet:resourceURL id="verObservacionesFormatos" />';
			this.urlVerFormatos='<portlet:resourceURL id="verFormatosReporte" />'; 		
			
			this.urlBusquedaArchivos='<portlet:resourceURL id="busquedaArchivosSustentoFormato" />'; 	
			
			
			//botones
			this.botonBuscar=$("#<portlet:namespace/>btnBuscarFormatos");		
			this.botonRegresarFormatos=$("#<portlet:namespace/>btnRegresarBusqFormatos");
			this.botonNuevoArchivo=$("#<portlet:namespace/>btnNuevoArchivoSustento");
			
			this.botonCargarArchivoSustento=$("#<portlet:namespace/>cargarArchivoSustento");
			
			//variables de busqueda
			this.i_codEmpresaBusq=$('#codEmpresaBusq');
			this.i_grupoInfBusq=$('#grupoInfBusq');				
			this.i_tipoBienal=$('#rbtBienal');
			this.i_tipoMensual=$('#rbtMensual');
			this.i_etapaBusq=$('#etapaBusq');
					
			
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
			
			
			this.tablaResultadosArchivos=$("#<portlet:namespace/>grid_resultado_busqueda_archivos");
			this.paginadoResultadosArchivos='#<portlet:namespace/>paginador_resultado_busqueda_archivos';
			this.buildGridsArchivos();	//cargar el modelo de la grilla de archivos de sustentos por formato
			
			//llamado a la funciones de cada boton
			archivoSustentoVar.botonBuscar.click(function() {			
				archivoSustentoVar.buscarFormatos('P');//P = bucar y poblando la tabla fiseArchivoCab
			});			
			
			
			archivoSustentoVar.botonRegresarFormatos.click(function() {
				archivoSustentoVar.<portlet:namespace/>regresarFormatos();
		    });	
						
			archivoSustentoVar.initDialogs();
		    
		    
			archivoSustentoVar.i_tipoBienal.change(function(){
				archivoSustentoVar.<portlet:namespace/>loadGrupoInformacion();				
			});
			
			archivoSustentoVar.i_tipoMensual.change(function(){
				archivoSustentoVar.<portlet:namespace/>loadGrupoInformacion();			
			});	
			
			
			//botones para carga de archivos
		    archivoSustentoVar.botonNuevoArchivo.click(function() {		    	
		    	archivoSustentoVar.<portlet:namespace/>mostrarFormCargaArchivoSustento();	    		
		    });
		  
			archivoSustentoVar.i_tipoMensual.trigger('change');
			
		},		
		
		
		//funcion para armar el modelo de la grilla para el resultado
		buildGrids : function () {	
			archivoSustentoVar.tablaResultados.jqGrid({
			   datatype: "local",
		       colNames: ['Dist. Eléct.','Formato','Etapa','Año Decl.','Mes Decl.','Año Ejec.','Mes Ejec.','Año Ini. Vig.','Año Fin Vig.','Ver','Ver Obs.','Archivos.','','','',''],
		       colModel: [
                       { name: 'desEmpresa', index: 'desEmpresa', width: 50},				   
					   { name: 'formato', index: 'formato', width: 20,align:'center'},
					   { name: 'etapa', index: 'etapa', width: 30,align:'center'},	  	           
		               { name: 'anioPres', index: 'anioPres', width: 30,align:'center' },  
		               { name: 'desMes', index: 'desMes', width: 30,align:'center'},
		               { name: 'anioEjec', index: 'anioEjec', width: 30,align:'center' },   
		               { name: 'desMesEje', index: 'desMesEje', width: 30,align:'center'},
		               { name: 'anioIniVig', index: 'anioIniVig', width: 30,align:'center' },   
		               { name: 'anioFinVig', index: 'anioFinVig', width: 30,align:'center'},            	              
		               { name: 'verF', index: 'verF', width: 30,align:'center' },
		               { name: 'verObs', index: 'verObs', width: 30,align:'center' },		               
		               { name: 'mostrarArch', index: 'mostrarArch', width: 30,align:'center' },	              	
		               { name: 'codEmpresa', index: 'codEmpresa', hidden: true},
		               { name: 'mesPres', index: 'mesPres', hidden: true},             
		               { name: 'mesEjec', index: 'mesEjec', hidden: true},
		               { name: 'correlativo', index: 'correlativo', hidden: true}		               
			   	    ],
			   	 multiselect: false,
					rowNum:10,
				   	rowList:[10,20,50],
					height: 225,
				   	autowidth: true,
					rownumbers: true,
					shrinkToFit:true,
					pager: archivoSustentoVar.paginadoResultados,
				    viewrecords: true,
				   	caption: "Datos para los Archivos de Sustento",
				    sortorder: "asc",	   	    	   	   
		            gridComplete: function(){
		      		var ids = archivoSustentoVar.tablaResultados.jqGrid('getDataIDs');
		      		for(var i=0;i < ids.length;i++){
		      			var cl = ids[i];
		      			var ret = archivoSustentoVar.tablaResultados.jqGrid('getRowData',cl);	        			
		      			verF = "<a href='#'><img border='0' title='Ver Formato' src='/net-theme/images/img-net/file.png' align='center' onclick=\"archivoSustentoVar.mostrarReporteFormatos('"+ret.codEmpresa+"','"+ret.anioPres+"','"+ret.mesPres+"','"+ret.anioEjec+"','"+ret.mesEjec+"','"+ret.anioIniVig+"','"+ret.anioFinVig+"','"+ret.etapa+"','"+ret.formato+"');\" /></a> ";
		      			verObs = "<a href='#'><img border='0' title='Ver Obs.' src='/net-theme/images/img-net/file.png'  align='center' onclick=\"archivoSustentoVar.verObservaciones('"+ret.codEmpresa+"','"+ret.anioPres+"','"+ret.mesPres+"','"+ret.anioEjec+"','"+ret.mesEjec+"','"+ret.anioIniVig+"','"+ret.anioFinVig+"','"+ret.etapa+"','"+ret.formato+"');\" /></a> ";
		      			mostrarArch = "<a href='#'><img border='0' title='Motivo de no Reconocimiento/Establecimiento' src='/net-theme/images/img-net/file-add.png'  align='center' onclick=\"archivoSustentoVar.mostrarArchivosFormato('"+ret.correlativo+"','"+ret.desEmpresa+"','"+ret.anioPres+"','"+ret.desMes+"','"+ret.anioEjec+"','"+ret.desMesEje+"','"+ret.anioIniVig+"','"+ret.anioFinVig+"','"+ret.etapa+"','"+ret.formato+"');\" /></a> ";
		      			
		      			archivoSustentoVar.tablaResultados.jqGrid('setRowData',ids[i],{verF:verF});
		      		    archivoSustentoVar.tablaResultados.jqGrid('setRowData',ids[i],{verObs:verObs});		      		   
		      		    archivoSustentoVar.tablaResultados.jqGrid('setRowData',ids[i],{mostrarArch:mostrarArch});	      		  	
		      		}
		      } 
		  	});
			archivoSustentoVar.tablaResultados.jqGrid('navGrid',archivoSustentoVar.paginadoResultados,{add:false,edit:false,del:false,search: false,refresh: false});				
		},
		
		//Modelo de la grilla para mostrar Observaciones para los formatos 12A,12B,14A,14B 14C
		buildGridsObservacion : function () {	
		   archivoSustentoVar.tablaObservacion.jqGrid({
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
				pager: archivoSustentoVar.paginadoObservacion,
			    viewrecords: true,			   
			    sortorder: "asc"
		  	});
		    archivoSustentoVar.tablaObservacion.jqGrid('navGrid',archivoSustentoVar.paginadoObservacion,{add:false,edit:false,del:false,search: false,refresh: false});	
		   
		},
		
		//Modelo de la grilla para mostrar Observaciones para los formatos 12C y 12D
		buildGridsObservacion12 : function () {	
		   archivoSustentoVar.tablaObservacion12.jqGrid({
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
				pager: archivoSustentoVar.paginadoObservacion12,
			    viewrecords: true,			   
			    sortorder: "asc"
		  	});
		    archivoSustentoVar.tablaObservacion12.jqGrid('navGrid',archivoSustentoVar.paginadoObservacion12,{add:false,edit:false,del:false,search: false,refresh: false});	
		   
		},
		
		//Modelo de la grilla para mostrar Observaciones para los formatos 13A
		buildGridsObservacion13 : function () {	
		   archivoSustentoVar.tablaObservacion13.jqGrid({
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
				pager: archivoSustentoVar.paginadoObservacion13,
			    viewrecords: true,			   
			    sortorder: "asc"
		  	});
		    archivoSustentoVar.tablaObservacion13.jqGrid('navGrid',archivoSustentoVar.paginadoObservacion13,{add:false,edit:false,del:false,search: false,refresh: false});	

		},	
		
		//funcion para armar el modelo de la grilla para el resultado de los archivos de sustento por formato
		buildGridsArchivos : function () {	
			var ancho = archivoSustentoVar.divBuscar.width();
			archivoSustentoVar.tablaResultadosArchivos.jqGrid({
			   datatype: "local",
		       colNames: ['Ítem','Archivo','Descargar','Reemplazar','Eliminar',''],
		       colModel: [
                       { name: 'itemArchivo', index: 'itemArchivo', width: 20,align:'center'},                       
                       { name: 'nombreArchivo', index: 'nombreArchivo', width: 80},                       					  	  	           
		               { name: 'descargar', index: 'descargar', width: 20,align:'center' },		                
		               { name: 'reemplazar', index: 'reemplazar', width: 20,align:'center' },	
		               { name: 'eliminar', index: 'eliminar', width: 20,align:'center' },	
		               { name: 'corrArchivo', index: 'corrArchivo', hidden: true}	               
			   	    ],
			   	 multiselect: false,
					rowNum:10,
				   	rowList:[10,20,50],
				   	height: 'auto',
				   //	autowidth: true,
					rownumbers: true,
					//shrinkToFit:true,
					width:ancho ,
					pager: archivoSustentoVar.paginadoResultadosArchivos,
				    viewrecords: true,
				   	caption: "Resultado(s) de la búsqueda",
				    sortorder: "desc",	   	    	   	   
		            gridComplete: function(){
		      		var ids = archivoSustentoVar.tablaResultadosArchivos.jqGrid('getDataIDs');
		      		for(var i=0;i < ids.length;i++){
		      			var cl = ids[i];
		      			var ret = archivoSustentoVar.tablaResultadosArchivos.jqGrid('getRowData',cl);	        			
		      			descargar = "<a href='#'><img border='0' title='Descargar' src='/net-theme/images/img-net/edit.png' align='center' onclick=\"archivoSustentoVar.editarMotivo('"+ret.itemArchivo+"','"+ret.corrArchivo+"');\" /></a> ";
		      			reemplazar = "<a href='#'><img border='0' title='Reemplazar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"archivoSustentoVar.confirmarEliminarMotivo('"+ret.itemArchivo+"','"+ret.corrArchivo+"');\" /></a> ";
		      			eliminar = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"archivoSustentoVar.confirmarEliminarMotivo('"+ret.itemArchivo+"','"+ret.corrArchivo+"');\" /></a> ";
		      			archivoSustentoVar.tablaResultadosArchivos.jqGrid('setRowData',ids[i],{descargar:descargar});		      		   
		      		    archivoSustentoVar.tablaResultadosArchivos.jqGrid('setRowData',ids[i],{reemplazar:reemplazar});
		      		    archivoSustentoVar.tablaResultadosArchivos.jqGrid('setRowData',ids[i],{eliminar:eliminar});
		      		}
		      } 
		  	});
			archivoSustentoVar.tablaResultadosArchivos.jqGrid('navGrid',archivoSustentoVar.paginadoResultadosArchivos,{add:false,edit:false,del:false,search: false,refresh: false});				
		},	
		
		
		//funcion para buscar
		buscarFormatos : function (flag) {	
			console.debug("entranado a buscar formatos para la subrida de archivos de sustento");
			archivoSustentoVar.blockUI();
			jQuery.ajax({			
					url: archivoSustentoVar.urlBusqueda+'&'+archivoSustentoVar.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data : {
						   <portlet:namespace />flagBusq: flag						  
					},
					success: function(gridData) {					
						archivoSustentoVar.tablaResultados.clearGridData(true);
						archivoSustentoVar.tablaResultados.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
						archivoSustentoVar.tablaResultados[0].refreshIndex();
						archivoSustentoVar.initBlockUI();
					},error : function(){
						var addhtmError='Error de conexión.';					
						archivoSustentoVar.dialogErrorContent.html(addhtmError);
						archivoSustentoVar.dialogError.dialog("open");
						archivoSustentoVar.initBlockUI();
					}
				});			
		},
		
		//function para el evento onchange en empresa para cargar el periodo
		<portlet:namespace/>loadGrupoInformacion : function(){	
			console.debug("entranado a cargar grupoInfo");
			jQuery.ajax({
					url: archivoSustentoVar.urlCargaGrupoInf+'&'+archivoSustentoVar.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					success: function(data) {		
						dwr.util.removeAllOptions("grupoInfBusq");
						dwr.util.addOptions("grupoInfBusq", data,"codigoItem","descripcionItem");														
					},error : function(){
						var addhtmError='Error de conexión.';					
						archivoSustentoVar.dialogErrorContent.html(addhtmError);
						archivoSustentoVar.dialogError.dialog("open");
						archivoSustentoVar.initBlockUI();
					}
			});
		},	
	
		//funcion para ver observaciones de los formatos
		verObservaciones : function(cod_Empresa,anio_Pres,mes_Pres,anio_Ejec,mes_Ejec,anio_IniVig,anio_FinVig,cod_etapa,cod_formato){
			jQuery.ajax({
				url: archivoSustentoVar.urlVerObservaciones+'&'+archivoSustentoVar.formCommand.serialize(),
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
							archivoSustentoVar.dialogObservacion12.dialog("open");
							archivoSustentoVar.tablaObservacion12.clearGridData(true);
							archivoSustentoVar.tablaObservacion12.jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
							archivoSustentoVar.tablaObservacion12[0].refreshIndex();
							archivoSustentoVar.initBlockUI();	
						}else if(cod_formato=='F13A'){
							console.debug("entrando en formato13A ");
							archivoSustentoVar.dialogObservacion13.dialog("open");
							archivoSustentoVar.tablaObservacion13.clearGridData(true);
							archivoSustentoVar.tablaObservacion13.jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
							archivoSustentoVar.tablaObservacion13[0].refreshIndex();
							archivoSustentoVar.initBlockUI();	
						}else{
							archivoSustentoVar.dialogObservacion.dialog("open");
							archivoSustentoVar.tablaObservacion.clearGridData(true);
							archivoSustentoVar.tablaObservacion.jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
							archivoSustentoVar.tablaObservacion[0].refreshIndex();
							archivoSustentoVar.initBlockUI();	
						}						
					}else{						
						var addhtmError='Error al Ver las Observaciones del registro seleccionado.';					
						archivoSustentoVar.dialogErrorContent.html(addhtmError);
						archivoSustentoVar.dialogError.dialog("open");	
						archivoSustentoVar.initBlockUI();	
					}
				},error : function(){
					var addhtmError='Error de conexión.';					
					archivoSustentoVar.dialogErrorContent.html(addhtmError);
					archivoSustentoVar.dialogError.dialog("open");
					archivoSustentoVar.initBlockUI();
				}
			});
		}, 		
		
		//funcion para mostrar el reporte de cada formato	
		mostrarReporteFormatos : function(cod_Empresa,anio_Pres,mes_Pres,anio_Ejec,mes_Ejec,anio_IniVig,anio_FinVig,cod_etapa,cod_formato){
			console.debug("entranado a  ver el reporte del formato");
			$.blockUI({ message: archivoSustentoVar.mensajeReporte});
			jQuery.ajax({
				url: archivoSustentoVar.urlVerFormatos+'&'+archivoSustentoVar.formCommand.serialize(),
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
						archivoSustentoVar.verReporteFormato();	
						archivoSustentoVar.initBlockUI();
					}else{						
						var addhtmError='Error al ver el reporte del formato seleccionado.';					
						archivoSustentoVar.dialogErrorContent.html(addhtmError);
						archivoSustentoVar.dialogError.dialog("open");
						archivoSustentoVar.initBlockUI();
					}
				},error : function(){
					var addhtmError='Error de conexión.';					
					archivoSustentoVar.dialogErrorContent.html(addhtmError);
					archivoSustentoVar.dialogError.dialog("open");
					archivoSustentoVar.initBlockUI();
				}
			});
		}, 		
		
				
		//funcion para ver reposrte en una nueva pestaña
		verReporteFormato : function(){
			window.open('<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ViewReport")%>','_newtab');
		}, 
		
				
		//funcion  para mostrar detalle de los archivos de sustento
		mostrarArchivosFormato : function(cod_correlativo,empresa,anio_pres,mes_pres,anio_ejec,mes_ejec,anio_inicio,anio_fin,etapa_final,formato){	
			
			$('#empresaArchivo').val(empresa);
			$('#anioArchivo').val(anio_pres);
			$('#mesArchivo').val(mes_pres);
			$('#anioEjecArchivo').val(anio_ejec);
			$('#mesEjecArchivo').val(mes_ejec);
			$('#anioInicioVigArchivo').val(anio_inicio);
			$('#anioFinVigArchivo').val(anio_fin);
			$('#etapaArchivo').val(etapa_final);
			$('#formatoArchivo').val(formato);					
				
			//$('#coMotivo').val(cod_correlativo);//para el nuevo registro del archivo de sustento*
			
			$('#tituloBusquedaArchivo').val('Datos principales del Formato: '+formato);
						
			archivoSustentoVar.divBuscarArch.show();
			archivoSustentoVar.divBuscar.hide();		
			archivoSustentoVar.buscarArchivosSustentoFormato(cod_correlativo);			
		},
		
		
		//funcion para buscar los archivos de sustento de cada formato
		buscarArchivosSustentoFormato : function (cod_correlativo) {	
			console.debug("entranado a buscar archivos por formatos");
			archivoSustentoVar.blockUI();
			jQuery.ajax({			
					url: archivoSustentoVar.urlBusquedaArchivos+'&'+archivoSustentoVar.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data : {
						   <portlet:namespace />correlativo: cod_correlativo						  
					},
					success: function(gridData) {					
						archivoSustentoVar.tablaResultadosArchivos.clearGridData(true);
						archivoSustentoVar.tablaResultadosArchivos.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
						archivoSustentoVar.tablaResultadosArchivos[0].refreshIndex();
						archivoSustentoVar.initBlockUI();
					},error : function(){
						var addhtmError='Error de conexión.';					
						archivoSustentoVar.dialogErrorContent.html(addhtmError);
						archivoSustentoVar.dialogError.dialog("open");
						archivoSustentoVar.initBlockUI();
					}
				});			
		},	
		
		
		
		<portlet:namespace/>mostrarFormCargaArchivoSustento : function(){
			if (archivoSustentoVar.validarArchivoCarga()){
				/*if( archivoSustentoVar.flagCarga.val()=='0' ){//proviene de archivos nuevos
					archivoSustentoVar.flagCarga.val('2');//para cargar archivos excel
				}else if(archivoSustentoVar.flagCarga.val()=='1' ){//proviene de archivos modificados
					archivoSustentoVar.flagCarga.val('3');//para cargar archivos excel
				}*/
				archivoSustentoVar.divOverlay.show();
				archivoSustentoVar.dialogCargaArchivos.show();			   
				archivoSustentoVar.dialogCargaArchivos.draggable();
				archivoSustentoVar.dialogCargaArchivos.css({ 
			        'left': ($(window).width() / 2 - archivoSustentoVar.dialogCargaArchivos.width() / 2) + 'px', 
			        'top': ($(window).height()  - archivoSustentoVar.dialogCargaArchivos.height() ) + 'px'
			    });
			}
		},		
		
		validarArchivoCarga : function() {			
			/*if(archivoSustentoVar.f_empresa.val()==null || 
					archivoSustentoVar.f_empresa.val().length == '' ) { 			
				var addhtmAlert='Seleccione una Distribuidora Eléctrica para proceder con la carga de archivo.';					
				archivoSustentoVar.dialogValidacionContent.html(addhtmAlert);
				archivoSustentoVar.dialogValidacion.dialog("open");				
				return false; 
			}
			if(archivoSustentoVar.f_periodoEnvio.val()==null || 
					archivoSustentoVar.f_periodoEnvio.val().length == '' ) {					
				var addhtmAlert='Debe ingresar el Periodo a Declarar.';					
				archivoSustentoVar.dialogValidacionContent.html(addhtmAlert);
				archivoSustentoVar.dialogValidacion.dialog("open");				
				return false; 
			}*/
			return true; 
		},
		
		
		
		//funcion para regresar a buscar formatos
		<portlet:namespace/>regresarFormatos : function(){			
			archivoSustentoVar.divBuscar.show();
			archivoSustentoVar.divBuscarArch.hide();		
		},
		
		regresarFormularioCargaArchivos : function(){
			//archivoSustentoVar.flagCarga.val('');
			archivoSustentoVar.dialogCargaArchivos.hide();
			archivoSustentoVar.divOverlay.hide();   
			$("#msjUploadFile").html("");			
		},
				
		
		//DIALOGOS
		initDialogs : function(){		
			//dialogo para eliminar archivo de sustento
			archivoSustentoVar.dialogConfirm.dialog({
				modal: true,
				height: 200,
				width: 450,			
				autoOpen: false,
				buttons: {
					"Si": function() {
						//archivoSustentoVar.eliminarAchivoSustento(id_Correlativo);
						$( this ).dialog("close");
					},
					"No": function() {				
						$( this ).dialog("close");
					}
				}
			});			
			
			
			archivoSustentoVar.dialogMessage.dialog({
				modal: true,
				autoOpen: false,
				width: 450,		
				buttons: {
					Ok: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			 archivoSustentoVar.dialogObservacion.dialog({
				modal: true,
				width: 700,
				autoOpen: false,
				buttons: {
					Cerrar: function() {
						$( this ).dialog("close");
					}
				}
			}); 
			 
			 archivoSustentoVar.dialogObservacion12.dialog({
					modal: true,
					width: 740,
					autoOpen: false,
					buttons: {
						Cerrar: function() {
							$( this ).dialog("close");
						}
					}
				}); 
			 
			 archivoSustentoVar.dialogObservacion13.dialog({
					modal: true,
					width: 730,
					autoOpen: false,
					buttons: {
						Cerrar: function() {
							$( this ).dialog("close");
						}
					}
				}); 		
			 
			 archivoSustentoVar.dialogValidacion.dialog({
					modal: true,
					autoOpen: false,
					width:450,
					buttons: {
						Aceptar: function() {
							archivoSustentoVar.ponerFocus(cod_focus);
							$( this ).dialog("close");
						}
					}
				});
				
			 archivoSustentoVar.dialogError.dialog({
					modal: true,
					autoOpen: false,
					width: 450,		
					buttons: {
						Aceptar: function() {
							$( this ).dialog("close");
						}
					}
				});
				
			 archivoSustentoVar.dialogInfo.dialog({
					modal: true,
					autoOpen: false,
					width: 450,		
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