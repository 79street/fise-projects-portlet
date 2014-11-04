package gob.osinergmin.fise.gart.json;

import gob.osinergmin.fise.domain.FiseFormato13AC;

import org.json.JSONException;
import org.json.JSONObject;

public class Formato13AGartJSON {
	
	private String codEmpresa;
	private long anoPresentacion;
	private long mesPresentacion;
	private String etapa;
	private long idGrupoinformacion;
	//
	private String descEmpresa;
	private String descMesPresentacion;
	private String descGrupoInformacion;
	
	public JSONObject asJSONObject(FiseFormato13AC fiseFormato13AC) throws JSONException{
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("codEmpresa",fiseFormato13AC.getId().getCodEmpresa() );
		jsonObj.put("anoPresentacion",fiseFormato13AC.getId().getAnoPresentacion() );
		jsonObj.put("mesPresentacion",fiseFormato13AC.getId().getMesPresentacion() );
		jsonObj.put("etapa",fiseFormato13AC.getId().getEtapa());
		jsonObj.put("idGrupoinformacion",fiseFormato13AC.getIdGrupoInformacion());
		jsonObj.put("descEmpresa",fiseFormato13AC.getDescEmpresa());
		jsonObj.put("descMesPresentacion",fiseFormato13AC.getDescMesPresentacion());
		jsonObj.put("descGrupoInformacion",fiseFormato13AC.getDescGrupoInformacion());
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

	public long getIdGrupoinformacion() {
		return idGrupoinformacion;
	}

	public void setIdGrupoinformacion(long idGrupoinformacion) {
		this.idGrupoinformacion = idGrupoinformacion;
	}

	public String getDescGrupoInformacion() {
		return descGrupoInformacion;
	}

	public void setDescGrupoInformacion(String descGrupoInformacion) {
		this.descGrupoInformacion = descGrupoInformacion;
	}
}
