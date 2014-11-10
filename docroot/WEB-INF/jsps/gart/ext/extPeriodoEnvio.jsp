<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects />
<script type="text/javascript">
var periodoEnvio= {
	tablaResultados:null,
	paginadoResultados:null,
	urlBusqueda: null,
	botonBuscar:null,
	formBusqueda: null,
	init : function(){
		this.tablaResultados=$("#<portlet:namespace/>grid_formato");
		this.paginadoResultados='#<portlet:namespace/>pager_formato';
		this.urlBusqueda='<portlet:resourceURL id="busqueda" />';
		this.botonBuscar=$("#<portlet:namespace/>buscarPeriodoEnvio");
		this.formBusqueda=$('#periodoEnvioCommand');
		periodoEnvio.botonBuscar.click(function() {
			periodoEnvio.buscar();
		});
		periodoEnvio.botonBuscar.trigger('click');
		
	},
	buildGrids : function () {	
		periodoEnvio.tablaResultados.jqGrid({
		   datatype: "local",
	       colNames: ['Secuencia','Empresa','Formato','Año Pres.','Mes Pres.','Estado','Visualizar','Editar','Eliminar'],
	       colModel: [
                    { name: 'secuencia', index: 'secuencia', width: 50},
					{ name: 'codEmpresa', index: 'codEmpresa', width: 50},
	               { name: 'anoPresentacion', index: 'anoPresentacion', width: 30 },   
	               { name: 'mesPresentacion', index: 'mesPresentacion', width: 30},
	               { name: 'codEmpresa', index: 'codEmpresa', width: 50,align:'center'},
	               { name: 'view', index: 'view', width: 20,align:'center' },
	               { name: 'edit', index: 'edit', width: 20,align:'center' },
	               { name: 'elim', index: 'elim', width: 20,align:'center' }
	               
	               
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
	      		var ids = periodoEnvio.tablaResultados.jqGrid('getDataIDs');
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
		periodoEnvio.tablaResultados.jqGrid('navGrid',periodoEnvio.paginadoResultados,{add:false,edit:false,del:false,search: false,refresh: false});	
		periodoEnvio.tablaResultados.jqGrid('navButtonAdd',periodoEnvio.paginadoResultados,{
		       caption:"Exportar a Excel",
		       buttonicon: "ui-icon-bookmark",
		       onClickButton : function () {
		           location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';
		       } 
		}); 
	},
	buscar : function () {	

			$.ajax({			
					url: periodoEnvio.urlBusqueda+'&'+periodoEnvio.formBusqueda.serialize(),
					type: 'post',
					dataType: 'json',
					beforeSend:function(){
						periodoEnvio.blockUI();
					},				
					success: function(gridData) {					
						periodoEnvio.tablaResultados.clearGridData(true);
						periodoEnvio.tablaResultados.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
						periodoEnvio.tablaResultados[0].refreshIndex();
						periodoEnvio.unblockUI();
					},error : function(){
						alert("Error de conexión.");
						periodoEnvio.unblockUI();
					}
			});


	},
	blockUI : function(){
		$.blockUI({ message: '<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Cargando </h3>' });
	},
	unblockUI : function(){
		$.unblockUI();
	}
};
</script>