<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects />

<script type="text/javascript">

var periodoEnvio= {
		
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
		i_codEmpresaBusq:null,
		i_anioDesde:null,
		i_mesDesde:null,	
		i_formatoBusq:null,
		i_etapaBusq:null,
		i_flagEnvioBusq:null,
		i_estadoBusq:null,
		
		//variables para nuevo registro
		f_codEmpresa:null,f_anoPres:null,f_mesPres:null,f_formato:null,f_etapa:null,f_estado:null,f_desde:null,
		f_hasta:null,f_dias:null,f_flagEnvioObs:null,f_flagAnoMes:null,	f_flagCosto:null,
		f_anoIniVigencia:null,f_anoFinVigencia:null,
		f_secuencia:null,
		//grillas
		tablaResultados:null,
		paginadoResultados:null,	
		
		
		
		init : function() {
			
			this.formCommand=$('#periodoEnvioBean');
			
			//divs
			this.divBuscar=$("#<portlet:namespace/>div_buscar");
			this.divNuevo=$("#<portlet:namespace/>div_nuevo");			
			
			
			//dialogos		
			this.dialogMessage=$("#<portlet:namespace/>dialog-message-grabar");//para guardar y actualizar
			this.dialogMessageContent=$("#<portlet:namespace/>dialog-message-content-grabar");//para guardar y actualizar
			this.dialogConfirm=$("#<portlet:namespace/>dialog-confirm");//para eliminar
			this.dialogConfirmContent=$("#<portlet:namespace/>dialog-confirm-content");//para eliminar
			
			
			//mensajes			
			this.mensajeCargando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Cargando </h3>';
			this.mensajeObteniendoDatos='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Obteniendo Datos </h3>';
			this.mensajeEliminando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Eliminando </h3>';
			this.mensajeGuardando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Guardando Datos </h3>';
			this.mensajeActualizando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Actualizando Datos </h3>';
			
			
			//urls
			this.urlBusqueda='<portlet:resourceURL id="busquedaPeriodoEnvio" />';			
			this.urlGrabar='<portlet:resourceURL id="grabarPeriodoEnvio" />';			
			this.urlActualizar='<portlet:resourceURL id="actualizarPeriodoEnvio" />';
			this.urlEditarView='<portlet:resourceURL id="editarViewPeriodoEnvio" />';			
			this.urlEliminar='<portlet:resourceURL id="eliminarPeriodoEnvio" />';
			
			//botones
			this.botonBuscar=$("#<portlet:namespace/>btnBuscarPerdioEnvio");
			this.botonNuevo=$("#<portlet:namespace/>btnNuevoPeridoEnvio");
			this.botonRegresar=$("#<portlet:namespace/>regresarPeriodoEnvio");			
			this.botonGrabar=$("#<portlet:namespace/>guardarPeriodoEnvio");
			this.botonActualizar=$("#<portlet:namespace/>actualizarPeriodoEnvio");		
			
			//variables de busqueda
			this.i_codEmpresaBusq=$('#codEmpresaBusq');
			this.i_anioDesde=$('#anioDesde');
			this.i_mesDesde=$('#mesDesde');
			this.i_formatoBusq=$('#formatoBusq');
			this.i_etapaBusq=$('#etapaBusq');
			this.i_flagEnvioBusq=$('#flagEnvioBusq');
			this.i_estadoBusq=$('#estadoBusq');	
			
			//VARIBALES PARA NUEVO REGISTRO	
			this.f_secuencia=$('#secuencial');
			
			this.f_codEmpresa=$('#codEmpresa');
			this.f_anoPres=$('#anioPres');	
			this.f_mesPres=$("#mesPres");
			this.f_formato=$('#formato');
			this.f_etapa=$('#etapa');
			this.f_estado=$('#estado');
			this.f_desde=$('#desde');
			this.f_hasta=$('#hasta');
			this.f_dias=$('#diasNotifCierre');
			this.f_flagEnvioObs=$('#flagEnvioObs');	
			this.f_flagAnoMes=$('#flagAnioMesEjec');	
			this.f_flagCosto=$('#flagHabCostos');
			this.f_anoIniVigencia=$('#anoIniVigencia');	
			this.f_anoFinVigencia=$('#anoFinVigencia');
			
			
			//grillas			
			this.tablaResultados=$("#<portlet:namespace/>grid_resultado_busqueda");
			this.paginadoResultados='#<portlet:namespace/>paginador_resultado_busqueda';
			this.buildGrids();	//cargar el modelo de la grilla	
			
			
			
			//llamado a la funciones de cada boton
			periodoEnvio.botonBuscar.click(function() {
				periodoEnvio.buscarPeriodoEnvio();
			});			
			
			 periodoEnvio.botonNuevo.click(function() {
				periodoEnvio.<portlet:namespace/>nuevoPeriodoEnvio();
		    });
			
			periodoEnvio.botonGrabar.click(function() {
				periodoEnvio.<portlet:namespace/>guardarPeriodoEnvio();
			});
			
			periodoEnvio.botonActualizar.click(function() {
				periodoEnvio.<portlet:namespace/>actualizarPeriodoEnvio();
			});		
			
			
		    periodoEnvio.botonRegresar.click(function() {
		    	periodoEnvio.<portlet:namespace/>regresarPeriodoEnvio();
		    });			
		    
		    periodoEnvio.initDialogs();
		    
		    //eventos por defecto		   
			periodoEnvio.botonBuscar.trigger('click');
			periodoEnvio.initBlockUI();
		},
		//funcion para armar el modelo de la grilla para el resultado
		buildGrids : function () {	
			periodoEnvio.tablaResultados.jqGrid({
			   datatype: "local",
		       colNames: ['Secuencia','Empresa.','Formato.','Año Pres.','Mes Pres.','Estado','Visualizar','Editar','Anular'],
		       colModel: [
                       { name: 'secuencia', index: 'secuencia', width: 20},
					   { name: 'descEmpresa', index: 'descEmpresa', width: 50},
					   { name: 'formato', index: 'formato', width: 30},
		               { name: 'anoPresentacion', index: 'anoPresentacion', width: 30 },   
		               { name: 'descMesPresentacion', index: 'descMesPresentacion', width: 30},	              
		               { name: 'estado', index: 'estado', width: 50,align:'center'},
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
					pager: periodoEnvio.paginadoResultados,
				    viewrecords: true,
				   	caption: "Peridos de Envio",
				    sortorder: "asc",	   	    	   	   
		       gridComplete: function(){
		      		var ids = periodoEnvio.tablaResultados.jqGrid('getDataIDs');
		      		for(var i=0;i < ids.length;i++){
		      			var cl = ids[i];
		      			var ret = periodoEnvio.tablaResultados.jqGrid('getRowData',cl);           
		      			view = "<a href='#'><img border='0' title='View' src='/net-theme/images/img-net/file.png'  align='center' onclick=\"periodoEnvio.verPeriodoEnvio('"+ret.secuencia+"');\" /></a> ";
		      			edit = "<a href='#'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png'  align='center' onclick=\"periodoEnvio.editarPeriodoEnvio('"+ret.secuencia+"');\" /></a> ";
		      			elim = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"periodoEnvio.confirmarEliminarPeriodoEnvio('"+ret.secuencia+"');\" /></a> ";              			
		      			periodoEnvio.tablaResultados.jqGrid('setRowData',ids[i],{view:view});
		      			periodoEnvio.tablaResultados.jqGrid('setRowData',ids[i],{edit:edit});
		      			periodoEnvio.tablaResultados.jqGrid('setRowData',ids[i],{elim:elim});
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
		//funcion para buscar
		buscarPeriodoEnvio : function () {		
			periodoEnvio.blockUI();
			jQuery.ajax({			
					url: periodoEnvio.urlBusqueda+'&'+periodoEnvio.formCommand.serialize(),
					type: 'post',
					dataType: 'json',				
					success: function(gridData) {					
							periodoEnvio.tablaResultados.clearGridData(true);
							periodoEnvio.tablaResultados.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
							periodoEnvio.tablaResultados[0].refreshIndex();
							periodoEnvio.initBlockUI();
					},error : function(){
							alert("Error de conexión.");
							periodoEnvio.initBlockUI();
					}
				});			
		},		
		//funcion para nuevo registro
		<portlet:namespace/>nuevoPeriodoEnvio : function(){	
			
			periodoEnvio.inicializarFormulario();	
			
			periodoEnvio.divNuevo.show();
			periodoEnvio.divBuscar.hide();		
						
			console.debug("boton nuevo registro:  ");
			
			$('#<portlet:namespace/>guardarPeriodoEnvio').css('display','block');
			$('#<portlet:namespace/>actualizarPeriodoEnvio').css('display','none');
			
		},
		
		//function para inicializar el formulario
		inicializarFormulario : function(){	
			periodoEnvio.f_secuencia.val('');
			
			periodoEnvio.f_codEmpresa.val('');
			var f = new Date();
			periodoEnvio.f_anoPres.val(f.getFullYear());
			periodoEnvio.f_mesPres.val(f.getMonth() +1);
			periodoEnvio.f_formato.val('F14A');
			periodoEnvio.f_etapa.val('SOLICITUD');
			periodoEnvio.f_estado.val('V');
			periodoEnvio.f_desde.val('');
			periodoEnvio.f_hasta.val('');
			periodoEnvio.f_dias.val('1');
			
			/*$('#rbtEnvioSi').removeAttr("disabled");
        	$('#rbtEnvioNo').removeAttr("disabled");
        	
        	$('#rbtMesSi').removeAttr("disabled");
        	$('#rbtMesNo').removeAttr("disabled");
        	
        	$('#rbtAmbos').removeAttr("disabled");
        	$('#rbtDirecto').removeAttr("disabled");
        	$('#rbtIndirecto').removeAttr("disabled");*/
			
			periodoEnvio.f_anoIniVigencia.val(f.getFullYear());
			periodoEnvio.f_anoFinVigencia.val(f.getFullYear()+2);
			
			periodoEnvio.habilitarCamposEditar();
			
			periodoEnvio.habilitarCamposVisualizar();
		},		
		
		//Function para Visualizar los datos del formulario		
		verPeriodoEnvio : function(secuencial){	
			$.blockUI({ message: periodoEnvio.mensajeObteniendoDatos});
			jQuery.ajax({
					url: periodoEnvio.urlEditarView+'&'+periodoEnvio.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {						  
					      <portlet:namespace />secuencial: secuencial					 
						},
					success: function(data) {
					    if (data != null){															
					    	periodoEnvio.divNuevo.show();
					    	periodoEnvio.divBuscar.hide();	
					    	
					    	periodoEnvio.llenarDatosEditar(data);
					    	
					    	periodoEnvio.initBlockUI();	
					    	
					    	periodoEnvio.deshabilitarCamposEditar();
					    	
					    	periodoEnvio.deshabilitarCamposVisualizar();
					    	
					    	$('#<portlet:namespace/>guardarPeriodoEnvio').css('display','none');
					    	$('#<portlet:namespace/>actualizarPeriodoEnvio').css('display','none');
					    							
				        }						
						else{
							alert("Error al visualizar los datos del registro seleccionado");
							periodoEnvio.initBlockUI();
						}
					},error : function(){
						alert("Error de conexión.");
						periodoEnvio.initBlockUI();
					}
			});	
		},
		//Function para editar los datos del formulario
		editarPeriodoEnvio : function(secuencial){	
			    console.debug("entrando a editar ");		
				$.blockUI({ message: periodoEnvio.mensajeObteniendoDatos });			 
				jQuery.ajax({
						url: periodoEnvio.urlEditarView+'&'+periodoEnvio.formCommand.serialize(),
						type: 'post',
						dataType: 'json',
						data: {							
						   <portlet:namespace />secuencial: secuencial						   				  
						},
						success: function(data) {				
							if (data != null){															
								periodoEnvio.divNuevo.show();
								periodoEnvio.divBuscar.hide();	
								
								periodoEnvio.llenarDatosEditar(data);
								
								periodoEnvio.initBlockUI();
								
								periodoEnvio.deshabilitarCamposEditar();
								
								periodoEnvio.habilitarCamposVisualizar();
								
								$('#<portlet:namespace/>guardarPeriodoEnvio').css('display','none');
								$('#<portlet:namespace/>actualizarPeriodoEnvio').css('display','block');	
								//ESTILOS								
					         }
							else{
								alert("Error al recuperar los datos del registro seleccionado");
								periodoEnvio.initBlockUI();
							}
						},error : function(){
							alert("Error de conexión.");
							periodoEnvio.initBlockUI();
						}
				});		
		},
		//funcion  para llenar los campos para editar
		llenarDatosEditar : function(bean){			
            periodoEnvio.f_secuencia.val(bean.secuencial);			
			periodoEnvio.f_codEmpresa.val(bean.codEmpresa);			
			periodoEnvio.f_anoPres.val(bean.anioPres);
			periodoEnvio.f_mesPres.val(bean.mesPres);
			periodoEnvio.f_formato.val(bean.formato);
			periodoEnvio.f_etapa.val(bean.etapa);
			periodoEnvio.f_estado.val(bean.estado);
			periodoEnvio.f_desde.val(bean.desde);
			periodoEnvio.f_hasta.val(bean.hasta);
			periodoEnvio.f_dias.val(bean.diasNotifCierre);
			
			console.debug("Valor del flag mostrar anio y mes pres..:  "+bean.flagAnioMesEjec);
			 if(bean.flagAnioMesEjec=='S'){
				// $('#rbtSi').checked = true;
				 document.getElementById('rbtMesSi').checked = true;
			}else{
				// $('#rbtNo').checked = true;
				 document.getElementById('rbtMesNo').checked = true;
			}
			
			console.debug("Valor del flag envio obs..:  "+bean.flagEnvioObs);
			if(bean.flagEnvioObs=='S'){
				// $('#rbtSi').checked = true;
				 document.getElementById('rbtEnvioSi').checked = true;
			}else{
				// $('#rbtNo').checked = true;
				 document.getElementById('rbtEnvioNo').checked = true;
			}
			
			console.debug("Valor del flag costos..:  "+bean.flagHabCostos);
			if(bean.flagHabCostos=='D'){
				// $('#rbtSi').checked = true;
				 document.getElementById('rbtDirecto').checked = true;
			}else if(bean.flagHabCostos=='I'){
				// $('#rbtNo').checked = true;
				 document.getElementById('rbtIndirecto').checked = true;
			}else{
				// $('#rbtNo').checked = true;
				 document.getElementById('rbtAmbos').checked = true;
			}			
			periodoEnvio.f_anoIniVigencia.val(bean.anoIniVigencia);
			periodoEnvio.f_anoFinVigencia.val(bean.anoFinVigencia);
		},
		/**Function para confirmar si quiere eliminar el registro o no*/
		confirmarEliminarPeriodoEnvio : function(secuencia){
			console.debug("entranado a eliminar confirmar:  "+secuencia);
			var addhtml='¿Está seguro que desea eliminar el registro seleccionado?';
			periodoEnvio.dialogConfirmContent.html(addhtml);
			periodoEnvio.dialogConfirm.dialog("open");	
			cod_secuencia=secuencia;
		},
		/**Function para  eliminar el registro una vez hecho la confirmacion*/
		eliminarPeriodoEnvio : function(cod_secuencia){
			console.debug("entranado a eliminar:  "+cod_secuencia);
			$.blockUI({ message: periodoEnvio.mensajeEliminando });
			jQuery.ajax({
				url: periodoEnvio.urlEliminar+'&'+periodoEnvio.formCommand.serialize(),
				type: 'post',
				dataType: 'json',
				data: {				
				   <portlet:namespace />secuencial: cod_secuencia				  
					},
				success: function(data) {
					if (data.resultado == "OK"){
						var addhtml2='Registro eliminado con éxito';					
						periodoEnvio.dialogMessageContent.html(addhtml2);
						periodoEnvio.dialogMessage.dialog("open");
						periodoEnvio.buscarPeriodoEnvio();
						periodoEnvio.initBlockUI();
					}
					else{
						alert("Error al eliminar el registro");
						periodoEnvio.initBlockUI();
					}
				},error : function(){
					alert("Error de conexión.");
					periodoEnvio.initBlockUI();
				}
			});
		},
		//Funcion para Grabar nuevo registro
		<portlet:namespace/>guardarPeriodoEnvio: function(){
			if (periodoEnvio.validarFormulario()){
				$.blockUI({ message: periodoEnvio.mensajeGuardando });
				 jQuery.ajax({
					 url: periodoEnvio.urlGrabar+'&'+periodoEnvio.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {											
						<portlet:namespace />diasNotifCierre: periodoEnvio.f_dias.val(),
						<portlet:namespace />hasta: periodoEnvio.f_hasta.val(), 
						<portlet:namespace />desde: periodoEnvio.f_desde.val()										
						},
					success: function(data) {			
						if (data.resultado == "OK"){				
							var addhtml2='Datos guardados satisfactoriamente';
							periodoEnvio.dialogMessageContent.html(addhtml2);
							periodoEnvio.dialogMessage.dialog("open");							
							periodoEnvio.initBlockUI();	
							//asigno el valor del id = secuencia al hidden
							console.debug("secuencia obtenida de grabar: "+data.secuencia);
							periodoEnvio.f_secuencia.val(data.secuencia);
							periodoEnvio.deshabilitarCamposEditar();							
							$('#<portlet:namespace/>guardarPeriodoEnvio').css('display','none');
							$('#<portlet:namespace/>actualizarPeriodoEnvio').css('display','block');
						}else if(data.resultado == "Error"){				
							var addhtml2='Se produjo un error al guardar los datos.';
							periodoEnvio.dialogMessageContent.html(addhtml2);
							periodoEnvio.dialogMessage.dialog("open");						
							periodoEnvio.initBlockUI();
						}
					},error : function(){
						alert("Error de conexión.");
						periodoEnvio.initBlockUI();
					}
				});			
			}
		},
		//Funcion para actualizar un registro
		<portlet:namespace/>actualizarPeriodoEnvio : function(){
			if (periodoEnvio.validarFormulario()){
				$.blockUI({ message: periodoEnvio.mensajeActualizando });
				 jQuery.ajax({
					 url: periodoEnvio.urlActualizar+'&'+periodoEnvio.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {
						<portlet:namespace />secuencial: periodoEnvio.f_secuencia.val(),
						<portlet:namespace />diasNotifCierre: periodoEnvio.f_dias.val(),
						<portlet:namespace />hasta: periodoEnvio.f_hasta.val(), 
						<portlet:namespace />desde: periodoEnvio.f_desde.val()		
						},
					success: function(data) {			
						if (data.resultado == "OK"){				
							var addhtml2='Datos actualizados satisfactoriamente';
							periodoEnvio.dialogMessageContent.html(addhtml2);
							periodoEnvio.dialogMessage.dialog("open");						
							periodoEnvio.initBlockUI();								
						}else if(data.resultado == "Error"){				
							var addhtml2='Se produjo un error al actualizar los datos.';
							periodoEnvio.dialogMessageContent.html(addhtml2);
							periodoEnvio.dialogMessage.dialog("open");						
							periodoEnvio.initBlockUI();
						}
					},error : function(){
						alert("Error de conexión.");
						periodoEnvio.initBlockUI();
					}
				});						
			}
		},
		//funcion para validar ingreso de datos
		validarFormulario : function() {			
			if(periodoEnvio.f_codEmpresa.val().length == ''){
				alert('Seleccione una empresa'); 
				periodoEnvio.f_codEmpresa.focus();
			  	return false; 
			}else if(periodoEnvio.f_anoPres.val().length == ''){
				alert('Debe ingresar año de presentacion'); 
				periodoEnvio.f_anoPres.focus();
			  	return false; 
			}else if(periodoEnvio.f_mesPres.val().length == ''){
				alert('Debe seleccionar mes de presentacion'); 
				periodoEnvio.f_mesPres.focus();
			  	return false; 
			}else if(periodoEnvio.f_formato.val().length == ''){
				alert('Debe seleccionar un formato'); 
				periodoEnvio.f_formato.focus();
			  	return false; 
			}else if(periodoEnvio.f_etapa.val().length == ''){
				alert('Debe seleccionar una etapa'); 
				periodoEnvio.f_etapa.focus();
			  	return false; 
			}else if(periodoEnvio.f_estado.val().length == ''){
				alert('Debe seleccionar un estado'); 
				periodoEnvio.f_estado.focus();
			  	return false; 
			}else if(periodoEnvio.f_desde.val().length == ''){
				alert('Debe ingresar una fecha desde'); 
				periodoEnvio.f_desde.focus();
			  	return false; 
			}else if(!validaFechaDDMMAAAA(periodoEnvio.f_desde.val())){
				alert('Debe ingresar una fecha desde valida'); 
				periodoEnvio.f_desde.focus();
			  	return false; 
			}else if(periodoEnvio.f_hasta.val().length == ''){
				alert('Debe ingresar una fecha hasta'); 
				periodoEnvio.f_hasta.focus();
			  	return false; 
			}else if(!validaFechaDDMMAAAA(periodoEnvio.f_hasta.val())){
				alert('Debe ingresar una fecha hasta valida'); 
				periodoEnvio.f_desde.focus();
			  	return false; 
			}else if(periodoEnvio.f_dias.val().length == ''){
				alert('Debe ingresar el numero de dias de notificacion antes del cierre'); 
				periodoEnvio.f_dias.focus();
			  	return false; 
			}else if(periodoEnvio.f_anoIniVigencia.val().length == ''){
				alert('Debe ingresar el año de inicio de vigencia'); 
				periodoEnvio.f_anoIniVigencia.focus();
			  	return false; 
			}else if(periodoEnvio.f_anoFinVigencia.val().length == ''){
				alert('Debe ingresar el año fin de vigencia'); 
				periodoEnvio.f_anoFinVigencia.focus();
			  	return false; 
			}else{
				return true;
			}		
		},
		
		//funcion para validar formulario de ingreso y actualizacion
        habilitarCamposEditar : function(){        
        	periodoEnvio.f_codEmpresa.removeAttr("disabled");
			periodoEnvio.f_anoPres.removeAttr("disabled");
			periodoEnvio.f_mesPres.removeAttr("disabled");
			periodoEnvio.f_formato.removeAttr("disabled");
			periodoEnvio.f_etapa.removeAttr("disabled");
		},
		
		deshabilitarCamposEditar : function(){		
			periodoEnvio.f_codEmpresa.attr("disabled",true);
        	periodoEnvio.f_anoPres.attr("disabled",true);
        	periodoEnvio.f_mesPres.attr("disabled",true);
        	periodoEnvio.f_formato.attr("disabled",true);
        	periodoEnvio.f_etapa.attr("disabled",true);
		},	
		
		//funcion para validar formulario de ingreso y actualizacion
        habilitarCamposVisualizar : function(){  		
			periodoEnvio.f_estado.removeAttr("disabled");
        	periodoEnvio.f_desde.removeAttr("disabled");
        	periodoEnvio.f_hasta.removeAttr("disabled");
        	periodoEnvio.f_dias.removeAttr("disabled"); 
        	
        	$('#rbtEnvioSi').removeAttr("disabled");
        	$('#rbtEnvioNo').removeAttr("disabled");
        	
        	$('#rbtMesSi').removeAttr("disabled");
        	$('#rbtMesNo').removeAttr("disabled");
        	
        	$('#rbtAmbos').removeAttr("disabled");
        	$('#rbtDirecto').removeAttr("disabled");
        	$('#rbtIndirecto').removeAttr("disabled");      	
        	
        	periodoEnvio.f_anoIniVigencia.removeAttr("disabled");
        	periodoEnvio.f_anoFinVigencia.removeAttr("disabled"); 
			
		},		
		deshabilitarCamposVisualizar : function(){		
			periodoEnvio.f_estado.attr("disabled",true);
        	periodoEnvio.f_desde.attr("disabled",true);
        	periodoEnvio.f_hasta.attr("disabled",true);
        	periodoEnvio.f_dias.attr("disabled",true);
        	
        	$('#rbtEnvioSi').attr("disabled",true);
        	$('#rbtEnvioNo').attr("disabled",true);
        	
        	$('#rbtMesSi').attr("disabled",true);
        	$('#rbtMesNo').attr("disabled",true);
        	
        	$('#rbtAmbos').attr("disabled",true);
        	$('#rbtDirecto').attr("disabled",true);
        	$('#rbtIndirecto').attr("disabled",true);    
        	
        	periodoEnvio.f_anoIniVigencia.attr("disabled",true);
        	periodoEnvio.f_anoFinVigencia.attr("disabled",true);       	
		},		
		
		
		
		//funcion para regresar
		<portlet:namespace/>regresarPeriodoEnvio : function(){			
			periodoEnvio.divNuevo.hide();
			periodoEnvio.divBuscar.show();	
			
			//periodoEnvio.habilitarCamposView();
				
			periodoEnvio.botonBuscar.trigger('click');
		},
		
		//DIALOGOS
		initDialogs : function(){	
			periodoEnvio.dialogMessage.dialog({
				modal: true,
				autoOpen: false,
				buttons: {
					Ok: function() {
						$( this ).dialog("close");
					}
				}
			});
			//dialogo para eliminar registro
			periodoEnvio.dialogConfirm.dialog({
				modal: true,
				height: 200,
				width: 400,			
				autoOpen: false,
				buttons: {
					"Si": function() {
						periodoEnvio.eliminarPeriodoEnvio(cod_secuencia);
						$( this ).dialog("close");
					},
					"No": function() {				
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