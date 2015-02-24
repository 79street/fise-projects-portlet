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
		i_etapaBusq:null,
		
		
		
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
			
			//this.i_cadenaVariacion=$('#cadenaValorVariacion');
			
			//urls
			this.urlBusqueda='<portlet:resourceURL id="generarGrafico" />';					
			this.urlCargaConceptos='<portlet:resourceURL id="cargarConceptos" />';
			
			//botones
			this.botonGenerar=$("#<portlet:namespace/>btnGenerar");	
			
			$("#exportarPdf").click(function() {
				variacionCostos.exportarPdf();
			});
			
			$("#exportarExcel").click(function() {
				variacionCostos.exportarExcel();
			});
			
			//variables de busqueda
			this.i_grupoInfoBusq=$('#grupoInfoBusq');
			this.i_formatoBusq=$('#formatoBusq');	
			this.i_zonaBusq=$('#zonaBusq');	
			this.i_conceptoBusq=$('#conceptoBusq');
			this.i_etapaBusq=$('#etapaBusq');
			
			variacionCostos.initDialogs();
		    
			variacionCostos.i_formatoBusq.change(function(){variacionCostos.<portlet:namespace/>loadConceptos();});
			variacionCostos.i_zonaBusq.change(function(){variacionCostos.<portlet:namespace/>loadConceptos();});
			variacionCostos.botonGenerar.click(function(){
				variacionCostos.generarGrafico();
				//location.href=urlPlot+'&grupoInfoBusq='+variacionCostos.i_grupoInfoBusq.val()+'&formatoBusq='+variacionCostos.i_formatoBusq.val()+'&zonaBusq='+variacionCostos.i_zonaBusq.val()+'&conceptoBusq='+variacionCostos.i_conceptoBusq.val();
			});
		    
		    //eventos por defecto			    
			//variacionCostos.botonBuscar.trigger('click');
		    variacionCostos.i_zonaBusq.trigger('change');
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
							variacionCostos.plotearImagen(data.cadena, data.promedio, data.titulo);
							//seteamos los titulos
							$('#<portlet:namespace/>titulo1-imagen').val(data.titulo1);
							$('#<portlet:namespace/>titulo2-imagen').val(data.titulo2);
							$('#<portlet:namespace/>titulo3-imagen').val(data.titulo3);
							$('#<portlet:namespace/>titulo4-imagen').val(data.titulo4);
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
		
		plotearImagen : function(valor,promedio,titulo){
			//n="[[['ADIL', 7], ['ELNO', 9], ['EDLN', 15],['LDS', 12], ['ELS', 3],['ABC', 6], ['XXX', 18]]]";
			//n="[[['ADIL', 7], ['ELNO', 9], ['EDLN', 15],['LDS ', 12], ['ELS ', 3],['ABC ', 6], ['XXX ', 18],['XXA ', 2],['XXB ', 15],['XXC ', 12],['XXD ', 13],['XXE ', 8],['XXF ', 5],['XXG ', 4],['XXH ', 8],['XXI ', 18],['XXJ ', 1],['XXL  ', 15],['XXM ', 13],['XXN ', 18],['XXO', 12],['XAP ', 2],['XBP ', 8],['XCP ', 9],['XDP ', 5],['XEP ', 12],['XFP ', 10],['XGP ', 11],['XHP ', 12],['XIP ', 7],['XJP ', 8],['XKP ', 10],['XLP ', 2],['XMP ', 4],['XNP ', 5],['XOP ', 2]]]";
			//valor="";
			//alert(valor);
			if( valor=="[[],[]]" ){
				var addhtmError='No hay información disponible para generar el gráfico';					
				variacionCostos.dialogValidacionContent.html(addhtmError);
				variacionCostos.dialogValidacion.dialog("open");
				//
				$('#<portlet:namespace/>titulo-imagen').html('');
			}else{
				
				$('#<portlet:namespace/>titulo-imagen').html(titulo);
				
				var plot1 = $.jqplot('chkDispersionHid', eval(valor), {
					//if (plot1) {
       				// plot1.destroy();
    				//}
				//var plot1 = $.jqplot('chkDispersionHid', eval(n), {
					 //seriesColors: ["#4177C9","#C94E41" ],
					    //---- title:titulo,
					      //series:[{color:'#5FAB78'}],
					      series:[ 
					              {
					                showLine:false, 
					                //markerOptions: { size: 8, style:'diamond' },
					                //markerOptions: { size: 14},
					                pointLabels:{
						                  show: true,
						                  //fontSize: '14pt',
						                  formatString: "%#.2f"
						                  //labels:['ADIL', 'ELNO', 'EDLN', 'LDS', 'ELS','ABC','XXX']
						            },
						            fontSize: '14pt',
						            //lineWidth: 2,
						            //highlighter: { formatString: "<div style='background-color:white; border:1px #ddd solid; width:220px; height:60px'>hola . Views como Revenue :estas </div>" }
					              },
					              { 
					                showMarker:false,
					                linePattern: 'dashed',
					                color: 'red',
					                lineWidth: 1,
					                pointLabels: { 
					                	show:true, 
					                	location: 'ne' 
					              	} // do not show marker, but do show point label
					              }
					       ],
					      /*axesDefaults: {
					          tickRenderer: $.jqplot.CanvasAxisTickRenderer ,
					          tickOptions: {
					            //angle: -30,
					            fontSize: '8pt'
					          }
					      },*/
					      axes: {
					    	  xaxis: {
					    		  //renderer: $.jqplot.CategoryAxisRenderer,
					    		  //label:'Distribuidora Eléctrica',
					    		  //fontSize: '6pt'
				    			  fontSize: '8pt',
				    			  renderer: $.jqplot.CategoryAxisRenderer,
				    	          label: 'Distribuidora Eléctrica',
				    	          labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
				    	          tickRenderer: $.jqplot.CanvasAxisTickRenderer,
				    	          tickOptions: {
				    	              angle: -90,
				    	              //fontFamily: 'Courier New',
				    	              fontSize: '8pt'
				    	          }
					    	  },
					    	  yaxis: {
					    		  //label:'Costo Unitario (S/.)',
					    		  //fontSize: '6pt'
				    			  fontSize: '8pt',
				    			  //renderer: $.jqplot.CategoryAxisRenderer,
				    	          label: 'Costo Unitario (Nuevos Soles)',
				    	          labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
				    	          //tickRenderer: $.jqplot.CanvasAxisTickRenderer,
				    	          tickOptions: {
				    	              //angle: -90,
				    	              //fontFamily: 'Courier New',
				    	              fontSize: '8pt'
				    	          }
					    	  }
					      },/*,
					      legend: {
					    	    show: true,
					            location: 'e',
					            placement: 'outside'
					      }*/
					      //grid: grid,
					      /*highlighter: {
					         show:true,
					     },*/
					     /* canvasOverlay: {
					            show: true,
					            objects: [
					                {dashedHorizontalLine: {
					                    name: 'promedio',
					                    y: promedio,
					                    //y: '1.5',
					                    //lineCap: 'butt',
					                    lineWidth: 1,
					                    //xOffset: 0,
					                    color: 'rgb(255, 0, 0)',
					                    shadow: false
					                }}
					            ]
					        },*/
					       /* legend:{ 
					           // show:true,
				                //renderer: $.jqplot.EnhancedLegendRenderer,
				                //location: 's' ,
				                //placement : "outside",
				                //rendererOptions: {
				                  //  numberRows: 1
				                //}
					        	renderer: jQuery.jqplot.EnhancedLegendRenderer,
					        	location: 'ne' ,
					           	show: true,
					           	labels: ['prueba1', 'prueba2'],
						        rendererOptions: {
			                        numberRows: 2
			                    }
					        }*/
					    }
				);
				plot1.series[1].data = [[plot1.axes.xaxis.min,promedio],[plot1.axes.xaxis.max,promedio]]; //dynamically add the data for the empty series, we do this so we can get the auto-scaled yaxis min/max
				plot1.redraw(); // redraw the plot with adjusted 2nd series
				$('.jqplot-point-label.jqplot-series-1.jqplot-point-0').css({"color":"red","font-size":"9pt"});
				$('.jqplot-point-label.jqplot-series-1.jqplot-point-0').html('Promedio = '+promedio);
				//plot1.replot( { resetAxes: true } );
				$('.jqplot-point-label').css({"font-size":"9pt"});
				
				//mostramos el boton de exportar a PDF
				$('#exportarPdf').css('display','');
				$('#exportarExcel').css('display','');
				
			}
			
			
		},
		
		exportarPdf : function(){
	         try {
	             var aaa = $('#chkDispersionHid');
	             try {
	                 var img = aaa.jqplotToImageElem();
	             }catch(e){
	            	 console.debug(e);
	             }
	             try {
	                 if($('#chartImgDiv').children().length > 0){
	                     $('#chartImgDiv').children().remove();
	                 }
	             } catch (e) {
	            	 console.debug(e);
	             }
	             var doc = new jsPDF("l", "pt", [800, 500]);
	            
	             doc.setFontSize(10);
	            
	             //centramos los textos ingresados
	             var largo1 = doc.getStringUnitWidth($('#<portlet:namespace/>titulo1-imagen').val());
	             var largo2 = doc.getStringUnitWidth($('#<portlet:namespace/>titulo2-imagen').val());
	             var largo3 = doc.getStringUnitWidth($('#<portlet:namespace/>titulo3-imagen').val());
	             var largo4 = doc.getStringUnitWidth($('#<portlet:namespace/>titulo4-imagen').val());
	             
	             var tamFuente = doc.internal.getFontSize();
	             var escala = doc.internal.scaleFactor;
	             var largo = doc.internal.pageSize.width;
	             
	             var tam1 = largo1*tamFuente/escala;
	             var x1= (largo-tam1)/2;
	             var tam2 = largo2*tamFuente/escala;
	             var x2= (largo-tam2)/2;
	             var tam3 = largo3*tamFuente/escala;
	             var x3= (largo-tam3)/2;
	             var tam4 = largo4*tamFuente/escala;
	             var x4= (largo-tam4)/2;
	             
	             doc.text(x1, 40, $('#<portlet:namespace/>titulo1-imagen').val());
	             doc.text(x2, 55, $('#<portlet:namespace/>titulo2-imagen').val());
	             doc.text(x3, 70, $('#<portlet:namespace/>titulo3-imagen').val());
	             doc.text(x4, 85, $('#<portlet:namespace/>titulo4-imagen').val());
	             
				doc.addImage(img, 'PNG',20,100);
				doc.save('variacionCostos.pdf');
			 } catch (e) {
				 console.debug(e);
	         }
	},
	
	exportarExcel : function(){
		location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';  
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