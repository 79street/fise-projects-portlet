<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@page import="com.liferay.portal.kernel.util.PrefsPropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects />

<script type="text/javascript">

var fiseTipoGastoVar= {
		
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
			
			this.formCommand=$('#tipoGastoBean');
			
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
			this.urlBusqueda='<portlet:resourceURL id="busquedaObservaciones" />';			
			this.urlGrabar='<portlet:resourceURL id="grabarObservacion" />';			
			this.urlActualizar='<portlet:resourceURL id="actualizarTipoGasto" />';
			this.urlEditarView='<portlet:resourceURL id="editarViewObservacion" />';			
			this.urlEliminar='<portlet:resourceURL id="eliminarObservacion" />';
			this.urlNuevo='<portlet:resourceURL id="nuevoRegistroObservacion" />';
			
			
			
			//botones
			this.botonBuscar=$("#<portlet:namespace/>btnBuscarTipogasto");
			this.botonNuevo=$("#<portlet:namespace/>btnNuevoTipoGasto");
			this.botonRegresar=$("#<portlet:namespace/>btnRegresarTipoGasto");			
			this.botonGrabar=$("#<portlet:namespace/>btnGuardarTipoGasto");
			this.botonActualizar=$("#<portlet:namespace/>btnActualizarTipoGasto");		
			
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
			fiseTipoGastoVar.botonBuscar.click(function() {
				fiseTipoGastoVar.buscarfiseTipoGastoVar();
			});			
			
			fiseTipoGastoVar.botonNuevo.click(function() {
				fiseTipoGastoVar.<portlet:namespace/>nuevofiseTipoGastoVar();
		    });
			
			fiseTipoGastoVar.botonGrabar.click(function() {
				fiseTipoGastoVar.<portlet:namespace/>guardarfiseTipoGastoVar();
			});
			
			fiseTipoGastoVar.botonActualizar.click(function() {
				fiseTipoGastoVar.<portlet:namespace/>actualizarfiseTipoGastoVar();
			});		
			
			
		    fiseTipoGastoVar.botonRegresar.click(function() {
		    	fiseTipoGastoVar.<portlet:namespace/>regresarfiseTipoGastoVar();
		    });			
		    
		    fiseTipoGastoVar.initDialogs();
		    
		    //eventos por defecto		   
			fiseTipoGastoVar.botonBuscar.trigger('click');
			fiseTipoGastoVar.initBlockUI();
		},
		//funcion para armar el modelo de la grilla para el resultado
		buildGrids : function () {	
			fiseTipoGastoVar.tablaResultados.jqGrid({
			   datatype: "local",
		       colNames: ['Id Observación','Descripción de Observación','Ver','Editar'],
		       colModel: [
                       { name: 'idObservacion', index: 'idObservacion', width: 20},
					   { name: 'descripcion', index: 'descripcion', width: 80},					  
		               { name: 'view', index: 'view', width: 20,align:'center' },
		               { name: 'edit', index: 'edit', width: 20,align:'center' }
		              /*  { name: 'elim', index: 'elim', width: 20,align:'center' } */  
		               
			   	    ],
			   	 multiselect: false,
					rowNum:10,
				   	rowList:[10,20,50],
					height: 225,
				   	autowidth: true,
					rownumbers: true,
					shrinkToFit:true,
					pager: fiseTipoGastoVar.paginadoResultados,
				    viewrecords: true,
				   	caption: "Resultado(s) de la búsqueda",
				    sortorder: "asc",	   	    	   	   
		       gridComplete: function(){
		      		var ids = fiseTipoGastoVar.tablaResultados.jqGrid('getDataIDs');
		      		for(var i=0;i < ids.length;i++){
		      			var cl = ids[i];
		      			var ret = fiseTipoGastoVar.tablaResultados.jqGrid('getRowData',cl);           
		      			view = "<a href='#'><img border='0' title='Ver' src='/net-theme/images/img-net/file.png'  align='center' onclick=\"fiseTipoGastoVar.verfiseTipoGastoVar('"+ret.idObservacion+"');\" /></a> ";
		      			edit = "<a href='#'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png'  align='center' onclick=\"fiseTipoGastoVar.editarfiseTipoGastoVar('"+ret.idObservacion+"');\" /></a> ";
		      			/* elim = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"fiseTipoGastoVar.confirmarEliminarfiseTipoGastoVar('"+ret.idObservacion+"');\" /></a> "; */              			
		      			fiseTipoGastoVar.tablaResultados.jqGrid('setRowData',ids[i],{view:view});
		      			fiseTipoGastoVar.tablaResultados.jqGrid('setRowData',ids[i],{edit:edit});
		      			/* fiseTipoGastoVar.tablaResultados.jqGrid('setRowData',ids[i],{elim:elim}); */
		      		}
		      }
		  	});
			fiseTipoGastoVar.tablaResultados.jqGrid('navGrid',fiseTipoGastoVar.paginadoResultados,{add:false,edit:false,del:false,search: false,refresh: false});	
			fiseTipoGastoVar.tablaResultados.jqGrid('navButtonAdd',fiseTipoGastoVar.paginadoResultados,{
			       caption:"Exportar a Excel",
			       buttonicon: "ui-icon-bookmark",
			       onClickButton : function () {
			    	   var ids = fiseTipoGastoVar.tablaResultados.jqGrid('getDataIDs');
				       if(ids!=0){
				    		  location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';   
				       }else{
				    	var addhtmInfo='No existe información para exportar a Excel';				
				    	fiseTipoGastoVar.dialogInfoContent.html(addhtmInfo);
				    	fiseTipoGastoVar.dialogInfo.dialog("open");
				       }  		       
			       } 
			});  
		},
		//funcion para buscar
		buscarfiseTipoGastoVar : function () {		
			fiseTipoGastoVar.blockUI();
			jQuery.ajax({			
					url: fiseTipoGastoVar.urlBusqueda+'&'+fiseTipoGastoVar.formCommand.serialize(),
					type: 'post',
					dataType: 'json',				
					success: function(gridData) {					
							fiseTipoGastoVar.tablaResultados.clearGridData(true);
							fiseTipoGastoVar.tablaResultados.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
							fiseTipoGastoVar.tablaResultados[0].refreshIndex();
							fiseTipoGastoVar.initBlockUI();
					},error : function(){
						var addhtmError='Error de conexión.';					
						fiseTipoGastoVar.dialogErrorContent.html(addhtmError);
						fiseTipoGastoVar.dialogError.dialog("open");
						fiseTipoGastoVar.initBlockUI();
					}
				});			
		},		
		//funcion para nuevo registro
		<portlet:namespace/>nuevofiseTipoGastoVar : function(){	
			jQuery.ajax({			
				url: fiseTipoGastoVar.urlNuevo+'&'+fiseTipoGastoVar.formCommand.serialize(),
				type: 'post',
				dataType: 'json',				
				success: function(data) {					
					fiseTipoGastoVar.f_id.val(data.id);
					fiseTipoGastoVar.f_descripcion.val('');			
					fiseTipoGastoVar.f_id.attr("disabled",true);  	
		        	fiseTipoGastoVar.f_descripcion.removeAttr("disabled");	
					
					fiseTipoGastoVar.divNuevo.show();
					fiseTipoGastoVar.divBuscar.hide();		
								
					console.debug("boton nuevo registro:  ");
					
					$('#<portlet:namespace/>guardarfiseTipoGastoVar').css('display','block');
					$('#<portlet:namespace/>actualizarfiseTipoGastoVar').css('display','none');	
					
				},error : function(){
					var addhtmError='Error de conexión.';					
					fiseTipoGastoVar.dialogErrorContent.html(addhtmError);
					fiseTipoGastoVar.dialogError.dialog("open");
					fiseTipoGastoVar.initBlockUI();
				}
			});			
		},
		
		//Function para Visualizar los datos del formulario		
		verfiseTipoGastoVar : function(idObservacion){	
			$.blockUI({ message: fiseTipoGastoVar.mensajeObteniendoDatos});
			jQuery.ajax({
					url: fiseTipoGastoVar.urlEditarView+'&'+fiseTipoGastoVar.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {						  
					      <portlet:namespace />id: idObservacion					 
						},
					success: function(data) {
					    if (data != null){															
					    	fiseTipoGastoVar.divNuevo.show();
					    	fiseTipoGastoVar.divBuscar.hide();	
					    	
					    	fiseTipoGastoVar.llenarDatosEditar(data);
					    	
					    	fiseTipoGastoVar.initBlockUI();				    	
					    			
							fiseTipoGastoVar.f_id.attr("disabled",true);				        	
				        	fiseTipoGastoVar.f_descripcion.attr("disabled",true);
				        	
					    	
					    	$('#<portlet:namespace/>guardarfiseTipoGastoVar').css('display','none');
					    	$('#<portlet:namespace/>actualizarfiseTipoGastoVar').css('display','none');
					    							
				        }						
						else{							
							var addhtmError='Error al visualizar los datos del registro seleccionado.';					
							fiseTipoGastoVar.dialogErrorContent.html(addhtmError);
							fiseTipoGastoVar.dialogError.dialog("open");	
							fiseTipoGastoVar.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						fiseTipoGastoVar.dialogErrorContent.html(addhtmError);
						fiseTipoGastoVar.dialogError.dialog("open");
						fiseTipoGastoVar.initBlockUI();
					}
			});	
		},
		//Function para editar los datos del formulario
		editarfiseTipoGastoVar : function(idObservacion){	
			    console.debug("entrando a editar ");		
				$.blockUI({ message: fiseTipoGastoVar.mensajeObteniendoDatos });			 
				jQuery.ajax({
						url: fiseTipoGastoVar.urlEditarView+'&'+fiseTipoGastoVar.formCommand.serialize(),
						type: 'post',
						dataType: 'json',
						data: {							
						   <portlet:namespace />id: idObservacion						   				  
						},
						success: function(data) {				
							if (data != null){															
								fiseTipoGastoVar.divNuevo.show();
								fiseTipoGastoVar.divBuscar.hide();	
								
								fiseTipoGastoVar.llenarDatosEditar(data);
								
								fiseTipoGastoVar.initBlockUI();					
											
								fiseTipoGastoVar.f_id.attr("disabled",true);			        	
					        	fiseTipoGastoVar.f_descripcion.removeAttr("disabled");
								
								$('#<portlet:namespace/>guardarfiseTipoGastoVar').css('display','none');
								$('#<portlet:namespace/>actualizarfiseTipoGastoVar').css('display','block');	
								//ESTILOS								
					         }
							else{								
								var addhtmError='Error al recuperar los datos del registro seleccionado.';					
								fiseTipoGastoVar.dialogErrorContent.html(addhtmError);
								fiseTipoGastoVar.dialogError.dialog("open");	
								fiseTipoGastoVar.initBlockUI();
							}
						},error : function(){
							var addhtmError='Error de conexión.';					
							fiseTipoGastoVar.dialogErrorContent.html(addhtmError);
							fiseTipoGastoVar.dialogError.dialog("open");
							fiseTipoGastoVar.initBlockUI();
						}
				});		
		},
		//funcion  para llenar los campos para editar
		llenarDatosEditar : function(bean){	
			fiseTipoGastoVar.f_id.val(bean.id);
			fiseTipoGastoVar.f_descripcion.val(bean.descripcion);  	
		},
		/**Function para confirmar si quiere eliminar el registro o no*/
		confirmarEliminarfiseTipoGastoVar : function(idObservacion){
			console.debug("entranado a eliminar confirmar:  "+idObservacion);
			var addhtml='¿Está seguro que desea eliminar el registro seleccionado?';
			fiseTipoGastoVar.dialogConfirmContent.html(addhtml);
			fiseTipoGastoVar.dialogConfirm.dialog("open");	
			id_observacion=idObservacion;
		},
		/**Function para  eliminar el registro una vez hecho la confirmacion*/
		eliminarfiseTipoGastoVar : function(id_observacion){
			console.debug("entranado a eliminar:  "+id_observacion);
			$.blockUI({ message: fiseTipoGastoVar.mensajeEliminando });
			jQuery.ajax({
				url: fiseTipoGastoVar.urlEliminar+'&'+fiseTipoGastoVar.formCommand.serialize(),
				type: 'post',
				dataType: 'json',
				data: {				
				   <portlet:namespace />id: id_observacion				  
					},
				success: function(data) {
					if (data.resultado == "OK"){
						var addhtml2='El registro fue eliminado satisfactoriamente';					
						fiseTipoGastoVar.dialogMessageContent.html(addhtml2);
						fiseTipoGastoVar.dialogMessage.dialog("open");
						fiseTipoGastoVar.buscarfiseTipoGastoVar();
						fiseTipoGastoVar.initBlockUI();
					}
					else{						
						var addhtmError='Error al eliminar el registro.';					
						fiseTipoGastoVar.dialogErrorContent.html(addhtmError);
						fiseTipoGastoVar.dialogError.dialog("open");	
						fiseTipoGastoVar.initBlockUI();
					}
				},error : function(){
					var addhtmError='Error de conexión.';					
					fiseTipoGastoVar.dialogErrorContent.html(addhtmError);
					fiseTipoGastoVar.dialogError.dialog("open");
					fiseTipoGastoVar.initBlockUI();
				}
			});
		},
		//Funcion para Grabar nuevo registro
		<portlet:namespace/>guardarfiseTipoGastoVar: function(){
			if (fiseTipoGastoVar.validarFormulario()){
				$.blockUI({ message: fiseTipoGastoVar.mensajeGuardando });
				 jQuery.ajax({
					 url: fiseTipoGastoVar.urlGrabar+'&'+fiseTipoGastoVar.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {											
						<portlet:namespace />id: fiseTipoGastoVar.f_id.val(),
						<portlet:namespace />descripcion: fiseTipoGastoVar.f_descripcion.val() 																
						},
					success: function(data) {			
						if (data.resultado == "OK"){				
							var addhtml2='La Observación se guardó satisfactoriamente';
							
							fiseTipoGastoVar.dialogMessageContent.html(addhtml2);
							fiseTipoGastoVar.dialogMessage.dialog("open");							
							fiseTipoGastoVar.initBlockUI();						
										
							fiseTipoGastoVar.f_id.attr("disabled",true);			        	
				        	fiseTipoGastoVar.f_descripcion.removeAttr("disabled");
							
							$('#<portlet:namespace/>guardarfiseTipoGastoVar').css('display','none');
							$('#<portlet:namespace/>actualizarfiseTipoGastoVar').css('display','block');
							
						}else if(data.resultado == "Error"){							
							var addhtmError='Se produjo un error al guardar la Observación.';					
							fiseTipoGastoVar.dialogErrorContent.html(addhtmError);
							fiseTipoGastoVar.dialogError.dialog("open");						
							fiseTipoGastoVar.initBlockUI();
						}else if(data.resultado=="Duplicado"){
							var addhtmlInfo='Ya existe registrado una Observación con este Id.';
							fiseTipoGastoVar.dialogInfoContent.html(addhtmlInfo);
							fiseTipoGastoVar.dialogInfo.dialog("open");						
							fiseTipoGastoVar.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						fiseTipoGastoVar.dialogErrorContent.html(addhtmError);
						fiseTipoGastoVar.dialogError.dialog("open");
						fiseTipoGastoVar.initBlockUI();
					}
				});			
			}
		},
		//Funcion para actualizar un registro
		<portlet:namespace/>actualizarfiseTipoGastoVar : function(){
			if (fiseTipoGastoVar.validarFormulario()){
				$.blockUI({ message: fiseTipoGastoVar.mensajeActualizando });
				 jQuery.ajax({
					 url: fiseTipoGastoVar.urlActualizar+'&'+fiseTipoGastoVar.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {
						<portlet:namespace />id: fiseTipoGastoVar.f_id.val(),
						<portlet:namespace />descripcion: fiseTipoGastoVar.f_descripcion.val() 	
						},
					success: function(data) {			
						if (data.resultado == "OK"){				
							var addhtml2='La Observación se actualizó satisfactoriamente';
							fiseTipoGastoVar.dialogMessageContent.html(addhtml2);
							fiseTipoGastoVar.dialogMessage.dialog("open");						
							fiseTipoGastoVar.initBlockUI();								
						}else if(data.resultado == "Error"){						
							var addhtmError='Se produjo un error al actualizar la Observación.';					
							fiseTipoGastoVar.dialogErrorContent.html(addhtmError);
							fiseTipoGastoVar.dialogError.dialog("open");				
							fiseTipoGastoVar.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						fiseTipoGastoVar.dialogErrorContent.html(addhtmError);
						fiseTipoGastoVar.dialogError.dialog("open");
						fiseTipoGastoVar.initBlockUI();
					}
				});						
			}
		},
		//funcion para validar ingreso de datos
		validarFormulario : function() {
			console.debug("tamaño de descripcion:  "+fiseTipoGastoVar.f_descripcion.val().length);
			if(fiseTipoGastoVar.f_id.val().length == ''){				
				var addhtmAlert='Debe ingresar el id de Observación.';					
				fiseTipoGastoVar.dialogValidacionContent.html(addhtmAlert);
				fiseTipoGastoVar.dialogValidacion.dialog("open");
				//fiseTipoGastoVar.f_id.focus();
				cod_focus=fiseTipoGastoVar.f_id;
			  	return false; 
			}else if(fiseTipoGastoVar.f_descripcion.val().length == ''){				
				var addhtmAlert='Debe ingresar descripción.';					
				fiseTipoGastoVar.dialogValidacionContent.html(addhtmAlert);
				fiseTipoGastoVar.dialogValidacion.dialog("open");
				//fiseTipoGastoVar.f_descripcion.focus();
				cod_focus=fiseTipoGastoVar.f_descripcion;
			  	return false; 
			}else if(!validarLetra(fiseTipoGastoVar.f_descripcion.val())){				
				var addhtmAlert='Debe ingresar una Descripción válida.';					
				fiseTipoGastoVar.dialogValidacionContent.html(addhtmAlert);
				fiseTipoGastoVar.dialogValidacion.dialog("open");
				//fiseTipoGastoVar.f_descripcion.focus();
				cod_focus=fiseTipoGastoVar.f_descripcion;
			  	return false; 
			}else if(fiseTipoGastoVar.f_descripcion.val().length > 499){				
				var addhtmAlert='La  descripción acepta como máximo 500 caracteres.';					
				fiseTipoGastoVar.dialogValidacionContent.html(addhtmAlert);
				fiseTipoGastoVar.dialogValidacion.dialog("open");
				//fiseTipoGastoVar.f_descripcion.focus();
				cod_focus=fiseTipoGastoVar.f_descripcion;
			  	return false; 
			}else{
				return true;
			}		
		},
		
		//funcion para regresar
		<portlet:namespace/>regresarfiseTipoGastoVar : function(){			
			fiseTipoGastoVar.divNuevo.hide();
			fiseTipoGastoVar.divBuscar.show();
					
			fiseTipoGastoVar.botonBuscar.trigger('click');
		},
		
		ponerFocus : function(cadena){		
			cadena.focus();
		},	
		
		//DIALOGOS
		initDialogs : function(){	
			fiseTipoGastoVar.dialogMessage.dialog({
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
			fiseTipoGastoVar.dialogConfirm.dialog({
				modal: true,
				height: 200,
				width: 450,			
				autoOpen: false,
				buttons: {
					"Si": function() {
						fiseTipoGastoVar.eliminarfiseTipoGastoVar(id_observacion);
						$( this ).dialog("close");
					},
					"No": function() {				
						$( this ).dialog("close");
					}
				}
			});	
			
			fiseTipoGastoVar.dialogValidacion.dialog({
				modal: true,
				autoOpen: false,
				width: 450,		
				buttons: {
					Aceptar: function() {
						fiseTipoGastoVar.ponerFocus(cod_focus);
						$( this ).dialog("close");
					}
				}
			});
			
			fiseTipoGastoVar.dialogError.dialog({
				modal: true,
				autoOpen: false,
				width: 450,		
				buttons: {
					Aceptar: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			fiseTipoGastoVar.dialogInfo.dialog({
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