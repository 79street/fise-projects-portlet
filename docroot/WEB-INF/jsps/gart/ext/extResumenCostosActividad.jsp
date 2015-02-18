<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects />

<script type="text/javascript">

var resumenCostoActiv= {
		
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
		
		//mensajes			
		mensajeReporte:null,
			
		//urls		
	    urlCargaGrupoInf:null,    
	    urlVerF14AB:null,
	    urlVerF14ABExcel:null,
	   
		
		//botones
		botonGenerarBienal:null,	
		
		//varibales de busqueda
		i_codEmpresaBusq:null,
		i_grupoInfBusq:null,		
			
				
		
		
		init : function() {
			
			this.formCommand=$('#resumenCostoActividadBean');				
			
			//dialogos
			this.dialogMessage=$("#<portlet:namespace/>dialog-message-resumen_costos_actividad");
			this.dialogMessageContent=$("#<portlet:namespace/>dialog-message-content-resumen_costos_actividad");
					
			this.dialogValidacion=$("#<portlet:namespace/>dialog-alert");	
			this.dialogValidacionContent=$("#<portlet:namespace/>dialog-alert-content");
			this.dialogError=$("#<portlet:namespace/>dialog-error");
			this.dialogErrorContent=$("#<portlet:namespace/>dialog-error-content");	
            this.dialogInfo=$("#<portlet:namespace/>dialog-info");
			this.dialogInfoContent=$("#<portlet:namespace/>dialog-info-content");	
					
			//mensajes				
			this.mensajeReporte='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Obteniendo el Reporte </h3>';
			
					
			//urls		
			this.urlCargaGrupoInf='<portlet:resourceURL id="cargarGrupoInformacion" />';		
			this.urlVerF14AB='<portlet:resourceURL id="verResumenCostoActividadF14AB" />';
			this.urlVerF14ABExcel='<portlet:resourceURL id="verResumenCostoActividadF14ABExcel" />';			
				
					
			//botones			
			this.botonGenerarBienal=$("#<portlet:namespace/>btnGenerarF14AB");		
			
			//variables de busqueda
			this.i_codEmpresaBusq=$('#codEmpresaBusq');
			this.i_grupoInfBusq=$('#grupoInfBusq');	
			
			
						
			//llamado a la funciones de cada boton
			
			//boton generar los formatos bienales
			resumenCostoActiv.botonGenerarBienal.click(function() {				
				var valorPdf = document.getElementById('rbtPdf').checked;
				var valorExcel = document.getElementById('rbtExcel').checked;
				console.debug("entranado a exportar los bienales PDF: "+valorPdf);	
				console.debug("entranado a exportar los bienales Excel: "+valorExcel);	
				
				if(valorPdf){					
					resumenCostoActiv.mostrarReporteCostoF14AB();	
				}else if(valorExcel){
					resumenCostoActiv.mostrarReporteCostoF14ABExcel();
				}else{					
				}				
			});	
			
			
			resumenCostoActiv.initDialogs();		    
		    
			//evento change para bienales
			/* resumenCostoActiv.i_tipoBienal.change(function(){
				resumenCostoActiv.<portlet:namespace/>loadGrupoInformacion();										
			});	 */
			
			//evento change para mensuales
			/* resumenCostoActiv.i_tipoMensual.change(function(){
				resumenCostoActiv.<portlet:namespace/>loadGrupoInformacion();								
			}); */
			
		    		   
			//resumenCostoActiv.i_tipoMensual.trigger('change');
			
		},			
		
		//function para el evento onchange en empresa para cargar el periodo
		/*<portlet:namespace/>loadGrupoInformacion : function(){	
			console.debug("entranado a cargar grupoInfo");
			jQuery.ajax({
					url: resumenCostoActiv.urlCargaGrupoInf+'&'+resumenCostoActiv.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					success: function(data) {		
						dwr.util.removeAllOptions("grupoInfBusq");
						dwr.util.addOptions("grupoInfBusq", data,"codigoItem","descripcionItem");														
					},error : function(){
						var addhtmError='Error de conexión.';					
						resumenCostoActiv.dialogErrorContent.html(addhtmError);
						resumenCostoActiv.dialogError.dialog("open");
						resumenCostoActiv.initBlockUI();
					}
			});
		},	*/	
		
		//funcion para mostrar reporte de resumen de costos F14A		
		mostrarReporteCostoF14AB : function(){
			console.debug("entranado a  ver el reporte resumen costo F14AB");
			if (resumenCostoActiv.validarBusqueda()){
				$.blockUI({ message: resumenCostoActiv.mensajeReporte});
				jQuery.ajax({
					url: resumenCostoActiv.urlVerF14AB+'&'+resumenCostoActiv.formCommand.serialize(),
					type : 'post',
					dataType : 'json',
					data : {					
						   <portlet:namespace />codEmpresaBusq: resumenCostoActiv.i_codEmpresaBusq.val(),
						   <portlet:namespace />grupoInfBusq: resumenCostoActiv.i_grupoInfBusq.val()				  
					},
					success : function(data) {
						if(data.resultado=='OK'){
							resumenCostoActiv.verReporteCostos();	
							resumenCostoActiv.initBlockUI();
						}else if(data.resultado=='VACIO'){							
							var addhtmInfo='No existe información para los criterios seleccionados.';					
							resumenCostoActiv.dialogInfoContent.html(addhtmInfo);
							resumenCostoActiv.dialogInfo.dialog("open");	
							resumenCostoActiv.initBlockUI();
						}else{							
							var addhtmError='Error al mostrar el reporte del resumen de costos por Actividad F14A y F14B';					
							resumenCostoActiv.dialogErrorContent.html(addhtmError);
							resumenCostoActiv.dialogError.dialog("open");	
							resumenCostoActiv.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						resumenCostoActiv.dialogErrorContent.html(addhtmError);
						resumenCostoActiv.dialogError.dialog("open");
						resumenCostoActiv.initBlockUI();
					}
				});	
			}		
		},
		
		
		//funcion para mostrar reporte de resumen de costos F14A		
		mostrarReporteCostoF14ABExcel : function(){
			console.debug("entranado a  ver el reporte resumen costo F14AB Excel");
			if (resumenCostoActiv.validarBusqueda()){
				$.blockUI({ message: resumenCostoActiv.mensajeReporte});
				jQuery.ajax({
					url: resumenCostoActiv.urlVerF14ABExcel+'&'+resumenCostoActiv.formCommand.serialize(),
					type : 'post',
					dataType : 'json',
					data : {					
						   <portlet:namespace />codEmpresaBusq: resumenCostoActiv.i_codEmpresaBusq.val(),
						   <portlet:namespace />grupoInfBusq: resumenCostoActiv.i_grupoInfBusq.val()				  
					},
					success : function(data) {
						if(data.resultado=='OK'){
							resumenCostoActiv.verReporteCostos();	
							resumenCostoActiv.initBlockUI();
						}else if(data.resultado=='VACIO'){							
							var addhtmInfo='No existe información para los criterios seleccionados.';					
							resumenCostoActiv.dialogInfoContent.html(addhtmInfo);
							resumenCostoActiv.dialogInfo.dialog("open");	
							resumenCostoActiv.initBlockUI();
						}else{							
							var addhtmError='Error al mostrar el reporte del resumen de costos or Actividad de F14A y F14B';					
							resumenCostoActiv.dialogErrorContent.html(addhtmError);
							resumenCostoActiv.dialogError.dialog("open");	
							resumenCostoActiv.initBlockUI();
						}
					},error : function(){
						var addhtmError='Error de conexión.';					
						resumenCostoActiv.dialogErrorContent.html(addhtmError);
						resumenCostoActiv.dialogError.dialog("open");
						resumenCostoActiv.initBlockUI();
					}
				});	
			}		
		},	
		
		//funcion para validar los datos de la busqueda para el procesamiento del reporte
		validarBusqueda : function() {			
			if(resumenCostoActiv.i_grupoInfBusq.val().length == ''){				
				var addhtmVali='Debe seleccionar un grupo de información.';					
				resumenCostoActiv.dialogValidacionContent.html(addhtmVali);
				resumenCostoActiv.dialogValidacion.dialog("open");
				resumenCostoActiv.i_grupoInfBusq.focus();
			  	return false; 
			}else if(resumenCostoActiv.i_codEmpresaBusq.val().length == ''){				
				var addhtmVali='Debe seleccionar una distribuidora eléctrica.';					
				resumenCostoActiv.dialogValidacionContent.html(addhtmVali);
				resumenCostoActiv.dialogValidacion.dialog("open");
				resumenCostoActiv.i_codEmpresaBusq.focus();
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
			
			resumenCostoActiv.dialogMessage.dialog({
				modal: true,
				autoOpen: false,
				width: 450,		
				buttons: {
					Ok: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			resumenCostoActiv.dialogValidacion.dialog({
				modal: true,
				autoOpen: false,
				width: 450,		
				buttons: {
					Aceptar: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			resumenCostoActiv.dialogError.dialog({
				modal: true,
				autoOpen: false,
				width: 450,		
				buttons: {
					Aceptar: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			resumenCostoActiv.dialogInfo.dialog({
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