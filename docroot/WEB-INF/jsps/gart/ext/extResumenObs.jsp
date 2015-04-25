<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@page import="com.liferay.portal.kernel.util.PrefsPropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects />

<script type="text/javascript">

var resumenObs= {
		
	    //decalaracion de los elementos del html	
		formCommand : null,	
		
			
		//dialogos	
		dialogMessage:null,
		dialogMessageContent:null,		
		
		dialogValidacion:null,
		dialogValidacionContent:null,
		dialogError:null,
		dialogErrorContent:null,
		dialogInfo:null,
		dialogInfoContent:null,
		
		
		//divs
		divFMensual:null,		
		divFBienal:null,	
		
		divEtapaMensual:null,
		divEtapaBienal:null,
		
		
		
	    //mensajes				
		mensajeReporte:null,
			
		//urls		
	    urlCargaGrupoInf:null,    
	    urlVerReporte12Pdf:null,
	    urlVerReporte12Excel:null,
	    urlVerReporte14Pdf:null,
	    urlVerReporte14Excel:null,
	    
		//botones
		botonGenerarReporteM:null,
		botonGenerarReporteB:null,		
		
		//varibales de busqueda
		i_codEmpresaBusq:null,
		i_grupoInfBusq:null,		
		i_tipoBienal:null,
		i_tipoMensual:null,		
		i_cboBienal:null,	
		i_cboMensual:null,
		i_etapaMensual:null,
		i_etapaBienal:null,
				
				
		init : function() {
			
			this.formCommand=$('#resumenObsBean');		
			
			//dialogos
			this.dialogMessage=$("#<portlet:namespace/>dialog-message-resumen_obs");
			this.dialogMessageContent=$("#<portlet:namespace/>dialog-message-content-resumen_obs");		
			
			this.dialogValidacion=$("#<portlet:namespace/>dialog-alert");	
			this.dialogValidacionContent=$("#<portlet:namespace/>dialog-alert-content");
			this.dialogError=$("#<portlet:namespace/>dialog-error");
			this.dialogErrorContent=$("#<portlet:namespace/>dialog-error-content");	
            this.dialogInfo=$("#<portlet:namespace/>dialog-info");
			this.dialogInfoContent=$("#<portlet:namespace/>dialog-info-content");	
			
			//divs
			this.divFMensual=$("#<portlet:namespace/>div_formato_mensual");		
			this.divFBienal=$("#<portlet:namespace/>div_formato_bienal");					
			this.divEtapaMensual=$("#<portlet:namespace/>div_etapa_mensual");			
			this.divEtapaBienal=$("#<portlet:namespace/>div_etapa_bienal");
			
			
			//mensajes			
			this.mensajeReporte='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Obteniendo el Reporte </h3>';
			
					
			//urls		
			this.urlCargaGrupoInf='<portlet:resourceURL id="cargarGrupoInformacion" />';		
			this.urlVerReporte12Pdf='<portlet:resourceURL id="verResumenObsMensualesPdf" />';
			this.urlVerReporte12Excel='<portlet:resourceURL id="verResumenObsMensualesExcel" />';			
			this.urlVerReporte14Pdf='<portlet:resourceURL id="verResumenObsBienalesPdf" />'; 
			this.urlVerReporte14Excel='<portlet:resourceURL id="verResumenObsBienalesExcel" />'; 
			
		
					
			//botones			
			this.botonGenerarReporteM=$("#<portlet:namespace/>btnGenerarReporteM");
			this.botonGenerarReporteB=$("#<portlet:namespace/>btnGenerarReporteB");
			
			//variables de busqueda
			this.i_codEmpresaBusq=$('#codEmpresaBusq');
			this.i_grupoInfBusq=$('#grupoInfBusq'); 				
			
			this.i_tipoBienal=$('#rbtBienal');
			this.i_tipoMensual=$('#rbtMensual');			
			
			this.i_cboMensual=$('#optionMensual');
			this.i_cboBienal=$('#optionBienal');
			
			this.i_etapaMensual=$('#etapaMBusq');
			this.i_etapaBienal=$('#etapaBBusq');
			
			
			resumenObs.i_tipoMensual.trigger('change');
			
						
			//llamado a la funciones de cada boton
			
			//boton generar los formatos bienales
			resumenObs.botonGenerarReporteM.click(function() {
				console.debug("entranado a exportar los mensuales ");			
				var valorPdf = document.getElementById('rbtPdf').checked;
				var valorExcel = document.getElementById('rbtExcel').checked;
				console.debug("entranado a exportar los mensuales PDF: "+valorPdf);	
				console.debug("entranado a exportar los mensuales Excel: "+valorExcel);	
				
				if(valorPdf){					
					resumenObs.mostrarReporteObsMensualesPDF();	
				}else if(valorExcel){
					resumenObs.mostrarReporteObsMensualesExcel();
				}else{					
				}		
					
			});	
			
			//boton generar los formatos mensuales
			resumenObs.botonGenerarReporteB.click(function() {
				console.debug("entranado a exportar los bienales ");			
				var valorPdf = document.getElementById('rbtPdf').checked;
				var valorExcel = document.getElementById('rbtExcel').checked;
				console.debug("entranado a exportar los bienales PDF: "+valorPdf);	
				console.debug("entranado a exportar los bienales Excel: "+valorExcel);
				
				if(valorPdf){					
					resumenObs.mostrarReporteObsBienalesPDF();
				}else if(valorExcel){
					resumenObs.mostrarReporteObsBienalesExcel();
				}else{					
				}	
					
			});	
			
			
			resumenObs.initDialogs();	
			
			//evento change para mensuales
			resumenObs.i_tipoMensual.change(function(){
				resumenObs.<portlet:namespace/>loadGrupoInformacion();
				resumenObs.divFMensual.show();
				resumenObs.divFBienal.hide();
				resumenObs.divEtapaMensual.show();
				resumenObs.divEtapaBienal.hide();	
			});	
			
			//evento change para bienales
			resumenObs.i_tipoBienal.change(function(){
				resumenObs.<portlet:namespace/>loadGrupoInformacion();
				resumenObs.divFMensual.hide();
				resumenObs.divFBienal.show();
				resumenObs.divEtapaMensual.hide();
				resumenObs.divEtapaBienal.show();								
			});	
			
		},			
		
		//function para el evento onchange en empresa para cargar el periodo
		<portlet:namespace/>loadGrupoInformacion : function(){	
			console.debug("entranado a cargar grupoInfo");
			jQuery.ajax({
					url: resumenObs.urlCargaGrupoInf+'&'+resumenObs.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					success: function(data) {		
						dwr.util.removeAllOptions("grupoInfBusq");
						dwr.util.addOptions("grupoInfBusq", data,"codigoItem","descripcionItem");														
					},error : function(){
						var addhtmError='Error de conexión.';					
						resumenObs.dialogErrorContent.html(addhtmError);
						resumenObs.dialogError.dialog("open");
						resumenObs.initBlockUI();
					}
			});
		},				
	    
		//funcion para mostrar reporte de resumen de observaciones en PDF para mensuales		
		mostrarReporteObsMensualesPDF : function(){
			var desGrupo = document.getElementById('grupoInfBusq')[document.getElementById('grupoInfBusq').selectedIndex].innerHTML;
			console.debug("entranado a  ver el reporte resumen observaciones mensuales");
			if (resumenObs.validarBusqueda()){
				$.blockUI({ message: resumenObs.mensajeReporte});
				jQuery.ajax({
					url: resumenObs.urlVerReporte12Pdf+'&'+resumenObs.formCommand.serialize(),
					type : 'post',
					dataType : 'json',
					data : {					
						   <portlet:namespace />codEmpresaBusq: resumenObs.i_codEmpresaBusq.val(),
						   <portlet:namespace />grupoInfBusq: resumenObs.i_grupoInfBusq.val(),
						   <portlet:namespace />desGrupoInf: desGrupo,
						   <portlet:namespace />etapaMBusq: resumenObs.i_etapaMensual.val(),
						   <portlet:namespace />optionMensual: resumenObs.i_cboMensual.val()
					},
					success : function(data) {
						if(data.resultado=='OK'){
							resumenObs.verReporteObservaciones();	
							resumenObs.initBlockUI();
						}else if(data.resultado=='VACIO'){							
							var addhtmInfo='No existe información para los criterios seleccionados.';					
							resumenObs.dialogInfoContent.html(addhtmInfo);
							resumenObs.dialogInfo.dialog("open");	
							resumenObs.initBlockUI();
						}else{							
							var addhtmError='Error al mostrar el reporte del resumen de Observaciones Mensuales';					
							resumenObs.dialogErrorContent.html(addhtmError);
							resumenObs.dialogError.dialog("open");	
							resumenObs.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						resumenObs.dialogErrorContent.html(addhtmError);
						resumenObs.dialogError.dialog("open");
						resumenObs.initBlockUI();
					}
				});	
			}		
		},
		
		
		//funcion para mostrar reporte de resumen de observaciones en Excel para mensuales	
		mostrarReporteObsMensualesExcel : function(){
			var desGrupo = document.getElementById('grupoInfBusq')[document.getElementById('grupoInfBusq').selectedIndex].innerHTML;
			console.debug("entranado a  ver el reporte resumen de observaciones mensuales Excel");
			if (resumenObs.validarBusqueda()){
				$.blockUI({ message: resumenObs.mensajeReporte});
				jQuery.ajax({
					url: resumenObs.urlVerReporte12Excel+'&'+resumenObs.formCommand.serialize(),
					type : 'post',
					dataType : 'json',
					data : {					
						   <portlet:namespace />codEmpresaBusq: resumenObs.i_codEmpresaBusq.val(),
						   <portlet:namespace />grupoInfBusq: resumenObs.i_grupoInfBusq.val(),
						   <portlet:namespace />desGrupoInf: desGrupo,
						   <portlet:namespace />etapaMBusq: resumenObs.i_etapaMensual.val(),
						   <portlet:namespace />optionMensual: resumenObs.i_cboMensual.val()
					},
					success : function(data) {
						if(data.resultado=='OK'){
							resumenObs.verReporteObservaciones();	
							resumenObs.initBlockUI();
						}else if(data.resultado=='VACIO'){							
							var addhtmInfo='No existe información para los criterios seleccionados.';					
							resumenObs.dialogInfoContent.html(addhtmInfo);
							resumenObs.dialogInfo.dialog("open");	
							resumenObs.initBlockUI();
						}else{							
							var addhtmError='Error al mostrar el reporte del resumen de Observaciones Mensuales';					
							resumenObs.dialogErrorContent.html(addhtmError);
							resumenObs.dialogError.dialog("open");	
							resumenObs.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						resumenObs.dialogErrorContent.html(addhtmError);
						resumenObs.dialogError.dialog("open");
						resumenObs.initBlockUI();
					}
				});	
			}		
		},
		
		
		//funcion para mostrar reporte de resumen de observaciones en PDF para mensuales		
		mostrarReporteObsBienalesPDF : function(){
			var desGrupo = document.getElementById('grupoInfBusq')[document.getElementById('grupoInfBusq').selectedIndex].innerHTML;
			console.debug("entranado a  ver el reporte resumen observaciones bienales");
			if (resumenObs.validarBusqueda()){
				$.blockUI({ message: resumenObs.mensajeReporte});
				jQuery.ajax({
					url: resumenObs.urlVerReporte14Pdf+'&'+resumenObs.formCommand.serialize(),
					type : 'post',
					dataType : 'json',
					data : {					
						<portlet:namespace />codEmpresaBusq: resumenObs.i_codEmpresaBusq.val(),
						   <portlet:namespace />grupoInfBusq: resumenObs.i_grupoInfBusq.val(),
						   <portlet:namespace />desGrupoInf: desGrupo,
						   <portlet:namespace />etapaBBusq: resumenObs.i_etapaBienal.val(),
						   <portlet:namespace />optionBienal: resumenObs.i_cboBienal.val()
					},
					success : function(data) {
						if(data.resultado=='OK'){
							resumenObs.verReporteObservaciones();	
							resumenObs.initBlockUI();
						}else if(data.resultado=='VACIO'){							
							var addhtmInfo='No existe información para los criterios seleccionados.';					
							resumenObs.dialogInfoContent.html(addhtmInfo);
							resumenObs.dialogInfo.dialog("open");	
							resumenObs.initBlockUI();
						}else{							
							var addhtmError='Error al mostrar el reporte del resumen de Observaciones Bienales';					
							resumenObs.dialogErrorContent.html(addhtmError);
							resumenObs.dialogError.dialog("open");	
							resumenObs.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						resumenObs.dialogErrorContent.html(addhtmError);
						resumenObs.dialogError.dialog("open");
						resumenObs.initBlockUI();
					}
				});	
			}		
		},
		
		
		//funcion para mostrar reporte de resumen de observaciones en Excel para mensuales	
		mostrarReporteObsBienalesExcel : function(){
			var desGrupo = document.getElementById('grupoInfBusq')[document.getElementById('grupoInfBusq').selectedIndex].innerHTML;
			console.debug("entranado a  ver el reporte resumen de observaciones bienales Excel");
			if (resumenObs.validarBusqueda()){
				$.blockUI({ message: resumenObs.mensajeReporte});
				jQuery.ajax({
					url: resumenObs.urlVerReporte14Excel+'&'+resumenObs.formCommand.serialize(),
					type : 'post',
					dataType : 'json',
					data : {					
						   <portlet:namespace />codEmpresaBusq: resumenObs.i_codEmpresaBusq.val(),
						   <portlet:namespace />grupoInfBusq: resumenObs.i_grupoInfBusq.val(),
						   <portlet:namespace />desGrupoInf: desGrupo,
						   <portlet:namespace />etapaBBusq: resumenObs.i_etapaBienal.val(),
						   <portlet:namespace />optionBienal: resumenObs.i_cboBienal.val()
					},
					success : function(data) {
						if(data.resultado=='OK'){
							resumenObs.verReporteObservaciones();	
							resumenObs.initBlockUI();
						}else if(data.resultado=='VACIO'){							
							var addhtmInfo='No existe información para los criterios seleccionados.';					
							resumenObs.dialogInfoContent.html(addhtmInfo);
							resumenObs.dialogInfo.dialog("open");	
							resumenObs.initBlockUI();
						}else{							
							var addhtmError='Error al mostrar el reporte del resumen de Observaciones Bienales';					
							resumenObs.dialogErrorContent.html(addhtmError);
							resumenObs.dialogError.dialog("open");	
							resumenObs.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						resumenObs.dialogErrorContent.html(addhtmError);
						resumenObs.dialogError.dialog("open");
						resumenObs.initBlockUI();
					}
				});	
			}		
		},
		
		
		//funcion para validar los datos de la busqueda para el procesamiento del reporte
		validarBusqueda : function() {			
			if(resumenObs.i_grupoInfBusq.val().length == ''){				
				var addhtmVali='Debe seleccionar un grupo de información.';					
				resumenObs.dialogValidacionContent.html(addhtmVali);
				resumenObs.dialogValidacion.dialog("open");
				resumenObs.i_grupoInfBusq.focus();
			  	return false; 
			}else if(resumenObs.i_codEmpresaBusq.val().length == ''){				
				var addhtmVali='Debe seleccionar una distribuidora eléctrica.';					
				resumenObs.dialogValidacionContent.html(addhtmVali);
				resumenObs.dialogValidacion.dialog("open");
				resumenObs.i_codEmpresaBusq.focus();
			  	return false; 
			}else{
				return true;
			}		
		},
		
		//funcion para ver reposrte en una nueva pestaña
		verReporteObservaciones : function(){
			 window.open('<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ViewReport")%>','_newtab'); 
		}, 		
		
		//DIALOGOS
		initDialogs : function(){	
			
			resumenObs.dialogMessage.dialog({
				modal: true,
				autoOpen: false,
				width: 450,		
				buttons: {
					Ok: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			resumenObs.dialogValidacion.dialog({
				modal: true,
				autoOpen: false,
				width: 450,		
				buttons: {
					Aceptar: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			resumenObs.dialogError.dialog({
				modal: true,
				autoOpen: false,
				width: 450,		
				buttons: {
					Aceptar: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			resumenObs.dialogInfo.dialog({
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