package gob.osinergmin.fise.gart.json;

import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.FiseFormato14BC;
import gob.osinergmin.fise.domain.FiseFormato14BD;
import gob.osinergmin.fise.util.FormatoUtil;

import java.math.BigDecimal;

import org.json.JSONException;
import org.json.JSONObject;

public class Formato14BGartJSON {
	
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
	
	public JSONObject asJSONObject(FiseFormato14BC fiseFormato14BC, String flagPeriodoEjecucion, String flagOperacion) throws JSONException{
		
		JSONObject jsonObj = new JSONObject();
		
		this.descEmpresa=fiseFormato14BC.getDescEmpresa();
		this.anoPresentacion=fiseFormato14BC.getId().getAnoPresentacion();
		this.mesPresentacion=fiseFormato14BC.getId().getMesPresentacion();
		this.anoIniVigencia=fiseFormato14BC.getId().getAnoInicioVigencia();
		this.anoFinVigencia=fiseFormato14BC.getId().getAnoFinVigencia();
		this.etapa=fiseFormato14BC.getId().getEtapa();
		this.descMesPresentacion=fiseFormato14BC.getDescMesPresentacion();
		this.grupoInfo=fiseFormato14BC.getDescGrupoInformacion();
		this.estado=fiseFormato14BC.getDescEstado();

		
		
		this.estado=fiseFormato14BC.getDescEstado();
		
		jsonObj.put("codEmpresa", fiseFormato14BC.getId().getCodEmpresa());
		//formar flag de verificado
		jsonObj.put("flagPeriodoEjecucion", flagPeriodoEjecucion);
		jsonObj.put("descEmpresa", fiseFormato14BC.getDescEmpresa());
		jsonObj.put("anoPresentacion", fiseFormato14BC.getId().getAnoPresentacion());
		jsonObj.put("mesPresentacion", fiseFormato14BC.getId().getMesPresentacion());
		jsonObj.put("anoIniVigencia", fiseFormato14BC.getId().getAnoInicioVigencia());
		jsonObj.put("anoFinVigencia", fiseFormato14BC.getId().getAnoFinVigencia());
		jsonObj.put("etapa", fiseFormato14BC.getId().getEtapa());
		jsonObj.put("descMesPresentacion", fiseFormato14BC.getDescMesPresentacion());

//		if( FiseConstants.BLANCO.equals(flagOperacion) ){
//			//grupo informacion y estado
//				if(fiseFormato14BC.getFiseGrupoInformacion()!=null && fiseFormato14BC.getFiseGrupoInformacion().getDescripcion()!=null){
//					jsonObj.put("grupoInfo",fiseFormato14BC.getFiseGrupoInformacion().getDescripcion());
//				}else{
//					jsonObj.put("grupoInfo",FiseConstants.BLANCO);
//				}
//				/*if(fiseFormato14BC.getFechaEnvioDefinitivo()!=null){
//					jsonObj.put("estado",FiseConstants.ESTADO_FECHAENVIO_ENVIADO);
//				}else{
//					jsonObj.put("estado",FiseConstants.ESTADO_FECHAENVIO_POR_ENVIAR);
//				}*/
//				jsonObj.put("estado", FormatoUtil.cambiaTextoAMinusculas(flagOperacion, 0));
//		}else{
//			jsonObj.put("grupoInfo", fiseFormato14BC.getDescGrupoInformacion());
//			jsonObj.put("estado", fiseFormato14BC.getDescEstado());
//		}
		
		if(fiseFormato14BC.getFiseGrupoInformacion()!=null && fiseFormato14BC.getFiseGrupoInformacion().getDescripcion()!=null){
			jsonObj.put("grupoInfo",fiseFormato14BC.getFiseGrupoInformacion().getDescripcion());
		}else{
			jsonObj.put("grupoInfo",FiseConstants.BLANCO);
		}
		jsonObj.put("estado", FormatoUtil.cambiaTextoAMinusculas(flagOperacion, 0));
		
		jsonObj.put("flagOperacion", flagOperacion);
		
		//valores de cabecera y detalle
		//averiguar si se tiene que hacer este calculo, al parecer es innecesario
		
		//Rural
		BigDecimal impValEdeR = new BigDecimal(0);
		BigDecimal impValNoEdeR = new BigDecimal(0);
		BigDecimal costoTotalImpR = new BigDecimal(0);
		Long nroValesImpR = new Long(0);
		BigDecimal costoUnitImpValesR = new BigDecimal(0);
		BigDecimal costoTotalValR = new BigDecimal(0);
		Long nroValesReptR = new Long(0);
		BigDecimal costoUnitReptValesR = new BigDecimal(0);
		BigDecimal costoTotalValOficR = new BigDecimal(0);
		Long nroValesEntrR = new Long(0);
		BigDecimal costoUnitEntrValesR = new BigDecimal(0);
		BigDecimal costoEnvPadronR = new BigDecimal(0);
		Long nroValesFisR = new Long(0);
		BigDecimal costoUnitLiqR = new BigDecimal(0);
		BigDecimal costoUnitDigitR = new BigDecimal(0);
		BigDecimal costoAtenSolicR = new BigDecimal(0);
		BigDecimal costoAtenConsR = new BigDecimal(0);
		BigDecimal costoTotalAtenR = new BigDecimal(0);
		Long nroTotalAtenR = new Long(0);
		BigDecimal costoUnitAtenR = new BigDecimal(0);
		BigDecimal costoPersonalR = new BigDecimal(0);
		BigDecimal capacAgentR = new BigDecimal(0);
		BigDecimal utilMatOficR = new BigDecimal(0);
		BigDecimal costoTotalGestR = new BigDecimal(0);
		//Provincia
		BigDecimal impValEdeP = new BigDecimal(0);
		BigDecimal impValNoEdeP = new BigDecimal(0);
		BigDecimal costoTotalImpP = new BigDecimal(0);
		Long nroValesImpP = new Long(0);
		BigDecimal costoUnitImpValesP = new BigDecimal(0);
		BigDecimal costoTotalValP = new BigDecimal(0);
		Long nroValesReptP = new Long(0);
		BigDecimal costoUnitReptValesP = new BigDecimal(0);
		BigDecimal costoTotalValOficP = new BigDecimal(0);
		Long nroValesEntrP = new Long(0);
		BigDecimal costoUnitEntrValesP = new BigDecimal(0);
		BigDecimal costoEnvPadronP = new BigDecimal(0);
		Long nroValesFisP = new Long(0);
		BigDecimal costoUnitLiqP = new BigDecimal(0);
		BigDecimal costoUnitDigitP = new BigDecimal(0);
		BigDecimal costoAtenSolicP = new BigDecimal(0);
		BigDecimal costoAtenConsP = new BigDecimal(0);
		BigDecimal costoTotalAtenP = new BigDecimal(0);
		Long nroTotalAtenP = new Long(0);
		BigDecimal costoUnitAtenP = new BigDecimal(0);
		BigDecimal costoPersonalP = new BigDecimal(0);
		BigDecimal capacAgentP = new BigDecimal(0);
		BigDecimal utilMatOficP = new BigDecimal(0);
		BigDecimal costoTotalGestP = new BigDecimal(0);
		//Lima
		BigDecimal impValEdeL = new BigDecimal(0);
		BigDecimal impValNoEdeL = new BigDecimal(0);
		BigDecimal costoTotalImpL = new BigDecimal(0);
		Long nroValesImpL = new Long(0);
		BigDecimal costoUnitImpValesL = new BigDecimal(0);
		BigDecimal costoTotalValL = new BigDecimal(0);
		Long nroValesReptL = new Long(0);
		BigDecimal costoUnitReptValesL = new BigDecimal(0);
		BigDecimal costoTotalValOficL = new BigDecimal(0);
		Long nroValesEntrL = new Long(0);
		BigDecimal costoUnitEntrValesL = new BigDecimal(0);
		BigDecimal costoEnvPadronL = new BigDecimal(0);
		Long nroValesFisL = new Long(0);
		BigDecimal costoUnitLiqL = new BigDecimal(0);
		BigDecimal costoUnitDigitL = new BigDecimal(0);
		BigDecimal costoAtenSolicL = new BigDecimal(0);
		BigDecimal costoAtenConsL = new BigDecimal(0);
		BigDecimal costoTotalAtenL = new BigDecimal(0);
		Long nroTotalAtenL = new Long(0);
		BigDecimal costoUnitAtenL = new BigDecimal(0);
		BigDecimal costoPersonalL = new BigDecimal(0);
		BigDecimal capacAgentL = new BigDecimal(0);
		BigDecimal utilMatOficL = new BigDecimal(0);
		BigDecimal costoTotalGestL = new BigDecimal(0);

		
		if( fiseFormato14BC.getFiseFormato14BDs() != null && !fiseFormato14BC.getFiseFormato14BDs().isEmpty() ){
			for (FiseFormato14BD detalle : fiseFormato14BC.getFiseFormato14BDs()) {
				if( detalle.getId().getIdZonaBenef()==FiseConstants.ZONABENEF_RURAL_COD ){//RURAL
					
					impValEdeR = detalle.getImpresionValDsctoCliDisEl();
					impValNoEdeR = detalle.getImpreValDsctoCliNoDisEl();
					costoTotalImpR = detalle.getCostoTotalImpresion();
					nroValesImpR = detalle.getNumeroValesImpreso();
					costoUnitImpValesR = detalle.getCostoUnitarioImpresionVales();
					costoTotalValR = detalle.getCostoRepartoValesDescuento();
					nroValesReptR = detalle.getNumeroValesRepartidos();
					costoUnitReptValesR = detalle.getCostoUnitReprtoValeDomici();
					costoTotalValOficR = detalle.getCostoTotReprtoValDisEl();
					nroValesEntrR = detalle.getNumeroValesEntregados();
					costoUnitEntrValesR = detalle.getCostoUnitEntregaValDisEl();
					costoEnvPadronR = detalle.getCostoEnviarPadronValCanje();
					nroValesFisR = detalle.getNumeroValesFisicosEmitidos();
					costoUnitLiqR = detalle.getCostoUnitCanjeLiqValFisi();
					costoUnitDigitR = detalle.getCostoUnitCanjeValDigital();
					costoAtenSolicR = detalle.getCostoAtencionSolicitudes();
					costoAtenConsR = detalle.getCostoAtencionConsultaRecla();
					costoTotalAtenR = detalle.getCostoTotalAtencion();
					nroTotalAtenR = detalle.getNumeroTotalAtencion();
					costoUnitAtenR = detalle.getCostoUnitarioPorAtencion();
					costoPersonalR = detalle.getCostoPersonal();
					capacAgentR = detalle.getCapacitacionAgentesAutGlp();
					utilMatOficR = detalle.getUtilesMaterialesOficina();
					costoTotalGestR = detalle.getCostoTotalGestionAdministra();
				
				}else if( detalle.getId().getIdZonaBenef()==FiseConstants.ZONABENEF_PROVINCIA_COD ){//PROVINCIA
					
					impValEdeP = detalle.getImpresionValDsctoCliDisEl();
					impValNoEdeP = detalle.getImpreValDsctoCliNoDisEl();
					costoTotalImpP = detalle.getCostoTotalImpresion();
					nroValesImpP = detalle.getNumeroValesImpreso();
					costoUnitImpValesP = detalle.getCostoUnitarioImpresionVales();
					costoTotalValP = detalle.getCostoRepartoValesDescuento();
					nroValesReptP = detalle.getNumeroValesRepartidos();
					costoUnitReptValesP = detalle.getCostoUnitReprtoValeDomici();
					costoTotalValOficP = detalle.getCostoTotReprtoValDisEl();
					nroValesEntrP = detalle.getNumeroValesEntregados();
					costoUnitEntrValesP = detalle.getCostoUnitEntregaValDisEl();
					costoEnvPadronP = detalle.getCostoEnviarPadronValCanje();
					nroValesFisP = detalle.getNumeroValesFisicosEmitidos();
					costoUnitLiqP = detalle.getCostoUnitCanjeLiqValFisi();
					costoUnitDigitP = detalle.getCostoUnitCanjeValDigital();
					costoAtenSolicP = detalle.getCostoAtencionSolicitudes();
					costoAtenConsP = detalle.getCostoAtencionConsultaRecla();
					costoTotalAtenP = detalle.getCostoTotalAtencion();
					nroTotalAtenP = detalle.getNumeroTotalAtencion();
					costoUnitAtenP = detalle.getCostoUnitarioPorAtencion();
					costoPersonalP = detalle.getCostoPersonal();
					capacAgentP = detalle.getCapacitacionAgentesAutGlp();
					utilMatOficP = detalle.getUtilesMaterialesOficina();
					costoTotalGestP = detalle.getCostoTotalGestionAdministra();
					
				}else if( detalle.getId().getIdZonaBenef()==FiseConstants.ZONABENEF_LIMA_COD ){//LIMA
					
					impValEdeL = detalle.getImpresionValDsctoCliDisEl();
					impValNoEdeL = detalle.getImpreValDsctoCliNoDisEl();
					costoTotalImpL = detalle.getCostoTotalImpresion();
					nroValesImpL = detalle.getNumeroValesImpreso();
					costoUnitImpValesL = detalle.getCostoUnitarioImpresionVales();
					costoTotalValL = detalle.getCostoRepartoValesDescuento();
					nroValesReptL = detalle.getNumeroValesRepartidos();
					costoUnitReptValesL = detalle.getCostoUnitReprtoValeDomici();
					costoTotalValOficL = detalle.getCostoTotReprtoValDisEl();
					nroValesEntrL = detalle.getNumeroValesEntregados();
					costoUnitEntrValesL = detalle.getCostoUnitEntregaValDisEl();
					costoEnvPadronL = detalle.getCostoEnviarPadronValCanje();
					nroValesFisL = detalle.getNumeroValesFisicosEmitidos();
					costoUnitLiqL = detalle.getCostoUnitCanjeLiqValFisi();
					costoUnitDigitL = detalle.getCostoUnitCanjeValDigital();
					costoAtenSolicL = detalle.getCostoAtencionSolicitudes();
					costoAtenConsL = detalle.getCostoAtencionConsultaRecla();
					costoTotalAtenL = detalle.getCostoTotalAtencion();
					nroTotalAtenL = detalle.getNumeroTotalAtencion();
					costoUnitAtenL = detalle.getCostoUnitarioPorAtencion();
					costoPersonalL = detalle.getCostoPersonal();
					capacAgentL = detalle.getCapacitacionAgentesAutGlp();
					utilMatOficL = detalle.getUtilesMaterialesOficina();
					costoTotalGestL = detalle.getCostoTotalGestionAdministra();
					
				}
			}
		}

		jsonObj.put("impValEdeR", impValEdeR!=null?impValEdeR:new BigDecimal(0));
		jsonObj.put("impValNoEdeR", impValNoEdeR!=null?impValNoEdeR:new BigDecimal(0));
		jsonObj.put("costoTotalImpR", costoTotalImpR!=null?costoTotalImpR:new BigDecimal(0));
		jsonObj.put("nroValesImpR", nroValesImpR!=null?nroValesImpR:0L);
		jsonObj.put("costoUnitImpValesR", costoUnitImpValesR!=null?costoUnitImpValesR:new BigDecimal(0));
		jsonObj.put("costoTotalValR", costoTotalValR!=null?costoTotalValR:new BigDecimal(0));
		jsonObj.put("nroValesReptR", nroValesReptR!=null?nroValesReptR:0L);
		jsonObj.put("costoUnitReptValesR", costoUnitReptValesR!=null?costoUnitReptValesR:new BigDecimal(0));
		jsonObj.put("costoTotalValOficR", costoTotalValOficR!=null?costoTotalValOficR:new BigDecimal(0));
		jsonObj.put("nroValesEntrR", nroValesEntrR!=null?nroValesEntrR:0L);
		jsonObj.put("costoUnitEntrValesR", costoUnitEntrValesR!=null?costoUnitEntrValesR:new BigDecimal(0));
		jsonObj.put("costoEnvPadronR", costoEnvPadronR!=null?costoEnvPadronR:new BigDecimal(0));
		jsonObj.put("nroValesFisR", nroValesFisR!=null?nroValesFisR:0L);
		jsonObj.put("costoUnitLiqR", costoUnitLiqR!=null?costoUnitLiqR:new BigDecimal(0));
		jsonObj.put("costoUnitDigitR", costoUnitDigitR!=null?costoUnitDigitR:new BigDecimal(0));
		jsonObj.put("costoAtenSolicR", costoAtenSolicR!=null?costoAtenSolicR:new BigDecimal(0));
		jsonObj.put("costoAtenConsR", costoAtenConsR!=null?costoAtenConsR:new BigDecimal(0));
		jsonObj.put("costoTotalAtenR", costoTotalAtenR!=null?costoTotalAtenR:new BigDecimal(0));
		jsonObj.put("nroTotalAtenR", nroTotalAtenR!=null?nroTotalAtenR:0L);
		jsonObj.put("costoUnitAtenR", costoUnitAtenR!=null?costoUnitAtenR:new BigDecimal(0));
		jsonObj.put("costoPersonalR", costoPersonalR!=null?costoPersonalR:new BigDecimal(0));
		jsonObj.put("capacAgentR", capacAgentR!=null?capacAgentR:new BigDecimal(0));
		jsonObj.put("utilMatOficR", utilMatOficR!=null?utilMatOficR:new BigDecimal(0));
		jsonObj.put("costoTotalGestR", costoTotalGestR!=null?costoTotalGestR:new BigDecimal(0));
		//
		jsonObj.put("impValEdeP", impValEdeP!=null?impValEdeP:new BigDecimal(0));
		jsonObj.put("impValNoEdeP", impValNoEdeP!=null?impValNoEdeP:new BigDecimal(0));
		jsonObj.put("costoTotalImpP", costoTotalImpP!=null?costoTotalImpP:new BigDecimal(0));
		jsonObj.put("nroValesImpP", nroValesImpP!=null?nroValesImpP:0L);
		jsonObj.put("costoUnitImpValesP", costoUnitImpValesP!=null?costoUnitImpValesP:new BigDecimal(0));
		jsonObj.put("costoTotalValP", costoTotalValP!=null?costoTotalValP:new BigDecimal(0));
		jsonObj.put("nroValesReptP", nroValesReptP!=null?nroValesReptP:0L);
		jsonObj.put("costoUnitReptValesP", costoUnitReptValesP!=null?costoUnitReptValesP:new BigDecimal(0));
		jsonObj.put("costoTotalValOficP", costoTotalValOficP!=null?costoTotalValOficP:new BigDecimal(0));
		jsonObj.put("nroValesEntrP", nroValesEntrP!=null?nroValesEntrP:0L);
		jsonObj.put("costoUnitEntrValesP", costoUnitEntrValesP!=null?costoUnitEntrValesP:new BigDecimal(0));
		jsonObj.put("costoEnvPadronP", costoEnvPadronP!=null?costoEnvPadronP:new BigDecimal(0));
		jsonObj.put("nroValesFisP", nroValesFisP!=null?nroValesFisP:0L);
		jsonObj.put("costoUnitLiqP", costoUnitLiqP!=null?costoUnitLiqP:new BigDecimal(0));
		jsonObj.put("costoUnitDigitP", costoUnitDigitP!=null?costoUnitDigitP:new BigDecimal(0));
		jsonObj.put("costoAtenSolicP", costoAtenSolicP!=null?costoAtenSolicP:new BigDecimal(0));
		jsonObj.put("costoAtenConsP", costoAtenConsP!=null?costoAtenConsP:new BigDecimal(0));
		jsonObj.put("costoTotalAtenP", costoTotalAtenP!=null?costoTotalAtenP:new BigDecimal(0));
		jsonObj.put("nroTotalAtenP", nroTotalAtenP!=null?nroTotalAtenP:0L);
		jsonObj.put("costoUnitAtenP", costoUnitAtenP!=null?costoUnitAtenP:new BigDecimal(0));
		jsonObj.put("costoPersonalP", costoPersonalP!=null?costoPersonalP:new BigDecimal(0));
		jsonObj.put("capacAgentP", capacAgentP!=null?capacAgentP:new BigDecimal(0));
		jsonObj.put("utilMatOficP", utilMatOficP!=null?utilMatOficP:new BigDecimal(0));
		jsonObj.put("costoTotalGestP", costoTotalGestP!=null?costoTotalGestP:new BigDecimal(0));
		//
		jsonObj.put("impValEdeL", impValEdeL!=null?impValEdeL:new BigDecimal(0));
		jsonObj.put("impValNoEdeL", impValNoEdeL!=null?impValNoEdeL:new BigDecimal(0));
		jsonObj.put("costoTotalImpL", costoTotalImpL!=null?costoTotalImpL:new BigDecimal(0));
		jsonObj.put("nroValesImpL", nroValesImpL!=null?nroValesImpL:0L);
		jsonObj.put("costoUnitImpValesL", costoUnitImpValesL!=null?costoUnitImpValesL:new BigDecimal(0));
		jsonObj.put("costoTotalValL", costoTotalValL!=null?costoTotalValL:new BigDecimal(0));
		jsonObj.put("nroValesReptL", nroValesReptL!=null?nroValesReptL:0L);
		jsonObj.put("costoUnitReptValesL", costoUnitReptValesL!=null?costoUnitReptValesL:new BigDecimal(0));
		jsonObj.put("costoTotalValOficL", costoTotalValOficL!=null?costoTotalValOficL:new BigDecimal(0));
		jsonObj.put("nroValesEntrL", nroValesEntrL!=null?nroValesEntrL:0L);
		jsonObj.put("costoUnitEntrValesL", costoUnitEntrValesL!=null?costoUnitEntrValesL:new BigDecimal(0));
		jsonObj.put("costoEnvPadronL", costoEnvPadronL!=null?costoEnvPadronL:new BigDecimal(0));
		jsonObj.put("nroValesFisL", nroValesFisL!=null?nroValesFisL:0L);
		jsonObj.put("costoUnitLiqL", costoUnitLiqL!=null?costoUnitLiqL:new BigDecimal(0));
		jsonObj.put("costoUnitDigitL", costoUnitDigitL!=null?costoUnitDigitL:new BigDecimal(0));
		jsonObj.put("costoAtenSolicL", costoAtenSolicL!=null?costoAtenSolicL:new BigDecimal(0));
		jsonObj.put("costoAtenConsL", costoAtenConsL!=null?costoAtenConsL:new BigDecimal(0));
		jsonObj.put("costoTotalAtenL", costoTotalAtenL!=null?costoTotalAtenL:new BigDecimal(0));
		jsonObj.put("nroTotalAtenL", nroTotalAtenL!=null?nroTotalAtenL:0L);
		jsonObj.put("costoUnitAtenL", costoUnitAtenL!=null?costoUnitAtenL:new BigDecimal(0));
		jsonObj.put("costoPersonalL", costoPersonalL!=null?costoPersonalL:new BigDecimal(0));
		jsonObj.put("capacAgentL", capacAgentL!=null?capacAgentL:new BigDecimal(0));
		jsonObj.put("utilMatOficL", utilMatOficL!=null?utilMatOficL:new BigDecimal(0));
		jsonObj.put("costoTotalGestL", costoTotalGestL!=null?costoTotalGestL:new BigDecimal(0));
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
