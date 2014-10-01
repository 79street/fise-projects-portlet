package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.Formato12ACBean;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.AdmEmpresa;
import gob.osinergmin.fise.domain.FiseFormato12AC;
import gob.osinergmin.fise.domain.FiseFormato12ACPK;
import gob.osinergmin.fise.domain.FiseZonaBenef;
import gob.osinergmin.fise.gart.json.Formato12AGartJSON;
import gob.osinergmin.fise.gart.service.AdmEmpresaGartService;
import gob.osinergmin.fise.gart.service.FiseZonaBenefGartService;
import gob.osinergmin.fise.gart.service.Formato12AGartService;
import gob.osinergmin.fise.util.FechaUtil;
import gob.osinergmin.fise.util.FormatoUtil;

import java.io.File;
import java.io.InputStream;
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

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

@Controller
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
		
		obj.setCodEmpresa(codEmpresa!=null?codEmpresa:"");
		obj.setAnoPres(anioPresentacion!=null?anioPresentacion:"");
		obj.setMesPres(anioPresentacion!=null?mesPresentacion:"");
		obj.setAnoEjec(anioPresentacion!=null?anioEjecucion:"");
		obj.setMesEjec(anioPresentacion!=null?mesEjecucion:"");
		obj.setEtapa(etapa!=null?etapa:"");
		
		//model.put("model", obj);
		model.addAttribute("model", obj);
		
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
	
	@ActionMapping(params="action=cargar")
	public void subirDocumento(ActionRequest request,ActionResponse response){
		
		logger.info("--- cargar documento");
		FiseFormato12AC formato = new FiseFormato12AC();
		
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		
		FileEntry fileEntry=this.subirDocumento(request);
		formato = readExcelFile(fileEntry, themeDisplay.getUser());
		
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
			 
			 File file = uploadPortletRequest.getFile("archivo");
			 String mimeType = uploadPortletRequest.getContentType("archivo");
			 long size = uploadPortletRequest.getSize("archivo");
			 String sourceFileName = uploadPortletRequest.getFileName("archivo");

			 logger.info("MIME ARCHIVO:"+mimeType);
			 if (Arrays.binarySearch(mimeTypes, mimeType) < 0) {
					throw new FileMimeTypeException(mimeType);
			 }
			
			 String contenType=MimeTypesUtil.getContentType(file,"archivo");
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
			
			/*DLAppLocalServiceUtil.updateFileEntry(fileEntry.getUserId(), 
					fileEntry.getFileEntryId(), 
					sourceFileName, mimeType, title, fileEntry.getDescription(), "Actualizo estado", true, file, serviceContext);*/
			
			logger.info("Archivo subido:"+sourceFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 return fileEntry;
		
	}
	
	public FiseFormato12AC readExcelFile(FileEntry archivo, User user) {
		StringBuilder html=new StringBuilder();
		InputStream is=null;
		
		FiseFormato12AC objeto = null;
		JSONObject json = new JSONObject();
		
		try {
			if (archivo != null) {
				Workbook libro = null;
				try {
					is=archivo.getContentStream();
					libro = new HSSFWorkbook(is);//Se lee libro xls
					
				} catch (Exception e1) {
					logger.warn("El archivo no es formato XLS");
				}
				
				if(libro==null){
					try {
						is=archivo.getContentStream();
						libro = new XSSFWorkbook(is); //Se lee el libro xlsx
					
					} catch (Exception e1) {
						logger.warn("El archivo no es formato XLSX");
					}
				}

				int nroHojaSelec=0;
				
				if (libro != null) {
					//el excel puede tener varias hojas, se tiene qie leer el total de hojas y encontrar la que necesitemos
					logger.info("nro de hojas:"+ libro.getNumberOfSheets());
					for (int sheetNro = 0; sheetNro < libro.getNumberOfSheets(); sheetNro++){
						logger.info("nombre de hoja "+sheetNro+":"+ libro.getSheetAt(sheetNro).getSheetName());
						if( "F12A_Remision".equals(libro.getSheetAt(sheetNro).getSheetName()) ){
							nroHojaSelec = sheetNro;
							break;
						}
					}
					logger.info("nro de hoja seleccionada "+nroHojaSelec);
					Sheet hojaF12 = libro.getSheetAt(nroHojaSelec);
					//int nroFilas = hojaF12.getLastRowNum()+1;
					
					Row filaEmpresa = hojaF12.getRow(4);					//COD EMPRESA
					Row filaAnioMes = hojaF12.getRow(5);					//ANO MES PRESENTACION
					//Row filaZonaBenef = hojaF12.getRow(10);			//RURAL-PROVINCIA-LIMA
					Row filaNroEmpad = hojaF12.getRow(12);			//NRO EMPADRONADOS
					Row filaCostoUnitEmpad = hojaF12.getRow(13);//COSTO UNIT EMPAD
					Row filaNroAgent = hojaF12.getRow(16);				//NRO AGENTES
					Row filaCostoUnitAgent = hojaF12.getRow(17);	//COSTO UNIT AGENT
					Row filaDespPersonal = hojaF12.getRow(19);		//DESPLAZ. PERSONAL
					Row filaActivExtraord = hojaF12.getRow(20);	//ACTIV. EXTRAORDINARIAS

					//guardar valores del formulario para su grabacion
					Formato12ACBean formulario = new Formato12ACBean();
					
					Cell celdaEmpresa = filaEmpresa.getCell(5);
					Cell celdaAnio = filaAnioMes.getCell(5);
					Cell celdaMes = filaAnioMes.getCell(6);
					
					///Se capturaran los valores de las 3 columnas tanto RURAL - PROVINCIA - LIMA
					Cell nroEmpadRural = filaNroEmpad.getCell(7);
					Cell nroEmpadProv = filaNroEmpad.getCell(8);
					Cell nroEmpadLima = filaNroEmpad.getCell(9);
					
					Cell costoUnitEmpRural = filaCostoUnitEmpad.getCell(7);
					Cell costoUnitEmpProv = filaCostoUnitEmpad.getCell(8);
					Cell costoUnitEmpLima = filaCostoUnitEmpad.getCell(9);
					
					Cell nroAgentRural = filaNroAgent.getCell(7);
					Cell nroAgentProv = filaNroAgent.getCell(8);
					Cell nroAgentLima = filaNroAgent.getCell(9);
					
					Cell costoUnitAgentRural = filaCostoUnitAgent.getCell(7);
					Cell costoUnitAgentProv = filaCostoUnitAgent.getCell(8);
					Cell costoUnitAgentLima = filaCostoUnitAgent.getCell(9);
					
					Cell despPersonalR = filaDespPersonal.getCell(7);
					Cell despPersonalP = filaDespPersonal.getCell(8);
					Cell despPersonalL = filaDespPersonal.getCell(9);
					
					Cell activExtraordR = filaActivExtraord.getCell(7);
					Cell activExtraordP = filaActivExtraord.getCell(8);
					Cell activExtraordL = filaActivExtraord.getCell(9);
					
					//tipos
					if( Cell.CELL_TYPE_STRING == celdaEmpresa.getCellType()  ){
						formulario.setCodigoEmpresa(celdaEmpresa.getStringCellValue());
					}else{
						formulario.setCodigoEmpresa("");
					}
					if( Cell.CELL_TYPE_STRING == celdaAnio.getCellType()  ){
						formulario.setAnioPresent(Long.parseLong(celdaAnio.getStringCellValue()));
						formulario.setAnioEjecuc(Long.parseLong(celdaAnio.getStringCellValue()));
					}else{
						formulario.setAnioPresent(0);
						formulario.setAnioEjecuc(0);
					}
					if( Cell.CELL_TYPE_STRING == celdaMes.getCellType()  ){
						formulario.setMesPresent(Long.parseLong(celdaMes.getStringCellValue()));
						formulario.setMesEjecuc(Long.parseLong(celdaMes.getStringCellValue()));
					}else{
						formulario.setMesPresent(0);
						formulario.setMesEjecuc(0);
					}
					if( Cell.CELL_TYPE_NUMERIC == nroEmpadRural.getCellType()  ){
						formulario.setNroEmpadR(new Double(nroEmpadRural.getNumericCellValue()).longValue());
					}else{
						formulario.setNroEmpadR(0);
					}
					if( Cell.CELL_TYPE_NUMERIC == nroEmpadProv.getCellType()  ){
						formulario.setNroEmpadP(new Double(nroEmpadProv.getNumericCellValue()).longValue());
					}else{
						formulario.setNroEmpadP(0);
					}
					if( Cell.CELL_TYPE_NUMERIC == nroEmpadLima.getCellType()  ){
						formulario.setNroEmpadL(new Double(nroEmpadLima.getNumericCellValue()).longValue());
					}else{
						formulario.setNroEmpadL(0);
					}
					//
					if( Cell.CELL_TYPE_NUMERIC == costoUnitEmpRural.getCellType()  ){
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
					}
					//
					if( Cell.CELL_TYPE_NUMERIC == nroAgentRural.getCellType()  ){
						formulario.setNroAgentR(new Double(nroAgentRural.getNumericCellValue()).longValue());
					}else{
						formulario.setNroAgentR(0);
					}
					if( Cell.CELL_TYPE_NUMERIC == nroAgentProv.getCellType()  ){
						formulario.setNroAgentP(new Double(nroAgentProv.getNumericCellValue()).longValue());
					}else{
						formulario.setNroAgentP(0);
					}
					if( Cell.CELL_TYPE_NUMERIC == nroAgentLima.getCellType()  ){
						formulario.setNroAgentL(new Double(nroAgentLima.getNumericCellValue()).longValue());
					}else{
						formulario.setNroAgentL(0);
					}
					//
					if( Cell.CELL_TYPE_NUMERIC == costoUnitAgentRural.getCellType()  ){
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
					}
					//
					if( Cell.CELL_TYPE_NUMERIC == despPersonalR.getCellType()  ){
						formulario.setDesplPersonalR(new BigDecimal(despPersonalR.getNumericCellValue()));
					}else{
						formulario.setDesplPersonalR(new BigDecimal(0));
					}
					if( Cell.CELL_TYPE_NUMERIC == despPersonalP.getCellType()  ){
						formulario.setDesplPersonalP(new BigDecimal(despPersonalP.getNumericCellValue()));
					}else{
						formulario.setDesplPersonalP(new BigDecimal(0));
					}
					if( Cell.CELL_TYPE_NUMERIC == despPersonalL.getCellType()  ){
						formulario.setDesplPersonalL(new BigDecimal(despPersonalL.getNumericCellValue()));
					}else{
						formulario.setDesplPersonalL(new BigDecimal(0));
					}
					if( Cell.CELL_TYPE_NUMERIC == activExtraordR.getCellType()  ){
						formulario.setActivExtraordR(new BigDecimal(activExtraordR.getNumericCellValue()));
					}else{
						formulario.setActivExtraordR(new BigDecimal(0));
					}
					if( Cell.CELL_TYPE_NUMERIC == activExtraordP.getCellType()  ){
						formulario.setActivExtraordP(new BigDecimal(activExtraordP.getNumericCellValue()));
					}else{
						formulario.setActivExtraordP(new BigDecimal(0));
					}
					if( Cell.CELL_TYPE_NUMERIC == activExtraordL.getCellType()  ){
						formulario.setActivExtraordL(new BigDecimal(activExtraordL.getNumericCellValue()));
					}else{
						formulario.setActivExtraordL(new BigDecimal(0));
					}
					//validar luego si va a ver campos en blanco y mandar los errores en una traza
					
					formulario.setUsuario(user.getLogin());
					formulario.setTerminal(user.getLoginIP());
					
					objeto = formatoService.registrarFormato12AC(formulario);
				}
					
			}

		} catch (Exception e) {
			logger.error("Error al leer el archivo excel:",e);
		}finally{
			StreamUtil.cleanUp(is);
		}
		return objeto;
	}
	
	
	
}
