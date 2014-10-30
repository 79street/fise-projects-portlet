<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects />

<script type="text/javascript">

var formato14C= {
		tablaResultados:null,
		paginadoResultados:null,
		urlBusqueda: null,
		botonBuscar:null,
		formBusqueda: null,
		init : function(){
			this.buildGrids();
			this.tablaResultados=$("#<portlet:namespace/>grid_formato");
			this.paginadoResultados='#<portlet:namespace/>pager_formato';
			this.urlBusqueda='<portlet:resourceURL id="busqueda" />';
			this.botonBuscar=$("#<portlet:namespace/>buscarFormato14C");
			this.formBusqueda=$('#formato14CBean');
			//evento para buscar
			formato14C.botonBuscar.click(function() {
				formato14C.buscar();
			});		
			//busqueda por defecto al momento de cargar el portlet busca
			//formato14C.botonBuscar.trigger('click');
			
		},
		buildGrids : function () {	
			formato14C.tablaResultados.jqGrid({
			   datatype: "local",
		       colNames: ['Empresa','Año Pres.','Mes Pres.','Año Inicio Vigencia.','Año Fin Vigencia.','Grupo Inf','Visualizar','Editar','Anular','Estado','',''],
		       colModel: [
					 { name: 'descEmpresa', index: 'descEmpresa', width: 50},
		               { name: 'anoPresentacion', index: 'anoPresentacion', width: 30 },   
		               { name: 'descMesPresentacion', index: 'descMesPresentacion', width: 30},
		               { name: 'anoIniVigencia', index: 'anoIniVigencia', width: 30 },   //anoIniVigencia id de la clase json
		               { name: 'anoFinVigencia', index: 'anoFinVigencia', width: 30},
		               { name: 'grupoInfo', index: 'grupoInfo', width: 50},
		               { name: 'view', index: 'view', width: 20,align:'center' },
		               { name: 'edit', index: 'edit', width: 20,align:'center' },
		               { name: 'elim', index: 'elim', width: 20,align:'center' },
		               { name: 'estado', index: 'estado', width: 50,align:'center'},
		               { name: 'codEmpresa', index: 'codEmpresa', hidden: true},		              
		               { name: 'etapa', index: 'etapa', hidden: true}
			   	    ],
			   	 multiselect: false,
					rowNum:10,
				   	rowList:[10,20,50],
					height: 200,
				   	autowidth: true,
					rownumbers: true,
					shrinkToFit:true,
					pager: formato14C.paginadoResultados,
				    viewrecords: true,
				   	caption: "Formatos",//titulo de la grilla
				    sortorder: "asc",	   	    	   	   
		       gridComplete: function(){
		      		var ids = formato14C.tablaResultados.jqGrid('getDataIDs');
		      		for(var i=0;i < ids.length;i++){
		      			var cl = ids[i];
		      			var ret = formato14C.tablaResultados.jqGrid('getRowData',cl);           
		      			view = "<a href='#'><img border='0' title='View' src='/net-theme/images/img-net/file.png'  align='center' onclick=\"verFormato('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoIniVigencia+"','"+ret.anoFinVigencia+"','"+ret.etapa+"');\" /></a> ";//son los id de la clase json
		      			edit = "<a href='#'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png'  align='center' onclick=\"editarFormato('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoIniVigencia+"','"+ret.anoFinVigencia+"','"+ret.etapa+"');\" /></a> ";
		      			elem = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"confirmarEliminar('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoIniVigencia+"','"+ret.anoFinVigencia+"','"+ret.etapa+"');\" /></a> ";              			
		      			formato14C.tablaResultados.jqGrid('setRowData',ids[i],{view:view});
		      			formato14C.tablaResultados.jqGrid('setRowData',ids[i],{edit:edit});
		      			formato14C.tablaResultados.jqGrid('setRowData',ids[i],{elim:elem});
		      		}
		      }
		  	});
			//navGrid son variables del jquery
			formato14C.tablaResultados.jqGrid('navGrid',formato14C.paginadoResultados,{add:false,edit:false,del:false,search: false,refresh: false});	
			formato14C.tablaResultados.jqGrid('navButtonAdd',formato14C.paginadoResultados,{
			       caption:"Exportar a Excel",
			       buttonicon: "ui-icon-bookmark",
			       onClickButton : function () {
			           location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';
			       } 
			}); 
		},
		buscar : function () {	
				$.ajax({			
						url: formato14C.urlBusqueda+'&'+formato14C.formBusqueda.serialize(),
						type: 'post',
						dataType: 'json',
						beforeSend:function(){
							formato14C.blockUI();
						},				
						success: function(gridData) {					
							formato14C.tablaResultados.clearGridData(true);
							formato14C.tablaResultados.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
							formato14C.tablaResultados[0].refreshIndex();
							formato14C.unblockUI();
						},error : function(){
							alert("Error de conexión.");
							formato14C.unblockUI();
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