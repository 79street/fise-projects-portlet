package gob.osinergmin.fise.gart.command;

import gob.osinergmin.fise.domain.AdmEmpresa;
import gob.osinergmin.fise.domain.FisePeriodoEnvio;
import gob.osinergmin.fise.domain.FisePeriodoEnvioPK;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;



public class PeriodoEnvioCommand implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private Map<Long,String> listaMes;
	private List<AdmEmpresa> listaEmpresas;
	
	private String mesInicio;
	private String anioInicio;
	private String mesFin;
	private String anioFin;
	private String habilitadoEnvio;
	
	private long secuencia;
	private String codEmpresa;
    private long anoPresentacion;
    private long mesPresentacion;
    private String formato;
    private String etapa;
    
    private Date desde;
    private Date hasta;
    private Integer diasNotificacionAntesCierre;
    private String flagEnvioConObservaciones;
    
    private String ceginroexp;
    private String ceginrotra;
    private String ceginrodoc;
    private Date cegifecenv;
    private Date cegifecrecosi;
    private Date cegifecrecgart;
    private String ceginrodocobs;
    private Date cegifecdocobs;
    private Date cegifecenvdocobs;
    private Date cegifecrecnotobs;
    private Date cegifecemanotobs;
    private Date cegifecvenlvtoobs;
	
    private String usuarioCreacion;
	private String terminalCreacion;
	private Date fechaCreacion;
	
	private String usuarioActualizacion;
	private String terminalActualizacion;
	private Date fechaActualizacion;
	private String flagMostrarAnoMesEjec;
	
	public static FisePeriodoEnvio toBean(PeriodoEnvioCommand command){
		FisePeriodoEnvio bean=new FisePeriodoEnvio();
		FisePeriodoEnvioPK pk=new FisePeriodoEnvioPK();
		pk.setSecuencia(command.getSecuencia());
		pk.setAnoPresentacion(command.getAnoPresentacion());
		pk.setCodEmpresa(command.getCodEmpresa());
		pk.setEtapa(command.getEtapa());
		pk.setFormato(command.getFormato());
		pk.setMesPresentacion(command.getMesPresentacion());
		bean.setId(pk);
		bean.setDesde(command.getDesde());
		bean.setHasta(command.getHasta());
		bean.setDiasNotificacionAntesCierre(command.getDiasNotificacionAntesCierre());
		bean.setFlagEnvioConObservaciones(command.getFlagEnvioConObservaciones());
		bean.setCeginroexp(command.getCeginroexp());
		bean.setCeginrotra(command.getCeginrotra());
		bean.setCeginrodoc(command.getCeginrodoc());
		bean.setCegifecenv(command.getCegifecenv());
		bean.setCegifecrecosi(command.getCegifecrecosi());
		bean.setCegifecrecgart(command.getCegifecrecgart());
		bean.setCeginrodocobs(command.getCeginrodocobs());
		bean.setCegifecdocobs(command.getCegifecdocobs());
		bean.setCegifecenvdocobs(command.getCegifecenvdocobs());
		bean.setCegifecrecnotobs(command.getCegifecrecnotobs());
		bean.setCegifecemanotobs(command.getCegifecemanotobs());
		bean.setCegifecvenlvtoobs(command.getCegifecvenlvtoobs());
		bean.setUsuarioCreacion(command.getUsuarioCreacion());
		bean.setTerminalCreacion(command.getTerminalCreacion());
		bean.setFechaCreacion(command.getFechaCreacion());
		bean.setUsuarioActualizacion(command.getUsuarioActualizacion());
		bean.setTerminalActualizacion(command.getTerminalActualizacion());
		bean.setFechaActualizacion(command.getFechaActualizacion());
		bean.setFlagMostrarAnoMesEjec(command.getFlagMostrarAnoMesEjec());
		return bean;
	}
	
	public static List<PeriodoEnvioCommand> toListCommand(List<FisePeriodoEnvio> lst){
		List<PeriodoEnvioCommand> lstRetorno=new ArrayList<PeriodoEnvioCommand>();
		if(lst!=null && !lst.isEmpty()){
			for(FisePeriodoEnvio bean:lst){
				lstRetorno.add(PeriodoEnvioCommand.toCommand(bean));
			}
			
		}
		return lstRetorno;
	}
	
	
	public static PeriodoEnvioCommand toCommand(FisePeriodoEnvio bean){
		PeriodoEnvioCommand command=new PeriodoEnvioCommand();
		command.setSecuencia(bean.getId().getSecuencia());
		command.setAnoPresentacion(bean.getId().getAnoPresentacion());
		command.setCodEmpresa(bean.getId().getCodEmpresa());
		command.setEtapa(bean.getId().getEtapa());
		command.setFormato(bean.getId().getFormato());
		command.setMesPresentacion(bean.getId().getMesPresentacion());
		command.setDesde(bean.getDesde());
		command.setHasta(bean.getHasta());
		command.setDiasNotificacionAntesCierre(bean.getDiasNotificacionAntesCierre());
		command.setFlagEnvioConObservaciones(bean.getFlagEnvioConObservaciones());
		command.setCeginroexp(bean.getCeginroexp());
		command.setCeginrotra(bean.getCeginrotra());
		command.setCeginrodoc(bean.getCeginrodoc());
		command.setCegifecenv(bean.getCegifecenv());
		command.setCegifecrecosi(bean.getCegifecrecosi());
		command.setCegifecrecgart(bean.getCegifecrecgart());
		command.setCeginrodocobs(bean.getCeginrodocobs());
		command.setCegifecdocobs(bean.getCegifecdocobs());
		command.setCegifecenvdocobs(bean.getCegifecenvdocobs());
		command.setCegifecrecnotobs(bean.getCegifecrecnotobs());
		command.setCegifecemanotobs(bean.getCegifecemanotobs());
		command.setCegifecvenlvtoobs(bean.getCegifecvenlvtoobs());
		command.setUsuarioCreacion(bean.getUsuarioCreacion());
		command.setTerminalCreacion(bean.getTerminalCreacion());
		command.setFechaCreacion(bean.getFechaCreacion());
		command.setUsuarioActualizacion(bean.getUsuarioActualizacion());
		command.setTerminalActualizacion(bean.getTerminalActualizacion());
		command.setFechaActualizacion(bean.getFechaActualizacion());
		command.setFlagMostrarAnoMesEjec(bean.getFlagMostrarAnoMesEjec());
		return command;
	}
	
	public Map<Long, String> getListaMes() {
		return listaMes;
	}
	public void setListaMes(Map<Long, String> listaMes) {
		this.listaMes = listaMes;
	}
	public List<AdmEmpresa> getListaEmpresas() {
		return listaEmpresas;
	}
	public void setListaEmpresas(List<AdmEmpresa> listaEmpresas) {
		this.listaEmpresas = listaEmpresas;
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
	public String getHabilitadoEnvio() {
		return habilitadoEnvio;
	}
	public void setHabilitadoEnvio(String habilitadoEnvio) {
		this.habilitadoEnvio = habilitadoEnvio;
	}
	public long getSecuencia() {
		return secuencia;
	}
	public void setSecuencia(long secuencia) {
		this.secuencia = secuencia;
	}
	public String getCodEmpresa() {
		return codEmpresa;
	}
	public void setCodEmpresa(String codEmpresa) {
		this.codEmpresa = codEmpresa;
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
	public String getFormato() {
		return formato;
	}
	public void setFormato(String formato) {
		this.formato = formato;
	}
	public String getEtapa() {
		return etapa;
	}
	public void setEtapa(String etapa) {
		this.etapa = etapa;
	}
	public Date getDesde() {
		return desde;
	}
	public void setDesde(Date desde) {
		this.desde = desde;
	}
	public Date getHasta() {
		return hasta;
	}
	public void setHasta(Date hasta) {
		this.hasta = hasta;
	}
	public Integer getDiasNotificacionAntesCierre() {
		return diasNotificacionAntesCierre;
	}
	public void setDiasNotificacionAntesCierre(Integer diasNotificacionAntesCierre) {
		this.diasNotificacionAntesCierre = diasNotificacionAntesCierre;
	}
	public String getFlagEnvioConObservaciones() {
		return flagEnvioConObservaciones;
	}
	public void setFlagEnvioConObservaciones(String flagEnvioConObservaciones) {
		this.flagEnvioConObservaciones = flagEnvioConObservaciones;
	}
	public String getCeginroexp() {
		return ceginroexp;
	}
	public void setCeginroexp(String ceginroexp) {
		this.ceginroexp = ceginroexp;
	}
	public String getCeginrotra() {
		return ceginrotra;
	}
	public void setCeginrotra(String ceginrotra) {
		this.ceginrotra = ceginrotra;
	}
	public String getCeginrodoc() {
		return ceginrodoc;
	}
	public void setCeginrodoc(String ceginrodoc) {
		this.ceginrodoc = ceginrodoc;
	}
	public Date getCegifecenv() {
		return cegifecenv;
	}
	public void setCegifecenv(Date cegifecenv) {
		this.cegifecenv = cegifecenv;
	}
	public Date getCegifecrecosi() {
		return cegifecrecosi;
	}
	public void setCegifecrecosi(Date cegifecrecosi) {
		this.cegifecrecosi = cegifecrecosi;
	}
	public Date getCegifecrecgart() {
		return cegifecrecgart;
	}
	public void setCegifecrecgart(Date cegifecrecgart) {
		this.cegifecrecgart = cegifecrecgart;
	}
	public String getCeginrodocobs() {
		return ceginrodocobs;
	}
	public void setCeginrodocobs(String ceginrodocobs) {
		this.ceginrodocobs = ceginrodocobs;
	}
	public Date getCegifecdocobs() {
		return cegifecdocobs;
	}
	public void setCegifecdocobs(Date cegifecdocobs) {
		this.cegifecdocobs = cegifecdocobs;
	}
	public Date getCegifecenvdocobs() {
		return cegifecenvdocobs;
	}
	public void setCegifecenvdocobs(Date cegifecenvdocobs) {
		this.cegifecenvdocobs = cegifecenvdocobs;
	}
	public Date getCegifecrecnotobs() {
		return cegifecrecnotobs;
	}
	public void setCegifecrecnotobs(Date cegifecrecnotobs) {
		this.cegifecrecnotobs = cegifecrecnotobs;
	}
	public Date getCegifecemanotobs() {
		return cegifecemanotobs;
	}
	public void setCegifecemanotobs(Date cegifecemanotobs) {
		this.cegifecemanotobs = cegifecemanotobs;
	}
	public Date getCegifecvenlvtoobs() {
		return cegifecvenlvtoobs;
	}
	public void setCegifecvenlvtoobs(Date cegifecvenlvtoobs) {
		this.cegifecvenlvtoobs = cegifecvenlvtoobs;
	}
	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}
	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}
	public String getTerminalCreacion() {
		return terminalCreacion;
	}
	public void setTerminalCreacion(String terminalCreacion) {
		this.terminalCreacion = terminalCreacion;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getUsuarioActualizacion() {
		return usuarioActualizacion;
	}
	public void setUsuarioActualizacion(String usuarioActualizacion) {
		this.usuarioActualizacion = usuarioActualizacion;
	}
	public String getTerminalActualizacion() {
		return terminalActualizacion;
	}
	public void setTerminalActualizacion(String terminalActualizacion) {
		this.terminalActualizacion = terminalActualizacion;
	}
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	public String getFlagMostrarAnoMesEjec() {
		return flagMostrarAnoMesEjec;
	}
	public void setFlagMostrarAnoMesEjec(String flagMostrarAnoMesEjec) {
		this.flagMostrarAnoMesEjec = flagMostrarAnoMesEjec;
	}
	
	
	

}
