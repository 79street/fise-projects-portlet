<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@page import="com.liferay.portal.kernel.util.PrefsPropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects />

<script type="text/javascript">

var tipoPersonalVar= {
		
	    //decalaracion de los elementos del html	
		formCommand : null,

		//divs
		divBuscar:null,	
		divNuevo:null,			
		
		
		//dialogos	
		dialogMessage:null,//para guardar y actualizar
		dialogMessageContent:null,//para mostrar mensajes al guardar y actualizar 
		dialogConfirm:null,//para eliminar registro
		dialogConfirmContent:null,//para mostrar la confirmacion al eliminar el registro
		
		dialogValidacion:null,
		dialogValidacionContent:null,
		dialogError:null,
		dialogErrorContent:null,
		dialogInfo:null,
		dialogInfoContent:null,
		
		//mensajes
		mensajeCargando:null,
		mensajeObteniendoDatos:null,
		mensajeEliminando:null,
		mensajeGuardando:null,	
		mensajeActualizando:null,	
		
		//urls
		urlBusqueda: null,		
		urlGrabar:null,
		urlActualizar:null,
		urlEditarView:null,		
	    urlEliminar:null,
	    urlNuevo:null,
		
		//botones		
		botonBuscar:null,
		botonNuevo:null,
		botonRegresar:null,		
		botonGrabar:null,
		botonActualizar:null,			
		
		//varibales de busqueda
		i_idBusq:null,
		i_descripcionBusq:null,		
		
		//variables para nuevo registro
		f_id:null,
		f_descripcion:null,		
		//grillas
		tablaResultados:null,
		paginadoResultados:null,	
		
		
		
		init : function() {
			
			this.formCommand=$('#tipoPersonalBean');
			
			//divs
			this.divBuscar=$("#<portlet:namespace/>div_buscar");
			this.divNuevo=$("#<portlet:namespace/>div_nuevo");			
			
			
			//dialogos		
			this.dialogMessage=$("#<portlet:namespace/>dialog-message-grabar");//para guardar y actualizar
			this.dialogMessageContent=$("#<portlet:namespace/>dialog-message-content-grabar");//para guardar y actualizar
			this.dialogConfirm=$("#<portlet:namespace/>dialog-confirm");//para eliminar
			this.dialogConfirmContent=$("#<portlet:namespace/>dialog-confirm-content");//para eliminar
			
			this.dialogValidacion=$("#<portlet:namespace/>dialog-alert");	
			this.dialogValidacionContent=$("#<portlet:namespace/>dialog-alert-content");
			this.dialogError=$("#<portlet:namespace/>dialog-error");
			this.dialogErrorContent=$("#<portlet:namespace/>dialog-error-content");	
            this.dialogInfo=$("#<portlet:namespace/>dialog-info");
			this.dialogInfoContent=$("#<portlet:namespace/>dialog-info-content");				
			
			//mensajes			
			this.mensajeCargando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Cargando </h3>';
			this.mensajeObteniendoDatos='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Obteniendo Datos </h3>';
			this.mensajeEliminando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Eliminando </h3>';
			this.mensajeGuardando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Guardando Datos </h3>';
			this.mensajeActualizando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Actualizando Datos </h3>';
			
			
			//urls
			this.urlBusqueda='<portlet:resourceURL id="busquedaTipoPersonal" />';			
			this.urlGrabar='<portlet:resourceURL id="grabarTipoPersonal" />';			
			this.urlActualizar='<portlet:resourceURL id="actualizarTipoPersonal" />';
			this.urlEditarView='<portlet:resourceURL id="editarViewTipoPersonal" />';			
			this.urlEliminar='<portlet:resourceURL id="eliminarTipoPersonal" />';
			this.urlNuevo='<portlet:resourceURL id="nuevoRegistroTipoPersonal" />';
			
			
			
			//botones
			this.botonBuscar=$("#<portlet:namespace/>btnBuscarTipoPersonal");
			this.botonNuevo=$("#<portlet:namespace/>btnNuevoTipoPersonal");
			this.botonRegresar=$("#<portlet:namespace/>btnRegresarTipoPersonal");			
			this.botonGrabar=$("#<portlet:namespace/>btnGuardarTipoPersonal");
			this.botonActualizar=$("#<portlet:namespace/>btnActualizarTipoPersonal");		
			
			//variables de busqueda
			this.i_idBusq=$('#idBusq');
			this.i_descripcionBusq=$('#descripcionBusq');
			
			//VARIBALES PARA NUEVO REGISTRO	
			this.f_id=$('#id');			
			this.f_descripcion=$('#descripcion');
			
			//grillas			
			this.tablaResultados=$("#<portlet:namespace/>grid_resultado_busqueda");
			this.paginadoResultados='#<portlet:namespace/>paginador_resultado_busqueda';
			this.buildGrids();	//cargar el modelo de la grilla	
			
			
			
			//llamado a la funciones de cada boton
			tipoPersonalVar.botonBuscar.click(function() {
				tipoPersonalVar.buscarTipoPersonal();
			});			
			
			tipoPersonalVar.botonNuevo.click(function() {
				tipoPersonalVar.<portlet:namespace/>nuevoTipoPersonal();
		    });
			
			tipoPersonalVar.botonGrabar.click(function() {
				tipoPersonalVar.<portlet:namespace/>guardarTipoPersonal();
			});
			
			tipoPersonalVar.botonActualizar.click(function() {
				tipoPersonalVar.<portlet:namespace/>actualizarTipoPersonal();
			});		
			
			
		    tipoPersonalVar.botonRegresar.click(function() {
		    	tipoPersonalVar.<portlet:namespace/>regresarTipoPersonal();
		    });			
		    
		    tipoPersonalVar.initDialogs();
		    
		    //eventos por defecto		   
			tipoPersonalVar.botonBuscar.trigger('click');
			tipoPersonalVar.initBlockUI();
		},
		
		//funcion para armar el modelo de la grilla para el resultado
		buildGrids : function () {	
			tipoPersonalVar.tablaResultados.jqGrid({
			   datatype: "local",
		       colNames: ['Id Tipo Personal','Descripción','Ver','Editar','Eliminar'],
		       colModel: [
                       { name: 'idTipPersonal', index: 'idTipPersonal', width: 20,align:'center'},
					   { name: 'descripcion', index: 'descripcion', width: 80},					  
		               { name: 'view', index: 'view', width: 15,align:'center' },
		               { name: 'edit', index: 'edit', width: 15,align:'center' },
		               { name: 'elim', index: 'elim', width: 15,align:'center' }  
		               
			   	    ],
			   	 multiselect: false,
					rowNum:10,
				   	rowList:[10,20,50],
					height: 225,
				   	autowidth: true,
					rownumbers: true,
					shrinkToFit:true,
					pager: tipoPersonalVar.paginadoResultados,
				    viewrecords: true,
				   	caption: "Resultado(s) de la búsqueda",
				    sortorder: "asc",	   	    	   	   
		       gridComplete: function(){
		      		var ids = tipoPersonalVar.tablaResultados.jqGrid('getDataIDs');
		      		for(var i=0;i < ids.length;i++){
		      			var cl = ids[i];
		      			var ret = tipoPersonalVar.tablaResultados.jqGrid('getRowData',cl);           
		      			view = "<a href='#'><img border='0' title='Ver' src='/net-theme/images/img-net/file.png'  align='center' onclick=\"tipoPersonalVar.verTipoPersonal('"+ret.idTipPersonal+"');\" /></a> ";
		      			edit = "<a href='#'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png'  align='center' onclick=\"tipoPersonalVar.editarTipoPersonal('"+ret.idTipPersonal+"');\" /></a> ";
		      			elim = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"tipoPersonalVar.confirmarEliminarTipoPersonal('"+ret.idTipPersonal+"');\" /></a> ";              			
		      			tipoPersonalVar.tablaResultados.jqGrid('setRowData',ids[i],{view:view});
		      			tipoPersonalVar.tablaResultados.jqGrid('setRowData',ids[i],{edit:edit});
		      			tipoPersonalVar.tablaResultados.jqGrid('setRowData',ids[i],{elim:elim});
		      		}
		      }
		  	});
			tipoPersonalVar.tablaResultados.jqGrid('navGrid',tipoPersonalVar.paginadoResultados,{add:false,edit:false,del:false,search: false,refresh: false});	
			tipoPersonalVar.tablaResultados.jqGrid('navButtonAdd',tipoPersonalVar.paginadoResultados,{
			       caption:"Exportar a Excel",
			       buttonicon: "ui-icon-bookmark",
			       onClickButton : function () {
			    	   var ids = tipoPersonalVar.tablaResultados.jqGrid('getDataIDs');
				       if(ids!=0){
				    		  location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';   
				       }else{
				    	var addhtmInfo='No existe información para exportar a Excel';				
				    	tipoPersonalVar.dialogInfoContent.html(addhtmInfo);
				    	tipoPersonalVar.dialogInfo.dialog("open");
				       }  		       
			       } 
			});  
		},
		//funcion para buscar
		buscarTipoPersonal : function () {		
			tipoPersonalVar.blockUI();
			jQuery.ajax({			
					url: tipoPersonalVar.urlBusqueda+'&'+tipoPersonalVar.formCommand.serialize(),
					type: 'post',
					dataType: 'json',				
					success: function(gridData) {					
							tipoPersonalVar.tablaResultados.clearGridData(true);
							tipoPersonalVar.tablaResultados.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
							tipoPersonalVar.tablaResultados[0].refreshIndex();
							tipoPersonalVar.initBlockUI();
					},error : function(){
						var addhtmError='Error de conexión.';					
						tipoPersonalVar.dialogErrorContent.html(addhtmError);
						tipoPersonalVar.dialogError.dialog("open");
						tipoPersonalVar.initBlockUI();
					}
				});			
		},		
		
		//funcion para nuevo registro
		<portlet:namespace/>nuevoTipoPersonal : function(){	
			jQuery.ajax({			
				url: tipoPersonalVar.urlNuevo+'&'+tipoPersonalVar.formCommand.serialize(),
				type: 'post',
				dataType: 'json',				
				success: function(data) {					
					tipoPersonalVar.f_id.val(data.id);
					tipoPersonalVar.f_descripcion.val('');			
					tipoPersonalVar.f_id.attr("disabled",true);  	
		        	tipoPersonalVar.f_descripcion.removeAttr("disabled");	
					
					tipoPersonalVar.divNuevo.show();
					tipoPersonalVar.divBuscar.hide();		
								
					console.debug("boton nuevo registro:  ");
					
					$('#<portlet:namespace/>btnGuardarTipoPersonal').css('display','block');
					$('#<portlet:namespace/>btnActualizarTipoPersonal').css('display','none');	
					
				},error : function(){
					var addhtmError='Error de conexión.';					
					tipoPersonalVar.dialogErrorContent.html(addhtmError);
					tipoPersonalVar.dialogError.dialog("open");
					tipoPersonalVar.initBlockUI();
				}
			});			
		},
		
		//Function para Visualizar los datos del formulario		
		verTipoPersonal : function(id_TipoPersonal){	
			$.blockUI({ message: tipoPersonalVar.mensajeObteniendoDatos});
			jQuery.ajax({
					url: tipoPersonalVar.urlEditarView+'&'+tipoPersonalVar.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {						  
					      <portlet:namespace />id: id_TipoPersonal,
					      <portlet:namespace />codigo: id_TipoPersonal
						},
					success: function(data) {
					    if (data != null){															
					    	tipoPersonalVar.divNuevo.show();
					    	tipoPersonalVar.divBuscar.hide();	
					    	
					    	tipoPersonalVar.llenarDatosEditar(data);
					    	
					    	tipoPersonalVar.initBlockUI();				    	
					    			
							tipoPersonalVar.f_id.attr("disabled",true);				        	
				        	tipoPersonalVar.f_descripcion.attr("disabled",true);
				        	
					    	
					    	$('#<portlet:namespace/>btnGuardarTipoPersonal').css('display','none');
					    	$('#<portlet:namespace/>btnActualizarTipoPersonal').css('display','none');
					    							
				        }						
						else{							
							var addhtmError='Error al visualizar los datos del registro seleccionado.';					
							tipoPersonalVar.dialogErrorContent.html(addhtmError);
							tipoPersonalVar.dialogError.dialog("open");	
							tipoPersonalVar.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						tipoPersonalVar.dialogErrorContent.html(addhtmError);
						tipoPersonalVar.dialogError.dialog("open");
						tipoPersonalVar.initBlockUI();
					}
			});	
		},
		//Function para editar los datos del formulario
		editarTipoPersonal : function(id_tipoPersonal){	
			    console.debug("entrando a editar ");		
				$.blockUI({ message: tipoPersonalVar.mensajeObteniendoDatos });			 
				jQuery.ajax({
						url: tipoPersonalVar.urlEditarView+'&'+tipoPersonalVar.formCommand.serialize(),
						type: 'post',
						dataType: 'json',
						data: {							
						   <portlet:namespace />id: id_tipoPersonal						   				  
						},
						success: function(data) {				
							if (data != null){															
								tipoPersonalVar.divNuevo.show();
								tipoPersonalVar.divBuscar.hide();	
								
								tipoPersonalVar.llenarDatosEditar(data);
								
								tipoPersonalVar.initBlockUI();					
											
								tipoPersonalVar.f_id.attr("disabled",true);			        	
					        	tipoPersonalVar.f_descripcion.removeAttr("disabled");
								
								$('#<portlet:namespace/>btnGuardarTipoPersonal').css('display','none');
								$('#<portlet:namespace/>btnActualizarTipoPersonal').css('display','block');	
								//ESTILOS								
					         }
							else{								
								var addhtmError='Error al recuperar los datos del registro seleccionado.';					
								tipoPersonalVar.dialogErrorContent.html(addhtmError);
								tipoPersonalVar.dialogError.dialog("open");	
								tipoPersonalVar.initBlockUI();
							}
						},error : function(){
							var addhtmError='Error de conexión.';					
							tipoPersonalVar.dialogErrorContent.html(addhtmError);
							tipoPersonalVar.dialogError.dialog("open");
							tipoPersonalVar.initBlockUI();
						}
				});		
		},
		//funcion  para llenar los campos para editar
		llenarDatosEditar : function(bean){	
			tipoPersonalVar.f_id.val(bean.id);
			tipoPersonalVar.f_descripcion.val(bean.descripcion);  	
		},
		/**Function para confirmar si quiere eliminar el registro o no*/
		confirmarEliminarTipoPersonal : function(idTipoPersonal){
			console.debug("entranado a eliminar confirmar:  "+idTipoPersonal);
			var addhtml='¿Está seguro que desea eliminar el registro seleccionado?';
			tipoPersonalVar.dialogConfirmContent.html(addhtml);
			tipoPersonalVar.dialogConfirm.dialog("open");	
			id_TipoPersonal=idTipoPersonal;
		},
		/**Function para  eliminar el registro una vez hecho la confirmacion*/
		eliminarTipoPersonal : function(id_TipoPersonal){
			console.debug("entranado a eliminar:  "+id_TipoPersonal);
			$.blockUI({ message: tipoPersonalVar.mensajeEliminando });
			jQuery.ajax({
				url: tipoPersonalVar.urlEliminar+'&'+tipoPersonalVar.formCommand.serialize(),
				type: 'post',
				dataType: 'json',
				data: {				
				   <portlet:namespace />id: id_TipoPersonal				  
					},
				success: function(data) {
					if (data.resultado == "OK"){
						var addhtml2='El registro fue eliminado satisfactoriamente';					
						tipoPersonalVar.dialogMessageContent.html(addhtml2);
						tipoPersonalVar.dialogMessage.dialog("open");
						tipoPersonalVar.buscartipoPersonalVar();
						tipoPersonalVar.initBlockUI();
					}
					else{						
						var addhtmError='Error al eliminar el registro.';					
						tipoPersonalVar.dialogErrorContent.html(addhtmError);
						tipoPersonalVar.dialogError.dialog("open");	
						tipoPersonalVar.initBlockUI();
					}
				},error : function(){
					var addhtmError='Error de conexión.';					
					tipoPersonalVar.dialogErrorContent.html(addhtmError);
					tipoPersonalVar.dialogError.dialog("open");
					tipoPersonalVar.initBlockUI();
				}
			});
		},
		
		//Funcion para Grabar nuevo registro
		<portlet:namespace/>guardarTipoPersonal: function(){
			if (tipoPersonalVar.validarFormulario()){
				$.blockUI({ message: tipoPersonalVar.mensajeGuardando });
				 jQuery.ajax({
					 url: tipoPersonalVar.urlGrabar+'&'+tipoPersonalVar.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {											
						<portlet:namespace />id: tipoPersonalVar.f_id.val(),
						<portlet:namespace />descripcion: tipoPersonalVar.f_descripcion.val() 																
						},
					success: function(data) {			
						if (data.resultado == "OK"){				
							var addhtml2='El Tipo de Personal se guardó satisfactoriamente';
							
							tipoPersonalVar.dialogMessageContent.html(addhtml2);
							tipoPersonalVar.dialogMessage.dialog("open");							
							tipoPersonalVar.initBlockUI();						
										
							tipoPersonalVar.f_id.attr("disabled",true);			        	
				        	tipoPersonalVar.f_descripcion.removeAttr("disabled");
							
							$('#<portlet:namespace/>btnGuardarTipoPersonal').css('display','none');
							$('#<portlet:namespace/>btnActualizarTipoPersonal').css('display','block');
							
						}else if(data.resultado == "Error"){							
							var addhtmError='Se produjo un error al guardar el Tipo de Personal.';					
							tipoPersonalVar.dialogErrorContent.html(addhtmError);
							tipoPersonalVar.dialogError.dialog("open");						
							tipoPersonalVar.initBlockUI();
						}else if(data.resultado=="Duplicado"){
							var addhtmlInfo='Ya existe registrado un Tipo de Personal con este Id.';
							tipoPersonalVar.dialogInfoContent.html(addhtmlInfo);
							tipoPersonalVar.dialogInfo.dialog("open");						
							tipoPersonalVar.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						tipoPersonalVar.dialogErrorContent.html(addhtmError);
						tipoPersonalVar.dialogError.dialog("open");
						tipoPersonalVar.initBlockUI();
					}
				});			
			}
		},
		//Funcion para actualizar un registro
		<portlet:namespace/>actualizarTipoPersonal : function(){
			if (tipoPersonalVar.validarFormulario()){
				$.blockUI({ message: tipoPersonalVar.mensajeActualizando });
				 jQuery.ajax({
					 url: tipoPersonalVar.urlActualizar+'&'+tipoPersonalVar.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {
						<portlet:namespace />id: tipoPersonalVar.f_id.val(),
						<portlet:namespace />descripcion: tipoPersonalVar.f_descripcion.val() 	
						},
					success: function(data) {			
						if (data.resultado == "OK"){				
							var addhtml2='El Tipo de Personal se actualizó satisfactoriamente';
							tipoPersonalVar.dialogMessageContent.html(addhtml2);
							tipoPersonalVar.dialogMessage.dialog("open");						
							tipoPersonalVar.initBlockUI();								
						}else if(data.resultado == "Error"){						
							var addhtmError='Se produjo un error al actualizar el Tipo de Personal.';					
							tipoPersonalVar.dialogErrorContent.html(addhtmError);
							tipoPersonalVar.dialogError.dialog("open");				
							tipoPersonalVar.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						tipoPersonalVar.dialogErrorContent.html(addhtmError);
						tipoPersonalVar.dialogError.dialog("open");
						tipoPersonalVar.initBlockUI();
					}
				});						
			}
		},
		//funcion para validar ingreso de datos
		validarFormulario : function() {
			console.debug("tamaño de descripcion:  "+tipoPersonalVar.f_descripcion.val().length);
			if(tipoPersonalVar.f_id.val().length == ''){				
				var addhtmAlert='Debe ingresar el id de Tipo de Personal.';					
				tipoPersonalVar.dialogValidacionContent.html(addhtmAlert);
				tipoPersonalVar.dialogValidacion.dialog("open");
				//tipoPersonalVar.f_id.focus();
				cod_focus=tipoPersonalVar.f_id;
			  	return false; 
			}else if(tipoPersonalVar.f_descripcion.val().length == ''){				
				var addhtmAlert='Debe ingresar descripción.';					
				tipoPersonalVar.dialogValidacionContent.html(addhtmAlert);
				tipoPersonalVar.dialogValidacion.dialog("open");
				//tipoPersonalVar.f_descripcion.focus();
				cod_focus=tipoPersonalVar.f_descripcion;
			  	return false; 
			}else if(!validarLetra(tipoPersonalVar.f_descripcion.val())){				
				var addhtmAlert='Debe ingresar una Descripción válida.';					
				tipoPersonalVar.dialogValidacionContent.html(addhtmAlert);
				tipoPersonalVar.dialogValidacion.dialog("open");
				//tipoPersonalVar.f_descripcion.focus();
				cod_focus=tipoPersonalVar.f_descripcion;
			  	return false; 
			}else if(tipoPersonalVar.f_descripcion.val().length > 499){				
				var addhtmAlert='La  descripción acepta como máximo 500 caracteres.';					
				tipoPersonalVar.dialogValidacionContent.html(addhtmAlert);
				tipoPersonalVar.dialogValidacion.dialog("open");
				//tipoPersonalVar.f_descripcion.focus();
				cod_focus=tipoPersonalVar.f_descripcion;
			  	return false; 
			}else{
				return true;
			}		
		},
		
		//funcion para regresar
		<portlet:namespace/>regresarTipoPersonal : function(){			
			tipoPersonalVar.divNuevo.hide();
			tipoPersonalVar.divBuscar.show();
					
			tipoPersonalVar.botonBuscar.trigger('click');
		},
		
		ponerFocus : function(cadena){		
			cadena.focus();
		},	
		
		//DIALOGOS
		initDialogs : function(){	
			tipoPersonalVar.dialogMessage.dialog({
				modal: true,
				autoOpen: false,
				width: 450,	
				buttons: {
					Ok: function() {
						$( this ).dialog("close");
					}
				}
			});
			//dialogo para eliminar registro
			tipoPersonalVar.dialogConfirm.dialog({
				modal: true,
				height: 200,
				width: 450,			
				autoOpen: false,
				buttons: {
					"Si": function() {
						tipoPersonalVar.eliminarTipoPersonal(id_TipoPersonal);
						$( this ).dialog("close");
					},
					"No": function() {				
						$( this ).dialog("close");
					}
				}
			});	
			
			tipoPersonalVar.dialogValidacion.dialog({
				modal: true,
				autoOpen: false,
				width: 450,		
				buttons: {
					Aceptar: function() {
						tipoPersonalVar.ponerFocus(cod_focus);
						$( this ).dialog("close");
					}
				}
			});
			
			tipoPersonalVar.dialogError.dialog({
				modal: true,
				autoOpen: false,
				width: 450,		
				buttons: {
					Aceptar: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			tipoPersonalVar.dialogInfo.dialog({
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