<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects />

<script type="text/javascript">

var liquidacionVar= {
		
	    //decalaracion de los elementos del html	
		formCommand : null,	
		
		//divs
		divBuscarLiq:null,	
		divBuscarMotivo:null,	
		divNuevoMotivo:null,	
		
		
		//dialogos	
		dialogMessage:null,
		dialogMessageContent:null,
		
		dialogConfirm:null,//para eliminar
		dialogConfirmContent:null,//para mostrar la confirmacion de eliminar		
		dialogObservacion:null,
		dialogObservacion12:null,
		dialogObservacion13:null,
		
		dialogConfirmEstablecer:null,
		dialogConfirmEstablecerContent:null,
		dialogConfirmLiquidar:null,
		dialogConfirmLiquidarContent:null,
		
		dialogConfirmMotivo:null,//para eliminar
		dialogConfirmContentMotivo:null,//para mostrar la confirmacion de eliminar
		
		
		dialogValidacion:null,
		dialogValidacionContent:null,
		dialogError:null,
		dialogErrorContent:null,
		dialogInfo:null,
		dialogInfoContent:null,
		
		
		
		//mensajes		
		mensajeEliminar:null,
		mensajePrepara:null,
		mensajeLiquidar:null,
		mensajeReporte:null,
		
		mensajeObteniendoDatos:null,
		mensajeEliminando:null,
		mensajeGuardando:null,	
		mensajeActualizando:null,
		
		//urls
		urlBusqueda: null,	    
	    urlCargaGrupoInf:null,	    
	    urlEliminar:null,
	    urlPreparaLiquidacion:null,
	    urlLiquidar:null,
	    urlVerObservaciones:null,
	    urlVerFormatos:null,
		//urlReporteObservaciones:null,
		//urlEnvioDefinitivo:null,
		//urlReporteEnvioDefinitivo:null,
		
		/***para motivos de la liquidacion**/
		urlBusquedaMotivo: null,	    
	    urlGrabarMotivo:null,	    
	    urlEliminarMotivo:null,
	    urlEditarMotivo:null,
	    urlActualizarMotivo:null,
		
		
		//botones		
		botonBuscar:null,		
		botonGenerarEtapa:null,
		botonLiquidar:null,
		botonRegresarLiqui:null,	
		/***para motivos de la liquidacion**/	
		botonNuevoMotivo:null,	
		botonRegresarMotivo:null,		
		botonGrabarMotivo:null,
		botonActualizarMotivo:null,	
		
		//varibales de busqueda
		i_codEmpresaBusq:null,
		i_grupoInfBusq:null,		
		i_tipoBienal:null,
		i_tipoMensual:null,	
		
		//variables 
		f_empresa:null,
		
		/***para motivos de la liquidacion**/
		f_codCorrelativo:null,
		f_codItem:null,
		f_descMotivo:null,
		
		
		//grillas
		tablaResultados:null,
		paginadoResultados:null,	
		
		tablaObservacion:null,	
		paginadoObservacion:null,
		
		tablaObservacion12:null,	
		paginadoObservacion12:null,
		
		tablaObservacion13:null,	
		paginadoObservacion13:null,
		
		/***para motivos de la liquidacion**/
		tablaResultadosMotivo:null,
		paginadoResultadosMotivo:null,
		
		init : function() {
			
			this.formCommand=$('#liquidacionBean');	
			
			//divs
			this.divBuscarLiq=$("#<portlet:namespace/>div_buscar");
			this.divBuscarMotivo=$("#<portlet:namespace/>div_buscar_motivo");
			this.divNuevoMotivo=$("#<portlet:namespace/>div_nuevo_motivo");
			
			
			//dialogos
			this.dialogMessage=$("#<portlet:namespace/>dialog-message-liquidacion");
			this.dialogMessageContent=$("#<portlet:namespace/>dialog-message-content-liquidacion");
			
			this.dialogConfirm=$("#<portlet:namespace/>dialog-confirm-eliminar");//para elimar
			this.dialogConfirmContent=$("#<portlet:namespace/>dialog-confirm-content-eliminar");//para eliminar
			this.dialogObservacion=$("#<portlet:namespace/>dialog-form-observacion");	
			this.dialogObservacion12=$("#<portlet:namespace/>dialog-form-observacion12");
			this.dialogObservacion13=$("#<portlet:namespace/>dialog-form-observacion13");
			
			this.dialogConfirmEstablecer=$("#<portlet:namespace/>dialog-confirm-establecer");
			this.dialogConfirmEstablecerContent=$("#<portlet:namespace/>dialog-confirm-content-establecer");
			this.dialogConfirmLiquidar=$("#<portlet:namespace/>dialog-confirm-liquidar");
			this.dialogConfirmLiquidarContent=$("#<portlet:namespace/>dialog-confirm-content-liquidar");
			
			this.dialogConfirmMotivo=$("#<portlet:namespace/>dialog-confirm-eliminar_motivo");//para elimar
			this.dialogConfirmContentMotivo=$("#<portlet:namespace/>dialog-confirm-content-eliminar_motivo");//para eliminar
			
			this.dialogValidacion=$("#<portlet:namespace/>dialog-alert");	
			this.dialogValidacionContent=$("#<portlet:namespace/>dialog-alert-content");
			this.dialogError=$("#<portlet:namespace/>dialog-error");
			this.dialogErrorContent=$("#<portlet:namespace/>dialog-error-content");	
            this.dialogInfo=$("#<portlet:namespace/>dialog-info");
			this.dialogInfoContent=$("#<portlet:namespace/>dialog-info-content");				
			
			//mensajes						
			this.mensajeEliminar='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Eliminando </h3>';
			this.mensajePrepara='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Generando Liq. </h3>';
			this.mensajeLiquidar='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Liquidando </h3>';
			this.mensajeReporte='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Obteniendo el Reporte </h3>';
			
			this.mensajeObteniendoDatos='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Obteniendo Datos </h3>';
			this.mensajeEliminando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Eliminando </h3>';
			this.mensajeGuardando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Guardando Datos </h3>';
			this.mensajeActualizando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Actualizando Datos </h3>';
			
			
			//urls
			this.urlBusqueda='<portlet:resourceURL id="busquedaLiquidacion" />';					
			this.urlEliminar='<portlet:resourceURL id="eliminarLiquidacion" />';
			this.urlCargaGrupoInf='<portlet:resourceURL id="cargarGrupoInformacion" />';			
			this.urlPreparaLiquidacion='<portlet:resourceURL id="preparaLiquidacionFormatos" />';
			this.urlLiquidar='<portlet:resourceURL id="liquidarFormatos" />';
			this.urlVerObservaciones='<portlet:resourceURL id="verObservacionesFormatos" />';
			this.urlVerFormatos='<portlet:resourceURL id="verFormatosReporte" />'; 
			
			//this.urlReporteObservaciones='<portlet:resourceURL id="reporteValidacionNotificacion" />';
			//this.urlReporteEnvioDefinitivo='<portlet:resourceURL id="reporteEnvioDefinitivo" />';
			
			this.urlBusquedaMotivo='<portlet:resourceURL id="busquedaMotivosLiquidacion" />';					
			this.urlGrabarMotivo='<portlet:resourceURL id="grabarMotivoLiquidacion" />';
			this.urlEliminarMotivo='<portlet:resourceURL id="eliminarMotivoLiquidacion" />';			
			this.urlEditarMotivo='<portlet:resourceURL id="editarMotivoLiquidacion" />';
			this.urlActualizarMotivo='<portlet:resourceURL id="actualizarMotivoLiquidacion" />';
			
			
			//botones
			this.botonBuscar=$("#<portlet:namespace/>btnBuscarLiquidacion");		
			this.botonGenerarEtapa=$("#<portlet:namespace/>btnGenerarEtapa");
			this.botonLiquidar=$("#<portlet:namespace/>btnLiquidar");			
			
			this.botonRegresarLiqui=$("#<portlet:namespace/>btnRegresarLiquidacion");	
			this.botonNuevoMotivo=$("#<portlet:namespace/>btnNuevoMotivo");		
			this.botonRegresarMotivo=$("#<portlet:namespace/>regresarMotivoLiq");		
			this.botonGrabarMotivo=$("#<portlet:namespace/>guardarMotivoLiq");
			this.botonActualizarMotivo=$("#<portlet:namespace/>actualizarMotivoLiq");
			
			
			//variables de busqueda
			this.i_codEmpresaBusq=$('#codEmpresaBusq');
			this.i_grupoInfBusq=$('#grupoInfBusq');				
			this.i_tipoBienal=$('#rbtBienal');
			this.i_tipoMensual=$('#rbtMensual');
			
			this.f_descMotivo=$('#descMotivo');
			this.f_codItem=$('#itemMotivo');
			this.f_codCorrelativo=$('#coMotivo');			
			
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
			
			//grillas para motivo de la liquidacion					
			this.tablaResultadosMotivo=$("#<portlet:namespace/>grid_resultado_busqueda_motivos");
			this.paginadoResultadosMotivo='#<portlet:namespace/>paginador_resultado_busqueda_motivos';
			this.buildGridsMotivos();	//cargar el modelo de la grilla	
			
			
			//llamado a la funciones de cada boton
			liquidacionVar.botonBuscar.click(function() {			
				liquidacionVar.buscarLiquidacion('P');
			});
			
			
			liquidacionVar.botonGenerarEtapa.click(function() {			
				liquidacionVar.confirmarEstablecer();
				//liquidacionVar.preparaLiquidacionFormatos();
			});
			
			liquidacionVar.botonLiquidar.click(function() {	
				liquidacionVar.confirmarLiquidar();
				//liquidacionVar.liquidarFormatos();
			});
			
			liquidacionVar.botonRegresarLiqui.click(function() {
				liquidacionVar.<portlet:namespace/>regresarLiquidacion();
		    });			
			
			//para motivos de liquidacion	
			
			liquidacionVar.botonNuevoMotivo.click(function() {
				liquidacionVar.<portlet:namespace/>nuevoMotivo();
		    });
			
			liquidacionVar.botonGrabarMotivo.click(function() {
				liquidacionVar.<portlet:namespace/>guardarMotivo();
			});
			
			liquidacionVar.botonActualizarMotivo.click(function() {
				liquidacionVar.<portlet:namespace/>actualizarMotivo();
			});			
			
			liquidacionVar.botonRegresarMotivo.click(function() {
				liquidacionVar.<portlet:namespace/>regresarMotivos();
		    });			
			
			
			liquidacionVar.initDialogs();
		    
		    
			liquidacionVar.i_tipoBienal.change(function(){
				liquidacionVar.<portlet:namespace/>loadGrupoInformacion();
				liquidacionVar.botonGenerarEtapa.val("Procesar costos estándares");
				liquidacionVar.botonLiquidar.val("Aprobar costos estándares");
				//liquidacionVar.buscarLiquidacion('B');
			});
			
			liquidacionVar.i_tipoMensual.change(function(){
				liquidacionVar.<portlet:namespace/>loadGrupoInformacion();
				liquidacionVar.botonGenerarEtapa.val("Procesar gastos operativos");
				liquidacionVar.botonLiquidar.val("Liquidar gastos operativos");
				//liquidacionVar.buscarLiquidacion('B');
			});			
			
		    
		    //eventos por defecto	
		    
			//liquidacionVar.botonBuscar.trigger('click');
			//liquidacionVar.initBlockUI();
			liquidacionVar.i_tipoMensual.trigger('change');
			
		},
		//funcion para armar el modelo de la grilla para el resultado
		buildGrids : function () {	
			liquidacionVar.tablaResultados.jqGrid({
			   datatype: "local",
		       colNames: ['Dist. Eléct.','Formato','Etapa Orig.','Año Decl.','Mes Decl.','Año Ejec.','Mes Ejec.','Año Ini. Vig.','Año Fin Vig.','Etapa Final','Aprob./Liquid.','Ver','Ver Obs.','Excluir','Motivo No Rec./Est.','','','',''],
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
		               { name: 'etapaReconocido', index: 'etapaReconocido', width: 40,align:'center' },  
		               { name: 'liquidado', index: 'liquidado', width: 40,align:'center' }, 		              
		               { name: 'verF', index: 'verF', width: 20,align:'center' },
		               { name: 'verObs', index: 'verObs', width: 20,align:'center' },	 
		               { name: 'elim', index: 'elim', width: 20,align:'center' },
		               { name: 'mostrar', index: 'mostrar', width: 60,align:'center' },	
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
					pager: liquidacionVar.paginadoResultados,
				    viewrecords: true,
				   	caption: "Datos para Aprobación Bienal / Liquidación Mensual",
				    sortorder: "asc",	   	    	   	   
		            gridComplete: function(){
		      		var ids = liquidacionVar.tablaResultados.jqGrid('getDataIDs');
		      		for(var i=0;i < ids.length;i++){
		      			var cl = ids[i];
		      			var ret = liquidacionVar.tablaResultados.jqGrid('getRowData',cl);	        			
		      			verF = "<a href='#'><img border='0' title='Ver Formato' src='/net-theme/images/img-net/file.png' align='center' onclick=\"liquidacionVar.mostrarReporteFormatos('"+ret.codEmpresa+"','"+ret.anioPres+"','"+ret.mesPres+"','"+ret.anioEjec+"','"+ret.mesEjec+"','"+ret.anioIniVig+"','"+ret.anioFinVig+"','"+ret.etapa+"','"+ret.formato+"');\" /></a> ";
		      			verObs = "<a href='#'><img border='0' title='Ver Obs.' src='/net-theme/images/img-net/file.png'  align='center' onclick=\"liquidacionVar.verObservaciones('"+ret.codEmpresa+"','"+ret.anioPres+"','"+ret.mesPres+"','"+ret.anioEjec+"','"+ret.mesEjec+"','"+ret.anioIniVig+"','"+ret.anioFinVig+"','"+ret.etapa+"','"+ret.formato+"');\" /></a> ";
		      			elim = "<a href='#'><img border='0' title='Excluir' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"liquidacionVar.confirmarEliminar('"+ret.correlativo+"','"+ret.liquidado+"');\" /></a> ";
		      			mostrar = "<a href='#'><img border='0' title='Motivo de no Reconocimiento/Establecimiento' src='/net-theme/images/img-net/file-add.png'  align='center' onclick=\"liquidacionVar.mostrarNoReconocido('"+ret.correlativo+"','"+ret.desEmpresa+"','"+ret.anioPres+"','"+ret.desMes+"','"+ret.anioEjec+"','"+ret.desMesEje+"','"+ret.anioIniVig+"','"+ret.anioFinVig+"','"+ret.etapaReconocido+"','"+ret.formato+"','"+ret.etapaReconocido+"','"+ret.liquidado+"');\" /></a> ";
		      			liquidacionVar.tablaResultados.jqGrid('setRowData',ids[i],{verF:verF});
		      		    liquidacionVar.tablaResultados.jqGrid('setRowData',ids[i],{verObs:verObs});
		      		    liquidacionVar.tablaResultados.jqGrid('setRowData',ids[i],{elim:elim});
		      		    liquidacionVar.tablaResultados.jqGrid('setRowData',ids[i],{mostrar:mostrar});
		      		}
		      } 
		  	});
			liquidacionVar.tablaResultados.jqGrid('navGrid',liquidacionVar.paginadoResultados,{add:false,edit:false,del:false,search: false,refresh: false});				
		},
		
		//Modelo de la grilla para mostrar Observaciones para los formatos 12A,12B,14A,14B 14C
		buildGridsObservacion : function () {	
		   liquidacionVar.tablaObservacion.jqGrid({
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
				pager: liquidacionVar.paginadoObservacion,
			    viewrecords: true,			   
			    sortorder: "asc"
		  	});
		    liquidacionVar.tablaObservacion.jqGrid('navGrid',liquidacionVar.paginadoObservacion,{add:false,edit:false,del:false,search: false,refresh: false});	
		   
		},
		
		//Modelo de la grilla para mostrar Observaciones para los formatos 12C y 12D
		buildGridsObservacion12 : function () {	
		   liquidacionVar.tablaObservacion12.jqGrid({
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
				pager: liquidacionVar.paginadoObservacion12,
			    viewrecords: true,			   
			    sortorder: "asc"
		  	});
		    liquidacionVar.tablaObservacion12.jqGrid('navGrid',liquidacionVar.paginadoObservacion12,{add:false,edit:false,del:false,search: false,refresh: false});	
		   
		},
		
		//Modelo de la grilla para mostrar Observaciones para los formatos 13A
		buildGridsObservacion13 : function () {	
		   liquidacionVar.tablaObservacion13.jqGrid({
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
				pager: liquidacionVar.paginadoObservacion13,
			    viewrecords: true,			   
			    sortorder: "asc"
		  	});
		    liquidacionVar.tablaObservacion13.jqGrid('navGrid',liquidacionVar.paginadoObservacion13,{add:false,edit:false,del:false,search: false,refresh: false});	

		},		
		
		//funcion para armar el modelo de la grilla para el resultado de los motivos de  la liquidacion
		buildGridsMotivos : function () {	
			var ancho = liquidacionVar.divBuscarLiq.width();
			liquidacionVar.tablaResultadosMotivo.jqGrid({
			   datatype: "local",
		       colNames: ['Item','Descripción del Motivo','Editar','Eliminar',''],
		       colModel: [
                       { name: 'itemMotivo', index: 'itemMotivo', width: 50,align:'center'},				   
					   { name: 'descMotivo', index: 'descMotivo', width: 300},					  	  	           
		               { name: 'edit', index: 'edit', width: 20,align:'center' },		                
		               { name: 'elim', index: 'elim', width: 20,align:'center' },		    
		               { name: 'coMotivo', index: 'coMotivo', hidden: true}	               
			   	    ],
			   	 multiselect: false,
					rowNum:10,
				   	rowList:[10,20,50],
				   	height: 'auto',
				   //	autowidth: true,
					rownumbers: true,
					//shrinkToFit:true,
					width:ancho ,
					pager: liquidacionVar.paginadoResultadosMotivo,
				    viewrecords: true,
				   	caption: "Resultado(s) de la búsqueda",
				    sortorder: "desc",	   	    	   	   
		            gridComplete: function(){
		      		var ids = liquidacionVar.tablaResultadosMotivo.jqGrid('getDataIDs');
		      		for(var i=0;i < ids.length;i++){
		      			var cl = ids[i];
		      			var ret = liquidacionVar.tablaResultadosMotivo.jqGrid('getRowData',cl);	        			
		      			edit = "<a href='#'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png' align='center' onclick=\"liquidacionVar.editarMotivo('"+ret.itemMotivo+"','"+ret.coMotivo+"');\" /></a> ";
		      			elim = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"liquidacionVar.confirmarEliminarMotivo('"+ret.itemMotivo+"','"+ret.coMotivo+"');\" /></a> ";
		      			liquidacionVar.tablaResultadosMotivo.jqGrid('setRowData',ids[i],{edit:edit});		      		   
		      		    liquidacionVar.tablaResultadosMotivo.jqGrid('setRowData',ids[i],{elim:elim});
		      		}
		      } 
		  	});
			liquidacionVar.tablaResultadosMotivo.jqGrid('navGrid',liquidacionVar.paginadoResultadosMotivo,{add:false,edit:false,del:false,search: false,refresh: false});				
		},
		
		
		//funcion para buscar
		buscarLiquidacion : function (flag) {	
			console.debug("entranado a buscar liquidacion");
			liquidacionVar.blockUI();
			jQuery.ajax({			
					url: liquidacionVar.urlBusqueda+'&'+liquidacionVar.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data : {
						   <portlet:namespace />flagBusq: flag						  
					},
					success: function(gridData) {					
						liquidacionVar.tablaResultados.clearGridData(true);
						liquidacionVar.tablaResultados.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
						liquidacionVar.tablaResultados[0].refreshIndex();
						liquidacionVar.initBlockUI();
					},error : function(){
						var addhtmError='Error de conexión.';					
						liquidacionVar.dialogErrorContent.html(addhtmError);
						liquidacionVar.dialogError.dialog("open");
						liquidacionVar.initBlockUI();
					}
				});			
		},			
		//function para el evento onchange en empresa para cargar el periodo
		<portlet:namespace/>loadGrupoInformacion : function(){	
			console.debug("entranado a cargar grupoInfo");
			jQuery.ajax({
					url: liquidacionVar.urlCargaGrupoInf+'&'+liquidacionVar.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					success: function(data) {		
						dwr.util.removeAllOptions("grupoInfBusq");
						dwr.util.addOptions("grupoInfBusq", data,"codigoItem","descripcionItem");														
					},error : function(){
						var addhtmError='Error de conexión.';					
						liquidacionVar.dialogErrorContent.html(addhtmError);
						liquidacionVar.dialogError.dialog("open");
						liquidacionVar.initBlockUI();
					}
			});
		},	
	
		verObservaciones : function(cod_Empresa,anio_Pres,mes_Pres,anio_Ejec,mes_Ejec,anio_IniVig,anio_FinVig,cod_etapa,cod_formato){
			jQuery.ajax({
				url: liquidacionVar.urlVerObservaciones+'&'+liquidacionVar.formCommand.serialize(),
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
							liquidacionVar.dialogObservacion12.dialog("open");
							liquidacionVar.tablaObservacion12.clearGridData(true);
							liquidacionVar.tablaObservacion12.jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
							liquidacionVar.tablaObservacion12[0].refreshIndex();
							liquidacionVar.initBlockUI();	
						}else if(cod_formato=='F13A'){
							console.debug("entrando en formato13A ");
							liquidacionVar.dialogObservacion13.dialog("open");
							liquidacionVar.tablaObservacion13.clearGridData(true);
							liquidacionVar.tablaObservacion13.jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
							liquidacionVar.tablaObservacion13[0].refreshIndex();
							liquidacionVar.initBlockUI();	
						}else{
							liquidacionVar.dialogObservacion.dialog("open");
							liquidacionVar.tablaObservacion.clearGridData(true);
							liquidacionVar.tablaObservacion.jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
							liquidacionVar.tablaObservacion[0].refreshIndex();
							liquidacionVar.initBlockUI();	
						}						
					}else{						
						var addhtmError='Error al Ver las Observaciones del registro seleccionado.';					
						liquidacionVar.dialogErrorContent.html(addhtmError);
						liquidacionVar.dialogError.dialog("open");	
						liquidacionVar.initBlockUI();	
					}
				},error : function(){
					var addhtmError='Error de conexión.';					
					liquidacionVar.dialogErrorContent.html(addhtmError);
					liquidacionVar.dialogError.dialog("open");
					liquidacionVar.initBlockUI();
				}
			});
		}, 		
		
		/* <portlet:namespace/>mostrarReporteObservaciones : function(){
			jQuery.ajax({
				url: liquidacionVar.urlReporteObservaciones+'&'+liquidacionVar.formCommand.serialize(),
				type : 'post',
				dataType : 'json',
				data : {					
					<portlet:namespace />nombreReporte: 'validacion',
					<portlet:namespace />nombreArchivo: 'validacion',
					<portlet:namespace />tipoArchivo: '0'//PDF
				},
				success : function(gridData) {
					if(gridData!=null){
						liquidacionVar.verReporteObservaciones();	
					}else{
						alert("Error al mostrar el reporte");
						liquidacionVar.initBlockUI();
					}
					
				},error : function(){
					alert("Error de conexión.");
					liquidacionVar.initBlockUI();
				}
			});
		}, */			
		
		mostrarReporteFormatos : function(cod_Empresa,anio_Pres,mes_Pres,anio_Ejec,mes_Ejec,anio_IniVig,anio_FinVig,cod_etapa,cod_formato){
			console.debug("entranado a  ver el reporte del formato");
			$.blockUI({ message: liquidacionVar.mensajeReporte});
			jQuery.ajax({
				url: liquidacionVar.urlVerFormatos+'&'+liquidacionVar.formCommand.serialize(),
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
						liquidacionVar.verReporteFormato();	
						liquidacionVar.initBlockUI();
					}else{						
						var addhtmError='Error al ver el reporte del formato seleccionado.';					
						liquidacionVar.dialogErrorContent.html(addhtmError);
						liquidacionVar.dialogError.dialog("open");
						liquidacionVar.initBlockUI();
					}
				},error : function(){
					var addhtmError='Error de conexión.';					
					liquidacionVar.dialogErrorContent.html(addhtmError);
					liquidacionVar.dialogError.dialog("open");
					liquidacionVar.initBlockUI();
				}
			});
		}, 		
		
		/**Function para confirmar para eliminar.*/
		confirmarEliminar : function(correlativo,liquidado){
			console.debug("entranado a eliminar de lista general o sea a excluir");
			if(liquidado=='NO'){
				var addhtml='¿Está seguro que desea excluir el registro seleccionado?';
				liquidacionVar.dialogConfirmContent.html(addhtml);
				liquidacionVar.dialogConfirm.dialog("open");
				id_Correlativo=correlativo;				
			}else{
				var msg;
				if( liquidacionVar.i_tipoMensual.prop('checked')){
					msg = 'liquidado';
				}else if( liquidacionVar.i_tipoBienal.prop('checked') ){
					msg = 'aprobado';
				}
				var addhtml2='No se puede excluir, el registro seleccionado ya se encuentra '+msg;
				liquidacionVar.dialogInfoContent.html(addhtml2);
				liquidacionVar.dialogInfo.dialog("open");	
			}	
		},
		//
		confirmarEstablecer : function(){
			console.debug("entranado a establecer");
			var msg;
			if( liquidacionVar.i_tipoMensual.prop('checked')){
				msg = 'gastos operativos';
			}else if( liquidacionVar.i_tipoBienal.prop('checked') ){
				msg = 'costos estándares';
			}
			var addhtml='¿Está seguro que desea Procesar los '+msg+'?';
			liquidacionVar.dialogConfirmEstablecerContent.html(addhtml);
			liquidacionVar.dialogConfirmEstablecer.dialog("open");
		},
		confirmarLiquidar : function(){
			console.debug("entranado a liquidar");
			var msg;
			if( liquidacionVar.i_tipoMensual.prop('checked')){
				msg = 'Liquidar los gastos operativos';
			}else if( liquidacionVar.i_tipoBienal.prop('checked') ){
				msg = 'Aprobar los costos estándares';
			}
			var addhtml='¿Está seguro que desea '+msg+'?';
			liquidacionVar.dialogConfirmLiquidarContent.html(addhtml);
			liquidacionVar.dialogConfirmLiquidar.dialog("open");	
		},
		/**Function para  eliminar despues de la confirmacion*/
		eliminarFormato : function(id_Correlativo){
			console.debug("entranado a eliminar formato");					
			$.blockUI({ message: liquidacionVar.mensajeEliminar});
			jQuery.ajax({
				url: liquidacionVar.urlEliminar+'&'+liquidacionVar.formCommand.serialize(),
				type: 'post',
				dataType: 'json',
				data: {				
				     <portlet:namespace />correlativo: id_Correlativo				     
					},
				success: function(data) {
					if(data.resultado == "OK"){
						var addhtml2='Registro excluido satisfactoriamente. Ese registro no será considerado para la Aprobación de los costos estándares o la Liquidación de los gastos operativos según sea el caso.';				
						liquidacionVar.dialogMessageContent.html(addhtml2);
						liquidacionVar.dialogMessage.dialog("open");
						liquidacionVar.buscarLiquidacion('B');
						liquidacionVar.initBlockUI();						
					}else{
						var addhtmError='Error al excluir el registro seleccionado.';					
						liquidacionVar.dialogErrorContent.html(addhtmError);
						liquidacionVar.dialogError.dialog("open");						
						liquidacionVar.initBlockUI();
					}					
				},error : function(){
					var addhtmError='Error de conexión.';					
					liquidacionVar.dialogErrorContent.html(addhtmError);
					liquidacionVar.dialogError.dialog("open");
					liquidacionVar.initBlockUI();
				}
			});
		},	
		/*Funcion para prepara la liquidacion de los formatos*/
		preparaLiquidacionFormatos : function(){
			console.debug("entranado a preparar liquidacion formatos");					
			$.blockUI({ message: liquidacionVar.mensajePrepara});				
			jQuery.ajax({
				url: liquidacionVar.urlPreparaLiquidacion+'&'+liquidacionVar.formCommand.serialize(),
				type: 'post',
				dataType: 'json',				
				success: function(data) {
					var msgFinal;
					var formatoDes;
					if( liquidacionVar.i_tipoMensual.prop('checked')){
						msgFinal = 'Reconocimiento de los gastos operativos';
						formatoDes ='Mensuales';
					}else if( liquidacionVar.i_tipoBienal.prop('checked') ){
						msgFinal = 'Establecimiento de los costos estándares';
						formatoDes= 'Bienales';
					}
					if(data.resultado == "OK"){						
						var addhtml2='El '+msgFinal+' se procesó satisfactoriamente.';				
						liquidacionVar.dialogMessageContent.html(addhtml2);
						liquidacionVar.dialogMessage.dialog("open");
						liquidacionVar.buscarLiquidacion('B');
						liquidacionVar.initBlockUI();						
					}else if(data.resultado == "NODATOS"){
						var addhtmNoDatos='Para Procesar el '+msgFinal+', se requiere realizar el Envío Definitivo de los Formatos '+formatoDes+' de la Dist. Eléctrica seleccionada.';					
						liquidacionVar.dialogInfoContent.html(addhtmNoDatos);
						liquidacionVar.dialogInfo.dialog("open");
						liquidacionVar.initBlockUI();
					}else{						
						var addhtmError='Error al Procesar '+msgFinal+' para realizar la liquidación';					
						liquidacionVar.dialogErrorContent.html(addhtmError);
						liquidacionVar.dialogError.dialog("open");	
						liquidacionVar.initBlockUI();
					}					
				},error : function(){
					var addhtmError='Error de conexión.';					
					liquidacionVar.dialogErrorContent.html(addhtmError);
					liquidacionVar.dialogError.dialog("open");
					liquidacionVar.initBlockUI();
				}
			});
		},			
		/*Funcion para realizar la liquidacion de los formatos*/
		liquidarFormatos : function(){
			console.debug("entranado a liquidar los formatos");					
			$.blockUI({ message: liquidacionVar.mensajeLiquidar});
			jQuery.ajax({
				url: liquidacionVar.urlLiquidar+'&'+liquidacionVar.formCommand.serialize(),
				type: 'post',
				dataType: 'json',				
				success: function(data) {
					var msgFinal;
					var formatoDes;
					var desAterior;				
					if( liquidacionVar.i_tipoMensual.prop('checked')){
						msgFinal = 'liquidación de los gastos operativos';
						desAterior = 'Reconocimiento de los gastos operativos';
						formatoDes= 'Mensuales';
					}else if( liquidacionVar.i_tipoBienal.prop('checked') ){
						msgFinal = 'aprobación de los costos estándares';
						desAterior = 'Establecimiento de los costos estándares';
						formatoDes ='Bienales';
					}
					if(data.resultado == "OK"){						
						var addhtml2='La '+msgFinal+' se realizó satisfactoriamente';				
						liquidacionVar.dialogMessageContent.html(addhtml2);
						liquidacionVar.dialogMessage.dialog("open");
						liquidacionVar.buscarLiquidacion('B');//valor que se envia al controler en la busqueda
						liquidacionVar.initBlockUI();						
					}else if(data.resultado == "NODATOS"){
						var addhtmNoDatos='Para realizar la '+msgFinal+', se requiere realizar el Envío Definitivo de los Formatos '+formatoDes+' de la Dist. Eléctrica seleccionada.';	
						liquidacionVar.dialogInfoContent.html(addhtmNoDatos);
						liquidacionVar.dialogInfo.dialog("open");
						liquidacionVar.initBlockUI();
					}else if(data.resultado == "NOAPTO"){
						var addhtmNoApto='La información no está preparada para realizar la '+msgFinal+'. Primero debe Procesar el '+desAterior+' de la Dist. Eléctrica seleccionada.';	
						liquidacionVar.dialogInfoContent.html(addhtmNoApto);
						liquidacionVar.dialogInfo.dialog("open");
						liquidacionVar.initBlockUI();
					}else{						
						var addhtmError='Error al realizar la '+msgFinal+' ';					
						liquidacionVar.dialogErrorContent.html(addhtmError);
						liquidacionVar.dialogError.dialog("open");	
						liquidacionVar.initBlockUI();
					}					
				},error : function(){
					var addhtmError='Error de conexión.';					
					liquidacionVar.dialogErrorContent.html(addhtmError);
					liquidacionVar.dialogError.dialog("open");
					liquidacionVar.initBlockUI();
				}
			});
		},	
		
		//funcion para ver reposrte en una nueva pestaña
		verReporteFormato : function(){
			window.open('<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ViewReport")%>','_newtab');
		}, 
		
		//funcion para buscar motivos de la liquidacion
		buscarMotivos : function (cod_correlativo) {	
			console.debug("entranado a buscar motivos");
			liquidacionVar.blockUI();
			jQuery.ajax({			
					url: liquidacionVar.urlBusquedaMotivo+'&'+liquidacionVar.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data : {
						   <portlet:namespace />correlativo: cod_correlativo						  
					},
					success: function(gridData) {					
						liquidacionVar.tablaResultadosMotivo.clearGridData(true);
						liquidacionVar.tablaResultadosMotivo.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
						liquidacionVar.tablaResultadosMotivo[0].refreshIndex();
						liquidacionVar.initBlockUI();
					},error : function(){
						var addhtmError='Error de conexión.';					
						liquidacionVar.dialogErrorContent.html(addhtmError);
						liquidacionVar.dialogError.dialog("open");
						liquidacionVar.initBlockUI();
					}
				});			
		},		
		//funcion  para mostrar detalle de los motivos y la grilla de la busqueda
		mostrarNoReconocido : function(cod_correlativo,empresa,anio_pres,mes_pres,anio_ejec,mes_ejec,anio_inicio,anio_fin,etapa_final,formato,reconocido,liquidado){	
			if((reconocido=='RECONOCIDO' || reconocido=='ESTABLECIDO') && liquidado=='SI'){
				$('#empresaMotivo').val(empresa);
				$('#anioMotivo').val(anio_pres);
				$('#mesMotivo').val(mes_pres);
				$('#anioEjecMotivo').val(anio_ejec);
				$('#mesEjecMotivo').val(mes_ejec);
				$('#anioInicioVigMotivo').val(anio_inicio);
				$('#anioFinVigMotivo').val(anio_fin);
				$('#etapaFinalMotivo').val(etapa_final);
				$('#formatoMotivo').val(formato);					
				
				$('#coMotivo').val(cod_correlativo);//para el nuevo registro de motivo
				if(liquidacionVar.i_tipoMensual.prop('checked')){
					$('#tituloBusquedaMotivo').val('Motivo de no Reconocimiento de Gastos Operativos.');	
				}else{
					$('#tituloBusquedaMotivo').val('Motivo de no Establecimiento de Costos Estándares.');
				}				
				liquidacionVar.divBuscarMotivo.show();
				liquidacionVar.divBuscarLiq.hide();		
				liquidacionVar.buscarMotivos(cod_correlativo);	
			}else{
				var addhtml2='El registro seleccionado no se encuentra Aprobado/Liquidado.';				
				liquidacionVar.dialogInfoContent.html(addhtml2);
				liquidacionVar.dialogInfo.dialog("open");
			}		
		},
		
		//funcion para nuevo registro de motivo
		<portlet:namespace/>nuevoMotivo : function(){
			console.debug("boton nuevo registro motivo:  ");		
			liquidacionVar.divNuevoMotivo.show();
			liquidacionVar.divBuscarLiq.hide();	
			liquidacionVar.divBuscarMotivo.hide();
			liquidacionVar.f_descMotivo.val('');
			if(liquidacionVar.i_tipoMensual.prop('checked')){
				$('#tituloNuevoMotivo').val('Motivo de no Reconocimiento de Gastos Operativos.');	
			}else{
				$('#tituloNuevoMotivo').val('Motivo de no Establecimiento de Costos Estándares.');
			}	
		    $('#<portlet:namespace/>guardarMotivoLiq').css('display','block');
			$('#<portlet:namespace/>actualizarMotivoLiq').css('display','none');			
		},	
		
		//Funcion para Grabar nuevo registro de motivo de liquidacion
		<portlet:namespace/>guardarMotivo: function(){
			if (liquidacionVar.validarFormulario()){
				$.blockUI({ message: liquidacionVar.mensajeGuardando});
				 jQuery.ajax({
					 url: liquidacionVar.urlGrabarMotivo+'&'+liquidacionVar.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {						  
						  <portlet:namespace />coMotivo: liquidacionVar.f_codCorrelativo.val(), 
						  <portlet:namespace />descMotivo: liquidacionVar.f_descMotivo.val()
						},
					    success: function(data) {			
						if (data.resultado == "OK"){				
							var addhtml2='El Motivo de la Aprobación/Liquidación se guardó satisfactoriamente';							
							liquidacionVar.dialogMessageContent.html(addhtml2);
							liquidacionVar.dialogMessage.dialog("open");							
							liquidacionVar.initBlockUI();												
							
							$('#<portlet:namespace/>guardarMotivoLiq').css('display','none');
							$('#<portlet:namespace/>actualizarMotivoLiq').css('display','block');
							
						}else{								
							var addhtmError='Se produjo un error al guardar el Motivo de la Aprobación/Liquidación.';					
							liquidacionVar.dialogErrorContent.html(addhtmError);
							liquidacionVar.dialogError.dialog("open");						
							liquidacionVar.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						liquidacionVar.dialogErrorContent.html(addhtmError);
						liquidacionVar.dialogError.dialog("open");
						liquidacionVar.initBlockUI();
					}
				});			
			}
		},
		
		//Funcion para actualizar un registro
		<portlet:namespace/>actualizarMotivo : function(){
			if (liquidacionVar.validarFormulario()){
				$.blockUI({ message: liquidacionVar.mensajeActualizando});
				 jQuery.ajax({
					 url: liquidacionVar.urlActualizarMotivo+'&'+liquidacionVar.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {
						<portlet:namespace />itemMotivo: liquidacionVar.f_codItem.val(),
						<portlet:namespace />coMotivo: liquidacionVar.f_codCorrelativo.val(),
						<portlet:namespace />descMotivo: liquidacionVar.f_descMotivo.val()
						},
					success: function(data) {			
						if (data.resultado == "OK"){				
							var addhtml2='El Motivo de la Aprobación/Liquidación se actualizó satisfactoriamente';
							liquidacionVar.dialogMessageContent.html(addhtml2);
							liquidacionVar.dialogMessage.dialog("open");						
							liquidacionVar.initBlockUI();								
						}else if(data.resultado == "Error"){						
							var addhtmError='Se produjo un error al actualizar el Motivo de la Aprobación/Liquidación.';					
							liquidacionVar.dialogErrorContent.html(addhtmError);
							liquidacionVar.dialogError.dialog("open");						
							liquidacionVar.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						liquidacionVar.dialogErrorContent.html(addhtmError);
						liquidacionVar.dialogError.dialog("open");
						liquidacionVar.initBlockUI();
					}
				});						
			}
		},
		
		//funcion para validar ingreso de datos del motivo de liquidacion
		validarFormulario : function() {
			console.debug("tamaño de descripcion:  "+liquidacionVar.f_descMotivo.val().length);
			if(liquidacionVar.f_descMotivo.val().length == ''){			
				var addhtmAlert='Debe ingresar una Descripción del Motivo.';					
				liquidacionVar.dialogValidacionContent.html(addhtmAlert);
				liquidacionVar.dialogValidacion.dialog("open");		
				liquidacionVar.f_descMotivo.focus();
			  	return false; 
			}else if(liquidacionVar.f_descMotivo.val().length > 499){				
				var addhtmAlert='La descripción del Motivo no debe exceder a los 500 caracteres.';					
				liquidacionVar.dialogValidacionContent.html(addhtmAlert);
				liquidacionVar.dialogValidacion.dialog("open");	
				liquidacionVar.f_descMotivo.focus();
			  	return false; 
			}else{
				return true;
			}		
		},	
		
		//Function para editar los datos del motivo de la liquidacion
		editarMotivo : function(cod_item,cod_correlativo){	
			    console.debug("entrando a editar "+cod_item);		
				$.blockUI({ message: liquidacionVar.mensajeObteniendoDatos});			 
				jQuery.ajax({
						url: liquidacionVar.urlEditarMotivo+'&'+liquidacionVar.formCommand.serialize(),
						type: 'post',
						dataType: 'json',
						data: {							
							<portlet:namespace />itemMotivoEdit:cod_item,
							<portlet:namespace />correlativoEdit:cod_correlativo					   				  
						},
						success: function(data) {				
							if (data != null){										
								liquidacionVar.divNuevoMotivo.show();
								liquidacionVar.divBuscarLiq.hide();	
								liquidacionVar.divBuscarMotivo.hide();
								
								liquidacionVar.llenarDatosEditar(data);								
								liquidacionVar.initBlockUI();	
								
							    $('#<portlet:namespace/>guardarMotivoLiq').css('display','none');
								$('#<portlet:namespace/>actualizarMotivoLiq').css('display','block');																			
					         }
							else{								
								var addhtmError='Error al obtener los datos del registro seleccionado';					
								liquidacionVar.dialogErrorContent.html(addhtmError);
								liquidacionVar.dialogError.dialog("open");	
								liquidacionVar.initBlockUI();
							}
						},error : function(){
							var addhtmError='Error de conexión.';					
							liquidacionVar.dialogErrorContent.html(addhtmError);
							liquidacionVar.dialogError.dialog("open");
							liquidacionVar.initBlockUI();
						}
				});		
		},
		
		//funcion  para llenar los campos para editar
		llenarDatosEditar : function(bean){		
			liquidacionVar.f_descMotivo.val(bean.descMotivo); 
			$('#itemMotivo').val(bean.itemMotivo);
			$('#coMotivo').val(bean.coMotivo);
		},
		
		/**Function para confirmar si quiere eliminar el registro o no*/
		confirmarEliminarMotivo : function(cod_item,cod_correlativo){
			console.debug("entranado a eliminar confirmar motivo:  "+cod_item);
			var addhtml='¿Está seguro que desea eliminar el registro seleccionado?';
			liquidacionVar.dialogConfirmContentMotivo.html(addhtml);
			liquidacionVar.dialogConfirmMotivo.dialog("open");				
			codMotivo= cod_item; 
			codCorrelativo= cod_correlativo;
		},
		/**Function para  eliminar el registro una vez hecho la confirmacion*/
		eliminarMotivo : function(codMotivo,codCorrelativo){
			console.debug("entranado a eliminar motivo:  "+codMotivo);
			$.blockUI({ message: liquidacionVar.mensajeEliminando });
			jQuery.ajax({
				url: liquidacionVar.urlEliminarMotivo+'&'+liquidacionVar.formCommand.serialize(),
				type: 'post',
				dataType: 'json',
				data: {				
					   <portlet:namespace />itemMotivoEdit: codMotivo,
					   <portlet:namespace />correlativoEdit: codCorrelativo				  
					},
				success: function(data) {
					if (data.resultado == "OK"){
						var addhtml2='El registro fue eliminado satisfactoriamente';					
						liquidacionVar.dialogMessageContent.html(addhtml2);
						liquidacionVar.dialogMessage.dialog("open");
						liquidacionVar.buscarMotivos(codCorrelativo);
						liquidacionVar.initBlockUI();
					}
					else{						
						var addhtmError='Error al eliminar el registro seleccionado.';					
						liquidacionVar.dialogErrorContent.html(addhtmError);
						liquidacionVar.dialogError.dialog("open");
						liquidacionVar.initBlockUI();
					}
				},error : function(){
					var addhtmError='Error de conexión.';					
					liquidacionVar.dialogErrorContent.html(addhtmError);
					liquidacionVar.dialogError.dialog("open");
					liquidacionVar.initBlockUI();
				}
			});
		},
		
		//funcion para regresar a liquidacion
		<portlet:namespace/>regresarLiquidacion : function(){			
			liquidacionVar.divBuscarMotivo.hide();
			liquidacionVar.divBuscarLiq.show();
					
			//liquidacionVar.botonBuscar.trigger('click');
		},
		
		//funcion para regresar a detalle de motivos
		<portlet:namespace/>regresarMotivos : function(){			
			liquidacionVar.divNuevoMotivo.hide();
			liquidacionVar.divBuscarLiq.hide();	
			liquidacionVar.divBuscarMotivo.show();
			liquidacionVar.buscarMotivos($('#coMotivo').val());
		},
		
		//DIALOGOS
		initDialogs : function(){		
			//dialogo para eliminar
			liquidacionVar.dialogConfirm.dialog({
				modal: true,
				height: 200,
				width: 450,			
				autoOpen: false,
				buttons: {
					"Si": function() {
						liquidacionVar.eliminarFormato(id_Correlativo);
						$( this ).dialog("close");
					},
					"No": function() {				
						$( this ).dialog("close");
					}
				}
			});
			
			//
			liquidacionVar.dialogConfirmEstablecer.dialog({
				modal: true,
				height: 200,
				width: 450,			
				autoOpen: false,
				buttons: {
					"Si": function() {
						liquidacionVar.preparaLiquidacionFormatos();
						$( this ).dialog("close");
					},
					"No": function() {				
						$( this ).dialog("close");
					}
				}
			});
			liquidacionVar.dialogConfirmLiquidar.dialog({
				modal: true,
				height: 200,
				width: 450,			
				autoOpen: false,
				buttons: {
					"Si": function() {
						liquidacionVar.liquidarFormatos();
						$( this ).dialog("close");
					},
					"No": function() {				
						$( this ).dialog("close");
					}
				}
			});
			//
			
			liquidacionVar.dialogMessage.dialog({
				modal: true,
				autoOpen: false,
				width: 450,		
				buttons: {
					Ok: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			 liquidacionVar.dialogObservacion.dialog({
				modal: true,
				width: 700,
				autoOpen: false,
				buttons: {
					Cerrar: function() {
						$( this ).dialog("close");
					}
				}
			}); 
			 
			 liquidacionVar.dialogObservacion12.dialog({
					modal: true,
					width: 740,
					autoOpen: false,
					buttons: {
						Cerrar: function() {
							$( this ).dialog("close");
						}
					}
				}); 
			 
			 liquidacionVar.dialogObservacion13.dialog({
					modal: true,
					width: 730,
					autoOpen: false,
					buttons: {
						Cerrar: function() {
							$( this ).dialog("close");
						}
					}
				}); 
			 
			 liquidacionVar.dialogConfirmMotivo.dialog({
					modal: true,
					height: 200,
					width: 450,			
					autoOpen: false,
					buttons: {
						"Si": function() {							
							liquidacionVar.eliminarMotivo(codMotivo,codCorrelativo);
							$( this ).dialog("close");
						},
						"No": function() {				
							$( this ).dialog("close");
						}
					}
				});
			 
			 liquidacionVar.dialogValidacion.dialog({
					modal: true,
					autoOpen: false,
					width:450,
					buttons: {
						Aceptar: function() {
							$( this ).dialog("close");
						}
					}
				});
				
			 liquidacionVar.dialogError.dialog({
					modal: true,
					autoOpen: false,
					width: 450,		
					buttons: {
						Aceptar: function() {
							$( this ).dialog("close");
						}
					}
				});
				
			 liquidacionVar.dialogInfo.dialog({
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