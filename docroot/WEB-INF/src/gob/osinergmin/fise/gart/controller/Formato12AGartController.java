package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.Formato12ACBean;
import gob.osinergmin.fise.bean.Formato12AMensajeBean;
import gob.osinergmin.fise.bean.MensajeErrorBean;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.AdmEmpresa;
import gob.osinergmin.fise.domain.CfgCampo;
import gob.osinergmin.fise.domain.CfgTabla;
import gob.osinergmin.fise.domain.FiseFormato12AC;
import gob.osinergmin.fise.domain.FiseFormato12ACPK;
import gob.osinergmin.fise.domain.FiseFormato14AD;
import gob.osinergmin.fise.domain.FiseZonaBenef;
import gob.osinergmin.fise.gart.json.Formato12AGartJSON;
import gob.osinergmin.fise.gart.service.AdmEmpresaGartService;
import gob.osinergmin.fise.gart.service.CfgCampoGartService;
import gob.osinergmin.fise.gart.service.FiseZonaBenefGartService;
import gob.osinergmin.fise.gart.service.Formato12AGartService;
import gob.osinergmin.fise.gart.service.Formato14AGartService;
import gob.osinergmin.fise.util.FechaUtil;
import gob.osinergmin.fise.util.FormatoUtil;
import gob.osinergmin.fise.xls.XlsTableConfig;
import gob.osinergmin.fise.xls.XlsWorkbookConfig;
import gob.osinergmin.fise.xls.XlsWorksheetConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.bookmarks.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.FileMimeTypeException;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoTableConstants;
import com.liferay.portlet.expando.model.ExpandoValueModel;
import com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil;

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
	
	@Autowired
	@Qualifier("cfgCampoGartServiceImpl")
	CfgCampoGartService campoService;
	
	//@Autowired
	List<FiseFormato12AC> listaFormato;
	private Map<Long,String> listaMes;
	private List<AdmEmpresa> listaEmpresa;
	private List<FiseZonaBenef> listaZonaBenef;
	
	private Map<String, String> mapaEmpresa;
	
	@SuppressWarnings("unchecked")
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
		String msg = (String)pRequest.getPortletSession().getAttribute("mensajeInformacion", PortletSession.APPLICATION_SCOPE);
		List<MensajeErrorBean> listaError = (List<MensajeErrorBean>)pRequest.getPortletSession().getAttribute("listaError", PortletSession.APPLICATION_SCOPE);
		
		obj.setCodEmpresa(codEmpresa!=null?codEmpresa:"");
		obj.setAnoPres(anioPresentacion!=null?anioPresentacion:"");
		obj.setMesPres(anioPresentacion!=null?mesPresentacion:"");
		obj.setAnoEjec(anioPresentacion!=null?anioEjecucion:"");
		obj.setMesEjec(anioPresentacion!=null?mesEjecucion:"");
		obj.setEtapa(etapa!=null?etapa:"");
		//
		obj.setMensaje(msg!=null?msg:"");
		
		pRequest.getPortletSession().setAttribute("codEmpresa", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("anoPresentacion", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("mesPresentacion", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("anoEjecucion", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("mesEjecucion", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("etapa", "", PortletSession.APPLICATION_SCOPE);
	    //
	    pRequest.getPortletSession().setAttribute("mensajeInformacion", "", PortletSession.APPLICATION_SCOPE);
		
		cargaInicial(renderRequest);
		model.addAttribute("listaMes", listaMes);
		model.addAttribute("listaEmpresa", listaEmpresa);
		model.addAttribute("listaZonaBenef", listaZonaBenef);
		if( listaError!=null && listaError.size()>0){
			model.addAttribute("listaError", listaError);
		}
		
		String anioDesde = FechaUtil.obtenerNroAnioFechaActual();
		String mesDesde = String.valueOf(Integer.parseInt(FechaUtil.obtenerNroMesFechaActual())-1);
		String anioHasta = FechaUtil.obtenerNroAnioFechaActual();
		String mesHasta = FechaUtil.obtenerNroMesFechaActual();
		
		obj.setAnioDesde(anioDesde!=null?anioDesde:"");
		obj.setMesDesde(mesDesde!=null?mesDesde:"");
		obj.setAnioHasta(anioHasta!=null?anioHasta:"");
		obj.setMesHasta(mesHasta!=null?mesHasta:"");
		obj.setCodEtapa(FiseConstants.ETAPA_SOLICITUD);
		
		model.addAttribute("model", obj);
		
		return "formato12A";
	}
	
	public void cargaInicial(RenderRequest renderRequest){
		listaMes = new HashMap<Long, String>();
		listaEmpresa = new ArrayList<AdmEmpresa>();
		listaZonaBenef = new ArrayList<FiseZonaBenef>();
		
		List<AdmEmpresa> listaEmpresaBD = new ArrayList<AdmEmpresa>();
		
		listaMes = FechaUtil.cargarMapaMeses();
		logger.info("lista mes"+listaMes);
		
		listaEmpresaBD = admEmpresaService.listarAdmEmpresa();
		
		//Cargar Listas
		try {
			ThemeDisplay theme = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
			PermissionChecker permissionChecker = PermissionCheckerFactoryUtil.create(theme.getUser(), false);
			
			String cod_proceso = "FISE";    		
			String cod_funcion = "REMISION";
			String cadenaEmpresas ="";
			
			long groupId = theme.getScopeGroupId();
			String name = theme.getPortletDisplay().getRootPortletId();
			String primKey = theme.getPortletDisplay().getResourcePK();//portlet.getPortletId();
			String actionAdministrador = "ADMINISTRADOR";
			boolean bAdministrador = permissionChecker.hasPermission(groupId, name, primKey, actionAdministrador);
		
			List<Organization> lstOrgUser = OrganizationLocalServiceUtil.getUserOrganizations(theme.getUserId());
			for (Organization organization : lstOrgUser) {
				String codigo=null;
				ExpandoValueModel dato = ExpandoValueLocalServiceUtil.getValue(organization.getCompanyId(), Organization.class.getName(), ExpandoTableConstants.DEFAULT_TABLE_NAME, "cod_empresa", organization.getOrganizationId());
				if (dato != null){
					codigo=dato.getData();
				}	
				
				if(codigo == null)
					continue;
				
				if(codigo.trim().equals(""))
					continue;
				
				if(cadenaEmpresas.trim().equals(""))									
					cadenaEmpresas = "'"+codigo.trim()+"'";
				else
					cadenaEmpresas = cadenaEmpresas+",'"+codigo.trim()+"'";
				
			}
			
			if(cadenaEmpresas.trim().equals(""))
				cadenaEmpresas="'XXX'";
			
			if(bAdministrador)
				listaEmpresa = admEmpresaService.getEmpresaFise(cod_proceso,cod_funcion,"");
			else    			
				listaEmpresa = admEmpresaService.getEmpresaFise(cod_proceso,cod_funcion,cadenaEmpresas);
				
		} catch (Exception e) {
			// TODO: handle exception
		}

		
		logger.info("lista empresa"+listaEmpresa);
		listaZonaBenef = zonaBenefService.listarFiseZonaBenef();
		logger.info("lista fise"+listaZonaBenef);
		
		mapaEmpresa = new HashMap<String, String>();
		for (AdmEmpresa admEmpresa : listaEmpresaBD) {
			logger.info("codEmpresa: "+admEmpresa.getCodEmpresa()+" desccortaempresa: "+admEmpresa.getDscCortaEmpresa());
			mapaEmpresa.put(admEmpresa.getCodEmpresa(), admEmpresa.getDscCortaEmpresa());
		}

	}
	
	@ResourceMapping("grid")
  	public void grid(SessionStatus status, ResourceRequest request,ResourceResponse response) {
  		try {
  			response.setContentType("application/json");	
  			
  			HttpServletRequest req = PortalUtil.getHttpServletRequest(request);	        
	        HttpSession session = req.getSession();
  			
		    JSONArray jsonArray = new JSONArray();
  			String codEmpresa = request.getParameter("s_empresa_b");
  			String anioDesde = request.getParameter("i_anio_d");
  			String mesDesde = request.getParameter("s_mes_d");
  			String anioHasta = request.getParameter("i_anio_h");
  			String mesHasta = request.getParameter("s_mes_h");
  			String etapa = request.getParameter("s_etapa");
  			//
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
  			
  			//************************************************************************
			//Generamos la configuración de la exportación a Excel
			//************************************************************************
  			XlsWorkbookConfig xlsWorkbookConfig = new XlsWorkbookConfig();
			xlsWorkbookConfig.setName(FiseConstants.NOMBRE_EXCEL_FORMATO12A);
			List<XlsTableConfig> tables = new LinkedList<XlsTableConfig>();
			tables.add(new XlsTableConfig(listaFormato,FiseConstants.TIPO_FORMATO_12));
			List<XlsWorksheetConfig> sheets = new LinkedList<XlsWorksheetConfig>();
			sheets.add(new XlsWorksheetConfig(FiseConstants.NOMBRE_HOJA_FORMATO12A,tables));
			xlsWorkbookConfig.setSheets(sheets);
			session.setAttribute(FiseConstants.KEY_CFG_EXCEL_EXPORT,xlsWorkbookConfig);	
		    
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
		        
		        if( formato != null ){
		        	//guardamos valores en sesion
					PortletRequest pRequest = (PortletRequest) request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
					pRequest.getPortletSession().setAttribute("codEmpresa", formato.getId().getCodEmpresa(), PortletSession.APPLICATION_SCOPE);
				    pRequest.getPortletSession().setAttribute("anoPresentacion", String.valueOf(formato.getId().getAnoPresentacion()), PortletSession.APPLICATION_SCOPE);
				    pRequest.getPortletSession().setAttribute("mesPresentacion", String.valueOf(formato.getId().getMesPresentacion()), PortletSession.APPLICATION_SCOPE);
				    pRequest.getPortletSession().setAttribute("anoEjecucion", String.valueOf(formato.getId().getAnoEjecucionGasto()), PortletSession.APPLICATION_SCOPE);
				    pRequest.getPortletSession().setAttribute("mesEjecucion", String.valueOf(formato.getId().getMesEjecucionGasto()), PortletSession.APPLICATION_SCOPE);
				    pRequest.getPortletSession().setAttribute("etapa", formato.getId().getEtapa(), PortletSession.APPLICATION_SCOPE);
		        }
		        
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
		//FiseFormato12AC formato = new FiseFormato12AC();
		Formato12AMensajeBean formatoMensaje = new Formato12AMensajeBean();
		//StringBuffer sMsg = new StringBuffer();
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(request);
		
		String flagCarga = uploadPortletRequest.getParameter("flagCarga");
    	String codEmpresaNew = uploadPortletRequest.getParameter("s_empresa");
		String anioPresNew = uploadPortletRequest.getParameter("i_aniopresent");
		String mesPresNew = uploadPortletRequest.getParameter("s_mes_present");
		//String anioEjecNew = uploadPortletRequest.getParameter("i_anioejecuc");
		//String mesEjecNew = uploadPortletRequest.getParameter("s_mes_ejecuc");
		
		PortletRequest pRequest = (PortletRequest) request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
		
		String codEmpresaEdit = (String)pRequest.getPortletSession().getAttribute("codEmpresa", PortletSession.APPLICATION_SCOPE);
		String anioPresEdit = (String)pRequest.getPortletSession().getAttribute("anoPresentacion", PortletSession.APPLICATION_SCOPE);
		String mesPresEdit = (String)pRequest.getPortletSession().getAttribute("mesPresentacion", PortletSession.APPLICATION_SCOPE);
		String anioEjecEdit = (String)pRequest.getPortletSession().getAttribute("anoEjecucion", PortletSession.APPLICATION_SCOPE);
		String mesEjecEdit = (String)pRequest.getPortletSession().getAttribute("mesEjecucion", PortletSession.APPLICATION_SCOPE);
		String etapaEdit = (String)pRequest.getPortletSession().getAttribute("etapa", PortletSession.APPLICATION_SCOPE);
		FileEntry fileEntry=null;
		if( flagCarga.equals(FiseConstants.FLAG_CARGAEXCEL_FORMULARIONUEVO) ){
			fileEntry=this.subirDocumento(request, uploadPortletRequest, FiseConstants.TIPOARCHIVO_XLS);
			formatoMensaje = readExcelFile(fileEntry, themeDisplay.getUser(), flagCarga, codEmpresaNew, anioPresNew, mesPresNew, anioPresNew, mesPresNew, FiseConstants.ETAPA_SOLICITUD);
		}else if( flagCarga.equals(FiseConstants.FLAG_CARGAEXCEL_FORMULARIOMODIFICACION) ){
			fileEntry=this.subirDocumento(request, uploadPortletRequest, FiseConstants.TIPOARCHIVO_XLS);
			formatoMensaje = readExcelFile(fileEntry, themeDisplay.getUser(), flagCarga, codEmpresaEdit, anioPresEdit, mesPresEdit, anioPresNew, mesPresEdit, etapaEdit);
		}else if( flagCarga.equals(FiseConstants.FLAG_CARGATXT_FORMULARIONUEVO) ){
			fileEntry =this.subirDocumento(request, uploadPortletRequest, FiseConstants.TIPOARCHIVO_TXT);
			formatoMensaje =	readTxtFile(fileEntry, uploadPortletRequest, themeDisplay.getUser(), flagCarga, codEmpresaNew, anioPresNew, mesPresNew, anioPresNew, mesPresNew, FiseConstants.ETAPA_SOLICITUD);
		}else if( flagCarga.equals(FiseConstants.FLAG_CARGATXT_FORMULARIOMODIFICACION) ){
			fileEntry=this.subirDocumento(request, uploadPortletRequest, FiseConstants.TIPOARCHIVO_TXT);
			formatoMensaje =	readTxtFile(fileEntry, uploadPortletRequest, themeDisplay.getUser(), flagCarga, codEmpresaEdit, anioPresEdit, mesPresEdit, anioEjecEdit, mesEjecEdit, etapaEdit);
		}
		
		if( formatoMensaje.getFiseFormato12AC()!=null ){
			String codEmpresa = formatoMensaje.getFiseFormato12AC().getId().getCodEmpresa();
			String anoPresentacion = String.valueOf(formatoMensaje.getFiseFormato12AC().getId().getAnoPresentacion());
			String mesPresentacion = String.valueOf(formatoMensaje.getFiseFormato12AC().getId().getMesPresentacion());
			String anoEjecucion = String.valueOf(formatoMensaje.getFiseFormato12AC().getId().getAnoEjecucionGasto());
			String mesEjecucion = String.valueOf(formatoMensaje.getFiseFormato12AC().getId().getMesEjecucionGasto());
			String etapa = String.valueOf(formatoMensaje.getFiseFormato12AC().getId().getEtapa());
			
			pRequest.getPortletSession().setAttribute("codEmpresa", codEmpresa, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("anoPresentacion", anoPresentacion, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("mesPresentacion", mesPresentacion, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("anoEjecucion", anoEjecucion, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("mesEjecucion", mesEjecucion, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("etapa", etapa, PortletSession.APPLICATION_SCOPE);
		}else{
			if( flagCarga.equals(FiseConstants.FLAG_CARGAEXCEL_FORMULARIONUEVO) || flagCarga.equals(FiseConstants.FLAG_CARGATXT_FORMULARIONUEVO) ){
				pRequest.getPortletSession().setAttribute("codEmpresa", codEmpresaNew, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("anoPresentacion", anioPresNew, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("mesPresentacion", mesPresNew, PortletSession.APPLICATION_SCOPE);
			    //pRequest.getPortletSession().setAttribute("anoEjecucion", anioEjecEdit, PortletSession.APPLICATION_SCOPE);
			    //pRequest.getPortletSession().setAttribute("mesEjecucion", mesEjecEdit, PortletSession.APPLICATION_SCOPE);
			}else if( flagCarga.equals(FiseConstants.FLAG_CARGAEXCEL_FORMULARIOMODIFICACION) || flagCarga.equals(FiseConstants.FLAG_CARGATXT_FORMULARIOMODIFICACION) ){
				pRequest.getPortletSession().setAttribute("codEmpresa", codEmpresaEdit, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("anoPresentacion", anioPresEdit, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("mesPresentacion", mesPresEdit, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("anoEjecucion", anioEjecEdit, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("mesEjecucion", mesEjecEdit, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("etapa", etapaEdit, PortletSession.APPLICATION_SCOPE);
			}
			
		}
		if(formatoMensaje.getListaMensajeError()!=null && formatoMensaje.getListaMensajeError().size()>0){
			//System.out.println(sMsg);
			pRequest.getPortletSession().setAttribute("listaError", formatoMensaje.getListaMensajeError(), PortletSession.APPLICATION_SCOPE);
			//tambien ha generado la tira del error.
			pRequest.getPortletSession().setAttribute("mensajeInformacion", formatoMensaje.getMensajeInformacion(), PortletSession.APPLICATION_SCOPE);
		}

	}
	
	/////////////////////////////////////////////////////////////////
	///////////////////CARGA EXCEL - TXT//////////////////////
	/////////////////////////////////////////////////////////////////
	
	public FileEntry subirDocumento(PortletRequest request, UploadPortletRequest uploadPortletRequest, String tipoArchivo) {
		// TODO Auto-generated method stub
		FileEntry fileEntry=null;
		//--UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(request);
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		try {
			String[] mimeTypesXls = new String[]{"application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"};
			String[] mimeTypesTxt = new String[]{"text/plain"};
			String[] mimeTypes = new String[]{};
			long maxUploadFileSize =2097152;//bytes = 2MB
			DLFolder dlFolder = DLFolderLocalServiceUtil.getFolder(themeDisplay.getScopeGroupId(), 0, "FormatosDeclarados");
			
			if (dlFolder.getGroupId() != themeDisplay.getScopeGroupId()) {
				throw new NoSuchFolderException();
			}
			 
			String nameFileInput = null;
			if( FiseConstants.TIPOARCHIVO_XLS.equals(tipoArchivo) ){
				nameFileInput = "archivoExcel";
				mimeTypes = mimeTypesXls;
			}else if( FiseConstants.TIPOARCHIVO_TXT.equals(tipoArchivo) ){
				nameFileInput = "archivoTxt";
				mimeTypes = mimeTypesTxt;
			}else{
				throw new Exception("Archivo de formato diferente");
			}
			
			File file = uploadPortletRequest.getFile(nameFileInput);
			String mimeType = uploadPortletRequest.getContentType(nameFileInput);
			long size = uploadPortletRequest.getSize(nameFileInput);
			String sourceFileName = uploadPortletRequest.getFileName(nameFileInput);

			logger.info("MIME ARCHIVO:"+mimeType);
			if (Arrays.binarySearch(mimeTypes, mimeType) < 0) {
				throw new FileMimeTypeException(mimeType);
			}
			//solo para txt/verificar luego
			//if( FiseConstants.TIPOARCHIVO_XLS.equals(tipoArchivo) ){
				//String contenType=MimeTypesUtil.getContentType(file,nameFileInput);´
				//String contenType=MimeTypesUtil.getContentType(file);
				//logger.info("MIME CONTENT TYPE:"+contenType);
				//if (Arrays.binarySearch(mimeTypes, contenType) < 0) {
				//	throw new FileMimeTypeException(contenType);
				//}
			//}
			
						 
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
			//--String title = hoy+"-"+sourceFileName;
			int secuencia = formatoService.obtenerSecuencia();
			String title = secuencia+FiseConstants.UNDERLINE+sourceFileName;
			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(), request);
			try {
				fileEntry = DLAppLocalServiceUtil.getFileEntry(dlFolder.getGroupId(), folderId, sourceFileName);
			} catch (NoSuchFileEntryException e) {
				logger.info("el archivo no existe en el folder del repositorio");
				fileEntry=DLAppLocalServiceUtil.addFileEntry(userId, repositoryId, folderId, sourceFileName, mimeType,title, "", "Subido el "+hoy, file, serviceContext);
			}
			DLAppLocalServiceUtil.updateFileEntry(fileEntry.getUserId(), fileEntry.getFileEntryId(),sourceFileName, mimeType, title, fileEntry.getDescription(), "Actualizo estado", true, file, serviceContext);
			logger.info("Archivo subido:"+sourceFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return fileEntry;
		
	}
	
	public Formato12AMensajeBean readExcelFile(FileEntry archivo, User user, String flagCarga, String codEmpresa, 	String anioPres, String mesPres, String anioEjec, String mesEjec, String etapaEdit) {
		
		//---------------------
		//FLAG CARGA:
		//	1: para registros nuevos
		//	2: para registros modificados
		//---------------------
		Formato12AMensajeBean formatoMensaje = new Formato12AMensajeBean();
		
		InputStream is=null;
		FiseFormato12AC objeto = null;
		String sMsg = "";
		List<MensajeErrorBean> listaError = new ArrayList<MensajeErrorBean>();
		int cont = 0;
		
		try {
			if (archivo != null) {
				HSSFWorkbook libro = null;
				try {
					is=archivo.getContentStream();
					libro = new HSSFWorkbook(is);//Se lee libro xls
				} catch (Exception e1) {
					logger.warn("El archivo no es formato XLS");
					cont++;
					sMsg = sMsg + "El archivo " + archivo.getDescription() + " no corresponde al formato XLS.";
					MensajeErrorBean error = new MensajeErrorBean();
					error.setId(cont);
					error.setDescripcion("El archivo " + archivo.getDescription() + " no corresponde al formato XLS.");
					listaError.add(error);
					throw new Exception("El archivo no corresponde al formato XLS.");
				}
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
					HSSFRow filaNroEmpad = hojaF12.getRow(FiseConstants.NRO_FILA_EMPAD_FORMATO12A);				//NRO EMPADRONADOS
					HSSFRow filaNroAgent = hojaF12.getRow(FiseConstants.NRO_FILA_AGENT_FORMATO12A);				//NRO AGENTES
					HSSFRow filaDespPersonal = hojaF12.getRow(FiseConstants.NRO_FILA_DESPLPERSON_FORMATO12A);			//DESPLAZ. PERSONAL
					HSSFRow filaActivExtraord = hojaF12.getRow(FiseConstants.NRO_FILA_ACTIVEXTR_FORMATO12A);		//ACTIV. EXTRAORDINARIAS

					Formato12ACBean formulario = new Formato12ACBean();
					
					HSSFCell celdaEmpresa = filaEmpresa.getCell(FiseConstants.NRO_CELDA_EMPRESA);
					HSSFCell celdaAnio = filaAnioMes.getCell(FiseConstants.NRO_CELDA_ANIO);
					HSSFCell celdaMes = filaAnioMes.getCell(FiseConstants.NRO_CELDA_MES);
					HSSFCell nroEmpadRural = filaNroEmpad.getCell(FiseConstants.NRO_CELDA_RURAL);
					HSSFCell nroEmpadProv = filaNroEmpad.getCell(FiseConstants.NRO_CELDA_PROVINCIA);
					HSSFCell nroEmpadLima = filaNroEmpad.getCell(FiseConstants.NRO_CELDA_LIMA);
					HSSFCell nroAgentRural = filaNroAgent.getCell(FiseConstants.NRO_CELDA_RURAL);
					HSSFCell nroAgentProv = filaNroAgent.getCell(FiseConstants.NRO_CELDA_PROVINCIA);
					HSSFCell nroAgentLima = filaNroAgent.getCell(FiseConstants.NRO_CELDA_LIMA);
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
						sMsg = sMsg + "El codigo de empresa no corresponde al formato requerido."+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion("El codigo de empresa no corresponde al formato requerido.");
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_STRING == celdaAnio.getCellType()  ){
						formulario.setAnioPresent(Long.parseLong(celdaAnio.toString()));
						formulario.setAnioEjecuc(Long.parseLong(celdaAnio.toString()));
					}else{
						formulario.setAnioPresent(0);
						formulario.setAnioEjecuc(0);
						sMsg = sMsg + "El Año de presentación no corresponde al formato requerido."+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion("El Año de presentación no corresponde al formato requerido.");
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_STRING == celdaMes.getCellType()  ){
						formulario.setMesPresent(Long.parseLong(celdaMes.toString()));
						formulario.setMesEjecuc(Long.parseLong(celdaMes.toString()));
					}else{
						formulario.setMesPresent(0);
						formulario.setMesEjecuc(0);
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion("El mes de presentación no corresponde al formato requerido.");
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == nroEmpadRural.getCellType()  ){
						formulario.setNroEmpadR(new Double(nroEmpadRural.getNumericCellValue()).longValue());
					}else{
						formulario.setNroEmpadR(0);
						sMsg = sMsg + "El número de empadronados Rural no corresponde al formato requerido."+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion("El número de empadronados Rural no corresponde al formato requerido.");
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == nroEmpadProv.getCellType()  ){
						formulario.setNroEmpadP(new Double(nroEmpadProv.getNumericCellValue()).longValue());
					}else{
						formulario.setNroEmpadP(0);
						sMsg = sMsg + "El número de empadronados Provincia no corresponde al formato requerido."+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion("El número de empadronados Provincia no corresponde al formato requerido.");
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == nroEmpadLima.getCellType()  ){
						formulario.setNroEmpadL(new Double(nroEmpadLima.getNumericCellValue()).longValue());
					}else{
						formulario.setNroEmpadL(0);
						sMsg = sMsg + "El número de empadronados Lima no corresponde al formato requerido."+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion("El número de empadronados Lima no corresponde al formato requerido.");
						listaError.add(error);
					}
					//
					if( HSSFCell.CELL_TYPE_NUMERIC == nroAgentRural.getCellType()  ){
						formulario.setNroAgentR(new Double(nroAgentRural.getNumericCellValue()).longValue());
					}else{
						formulario.setNroAgentR(0);
						sMsg = sMsg + "El número de agentes Rural no corresponde al formato requerido."+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion("El número de agentes Rural no corresponde al formato requerido.");
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == nroAgentProv.getCellType()  ){
						formulario.setNroAgentP(new Double(nroAgentProv.getNumericCellValue()).longValue());
					}else{
						formulario.setNroAgentP(0);
						sMsg = sMsg + "El número de agentes Provincia no corresponde al formato requerido."+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion("El número de agentes Provincia no corresponde al formato requerido.");
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == nroAgentLima.getCellType()  ){
						formulario.setNroAgentL(new Double(nroAgentLima.getNumericCellValue()).longValue());
					}else{
						formulario.setNroAgentL(0);
						sMsg = sMsg + "El número de agentes Lima no corresponde al formato requerido."+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion("El número de agentes Lima no corresponde al formato requerido.");
						listaError.add(error);
					}
					//
					if( HSSFCell.CELL_TYPE_NUMERIC == despPersonalR.getCellType()  ){
						formulario.setDesplPersonalR(new BigDecimal(despPersonalR.getNumericCellValue()));
					}else{
						formulario.setDesplPersonalR(new BigDecimal(0.00));
						sMsg = sMsg + "El desplazamiento personal Rural no corresponde al formato requerido."+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion("El desplazamiento personal Rural no corresponde al formato requerido.");
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == despPersonalP.getCellType()  ){
						formulario.setDesplPersonalP(new BigDecimal(despPersonalP.getNumericCellValue()));
					}else{
						formulario.setDesplPersonalP(new BigDecimal(0.00));
						sMsg = sMsg + "El desplazamiento personal Provincia no corresponde al formato requerido."+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion("El desplazamiento personal Provincia no corresponde al formato requerido.");
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == despPersonalL.getCellType()  ){
						formulario.setDesplPersonalL(new BigDecimal(despPersonalL.getNumericCellValue()));
					}else{
						formulario.setDesplPersonalL(new BigDecimal(0.00));
						sMsg = sMsg + "El desplazamiento personal Lima no corresponde al formato requerido."+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion("El desplazamiento personal Lima no corresponde al formato requerido.");
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == activExtraordR.getCellType()  ){
						formulario.setActivExtraordR(new BigDecimal(activExtraordR.getNumericCellValue()));
					}else{
						formulario.setActivExtraordR(new BigDecimal(0.00));
						sMsg = sMsg + "Las actividades extraordinarias Rural no corresponde al formato requerido."+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion("Las actividades extraordinarias Rural no corresponde al formato requerido.");
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == activExtraordP.getCellType()  ){
						formulario.setActivExtraordP(new BigDecimal(activExtraordP.getNumericCellValue()));
					}else{
						formulario.setActivExtraordP(new BigDecimal(0.00));
						sMsg = sMsg + "Las actividades extraordinarias Provincia no corresponde al formato requerido."+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion("Las actividades extraordinarias Provincia no corresponde al formato requerido.");
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == activExtraordL.getCellType()  ){
						formulario.setActivExtraordL(new BigDecimal(activExtraordL.getNumericCellValue()));
					}else{
						formulario.setActivExtraordL(new BigDecimal(0.00));
						sMsg = sMsg + "Las actividades extraordinarias Lima no corresponde al formato requerido."+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion("Las actividades extraordinarias Lima no corresponde al formato requerido.");
						listaError.add(error);
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
					formulario.setTipoArchivo(FiseConstants.TIPOARCHIVO_XLS);
					formulario.setNombreArchivo(archivo.getTitle());
					//
					if( codEmpresa.equals(formulario.getCodigoEmpresa()) &&
							anioPres.equals(String.valueOf(formulario.getAnioPresent())) &&
							mesPres.equals(String.valueOf(formulario.getMesPresent())) &&
							anioEjec.equals(String.valueOf(formulario.getAnioPresent())) &&
							mesEjec.equals(String.valueOf(formulario.getMesPresent())) 
							){
						if( FiseConstants.FLAG_CARGAEXCEL_FORMULARIONUEVO.equals(flagCarga) ){
							objeto = formatoService.registrarFormato12AC(formulario);
						}else if( FiseConstants.FLAG_CARGAEXCEL_FORMULARIOMODIFICACION.equals(flagCarga) ){
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
						}
						
					}else{
						sMsg = sMsg + "El archivo cargado no corresponde a los valores del registro del formulario."+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion("El archivo cargado no corresponde a los valores del registro del formulario.");
						listaError.add(error);
					}
					
				}
					
			}
			is.close();

		} catch (Exception e) {
			logger.error("Error al leer el archivo excel.",e);
			cont++;
			MensajeErrorBean errorBean = new MensajeErrorBean();
			errorBean.setId(cont);
			errorBean.setDescripcion("Error al leer el archivo excel:");
			listaError.add(errorBean);
		}finally{
			StreamUtil.cleanUp(is);
		}
		formatoMensaje.setMensajeInformacion(sMsg);
		formatoMensaje.setFiseFormato12AC(objeto);
		if(listaError.size()>0)
			formatoMensaje.setListaMensajeError(listaError);
		
		return formatoMensaje;
	}
	
	public Formato12AMensajeBean readTxtFile(FileEntry archivo, UploadPortletRequest uploadPortletRequest, User user, String flagCarga, String codEmpresaEdit, String anioPresEdit, String mesPresEdit, String anioEjecEdit, String mesEjecEdit, String etapaEdit) {
		
		//---------------------
		//FLAG CARGA:
		//	3: para registros nuevos
		//	4: para registros modificados
		//---------------------
		Formato12AMensajeBean formatoMensaje = new Formato12AMensajeBean();
		InputStream is=null;
		FiseFormato12AC objeto = null;
		List<MensajeErrorBean> listaError = new ArrayList<MensajeErrorBean>();
		String sMsg = "";
		int cont = 0;
		List<CfgCampo> listaCampo = null;
		try{
			CfgTabla tabla = new CfgTabla();
			tabla.setIdTabla(FiseConstants.ID_TABLA_FORMATO12A);
			listaCampo = campoService.listarCamposByTabla(tabla);
			if( listaCampo != null ){
				int longitudMaxima = campoService.longitudMaximaRegistro(listaCampo);
				int posicionCodEmpresa = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_COD_EMPRESA);
				int posicionAnioPresentacion = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_ANO_PRESENTACION);
				int posicionMesPresentacion = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_MES_PRESENTACION);
				int posicionMesEjecucion = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_MES_EJECUCION);
				int posicionZonaBenef = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_ZONA_BENEFICIARIO);
				int posicionNroEmpad = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_NRO_EMPADRONADOS);
				int posicionTotalEmpad = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_TOTAL_EMPADRONADOS);
				int posicionNroAgent = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_NRO_AGENTES_AUTOR);
				int posicionTotalAgent = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_TOTAL_AGENTES);
				int posicionDesplPersonal = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_DESPLAZ_PERSONAL);
				int posicionActivExtraord = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_ACTIVID_EXTRAORD);
				
				String sCurrentLine;
				is=uploadPortletRequest.getFileAsStream("archivoTxt");
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
							sMsg = sMsg + "Los datos contenidos en el archivo no estan completos ."+FiseConstants.SALTO_LINEA;
							cont++;
							MensajeErrorBean error = new MensajeErrorBean();
							error.setId(cont);
							error.setDescripcion("Los datos contenidos en el archivo no estan completos .");
							listaError.add(error);
						}
					}else{
						sMsg = sMsg + "El archivo cargado debe contener datos para poder ser procesado."+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion("El archivo cargado debe contener datos para poder ser procesado.");
						listaError.add(error);
					}
					sCurrentLine = br.readLine();
					if( cont>3 ){
						sMsg = sMsg + "El archivo no debe contener más de 3 lineas de detalle."+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion("El archivo no debe contener más de 3 lineas de detalle.");
						listaError.add(error);
						break;
					}
				}
				
				String key1,key2,key3="";//,key4,key5,key6="";
				if( listaDetalleTxt.size()>0 ){
					key1 = listaDetalleTxt.get(0).substring(0, posicionCodEmpresa).trim();
					key2 = listaDetalleTxt.get(0).substring(posicionCodEmpresa, posicionAnioPresentacion).trim();
					key3 = listaDetalleTxt.get(0).substring(posicionAnioPresentacion, posicionMesPresentacion).trim();
					//key4 = listaDetalleTxt.get(0).substring(FiseConstants.POSICION_MES_PRESENTACION, FiseConstants.POSICION_ANIO_EJECUCION) ;
					//key5 = listaDetalleTxt.get(0).substring(FiseConstants.POSICION_ANIO_EJECUCION, FiseConstants.POSICION_MES_EJECUCION) ;
					//key6 = listaDetalleTxt.get(0).substring(FiseConstants.POSICION_MES_EJECUCION, FiseConstants.POSICION_ZONA_BENEFICIARIO) ;
					boolean process = true;
					Set<String> zonaSet = new java.util.HashSet<String>();
					for (String s : listaDetalleTxt) {
						String codEmp = s.substring(0, posicionCodEmpresa).trim();
						String anioPres = s.substring(posicionCodEmpresa, posicionAnioPresentacion).trim();
						String mesPres = s.substring(posicionAnioPresentacion, posicionMesPresentacion) ;
						//String anioEje = s.substring(FiseConstants.POSICION_MES_PRESENTACION, FiseConstants.POSICION_ANIO_EJECUCION);
						//String mesEje = s.substring(FiseConstants.POSICION_ANIO_EJECUCION, FiseConstants.POSICION_MES_EJECUCION);
						String zonaBenef = s.substring(posicionMesEjecucion, posicionZonaBenef).trim();
						if( key1.equals(codEmp) && key2.equals(anioPres) && key3.equals(mesPres) &&
								(FiseConstants.ZONABENEF_RURAL == Long.parseLong(zonaBenef) ||
								FiseConstants.ZONABENEF_PROVINCIA == Long.parseLong(zonaBenef) ||
								FiseConstants.ZONABENEF_LIMA == Long.parseLong(zonaBenef) )
								){
							if( zonaSet.contains(zonaBenef) ){
								sMsg = sMsg + "Hay registros que contienen el mismo Zona de Beneficiario."+FiseConstants.SALTO_LINEA;
								process=false;
								cont++;
								MensajeErrorBean error = new MensajeErrorBean();
								error.setId(cont);
								error.setDescripcion("Hay registros que contienen el mismo Zona de Beneficiario.");
								listaError.add(error);
								break;
							}else{
								zonaSet.add(zonaBenef);
								process=true;
							}
						}else{
							sMsg = sMsg + "Los datos contenidos en el archivo no son consistentes."+FiseConstants.SALTO_LINEA;
							process=false;
							cont++;
							MensajeErrorBean error = new MensajeErrorBean();
							error.setId(cont);
							error.setDescripcion("Los datos contenidos en el archivo no son consistentes.");
							listaError.add(error);
							break;
						}
					}
					if(process){
						Formato12ACBean formulario = new Formato12ACBean();
						//nuevamente recorremos la lista para armar los objetos
						formulario.setCodigoEmpresa(key1);
						formulario.setAnioPresent(Long.parseLong(key2));
						formulario.setMesPresent(Long.parseLong(key3));
						formulario.setAnioEjecuc(Long.parseLong(key2));
						formulario.setMesEjecuc(Long.parseLong(key3));

						if( codEmpresaEdit.equals(formulario.getCodigoEmpresa()) &&
								anioPresEdit.equals(String.valueOf(formulario.getAnioPresent())) &&
								mesPresEdit.equals(String.valueOf(formulario.getMesPresent())) &&
								anioEjecEdit.equals(String.valueOf(formulario.getAnioPresent())) &&
								mesEjecEdit.equals(String.valueOf(formulario.getMesPresent())) 
								){
							
							//
							for (String s : listaDetalleTxt) {
								String zonaBenef = s.substring(posicionMesEjecucion, posicionZonaBenef).trim();
								String nroEmpad=s.substring(posicionZonaBenef, posicionNroEmpad).trim();
								String nroAgent=s.substring(posicionTotalEmpad, posicionNroAgent).trim();
								String desplazPersonal=s.substring(posicionTotalAgent, posicionDesplPersonal).trim();
								String activExtraord=s.substring(posicionDesplPersonal, posicionActivExtraord).trim();
								FiseFormato14AD detalle = formato14Service.obtenerFormato14ADVigente(key1, Long.parseLong(key2), Long.parseLong(zonaBenef));
								if( FiseConstants.ZONABENEF_RURAL == Long.parseLong(zonaBenef) ){
									formulario.setNroEmpadR(Long.parseLong(nroEmpad));
									formulario.setNroAgentR(Long.parseLong(nroAgent));
									formulario.setDesplPersonalR(new BigDecimal(desplazPersonal));
									formulario.setActivExtraordR(new BigDecimal(activExtraord));
									if(detalle!=null){
										formulario.setCostoUnitEmpadR(detalle.getCostoUnitarioEmpadronamiento());
										formulario.setCostoUnitAgentR(detalle.getCostoUntitarioAgenteGlp());
									}else{
										formulario.setCostoUnitEmpadR(new BigDecimal(0.00));
										formulario.setCostoUnitAgentR(new BigDecimal(0.00));
									}
								}else if( FiseConstants.ZONABENEF_PROVINCIA == Long.parseLong(zonaBenef) ){
									formulario.setNroEmpadP(Long.parseLong(nroEmpad));
									formulario.setNroAgentP(Long.parseLong(nroAgent));
									formulario.setDesplPersonalP(new BigDecimal(desplazPersonal));
									formulario.setActivExtraordP(new BigDecimal(activExtraord));
									if(detalle!=null){
										formulario.setCostoUnitEmpadP(detalle.getCostoUnitarioEmpadronamiento());
										formulario.setCostoUnitAgentP(detalle.getCostoUntitarioAgenteGlp());
									}else{
										formulario.setCostoUnitEmpadP(new BigDecimal(0.00));
										formulario.setCostoUnitAgentP(new BigDecimal(0.00));
									}
								}else if( FiseConstants.ZONABENEF_LIMA == Long.parseLong(zonaBenef) ){
									formulario.setNroEmpadL(Long.parseLong(nroEmpad));
									formulario.setNroAgentL(Long.parseLong(nroAgent));
									formulario.setDesplPersonalL(new BigDecimal(desplazPersonal));
									formulario.setActivExtraordL(new BigDecimal(activExtraord));
									if(detalle!=null){
										formulario.setCostoUnitEmpadL(detalle.getCostoUnitarioEmpadronamiento());
										formulario.setCostoUnitAgentL(detalle.getCostoUntitarioAgenteGlp());
									}else{
										formulario.setCostoUnitEmpadL(new BigDecimal(0.00));
										formulario.setCostoUnitAgentL(new BigDecimal(0.00));
									}
								}
							}
							//
							formulario.setUsuario(user.getLogin());
							formulario.setTerminal(user.getLoginIP());
							formulario.setTipoArchivo(FiseConstants.TIPOARCHIVO_TXT);
							formulario.setNombreArchivo(archivo.getTitle());
							//
							
							if( FiseConstants.FLAG_CARGATXT_FORMULARIONUEVO.equals(flagCarga) ){
								objeto = formatoService.registrarFormato12AC(formulario);
							}else if( FiseConstants.FLAG_CARGATXT_FORMULARIOMODIFICACION.equals(flagCarga) ){
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
							}
						}else{
							sMsg = sMsg + "El archivo cargado no corresponde a los valores del registro del formulario."+FiseConstants.SALTO_LINEA;
							cont++;
							MensajeErrorBean error = new MensajeErrorBean();
							error.setId(cont);
							error.setDescripcion("El archivo cargado no corresponde a los valores del registro del formulario.");
							listaError.add(error);
						}
						
					}else{
						sMsg = sMsg + "Los datos contenidos en el archivo no coinciden entre ellos."+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion("Los datos contenidos en el archivo no coinciden entre ellos.");
						listaError.add(error);
					}
				}
				is.close();
			}else{
				throw new Exception("No está configurado los campos de las tablas del Formato 12A");
			}
			
		}catch (Exception e) {			   
			  //refer.setCondicion(false);				  
			String error = e.getMessage();
			sMsg = sMsg+error;	        	
			System.out.println(error);
			cont++;
			MensajeErrorBean errorBean = new MensajeErrorBean();
			errorBean.setId(cont);
			errorBean.setDescripcion("Los datos contenidos en el archivo no coinciden entre ellos.");
			listaError.add(errorBean);
			  //throw new Exception(error); 
			  			   
		  }
		//pRequest.getPortletSession().setAttribute("MensajeInformacion", sMsg, PortletSession.APPLICATION_SCOPE);
		formatoMensaje.setFiseFormato12AC(objeto);
		formatoMensaje.setMensajeInformacion(sMsg);
		if(listaError.size()>0)
			formatoMensaje.setListaMensajeError(listaError);
		
		return formatoMensaje;
	}
	
	@ResourceMapping("reporte")
	public void reporte(SessionStatus status, ResourceRequest request,ResourceResponse response) {
		try {
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();
	        
		    JSONArray jsonArray = new JSONArray();	
		    FiseFormato12AC formato = new FiseFormato12AC();
		    
		    Formato12ACBean bean = new Formato12ACBean();
		    
		    
		    String codEmpresa = request.getParameter("codEmpresa").trim();
		    String anoPresentacion = request.getParameter("anoPresentacion").trim();
		    String mesPresentacion = request.getParameter("mesPresentacion").trim();
		    String anoEjecucion = request.getParameter("anoEjecucion").trim();
		    String mesEjecucion = request.getParameter("mesEjecucion").trim();
		    String etapa = request.getParameter("etapa").trim();
		    
		    String nombreReporte = request.getParameter("nombreReporte").trim();
		    String nombreArchivo = request.getParameter("nombreArchivo").trim();
		    String tipoFormato = FiseConstants.TIPO_FORMATO_12;
		   
		    session.setAttribute("nombreReporte",nombreReporte);
		    session.setAttribute("nombreArchivo",nombreArchivo);
		    session.setAttribute("tipoFormato",tipoFormato);

		    FiseFormato12ACPK pk = new FiseFormato12ACPK();
		    pk.setCodEmpresa(codEmpresa);
	        pk.setAnoPresentacion(new Long(anoPresentacion));
	        pk.setMesPresentacion(new Long(mesPresentacion));
	        pk.setAnoEjecucionGasto(new Long(anoEjecucion));
	        pk.setMesEjecucionGasto(new Long(mesEjecucion));
	        pk.setEtapa(etapa);

	        formato = formatoService.obtenerFormato12ACByPK(pk);
	        if( formato!=null ){
	        	//setamos los valores en el bean
	        	bean = formatoService.estructurarFormato12ABeanByFiseFormato12AC(formato);
	        	bean.setDescEmpresa(mapaEmpresa.get(formato.getId().getCodEmpresa()));
	        	bean.setDescMesPresentacion(listaMes.get(formato.getId().getMesPresentacion()));
	        	bean.setDescMesEjecucion(listaMes.get(formato.getId().getMesEjecucionGasto()));
	        	//
	        	session.setAttribute("mapa", formatoService.mapearParametrosFormato12A(bean));
	        }
	        
		    response.setContentType("application/json");
		    PrintWriter pw = response.getWriter();
		    pw.write(jsonArray.toString());
		    pw.flush();
		    pw.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
