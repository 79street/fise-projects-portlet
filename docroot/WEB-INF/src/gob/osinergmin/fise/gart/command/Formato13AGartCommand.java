package gob.osinergmin.fise.gart.command;

import gob.osinergmin.fise.domain.AdmEmpresa;

import java.util.List;
import java.util.Map;

public class Formato13AGartCommand {

	private Map<Long,String> listaMes;
	private List<AdmEmpresa> listaEmpresas;
	private String codEmpresa;
	private Long mesInicio;
	private Long anioInicio;
	private Long mesFin;
	private Long anioFin;
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

	public Long getMesInicio() {
		return mesInicio;
	}

	public void setMesInicio(Long mesInicio) {
		this.mesInicio = mesInicio;
	}

	public Long getAnioInicio() {
		return anioInicio;
	}

	public void setAnioInicio(Long anioInicio) {
		this.anioInicio = anioInicio;
	}

	public Long getMesFin() {
		return mesFin;
	}

	public void setMesFin(Long mesFin) {
		this.mesFin = mesFin;
	}

	public Long getAnioFin() {
		return anioFin;
	}

	public void setAnioFin(Long anioFin) {
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
