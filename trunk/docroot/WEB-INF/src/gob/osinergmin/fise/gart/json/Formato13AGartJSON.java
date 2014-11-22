package gob.osinergmin.fise.gart.json;

import gob.osinergmin.fise.bean.Formato13ADReportBean;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.FiseFormato13AC;
import gob.osinergmin.fise.domain.FiseFormato13AD;

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
	
	
	
	private String flagOperacion;//Cerrado, abierto, enviado
	
	public JSONObject asJSONObject(FiseFormato13AC fiseFormato13AC, String flagOperacion,String inicioVig,String finVig) throws JSONException{
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("codEmpresa",fiseFormato13AC.getId().getCodEmpresa() );
		jsonObj.put("anoPresentacion",fiseFormato13AC.getId().getAnoPresentacion() );
		jsonObj.put("mesPresentacion",fiseFormato13AC.getId().getMesPresentacion() );
		jsonObj.put("etapa",fiseFormato13AC.getId().getEtapa());
		//jsonObj.put("idGrupoinformacion",fiseFormato13AC.getIdGrupoInformacion());
		jsonObj.put("descEmpresa",fiseFormato13AC.getDescEmpresa());
		jsonObj.put("descMesPresentacion",fiseFormato13AC.getDescMesPresentacion());
		//jsonObj.put("descGrupoInformacion",fiseFormato13AC.getDescGrupoInformacion());
		
		if(fiseFormato13AC.getFiseGrupoInformacion()!=null && fiseFormato13AC.getFiseGrupoInformacion().getDescripcion()!=null){
			jsonObj.put("grupoInfo", fiseFormato13AC.getFiseGrupoInformacion().getDescripcion());	
		}else{
			jsonObj.put("grupoInfo", FiseConstants.BLANCO);	
		}
		
		if(fiseFormato13AC.getFechaEnvioDefinitivo()!=null){
			jsonObj.put("estado", FiseConstants.ESTADO_FECHAENVIO_ENVIADO);
		}else{
			jsonObj.put("estado", FiseConstants.ESTADO_FECHAENVIO_POR_ENVIAR);
		}
		
		
		jsonObj.put("anioInicioVigencia", inicioVig);
		jsonObj.put("anioFinVigencia", finVig);
		
		jsonObj.put("flagOperacion", flagOperacion);
		
		return jsonObj;
	}
	
	public JSONObject asJSONObject(FiseFormato13AD fiseFormato13AD) throws JSONException{
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("codEmpresa",fiseFormato13AD.getId().getCodEmpresa() );
		jsonObj.put("anoPresentacion",fiseFormato13AD.getId().getAnoPresentacion() );
		jsonObj.put("mesPresentacion",fiseFormato13AD.getId().getMesPresentacion() );
		jsonObj.put("etapa",fiseFormato13AD.getId().getEtapa());
		jsonObj.put("codUbigeo",fiseFormato13AD.getId().getCodUbigeo());
		jsonObj.put("codSectorTipico",fiseFormato13AD.getId().getCodSectorTipico());
		jsonObj.put("idZonaBenef",fiseFormato13AD.getId().getIdZonaBenef());
		
		jsonObj.put("anioMesAlta",fiseFormato13AD.getAnoAlta()+"-"+fiseFormato13AD.getMesAlta());
		jsonObj.put("descripcionLocalidad",fiseFormato13AD.getDescripcionLocalidad());
		jsonObj.put("numeroBenefiPoteSectTipico",fiseFormato13AD.getNumeroBenefiPoteSectTipico());
		jsonObj.put("nombreSedeAtiende",fiseFormato13AD.getNombreSedeAtiende());
		return jsonObj;
	}
	
	public JSONObject asJSONObject(Formato13ADReportBean fiseFormato13AD,FiseFormato13AC filtro) throws JSONException{
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("codEmpresa",filtro.getId().getCodEmpresa() );
		jsonObj.put("anoPresentacion",filtro.getId().getAnoPresentacion() );
		jsonObj.put("mesPresentacion",filtro.getId().getMesPresentacion() );
		jsonObj.put("etapa",filtro.getId().getEtapa());
		jsonObj.put("codUbigeo",fiseFormato13AD.getCodUbigeo());

		
		jsonObj.put("anioMesAlta",fiseFormato13AD.getAnioAlta()+"-"+fiseFormato13AD.getMesAlta());
		jsonObj.put("descripcionLocalidad",fiseFormato13AD.getDescLocalidad());
		jsonObj.put("st1",fiseFormato13AD.getNroBenefPoteSecTipico1());
		jsonObj.put("st2",fiseFormato13AD.getNroBenefPoteSecTipico2());
		jsonObj.put("st3",fiseFormato13AD.getNroBenefPoteSecTipico3());
		jsonObj.put("st4",fiseFormato13AD.getNroBenefPoteSecTipico4());
		jsonObj.put("st5",fiseFormato13AD.getNroBenefPoteSecTipico5());
		jsonObj.put("st6",fiseFormato13AD.getNroBenefPoteSecTipico6());
		jsonObj.put("stserv",fiseFormato13AD.getNroBenefPoteSecTipico7());
		jsonObj.put("stesp",fiseFormato13AD.getNroBenefPoteSecTipico8());
		jsonObj.put("total", fiseFormato13AD.getNroBenefPoteSecTipico1()+fiseFormato13AD.getNroBenefPoteSecTipico2()+
				fiseFormato13AD.getNroBenefPoteSecTipico3()+fiseFormato13AD.getNroBenefPoteSecTipico4()+
				fiseFormato13AD.getNroBenefPoteSecTipico5()+fiseFormato13AD.getNroBenefPoteSecTipico6()+
				fiseFormato13AD.getNroBenefPoteSecTipico7()+fiseFormato13AD.getNroBenefPoteSecTipico8());
		jsonObj.put("idZonaBenef",fiseFormato13AD.getIdZonaBenef());
		jsonObj.put("nombreSedeAtiende",fiseFormato13AD.getNombreSedeAtiende());
		//add
		jsonObj.put("descAnioMesAlta", fiseFormato13AD.getAnioAlta()+"-"+fiseFormato13AD.getDescMesAlta());
		jsonObj.put("descZonaBenef", fiseFormato13AD.getDescZonaBenef());
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

	public String getFlagOperacion() {
		return flagOperacion;
	}

	public void setFlagOperacion(String flagOperacion) {
		this.flagOperacion = flagOperacion;
	}
}
