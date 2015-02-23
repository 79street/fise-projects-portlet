package gob.osinergmin.fise.gart.json;

import java.math.BigDecimal;

import gob.osinergmin.fise.domain.FiseParametro;

import org.json.JSONException;
import org.json.JSONObject;

public class ParametroJSON {
	
	private String codigo;
	private String nombre;
	private String valor;
	private String orden;
	
	public JSONObject asJSONObject(FiseParametro parametro) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("codigo", parametro.getCodigo());
		jsonObj.put("nombre", parametro.getNombre());
		jsonObj.put("valor", parametro.getValor());
		jsonObj.put("orden", parametro.getOrden());
		
		return jsonObj;
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}
	
	
	
}
