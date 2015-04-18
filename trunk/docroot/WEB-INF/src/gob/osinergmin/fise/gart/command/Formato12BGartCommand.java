package gob.osinergmin.fise.gart.command;

import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.AdmEmpresa;
import gob.osinergmin.fise.domain.FiseFormato12BC;
import gob.osinergmin.fise.domain.FiseFormato12BCPK;
import gob.osinergmin.fise.domain.FiseFormato12BD;
import gob.osinergmin.fise.domain.FiseFormato12BDPK;
import gob.osinergmin.fise.domain.FiseGrupoInformacion;
import gob.osinergmin.fise.domain.FisePeriodoEnvio;
import gob.osinergmin.fise.gart.service.CommonGartService;
import gob.osinergmin.fise.util.FormatoUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Formato12BGartCommand implements Serializable {

	private static final long serialVersionUID = 1L;

	// BUSQUEDA
	private Map<Long, String> listaMes;
	private Map<Long, String> listaZonasBenef;
	private List<AdmEmpresa> listaEmpresas;
	private Integer mesInicio;
	private Integer anioInicio;
	private Integer mesFin;
	private Integer anioFin;

	private List<FisePeriodoEnvio> listaPeriodo;
	private String peridoDeclaracion;
	private String descEmpresa;
	private String descGrupo;
	private String descEstado;
	private String descMes;
	private String descMesEjec;

	private String codEmpresa;
	private String codEmpresaBusqueda;

	private Integer anoPresentacion;
	private Integer mesPresentacion;
	private Integer anoEjecucionGasto;
	private Integer mesEjecucionGasto;
	private String etapa;
	private String etapaBusqueda;

	private Integer idZonaBenef;

	private Date fechaEnvioDefinitivo;
	private String strfechaEnvioDefinitivo;
	private String nombreArchivoExcel;
	private String nombreArchivoTexto;

	private String usuarioActualizacion;
	private String usuarioCreacion;
	private Date fechaActualizacion;
	private Date fechaCreacion;
	private String terminalCreacion;
	private String terminalActualizacion;
	private BigDecimal totalReconocer;

	private Integer idGrupoInf;

	// RURAL
	private BigDecimal costoEstandarUnitAtencion;
	private BigDecimal costoEstandarUnitValDgCan;
	private BigDecimal costoEstandarUnitValDisEl;
	private BigDecimal costoEstandarUnitValFiCan;
	private BigDecimal costoEstandarUnitValeImpre;
	private BigDecimal costoEstandarUnitValeRepar;
	private BigDecimal costoTotalAtencionConsRecl;
	private BigDecimal costoTotalCanjeLiqValeDig;
	private BigDecimal costoTotalCanjeLiqValeFis;
	private BigDecimal costoTotalEntregaValDisEl;
	private BigDecimal costoTotalImpresionVale;
	private BigDecimal costoTotalRepartoValesDomi;
	private Integer numeroAtenciones;
	private Integer numeroValesDigitalCanjeados;
	private Integer numeroValesEntregadoDisEl;
	private Integer numeroValesFisicosCanjeados;
	private Integer numeroValesImpreso;
	private Integer numeroValesRepartidosDomi;
	private BigDecimal totalActividadesExtraord;
	private BigDecimal totalDesplazamientoPersonal;
	private BigDecimal totalGestionAdministrativa;

	// PROVINCIA
	private BigDecimal costoEstandarUnitAtencionProv;
	private BigDecimal costoEstandarUnitValDgCanProv;
	private BigDecimal costoEstandarUnitValDisElProv;
	private BigDecimal costoEstandarUnitValFiCanProv;
	private BigDecimal costoEstandarUnitValeImpreProv;
	private BigDecimal costoEstandarUnitValeReparProv;
	private BigDecimal costoTotalAtencionConsReclProv;
	private BigDecimal costoTotalCanjeLiqValeDigProv;
	private BigDecimal costoTotalCanjeLiqValeFisProv;
	private BigDecimal costoTotalEntregaValDisElProv;
	private BigDecimal costoTotalImpresionValeProv;
	private BigDecimal costoTotalRepartoValesDomiProv;
	private Integer numeroAtencionesProv;
	private Integer numeroValesDigitalCanjeadosProv;
	private Integer numeroValesEntregadoDisElProv;
	private Integer numeroValesFisicosCanjeadosProv;
	private Integer numeroValesImpresoProv;
	private Integer numeroValesRepartidosDomiProv;
	private BigDecimal totalActividadesExtraordProv;
	private BigDecimal totalDesplazamientoPersonalProv;
	private BigDecimal totalGestionAdministrativaProv;

	// LIMA
	private BigDecimal costoEstandarUnitAtencionLim;
	private BigDecimal costoEstandarUnitValDgCanLim;
	private BigDecimal costoEstandarUnitValDisElLim;
	private BigDecimal costoEstandarUnitValFiCanLim;
	private BigDecimal costoEstandarUnitValeImpreLim;
	private BigDecimal costoEstandarUnitValeReparLim;
	private BigDecimal costoTotalAtencionConsReclLim;
	private BigDecimal costoTotalCanjeLiqValeDigLim;
	private BigDecimal costoTotalCanjeLiqValeFisLim;
	private BigDecimal costoTotalEntregaValDisElLim;
	private BigDecimal costoTotalImpresionValeLim;
	private BigDecimal costoTotalRepartoValesDomiLim;
	private Integer numeroAtencionesLim;
	private Integer numeroValesDigitalCanjeadosLim;
	private Integer numeroValesEntregadoDisElLim;
	private Integer numeroValesFisicosCanjeadosLim;
	private Integer numeroValesImpresoLim;
	private Integer numeroValesRepartidosDomiLim;
	private BigDecimal totalActividadesExtraordLim;
	private BigDecimal totalDesplazamientoPersonalLim;
	private BigDecimal totalGestionAdministrativaLim;

	private Integer tipoOperacion;
	private String codEmpresaHidden;
	private String peridoDeclaracionHidden;

	private Integer estadoEnvio;
	private String estadoProceso;

	private BigDecimal porImpresionVales;
	private BigDecimal porRepartoDom;
	private BigDecimal porEntregaValesDE;
	private BigDecimal porValesFisicos;
	private BigDecimal porValesDigitales;
	private BigDecimal porAtencionReclamos;
	private BigDecimal porGestionAdm;
	private BigDecimal porDesplazamientoPers;
	private BigDecimal porActividadExtra;
	private BigDecimal totalGeneralReconocer;
	
	private Integer typeFile;

	private String idGrupoInfo;
	
	private String etapaFinal;
	
	private String flagPeriodoEjecucion;
	
	public static FiseFormato12BC toBeanCabecera(Formato12BGartCommand command) {

		FiseFormato12BC bean = null;
		try {
			if (command != null) {
				bean = new FiseFormato12BC();
				FiseFormato12BCPK pk = new FiseFormato12BCPK();
				pk.setAnoEjecucionGasto(command.getAnoEjecucionGasto());
				pk.setAnoPresentacion(command.getAnoPresentacion());
				pk.setCodEmpresa(command.getCodEmpresa());
				pk.setEtapa(command.getEtapa());
				pk.setMesEjecucionGasto(command.getMesEjecucionGasto());
				pk.setMesPresentacion(command.getMesPresentacion());
				bean.setId(pk);

				bean.setFechaActualizacion(command.getFechaActualizacion());
				bean.setFechaCreacion(command.getFechaCreacion());
				bean.setFechaEnvioDefinitivo(command.getFechaEnvioDefinitivo());
				FiseGrupoInformacion grupo = new FiseGrupoInformacion();
				grupo.setIdGrupoInformacion(command.getIdGrupoInf().longValue());
				bean.setFiseGrupoInformacion(grupo);
				bean.setNombreArchivoExcel(command.getNombreArchivoExcel());
				bean.setNombreArchivoTexto(command.getNombreArchivoTexto());
				bean.setTerminalActualizacion(command.getTerminalActualizacion());
				bean.setTerminalCreacion(command.getTerminalCreacion());
				bean.setTotalReconocer(command.getTotalReconocer());
				bean.setUsuarioActualizacion(command.getUsuarioActualizacion());
				bean.setUsuarioCreacion(command.getUsuarioCreacion());
			}
		} catch (Exception e) {

		}
		return bean;
	}

	public static Formato12BGartCommand toCommandCabecera(FiseFormato12BC bean,Formato12BGartCommand busqueda) {
		Formato12BGartCommand command = null;
		try {

			if (bean != null) {
				command = new Formato12BGartCommand();
				
				if(busqueda!=null){
					command.setAnioInicio(busqueda.getAnioInicio());
					command.setAnioFin(busqueda.getAnioFin());
					command.setMesInicio(busqueda.getMesInicio());
					command.setMesFin(busqueda.getMesFin());
					command.setEtapaBusqueda(busqueda.getEtapaBusqueda());
					command.setCodEmpresaBusqueda(busqueda.getCodEmpresaBusqueda()!=null?busqueda.getCodEmpresaBusqueda().trim():null);
					command.setDescEstado(busqueda.getDescEstado());
				}

				command.setAnoEjecucionGasto(bean.getId().getAnoEjecucionGasto());
				command.setAnoPresentacion(bean.getId().getAnoPresentacion());
				command.setCodEmpresa(bean.getId().getCodEmpresa().trim());
				command.setCodEmpresaHidden(bean.getId().getCodEmpresa().trim());
				command.setDescEmpresa(bean.getAdmEmpresa() != null ? bean.getAdmEmpresa().getDscCortaEmpresa() : "");
				command.setEtapa(bean.getId().getEtapa());
				command.setMesEjecucionGasto(bean.getId().getMesEjecucionGasto());
				command.setMesPresentacion(bean.getId().getMesPresentacion());
				command.setDescMes((bean.getId().getMesPresentacion() != null && bean.getId().getMesPresentacion() > 0) ? FiseUtil.descripcionMes(bean.getId().getMesPresentacion()) : "");
				command.setDescMesEjec((bean.getId().getMesEjecucionGasto() != null && bean.getId().getMesEjecucionGasto() > 0) ? FiseUtil.descripcionMes(bean.getId().getMesEjecucionGasto()) : "");

				String mesPres = ((bean.getId().getMesPresentacion() + "").length() > 1 ? bean.getId().getMesPresentacion() + "" : "0" + bean.getId().getMesPresentacion());
				command.setPeridoDeclaracion(bean.getId().getAnoPresentacion() + "" + mesPres + bean.getId().getEtapa());
				command.setPeridoDeclaracionHidden(bean.getId().getAnoPresentacion() + mesPres + bean.getId().getEtapa());

				command.setFechaActualizacion(bean.getFechaActualizacion());
				command.setFechaCreacion(bean.getFechaCreacion());
				command.setFechaEnvioDefinitivo(bean.getFechaEnvioDefinitivo());
				command.setStrfechaEnvioDefinitivo(FiseUtil.toStringFormat(bean.getFechaEnvioDefinitivo(), FiseUtil.FORMATO_DDMMYYYY));
				command.setIdGrupoInf(bean.getFiseGrupoInformacion() != null ? bean.getFiseGrupoInformacion().getIdGrupoInformacion().intValue() : null);
				command.setDescGrupo(bean.getFiseGrupoInformacion() != null ? bean.getFiseGrupoInformacion().getDescripcion() : "");
				command.setNombreArchivoExcel(bean.getNombreArchivoExcel());
				command.setNombreArchivoTexto(bean.getNombreArchivoTexto());
				command.setTerminalActualizacion(bean.getTerminalActualizacion());
				command.setTerminalCreacion(bean.getTerminalCreacion());
				command.setTotalReconocer(bean.getTotalReconocer());
				command.setUsuarioActualizacion(bean.getUsuarioActualizacion());
				command.setUsuarioCreacion(bean.getUsuarioCreacion());
				//command.setDescEstado(bean.getFechaEnvioDefinitivo() != null ? FiseConstants.ESTADO_FECHAENVIO_ENVIADO : FiseConstants.ESTADO_FECHAENVIO_POR_ENVIAR);
				command.setDescEstado(FormatoUtil.cambiaTextoAMinusculas(command.getDescEstado(), 0));
				command.setEstadoEnvio(bean.getFechaEnvioDefinitivo() != null ? FiseConstants.ESTADO_ENVIADO : FiseConstants.ESTADO_POR_ENVIAR);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return command;
	}

	public static JSONObject toJSONCabecera(FiseFormato12BC bean, CommonGartService commonService) throws JSONException {
		JSONObject jsonObj = null;
		if (bean != null) {
			jsonObj = new JSONObject();

			String estadoProceso = commonService.obtenerEstadoProceso(bean.getId().getCodEmpresa(), FiseConstants.TIPO_FORMATO_12B, bean.getId().getAnoPresentacion(), bean.getId().getMesPresentacion(), bean.getId().getEtapa());
			
			jsonObj.put("anoEjecucionGasto", bean.getId().getAnoEjecucionGasto());
			jsonObj.put("anoPresentacion", bean.getId().getAnoPresentacion());
			jsonObj.put("codEmpresa", bean.getId().getCodEmpresa());
			jsonObj.put("descEmpresa", bean.getAdmEmpresa() != null ? bean.getAdmEmpresa().getDscCortaEmpresa() : "");
			jsonObj.put("etapa", bean.getId().getEtapa());
			jsonObj.put("mesEjecucionGasto", bean.getId().getMesEjecucionGasto());
			jsonObj.put("mesPresentacion", bean.getId().getMesPresentacion());
			jsonObj.put("strfechaEnvioDefinitivo", bean.getFechaEnvioDefinitivo() != null ? FiseUtil.toStringFormat(bean.getFechaEnvioDefinitivo(), FiseUtil.FORMATO_DDMMYYYY) : "");
			jsonObj.put("idGrupoInf", bean.getFiseGrupoInformacion() != null ? bean.getFiseGrupoInformacion().getIdGrupoInformacion().intValue() : null);
			jsonObj.put("descGrupo", bean.getFiseGrupoInformacion() != null ? bean.getFiseGrupoInformacion().getDescripcion() : "");
			jsonObj.put("nombreArchivoExcel", bean.getNombreArchivoExcel());
			jsonObj.put("nombreArchivoTexto", bean.getNombreArchivoTexto());
			jsonObj.put("totalReconocer", bean.getTotalReconocer());
			//jsonObj.put("descEstado", bean.getFechaEnvioDefinitivo() != null ? FiseConstants.ESTADO_FECHAENVIO_ENVIADO : FiseConstants.ESTADO_FECHAENVIO_POR_ENVIAR);
			jsonObj.put("descEstado", FormatoUtil.cambiaTextoAMinusculas(estadoProceso, 0));
			jsonObj.put("descMes", (bean.getId().getMesPresentacion() != null && bean.getId().getMesPresentacion() > 0) ? FiseUtil.descripcionMes(bean.getId().getMesPresentacion()) : "");
			jsonObj.put("estadoEnvio", bean.getFechaEnvioDefinitivo() != null ? FiseConstants.ESTADO_ENVIADO : FiseConstants.ESTADO_POR_ENVIAR);
			//jsonObj.put("estadoProceso", commonService.obtenerEstadoProceso(bean.getId().getCodEmpresa(), FiseConstants.TIPO_FORMATO_12B, bean.getId().getAnoPresentacion(), bean.getId().getMesPresentacion(), bean.getId().getEtapa()));
			jsonObj.put("estadoProceso", estadoProceso);
			jsonObj.put("mesEjecucionGasto", bean.getId().getMesEjecucionGasto());
			jsonObj.put("descMesEjec", (bean.getId().getMesEjecucionGasto() != null && bean.getId().getMesEjecucionGasto() > 0) ? FiseUtil.descripcionMes(bean.getId().getMesEjecucionGasto()) : "");
			jsonObj.put("anoEjecucionGasto", bean.getId().getAnoEjecucionGasto());

			//System.out.println("estado:" + commonService.obtenerEstadoProceso(bean.getId().getCodEmpresa(), FiseConstants.TIPO_FORMATO_12B, bean.getId().getAnoPresentacion(), bean.getId().getMesPresentacion(), bean.getId().getEtapa()));
			System.out.println("estado:" + estadoProceso);
		}
		return jsonObj;

	}

	public static List<Formato12BGartCommand> toListCommandCabecera(List<FiseFormato12BC> lst) {
		List<Formato12BGartCommand> lstReturn = null;
		try {
			if (lst != null && !lst.isEmpty()) {
				lstReturn = new ArrayList<Formato12BGartCommand>();
				for (FiseFormato12BC bean : lst) {
					lstReturn.add(toCommandCabecera(bean,null));

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lstReturn;
	}

	public static JSONArray toListJSONCabecera(List<FiseFormato12BC> lst, CommonGartService commonService) throws JSONException {
		JSONArray lstReturn = null;

		if (lst != null && !lst.isEmpty()) {
			lstReturn = new JSONArray();
			for (FiseFormato12BC bean : lst) {
				lstReturn.put(toJSONCabecera(bean, commonService));

			}
		}
		return lstReturn;
	}


	public static Formato12BGartCommand toCommandDetalle(List<FiseFormato12BD> lst, Formato12BGartCommand command) {

		try {

			if (lst != null && !lst.isEmpty()) {
				if (command == null) {
					command = new Formato12BGartCommand();

					command.setAnoEjecucionGasto(lst.get(0).getId().getAnoEjecucionGasto());
					command.setAnoPresentacion(lst.get(0).getId().getAnoPresentacion());
					command.setCodEmpresa(lst.get(0).getId().getCodEmpresa().trim());
					command.setEtapa(lst.get(0).getId().getEtapa());
					command.setMesEjecucionGasto(lst.get(0).getId().getMesEjecucionGasto());
					command.setMesPresentacion(lst.get(0).getId().getMesPresentacion());
					command.setIdZonaBenef(lst.get(0).getId().getIdZonaBenef());

					String mesPres = ((lst.get(0).getId().getMesPresentacion() + "").length() > 1 ? lst.get(0).getId().getMesPresentacion() + "" : "0" + lst.get(0).getId().getMesPresentacion());
					command.setPeridoDeclaracion(lst.get(0).getId().getAnoPresentacion() + mesPres + lst.get(0).getId().getEtapa());
					command.setPeridoDeclaracionHidden(lst.get(0).getId().getAnoPresentacion() + mesPres + lst.get(0).getId().getEtapa());

					command.setDescGrupo(lst.get(0).getFiseFormato12BC().getFiseGrupoInformacion() != null ? lst.get(0).getFiseFormato12BC().getFiseGrupoInformacion().getDescripcion() : "");
					command.setCodEmpresaHidden(lst.get(0).getId().getCodEmpresa());
					command.setDescEmpresa(lst.get(0).getFiseFormato12BC() != null ? lst.get(0).getFiseFormato12BC().getAdmEmpresa().getDscCortaEmpresa() : "");

					command.setFechaActualizacion(lst.get(0).getFechaActualizacion());
					command.setFechaCreacion(lst.get(0).getFechaCreacion());
					command.setTerminalActualizacion(lst.get(0).getTerminalActualizacion());
					command.setTerminalCreacion(lst.get(0).getTerminalCreacion());
					command.setTotalReconocer(lst.get(0).getTotalReconocer());
					command.setUsuarioActualizacion(lst.get(0).getUsuarioActualizacion());
					command.setUsuarioCreacion(lst.get(0).getUsuarioCreacion());

					command.setDescMes((lst.get(0).getId().getMesPresentacion() != null && lst.get(0).getId().getMesPresentacion() > 0) ? FiseUtil.descripcionMes(lst.get(0).getId().getMesPresentacion()) : "");
					command.setDescMesEjec((lst.get(0).getId().getMesEjecucionGasto() != null && lst.get(0).getId().getMesEjecucionGasto() > 0) ? FiseUtil.descripcionMes(lst.get(0).getId().getMesEjecucionGasto()) : "");

				}

				for (FiseFormato12BD bean : lst) {
					System.out.println("ID ZONA:  "+bean.getId().getIdZonaBenef()); 
					if (bean.getId().getIdZonaBenef() == FiseConstants.ZONA_RURAL) {
						System.out.println("ID ZONA costo unitario :  "+bean.getCostoEstandarUnitAtencion()); 
						command.setCostoEstandarUnitAtencion(bean.getCostoEstandarUnitAtencion()!=null?bean.getCostoEstandarUnitAtencion().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setCostoEstandarUnitValDgCan(bean.getCostoEstandarUnitValDgCan()!=null?bean.getCostoEstandarUnitValDgCan().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setCostoEstandarUnitValDisEl(bean.getCostoEstandarUnitValDisEl()!=null?bean.getCostoEstandarUnitValDisEl().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setCostoEstandarUnitValeImpre(bean.getCostoEstandarUnitValeImpre()!=null?bean.getCostoEstandarUnitValeImpre().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setCostoEstandarUnitValeRepar(bean.getCostoEstandarUnitValeRepar()!=null?bean.getCostoEstandarUnitValeRepar().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setCostoEstandarUnitValFiCan(bean.getCostoEstandarUnitValFiCan()!=null?bean.getCostoEstandarUnitValFiCan().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));

						command.setCostoTotalAtencionConsRecl(bean.getCostoTotalAtencionConsRecl()!=null?bean.getCostoTotalAtencionConsRecl().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setCostoTotalCanjeLiqValeDig(bean.getCostoTotalCanjeLiqValeDig()!=null?bean.getCostoTotalCanjeLiqValeDig().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setCostoTotalCanjeLiqValeFis(bean.getCostoTotalCanjeLiqValeFis()!=null?bean.getCostoTotalCanjeLiqValeFis().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setCostoTotalEntregaValDisEl(bean.getCostoTotalEntregaValDisEl()!=null?bean.getCostoTotalEntregaValDisEl().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setCostoTotalImpresionVale(bean.getCostoTotalImpresionVale()!=null?bean.getCostoTotalImpresionVale().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setCostoTotalRepartoValesDomi(bean.getCostoTotalRepartoValesDomi()!=null?bean.getCostoTotalRepartoValesDomi().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));

						command.setNumeroAtenciones(bean.getNumeroAtenciones());
						command.setNumeroValesDigitalCanjeados(bean.getNumeroValesDigitalCanjeados());
						command.setNumeroValesEntregadoDisEl(bean.getNumeroValesEntregadoDisEl());
						command.setNumeroValesFisicosCanjeados(bean.getNumeroValesFisicosCanjeados());
						command.setNumeroValesImpreso(bean.getNumeroValesImpreso());
						command.setNumeroValesRepartidosDomi(bean.getNumeroValesRepartidosDomi());

						command.setTotalActividadesExtraord(bean.getTotalActividadesExtraord()!=null?bean.getTotalActividadesExtraord().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setTotalDesplazamientoPersonal(bean.getTotalDesplazamientoPersonal()!=null?bean.getTotalDesplazamientoPersonal().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setTotalGestionAdministrativa(bean.getTotalGestionAdministrativa()!=null?bean.getTotalGestionAdministrativa().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));

					/*	command.getCostoEstandarUnitAtencion().setScale(2, BigDecimal.ROUND_DOWN);
						command.getCostoEstandarUnitValDgCan().setScale(2, BigDecimal.ROUND_DOWN);
						command.getCostoEstandarUnitValDisEl().setScale(2, BigDecimal.ROUND_DOWN);
						command.getCostoEstandarUnitValeImpre().setScale(2, BigDecimal.ROUND_DOWN);
						command.getCostoEstandarUnitValeRepar().setScale(2, BigDecimal.ROUND_DOWN);
						command.getCostoEstandarUnitValFiCan().setScale(2, BigDecimal.ROUND_DOWN);
						command.getCostoTotalAtencionConsRecl().setScale(2, BigDecimal.ROUND_DOWN);
						command.getCostoTotalCanjeLiqValeDig().setScale(2, BigDecimal.ROUND_DOWN);
						command.getCostoTotalCanjeLiqValeFis().setScale(2, BigDecimal.ROUND_DOWN);
						command.getCostoTotalEntregaValDisEl().setScale(2, BigDecimal.ROUND_UP);
						command.getCostoTotalImpresionVale().setScale(2, BigDecimal.ROUND_DOWN);
						command.getCostoTotalRepartoValesDomi().setScale(2, BigDecimal.ROUND_DOWN);
						command.getTotalActividadesExtraord().setScale(2, BigDecimal.ROUND_DOWN);
						command.getTotalDesplazamientoPersonal().setScale(2, BigDecimal.ROUND_DOWN);
						command.getTotalGestionAdministrativa().setScale(2, BigDecimal.ROUND_DOWN);*/

					}
					if (bean.getId().getIdZonaBenef() == FiseConstants.ZONA_PROVINCIA) {
						command.setCostoEstandarUnitAtencionProv(bean.getCostoEstandarUnitAtencion()!=null?bean.getCostoEstandarUnitAtencion().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setCostoEstandarUnitValDgCanProv(bean.getCostoEstandarUnitValDgCan()!=null?bean.getCostoEstandarUnitValDgCan().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setCostoEstandarUnitValDisElProv(bean.getCostoEstandarUnitValDisEl()!=null?bean.getCostoEstandarUnitValDisEl().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setCostoEstandarUnitValeImpreProv(bean.getCostoEstandarUnitValeImpre()!=null?bean.getCostoEstandarUnitValeImpre().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setCostoEstandarUnitValeReparProv(bean.getCostoEstandarUnitValeRepar()!=null?bean.getCostoEstandarUnitValeRepar().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setCostoEstandarUnitValFiCanProv(bean.getCostoEstandarUnitValFiCan()!=null?bean.getCostoEstandarUnitValFiCan().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));

						command.setCostoTotalAtencionConsReclProv(bean.getCostoTotalAtencionConsRecl()!=null?bean.getCostoTotalAtencionConsRecl().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setCostoTotalCanjeLiqValeDigProv(bean.getCostoTotalCanjeLiqValeDig()!=null?bean.getCostoTotalCanjeLiqValeDig().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setCostoTotalCanjeLiqValeFisProv(bean.getCostoTotalCanjeLiqValeFis()!=null?bean.getCostoTotalCanjeLiqValeFis().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setCostoTotalEntregaValDisElProv(bean.getCostoTotalEntregaValDisEl()!=null?bean.getCostoTotalEntregaValDisEl().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setCostoTotalImpresionValeProv(bean.getCostoTotalImpresionVale()!=null?bean.getCostoTotalImpresionVale().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setCostoTotalRepartoValesDomiProv(bean.getCostoTotalRepartoValesDomi()!=null?bean.getCostoTotalRepartoValesDomi().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));

						command.setNumeroAtencionesProv(bean.getNumeroAtenciones());
						command.setNumeroValesDigitalCanjeadosProv(bean.getNumeroValesDigitalCanjeados());
						command.setNumeroValesEntregadoDisElProv(bean.getNumeroValesEntregadoDisEl());
						command.setNumeroValesFisicosCanjeadosProv(bean.getNumeroValesFisicosCanjeados());
						command.setNumeroValesImpresoProv(bean.getNumeroValesImpreso());
						command.setNumeroValesRepartidosDomiProv(bean.getNumeroValesRepartidosDomi());

						command.setTotalActividadesExtraordProv(bean.getTotalActividadesExtraord()!=null?bean.getTotalActividadesExtraord().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setTotalDesplazamientoPersonalProv(bean.getTotalDesplazamientoPersonal()!=null?bean.getTotalDesplazamientoPersonal().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setTotalGestionAdministrativaProv(bean.getTotalGestionAdministrativa()!=null?bean.getTotalGestionAdministrativa().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));

					/*	command.getCostoEstandarUnitAtencionProv().setScale(2, BigDecimal.ROUND_DOWN);
						command.getCostoEstandarUnitValDgCanProv().setScale(2, BigDecimal.ROUND_DOWN);
						command.getCostoEstandarUnitValDisElProv().setScale(2, BigDecimal.ROUND_DOWN);
						command.getCostoEstandarUnitValeImpreProv().setScale(2, BigDecimal.ROUND_DOWN);
						command.getCostoEstandarUnitValeReparProv().setScale(2, BigDecimal.ROUND_DOWN);
						command.getCostoEstandarUnitValFiCanProv().setScale(2, BigDecimal.ROUND_DOWN);
						command.getCostoTotalAtencionConsReclProv().setScale(2, BigDecimal.ROUND_DOWN);
						command.getCostoTotalCanjeLiqValeDigProv().setScale(2, BigDecimal.ROUND_DOWN);
						command.getCostoTotalCanjeLiqValeFisProv().setScale(2, BigDecimal.ROUND_DOWN);
						command.getCostoTotalEntregaValDisElProv().setScale(2, BigDecimal.ROUND_DOWN);
						command.getCostoTotalImpresionValeProv().setScale(2, BigDecimal.ROUND_DOWN);
						command.getCostoTotalRepartoValesDomiProv().setScale(2, BigDecimal.ROUND_DOWN);
						command.getTotalActividadesExtraordProv().setScale(2, BigDecimal.ROUND_DOWN);
						command.getTotalDesplazamientoPersonalProv().setScale(2, BigDecimal.ROUND_DOWN);
						command.getTotalGestionAdministrativaProv().setScale(2, BigDecimal.ROUND_DOWN);*/

					} if (bean.getId().getIdZonaBenef() == FiseConstants.ZONA_LIMA) {
						command.setCostoEstandarUnitAtencionLim(bean.getCostoEstandarUnitAtencion()!=null?bean.getCostoEstandarUnitAtencion().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setCostoEstandarUnitValDgCanLim(bean.getCostoEstandarUnitValDgCan()!=null?bean.getCostoEstandarUnitValDgCan().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setCostoEstandarUnitValDisElLim(bean.getCostoEstandarUnitValDisEl()!=null?bean.getCostoEstandarUnitValDisEl().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setCostoEstandarUnitValeImpreLim(bean.getCostoEstandarUnitValeImpre()!=null?bean.getCostoEstandarUnitValeImpre().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setCostoEstandarUnitValeReparLim(bean.getCostoEstandarUnitValeRepar()!=null?bean.getCostoEstandarUnitValeRepar().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setCostoEstandarUnitValFiCanLim(bean.getCostoEstandarUnitValFiCan()!=null?bean.getCostoEstandarUnitValFiCan().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setCostoTotalAtencionConsReclLim(bean.getCostoTotalAtencionConsRecl()!=null?bean.getCostoTotalAtencionConsRecl().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setCostoTotalCanjeLiqValeDigLim(bean.getCostoTotalCanjeLiqValeDig()!=null?bean.getCostoTotalCanjeLiqValeDig().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setCostoTotalCanjeLiqValeFisLim(bean.getCostoTotalCanjeLiqValeFis()!=null?bean.getCostoTotalCanjeLiqValeFis().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setCostoTotalEntregaValDisElLim(bean.getCostoTotalEntregaValDisEl()!=null?bean.getCostoTotalEntregaValDisEl().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setCostoTotalImpresionValeLim(bean.getCostoTotalImpresionVale()!=null?bean.getCostoTotalImpresionVale():new BigDecimal(0));
						command.setCostoTotalRepartoValesDomiLim(bean.getCostoTotalRepartoValesDomi()!=null?bean.getCostoTotalRepartoValesDomi().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setNumeroAtencionesLim(bean.getNumeroAtenciones());
						command.setNumeroValesDigitalCanjeadosLim(bean.getNumeroValesDigitalCanjeados());
						command.setNumeroValesEntregadoDisElLim(bean.getNumeroValesEntregadoDisEl());
						command.setNumeroValesFisicosCanjeadosLim(bean.getNumeroValesFisicosCanjeados());
						command.setNumeroValesImpresoLim(bean.getNumeroValesImpreso());
						command.setNumeroValesRepartidosDomiLim(bean.getNumeroValesRepartidosDomi());
						command.setTotalActividadesExtraordLim(bean.getTotalActividadesExtraord()!=null?bean.getTotalActividadesExtraord().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setTotalDesplazamientoPersonalLim(bean.getTotalDesplazamientoPersonal()!=null?bean.getTotalDesplazamientoPersonal().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
						command.setTotalGestionAdministrativaLim(bean.getTotalGestionAdministrativa()!=null?bean.getTotalGestionAdministrativa().setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));

						/*command.getCostoEstandarUnitAtencionLim().setScale(2, BigDecimal.ROUND_DOWN);
						command.getCostoEstandarUnitValDgCanLim().setScale(2, BigDecimal.ROUND_DOWN);
						command.getCostoEstandarUnitValDisElLim().setScale(2, BigDecimal.ROUND_DOWN);
						command.getCostoEstandarUnitValeImpreLim().setScale(2, BigDecimal.ROUND_DOWN);
						command.getCostoEstandarUnitValeReparLim().setScale(2, BigDecimal.ROUND_DOWN);
						command.getCostoEstandarUnitValFiCanLim().setScale(2, BigDecimal.ROUND_DOWN);
						command.getCostoTotalAtencionConsReclLim().setScale(2, BigDecimal.ROUND_DOWN);
						command.getCostoTotalCanjeLiqValeDigLim().setScale(2, BigDecimal.ROUND_DOWN);
						command.getCostoTotalCanjeLiqValeFisLim().setScale(2, BigDecimal.ROUND_DOWN);
						command.getCostoTotalEntregaValDisElLim().setScale(2, BigDecimal.ROUND_DOWN);
						command.getCostoTotalImpresionValeLim().setScale(2, BigDecimal.ROUND_DOWN);
						command.getCostoTotalRepartoValesDomiLim().setScale(2, BigDecimal.ROUND_DOWN);
						command.getTotalActividadesExtraordLim().setScale(2, BigDecimal.ROUND_DOWN);
						command.getTotalDesplazamientoPersonalLim().setScale(2, BigDecimal.ROUND_DOWN);
						command.getTotalGestionAdministrativaLim().setScale(2, BigDecimal.ROUND_DOWN);*/
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return command;

	}

	/*
	 * public static List<Formato12BGartCommand>
	 * toListCommandDetalle(List<FiseFormato12BD> lst){
	 * List<Formato12BGartCommand> lstReturn=null; if(lst!=null &&
	 * !lst.isEmpty()){ lstReturn=new ArrayList<Formato12BGartCommand>();
	 * for(FiseFormato12BD bean:lst){ lstReturn.add(toCommandDetalleOne(bean));
	 * } } return lstReturn; }
	 */

	public static List<FiseFormato12BD> toBeanDetalle(Formato12BGartCommand command) {
		List<FiseFormato12BD> lstresult = new ArrayList<FiseFormato12BD>();
		FiseFormato12BD bean = null;
		int nro = 0;
		boolean isAdd = true;
		System.out.println("COMMAND ==> "+command.getCodEmpresa());
		try {
			if (command != null) {
				while (nro < 3) {
					bean = new FiseFormato12BD();
					FiseFormato12BDPK pk = new FiseFormato12BDPK();
					pk.setAnoEjecucionGasto(command.getAnoEjecucionGasto());
					pk.setAnoPresentacion(command.getAnoPresentacion());
					pk.setCodEmpresa(command.getCodEmpresa());
					pk.setEtapa(command.getEtapa());
					pk.setMesEjecucionGasto(command.getMesEjecucionGasto());
					pk.setMesPresentacion(command.getMesPresentacion());

					bean.setFechaActualizacion(command.getFechaActualizacion());
					bean.setFechaCreacion(command.getFechaCreacion());
					bean.setTerminalActualizacion(command.getTerminalActualizacion());
					bean.setTerminalCreacion(command.getTerminalCreacion());
					bean.setTotalReconocer(command.getTotalReconocer());
					bean.setUsuarioActualizacion(command.getUsuarioActualizacion());
					bean.setUsuarioCreacion(command.getUsuarioCreacion());

					if (nro == 0) {
						bean.setCostoEstandarUnitAtencion(command.getCostoEstandarUnitAtencion().setScale(2, BigDecimal.ROUND_DOWN));
						bean.setCostoEstandarUnitValDgCan(command.getCostoEstandarUnitValDgCan().setScale(2, BigDecimal.ROUND_DOWN));
						bean.setCostoEstandarUnitValDisEl(command.getCostoEstandarUnitValDisEl().setScale(2, BigDecimal.ROUND_DOWN));
						bean.setCostoEstandarUnitValeImpre(command.getCostoEstandarUnitValeImpre().setScale(2, BigDecimal.ROUND_DOWN));
						bean.setCostoEstandarUnitValeRepar(command.getCostoEstandarUnitValeRepar().setScale(2, BigDecimal.ROUND_DOWN));
						bean.setCostoEstandarUnitValFiCan(command.getCostoEstandarUnitValFiCan().setScale(2, BigDecimal.ROUND_DOWN));

						bean.setCostoTotalAtencionConsRecl(command.getCostoTotalAtencionConsRecl().setScale(2, BigDecimal.ROUND_DOWN));
						bean.setCostoTotalCanjeLiqValeDig(command.getCostoTotalCanjeLiqValeDig().setScale(2, BigDecimal.ROUND_DOWN));
						bean.setCostoTotalCanjeLiqValeFis(command.getCostoTotalCanjeLiqValeFis().setScale(2, BigDecimal.ROUND_DOWN));
						bean.setCostoTotalEntregaValDisEl(command.getCostoTotalEntregaValDisEl().setScale(2, BigDecimal.ROUND_DOWN));
						bean.setCostoTotalImpresionVale(command.getCostoTotalImpresionVale().setScale(2, BigDecimal.ROUND_DOWN));
						bean.setCostoTotalRepartoValesDomi(command.getCostoTotalRepartoValesDomi().setScale(2, BigDecimal.ROUND_DOWN));

						bean.setNumeroAtenciones(command.getNumeroAtenciones());
						bean.setNumeroValesDigitalCanjeados(command.getNumeroValesDigitalCanjeados());
						bean.setNumeroValesEntregadoDisEl(command.getNumeroValesEntregadoDisEl());
						bean.setNumeroValesFisicosCanjeados(command.getNumeroValesFisicosCanjeados());
						bean.setNumeroValesImpreso(command.getNumeroValesImpreso());
						bean.setNumeroValesRepartidosDomi(command.getNumeroValesRepartidosDomi());

						bean.setTotalActividadesExtraord(command.getTotalActividadesExtraord().setScale(2, BigDecimal.ROUND_DOWN));
						bean.setTotalDesplazamientoPersonal(command.getTotalDesplazamientoPersonal().setScale(2, BigDecimal.ROUND_DOWN));
						bean.setTotalGestionAdministrativa(command.getTotalGestionAdministrativa().setScale(2, BigDecimal.ROUND_DOWN));
						pk.setIdZonaBenef(FiseConstants.ZONA_RURAL);

					} else if (nro == 1) {
						// provincial
						bean.setCostoEstandarUnitAtencion(command.getCostoEstandarUnitAtencionProv().setScale(2, BigDecimal.ROUND_DOWN));
						bean.setCostoEstandarUnitValDgCan(command.getCostoEstandarUnitValDgCanProv().setScale(2, BigDecimal.ROUND_DOWN));
						bean.setCostoEstandarUnitValDisEl(command.getCostoEstandarUnitValDisElProv().setScale(2, BigDecimal.ROUND_DOWN));
						bean.setCostoEstandarUnitValeImpre(command.getCostoEstandarUnitValeImpreProv().setScale(2, BigDecimal.ROUND_DOWN));
						bean.setCostoEstandarUnitValeRepar(command.getCostoEstandarUnitValeReparProv().setScale(2, BigDecimal.ROUND_DOWN));
						bean.setCostoEstandarUnitValFiCan(command.getCostoEstandarUnitValFiCanProv().setScale(2, BigDecimal.ROUND_DOWN));

						bean.setCostoTotalAtencionConsRecl(command.getCostoTotalAtencionConsReclProv().setScale(2, BigDecimal.ROUND_DOWN));
						bean.setCostoTotalCanjeLiqValeDig(command.getCostoTotalCanjeLiqValeDigProv().setScale(2, BigDecimal.ROUND_DOWN));
						bean.setCostoTotalCanjeLiqValeFis(command.getCostoTotalCanjeLiqValeFisProv().setScale(2, BigDecimal.ROUND_DOWN));
						bean.setCostoTotalEntregaValDisEl(command.getCostoTotalEntregaValDisElProv().setScale(2, BigDecimal.ROUND_DOWN));
						bean.setCostoTotalImpresionVale(command.getCostoTotalImpresionValeProv().setScale(2, BigDecimal.ROUND_DOWN));
						bean.setCostoTotalRepartoValesDomi(command.getCostoTotalRepartoValesDomiProv().setScale(2, BigDecimal.ROUND_DOWN));

						bean.setNumeroAtenciones(command.getNumeroAtencionesProv());
						bean.setNumeroValesDigitalCanjeados(command.getNumeroValesDigitalCanjeadosProv());
						bean.setNumeroValesEntregadoDisEl(command.getNumeroValesEntregadoDisElProv());
						bean.setNumeroValesFisicosCanjeados(command.getNumeroValesFisicosCanjeadosProv());
						bean.setNumeroValesImpreso(command.getNumeroValesImpresoProv());
						bean.setNumeroValesRepartidosDomi(command.getNumeroValesRepartidosDomiProv());

						bean.setTotalActividadesExtraord(command.getTotalActividadesExtraordProv().setScale(2, BigDecimal.ROUND_DOWN));
						bean.setTotalDesplazamientoPersonal(command.getTotalDesplazamientoPersonalProv().setScale(2, BigDecimal.ROUND_DOWN));
						bean.setTotalGestionAdministrativa(command.getTotalGestionAdministrativaProv().setScale(2, BigDecimal.ROUND_DOWN));
						pk.setIdZonaBenef(FiseConstants.ZONA_PROVINCIA);

					} else if (nro == 2) {
						if (command.getCodEmpresa().trim().equalsIgnoreCase("EDLN") || command.getCodEmpresa().trim().equalsIgnoreCase("LDS") ) {
	                      	// lima
							bean.setCostoEstandarUnitAtencion(command.getCostoEstandarUnitAtencionLim().setScale(2, BigDecimal.ROUND_DOWN));
							bean.setCostoEstandarUnitValDgCan(command.getCostoEstandarUnitValDgCanLim().setScale(2, BigDecimal.ROUND_DOWN));
							bean.setCostoEstandarUnitValDisEl(command.getCostoEstandarUnitValDisElLim().setScale(2, BigDecimal.ROUND_DOWN));
							bean.setCostoEstandarUnitValeImpre(command.getCostoEstandarUnitValeImpreLim().setScale(2, BigDecimal.ROUND_DOWN));
							bean.setCostoEstandarUnitValeRepar(command.getCostoEstandarUnitValeReparLim().setScale(2, BigDecimal.ROUND_DOWN));
							bean.setCostoEstandarUnitValFiCan(command.getCostoEstandarUnitValFiCanLim().setScale(2, BigDecimal.ROUND_DOWN));

							bean.setCostoTotalAtencionConsRecl(command.getCostoTotalAtencionConsReclLim().setScale(2, BigDecimal.ROUND_DOWN));
							bean.setCostoTotalCanjeLiqValeDig(command.getCostoTotalCanjeLiqValeDigLim().setScale(2, BigDecimal.ROUND_DOWN));
							bean.setCostoTotalCanjeLiqValeFis(command.getCostoTotalCanjeLiqValeFisLim().setScale(2, BigDecimal.ROUND_DOWN));
							bean.setCostoTotalEntregaValDisEl(command.getCostoTotalEntregaValDisElLim().setScale(2, BigDecimal.ROUND_DOWN));
							bean.setCostoTotalImpresionVale(command.getCostoTotalImpresionValeLim().setScale(2, BigDecimal.ROUND_DOWN));
							bean.setCostoTotalRepartoValesDomi(command.getCostoTotalRepartoValesDomiLim().setScale(2, BigDecimal.ROUND_DOWN));

							bean.setNumeroAtenciones(command.getNumeroAtencionesLim());
							bean.setNumeroValesDigitalCanjeados(command.getNumeroValesDigitalCanjeadosLim());
							bean.setNumeroValesEntregadoDisEl(command.getNumeroValesEntregadoDisElLim());
							bean.setNumeroValesFisicosCanjeados(command.getNumeroValesFisicosCanjeadosLim());
							bean.setNumeroValesImpreso(command.getNumeroValesImpresoLim());
							bean.setNumeroValesRepartidosDomi(command.getNumeroValesRepartidosDomiLim());

							bean.setTotalActividadesExtraord(command.getTotalActividadesExtraordLim().setScale(2, BigDecimal.ROUND_DOWN));
							bean.setTotalDesplazamientoPersonal(command.getTotalDesplazamientoPersonalLim().setScale(2, BigDecimal.ROUND_DOWN));
							bean.setTotalGestionAdministrativa(command.getTotalGestionAdministrativaLim().setScale(2, BigDecimal.ROUND_DOWN));
							pk.setIdZonaBenef(FiseConstants.ZONA_LIMA);

						}else{
							isAdd=false;
						}
					}                
					if(isAdd){
						bean.setId(pk);
						lstresult.add(bean);
					}
					nro++;				
				}
			}
			System.out.println("lista de detalle::" + lstresult.size());
		} catch (Exception e) {
			lstresult = new ArrayList<FiseFormato12BD>();	
		}	
		return lstresult;
	}

	public Map<Long, String> getListaMes() {
		return listaMes;
	}

	public void setListaMes(Map<Long, String> listaMes) {
		this.listaMes = listaMes;
	}

	public Map<Long, String> getListaZonasBenef() {
		return listaZonasBenef;
	}

	public void setListaZonasBenef(Map<Long, String> listaZonasBenef) {
		this.listaZonasBenef = listaZonasBenef;
	}

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

	public String getEtapa() {
		return etapa;
	}

	public void setEtapa(String etapa) {
		this.etapa = etapa;
	}

	public String getDescEmpresa() {
		return descEmpresa;
	}

	public void setDescEmpresa(String descEmpresa) {
		this.descEmpresa = descEmpresa;
	}

	public String getDescGrupo() {
		return descGrupo;
	}

	public void setDescGrupo(String descGrupo) {
		this.descGrupo = descGrupo;
	}

	public String getDescEstado() {
		return descEstado;
	}

	public void setDescEstado(String descEstado) {
		this.descEstado = descEstado;
	}

	public String getDescMes() {
		return descMes;
	}

	public void setDescMes(String descMes) {
		this.descMes = descMes;
	}

	public Integer getIdGrupoInf() {
		return idGrupoInf;
	}

	public void setIdGrupoInf(Integer idGrupoInf) {
		this.idGrupoInf = idGrupoInf;
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

	public Integer getAnoEjecucionGasto() {
		return anoEjecucionGasto;
	}

	public void setAnoEjecucionGasto(Integer anoEjecucionGasto) {
		this.anoEjecucionGasto = anoEjecucionGasto;
	}

	public Integer getMesEjecucionGasto() {
		return mesEjecucionGasto;
	}

	public void setMesEjecucionGasto(Integer mesEjecucionGasto) {
		this.mesEjecucionGasto = mesEjecucionGasto;
	}

	public Integer getIdZonaBenef() {
		return idZonaBenef;
	}

	public void setIdZonaBenef(Integer idZonaBenef) {
		this.idZonaBenef = idZonaBenef;
	}

	public Date getFechaEnvioDefinitivo() {
		return fechaEnvioDefinitivo;
	}

	public void setFechaEnvioDefinitivo(Date fechaEnvioDefinitivo) {
		this.fechaEnvioDefinitivo = fechaEnvioDefinitivo;
	}

	public String getNombreArchivoExcel() {
		return nombreArchivoExcel;
	}

	public void setNombreArchivoExcel(String nombreArchivoExcel) {
		this.nombreArchivoExcel = nombreArchivoExcel;
	}

	public String getNombreArchivoTexto() {
		return nombreArchivoTexto;
	}

	public void setNombreArchivoTexto(String nombreArchivoTexto) {
		this.nombreArchivoTexto = nombreArchivoTexto;
	}

	public String getUsuarioActualizacion() {
		return usuarioActualizacion;
	}

	public void setUsuarioActualizacion(String usuarioActualizacion) {
		this.usuarioActualizacion = usuarioActualizacion;
	}

	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getTerminalCreacion() {
		return terminalCreacion;
	}

	public void setTerminalCreacion(String terminalCreacion) {
		this.terminalCreacion = terminalCreacion;
	}

	public String getTerminalActualizacion() {
		return terminalActualizacion;
	}

	public void setTerminalActualizacion(String terminalActualizacion) {
		this.terminalActualizacion = terminalActualizacion;
	}

	public BigDecimal getTotalReconocer() {
		return totalReconocer;
	}

	public void setTotalReconocer(BigDecimal totalReconocer) {
		this.totalReconocer = totalReconocer;
	}

	public BigDecimal getCostoEstandarUnitAtencion() {
		return costoEstandarUnitAtencion;
	}

	public void setCostoEstandarUnitAtencion(BigDecimal costoEstandarUnitAtencion) {
		this.costoEstandarUnitAtencion = costoEstandarUnitAtencion;
	}

	public BigDecimal getCostoEstandarUnitValDgCan() {
		return costoEstandarUnitValDgCan;
	}

	public void setCostoEstandarUnitValDgCan(BigDecimal costoEstandarUnitValDgCan) {
		this.costoEstandarUnitValDgCan = costoEstandarUnitValDgCan;
	}

	public BigDecimal getCostoEstandarUnitValDisEl() {
		return costoEstandarUnitValDisEl;
	}

	public void setCostoEstandarUnitValDisEl(BigDecimal costoEstandarUnitValDisEl) {
		this.costoEstandarUnitValDisEl = costoEstandarUnitValDisEl;
	}

	public BigDecimal getCostoEstandarUnitValFiCan() {
		return costoEstandarUnitValFiCan;
	}

	public void setCostoEstandarUnitValFiCan(BigDecimal costoEstandarUnitValFiCan) {
		this.costoEstandarUnitValFiCan = costoEstandarUnitValFiCan;
	}

	public BigDecimal getCostoEstandarUnitValeImpre() {
		return costoEstandarUnitValeImpre;
	}

	public void setCostoEstandarUnitValeImpre(BigDecimal costoEstandarUnitValeImpre) {
		this.costoEstandarUnitValeImpre = costoEstandarUnitValeImpre;
	}

	public BigDecimal getCostoEstandarUnitValeRepar() {
		return costoEstandarUnitValeRepar;
	}

	public void setCostoEstandarUnitValeRepar(BigDecimal costoEstandarUnitValeRepar) {
		this.costoEstandarUnitValeRepar = costoEstandarUnitValeRepar;
	}

	public BigDecimal getCostoTotalAtencionConsRecl() {
		return costoTotalAtencionConsRecl;
	}

	public void setCostoTotalAtencionConsRecl(BigDecimal costoTotalAtencionConsRecl) {
		this.costoTotalAtencionConsRecl = costoTotalAtencionConsRecl;
	}

	public BigDecimal getCostoTotalCanjeLiqValeDig() {
		return costoTotalCanjeLiqValeDig;
	}

	public void setCostoTotalCanjeLiqValeDig(BigDecimal costoTotalCanjeLiqValeDig) {
		this.costoTotalCanjeLiqValeDig = costoTotalCanjeLiqValeDig;
	}

	public BigDecimal getCostoTotalCanjeLiqValeFis() {
		return costoTotalCanjeLiqValeFis;
	}

	public void setCostoTotalCanjeLiqValeFis(BigDecimal costoTotalCanjeLiqValeFis) {
		this.costoTotalCanjeLiqValeFis = costoTotalCanjeLiqValeFis;
	}

	public BigDecimal getCostoTotalEntregaValDisEl() {
		return costoTotalEntregaValDisEl;
	}

	public void setCostoTotalEntregaValDisEl(BigDecimal costoTotalEntregaValDisEl) {
		this.costoTotalEntregaValDisEl = costoTotalEntregaValDisEl;
	}

	public BigDecimal getCostoTotalImpresionVale() {
		return costoTotalImpresionVale;
	}

	public void setCostoTotalImpresionVale(BigDecimal costoTotalImpresionVale) {
		this.costoTotalImpresionVale = costoTotalImpresionVale;
	}

	public BigDecimal getCostoTotalRepartoValesDomi() {
		return costoTotalRepartoValesDomi;
	}

	public void setCostoTotalRepartoValesDomi(BigDecimal costoTotalRepartoValesDomi) {
		this.costoTotalRepartoValesDomi = costoTotalRepartoValesDomi;
	}

	public Integer getNumeroAtenciones() {
		return numeroAtenciones;
	}

	public void setNumeroAtenciones(Integer numeroAtenciones) {
		this.numeroAtenciones = numeroAtenciones;
	}

	public Integer getNumeroValesDigitalCanjeados() {
		return numeroValesDigitalCanjeados;
	}

	public void setNumeroValesDigitalCanjeados(Integer numeroValesDigitalCanjeados) {
		this.numeroValesDigitalCanjeados = numeroValesDigitalCanjeados;
	}

	public Integer getNumeroValesEntregadoDisEl() {
		return numeroValesEntregadoDisEl;
	}

	public void setNumeroValesEntregadoDisEl(Integer numeroValesEntregadoDisEl) {
		this.numeroValesEntregadoDisEl = numeroValesEntregadoDisEl;
	}

	public Integer getNumeroValesFisicosCanjeados() {
		return numeroValesFisicosCanjeados;
	}

	public void setNumeroValesFisicosCanjeados(Integer numeroValesFisicosCanjeados) {
		this.numeroValesFisicosCanjeados = numeroValesFisicosCanjeados;
	}

	public Integer getNumeroValesImpreso() {
		return numeroValesImpreso;
	}

	public void setNumeroValesImpreso(Integer numeroValesImpreso) {
		this.numeroValesImpreso = numeroValesImpreso;
	}

	public Integer getNumeroValesRepartidosDomi() {
		return numeroValesRepartidosDomi;
	}

	public void setNumeroValesRepartidosDomi(Integer numeroValesRepartidosDomi) {
		this.numeroValesRepartidosDomi = numeroValesRepartidosDomi;
	}

	public BigDecimal getTotalActividadesExtraord() {
		return totalActividadesExtraord;
	}

	public void setTotalActividadesExtraord(BigDecimal totalActividadesExtraord) {
		this.totalActividadesExtraord = totalActividadesExtraord;
	}

	public BigDecimal getTotalDesplazamientoPersonal() {
		return totalDesplazamientoPersonal;
	}

	public void setTotalDesplazamientoPersonal(BigDecimal totalDesplazamientoPersonal) {
		this.totalDesplazamientoPersonal = totalDesplazamientoPersonal;
	}

	public BigDecimal getTotalGestionAdministrativa() {
		return totalGestionAdministrativa;
	}

	public void setTotalGestionAdministrativa(BigDecimal totalGestionAdministrativa) {
		this.totalGestionAdministrativa = totalGestionAdministrativa;
	}

	public String getStrfechaEnvioDefinitivo() {
		return strfechaEnvioDefinitivo;
	}

	public void setStrfechaEnvioDefinitivo(String strfechaEnvioDefinitivo) {
		this.strfechaEnvioDefinitivo = strfechaEnvioDefinitivo;
	}

	public Integer getMesInicio() {
		return mesInicio;
	}

	public void setMesInicio(Integer mesInicio) {
		this.mesInicio = mesInicio;
	}

	public Integer getAnioInicio() {
		return anioInicio;
	}

	public void setAnioInicio(Integer anioInicio) {
		this.anioInicio = anioInicio;
	}

	public Integer getMesFin() {
		return mesFin;
	}

	public void setMesFin(Integer mesFin) {
		this.mesFin = mesFin;
	}

	public Integer getAnioFin() {
		return anioFin;
	}

	public void setAnioFin(Integer anioFin) {
		this.anioFin = anioFin;
	}

	public BigDecimal getCostoEstandarUnitAtencionProv() {
		return costoEstandarUnitAtencionProv;
	}

	public void setCostoEstandarUnitAtencionProv(BigDecimal costoEstandarUnitAtencionProv) {
		this.costoEstandarUnitAtencionProv = costoEstandarUnitAtencionProv;
	}

	public BigDecimal getCostoEstandarUnitValDgCanProv() {
		return costoEstandarUnitValDgCanProv;
	}

	public void setCostoEstandarUnitValDgCanProv(BigDecimal costoEstandarUnitValDgCanProv) {
		this.costoEstandarUnitValDgCanProv = costoEstandarUnitValDgCanProv;
	}

	public BigDecimal getCostoEstandarUnitValDisElProv() {
		return costoEstandarUnitValDisElProv;
	}

	public void setCostoEstandarUnitValDisElProv(BigDecimal costoEstandarUnitValDisElProv) {
		this.costoEstandarUnitValDisElProv = costoEstandarUnitValDisElProv;
	}

	public BigDecimal getCostoEstandarUnitValFiCanProv() {
		return costoEstandarUnitValFiCanProv;
	}

	public void setCostoEstandarUnitValFiCanProv(BigDecimal costoEstandarUnitValFiCanProv) {
		this.costoEstandarUnitValFiCanProv = costoEstandarUnitValFiCanProv;
	}

	public BigDecimal getCostoEstandarUnitValeImpreProv() {
		return costoEstandarUnitValeImpreProv;
	}

	public void setCostoEstandarUnitValeImpreProv(BigDecimal costoEstandarUnitValeImpreProv) {
		this.costoEstandarUnitValeImpreProv = costoEstandarUnitValeImpreProv;
	}

	public BigDecimal getCostoEstandarUnitValeReparProv() {
		return costoEstandarUnitValeReparProv;
	}

	public void setCostoEstandarUnitValeReparProv(BigDecimal costoEstandarUnitValeReparProv) {
		this.costoEstandarUnitValeReparProv = costoEstandarUnitValeReparProv;
	}

	public BigDecimal getCostoTotalAtencionConsReclProv() {
		return costoTotalAtencionConsReclProv;
	}

	public void setCostoTotalAtencionConsReclProv(BigDecimal costoTotalAtencionConsReclProv) {
		this.costoTotalAtencionConsReclProv = costoTotalAtencionConsReclProv;
	}

	public BigDecimal getCostoTotalCanjeLiqValeDigProv() {
		return costoTotalCanjeLiqValeDigProv;
	}

	public void setCostoTotalCanjeLiqValeDigProv(BigDecimal costoTotalCanjeLiqValeDigProv) {
		this.costoTotalCanjeLiqValeDigProv = costoTotalCanjeLiqValeDigProv;
	}

	public BigDecimal getCostoTotalCanjeLiqValeFisProv() {
		return costoTotalCanjeLiqValeFisProv;
	}

	public void setCostoTotalCanjeLiqValeFisProv(BigDecimal costoTotalCanjeLiqValeFisProv) {
		this.costoTotalCanjeLiqValeFisProv = costoTotalCanjeLiqValeFisProv;
	}

	public BigDecimal getCostoTotalEntregaValDisElProv() {
		return costoTotalEntregaValDisElProv;
	}

	public void setCostoTotalEntregaValDisElProv(BigDecimal costoTotalEntregaValDisElProv) {
		this.costoTotalEntregaValDisElProv = costoTotalEntregaValDisElProv;
	}

	public BigDecimal getCostoTotalImpresionValeProv() {
		return costoTotalImpresionValeProv;
	}

	public void setCostoTotalImpresionValeProv(BigDecimal costoTotalImpresionValeProv) {
		this.costoTotalImpresionValeProv = costoTotalImpresionValeProv;
	}

	public BigDecimal getCostoTotalRepartoValesDomiProv() {
		return costoTotalRepartoValesDomiProv;
	}

	public void setCostoTotalRepartoValesDomiProv(BigDecimal costoTotalRepartoValesDomiProv) {
		this.costoTotalRepartoValesDomiProv = costoTotalRepartoValesDomiProv;
	}

	public Integer getNumeroAtencionesProv() {
		return numeroAtencionesProv;
	}

	public void setNumeroAtencionesProv(Integer numeroAtencionesProv) {
		this.numeroAtencionesProv = numeroAtencionesProv;
	}

	public Integer getNumeroValesDigitalCanjeadosProv() {
		return numeroValesDigitalCanjeadosProv;
	}

	public void setNumeroValesDigitalCanjeadosProv(Integer numeroValesDigitalCanjeadosProv) {
		this.numeroValesDigitalCanjeadosProv = numeroValesDigitalCanjeadosProv;
	}

	public Integer getNumeroValesEntregadoDisElProv() {
		return numeroValesEntregadoDisElProv;
	}

	public void setNumeroValesEntregadoDisElProv(Integer numeroValesEntregadoDisElProv) {
		this.numeroValesEntregadoDisElProv = numeroValesEntregadoDisElProv;
	}

	public Integer getNumeroValesFisicosCanjeadosProv() {
		return numeroValesFisicosCanjeadosProv;
	}

	public void setNumeroValesFisicosCanjeadosProv(Integer numeroValesFisicosCanjeadosProv) {
		this.numeroValesFisicosCanjeadosProv = numeroValesFisicosCanjeadosProv;
	}

	public Integer getNumeroValesImpresoProv() {
		return numeroValesImpresoProv;
	}

	public void setNumeroValesImpresoProv(Integer numeroValesImpresoProv) {
		this.numeroValesImpresoProv = numeroValesImpresoProv;
	}

	public Integer getNumeroValesRepartidosDomiProv() {
		return numeroValesRepartidosDomiProv;
	}

	public void setNumeroValesRepartidosDomiProv(Integer numeroValesRepartidosDomiProv) {
		this.numeroValesRepartidosDomiProv = numeroValesRepartidosDomiProv;
	}

	public BigDecimal getTotalActividadesExtraordProv() {
		return totalActividadesExtraordProv;
	}

	public void setTotalActividadesExtraordProv(BigDecimal totalActividadesExtraordProv) {
		this.totalActividadesExtraordProv = totalActividadesExtraordProv;
	}

	public BigDecimal getTotalDesplazamientoPersonalProv() {
		return totalDesplazamientoPersonalProv;
	}

	public void setTotalDesplazamientoPersonalProv(BigDecimal totalDesplazamientoPersonalProv) {
		this.totalDesplazamientoPersonalProv = totalDesplazamientoPersonalProv;
	}

	public BigDecimal getTotalGestionAdministrativaProv() {
		return totalGestionAdministrativaProv;
	}

	public void setTotalGestionAdministrativaProv(BigDecimal totalGestionAdministrativaProv) {
		this.totalGestionAdministrativaProv = totalGestionAdministrativaProv;
	}

	public BigDecimal getCostoEstandarUnitAtencionLim() {
		return costoEstandarUnitAtencionLim;
	}

	public void setCostoEstandarUnitAtencionLim(BigDecimal costoEstandarUnitAtencionLim) {
		this.costoEstandarUnitAtencionLim = costoEstandarUnitAtencionLim;
	}

	public BigDecimal getCostoEstandarUnitValDgCanLim() {
		return costoEstandarUnitValDgCanLim;
	}

	public void setCostoEstandarUnitValDgCanLim(BigDecimal costoEstandarUnitValDgCanLim) {
		this.costoEstandarUnitValDgCanLim = costoEstandarUnitValDgCanLim;
	}

	public BigDecimal getCostoEstandarUnitValDisElLim() {
		return costoEstandarUnitValDisElLim;
	}

	public void setCostoEstandarUnitValDisElLim(BigDecimal costoEstandarUnitValDisElLim) {
		this.costoEstandarUnitValDisElLim = costoEstandarUnitValDisElLim;
	}

	public BigDecimal getCostoEstandarUnitValFiCanLim() {
		return costoEstandarUnitValFiCanLim;
	}

	public void setCostoEstandarUnitValFiCanLim(BigDecimal costoEstandarUnitValFiCanLim) {
		this.costoEstandarUnitValFiCanLim = costoEstandarUnitValFiCanLim;
	}

	public BigDecimal getCostoEstandarUnitValeImpreLim() {
		return costoEstandarUnitValeImpreLim;
	}

	public void setCostoEstandarUnitValeImpreLim(BigDecimal costoEstandarUnitValeImpreLim) {
		this.costoEstandarUnitValeImpreLim = costoEstandarUnitValeImpreLim;
	}

	public BigDecimal getCostoEstandarUnitValeReparLim() {
		return costoEstandarUnitValeReparLim;
	}

	public void setCostoEstandarUnitValeReparLim(BigDecimal costoEstandarUnitValeReparLim) {
		this.costoEstandarUnitValeReparLim = costoEstandarUnitValeReparLim;
	}

	public BigDecimal getCostoTotalAtencionConsReclLim() {
		return costoTotalAtencionConsReclLim;
	}

	public void setCostoTotalAtencionConsReclLim(BigDecimal costoTotalAtencionConsReclLim) {
		this.costoTotalAtencionConsReclLim = costoTotalAtencionConsReclLim;
	}

	public BigDecimal getCostoTotalCanjeLiqValeDigLim() {
		return costoTotalCanjeLiqValeDigLim;
	}

	public void setCostoTotalCanjeLiqValeDigLim(BigDecimal costoTotalCanjeLiqValeDigLim) {
		this.costoTotalCanjeLiqValeDigLim = costoTotalCanjeLiqValeDigLim;
	}

	public BigDecimal getCostoTotalCanjeLiqValeFisLim() {
		return costoTotalCanjeLiqValeFisLim;
	}

	public void setCostoTotalCanjeLiqValeFisLim(BigDecimal costoTotalCanjeLiqValeFisLim) {
		this.costoTotalCanjeLiqValeFisLim = costoTotalCanjeLiqValeFisLim;
	}

	public BigDecimal getCostoTotalEntregaValDisElLim() {
		return costoTotalEntregaValDisElLim;
	}

	public void setCostoTotalEntregaValDisElLim(BigDecimal costoTotalEntregaValDisElLim) {
		this.costoTotalEntregaValDisElLim = costoTotalEntregaValDisElLim;
	}

	public BigDecimal getCostoTotalImpresionValeLim() {
		return costoTotalImpresionValeLim;
	}

	public void setCostoTotalImpresionValeLim(BigDecimal costoTotalImpresionValeLim) {
		this.costoTotalImpresionValeLim = costoTotalImpresionValeLim;
	}

	public BigDecimal getCostoTotalRepartoValesDomiLim() {
		return costoTotalRepartoValesDomiLim;
	}

	public void setCostoTotalRepartoValesDomiLim(BigDecimal costoTotalRepartoValesDomiLim) {
		this.costoTotalRepartoValesDomiLim = costoTotalRepartoValesDomiLim;
	}

	public Integer getNumeroAtencionesLim() {
		return numeroAtencionesLim;
	}

	public void setNumeroAtencionesLim(Integer numeroAtencionesLim) {
		this.numeroAtencionesLim = numeroAtencionesLim;
	}

	public Integer getNumeroValesDigitalCanjeadosLim() {
		return numeroValesDigitalCanjeadosLim;
	}

	public void setNumeroValesDigitalCanjeadosLim(Integer numeroValesDigitalCanjeadosLim) {
		this.numeroValesDigitalCanjeadosLim = numeroValesDigitalCanjeadosLim;
	}

	public Integer getNumeroValesEntregadoDisElLim() {
		return numeroValesEntregadoDisElLim;
	}

	public void setNumeroValesEntregadoDisElLim(Integer numeroValesEntregadoDisElLim) {
		this.numeroValesEntregadoDisElLim = numeroValesEntregadoDisElLim;
	}

	public Integer getNumeroValesFisicosCanjeadosLim() {
		return numeroValesFisicosCanjeadosLim;
	}

	public void setNumeroValesFisicosCanjeadosLim(Integer numeroValesFisicosCanjeadosLim) {
		this.numeroValesFisicosCanjeadosLim = numeroValesFisicosCanjeadosLim;
	}

	public Integer getNumeroValesImpresoLim() {
		return numeroValesImpresoLim;
	}

	public void setNumeroValesImpresoLim(Integer numeroValesImpresoLim) {
		this.numeroValesImpresoLim = numeroValesImpresoLim;
	}

	public Integer getNumeroValesRepartidosDomiLim() {
		return numeroValesRepartidosDomiLim;
	}

	public void setNumeroValesRepartidosDomiLim(Integer numeroValesRepartidosDomiLim) {
		this.numeroValesRepartidosDomiLim = numeroValesRepartidosDomiLim;
	}

	public BigDecimal getTotalActividadesExtraordLim() {
		return totalActividadesExtraordLim;
	}

	public void setTotalActividadesExtraordLim(BigDecimal totalActividadesExtraordLim) {
		this.totalActividadesExtraordLim = totalActividadesExtraordLim;
	}

	public BigDecimal getTotalDesplazamientoPersonalLim() {
		return totalDesplazamientoPersonalLim;
	}

	public void setTotalDesplazamientoPersonalLim(BigDecimal totalDesplazamientoPersonalLim) {
		this.totalDesplazamientoPersonalLim = totalDesplazamientoPersonalLim;
	}

	public BigDecimal getTotalGestionAdministrativaLim() {
		return totalGestionAdministrativaLim;
	}

	public void setTotalGestionAdministrativaLim(BigDecimal totalGestionAdministrativaLim) {
		this.totalGestionAdministrativaLim = totalGestionAdministrativaLim;
	}

	public List<FisePeriodoEnvio> getListaPeriodo() {
		return listaPeriodo;
	}

	public void setListaPeriodo(List<FisePeriodoEnvio> listaPeriodo) {
		this.listaPeriodo = listaPeriodo;
	}

	public String getPeridoDeclaracion() {
		return peridoDeclaracion;
	}

	public void setPeridoDeclaracion(String peridoDeclaracion) {
		this.peridoDeclaracion = peridoDeclaracion;
	}

	public Integer getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(Integer tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public String getCodEmpresaHidden() {
		return codEmpresaHidden;
	}

	public void setCodEmpresaHidden(String codEmpresaHidden) {
		this.codEmpresaHidden = codEmpresaHidden;
	}

	public String getPeridoDeclaracionHidden() {
		return peridoDeclaracionHidden;
	}

	public void setPeridoDeclaracionHidden(String peridoDeclaracionHidden) {
		this.peridoDeclaracionHidden = peridoDeclaracionHidden;
	}

	public Integer getEstadoEnvio() {
		return estadoEnvio;
	}

	public void setEstadoEnvio(Integer estadoEnvio) {
		this.estadoEnvio = estadoEnvio;
	}

	public String getDescMesEjec() {
		return descMesEjec;
	}

	public void setDescMesEjec(String descMesEjec) {
		this.descMesEjec = descMesEjec;
	}

	public String getEstadoProceso() {
		return estadoProceso;
	}

	public void setEstadoProceso(String estadoProceso) {
		this.estadoProceso = estadoProceso;
	}

	public BigDecimal getPorImpresionVales() {
		return porImpresionVales;
	}

	public void setPorImpresionVales(BigDecimal porImpresionVales) {
		this.porImpresionVales = porImpresionVales;
	}

	public BigDecimal getPorRepartoDom() {
		return porRepartoDom;
	}

	public void setPorRepartoDom(BigDecimal porRepartoDom) {
		this.porRepartoDom = porRepartoDom;
	}

	public BigDecimal getPorEntregaValesDE() {
		return porEntregaValesDE;
	}

	public void setPorEntregaValesDE(BigDecimal porEntregaValesDE) {
		this.porEntregaValesDE = porEntregaValesDE;
	}

	public BigDecimal getPorValesFisicos() {
		return porValesFisicos;
	}

	public void setPorValesFisicos(BigDecimal porValesFisicos) {
		this.porValesFisicos = porValesFisicos;
	}

	public BigDecimal getPorValesDigitales() {
		return porValesDigitales;
	}

	public void setPorValesDigitales(BigDecimal porValesDigitales) {
		this.porValesDigitales = porValesDigitales;
	}

	public BigDecimal getPorAtencionReclamos() {
		return porAtencionReclamos;
	}

	public void setPorAtencionReclamos(BigDecimal porAtencionReclamos) {
		this.porAtencionReclamos = porAtencionReclamos;
	}

	public BigDecimal getPorGestionAdm() {
		return porGestionAdm;
	}

	public void setPorGestionAdm(BigDecimal porGestionAdm) {
		this.porGestionAdm = porGestionAdm;
	}

	public BigDecimal getPorDesplazamientoPers() {
		return porDesplazamientoPers;
	}

	public void setPorDesplazamientoPers(BigDecimal porDesplazamientoPers) {
		this.porDesplazamientoPers = porDesplazamientoPers;
	}

	public BigDecimal getPorActividadExtra() {
		return porActividadExtra;
	}

	public void setPorActividadExtra(BigDecimal porActividadExtra) {
		this.porActividadExtra = porActividadExtra;
	}

	public BigDecimal getTotalGeneralReconocer() {
		return totalGeneralReconocer;
	}

	public void setTotalGeneralReconocer(BigDecimal totalGeneralReconocer) {
		this.totalGeneralReconocer = totalGeneralReconocer;
	}

	public String getEtapaBusqueda() {
		return etapaBusqueda;
	}

	public void setEtapaBusqueda(String etapaBusqueda) {
		this.etapaBusqueda = etapaBusqueda;
	}

	public String getCodEmpresaBusqueda() {
		return codEmpresaBusqueda;
	}

	public void setCodEmpresaBusqueda(String codEmpresaBusqueda) {
		this.codEmpresaBusqueda = codEmpresaBusqueda;
	}

	public Integer getTypeFile() {
		return typeFile;
	}

	public void setTypeFile(Integer typeFile) {
		this.typeFile = typeFile;
	}
	public String getIdGrupoInfo() {
		return idGrupoInfo;
	}
	public void setIdGrupoInfo(String idGrupoInfo) {
		this.idGrupoInfo = idGrupoInfo;
	}

	public String getEtapaFinal() {
		return etapaFinal;
	}

	public void setEtapaFinal(String etapaFinal) {
		this.etapaFinal = etapaFinal;
	}

	public String getFlagPeriodoEjecucion() {
		return flagPeriodoEjecucion;
	}

	public void setFlagPeriodoEjecucion(String flagPeriodoEjecucion) {
		this.flagPeriodoEjecucion = flagPeriodoEjecucion;
	}
	

}
