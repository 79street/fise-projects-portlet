<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@page import="com.liferay.portal.kernel.util.PrefsPropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<portlet:defineObjects />

<script type="text/javascript">

var archivoSustentoVar= {
		
	    //decalaracion de los elementos del html	
		formCommand : null,	
		
		//divs
		divBuscar:null,	
		divBuscarArch:null,
		
		
		//dialogos	
		dialogMessage:null,
		dialogMessageContent:null,	
		
		
		dialogConfirm:null,//para eliminar archivos
		dialogConfirmContent:null,//para mostrar la confirmacion de eliminar archivos		
		
		dialogObservacion:null,
		dialogObservacion12:null,
		dialogObservacion13:null,			
		
		dialogValidacion:null,
		dialogValidacionContent:null,
		dialogError:null,
		dialogErrorContent:null,
		dialogInfo:null,
		dialogInfoContent:null,	
		
		dialogCargaArchivos:null,
		divOverlay:null,
		
		//mensajes		
		mensajeEliminar:null,		
		mensajeReporte:null,	
		mensajeObteniendoDatos:null,		
		mensajeGuardando:null,	
		mensajeReemplazando:null,
		mensajeDescarga:null,
		
		//urls
		urlBusqueda: null,	    
	    urlCargaGrupoInf:null,	    
	    urlEliminar:null,	   
	    urlVerObservaciones:null,
	    urlVerFormatos:null,  	
	    urlDescargarArchivo:null,  
	    urlBusquedaArchivos: null,
	    urlFlagOperacion: null,
	    
	    urlListarActividades:null,
		
	    
		//botones		
		botonBuscar:null,			
		botonRegresarFormatos:null,	
		botonNuevoArchivo:null,
		
		botonCargarArchivoSustento:null,
		
		//varibales de busqueda
		i_codEmpresaBusq:null,
		i_grupoInfBusq:null,		
		i_tipoBienal:null,
		i_tipoMensual:null,	
		i_etapaBusq:null,
		
			
		//grillas
		tablaResultados:null,
		paginadoResultados:null,	
		
		tablaObservacion:null,	
		paginadoObservacion:null,
		
		tablaObservacion12:null,	
		paginadoObservacion12:null,
		
		tablaObservacion13:null,	
		paginadoObservacion13:null,
		
		tablaResultadosArchivos:null,
		paginadoResultadosArchivos:null,
		
		//varibales para la carga de archivos 
		flag:null, //flag = E ocurrrio un error al cargar archivos
		flagCarga:null,//indica si es nueva carga o reemplazar archivo existente
		codEmpresaOriSes:null,
	    desEmpresaSes:null,
		anioPresSes:null,
		mesPresSes:null,
		codMesPresSes:null,
		anioEjecSes:null,
		mesEjecSes:null,
		anioIniVigSes:null,
		anioFinVigSes:null,
		etapaSes:null,
		formatoSes:null,
		correlativoSes:null,
		//para la busqueda inicial
		codEmpresaSes:null,
		grupoInfSes:null,
		periocidadSes:null,
		
		//pra mesajes error o exito
		mensajeError:null,
		mensajeInfo:null,
		
		//varibales para enviar al controller cuando se carga un archivo
		codEmpresaOriF:null,		
		desEmpresaF:null,
		anioPresF:null,
		mesPresF:null,
		codMesPresF:null,
		anioEjecF:null,
		mesEjecF:null,
		anioIniVigF:null,
		anioFinVigF:null,
		estapaF:null,
		formatoF:null,
		//para mantener en session de la busqueda de formatos
		codEmpresaF:null,		
		grupoInforF:null,
		periocidadF:null,
		
		flagOperacion:null,//flag para saber si esta ABIERTO,CERRADO o ENVIADO
		
		//para nuevo registro
		correlativoF:null,//correlativo del formato para insertar en el detalle del archivo sustento
		//para reemplazar archivo
		itemArchivo:null,
		correlativoArchivo:null,
		
		
		
		
		
		init : function() {
			
			this.formCommand=$('#archivoSustentoBean');	
			
			//divs
			this.divBuscar=$("#<portlet:namespace/>div_buscar");
			this.divBuscarArch=$("#<portlet:namespace/>div_buscar_archivos");		
			
			
			//dialogos
			this.dialogMessage=$("#<portlet:namespace/>dialog-message-archivos");
			this.dialogMessageContent=$("#<portlet:namespace/>dialog-message-content-archivos");
			
			this.dialogConfirm=$("#<portlet:namespace/>dialog-confirm-eliminar");//para elimar archivos
			this.dialogConfirmContent=$("#<portlet:namespace/>dialog-confirm-content-eliminar");//para eliminar archivos
			
			this.dialogObservacion=$("#<portlet:namespace/>dialog-form-observacion");	
			this.dialogObservacion12=$("#<portlet:namespace/>dialog-form-observacion12");
			this.dialogObservacion13=$("#<portlet:namespace/>dialog-form-observacion13");			
			
			this.dialogValidacion=$("#<portlet:namespace/>dialog-alert");	
			this.dialogValidacionContent=$("#<portlet:namespace/>dialog-alert-content");
			this.dialogError=$("#<portlet:namespace/>dialog-error");
			this.dialogErrorContent=$("#<portlet:namespace/>dialog-error-content");	
            this.dialogInfo=$("#<portlet:namespace/>dialog-info");
			this.dialogInfoContent=$("#<portlet:namespace/>dialog-info-content");
			
			this.dialogCargaArchivos=$("#<portlet:namespace/>dialog-form-cargaArchivos");
			this.divOverlay=$("#<portlet:namespace/>divOverlay");
			
			//mensajes						
			this.mensajeEliminar='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Eliminando Archivo de Sustento </h3>';
			this.mensajeReporte='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Obteniendo el Reporte </h3>';
			this.mensajeObteniendoDatos='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Obteniendo Archivos </h3>';
			this.mensajeGuardando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Guardando Archivo </h3>';
			this.mensajeReemplazando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Reemplazando Archivo </h3>';
			this.mensajeDescarga='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Descargando Archivo de Sustento </h3>';
						
			//urls
			this.urlBusqueda='<portlet:resourceURL id="busquedaFormatos" />';					
			this.urlEliminar='<portlet:resourceURL id="eliminarArchivosSustento" />';
			this.urlCargaGrupoInf='<portlet:resourceURL id="cargarGrupoInformacion" />';			
			this.urlVerObservaciones='<portlet:resourceURL id="verObservacionesFormatos" />';
			this.urlVerFormatos='<portlet:resourceURL id="verFormatosReporte" />'; 		
			this.urlDescargarArchivo='<portlet:resourceURL id="descargarArchivoSustento" />'; 		
			this.urlBusquedaArchivos='<portlet:resourceURL id="busquedaArchivosSustentoFormato" />'; 	
			this.urlFlagOperacion='<portlet:resourceURL id="obtenerFlagOperacion" />';
			
			this.urlListarActividades='<portlet:resourceURL id="listarActividades" />';	
			
			//botones
			this.botonBuscar=$("#<portlet:namespace/>btnBuscarFormatos");		
			this.botonRegresarFormatos=$("#<portlet:namespace/>btnRegresarBusqFormatos");
			this.botonNuevoArchivo=$("#<portlet:namespace/>btnNuevoArchivoSustento");
			
			this.botonCargarArchivoSustento=$("#<portlet:namespace/>cargarArchivoSustento");
			
			//variables de busqueda
			this.i_codEmpresaBusq=$('#codEmpresaBusq');
			this.i_grupoInfBusq=$('#grupoInfBusq');				
			this.i_tipoBienal=$('#rbtBienal');
			this.i_tipoMensual=$('#rbtMensual');
			this.i_etapaBusq=$('#etapaBusq');
					
			
			//varibales hidden para la carga de archivos
			this.flag=$('#<portlet:namespace/>flag');
			this.flagCarga=$('#<portlet:namespace/>flagCarga');
			this.codEmpresaOriSes=$('#<portlet:namespace/>codEmpresaOriSes');
			this.desEmpresaSes=$('#<portlet:namespace/>desEmpresaSes');
			this.anioPresSes=$('#<portlet:namespace/>anioPresSes');
			this.mesPresSes=$('#<portlet:namespace/>mesPresSes');
			this.codMesPresSes=$('#<portlet:namespace/>codMesPresSes');
			this.anioEjecSes=$('#<portlet:namespace/>anioEjecSes');
			this.mesEjecSes=$('#<portlet:namespace/>mesEjecSes');
			this.anioIniVigSes=$('#<portlet:namespace/>anioIniVigSes');
			this.anioFinVigSes=$('#<portlet:namespace/>anioFinVigSes');
			this.etapaSes=$('#<portlet:namespace/>etapaSes');
			this.formatoSes=$('#<portlet:namespace/>formatoSes');
			this.correlativoSes=$('#<portlet:namespace/>correlativoSes');
			
			//para mantener la sesion para la busqueda inicial
			this.codEmpresaSes=$('#<portlet:namespace/>codEmpresaSes');
			this.grupoInfSes=$('#<portlet:namespace/>grupoInfSes');
			this.periocidadSes=$('#<portlet:namespace/>periocidadSes');
		
			
			//variables para mandar al controller
			this.codEmpresaOriF=$('#<portlet:namespace/>codEmpresaOriF');
			this.desEmpresaF=$('#<portlet:namespace/>desEmpresaF');
			this.anioPresF=$('#<portlet:namespace/>anioPresF');
			this.mesPresF=$('#<portlet:namespace/>mesPresF');
			this.codMesPresF=$('#<portlet:namespace/>codMesPresF');
			this.anioEjecF=$('#<portlet:namespace/>anioEjecF');
			this.mesEjecF=$('#<portlet:namespace/>mesEjecF');
			this.anioIniVigF=$('#<portlet:namespace/>anioIniVigF');
			this.anioFinVigF=$('#<portlet:namespace/>anioFinVigF');
			this.estapaF=$('#<portlet:namespace/>estapaF');
			this.formatoF=$('#<portlet:namespace/>formatoF');
			//para mantener en session de la busqueda de formatos
			this.codEmpresaF=$('#<portlet:namespace/>codEmpresaF');
			this.grupoInforF=$('#<portlet:namespace/>grupoInforF');
			this.periocidadF=$('#<portlet:namespace/>periocidadF');
			
			//nuevo
			this.correlativoF=$('#<portlet:namespace/>correlativoF');
			//reemplazar
			this.itemArchivo=$('#<portlet:namespace/>itemArchivo');
			this.correlativoArchivo=$('#<portlet:namespace/>correlativoArchivo');
			
			//mensajes de error o exito
			this.mensajeError=$('#<portlet:namespace/>mensajeError');
			this.mensajeInfo=$('#<portlet:namespace/>mensajeInfo');	
			
			this.flagOperacion=$('#<portlet:namespace/>flagOperacion');	
			
			
			//grillas			
			this.tablaResultados=$("#<portlet:namespace/>grid_resultado_busqueda");
			this.paginadoResultados='#<portlet:namespace/>paginador_resultado_busqueda';
			this.buildGrids();	//cargar el modelo de la grilla			
			
			this.tablaObservacion=$("#<portlet:namespace/>grid_observacion");			
			this.paginadoObservacion='#<portlet:namespace/>pager_observacion';			
			this.buildGridsObservacion();
			
			this.tablaObservacion12=$("#<portlet:namespace/>grid_observacion12");			
			this.paginadoObservacion12='#<portlet:namespace/>pager_observacion12';		
			this.buildGridsObservacion12();
			
			this.tablaObservacion13=$("#<portlet:namespace/>grid_observacion13");			
			this.paginadoObservacion13='#<portlet:namespace/>pager_observacion13';		
			this.buildGridsObservacion13();
			
			
			this.tablaResultadosArchivos=$("#<portlet:namespace/>grid_resultado_busqueda_archivos");
			this.paginadoResultadosArchivos='#<portlet:namespace/>paginador_resultado_busqueda_archivos';
			this.buildGridsArchivos();	//cargar el modelo de la grilla de archivos de sustentos por formato
			
			//llamado a la funciones de cada boton
			archivoSustentoVar.botonBuscar.click(function() {			
				archivoSustentoVar.buscarFormatos('P');//P = bucar y poblando la tabla fiseArchivoCab
			});			
			
			
			archivoSustentoVar.botonRegresarFormatos.click(function() {
				archivoSustentoVar.<portlet:namespace/>regresarFormatos();
		    });	
						
			archivoSustentoVar.initDialogs();
			archivoSustentoVar.cargaInicial();
		    
		    
			archivoSustentoVar.i_tipoBienal.change(function(){
				archivoSustentoVar.<portlet:namespace/>loadGrupoInformacion();				
			});
			
			archivoSustentoVar.i_tipoMensual.change(function(){
				archivoSustentoVar.<portlet:namespace/>loadGrupoInformacion();			
			});	
			
			
			//botones para carga de archivos
		    archivoSustentoVar.botonNuevoArchivo.click(function() {	
		    	var flagOperacion = archivoSustentoVar.flagOperacion.val();
				 console.debug("Flag de operacion al momento de nuevo archivo sustento");
				if(flagOperacion=='ABIERTO'){
					archivoSustentoVar.flagCarga.val('0');// 0= nueva carga			    	
			    	archivoSustentoVar.<portlet:namespace/>mostrarFormCargaArchivoSustentoNuevo();	 
				}else if(flagOperacion=='CERRADO'){				
					var addhtmInfo='El plazo para realizar esta acción se encuentra cerrado';				
					archivoSustentoVar.dialogInfoContent.html(addhtmInfo);
					archivoSustentoVar.dialogInfo.dialog("open");	
				}else{				
					var addhtmInfo='Este Formato seleccionado ya fue enviado a OSINERGMIN-GART.';				
					archivoSustentoVar.dialogInfoContent.html(addhtmInfo);
					archivoSustentoVar.dialogInfo.dialog("open");	
				}			    	   		
		    });
			
			
		    archivoSustentoVar.botonCargarArchivoSustento.click(function() {
		    	archivoSustentoVar.<portlet:namespace/>cargarArchivosSustentoFormato();
		    });
			
		  
			archivoSustentoVar.i_tipoMensual.trigger('change');
			
		},		
		
		
		//function para la carga inicial del formulario al cargar archivos
		cargaInicial : function(){				
			//SE CARGA VARIABLES EN SESION PARA MOSTRAR LOS PANELES DE BUSQUEDA DE ARCHIVOS
			 var codEmpOriSes = archivoSustentoVar.codEmpresaOriSes.val();
			 var desEmpSes = archivoSustentoVar.desEmpresaSes.val();
			 var anioPresSes = archivoSustentoVar.anioPresSes.val();
			 var codMesPresSes = archivoSustentoVar.codMesPresSes.val();
			 var mesPresSes = archivoSustentoVar.mesPresSes.val();
			 var anioEjecSes = archivoSustentoVar.anioEjecSes.val();
			 var mesEjecSes = archivoSustentoVar.mesEjecSes.val();
			 var anioIniVigSes = archivoSustentoVar.anioIniVigSes.val();
			 var anioFinVigSes = archivoSustentoVar.anioFinVigSes.val();
			 var etapaSes = archivoSustentoVar.etapaSes.val();
			 var formatoSes = archivoSustentoVar.formatoSes.val();
			 var correlativoSes = archivoSustentoVar.correlativoSes.val();	
			 //varibales para mantener la session de la busqueda
			 var codEmpresaBusqSes = archivoSustentoVar.codEmpresaSes.val(); 
			 var grupoInfSes= archivoSustentoVar.grupoInfSes.val();
			 var periocidadSes = archivoSustentoVar.periocidadSes.val();
				 
			    console.debug("Entrando despues de cargar correlativo: "+correlativoSes);
			    console.debug("Entrando despues de cargar cod empreesa ori : "+codEmpOriSes);
				console.debug("Entrando despues de cargar des empresa:  "+desEmpSes);
				console.debug("Entrando despues de cargar anio pres :  "+anioPresSes);
				console.debug("Entrando despues de cargar mes pres :  "+mesPresSes);
				console.debug("Entrando despues de cargar cod mes pres :  "+codMesPresSes);
				console.debug("Entrando despues de cargar anio ejec : "+anioEjecSes);
				console.debug("Entrando despues de cargar mes ejec :  "+mesEjecSes);
				console.debug("Entrando despues de cargar anio ini:  "+anioIniVigSes);
				console.debug("Entrando despues de cargar anio fin : "+anioFinVigSes);				
				console.debug("Entrando despues de cargar etapa:  "+etapaSes);
				console.debug("Entrando despues de cargar formato:   "+formatoSes);		
				//////////////////////////////////////////////////////////////////////
				console.debug("Entrando despues de cargar cod empresa busq: "+codEmpresaBusqSes);
				console.debug("Entrando despues de cargar grupo inf busq : "+grupoInfSes);
			    console.debug("Entrando despues de cargar periocidad busq:  "+periocidadSes);
			
			 var flag = archivoSustentoVar.flag.val();
			 console.debug("Entrando despues de cargar flag  "+flag);
			 if(flag=='E' ){//solo ocurre cuando hay un error en la carga de formularios, sino se muestra el proceso normal				 
				 
				 if(correlativoSes!='' && desEmpSes != '' && anioPresSes != '' && mesPresSes != '' 
						 && anioEjecSes != '' && mesEjecSes != '' 
						 && anioIniVigSes != '' && anioFinVigSes != '' 
						 && etapaSes != ''  && formatoSes != ''){
					 archivoSustentoVar.mostrarArchivosFormato(codEmpOriSes,correlativoSes,desEmpSes,anioPresSes,mesPresSes,anioEjecSes,mesEjecSes,anioIniVigSes,anioFinVigSes,etapaSes,formatoSes,codMesPresSes);
				 }
				 archivoSustentoVar.flagCarga.val('0');// 0= nueva carga
				 				
				//mantener datos en sesion
				if(archivoSustentoVar.codEmpresaSes.val()!='' 
						&& archivoSustentoVar.grupoInfSes.val()!='' 
						&& archivoSustentoVar.periocidadSes.val()!=''
						&& archivoSustentoVar.etapaSes.val()!=''){
					
					archivoSustentoVar.i_codEmpresaBusq.val(archivoSustentoVar.codEmpresaSes.val());
					archivoSustentoVar.i_grupoInfBusq.val(archivoSustentoVar.grupoInfSes.val());
					if(archivoSustentoVar.periocidadSes.val()=='MENSUAL'){
						document.getElementById('rbtMensual').checked = true;
					}else{
						document.getElementById('rbtBienal').checked = true;
					}	
					$('#optionFormato').val(archivoSustentoVar.periocidadSes.val());		
					archivoSustentoVar.i_etapaBusq.val(archivoSustentoVar.etapaSes.val());
				}else{
					archivoSustentoVar.i_codEmpresaBusq.val('');
					archivoSustentoVar.i_grupoInfBusq.val('');
					document.getElementById('rbtMensual').checked = true;
					archivoSustentoVar.i_etapaBusq.val('');
					$('#optionFormato').val('');		
				}
				 
			}else{						
				 if(correlativoSes!='' && desEmpSes != '' && anioPresSes != '' && mesPresSes != '' 
					 && anioEjecSes != '' && mesEjecSes != '' 
					 && anioIniVigSes != '' && anioFinVigSes != '' 
					 && etapaSes != ''  && formatoSes != ''){
					 console.debug("Entrando despues de cargar ");
				     archivoSustentoVar.mostrarArchivosFormato(codEmpOriSes,correlativoSes,desEmpSes,anioPresSes,mesPresSes,anioEjecSes,mesEjecSes,anioIniVigSes,anioFinVigSes,etapaSes,formatoSes,codMesPresSes);
			    }
				//mantener datos en sesion
				 if(archivoSustentoVar.codEmpresaSes.val()!='' 
						&& archivoSustentoVar.grupoInfSes.val()!='' 
						&& archivoSustentoVar.periocidadSes.val()!=''
						&& archivoSustentoVar.etapaSes.val()!=''){
					
					archivoSustentoVar.i_codEmpresaBusq.val(archivoSustentoVar.codEmpresaSes.val());
					archivoSustentoVar.i_grupoInfBusq.val(archivoSustentoVar.grupoInfSes.val());
					if(archivoSustentoVar.periocidadSes.val()=='MENSUAL'){
						document.getElementById('rbtMensual').checked = true;
					}else{
						document.getElementById('rbtBienal').checked = true;
					}
					$('#optionFormato').val(archivoSustentoVar.periocidadSes.val());					
					archivoSustentoVar.i_etapaBusq.val(archivoSustentoVar.etapaSes.val());
				}else{
					archivoSustentoVar.i_codEmpresaBusq.val('');
					archivoSustentoVar.i_grupoInfBusq.val('');
					document.getElementById('rbtMensual').checked = true;
					$('#optionFormato').val('');		
					archivoSustentoVar.i_etapaBusq.val('');
				}
			 }
			
			 var mensajeInfo =  archivoSustentoVar.mensajeInfo.val();
			 console.debug("Entrando despues de cargar mensaje info : "+mensajeInfo);
			 var mensajeError = archivoSustentoVar.mensajeError.val();
			 console.debug("Entrando despues de cargar mensaje error:   "+mensajeError);
			 //SE MUESTRAN LOS MENSAJES DE ERROR PARA LA CARGA DE LOS ARCHIVOS
			 if(mensajeError!=''){				
				 archivoSustentoVar.dialogErrorContent.html(mensajeError);
				 archivoSustentoVar.dialogError.dialog("open");	
			}else{
				//Se muestra el mensaje de informacion exitosa
				 if(mensajeInfo!=''){
					 archivoSustentoVar.dialogMessageContent.html(mensajeInfo);
					 archivoSustentoVar.dialogMessage.dialog("open");			
				 }
			 }
			 //limpiar variables	
			 archivoSustentoVar.codEmpresaOriSes.val('');
			 archivoSustentoVar.desEmpresaSes.val('');
			 archivoSustentoVar.anioPresSes.val('');
			 archivoSustentoVar.codMesPresSes.val('');
			 archivoSustentoVar.mesPresSes.val('');
			 archivoSustentoVar.anioEjecSes.val('');
			 archivoSustentoVar.mesEjecSes.val('');
			 archivoSustentoVar.anioIniVigSes.val('');
			 archivoSustentoVar.anioFinVigSes.val('');
			 archivoSustentoVar.etapaSes.val('');
			 archivoSustentoVar.formatoSes.val('');
			 archivoSustentoVar.correlativoSes.val('');
			 
			 archivoSustentoVar.codEmpresaSes.val('');
			 archivoSustentoVar.grupoInfSes.val('');
			 archivoSustentoVar.periocidadSes.val('');
		},	
		
		
		//funcion para armar el modelo de la grilla para el resultado
		buildGrids : function () {	
			archivoSustentoVar.tablaResultados.jqGrid({
			   datatype: "local",
		       colNames: ['Dist. Eléct.','Formato','Año Decl.','Mes Decl.','Año Ejec.','Mes Ejec.','Año Ini. Vig.','Año Fin Vig.','Estado','Ver','Ver Obs.','Archivos.','','','','',''],
		       colModel: [
                       { name: 'desEmpresa', index: 'desEmpresa', width: 50},				   
					   { name: 'formato', index: 'formato', width: 20,align:'center'},					             
		               { name: 'anioPres', index: 'anioPres', width: 30,align:'center' },  
		               { name: 'desMes', index: 'desMes', width: 30,align:'center'},
		               { name: 'anioEjec', index: 'anioEjec', width: 30,align:'center' },   
		               { name: 'desMesEje', index: 'desMesEje', width: 30,align:'center'},
		               { name: 'anioIniVig', index: 'anioIniVig', width: 30,align:'center' },   
		               { name: 'anioFinVig', index: 'anioFinVig', width: 30,align:'center'},  
		               { name: 'estadoFormato', index: 'estadoFormato', width: 30,align:'center'},	  	
		               { name: 'verF', index: 'verF', width: 30,align:'center' },
		               { name: 'verObs', index: 'verObs', width: 30,align:'center' },		               
		               { name: 'mostrarArch', index: 'mostrarArch', width: 30,align:'center' },	              	
		               { name: 'codEmpresa', index: 'codEmpresa', hidden: true},
		               { name: 'mesPres', index: 'mesPres', hidden: true},             
		               { name: 'mesEjec', index: 'mesEjec', hidden: true},
		               { name: 'correlativo', index: 'correlativo', hidden: true},
		               { name: 'etapa', index: 'etapa', hidden: true},
			   	    ],
			   	 multiselect: false,
					rowNum:10,
				   	rowList:[10,20,50],
					height: 225,
				   	autowidth: true,
					rownumbers: true,
					shrinkToFit:true,
					pager: archivoSustentoVar.paginadoResultados,
				    viewrecords: true,
				   	caption: "Datos para los Archivos de Sustento",
				    sortorder: "asc",	   	    	   	   
		            gridComplete: function(){
		      		var ids = archivoSustentoVar.tablaResultados.jqGrid('getDataIDs');
		      		for(var i=0;i < ids.length;i++){
		      			var cl = ids[i];
		      			var ret = archivoSustentoVar.tablaResultados.jqGrid('getRowData',cl);	        			
		      			verF = "<a href='#'><img border='0' title='Ver Formato' src='/net-theme/images/img-net/file.png' align='center' onclick=\"archivoSustentoVar.mostrarReporteFormatos('"+ret.codEmpresa+"','"+ret.anioPres+"','"+ret.mesPres+"','"+ret.anioEjec+"','"+ret.mesEjec+"','"+ret.anioIniVig+"','"+ret.anioFinVig+"','"+ret.etapa+"','"+ret.formato+"');\" /></a> ";
		      			verObs = "<a href='#'><img border='0' title='Ver Obs.' src='/net-theme/images/img-net/file.png'  align='center' onclick=\"archivoSustentoVar.verObservaciones('"+ret.codEmpresa+"','"+ret.anioPres+"','"+ret.mesPres+"','"+ret.anioEjec+"','"+ret.mesEjec+"','"+ret.anioIniVig+"','"+ret.anioFinVig+"','"+ret.etapa+"','"+ret.formato+"');\" /></a> ";
		      			mostrarArch = "<a href='#'><img border='0' title='Archivos de Sustento' src='/net-theme/images/img-net/address-book-add.png'  align='center' onclick=\"archivoSustentoVar.mostrarArchivosFormato('"+ret.codEmpresa+"','"+ret.correlativo+"','"+ret.desEmpresa+"','"+ret.anioPres+"','"+ret.desMes+"','"+ret.anioEjec+"','"+ret.desMesEje+"','"+ret.anioIniVig+"','"+ret.anioFinVig+"','"+ret.etapa+"','"+ret.formato+"','"+ret.mesPres+"');\" /></a> ";
		      			
		      			archivoSustentoVar.tablaResultados.jqGrid('setRowData',ids[i],{verF:verF});
		      		    archivoSustentoVar.tablaResultados.jqGrid('setRowData',ids[i],{verObs:verObs});		      		   
		      		    archivoSustentoVar.tablaResultados.jqGrid('setRowData',ids[i],{mostrarArch:mostrarArch});	      		  	
		      		}
		      } 
		  	});
			archivoSustentoVar.tablaResultados.jqGrid('navGrid',archivoSustentoVar.paginadoResultados,{add:false,edit:false,del:false,search: false,refresh: false});				
		},
		
		//Modelo de la grilla para mostrar Observaciones para los formatos 12A,12B,14A,14B 14C
		buildGridsObservacion : function () {	
		   archivoSustentoVar.tablaObservacion.jqGrid({
		   datatype: "local",
	       colNames: ['Grupo Zona','Código','Descripción'],
	       colModel: [
						{ name: 'descZonaBenef', index: 'descZonaBenef', width: 150 ,align: 'left'},
						{ name: 'codigo', index: 'codigo', width: 50 ,align: 'center'},
		                { name: 'descripcion', index: 'descripcion', width: 420 ,align: 'left'}               
			   	    ],
		   	 multiselect: false,
				rowNum:10,
			   	rowList:[10,20,50],
				height: 'auto',
			   	autowidth: true,
				rownumbers: true,				
				pager: archivoSustentoVar.paginadoObservacion,
			    viewrecords: true,			   
			    sortorder: "asc"
		  	});
		    archivoSustentoVar.tablaObservacion.jqGrid('navGrid',archivoSustentoVar.paginadoObservacion,{add:false,edit:false,del:false,search: false,refresh: false});	
		   
		},
		
		//Modelo de la grilla para mostrar Observaciones para los formatos 12C y 12D
		buildGridsObservacion12 : function () {	
		   archivoSustentoVar.tablaObservacion12.jqGrid({
		   datatype: "local",
		   colNames: ['Etapa ejecución','Nro. item etapa','Código','Descripción'],
	       colModel: [
						{ name: 'descEtapaEjecucion', index: 'descEtapaEjecucion', width: 100 ,align: 'left'},
						{ name: 'nroItemEtapa', index: 'nroItemEtapa', width: 75 ,align: 'left'},
						{ name: 'codigo', index: 'codigo', width: 50 ,align: 'center'},
		                { name: 'descripcion', index: 'descripcion', width: 430 ,align: 'left'}               
			   	    ],
		   	 multiselect: false,
				rowNum:10,
			   	rowList:[10,20,50],
				height: 'auto',
			   	autowidth: true,
				rownumbers: true,				
				pager: archivoSustentoVar.paginadoObservacion12,
			    viewrecords: true,			   
			    sortorder: "asc"
		  	});
		    archivoSustentoVar.tablaObservacion12.jqGrid('navGrid',archivoSustentoVar.paginadoObservacion12,{add:false,edit:false,del:false,search: false,refresh: false});	
		   
		},
		
		//Modelo de la grilla para mostrar Observaciones para los formatos 13A
		buildGridsObservacion13 : function () {	
		   archivoSustentoVar.tablaObservacion13.jqGrid({
		   datatype: "local",
		   colNames: ['Sector Típico','Grupo Zona','Código','Descripción'],
	       colModel: [
						{ name: 'descSectorTipico', index: 'descSectorTipico', width: 80 ,align: 'left'},
						{ name: 'descZonaBenef', index: 'descZonaBenef', width: 100 ,align: 'left'},
						{ name: 'codigo', index: 'codigo', width: 50 ,align: 'center'},
		                { name: 'descripcion', index: 'descripcion', width: 430 ,align: 'left'}               
			   	    ],
		   	 multiselect: false,
				rowNum:10,
			   	rowList:[10,20,50],
				height: 'auto',
			   	autowidth: true,
				rownumbers: true,				
				pager: archivoSustentoVar.paginadoObservacion13,
			    viewrecords: true,			   
			    sortorder: "asc"
		  	});
		    archivoSustentoVar.tablaObservacion13.jqGrid('navGrid',archivoSustentoVar.paginadoObservacion13,{add:false,edit:false,del:false,search: false,refresh: false});	

		},	
		
		//funcion para armar el modelo de la grilla para el resultado de los archivos de sustento por formato
		buildGridsArchivos : function () {	
			var ancho = archivoSustentoVar.divBuscar.width();
			archivoSustentoVar.tablaResultadosArchivos.jqGrid({
			   datatype: "local",
		       colNames: ['Ítem','Archivo','Actividad','Descargar','Reemplazar','Eliminar','','',''],
		       colModel: [
                       { name: 'itemArchivo', index: 'itemArchivo', width: 20,align:'center'},                       
                       { name: 'nombreArchivo', index: 'nombreArchivo', width: 80}, 
                       { name: 'descripcionActiv', index: 'descripcionActiv', width: 60},  
		               { name: 'descargar', index: 'descargar', width: 20,align:'center' },		                
		               { name: 'reemplazar', index: 'reemplazar', width: 20,align:'center' },	
		               { name: 'eliminar', index: 'eliminar', width: 20,align:'center' },	
		               { name: 'corrArchivo', index: 'corrArchivo', hidden: true},
		               { name: 'idFileEntry', index: 'idFileEntry', hidden: true},
		               { name: 'itemActividad', index: 'itemActividad', hidden: true}	
			   	    ],
			   	 multiselect: false,
					rowNum:10,
				   	rowList:[10,20,50],
				   	height: 'auto',
				   //	autowidth: true,
					rownumbers: true,
					//shrinkToFit:true,
					width:ancho ,
					pager: archivoSustentoVar.paginadoResultadosArchivos,
				    viewrecords: true,
				   	caption: "Resultado(s) de la búsqueda",
				    sortorder: "desc",	   	    	   	   
		            gridComplete: function(){
		      		var ids = archivoSustentoVar.tablaResultadosArchivos.jqGrid('getDataIDs');
		      		for(var i=0;i < ids.length;i++){
		      			var cl = ids[i];
		      			var ret = archivoSustentoVar.tablaResultadosArchivos.jqGrid('getRowData',cl);	        			
		      			descargar = "<a href='#'><img border='0' title='Descargar' src='/net-theme/images/img-net/descarga.png' align='center' onclick=\"archivoSustentoVar.descargarArchivoSustento('"+ret.itemArchivo+"','"+ret.corrArchivo+"','"+ret.nombreArchivo+"','"+ret.idFileEntry+"');\" /></a> ";
		      			reemplazar = "<a href='#'><img border='0' title='Reemplazar' src='/net-theme/images/img-net/address-book-edit.png'  align='center' onclick=\"archivoSustentoVar.reemplazarArchivoSustento('"+ret.itemArchivo+"','"+ret.corrArchivo+"','"+ret.itemActividad+"');\" /></a> ";
		      			eliminar = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/address-book-delete.png'  align='center' onclick=\"archivoSustentoVar.confirmarEliminarArchivo('"+ret.itemArchivo+"','"+ret.corrArchivo+"');\" /></a> ";
		      			archivoSustentoVar.tablaResultadosArchivos.jqGrid('setRowData',ids[i],{descargar:descargar});		      		   
		      		    archivoSustentoVar.tablaResultadosArchivos.jqGrid('setRowData',ids[i],{reemplazar:reemplazar});
		      		    archivoSustentoVar.tablaResultadosArchivos.jqGrid('setRowData',ids[i],{eliminar:eliminar});		      		   
		      		}
		      } 
		  	});
			archivoSustentoVar.tablaResultadosArchivos.jqGrid('navGrid',archivoSustentoVar.paginadoResultadosArchivos,{add:false,edit:false,del:false,search: false,refresh: false});				
		},	
		
		
		//funcion para buscar
		buscarFormatos : function (flag) {	
			console.debug("entranado a buscar formatos para la subrida de archivos de sustento");
			archivoSustentoVar.blockUI();
			jQuery.ajax({			
					url: archivoSustentoVar.urlBusqueda+'&'+archivoSustentoVar.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data : {
						   <portlet:namespace />flagBusq: flag						  
					},
					success: function(gridData) {					
						archivoSustentoVar.tablaResultados.clearGridData(true);
						archivoSustentoVar.tablaResultados.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
						archivoSustentoVar.tablaResultados[0].refreshIndex();
						archivoSustentoVar.initBlockUI();
						archivoSustentoVar.flagCarga.val('0');// 0= nueva carga
					},error : function(){
						var addhtmError='Error de conexión.';					
						archivoSustentoVar.dialogErrorContent.html(addhtmError);
						archivoSustentoVar.dialogError.dialog("open");
						archivoSustentoVar.initBlockUI();
					}
				});			
		},
		
		//function para el evento onchange en empresa para cargar el periodo
		<portlet:namespace/>loadGrupoInformacion : function(){	
			console.debug("entranado a cargar grupoInfo");
			jQuery.ajax({
					url: archivoSustentoVar.urlCargaGrupoInf+'&'+archivoSustentoVar.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					success: function(data) {		
						dwr.util.removeAllOptions("grupoInfBusq");
						dwr.util.addOptions("grupoInfBusq", data,"codigoItem","descripcionItem");														
					},error : function(){
						var addhtmError='Error de conexión.';					
						archivoSustentoVar.dialogErrorContent.html(addhtmError);
						archivoSustentoVar.dialogError.dialog("open");
						archivoSustentoVar.initBlockUI();
					}
			});
		},	
	
		//funcion para ver observaciones de los formatos
		verObservaciones : function(cod_Empresa,anio_Pres,mes_Pres,anio_Ejec,mes_Ejec,anio_IniVig,anio_FinVig,cod_etapa,cod_formato){
			jQuery.ajax({
				url: archivoSustentoVar.urlVerObservaciones+'&'+archivoSustentoVar.formCommand.serialize(),
				type : 'post',
				dataType : 'json',
				data : {
					   <portlet:namespace />codEmpresa: cod_Empresa,
					   <portlet:namespace />anioPres: anio_Pres,
					   <portlet:namespace />mesPres: mes_Pres,
					   <portlet:namespace />etapa: cod_etapa,
					   <portlet:namespace />anioEjec: anio_Ejec,
					   <portlet:namespace />mesEjec: mes_Ejec,
					   <portlet:namespace />anioIniVig: anio_IniVig,
					   <portlet:namespace />anioFinVig: anio_FinVig,
					   <portlet:namespace />formato: cod_formato
				},
				success : function(data) {
					if( data!=null ){
						console.debug("formato al lanzar el modelo de las observaciones: "+cod_formato);
						if(cod_formato=='F12C' || cod_formato=='F12D'){
							console.debug("entrando en formato 12C y 12D");
							archivoSustentoVar.dialogObservacion12.dialog("open");
							archivoSustentoVar.tablaObservacion12.clearGridData(true);
							archivoSustentoVar.tablaObservacion12.jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
							archivoSustentoVar.tablaObservacion12[0].refreshIndex();
							archivoSustentoVar.initBlockUI();	
						}else if(cod_formato=='F13A'){
							console.debug("entrando en formato13A ");
							archivoSustentoVar.dialogObservacion13.dialog("open");
							archivoSustentoVar.tablaObservacion13.clearGridData(true);
							archivoSustentoVar.tablaObservacion13.jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
							archivoSustentoVar.tablaObservacion13[0].refreshIndex();
							archivoSustentoVar.initBlockUI();	
						}else{
							archivoSustentoVar.dialogObservacion.dialog("open");
							archivoSustentoVar.tablaObservacion.clearGridData(true);
							archivoSustentoVar.tablaObservacion.jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
							archivoSustentoVar.tablaObservacion[0].refreshIndex();
							archivoSustentoVar.initBlockUI();	
						}						
					}else{						
						var addhtmError='Error al Ver las Observaciones del registro seleccionado.';					
						archivoSustentoVar.dialogErrorContent.html(addhtmError);
						archivoSustentoVar.dialogError.dialog("open");	
						archivoSustentoVar.initBlockUI();	
					}
				},error : function(){
					var addhtmError='Error de conexión.';					
					archivoSustentoVar.dialogErrorContent.html(addhtmError);
					archivoSustentoVar.dialogError.dialog("open");
					archivoSustentoVar.initBlockUI();
				}
			});
		}, 		
		
		//funcion para mostrar el reporte de cada formato	
		mostrarReporteFormatos : function(cod_Empresa,anio_Pres,mes_Pres,anio_Ejec,mes_Ejec,anio_IniVig,anio_FinVig,cod_etapa,cod_formato){
			console.debug("entranado a  ver el reporte del formato");
			$.blockUI({ message: archivoSustentoVar.mensajeReporte});
			jQuery.ajax({
				url: archivoSustentoVar.urlVerFormatos+'&'+archivoSustentoVar.formCommand.serialize(),
				type : 'post',
				dataType : 'json',
				data : {
					   <portlet:namespace />codEmpresa: cod_Empresa,
					   <portlet:namespace />anioPres: anio_Pres,
					   <portlet:namespace />mesPres: mes_Pres,
					   <portlet:namespace />etapa: cod_etapa,
					   <portlet:namespace />anioEjec: anio_Ejec,
					   <portlet:namespace />mesEjec: mes_Ejec,
					   <portlet:namespace />anioIniVig: anio_IniVig,
					   <portlet:namespace />anioFinVig: anio_FinVig,
					   <portlet:namespace />formato: cod_formato
				},
				success : function(data) {
					if(data.resultado=='OK'){
						archivoSustentoVar.verReporteFormato();	
						archivoSustentoVar.initBlockUI();
					}else{						
						var addhtmError='Error al ver el reporte del formato seleccionado.';					
						archivoSustentoVar.dialogErrorContent.html(addhtmError);
						archivoSustentoVar.dialogError.dialog("open");
						archivoSustentoVar.initBlockUI();
					}
				},error : function(){
					var addhtmError='Error de conexión.';					
					archivoSustentoVar.dialogErrorContent.html(addhtmError);
					archivoSustentoVar.dialogError.dialog("open");
					archivoSustentoVar.initBlockUI();
				}
			});
		}, 		
		
		
		
		//funcion para mostrar el reporte de cada formato	
		descargarArchivoSustento : function(item_archivo,corr_archivo,nombre_archivo,id_file){
			console.debug("entranado a  descaragar archivo de sustento");
			$.blockUI({ message: archivoSustentoVar.mensajeDescarga});
			jQuery.ajax({
				url: archivoSustentoVar.urlDescargarArchivo+'&'+archivoSustentoVar.formCommand.serialize(),
				type : 'post',
				dataType : 'json',
				data : {
					   <portlet:namespace />itemArchivo: item_archivo,
					   <portlet:namespace />corrArchivo: corr_archivo,
					   <portlet:namespace />nombreArchivo: nombre_archivo,
					   <portlet:namespace />idFileEntry: id_file	
				},
				success : function(data) {
					if(data.resultado=='OK'){
						archivoSustentoVar.verReporteArchivoSustento(data.url);	
						archivoSustentoVar.initBlockUI();
					}else{						
						var addhtmError='Error al descargar Archivo de Sustento seleccionado.';					
						archivoSustentoVar.dialogErrorContent.html(addhtmError);
						archivoSustentoVar.dialogError.dialog("open");
						archivoSustentoVar.initBlockUI();
					}
				},error : function(){
					var addhtmError='Error de conexión.';					
					archivoSustentoVar.dialogErrorContent.html(addhtmError);
					archivoSustentoVar.dialogError.dialog("open");
					archivoSustentoVar.initBlockUI();
				}
			});
		}, 		
		
				
		//funcion para ver reposrte en una nueva pestaña
		verReporteFormato : function(){
			window.open('<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ViewReport")%>','_newtab');
		}, 
		
		//funcion para ver reposrte en una nueva pestaña
		verReporteArchivoSustento : function(url){
			window.open(url,'_newtab');
		}, 
		
				
		//funcion  para mostrar detalle de los archivos de sustento
		mostrarArchivosFormato1 : function(cod_empresaOri,cod_correlativo,empresa,anio_pres,mes_pres,anio_ejec,mes_ejec,anio_inicio,anio_fin,etapa_final,formato,cod_mesPres){	
			
			
			console.debug("codEmpresaBus:  "+archivoSustentoVar.i_codEmpresaBusq.val());			
			console.debug("grupo informacion :  "+archivoSustentoVar.i_grupoInfBusq.val());	
			console.debug("periocidad :  "+ document.getElementById('rbtMensual').checked);
			console.debug("periocidad :  "+ document.getElementById('rbtBienal').checked);			
			//varibles  para mantener sesion cuando volvemos a la busqueda inicial
			var periocidad = "";
			if(document.getElementById('rbtMensual').checked==true){
				periocidad = 'MENSUAL';
			}else{
				periocidad = 'BIENAL';
			}
			archivoSustentoVar.codEmpresaF.val(archivoSustentoVar.i_codEmpresaBusq.val());
			archivoSustentoVar.grupoInforF.val(archivoSustentoVar.i_grupoInfBusq.val());
			archivoSustentoVar.periocidadF.val(periocidad);
			
			//variables para funcionalidad		
			console.debug("codEmpresaOri:  "+cod_empresaOri);
			archivoSustentoVar.codEmpresaOriF.val(cod_empresaOri);
			$('#empresaArchivo').val(empresa);			
			archivoSustentoVar.desEmpresaF.val(empresa);
			$('#anioArchivo').val(anio_pres);
			archivoSustentoVar.anioPresF.val(anio_pres);
			$('#mesArchivo').val(mes_pres);
			archivoSustentoVar.mesPresF.val(mes_pres);
			archivoSustentoVar.codMesPresF.val(cod_mesPres);
			$('#anioEjecArchivo').val(anio_ejec);
			archivoSustentoVar.anioEjecF.val(anio_ejec);
			$('#mesEjecArchivo').val(mes_ejec);
			archivoSustentoVar.mesEjecF.val(mes_ejec);
			$('#anioInicioVigArchivo').val(anio_inicio);
			archivoSustentoVar.anioIniVigF.val(anio_inicio);
			$('#anioFinVigArchivo').val(anio_fin);
			archivoSustentoVar.anioFinVigF.val(anio_fin);
			$('#etapaArchivo').val(etapa_final);
			archivoSustentoVar.estapaF.val(etapa_final);
			$('#formatoArchivo').val(formato);
			archivoSustentoVar.formatoF.val(formato);		
			archivoSustentoVar.correlativoF.val(cod_correlativo); //para nuevo registro	
			
			$('#tituloBusquedaArchivo').val('Datos principales del Formato: '+formato);		
			
			archivoSustentoVar.divBuscarArch.show();
			archivoSustentoVar.divBuscar.hide();		
			archivoSustentoVar.buscarArchivosSustentoFormato(cod_correlativo);			
		},
		
		//funcion para mostrar el detalle del formato y la lista de archvios de sustento asociados al formato y el flag operacion
		mostrarArchivosFormato : function(cod_empresaOri,cod_correlativo,empresa,anio_pres,mes_pres,anio_ejec,mes_ejec,anio_inicio,anio_fin,etapa_final,formato,cod_mesPres){
			console.debug("entranado a mostrar archivo de sustento ");			
			jQuery.ajax({
				url: archivoSustentoVar.urlFlagOperacion+'&'+archivoSustentoVar.formCommand.serialize(),
				type: 'post',
				dataType: 'json',
				data: {				
					   <portlet:namespace />codEmpresa: cod_empresaOri,
					   <portlet:namespace />formato: formato,
					   <portlet:namespace />anioPres: anio_pres,
					   <portlet:namespace />mesPres: cod_mesPres,
					   <portlet:namespace />etapa: etapa_final		  
					},
				success: function(data) {
					if (data.resultado == "OK"){
						
						console.debug("codEmpresaBus:  "+archivoSustentoVar.i_codEmpresaBusq.val());			
						console.debug("grupo informacion :  "+archivoSustentoVar.i_grupoInfBusq.val());	
						console.debug("periocidad :  "+ document.getElementById('rbtMensual').checked);
						console.debug("periocidad :  "+ document.getElementById('rbtBienal').checked);			
						//varibles  para mantener sesion cuando volvemos a la busqueda inicial
						var periocidad = "";
						if(document.getElementById('rbtMensual').checked==true){
							periocidad = 'MENSUAL';
						}else{
							periocidad = 'BIENAL';
						}
						archivoSustentoVar.codEmpresaF.val(archivoSustentoVar.i_codEmpresaBusq.val());
						archivoSustentoVar.grupoInforF.val(archivoSustentoVar.i_grupoInfBusq.val());
						archivoSustentoVar.periocidadF.val(periocidad);
						
						//variables para funcionalidad	
						console.debug("flag operacion al mostrar lista de archivos de sustento:  "+data.flagOperacion);
						archivoSustentoVar.flagOperacion.val(data.flagOperacion);
						console.debug("codEmpresaOri:  "+cod_empresaOri);
						archivoSustentoVar.codEmpresaOriF.val(cod_empresaOri);
						$('#empresaArchivo').val(empresa);			
						archivoSustentoVar.desEmpresaF.val(empresa);
						$('#anioArchivo').val(anio_pres);
						archivoSustentoVar.anioPresF.val(anio_pres);
						$('#mesArchivo').val(mes_pres);
						archivoSustentoVar.mesPresF.val(mes_pres);
						archivoSustentoVar.codMesPresF.val(cod_mesPres);
						$('#anioEjecArchivo').val(anio_ejec);
						archivoSustentoVar.anioEjecF.val(anio_ejec);
						$('#mesEjecArchivo').val(mes_ejec);
						archivoSustentoVar.mesEjecF.val(mes_ejec);
						$('#anioInicioVigArchivo').val(anio_inicio);
						archivoSustentoVar.anioIniVigF.val(anio_inicio);
						$('#anioFinVigArchivo').val(anio_fin);
						archivoSustentoVar.anioFinVigF.val(anio_fin);
						$('#etapaArchivo').val(etapa_final);
						archivoSustentoVar.estapaF.val(etapa_final);
						$('#formatoArchivo').val(formato);
						archivoSustentoVar.formatoF.val(formato);		
						archivoSustentoVar.correlativoF.val(cod_correlativo); //para nuevo registro							
						$('#tituloBusquedaArchivo').val('Datos principales del Formato: '+formato);		
						
						archivoSustentoVar.divBuscarArch.show();
						archivoSustentoVar.divBuscar.hide();		
						archivoSustentoVar.buscarArchivosSustentoFormato(cod_correlativo);
					}
					else{						
						var addhtmError='Error al mostrar los Archivos de Sustento asociados al formato: '+formato;					
						archivoSustentoVar.dialogErrorContent.html(addhtmError);
						archivoSustentoVar.dialogError.dialog("open");
						archivoSustentoVar.initBlockUI();
					}
				},error : function(){
					var addhtmError='Error de conexión.';					
					archivoSustentoVar.dialogErrorContent.html(addhtmError);
					archivoSustentoVar.dialogError.dialog("open");
					archivoSustentoVar.initBlockUI();
				}
			});
		},	
		
		
		//funcion para buscar los archivos de sustento de cada formato
		buscarArchivosSustentoFormato : function (cod_correlativo) {	
			console.debug("entranado a buscar archivos por formatos");
			archivoSustentoVar.blockUI();
			jQuery.ajax({			
					url: archivoSustentoVar.urlBusquedaArchivos+'&'+archivoSustentoVar.formCommand.serialize(),
					type: 'post',
					dataType: 'json',
					data : {
						   <portlet:namespace />correlativo: cod_correlativo						  
					},
					success: function(gridData) {						
						  archivoSustentoVar.tablaResultadosArchivos.clearGridData(true);
						  archivoSustentoVar.tablaResultadosArchivos.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
						  archivoSustentoVar.tablaResultadosArchivos[0].refreshIndex();					
						  archivoSustentoVar.initBlockUI();																	
					},error : function(){
						var addhtmError='Error de conexión.';					
						archivoSustentoVar.dialogErrorContent.html(addhtmError);
						archivoSustentoVar.dialogError.dialog("open");
						archivoSustentoVar.initBlockUI();
					}
				});			
		},	
		
		
		//funcion para reemplazar un archivo de sustento
		 reemplazarArchivoSustento : function (item_formato,cod_correlativo,item_Actividad) {
			var flagOperacion = archivoSustentoVar.flagOperacion.val();
			 console.debug("Flag de operacion al momento de reemplazar archivo");
			if(flagOperacion=='ABIERTO'){
				archivoSustentoVar.flagCarga.val('1');	
				archivoSustentoVar.itemArchivo.val(item_formato);
				archivoSustentoVar.correlativoArchivo.val(cod_correlativo);
				archivoSustentoVar.<portlet:namespace/>mostrarFormCargaArchivoSustentoEditar(item_Actividad);
			}else if(flagOperacion=='CERRADO'){				
				var addhtmInfo='El plazo para realizar esta acción se encuentra cerrado';				
				archivoSustentoVar.dialogInfoContent.html(addhtmInfo);
				archivoSustentoVar.dialogInfo.dialog("open");	
			}else{				
				var addhtmInfo='Este Formato seleccionado ya fue enviado a OSINERGMIN-GART.';				
				archivoSustentoVar.dialogInfoContent.html(addhtmInfo);
				archivoSustentoVar.dialogInfo.dialog("open");	
			}		
		},
		
		
		<portlet:namespace/>mostrarFormCargaArchivoSustentoNuevo : function(){
			console.debug("formato para enviar a listar actividades:  "+archivoSustentoVar.formatoF.val());
			jQuery.ajax({
				url: archivoSustentoVar.urlListarActividades+'&'+archivoSustentoVar.formCommand.serialize(),
				type: 'post',
				dataType: 'json',
				data : {
					   <portlet:namespace />formatoActiv: archivoSustentoVar.formatoF.val()						  
				},
				success: function(data) {		
					dwr.util.removeAllOptions("itemActividad");
					dwr.util.addOptions("itemActividad", ["--Seleccione--"]);
					dwr.util.addOptions("itemActividad", data,"codigoActividad","descActividad");	
					//para mostrar el papel de carga de archivos de sustento
					if( archivoSustentoVar.flagCarga.val()=='0' ){//proviene de archivos nuevos
						archivoSustentoVar.flagCarga.val('2');//para cargar archivos excel
					}else if(archivoSustentoVar.flagCarga.val()=='1' ){//proviene de archivos modificados
						archivoSustentoVar.flagCarga.val('3');//para cargar archivos excel
					}
					archivoSustentoVar.divOverlay.show();
					archivoSustentoVar.dialogCargaArchivos.show();			   
					archivoSustentoVar.dialogCargaArchivos.draggable();
					archivoSustentoVar.dialogCargaArchivos.css({ 
				        'left': ($(window).width() / 2 - archivoSustentoVar.dialogCargaArchivos.width() / 2) + 'px', 
				        'top': ($(window).height()  - archivoSustentoVar.dialogCargaArchivos.height() ) + 'px'
				    });					
				},error : function(){
					var addhtmError='Error de conexión.';					
					archivoSustentoVar.dialogErrorContent.html(addhtmError);
					archivoSustentoVar.dialogError.dialog("open");
					archivoSustentoVar.initBlockUI();
				}
		     });	
		},	
		
		<portlet:namespace/>mostrarFormCargaArchivoSustentoEditar : function(item_Actividad){
			console.debug("formato para enviar a listar actividades:  "+archivoSustentoVar.formatoF.val());
			jQuery.ajax({
				url: archivoSustentoVar.urlListarActividades+'&'+archivoSustentoVar.formCommand.serialize(),
				type: 'post',
				dataType: 'json',
				data : {
					   <portlet:namespace />formatoActiv: archivoSustentoVar.formatoF.val()						  
				},
				success: function(data) {		
					dwr.util.removeAllOptions("itemActividad");
					dwr.util.addOptions("itemActividad", ["--Seleccione--"]);
					dwr.util.addOptions("itemActividad", data,"codigoActividad","descActividad");
					$('#itemActividad').val(item_Actividad); //para mostrar la actividad selecccionada
					//para mostrar el papel de carga de archivo de sustento
					if( archivoSustentoVar.flagCarga.val()=='0' ){//proviene de archivos nuevos
						archivoSustentoVar.flagCarga.val('2');//para cargar archivos excel
					}else if(archivoSustentoVar.flagCarga.val()=='1' ){//proviene de archivos modificados
						archivoSustentoVar.flagCarga.val('3');//para cargar archivos excel
					}
					archivoSustentoVar.divOverlay.show();
					archivoSustentoVar.dialogCargaArchivos.show();			   
					archivoSustentoVar.dialogCargaArchivos.draggable();
					archivoSustentoVar.dialogCargaArchivos.css({ 
				        'left': ($(window).width() / 2 - archivoSustentoVar.dialogCargaArchivos.width() / 2) + 'px', 
				        'top': ($(window).height()  - archivoSustentoVar.dialogCargaArchivos.height() ) + 'px'
				    });
				},error : function(){
					var addhtmError='Error de conexión.';					
					archivoSustentoVar.dialogErrorContent.html(addhtmError);
					archivoSustentoVar.dialogError.dialog("open");
					archivoSustentoVar.initBlockUI();
				}
		     });	
		},	
		
		validarArchivoCarga : function() {			
			if($('#itemActividad').val()==null || 
					$('#itemActividad').val().length == '' ) { 								
				$("#msjUploadFile").html("Seleccione un Ítem de Actividad para proceder con la carga de archivo.");		
				return false; 
			}			
			return true; 
		},		
		
		
		
		/**Function para confirmar si quiere eliminar el registro o no*/
		confirmarEliminarArchivo : function(cod_item,cod_correlativo){
			console.debug("entranado a eliminar confirmar archivo sustento:  "+cod_item);
			var flagOperacion = archivoSustentoVar.flagOperacion.val();
			 console.debug("Flag de operacion al momento de eliminar archivo");
			if(flagOperacion=='ABIERTO'){
				var addhtml='¿Está seguro que desea eliminar el Archivo de Sustento seleccionado?';
				archivoSustentoVar.dialogConfirmContent.html(addhtml);
				archivoSustentoVar.dialogConfirm.dialog("open");				
				codMotivo= cod_item; 
				codCorrelativo= cod_correlativo;
			}else if(flagOperacion=='CERRADO'){				
				var addhtmInfo='El plazo para realizar esta acción se encuentra cerrado';				
				archivoSustentoVar.dialogInfoContent.html(addhtmInfo);
				archivoSustentoVar.dialogInfo.dialog("open");	
			}else{				
				var addhtmInfo='Este Formato seleccionado ya fue enviado a OSINERGMIN-GART.';				
				archivoSustentoVar.dialogInfoContent.html(addhtmInfo);
				archivoSustentoVar.dialogInfo.dialog("open");	
			}		
		},
		/**Function para  eliminar el registro una vez hecho la confirmacion*/
		eliminarArchivoSustento : function(codMotivo,codCorrelativo){
			console.debug("entranado a eliminar motivo:  "+codMotivo);
			$.blockUI({ message: archivoSustentoVar.mensajeEliminar });
			jQuery.ajax({
				url: archivoSustentoVar.urlEliminar+'&'+archivoSustentoVar.formCommand.serialize(),
				type: 'post',
				dataType: 'json',
				data: {				
					   <portlet:namespace />itemArchivo: codMotivo,
					   <portlet:namespace />corrArchivo: codCorrelativo				  
					},
				success: function(data) {
					if (data.resultado == "OK"){
						var addhtml2='El Archivo de Sustento fue eliminado satisfactoriamente';					
						archivoSustentoVar.dialogMessageContent.html(addhtml2);
						archivoSustentoVar.dialogMessage.dialog("open");
						archivoSustentoVar.buscarArchivosSustentoFormato(codCorrelativo);
						archivoSustentoVar.initBlockUI();
					}
					else{						
						var addhtmError='Error al eliminar el Archivo de Sustento seleccionado.';					
						archivoSustentoVar.dialogErrorContent.html(addhtmError);
						archivoSustentoVar.dialogError.dialog("open");
						archivoSustentoVar.initBlockUI();
					}
				},error : function(){
					var addhtmError='Error de conexión.';					
					archivoSustentoVar.dialogErrorContent.html(addhtmError);
					archivoSustentoVar.dialogError.dialog("open");
					archivoSustentoVar.initBlockUI();
				}
			});
		},	
		
		
		
		//funcion para regresar a buscar formatos
		<portlet:namespace/>regresarFormatos : function(){			
			archivoSustentoVar.divBuscar.show();
			archivoSustentoVar.divBuscarArch.hide();
			archivoSustentoVar.buscarFormatos('M');
		},
		
		regresarFormularioCargaArchivos : function(){
			archivoSustentoVar.flagCarga.val('');
			archivoSustentoVar.dialogCargaArchivos.hide();
			archivoSustentoVar.divOverlay.hide();   
			$("#msjUploadFile").html("");			
		},
				
		
		<portlet:namespace/>cargarArchivosSustentoFormato : function(){
			if (archivoSustentoVar.validarArchivoCarga()){
				var frm = document.getElementById('archivoSustentoBean');
				
				var nameFile=$("#fileArchivoSustento").val();
				var isSubmit=true;
				
				$("#msjUploadFile").html("");			
				if(typeof (nameFile) == "undefined" || nameFile.length==0){				
					isSubmit=false;
					$("#msjUploadFile").html("Debe seleccionar un archivo");
				}else{
					isSubmit=true;
					//isSubmit=false;
					var extension=nameFile.substr(nameFile.indexOf(".")+1,nameFile.length);		
					console.debug("Extencion del archivo a subir:  "+extension);
					/*if(extension == 'xls' || extension == 'xlsx'){
						isSubmit=true;
					}else{
						isSubmit=false;
						$("#msjUploadFile").html("Archivo inválido");
					}*/				
				}			
				if(isSubmit){
					frm.submit();
				}		
			}				
		},
		
		
		
		//DIALOGOS
		initDialogs : function(){		
			//dialogo para eliminar archivo de sustento
			archivoSustentoVar.dialogConfirm.dialog({
				modal: true,
				height: 200,
				width: 450,			
				autoOpen: false,
				buttons: {
					"Si": function() {
						archivoSustentoVar.eliminarArchivoSustento(codMotivo,codCorrelativo);
						$( this ).dialog("close");
					},
					"No": function() {				
						$( this ).dialog("close");
					}
				}
			});			
			
			
			archivoSustentoVar.dialogMessage.dialog({
				modal: true,
				autoOpen: false,
				width: 450,		
				buttons: {
					Ok: function() {
						$( this ).dialog("close");
					}
				}
			});
			
			 archivoSustentoVar.dialogObservacion.dialog({
				modal: true,
				width: 700,
				autoOpen: false,
				buttons: {
					Cerrar: function() {
						$( this ).dialog("close");
					}
				}
			}); 
			 
			 archivoSustentoVar.dialogObservacion12.dialog({
					modal: true,
					width: 740,
					autoOpen: false,
					buttons: {
						Cerrar: function() {
							$( this ).dialog("close");
						}
					}
				}); 
			 
			 archivoSustentoVar.dialogObservacion13.dialog({
					modal: true,
					width: 730,
					autoOpen: false,
					buttons: {
						Cerrar: function() {
							$( this ).dialog("close");
						}
					}
				}); 		
			 
			 archivoSustentoVar.dialogValidacion.dialog({
					modal: true,
					autoOpen: false,
					width:450,
					buttons: {
						Aceptar: function() {
							archivoSustentoVar.ponerFocus(cod_focus);
							$( this ).dialog("close");
						}
					}
				});
				
			 archivoSustentoVar.dialogError.dialog({
					modal: true,
					autoOpen: false,
					width: 450,		
					buttons: {
						Aceptar: function() {
							$( this ).dialog("close");
						}
					}
				});
				
			 archivoSustentoVar.dialogInfo.dialog({
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