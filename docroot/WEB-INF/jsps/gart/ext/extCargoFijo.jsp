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
		f_empresa:null,
		f_anioRep:null,	
		f_mesRep:null,			
		f_numUsuBenefR:null, f_numUsuBenefP:null, f_numUsuBenefL:null,
		f_numUsuEmpR:null, f_numUsuEmpP:null, f_numUsuEmpL:null,
		f_numValDCanR:null, f_numValDCanP:null, f_numValDCanL:null,
		f_numValDEmiR:null, f_numValDEmiP:null, f_numValDEmiL:null,
		f_numValFCanR:null, f_numValFCanP:null, f_numValFCanL:null,
		f_numValFEmiR:null, f_numValFEmiP:null, f_numValFEmiL:null,
		f_numAgenR:null,	f_numAgenP:null,	f_numAgenL:null,		
		f_montoMesR:null, f_montoMesP:null, f_montoMesL:null,
		f_montoCanje:null,		
		f_numDoc:null,
		f_numDocRecepcion:null,
		f_fechaSustento:null,
		f_fechaRecepcion:null,		
		f_estado:null,
		f_igv:null,
		f_aplicaIgv:null,
		f_gloza:null,	
		
		f_fechaRecepcionEditar:null,	
		f_flagVer:null,
		
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
			this.i_codEmpresa=$('#codEmpresaBusq');
			this.i_anioRep=$('#anioRepBusq');
			this.i_mesRep=$('#mesRepBusq');
			
			//VARIBALES PARA NUEVO REGISTRO		
			this.f_empresa=$('#codigoEmpresa');			
			this.f_anioRep=$('#anioReporte');
			this.f_mesRep=$('#mesReporte');	
			
			this.f_numUsuBenefR=$('#numUsuBenefR');
			this.f_numUsuBenefP=$('#numUsuBenefP');
			this.f_numUsuBenefL=$('#numUsuBenefL');
			
			this.f_numUsuEmpR=$('#numUsuEmpR');
			this.f_numUsuEmpP=$('#numUsuEmpP');
			this.f_numUsuEmpL=$('#numUsuEmpL');
			
			this.f_numValDCanR=$('#numValDCanR');
			this.f_numValDCanP=$('#numValDCanP');
			this.f_numValDCanL=$('#numValDCanL');
			
			this.f_numValDEmiR=$('#numValDEmiR');
			this.f_numValDEmiP=$('#numValDEmiP');
			this.f_numValDEmiL=$('#numValDEmiL');
			
			this.f_numValFCanR=$('#numValFCanR');
			this.f_numValFCanP=$('#numValFCanP');
			this.f_numValFCanL=$('#numValFCanL');
			
			this.f_numValFEmiR=$('#numValFEmiR');
			this.f_numValFEmiP=$('#numValFEmiP');
			this.f_numValFEmiL=$('#numValFEmiL');
			
			this.f_numAgenR=$('#numAgenteR');
			this.f_numAgenP=$('#numAgenteP');
			this.f_numAgenL=$('#numAgenteL');
			
			this.f_montoMesR=$('#montoMesR');	
			this.f_montoMesP=$('#montoMesP');
			this.f_montoMesL=$('#montoMesL');
			
			this.f_montoCanje=$('#montoCanje');
			this.f_numDoc=$('#numDoc');			
			this.f_numDocRecepcion=$('#numDocRecepcion');
			this.f_fechaSustento=$('#fechaSustento');		
			this.f_fechaRecepcion=$('#fechaRecepcion');
			this.f_estado=$('#estado');			
			this.f_igv=$('#igv');
			this.f_aplicaIgv=$('#aplicaIgv');			
			this.f_gloza=$('#gloza');
			
			this.f_fechaRecepcionEditar=$('#fechaRecepcionEditar');
			this.f_flagVer=$('#flagVer');
			
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
		    
			fiseCargoFijo.botonBuscar.trigger('click');
			fiseCargoFijo.initBlockUI();
		},
		//funcion para armar el modelo de la grilla para el resultado
		buildGrids : function () {	
			fiseCargoFijo.tablaResultados.jqGrid({
			   datatype: "local",
		       colNames: ['Dist. El�ct.','A�o Repo.','Mes Repo.','N� Usu. Benef.','N� Usu. Emp.','N� Vales F�sicos Emi.','N� Vales F�sicos Canj.','N� Vales Digitales Emi.','Estado','Visualizar','Editar','Anular','',''],
		       colModel: [
						   { name: 'desEmpresa', index: 'desEmpresa', width: 50},
			               { name: 'anioReporte', index: 'anioReporte', width: 30 },   
			               { name: 'desMesRep', index: 'desMesRep', width: 30},
			               { name: 'numUsuBenefR', index: 'numUsuBenefR', width: 30 },   
			               { name: 'numUsuEmpR', index: 'numUsuEmpR', width: 30},
			               { name: 'numValFEmiR', index: 'numValFEmiR', width: 50},
			               { name: 'numValFCanR', index: 'numValFCanR', width: 50},
			               { name: 'numValDEmiR', index: 'numValFEmiR', width: 50},
			               { name: 'desEstado', index: 'desEstado', width: 50},
			               { name: 'view', index: 'view', width: 20,align:'center' },
			               { name: 'edit', index: 'edit', width: 20,align:'center' },
			               { name: 'elim', index: 'elim', width: 20,align:'center' },		              
			               { name: 'codigoEmpresa', index: 'codigoEmpresa', hidden: true},
			               { name: 'mesReporte', index: 'mesReporte', hidden: true}			               
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
					   	caption: "Resultado(s) de la b�squeda",
					    sortorder: "asc",	   	    	   	   
			       gridComplete: function(){
			      		var ids = fiseCargoFijo.tablaResultados.jqGrid('getDataIDs');
			      		for(var i=0;i < ids.length;i++){
			      			var cl = ids[i];
			      			var ret = fiseCargoFijo.tablaResultados.jqGrid('getRowData',cl);           
			      			view = "<a href='#'><img border='0' title='View' src='/net-theme/images/img-net/file.png'  align='center' onclick=\"fiseCargoFijo.verCargoFijo('"+ret.codigoEmpresa+"','"+ret.anioReporte+"','"+ret.mesReporte+"');\" /></a> ";
			      			edit = "<a href='#'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png'  align='center' onclick=\"fiseCargoFijo.editarCargoFijo('"+ret.codigoEmpresa+"','"+ret.anioReporte+"','"+ret.mesReporte+"');\" /></a> ";
			      			elim = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"fiseCargoFijo.confirmarEliminarCargoFijo('"+ret.codigoEmpresa+"','"+ret.anioReporte+"','"+ret.mesReporte+"');\" /></a> ";              			
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
							alert("Error de conexi�n.");
							fiseCargoFijo.initBlockUI();
					}
				});			
		},	
		
		//funcion para nuevo registro
		<portlet:namespace/>nuevoCargoFijo : function(){
			console.debug("boton nuevo registro:  ");	
			fiseCargoFijo.divNuevo.show();
			fiseCargoFijo.divBuscar.hide();
			
			fiseCargoFijo.inicializarFormulario();			
			
			fiseCargoFijo.verElementosEditar();		
			
			fiseCargoFijo.habilitarCamposView();
			
		    $('#<portlet:namespace/>guardarCargoFijo').css('display','block');
			$('#<portlet:namespace/>actualizarCargoFijo').css('display','none');			
		},
		
		//function para inicializar el formulario
		inicializarFormulario : function(){		
			var f = new Date();			
			
			if(fiseCargoFijo.i_codEmpresa.val()!=''){
				fiseCargoFijo.f_empresa.val(fiseCargoFijo.i_codEmpresa.val());	
			}else{
				fiseCargoFijo.f_empresa.val('');	
			}
			
			if(fiseCargoFijo.i_anioRep.val()!=''){
				fiseCargoFijo.f_anioRep.val(fiseCargoFijo.i_anioRep.val());	
			}else{
				fiseCargoFijo.f_anioRep.val(f.getFullYear());	
			}
			
			if(fiseCargoFijo.i_mesRep.val()!=''){
				fiseCargoFijo.f_mesRep.val(fiseCargoFijo.i_mesRep.val());	
			}else{
				fiseCargoFijo.f_mesRep.val(f.getMonth() +1);	
			}				
			
			fiseCargoFijo.f_numUsuBenefR.val('0');
			fiseCargoFijo.f_numUsuBenefP.val('0');
			fiseCargoFijo.f_numUsuBenefL.val('0');
			
			fiseCargoFijo.f_numUsuEmpR.val('0');	
			fiseCargoFijo.f_numUsuEmpP.val('0');	
			fiseCargoFijo.f_numUsuEmpL.val('0');	
			
			fiseCargoFijo.f_numValDCanR.val('0');
			fiseCargoFijo.f_numValDCanP.val('0');
			fiseCargoFijo.f_numValDCanL.val('0');
			
			fiseCargoFijo.f_numValDEmiR.val('0');
			fiseCargoFijo.f_numValDEmiP.val('0');
			fiseCargoFijo.f_numValDEmiL.val('0');
			
			fiseCargoFijo.f_numValFCanR.val('0');
			fiseCargoFijo.f_numValFCanP.val('0');
			fiseCargoFijo.f_numValFCanL.val('0');
			
			fiseCargoFijo.f_numValFEmiR.val('0');
			fiseCargoFijo.f_numValFEmiP.val('0');
			fiseCargoFijo.f_numValFEmiL.val('0');
			
			fiseCargoFijo.f_numAgenR.val('0');
			fiseCargoFijo.f_numAgenP.val('0');
			fiseCargoFijo.f_numAgenL.val('0');
			
			fiseCargoFijo.f_montoMesR.val('0.00');
			fiseCargoFijo.f_montoMesP.val('0.00');
			fiseCargoFijo.f_montoMesL.val('0.00');
			
			fiseCargoFijo.f_montoCanje.val('0.00');
			fiseCargoFijo.f_numDoc.val('');			
			fiseCargoFijo.f_numDocRecepcion.val('');
			fiseCargoFijo.f_fechaSustento.val('');		
			fiseCargoFijo.f_fechaRecepcion.val('');			
			fiseCargoFijo.f_igv.val('0.00');			
			fiseCargoFijo.f_gloza.val('');		
			
			fiseCargoFijo.f_empresa.attr("disabled",false);
			fiseCargoFijo.f_anioRep.attr("disabled",false);
			fiseCargoFijo.f_mesRep.attr("disabled",false);
			
			fiseCargoFijo.soloNumerosEnteros();
			fiseCargoFijo.soloNumerosDecimales();
		},		
		//function para validar solo numeros enteros
		soloNumerosEnteros : function(){		
			fiseCargoFijo.f_anioRep.attr("onkeypress","return soloNumerosDecimales(event, 1, 'anioReporte',4,0)");
			
			fiseCargoFijo.f_numUsuBenefR.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numUsuBenefR',7,0)");
			fiseCargoFijo.f_numUsuBenefP.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numUsuBenefP',7,0)");
			fiseCargoFijo.f_numUsuBenefL.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numUsuBenefL',7,0)");
			
			fiseCargoFijo.f_numUsuEmpR.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numUsuEmpR',7,0)");	
			fiseCargoFijo.f_numUsuEmpP.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numUsuEmpP',7,0)");	
			fiseCargoFijo.f_numUsuEmpL.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numUsuEmpL',7,0)");	
			
			fiseCargoFijo.f_numValDCanR.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numValDCanR',7,0)");
			fiseCargoFijo.f_numValDCanP.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numValDCanP',7,0)");
			fiseCargoFijo.f_numValDCanL.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numValDCanL',7,0)");
			
			fiseCargoFijo.f_numValDEmiR.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numValDEmiR',7,0)");
			fiseCargoFijo.f_numValDEmiP.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numValDEmiP',7,0)");
			fiseCargoFijo.f_numValDEmiL.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numValDEmiL',7,0)");
			
			fiseCargoFijo.f_numValFCanR.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numValFCanR',7,0)");
			fiseCargoFijo.f_numValFCanP.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numValFCanP',7,0)");
			fiseCargoFijo.f_numValFCanL.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numValFCanL',7,0)");
			
			fiseCargoFijo.f_numValFEmiR.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numValFEmiR',7,0)");
			fiseCargoFijo.f_numValFEmiP.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numValFEmiP',7,0)");
			fiseCargoFijo.f_numValFEmiL.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numValFEmiL',7,0)");
			
			fiseCargoFijo.f_numAgenR.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numAgenteR',7,0)");
			fiseCargoFijo.f_numAgenP.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numAgenteP',7,0)");
			fiseCargoFijo.f_numAgenL.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numAgenteL',7,0)");
			
			fiseCargoFijo.f_numDoc.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numDoc',20,0)");			
			fiseCargoFijo.f_numDocRecepcion.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numDocRecepcion',20,0)");		
		},
		
		//function para validar solo numeros decimales
		soloNumerosDecimales : function(){			
			fiseCargoFijo.f_montoMesR.attr("onkeypress","return soloNumerosDecimales(event, 2, 'montoMesR',7,2)");
			fiseCargoFijo.f_montoMesP.attr("onkeypress","return soloNumerosDecimales(event, 2, 'montoMesP',7,2)");
			fiseCargoFijo.f_montoMesL.attr("onkeypress","return soloNumerosDecimales(event, 2, 'montoMesL',7,2)");
			
			fiseCargoFijo.f_montoCanje.attr("onkeypress","return soloNumerosDecimales(event, 2, 'montoCanje',7,2)");
			fiseCargoFijo.f_igv.attr("onkeypress","return soloNumerosDecimales(event, 2, 'igv',1,2)");	
			completarDecimal('montoMesR',2);
			completarDecimal('montoMesP',2);
			completarDecimal('montoMesL',2);
			completarDecimal('montoCanje',2);
			completarDecimal('igv',2);
		},	
		
		//Function para Visualizar los datos del formulario		
		verCargoFijo : function(cod_Empresa, anio_Rep, mes_Rep){	
			 console.debug("entrando a ver "+cod_Empresa);
			 console.debug("entrando a ver "+anio_Rep);
			 console.debug("entrando a ver "+mes_Rep);		
			$.blockUI({ message: fiseCargoFijo.mensajeObteniendoDatos});
			jQuery.ajax({
					url: fiseCargoFijo.urlEditarView+'&'+fiseCargoFijo.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data: {						  
					      <portlet:namespace />codigoEmp: cod_Empresa,
					      <portlet:namespace />anioRep: anio_Rep,
					      <portlet:namespace />codigoMes: mes_Rep					      
						},
					success: function(data) {
					    if (data != null){															
					    	fiseCargoFijo.divNuevo.show();
					    	fiseCargoFijo.divBuscar.hide();	
					    	
					    	fiseCargoFijo.llenarDatosEditar(data);				    	   						
					    	
					    	fiseCargoFijo.ocultarElementosEditar();
					    	
					    	fiseCargoFijo.deshabilitarCamposView();
					    	
					    	
					    	fiseCargoFijo.initBlockUI();				    	
					    	$('#<portlet:namespace/>guardarCargoFijo').css('display','none');
							$('#<portlet:namespace/>actualizarCargoFijo').css('display','none');		    					    							
				        }						
						else{
							alert("Error al visualizar los datos del registro seleccionado");
							fiseCargoFijo.initBlockUI();
						}
					},error : function(){
						alert("Error de conexi�n.");
						fiseCargoFijo.initBlockUI();
					}
			});	
		},
		
		//Function para editar los datos del formulario
		editarCargoFijo : function(cod_Empresa, anio_Rep, mes_Rep){	
			    console.debug("entrando a editar "+cod_Empresa);
			    console.debug("entrando a editar "+anio_Rep);
			    console.debug("entrando a editar "+mes_Rep);
				$.blockUI({ message: fiseCargoFijo.mensajeObteniendoDatos });			 
				jQuery.ajax({
						url: fiseCargoFijo.urlEditarView+'&'+fiseCargoFijo.formCommand.serialize(),
						type: 'post',
						dataType: 'json',
						data: {							
							<portlet:namespace />codigoEmp: cod_Empresa,
						    <portlet:namespace />anioRep: anio_Rep,
						    <portlet:namespace />codigoMes: mes_Rep						     
						},
						success: function(data) {				
							if (data != null){															
								fiseCargoFijo.divNuevo.show();
								fiseCargoFijo.divBuscar.hide();	
								
								fiseCargoFijo.llenarDatosEditar(data);							
								
								fiseCargoFijo.ocultarElementosEditar();			
								
								fiseCargoFijo.habilitarCamposView();								
								
								fiseCargoFijo.initBlockUI();			
								$('#<portlet:namespace/>guardarCargoFijo').css('display','none');
								$('#<portlet:namespace/>actualizarCargoFijo').css('display','block');													
					         }
							else{
								alert("Error al recuperar los datos del registro seleccionado");
								fiseCargoFijo.initBlockUI();
							}
						},error : function(){
							alert("Error de conexi�n.");
							fiseCargoFijo.initBlockUI();
						}
				});		
		},
		
		//funcion  para llenar los campos para editar
		llenarDatosEditar : function(bean){	
			var codEmp = bean.codigoEmpresa.trim();
			$('#codigoEmpresa').val(codEmp);
        	$('#anioReporte').val(bean.anioReporte);
        	$('#mesReporte').val(bean.mesReporte);
					
			fiseCargoFijo.f_numUsuBenefR.val(bean.numUsuBenefR);
			fiseCargoFijo.f_numUsuBenefP.val(bean.numUsuBenefP);
			fiseCargoFijo.f_numUsuBenefL.val(bean.numUsuBenefL);
			
			fiseCargoFijo.f_numUsuEmpR.val(bean.numUsuEmpR);	
			fiseCargoFijo.f_numUsuEmpP.val(bean.numUsuEmpP);	
			fiseCargoFijo.f_numUsuEmpL.val(bean.numUsuEmpL);	
			
			fiseCargoFijo.f_numValDCanR.val(bean.numValDCanR);
			fiseCargoFijo.f_numValDCanP.val(bean.numValDCanP);
			fiseCargoFijo.f_numValDCanL.val(bean.numValDCanL);
			
			fiseCargoFijo.f_numValDEmiR.val(bean.numValDEmiR);
			fiseCargoFijo.f_numValDEmiP.val(bean.numValDEmiP);
			fiseCargoFijo.f_numValDEmiL.val(bean.numValDEmiL);
			
			fiseCargoFijo.f_numValFCanR.val(bean.numValFCanR);
			fiseCargoFijo.f_numValFCanP.val(bean.numValFCanP);
			fiseCargoFijo.f_numValFCanL.val(bean.numValFCanL);
			
			fiseCargoFijo.f_numValFEmiR.val(bean.numValFEmiR);
			fiseCargoFijo.f_numValFEmiP.val(bean.numValFEmiP);
			fiseCargoFijo.f_numValFEmiL.val(bean.numValFEmiL);
			
			fiseCargoFijo.f_numAgenR.val(bean.numAgenteR);
			fiseCargoFijo.f_numAgenP.val(bean.numAgenteP);
			fiseCargoFijo.f_numAgenL.val(bean.numAgenteL);
			
			fiseCargoFijo.f_montoMesR.val(bean.montoMesR);	
			fiseCargoFijo.f_montoMesP.val(bean.montoMesP);
			fiseCargoFijo.f_montoMesL.val(bean.montoMesL);
			
			fiseCargoFijo.f_montoCanje.val(bean.montoCanje);
			fiseCargoFijo.f_numDoc.val(bean.numDoc);			
			fiseCargoFijo.f_numDocRecepcion.val(bean.numDocRecepcion);
			fiseCargoFijo.f_fechaSustento.val(bean.fechaSustento);		
			fiseCargoFijo.f_fechaRecepcion.val(bean.fechaRecepcion);					
			fiseCargoFijo.f_igv.val(bean.igv);		
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
			fiseCargoFijo.soloNumerosEnteros();
			fiseCargoFijo.soloNumerosDecimales();
		},
		
		ocultarElementosEditar : function(){	
			$('#codigoEmpresa').attr("disabled",true);
			$('#anioReporte').attr("disabled",true);
			$('#mesReporte').attr("disabled",true);	
			//estilo
			$('#anioReporte').removeClass("fise-editable");
		},
        verElementosEditar : function(){	
        	$('#codigoEmpresa').attr("disabled",false);
        	$('#anioReporte').attr("disabled",false);
        	$('#mesReporte').attr("disabled",false);
        	//estilo
        	$('#anioReporte').addClass("fise-editable");	
		},
		
		deshabilitarCamposView : function(){			
			fiseCargoFijo.f_numUsuBenefR.attr("disabled",true);
			fiseCargoFijo.f_numUsuBenefP.attr("disabled",true);
			fiseCargoFijo.f_numUsuBenefL.attr("disabled",true);
			
			fiseCargoFijo.f_numUsuEmpR.attr("disabled",true);
			fiseCargoFijo.f_numUsuEmpP.attr("disabled",true);
			fiseCargoFijo.f_numUsuEmpL.attr("disabled",true);
			
			fiseCargoFijo.f_numValDCanR.attr("disabled",true);
			fiseCargoFijo.f_numValDCanP.attr("disabled",true);
			fiseCargoFijo.f_numValDCanL.attr("disabled",true);
			
			fiseCargoFijo.f_numValDEmiR.attr("disabled",true);
			fiseCargoFijo.f_numValDEmiP.attr("disabled",true);
			fiseCargoFijo.f_numValDEmiL.attr("disabled",true);
			
			fiseCargoFijo.f_numValFCanR.attr("disabled",true);
			fiseCargoFijo.f_numValFCanP.attr("disabled",true);
			fiseCargoFijo.f_numValFCanL.attr("disabled",true);
			
			fiseCargoFijo.f_numValFEmiR.attr("disabled",true);
			fiseCargoFijo.f_numValFEmiP.attr("disabled",true);
			fiseCargoFijo.f_numValFEmiL.attr("disabled",true);
			
			fiseCargoFijo.f_numAgenR.attr("disabled",true);
			fiseCargoFijo.f_numAgenP.attr("disabled",true);
			fiseCargoFijo.f_numAgenL.attr("disabled",true);
			
			fiseCargoFijo.f_montoMesR.attr("disabled",true);
			fiseCargoFijo.f_montoMesP.attr("disabled",true);
			fiseCargoFijo.f_montoMesL.attr("disabled",true);
			
			fiseCargoFijo.f_montoCanje.attr("disabled",true);
			fiseCargoFijo.f_numDoc.attr("disabled",true);
			fiseCargoFijo.f_numDocRecepcion.attr("disabled",true);
			fiseCargoFijo.f_fechaSustento.attr("disabled",true);
			fiseCargoFijo.f_fechaRecepcion.attr("disabled",true);
			fiseCargoFijo.f_igv.attr("disabled",true);
			fiseCargoFijo.f_gloza.attr("disabled",true);
			
			$('#rbtIgvSI').attr("disabled",true);
        	$('#rbtIgvNO').attr("disabled",true);
        	$('#rbtActivo').attr("disabled",true);
        	$('#rbtInactivo').attr("disabled",true);
			
			//ESTILOS
			fiseCargoFijo.f_numUsuBenefR.removeClass("fise-editable");
			fiseCargoFijo.f_numUsuBenefP.removeClass("fise-editable");
			fiseCargoFijo.f_numUsuBenefL.removeClass("fise-editable");
			
			fiseCargoFijo.f_numUsuEmpR.removeClass("fise-editable");
			fiseCargoFijo.f_numUsuEmpP.removeClass("fise-editable");	
			fiseCargoFijo.f_numUsuEmpL.removeClass("fise-editable");	
			
			fiseCargoFijo.f_numValDCanR.removeClass("fise-editable");
			fiseCargoFijo.f_numValDCanP.removeClass("fise-editable");
			fiseCargoFijo.f_numValDCanL.removeClass("fise-editable");
			
			fiseCargoFijo.f_numValDEmiR.removeClass("fise-editable");
			fiseCargoFijo.f_numValDEmiP.removeClass("fise-editable");
			fiseCargoFijo.f_numValDEmiL.removeClass("fise-editable");
			
			fiseCargoFijo.f_numValFCanR.removeClass("fise-editable");
			fiseCargoFijo.f_numValFCanP.removeClass("fise-editable");
			fiseCargoFijo.f_numValFCanL.removeClass("fise-editable");
			
			fiseCargoFijo.f_numValFEmiR.removeClass("fise-editable");
			fiseCargoFijo.f_numValFEmiP.removeClass("fise-editable");
			fiseCargoFijo.f_numValFEmiL.removeClass("fise-editable");
			
			fiseCargoFijo.f_numAgenR.removeClass("fise-editable");
			fiseCargoFijo.f_numAgenP.removeClass("fise-editable");
			fiseCargoFijo.f_numAgenL.removeClass("fise-editable");
			
			fiseCargoFijo.f_montoMesR.removeClass("fise-editable");
			fiseCargoFijo.f_montoMesP.removeClass("fise-editable");
			fiseCargoFijo.f_montoMesL.removeClass("fise-editable");
			
			fiseCargoFijo.f_montoCanje.removeClass("fise-editable");
			fiseCargoFijo.f_numDoc.removeClass("fise-editable");		
			fiseCargoFijo.f_numDocRecepcion.removeClass("fise-editable");
			fiseCargoFijo.f_fechaSustento.removeClass("fise-editable");		
			fiseCargoFijo.f_fechaRecepcion.removeClass("fise-editable");
			fiseCargoFijo.f_igv.removeClass("fise-editable");		
			fiseCargoFijo.f_gloza.removeClass("fise-editable");	
			
			fiseCargoFijo.f_fechaRecepcion.removeClass("datepicker");
			
		},
		//Funcion para habilitar los campos que se desabilitan en la visualizacion opcion ver
		habilitarCamposView : function(){			
			fiseCargoFijo.f_numUsuBenefR.removeAttr("disabled");
			fiseCargoFijo.f_numUsuBenefP.removeAttr("disabled");
			fiseCargoFijo.f_numUsuBenefL.removeAttr("disabled");
			
			fiseCargoFijo.f_numUsuEmpR.removeAttr("disabled");
			fiseCargoFijo.f_numUsuEmpP.removeAttr("disabled");
			fiseCargoFijo.f_numUsuEmpL.removeAttr("disabled");
			
			fiseCargoFijo.f_numValDCanR.removeAttr("disabled");
			fiseCargoFijo.f_numValDCanP.removeAttr("disabled");
			fiseCargoFijo.f_numValDCanL.removeAttr("disabled");
			
			fiseCargoFijo.f_numValDEmiR.removeAttr("disabled");
			fiseCargoFijo.f_numValDEmiP.removeAttr("disabled");
			fiseCargoFijo.f_numValDEmiL.removeAttr("disabled");
			
			fiseCargoFijo.f_numValFCanR.removeAttr("disabled");
			fiseCargoFijo.f_numValFCanP.removeAttr("disabled");
			fiseCargoFijo.f_numValFCanL.removeAttr("disabled");
			
			fiseCargoFijo.f_numValFEmiR.removeAttr("disabled");
			fiseCargoFijo.f_numValFEmiP.removeAttr("disabled");
			fiseCargoFijo.f_numValFEmiL.removeAttr("disabled");
			
			fiseCargoFijo.f_numAgenR.removeAttr("disabled");
			fiseCargoFijo.f_numAgenP.removeAttr("disabled");
			fiseCargoFijo.f_numAgenL.removeAttr("disabled");
			
			fiseCargoFijo.f_montoMesR.removeAttr("disabled");
			fiseCargoFijo.f_montoMesP.removeAttr("disabled");
			fiseCargoFijo.f_montoMesL.removeAttr("disabled");		
			
			fiseCargoFijo.f_montoCanje.removeAttr("disabled");
			fiseCargoFijo.f_numDoc.removeAttr("disabled");
			fiseCargoFijo.f_numDocRecepcion.removeAttr("disabled");
			fiseCargoFijo.f_fechaSustento.removeAttr("disabled");
			fiseCargoFijo.f_fechaRecepcion.removeAttr("disabled");
			fiseCargoFijo.f_igv.removeAttr("disabled");
			fiseCargoFijo.f_gloza.removeAttr("disabled");	
			
			$('#rbtIgvSI').removeAttr("disabled");
        	$('#rbtIgvNO').removeAttr("disabled");
        	$('#rbtActivo').removeAttr("disabled");
        	$('#rbtInactivo').removeAttr("disabled");
			
			//ESTILOS			
			fiseCargoFijo.f_numUsuBenefR.addClass("fise-editable");
			fiseCargoFijo.f_numUsuBenefP.addClass("fise-editable");
			fiseCargoFijo.f_numUsuBenefL.addClass("fise-editable");
			
			fiseCargoFijo.f_numUsuEmpR.addClass("fise-editable");
			fiseCargoFijo.f_numUsuEmpP.addClass("fise-editable");	
			fiseCargoFijo.f_numUsuEmpL.addClass("fise-editable");
			
			fiseCargoFijo.f_numValDCanR.addClass("fise-editable");
			fiseCargoFijo.f_numValDCanP.addClass("fise-editable");
			fiseCargoFijo.f_numValDCanL.addClass("fise-editable");
			
			fiseCargoFijo.f_numValDEmiR.addClass("fise-editable");
			fiseCargoFijo.f_numValDEmiP.addClass("fise-editable");
			fiseCargoFijo.f_numValDEmiL.addClass("fise-editable");
			
			fiseCargoFijo.f_numValFCanR.addClass("fise-editable");
			fiseCargoFijo.f_numValFCanP.addClass("fise-editable");
			fiseCargoFijo.f_numValFCanL.addClass("fise-editable");
			
			fiseCargoFijo.f_numValFEmiR.addClass("fise-editable");
			fiseCargoFijo.f_numValFEmiP.addClass("fise-editable");
			fiseCargoFijo.f_numValFEmiL.addClass("fise-editable");
			
			fiseCargoFijo.f_numAgenR.addClass("fise-editable");
			fiseCargoFijo.f_numAgenP.addClass("fise-editable");
			fiseCargoFijo.f_numAgenL.addClass("fise-editable");
			
			fiseCargoFijo.f_montoMesR.addClass("fise-editable");
			fiseCargoFijo.f_montoMesP.addClass("fise-editable");
			fiseCargoFijo.f_montoMesL.addClass("fise-editable");			
			
			fiseCargoFijo.f_montoCanje.addClass("fise-editable");
			fiseCargoFijo.f_numDoc.addClass("fise-editable");	
			fiseCargoFijo.f_fechaRecepcion.addClass("fise-editable");
			fiseCargoFijo.f_numDocRecepcion.addClass("fise-editable");
			fiseCargoFijo.f_fechaSustento.addClass("fise-editable");			
			fiseCargoFijo.f_igv.addClass("fise-editable");		
			fiseCargoFijo.f_gloza.addClass("fise-editable");
			
			fiseCargoFijo.f_fechaRecepcion.addClass("datepicker");
		},
		
		/**Function para confirmar si quiere eliminar el registro o no*/
		confirmarEliminarCargoFijo : function(codEmpresa, anioRep, mesRep){			
			var addhtml='�Est� seguro que desea eliminar el Dato del Proyecto Fise seleccionado?';
			fiseCargoFijo.dialogConfirmContent.html(addhtml);
			fiseCargoFijo.dialogConfirm.dialog("open");	
			console.debug("codigo empresa: "+codEmpresa);
			cod_empresa = codEmpresa;
			anio_rep = anioRep;
			mes_rep = mesRep;
		},
		
		/**Function para  eliminar el registro una vez hecho la confirmacion*/
		eliminarCargoFijo : function(cod_empresa, anio_rep, mes_rep){
			console.debug("entranado a eliminar ");
			console.debug("codigo empresa: "+cod_empresa);
			$.blockUI({ message: fiseCargoFijo.mensajeEliminando });
			jQuery.ajax({
				url: fiseCargoFijo.urlEliminar+'&'+fiseCargoFijo.formCommand.serialize(),
				type: 'post',
				dataType: 'json',
				data: {				
					<portlet:namespace />codigoEmp: cod_empresa,
				    <portlet:namespace />anioRep: anio_rep,
				    <portlet:namespace />codigoMes: mes_rep				     
					},
				success: function(data) {
					if (data.resultado == "OK"){
						var addhtml2='El Dato del Proyecto Fise fue eliminado con �xito';					
						fiseCargoFijo.dialogMessageContent.html(addhtml2);
						fiseCargoFijo.dialogMessage.dialog("open");
						fiseCargoFijo.botonBuscar.trigger('click');
						fiseCargoFijo.initBlockUI();
					}
					else{
						alert("Error al eliminar el Dato del Proyecto Fise");
						fiseCargoFijo.initBlockUI();
					}
				},error : function(){
					alert("Error de conexi�n.");
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
					    <portlet:namespace />codigoEmp: fiseCargoFijo.f_empresa.val(),
					    <portlet:namespace />anioRep: fiseCargoFijo.f_anioRep.val(),
					    <portlet:namespace />codigoMes: fiseCargoFijo.f_mesRep.val()
						},
					success: function(data) {			
						if (data.resultado == "OK"){				
							var addhtml2='El Dato del Proyecto Fise se guard� satisfactoriamente';
							
							fiseCargoFijo.dialogMessageContent.html(addhtml2);
							fiseCargoFijo.dialogMessage.dialog("open");							
							fiseCargoFijo.initBlockUI();
							$('#<portlet:namespace/>guardarCargoFijo').css('display','none');
							$('#<portlet:namespace/>actualizarCargoFijo').css('display','block');				
							
						}else if(data.resultado == "Error"){				
							var addhtml2='Se produjo un error al guardar el Dato del Proyecto Fise.';
							fiseCargoFijo.dialogMessageContent.html(addhtml2);
							fiseCargoFijo.dialogMessage.dialog("open");						
							fiseCargoFijo.initBlockUI();
						}else if(data.resultado=="Duplicado"){
							var addhtml2='Ya existe registrado un Dato del Proyecto Fise con la misma Dist.El�ct, A�o y Mes';
							fiseCargoFijo.dialogMessageContent.html(addhtml2);
							fiseCargoFijo.dialogMessage.dialog("open");						
							fiseCargoFijo.initBlockUI();
						}
					},error : function(){
						alert("Error de conexi�n.");
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
						<portlet:namespace />codigoEmp: fiseCargoFijo.f_empresa.val(),
					    <portlet:namespace />anioRep: fiseCargoFijo.f_anioRep.val(),
					    <portlet:namespace />codigoMes: fiseCargoFijo.f_mesRep.val()	
						},
					success: function(data) {			
						if (data.resultado == "OK"){				
							var addhtml2='El Dato del Proyecto Fise se actualiz� satisfactoriamente';
							fiseCargoFijo.dialogMessageContent.html(addhtml2);
							fiseCargoFijo.dialogMessage.dialog("open");						
							fiseCargoFijo.initBlockUI();								
						}else if(data.resultado == "Error"){				
							var addhtml2='Se produjo un error al actualizar el Dato del Proyecto Fise.';
							fiseCargoFijo.dialogMessageContent.html(addhtml2);
							fiseCargoFijo.dialogMessage.dialog("open");						
							fiseCargoFijo.initBlockUI();
						}
					},error : function(){
						alert("Error de conexi�n.");
						fiseCargoFijo.initBlockUI();
					}
				});						
			}
		},
		//funcion para validar ingreso de datos
		validarFormulario : function() {		
			if(fiseCargoFijo.f_empresa.val().length == ''){
				alert('Debe seleccionar una Distribuidora El�ctrica.'); 
				fiseCargoFijo.f_empresa.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_anioRep.val().length == ''){
				alert('Debe ingresar a�o de reporte.'); 
				fiseCargoFijo.f_anioRep.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_mesRep.val().length == ''){
				alert('Debe seleccionar un mes de reporte.'); 
				fiseCargoFijo.f_mesRep.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_numUsuBenefR.val().length == ''){
				alert('Debe ingresar n�mero de usuarios beneficiados Rural.'); 
				fiseCargoFijo.f_numUsuBenefR.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_numUsuBenefP.val().length == ''){
				alert('Debe ingresar n�mero de usuarios beneficiados Urb. Provincias.'); 
				fiseCargoFijo.f_numUsuBenefP.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_numUsuBenefL.val().length == ''){
				alert('Debe ingresar n�mero de usuarios beneficiados Urb. Lima.'); 
				fiseCargoFijo.f_numUsuBenefL.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_numUsuEmpR.val().length == ''){
				alert('Debe ingresar n�mero de usuarios empadronados Rural.'); 
				fiseCargoFijo.f_numUsuEmpR.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_numUsuEmpP.val().length == ''){
				alert('Debe ingresar n�mero de usuarios empadronados Urb. Provincias.'); 
				fiseCargoFijo.f_numUsuEmpP.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_numUsuEmpL.val().length == ''){
				alert('Debe ingresar n�mero de usuarios empadronados Urb. Lima.'); 
				fiseCargoFijo.f_numUsuEmpL.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_numValFEmiR.val().length == ''){
				alert('Debe ingresar n�mero de vales f�sicos emitidos Rural.'); 
				fiseCargoFijo.f_numValFEmiR.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_numValFEmiP.val().length == ''){
				alert('Debe ingresar n�mero de vales f�sicos emitidos Urb. Provincias.'); 
				fiseCargoFijo.f_numValFEmiP.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_numValFEmiL.val().length == ''){
				alert('Debe ingresar n�mero de vales f�sicos emitidos Urb. Lima.'); 
				fiseCargoFijo.f_numValFEmiL.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_numValDEmiR.val().length == ''){
				alert('Debe ingresar n�mero de vales digitales emitidos Rural.'); 
				fiseCargoFijo.f_numValDEmiR.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_numValDEmiP.val().length == ''){
				alert('Debe ingresar n�mero de vales digitales emitidos Urb. Provincias.'); 
				fiseCargoFijo.f_numValDEmiP.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_numValDEmiL.val().length == ''){
				alert('Debe ingresar n�mero de vales digitales emitidos Urb. Lima.'); 
				fiseCargoFijo.f_numValDEmiL.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_numValFCanR.val().length == ''){
				alert('Debe ingresar n�mero de vales f�sicos canjeados Rural.'); 
				fiseCargoFijo.f_numValFCanR.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_numValFCanP.val().length == ''){
				alert('Debe ingresar n�mero de vales f�sicos canjeados Urb. Provincias.'); 
				fiseCargoFijo.f_numValFCanP.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_numValFCanL.val().length == ''){
				alert('Debe ingresar n�mero de vales f�sicos canjeados Urb. Lima.'); 
				fiseCargoFijo.f_numValFCanL.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_numValDCanR.val().length == ''){
				alert('Debe ingresar n�mero de vales digitales canjeados Rural.'); 
				fiseCargoFijo.f_numValDCanR.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_numValDCanP.val().length == ''){
				alert('Debe ingresar n�mero de vales digitales canjeados Urb. Provincias.'); 
				fiseCargoFijo.f_numValDCanP.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_numValDCanL.val().length == ''){
				alert('Debe ingresar n�mero de vales digitales canjeados Urb. Lima.'); 
				fiseCargoFijo.f_numValDCanL.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_numAgenR.val().length == ''){
				alert('Debe ingresar n�mero de agentes Rural.'); 
				fiseCargoFijo.f_numAgenR.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_numAgenP.val().length == ''){
				alert('Debe ingresar n�mero de agentes Urb. Provincias.'); 
				fiseCargoFijo.f_numAgenP.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_numAgenL.val().length == ''){
				alert('Debe ingresar n�mero de agentes Urb. Lima.'); 
				fiseCargoFijo.f_numAgenL.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_montoMesR.val().length == ''){
				alert('Debe ingresar monto de cargo fijo al mes Rural.'); 
				fiseCargoFijo.f_montoMesR.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_montoMesP.val().length == ''){
				alert('Debe ingresar monto de cargo fijo al mes Urb. Provincias.'); 
				fiseCargoFijo.f_montoMesP.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_montoMesL.val().length == ''){
				alert('Debe ingresar monto de cargo fijo al mes Urb. Lima.'); 
				fiseCargoFijo.f_montoMesL.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_montoCanje.val().length == ''){
				alert('Debe ingresar monto transferido por canje.'); 
				fiseCargoFijo.f_montoCanje.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_igv.val().length == ''){
				alert('Debe ingresar IGV.'); 
				fiseCargoFijo.f_igv.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_fechaRecepcion.val().length != '' && 
					!validaFechaDDMMAAAA(fiseCargoFijo.f_fechaRecepcion.val()) ){
				alert('Debe ingresar una fecha de recepci�n v�lida');
				fiseCargoFijo.f_fechaRecepcion.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_fechaSustento.val().length != '' && 
					!validaFechaDDMMAAAA(fiseCargoFijo.f_fechaSustento.val())){
				alert('Debe ingresar una fecha de sustento v�lida'); 
				fiseCargoFijo.f_fechaSustento.focus();
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
						console.debug("dialogo cod empresa "+cod_empresa);
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