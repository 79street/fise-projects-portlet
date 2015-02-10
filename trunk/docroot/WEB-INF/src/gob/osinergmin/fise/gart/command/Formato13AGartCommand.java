package gob.osinergmin.fise.gart.command;

import gob.osinergmin.fise.bean.MensajeErrorBean;
import gob.osinergmin.fise.domain.AdmEmpresa;
import gob.osinergmin.fise.domain.AdmUbigeo;
import gob.osinergmin.fise.domain.FisePeriodoEnvio;

import java.util.List;
import java.util.Map;

public class Formato13AGartCommand {

	private Map<Long,String> listaMes;
	private Map<Long, String> listaZonasBenef;
	private List<AdmEmpresa> listaEmpresas;
	private List<AdmUbigeo> listaDepartamentos;
	private String codEmpresa;
	private String mesInicio;
	private String anioInicio;
	private String mesFin;
	private String anioFin;
	private String etapa;
	//Nuevo
	private String peridoDeclaracion; 
	private String descripcionPeriodo; 
	
	private String codEmpresaHidden;
	private String descripcionPeriodoHidden;
	//Detalle
	private String descEmpresa;
	private String mesPresentacion;
	private String anioPresentacion;
	

	private String mensajeInformacion;
	private String mensajeError;
	private List<MensajeErrorBean> listaMensajeError;

	private String mesAlta;
	private String anioAlta;
	private String codDepartamento;
	private String codProvincia;
	private String codDistrito;
	private String localidad;
	private String st1;
	private String st2;
	private String st3;
	private String st4;
	private String st5;
	private String st6;
	private String stser;
	private String stesp;
	private String total;
	private String idZonaBenef;
	private String nombreSede;
	//
	private String anioInicioVigencia;
	private String anioFinVigencia;
	
	//add
	private String descDepartamento;
	private String descProvincia;
	private String descDistrito;
	private String codDepartamentoHidden;
	private String codProvinciaHidden;
	private String codDistritoHidden;
	
	private String codUbigeo;
	

	private List<FisePeriodoEnvio> listaPeriodo;
	private boolean readOnly;
	private boolean readonlyFlagPeriodo;
	private boolean readonlyEdit;
	
	private String  codEdelnor;
	private String  codLuzSur;
	
	private int tipoOperacion;
	
	private String descGrupoInformacion;
	private String descestado;
	
	private String anoInicioVigenciaHidden;
	private String anoFinVigenciaHidden;
	
	private Integer typeFile;
	
	private String idGrupoInfo;
	
	public List<AdmEmpresa> getListaEmpresas() {
		return listaEmpresas;
	}

	public void setListaEmpresas(List<AdmEmpresa> listaEmpresas) {
		this.listaEmpresas = listaEmpresas;
	}

	public String getCodEmpresa() {
		return codEmpresa;
	}

	public void setCodEmpresa(String codEmpresa) {
		this.codEmpresa = codEmpresa;
	}

	public Map<Long, String> getListaMes() {
		return listaMes;
	}

	public void setListaMes(Map<Long, String> listaMes) {
		this.listaMes = listaMes;
	}

	public String getMesInicio() {
		return mesInicio;
	}

	public void setMesInicio(String mesInicio) {
		this.mesInicio = mesInicio;
	}

	public String getAnioInicio() {
		return anioInicio;
	}

	public void setAnioInicio(String anioInicio) {
		this.anioInicio = anioInicio;
	}

	public String getMesFin() {
		return mesFin;
	}

	public void setMesFin(String mesFin) {
		this.mesFin = mesFin;
	}

	public String getAnioFin() {
		return anioFin;
	}

	public void setAnioFin(String anioFin) {
		this.anioFin = anioFin;
	}

	public String getEtapa() {
		return etapa;
	}

	public void setEtapa(String etapa) {
		this.etapa = etapa;
	}

	public String getPeridoDeclaracion() {
		return peridoDeclaracion;
	}

	public void setPeridoDeclaracion(String peridoDeclaracion) {
		this.peridoDeclaracion = peridoDeclaracion;
	}


	public String getMensajeInformacion() {
		return mensajeInformacion;
	}

	public void setMensajeInformacion(String mensajeInformacion) {
		this.mensajeInformacion = mensajeInformacion;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public List<MensajeErrorBean> getListaMensajeError() {
		return listaMensajeError;
	}

	public void setListaMensajeError(List<MensajeErrorBean> listaMensajeError) {
		this.listaMensajeError = listaMensajeError;
	}
	


	public String getDescEmpresa() {
		return descEmpresa;
	}

	public void setDescEmpresa(String descEmpresa) {
		this.descEmpresa = descEmpresa;
	}

	public String getMesPresentacion() {
		return mesPresentacion;
	}

	public void setMesPresentacion(String mesPresentacion) {
		this.mesPresentacion = mesPresentacion;
	}

	public String getAnioPresentacion() {
		return anioPresentacion;
	}

	public void setAnioPresentacion(String anioPresentacion) {
		this.anioPresentacion = anioPresentacion;
	}

	public String getMesAlta() {
		return mesAlta;
	}

	public void setMesAlta(String mesAlta) {
		this.mesAlta = mesAlta;
	}

	public String getAnioAlta() {
		return anioAlta;
	}

	public void setAnioAlta(String anioAlta) {
		this.anioAlta = anioAlta;
	}
	
	public String getSt1() {
		return st1;
	}

	public void setSt1(String st1) {
		this.st1 = st1;
	}

	public String getSt2() {
		return st2;
	}

	public void setSt2(String st2) {
		this.st2 = st2;
	}

	public String getSt3() {
		return st3;
	}

	public void setSt3(String st3) {
		this.st3 = st3;
	}

	public String getSt4() {
		return st4;
	}

	public void setSt4(String st4) {
		this.st4 = st4;
	}

	public String getSt5() {
		return st5;
	}

	public void setSt5(String st5) {
		this.st5 = st5;
	}

	public String getSt6() {
		return st6;
	}

	public void setSt6(String st6) {
		this.st6 = st6;
	}

	public String getStser() {
		return stser;
	}

	public void setStser(String stser) {
		this.stser = stser;
	}

	public String getStesp() {
		return stesp;
	}

	public void setStesp(String stesp) {
		this.stesp = stesp;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getIdZonaBenef() {
		return idZonaBenef;
	}

	public void setIdZonaBenef(String idZonaBenef) {
		this.idZonaBenef = idZonaBenef;
	}

	public String getNombreSede() {
		return nombreSede;
	}

	public void setNombreSede(String nombreSede) {
		this.nombreSede = nombreSede;
	}

	public String getCodDepartamento() {
		return codDepartamento;
	}

	public void setCodDepartamento(String codDepartamento) {
		this.codDepartamento = codDepartamento;
	}

	public String getCodProvincia() {
		return codProvincia;
	}

	public void setCodProvincia(String codProvincia) {
		this.codProvincia = codProvincia;
	}

	public String getCodDistrito() {
		return codDistrito;
	}

	public void setCodDistrito(String codDistrito) {
		this.codDistrito = codDistrito;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public Map<Long, String> getListaZonasBenef() {
		return listaZonasBenef;
	}

	public void setListaZonasBenef(Map<Long, String> listaZonasBenef) {
		this.listaZonasBenef = listaZonasBenef;
	}

	public List<AdmUbigeo> getListaDepartamentos() {
		return listaDepartamentos;
	}

	public void setListaDepartamentos(List<AdmUbigeo> listaDepartamentos) {
		this.listaDepartamentos = listaDepartamentos;
	}

	public List<FisePeriodoEnvio> getListaPeriodo() {
		return listaPeriodo;
	}

	public void setListaPeriodo(List<FisePeriodoEnvio> listaPeriodo) {
		this.listaPeriodo = listaPeriodo;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public String getDescripcionPeriodo() {
		return descripcionPeriodo;
	}

	public void setDescripcionPeriodo(String descripcionPeriodo) {
		this.descripcionPeriodo = descripcionPeriodo;
	}


	public String getAnioInicioVigencia() {
		return anioInicioVigencia;
	}

	public void setAnioInicioVigencia(String anioInicioVigencia) {
		this.anioInicioVigencia = anioInicioVigencia;
	}

	public String getAnioFinVigencia() {
		return anioFinVigencia;
	}

	public void setAnioFinVigencia(String anioFinVigencia) {
		this.anioFinVigencia = anioFinVigencia;
	}

	public boolean isReadonlyFlagPeriodo() {
		return readonlyFlagPeriodo;
	}

	public void setReadonlyFlagPeriodo(boolean readonlyFlagPeriodo) {
		this.readonlyFlagPeriodo = readonlyFlagPeriodo;
	}

	public boolean isReadonlyEdit() {
		return readonlyEdit;
	}

	public void setReadonlyEdit(boolean readonlyEdit) {
		this.readonlyEdit = readonlyEdit;
	}

	public int getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(int tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public String getCodEmpresaHidden() {
		return codEmpresaHidden;
	}

	public void setCodEmpresaHidden(String codEmpresaHidden) {
		this.codEmpresaHidden = codEmpresaHidden;
	}

	public String getDescripcionPeriodoHidden() {
		return descripcionPeriodoHidden;
	}

	public void setDescripcionPeriodoHidden(String descripcionPeriodoHidden) {
		this.descripcionPeriodoHidden = descripcionPeriodoHidden;
	}

	public String getDescGrupoInformacion() {
		return descGrupoInformacion;
	}

	public void setDescGrupoInformacion(String descGrupoInformacion) {
		this.descGrupoInformacion = descGrupoInformacion;
	}

	public String getDescestado() {
		return descestado;
	}

	public void setDescestado(String descestado) {
		this.descestado = descestado;
	}

	public String getDescDepartamento() {
		return descDepartamento;
	}

	public void setDescDepartamento(String descDepartamento) {
		this.descDepartamento = descDepartamento;
	}

	public String getDescProvincia() {
		return descProvincia;
	}

	public void setDescProvincia(String descProvincia) {
		this.descProvincia = descProvincia;
	}

	public String getDescDistrito() {
		return descDistrito;
	}

	public void setDescDistrito(String descDistrito) {
		this.descDistrito = descDistrito;
	}

	public String getCodDepartamentoHidden() {
		return codDepartamentoHidden;
	}

	public void setCodDepartamentoHidden(String codDepartamentoHidden) {
		this.codDepartamentoHidden = codDepartamentoHidden;
	}

	public String getCodProvinciaHidden() {
		return codProvinciaHidden;
	}

	public void setCodProvinciaHidden(String codProvinciaHidden) {
		this.codProvinciaHidden = codProvinciaHidden;
	}

	public String getCodDistritoHidden() {
		return codDistritoHidden;
	}

	public void setCodDistritoHidden(String codDistritoHidden) {
		this.codDistritoHidden = codDistritoHidden;
	}

	public String getCodUbigeo() {
		return codUbigeo;
	}

	public void setCodUbigeo(String codUbigeo) {
		this.codUbigeo = codUbigeo;
	}

	public String getCodEdelnor() {
		return codEdelnor;
	}

	public void setCodEdelnor(String codEdelnor) {
		this.codEdelnor = codEdelnor;
	}

	public String getCodLuzSur() {
		return codLuzSur;
	}

	public void setCodLuzSur(String codLuzSur) {
		this.codLuzSur = codLuzSur;
	}

	public String getAnoInicioVigenciaHidden() {
		return anoInicioVigenciaHidden;
	}

	public void setAnoInicioVigenciaHidden(String anoInicioVigenciaHidden) {
		this.anoInicioVigenciaHidden = anoInicioVigenciaHidden;
	}

	public String getAnoFinVigenciaHidden() {
		return anoFinVigenciaHidden;
	}

	public void setAnoFinVigenciaHidden(String anoFinVigenciaHidden) {
		this.anoFinVigenciaHidden = anoFinVigenciaHidden;
	}

	public String getIdGrupoInfo() {
		return idGrupoInfo;
	}

	public void setIdGrupoInfo(String idGrupoInfo) {
		this.idGrupoInfo = idGrupoInfo;
	}

	public Integer getTypeFile() {
		return typeFile;
	}

	public void setTypeFile(Integer typeFile) {
		this.typeFile = typeFile;
	}

	
	

}
