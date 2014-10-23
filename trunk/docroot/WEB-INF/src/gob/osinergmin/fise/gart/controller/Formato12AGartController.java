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
import gob.osinergmin.fise.domain.FiseFormato12AD;
import gob.osinergmin.fise.domain.FiseFormato12ADOb;
import gob.osinergmin.fise.domain.FiseFormato14AD;
import gob.osinergmin.fise.domain.FiseObservacion;
import gob.osinergmin.fise.domain.FisePeriodoEnvio;
import gob.osinergmin.fise.domain.FiseZonaBenef;
import gob.osinergmin.fise.gart.json.Formato12AGartJSON;
import gob.osinergmin.fise.gart.service.AdmEmpresaGartService;
import gob.osinergmin.fise.gart.service.CfgCampoGartService;
import gob.osinergmin.fise.gart.service.FiseObservacionGartService;
import gob.osinergmin.fise.gart.service.FisePeriodoEnvioGartService;
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
	@Autowired
	@Qualifier("fiseObservacionGartServiceImpl")
	FiseObservacionGartService observacionService;
	@Autowired
	@Qualifier("fisePeriodoEnvioGartServiceImpl")
	FisePeriodoEnvioGartService periodoService;
	
	List<FiseFormato12AC> listaFormato;
	private Map<Long,String> listaMes;
	private List<AdmEmpresa> listaEmpresa;
	private List<AdmEmpresa> listaEmpresaNew;
	private List<FiseZonaBenef> listaZonaBenef;
	private Map<String, String> mapaEmpresa;
	private List<FiseObservacion> listaFiseObservacion;
	private Map<String, String> mapaErrores;
	private List<FisePeriodoEnvio> listaPeriodoEnvio;
	private Map<Long, String> mapaZonaBenef;
	//private String flagMostrarPeriodoEjecucion;
	private List<MensajeErrorBean> listaObservaciones;
	
	@SuppressWarnings("unchecked")
	@RequestMapping
	public  String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse){
		logger.info("--- defaultView");
		PortletRequest pRequest = (PortletRequest)renderRequest.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
		Formato12AGartJSON obj = new Formato12AGartJSON();
		
		//flagMostrarPeriodoEjecucion = "";
		
		String codEmpresa = (String)pRequest.getPortletSession().getAttribute("codEmpresa", PortletSession.APPLICATION_SCOPE);
		String anioPresentacion = (String)pRequest.getPortletSession().getAttribute("anoPresentacion", PortletSession.APPLICATION_SCOPE);
		String mesPresentacion = (String)pRequest.getPortletSession().getAttribute("mesPresentacion", PortletSession.APPLICATION_SCOPE);
		String anioEjecucion = (String)pRequest.getPortletSession().getAttribute("anoEjecucion", PortletSession.APPLICATION_SCOPE);
		String mesEjecucion = (String)pRequest.getPortletSession().getAttribute("mesEjecucion", PortletSession.APPLICATION_SCOPE);
		String etapa = (String)pRequest.getPortletSession().getAttribute("etapa", PortletSession.APPLICATION_SCOPE);
		//
		String msgError = (String)pRequest.getPortletSession().getAttribute("mensajeError", PortletSession.APPLICATION_SCOPE);
		List<MensajeErrorBean> listaError = (List<MensajeErrorBean>)pRequest.getPortletSession().getAttribute("listaError", PortletSession.APPLICATION_SCOPE);
		String msgInfo = (String)pRequest.getPortletSession().getAttribute("mensajeInformacion", PortletSession.APPLICATION_SCOPE);
		
		obj.setCodEmpresa(codEmpresa!=null?codEmpresa:"");
		obj.setAnoPres(anioPresentacion!=null?anioPresentacion:"");
		obj.setMesPres(anioPresentacion!=null?mesPresentacion:"");
		obj.setAnoEjec(anioPresentacion!=null?anioEjecucion:"");
		obj.setMesEjec(anioPresentacion!=null?mesEjecucion:"");
		obj.setEtapa(etapa!=null?etapa:"");
		//
		obj.setMensajeError(msgError!=null?msgError:"");
		obj.setMensajeInfo(msgInfo!=null?msgInfo:"");
		
		pRequest.getPortletSession().setAttribute("codEmpresa", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("anoPresentacion", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("mesPresentacion", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("anoEjecucion", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("mesEjecucion", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("etapa", "", PortletSession.APPLICATION_SCOPE);
	    //
	    pRequest.getPortletSession().setAttribute("mensajeError", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("listaError", null, PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("mensajeInformacion", "", PortletSession.APPLICATION_SCOPE);
	    
		cargaInicial(renderRequest);
		model.addAttribute("listaMes", listaMes);
		model.addAttribute("listaEmpresa", listaEmpresa);
		model.addAttribute("listaEmpresaNew", listaEmpresaNew);
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
		listaEmpresaNew = new ArrayList<AdmEmpresa>();
		listaZonaBenef = new ArrayList<FiseZonaBenef>();
		
		listaFiseObservacion = new ArrayList<FiseObservacion>();
		listaPeriodoEnvio = new ArrayList<FisePeriodoEnvio>();
		
		//listaObservaciones=new ArrayList<MensajeErrorBean>();
		
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
			
			logger.info("es administrador"+bAdministrador);
			if(bAdministrador){
				logger.info("genera un item TODOS al inicio");
				AdmEmpresa admEmpresaTodos = new AdmEmpresa();
				admEmpresaTodos.setCodEmpresa(FiseConstants.ITEM_TODOS_VALUE);
				admEmpresaTodos.setDscEmpresa(FiseConstants.ITEM_TODOS_DESCRIPCION);
				listaEmpresa.add(admEmpresaTodos);
				List<AdmEmpresa> lista = admEmpresaService.getEmpresaFise(cod_proceso,cod_funcion,"");
				if( lista!=null ){
					listaEmpresa.addAll(lista);
					listaEmpresaNew.addAll(lista);
				}
				//listaEmpresa = admEmpresaService.getEmpresaFise(cod_proceso,cod_funcion,"");
			}else{
				listaEmpresa = admEmpresaService.getEmpresaFise(cod_proceso,cod_funcion,cadenaEmpresas);
				listaEmpresaNew.addAll(listaEmpresa);
			}
				
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		//cargar mapas para su consulta posterior
		logger.info("lista empresa"+listaEmpresa);
		listaZonaBenef = zonaBenefService.listarFiseZonaBenef();
		logger.info("lista fise"+listaZonaBenef);
		
		mapaEmpresa = new HashMap<String, String>();
		for (AdmEmpresa admEmpresa : listaEmpresaBD) {
			logger.info("codEmpresa: "+admEmpresa.getCodEmpresa()+" desccortaempresa: "+admEmpresa.getDscCortaEmpresa());
			mapaEmpresa.put(admEmpresa.getCodEmpresa(), admEmpresa.getDscCortaEmpresa());
		}
		
		listaFiseObservacion = observacionService.listarFiseObservacion();
		
		mapaErrores = new HashMap<String, String>();
		for (FiseObservacion error : listaFiseObservacion) {
			logger.info("codError: "+error.getIdObservacion()+" descError: "+error.getDescripcion());
			mapaErrores.put(error.getIdObservacion(), error.getDescripcion());
		}

		for (AdmEmpresa admEmpresa : listaEmpresa) {
			logger.info("codEmpresa: "+admEmpresa.getCodEmpresa()+" desccortaempresa: "+admEmpresa.getDscCortaEmpresa());
		}
		
		mapaZonaBenef = new HashMap<Long, String>();
		for (FiseZonaBenef zonaBenef : listaZonaBenef) {
			logger.info("idZona: "+zonaBenef.getIdZonaBenef()+" descZona: "+zonaBenef.getDescripcion());
			mapaZonaBenef.put(zonaBenef.getIdZonaBenef(), zonaBenef.getDescripcion());
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
  				jsonArray.put(new Formato12AGartJSON().asJSONObject(fiseFormato12AC,""));
  			}
  			
  			//************************************************************************
			//Generamos la configuración de la exportación a Excel
			//************************************************************************
  			XlsWorkbookConfig xlsWorkbookConfig = new XlsWorkbookConfig();
			xlsWorkbookConfig.setName(FiseConstants.NOMBRE_EXCEL_FORMATO12A);
			List<XlsTableConfig> tables = new LinkedList<XlsTableConfig>();
			tables.add(new XlsTableConfig(listaFormato,FiseConstants.TIPO_FORMATO_12A));
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
	public void crud(ModelMap model, SessionStatus status, ResourceRequest request,ResourceResponse response) {
 	
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
		        
		        Formato12AGartJSON obj = new Formato12AGartJSON();
		        String codigoPeriodoEnvio="";
		        String flagPeriodo="";
		        
		        if( formato != null ){
		        	//guardamos valores en sesion
					PortletRequest pRequest = (PortletRequest) request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
					pRequest.getPortletSession().setAttribute("codEmpresaEdit", formato.getId().getCodEmpresa(), PortletSession.APPLICATION_SCOPE);
				    pRequest.getPortletSession().setAttribute("anoPresentacionEdit", String.valueOf(formato.getId().getAnoPresentacion()), PortletSession.APPLICATION_SCOPE);
				    pRequest.getPortletSession().setAttribute("mesPresentacionEdit", String.valueOf(formato.getId().getMesPresentacion()), PortletSession.APPLICATION_SCOPE);
				    pRequest.getPortletSession().setAttribute("anoEjecucionEdit", String.valueOf(formato.getId().getAnoEjecucionGasto()), PortletSession.APPLICATION_SCOPE);
				    pRequest.getPortletSession().setAttribute("mesEjecucionEdit", String.valueOf(formato.getId().getMesEjecucionGasto()), PortletSession.APPLICATION_SCOPE);
				    pRequest.getPortletSession().setAttribute("etapaEdit", formato.getId().getEtapa(), PortletSession.APPLICATION_SCOPE);
				    
				    obj.setCodEmpresa(formato.getId().getCodEmpresa());
					obj.setAnoPres(String.valueOf(formato.getId().getAnoPresentacion()));
					obj.setMesPres(String.valueOf(formato.getId().getMesPresentacion()));
					obj.setAnoEjec(String.valueOf(formato.getId().getAnoEjecucionGasto()));
					obj.setMesEjec(String.valueOf(formato.getId().getMesEjecucionGasto()));
					obj.setEtapa(formato.getId().getEtapa());
				    
					model.addAttribute("model", obj);
					
				    //setear la lista de periodo correspondiente al registro
				    listaPeriodoEnvio = periodoService.listarFisePeriodoEnvioMesAnioEtapa(codEmpresa, FiseConstants.NOMBRE_FORMATO_12A);
				    JSONArray jsonArray = new JSONArray();
		  			for (FisePeriodoEnvio periodo : listaPeriodoEnvio) {
		  				JSONObject jsonObj2 = new JSONObject();
						jsonObj2.put("codigoItem", periodo.getCodigoItem());				
						jsonObj2.put("descripcionItem", periodo.getDescripcionItem());					
						jsonArray.put(jsonObj2);		
					}
		  			jsonObj.put("periodoEnvio",jsonArray);
		  			codigoPeriodoEnvio =String.valueOf(formato.getId().getAnoPresentacion())+
		        			FormatoUtil.rellenaIzquierda(String.valueOf(formato.getId().getMesPresentacion()), '0', 2)+
		        			formato.getId().getEtapa();
		        }
		        
		        for (FisePeriodoEnvio p : listaPeriodoEnvio) {
					if( codigoPeriodoEnvio.equals(p.getCodigoItem()) ){
						flagPeriodo= p.getFlagPeriodoEjecucion();
						break;
					}
				}
		        
		        JSONObject jsonent = new Formato12AGartJSON().asJSONObject(formato,flagPeriodo);
		        logger.info("jsonformato:"+jsonent);
		        jsonObj.put("formato",jsonent);
				jsonObj.put("resultado", "OK");
				
				
				
				
			}else if(tipo.equals(FiseConstants.COD_SAVE)){ 
				try {
					Formato12ACBean formulario = new Formato12ACBean();
					String codEmpresa = request.getParameter("codEmpresa");
					
					String periodoEnvio = request.getParameter("periodoEnvio");
					
					String anoPresentacion = request.getParameter("anoPresentacion");
					String mesPresentacion = request.getParameter("mesPresentacion");
					
					String flagPeriodoEjecucion = request.getParameter("flagPeriodoEjecucion");
					String anoEjecucion="";
					String mesEjecucion="";
					if( "S".equals(flagPeriodoEjecucion) ){
						anoEjecucion = request.getParameter("anoEjecucion");
						mesEjecucion = request.getParameter("mesEjecucion");
					}
					
					
					//String anoEjecucion = request.getParameter("anoEjecucion");
					//String mesEjecucion = request.getParameter("mesEjecucion");
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
					
					if( periodoEnvio.length()>6 ){
						formulario.setAnioPresent(Long.parseLong(periodoEnvio.substring(0, 4)));
						formulario.setMesPresent(Long.parseLong(periodoEnvio.substring(4, 6)));
						formulario.setEtapa(periodoEnvio.substring(6, periodoEnvio.length()));
						if( "S".equals(flagPeriodoEjecucion) ){
							formulario.setAnioEjecuc(Long.parseLong(anoEjecucion));
							formulario.setMesEjecuc(Long.parseLong(mesEjecucion));
						}else{
							formulario.setAnioEjecuc(formulario.getAnioPresent());
							formulario.setMesEjecuc(formulario.getMesPresent());
						}
					
					}
					/*formulario.setAnioPresent(Long.parseLong(anoPresentacion));
					formulario.setMesPresent(Long.parseLong(mesPresentacion));*/
					
					
					
					
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
					jsonObj.put("mensaje", e.getMessage());
					logger.error("Error al guardar los datos: "+e.getMessage());
				}   				   				
					 					 				
			}else if(tipo.equals(FiseConstants.COD_UPDATE)){
				logger.info("entro a modificar valores");
				
				try {
					
					FiseFormato12AC formato = new FiseFormato12AC();
					FiseFormato12ACPK pk = new FiseFormato12ACPK();
					String codEmpresa = request.getParameter("codEmpresa");
					
					String periodoEnvio = request.getParameter("periodoEnvio");
					
					String anoPresentacion = request.getParameter("anoPresentacion");
					String mesPresentacion = request.getParameter("mesPresentacion");
					
					String flagPeriodoEjecucion = request.getParameter("flagPeriodoEjecucion");
					String anoEjecucion="";
					String mesEjecucion="";
					if( "S".equals(flagPeriodoEjecucion) ){
						anoEjecucion = request.getParameter("anoEjecucion");
						mesEjecucion = request.getParameter("mesEjecucion");
					}
					
					//String anoEjecucion = request.getParameter("anoEjecucion");
					//String mesEjecucion = request.getParameter("mesEjecucion");
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
					
					if( periodoEnvio.length()>6 ){
						formulario.setAnioPresent(Long.parseLong(periodoEnvio.substring(0, 4)));
						formulario.setMesPresent(Long.parseLong(periodoEnvio.substring(4, 6)));
						formulario.setEtapa(periodoEnvio.substring(6, periodoEnvio.length()));
						if( "S".equals(flagPeriodoEjecucion) ){
							formulario.setAnioEjecuc(Long.parseLong(anoEjecucion));
							formulario.setMesEjecuc(Long.parseLong(mesEjecucion));
						}else{
							formulario.setAnioEjecuc(formulario.getAnioPresent());
							formulario.setMesEjecuc(formulario.getMesPresent());
						}
					}
					//formulario.setAnioPresent(Long.parseLong(anoPresentacion));
					//formulario.setMesPresent(Long.parseLong(mesPresentacion));
					
					/*pk.setCodEmpresa(codEmpresa);
			        pk.setAnoPresentacion(new Long(anoPresentacion));
			        pk.setMesPresentacion(new Long(mesPresentacion));
			        pk.setAnoEjecucionGasto(new Long(anoEjecucion));
			        pk.setMesEjecucionGasto(new Long(mesEjecucion));
			        pk.setEtapa(etapa);*/
					pk.setCodEmpresa(formulario.getCodigoEmpresa());
			        pk.setAnoPresentacion(formulario.getAnioPresent());
			        pk.setMesPresentacion(formulario.getMesPresent());
			        //pk.setAnoEjecucionGasto(new Long(anoEjecucion));
			        //pk.setMesEjecucionGasto(new Long(mesEjecucion));
			        pk.setAnoEjecucionGasto(formulario.getAnioEjecuc());
			        pk.setMesEjecucionGasto(formulario.getMesEjecuc());
			        pk.setEtapa(formulario.getEtapa());
			        
			        formato = formatoService.obtenerFormato12ACByPK(pk);
					
			        logger.info("objeto "+formato);
			        
					formatoService.modificarFormato12AC(formulario, formato);
					jsonObj.put("resultado", "OK"); 	
				} catch (Exception e) {
					jsonObj.put("resultado", "Error");
					jsonObj.put("mensaje", e.getMessage());
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
					jsonObj.put("mensaje", e.getMessage());
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
  	public void requestData(ModelMap model, SessionStatus status, ResourceRequest request,ResourceResponse response){
		try {			
  			response.setContentType("applicacion/json");
  			String codEmpresa = request.getParameter("s_empresa");
  			String anoPresentacion = request.getParameter("s_periodoenvio_present");
  			
  			listaPeriodoEnvio = periodoService.listarFisePeriodoEnvioMesAnioEtapa(codEmpresa, FiseConstants.NOMBRE_FORMATO_12A);
  			
  			JSONArray jsonArray = new JSONArray();
  			for (FisePeriodoEnvio periodo : listaPeriodoEnvio) {
  				JSONObject jsonObj = new JSONObject();
				jsonObj.put("codigoItem", periodo.getCodigoItem());				
				jsonObj.put("descripcionItem", periodo.getDescripcionItem());			
				jsonObj.put("flagPeriodoEjecucion", periodo.getFlagPeriodoEjecucion());	
				//agregar los valores
				jsonArray.put(jsonObj);		
			}
  			
  			//response.setRenderParameter("action", "exito");
  			if( listaEmpresaNew !=null && listaEmpresaNew.size()>0 ){
  				if( codEmpresa==null || codEmpresa.equals("null") ){
  					model.addAttribute("s_empresa", listaEmpresaNew.get(0));
  				}else{
  					model.addAttribute("s_empresa", codEmpresa);
  				}
  			}
  			if( listaPeriodoEnvio != null && listaPeriodoEnvio.size()>0 ){
  				if( anoPresentacion==null || anoPresentacion.equals("null") ){
  					model.addAttribute("s_periodoenvio_present", listaPeriodoEnvio.get(0));
  					//flagMostrarPeriodoEjecucion = listaPeriodoEnvio.get(0).getFlagPeriodoEjecucion();
  					//model.addAttribute("flagPeriodoEjecucion", flagMostrarPeriodoEjecucion);
  				}else{
  					model.addAttribute("s_periodoenvio_present", anoPresentacion);
  					//for (FisePeriodoEnvio p : listaPeriodoEnvio) {
					//	if( anoPresentacion.equals(p.getCodigoItem()) ){
					//		flagMostrarPeriodoEjecucion = p.getFlagPeriodoEjecucion();
					//		model.addAttribute("flagPeriodoEjecucion", flagMostrarPeriodoEjecucion);
					//		break;
					//	}
					//}
  				}
  				
  			}
  			
  		    PrintWriter pw = response.getWriter();
  		    pw.write(jsonArray.toString());
  		    pw.flush();
  		    pw.close();							
  		}catch (Exception e) {
  			// TODO: handle exception
  			e.printStackTrace();
  		}
	}
	
	@ResourceMapping("request_data2")
  	public void requestData2(SessionStatus status, ResourceRequest request,ResourceResponse response){
		try {			
  			response.setContentType("applicacion/json");
  			FiseFormato14AD detalleRuralPadre = null;
  			FiseFormato14AD detalleProvinciaPadre = null;
  			FiseFormato14AD detalleLimaPadre = null;
  			
  			String codEmpresa = request.getParameter("s_empresa");
  			String periodoEnvio = request.getParameter("s_periodoenvio_present");
  			String anoPresentacion ="";
  			if( periodoEnvio!=null && periodoEnvio.length()>4 ){
  				anoPresentacion = periodoEnvio.substring(0, 4);
  			}
  			
  			BigDecimal costoUnitEmpR = null;
  			BigDecimal costoUnitAgentR = null;
  			BigDecimal costoUnitEmpP = null;
  			BigDecimal costoUnitAgentP = null;
  			BigDecimal costoUnitEmpL = null;
  			BigDecimal costoUnitAgentL = null;
  			
  			/*listaPeriodoEnvio = periodoService.listarFisePeriodoEnvioMesAnioEtapa(codEmpresa, FiseConstants.NOMBRE_FORMATO_12A);*/
  			
  			
  			detalleRuralPadre = formato14Service.obtenerFormato14ADVigente(codEmpresa, (anoPresentacion==null || FiseConstants.BLANCO.equals(anoPresentacion))?0:Long.parseLong(anoPresentacion), FiseConstants.ZONABENEF_RURAL_COD);
  			if( detalleRuralPadre!=null ){
  				costoUnitEmpR = detalleRuralPadre.getCostoUnitarioEmpadronamiento();
  				costoUnitAgentR = detalleRuralPadre.getCostoUntitarioAgenteGlp();
  			}else{
  				costoUnitEmpR = new BigDecimal(0.00);
  				costoUnitAgentR = new BigDecimal(0.00);
  			}
  			detalleProvinciaPadre = formato14Service.obtenerFormato14ADVigente(codEmpresa, (anoPresentacion==null || FiseConstants.BLANCO.equals(anoPresentacion))?0:Long.parseLong(anoPresentacion), FiseConstants.ZONABENEF_PROVINCIA_COD);
  			if( detalleProvinciaPadre!=null ){
  				costoUnitEmpP = detalleProvinciaPadre.getCostoUnitarioEmpadronamiento();
  				costoUnitAgentP = detalleProvinciaPadre.getCostoUntitarioAgenteGlp();
  			}else{
  				costoUnitEmpP = new BigDecimal(0.00);
  				costoUnitAgentP = new BigDecimal(0.00);
  			}
  			detalleLimaPadre = formato14Service.obtenerFormato14ADVigente(codEmpresa, (anoPresentacion==null || FiseConstants.BLANCO.equals(anoPresentacion))?0:Long.parseLong(anoPresentacion), FiseConstants.ZONABENEF_LIMA_COD);
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
  			
  			//jsonObj.put("costoAgentL", costoUnitAgentL);
  			
  			for (FisePeriodoEnvio p : listaPeriodoEnvio) {
				if( periodoEnvio.equals(p.getCodigoItem()) ){
					jsonObj.put("flagPeriodoEjecucion", p.getFlagPeriodoEjecucion());
					break;
				}
			}
  			//se anade los periodo de envio
  			/*JSONArray jsonArray = new JSONArray();
  			for (FisePeriodoEnvio periodo : listaPeriodoEnvio) {
  				JSONObject jsonObj2 = new JSONObject();
				jsonObj2.put("codigoItem", periodo.getCodigoItem());				
				jsonObj2.put("descripcionItem", periodo.getDescripcionItem());					
				jsonArray.put(jsonObj2);		
			}
  			jsonObj.put("periodoEnvio",jsonArray);*/
  			//jsonArray.put(jsonObj);		
  			System.out.println(jsonObj.toString());
  			
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
		Formato12AMensajeBean formatoMensaje = new Formato12AMensajeBean();
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(request);
		
		String flagCarga = uploadPortletRequest.getParameter("flagCarga");
    	String codEmpresaNew = uploadPortletRequest.getParameter("s_empresa");
    	
    	String periodoEnvioPresNew = uploadPortletRequest.getParameter("s_periodoenvio_present");
    	String anioPresNew = "";
		String mesPresNew = "";
		/*String anioPresNew = uploadPortletRequest.getParameter("i_aniopresent");
		String mesPresNew = uploadPortletRequest.getParameter("s_mes_present");*/
    	if(periodoEnvioPresNew!=null && periodoEnvioPresNew.length()>6){
    		anioPresNew = periodoEnvioPresNew.substring(0, 4);
    		mesPresNew = periodoEnvioPresNew.substring(4, 6);
    	}
    	
		//String anioEjecNew = uploadPortletRequest.getParameter("i_anioejecuc");
		//String mesEjecNew = uploadPortletRequest.getParameter("s_mes_ejecuc");
		
		PortletRequest pRequest = (PortletRequest) request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
		
		String codEmpresaEdit = (String)pRequest.getPortletSession().getAttribute("codEmpresaEdit", PortletSession.APPLICATION_SCOPE);
		String anioPresEdit = (String)pRequest.getPortletSession().getAttribute("anoPresentacionEdit", PortletSession.APPLICATION_SCOPE);
		String mesPresEdit = (String)pRequest.getPortletSession().getAttribute("mesPresentacionEdit", PortletSession.APPLICATION_SCOPE);
		String anioEjecEdit = (String)pRequest.getPortletSession().getAttribute("anoEjecucionEdit", PortletSession.APPLICATION_SCOPE);
		String mesEjecEdit = (String)pRequest.getPortletSession().getAttribute("mesEjecucionEdit", PortletSession.APPLICATION_SCOPE);
		String etapaEdit = (String)pRequest.getPortletSession().getAttribute("etapaEdit", PortletSession.APPLICATION_SCOPE);
		FileEntry fileEntry=null;
		if( flagCarga.equals(FiseConstants.FLAG_CARGAEXCEL_FORMULARIONUEVO) ){
			fileEntry=this.subirDocumento(request, uploadPortletRequest, FiseConstants.TIPOARCHIVO_XLS);
			formatoMensaje = readExcelFile(fileEntry, themeDisplay.getUser(), flagCarga, codEmpresaNew, anioPresNew, mesPresNew, anioPresNew, mesPresNew, FiseConstants.ETAPA_SOLICITUD);
		}else if( flagCarga.equals(FiseConstants.FLAG_CARGAEXCEL_FORMULARIOMODIFICACION) ){
			fileEntry=this.subirDocumento(request, uploadPortletRequest, FiseConstants.TIPOARCHIVO_XLS);
			formatoMensaje = readExcelFile(fileEntry, themeDisplay.getUser(), flagCarga, codEmpresaEdit, anioPresEdit, mesPresEdit, anioPresEdit, mesPresEdit, etapaEdit);
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
			pRequest.getPortletSession().setAttribute("listaError", formatoMensaje.getListaMensajeError(), PortletSession.APPLICATION_SCOPE);
			pRequest.getPortletSession().setAttribute("mensajeError", formatoMensaje.getMensajeError(), PortletSession.APPLICATION_SCOPE);
		}else{
			pRequest.getPortletSession().setAttribute("mensajeInformacion", "Datos guardados satisfactoriamente.", PortletSession.APPLICATION_SCOPE);
		}
		//limpiamos las variables en sesion utilizados para la edicion de archivos de carga
		pRequest.getPortletSession().setAttribute("codEmpresaEdit", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("anoPresentacionEdit", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("mesPresentacionEdit", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("anoEjecucionEdit", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("mesEjecucionEdit", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("etapaEdit", "", PortletSession.APPLICATION_SCOPE);
		
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
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaEmpresa.getCellType()  ){
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_30)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_30));
						listaError.add(error);
					}else{
						formulario.setCodigoEmpresa("");
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_40)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_40));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_STRING == celdaAnio.getCellType()  ){
						formulario.setAnioPresent(Long.parseLong(celdaAnio.toString()));
						formulario.setAnioEjecuc(Long.parseLong(celdaAnio.toString()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaAnio.getCellType()  ){
						formulario.setAnioPresent(0);
						formulario.setAnioEjecuc(0);
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_50)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_50));
						listaError.add(error);
					}else{
						formulario.setAnioPresent(0);
						formulario.setAnioEjecuc(0);
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_60)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaEmpresa.get(FiseConstants.COD_ERROR_F12_60));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_STRING == celdaMes.getCellType()  ){
						formulario.setMesPresent(Long.parseLong(celdaMes.toString()));
						formulario.setMesEjecuc(Long.parseLong(celdaMes.toString()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaMes.getCellType()  ){
						formulario.setMesPresent(0);
						formulario.setMesEjecuc(0);
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_70)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_70));
						listaError.add(error);
					}else{
						formulario.setMesPresent(0);
						formulario.setMesEjecuc(0);
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_80)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_80));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == nroEmpadRural.getCellType()  ){
						formulario.setNroEmpadR(new Double(nroEmpadRural.getNumericCellValue()).longValue());
					}else if( HSSFCell.CELL_TYPE_BLANK == nroEmpadRural.getCellType()  ){
						formulario.setNroEmpadR(0);
					}else{
						formulario.setNroEmpadR(0);
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_90)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_90));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == nroEmpadProv.getCellType()  ){
						formulario.setNroEmpadP(new Double(nroEmpadProv.getNumericCellValue()).longValue());
					}else if( HSSFCell.CELL_TYPE_BLANK == nroEmpadProv.getCellType()  ){
						formulario.setNroEmpadP(0);
					}else{
						formulario.setNroEmpadP(0);
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_100)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_100));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == nroEmpadLima.getCellType()  ){
						formulario.setNroEmpadL(new Double(nroEmpadLima.getNumericCellValue()).longValue());
					}else if( HSSFCell.CELL_TYPE_BLANK == nroEmpadLima.getCellType()  ){
						formulario.setNroEmpadL(0);
					}else{
						formulario.setNroEmpadL(0);
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_110)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_110));
						listaError.add(error);
					}
					//
					if( HSSFCell.CELL_TYPE_NUMERIC == nroAgentRural.getCellType()  ){
						formulario.setNroAgentR(new Double(nroAgentRural.getNumericCellValue()).longValue());
					}else{
						formulario.setNroAgentR(0);
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_120)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_120));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == nroAgentProv.getCellType()  ){
						formulario.setNroAgentP(new Double(nroAgentProv.getNumericCellValue()).longValue());
					}else{
						formulario.setNroAgentP(0);
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_130)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_130));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == nroAgentLima.getCellType()  ){
						formulario.setNroAgentL(new Double(nroAgentLima.getNumericCellValue()).longValue());
					}else{
						formulario.setNroAgentL(0);
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_140)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_140));
						listaError.add(error);
					}
					//validar que siempre que ingrese un valor en la comlumna si se ingreso otro valor
					if( BigDecimal.ZERO.equals(formulario.getNroEmpadR()) && !BigDecimal.ZERO.equals(formulario.getNroAgentR()) ){
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_300)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_300));
						listaError.add(error);
					}else if( BigDecimal.ZERO.equals(formulario.getNroAgentR()) && !BigDecimal.ZERO.equals(formulario.getNroEmpadR()) ){
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_310)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_310));
						listaError.add(error);
					}
					if( BigDecimal.ZERO.equals(formulario.getNroEmpadP()) && !BigDecimal.ZERO.equals(formulario.getNroAgentP()) ){
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_320)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_320));
						listaError.add(error);
					}else if( BigDecimal.ZERO.equals(formulario.getNroAgentP()) && !BigDecimal.ZERO.equals(formulario.getNroEmpadP()) ){
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_330)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_330));
						listaError.add(error);
					}
					if( BigDecimal.ZERO.equals(formulario.getNroEmpadL()) && !BigDecimal.ZERO.equals(formulario.getNroAgentL()) ){
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_340)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_340));
						listaError.add(error);
					}else if( BigDecimal.ZERO.equals(formulario.getNroAgentL()) && !BigDecimal.ZERO.equals(formulario.getNroEmpadL()) ){
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_350)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_350));
						listaError.add(error);
					}
					
					//
					if( HSSFCell.CELL_TYPE_NUMERIC == despPersonalR.getCellType()  ){
						formulario.setDesplPersonalR(new BigDecimal(despPersonalR.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == despPersonalR.getCellType()  ){
						formulario.setDesplPersonalR(new BigDecimal(0.00));
					}else{
						formulario.setDesplPersonalR(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == despPersonalP.getCellType()  ){
						formulario.setDesplPersonalP(new BigDecimal(despPersonalP.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == despPersonalP.getCellType()  ){
						formulario.setDesplPersonalP(new BigDecimal(0.00));
					}else{
						formulario.setDesplPersonalP(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_160)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_160));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == despPersonalL.getCellType()  ){
						formulario.setDesplPersonalL(new BigDecimal(despPersonalL.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == despPersonalL.getCellType()  ){
						formulario.setDesplPersonalL(new BigDecimal(0.00));
					}else{
						formulario.setDesplPersonalL(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_170)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_170));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == activExtraordR.getCellType()  ){
						formulario.setActivExtraordR(new BigDecimal(activExtraordR.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == activExtraordR.getCellType()  ){
						formulario.setActivExtraordR(new BigDecimal(0.00));
					}else{
						formulario.setActivExtraordR(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_180)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_180));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == activExtraordP.getCellType()  ){
						formulario.setActivExtraordP(new BigDecimal(activExtraordP.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == activExtraordP.getCellType()  ){
						formulario.setActivExtraordP(new BigDecimal(0.00));
					}else{
						formulario.setActivExtraordP(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_190)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_190));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == activExtraordL.getCellType()  ){
						formulario.setActivExtraordL(new BigDecimal(activExtraordL.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == activExtraordL.getCellType()  ){
						formulario.setActivExtraordL(new BigDecimal(0.00));
					}else{
						formulario.setActivExtraordL(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_200)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_200));
						listaError.add(error);
					}
					
					if( FiseConstants.BLANCO.equals(sMsg) ){
						//obtenemos los costos unitarios del formato padre
						FiseFormato14AD detalleRuralPadre = null;
			  			FiseFormato14AD detalleProvinciaPadre = null;
			  			FiseFormato14AD detalleLimaPadre = null;
			  			
			  			detalleRuralPadre = formato14Service.obtenerFormato14ADVigente(formulario.getCodigoEmpresa(), formulario.getAnioPresent(), FiseConstants.ZONABENEF_RURAL_COD);
			  			if( detalleRuralPadre!=null ){
			  				formulario.setCostoUnitEmpadR(detalleRuralPadre.getCostoUnitarioEmpadronamiento());
			  				formulario.setCostoUnitAgentR(detalleRuralPadre.getCostoUntitarioAgenteGlp());
			  			}else{
			  				formulario.setCostoUnitEmpadR(new BigDecimal(0.00));
			  				formulario.setCostoUnitAgentR(new BigDecimal(0.00));
			  			}
			  			detalleProvinciaPadre = formato14Service.obtenerFormato14ADVigente(formulario.getCodigoEmpresa(), formulario.getAnioPresent(), FiseConstants.ZONABENEF_PROVINCIA_COD);
			  			if( detalleProvinciaPadre!=null ){
			  				formulario.setCostoUnitEmpadP(detalleProvinciaPadre.getCostoUnitarioEmpadronamiento());
			  				formulario.setCostoUnitAgentP(detalleProvinciaPadre.getCostoUntitarioAgenteGlp());
			  			}else{
			  				formulario.setCostoUnitEmpadP(new BigDecimal(0.00));
			  				formulario.setCostoUnitAgentP(new BigDecimal(0.00));
			  			}
			  			detalleLimaPadre = formato14Service.obtenerFormato14ADVigente(formulario.getCodigoEmpresa(), formulario.getAnioPresent(), FiseConstants.ZONABENEF_LIMA_COD);
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
								Long.parseLong(mesPres) == formulario.getMesPresent() &&
								anioEjec.equals(String.valueOf(formulario.getAnioPresent())) &&
								Long.parseLong(mesEjec)==formulario.getMesPresent()
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
							sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_210)+FiseConstants.SALTO_LINEA;
							cont++;
							MensajeErrorBean error = new MensajeErrorBean();
							error.setId(cont);
							error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_210));
							listaError.add(error);
						}
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
		}finally{
			StreamUtil.cleanUp(is);
		}
		formatoMensaje.setMensajeError(sMsg);
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
							sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_220)+FiseConstants.SALTO_LINEA;
							cont++;
							MensajeErrorBean error = new MensajeErrorBean();
							error.setId(cont);
							error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_220));
							listaError.add(error);
						}
					}else{
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_230)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_230));
						listaError.add(error);
					}
					sCurrentLine = br.readLine();
					if( cont>3 ){
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_240)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_240));
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
								(FiseConstants.ZONABENEF_RURAL_COD == Long.parseLong(zonaBenef) ||
								FiseConstants.ZONABENEF_PROVINCIA_COD == Long.parseLong(zonaBenef) ||
								FiseConstants.ZONABENEF_LIMA_COD == Long.parseLong(zonaBenef) )
								){
							if( zonaSet.contains(zonaBenef) ){
								sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_280)+FiseConstants.SALTO_LINEA;
								process=false;
								cont++;
								MensajeErrorBean error = new MensajeErrorBean();
								error.setId(cont);
								error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_280));
								listaError.add(error);
								break;
							}else{
								zonaSet.add(zonaBenef);
								process=true;
							}
						}else{
							sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_260)+FiseConstants.SALTO_LINEA;
							process=false;
							cont++;
							MensajeErrorBean error = new MensajeErrorBean();
							error.setId(cont);
							error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_260));
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
								Long.parseLong(mesPresEdit)==formulario.getMesPresent() &&
								anioEjecEdit.equals(String.valueOf(formulario.getAnioPresent())) &&
								Long.parseLong(mesEjecEdit)==formulario.getMesPresent() 
								){
							
							//
							for (String s : listaDetalleTxt) {
								String zonaBenef = s.substring(posicionMesEjecucion, posicionZonaBenef).trim();
								String nroEmpad=s.substring(posicionZonaBenef, posicionNroEmpad).trim();
								String nroAgent=s.substring(posicionTotalEmpad, posicionNroAgent).trim();
								String desplazPersonal=s.substring(posicionTotalAgent, posicionDesplPersonal).trim();
								String activExtraord=s.substring(posicionDesplPersonal, posicionActivExtraord).trim();
								FiseFormato14AD detalle = formato14Service.obtenerFormato14ADVigente(key1, Long.parseLong(key2), Long.parseLong(zonaBenef));
								if( FiseConstants.ZONABENEF_RURAL_COD == Long.parseLong(zonaBenef) ){
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
								}else if( FiseConstants.ZONABENEF_PROVINCIA_COD == Long.parseLong(zonaBenef) ){
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
								}else if( FiseConstants.ZONABENEF_LIMA_COD == Long.parseLong(zonaBenef) ){
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
							sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_270)+FiseConstants.SALTO_LINEA;
							cont++;
							MensajeErrorBean error = new MensajeErrorBean();
							error.setId(cont);
							error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_270));
							listaError.add(error);
						}
						
					}/*else{
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_10)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_10));
						listaError.add(error);
					}*/
				}
				is.close();
			}else{
				throw new Exception(mapaErrores.get(FiseConstants.COD_ERROR_F12_290));
			}
			
		}catch (Exception e) {			   
			  //refer.setCondicion(false);				  
			String error = e.getMessage();
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
		formatoMensaje.setFiseFormato12AC(objeto);
		formatoMensaje.setMensajeError(sMsg);
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
		    String periodoEnvio = request.getParameter("periodoEnvio").trim();
		    String flagPeriodoEjecucion = request.getParameter("flagPeriodoEjecucion");
		    String anoPresentacion = "";//request.getParameter("anoPresentacion").trim();
		    String mesPresentacion = "";//request.getParameter("mesPresentacion").trim();
		    String anoEjecucion = "";//request.getParameter("anoEjecucion").trim();
		    String mesEjecucion = "";//request.getParameter("mesEjecucion").trim();
		    String etapa = "";//request.getParameter("etapa").trim();
		    
		    String nombreReporte = request.getParameter("nombreReporte").trim();
		    String nombreArchivo = request.getParameter("nombreArchivo").trim();
		    String tipoFormato = FiseConstants.TIPO_FORMATO_12A;
		    String tipoArchivo = request.getParameter("tipoArchivo").trim();
		   
		    session.setAttribute("nombreReporte",nombreReporte);
		    session.setAttribute("nombreArchivo",nombreArchivo);
		    session.setAttribute("tipoFormato",tipoFormato);
		    session.setAttribute("tipoArchivo",tipoArchivo);

		    if( periodoEnvio.length()>6 ){
		    	anoPresentacion = periodoEnvio.substring(0, 4);
		    	mesPresentacion = periodoEnvio.substring(4, 6);
		    	etapa = periodoEnvio.substring(6, periodoEnvio.length());
		    	if( "S".equals(flagPeriodoEjecucion) ){
		    		anoEjecucion = request.getParameter("anoEjecucion").trim();
					mesEjecucion = request.getParameter("mesEjecucion").trim();
				}else{
					anoEjecucion = anoPresentacion;
					mesEjecucion = mesPresentacion;
				}
		    }
		    
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
	        	//bean.setDescMesEjecucion(listaMes.get(formato.getId().getMesEjecucionGasto()));
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
	
	/**Validacion de Formato**/
	
	@ResourceMapping("validacion")
  	public void validacion(ModelMap model, SessionStatus status, ResourceRequest request,ResourceResponse response) {
		//JSONObject jsonObj = new JSONObject();
		HttpServletRequest req = PortalUtil.getHttpServletRequest(request);	        
        HttpSession session = req.getSession();
		
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		try {
			FiseFormato12AC formato = new FiseFormato12AC();
			//List<MensajeErrorBean> listaObservaciones = new ArrayList<MensajeErrorBean>();
			listaObservaciones = new ArrayList<MensajeErrorBean>();
			JSONArray jsonArray = new JSONArray();
  			
			String codEmpresa = request.getParameter("codEmpresa").trim();
		    String periodoEnvio = request.getParameter("periodoEnvio").trim();
		    String flagPeriodoEjecucion = request.getParameter("flagPeriodoEjecucion");
		    String anoPresentacion = "";
		    String mesPresentacion = "";
		    String anoEjecucion = "";
		    String mesEjecucion = "";
		    String etapa = "";
		    
		    if( periodoEnvio.length()>6 ){
		    	anoPresentacion = periodoEnvio.substring(0, 4);
		    	mesPresentacion = periodoEnvio.substring(4, 6);
		    	etapa = periodoEnvio.substring(6, periodoEnvio.length());
		    	if( "S".equals(flagPeriodoEjecucion) ){
		    		anoEjecucion = request.getParameter("anoEjecucion").trim();
					mesEjecucion = request.getParameter("mesEjecucion").trim();
				}else{
					anoEjecucion = anoPresentacion;
					mesEjecucion = mesPresentacion;
				}
		    }
		    FiseFormato12ACPK pk = new FiseFormato12ACPK();
			pk.setCodEmpresa(codEmpresa);
			pk.setAnoPresentacion(new Long(anoPresentacion));
	        pk.setMesPresentacion(new Long(mesPresentacion));
	        pk.setAnoEjecucionGasto(new Long(anoEjecucion));
	        pk.setMesEjecucionGasto(new Long(mesEjecucion));
	        pk.setEtapa(etapa);
		        
		    formato = formatoService.obtenerFormato12ACByPK(pk);
		    if( formato!=null ){
		    	int cont=0;
		    	int i = formatoService.validarFormato12A(formato, FiseConstants.NOMBRE_FORMATO_12A, themeDisplay.getUser().getLogin(), themeDisplay.getUser().getLogin());
			    if(i==0){
			    	//se tendra que setear todos las observaciones a cada detalle de los 
			    	for (FiseFormato12AD detalle : formato.getFiseFormato12ADs()) {
						detalle.setFiseFormato12ADObs(formatoService.listarFormato12ADObByFormato12AD(detalle));
						List<FiseFormato12ADOb> listaObser = formatoService.listarFormato12ADObByFormato12AD(detalle);
						for (FiseFormato12ADOb observacion : listaObser) {
							cont++;
							MensajeErrorBean obs = new MensajeErrorBean();
							obs.setId(cont);
							/*String zonaBenef = "";
							if( FiseConstants.ZONABENEF_RURAL_COD==observacion.getId().getIdZonaBenef() ){
								zonaBenef = FiseConstants.ZONABENEF_RURAL_DESC;
							}else if( FiseConstants.ZONABENEF_PROVINCIA_COD==observacion.getId().getIdZonaBenef() ){
								zonaBenef = FiseConstants.ZONABENEF_PROVINCIA_DESC;
							}else if( FiseConstants.ZONABENEF_LIMA_COD==observacion.getId().getIdZonaBenef() ){
								zonaBenef = FiseConstants.ZONABENEF_LIMA_DESC;
							}*/
							obs.setDescZonaBenef(mapaZonaBenef.get(observacion.getId().getIdZonaBenef()));
							obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
							obs.setDescripcion(mapaErrores.get(observacion.getFiseObservacion().getIdObservacion()));
							listaObservaciones.add(obs);
						}
						
					}
			    	//model.addAttribute("listaObservaciones", listaObservaciones);
			    	for (MensajeErrorBean error : listaObservaciones) {
		  				JSONObject jsonObj = new JSONObject();
						jsonObj.put("id", error.getId());	
						jsonObj.put("descZonaBenef", error.getDescZonaBenef());
						jsonObj.put("codigo", error.getCodigo());			
						jsonObj.put("descripcion", error.getDescripcion());	
						//agregar los valores
						jsonArray.put(jsonObj);		
					}
			    			
			    	/**exportar excel*/
			    	XlsWorkbookConfig xlsWorkbookConfig = new XlsWorkbookConfig();
					xlsWorkbookConfig.setName(FiseConstants.NOMBRE_EXCEL_VALIDACION);
					List<XlsTableConfig> tables = new LinkedList<XlsTableConfig>();
					tables.add(new XlsTableConfig(listaObservaciones,FiseConstants.TIPO_FORMATO_VAL));
					List<XlsWorksheetConfig> sheets = new LinkedList<XlsWorksheetConfig>();
					sheets.add(new XlsWorksheetConfig(FiseConstants.NOMBRE_HOJA_VALIDACION,tables));
					xlsWorkbookConfig.setSheets(sheets);
					session.setAttribute(FiseConstants.KEY_CFG_EXCEL_EXPORT,xlsWorkbookConfig);
			    	/***/
			    	
			    	
			    	//jsonObj.put("resultado", "OK");
			    }else{
			    	//jsonObj.put("resultado", "Error");
			    }
		    }
		    
		    response.setContentType("application/json");
		    PrintWriter pw = response.getWriter();
		    //pw.write(jsonObj.toString());
		    pw.write(jsonArray.toString());
		    pw.flush();
		    pw.close();
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@ResourceMapping("reporteValidacion")
	public void reporteValidacion(SessionStatus status, ResourceRequest request,ResourceResponse response) {
		try {
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();
	        
		    JSONArray jsonArray = new JSONArray();	
		    
		    String nombreReporte = request.getParameter("nombreReporte").trim();
		    String nombreArchivo = request.getParameter("nombreArchivo").trim();
		    String tipoFormato = FiseConstants.TIPO_FORMATO_VAL;
		    String tipoArchivo = request.getParameter("tipoArchivo").trim();
		   
		    session.setAttribute("nombreReporte",nombreReporte);
		    session.setAttribute("nombreArchivo",nombreArchivo);
		    session.setAttribute("tipoFormato",tipoFormato);
		    session.setAttribute("tipoArchivo",tipoArchivo);

		    if( listaObservaciones!=null ){
		    	session.setAttribute("lista", listaObservaciones);
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
