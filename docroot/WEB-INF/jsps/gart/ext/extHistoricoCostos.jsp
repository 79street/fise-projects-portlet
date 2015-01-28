<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects />

<script type="text/javascript">

var historicoCostos= {
		
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
		//urlCargaConceptos: null,
	   //botones		
		botonGenerar:null,
		
		//varibales de busqueda
		i_codEmpresaBusq:null,
		i_formatoBusq:null,
		//i_zonaBusq:null,
		//i_conceptoBusq:null,
		
		
		
		init : function() {
			
			this.formCommand=$('#historicoCostosBean');	
			
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
			//this.urlCargaConceptos='<portlet:resourceURL id="cargarConceptos" />';
			
			//botones
			this.botonGenerar=$("#<portlet:namespace/>btnGenerar");	
			
			//variables de busqueda
			this.i_codEmpresaBusq=$('#codEmpresaBusq');
			this.i_formatoBusq=$('#formatoBusq');	
			
			historicoCostos.initDialogs();
		    
			//historicoCostos.i_formatoBusq.change(function(){historicoCostos.<portlet:namespace/>loadConceptos();});
			//historicoCostos.i_zonaBusq.change(function(){historicoCostos.<portlet:namespace/>loadConceptos();});
			historicoCostos.botonGenerar.click(function(){
				historicoCostos.generarGrafico();
				//location.href=urlPlot+'&grupoInfoBusq='+historicoCostos.i_grupoInfoBusq.val()+'&formatoBusq='+historicoCostos.i_formatoBusq.val()+'&zonaBusq='+historicoCostos.i_zonaBusq.val()+'&conceptoBusq='+historicoCostos.i_conceptoBusq.val();
			});
		    
		    //eventos por defecto			    
			//historicoCostos.botonBuscar.trigger('click');
		    historicoCostos.initBlockUI();
		},
		
		/*fin cambios para ingresar observaciones manuales**/	
		
		//funcion para generar grafico
		generarGrafico : function () {	
			console.debug("entranado a generar grafico");
			historicoCostos.blockUI();
			jQuery.ajax({			
					url: historicoCostos.urlBusqueda+'&'+historicoCostos.formCommand.serialize(),
					type: 'post',
					dataType: 'json',				
					success: function(data) {			
						if (data.resultado == "OK"){
							//alert(data.cadena);
							//historicoCostos.i_cadenaVariacion.val(data.cadena);
							historicoCostos.plotearImagen(data.cadena, data.titulo);
						}
						historicoCostos.initBlockUI();
					},error : function(){
						var addhtmError='Error de conexi�n.';					
						historicoCostos.dialogErrorContent.html(addhtmError);
						historicoCostos.dialogError.dialog("open");
						historicoCostos.initBlockUI();
					}
				});			
		},			
		//function para el evento onchange en empresa para cargar el periodo
		/*<portlet:namespace/>loadConceptos : function(){	
			console.debug("entranado a cargar conceptos");
			jQuery.ajax({
					url: historicoCostos.urlCargaConceptos+'&'+historicoCostos.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					success: function(data) {		
						dwr.util.removeAllOptions("conceptoBusq");
						dwr.util.addOptions("conceptoBusq", data,"codigoItem","descripcionItem");														
					},error : function(){
						var addhtmError='Error de conexi�n.';					
						historicoCostos.dialogErrorContent.html(addhtmError);
						historicoCostos.dialogError.dialog("open");
						historicoCostos.initBlockUI();
					}
			});
		},	*/	
		
		plotearImagen : function(valor,titulo){
			//n="[[['ADIL', 7], ['ELNO', 9], ['EDLN', 15],['LDS', 12], ['ELS', 3],['ABC', 6], ['XXX', 18]]]";
			var plot1 = $.jqplot('chkDispersionHid', eval(valor), {
				 //seriesColors: ["#4177C9","#C94E41" ],
				     title: titulo,
				      //series:[{color:'#5FAB78'}],
				      series:[ 
				              {
				                //showLine:false, 
				                showMarker:false,
				                //markerOptions: { size: 8, style:'diamond' },
				                pointLabels:{
					                  show: false,
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
				    		  //label:'Distribuidora El�ctrica',
				    		  fontSize: '6pt'
				    	  },
				    	  yaxis: {
				    		  //label:'Costo Unitario (S/.)',
				    		  fontSize: '6pt'
				    	  }
				      },/*,
				      legend: {
				    	    show: true,
				            location: 'e',
				            placement: 'outside'
				      }*/
				      
				    }
			);
			plot1.replot( { resetAxes: true } );
		},
		
		//DIALOGOS
		initDialogs : function(){		
			historicoCostos.dialogMessage.dialog({
				modal: true,
				autoOpen: false,
				width: 450,
				buttons: {
					Ok: function() {
						$( this ).dialog("close");
					}
				}
			});
			historicoCostos.dialogValidacion.dialog({
				modal: true,
				autoOpen: false,
				width: 450,
				buttons: {
					Aceptar: function() {
						$( this ).dialog("close");
					}
				}
			});
			historicoCostos.dialogError.dialog({
				modal: true,
				autoOpen: false,
				width: 450,
				buttons: {
					Aceptar: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			historicoCostos.dialogInfo.dialog({
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