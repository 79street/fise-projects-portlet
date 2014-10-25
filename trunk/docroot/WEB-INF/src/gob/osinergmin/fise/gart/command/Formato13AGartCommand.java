package gob.osinergmin.fise.gart.command;

import gob.osinergmin.fise.domain.AdmEmpresa;
import gob.osinergmin.fise.util.FechaUtil;

import java.util.List;
import java.util.Map;

public class Formato13AGartCommand {

	private Map<Long,String> listaMes;
	private List<AdmEmpresa> listaEmpresas;
	private String codEmpresa;
	private String mesInicio;
	private String anioInicio;
	private String mesFin;
	private String anioFin;
	private String etapa;
	private boolean admin;
	
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

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	
}
