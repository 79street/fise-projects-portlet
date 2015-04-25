package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.Formato12C12D13Generic;
import gob.osinergmin.fise.bean.Formato12CCBean;
import gob.osinergmin.fise.bean.Formato12CMensajeBean;
import gob.osinergmin.fise.bean.MensajeErrorBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.AdmUbigeo;
import gob.osinergmin.fise.domain.CfgCampo;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
	private static final String CRUD_CREATEUPDATE = "CREATEUPDATE";
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
	
	Map<String, String> mapaEtapa;
	
	List<FisePeriodoEnvio> listaPeriodoEnvio;
	
	//bean de sesion
	Formato12CCBean beanBusqueda;

	@RequestMapping
	public String defaultView(ModelMap model, RenderRequest renderRequest, RenderResponse renderResponse, @ModelAttribute("formato12CCBean") Formato12CCBean bean) {

		listaPeriodoEnvio = new ArrayList<FisePeriodoEnvio>();
		
		bean.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));
		bean.setListaMes(fiseUtil.getMapaMeses());
		
		if( beanBusqueda!=null && beanBusqueda.getCodEmpresaB()!=null ){
			bean.setCodEmpresaB(beanBusqueda.getCodEmpresaB());
		}
		if( beanBusqueda!=null && beanBusqueda.getAnioDesde()!=null ){
			bean.setAnioDesde(beanBusqueda.getAnioDesde());
		}else{
			bean.setAnioDesde(fiseUtil.obtenerNroAnioFechaAnterior());
		}
		if( beanBusqueda!=null && beanBusqueda.getAnioHasta()!=null ){
			bean.setAnioHasta(beanBusqueda.getAnioHasta());
		}else{
			bean.setAnioHasta(fiseUtil.obtenerNroAnioFechaActual());
		}
		if( beanBusqueda!=null && beanBusqueda.getMesDesde()!=null ){
			bean.setMesDesde(beanBusqueda.getMesDesde());
		}else{
			bean.setMesDesde(fiseUtil.obtenerNroMesFechaAnterior());
		}
		if( beanBusqueda!=null && beanBusqueda.getMesHasta()!=null ){
			bean.setMesHasta(beanBusqueda.getMesHasta());
		}else{
			bean.setMesHasta(fiseUtil.obtenerNroMesFechaActual());
		}
		if( beanBusqueda!=null && beanBusqueda.getEtapaB()!=null ){
			bean.setEtapaB(beanBusqueda.getEtapaB());
		}
		
		/*bean.setAnioDesde(fiseUtil.obtenerNroAnioFechaActual());
		bean.setMesDesde(String.valueOf(Integer.parseInt(fiseUtil.obtenerNroMesFechaActual()) - 1));
		bean.setAnioHasta(fiseUtil.obtenerNroAnioFechaActual());
		bean.setMesHasta(fiseUtil.obtenerNroMesFechaActual());*/
		
		model.addAttribute("esAdministrador", fiseUtil.esAdministrador(renderRequest));

		mapaErrores = fiseUtil.getMapaErrores();
		mapaSectorTipico = fiseUtil.getMapaSectorTipico();
		mapaEmpresa = fiseUtil.getMapaEmpresa();
		mapaMeses = fiseUtil.getMapaMeses();
		mapaZonaBenef = fiseUtil.getMapaZonaBenef();
		mapaEtapaEjecucion = fiseUtil.getMapaEtapaEjecucion();
		
		mapaTipoDocumento = fiseUtil.getMapTipoDocumento();
		mapaUbigeo = fiseUtil.getMapaUbigeo();
		
		mapaEtapa = fiseUtil.getMapaEtapa();

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
  				/*if(formato.getFechaEnvioDefinitivo()!=null){
  					formato.setDescEstado(FiseConstants.ESTADO_FECHAENVIO_ENVIADO);
  				}else{
  					formato.setDescEstado(FiseConstants.ESTADO_FECHAENVIO_POR_ENVIAR);
  				}*/
  				
  				/**Obteniendo el flag de la operacion*/
  				String flagOper = commonService.obtenerEstadoProceso(formato.getId().getCodEmpresa(),FiseConstants.TIPO_FORMATO_12C,formato.getId().getAnoPresentacion(),
  						formato.getId().getMesPresentacion(), formato.getId().getEtapa());
  				logger.info("flag operacion:  "+flagOper);
  				
  				formato.setDescEstado(FormatoUtil.cambiaTextoAMinusculas(flagOper, 0));
  				
  				jsonArray.put(new Formato12CGartJSON().asJSONObject(formato,"",flagOper));
			}
			
			//valores busqueda
			beanBusqueda = new Formato12CCBean();
			beanBusqueda.setCodEmpresaB(bean.getCodEmpresaB());
			beanBusqueda.setAnioDesde(bean.getAnioDesde());
			beanBusqueda.setMesDesde(bean.getMesDesde());
			beanBusqueda.setAnioHasta(bean.getAnioHasta());
			beanBusqueda.setMesHasta(bean.getMesHasta());
			beanBusqueda.setEtapaB(bean.getEtapaB());

			fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_FORMATO_12C, FiseConstants.NOMBRE_EXCEL_FORMATO12C, FiseConstants.NOMBRE_HOJA_FORMATO12C, listaFormato);

			logger.info("arreglo json:" + jsonArray);
			PrintWriter pw = response.getWriter();
			pw.write(jsonArray.toString());
			pw.flush();
			pw.close();
		} catch (Exception e) {			
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
		
		String descGrupoInformacion = renderRequest.getParameter("descGrupoInformacion");
		String descEstado = renderRequest.getParameter("descestado");
		
		System.out.println("codEmpresa::>" + codEmpresa);
		System.out.println("periodo::>" + periodo);

		if (codEmpresa != null) {
			bean.setCodigoEmpresa(codEmpresa);
		}
		
		if (descGrupoInformacion != null) {
			bean.setDescGrupoInformacion(descGrupoInformacion);
		}
		if (descEstado != null) {
			bean.setDescEstado(descEstado);
		}
		
		//valores busqueda
		beanBusqueda = new Formato12CCBean();
		beanBusqueda.setCodEmpresaB(bean.getCodEmpresaB());
		beanBusqueda.setAnioDesde(bean.getAnioDesde());
		beanBusqueda.setMesDesde(bean.getMesDesde());
		beanBusqueda.setAnioHasta(bean.getAnioHasta());
		beanBusqueda.setMesHasta(bean.getMesHasta());
		beanBusqueda.setEtapaB(bean.getEtapaB());
		
		
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
	public String viewEditFormato(ModelMap model, RenderRequest request, RenderResponse response, 
			@ModelAttribute("formato12CCBean") Formato12CCBean bean) {

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
				/*if(f.getFechaEnvioDefinitivo()!=null){
					f.setDescEstado(FiseConstants.ESTADO_FECHAENVIO_ENVIADO);
				}else{
					f.setDescEstado(FiseConstants.ESTADO_FECHAENVIO_POR_ENVIAR);
				}*/
				
				/**Obteniendo el flag de la operacion*/
  				String flagOper = commonService.obtenerEstadoProceso(pk.getCodEmpresa(),FiseConstants.TIPO_FORMATO_12C,pk.getAnoPresentacion(),pk.getMesPresentacion(), pk.getEtapa());
  				
  				f.setDescEstado(FormatoUtil.cambiaTextoAMinusculas(flagOper, 0));
				
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
				//
				//seteamos el ano inicio y fin de vigencia
				bean.setAnioEjecucionDetalle(""+f.getAnoEjecucionView());
				bean.setMesEjecucionDetalle(""+mapaMeses.get(f.getMesEjecucionView()));
				
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
	public void gridDetalle(ModelMap model, ResourceRequest request, ResourceResponse response, 
			@ModelAttribute("formato12CCBean") Formato12CCBean bean) {

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
			pw.write(jsonObj.toString());		
			pw.flush();
			pw.close();

			model.addAttribute("formato12CCBean", bean);
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	
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
  			e.printStackTrace();
  		}
	}
	
	@ResourceMapping("cargaFlagPeriodo")
  	public void cargaFlagPeriodo(ResourceRequest request,ResourceResponse response,
  			@ModelAttribute("formato12CCBean") Formato12CCBean bean){
		try {			
  			response.setContentType("applicacion/json");
  			String periodoEnvio = bean.getPeriodoEnvio();
  			JSONObject jsonObj = new JSONObject();
  			
  			String anoPresentacion = "";
  			String mesPresentacion = "";
  			
  			if( periodoEnvio!=null && periodoEnvio.length()>6 ){
  				anoPresentacion = periodoEnvio.substring(0, 4);
  				mesPresentacion = periodoEnvio.substring(4,6);
  			}
  			
  			long anoP =0;
  			long mesP =0;
  			
  			if(FormatoUtil.isNotBlank(anoPresentacion)){ 
  				anoP = Long.valueOf(anoPresentacion);
  			}
  			if(FormatoUtil.isNotBlank(mesPresentacion)){ 
  				mesP = Long.valueOf(mesPresentacion);
  			}
  			
  			boolean ultimaEtapaFormato = fiseUtil.bloquearFormatoXEtapa(FiseConstants.TIPO_FORMATO_12C,bean.getCodigoEmpresa(),anoP, mesP,0,0,0,0);
  			if(ultimaEtapaFormato){
  				jsonObj.put("etapaFinal","SI");
  			}else{
  				jsonObj.put("etapaFinal","NO");
  			}
  			
  			if( periodoEnvio!=null && periodoEnvio.length()>6 ){
  				long idGrupo = commonService.obtenerIdGrupoInformacion(Long.parseLong(periodoEnvio.substring(0, 4)), Long.parseLong(periodoEnvio.substring(4, 6)), FiseConstants.MENSUAL);
  				jsonObj.put("idGrupoInfo", idGrupo);
  			}else{
  				jsonObj.put("idGrupoInfo", 0);
  			}  			
  			
  			PrintWriter pw = response.getWriter();
  		    pw.write(jsonObj.toString());
  		    pw.flush();
  		    pw.close();							
  		}catch (Exception e) {  			
  			e.printStackTrace();
  		}
	}
	
	@ActionMapping(params = "action=nuevoDetalle")
	public void nuevoDetalleFormato(ModelMap model, ActionRequest request, ActionResponse response, 
			@ModelAttribute("formato12CCBean") Formato12CCBean bean,
			@RequestParam("origen") String origen ) {
		String codEmpresa = bean.getCodigoEmpresa();	
		String periodoDeclaracion = bean.getPeriodoEnvio();//se obtiene el valor del periodo guardado de el campo descripcionPeriodoHidden(valorPeriodoHidden), probar los demas flujos
		
		String msg = "";
		
		if( codEmpresa==null ){
			codEmpresa = bean.getCodigoEmpresaHidden();
		}
		if( periodoDeclaracion==null ){
			periodoDeclaracion = bean.getPeriodoEnvioHidden();
		}
		
		if( "0".equals(origen) ){
			response.setRenderParameter("crud", CRUD_CREATE);
		}else if( "1".equals(origen) ){
			response.setRenderParameter("crud", CRUD_CREATEUPDATE);
		}				
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
		
	}

	@RequestMapping(params = "action=detalle")
	public String detalle(ModelMap model, RenderRequest renderRequest, RenderResponse renderResponse, 
			@RequestParam("crud") String crud, @RequestParam("msg") String msg,
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
		
		bean.setCodigoEmpresa(codEmpresa.trim());
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
		
		List<FisePeriodoEnvio> listaPeriodoEnvioN = periodoService.listarFisePeriodoEnvioMesAnioEtapa(codEmpresa, FiseConstants.TIPO_FORMATO_12C);
		bean.setListaPeriodoEnvio(listaPeriodoEnvioN);
		
		//add item
		bean.setNroItemEtapa(Long.parseLong(nroItemEtapa));
		
		model.addAttribute("readonly", "false");

		// cargamos el ano y fin de vigencia
		//----List<FisePeriodoEnvio> listaPeriodoEnvio = periodoService.listarFisePeriodoEnvioMesAnioEtapa(codEmpresa, FiseConstants.TIPO_FORMATO_13A);
		for (FisePeriodoEnvio periodo : listaPeriodoEnvioN) {
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
		
		if( "OK1".equals(msg) || "OK2".equals(msg) || "DONE".equals(msg) ){
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
						
						if( bean.getCodigoEmpresa().trim().equals(detalle.getId().getCodEmpresa().trim()) &&
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
			} else if (CRUD_CREATE.equals(crud) || CRUD_CREATEUPDATE.equals(crud) ) {
				model.addAttribute("readonlyEdit", "false");
				bean.setAnioEjecucion(Long.parseLong(anioPres));
				bean.setMesEjecucion(Long.parseLong(mesPres));			
			}		
		}

		model.addAttribute("crud", crud);
		model.addAttribute("msg", msg);		
		//valores constantes para las empresas edelnor y luz del sur
		bean.setCodEdelnor(FiseConstants.COD_EMPRESA_EDELNOR);
		bean.setCodLuzSur(FiseConstants.COD_EMPRESA_LUZ_SUR);
		
		return "formato12CCRUDDetalle";
	}
	
	@ResourceMapping("provincias")
	public void cargarProvincias(ModelMap model, SessionStatus status, ResourceRequest request, 
			ResourceResponse response, @RequestParam("codDepartamento") String codDepartamento) {
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
			e.printStackTrace();
		}
	}

	@ResourceMapping("distritos")
	public void cargaDistritos(ModelMap model, SessionStatus status, ResourceRequest request, 
			ResourceResponse response, @RequestParam("codProvincia") String codProvincia) {
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
			e.printStackTrace();
		}
	}
	
	@ActionMapping(params = "action=guardarDetalle")
	public void guardarDetalleFormato(ModelMap model, ActionRequest request, ActionResponse response, 
			@RequestParam("crud") String crud, 			
			@RequestParam("codEmpresa") String codEmpresa, 			
			@ModelAttribute("formato12CCBean") Formato12CCBean bean) {

		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

		String msg = "";
		
		FiseFormato12CC f = null;	
		
		Long anoPresentacion = bean.getAnioPresentacion();
		Long mesPresentacion = bean.getMesPresentacion();
		String etapa = bean.getEtapa();
		String periodoEnvio = bean.getPeriodoEnvio();		
		
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
				if( CRUD_CREATE.equals(crud) || CRUD_CREATEUPDATE.equals(crud) ){
					f = formatoService.modificarFormato12CCregistrarFormato12CD(bean, formato);
					msg = "OK1";
				}else if( CRUD_UPDATE.equals(crud) ){
					f = formatoService.modificarFormato12CCmodificarFormato12CD(bean, formato);
					msg = "OK2";
				}
				
			} else {
				//para nuevos registros
				f = formatoService.registrarFormato12CCregistrarFormato12CD(bean);
				msg = "OK1";
			}			
			//----msg = "OK";			
		} catch (Exception e) {
			//e.printStackTrace();
			msg = "ERROR";
		}
		
		if(periodoEnvio==null){
			periodoEnvio = ""+anoPresentacion+FormatoUtil.rellenaIzquierda(""+mesPresentacion, '0', 2)+etapa;
		}
		
		if( "OK1".equals(msg) ){
			response.setRenderParameter("crud", CRUD_UPDATE);
		}else if( "OK2".equals(msg) ){
			response.setRenderParameter("crud", CRUD_UPDATE);
		}else if( "ERROR".equals(msg) ){
			if( CRUD_CREATE.equals(crud) ){
				response.setRenderParameter("crud", CRUD_CREATE);
			}else if ( CRUD_CREATEUPDATE.equals(crud) ) {
				response.setRenderParameter("crud", CRUD_CREATEUPDATE);
			}else if ( CRUD_UPDATE.equals(crud) ) {
				response.setRenderParameter("crud", CRUD_UPDATE);
			}
		}
		
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
		
	}
	
	@ResourceMapping("deleteDetalle")
	public void deleteDetalle(ResourceRequest request, ResourceResponse response, 
			@ModelAttribute("formato12CCBean") Formato12CCBean bean) {
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
	public void deleteCabecera(ResourceRequest request, ResourceResponse response, 
			@ModelAttribute("formato12CCBean") Formato12CCBean bean) {
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
	public void reporte(ResourceRequest request, ResourceResponse response, 
			@ModelAttribute("formato12CCBean") Formato12CCBean bean) {
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
				// cargamos la lista a enviar
				//cambios elozano
				List<FiseFormato12CD> listaEnviar = new ArrayList<FiseFormato12CD>();
				//logger.error("Tamanio de lista a enviar para el reporte:  "+formato.getFiseFormato12CDs().size());
				for(FiseFormato12CD d : formato.getFiseFormato12CDs()){					
					d.setDescZonaBenef(mapaZonaBenef.get(d.getIdZonaBenef()));
					d.setIdTipDocRef(mapaTipoDocumento.get(d.getIdTipDocRef())); 
					d.setEtapaEjecucionReport(d.getId().getEtapaEjecucion());
					listaEnviar.add(d);
				}
				
				session.setAttribute("lista", listaEnviar);				
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
	public void reporteValidacion(ResourceRequest request, ResourceResponse response, 
			@ModelAttribute("formato12CCBean") Formato12CCBean bean) {
		try {
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
			HttpSession session = httpRequest.getSession();
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

			JSONArray jsonArray = new JSONArray();

			String nombreReporte = request.getParameter("nombreReporte").trim();
			String nombreArchivo = request.getParameter("nombreArchivo").trim();
			String tipoFormato = FiseConstants.TIPO_FORMATO_VAL_12C;
			String tipoArchivo = request.getParameter("tipoArchivo").trim();	

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
			mapa.put("ETAPA", mapaEtapa.get(etapa));
			session.setAttribute("mapa", mapa);		

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
	public void envioDefinitivo(ResourceRequest request, ResourceResponse response, 
			@ModelAttribute("formato12CCBean") Formato12CCBean bean) {
		FiseFormato12CC formato = null;		
		try {

			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
			HttpSession session = httpRequest.getSession();
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			JSONObject jsonObj = new JSONObject(); 
			List<FileEntryJSP> listaArchivo = new ArrayList<FileEntryJSP>();		

			Formato12CCBean reportBean = new Formato12CCBean();
			Map<String, Object> mapa = null;
			String directorio = null;
           //cambios		    
		    boolean valorFormato = false;
		    boolean valorActa = false;			
			String respuestaEmail ="";	

			String codEmpresa = bean.getCodigoEmpresaHidden();
			Long anoPresentacion = bean.getAnioPresentacion();
			Long mesPresentacion = bean.getMesPresentacion();
			String etapa = bean.getEtapa();

			String nombreReporte = request.getParameter("nombreReporte").trim();
			String nombreArchivo = request.getParameter("nombreArchivo").trim();
            logger.info("Nombre del archivo:  "+nombreArchivo); 
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
				//cambios flag observacion
				String flagEnvioObs = "";
				String periodoEnvio = bean.getPeriodoEnvioHidden();
				logger.info("Periodo para comparar :" +periodoEnvio);
				List<FisePeriodoEnvio> listaPeriodoEnvio = periodoService.listarFisePeriodoEnvioMesAnioEtapa(codEmpresa, FiseConstants.TIPO_FORMATO_12C);
				for (FisePeriodoEnvio p : listaPeriodoEnvio) {
					if(periodoEnvio.equals(p.getCodigoItem()) ){
						 flagEnvioObs = p.getFlagEnvioConObservaciones();
						break;
					}
				}
				if(listaObservaciones!=null && listaObservaciones.size()>0 &&
			    		FiseConstants.PERIODO_CON_ENVIO_OBS_NO.equals(flagEnvioObs)){
			    	 jsonObj.put("resultado", "OBSERVACION");	
			    }else{
			    	if (mapa != null) {
						mapa.put("IMG", session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg"));
						mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
						// verificar si ponerlo aca o no
						mapa.put("USUARIO", themeDisplay.getUser().getLogin());
						mapa.put("NOMBRE_FORMATO", descripcionFormato);
						mapa.put("FECHA_ENVIO", FechaUtil.obtenerFechaActual());
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
						cumplePlazo = commonService.fechaEnvioCumplePlazo(FiseConstants.TIPO_FORMATO_12C, 
								formato.getId().getCodEmpresa(), 
								formato.getId().getAnoPresentacion(), 
								formato.getId().getMesPresentacion(), 
								formato.getId().getEtapa(), 
								FechaUtil.fecha_DD_MM_YYYY(FechaUtil.obtenerFechaActual()));
						if (cumplePlazo) {
							mapa.put("CHECKED_CUMPLEPLAZO", dirCheckedImage);
						} else {
							mapa.put("CHECKED_CUMPLEPLAZO", dirUncheckedImage);
						}
						if( listaObservaciones!=null && !listaObservaciones.isEmpty() ){
							mapa.put(FiseConstants.PARAM_CHECKED_OBSERVACION, dirCheckedImage);
						}else{
							mapa.put(FiseConstants.PARAM_CHECKED_OBSERVACION, dirUncheckedImage);
						}
						mapa.put("ETAPA", mapaEtapa.get(formato.getId().getEtapa()));
					}

					/** REPORTE FORMATO 12C */
					nombreReporte = "formato12C";
					nombreArchivo = nombreReporte;
					directorio = "/reports/" + nombreReporte + ".jasper";
					File reportFile = new File(session.getServletContext().getRealPath(directorio));
					byte[] bytes = null;
					
					if( formato.getFiseFormato12CDs()!=null && formato.getFiseFormato12CDs().size()>0 ){
						bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), mapa, new JRBeanCollectionDataSource(formato.getFiseFormato12CDs()));
					}else{
						bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), mapa, new JREmptyDataSource());
					}
					
					
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
							valorFormato = true;
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
							valorActa = true;
						}
					}
				    /**actualizamos  la fecha de envio*/
					String valorActuaizar = "0";
					if(valorFormato && valorActa){
						//formatoActualizar =  formatoService.modificarEnvioDefinitivoFormato12CC(bean, formato);
						valorActuaizar =  formatoService.modificarEnvioDefinitivoFormato12CC(
								themeDisplay.getUser().getLogin(),
			    			    themeDisplay.getUser().getLoginIP(), formato);
					}
					if(valorActuaizar.equals("1")){
						if (listaArchivo != null && listaArchivo.size() > 0) {					
							respuestaEmail= fiseUtil.enviarMailsAdjunto(
					    			   request,
					    			   listaArchivo, 
					    			   fiseUtil.getMapaEmpresa().get(formato.getId().getCodEmpresa()),
					    			   formato.getId().getAnoPresentacion(),
					    			   formato.getId().getMesPresentacion(),
					    			   FiseConstants.TIPO_FORMATO_12C,
					    			   descripcionFormato,
					    			   FiseConstants.FRECUENCIA_MENSUAL_DESCRIPCION,
					    			   null,
					    			   null);
						}
						String[] msnId = respuestaEmail.split("/");
						if(FiseConstants.PROCESO_ENVIO_EMAIL_OK.equals(msnId[0])){
							jsonObj.put("resultado", "OK");	
							jsonObj.put("correo",msnId[1]);		
						}else{
							jsonObj.put("resultado", "EMAIL");//error al enviar al email	
							jsonObj.put("correo",msnId[1]);		
						}	
					}else{
						 jsonObj.put("resultado", "ERROR");//ocurrio un error al actualizar fecha envio o al formar
			    		   //los reportes del formato o del acta de envio
					}
				}	//fin del else flag observacion NO					
			}else{
				 jsonObj.put("resultado", "ERROR"); //formato null  	
			}
			response.setContentType("application/json");
			logger.info("arreglo json:"+jsonObj);
			PrintWriter pw = response.getWriter();
			pw.write(jsonObj.toString());
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
           if(formato!=null){
        	   formato = null;
           }           
		}
	}

	public void cargarListaObservaciones(List<FiseFormato12CD> listaDetalle) {
		int cont = 0;
		listaObservaciones = new ArrayList<MensajeErrorBean>();
		if( listaDetalle!=null && listaDetalle.size()>0 ){
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
	public void reporteActaEnvio(ResourceRequest request,ResourceResponse response, 
			@ModelAttribute("formato12CCBean") Formato12CCBean bean) {
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
					mapa.put(FiseConstants.PARAM_CHECKED_OBSERVACION, dirCheckedImage);
				}else{
					mapa.put(FiseConstants.PARAM_CHECKED_OBSERVACION, dirUncheckedImage);
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
	public void cargarDocumento(ActionRequest request,ActionResponse response,
			@ModelAttribute("formato12CCBean") Formato12CCBean bean){
		
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
				formatoMensaje = readExcelFile(fileEntry, themeDisplay.getUser(), tipoOperacion, codEmpresaNew, anioPresNew, mesPresNew, etapaNew);
			}else if( flagCarga.equals(FiseConstants.FLAG_CARGAEXCEL_FORMULARIOMODIFICACION) ){
				fileEntry=fiseUtil.subirDocumento(request, uploadPortletRequest, FiseConstants.TIPOARCHIVO_XLS);
				formatoMensaje = readExcelFile(fileEntry, themeDisplay.getUser(), tipoOperacion, codEmpresaEdit, anioPresEdit, mesPresEdit, etapaEdit);
			}else if( flagCarga.equals(FiseConstants.FLAG_CARGATXT_FORMULARIONUEVO) ){
				fileEntry =fiseUtil.subirDocumento(request, uploadPortletRequest, FiseConstants.TIPOARCHIVO_TXT);
				formatoMensaje =	readTxtFile(fileEntry, uploadPortletRequest, themeDisplay.getUser(), tipoOperacion, codEmpresaNew, anioPresNew, mesPresNew, etapaNew);
			}else if( flagCarga.equals(FiseConstants.FLAG_CARGATXT_FORMULARIOMODIFICACION) ){
				fileEntry=fiseUtil.subirDocumento(request, uploadPortletRequest, FiseConstants.TIPOARCHIVO_TXT);
				formatoMensaje =	readTxtFile(fileEntry, uploadPortletRequest, themeDisplay.getUser(), tipoOperacion, codEmpresaEdit, anioPresEdit, mesPresEdit, etapaEdit);
			}
		
		}catch(FileMimeTypeException ex){
			
		}catch (Exception e) {			
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
	}
	
	
	public Formato12CMensajeBean readExcelFile(FileEntry archivo, User user, String tipoOperacion, 
			String codEmpresa, 	String anioPres, String mesPres, String etapaEdit) {
		
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
					logger.warn(mapaErrores.get(FiseConstants.COD_ERROR_F12C_3350));
					cont++;
					sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12C_3350);
					throw new Exception(mapaErrores.get(FiseConstants.COD_ERROR_F12C_3350));
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
						
						formulario.setEtapa(etapaEdit);
						
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
								if(cell!=null && HSSFCell.CELL_TYPE_STRING==cell.getCellType()  && HSSFCell.CELL_TYPE_BLANK != cell.getCellType() ){
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
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3351);
						}else{
							formulario.setCodigoEmpresa("");
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3352);
						}
						
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaAnio.getCellType()  ){
							formulario.setAnioPresentacion(new Double(celdaAnio.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaAnio.getCellType()  ){
							formulario.setAnioPresentacion(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3353);
						}else{
							formulario.setAnioPresentacion(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3354);
						}
						
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaMes.getCellType()  ){
							formulario.setMesPresentacion(new Double(celdaMes.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaMes.getCellType()  ){
							formulario.setMesPresentacion(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3355);
						}else{
							formulario.setMesPresentacion(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3356);
						}
						
						if( inicioImplementacion!=0 && inicioOperativa!=0 && finRegistros!=0 ){
							for (int i = (inicioImplementacion+1); i<(inicioOperativa-1); i++) {
								
								Formato12CCBean detalleBean = new Formato12CCBean();
								
								HSSFRow row= hojaF12.getRow(i);
								
								HSSFCell celdaAnoEjecucion = row.getCell(FiseConstants.NRO_CELDA_ANO_EJECUCION_FORMATO12C);
								HSSFCell celdaMesEjecucion = row.getCell(FiseConstants.NRO_CELDA_MES_EJECUCION_FORMATO12C);
								//HSSFCell celdaItem = row.getCell(FiseConstants.NRO_CELDA_ITEM_FORMATO12C);
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
								
								detalleBean.setEtapa(etapaEdit);
								
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
									sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3371,i+1));
								}
								
								boolean target=false;
								if(  FiseConstants.ZONABENEF_LIMA_COD == detalleBean.getZonaBenef() ){
									if( FiseConstants.COD_EMPRESA_EDELNOR.equals(formulario.getCodigoEmpresa()) || FiseConstants.COD_EMPRESA_LUZ_SUR.equals(formulario.getCodigoEmpresa()) ){
										target=true;
									}
								}else{
									target=true;
								}
								
								if(target){
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
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3357,i+1));
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
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3359,i+1));
									}
									
									/**validacion de periodo de ejecucion*/
									if( detalleBean.getAnioEjecucion()!=null && detalleBean.getMesEjecucion()!=null ){
										if( (detalleBean.getAnioEjecucion()*100+detalleBean.getMesEjecucion())>(detalleBean.getAnioPresentacion()*100+detalleBean.getMesPresentacion()) ){
											cont++;
											sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3631,i+1));
										}
									}
									/***/
									
									//NRO ITEM ETAPA
									/*if( HSSFCell.CELL_TYPE_NUMERIC == celdaItem.getCellType()  ){
										detalleBean.setNroItemEtapa(new Double(celdaItem.getNumericCellValue()).longValue());
									}else if( HSSFCell.CELL_TYPE_BLANK == celdaItem.getCellType()  ){
										detalleBean.setNroItemEtapa(0L);
										cont++;
										//---sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_70));
									}else{
										detalleBean.setNroItemEtapa(0L);
										cont++;
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3357,i+1));
									}*/
									//COD UBIGEO ORIGEN
									if( HSSFCell.CELL_TYPE_STRING == celdaCodUbigeoOrigen.getCellType() ){
										detalleBean.setCodUbigeoOrigen(celdaCodUbigeoOrigen.toString());
									}else if( HSSFCell.CELL_TYPE_BLANK == celdaCodUbigeoOrigen.getCellType()  ){
										detalleBean.setCodUbigeoOrigen(FiseConstants.BLANCO);
										cont++;
										//---sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_30));
									}else if( HSSFCell.CELL_TYPE_NUMERIC == celdaCodUbigeoOrigen.getCellType()  ){
										double d = celdaCodUbigeoOrigen.getNumericCellValue();
										String valor = FormatoUtil.conversion(d);
										detalleBean.setCodUbigeoOrigen(FormatoUtil.eliminaDecimales(valor));
									}else{
										detalleBean.setCodUbigeoOrigen(FiseConstants.BLANCO);
										cont++;
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3361,i+1));
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
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3364,i+1));
									}
									//COD UBIGEO DESTINO
									if( HSSFCell.CELL_TYPE_STRING == celdaCodUbigeoDestino.getCellType() ){
										detalleBean.setCodUbigeoDestino(celdaCodUbigeoDestino.toString());
									}else if( HSSFCell.CELL_TYPE_BLANK == celdaCodUbigeoDestino.getCellType()  ){
										detalleBean.setCodUbigeoDestino(FiseConstants.BLANCO);
										cont++;
										//---sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_30));
									}else if( HSSFCell.CELL_TYPE_NUMERIC == celdaCodUbigeoDestino.getCellType()  ){
										double d = celdaCodUbigeoDestino.getNumericCellValue();
										String valor = FormatoUtil.conversion(d);
										detalleBean.setCodUbigeoDestino(FormatoUtil.eliminaDecimales(valor));
									}else{
										detalleBean.setCodUbigeoDestino(FiseConstants.BLANCO);
										cont++;
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3366,i+1));
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
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3369,i+1));
									}
									
									//CUENTA CONTABLE									
									if( HSSFCell.CELL_TYPE_STRING == celdaCuentaContable.getCellType() ){
										detalleBean.setCodCuentaContable(celdaCuentaContable.toString());
										logger.error("pasando cuenta a String:   "+celdaCuentaContable.toString());
									}else if( HSSFCell.CELL_TYPE_NUMERIC == celdaCuentaContable.getCellType()  ){
										String valor = "" + celdaCuentaContable.getNumericCellValue();
										detalleBean.setCodCuentaContable(FormatoUtil.eliminaDecimales(valor));
										logger.error("pasando cuenta  numeric:   "+valor);
										logger.error("pasando cuenta a numeric:   "+FormatoUtil.eliminaDecimales(valor));
									}else if( HSSFCell.CELL_TYPE_BLANK == celdaCuentaContable.getCellType()  ){
										detalleBean.setCodCuentaContable(FiseConstants.BLANCO);
										cont++;
										//---sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_30));
									}else{
										detalleBean.setCodCuentaContable(FiseConstants.BLANCO);
										cont++;
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3374,i+1));
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
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3376,i+1));
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
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3378,i+1));
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
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3381,i+1));
									}
									//SERIE DOCUMENTO
									logger.error("tipo de la celda serie documento implementacion::   "+celdaSerieDocumento.getCellType()); 
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
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3383,i+1));
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
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3385,i+1));
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
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3387,i+1));
									}
									//ALIMENTACION
									if( HSSFCell.CELL_TYPE_NUMERIC == celdaAlimentacion.getCellType()  ){
										detalleBean.setMontoAlimentacion(new BigDecimal(celdaAlimentacion.getNumericCellValue()).setScale(2, RoundingMode.HALF_UP));
									}else if( HSSFCell.CELL_TYPE_BLANK == celdaAlimentacion.getCellType()  ){
										detalleBean.setMontoAlimentacion(new BigDecimal(0.00));
									}else{
										detalleBean.setMontoAlimentacion(new BigDecimal(0.00));
										cont++;
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3389,i+1));
									}
									//ALOJAMIENTO
									if( HSSFCell.CELL_TYPE_NUMERIC == celdaAlojamiento.getCellType()  ){
										detalleBean.setMontoAlojamiento(new BigDecimal(celdaAlojamiento.getNumericCellValue()).setScale(2, RoundingMode.HALF_UP));
									}else if( HSSFCell.CELL_TYPE_BLANK == celdaAlojamiento.getCellType()  ){
										detalleBean.setMontoAlojamiento(new BigDecimal(0.00));
									}else{
										detalleBean.setMontoAlojamiento(new BigDecimal(0.00));
										cont++;
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3391,i+1));
									}
									//MOVILIDAD
									if( HSSFCell.CELL_TYPE_NUMERIC == celdaMovilidad.getCellType()  ){
										detalleBean.setMontoMovilidad(new BigDecimal(celdaMovilidad.getNumericCellValue()).setScale(2, RoundingMode.HALF_UP));
									}else if( HSSFCell.CELL_TYPE_BLANK == celdaMovilidad.getCellType()  ){
										detalleBean.setMontoMovilidad(new BigDecimal(0.00));
									}else{
										detalleBean.setMontoMovilidad(new BigDecimal(0.00));
										cont++;
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3393,i+1));
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
											sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3362,i+1));
										}
										if( !mapaUbigeo.containsKey(detalleBean.getCodUbigeoDestino()) ){
											cont++;
											sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3367,i+1));
										}
										if( !mapaZonaBenef.containsKey(detalleBean.getZonaBenef()) ){
											cont++;
											sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3372,i+1));
										}
										if( !mapaTipoDocumento.containsKey(detalleBean.getTipoDocumento()) ){
											cont++;
											sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3379,i+1));
										}
										
									}
									
									/**validacion de estructura de campos*/
									//ZONA BENEFICIARIO - 1
									if( !FormatoUtil.validaCampoNumeroEntero(detalleBean.getZonaBenef(),1) ){
										cont++;
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3371,i+1));
									}
									//ANO EJECUCION - 4
									if( !FormatoUtil.validaCampoNumeroEntero(detalleBean.getAnioEjecucion(),4) ){
										cont++;
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3357,i+1));
									}
									//MES EJECUCION - 2
									if( !FormatoUtil.validaCampoNumeroEntero(detalleBean.getMesEjecucion(),2) ){
										cont++;
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3359,i+1));
									}
									//COD UBIGEO ORIGEN - 6
									if( !FormatoUtil.validaCampoString(detalleBean.getCodUbigeoOrigen(),6) ){
										cont++;
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3361,i+1));
									}
									//LOCALIDAD ORIGEN - 50
									if( !FormatoUtil.validaCampoString(detalleBean.getLocalidadOrigen(),50) ){
										cont++;
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3364,i+1));
									}
									//COD UBIGEO DESTINO - 6
									if( !FormatoUtil.validaCampoString(detalleBean.getCodUbigeoDestino(),6) ){
										cont++;
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3366,i+1));
									}
									//LOCALIDAD DESTINO - 50
									if( !FormatoUtil.validaCampoString(detalleBean.getLocalidadDestino(),50) ){
										cont++;
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3369,i+1));
									}
									//CUENTA CONTABLE - 30
									if( !FormatoUtil.validaCampoString(detalleBean.getCodCuentaContable(),30) ){
										cont++;
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3374,i+1));
									}
									//ACTIVIDAD - 250
									if( !FormatoUtil.validaCampoString(detalleBean.getActividad(),250) ){
										cont++;
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3376,i+1));
									}
									//TIPO DOCUMENTO - 3
									if( !FormatoUtil.validaCampoString(detalleBean.getTipoDocumento(),3) ){
										cont++;
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3378,i+1));
									}
									//RUC - 11
									if( !FormatoUtil.validaCampoString(detalleBean.getRucEmpresa(),11) ){
										cont++;
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3381,i+1));
									}
									//SERIE DOCUMENTO - 6
									if( !FormatoUtil.validaCampoString(detalleBean.getSerieDocumento(),6) ){
										cont++;
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3383,i+1));
									}
									//NRO DOCUMENTO - 20
									if( !FormatoUtil.validaCampoString(detalleBean.getNroDocumento(),20) ){
										cont++;
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3385,i+1));
									}
									//NRO DIAS - 3
									if( !FormatoUtil.validaCampoNumeroEntero(detalleBean.getNroDias(),3) ){
										cont++;
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3387,i+1));
									}
									//ALIMENTACION - 18,2
									if( !FormatoUtil.validaCampoNumeroDecimal(detalleBean.getMontoAlimentacion(),18,2) ){
										cont++;
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3389,i+1));
									}
									//ALOJAMIENTO - 18,2
									if( !FormatoUtil.validaCampoNumeroDecimal(detalleBean.getMontoAlojamiento(),18,2) ){
										cont++;
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3391,i+1));
									}
									//MOVILIDAD - 18,2
									if( !FormatoUtil.validaCampoNumeroDecimal(detalleBean.getMontoMovilidad(),18,2) ){
										cont++;
										sMsgImplementacion = sMsgImplementacion.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3393,i+1));
									}
									
									/***/
									
									if( FiseConstants.BLANCO.equals(sMsgImplementacion.toString()) ){
										listaDetalle.add(detalleBean);
									}
								}

							}
							for (int i = (inicioOperativa+1); i<(finRegistros-1); i++) {
								
								Formato12CCBean detalleBean = new Formato12CCBean();
								
								HSSFRow row= hojaF12.getRow(i);
								
								HSSFCell celdaAnoEjecucion = row.getCell(FiseConstants.NRO_CELDA_ANO_EJECUCION_FORMATO12C);
								HSSFCell celdaMesEjecucion = row.getCell(FiseConstants.NRO_CELDA_MES_EJECUCION_FORMATO12C);
								//HSSFCell celdaItem = row.getCell(FiseConstants.NRO_CELDA_ITEM_FORMATO12C);
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
								
								detalleBean.setEtapa(etapaEdit);
								
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
									sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3409,i+1));
								}
								
								boolean target=false;
								if(  FiseConstants.ZONABENEF_LIMA_COD == detalleBean.getZonaBenef() ){
									if( FiseConstants.COD_EMPRESA_EDELNOR.equals(formulario.getCodigoEmpresa()) || FiseConstants.COD_EMPRESA_LUZ_SUR.equals(formulario.getCodigoEmpresa()) ){
										target=true;
									}
								}else{
									target=true;
								}
								
								if(target){
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
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3395,i+1));
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
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3397,i+1));
									}
									
									/**validacion de periodo de ejecucion*/
									if( detalleBean.getAnioEjecucion()!=null && detalleBean.getMesEjecucion()!=null ){
										if( (detalleBean.getAnioEjecucion()*100+detalleBean.getMesEjecucion())>(detalleBean.getAnioPresentacion()*100+detalleBean.getMesPresentacion()) ){
											cont++;
											sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3631,i+1));
										}
									}
									/***/
									
									//NRO ITEM ETAPA
									/*if( HSSFCell.CELL_TYPE_NUMERIC == celdaItem.getCellType()  ){
										detalleBean.setNroItemEtapa(new Double(celdaItem.getNumericCellValue()).longValue());
									}else if( HSSFCell.CELL_TYPE_BLANK == celdaItem.getCellType()  ){
										detalleBean.setNroItemEtapa(0L);
										cont++;
										//---sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_70));
									}else{
										detalleBean.setNroItemEtapa(0L);
										cont++;
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_80,i+1));
									}*/
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
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3399,i+1));
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
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3402,i+1));
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
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3404,i+1));
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
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3407,i+1));
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
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3412,i+1));
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
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3414,i+1));
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
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3416,i+1));
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
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3419,i+1));
									}
									//SERIE DOCUMENTO
									logger.error("tipo de la celda serie documento mensuales::   "+celdaSerieDocumento.getCellType()); 
									if( HSSFCell.CELL_TYPE_STRING == celdaSerieDocumento.getCellType() ){
										detalleBean.setSerieDocumento(celdaSerieDocumento.toString());
									}else if( HSSFCell.CELL_TYPE_NUMERIC == celdaSerieDocumento.getCellType()  ){
										String valor = "" + celdaSerieDocumento.getNumericCellValue();
										logger.error("valor numeric de la celda serie documento mensuales::   "+valor); 
										detalleBean.setSerieDocumento(FormatoUtil.eliminaDecimales(valor));
									}else if( HSSFCell.CELL_TYPE_BLANK == celdaSerieDocumento.getCellType()  ){
										detalleBean.setSerieDocumento(FiseConstants.BLANCO);
										cont++;
										//---sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_30));
									}else{
										detalleBean.setSerieDocumento(FiseConstants.BLANCO);
										cont++;
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3421,i+1));
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
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3423,i+1));
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
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3425,i+1));
									}
									//ALIMENTACION
									if( HSSFCell.CELL_TYPE_NUMERIC == celdaAlimentacion.getCellType()  ){
										detalleBean.setMontoAlimentacion(new BigDecimal(celdaAlimentacion.getNumericCellValue()).setScale(2, RoundingMode.HALF_UP));
									}else if( HSSFCell.CELL_TYPE_BLANK == celdaAlimentacion.getCellType()  ){
										detalleBean.setMontoAlimentacion(new BigDecimal(0.00));
									}else{
										detalleBean.setMontoAlimentacion(new BigDecimal(0.00));
										cont++;
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3427,i+1));
									}
									//ALOJAMIENTO
									if( HSSFCell.CELL_TYPE_NUMERIC == celdaAlojamiento.getCellType()  ){
										detalleBean.setMontoAlojamiento(new BigDecimal(celdaAlojamiento.getNumericCellValue()).setScale(2, RoundingMode.HALF_UP));
									}else if( HSSFCell.CELL_TYPE_BLANK == celdaAlojamiento.getCellType()  ){
										detalleBean.setMontoAlojamiento(new BigDecimal(0.00));
									}else{
										detalleBean.setMontoAlojamiento(new BigDecimal(0.00));
										cont++;
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3429,i+1));
									}
									//MOVILIDAD
									if( HSSFCell.CELL_TYPE_NUMERIC == celdaMovilidad.getCellType()  ){
										detalleBean.setMontoMovilidad(new BigDecimal(celdaMovilidad.getNumericCellValue()).setScale(2, RoundingMode.HALF_UP));
									}else if( HSSFCell.CELL_TYPE_BLANK == celdaMovilidad.getCellType()  ){
										detalleBean.setMontoMovilidad(new BigDecimal(0.00));
									}else{
										detalleBean.setMontoMovilidad(new BigDecimal(0.00));
										cont++;
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3431,i+1));
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
											sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3400,i+1));
										}
										if( !mapaUbigeo.containsKey(detalleBean.getCodUbigeoDestino()) ){
											cont++;
											sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3405,i+1));
										}
										if( !mapaZonaBenef.containsKey(detalleBean.getZonaBenef()) ){
											cont++;
											sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3410,i+1));
										}
										if( !mapaTipoDocumento.containsKey(detalleBean.getTipoDocumento()) ){
											cont++;
											sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3417,i+1));
										}
										
									}
									
									/**validacion de estructura de campos*/
									//ZONA BENEFICIARIO - 1
									if( !FormatoUtil.validaCampoNumeroEntero(detalleBean.getZonaBenef(),1) ){
										cont++;
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3409,i+1));
									}
									//ANO EJECUCION - 4
									if( !FormatoUtil.validaCampoNumeroEntero(detalleBean.getAnioPresentacion(),4) ){
										cont++;
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3395,i+1));
									}
									//MES EJECUCION - 2
									if( !FormatoUtil.validaCampoNumeroEntero(detalleBean.getMesPresentacion(),2) ){
										cont++;
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3397,i+1));
									}
									//COD UBIGEO ORIGEN - 6
									if( !FormatoUtil.validaCampoString(detalleBean.getCodUbigeoOrigen(),6) ){
										cont++;
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3399,i+1));
									}
									//LOCALIDAD ORIGEN - 50
									if( !FormatoUtil.validaCampoString(detalleBean.getLocalidadOrigen(),50) ){
										cont++;
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3402,i+1));
									}
									//COD UBIGEO DESTINO - 6
									if( !FormatoUtil.validaCampoString(detalleBean.getCodUbigeoDestino(),6) ){
										cont++;
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3404,i+1));
									}
									//LOCALIDAD DESTINO - 50
									if( !FormatoUtil.validaCampoString(detalleBean.getLocalidadDestino(),50) ){
										cont++;
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3407,i+1));
									}
									//CUENTA CONTABLE - 30
									if( !FormatoUtil.validaCampoString(detalleBean.getCodCuentaContable(),30) ){
										cont++;
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3412,i+1));
									}
									//ACTIVIDAD - 250
									if( !FormatoUtil.validaCampoString(detalleBean.getActividad(),250) ){
										cont++;
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3414,i+1));
									}
									//TIPO DOCUMENTO - 3
									if( !FormatoUtil.validaCampoString(detalleBean.getTipoDocumento(),3) ){
										cont++;
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3416,i+1));
									}
									//RUC - 11
									if( !FormatoUtil.validaCampoString(detalleBean.getRucEmpresa(),11) ){
										cont++;
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3419,i+1));
									}
									//SERIE DOCUMENTO - 6
									if( !FormatoUtil.validaCampoString(detalleBean.getSerieDocumento(),6) ){
										cont++;
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3421,i+1));
									}
									//NRO DOCUMENTO - 20
									if( !FormatoUtil.validaCampoString(detalleBean.getNroDocumento(),20) ){
										cont++;
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3423,i+1));
									}
									//NRO DIAS - 3
									if( !FormatoUtil.validaCampoNumeroEntero(detalleBean.getNroDias(),3) ){
										cont++;
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3425,i+1));
									}
									//ALIMENTACION - 18,2
									if( !FormatoUtil.validaCampoNumeroDecimal(detalleBean.getMontoAlimentacion(),18,2) ){
										cont++;
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3427,i+1));
									}
									//ALOJAMIENTO - 18,2
									if( !FormatoUtil.validaCampoNumeroDecimal(detalleBean.getMontoAlojamiento(),18,2) ){
										cont++;
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3429,i+1));
									}
									//MOVILIDAD - 18,2
									if( !FormatoUtil.validaCampoNumeroDecimal(detalleBean.getMontoMovilidad(),18,2) ){
										cont++;
										sMsgOperativa = sMsgOperativa.append(fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3431,i+1));
									}
									
									/***/
									
									if( FiseConstants.BLANCO.equals(sMsgOperativa.toString()) ){
										listaDetalle.add(detalleBean);
									}
								}
								
							}
						}
						
						/*boolean p = false;
						if(p){*/
						
						if( FiseConstants.BLANCO.equals(sMsg) &&
								FiseConstants.BLANCO.equals(sMsgImplementacion.toString()) &&
								FiseConstants.BLANCO.equals(sMsgOperativa.toString())
								){
							
							if( codEmpresa.trim().equals(formulario.getCodigoEmpresa().trim()) &&
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
									id.setEtapa(formulario.getEtapa());
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
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3433);
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
			if( FiseConstants.BLANCO.equals(error.trim()) ){
				error = mapaErrores.get(FiseConstants.COD_ERROR_3633);
			}
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
	
	public Formato12CMensajeBean readTxtFile(FileEntry archivo,UploadPortletRequest uploadPortletRequest, User user, String tipoOperacion, String codEmpresa, 	String anioPres, String mesPres, String etapaEdit) {
		
		Formato12CMensajeBean formatoMensaje = new Formato12CMensajeBean();
		InputStream is=null;
		FiseFormato12CC objeto = null;
		List<MensajeErrorBean> listaError = new ArrayList<MensajeErrorBean>();
		String sMsg = "";
		
		//StringBuilder sMsgImplementacion = new StringBuilder(FiseConstants.BLANCO);
		//StringBuilder sMsgOperativa = new StringBuilder(FiseConstants.BLANCO);
		StringBuilder sMsg12C = new StringBuilder(FiseConstants.BLANCO);
		
		int cont = 0;
		List<CfgCampo> listaCampo = null;
		try{
			CfgTabla tabla = new CfgTabla();
			tabla.setIdTabla(FiseConstants.ID_TABLA_FORMATO12C);
			listaCampo = campoService.listarCamposByTabla(tabla);
			if( listaCampo != null ){
				int longitudMaxima = campoService.longitudMaximaRegistro(listaCampo);
				
				int posicionCodEmpresa = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_COD_EMPRESA);
				int posicionAnioPresentacion = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_ANO_PRESENTACION);
				int posicionMesPresentacion = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_MES_PRESENTACION);
				
				int posicionAnioEjecucion = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_ANO_EJECUCION_GASTO_F12C);
				int posicionMesEjecucion = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_MES_EJECUCION_GASTO_F12C);
				int posicionEtapaEjecucion = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_ETAPA_EJECUCION_F12C);
				int posicionItem = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_NUMERO_ITEM_ETAPA_F12C);
				int posicionCodUbigeoOrigen = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_ID_UBIGEO_ORIGEN_F12C);
				int posicionLocalidadOrigen = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_DESCRIPCION_LOCALIDAD_ORIGEN_F12C);
				int posicionCodUbigeoDestino = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_ID_UBIGEO_DESTINO_F12C);
				int posicionLocalidadDestino = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_DESCRIPCION_LOCALIDAD_DESTINO_F12C);
				int posicionZonaBenef = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_ID_ZONA_BENEF_F12C);
				int posicionCtaContable = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_CODIGO_CUENTA_CONTA_EDE_F12C);
				int posicionActividad = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_DESCRIPCION_ACTIVIDAD_F12C);
				int posicionTipoDocReferencia = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_ID_TIP_DOC_REF_F12C);
				int posicionRUCEmpresa = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_RUC_EMPRESA_EMITE_DOC_REF_F12C);
				int posicionSerieDocumento = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_SERIE_DOCUMENTO_REFERENCIA_F12C);
				int posicionNroDocumento = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_NUMERO_DOCUMENTO_REFERENCIA_F12C);
				int posicionNroDias = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_NUMERO_DIAS_F12C);
				int posicionAlimentacion = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_MONTO_ALIMENTACION_F12C);
				int posicionAlojamiento = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_MONTO_ALOJAMIENTO_F12C);
				int posicionMovilidad = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_MONTO_MOVILIDAD_F12C);
				//int posicionTotalDesplazamiento = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_TOTAL_DESPLAZAMIENTO_REALIZADO_F12C);
				
				
				String sCurrentLine;
				is=uploadPortletRequest.getFileAsStream("archivoTxt");
				
				String nombreIdeal = FormatoUtil.nombreArchivoCargaTxt(Long.parseLong(anioPres), Long.parseLong(mesPres), codEmpresa, FiseConstants.TIPO_FORMATO_12C);
				if( nombreIdeal.trim().equals(archivo.getDescription().trim()) ){
					
					int BUFFER_SIZE = 8192;
					BufferedReader br = new BufferedReader( new InputStreamReader(is),BUFFER_SIZE);
					List<String> listaDetalleTxt= new ArrayList<String>();
					sCurrentLine = br.readLine();
					while(sCurrentLine!=null){
						cont++;
						if( sCurrentLine.length()>0 ){
							if( sCurrentLine.length() == longitudMaxima ){
								listaDetalleTxt.add(sCurrentLine);
							}else{
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3434, cont);
							}
						}/*else{
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3435);
						}*/
						sCurrentLine = br.readLine();
						/*if( cont>3 ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_240);
							break;
						}*/
					}
					if(cont==0){
						cont++;
						sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3435);
					}
					
					String key1,key2,key3,key4,key5="";//,key6="";
					if( listaDetalleTxt.size()>0 ){
						key1 = listaDetalleTxt.get(0).substring(0, posicionCodEmpresa).trim();
						key2 = listaDetalleTxt.get(0).substring(posicionCodEmpresa, posicionAnioPresentacion).trim();
						key3 = listaDetalleTxt.get(0).substring(posicionAnioPresentacion, posicionMesPresentacion).trim();
						key4 = listaDetalleTxt.get(0).substring(posicionMesPresentacion, posicionAnioEjecucion).trim();
						key5 = listaDetalleTxt.get(0).substring(posicionAnioEjecucion, posicionMesEjecucion).trim();
						boolean process = true;
						/*Set<String> etapaSet = new java.util.HashSet<String>();
						for (String s : listaDetalleTxt) {
							String codEmp = s.substring(0, posicionCodEmpresa).trim();
							String anioP = s.substring(posicionCodEmpresa, posicionAnioPresentacion).trim();
							String mesP = s.substring(posicionAnioPresentacion, posicionMesPresentacion) ;
							String anioE = s.substring(posicionMesPresentacion, posicionAnioEjecucion).trim();
							String mesE = s.substring(posicionAnioEjecucion, posicionMesEjecucion).trim();
							String etapaEjec = s.substring(posicionMesEjecucion, posicionEtapaEjecucion).trim();
							//--String nroItem = s.substring(posicionEtapaEjecucion, posicionItem).trim();
							
							if( key1.equals(codEmp) && key2.equals(anioP) && key3.equals(mesP) && key4.equals(anioE) && key5.equals(mesE) &&
									(FiseConstants.ETAPA_EJECUCION_IMPLEMENTACION_COD_STRING.equals(etapaEjec) ||
									FiseConstants.ETAPA_EJECUCION_OPERATIVA_COD_STRING.equals(etapaEjec)  )
									){
								if( etapaSet.contains(etapaEjec) ){
									process=false;
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_280);
									break;
								}else{
									etapaSet.add(etapaEjec);
									process=true;
								}
							}else{
								process=false;
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_260);
								break;
							}
						}*/
						if(process){
							
							List<Formato12CCBean> listaDetalle = new ArrayList<Formato12CCBean>();
							
							Formato12CCBean formulario = new Formato12CCBean();
							//nuevamente recorremos la lista para armar los objetos
							formulario.setCodigoEmpresa(key1);
							formulario.setAnioPresentacion(Long.parseLong(key2));
							formulario.setMesPresentacion(Long.parseLong(key3));
							formulario.setAnioEjecucion(Long.parseLong(key4));
							formulario.setMesEjecucion(Long.parseLong(key5));
							
							formulario.setEtapa(etapaEdit);

							if( codEmpresa.equals(formulario.getCodigoEmpresa()) &&
									anioPres.equals(String.valueOf(formulario.getAnioPresentacion())) &&
									Long.parseLong(mesPres)==formulario.getMesPresentacion() 
									//anioEjec.equals(String.valueOf(formulario.getAnioPresent())) &&
									//anioFinVigEdit.equals(String.valueOf(formulario.getAnioPresent())) 
									){
								
								//
								for (String s : listaDetalleTxt) {
									
									Formato12CCBean detalleBean = new Formato12CCBean();
									
									detalleBean.setCodigoEmpresa(formulario.getCodigoEmpresa());
									detalleBean.setAnioPresentacion(formulario.getAnioPresentacion());
									detalleBean.setMesPresentacion(formulario.getMesPresentacion());
									
									//detalleBean.setEtapaEjecucion(FiseConstants.ETAPA_EJECUCION_OPERATIVA_COD);
									
									detalleBean.setUsuario(user.getLogin());
									detalleBean.setTerminal(user.getLoginIP());
									detalleBean.setTipoArchivo(FiseConstants.TIPOARCHIVO_TXT);
									detalleBean.setNombreArchivo(archivo.getTitle());
									
									detalleBean.setEtapa(etapaEdit);
									
									String etapaEjecucion = s.substring(posicionMesEjecucion, posicionEtapaEjecucion).trim();
									
									String anioEjecucion = s.substring(posicionMesPresentacion, posicionAnioEjecucion).trim();
									String mesEjecucion = s.substring(posicionAnioEjecucion, posicionMesEjecucion).trim();
									String codUbigeoOrigen = s.substring(posicionItem, posicionCodUbigeoOrigen).trim();
									String localidadOrigen = s.substring(posicionCodUbigeoOrigen, posicionLocalidadOrigen).trim();
									String codUbigeoDestino = s.substring(posicionLocalidadOrigen, posicionCodUbigeoDestino).trim();
									String localidadDestino = s.substring(posicionCodUbigeoDestino, posicionLocalidadDestino).trim();
									String zonaBenef = s.substring(posicionLocalidadDestino, posicionZonaBenef).trim();
									String ctaContable = s.substring(posicionZonaBenef, posicionCtaContable).trim();
									String actividad = s.substring(posicionCtaContable, posicionActividad).trim();
									String tipoDocReferencia = s.substring(posicionActividad, posicionTipoDocReferencia).trim();
									String rucEmpresa = s.substring(posicionTipoDocReferencia, posicionRUCEmpresa).trim();
									String serieDocumento = s.substring(posicionRUCEmpresa, posicionSerieDocumento).trim();
									String nroDocumento = s.substring(posicionSerieDocumento, posicionNroDocumento).trim();
									String nroDias = s.substring(posicionNroDocumento, posicionNroDias).trim();
									String alimentacion = s.substring(posicionNroDias, posicionAlimentacion).trim();
									String alojamiento = s.substring(posicionAlimentacion, posicionAlojamiento).trim();
									String movilidad = s.substring(posicionAlojamiento, posicionMovilidad).trim();
									
									//validaciones de consistencia
									/*if( !FormatoUtil.validarCampoLongTxt(etapaEjecucion) ){
										//el campo etapaEjecucion no corresponde al tipo de dato correcto
										cont++;
										sMsg12C = sMsg12C.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3357));
									}*/
									if( !FormatoUtil.validarCampoLongEnteroPositivoTxt(anioEjecucion) ){
										//el campo anioEjecucion no corresponde al tipo de dato correcto
										cont++;
										sMsg12C = sMsg12C.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3440));
									}
									if( !FormatoUtil.validarCampoLongEnteroPositivoTxt(mesEjecucion) ){
										//el campo mesEjecucion no corresponde al tipo de dato correcto
										cont++;
										sMsg12C = sMsg12C.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3442));
									}
									
									/**validacion de periodo de ejecucion*/
									if( FormatoUtil.validarCampoLongEnteroPositivoTxt(anioEjecucion) && FormatoUtil.validarCampoLongEnteroPositivoTxt(mesEjecucion) ){
										if( (Long.parseLong(anioEjecucion)*100+Long.parseLong(mesEjecucion))>(detalleBean.getAnioPresentacion()*100+detalleBean.getMesPresentacion()) ){
											cont++;
											sMsg12C = sMsg12C.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3631));
										}
									}
									/***/
									
									
									
									if( !FormatoUtil.validarCampoLongEnteroPositivoTxt(zonaBenef) ){
										//el campo Zona benef no corresponde al tipo de dato correcto
										cont++;
										sMsg12C = sMsg12C.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3454));
									}
									if( !FormatoUtil.validarCampoLongEnteroPositivoTxt(nroDias) ){
										//el campo nroDias no corresponde al tipo de dato correcto
										cont++;
										sMsg12C = sMsg12C.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3470));
									}
									if( !FormatoUtil.validarCampoBigDecimalPositivoTxt(alimentacion) ){
										//el campo alimentacion no corresponde al tipo de dato correcto
										cont++;
										sMsg12C = sMsg12C.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3472));
									}
									if( !FormatoUtil.validarCampoBigDecimalPositivoTxt(alojamiento) ){
										//el campo alojamiento no corresponde al tipo de dato correcto
										cont++;
										sMsg12C = sMsg12C.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3474));
									}
									if( !FormatoUtil.validarCampoBigDecimalPositivoTxt(movilidad) ){
										//el campo movilidad no corresponde al tipo de dato correcto
										cont++;
										sMsg12C = sMsg12C.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3476));
									}
									
									if( ( !FiseConstants.BLANCO.equals(anioEjecucion.trim()) && !FiseConstants.CERO.equals(anioEjecucion.trim()) ) ||
											( !FiseConstants.BLANCO.equals(mesEjecucion.trim()) && !FiseConstants.CERO.equals(mesEjecucion.trim()) ) ||
											( !FiseConstants.BLANCO.equals(etapaEjecucion.trim()) && !FiseConstants.CERO.equals(etapaEjecucion.trim()) ) ||
											( !FiseConstants.BLANCO.equals(codUbigeoOrigen.trim())  ) ||
											( !FiseConstants.BLANCO.equals(localidadOrigen.trim()) ) ||
											( !FiseConstants.BLANCO.equals(codUbigeoDestino.trim()) ) ||
											( !FiseConstants.BLANCO.equals(localidadDestino.trim()) ) ||
											( !FiseConstants.BLANCO.equals(zonaBenef.trim()) && !FiseConstants.CERO.equals(zonaBenef.trim()) ) ||
											( !FiseConstants.BLANCO.equals(ctaContable.trim()) ) ||
											( !FiseConstants.BLANCO.equals(actividad.trim()) ) ||
											( !FiseConstants.BLANCO.equals(tipoDocReferencia.trim()) ) ||
											( !FiseConstants.BLANCO.equals(rucEmpresa.trim()) ) ||
											( !FiseConstants.BLANCO.equals(serieDocumento.trim()) ) ||
											( !FiseConstants.BLANCO.equals(nroDocumento.trim()) ) ||
											( !FiseConstants.BLANCO.equals(nroDias.trim()) && !FiseConstants.CERO.equals(nroDias.trim()) ) ||
											( !FiseConstants.BLANCO.equals(alimentacion.trim()) && !FiseConstants.CERO.equals(alimentacion.trim()) ) ||
											( !FiseConstants.BLANCO.equals(alojamiento.trim()) && !FiseConstants.CERO.equals(alojamiento.trim()) ) ||
											( !FiseConstants.BLANCO.equals(movilidad.trim()) && !FiseConstants.CERO.equals(movilidad.trim()) ) 
											
											){
									
										if( !mapaUbigeo.containsKey(codUbigeoOrigen) ){
											cont++;
											sMsg12C = sMsg12C.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3445));
										}
										if( !mapaUbigeo.containsKey(codUbigeoDestino) ){
											cont++;
											sMsg12C = sMsg12C.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3450));
										}
										if( !mapaZonaBenef.containsKey(Long.parseLong(zonaBenef)) ){
											cont++;
											sMsg12C = sMsg12C.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3455));
										}
										if( !mapaTipoDocumento.containsKey(tipoDocReferencia) ){
											cont++;
											sMsg12C = sMsg12C.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3462));
										}
										
									}
									
									if( FiseConstants.BLANCO.equals(sMsg) &&
											FiseConstants.BLANCO.equals(sMsg12C.toString()) 
											){
										
										if( FiseConstants.ZONABENEF_RURAL_COD == Long.parseLong(zonaBenef) ){
											
											detalleBean.setEtapaEjecucion(Long.parseLong(etapaEjecucion));
											detalleBean.setAnioEjecucion(Long.parseLong(anioEjecucion));
											detalleBean.setMesEjecucion(Long.parseLong(mesEjecucion));
											detalleBean.setCodUbigeoOrigen(codUbigeoOrigen);
											detalleBean.setLocalidadOrigen(localidadOrigen);
											detalleBean.setCodUbigeoDestino(codUbigeoDestino);
											detalleBean.setLocalidadDestino(localidadDestino);
											detalleBean.setZonaBenef(Long.parseLong(zonaBenef));
											detalleBean.setCodCuentaContable(ctaContable);
											detalleBean.setActividad(actividad);
											detalleBean.setTipoDocumento(tipoDocReferencia);
											detalleBean.setRucEmpresa(rucEmpresa);
											detalleBean.setSerieDocumento(serieDocumento);
											detalleBean.setNroDocumento(nroDocumento);
											detalleBean.setNroDias(Long.parseLong(nroDias));
											detalleBean.setMontoAlimentacion(new BigDecimal(alimentacion));
											detalleBean.setMontoAlojamiento(new BigDecimal(alojamiento));
											detalleBean.setMontoMovilidad(new BigDecimal(movilidad));
											
										}else if( FiseConstants.ZONABENEF_PROVINCIA_COD == Long.parseLong(zonaBenef) ){
											detalleBean.setEtapaEjecucion(Long.parseLong(etapaEjecucion));
											detalleBean.setAnioEjecucion(Long.parseLong(anioEjecucion));
											detalleBean.setMesEjecucion(Long.parseLong(mesEjecucion));
											detalleBean.setCodUbigeoOrigen(codUbigeoOrigen);
											detalleBean.setLocalidadOrigen(localidadOrigen);
											detalleBean.setCodUbigeoDestino(codUbigeoDestino);
											detalleBean.setLocalidadDestino(localidadDestino);
											detalleBean.setZonaBenef(Long.parseLong(zonaBenef));
											detalleBean.setCodCuentaContable(ctaContable);
											detalleBean.setActividad(actividad);
											detalleBean.setTipoDocumento(tipoDocReferencia);
											detalleBean.setRucEmpresa(rucEmpresa);
											detalleBean.setSerieDocumento(serieDocumento);
											detalleBean.setNroDocumento(nroDocumento);
											detalleBean.setNroDias(Long.parseLong(nroDias));
											detalleBean.setMontoAlimentacion(new BigDecimal(alimentacion));
											detalleBean.setMontoAlojamiento(new BigDecimal(alojamiento));
											detalleBean.setMontoMovilidad(new BigDecimal(movilidad));
											
										}else if( FiseConstants.ZONABENEF_LIMA_COD == Long.parseLong(zonaBenef) ){
											if( FiseConstants.COD_EMPRESA_EDELNOR.equals(formulario.getCodigoEmpresa()) || FiseConstants.COD_EMPRESA_LUZ_SUR.equals(formulario.getCodigoEmpresa()) ){
												detalleBean.setEtapaEjecucion(Long.parseLong(etapaEjecucion));
												detalleBean.setAnioEjecucion(Long.parseLong(anioEjecucion));
												detalleBean.setMesEjecucion(Long.parseLong(mesEjecucion));
												detalleBean.setCodUbigeoOrigen(codUbigeoOrigen);
												detalleBean.setLocalidadOrigen(localidadOrigen);
												detalleBean.setCodUbigeoDestino(codUbigeoDestino);
												detalleBean.setLocalidadDestino(localidadDestino);
												detalleBean.setZonaBenef(Long.parseLong(zonaBenef));
												detalleBean.setCodCuentaContable(ctaContable);
												detalleBean.setActividad(actividad);
												detalleBean.setTipoDocumento(tipoDocReferencia);
												detalleBean.setRucEmpresa(rucEmpresa);
												detalleBean.setSerieDocumento(serieDocumento);
												detalleBean.setNroDocumento(nroDocumento);
												detalleBean.setNroDias(Long.parseLong(nroDias));
												detalleBean.setMontoAlimentacion(new BigDecimal(alimentacion));
												detalleBean.setMontoAlojamiento(new BigDecimal(alojamiento));
												detalleBean.setMontoMovilidad(new BigDecimal(movilidad));
											}else{
												detalleBean.setEtapaEjecucion(0L);
												detalleBean.setAnioEjecucion(0L);
												detalleBean.setMesEjecucion(0L);
												detalleBean.setCodUbigeoOrigen("");
												detalleBean.setLocalidadOrigen("");
												detalleBean.setCodUbigeoDestino("");
												detalleBean.setLocalidadDestino("");
												detalleBean.setZonaBenef(0L);
												detalleBean.setCodCuentaContable("");
												detalleBean.setActividad("");
												detalleBean.setTipoDocumento("");
												detalleBean.setRucEmpresa("");
												detalleBean.setSerieDocumento("");
												detalleBean.setNroDocumento("");
												detalleBean.setNroDias(0L);
												detalleBean.setMontoAlimentacion(new BigDecimal(0));
												detalleBean.setMontoAlojamiento(new BigDecimal(0));
												detalleBean.setMontoMovilidad(new BigDecimal(0));
											}
											
											
											
										}
										
										listaDetalle.add(detalleBean);
										
									}
									
									/**validacion de estructura de campos*/
									//ZONA BENEFICIARIO - 1
									if( !FormatoUtil.validaCampoNumeroEntero(detalleBean.getZonaBenef(),1) ){
										cont++;
										sMsg12C = sMsg12C.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3454));
									}
									//ANO EJECUCION - 4
									if( !FormatoUtil.validaCampoNumeroEntero(detalleBean.getAnioPresentacion(),4) ){
										cont++;
										sMsg12C = sMsg12C.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3440));
									}
									//MES EJECUCION - 2
									if( !FormatoUtil.validaCampoNumeroEntero(detalleBean.getMesPresentacion(),2) ){
										cont++;
										sMsg12C = sMsg12C.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3442));
									}
									//COD UBIGEO ORIGEN - 6
									if( !FormatoUtil.validaCampoString(detalleBean.getCodUbigeoOrigen(),6) ){
										cont++;
										sMsg12C = sMsg12C.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3444));
									}
									//LOCALIDAD ORIGEN - 50
									if( !FormatoUtil.validaCampoString(detalleBean.getLocalidadOrigen(),50) ){
										cont++;
										sMsg12C = sMsg12C.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3447));
									}
									//COD UBIGEO DESTINO - 6
									if( !FormatoUtil.validaCampoString(detalleBean.getCodUbigeoDestino(),6) ){
										cont++;
										sMsg12C = sMsg12C.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3449));
									}
									//LOCALIDAD DESTINO - 50
									if( !FormatoUtil.validaCampoString(detalleBean.getLocalidadDestino(),50) ){
										cont++;
										sMsg12C = sMsg12C.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3452));
									}
									//CUENTA CONTABLE - 30
									if( !FormatoUtil.validaCampoString(detalleBean.getCodCuentaContable(),30) ){
										cont++;
										sMsg12C = sMsg12C.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3457));
									}
									//ACTIVIDAD - 250
									if( !FormatoUtil.validaCampoString(detalleBean.getActividad(),250) ){
										cont++;
										sMsg12C = sMsg12C.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3459));
									}
									//TIPO DOCUMENTO - 3
									if( !FormatoUtil.validaCampoString(detalleBean.getTipoDocumento(),3) ){
										cont++;
										sMsg12C = sMsg12C.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3461));
									}
									//RUC - 11
									if( !FormatoUtil.validaCampoString(detalleBean.getRucEmpresa(),11) ){
										cont++;
										sMsg12C = sMsg12C.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3464));
									}
									//SERIE DOCUMENTO - 6
									if( !FormatoUtil.validaCampoString(detalleBean.getSerieDocumento(),6) ){
										cont++;
										sMsg12C = sMsg12C.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3466));
									}
									//NRO DOCUMENTO - 20
									if( !FormatoUtil.validaCampoString(detalleBean.getNroDocumento(),20) ){
										cont++;
										sMsg12C = sMsg12C.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3468));
									}
									//NRO DIAS - 3
									if( !FormatoUtil.validaCampoNumeroEntero(detalleBean.getNroDias(),3) ){
										cont++;
										sMsg12C = sMsg12C.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3470));
									}
									//ALIMENTACION - 18,2
									if( !FormatoUtil.validaCampoNumeroDecimal(detalleBean.getMontoAlimentacion(),18,2) ){
										cont++;
										sMsg12C = sMsg12C.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3472));
									}
									//ALOJAMIENTO - 18,2
									if( !FormatoUtil.validaCampoNumeroDecimal(detalleBean.getMontoAlojamiento(),18,2) ){
										cont++;
										sMsg12C = sMsg12C.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3474));
									}
									//MOVILIDAD - 18,2
									if( !FormatoUtil.validaCampoNumeroDecimal(detalleBean.getMontoMovilidad(),18,2) ){
										cont++;
										sMsg12C = sMsg12C.append(fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3476));
									}
								}
								
								
								if( FiseConstants.BLANCO.equals(sMsg) &&
										FiseConstants.BLANCO.equals(sMsg12C.toString()) 
										){
									
									
									/*if( codEmpresa.equals(formulario.getCodigoEmpresa()) &&
											anioPres.equals(String.valueOf(formulario.getAnioPresentacion())) &&
											Long.parseLong(mesPres) == formulario.getMesPresentacion() 
											){*/
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
											id.setEtapa(formulario.getEtapa());
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
									/*}else{
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_210);
									}*/
									
									
								}
								
							}else{
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12C_3439);
							}
							
						}
					}else{
						cont++;
						sMsg = sMsg + "El archivo cargado debe contener información para el Formato 12C ";
						throw new Exception("El archivo cargado debe contener información para el Formato 12C ");
					}
					is.close();
					
				}else{
					cont++;
					sMsg = sMsg + "El nombre del archivo debe corresponder al periodo a declarar ";
					throw new Exception("El nombre del archivo debe corresponder al periodo a declarar ");
				}
				
				
			}else{
				throw new Exception(mapaErrores.get(FiseConstants.COD_ERROR_3667));
			}
			
		}catch (Exception e) {			   
			  //refer.setCondicion(false);				  
			String error = e.getMessage();
			if( FiseConstants.BLANCO.equals(error.trim()) ){
				error = mapaErrores.get(FiseConstants.COD_ERROR_3633);
			}
			sMsg = sMsg+error;	        	
			System.out.println(error);
			cont++;
			MensajeErrorBean errorBean = new MensajeErrorBean();
			errorBean.setId(cont);
			errorBean.setDescripcion(error);
			listaError.add(errorBean);
			  //throw new Exception(error); 
			  			   
		  }
		//pRequest.getPortletSession().setAttribute("MensajeInformacion", sMsg, PortletSession.APPLICATION_SCOPE);
		formatoMensaje.setFiseFormato12CC(objeto);
		formatoMensaje.setMensajeError(sMsg);
		if(listaError.size()>0)
			formatoMensaje.setListaMensajeError(listaError);
		
		return formatoMensaje;
	}

}
