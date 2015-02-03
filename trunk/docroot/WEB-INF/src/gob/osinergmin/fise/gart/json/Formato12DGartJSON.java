package gob.osinergmin.fise.gart.json;

import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.FiseFormato12DC;
import gob.osinergmin.fise.domain.FiseFormato12DD;
import gob.osinergmin.fise.util.FechaUtil;
import gob.osinergmin.fise.util.FormatoUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class Formato12DGartJSON {
	
	private String codEmpresa;
	private String descEmpresa;
	private long anoPresentacion = 0;
	private long mesPresentacion = 0;
	private String etapa;
	private String descMesPresentacion;
	private String grupoInfo;
	private String estado;
	private String anoPres;
	private String mesPres;
	private String anoEjec;
	private String mesEjec;
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
	
	public JSONObject asJSONObject(FiseFormato12DC fiseFormato12DC, String flagPeriodoEjecucion, String flagOperacion) throws JSONException{
		
		JSONObject jsonObj = new JSONObject();
		
		this.descEmpresa=fiseFormato12DC.getDescEmpresa();
		this.anoPresentacion=fiseFormato12DC.getId().getAnoPresentacion();
		this.mesPresentacion=fiseFormato12DC.getId().getMesPresentacion();
		this.etapa=fiseFormato12DC.getId().getEtapa();
		this.descMesPresentacion=fiseFormato12DC.getDescMesPresentacion();
		this.grupoInfo=fiseFormato12DC.getDescGrupoInformacion();
		this.estado=fiseFormato12DC.getDescEstado();
		
		jsonObj.put("codEmpresa", fiseFormato12DC.getId().getCodEmpresa());
		//formar flag de verificado
		jsonObj.put("flagPeriodoEjecucion", flagPeriodoEjecucion);
		jsonObj.put("descEmpresa", fiseFormato12DC.getDescEmpresa());
		jsonObj.put("anoPresentacion", fiseFormato12DC.getId().getAnoPresentacion());
		jsonObj.put("mesPresentacion", fiseFormato12DC.getId().getMesPresentacion());
		jsonObj.put("etapa", fiseFormato12DC.getId().getEtapa());
		jsonObj.put("descMesPresentacion", fiseFormato12DC.getDescMesPresentacion());
		
//		if( FiseConstants.BLANCO.equals(flagOperacion) ){
//			//grupo informacion y estado
//				if(fiseFormato12DC.getFiseGrupoInformacion()!=null && fiseFormato12DC.getFiseGrupoInformacion().getDescripcion()!=null){
//					jsonObj.put("grupoInfo",fiseFormato12DC.getFiseGrupoInformacion().getDescripcion());
//				}else{
//					jsonObj.put("grupoInfo",FiseConstants.BLANCO);
//				}
//				/*if(fiseFormato12DC.getFechaEnvioDefinitivo()!=null){
//					jsonObj.put("estado",FiseConstants.ESTADO_FECHAENVIO_ENVIADO);
//				}else{
//					jsonObj.put("estado",FiseConstants.ESTADO_FECHAENVIO_POR_ENVIAR);
//				}*/
//				jsonObj.put("estado", FormatoUtil.cambiaTextoAMinusculas(flagOperacion, 0));
//		}else{
//			jsonObj.put("grupoInfo", fiseFormato12DC.getDescGrupoInformacion());
//			jsonObj.put("estado", fiseFormato12DC.getDescEstado());
//		}

		if(fiseFormato12DC.getFiseGrupoInformacion()!=null && fiseFormato12DC.getFiseGrupoInformacion().getDescripcion()!=null){
			jsonObj.put("grupoInfo",fiseFormato12DC.getFiseGrupoInformacion().getDescripcion());
		}else{
			jsonObj.put("grupoInfo",FiseConstants.BLANCO);
		}
		jsonObj.put("estado", FormatoUtil.cambiaTextoAMinusculas(flagOperacion, 0));
		
		jsonObj.put("flagOperacion", flagOperacion);

		return jsonObj;
	}

	public JSONObject asJSONObject(FiseFormato12DD fiseFormato12DD) throws JSONException{
		
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("codEmpresa", fiseFormato12DD.getId().getCodEmpresa());
		jsonObj.put("anoPresentacion", fiseFormato12DD.getId().getAnoPresentacion());
		jsonObj.put("mesPresentacion", fiseFormato12DD.getId().getMesPresentacion());
		jsonObj.put("etapa", fiseFormato12DD.getId().getEtapa());
		jsonObj.put("anoEjecucion", fiseFormato12DD.getId().getAnoEjecucionGasto());
		jsonObj.put("mesEjecucion", fiseFormato12DD.getId().getMesEjecucionGasto());
		jsonObj.put("etapaEjecucion", fiseFormato12DD.getId().getEtapaEjecucion());
		jsonObj.put("item", fiseFormato12DD.getId().getNumeroItemEtapa());
		
		jsonObj.put("descMesEjecucion", fiseFormato12DD.getDescMesEjecucion());
		jsonObj.put("descEtapaEjecucion", fiseFormato12DD.getDescEtapaEjecucion());
		jsonObj.put("ubigeo", fiseFormato12DD.getCodUbigeo());
		jsonObj.put("localidad", fiseFormato12DD.getDescripcionLocalidad());
		jsonObj.put("descZonaBenef", fiseFormato12DD.getDescZonaBenef());
		jsonObj.put("cuentaContable", fiseFormato12DD.getCodigoCuentaContaEde());
		jsonObj.put("gasto", fiseFormato12DD.getDescripcionGasto());
		jsonObj.put("tipoGasto", fiseFormato12DD.getIdTipGasto());
		jsonObj.put("tipoDoc", fiseFormato12DD.getIdTipDocRef());
		jsonObj.put("ruc", fiseFormato12DD.getRucEmpresaEmiteDocRef());
		jsonObj.put("serieDoc", fiseFormato12DD.getSerieDocumentoReferencia());
		jsonObj.put("nroDoc", fiseFormato12DD.getNumeroDocumentoRefGasto());
		jsonObj.put("fechaAuto", FechaUtil.fecha_DD_MM_YYYY(fiseFormato12DD.getFechaAutorizacionGasto()));
		jsonObj.put("nroDocAuto", fiseFormato12DD.getNumeroDocAutorizaGasto());
		jsonObj.put("cantidad", fiseFormato12DD.getCantidad());
		jsonObj.put("costoUnitario", fiseFormato12DD.getCostoUnitario());
		
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
