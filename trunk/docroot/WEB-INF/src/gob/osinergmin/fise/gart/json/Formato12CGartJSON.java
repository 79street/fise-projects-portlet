package gob.osinergmin.fise.gart.json;

import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.FiseFormato12CC;
import gob.osinergmin.fise.domain.FiseFormato12CD;
import gob.osinergmin.fise.util.FormatoUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class Formato12CGartJSON {
	
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
	
	public JSONObject asJSONObject(FiseFormato12CC fiseFormato12CC, String flagPeriodoEjecucion, String flagOperacion) throws JSONException{
		
		JSONObject jsonObj = new JSONObject();
		
		this.descEmpresa=fiseFormato12CC.getDescEmpresa();
		this.anoPresentacion=fiseFormato12CC.getId().getAnoPresentacion();
		this.mesPresentacion=fiseFormato12CC.getId().getMesPresentacion();
		this.etapa=fiseFormato12CC.getId().getEtapa();
		this.descMesPresentacion=fiseFormato12CC.getDescMesPresentacion();
		this.grupoInfo=fiseFormato12CC.getDescGrupoInformacion();
		this.estado=fiseFormato12CC.getDescEstado();
		
		jsonObj.put("codEmpresa", fiseFormato12CC.getId().getCodEmpresa());
		//formar flag de verificado
		jsonObj.put("flagPeriodoEjecucion", flagPeriodoEjecucion);
		jsonObj.put("descEmpresa", fiseFormato12CC.getDescEmpresa());
		jsonObj.put("anoPresentacion", fiseFormato12CC.getId().getAnoPresentacion());
		jsonObj.put("mesPresentacion", fiseFormato12CC.getId().getMesPresentacion());
		jsonObj.put("etapa", fiseFormato12CC.getId().getEtapa());
		jsonObj.put("descMesPresentacion", fiseFormato12CC.getDescMesPresentacion());
		
//		if( FiseConstants.BLANCO.equals(flagOperacion) ){
//			//grupo informacion y estado
//				if(fiseFormato12CC.getFiseGrupoInformacion()!=null && fiseFormato12CC.getFiseGrupoInformacion().getDescripcion()!=null){
//					jsonObj.put("grupoInfo",fiseFormato12CC.getFiseGrupoInformacion().getDescripcion());
//				}else{
//					jsonObj.put("grupoInfo",FiseConstants.BLANCO);
//				}
//				/*if(fiseFormato12CC.getFechaEnvioDefinitivo()!=null){
//					jsonObj.put("estado",FiseConstants.ESTADO_FECHAENVIO_ENVIADO);
//				}else{
//					jsonObj.put("estado",FiseConstants.ESTADO_FECHAENVIO_POR_ENVIAR);
//				}*/
//				jsonObj.put("estado", FormatoUtil.cambiaTextoAMinusculas(flagOperacion, 0));
//		}else{
//			jsonObj.put("grupoInfo", fiseFormato12CC.getDescGrupoInformacion());
//			jsonObj.put("estado", fiseFormato12CC.getDescEstado());
//		}

		if(fiseFormato12CC.getFiseGrupoInformacion()!=null && fiseFormato12CC.getFiseGrupoInformacion().getDescripcion()!=null){
			jsonObj.put("grupoInfo",fiseFormato12CC.getFiseGrupoInformacion().getDescripcion());
		}else{
			jsonObj.put("grupoInfo",FiseConstants.BLANCO);
		}
		jsonObj.put("estado", FormatoUtil.cambiaTextoAMinusculas(flagOperacion, 0));
		
		jsonObj.put("flagOperacion", flagOperacion);

		return jsonObj;
	}

	public JSONObject asJSONObject(FiseFormato12CD fiseFormato12CD) throws JSONException{
		
		JSONObject jsonObj = new JSONObject();
		
	/*	if( FiseConstants.ETAPA_EJECUCION_IMPLEMENTACION_COD == fiseFormato12CD.getId().getEtapaEjecucion() ){
			jsonObj.put("codEmpresaI", fiseFormato12CD.getId().getCodEmpresa());
			jsonObj.put("anoPresentacion", fiseFormato12CD.getId().getAnoPresentacion());
			jsonObj.put("mesPresentacionI", fiseFormato12CD.getId().getMesPresentacion());
			jsonObj.put("etapaI", fiseFormato12CD.getId().getEtapa());
			jsonObj.put("anoEjecucionI", fiseFormato12CD.getId().getAnoEjecucionGasto());
			jsonObj.put("mesEjecucionI", fiseFormato12CD.getId().getMesEjecucionGasto());
			jsonObj.put("etapaEjecucionI", fiseFormato12CD.getId().getEtapaEjecucion());
			jsonObj.put("itemI", fiseFormato12CD.getId().getNumeroItemEtapa());
			
			jsonObj.put("descMesEjecucionI", fiseFormato12CD.getDescMesEjecucion());
			jsonObj.put("descEtapaEjecucionI", fiseFormato12CD.getDescEtapaEjecucion());
			jsonObj.put("ubigeoOrigenI", fiseFormato12CD.getCodUbigeoOrigen());
			jsonObj.put("localidadOrigenI", fiseFormato12CD.getDescripcionLocalidadOrigen());
			jsonObj.put("ubigeoDestinoI", fiseFormato12CD.getCodUbigeoDestino());
			jsonObj.put("localidadDestinoI", fiseFormato12CD.getDescripcionLocalidadDestino());
			jsonObj.put("descZonaBenefI", fiseFormato12CD.getDescZonaBenef());
			jsonObj.put("cuentaContableI", fiseFormato12CD.getCodigoCuentaContaEde());
			jsonObj.put("actividadI", fiseFormato12CD.getDescripcionActividad());
			jsonObj.put("tipoDocI", fiseFormato12CD.getIdTipDocRef());
			jsonObj.put("rucI", fiseFormato12CD.getRucEmpresaEmiteDocRef());
			jsonObj.put("serieDocI", fiseFormato12CD.getSerieDocumentoReferencia());
			jsonObj.put("nroDocI", fiseFormato12CD.getNumeroDocumentoReferencia());
			jsonObj.put("nroDiasI", fiseFormato12CD.getNumeroDias());
			jsonObj.put("alimentacionI", fiseFormato12CD.getMontoAlimentacion());
			jsonObj.put("alojamientoI", fiseFormato12CD.getMontoAlojamiento());
			jsonObj.put("movilidadI", fiseFormato12CD.getMontoMovilidad());
		}else if( FiseConstants.ETAPA_EJECUCION_OPERATIVA_COD == fiseFormato12CD.getId().getEtapaEjecucion() ){
			jsonObj.put("codEmpresaO", fiseFormato12CD.getId().getCodEmpresa());
			jsonObj.put("anoPresentacionO", fiseFormato12CD.getId().getAnoPresentacion());
			jsonObj.put("mesPresentacionO", fiseFormato12CD.getId().getMesPresentacion());
			jsonObj.put("etapaO", fiseFormato12CD.getId().getEtapa());
			jsonObj.put("anoEjecucionO", fiseFormato12CD.getId().getAnoEjecucionGasto());
			jsonObj.put("mesEjecucionO", fiseFormato12CD.getId().getMesEjecucionGasto());
			jsonObj.put("etapaEjecucionO", fiseFormato12CD.getId().getEtapaEjecucion());
			jsonObj.put("itemO", fiseFormato12CD.getId().getNumeroItemEtapa());
			
			jsonObj.put("descMesEjecucionO", fiseFormato12CD.getDescMesEjecucion());
			jsonObj.put("descEtapaEjecucionO", fiseFormato12CD.getDescEtapaEjecucion());
			jsonObj.put("ubigeoOrigenO", fiseFormato12CD.getCodUbigeoOrigen());
			jsonObj.put("localidadOrigenO", fiseFormato12CD.getDescripcionLocalidadOrigen());
			jsonObj.put("ubigeoDestinoO", fiseFormato12CD.getCodUbigeoDestino());
			jsonObj.put("localidadDestinoO", fiseFormato12CD.getDescripcionLocalidadDestino());
			jsonObj.put("descZonaBenefO", fiseFormato12CD.getDescZonaBenef());
			jsonObj.put("cuentaContableO", fiseFormato12CD.getCodigoCuentaContaEde());
			jsonObj.put("actividadO", fiseFormato12CD.getDescripcionActividad());
			jsonObj.put("tipoDocO", fiseFormato12CD.getIdTipDocRef());
			jsonObj.put("rucO", fiseFormato12CD.getRucEmpresaEmiteDocRef());
			jsonObj.put("serieDocO", fiseFormato12CD.getSerieDocumentoReferencia());
			jsonObj.put("nroDocO", fiseFormato12CD.getNumeroDocumentoReferencia());
			jsonObj.put("nroDiasO", fiseFormato12CD.getNumeroDias());
			jsonObj.put("alimentacionO", fiseFormato12CD.getMontoAlimentacion());
			jsonObj.put("alojamientoO", fiseFormato12CD.getMontoAlojamiento());
			jsonObj.put("movilidadO", fiseFormato12CD.getMontoMovilidad());
		}*/
		
		jsonObj.put("codEmpresa", fiseFormato12CD.getId().getCodEmpresa());
		jsonObj.put("anoPresentacion", fiseFormato12CD.getId().getAnoPresentacion());
		jsonObj.put("mesPresentacion", fiseFormato12CD.getId().getMesPresentacion());
		jsonObj.put("etapa", fiseFormato12CD.getId().getEtapa());
		jsonObj.put("anoEjecucion", fiseFormato12CD.getId().getAnoEjecucionGasto());
		jsonObj.put("mesEjecucion", fiseFormato12CD.getId().getMesEjecucionGasto());
		jsonObj.put("etapaEjecucion", fiseFormato12CD.getId().getEtapaEjecucion());
		jsonObj.put("item", fiseFormato12CD.getId().getNumeroItemEtapa());
		
		jsonObj.put("descMesEjecucion", fiseFormato12CD.getDescMesEjecucion());
		jsonObj.put("descEtapaEjecucion", fiseFormato12CD.getDescEtapaEjecucion());
		jsonObj.put("ubigeoOrigen", fiseFormato12CD.getCodUbigeoOrigen());
		jsonObj.put("localidadOrigen", fiseFormato12CD.getDescripcionLocalidadOrigen());
		jsonObj.put("ubigeoDestino", fiseFormato12CD.getCodUbigeoDestino());
		jsonObj.put("localidadDestino", fiseFormato12CD.getDescripcionLocalidadDestino());
		jsonObj.put("descZonaBenef", fiseFormato12CD.getDescZonaBenef());
		jsonObj.put("cuentaContable", fiseFormato12CD.getCodigoCuentaContaEde());
		jsonObj.put("actividad", fiseFormato12CD.getDescripcionActividad());
		jsonObj.put("tipoDoc", fiseFormato12CD.getIdTipDocRef());
		jsonObj.put("ruc", fiseFormato12CD.getRucEmpresaEmiteDocRef());
		jsonObj.put("serieDoc", fiseFormato12CD.getSerieDocumentoReferencia());
		jsonObj.put("nroDoc", fiseFormato12CD.getNumeroDocumentoReferencia());
		jsonObj.put("nroDias", fiseFormato12CD.getNumeroDias());
		jsonObj.put("alimentacion", fiseFormato12CD.getMontoAlimentacion());
		jsonObj.put("alojamiento", fiseFormato12CD.getMontoAlojamiento());
		jsonObj.put("movilidad", fiseFormato12CD.getMontoMovilidad());
		
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
