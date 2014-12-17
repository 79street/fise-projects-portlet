package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.Formato12C12D13Generic;
import gob.osinergmin.fise.bean.Formato12CCBean;
import gob.osinergmin.fise.bean.Formato12CMensajeBean;
import gob.osinergmin.fise.bean.MensajeErrorBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.AdmUbigeo;
import gob.osinergmin.fise.domain.CfgTabla;
import gob.osinergmin.fise.domain.FiseFormato12CC;
import gob.osinergmin.fise.domain.FiseFormato12CCPK;
import gob.osinergmin.fise.domain.FiseFormato12CD;
import gob.osinergmin.fise.domain.FiseFormato12CDOb;
import gob.osinergmin.fise.domain.FisePeriodoEnvio;
import gob.osinergmin.fise.gart.json.Formato12CGartJSON;
import gob.osinergmin.fise.gart.jsp.FileEntryJSP;
import gob.osinergmin.fise.gart.service.CfgCampoGartService;
import gob.osinergmin.fise.gart.service.CfgTablaGartService;
import gob.osinergmin.fise.gart.service.CommonGartService;
import gob.osinergmin.fise.gart.service.FisePeriodoEnvioGartService;
import gob.osinergmin.fise.gart.service.Formato12CGartService;
import gob.osinergmin.fise.util.FechaUtil;
import gob.osinergmin.fise.util.FormatoUtil;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
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

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.FileMimeTypeException;

@SessionAttributes({ "esAdministrador" })
@Controller("formato12CGartController")
@RequestMapping("VIEW")
public class Formato12CGartController {
	
	Logger logger = Logger.getLogger(Formato12CGartController.class);
	
	private static final String CRUD_CREATE = "CREATE";
	private static final String CRUD_UPDATE = "UPDATE";
	private static final String CRUD_READ = "READ";
	private static final String CRUD_READ_CREATEUPDATE = "READCREATEUPDATE";

	@Autowired
	@Qualifier("fiseUtil")
	private FiseUtil fiseUtil;

	@Autowired
	@Qualifier("formato12CGartServiceImpl")
	Formato12CGartService formatoService;
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
	Map<String, String> mapaEmpresa;
	Map<Long, String> mapaMeses;
	Map<Long, String> mapaZonaBenef;
	Map<Long, String> mapaEtapaEjecucion;
	Map<String, String> mapaTipoDocumento;
	Map<String, String> mapaUbigeo;
	
	List<FisePeriodoEnvio> listaPeriodoEnvio;

	@RequestMapping
	public String defaultView(ModelMap model, RenderRequest renderRequest, RenderResponse renderResponse, @ModelAttribute("formato12CCBean") Formato12CCBean bean) {

		listaPeriodoEnvio = new ArrayList<FisePeriodoEnvio>();
		
		bean.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));
		bean.setListaMes(fiseUtil.getMapaMeses());
		bean.setAnioDesde(fiseUtil.obtenerNroAnioFechaActual());
		bean.setMesDesde(String.valueOf(Integer.parseInt(fiseUtil.obtenerNroMesFechaActual()) - 1));
		bean.setAnioHasta(fiseUtil.obtenerNroAnioFechaActual());
		bean.setMesHasta(fiseUtil.obtenerNroMesFechaActual());
		
		model.addAttribute("esAdministrador", fiseUtil.esAdministrador(renderRequest));

		mapaErrores = fiseUtil.getMapaErrores();
		mapaSectorTipico = fiseUtil.getMapaSectorTipico();
		mapaEmpresa = fiseUtil.getMapaEmpresa();
		mapaMeses = fiseUtil.getMapaMeses();
		mapaZonaBenef = fiseUtil.getMapaZonaBenef();
		mapaEtapaEjecucion = fiseUtil.getMapaEtapaEjecucion();
		
		mapaTipoDocumento = fiseUtil.getMapTipoDocumento();
		mapaUbigeo = fiseUtil.getMapaUbigeo();

		return "formato12CInicio";
	}

	@ResourceMapping("busqueda")
	public void grid(ModelMap model, ResourceRequest request, ResourceResponse response, @ModelAttribute("formato12CCBean") Formato12CCBean bean) {

		try {
			response.setContentType("application/json");
			
			HttpServletRequest req = PortalUtil.getHttpServletRequest(request);
			HttpSession session = req.getSession();
			List<FiseFormato12CC> listaFormato;
			JSONArray jsonArray = new JSONArray();

			String codEmpresa = bean.getCodEmpresaB();
			String anioDesde = bean.getAnioDesde();
			String mesDesde = bean.getMesDesde();
			String anioHasta = bean.getAnioHasta();
			String mesHasta = bean.getMesHasta();
			String etapa = bean.getEtapaB();

			listaFormato = formatoService.buscarFormato12CC(
					(codEmpresa!=null&&codEmpresa!="")?FormatoUtil.rellenaDerecha(codEmpresa, ' ', 4):"", 
		  					(anioDesde!=null&&anioDesde!="")?Long.parseLong(anioDesde):0, 
		  					(mesDesde!=null&&mesDesde!="")?Long.parseLong(mesDesde):0, 
		  					(anioHasta!=null&&anioHasta!="")?Long.parseLong(anioHasta):0, 
		  					(mesHasta!=null&&mesHasta!="")?Long.parseLong(mesHasta):0, 
		  					(etapa!=null&&etapa!="")?etapa:""
		  					);

			for (FiseFormato12CC formato : listaFormato) {
				formato.setDescEmpresa(mapaEmpresa.get(formato.getId().getCodEmpresa()));
				formato.setDescMesPresentacion(fiseUtil.getMapaMeses().get(formato.getId().getMesPresentacion()));
  				
  				//grupo informacion y estado
  				if(formato.getFiseGrupoInformacion()!=null && formato.getFiseGrupoInformacion().getDescripcion()!=null){
  					formato.setDescGrupoInformacion(formato.getFiseGrupoInformacion().getDescripcion());
  				}else{
  					formato.setDescGrupoInformacion(FiseConstants.BLANCO);
  				}
  				if(formato.getFechaEnvioDefinitivo()!=null){
  					formato.setDescEstado(FiseConstants.ESTADO_FECHAENVIO_ENVIADO);
  				}else{
  					formato.setDescEstado(FiseConstants.ESTADO_FECHAENVIO_POR_ENVIAR);
  				}
  				
  				/**Obteniendo el flag de la operacion*/
  				String flagOper = commonService.obtenerEstadoProceso(formato.getId().getCodEmpresa(),FiseConstants.TIPO_FORMATO_12C,formato.getId().getAnoPresentacion(),
  						formato.getId().getMesPresentacion(), formato.getId().getEtapa());
  				logger.info("flag operacion:  "+flagOper);
  				
  				jsonArray.put(new Formato12CGartJSON().asJSONObject(formato,"",flagOper));
			}

			fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_FORMATO_12C, FiseConstants.NOMBRE_EXCEL_FORMATO12C, FiseConstants.NOMBRE_HOJA_FORMATO12C, listaFormato);

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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "action=nuevo")
	public String nuevoFormato(ModelMap model, RenderRequest renderRequest, RenderResponse renderResponse, @ModelAttribute("formato12CCBean") Formato12CCBean bean) {
		System.out.println("aqui en nuevoFormato");
		
		PortletRequest pRequest = (PortletRequest)renderRequest.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);

		String codEmpresa = renderRequest.getParameter("codEmpresa");
		String periodo = renderRequest.getParameter("periodoDeclaracion");
		String read = renderRequest.getParameter("readonly");
		String tipoOperacion = renderRequest.getParameter("tipoOperacion");
		
		String msg = renderRequest.getParameter("msgTrans");
		
		List<MensajeErrorBean> listaError = (List<MensajeErrorBean>)pRequest.getPortletSession().getAttribute("listaError", PortletSession.APPLICATION_SCOPE);
		
		//String descPeriodo = renderRequest.getParameter("descripcionPeriodo");
		//String error = renderRequest.getParameter("error");

		//String codEmpresaHidden = renderRequest.getParameter("codEmpresaHidden");
		//String descPeriodoHidden = renderRequest.getParameter("descripcionPeriodoHidden");
		
		String descGrupoInformacion = renderRequest.getParameter("descGrupoInformacion");
		String descEstado = renderRequest.getParameter("descestado");
		
		//String anioInicioVigencia = renderRequest.getParameter("anioInicioVigencia");
		//String anioFinVigencia = renderRequest.getParameter("anioFinVigencia");
		
		

		System.out.println("codEmpresa::>" + codEmpresa);
		System.out.println("periodo::>" + periodo);

		/*if (error != null) {
			model.addAttribute("error", error);
		}*/

		if (codEmpresa != null) {
			bean.setCodigoEmpresa(codEmpresa);
		}
		//if (periodo != null) {
		//	bean.setPeridoDeclaracion(periodo);
		//}
		if (descGrupoInformacion != null) {
			bean.setDescGrupoInformacion(descGrupoInformacion);
		}
		if (descEstado != null) {
			bean.setDescEstado(descEstado);
		}
		

		bean.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));

		bean.setReadOnly(read != null ? Boolean.parseBoolean(read) : false);
		//bean.setTipoOperacion((tipoOperacion != null && tipoOperacion.equalsIgnoreCase(String.valueOf(FiseConstants.UPDATE))) ? FiseConstants.UPDATE : FiseConstants.ADD);

		model.addAttribute("crud", (tipoOperacion != null && tipoOperacion.equalsIgnoreCase(String.valueOf(FiseConstants.UPDATE))) ? CRUD_UPDATE : CRUD_CREATE);
		model.addAttribute("readonly", "false");
		if( listaError!=null && listaError.size()>0){
			model.addAttribute("listaError", listaError);
		}
		model.addAttribute("msgTrans", msg);
		
		pRequest.getPortletSession().setAttribute("listaError", null, PortletSession.APPLICATION_SCOPE);

		return "formato12CCRUD";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "action=viewedit")
	public String viewEditFormato(ModelMap model, RenderRequest request, RenderResponse response, @ModelAttribute("formato12CCBean") Formato12CCBean bean) {

		PortletRequest pRequest = (PortletRequest)request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
		
		try {

			String tipo = request.getParameter("tipo");
			String codEmpresa = request.getParameter("codEmpresa");
			String periodoEnvio = request.getParameter("periodoEnvio");
			String anio = request.getParameter("anioPresentacion");
			String mes = request.getParameter("mesPresentacion");
			String etapa = request.getParameter("etapa");
			
			String msg = request.getParameter("msgTrans");
			
			List<MensajeErrorBean> listaError = (List<MensajeErrorBean>)pRequest.getPortletSession().getAttribute("listaError", PortletSession.APPLICATION_SCOPE);
			
			String desGrupoInformacion = request.getParameter("descGrupoInformacion");
			String descEstado = request.getParameter("descEstado");

			bean.setReadOnly(true);
			bean.setCodigoEmpresa(codEmpresa.trim());
			
			//guardamos el codigo de empresa
			bean.setCodigoEmpresaHidden(codEmpresa.trim());
			bean.setPeriodoEnvioHidden(periodoEnvio);
			
			bean.setAnioPresentacion(Long.parseLong(anio));
			bean.setMesPresentacion(Long.parseLong(mes));
			bean.setEtapa(etapa);
			bean.setListaEmpresas(fiseUtil.getEmpresaxUsuario(request));
			bean.setListaPeriodo(periodoService.listarFisePeriodoEnvioMesAnioEtapa(codEmpresa, FiseConstants.NOMBRE_FORMATO_12C));
			//bean.setPeridoDeclaracion("" + anio + "" + mes + "" + etapa);
			
			bean.setPeriodoEnvioHidden(""+anio+FormatoUtil.rellenaIzquierda(mes, '0', 2)+etapa);

			//bean.setDescripcionPeriodo(FiseUtil.descripcionPeriodo(Long.parseLong(bean.getMesPresentacion()), Long.parseLong(bean.getAnioPresentacion()), bean.getEtapa()));
			//bean.setCodEmpresaHidden(bean.getCodEmpresa());
			//bean.setDescripcionPeriodoHidden(bean.getPeridoDeclaracion());
			
			//obtenemos el formato solo para mostrar el grupo de informacion y el estado
			FiseFormato12CCPK pk = new FiseFormato12CCPK();
			pk.setCodEmpresa(codEmpresa);
			pk.setAnoPresentacion((anio!=null&&!anio.equals(FiseConstants.BLANCO))?Long.parseLong(anio):0);
			pk.setMesPresentacion((mes!=null&&!mes.equals(FiseConstants.BLANCO))?Long.parseLong(mes):0);
			pk.setEtapa(etapa);
			FiseFormato12CC f = formatoService.obtenerFormato12CCByPK(pk);
			if(f != null){
				if(f.getFiseGrupoInformacion()!=null && f.getFiseGrupoInformacion().getDescripcion()!=null){
					f.setDescGrupoInformacion(f.getFiseGrupoInformacion().getDescripcion());
				}else{
					f.setDescGrupoInformacion(FiseConstants.BLANCO);
				}
				if(f.getFechaEnvioDefinitivo()!=null){
					f.setDescEstado(FiseConstants.ESTADO_FECHAENVIO_ENVIADO);
				}else{
					f.setDescEstado(FiseConstants.ESTADO_FECHAENVIO_POR_ENVIAR);
				}
				//ponemos los valores de decripciones
				if(desGrupoInformacion == null ){
					bean.setDescGrupoInformacion(f.getDescGrupoInformacion());
				}else{
					bean.setDescGrupoInformacion(desGrupoInformacion!=null?desGrupoInformacion:"");
				}
				if(descEstado == null){
					bean.setDescEstado(f.getDescEstado());
				}else{
					bean.setDescEstado(descEstado!=null?descEstado:"");
				}
			}
			
			if (tipo != null && tipo.equalsIgnoreCase(String.valueOf(FiseConstants.VIEW))) {
				model.addAttribute("crud", CRUD_READ);
				model.addAttribute("readonly", "true");

			}
			if (tipo != null && tipo.equalsIgnoreCase(String.valueOf(FiseConstants.UPDATE))) {
				model.addAttribute("crud", CRUD_UPDATE);
				model.addAttribute("readonly", "false");
				//bean.setTipoOperacion(FiseConstants.UPDATE);
				if( listaError!=null && listaError.size()>0){
					model.addAttribute("listaError", listaError);
				}
				
			}

			model.addAttribute("formato12CCBean", bean);
			model.addAttribute("msgTrans", msg);
			
			pRequest.getPortletSession().setAttribute("listaError", null, PortletSession.APPLICATION_SCOPE);

		} catch (Exception e) {
			System.out.println("entro error view");
			e.printStackTrace();
		}
		return "formato12CCRUD";
	}
	
	@ResourceMapping("busquedaDetalle")
	public void gridDetalle(ModelMap model, ResourceRequest request, ResourceResponse response, @ModelAttribute("formato12CCBean") Formato12CCBean bean) {

		try {
			System.out.println("busquedaDetalle");

			response.setContentType("application/json");
			
			HttpServletRequest req = PortalUtil.getHttpServletRequest(request);
			HttpSession session = req.getSession();
			List<FiseFormato12CD> listaFormato;
			
			listaFormato = new ArrayList<FiseFormato12CD>();
			
			JSONObject jsonObj = new JSONObject();
			
			JSONArray jsonArray = new JSONArray();
			
			JSONArray jsonArrayImplementacion = new JSONArray();
			JSONArray jsonArrayOperativa = new JSONArray();
			
			String tipo = request.getParameter("tipo");
			
			String codEmpresa = "";
			//String periodoDeclaracion = "";

			if (tipo != null && (tipo.equalsIgnoreCase("0") || tipo.equalsIgnoreCase("1"))) {

				System.out.println("codEmpresa::>" + request.getParameter("codEmpresa"));
				System.out.println("anoPresentacion::>" + request.getParameter("anioPresentacion"));

				codEmpresa = request.getParameter("codEmpresa");
				//periodoDeclaracion = request.getParameter("anioPresentacion") + request.getParameter("mesPresentacion") + request.getParameter("etapa");

				String anioPresentacion = request.getParameter("anioPresentacion");
				String mesPresentacion = request.getParameter("mesPresentacion");
				String etapa = request.getParameter("etapa");
				
				bean.setCodigoEmpresa(codEmpresa);
				bean.setAnioPresentacion(Long.parseLong(anioPresentacion));
				bean.setMesPresentacion(Long.parseLong(mesPresentacion));
				bean.setEtapa(etapa);
				//bean.setPeriodoEnvio(periodoDeclaracion);

				FiseFormato12CC formato12CC = new FiseFormato12CC();
				
				FiseFormato12CCPK pk = new FiseFormato12CCPK();
				pk.setCodEmpresa(codEmpresa);
				pk.setAnoPresentacion(Long.parseLong(anioPresentacion));
				pk.setMesPresentacion(Long.parseLong(mesPresentacion));
				pk.setEtapa(etapa);
				
				formato12CC = formatoService.obtenerFormato12CCByPK(pk);
				
				if( formato12CC != null ){
					listaFormato = formatoService.listarFormato12CDByFormato12CC(formato12CC);
					for (FiseFormato12CD formato : listaFormato) {
						//setear algunas descripciones
						formato.setDescMesEjecucion(mapaMeses.get(formato.getId().getMesEjecucionGasto()));
						formato.setDescEtapaEjecucion(mapaEtapaEjecucion.get(formato.getId().getEtapaEjecucion()));
						formato.setDescZonaBenef(mapaZonaBenef.get(formato.getIdZonaBenef()));
						
						//jsonArray.put(new Formato12CGartJSON().asJSONObject(formato));
						
						//jsonArray.put(new Formato12CGartJSON().asJSONObject(formato));
						
						if( FiseConstants.ETAPA_EJECUCION_IMPLEMENTACION_COD == formato.getId().getEtapaEjecucion() ){
							jsonArrayImplementacion.put(new Formato12CGartJSON().asJSONObject(formato));
						}else if( FiseConstants.ETAPA_EJECUCION_OPERATIVA_COD == formato.getId().getEtapaEjecucion() ){
							jsonArrayOperativa.put(new Formato12CGartJSON().asJSONObject(formato));
						}
						
					}
				}
				
			}
			
			fiseUtil.configuracionExportarExcelImplementacionMensual(session, FiseConstants.TIPO_FORMATO_12CD, listaFormato);
			
			logger.info("arreglo jsonarray:" + jsonArray);
			logger.info("arreglo jsonimplemtacion:" + jsonArrayImplementacion);
			logger.info("arreglo jsonoperativa:" + jsonArrayOperativa);
			logger.info("arreglo json:" + jsonObj);
			
			//guardamos en el jsobobj
			jsonObj.put("implementacion", jsonArrayImplementacion);
			jsonObj.put("operativa", jsonArrayOperativa);
			
			PrintWriter pw = response.getWriter();
			//pw.write(jsonArray.toString());
			pw.write(jsonObj.toString());
			//pw.write(jsonArrayImplementacion.toString());
			//pw.write(jsonArrayOperativa.toString());
			
			pw.flush();
			pw.close();

			model.addAttribute("formato12CCBean", bean);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/*@ResourceMapping("cargaPeriodoDeclaracion")
	public void cargaPeriodoDeclaracion(ModelMap model, SessionStatus status, ResourceRequest request, ResourceResponse response, 
			@RequestParam("codEmpresa") String codEmpresa, @ModelAttribute("formato12CCBean") Formato12CCBean bean) {
		try {
			logger.info("cargaPeriodoDeclaracion");
			logger.debug("-->cargaPeriodoDeclaracion");
			response.setContentType("applicacion/json");

			List<FisePeriodoEnvio> listaPeriodoEnvio = periodoService.listarFisePeriodoEnvioMesAnioEtapa(codEmpresa, FiseConstants.TIPO_FORMATO_12C);

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
	}*/
	
	@ResourceMapping("cargaPeriodo")
  	public void cargaPeriodo(ModelMap model, ResourceRequest request,ResourceResponse response,@ModelAttribute("formato12CCBean") Formato12CCBean bean){
		try {			
  			response.setContentType("applicacion/json");
  			String codEmpresa = bean.getCodigoEmpresa();
  			
  			listaPeriodoEnvio = periodoService.listarFisePeriodoEnvioMesAnioEtapa(codEmpresa, FiseConstants.TIPO_FORMATO_12C);
  			bean.setListaPeriodoEnvio(listaPeriodoEnvio);
  			
  			JSONArray jsonArray = new JSONArray();
  			for (FisePeriodoEnvio periodo : bean.getListaPeriodoEnvio()) {
  				JSONObject jsonObj = new JSONObject();
				jsonObj.put("codigoItem", periodo.getCodigoItem());				
				jsonObj.put("descripcionItem", periodo.getDescripcionItem());			
				jsonObj.put("flagPeriodoEjecucion", periodo.getFlagPeriodoEjecucion());	
				//agregar los valores
				jsonArray.put(jsonObj);		
			}
  			
  		    PrintWriter pw = response.getWriter();
  		    logger.info(jsonArray.toString());
  		    pw.write(jsonArray.toString());
  		    pw.flush();
  		    pw.close();							
  		}catch (Exception e) {
  			// TODO: handle exception
  			e.printStackTrace();
  		}
	}
	
	@ResourceMapping("cargaFlagPeriodo")
  	public void cargaFlagPeriodo(ResourceRequest request,ResourceResponse response,@ModelAttribute("formato12CCBean") Formato12CCBean bean){
		try {			
  			response.setContentType("applicacion/json");
  			String periodoEnvio = bean.getPeriodoEnvio();
  			JSONObject jsonObj = new JSONObject();
  			for (FisePeriodoEnvio p : listaPeriodoEnvio) {
				if( periodoEnvio.equals(p.getCodigoItem()) ){
					jsonObj.put("flagPeriodoEjecucion", p.getFlagPeriodoEjecucion());
					jsonObj.put("anioInicioVigencia", p.getAnioInicioVig());
					jsonObj.put("anioFinVigencia", p.getAnioFinVig());
					break;
				}
			}
  			System.out.println(jsonObj.toString());
  			PrintWriter pw = response.getWriter();
  		    pw.write(jsonObj.toString());
  		    pw.flush();
  		    pw.close();							
  		}catch (Exception e) {
  			// TODO: handle exception
  			e.printStackTrace();
  		}
	}
	
	@ActionMapping(params = "action=nuevoDetalle")
	public void nuevoDetalleFormato(ModelMap model, ActionRequest request, ActionResponse response, @ModelAttribute("formato12CCBean") Formato12CCBean bean) {
		String codEmpresa = bean.getCodigoEmpresa();
		//String periodoDeclaracion = command.getPeridoDeclaracion();
		String periodoDeclaracion = bean.getPeriodoEnvio();//se obtiene el valor del periodo guardado de el campo descripcionPeriodoHidden(valorPeriodoHidden), probar los demas flujos
		
		String msg = "";
		
		/*String anioPresentacion = "";
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
		FiseFormato12CCPK pk = new FiseFormato12CCPK();
		pk.setCodEmpresa(codEmpresa);
		pk.setAnoPresentacion(Long.parseLong(anioPresentacion));
		pk.setMesPresentacion(Long.parseLong(mesPresentacion));
		pk.setEtapa(etapa);

		FiseFormato12CC formato = new FiseFormato12CC();
		formato.setId(pk);

		// Primero buscamos si existe una cabecera
		formato = formatoService.obtenerFormato12CCByPK(pk);

		if (formato != null) {
			// update

		} else {
			// create
		}
*/
		if( codEmpresa==null ){
			codEmpresa = bean.getCodigoEmpresaHidden();
		}
		if( periodoDeclaracion==null ){
			periodoDeclaracion = bean.getPeriodoEnvioHidden();
		}
		
		response.setRenderParameter("crud", CRUD_CREATE);
		response.setRenderParameter("action", "detalle");
		
		response.setRenderParameter("msg",msg);
		
		response.setRenderParameter("codigoEmpresaDetalle", codEmpresa);
		response.setRenderParameter("periodoEnvioDetalle", periodoDeclaracion);
		
		response.setRenderParameter("anoPresentacionDetalle", FiseConstants.CERO);
		response.setRenderParameter("mesPresentacionDetalle", FiseConstants.CERO);
		response.setRenderParameter("etapaDetalle", FiseConstants.CERO);
		response.setRenderParameter("anoEjecucionDetalle", FiseConstants.CERO);
		response.setRenderParameter("mesEjecucionDetalle", FiseConstants.CERO);
		response.setRenderParameter("etapaEjecucionDetalle", FiseConstants.CERO);
		response.setRenderParameter("itemDetalle", FiseConstants.CERO);
		
		//--response.setRenderParameter("periodoEnvioDetalle", periodoDeclaracion);
		//--response.setRenderParameter("nroItemEtapa", FiseConstants.CERO);
	}

	@RequestMapping(params = "action=detalle")
	public String detalle(ModelMap model, RenderRequest renderRequest, RenderResponse renderResponse, @RequestParam("crud") String crud, @RequestParam("msg") String msg,
			@RequestParam("codigoEmpresaDetalle") String codEmpresa, 
			@RequestParam("periodoEnvioDetalle") String periodoDeclaracion,
			@RequestParam("anoPresentacionDetalle") String anoPresentacion,
			@RequestParam("mesPresentacionDetalle") String mesPresentacion,
			@RequestParam("etapaDetalle") String etapa,
			@RequestParam("anoEjecucionDetalle") String anoEjecucion,
			@RequestParam("mesEjecucionDetalle") String mesEjecucion,
			@RequestParam("etapaEjecucionDetalle") String etapaEjecucion,
			@RequestParam("itemDetalle") String nroItemEtapa,
			@ModelAttribute("formato12CCBean") Formato12CCBean bean) {

		//String codEmpresa = bean.getCodigoEmpresa();
		//String  periodoDeclaracion = bean.getPeriodoEnvio();
		
		
		logger.info("valores requestparameter" + crud);
		logger.info("valores periodoDeclaracion" + periodoDeclaracion);
		logger.info("valores codEmpresa" + codEmpresa);

		String anioPres = "";
		String mesPres = "";
		String etapaPres = "";

		if (periodoDeclaracion != null && periodoDeclaracion.length() > 6) {
			anioPres = periodoDeclaracion.substring(0, 4);
			mesPres = periodoDeclaracion.substring(4, 6);
			etapaPres = periodoDeclaracion.substring(6);
		}
		// Cabecera
		
		bean.setCodigoEmpresa(codEmpresa);
		bean.setPeriodoEnvio(periodoDeclaracion);
		bean.setDescEmpresa(mapaEmpresa.get(FormatoUtil.rellenaDerecha(codEmpresa, ' ', 4)));
		bean.setAnioPresentacion(Long.parseLong(anioPres));
		bean.setMesPresentacion(Long.parseLong(mesPres));
		bean.setEtapa(etapaPres);

		//
		bean.setListaMes(fiseUtil.getMapaMeses());
		bean.setListaZonaBenef(fiseUtil.getMapaZonaBenef());
		bean.setListaDepartamentos(fiseUtil.listaDepartamentos());
		bean.setListaEtapaEjecucion(fiseUtil.getMapaEtapaEjecucion());
		bean.setListaTipoDocumento(fiseUtil.getMapTipoDocumento());
		
		bean.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));
		bean.setListaPeriodoEnvio(listaPeriodoEnvio);
		
		//add item
		bean.setNroItemEtapa(Long.parseLong(nroItemEtapa));
		
		model.addAttribute("readonly", "false");

		// cargamos el ano y fin de vigencia
		//----List<FisePeriodoEnvio> listaPeriodoEnvio = periodoService.listarFisePeriodoEnvioMesAnioEtapa(codEmpresa, FiseConstants.TIPO_FORMATO_13A);
		for (FisePeriodoEnvio periodo : listaPeriodoEnvio) {
			if (periodoDeclaracion.equals(periodo.getCodigoItem())) {
				// verificamos el flag de periodo de ejecucion
				if ("S".equals(periodo.getFlagPeriodoEjecucion())) {
					model.addAttribute("readonlyFlagPeriodo", "false");
				} else {
					model.addAttribute("readonlyFlagPeriodo", "true");
				}
				break;
			}
		}
		
		if( "OK".equals(msg) || "DONE".equals(msg) ){
			//----
			if (CRUD_READ.equals(crud) || CRUD_READ_CREATEUPDATE.equals(crud) || CRUD_UPDATE.equals(crud)) {

				if (CRUD_READ.equals(crud) || CRUD_READ_CREATEUPDATE.equals(crud)) {
					// Es lectura
					model.addAttribute("readonly", "true");
					model.addAttribute("readonlyFlagPeriodo", "true");
					model.addAttribute("readonlyEdit", "true");
				} else if (CRUD_UPDATE.equals(crud)) {
					model.addAttribute("readonly", "true");
					model.addAttribute("readonlyFlagPeriodo", "true");
					model.addAttribute("readonlyEdit", "false");
				}
				
				bean.setAnioEjecucion(Long.parseLong(anoEjecucion));
				bean.setMesEjecucion(Long.parseLong(mesEjecucion));
				bean.setEtapaEjecucion(Long.parseLong(etapaEjecucion));
				//add hidden
				bean.setAnoEjecucionHidden(Long.parseLong(anoEjecucion));
				bean.setMesEjecucionHidden(Long.parseLong(mesEjecucion));
				bean.setEtapaEjecucionHidden(Long.parseLong(etapaEjecucion));

				logger.info("LECTURA DETALLE");
				FiseFormato12CC formato = new FiseFormato12CC();
				
				FiseFormato12CCPK pk = new FiseFormato12CCPK();
				pk.setCodEmpresa(codEmpresa);
				pk.setAnoPresentacion(Long.parseLong(anioPres));
				pk.setMesPresentacion(Long.parseLong(mesPres));
				pk.setEtapa(etapa);
				
				formato = formatoService.obtenerFormato12CCByPK(pk);
				
				if( formato != null ){
					
					List<FiseFormato12CD> listaDetalle = formatoService.listarFormato12CDByFormato12CC(formato);
					
					for (FiseFormato12CD detalle : listaDetalle) {
						
						if( bean.getCodigoEmpresa().equals(detalle.getId().getCodEmpresa()) &&
								bean.getAnioPresentacion() == detalle.getId().getAnoPresentacion() &&
								bean.getMesPresentacion() == detalle.getId().getMesPresentacion() &&
								bean.getEtapa().equals(detalle.getId().getEtapa()) &&
								bean.getAnioEjecucion() == detalle.getId().getAnoEjecucionGasto() &&
								bean.getMesEjecucion() == detalle.getId().getMesEjecucionGasto() &&
								bean.getEtapaEjecucion() == detalle.getId().getEtapaEjecucion() &&
								bean.getNroItemEtapa() == detalle.getId().getNumeroItemEtapa()
								){
							bean.setCodUbigeoOrigen(detalle.getCodUbigeoOrigen());
							bean.setLocalidadOrigen(detalle.getDescripcionLocalidadOrigen());
							bean.setCodUbigeoDestino(detalle.getCodUbigeoDestino());
							bean.setLocalidadDestino(detalle.getDescripcionLocalidadDestino());
							bean.setZonaBenef(detalle.getIdZonaBenef());
							bean.setCodCuentaContable(detalle.getCodigoCuentaContaEde());
							bean.setActividad(detalle.getDescripcionActividad());
							bean.setTipoDocumento(detalle.getIdTipDocRef());
							bean.setRucEmpresa(detalle.getRucEmpresaEmiteDocRef());
							bean.setSerieDocumento(detalle.getSerieDocumentoReferencia());
							bean.setNroDocumento(detalle.getNumeroDocumentoReferencia());
							bean.setNroDias(detalle.getNumeroDias());
							bean.setMontoAlimentacion(detalle.getMontoAlimentacion());
							bean.setMontoAlojamiento(detalle.getMontoAlojamiento());
							bean.setMontoMovilidad(detalle.getMontoMovilidad());
							bean.setTotalGeneral(detalle.getTotalGeneral());
							
							//cargamos el departamento provincia y distrito
							List<AdmUbigeo> listaDepartamentos = fiseUtil.listaDepartamentos();
							
							if( null!=bean.getCodUbigeoOrigen() ){
								String codDepartamentoOrigen = bean.getCodUbigeoOrigen().substring(0, 2);
								String codProvinciaOrigen = bean.getCodUbigeoOrigen().substring(0, 4);
								String codDistritoOrigen = bean.getCodUbigeoOrigen().substring(0, 6);
								List<AdmUbigeo> provinciasOrigen = fiseUtil.listaProvincias(codDepartamentoOrigen);
								List<AdmUbigeo> distritosOrigen = fiseUtil.listaDistritos(codProvinciaOrigen);
								
								String descDepartamentoOrigen = "";
								String descProvinciaOrigen = "";
								String descDistritoOrigen = "";
								
								for (AdmUbigeo depto : listaDepartamentos) {
									if( codDepartamentoOrigen.concat("0000").equals(depto.getCodUbigeo().trim()) ){
										descDepartamentoOrigen = depto.getNomUbigeo();
										break;
									}
								}
								for (AdmUbigeo prov : provinciasOrigen) {
									if( codProvinciaOrigen.concat("00").equals(prov.getCodUbigeo().trim()) ){
										descProvinciaOrigen = prov.getNomUbigeo();
										break;
									}
								}
								for (AdmUbigeo dist : distritosOrigen) {
									if( codDistritoOrigen.equals(dist.getCodUbigeo().trim()) ){
										descDistritoOrigen = dist.getNomUbigeo();
										break;
									}
								}
								
								//seteamos los valores
								//ORIGEN
								bean.setCodDepartamentoOrigenHidden(codDepartamentoOrigen.concat("0000"));
								bean.setCodProvinciaOrigenHidden(codProvinciaOrigen.concat("00"));
								bean.setCodDistritoOrigenHidden(codDistritoOrigen);
								bean.setDescDepartamentoOrigen(descDepartamentoOrigen);
								bean.setDescProvinciaOrigen(descProvinciaOrigen);
								bean.setDescDistritoOrigen(descDistritoOrigen);
							}
							if( null!=bean.getCodUbigeoDestino() ){
								String codDepartamentoDestino = bean.getCodUbigeoDestino().substring(0, 2);
								String codProvinciaDestino = bean.getCodUbigeoDestino().substring(0, 4);
								String codDistritoDestino = bean.getCodUbigeoDestino().substring(0, 6);
								List<AdmUbigeo> provinciasDestino = fiseUtil.listaProvincias(codDepartamentoDestino);
								List<AdmUbigeo> distritosDestino = fiseUtil.listaDistritos(codProvinciaDestino);
								
								String descDepartamentoDestino = "";
								String descProvinciaDestino = "";
								String descDistritoDestino = "";
								//
								for (AdmUbigeo depto : listaDepartamentos) {
									if( codDepartamentoDestino.concat("0000").equals(depto.getCodUbigeo().trim()) ){
										descDepartamentoDestino = depto.getNomUbigeo();
										break;
									}
								}
								for (AdmUbigeo prov : provinciasDestino) {
									if( codProvinciaDestino.concat("00").equals(prov.getCodUbigeo().trim()) ){
										descProvinciaDestino = prov.getNomUbigeo();
										break;
									}
								}
								for (AdmUbigeo dist : distritosDestino) {
									if( codDistritoDestino.equals(dist.getCodUbigeo().trim()) ){
										descDistritoDestino = dist.getNomUbigeo();
										break;
									}
								}
								//DESTINO
								bean.setCodDepartamentoDestinoHidden(codDepartamentoDestino.concat("0000"));
								bean.setCodProvinciaDestinoHidden(codProvinciaDestino.concat("00"));
								bean.setCodDistritoDestinoHidden(codDistritoDestino);
								bean.setDescDepartamentoDestino(descDepartamentoDestino);
								bean.setDescProvinciaDestino(descProvinciaDestino);
								bean.setDescDistritoDestino(descDistritoDestino);
							}
							
							break;
						}
						
					}
					
				}
				
			} else if (CRUD_CREATE.equals(crud)) {
				model.addAttribute("readonlyEdit", "false");

				bean.setAnioEjecucion(Long.parseLong(anioPres));
				bean.setMesEjecucion(Long.parseLong(mesPres));
				
				/*bean.setSt1(FiseConstants.CERO);
				bean.setSt2(FiseConstants.CERO);
				bean.setSt3(FiseConstants.CERO);
				bean.setSt4(FiseConstants.CERO);
				bean.setSt5(FiseConstants.CERO);
				bean.setSt6(FiseConstants.CERO);
				bean.setStser(FiseConstants.CERO);
				bean.setStesp(FiseConstants.CERO);
				bean.setTotal(FiseConstants.CERO);*/
			}
			//----
		}
		


		model.addAttribute("crud", crud);
		model.addAttribute("msg", msg);

		return "formato12CCRUDDetalle";
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
	
	@ActionMapping(params = "action=guardarDetalle")
	public void guardarDetalleFormato(ModelMap model, ActionRequest request, ActionResponse response, @RequestParam("crud") String crud, 
			//--@RequestParam("idZonaBenef") String idZona, 
			@RequestParam("codEmpresa") String codEmpresa, 
			//--@RequestParam("periodoEnvio") String periodoEnvio, 
			//--@RequestParam("anoEjecucionHidden") String anoEjecucionHidden, @RequestParam("mesEjecucionHidden") String mesEjecucionHidden, 
			//--@RequestParam("etapaEjecucionHidden") String etapaEjecucionHidden,
			@ModelAttribute("formato12CCBean") Formato12CCBean bean) {

		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

		String msg = "";
		
		FiseFormato12CC f = null;
		
		//String codEmpresa = bean.getCodigoEmpresa();
		Long anoPresentacion = bean.getAnioPresentacion();
		Long mesPresentacion = bean.getMesPresentacion();
		String etapa = bean.getEtapa();
		String periodoEnvio = bean.getPeriodoEnvio();
		//Long anoEjecucion = bean.getAnioEjecucion();
		//Long mesEjecucion = bean.getMesEjecucion();

		//
		bean.setCodigoEmpresa(codEmpresa);
		
		if( bean.getAnioEjecucion()==null || bean.getAnioEjecucion().equals(new Long(0)) ){
			bean.setAnioEjecucion(bean.getAnoEjecucionHidden());
		}
		if( bean.getMesEjecucion()==null || bean.getMesEjecucion().equals(new Long(0)) ){
			bean.setMesEjecucion(bean.getMesEjecucionHidden());
		}
		if( bean.getEtapaEjecucion()==null || bean.getEtapaEjecucion().equals(new Long(0)) ){
			bean.setEtapaEjecucion(bean.getEtapaEjecucionHidden());
		}
		
		try{
			
			bean.setUsuario(themeDisplay.getUser().getLogin());
			bean.setTerminal(themeDisplay.getUser().getLoginIP());

			FiseFormato12CCPK pk = new FiseFormato12CCPK();
			pk.setCodEmpresa(codEmpresa);
			/*pk.setAnoPresentacion(Long.parseLong(anioPresentacion));
			pk.setMesPresentacion(Long.parseLong(mesPresentacion));
			pk.setEtapa(etapa);*/
			pk.setAnoPresentacion(anoPresentacion);
			pk.setMesPresentacion(mesPresentacion);
			pk.setEtapa(etapa);

			// Primero buscamos si existe una cabecera
			FiseFormato12CC formato = formatoService.obtenerFormato12CCByPK(pk);
			
			//seteamos el codigo de ubigeo para oirgen y destino
			bean.setCodUbigeoOrigen(bean.getCodDistritoOrigen());
			bean.setCodUbigeoDestino(bean.getCodDistritoDestino());
			
			if (formato != null) {
				//para modificar registros
				if( CRUD_CREATE.equals(crud) ){
					f = formatoService.modificarFormato12CCregistrarFormato12CD(bean, formato);
				}else if( CRUD_UPDATE.equals(crud) ){
					f = formatoService.modificarFormato12CCmodificarFormato12CD(bean, formato);
				}
				
			} else {
				//para nuevos registros
				f = formatoService.registrarFormato12CCregistrarFormato12CD(bean);
			}
			
			msg = "OK";
			
		} catch (Exception e) {
			//e.printStackTrace();
			msg = "ERROR";
		}
		
		if(periodoEnvio==null){
			periodoEnvio = ""+anoPresentacion+FormatoUtil.rellenaIzquierda(""+mesPresentacion, '0', 2)+etapa;
		}
		
		if( "OK".equals(msg) ){
			response.setRenderParameter("crud", CRUD_UPDATE);
		}else if( "ERROR".equals(msg) ){
			if( CRUD_CREATE.equals(crud) ){
				response.setRenderParameter("crud", CRUD_CREATE);
			}else if ( CRUD_UPDATE.equals(crud) ) {
				response.setRenderParameter("crud", CRUD_UPDATE);
			}
		}

		//response.setRenderParameter("crud", CRUD_READ_CREATEUPDATE);
		response.setRenderParameter("action", "detalle");
		
		response.setRenderParameter("msg", msg);
		
		response.setRenderParameter("codigoEmpresaDetalle", codEmpresa);
		response.setRenderParameter("periodoEnvioDetalle", periodoEnvio);
		
		response.setRenderParameter("anoPresentacionDetalle", anoPresentacion.toString());
		response.setRenderParameter("mesPresentacionDetalle", mesPresentacion.toString());
		response.setRenderParameter("etapaDetalle", etapa);
		response.setRenderParameter("anoEjecucionDetalle", f.getAnoEjecucionDetalle().toString());
		response.setRenderParameter("mesEjecucionDetalle", f.getMesEjecucionDetalle().toString());
		response.setRenderParameter("etapaEjecucionDetalle", f.getEtapaEjecucionDetalle().toString());
		response.setRenderParameter("itemDetalle", f.getNumeroItemEtapaDetalle().toString());
		
		
		//--response.setRenderParameter("periodoEnvioDetalle", periodoEnvio);
		//---response.setRenderParameter("nroItemEtapa", f.getNumeroItemEtapaDetalle().toString());

	}
	
	@ResourceMapping("deleteDetalle")
	public void deleteDetalle(ResourceRequest request, ResourceResponse response, @ModelAttribute("formato12CCBean") Formato12CCBean bean) {
		try {
			JSONObject jsonObj = new JSONObject();
			FiseFormato12CC formato = new FiseFormato12CC();

			String codEmpresa = bean.getCodigoEmpresaHidden();
			Long anoPresentacion = bean.getAnioPresentacion();
			Long mesPresentacion = bean.getMesPresentacion();
			String etapa = bean.getEtapa();

			String anoEjecucion = request.getParameter("anoEjecucion").trim();
			String mesEjecucion = request.getParameter("mesEjecucion").trim();
			String etapaEjecucion = request.getParameter("etapaEjecucion").trim();
			String item = request.getParameter("item").trim();

			FiseFormato12CCPK pk = new FiseFormato12CCPK();
			pk.setCodEmpresa(codEmpresa);
			pk.setAnoPresentacion(anoPresentacion);
			pk.setMesPresentacion(mesPresentacion);
			pk.setEtapa(etapa);

			formato = formatoService.obtenerFormato12CCByPK(pk);
			try {
				if (formato != null) {

					for (FiseFormato12CD detalle : formato.getFiseFormato12CDs()) {
						if( Long.parseLong(anoEjecucion) == detalle.getId().getAnoEjecucionGasto() && 
								Long.parseLong(mesEjecucion) == detalle.getId().getMesEjecucionGasto() &&
								Long.parseLong(etapaEjecucion) == detalle.getId().getEtapaEjecucion() &&
								Long.parseLong(item) == detalle.getId().getNumeroItemEtapa()
								){
							formatoService.eliminarFormato12CD(detalle);
						}
					}
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
	public void deleteCabecera(ResourceRequest request, ResourceResponse response, @ModelAttribute("formato12CCBean") Formato12CCBean bean) {
		try {
			JSONObject jsonObj = new JSONObject();
			FiseFormato12CC formato = new FiseFormato12CC();

			String codEmpresa = request.getParameter("codigoEmpresa").trim();
			String anoPresentacion = request.getParameter("anoPresentacion").trim();
			String mesPresentacion = request.getParameter("mesPresentacion").trim();
			String etapa = request.getParameter("codEtapa").trim();

			FiseFormato12CCPK pk = new FiseFormato12CCPK();
			pk.setCodEmpresa(codEmpresa);
			pk.setAnoPresentacion(new Long(anoPresentacion));
			pk.setMesPresentacion(new Long(mesPresentacion));
			pk.setEtapa(etapa);

			formato = formatoService.obtenerFormato12CCByPK(pk);
			try {
				if (formato != null) {
					formatoService.eliminarFormato12CC(formato);
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
	
	/** reportes */
	@ResourceMapping("reporte")
	public void reporte(ResourceRequest request, ResourceResponse response, @ModelAttribute("formato12CCBean") Formato12CCBean bean) {
		try {
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
			HttpSession session = httpRequest.getSession();

			JSONArray jsonArray = new JSONArray();
			FiseFormato12CC formato = new FiseFormato12CC();

			Formato12CCBean reportBean = new Formato12CCBean();

			String codEmpresa = bean.getCodigoEmpresaHidden();
			Long anoPresentacion = bean.getAnioPresentacion();
			Long mesPresentacion = bean.getMesPresentacion();
			String etapa = bean.getEtapa();

			String nombreReporte = request.getParameter("nombreReporte").trim();
			String nombreArchivo = request.getParameter("nombreArchivo").trim();
			String tipoFormato = FiseConstants.TIPO_FORMATO_12C;
			String tipoArchivo = request.getParameter("tipoArchivo").trim();

			session.setAttribute("nombreReporte", nombreReporte);
			session.setAttribute("nombreArchivo", nombreArchivo);
			session.setAttribute("tipoFormato", tipoFormato);
			session.setAttribute("tipoArchivo", tipoArchivo);

			FiseFormato12CCPK pk = new FiseFormato12CCPK();
			pk.setCodEmpresa(codEmpresa);
			pk.setAnoPresentacion(anoPresentacion);
			pk.setMesPresentacion(mesPresentacion);
			pk.setEtapa(etapa);

			formato = formatoService.obtenerFormato12CCByPK(pk);
			if (formato != null) {
				// setamos los valores en el bean
				reportBean = formatoService.estructurarFormato12CBeanByFiseFormato12CC(formato);
				reportBean.setDescEmpresa(fiseUtil.getMapaEmpresa().get(formato.getId().getCodEmpresa()));
				reportBean.setDescMesPresentacion(fiseUtil.getMapaMeses().get(formato.getId().getMesPresentacion()));
				//
				// cargamos la lista a enviar
				session.setAttribute("lista", formato.getFiseFormato12CDs());
				
				session.setAttribute("mapa", formatoService.mapearParametrosFormato12C(reportBean) );
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
	public void validacion(ModelMap model, ResourceRequest request, ResourceResponse response, @ModelAttribute("formato12CCBean") Formato12CCBean bean) {
		HttpServletRequest req = PortalUtil.getHttpServletRequest(request);
		HttpSession session = req.getSession();

		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		try {
			FiseFormato12CC formato = new FiseFormato12CC();

			JSONArray jsonArray = new JSONArray();

			String codEmpresa = bean.getCodigoEmpresaHidden();
			Long anoPresentacion = bean.getAnioPresentacion();
			Long mesPresentacion = bean.getMesPresentacion();
			String etapa = bean.getEtapa();

			FiseFormato12CCPK pk = new FiseFormato12CCPK();
			pk.setCodEmpresa(codEmpresa);
			pk.setAnoPresentacion(anoPresentacion);
			pk.setMesPresentacion(mesPresentacion);
			pk.setEtapa(etapa);

			formato = formatoService.obtenerFormato12CCByPK(pk);
			if (formato != null) {
				// int cont=0;
				Formato12C12D13Generic formato12Generic = new Formato12C12D13Generic(formato);
				int i = commonService.validarFormatos_12C12D13A(formato12Generic, FiseConstants.NOMBRE_FORMATO_12C, themeDisplay.getUser().getLogin(), themeDisplay.getUser().getLoginIP());
				if (i == 0) {
					cargarListaObservaciones(formato.getFiseFormato12CDs());
					for (MensajeErrorBean error : listaObservaciones) {
						JSONObject jsonObj = new JSONObject();
						jsonObj.put("id", error.getId());
						jsonObj.put("nroItemEtapa", error.getNroItemEtapa());
						jsonObj.put("codigo", error.getCodigo());
						jsonObj.put("descripcion", error.getDescripcion());
						jsonObj.put("descEtapaEjecucion", error.getDescEtapaEjecucion());
						// agregar los valores
						jsonArray.put(jsonObj);
					}
					// **exportar excel
					fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_FORMATO_VAL_12C, FiseConstants.NOMBRE_EXCEL_VALIDACION_F12C, FiseConstants.NOMBRE_HOJA_VALIDACION, listaObservaciones);
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
	public void reporteValidacion(ResourceRequest request, ResourceResponse response, @ModelAttribute("formato12CCBean") Formato12CCBean bean) {
		try {
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
			HttpSession session = httpRequest.getSession();
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

			JSONArray jsonArray = new JSONArray();

			String nombreReporte = request.getParameter("nombreReporte").trim();
			String nombreArchivo = request.getParameter("nombreArchivo").trim();
			String tipoFormato = FiseConstants.TIPO_FORMATO_VAL_12C;
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
			String codEmpresa = bean.getCodigoEmpresaHidden();
			Long anoPresentacion = bean.getAnioPresentacion();
			Long mesPresentacion = bean.getMesPresentacion();
			String etapa = bean.getEtapa();
			CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12C);
			String descripcionFormato = "";
			if (tabla != null) {
				descripcionFormato = tabla.getDescripcionTabla();
			}
			Map<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("IMG", session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg"));
			mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
			mapa.put(FiseConstants.PARAM_ANO_PRESENTACION, anoPresentacion);
			mapa.put(FiseConstants.PARAM_DESC_MES_PRESENTACION, fiseUtil.getMapaMeses().get(mesPresentacion));
			mapa.put(FiseConstants.PARAM_USUARIO, themeDisplay.getUser().getLogin());
			mapa.put(FiseConstants.PARAM_NOMBRE_FORMATO, descripcionFormato);
			mapa.put(FiseConstants.PARAM_NRO_OBSERVACIONES, (listaObservaciones != null && !listaObservaciones.isEmpty()) ? listaObservaciones.size() : 0);
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
	public void envioDefinitivo(ResourceRequest request, ResourceResponse response, @ModelAttribute("formato12CCBean") Formato12CCBean bean) {
		try {

			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
			HttpSession session = httpRequest.getSession();
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			JSONArray jsonArray = new JSONArray();
			List<FileEntryJSP> listaArchivo = new ArrayList<FileEntryJSP>();

			FiseFormato12CC formato = new FiseFormato12CC();

			Formato12CCBean reportBean = new Formato12CCBean();
			Map<String, Object> mapa = null;
			String directorio = null;

			String codEmpresa = bean.getCodigoEmpresaHidden();
			Long anoPresentacion = bean.getAnioPresentacion();
			Long mesPresentacion = bean.getMesPresentacion();
			String etapa = bean.getEtapa();

			String nombreReporte = request.getParameter("nombreReporte").trim();
			String nombreArchivo = request.getParameter("nombreArchivo").trim();

			FiseFormato12CCPK pk = new FiseFormato12CCPK();
			pk.setCodEmpresa(codEmpresa);
			pk.setAnoPresentacion(new Long(anoPresentacion));
			pk.setMesPresentacion(new Long(mesPresentacion));
			pk.setEtapa(etapa);

			formato = formatoService.obtenerFormato12CCByPK(pk);
			if (formato != null) {
				reportBean = formatoService.estructurarFormato12CBeanByFiseFormato12CC(formato);
				reportBean.setDescEmpresa(fiseUtil.getMapaEmpresa().get(formato.getId().getCodEmpresa()));
				reportBean.setDescMesPresentacion(fiseUtil.getMapaMeses().get(formato.getId().getMesPresentacion()));
				mapa = formatoService.mapearParametrosFormato12C(reportBean);

				CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12C);
				String descripcionFormato = "";
				if (tabla != null) {
					descripcionFormato = tabla.getDescripcionTabla();
				}

				Formato12C12D13Generic formato12Generic = new Formato12C12D13Generic(formato);
				int i = commonService.validarFormatos_12C12D13A(formato12Generic, FiseConstants.NOMBRE_FORMATO_12C, themeDisplay.getUser().getLogin(), themeDisplay.getUser().getLoginIP());
				if (i == 0) {
					cargarListaObservaciones(formato.getFiseFormato12CDs());
				}

				// guardamos la fecha de envio, en este momento porque
				// necesitamos la fecha de envio para mandar al reporte
				formato = formatoService.modificarEnvioDefinitivoFormato12CC(bean, formato);

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
					mapa.put("FECHA_REGISTRO", formato.getFechaCreacion());
					mapa.put("USUARIO_REGISTRO", formato.getUsuarioCreacion());
					// prueba de envio definitivo
					String dirCheckedImage = session.getServletContext().getRealPath("/reports/checked.jpg");
					String dirUncheckedImage = session.getServletContext().getRealPath("/reports/unchecked.jpg");
					mapa.put("CHECKED", dirCheckedImage);
					mapa.put("UNCHECKED", dirUncheckedImage);
					boolean cumplePlazo = false;
					cumplePlazo = commonService.fechaEnvioCumplePlazo(FiseConstants.TIPO_FORMATO_12C, formato.getId().getCodEmpresa(), formato.getId().getAnoPresentacion(), formato.getId().getMesPresentacion(), formato.getId().getEtapa(), FechaUtil.fecha_DD_MM_YYYY(formato.getFechaEnvioDefinitivo()));
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

				/** REPORTE FORMATO 12C */
				nombreReporte = "formato12C";
				nombreArchivo = nombreReporte;
				directorio = "/reports/" + nombreReporte + ".jasper";
				File reportFile = new File(session.getServletContext().getRealPath(directorio));
				byte[] bytes = null;
				
				bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), mapa, new JRBeanCollectionDataSource(formato.getFiseFormato12CDs()));
				
				if (bytes != null) {
					// session.setAttribute("bytes1", bytes);
					//String nombre = nombreArchivo + FiseConstants.EXTENSIONARCHIVO_PDF;
					String nombre = FormatoUtil.nombreIndividualFormato(formato.getId().getCodEmpresa(), formato.getId().getAnoPresentacion(), formato.getId().getMesPresentacion(), FiseConstants.TIPO_FORMATO_12C);
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
					nombreReporte = "validacion12";
					nombreArchivo = nombreReporte;
					directorio = "/reports/" + nombreReporte + ".jasper";
					File reportFile2 = new File(session.getServletContext().getRealPath(directorio));
					byte[] bytes2 = null;
					bytes2 = JasperRunManager.runReportToPdf(reportFile2.getPath(), mapa, new JRBeanCollectionDataSource(listaObservaciones));
					if (bytes2 != null) {
						//String nombre = nombreArchivo + FiseConstants.EXTENSIONARCHIVO_PDF;
						String nombre = FormatoUtil.nombreIndividualAnexoObs(formato.getId().getCodEmpresa(), formato.getId().getAnoPresentacion(), formato.getId().getMesPresentacion(), FiseConstants.TIPO_FORMATO_12C);
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
				nombreReporte = "gastoMensualIndividualCD";
				nombreArchivo = nombreReporte;
				directorio = "/reports/" + nombreReporte + ".jasper";
				File reportFile3 = new File(session.getServletContext().getRealPath(directorio));
				byte[] bytes3 = null;
				bytes3 = JasperRunManager.runReportToPdf(reportFile3.getPath(), mapa, new JREmptyDataSource());
				if (bytes3 != null) {
					session.setAttribute("bytesActaEnvio", bytes3);
					//String nombre = nombreArchivo + FiseConstants.EXTENSIONARCHIVO_PDF;
					String nombre = FormatoUtil.nombreIndividualActaRemision(formato.getId().getCodEmpresa(), formato.getId().getAnoPresentacion(), formato.getId().getMesPresentacion(), FiseConstants.TIPO_FORMATO_12C);
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
			    			   FiseConstants.TIPO_FORMATO_12C,
			    			   descripcionFormato,
			    			   FiseConstants.FRECUENCIA_MENSUAL_DESCRIPCION);
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

	public void cargarListaObservaciones(List<FiseFormato12CD> listaDetalle) {
		int cont = 0;
		listaObservaciones = new ArrayList<MensajeErrorBean>();
		for (FiseFormato12CD detalle : listaDetalle) {
			List<FiseFormato12CDOb> listaObser = formatoService.listarFormato12CDObByFormato12CD(detalle);
			for (FiseFormato12CDOb observacion : listaObser) {
				cont++;
				MensajeErrorBean obs = new MensajeErrorBean();
				obs.setId(cont);
				obs.setNroItemEtapa(observacion.getId().getNumeroItemEtapa());
				obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
				obs.setDescripcion(mapaErrores.get(observacion.getFiseObservacion().getIdObservacion()));
				obs.setDescEtapaEjecucion(mapaEtapaEjecucion.get(observacion.getId().getEtapaEjecucion()));
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
	
	@ResourceMapping("reporteActaEnvioView")
	public void reporteActaEnvio(ResourceRequest request,ResourceResponse response, @ModelAttribute("formato12CCBean") Formato12CCBean bean) {
		try {
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
			HttpSession session = httpRequest.getSession();
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			JSONArray jsonArray = new JSONArray();	
			    
			FiseFormato12CC formato = new FiseFormato12CC();
			
			String tipoFormato = FiseConstants.TIPO_FORMATO_ACTAENVIO;
			String tipoArchivo = FiseConstants.FORMATO_EXPORT_PDF;
			
			CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12C);
			String descripcionFormato = "";
			if( tabla!=null ){
				descripcionFormato = tabla.getDescripcionTabla();
			}
			
			String codEmpresa = bean.getCodigoEmpresaHidden();
			Long anoPresentacion = bean.getAnioPresentacion();
			Long mesPresentacion = bean.getMesPresentacion();
			String etapa = bean.getEtapa();

			String nombreReporte = "gastoMensualIndividualCD";
		    String nombreArchivo = nombreReporte;
			
		    FiseFormato12CCPK pk = new FiseFormato12CCPK();
			pk.setCodEmpresa(codEmpresa);
			pk.setAnoPresentacion(new Long(anoPresentacion));
			pk.setMesPresentacion(new Long(mesPresentacion));
			pk.setEtapa(etapa);
			
			formato = formatoService.obtenerFormato12CCByPK(pk);
			if( formato!=null ){
				Map<String, Object> mapa = new HashMap<String, Object>();
				mapa.put(FiseConstants.PARAM_IMG_LOGOTIPO, session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg"));
				mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
				mapa.put(FiseConstants.PARAM_USUARIO, themeDisplay.getUser().getLogin());
				mapa.put(FiseConstants.PARAM_NOMBRE_FORMATO, descripcionFormato);
				mapa.put(FiseConstants.PARAM_FECHA_ENVIO, formato.getFechaEnvioDefinitivo());
				mapa.put(FiseConstants.PARAM_NRO_OBSERVACIONES, (listaObservaciones!=null && !listaObservaciones.isEmpty())?listaObservaciones.size():0);
				mapa.put(FiseConstants.PARAM_MSG_OBSERVACIONES, (listaObservaciones!=null && !listaObservaciones.isEmpty())?FiseConstants.MSG_OBSERVACION_REPORTE_LLENO:FiseConstants.MSG_OBSERVACION_REPORTE_VACIO);
				mapa.put(FiseConstants.PARAM_FECHA_REGISTRO, formato.getFechaCreacion());
				mapa.put(FiseConstants.PARAM_USUARIO_REGISTRO, formato.getUsuarioCreacion());
				String dirCheckedImage = session.getServletContext().getRealPath("/reports/checked.jpg");
				String dirUncheckedImage = session.getServletContext().getRealPath("/reports/unchecked.jpg");
				mapa.put(FiseConstants.PARAM_IMG_CHECKED, dirCheckedImage);
				mapa.put(FiseConstants.PARAM_IMG_UNCHECKED, dirUncheckedImage);
				boolean cumplePlazo = false;
				cumplePlazo = commonService.fechaEnvioCumplePlazo(
						FiseConstants.TIPO_FORMATO_12C, 
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
	
	/***CARGA EXCEL Y TXT***/
	@ActionMapping(params="action=uploadFile")
	public void cargarDocumento(ActionRequest request,ActionResponse response,@ModelAttribute("formato12CCBean") Formato12CCBean bean){
		
		logger.info("--- cargar documento");
		
		PortletRequest pRequest = (PortletRequest) request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
		
		Formato12CMensajeBean formatoMensaje = new Formato12CMensajeBean();
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(request);
		
		String flagCarga = uploadPortletRequest.getParameter("flagCarga");
		String tipoOperacion = uploadPortletRequest.getParameter("tipoOperacion");
    	String codEmpresaNew = uploadPortletRequest.getParameter("codigoEmpresa");
    	String periodoEnvioPresNew = uploadPortletRequest.getParameter("periodoEnvio");
    	//String flagPeriodoEjecucion = uploadPortletRequest.getParameter("flagPeriodoEjecucion");
    	
    	String codEmpresaEdit = uploadPortletRequest.getParameter("codigoEmpresaHidden");
    	String anioPresEdit = uploadPortletRequest.getParameter("anioPresentacion");
    	String mesPresEdit = uploadPortletRequest.getParameter("mesPresentacion");
    	String etapaEdit = uploadPortletRequest.getParameter("etapa");
    	
    	String anioPresNew = "";
		String mesPresNew = "";
		String etapaNew = "";
		
		if(periodoEnvioPresNew!=null && periodoEnvioPresNew.length()>6){
    		anioPresNew = periodoEnvioPresNew.substring(0, 4);
    		mesPresNew = periodoEnvioPresNew.substring(4, 6);
    		etapaNew = periodoEnvioPresNew.substring(6, periodoEnvioPresNew.length());
		}
		
		try{
		
			FileEntry fileEntry=null;
			if( flagCarga.equals(FiseConstants.FLAG_CARGAEXCEL_FORMULARIONUEVO) ){
				fileEntry=fiseUtil.subirDocumento(request, uploadPortletRequest, FiseConstants.TIPOARCHIVO_XLS);
				formatoMensaje = readExcelFile(fileEntry, themeDisplay.getUser(), tipoOperacion, codEmpresaNew, anioPresNew, mesPresNew);
			}else if( flagCarga.equals(FiseConstants.FLAG_CARGAEXCEL_FORMULARIOMODIFICACION) ){
				fileEntry=fiseUtil.subirDocumento(request, uploadPortletRequest, FiseConstants.TIPOARCHIVO_XLS);
				formatoMensaje = readExcelFile(fileEntry, themeDisplay.getUser(), tipoOperacion, codEmpresaEdit, anioPresEdit, mesPresEdit);
			}else if( flagCarga.equals(FiseConstants.FLAG_CARGATXT_FORMULARIONUEVO) ){
				fileEntry =fiseUtil.subirDocumento(request, uploadPortletRequest, FiseConstants.TIPOARCHIVO_TXT);
				//!!!!formatoMensaje =	readTxtFile(fileEntry, uploadPortletRequest, themeDisplay.getUser(), flagCarga, codEmpresaNew, anioPresNew, mesPresNew);
			}else if( flagCarga.equals(FiseConstants.FLAG_CARGATXT_FORMULARIOMODIFICACION) ){
				fileEntry=fiseUtil.subirDocumento(request, uploadPortletRequest, FiseConstants.TIPOARCHIVO_TXT);
				//!!!!formatoMensaje =	readTxtFile(fileEntry, uploadPortletRequest, themeDisplay.getUser(), flagCarga, codEmpresaEdit, anioPresEdit, mesPresEdit);
			}
		
		}catch(FileMimeTypeException ex){
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		if( formatoMensaje.getFiseFormato12CC()!=null ){
			
			response.setRenderParameter("action", "viewedit");
			response.setRenderParameter("msgTrans", "OK");
			
			response.setRenderParameter("tipo", FiseConstants.UPDATE+"");
			response.setRenderParameter("codEmpresa", formatoMensaje.getFiseFormato12CC().getId().getCodEmpresa());
			response.setRenderParameter("anioPresentacion", ""+formatoMensaje.getFiseFormato12CC().getId().getAnoPresentacion());
			response.setRenderParameter("mesPresentacion", ""+formatoMensaje.getFiseFormato12CC().getId().getMesPresentacion());
			response.setRenderParameter("etapa", formatoMensaje.getFiseFormato12CC().getId().getEtapa());
		}else{
			
			response.setRenderParameter("msgTrans", "ERROR");
			
			if(CRUD_CREATE.equals(tipoOperacion)){
				
				response.setRenderParameter("action", "nuevo");
				//response.setRenderParameter("tipo", FiseConstants.+"");
				response.setRenderParameter("codEmpresa", codEmpresaNew);
				response.setRenderParameter("anioPresentacion", anioPresNew);
				response.setRenderParameter("mesPresentacion", mesPresNew);
				response.setRenderParameter("etapa", etapaNew);
			}else if(CRUD_UPDATE.equals(tipoOperacion)){
				
				response.setRenderParameter("action", "viewedit");
				response.setRenderParameter("tipo", FiseConstants.UPDATE+"");
				response.setRenderParameter("codEmpresa", codEmpresaEdit);
				response.setRenderParameter("anioPresentacion", anioPresEdit);
				response.setRenderParameter("mesPresentacion", mesPresEdit);
				response.setRenderParameter("etapa", etapaEdit);
			}
			
			if(formatoMensaje.getListaMensajeError()!=null && formatoMensaje.getListaMensajeError().size()>0){
				pRequest.getPortletSession().setAttribute("listaError", formatoMensaje.getListaMensajeError(), PortletSession.APPLICATION_SCOPE);
			}
			
		}
		
		

		
		/*if( formatoMensaje.getFiseFormato12CC()!=null ){
			String codEmpresa = formatoMensaje.getFiseFormato14AC().getId().getCodEmpresa();
			String anoPresentacion = String.valueOf(formatoMensaje.getFiseFormato14AC().getId().getAnoPresentacion());
			String mesPresentacion = String.valueOf(formatoMensaje.getFiseFormato14AC().getId().getMesPresentacion());
			String anoIniVigencia = String.valueOf(formatoMensaje.getFiseFormato14AC().getId().getAnoInicioVigencia());
			String anoFinVigencia = String.valueOf(formatoMensaje.getFiseFormato14AC().getId().getAnoFinVigencia());
			String etapa = String.valueOf(formatoMensaje.getFiseFormato14AC().getId().getEtapa());
			
			pRequest.getPortletSession().setAttribute("codEmpresa", codEmpresa, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("anoPresentacion", anoPresentacion, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("mesPresentacion", mesPresentacion, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("anoInicioVigencia", anoIniVigencia, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("anoFinVigencia", anoFinVigencia, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("etapa", etapa, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("flag", "", PortletSession.APPLICATION_SCOPE);//para control de mostrar formulario a ingresar
		}else{
			if( flagCarga.equals(FiseConstants.FLAG_CARGAEXCEL_FORMULARIONUEVO) || flagCarga.equals(FiseConstants.FLAG_CARGATXT_FORMULARIONUEVO) ){
				pRequest.getPortletSession().setAttribute("codEmpresa", codEmpresaNew, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("anoPresentacion", anioPresNew, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("mesPresentacion", mesPresNew, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("anoInicioVigencia", anioIniVigNew, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("anoFinVigencia", anioFinVigNew, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("etapa", etapaNew, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("flag", "N", PortletSession.APPLICATION_SCOPE);//para control de mostrar formulario a ingresar
			}else if( flagCarga.equals(FiseConstants.FLAG_CARGAEXCEL_FORMULARIOMODIFICACION) || flagCarga.equals(FiseConstants.FLAG_CARGATXT_FORMULARIOMODIFICACION) ){
				pRequest.getPortletSession().setAttribute("codEmpresa", codEmpresaEdit, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("anoPresentacion", anioPresEdit, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("mesPresentacion", mesPresEdit, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("anoInicioVigencia", anioIniVigEdit, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("anoFinVigencia", anioFinVigEdit, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("etapa", etapaEdit, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("flag", "", PortletSession.APPLICATION_SCOPE);//para control de mostrar formulario a ingresar
			}
			
		}
		if(formatoMensaje.getListaMensajeError()!=null && formatoMensaje.getListaMensajeError().size()>0){
			pRequest.getPortletSession().setAttribute("listaError", formatoMensaje.getListaMensajeError(), PortletSession.APPLICATION_SCOPE);
			pRequest.getPortletSession().setAttribute("mensajeError", formatoMensaje.getMensajeError(), PortletSession.APPLICATION_SCOPE);
		}else{
			pRequest.getPortletSession().setAttribute("mensajeInformacion", "Datos guardados satisfactoriamente.", PortletSession.APPLICATION_SCOPE);
		}*/
		//limpiamos las variables en sesion utilizados para la edicion de archivos de carga
		/*pRequest.getPortletSession().setAttribute("codEmpresaEdit", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("anoPresentacionEdit", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("mesPresentacionEdit", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("anoEjecucionEdit", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("mesEjecucionEdit", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("etapaEdit", "", PortletSession.APPLICATION_SCOPE);*/
		
		
		
		/*response.setRenderParameter("codEmpresa", FiseConstants.UPDATE+"");
		response.setRenderParameter("anioPresentacion", cabecerapk.getAnoPresentacion() + "");
		response.setRenderParameter("mesPresentacion", cabecerapk.getMesPresentacion() + "");
		response.setRenderParameter("etapa", cabecerapk.getEtapa() + "");
		response.setRenderParameter("periodoDeclaracion", command.getPeridoDeclaracion());
		response.setRenderParameter("descripcionPeriodo", command.getDescripcionPeriodo());
		response.setRenderParameter("codEmpresaHidden", command.getCodEmpresaHidden());
		response.setRenderParameter("descripcionPeriodoHidden", command.getDescripcionPeriodoHidden());*/
		
	}
	
	public Formato12CMensajeBean readExcelFile(FileEntry archivo, User user, String tipoOperacion, String codEmpresa, 	String anioPres, String mesPres) {
		
		//---------------------
		//FLAG CARGA:
		//	1: para registros nuevos
		//	2: para registros modificados
		//---------------------
		Formato12CMensajeBean formatoMensaje = new Formato12CMensajeBean();

		InputStream is=null;
		FiseFormato12CC objeto = null;
		String sMsg = "";
		StringBuilder sMsgImplementacion = new StringBuilder(FiseConstants.BLANCO);
		StringBuilder sMsgOperativa = new StringBuilder(FiseConstants.BLANCO);
		
		List<MensajeErrorBean> listaError = new ArrayList<MensajeErrorBean>();
		int cont = 0;
		
		boolean process = false;
		
		try {
			if (archivo != null) {
				HSSFWorkbook libro = null;
				try {
					is=archivo.getContentStream();
					libro = new HSSFWorkbook(is);//Se lee libro xls
				} catch (Exception e1) {
					logger.warn(mapaErrores.get(FiseConstants.COD_ERROR_F12_20));
					cont++;
					sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_20);
					throw new Exception(mapaErrores.get(FiseConstants.COD_ERROR_F12_20));
				}
				int nroHojaSelec=0;
				
				if (libro != null) {
					//el excel puede tener varias hojas, se tiene qie leer el total de hojas y encontrar la que necesitemos
					logger.info("nro de hojas:"+ libro.getNumberOfSheets());
					for (int sheetNro = 0; sheetNro < libro.getNumberOfSheets(); sheetNro++){
						logger.info("nombre de hoja "+sheetNro+":"+ libro.getSheetName(sheetNro));
						if( FiseConstants.NOMBRE_HOJA_FORMATO12C.equals(libro.getSheetName(sheetNro)) ){
							process = true;
							nroHojaSelec = sheetNro;
							break;
						}
					}
					logger.info("nro de hoja seleccionada "+nroHojaSelec);
					
					if(process){
						
						HSSFSheet hojaF12 = libro.getSheetAt(nroHojaSelec);
						//int nroFilas = hojaF12.getLastRowNum()+1;
						int numRows=hojaF12.getPhysicalNumberOfRows();
						
						HSSFRow filaEmpresa = hojaF12.getRow(FiseConstants.NRO_FILA_CODEMPRESA_FORMATO12C);						
						HSSFRow filaAnioMes = hojaF12.getRow(FiseConstants.NRO_FILA_ANIOMES_FORMATO12C);	
						
						//--HSSFRow filaImplemenatacion;
						//--HSSFRow filaOperativa;
						
						Formato12CCBean formulario = new Formato12CCBean();
						
						HSSFCell celdaEmpresa = filaEmpresa.getCell(FiseConstants.NRO_CELDA_EMPRESA_FORMATO12C);
						HSSFCell celdaAnio = filaAnioMes.getCell(FiseConstants.NRO_CELDA_ANIO_FORMATO12C);
						HSSFCell celdaMes = filaAnioMes.getCell(FiseConstants.NRO_CELDA_MES_FORMATO12C);
						
						
						List<Formato12CCBean> listaDetalle = new ArrayList<Formato12CCBean>();
						//logica para mostrar la lista de detalle de los dos sectores
						//obtenemos el inicio de IMPLEMENTACION y OPERATIVA
						
						int inicioImplementacion = 0;
						int inicioOperativa = 0;
						int finRegistros = 0;
						
						for( int i=0; i<numRows; i++ ){
							HSSFRow row= hojaF12.getRow(i);
							if( row != null ){
								HSSFCell cell = row.getCell(0);//cogemos la primera columna del excel, para detectar las posicion inicial de cada sector
								if( HSSFCell.CELL_TYPE_STRING==cell.getCellType()  && HSSFCell.CELL_TYPE_BLANK != cell.getCellType() ){
									if( FiseConstants.DESC_FILA_INICIO_IMPLEMENTACION_FORMATO12C.equalsIgnoreCase(cell.toString()) ){
										inicioImplementacion = i;
									}else if( FiseConstants.DESC_FILA_INICIO_OPERATIVO_FORMATO12C.equalsIgnoreCase(cell.toString()) ){
										inicioOperativa = i;
									}else if( FiseConstants.DESC_FILA_FIN_REGISTROS_FORMATO12C.equalsIgnoreCase(cell.toString()) ){
										finRegistros = i;
									}
									if( inicioImplementacion!=0 && inicioOperativa!=0 && finRegistros!=0 ){
										break;
									}
								}
							}
						}
						
						//tipos
						if( HSSFCell.CELL_TYPE_STRING == celdaEmpresa.getCellType() ){
							formulario.setCodigoEmpresa(celdaEmpresa.toString());
						}else if( HSSFCell.CELL_TYPE_FORMULA == celdaEmpresa.getCellType()  ){
							formulario.setCodigoEmpresa(celdaEmpresa.getRichStringCellValue().toString());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaEmpresa.getCellType()  ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_30);
						}else{
							formulario.setCodigoEmpresa("");
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_40);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaAnio.getCellType()  ){
							formulario.setAnioPresentacion(new Double(celdaAnio.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaAnio.getCellType()  ){
							formulario.setAnioPresentacion(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_50);
						}else{
							formulario.setAnioPresentacion(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_60);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaMes.getCellType()  ){
							formulario.setMesPresentacion(new Double(celdaMes.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaMes.getCellType()  ){
							formulario.setMesPresentacion(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_70);
						}else{
							formulario.setMesPresentacion(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_80);
						}
						
						if( inicioImplementacion!=0 && inicioOperativa!=0 && finRegistros!=0 ){
							for (int i = (inicioImplementacion+1); i<(inicioOperativa-1); i++) {
								
								Formato12CCBean detalleBean = new Formato12CCBean();
								
								HSSFRow row= hojaF12.getRow(i);
								
								HSSFCell celdaAnoEjecucion = row.getCell(FiseConstants.NRO_CELDA_ANO_EJECUCION_FORMATO12C);
								HSSFCell celdaMesEjecucion = row.getCell(FiseConstants.NRO_CELDA_MES_EJECUCION_FORMATO12C);
								HSSFCell celdaItem = row.getCell(FiseConstants.NRO_CELDA_ITEM_FORMATO12C);
								HSSFCell celdaCodUbigeoOrigen = row.getCell(FiseConstants.NRO_CELDA_CODUBIGEO_ORIGEN_FORMATO12C);
								HSSFCell celdaLocalidadOrigen = row.getCell(FiseConstants.NRO_CELDA_LOCALIDAD_ORIGEN_FORMATO12C);
								HSSFCell celdaCodUbigeoDestino = row.getCell(FiseConstants.NRO_CELDA_CODUBIGEO_DESTINO_FORMATO12C);
								HSSFCell celdaLocalidadDestino = row.getCell(FiseConstants.NRO_CELDA_LOCALIDAD_DESTINO_FORMATO12C);
								HSSFCell celdaZonaBeneficiario = row.getCell(FiseConstants.NRO_CELDA_ZONA_BENEFICIARIO_FORMATO12C);
								HSSFCell celdaCuentaContable = row.getCell(FiseConstants.NRO_CELDA_CTACONTABLE_FORMATO12C);
								HSSFCell celdaActividad = row.getCell(FiseConstants.NRO_CELDA_ACTIVIDAD_FORMATO12C);
								HSSFCell celdaTipoDocumento = row.getCell(FiseConstants.NRO_CELDA_TIPODOCUMENTO_FORMATO12C);
								HSSFCell celdaRUCEmpresa = row.getCell(FiseConstants.NRO_CELDA_RUC_FORMATO12C);
								HSSFCell celdaSerieDocumento = row.getCell(FiseConstants.NRO_CELDA_SERIEDOCUMENTO_FORMATO12C);
								HSSFCell celdaNroDocumento = row.getCell(FiseConstants.NRO_CELDA_NRODOCUMENTO_FORMATO12C);
								HSSFCell celdaNroDias = row.getCell(FiseConstants.NRO_CELDA_NRODIAS_FORMATO12C);
								HSSFCell celdaAlimentacion = row.getCell(FiseConstants.NRO_CELDA_ALIMENTACION_FORMATO12C);
								HSSFCell celdaAlojamiento = row.getCell(FiseConstants.NRO_CELDA_ALOJAMIENTO_FORMATO12C);
								HSSFCell celdaMovilidad = row.getCell(FiseConstants.NRO_CELDA_MOVILIDAD_FORMATO12C);
								
								detalleBean.setCodigoEmpresa(formulario.getCodigoEmpresa());
								detalleBean.setAnioPresentacion(formulario.getAnioPresentacion());
								detalleBean.setMesPresentacion(formulario.getMesPresentacion());
								
								detalleBean.setEtapaEjecucion(FiseConstants.ETAPA_EJECUCION_IMPLEMENTACION_COD);
								
								detalleBean.setUsuario(user.getLogin());
								detalleBean.setTerminal(user.getLoginIP());
								detalleBean.setTipoArchivo(FiseConstants.TIPOARCHIVO_XLS);
								detalleBean.setNombreArchivo(archivo.getTitle());
								
								//ANO EJECUCION
								if( HSSFCell.CELL_TYPE_NUMERIC == celdaAnoEjecucion.getCellType()  ){
									detalleBean.setAnioEjecucion(new Double(celdaAnoEjecucion.getNumericCellValue()).longValue());
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaAnoEjecucion.getCellType()  ){
									detalleBean.setAnioEjecucion(0L);
									cont++;
									//---sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_50));
								}else{
									detalleBean.setAnioEjecucion(0L);
									cont++;
									sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_60,i+1));
								}
								//MES EJECUCION
								if( HSSFCell.CELL_TYPE_NUMERIC == celdaMesEjecucion.getCellType()  ){
									detalleBean.setMesEjecucion(new Double(celdaMesEjecucion.getNumericCellValue()).longValue());
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaMesEjecucion.getCellType()  ){
									detalleBean.setMesEjecucion(0L);
									cont++;
									//---sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_70));
								}else{
									detalleBean.setMesEjecucion(0L);
									cont++;
									sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_80,i+1));
								}
								//NRO ITEM ETAPA
								if( HSSFCell.CELL_TYPE_NUMERIC == celdaItem.getCellType()  ){
									detalleBean.setNroItemEtapa(new Double(celdaItem.getNumericCellValue()).longValue());
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaItem.getCellType()  ){
									detalleBean.setNroItemEtapa(0L);
									cont++;
									//---sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_70));
								}else{
									detalleBean.setNroItemEtapa(0L);
									cont++;
									sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_80,i+1));
								}
								//COD UBIGEO ORIGEN
								if( HSSFCell.CELL_TYPE_STRING == celdaCodUbigeoOrigen.getCellType() ){
									detalleBean.setCodUbigeoOrigen(celdaCodUbigeoOrigen.toString());
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaCodUbigeoOrigen.getCellType()  ){
									detalleBean.setCodUbigeoOrigen(FiseConstants.BLANCO);
									cont++;
									//---sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_30));
								}else{
									detalleBean.setCodUbigeoOrigen(FiseConstants.BLANCO);
									cont++;
									sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_40,i+1));
								}
								//LOCALIDAD ORIGEN
								if( HSSFCell.CELL_TYPE_STRING == celdaLocalidadOrigen.getCellType() ){
									detalleBean.setLocalidadOrigen(celdaLocalidadOrigen.toString());
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaLocalidadOrigen.getCellType()  ){
									detalleBean.setLocalidadOrigen(FiseConstants.BLANCO);
									cont++;
									//---sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_30));
								}else{
									detalleBean.setLocalidadOrigen(FiseConstants.BLANCO);
									cont++;
									sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_40,i+1));
								}
								//COD UBIGEO DESTINO
								if( HSSFCell.CELL_TYPE_STRING == celdaCodUbigeoDestino.getCellType() ){
									detalleBean.setCodUbigeoDestino(celdaCodUbigeoDestino.toString());
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaCodUbigeoDestino.getCellType()  ){
									detalleBean.setCodUbigeoDestino(FiseConstants.BLANCO);
									cont++;
									//---sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_30));
								}else{
									detalleBean.setCodUbigeoDestino(FiseConstants.BLANCO);
									cont++;
									sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_40,i+1));
								}
								//LOCALIDAD DESTINO
								if( HSSFCell.CELL_TYPE_STRING == celdaLocalidadDestino.getCellType() ){
									detalleBean.setLocalidadDestino(celdaLocalidadDestino.toString());
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaLocalidadDestino.getCellType()  ){
									detalleBean.setLocalidadDestino(FiseConstants.BLANCO);
									cont++;
									//---sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_30));
								}else{
									detalleBean.setLocalidadDestino(FiseConstants.BLANCO);
									cont++;
									sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_40,i+1));
								}
								//ZONA BENEFICIARIO
								if( HSSFCell.CELL_TYPE_NUMERIC == celdaZonaBeneficiario.getCellType()  ){
									detalleBean.setZonaBenef(new Double(celdaZonaBeneficiario.getNumericCellValue()).longValue());
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaZonaBeneficiario.getCellType()  ){
									detalleBean.setZonaBenef(0L);
									cont++;
									//---sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_70));
								}else{
									detalleBean.setZonaBenef(0L);
									cont++;
									sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_80,i+1));
								}
								//CUENTA CONTABLE
								if( HSSFCell.CELL_TYPE_STRING == celdaCuentaContable.getCellType() ){
									detalleBean.setCodCuentaContable(celdaCuentaContable.toString());
								}else if( HSSFCell.CELL_TYPE_NUMERIC == celdaCuentaContable.getCellType()  ){
									String valor = "" + celdaCuentaContable.getNumericCellValue();
									detalleBean.setCodCuentaContable(FormatoUtil.eliminaDecimales(valor));
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaCuentaContable.getCellType()  ){
									detalleBean.setCodCuentaContable(FiseConstants.BLANCO);
									cont++;
									//---sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_30));
								}else{
									detalleBean.setCodCuentaContable(FiseConstants.BLANCO);
									cont++;
									sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_40,i+1));
								}
								//ACTIVIDAD
								if( HSSFCell.CELL_TYPE_STRING == celdaActividad.getCellType() ){
									detalleBean.setActividad(celdaActividad.toString());
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaActividad.getCellType()  ){
									detalleBean.setActividad(FiseConstants.BLANCO);
									cont++;
									//---sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_30));
								}else{
									detalleBean.setActividad(FiseConstants.BLANCO);
									cont++;
									sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_40,i+1));
								}
								//TIPO DOCUMENTO
								if( HSSFCell.CELL_TYPE_STRING == celdaTipoDocumento.getCellType() ){
									detalleBean.setTipoDocumento(celdaTipoDocumento.toString());
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaTipoDocumento.getCellType()  ){
									detalleBean.setTipoDocumento(FiseConstants.BLANCO);
									cont++;
									//---sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_30));
								}else{
									detalleBean.setTipoDocumento(FiseConstants.BLANCO);
									cont++;
									sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_40,i+1));
								}
								//RUC
								if( HSSFCell.CELL_TYPE_STRING == celdaRUCEmpresa.getCellType() ){
									detalleBean.setRucEmpresa(celdaRUCEmpresa.toString());
								}else if( HSSFCell.CELL_TYPE_NUMERIC == celdaRUCEmpresa.getCellType()  ){
									double d = celdaRUCEmpresa.getNumericCellValue();
									String valor = FormatoUtil.conversion(d);
									//String valor = "" + celdaRUCEmpresa.getNumericCellValue();
									detalleBean.setRucEmpresa(FormatoUtil.eliminaDecimales(valor));
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaRUCEmpresa.getCellType()  ){
									detalleBean.setRucEmpresa(FiseConstants.BLANCO);
									cont++;
									//---sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_30));
								}else{
									detalleBean.setRucEmpresa(FiseConstants.BLANCO);
									cont++;
									sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_40,i+1));
								}
								//SERIE DOCUMENTO
								if( HSSFCell.CELL_TYPE_STRING == celdaSerieDocumento.getCellType() ){
									detalleBean.setSerieDocumento(celdaSerieDocumento.toString());
								}else if( HSSFCell.CELL_TYPE_NUMERIC == celdaSerieDocumento.getCellType()  ){
									String valor = "" + celdaSerieDocumento.getNumericCellValue();
									detalleBean.setSerieDocumento(FormatoUtil.eliminaDecimales(valor));
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaSerieDocumento.getCellType()  ){
									detalleBean.setSerieDocumento(FiseConstants.BLANCO);
									cont++;
									//---sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_30));
								}else{
									detalleBean.setSerieDocumento(FiseConstants.BLANCO);
									cont++;
									sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_40,i+1));
								}
								//NRO DOCUMENTO
								if( HSSFCell.CELL_TYPE_STRING == celdaNroDocumento.getCellType() ){
									detalleBean.setNroDocumento(celdaNroDocumento.toString());
								}else if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroDocumento.getCellType()  ){
									String valor = "" + celdaNroDocumento.getNumericCellValue();
									detalleBean.setNroDocumento(FormatoUtil.eliminaDecimales(valor));
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroDocumento.getCellType()  ){
									detalleBean.setNroDocumento(FiseConstants.BLANCO);
									cont++;
									//---sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_30));
								}else{
									detalleBean.setNroDocumento(FiseConstants.BLANCO);
									cont++;
									sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_40,i+1));
								}
								//NRO DIAS
								if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroDias.getCellType()  ){
									detalleBean.setNroDias(new Double(celdaNroDias.getNumericCellValue()).longValue());
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroDias.getCellType()  ){
									detalleBean.setNroDias(0L);
									cont++;
									//---sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_70));
								}else{
									detalleBean.setNroDias(0L);
									cont++;
									sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_80,i+1));
								}
								//ALIMENTACION
								if( HSSFCell.CELL_TYPE_NUMERIC == celdaAlimentacion.getCellType()  ){
									detalleBean.setMontoAlimentacion(new BigDecimal(celdaAlimentacion.getNumericCellValue()));
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaAlimentacion.getCellType()  ){
									detalleBean.setMontoAlimentacion(new BigDecimal(0.00));
								}else{
									detalleBean.setMontoAlimentacion(new BigDecimal(0.00));
									cont++;
									sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_150,i+1));
								}
								//ALOJAMIENTO
								if( HSSFCell.CELL_TYPE_NUMERIC == celdaAlojamiento.getCellType()  ){
									detalleBean.setMontoAlojamiento(new BigDecimal(celdaAlojamiento.getNumericCellValue()));
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaAlojamiento.getCellType()  ){
									detalleBean.setMontoAlojamiento(new BigDecimal(0.00));
								}else{
									detalleBean.setMontoAlojamiento(new BigDecimal(0.00));
									cont++;
									sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_150,i+1));
								}
								//MOVILIDAD
								if( HSSFCell.CELL_TYPE_NUMERIC == celdaMovilidad.getCellType()  ){
									detalleBean.setMontoMovilidad(new BigDecimal(celdaMovilidad.getNumericCellValue()));
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaMovilidad.getCellType()  ){
									detalleBean.setMontoMovilidad(new BigDecimal(0.00));
								}else{
									detalleBean.setMontoMovilidad(new BigDecimal(0.00));
									cont++;
									sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_150,i+1));
								}
								
								if( detalleBean.getAnioEjecucion() != 0 ||
										detalleBean.getMesEjecucion() != 0 ||
										detalleBean.getNroItemEtapa() != 0 ||
										!detalleBean.getCodUbigeoOrigen().equals(FiseConstants.BLANCO) ||
										!detalleBean.getLocalidadOrigen().equals(FiseConstants.BLANCO) ||
										!detalleBean.getCodUbigeoDestino().equals(FiseConstants.BLANCO) ||
										!detalleBean.getLocalidadDestino().equals(FiseConstants.BLANCO) ||
										detalleBean.getZonaBenef() != 0 ||
										!detalleBean.getCodCuentaContable().equals(FiseConstants.BLANCO) ||
										!detalleBean.getActividad().equals(FiseConstants.BLANCO) ||
										!detalleBean.getTipoDocumento().equals(FiseConstants.BLANCO) ||
										!detalleBean.getRucEmpresa().equals(FiseConstants.BLANCO) ||
										!detalleBean.getSerieDocumento().equals(FiseConstants.BLANCO) ||
										!detalleBean.getNroDocumento().equals(FiseConstants.BLANCO) ||
										detalleBean.getNroDias() != 0 ||
										!detalleBean.getMontoAlimentacion().equals(BigDecimal.ZERO) ||
										!detalleBean.getMontoAlojamiento().equals(BigDecimal.ZERO) ||
										!detalleBean.getMontoMovilidad().equals(BigDecimal.ZERO) 
										){
									
									/**validar los valores de los inputs correctos*/
									if( !mapaUbigeo.containsKey(detalleBean.getCodUbigeoOrigen()) ){
										cont++;
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_150,i+1));
									}
									if( !mapaUbigeo.containsKey(detalleBean.getCodUbigeoDestino()) ){
										cont++;
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_150,i+1));
									}
									if( !mapaZonaBenef.containsKey(detalleBean.getZonaBenef()) ){
										cont++;
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_150,i+1));
									}
									if( !mapaTipoDocumento.containsKey(detalleBean.getTipoDocumento()) ){
										cont++;
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_150,i+1));
									}
									
								}
								
								if( FiseConstants.BLANCO.equals(sMsgImplementacion.toString()) ){
									listaDetalle.add(detalleBean);
								}
								
							}
							for (int i = (inicioOperativa+1); i<(finRegistros-1); i++) {
								
								Formato12CCBean detalleBean = new Formato12CCBean();
								
								HSSFRow row= hojaF12.getRow(i);
								
								HSSFCell celdaAnoEjecucion = row.getCell(FiseConstants.NRO_CELDA_ANO_EJECUCION_FORMATO12C);
								HSSFCell celdaMesEjecucion = row.getCell(FiseConstants.NRO_CELDA_MES_EJECUCION_FORMATO12C);
								HSSFCell celdaItem = row.getCell(FiseConstants.NRO_CELDA_ITEM_FORMATO12C);
								HSSFCell celdaCodUbigeoOrigen = row.getCell(FiseConstants.NRO_CELDA_CODUBIGEO_ORIGEN_FORMATO12C);
								HSSFCell celdaLocalidadOrigen = row.getCell(FiseConstants.NRO_CELDA_LOCALIDAD_ORIGEN_FORMATO12C);
								HSSFCell celdaCodUbigeoDestino = row.getCell(FiseConstants.NRO_CELDA_CODUBIGEO_DESTINO_FORMATO12C);
								HSSFCell celdaLocalidadDestino = row.getCell(FiseConstants.NRO_CELDA_LOCALIDAD_DESTINO_FORMATO12C);
								HSSFCell celdaZonaBeneficiario = row.getCell(FiseConstants.NRO_CELDA_ZONA_BENEFICIARIO_FORMATO12C);
								HSSFCell celdaCuentaContable = row.getCell(FiseConstants.NRO_CELDA_CTACONTABLE_FORMATO12C);
								HSSFCell celdaActividad = row.getCell(FiseConstants.NRO_CELDA_ACTIVIDAD_FORMATO12C);
								HSSFCell celdaTipoDocumento = row.getCell(FiseConstants.NRO_CELDA_TIPODOCUMENTO_FORMATO12C);
								HSSFCell celdaRUCEmpresa = row.getCell(FiseConstants.NRO_CELDA_RUC_FORMATO12C);
								HSSFCell celdaSerieDocumento = row.getCell(FiseConstants.NRO_CELDA_SERIEDOCUMENTO_FORMATO12C);
								HSSFCell celdaNroDocumento = row.getCell(FiseConstants.NRO_CELDA_NRODOCUMENTO_FORMATO12C);
								HSSFCell celdaNroDias = row.getCell(FiseConstants.NRO_CELDA_NRODIAS_FORMATO12C);
								HSSFCell celdaAlimentacion = row.getCell(FiseConstants.NRO_CELDA_ALIMENTACION_FORMATO12C);
								HSSFCell celdaAlojamiento = row.getCell(FiseConstants.NRO_CELDA_ALOJAMIENTO_FORMATO12C);
								HSSFCell celdaMovilidad = row.getCell(FiseConstants.NRO_CELDA_MOVILIDAD_FORMATO12C);
								
								detalleBean.setCodigoEmpresa(formulario.getCodigoEmpresa());
								detalleBean.setAnioPresentacion(formulario.getAnioPresentacion());
								detalleBean.setMesPresentacion(formulario.getMesPresentacion());
								
								detalleBean.setEtapaEjecucion(FiseConstants.ETAPA_EJECUCION_OPERATIVA_COD);
								
								detalleBean.setUsuario(user.getLogin());
								detalleBean.setTerminal(user.getLoginIP());
								detalleBean.setTipoArchivo(FiseConstants.TIPOARCHIVO_XLS);
								detalleBean.setNombreArchivo(archivo.getTitle());
								
								//ANO EJECUCION
								if( HSSFCell.CELL_TYPE_NUMERIC == celdaAnoEjecucion.getCellType()  ){
									detalleBean.setAnioEjecucion(new Double(celdaAnoEjecucion.getNumericCellValue()).longValue());
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaAnoEjecucion.getCellType()  ){
									detalleBean.setAnioEjecucion(0L);
									cont++;
									//---sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_50));
								}else{
									detalleBean.setAnioEjecucion(0L);
									cont++;
									sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_60,i+1));
								}
								//MES EJECUCION
								if( HSSFCell.CELL_TYPE_NUMERIC == celdaMesEjecucion.getCellType()  ){
									detalleBean.setMesEjecucion(new Double(celdaMesEjecucion.getNumericCellValue()).longValue());
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaMesEjecucion.getCellType()  ){
									detalleBean.setMesEjecucion(0L);
									cont++;
									//---sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_70));
								}else{
									detalleBean.setMesEjecucion(0L);
									cont++;
									sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_80,i+1));
								}
								//NRO ITEM ETAPA
								if( HSSFCell.CELL_TYPE_NUMERIC == celdaItem.getCellType()  ){
									detalleBean.setNroItemEtapa(new Double(celdaItem.getNumericCellValue()).longValue());
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaItem.getCellType()  ){
									detalleBean.setNroItemEtapa(0L);
									cont++;
									//---sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_70));
								}else{
									detalleBean.setNroItemEtapa(0L);
									cont++;
									sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_80,i+1));
								}
								//COD UBIGEO ORIGEN
								if( HSSFCell.CELL_TYPE_STRING == celdaCodUbigeoOrigen.getCellType() ){
									detalleBean.setCodUbigeoOrigen(celdaCodUbigeoOrigen.toString());
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaCodUbigeoOrigen.getCellType()  ){
									detalleBean.setCodUbigeoOrigen(FiseConstants.BLANCO);
									cont++;
									//---sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_30));
								}else{
									detalleBean.setCodUbigeoOrigen(FiseConstants.BLANCO);
									cont++;
									sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_40,i+1));
								}
								//LOCALIDAD ORIGEN
								if( HSSFCell.CELL_TYPE_STRING == celdaLocalidadOrigen.getCellType() ){
									detalleBean.setLocalidadOrigen(celdaLocalidadOrigen.toString());
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaLocalidadOrigen.getCellType()  ){
									detalleBean.setLocalidadOrigen(FiseConstants.BLANCO);
									cont++;
									//---sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_30));
								}else{
									detalleBean.setLocalidadOrigen(FiseConstants.BLANCO);
									cont++;
									sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_40,i+1));
								}
								//COD UBIGEO DESTINO
								if( HSSFCell.CELL_TYPE_STRING == celdaCodUbigeoDestino.getCellType() ){
									detalleBean.setCodUbigeoDestino(celdaCodUbigeoDestino.toString());
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaCodUbigeoDestino.getCellType()  ){
									detalleBean.setCodUbigeoDestino(FiseConstants.BLANCO);
									cont++;
									//---sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_30));
								}else{
									detalleBean.setCodUbigeoDestino(FiseConstants.BLANCO);
									cont++;
									sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_40,i+1));
								}
								//LOCALIDAD DESTINO
								if( HSSFCell.CELL_TYPE_STRING == celdaLocalidadDestino.getCellType() ){
									detalleBean.setLocalidadDestino(celdaLocalidadDestino.toString());
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaLocalidadDestino.getCellType()  ){
									detalleBean.setLocalidadDestino(FiseConstants.BLANCO);
									cont++;
									//---sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_30));
								}else{
									detalleBean.setLocalidadDestino(FiseConstants.BLANCO);
									cont++;
									sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_40,i+1));
								}
								//ZONA BENEFICIARIO
								if( HSSFCell.CELL_TYPE_NUMERIC == celdaZonaBeneficiario.getCellType()  ){
									detalleBean.setZonaBenef(new Double(celdaZonaBeneficiario.getNumericCellValue()).longValue());
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaZonaBeneficiario.getCellType()  ){
									detalleBean.setZonaBenef(0L);
									cont++;
									//---sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_70));
								}else{
									detalleBean.setZonaBenef(0L);
									cont++;
									sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_80,i+1));
								}
								//CUENTA CONTABLE
								if( HSSFCell.CELL_TYPE_STRING == celdaCuentaContable.getCellType() ){
									detalleBean.setCodCuentaContable(celdaCuentaContable.toString());
								}else if( HSSFCell.CELL_TYPE_NUMERIC == celdaCuentaContable.getCellType()  ){
									String valor = "" + celdaCuentaContable.getNumericCellValue();
									detalleBean.setCodCuentaContable(FormatoUtil.eliminaDecimales(valor));
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaCuentaContable.getCellType()  ){
									detalleBean.setCodCuentaContable(FiseConstants.BLANCO);
									cont++;
									//---sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_30));
								}else{
									detalleBean.setCodCuentaContable(FiseConstants.BLANCO);
									cont++;
									sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_40,i+1));
								}
								//ACTIVIDAD
								if( HSSFCell.CELL_TYPE_STRING == celdaActividad.getCellType() ){
									detalleBean.setActividad(celdaActividad.toString());
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaActividad.getCellType()  ){
									detalleBean.setActividad(FiseConstants.BLANCO);
									cont++;
									//---sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_30));
								}else{
									detalleBean.setActividad(FiseConstants.BLANCO);
									cont++;
									sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_40,i+1));
								}
								//TIPO DOCUMENTO
								if( HSSFCell.CELL_TYPE_STRING == celdaTipoDocumento.getCellType() ){
									detalleBean.setTipoDocumento(celdaTipoDocumento.toString());
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaTipoDocumento.getCellType()  ){
									detalleBean.setTipoDocumento(FiseConstants.BLANCO);
									cont++;
									//---sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_30));
								}else{
									detalleBean.setTipoDocumento(FiseConstants.BLANCO);
									cont++;
									sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_40,i+1));
								}
								//RUC
								if( HSSFCell.CELL_TYPE_STRING == celdaRUCEmpresa.getCellType() ){
									detalleBean.setRucEmpresa(celdaRUCEmpresa.toString());
								}else if( HSSFCell.CELL_TYPE_NUMERIC == celdaRUCEmpresa.getCellType()  ){
									double d = celdaRUCEmpresa.getNumericCellValue();
									String valor = FormatoUtil.conversion(d);
									//String valor = "" + celdaRUCEmpresa.getNumericCellValue();
									detalleBean.setRucEmpresa(FormatoUtil.eliminaDecimales(valor));
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaRUCEmpresa.getCellType()  ){
									detalleBean.setRucEmpresa(FiseConstants.BLANCO);
									cont++;
									//---sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_30));
								}else{
									detalleBean.setRucEmpresa(FiseConstants.BLANCO);
									cont++;
									sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_40,i+1));
								}
								//SERIE DOCUMENTO
								if( HSSFCell.CELL_TYPE_STRING == celdaSerieDocumento.getCellType() ){
									detalleBean.setSerieDocumento(celdaSerieDocumento.toString());
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaSerieDocumento.getCellType()  ){
									detalleBean.setSerieDocumento(FiseConstants.BLANCO);
									cont++;
									//---sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_30));
								}else{
									detalleBean.setSerieDocumento(FiseConstants.BLANCO);
									cont++;
									sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_40,i+1));
								}
								//NRO DOCUMENTO
								if( HSSFCell.CELL_TYPE_STRING == celdaNroDocumento.getCellType() ){
									detalleBean.setNroDocumento(celdaNroDocumento.toString());
								}else if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroDocumento.getCellType()  ){
									String valor = "" + celdaNroDocumento.getNumericCellValue();
									detalleBean.setNroDocumento(FormatoUtil.eliminaDecimales(valor));
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroDocumento.getCellType()  ){
									detalleBean.setNroDocumento(FiseConstants.BLANCO);
									cont++;
									//---sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_30));
								}else{
									detalleBean.setNroDocumento(FiseConstants.BLANCO);
									cont++;
									sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_40,i+1));
								}
								//NRO DIAS
								if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroDias.getCellType()  ){
									detalleBean.setNroDias(new Double(celdaNroDias.getNumericCellValue()).longValue());
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroDias.getCellType()  ){
									detalleBean.setNroDias(0L);
									cont++;
									//---sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_70));
								}else{
									detalleBean.setNroDias(0L);
									cont++;
									sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_80,i+1));
								}
								//ALIMENTACION
								if( HSSFCell.CELL_TYPE_NUMERIC == celdaAlimentacion.getCellType()  ){
									detalleBean.setMontoAlimentacion(new BigDecimal(celdaAlimentacion.getNumericCellValue()));
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaAlimentacion.getCellType()  ){
									detalleBean.setMontoAlimentacion(new BigDecimal(0.00));
								}else{
									detalleBean.setMontoAlimentacion(new BigDecimal(0.00));
									cont++;
									sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_150,i+1));
								}
								//ALOJAMIENTO
								if( HSSFCell.CELL_TYPE_NUMERIC == celdaAlojamiento.getCellType()  ){
									detalleBean.setMontoAlojamiento(new BigDecimal(celdaAlojamiento.getNumericCellValue()));
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaAlojamiento.getCellType()  ){
									detalleBean.setMontoAlojamiento(new BigDecimal(0.00));
								}else{
									detalleBean.setMontoAlojamiento(new BigDecimal(0.00));
									cont++;
									sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_150,i+1));
								}
								//MOVILIDAD
								if( HSSFCell.CELL_TYPE_NUMERIC == celdaMovilidad.getCellType()  ){
									detalleBean.setMontoMovilidad(new BigDecimal(celdaMovilidad.getNumericCellValue()));
								}else if( HSSFCell.CELL_TYPE_BLANK == celdaMovilidad.getCellType()  ){
									detalleBean.setMontoMovilidad(new BigDecimal(0.00));
								}else{
									detalleBean.setMontoMovilidad(new BigDecimal(0.00));
									cont++;
									sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_150,i+1));
								}
								
								/**validar los valores de los inputs correctos*/
								
								if( detalleBean.getAnioEjecucion() != 0 ||
										detalleBean.getMesEjecucion() != 0 ||
										detalleBean.getNroItemEtapa() != 0 ||
										!detalleBean.getCodUbigeoOrigen().equals(FiseConstants.BLANCO) ||
										!detalleBean.getLocalidadOrigen().equals(FiseConstants.BLANCO) ||
										!detalleBean.getCodUbigeoDestino().equals(FiseConstants.BLANCO) ||
										!detalleBean.getLocalidadDestino().equals(FiseConstants.BLANCO) ||
										detalleBean.getZonaBenef() != 0 ||
										!detalleBean.getCodCuentaContable().equals(FiseConstants.BLANCO) ||
										!detalleBean.getActividad().equals(FiseConstants.BLANCO) ||
										!detalleBean.getTipoDocumento().equals(FiseConstants.BLANCO) ||
										!detalleBean.getRucEmpresa().equals(FiseConstants.BLANCO) ||
										!detalleBean.getSerieDocumento().equals(FiseConstants.BLANCO) ||
										!detalleBean.getNroDocumento().equals(FiseConstants.BLANCO) ||
										detalleBean.getNroDias() != 0 ||
										!detalleBean.getMontoAlimentacion().equals(BigDecimal.ZERO) ||
										!detalleBean.getMontoAlojamiento().equals(BigDecimal.ZERO) ||
										!detalleBean.getMontoMovilidad().equals(BigDecimal.ZERO) 
										){
								
									if( !mapaUbigeo.containsKey(detalleBean.getCodUbigeoOrigen()) ){
										cont++;
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_150,i+1));
									}
									if( !mapaUbigeo.containsKey(detalleBean.getCodUbigeoDestino()) ){
										cont++;
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_150,i+1));
									}
									if( !mapaZonaBenef.containsKey(detalleBean.getZonaBenef()) ){
										cont++;
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_150,i+1));
									}
									if( !mapaTipoDocumento.containsKey(detalleBean.getTipoDocumento()) ){
										cont++;
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_150,i+1));
									}
									
								}
								
								if( FiseConstants.BLANCO.equals(sMsgOperativa.toString()) ){
									listaDetalle.add(detalleBean);
								}
								
							}
						}
						
						boolean p = false;
						if(p){
						
						/*if( FiseConstants.BLANCO.equals(sMsg) &&
								FiseConstants.BLANCO.equals(sMsgImplementacion.toString()) &&
								FiseConstants.BLANCO.equals(sMsgOperativa.toString())
								){*/
							
							if( codEmpresa.equals(formulario.getCodigoEmpresa()) &&
									anioPres.equals(String.valueOf(formulario.getAnioPresentacion())) &&
									Long.parseLong(mesPres) == formulario.getMesPresentacion() 
									){
								//se hace la logica para que cuando sea nuevo se crea la cabecera para el primer detalle y luego sobre eso se agregan las demas
								//para cuando es una edicion, se borran primero los detalles existentes de la cabecera y se cargan nuevamente todos los detalles
								
								if( CRUD_CREATE.equals(tipoOperacion) ){
									if( listaDetalle!=null && listaDetalle.size()>0 ){
										FiseFormato12CC formatoNuevo = null;
										for( int i=0; i<listaDetalle.size(); i++ ){
											if( i==0 ){
												formatoNuevo = formatoService.registrarFormato12CCregistrarFormato12CD(listaDetalle.get(i));
												objeto = formatoNuevo;
											}else{
												if( formatoNuevo!=null ){
													objeto = formatoService.modificarFormato12CCregistrarFormato12CD(listaDetalle.get(i), formatoNuevo);
												}
											}
											
										}
									}
									
								}else if( CRUD_UPDATE.equals(tipoOperacion) ){
									FiseFormato12CC formatoModif = new FiseFormato12CC();
									FiseFormato12CCPK id = new FiseFormato12CCPK();
									id.setCodEmpresa(formulario.getCodigoEmpresa());
									id.setAnoPresentacion(formulario.getAnioPresentacion());
									id.setMesPresentacion(formulario.getMesPresentacion());
									id.setEtapa(FiseConstants.ETAPA_SOLICITUD);
									formatoModif = formatoService.obtenerFormato12CCByPK(id);
									
									if( listaDetalle!=null && listaDetalle.size()>0 ){
										//borramos todos los detalles
										for (FiseFormato12CD formato12CD : formatoModif.getFiseFormato12CDs()) {
											formatoService.eliminarFormato12CD(formato12CD);
										}
										//agregamos todos los detalles que se estan cargando
										for (Formato12CCBean detalle : listaDetalle) {
											objeto = formatoService.modificarFormato12CCregistrarFormato12CD(detalle, formatoModif);
										}
									}
								}
							}else{
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_210);
							}
							
						}
						///
						
					}else{
						//--logger.warn(mapaErrores.get(FiseConstants.COD_ERROR_F12_20));
						cont++;
						sMsg = sMsg + "No se encuentra la hoja "+FiseConstants.NOMBRE_HOJA_FORMATO12C+" en el archivo cargado";
						throw new Exception("No se encuentra la hoja "+FiseConstants.NOMBRE_HOJA_FORMATO12C+" en el archivo cargado");
					}
					
					
					
				}
					
			}
			is.close();

		} catch (Exception e) {
			logger.error("Error al leer el archivo excel.",e);
			String error = e.getMessage();
			sMsg = sMsg+error;	        	
			cont++;
			MensajeErrorBean errorBean = new MensajeErrorBean();
			errorBean.setId(cont);
			errorBean.setDescripcion(error);
			listaError.add(errorBean);
			objeto = null;
		}finally{
			StreamUtil.cleanUp(is);
		}
		formatoMensaje.setMensajeError(sMsg);
		formatoMensaje.setFiseFormato12CC(objeto);
		if(listaError.size()>0)
			formatoMensaje.setListaMensajeError(listaError);
		
		return formatoMensaje;
	}

}
