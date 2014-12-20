<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects />

<script type="text/javascript">

var liquidacionVar= {
		
	    //decalaracion de los elementos del html	
		formCommand : null,	
		
		//dialogos	
		dialogMessage:null,
		dialogMessageContent:null,
		
		dialogConfirm:null,//para eliminar
		dialogConfirmContent:null,//para mostrar la confirmacion de eliminar		
		dialogObservacion:null,
		dialogObservacion12:null,
		dialogObservacion13:null,
		
		
		
		//mensajes		
		mensajeEliminar:null,
		mensajePrepara:null,
		mensajeLiquidar:null,
		mensajeReporte:null,
		
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
		
		//botones		
		botonBuscar:null,		
		botonGenerarEtapa:null,
		botonLiquidar:null,
		
		//varibales de busqueda
		i_codEmpresaBusq:null,
		i_grupoInfBusq:null,		
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
			
			this.formCommand=$('#liquidacionBean');	
			
			
			//dialogos
			this.dialogMessage=$("#<portlet:namespace/>dialog-message-liquidacion");
			this.dialogMessageContent=$("#<portlet:namespace/>dialog-message-content-liquidacion");
			
			this.dialogConfirm=$("#<portlet:namespace/>dialog-confirm-eliminar");//para elimar
			this.dialogConfirmContent=$("#<portlet:namespace/>dialog-confirm-content-eliminar");//para eliminar
			this.dialogObservacion=$("#<portlet:namespace/>dialog-form-observacion");	
			this.dialogObservacion12=$("#<portlet:namespace/>dialog-form-observacion12");
			this.dialogObservacion13=$("#<portlet:namespace/>dialog-form-observacion13");
			
			
			//mensajes						
			this.mensajeEliminar='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Eliminando </h3>';
			this.mensajePrepara='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Generando Liq. </h3>';
			this.mensajeLiquidar='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Liquidando </h3>';
			this.mensajeReporte='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Obteniendo el Reporte </h3>';
			
			
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
			
			//botones
			this.botonBuscar=$("#<portlet:namespace/>btnBuscarLiquidacion");		
			this.botonGenerarEtapa=$("#<portlet:namespace/>btnGenerarEtapa");
			this.botonLiquidar=$("#<portlet:namespace/>btnLiquidar");
			
			//variables de busqueda
			this.i_codEmpresaBusq=$('#codEmpresaBusq');
			this.i_grupoInfBusq=$('#grupoInfBusq');				
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
			liquidacionVar.botonBuscar.click(function() {			
				liquidacionVar.buscarLiquidacion('P');
			});
			
			
			liquidacionVar.botonGenerarEtapa.click(function() {			
				liquidacionVar.preparaLiquidacionFormatos();
			});
			
			liquidacionVar.botonLiquidar.click(function() {			
				liquidacionVar.liquidarFormatos();
			});
			
			
			liquidacionVar.initDialogs();
		    
		    
			liquidacionVar.i_tipoBienal.change(function(){
				liquidacionVar.<portlet:namespace/>loadGrupoInformacion();
			});
			
			liquidacionVar.i_tipoMensual.change(function(){
				liquidacionVar.<portlet:namespace/>loadGrupoInformacion();
			});
		    
		    //eventos por defecto	
		    
			//liquidacionVar.botonBuscar.trigger('click');
			//liquidacionVar.initBlockUI();
		},
		//funcion para armar el modelo de la grilla para el resultado
		buildGrids : function () {	
			liquidacionVar.tablaResultados.jqGrid({
			   datatype: "local",
		       colNames: ['Empresa.','Formato.','Etapa Ori.','Año Pres.','Mes Pres.','Año Ejec.','Mes Ejec.','Año Ini. Vig.','Año Fin Vig.','Etapa Reco.','Liquidado','Ver','Ver Obs.','Excluir','','','',''],
		       colModel: [
                       { name: 'desEmpresa', index: 'desEmpresa', width: 50},				   
					   { name: 'formato', index: 'formato', width: 20,align:'center'},
					   { name: 'etapa', index: 'etapa', width: 30,align:'center'},	  	           
		               { name: 'anioPres', index: 'anioPres', width: 20,align:'center' },  
		               { name: 'desMes', index: 'desMes', width: 30,align:'center'},
		               { name: 'anioEjec', index: 'anioEjec', width: 20,align:'center' },   
		               { name: 'desMesEje', index: 'desMesEje', width: 30,align:'center'},
		               { name: 'anioIniVig', index: 'anioIniVig', width: 20,align:'center' },   
		               { name: 'anioFinVig', index: 'anioFinVig', width: 20,align:'center'},
		               { name: 'etapaReconocido', index: 'etapaReconocido', width: 30,align:'center' },  
		               { name: 'liquidado', index: 'liquidado', width: 20,align:'center' }, 		              
		               { name: 'verF', index: 'verF', width: 20,align:'center' },
		               { name: 'verObs', index: 'verObs', width: 20,align:'center' },	 
		               { name: 'elim', index: 'elim', width: 20,align:'center' },		    
		               { name: 'codEmpresa', index: 'codEmpresa', hidden: true},
		               { name: 'mesPres', index: 'mesPres', hidden: true},             
		               { name: 'mesEjec', index: 'mesEjec', hidden: true},
		               { name: 'correlativo', index: 'correlativo', hidden: true}		               
			   	    ],
			   	 multiselect: false,
					rowNum:10,
				   	rowList:[10,20,50],
					height: 200,
				   	autowidth: true,
					rownumbers: true,
					shrinkToFit:true,
					pager: liquidacionVar.paginadoResultados,
				    viewrecords: true,
				   	caption: "Liquidación",
				    sortorder: "asc",	   	    	   	   
		            gridComplete: function(){
		      		var ids = liquidacionVar.tablaResultados.jqGrid('getDataIDs');
		      		for(var i=0;i < ids.length;i++){
		      			var cl = ids[i];
		      			var ret = liquidacionVar.tablaResultados.jqGrid('getRowData',cl);	        			
		      			verF = "<a href='#'><img border='0' title='Ver Formato' src='/net-theme/images/img-net/file.png' align='center' onclick=\"liquidacionVar.mostrarReporteFormatos('"+ret.codEmpresa+"','"+ret.anioPres+"','"+ret.mesPres+"','"+ret.anioEjec+"','"+ret.mesEjec+"','"+ret.anioIniVig+"','"+ret.anioFinVig+"','"+ret.etapa+"','"+ret.formato+"');\" /></a> ";
		      			verObs = "<a href='#'><img border='0' title='Ver Obs.' src='/net-theme/images/img-net/file.png'  align='center' onclick=\"liquidacionVar.verObservaciones('"+ret.codEmpresa+"','"+ret.anioPres+"','"+ret.mesPres+"','"+ret.anioEjec+"','"+ret.mesEjec+"','"+ret.anioIniVig+"','"+ret.anioFinVig+"','"+ret.etapa+"','"+ret.formato+"');\" /></a> ";
		      			elim = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"liquidacionVar.confirmarEliminar('"+ret.correlativo+"','"+ret.liquidado+"');\" /></a> ";
		      			liquidacionVar.tablaResultados.jqGrid('setRowData',ids[i],{verF:verF});
		      		    liquidacionVar.tablaResultados.jqGrid('setRowData',ids[i],{verObs:verObs});
		      		    liquidacionVar.tablaResultados.jqGrid('setRowData',ids[i],{elim:elim});
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
		   <%--  liquidacionVar.tablaObservacion.jqGrid('navButtonAdd',liquidacionVar.paginadoObservacion,{
			       caption:"Exportar a Excel",
			       buttonicon: "ui-icon-bookmark",
			       onClickButton : function () {
			            location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>'; 
			       } 
			}); 
		    liquidacionVar.tablaObservacion.jqGrid('navButtonAdd',liquidacionVar.paginadoObservacion,{
			       caption:"Exportar a Pdf",
			       buttonicon: "ui-icon-bookmark",
			       onClickButton : function () {
			    	   liquidacionVar.<portlet:namespace/>mostrarReporteObservaciones();
			       } 
			}); --%>
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
		   <%--  liquidacionVar.tablaObservacion.jqGrid('navButtonAdd',liquidacionVar.paginadoObservacion,{
			       caption:"Exportar a Excel",
			       buttonicon: "ui-icon-bookmark",
			       onClickButton : function () {
			            location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>'; 
			       } 
			}); 
		    liquidacionVar.tablaObservacion.jqGrid('navButtonAdd',liquidacionVar.paginadoObservacion,{
			       caption:"Exportar a Pdf",
			       buttonicon: "ui-icon-bookmark",
			       onClickButton : function () {
			    	   liquidacionVar.<portlet:namespace/>mostrarReporteObservaciones();
			       } 
			}); --%>
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
		   <%--  liquidacionVar.tablaObservacion.jqGrid('navButtonAdd',liquidacionVar.paginadoObservacion,{
			       caption:"Exportar a Excel",
			       buttonicon: "ui-icon-bookmark",
			       onClickButton : function () {
			            location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>'; 
			       } 
			}); 
		    liquidacionVar.tablaObservacion.jqGrid('navButtonAdd',liquidacionVar.paginadoObservacion,{
			       caption:"Exportar a Pdf",
			       buttonicon: "ui-icon-bookmark",
			       onClickButton : function () {
			    	   liquidacionVar.<portlet:namespace/>mostrarReporteObservaciones();
			       } 
			}); --%>
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
							alert("Error de conexión.");
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
						alert("Error de conexión.");
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
						alert("Error al ver las observaciones");
						liquidacionVar.initBlockUI();	
					}
				},error : function(){
					alert("Error de conexión.");
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
						alert("Error al mostrar el reporte del formato");
						liquidacionVar.initBlockUI();
					}
				},error : function(){
					alert("Error de conexión.");
					liquidacionVar.initBlockUI();
				}
			});
		}, 		
		
		/**Function para confirmar para eliminar.*/
		confirmarEliminar : function(correlativo,liquidado){
			console.debug("entranado a eliminar");
			if(liquidado=='NO'){
				var addhtml='¿Está seguro que desea eliminar el registro seleccionado?';
				liquidacionVar.dialogConfirmContent.html(addhtml);
				liquidacionVar.dialogConfirm.dialog("open");
				id_Correlativo=correlativo;				
			}else{
				alert("No se puede eliminar ya esta liquidado");	
			}	
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
						var addhtml2='Registro eliminado con éxito';				
						liquidacionVar.dialogMessageContent.html(addhtml2);
						liquidacionVar.dialogMessage.dialog("open");
						liquidacionVar.buscarLiquidacion('B');
						liquidacionVar.initBlockUI();						
					}else{
						alert("Error al eliminar el registro");
						liquidacionVar.initBlockUI();
					}					
				},error : function(){
					alert("Error de conexión.");
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
					if(data.resultado == "OK"){
						var addhtml2='La preparación de liquidación fue correcta';				
						liquidacionVar.dialogMessageContent.html(addhtml2);
						liquidacionVar.dialogMessage.dialog("open");
						liquidacionVar.buscarLiquidacion('B');
						liquidacionVar.initBlockUI();						
					}else{
						alert("Error al preparar para realizar la liquidación");
						liquidacionVar.initBlockUI();
					}					
				},error : function(){
					alert("Error de conexión.");
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
					if(data.resultado == "OK"){
						var addhtml2='La liquidación se realizó con éxito';				
						liquidacionVar.dialogMessageContent.html(addhtml2);
						liquidacionVar.dialogMessage.dialog("open");
						liquidacionVar.buscarLiquidacion('B');
						liquidacionVar.initBlockUI();						
					}else{
						alert("Error al realizar la liquidación");
						liquidacionVar.initBlockUI();
					}					
				},error : function(){
					alert("Error de conexión.");
					liquidacionVar.initBlockUI();
				}
			});
		},	
		
		//funcion para ver reposrte en una nueva pestaña
		verReporteFormato : function(){
			window.open('<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ViewReport")%>','_newtab');
		}, 
		
		//DIALOGOS
		initDialogs : function(){		
			//dialogo para eliminar
			liquidacionVar.dialogConfirm.dialog({
				modal: true,
				height: 200,
				width: 400,			
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
			
			liquidacionVar.dialogMessage.dialog({
				modal: true,
				autoOpen: false,
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