<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@page import="com.liferay.portal.kernel.util.PrefsPropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects />

<script type="text/javascript">

var fiseTipoDocRef= {
		
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
			
			this.formCommand=$('#tipDocReferenciaBean');
			
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
			this.urlBusqueda='<portlet:resourceURL id="busquedaTipoDocReferencia" />';			
			this.urlGrabar='<portlet:resourceURL id="grabarTipoDocReferencia" />';			
			this.urlActualizar='<portlet:resourceURL id="actualizarTipoDocReferencia" />';
			this.urlEditarView='<portlet:resourceURL id="editarViewTipoDocReferencia" />';			
			this.urlEliminar='<portlet:resourceURL id="eliminarTipoDocReferencia" />';			
			
			
			
			//botones
			this.botonBuscar=$("#<portlet:namespace/>btnBuscarTipoDocRefe");
			this.botonNuevo=$("#<portlet:namespace/>btnNuevaTipoDocRefe");
			this.botonRegresar=$("#<portlet:namespace/>regresarTipoDocRefe");			
			this.botonGrabar=$("#<portlet:namespace/>guardarTipoDocRefe");
			this.botonActualizar=$("#<portlet:namespace/>actualizarTipoDocRefe");		
			
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
			fiseTipoDocRef.botonBuscar.click(function() {
				fiseTipoDocRef.buscarfiseTipoDocRef();
			});			
			
			fiseTipoDocRef.botonNuevo.click(function() {
				fiseTipoDocRef.<portlet:namespace/>nuevofiseTipoDocRef();
		    });
			
			fiseTipoDocRef.botonGrabar.click(function() {
				fiseTipoDocRef.<portlet:namespace/>guardarfiseTipoDocRef();
			});
			
			fiseTipoDocRef.botonActualizar.click(function() {
				fiseTipoDocRef.<portlet:namespace/>actualizarfiseTipoDocRef();
			});		
			
			
		    fiseTipoDocRef.botonRegresar.click(function() {
		    	fiseTipoDocRef.<portlet:namespace/>regresarfiseTipoDocRef();
		    });			
		    
		    fiseTipoDocRef.initDialogs();
		    
		    //eventos por defecto		   
			fiseTipoDocRef.botonBuscar.trigger('click');
			fiseTipoDocRef.initBlockUI();
		},
		//funcion para armar el modelo de la grilla para el resultado
		buildGrids : function () {	
			fiseTipoDocRef.tablaResultados.jqGrid({
			   datatype: "local",
		       colNames: ['Id Tipo Doc. Referencia','Descripción','Ver','Editar','Eliminar'],
		       colModel: [
                       { name: 'idTipDocRef', index: 'idTipDocRef', width: 20,align:'center'},
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
					pager: fiseTipoDocRef.paginadoResultados,
				    viewrecords: true,
				   	caption: "Resultado(s) de la búsqueda",
				    sortorder: "asc",	   	    	   	   
		       gridComplete: function(){
		      		var ids = fiseTipoDocRef.tablaResultados.jqGrid('getDataIDs');
		      		for(var i=0;i < ids.length;i++){
		      			var cl = ids[i];
		      			var ret = fiseTipoDocRef.tablaResultados.jqGrid('getRowData',cl);           
		      			view = "<a href='#'><img border='0' title='Ver' src='/net-theme/images/img-net/file.png'  align='center' onclick=\"fiseTipoDocRef.verfiseTipoDocRef('"+ret.idTipDocRef+"');\" /></a> ";
		      			edit = "<a href='#'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png'  align='center' onclick=\"fiseTipoDocRef.editarfiseTipoDocRef('"+ret.idTipDocRef+"');\" /></a> ";
		      			elim = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"fiseTipoDocRef.confirmarEliminarfiseTipoDocRef('"+ret.idTipDocRef+"');\" /></a> ";               			
		      			fiseTipoDocRef.tablaResultados.jqGrid('setRowData',ids[i],{view:view});
		      			fiseTipoDocRef.tablaResultados.jqGrid('setRowData',ids[i],{edit:edit});
		      			 fiseTipoDocRef.tablaResultados.jqGrid('setRowData',ids[i],{elim:elim}); 
		      		}
		      }
		  	});
			fiseTipoDocRef.tablaResultados.jqGrid('navGrid',fiseTipoDocRef.paginadoResultados,{add:false,edit:false,del:false,search: false,refresh: false});	
			fiseTipoDocRef.tablaResultados.jqGrid('navButtonAdd',fiseTipoDocRef.paginadoResultados,{
			       caption:"Exportar a Excel",
			       buttonicon: "ui-icon-bookmark",
			       onClickButton : function () {
			    	   var ids = fiseTipoDocRef.tablaResultados.jqGrid('getDataIDs');
				       if(ids!=0){
				    		  location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';   
				       }else{
				    	var addhtmInfo='No existe información para exportar a Excel';				
				    	fiseTipoDocRef.dialogInfoContent.html(addhtmInfo);
				    	fiseTipoDocRef.dialogInfo.dialog("open");
				       }  		       
			       } 
			});  
		},
		
		//funcion para buscar
		buscarfiseTipoDocRef : function () {		
			fiseTipoDocRef.blockUI();
			jQuery.ajax({			
					url: fiseTipoDocRef.urlBusqueda+'&'+fiseTipoDocRef.formCommand.serialize(),
					type: 'post',
					dataType: 'json',				
					success: function(gridData) {					
							fiseTipoDocRef.tablaResultados.clearGridData(true);
							fiseTipoDocRef.tablaResultados.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
							fiseTipoDocRef.tablaResultados[0].refreshIndex();
							fiseTipoDocRef.initBlockUI();
					},error : function(){
						var addhtmError='Error de conexión.';					
						fiseTipoDocRef.dialogErrorContent.html(addhtmError);
						fiseTipoDocRef.dialogError.dialog("open");
						fiseTipoDocRef.initBlockUI();
					}
				});			
		},		
		
		//funcion para nuevo registro
		<portlet:namespace/>nuevofiseTipoDocRef : function(){	
			fiseTipoDocRef.f_id.val('');
			fiseTipoDocRef.f_descripcion.val('');			
			fiseTipoDocRef.f_id.removeAttr("disabled");  	
        	fiseTipoDocRef.f_descripcion.removeAttr("disabled");	
			
			fiseTipoDocRef.divNuevo.show();
			fiseTipoDocRef.divBuscar.hide();		
						
			console.debug("boton nuevo registro:  ");
			
			$('#<portlet:namespace/>guardarTipoDocRefe').css('display','block');
			$('#<portlet:namespace/>actualizarTipoDocRefe').css('display','none');
		},
		
		//Function para Visualizar los datos del formulario		
		verfiseTipoDocRef : function(id_TipoDoc){	
			 console.debug("entrando a editar "+id_TipoDoc);		
			$.blockUI({ message: fiseTipoDocRef.mensajeObteniendoDatos});
			jQuery.ajax({
					url: fiseTipoDocRef.urlEditarView+'&'+fiseTipoDocRef.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {						  
					      <portlet:namespace />id: id_TipoDoc,
					      <portlet:namespace />codigo: id_TipoDoc
						},
					success: function(data) {
					    if (data != null){															
					    	fiseTipoDocRef.divNuevo.show();
					    	fiseTipoDocRef.divBuscar.hide();	
					    	
					    	fiseTipoDocRef.llenarDatosEditar(data);
					    	
					    	fiseTipoDocRef.initBlockUI();				    	
					    			
							fiseTipoDocRef.f_id.attr("disabled",true);				        	
				        	fiseTipoDocRef.f_descripcion.attr("disabled",true);
				        	
					    	
					    	$('#<portlet:namespace/>guardarTipoDocRefe').css('display','none');
					    	$('#<portlet:namespace/>actualizarTipoDocRefe').css('display','none');
					    							
				        }						
						else{							
							var addhtmError='Error al visualizar los datos del registro seleccionado.';					
							fiseTipoDocRef.dialogErrorContent.html(addhtmError);
							fiseTipoDocRef.dialogError.dialog("open");	
							fiseTipoDocRef.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						fiseTipoDocRef.dialogErrorContent.html(addhtmError);
						fiseTipoDocRef.dialogError.dialog("open");
						fiseTipoDocRef.initBlockUI();
					}
			});	
		},
		
		//Function para editar los datos del formulario
		editarfiseTipoDocRef : function(id_TipoDocRef){	
			    console.debug("entrando a editar "+id_TipoDocRef);		
				$.blockUI({ message: fiseTipoDocRef.mensajeObteniendoDatos });			 
				jQuery.ajax({
						url: fiseTipoDocRef.urlEditarView+'&'+fiseTipoDocRef.formCommand.serialize(),
						type: 'post',
						dataType: 'json',
						data: {							
						   <portlet:namespace />id: id_TipoDocRef,
						   <portlet:namespace />codigo: id_TipoDocRef
						},
						success: function(data) {				
							if (data != null){															
								fiseTipoDocRef.divNuevo.show();
								fiseTipoDocRef.divBuscar.hide();	
								
								fiseTipoDocRef.llenarDatosEditar(data);
								
								fiseTipoDocRef.initBlockUI();					
											
								fiseTipoDocRef.f_id.attr("disabled",true);			        	
					        	fiseTipoDocRef.f_descripcion.removeAttr("disabled");
								
								$('#<portlet:namespace/>guardarTipoDocRefe').css('display','none');
								$('#<portlet:namespace/>actualizarTipoDocRefe').css('display','block');	
								//ESTILOS								
					         }
							else{								
								var addhtmError='Error al recuperar los datos del registro seleccionado.';					
								fiseTipoDocRef.dialogErrorContent.html(addhtmError);
								fiseTipoDocRef.dialogError.dialog("open");	
								fiseTipoDocRef.initBlockUI();
							}
						},error : function(){
							var addhtmError='Error de conexión.';					
							fiseTipoDocRef.dialogErrorContent.html(addhtmError);
							fiseTipoDocRef.dialogError.dialog("open");
							fiseTipoDocRef.initBlockUI();
						}
				});		
		},
		
		//funcion  para llenar los campos para editar
		llenarDatosEditar : function(bean){	
			fiseTipoDocRef.f_id.val(bean.id);
			fiseTipoDocRef.f_descripcion.val(bean.descripcion);  	
		},
		
		/**Function para confirmar si quiere eliminar el registro o no*/
		confirmarEliminarfiseTipoDocRef : function(idTipoDoc){
			console.debug("entranado a eliminar confirmar:  "+idTipoDoc);
			var addhtml='¿Está seguro que desea eliminar el registro seleccionado?';
			fiseTipoDocRef.dialogConfirmContent.html(addhtml);
			fiseTipoDocRef.dialogConfirm.dialog("open");	
			id_tipoDoc=idTipoDoc;
		},
		
		/**Function para  eliminar el registro una vez hecho la confirmacion*/
		eliminarfiseTipoDocRef : function(id_tipoDoc){
			console.debug("entranado a eliminar:  "+id_tipoDoc);
			$.blockUI({ message: fiseTipoDocRef.mensajeEliminando });
			jQuery.ajax({
				url: fiseTipoDocRef.urlEliminar+'&'+fiseTipoDocRef.formCommand.serialize(),
				type: 'post',
				dataType: 'json',
				data: {				
				   <portlet:namespace />id: id_tipoDoc				  
					},
				success: function(data) {
					if (data.resultado == "OK"){
						var addhtml2='El registro fue eliminado satisfactoriamente';					
						fiseTipoDocRef.dialogMessageContent.html(addhtml2);
						fiseTipoDocRef.dialogMessage.dialog("open");
						fiseTipoDocRef.buscarfiseTipoDocRef();
						fiseTipoDocRef.initBlockUI();
					}
					else{						
						var addhtmError='Error al eliminar el registro.';					
						fiseTipoDocRef.dialogErrorContent.html(addhtmError);
						fiseTipoDocRef.dialogError.dialog("open");	
						fiseTipoDocRef.initBlockUI();
					}
				},error : function(){
					var addhtmError='Error de conexión.';					
					fiseTipoDocRef.dialogErrorContent.html(addhtmError);
					fiseTipoDocRef.dialogError.dialog("open");
					fiseTipoDocRef.initBlockUI();
				}
			});
		},
		
		//Funcion para Grabar nuevo registro
		<portlet:namespace/>guardarfiseTipoDocRef: function(){
			if (fiseTipoDocRef.validarFormulario()){
				$.blockUI({ message: fiseTipoDocRef.mensajeGuardando });
				 jQuery.ajax({
					 url: fiseTipoDocRef.urlGrabar+'&'+fiseTipoDocRef.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {											
						<portlet:namespace />id: fiseTipoDocRef.f_id.val(),
						<portlet:namespace />descripcion: fiseTipoDocRef.f_descripcion.val() 																
						},
					success: function(data) {			
						if (data.resultado == "OK"){				
							var addhtml2='Tipo de Documento de Referencia se guardó satisfactoriamente';
							
							fiseTipoDocRef.dialogMessageContent.html(addhtml2);
							fiseTipoDocRef.dialogMessage.dialog("open");							
							fiseTipoDocRef.initBlockUI();						
										
							fiseTipoDocRef.f_id.attr("disabled",true);			        	
				        	fiseTipoDocRef.f_descripcion.removeAttr("disabled");
							
							$('#<portlet:namespace/>guardarTipoDocRefe').css('display','none');
							$('#<portlet:namespace/>actualizarTipoDocRefe').css('display','block');
							
						}else if(data.resultado == "Error"){							
							var addhtmError='Se produjo un error al guardar Tipo de Documento de Referencia.';					
							fiseTipoDocRef.dialogErrorContent.html(addhtmError);
							fiseTipoDocRef.dialogError.dialog("open");						
							fiseTipoDocRef.initBlockUI();
						}else if(data.resultado=="Duplicado"){
							var addhtmlInfo='Ya existe registrado un Tipo de Documento de Referencia con este Id.';
							fiseTipoDocRef.dialogInfoContent.html(addhtmlInfo);
							fiseTipoDocRef.dialogInfo.dialog("open");						
							fiseTipoDocRef.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						fiseTipoDocRef.dialogErrorContent.html(addhtmError);
						fiseTipoDocRef.dialogError.dialog("open");
						fiseTipoDocRef.initBlockUI();
					}
				});			
			}
		},
		//Funcion para actualizar un registro
		<portlet:namespace/>actualizarfiseTipoDocRef : function(){
			if (fiseTipoDocRef.validarFormulario()){
				$.blockUI({ message: fiseTipoDocRef.mensajeActualizando });
				 jQuery.ajax({
					 url: fiseTipoDocRef.urlActualizar+'&'+fiseTipoDocRef.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {
						<portlet:namespace />id: fiseTipoDocRef.f_id.val(),
						<portlet:namespace />descripcion: fiseTipoDocRef.f_descripcion.val() 	
						},
					success: function(data) {			
						if (data.resultado == "OK"){				
							var addhtml2='Tipo de Documento de Referencia se actualizó satisfactoriamente';
							fiseTipoDocRef.dialogMessageContent.html(addhtml2);
							fiseTipoDocRef.dialogMessage.dialog("open");						
							fiseTipoDocRef.initBlockUI();								
						}else if(data.resultado == "Error"){						
							var addhtmError='Se produjo un error al actualizar Tipo de Documento de Referencia.';					
							fiseTipoDocRef.dialogErrorContent.html(addhtmError);
							fiseTipoDocRef.dialogError.dialog("open");				
							fiseTipoDocRef.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						fiseTipoDocRef.dialogErrorContent.html(addhtmError);
						fiseTipoDocRef.dialogError.dialog("open");
						fiseTipoDocRef.initBlockUI();
					}
				});						
			}
		},
		//funcion para validar ingreso de datos
		validarFormulario : function() {
			console.debug("tamaño de descripcion:  "+fiseTipoDocRef.f_descripcion.val().length);
			if(fiseTipoDocRef.f_id.val().length == ''){				
				var addhtmAlert='Debe ingresar el id de Tipo de Documento de Referencia.';					
				fiseTipoDocRef.dialogValidacionContent.html(addhtmAlert);
				fiseTipoDocRef.dialogValidacion.dialog("open");
				//fiseTipoDocRef.f_id.focus();
				cod_focus=fiseTipoDocRef.f_id;
			  	return false; 
			}else if(fiseTipoDocRef.f_descripcion.val().length == ''){				
				var addhtmAlert='Debe ingresar descripción.';					
				fiseTipoDocRef.dialogValidacionContent.html(addhtmAlert);
				fiseTipoDocRef.dialogValidacion.dialog("open");
				//fiseTipoDocRef.f_descripcion.focus();
				cod_focus=fiseTipoDocRef.f_descripcion;
			  	return false; 
			}else if(!validarLetra(fiseTipoDocRef.f_descripcion.val())){				
				var addhtmAlert='Debe ingresar una Descripción válida.';					
				fiseTipoDocRef.dialogValidacionContent.html(addhtmAlert);
				fiseTipoDocRef.dialogValidacion.dialog("open");
				//fiseTipoDocRef.f_descripcion.focus();
				cod_focus=fiseTipoDocRef.f_descripcion;
			  	return false; 
			}else if(fiseTipoDocRef.f_descripcion.val().length > 499){				
				var addhtmAlert='La  descripción acepta como máximo 500 caracteres.';					
				fiseTipoDocRef.dialogValidacionContent.html(addhtmAlert);
				fiseTipoDocRef.dialogValidacion.dialog("open");
				//fiseTipoDocRef.f_descripcion.focus();
				cod_focus=fiseTipoDocRef.f_descripcion;
			  	return false; 
			}else{
				return true;
			}		
		},
		
		//funcion para regresar
		<portlet:namespace/>regresarfiseTipoDocRef : function(){			
			fiseTipoDocRef.divNuevo.hide();
			fiseTipoDocRef.divBuscar.show();
					
			fiseTipoDocRef.botonBuscar.trigger('click');
		},
		
		ponerFocus : function(cadena){		
			cadena.focus();
		},	
		
		//DIALOGOS
		initDialogs : function(){	
			fiseTipoDocRef.dialogMessage.dialog({
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
			fiseTipoDocRef.dialogConfirm.dialog({
				modal: true,
				height: 200,
				width: 450,			
				autoOpen: false,
				buttons: {
					"Si": function() {
						fiseTipoDocRef.eliminarfiseTipoDocRef(id_tipoDoc);
						$( this ).dialog("close");
					},
					"No": function() {				
						$( this ).dialog("close");
					}
				}
			});	
			
			fiseTipoDocRef.dialogValidacion.dialog({
				modal: true,
				autoOpen: false,
				width: 450,		
				buttons: {
					Aceptar: function() {
						fiseTipoDocRef.ponerFocus(cod_focus);
						$( this ).dialog("close");
					}
				}
			});
			
			fiseTipoDocRef.dialogError.dialog({
				modal: true,
				autoOpen: false,
				width: 450,		
				buttons: {
					Aceptar: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			fiseTipoDocRef.dialogInfo.dialog({
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