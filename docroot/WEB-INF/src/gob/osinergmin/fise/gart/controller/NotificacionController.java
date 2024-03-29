package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.FiseObservacionBean;
import gob.osinergmin.fise.bean.Formato12A12BGeneric;
import gob.osinergmin.fise.bean.Formato12C12D13Generic;
import gob.osinergmin.fise.bean.Formato14CBean;
import gob.osinergmin.fise.bean.Formato14Generic;
import gob.osinergmin.fise.bean.MensajeErrorBean;
import gob.osinergmin.fise.bean.NotificacionBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.CfgTabla;
import gob.osinergmin.fise.domain.FiseFormato12AC;
import gob.osinergmin.fise.domain.FiseFormato12ACPK;
import gob.osinergmin.fise.domain.FiseFormato12AD;
import gob.osinergmin.fise.domain.FiseFormato12ADOb;
import gob.osinergmin.fise.domain.FiseFormato12ADPK;
import gob.osinergmin.fise.domain.FiseFormato12BC;
import gob.osinergmin.fise.domain.FiseFormato12BCPK;
import gob.osinergmin.fise.domain.FiseFormato12BD;
import gob.osinergmin.fise.domain.FiseFormato12BDOb;
import gob.osinergmin.fise.domain.FiseFormato12BDPK;
import gob.osinergmin.fise.domain.FiseFormato12CC;
import gob.osinergmin.fise.domain.FiseFormato12CCPK;
import gob.osinergmin.fise.domain.FiseFormato12CD;
import gob.osinergmin.fise.domain.FiseFormato12CDOb;
import gob.osinergmin.fise.domain.FiseFormato12CDPK;
import gob.osinergmin.fise.domain.FiseFormato12DC;
import gob.osinergmin.fise.domain.FiseFormato12DCPK;
import gob.osinergmin.fise.domain.FiseFormato12DD;
import gob.osinergmin.fise.domain.FiseFormato12DDOb;
import gob.osinergmin.fise.domain.FiseFormato12DDPK;
import gob.osinergmin.fise.domain.FiseFormato13AC;
import gob.osinergmin.fise.domain.FiseFormato13ACPK;
import gob.osinergmin.fise.domain.FiseFormato13AD;
import gob.osinergmin.fise.domain.FiseFormato13ADOb;
import gob.osinergmin.fise.domain.FiseFormato13ADPK;
import gob.osinergmin.fise.domain.FiseFormato14AC;
import gob.osinergmin.fise.domain.FiseFormato14ACPK;
import gob.osinergmin.fise.domain.FiseFormato14AD;
import gob.osinergmin.fise.domain.FiseFormato14ADOb;
import gob.osinergmin.fise.domain.FiseFormato14ADPK;
import gob.osinergmin.fise.domain.FiseFormato14BC;
import gob.osinergmin.fise.domain.FiseFormato14BCPK;
import gob.osinergmin.fise.domain.FiseFormato14BD;
import gob.osinergmin.fise.domain.FiseFormato14BDOb;
import gob.osinergmin.fise.domain.FiseFormato14BDPK;
import gob.osinergmin.fise.domain.FiseFormato14CC;
import gob.osinergmin.fise.domain.FiseFormato14CD;
import gob.osinergmin.fise.domain.FiseFormato14CDOb;
import gob.osinergmin.fise.domain.FiseFormato14CDPK;
import gob.osinergmin.fise.domain.FiseGrupoInformacion;
import gob.osinergmin.fise.domain.FiseObservacion;
import gob.osinergmin.fise.gart.jsp.FileEntryJSP;
import gob.osinergmin.fise.gart.service.CfgTablaGartService;
import gob.osinergmin.fise.gart.service.CommonGartService;
import gob.osinergmin.fise.gart.service.FiseGrupoInformacionGartService;
import gob.osinergmin.fise.gart.service.FiseObservacionGartService;
import gob.osinergmin.fise.gart.service.FisePeriodoEnvioGartService;
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
import java.util.Date;
import java.util.HashMap;
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

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;


@Controller("notificacionController")
@RequestMapping("VIEW")
public class NotificacionController {
	
	Logger logger = Logger.getLogger(NotificacionController.class);
	
	@Autowired
	@Qualifier("commonGartServiceImpl")
	private CommonGartService commonService;
	
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
	
	@Autowired
	@Qualifier("fiseObservacionGartServiceImpl")
	private FiseObservacionGartService fiseObservacionGartService;
	
	@Autowired
	@Qualifier("fisePeriodoEnvioGartServiceImpl")
	private FisePeriodoEnvioGartService fisePeriodoEnvioGartService;
	
	
	
	private Map<String, String> mapaEmpresa;	
	private List<MensajeErrorBean> listaObservaciones;	
	private Map<String, String> mapaSectorTipico;
	private Map<Long, String> mapaEtapaEjecucion;
	
	
	@ModelAttribute("notificacionBean")
    public NotificacionBean listNotificacionBean() {
		NotificacionBean comman  = new NotificacionBean();
        return comman;	        
    }

	
	@RequestMapping
	public String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse,
			     @ModelAttribute("notificacionBean")NotificacionBean n){
        try {           	
    		if(n.getListaEmpresas()==null){
    			logger.info("Lista de empresa es null:  " +renderRequest); 
    			n.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));
    		}     		
    		n.setAdmin(fiseUtil.esAdministrador(renderRequest));
    		
    		n.setListaGrupoInf(fiseGrupoInformacionService.listarGrupoInformacion(FiseConstants.MENSUAL,"")); 
    		
    		mapaEmpresa = fiseUtil.getMapaEmpresa();
    		mapaSectorTipico = fiseUtil.getMapaSectorTipico();    		
    		mapaEtapaEjecucion = fiseUtil.getMapaEtapaEjecucion();
    		
    		
    		logger.info("Fecha actual del sistema:  "+new Date());
    		
    		model.addAttribute("model", n);
    		
		} catch (Exception e) {
			logger.info("Ocurrio un errror al caragar la pagina notificacion-validacion"); 
			e.printStackTrace();
		}		
		return "notificacion";
	}
	
	
	@ResourceMapping("busquedaNotificacion")
  	public void busqueda(ResourceRequest request,ResourceResponse response,
  			 @ModelAttribute("notificacionBean")NotificacionBean n){
		
		try{
			response.setContentType("application/json");
			
			PortletRequest pRequest = (PortletRequest) request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
			long idGrupo=0;
			String codEmpresa = n.getCodEmpresaBusq();				
			String optionFormato = n.getOptionFormato();
			String idgrupoInf = n.getGrupoInfBusq();
			String etapa = n.getEtapaBusq();
			
			if(FormatoUtil.isNotBlank(n.getGrupoInfBusq())){ 
		    	idGrupo = new Long(idgrupoInf);
		    }
			String data ="";			
			logger.info("codigo empresa "+ codEmpresa);  			
  			logger.info("id Grupo inf "+ idgrupoInf);  	 			
  			logger.info("Option formato "+ optionFormato);
  			logger.info("etapa "+ etapa);
  			
  			String codEmpreCompleta = FormatoUtil.rellenaDerecha(codEmpresa, ' ', 4);
  			
  			List<NotificacionBean> lista =commonService.buscarNotificacion(codEmpreCompleta, 
  					optionFormato, etapa,idGrupo, "");
  			
  			logger.info("tama�o de la lista notificacion   :"+lista.size());
  			
  			List<NotificacionBean> listaNotifi = new ArrayList<NotificacionBean>();
  			
  			for(NotificacionBean not : lista){    				
  				not.setDesEmpresa(mapaEmpresa.get(not.getCodEmpresa()));
  				not.setDesMes(fiseUtil.getMapaMeses().get(Long.valueOf(not.getMesPres())));
  				if(!"00".equals(not.getMesEjec())){ 
  					not.setDesMesEje(fiseUtil.getMapaMeses().get(Long.valueOf(not.getMesEjec())));   		
  				}else{
  					not.setDesMesEje("---");
  				}
  				listaNotifi.add(not);
  			} 			
  			data = toStringListJSON(listaNotifi);
  			logger.info("arreglo json:"+data);
  			PrintWriter pw = response.getWriter();
  			pw.write(data);
  			pw.flush();
  			pw.close(); 
  			pRequest.getPortletSession().setAttribute("listaNotificacion", listaNotifi, PortletSession.APPLICATION_SCOPE);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	
	@ResourceMapping("busquedaNotificacionEliminar")
  	public void busquedaEliminar(ResourceRequest request,ResourceResponse response,
  			 @ModelAttribute("notificacionBean")NotificacionBean n){
		
		try{
			response.setContentType("application/json");
			
			PortletRequest pRequest = (PortletRequest) request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
			long idGrupo=0;
			String codEmpresa = n.getCodEmpresaBusq();				
			String optionFormato = n.getOptionFormato();
			String idgrupoInf = n.getGrupoInfBusq();
			String etapa = n.getEtapaBusq();
			
			if(FormatoUtil.isNotBlank(n.getGrupoInfBusq())){ 
		    	idGrupo = new Long(idgrupoInf);
		    }
			String data ="";			
			logger.info("codigo empresa "+ codEmpresa);  			
  			logger.info("id Grupo inf "+ idgrupoInf);  	 			
  			logger.info("Option formato "+ optionFormato);
  			logger.info("etapa "+ etapa);
  			
  			String codEmpreCompleta = FormatoUtil.rellenaDerecha(codEmpresa, ' ', 4);
  			
  			List<NotificacionBean> lista =commonService.buscarNotificacion(codEmpreCompleta, 
  					optionFormato, etapa,idGrupo,FiseConstants.EXCLUIR_REGISTRO_NOTIFICACION);
  			
  			logger.info("tama�o de la lista notificacion al eliminar   :"+lista.size());
  			
  			List<NotificacionBean> listaNotifi = new ArrayList<NotificacionBean>();
  			
  			for(NotificacionBean not : lista){    				
  				not.setDesEmpresa(mapaEmpresa.get(not.getCodEmpresa()));
  				not.setDesMes(fiseUtil.getMapaMeses().get(Long.valueOf(not.getMesPres())));
  				if(!"00".equals(not.getMesEjec())){ 
  					not.setDesMesEje(fiseUtil.getMapaMeses().get(Long.valueOf(not.getMesEjec())));   		
  				}else{
  					not.setDesMesEje("---");
  				}
  				listaNotifi.add(not);
  			} 			
  			data = toStringListJSON(listaNotifi);
  			logger.info("arreglo json:"+data);
  			PrintWriter pw = response.getWriter();
  			pw.write(data);
  			pw.flush();
  			pw.close(); 
  			pRequest.getPortletSession().setAttribute("listaNotificacion", listaNotifi, PortletSession.APPLICATION_SCOPE);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}	
	
	
	private String toStringListJSON(List<NotificacionBean> lista) {
		Serializer serializer = new JsonSerializer();
		Object result = serializer.serialize(lista);
		String data = String.valueOf(result);
		return data;
	}
	
	@ResourceMapping("cargarGrupoInformacion")
  	public void cargaPeriodo(ModelMap model, ResourceRequest request,ResourceResponse response,
  			 @ModelAttribute("notificacionBean")NotificacionBean n){
		try {			
  			response.setContentType("applicacion/json");
  			String tipoFormato = n.getOptionFormato();
  			
  			logger.info("Codigo grupo inf. para cargar grupo de infor.:  "+tipoFormato);
  			
  			List<FiseGrupoInformacion> listaGrupoInf = fiseGrupoInformacionService.listarGrupoInformacion(tipoFormato,"");
  			logger.info("Tama�o de lista de grupo inf:  "+listaGrupoInf.size()); 
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
	
	
	@ResourceMapping("obtenerEstadoNotificacion")
  	public void obtenerEstadoNotificacion(ResourceRequest request,ResourceResponse response,
  			 @ModelAttribute("notificacionBean")NotificacionBean n){
		
		try{
			response.setContentType("application/json");
			
			JSONObject jsonObj = new JSONObject();
			long idGrupo=0;
			String codEmpresa = n.getCodEmpresaBusq();		
			String idgrupoInf = n.getGrupoInfBusq();
			String etapa = n.getEtapaBusq();    
			if(FormatoUtil.isNotBlank(n.getGrupoInfBusq())){ 
		    	idGrupo = new Long(idgrupoInf);
		    }			
			logger.info("codigo empresa "+ codEmpresa);  			
  			logger.info("id Grupo inf "+ idgrupoInf);  				
  			logger.info("etapa "+ etapa);
  			
  			String estado = commonService.obtenerEstadoNotificacion(codEmpresa, idGrupo, etapa);
  			
  			jsonObj.put("resultado", estado);
  			
  			logger.info("arreglo json:"+jsonObj);
  			
  			PrintWriter pw = response.getWriter();
  			pw.write(jsonObj.toString());
  			pw.flush();
  			pw.close();  			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}	
	
	
	@ResourceMapping("procesarNotificacion")
  	public void procesarNotificacion(ResourceRequest request,ResourceResponse response,
  			 @ModelAttribute("notificacionBean")NotificacionBean n){
		
		try{
			response.setContentType("application/json");
			
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			
			JSONObject jsonObj = new JSONObject();
			long idGrupo=0;
			String codEmpresa = n.getCodEmpresaBusq();				
			String optionFormato = n.getOptionFormato();
			String idgrupoInf = n.getGrupoInfBusq();
			String etapa = n.getEtapaBusq();    
			if(FormatoUtil.isNotBlank(n.getGrupoInfBusq())){ 
		    	idGrupo = new Long(idgrupoInf);
		    }
			boolean valor = true;
			logger.info("codigo empresa "+ codEmpresa);  			
  			logger.info("id Grupo inf "+ idgrupoInf);  	 			
  			logger.info("Option formato "+ optionFormato);
  			logger.info("etapa "+ etapa);
  			
  			String codEmpreCompleta = FormatoUtil.rellenaDerecha(codEmpresa, ' ', 4);
  			
  			List<NotificacionBean> lista =commonService.buscarNotificacion(codEmpreCompleta, 
  					optionFormato, etapa, idGrupo,FiseConstants.PROCESAR_VALIDACION);
  			
  			logger.info("tama�o de la lista notificacion al procesar   :"+lista.size()); 			 
  			 
  			for(NotificacionBean not:lista){
  				
  				if(FiseConstants.NOMBRE_FORMATO_12A.equals(not.getFormato())){ 
  					FiseFormato12ACPK pk = new FiseFormato12ACPK();
  	  				pk.setCodEmpresa(not.getCodEmpresa());
  	  				pk.setAnoPresentacion(new Long(not.getAnioPres()));
  	  		        pk.setMesPresentacion(new Long(not.getMesPres()));
  	  		        pk.setAnoEjecucionGasto(new Long(not.getAnioEjec()));
  	  		        pk.setMesEjecucionGasto(new Long(not.getMesEjec()));
  	  		        pk.setEtapa(not.getEtapa());  	  			        
  	  		        FiseFormato12AC formato12A = formatoService12A.obtenerFormato12ACByPK(pk);
  	  		        int i = formatoService12A.validarFormato12A(formato12A, FiseConstants.NOMBRE_FORMATO_12A,
  	  		        		themeDisplay.getUser().getLogin(), themeDisplay.getUser().getLoginIP());
  	  		        if(i!=0){
  	  		          valor = false;
  	  		          logger.info("Valor de procesar notificacion 12A: "+valor); 
  	  		          break;
  	  		        }
  	  		        
  				}else if(FiseConstants.NOMBRE_FORMATO_12B.equals(not.getFormato())){ 
  					FiseFormato12BCPK pk=new FiseFormato12BCPK();
  					pk.setCodEmpresa(not.getCodEmpresa());
  	  				pk.setAnoPresentacion(new Integer(not.getAnioPres()));
  	  		        pk.setMesPresentacion(new Integer(not.getMesPres()));
  	  		        pk.setAnoEjecucionGasto(new Integer(not.getAnioEjec()));
  	  		        pk.setMesEjecucionGasto(new Integer(not.getMesEjec()));
  	  		        pk.setEtapa(not.getEtapa());  
  	  		        FiseFormato12BC formato12B =formatoService12B.getFormatoCabeceraById(pk);  		        
  	  		        Formato12A12BGeneric formato12Generic = new Formato12A12BGeneric(formato12B);
	        	    int i = commonService.validarFormatos_12A12B(formato12Generic, 
	        	    		FiseConstants.NOMBRE_FORMATO_12B, 
	        	    		themeDisplay.getUser().getLogin(), themeDisplay.getUser().getLoginIP());
	        	    if(i!=0){
	        	    	valor = false;
	        	    	 logger.info("Valor de procesar notificacion 12B: "+valor);
	        	    	break;
	        	    }
	        	    
  				}else if(FiseConstants.NOMBRE_FORMATO_12C.equals(not.getFormato())){ 
  					FiseFormato12CCPK pk = new FiseFormato12CCPK();
  					pk.setCodEmpresa(not.getCodEmpresa());
  					pk.setAnoPresentacion(new Long(not.getAnioPres()));
  	  		        pk.setMesPresentacion(new Long(not.getMesPres()));
  	  		        pk.setEtapa(not.getEtapa()); 
  	  		        FiseFormato12CC formato12C = formatoService12C.obtenerFormato12CCByPK(pk);
  	  		        Formato12C12D13Generic formato13Generic = new Formato12C12D13Generic(formato12C);
				    int i = commonService.validarFormatos_12C12D13A(formato13Generic, FiseConstants.NOMBRE_FORMATO_12C,
				    		themeDisplay.getUser().getLogin(), themeDisplay.getUser().getLoginIP());
				    logger.info("Valor del item de procesar notificacion 12C: "+i);
				    if(i!=0){
				    	valor = false;
				    	 logger.info("Valor de procesar notificacion 12C: "+valor);
				    	break;
		  		    } 
				    
  				}else if(FiseConstants.NOMBRE_FORMATO_12D.equals(not.getFormato())){ 
  					FiseFormato12DCPK pk = new FiseFormato12DCPK();
  					pk.setCodEmpresa(not.getCodEmpresa());
  					pk.setAnoPresentacion(new Long(not.getAnioPres()));
  	  		        pk.setMesPresentacion(new Long(not.getMesPres()));
  	  		        pk.setEtapa(not.getEtapa()); 
  					FiseFormato12DC formato12D = formatoService12D.obtenerFormato12DCByPK(pk);
  					Formato12C12D13Generic formato12Generic = new Formato12C12D13Generic(formato12D);
  					int i = commonService.validarFormatos_12C12D13A(formato12Generic, FiseConstants.NOMBRE_FORMATO_12D,
  							themeDisplay.getUser().getLogin(), themeDisplay.getUser().getLoginIP());
  					 logger.info("Valor del item de procesar notificacion 12D: "+i);
  					if (i != 0) {
  						valor = false;
  						 logger.info("Valor de procesar notificacion 12D: "+valor);
				    	break;
  					}  		 					
  					
  				}else if(FiseConstants.NOMBRE_FORMATO_13A.equals(not.getFormato())){ 
  					FiseFormato13ACPK pk = new FiseFormato13ACPK();
  					pk.setCodEmpresa(not.getCodEmpresa());
  	  				pk.setAnoPresentacion(new Long(not.getAnioPres()));
  	  		        pk.setMesPresentacion(new Long(not.getMesPres()));
  	  		        pk.setEtapa(not.getEtapa());  	 
  	  		        FiseFormato13AC formato13A = formatoService13A.obtenerFormato13ACByPK(pk);	
  	  		        Formato12C12D13Generic formato13Generic = new Formato12C12D13Generic(formato13A);
  	  		       int i = commonService.validarFormatos_12C12D13A(formato13Generic, FiseConstants.NOMBRE_FORMATO_13A,
  	  		    		   themeDisplay.getUser().getLogin(), themeDisplay.getUser().getLoginIP());
  	  		       if(i!=0){
	  		          valor = false;
	  		        logger.info("Valor de procesar notificacion 13A: "+valor);
	  		          break;
	  		        } 	  		       
  				}else if(FiseConstants.NOMBRE_FORMATO_14A.equals(not.getFormato())){ 
  					FiseFormato14ACPK pk = new FiseFormato14ACPK();
  					pk.setCodEmpresa(not.getCodEmpresa());
  	  				pk.setAnoPresentacion(new Long(not.getAnioPres()));
  	  		        pk.setMesPresentacion(new Long(not.getMesPres()));
  			        pk.setAnoInicioVigencia(new Long(not.getAnioIniVig()));
  			        pk.setAnoFinVigencia(new Long(not.getAnioFinVig()));
  			        pk.setEtapa(not.getEtapa());  				        
  			        FiseFormato14AC formato14A = formatoService14A.obtenerFormato14ACByPK(pk);	
  			        Formato14Generic formato14Generic = new Formato14Generic(formato14A);
  		    	   int i = commonService.validarFormatos_14(formato14Generic, FiseConstants.NOMBRE_FORMATO_14A,
  		    			   themeDisplay.getUser().getLogin(), themeDisplay.getUser().getLoginIP());
  		    	   if(i!=0){
	  		          valor = false;
	  		          logger.info("Valor de procesar notificacion 14A: "+valor);
	  		          break;
	  		       } 	  		
  				}else if(FiseConstants.NOMBRE_FORMATO_14B.equals(not.getFormato())){ 
  					FiseFormato14BCPK pk = new FiseFormato14BCPK();
  					pk.setCodEmpresa(not.getCodEmpresa());
  	  				pk.setAnoPresentacion(new Long(not.getAnioPres()));
  	  		        pk.setMesPresentacion(new Long(not.getMesPres()));
  			        pk.setAnoInicioVigencia(new Long(not.getAnioIniVig()));
  			        pk.setAnoFinVigencia(new Long(not.getAnioFinVig()));
  			        pk.setEtapa(not.getEtapa());  						        
  			        FiseFormato14BC formato14B = formatoService14B.obtenerFormato14BCByPK(pk);	
  			        Formato14Generic formato14Generic = new Formato14Generic(formato14B);
  		    	    int i = commonService.validarFormatos_14(formato14Generic, FiseConstants.NOMBRE_FORMATO_14B,
  		    	    		themeDisplay.getUser().getLogin(), themeDisplay.getUser().getLoginIP());
  		    	   if(i!=0){
	  		          valor = false;
	  		          logger.info("Valor de procesar notificacion 14B: "+valor);
	  		          break;
	  		       }  			   
  				}else if(FiseConstants.NOMBRE_FORMATO_14C.equals(not.getFormato())){ 
  					Formato14CBean f14C = new Formato14CBean();  					
  					f14C.setCodEmpresa(not.getCodEmpresa());
  					f14C.setAnioPres(not.getAnioPres());
  					f14C.setMesPres(not.getMesPres());
  					f14C.setAnoIniVigencia(not.getAnioIniVig());
  					f14C.setAnoFinVigencia(not.getAnioFinVig());
  					f14C.setEtapa(not.getEtapa());
  					FiseFormato14CC formato14C = formatoService14C.obtenerFiseFormato14CC(f14C);
  					Formato14Generic formato14Generic = new Formato14Generic(formato14C);
  			    	int i = commonService.validarFormatos_14(formato14Generic, FiseConstants.NOMBRE_FORMATO_14C,
  			    			themeDisplay.getUser().getLogin(), themeDisplay.getUser().getLoginIP());
  			    	if(i!=0){
  			    		valor = false;
  			    		 logger.info("Valor de procesar notificacion 14C: "+valor);
  			    		break;
  			    	}  	
  				}				
  			}//fin del for de la lista		
  			
  			if(valor && lista.size()>0){
  				logger.info("si hay datos");
  				jsonObj.put("resultado", "OK");	   
  			}else if(lista.size()==0){
  				logger.info("ningun dato para procesar"); 
  				jsonObj.put("resultado", "NINGUNO");	  	
  			}else{
  				jsonObj.put("resultado", "ERROR");	   	
  			}
  			logger.info("arreglo json:"+jsonObj);
  			PrintWriter pw = response.getWriter();
  			pw.write(jsonObj.toString());
  			pw.flush();
  			pw.close();  			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}	
	
	
	@ResourceMapping("verObservacionesValidacion")
  	public void verObservaciones(ResourceRequest request,ResourceResponse response,
  			 @ModelAttribute("notificacionBean")NotificacionBean n){
		
		HttpServletRequest req = PortalUtil.getHttpServletRequest(request);
		HttpSession session = req.getSession();
		
		try{
			response.setContentType("application/json");
			
			PortletRequest pRequest = (PortletRequest) request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
			
			JSONArray jsonArray = null;
			
			boolean valorObs = false;
			
			logger.info("Entrando a ver observaciones notificacion"); 				
			logger.info("Codigo empresa:  "+ n.getCodEmpresa()); 
			logger.info("anio pres:  "+ n.getAnioPres());	
			logger.info("mes pres:  "+ n.getMesPres());
			logger.info("formato:  "+ n.getFormato());	
			logger.info("etapa:  "+ n.getEtapa());	
			logger.info("anio ejec:  "+ n.getAnioEjec());
			logger.info("mes ejec:  "+ n.getMesEjec());
			logger.info("anio ini vige:  "+ n.getAnioIniVig());
			logger.info("anio fin vige:  "+ n.getAnioFinVig());	 
  			 							
			if(FiseConstants.NOMBRE_FORMATO_12A.equals(n.getFormato())){
				jsonArray= new JSONArray();
				FiseFormato12ACPK pk = new FiseFormato12ACPK();
				pk.setCodEmpresa(n.getCodEmpresa());
				pk.setAnoPresentacion(new Long(n.getAnioPres()));
				pk.setMesPresentacion(new Long(n.getMesPres()));
				pk.setAnoEjecucionGasto(new Long(n.getAnioEjec()));
				pk.setMesEjecucionGasto(new Long(n.getMesEjec()));
				pk.setEtapa(n.getEtapa());  	  			        
				FiseFormato12AC formato12A = formatoService12A.obtenerFormato12ACByPK(pk);	
				if(formato12A!=null){					
					cargarListaObservaciones12A(formato12A.getFiseFormato12ADs());	
					for (MensajeErrorBean error : listaObservaciones) {
		  				JSONObject jsonObj = new JSONObject();
						jsonObj.put("id", error.getId());	
						jsonObj.put("descZonaBenef", error.getDescZonaBenef());
						jsonObj.put("codigo", error.getCodigo());			
						jsonObj.put("descripcion", error.getDescripcion());						
						jsonArray.put(jsonObj);		
					}		    			
			    	/**exportar excel*/			
					fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_FORMATO_VAL,
							FiseConstants.NOMBRE_EXCEL_VALIDACION_F12A, 
							FiseConstants.NOMBRE_HOJA_VALIDACION, listaObservaciones);
					valorObs = true;
				}    
			}else if(FiseConstants.NOMBRE_FORMATO_12B.equals(n.getFormato())){ 
				jsonArray= new JSONArray();
				FiseFormato12BCPK pk=new FiseFormato12BCPK();
				pk.setCodEmpresa(n.getCodEmpresa());
				pk.setAnoPresentacion(new Integer(n.getAnioPres()));
				pk.setMesPresentacion(new Integer(n.getMesPres()));
				pk.setAnoEjecucionGasto(new Integer(n.getAnioEjec()));
				pk.setMesEjecucionGasto(new Integer(n.getMesEjec()));
				pk.setEtapa(n.getEtapa());  
				FiseFormato12BC formato12B =formatoService12B.getFormatoCabeceraById(pk);
				if(formato12B!=null){
					cargarListaObservaciones12B(formato12B.getListaDetalle12BDs());
					for (MensajeErrorBean error : listaObservaciones) {
						JSONObject jsonObj = new JSONObject();
						jsonObj.put("id", error.getId());	
						jsonObj.put("descZonaBenef", error.getDescZonaBenef());
						jsonObj.put("codigo", error.getCodigo());			
						jsonObj.put("descripcion", error.getDescripcion());						
						jsonArray.put(jsonObj);		
					}
					/**exportar excel*/			
					fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_FORMATO_VAL,
							FiseConstants.NOMBRE_EXCEL_VALIDACION_F12B, 
							FiseConstants.NOMBRE_HOJA_VALIDACION, listaObservaciones);
					valorObs = true;
				}			
			}else if(FiseConstants.NOMBRE_FORMATO_12C.equals(n.getFormato())){ 
				jsonArray= new JSONArray();
				FiseFormato12CCPK pk = new FiseFormato12CCPK();
				pk.setCodEmpresa(n.getCodEmpresa());
				pk.setAnoPresentacion(new Long(n.getAnioPres()));
				pk.setMesPresentacion(new Long(n.getMesPres()));
				pk.setEtapa(n.getEtapa()); 
				FiseFormato12CC formato12C = formatoService12C.obtenerFormato12CCByPK(pk);
				if(formato12C!=null){
					cargarListaObservaciones12C(formato12C.getFiseFormato12CDs());
					for (MensajeErrorBean error : listaObservaciones) {
						JSONObject jsonObj = new JSONObject();
						jsonObj.put("id", error.getId());
						jsonObj.put("nroItemEtapa", error.getNroItemEtapa());
						jsonObj.put("codigo", error.getCodigo());
						jsonObj.put("descripcion", error.getDescripcion());
						jsonObj.put("descEtapaEjecucion", error.getDescEtapaEjecucion());					
						jsonArray.put(jsonObj);
					}
					/**exportar excel*/			
					fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_FORMATO_VAL,
							FiseConstants.NOMBRE_EXCEL_VALIDACION_F12C, 
							FiseConstants.NOMBRE_HOJA_VALIDACION, listaObservaciones);
					valorObs = true;	
				}				
			}else if(FiseConstants.NOMBRE_FORMATO_12D.equals(n.getFormato())){ 
				jsonArray= new JSONArray();
				FiseFormato12DCPK pk = new FiseFormato12DCPK();
				pk.setCodEmpresa(n.getCodEmpresa());
				pk.setAnoPresentacion(new Long(n.getAnioPres()));
				pk.setMesPresentacion(new Long(n.getMesPres()));
				pk.setEtapa(n.getEtapa()); 
				FiseFormato12DC formato12D = formatoService12D.obtenerFormato12DCByPK(pk); 
				if(formato12D!=null){
					cargarListaObservaciones12D(formato12D.getFiseFormato12DDs());
					for (MensajeErrorBean error : listaObservaciones) {
						JSONObject jsonObj = new JSONObject();
						jsonObj.put("id", error.getId());
						jsonObj.put("nroItemEtapa", error.getNroItemEtapa());
						jsonObj.put("codigo", error.getCodigo());
						jsonObj.put("descripcion", error.getDescripcion());
						jsonObj.put("descEtapaEjecucion", error.getDescEtapaEjecucion());			
						jsonArray.put(jsonObj);
					}
					/**exportar excel*/			
					fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_FORMATO_VAL,
							FiseConstants.NOMBRE_EXCEL_VALIDACION_F12D, 
							FiseConstants.NOMBRE_HOJA_VALIDACION, listaObservaciones);
					valorObs = true;	
				}			
			}else if(FiseConstants.NOMBRE_FORMATO_13A.equals(n.getFormato())){
				jsonArray= new JSONArray();
				FiseFormato13ACPK pk = new FiseFormato13ACPK();
				pk.setCodEmpresa(n.getCodEmpresa());
				pk.setAnoPresentacion(new Long(n.getAnioPres()));
				pk.setMesPresentacion(new Long(n.getMesPres()));
				pk.setEtapa(n.getEtapa());
				
				FiseFormato13AC formato13A = formatoService13A.obtenerFormato13ACByPK(pk);	
				if(formato13A!=null){
					cargarListaObservaciones13A(formato13A.getFiseFormato13ADs());
					for (MensajeErrorBean error : listaObservaciones) {
						JSONObject jsonObj = new JSONObject();
						jsonObj.put("id", error.getId());
						jsonObj.put("descZonaBenef", error.getDescZonaBenef());
						jsonObj.put("codigo", error.getCodigo());
						jsonObj.put("descripcion", error.getDescripcion());
						jsonObj.put("descSectorTipico", error.getDescCodSectorTipico());					
						jsonArray.put(jsonObj);
					}
					// **exportar excel
					fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_FORMATO_VAL,
							FiseConstants.NOMBRE_EXCEL_VALIDACION_F13A, 
							FiseConstants.NOMBRE_HOJA_VALIDACION, listaObservaciones);
					valorObs = true;	
				}
			}else if(FiseConstants.NOMBRE_FORMATO_14A.equals(n.getFormato())){ 
				jsonArray= new JSONArray();
				FiseFormato14ACPK pk = new FiseFormato14ACPK();
				pk.setCodEmpresa(n.getCodEmpresa());
				pk.setAnoPresentacion(new Long(n.getAnioPres()));
				pk.setMesPresentacion(new Long(n.getMesPres()));
				pk.setAnoInicioVigencia(new Long(n.getAnioIniVig()));
				pk.setAnoFinVigencia(new Long(n.getAnioFinVig()));
				pk.setEtapa(n.getEtapa());  				        
				FiseFormato14AC formato14A = formatoService14A.obtenerFormato14ACByPK(pk);
				if(formato14A!=null){
					cargarListaObservaciones14A(formato14A.getFiseFormato14ADs());		    	
			    	for (MensajeErrorBean error : listaObservaciones) {
		  				JSONObject jsonObj = new JSONObject();
						jsonObj.put("id", error.getId());	
						jsonObj.put("descZonaBenef", error.getDescZonaBenef());
						jsonObj.put("codigo", error.getCodigo());			
						jsonObj.put("descripcion", error.getDescripcion());				
						jsonArray.put(jsonObj);		
					}	    			
			    	//**exportar excel
			    	fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_FORMATO_VAL,
			    			FiseConstants.NOMBRE_EXCEL_VALIDACION_F14A, 
			    			FiseConstants.NOMBRE_HOJA_VALIDACION, listaObservaciones);
			    	valorObs = true;	
				}		    	
			}else if(FiseConstants.NOMBRE_FORMATO_14B.equals(n.getFormato())){
				jsonArray= new JSONArray();
				FiseFormato14BCPK pk = new FiseFormato14BCPK();
				pk.setCodEmpresa(n.getCodEmpresa());
				pk.setAnoPresentacion(new Long(n.getAnioPres()));
				pk.setMesPresentacion(new Long(n.getMesPres()));
				pk.setAnoInicioVigencia(new Long(n.getAnioIniVig()));
				pk.setAnoFinVigencia(new Long(n.getAnioFinVig()));
				pk.setEtapa(n.getEtapa());  						        
				FiseFormato14BC formato14B = formatoService14B.obtenerFormato14BCByPK(pk);
				if(formato14B!=null){
					cargarListaObservaciones14B(formato14B.getFiseFormato14BDs());		    
			    	for (MensajeErrorBean error : listaObservaciones) {
		  				JSONObject jsonObj = new JSONObject();
						jsonObj.put("id", error.getId());	
						jsonObj.put("descZonaBenef", error.getDescZonaBenef());
						jsonObj.put("codigo", error.getCodigo());			
						jsonObj.put("descripcion", error.getDescripcion());					
						jsonArray.put(jsonObj);		
					}	    			
			    	//**exportar excel
			    	fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_FORMATO_VAL, 
			    			FiseConstants.NOMBRE_EXCEL_VALIDACION_F14B, 
			    			FiseConstants.NOMBRE_HOJA_VALIDACION, listaObservaciones);
			    	valorObs = true;	
				}	    	
			}else if(FiseConstants.NOMBRE_FORMATO_14C.equals(n.getFormato())){ 
				jsonArray= new JSONArray();
				Formato14CBean f14C = new Formato14CBean();				
				f14C.setCodEmpresa(n.getCodEmpresa());
				f14C.setAnioPres(n.getAnioPres());
				f14C.setMesPres(n.getMesPres());
				f14C.setAnoIniVigencia(n.getAnioIniVig());
				f14C.setAnoFinVigencia(n.getAnioFinVig());
				f14C.setEtapa(n.getEtapa());
				FiseFormato14CC formato14C = formatoService14C.obtenerFiseFormato14CC(f14C);
				if(formato14C!=null){
					cargarListaObservaciones14C(formato14C.getListaDetalle14cDs());		    	
			    	for (MensajeErrorBean error : listaObservaciones) {
		  				JSONObject jsonObj = new JSONObject();
						jsonObj.put("id", error.getId());	
						jsonObj.put("descZonaBenef", error.getDescZonaBenef());
						jsonObj.put("codigo", error.getCodigo());			
						jsonObj.put("descripcion", error.getDescripcion());					
						jsonArray.put(jsonObj);		
					}		    			
			    	//**exportar excel
			    	fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_FORMATO_VAL,
			    			FiseConstants.NOMBRE_EXCEL_VALIDACION_F14C,
			    			FiseConstants.NOMBRE_HOJA_VALIDACION, listaObservaciones);
			    	valorObs = true;	
				}	    	
			}
			 pRequest.getPortletSession().setAttribute("codigoEmpresa", n.getCodEmpresa(), PortletSession.APPLICATION_SCOPE);
			 pRequest.getPortletSession().setAttribute("anioPresentacion", n.getAnioPres(), PortletSession.APPLICATION_SCOPE);
			 pRequest.getPortletSession().setAttribute("mesPresentacion", n.getMesPres(), PortletSession.APPLICATION_SCOPE);
			 pRequest.getPortletSession().setAttribute("formato", n.getFormato(), PortletSession.APPLICATION_SCOPE);
			 pRequest.getPortletSession().setAttribute("etapa", n.getEtapa(), PortletSession.APPLICATION_SCOPE);
			 pRequest.getPortletSession().setAttribute("anioIniVig", n.getAnioIniVig(), PortletSession.APPLICATION_SCOPE);
			 pRequest.getPortletSession().setAttribute("anioFinVig", n.getAnioFinVig(), PortletSession.APPLICATION_SCOPE);	
			 pRequest.getPortletSession().setAttribute("anioEjec", n.getAnioEjec(), PortletSession.APPLICATION_SCOPE);
			 pRequest.getPortletSession().setAttribute("mesEjec", n.getMesEjec(), PortletSession.APPLICATION_SCOPE);			 
			
			if(!valorObs){
				jsonArray= new JSONArray();
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("id", "Ocurrio un error al mostrar las observaciones");						
				jsonArray.put(jsonObj);		
			}
			logger.info("arreglo json:"+jsonArray);			
  			PrintWriter pw = response.getWriter();
  			pw.write(jsonArray.toString());
  			pw.flush();
  			pw.close();  			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	private void cargarListaObservaciones12A(List<FiseFormato12AD> listaDetalle){
		int cont=0;
		listaObservaciones = new ArrayList<MensajeErrorBean>();
		for (FiseFormato12AD detalle : listaDetalle) {
			detalle.setFiseFormato12ADObs(formatoService12A.listarFormato12ADObByFormato12AD(detalle));
			List<FiseFormato12ADOb> listaObser = formatoService12A.listarFormato12ADObByFormato12AD(detalle);
			for (FiseFormato12ADOb observacion : listaObser) {
				cont++;
				MensajeErrorBean obs = new MensajeErrorBean();
				obs.setId(cont);
				obs.setDescZonaBenef(fiseUtil.getMapaZonaBenef().get(observacion.getId().getIdZonaBenef()));
				obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
				obs.setDescripcion(observacion.getFiseObservacion().getDescripcion());
				//obs.setDescCodSectorTipico(mapaSectorTipico.get(observacion.getId().getCodSectorTipico()));
				listaObservaciones.add(obs);
			}
		}
	}
	
	private void cargarListaObservaciones12B(List<FiseFormato12BD> listaDetalle){
		int cont=0;
		listaObservaciones = new ArrayList<MensajeErrorBean>();		
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
				listaObservaciones.add(obs);
			}
		}
	}
  	
  	private void cargarListaObservaciones12C(List<FiseFormato12CD> listaDetalle){
		int cont=0;
		listaObservaciones = new ArrayList<MensajeErrorBean>();
		for (FiseFormato12CD detalle : listaDetalle) {
			List<FiseFormato12CDOb> listaObser = formatoService12C.listarFormato12CDObByFormato12CD(detalle);
			for (FiseFormato12CDOb observacion : listaObser) {
				cont++;
				MensajeErrorBean obs = new MensajeErrorBean();
				obs.setId(cont);
				obs.setNroItemEtapa(observacion.getId().getNumeroItemEtapa());
				obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
				obs.setDescripcion(observacion.getFiseObservacion().getDescripcion());
				obs.setDescEtapaEjecucion(mapaEtapaEjecucion.get(observacion.getId().getEtapaEjecucion()));
				listaObservaciones.add(obs);
			}
		}
	} 
  	
  	private void cargarListaObservaciones12D(List<FiseFormato12DD> listaDetalle) {
		int cont = 0;
		listaObservaciones = new ArrayList<MensajeErrorBean>();
		for (FiseFormato12DD detalle : listaDetalle) {
			List<FiseFormato12DDOb> listaObser = formatoService12D.listarFormato12DDObByFormato12DD(detalle);
			for (FiseFormato12DDOb observacion : listaObser) {
				cont++;
				MensajeErrorBean obs = new MensajeErrorBean();
				obs.setId(cont);
				obs.setNroItemEtapa(observacion.getId().getNumeroItemEtapa());
				obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
				obs.setDescripcion(observacion.getFiseObservacion().getDescripcion());				
				obs.setDescEtapaEjecucion(mapaEtapaEjecucion.get(observacion.getId().getEtapaEjecucion()));
				listaObservaciones.add(obs);
			}
		}
	}	
	
	private void cargarListaObservaciones13A(List<FiseFormato13AD> listaDetalle) {
		int cont = 0;
		listaObservaciones = new ArrayList<MensajeErrorBean>();
		for (FiseFormato13AD detalle : listaDetalle) {
			List<FiseFormato13ADOb> listaObser = formatoService13A.listarFormato13ADObByFormato13AD(detalle);
			for (FiseFormato13ADOb observacion : listaObser) {
				cont++;
				MensajeErrorBean obs = new MensajeErrorBean();
				obs.setId(cont);
				obs.setDescZonaBenef(fiseUtil.getMapaZonaBenef().get(observacion.getId().getIdZonaBenef()));
				obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
				obs.setDescripcion(observacion.getFiseObservacion().getDescripcion());
				obs.setDescCodSectorTipico(mapaSectorTipico.get(observacion.getId().getCodSectorTipico()));
				listaObservaciones.add(obs);
			}
		}
	}
	
	private void cargarListaObservaciones14A(List<FiseFormato14AD> listaDetalle){
		int cont=0;
		listaObservaciones = new ArrayList<MensajeErrorBean>();
		for (FiseFormato14AD detalle : listaDetalle) {
			detalle.setFiseFormato14ADObs(formatoService14A.listarFormato14ADObByFormato14AD(detalle));
			List<FiseFormato14ADOb> listaObser = formatoService14A.listarFormato14ADObByFormato14AD(detalle);
			for (FiseFormato14ADOb observacion : listaObser) {
				cont++;
				MensajeErrorBean obs = new MensajeErrorBean();
				obs.setId(cont);
				obs.setDescZonaBenef(fiseUtil.getMapaZonaBenef().get(observacion.getId().getIdZonaBenef()));
				obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
				obs.setDescripcion(observacion.getFiseObservacion().getDescripcion());
				listaObservaciones.add(obs);
			}
		}
	}
	
	private void cargarListaObservaciones14B(List<FiseFormato14BD> listaDetalle){
		int cont=0;
		listaObservaciones = new ArrayList<MensajeErrorBean>();
		for (FiseFormato14BD detalle : listaDetalle) {
			detalle.setFiseFormato14BDObs(formatoService14B.listarFormato14BDObByFormato14BD(detalle));
			List<FiseFormato14BDOb> listaObser = formatoService14B.listarFormato14BDObByFormato14BD(detalle);
			for (FiseFormato14BDOb observacion : listaObser) {
				cont++;
				MensajeErrorBean obs = new MensajeErrorBean();
				obs.setId(cont);
				obs.setDescZonaBenef(fiseUtil.getMapaZonaBenef().get(observacion.getId().getIdZonaBenef()));
				obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
				obs.setDescripcion(observacion.getFiseObservacion().getDescripcion());			
				listaObservaciones.add(obs);
			}
		}
	}
	
	private void cargarListaObservaciones14C(List<FiseFormato14CD> listaDetalle) throws Exception{
		int cont=0;
		listaObservaciones = new ArrayList<MensajeErrorBean>();
		for (FiseFormato14CD detalle : listaDetalle) {			
			List<FiseFormato14CDOb> listaObser = formatoService14C.listaObservacionesF14C(detalle);
			logger.info("Tama�o de lista de observaciones:  "+listaObser.size()); 
			for (FiseFormato14CDOb o : listaObser) {			
				cont++;
				MensajeErrorBean obs = new MensajeErrorBean();
				obs.setId(cont);
				obs.setDescZonaBenef(fiseUtil.getMapaZonaBenef().get(o.getId().getIdZonaBenef()));
				//obs.setDescZonaBenef(mapaZonaBenef.get(o.getId().getIdZonaBenef()));
				obs.setCodigo(o.getFiseObservacion().getIdObservacion());
				obs.setDescripcion(o.getFiseObservacion().getDescripcion());
				listaObservaciones.add(obs);							
			}
		}
	}	
	
	@ResourceMapping("reporteValidacionNotificacion")
	public void reporteNotificacion(ResourceRequest request,ResourceResponse response,
 			 @ModelAttribute("notificacionBean")NotificacionBean n){
		try {
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();
	        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
	        
	        logger.info("Codigo empresa:  "+ n.getCodEmpresa());
	        
	        PortletRequest pRequest = (PortletRequest) request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
	        
		    JSONArray jsonArray = new JSONArray(); 		   
	
		    if( listaObservaciones!=null ){
		    	session.setAttribute("lista", listaObservaciones);
		    }	   
		    String codEmpresa = (String)pRequest.getPortletSession().getAttribute("codigoEmpresa", 
		    		PortletSession.APPLICATION_SCOPE);
		    String anioPres = (String)pRequest.getPortletSession().getAttribute("anioPresentacion", 
		    		PortletSession.APPLICATION_SCOPE);
		    String mesPres = (String)pRequest.getPortletSession().getAttribute("mesPresentacion", 
		    		PortletSession.APPLICATION_SCOPE);
		    String formato = (String)pRequest.getPortletSession().getAttribute("formato", 
		    		PortletSession.APPLICATION_SCOPE);
		    String etapa = (String)pRequest.getPortletSession().getAttribute("etapa", 
		    		PortletSession.APPLICATION_SCOPE); 		    
		    String anioIniVig = (String)pRequest.getPortletSession().getAttribute("anioIniVig", 
		    		PortletSession.APPLICATION_SCOPE);
		    String anioFinVig = (String)pRequest.getPortletSession().getAttribute("anioFinVig", 
		    		PortletSession.APPLICATION_SCOPE);		    
		    String anioEjec = (String)pRequest.getPortletSession().getAttribute("anioEjec", 
		    		PortletSession.APPLICATION_SCOPE);
		    String mesEjec = (String)pRequest.getPortletSession().getAttribute("mesEjec", 
		    		PortletSession.APPLICATION_SCOPE);	   
		   logger.info("Anio Ejec:  "+anioEjec); 
		   logger.info("Mes Ejec:  "+mesEjec); 
		    
		    CfgTabla tabla = null;
	    	String descripcionFormato = "";   	
	    	
	    	Map<String, Object> mapa = new HashMap<String, Object>();
	    	mapa.put(FiseConstants.PARAM_IMG_LOGOTIPO, session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg"));
	    	mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
	    	
	    	if(FiseConstants.NOMBRE_FORMATO_12A.equals(formato)){	
	    		
	    		session.setAttribute("nombreReporte","validacion");//nombre del jasper
	    		session.setAttribute("nombreArchivo","validacion");//nombre para mostrar el reporte
	    		session.setAttribute("tipoFormato",FiseConstants.TIPO_FORMATO_VAL);
	    		session.setAttribute("tipoArchivo","0"); //PDF   
	    		
	    		tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12A);
	    		if( tabla!=null ){
		    		descripcionFormato = tabla.getDescripcionTabla();
		    	}
	    		
			}else if(FiseConstants.NOMBRE_FORMATO_12B.equals(formato)){
				
				session.setAttribute("nombreReporte","validacion");//nombre del jasper
	    		session.setAttribute("nombreArchivo","validacion");//nombre para mostrar el reporte
	    		session.setAttribute("tipoFormato",FiseConstants.TIPO_FORMATO_VAL);
	    		session.setAttribute("tipoArchivo","0"); //PDF   
	    		
				tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12B);
	    		if( tabla!=null ){
		    		descripcionFormato = tabla.getDescripcionTabla();
		    	}
	    		
			}else if(FiseConstants.NOMBRE_FORMATO_12C.equals(formato)){ 
				
				session.setAttribute("nombreReporte","validacion12");//nombre del jasper
	    		session.setAttribute("nombreArchivo","validacion");//nombre para mostrar el reporte
	    		session.setAttribute("tipoFormato",FiseConstants.TIPO_FORMATO_VAL);
	    		session.setAttribute("tipoArchivo","0"); //PDF   
	    		
				tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12C);
	    		if( tabla!=null ){
		    		descripcionFormato = tabla.getDescripcionTabla();
		    	}
	    		
			}else if(FiseConstants.NOMBRE_FORMATO_12D.equals(formato)){
				
				session.setAttribute("nombreReporte","validacion12");//nombre del jasper
	    		session.setAttribute("nombreArchivo","validacion");//nombre para mostrar el reporte
	    		session.setAttribute("tipoFormato",FiseConstants.TIPO_FORMATO_VAL);
	    		session.setAttribute("tipoArchivo","0"); //PDF   
	    		
				tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12D);
	    		if( tabla!=null ){
		    		descripcionFormato = tabla.getDescripcionTabla();
		    	}
	    		
			}else if(FiseConstants.NOMBRE_FORMATO_13A.equals(formato)){
				
				session.setAttribute("nombreReporte","validacion13");//nombre del jasper
	    		session.setAttribute("nombreArchivo","validacion");//nombre para mostrar el reporte
	    		session.setAttribute("tipoFormato",FiseConstants.TIPO_FORMATO_VAL);
	    		session.setAttribute("tipoArchivo","0"); //PDF   
	    		
				tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO13A);
	    		if( tabla!=null ){
		    		descripcionFormato = tabla.getDescripcionTabla();
		    	}
	    		/*mapa.put(FiseConstants.PARAM_ANO_INICIO_VIGENCIA, anioIniVig!=null?anioIniVig:"");
				mapa.put(FiseConstants.PARAM_ANO_FIN_VIGENCIA, anioFinVig!=null?anioFinVig:"");*/
	    		mapa.put(FiseConstants.PARAM_ANO_INICIO_VIGENCIA, (anioIniVig!=null && !anioIniVig.equals("---")) ?Long.parseLong(anioIniVig):0);
				mapa.put(FiseConstants.PARAM_ANO_FIN_VIGENCIA, (anioFinVig!=null && !anioFinVig.equals("---"))?Long.parseLong(anioFinVig):0);
	    		
			}else if(FiseConstants.NOMBRE_FORMATO_14A.equals(formato)){ 
				
				session.setAttribute("nombreReporte","validacion");//nombre del jasper
	    		session.setAttribute("nombreArchivo","validacion");//nombre para mostrar el reporte
	    		session.setAttribute("tipoFormato",FiseConstants.TIPO_FORMATO_VAL);
	    		session.setAttribute("tipoArchivo","0"); //PDF   
	    		
				tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14A);
	    		if( tabla!=null ){
		    		descripcionFormato = tabla.getDescripcionTabla();
		    	}	
	    		
	    		mapa.put(FiseConstants.PARAM_ANO_INICIO_VIGENCIA, (anioIniVig!=null && !anioIniVig.equals("---")) ?Long.parseLong(anioIniVig):0);
				mapa.put(FiseConstants.PARAM_ANO_FIN_VIGENCIA, (anioFinVig!=null && !anioFinVig.equals("---"))?Long.parseLong(anioFinVig):0);
	    		
			}else if(FiseConstants.NOMBRE_FORMATO_14B.equals(formato)){
				
				session.setAttribute("nombreReporte","validacion14");//nombre del jasper
	    		session.setAttribute("nombreArchivo","validacion");//nombre para mostrar el reporte
	    		session.setAttribute("tipoFormato",FiseConstants.TIPO_FORMATO_VAL);
	    		session.setAttribute("tipoArchivo","0"); //PDF   
	    		
				tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14B);
	    		if( tabla!=null ){
		    		descripcionFormato = tabla.getDescripcionTabla();
		    	}
	    		
	    		mapa.put(FiseConstants.PARAM_ANO_INICIO_VIGENCIA, (anioIniVig!=null && !anioIniVig.equals("---")) ?Long.parseLong(anioIniVig):0);
				mapa.put(FiseConstants.PARAM_ANO_FIN_VIGENCIA, (anioFinVig!=null && !anioFinVig.equals("---"))?Long.parseLong(anioFinVig):0);
	    		
			}else if(FiseConstants.NOMBRE_FORMATO_14C.equals(formato)){ 
				
				session.setAttribute("nombreReporte","validacion14");//nombre del jasper
	    		session.setAttribute("nombreArchivo","validacion");//nombre para mostrar el reporte
	    		session.setAttribute("tipoFormato",FiseConstants.TIPO_FORMATO_VAL);
	    		session.setAttribute("tipoArchivo","0"); //PDF   
	    		
				tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14C);
	    		if( tabla!=null ){
		    		descripcionFormato = tabla.getDescripcionTabla();
		    	}
	    		
	    		mapa.put(FiseConstants.PARAM_ANO_INICIO_VIGENCIA, (anioIniVig!=null && !anioIniVig.equals("---")) ?Long.parseLong(anioIniVig):0);
				mapa.put(FiseConstants.PARAM_ANO_FIN_VIGENCIA, (anioFinVig!=null && !anioFinVig.equals("---"))?Long.parseLong(anioFinVig):0);
	    		
			} 	    	
		   	mapa.put(FiseConstants.PARAM_ANO_PRESENTACION, Long.parseLong(anioPres));
		   	mapa.put(FiseConstants.PARAM_DESC_MES_PRESENTACION, fiseUtil.getMapaMeses().get(Long.parseLong(mesPres)));
		   	mapa.put(FiseConstants.PARAM_USUARIO, themeDisplay.getUser().getLogin());
		   	mapa.put(FiseConstants.PARAM_NOMBRE_FORMATO, descripcionFormato);
		   	mapa.put(FiseConstants.PARAM_NRO_OBSERVACIONES, (listaObservaciones!=null && !listaObservaciones.isEmpty())?listaObservaciones.size():0);
			mapa.put(FiseConstants.PARAM_DESC_EMPRESA, mapaEmpresa.get(codEmpresa));
		   	mapa.put(FiseConstants.PARAM_ETAPA, etapa);   	
		   	
		   	session.setAttribute("mapa", mapa);	   
		   	
		    response.setContentType("application/json");
		    PrintWriter pw = response.getWriter();
		    pw.write(jsonArray.toString());
		    pw.flush();
		    pw.close();    
		    
		    /***Limpiando las variables en session*/
		    pRequest.getPortletSession().setAttribute("codigoEmpresa", "", PortletSession.APPLICATION_SCOPE);
			pRequest.getPortletSession().setAttribute("anioPresentacion", "", PortletSession.APPLICATION_SCOPE);
			pRequest.getPortletSession().setAttribute("mesPresentacion", "", PortletSession.APPLICATION_SCOPE);
			pRequest.getPortletSession().setAttribute("formato", "", PortletSession.APPLICATION_SCOPE);
			pRequest.getPortletSession().setAttribute("etapa", "", PortletSession.APPLICATION_SCOPE);
			pRequest.getPortletSession().setAttribute("anioIniVig", "", PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("anioFinVig", "", PortletSession.APPLICATION_SCOPE);		    
		    pRequest.getPortletSession().setAttribute("anioEjec", "", PortletSession.APPLICATION_SCOPE);
			pRequest.getPortletSession().setAttribute("mesEjec", "", PortletSession.APPLICATION_SCOPE);	
			
		}catch (Exception e) {
			logger.info("Error al mostrar el reporte de las observaciones: "+e); 
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@ResourceMapping("notificarValidacion")
  	public void notificarValidacion(ResourceRequest request,ResourceResponse response,
  			 @ModelAttribute("notificacionBean")NotificacionBean n){
		
		try{
			logger.info("ingresando a notificar validar ");  
			
			response.setContentType("application/json");			
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);	
			PortletRequest pRequest = (PortletRequest) request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
			
			JSONObject jsonObj = new JSONObject(); 
			List<FileEntryJSP> listaArchivo = new ArrayList<FileEntryJSP>();		
			String directorio =  null;	
			String nombreReporte = "";	
			String respuestaEmail ="";			
			boolean valor = false;
			String codEmpresa = n.getCodEmpresaBusq();				
			String optionFormato = n.getOptionFormato();
			String idgrupoInf = n.getGrupoInfBusq();
			String etapa = n.getEtapaBusq();    			
			logger.info("codigo empresa "+ codEmpresa);  			
  			logger.info("id Grupo inf "+ idgrupoInf);  	 			
  			logger.info("Option formato "+ optionFormato);
  			logger.info("etapa "+ etapa);	
  			logger.info("Desc Grupo inf.  "+n.getDescGrupoInf()); 
  			
  			
  			List<NotificacionBean> lista = (List<NotificacionBean>)pRequest.getPortletSession().getAttribute("listaNotificacion", 
		    		PortletSession.APPLICATION_SCOPE);
  			
  			if(lista!=null && lista.size()>0){  				
  				
  				String mensaje = commonService.notificarValidacionMensual(codEmpresa,
  						etapa, Long.valueOf(idgrupoInf), optionFormato, themeDisplay.getUser().getLogin(),
  						themeDisplay.getUser().getLoginIP());	
  				
  				logger.info("Valor del mensaje:  "+mensaje); 
  				
  				if(FiseConstants.ENVIO_EMAIL_OK_VALIDACION.equals(mensaje))
  				{ 
  					Map<String, Object> mapa = new HashMap<String, Object>();
  	  		    	mapa.put(FiseConstants.PARAM_IMG_LOGOTIPO, session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg"));
  	  		    	mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
  	  		    	
  	  		    	CfgTabla tabla = null;
  	  		    	String descripcionFormato = ""; 
  	  		    	String codEmpresaLista = "";
  	  		    	
  	  		    	//obtenemos el plazo
  	  		    	String  plazo = fisePeriodoEnvioGartService.listarPlazoMaximoEnvioObs(lista.get(0).getCodEmpresa(),
  	  				    	new Long(lista.get(0).getAnioPres()), new Long(lista.get(0).getMesPres()), FiseConstants.ETAPA_LEVOBS, lista.get(0).getFormato());
  	  		    	
  	  		    	logger.info("Plazo al envio de notificacion de observaciones:  "+plazo);
  	  				
  	  		    	for(NotificacionBean not :lista){
  	  					
  	  				    codEmpresaLista = not.getCodEmpresa();	  				   
  	  				    
  	  					if(FiseConstants.NOMBRE_FORMATO_12A.equals(not.getFormato())){  						
  	  						FiseFormato12ACPK pk = new FiseFormato12ACPK();
  	  						pk.setCodEmpresa(not.getCodEmpresa());
  	  						pk.setAnoPresentacion(new Long(not.getAnioPres()));
  	  						pk.setMesPresentacion(new Long(not.getMesPres()));
  	  						pk.setAnoEjecucionGasto(new Long(not.getAnioEjec()));
  	  						pk.setMesEjecucionGasto(new Long(not.getMesEjec()));
  	  						pk.setEtapa(not.getEtapa());  	  			        
  	  						FiseFormato12AC formato12A = formatoService12A.obtenerFormato12ACByPK(pk); 
  	  						
  	  						if(formato12A.getFiseFormato12ADs()!=null){
  	  						    cargarListaObservaciones12A(formato12A.getFiseFormato12ADs());	
  	  						}  	  						
  	  						
  	  						nombreReporte ="validacion";
  	  						directorio =  "/reports/"+nombreReporte+".jasper";
  	  						tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12A);
  	  			    		if( tabla!=null ){
  	  				    		descripcionFormato = tabla.getDescripcionTabla();
  	  				    	}
  	  			    		
  	  			    		mapa.put(FiseConstants.PARAM_ANO_PRESENTACION, Long.parseLong(not.getAnioPres()));
  	  					   	mapa.put(FiseConstants.PARAM_DESC_MES_PRESENTACION, 
  	  					   			fiseUtil.getMapaMeses().get(Long.parseLong(not.getMesPres())));
  	  					   	mapa.put(FiseConstants.PARAM_USUARIO, themeDisplay.getUser().getLogin());
  	  					   	mapa.put(FiseConstants.PARAM_NOMBRE_FORMATO, descripcionFormato);
  	  					   	mapa.put(FiseConstants.PARAM_NRO_OBSERVACIONES, (listaObservaciones!=null && 
  	  					   			!listaObservaciones.isEmpty())?listaObservaciones.size():0);
  	  						mapa.put(FiseConstants.PARAM_DESC_EMPRESA, mapaEmpresa.get(not.getCodEmpresa()));
  	  					   	mapa.put(FiseConstants.PARAM_ETAPA, not.getEtapa()); 
  	  					   	
  	  					    File reportFile12A = new File(session.getServletContext().getRealPath(directorio));
  	  	 		    	    byte[] bytes12A = null;
  	  	 		    	    if(listaObservaciones!=null && !listaObservaciones.isEmpty() && listaObservaciones.size()>0){
  	  	 		    	         bytes12A = JasperRunManager.runReportToPdf(reportFile12A.getPath(), mapa,
  	  	 			    		   new JRBeanCollectionDataSource(listaObservaciones));	
  	  	 		    	    }else{
  	  	 		    	        bytes12A = JasperRunManager.runReportToPdf(reportFile12A.getPath(), mapa,
  	  	 		    	        	new JREmptyDataSource()); 	  	 		    	   
  	  	 		    	    }  	  	 		    	    
  		  	 		    	if (bytes12A != null) {
  		   			    	   String nombre = FormatoUtil.nombreReporteGeneralAnexoObs(not.getCodEmpresa(),
  		   			    			   Long.valueOf(not.getAnioPres()), Long.valueOf(not.getMesPres()), 
  		   			    			   FiseConstants.NOMBRE_CONSOLIDADO_EMAIL,FiseConstants.TIPO_FORMATO_12A);	   			    	 
  		   			    	   FileEntry archivo12A = fiseUtil.subirDocumentoBytes(request, bytes12A, "application/pdf", nombre);	   			    	   
  		   			    	   if( archivo12A!=null ){
  		   			    		   FileEntryJSP fileEntryJsp = new FileEntryJSP();
  		   			    		   fileEntryJsp.setNombreArchivo(nombre);
  		   			    		   fileEntryJsp.setFileEntry(archivo12A);
  		   			    		   listaArchivo.add(fileEntryJsp);
  		   			    	   }
  		   			       } 
  		  	 		    	
  	  					}else if(FiseConstants.NOMBRE_FORMATO_12B.equals(not.getFormato())){   						
  	  						FiseFormato12BCPK pk=new FiseFormato12BCPK();
  	  						pk.setCodEmpresa(not.getCodEmpresa());
  	  						pk.setAnoPresentacion(new Integer(not.getAnioPres()));
  	  						pk.setMesPresentacion(new Integer(not.getMesPres()));
  	  						pk.setAnoEjecucionGasto(new Integer(not.getAnioEjec()));
  	  						pk.setMesEjecucionGasto(new Integer(not.getMesEjec()));
  	  						pk.setEtapa(not.getEtapa());  
  	  						FiseFormato12BC formato12B =formatoService12B.getFormatoCabeceraById(pk);
  	  						
  	  						if(formato12B.getListaDetalle12BDs()!=null){
  	  						   cargarListaObservaciones12B(formato12B.getListaDetalle12BDs());
  	  						}	  						
  	  						
  	  						nombreReporte ="validacion";
  	  						directorio =  "/reports/"+nombreReporte+".jasper";
  	  						tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12B);
  	  						if( tabla!=null ){
  	  							descripcionFormato = tabla.getDescripcionTabla();
  	  						}
  	  					    mapa.put(FiseConstants.PARAM_ANO_PRESENTACION, Long.parseLong(not.getAnioPres()));
  	  						mapa.put(FiseConstants.PARAM_DESC_MES_PRESENTACION, 
  	  								fiseUtil.getMapaMeses().get(Long.parseLong(not.getMesPres())));
  	  						mapa.put(FiseConstants.PARAM_USUARIO, themeDisplay.getUser().getLogin());
  	  						mapa.put(FiseConstants.PARAM_NOMBRE_FORMATO, descripcionFormato);
  	  						mapa.put(FiseConstants.PARAM_NRO_OBSERVACIONES, (listaObservaciones!=null && 
  	  								!listaObservaciones.isEmpty())?listaObservaciones.size():0);
  	  						mapa.put(FiseConstants.PARAM_DESC_EMPRESA, mapaEmpresa.get(not.getCodEmpresa()));
  	  						mapa.put(FiseConstants.PARAM_ETAPA, not.getEtapa()); 

  	  						File reportFile12B = new File(session.getServletContext().getRealPath(directorio));
  	  						byte[] bytes12B = null;
  	  					    if(listaObservaciones!=null && !listaObservaciones.isEmpty() && listaObservaciones.size()>0){
  	  					        bytes12B = JasperRunManager.runReportToPdf(reportFile12B.getPath(), mapa,
  	  								new JRBeanCollectionDataSource(listaObservaciones));
	  	 		    	    }else{
	  	 		    	    	bytes12B = JasperRunManager.runReportToPdf(reportFile12B.getPath(), mapa,
	  	 		    	    			new JREmptyDataSource()); 	  	 		    	   
	  	 		    	    }
  	  					    
  	  						if (bytes12B != null) {
  	  							String nombre = FormatoUtil.nombreReporteGeneralAnexoObs(not.getCodEmpresa(),
  	  									Long.valueOf(not.getAnioPres()), Long.valueOf(not.getMesPres()), 
  	  									FiseConstants.NOMBRE_CONSOLIDADO_EMAIL,FiseConstants.TIPO_FORMATO_12B);	   			    	 
  	  							FileEntry archivo12B = fiseUtil.subirDocumentoBytes(request, bytes12B, "application/pdf", nombre);	   			    	   
  	  							if( archivo12B!=null ){
  	  								FileEntryJSP fileEntryJsp = new FileEntryJSP();
  	  								fileEntryJsp.setNombreArchivo(nombre);
  	  								fileEntryJsp.setFileEntry(archivo12B);
  	  								listaArchivo.add(fileEntryJsp);
  	  							}
  	  						}			
  	  						
  	  					}else if(FiseConstants.NOMBRE_FORMATO_12C.equals(not.getFormato())){  						
  	  						FiseFormato12CCPK pk = new FiseFormato12CCPK();
  	  						pk.setCodEmpresa(not.getCodEmpresa());
  	  						pk.setAnoPresentacion(new Long(not.getAnioPres()));
  	  						pk.setMesPresentacion(new Long(not.getMesPres()));
  	  						pk.setEtapa(not.getEtapa()); 
  	  						FiseFormato12CC formato12C = formatoService12C.obtenerFormato12CCByPK(pk);
  	  						
  	  						if(formato12C.getFiseFormato12CDs()!=null){
  	  						   cargarListaObservaciones12C(formato12C.getFiseFormato12CDs());  	  		
  	  						}  	  											
  	  						
  	  						nombreReporte = "validacion12";		  	  		    		
  	  						directorio = "/reports/" + nombreReporte + ".jasper";
  	  						tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12C);
  	  						if( tabla!=null ){
  	  							descripcionFormato = tabla.getDescripcionTabla();
  	  						}
  	  					    mapa.put(FiseConstants.PARAM_ANO_PRESENTACION, Long.parseLong(not.getAnioPres()));
  	  						mapa.put(FiseConstants.PARAM_DESC_MES_PRESENTACION, 
  	  								fiseUtil.getMapaMeses().get(Long.parseLong(not.getMesPres())));
  	  						mapa.put(FiseConstants.PARAM_USUARIO, themeDisplay.getUser().getLogin());
  	  						mapa.put(FiseConstants.PARAM_NOMBRE_FORMATO, descripcionFormato);
  	  						mapa.put(FiseConstants.PARAM_NRO_OBSERVACIONES, (listaObservaciones!=null && 
  	  								!listaObservaciones.isEmpty())?listaObservaciones.size():0);
  	  						mapa.put(FiseConstants.PARAM_DESC_EMPRESA, mapaEmpresa.get(not.getCodEmpresa()));
  	  						mapa.put(FiseConstants.PARAM_ETAPA, not.getEtapa()); 
  	  						
  	  						File reportFile12C = new File(session.getServletContext().getRealPath(directorio));
  	  						byte[] bytes12C = null;
  	  						if(listaObservaciones!=null && !listaObservaciones.isEmpty() && listaObservaciones.size()>0){
  	  							bytes12C = JasperRunManager.runReportToPdf(reportFile12C.getPath(), mapa, 
  	  									new JRBeanCollectionDataSource(listaObservaciones));	
  	  						}else{
  	  							bytes12C = JasperRunManager.runReportToPdf(reportFile12C.getPath(), mapa, 
  	  								new JREmptyDataSource()); 	  	 		    	   
  	  						}  	  						
  	  						if (bytes12C != null) {	  	  		    			
  	  							String nombre = FormatoUtil.nombreReporteGeneralAnexoObs(not.getCodEmpresa(),
  	  									Long.valueOf(not.getAnioPres()),Long.valueOf(not.getMesPres()),
  	  									FiseConstants.NOMBRE_CONSOLIDADO_EMAIL,FiseConstants.TIPO_FORMATO_12C); 
  	  							FileEntry archivo12C = fiseUtil.subirDocumentoBytes(request, bytes12C, "application/pdf", nombre);
  	  							if (archivo12C != null) {
  	  								FileEntryJSP fileEntryJsp = new FileEntryJSP();
  	  								fileEntryJsp.setNombreArchivo(nombre);
  	  								fileEntryJsp.setFileEntry(archivo12C);
  	  								listaArchivo.add(fileEntryJsp);
  	  							}
  	  						} 	  						
  	  						
  	  					}else if(FiseConstants.NOMBRE_FORMATO_12D.equals(not.getFormato())){   					
  	  						FiseFormato12DCPK pk = new FiseFormato12DCPK();
  	  						pk.setCodEmpresa(not.getCodEmpresa());
  	  						pk.setAnoPresentacion(new Long(not.getAnioPres()));
  	  						pk.setMesPresentacion(new Long(not.getMesPres()));
  	  						pk.setEtapa(not.getEtapa()); 
  	  						FiseFormato12DC formato12D = formatoService12D.obtenerFormato12DCByPK(pk); 
  	  						
  	  						if(formato12D.getFiseFormato12DDs()!=null){
  	  						   cargarListaObservaciones12D(formato12D.getFiseFormato12DDs());
  	  						} 	  						
  	  						
  	  						nombreReporte = "validacion12";		  	  		    		
  	  						directorio = "/reports/" + nombreReporte + ".jasper";
  	  						tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12D);
  	  						if( tabla!=null ){
  	  							descripcionFormato = tabla.getDescripcionTabla();
  	  						} 
  	  				    	mapa.put(FiseConstants.PARAM_ANO_PRESENTACION, Long.parseLong(not.getAnioPres()));
  	  						mapa.put(FiseConstants.PARAM_DESC_MES_PRESENTACION, 
  	  								fiseUtil.getMapaMeses().get(Long.parseLong(not.getMesPres())));
  	  						mapa.put(FiseConstants.PARAM_USUARIO, themeDisplay.getUser().getLogin());
  	  						mapa.put(FiseConstants.PARAM_NOMBRE_FORMATO, descripcionFormato);
  	  						mapa.put(FiseConstants.PARAM_NRO_OBSERVACIONES, (listaObservaciones!=null && 
  	  								!listaObservaciones.isEmpty())?listaObservaciones.size():0);
  	  						mapa.put(FiseConstants.PARAM_DESC_EMPRESA, mapaEmpresa.get(not.getCodEmpresa()));
  	  						mapa.put(FiseConstants.PARAM_ETAPA, not.getEtapa()); 

  	  						File reportFile12D = new File(session.getServletContext().getRealPath(directorio));
  	  						byte[] bytes12D = null;
  	  						if(listaObservaciones!=null && !listaObservaciones.isEmpty() && listaObservaciones.size()>0){
  	  							bytes12D = JasperRunManager.runReportToPdf(reportFile12D.getPath(), mapa, 
  	  									new JRBeanCollectionDataSource(listaObservaciones));	
  	  						}else{
  	  							bytes12D = JasperRunManager.runReportToPdf(reportFile12D.getPath(), mapa, 
  	  								new JREmptyDataSource()); 	  	 		    	   
  	  						}  	  						
  	  						if (bytes12D != null) {	  	  		    			
  	  							String nombre = FormatoUtil.nombreReporteGeneralAnexoObs(not.getCodEmpresa(),
  	  									Long.valueOf(not.getAnioPres()),Long.valueOf(not.getMesPres()),
  	  									FiseConstants.NOMBRE_CONSOLIDADO_EMAIL,FiseConstants.TIPO_FORMATO_12D); 
  	  							FileEntry archivo12D = fiseUtil.subirDocumentoBytes(request, bytes12D, "application/pdf", nombre);
  	  							if (archivo12D != null) {
  	  								FileEntryJSP fileEntryJsp = new FileEntryJSP();
  	  								fileEntryJsp.setNombreArchivo(nombre);
  	  								fileEntryJsp.setFileEntry(archivo12D);
  	  								listaArchivo.add(fileEntryJsp);
  	  							}
  	  						} 	 	  						
  	  						
  	  					}else if(FiseConstants.NOMBRE_FORMATO_13A.equals(not.getFormato())){  						
  	  						FiseFormato13ACPK pk = new FiseFormato13ACPK();
  	  						pk.setCodEmpresa(not.getCodEmpresa());
  	  						pk.setAnoPresentacion(new Long(not.getAnioPres()));
  	  						pk.setMesPresentacion(new Long(not.getMesPres()));
  	  						pk.setEtapa(not.getEtapa());  						
  	  						FiseFormato13AC formato13A = formatoService13A.obtenerFormato13ACByPK(pk); 
  	  						
  	  						if(formato13A.getFiseFormato13ADs()!=null){
  	  						    cargarListaObservaciones13A(formato13A.getFiseFormato13ADs());
  	  						}
  	  						
  	  						nombreReporte ="validacion13";
  	  						directorio =  "/reports/"+nombreReporte+".jasper";
  	  						tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO13A);
  	  						if( tabla!=null ){
  	  				    		descripcionFormato = tabla.getDescripcionTabla();
  	  				    	}
  	  					    mapa.put(FiseConstants.PARAM_ANO_PRESENTACION, Long.parseLong(not.getAnioPres()));
  	  						mapa.put(FiseConstants.PARAM_DESC_MES_PRESENTACION, 
  	  								fiseUtil.getMapaMeses().get(Long.parseLong(not.getMesPres())));
  	  						mapa.put(FiseConstants.PARAM_USUARIO, themeDisplay.getUser().getLogin());
  	  						mapa.put(FiseConstants.PARAM_NOMBRE_FORMATO, descripcionFormato);
  	  						mapa.put(FiseConstants.PARAM_NRO_OBSERVACIONES, (listaObservaciones!=null && 
  	  								!listaObservaciones.isEmpty())?listaObservaciones.size():0);
  	  						mapa.put(FiseConstants.PARAM_DESC_EMPRESA, mapaEmpresa.get(not.getCodEmpresa()));
  	  						mapa.put(FiseConstants.PARAM_ETAPA, not.getEtapa()); 
  	  						/*mapa.put(FiseConstants.PARAM_ANO_INICIO_VIGENCIA, not.getAnioIniVig()!=null?not.getAnioIniVig():"");
  	  						mapa.put(FiseConstants.PARAM_ANO_FIN_VIGENCIA, not.getAnioFinVig()!=null?not.getAnioFinVig():"");*/
  	  						mapa.put(FiseConstants.PARAM_ANO_INICIO_VIGENCIA, (not.getAnioIniVig()!=null && !not.getAnioIniVig().equals("---"))?Long.parseLong(not.getAnioIniVig()):0);
	  						mapa.put(FiseConstants.PARAM_ANO_FIN_VIGENCIA, (not.getAnioFinVig()!=null && !not.getAnioFinVig().equals("---"))?Long.parseLong(not.getAnioFinVig()):0);

  							File reportFile13A = new File(session.getServletContext().getRealPath(directorio));
  	  	 		    	    byte[] bytes13A = null;
  	  	 		    	    if(listaObservaciones!=null && !listaObservaciones.isEmpty() && listaObservaciones.size()>0){
  	  	 		    	    	bytes13A = JasperRunManager.runReportToPdf(reportFile13A.getPath(), mapa,
  	  	 		    	    			new JRBeanCollectionDataSource(listaObservaciones));	
  	  	 		    	    }else{
  	  	 		    	    	bytes13A = JasperRunManager.runReportToPdf(reportFile13A.getPath(), mapa,
  	  	 		    	    		new JREmptyDataSource()); 	  	 		    	   
  	  	 		    	    }  	  	 		    	    
  		  	 		    	if (bytes13A != null) {
  		   			    	   String nombre = FormatoUtil.nombreReporteGeneralAnexoObs(not.getCodEmpresa(),
  		   			    			   Long.valueOf(not.getAnioPres()), Long.valueOf(not.getMesPres()),
  		   			    			   FiseConstants.NOMBRE_CONSOLIDADO_EMAIL,FiseConstants.TIPO_FORMATO_13A);	   			    	 
  		   			    	   FileEntry archivo13A = fiseUtil.subirDocumentoBytes(request, bytes13A, "application/pdf", nombre);	   			    	   
  		   			    	   if( archivo13A!=null ){
  		   			    		   FileEntryJSP fileEntryJsp = new FileEntryJSP();
  		   			    		   logger.info("Nombre del reporte de validacion 13A:  "+nombre); 
  		   			    		   fileEntryJsp.setNombreArchivo(nombre);
  		   			    		   fileEntryJsp.setFileEntry(archivo13A);
  		   			    		   listaArchivo.add(fileEntryJsp);
  		   			    	   }
  		   			       }
  		  	 		    	
  	  					}else if(FiseConstants.NOMBRE_FORMATO_14A.equals(not.getFormato())){   					
  	  						FiseFormato14ACPK pk = new FiseFormato14ACPK();
  	  						pk.setCodEmpresa(not.getCodEmpresa());
  	  						pk.setAnoPresentacion(new Long(not.getAnioPres()));
  	  						pk.setMesPresentacion(new Long(not.getMesPres()));
  	  						pk.setAnoInicioVigencia(new Long(not.getAnioIniVig()));
  	  						pk.setAnoFinVigencia(new Long(not.getAnioFinVig()));
  	  						pk.setEtapa(not.getEtapa());  				        
  	  						FiseFormato14AC formato14A = formatoService14A.obtenerFormato14ACByPK(pk);
  	  						
  	  						if(formato14A.getFiseFormato14ADs()!=null){
  	  						  cargarListaObservaciones14A(formato14A.getFiseFormato14ADs());
  	  						}  	  						
  	  						
  	  						nombreReporte ="validacion";
  	  						directorio =  "/reports/"+nombreReporte+".jasper";
  	  						tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14A);
  	  			    		if( tabla!=null ){
  	  				    		descripcionFormato = tabla.getDescripcionTabla();
  	  				    	}
  	  			    	    mapa.put(FiseConstants.PARAM_ANO_PRESENTACION, Long.parseLong(not.getAnioPres()));
  	  			    		mapa.put(FiseConstants.PARAM_DESC_MES_PRESENTACION, 
  	  			    				fiseUtil.getMapaMeses().get(Long.parseLong(not.getMesPres())));
  	  			    		mapa.put(FiseConstants.PARAM_USUARIO, themeDisplay.getUser().getLogin());
  	  			    		mapa.put(FiseConstants.PARAM_NOMBRE_FORMATO, descripcionFormato);
  	  			    		mapa.put(FiseConstants.PARAM_NRO_OBSERVACIONES, (listaObservaciones!=null && 
  	  			    				!listaObservaciones.isEmpty())?listaObservaciones.size():0);
  	  			    		mapa.put(FiseConstants.PARAM_DESC_EMPRESA, mapaEmpresa.get(not.getCodEmpresa()));
  	  			    		mapa.put(FiseConstants.PARAM_ETAPA, not.getEtapa()); 	
  	  			    		
  	  					    File reportFile14A = new File(session.getServletContext().getRealPath(directorio));
  	  	 		    	    byte[] bytes14A = null;
  	  	 		    	    if(listaObservaciones!=null && !listaObservaciones.isEmpty() && listaObservaciones.size()>0){
  	  	 		    	    	bytes14A = JasperRunManager.runReportToPdf(reportFile14A.getPath(), mapa,
  	  	 		    	    			new JRBeanCollectionDataSource(listaObservaciones));
  	  	 		    	    }else{
  	  	 		    	    	bytes14A = JasperRunManager.runReportToPdf(reportFile14A.getPath(), mapa,
  	  	 		    	    		new JREmptyDataSource());	  	 		    	   
  	  	 		    	    }  	  	 		    	    
  		  	 		    	if (bytes14A != null) {
  		   			    	   String nombre = FormatoUtil.nombreReporteGeneralAnexoObs(not.getCodEmpresa(),
  		   			    			   Long.valueOf(not.getAnioPres()), Long.valueOf(not.getMesPres()),
  		   			    			   FiseConstants.NOMBRE_CONSOLIDADO_EMAIL,FiseConstants.TIPO_FORMATO_14A);	   			    	 
  		   			    	   FileEntry archivo14A = fiseUtil.subirDocumentoBytes(request, bytes14A, "application/pdf", nombre);	   			    	   
  		   			    	   if( archivo14A!=null ){
  		   			    		   FileEntryJSP fileEntryJsp = new FileEntryJSP();
  		   			    		 logger.info("Nombre del reporte de validacion 14A:  "+nombre); 
  		   			    		   fileEntryJsp.setNombreArchivo(nombre);
  		   			    		   fileEntryJsp.setFileEntry(archivo14A);
  		   			    		   listaArchivo.add(fileEntryJsp);
  		   			    	   }
  		   			       }  
  		  	 		    	
  	  					}else if(FiseConstants.NOMBRE_FORMATO_14B.equals(not.getFormato())){  					
  	  						FiseFormato14BCPK pk = new FiseFormato14BCPK();
  	  						pk.setCodEmpresa(not.getCodEmpresa());
  	  						pk.setAnoPresentacion(new Long(not.getAnioPres()));
  	  						pk.setMesPresentacion(new Long(not.getMesPres()));
  	  						pk.setAnoInicioVigencia(new Long(not.getAnioIniVig()));
  	  						pk.setAnoFinVigencia(new Long(not.getAnioFinVig()));
  	  						pk.setEtapa(not.getEtapa());  						        
  	  						FiseFormato14BC formato14B = formatoService14B.obtenerFormato14BCByPK(pk);
  	  						if(formato14B.getFiseFormato14BDs()!=null){
  	  						   cargarListaObservaciones14B(formato14B.getFiseFormato14BDs());
  	  						}  	  						
  	  						
  	  						nombreReporte ="validacion";
  	  						directorio =  "/reports/"+nombreReporte+".jasper";
  	  						tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14B);
  	  			    		if( tabla!=null ){
  	  				    		descripcionFormato = tabla.getDescripcionTabla();
  	  				    	}
  	  			    	    mapa.put(FiseConstants.PARAM_ANO_PRESENTACION, Long.parseLong(not.getAnioPres()));
  	  			    		mapa.put(FiseConstants.PARAM_DESC_MES_PRESENTACION, 
  	  			    				fiseUtil.getMapaMeses().get(Long.parseLong(not.getMesPres())));
  	  			    		mapa.put(FiseConstants.PARAM_USUARIO, themeDisplay.getUser().getLogin());
  	  			    		mapa.put(FiseConstants.PARAM_NOMBRE_FORMATO, descripcionFormato);
  	  			    		mapa.put(FiseConstants.PARAM_NRO_OBSERVACIONES, (listaObservaciones!=null && 
  	  			    				!listaObservaciones.isEmpty())?listaObservaciones.size():0);
  	  			    		mapa.put(FiseConstants.PARAM_DESC_EMPRESA, mapaEmpresa.get(not.getCodEmpresa()));
  	  			    		mapa.put(FiseConstants.PARAM_ETAPA, not.getEtapa()); 	
  	  			    		
  	  					    File reportFile14B = new File(session.getServletContext().getRealPath(directorio));
  	  	 		    	    byte[] bytes14B = null;
  	  	 		    	    if(listaObservaciones!=null && !listaObservaciones.isEmpty() && listaObservaciones.size()>0){
  	  	 		    	      bytes14B = JasperRunManager.runReportToPdf(reportFile14B.getPath(), mapa,
  	  	 			    		   new JRBeanCollectionDataSource(listaObservaciones));	
  	  	 		    	    }else{
  	  	 		    	       bytes14B = JasperRunManager.runReportToPdf(reportFile14B.getPath(), mapa,
  	  	 		    	    		new JREmptyDataSource()); 	  	 		    	   
  	  	 		    	    }
  	  	 		    	   
  		  	 		    	if (bytes14B != null) {
  		   			    	   String nombre = FormatoUtil.nombreReporteGeneralAnexoObs(not.getCodEmpresa(),
  		   			    			   Long.valueOf(not.getAnioPres()), Long.valueOf(not.getMesPres()),
  		   			    			   FiseConstants.NOMBRE_CONSOLIDADO_EMAIL,FiseConstants.TIPO_FORMATO_14B);	   			    	 
  		   			    	   FileEntry archivo14B = fiseUtil.subirDocumentoBytes(request, bytes14B, "application/pdf", nombre);	   			    	   
  		   			    	   if( archivo14B!=null ){
  		   			    		   FileEntryJSP fileEntryJsp = new FileEntryJSP();
  		   			    		 logger.info("Nombre del reporte de validacion 14B:  "+nombre); 
  		   			    		   fileEntryJsp.setNombreArchivo(nombre);
  		   			    		   fileEntryJsp.setFileEntry(archivo14B);
  		   			    		   listaArchivo.add(fileEntryJsp);
  		   			    	   }
  		   			       } 
  		  	 		    	
  	  					}else if(FiseConstants.NOMBRE_FORMATO_14C.equals(not.getFormato())){  					
  	  						Formato14CBean f14C = new Formato14CBean();				
  	  						f14C.setCodEmpresa(not.getCodEmpresa());
  	  						f14C.setAnioPres(not.getAnioPres());
  	  						f14C.setMesPres(not.getMesPres());
  	  						f14C.setAnoIniVigencia(not.getAnioIniVig());
  	  						f14C.setAnoFinVigencia(not.getAnioFinVig());
  	  						f14C.setEtapa(not.getEtapa());
  	  						FiseFormato14CC formato14C = formatoService14C.obtenerFiseFormato14CC(f14C);
  	  						
  	  						if(formato14C.getListaDetalle14cDs()!=null){
  	  						   cargarListaObservaciones14C(formato14C.getListaDetalle14cDs());
  	  						} 	  						
  	  						
  	  						nombreReporte ="validacion";
  	  						directorio =  "/reports/"+nombreReporte+".jasper";
  	  						tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14C);
  	  			    		if( tabla!=null ){
  	  				    		descripcionFormato = tabla.getDescripcionTabla();
  	  				    	}
  	  			    	    mapa.put(FiseConstants.PARAM_ANO_PRESENTACION, Long.parseLong(not.getAnioPres()));
  	  			    		mapa.put(FiseConstants.PARAM_DESC_MES_PRESENTACION, 
  	  			    				fiseUtil.getMapaMeses().get(Long.parseLong(not.getMesPres())));
  	  			    		mapa.put(FiseConstants.PARAM_USUARIO, themeDisplay.getUser().getLogin());
  	  			    		mapa.put(FiseConstants.PARAM_NOMBRE_FORMATO, descripcionFormato);
  	  			    		mapa.put(FiseConstants.PARAM_NRO_OBSERVACIONES, (listaObservaciones!=null && 
  	  			    				!listaObservaciones.isEmpty())?listaObservaciones.size():0);
  	  			    		mapa.put(FiseConstants.PARAM_DESC_EMPRESA, mapaEmpresa.get(not.getCodEmpresa()));
  	  			    		mapa.put(FiseConstants.PARAM_ETAPA, not.getEtapa()); 
	  						
  	  					    File reportFile14C = new File(session.getServletContext().getRealPath(directorio));
  	  	 		    	    byte[] bytes14C = null;
  	  	 		    	    if(listaObservaciones!=null && !listaObservaciones.isEmpty() && listaObservaciones.size()>0){
  	  	 		    	      bytes14C = JasperRunManager.runReportToPdf(reportFile14C.getPath(), mapa,
  	  	 			    		   new JRBeanCollectionDataSource(listaObservaciones));	
  	  	 		    	    }else{
  	  	 		    	        bytes14C = JasperRunManager.runReportToPdf(reportFile14C.getPath(), mapa,
  	  	 		    	        	new JREmptyDataSource());  	  	 		    	    		 		    	   
  	  	 		    	    }  	  	 		    	    
  		  	 		    	if (bytes14C != null) {
  		   			    	   String nombre = FormatoUtil.nombreReporteGeneralAnexoObs(not.getCodEmpresa(),
  		   			    			   Long.valueOf(not.getAnioPres()), Long.valueOf(not.getMesPres()),
  		   			    			   FiseConstants.NOMBRE_CONSOLIDADO_EMAIL,FiseConstants.TIPO_FORMATO_14C);	   			    	 
  		   			    	   FileEntry archivo14C = fiseUtil.subirDocumentoBytes(request, bytes14C, "application/pdf", nombre);	   			    	   
  		   			    	   if( archivo14C!=null ){
  		   			    		   FileEntryJSP fileEntryJsp = new FileEntryJSP();
  		   			    		   fileEntryJsp.setNombreArchivo(nombre);
  		   			    		   logger.info("Nombre del reporte de validacion 14C:  "+nombre); 
  		   			    		   fileEntryJsp.setFileEntry(archivo14C);
  		   			    		   listaArchivo.add(fileEntryJsp);
  		   			    	   }
  		   			       }				    	
  	  					}	
  	  				}//fin del for de la lista 
  	  			    if(listaArchivo!=null && listaArchivo.size()>0 ){		    	  
  		    	      logger.info("Entrando a enviar email envio notificacion a:  "+
  	  			           themeDisplay.getUser().getEmailAddress());  
  		    	       String codEmpreCompleta = FormatoUtil.rellenaDerecha(codEmpresaLista, ' ', 4);
  		    	       respuestaEmail=fiseUtil.enviarMailsAdjuntoValidacion(
  		    	    		  request, 
  		    	    		  listaArchivo, 
  		    	    		  mapaEmpresa.get(codEmpreCompleta), 
  		    	    		  n.getDescGrupoInf()!= null ? n.getDescGrupoInf():"--",codEmpreCompleta,plazo);
  		    	       logger.info("El envio de email fue correctamente al realizar notificacion."+respuestaEmail); 		    	       
  		    	       valor = true;
  		           }  	  		     	 	   
  				}//fin del if mensaje 
  				else{
  					jsonObj.put("resultado", "Mensaje");  					
  				}   			
  			}//fin del if lista diferente de null
  			else{
  				jsonObj.put("resultado", "NO_DATOS");			
  			}
  			if(valor){ 
  				String[] msnId = respuestaEmail.split("/");
  				if(FiseConstants.PROCESO_ENVIO_EMAIL_OK.equals(msnId[0])){
  					jsonObj.put("resultado", "OK");	
  	  				jsonObj.put("correo",msnId[1]);		
  				}else{
  					jsonObj.put("resultado", "EMAIL");//error al enviar al email	
  	  				jsonObj.put("correo",msnId[1]);		
  				}	
  			}
  			logger.info("arreglo json:"+jsonObj);
  			PrintWriter pw = response.getWriter();
  			pw.write(jsonObj.toString());
  			pw.flush();
  			pw.close();
  			pRequest.getPortletSession().setAttribute("listaNotificacion", null, PortletSession.APPLICATION_SCOPE);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	
	@ResourceMapping("eliminarNotificacion")
  	public void eliminarNotificacion(ResourceRequest request,ResourceResponse response,
  			 @ModelAttribute("notificacionBean")NotificacionBean n){
		
		try{
			response.setContentType("application/json");		
			JSONObject jsonObj = new JSONObject(); 
			boolean valor = false;
			logger.info("Entrando a eliminar notificacion"); 				
			logger.info("Codigo empresa:  "+ n.getCodEmpresa()); 
			logger.info("anio pres:  "+ n.getAnioPres());	
			logger.info("mes pres:  "+ n.getMesPres());
			logger.info("formato:  "+ n.getFormato());	
			logger.info("etapa:  "+ n.getEtapa());	
			logger.info("anio ejec:  "+ n.getAnioEjec());
			logger.info("mes ejec:  "+ n.getMesEjec());
			logger.info("anio ini vige:  "+ n.getAnioIniVig());
			logger.info("anio fin vige:  "+ n.getAnioFinVig());
			if(FiseConstants.NOMBRE_FORMATO_12A.equals(n.getFormato())){			
				FiseFormato12ACPK pk = new FiseFormato12ACPK();
				pk.setCodEmpresa(n.getCodEmpresa());
				pk.setAnoPresentacion(new Long(n.getAnioPres()));
				pk.setMesPresentacion(new Long(n.getMesPres()));
				pk.setAnoEjecucionGasto(new Long(n.getAnioEjec()));
				pk.setMesEjecucionGasto(new Long(n.getMesEjec()));
				pk.setEtapa(n.getEtapa());  	  			        
				FiseFormato12AC formato12A = formatoService12A.obtenerFormato12ACByPK(pk);	
				
				for (FiseFormato12AD detalle : formato12A.getFiseFormato12ADs()) {
					//detalle.setFiseFormato12ADObs(formatoService12A.listarFormato12ADObByFormato12AD(detalle));
					List<FiseFormato12ADOb> listaObser = formatoService12A.listarFormato12ADObByFormato12AD(detalle);
					formatoService12A.eliminarObservaciones12A(listaObser);
					valor = true;
				}				
			}else if(FiseConstants.NOMBRE_FORMATO_12B.equals(n.getFormato())){ 				
				FiseFormato12BCPK pk=new FiseFormato12BCPK();
				pk.setCodEmpresa(n.getCodEmpresa());
				pk.setAnoPresentacion(new Integer(n.getAnioPres()));
				pk.setMesPresentacion(new Integer(n.getMesPres()));
				pk.setAnoEjecucionGasto(new Integer(n.getAnioEjec()));
				pk.setMesEjecucionGasto(new Integer(n.getMesEjec()));
				pk.setEtapa(n.getEtapa());  
				FiseFormato12BC formato12B =formatoService12B.getFormatoCabeceraById(pk);   
				
				for (FiseFormato12BD detalle : formato12B.getFiseFormato12BDs()) {
					//detalle.setFiseFormato12BDObs(formatoService12B.getLstFormatoObs(detalle)); 
					List<FiseFormato12BDOb> listaObser = formatoService12B.getLstFormatoObs(detalle);
					formatoService12B.eliminarObservaciones12B(listaObser);
					logger.info("Formato 12B eliminado"); 
					valor = true;
				}			
			}else if(FiseConstants.NOMBRE_FORMATO_12C.equals(n.getFormato())){ 				
				FiseFormato12CCPK pk = new FiseFormato12CCPK();
				pk.setCodEmpresa(n.getCodEmpresa());
				pk.setAnoPresentacion(new Long(n.getAnioPres()));
				pk.setMesPresentacion(new Long(n.getMesPres()));
				pk.setEtapa(n.getEtapa()); 
				FiseFormato12CC formato12C = formatoService12C.obtenerFormato12CCByPK(pk);	
				
				for (FiseFormato12CD detalle : formato12C.getFiseFormato12CDs()) {
					//detalle.setFiseFormato12ADObs(formatoService12A.listarFormato12ADObByFormato12AD(detalle));
					List<FiseFormato12CDOb> listaObser = formatoService12C.listarFormato12CDObByFormato12CD(detalle);
					formatoService12C.eliminarObservaciones12C(listaObser);
					valor = true;
				}			
			}else if(FiseConstants.NOMBRE_FORMATO_12D.equals(n.getFormato())){ 			
				FiseFormato12DCPK pk = new FiseFormato12DCPK();
				pk.setCodEmpresa(n.getCodEmpresa());
				pk.setAnoPresentacion(new Long(n.getAnioPres()));
				pk.setMesPresentacion(new Long(n.getMesPres()));
				pk.setEtapa(n.getEtapa()); 
				FiseFormato12DC formato12D = formatoService12D.obtenerFormato12DCByPK(pk);
				
				for (FiseFormato12DD detalle : formato12D.getFiseFormato12DDs()) {
					//detalle.setFiseFormato12ADObs(formatoService12A.listarFormato12ADObByFormato12AD(detalle));
					List<FiseFormato12DDOb> listaObser = formatoService12D.listarFormato12DDObByFormato12DD(detalle);
					formatoService12D.eliminarObservaciones12D(listaObser); 
					valor = true;
				}			
			}else if(FiseConstants.NOMBRE_FORMATO_13A.equals(n.getFormato())){				
				FiseFormato13ACPK pk = new FiseFormato13ACPK();
				pk.setCodEmpresa(n.getCodEmpresa());
				pk.setAnoPresentacion(new Long(n.getAnioPres()));
				pk.setMesPresentacion(new Long(n.getMesPres()));
				pk.setEtapa(n.getEtapa());				
				FiseFormato13AC formato13A = formatoService13A.obtenerFormato13ACByPK(pk);
				
				for (FiseFormato13AD detalle : formato13A.getFiseFormato13ADs()) {
					List<FiseFormato13ADOb> listaObser = formatoService13A.listarFormato13ADObByFormato13AD(detalle);
					formatoService13A.eliminarObservaciones13A(listaObser);
					valor = true;
				}			
			}else if(FiseConstants.NOMBRE_FORMATO_14A.equals(n.getFormato())){ 				
				FiseFormato14ACPK pk = new FiseFormato14ACPK();
				pk.setCodEmpresa(n.getCodEmpresa());
				pk.setAnoPresentacion(new Long(n.getAnioPres()));
				pk.setMesPresentacion(new Long(n.getMesPres()));
				pk.setAnoInicioVigencia(new Long(n.getAnioIniVig()));
				pk.setAnoFinVigencia(new Long(n.getAnioFinVig()));
				pk.setEtapa(n.getEtapa());  				        
				FiseFormato14AC formato14A = formatoService14A.obtenerFormato14ACByPK(pk);
				
				for (FiseFormato14AD detalle : formato14A.getFiseFormato14ADs()) {
					//detalle.setFiseFormato14ADObs(formatoService14A.listarFormato14ADObByFormato14AD(detalle));
					List<FiseFormato14ADOb> listaObser = formatoService14A.listarFormato14ADObByFormato14AD(detalle);
					formatoService14A.eliminarObservaciones14A(listaObser); 
					valor = true;
				}			
			}else if(FiseConstants.NOMBRE_FORMATO_14B.equals(n.getFormato())){				
				FiseFormato14BCPK pk = new FiseFormato14BCPK();
				pk.setCodEmpresa(n.getCodEmpresa());
				pk.setAnoPresentacion(new Long(n.getAnioPres()));
				pk.setMesPresentacion(new Long(n.getMesPres()));
				pk.setAnoInicioVigencia(new Long(n.getAnioIniVig()));
				pk.setAnoFinVigencia(new Long(n.getAnioFinVig()));
				pk.setEtapa(n.getEtapa());  						        
				FiseFormato14BC formato14B = formatoService14B.obtenerFormato14BCByPK(pk);	
				
				for (FiseFormato14BD detalle : formato14B.getFiseFormato14BDs()) {
					//detalle.setFiseFormato14BDObs(formatoService14B.listarFormato14BDObByFormato14BD(detalle));
					List<FiseFormato14BDOb> listaObser = formatoService14B.listarFormato14BDObByFormato14BD(detalle);
					formatoService14B.eliminarObservaciones14B(listaObser);
					valor = true;
				}			
			}else if(FiseConstants.NOMBRE_FORMATO_14C.equals(n.getFormato())){ 			
				Formato14CBean f14C = new Formato14CBean();				
				f14C.setCodEmpresa(n.getCodEmpresa());
				f14C.setAnioPres(n.getAnioPres());
				f14C.setMesPres(n.getMesPres());
				f14C.setAnoIniVigencia(n.getAnioIniVig());
				f14C.setAnoFinVigencia(n.getAnioFinVig());
				f14C.setEtapa(n.getEtapa());
				FiseFormato14CC formato14C = formatoService14C.obtenerFiseFormato14CC(f14C);	
				
				for (FiseFormato14CD detalle : formato14C.getListaDetalle14cDs()) {			
					List<FiseFormato14CDOb> listaObser = formatoService14C.listaObservacionesF14C(detalle);					
					formatoService14C.eliminarObservaciones14C(listaObser); 
					valor = true;
				}			
			}
			if(valor){
				jsonObj.put("resultado", "OK");			
			}else{
				jsonObj.put("resultado", "ERROR");		
			}				
  			PrintWriter pw = response.getWriter();
  			pw.write(jsonObj.toString());
  			pw.flush();
  			pw.close();		
		}catch(Exception e){
			e.printStackTrace(); 
			logger.error("Error al eliminar el registro seleccionado");
		}		
	}
	
	/***Para agregar observaciones de forma manual****/	
	
	@ResourceMapping("verDetalleFormatos")
  	public void verDetalleFormato(ResourceRequest request,ResourceResponse response,
  			 @ModelAttribute("notificacionBean")NotificacionBean n){
		NotificacionBean not =null;
		try{
			response.setContentType("application/json");
			
			String data = "";			
			List<NotificacionBean> listaDetalle = new ArrayList<NotificacionBean>();			
			logger.info("Entrando a ver detalle de cada formato"); 				
			logger.info("Codigo empresa:  "+ n.getCodEmpresa()); 
			logger.info("anio pres:  "+ n.getAnioPres());	
			logger.info("mes pres:  "+ n.getMesPres());
			logger.info("formato:  "+ n.getFormato());	
			logger.info("etapa:  "+ n.getEtapa());	
			logger.info("anio ejec:  "+ n.getAnioEjec());
			logger.info("mes ejec:  "+ n.getMesEjec());
			logger.info("anio ini vige:  "+ n.getAnioIniVig());
			logger.info("anio fin vige:  "+ n.getAnioFinVig());	 
  			 							
			if(FiseConstants.NOMBRE_FORMATO_12A.equals(n.getFormato())){				
				FiseFormato12ACPK pk = new FiseFormato12ACPK();
				pk.setCodEmpresa(n.getCodEmpresa());
				pk.setAnoPresentacion(new Long(n.getAnioPres()));
				pk.setMesPresentacion(new Long(n.getMesPres()));
				pk.setAnoEjecucionGasto(new Long(n.getAnioEjec()));
				pk.setMesEjecucionGasto(new Long(n.getMesEjec()));
				pk.setEtapa(n.getEtapa());  	  			        
				FiseFormato12AC formato12A = formatoService12A.obtenerFormato12ACByPK(pk);	
				if(formato12A!=null){					
					for (FiseFormato12AD d : formato12A.getFiseFormato12ADs()) {
						not = new NotificacionBean();
						not.setCodEmpresa(d.getId().getCodEmpresa());
						not.setDesEmpresa(mapaEmpresa.get(d.getId().getCodEmpresa()));
						not.setAnioPres(""+d.getId().getAnoPresentacion());
						not.setMesPres(""+d.getId().getMesPresentacion());						
						not.setDesMes(fiseUtil.getMapaMeses().get(d.getId().getMesPresentacion())); 
						not.setEtapa(d.getId().getEtapa());
						not.setAnioEjec(""+d.getId().getAnoEjecucionGasto());
						not.setMesEjec(""+d.getId().getMesEjecucionGasto()); 						
						not.setDesMesEje(fiseUtil.getMapaMeses().get(d.getId().getMesPresentacion())); 
						not.setIdZona(""+d.getId().getIdZonaBenef()); 						
						not.setDesZona(fiseUtil.getMapaZonaBenef().get(d.getId().getIdZonaBenef()));						
						listaDetalle.add(not);
					}	    	
				}    
			}else if(FiseConstants.NOMBRE_FORMATO_12B.equals(n.getFormato())){ 				
				FiseFormato12BCPK pk=new FiseFormato12BCPK();
				pk.setCodEmpresa(n.getCodEmpresa());
				pk.setAnoPresentacion(new Integer(n.getAnioPres()));
				pk.setMesPresentacion(new Integer(n.getMesPres()));
				pk.setAnoEjecucionGasto(new Integer(n.getAnioEjec()));
				pk.setMesEjecucionGasto(new Integer(n.getMesEjec()));
				pk.setEtapa(n.getEtapa());  
				FiseFormato12BC formato12B =formatoService12B.getFormatoCabeceraById(pk);
				if(formato12B!=null){					
					for (FiseFormato12BD d : formato12B.getListaDetalle12BDs()) {
						logger.info("Tamanio lista detalle:  "+formato12B.getListaDetalle12BDs().size()); 
						not = new NotificacionBean();
						not.setCodEmpresa(d.getId().getCodEmpresa());
						not.setDesEmpresa(mapaEmpresa.get(d.getId().getCodEmpresa()));
						not.setAnioPres(""+d.getId().getAnoPresentacion());
						not.setMesPres(""+d.getId().getMesPresentacion());						
						not.setDesMes(fiseUtil.getMapaMeses().get(new Long(d.getId().getMesPresentacion())));						
						not.setEtapa(d.getId().getEtapa());
						not.setAnioEjec(""+d.getId().getAnoEjecucionGasto());						
						not.setMesEjec(""+d.getId().getMesEjecucionGasto()); 						
						not.setDesMesEje(fiseUtil.getMapaMeses().get(new Long(d.getId().getMesPresentacion())));
						not.setIdZona(""+d.getId().getIdZonaBenef()); 						
						not.setDesZona(fiseUtil.getMapaZonaBenef().get(new Long(d.getId().getIdZonaBenef())));						
						listaDetalle.add(not);	
					}		
				}			
			}else if(FiseConstants.NOMBRE_FORMATO_12C.equals(n.getFormato())){ 				
				FiseFormato12CCPK pk = new FiseFormato12CCPK();
				pk.setCodEmpresa(n.getCodEmpresa());
				pk.setAnoPresentacion(new Long(n.getAnioPres()));
				pk.setMesPresentacion(new Long(n.getMesPres()));
				pk.setEtapa(n.getEtapa()); 
				FiseFormato12CC formato12C = formatoService12C.obtenerFormato12CCByPK(pk);
				if(formato12C!=null){					
					for (FiseFormato12CD d : formato12C.getFiseFormato12CDs()) {
						not = new NotificacionBean();
						not.setCodEmpresa(d.getId().getCodEmpresa());
						not.setDesEmpresa(mapaEmpresa.get(d.getId().getCodEmpresa()));
						not.setAnioPres(""+d.getId().getAnoPresentacion());
						not.setMesPres(""+d.getId().getMesPresentacion());
						not.setDesMes(fiseUtil.getMapaMeses().get(d.getId().getMesPresentacion())); 
						not.setEtapa(d.getId().getEtapa());
						not.setAnioEjec(""+d.getId().getAnoEjecucionGasto());
						not.setMesEjec(""+d.getId().getMesEjecucionGasto()); 
						not.setDesMesEje(fiseUtil.getMapaMeses().get(d.getId().getMesPresentacion()));
						not.setEtapaEjec(""+d.getId().getEtapaEjecucion());
						not.setDesEstapaEjec(mapaEtapaEjecucion.get(d.getId().getEtapaEjecucion())); 
						not.setItemEtapa(""+d.getId().getNumeroItemEtapa());						
						listaDetalle.add(not);
					}			
				}				
			}else if(FiseConstants.NOMBRE_FORMATO_12D.equals(n.getFormato())){				
				FiseFormato12DCPK pk = new FiseFormato12DCPK();
				pk.setCodEmpresa(n.getCodEmpresa());
				pk.setAnoPresentacion(new Long(n.getAnioPres()));
				pk.setMesPresentacion(new Long(n.getMesPres()));
				pk.setEtapa(n.getEtapa()); 
				FiseFormato12DC formato12D = formatoService12D.obtenerFormato12DCByPK(pk); 
				if(formato12D!=null){					
					for (FiseFormato12DD d : formato12D.getFiseFormato12DDs()) {
						not = new NotificacionBean();
						not.setCodEmpresa(d.getId().getCodEmpresa());
						not.setDesEmpresa(mapaEmpresa.get(d.getId().getCodEmpresa()));
						not.setAnioPres(""+d.getId().getAnoPresentacion());
						not.setMesPres(""+d.getId().getMesPresentacion());
						not.setDesMes(fiseUtil.getMapaMeses().get(d.getId().getMesPresentacion()));						
						not.setEtapa(d.getId().getEtapa());
						not.setAnioEjec(""+d.getId().getAnoEjecucionGasto());
						not.setMesEjec(""+d.getId().getMesEjecucionGasto()); 
						not.setDesMesEje(fiseUtil.getMapaMeses().get(d.getId().getMesPresentacion()));
						not.setEtapaEjec(""+d.getId().getEtapaEjecucion());
						not.setDesEstapaEjec(mapaEtapaEjecucion.get(d.getId().getEtapaEjecucion())); 
						not.setItemEtapa(""+d.getId().getNumeroItemEtapa());
						listaDetalle.add(not);
					}				
				}			
			}else if(FiseConstants.NOMBRE_FORMATO_13A.equals(n.getFormato())){				
				FiseFormato13ACPK pk = new FiseFormato13ACPK();
				pk.setCodEmpresa(n.getCodEmpresa());
				pk.setAnoPresentacion(new Long(n.getAnioPres()));
				pk.setMesPresentacion(new Long(n.getMesPres()));
				pk.setEtapa(n.getEtapa());				
				FiseFormato13AC formato13A = formatoService13A.obtenerFormato13ACByPK(pk);	
				if(formato13A!=null){					
					for (FiseFormato13AD d : formato13A.getFiseFormato13ADs()) {
						not = new NotificacionBean();
						not.setCodEmpresa(d.getId().getCodEmpresa());
						not.setDesEmpresa(mapaEmpresa.get(d.getId().getCodEmpresa()));
						not.setAnioPres(""+d.getId().getAnoPresentacion());
						not.setMesPres(""+d.getId().getMesPresentacion());
						not.setDesMes(fiseUtil.getMapaMeses().get(d.getId().getMesPresentacion()));	
						not.setEtapa(d.getId().getEtapa());
						not.setIdZona(""+d.getId().getIdZonaBenef()); 
						not.setDesZona(fiseUtil.getMapaZonaBenef().get(d.getId().getIdZonaBenef()));
						not.setCodUbigeo(d.getId().getCodUbigeo());
						not.setCodSector(d.getId().getCodSectorTipico());
						not.setDesSector(mapaSectorTipico.get(d.getId().getCodSectorTipico())); 						 
						listaDetalle.add(not);
					}			
				}
			}else if(FiseConstants.NOMBRE_FORMATO_14A.equals(n.getFormato())){ 				
				FiseFormato14ACPK pk = new FiseFormato14ACPK();
				pk.setCodEmpresa(n.getCodEmpresa());
				pk.setAnoPresentacion(new Long(n.getAnioPres()));
				pk.setMesPresentacion(new Long(n.getMesPres()));
				pk.setAnoInicioVigencia(new Long(n.getAnioIniVig()));
				pk.setAnoFinVigencia(new Long(n.getAnioFinVig()));
				pk.setEtapa(n.getEtapa());  				        
				FiseFormato14AC formato14A = formatoService14A.obtenerFormato14ACByPK(pk);
				if(formato14A!=null){					
			    	for (FiseFormato14AD d : formato14A.getFiseFormato14ADs()) {
			    		not = new NotificacionBean();
			    		not.setCodEmpresa(d.getId().getCodEmpresa());
						not.setDesEmpresa(mapaEmpresa.get(d.getId().getCodEmpresa()));
						not.setAnioPres(""+d.getId().getAnoPresentacion());
						not.setMesPres(""+d.getId().getMesPresentacion());
						not.setDesMes(fiseUtil.getMapaMeses().get(d.getId().getMesPresentacion()));	
						not.setEtapa(d.getId().getEtapa());						
						not.setAnioIniVig(""+d.getId().getAnoInicioVigencia());		
						not.setAnioFinVig(""+d.getId().getAnoFinVigencia());
						not.setIdZona(""+d.getId().getIdZonaBenef()); 
						not.setDesZona(fiseUtil.getMapaZonaBenef().get(d.getId().getIdZonaBenef()));						 
						listaDetalle.add(not);
					}			    		
				}		    	
			}else if(FiseConstants.NOMBRE_FORMATO_14B.equals(n.getFormato())){				
				FiseFormato14BCPK pk = new FiseFormato14BCPK();
				pk.setCodEmpresa(n.getCodEmpresa());
				pk.setAnoPresentacion(new Long(n.getAnioPres()));
				pk.setMesPresentacion(new Long(n.getMesPres()));
				pk.setAnoInicioVigencia(new Long(n.getAnioIniVig()));
				pk.setAnoFinVigencia(new Long(n.getAnioFinVig()));
				pk.setEtapa(n.getEtapa());  						        
				FiseFormato14BC formato14B = formatoService14B.obtenerFormato14BCByPK(pk);
				if(formato14B!=null){							    
			    	for (FiseFormato14BD d : formato14B.getFiseFormato14BDs()) {
			    		not = new NotificacionBean();
			    		not.setCodEmpresa(d.getId().getCodEmpresa());
						not.setDesEmpresa(mapaEmpresa.get(d.getId().getCodEmpresa()));
						not.setAnioPres(""+d.getId().getAnoPresentacion());
						not.setMesPres(""+d.getId().getMesPresentacion());
						not.setDesMes(fiseUtil.getMapaMeses().get(d.getId().getMesPresentacion()));	
						not.setEtapa(d.getId().getEtapa());						
						not.setAnioIniVig(""+d.getId().getAnoInicioVigencia());		
						not.setAnioFinVig(""+d.getId().getAnoFinVigencia());
						not.setIdZona(""+d.getId().getIdZonaBenef());
						not.setDesZona(fiseUtil.getMapaZonaBenef().get(d.getId().getIdZonaBenef()));					
						listaDetalle.add(not);
					}		    	
				}	    	
			}else if(FiseConstants.NOMBRE_FORMATO_14C.equals(n.getFormato())){ 				
				Formato14CBean f14C = new Formato14CBean();				
				f14C.setCodEmpresa(n.getCodEmpresa());
				f14C.setAnioPres(n.getAnioPres());
				f14C.setMesPres(n.getMesPres());
				f14C.setAnoIniVigencia(n.getAnioIniVig());
				f14C.setAnoFinVigencia(n.getAnioFinVig());
				f14C.setEtapa(n.getEtapa());
				FiseFormato14CC formato14C = formatoService14C.obtenerFiseFormato14CC(f14C);
				if(formato14C!=null){					
			    	for (FiseFormato14CD d : formato14C.getListaDetalle14cDs()) {
			    		not = new NotificacionBean();
			    		not.setCodEmpresa(d.getId().getCodEmpresa());
						not.setDesEmpresa(mapaEmpresa.get(d.getId().getCodEmpresa()));
						not.setAnioPres(""+d.getId().getAnoPresentacion());
						not.setMesPres(""+d.getId().getMesPresentacion());
						not.setDesMes(fiseUtil.getMapaMeses().get(d.getId().getMesPresentacion()));	
						not.setEtapa(d.getId().getEtapa());						
						not.setAnioIniVig(""+d.getId().getAnoInicioVigencia());		
						not.setAnioFinVig(""+d.getId().getAnoFinVigencia());
						not.setIdZona(""+d.getId().getIdZonaBenef());
						not.setDesZona(fiseUtil.getMapaZonaBenef().get(d.getId().getIdZonaBenef()));
						logger.info("Descripcion de la zona a mostrar:  "+not.getDesZona()); 
						not.setIdPersonal(""+d.getId().getIdTipPersonal()); 
						not.setDesPersonal(fiseUtil.getMapaTipoPersonal().get(d.getId().getIdTipPersonal()));					
						listaDetalle.add(not);		
					}		    	
				}	    	
			}		
  			data = toStringListJSON(listaDetalle);
  			logger.info("arreglo json:"+data);
  			PrintWriter pw = response.getWriter();
  			pw.write(data);
  			pw.flush();
  			pw.close();  			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{
			if(not!=null){
				not =null;
			}
		}
	}
	
	/*****Lista las observaciones por cada detalle de los formatos******/
	
	@ResourceMapping("verListaObservacionesDetalle")
  	public void verObservacionesPorDetalle(ResourceRequest request,ResourceResponse response,
  			 @ModelAttribute("notificacionBean")NotificacionBean n){
		NotificacionBean not =null;
		try{
			response.setContentType("application/json");
			
			String data = "";			
			List<NotificacionBean> listaObsFormato = new ArrayList<NotificacionBean>();			
			logger.info("Entrando a ver lista de observaciones por cada detalle del formato"); 				
			logger.info("Codigo empresa:  "+ n.getCodEmpresa()); 
			logger.info("anio pres:  "+ n.getAnioPres());	
			logger.info("mes pres:  "+ n.getMesPres());
			logger.info("formato:  "+ n.getFormato());	
			logger.info("etapa:  "+ n.getEtapa());	
			logger.info("anio ejec:  "+ n.getAnioEjec());
			logger.info("mes ejec:  "+ n.getMesEjec());
			logger.info("anio ini vige:  "+ n.getAnioIniVig());
			logger.info("anio fin vige:  "+ n.getAnioFinVig());	
			logger.info("etapa ejec:  "+ n.getEtapaEjec());
			logger.info("id zona:  "+ n.getIdZona());
			logger.info("id personal:  "+ n.getIdPersonal());
			logger.info("item etapa:  "+ n.getItemEtapa());
			logger.info("cod ubigeo:  "+ n.getCodUbigeo());
			logger.info("cod sector:  "+ n.getCodSector());
  			 							
			if(FiseConstants.NOMBRE_FORMATO_12A.equals(n.getFormato())){				
				FiseFormato12ADPK pk = new FiseFormato12ADPK();
				FiseFormato12AD detalle = new FiseFormato12AD();				
				pk.setCodEmpresa(n.getCodEmpresa());
				pk.setAnoPresentacion(new Long(n.getAnioPres()));
				pk.setMesPresentacion(new Long(n.getMesPres()));
				pk.setAnoEjecucionGasto(new Long(n.getAnioEjec()));
				pk.setMesEjecucionGasto(new Long(n.getMesEjec()));
				pk.setEtapa(n.getEtapa()); 
				pk.setIdZonaBenef(new Long(n.getIdZona()));
				detalle.setId(pk);				
				List<FiseFormato12ADOb> listaObser = formatoService12A.listarFormato12ADObByFormato12AD(detalle);								
				for (FiseFormato12ADOb o : listaObser) {					
					not = new NotificacionBean();
					not.setCodEmpresa(o.getId().getCodEmpresa());					
					not.setAnioPres(""+o.getId().getAnoPresentacion());
					not.setMesPres(""+o.getId().getMesPresentacion());					
					not.setEtapa(o.getId().getEtapa());
					not.setAnioEjec(""+o.getId().getAnoEjecucionGasto());
					not.setMesEjec(""+o.getId().getMesEjecucionGasto()); 					
					not.setIdZona(""+o.getId().getIdZonaBenef()); 
					not.setDesZona(fiseUtil.getMapaZonaBenef().get(o.getId().getIdZonaBenef()));
					not.setItemObs(""+o.getId().getItemObservacion());  
					not.setIdObservacion(""+o.getFiseObservacion().getIdObservacion()); 
					not.setDesObservacion(o.getFiseObservacion().getDescripcion());
					not.setOrigenObs(o.getFiseObservacion().getOrigen());
					listaObsFormato.add(not);				
				} 				   
			}else if(FiseConstants.NOMBRE_FORMATO_12B.equals(n.getFormato())){ 				
				FiseFormato12BDPK pk=new FiseFormato12BDPK();
				FiseFormato12BD detalle = new FiseFormato12BD();
				pk.setCodEmpresa(n.getCodEmpresa());
				pk.setAnoPresentacion(new Integer(n.getAnioPres()));
				pk.setMesPresentacion(new Integer(n.getMesPres()));
				pk.setAnoEjecucionGasto(new Integer(n.getAnioEjec()));
				pk.setMesEjecucionGasto(new Integer(n.getMesEjec()));
				pk.setEtapa(n.getEtapa());  
				pk.setIdZonaBenef(new Integer(n.getIdZona()));
				detalle.setId(pk); 
				List<FiseFormato12BDOb> listaObser = formatoService12B.getLstFormatoObs(detalle);								
				for (FiseFormato12BDOb o : listaObser) {
					not = new NotificacionBean();
					not.setCodEmpresa(o.getId().getCodEmpresa());					
					not.setAnioPres(""+o.getId().getAnoPresentacion());
					not.setMesPres(""+o.getId().getMesPresentacion());					
					not.setEtapa(o.getId().getEtapa());
					not.setAnioEjec(""+o.getId().getAnoEjecucionGasto());
					not.setMesEjec(""+o.getId().getMesEjecucionGasto());					
					not.setIdZona(""+o.getId().getIdZonaBenef()); 
					not.setDesZona(fiseUtil.getMapaZonaBenef().get(new Long(o.getId().getIdZonaBenef())));
					not.setItemObs(""+o.getId().getItemObservacion()); 
					not.setIdObservacion(""+o.getFiseObservacion().getIdObservacion()); 
					not.setDesObservacion(o.getFiseObservacion().getDescripcion());
					not.setOrigenObs(o.getFiseObservacion().getOrigen());
					listaObsFormato.add(not);	
				}						
			}else if(FiseConstants.NOMBRE_FORMATO_12C.equals(n.getFormato())){ 				
				FiseFormato12CDPK pk = new FiseFormato12CDPK();
				FiseFormato12CD detalle = new FiseFormato12CD();
				pk.setCodEmpresa(n.getCodEmpresa());
				pk.setAnoPresentacion(new Long(n.getAnioPres()));
				pk.setMesPresentacion(new Long(n.getMesPres()));
				pk.setEtapa(n.getEtapa()); 
				pk.setAnoEjecucionGasto(new Long(n.getAnioEjec()));
				pk.setMesEjecucionGasto(new Long(n.getMesEjec()));
				pk.setEtapaEjecucion(new Long(n.getEtapaEjec()));
				pk.setNumeroItemEtapa(new Long(n.getItemEtapa())); 
				detalle.setId(pk); 
				List<FiseFormato12CDOb> listaObser = formatoService12C.listarFormato12CDObByFormato12CD(detalle);								
				for (FiseFormato12CDOb o : listaObser) {
					not = new NotificacionBean();
					not.setCodEmpresa(o.getId().getCodEmpresa());					
					not.setAnioPres(""+o.getId().getAnoPresentacion());
					not.setMesPres(""+o.getId().getMesPresentacion());					
					not.setEtapa(o.getId().getEtapa());
					not.setAnioEjec(""+o.getId().getAnoEjecucionGasto());
					not.setMesEjec(""+o.getId().getMesEjecucionGasto()); 					
					not.setEtapaEjec(""+o.getId().getEtapaEjecucion());
					not.setDesEstapaEjec(mapaEtapaEjecucion.get(o.getId().getEtapaEjecucion())); 
					not.setItemEtapa(""+o.getId().getNumeroItemEtapa());
					not.setItemObs(""+o.getId().getItemObservacion()); 
					not.setIdObservacion(""+o.getFiseObservacion().getIdObservacion()); 
					not.setDesObservacion(o.getFiseObservacion().getDescripcion()); 
					not.setOrigenObs(o.getFiseObservacion().getOrigen());
					listaObsFormato.add(not);
				}			
							
			}else if(FiseConstants.NOMBRE_FORMATO_12D.equals(n.getFormato())){				
				FiseFormato12DDPK pk = new FiseFormato12DDPK();
				FiseFormato12DD detalle = new FiseFormato12DD();
				pk.setCodEmpresa(n.getCodEmpresa());
				pk.setAnoPresentacion(new Long(n.getAnioPres()));
				pk.setMesPresentacion(new Long(n.getMesPres()));
				pk.setEtapa(n.getEtapa()); 
				pk.setAnoEjecucionGasto(new Long(n.getAnioEjec()));
				pk.setMesEjecucionGasto(new Long(n.getMesEjec()));
				pk.setEtapaEjecucion(new Long(n.getEtapaEjec()));
				pk.setNumeroItemEtapa(new Long(n.getItemEtapa())); 
				detalle.setId(pk); 
				List<FiseFormato12DDOb> listaObser = formatoService12D.listarFormato12DDObByFormato12DD(detalle);								
				for (FiseFormato12DDOb o : listaObser) {
					not = new NotificacionBean();
					not.setCodEmpresa(o.getId().getCodEmpresa());					
					not.setAnioPres(""+o.getId().getAnoPresentacion());
					not.setMesPres(""+o.getId().getMesPresentacion());					
					not.setEtapa(o.getId().getEtapa());
					not.setAnioEjec(""+o.getId().getAnoEjecucionGasto());
					not.setMesEjec(""+o.getId().getMesEjecucionGasto()); 					
					not.setEtapaEjec(""+o.getId().getEtapaEjecucion());
					not.setDesEstapaEjec(mapaEtapaEjecucion.get(o.getId().getEtapaEjecucion())); 
					not.setItemEtapa(""+o.getId().getNumeroItemEtapa());
					not.setItemObs(""+o.getId().getItemObservacion()); 
					not.setIdObservacion(""+o.getFiseObservacion().getIdObservacion()); 
					not.setDesObservacion(o.getFiseObservacion().getDescripcion());
					not.setOrigenObs(o.getFiseObservacion().getOrigen());
					listaObsFormato.add(not);
				}	
			}else if(FiseConstants.NOMBRE_FORMATO_13A.equals(n.getFormato())){				
				FiseFormato13ADPK pk = new FiseFormato13ADPK();
				FiseFormato13AD detalle = new FiseFormato13AD();
				pk.setCodEmpresa(n.getCodEmpresa());
				pk.setAnoPresentacion(new Long(n.getAnioPres()));
				pk.setMesPresentacion(new Long(n.getMesPres()));
				pk.setEtapa(n.getEtapa());	
				pk.setCodUbigeo(n.getCodUbigeo());
				pk.setCodSectorTipico(n.getCodSector());
				pk.setIdZonaBenef(new Long(n.getIdZona()));
				detalle.setId(pk); 
				List<FiseFormato13ADOb> listaObser = formatoService13A.listarFormato13ADObByFormato13AD(detalle);								
				for (FiseFormato13ADOb o : listaObser) {
					not = new NotificacionBean();
					not.setCodEmpresa(o.getId().getCodEmpresa());					
					not.setAnioPres(""+o.getId().getAnoPresentacion());
					not.setMesPres(""+o.getId().getMesPresentacion());					
					not.setEtapa(o.getId().getEtapa());
					not.setIdZona(""+o.getId().getIdZonaBenef()); 
					not.setDesZona(fiseUtil.getMapaZonaBenef().get(o.getId().getIdZonaBenef()));
					not.setCodUbigeo(o.getId().getCodUbigeo());
					not.setCodSector(o.getId().getCodSectorTipico());
					not.setDesSector(mapaSectorTipico.get(o.getId().getCodSectorTipico())); 
					not.setItemObs(""+o.getId().getItemObservacion()); 
					not.setIdObservacion(""+o.getFiseObservacion().getIdObservacion()); 
					not.setDesObservacion(o.getFiseObservacion().getDescripcion());
					not.setOrigenObs(o.getFiseObservacion().getOrigen());
					listaObsFormato.add(not);
				}			
			}else if(FiseConstants.NOMBRE_FORMATO_14A.equals(n.getFormato())){ 				
				FiseFormato14ADPK pk = new FiseFormato14ADPK();
				FiseFormato14AD detalle = new FiseFormato14AD();
				pk.setCodEmpresa(n.getCodEmpresa());
				pk.setAnoPresentacion(new Long(n.getAnioPres()));
				pk.setMesPresentacion(new Long(n.getMesPres()));
				pk.setAnoInicioVigencia(new Long(n.getAnioIniVig()));
				pk.setAnoFinVigencia(new Long(n.getAnioFinVig()));
				pk.setEtapa(n.getEtapa());
				pk.setIdZonaBenef(new Long(n.getIdZona()));
				detalle.setId(pk); 
				List<FiseFormato14ADOb> listaObser = formatoService14A.listarFormato14ADObByFormato14AD(detalle);								
				for (FiseFormato14ADOb o : listaObser) {
					not = new NotificacionBean();
					not.setCodEmpresa(o.getId().getCodEmpresa());					
					not.setAnioPres(""+o.getId().getAnoPresentacion());
					not.setMesPres(""+o.getId().getMesPresentacion());					
					not.setEtapa(o.getId().getEtapa());						
					not.setAnioIniVig(""+o.getId().getAnoInicioVigencia());		
					not.setAnioFinVig(""+o.getId().getAnoFinVigencia());
					not.setIdZona(""+o.getId().getIdZonaBenef()); 
					not.setDesZona(fiseUtil.getMapaZonaBenef().get(o.getId().getIdZonaBenef()));
					not.setItemObs(""+o.getId().getItemObservacion()); 
					not.setIdObservacion(""+o.getFiseObservacion().getIdObservacion()); 
					not.setDesObservacion(o.getFiseObservacion().getDescripcion());
					not.setOrigenObs(o.getFiseObservacion().getOrigen());
					listaObsFormato.add(not);
				}						    	
			}else if(FiseConstants.NOMBRE_FORMATO_14B.equals(n.getFormato())){				
				FiseFormato14BDPK pk = new FiseFormato14BDPK();
				FiseFormato14BD detalle = new FiseFormato14BD();
				pk.setCodEmpresa(n.getCodEmpresa());
				pk.setAnoPresentacion(new Long(n.getAnioPres()));
				pk.setMesPresentacion(new Long(n.getMesPres()));
				pk.setAnoInicioVigencia(new Long(n.getAnioIniVig()));
				pk.setAnoFinVigencia(new Long(n.getAnioFinVig()));
				pk.setEtapa(n.getEtapa()); 
				pk.setIdZonaBenef(new Long(n.getIdZona()));
				detalle.setId(pk); 
				List<FiseFormato14BDOb> listaObser = formatoService14B.listarFormato14BDObByFormato14BD(detalle);										    
				for (FiseFormato14BDOb o : listaObser) {
					not = new NotificacionBean();
					not.setCodEmpresa(o.getId().getCodEmpresa());					
					not.setAnioPres(""+o.getId().getAnoPresentacion());
					not.setMesPres(""+o.getId().getMesPresentacion());					
					not.setEtapa(o.getId().getEtapa());						
					not.setAnioIniVig(""+o.getId().getAnoInicioVigencia());		
					not.setAnioFinVig(""+o.getId().getAnoFinVigencia());
					not.setIdZona(""+o.getId().getIdZonaBenef()); 
					not.setDesZona(fiseUtil.getMapaZonaBenef().get(o.getId().getIdZonaBenef()));
					not.setItemObs(""+o.getId().getItemObservacion()); 
					not.setIdObservacion(""+o.getFiseObservacion().getIdObservacion()); 
					not.setDesObservacion(o.getFiseObservacion().getDescripcion());	
					not.setOrigenObs(o.getFiseObservacion().getOrigen());
					listaObsFormato.add(not);
				}			    	
			}else if(FiseConstants.NOMBRE_FORMATO_14C.equals(n.getFormato())){ 				
				FiseFormato14CDPK pk = new FiseFormato14CDPK();
				FiseFormato14CD detalle = new FiseFormato14CD();
				pk.setCodEmpresa(n.getCodEmpresa());
				pk.setAnoPresentacion(new Long(n.getAnioPres()));
				pk.setMesPresentacion(new Long(n.getMesPres()));
				pk.setAnoInicioVigencia(new Long(n.getAnioIniVig()));
				pk.setAnoFinVigencia(new Long(n.getAnioFinVig()));
				pk.setEtapa(n.getEtapa()); 
				pk.setIdZonaBenef(new Long(n.getIdZona()));
				pk.setIdTipPersonal(new Long(n.getIdPersonal())); 
				detalle.setId(pk); 
				List<FiseFormato14CDOb> listaObser = formatoService14C.listaObservacionesF14C(detalle);						
				for (FiseFormato14CDOb o : listaObser) {
					not = new NotificacionBean();
					not.setCodEmpresa(o.getId().getCodEmpresa());					
					not.setAnioPres(""+o.getId().getAnoPresentacion());
					not.setMesPres(""+o.getId().getMesPresentacion());					
					not.setEtapa(o.getId().getEtapa());						
					not.setAnioIniVig(""+o.getId().getAnoInicioVigencia());		
					not.setAnioFinVig(""+o.getId().getAnoFinVigencia());
					not.setIdZona(""+o.getId().getIdZonaBenef());
					not.setDesZona(fiseUtil.getMapaZonaBenef().get(o.getId().getIdZonaBenef()));
					not.setIdPersonal(""+o.getId().getIdTipPersonal()); 
					not.setDesPersonal(fiseUtil.getMapaTipoPersonal().get(o.getId().getIdTipPersonal()));
					not.setItemObs(""+o.getId().getItemObservacion()); 
					not.setIdObservacion(""+o.getFiseObservacion().getIdObservacion()); 
					not.setDesObservacion(o.getFiseObservacion().getDescripcion());
					not.setOrigenObs(o.getFiseObservacion().getOrigen());
					logger.info("origen observacion del 14c:  "+o.getFiseObservacion().getOrigen()); 
					listaObsFormato.add(not);		
				}		    	
			}	  				
  			data = toStringListJSON(listaObsFormato);
  			logger.info("arreglo json:"+data);
  			PrintWriter pw = response.getWriter();
  			pw.write(data);
  			pw.flush();
  			pw.close();  			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{
			if(not!=null){
				not =null;
			}
		}
	}
	
	
	
    /*****metodo para grabar observacion manual*****/	
	
	@ResourceMapping("grabarObservacionManual")
  	public void grabarObservacionManual(ResourceRequest request,ResourceResponse response,
  			 @ModelAttribute("notificacionBean")NotificacionBean n){		
		String valor = "0";
		try{
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);			
			String user = themeDisplay.getUser().getLogin();
		    String terminal = themeDisplay.getUser().getLoginIP(); 
		    JSONObject jsonObj = new JSONObject();
		    String codEmpreCompleta = FormatoUtil.rellenaDerecha(n.getCodEmpresa(), ' ', 4);
			logger.info("Entrando a grabar observaciones manual"); 				
			logger.info("Codigo empresa:  "+ n.getCodEmpresa()); 
			logger.info("anio pres:  "+ n.getAnioPres());	
			logger.info("mes pres:  "+ n.getMesPres());
			logger.info("formato:  "+ n.getFormato());	
			logger.info("etapa:  "+ n.getEtapa());	
			logger.info("anio ejec:  "+ n.getAnioEjec());
			logger.info("mes ejec:  "+ n.getMesEjec());
			logger.info("anio ini vige:  "+ n.getAnioIniVig());
			logger.info("anio fin vige:  "+ n.getAnioFinVig());	
			logger.info("etapa ejec:  "+ n.getEtapaEjec());
			logger.info("id zona:  "+ n.getIdZona());
			logger.info("id personal:  "+ n.getIdPersonal());
			logger.info("item etapa:  "+ n.getItemEtapa());
			logger.info("cod ubigeo:  "+ n.getCodUbigeo());
			logger.info("cod sector:  "+ n.getCodSector());
  							
			if(FiseConstants.NOMBRE_FORMATO_12A.equals(n.getFormato())){
				logger.info("ingresando a grabar obs 12A");
				valor = formatoService12A.insertarObservacion12A(codEmpreCompleta, 
						Long.valueOf(n.getAnioPres()), Long.valueOf(n.getMesPres()), 
						Long.valueOf(n.getAnioEjec()), Long.valueOf(n.getMesEjec()), n.getEtapa(), 
					    Long.valueOf(n.getIdZona()), n.getDesObservacion(), user, terminal,
					    n.getIdObsExistente(),FiseConstants.TIPO_OBSERVACION_MANUAL);
						
			}else if(FiseConstants.NOMBRE_FORMATO_12B.equals(n.getFormato())){ 				
				logger.info("ingresando a grabar obs 12B");
				valor = formatoService12B.insertarObservacion12B(codEmpreCompleta, 
						Integer.valueOf(n.getAnioPres()), Integer.valueOf(n.getMesPres()), 
						Integer.valueOf(n.getAnioEjec()), Integer.valueOf(n.getMesEjec()), n.getEtapa(), 
						Integer.valueOf(n.getIdZona()), n.getDesObservacion(), user, terminal,
						n.getIdObsExistente(),FiseConstants.TIPO_OBSERVACION_MANUAL);
						
			}else if(FiseConstants.NOMBRE_FORMATO_12C.equals(n.getFormato())){ 				
				logger.info("ingresando a grabar obs 12C");
				valor = formatoService12C.insertarObservacion12C(codEmpreCompleta, 
						Long.valueOf(n.getAnioPres()), Long.valueOf(n.getMesPres()), 
						Long.valueOf(n.getAnioEjec()), Long.valueOf(n.getMesEjec()), n.getEtapa(), 
					    Long.valueOf(n.getEtapaEjec()),Long.valueOf(n.getItemEtapa()),
					    n.getDesObservacion(), user, terminal,
					    n.getIdObsExistente(),FiseConstants.TIPO_OBSERVACION_MANUAL);
				
			}else if(FiseConstants.NOMBRE_FORMATO_12D.equals(n.getFormato())){	
				logger.info("ingresando a grabar obs 12D");
				valor = formatoService12D.insertarObservacion12D(codEmpreCompleta, 
						Long.valueOf(n.getAnioPres()), Long.valueOf(n.getMesPres()), 
						Long.valueOf(n.getAnioEjec()), Long.valueOf(n.getMesEjec()), n.getEtapa(), 
					    Long.valueOf(n.getEtapaEjec()),Long.valueOf(n.getItemEtapa()),
					    n.getDesObservacion(), user, terminal,
					    n.getIdObsExistente(),FiseConstants.TIPO_OBSERVACION_MANUAL);
				
			}else if(FiseConstants.NOMBRE_FORMATO_13A.equals(n.getFormato())){				
				logger.info("ingresando a grabar obs 13A");
				valor = formatoService13A.insertarObservacion13A(codEmpreCompleta, 
						Long.valueOf(n.getAnioPres()), Long.valueOf(n.getMesPres()), 
						n.getCodUbigeo(), n.getCodSector(), n.getEtapa(),Long.valueOf(n.getIdZona()),					    
					    n.getDesObservacion(), user, terminal,
					    n.getIdObsExistente(),FiseConstants.TIPO_OBSERVACION_MANUAL);
					
			}else if(FiseConstants.NOMBRE_FORMATO_14A.equals(n.getFormato())){ 			
				logger.info("ingresando a grabar obs 14A");
				valor = formatoService14A.insertarObservacion14A(codEmpreCompleta, 
						Long.valueOf(n.getAnioPres()), Long.valueOf(n.getMesPres()), 
						Long.valueOf(n.getAnioIniVig()), Long.valueOf(n.getAnioFinVig()), n.getEtapa(), 
					    Long.valueOf(n.getIdZona()), n.getDesObservacion(), user, terminal,
					    n.getIdObsExistente(),FiseConstants.TIPO_OBSERVACION_MANUAL);
							    	
			}else if(FiseConstants.NOMBRE_FORMATO_14B.equals(n.getFormato())){			 
				logger.info("ingresando a grabar obs 14B");
				valor = formatoService14B.insertarObservacion14B(codEmpreCompleta, 
						Long.valueOf(n.getAnioPres()), Long.valueOf(n.getMesPres()), 
						Long.valueOf(n.getAnioIniVig()), Long.valueOf(n.getAnioFinVig()), n.getEtapa(), 
					    Long.valueOf(n.getIdZona()), n.getDesObservacion(), user, terminal,
					    n.getIdObsExistente(),FiseConstants.TIPO_OBSERVACION_MANUAL);
				    	
			}else if(FiseConstants.NOMBRE_FORMATO_14C.equals(n.getFormato())){ 			
				logger.info("ingresando a grabar obs 14C");
				valor = formatoService14C.insertarObservacion14C(codEmpreCompleta, 
						Long.valueOf(n.getAnioPres()), Long.valueOf(n.getMesPres()), 
						Long.valueOf(n.getAnioIniVig()), Long.valueOf(n.getAnioFinVig()), n.getEtapa(), 
					    Long.valueOf(n.getIdZona()), Long.valueOf(n.getIdPersonal()), 
					    n.getDesObservacion(), user, terminal,
					    n.getIdObsExistente(),FiseConstants.TIPO_OBSERVACION_MANUAL);					
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
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	
	@ResourceMapping("grabarObservacionExistente")
  	public void grabarObservacionExistente(ResourceRequest request,ResourceResponse response,
  			 @ModelAttribute("notificacionBean")NotificacionBean n){		
		String valor = "0";
		try{
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);			
			String user = themeDisplay.getUser().getLogin();
		    String terminal = themeDisplay.getUser().getLoginIP(); 
		    JSONObject jsonObj = new JSONObject();
		    String codEmpreCompleta = FormatoUtil.rellenaDerecha(n.getCodEmpresa(), ' ', 4);
			logger.info("Entrando a grabar observaciones existente"); 				
			logger.info("Codigo empresa:  "+ n.getCodEmpresa()); 
			logger.info("anio pres:  "+ n.getAnioPres());	
			logger.info("mes pres:  "+ n.getMesPres());
			logger.info("formato:  "+ n.getFormato());	
			logger.info("etapa:  "+ n.getEtapa());	
			logger.info("anio ejec:  "+ n.getAnioEjec());
			logger.info("mes ejec:  "+ n.getMesEjec());
			logger.info("anio ini vige:  "+ n.getAnioIniVig());
			logger.info("anio fin vige:  "+ n.getAnioFinVig());	
			logger.info("etapa ejec:  "+ n.getEtapaEjec());
			logger.info("id zona:  "+ n.getIdZona());
			logger.info("id personal:  "+ n.getIdPersonal());
			logger.info("item etapa:  "+ n.getItemEtapa());
			logger.info("cod ubigeo:  "+ n.getCodUbigeo());
			logger.info("cod sector:  "+ n.getCodSector());
  			 							
			if(FiseConstants.NOMBRE_FORMATO_12A.equals(n.getFormato())){
				logger.info("ingresando a grabar obs 12A");
				valor = formatoService12A.insertarObservacion12A(codEmpreCompleta, 
						Long.valueOf(n.getAnioPres()), Long.valueOf(n.getMesPres()), 
						Long.valueOf(n.getAnioEjec()), Long.valueOf(n.getMesEjec()), n.getEtapa(), 
					    Long.valueOf(n.getIdZona()), n.getDesObservacion(), user, terminal,
					    n.getIdObsExistente(),FiseConstants.TIPO_OBSERVACION_AUTOMATICA);
						
			}else if(FiseConstants.NOMBRE_FORMATO_12B.equals(n.getFormato())){ 				
				logger.info("ingresando a grabar obs 12B");
				valor = formatoService12B.insertarObservacion12B(codEmpreCompleta, 
						Integer.valueOf(n.getAnioPres()), Integer.valueOf(n.getMesPres()), 
						Integer.valueOf(n.getAnioEjec()), Integer.valueOf(n.getMesEjec()), n.getEtapa(), 
						Integer.valueOf(n.getIdZona()), n.getDesObservacion(), user, terminal,
						n.getIdObsExistente(),FiseConstants.TIPO_OBSERVACION_AUTOMATICA);
						
			}else if(FiseConstants.NOMBRE_FORMATO_12C.equals(n.getFormato())){ 				
				logger.info("ingresando a grabar obs 12C");
				valor = formatoService12C.insertarObservacion12C(codEmpreCompleta, 
						Long.valueOf(n.getAnioPres()), Long.valueOf(n.getMesPres()), 
						Long.valueOf(n.getAnioEjec()), Long.valueOf(n.getMesEjec()), n.getEtapa(), 
					    Long.valueOf(n.getEtapaEjec()),Long.valueOf(n.getItemEtapa()),
					    n.getDesObservacion(), user, terminal,
					    n.getIdObsExistente(),FiseConstants.TIPO_OBSERVACION_AUTOMATICA);
				
			}else if(FiseConstants.NOMBRE_FORMATO_12D.equals(n.getFormato())){	
				logger.info("ingresando a grabar obs 12D");
				valor = formatoService12D.insertarObservacion12D(codEmpreCompleta, 
						Long.valueOf(n.getAnioPres()), Long.valueOf(n.getMesPres()), 
						Long.valueOf(n.getAnioEjec()), Long.valueOf(n.getMesEjec()), n.getEtapa(), 
					    Long.valueOf(n.getEtapaEjec()),Long.valueOf(n.getItemEtapa()),
					    n.getDesObservacion(), user, terminal,
					    n.getIdObsExistente(),FiseConstants.TIPO_OBSERVACION_AUTOMATICA);
				
			}else if(FiseConstants.NOMBRE_FORMATO_13A.equals(n.getFormato())){				
				logger.info("ingresando a grabar obs 13A");
				valor = formatoService13A.insertarObservacion13A(codEmpreCompleta, 
						Long.valueOf(n.getAnioPres()), Long.valueOf(n.getMesPres()), 
						n.getCodUbigeo(), n.getCodSector(), n.getEtapa(),Long.valueOf(n.getIdZona()),					    
					    n.getDesObservacion(), user, terminal,
					    n.getIdObsExistente(),FiseConstants.TIPO_OBSERVACION_AUTOMATICA);
					
			}else if(FiseConstants.NOMBRE_FORMATO_14A.equals(n.getFormato())){ 			
				logger.info("ingresando a grabar obs 14A");
				valor = formatoService14A.insertarObservacion14A(codEmpreCompleta, 
						Long.valueOf(n.getAnioPres()), Long.valueOf(n.getMesPres()), 
						Long.valueOf(n.getAnioIniVig()), Long.valueOf(n.getAnioFinVig()), n.getEtapa(), 
					    Long.valueOf(n.getIdZona()), n.getDesObservacion(), user, terminal,
					    n.getIdObsExistente(),FiseConstants.TIPO_OBSERVACION_AUTOMATICA);
							    	
			}else if(FiseConstants.NOMBRE_FORMATO_14B.equals(n.getFormato())){			 
				logger.info("ingresando a grabar obs 14B");
				valor = formatoService14B.insertarObservacion14B(codEmpreCompleta, 
						Long.valueOf(n.getAnioPres()), Long.valueOf(n.getMesPres()), 
						Long.valueOf(n.getAnioIniVig()), Long.valueOf(n.getAnioFinVig()), n.getEtapa(), 
					    Long.valueOf(n.getIdZona()), n.getDesObservacion(), user, terminal,
					    n.getIdObsExistente(),FiseConstants.TIPO_OBSERVACION_AUTOMATICA);
				    	
			}else if(FiseConstants.NOMBRE_FORMATO_14C.equals(n.getFormato())){ 			
				logger.info("ingresando a grabar obs 14C");
				valor = formatoService14C.insertarObservacion14C(codEmpreCompleta, 
						Long.valueOf(n.getAnioPres()), Long.valueOf(n.getMesPres()), 
						Long.valueOf(n.getAnioIniVig()), Long.valueOf(n.getAnioFinVig()), n.getEtapa(), 
					    Long.valueOf(n.getIdZona()), Long.valueOf(n.getIdPersonal()), 
					    n.getDesObservacion(), user, terminal,
					    n.getIdObsExistente(),FiseConstants.TIPO_OBSERVACION_AUTOMATICA);					
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
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	
	
	@ResourceMapping("busquedaObsExistente")
  	public void busquedaObservacionExistente(ResourceRequest request,ResourceResponse response,
  			 @ModelAttribute("notificacionBean")NotificacionBean n){
		
		try{
			response.setContentType("application/json");					
			
			String data ="";	
			
			List<FiseObservacion> listaObs = fiseObservacionGartService.buscarFiseObservacion(n.getIdObsBusq(), n.getDescObsBusq());
			 			
  			//logger.info("tama�o de la lista observaciones   :"+listaObs.size());
  			
  			List<NotificacionBean> listaNotifi = new ArrayList<NotificacionBean>();
  			
  			for(FiseObservacion o : listaObs){    				
  				NotificacionBean not = new NotificacionBean();
  				not.setIdObsExistente(o.getIdObservacion()); 
  				not.setTipoObservacion(o.getOrigen().equals("A")?"Autom�tica":"Manual");
  				not.setDescObsExistente(o.getDescripcion()); 
  				listaNotifi.add(not);
  			} 			
  			data = toStringListJSON(listaNotifi);
  			logger.info("arreglo json:"+data);
  			PrintWriter pw = response.getWriter();
  			pw.write(data);
  			pw.flush();
  			pw.close();   			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}	
	
	
   /*****metodo para actualizar observacion manual*****/	
	
	@ResourceMapping("actualizarObservacionManual")
  	public void actualizarObservacionManual(ResourceRequest request,ResourceResponse response,
  			 @ModelAttribute("notificacionBean")NotificacionBean n){		
		FiseObservacionBean bean = null;
		try{
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);			
			String user = themeDisplay.getUser().getLogin();
		    String terminal = themeDisplay.getUser().getLoginIP();
		    
		    JSONObject jsonObj = new JSONObject();	   
  			 							
			logger.info("Entrando a actualizar observacion manual"); 	
			logger.info("ID:  "+ n.getIdObservacion()); 
			logger.info("Descripcion:  "+ n.getDesObservacion());		
			
			bean = new FiseObservacionBean();
			bean.setId(n.getIdObservacion()); 
			bean.setDescripcion(n.getDesObservacion());
			bean.setUsuario(user);
			bean.setTerminal(terminal);			
			String valor = fiseObservacionGartService.actualizarDatosFiseObservacion(bean);
			logger.info("valor de la transaccion al actualizar:  "+valor); 
			if(!valor.equals("0")){ 
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
			logger.error(e.getMessage());
		}finally{
			if(bean!=null){
				bean = null;
			}
		}
	}
	
	/**Metodo para eliminar una observacion manual*/
	
	@ResourceMapping("eliminarObservacionManual")
  	public void eliminarObservacionManual(ResourceRequest request,ResourceResponse response,
  			 @ModelAttribute("notificacionBean")NotificacionBean n){		
		String valor = "0";
		try{			
		    JSONObject jsonObj = new JSONObject();
		    String codEmpreCompleta = FormatoUtil.rellenaDerecha(n.getCodEmpresa(), ' ', 4);
			logger.info("Entrando a eliminar observaciones manual"); 				
			logger.info("Codigo empresa:  "+ n.getCodEmpresa()); 
			logger.info("anio pres:  "+ n.getAnioPres());	
			logger.info("mes pres:  "+ n.getMesPres());
			logger.info("formato:  "+ n.getFormato());	
			logger.info("etapa:  "+ n.getEtapa());	
			logger.info("anio ejec:  "+ n.getAnioEjec());
			logger.info("mes ejec:  "+ n.getMesEjec());
			logger.info("anio ini vige:  "+ n.getAnioIniVig());
			logger.info("anio fin vige:  "+ n.getAnioFinVig());	
			logger.info("etapa ejec:  "+ n.getEtapaEjec());
			logger.info("id zona:  "+ n.getIdZona());
			logger.info("id personal:  "+ n.getIdPersonal());
			logger.info("item etapa:  "+ n.getItemEtapa());
			logger.info("cod ubigeo:  "+ n.getCodUbigeo());
			logger.info("cod sector:  "+ n.getCodSector());
			logger.info("itemObs:   "+n.getItemObs()); 
			logger.info("id observacion:   "+n.getIdObservacion()); 
  			 							
			if(FiseConstants.NOMBRE_FORMATO_12A.equals(n.getFormato())){
				logger.info("ingresando a eliminar obs 12A");
				valor = formatoService12A.eliminarObservacion12A(codEmpreCompleta, 
						Long.valueOf(n.getAnioPres()), Long.valueOf(n.getMesPres()), 
						Long.valueOf(n.getAnioEjec()), Long.valueOf(n.getMesEjec()), n.getEtapa(), 
					    Long.valueOf(n.getIdZona()), n.getIdObservacion(), Long.valueOf(n.getItemObs()));
						
			}else if(FiseConstants.NOMBRE_FORMATO_12B.equals(n.getFormato())){ 				
				logger.info("ingresando a eliminar obs 12B");
				valor = formatoService12B.eliminarObservacion12B(codEmpreCompleta, 
						Integer.valueOf(n.getAnioPres()), Integer.valueOf(n.getMesPres()), 
						Integer.valueOf(n.getAnioEjec()), Integer.valueOf(n.getMesEjec()), n.getEtapa(), 
						Integer.valueOf(n.getIdZona()), n.getIdObservacion(), Integer.valueOf(n.getItemObs()));
						
			}else if(FiseConstants.NOMBRE_FORMATO_12C.equals(n.getFormato())){ 				
				logger.info("ingresando a eliminar obs 12C");
				valor = formatoService12C.eliminarObservacion12C(codEmpreCompleta, 
						Long.valueOf(n.getAnioPres()), Long.valueOf(n.getMesPres()), 
						Long.valueOf(n.getAnioEjec()), Long.valueOf(n.getMesEjec()), n.getEtapa(), 
					    Long.valueOf(n.getEtapaEjec()),Long.valueOf(n.getItemEtapa()),
					    n.getIdObservacion(), Long.valueOf(n.getItemObs()));
				
			}else if(FiseConstants.NOMBRE_FORMATO_12D.equals(n.getFormato())){	
				logger.info("ingresando a eliminar obs 12D");
				valor = formatoService12D.eliminarObservacion12D(codEmpreCompleta, 
						Long.valueOf(n.getAnioPres()), Long.valueOf(n.getMesPres()), 
						Long.valueOf(n.getAnioEjec()), Long.valueOf(n.getMesEjec()), n.getEtapa(), 
					    Long.valueOf(n.getEtapaEjec()),Long.valueOf(n.getItemEtapa()),
					    n.getIdObservacion(), Long.valueOf(n.getItemObs()));
				
			}else if(FiseConstants.NOMBRE_FORMATO_13A.equals(n.getFormato())){				
				logger.info("ingresando a eliminar obs 13A");
				valor = formatoService13A.eliminarObservacion13A(codEmpreCompleta, 
						Long.valueOf(n.getAnioPres()), Long.valueOf(n.getMesPres()), 
						n.getCodUbigeo(), n.getCodSector(), n.getEtapa(),Long.valueOf(n.getIdZona()),					    
						n.getIdObservacion(), Long.valueOf(n.getItemObs()));
					
			}else if(FiseConstants.NOMBRE_FORMATO_14A.equals(n.getFormato())){ 			
				logger.info("ingresando a eliminar obs 14A");
				valor = formatoService14A.eliminarObservacion14A(codEmpreCompleta, 
						Long.valueOf(n.getAnioPres()), Long.valueOf(n.getMesPres()), 
						Long.valueOf(n.getAnioIniVig()), Long.valueOf(n.getAnioFinVig()), n.getEtapa(), 
					    Long.valueOf(n.getIdZona()), n.getIdObservacion(), Long.valueOf(n.getItemObs()));
							    	
			}else if(FiseConstants.NOMBRE_FORMATO_14B.equals(n.getFormato())){			 
				logger.info("ingresando a eliminar obs 14B");
				valor = formatoService14B.eliminarObservacion14B(codEmpreCompleta, 
						Long.valueOf(n.getAnioPres()), Long.valueOf(n.getMesPres()), 
						Long.valueOf(n.getAnioIniVig()), Long.valueOf(n.getAnioFinVig()), n.getEtapa(), 
					    Long.valueOf(n.getIdZona()), n.getIdObservacion(), Long.valueOf(n.getItemObs()));
				    	
			}else if(FiseConstants.NOMBRE_FORMATO_14C.equals(n.getFormato())){ 			
				logger.info("ingresando a eliminar obs 14C");
				valor = formatoService14C.eliminarObservacion14C(codEmpreCompleta, 
						Long.valueOf(n.getAnioPres()), Long.valueOf(n.getMesPres()), 
						Long.valueOf(n.getAnioIniVig()), Long.valueOf(n.getAnioFinVig()), n.getEtapa(), 
					    Long.valueOf(n.getIdZona()), Long.valueOf(n.getIdPersonal()), 
					    n.getIdObservacion(), Long.valueOf(n.getItemObs()));					
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
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	 }
	
	
	

}
