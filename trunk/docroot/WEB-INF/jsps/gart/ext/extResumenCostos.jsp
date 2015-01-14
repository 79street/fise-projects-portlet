<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects />

<script type="text/javascript">

var resumenCosto= {
		
	    //decalaracion de los elementos del html	
		formCommand : null,	
		
			
		//dialogos	
		dialogMessage:null,
		dialogMessageContent:null,
		
		dialogConfirm:null,//para eliminar
		dialogConfirmContent:null,//para mostrar la confirmacion de eliminar		
		
		dialogValidacion:null,
		dialogValidacionContent:null,
		dialogError:null,
		dialogErrorContent:null,
		dialogInfo:null,
		dialogInfoContent:null,
		
		
		//divs
		divF14A:null,
		divF14APdf:null,
		divF14AExcel:null,	
		divF12A:null,
		divF12APdf:null,
		divF12AExcel:null,
		
		div_F14AB_opc:null,
		div_F14AB_zona:null,
		//divF12B:null,
		
		//mensajes			
		mensajePrepara:null,		
		mensajeReporte:null,
			
		//urls		
	    urlCargaGrupoInf:null,    
	    urlVerF14A:null,
	    urlVerF14AExcel:null,
	    urlVerF14B:null,
	    urlVerF14BExcel:null,
	    urlVerF12A:null,
	    urlVerF12AExcel:null,
	    urlVerF12B:null,	
	    urlVerF12BExcel:null,		
		
		//botones
		botonExportar:null,
		//botonF14A:null,
		//botonF14AExcel:null,	
		//botonF14B:null,	
		//botonF14BExcel:null,		
		botonF12A:null,
		botonF12AExcel:null,
		botonF12B:null,
		botonF12BExcel:null,
		
		//varibales de busqueda
		i_codEmpresaBusq:null,
		i_grupoInfBusq:null,		
		i_tipoBienal:null,
		i_tipoMensual:null,
		
		i_cboBienal:null,
		i_cboZonas:null,		
		
		i_formatoPdf:null,
		i_formatoExcel:null,
		
		i_tipoExport:null,
				
		init : function() {
			
			this.formCommand=$('#resumenCostoBean');				
			
			//dialogos
			this.dialogMessage=$("#<portlet:namespace/>dialog-message-resumen_costos");
			this.dialogMessageContent=$("#<portlet:namespace/>dialog-message-content-resumen_costos");
			
			this.dialogConfirm=$("#<portlet:namespace/>dialog-confirm-eliminar");//para elimar
			this.dialogConfirmContent=$("#<portlet:namespace/>dialog-confirm-content-eliminar");//para eliminar
			
			this.dialogValidacion=$("#<portlet:namespace/>dialog-alert");	
			this.dialogValidacionContent=$("#<portlet:namespace/>dialog-alert-content");
			this.dialogError=$("#<portlet:namespace/>dialog-error");
			this.dialogErrorContent=$("#<portlet:namespace/>dialog-error-content");	
            this.dialogInfo=$("#<portlet:namespace/>dialog-info");
			this.dialogInfoContent=$("#<portlet:namespace/>dialog-info-content");	
			
			//divs
			this.divF14A=$("#<portlet:namespace/>div_F14A");			
			//this.divF14AExcel=$("#<portlet:namespace/>div_F14A_excel");
			this.divF12A=$("#<portlet:namespace/>div_F12A");
			this.divF12APdf=$("#<portlet:namespace/>div_F12A_pdf");
			this.divF12AExcel=$("#<portlet:namespace/>div_F12A_excel");
			
			this.div_F14AB_opc=$("#<portlet:namespace/>div_F14B_opciones");
			this.div_F14AB_zona=$("#<portlet:namespace/>div_F14B_zonas");
			
			
			//mensajes						
			
			this.mensajePrepara='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Generando Liq. </h3>';
			this.mensajeReporte='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Obteniendo el Reporte </h3>';
			
					
			//urls		
			this.urlCargaGrupoInf='<portlet:resourceURL id="cargarGrupoInformacion" />';		
			this.urlVerF14A='<portlet:resourceURL id="verResumenCostoF14A" />';
			this.urlVerF14AExcel='<portlet:resourceURL id="verResumenCostoF14AExcel" />';			
			this.urlVerF14B='<portlet:resourceURL id="verResumenCostoF14B" />'; 
			this.urlVerF14BExcel='<portlet:resourceURL id="verResumenCostoF14BExcel" />'; 
			this.urlVerF12A='<portlet:resourceURL id="verResumenCostoF12A" />'; 
			this.urlVerF12AExcel='<portlet:resourceURL id="verResumenCostoF12AExcel" />'; 	
			this.urlVerF12B='<portlet:resourceURL id="verResumenCostoF12B" />'; 
			this.urlVerF12BExcel='<portlet:resourceURL id="verResumenCostoF12BExcel" />'; 
					
			//botones			
			this.botonExportar=$("#<portlet:namespace/>btnExportar");
			//this.botonF14A=$("#<portlet:namespace/>btnF14A");			
			//this.botonF14AExcel=$("#<portlet:namespace/>btnF14AExcel");
			//this.botonF14B=$("#<portlet:namespace/>btnF14B");
			//this.botonF14BExcel=$("#<portlet:namespace/>btnF14BExcel");
			this.botonF12A=$("#<portlet:namespace/>btnF12A");
			this.botonF12AExcel=$("#<portlet:namespace/>btnF12AExcel");
			this.botonF12B=$("#<portlet:namespace/>btnF12B");
			this.botonF12BExcel=$("#<portlet:namespace/>btnF12BExcel");
			
			//variables de busqueda
			this.i_codEmpresaBusq=$('#codEmpresaBusq');
			this.i_grupoInfBusq=$('#grupoInfBusq');				
			this.i_tipoBienal=$('#rbtBienal');
			this.i_tipoMensual=$('#rbtMensual');
			
			this.i_formatoPdf=$('#rbtPdf');
			this.i_formatoExcel=$('#rbtExcel');	
			
			this.i_cboBienal=$('#optionBienal');
			this.i_cboZonas=$('#optionZona');
			
			this.i_tipoExport=$('#formatoExportar');			
			
						
			//llamado a la funciones de cada boton
			
			//boton export pdf 14A
			resumenCosto.botonExportar.click(function() {
				console.debug("entranado a exportar los bienales ");
				console.debug("entranado a exportar los bienales: "+resumenCosto.i_cboBienal.val());
				var valorPdf = document.getElementById('rbtPdf').checked;
				var valorExcel = document.getElementById('rbtExcel').checked;
				console.debug("entranado a exportar los bienales PDF: "+valorPdf);	
				console.debug("entranado a exportar los bienales Excel: "+valorExcel);	
				
				if(valorPdf && resumenCosto.i_cboBienal.val()=='F14A'){					
					resumenCosto.mostrarReporteCostoF14A();	
				}else if(valorExcel && resumenCosto.i_cboBienal.val()=='F14A'){
					resumenCosto.mostrarReporteCostoF14AExcel();
				}else if(valorPdf && resumenCosto.i_cboBienal.val()=='F14B'){
					resumenCosto.mostrarReporteCostoF14B();
				}else if(valorExcel && resumenCosto.i_cboBienal.val()=='F14B'){
					resumenCosto.mostrarReporteCostoF14BExcel();
				}else{					
				}				
			});			
			
			//boton export pdf 14A
			/* resumenCosto.botonF14A.click(function() {			
				resumenCosto.mostrarReporteCostoF14A();
			}); */
			
			//boton export excel 14A
			/* resumenCosto.botonF14AExcel.click(function() {			
				resumenCosto.mostrarReporteCostoF14AExcel();
			}); */
			
			//boton export pdf 14B
			/* resumenCosto.botonF14B.click(function() {			
				//resumenCosto.mostrarReporteCostoF14B();
				resumenCosto.div_F14B_opc.show();
			}); */
			
			//boton export excel 14B
			/* resumenCosto.botonF14BExcel.click(function() {			
				resumenCosto.mostrarReporteCostoF14BExcel();
			}); */
			
			
			
			
			//boton export pdf 12A
			resumenCosto.botonF12A.click(function() {			
				resumenCosto.mostrarReporteCostoF12A();				
			});
			
			//boton export excel 12A
			resumenCosto.botonF12AExcel.click(function() {			
				resumenCosto.mostrarReporteCostoF12AExcel();				
			});
			
			//boton export pdf 12B
			resumenCosto.botonF12B.click(function() {	
				resumenCosto.mostrarReporteCostoF12B();				
			});	
			
			//boton export exel 12B
			resumenCosto.botonF12BExcel.click(function() {	
				resumenCosto.mostrarReporteCostoF12BExcel();				
			});	
			
			resumenCosto.initDialogs();		    
		    
			//evento change para bienales
			resumenCosto.i_tipoBienal.change(function(){
				resumenCosto.<portlet:namespace/>loadGrupoInformacion();
				resumenCosto.divF14A.show();
				resumenCosto.divF12A.hide();							
			});
			
			//evento change para reportes en pdf
			resumenCosto.i_formatoPdf.change(function(){				
				//resumenCosto.divF14AExcel.hide();
				//resumenCosto.divF14APdf.show();
				resumenCosto.div_F14AB_opc.show();				
				resumenCosto.divF12AExcel.hide();
				resumenCosto.divF12APdf.show();
			});
			
			//evento change para mensuales
			resumenCosto.i_tipoMensual.change(function(){
				resumenCosto.<portlet:namespace/>loadGrupoInformacion();
				resumenCosto.divF14A.hide();
				resumenCosto.divF12A.show();					
			});
			
			//evento change para reportes en excel
			resumenCosto.i_formatoExcel.change(function(){				
				//resumenCosto.divF14AExcel.show();
				//resumenCosto.divF14APdf.hide();
				resumenCosto.div_F14AB_opc.show();
				resumenCosto.divF12AExcel.show();
				resumenCosto.divF12APdf.hide();
			});
			
			
			resumenCosto.i_cboBienal.change(function(){	
				if(resumenCosto.i_cboBienal.val()=='F14A'){
					resumenCosto.div_F14AB_zona.hide();		
				}else{
					resumenCosto.div_F14AB_zona.show();		
				}							
			});			
		    		   
			resumenCosto.i_tipoMensual.trigger('change');
			
			resumenCosto.i_formatoPdf.trigger('change');
			
		},			
		
		//function para el evento onchange en empresa para cargar el periodo
		<portlet:namespace/>loadGrupoInformacion : function(){	
			console.debug("entranado a cargar grupoInfo");
			jQuery.ajax({
					url: resumenCosto.urlCargaGrupoInf+'&'+resumenCosto.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					success: function(data) {		
						dwr.util.removeAllOptions("grupoInfBusq");
						dwr.util.addOptions("grupoInfBusq", data,"codigoItem","descripcionItem");														
					},error : function(){
						var addhtmError='Error de conexión.';					
						resumenCosto.dialogErrorContent.html(addhtmError);
						resumenCosto.dialogError.dialog("open");
						resumenCosto.initBlockUI();
					}
			});
		},	
		//funcion para mostrar reporte de resumen de costos F14A		
		mostrarReporteCostoF14A : function(){
			console.debug("entranado a  ver el reporte resumen costo F14A");
			if (resumenCosto.validarBusqueda()){
				$.blockUI({ message: resumenCosto.mensajeReporte});
				jQuery.ajax({
					url: resumenCosto.urlVerF14A+'&'+resumenCosto.formCommand.serialize(),
					type : 'post',
					dataType : 'json',
					data : {					
						   <portlet:namespace />codEmpresaBusq: resumenCosto.i_codEmpresaBusq.val(),
						   <portlet:namespace />grupoInfBusq: resumenCosto.i_grupoInfBusq.val()				  
					},
					success : function(data) {
						if(data.resultado=='OK'){
							resumenCosto.verReporteCostos();	
							resumenCosto.initBlockUI();
						}else if(data.resultado=='VACIO'){							
							var addhtmInfo='No existe ningún dato para los criterios seleccionados.';					
							resumenCosto.dialogInfoContent.html(addhtmInfo);
							resumenCosto.dialogInfo.dialog("open");	
							resumenCosto.initBlockUI();
						}else{							
							var addhtmError='Error al mostrar el reporte del resumen de costos de F14A';					
							resumenCosto.dialogErrorContent.html(addhtmError);
							resumenCosto.dialogError.dialog("open");	
							resumenCosto.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						resumenCosto.dialogErrorContent.html(addhtmError);
						resumenCosto.dialogError.dialog("open");
						resumenCosto.initBlockUI();
					}
				});	
			}		
		},
		
		
		//funcion para mostrar reporte de resumen de costos F14A		
		mostrarReporteCostoF14AExcel : function(){
			console.debug("entranado a  ver el reporte resumen costo F14A Excel");
			if (resumenCosto.validarBusqueda()){
				$.blockUI({ message: resumenCosto.mensajeReporte});
				jQuery.ajax({
					url: resumenCosto.urlVerF14AExcel+'&'+resumenCosto.formCommand.serialize(),
					type : 'post',
					dataType : 'json',
					data : {					
						   <portlet:namespace />codEmpresaBusq: resumenCosto.i_codEmpresaBusq.val(),
						   <portlet:namespace />grupoInfBusq: resumenCosto.i_grupoInfBusq.val()				  
					},
					success : function(data) {
						if(data.resultado=='OK'){
							resumenCosto.verReporteCostos();	
							resumenCosto.initBlockUI();
						}else if(data.resultado=='VACIO'){							
							var addhtmInfo='No existe ningún dato para los criterios seleccionados.';					
							resumenCosto.dialogInfoContent.html(addhtmInfo);
							resumenCosto.dialogInfo.dialog("open");	
							resumenCosto.initBlockUI();
						}else{							
							var addhtmError='Error al mostrar el reporte del resumen de costos de F14A';					
							resumenCosto.dialogErrorContent.html(addhtmError);
							resumenCosto.dialogError.dialog("open");	
							resumenCosto.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						resumenCosto.dialogErrorContent.html(addhtmError);
						resumenCosto.dialogError.dialog("open");
						resumenCosto.initBlockUI();
					}
				});	
			}		
		},
		
		//funcion para mostrar reporte de resumen de costos F14B		
		mostrarReporteCostoF14B : function(){
			console.debug("entranado a  ver el reporte resumen costo F14B");
			if (resumenCosto.validarBusqueda()){
				$.blockUI({ message: resumenCosto.mensajeReporte});
				jQuery.ajax({
					url: resumenCosto.urlVerF14B+'&'+resumenCosto.formCommand.serialize(),
					type : 'post',
					dataType : 'json',
					data : {					
						   <portlet:namespace />codEmpresaBusq: resumenCosto.i_codEmpresaBusq.val(),
						   <portlet:namespace />grupoInfBusq: resumenCosto.i_grupoInfBusq.val(),
						   <portlet:namespace />optionZona: resumenCosto.i_cboZonas.val()
					},
					success : function(data) {
						if(data.resultado=='OK'){
							resumenCosto.verReporteCostos();	
							resumenCosto.initBlockUI();
						}else if(data.resultado=='VACIO'){							
							var addhtmInfo='No existe ningún dato para los criterios seleccionados.';					
							resumenCosto.dialogInfoContent.html(addhtmInfo);
							resumenCosto.dialogInfo.dialog("open");	
							resumenCosto.initBlockUI();
						}else{							
							var addhtmError='Error al mostrar el reporte del resumen de costos de F14B';					
							resumenCosto.dialogErrorContent.html(addhtmError);
							resumenCosto.dialogError.dialog("open");	
							resumenCosto.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						resumenCosto.dialogErrorContent.html(addhtmError);
						resumenCosto.dialogError.dialog("open");
						resumenCosto.initBlockUI();
					}
				});	
			}		
		}, 
		
		//funcion para mostrar reporte de resumen de costos F14B excel		
		mostrarReporteCostoF14BExcel : function(){
			console.debug("entranado a  ver el reporte resumen costo F14B excel");
			if (resumenCosto.validarBusqueda()){
				$.blockUI({ message: resumenCosto.mensajeReporte});
				jQuery.ajax({
					url: resumenCosto.urlVerF14BExcel+'&'+resumenCosto.formCommand.serialize(),
					type : 'post',
					dataType : 'json',
					data : {					
						   <portlet:namespace />codEmpresaBusq: resumenCosto.i_codEmpresaBusq.val(),
						   <portlet:namespace />grupoInfBusq: resumenCosto.i_grupoInfBusq.val(),
						   <portlet:namespace />optionZona: resumenCosto.i_cboZonas.val()
					},
					success : function(data) {
						if(data.resultado=='OK'){
							resumenCosto.verReporteCostos();	
							resumenCosto.initBlockUI();
						}else if(data.resultado=='VACIO'){							
							var addhtmInfo='No existe ningún dato para los criterios seleccionados.';					
							resumenCosto.dialogInfoContent.html(addhtmInfo);
							resumenCosto.dialogInfo.dialog("open");	
							resumenCosto.initBlockUI();
						}else{							
							var addhtmError='Error al mostrar el reporte del resumen de costos de F14B';					
							resumenCosto.dialogErrorContent.html(addhtmError);
							resumenCosto.dialogError.dialog("open");	
							resumenCosto.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						resumenCosto.dialogErrorContent.html(addhtmError);
						resumenCosto.dialogError.dialog("open");
						resumenCosto.initBlockUI();
					}
				});	
			}		
		}, 		
		
		//funcion para mostrar reporte de resumen de costos F12A		
		mostrarReporteCostoF12A : function(){
			console.debug("entranado a  ver el reporte resumen costo F12A");
			if (resumenCosto.validarBusqueda()){
				$.blockUI({ message: resumenCosto.mensajeReporte});
				jQuery.ajax({
					url: resumenCosto.urlVerF12A+'&'+resumenCosto.formCommand.serialize(),
					type : 'post',
					dataType : 'json',
					data : {					
						   <portlet:namespace />codEmpresaBusq: resumenCosto.i_codEmpresaBusq.val(),
						   <portlet:namespace />grupoInfBusq: resumenCosto.i_grupoInfBusq.val()				  
					},
					success : function(data) {
						if(data.resultado=='OK'){
							resumenCosto.verReporteCostos();	
							resumenCosto.initBlockUI();
						}else if(data.resultado=='VACIO'){							
							var addhtmInfo='No existe ningún dato para los criterios seleccionados.';					
							resumenCosto.dialogInfoContent.html(addhtmInfo);
							resumenCosto.dialogInfo.dialog("open");	
							resumenCosto.initBlockUI();
						}else{							
							var addhtmError='Error al mostrar el reporte del resumen de costos de F12A';					
							resumenCosto.dialogErrorContent.html(addhtmError);
							resumenCosto.dialogError.dialog("open");	
							resumenCosto.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						resumenCosto.dialogErrorContent.html(addhtmError);
						resumenCosto.dialogError.dialog("open");
						resumenCosto.initBlockUI();
					}
				});	
			}		
		}, 
		
		
		//funcion para mostrar reporte de resumen de costos F12A exel		
		mostrarReporteCostoF12AExcel : function(){
			console.debug("entranado a  ver el reporte resumen costo F12A excel");
			if (resumenCosto.validarBusqueda()){
				$.blockUI({ message: resumenCosto.mensajeReporte});
				jQuery.ajax({
					url: resumenCosto.urlVerF12AExcel+'&'+resumenCosto.formCommand.serialize(),
					type : 'post',
					dataType : 'json',
					data : {					
						   <portlet:namespace />codEmpresaBusq: resumenCosto.i_codEmpresaBusq.val(),
						   <portlet:namespace />grupoInfBusq: resumenCosto.i_grupoInfBusq.val()				  
					},
					success : function(data) {
						if(data.resultado=='OK'){
							resumenCosto.verReporteCostos();	
							resumenCosto.initBlockUI();
						}else if(data.resultado=='VACIO'){							
							var addhtmInfo='No existe ningún dato para los criterios seleccionados.';					
							resumenCosto.dialogInfoContent.html(addhtmInfo);
							resumenCosto.dialogInfo.dialog("open");	
							resumenCosto.initBlockUI();
						}else{							
							var addhtmError='Error al mostrar el reporte del resumen de costos de F12A';					
							resumenCosto.dialogErrorContent.html(addhtmError);
							resumenCosto.dialogError.dialog("open");	
							resumenCosto.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						resumenCosto.dialogErrorContent.html(addhtmError);
						resumenCosto.dialogError.dialog("open");
						resumenCosto.initBlockUI();
					}
				});	
			}		
		}, 
		
		//funcion para mostrar reporte de resumen de costos F12B		
		mostrarReporteCostoF12B : function(){
			console.debug("entranado a  ver el reporte resumen costo F12B");
			if (resumenCosto.validarBusqueda()){
				$.blockUI({ message: resumenCosto.mensajeReporte});
				jQuery.ajax({
					url: resumenCosto.urlVerF12B+'&'+resumenCosto.formCommand.serialize(),
					type : 'post',
					dataType : 'json',
					data : {					
						   <portlet:namespace />codEmpresaBusq: resumenCosto.i_codEmpresaBusq.val(),
						   <portlet:namespace />grupoInfBusq: resumenCosto.i_grupoInfBusq.val()				  
					},
					success : function(data) {
						if(data.resultado=='OK'){
							resumenCosto.verReporteCostos();	
							resumenCosto.initBlockUI();
						}else if(data.resultado=='VACIO'){							
							var addhtmInfo='No existe ningún dato para los criterios seleccionados.';					
							resumenCosto.dialogInfoContent.html(addhtmInfo);
							resumenCosto.dialogInfo.dialog("open");	
							resumenCosto.initBlockUI();
						}else{							
							var addhtmError='Error al mostrar el reporte del resumen de costos de F12B';					
							resumenCosto.dialogErrorContent.html(addhtmError);
							resumenCosto.dialogError.dialog("open");	
							resumenCosto.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						resumenCosto.dialogErrorContent.html(addhtmError);
						resumenCosto.dialogError.dialog("open");
						resumenCosto.initBlockUI();
					}
				});	
			}		
		}, 
		
		//funcion para mostrar reporte de resumen de costos F12B excel		
		mostrarReporteCostoF12BExcel : function(){
			console.debug("entranado a  ver el reporte resumen costo F12B excel");
			if (resumenCosto.validarBusqueda()){
				$.blockUI({ message: resumenCosto.mensajeReporte});
				jQuery.ajax({
					url: resumenCosto.urlVerF12BExcel+'&'+resumenCosto.formCommand.serialize(),
					type : 'post',
					dataType : 'json',
					data : {					
						   <portlet:namespace />codEmpresaBusq: resumenCosto.i_codEmpresaBusq.val(),
						   <portlet:namespace />grupoInfBusq: resumenCosto.i_grupoInfBusq.val()				  
					},
					success : function(data) {
						if(data.resultado=='OK'){
							resumenCosto.verReporteCostos();	
							resumenCosto.initBlockUI();
						}else if(data.resultado=='VACIO'){							
							var addhtmInfo='No existe ningún dato para los criterios seleccionados.';					
							resumenCosto.dialogInfoContent.html(addhtmInfo);
							resumenCosto.dialogInfo.dialog("open");	
							resumenCosto.initBlockUI();
						}else{							
							var addhtmError='Error al mostrar el reporte del resumen de costos de F12B';					
							resumenCosto.dialogErrorContent.html(addhtmError);
							resumenCosto.dialogError.dialog("open");	
							resumenCosto.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						resumenCosto.dialogErrorContent.html(addhtmError);
						resumenCosto.dialogError.dialog("open");
						resumenCosto.initBlockUI();
					}
				});	
			}		
		}, 
		
		//funcion para validar los datos de la busqueda para el procesamiento del reporte
		validarBusqueda : function() {			
			if(resumenCosto.i_grupoInfBusq.val().length == ''){				
				var addhtmVali='Debe seleccionar un grupo de información.';					
				resumenCosto.dialogValidacionContent.html(addhtmVali);
				resumenCosto.dialogValidacion.dialog("open");
				resumenCosto.i_grupoInfBusq.focus();
			  	return false; 
			}else if(resumenCosto.i_codEmpresaBusq.val().length == ''){				
				var addhtmVali='Debe seleccionar una distribuidora eléctrica.';					
				resumenCosto.dialogValidacionContent.html(addhtmVali);
				resumenCosto.dialogValidacion.dialog("open");
				resumenCosto.i_codEmpresaBusq.focus();
			  	return false; 
			}else{
				return true;
			}		
		},
		
		//funcion para ver reposrte en una nueva pestaña
		verReporteCostos : function(){
			 window.open('<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ViewReport")%>','_newtab'); 
		}, 		
		
		//DIALOGOS
		initDialogs : function(){		
			//dialogo para eliminar
			resumenCosto.dialogConfirm.dialog({
				modal: true,
				height: 200,
				width: 500,			
				autoOpen: false,
				buttons: {
					"Si": function() {
						resumenCosto.eliminarFormato(id_Correlativo);
						$( this ).dialog("close");
					},
					"No": function() {				
						$( this ).dialog("close");
					}
				}
			});		
			
			resumenCosto.dialogMessage.dialog({
				modal: true,
				autoOpen: false,
				width: 500,		
				buttons: {
					Ok: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			resumenCosto.dialogValidacion.dialog({
				modal: true,
				autoOpen: false,
				width: 500,		
				buttons: {
					Aceptar: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			resumenCosto.dialogError.dialog({
				modal: true,
				autoOpen: false,
				width: 500,		
				buttons: {
					Aceptar: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			resumenCosto.dialogInfo.dialog({
				modal: true,
				autoOpen: false,
				width: 500,		
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