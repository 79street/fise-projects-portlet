<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects />

<script type="text/javascript">

var fiseGrupoInformacion= {
		
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
		i_descripcionBusq:null,		
		
		//variables para nuevo registro
		f_idGrupoInf:null,
		f_descripcion:null,	
		f_estado :null,
		f_tipo :null,
		f_anoPres:null,
		f_mesPres:null,
		
		//grillas
		tablaResultados:null,
		paginadoResultados:null,	
		
		
		
		init : function() {
			
			this.formCommand=$('#grupoInformacionBean');
			
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
			this.mensajeEliminando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Desactivando </h3>';
			this.mensajeGuardando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Guardando Datos </h3>';
			this.mensajeActualizando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Actualizando Datos </h3>';
			
			
			//urls
			this.urlBusqueda='<portlet:resourceURL id="busquedaGrupoInformacion" />';			
			this.urlGrabar='<portlet:resourceURL id="grabarGrupoInformacion" />';			
			this.urlActualizar='<portlet:resourceURL id="actualizarGrupoInformacion" />';
			this.urlEditarView='<portlet:resourceURL id="editarViewGrupoInformacion" />';			
			this.urlEliminar='<portlet:resourceURL id="eliminarGrupoInformacion" />';
			
			//botones
			this.botonBuscar=$("#<portlet:namespace/>btnBuscarGrupoInf");
			this.botonNuevo=$("#<portlet:namespace/>btnNuevaGrupoInf");
			this.botonRegresar=$("#<portlet:namespace/>regresarGrupoInf");			
			this.botonGrabar=$("#<portlet:namespace/>guardarGrupoInf");
			this.botonActualizar=$("#<portlet:namespace/>actualizarGrupoInf");		
			
			//variables de busqueda			
			this.i_descripcionBusq=$('#descripcionBusq');
			
			//VARIBALES PARA NUEVO REGISTRO	
			this.f_idGrupoInf=$('#idGrupoInf');			
			this.f_descripcion=$('#descripcion');
			this.f_estado=$('#estado');
			this.f_tipo=$('#tipo');
			this.f_anoPres=$('#anioPres');	
			this.f_mesPres=$("#mesPres");
			
			
			//grillas			
			this.tablaResultados=$("#<portlet:namespace/>grid_resultado_busqueda");
			this.paginadoResultados='#<portlet:namespace/>paginador_resultado_busqueda';
			this.buildGrids();	//cargar el modelo de la grilla	
			
			
			
			//llamado a la funciones de cada boton
			fiseGrupoInformacion.botonBuscar.click(function() {
				fiseGrupoInformacion.buscarGrupoInformacion();
			});			
			
			 fiseGrupoInformacion.botonNuevo.click(function() {
				fiseGrupoInformacion.<portlet:namespace/>nuevoGrupoInformacion();
		    });
			
			fiseGrupoInformacion.botonGrabar.click(function() {
				fiseGrupoInformacion.<portlet:namespace/>guardarGrupoInformacion();
			});
			
			fiseGrupoInformacion.botonActualizar.click(function() {
				fiseGrupoInformacion.<portlet:namespace/>actualizarGrupoInformacion();
			});		
			
			
		    fiseGrupoInformacion.botonRegresar.click(function() {
		    	fiseGrupoInformacion.<portlet:namespace/>regresarGrupoInformacion();
		    });			
		    
		    fiseGrupoInformacion.initDialogs();
		    
		    //eventos por defecto		   
			fiseGrupoInformacion.botonBuscar.trigger('click');
			fiseGrupoInformacion.initBlockUI();
		},
		//funcion para armar el modelo de la grilla para el resultado
		buildGrids : function () {	
			fiseGrupoInformacion.tablaResultados.jqGrid({
			   datatype: "local",
		       colNames: ['Id Grupo Inf.','Descripción','Año Decl.','Mes Decl.','Estado','Periodicidad','Ver','Editar','Desactivar'],
		       colModel: [
                       { name: 'idGrupoInformacion', index: 'idGrupoInformacion', width: 20},
					   { name: 'descripcion', index: 'descripcion', width: 80},	
					   { name: 'anoPresentacion', index: 'anoPresentacion', width: 30 },   
		               { name: 'descMesPresentacion', index: 'descMesPresentacion', width: 30},	   
					   { name: 'descEstado', index: 'descEstado', width: 20,align:'center'},
					   { name: 'tipo', index: 'tipo', width: 20},
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
					pager: fiseGrupoInformacion.paginadoResultados,
				    viewrecords: true,
				   	caption: "Resultado(s) de la búsqueda",
				    sortorder: "asc",	   	    	   	   
		       gridComplete: function(){
		      		var ids = fiseGrupoInformacion.tablaResultados.jqGrid('getDataIDs');
		      		for(var i=0;i < ids.length;i++){
		      			var cl = ids[i];
		      			var ret = fiseGrupoInformacion.tablaResultados.jqGrid('getRowData',cl);           
		      			view = "<a href='#'><img border='0' title='Ver' src='/net-theme/images/img-net/file.png'  align='center' onclick=\"fiseGrupoInformacion.verGrupoInformacion('"+ret.idGrupoInformacion+"');\" /></a> ";
		      			edit = "<a href='#'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png'  align='center' onclick=\"fiseGrupoInformacion.editarGrupoInformacion('"+ret.idGrupoInformacion+"');\" /></a> ";
		      			elim = "<a href='#'><img border='0' title='Desactivar' src='/net-theme/images/img-net/bulb-off.png'  align='center' onclick=\"fiseGrupoInformacion.confirmarEliminarGrupoInformacion('"+ret.idGrupoInformacion+"','"+ret.descEstado+"');\" /></a> ";              			
		      			fiseGrupoInformacion.tablaResultados.jqGrid('setRowData',ids[i],{view:view});
		      			fiseGrupoInformacion.tablaResultados.jqGrid('setRowData',ids[i],{edit:edit});
		      			fiseGrupoInformacion.tablaResultados.jqGrid('setRowData',ids[i],{elim:elim});
		      		}
		      }
		  	});
			fiseGrupoInformacion.tablaResultados.jqGrid('navGrid',fiseGrupoInformacion.paginadoResultados,{add:false,edit:false,del:false,search: false,refresh: false});	
			<%-- fiseGrupoInformacion.tablaResultados.jqGrid('navButtonAdd',fiseGrupoInformacion.paginadoResultados,{
			       caption:"Exportar a Excel",
			       buttonicon: "ui-icon-bookmark",
			       onClickButton : function () {
			       location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';  
			       } 
			}); --%>
		},
		//funcion para buscar
		buscarGrupoInformacion : function () {		
			fiseGrupoInformacion.blockUI();
			jQuery.ajax({			
					url: fiseGrupoInformacion.urlBusqueda+'&'+fiseGrupoInformacion.formCommand.serialize(),
					type: 'post',
					dataType: 'json',				
					success: function(gridData) {					
							fiseGrupoInformacion.tablaResultados.clearGridData(true);
							fiseGrupoInformacion.tablaResultados.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
							fiseGrupoInformacion.tablaResultados[0].refreshIndex();
							fiseGrupoInformacion.initBlockUI();
					},error : function(){
						var addhtmError='Error de conexión.';					
						fiseGrupoInformacion.dialogErrorContent.html(addhtmError);
						fiseGrupoInformacion.dialogError.dialog("open");
						fiseGrupoInformacion.initBlockUI();
					}
				});			
		},		
		//funcion para nuevo registro
		<portlet:namespace/>nuevoGrupoInformacion : function(){	
			
			var f = new Date();
			
			fiseGrupoInformacion.f_idGrupoInf.val('');
			fiseGrupoInformacion.f_descripcion.val('');	  
			
			fiseGrupoInformacion.f_anoPres.val(f.getFullYear());
			
			fiseGrupoInformacion.f_mesPres.val(f.getMonth() +1);	
			     	
        	fiseGrupoInformacion.f_descripcion.removeAttr("disabled");
        	fiseGrupoInformacion.f_anoPres.removeAttr("disabled");
        	fiseGrupoInformacion.f_mesPres.removeAttr("disabled");
        	
        	$('#rbtMensual').removeAttr("disabled");
        	$('#rbtBienal').removeAttr("disabled");
        	$('#rbtActivo').removeAttr("disabled");
        	$('#rbtInactivo').removeAttr("disabled");   	
        	
			
			fiseGrupoInformacion.divNuevo.show();
			fiseGrupoInformacion.divBuscar.hide();		
						
			console.debug("boton nuevo registro:  ");
			
			$('#<portlet:namespace/>guardarGrupoInf').css('display','block');
			$('#<portlet:namespace/>actualizarGrupoInf').css('display','none');
			
		},
		
		//Function para Visualizar los datos del formulario		
		verGrupoInformacion : function(id_GrupoInf){	
			$.blockUI({ message: fiseGrupoInformacion.mensajeObteniendoDatos});
			jQuery.ajax({
					url: fiseGrupoInformacion.urlEditarView+'&'+fiseGrupoInformacion.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {						  
					      <portlet:namespace />idGrupoInf: id_GrupoInf					 
						},
					success: function(data) {
					    if (data != null){															
					    	fiseGrupoInformacion.divNuevo.show();
					    	fiseGrupoInformacion.divBuscar.hide();	
					    	
					    	fiseGrupoInformacion.llenarDatosEditar(data);
					    	
					    	fiseGrupoInformacion.initBlockUI();   			
										        	
				        	fiseGrupoInformacion.f_descripcion.attr("disabled",true);
				        	fiseGrupoInformacion.f_anoPres.attr("disabled",true);
				        	fiseGrupoInformacion.f_mesPres.attr("disabled",true);
				        	$('#rbtMensual').attr("disabled",true);
				        	$('#rbtBienal').attr("disabled",true);        	
				        	$('#rbtActivo').attr("disabled",true);
				        	$('#rbtInactivo').attr("disabled",true);
				        	
					    	
					    	$('#<portlet:namespace/>guardarGrupoInf').css('display','none');
					    	$('#<portlet:namespace/>actualizarGrupoInf').css('display','none');
					    							
				        }						
						else{						
							var addhtmError='Error al visualizar los datos del registro seleccionado.';					
							fiseGrupoInformacion.dialogErrorContent.html(addhtmError);
							fiseGrupoInformacion.dialogError.dialog("open");	
							fiseGrupoInformacion.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						fiseGrupoInformacion.dialogErrorContent.html(addhtmError);
						fiseGrupoInformacion.dialogError.dialog("open");
						fiseGrupoInformacion.initBlockUI();
					}
			});	
		},
		
		//Function para editar los datos del formulario
		editarGrupoInformacion : function(id_GrupoInf){	
			    console.debug("entrando a editar ");		
				$.blockUI({ message: fiseGrupoInformacion.mensajeObteniendoDatos });			 
				jQuery.ajax({
						url: fiseGrupoInformacion.urlEditarView+'&'+fiseGrupoInformacion.formCommand.serialize(),
						type: 'post',
						dataType: 'json',
						data: {							
						   <portlet:namespace />idGrupoInf: id_GrupoInf						   				  
						},
						success: function(data) {				
							if (data != null){															
								fiseGrupoInformacion.divNuevo.show();
								fiseGrupoInformacion.divBuscar.hide();	
								
								fiseGrupoInformacion.llenarDatosEditar(data);
								
								fiseGrupoInformacion.initBlockUI();										
									        	
								fiseGrupoInformacion.f_descripcion.removeAttr("disabled");
								fiseGrupoInformacion.f_anoPres.removeAttr("disabled");
								fiseGrupoInformacion.f_mesPres.removeAttr("disabled");
					        	$('#rbtMensual').removeAttr("disabled");
					        	$('#rbtBienal').removeAttr("disabled");
					        	$('#rbtActivo').removeAttr("disabled");
					        	$('#rbtInactivo').removeAttr("disabled"); 
								
								$('#<portlet:namespace/>guardarGrupoInf').css('display','none');
								$('#<portlet:namespace/>actualizarGrupoInf').css('display','block');	
															
					         }
							else{								
								var addhtmError='Error al recuperar los datos del registro seleccionado.';					
								fiseGrupoInformacion.dialogErrorContent.html(addhtmError);
								fiseGrupoInformacion.dialogError.dialog("open");	
								fiseGrupoInformacion.initBlockUI();
							}
						},error : function(){
							var addhtmError='Error de conexión.';					
							fiseGrupoInformacion.dialogErrorContent.html(addhtmError);
							fiseGrupoInformacion.dialogError.dialog("open");
							fiseGrupoInformacion.initBlockUI();
						}
				});		
		},
		
		//funcion  para llenar los campos para editar
		llenarDatosEditar : function(bean){			
			fiseGrupoInformacion.f_idGrupoInf.val(bean.idGrupoInf);
			fiseGrupoInformacion.f_descripcion.val(bean.descripcion); 
			fiseGrupoInformacion.f_anoPres.val(bean.anioPres);
			fiseGrupoInformacion.f_mesPres.val(bean.mesPres);
			
			console.debug("Valor de tipo..:  "+bean.tipo);
			if(bean.tipo=='MENSUAL'){	
				 console.debug("ingresando a mensual");
				 document.getElementById('rbtMensual').checked = true;
			}else{	
				 console.debug("ingresando a mensual");
				 document.getElementById('rbtBienal').checked = true;
			}
			
			console.debug("Valor de estado..:  "+bean.estado);
			if(bean.estado=='1'){	
				 console.debug("ingresando a activo");
				 document.getElementById('rbtActivo').checked = true;
			}else{		
				 console.debug("ingresando a inactivo");
				 document.getElementById('rbtInactivo').checked = true;
			}	
		},
		
		/**Function para confirmar si quiere eliminar el registro o no*/
		confirmarEliminarGrupoInformacion : function(idGrupoInf,desEstado){
			if(desEstado=='Inactivo'){			
				var addhtml2='El registro seleccionado ya se encuentra Desactivado';					
				fiseGrupoInformacion.dialogInfoContent.html(addhtml2);
				fiseGrupoInformacion.dialogInfo.dialog("open");
			}else{
				console.debug("entranado a eliminar confirmar:  "+idGrupoInf);
				var addhtml='¿Está seguro que desea Desactivar el Grupo de Información seleccionado?';
				fiseGrupoInformacion.dialogConfirmContent.html(addhtml);
				fiseGrupoInformacion.dialogConfirm.dialog("open");	
				id_GrupoInf=idGrupoInf;
			}	
		},
		
		/**Function para  eliminar el registro una vez hecho la confirmacion*/
		eliminarGrupoInformacion : function(id_GrupoInf){
			console.debug("entranado a eliminar:  "+id_GrupoInf);
			$.blockUI({ message: fiseGrupoInformacion.mensajeEliminando});
			jQuery.ajax({
				url: fiseGrupoInformacion.urlEliminar+'&'+fiseGrupoInformacion.formCommand.serialize(),
				type: 'post',
				dataType: 'json',
				data: {				
				   <portlet:namespace />idGrupoInf: id_GrupoInf				  
					},
				success: function(data) {
					if (data.resultado == "OK"){
						var addhtml2='El Grupo de Información fue Desactivado satisfactoriamente.';					
						fiseGrupoInformacion.dialogMessageContent.html(addhtml2);
						fiseGrupoInformacion.dialogMessage.dialog("open");
						fiseGrupoInformacion.buscarGrupoInformacion();
						fiseGrupoInformacion.initBlockUI();
					}
					else{						
						var addhtmError='Error al desactivar el Grupo de Información.';					
						fiseGrupoInformacion.dialogErrorContent.html(addhtmError);
						fiseGrupoInformacion.dialogError.dialog("open");	
						fiseGrupoInformacion.initBlockUI();
					}
				},error : function(){
					var addhtmError='Error de conexión.';					
					fiseGrupoInformacion.dialogErrorContent.html(addhtmError);
					fiseGrupoInformacion.dialogError.dialog("open");
					fiseGrupoInformacion.initBlockUI();
				}
			});
		},
		
		//Funcion para Grabar nuevo registro
		<portlet:namespace/>guardarGrupoInformacion: function(){
			if (fiseGrupoInformacion.validarFormulario()){
				$.blockUI({ message: fiseGrupoInformacion.mensajeGuardando});
				 jQuery.ajax({
					 url: fiseGrupoInformacion.urlGrabar+'&'+fiseGrupoInformacion.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {						
						<portlet:namespace />descripcion: fiseGrupoInformacion.f_descripcion.val() 																
						},
					success: function(data) {			
						if (data.resultado == "OK"){				
							var addhtml2='El Grupo de Información se guardó satisfactoriamente.';							
							fiseGrupoInformacion.dialogMessageContent.html(addhtml2);
							fiseGrupoInformacion.dialogMessage.dialog("open");							
							fiseGrupoInformacion.initBlockUI();						
																	        	
							console.debug("Id obtenida de grabar: "+data.IdGrupoInf);
							fiseGrupoInformacion.f_idGrupoInf.val(data.IdGrupoInf);					
							
							$('#<portlet:namespace/>guardarGrupoInf').css('display','none');
							$('#<portlet:namespace/>actualizarGrupoInf').css('display','block');
							
						}else{								
							var addhtmError='Se produjo un error al guardar el Grupo de Información.';					
							fiseGrupoInformacion.dialogErrorContent.html(addhtmError);
							fiseGrupoInformacion.dialogError.dialog("open");											
							fiseGrupoInformacion.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						fiseGrupoInformacion.dialogErrorContent.html(addhtmError);
						fiseGrupoInformacion.dialogError.dialog("open");
						fiseGrupoInformacion.initBlockUI();
					}
				});			
			}
		},
		
		//Funcion para actualizar un registro
		<portlet:namespace/>actualizarGrupoInformacion : function(){
			if (fiseGrupoInformacion.validarFormulario()){
				$.blockUI({ message: fiseGrupoInformacion.mensajeActualizando});
				 jQuery.ajax({
					 url: fiseGrupoInformacion.urlActualizar+'&'+fiseGrupoInformacion.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {
						<portlet:namespace />idGrupoInf: fiseGrupoInformacion.f_idGrupoInf.val(),
						<portlet:namespace />descripcion: fiseGrupoInformacion.f_descripcion.val() 	
						},
					success: function(data) {			
						if (data.resultado == "OK"){				
							var addhtml2='El Grupo de Información se actualizó satisfactoriamente.';
							fiseGrupoInformacion.dialogMessageContent.html(addhtml2);
							fiseGrupoInformacion.dialogMessage.dialog("open");						
							fiseGrupoInformacion.initBlockUI();								
						}else{						
							var addhtmError='Se produjo un error al actualizar el Grupo de Información.';					
							fiseGrupoInformacion.dialogErrorContent.html(addhtmError);
							fiseGrupoInformacion.dialogError.dialog("open");						
							fiseGrupoInformacion.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						fiseGrupoInformacion.dialogErrorContent.html(addhtmError);
						fiseGrupoInformacion.dialogError.dialog("open");
						fiseGrupoInformacion.initBlockUI();
					}
				});						
			}
		},
		
		//funcion para validar ingreso de datos
		validarFormulario : function() {
			console.debug("tamaño de descripcion:  "+fiseGrupoInformacion.f_descripcion.val().length);
			if(fiseGrupoInformacion.f_descripcion.val().length == ''){				
				var addhtmAlert='Debe ingresar descripción.';					
				fiseGrupoInformacion.dialogValidacionContent.html(addhtmAlert);
				fiseGrupoInformacion.dialogValidacion.dialog("open");
				//fiseGrupoInformacion.f_descripcion.focus();
				cod_focus=fiseGrupoInformacion.f_descripcion;
			  	return false; 
			}else if(fiseGrupoInformacion.f_descripcion.val().length > 100){				
				var addhtmAlert='La  descripción acepta como máximo 100 caracteres.';					
				fiseGrupoInformacion.dialogValidacionContent.html(addhtmAlert);
				fiseGrupoInformacion.dialogValidacion.dialog("open");
				//fiseGrupoInformacion.f_descripcion.focus();
				cod_focus=fiseGrupoInformacion.f_descripcion;
			  	return false; 
			}else if(fiseGrupoInformacion.f_anoPres.val().length == ''){				
				var addhtmAlert='Debe ingresar año a declarar.';					
				fiseGrupoInformacion.dialogValidacionContent.html(addhtmAlert);
				fiseGrupoInformacion.dialogValidacion.dialog("open");
				//fiseGrupoInformacion.f_anoPres.focus();
				cod_focus=fiseGrupoInformacion.f_anoPres;
			  	return false; 
			}else if(fiseGrupoInformacion.f_anoPres.val().length != 4){				
				var addhtmAlert='Debe ingresar año a declarar válido.';					
				fiseGrupoInformacion.dialogValidacionContent.html(addhtmAlert);
				fiseGrupoInformacion.dialogValidacion.dialog("open");
				//fiseGrupoInformacion.f_anoPres.focus();
				cod_focus=fiseGrupoInformacion.f_anoPres;
			  	return false; 
			}else if(fiseGrupoInformacion.f_mesPres.val().length == ''){				
				var addhtmAlert='Debe seleccionar mes a declarar.';					
				fiseGrupoInformacion.dialogValidacionContent.html(addhtmAlert);
				fiseGrupoInformacion.dialogValidacion.dialog("open");
				//fiseGrupoInformacion.f_mesPres.focus();
				cod_focus=fiseGrupoInformacion.f_mesPres;
			  	return false; 
			}else{
				return true;
			}		
		},
		
		ponerFocus : function(cadena){		
			cadena.focus();
		},	
		
		//funcion para regresar
		<portlet:namespace/>regresarGrupoInformacion : function(){			
			fiseGrupoInformacion.divNuevo.hide();
			fiseGrupoInformacion.divBuscar.show();					
			fiseGrupoInformacion.buscarGrupoInformacion();
		},
		
		//DIALOGOS
		initDialogs : function(){	
			fiseGrupoInformacion.dialogMessage.dialog({
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
			fiseGrupoInformacion.dialogConfirm.dialog({
				modal: true,
				height: 200,
				width: 450,			
				autoOpen: false,
				buttons: {
					"Si": function() {
						fiseGrupoInformacion.eliminarGrupoInformacion(id_GrupoInf);
						$( this ).dialog("close");
					},
					"No": function() {				
						$( this ).dialog("close");
					}
				}
			});	
			
			fiseGrupoInformacion.dialogValidacion.dialog({
				modal: true,
				autoOpen: false,
				width: 450,		
				buttons: {
					Aceptar: function() {
						fiseGrupoInformacion.ponerFocus(cod_focus);
						$( this ).dialog("close");
					}
				}
			});
			
			fiseGrupoInformacion.dialogError.dialog({
				modal: true,
				autoOpen: false,
				width: 450,		
				buttons: {
					Aceptar: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			fiseGrupoInformacion.dialogInfo.dialog({
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