package gob.osinergmin.fise.gart.json;

import gob.osinergmin.fise.domain.FiseFormato14CC;

import org.json.JSONException;
import org.json.JSONObject;

public class Formato14CJSON {	
	
	private String codEmpresa;	
	private long anoPresentacion;
	private long mesPresentacion;
	private long anoIniVigencia;
	private long anoFinVigencia;	
	private String etapa;
	/////////////////
	private String descEmpresa;
	private String descMesPresentacion;	
	private String grupoInfo;
	private String estado;
	
	
	private String mensajeInfo;
	private String mensajeError;
	private String flag;//flag para controlar mostrar el formulario de ingreso cuando hay un error en carga de formulario excel o texto

	public JSONObject asJSONObject(FiseFormato14CC f, String flagPeriodoEjecucion) throws JSONException{
		
		JSONObject jsonObj = new JSONObject();
		
		this.descEmpresa=f.getDescEmpresa();
		this.anoPresentacion=f.getId().getAnoPresentacion();
		this.mesPresentacion=f.getId().getMesPresentacion();
		this.anoIniVigencia=f.getId().getAnoInicioVigencia();
		this.anoFinVigencia=f.getId().getAnoFinVigencia();
		this.etapa=f.getId().getEtapa();
		
		this.descMesPresentacion=f.getDescMesPresentacion();
		this.grupoInfo=f.getDescEmpresa();
		this.estado=f.getDescEstado();
		
		//seteando valores al json para mostrar
		jsonObj.put("codEmpresa", f.getId().getCodEmpresa());	
		jsonObj.put("anoPresentacion", f.getId().getAnoPresentacion());
		jsonObj.put("mesPresentacion", f.getId().getMesPresentacion());
		jsonObj.put("anoIniVigencia", f.getId().getAnoInicioVigencia());
		jsonObj.put("anoFinVigencia", f.getId().getAnoFinVigencia());
		jsonObj.put("etapa", f.getId().getEtapa());
		
		jsonObj.put("descEmpresa", f.getDescEmpresa());
		jsonObj.put("descMesPresentacion", f.getDescMesPresentacion());
		if(f.getFiseGrupoInformacion()!=null && f.getFiseGrupoInformacion().getDescripcion()!=null){
			jsonObj.put("grupoInfo", f.getFiseGrupoInformacion().getDescripcion());	
		}else{
			jsonObj.put("grupoInfo", "---");	
		}
		if(f.getFechaEnvioDefinitivo()!=null){
			jsonObj.put("estado", "Enviado");	
		}else{
			jsonObj.put("estado", "Por Enviar");	
		}		
		return jsonObj;
	}
	
	
	
	/***Metodos get y set*/	
	
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

	
	
}
