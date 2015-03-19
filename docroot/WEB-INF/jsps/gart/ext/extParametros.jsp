<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@page import="com.liferay.portal.kernel.util.PrefsPropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects />

<script type="text/javascript">

var fiseParametros= {
		
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
		i_codigoBusq:null,
		i_nombreBusq:null,		
		
		//variables para nuevo registro
		f_codigo:null,
		f_nombre:null,
		f_valor:null,
		f_orden:null,
		//grillas
		tablaResultados:null,
		paginadoResultados:null,	
		
		
		
		init : function() {
			
			this.formCommand=$('#fiseParametroBean');
			
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
			this.urlBusqueda='<portlet:resourceURL id="busquedaParametros" />';			
			this.urlGrabar='<portlet:resourceURL id="grabarParametro" />';			
			this.urlActualizar='<portlet:resourceURL id="actualizarParametro" />';
			this.urlEditarView='<portlet:resourceURL id="editarViewParametro" />';			
			this.urlEliminar='<portlet:resourceURL id="eliminarParametro" />';
			this.urlNuevo='<portlet:resourceURL id="nuevoRegistroParametro" />';
			
			
			
			//botones
			this.botonBuscar=$("#<portlet:namespace/>btnBuscarParametros");
			this.botonNuevo=$("#<portlet:namespace/>btnNuevoParametro");
			this.botonRegresar=$("#<portlet:namespace/>regresarfiseParametro");			
			this.botonGrabar=$("#<portlet:namespace/>guardarfiseParametro");
			this.botonActualizar=$("#<portlet:namespace/>actualizarfiseParametro");		
			
			//variables de busqueda
			this.i_codBusq=$('#codigoBusq');
			this.i_nombreBusq=$('#nombreBusq');
			
			//VARIBALES PARA NUEVO REGISTRO	
			this.f_codigo=$('#codigo');			
			this.f_nombre=$('#nombre');
			this.f_valor=$('#valor');
			this.f_orden=$('#orden');
			
			//grillas			
			this.tablaResultados=$("#<portlet:namespace/>grid_resultado_busqueda");
			this.paginadoResultados='#<portlet:namespace/>paginador_resultado_busqueda';
			this.buildGrids();	//cargar el modelo de la grilla	
			
			
			
			//llamado a la funciones de cada boton
			fiseParametros.botonBuscar.click(function() {
				fiseParametros.buscarfiseParametro();
			});			
			
			fiseParametros.botonNuevo.click(function() {
				fiseParametros.<portlet:namespace/>nuevofiseParametro();
		    });
			
			fiseParametros.botonGrabar.click(function() {
				fiseParametros.<portlet:namespace/>guardarfiseParametro();
			});
			
			fiseParametros.botonActualizar.click(function() {
				fiseParametros.<portlet:namespace/>actualizarfiseParametro();
			});		
			
			
			fiseParametros.botonRegresar.click(function() {
				fiseParametros.<portlet:namespace/>regresarfiseParametro();
		    });			
		    
			fiseParametros.initDialogs();
		    
		    //eventos por defecto		   
			fiseParametros.botonBuscar.trigger('click');
			fiseParametros.initBlockUI();
		},
		//funcion para armar el modelo de la grilla para el resultado
		buildGrids : function () {	
			fiseParametros.tablaResultados.jqGrid({
			   datatype: "local",
		       colNames: ['Código','Nombre','Valor','Orden','Ver','Editar','Eliminar'],
		       colModel: [
                       { name: 'codigo', index: 'codigo', width: 40},
					   { name: 'nombre', index: 'nombre', width: 150},					
					   { name: 'valor', index: 'valor', width: 80},		
					   { name: 'orden', index: 'orden', width: 30},	
		               { name: 'view', index: 'view', width: 20,align:'center' },
		               { name: 'edit', index: 'edit', width: 20,align:'center' },
		               { name: 'elim', index: 'elim', width: 20,align:'center' }  
		               
			   	    ],
			   	 multiselect: false,
					rowNum:10,
				   	rowList:[10,20,50],
					height: 225,
				   	autowidth: true,
					rownumbers: true,
					shrinkToFit:true,
					pager: fiseParametros.paginadoResultados,
				    viewrecords: true,
				   	caption: "Resultado(s) de la búsqueda",
				    sortorder: "asc",	   	    	   	   
		       gridComplete: function(){
		      		var ids = fiseParametros.tablaResultados.jqGrid('getDataIDs');
		      		for(var i=0;i < ids.length;i++){
		      			var cl = ids[i];
		      			var ret = fiseParametros.tablaResultados.jqGrid('getRowData',cl);           
		      			view = "<a href='#'><img border='0' title='Ver' src='/net-theme/images/img-net/file.png'  align='center' onclick=\"fiseParametros.verfiseParametro('"+ret.codigo+"');\" /></a> ";
		      			edit = "<a href='#'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png'  align='center' onclick=\"fiseParametros.editarfiseParametro('"+ret.codigo+"');\" /></a> ";
		      			elim = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"fiseParametros.confirmarEliminarfiseParametro('"+ret.codigo+"');\" /></a> ";              			
		      			fiseParametros.tablaResultados.jqGrid('setRowData',ids[i],{view:view});
		      			fiseParametros.tablaResultados.jqGrid('setRowData',ids[i],{edit:edit});
		      			fiseParametros.tablaResultados.jqGrid('setRowData',ids[i],{elim:elim});
		      		}
		      }
		  	});
			fiseParametros.tablaResultados.jqGrid('navGrid',fiseParametros.paginadoResultados,{add:false,edit:false,del:false,search: false,refresh: false});	
			fiseParametros.tablaResultados.jqGrid('navButtonAdd',fiseParametros.paginadoResultados,{
			       caption:"Exportar a Excel",
			       buttonicon: "ui-icon-bookmark",
			       onClickButton : function () {
			    	   var ids = fiseParametros.tablaResultados.jqGrid('getDataIDs');
				       if(ids!=0){
				    		  location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';   
				       }else{
				    	var addhtmInfo='No existe información para exportar a Excel';				
				    	fiseParametros.dialogInfoContent.html(addhtmInfo);
				    	fiseParametros.dialogInfo.dialog("open");
				       }  		       
			       } 
			});  
		},
		//funcion para buscar
		buscarfiseParametro : function () {		
			fiseParametros.blockUI();
			jQuery.ajax({			
					url: fiseParametros.urlBusqueda+'&'+fiseParametros.formCommand.serialize(),
					type: 'post',
					dataType: 'json',				
					success: function(gridData) {					
						fiseParametros.tablaResultados.clearGridData(true);
						fiseParametros.tablaResultados.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
						fiseParametros.tablaResultados[0].refreshIndex();
						fiseParametros.initBlockUI();
					},error : function(){
						var addhtmError='Error de conexión.';					
						fiseParametros.dialogErrorContent.html(addhtmError);
						fiseParametros.dialogError.dialog("open");
						fiseParametros.initBlockUI();
					}
				});			
		},		
		//funcion para nuevo registro
		<portlet:namespace/>nuevofiseParametro : function(){	

					fiseParametros.f_codigo.val('');
					fiseParametros.f_nombre.val('');			
					fiseParametros.f_valor.val('');
					fiseParametros.f_orden.val('');
					fiseParametros.f_codigo.removeAttr("disabled");  	
					fiseParametros.f_nombre.removeAttr("disabled");	
					fiseParametros.f_valor.removeAttr("disabled");	
					fiseParametros.f_orden.removeAttr("disabled");	
					
					fiseParametros.f_orden.attr("onkeypress","return soloNumerosEntero(event, 1, 'orden',5,0)");
					
					fiseParametros.divNuevo.show();
					fiseParametros.divBuscar.hide();		
								
					console.debug("boton nuevo registro:  ");
					
					$('#<portlet:namespace/>guardarfiseParametro').css('display','block');
					$('#<portlet:namespace/>actualizarfiseParametro').css('display','none');	
					
				
		},
		
		//Function para Visualizar los datos del formulario		
		verfiseParametro : function(cod_parametro){	
			$.blockUI({ message: fiseParametros.mensajeObteniendoDatos});
			jQuery.ajax({
					url: fiseParametros.urlEditarView+'&'+fiseParametros.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {						  
					      <portlet:namespace />codParametro  : cod_parametro					 
						},
					success: function(data) {
					    if (data != null){															
					    	fiseParametros.divNuevo.show();
					    	fiseParametros.divBuscar.hide();	
					    	
					    	fiseParametros.llenarDatosEditar(data);
					    	
					    	fiseParametros.initBlockUI();				    	
					    			
					    	fiseParametros.f_codigo.attr("disabled",true);				        	
					    	fiseParametros.f_nombre.attr("disabled",true);
					    	fiseParametros.f_valor.attr("disabled",true);
					    	fiseParametros.f_orden.attr("disabled",true);
					    	
					    	$('#<portlet:namespace/>guardarfiseParametro').css('display','none');
					    	$('#<portlet:namespace/>actualizarfiseParametro').css('display','none');
					    							
				        }						
						else{							
							var addhtmError='Error al visualizar los datos del registro seleccionado.';					
							fiseParametros.dialogErrorContent.html(addhtmError);
							fiseParametros.dialogError.dialog("open");	
							fiseParametros.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						fiseParametros.dialogErrorContent.html(addhtmError);
						fiseParametros.dialogError.dialog("open");
						fiseParametros.initBlockUI();
					}
			});	
		},
		//Function para editar los datos del formulario
		editarfiseParametro : function(cod_parametro){	
			    console.debug("entrando a editar ");		
				$.blockUI({ message: fiseParametros.mensajeObteniendoDatos });			 
				jQuery.ajax({
						url: fiseParametros.urlEditarView+'&'+fiseParametros.formCommand.serialize(),
						type: 'post',
						dataType: 'json',
						data: {							
						   <portlet:namespace />codParametro  : cod_parametro						   				  
						},
						success: function(data) {				
							if (data != null){															
								fiseParametros.divNuevo.show();
								fiseParametros.divBuscar.hide();	
								
								fiseParametros.llenarDatosEditar(data);
								
								fiseParametros.initBlockUI();					
											
								fiseParametros.f_codigo.attr("disabled",true);  	
								fiseParametros.f_nombre.removeAttr("disabled");
								fiseParametros.f_valor.removeAttr("disabled");
								fiseParametros.f_orden.removeAttr("disabled");
								
								$('#<portlet:namespace/>guardarfiseParametro').css('display','none');
								$('#<portlet:namespace/>actualizarfiseParametro').css('display','block');	
								//ESTILOS								
					         }
							else{								
								var addhtmError='Error al recuperar los datos del registro seleccionado.';					
								fiseParametros.dialogErrorContent.html(addhtmError);
								fiseParametros.dialogError.dialog("open");	
								fiseParametros.initBlockUI();
							}
						},error : function(){
							var addhtmError='Error de conexión.';					
							fiseParametros.dialogErrorContent.html(addhtmError);
							fiseParametros.dialogError.dialog("open");
							fiseParametros.initBlockUI();
						}
				});		
		},
		//funcion  para llenar los campos para editar
		llenarDatosEditar : function(bean){	
			fiseParametros.f_codigo.val(bean.codigo);
			fiseParametros.f_nombre.val(bean.nombre);
			fiseParametros.f_valor.val(bean.valor);
			fiseParametros.f_orden.val(bean.orden);
			
			fiseParametros.f_orden.attr("onkeypress","return soloNumerosEntero(event, 1, 'orden',5,0)");
		},
		/**Function para confirmar si quiere eliminar el registro o no*/
		confirmarEliminarfiseParametro : function(codigo_parametro){
			console.debug("entranado a eliminar confirmar:  "+codigo_parametro);
			var addhtml='¿Está seguro que desea eliminar el registro seleccionado?';
			fiseParametros.dialogConfirmContent.html(addhtml);
			fiseParametros.dialogConfirm.dialog("open");	
			cod_parametro=codigo_parametro;
		},
		/**Function para  eliminar el registro una vez hecho la confirmacion*/
		eliminarfiseParametro : function(cod_parametro){
			console.debug("entranado a eliminar:  "+cod_parametro);
			$.blockUI({ message: fiseParametros.mensajeEliminando });
			jQuery.ajax({
				url: fiseParametros.urlEliminar+'&'+fiseParametros.formCommand.serialize(),
				type: 'post',
				dataType: 'json',
				data: {				
				   <portlet:namespace />codParametro  : cod_parametro				  
					},
				success: function(data) {
					if (data.resultado == "OK"){
						var addhtml2='Registro eliminado satisfactoriamente';					
						fiseParametros.dialogMessageContent.html(addhtml2);
						fiseParametros.dialogMessage.dialog("open");
						fiseParametros.buscarfiseParametro();
						fiseParametros.initBlockUI();
					}
					else{						
						var addhtmError='Error al eliminar el registro.';					
						fiseParametros.dialogErrorContent.html(addhtmError);
						fiseParametros.dialogError.dialog("open");	
						fiseParametros.initBlockUI();
					}
				},error : function(){
					var addhtmError='Error de conexión.';					
					fiseParametros.dialogErrorContent.html(addhtmError);
					fiseParametros.dialogError.dialog("open");
					fiseParametros.initBlockUI();
				}
			});
		},
		//Funcion para Grabar nuevo registro
		<portlet:namespace/>guardarfiseParametro: function(){
			if (fiseParametros.validarFormulario()){
				$.blockUI({ message: fiseParametros.mensajeGuardando });
				 jQuery.ajax({
					 url: fiseParametros.urlGrabar+'&'+fiseParametros.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {											
						<portlet:namespace />codigo : fiseParametros.f_codigo.val(),
						<portlet:namespace />nombre : fiseParametros.f_nombre.val() ,
						<portlet:namespace />valor  : fiseParametros.f_valor.val(),
						<portlet:namespace />orden : fiseParametros.f_orden.val()
						},
					success: function(data) {			
						if (data.resultado == "OK"){				
							var addhtml2='La Observación se guardó satisfactoriamente';
							
							fiseParametros.dialogMessageContent.html(addhtml2);
							fiseParametros.dialogMessage.dialog("open");							
							fiseParametros.initBlockUI();						
										
							fiseParametros.f_codigo.attr("disabled",true);			        	
							fiseParametros.f_nombre.removeAttr("disabled");
							fiseParametros.f_valor.removeAttr("disabled");
							fiseParametros.f_orden.removeAttr("disabled");
							
							$('#<portlet:namespace/>guardarfiseParametro').css('display','none');
							$('#<portlet:namespace/>actualizarfiseParametro').css('display','block');
							
						}else if(data.resultado == "Error"){							
							var addhtmError='Se produjo un error al guardar el Parámetro.';					
							fiseParametros.dialogErrorContent.html(addhtmError);
							fiseParametros.dialogError.dialog("open");						
							fiseParametros.initBlockUI();
						}else if(data.resultado=="Duplicado"){
							var addhtmlInfo='Ya existe registrado una Parámetro con este código.';
							fiseParametros.dialogInfoContent.html(addhtmlInfo);
							fiseParametros.dialogInfo.dialog("open");						
							fiseParametros.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						fiseParametros.dialogErrorContent.html(addhtmError);
						fiseParametros.dialogError.dialog("open");
						fiseParametros.initBlockUI();
					}
				});			
			}
		},
		//Funcion para actualizar un registro
		<portlet:namespace/>actualizarfiseParametro : function(){
			if (fiseParametros.validarFormulario()){
				$.blockUI({ message: fiseParametros.mensajeActualizando });
				 jQuery.ajax({
					 url: fiseParametros.urlActualizar+'&'+fiseParametros.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {
						<portlet:namespace />codigo : fiseParametros.f_codigo.val(),
						<portlet:namespace />nombre : fiseParametros.f_nombre.val(),
						<portlet:namespace />valor : fiseParametros.f_valor.val() ,
						<portlet:namespace />orden : fiseParametros.f_orden.val()
						},
					success: function(data) {			
						if (data.resultado == "OK"){				
							var addhtml2='La Observación se actualizó satisfactoriamente';
							fiseParametros.dialogMessageContent.html(addhtml2);
							fiseParametros.dialogMessage.dialog("open");						
							fiseParametros.initBlockUI();								
						}else if(data.resultado == "Error"){						
							var addhtmError='Se produjo un error al actualizar la Observación.';					
							fiseParametros.dialogErrorContent.html(addhtmError);
							fiseParametros.dialogError.dialog("open");				
							fiseParametros.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						fiseParametros.dialogErrorContent.html(addhtmError);
						fiseParametros.dialogError.dialog("open");
						fiseParametros.initBlockUI();
					}
				});						
			}
		},
		//funcion para validar ingreso de datos
		validarFormulario : function() {
			console.debug("tamaño de descripcion:  "+fiseParametros.f_nombre.val().length);
			
			if(fiseParametros.f_codigo.val().length == ''){				
				var addhtmAlert='Debe ingresar el código de Parámetro.';					
				fiseParametros.dialogValidacionContent.html(addhtmAlert);
				fiseParametros.dialogValidacion.dialog("open");
				cod_focus=fiseParametros.f_codigo;
			  	return false; 
			}
			
			if(fiseParametros.f_nombre.val().length == ''){				
				var addhtmAlert='Debe ingresar el nombre del Parámetro.';					
				fiseParametros.dialogValidacionContent.html(addhtmAlert);
				fiseParametros.dialogValidacion.dialog("open");
				cod_focus=fiseParametros.f_nombre;
			  	return false; 
			}else if(!validarLetra(fiseParametros.f_nombre.val())){				
				var addhtmAlert='Debe ingresar un Nombre de Parámetro válido.';					
				fiseParametros.dialogValidacionContent.html(addhtmAlert);
				fiseParametros.dialogValidacion.dialog("open");
				cod_focus=fiseParametros.f_nombre;
			  	return false; 
			}else if(fiseParametros.f_nombre.val().length > 199){				
				var addhtmAlert='El Nombre de Parámetro acepta como máximo 200 caracteres.';					
				fiseParametros.dialogValidacionContent.html(addhtmAlert);
				fiseParametros.dialogValidacion.dialog("open");
				cod_focus=fiseParametros.f_nombre;
			  	return false; 
			}
			
			if(fiseParametros.f_valor.val().length == ''){				
				var addhtmAlert='Debe ingresar el valor del Parámetro.';					
				fiseParametros.dialogValidacionContent.html(addhtmAlert);
				fiseParametros.dialogValidacion.dialog("open");
				cod_focus=fiseParametros.f_valor;
			  	return false; 
			}
			
			if(fiseParametros.f_orden.val().length == ''){				
				var addhtmAlert='Debe ingresar el orden del Parámetro.';					
				fiseParametros.dialogValidacionContent.html(addhtmAlert);
				fiseParametros.dialogValidacion.dialog("open");
				cod_focus=fiseParametros.f_orden;
			  	return false; 
			}
			
			return true;
		},
		
		//funcion para regresar
		<portlet:namespace/>regresarfiseParametro : function(){			
			fiseParametros.divNuevo.hide();
			fiseParametros.divBuscar.show();
					
			fiseParametros.botonBuscar.trigger('click');
		},
		
		ponerFocus : function(cadena){		
			cadena.focus();
		},	
		
		//DIALOGOS
		initDialogs : function(){	
			fiseParametros.dialogMessage.dialog({
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
			fiseParametros.dialogConfirm.dialog({
				modal: true,
				height: 200,
				width: 450,			
				autoOpen: false,
				buttons: {
					"Si": function() {
						fiseParametros.eliminarfiseParametro(cod_parametro);
						$( this ).dialog("close");
					},
					"No": function() {				
						$( this ).dialog("close");
					}
				}
			});	
			
			fiseParametros.dialogValidacion.dialog({
				modal: true,
				autoOpen: false,
				width: 450,		
				buttons: {
					Aceptar: function() {
						fiseParametros.ponerFocus(cod_focus);
						$( this ).dialog("close");
					}
				}
			});
			
			fiseParametros.dialogError.dialog({
				modal: true,
				autoOpen: false,
				width: 450,		
				buttons: {
					Aceptar: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			fiseParametros.dialogInfo.dialog({
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