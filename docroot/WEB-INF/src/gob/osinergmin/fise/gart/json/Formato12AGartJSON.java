package gob.osinergmin.fise.gart.json;

import gob.osinergmin.fise.domain.FiseFormato12AC;
import gob.osinergmin.fise.domain.FiseFormato12AD;

import java.math.BigDecimal;

import org.json.JSONException;
import org.json.JSONObject;

public class Formato12AGartJSON {
	
	private String codEmpresa;
	private String descEmpresa;
	private long anoPresentacion = 0;
	private long mesPresentacion = 0;
	private long anoEjecucion = 0;
	private long mesEjecucion = 0;
	private String etapa;
	private String descMesPresentacion;
	private String descMesEjecucion;
	private String grupoInfo;
	private String estado;
	private String anoPres;
	private String mesPres;
	private String anoEjec;
	private String mesEjec;

	public JSONObject asJSONObject(FiseFormato12AC fiseFormato12AC) throws JSONException{
		
		JSONObject jsonObj = new JSONObject();
		
		this.descEmpresa=fiseFormato12AC.getDescEmpresa();
		this.anoPresentacion=fiseFormato12AC.getId().getAnoPresentacion();
		this.mesPresentacion=fiseFormato12AC.getId().getMesPresentacion();
		this.anoEjecucion=fiseFormato12AC.getId().getAnoEjecucionGasto();
		this.mesEjecucion=fiseFormato12AC.getId().getMesEjecucionGasto();
		this.etapa=fiseFormato12AC.getId().getEtapa();
		this.descMesPresentacion=fiseFormato12AC.getDescMesPresentacion();
		this.descMesEjecucion=fiseFormato12AC.getDescMesEjecucion();
		this.grupoInfo=fiseFormato12AC.getFiseGrupoInformacion()!=null?fiseFormato12AC.getFiseGrupoInformacion().getDescripcion():null;
		this.estado=fiseFormato12AC.getDescEstado();
		//verificar despues si el objeto grupoinfo siempre va a venir seteado
		jsonObj.put("codEmpresa", fiseFormato12AC.getId().getCodEmpresa());
		jsonObj.put("descEmpresa", fiseFormato12AC.getDescEmpresa());
		jsonObj.put("anoPresentacion", fiseFormato12AC.getId().getAnoPresentacion());
		jsonObj.put("mesPresentacion", fiseFormato12AC.getId().getMesPresentacion());
		jsonObj.put("anoEjecucion", fiseFormato12AC.getId().getAnoEjecucionGasto());
		jsonObj.put("mesEjecucion", fiseFormato12AC.getId().getMesEjecucionGasto());
		jsonObj.put("etapa", fiseFormato12AC.getId().getEtapa());
		jsonObj.put("descMesPresentacion", fiseFormato12AC.getDescMesPresentacion());
		jsonObj.put("descMesEjecucion", fiseFormato12AC.getDescMesEjecucion());
		jsonObj.put("grupoInfo", fiseFormato12AC.getFiseGrupoInformacion()!=null?fiseFormato12AC.getFiseGrupoInformacion().getDescripcion():null);
		jsonObj.put("estado", fiseFormato12AC.getDescEstado());
		//valores de cabecera y detalle
		long nroEmpadR = 0;
		BigDecimal costoUnitEmpadR = new BigDecimal(0);
		long nroAgentR = 0;
		BigDecimal costoUnitAgentR = new BigDecimal(0);
		BigDecimal desplPersonalR = new BigDecimal(0);
		BigDecimal activExtraordR = new BigDecimal(0);
		long nroEmpadP = 0;
		BigDecimal costoUnitEmpadP = new BigDecimal(0);
		long nroAgentP = 0;
		BigDecimal costoUnitAgentP = new BigDecimal(0);
		BigDecimal desplPersonalP = new BigDecimal(0);
		BigDecimal activExtraordP = new BigDecimal(0);
		long nroEmpadL = 0;
		BigDecimal costoUnitEmpadL = new BigDecimal(0);
		long nroAgentL = 0;
		BigDecimal costoUnitAgentL = new BigDecimal(0);
		BigDecimal desplPersonalL = new BigDecimal(0);
		BigDecimal activExtraordL = new BigDecimal(0);
		
		if( fiseFormato12AC.getFiseFormato12ADs() != null && !fiseFormato12AC.getFiseFormato12ADs().isEmpty() ){
			for (FiseFormato12AD detalle : fiseFormato12AC.getFiseFormato12ADs()) {
				if( detalle.getId().getIdZonaBenef()==1 ){//RURAL
					nroEmpadR = detalle.getNumeroEmpadronados();
					costoUnitEmpadR = detalle.getCostoEstandarUnitarioEmpad();
					nroAgentR = detalle.getNumeroAgentesAutorizGlp();
					costoUnitAgentR = detalle.getCostoEstandarUnitAgAutGlp();
					desplPersonalR = detalle.getTotalDesplazamientoPersonal();
					activExtraordR = detalle.getTotalActividadesExtraord();
				}else if( detalle.getId().getIdZonaBenef()==2 ){//PROVINCIA
					nroEmpadP = detalle.getNumeroEmpadronados();
					costoUnitEmpadP = detalle.getCostoEstandarUnitarioEmpad();
					nroAgentP = detalle.getNumeroAgentesAutorizGlp();
					costoUnitAgentP = detalle.getCostoEstandarUnitAgAutGlp();
					desplPersonalP = detalle.getTotalDesplazamientoPersonal();
					activExtraordP = detalle.getTotalActividadesExtraord();
				}else if( detalle.getId().getIdZonaBenef()==3 ){//LIMA
					nroEmpadL = detalle.getNumeroEmpadronados();
					costoUnitEmpadL = detalle.getCostoEstandarUnitarioEmpad();
					nroAgentL = detalle.getNumeroAgentesAutorizGlp();
					costoUnitAgentL = detalle.getCostoEstandarUnitAgAutGlp();
					desplPersonalL = detalle.getTotalDesplazamientoPersonal();
					activExtraordL = detalle.getTotalActividadesExtraord();
				}
			}
		}

		jsonObj.put("nroEmpadR", nroEmpadR);
		jsonObj.put("costoUnitEmpadR", costoUnitEmpadR);
		jsonObj.put("nroAgentR", nroAgentR);
		jsonObj.put("costoUnitAgentR", costoUnitAgentR);
		jsonObj.put("desplPersonalR", desplPersonalR);
		jsonObj.put("activExtraordR", activExtraordR);
		//
		jsonObj.put("nroEmpadP", nroEmpadP);
		jsonObj.put("costoUnitEmpadP", costoUnitEmpadP);
		jsonObj.put("nroAgentP", nroAgentP);
		jsonObj.put("costoUnitAgentP", costoUnitAgentP);
		jsonObj.put("desplPersonalP", desplPersonalP);
		jsonObj.put("activExtraordP", activExtraordP);
		//
		jsonObj.put("nroEmpadL", nroEmpadL);
		jsonObj.put("costoUnitEmpadL", costoUnitEmpadL);
		jsonObj.put("nroAgentL", nroAgentL);
		jsonObj.put("costoUnitAgentL", costoUnitAgentL);
		jsonObj.put("desplPersonalL", desplPersonalL);
		jsonObj.put("activExtraordL", activExtraordL);
		//
		jsonObj.put("totalGeneral", fiseFormato12AC.getTotalAReconocer());

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

	public long getAnoEjecucion() {
		return anoEjecucion;
	}

	public void setAnoEjecucion(long anoEjecucion) {
		this.anoEjecucion = anoEjecucion;
	}

	public long getMesEjecucion() {
		return mesEjecucion;
	}

	public void setMesEjecucion(long mesEjecucion) {
		this.mesEjecucion = mesEjecucion;
	}

	public String getEtapa() {
		return etapa;
	}

	public void setEtapa(String etapa) {
		this.etapa = etapa;
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

	public String getAnoEjec() {
		return anoEjec;
	}

	public void setAnoEjec(String anoEjec) {
		this.anoEjec = anoEjec;
	}

	public String getMesEjec() {
		return mesEjec;
	}

	public void setMesEjec(String mesEjec) {
		this.mesEjec = mesEjec;
	}

	public String getDescMesPresentacion() {
		return descMesPresentacion;
	}

	public void setDescMesPresentacion(String descMesPresentacion) {
		this.descMesPresentacion = descMesPresentacion;
	}

	public String getDescMesEjecucion() {
		return descMesEjecucion;
	}

	public void setDescMesEjecucion(String descMesEjecucion) {
		this.descMesEjecucion = descMesEjecucion;
	}

}
