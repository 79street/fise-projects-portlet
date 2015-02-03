package gob.osinergmin.fise.gart.json;

import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.FiseFormato14AC;
import gob.osinergmin.fise.domain.FiseFormato14AD;
import gob.osinergmin.fise.util.FormatoUtil;

import java.math.BigDecimal;

import org.json.JSONException;
import org.json.JSONObject;

public class Formato14AGartJSON {
	
	private String codEmpresa;
	private String descEmpresa;
	private long anoPresentacion = 0;
	private long mesPresentacion = 0;
	private long anoIniVigencia = 0;
	private long anoFinVigencia = 0;
	private String etapa;
	private String descMesPresentacion;
	private String grupoInfo;
	private String estado;
	private String anoPres;
	private String mesPres;
	private String anoIniVig;
	private String anoFinVig;
	//
	private String anioDesde;
	private String mesDesde;
	private String anioHasta;
	private String mesHasta;
	private String codEtapa;
	//
	private String mensajeInfo;
	private String mensajeError;
	//
	private String flag;//flag para controlar mostrar el formulario de ingreso cuando hay un error en carga de formulario excel o texto

	private String flagOperacion;//Cerrado, abierto, enviado
	
	public JSONObject asJSONObject(FiseFormato14AC fiseFormato14AC, String flagPeriodoEjecucion, String flagOperacion) throws JSONException{
		
		JSONObject jsonObj = new JSONObject();
		
		this.descEmpresa=fiseFormato14AC.getDescEmpresa();
		this.anoPresentacion=fiseFormato14AC.getId().getAnoPresentacion();
		this.mesPresentacion=fiseFormato14AC.getId().getMesPresentacion();
		this.anoIniVigencia=fiseFormato14AC.getId().getAnoInicioVigencia();
		this.anoFinVigencia=fiseFormato14AC.getId().getAnoFinVigencia();
		this.etapa=fiseFormato14AC.getId().getEtapa();
		this.descMesPresentacion=fiseFormato14AC.getDescMesPresentacion();
		this.grupoInfo=fiseFormato14AC.getDescGrupoInformacion();
		this.estado=fiseFormato14AC.getDescEstado();
		
		jsonObj.put("codEmpresa", fiseFormato14AC.getId().getCodEmpresa());
		//formar flag de verificado
		jsonObj.put("flagPeriodoEjecucion", flagPeriodoEjecucion);
		jsonObj.put("descEmpresa", fiseFormato14AC.getDescEmpresa());
		jsonObj.put("anoPresentacion", fiseFormato14AC.getId().getAnoPresentacion());
		jsonObj.put("mesPresentacion", fiseFormato14AC.getId().getMesPresentacion());
		jsonObj.put("anoIniVigencia", fiseFormato14AC.getId().getAnoInicioVigencia());
		jsonObj.put("anoFinVigencia", fiseFormato14AC.getId().getAnoFinVigencia());
		jsonObj.put("etapa", fiseFormato14AC.getId().getEtapa());
		jsonObj.put("descMesPresentacion", fiseFormato14AC.getDescMesPresentacion());
		
		if( FiseConstants.BLANCO.equals(flagOperacion) ){
			//grupo informacion y estado
				if(fiseFormato14AC.getFiseGrupoInformacion()!=null && fiseFormato14AC.getFiseGrupoInformacion().getDescripcion()!=null){
					jsonObj.put("grupoInfo",fiseFormato14AC.getFiseGrupoInformacion().getDescripcion());
				}else{
					jsonObj.put("grupoInfo",FiseConstants.BLANCO);
				}
				/*if(fiseFormato14AC.getFechaEnvioDefinitivo()!=null){
					jsonObj.put("estado",FiseConstants.ESTADO_FECHAENVIO_ENVIADO);
				}else{
					jsonObj.put("estado",FiseConstants.ESTADO_FECHAENVIO_POR_ENVIAR);
				}*/
				jsonObj.put("estado", FormatoUtil.cambiaTextoAMinusculas(flagOperacion, 0));
		}else{
			jsonObj.put("grupoInfo", fiseFormato14AC.getDescGrupoInformacion());
			jsonObj.put("estado", fiseFormato14AC.getDescEstado());
		}

		jsonObj.put("flagOperacion", flagOperacion);
		
		//valores de cabecera y detalle
		//averiguar si se tiene que hacer este calculo, al parecer es innecesario
		
		//Rural
		//BigDecimal sumaTotalEmpadR = new BigDecimal(0);
		//
		BigDecimal totalEmpadR = new BigDecimal(0);
		BigDecimal impEsqInvR = new BigDecimal(0);
		BigDecimal impDeclJuradaR = new BigDecimal(0);
		BigDecimal impFichaVerifR = new BigDecimal(0);
		BigDecimal repEsqInvR = new BigDecimal(0);
		BigDecimal verifInfoR = new BigDecimal(0);
		BigDecimal elabArchBenefR = new BigDecimal(0);
		BigDecimal digitFichaBenefR = new BigDecimal(0);
		
		BigDecimal totalDifusionIniProgR = new BigDecimal(0);
		BigDecimal impVolanteR = new BigDecimal(0);
		BigDecimal impAficheR = new BigDecimal(0);
		BigDecimal repFolletoBenefR = new BigDecimal(0);
		BigDecimal spotPublicTvR = new BigDecimal(0);
		BigDecimal spotPublicRadioR = new BigDecimal(0);
		Long nroBenefEmpadR = new Long(0);
		BigDecimal costoUnitEmpadR = new BigDecimal(0);
		//
		BigDecimal totalCostoAgentR = new BigDecimal(0);
		BigDecimal promConvAgentR = new BigDecimal(0);
		BigDecimal regFirmaConvR = new BigDecimal(0);
		BigDecimal impEntregaBandR = new BigDecimal(0);
		Long nroAgentR = new Long(0);
		BigDecimal costoUnitAgentR = new BigDecimal(0);
		//Provincia
		//BigDecimal sumaTotalEmpadP = new BigDecimal(0);
		//
		BigDecimal totalEmpadP = new BigDecimal(0);
		BigDecimal impEsqInvP = new BigDecimal(0);
		BigDecimal impDeclJuradaP = new BigDecimal(0);
		BigDecimal impFichaVerifP = new BigDecimal(0);
		BigDecimal repEsqInvP = new BigDecimal(0);
		BigDecimal verifInfoP = new BigDecimal(0);
		BigDecimal elabArchBenefP = new BigDecimal(0);
		BigDecimal digitFichaBenefP = new BigDecimal(0);
		
		BigDecimal totalDifusionIniProgP = new BigDecimal(0);
		BigDecimal impVolanteP = new BigDecimal(0);
		BigDecimal impAficheP = new BigDecimal(0);
		BigDecimal repFolletoBenefP = new BigDecimal(0);
		BigDecimal spotPublicTvP = new BigDecimal(0);
		BigDecimal spotPublicRadioP = new BigDecimal(0);
		Long nroBenefEmpadP = new Long(0);
		BigDecimal costoUnitEmpadP = new BigDecimal(0);
		//
		BigDecimal totalCostoAgentP = new BigDecimal(0);
		BigDecimal promConvAgentP = new BigDecimal(0);
		BigDecimal regFirmaConvP = new BigDecimal(0);
		BigDecimal impEntregaBandP = new BigDecimal(0);
		Long nroAgentP = new Long(0);
		BigDecimal costoUnitAgentP = new BigDecimal(0);
		//Lima
		//BigDecimal sumaTotalEmpadL = new BigDecimal(0);
		//
		BigDecimal totalEmpadL = new BigDecimal(0);
		BigDecimal impEsqInvL = new BigDecimal(0);
		BigDecimal impDeclJuradaL = new BigDecimal(0);
		BigDecimal impFichaVerifL = new BigDecimal(0);
		BigDecimal repEsqInvL = new BigDecimal(0);
		BigDecimal verifInfoL = new BigDecimal(0);
		BigDecimal elabArchBenefL = new BigDecimal(0);
		BigDecimal digitFichaBenefL = new BigDecimal(0);
		
		BigDecimal totalDifusionIniProgL = new BigDecimal(0);
		BigDecimal impVolanteL = new BigDecimal(0);
		BigDecimal impAficheL = new BigDecimal(0);
		BigDecimal repFolletoBenefL = new BigDecimal(0);
		BigDecimal spotPublicTvL = new BigDecimal(0);
		BigDecimal spotPublicRadioL = new BigDecimal(0);
		Long nroBenefEmpadL = new Long(0);
		BigDecimal costoUnitEmpadL = new BigDecimal(0);
		//
		BigDecimal totalCostoAgentL = new BigDecimal(0);
		BigDecimal promConvAgentL = new BigDecimal(0);
		BigDecimal regFirmaConvL = new BigDecimal(0);
		BigDecimal impEntregaBandL = new BigDecimal(0);
		Long nroAgentL = new Long(0);
		BigDecimal costoUnitAgentL = new BigDecimal(0);

		
		if( fiseFormato14AC.getFiseFormato14ADs() != null && !fiseFormato14AC.getFiseFormato14ADs().isEmpty() ){
			for (FiseFormato14AD detalle : fiseFormato14AC.getFiseFormato14ADs()) {
				if( detalle.getId().getIdZonaBenef()==FiseConstants.ZONABENEF_RURAL_COD ){//RURAL
					//sumaTotalEmpadR = new BigDecimal(0);
					//
					totalEmpadR = detalle.getTotalEmpadronamiento();
					impEsqInvR = detalle.getImpresionEsquelaInvitacion();
					impDeclJuradaR = detalle.getImpresionDeclaracionJurada();
					impFichaVerifR = detalle.getImpresionFichasVerificacion();
					repEsqInvR = detalle.getRepartoEsquelaInvitacion();
					verifInfoR = detalle.getVerificacionInformacion();
					elabArchBenefR = detalle.getElaboracionArchivoBenef();
					digitFichaBenefR = detalle.getDigitacionFichaBenef();
					//
					totalDifusionIniProgR = detalle.getTotalDifusionInicioPrgFise();
					impVolanteR = detalle.getImpresionVolantes();
					impAficheR = detalle.getImpresionAfiches();
					repFolletoBenefR = detalle.getRepartoFolletoPotenciaBenef();
					spotPublicTvR = detalle.getSpotPublicitarioTv();
					spotPublicRadioR = detalle.getSpotPublicitarioRadio();
					nroBenefEmpadR = detalle.getNumeroBenefEmpadroMesDic();
					costoUnitEmpadR = detalle.getCostoUnitarioEmpadronamiento();
					//
					totalCostoAgentR = detalle.getTotalCostoGestionRedAgGlp();
					promConvAgentR = detalle.getPromocionConvenioAgAutGlp();
					regFirmaConvR = detalle.getRegistroFirmaConvAgAutGlp();
					impEntregaBandR = detalle.getImpresionEntregaBanderola();
					nroAgentR = detalle.getNumeroAgentes();
					costoUnitAgentR = detalle.getCostoUntitarioAgenteGlp();
				}else if( detalle.getId().getIdZonaBenef()==FiseConstants.ZONABENEF_PROVINCIA_COD ){//PROVINCIA
					//sumaTotalEmpadR = new BigDecimal(0);
					//
					totalEmpadP = detalle.getTotalEmpadronamiento();
					impEsqInvP = detalle.getImpresionEsquelaInvitacion();
					impDeclJuradaP = detalle.getImpresionDeclaracionJurada();
					impFichaVerifP = detalle.getImpresionFichasVerificacion();
					repEsqInvP = detalle.getRepartoEsquelaInvitacion();
					verifInfoP = detalle.getVerificacionInformacion();
					elabArchBenefP = detalle.getElaboracionArchivoBenef();
					digitFichaBenefP = detalle.getDigitacionFichaBenef();
					//
					totalDifusionIniProgP = detalle.getTotalDifusionInicioPrgFise();
					impVolanteP = detalle.getImpresionVolantes();
					impAficheP = detalle.getImpresionAfiches();
					repFolletoBenefP = detalle.getRepartoFolletoPotenciaBenef();
					spotPublicTvP = detalle.getSpotPublicitarioTv();
					spotPublicRadioP = detalle.getSpotPublicitarioRadio();
					nroBenefEmpadP = detalle.getNumeroBenefEmpadroMesDic();
					costoUnitEmpadP = detalle.getCostoUnitarioEmpadronamiento();
					//
					totalCostoAgentP = detalle.getTotalCostoGestionRedAgGlp();
					promConvAgentP = detalle.getPromocionConvenioAgAutGlp();
					regFirmaConvP = detalle.getRegistroFirmaConvAgAutGlp();
					impEntregaBandP = detalle.getImpresionEntregaBanderola();
					nroAgentP = detalle.getNumeroAgentes();
					costoUnitAgentP = detalle.getCostoUntitarioAgenteGlp();
				}else if( detalle.getId().getIdZonaBenef()==FiseConstants.ZONABENEF_LIMA_COD ){//LIMA
					//sumaTotalEmpadR = new BigDecimal(0);
					//
					totalEmpadL = detalle.getTotalEmpadronamiento();
					impEsqInvL = detalle.getImpresionEsquelaInvitacion();
					impDeclJuradaL = detalle.getImpresionDeclaracionJurada();
					impFichaVerifL = detalle.getImpresionFichasVerificacion();
					repEsqInvL = detalle.getRepartoEsquelaInvitacion();
					verifInfoL = detalle.getVerificacionInformacion();
					elabArchBenefL = detalle.getElaboracionArchivoBenef();
					digitFichaBenefL = detalle.getDigitacionFichaBenef();
					//
					totalDifusionIniProgL = detalle.getTotalDifusionInicioPrgFise();
					impVolanteL = detalle.getImpresionVolantes();
					impAficheL = detalle.getImpresionAfiches();
					repFolletoBenefL = detalle.getRepartoFolletoPotenciaBenef();
					spotPublicTvL = detalle.getSpotPublicitarioTv();
					spotPublicRadioL = detalle.getSpotPublicitarioRadio();
					nroBenefEmpadL = detalle.getNumeroBenefEmpadroMesDic();
					costoUnitEmpadL = detalle.getCostoUnitarioEmpadronamiento();
					//
					totalCostoAgentL = detalle.getTotalCostoGestionRedAgGlp();
					promConvAgentL = detalle.getPromocionConvenioAgAutGlp();
					regFirmaConvL = detalle.getRegistroFirmaConvAgAutGlp();
					impEntregaBandL = detalle.getImpresionEntregaBanderola();
					nroAgentL = detalle.getNumeroAgentes();
					costoUnitAgentL = detalle.getCostoUntitarioAgenteGlp();
				}
			}
		}

		jsonObj.put("totalEmpadR", totalEmpadR);
		jsonObj.put("impEsqInvR", impEsqInvR);
		jsonObj.put("impDeclJuradaR", impDeclJuradaR);
		jsonObj.put("impFichaVerifR", impFichaVerifR);
		jsonObj.put("repEsqInvR", repEsqInvR);
		jsonObj.put("verifInfoR", verifInfoR);
		jsonObj.put("elabArchBenefR", elabArchBenefR);
		jsonObj.put("digitFichaBenefR", digitFichaBenefR);
		jsonObj.put("totalDifusionIniProgR", totalDifusionIniProgR);
		jsonObj.put("impVolanteR", impVolanteR);
		jsonObj.put("impAficheR", impAficheR);
		jsonObj.put("repFolletoBenefR", repFolletoBenefR);
		jsonObj.put("spotPublicTvR", spotPublicTvR);
		jsonObj.put("spotPublicRadioR", spotPublicRadioR);
		jsonObj.put("nroBenefEmpadR", nroBenefEmpadR);
		jsonObj.put("costoUnitEmpadR", costoUnitEmpadR);
		jsonObj.put("totalCostoAgentR", totalCostoAgentR);
		jsonObj.put("promConvAgentR", promConvAgentR);
		jsonObj.put("regFirmaConvR", regFirmaConvR);
		jsonObj.put("impEntregaBandR", impEntregaBandR);
		jsonObj.put("nroAgentR", nroAgentR);
		jsonObj.put("costoUnitAgentR", costoUnitAgentR);
		//
		jsonObj.put("totalEmpadP", totalEmpadP);
		jsonObj.put("impEsqInvP", impEsqInvP);
		jsonObj.put("impDeclJuradaP", impDeclJuradaP);
		jsonObj.put("impFichaVerifP", impFichaVerifP);
		jsonObj.put("repEsqInvP", repEsqInvP);
		jsonObj.put("verifInfoP", verifInfoP);
		jsonObj.put("elabArchBenefP", elabArchBenefP);
		jsonObj.put("digitFichaBenefP", digitFichaBenefP);
		jsonObj.put("totalDifusionIniProgP", totalDifusionIniProgP);
		jsonObj.put("impVolanteP", impVolanteP);
		jsonObj.put("impAficheP", impAficheP);
		jsonObj.put("repFolletoBenefP", repFolletoBenefP);
		jsonObj.put("spotPublicTvP", spotPublicTvP);
		jsonObj.put("spotPublicRadioP", spotPublicRadioP);
		jsonObj.put("nroBenefEmpadP", nroBenefEmpadP);
		jsonObj.put("costoUnitEmpadP", costoUnitEmpadP);
		jsonObj.put("totalCostoAgentP", totalCostoAgentP);
		jsonObj.put("promConvAgentP", promConvAgentP);
		jsonObj.put("regFirmaConvP", regFirmaConvP);
		jsonObj.put("impEntregaBandP", impEntregaBandP);
		jsonObj.put("nroAgentP", nroAgentP);
		jsonObj.put("costoUnitAgentP", costoUnitAgentP);
		//
		jsonObj.put("totalEmpadL", totalEmpadL);
		jsonObj.put("impEsqInvL", impEsqInvL);
		jsonObj.put("impDeclJuradaL", impDeclJuradaL);
		jsonObj.put("impFichaVerifL", impFichaVerifL);
		jsonObj.put("repEsqInvL", repEsqInvL);
		jsonObj.put("verifInfoL", verifInfoL);
		jsonObj.put("elabArchBenefL", elabArchBenefL);
		jsonObj.put("digitFichaBenefL", digitFichaBenefL);
		jsonObj.put("totalDifusionIniProgL", totalDifusionIniProgL);
		jsonObj.put("impVolanteL", impVolanteL);
		jsonObj.put("impAficheL", impAficheL);
		jsonObj.put("repFolletoBenefL", repFolletoBenefL);
		jsonObj.put("spotPublicTvL", spotPublicTvL);
		jsonObj.put("spotPublicRadioL", spotPublicRadioL);
		jsonObj.put("nroBenefEmpadL", nroBenefEmpadL);
		jsonObj.put("costoUnitEmpadL", costoUnitEmpadL);
		jsonObj.put("totalCostoAgentL", totalCostoAgentL);
		jsonObj.put("promConvAgentL", promConvAgentL);
		jsonObj.put("regFirmaConvL", regFirmaConvL);
		jsonObj.put("impEntregaBandL", impEntregaBandL);
		jsonObj.put("nroAgentL", nroAgentL);
		jsonObj.put("costoUnitAgentL", costoUnitAgentL);
		//

		return jsonObj;
	}

	public String getCodEmpresa() {
		return codEmpresa;
	}

	public void setCodEmpresa(String codEmpresa) {
		this.codEmpresa = codEmpresa;
	}

	public String getDescEmpresa() {
		return descEmpresa;
	}

	public void setDescEmpresa(String descEmpresa) {
		this.descEmpresa = descEmpresa;
	}

	public long getAnoPresentacion() {
		return anoPresentacion;
	}

	public void setAnoPresentacion(long anoPresentacion) {
		this.anoPresentacion = anoPresentacion;
	}

	public long getMesPresentacion() {
		return mesPresentacion;
	}

	public void setMesPresentacion(long mesPresentacion) {
		this.mesPresentacion = mesPresentacion;
	}

	public long getAnoIniVigencia() {
		return anoIniVigencia;
	}

	public void setAnoIniVigencia(long anoIniVigencia) {
		this.anoIniVigencia = anoIniVigencia;
	}

	public long getAnoFinVigencia() {
		return anoFinVigencia;
	}

	public void setAnoFinVigencia(long anoFinVigencia) {
		this.anoFinVigencia = anoFinVigencia;
	}

	public String getEtapa() {
		return etapa;
	}

	public void setEtapa(String etapa) {
		this.etapa = etapa;
	}

	public String getDescMesPresentacion() {
		return descMesPresentacion;
	}

	public void setDescMesPresentacion(String descMesPresentacion) {
		this.descMesPresentacion = descMesPresentacion;
	}

	public String getGrupoInfo() {
		return grupoInfo;
	}

	public void setGrupoInfo(String grupoInfo) {
		this.grupoInfo = grupoInfo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getAnoPres() {
		return anoPres;
	}

	public void setAnoPres(String anoPres) {
		this.anoPres = anoPres;
	}

	public String getMesPres() {
		return mesPres;
	}

	public void setMesPres(String mesPres) {
		this.mesPres = mesPres;
	}

	public String getAnoIniVig() {
		return anoIniVig;
	}

	public void setAnoIniVig(String anoIniVig) {
		this.anoIniVig = anoIniVig;
	}

	public String getAnoFinVig() {
		return anoFinVig;
	}

	public void setAnoFinVig(String anoFinVig) {
		this.anoFinVig = anoFinVig;
	}

	public String getAnioDesde() {
		return anioDesde;
	}

	public void setAnioDesde(String anioDesde) {
		this.anioDesde = anioDesde;
	}

	public String getMesDesde() {
		return mesDesde;
	}

	public void setMesDesde(String mesDesde) {
		this.mesDesde = mesDesde;
	}

	public String getAnioHasta() {
		return anioHasta;
	}

	public void setAnioHasta(String anioHasta) {
		this.anioHasta = anioHasta;
	}

	public String getMesHasta() {
		return mesHasta;
	}

	public void setMesHasta(String mesHasta) {
		this.mesHasta = mesHasta;
	}

	public String getCodEtapa() {
		return codEtapa;
	}

	public void setCodEtapa(String codEtapa) {
		this.codEtapa = codEtapa;
	}

	public String getMensajeInfo() {
		return mensajeInfo;
	}

	public void setMensajeInfo(String mensajeInfo) {
		this.mensajeInfo = mensajeInfo;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getFlagOperacion() {
		return flagOperacion;
	}

	public void setFlagOperacion(String flagOperacion) {
		this.flagOperacion = flagOperacion;
	}

	
	
}
