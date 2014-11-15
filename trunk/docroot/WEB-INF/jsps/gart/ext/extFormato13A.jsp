<%@page import="com.liferay.portal.util.PortalUtil"%>
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
	//URL
	urlBusqueda: null,
	urlBusquedaDetalle: null,
	urlCargaDeclaracion: null,
	urlProvincias:null,
	urlDistritos:null,
	urlReporte:null,
	
	botonReportePdf:null,
	botonReporteExcel:null,
	
	botonBuscar:null,
	formBusqueda: null,
	formNuevo : null,
	formDetalle : null,
	botonCrearFormato : null,
	botonGuardarDetalle:null,
	codEmpresa : null,
	peridoDeclaracion : null,
	portletID:null,
	mensajeObteniendoDatos:null,
	urlACrud:null,
	command:null,
	
	btnGuardarCabecera:null,
	urlGuardarCabecera:null,
	dlgConfirmacion:null,
	
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
	
	//detalleCRUD
	codEmpresaDetalle:null,
	anoPresentacionDetalle:null,
	mesPresentacionDetalle:null,
	etapaDetalle:null,
	//valores sector tipico
	st1Detalle:null,
	st2Detalle:null,
	st3Detalle:null,
	st4Detalle:null,
	st5Detalle:null,
	st6Detalle:null,
	stserDetalle:null,
	stespDetalle:null,
	sttotalDetalle:null,
	
	init : function(urlNuevo,urlView,urlEdit){
		
		this.tablaResultados=$("#<portlet:namespace/>grid_formato");
		this.paginadoResultados='#<portlet:namespace/>pager_formato';
		this.urlBusqueda='<portlet:resourceURL id="busqueda" />';
		this.botonBuscar=$("#<portlet:namespace/>buscarFormato");
		this.formBusqueda=$('#formato13AGartCommand');
		this.botonCrearFormato=$('#<portlet:namespace/>crearFormato');
		this.dlgConfirmacion=$("#<portlet:namespace/>divDlgDelete");
		this.divOverlay=$("#<portlet:namespace/>divOverlay");
		this.mensajeObteniendoDatos='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Obteniendo Datos </h3>';
		//this.urlACrud='<portlet:resourceURL id="getData" />';
		this.command=$('#formato13AGartCommand');
		this.urlACrud=urlView;
		
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
		this.portletID='<%=PortalUtil.getPortletId(renderRequest)%>';
		this.urlCargaDeclaracion='<portlet:resourceURL id="cargaPeriodoDeclaracion" />';
		this.urlBusquedaDetalle='<portlet:resourceURL id="busquedaDetalle" />';
		this.formNuevo=$('#formato13AGartCommand');
		this.codEmpresa=$('#codEmpresa');
		this.peridoDeclaracion=$('#peridoDeclaracion');
		this.tablaDeclaracion=$("#<portlet:namespace/>grid_formato_declaracion");
		this.paginadoDeclaracion='#<portlet:namespace/>pager_formato_declaracion';
		var botonAnadirFormato=$('#<portlet:namespace/>anadirFormato');
		var botonRegresarBusqueda=$('#<portlet:namespace/>regresarBusqueda');
		
		this.btnGuardarCabecera=$('#<portlet:namespace/>guardarFormato');
		//this.urlGuardarCabecera='<portlet:actionURL name="saveNuevoFormato"/>';
		this.urlGuardarCabecera='<portlet:resourceURL id="saveNuevoFormato"/>';
		
		
		this.btnExcel=$('#<portlet:namespace/>showDialogUploadExcel');
		this.dialogCargaExcel=$("#<portlet:namespace/>dialog-form-cargaExcel");
		this.btnCargarFormatoExcel=$('#<portlet:namespace/>cargarFormatoExcel');
		this.divOverlay=$("#<portlet:namespace/>divOverlay");
		
		this.btnTxt=$('#<portlet:namespace/>showDialogUploadTxt');
		this.dialogCargaTexto=$("#<portlet:namespace/>dialog-form-cargaTxt");
		this.btnCargarFormatoTexto=$('#<portlet:namespace/>cargarFormatoTxt');
		
		this.urlReporte='<portlet:resourceURL id="reporte" />';
		this.botonReportePdf=$("#<portlet:namespace/>reportePdf");
		this.botonReporteExcel=$("#<portlet:namespace/>reporteExcel");
		
		formato13A.btnExcel.click(function() {formato13A.<portlet:namespace/>showUploadExcel();});
		formato13A.btnCargarFormatoExcel.click(function() {formato13A.<portlet:namespace/>cargarFormatoExcel();});
		formato13A.btnTxt.click(function() {formato13A.<portlet:namespace/>showUploadTxt();});
		
		formato13A.btnGuardarCabecera.click(function(){formato13A.savecabecera();});
		
		formato13A.buildGridsDeclaracion();
		
		if(operacion=='CREATE'){
			
			formato13A.codEmpresa.change(function(){
				$.when(formato13A.cargarPeriodoDeclaracion()).then(formato13A.buscarDetalles);
			});
			
			formato13A.peridoDeclaracion.change(function(){
				formato13A.buscarDetalles();
			});
			formato13A.codEmpresa.trigger('change');

			
			botonAnadirFormato.click(function(){
				formato13A.blockUI();
				formato13A.formNuevo.attr('action',urlAnadirFormato+'&strip=0').removeAttr('enctype').submit();
			});
			
			botonRegresarBusqueda.click(function(){
				formato13A.blockUI();
				location.href=urlRegresarBusqueda;
			});
			

		}if(operacion=='READ'){
			formato13A.buscarDetalles();
			
			formato13A.botonReportePdf.click(function() {formato13A.<portlet:namespace/>mostrarReportePdf();});
			formato13A.botonReporteExcel.click(function() {formato13A.<portlet:namespace/>mostrarReporteExcel();});
			
			botonRegresarBusqueda.click(function(){
				formato13A.blockUI();
				location.href=urlRegresarBusqueda;
			});
			
		}if(operacion=='UPDATE'){
			
			formato13A.cargarPeriodoDeclaracion();
			formato13A.buscarDetalles();
			/*formato13A.codEmpresa.change(function(){
				$.when( formato13A.cargarPeriodoDeclaracion()).then(formato13A.buscarDetalles);
			});*/
			
			/*formato13A.peridoDeclaracion.change(function(){
				formato13A.buscarDetalles();
		});
			formato13A.codEmpresa.trigger('change');
*/
			
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
	initCRUDDetalle : function(operacion,urlGuardarDetalle,urlRegresarDetalle){
		this.formDetalle=$("#formato13AGartCommand");
		this.codDepa=$("select[name='codDepartamento']");
		this.codProv=$("select[name='codProvincia']");
		this.codDist=$("select[name='codDistrito']");
		this.urlProvincias='<portlet:resourceURL id="provincias" />';
		this.urlDistritos='<portlet:resourceURL id="distritos" />';
		this.botonGuardarDetalle=$('#<portlet:namespace/>guardarDetalle');
		var botonRegresarDetalle=$('#<portlet:namespace/>regresarFormato');
		//valores de cabecera
		this.codEmpresaDetalle=$('#codEmpresa');
		this.anoPresentacionDetalle=$('#anioPresentacion');
		this.mesPresentacionDetalle=$('#mesPresentacion');
		this.etapaDetalle=$('#etapa');
		//valores de sector tipico
		this.st1Detalle=$('#st1');
		this.st2Detalle=$('#st2');
		this.st3Detalle=$('#st3');
		this.st4Detalle=$('#st4');
		this.st5Detalle=$('#st5');
		this.st6Detalle=$('#st6');
		this.stserDetalle=$('#stser');
		this.stespDetalle=$('#stesp');
		this.sttotalDetalle=$('#total');
		
		$('input.target[type=text]').on('change', function(){
			formato13A.calculoTotal();
		});
		
		<c:if test="${crud =='CREATE'}">
			formato13A.codDepa.change(function(){
				formato13A.listarProvincias(formato13A.codDepa.val());
			});
			
			formato13A.codProv.change(function(){
				formato13A.listarDistritos(formato13A.codProv.val());
			});
			
			formato13A.botonGuardarDetalle.click(function(){
				formato13A.formDetalle.attr('action',urlGuardarDetalle+'&crud='+operacion).submit();
			});
			
			botonRegresarDetalle.click(function(){
				formato13A.blockUI();
				location.href=urlRegresarDetalle+'&crud='+operacion+'&codEmpresa='+formato13A.codEmpresaDetalle.val()+'&anoPresentacion='+formato13A.anoPresentacionDetalle.val()+'&mesPresentacion='+formato13A.mesPresentacionDetalle.val()+'&etapa='+formato13A.etapaDetalle.val()+'&tipo=1';
			});
		
		</c:if>
		
		<c:if test="${crud =='READ'}">
			botonRegresarDetalle.click(function(){
				formato13A.blockUI();
				location.href=urlRegresarDetalle+'&crud='+operacion+'&codEmpresa='+formato13A.codEmpresaDetalle.val()+'&anoPresentacion='+formato13A.anoPresentacionDetalle.val()+'&mesPresentacion='+formato13A.mesPresentacionDetalle.val()+'&etapa='+formato13A.etapaDetalle.val()+'&tipo=0';
				//location.href=urlRegresarDetalle+'&crud='+operacion+'&'+formato13A.formDetalle.serialize()+'&tipo=0';
			});
		</c:if>
		<c:if test="${crud =='UPDATE'}">
		
			formato13A.botonGuardarDetalle.click(function(){
				formato13A.formDetalle.attr('action',urlGuardarDetalle+'&crud='+operacion).submit();
			});
		
			botonRegresarDetalle.click(function(){
				formato13A.blockUI();
				location.href=urlRegresarDetalle+'&crud='+operacion+'&codEmpresa='+formato13A.codEmpresaDetalle.val()+'&anoPresentacion='+formato13A.anoPresentacionDetalle.val()+'&mesPresentacion='+formato13A.mesPresentacionDetalle.val()+'&etapa='+formato13A.etapaDetalle.val()+'&tipo=1';
				//location.href=urlRegresarDetalle+'&crud='+operacion+'&'+formato13A.formDetalle.serialize()+'&tipo=1';
			});
			
		</c:if>
		
		/* botonRegresarDetalle.click(function(){
			formato13A.blockUI();
			location.href=urlRegresarDetalle+'&crud='+operacion+'&'+formato13A.formDetalle.serialize();
		}); */
		
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
	      			

	      			view = "<a href='"+formato13A.urlACrud+"&codEmpresa="+ret.codEmpresa+"&anioPresentacion="+ret.anoPresentacion+"&mesPresentacion="+ret.mesPresentacion+"&etapa="+ret.etapa+"&tipo=0' ><img border='0' title='View' src='/net-theme/images/img-net/file.png'  align='center' /></a> ";
	      			edit = "<a href='"+formato13A.urlACrud+"&codEmpresa="+ret.codEmpresa+"&anioPresentacion="+ret.anoPresentacion+"&mesPresentacion="+ret.mesPresentacion+"&etapa="+ret.etapa+"&tipo=1'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png'  align='center' /></a> ";
	      			elem = "<a href='#' onclick=\"formato13A.showconfirmacion('ss','ss')\"><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center'  /></a> ";              			

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
		  	      		
		  	      			urlView.setParameter("action", "detalle");
		  	      			urlView.setParameter("crud", "READ");
		  	      			urlView.setParameter("codEmpresa", ret.codEmpresa);
		  	      			urlView.setParameter("periodoDeclaracion", ret.anoPresentacion+ret.mesPresentacion+ret.etapa);
		  	      			urlView.setPortletId(formato13A.portletID);
		  	      			
		  	      			
		  	      			view = "<a href='"+urlView+"'><img border='0' title='View' src='/net-theme/images/img-net/file.png'  align='center'  /></a> ";
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
		var frm = document.getElementById('form_13ACRUD');
		frm.submit();
	

	},
	
	<portlet:namespace/>showUploadTxt : function(){
		formato13A.divOverlay.show();
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
	verFormato : function(codEmpresa,anoPresentacion,mesPresentacion,etapa,tipo){	
		$.blockUI({ message: formato13A.mensajeObteniendoDatos });
		location.href=formato13A.urlACrud+'&codEmpresa='+codEmpresa+'&anoPresentacion='+anoPresentacion+'&mesPresentacion='+mesPresentacion+'&etapa='+etapa+'&tipo='+tipo;
		
	},

	savecabecera : function(){	
		var form = $('#form_13ACRUD').serialize();
		jQuery.ajax({	
			url: formato13A.urlGuardarCabecera,
			cache : false,
			async : false,
			type: 'post',
			data : form,
			dataType : "text",
			beforeSend:function(){
				formato13A.blockUI();
			},				
			success: function(data) {					
				if(data == '1'){
					formato13A.codEmpresa.attr("disabled", true);
					formato13A.peridoDeclaracion.attr("disabled", true);
				}else if(data == '-1'){
					alert('El formato ya existe para esa empresa para ese periodo');
				}else if(data == '-2'){
					alert('Error al obtener usuario');
				}else if(data == '-3'){
					alert('Error al obtener terminal');
				}else if(data == '-4'){
					alert('Error al registrar');
				}
				
				
				formato13A.unblockUI();
			},error : function(){
				alert("Error de conexión.");
				formato13A.unblockUI();
			}
	});
		
	},
	
	showconfirmacion:function(msj,valor){
	
	$("#estado_proceso").html("Esta seguro de eliminar formato");
		formato13A.dlgConfirmacion.dialog({ 
	     	title:"Confirmacion",
	     	height: 200,
			width: 200,	
	         modal: true,
	         buttons:{
	        	 "Si":function(){
	        		 $( this ).dialog( "close" );
	        		
	        	 },
	        	 "No":function(){
	        		 $( this ).dialog( "close" );
	        		
	        	 }
	         },
	         open: function(event, ui) {$(".ui-dialog-titlebar-close").show(); }
	     }).load();
	},


	//calculo total
	calculoTotal : function(){
		var st1;
		var st2;
		var st3;
		var st4;
		var st5;
		var st6;
		var stser;
		var stesp;
		var total;
		st1=formato13A.st1Detalle.val();
		st2=formato13A.st2Detalle.val();
		st3=formato13A.st3Detalle.val();
		st4=formato13A.st4Detalle.val();
		st5=formato13A.st5Detalle.val();
		st6=formato13A.st6Detalle.val();
		stser=formato13A.stserDetalle.val();
		stesp=formato13A.stespDetalle.val();
		console.debug(''+st1+','+st2+','+st3+','+st4+','+st5+','+st6+','+stser+','+stesp);
		total=parseFloat(st1)+parseFloat(st2)+parseFloat(st3)+parseFloat(st4)+parseFloat(st5)+parseFloat(st6)+parseFloat(stser)+parseFloat(stesp);
		formato13A.sttotalDetalle.val(parseFloat(total));
	},
	//MOSTRAR REPORTE
	<portlet:namespace/>mostrarReportePdf : function(){
		var form = formato13A.formNuevo;
		form.removeAttr('enctype');
		
		jQuery.ajax({
			url : formato13A.urlReporte+'&'+form.serialize(),
			type : 'post',
			dataType : 'json',
			data : {
				//<portlet:namespace />codEmpresa: formato13A.f_empresa.val(),
				//<portlet:namespace />periodoEnvio: formato13A.f_periodoEnvio.val(),
				//<portlet:namespace />anoInicioVigencia: $('#anioInicioVigencia').val(),
				//<portlet:namespace />anoFinVigencia: $('#anioFinVigencia').val(),
				<portlet:namespace />nombreReporte: 'formato13A',
				<portlet:namespace />nombreArchivo: 'formato13A',
				<portlet:namespace />tipoArchivo: '0'//PDF
			},
			success : function(gridData) {
				formato13A.verReporte();
			},error : function(){
				alert("Error de conexión.");
				formato13A.initBlockUI();
			}
		});
	},
	<portlet:namespace/>mostrarReporteExcel : function(){
		jQuery.ajax({
			url : formato13A.urlReporte+'&'+formato13A.formNuevo.serialize(),
			type : 'post',
			dataType : 'json',
			data : {
				//<portlet:namespace />codEmpresa: formato13A.f_empresa.val(),
				//<portlet:namespace />periodoEnvio: formato13A.f_periodoEnvio.val(),
				//<portlet:namespace />anoInicioVigencia: $('#anioInicioVigencia').val(),
				//<portlet:namespace />anoFinVigencia: $('#anioFinVigencia').val(),
				<portlet:namespace />nombreReporte: 'formato13A',
				<portlet:namespace />nombreArchivo: 'formato13A',
				<portlet:namespace />tipoArchivo: '1'//XLS
			},
			success : function(gridData) {
				//alert('entro');
				formato13A.verReporte();
			},error : function(){
				alert("Error de conexión.");
				formato13A.initBlockUI();
			}
		});
	},
	verReporte : function(){
		window.open('<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ViewReport")%>','_newtab');
	}

	
};



</script>