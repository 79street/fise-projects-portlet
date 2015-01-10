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
		
		//divs
		divF14A:null,	
		divF12A:null,	
		//divF12B:null,
		
		//mensajes			
		mensajePrepara:null,		
		mensajeReporte:null,
			
		//urls		
	    urlCargaGrupoInf:null,    
	    urlVerF14A:null,
	    urlVerF12A:null,
	    urlVerF12B:null,		
		
		//botones		
		botonF14A:null,		
		botonF12A:null,
		botonF12B:null,
		
		//varibales de busqueda
		i_codEmpresaBusq:null,
		i_grupoInfBusq:null,		
		i_tipoBienal:null,
		i_tipoMensual:null,
				
				
		init : function() {
			
			this.formCommand=$('#resumenCostoBean');				
			
			//dialogos
			this.dialogMessage=$("#<portlet:namespace/>dialog-message-resumen_costos");
			this.dialogMessageContent=$("#<portlet:namespace/>dialog-message-content-resumen_costos");
			
			this.dialogConfirm=$("#<portlet:namespace/>dialog-confirm-eliminar");//para elimar
			this.dialogConfirmContent=$("#<portlet:namespace/>dialog-confirm-content-eliminar");//para eliminar
			
			//divs
			this.divF14A=$("#<portlet:namespace/>div_F14A");
			this.divF12A=$("#<portlet:namespace/>div_F12A");
			//this.divF12B=$("#<portlet:namespace/>div_F12B");	
			
			//mensajes						
			
			this.mensajePrepara='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Generando Liq. </h3>';
			this.mensajeReporte='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Obteniendo el Reporte </h3>';
			
					
			//urls		
			this.urlCargaGrupoInf='<portlet:resourceURL id="cargarGrupoInformacion" />';		
			this.urlVerF14A='<portlet:resourceURL id="verResumenCostoF14A" />'; 	
			this.urlVerF12A='<portlet:resourceURL id="verResumenCostoF12A" />'; 	
			this.urlVerF12B='<portlet:resourceURL id="verResumenCostoF12B" />'; 	
					
			//botones			
			this.botonF14A=$("#<portlet:namespace/>btnF14A");		
			this.botonF12A=$("#<portlet:namespace/>btnF12A");
			this.botonF12B=$("#<portlet:namespace/>btnF12B");		
			
			//variables de busqueda
			this.i_codEmpresaBusq=$('#codEmpresaBusq');
			this.i_grupoInfBusq=$('#grupoInfBusq');				
			this.i_tipoBienal=$('#rbtBienal');
			this.i_tipoMensual=$('#rbtMensual');
			
						
			//llamado a la funciones de cada boton
			
			resumenCosto.botonF14A.click(function() {			
				resumenCosto.mostrarReporteCostoF14A();
			});
			
			
			resumenCosto.botonF12A.click(function() {			
				resumenCosto.mostrarReporteCostoF12A();				
			});
			
			resumenCosto.botonF12B.click(function() {	
				resumenCosto.mostrarReporteCostoF12B();				
			});		
			
			resumenCosto.initDialogs();
		    
		    
			resumenCosto.i_tipoBienal.change(function(){
				resumenCosto.<portlet:namespace/>loadGrupoInformacion();
				resumenCosto.divF14A.show();
				resumenCosto.divF12A.hide();	
				//resumenCosto.divF12B.hide();				
			});
			
			resumenCosto.i_tipoMensual.change(function(){
				resumenCosto.<portlet:namespace/>loadGrupoInformacion();
				resumenCosto.divF14A.hide();
				resumenCosto.divF12A.show();	
				//resumenCosto.divF12B.show();
			});
		    		   
			resumenCosto.i_tipoMensual.trigger('change');
			
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
						alert("Error de conexión.");
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
							alert("No existe ningún dato para los criterios seleccionados.");
							resumenCosto.initBlockUI();
						}else{
							alert("Error al mostrar el reporte del resumen de costos de F14A");
							resumenCosto.initBlockUI();
						}
					},error : function(){
						alert("Error de conexión.");
						resumenCosto.initBlockUI();
					}
				});	
			}		
		}, 
		
		//funcion para mostrar reporte de resumen de costos F14A		
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
							alert("No existe ningún dato para este reporte.");
							resumenCosto.initBlockUI();
						}else{
							alert("Error al mostrar el reporte del resumen de costos de F12A");
							resumenCosto.initBlockUI();
						}
					},error : function(){
						alert("Error de conexión.");
						resumenCosto.initBlockUI();
					}
				});	
			}		
		}, 
		
		//funcion para mostrar reporte de resumen de costos F14A		
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
							alert("No existe ningún dato para este reporte.");
							resumenCosto.initBlockUI();
						}else{
							alert("Error al mostrar el reporte del resumen de costos de F12B");
							resumenCosto.initBlockUI();
						}
					},error : function(){
						alert("Error de conexión.");
						resumenCosto.initBlockUI();
					}
				});	
			}		
		}, 
		
		//funcion para validar los datos de la busqueda para el procesamiento del reporte
		validarBusqueda : function() {			
			if(resumenCosto.i_grupoInfBusq.val().length == ''){
				alert('Debe seleccionar un grupo de información.'); 
				resumenCosto.i_grupoInfBusq.focus();
			  	return false; 
			}else if(resumenCosto.i_codEmpresaBusq.val().length == ''){
				alert('Debe seleccionar una distribuidora eléctrica.'); 
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
				width: 400,			
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
				buttons: {
					Ok: function() {
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