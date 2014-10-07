package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.Formato12ACBean;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.AdmEmpresa;
import gob.osinergmin.fise.domain.FiseFormato12AC;
import gob.osinergmin.fise.domain.FiseFormato12ACPK;
import gob.osinergmin.fise.domain.FiseFormato14AD;
import gob.osinergmin.fise.domain.FiseZonaBenef;
import gob.osinergmin.fise.gart.json.Formato12AGartJSON;
import gob.osinergmin.fise.gart.service.AdmEmpresaGartService;
import gob.osinergmin.fise.gart.service.FiseZonaBenefGartService;
import gob.osinergmin.fise.gart.service.Formato12AGartService;
import gob.osinergmin.fise.gart.service.Formato14AGartService;
import gob.osinergmin.fise.util.FechaUtil;
import gob.osinergmin.fise.util.FormatoUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.bookmarks.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.FileMimeTypeException;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;

@Controller("formato12AGartController")
@RequestMapping("VIEW")
public class Formato12AGartController {
	
	Log logger=LogFactoryUtil.getLog(Formato12AGartController.class);

	@Autowired
	ServletContext context;
	
	@Autowired
	@Qualifier("formato12AGartServiceImpl")
	Formato12AGartService formatoService;
	@Autowired
	@Qualifier("admEmpresaGartServiceImpl")
	AdmEmpresaGartService admEmpresaService;
	@Autowired
	@Qualifier("fiseZonaBenefGartServiceImpl")
	FiseZonaBenefGartService zonaBenefService;
	
	@Autowired
	@Qualifier("formato14AGartServiceImpl")
	Formato14AGartService formato14Service;
	
	//@Autowired
	List<FiseFormato12AC> listaFormato;
	private Map<Long,String> listaMes;
	private List<AdmEmpresa> listaEmpresa;
	private List<FiseZonaBenef> listaZonaBenef;
	
	private Map<String, String> mapaEmpresa;
	
	@RequestMapping
	public  String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse){
		logger.info("--- defaultView");
		PortletRequest pRequest = (PortletRequest)renderRequest.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
		Formato12AGartJSON obj = new Formato12AGartJSON();
		
		String codEmpresa = (String)pRequest.getPortletSession().getAttribute("codEmpresa", PortletSession.APPLICATION_SCOPE);
		String anioPresentacion = (String)pRequest.getPortletSession().getAttribute("anoPresentacion", PortletSession.APPLICATION_SCOPE);
		String mesPresentacion = (String)pRequest.getPortletSession().getAttribute("mesPresentacion", PortletSession.APPLICATION_SCOPE);
		String anioEjecucion = (String)pRequest.getPortletSession().getAttribute("anoEjecucion", PortletSession.APPLICATION_SCOPE);
		String mesEjecucion = (String)pRequest.getPortletSession().getAttribute("mesEjecucion", PortletSession.APPLICATION_SCOPE);
		String etapa = (String)pRequest.getPortletSession().getAttribute("etapa", PortletSession.APPLICATION_SCOPE);
		//
		
		obj.setCodEmpresa(codEmpresa!=null?codEmpresa:"");
		obj.setAnoPres(anioPresentacion!=null?anioPresentacion:"");
		obj.setMesPres(anioPresentacion!=null?mesPresentacion:"");
		obj.setAnoEjec(anioPresentacion!=null?anioEjecucion:"");
		obj.setMesEjec(anioPresentacion!=null?mesEjecucion:"");
		obj.setEtapa(etapa!=null?etapa:"");
		
		
		pRequest.getPortletSession().setAttribute("codEmpresa", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("anoPresentacion", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("mesPresentacion", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("anoEjecucion", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("mesEjecucion", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("etapa", "", PortletSession.APPLICATION_SCOPE);
		
		cargaInicial();
		model.addAttribute("listaMes", listaMes);
		model.addAttribute("listaEmpresa", listaEmpresa);
		model.addAttribute("listaZonaBenef", listaZonaBenef);
		//cargamos valores por defecto para el formulario de busqueda
		String anioDesde = FechaUtil.obtenerNroAnioFechaActual();
		String mesDesde = String.valueOf(Integer.parseInt(FechaUtil.obtenerNroMesFechaActual())-1);
		String anioHasta = FechaUtil.obtenerNroAnioFechaActual();
		String mesHasta = FechaUtil.obtenerNroMesFechaActual();
		
		obj.setAnioDesde(anioDesde!=null?anioDesde:"");
		obj.setMesDesde(mesDesde!=null?mesDesde:"");
		obj.setAnioHasta(anioHasta!=null?anioHasta:"");
		obj.setMesHasta(mesHasta!=null?mesHasta:"");
		obj.setCodEtapa(FiseConstants.ETAPA_SOLICITUD);
		
		
		
		//model.put("model", obj);
		model.addAttribute("model", obj);
		
		/*model.addAttribute("anioDesde", FechaUtil.obtenerNroAnioFechaActual());
		model.addAttribute("mesDesde", String.valueOf(Integer.parseInt(FechaUtil.obtenerNroMesFechaActual())-1));
		model.addAttribute("anioHasta", FechaUtil.obtenerNroAnioFechaActual());
		model.addAttribute("mesHasta", FechaUtil.obtenerNroMesFechaActual());*/
		
		return "formato12A";
	}
	
	public void cargaInicial(){
		listaMes = new HashMap<Long, String>();
		listaEmpresa = new ArrayList<AdmEmpresa>();
		listaZonaBenef = new ArrayList<FiseZonaBenef>();
		
		listaMes = FechaUtil.cargarMapaMeses();
		logger.info("lista mes"+listaMes);
		listaEmpresa = admEmpresaService.listarAdmEmpresa();
		logger.info("lista empresa"+listaEmpresa);
		listaZonaBenef = zonaBenefService.listarFiseZonaBenef();
		logger.info("lista fise"+listaZonaBenef);
		
		mapaEmpresa = new HashMap<String, String>();
		for (AdmEmpresa admEmpresa : listaEmpresa) {
			logger.info("codEmpresa: "+admEmpresa.getCodEmpresa()+" desccortaempresa: "+admEmpresa.getDscCortaEmpresa());
			mapaEmpresa.put(admEmpresa.getCodEmpresa(), admEmpresa.getDscCortaEmpresa());
		}

	}
	
	@ResourceMapping("grid")
  	public void grid(SessionStatus status, ResourceRequest request,ResourceResponse response) {
  		try {
  			response.setContentType("application/json");	
  			HttpServletRequest req = PortalUtil.getHttpServletRequest(request);	        
		    //HttpSession session = req.getSession();
		    
		    JSONArray jsonArray = new JSONArray();
  			//Map<String,Object> parametros = new HashMap<String,Object>();			   	   
  			String codEmpresa = request.getParameter("s_empresa_b");
  			String anioDesde = request.getParameter("i_anio_d");
  			String mesDesde = request.getParameter("s_mes_d");
  			String anioHasta = request.getParameter("i_anio_h");
  			String mesHasta = request.getParameter("s_mes_h");
  			String etapa = request.getParameter("s_etapa");
  			//
  			String flagCarga = request.getParameter("flagCarga");
  			
  			logger.info("valores "+ codEmpresa);
  			logger.info("valores "+ anioDesde);
  			logger.info("valores "+ mesDesde);
  			logger.info("valores "+ anioHasta);
  			logger.info("valores "+ mesHasta);
  			logger.info("valores "+ etapa);
  			
  			listaFormato = formatoService.buscarFormato12AC(
  					codEmpresa!=""?FormatoUtil.rellenaDerecha(codEmpresa, ' ', 4):"", 
  					anioDesde!=""?Long.parseLong(anioDesde):0, 
  					mesDesde!=""?Long.parseLong(mesDesde):0, 
  					anioHasta!=""?Long.parseLong(anioHasta):0, 
  					mesHasta!=""?Long.parseLong(mesHasta):0, 
  					etapa);
  			logger.info("arreglo lista:"+listaFormato);
  			for(FiseFormato12AC fiseFormato12AC : listaFormato){
  				//seteamos la descripcion de la empresa
  				logger.info("empresa "+mapaEmpresa.get(fiseFormato12AC.getId().getCodEmpresa()));
  				fiseFormato12AC.setDescEmpresa(mapaEmpresa.get(fiseFormato12AC.getId().getCodEmpresa()));
  				fiseFormato12AC.setDescMesPresentacion(listaMes.get(fiseFormato12AC.getId().getMesPresentacion()));
  				fiseFormato12AC.setDescMesEjecucion(listaMes.get(fiseFormato12AC.getId().getMesEjecucionGasto()));
  				jsonArray.put(new Formato12AGartJSON().asJSONObject(fiseFormato12AC));
  			}
  			logger.info("arreglo json:"+jsonArray);
  			PrintWriter pw = response.getWriter();
  			pw.write(jsonArray.toString());
  			pw.flush();
  			pw.close();
  			   
  		} catch (Exception e) {
  			e.printStackTrace();
  			logger.error(e.getMessage());
  			// TODO: handle exception
  		}
  	}
	
	@ResourceMapping("crud")
	public void crud(SessionStatus status, ResourceRequest request,ResourceResponse response) {
 	
		try {
			JSONObject jsonObj = new JSONObject();
			String tipo = request.getParameter("tipo");   		 
			if(tipo.equals(FiseConstants.COD_GET)){//ver editar
				
				FiseFormato12AC formato = new FiseFormato12AC();
				FiseFormato12ACPK pk = new FiseFormato12ACPK();
				String codEmpresa = request.getParameter("codEmpresa");
				String anoPresentacion = request.getParameter("anoPresentacion");
				String mesPresentacion = request.getParameter("mesPresentacion");
				String anoEjecucion = request.getParameter("anoEjecucion");
				String mesEjecucion = request.getParameter("mesEjecucion");
				String etapa = request.getParameter("etapa");
				
				pk.setCodEmpresa(codEmpresa);
		        pk.setAnoPresentacion(new Long(anoPresentacion));
		        pk.setMesPresentacion(new Long(mesPresentacion));
		        pk.setAnoEjecucionGasto(new Long(anoEjecucion));
		        pk.setMesEjecucionGasto(new Long(mesEjecucion));
		        pk.setEtapa(etapa);
				
		        logger.info("codempresa "+codEmpresa);
		        logger.info("anopresent "+anoPresentacion);
		        logger.info("mespresent "+mesPresentacion);
		        logger.info("anoejecuc "+anoEjecucion);
		        logger.info("mesejecuc "+mesEjecucion);
		        logger.info("etapa "+etapa);
		        
		        formato = formatoService.obtenerFormato12ACByPK(pk);
		        
		        JSONObject jsonent = new Formato12AGartJSON().asJSONObject(formato);
		        logger.info("jsonformato:"+jsonent);
		        jsonObj.put("formato",jsonent);
				jsonObj.put("resultado", "OK");
			}else if(tipo.equals(FiseConstants.COD_SAVE)){ 
				try {
					Formato12ACBean formulario = new Formato12ACBean();
					String codEmpresa = request.getParameter("codEmpresa");
					String anoPresentacion = request.getParameter("anoPresentacion");
					String mesPresentacion = request.getParameter("mesPresentacion");
					String anoEjecucion = request.getParameter("anoEjecucion");
					String mesEjecucion = request.getParameter("mesEjecucion");
					//String zonaBenef = request.getParameter("zonaBenef");
					String nroEmpadR = request.getParameter("nroEmpadR");
					String costoUnitEmpadR = request.getParameter("costoUnitEmpadR");
					String nroAgentR = request.getParameter("nroAgentR");
					String costoUnitAgentR = request.getParameter("costoUnitAgentR");
					String despPersonalR = request.getParameter("despPersonalR");
					String activExtraordR = request.getParameter("activExtraordR");
					String nroEmpadP = request.getParameter("nroEmpadP");
					String costoUnitEmpadP = request.getParameter("costoUnitEmpadP");
					String nroAgentP = request.getParameter("nroAgentP");
					String costoUnitAgentP = request.getParameter("costoUnitAgentP");
					String despPersonalP = request.getParameter("despPersonalP");
					String activExtraordP = request.getParameter("activExtraordP");
					String nroEmpadL = request.getParameter("nroEmpadL");
					String costoUnitEmpadL = request.getParameter("costoUnitEmpadL");
					String nroAgentL = request.getParameter("nroAgentL");
					String costoUnitAgentL = request.getParameter("costoUnitAgentL");
					String despPersonalL = request.getParameter("despPersonalL");
					String activExtraordL = request.getParameter("activExtraordL");
					
					formulario.setCodigoEmpresa(codEmpresa);
					formulario.setAnioPresent(Long.parseLong(anoPresentacion));
					formulario.setMesPresent(Long.parseLong(mesPresentacion));
					formulario.setAnioEjecuc(Long.parseLong(anoEjecucion));
					formulario.setMesEjecuc(Long.parseLong(mesEjecucion));
					//formulario.setIdGrupoInfo(Long.parseLong(zonaBenef));
					formulario.setNroEmpadR(Long.parseLong(nroEmpadR));
					formulario.setCostoUnitEmpadR(new BigDecimal(costoUnitEmpadR));
					formulario.setNroAgentR(Long.parseLong(nroAgentR));
					formulario.setCostoUnitAgentR(new BigDecimal(costoUnitAgentR));
					formulario.setDesplPersonalR(new BigDecimal(despPersonalR));
					formulario.setActivExtraordR(new BigDecimal(activExtraordR));
					formulario.setNroEmpadP(Long.parseLong(nroEmpadP));
					formulario.setCostoUnitEmpadP(new BigDecimal(costoUnitEmpadP));
					formulario.setNroAgentP(Long.parseLong(nroAgentP));
					formulario.setCostoUnitAgentP(new BigDecimal(costoUnitAgentP));
					formulario.setDesplPersonalP(new BigDecimal(despPersonalP));
					formulario.setActivExtraordP(new BigDecimal(activExtraordP));
					formulario.setNroEmpadL(Long.parseLong(nroEmpadL));
					formulario.setCostoUnitEmpadL(new BigDecimal(costoUnitEmpadL));
					formulario.setNroAgentL(Long.parseLong(nroAgentL));
					formulario.setCostoUnitAgentL(new BigDecimal(costoUnitAgentL));
					formulario.setDesplPersonalL(new BigDecimal(despPersonalL));
					formulario.setActivExtraordL(new BigDecimal(activExtraordL));
					
					ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
					logger.info("save"+codEmpresa);
					logger.info("save"+anoPresentacion);
					logger.info("save"+mesPresentacion);
					
					formulario.setUsuario(themeDisplay.getUser().getLogin());
					formulario.setTerminal(themeDisplay.getUser().getLoginIP());
					
					formatoService.registrarFormato12AC(formulario);
					jsonObj.put("resultado", "OK");
	   				
				} catch (Exception e) {
					jsonObj.put("resultado", "Error");
					logger.error("Error al guardar los datos: "+e.getMessage());
				}   				   				
					 					 				
			}else if(tipo.equals(FiseConstants.COD_UPDATE)){
				logger.info("entro a modificar valores");
				
				try {
					
					FiseFormato12AC formato = new FiseFormato12AC();
					FiseFormato12ACPK pk = new FiseFormato12ACPK();
					String codEmpresa = request.getParameter("codEmpresa");
					String anoPresentacion = request.getParameter("anoPresentacion");
					String mesPresentacion = request.getParameter("mesPresentacion");
					String anoEjecucion = request.getParameter("anoEjecucion");
					String mesEjecucion = request.getParameter("mesEjecucion");
					String etapa = request.getParameter("etapa");
					
					logger.info("codempresa "+codEmpresa);
			        logger.info("anopresent "+anoPresentacion);
			        logger.info("mespresent "+mesPresentacion);
			        logger.info("anoejecuc "+anoEjecucion);
			        logger.info("mesejecuc "+mesEjecucion);
			        logger.info("etapa "+etapa);
					
			        Formato12ACBean formulario = new Formato12ACBean();
					
					String nroEmpadR = request.getParameter("nroEmpadR");
					String costoUnitEmpadR = request.getParameter("costoUnitEmpadR");
					String nroAgentR = request.getParameter("nroAgentR");
					String costoUnitAgentR = request.getParameter("costoUnitAgentR");
					String despPersonalR = request.getParameter("despPersonalR");
					String activExtraordR = request.getParameter("activExtraordR");
					String nroEmpadP = request.getParameter("nroEmpadP");
					String costoUnitEmpadP = request.getParameter("costoUnitEmpadP");
					String nroAgentP = request.getParameter("nroAgentP");
					String costoUnitAgentP = request.getParameter("costoUnitAgentP");
					String despPersonalP = request.getParameter("despPersonalP");
					String activExtraordP = request.getParameter("activExtraordP");
					String nroEmpadL = request.getParameter("nroEmpadL");
					String costoUnitEmpadL = request.getParameter("costoUnitEmpadL");
					String nroAgentL = request.getParameter("nroAgentL");
					String costoUnitAgentL = request.getParameter("costoUnitAgentL");
					String despPersonalL = request.getParameter("despPersonalL");
					String activExtraordL = request.getParameter("activExtraordL");
					
					formulario.setNroEmpadR(Long.parseLong(nroEmpadR));
					formulario.setCostoUnitEmpadR(new BigDecimal(costoUnitEmpadR));
					formulario.setNroAgentR(Long.parseLong(nroAgentR));
					formulario.setCostoUnitAgentR(new BigDecimal(costoUnitAgentR));
					formulario.setDesplPersonalR(new BigDecimal(despPersonalR));
					formulario.setActivExtraordR(new BigDecimal(activExtraordR));
					formulario.setNroEmpadP(Long.parseLong(nroEmpadP));
					formulario.setCostoUnitEmpadP(new BigDecimal(costoUnitEmpadP));
					formulario.setNroAgentP(Long.parseLong(nroAgentP));
					formulario.setCostoUnitAgentP(new BigDecimal(costoUnitAgentP));
					formulario.setDesplPersonalP(new BigDecimal(despPersonalP));
					formulario.setActivExtraordP(new BigDecimal(activExtraordP));
					formulario.setNroEmpadL(Long.parseLong(nroEmpadL));
					formulario.setCostoUnitEmpadL(new BigDecimal(costoUnitEmpadL));
					formulario.setNroAgentL(Long.parseLong(nroAgentL));
					formulario.setCostoUnitAgentL(new BigDecimal(costoUnitAgentL));
					formulario.setDesplPersonalL(new BigDecimal(despPersonalL));
					formulario.setActivExtraordL(new BigDecimal(activExtraordL));
					
					formulario.setCodigoEmpresa(codEmpresa);
					formulario.setAnioPresent(Long.parseLong(anoPresentacion));
					formulario.setMesPresent(Long.parseLong(mesPresentacion));
					
					pk.setCodEmpresa(codEmpresa);
			        pk.setAnoPresentacion(new Long(anoPresentacion));
			        pk.setMesPresentacion(new Long(mesPresentacion));
			        pk.setAnoEjecucionGasto(new Long(anoEjecucion));
			        pk.setMesEjecucionGasto(new Long(mesEjecucion));
			        pk.setEtapa(etapa);
			        logger.info("llego aqui ");
			        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			        
			        logger.info("llego tambien aqui ");
			        
			        formato = formatoService.obtenerFormato12ACByPK(pk);
					
			        logger.info("objeto "+formato);
			        
					formatoService.modificarFormato12AC(formulario, formato);
					jsonObj.put("resultado", "OK"); 	
				} catch (Exception e) {
					jsonObj.put("resultado", "Error");
					System.out.println("Error al guardar la tabla fiseformato12A: "+e.getMessage());
				}
					
				
			}else if(tipo.equals(FiseConstants.COD_DELETE)){   				
				try {
					FiseFormato12AC formato = new FiseFormato12AC();
					FiseFormato12ACPK pk = new FiseFormato12ACPK();
					String codEmpresa = request.getParameter("codEmpresa");
					String anoPresentacion = request.getParameter("anoPresentacion");
					String mesPresentacion = request.getParameter("mesPresentacion");
					String anoEjecucion = request.getParameter("anoEjecucion");
					String mesEjecucion = request.getParameter("mesEjecucion");
					String etapa = request.getParameter("etapa");
					
					logger.info("valorp"+codEmpresa);
					logger.info("valorp"+anoPresentacion);
					logger.info("valorp"+mesPresentacion);
					logger.info("valorp"+anoEjecucion);
					logger.info("valorp"+mesEjecucion);
					
					
					pk.setCodEmpresa(codEmpresa);
			        pk.setAnoPresentacion(new Long(anoPresentacion));
			        pk.setMesPresentacion(new Long(mesPresentacion));
			        pk.setAnoEjecucionGasto(new Long(anoEjecucion));
			        pk.setMesEjecucionGasto(new Long(mesEjecucion));
			        pk.setEtapa(etapa);
					
			        formato = formatoService.obtenerFormato12ACByPK(pk);
			        logger.info("valorpobjeto"+formato);
			        
			        //metodo delete
			        formatoService.eliminarFormato12AC(formato);
			        jsonObj.put("resultado", "OK");
				} catch (Exception e) {
					jsonObj.put("resultado", "Error");
					System.out.println("Error al eliminar datos "+e.getMessage());
				}   				   				
				
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
	
	@ResourceMapping("request_data")
  	public void requestData(SessionStatus status, ResourceRequest request,ResourceResponse response){
		try {			
  			response.setContentType("applicacion/json");
  			JSONArray jsonArray = new JSONArray();			
  			//String tipo = request.getParameter("tipo");			
  			//Map<String, Object> parametros = new HashMap<String, Object>();
  			FiseFormato14AD detalleRuralPadre = null;
  			FiseFormato14AD detalleProvinciaPadre = null;
  			FiseFormato14AD detalleLimaPadre = null;
  			
  			String codEmpresa = request.getParameter("s_empresa");
  			String anioPresent = request.getParameter("i_aniopresent");
  			
  			BigDecimal costoUnitEmpR = null;
  			BigDecimal costoUnitAgentR = null;
  			BigDecimal costoUnitEmpP = null;
  			BigDecimal costoUnitAgentP = null;
  			BigDecimal costoUnitEmpL = null;
  			BigDecimal costoUnitAgentL = null;
  			
  			detalleRuralPadre = formato14Service.obtenerFormato14ADVigente(codEmpresa, anioPresent.equals("")?0:Long.parseLong(anioPresent), FiseConstants.ZONABENEF_RURAL);
  			if( detalleRuralPadre!=null ){
  				costoUnitEmpR = detalleRuralPadre.getCostoUnitarioEmpadronamiento();
  				costoUnitAgentR = detalleRuralPadre.getCostoUntitarioAgenteGlp();
  			}else{
  				costoUnitEmpR = new BigDecimal(0.00);
  				costoUnitAgentR = new BigDecimal(0.00);
  			}
  			detalleProvinciaPadre = formato14Service.obtenerFormato14ADVigente(codEmpresa, anioPresent.equals("")?0:Long.parseLong(anioPresent), FiseConstants.ZONABENEF_PROVINCIA);
  			if( detalleProvinciaPadre!=null ){
  				costoUnitEmpP = detalleProvinciaPadre.getCostoUnitarioEmpadronamiento();
  				costoUnitAgentP = detalleProvinciaPadre.getCostoUntitarioAgenteGlp();
  			}else{
  				costoUnitEmpP = new BigDecimal(0.00);
  				costoUnitAgentP = new BigDecimal(0.00);
  			}
  			detalleLimaPadre = formato14Service.obtenerFormato14ADVigente(codEmpresa, anioPresent.equals("")?0:Long.parseLong(anioPresent), FiseConstants.ZONABENEF_LIMA);
  			if( detalleLimaPadre!=null ){
  				costoUnitEmpL = detalleLimaPadre.getCostoUnitarioEmpadronamiento();
  				costoUnitAgentL = detalleLimaPadre.getCostoUntitarioAgenteGlp();
  			}else{
  				costoUnitEmpL = new BigDecimal(0.00);
  				costoUnitAgentL = new BigDecimal(0.00);
  			}
  			
  			JSONObject jsonObj = new JSONObject();
  			jsonObj.put("costoEmpR", costoUnitEmpR);
  			jsonObj.put("costoAgentR", costoUnitAgentR);
  			jsonObj.put("costoEmpP", costoUnitEmpP);
  			jsonObj.put("costoAgentP", costoUnitAgentP);
  			jsonObj.put("costoEmpL", costoUnitEmpL);
  			jsonObj.put("costoAgentL", costoUnitAgentL);
  			//jsonArray.put(jsonObj);					 
  		    PrintWriter pw = response.getWriter();
  		    //pw.write(jsonArray.toString());
  		    pw.write(jsonObj.toString());
  		    pw.flush();
  		    pw.close();							
  		}catch (Exception e) {
  			// TODO: handle exception
  			e.printStackTrace();
  		}
	}
	
	@ActionMapping(params="action=cargar")
	public void subirDocumento(ActionRequest request,ActionResponse response){
		
		logger.info("--- cargar documento");
		FiseFormato12AC formato = new FiseFormato12AC();
		
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		
		//obtenemos parámetros del request
    	UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(request);
    	//String flagCarga = uploadPortletRequest.getParameter("flagCarga");
    	String flagCarga = request.getParameter("flagCarga");
    	String flagCarga2 = uploadPortletRequest.getParameter("flagCarga");
    	String codEmpresaEdit = request.getParameter("s_empresa");
		String anioPresEdit = request.getParameter("i_aniopresent");
		String mesPresEdit = request.getParameter("s_mes_present");
		String anioEjecEdit = request.getParameter("i_anioejecuc");
		String mesEjecEdit = request.getParameter("s_mes_ejecuc");
		
		if( flagCarga.equals(FiseConstants.FLAG_CARGAEXCEL_FORMULARIONUEVO) || flagCarga.equals(FiseConstants.FLAG_CARGAEXCEL_FORMULARIOMODIFICACION) ){
			FileEntry fileEntry=this.subirDocumento(request);
			formato = readExcelFile(fileEntry, themeDisplay.getUser(), flagCarga, codEmpresaEdit, anioPresEdit, mesPresEdit, anioEjecEdit, mesEjecEdit);
		}else if( flagCarga.equals(FiseConstants.FLAG_CARGATXT_FORMULARIONUEVO) || flagCarga.equals(FiseConstants.FLAG_CARGATXT_FORMULARIOMODIFICACION) ){
			File file = uploadPortletRequest.getFile("archivoTxt");
			if(file.getName().contains(".txt")){
				formato =	readTxtFile(file, themeDisplay.getUser(), flagCarga, codEmpresaEdit, anioPresEdit, mesPresEdit, anioEjecEdit, mesEjecEdit);
			}
		}
		
		
		
		String codEmpresa = formato.getId().getCodEmpresa();
		String anoPresentacion = String.valueOf(formato.getId().getAnoPresentacion());
		String mesPresentacion = String.valueOf(formato.getId().getMesPresentacion());
		String anoEjecucion = String.valueOf(formato.getId().getAnoEjecucionGasto());
		String mesEjecucion = String.valueOf(formato.getId().getMesEjecucionGasto());
		String etapa = String.valueOf(formato.getId().getEtapa());
		
		/*response.setRenderParameter("codEmpresa", codEmpresa);
		response.setRenderParameter("anoPresentacion", anoPresentacion);
		response.setRenderParameter("mesPresentacion", mesPresentacion);
		response.setRenderParameter("anoEjecucion", anoPresentacion);
		response.setRenderParameter("mesEjecucion", mesPresentacion);
		response.setRenderParameter("etapa", anoPresentacion);*/
		
		PortletRequest pRequest = (PortletRequest) request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
		
		/*PortletSession pSession = request.getPortletSession();
		pSession.setAttribute("codEmpresa", codEmpresa, PortletSession.APPLICATION_SCOPE);
		pSession.setAttribute("anoPresentacion", anoPresentacion, PortletSession.APPLICATION_SCOPE);
		pSession.setAttribute("mesPresentacion", mesPresentacion, PortletSession.APPLICATION_SCOPE);
		pSession.setAttribute("anoEjecucion", anoEjecucion, PortletSession.APPLICATION_SCOPE);
		pSession.setAttribute("mesEjecucion", mesEjecucion, PortletSession.APPLICATION_SCOPE);
		pSession.setAttribute("etapa", etapa, PortletSession.APPLICATION_SCOPE);*/
		
		pRequest.getPortletSession().setAttribute("codEmpresa", codEmpresa, PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("anoPresentacion", anoPresentacion, PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("mesPresentacion", mesPresentacion, PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("anoEjecucion", anoEjecucion, PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("mesEjecucion", mesEjecucion, PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("etapa", etapa, PortletSession.APPLICATION_SCOPE);
		
		//-----formato = formatoService.otroReadExcelFile(fileEntry, themeDisplay.getUser());
		//Registro BD
		/*String respuesta="OK";
		
		String codEmpresa = formato.getId().getCodEmpresa();
		String anoPresentacion = String.valueOf(formato.getId().getAnoPresentacion());
		String mesPresentacion = String.valueOf(formato.getId().getMesPresentacion());
		//---String zonaBenef = String.valueOf(formato.getIdGrupoInformacion());
		
		response.setRenderParameter("action", "exito");
		
		response.setRenderParameter("codEmpresa", codEmpresa);
		response.setRenderParameter("anoPresentacion", anoPresentacion);
		response.setRenderParameter("mesPresentacion", mesPresentacion);*/
		//---response.setRenderParameter("zonaBenef", zonaBenef);
		
	}
	
	@RequestMapping(params="action=exito")
	public  String mostrarSubida(RenderRequest request,RenderResponse response,ModelMap model,
			@RequestParam("codEmpresa")String codEmpresa,
			@RequestParam("anoPresentacion")String anoPresentacion,
			@RequestParam("mesPresentacion")String mesPresentacion,
			@RequestParam("zonaBenef")String zonaBenef,
			@RequestParam("nroEmpad")String nroEmpad,
			@RequestParam("costoUnitEmpad")String costoUnitEmpad,
			@RequestParam("nroAgent")String nroAgent,
			@RequestParam("costoUnitAgent")String costoUnitAgent,
			@RequestParam("despPersonal")String despPersonal,
			@RequestParam("activExtraord")String activExtraord
			){
		logger.info("--- mostrarResultados cargar");
		
		String mm="";
		
		if(codEmpresa!=null){
			mm = "1";
			model.addAttribute("flag", mm);
			
			model.addAttribute("s_empresa", codEmpresa);
			model.addAttribute("i_aniopresent", anoPresentacion);
			model.addAttribute("s_mes_present", mesPresentacion);
			model.addAttribute("s_zonabenef", zonaBenef);
			model.addAttribute("i_nroEmpad_r", nroEmpad);
			model.addAttribute("i_costoUnitEmpad_r", costoUnitEmpad);
			model.addAttribute("i_nroAgentGlp_r", nroAgent);
			model.addAttribute("i_costoUnitAgent_r", costoUnitAgent);
			model.addAttribute("i_despPersonal_r", despPersonal);
			model.addAttribute("i_activExtraord_r", activExtraord);

			
		}
		
		
		
		//setear atributos
		
		
		return "buscarFormatoFise12A";
	}
	
	/////////////////////////////////////////////////////////////////
	///////////////////////CARGA EXCEL/////////////////////////
	/////////////////////////////////////////////////////////////////
	
	public FileEntry subirDocumento(PortletRequest request) {
		// TODO Auto-generated method stub
		FileEntry fileEntry=null;
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(request);
		ThemeDisplay themeDisplay = (ThemeDisplay) request
                .getAttribute(WebKeys.THEME_DISPLAY);
		 try {
			 String[] mimeTypes = new String[]{"application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"};
			 long maxUploadFileSize =2097152;//bytes = 2MB
			 DLFolder dlFolder = DLFolderLocalServiceUtil.getFolder(themeDisplay.getScopeGroupId(), 0, "Backup");
			 
			 if (dlFolder.getGroupId() != themeDisplay.getScopeGroupId()) {
			 	throw new NoSuchFolderException();
			 }
			 
			 File file = uploadPortletRequest.getFile("archivoExcel");
			 String mimeType = uploadPortletRequest.getContentType("archivoExcel");
			 long size = uploadPortletRequest.getSize("archivoExcel");
			 String sourceFileName = uploadPortletRequest.getFileName("archivoExcel");

			 logger.info("MIME ARCHIVO:"+mimeType);
			 if (Arrays.binarySearch(mimeTypes, mimeType) < 0) {
					throw new FileMimeTypeException(mimeType);
			 }
			
			 String contenType=MimeTypesUtil.getContentType(file,"archivoExcel");
			 logger.info("MIME CONTENT TYPE:"+contenType);
			 if (Arrays.binarySearch(mimeTypes, contenType) < 0) {
					throw new FileMimeTypeException(contenType);
			 }
			
			 
			 logger.info("Size:"+size+" bytes");
			 logger.info("Max Size:"+maxUploadFileSize+" bytes");
			 
			 if(size>maxUploadFileSize){
				 throw new FileSizeException(String.valueOf(maxUploadFileSize));
			 }
			 
			 SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
			 String hoy=sdf.format(new Date());
			 long userId=themeDisplay.getUserId();
			 
			 long repositoryId=dlFolder.getRepositoryId();
			 long folderId=dlFolder.getFolderId();
			 //String ext =FileUtil.getExtension(sourceFileName);
			 
			 String title = hoy+"-"+sourceFileName;
			
			 //long basicDocument=DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT;

			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(), request);
			
			fileEntry=DLAppLocalServiceUtil.addFileEntry(userId, repositoryId, folderId, sourceFileName, mimeType,title, "", "Subido el "+hoy, file, serviceContext);
			
			DLAppLocalServiceUtil.updateFileEntry(fileEntry.getUserId(), 
					fileEntry.getFileEntryId(), 
					sourceFileName, mimeType, title, fileEntry.getDescription(), "Actualizo estado", true, file, serviceContext);
			
			logger.info("Archivo subido:"+sourceFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return fileEntry;
		
	}
	
	public FiseFormato12AC readExcelFile(FileEntry archivo, User user, String flagCarga, String codEmpresa, String anioPres, String mesPres, String anioEjec, String mesEjec) {
		
		//---------------------
		//FLAG CARGA:
		//	0: para registros nuevos
		//	1: para registros modificados
		//---------------------
		
		//StringBuilder html=new StringBuilder();
		InputStream is=null;
		
		FiseFormato12AC objeto = null;
		//JSONObject json = new JSONObject();
		
		String sMsg = "";
		
		try {
			if (archivo != null) {
				HSSFWorkbook libro = null;
				try {
					is=archivo.getContentStream();
					libro = new HSSFWorkbook(is);//Se lee libro xls
					
				} catch (Exception e1) {
					logger.warn("El archivo no es formato XLS");
					sMsg = sMsg + "El archivo " + archivo.getDescription() + " no corresponde al formato XLS. ";
					throw new Exception("El archivo no corresponde al formato XLS.");
				}
				
				/*if(libro==null){
					try {
						is=archivo.getContentStream();
						libro = new XSSFWorkbook(is); //Se lee el libro xlsx
					
					} catch (Exception e1) {
						logger.warn("El archivo no es formato XLSX");
					}
				}*/

				int nroHojaSelec=0;
				
				if (libro != null) {
					//el excel puede tener varias hojas, se tiene qie leer el total de hojas y encontrar la que necesitemos
					logger.info("nro de hojas:"+ libro.getNumberOfSheets());
					for (int sheetNro = 0; sheetNro < libro.getNumberOfSheets(); sheetNro++){
						logger.info("nombre de hoja "+sheetNro+":"+ libro.getSheetName(sheetNro));
						if( FiseConstants.NOMBRE_HOJA_FORMATO12A.equals(libro.getSheetName(sheetNro)) ){
							nroHojaSelec = sheetNro;
							break;
						}
					}
					logger.info("nro de hoja seleccionada "+nroHojaSelec);
					HSSFSheet hojaF12 = libro.getSheetAt(nroHojaSelec);
					//int nroFilas = hojaF12.getLastRowNum()+1;
					
					HSSFRow filaEmpresa = hojaF12.getRow(FiseConstants.NRO_FILA_CODEMPRESA_FORMATO12A);					//COD EMPRESA
					HSSFRow filaAnioMes = hojaF12.getRow(FiseConstants.NRO_FILA_ANIOMES_FORMATO12A);					//ANO MES PRESENTACION
					//Row filaZonaBenef = hojaF12.getRow(10);						//RURAL-PROVINCIA-LIMA
					HSSFRow filaNroEmpad = hojaF12.getRow(FiseConstants.NRO_FILA_EMPAD_FORMATO12A);				//NRO EMPADRONADOS
					//Row filaCostoUnitEmpad = hojaF12.getRow(13);			//COSTO UNIT EMPAD
					HSSFRow filaNroAgent = hojaF12.getRow(FiseConstants.NRO_FILA_AGENT_FORMATO12A);				//NRO AGENTES
					//Row filaCostoUnitAgent = hojaF12.getRow(17);			//COSTO UNIT AGENT
					HSSFRow filaDespPersonal = hojaF12.getRow(FiseConstants.NRO_FILA_DESPLPERSON_FORMATO12A);			//DESPLAZ. PERSONAL
					HSSFRow filaActivExtraord = hojaF12.getRow(FiseConstants.NRO_FILA_ACTIVEXTR_FORMATO12A);		//ACTIV. EXTRAORDINARIAS

					//guardar valores del formulario para su grabacion
					Formato12ACBean formulario = new Formato12ACBean();
					
					HSSFCell celdaEmpresa = filaEmpresa.getCell(FiseConstants.NRO_CELDA_EMPRESA);
					HSSFCell celdaAnio = filaAnioMes.getCell(FiseConstants.NRO_CELDA_ANIO);
					HSSFCell celdaMes = filaAnioMes.getCell(FiseConstants.NRO_CELDA_MES);
					
					///Se capturaran los valores de las 3 columnas tanto RURAL - PROVINCIA - LIMA
					HSSFCell nroEmpadRural = filaNroEmpad.getCell(FiseConstants.NRO_CELDA_RURAL);
					HSSFCell nroEmpadProv = filaNroEmpad.getCell(FiseConstants.NRO_CELDA_PROVINCIA);
					HSSFCell nroEmpadLima = filaNroEmpad.getCell(FiseConstants.NRO_CELDA_LIMA);
					
					//Cell costoUnitEmpRural = filaCostoUnitEmpad.getCell(7);
					//Cell costoUnitEmpProv = filaCostoUnitEmpad.getCell(8);
					//Cell costoUnitEmpLima = filaCostoUnitEmpad.getCell(9);
					
					HSSFCell nroAgentRural = filaNroAgent.getCell(FiseConstants.NRO_CELDA_RURAL);
					HSSFCell nroAgentProv = filaNroAgent.getCell(FiseConstants.NRO_CELDA_PROVINCIA);
					HSSFCell nroAgentLima = filaNroAgent.getCell(FiseConstants.NRO_CELDA_LIMA);
					
					//Cell costoUnitAgentRural = filaCostoUnitAgent.getCell(7);
					//Cell costoUnitAgentProv = filaCostoUnitAgent.getCell(8);
					//Cell costoUnitAgentLima = filaCostoUnitAgent.getCell(9);
					
					HSSFCell despPersonalR = filaDespPersonal.getCell(FiseConstants.NRO_CELDA_RURAL);
					HSSFCell despPersonalP = filaDespPersonal.getCell(FiseConstants.NRO_CELDA_PROVINCIA);
					HSSFCell despPersonalL = filaDespPersonal.getCell(FiseConstants.NRO_CELDA_LIMA);
					
					HSSFCell activExtraordR = filaActivExtraord.getCell(FiseConstants.NRO_CELDA_RURAL);
					HSSFCell activExtraordP = filaActivExtraord.getCell(FiseConstants.NRO_CELDA_PROVINCIA);
					HSSFCell activExtraordL = filaActivExtraord.getCell(FiseConstants.NRO_CELDA_LIMA);
					
					//tipos
					if( HSSFCell.CELL_TYPE_STRING == celdaEmpresa.getCellType()  ){
						formulario.setCodigoEmpresa(celdaEmpresa.toString());
					}else{
						formulario.setCodigoEmpresa("");
						sMsg = sMsg + "El codigo de empresa no corresponde al formato requerido.";
					}
					if( HSSFCell.CELL_TYPE_STRING == celdaAnio.getCellType()  ){
						formulario.setAnioPresent(Long.parseLong(celdaAnio.toString()));
						formulario.setAnioEjecuc(Long.parseLong(celdaAnio.toString()));
					}else{
						formulario.setAnioPresent(0);
						formulario.setAnioEjecuc(0);
						sMsg = sMsg + "El Año y mes de presentación no corresponde al formato requerido.";
					}
					if( HSSFCell.CELL_TYPE_STRING == celdaMes.getCellType()  ){
						formulario.setMesPresent(Long.parseLong(celdaMes.toString()));
						formulario.setMesEjecuc(Long.parseLong(celdaMes.toString()));
					}else{
						formulario.setMesPresent(0);
						formulario.setMesEjecuc(0);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == nroEmpadRural.getCellType()  ){
						formulario.setNroEmpadR(new Double(nroEmpadRural.getNumericCellValue()).longValue());
					}else{
						formulario.setNroEmpadR(0);
						sMsg = sMsg + "El número de empadronados Rural no corresponde al formato requerido.";
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == nroEmpadProv.getCellType()  ){
						formulario.setNroEmpadP(new Double(nroEmpadProv.getNumericCellValue()).longValue());
					}else{
						formulario.setNroEmpadP(0);
						sMsg = sMsg + "El número de empadronados Provincia no corresponde al formato requerido.";
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == nroEmpadLima.getCellType()  ){
						formulario.setNroEmpadL(new Double(nroEmpadLima.getNumericCellValue()).longValue());
					}else{
						formulario.setNroEmpadL(0);
						sMsg = sMsg + "El número de empadronados Lima no corresponde al formato requerido.";
					}
					//
					/*if( Cell.CELL_TYPE_NUMERIC == costoUnitEmpRural.getCellType()  ){
						formulario.setCostoUnitEmpadR(new BigDecimal(costoUnitEmpRural.getNumericCellValue()));
					}else{
						formulario.setCostoUnitEmpadR(new BigDecimal(0));
					}
					if( Cell.CELL_TYPE_NUMERIC == costoUnitEmpProv.getCellType()  ){
						formulario.setCostoUnitEmpadP(new BigDecimal(costoUnitEmpProv.getNumericCellValue()));
					}else{
						formulario.setCostoUnitEmpadP(new BigDecimal(0));
					}
					if( Cell.CELL_TYPE_NUMERIC == costoUnitEmpLima.getCellType()  ){
						formulario.setCostoUnitEmpadL(new BigDecimal(costoUnitEmpLima.getNumericCellValue()));
					}else{
						formulario.setCostoUnitEmpadL(new BigDecimal(0));
					}*/
					//
					if( HSSFCell.CELL_TYPE_NUMERIC == nroAgentRural.getCellType()  ){
						formulario.setNroAgentR(new Double(nroAgentRural.getNumericCellValue()).longValue());
					}else{
						formulario.setNroAgentR(0);
						sMsg = sMsg + "El número de agentes Rural no corresponde al formato requerido.";
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == nroAgentProv.getCellType()  ){
						formulario.setNroAgentP(new Double(nroAgentProv.getNumericCellValue()).longValue());
					}else{
						formulario.setNroAgentP(0);
						sMsg = sMsg + "El número de agentes Provincia no corresponde al formato requerido.";
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == nroAgentLima.getCellType()  ){
						formulario.setNroAgentL(new Double(nroAgentLima.getNumericCellValue()).longValue());
					}else{
						formulario.setNroAgentL(0);
						sMsg = sMsg + "El número de agentes Lima no corresponde al formato requerido.";
					}
					//
					/*if( Cell.CELL_TYPE_NUMERIC == costoUnitAgentRural.getCellType()  ){
						formulario.setCostoUnitAgentR(new BigDecimal(costoUnitAgentRural.getNumericCellValue()));
					}else{
						formulario.setCostoUnitAgentR(new BigDecimal(0));
					}
					if( Cell.CELL_TYPE_NUMERIC == costoUnitAgentProv.getCellType()  ){
						formulario.setCostoUnitAgentP(new BigDecimal(costoUnitAgentProv.getNumericCellValue()));
					}else{
						formulario.setCostoUnitAgentP(new BigDecimal(0));
					}
					if( Cell.CELL_TYPE_NUMERIC == costoUnitAgentLima.getCellType()  ){
						formulario.setCostoUnitAgentL(new BigDecimal(costoUnitAgentLima.getNumericCellValue()));
					}else{
						formulario.setCostoUnitAgentL(new BigDecimal(0));
					}*/
					//
					if( HSSFCell.CELL_TYPE_NUMERIC == despPersonalR.getCellType()  ){
						formulario.setDesplPersonalR(new BigDecimal(despPersonalR.getNumericCellValue()));
					}else{
						formulario.setDesplPersonalR(new BigDecimal(0.00));
						sMsg = sMsg + "El desplazamiento personal Rural no corresponde al formato requerido.";
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == despPersonalP.getCellType()  ){
						formulario.setDesplPersonalP(new BigDecimal(despPersonalP.getNumericCellValue()));
					}else{
						formulario.setDesplPersonalP(new BigDecimal(0.00));
						sMsg = sMsg + "El desplazamiento personal Provincia no corresponde al formato requerido.";
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == despPersonalL.getCellType()  ){
						formulario.setDesplPersonalL(new BigDecimal(despPersonalL.getNumericCellValue()));
					}else{
						formulario.setDesplPersonalL(new BigDecimal(0.00));
						sMsg = sMsg + "El desplazamiento personal Lima no corresponde al formato requerido.";
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == activExtraordR.getCellType()  ){
						formulario.setActivExtraordR(new BigDecimal(activExtraordR.getNumericCellValue()));
					}else{
						formulario.setActivExtraordR(new BigDecimal(0.00));
						sMsg = sMsg + "Las actividades extraordinarias Rural no corresponde al formato requerido.";
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == activExtraordP.getCellType()  ){
						formulario.setActivExtraordP(new BigDecimal(activExtraordP.getNumericCellValue()));
					}else{
						formulario.setActivExtraordP(new BigDecimal(0.00));
						sMsg = sMsg + "Las actividades extraordinarias Provincia no corresponde al formato requerido.";
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == activExtraordL.getCellType()  ){
						formulario.setActivExtraordL(new BigDecimal(activExtraordL.getNumericCellValue()));
					}else{
						formulario.setActivExtraordL(new BigDecimal(0.00));
						sMsg = sMsg + "Las actividades extraordinarias Lima no corresponde al formato requerido.";
					}
					//obtenemos los costos unitarios del formato padre
					FiseFormato14AD detalleRuralPadre = null;
		  			FiseFormato14AD detalleProvinciaPadre = null;
		  			FiseFormato14AD detalleLimaPadre = null;
		  			
		  			detalleRuralPadre = formato14Service.obtenerFormato14ADVigente(formulario.getCodigoEmpresa(), formulario.getAnioPresent(), FiseConstants.ZONABENEF_RURAL);
		  			if( detalleRuralPadre!=null ){
		  				formulario.setCostoUnitEmpadR(detalleRuralPadre.getCostoUnitarioEmpadronamiento());
		  				formulario.setCostoUnitAgentR(detalleRuralPadre.getCostoUntitarioAgenteGlp());
		  			}else{
		  				formulario.setCostoUnitEmpadR(new BigDecimal(0.00));
		  				formulario.setCostoUnitAgentR(new BigDecimal(0.00));
		  			}
		  			detalleProvinciaPadre = formato14Service.obtenerFormato14ADVigente(formulario.getCodigoEmpresa(), formulario.getAnioPresent(), FiseConstants.ZONABENEF_PROVINCIA);
		  			if( detalleProvinciaPadre!=null ){
		  				formulario.setCostoUnitEmpadP(detalleProvinciaPadre.getCostoUnitarioEmpadronamiento());
		  				formulario.setCostoUnitAgentP(detalleProvinciaPadre.getCostoUntitarioAgenteGlp());
		  			}else{
		  				formulario.setCostoUnitEmpadP(new BigDecimal(0.00));
		  				formulario.setCostoUnitAgentP(new BigDecimal(0.00));
		  			}
		  			detalleLimaPadre = formato14Service.obtenerFormato14ADVigente(formulario.getCodigoEmpresa(), formulario.getAnioPresent(), FiseConstants.ZONABENEF_LIMA);
		  			if( detalleLimaPadre!=null ){
		  				formulario.setCostoUnitEmpadL(detalleLimaPadre.getCostoUnitarioEmpadronamiento());
		  				formulario.setCostoUnitAgentL(detalleLimaPadre.getCostoUntitarioAgenteGlp());
		  			}else{
		  				formulario.setCostoUnitEmpadL(new BigDecimal(0.00));
		  				formulario.setCostoUnitAgentL(new BigDecimal(0.00));
		  			}
					//
					formulario.setUsuario(user.getLogin());
					formulario.setTerminal(user.getLoginIP());
					//
					if( FiseConstants.FLAG_CARGAEXCEL_FORMULARIONUEVO.equals(flagCarga) ){
						objeto = formatoService.registrarFormato12AC(formulario);
					}else if( FiseConstants.FLAG_CARGAEXCEL_FORMULARIOMODIFICACION.equals(flagCarga) ){
						//
						if( codEmpresa.equals(formulario.getCodigoEmpresa()) &&
								anioPres.equals(String.valueOf(formulario.getAnioPresent())) &&
								mesPres.equals(String.valueOf(formulario.getMesPresent())) &&
								anioEjec.equals(String.valueOf(formulario.getAnioPresent())) &&
								mesEjec.equals(String.valueOf(formulario.getMesPresent())) 
								){
							FiseFormato12AC formatoModif = new FiseFormato12AC();
							FiseFormato12ACPK id = new FiseFormato12ACPK();
							id.setCodEmpresa(formulario.getCodigoEmpresa());
							id.setAnoPresentacion(formulario.getAnioPresent());
							id.setMesPresentacion(formulario.getMesPresent());
							id.setAnoEjecucionGasto(formulario.getAnioPresent());
							id.setMesEjecucionGasto(formulario.getMesPresent());
							id.setEtapa(FiseConstants.ETAPA_SOLICITUD);
							formatoModif = formatoService.obtenerFormato12ACByPK(id);
							
							objeto = formatoService.modificarFormato12AC(formulario, formatoModif);
						
						}else{
							//el archivo cargado no coincide con el archivo que se esta modificando
							sMsg = sMsg + "El archivo cargado no corresponde al registro que se esta modificando.";
						}
						
					}
					
				}
					
			}

		} catch (Exception e) {
			logger.error("Error al leer el archivo excel:",e);
		}finally{
			StreamUtil.cleanUp(is);
		}
		return objeto;
	}
	
	public FiseFormato12AC readTxtFile(File archivo, User user, String flagCarga, String codEmpresa, String anioPres, String mesPres, String anioEjec, String mesEjec) {
		
		int cont = 0;
		
		try{
			String sCurrentLine;
			
			FileInputStream fis = new FileInputStream(archivo);
			int BUFFER_SIZE = 8192;
			BufferedReader br = new BufferedReader( new InputStreamReader(fis, "utf8"),BUFFER_SIZE);
			File aTemporal = File.createTempFile(codEmpresa+"_"+anioPres+"_"+mesPres+"_"+anioPres+"_"+anioEjec+"_"+mesEjec+"_",".tmp");
			//tempFile.deleteOnExit();
			BufferedWriter out = new BufferedWriter(new FileWriter(aTemporal));
			while ((sCurrentLine = br.readLine()) != null) {
				cont++;
				if( sCurrentLine.length()>0 ){
					if( sCurrentLine.length() == 159 ){
						sCurrentLine = sCurrentLine.substring(0, sCurrentLine.length()-1);
						System.out.println(sCurrentLine);
					}else{
						//el tamano de los registros no alcanza el permitido
					}
				}else{
					//no hay linea de text para leer
				}
				
				 sCurrentLine = sCurrentLine.substring(0, sCurrentLine.length()-1);					   
			}
			
			
		}catch (Exception e) {			   
			  //refer.setCondicion(false);				  
			  String error = e.getMessage();
			  System.out.println(error);
			  //throw new Exception(error); 
			  			   
		  }
		return null;
	}
	
}
