<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects />

<script type="text/javascript">
var formato14A= {
	tablaResultados:null,
	paginadoResultados:null,
	urlBusqueda: null,
	formBusqueda: null,
	botonBuscar:null,
	botonNuevo:null,
	botonRegresar:null,
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
	//divs
	divHome:null,
	divFormato:null,
	//formulario
	f_empresa:null,
	//f_empresa:null,
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
		this.urlBusqueda='<portlet:resourceURL id="busqueda" />';
		this.formBusqueda=$('#formato14ACBean');
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
		//divs
		this.divHome=$("#<portlet:namespace/>div_home");
		this.divFormato=$("#<portlet:namespace/>div_formato");
		//formulario
		this.f_empresa=$('#codigoEmpresa');
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
		//eventos
		formato14A.botonNuevo.click(function() {formato14A.<portlet:namespace/>crearFormato();});
		formato14A.botonBuscar.click(function() {formato14A.buscar();});
		formato14A.botonRegresar.click(function() {formato14A.<portlet:namespace/>regresar();});
		//eventos por defecto
		formato14A.botonBuscar.trigger('click');
		formato14A.initBlockUI();
		//$('input[type=text].target').on('change', function(){
		$('input[type=text]').on('change', function(){
			//$(".target").
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
	      			view = "<a href='#'><img border='0' title='View' src='/net-theme/images/img-net/file.png'  align='center' onclick=\"verFormato('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoIniVigencia+"','"+ret.anoFinVigencia+"','"+ret.etapa+"');\" /></a> ";
	      			edit = "<a href='#'><img border='0' title='Editar' src='/net-theme/images/img-net/edit.png'  align='center' onclick=\"editarFormato('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoIniVigencia+"','"+ret.anoFinVigencia+"','"+ret.etapa+"');\" /></a> ";
	      			elem = "<a href='#'><img border='0' title='Eliminar' src='/net-theme/images/img-net/elim.png'  align='center' onclick=\"confirmarEliminar('"+ret.codEmpresa+"','"+ret.anoPresentacion+"','"+ret.mesPresentacion+"','"+ret.anoIniVigencia+"','"+ret.anoFinVigencia+"','"+ret.etapa+"');\" /></a> ";              			
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
		//alert('entro a buscar');
		if(formato14A.validarBusqueda()){
			formato14A.blockUI();
			jQuery.ajax({			
					url: formato14A.urlBusqueda+'&'+formato14A.formBusqueda.serialize(),
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
		//alert('entro a crear');
		formato14A.inicializarFormulario();
		//data_funcion = [];
		//$('#Estado').val('SAVE');
		//$("#etapaEdit").val("");
		formato14A.divFormato.show();
		formato14A.divHome.hide();
		//$('#flagCarga').val('0');
		//
		//if( $('#flagPeriodoEjecucion').val()=='S' ){
		//	 $("#i_anioejecuc").val($("#anioDesdeSes").val());
		//	 $("#s_mes_ejecuc").val(parseInt($("#mesDesdeSes").val()));
		//}
		//$('#<portlet:namespace/>validacionFormato').css('display','none');
		//cargarPeriodoYCostos('','');
	},
	<portlet:namespace/>regresar : function(){
		//$("#Estado").val("");
		//$("#etapaEdit").val("");
		formato14A.divFormato.hide();
		formato14A.divHome.show();		
		//
		//removerDeshabilitados();
		//se visualizan los componentes escondidos
		/*$('#<portlet:namespace/>reportePdf').css('display','none');
		$('#<portlet:namespace/>reporteExcel').css('display','none');
		$('#<portlet:namespace/>guardarFormato').css('display','');
		$('#panelCargaArchivos').css('display','');
		$('#<portlet:namespace/>validacionFormato').css('display','');
		$('#<portlet:namespace/>envioDefinitivo').css('display','');*/
		formato14A.botonBuscar.trigger('click');
		//<portlet:namespace/>buscar();
	},
	inicializarFormulario : function(){
		formato14A.f_empresa.val('');
		
		/*$('#s_periodoenvio_present').val('');
		if( $('#flagPeriodoEjecucion').val()=='S' ){
			$('#i_anioejecuc').val('');
			$('#s_mes_ejecuc').val('');
			$('#i_anioejecuc').attr("disabled",false);
			$('#s_mes_ejecuc').attr("disabled",false);
		}*/
		
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
		
		//realizarCalculoCampos();
		//quitamos los componentes deshabilitados
		//$('#s_empresa').attr("disabled",false);
		//$('#s_periodoenvio_present').attr("disabled",false);
		//
		//deshabilitarCampos();
		//soloNumerosEnteros();
	},
	calculoTotal : function(){
		totalEmpadronamientoRural();
		totalEmpadronamientoProvincia();
		totalEmpadronamientoLima();
		//
		totalRedAgentesRural();
		totalRedAgentesProvincia();
		totalRedAgentesLima();
		//
		totalImportes();
		totalGeneral();
		//completar nulos
		$('#i_nroEmpad_r').attr("onkeypress","return soloNumerosDecimales(event, 1, 'i_nroEmpad_r',7,0)");
		$('#i_nroEmpad_p').attr("onkeypress","return soloNumerosDecimales(event, 1, 'i_nroEmpad_p',7,0)");
		$('#i_nroEmpad_l').attr("onkeypress","return soloNumerosDecimales(event, 1, 'i_nroEmpad_l',7,0)");
		$('#i_nroAgentGlp_r').attr("onkeypress","return soloNumerosDecimales(event, 1, 'i_nroAgentGlp_r',7,0)");
		$('#i_nroAgentGlp_p').attr("onkeypress","return soloNumerosDecimales(event, 1, 'i_nroAgentGlp_p',7,0)");
		$('#i_nroAgentGlp_l').attr("onkeypress","return soloNumerosDecimales(event, 1, 'i_nroAgentGlp_l',7,0)");
		completarBlanco('i_nroEmpad_r');
		completarBlanco('i_nroEmpad_p');
		completarBlanco('i_nroEmpad_l');
		completarBlanco('i_nroAgentGlp_r');
		completarBlanco('i_nroAgentGlp_p');
		completarBlanco('i_nroAgentGlp_l');
		//completar decimales
		formularioCompletarDecimales();

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