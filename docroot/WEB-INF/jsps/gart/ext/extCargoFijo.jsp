<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects />

<script type="text/javascript">

var fiseCargoFijo= {
		
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
		i_codEmpresa:null,
		i_anioRep:null,		
		i_mesRep:null,		
		
		//variables para nuevo registro
		f_codEmpresa:null,
		f_anioRep:null,	
		f_mesRep:null,			
		f_numUsuBenef:null,
		f_numUsuEmp:null,
		f_numValDCan:null,
		f_numValDEmi:null,
		f_numValFCan:null,
		f_numValFEmi:null,
		f_numAgen:null,		
		f_montoMes:null,
		f_montoCanje:null,		
		f_numDoc:null,
		f_numDocRecepcion:null,
		f_fechaSustento:null,
		f_fechaRecepcion:null,		
		f_estado:null,
		f_igv:null,
		f_aplicaIgv:null,
		f_gloza:null,		
		
		//grillas
		tablaResultados:null,
		paginadoResultados:null,	
		
		
		
		init : function() {
			
			this.formCommand=$('#fiseCargoFijoBean');
			
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
			this.urlBusqueda='<portlet:resourceURL id="busquedaCargosFijos" />';			
			this.urlGrabar='<portlet:resourceURL id="grabarCargosFijos" />';			
			this.urlActualizar='<portlet:resourceURL id="actualizarCargosFijos" />';
			this.urlEditarView='<portlet:resourceURL id="editarViewCargosFijos" />';			
			this.urlEliminar='<portlet:resourceURL id="eliminarCargosFijos" />';	
			
			
			//botones
			this.botonBuscar=$("#<portlet:namespace/>btnBuscarCargosFijos");
			this.botonNuevo=$("#<portlet:namespace/>btnNuevoCargoFijo");
			this.botonRegresar=$("#<portlet:namespace/>regresarCargoFijo");			
			this.botonGrabar=$("#<portlet:namespace/>guardarCargoFijo");
			this.botonActualizar=$("#<portlet:namespace/>actualizarCargoFijo");		
			
			//variables de busqueda		
			this.i_codEmpresa=$('#idBusq');
			this.i_anioRep=$('#descripcionBusq');
			this.i_mesRep=$('#descripcionBusq');
			
			//VARIBALES PARA NUEVO REGISTRO		
			this.f_codEmpresa=$('#codEmpresa');			
			this.f_anioRep=$('#anioRep');
			this.f_mesRep=$('#mesRep');			
			this.f_numUsuBenef=$('#numUsuBenef');
			this.f_numUsuEmp=$('#numUsuEmp');			
			this.f_numValDCan=$('#numValDCan');
			this.f_numValDEmi=$('#numValDEmi');			
			this.f_numValFCan=$('#numValFCan');
			this.f_numValFEmi=$('#numValFEmi');			
			this.f_numAgen=$('#numAgen');
			this.f_montoMes=$('#montoMes');			
			this.f_montoCanje=$('#montoCanje');
			this.f_numDoc=$('#numDoc');			
			this.f_numDocRecepcion=$('#numDocRecepcion');
			this.f_fechaSustento=$('#fechaSustento');		
			this.f_fechaRecepcion=$('#fechaRecepcion');
			this.f_estado=$('#estado');			
			this.f_igv=$('#igv');
			this.f_aplicaIgv=$('#aplicaIgv');			
			this.f_gloza=$('#gloza');
			
			//grillas			
			this.tablaResultados=$("#<portlet:namespace/>grid_resultado_busqueda");
			this.paginadoResultados='#<portlet:namespace/>paginador_resultado_busqueda';
			this.buildGrids();	//cargar el modelo de la grilla	
			
			
			
			//llamado a la funciones de cada boton
			fiseCargoFijo.botonBuscar.click(function() {
				fiseCargoFijo.buscarCargosFijos();
			});			
			
			 fiseCargoFijo.botonNuevo.click(function() {
				fiseCargoFijo.<portlet:namespace/>nuevoCargoFijo();
		    });
			
			fiseCargoFijo.botonGrabar.click(function() {
				fiseCargoFijo.<portlet:namespace/>guardarCargoFijo();
			});
			
			fiseCargoFijo.botonActualizar.click(function() {
				fiseCargoFijo.<portlet:namespace/>actualizarCargoFijo();
			});		
			
			
		    fiseCargoFijo.botonRegresar.click(function() {
		    	fiseCargoFijo.<portlet:namespace/>regresarCargoFijo();
		    });			
		    
		    fiseCargoFijo.initDialogs();
		    
		    //eventos por defecto	
		    
			//fiseCargoFijo.botonBuscar.trigger('click');
			//fiseCargoFijo.initBlockUI();
		},
		//funcion para armar el modelo de la grilla para el resultado
		buildGrids : function () {	
			fiseCargoFijo.tablaResultados.jqGrid({
			   datatype: "local",
		       colNames: ['Empresa','Año Repo.','Mes Repo.','N° Usu. Benef.','N° Usu. Emp.','N° Vales Físcios Emi.','N° Vales Físcios Canj.','N° Vales Digitales Emi.','N° Vales Digitales Canj.','Visualizar','Editar','Anular','',''],
		       colModel: [
                       { name: 'descEmpresa', index: 'descEmpresa', width: 50},
					   { name: 'anioRep', index: 'anioRep', width: 20},	
					   { name: 'descMesPresentacion', index: 'descMesPresentacion', width: 30},
					   { name: 'cfinumusuben', index: 'cfinumusuben', width: 30},		
					   { name: 'cfinumusuemp', index: 'cfinumusuemp', width: 30},
					   { name: 'cfinumvalfemi', index: 'cfinumvalfemi', width: 30},		
					   { name: 'cfinumvalfcan', index: 'cfinumvalfcan', width: 30},
					   { name: 'cfinumvaldemi', index: 'cfinumvaldemi', width: 30},	
					   { name: 'cfinumvaldcan', index: 'cfinumvaldcan', width: 30},	
		               { name: 'view', index: 'view', width: 20,align:'center' },
		               { name: 'edit', index: 'edit', width: 20,align:'center' },
		               { name: 'elim', index: 'elim', width: 20,align:'center' },
		               { name: 'codEmpresa', index: 'codEmpresa', hidden: true},
		               { name: 'mesRep', index: 'mesRep', hidden: true}		                
			   	    ],
			   	 multiselect: false,
					rowNum:10,
				   	rowList:[10,20,50],
					height: 200,
				   	autowidth: true,
					rownumbers: true,
					shrinkToFit:true,
					pager: fiseCargoFijo.paginadoResultados,
				    viewrecords: true,
				   	caption: "Cargos Fijos",
				    sortorder: "asc",	   	    	   	   
		       gridComplete: function(){
		      		var ids = fiseCargoFijo.tablaResultados.jqGrid('getDataIDs');
		      		for(var i=0;i < ids.length;i++){
		      			var cl = ids[i];
		      			var ret = fiseCargoFijo.tablaResultados.jqGrid('getRowData',cl);           
		      			view = "<a href='#'><img border='0' title='View' src='/net-theme/images/img-net/file.png'  align='center' onclick=\"fiseCargoFijo.verCargoFijo('"+ret.codEmpresa+"','"+ret.anioRep+"','"+ret.mesRep+"');\" /></a> ";
		      			edit = "<a href='#'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png'  align='center' onclick=\"fiseCargoFijo.editarCargoFijo('"+ret.codEmpresa+"','"+ret.anioRep+"','"+ret.mesRep+"');\" /></a> ";
		      			elim = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"fiseCargoFijo.confirmarEliminarCargoFijo('"+ret.codEmpresa+"','"+ret.anioRep+"','"+ret.mesRep+"');\" /></a> ";              			
		      			fiseCargoFijo.tablaResultados.jqGrid('setRowData',ids[i],{view:view});
		      			fiseCargoFijo.tablaResultados.jqGrid('setRowData',ids[i],{edit:edit});
		      			fiseCargoFijo.tablaResultados.jqGrid('setRowData',ids[i],{elim:elim});
		      		}
		      }
		  	});
			fiseCargoFijo.tablaResultados.jqGrid('navGrid',fiseCargoFijo.paginadoResultados,{add:false,edit:false,del:false,search: false,refresh: false});	
			 fiseCargoFijo.tablaResultados.jqGrid('navButtonAdd',fiseCargoFijo.paginadoResultados,{
			       caption:"Exportar a Excel",
			       buttonicon: "ui-icon-bookmark",
			       onClickButton : function () {
			      location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>'; 
			       } 
			}); 
		},
		//funcion para buscar
		buscarCargosFijos : function () {		
			fiseCargoFijo.blockUI();
			jQuery.ajax({			
					url: fiseCargoFijo.urlBusqueda+'&'+fiseCargoFijo.formCommand.serialize(),
					type: 'post',
					dataType: 'json',				
					success: function(gridData) {					
							fiseCargoFijo.tablaResultados.clearGridData(true);
							fiseCargoFijo.tablaResultados.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
							fiseCargoFijo.tablaResultados[0].refreshIndex();
							fiseCargoFijo.initBlockUI();
					},error : function(){
							alert("Error de conexión.");
							fiseCargoFijo.initBlockUI();
					}
				});			
		},	
		
		//funcion para nuevo registro
		<portlet:namespace/>nuevoCargoFijo : function(){
			console.debug("boton nuevo registro:  ");	
			fiseCargoFijo.divNuevo.show();
			fiseCargoFijo.divBuscar.hide();							
		    $('#<portlet:namespace/>guardarCargoFijo').css('display','block');
			$('#<portlet:namespace/>actualizarCargoFijo').css('display','none');			
		},
		
		//Function para Visualizar los datos del formulario		
		verCargoFijo : function(cod_Empresa, anio_Rep, mes_Rep){	
			console.debug("entrando a ver ");			
			$.blockUI({ message: fiseCargoFijo.mensajeObteniendoDatos});
			jQuery.ajax({
					url: fiseCargoFijo.urlEditarView+'&'+fiseCargoFijo.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {						  
					      <portlet:namespace />codEmpresa: cod_Empresa,
					      <portlet:namespace />anioRepBusq: anio_Rep,
					      <portlet:namespace />mesRep: mes_Rep
						},
					success: function(data) {
					    if (data != null){															
					    	fiseCargoFijo.divNuevo.show();
					    	fiseCargoFijo.divBuscar.hide();	
					    	
					    	fiseCargoFijo.llenarDatosEditar(data);
					    	
					    	fiseCargoFijo.initBlockUI();				    	
					    	$('#<portlet:namespace/>guardarCargoFijo').css('display','none');
							$('#<portlet:namespace/>actualizarCargoFijo').css('display','none');		    					    							
				        }						
						else{
							alert("Error al visualizar los datos del registro seleccionado");
							fiseCargoFijo.initBlockUI();
						}
					},error : function(){
						alert("Error de conexión.");
						fiseCargoFijo.initBlockUI();
					}
			});	
		},
		
		//Function para editar los datos del formulario
		editarCargoFijo : function(cod_Empresa, anio_Rep, mes_Rep){	
			    console.debug("entrando a editar ");		
				$.blockUI({ message: fiseCargoFijo.mensajeObteniendoDatos });			 
				jQuery.ajax({
						url: fiseCargoFijo.urlEditarView+'&'+fiseCargoFijo.formCommand.serialize(),
						type: 'post',
						dataType: 'json',
						data: {							
							 <portlet:namespace />codEmpresa: cod_Empresa,
						     <portlet:namespace />anioRep: anio_Rep,
						     <portlet:namespace />mesRep: mes_Rep					   				  
						},
						success: function(data) {				
							if (data != null){															
								fiseCargoFijo.divNuevo.show();
								fiseCargoFijo.divBuscar.hide();	
								
								fiseCargoFijo.llenarDatosEditar(data);
								
								fiseCargoFijo.initBlockUI();			
								$('#<portlet:namespace/>guardarCargoFijo').css('display','none');
								$('#<portlet:namespace/>actualizarCargoFijo').css('display','block');													
					         }
							else{
								alert("Error al recuperar los datos del registro seleccionado");
								fiseCargoFijo.initBlockUI();
							}
						},error : function(){
							alert("Error de conexión.");
							fiseCargoFijo.initBlockUI();
						}
				});		
		},
		
		//funcion  para llenar los campos para editar
		llenarDatosEditar : function(bean){	
			fiseCargoFijo.f_codEmpresa.val(bean.codEmpresa);			
			fiseCargoFijo.f_anioRep.val(bean.anioRep);
			fiseCargoFijo.f_mesRep.val(bean.mesRep);			
			fiseCargoFijo.f_numUsuBenef.val(bean.numUsuBenef);
			fiseCargoFijo.f_numUsuEmp.val(bean.numUsuEmp);			
			fiseCargoFijo.f_numValDCan.val(bean.numValDCan);
			fiseCargoFijo.f_numValDEmi.val(bean.numValDEmi);			
			fiseCargoFijo.f_numValFCan.val(bean.numValFCan);
			fiseCargoFijo.f_numValFEmi.val(bean.numValFEmi);			
			fiseCargoFijo.f_numAgen.val(bean.numAgen);
			fiseCargoFijo.f_montoMes.val(bean.montoMes);			
			fiseCargoFijo.f_montoCanje.val(bean.montoCanje);
			fiseCargoFijo.f_numDoc.val(bean.numDoc);			
			fiseCargoFijo.f_numDocRecepcion.val(bean.numDocRecepcion);
			fiseCargoFijo.f_fechaSustento.val(bean.fechaSustento);		
			fiseCargoFijo.f_fechaRecepcion.val(bean.fechaRecepcion);
			//fiseCargoFijo.f_estado.val(bean.estado);			
			fiseCargoFijo.f_igv.val(bean.igv);
			//fiseCargoFijo.f_aplicaIgv.val(bean.aplicaIgv);			
			fiseCargoFijo.f_gloza.val(bean.gloza);
			
			if(bean.estado=='1'){				
				 document.getElementById('rbtActivo').checked = true;
			}else{				
				 document.getElementById('rbtInactivo').checked = true;
			}
			
			if(bean.aplicaIgv=='1'){				
				 document.getElementById('rbtIgvSI').checked = true;
			}else{				
				 document.getElementById('rbtIgvNO').checked = true;
			}
		},
		
		/**Function para confirmar si quiere eliminar el registro o no*/
		confirmarEliminarCargoFijo : function(codEmpresa, anioRep, mesRep){			
			var addhtml='¿Está seguro que desea eliminar el registro seleccionado?';
			fiseCargoFijo.dialogConfirmContent.html(addhtml);
			fiseCargoFijo.dialogConfirm.dialog("open");	
			cod_empresa=codEmpresa;
			anio_rep = anioRep;
			mes_rep = mesRep;
		},
		
		/**Function para  eliminar el registro una vez hecho la confirmacion*/
		eliminarCargoFijo : function(cod_empresa, anio_rep, mes_rep){
			console.debug("entranado a eliminar ");
			$.blockUI({ message: fiseCargoFijo.mensajeEliminando });
			jQuery.ajax({
				url: fiseCargoFijo.urlEliminar+'&'+fiseCargoFijo.formCommand.serialize(),
				type: 'post',
				dataType: 'json',
				data: {				
					 <portlet:namespace />codEmpresa: cod_empresa,
				     <portlet:namespace />anioRep: anio_rep,
				     <portlet:namespace />mesRep: mes_rep			  
					},
				success: function(data) {
					if (data.resultado == "OK"){
						var addhtml2='Registro eliminado con éxito';					
						fiseCargoFijo.dialogMessageContent.html(addhtml2);
						fiseCargoFijo.dialogMessage.dialog("open");
						fiseCargoFijo.buscarCargosFijos();
						fiseCargoFijo.initBlockUI();
					}
					else{
						alert("Error al eliminar el registro");
						fiseCargoFijo.initBlockUI();
					}
				},error : function(){
					alert("Error de conexión.");
					fiseCargoFijo.initBlockUI();
				}
			});
		},
		
		//Funcion para Grabar nuevo registro
		<portlet:namespace/>guardarCargoFijo: function(){
			if (fiseCargoFijo.validarFormulario()){
				$.blockUI({ message: fiseCargoFijo.mensajeGuardando });
				 jQuery.ajax({
					 url: fiseCargoFijo.urlGrabar+'&'+fiseCargoFijo.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {							
						<portlet:namespace />codEmpresa: fiseCargoFijo.f_codEmpresa.val(),
					    <portlet:namespace />anioRep: fiseCargoFijo.f_anioRep.val(),
					    <portlet:namespace />mesRep: fiseCargoFijo.f_mesRep.val()		
						},
					success: function(data) {			
						if (data.resultado == "OK"){				
							var addhtml2='Datos guardados satisfactoriamente';
							
							fiseCargoFijo.dialogMessageContent.html(addhtml2);
							fiseCargoFijo.dialogMessage.dialog("open");							
							fiseCargoFijo.initBlockUI();
							$('#<portlet:namespace/>guardarCargoFijo').css('display','none');
							$('#<portlet:namespace/>actualizarCargoFijo').css('display','block');				
							
						}else if(data.resultado == "Error"){				
							var addhtml2='Se produjo un error al guardar los datos.';
							fiseCargoFijo.dialogMessageContent.html(addhtml2);
							fiseCargoFijo.dialogMessage.dialog("open");						
							fiseCargoFijo.initBlockUI();
						}else if(data.resultado=="Duplicado"){
							var addhtml2='Ya existe registrado un cargo fijo con la misma empresa, año y mes';
							fiseCargoFijo.dialogMessageContent.html(addhtml2);
							fiseCargoFijo.dialogMessage.dialog("open");						
							fiseCargoFijo.initBlockUI();
						}
					},error : function(){
						alert("Error de conexión.");
						fiseCargoFijo.initBlockUI();
					}
				});			
			}
		},
		
		//Funcion para actualizar un registro
		<portlet:namespace/>actualizarCargoFijo : function(){
			if (fiseCargoFijo.validarFormulario()){
				$.blockUI({ message: fiseCargoFijo.mensajeActualizando });
				 jQuery.ajax({
					 url: fiseCargoFijo.urlActualizar+'&'+fiseCargoFijo.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {
						<portlet:namespace />codEmpresa: fiseCargoFijo.f_codEmpresa.val(),
					    <portlet:namespace />anioRep: fiseCargoFijo.f_anioRep.val(),
					    <portlet:namespace />mesRep: fiseCargoFijo.f_mesRep.val()	 	
						},
					success: function(data) {			
						if (data.resultado == "OK"){				
							var addhtml2='Datos actualizados satisfactoriamente';
							fiseCargoFijo.dialogMessageContent.html(addhtml2);
							fiseCargoFijo.dialogMessage.dialog("open");						
							fiseCargoFijo.initBlockUI();								
						}else if(data.resultado == "Error"){				
							var addhtml2='Se produjo un error al actualizar los datos.';
							fiseCargoFijo.dialogMessageContent.html(addhtml2);
							fiseCargoFijo.dialogMessage.dialog("open");						
							fiseCargoFijo.initBlockUI();
						}
					},error : function(){
						alert("Error de conexión.");
						fiseCargoFijo.initBlockUI();
					}
				});						
			}
		},
		//funcion para validar ingreso de datos
		validarFormulario : function() {		
			if(fiseCargoFijo.f_codEmpresa.val().length == ''){
				alert('Debe seleccionar una empresa.'); 
				fiseCargoFijo.f_codEmpresa.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_anioRep.val().length == ''){
				alert('Debe ingresar año de reporte.'); 
				fiseCargoFijo.f_anioRep.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_mesRep.val().length == ''){
				alert('Debe seleccionar un mes de reporte.'); 
				fiseCargoFijo.f_mesRep.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_numUsuBenef.val().length == ''){
				alert('Debe ingresar número de usuarios beneficiados.'); 
				fiseCargoFijo.f_numUsuBenef.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_numUsuEmp.val().length == ''){
				alert('Debe ingresar número de usuarios empadronados.'); 
				fiseCargoFijo.f_numUsuEmp.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_numValDCan.val().length == ''){
				alert('Debe ingresar número de vales digitales canjeados.'); 
				fiseCargoFijo.f_numValDCan.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_numValDEmi.val().length == ''){
				alert('Debe ingresar número de vales digitales emitidos.'); 
				fiseCargoFijo.f_numValDEmi.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_numValFCan.val().length == ''){
				alert('Debe ingresar número de vales físicos canjeados.'); 
				fiseCargoFijo.f_numValFCan.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_numValFEmi.val().length == ''){
				alert('Debe ingresar número de vales físicos emitidos.'); 
				fiseCargoFijo.f_numValFEmi.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_numAgen.val().length == ''){
				alert('Debe ingresar número de agentes.'); 
				fiseCargoFijo.f_numAgen.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_montoMes.val().length == ''){
				alert('Debe ingresar monto de cargo fijo al mes.'); 
				fiseCargoFijo.f_montoMes.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_montoCanje.val().length == ''){
				alert('Debe ingresar monto transferido por canje.'); 
				fiseCargoFijo.f_montoCanje.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_igv.val().length == ''){
				alert('Debe ingresar IGV.'); 
				fiseCargoFijo.f_igv.focus();
			  	return false; 
			}else{
				return true;
			}		
		},
		
		//funcion para regresar
		<portlet:namespace/>regresarCargoFijo : function(){			
			fiseCargoFijo.divNuevo.hide();
			fiseCargoFijo.divBuscar.show();
					
			fiseCargoFijo.botonBuscar.trigger('click');
		},
		
		//DIALOGOS
		initDialogs : function(){	
			fiseCargoFijo.dialogMessage.dialog({
				modal: true,
				autoOpen: false,
				buttons: {
					Ok: function() {
						$( this ).dialog("close");
					}
				}
			});
			//dialogo para eliminar registro
			fiseCargoFijo.dialogConfirm.dialog({
				modal: true,
				height: 200,
				width: 400,			
				autoOpen: false,
				buttons: {
					"Si": function() {
						fiseCargoFijo.eliminarCargoFijo(cod_empresa, anio_rep, mes_rep);
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