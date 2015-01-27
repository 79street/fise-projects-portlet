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
		f_secuencia:null,f_fechaAmpl:null,
	
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
			this.f_fechaAmpl=$('#fechaAmpl');
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
		    
		    //eventos
		    periodoEnvio.f_formato.change(function(){
		    	periodoEnvio.<portlet:namespace/>mostrarAnioVigencia();
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
		       colNames: ['Secuencia','Dist. Eléct.','Formato','Año Decl.','Mes Decl.','Estado','Ver','Editar','Desactivar'],
		       colModel: [
                       { name: 'secuencia', index: 'secuencia', width: 20},
					   { name: 'descEmpresa', index: 'descEmpresa', width: 80},
					   { name: 'formato', index: 'formato', width: 30},
		               { name: 'anoPresentacion', index: 'anoPresentacion', width: 30 },   
		               { name: 'descMesPresentacion', index: 'descMesPresentacion', width: 30},	              
		               { name: 'descEstado', index: 'descEstado', width: 20,align:'center'},
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
					pager: periodoEnvio.paginadoResultados,
				    viewrecords: true,
				   	caption: "Resultado(s) de la búsqueda",
				    sortorder: "asc",	   	    	   	   
		       gridComplete: function(){
		      		var ids = periodoEnvio.tablaResultados.jqGrid('getDataIDs');
		      		for(var i=0;i < ids.length;i++){
		      			var cl = ids[i];
		      			var ret = periodoEnvio.tablaResultados.jqGrid('getRowData',cl);           
		      			view = "<a href='#'><img border='0' title='Ver' src='/net-theme/images/img-net/file.png'  align='center' onclick=\"periodoEnvio.verPeriodoEnvio('"+ret.secuencia+"');\" /></a> ";
		      			edit = "<a href='#'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png'  align='center' onclick=\"periodoEnvio.editarPeriodoEnvio('"+ret.secuencia+"');\" /></a> ";
		      			elim = "<a href='#'><img border='0' title='Desactivar' src='/net-theme/images/img-net/bulb-off.png'  align='center' onclick=\"periodoEnvio.confirmarEliminarPeriodoEnvio('"+ret.secuencia+"','"+ret.descEstado+"');\" /></a> ";              			
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
			    	   var ids = periodoEnvio.tablaResultados.jqGrid('getDataIDs');
				       if(ids!=0){
				    		  location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';   
				       }else{
				    	var addhtmInfo='No existe información para exportar a Excel';				
				    	periodoEnvio.dialogInfoContent.html(addhtmInfo);
				    	periodoEnvio.dialogInfo.dialog("open");
				       }		         
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
							var addhtmError='Error de conexión.';					
							periodoEnvio.dialogErrorContent.html(addhtmError);
							periodoEnvio.dialogError.dialog("open");	
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
			periodoEnvio.f_formato.trigger('change');
			$('#<portlet:namespace/>guardarPeriodoEnvio').css('display','block');
			$('#<portlet:namespace/>actualizarPeriodoEnvio').css('display','none');
			
		},
		
		//function para inicializar el formulario
		inicializarFormulario : function(){			
			var f = new Date();
			periodoEnvio.f_secuencia.val('');
			if(periodoEnvio.i_codEmpresaBusq.val()!=''){
				periodoEnvio.f_codEmpresa.val(periodoEnvio.i_codEmpresaBusq.val());	
			}else{
				periodoEnvio.f_codEmpresa.val('');
			}
			
			if(periodoEnvio.i_anioDesde.val()!=''){
				periodoEnvio.f_anoPres.val(periodoEnvio.i_anioDesde.val());	
			}else{
				periodoEnvio.f_anoPres.val(f.getFullYear());
			}
			
			if(periodoEnvio.i_mesDesde.val()!=''){
				periodoEnvio.f_mesPres.val(periodoEnvio.i_mesDesde.val());
			}else{
				console.debug("mes actual :  "+f.getMonth());
				periodoEnvio.f_mesPres.val(f.getMonth() +1);	
			}
			
			if(periodoEnvio.i_formatoBusq.val()!=''){
				periodoEnvio.f_formato.val(periodoEnvio.i_formatoBusq.val());
			}else{
				periodoEnvio.f_formato.val('F12A');
			}
			
			if(periodoEnvio.i_etapaBusq.val()!=''){
				periodoEnvio.f_etapa.val(periodoEnvio.i_etapaBusq.val());
			}else{
				periodoEnvio.f_etapa.val('SOLICITUD');
			}		
			periodoEnvio.f_estado.val('V');
			periodoEnvio.f_desde.val('');
			periodoEnvio.f_hasta.val('');
			periodoEnvio.f_dias.val('3');
			periodoEnvio.f_fechaAmpl.val('');
			
			periodoEnvio.habilitarCamposEditar();			
			periodoEnvio.habilitarCamposVisualizar();
			
			console.debug("id formato al hacer nuevo:  "+periodoEnvio.f_formato.val());			 
			var idFormato = periodoEnvio.f_formato.val();
			if(periodoEnvio.verificarFormato(idFormato)==false){
				 console.debug("formato es 12  ");	
				 periodoEnvio.f_anoIniVigencia.val('');
				 periodoEnvio.f_anoFinVigencia.val('');
				 periodoEnvio.f_anoIniVigencia.attr("disabled",true);
				 periodoEnvio.f_anoFinVigencia.attr("disabled",true);
			 }else{
				 console.debug("formato es diferente de 12  ");
				 periodoEnvio.f_anoIniVigencia.removeAttr("disabled");
				 periodoEnvio.f_anoFinVigencia.removeAttr("disabled");
				 periodoEnvio.f_anoIniVigencia.val(f.getFullYear()+1);
				 periodoEnvio.f_anoFinVigencia.val(f.getFullYear()+3);				
			 }
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
					    	
					    	periodoEnvio.initBlockUI();	
					    	
					    	periodoEnvio.deshabilitarCamposEditar();
					    	
					    	periodoEnvio.deshabilitarCamposVisualizar();
					    	
					    	periodoEnvio.llenarDatosEditar(data);
					    	
					    	$('#<portlet:namespace/>guardarPeriodoEnvio').css('display','none');
					    	$('#<portlet:namespace/>actualizarPeriodoEnvio').css('display','none');
					    							
				        }						
						else{							
							var addhtmError='Error al visualizar los datos del registro seleccionado.';					
							periodoEnvio.dialogErrorContent.html(addhtmError);
							periodoEnvio.dialogError.dialog("open");	
							periodoEnvio.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						periodoEnvio.dialogErrorContent.html(addhtmError);
						periodoEnvio.dialogError.dialog("open");
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
								
								periodoEnvio.initBlockUI();
								
								periodoEnvio.deshabilitarCamposEditar();
								
								periodoEnvio.habilitarCamposVisualizar();
								
								periodoEnvio.llenarDatosEditar(data);
								
								$('#<portlet:namespace/>guardarPeriodoEnvio').css('display','none');
								$('#<portlet:namespace/>actualizarPeriodoEnvio').css('display','block');	
								//ESTILOS								
					         }
							else{								
								var addhtmError='Error al recuperar los datos del registro seleccionado.';					
								periodoEnvio.dialogErrorContent.html(addhtmError);
								periodoEnvio.dialogError.dialog("open");
								periodoEnvio.initBlockUI();
							}
						},error : function(){
							var addhtmError='Error de conexión.';					
							periodoEnvio.dialogErrorContent.html(addhtmError);
							periodoEnvio.dialogError.dialog("open");
							periodoEnvio.initBlockUI();
						}
				});		
		},
		//funcion  para llenar los campos para editar
		llenarDatosEditar : function(bean){			
            periodoEnvio.f_secuencia.val(bean.secuencial);			
			periodoEnvio.f_codEmpresa.val(bean.codEmpresa.trim());			
			periodoEnvio.f_anoPres.val(bean.anioPres);
			periodoEnvio.f_mesPres.val(bean.mesPres);
			periodoEnvio.f_formato.val(bean.formato);
			//periodoEnvio.f_etapa.val(bean.etapa);
			dwr.util.removeAllOptions("etapa");				
			var dataEtapa = [bean.etapa];			
			dwr.util.addOptions("etapa", dataEtapa);
			
			periodoEnvio.f_estado.val(bean.estado);
			periodoEnvio.f_desde.val(bean.desde);
			periodoEnvio.f_hasta.val(bean.hasta);
			periodoEnvio.f_dias.val(bean.diasNotifCierre);
			periodoEnvio.f_fechaAmpl.val(bean.fechaAmpl);
			
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
			
			console.debug("id formato al hacer editar o visualizar:  "+bean.formato);			
			if(periodoEnvio.verificarFormato(bean.formato)==false){
				 periodoEnvio.f_anoIniVigencia.val('');
				 periodoEnvio.f_anoFinVigencia.val('');
				 periodoEnvio.f_anoIniVigencia.attr("disabled",true);
				 periodoEnvio.f_anoFinVigencia.attr("disabled",true);
			 }else{
				 periodoEnvio.f_anoIniVigencia.removeAttr("disabled");
				 periodoEnvio.f_anoFinVigencia.removeAttr("disabled");
				 periodoEnvio.f_anoIniVigencia.val(bean.anoIniVigencia);
				 periodoEnvio.f_anoFinVigencia.val(bean.anoFinVigencia);				
			 }
		},
		/**Function para confirmar si quiere eliminar el registro o no*/
		confirmarEliminarPeriodoEnvio : function(secuencia,estado){
			if(estado=='Vigente'){
				console.debug("entranado a eliminar confirmar:  "+secuencia);
				var addhtml='¿Está seguro que desea Desactivar el Control de Remisión seleccionado.?';
				periodoEnvio.dialogConfirmContent.html(addhtml);
				periodoEnvio.dialogConfirm.dialog("open");	
				cod_secuencia=secuencia;	
			}else{				
				var addhtmInfo='El registro seleccionado del Control de Remisión ya se encuentra Desactivado.';					
				periodoEnvio.dialogInfoContent.html(addhtmInfo);
				periodoEnvio.dialogInfo.dialog("open");				
			}			
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
						var addhtml2='El Control de Remisión fue Desactivado satisfactoriamente';					
						periodoEnvio.dialogMessageContent.html(addhtml2);
						periodoEnvio.dialogMessage.dialog("open");
						periodoEnvio.buscarPeriodoEnvio();
						periodoEnvio.initBlockUI();
					}
					else{						
						var addhtmError='Error al Desactivar el registro de Control de Remisión.';					
						periodoEnvio.dialogErrorContent.html(addhtmError);
						periodoEnvio.dialogError.dialog("open");	
						periodoEnvio.initBlockUI();
					}
				},error : function(){
					var addhtmError='Error de conexión.';					
					periodoEnvio.dialogErrorContent.html(addhtmError);
					periodoEnvio.dialogError.dialog("open");
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
							var addhtml2='El Detalle de Control de Remisión se guardó satisfactoriamente';
							periodoEnvio.dialogMessageContent.html(addhtml2);
							periodoEnvio.dialogMessage.dialog("open");							
							periodoEnvio.initBlockUI();	
							//asigno el valor del id = secuencia al hidden
							console.debug("secuencia obtenida de grabar: "+data.secuencia);
							periodoEnvio.f_secuencia.val(data.secuencia);
							periodoEnvio.deshabilitarCamposEditar();							
							$('#<portlet:namespace/>guardarPeriodoEnvio').css('display','none');
							$('#<portlet:namespace/>actualizarPeriodoEnvio').css('display','block');
						}else if(data.resultado == "Mayor"){							
							var addhtmInfo='La fecha hasta no debe ser mayor a la fecha de ampliacion.';					
							periodoEnvio.dialogInfoContent.html(addhtmInfo);
							periodoEnvio.dialogInfo.dialog("open");
							periodoEnvio.f_fechaAmpl.focus();	
						}else if(data.resultado == "FECHA"){						
							var addhtmInfo='La fecha desde no debe ser mayor a la fecha hasta.';					
							periodoEnvio.dialogInfoContent.html(addhtmInfo);
							periodoEnvio.dialogInfo.dialog("open");	
							periodoEnvio.f_desde.focus();	
						}else if(data.resultado == "Error"){						
							var addhtmError='Se produjo un error al guardar el Detalle del Control de Remisión.';					
							periodoEnvio.dialogErrorContent.html(addhtmError);
							periodoEnvio.dialogError.dialog("open");												
							periodoEnvio.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						periodoEnvio.dialogErrorContent.html(addhtmError);
						periodoEnvio.dialogError.dialog("open");
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
							var addhtml2='El Detalle del Control de Remisión se actualizó satisfactoriamente';
							periodoEnvio.dialogMessageContent.html(addhtml2);
							periodoEnvio.dialogMessage.dialog("open");						
							periodoEnvio.initBlockUI();								
						}else if(data.resultado == "Mayor"){				
							var addhtmInfo='La fecha hasta no debe ser mayor a la fecha de ampliacion.';					
							periodoEnvio.dialogInfoContent.html(addhtmInfo);
							periodoEnvio.dialogInfo.dialog("open");
							periodoEnvio.f_fechaAmpl.focus();	
						}else if(data.resultado == "Error"){							
							var addhtmError='Se produjo un error al actualizar el Detalle del Control de Remisión.';					
							periodoEnvio.dialogErrorContent.html(addhtmError);
							periodoEnvio.dialogError.dialog("open");
							periodoEnvio.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						periodoEnvio.dialogErrorContent.html(addhtmError);
						periodoEnvio.dialogError.dialog("open");
						periodoEnvio.initBlockUI();
					}
				});						
			}
		},
		//funcion para validar ingreso de datos
		validarFormulario : function() {
			var form = periodoEnvio.f_formato.val();
			console.debug("id formato al validar formulario: "+form);
			if(periodoEnvio.f_codEmpresa.val().length == ''){				
				var addhtmAlert='Seleccione una Distribuidora Eléctrica.';					
				periodoEnvio.dialogValidacionContent.html(addhtmAlert);
				periodoEnvio.dialogValidacion.dialog("open");
				periodoEnvio.f_codEmpresa.focus();
			  	return false; 
			}else if(periodoEnvio.f_anoPres.val().length == ''){				
				var addhtmAlert='Debe ingresar año a declarar.';					
				periodoEnvio.dialogValidacionContent.html(addhtmAlert);
				periodoEnvio.dialogValidacion.dialog("open");
				periodoEnvio.f_anoPres.focus();
			  	return false; 
			}else if(periodoEnvio.f_anoPres.val().length < 4 ||
					parseFloat(periodoEnvio.f_anoPres.val())<1900){				
				var addhtmAlert='Debe ingresar año a declarar válido.';					
				periodoEnvio.dialogValidacionContent.html(addhtmAlert);
				periodoEnvio.dialogValidacion.dialog("open");
				periodoEnvio.f_anoPres.focus();
			  	return false; 
			}else if(periodoEnvio.f_mesPres.val().length == ''){				
				var addhtmAlert='Debe seleccionar mes a declarar.';					
				periodoEnvio.dialogValidacionContent.html(addhtmAlert);
				periodoEnvio.dialogValidacion.dialog("open");
				periodoEnvio.f_mesPres.focus();
			  	return false; 
			}else if(periodoEnvio.f_formato.val().length == ''){				
				var addhtmAlert='Debe seleccionar un formato.';					
				periodoEnvio.dialogValidacionContent.html(addhtmAlert);
				periodoEnvio.dialogValidacion.dialog("open");
				periodoEnvio.f_formato.focus();
			  	return false; 
			}else if(periodoEnvio.f_etapa.val().length == ''){				
				var addhtmAlert='Debe seleccionar una etapa.';					
				periodoEnvio.dialogValidacionContent.html(addhtmAlert);
				periodoEnvio.dialogValidacion.dialog("open");
				periodoEnvio.f_etapa.focus();
			  	return false; 
			}else if(periodoEnvio.f_estado.val().length == ''){				
				var addhtmAlert='Debe seleccionar un estado.';					
				periodoEnvio.dialogValidacionContent.html(addhtmAlert);
				periodoEnvio.dialogValidacion.dialog("open");
				periodoEnvio.f_estado.focus();
			  	return false; 
			}else if(periodoEnvio.f_desde.val().length == ''){				
				var addhtmAlert='Debe ingresar una fecha desde.';					
				periodoEnvio.dialogValidacionContent.html(addhtmAlert);
				periodoEnvio.dialogValidacion.dialog("open");
				periodoEnvio.f_desde.focus();
			  	return false; 
			}else if(!validaFechaDDMMAAAA(periodoEnvio.f_desde.val())){
				var addhtmAlert='Debe ingresar una fecha desde válida.';					
				periodoEnvio.dialogValidacionContent.html(addhtmAlert);
				periodoEnvio.dialogValidacion.dialog("open");
				periodoEnvio.f_desde.focus();
			  	return false; 
			}else if(periodoEnvio.f_hasta.val().length == ''){				
				var addhtmAlert='Debe ingresar una fecha hasta.';					
				periodoEnvio.dialogValidacionContent.html(addhtmAlert);
				periodoEnvio.dialogValidacion.dialog("open");
				periodoEnvio.f_hasta.focus();
			  	return false; 
			}else if(!validaFechaDDMMAAAA(periodoEnvio.f_hasta.val())){				
				var addhtmAlert='Debe ingresar una fecha hasta válida.';					
				periodoEnvio.dialogValidacionContent.html(addhtmAlert);
				periodoEnvio.dialogValidacion.dialog("open");
				periodoEnvio.f_desde.focus();
			  	return false; 
			}else if(!periodoEnvio.f_fechaAmpl.val().length == '' && 
					!validaFechaDDMMAAAA(periodoEnvio.f_fechaAmpl.val())){				
				var addhtmAlert='Debe ingresar fecha ampliacion válida.';					
				periodoEnvio.dialogValidacionContent.html(addhtmAlert);
				periodoEnvio.dialogValidacion.dialog("open");
				periodoEnvio.f_fechaAmpl.focus();
			  	return false; 			
			}else if(periodoEnvio.f_dias.val().length == ''){				
				var addhtmAlert='Debe ingresar el número de dias de notificación antes del cierre.';					
				periodoEnvio.dialogValidacionContent.html(addhtmAlert);
				periodoEnvio.dialogValidacion.dialog("open");
				periodoEnvio.f_dias.focus();
			  	return false; 
			}else if(periodoEnvio.verificarFormato(form)==true){
				if(periodoEnvio.f_anoIniVigencia.val().length == ''){					
					var addhtmAlert='Debe ingresar el año de inicio de vigencia.';					
					periodoEnvio.dialogValidacionContent.html(addhtmAlert);
					periodoEnvio.dialogValidacion.dialog("open");
					periodoEnvio.f_anoIniVigencia.focus();
				  	return false; 
				}else if(periodoEnvio.f_anoIniVigencia.val().length != 4){					
					var addhtmAlert='Debe ingresar el año de inicio de vigencia válido.';					
					periodoEnvio.dialogValidacionContent.html(addhtmAlert);
					periodoEnvio.dialogValidacion.dialog("open");
					periodoEnvio.f_anoIniVigencia.focus();
				  	return false; 
				}else if(periodoEnvio.f_anoFinVigencia.val().length == ''){					
					var addhtmAlert='Debe ingresar el año fin de vigencia.';					
					periodoEnvio.dialogValidacionContent.html(addhtmAlert);
					periodoEnvio.dialogValidacion.dialog("open");
					periodoEnvio.f_anoFinVigencia.focus();
				  	return false; 
				}else if(periodoEnvio.f_anoFinVigencia.val().length != 4){				
					var addhtmAlert='Debe ingresar el año fin de vigencia válido.';					
					periodoEnvio.dialogValidacionContent.html(addhtmAlert);
					periodoEnvio.dialogValidacion.dialog("open");
					periodoEnvio.f_anoFinVigencia.focus();
				  	return false; 
				}else{
					return true;
				}	
			}else{
				return true;
			}		
		},
		verificarFormato: function(idFormato){			
			if(idFormato=='F12A'){
			  return false;	
			}else if(idFormato=='F12B'){
			  return false;	
			}else if(idFormato=='F12C'){
			  return false;	
			}else if(idFormato=='F12D'){
			  return false;	
			}else{
			  return true;	
			}					
		},
		//funcion para mostrar anio vigencia
		 <portlet:namespace/>mostrarAnioVigencia : function(){		
			console.debug("id formato al hacer onchange:  "+periodoEnvio.f_formato.val());			 
			var idFormato = periodoEnvio.f_formato.val();
			var f = new Date();
			if(idFormato=='F12A' || idFormato=='F12B' ||idFormato=='F12C' || idFormato=='F12D'){
				periodoEnvio.f_anoIniVigencia.val('');
			    periodoEnvio.f_anoFinVigencia.val('');
				periodoEnvio.f_anoIniVigencia.attr("disabled",true);
				periodoEnvio.f_anoFinVigencia.attr("disabled",true);				
				dwr.util.removeAllOptions("etapa");				
				var dataEtapa = ['SOLICITUD', 'LEV.OBS', 'RECONSIDERACION', 'RECONOCIDO'];			
				dwr.util.addOptions("etapa", dataEtapa);
			}else{
				periodoEnvio.f_anoIniVigencia.removeAttr("disabled");
			    periodoEnvio.f_anoFinVigencia.removeAttr("disabled");
			    periodoEnvio.f_anoIniVigencia.val(f.getFullYear()+1);
				periodoEnvio.f_anoFinVigencia.val(f.getFullYear()+3);				
				dwr.util.removeAllOptions("etapa");				
				var dataEtapa = ['SOLICITUD', 'LEV.OBS', 'RECONSIDERACION', 'ESTABLECIDO'];			
				dwr.util.addOptions("etapa", dataEtapa);
		   }
		},
		
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
        	
        	periodoEnvio.f_fechaAmpl.removeAttr("disabled"); 
			
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
        	
        	periodoEnvio.f_fechaAmpl.attr("disabled",true); 
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
				width: 450,	
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
				width: 450,			
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
			
			periodoEnvio.dialogValidacion.dialog({
				modal: true,
				autoOpen: false,
				width: 450,		
				buttons: {
					Aceptar: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			periodoEnvio.dialogError.dialog({
				modal: true,
				autoOpen: false,
				width: 450,		
				buttons: {
					Aceptar: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			periodoEnvio.dialogInfo.dialog({
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