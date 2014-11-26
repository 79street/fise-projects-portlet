package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.Formato12C12D13Generic;
import gob.osinergmin.fise.bean.Formato13ACBean;
import gob.osinergmin.fise.bean.Formato13ADReportBean;
import gob.osinergmin.fise.bean.MensajeErrorBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.AdmUbigeo;
import gob.osinergmin.fise.domain.CfgTabla;
import gob.osinergmin.fise.domain.FiseFormato13AC;
import gob.osinergmin.fise.domain.FiseFormato13ACPK;
import gob.osinergmin.fise.domain.FiseFormato13AD;
import gob.osinergmin.fise.domain.FiseFormato13ADOb;
import gob.osinergmin.fise.domain.FiseFormato13ADPK;
import gob.osinergmin.fise.domain.FiseGrupoInformacion;
import gob.osinergmin.fise.domain.FisePeriodoEnvio;
import gob.osinergmin.fise.gart.command.Formato13AGartCommand;
import gob.osinergmin.fise.gart.json.Formato13AGartJSON;
import gob.osinergmin.fise.gart.jsp.FileEntryJSP;
import gob.osinergmin.fise.gart.service.CfgCampoGartService;
import gob.osinergmin.fise.gart.service.CfgTablaGartService;
import gob.osinergmin.fise.gart.service.CommonGartService;
import gob.osinergmin.fise.gart.service.FisePeriodoEnvioGartService;
import gob.osinergmin.fise.gart.service.Formato13AGartService;
import gob.osinergmin.fise.gart.xls.FormatoExcelImport;
import gob.osinergmin.fise.util.FechaUtil;
import gob.osinergmin.fise.util.FormatoUtil;
import gob.osinergmin.fise.xls.XlsTableConfig;
import gob.osinergmin.fise.xls.XlsWorkbookConfig;
import gob.osinergmin.fise.xls.XlsWorksheetConfig;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

@SessionAttributes({ "esAdministrador" })
@Controller("formato13AGartController")
@RequestMapping("VIEW")
public class Formato13AGartController {

	private static final Log logger = LogFactoryUtil.getLog(Formato13AGartController.class);
	private static final String CRUD_CREATE = "CREATE";
	private static final String CRUD_UPDATE = "UPDATE";
	// ---private static final String CRUD_DELETE = "DELETE";
	private static final String CRUD_READ = "READ";
	private static final String CRUD_READ_CREATEUPDATE = "READCREATEUPDATE";

	@Autowired
	@Qualifier("fiseUtil")
	private FiseUtil fiseUtil;

	@Autowired
	@Qualifier("formato13AGartServiceImpl")
	private Formato13AGartService formatoService;

	@Autowired
	@Qualifier("fisePeriodoEnvioGartServiceImpl")
	FisePeriodoEnvioGartService periodoService;
	@Autowired
	@Qualifier("cfgTablaGartServiceImpl")
	CfgTablaGartService tablaService;
	@Autowired
	@Qualifier("cfgCampoGartServiceImpl")
	CfgCampoGartService campoService;
	@Autowired
	@Qualifier("commonGartServiceImpl")
	CommonGartService commonService;

	List<MensajeErrorBean> listaObservaciones;
	Map<String, String> mapaErrores;
	Map<String, String> mapaSectorTipico;
	
	private String nameEstado;
	private String nameGrupo;
	private String inicioVigencia;
	private String finVigencia;

	@RequestMapping
	public String defaultView(ModelMap model, RenderRequest renderRequest, RenderResponse renderResponse, @ModelAttribute("formato13AGartCommand") Formato13AGartCommand command) {

		command.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));
		command.setListaMes(fiseUtil.getMapaMeses());
		command.setAnioInicio(fiseUtil.obtenerNroAnioFechaActual());
		command.setMesInicio(String.valueOf(Integer.parseInt(fiseUtil.obtenerNroMesFechaActual()) - 1));
		command.setAnioFin(fiseUtil.obtenerNroAnioFechaActual());
		command.setMesFin(fiseUtil.obtenerNroMesFechaActual());

		model.addAttribute("esAdministrador", fiseUtil.esAdministrador(renderRequest));

		mapaErrores = fiseUtil.getMapaErrores();
		mapaSectorTipico = fiseUtil.getMapaSectorTipico();

		logger.info("admin1.1:" + model.get("esAdministrador"));

		return "formato13AInicio";
	}

	@ResourceMapping("busqueda")
	public void grid(ModelMap model, ResourceRequest request, ResourceResponse response, @ModelAttribute("formato13AGartCommand") Formato13AGartCommand command) {

		try {
			response.setContentType("application/json");
			logger.info("admin2.1:" + model.get("esAdministrador"));
			HttpServletRequest req = PortalUtil.getHttpServletRequest(request);
			HttpSession session = req.getSession();
			List<FiseFormato13AC> listaFormato;
			JSONArray jsonArray = new JSONArray();
			Map<String, String> mapaEmpresa = fiseUtil.getMapaEmpresa();
			Map<Long, String> listaMes = fiseUtil.getMapaMeses();

			String codEmpresa = command.getCodEmpresa();
			String anioDesde = command.getAnioInicio();
			String mesDesde = command.getMesInicio();
			String anioHasta = command.getAnioFin();
			String mesHasta = command.getMesFin();
			String etapa = command.getEtapa();
			logger.info("valores " + codEmpresa);
			logger.info("valores " + anioDesde);
			logger.info("valores " + mesDesde);
			logger.info("valores " + anioHasta);
			logger.info("valores " + mesHasta);
			logger.info("valores " + etapa);

			listaFormato = formatoService.buscarFormato13AC(codEmpresa != "" ? FormatoUtil.rellenaDerecha(codEmpresa, ' ', 4) : "", anioDesde != "" ? Long.parseLong(anioDesde) : 0, mesDesde != "" ? Long.parseLong(mesDesde) : 0, anioHasta != "" ? Long.parseLong(anioHasta) : 0, mesHasta != "" ? Long.parseLong(mesHasta) : 0, etapa);

			logger.info("arreglo lista:" + listaFormato);
			for (FiseFormato13AC fiseFormato13AC : listaFormato) {
				// seteamos la descripcion de la empresa
				logger.info("empresa " + mapaEmpresa.get(fiseFormato13AC.getId().getCodEmpresa()));
				fiseFormato13AC.setDescEmpresa(mapaEmpresa.get(fiseFormato13AC.getId().getCodEmpresa()));
				fiseFormato13AC.setDescMesPresentacion(listaMes.get(fiseFormato13AC.getId().getMesPresentacion()));
				
				if(fiseFormato13AC.getFiseGrupoInformacion()!=null && fiseFormato13AC.getFiseGrupoInformacion().getDescripcion()!=null){
					fiseFormato13AC.setDescGrupoInformacion(fiseFormato13AC.getFiseGrupoInformacion().getDescripcion());
					
				}
				
				if(fiseFormato13AC.getFechaEnvioDefinitivo()!=null){
					fiseFormato13AC.setDescEstado(FiseConstants.ESTADO_FECHAENVIO_ENVIADO);
				}else{
					fiseFormato13AC.setDescEstado(FiseConstants.ESTADO_FECHAENVIO_POR_ENVIAR);
					
				}

				/** Obteniendo el flag de la operacion */
				String flagOper = commonService.obtenerEstadoProceso(fiseFormato13AC.getId().getCodEmpresa(), FiseConstants.TIPO_FORMATO_13A, fiseFormato13AC.getId().getAnoPresentacion(), fiseFormato13AC.getId().getMesPresentacion(), fiseFormato13AC.getId().getEtapa());
				String inicioVig="";
				String finVig="";
				String periodo=fiseFormato13AC.getId().getAnoPresentacion()+""+fiseFormato13AC.getId().getMesPresentacion()+fiseFormato13AC.getId().getEtapa();
				
				List<FisePeriodoEnvio> listaPeriodoEnvio = periodoService.listarFisePeriodoEnvioMesAnioEtapa(fiseFormato13AC.getId().getCodEmpresa(), FiseConstants.TIPO_FORMATO_13A);
					for (FisePeriodoEnvio prd : listaPeriodoEnvio) {
						if (periodo.equalsIgnoreCase(prd.getCodigoItem())) {
							inicioVig=prd.getAnioInicioVig();
							finVig=prd.getAnioFinVig();
							break;
						}
					}
				
				jsonArray.put(new Formato13AGartJSON().asJSONObject(fiseFormato13AC, flagOper,inicioVig,finVig));

			}

			// ************************************************************************
			// Generamos la configuración de la exportación a Excel
			// ************************************************************************
			XlsWorkbookConfig xlsWorkbookConfig = new XlsWorkbookConfig();
			xlsWorkbookConfig.setName(FiseConstants.NOMBRE_EXCEL_FORMATO13A);
			List<XlsTableConfig> tables = new LinkedList<XlsTableConfig>();
			tables.add(new XlsTableConfig(listaFormato, FiseConstants.TIPO_FORMATO_13A));
			List<XlsWorksheetConfig> sheets = new LinkedList<XlsWorksheetConfig>();
			sheets.add(new XlsWorksheetConfig(FiseConstants.NOMBRE_HOJA_FORMATO13A, tables));
			xlsWorkbookConfig.setSheets(sheets);
			session.setAttribute(FiseConstants.KEY_CFG_EXCEL_EXPORT, xlsWorkbookConfig);

			logger.info("arreglo json:" + jsonArray);
			PrintWriter pw = response.getWriter();
			pw.write(jsonArray.toString());
			pw.flush();
			pw.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@RequestMapping(params = "action=nuevo")
	public String nuevoFormato(ModelMap model, RenderRequest renderRequest, RenderResponse renderResponse, @ModelAttribute("formato13AGartCommand") Formato13AGartCommand command) {
		System.out.println("aqui en nuevoFormato");

		String codEmpresa = renderRequest.getParameter("codEmpresa");
		String periodo = renderRequest.getParameter("periodoDeclaracion");
		String read = renderRequest.getParameter("readonly");
		String tipoOperacion = renderRequest.getParameter("tipoOperacion");
		String descPeriodo = renderRequest.getParameter("descripcionPeriodo");
		String error = renderRequest.getParameter("error");

		String codEmpresaHidden = renderRequest.getParameter("codEmpresaHidden");
		String descPeriodoHidden = renderRequest.getParameter("descripcionPeriodoHidden");
		
		String descGrupoInformacion = renderRequest.getParameter("descGrupoInformacion");
		String descestado = renderRequest.getParameter("descestado");
		
		String anioInicioVigencia = renderRequest.getParameter("anioInicioVigencia");
		String anioFinVigencia = renderRequest.getParameter("anioFinVigencia");
		
		

		System.out.println("codEmpresa::>" + codEmpresa);
		System.out.println("periodo::>" + periodo);

		if (error != null) {
			model.addAttribute("error", error);
		}

		if (codEmpresa != null) {
			command.setCodEmpresa(codEmpresa);
		}
		if (periodo != null) {
			command.setPeridoDeclaracion(periodo);
		}
		if (descPeriodo != null) {
			command.setDescripcionPeriodo(descPeriodo);
		}
		if (codEmpresaHidden != null) {
			command.setCodEmpresaHidden(codEmpresaHidden);
		}
		if (descPeriodoHidden != null) {
			command.setDescripcionPeriodoHidden(descPeriodoHidden);
		}
		if (descGrupoInformacion != null) {
			command.setDescGrupoInformacion(descGrupoInformacion);
		}
		if (descestado != null) {
			command.setDescestado(descestado);
		}
		if (anioInicioVigencia != null) {
			command.setAnioInicioVigencia(anioInicioVigencia);
		}
		if (anioFinVigencia != null) {
			command.setAnioFinVigencia(anioFinVigencia);
		}
		

		command.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));

		command.setReadOnly(read != null ? Boolean.parseBoolean(read) : false);
		command.setTipoOperacion((tipoOperacion != null && tipoOperacion.equalsIgnoreCase(String.valueOf(FiseConstants.UPDATE))) ? FiseConstants.UPDATE : FiseConstants.ADD);

		model.addAttribute("crud", (tipoOperacion != null && tipoOperacion.equalsIgnoreCase(String.valueOf(FiseConstants.UPDATE))) ? CRUD_UPDATE : CRUD_CREATE);
		model.addAttribute("readonly", "false");

		System.out.println("paso model");

		return "formato13ACRUD";
	}

	@ResourceMapping("saveNuevoFormato")
	public void saveNuevoFormato(ModelMap model, ResourceRequest request, ResourceResponse response, @ModelAttribute("formato13AGartCommand") Formato13AGartCommand command) {
		String msg = "1";
		try {
			System.out.println("aqui save formato");
			System.out.println("empresa::" + command.getCodEmpresa());
			System.out.println("perido::" + command.getPeridoDeclaracion());

			FiseFormato13ACPK pkCabecera = new FiseFormato13ACPK();
			// pkCabecera.setCodEmpresa(command.getCodEmpresa() != "" ?
			// FormatoUtil.rellenaDerecha(command.getCodEmpresa(), ' ', 4) :
			// "");
			pkCabecera.setCodEmpresa(command.getCodEmpresa().trim());

			if (command != null && command.getPeridoDeclaracion().length() > 6) {
				command.setAnioAlta(command.getPeridoDeclaracion().substring(0, 4));
				command.setMesAlta(command.getPeridoDeclaracion().substring(4, 6));
				command.setEtapa(command.getPeridoDeclaracion().substring(6, command.getPeridoDeclaracion().length()));

			}

			pkCabecera.setAnoPresentacion(Long.parseLong(command.getAnioAlta()));
			pkCabecera.setMesPresentacion(Long.parseLong(command.getMesAlta()));
			pkCabecera.setEtapa(command.getEtapa());

			command.setDescripcionPeriodo(FiseUtil.descripcionPeriodo(Long.parseLong(command.getMesAlta()), Long.parseLong(command.getAnioAlta()), command.getEtapa()));
            command.setDescripcionPeriodoHidden(command.getPeridoDeclaracion());
			
            List<FisePeriodoEnvio> listaPeriodoEnvio = periodoService.listarFisePeriodoEnvioMesAnioEtapa(command.getCodEmpresa(), FiseConstants.TIPO_FORMATO_13A);
			for (FisePeriodoEnvio prd : listaPeriodoEnvio) {
				if (command.getPeridoDeclaracion().equalsIgnoreCase(prd.getCodigoItem())) {
					command.setAnioInicioVigencia(prd.getAnioInicioVig());
					command.setAnioFinVigencia(prd.getAnioFinVig());
					break;
				}
			}
			
			// Primero buscamos si existe una cabecera
			// FiseFormato13AC cab =
			// formatoService.obtenerFormato13ACByPK(pkCabecera);
			FiseFormato13AC cab = formatoService.getCabecera(pkCabecera);
			response.setContentType("text/plain");

			if (cab != null) {
				msg = "-1";
			} else {
				ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
				FiseFormato13AC cabecera = new FiseFormato13AC();
				cabecera.setId(pkCabecera);
				cabecera.getId().setEtapa(pkCabecera.getEtapa());

				// guardamos el grupo de informacion
				FiseGrupoInformacion grupoInfo = null;
				long idGrupoInf = commonService.obtenerIdGrupoInformacion(pkCabecera.getAnoPresentacion(), pkCabecera.getMesPresentacion());
				if (idGrupoInf != 0) {
					grupoInfo = commonService.obtenerFiseGrupoInformacionByPK(idGrupoInf);
					command.setDescGrupoInformacion(grupoInfo.getDescripcion());
					//nameGrupo=grupoInfo.getDescripcion();
					
				}
				cabecera.setFiseGrupoInformacion(grupoInfo);

				cabecera.setUsuarioCreacion(themeDisplay.getUser().getLogin());

				cabecera.setTerminalCreacion(themeDisplay.getUser().getLoginIP());
				cabecera.setFechaCreacion(new Date());
				
				cabecera = formatoService.savecabecera(cabecera);
				
				command.setDescestado(FiseConstants.ESTADO_FECHAENVIO_POR_ENVIAR);
				

				model.addAttribute("crud", CRUD_UPDATE);
				model.addAttribute("readonly", "false");
				
				model.addAttribute("formato13AGartCommand", command);

			}

		} catch (PortalException e) {
			msg = "-2";
			e.printStackTrace();
		} catch (SystemException e) {
			msg = "-3";
			e.printStackTrace();
		} catch (Exception e) {

			msg = "-4";

			e.printStackTrace();
		} finally {
			try {
				PrintWriter pw = response.getWriter();
				pw.write(msg);
				pw.flush();
				pw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	@ActionMapping(params = "action=guardarDetalle")
	public void guardarDetalleFormato(ModelMap model, ActionRequest request, ActionResponse response, @RequestParam("crud") String crud, @RequestParam("idZonaBenef") String idZona, @ModelAttribute("formato13AGartCommand") Formato13AGartCommand command) {

		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

		String codEmpresa = command.getCodEmpresa();
		String periodoDeclaracion = command.getPeridoDeclaracion();
		String anioAlta = command.getAnioAlta();
		String mesAlta = command.getMesAlta();
		String codUbigeo = command.getCodDistrito();
		String localidad = command.getLocalidad();
		String st1 = command.getSt1();
		String st2 = command.getSt2();
		String st3 = command.getSt3();
		String st4 = command.getSt4();
		String st5 = command.getSt5();
		String st6 = command.getSt6();
		String stserv = command.getStser();
		String stesp = command.getStesp();
		// String idZonaBenef = command.getIdZonaBenef();
		String idZonaBenef = idZona;
		String sedeAtencion = command.getNombreSede();

		String anioPresentacion = "";
		String mesPresentacion = "";
		String etapa = "";
		if (periodoDeclaracion != null && periodoDeclaracion.length() > 6) {
			anioPresentacion = periodoDeclaracion.substring(0, 4);
			mesPresentacion = periodoDeclaracion.substring(4, 6);
			etapa = periodoDeclaracion.substring(6);
		}

		logger.info("valores detalle " + codEmpresa);
		logger.info("valores detalle" + periodoDeclaracion);
		logger.info("valores anioAlta" + anioAlta);
		logger.info("valores mesAlta" + mesAlta);
		logger.info("valores codUbigeo" + codUbigeo);
		logger.info("valores localidad" + localidad);
		logger.info("valores st1" + st1);
		logger.info("valores st2" + st2);
		logger.info("valores st3" + st3);
		logger.info("valores st4" + st4);
		logger.info("valores st5" + st5);
		logger.info("valores st6" + st6);
		logger.info("valores stserv" + stserv);
		logger.info("valores stesp" + stesp);
		logger.info("valores idZonaBenef" + idZonaBenef);
		logger.info("valores sedeAtencion" + sedeAtencion);

		// seteamos los valores obtenidos
		command.setAnioPresentacion(anioPresentacion);
		command.setMesPresentacion(mesPresentacion);
		command.setEtapa(etapa);
		//

		FiseFormato13ACPK pkCabecera = new FiseFormato13ACPK();
		pkCabecera.setCodEmpresa(codEmpresa != "" ? FormatoUtil.rellenaDerecha(codEmpresa, ' ', 4) : "");
		pkCabecera.setAnoPresentacion(Long.parseLong(anioPresentacion));
		pkCabecera.setMesPresentacion(Long.parseLong(mesPresentacion));
		pkCabecera.setEtapa(etapa);

		FiseFormato13AC cabecera = new FiseFormato13AC();
		cabecera.setId(pkCabecera);

		// Primero buscamos si existe una cabecera
		FiseFormato13AC cab = formatoService.obtenerFormato13ACByPK(pkCabecera);

		if (cab != null) {
			// validar si es nuevo o modificacion
			try {

				if (CRUD_CREATE.equals(crud)) {
					// create
					FiseFormato13AC formato13 = new FiseFormato13AC();
					formato13.setId(pkCabecera);
					List<FiseFormato13AD> listaDetalle = new ArrayList<FiseFormato13AD>();
					// se agregará varaiables de sector tipico para cada tipo de
					// grupo beneficiario

					agregarSectorTipico(themeDisplay, FiseConstants.SECTOR_TIPICO_1_COD, command, listaDetalle);
					agregarSectorTipico(themeDisplay, FiseConstants.SECTOR_TIPICO_2_COD, command, listaDetalle);
					agregarSectorTipico(themeDisplay, FiseConstants.SECTOR_TIPICO_3_COD, command, listaDetalle);
					agregarSectorTipico(themeDisplay, FiseConstants.SECTOR_TIPICO_4_COD, command, listaDetalle);
					agregarSectorTipico(themeDisplay, FiseConstants.SECTOR_TIPICO_5_COD, command, listaDetalle);
					agregarSectorTipico(themeDisplay, FiseConstants.SECTOR_TIPICO_6_COD, command, listaDetalle);
					agregarSectorTipico(themeDisplay, FiseConstants.SECTOR_TIPICO_SER_COD, command, listaDetalle);
					agregarSectorTipico(themeDisplay, FiseConstants.SECTOR_TIPICO_ESP_COD, command, listaDetalle);

					agregarFormato13Detalle(listaDetalle);
				} else if (CRUD_UPDATE.equals(crud)) {
					// update

					if (cab.getFiseFormato13ADs() != null && !cab.getFiseFormato13ADs().isEmpty()) {
						for (FiseFormato13AD d : cab.getFiseFormato13ADs()) {

							if (Long.parseLong(idZonaBenef) == d.getId().getIdZonaBenef()) {
								if (FiseConstants.SECTOR_TIPICO_1_COD.equals(d.getId().getCodSectorTipico().trim())) {
									modificarSectorTipico(themeDisplay, FiseConstants.SECTOR_TIPICO_1_COD, command, d);
								} else if (FiseConstants.SECTOR_TIPICO_2_COD.equals(d.getId().getCodSectorTipico().trim())) {
									modificarSectorTipico(themeDisplay, FiseConstants.SECTOR_TIPICO_2_COD, command, d);
								} else if (FiseConstants.SECTOR_TIPICO_3_COD.equals(d.getId().getCodSectorTipico().trim())) {
									modificarSectorTipico(themeDisplay, FiseConstants.SECTOR_TIPICO_3_COD, command, d);
								} else if (FiseConstants.SECTOR_TIPICO_4_COD.equals(d.getId().getCodSectorTipico().trim())) {
									modificarSectorTipico(themeDisplay, FiseConstants.SECTOR_TIPICO_4_COD, command, d);
								} else if (FiseConstants.SECTOR_TIPICO_5_COD.equals(d.getId().getCodSectorTipico().trim())) {
									modificarSectorTipico(themeDisplay, FiseConstants.SECTOR_TIPICO_5_COD, command, d);
								} else if (FiseConstants.SECTOR_TIPICO_6_COD.equals(d.getId().getCodSectorTipico().trim())) {
									modificarSectorTipico(themeDisplay, FiseConstants.SECTOR_TIPICO_6_COD, command, d);
								} else if (FiseConstants.SECTOR_TIPICO_SER_COD.equals(d.getId().getCodSectorTipico().trim())) {
									modificarSectorTipico(themeDisplay, FiseConstants.SECTOR_TIPICO_SER_COD, command, d);
								} else if (FiseConstants.SECTOR_TIPICO_ESP_COD.equals(d.getId().getCodSectorTipico().trim())) {
									modificarSectorTipico(themeDisplay, FiseConstants.SECTOR_TIPICO_ESP_COD, command, d);
								}
							}

						}
						modificarFormato13Detalle(cab.getFiseFormato13ADs());
					}

				}

			} catch (DataIntegrityViolationException dt) {
				dt.printStackTrace();
				response.setRenderParameter("msj", "Existe detalle ");
			} catch (Exception e) {
				e.printStackTrace();
				response.setRenderParameter("msj", "Ocurrio al guardar cambios");
			}

		} else {
			// no se valida si es nulo la cabecera

		}

		response.setRenderParameter("crud", CRUD_READ_CREATEUPDATE);
		response.setRenderParameter("action", "detalle");
		response.setRenderParameter("codEmpresa", codEmpresa);
		response.setRenderParameter("periodoDeclaracion", periodoDeclaracion);

	}

	@ActionMapping(params = "action=nuevoDetalle")
	public void nuevoDetalleFormato(ModelMap model, ActionRequest request, ActionResponse response, @ModelAttribute("formato13AGartCommand") Formato13AGartCommand command) {
		String codEmpresa = command.getCodEmpresa();
		//String periodoDeclaracion = command.getPeridoDeclaracion();
		String periodoDeclaracion = command.getDescripcionPeriodoHidden();//se obtiene el valor del periodo guardado de el campo descripcionPeriodoHidden(valorPeriodoHidden), probar los demas flujos
		
		String anioPresentacion = "";
		String mesPresentacion = "";
		String etapa = "";
		logger.info("valores detalle " + codEmpresa);
		logger.info("valores detalle" + periodoDeclaracion);

		if (periodoDeclaracion != null && periodoDeclaracion.length() > 6) {
			anioPresentacion = periodoDeclaracion.substring(0, 4);
			mesPresentacion = periodoDeclaracion.substring(4, 6);
			etapa = periodoDeclaracion.substring(6);
		}
		logger.info("Cabecera codEmpresa:" + codEmpresa);
		logger.info("Cabecera anioPresentacion:" + anioPresentacion);
		logger.info("Cabecera mesPresentacion:" + mesPresentacion);
		logger.info("Cabecera etapa:" + etapa);

		// Registramos la cabecera
		FiseFormato13ACPK pkCabecera = new FiseFormato13ACPK();
		pkCabecera.setCodEmpresa(codEmpresa != "" ? FormatoUtil.rellenaDerecha(codEmpresa, ' ', 4) : "");
		pkCabecera.setAnoPresentacion(Long.parseLong(anioPresentacion));
		pkCabecera.setMesPresentacion(Long.parseLong(mesPresentacion));
		pkCabecera.setEtapa(etapa);

		FiseFormato13AC cabecera = new FiseFormato13AC();
		cabecera.setId(pkCabecera);

		// Primero buscamos si existe una cabecera
		FiseFormato13AC cab = formatoService.obtenerFormato13ACByPK(pkCabecera);

		if (cab != null) {
			// update

		} else {
			// create
		}

		response.setRenderParameter("crud", CRUD_CREATE);
		response.setRenderParameter("action", "detalle");
		response.setRenderParameter("codEmpresa", codEmpresa);
		response.setRenderParameter("periodoDeclaracion", periodoDeclaracion);
	}

	@RequestMapping(params = "action=detalle")
	public String detalle(ModelMap model, RenderRequest renderRequest, RenderResponse renderResponse, @RequestParam("crud") String crud, @RequestParam("codEmpresa") String codEmpresa, @RequestParam("periodoDeclaracion") String periodoDeclaracion, @ModelAttribute("formato13AGartCommand") Formato13AGartCommand command) {

		logger.info("valores requestparameter" + crud);
		logger.info("valores periodoDeclaracion" + periodoDeclaracion);
		logger.info("valores codEmpresa" + codEmpresa);

		String anioPresentacion = "";
		String mesPresentacion = "";
		String etapa = "";

		if (periodoDeclaracion != null && periodoDeclaracion.length() > 6) {
			anioPresentacion = periodoDeclaracion.substring(0, 4);
			mesPresentacion = periodoDeclaracion.substring(4, 6);
			etapa = periodoDeclaracion.substring(6);
		}
		// Cabecera
		Map<String, String> mapaEmpresa = fiseUtil.getMapaEmpresa();
		command.setCodEmpresa(codEmpresa);
		command.setPeridoDeclaracion(periodoDeclaracion);
		command.setDescEmpresa(mapaEmpresa.get(FormatoUtil.rellenaDerecha(codEmpresa, ' ', 4)));
		command.setAnioPresentacion(anioPresentacion);
		command.setMesPresentacion(mesPresentacion);
		command.setEtapa(etapa);

		//
		command.setListaMes(fiseUtil.getMapaMeses());
		command.setListaZonasBenef(fiseUtil.getMapaZonaBenef());
		command.setListaDepartamentos(fiseUtil.listaDepartamentos());
		model.addAttribute("readonly", "false");

		// cargamos el ano y fin de vigencia
		List<FisePeriodoEnvio> listaPeriodoEnvio = periodoService.listarFisePeriodoEnvioMesAnioEtapa(codEmpresa, FiseConstants.TIPO_FORMATO_13A);
		for (FisePeriodoEnvio periodo : listaPeriodoEnvio) {
			if (periodoDeclaracion.equals(periodo.getCodigoItem())) {
				command.setAnioInicioVigencia(periodo.getAnioInicioVig());
				command.setAnioFinVigencia(periodo.getAnioFinVig());
				// verificamos el flag de periodo de ejecucion
				if ("S".equals(periodo.getFlagPeriodoEjecucion())) {
					model.addAttribute("readonlyFlagPeriodo", "false");
				} else {
					model.addAttribute("readonlyFlagPeriodo", "true");
				}
				break;
			}
		}

		if (CRUD_READ.equals(crud) || CRUD_READ_CREATEUPDATE.equals(crud) || CRUD_UPDATE.equals(crud)) {

			if (CRUD_READ.equals(crud) || CRUD_READ_CREATEUPDATE.equals(crud)) {
				// Es lectura
				model.addAttribute("readonly", "true");
				model.addAttribute("readonlyFlagPeriodo", "true");
				model.addAttribute("readonlyEdit", "true");
			} else if (CRUD_UPDATE.equals(crud)) {
				model.addAttribute("readonly", "true");
				model.addAttribute("readonlyEdit", "false");
			}

			logger.info("LECTURA DETALLE");
			FiseFormato13AC cabecera = new FiseFormato13AC();
			cabecera.setId(new FiseFormato13ACPK());
			cabecera.getId().setCodEmpresa(codEmpresa != "" ? FormatoUtil.rellenaDerecha(codEmpresa, ' ', 4) : "");
			cabecera.getId().setAnoPresentacion(anioPresentacion != "" ? Long.parseLong(anioPresentacion) : 0);
			cabecera.getId().setMesPresentacion(mesPresentacion != "" ? Long.parseLong(mesPresentacion) : 0);
			cabecera.getId().setEtapa(etapa);

			List<Formato13ADReportBean> detalle = formatoService.listarLocalidadesPorZonasBenefFormato13ADByFormato13AC(cabecera);

			logger.info("arreglo lista:" + detalle.size());
			for (Formato13ADReportBean f : detalle) {

				// comparamos la idZonaBenef que se esta seleccionando
				if (command.getIdZonaBenef().equals(f.getIdZonaBenef().toString())) {
					// seteamos la descripcion de la empresa
					command.setAnioAlta(String.valueOf(f.getAnioAlta()));
					command.setMesAlta(String.valueOf(f.getMesAlta()));
					//
					command.setAnioInicioVigencia(String.valueOf(f.getAnioInicioVigencia()));
					command.setAnioFinVigencia(String.valueOf(f.getAnioFinVigencia()));

					String ubigeo = f.getCodUbigeo();
					if (StringUtils.isNotBlank(ubigeo)) {
						command.setCodDepartamento(ubigeo.substring(0, 2).concat("0000"));
						command.setCodProvincia(ubigeo.substring(0, 4).concat("00"));
						command.setCodDistrito(ubigeo);
					}

					command.setLocalidad(f.getDescLocalidad());
					command.setSt1(String.valueOf(f.getNroBenefPoteSecTipico1()));
					command.setSt2(String.valueOf(f.getNroBenefPoteSecTipico2()));
					command.setSt3(String.valueOf(f.getNroBenefPoteSecTipico3()));
					command.setSt4(String.valueOf(f.getNroBenefPoteSecTipico4()));
					command.setSt5(String.valueOf(f.getNroBenefPoteSecTipico5()));
					command.setSt6(String.valueOf(f.getNroBenefPoteSecTipico6()));
					command.setStser(String.valueOf(f.getNroBenefPoteSecTipico7()));
					command.setStesp(String.valueOf(f.getNroBenefPoteSecTipico8()));
					long total = f.getNroBenefPoteSecTipico1() + f.getNroBenefPoteSecTipico2() + f.getNroBenefPoteSecTipico3() + f.getNroBenefPoteSecTipico4() + f.getNroBenefPoteSecTipico5() + f.getNroBenefPoteSecTipico6() + f.getNroBenefPoteSecTipico7() + f.getNroBenefPoteSecTipico8();
					command.setTotal(String.valueOf(total));
					command.setIdZonaBenef(String.valueOf(f.getIdZonaBenef()));
					command.setNombreSede(f.getNombreSedeAtiende());
				}

			}
		} else if (CRUD_CREATE.equals(crud)) {
			model.addAttribute("readonlyEdit", "false");

			command.setSt1(FiseConstants.CERO);
			command.setSt2(FiseConstants.CERO);
			command.setSt3(FiseConstants.CERO);
			command.setSt4(FiseConstants.CERO);
			command.setSt5(FiseConstants.CERO);
			command.setSt6(FiseConstants.CERO);
			command.setStser(FiseConstants.CERO);
			command.setStesp(FiseConstants.CERO);
			command.setTotal(FiseConstants.CERO);
		}

		model.addAttribute("crud", crud);

		return "formato13ACRUDDetalle";
	}

	@ResourceMapping("cargaPeriodoDeclaracion")
	public void cargaPeriodoDeclaracion(ModelMap model, SessionStatus status, ResourceRequest request, ResourceResponse response, @RequestParam("codEmpresa") String codEmpresa) {
		try {
			logger.info("cargaPeriodoDeclaracion");
			logger.debug("-->cargaPeriodoDeclaracion");
			response.setContentType("applicacion/json");

			List<FisePeriodoEnvio> listaPeriodoEnvio = periodoService.listarFisePeriodoEnvioMesAnioEtapa(codEmpresa, "F13A");

			JSONArray jsonArray = new JSONArray();
			for (FisePeriodoEnvio periodo : listaPeriodoEnvio) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("codigoItem", periodo.getCodigoItem());
				jsonObj.put("descripcionItem", periodo.getDescripcionItem());
				jsonObj.put("flagPeriodoEjecucion", periodo.getFlagPeriodoEjecucion());
				// agregar los valores
				jsonArray.put(jsonObj);
			}

			PrintWriter pw = response.getWriter();
			pw.write(jsonArray.toString());
			pw.flush();
			pw.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@ResourceMapping("provincias")
	public void cargarProvincias(ModelMap model, SessionStatus status, ResourceRequest request, ResourceResponse response, @RequestParam("codDepartamento") String codDepartamento) {
		try {
			logger.info("cargarProvincias");
			logger.debug("-->cargarProvincias");
			response.setContentType("applicacion/json");
			if (codDepartamento != null && !codDepartamento.equals("")) {
				codDepartamento = codDepartamento.substring(0, 2);
			}
			List<AdmUbigeo> provincias = fiseUtil.listaProvincias(codDepartamento);

			JSONArray jsonArray = new JSONArray();

			// Temp
			JSONObject seleccioneItem = new JSONObject();
			seleccioneItem.put("codigoItem", "");
			seleccioneItem.put("descripcionItem", "--Seleccione--");
			jsonArray.put(seleccioneItem);

			for (AdmUbigeo provincia : provincias) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("codigoItem", provincia.getCodUbigeo());
				jsonObj.put("descripcionItem", provincia.getNomUbigeo());
				jsonArray.put(jsonObj);
			}

			PrintWriter pw = response.getWriter();
			pw.write(jsonArray.toString());
			pw.flush();
			pw.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@ResourceMapping("distritos")
	public void cargaDistritos(ModelMap model, SessionStatus status, ResourceRequest request, ResourceResponse response, @RequestParam("codProvincia") String codProvincia) {
		try {
			logger.info("cargaDistritos");
			logger.debug("-->cargaDistritos");
			response.setContentType("applicacion/json");
			if (codProvincia != null && !codProvincia.equals("")) {
				codProvincia = codProvincia.substring(0, 4);
			}

			List<AdmUbigeo> distritos = fiseUtil.listaDistritos(codProvincia);
			JSONArray jsonArray = new JSONArray();

			// Temp
			JSONObject seleccioneItem = new JSONObject();
			seleccioneItem.put("codigoItem", "");
			seleccioneItem.put("descripcionItem", "--Seleccione--");
			jsonArray.put(seleccioneItem);

			for (AdmUbigeo distrito : distritos) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("codigoItem", distrito.getCodUbigeo());
				jsonObj.put("descripcionItem", distrito.getNomUbigeo());
				// agregar los valores
				jsonArray.put(jsonObj);
			}

			PrintWriter pw = response.getWriter();
			pw.write(jsonArray.toString());
			pw.flush();
			pw.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@ResourceMapping("busquedaDetalle")
	public void gridDetalle(ModelMap model, ResourceRequest request, ResourceResponse response, @ModelAttribute("formato13AGartCommand") Formato13AGartCommand command) {

		try {
			System.out.println("busquedaDetalle");

			response.setContentType("application/json");
			logger.info("admin2.1:" + model.get("esAdministrador"));
			HttpServletRequest req = PortalUtil.getHttpServletRequest(request);
			HttpSession session = req.getSession();
			List<Formato13ADReportBean> listaFormato;
			JSONArray jsonArray = new JSONArray();
			Map<String, String> mapaEmpresa = fiseUtil.getMapaEmpresa();

			String tipo = request.getParameter("tipoOperacion");
			System.out.println("tipooo::>" + request.getParameter("tipoOperacion"));
			String codEmpresa = "";
			String periodoDeclaracion = "";

			if (tipo != null && (tipo.equalsIgnoreCase("0") || tipo.equalsIgnoreCase("1"))) {

				System.out.println("codEmpresa::>" + request.getParameter("codEmpresa"));
				System.out.println("anoPresentacion::>" + request.getParameter("anioPresentacion"));

				codEmpresa = request.getParameter("codEmpresa");
				periodoDeclaracion = request.getParameter("anioPresentacion") + request.getParameter("mesPresentacion") + request.getParameter("etapa");

				command.setCodEmpresa(codEmpresa);

				command.setAnioPresentacion(request.getParameter("anioPresentacion"));
				command.setMesPresentacion(request.getParameter("mesPresentacion"));
				command.setEtapa(request.getParameter("etapa"));
				command.setPeridoDeclaracion(periodoDeclaracion);

				String anioPresentacion = "";
				String mesPresentacion = "";
				String etapa = "";
				logger.info("valores " + codEmpresa);
				logger.info("valores " + periodoDeclaracion);

				if (periodoDeclaracion != null && periodoDeclaracion.length() > 6) {
					anioPresentacion = periodoDeclaracion.substring(0, 4);
					mesPresentacion = periodoDeclaracion.substring(4, 6);
					etapa = periodoDeclaracion.substring(6);
				}
				FiseFormato13AC formato13AC = new FiseFormato13AC();
				formato13AC.setId(new FiseFormato13ACPK());
				formato13AC.getId().setCodEmpresa(codEmpresa != "" ? FormatoUtil.rellenaDerecha(codEmpresa, ' ', 4) : "");
				formato13AC.getId().setAnoPresentacion(anioPresentacion != "" ? Long.parseLong(anioPresentacion) : 0);
				formato13AC.getId().setMesPresentacion(mesPresentacion != "" ? Long.parseLong(mesPresentacion) : 0);
				formato13AC.getId().setEtapa(etapa);

				if (anioPresentacion.equalsIgnoreCase("") || mesPresentacion.equalsIgnoreCase("") || etapa.equalsIgnoreCase("")) {
					throw new Exception("Error al buscar detalle: Empresa no seleccionada");
				}

				listaFormato = formatoService.listarLocalidadesPorZonasBenefFormato13ADByFormato13AC(formato13AC);

				logger.info("arreglo lista:" + listaFormato);
				for (Formato13ADReportBean fiseFormato13AD : listaFormato) {
					// seteamos la descripcion de la empresa
					logger.info("empresa " + mapaEmpresa.get(formato13AC.getId().getCodEmpresa()));
					fiseFormato13AD.setDescMesAlta(fiseUtil.getMapaMeses().get(fiseFormato13AD.getMesAlta()));
					fiseFormato13AD.setDescZonaBenef(fiseUtil.getMapaZonaBenef().get(fiseFormato13AD.getIdZonaBenef()));
					jsonArray.put(new Formato13AGartJSON().asJSONObject(fiseFormato13AD, formato13AC));
				}
				fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_FORMATO_13AD, FiseConstants.NOMBRE_EXCEL_FORMATO13AD, FiseConstants.NOMBRE_HOJA_FORMATO13AD, listaFormato);

			} else {
				codEmpresa = command.getCodEmpresa();
				periodoDeclaracion = command.getPeridoDeclaracion();
			}

			logger.info("arreglo json:" + jsonArray);
			PrintWriter pw = response.getWriter();
			pw.write(jsonArray.toString());
			pw.flush();
			pw.close();

			model.addAttribute("formato13AGartCommand", command);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@ActionMapping(params = "action=uploadFile")
	public void cargarDocumento(ActionRequest request, ActionResponse response, @ModelAttribute("formato13AGartCommand") Formato13AGartCommand command) {
		System.out.println("aqui en upload controller");

		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(request);

		String codEmpresaNew = null;
		String periodo = null;
		String tipoaccion = uploadPortletRequest.getParameter("tipoOperacion");
		System.out.println("command UPLOAD tipoaccion::" + tipoaccion);
		if (tipoaccion != null && tipoaccion.endsWith(String.valueOf(FiseConstants.UPDATE))) {
			codEmpresaNew = uploadPortletRequest.getParameter("codEmpresaHidden");
			periodo = uploadPortletRequest.getParameter("descripcionPeriodoHidden");
		} else {
			codEmpresaNew = uploadPortletRequest.getParameter("codEmpresa");
			periodo = uploadPortletRequest.getParameter("peridoDeclaracion");

		}

		System.out.println("command UPLOAD::" + codEmpresaNew);
		System.out.println("command UPLOAD::" + periodo);
		System.out.println("command UPLOAD::" + tipoaccion);

		FiseFormato13ACPK cabecerapk = new FiseFormato13ACPK();

		cabecerapk.setCodEmpresa(codEmpresaNew!=null?codEmpresaNew.trim():"");
		command.setCodEmpresa(codEmpresaNew!=null?codEmpresaNew.trim():"");
		command.setCodEmpresaHidden(codEmpresaNew!=null?codEmpresaNew.trim():"");
		if (periodo != null && periodo.length() > 6) {
			cabecerapk.setAnoPresentacion(Long.parseLong(periodo.substring(0, 4)));
			cabecerapk.setMesPresentacion(Long.parseLong(periodo.substring(4, 6)));
			cabecerapk.setEtapa(periodo.substring(6, periodo.length()));
			command.setPeridoDeclaracion(periodo);
			command.setDescripcionPeriodo(FiseUtil.descripcionPeriodo(cabecerapk.getMesPresentacion(), cabecerapk.getAnoPresentacion(), cabecerapk.getEtapa()));
			command.setDescripcionPeriodoHidden(periodo);
		}

		FileEntry fileEntry = fiseUtil.subirDocumento(request, uploadPortletRequest, FiseConstants.TIPOARCHIVO_XLS);
		List<MensajeErrorBean> lstErrores = readExcelFile(fileEntry, themeDisplay, cabecerapk, tipoaccion);
		System.out.println("tipo de errores::" + lstErrores.size());

		if (lstErrores != null && !lstErrores.isEmpty()) {
			if (tipoaccion != null && tipoaccion != null && tipoaccion.endsWith(String.valueOf(FiseConstants.UPDATE))) {
				response.setRenderParameter("tipoOperacion", String.valueOf(FiseConstants.UPDATE));
				response.setRenderParameter("crud", "UPDATE");
				response.setRenderParameter("readonly", "true");
			} else {
				response.setRenderParameter("tipoOperacion", String.valueOf(FiseConstants.ADD));
				response.setRenderParameter("crud", "CREATE");
				response.setRenderParameter("readonly", "false");
			}
			response.setRenderParameter("error", lstErrores.get(0).getDescripcion());

		} else {
			response.setRenderParameter("error", "Carga exitosa");
			response.setRenderParameter("tipoOperacion", String.valueOf(FiseConstants.UPDATE));
			response.setRenderParameter("crud", "UPDATE");
			response.setRenderParameter("readonly", "true");

		}

		response.setRenderParameter("action", "nuevo");
		response.setRenderParameter("codEmpresa", command.getCodEmpresa().trim());
		response.setRenderParameter("anioPresentacion", cabecerapk.getAnoPresentacion() + "");
		response.setRenderParameter("mesPresentacion", cabecerapk.getMesPresentacion() + "");
		response.setRenderParameter("etapa", cabecerapk.getEtapa() + "");
		response.setRenderParameter("periodoDeclaracion", command.getPeridoDeclaracion());
		response.setRenderParameter("descripcionPeriodo", command.getDescripcionPeriodo());
		response.setRenderParameter("codEmpresaHidden", command.getCodEmpresaHidden());
		response.setRenderParameter("descripcionPeriodoHidden", command.getDescripcionPeriodoHidden());
		
		response.setRenderParameter("descGrupoInformacion", nameGrupo);
		response.setRenderParameter("descestado", nameEstado);
		
		response.setRenderParameter("anioInicioVigencia", inicioVigencia);
		response.setRenderParameter("anioFinVigencia", finVigencia);
		
		

	}

	public List<MensajeErrorBean> readExcelFile(FileEntry archivo, ThemeDisplay themeDisplay, FiseFormato13ACPK pk, String tipoOperacion) {

		InputStream is = null;
		List<MensajeErrorBean> listaError = new ArrayList<MensajeErrorBean>();
		int cont = 0;
		try {
			if (archivo != null) {
				HSSFWorkbook libro = null;
				try {
					is = archivo.getContentStream();
					libro = new HSSFWorkbook(is);// Se lee libro xls
				} catch (Exception e1) {
					MensajeErrorBean msg = new MensajeErrorBean();
					msg.setId(cont);
					msg.setDescripcion("Errror al leer libro");
					listaError.add(msg);
					cont++;
					libro=null;
				}

				if (libro != null) {
					FiseFormato13AC cabecera = null;
					try {
						String periodo="";
						cabecera = FormatoExcelImport.readSheetCabecera(libro);
						List<FiseFormato13AD> lstDetalle =  FormatoExcelImport.getListDetalleSheet(libro, cabecera);
						if (cabecera != null) {
							System.out.println("pkempresa::>" + pk.getCodEmpresa() + " vs " + cabecera.getId().getCodEmpresa());
							System.out.println("pkmes::>" + pk.getMesPresentacion() + " vs " + cabecera.getId().getMesPresentacion());
							System.out.println("pkanio::>" + pk.getAnoPresentacion() + " vs " + cabecera.getId().getAnoPresentacion());
							System.out.println("pketapa::>" + pk.getEtapa());
							periodo=pk.getAnoPresentacion()+""+pk.getMesPresentacion()+pk.getEtapa();
							if (pk.getCodEmpresa().trim().equalsIgnoreCase(cabecera.getId().getCodEmpresa().trim()) && pk.getAnoPresentacion() == cabecera.getId().getAnoPresentacion() && pk.getMesPresentacion() == cabecera.getId().getMesPresentacion()) {
								System.out.println("entro la validacion");

								cabecera.getId().setEtapa(pk.getEtapa());
								cabecera.setNombreArchivoExcel(archivo.getTitle());
								if (tipoOperacion != null && tipoOperacion.equalsIgnoreCase(String.valueOf(FiseConstants.UPDATE))) {
									cabecera = formatoService.getCabecera(cabecera.getId());
									cabecera.setUsuarioActualizacion(themeDisplay.getUser().getLogin());
									cabecera.setTerminalActualizacion(themeDisplay.getUser().getLoginIP());
									cabecera.setFechaActualizacion(new Date());
									cabecera = formatoService.updatecabecera(cabecera);
									if (cabecera != null) {
										formatoService.deletedetalle(cabecera.getId().getCodEmpresa(), cabecera.getId().getAnoPresentacion(), cabecera.getId().getMesPresentacion(), cabecera.getId().getEtapa());
									}

								} else {
									cabecera.setUsuarioCreacion(themeDisplay.getUser().getLogin());
									cabecera.setTerminalCreacion(themeDisplay.getUser().getLoginIP());
									cabecera.setFechaCreacion(new Date());
									FiseGrupoInformacion grupoInfo = null;
									long idGrupoInf = commonService.obtenerIdGrupoInformacion(cabecera.getId().getAnoPresentacion(), cabecera.getId().getMesPresentacion());
									if (idGrupoInf != 0) {
										grupoInfo = commonService.obtenerFiseGrupoInformacionByPK(idGrupoInf);
										nameGrupo=grupoInfo.getDescripcion();
									}
									cabecera.setFiseGrupoInformacion(grupoInfo);
                                    nameEstado=FiseConstants.ESTADO_FECHAENVIO_POR_ENVIAR;
                                    
                                    List<FisePeriodoEnvio> listaPeriodoEnvio = periodoService.listarFisePeriodoEnvioMesAnioEtapa(cabecera.getId().getCodEmpresa(), FiseConstants.TIPO_FORMATO_13A);
    								for (FisePeriodoEnvio prd : listaPeriodoEnvio) {
    									if (periodo.equalsIgnoreCase(prd.getCodigoItem())) {
    										inicioVigencia=  prd.getAnioInicioVig();
    										finVigencia= prd.getAnioFinVig();
    										break;
    									}
    								}
									try {
                                        cabecera = formatoService.savecabecera(cabecera);
									} catch (DataIntegrityViolationException ex) {
										cabecera = null;
										MensajeErrorBean msg = new MensajeErrorBean();
										msg.setId(cont);
										msg.setDescripcion("El formato ya existe");
										listaError.add(msg);
									}
									
									

								}

							} else {
								throw new Exception("La empresa y/o mes año no coinciden con el seleccionado");

							}

						}

						System.out.println("cabecera es :::=>" + cabecera);
						if (cabecera != null) {
							if (lstDetalle != null && !lstDetalle.isEmpty()) {
								for (FiseFormato13AD detalle : lstDetalle) {
									detalle.setAnoInicioVigencia(inicioVigencia!=null && !inicioVigencia.isEmpty()?Long.parseLong(inicioVigencia):null);
									detalle.setAnoFinVigencia(finVigencia!=null && !finVigencia.isEmpty()?Long.parseLong(finVigencia):null);
									detalle.getId().setEtapa(pk.getEtapa());
									detalle.setUsuarioCreacion(themeDisplay.getUser().getLogin());
									detalle.setTerminalCreacion(themeDisplay.getUser().getLoginIP());
									detalle.setFechaCreacion(new Date());
									formatoService.savedetalle(detalle);

								}

							}

							

						}

					} catch (Exception e1) {
						e1.printStackTrace();
						cabecera = null;
						System.out.println("entro en !!!" + e1);
						MensajeErrorBean msg = new MensajeErrorBean();
						msg.setId(cont);
						msg.setDescripcion(e1 + "");
						listaError.add(msg);
					}

				}

			}
		} catch (Exception ex) {
			MensajeErrorBean msg = new MensajeErrorBean();
			msg.setId(cont);
			msg.setDescripcion("Error");
			listaError.add(msg);
			ex.printStackTrace();
		}

		return listaError;
	}

	@RequestMapping(params = "action=view")
	public String viewFormato(ModelMap model, RenderRequest request, RenderResponse response, @ModelAttribute("formato13AGartCommand") Formato13AGartCommand command) {

		try {

			String tipo = request.getParameter("tipo");
			String codEmp = request.getParameter("codEmpresa");
			String anio = request.getParameter("anioPresentacion");
			String mes = request.getParameter("mesPresentacion");
			String etapa = request.getParameter("etapa");
			
			String desgrupo = request.getParameter("descGrupoInformacion");
			String destd = request.getParameter("descestado");

			System.out.println("etapa::>" + etapa);
			System.out.println("codEmp::>" + codEmp.trim().length());


			command.setReadOnly(true);
			command.setCodEmpresa(codEmp.trim());
			command.setAnioPresentacion(anio);
			command.setMesPresentacion(mes);
			command.setEtapa(etapa);
			command.setListaEmpresas(fiseUtil.getEmpresaxUsuario(request));
			command.setListaPeriodo(periodoService.listarFisePeriodoEnvioMesAnioEtapa(codEmp, FiseConstants.NOMBRE_FORMATO_13A));
			command.setPeridoDeclaracion("" + anio + "" + mes + "" + etapa);

			command.setDescripcionPeriodo(FiseUtil.descripcionPeriodo(Long.parseLong(command.getMesPresentacion()), Long.parseLong(command.getAnioPresentacion()), command.getEtapa()));
			command.setCodEmpresaHidden(command.getCodEmpresa());
			command.setDescripcionPeriodoHidden(command.getPeridoDeclaracion());
			
			command.setDescGrupoInformacion(desgrupo!=null?desgrupo:"");
			command.setDescestado(destd!=null?destd:"");

			System.out.println("setPeridoDeclaracion::>" + command.getPeridoDeclaracion());
			if (tipo != null && tipo.equalsIgnoreCase(String.valueOf(FiseConstants.VIEW))) {
				model.addAttribute("crud", CRUD_READ);
				model.addAttribute("readonly", "true");

			}
			if (tipo != null && tipo.equalsIgnoreCase(String.valueOf(FiseConstants.UPDATE))) {
				model.addAttribute("crud", CRUD_UPDATE);
				model.addAttribute("readonly", "false");
				command.setTipoOperacion(FiseConstants.UPDATE);

			}

			model.addAttribute("formato13AGartCommand", command);

		} catch (Exception e) {
			System.out.println("entro error view");
			e.printStackTrace();
		}
		return "formato13ACRUD";
	}

	/** reportes */
	@ResourceMapping("reporte")
	public void reporte(ResourceRequest request, ResourceResponse response, @ModelAttribute("formato13AGartCommand") Formato13AGartCommand command) {
		try {
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
			HttpSession session = httpRequest.getSession();

			JSONArray jsonArray = new JSONArray();
			FiseFormato13AC formato = new FiseFormato13AC();

			Formato13ACBean bean = new Formato13ACBean();

			String codEmpresa = command.getCodEmpresa();
			String anoPresentacion = command.getAnioPresentacion();
			String mesPresentacion = command.getMesPresentacion();
			// String anoInicioVigencia = "";
			// String anoFinVigencia = "";
			String etapa = command.getEtapa();

			String nombreReporte = request.getParameter("nombreReporte").trim();
			String nombreArchivo = request.getParameter("nombreArchivo").trim();
			String tipoFormato = FiseConstants.TIPO_FORMATO_13A;
			String tipoArchivo = request.getParameter("tipoArchivo").trim();

			session.setAttribute("nombreReporte", nombreReporte);
			session.setAttribute("nombreArchivo", nombreArchivo);
			session.setAttribute("tipoFormato", tipoFormato);
			session.setAttribute("tipoArchivo", tipoArchivo);

			// anoInicioVigencia = request.getParameter("anoInicioVigencia");
			// anoFinVigencia = request.getParameter("anoFinVigencia");

			FiseFormato13ACPK pk = new FiseFormato13ACPK();
			pk.setCodEmpresa(codEmpresa);
			pk.setAnoPresentacion(new Long(anoPresentacion));
			pk.setMesPresentacion(new Long(mesPresentacion));
			pk.setEtapa(etapa);

			formato = formatoService.obtenerFormato13ACByPK(pk);
			if (formato != null) {
				// setamos los valores en el bean
				bean = formatoService.estructurarFormato13ABeanByFiseFormato13AC(formato);
				bean.setDescEmpresa(fiseUtil.getMapaEmpresa().get(formato.getId().getCodEmpresa()));
				
				//
				// cargamos la lista a enviar
				List<Formato13ADReportBean> lista = formatoService.listarLocalidadesPorZonasBenefFormato13ADByFormato13AC(formato);

				session.setAttribute("lista", lista);
				
				Map<String, Object> mapa = formatoService.mapearParametrosFormato13A(bean);
				mapa.put("ANO_INICIO_VIGENCIA", formato.getAnoInicioVigenciaDetalle());
				mapa.put("ANO_FIN_VIGENCIA", formato.getAnoFinVigenciaDetalle());
				session.setAttribute("mapa", mapa );
			}

			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();
			pw.write(jsonArray.toString());
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** validacion y envio definitivo */
	@ResourceMapping("validacion")
	public void validacion(ModelMap model, ResourceRequest request, ResourceResponse response, @ModelAttribute("formato13AGartCommand") Formato13AGartCommand command) {
		HttpServletRequest req = PortalUtil.getHttpServletRequest(request);
		HttpSession session = req.getSession();

		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		try {
			FiseFormato13AC formato = new FiseFormato13AC();

			JSONArray jsonArray = new JSONArray();

			String codEmpresa = command.getCodEmpresa();
			String anoPresentacion = command.getAnioPresentacion();
			String mesPresentacion = command.getMesPresentacion();
			String etapa = command.getEtapa();

			FiseFormato13ACPK pk = new FiseFormato13ACPK();
			pk.setCodEmpresa(codEmpresa);
			pk.setAnoPresentacion(new Long(anoPresentacion));
			pk.setMesPresentacion(new Long(mesPresentacion));
			pk.setEtapa(etapa);

			formato = formatoService.obtenerFormato13ACByPK(pk);
			if (formato != null) {
				// int cont=0;
				Formato12C12D13Generic formato13Generic = new Formato12C12D13Generic(formato);
				int i = commonService.validarFormatos_12C12D13A(formato13Generic, FiseConstants.NOMBRE_FORMATO_13A, themeDisplay.getUser().getLogin(), themeDisplay.getUser().getLogin());
				if (i == 0) {
					cargarListaObservaciones(formato.getFiseFormato13ADs());
					for (MensajeErrorBean error : listaObservaciones) {
						JSONObject jsonObj = new JSONObject();
						jsonObj.put("id", error.getId());
						jsonObj.put("descZonaBenef", error.getDescZonaBenef());
						jsonObj.put("codigo", error.getCodigo());
						jsonObj.put("descripcion", error.getDescripcion());
						jsonObj.put("descSectorTipico", error.getDescCodSectorTipico());
						// agregar los valores
						jsonArray.put(jsonObj);
					}
					// **exportar excel
					fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_FORMATO_VAL, FiseConstants.NOMBRE_EXCEL_VALIDACION_F13A, FiseConstants.NOMBRE_HOJA_VALIDACION, listaObservaciones);
				}
			}

			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();
			logger.info(jsonArray.toString());
			pw.write(jsonArray.toString());
			pw.flush();
			pw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@ResourceMapping("reporteValidacion")
	public void reporteValidacion(ResourceRequest request, ResourceResponse response, @ModelAttribute("formato13AGartCommand") Formato13AGartCommand command) {
		try {
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
			HttpSession session = httpRequest.getSession();
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

			JSONArray jsonArray = new JSONArray();

			String nombreReporte = request.getParameter("nombreReporte").trim();
			String nombreArchivo = request.getParameter("nombreArchivo").trim();
			String tipoFormato = FiseConstants.TIPO_FORMATO_VAL_13A;
			String tipoArchivo = request.getParameter("tipoArchivo").trim();
			
			String anioInicioVigencia = request.getParameter("anioInicioVigencia");
			String anioFinVigencia = request.getParameter("anioFinVigencia");

			session.setAttribute("nombreReporte", nombreReporte);
			session.setAttribute("nombreArchivo", nombreArchivo);
			session.setAttribute("tipoFormato", tipoFormato);
			session.setAttribute("tipoArchivo", tipoArchivo);

			if (listaObservaciones != null) {
				session.setAttribute("lista", listaObservaciones);
			}

			// add
			String codEmpresa = command.getCodEmpresa();
			String anoPresentacion = command.getAnioPresentacion();
			String mesPresentacion = command.getMesPresentacion();
			String etapa = command.getEtapa();
			CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO13A);
			String descripcionFormato = "";
			if (tabla != null) {
				descripcionFormato = tabla.getDescripcionTabla();
			}
			Map<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("IMG", session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg"));
			mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
			mapa.put(FiseConstants.PARAM_ANO_PRES_F13A, Long.parseLong(anoPresentacion));
			mapa.put(FiseConstants.PARAM_DESC_MES_PRES_F13A, fiseUtil.getMapaMeses().get(Long.parseLong(mesPresentacion)));
			mapa.put("USUARIO", themeDisplay.getUser().getLogin());
			mapa.put("NOMBRE_FORMATO", descripcionFormato);
			mapa.put("NRO_OBSERVACIONES", (listaObservaciones != null && !listaObservaciones.isEmpty()) ? listaObservaciones.size() : 0);
			mapa.put("INICIO_VIGENCIA", anioInicioVigencia!=null?anioInicioVigencia:"");//anioInicioVigencia!=null?anioInicioVigencia:""
			mapa.put("FIN_VIGENCIA", anioFinVigencia!=null?anioFinVigencia:"");//-anioFinVigencia!=null?anioFinVigencia:""
			// add
			mapa.put("DESC_EMPRESA", fiseUtil.getMapaEmpresa().get(codEmpresa));
			mapa.put("ETAPA", etapa);

			session.setAttribute("mapa", mapa);
			//

			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();
			pw.write(jsonArray.toString());
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@ResourceMapping("envioDefinitivo")
	public void envioDefinitivo(ResourceRequest request, ResourceResponse response, @ModelAttribute("formato13AGartCommand") Formato13AGartCommand command) {
		try {

			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
			HttpSession session = httpRequest.getSession();
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			JSONArray jsonArray = new JSONArray();
			List<FileEntryJSP> listaArchivo = new ArrayList<FileEntryJSP>();

			FiseFormato13AC formato = new FiseFormato13AC();

			Formato13ACBean bean = new Formato13ACBean();
			Map<String, Object> mapa = null;
			String directorio = null;

			String codEmpresa = command.getCodEmpresa();
			String anoPresentacion = command.getAnioPresentacion();
			String mesPresentacion = command.getMesPresentacion();
			String etapa = command.getEtapa();

			String nombreReporte = request.getParameter("nombreReporte").trim();
			String nombreArchivo = request.getParameter("nombreArchivo").trim();

			FiseFormato13ACPK pk = new FiseFormato13ACPK();
			pk.setCodEmpresa(codEmpresa);
			pk.setAnoPresentacion(new Long(anoPresentacion));
			pk.setMesPresentacion(new Long(mesPresentacion));
			pk.setEtapa(etapa);

			formato = formatoService.obtenerFormato13ACByPK(pk);
			if (formato != null) {
				bean = formatoService.estructurarFormato13ABeanByFiseFormato13AC(formato);
				bean.setDescEmpresa(fiseUtil.getMapaEmpresa().get(formato.getId().getCodEmpresa()));
				bean.setDescMesPresentacion(fiseUtil.getMapaMeses().get(formato.getId().getMesPresentacion()));
				mapa = formatoService.mapearParametrosFormato13A(bean);

				CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO13A);
				String descripcionFormato = "";
				if (tabla != null) {
					descripcionFormato = tabla.getDescripcionTabla();
				}

				Formato12C12D13Generic formato13Generic = new Formato12C12D13Generic(formato);
				int i = commonService.validarFormatos_12C12D13A(formato13Generic, FiseConstants.NOMBRE_FORMATO_13A, themeDisplay.getUser().getLogin(), themeDisplay.getUser().getLoginIP());
				if (i == 0) {
					cargarListaObservaciones(formato.getFiseFormato13ADs());
				}

				// guardamos la fecha de envio, en este momento porque
				// necesitamos la fecha de envio para mandar al reporte
				formato = this.modificarEnvioDefinitivoFormato13AC(themeDisplay, formato);

				if (mapa != null) {
					mapa.put("IMG", session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg"));
					mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
					// verificar si ponerlo aca o no
					mapa.put("USUARIO", themeDisplay.getUser().getLogin());
					mapa.put("NOMBRE_FORMATO", descripcionFormato);
					mapa.put("FECHA_ENVIO", formato.getFechaEnvioDefinitivo());
					mapa.put("CORREO", themeDisplay.getUser().getEmailAddress());
					mapa.put("NRO_OBSERVACIONES", (listaObservaciones != null && !listaObservaciones.isEmpty()) ? listaObservaciones.size() : 0);
					mapa.put("MSG_OBSERVACIONES", (listaObservaciones != null && !listaObservaciones.isEmpty()) ? FiseConstants.MSG_OBSERVACION_REPORTE_LLENO : FiseConstants.MSG_OBSERVACION_REPORTE_VACIO);
					// add para acta envio
					mapa.put("ANO_INICIO_VIGENCIA", formato.getAnoInicioVigenciaDetalle());
					mapa.put("ANO_FIN_VIGENCIA", formato.getAnoFinVigenciaDetalle());
					mapa.put("FECHA_REGISTRO", formato.getFechaCreacion());
					mapa.put("USUARIO_REGISTRO", formato.getUsuarioCreacion());
					// prueba de envio definitivo
					String dirCheckedImage = session.getServletContext().getRealPath("/reports/checked.jpg");
					String dirUncheckedImage = session.getServletContext().getRealPath("/reports/unchecked.jpg");
					mapa.put("CHECKED", dirCheckedImage);
					mapa.put("UNCHECKED", dirUncheckedImage);
					boolean cumplePlazo = false;
					cumplePlazo = commonService.fechaEnvioCumplePlazo(FiseConstants.TIPO_FORMATO_13A, formato.getId().getCodEmpresa(), formato.getId().getAnoPresentacion(), formato.getId().getMesPresentacion(), formato.getId().getEtapa(), FechaUtil.fecha_DD_MM_YYYY(formato.getFechaEnvioDefinitivo()));
					if (cumplePlazo) {
						mapa.put("CHECKED_CUMPLEPLAZO", dirCheckedImage);
					} else {
						mapa.put("CHECKED_CUMPLEPLAZO", dirUncheckedImage);
					}
					if (listaObservaciones != null && !listaObservaciones.isEmpty()) {
						mapa.put("CHECKED_OBSERVACION", dirUncheckedImage);
					} else {
						mapa.put("CHECKED_OBSERVACION", dirCheckedImage);
					}
					mapa.put("ETAPA", formato.getId().getEtapa());
				}

				/** REPORTE FORMATO 13A */
				nombreReporte = "formato13A";
				nombreArchivo = nombreReporte;
				directorio = "/reports/" + nombreReporte + ".jasper";
				File reportFile = new File(session.getServletContext().getRealPath(directorio));
				byte[] bytes = null;
				//enviamos la lista de los sectores decalarados
				List<Formato13ADReportBean> lista = formatoService.listarLocalidadesPorZonasBenefFormato13ADByFormato13AC(formato);
				
				bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), mapa, new JRBeanCollectionDataSource(lista));
				
				if (bytes != null) {
					// session.setAttribute("bytes1", bytes);
					//String nombre = nombreArchivo + FiseConstants.EXTENSIONARCHIVO_PDF;
					String nombre = FormatoUtil.nombreIndividualFormato(formato.getId().getCodEmpresa(), formato.getId().getAnoPresentacion(), formato.getId().getMesPresentacion(), FiseConstants.TIPO_FORMATO_13A);
					FileEntry archivo = fiseUtil.subirDocumentoBytes(request, bytes, "application/pdf", nombre);
					if (archivo != null) {
						FileEntryJSP fileEntryJsp = new FileEntryJSP();
						fileEntryJsp.setNombreArchivo(nombre);
						fileEntryJsp.setFileEntry(archivo);
						listaArchivo.add(fileEntryJsp);
					}
				}
				/** REPORTE OBSERVACIONES */
				if (listaObservaciones != null && listaObservaciones.size() > 0) {
					nombreReporte = "validacion13";
					nombreArchivo = nombreReporte;
					directorio = "/reports/" + nombreReporte + ".jasper";
					File reportFile2 = new File(session.getServletContext().getRealPath(directorio));
					byte[] bytes2 = null;
					bytes2 = JasperRunManager.runReportToPdf(reportFile2.getPath(), mapa, new JRBeanCollectionDataSource(listaObservaciones));
					if (bytes2 != null) {
						//String nombre = nombreArchivo + FiseConstants.EXTENSIONARCHIVO_PDF;
						String nombre = FormatoUtil.nombreIndividualAnexoObs(formato.getId().getCodEmpresa(), formato.getId().getAnoPresentacion(), formato.getId().getMesPresentacion(), FiseConstants.TIPO_FORMATO_13A);
						FileEntry archivo2 = fiseUtil.subirDocumentoBytes(request, bytes2, "application/pdf", nombre);
						if (archivo2 != null) {
							FileEntryJSP fileEntryJsp = new FileEntryJSP();
							fileEntryJsp.setNombreArchivo(nombre);
							fileEntryJsp.setFileEntry(archivo2);
							listaArchivo.add(fileEntryJsp);
						}
					}
				}
				/** REPORTE ACTA DE ENVIO */
				nombreReporte = "actaEnvio";
				nombreArchivo = nombreReporte;
				directorio = "/reports/" + nombreReporte + ".jasper";
				File reportFile3 = new File(session.getServletContext().getRealPath(directorio));
				byte[] bytes3 = null;
				bytes3 = JasperRunManager.runReportToPdf(reportFile3.getPath(), mapa, new JREmptyDataSource());
				if (bytes3 != null) {
					session.setAttribute("bytesActaEnvio", bytes3);
					//String nombre = nombreArchivo + FiseConstants.EXTENSIONARCHIVO_PDF;
					String nombre = FormatoUtil.nombreIndividualActaRemision(formato.getId().getCodEmpresa(), formato.getId().getAnoPresentacion(), formato.getId().getMesPresentacion(), FiseConstants.TIPO_FORMATO_13A);
					FileEntry archivo = fiseUtil.subirDocumentoBytes(request, bytes3, "application/pdf", nombre);
					if (archivo != null) {
						FileEntryJSP fileEntryJsp = new FileEntryJSP();
						fileEntryJsp.setNombreArchivo(nombre);
						fileEntryJsp.setFileEntry(archivo);
						listaArchivo.add(fileEntryJsp);
					}
				}
				//
				if (listaArchivo != null && listaArchivo.size() > 0) {
					// obtener e nombre del formato
					fiseUtil.enviarMailsAdjunto(
			    			   request,
			    			   listaArchivo, 
			    			   fiseUtil.getMapaEmpresa().get(formato.getId().getCodEmpresa()),
			    			   formato.getId().getAnoPresentacion(),
			    			   formato.getId().getMesPresentacion(),
			    			   FiseConstants.TIPO_FORMATO_13A,
			    			   descripcionFormato);
				}
			}

			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();
			pw.write(jsonArray.toString());
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}

	public void cargarListaObservaciones(List<FiseFormato13AD> listaDetalle) {
		int cont = 0;
		listaObservaciones = new ArrayList<MensajeErrorBean>();
		for (FiseFormato13AD detalle : listaDetalle) {
			List<FiseFormato13ADOb> listaObser = formatoService.listarFormato13ADObByFormato13AD(detalle);
			for (FiseFormato13ADOb observacion : listaObser) {
				cont++;
				MensajeErrorBean obs = new MensajeErrorBean();
				obs.setId(cont);
				obs.setDescZonaBenef(fiseUtil.getMapaZonaBenef().get(observacion.getId().getIdZonaBenef()));
				obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
				obs.setDescripcion(mapaErrores.get(observacion.getFiseObservacion().getIdObservacion()));
				obs.setDescCodSectorTipico(mapaSectorTipico.get(observacion.getId().getCodSectorTipico()));
				listaObservaciones.add(obs);
			}
		}
	}

	@ResourceMapping("reporteEnvioDefinitivo")
	public void reporteEnvioDefinitivo(ResourceRequest request, ResourceResponse response) {
		try {
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
			HttpSession session = httpRequest.getSession();

			JSONArray jsonArray = new JSONArray();

			String tipoFormato = FiseConstants.TIPO_FORMATO_ACTAENVIO;
			String tipoArchivo = FiseConstants.FORMATO_EXPORT_ACTAENVIO;

			session.setAttribute("tipoFormato", tipoFormato);
			session.setAttribute("tipoArchivo", tipoArchivo);

			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();
			pw.write(jsonArray.toString());
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@ResourceMapping("deleteDetalle")
	public void deleteDetalle(ResourceRequest request, ResourceResponse response, @ModelAttribute("formato13AGartCommand") Formato13AGartCommand command) {
		try {
			JSONObject jsonObj = new JSONObject();
			FiseFormato13AC formato = new FiseFormato13AC();

			String codEmpresa = command.getCodEmpresa();
			String anoPresentacion = command.getAnioPresentacion();
			String mesPresentacion = command.getMesPresentacion();
			String etapa = command.getEtapa();

			String codUbigeo = request.getParameter("codUbigeo").trim();
			String idZonaBenef = request.getParameter("idZonaBenef").trim();

			FiseFormato13ACPK pk = new FiseFormato13ACPK();
			pk.setCodEmpresa(codEmpresa);
			pk.setAnoPresentacion(new Long(anoPresentacion));
			pk.setMesPresentacion(new Long(mesPresentacion));
			pk.setEtapa(etapa);

			formato = formatoService.obtenerFormato13ACByPK(pk);
			try {
				if (formato != null) {

					this.eliminarDetalleDeclarado(formato.getFiseFormato13ADs(), idZonaBenef, codUbigeo);
					jsonObj.put("resultado", "OK");
				}
			} catch (Exception e) {
				jsonObj.put("resultado", "Error");
				jsonObj.put("mensaje", e.getMessage());
				System.out.println("Error al eliminar datos " + e.getMessage());
			}
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();
			pw.write(jsonObj.toString());
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@ResourceMapping("deleteCabecera")
	public void deleteCabecera(ResourceRequest request, ResourceResponse response, @ModelAttribute("formato13AGartCommand") Formato13AGartCommand command) {
		try {
			JSONObject jsonObj = new JSONObject();
			FiseFormato13AC formato = new FiseFormato13AC();

			String codEmpresa = request.getParameter("codigoEmpresa").trim();
			String anoPresentacion = request.getParameter("anoPresentacion").trim();
			String mesPresentacion = request.getParameter("mesPresentacion").trim();
			String etapa = request.getParameter("codEtapa").trim();

			FiseFormato13ACPK pk = new FiseFormato13ACPK();
			pk.setCodEmpresa(codEmpresa);
			pk.setAnoPresentacion(new Long(anoPresentacion));
			pk.setMesPresentacion(new Long(mesPresentacion));
			pk.setEtapa(etapa);

			formato = formatoService.obtenerFormato13ACByPK(pk);
			try {
				if (formato != null) {
					formatoService.eliminarCabecera(formato);
					jsonObj.put("resultado", "OK");
				}
			} catch (Exception e) {
				jsonObj.put("resultado", "Error");
				jsonObj.put("mensaje", e.getMessage());
				System.out.println("Error al eliminar datos " + e.getMessage());
			}
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();
			pw.write(jsonObj.toString());
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@ResourceMapping("reporteActaEnvioView")
	public void reporteActaEnvio(ResourceRequest request,ResourceResponse response, @ModelAttribute("formato13AGartCommand") Formato13AGartCommand command) {
		try {
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
			HttpSession session = httpRequest.getSession();
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			JSONArray jsonArray = new JSONArray();	
			    
			FiseFormato13AC formato = new FiseFormato13AC();
			
			String tipoFormato = FiseConstants.TIPO_FORMATO_ACTAENVIO;
			String tipoArchivo = FiseConstants.FORMATO_EXPORT_PDF;
			
			CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO13A);
			String descripcionFormato = "";
			if( tabla!=null ){
				descripcionFormato = tabla.getDescripcionTabla();
			}
			
			String codEmpresa = command.getCodEmpresa();
			String anoPresentacion = command.getAnioPresentacion();
			String mesPresentacion = command.getMesPresentacion();
			String etapa = command.getEtapa();

			String nombreReporte = "actaEnvio";
		    String nombreArchivo = nombreReporte;
			
		    FiseFormato13ACPK pk = new FiseFormato13ACPK();
			pk.setCodEmpresa(codEmpresa);
			pk.setAnoPresentacion(new Long(anoPresentacion));
			pk.setMesPresentacion(new Long(mesPresentacion));
			pk.setEtapa(etapa);
			
			formato = formatoService.obtenerFormato13ACByPK(pk);
			if( formato!=null ){
				Map<String, Object> mapa = new HashMap<String, Object>();
				mapa.put(FiseConstants.PARAM_IMG_LOGOTIPO, session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg"));
				mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
				mapa.put(FiseConstants.PARAM_USUARIO, themeDisplay.getUser().getLogin());
				mapa.put(FiseConstants.PARAM_NOMBRE_FORMATO, descripcionFormato);
				mapa.put(FiseConstants.PARAM_FECHA_ENVIO, formato.getFechaEnvioDefinitivo());
				mapa.put(FiseConstants.PARAM_NRO_OBSERVACIONES, (listaObservaciones!=null && !listaObservaciones.isEmpty())?listaObservaciones.size():0);
				mapa.put(FiseConstants.PARAM_MSG_OBSERVACIONES, (listaObservaciones!=null && !listaObservaciones.isEmpty())?FiseConstants.MSG_OBSERVACION_REPORTE_LLENO:FiseConstants.MSG_OBSERVACION_REPORTE_VACIO);
				
				mapa.put(FiseConstants.PARAM_ANO_INICIO_VIGENCIA, formato.getAnoInicioVigenciaDetalle());
				mapa.put(FiseConstants.PARAM_ANO_FIN_VIGENCIA, formato.getAnoFinVigenciaDetalle());

				mapa.put(FiseConstants.PARAM_FECHA_REGISTRO, formato.getFechaCreacion());
				mapa.put(FiseConstants.PARAM_USUARIO_REGISTRO, formato.getUsuarioCreacion());
				String dirCheckedImage = session.getServletContext().getRealPath("/reports/checked.jpg");
				String dirUncheckedImage = session.getServletContext().getRealPath("/reports/unchecked.jpg");
				mapa.put(FiseConstants.PARAM_IMG_CHECKED, dirCheckedImage);
				mapa.put(FiseConstants.PARAM_IMG_UNCHECKED, dirUncheckedImage);
				boolean cumplePlazo = false;
				cumplePlazo = commonService.fechaEnvioCumplePlazo(
						FiseConstants.TIPO_FORMATO_13A, 
						formato.getId().getCodEmpresa(), 
						formato.getId().getAnoPresentacion(), 
						formato.getId().getMesPresentacion(), 
						formato.getId().getEtapa(), 
						FechaUtil.fecha_DD_MM_YYYY(formato.getFechaEnvioDefinitivo()));
				if( cumplePlazo ){
					mapa.put(FiseConstants.PARAM_CHECKED_CUMPLEPLAZO, dirCheckedImage);
				}else{
					mapa.put(FiseConstants.PARAM_CHECKED_CUMPLEPLAZO, dirUncheckedImage);
				}
				if( listaObservaciones!=null && !listaObservaciones.isEmpty() ){
					mapa.put(FiseConstants.PARAM_CHECKED_OBSERVACION, dirUncheckedImage);
				}else{
					mapa.put(FiseConstants.PARAM_CHECKED_OBSERVACION, dirCheckedImage);
				}
				mapa.put(FiseConstants.PARAM_DESC_EMPRESA, fiseUtil.getMapaEmpresa().get(formato.getId().getCodEmpresa()));
				mapa.put(FiseConstants.PARAM_ANO_PRESENTACION, formato.getId().getAnoPresentacion());
				mapa.put(FiseConstants.PARAM_DESC_MES_PRESENTACION, fiseUtil.getMapaMeses().get(formato.getId().getMesPresentacion()));
				mapa.put(FiseConstants.PARAM_ETAPA, formato.getId().getEtapa());
				
				session.setAttribute("mapa", mapa);
			}
			session.setAttribute("nombreReporte",nombreReporte);
		    session.setAttribute("nombreArchivo",nombreArchivo);
			session.setAttribute("tipoFormato",tipoFormato);
			session.setAttribute("tipoArchivo",tipoArchivo);
				
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();
			pw.write(jsonArray.toString());
			pw.flush();
			pw.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * metodos utiles
	 * 
	 * @throws Exception
	 */

	public void agregarFormato13Detalle(List<FiseFormato13AD> listaDetalle) throws DataIntegrityViolationException, Exception {
		if (listaDetalle != null && listaDetalle.size() > 0) {
			for (FiseFormato13AD detalle : listaDetalle) {
				formatoService.savedetalle(detalle);

			}
		}
	}

	public void modificarFormato13Detalle(List<FiseFormato13AD> listaDetalle) {
		if (listaDetalle != null && listaDetalle.size() > 0) {
			for (FiseFormato13AD detalle : listaDetalle) {
				formatoService.updatedetalle(detalle);
			}
		}
	}

	public void agregarSectorTipico(ThemeDisplay themeDisplay, String sectorTipico, Formato13AGartCommand command, List<FiseFormato13AD> listaDetalle) {

		Date hoy = FechaUtil.obtenerFechaActual();

		try {

			FiseFormato13AD detalle = new FiseFormato13AD();
			FiseFormato13ADPK pk = new FiseFormato13ADPK();
			pk.setCodEmpresa(command.getCodEmpresa());
			pk.setAnoPresentacion(Long.parseLong(command.getAnioPresentacion()));
			pk.setMesPresentacion(Long.parseLong(command.getMesPresentacion()));
			pk.setEtapa(command.getEtapa());
			pk.setCodUbigeo(command.getCodDistrito());
			pk.setCodSectorTipico(sectorTipico);
			pk.setIdZonaBenef(Long.parseLong(command.getIdZonaBenef()));
			detalle.setId(pk);
			detalle.setAnoAlta(Long.parseLong(command.getAnioAlta()));
			detalle.setMesAlta(Long.parseLong(command.getMesAlta()));
			// luego verificar de donde se obtendra los valores de ano e inicio
			// de vigencia
			detalle.setAnoInicioVigencia(Long.parseLong(command.getAnioInicioVigencia()));
			detalle.setAnoFinVigencia(Long.parseLong(command.getAnioFinVigencia()));
			//
			detalle.setDescripcionLocalidad(command.getLocalidad());
			detalle.setNombreSedeAtiende(command.getNombreSede());
			if (FiseConstants.SECTOR_TIPICO_1_COD.equals(sectorTipico.trim())) {
				detalle.setNumeroBenefiPoteSectTipico(Long.parseLong(command.getSt1()));
			} else if (FiseConstants.SECTOR_TIPICO_2_COD.equals(sectorTipico.trim())) {
				detalle.setNumeroBenefiPoteSectTipico(Long.parseLong(command.getSt2()));
			} else if (FiseConstants.SECTOR_TIPICO_3_COD.equals(sectorTipico.trim())) {
				detalle.setNumeroBenefiPoteSectTipico(Long.parseLong(command.getSt3()));
			} else if (FiseConstants.SECTOR_TIPICO_4_COD.equals(sectorTipico.trim())) {
				detalle.setNumeroBenefiPoteSectTipico(Long.parseLong(command.getSt4()));
			} else if (FiseConstants.SECTOR_TIPICO_5_COD.equals(sectorTipico.trim())) {
				detalle.setNumeroBenefiPoteSectTipico(Long.parseLong(command.getSt5()));
			} else if (FiseConstants.SECTOR_TIPICO_6_COD.equals(sectorTipico.trim())) {
				detalle.setNumeroBenefiPoteSectTipico(Long.parseLong(command.getSt6()));
			} else if (FiseConstants.SECTOR_TIPICO_SER_COD.equals(sectorTipico.trim())) {
				detalle.setNumeroBenefiPoteSectTipico(Long.parseLong(command.getStser()));
			} else if (FiseConstants.SECTOR_TIPICO_ESP_COD.equals(sectorTipico.trim())) {
				detalle.setNumeroBenefiPoteSectTipico(Long.parseLong(command.getStesp()));
			}
			detalle.setUsuarioCreacion(themeDisplay.getUser().getLogin());
			detalle.setTerminalCreacion(themeDisplay.getUser().getLoginIP());
			detalle.setFechaCreacion(hoy);
			detalle.setUsuarioActualizacion(themeDisplay.getUser().getLogin());
			detalle.setTerminalActualizacion(themeDisplay.getUser().getLoginIP());
			detalle.setFechaActualizacion(hoy);
			listaDetalle.add(detalle);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void modificarSectorTipico(ThemeDisplay themeDisplay, String sectorTipico, Formato13AGartCommand command, FiseFormato13AD detalle) {

		Date hoy = FechaUtil.obtenerFechaActual();

		try {
			// verificar que campos son editables en la vista de modificacion
			detalle.setAnoAlta(Long.parseLong(command.getAnioAlta()));
			detalle.setMesAlta(Long.parseLong(command.getMesAlta()));
			// luego verificar de donde se obtendra los valores de ano e inicio
			// de vigencia
			detalle.setAnoInicioVigencia(Long.parseLong(command.getAnioInicioVigencia()));
			detalle.setAnoFinVigencia(Long.parseLong(command.getAnioFinVigencia()));
			//
			detalle.setDescripcionLocalidad(command.getLocalidad());
			detalle.setNombreSedeAtiende(command.getNombreSede());
			if (FiseConstants.SECTOR_TIPICO_1_COD.equals(sectorTipico.trim())) {
				detalle.setNumeroBenefiPoteSectTipico(Long.parseLong(command.getSt1()));
			} else if (FiseConstants.SECTOR_TIPICO_2_COD.equals(sectorTipico.trim())) {
				detalle.setNumeroBenefiPoteSectTipico(Long.parseLong(command.getSt2()));
			} else if (FiseConstants.SECTOR_TIPICO_3_COD.equals(sectorTipico.trim())) {
				detalle.setNumeroBenefiPoteSectTipico(Long.parseLong(command.getSt3()));
			} else if (FiseConstants.SECTOR_TIPICO_4_COD.equals(sectorTipico.trim())) {
				detalle.setNumeroBenefiPoteSectTipico(Long.parseLong(command.getSt4()));
			} else if (FiseConstants.SECTOR_TIPICO_5_COD.equals(sectorTipico.trim())) {
				detalle.setNumeroBenefiPoteSectTipico(Long.parseLong(command.getSt5()));
			} else if (FiseConstants.SECTOR_TIPICO_6_COD.equals(sectorTipico.trim())) {
				detalle.setNumeroBenefiPoteSectTipico(Long.parseLong(command.getSt6()));
			} else if (FiseConstants.SECTOR_TIPICO_SER_COD.equals(sectorTipico.trim())) {
				detalle.setNumeroBenefiPoteSectTipico(Long.parseLong(command.getStser()));
			} else if (FiseConstants.SECTOR_TIPICO_ESP_COD.equals(sectorTipico.trim())) {
				detalle.setNumeroBenefiPoteSectTipico(Long.parseLong(command.getStesp()));
			}
			detalle.setUsuarioActualizacion(themeDisplay.getUser().getLogin());
			detalle.setTerminalActualizacion(themeDisplay.getUser().getLoginIP());
			detalle.setFechaActualizacion(hoy);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public FiseFormato13AC modificarEnvioDefinitivoFormato13AC(ThemeDisplay themeDisplay, FiseFormato13AC fiseFormato13AC) throws Exception {

		FiseFormato13AC dto = null;
		Date hoy = FechaUtil.obtenerFechaActual();
		try {
			fiseFormato13AC.setFechaEnvioDefinitivo(hoy);
			fiseFormato13AC.setUsuarioActualizacion(themeDisplay.getUser().getLogin());
			fiseFormato13AC.setTerminalActualizacion(themeDisplay.getUser().getLoginIP());
			fiseFormato13AC.setFechaActualizacion(hoy);
			formatoService.updatecabecera(fiseFormato13AC);
			dto = fiseFormato13AC;
		} catch (Exception e) {
			logger.error("--error" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return dto;
	}

	public void eliminarDetalleDeclarado(List<FiseFormato13AD> listaDetalle, String idZonaBenef, String codUbigeo) {
		for (FiseFormato13AD detalle : listaDetalle) {
			if (Long.parseLong(idZonaBenef) == detalle.getId().getIdZonaBenef() && codUbigeo.equals(detalle.getId().getCodUbigeo())) {
				if (FiseConstants.SECTOR_TIPICO_1_COD.equals(detalle.getId().getCodSectorTipico().trim())) {
					formatoService.eliminarDetalle(detalle);
				} else if (FiseConstants.SECTOR_TIPICO_2_COD.equals(detalle.getId().getCodSectorTipico().trim())) {
					formatoService.eliminarDetalle(detalle);
				} else if (FiseConstants.SECTOR_TIPICO_3_COD.equals(detalle.getId().getCodSectorTipico().trim())) {
					formatoService.eliminarDetalle(detalle);
				} else if (FiseConstants.SECTOR_TIPICO_4_COD.equals(detalle.getId().getCodSectorTipico().trim())) {
					formatoService.eliminarDetalle(detalle);
				} else if (FiseConstants.SECTOR_TIPICO_5_COD.equals(detalle.getId().getCodSectorTipico().trim())) {
					formatoService.eliminarDetalle(detalle);
				} else if (FiseConstants.SECTOR_TIPICO_6_COD.equals(detalle.getId().getCodSectorTipico().trim())) {
					formatoService.eliminarDetalle(detalle);
				} else if (FiseConstants.SECTOR_TIPICO_SER_COD.equals(detalle.getId().getCodSectorTipico().trim())) {
					formatoService.eliminarDetalle(detalle);
				} else if (FiseConstants.SECTOR_TIPICO_ESP_COD.equals(detalle.getId().getCodSectorTipico().trim())) {
					formatoService.eliminarDetalle(detalle);
				}
			}
		}
	}

}
