<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects />

<script type="text/javascript">

var variacionCostos= {
		
	    //decalaracion de los elementos del html	
		formCommand : null,	
		
		//i_cadenaVariacion:null,
		
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
			this.mensajeNotificar='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Procesando Notificaci�n </h3>';			
			this.mensajeProcesando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Procesando Datos </h3>';
			this.mensajeEliminando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Excluyendo </h3>';
			
			//this.mensajeEliminando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Eliminando </h3>';
			this.mensajeGuardando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Guardando Datos </h3>';
			this.mensajeActualizando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Actualizando Datos </h3>';
			this.mensajeEliminandoObs='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Eliminando </h3>';
			
			//this.i_cadenaVariacion=$('#cadenaValorVariacion');
			
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
			variacionCostos.botonGenerar.click(function(){
				variacionCostos.generarGrafico();
				//location.href=urlPlot+'&grupoInfoBusq='+variacionCostos.i_grupoInfoBusq.val()+'&formatoBusq='+variacionCostos.i_formatoBusq.val()+'&zonaBusq='+variacionCostos.i_zonaBusq.val()+'&conceptoBusq='+variacionCostos.i_conceptoBusq.val();
			});
		    
		    //eventos por defecto			    
			//variacionCostos.botonBuscar.trigger('click');
		    variacionCostos.initBlockUI();
		},
		
		/*fin cambios para ingresar observaciones manuales**/	
		
		//funcion para generar grafico
		generarGrafico : function () {	
			console.debug("entranado a generar grafico");
			variacionCostos.blockUI();
			jQuery.ajax({			
					url: variacionCostos.urlBusqueda+'&'+variacionCostos.formCommand.serialize(),
					type: 'post',
					dataType: 'json',				
					success: function(data) {			
						if (data.resultado == "OK"){
							//alert(data.cadena);
							//variacionCostos.i_cadenaVariacion.val(data.cadena);
							variacionCostos.plotearImagen(data.cadena, data.promedio);
						}
						variacionCostos.initBlockUI();
					},error : function(){
						var addhtmError='Error de conexi�n.';					
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
						var addhtmError='Error de conexi�n.';					
						variacionCostos.dialogErrorContent.html(addhtmError);
						variacionCostos.dialogError.dialog("open");
						variacionCostos.initBlockUI();
					}
			});
		},		
		
		plotearImagen : function(valor,promedio){
			//n="[[['ADIL', 7], ['ELNO', 9], ['EDLN', 15],['LDS', 12], ['ELS', 3],['ABC', 6], ['XXX', 18]]]";
			var plot1 = $.jqplot('chkDispersionHid', eval(valor), {
				 //seriesColors: ["#4177C9","#C94E41" ],
				     //title:'Grafico de prueba',
				      //series:[{color:'#5FAB78'}],
				      series:[ 
				              {
				                showLine:false, 
				                //markerOptions: { size: 8, style:'diamond' },
				                pointLabels:{
					                  show: true,
					                  //labels:['ADIL', 'ELNO', 'EDLN', 'LDS', 'ELS','ABC','XXX']
					            }
				              }
				       ],
				      axesDefaults: {
				          tickRenderer: $.jqplot.CanvasAxisTickRenderer ,
				          tickOptions: {
				            //angle: -30,
				            fontSize: '8pt'
				          }
				      },
				      axes: {
				    	  xaxis: {
				    		  renderer: $.jqplot.CategoryAxisRenderer,
				    		  label:'Distribuidora El�ctrica',
				    		  fontSize: '6pt'
				    	  },
				    	  yaxis: {
				    		  label:'Costo Unitario (S/.)',
				    		  fontSize: '6pt'
				    	  }
				      },/*,
				      legend: {
				    	    show: true,
				            location: 'e',
				            placement: 'outside'
				      }*/
				      //grid: grid,
				      canvasOverlay: {
				            show: true,
				            objects: [
				                {dashedHorizontalLine: {
				                    name: 'promedio',
				                    y: promedio,
				                    //y: '1.5',
				                    lineCap: 'butt',
				                    lineWidth: 1,
				                    xOffset: 0,
				                    color: 'rgb(255, 0, 0)',
				                    shadow: false
				                }}
				            ]
				        }
				    }
			);
			plot1.replot( { resetAxes: true } );
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