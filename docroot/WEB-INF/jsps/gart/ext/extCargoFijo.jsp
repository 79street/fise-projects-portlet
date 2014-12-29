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
			this.f_numUsuBenef=$('#numUsuBenef');
			this.f_numUsuEmp=$('#numUsuEmp');			
			this.f_numValDCan=$('#numValDCan');
			this.f_numValDEmi=$('#numValDEmi');			
			this.f_numValFCan=$('#numValFCan');
			this.f_numValFEmi=$('#numValFEmi');			
			this.f_numAgen=$('#numAgente');
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
		       colNames: ['Dist. Eléct.','Año Repo.','Mes Repo.','N° Usu. Benef.','N° Usu. Emp.','N° Vales Físicos Emi.','N° Vales Físicos Canj.','N° Vales Digitales Emi.','Estado','Visualizar','Editar','Anular','',''],
		       colModel: [
						   { name: 'desEmpresa', index: 'desEmpresa', width: 50},
			               { name: 'anioReporte', index: 'anioReporte', width: 30 },   
			               { name: 'desMesRep', index: 'desMesRep', width: 30},
			               { name: 'numUsuBenef', index: 'numUsuBenef', width: 30 },   
			               { name: 'numUsuEmp', index: 'numUsuEmp', width: 30},
			               { name: 'numValFEmi', index: 'numValFEmi', width: 50},
			               { name: 'numValFCan', index: 'numValFCan', width: 50},
			               { name: 'numValDEmi', index: 'numValFEmi', width: 50},
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
					   	caption: "Resultado(s) de la búsqueda",
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
			
			fiseCargoFijo.f_numUsuBenef.val('0');
			fiseCargoFijo.f_numUsuEmp.val('0');			
			fiseCargoFijo.f_numValDCan.val('0');
			fiseCargoFijo.f_numValDEmi.val('0');			
			fiseCargoFijo.f_numValFCan.val('0');
			fiseCargoFijo.f_numValFEmi.val('0');			
			fiseCargoFijo.f_numAgen.val('0');
			fiseCargoFijo.f_montoMes.val('0.00');			
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
			fiseCargoFijo.f_numUsuBenef.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numUsuBenef',7,0)");
			fiseCargoFijo.f_numUsuEmp.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numUsuEmp',7,0)");			
			fiseCargoFijo.f_numValDCan.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numValDCan',7,0)");
			fiseCargoFijo.f_numValDEmi.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numValDEmi',7,0)");		
			fiseCargoFijo.f_numValFCan.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numValFCan',7,0)");
			fiseCargoFijo.f_numValFEmi.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numValFEmi',7,0)");			
			fiseCargoFijo.f_numAgen.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numAgente',7,0)");
			fiseCargoFijo.f_numDoc.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numDoc',20,0)");			
			fiseCargoFijo.f_numDocRecepcion.attr("onkeypress","return soloNumerosDecimales(event, 1, 'numDocRecepcion',20,0)");		
		},
		
		//function para validar solo numeros decimales
		soloNumerosDecimales : function(){			
			fiseCargoFijo.f_montoMes.attr("onkeypress","return soloNumerosDecimales(event, 2, 'montoMes',7,2)");
			fiseCargoFijo.f_montoCanje.attr("onkeypress","return soloNumerosDecimales(event, 2, 'montoCanje',7,2)");
			fiseCargoFijo.f_igv.attr("onkeypress","return soloNumerosDecimales(event, 2, 'igv',1,2)");	
			completarDecimal('montoMes',2);
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
						alert("Error de conexión.");
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
							alert("Error de conexión.");
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
			//fiseCargoFijo.f_empresa.val(bean.codEmpresa.trim());			
			//fiseCargoFijo.f_anioRep.val(bean.anioReporte);
			//fiseCargoFijo.f_mesRep.val(bean.mesReporte);			
			fiseCargoFijo.f_numUsuBenef.val(bean.numUsuBenef);
			fiseCargoFijo.f_numUsuEmp.val(bean.numUsuEmp);			
			fiseCargoFijo.f_numValDCan.val(bean.numValDCan);
			fiseCargoFijo.f_numValDEmi.val(bean.numValDEmi);			
			fiseCargoFijo.f_numValFCan.val(bean.numValFCan);
			fiseCargoFijo.f_numValFEmi.val(bean.numValFEmi);			
			fiseCargoFijo.f_numAgen.val(bean.numAgente);
			fiseCargoFijo.f_montoMes.val(bean.montoMes);			
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
			fiseCargoFijo.f_numUsuBenef.attr("disabled",true);
			fiseCargoFijo.f_numUsuEmp.attr("disabled",true);
			fiseCargoFijo.f_numValDCan.attr("disabled",true);
			fiseCargoFijo.f_numValDEmi.attr("disabled",true);
			fiseCargoFijo.f_numValFCan.attr("disabled",true);
			fiseCargoFijo.f_numValFEmi.attr("disabled",true);
			fiseCargoFijo.f_numAgen.attr("disabled",true);
			fiseCargoFijo.f_montoMes.attr("disabled",true);
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
			fiseCargoFijo.f_numUsuBenef.removeClass("fise-editable");
			fiseCargoFijo.f_numUsuEmp.removeClass("fise-editable");	
			fiseCargoFijo.f_numValDCan.removeClass("fise-editable");
			fiseCargoFijo.f_numValDEmi.removeClass("fise-editable");		
			fiseCargoFijo.f_numValFCan.removeClass("fise-editable");
			fiseCargoFijo.f_numValFEmi.removeClass("fise-editable");				
			fiseCargoFijo.f_numAgen.removeClass("fise-editable");
			fiseCargoFijo.f_montoMes.removeClass("fise-editable");		
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
			fiseCargoFijo.f_numUsuBenef.removeAttr("disabled");
			fiseCargoFijo.f_numUsuEmp.removeAttr("disabled");
			fiseCargoFijo.f_numValDCan.removeAttr("disabled");
			fiseCargoFijo.f_numValDEmi.removeAttr("disabled");
			fiseCargoFijo.f_numValFCan.removeAttr("disabled");
			fiseCargoFijo.f_numValFEmi.removeAttr("disabled");
			fiseCargoFijo.f_numAgen.removeAttr("disabled");
			fiseCargoFijo.f_montoMes.removeAttr("disabled");
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
			fiseCargoFijo.f_numUsuBenef.addClass("fise-editable");
			fiseCargoFijo.f_numUsuEmp.addClass("fise-editable");	
			fiseCargoFijo.f_numValDCan.addClass("fise-editable");
			fiseCargoFijo.f_numValDEmi.addClass("fise-editable");		
			fiseCargoFijo.f_numValFCan.addClass("fise-editable");
			fiseCargoFijo.f_numValFEmi.addClass("fise-editable");				
			fiseCargoFijo.f_numAgen.addClass("fise-editable");
			fiseCargoFijo.f_montoMes.addClass("fise-editable");		
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
			var addhtml='¿Está seguro que desea eliminar el Cargo Fijo seleccionado?';
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
						var addhtml2='El Cargo Fijo fue eliminado con éxito';					
						fiseCargoFijo.dialogMessageContent.html(addhtml2);
						fiseCargoFijo.dialogMessage.dialog("open");
						fiseCargoFijo.botonBuscar.trigger('click');
						fiseCargoFijo.initBlockUI();
					}
					else{
						alert("Error al eliminar el registro de Cargo Fijo");
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
					    <portlet:namespace />codigoEmp: fiseCargoFijo.f_empresa.val(),
					    <portlet:namespace />anioRep: fiseCargoFijo.f_anioRep.val(),
					    <portlet:namespace />codigoMes: fiseCargoFijo.f_mesRep.val()
						},
					success: function(data) {			
						if (data.resultado == "OK"){				
							var addhtml2='El Cargo Fijo se guardó satisfactoriamente';
							
							fiseCargoFijo.dialogMessageContent.html(addhtml2);
							fiseCargoFijo.dialogMessage.dialog("open");							
							fiseCargoFijo.initBlockUI();
							$('#<portlet:namespace/>guardarCargoFijo').css('display','none');
							$('#<portlet:namespace/>actualizarCargoFijo').css('display','block');				
							
						}else if(data.resultado == "Error"){				
							var addhtml2='Se produjo un error al guardar el Cargo Fijo.';
							fiseCargoFijo.dialogMessageContent.html(addhtml2);
							fiseCargoFijo.dialogMessage.dialog("open");						
							fiseCargoFijo.initBlockUI();
						}else if(data.resultado=="Duplicado"){
							var addhtml2='Ya existe registrado un Cargo Fijo con la misma Dist.Eléct, Año y Mes';
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
						<portlet:namespace />codigoEmp: fiseCargoFijo.f_empresa.val(),
					    <portlet:namespace />anioRep: fiseCargoFijo.f_anioRep.val(),
					    <portlet:namespace />codigoMes: fiseCargoFijo.f_mesRep.val()	
						},
					success: function(data) {			
						if (data.resultado == "OK"){				
							var addhtml2='El Cargo Fijo se actualizó satisfactoriamente';
							fiseCargoFijo.dialogMessageContent.html(addhtml2);
							fiseCargoFijo.dialogMessage.dialog("open");						
							fiseCargoFijo.initBlockUI();								
						}else if(data.resultado == "Error"){				
							var addhtml2='Se produjo un error al actualizar el Cargo Fijo.';
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
			if(fiseCargoFijo.f_empresa.val().length == ''){
				alert('Debe seleccionar una Distribuidora Eléctrica.'); 
				fiseCargoFijo.f_empresa.focus();
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
			}else if(fiseCargoFijo.f_fechaRecepcion.val().length != '' && 
					!validaFechaDDMMAAAA(fiseCargoFijo.f_fechaRecepcion.val()) ){
				alert('Debe ingresar una fecha de recepción válida');
				fiseCargoFijo.f_fechaRecepcion.focus();
			  	return false; 
			}else if(fiseCargoFijo.f_fechaSustento.val().length != '' && 
					!validaFechaDDMMAAAA(fiseCargoFijo.f_fechaSustento.val())){
				alert('Debe ingresar una fecha de sustento válida'); 
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