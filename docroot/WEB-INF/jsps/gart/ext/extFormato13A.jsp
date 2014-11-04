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
	urlCargaDeclaracion: null,
	botonBuscar:null,
	formBusqueda: null,
	formNuevo : null,
	botonCrearFormato : null,
	codEmpresa : null,
	peridoDeclaracion : null,
	init : function(urlNuevo){
		this.tablaResultados=$("#<portlet:namespace/>grid_formato");
		this.paginadoResultados='#<portlet:namespace/>pager_formato';
		this.urlBusqueda='<portlet:resourceURL id="busqueda" />';
		this.botonBuscar=$("#<portlet:namespace/>buscarFormato");
		this.formBusqueda=$('#formato13AGartCommand');
		this.botonCrearFormato=$('#<portlet:namespace/>crearFormato');
		
		formato13A.botonCrearFormato.click(function() {
			formato13A.blockUI();
			location.href=urlNuevo;
		});
		
		formato13A.botonBuscar.click(function() {
			formato13A.buscar();
		});
		
		formato13A.buildGridsBusqueda();
		formato13A.botonBuscar.trigger('click');
		
	},
	initCRUD : function(operacion){
		this.urlCargaDeclaracion='<portlet:resourceURL id="cargaPeriodoDeclaracion" />';
		this.formNuevo=$('#formato13AGartCommand');
		this.codEmpresa=$('#codEmpresa');
		this.peridoDeclaracion=$('#peridoDeclaracion');
		this.tablaDeclaracion=$("#<portlet:namespace/>grid_formato_declaracion");
		this.paginadoDeclaracion='#<portlet:namespace/>pager_formato_declaracion';
		
		if(operacion=='CREATE'){
			
			formato13A.codEmpresa.change(function(){
				formato13A.cargarPeriodoDeclaracion();
			});
			formato13A.codEmpresa.trigger('change');
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
	               { name: 'descGrupoInformacion', index: 'anoEjecucion', width: 50},
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
	      			view = "<a href='#'><img border='0' title='View' src='/net-theme/images/img-net/file.png'  align='center' onclick=\"verFormato('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoEjecucion+"','"+ret.mesEjecucion+"','"+ret.etapa+"');\" /></a> ";
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
	buscar : function () {	

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
		jQuery.ajax({
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
	       colNames: ['Año / Mes Alta','Cód. Ubigeo','Loc.','ST-1','ST-2','ST-3','ST-4','ST-5','ST-6','ST-SER','Especial','Total','Zona Benef.','Sede Atención','Visualizar','Editar','Anular','',''],
	       colModel: [
					{ name: 'descEmpresa', index: 'descEmpresa', width: 50},
	               { name: 'anoPresentacion', index: 'anoPresentacion', width: 30 },   
	               { name: 'descMesPresentacion', index: 'descMesPresentacion', width: 30},
	               { name: 'etapa', index: 'etapa',width: 50},
	               { name: 'descGrupoInformacion', index: 'anoEjecucion', width: 50},
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
				pager: formato13A.paginadoDeclaracion,
			    viewrecords: true,
			   	caption: "Formatos declarados",
			    sortorder: "asc",	   	    	   	   
	       gridComplete: function(){
	      		var ids = formato13A.tablaDeclaracion.jqGrid('getDataIDs');
	      		for(var i=0;i < ids.length;i++){
	      			var cl = ids[i];
	      			var ret = formato13A.tablaDeclaracion.jqGrid('getRowData',cl);           
	      			view = "<a href='#'><img border='0' title='View' src='/net-theme/images/img-net/file.png'  align='center' onclick=\"verFormato('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoEjecucion+"','"+ret.mesEjecucion+"','"+ret.etapa+"');\" /></a> ";
	      			edit = "<a href='#'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png'  align='center' onclick=\"editarFormato('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoEjecucion+"','"+ret.mesEjecucion+"','"+ret.etapa+"');\" /></a> ";
	      			elem = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"confirmarEliminar('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoEjecucion+"','"+ret.mesEjecucion+"','"+ret.etapa+"');\" /></a> ";              			
	      			formato13A.tablaDeclaracion.jqGrid('setRowData',ids[i],{view:view});
	      			formato13A.tablaDeclaracion.jqGrid('setRowData',ids[i],{edit:edit});
	      			formato13A.tablaDeclaracion.jqGrid('setRowData',ids[i],{elim:elem});
	      		}
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
	}
};
</script>