package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.Formato12ACBean;
import gob.osinergmin.fise.bean.Formato12BCBean;
import gob.osinergmin.fise.bean.Formato12CCBean;
import gob.osinergmin.fise.bean.Formato12DCBean;
import gob.osinergmin.fise.bean.Formato13ACBean;
import gob.osinergmin.fise.bean.Formato13ADReportBean;
import gob.osinergmin.fise.bean.Formato14ACBean;
import gob.osinergmin.fise.bean.Formato14BCBean;
import gob.osinergmin.fise.bean.Formato14CBean;
import gob.osinergmin.fise.bean.Formato14CReportBean;
import gob.osinergmin.fise.bean.LiquidacionBean;
import gob.osinergmin.fise.bean.MensajeErrorBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.CfgTabla;
import gob.osinergmin.fise.domain.FiseFormato12AC;
import gob.osinergmin.fise.domain.FiseFormato12ACPK;
import gob.osinergmin.fise.domain.FiseFormato12AD;
import gob.osinergmin.fise.domain.FiseFormato12ADOb;
import gob.osinergmin.fise.domain.FiseFormato12BC;
import gob.osinergmin.fise.domain.FiseFormato12BCPK;
import gob.osinergmin.fise.domain.FiseFormato12BD;
import gob.osinergmin.fise.domain.FiseFormato12BDOb;
import gob.osinergmin.fise.domain.FiseFormato12CC;
import gob.osinergmin.fise.domain.FiseFormato12CCPK;
import gob.osinergmin.fise.domain.FiseFormato12CD;
import gob.osinergmin.fise.domain.FiseFormato12CDOb;
import gob.osinergmin.fise.domain.FiseFormato12DC;
import gob.osinergmin.fise.domain.FiseFormato12DCPK;
import gob.osinergmin.fise.domain.FiseFormato12DD;
import gob.osinergmin.fise.domain.FiseFormato12DDOb;
import gob.osinergmin.fise.domain.FiseFormato13AC;
import gob.osinergmin.fise.domain.FiseFormato13ACPK;
import gob.osinergmin.fise.domain.FiseFormato13AD;
import gob.osinergmin.fise.domain.FiseFormato13ADOb;
import gob.osinergmin.fise.domain.FiseFormato14AC;
import gob.osinergmin.fise.domain.FiseFormato14ACPK;
import gob.osinergmin.fise.domain.FiseFormato14AD;
import gob.osinergmin.fise.domain.FiseFormato14ADOb;
import gob.osinergmin.fise.domain.FiseFormato14BC;
import gob.osinergmin.fise.domain.FiseFormato14BCPK;
import gob.osinergmin.fise.domain.FiseFormato14BD;
import gob.osinergmin.fise.domain.FiseFormato14BDOb;
import gob.osinergmin.fise.domain.FiseFormato14CC;
import gob.osinergmin.fise.domain.FiseFormato14CD;
import gob.osinergmin.fise.domain.FiseFormato14CDOb;
import gob.osinergmin.fise.domain.FiseGrupoInformacion;
import gob.osinergmin.fise.gart.service.CfgTablaGartService;
import gob.osinergmin.fise.gart.service.FiseGrupoInformacionGartService;
import gob.osinergmin.fise.gart.service.FiseLiquidacionService;
import gob.osinergmin.fise.gart.service.Formato12AGartService;
import gob.osinergmin.fise.gart.service.Formato12BGartService;
import gob.osinergmin.fise.gart.service.Formato12CGartService;
import gob.osinergmin.fise.gart.service.Formato12DGartService;
import gob.osinergmin.fise.gart.service.Formato13AGartService;
import gob.osinergmin.fise.gart.service.Formato14AGartService;
import gob.osinergmin.fise.gart.service.Formato14BGartService;
import gob.osinergmin.fise.gart.service.Formato14CGartService;
import gob.osinergmin.fise.util.FormatoUtil;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;


@Controller("liquidacionController")
@RequestMapping("VIEW")
public class LiquidacionController {
	
	Logger logger = Logger.getLogger(LiquidacionController.class);
	
	@Autowired
	@Qualifier("fiseLiquidacionServiceImpl")
	private FiseLiquidacionService liquidacionService;
	
	@Autowired
	@Qualifier("fiseUtil")
	private FiseUtil fiseUtil;	
	
	@Autowired
	@Qualifier("fiseGrupoInformacionGartServiceImpl")
	private FiseGrupoInformacionGartService fiseGrupoInformacionService;
	
	@Autowired
	@Qualifier("formato12AGartServiceImpl")
	private Formato12AGartService formatoService12A;
	
	@Autowired
	@Qualifier("formato12BGartServiceImpl")
	private Formato12BGartService formatoService12B;
	
	@Autowired
	@Qualifier("formato12CGartServiceImpl")
	private Formato12CGartService formatoService12C;
	
	@Autowired
	@Qualifier("formato12DGartServiceImpl")
	private Formato12DGartService formatoService12D;
	
	@Autowired
	@Qualifier("formato13AGartServiceImpl")
	private Formato13AGartService formatoService13A;
	
	@Autowired
	@Qualifier("formato14AGartServiceImpl")
	private Formato14AGartService formatoService14A;
	
	@Autowired
	@Qualifier("formato14BGartServiceImpl")
	private Formato14BGartService formatoService14B;
	
	@Autowired
	@Qualifier("formato14CGartServiceImpl")
	private Formato14CGartService formatoService14C;
	
	
	@Autowired
	@Qualifier("cfgTablaGartServiceImpl")
	private CfgTablaGartService tablaService;
	
	
	
	private Map<String, String> mapaEmpresa;
	private Map<String, String> mapaErrores;
	private Map<String, String> mapaSectorTipico;
	private Map<Long, String> mapaEtapaEjecucion;
	
	
	private List<MensajeErrorBean> listaObs12A;
	private List<MensajeErrorBean> listaObs12B;
	private List<MensajeErrorBean> listaObs12C;
	private List<MensajeErrorBean> listaObs12D;
	private List<MensajeErrorBean> listaObs13A;
	private List<MensajeErrorBean> listaObs14A;
	private List<MensajeErrorBean> listaObs14B;
	private List<MensajeErrorBean> listaObs14C;	
	
	private List<Formato13ADReportBean> listaZonas13A;
	
	
	@ModelAttribute("liquidacionBean")
    public LiquidacionBean listLiquidacionBean() {
		LiquidacionBean comman  = new LiquidacionBean();
        return comman;	        
    }

	
	@RequestMapping
	public String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse,
			     @ModelAttribute("liquidacionBean")LiquidacionBean l){
        try {           	
    		if(l.getListaEmpresas()==null){
    			logger.info("Lista de empresa es null:  " +renderRequest); 
    			l.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));
    		}     		
    		l.setAdmin(fiseUtil.esAdministrador(renderRequest));
    		
    		l.setListaGrupoInf(fiseGrupoInformacionService.listarGrupoInformacion(FiseConstants.MENSUAL)); 
    		
    		mapaEmpresa = fiseUtil.getMapaEmpresa();
    		
    		mapaErrores = fiseUtil.getMapaErrores();
    		
    		mapaSectorTipico = fiseUtil.getMapaSectorTipico();
    		
    		mapaEtapaEjecucion = fiseUtil.getMapaEtapaEjecucion();
    		
    		model.addAttribute("model", l);
    		
		} catch (Exception e) {
			logger.info("Ocurrio un errror al caragar la pagina liquidacion"); 
			e.printStackTrace();
		}		
		return "liquidacion";
	}
	
	
	@ResourceMapping("busquedaLiquidacion")
  	public void busqueda(ResourceRequest request,ResourceResponse response,
  			 @ModelAttribute("liquidacionBean")LiquidacionBean l){
		
		try{
			response.setContentType("application/json");
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);	
			PortletRequest pRequest = (PortletRequest) request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
			
			String codEmpresa = l.getCodEmpresaBusq();				
			String optionFormato = l.getOptionFormato();
			String idGrupoInf = l.getGrupoInfBusq();
			String flagBusq = l.getFlagBusq();
			
			long idGrupo=0;
			
			String usuario = themeDisplay.getUser().getLogin();
		    String terminal = themeDisplay.getUser().getLoginIP();
		    
			String data ="";			
			logger.info("codigo empresa "+ codEmpresa);  			
  			logger.info("id Grupo inf "+ idGrupoInf);  	 			
  			logger.info("Option formato "+ optionFormato);
  			logger.info("flag busqueda "+ flagBusq);
  			
  			if(FormatoUtil.isNotBlank(l.getGrupoInfBusq())){ 
		    	idGrupo = new Long(idGrupoInf);
		    }
  			List<LiquidacionBean> lista =liquidacionService.listarLiquidaciones(codEmpresa, 
  					idGrupo, usuario, terminal,flagBusq);		
  			
  			logger.info("tamaño de la lista envio Defin..   :"+lista.size());
  			
  			List<LiquidacionBean> listaLiqui = new ArrayList<LiquidacionBean>();
  			
  			for(LiquidacionBean not : lista){    				
  				not.setDesEmpresa(mapaEmpresa.get(not.getCodEmpresa()));
  				not.setDesMes(fiseUtil.getMapaMeses().get(Long.valueOf(not.getMesPres())));
  				if(!"00".equals(not.getMesEjec())){ 
  					not.setDesMesEje(fiseUtil.getMapaMeses().get(Long.valueOf(not.getMesEjec())));   		
  				}else{
  					not.setDesMesEje("---");
  				}
  				listaLiqui.add(not);
  			} 			
  			data = toStringListJSON(listaLiqui);
  			logger.info("arreglo json:"+data);
  			PrintWriter pw = response.getWriter();
  			pw.write(data);
  			pw.flush();
  			pw.close(); 
  			pRequest.getPortletSession().setAttribute("listaLiquidacion", listaLiqui, PortletSession.APPLICATION_SCOPE);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}	
	
	private String toStringListJSON(List<LiquidacionBean> lista) {
		Serializer serializer = new JsonSerializer();
		Object result = serializer.serialize(lista);
		String data = String.valueOf(result);
		return data;
	}
	
	@ResourceMapping("cargarGrupoInformacion")
  	public void cargaGrupoInformacion(ModelMap model, ResourceRequest request,ResourceResponse response,
  			 @ModelAttribute("liquidacionBean")LiquidacionBean l){
		try {			
  			response.setContentType("applicacion/json");
  			String tipoFormato = l.getOptionFormato();
  			
  			logger.info("Codigo grupo inf. para cargar grupo de infor.:  "+tipoFormato);
  			
  			List<FiseGrupoInformacion> listaGrupoInf = fiseGrupoInformacionService.listarGrupoInformacion(tipoFormato);
  			logger.info("Tamaño de lista de grupo inf:  "+listaGrupoInf.size()); 
  			JSONArray jsonArray = new JSONArray();
  			for (FiseGrupoInformacion grupo : listaGrupoInf) {
  				JSONObject jsonObj = new JSONObject();
				jsonObj.put("codigoItem", grupo.getIdGrupoInformacion());				
				jsonObj.put("descripcionItem", grupo.getDescripcion());				
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
	
	@ResourceMapping("eliminarLiquidacion")
	public void eliminarLiquidacion(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("liquidacionBean")LiquidacionBean l) { 	
		
		JSONObject jsonObj = new JSONObject();
		try {				
			logger.info("Entrando a eliminar un registro liquidacion"); 			
			logger.info("Codigo correlativo:  "+ l.getCorrelativo());
     			
			logger.info("Enviando el formulario al service");
			
			String valor = liquidacionService.eliminarDatosLiquidacion(new Long(l.getCorrelativo()));
			if(valor.equals("1")){ 
				jsonObj.put("resultado", "OK");	   	
			}else{
				jsonObj.put("resultado", "Error");	
			}
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();
			pw.write(jsonObj.toString());
			pw.flush();
			pw.close();				
		} catch (Exception e) {
			e.printStackTrace();				
			logger.error("Error al eliminar los datos de liquidacion: "+e.getMessage());
		}  			
	}		
	
	@SuppressWarnings("unchecked")
	@ResourceMapping("preparaLiquidacionFormatos")
	public void preparaLiquidacion(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("liquidacionBean")LiquidacionBean l) { 	
		
		JSONObject jsonObj = new JSONObject();
		try {				
			String valor = "0";
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);	
			PortletRequest pRequest = (PortletRequest) request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
						
			String usuario = themeDisplay.getUser().getLogin();
		    String terminal = themeDisplay.getUser().getLoginIP();	
		    
		    List<LiquidacionBean> lista = (List<LiquidacionBean>)pRequest.getPortletSession().getAttribute("listaLiquidacion", 
		    		PortletSession.APPLICATION_SCOPE);
		    
		    if(lista!=null && lista.size()>0){
		        valor = liquidacionService.prepararLiquidacion(lista, usuario, terminal);
		    }		
			if(valor.equals("1")){ 
				jsonObj.put("resultado", "OK");	   	
			}else{
				jsonObj.put("resultado", "Error");	
			}
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();
			pw.write(jsonObj.toString());
			pw.flush();
			pw.close();
			pRequest.getPortletSession().setAttribute("listaLiquidacion", null, PortletSession.APPLICATION_SCOPE);
		} catch (Exception e) {
			e.printStackTrace();				
			logger.error("Error al preparar datos de liquidacion de formatos: "+e.getMessage());
		}  			
	}		
	
	@SuppressWarnings("unchecked")
	@ResourceMapping("liquidarFormatos")
	public void lLiquidarFormato(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("liquidacionBean")LiquidacionBean l) { 	
		
		JSONObject jsonObj = new JSONObject();
		try {		
			String valor ="0";
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);	
			PortletRequest pRequest = (PortletRequest) request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
			
			String usuario = themeDisplay.getUser().getLogin();
		    String terminal = themeDisplay.getUser().getLoginIP();
			
		    List<LiquidacionBean> lista = (List<LiquidacionBean>)pRequest.getPortletSession().getAttribute("listaLiquidacion", 
		    		PortletSession.APPLICATION_SCOPE);
		    if(lista!=null && lista.size()>0){
		        valor = liquidacionService.liquidarFormatos(lista, usuario, terminal);
		    }	
			
			if(valor.equals("1")){ 
				jsonObj.put("resultado", "OK");	   	
			}else{
				jsonObj.put("resultado", "Error");	
			}
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();
			pw.write(jsonObj.toString());
			pw.flush();
			pw.close();	
			pRequest.getPortletSession().setAttribute("listaLiquidacion", null, PortletSession.APPLICATION_SCOPE);
		} catch (Exception e) {
			e.printStackTrace();				
			logger.error("Error al liquidar formatos: "+e.getMessage());
		}  			
	}		
	
	
	@ResourceMapping("verObservacionesFormatos")
	public void verObservacionesFormatos(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("liquidacionBean")LiquidacionBean l) {		
		try {	
			JSONArray jsonArray = null;			
			logger.info("Entrando a ver observaciones de cada formato"); 			
			logger.info("Codigo empresa:  "+ l.getCodEmpresa()); 
			logger.info("anioPres:  "+ l.getAnioPres());	
			logger.info("mespres:  "+ l.getMesPres());	
			logger.info("etapa:  "+ l.getEtapa());	
			logger.info("anioEjec:  "+ l.getAnioEjec());	
			logger.info("mesEjec:  "+ l.getMesEjec());	
			logger.info("anioIniVig:  "+ l.getAnioIniVig());	
			logger.info("anioFinVig:  "+ l.getAnioFinVig());	
			logger.info("formato:  "+ l.getFormato());	
		
			if(FiseConstants.NOMBRE_FORMATO_12A.equals(l.getFormato())){ 
				jsonArray = new JSONArray();
				FiseFormato12ACPK pk = new FiseFormato12ACPK();
				pk.setCodEmpresa(l.getCodEmpresa());
				pk.setAnoPresentacion(new Long(l.getAnioPres()));
				pk.setMesPresentacion(new Long(l.getMesPres()));
				pk.setAnoEjecucionGasto(new Long(l.getAnioEjec()));
				pk.setMesEjecucionGasto(new Long(l.getMesEjec()));
				pk.setEtapa(l.getEtapa());  	  			        
				FiseFormato12AC formato12A = formatoService12A.obtenerFormato12ACByPK(pk);		        
				cargarListaObservaciones12A(formato12A.getFiseFormato12ADs());	
				for (MensajeErrorBean error : listaObs12A) {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("id", error.getId());	
					jsonObj.put("descZonaBenef", error.getDescZonaBenef());
					jsonObj.put("codigo", error.getCodigo());			
					jsonObj.put("descripcion", error.getDescripcion());					
					jsonArray.put(jsonObj);		
				}

			}else if(FiseConstants.NOMBRE_FORMATO_12B.equals(l.getFormato())){ 
				jsonArray = new JSONArray();
				FiseFormato12BCPK pk=new FiseFormato12BCPK();
				pk.setCodEmpresa(l.getCodEmpresa());
				pk.setAnoPresentacion(new Integer(l.getAnioPres()));
				pk.setMesPresentacion(new Integer(l.getMesPres()));
				pk.setAnoEjecucionGasto(new Integer(l.getAnioEjec()));
				pk.setMesEjecucionGasto(new Integer(l.getMesEjec()));
				pk.setEtapa(l.getEtapa());  
				FiseFormato12BC formato12B =formatoService12B.getFormatoCabeceraById(pk);      
				cargarListaObservaciones12B(formato12B.getListaDetalle12BDs());
				for (MensajeErrorBean error : listaObs12B) {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("id", error.getId());	
					jsonObj.put("descZonaBenef", error.getDescZonaBenef());
					jsonObj.put("codigo", error.getCodigo());			
					jsonObj.put("descripcion", error.getDescripcion());						
					jsonArray.put(jsonObj);		
				}

			}else if(FiseConstants.NOMBRE_FORMATO_12C.equals(l.getFormato())){ 
				jsonArray = new JSONArray();
				FiseFormato12CCPK pk = new FiseFormato12CCPK();
				pk.setCodEmpresa(l.getCodEmpresa());
				pk.setAnoPresentacion(new Long(l.getAnioPres()));
				pk.setMesPresentacion(new Long(l.getMesPres()));
				pk.setEtapa(l.getEtapa()); 
				FiseFormato12CC formato12C = formatoService12C.obtenerFormato12CCByPK(pk);	        
				cargarListaObservaciones12C(formato12C.getFiseFormato12CDs());
				for (MensajeErrorBean error : listaObs12C) {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("id", error.getId());
					jsonObj.put("nroItemEtapa", error.getNroItemEtapa());
					jsonObj.put("codigo", error.getCodigo());
					jsonObj.put("descripcion", error.getDescripcion());
					jsonObj.put("descEtapaEjecucion", error.getDescEtapaEjecucion());					
					jsonArray.put(jsonObj);
				}		        

			}else if(FiseConstants.NOMBRE_FORMATO_12D.equals(l.getFormato())){ 
				jsonArray = new JSONArray();
				FiseFormato12DCPK pk = new FiseFormato12DCPK();
				pk.setCodEmpresa(l.getCodEmpresa());
				pk.setAnoPresentacion(new Long(l.getAnioPres()));
				pk.setMesPresentacion(new Long(l.getMesPres()));
				pk.setEtapa(l.getEtapa()); 
				FiseFormato12DC formato12D = formatoService12D.obtenerFormato12DCByPK(pk);  
				cargarListaObservaciones12D(formato12D.getFiseFormato12DDs());
				for (MensajeErrorBean error : listaObs12D) {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("id", error.getId());
					jsonObj.put("nroItemEtapa", error.getNroItemEtapa());
					jsonObj.put("codigo", error.getCodigo());
					jsonObj.put("descripcion", error.getDescripcion());
					jsonObj.put("descEtapaEjecucion", error.getDescEtapaEjecucion());			
					jsonArray.put(jsonObj);
				}
				
			}else if(FiseConstants.NOMBRE_FORMATO_13A.equals(l.getFormato())){ 
				jsonArray = new JSONArray();
				FiseFormato13ACPK pk = new FiseFormato13ACPK();
				pk.setCodEmpresa(l.getCodEmpresa());
				pk.setAnoPresentacion(new Long(l.getAnioPres()));
				pk.setMesPresentacion(new Long(l.getMesPres()));
				pk.setEtapa(l.getEtapa());  	 
				FiseFormato13AC formato13A = formatoService13A.obtenerFormato13ACByPK(pk);	      
				cargarListaObservaciones13A(formato13A.getFiseFormato13ADs());
				for (MensajeErrorBean error : listaObs13A) {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("id", error.getId());
					jsonObj.put("descZonaBenef", error.getDescZonaBenef());
					jsonObj.put("codigo", error.getCodigo());
					jsonObj.put("descripcion", error.getDescripcion());
					jsonObj.put("descSectorTipico", error.getDescCodSectorTipico());					
					jsonArray.put(jsonObj);
				}

			}else if(FiseConstants.NOMBRE_FORMATO_14A.equals(l.getFormato())){ 
				jsonArray = new JSONArray();
				FiseFormato14ACPK pk = new FiseFormato14ACPK();
				pk.setCodEmpresa(l.getCodEmpresa());
				pk.setAnoPresentacion(new Long(l.getAnioPres()));
				pk.setMesPresentacion(new Long(l.getMesPres()));
				pk.setAnoInicioVigencia(new Long(l.getAnioIniVig()));
				pk.setAnoFinVigencia(new Long(l.getAnioFinVig()));
				pk.setEtapa(l.getEtapa());  				        
				FiseFormato14AC formato14A = formatoService14A.obtenerFormato14ACByPK(pk);	      
				cargarListaObservaciones14A(formato14A.getFiseFormato14ADs());
				for (MensajeErrorBean error : listaObs14A) {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("id", error.getId());	
					jsonObj.put("descZonaBenef", error.getDescZonaBenef());
					jsonObj.put("codigo", error.getCodigo());			
					jsonObj.put("descripcion", error.getDescripcion());					
					jsonArray.put(jsonObj);		
				}

			}else if(FiseConstants.NOMBRE_FORMATO_14B.equals(l.getFormato())){
				jsonArray = new JSONArray();
				FiseFormato14BCPK pk = new FiseFormato14BCPK();
				pk.setCodEmpresa(l.getCodEmpresa());
				pk.setAnoPresentacion(new Long(l.getAnioPres()));
				pk.setMesPresentacion(new Long(l.getMesPres()));
				pk.setAnoInicioVigencia(new Long(l.getAnioIniVig()));
				pk.setAnoFinVigencia(new Long(l.getAnioFinVig()));
				pk.setEtapa(l.getEtapa());  						        
				FiseFormato14BC formato14B = formatoService14B.obtenerFormato14BCByPK(pk);	       
				cargarListaObservaciones14B(formato14B.getFiseFormato14BDs());	
				for (MensajeErrorBean error : listaObs14B) {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("id", error.getId());	
					jsonObj.put("descZonaBenef", error.getDescZonaBenef());
					jsonObj.put("codigo", error.getCodigo());			
					jsonObj.put("descripcion", error.getDescripcion());					
					jsonArray.put(jsonObj);		
				}

			}else if(FiseConstants.NOMBRE_FORMATO_14C.equals(l.getFormato())){
				jsonArray = new JSONArray();
				Formato14CBean f14C = new Formato14CBean();  					
				f14C.setCodEmpresa(l.getCodEmpresa());
				f14C.setAnioPres(l.getAnioPres());
				f14C.setMesPres(l.getMesPres());
				f14C.setAnoIniVigencia(l.getAnioIniVig());
				f14C.setAnoFinVigencia(l.getAnioFinVig());
				f14C.setEtapa(l.getEtapa());				
				FiseFormato14CC formato14C = formatoService14C.obtenerFiseFormato14CC(f14C);			
				cargarListaObservaciones14C(formato14C.getListaDetalle14cDs());
				for (MensajeErrorBean error : listaObs14C) {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("id", error.getId());	
					jsonObj.put("descZonaBenef", error.getDescZonaBenef());
					jsonObj.put("codigo", error.getCodigo());			
					jsonObj.put("descripcion", error.getDescripcion());					
					jsonArray.put(jsonObj);		
				}		    
			}	   
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();		  
			logger.info(jsonArray.toString());
			pw.write(jsonArray.toString());
			pw.flush();
			pw.close();	    
		 } catch (Exception e) {
			logger.error("Error al ver observaciones del formato: "+e); 
			e.printStackTrace();
		}finally{
			if(listaObs12A!=null){
				listaObs12A=null;	
			}
			if(listaObs12B!=null){
				listaObs12B=null;	
			}
			if(listaObs12C!=null){
				listaObs12C=null;	
			}
			if(listaObs12D!=null){
				listaObs12D=null;	
			}
			if(listaObs13A!=null){
				listaObs13A=null;	
			}
			if(listaObs14A!=null){
				listaObs14A=null;	
			}
			if(listaObs14B!=null){
				listaObs14B=null;	
			}
			if(listaObs14C!=null){
				listaObs14C=null;	
			}						
		}
    }	
	
	
	@ResourceMapping("verFormatosReporte")
	public void verFormatos(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("liquidacionBean")LiquidacionBean l) {		
		try {	
			
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);	
			
			logger.info("Entrando a ver reporte de cada formato"); 			
			logger.info("Codigo empresa:  "+ l.getCodEmpresa()); 
			logger.info("anioPres:  "+ l.getAnioPres());	
			logger.info("mespres:  "+ l.getMesPres());	
			logger.info("etapa:  "+ l.getEtapa());	
			logger.info("anioEjec:  "+ l.getAnioEjec());	
			logger.info("mesEjec:  "+ l.getMesEjec());	
			logger.info("anioIniVig:  "+ l.getAnioIniVig());	
			logger.info("anioFinVig:  "+ l.getAnioFinVig());	
			logger.info("formato:  "+ l.getFormato());				
			
			String usuario = themeDisplay.getUser().getLogin();
		    String terminal = themeDisplay.getUser().getLoginIP();
		    String email =  themeDisplay.getUser().getEmailAddress(); 
		    Map<String, Object> mapa =null;	
		    String directorio =  "";	
		    String nombreReporte = "";
		    boolean valorReporte = false; 
		    String rutaImg = session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg");
		   
		    JSONObject jsonObj = new JSONObject();	   
		    
		    String tipoFormato = "FORMATO "+l.getFormato();
		    String tipoArchivo = "3";//PDF		   
		    session.setAttribute("tipoFormato",tipoFormato);
		    session.setAttribute("tipoArchivo",tipoArchivo);		
		
			if(FiseConstants.NOMBRE_FORMATO_12A.equals(l.getFormato())){ 
				nombreReporte = "formato12A";	  	  			       
				directorio =  "/reports/"+nombreReporte+".jasper";
				mapa = parametros12A(l.getCodEmpresa(), l.getAnioPres(), l.getMesPres(), 
						l.getAnioEjec(), l.getMesEjec(), l.getEtapa(), rutaImg, usuario, terminal, email);
				if(mapa!=null){
					logger.info("Map diferente de null"); 
					File reportFile12A = new File(session.getServletContext().getRealPath(directorio));
					logger.info("file diferente de null"); 
					byte[] bytes12A = null;		  	  			       
					bytes12A = JasperRunManager.runReportToPdf(reportFile12A.getPath(), mapa, new JREmptyDataSource());
					logger.info("Tamaño del arreglo de bytes>>>>>"+bytes12A.length); 
					if (bytes12A != null) {	  	  			    	 
						session.setAttribute("bytesFormato", bytes12A);
						valorReporte =true;
					}
				}

			}else if(FiseConstants.NOMBRE_FORMATO_12B.equals(l.getFormato())){ 
				nombreReporte = "formato12B";		 		      
				directorio =  "/reports/"+nombreReporte+".jasper";
				mapa = parametros12B(l.getCodEmpresa(), l.getAnioPres(), l.getMesPres(), 
						l.getAnioEjec(), l.getMesEjec(), l.getEtapa(), rutaImg, usuario, terminal, email);
				if(mapa!=null){	  					
					File reportFile12B = new File(session.getServletContext().getRealPath(directorio));
					byte[] bytes12B = null;
					bytes12B = JasperRunManager.runReportToPdf(reportFile12B.getPath(), 
							mapa, new JREmptyDataSource());
					if (bytes12B != null) {		  	  					
						session.setAttribute("bytesFormato", bytes12B);
						valorReporte =true;
					}
				}

			}else if(FiseConstants.NOMBRE_FORMATO_12C.equals(l.getFormato())){
				nombreReporte = "formato12C";		  	  		    	
				directorio = "/reports/" + nombreReporte + ".jasper";
				mapa = parametros12C(l.getCodEmpresa(), l.getAnioPres(), l.getMesPres(), 
						l.getAnioEjec(), l.getMesEjec(), l.getEtapa(), rutaImg, usuario, terminal, email);	
				FiseFormato12CCPK pk = new FiseFormato12CCPK();
				pk.setCodEmpresa(l.getCodEmpresa());
				pk.setAnoPresentacion(new Long(l.getAnioPres()));
				pk.setMesPresentacion(new Long(l.getMesPres()));
				pk.setEtapa(l.getEtapa());
				FiseFormato12CC formato = formatoService12C.obtenerFormato12CCByPK(pk);	
				if(mapa!=null && formato!=null){				
					File reportFile = new File(session.getServletContext().getRealPath(directorio));
					byte[] bytes12C = null;
					bytes12C = JasperRunManager.runReportToPdf(reportFile.getPath(), mapa, 
							new JRBeanCollectionDataSource(formato.getFiseFormato12CDs()));
					if (bytes12C != null) {				  	  		    		
						session.setAttribute("bytesFormato", bytes12C);
						valorReporte =true;
					}
				}

			}else if(FiseConstants.NOMBRE_FORMATO_12D.equals(l.getFormato())){ 
				nombreReporte = "formato12D";  	  		    	
				directorio = "/reports/" + nombreReporte + ".jasper";
				mapa = parametros12D(l.getCodEmpresa(), l.getAnioPres(), l.getMesPres(), 
						l.getAnioEjec(), l.getMesEjec(), l.getEtapa(), rutaImg, usuario, terminal, email);	
				FiseFormato12DCPK pk = new FiseFormato12DCPK();
				pk.setCodEmpresa(l.getCodEmpresa());
				pk.setAnoPresentacion(new Long(l.getAnioPres()));
				pk.setMesPresentacion(new Long(l.getMesPres()));
				pk.setEtapa(l.getEtapa());
				FiseFormato12DC formato = formatoService12D.obtenerFormato12DCByPK(pk);	
				if(mapa!=null && formato!=null){				
					File reportFile = new File(session.getServletContext().getRealPath(directorio));
					byte[] bytes12D = null;
					bytes12D = JasperRunManager.runReportToPdf(reportFile.getPath(), mapa, 
							new JRBeanCollectionDataSource(formato.getFiseFormato12DDs()));
					if (bytes12D != null) {				  	  		    		
						session.setAttribute("bytesFormato", bytes12D);
						valorReporte =true;
					}
				}

			}else if(FiseConstants.NOMBRE_FORMATO_13A.equals(l.getFormato())){ 
				nombreReporte = "formato13A";	       				
				directorio = "/reports/" + nombreReporte + ".jasper";
				mapa = parametros13A(l.getCodEmpresa(), l.getAnioPres(), l.getMesPres(), l.getEtapa(), 
						rutaImg, usuario, terminal, email); 				
				if(mapa!=null){					
					File reportFile13A = new File(session.getServletContext().getRealPath(directorio));
					byte[] bytes13A = null;	       				  				
					bytes13A = JasperRunManager.runReportToPdf(reportFile13A.getPath(), mapa, 
							new JRBeanCollectionDataSource(listaZonas13A));	       				
					if (bytes13A != null) {	        					
						session.setAttribute("bytesFormato", bytes13A);
						valorReporte =true;
					}
				}

			}else if(FiseConstants.NOMBRE_FORMATO_14A.equals(l.getFormato())){ 
				nombreReporte = "formato14A";	       				
				directorio = "/reports/" + nombreReporte + ".jasper";
				mapa = parametros14A(l.getCodEmpresa(), l.getAnioPres(), l.getMesPres(),  
						l.getAnioIniVig(), l.getAnioFinVig(),l.getEtapa(),rutaImg, usuario, terminal, email);            	 
				if(mapa!=null){            	    	
					File reportFile14A = new File(session.getServletContext().getRealPath(directorio));
					byte[] bytes14A = null;	       				  				
					bytes14A = JasperRunManager.runReportToPdf(reportFile14A.getPath(), mapa, new JREmptyDataSource());	       				
					if (bytes14A != null) {	        					
						session.setAttribute("bytesFormato", bytes14A);
						valorReporte =true;
					}
				}
            	    
			}else if(FiseConstants.NOMBRE_FORMATO_14B.equals(l.getFormato())){
				nombreReporte = "formato14B";	       				
				directorio = "/reports/" + nombreReporte + ".jasper";
				mapa = parametros14B(l.getCodEmpresa(), l.getAnioPres(), l.getMesPres(),  
						l.getAnioIniVig(), l.getAnioFinVig(),l.getEtapa(),rutaImg, usuario, terminal, email);
				if(mapa!=null){            		 
					File reportFile14B = new File(session.getServletContext().getRealPath(directorio));
					byte[] bytes14B = null;	       				  				
					bytes14B = JasperRunManager.runReportToPdf(reportFile14B.getPath(), mapa, new JREmptyDataSource());	       				
					if (bytes14B != null) {	        					
						session.setAttribute("bytesFormato", bytes14B);
						valorReporte =true;
					}
				}

			}else if(FiseConstants.NOMBRE_FORMATO_14C.equals(l.getFormato())){ 
				nombreReporte = "formato14C";	       				
				directorio = "/reports/" + nombreReporte + ".jasper";
				mapa = parametros14C(l.getCodEmpresa(), l.getAnioPres(), l.getMesPres(),  
						l.getAnioIniVig(), l.getAnioFinVig(),l.getEtapa(),rutaImg, usuario, terminal, email);           	 
				if(mapa!=null){           		 
					File reportFile14C = new File(session.getServletContext().getRealPath(directorio));
					byte[] bytes14C = null;	       				  				
					bytes14C = JasperRunManager.runReportToPdf(reportFile14C.getPath(), mapa, new JREmptyDataSource());	       				
					if (bytes14C != null) {	        					
						session.setAttribute("bytesFormato", bytes14C);
						valorReporte =true;
					}
				}           	   
			}
			if(valorReporte){
				jsonObj.put("resultado", "OK");	   		
			}else{
				jsonObj.put("resultado", "ERROR");	   	
			}
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();		  
			logger.info(jsonObj.toString());
			pw.write(jsonObj.toString());
			pw.flush();
			pw.close();	    
		 } catch (Exception e) {
			logger.error("Error al ver  formatos: "+e); 
			e.printStackTrace();
		}finally{			
			if(listaZonas13A!=null){
				listaZonas13A=null;	
			}						
		}
    }			
	  	
  	/*****LLENAR OBSERVACIONES FORMATOS MENSUALES*******/
  	private void cargarListaObservaciones12A(List<FiseFormato12AD> listaDetalle){
		int cont=0;
		listaObs12A = new ArrayList<MensajeErrorBean>();
		for (FiseFormato12AD detalle : listaDetalle) {
			detalle.setFiseFormato12ADObs(formatoService12A.listarFormato12ADObByFormato12AD(detalle));
			List<FiseFormato12ADOb> listaObser = formatoService12A.listarFormato12ADObByFormato12AD(detalle);
			for (FiseFormato12ADOb observacion : listaObser) {
				cont++;
				MensajeErrorBean obs = new MensajeErrorBean();
				obs.setId(cont);
				obs.setDescZonaBenef(fiseUtil.getMapaZonaBenef().get(observacion.getId().getIdZonaBenef()));
				obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
				obs.setDescripcion(mapaErrores.get(observacion.getFiseObservacion().getIdObservacion()));
				//obs.setDescCodSectorTipico(mapaSectorTipico.get(observacion.getId().getCodSectorTipico()));
				listaObs12A.add(obs);
			}
		}
	}
  	
  	private void cargarListaObservaciones12B(List<FiseFormato12BD> listaDetalle){
		int cont=0;
		listaObs12B = new ArrayList<MensajeErrorBean>();
		//logger.info("Tamaño de la lista detalle de B:   "+listaDetalle.size()); 
		for (FiseFormato12BD detalle : listaDetalle) {
			detalle.setFiseFormato12BDObs(formatoService12B.getLstFormatoObs(detalle));			
			List<FiseFormato12BDOb> listaObser = formatoService12B.getLstFormatoObs(detalle); 
			for (FiseFormato12BDOb observacion : listaObser) {
				cont++;
				MensajeErrorBean obs = new MensajeErrorBean();
				obs.setId(cont);
				obs.setDescZonaBenef(observacion.getId()!=null?
						(observacion.getId().getIdZonaBenef()==FiseConstants.ZONA_RURAL?FiseConstants.ZONABENEF_RURAL_DESC:
						(observacion.getId().getIdZonaBenef()==FiseConstants.ZONA_LIMA?FiseConstants.ZONABENEF_LIMA_DESC:
						(observacion.getId().getIdZonaBenef()==FiseConstants.ZONA_PROVINCIA?FiseConstants.ZONABENEF_PROVINCIA_DESC:""))
						):"");
				obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());				
				obs.setDescripcion(observacion.getFiseObservacion().getDescripcion());				
				listaObs12B.add(obs);
			}
		}
	}
  	
  	private void cargarListaObservaciones12C(List<FiseFormato12CD> listaDetalle){
		int cont=0;
		listaObs12C = new ArrayList<MensajeErrorBean>();
		for (FiseFormato12CD detalle : listaDetalle) {
			List<FiseFormato12CDOb> listaObser = formatoService12C.listarFormato12CDObByFormato12CD(detalle);
			for (FiseFormato12CDOb observacion : listaObser) {
				cont++;
				MensajeErrorBean obs = new MensajeErrorBean();
				obs.setId(cont);
				obs.setNroItemEtapa(observacion.getId().getNumeroItemEtapa());
				obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
				obs.setDescripcion(mapaErrores.get(observacion.getFiseObservacion().getIdObservacion()));
				obs.setDescEtapaEjecucion(mapaEtapaEjecucion.get(observacion.getId().getEtapaEjecucion()));
				listaObs12C.add(obs);
			}
		}
	}
  	
  	public void cargarListaObservaciones12D(List<FiseFormato12DD> listaDetalle) {
		int cont = 0;
		listaObs12D = new ArrayList<MensajeErrorBean>();
		for (FiseFormato12DD detalle : listaDetalle) {
			List<FiseFormato12DDOb> listaObser = formatoService12D.listarFormato12DDObByFormato12DD(detalle);
			for (FiseFormato12DDOb observacion : listaObser) {
				cont++;
				MensajeErrorBean obs = new MensajeErrorBean();
				obs.setId(cont);
				obs.setNroItemEtapa(observacion.getId().getNumeroItemEtapa());
				obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
				obs.setDescripcion(mapaErrores.get(observacion.getFiseObservacion().getIdObservacion()));
				obs.setDescEtapaEjecucion(mapaEtapaEjecucion.get(observacion.getId().getEtapaEjecucion()));
				listaObs12D.add(obs);
			}
		}
	}	
  	
  	/*****LLENAR OBSERVACIONES FORMATOS BIENALES*******/
	private void cargarListaObservaciones13A(List<FiseFormato13AD> listaDetalle) {
		int cont = 0;
		listaObs13A = new ArrayList<MensajeErrorBean>();
		for (FiseFormato13AD detalle : listaDetalle) {
			List<FiseFormato13ADOb> listaObser = formatoService13A.listarFormato13ADObByFormato13AD(detalle);
			for (FiseFormato13ADOb observacion : listaObser) {
				cont++;
				MensajeErrorBean obs = new MensajeErrorBean();
				obs.setId(cont);
				obs.setDescZonaBenef(fiseUtil.getMapaZonaBenef().get(observacion.getId().getIdZonaBenef()));
				obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
				obs.setDescripcion(mapaErrores.get(observacion.getFiseObservacion().getIdObservacion()));
				obs.setDescCodSectorTipico(mapaSectorTipico.get(observacion.getId().getCodSectorTipico()));
				listaObs13A.add(obs);
			}
		}
	}
	
	private void cargarListaObservaciones14A(List<FiseFormato14AD> listaDetalle){
		int cont=0;
		listaObs14A = new ArrayList<MensajeErrorBean>();
		for (FiseFormato14AD detalle : listaDetalle) {
			detalle.setFiseFormato14ADObs(formatoService14A.listarFormato14ADObByFormato14AD(detalle));
			List<FiseFormato14ADOb> listaObser = formatoService14A.listarFormato14ADObByFormato14AD(detalle);
			for (FiseFormato14ADOb observacion : listaObser) {
				cont++;
				MensajeErrorBean obs = new MensajeErrorBean();
				obs.setId(cont);
				obs.setDescZonaBenef(fiseUtil.getMapaZonaBenef().get(observacion.getId().getIdZonaBenef()));
				obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
				obs.setDescripcion(mapaErrores.get(observacion.getFiseObservacion().getIdObservacion()));
				listaObs14A.add(obs);
			}
		}
	}
	
	private void cargarListaObservaciones14B(List<FiseFormato14BD> listaDetalle){
		int cont=0;
		listaObs14B = new ArrayList<MensajeErrorBean>();
		for (FiseFormato14BD detalle : listaDetalle) {
			detalle.setFiseFormato14BDObs(formatoService14B.listarFormato14BDObByFormato14BD(detalle));
			List<FiseFormato14BDOb> listaObser = formatoService14B.listarFormato14BDObByFormato14BD(detalle);
			for (FiseFormato14BDOb observacion : listaObser) {
				cont++;
				MensajeErrorBean obs = new MensajeErrorBean();
				obs.setId(cont);
				obs.setDescZonaBenef(fiseUtil.getMapaZonaBenef().get(observacion.getId().getIdZonaBenef()));
				obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
				obs.setDescripcion(mapaErrores.get(observacion.getFiseObservacion().getIdObservacion()));
				listaObs14B.add(obs);
			}
		}
	}
	
	private void cargarListaObservaciones14C(List<FiseFormato14CD> listaDetalle) throws Exception{
		int cont=0;
		listaObs14C = new ArrayList<MensajeErrorBean>();
		for (FiseFormato14CD detalle : listaDetalle) {			
			List<FiseFormato14CDOb> listaObser = formatoService14C.listaObservacionesF14C(detalle);
			logger.info("Tamaño de lista de observaciones:  "+listaObser.size()); 
			for (FiseFormato14CDOb o : listaObser) {			
				cont++;
				MensajeErrorBean obs = new MensajeErrorBean();
				obs.setId(cont);
				obs.setDescZonaBenef(fiseUtil.getMapaZonaBenef().get(o.getId().getIdZonaBenef()));
				//obs.setDescZonaBenef(mapaZonaBenef.get(o.getId().getIdZonaBenef()));
				obs.setCodigo(o.getFiseObservacion().getIdObservacion());
				obs.setDescripcion(mapaErrores.get(o.getFiseObservacion().getIdObservacion()));
				listaObs14C.add(obs);							
			}
		}
	}	
	
	/*****LLENAR MAP DE PARAMETROS FORMATOS MENSUALES*******/	
	private Map<String, Object> parametros12A(String codEmpresa,String anioPres,String mesPres,
			String anioEjec,String mesEjec,String etapa,
			String rutaImg,String usuario,String terminal,String email){
		
		Map<String, Object> mapa = null;		
		FiseFormato12AC formato =null;
		Formato12ACBean bean =null;
		FiseFormato12ACPK pk =null;
		try {
			pk = new FiseFormato12ACPK();
		    pk.setCodEmpresa(codEmpresa);
	        pk.setAnoPresentacion(new Long(anioPres));
	        pk.setMesPresentacion(new Long(mesPres));
	        pk.setAnoEjecucionGasto(new Long(anioEjec));
	        pk.setMesEjecucionGasto(new Long(mesEjec));
	        pk.setEtapa(etapa);

	        formato = formatoService12A.obtenerFormato12ACByPK(pk);		   
		    if( formato!=null ){  	    	
		    	bean = formatoService12A.estructurarFormato12ABeanByFiseFormato12AC(formato);
	        	bean.setDescEmpresa(mapaEmpresa.get(formato.getId().getCodEmpresa()));
	        	bean.setDescMesPresentacion(fiseUtil.getMapaMeses().get(formato.getId().getMesPresentacion()));
	        	mapa = formatoService12A.mapearParametrosFormato12A(bean);
	        	CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12A);
	        	String descripcionFormato = "";
	        	if( tabla!=null ){
	        		descripcionFormato = tabla.getDescripcionTabla();
	        	}         	
	        	if(mapa!=null){	        	
	        		mapa.put("IMG", rutaImg);
	        		mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
	        		//verificar si ponerlo aca o no
	        		mapa.put("USUARIO", usuario);
	        		mapa.put("NOMBRE_FORMATO", descripcionFormato);
	        		mapa.put("FECHA_ENVIO", formato.getFechaEnvioDefinitivo());
	        		mapa.put("CORREO", email);
	        		mapa.put("NRO_OBSERVACIONES", (listaObs12A!=null && !listaObs12A.isEmpty())?listaObs12A.size():0);
	        		mapa.put("MSG_OBSERVACIONES", (listaObs12A!=null && !listaObs12A.isEmpty())?FiseConstants.MSG_OBSERVACION_REPORTE_LLENO:FiseConstants.MSG_OBSERVACION_REPORTE_VACIO);	        		
	     		    mapa.put("FECHA_REGISTRO", formato.getFechaCreacion());
	     		    mapa.put("USUARIO_REGISTRO", formato.getUsuarioCreacion());
	     		    mapa.put("ETAPA", formato.getId().getEtapa());
	 			}//fin if map !=null    	
		    }//fin del if formato !=null			
		} catch (Exception e) {
		   logger.error("Error al llenar parametros del formato 12A "+e);
		   e.printStackTrace();
		}finally{
		 if(pk!=null){
			 pk=null;
		 }
		 if(formato!=null){
			 formato=null; 
		 }
		 if(bean!=null){
			 bean=null; 
		 }
		}
		return mapa;
	}
	
	private Map<String, Object> parametros12B(String codEmpresa,String anioPres,String mesPres,
			String anioEjec,String mesEjec,String etapa,
			String rutaImg,String usuario,String terminal,String email){
		
		Map<String, Object> mapa = null;		
		FiseFormato12BC formato =null;
		Formato12BCBean bean =null;
		FiseFormato12BCPK pk =null;
		try {
			pk = new FiseFormato12BCPK();
		    pk.setCodEmpresa(codEmpresa);
	        pk.setAnoPresentacion(new Integer(anioPres));
	        pk.setMesPresentacion(new Integer(mesPres));
	        pk.setAnoEjecucionGasto(new Integer(anioEjec));
	        pk.setMesEjecucionGasto(new Integer(mesEjec));
	        pk.setEtapa(etapa);

	        formato = formatoService12B.getFormatoCabeceraById(pk);
		    if( formato!=null ){  	    	
		    	bean = formatoService12B.estructurarFormato12BBeanByFiseFormato12BC(formato);
		    	bean.setDescEmpresa(formato.getAdmEmpresa().getDscCortaEmpresa());
	        	bean.setDescMesPresentacion(FiseUtil.descripcionMes(formato.getId().getMesPresentacion()));
	        	bean.setDescMesEjecucion(FiseUtil.descripcionMes(formato.getId().getMesEjecucionGasto()));
	        	mapa = formatoService12B.mapearParametrosFormato12B(bean);
	        	
	        	CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12B);
	        	String descripcionFormato = "";
	        	if( tabla!=null ){
	        		descripcionFormato = tabla.getDescripcionTabla();
	        	}
	        	if(mapa!=null){	        	
	        		mapa.put("IMG", rutaImg);
	        		mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
	        		//verificar si ponerlo aca o no
	        		mapa.put("USUARIO", usuario);
	        		mapa.put("NOMBRE_FORMATO", descripcionFormato);
	        		mapa.put("FECHA_ENVIO", formato.getFechaEnvioDefinitivo());
	        		mapa.put("CORREO", email);
	        		mapa.put("NRO_OBSERVACIONES", (listaObs12B!=null && !listaObs12B.isEmpty())?listaObs12B.size():0);
	        		mapa.put("MSG_OBSERVACIONES", (listaObs12B!=null && !listaObs12B.isEmpty())?FiseConstants.MSG_OBSERVACION_REPORTE_LLENO:FiseConstants.MSG_OBSERVACION_REPORTE_VACIO);	        		
	     		    mapa.put("FECHA_REGISTRO", formato.getFechaCreacion());
	     		    mapa.put("USUARIO_REGISTRO", formato.getUsuarioCreacion());
	     		    mapa.put("ETAPA", formato.getId().getEtapa());
	 			}//fin if map !=null    	
		    }//fin del if formato !=null			
		} catch (Exception e) {
		   logger.error("Error al llenar parametros del formato 12B "+e);
		   e.printStackTrace();
		}finally{
		 if(pk!=null){
			 pk=null;
		 }
		 if(formato!=null){
			 formato=null; 
		 }
		 if(bean!=null){
			 bean=null; 
		 }
		}
		return mapa;
	}
	
	private Map<String, Object> parametros12C(String codEmpresa,String anioPres,String mesPres,
			String anioEjec,String mesEjec,String etapa,
			String rutaImg,String usuario,String terminal,String email){
		
		Map<String, Object> mapa = null;		
		FiseFormato12CC formato =null;
		Formato12CCBean bean =null;
		FiseFormato12CCPK pk =null;
		try {
			pk = new FiseFormato12CCPK();
		    pk.setCodEmpresa(codEmpresa);
	        pk.setAnoPresentacion(new Long(anioPres));
	        pk.setMesPresentacion(new Long(mesPres));	        
	        pk.setEtapa(etapa);	        
			formato = formatoService12C.obtenerFormato12CCByPK(pk);	   
		    if( formato!=null ){  	    	
		    	bean = formatoService12C.estructurarFormato12CBeanByFiseFormato12CC(formato);
				bean.setDescEmpresa(fiseUtil.getMapaEmpresa().get(formato.getId().getCodEmpresa()));
				bean.setDescMesPresentacion(fiseUtil.getMapaMeses().get(formato.getId().getMesPresentacion()));
				mapa = formatoService12C.mapearParametrosFormato12C(bean);

				CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12C);
				String descripcionFormato = "";
				if (tabla != null) {
					descripcionFormato = tabla.getDescripcionTabla();
				}
	        	if(mapa!=null){	        	
	        		mapa.put("IMG", rutaImg);
	        		mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
	        		//verificar si ponerlo aca o no
	        		mapa.put("USUARIO", usuario);
	        		mapa.put("NOMBRE_FORMATO", descripcionFormato);
	        		mapa.put("FECHA_ENVIO", formato.getFechaEnvioDefinitivo());
	        		mapa.put("CORREO", email);
	        		mapa.put("NRO_OBSERVACIONES", (listaObs12C!=null && !listaObs12C.isEmpty())?listaObs12C.size():0);
	        		mapa.put("MSG_OBSERVACIONES", (listaObs12C!=null && !listaObs12C.isEmpty())?FiseConstants.MSG_OBSERVACION_REPORTE_LLENO:FiseConstants.MSG_OBSERVACION_REPORTE_VACIO);	        		
	     		    mapa.put("FECHA_REGISTRO", formato.getFechaCreacion());
	     		    mapa.put("USUARIO_REGISTRO", formato.getUsuarioCreacion());
	     		    mapa.put("ETAPA", formato.getId().getEtapa());
	 			}//fin if map !=null    	
		    }//fin del if formato !=null			
		} catch (Exception e) {
		   logger.error("Error al llenar parametros del formato 12C "+e);
		   e.printStackTrace();
		}finally{
		 if(pk!=null){
			 pk=null;
		 }
		 if(formato!=null){
			 formato=null; 
		 }
		 if(bean!=null){
			 bean=null; 
		 }
		}
		return mapa;
	}
	
	private Map<String, Object> parametros12D(String codEmpresa,String anioPres,String mesPres,
			String anioEjec,String mesEjec,String etapa,
			String rutaImg,String usuario,String terminal,String email){
		
		Map<String, Object> mapa = null;		
		FiseFormato12DC formato =null;
		Formato12DCBean bean =null;
		FiseFormato12DCPK pk =null;
		try {
			pk = new FiseFormato12DCPK();
		    pk.setCodEmpresa(codEmpresa);
	        pk.setAnoPresentacion(new Long(anioPres));
	        pk.setMesPresentacion(new Long(mesPres));	        
	        pk.setEtapa(etapa);		       
			formato = formatoService12D.obtenerFormato12DCByPK(pk);
		    if( formato!=null ){  	    
				bean = formatoService12D.estructurarFormato12DBeanByFiseFormato12DC(formato);
				bean.setDescEmpresa(fiseUtil.getMapaEmpresa().get(formato.getId().getCodEmpresa()));
				bean.setDescMesPresentacion(fiseUtil.getMapaMeses().get(formato.getId().getMesPresentacion()));
				mapa = formatoService12D.mapearParametrosFormato12D(bean);
				CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12D);
				String descripcionFormato = "";
				if (tabla != null) {
					descripcionFormato = tabla.getDescripcionTabla();
				}			
	        	if(mapa!=null){	        	
	        		mapa.put("IMG", rutaImg);
	        		mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
	        		//verificar si ponerlo aca o no
	        		mapa.put("USUARIO", usuario);
	        		mapa.put("NOMBRE_FORMATO", descripcionFormato);
	        		mapa.put("FECHA_ENVIO", formato.getFechaEnvioDefinitivo());
	        		mapa.put("CORREO", email);
	        		mapa.put("NRO_OBSERVACIONES", (listaObs12D!=null && !listaObs12D.isEmpty())?listaObs12D.size():0);
	        		mapa.put("MSG_OBSERVACIONES", (listaObs12D!=null && !listaObs12D.isEmpty())?FiseConstants.MSG_OBSERVACION_REPORTE_LLENO:FiseConstants.MSG_OBSERVACION_REPORTE_VACIO);	        		
	     		    mapa.put("FECHA_REGISTRO", formato.getFechaCreacion());
	     		    mapa.put("USUARIO_REGISTRO", formato.getUsuarioCreacion());
	     		    mapa.put("ETAPA", formato.getId().getEtapa());
	 			}//fin if map !=null    	
		    }//fin del if formato !=null			
		} catch (Exception e) {
		   logger.error("Error al llenar parametros del formato 12D "+e);
		   e.printStackTrace();
		}finally{
		 if(pk!=null){
			 pk=null;
		 }
		 if(formato!=null){
			 formato=null; 
		 }
		 if(bean!=null){
			 bean=null; 
		 }
		}
		return mapa;
	}
	
	
	/*****LLENAR MAP DE PARAMETROS FORMATOS BIENALES*******/	
	private Map<String, Object> parametros13A(String codEmpresa,String anioPres,String mesPres,
			String etapa, String rutaImg,String usuario,
			String terminal,String email){
		
		Map<String, Object> mapa = null;
		FiseFormato13ACPK pk =null;
		FiseFormato13AC formato =null;
		Formato13ACBean bean =null;
		try {
			pk = new FiseFormato13ACPK();
			pk.setCodEmpresa(codEmpresa);
			pk.setAnoPresentacion(new Long(anioPres));
			pk.setMesPresentacion(new Long(mesPres));
			pk.setEtapa(etapa);
			formato = formatoService13A.obtenerFormato13ACByPK(pk);
		    if( formato!=null ){  
		    	/****Cargamos la lista de zonas *****/
		    	listaZonas13A = formatoService13A.listarLocalidadesPorZonasBenefFormato13ADByFormato13AC(formato);		    	
		    	bean = formatoService13A.estructurarFormato13ABeanByFiseFormato13AC(formato);
	        	bean.setDescEmpresa(mapaEmpresa.get(formato.getId().getCodEmpresa()));
	        	bean.setDescMesPresentacion(fiseUtil.getMapaMeses().get(formato.getId().getMesPresentacion()));
	        	mapa = formatoService13A.mapearParametrosFormato13A(bean);
	        	CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO13A);
	        	String descripcionFormato = "";
	        	if( tabla!=null ){
	        		descripcionFormato = tabla.getDescripcionTabla();
	        	}         	
	        	if(mapa!=null){	        	
	        		mapa.put("IMG", rutaImg);
	        		mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
	        		//verificar si ponerlo aca o no
	        		mapa.put("USUARIO", usuario);
	        		mapa.put("NOMBRE_FORMATO", descripcionFormato);
	        		mapa.put("FECHA_ENVIO", formato.getFechaEnvioDefinitivo());
	        		mapa.put("CORREO", email);
	        		mapa.put("NRO_OBSERVACIONES", (listaObs13A!=null && !listaObs13A.isEmpty())?listaObs13A.size():0);
	        		mapa.put("MSG_OBSERVACIONES", (listaObs13A!=null && !listaObs13A.isEmpty())?FiseConstants.MSG_OBSERVACION_REPORTE_LLENO:FiseConstants.MSG_OBSERVACION_REPORTE_VACIO);
	        		 //add para acta envio
	        		mapa.put("ANO_INICIO_VIGENCIA",formato.getAnoInicioVigenciaDetalle());
					mapa.put("ANO_FIN_VIGENCIA",formato.getAnoFinVigenciaDetalle());
	     		    mapa.put("FECHA_REGISTRO", formato.getFechaCreacion());
	     		    mapa.put("USUARIO_REGISTRO", formato.getUsuarioCreacion());	     		 
	     		    mapa.put("ETAPA", formato.getId().getEtapa());
	 			}//fin if map !=null    	
		    }//fin del if formato !=null			
		} catch (Exception e) {
		   logger.error("Error al llenar parametros del formato 13A "+e);
		   e.printStackTrace();
		}finally{
		 if(pk!=null){
			 pk=null;
		 }
		 if(formato!=null){
			 formato=null; 
		 }
		 if(bean!=null){
			 bean=null; 
		 }
		}
		return mapa;
	}
	
	private Map<String, Object> parametros14A(String codEmpresa,String anioPres,String mesPres,
			String anioIniVig,String anioFinVig,String etapa,
			String rutaImg,String usuario,String terminal,String email){
		
		Map<String, Object> mapa = null;
		FiseFormato14ACPK pk =null;
		FiseFormato14AC formato =null;
		Formato14ACBean bean =null;
		try {
			pk = new FiseFormato14ACPK();
		    pk.setCodEmpresa(codEmpresa);
	        pk.setAnoPresentacion(new Long(anioPres));
	        pk.setMesPresentacion(new Long(mesPres));
	        pk.setAnoInicioVigencia(new Long(anioIniVig));
	        pk.setAnoFinVigencia(new Long(anioFinVig));
	        pk.setEtapa(etapa);
	        formato = formatoService14A.obtenerFormato14ACByPK(pk);
		    if( formato!=null ){  	    	
		    	bean = formatoService14A.estructurarFormato14ABeanByFiseFormato14AC(formato);
	        	bean.setDescEmpresa(mapaEmpresa.get(formato.getId().getCodEmpresa()));
	        	bean.setDescMesPresentacion(fiseUtil.getMapaMeses().get(formato.getId().getMesPresentacion()));
	        	mapa = formatoService14A.mapearParametrosFormato14A(bean);
	        	CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14A);
	        	String descripcionFormato = "";
	        	if( tabla!=null ){
	        		descripcionFormato = tabla.getDescripcionTabla();
	        	}         	
	        	if(mapa!=null){	        	
	        		mapa.put("IMG", rutaImg);
	        		mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
	        		//verificar si ponerlo aca o no
	        		mapa.put("USUARIO", usuario);
	        		mapa.put("NOMBRE_FORMATO", descripcionFormato);
	        		mapa.put("FECHA_ENVIO", formato.getFechaEnvioDefinitivo());
	        		mapa.put("CORREO", email);
	        		mapa.put("NRO_OBSERVACIONES", (listaObs14A!=null && !listaObs14A.isEmpty())?listaObs14A.size():0);
	        		mapa.put("MSG_OBSERVACIONES", (listaObs14A!=null && !listaObs14A.isEmpty())?FiseConstants.MSG_OBSERVACION_REPORTE_LLENO:FiseConstants.MSG_OBSERVACION_REPORTE_VACIO);
	        		 //add para acta envio
	     		    mapa.put("ANO_INICIO_VIGENCIA", formato.getId().getAnoInicioVigencia());
	     		    mapa.put("ANO_FIN_VIGENCIA", formato.getId().getAnoFinVigencia());
	     		    mapa.put("FECHA_REGISTRO", formato.getFechaCreacion());
	     		    mapa.put("USUARIO_REGISTRO", formato.getUsuarioCreacion());	     		   
	     		    mapa.put("ETAPA", formato.getId().getEtapa());
	 			}//fin if map !=null    	
		    }//fin del if formato !=null			
		} catch (Exception e) {
		   logger.error("Error al llenar parametros del formato 14A "+e);
		   e.printStackTrace();
		}finally{
		 if(pk!=null){
			 pk=null;
		 }
		 if(formato!=null){
			 formato=null; 
		 }
		 if(bean!=null){
			 bean=null; 
		 }
		}
		return mapa;
	}
	
	private Map<String, Object> parametros14B(String codEmpresa,String anioPres,String mesPres,
			String anioIniVig,String anioFinVig,String etapa,
			String rutaImg,String usuario,String terminal,String email){
		
		Map<String, Object> mapa = null;
		FiseFormato14BCPK pk =null;
		FiseFormato14BC formato =null;
		Formato14BCBean bean =null;
		try {
			pk = new FiseFormato14BCPK();
		    pk.setCodEmpresa(codEmpresa);
	        pk.setAnoPresentacion(new Long(anioPres));
	        pk.setMesPresentacion(new Long(mesPres));
	        pk.setAnoInicioVigencia(new Long(anioIniVig));
	        pk.setAnoFinVigencia(new Long(anioFinVig));
	        pk.setEtapa(etapa);
	        formato = formatoService14B.obtenerFormato14BCByPK(pk);
		    if( formato!=null ){  	    	
		    	bean = formatoService14B.estructurarFormato14BBeanByFiseFormato14BC(formato);
	        	bean.setDescEmpresa(mapaEmpresa.get(formato.getId().getCodEmpresa()));
	        	bean.setDescMesPresentacion(fiseUtil.getMapaMeses().get(formato.getId().getMesPresentacion()));
	        	mapa = formatoService14B.mapearParametrosFormato14B(bean);	        	
	        	CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14B);
	        	String descripcionFormato = "";
	        	if( tabla!=null ){
	        		descripcionFormato = tabla.getDescripcionTabla();
	        	}         	
	        	if(mapa!=null){	        	
	        		mapa.put("IMG", rutaImg);
	        		mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
	        		//verificar si ponerlo aca o no
	        		mapa.put("USUARIO", usuario);
	        		mapa.put("NOMBRE_FORMATO", descripcionFormato);
	        		mapa.put("FECHA_ENVIO", formato.getFechaEnvioDefinitivo());
	        		mapa.put("CORREO", email);
	        		mapa.put("NRO_OBSERVACIONES", (listaObs14B!=null && !listaObs14B.isEmpty())?listaObs14B.size():0);
	        		mapa.put("MSG_OBSERVACIONES", (listaObs14B!=null && !listaObs14B.isEmpty())?FiseConstants.MSG_OBSERVACION_REPORTE_LLENO:FiseConstants.MSG_OBSERVACION_REPORTE_VACIO);
	        		 //add para acta envio
	     		    mapa.put("ANO_INICIO_VIGENCIA", formato.getId().getAnoInicioVigencia());
	     		    mapa.put("ANO_FIN_VIGENCIA", formato.getId().getAnoFinVigencia());
	     		    mapa.put("FECHA_REGISTRO", formato.getFechaCreacion());
	     		    mapa.put("USUARIO_REGISTRO", formato.getUsuarioCreacion());	     		  
	     		    mapa.put("ETAPA", formato.getId().getEtapa());
	 			}//fin if map !=null    	
		    }//fin del if formato !=null			
		} catch (Exception e) {
		   logger.error("Error al llenar parametros del formato 14B "+e);
		   e.printStackTrace();
		}finally{
		 if(pk!=null){
			 pk=null;
		 }
		 if(formato!=null){
			 formato=null; 
		 }
		 if(bean!=null){
			 bean=null; 
		 }
		}
		return mapa;
	}
	
	private Map<String, Object> parametros14C(String codEmpresa,String anioPres,String mesPres,
			String anioIniVig,String anioFinVig,String etapa,
			String rutaImg,String usuario,String terminal,String email){
		
		Map<String, Object> mapa = null;
		Formato14CBean f =null;
		FiseFormato14CC formato =null;
		Formato14CReportBean bean =null;
		try {
		    f= new Formato14CBean();
			f.setCodEmpresa(codEmpresa); 
			f.setAnioPres(anioPres);
			f.setMesPres(mesPres);
			f.setEtapa(etapa);
			f.setAnoIniVigencia(anioIniVig);
			f.setAnoFinVigencia(anioFinVig);
			logger.info(" anio ini vig1  "+f.getAnoIniVigencia()+ "   "+anioIniVig);
		    logger.info(" anio fin vig1  "+f.getAnoFinVigencia()+ "   "+anioFinVig);
		    formato = formatoService14C.obtenerFiseFormato14CC(f);
		    if( formato!=null ){  	    	
		    	bean = formatoService14C.estructurarFormato14CBeanByFiseFormato14C(formato);
	        	bean.setDescEmpresa(mapaEmpresa.get(formato.getId().getCodEmpresa()));
	        	bean.setDescMesPresentacion(fiseUtil.getMapaMeses().get(formato.getId().getMesPresentacion()));
	        	mapa = formatoService14C.mapearParametrosFormato14C(bean);
	        	CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14C);
	        	String descripcionFormato = "";
	        	if( tabla!=null ){
	        		descripcionFormato = tabla.getDescripcionTabla();
	        	}         	
	        	if(mapa!=null){	        	
	        		mapa.put("IMG", rutaImg);
	        		mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
	        		//verificar si ponerlo aca o no
	        		mapa.put("USUARIO", usuario);
	        		mapa.put("NOMBRE_FORMATO", descripcionFormato);
	        		mapa.put("FECHA_ENVIO", formato.getFechaEnvioDefinitivo());
	        		mapa.put("CORREO", email);
	        		mapa.put("NRO_OBSERVACIONES", (listaObs14C!=null && !listaObs14C.isEmpty())?listaObs14C.size():0);
	        		mapa.put("MSG_OBSERVACIONES", (listaObs14C!=null && !listaObs14C.isEmpty())?FiseConstants.MSG_OBSERVACION_REPORTE_LLENO:FiseConstants.MSG_OBSERVACION_REPORTE_VACIO);
	        		 //add para acta envio
	     		    mapa.put("ANO_INICIO_VIGENCIA", formato.getId().getAnoInicioVigencia());
	     		    mapa.put("ANO_FIN_VIGENCIA", formato.getId().getAnoFinVigencia());
	     		    mapa.put("FECHA_REGISTRO", formato.getFechaCreacion());
	     		    mapa.put("USUARIO_REGISTRO", formato.getUsuarioCreacion());	     		   
	     		    mapa.put("ETAPA", formato.getId().getEtapa());
	 			}//fin if map !=null    	
		    }//fin del if formato !=null			
		} catch (Exception e) {
		   logger.error("Error al llenar parametros del formato 14C "+e);
		   e.printStackTrace();
		}finally{
		 if(f!=null){
			 f=null;
		 }
		 if(formato!=null){
			 formato=null; 
		 }
		 if(bean!=null){
			 bean=null; 
		 }
		}
		return mapa;
	}
	
	
	
}
