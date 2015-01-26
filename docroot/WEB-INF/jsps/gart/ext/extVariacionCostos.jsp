<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects />

<script type="text/javascript" src="/fise-projects-portlet/js/third-party/jqplot/JQuery.jqplot.min.js"></script>
<script type="text/javascript" src="/fise-projects-portlet/js/third-party/jqplot/jqplot.barRenderer.min.js"></script>
<script type="text/javascript" src="/fise-projects-portlet/js/third-party/jqplot/jqplot.categoryAxisRenderer.min.js"></script>
<script type="text/javascript" src="/fise-projects-portlet/js/third-party/jqplot/jqplot.pointLabels.min.js"></script>
<script type="text/javascript" src="/fise-projects-portlet/js/third-party/jqplot/jqplot.dateAxisRenderer.min.js"></script>
<script type="text/javascript" src="/fise-projects-portlet/js/third-party/jqplot/jqplot.canvasTextRenderer.min.js"></script>
<script type="text/javascript" src="/fise-projects-portlet/js/third-party/jqplot/jqplot.canvasAxisTickRenderer.min.js"></script>
<link rel="stylesheet" type="text/css" href="/fise-projects-portlet/css/third-party/jquery.jqplot.min.css" />

<script type="text/javascript">

var variacionCostos= {
		
	    //decalaracion de los elementos del html	
		formCommand : null,	
		
		i_cadenaVariacion:null,
		
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
		mensajeNotificar:null,
		mensajeProcesando:null,
		mensajeEliminando:null,
		
		mensajeGuardando:null,
		mensajeActualizando:null,
		mensajeEliminandoObs:null,
		
		//divs cambios observacion  manual
		divBuscar:null,	
		//urls
		urlBusqueda: null,			
		urlCargaConceptos: null,
	   //botones		
		botonGenerar:null,
		
		//varibales de busqueda
		i_grupoInfoBusq:null,
		i_formatoBusq:null,
		i_zonaBusq:null,
		i_conceptoBusq:null,
		
		
		
		init : function() {
			
			this.formCommand=$('#variacionCostosBean');	
			
			//dialogos
			this.dialogMessage=$("#<portlet:namespace/>dialog-message-notificacion");
			this.dialogMessageContent=$("#<portlet:namespace/>dialog-message-content-notificacion");
			this.dialogValidacion=$("#<portlet:namespace/>dialog-alert");	
			this.dialogValidacionContent=$("#<portlet:namespace/>dialog-alert-content");
			this.dialogError=$("#<portlet:namespace/>dialog-error");
			this.dialogErrorContent=$("#<portlet:namespace/>dialog-error-content");	
			this.dialogInfo=$("#<portlet:namespace/>dialog-info");
			this.dialogInfoContent=$("#<portlet:namespace/>dialog-info-content");
			
			//divs cambios para observacion manual
			this.divBuscar=$("#<portlet:namespace/>div_buscar"); 
			
			//mensajes						
			this.mensajeNotificar='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Procesando Notificación </h3>';			
			this.mensajeProcesando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Procesando Datos </h3>';
			this.mensajeEliminando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Excluyendo </h3>';
			
			//this.mensajeEliminando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Eliminando </h3>';
			this.mensajeGuardando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Guardando Datos </h3>';
			this.mensajeActualizando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Actualizando Datos </h3>';
			this.mensajeEliminandoObs='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Eliminando </h3>';
			
			this.i_cadenaVariacion=$('#cadenaValorVariacion');
			
			//urls
			this.urlBusqueda='<portlet:resourceURL id="generarGrafico" />';					
			this.urlCargaConceptos='<portlet:resourceURL id="cargarConceptos" />';
			
			//botones
			this.botonGenerar=$("#<portlet:namespace/>btnGenerar");	
			
			//variables de busqueda
			this.i_grupoInfoBusq=$('#grupoInfoBusq');
			this.i_formatoBusq=$('#formatoBusq');	
			this.i_zonaBusq=$('#zonaBusq');	
			this.i_conceptoBusq=$('#conceptoBusq');
			
			variacionCostos.initDialogs();
		    
			variacionCostos.i_formatoBusq.change(function(){variacionCostos.<portlet:namespace/>loadConceptos();});
			variacionCostos.i_zonaBusq.change(function(){variacionCostos.<portlet:namespace/>loadConceptos();});
			variacionCostos.botonGenerar.click(function(){variacionCostos.generarGrafico();});
		    
		    //eventos por defecto			    
			//variacionCostos.botonBuscar.trigger('click');
		    variacionCostos.initBlockUI();
		},
		
		
		
		/*fin cambios para ingresar observaciones manuales**/	
		
		
		
		//funcion para buscar
		generarGrafico : function () {	
			console.debug("entranado a generar grafico");
			variacionCostos.blockUI();
			jQuery.ajax({			
					url: variacionCostos.urlBusqueda+'&'+variacionCostos.formCommand.serialize(),
					type: 'post',
					dataType: 'json',				
					success: function(data) {			
						/*variacionCostos.tablaResultados.clearGridData(true);
						variacionCostos.tablaResultados.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
						variacionCostos.tablaResultados[0].refreshIndex();*/
						if (data.resultado == "OK"){
							/*formato14A.procesoEstado.val("UPDATE");
							formato14A.etapaEdit.val(etapa);
							formato14A.divFormato.show();
							formato14A.divHome.hide();
							formato14A.divInformacion.show();
							formato14A.FillEditformato(data.formato);
							formato14A.deshabilitarCamposView();
							formato14A.initBlockUI();*/
							//alert(data.cadena);
							variacionCostos.i_cadenaVariacion.val(data.cadena);
							variacionCostos.plotearImagen(data.cadena);
							alert(variacionCostos.i_cadenaVariacion.val());
							
							//var cadena = variacionCostos.i_cadenaVariacion.val();
							//alert(cadena);
							
						}
						
						variacionCostos.initBlockUI();
					},error : function(){
						var addhtmError='Error de conexión.';					
						variacionCostos.dialogErrorContent.html(addhtmError);
						variacionCostos.dialogError.dialog("open");
						variacionCostos.initBlockUI();
					}
				});			
		},			
		//function para el evento onchange en empresa para cargar el periodo
		<portlet:namespace/>loadConceptos : function(){	
			console.debug("entranado a cargar conceptos");
			jQuery.ajax({
					url: variacionCostos.urlCargaConceptos+'&'+variacionCostos.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					success: function(data) {		
						dwr.util.removeAllOptions("conceptoBusq");
						dwr.util.addOptions("conceptoBusq", data,"codigoItem","descripcionItem");														
					},error : function(){
						var addhtmError='Error de conexión.';					
						variacionCostos.dialogErrorContent.html(addhtmError);
						variacionCostos.dialogError.dialog("open");
						variacionCostos.initBlockUI();
					}
			});
		},		
		
		plotearImagen : function(valor){
			//var ff = ${variacionCostosBean.cadenaValorVariacion};
			//alert(ff.val());
			var plot1 = $.jqplot('chkDispersionHid',[[['EDLN', 2.7],['LDS',0.88]]] , {
			//var plot1 = $.jqplot('chkDispersionHid',ff.val() , {
				 seriesColors: ["#4177C9","#C94E41" ],
				     title:'Grafico de prueba',
				      series:[{color:'#5FAB78'}],
				      axes: {
				    	  xaxis: {
				    		  label:'Potencia a Instalar (MW)',
				    	  },
				    	  yaxis: {
				    		  label:'US$/MWh',
				    		 
				    	  }
				      },
				      legend: {
				    	    show: true,
				            location: 'e',
				            placement: 'outside'
				      }//,series:${leyendaHid},  
				    });
		},
		
		//DIALOGOS
		initDialogs : function(){		
			variacionCostos.dialogMessage.dialog({
				modal: true,
				autoOpen: false,
				width: 450,
				buttons: {
					Ok: function() {
						$( this ).dialog("close");
					}
				}
			});
			variacionCostos.dialogValidacion.dialog({
				modal: true,
				autoOpen: false,
				width: 450,
				buttons: {
					Aceptar: function() {
						$( this ).dialog("close");
					}
				}
			});
			variacionCostos.dialogError.dialog({
				modal: true,
				autoOpen: false,
				width: 450,
				buttons: {
					Aceptar: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			variacionCostos.dialogInfo.dialog({
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