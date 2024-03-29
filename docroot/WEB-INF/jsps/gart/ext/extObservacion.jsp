<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@page import="com.liferay.portal.kernel.util.PrefsPropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects />

<script type="text/javascript">

var fiseObservacion= {
		
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
			
			this.formCommand=$('#fiseObservacionBean');
			
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
			this.urlActualizar='<portlet:resourceURL id="actualizarObservacion" />';
			this.urlEditarView='<portlet:resourceURL id="editarViewObservacion" />';			
			this.urlEliminar='<portlet:resourceURL id="eliminarObservacion" />';
			this.urlNuevo='<portlet:resourceURL id="nuevoRegistroObservacion" />';
			
			
			
			//botones
			this.botonBuscar=$("#<portlet:namespace/>btnBuscarObservaciones");
			this.botonNuevo=$("#<portlet:namespace/>btnNuevaObservacion");
			this.botonRegresar=$("#<portlet:namespace/>regresarfiseObservacion");			
			this.botonGrabar=$("#<portlet:namespace/>guardarfiseObservacion");
			this.botonActualizar=$("#<portlet:namespace/>actualizarfiseObservacion");		
			
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
			fiseObservacion.botonBuscar.click(function() {
				fiseObservacion.buscarfiseObservacion();
			});			
			
			fiseObservacion.botonNuevo.click(function() {
				fiseObservacion.<portlet:namespace/>nuevofiseObservacion();
		    });
			
			fiseObservacion.botonGrabar.click(function() {
				fiseObservacion.<portlet:namespace/>guardarfiseObservacion();
			});
			
			fiseObservacion.botonActualizar.click(function() {
				fiseObservacion.<portlet:namespace/>actualizarfiseObservacion();
			});		
			
			
		    fiseObservacion.botonRegresar.click(function() {
		    	fiseObservacion.<portlet:namespace/>regresarfiseObservacion();
		    });			
		    
		    fiseObservacion.initDialogs();
		    
		    //eventos por defecto		   
			fiseObservacion.botonBuscar.trigger('click');
			fiseObservacion.initBlockUI();
		},
		//funcion para armar el modelo de la grilla para el resultado
		buildGrids : function () {	
			fiseObservacion.tablaResultados.jqGrid({
			   datatype: "local",
		       colNames: ['Id Observaci�n','Descripci�n de Observaci�n','Ver','Editar'],
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
					pager: fiseObservacion.paginadoResultados,
				    viewrecords: true,
				   	caption: "Resultado(s) de la b�squeda",
				    sortorder: "asc",	   	    	   	   
		       gridComplete: function(){
		      		var ids = fiseObservacion.tablaResultados.jqGrid('getDataIDs');
		      		for(var i=0;i < ids.length;i++){
		      			var cl = ids[i];
		      			var ret = fiseObservacion.tablaResultados.jqGrid('getRowData',cl);           
		      			view = "<a href='#'><img border='0' title='Ver' src='/net-theme/images/img-net/file.png'  align='center' onclick=\"fiseObservacion.verfiseObservacion('"+ret.idObservacion+"');\" /></a> ";
		      			edit = "<a href='#'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png'  align='center' onclick=\"fiseObservacion.editarfiseObservacion('"+ret.idObservacion+"');\" /></a> ";
		      			/* elim = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"fiseObservacion.confirmarEliminarfiseObservacion('"+ret.idObservacion+"');\" /></a> "; */              			
		      			fiseObservacion.tablaResultados.jqGrid('setRowData',ids[i],{view:view});
		      			fiseObservacion.tablaResultados.jqGrid('setRowData',ids[i],{edit:edit});
		      			/* fiseObservacion.tablaResultados.jqGrid('setRowData',ids[i],{elim:elim}); */
		      		}
		      }
		  	});
			fiseObservacion.tablaResultados.jqGrid('navGrid',fiseObservacion.paginadoResultados,{add:false,edit:false,del:false,search: false,refresh: false});	
			fiseObservacion.tablaResultados.jqGrid('navButtonAdd',fiseObservacion.paginadoResultados,{
			       caption:"Exportar a Excel",
			       buttonicon: "ui-icon-bookmark",
			       onClickButton : function () {
			    	   var ids = fiseObservacion.tablaResultados.jqGrid('getDataIDs');
				       if(ids!=0){
				    		  location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';   
				       }else{
				    	var addhtmInfo='No existe informaci�n para exportar a Excel';				
				    	fiseObservacion.dialogInfoContent.html(addhtmInfo);
				    	fiseObservacion.dialogInfo.dialog("open");
				       }  		       
			       } 
			});  
		},
		//funcion para buscar
		buscarfiseObservacion : function () {		
			fiseObservacion.blockUI();
			jQuery.ajax({			
					url: fiseObservacion.urlBusqueda+'&'+fiseObservacion.formCommand.serialize(),
					type: 'post',
					dataType: 'json',				
					success: function(gridData) {					
							fiseObservacion.tablaResultados.clearGridData(true);
							fiseObservacion.tablaResultados.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
							fiseObservacion.tablaResultados[0].refreshIndex();
							fiseObservacion.initBlockUI();
					},error : function(){
						var addhtmError='Error de conexi�n.';					
						fiseObservacion.dialogErrorContent.html(addhtmError);
						fiseObservacion.dialogError.dialog("open");
						fiseObservacion.initBlockUI();
					}
				});			
		},		
		//funcion para nuevo registro
		<portlet:namespace/>nuevofiseObservacion : function(){	
			jQuery.ajax({			
				url: fiseObservacion.urlNuevo+'&'+fiseObservacion.formCommand.serialize(),
				type: 'post',
				dataType: 'json',				
				success: function(data) {					
					fiseObservacion.f_id.val(data.id);
					fiseObservacion.f_descripcion.val('');			
					fiseObservacion.f_id.attr("disabled",true);  	
		        	fiseObservacion.f_descripcion.removeAttr("disabled");	
					
					fiseObservacion.divNuevo.show();
					fiseObservacion.divBuscar.hide();		
								
					console.debug("boton nuevo registro:  ");
					
					$('#<portlet:namespace/>guardarfiseObservacion').css('display','block');
					$('#<portlet:namespace/>actualizarfiseObservacion').css('display','none');	
					
				},error : function(){
					var addhtmError='Error de conexi�n.';					
					fiseObservacion.dialogErrorContent.html(addhtmError);
					fiseObservacion.dialogError.dialog("open");
					fiseObservacion.initBlockUI();
				}
			});			
		},
		
		//Function para Visualizar los datos del formulario		
		verfiseObservacion : function(idObservacion){	
			$.blockUI({ message: fiseObservacion.mensajeObteniendoDatos});
			jQuery.ajax({
					url: fiseObservacion.urlEditarView+'&'+fiseObservacion.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {						  
					      <portlet:namespace />id: idObservacion					 
						},
					success: function(data) {
					    if (data != null){															
					    	fiseObservacion.divNuevo.show();
					    	fiseObservacion.divBuscar.hide();	
					    	
					    	fiseObservacion.llenarDatosEditar(data);
					    	
					    	fiseObservacion.initBlockUI();				    	
					    			
							fiseObservacion.f_id.attr("disabled",true);				        	
				        	fiseObservacion.f_descripcion.attr("disabled",true);
				        	
					    	
					    	$('#<portlet:namespace/>guardarfiseObservacion').css('display','none');
					    	$('#<portlet:namespace/>actualizarfiseObservacion').css('display','none');
					    							
				        }						
						else{							
							var addhtmError='Error al visualizar los datos del registro seleccionado.';					
							fiseObservacion.dialogErrorContent.html(addhtmError);
							fiseObservacion.dialogError.dialog("open");	
							fiseObservacion.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexi�n.';					
						fiseObservacion.dialogErrorContent.html(addhtmError);
						fiseObservacion.dialogError.dialog("open");
						fiseObservacion.initBlockUI();
					}
			});	
		},
		//Function para editar los datos del formulario
		editarfiseObservacion : function(idObservacion){	
			    console.debug("entrando a editar ");		
				$.blockUI({ message: fiseObservacion.mensajeObteniendoDatos });			 
				jQuery.ajax({
						url: fiseObservacion.urlEditarView+'&'+fiseObservacion.formCommand.serialize(),
						type: 'post',
						dataType: 'json',
						data: {							
						   <portlet:namespace />id: idObservacion						   				  
						},
						success: function(data) {				
							if (data != null){															
								fiseObservacion.divNuevo.show();
								fiseObservacion.divBuscar.hide();	
								
								fiseObservacion.llenarDatosEditar(data);
								
								fiseObservacion.initBlockUI();					
											
								fiseObservacion.f_id.attr("disabled",true);			        	
					        	fiseObservacion.f_descripcion.removeAttr("disabled");
								
								$('#<portlet:namespace/>guardarfiseObservacion').css('display','none');
								$('#<portlet:namespace/>actualizarfiseObservacion').css('display','block');	
								//ESTILOS								
					         }
							else{								
								var addhtmError='Error al recuperar los datos del registro seleccionado.';					
								fiseObservacion.dialogErrorContent.html(addhtmError);
								fiseObservacion.dialogError.dialog("open");	
								fiseObservacion.initBlockUI();
							}
						},error : function(){
							var addhtmError='Error de conexi�n.';					
							fiseObservacion.dialogErrorContent.html(addhtmError);
							fiseObservacion.dialogError.dialog("open");
							fiseObservacion.initBlockUI();
						}
				});		
		},
		//funcion  para llenar los campos para editar
		llenarDatosEditar : function(bean){	
			fiseObservacion.f_id.val(bean.id);
			fiseObservacion.f_descripcion.val(bean.descripcion);  	
		},
		/**Function para confirmar si quiere eliminar el registro o no*/
		confirmarEliminarfiseObservacion : function(idObservacion){
			console.debug("entranado a eliminar confirmar:  "+idObservacion);
			var addhtml='�Est� seguro que desea eliminar el registro seleccionado?';
			fiseObservacion.dialogConfirmContent.html(addhtml);
			fiseObservacion.dialogConfirm.dialog("open");	
			id_observacion=idObservacion;
		},
		/**Function para  eliminar el registro una vez hecho la confirmacion*/
		eliminarfiseObservacion : function(id_observacion){
			console.debug("entranado a eliminar:  "+id_observacion);
			$.blockUI({ message: fiseObservacion.mensajeEliminando });
			jQuery.ajax({
				url: fiseObservacion.urlEliminar+'&'+fiseObservacion.formCommand.serialize(),
				type: 'post',
				dataType: 'json',
				data: {				
				   <portlet:namespace />id: id_observacion				  
					},
				success: function(data) {
					if (data.resultado == "OK"){
						var addhtml2='El registro fue eliminado satisfactoriamente';					
						fiseObservacion.dialogMessageContent.html(addhtml2);
						fiseObservacion.dialogMessage.dialog("open");
						fiseObservacion.buscarfiseObservacion();
						fiseObservacion.initBlockUI();
					}
					else{						
						var addhtmError='Error al eliminar el registro.';					
						fiseObservacion.dialogErrorContent.html(addhtmError);
						fiseObservacion.dialogError.dialog("open");	
						fiseObservacion.initBlockUI();
					}
				},error : function(){
					var addhtmError='Error de conexi�n.';					
					fiseObservacion.dialogErrorContent.html(addhtmError);
					fiseObservacion.dialogError.dialog("open");
					fiseObservacion.initBlockUI();
				}
			});
		},
		//Funcion para Grabar nuevo registro
		<portlet:namespace/>guardarfiseObservacion: function(){
			if (fiseObservacion.validarFormulario()){
				$.blockUI({ message: fiseObservacion.mensajeGuardando });
				 jQuery.ajax({
					 url: fiseObservacion.urlGrabar+'&'+fiseObservacion.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {											
						<portlet:namespace />id: fiseObservacion.f_id.val(),
						<portlet:namespace />descripcion: fiseObservacion.f_descripcion.val() 																
						},
					success: function(data) {			
						if (data.resultado == "OK"){				
							var addhtml2='La Observaci�n se guard� satisfactoriamente';
							
							fiseObservacion.dialogMessageContent.html(addhtml2);
							fiseObservacion.dialogMessage.dialog("open");							
							fiseObservacion.initBlockUI();						
										
							fiseObservacion.f_id.attr("disabled",true);			        	
				        	fiseObservacion.f_descripcion.removeAttr("disabled");
							
							$('#<portlet:namespace/>guardarfiseObservacion').css('display','none');
							$('#<portlet:namespace/>actualizarfiseObservacion').css('display','block');
							
						}else if(data.resultado == "Error"){							
							var addhtmError='Se produjo un error al guardar la Observaci�n.';					
							fiseObservacion.dialogErrorContent.html(addhtmError);
							fiseObservacion.dialogError.dialog("open");						
							fiseObservacion.initBlockUI();
						}else if(data.resultado=="Duplicado"){
							var addhtmlInfo='Ya existe registrado una Observaci�n con este Id.';
							fiseObservacion.dialogInfoContent.html(addhtmlInfo);
							fiseObservacion.dialogInfo.dialog("open");						
							fiseObservacion.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexi�n.';					
						fiseObservacion.dialogErrorContent.html(addhtmError);
						fiseObservacion.dialogError.dialog("open");
						fiseObservacion.initBlockUI();
					}
				});			
			}
		},
		//Funcion para actualizar un registro
		<portlet:namespace/>actualizarfiseObservacion : function(){
			if (fiseObservacion.validarFormulario()){
				$.blockUI({ message: fiseObservacion.mensajeActualizando });
				 jQuery.ajax({
					 url: fiseObservacion.urlActualizar+'&'+fiseObservacion.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {
						<portlet:namespace />id: fiseObservacion.f_id.val(),
						<portlet:namespace />descripcion: fiseObservacion.f_descripcion.val() 	
						},
					success: function(data) {			
						if (data.resultado == "OK"){				
							var addhtml2='La Observaci�n se actualiz� satisfactoriamente';
							fiseObservacion.dialogMessageContent.html(addhtml2);
							fiseObservacion.dialogMessage.dialog("open");						
							fiseObservacion.initBlockUI();								
						}else if(data.resultado == "Error"){						
							var addhtmError='Se produjo un error al actualizar la Observaci�n.';					
							fiseObservacion.dialogErrorContent.html(addhtmError);
							fiseObservacion.dialogError.dialog("open");				
							fiseObservacion.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexi�n.';					
						fiseObservacion.dialogErrorContent.html(addhtmError);
						fiseObservacion.dialogError.dialog("open");
						fiseObservacion.initBlockUI();
					}
				});						
			}
		},
		//funcion para validar ingreso de datos
		validarFormulario : function() {
			console.debug("tama�o de descripcion:  "+fiseObservacion.f_descripcion.val().length);
			if(fiseObservacion.f_id.val().length == ''){				
				var addhtmAlert='Debe ingresar el id de Observaci�n.';					
				fiseObservacion.dialogValidacionContent.html(addhtmAlert);
				fiseObservacion.dialogValidacion.dialog("open");
				//fiseObservacion.f_id.focus();
				cod_focus=fiseObservacion.f_id;
			  	return false; 
			}else if(fiseObservacion.f_descripcion.val().length == ''){				
				var addhtmAlert='Debe ingresar descripci�n.';					
				fiseObservacion.dialogValidacionContent.html(addhtmAlert);
				fiseObservacion.dialogValidacion.dialog("open");
				//fiseObservacion.f_descripcion.focus();
				cod_focus=fiseObservacion.f_descripcion;
			  	return false; 
			}else if(!validarLetra(fiseObservacion.f_descripcion.val())){				
				var addhtmAlert='Debe ingresar una Descripci�n v�lida.';					
				fiseObservacion.dialogValidacionContent.html(addhtmAlert);
				fiseObservacion.dialogValidacion.dialog("open");
				//fiseObservacion.f_descripcion.focus();
				cod_focus=fiseObservacion.f_descripcion;
			  	return false; 
			}else if(fiseObservacion.f_descripcion.val().length > 499){				
				var addhtmAlert='La  descripci�n acepta como m�ximo 500 caracteres.';					
				fiseObservacion.dialogValidacionContent.html(addhtmAlert);
				fiseObservacion.dialogValidacion.dialog("open");
				//fiseObservacion.f_descripcion.focus();
				cod_focus=fiseObservacion.f_descripcion;
			  	return false; 
			}else{
				return true;
			}		
		},
		
		//funcion para regresar
		<portlet:namespace/>regresarfiseObservacion : function(){			
			fiseObservacion.divNuevo.hide();
			fiseObservacion.divBuscar.show();
					
			fiseObservacion.botonBuscar.trigger('click');
		},
		
		ponerFocus : function(cadena){		
			cadena.focus();
		},	
		
		//DIALOGOS
		initDialogs : function(){	
			fiseObservacion.dialogMessage.dialog({
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
			fiseObservacion.dialogConfirm.dialog({
				modal: true,
				height: 200,
				width: 450,			
				autoOpen: false,
				buttons: {
					"Si": function() {
						fiseObservacion.eliminarfiseObservacion(id_observacion);
						$( this ).dialog("close");
					},
					"No": function() {				
						$( this ).dialog("close");
					}
				}
			});	
			
			fiseObservacion.dialogValidacion.dialog({
				modal: true,
				autoOpen: false,
				width: 450,		
				buttons: {
					Aceptar: function() {
						fiseObservacion.ponerFocus(cod_focus);
						$( this ).dialog("close");
					}
				}
			});
			
			fiseObservacion.dialogError.dialog({
				modal: true,
				autoOpen: false,
				width: 450,		
				buttons: {
					Aceptar: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			fiseObservacion.dialogInfo.dialog({
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