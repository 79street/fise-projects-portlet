<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects />

<script type="text/javascript">
var formato14A= {
	tablaResultados:null,
	paginadoResultados:null,
	//
	urlBusqueda: null,
	urlCargaPeriodo:null,
	urlCargaFlagPeriodo:null,
	urlCrud:null,
	//
	mensajeCargando:null,
	mensajeObteniendoDatos:null,
	mensajeEliminando:null,
	mensajeGuardando:null,
	//valores hidden
	procesoEstado:null,
	etapaEdit:null,
	//
	formCommand: null,
	botonBuscar:null,
	botonNuevo:null,
	botonRegresar:null,
	botonReportePdf:null,
	botonReporteExcel:null,
	botonGrabar:null,
	botonValidacion:null,
	botonEnvioDefinitivo:null,
	//
	panelCargaArchivo:null,
	//
	i_codEmpresaB:null,
	i_anioDesde:null,
	i_mesDesde:null,
	i_anioHasta:null,
	i_mesHasta:null,
	i_etapaB:null,
	//dialogs
	dialogMessage:null,
	dialogConfirm:null,
	dialogConfirmEnvio:null,
	dialogCarga:null,
	dialogError:null,
	dialogObservacion:null,
	dialogMessageContent:null,
	dialogConfirmContent:null,
	//divs
	divHome:null,
	divFormato:null,
	divPeriodoEjecucion:null,
	//formulario
	f_empresa:null,
	f_periodoEnvio:null,
	f_flagPeriodo:null,
	//RURAL
	f_sumEmpadDifR:null,f_totalEmpadR:null,f_impEsqInvR:null,f_impDeclJuradaR:null,f_impFichaVerifR:null,f_repEsqInvR:null,f_verifInfoR:null,f_elabArchBenefR:null,f_digitFichaR:null,
	f_totalDifIniProgR:null,f_impVolanteR:	null,f_impAficheR:null,f_repFolletoR:null,f_spotPublicTvR:null,f_spotPublicRadioR:null,f_nroBenefR:null,f_costoUnitEmpadR:null,
	f_totalAgentR:null,f_promConvR:null,f_regFirmaConvR:null,f_impBandR:null,f_nroAgentR:null,f_costoUnitAgentR:null,
	//PROVINCIA
	f_sumEmpadDifP:null,f_totalEmpadP:null,f_impEsqInvP:null,f_impDeclJuradaP:null,f_impFichaVerifP:null,f_repEsqInvP:null,f_verifInfoP:null,f_elabArchBenefP:null,f_digitFichaP:null,
	f_totalDifIniProgP:null,f_impVolanteP:	null,f_impAficheP:null,f_repFolletoP:null,f_spotPublicTvP:null,f_spotPublicRadioP:null,f_nroBenefP:null,f_costoUnitEmpadP:null,
	f_totalAgentP:null,f_promConvP:null,f_regFirmaConvP:null,f_impBandP:null,f_nroAgentP:null,f_costoUnitAgentP:null,
	//LMA
	f_sumEmpadDifL:null,f_totalEmpadL:null,f_impEsqInvL:null,f_impDeclJuradaL:null,f_impFichaVerifL:null,f_repEsqInvL:null,f_verifInfoL:null,f_elabArchBenefL:null,f_digitFichaL:null,
	f_totalDifIniProgL:null,f_impVolanteL:	null,f_impAficheL:null,f_repFolletoL:null,f_spotPublicTvL:null,f_spotPublicRadioL:null,f_nroBenefL:null,f_costoUnitEmpadL:null,
	f_totalAgentL:null,f_promConvL:null,f_regFirmaConvL:null,f_impBandL:null,f_nroAgentL:null,f_costoUnitAgentL:null,
	//
	init : function(){
		this.tablaResultados=$("#<portlet:namespace/>grid_formato");
		this.paginadoResultados='#<portlet:namespace/>pager_formato';
		//
		this.urlBusqueda='<portlet:resourceURL id="busqueda" />';
		this.urlCargaPeriodo='<portlet:resourceURL id="cargaPeriodo" />';
		this.urlCargaFlagPeriodo='<portlet:resourceURL id="cargaFlagPeriodo" />';
		this.urlCrud='<portlet:resourceURL id="crud" />';
		//mensajes
		this.mensajeCargando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Cargando </h3>';
		this.mensajeObteniendoDatos='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Obteniendo Datos </h3>';
		this.mensajeEliminando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Eliminando </h3>';
		this.mensajeGuardando='<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Guardando Datos </h3>';
		//valores hidden utilitarios
		this.procesoEstado=$('#<portlet:namespace/>Estado');
		this.etapaEdit=$('#<portlet:namespace/>etapaEdit');
		//
		this.formCommand=$('#formato14ACBean');
		//
		this.i_codEmpresaB=$('#codEmpresaB');
		this.i_anioDesde=$('#anioDesde');
		this.i_mesDesde=$('#mesDesde');
		this.i_anioHasta=$('#anioHasta');
		this.i_mesHasta=$('#mesHasta');
		this.i_etapaB=$('#etapaB');
		//carga de dialogs id
		this.dialogMessage=$("#dialog-message");
		this.dialogConfirm=$("#dialog-confirm");
		this.dialogConfirmEnvio=$("#dialog-confirm-envio");
		this.dialogCarga=$("#dialog-form-carga");
		this.dialogError=$("#dialog-form-error");
		this.dialogObservacion=$("#dialog-form-observacion");
		this.dialogMessageContent=$("#dialog-message-content");
		this.dialogConfirmContent=$('#dialog-confirm-content');
		//divs
		this.divHome=$("#<portlet:namespace/>div_home");
		this.divFormato=$("#<portlet:namespace/>div_formato");
		this.divPeriodoEjecucion=$("#<portlet:namespace/>divPeriodoEjecucion");
		//formulario
		this.f_empresa=$('#codigoEmpresa');
		this.f_periodoEnvio=$('#periodoEnvio');
		this.f_flagPeriodo=$("#flagPeriodoEjecucion");
		//
		this.f_sumEmpadDifR=$('#sumEmpadDifusionR');
		this.f_totalEmpadR=$('#totalEmpadR');
		this.f_impEsqInvR=$('#imprEsqInvitR');
		this.f_impDeclJuradaR=$('#imprDeclaJuradaR');
		this.f_impFichaVerifR=$('#imprFichaVerifR');
		this.f_repEsqInvR=$('#repartoEsqInvitR');
		this.f_verifInfoR=$('#verifInfoR');
		this.f_elabArchBenefR=$('#elabArchivoBenefR');
		this.f_digitFichaR=$('#digitFichaBenefR');
		this.f_totalDifIniProgR=$('#totalDifIniProgR');
		this.f_impVolanteR=$('#imprVolantesR');
		this.f_impAficheR=$('#imprAfichesR');
		this.f_repFolletoR=$('#repFolletosR');
		this.f_spotPublicTvR=$('#spotPublTvR');
		this.f_spotPublicRadioR=$('#spotPublRadioR');
		this.f_nroBenefR=$('#nroBenefEmpadR');
		this.f_costoUnitEmpadR=$('#costoUnitEmpadR');
		this.f_totalAgentR=$('#totalCostoAgentR');
		this.f_promConvR=$('#promConvAgentR');
		this.f_regFirmaConvR=$('#regConvAgentR');
		this.f_impBandR=$('#impEntrBandR');
		this.f_nroAgentR=$('#nroAgentR');
		this.f_costoUnitAgentR=$('#costoUnitAgentR');
		//
		this.f_sumEmpadDifP=$('#sumEmpadDifusionP');
		this.f_totalEmpadP=$('#totalEmpadP');
		this.f_impEsqInvP=$('#imprEsqInvitP');
		this.f_impDeclJuradaP=$('#imprDeclaJuradaP');
		this.f_impFichaVerifP=$('#imprFichaVerifP');
		this.f_repEsqInvP=$('#repartoEsqInvitP');
		this.f_verifInfoP=$('#verifInfoP');
		this.f_elabArchBenefP=$('#elabArchivoBenefP');
		this.f_digitFichaP=$('#digitFichaBenefP');
		this.f_totalDifIniProgP=$('#totalDifIniProgP');
		this.f_impVolanteP=$('#imprVolantesP');
		this.f_impAficheP=$('#imprAfichesP');
		this.f_repFolletoP=$('#repFolletosP');
		this.f_spotPublicTvP=$('#spotPublTvP');
		this.f_spotPublicRadioP=$('#spotPublRadioP');
		this.f_nroBenefP=$('#nroBenefEmpadP');
		this.f_costoUnitEmpadP=$('#costoUnitEmpadP');
		this.f_totalAgentP=$('#totalCostoAgentP');
		this.f_promConvP=$('#promConvAgentP');
		this.f_regFirmaConvP=$('#regConvAgentP');
		this.f_impBandP=$('#impEntrBandP');
		this.f_nroAgentP=$('#nroAgentP');
		this.f_costoUnitAgentP=$('#costoUnitAgentP');
		//
		this.f_sumEmpadDifL=$('#sumEmpadDifusionL');
		this.f_totalEmpadL=$('#totalEmpadL');
		this.f_impEsqInvL=$('#imprEsqInvitL');
		this.f_impDeclJuradaL=$('#imprDeclaJuradaL');
		this.f_impFichaVerifL=$('#imprFichaVerifL');
		this.f_repEsqInvL=$('#repartoEsqInvitL');
		this.f_verifInfoL=$('#verifInfoL');
		this.f_elabArchBenefL=$('#elabArchivoBenefL');
		this.f_digitFichaL=$('#digitFichaBenefL');
		this.f_totalDifIniProgL=$('#totalDifIniProgL');
		this.f_impVolanteL=$('#imprVolantesL');
		this.f_impAficheL=$('#imprAfichesL');
		this.f_repFolletoL=$('#repFolletosL');
		this.f_spotPublicTvL=$('#spotPublTvL');
		this.f_spotPublicRadioL=$('#spotPublRadioL');
		this.f_nroBenefL=$('#nroBenefEmpadL');
		this.f_costoUnitEmpadL=$('#costoUnitEmpadL');
		this.f_totalAgentL=$('#totalCostoAgentL');
		this.f_promConvL=$('#promConvAgentL');
		this.f_regFirmaConvL=$('#regConvAgentL');
		this.f_impBandL=$('#impEntrBandL');
		this.f_nroAgentL=$('#nroAgentL');
		this.f_costoUnitAgentL=$('#costoUnitAgentL');
		//
		this.buildGrids();
		this.botonNuevo=$("#<portlet:namespace/>crearFormato");
		this.botonBuscar=$("#<portlet:namespace/>buscarFormato");	
		this.botonRegresar=$("#<portlet:namespace/>regresarFormato");
		this.botonReportePdf=$("#<portlet:namespace/>reportePdf");
		this.botonReporteExcel=$("#<portlet:namespace/>reporteExcel");
		this.botonGrabar=$("#<portlet:namespace/>guardarFormato");
		this.botonValidacion=$("#<portlet:namespace/>validacionFormato");
		this.botonEnvioDefinitivo=$("#<portlet:namespace/>envioDefinitivo");
		//
		this.panelCargaArchivo=$("#<portlet:namespace/>panelCargaArchivos");
		//eventos
		formato14A.botonNuevo.click(function() {formato14A.<portlet:namespace/>crearFormato();});
		formato14A.botonBuscar.click(function() {formato14A.buscar();});
		formato14A.botonGrabar.click(function() {formato14A.<portlet:namespace/>guardarFormato();});
		formato14A.botonRegresar.click(function() {formato14A.<portlet:namespace/>regresar();});
		formato14A.f_empresa.change(function(){formato14A.<portlet:namespace/>loadPeriodo('');});
		formato14A.f_periodoEnvio.change(function(){formato14A.<portlet:namespace/>loadPeriodo(this.value);});
		//eventos por defecto
		formato14A.botonBuscar.trigger('click');
		formato14A.initBlockUI();
		//$('input[type=text].target').on('change', function(){
		$('input.target[type=text]').on('change', function(){
			formato14A.calculoTotal();
		});
	},
	buildGrids : function () {	
		formato14A.tablaResultados.jqGrid({
		   datatype: "local",
	       colNames: ['Empresa','Año Pres.','Mes Pres.','Año Ini. Vig.','Año Fin Vig.','Grupo Inf','Visualizar','Editar','Anular','Estado','','',''],
	       colModel: [
					{ name: 'descEmpresa', index: 'descEmpresa', width: 50},
	               { name: 'anoPresentacion', index: 'anoPresentacion', width: 30 },   
	               { name: 'descMesPresentacion', index: 'descMesPresentacion', width: 30},
	               { name: 'anoIniVigencia', index: 'anoIniVigencia', width: 30 },   
	               { name: 'anoFinVigencia', index: 'anoFinVigencia', width: 30},
	               { name: 'grupoInfo', index: 'grupoInfo', width: 50},
	               { name: 'view', index: 'view', width: 20,align:'center' },
	               { name: 'edit', index: 'edit', width: 20,align:'center' },
	               { name: 'elim', index: 'elim', width: 20,align:'center' },
	               { name: 'estado', index: 'estado', width: 50,align:'center'},
	               { name: 'codEmpresa', index: 'codEmpresa', hidden: true},
	               { name: 'mesPresentacion', index: 'mesPresentacion', hidden: true},
	               { name: 'etapa', index: 'etapa', hidden: true}
		   	    ],
		   	 multiselect: false,
				rowNum:10,
			   	rowList:[10,20,50],
				height: 200,
			   	autowidth: true,
				rownumbers: true,
				shrinkToFit:true,
				pager: formato14A.paginadoResultados,
			    viewrecords: true,
			   	caption: "Formatos",
			    sortorder: "asc",	   	    	   	   
	       gridComplete: function(){
	      		var ids = formato14A.tablaResultados.jqGrid('getDataIDs');
	      		for(var i=0;i < ids.length;i++){
	      			var cl = ids[i];
	      			var ret = formato14A.tablaResultados.jqGrid('getRowData',cl);           
	      			view = "<a href='#'><img border='0' title='View' src='/net-theme/images/img-net/file.png'  align='center' onclick=\"formato14A.verFormato('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoIniVigencia+"','"+ret.anoFinVigencia+"','"+ret.etapa+"');\" /></a> ";
	      			edit = "<a href='#'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png'  align='center' onclick=\"formato14A.editarFormato('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoIniVigencia+"','"+ret.anoFinVigencia+"','"+ret.etapa+"');\" /></a> ";
	      			elem = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"formato14A.confirmarEliminar('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoIniVigencia+"','"+ret.anoFinVigencia+"','"+ret.etapa+"');\" /></a> ";              			
	      			formato14A.tablaResultados.jqGrid('setRowData',ids[i],{view:view});
	      			formato14A.tablaResultados.jqGrid('setRowData',ids[i],{edit:edit});
	      			formato14A.tablaResultados.jqGrid('setRowData',ids[i],{elim:elem});
	      		}
	      }
	  	});
		formato14A.tablaResultados.jqGrid('navGrid',formato14A.paginadoResultados,{add:false,edit:false,del:false,search: false,refresh: false});	
		formato14A.tablaResultados.jqGrid('navButtonAdd',formato14A.paginadoResultados,{
		       caption:"Exportar a Excel",
		       buttonicon: "ui-icon-bookmark",
		       onClickButton : function () {
		           location.href = '<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/ExportExcelPlus")%>';
		       } 
		}); 
	},
	buscar : function () {
		if(formato14A.validarBusqueda()){
			formato14A.blockUI();
			jQuery.ajax({			
					url: formato14A.urlBusqueda+'&'+formato14A.formCommand.serialize(),
					type: 'post',
					dataType: 'json',				
					success: function(gridData) {					
						formato14A.tablaResultados.clearGridData(true);
						formato14A.tablaResultados.jqGrid('setGridParam', {data: gridData}).trigger('reloadGrid');
						formato14A.tablaResultados[0].refreshIndex();
						formato14A.initBlockUI();
					},error : function(){
						alert("Error de conexión.");
						formato14A.initBlockUI();
					}
			});
		}
	},
	validarBusqueda : function(){
		if(formato14A.i_anioDesde.val().length != '' ) {		  
			  var numstr = trim(formato14A.i_anioDesde.val());
			  if (isNaN(numstr) || numstr.length<4 || parseFloat(numstr)<1900){
				  alert('Ingrese un año desde válido');
				  formato14A.i_anioDesde.focus();
				  return false;
			  }
		  }
		  if(formato14A.i_anioHasta.val().length != '' ) {		  
			  var numstr = trim(formato14A.i_anioHasta.val());
			  if (isNaN(numstr) || numstr.length<4 || parseFloat(numstr)<1900){
				  alert('Ingrese un año hasta válido');
				  formato14A.i_anioHasta.focus();
				  return false;
			  }
		  }
		  if(formato14A.i_anioDesde.val().length != '' && formato14A.i_anioHasta.val().length != '' ) {
			  if( parseFloat(formato14A.i_anioDesde.val()) > parseFloat(formato14A.i_anioHasta.val()) ){
					alert('El año desde no puede exceder al año hasta');
					return false;
			  }
		  }
		  if(formato14A.i_etapaB.val().length == '' ) { 	    
			    alert('Seleccione una etapa');
			    formato14A.i_etapaB.focus();
			    return false; 
		  }
		 return true; 
	},
	<portlet:namespace/>crearFormato :  function(){	
		formato14A.inicializarFormulario();
		formato14A.procesoEstado.val('SAVE');
		formato14A.etapaEdit.val("");
		formato14A.divFormato.show();
		formato14A.divHome.hide();
		//$('#flagCarga').val('0');
		//
		if( formato14A.f_flagPeriodo.val()=='S' ){
			//poner valores guardadose en sesion
		}
		//if( $('#flagPeriodoEjecucion').val()=='S' ){
		//	 $("#i_anioejecuc").val($("#anioDesdeSes").val());
		//	 $("#s_mes_ejecuc").val(parseInt($("#mesDesdeSes").val()));
		//}
		formato14A.botonValidacion.css('display','none');
		formato14A.<portlet:namespace/>loadPeriodo('');
	},
	<portlet:namespace/>regresar : function(){
		formato14A.procesoEstado.val('');
		formato14A.etapaEdit.val("");
		formato14A.divFormato.hide();
		formato14A.divHome.show();		
		//
		formato14A.removerDeshabilitados();
		//se visualizan los componentes escondidos
		formato14A.botonReportePdf.css('display','none');
		formato14A.botonReporteExcel.css('display','none');
		formato14A.botonGrabar.css('display','');
		formato14A.botonValidacion.css('display','');
		formato14A.botonEnvioDefinitivo.css('display','');
		
		formato14A.panelCargaArchivo.css('display','');

		formato14A.botonBuscar.trigger('click');
	},
	inicializarFormulario : function(){
		formato14A.f_empresa.val('');
		if( formato14A.f_flagPeriodo.val()=='S' ){
			$('#anioInicioVigencia').val('');
			$('#anioFinVigencia').val('');
			$('#anioInicioVigencia').attr("disabled",false);
			$('#anioFinVigencia').attr("disabled",false);
		}
		//RURAL
		formato14A.f_sumEmpadDifR.val('0.00');
		formato14A.f_totalEmpadR.val('0.00');
		formato14A.f_impEsqInvR.val('0.00');
		formato14A.f_impDeclJuradaR.val('0.00');
		formato14A.f_impFichaVerifR.val('0.00');
		formato14A.f_repEsqInvR.val('0.00');
		formato14A.f_verifInfoR.val('0.00');
		formato14A.f_elabArchBenefR.val('0.00');
		formato14A.f_digitFichaR.val('0.00');
		formato14A.f_totalDifIniProgR.val('0.00');
		formato14A.f_impVolanteR.val('0.00');
		formato14A.f_impAficheR.val('0.00');
		formato14A.f_repFolletoR.val('0.00');
		formato14A.f_spotPublicTvR.val('0.00');
		formato14A.f_spotPublicRadioR.val('0.00');
		formato14A.f_nroBenefR.val('0');
		formato14A.f_costoUnitEmpadR.val('0.00');
		formato14A.f_totalAgentR.val('0.00');
		formato14A.f_promConvR.val('0.00');
		formato14A.f_regFirmaConvR.val('0.00');
		formato14A.f_impBandR.val('0.00');
		formato14A.f_nroAgentR.val('0');
		formato14A.f_costoUnitAgentR.val('0.00');
		//PROVINCIA
		formato14A.f_sumEmpadDifP.val('0.00');
		formato14A.f_totalEmpadP.val('0.00');
		formato14A.f_impEsqInvP.val('0.00');
		formato14A.f_impDeclJuradaP.val('0.00');
		formato14A.f_impFichaVerifP.val('0.00');
		formato14A.f_repEsqInvP.val('0.00');
		formato14A.f_verifInfoP.val('0.00');
		formato14A.f_elabArchBenefP.val('0.00');
		formato14A.f_digitFichaP.val('0.00');
		formato14A.f_totalDifIniProgP.val('0.00');
		formato14A.f_impVolanteP.val('0.00');
		formato14A.f_impAficheP.val('0.00');
		formato14A.f_repFolletoP.val('0.00');
		formato14A.f_spotPublicTvP.val('0.00');
		formato14A.f_spotPublicRadioP.val('0.00');
		formato14A.f_nroBenefP.val('0');
		formato14A.f_costoUnitEmpadP.val('0.00');
		formato14A.f_totalAgentP.val('0.00');
		formato14A.f_promConvP.val('0.00');
		formato14A.f_regFirmaConvP.val('0.00');
		formato14A.f_impBandP.val('0.00');
		formato14A.f_nroAgentP.val('0');
		formato14A.f_costoUnitAgentP.val('0.00');
		//LIMA
		formato14A.f_sumEmpadDifL.val('0.00');
		formato14A.f_totalEmpadL.val('0.00');
		formato14A.f_impEsqInvL.val('0.00');
		formato14A.f_impDeclJuradaL.val('0.00');
		formato14A.f_impFichaVerifL.val('0.00');
		formato14A.f_repEsqInvL.val('0.00');
		formato14A.f_verifInfoL.val('0.00');
		formato14A.f_elabArchBenefL.val('0.00');
		formato14A.f_digitFichaL.val('0.00');
		formato14A.f_totalDifIniProgL.val('0.00');
		formato14A.f_impVolanteL.val('0.00');
		formato14A.f_impAficheL.val('0.00');
		formato14A.f_repFolletoL.val('0.00');
		formato14A.f_spotPublicTvL.val('0.00');
		formato14A.f_spotPublicRadioL.val('0.00');
		formato14A.f_nroBenefL.val('0');
		formato14A.f_costoUnitEmpadL.val('0.00');
		formato14A.f_totalAgentL.val('0.00');
		formato14A.f_promConvL.val('0.00');
		formato14A.f_regFirmaConvL.val('0.00');
		formato14A.f_impBandL.val('0.00');
		formato14A.f_nroAgentL.val('0');
		formato14A.f_costoUnitAgentL.val('0.00');
		
		//quitamos los componentes deshabilitados
		formato14A.f_empresa.attr("disabled",false);
		formato14A.f_periodoEnvio.attr("disabled",false);
		//
		//formato14A.deshabilitarCampos();
		//
		formato14A.soloNumerosEnteros();
		formato14A.soloNumerosDecimales();
	},
	removerDeshabilitados : function(){
		formato14A.f_empresa.removeAttr("disabled");
		formato14A.f_periodoEnvio.removeAttr("disabled");
		if( formato14A.f_flagPeriodo.val()=='S' ){
			$('#anioInicioVigencia').removeAttr("disabled");
			$('#anioFinVigencia').removeAttr("disabled");
		}
		//RURAL
		formato14A.f_impEsqInvR.removeAttr("disabled");
		formato14A.f_impDeclJuradaR.removeAttr("disabled");
		formato14A.f_impFichaVerifR.removeAttr("disabled");
		formato14A.f_repEsqInvR.removeAttr("disabled");
		formato14A.f_verifInfoR.removeAttr("disabled");
		formato14A.f_elabArchBenefR.removeAttr("disabled");
		formato14A.f_digitFichaR.removeAttr("disabled");
		formato14A.f_impVolanteR.removeAttr("disabled");
		formato14A.f_impAficheR.removeAttr("disabled");
		formato14A.f_repFolletoR.removeAttr("disabled");
		formato14A.f_spotPublicTvR.removeAttr("disabled");
		formato14A.f_spotPublicRadioR.removeAttr("disabled");
		formato14A.f_nroBenefR.removeAttr("disabled");
		formato14A.f_promConvR.removeAttr("disabled");
		formato14A.f_regFirmaConvR.removeAttr("disabled");
		formato14A.f_impBandR.removeAttr("disabled");
		formato14A.f_nroAgentR.removeAttr("disabled");
		//PROVINCIA
		formato14A.f_impEsqInvP.removeAttr("disabled");
		formato14A.f_impDeclJuradaP.removeAttr("disabled");
		formato14A.f_impFichaVerifP.removeAttr("disabled");
		formato14A.f_repEsqInvP.removeAttr("disabled");
		formato14A.f_verifInfoP.removeAttr("disabled");
		formato14A.f_elabArchBenefP.removeAttr("disabled");
		formato14A.f_digitFichaP.removeAttr("disabled");
		formato14A.f_impVolanteP.removeAttr("disabled");
		formato14A.f_impAficheP.removeAttr("disabled");
		formato14A.f_repFolletoP.removeAttr("disabled");
		formato14A.f_spotPublicTvP.removeAttr("disabled");
		formato14A.f_spotPublicRadioP.removeAttr("disabled");
		formato14A.f_nroBenefP.removeAttr("disabled");
		formato14A.f_promConvP.removeAttr("disabled");
		formato14A.f_regFirmaConvP.removeAttr("disabled");
		formato14A.f_impBandP.removeAttr("disabled");
		formato14A.f_nroAgentP.removeAttr("disabled");
		//LIMA
		formato14A.f_impEsqInvL.removeAttr("disabled");
		formato14A.f_impDeclJuradaL.removeAttr("disabled");
		formato14A.f_impFichaVerifL.removeAttr("disabled");
		formato14A.f_repEsqInvL.removeAttr("disabled");
		formato14A.f_verifInfoL.removeAttr("disabled");
		formato14A.f_elabArchBenefL.removeAttr("disabled");
		formato14A.f_digitFichaL.removeAttr("disabled");
		formato14A.f_impVolanteL.removeAttr("disabled");
		formato14A.f_impAficheL.removeAttr("disabled");
		formato14A.f_repFolletoL.removeAttr("disabled");
		formato14A.f_spotPublicTvL.removeAttr("disabled");
		formato14A.f_spotPublicRadioL.removeAttr("disabled");
		formato14A.f_nroBenefL.removeAttr("disabled");
		formato14A.f_promConvL.removeAttr("disabled");
		formato14A.f_regFirmaConvL.removeAttr("disabled");
		formato14A.f_impBandL.removeAttr("disabled");
		formato14A.f_nroAgentL.removeAttr("disabled");
	},
	deshabilitarCamposView : function(){
		//RURAL
		formato14A.f_impEsqInvR.attr("disabled",true);
		formato14A.f_impDeclJuradaR.attr("disabled",true);
		formato14A.f_impFichaVerifR.attr("disabled",true);
		formato14A.f_repEsqInvR.attr("disabled",true);
		formato14A.f_verifInfoR.attr("disabled",true);
		formato14A.f_elabArchBenefR.attr("disabled",true);
		formato14A.f_digitFichaR.attr("disabled",true);
		formato14A.f_impVolanteR.attr("disabled",true);
		formato14A.f_impAficheR.attr("disabled",true);
		formato14A.f_repFolletoR.attr("disabled",true);
		formato14A.f_spotPublicTvR.attr("disabled",true);
		formato14A.f_spotPublicRadioR.attr("disabled",true);
		formato14A.f_nroBenefR.attr("disabled",true);
		formato14A.f_promConvR.attr("disabled",true);
		formato14A.f_regFirmaConvR.attr("disabled",true);
		formato14A.f_impBandR.attr("disabled",true);
		formato14A.f_nroAgentR.attr("disabled",true);
		//PROVINCIA
		formato14A.f_impEsqInvP.attr("disabled",true);
		formato14A.f_impDeclJuradaP.attr("disabled",true);
		formato14A.f_impFichaVerifP.attr("disabled",true);
		formato14A.f_repEsqInvP.attr("disabled",true);
		formato14A.f_verifInfoP.attr("disabled",true);
		formato14A.f_elabArchBenefP.attr("disabled",true);
		formato14A.f_digitFichaP.attr("disabled",true);
		formato14A.f_impVolanteP.attr("disabled",true);
		formato14A.f_impAficheP.attr("disabled",true);
		formato14A.f_repFolletoP.attr("disabled",true);
		formato14A.f_spotPublicTvP.attr("disabled",true);
		formato14A.f_spotPublicRadioP.attr("disabled",true);
		formato14A.f_nroBenefP.attr("disabled",true);
		formato14A.f_promConvP.attr("disabled",true);
		formato14A.f_regFirmaConvP.attr("disabled",true);
		formato14A.f_impBandP.attr("disabled",true);
		formato14A.f_nroAgentP.attr("disabled",true);
		//LIMA
		formato14A.f_impEsqInvL.attr("disabled",true);
		formato14A.f_impDeclJuradaL.attr("disabled",true);
		formato14A.f_impFichaVerifL.attr("disabled",true);
		formato14A.f_repEsqInvL.attr("disabled",true);
		formato14A.f_verifInfoL.attr("disabled",true);
		formato14A.f_elabArchBenefL.attr("disabled",true);
		formato14A.f_digitFichaL.attr("disabled",true);
		formato14A.f_impVolanteL.attr("disabled",true);
		formato14A.f_impAficheL.attr("disabled",true);
		formato14A.f_repFolletoL.attr("disabled",true);
		formato14A.f_spotPublicTvL.attr("disabled",true);
		formato14A.f_spotPublicRadioL.attr("disabled",true);
		formato14A.f_nroBenefL.attr("disabled",true);
		formato14A.f_promConvL.attr("disabled",true);
		formato14A.f_regFirmaConvL.attr("disabled",true);
		formato14A.f_impBandL.attr("disabled",true);
		formato14A.f_nroAgentL.attr("disabled",true);
		//
		formato14A.botonReportePdf.css('display','');
		formato14A.botonReporteExcel.css('display','');
		formato14A.botonGrabar.css('display','none');
		formato14A.botonValidacion.css('display','none');
		formato14A.botonEnvioDefinitivo.css('display','none');
		//
		formato14A.panelCargaArchivo.css('display','none');
	},
	//CALCULO RURAL
	sumaEmpadDifusionRural : function(){
		var totalEmpad;
		var totalDifusion;
		var sumaEmpadDifusion;
		totalEmpad=formato14A.f_totalEmpadR.val();
		totalDifusion=formato14A.f_totalDifIniProgR.val();
		sumaEmpadDifusion = parseFloat(totalEmpad)+parseFloat(totalDifusion);
		//redondeo a 2, depende de la logica de negocio
		sumaEmpadDifusion = redondeo(sumaEmpadDifusion, 2);
		formato14A.f_sumEmpadDifR.val(parseFloat(sumaEmpadDifusion));	
	},
	totalEmpadronamientoRural : function(){
		var impresionEsquela;
		var impresionDeclaJurada;
		var impresionFichas;
		var repartoEsquela;
		var verifInformacion;
		var elabArchivo;
		var digitacionFichas;
		var totalEmpadronamiento;
		impresionEsquela = formato14A.f_impEsqInvR.val();
		impresionDeclaJurada = formato14A.f_impDeclJuradaR.val();
		impresionFichas = formato14A.f_impFichaVerifR.val();
		repartoEsquela = formato14A.f_repEsqInvR.val();
		verifInformacion = formato14A.f_verifInfoR.val();
		elabArchivo = formato14A.f_elabArchBenefR.val();
		digitacionFichas = formato14A.f_digitFichaR.val();
		//
		totalEmpadronamiento = parseFloat(impresionEsquela)+parseFloat(impresionDeclaJurada)+parseFloat(impresionFichas)+parseFloat(repartoEsquela)
			+parseFloat(verifInformacion)+parseFloat(elabArchivo)+parseFloat(digitacionFichas);
		totalEmpadronamiento = redondeo(totalEmpadronamiento, 2);
		//alert(totalEmpadronamiento);
		formato14A.f_totalEmpadR.val(parseFloat(totalEmpadronamiento));
	},
	totalDifusionRural : function(){
		var impresionVolantes;
		var impresionAfiches;
		var repartoFolletos;
		var spotTv;
		var spotRadio;
		var totalDifusion;
		impresionVolantes = formato14A.f_impVolanteR.val();
		impresionAfiches = formato14A.f_impAficheR.val();
		repartoFolletos = formato14A.f_repFolletoR.val();
		spotTv = formato14A.f_spotPublicTvR.val();
		spotRadio = formato14A.f_spotPublicRadioR.val();
		//
		totalDifusion = parseFloat(impresionVolantes)+parseFloat(impresionAfiches)+parseFloat(repartoFolletos)+parseFloat(spotTv)+parseFloat(spotRadio);
		totalDifusion = redondeo(totalDifusion, 2);
		formato14A.f_totalDifIniProgR.val(parseFloat(totalDifusion));
	},
	costoUnitarioEmpadRural : function(){
		var sumaEmpadDifusion;
		var nroBenefi;
		var costoUnitEmpadronamiento;
		sumaEmpadDifusion = formato14A.f_sumEmpadDifR.val();
		nroBenefi = formato14A.f_nroBenefR.val();
		//
		if( nroBenefi!=null && nroBenefi!=0 ){
			costoUnitEmpadronamiento=parseFloat(sumaEmpadDifusion)/parseFloat(nroBenefi);
			costoUnitEmpadronamiento=redondeo(costoUnitEmpadronamiento, 2);
		}else{
			costoUnitEmpadronamiento='0.00';
		}
		formato14A.f_costoUnitEmpadR.val(parseFloat(costoUnitEmpadronamiento));
	},
	totalGestionAgentesRural : function(){
		var promConvenio;
		var registroConvenio;
		var impresionBanderola;
		var totalGestionAgentes;
		promConvenio = formato14A.f_promConvR.val();
		registroConvenio = formato14A.f_regFirmaConvR.val();
		impresionBanderola = formato14A.f_impBandR.val();
		//
		totalGestionAgentes = parseFloat(promConvenio)+parseFloat(registroConvenio)+parseFloat(impresionBanderola);
		totalGestionAgentes = redondeo(totalGestionAgentes, 2);
		formato14A.f_totalAgentR.val(parseFloat(totalGestionAgentes));
	},
	costoUnitarioAgentesRural : function(){
		var totalAgentes;
		var nroAgentes;
		var costoUnitAgentes;
		totalAgentes = formato14A.f_totalAgentR.val();
		nroAgentes = formato14A.f_nroAgentR.val();
		//
		if( nroAgentes!=null && nroAgentes!=0 ){
			costoUnitAgentes=parseFloat(totalAgentes)/parseFloat(nroAgentes);
			costoUnitAgentes=redondeo(costoUnitAgentes, 2);
		}else{
			costoUnitAgentes='0.00';
		}
		formato14A.f_costoUnitAgentR.val(parseFloat(costoUnitAgentes));
	},
	//CALCULO PROVINCIA
	sumaEmpadDifusionProvincia : function(){
		var totalEmpad;
		var totalDifusion;
		var sumaEmpadDifusion;
		totalEmpad=formato14A.f_totalEmpadP.val();
		totalDifusion=formato14A.f_totalDifIniProgP.val();
		sumaEmpadDifusion = parseFloat(totalEmpad)+parseFloat(totalDifusion);
		//redondeo a 2, depende de la logica de negocio
		sumaEmpadDifusion = redondeo(sumaEmpadDifusion, 2);
		formato14A.f_sumEmpadDifP.val(parseFloat(sumaEmpadDifusion));	
	},
	totalEmpadronamientoProvincia : function(){
		var impresionEsquela;
		var impresionDeclaJurada;
		var impresionFichas;
		var repartoEsquela;
		var verifInformacion;
		var elabArchivo;
		var digitacionFichas;
		var totalEmpadronamiento;
		impresionEsquela = formato14A.f_impEsqInvP.val();
		impresionDeclaJurada = formato14A.f_impDeclJuradaP.val();
		impresionFichas = formato14A.f_impFichaVerifP.val();
		repartoEsquela = formato14A.f_repEsqInvP.val();
		verifInformacion = formato14A.f_verifInfoP.val();
		elabArchivo = formato14A.f_elabArchBenefP.val();
		digitacionFichas = formato14A.f_digitFichaP.val();
		//
		totalEmpadronamiento = parseFloat(impresionEsquela)+parseFloat(impresionDeclaJurada)+parseFloat(impresionFichas)+parseFloat(repartoEsquela)
			+parseFloat(verifInformacion)+parseFloat(elabArchivo)+parseFloat(digitacionFichas);
		totalEmpadronamiento = redondeo(totalEmpadronamiento, 2);
		formato14A.f_totalEmpadP.val(parseFloat(totalEmpadronamiento));
	},
	totalDifusionProvincia : function(){
		var impresionVolantes;
		var impresionAfiches;
		var repartoFolletos;
		var spotTv;
		var spotRadio;
		var totalDifusion;
		impresionVolantes = formato14A.f_impVolanteP.val();
		impresionAfiches = formato14A.f_impAficheP.val();
		repartoFolletos = formato14A.f_repFolletoP.val();
		spotTv = formato14A.f_spotPublicTvP.val();
		spotRadio = formato14A.f_spotPublicRadioP.val();
		//
		totalDifusion = parseFloat(impresionVolantes)+parseFloat(impresionAfiches)+parseFloat(repartoFolletos)+parseFloat(spotTv)+parseFloat(spotRadio);
		totalDifusion = redondeo(totalDifusion, 2);
		formato14A.f_totalDifIniProgP.val(parseFloat(totalDifusion));
	},
	costoUnitarioEmpadProvincia : function(){
		var sumaEmpadDifusion;
		var nroBenefi;
		var costoUnitEmpadronamiento;
		sumaEmpadDifusion = formato14A.f_sumEmpadDifP.val();
		nroBenefi = formato14A.f_nroBenefP.val();
		//
		if( nroBenefi!=null && nroBenefi!=0 ){
			costoUnitEmpadronamiento=parseFloat(sumaEmpadDifusion)/parseFloat(nroBenefi);
			costoUnitEmpadronamiento=redondeo(costoUnitEmpadronamiento, 2);
		}else{
			costoUnitEmpadronamiento='0.00';
		}
		formato14A.f_costoUnitEmpadP.val(parseFloat(costoUnitEmpadronamiento));
	},
	totalGestionAgentesProvincia : function(){
		var promConvenio;
		var registroConvenio;
		var impresionBanderola;
		var totalGestionAgentes;
		promConvenio = formato14A.f_promConvP.val();
		registroConvenio = formato14A.f_regFirmaConvP.val();
		impresionBanderola = formato14A.f_impBandP.val();
		//
		totalGestionAgentes = parseFloat(promConvenio)+parseFloat(registroConvenio)+parseFloat(impresionBanderola);
		totalGestionAgentes = redondeo(totalGestionAgentes, 2);
		formato14A.f_totalAgentP.val(parseFloat(totalGestionAgentes));
	},
	costoUnitarioAgentesProvincia : function(){
		var totalAgentes;
		var nroAgentes;
		var costoUnitAgentes;
		totalAgentes = formato14A.f_totalAgentP.val();
		nroAgentes = formato14A.f_nroAgentP.val();
		//
		if( nroAgentes!=null && nroAgentes!=0 ){
			costoUnitAgentes=parseFloat(totalAgentes)/parseFloat(nroAgentes);
			costoUnitAgentes=redondeo(costoUnitAgentes, 2);
		}else{
			costoUnitAgentes='0.00';
		}
		formato14A.f_costoUnitAgentP.val(parseFloat(costoUnitAgentes));
	},
	//CALCULO LIMA
	sumaEmpadDifusionLima : function(){
		var totalEmpad;
		var totalDifusion;
		var sumaEmpadDifusion;
		totalEmpad=formato14A.f_totalEmpadL.val();
		totalDifusion=formato14A.f_totalDifIniProgL.val();
		sumaEmpadDifusion = parseFloat(totalEmpad)+parseFloat(totalDifusion);
		//redondeo a 2, depende de la logica de negocio
		sumaEmpadDifusion = redondeo(sumaEmpadDifusion, 2);
		formato14A.f_sumEmpadDifL.val(parseFloat(sumaEmpadDifusion));	
	},
	totalEmpadronamientoLima : function(){
		var impresionEsquela;
		var impresionDeclaJurada;
		var impresionFichas;
		var repartoEsquela;
		var verifInformacion;
		var elabArchivo;
		var digitacionFichas;
		var totalEmpadronamiento;
		impresionEsquela = formato14A.f_impEsqInvL.val();
		impresionDeclaJurada = formato14A.f_impDeclJuradaL.val();
		impresionFichas = formato14A.f_impFichaVerifL.val();
		repartoEsquela = formato14A.f_repEsqInvL.val();
		verifInformacion = formato14A.f_verifInfoL.val();
		elabArchivo = formato14A.f_elabArchBenefL.val();
		digitacionFichas = formato14A.f_digitFichaL.val();
		//
		totalEmpadronamiento = parseFloat(impresionEsquela)+parseFloat(impresionDeclaJurada)+parseFloat(impresionFichas)+parseFloat(repartoEsquela)
			+parseFloat(verifInformacion)+parseFloat(elabArchivo)+parseFloat(digitacionFichas);
		totalEmpadronamiento = redondeo(totalEmpadronamiento, 2);
		formato14A.f_totalEmpadL.val(parseFloat(totalEmpadronamiento));
	},
	totalDifusionLima : function(){
		var impresionVolantes;
		var impresionAfiches;
		var repartoFolletos;
		var spotTv;
		var spotRadio;
		var totalDifusion;
		impresionVolantes = formato14A.f_impVolanteL.val();
		impresionAfiches = formato14A.f_impAficheL.val();
		repartoFolletos = formato14A.f_repFolletoL.val();
		spotTv = formato14A.f_spotPublicTvL.val();
		spotRadio = formato14A.f_spotPublicRadioL.val();
		//
		totalDifusion = parseFloat(impresionVolantes)+parseFloat(impresionAfiches)+parseFloat(repartoFolletos)+parseFloat(spotTv)+parseFloat(spotRadio);
		totalDifusion = redondeo(totalDifusion, 2);
		formato14A.f_totalDifIniProgL.val(parseFloat(totalDifusion));
	},
	costoUnitarioEmpadLima : function(){
		var sumaEmpadDifusion;
		var nroBenefi;
		var costoUnitEmpadronamiento;
		sumaEmpadDifusion = formato14A.f_sumEmpadDifL.val();
		nroBenefi = formato14A.f_nroBenefL.val();
		//
		if( nroBenefi!=null && nroBenefi!=0 ){
			costoUnitEmpadronamiento=parseFloat(sumaEmpadDifusion)/parseFloat(nroBenefi);
			costoUnitEmpadronamiento=redondeo(costoUnitEmpadronamiento, 2);
		}else{
			costoUnitEmpadronamiento='0.00';
		}
		formato14A.f_costoUnitEmpadL.val(parseFloat(costoUnitEmpadronamiento));
	},
	totalGestionAgentesLima : function(){
		var promConvenio;
		var registroConvenio;
		var impresionBanderola;
		var totalGestionAgentes;
		promConvenio = formato14A.f_promConvL.val();
		registroConvenio = formato14A.f_regFirmaConvL.val();
		impresionBanderola = formato14A.f_impBandL.val();
		//
		totalGestionAgentes = parseFloat(promConvenio)+parseFloat(registroConvenio)+parseFloat(impresionBanderola);
		totalGestionAgentes = redondeo(totalGestionAgentes, 2);
		formato14A.f_totalAgentL.val(parseFloat(totalGestionAgentes));
	},
	costoUnitarioAgentesLima : function(){
		var totalAgentes;
		var nroAgentes;
		var costoUnitAgentes;
		totalAgentes = formato14A.f_totalAgentL.val();
		nroAgentes = formato14A.f_nroAgentL.val();
		//
		if( nroAgentes!=null && nroAgentes!=0 ){
			costoUnitAgentes=parseFloat(totalAgentes)/parseFloat(nroAgentes);
			costoUnitAgentes=redondeo(costoUnitAgentes, 2);
		}else{
			costoUnitAgentes='0.00';
		}
		formato14A.f_costoUnitAgentL.val(parseFloat(costoUnitAgentes));
	},
	calculoTotal : function(){
		formato14A.totalEmpadronamientoRural();
		formato14A.totalEmpadronamientoProvincia();
		formato14A.totalEmpadronamientoLima();
		
		formato14A.totalDifusionRural();
		formato14A.totalDifusionProvincia();
		formato14A.totalDifusionLima();
		
		formato14A.sumaEmpadDifusionRural();
		formato14A.sumaEmpadDifusionProvincia();
		formato14A.sumaEmpadDifusionLima();
		
		formato14A.costoUnitarioEmpadRural();
		formato14A.costoUnitarioEmpadProvincia();
		formato14A.costoUnitarioEmpadLima();
		//
		formato14A.totalGestionAgentesRural();
		formato14A.totalGestionAgentesProvincia();
		formato14A.totalGestionAgentesLima();
		
		formato14A.costoUnitarioAgentesRural();
		formato14A.costoUnitarioAgentesProvincia();
		formato14A.costoUnitarioAgentesLima();
		
		formato14A.formularioCompletarDecimales();
	},
	soloNumerosEnteros : function(){
		formato14A.f_nroBenefR.attr("onkeypress","return soloNumerosDecimales(event, 1, 'nroBenefEmpadR',7,0)");
		formato14A.f_nroAgentR.attr("onkeypress","return soloNumerosDecimales(event, 1, 'nroAgentR',7,0)");
		formato14A.f_nroBenefP.attr("onkeypress","return soloNumerosDecimales(event, 1, 'nroBenefEmpadP',7,0)");
		formato14A.f_nroAgentP.attr("onkeypress","return soloNumerosDecimales(event, 1, 'nroAgentP',7,0)");
		formato14A.f_nroBenefL.attr("onkeypress","return soloNumerosDecimales(event, 1, 'nroBenefEmpadL',7,0)");
		formato14A.f_nroAgentL.attr("onkeypress","return soloNumerosDecimales(event, 1, 'nroAgentL',7,0)");
	},
	soloNumerosDecimales : function(){
		//RURAL
		formato14A.f_impEsqInvR.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadR',7,2)");
		formato14A.f_impDeclJuradaR.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadR',7,2)");
		formato14A.f_impFichaVerifR.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadR',7,2)");
		formato14A.f_repEsqInvR.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadR',7,2)");
		formato14A.f_verifInfoR.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadR',7,2)");
		formato14A.f_elabArchBenefR.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadR',7,2)");
		formato14A.f_digitFichaR.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadR',7,2)");
		formato14A.f_impVolanteR.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadR',7,2)");
		formato14A.f_impAficheR.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadR',7,2)");
		formato14A.f_repFolletoR.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadR',7,2)");
		formato14A.f_spotPublicTvR.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadR',7,2)");
		formato14A.f_spotPublicRadioR.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadR',7,2)");
		formato14A.f_promConvR.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadR',7,2)");
		formato14A.f_regFirmaConvR.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadR',7,2)");
		formato14A.f_impBandR.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadR',7,2)");
		//PROVINCIA
		formato14A.f_impEsqInvP.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadP',7,2)");
		formato14A.f_impDeclJuradaP.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadP',7,2)");
		formato14A.f_impFichaVerifP.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadP',7,2)");
		formato14A.f_repEsqInvP.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadP',7,2)");
		formato14A.f_verifInfoP.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadP',7,2)");
		formato14A.f_elabArchBenefP.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadP',7,2)");
		formato14A.f_digitFichaP.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadP',7,2)");
		formato14A.f_impVolanteP.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadP',7,2)");
		formato14A.f_impAficheP.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadP',7,2)");
		formato14A.f_repFolletoP.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadP',7,2)");
		formato14A.f_spotPublicTvP.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadP',7,2)");
		formato14A.f_spotPublicRadioP.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadP',7,2)");
		formato14A.f_promConvP.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadP',7,2)");
		formato14A.f_regFirmaConvP.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadP',7,2)");
		formato14A.f_impBandP.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadP',7,2)");
		//LIMA
		formato14A.f_impEsqInvL.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadL',7,2)");
		formato14A.f_impDeclJuradaL.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadL',7,2)");
		formato14A.f_impFichaVerifL.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadL',7,2)");
		formato14A.f_repEsqInvL.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadL',7,2)");
		formato14A.f_verifInfoL.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadL',7,2)");
		formato14A.f_elabArchBenefL.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadL',7,2)");
		formato14A.f_digitFichaL.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadL',7,2)");
		formato14A.f_impVolanteL.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadL',7,2)");
		formato14A.f_impAficheL.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadL',7,2)");
		formato14A.f_repFolletoL.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadL',7,2)");
		formato14A.f_spotPublicTvL.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadL',7,2)");
		formato14A.f_spotPublicRadioL.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadL',7,2)");
		formato14A.f_promConvL.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadL',7,2)");
		formato14A.f_regFirmaConvL.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadL',7,2)");
		formato14A.f_impBandL.attr("onkeypress","return soloNumerosDecimales(event, 2, 'nroBenefEmpadL',7,2)");
	},
	formularioCompletarDecimales : function(){
		//RURAL
		completarDecimal('sumEmpadDifusionR',2);
		completarDecimal('totalEmpadR',2);
		completarDecimal('imprEsqInvitR',2);
		completarDecimal('imprDeclaJuradaR',2);
		completarDecimal('imprFichaVerifR',2);
		completarDecimal('repartoEsqInvitR',2);
		completarDecimal('verifInfoR',2);
		completarDecimal('elabArchivoBenefR',2);
		completarDecimal('digitFichaBenefR',2);
		completarDecimal('totalDifIniProgR',2);
		completarDecimal('imprVolantesR',2);
		completarDecimal('imprAfichesR',2);
		completarDecimal('repFolletosR',2);
		completarDecimal('spotPublTvR',2);
		completarDecimal('spotPublRadioR',2);
		completarDecimal('costoUnitEmpadR',2);
		//
		completarDecimal('totalCostoAgentR',2);
		completarDecimal('promConvAgentR',2);
		completarDecimal('regConvAgentR',2);
		completarDecimal('impEntrBandR',2);
		completarDecimal('costoUnitAgentR',2);
		//PROVINCIA
		completarDecimal('sumEmpadDifusionP',2);
		completarDecimal('totalEmpadP',2);
		completarDecimal('imprEsqInvitP',2);
		completarDecimal('imprDeclaJuradaP',2);
		completarDecimal('imprFichaVerifP',2);
		completarDecimal('repartoEsqInvitP',2);
		completarDecimal('verifInfoP',2);
		completarDecimal('elabArchivoBenefP',2);
		completarDecimal('digitFichaBenefP',2);
		completarDecimal('totalDifIniProgP',2);
		completarDecimal('imprVolantesP',2);
		completarDecimal('imprAfichesP',2);
		completarDecimal('repFolletosP',2);
		completarDecimal('spotPublTvP',2);
		completarDecimal('spotPublRadioP',2);
		completarDecimal('costoUnitEmpadP',2);
		//
		completarDecimal('totalCostoAgentP',2);
		completarDecimal('promConvAgentP',2);
		completarDecimal('regConvAgentP',2);
		completarDecimal('impEntrBandP',2);
		completarDecimal('costoUnitAgentP',2);
		//LIMA
		completarDecimal('sumEmpadDifusionL',2);
		completarDecimal('totalEmpadL',2);
		completarDecimal('imprEsqInvitL',2);
		completarDecimal('imprDeclaJuradaL',2);
		completarDecimal('imprFichaVerifL',2);
		completarDecimal('repartoEsqInvitL',2);
		completarDecimal('verifInfoL',2);
		completarDecimal('elabArchivoBenefL',2);
		completarDecimal('digitFichaBenefL',2);
		completarDecimal('totalDifIniProgL',2);
		completarDecimal('imprVolantesL',2);
		completarDecimal('imprAfichesL',2);
		completarDecimal('repFolletosL',2);
		completarDecimal('spotPublTvL',2);
		completarDecimal('spotPublRadioL',2);
		completarDecimal('costoUnitEmpadL',2);
		//
		completarDecimal('totalCostoAgentL',2);
		completarDecimal('promConvAgentL',2);
		completarDecimal('regConvAgentL',2);
		completarDecimal('impEntrBandL',2);
		completarDecimal('costoUnitAgentL',2);
	},
	<portlet:namespace/>loadPeriodo : function(valPeriodo){
		jQuery.ajax({
				url: formato14A.urlCargaPeriodo+'&'+formato14A.formCommand.serialize(),
				type: 'post',
				dataType: 'json',
				success: function(data) {		
					dwr.util.removeAllOptions("periodoEnvio");
					dwr.util.addOptions("periodoEnvio", data,"codigoItem","descripcionItem");
					if( valPeriodo.length!='' ){
						dwr.util.setValue("periodoEnvio", valPeriodo);
					}
					formato14A.<portlet:namespace/>loadCargaFlagPeriodo();
				},error : function(){
					alert("Error de conexión.");
					formato14A.initBlockUI();
				}
		});
	},
	<portlet:namespace/>loadCargaFlagPeriodo : function() {
		jQuery.ajax({
			url: formato14A.urlCargaFlagPeriodo+'&'+formato14A.formCommand.serialize(),
				type: 'post',
				dataType: 'json',
				success: function(data) {				
					dwr.util.setValue("flagPeriodoEjecucion", data.flagPeriodoEjecucion);
					formato14A.recargarPeriodoEjecucion();
					formato14A.mostrarPeriodoEjecucion();
				},error : function(){
					alert("Error de conexión.");
					formato14A.initBlockUI();
				}
		});
	},
	mostrarPeriodoEjecucion : function(){
		if( formato14A.f_flagPeriodo.val()=='S' ){
			formato14A.divPeriodoEjecucion.show();  
		}else{
			formato14A.divPeriodoEjecucion.hide();  
		}
	},
	recargarPeriodoEjecucion : function(){
		var anoInicio;
		var anoFin;
		if( formato14A.f_periodoEnvio.val() != null ){
			anoInicio = formato14A.f_periodoEnvio.val().substring(0,4);
			anoFin = formato14A.f_periodoEnvio.val().substring(0,4);
			if( formato14A.f_flagPeriodo.val()=='S' ){
				$('#anioInicioVigencia').val(anoInicio);
				$('#anioFinVigencia').val(anoFin);
			}
		}
	},
	//FORMULARIOS DE VIEW Y EDICION
	verFormato : function(codEmpresa,anoPresentacion,mesPresentacion,anoIniVigencia,anoFinVigencia,etapa){	
		$.blockUI({ message: formato14A.mensajeObteniendoDatos });
		jQuery.ajax({
				url: formato14A.urlCrud+'&'+formato14A.formCommand.serialize(),
				type: 'post',
				dataType: 'json',
				data: {
				   <portlet:namespace />tipo: "GET",
				   <portlet:namespace />codEmpresa: codEmpresa,
				   <portlet:namespace />anoPresentacion: anoPresentacion,
				   <portlet:namespace />mesPresentacion: mesPresentacion,
				   <portlet:namespace />anoIniVigencia: anoIniVigencia,
				   <portlet:namespace />anoFinVigencia: anoFinVigencia,
				   <portlet:namespace />etapa: etapa
					},
				success: function(data) {				
					if (data.resultado == "OK"){
						formato14A.procesoEstado.val("UPDATE");
						formato14A.etapaEdit.val(etapa);
						formato14A.divFormato.show();
						formato14A.divHome.hide();
						dwr.util.removeAllOptions("periodoEnvio");
						dwr.util.addOptions("periodoEnvio", data.periodoEnvio,"codigoItem","descripcionItem");
						formato14A.FillEditformato(data.formato);
						formato14A.deshabilitarCamposView();
						formato14A.initBlockUI();
					}
					else{
						alert("Error al recuperar los datos del registro seleccionado");
						formato14A.initBlockUI();
					}
				},error : function(){
					alert("Error de conexión.");
					formato14A.initBlockUI();
				}
		});	
	},
	editarFormato : function(codEmpresa,anoPresentacion,mesPresentacion,anoIniVigencia,anoFinVigencia,etapa){	
		$.blockUI({ message: formato14A.mensajeObteniendoDatos });
		jQuery.ajax({
				url: formato14A.urlCrud+'&'+formato14A.formCommand.serialize(),
				type: 'post',
				dataType: 'json',
				data: {
				   <portlet:namespace />tipo: "GET",
				   <portlet:namespace />codEmpresa: codEmpresa,
				   <portlet:namespace />anoPresentacion: anoPresentacion,
				   <portlet:namespace />mesPresentacion: mesPresentacion,
				   <portlet:namespace />anoIniVigencia: anoIniVigencia,
				   <portlet:namespace />anoFinVigencia: anoFinVigencia,
				   <portlet:namespace />etapa: etapa
					},
				success: function(data) {				
					if (data.resultado == "OK"){
						formato14A.procesoEstado.val("UPDATE");
						formato14A.etapaEdit.val(etapa);
						formato14A.divFormato.show();
						formato14A.divHome.hide();
						dwr.util.removeAllOptions("periodoEnvio");
						dwr.util.addOptions("periodoEnvio", data.periodoEnvio,"codigoItem","descripcionItem");
						formato14A.FillEditformato(data.formato);
						formato14A.initBlockUI();
					}
					else{
						alert("Error al recuperar los datos del registro seleccionado");
						formato14A.initBlockUI();
					}
				},error : function(){
					alert("Error de conexión.");
					formato14A.initBlockUI();
				}
		});	
	},
	FillEditformato : function(row){
		
		formato14A.f_empresa.val(row.codEmpresa);
		//seteamos el concatenado
		formato14A.f_periodoEnvio.val(''+row.anoPresentacion+completarCerosIzq(row.mesPresentacion,2)+row.etapa);
		formato14A.f_flagPeriodo.val(row.flagPeriodoEjecucion);
		if( formato14A.f_flagPeriodo.val()=='S' ){
			$('#anioInicioVigencia').val(row.anoIniVigencia);
			$('#anioFinVigencia').val(row.anoFinVigencia);
			$('#anioInicioVigencia').attr("disabled",true);
			$('#anioFinVigencia').attr("disabled",true);
		}
		formato14A.etapaEdit.val(row.etapa);
		//RURAL
		//formato14A.f_sumEmpadDifR.val(row.);
		//alert(row.totalEmpadR);
		formato14A.f_totalEmpadR.val(row.totalEmpadR);
		formato14A.f_impEsqInvR.val(row.impEsqInvR);
		formato14A.f_impDeclJuradaR.val(row.impDeclJuradaR);
		formato14A.f_impFichaVerifR.val(row.impFichaVerifR);
		formato14A.f_repEsqInvR.val(row.repEsqInvR);
		formato14A.f_verifInfoR.val(row.verifInfoR);
		formato14A.f_elabArchBenefR.val(row.elabArchBenefR);
		formato14A.f_digitFichaR.val(row.digitFichaBenefR);
		formato14A.f_totalDifIniProgR.val(row.totalDifusionIniProgR);
		formato14A.f_impVolanteR.val(row.impVolanteR);
		formato14A.f_impAficheR.val(row.impAficheR);
		formato14A.f_repFolletoR.val(row.repFolletoBenefR);
		formato14A.f_spotPublicTvR.val(row.spotPublicTvR);
		formato14A.f_spotPublicRadioR.val(row.spotPublicRadioR);
		formato14A.f_nroBenefR.val(row.nroBenefEmpadR);
		formato14A.f_costoUnitEmpadR.val(row.costoUnitEmpadR);
		formato14A.f_totalAgentR.val(row.totalCostoAgentR);
		formato14A.f_promConvR.val(row.promConvAgentR);
		formato14A.f_regFirmaConvR.val(row.regFirmaConvR);
		formato14A.f_impBandR.val(row.impEntregaBandR);
		formato14A.f_nroAgentR.val(row.nroAgentR);
		formato14A.f_costoUnitAgentR.val(row.costoUnitAgentR);
		//PROVINCIA
		formato14A.f_totalEmpadP.val(row.totalEmpadP);
		formato14A.f_impEsqInvP.val(row.impEsqInvP);
		formato14A.f_impDeclJuradaP.val(row.impDeclJuradaP);
		formato14A.f_impFichaVerifP.val(row.impFichaVerifP);
		formato14A.f_repEsqInvP.val(row.repEsqInvP);
		formato14A.f_verifInfoP.val(row.verifInfoP);
		formato14A.f_elabArchBenefP.val(row.elabArchBenefP);
		formato14A.f_digitFichaP.val(row.digitFichaBenefP);
		formato14A.f_totalDifIniProgP.val(row.totalDifusionIniProgP);
		formato14A.f_impVolanteP.val(row.impVolanteP);
		formato14A.f_impAficheP.val(row.impAficheP);
		formato14A.f_repFolletoP.val(row.repFolletoBenefP);
		formato14A.f_spotPublicTvP.val(row.spotPublicTvP);
		formato14A.f_spotPublicRadioP.val(row.spotPublicRadioP);
		formato14A.f_nroBenefP.val(row.nroBenefEmpadP);
		formato14A.f_costoUnitEmpadP.val(row.costoUnitEmpadP);
		formato14A.f_totalAgentP.val(row.totalCostoAgentP);
		formato14A.f_promConvP.val(row.promConvAgentP);
		formato14A.f_regFirmaConvP.val(row.regFirmaConvP);
		formato14A.f_impBandP.val(row.impEntregaBandP);
		formato14A.f_nroAgentP.val(row.nroAgentP);
		formato14A.f_costoUnitAgentP.val(row.costoUnitAgentP);
		//LIMA
		formato14A.f_totalEmpadL.val(row.totalEmpadL);
		formato14A.f_impEsqInvL.val(row.impEsqInvL);
		formato14A.f_impDeclJuradaL.val(row.impDeclJuradaL);
		formato14A.f_impFichaVerifL.val(row.impFichaVerifL);
		formato14A.f_repEsqInvL.val(row.repEsqInvL);
		formato14A.f_verifInfoL.val(row.verifInfoL);
		formato14A.f_elabArchBenefL.val(row.elabArchBenefL);
		formato14A.f_digitFichaL.val(row.digitFichaBenefL);
		formato14A.f_totalDifIniProgL.val(row.totalDifusionIniProgL);
		formato14A.f_impVolanteL.val(row.impVolanteL);
		formato14A.f_impAficheL.val(row.impAficheL);
		formato14A.f_repFolletoL.val(row.repFolletoBenefL);
		formato14A.f_spotPublicTvL.val(row.spotPublicTvL);
		formato14A.f_spotPublicRadioL.val(row.spotPublicRadioL);
		formato14A.f_nroBenefL.val(row.nroBenefEmpadL);
		formato14A.f_costoUnitEmpadL.val(row.costoUnitEmpadL);
		formato14A.f_totalAgentL.val(row.totalCostoAgentL);
		formato14A.f_promConvL.val(row.promConvAgentL);
		formato14A.f_regFirmaConvL.val(row.regFirmaConvL);
		formato14A.f_impBandL.val(row.impEntregaBandL);
		formato14A.f_nroAgentL.val(row.nroAgentL);
		formato14A.f_costoUnitAgentL.val(row.costoUnitAgentL);
		
		formato14A.f_empresa.attr("disabled",true);
		formato14A.f_periodoEnvio.attr("disabled",true);
		//
		formato14A.calculoTotal();
		
		formato14A.soloNumerosEnteros();
		formato14A.soloNumerosDecimales();
		formato14A.formularioCompletarDecimales();
		//---$('#flagCarga').val('1');
		formato14A.mostrarPeriodoEjecucion();
	},
//////CRUD
	<portlet:namespace/>guardarFormato : function(){
		//---if (validarFormulario()){
			$.blockUI({ message: formato14A.mensajeGuardando });
			 jQuery.ajax({
				 url: formato14A.urlCrud+'&'+formato14A.formCommand.serialize(),
				type: 'post',
				dataType: 'json',
				data: {
					<portlet:namespace />tipo: formato14A.procesoEstado.val(),
					<portlet:namespace />codEmpresa: formato14A.f_empresa.val(),
					<portlet:namespace />periodoEnvio: formato14A.f_periodoEnvio.val(),
					<portlet:namespace />flagPeriodo: formato14A.f_flagPeriodo.val(),
					<portlet:namespace />anoInicioVigencia: $('#anioInicioVigencia').val(),
					<portlet:namespace />anoFinVigencia: $('#anioFinVigencia').val(),
					<portlet:namespace />etapa: formato14A.etapaEdit.val()/*,
					<portlet:namespace />nroEmpadR: $('#i_nroEmpad_r').val(),
					<portlet:namespace />costoUnitEmpadR: $('#i_costoUnitEmpad_r').val(),
					<portlet:namespace />nroAgentR: $('#i_nroAgentGlp_r').val(),
					<portlet:namespace />costoUnitAgentR: $('#i_costoUnitAgent_r').val(),
					<portlet:namespace />despPersonalR: $('#i_despPersonal_r').val(),
					<portlet:namespace />activExtraordR: $('#i_activExtraord_r').val(),
					//
					<portlet:namespace />nroEmpadP: $('#i_nroEmpad_p').val(),
					<portlet:namespace />costoUnitEmpadP: $('#i_costoUnitEmpad_p').val(),
					<portlet:namespace />nroAgentP: $('#i_nroAgentGlp_p').val(),
					<portlet:namespace />costoUnitAgentP: $('#i_costoUnitAgent_p').val(),
					<portlet:namespace />despPersonalP: $('#i_despPersonal_p').val(),
					<portlet:namespace />activExtraordP: $('#i_activExtraord_p').val(),
					//
					<portlet:namespace />nroEmpadL: $('#i_nroEmpad_l').val(),
					<portlet:namespace />costoUnitEmpadL: $('#i_costoUnitEmpad_l').val(),
					<portlet:namespace />nroAgentL: $('#i_nroAgentGlp_l').val(),
					<portlet:namespace />costoUnitAgentL: $('#i_costoUnitAgent_l').val(),
					<portlet:namespace />despPersonalL: $('#i_despPersonal_l').val(),
					<portlet:namespace />activExtraordL: $('#i_activExtraord_l').val()*/
					},
				success: function(data) {			
					if (data.resultado == "OK"){				
						var addhtml2='Datos guardados satisfactoriamente';
						formato14A.dialogMessageContent.html(addhtml2);
						formato14A.dialogMessage.dialog("open");
						//limpiar();		
						//---mostrarFormularioModificado();
						formato14A.initBlockUI();
					}
					else if(data.resultado == "Error"){				
						var addhtml2='Se produjo un error al guardar los datos: '+data.mensaje;
						formato14A.dialogMessageContent.html(addhtml2);
						formato14A.dialogMessage.dialog("open");
						//<portlet:namespace/>filtrar();
						//---mostrarUltimoFormato();
						formato14A.initBlockUI();
					}
				},error : function(){
					alert("Error de conexión.");
					formato14A.initBlockUI();
				}
			});
		   	//se deja el formulario activo
			formato14A.divFormato.hide();
			formato14A.divHome.show();
		//---}
		//
		 
		 
	},
	confirmarEliminar : function(cod_empresa,anoPresentacion,mesPresentacion,anoEjecucion,mesEjecucion,etapa){	
		var addhtml='¿Está seguro que desea eliminar el registro seleccionado?';
		formato14A.dialogConfirmContent.html(addhtml);
		formato14A.dialogConfirm.dialog("open");
		codEmpresa=cod_empresa;
		ano_Presentacion=anoPresentacion;
		mes_Presentacion=mesPresentacion;
		ano_Ejecucion=anoEjecucion;
		mes_Ejecucion=mesEjecucion;
		codEtapa=etapa;
	},
	eliminarFormato : function(codEmpresa,ano_Presentacion,mes_Presentacion,ano_Ejecucion,mes_Ejecucion,codEtapa){			
		$.blockUI({ message: formato14A.mensajeEliminando });
		jQuery.ajax({
			url: formato14A.urlCrud+'&'+formato14A.formCommand.serialize(),
			type: 'post',
			dataType: 'json',
			data: {
			   <portlet:namespace />tipo: "DELETE",
			   <portlet:namespace />codEmpresa: codEmpresa,
			   <portlet:namespace />anoPresentacion: ano_Presentacion,
			   <portlet:namespace />mesPresentacion: mes_Presentacion,
			   <portlet:namespace />anoEjecucion: ano_Ejecucion,
			   <portlet:namespace />mesEjecucion: mes_Ejecucion,
			   <portlet:namespace />etapa: codEtapa
				},
			success: function(data) {
				if (data.resultado == "OK"){
					var addhtml2='Registro eliminado con éxito';					
					formato14A.dialogMessageContent.html(addhtml2);
					formato14A.dialogMessage.dialog("open");
					formato14A.<portlet:namespace/>buscar();
					formato14A.initBlockUI();
				}
				else{
					alert("Error al eliminar el registro");
					formato14A.initBlockUI();
				}
			},error : function(){
				alert("Error de conexión.");
				formato14A.initBlockUI();
			}
		});
	},
	//
	blockUI : function(){
		$.blockUI({ message: '<h3><img src="/net-theme/images/img-net/loading_indicator.gif" /> Cargando </h3>' });
	},
	unblockUI : function(){
		$.unblockUI();
	},
	initBlockUI : function(){
		$(document).ajaxStop($.unblockUI); 		
	},
	initDialogs : function(){	
		formato14A.dialogMessage.dialog({
			modal: true,
			autoOpen: false,
			buttons: {
				Ok: function() {
					$( this ).dialog("close");
				}
			}
		});
		formato14A.dialogConfirm.dialog({
			modal: true,
			height: 200,
			width: 400,			
			autoOpen: false,
			buttons: {
				"Si": function() {
					//eliminarFormato(codEmpresa,ano_Presentacion,mes_Presentacion,ano_Ejecucion,mes_Ejecucion,codEtapa);
					$( this ).dialog("close");
				},
				"No": function() {				
					$( this ).dialog("close");
				}
			}
		});
		formato14A.dialogConfirmEnvio.dialog({
			modal: true,
			height: 200,
			width: 400,			
			autoOpen: false,
			buttons: {
				"Si": function() {
					//<portlet:namespace/>envioDefinitivo();
					$( this ).dialog("close");
				},
				"No": function() {				
					$( this ).dialog("close");
				}
			}
		});
		formato14A.dialogCarga.dialog({
			autoOpen: false,
			height: 240,
			width: 400,
			modal: true,
			buttons: {
				"Aceptar": function() {
					$( this ).dialog("close");					  				
				},
				"Cerrar": function() {
					$( this ).dialog("close");
				}
			},
			close: function() {
			}
		});
		formato14A.dialogError.dialog({
			modal: true,
			width: 700,
			autoOpen: false,
			buttons: {
				Ok: function() {
					$( this ).dialog("close");
				}
			}
		});
		formato14A.dialogObservacion.dialog({
			modal: true,
			width: 700,
			autoOpen: false,
			buttons: {
				Cerrar: function() {
					$( this ).dialog("close");
				}
			}
		});
	}
};
</script>