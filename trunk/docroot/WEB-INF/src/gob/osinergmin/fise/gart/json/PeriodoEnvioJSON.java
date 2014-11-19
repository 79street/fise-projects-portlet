package gob.osinergmin.fise.gart.json;

import gob.osinergmin.fise.domain.FisePeriodoEnvio;

import org.json.JSONException;
import org.json.JSONObject;

public class PeriodoEnvioJSON {	
	
	private long secuencia;
	private Integer anoPresentacion;
	private Integer mesPresentacion;	
	private String etapa;
	private String formato;
	private String estado;
	private String descEmpresa;
	private String descMesPresentacion;	
	
	
	public JSONObject asJSONObject(FisePeriodoEnvio p) throws JSONException{
		
		JSONObject jsonObj = new JSONObject();
		
		this.descEmpresa=p.getDescEmpresa();
		this.anoPresentacion=p.getAnoPresentacion();
		this.mesPresentacion=p.getMesPresentacion();		
		this.etapa=p.getEtapa();		
		this.descMesPresentacion=p.getDescMesPresentacion();
		
		//seteando valores al json para mostrar
		jsonObj.put("secuencia",p.getSecuencia());	
		jsonObj.put("descEmpresa", p.getDescEmpresa());	
		jsonObj.put("formato", p.getFormato());	
		jsonObj.put("anoPresentacion",p.getAnoPresentacion());	
		jsonObj.put("descMesPresentacion", p.getDescMesPresentacion());
		if("V".equals(p.getEstado())){ 
			jsonObj.put("estado", "Vigente");			
		}else{
			jsonObj.put("estado", "Anulado");			
		}	
		
		return jsonObj;
	}


	/***Metodos get y set*/	

	public long getSecuencia() {
		return secuencia;
	}




	public void setSecuencia(long secuencia) {
		this.secuencia = secuencia;
	}




	public Integer getAnoPresentacion() {
		return anoPresentacion;
	}




	public void setAnoPresentacion(Integer anoPresentacion) {
		this.anoPresentacion = anoPresentacion;
	}




	public Integer getMesPresentacion() {
		return mesPresentacion;
	}




	public void setMesPresentacion(Integer mesPresentacion) {
		this.mesPresentacion = mesPresentacion;
	}




	public String getEtapa() {
		return etapa;
	}




	public void setEtapa(String etapa) {
		this.etapa = etapa;
	}




	public String getFormato() {
		return formato;
	}




	public void setFormato(String formato) {
		this.formato = formato;
	}




	public String getEstado() {
		return estado;
	}




	public void setEstado(String estado) {
		this.estado = estado;
	}




	public String getDescEmpresa() {
		return descEmpresa;
	}




	public void setDescEmpresa(String descEmpresa) {
		this.descEmpresa = descEmpresa;
	}




	public String getDescMesPresentacion() {
		return descMesPresentacion;
	}




	public void setDescMesPresentacion(String descMesPresentacion) {
		this.descMesPresentacion = descMesPresentacion;
	}	
	
}
