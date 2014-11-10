<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects />

<script type="text/javascript">
var formato13A= {
	tablaResultados:null,
	paginadoResultados:null,
	tablaDeclaracion:null,
	paginadoDeclaracion:null,
	urlBusqueda: null,
	urlBusquedaDetalle: null,
	urlCargaDeclaracion: null,
	urlProvincias:null,
	urlDistritos:null,
	botonBuscar:null,
	formBusqueda: null,
	formNuevo : null,
	formDetalle : null,
	botonCrearFormato : null,
	botonGuardarDetalle:null,
	codEmpresa : null,
	peridoDeclaracion : null,

	mensajeObteniendoDatos:null,
	urlACrud:null,
	command:null,
	
	btnExcel:null,
	dialogCargaExcel:null,
	btnCargarFormatoExcel:null,
	
	btnTxt:null,
	dialogCargaTexto:null,
	btnCargarFormatoTexto:null,
	
	divOverlay:null,
	

	codDepa:null,
	codProv:null,
	codDist:null,

	init : function(urlNuevo){
		this.tablaResultados=$("#<portlet:namespace/>grid_formato");
		this.paginadoResultados='#<portlet:namespace/>pager_formato';
		this.urlBusqueda='<portlet:resourceURL id="busqueda" />';
		this.botonBuscar=$("#<portlet:namespace/>buscarFormato");
		this.formBusqueda=$('#formato13AGartCommand');
		this.botonCrearFormato=$('#<portlet:namespace/>crearFormato');
		
		this.mensajeObteniendoDatos='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Obteniendo Datos </h3>';
		this.urlACrud='<portlet:resourceURL id="getData" />';
		this.command=$('#formato13AGartCommand');
		
		formato13A.botonCrearFormato.click(function() {
			formato13A.blockUI();
			location.href=urlNuevo;
		});
		
		formato13A.botonBuscar.click(function() {
			formato13A.buscarFormatos();
		});
		
		formato13A.buildGridsBusqueda();
		formato13A.botonBuscar.trigger('click');
		
		
		
	},
	initCRUD : function(operacion,urlAnadirFormato,urlRegresarBusqueda){
		this.urlCargaDeclaracion='<portlet:resourceURL id="cargaPeriodoDeclaracion" />';
		this.urlBusquedaDetalle='<portlet:resourceURL id="busquedaDetalle" />';
		this.formNuevo=$('#formato13AGartCommand');
		this.codEmpresa=$('#codEmpresa');
		this.peridoDeclaracion=$('#peridoDeclaracion');
		this.tablaDeclaracion=$("#<portlet:namespace/>grid_formato_declaracion");
		this.paginadoDeclaracion='#<portlet:namespace/>pager_formato_declaracion';
		var botonAnadirFormato=$('#<portlet:namespace/>anadirFormato');
		var botonRegresarBusqueda=$('#<portlet:namespace/>regresarBusqueda');
		
		this.btnExcel=$('#<portlet:namespace/>showDialogUploadExcel');
		this.dialogCargaExcel=$("#<portlet:namespace/>dialog-form-cargaExcel");
		this.btnCargarFormatoExcel=$('#<portlet:namespace/>cargarFormatoExcel');
		this.divOverlay=$("#<portlet:namespace/>divOverlay");
		
		this.btnTxt=$('#<portlet:namespace/>showDialogUploadTxt');
		this.dialogCargaTexto=$("#<portlet:namespace/>dialog-form-cargaTxt");
		this.btnCargarFormatoTexto=$('#<portlet:namespace/>cargarFormatoTxt');
		
		formato13A.btnExcel.click(function() {formato13A.<portlet:namespace/>showUploadExcel();});
		formato13A.btnCargarFormatoExcel.click(function() {formato13A.<portlet:namespace/>cargarFormatoExcel();});
		formato13A.btnTxt.click(function() {formato13A.<portlet:namespace/>showUploadTxt();});
		
		formato13A.buildGridsDeclaracion();
		
		if(operacion=='CREATE'){
			
			formato13A.codEmpresa.change(function(){
				$.when( formato13A.cargarPeriodoDeclaracion()).then(formato13A.buscarDetalles);
			});
			
			formato13A.peridoDeclaracion.change(function(){
				formato13A.buscarDetalles();
			});
			formato13A.codEmpresa.trigger('change');

			
			botonAnadirFormato.click(function(){
				formato13A.blockUI();
				formato13A.formNuevo.attr('action',urlAnadirFormato+'&strip=0').submit();
			});
			
			botonRegresarBusqueda.click(function(){
				formato13A.blockUI();
				location.href=urlRegresarBusqueda;
			});
			

		}
		
		
	},
	initCRUDDetalle : function(operacion,urlGuardarDetalle){
		this.formDetalle=$("#formato13AGartCommand");
		this.codDepa=$("select[name='codDepartamento']");
		this.codProv=$("select[name='codProvincia']");
		this.codDist=$("select[name='codDistrito']");
		this.urlProvincias='<portlet:resourceURL id="provincias" />';
		this.urlDistritos='<portlet:resourceURL id="distritos" />';
		this.botonGuardarDetalle=$('#<portlet:namespace/>guardarDetalle');
		
		if(operacion=='CREATE'){
			formato13A.codDepa.change(function(){
				formato13A.listarProvincias(formato13A.codDepa.val());
			});
			
			formato13A.codProv.change(function(){
				formato13A.listarDistritos(formato13A.codProv.val());
			});
			
			formato13A.botonGuardarDetalle.click(function(){
				formato13A.formDetalle.attr('action',urlGuardarDetalle).submit();
			});
		}
		
	},
	buildGridsBusqueda : function () {	
		formato13A.tablaResultados.jqGrid({
		   datatype: "local",
	       colNames: ['Empresa','Año Pres.','Mes Pres.','Etapa','Grupo Inf','Visualizar','Editar','Anular','',''],
	       colModel: [
					{ name: 'descEmpresa', index: 'descEmpresa', width: 50},
	               { name: 'anoPresentacion', index: 'anoPresentacion', width: 30 },   
	               { name: 'descMesPresentacion', index: 'descMesPresentacion', width: 30},
	               { name: 'etapa', index: 'etapa',width: 50},
	               { name: 'descGrupoInformacion', index: 'descGrupoInformacion', width: 50},
	               { name: 'view', index: 'view', width: 20,align:'center' },
	               { name: 'edit', index: 'edit', width: 20,align:'center' },
	               { name: 'elim', index: 'elim', width: 20,align:'center' },
	               { name: 'codEmpresa', index: 'codEmpresa', hidden: true},
	               { name: 'mesPresentacion', index: 'mesPresentacion', hidden: true}
		   	    ],
		   	 multiselect: false,
				rowNum:10,
			   	rowList:[10,20,50],
				height: 200,
			   	autowidth: true,
				rownumbers: true,
				shrinkToFit:true,
				pager: formato13A.paginadoResultados,
			    viewrecords: true,
			   	caption: "Formatos",
			    sortorder: "asc",	   	    	   	   
	       gridComplete: function(){
	      		var ids = formato13A.tablaResultados.jqGrid('getDataIDs');
	      		for(var i=0;i < ids.length;i++){
	      			var cl = ids[i];
	      			var ret = formato13A.tablaResultados.jqGrid('getRowData',cl);           
	      			view = "<a href='#'><img border='0' title='View' src='/net-theme/images/img-net/file.png'  align='center' onclick=\"formato13A.verFormato('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.etapa+"');\" /></a> ";
	      			edit = "<a href='#'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png'  align='center' onclick=\"editarFormato('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoEjecucion+"','"+ret.mesEjecucion+"','"+ret.etapa+"');\" /></a> ";
	      			elem = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"confirmarEliminar('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoEjecucion+"','"+ret.mesEjecucion+"','"+ret.etapa+"');\" /></a> ";              			
	      			formato13A.tablaResultados.jqGrid('setRowData',ids[i],{view:view});
	      			formato13A.tablaResultados.jqGrid('setRowData',ids[i],{edit:edit});
	      			formato13A.tablaResultados.jqGrid('setRowData',ids[i],{elim:elem});
	      		}
	      }
	  	});
		formato13A.tablaResultados.jqGrid('navGrid',formato13A.paginadoResultados,{add:false,edit:false,del:false,search: false,refresh: false});	
		formato13A.tablaResultados.jqGrid('navButtonAdd',formato13A.paginadoResultados,{
		       caption:"Exportar a Excel",
		       buttonicon: "ui-icon-bookmark",
		       onClickButton : function () {
		           location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';
		       } 
		}); 
	},
	buscarFormatos : function () {	

		jQuery.ajax({			
					url: formato13A.urlBusqueda+'&'+formato13A.formBusqueda.serialize(),
					type: 'post',
					dataType: 'json',
					beforeSend:function(){
						formato13A.blockUI();
					},				
					success: function(gridData) {					
						formato13A.tablaResultados.clearGridData(true);
						formato13A.tablaResultados.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
						formato13A.tablaResultados[0].refreshIndex();
						formato13A.unblockUI();
					},error : function(){
						alert("Error de conexión.");
						formato13A.unblockUI();
					}
			});


	},
	blockUI : function(){
		$.blockUI({ message: '<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Cargando </h3>' });
	},
	unblockUI : function(){
		$.unblockUI();
	},
	cargarPeriodoDeclaracion : function(){
		return jQuery.ajax({
			url: formato13A.urlCargaDeclaracion,
			type: 'post',
			dataType: 'json',
			beforeSend:function(){
				formato13A.blockUI();
			},
			data: {
				codEmpresa: formato13A.codEmpresa.val(),
				anoPresentacion: formato13A.peridoDeclaracion.val()
				},
			success: function(data) {		
				dwr.util.removeAllOptions("peridoDeclaracion");
				dwr.util.addOptions("peridoDeclaracion", data,"codigoItem","descripcionItem");
				formato13A.unblockUI();
			},error : function(){
				alert("Error de conexión.");
				formato13A.unblockUI();
			}
		});
	},
	buildGridsDeclaracion : function () {	
		formato13A.tablaDeclaracion.jqGrid({
		   datatype: "local",
	       colNames: ['Año / Mes Alta','Cod. Ubigeo','Localidad','ST-1','ST-2','ST-3','ST-4','ST-5','ST-6','ST-SER','Especial','Total','Zona Benef.','Sede que Atiende','Visualizar','Editar','Anular','','','',''],
	       colModel: [
					{ name: 'anioMesAlta', index: 'anioMesAlta', width: 70},
		            { name: 'codUbigeo', index: 'codUbigeo', width: 50},
		            { name: 'descripcionLocalidad', index: 'descripcionLocalidad', width: 50},
	                { name: 'st1', index: 'st1', width: 20},
	                { name: 'st2', index: 'st2', width: 20},
	                { name: 'st3', index: 'st3', width: 20},
	                { name: 'st4', index: 'st4', width: 20},
	                { name: 'st5', index: 'st5', width: 20},
	                { name: 'st6', index: 'st6', width: 20},
	                { name: 'stserv', index: 'stserv', width: 20},
	                { name: 'stesp', index: 'stesp', width: 20},
	                { name: 'total', index: 'total', width: 30},
	                { name: 'idZonaBenef', index: 'idZonaBenef', width: 70},
	                { name: 'nombreSedeAtiende', index: 'nombreSedeAtiende', width: 80},
	               { name: 'view', index: 'view', width: 20,align:'center' },
	               { name: 'edit', index: 'edit', width: 20,align:'center' },
	               { name: 'elim', index: 'elim', width: 20,align:'center' },
	               { name: 'codEmpresa', index: 'codEmpresa', hidden: true},
	               { name: 'mesPresentacion', index: 'mesPresentacion', hidden: true},
	               { name: 'anoPresentacion', index: 'anoPresentacion', hidden: true },   
	               { name: 'etapa', index: 'etapa',hidden: true}
		   	    ],
		   	 multiselect: false,
				rowNum:10,
			   	rowList:[10,20,50],
				height: 200,
			   	autowidth: true,
				rownumbers: true,
				shrinkToFit:true,
				pager: formato13A.paginadoDeclaracion,
			    viewrecords: true,
			   	caption: "Formatos declarados",
			    sortorder: "asc",	   	    	   	   
	       gridComplete: function(){
	    	   AUI().use('liferay-portlet-url', function(A) {
	    		      var ids = formato13A.tablaDeclaracion.jqGrid('getDataIDs');
		  	      		for(var i=0;i < ids.length;i++){
		  	      			var cl = ids[i];
		  	      			var ret = formato13A.tablaDeclaracion.jqGrid('getRowData',cl);   
		  	      			var urlView=Liferay.PortletURL.createRenderURL();
		  	      			urlView.setParameter("action", "nuevoDetalle");
		  	      			view = "<a href='#'><img border='0' title='View' src='/net-theme/images/img-net/file.png'  align='center' onclick=\"verFormato('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoEjecucion+"','"+ret.mesEjecucion+"','"+ret.etapa+"');\" /></a> ";
		  	      			edit = "<a href='#'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png'  align='center' onclick=\"editarFormato('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoEjecucion+"','"+ret.mesEjecucion+"','"+ret.etapa+"');\" /></a> ";
		  	      			elem = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"confirmarEliminar('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoEjecucion+"','"+ret.mesEjecucion+"','"+ret.etapa+"');\" /></a> ";              			
		  	      			formato13A.tablaDeclaracion.jqGrid('setRowData',ids[i],{view:view});
		  	      			formato13A.tablaDeclaracion.jqGrid('setRowData',ids[i],{edit:edit});
		  	      			formato13A.tablaDeclaracion.jqGrid('setRowData',ids[i],{elim:elem});
		  	      		}
	    		   });	
	      }
	  	});
		formato13A.tablaDeclaracion.jqGrid('navGrid',formato13A.paginadoDeclaracion,{add:false,edit:false,del:false,search: false,refresh: false});	
		formato13A.tablaDeclaracion.jqGrid('navButtonAdd',formato13A.paginadoDeclaracion,{
		       caption:"Exportar a Excel",
		       buttonicon: "ui-icon-bookmark",
		       onClickButton : function () {
		           location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';
		       } 
		}); 
	},
	buscarDetalles : function () {	

		return jQuery.ajax({			
					url: formato13A.urlBusquedaDetalle+'&'+formato13A.formNuevo.serialize(),
					type: 'post',
					dataType: 'json',
					beforeSend:function(){
						formato13A.blockUI();
					},				
					success: function(gridData) {					
						formato13A.tablaDeclaracion.clearGridData(true);
						formato13A.tablaDeclaracion.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
						formato13A.tablaDeclaracion[0].refreshIndex();
						formato13A.unblockUI();
					},error : function(){
						alert("Error de conexión.");
						formato13A.unblockUI();
					}
			});



	},
	<portlet:namespace/>showUploadExcel : function(){
		formato13A.divOverlay.show();
		formato13A.dialogCargaExcel.show();
		formato13A.dialogCargaExcel.show();
		formato13A.dialogCargaExcel.css({ 
	        'left': ($(window).width() / 2 - formato13A.dialogCargaExcel.width() / 2) + 'px', 
	        'top': ($(window).height()  - formato13A.dialogCargaExcel.height() ) + 'px'
	    });
	},
	
	closeDialogCargaExcel : function(){
		formato13A.dialogCargaExcel.hide();
		formato13A.divOverlay.hide();   
	},
	
	<portlet:namespace/>cargarFormatoExcel : function(){
		var frm = document.getElementById('formato13AGartCommand');
		frm.submit();
	

	},
	
	<portlet:namespace/>showUploadTxt : function(){
		formato13A.divOverlay.show();
		formato13A.dialogCargaTexto.show();
		formato13A.dialogCargaTexto.show();
		formato13A.dialogCargaTexto.css({ 
	        'left': ($(window).width() / 2 - formato13A.dialogCargaTexto.width() / 2) + 'px', 
	        'top': ($(window).height()  - formato13A.dialogCargaTexto.height() ) + 'px'
	    });
	},
	
	closeDialogCargaTxt : function(){
		formato13A.dialogCargaTexto.hide();
		formato13A.divOverlay.hide();   
	},

	
	listarProvincias : function (codDepartamento) {	
		jQuery.ajax({			
					url: formato13A.urlProvincias,
					type: 'post',
					dataType: 'json',
					data:{
						codDepartamento:codDepartamento
					},
					beforeSend:function(){
						formato13A.blockUI();
					},				
					success: function(data) {					
						dwr.util.removeAllOptions("codProvincia");
						dwr.util.addOptions("codProvincia", data,"codigoItem","descripcionItem");
						formato13A.limpiarDistritos();
						formato13A.unblockUI();
					},error : function(){
						alert("Error de conexión.");
						formato13A.unblockUI();
					}
			});


	},
	listarDistritos : function (codProvincia) {	
		jQuery.ajax({			
					url: formato13A.urlDistritos,
					type: 'post',
					dataType: 'json',
					data:{
						codProvincia:codProvincia
					},
					beforeSend:function(){
						formato13A.blockUI();
					},				
					success: function(data) {					
						dwr.util.removeAllOptions("codDistrito");
						dwr.util.addOptions("codDistrito", data,"codigoItem","descripcionItem");
						formato13A.unblockUI();
					},error : function(){
						alert("Error de conexión.");
						formato13A.unblockUI();
					}
			});


	},
	emptySelectObject: function(){
		var jsonVacio='[{"descripcionItem":"--Seleccione--","codigoItem":""}]';
		return eval("(" + jsonVacio + ")");
	},
	limpiarProvincias:function(){
		dwr.util.removeAllOptions("codProvincia");
		dwr.util.addOptions("codProvincia", formato13A.emptySelectObject(),"codigoItem","descripcionItem");
	},
	limpiarDistritos:function(){
		dwr.util.removeAllOptions("codDistrito");
		dwr.util.addOptions("codDistrito", formato13A.emptySelectObject(),"codigoItem","descripcionItem");
	},
	
	//FORMULARIOS DE VIEW Y EDICION
	verFormato : function(codEmpresa,anoPresentacion,mesPresentacion,etapa){	
		$.blockUI({ message: formato13A.mensajeObteniendoDatos });
		jQuery.ajax({
				url: formato13A.urlACrud+'&'+formato13A.command.serialize(),
				type: 'post',
				dataType: 'json',
				data: {
				   <portlet:namespace />tipo: "GET",
				   <portlet:namespace />codEmpresa: codEmpresa,
				   <portlet:namespace />anoPresentacion: anoPresentacion,
				   <portlet:namespace />mesPresentacion: mesPresentacion,
				   <portlet:namespace />etapa: etapa
					},
				success: function(data) {				
					if (data.resultado == "OK"){
						formato13A.blockUI();
					}
					else{
						alert("Error al recuperar los datos del registro seleccionado");
						formato13A.blockUI();
					}
				},error : function(){
					alert("Error de conexión.");
					formato13A.blockUI();
				}
		});	
	},

	
};



</script>