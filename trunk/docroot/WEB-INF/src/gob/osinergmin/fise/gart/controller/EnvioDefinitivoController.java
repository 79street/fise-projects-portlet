package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.EnvioDefinitivoBean;
import gob.osinergmin.fise.bean.Formato12C12D13Generic;
import gob.osinergmin.fise.bean.Formato14CBean;
import gob.osinergmin.fise.bean.Formato14Generic;
import gob.osinergmin.fise.bean.MensajeErrorBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.CfgTabla;
import gob.osinergmin.fise.domain.FiseFormato12AC;
import gob.osinergmin.fise.domain.FiseFormato12ACPK;
import gob.osinergmin.fise.domain.FiseFormato12AD;
import gob.osinergmin.fise.domain.FiseFormato12ADOb;
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
import gob.osinergmin.fise.gart.jsp.FileEntryJSP;
import gob.osinergmin.fise.gart.service.CfgTablaGartService;
import gob.osinergmin.fise.gart.service.CommonGartService;
import gob.osinergmin.fise.gart.service.FiseGrupoInformacionGartService;
import gob.osinergmin.fise.gart.service.Formato12AGartService;
import gob.osinergmin.fise.gart.service.Formato13AGartService;
import gob.osinergmin.fise.gart.service.Formato14AGartService;
import gob.osinergmin.fise.gart.service.Formato14BGartService;
import gob.osinergmin.fise.gart.service.Formato14CGartService;
import gob.osinergmin.fise.util.FormatoUtil;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
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


@Controller("envioDefinitivoController")
@RequestMapping("VIEW")
public class EnvioDefinitivoController {
	
	Logger logger = Logger.getLogger(EnvioDefinitivoController.class);
	
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
	Formato12AGartService formatoService12A;
	
	@Autowired
	@Qualifier("formato13AGartServiceImpl")
	private Formato13AGartService formatoService13A;
	
	@Autowired
	@Qualifier("formato14AGartServiceImpl")
	Formato14AGartService formatoService14A;
	
	@Autowired
	@Qualifier("formato14BGartServiceImpl")
	Formato14BGartService formatoService14B;
	
	@Autowired
	@Qualifier("formato14CGartServiceImpl")
	private Formato14CGartService formatoService14C;
	
	
	@Autowired
	@Qualifier("cfgTablaGartServiceImpl")
	private CfgTablaGartService tablaService;
	
	
	
	private Map<String, String> mapaEmpresa;	
	private List<MensajeErrorBean> listaObservaciones;
	private Map<String, String> mapaErrores;
	private Map<String, String> mapaSectorTipico;
	
	
	@ModelAttribute("envioDefinitivoBean")
    public EnvioDefinitivoBean listEnvioDefinitivoBean() {
		EnvioDefinitivoBean comman  = new EnvioDefinitivoBean();
        return comman;	        
    }

	
	@RequestMapping
	public String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse,
			     @ModelAttribute("envioDefinitivoBean")EnvioDefinitivoBean n){
        try {           	
    		if(n.getListaEmpresas()==null){
    			logger.info("Lista de empresa es null:  " +renderRequest); 
    			n.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));
    		}     		
    		n.setAdmin(fiseUtil.esAdministrador(renderRequest));
    		
    		n.setListaGrupoInf(fiseGrupoInformacionService.listarGrupoInformacion(FiseConstants.MENSUAL)); 
    		
    		mapaEmpresa = fiseUtil.getMapaEmpresa();
    		
    		mapaErrores = fiseUtil.getMapaErrores();
    		
    		mapaSectorTipico = fiseUtil.getMapaSectorTipico();
    		
    		model.addAttribute("model", n);
    		
		} catch (Exception e) {
			logger.info("Ocurrio un errror al caragar la pagina notificacion-validacion"); 
			e.printStackTrace();
		}		
		return "envioDefinitivo";
	}
	
	
	@ResourceMapping("busquedaEnvioDefinitivo")
  	public void busqueda(ResourceRequest request,ResourceResponse response,
  			 @ModelAttribute("envioDefinitivoBean")EnvioDefinitivoBean n){
		
		try{
			response.setContentType("application/json");
			
			PortletRequest pRequest = (PortletRequest) request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
			
			String codEmpresa = n.getCodEmpresaBusq();				
			String optionFormato = n.getOptionFormato();
			String idgrupoInf = n.getGrupoInfBusq();
			String etapa = n.getEtapaBusq();
		    
			String data ="";			
			logger.info("codigo empresa "+ codEmpresa);  			
  			logger.info("id Grupo inf "+ idgrupoInf);  	 			
  			logger.info("Option formato "+ optionFormato);
  			logger.info("etapa "+ etapa);
  			
  			
  			List<EnvioDefinitivoBean> lista =commonService.buscarEnvioDefinitivo(codEmpresa, 
  					optionFormato, etapa, new Long(idgrupoInf));		
  			
  			logger.info("tamaño de la lista envio Defin..   :"+lista.size());
  			
  			List<EnvioDefinitivoBean> listaEnvio = new ArrayList<EnvioDefinitivoBean>();
  			
  			for(EnvioDefinitivoBean not : lista){    				
  				not.setDesEmpresa(mapaEmpresa.get(not.getCodEmpresa()));
  				not.setDesMes(fiseUtil.getMapaMeses().get(Long.valueOf(not.getMesPres())));
  				if(!"00".equals(not.getMesEjec())){ 
  					not.setDesMesEje(fiseUtil.getMapaMeses().get(Long.valueOf(not.getMesEjec())));   		
  				}else{
  					not.setDesMesEje("---");
  				}
  				listaEnvio.add(not);
  			} 			
  			data = toStringListJSON(listaEnvio);
  			logger.info("arreglo json:"+data);
  			PrintWriter pw = response.getWriter();
  			pw.write(data);
  			pw.flush();
  			pw.close(); 
  			pRequest.getPortletSession().setAttribute("listaEnvioDefinitivo", listaEnvio, PortletSession.APPLICATION_SCOPE);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}	
	
	private String toStringListJSON(List<EnvioDefinitivoBean> lista) {
		Serializer serializer = new JsonSerializer();
		Object result = serializer.serialize(lista);
		String data = String.valueOf(result);
		return data;
	}
	
	@ResourceMapping("cargarGrupoInformacion")
  	public void cargaPeriodo(ModelMap model, ResourceRequest request,ResourceResponse response,
  			 @ModelAttribute("envioDefinitivoBean")EnvioDefinitivoBean n){
		try {			
  			response.setContentType("applicacion/json");
  			String tipoFormato = n.getOptionFormato();
  			
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
	
	
	@ResourceMapping("procesarNotificacion")
  	public void procesarNotificacion(ResourceRequest request,ResourceResponse response,
  			 @ModelAttribute("envioDefinitivoBean")EnvioDefinitivoBean n){
		
		try{
			response.setContentType("application/json");
			
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			
			JSONObject jsonObj = new JSONObject();
			
			String codEmpresa = n.getCodEmpresaBusq();				
			String optionFormato = n.getOptionFormato();
			String idgrupoInf = n.getGrupoInfBusq();
			String etapa = n.getEtapaBusq();    
			
			boolean valor = true;
			logger.info("codigo empresa "+ codEmpresa);  			
  			logger.info("id Grupo inf "+ idgrupoInf);  	 			
  			logger.info("Option formato "+ optionFormato);
  			logger.info("etapa "+ etapa);
  			
  			
  			List<EnvioDefinitivoBean> lista = new ArrayList<EnvioDefinitivoBean>();
  			
  			logger.info("tamaño de la lista notificacion   :"+lista.size());
  			for(EnvioDefinitivoBean not:lista){
  				
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
  	  		          break;
  	  		        } 	  		      
  				}else if(FiseConstants.NOMBRE_FORMATO_12B.equals(not.getFormato())){ 
  				//falta	
  				}else if(FiseConstants.NOMBRE_FORMATO_12C.equals(not.getFormato())){ 
  				//falta	
  				}else if(FiseConstants.NOMBRE_FORMATO_12D.equals(not.getFormato())){ 
  				//falta	
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
  			    		break;
  			    	}  	
  				}				
  			}//fin del for de la lista		
  			
  			if(valor && lista.size()>0){
  				jsonObj.put("resultado", "OK");	   
  			}else if(lista.size()==0){
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
  			 @ModelAttribute("envioDefinitivoBean")EnvioDefinitivoBean n){
		
		HttpServletRequest req = PortalUtil.getHttpServletRequest(request);
		HttpSession session = req.getSession();
		
		try{
			response.setContentType("application/json");
			
			PortletRequest pRequest = (PortletRequest) request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
			
			JSONArray jsonArray = null;
			
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
				
				cargarListaObservaciones12A(formato12A.getFiseFormato12ADs());	
				
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
				fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_FORMATO_VAL,
						FiseConstants.NOMBRE_EXCEL_VALIDACION_F12A, 
						FiseConstants.NOMBRE_HOJA_VALIDACION, listaObservaciones);
		    	/***/
			}else if(FiseConstants.NOMBRE_FORMATO_12B.equals(n.getFormato())){ 
				jsonArray= new JSONArray();
				//falta
			}else if(FiseConstants.NOMBRE_FORMATO_12C.equals(n.getFormato())){ 
				jsonArray= new JSONArray();
				//falta
			}else if(FiseConstants.NOMBRE_FORMATO_12D.equals(n.getFormato())){ 
				jsonArray= new JSONArray();
				//falta
			}else if(FiseConstants.NOMBRE_FORMATO_13A.equals(n.getFormato())){
				jsonArray= new JSONArray();
				FiseFormato13ACPK pk = new FiseFormato13ACPK();
				pk.setCodEmpresa(n.getCodEmpresa());
				pk.setAnoPresentacion(new Long(n.getAnioPres()));
				pk.setMesPresentacion(new Long(n.getMesPres()));
				pk.setEtapa(n.getEtapa());
				
				FiseFormato13AC formato13A = formatoService13A.obtenerFormato13ACByPK(pk);	
				
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
			}
			 pRequest.getPortletSession().setAttribute("codigoEmpresa", n.getCodEmpresa(), PortletSession.APPLICATION_SCOPE);
			 pRequest.getPortletSession().setAttribute("anioPresentacion", n.getAnioPres(), PortletSession.APPLICATION_SCOPE);
			 pRequest.getPortletSession().setAttribute("mesPresentacion", n.getMesPres(), PortletSession.APPLICATION_SCOPE);
			 pRequest.getPortletSession().setAttribute("formato", n.getFormato(), PortletSession.APPLICATION_SCOPE);
			 pRequest.getPortletSession().setAttribute("etapa", n.getEtapa(), PortletSession.APPLICATION_SCOPE);
			 pRequest.getPortletSession().setAttribute("anioIniVig", n.getAnioIniVig(), PortletSession.APPLICATION_SCOPE);
			 pRequest.getPortletSession().setAttribute("anioFinVig", n.getAnioFinVig(), PortletSession.APPLICATION_SCOPE);			 
			 
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
				obs.setDescripcion(mapaErrores.get(observacion.getFiseObservacion().getIdObservacion()));
				//obs.setDescCodSectorTipico(mapaSectorTipico.get(observacion.getId().getCodSectorTipico()));
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
				obs.setDescripcion(mapaErrores.get(observacion.getFiseObservacion().getIdObservacion()));
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
				obs.setDescripcion(mapaErrores.get(observacion.getFiseObservacion().getIdObservacion()));
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
				obs.setDescripcion(mapaErrores.get(observacion.getFiseObservacion().getIdObservacion()));
				listaObservaciones.add(obs);
			}
		}
	}
	
	private void cargarListaObservaciones14C(List<FiseFormato14CD> listaDetalle) throws Exception{
		int cont=0;
		listaObservaciones = new ArrayList<MensajeErrorBean>();
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
				listaObservaciones.add(obs);							
			}
		}
	}	
	
	@ResourceMapping("reporteValidacionNotificacion")
	public void reporteNotificacion(ResourceRequest request,ResourceResponse response,
 			 @ModelAttribute("envioDefinitivoBean")EnvioDefinitivoBean n){
		try {
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();
	        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
	        
	        logger.info("Codigo empresa:  "+ n.getCodEmpresa());
	        
	        PortletRequest pRequest = (PortletRequest) request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
	        
		    JSONArray jsonArray = new JSONArray(); 
		    
		    String nombreReporte = "";
		    String nombreArchivo = "";
		    String tipoFormato = "";
		    String tipoArchivo = "";	  
	
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
		   
		    
		    CfgTabla tabla = null;
	    	String descripcionFormato = "";   	
	    	
	    	Map<String, Object> mapa = new HashMap<String, Object>();
	    	mapa.put("IMG", session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg"));
	    	mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
	    	
	    	if(FiseConstants.NOMBRE_FORMATO_12A.equals(formato)){
	    		nombreReporte = request.getParameter("nombreReporte").trim();
	    		nombreArchivo = request.getParameter("nombreArchivo").trim();
	    		tipoFormato = FiseConstants.TIPO_FORMATO_VAL;
	    		tipoArchivo = request.getParameter("tipoArchivo").trim();	
	    		session.setAttribute("nombreReporte",nombreReporte);
	    		session.setAttribute("nombreArchivo",nombreArchivo);
	    		session.setAttribute("tipoFormato",tipoFormato);
	    		session.setAttribute("tipoArchivo",tipoArchivo);    
	    		tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12A);
	    		if( tabla!=null ){
		    		descripcionFormato = tabla.getDescripcionTabla();
		    	}
			}else if(FiseConstants.NOMBRE_FORMATO_12B.equals(formato)){
				//falta
				tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12B);
	    		if( tabla!=null ){
		    		descripcionFormato = tabla.getDescripcionTabla();
		    	}
			}else if(FiseConstants.NOMBRE_FORMATO_12C.equals(formato)){ 
				//falta
				tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12C);
	    		if( tabla!=null ){
		    		descripcionFormato = tabla.getDescripcionTabla();
		    	}
			}else if(FiseConstants.NOMBRE_FORMATO_12D.equals(formato)){
				//falta
				tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12D);
	    		if( tabla!=null ){
		    		descripcionFormato = tabla.getDescripcionTabla();
		    	}
			}else if(FiseConstants.NOMBRE_FORMATO_13A.equals(formato)){
				nombreReporte = request.getParameter("nombreReporte").trim();
				nombreArchivo = request.getParameter("nombreArchivo").trim();
				tipoFormato = FiseConstants.TIPO_FORMATO_VAL_13A;
				tipoArchivo = request.getParameter("tipoArchivo").trim();

				session.setAttribute("nombreReporte", nombreReporte);
				session.setAttribute("nombreArchivo", nombreArchivo);
				session.setAttribute("tipoFormato", tipoFormato);
				session.setAttribute("tipoArchivo", tipoArchivo);
				tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO13A);
	    		if( tabla!=null ){
		    		descripcionFormato = tabla.getDescripcionTabla();
		    	}
	    		mapa.put("INICIO_VIGENCIA", anioIniVig!=null?anioIniVig:"");
				mapa.put("FIN_VIGENCIA", anioFinVig!=null?anioFinVig:"");
	    		
			}else if(FiseConstants.NOMBRE_FORMATO_14A.equals(formato)){ 
				nombreReporte = request.getParameter("nombreReporte").trim();
	    		nombreArchivo = request.getParameter("nombreArchivo").trim();
	    		tipoFormato = FiseConstants.TIPO_FORMATO_VAL;
	    		tipoArchivo = request.getParameter("tipoArchivo").trim();	
	    		session.setAttribute("nombreReporte",nombreReporte);
	    		session.setAttribute("nombreArchivo",nombreArchivo);
	    		session.setAttribute("tipoFormato",tipoFormato);
	    		session.setAttribute("tipoArchivo",tipoArchivo); 
				tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14A);
	    		if( tabla!=null ){
		    		descripcionFormato = tabla.getDescripcionTabla();
		    	}	
			}else if(FiseConstants.NOMBRE_FORMATO_14B.equals(formato)){
				nombreReporte = request.getParameter("nombreReporte").trim();
	    		nombreArchivo = request.getParameter("nombreArchivo").trim();
	    		tipoFormato = FiseConstants.TIPO_FORMATO_VAL;
	    		tipoArchivo = request.getParameter("tipoArchivo").trim();	
	    		session.setAttribute("nombreReporte",nombreReporte);
	    		session.setAttribute("nombreArchivo",nombreArchivo);
	    		session.setAttribute("tipoFormato",tipoFormato);
	    		session.setAttribute("tipoArchivo",tipoArchivo); 
				tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14B);
	    		if( tabla!=null ){
		    		descripcionFormato = tabla.getDescripcionTabla();
		    	}
			}else if(FiseConstants.NOMBRE_FORMATO_14C.equals(formato)){ 
				nombreReporte = request.getParameter("nombreReporte").trim();
	    		nombreArchivo = request.getParameter("nombreArchivo").trim();
	    		tipoFormato = FiseConstants.TIPO_FORMATO_VAL;
	    		tipoArchivo = request.getParameter("tipoArchivo").trim();	
	    		session.setAttribute("nombreReporte",nombreReporte);
	    		session.setAttribute("nombreArchivo",nombreArchivo);
	    		session.setAttribute("tipoFormato",tipoFormato);
	    		session.setAttribute("tipoArchivo",tipoArchivo); 
				tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14C);
	    		if( tabla!=null ){
		    		descripcionFormato = tabla.getDescripcionTabla();
		    	}		
			} 	   
		   	mapa.put(FiseConstants.PARAM_ANO_PRES_F14A, Long.parseLong(anioPres));
		   	mapa.put(FiseConstants.PARAM_DESC_MES_PRES_F14A, fiseUtil.getMapaMeses().get(Long.parseLong(mesPres)));
		   	mapa.put("USUARIO", themeDisplay.getUser().getLogin());
		   	mapa.put("NOMBRE_FORMATO", descripcionFormato);
		   	mapa.put("NRO_OBSERVACIONES", (listaObservaciones!=null && !listaObservaciones.isEmpty())?listaObservaciones.size():0);
			mapa.put("DESC_EMPRESA", mapaEmpresa.get(codEmpresa));
		   	mapa.put("ETAPA", etapa);
		   	
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
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@ResourceMapping("notificarValidacion")
  	public void notificarValidacion(ResourceRequest request,ResourceResponse response,
  			 @ModelAttribute("envioDefinitivoBean")EnvioDefinitivoBean n){
		
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
  			
  			
  			List<EnvioDefinitivoBean> lista = (List<EnvioDefinitivoBean>)pRequest.getPortletSession().getAttribute("listaNotificacion", 
		    		PortletSession.APPLICATION_SCOPE);
  			
  			if(lista!=null){  				
  				String mensaje = commonService.notificarValidacionMensual(codEmpresa,
  						etapa, Long.valueOf(idgrupoInf), optionFormato, themeDisplay.getUser().getLogin(),
  						themeDisplay.getUser().getLoginIP());			
  				logger.info("Valor del mensaje:  "+mensaje); 
  				
  				if(FiseConstants.ENVIO_EMAIL_OK_VALIDACION.equals(mensaje))
  				{ 
  					Map<String, Object> mapa = new HashMap<String, Object>();
  	  		    	mapa.put("IMG", session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg"));
  	  		    	mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
  	  		    	CfgTabla tabla = null;
  	  		    	String descripcionFormato = ""; 
  	  		    	String codEmpresaLista = "";
  	  				for(EnvioDefinitivoBean not :lista){
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
  	  						cargarListaObservaciones12A(formato12A.getFiseFormato12ADs());
  	  						nombreReporte ="validacion";
  	  						directorio =  "/reports/"+nombreReporte+".jasper";
  	  						tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12A);
  	  			    		if( tabla!=null ){
  	  				    		descripcionFormato = tabla.getDescripcionTabla();
  	  				    	}
  	  			    		mapa.put(FiseConstants.PARAM_ANO_PRES_F14A, Long.parseLong(not.getAnioPres()));
  	  					   	mapa.put(FiseConstants.PARAM_DESC_MES_PRES_F14A, fiseUtil.getMapaMeses().get(Long.parseLong(not.getMesPres())));
  	  					   	mapa.put("USUARIO", themeDisplay.getUser().getLogin());
  	  					   	mapa.put("NOMBRE_FORMATO", descripcionFormato);
  	  					   	mapa.put("NRO_OBSERVACIONES", (listaObservaciones!=null && !listaObservaciones.isEmpty())?listaObservaciones.size():0);
  	  						mapa.put("DESC_EMPRESA", mapaEmpresa.get(not.getCodEmpresa()));
  	  					   	mapa.put("ETAPA", not.getEtapa()); 			    		
  	  					    File reportFile12A = new File(session.getServletContext().getRealPath(directorio));
  	  	 		    	    byte[] bytes12A = null;
  	  	 		    	    bytes12A = JasperRunManager.runReportToPdf(reportFile12A.getPath(), mapa,
  	  	 			    		   new JRBeanCollectionDataSource(listaObservaciones));
  		  	 		    	if (bytes12A != null) {
  		   			    	   String nombre = FormatoUtil.nombreIndividualAnexoObs(not.getCodEmpresa(),
  		   			    			   Long.valueOf(not.getAnioPres()), Long.valueOf(not.getMesPres()), FiseConstants.NOMBRE_CONSOLIDADO_EMAIL);	   			    	 
  		   			    	   FileEntry archivo12A = fiseUtil.subirDocumentoBytes(request, bytes12A, "application/pdf", nombre);	   			    	   
  		   			    	   if( archivo12A!=null ){
  		   			    		   FileEntryJSP fileEntryJsp = new FileEntryJSP();
  		   			    		   fileEntryJsp.setNombreArchivo(nombre);
  		   			    		   fileEntryJsp.setFileEntry(archivo12A);
  		   			    		   listaArchivo.add(fileEntryJsp);
  		   			    	   }
  		   			       }  				    	
  	  					}else if(FiseConstants.NOMBRE_FORMATO_12B.equals(not.getFormato())){   						
  	  						//falta
  	  					}else if(FiseConstants.NOMBRE_FORMATO_12C.equals(not.getFormato())){  						
  	  						//falta
  	  					}else if(FiseConstants.NOMBRE_FORMATO_12D.equals(not.getFormato())){   					
  	  						//falta
  	  					}else if(FiseConstants.NOMBRE_FORMATO_13A.equals(not.getFormato())){  						
  	  						FiseFormato13ACPK pk = new FiseFormato13ACPK();
  	  						pk.setCodEmpresa(not.getCodEmpresa());
  	  						pk.setAnoPresentacion(new Long(not.getAnioPres()));
  	  						pk.setMesPresentacion(new Long(not.getMesPres()));
  	  						pk.setEtapa(not.getEtapa());  						
  	  						FiseFormato13AC formato13A = formatoService13A.obtenerFormato13ACByPK(pk);  						
  	  						cargarListaObservaciones13A(formato13A.getFiseFormato13ADs());
  	  						nombreReporte ="validacion13";
  	  						directorio =  "/reports/"+nombreReporte+".jasper";
  	  						if( tabla!=null ){
  	  				    		descripcionFormato = tabla.getDescripcionTabla();
  	  				    	}
  	  						mapa.put(FiseConstants.PARAM_ANO_PRES_F14A, Long.parseLong(not.getAnioPres()));
  	  					   	mapa.put(FiseConstants.PARAM_DESC_MES_PRES_F14A, fiseUtil.getMapaMeses().get(Long.parseLong(not.getMesPres())));
  	  					   	mapa.put("USUARIO", themeDisplay.getUser().getLogin());
  	  					   	mapa.put("NOMBRE_FORMATO", descripcionFormato);
  	  					   	mapa.put("NRO_OBSERVACIONES", (listaObservaciones!=null && !listaObservaciones.isEmpty())?listaObservaciones.size():0);
  	  						mapa.put("DESC_EMPRESA", mapaEmpresa.get(not.getCodEmpresa()));
  	  					   	mapa.put("ETAPA", not.getEtapa()); 	
  	  					    mapa.put("INICIO_VIGENCIA", not.getAnioIniVig()!=null?not.getAnioIniVig():"");
  							mapa.put("FIN_VIGENCIA", not.getAnioFinVig()!=null?not.getAnioFinVig():"");
  	  					    File reportFile13A = new File(session.getServletContext().getRealPath(directorio));
  	  	 		    	    byte[] bytes13A = null;
  	  	 		    	    bytes13A = JasperRunManager.runReportToPdf(reportFile13A.getPath(), mapa,
  	  	 			    		   new JRBeanCollectionDataSource(listaObservaciones));
  		  	 		    	if (bytes13A != null) {
  		   			    	   String nombre = FormatoUtil.nombreIndividualAnexoObs(not.getCodEmpresa(),
  		   			    			   Long.valueOf(not.getAnioPres()), Long.valueOf(not.getMesPres()), FiseConstants.NOMBRE_CONSOLIDADO_EMAIL);	   			    	 
  		   			    	   FileEntry archivo13A = fiseUtil.subirDocumentoBytes(request, bytes13A, "application/pdf", nombre);	   			    	   
  		   			    	   if( archivo13A!=null ){
  		   			    		   FileEntryJSP fileEntryJsp = new FileEntryJSP();
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
  	  						cargarListaObservaciones14A(formato14A.getFiseFormato14ADs());
  	  						nombreReporte ="validacion";
  	  						directorio =  "/reports/"+nombreReporte+".jasper";
  	  						tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14A);
  	  			    		if( tabla!=null ){
  	  				    		descripcionFormato = tabla.getDescripcionTabla();
  	  				    	}
  	  			    		mapa.put(FiseConstants.PARAM_ANO_PRES_F14A, Long.parseLong(not.getAnioPres()));
  	  					   	mapa.put(FiseConstants.PARAM_DESC_MES_PRES_F14A, fiseUtil.getMapaMeses().get(Long.parseLong(not.getMesPres())));
  	  					   	mapa.put("USUARIO", themeDisplay.getUser().getLogin());
  	  					   	mapa.put("NOMBRE_FORMATO", descripcionFormato);
  	  					   	mapa.put("NRO_OBSERVACIONES", (listaObservaciones!=null && !listaObservaciones.isEmpty())?listaObservaciones.size():0);
  	  						mapa.put("DESC_EMPRESA", mapaEmpresa.get(not.getCodEmpresa()));
  	  					   	mapa.put("ETAPA", not.getEtapa()); 					   
  	  					    File reportFile14A = new File(session.getServletContext().getRealPath(directorio));
  	  	 		    	    byte[] bytes14A = null;
  	  	 		    	    bytes14A = JasperRunManager.runReportToPdf(reportFile14A.getPath(), mapa,
  	  	 			    		   new JRBeanCollectionDataSource(listaObservaciones));
  		  	 		    	if (bytes14A != null) {
  		   			    	   String nombre = FormatoUtil.nombreIndividualAnexoObs(not.getCodEmpresa(),
  		   			    			   Long.valueOf(not.getAnioPres()), Long.valueOf(not.getMesPres()), FiseConstants.NOMBRE_CONSOLIDADO_EMAIL);	   			    	 
  		   			    	   FileEntry archivo14A = fiseUtil.subirDocumentoBytes(request, bytes14A, "application/pdf", nombre);	   			    	   
  		   			    	   if( archivo14A!=null ){
  		   			    		   FileEntryJSP fileEntryJsp = new FileEntryJSP();
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
  	  						cargarListaObservaciones14B(formato14B.getFiseFormato14BDs());
  	  						nombreReporte ="validacion";
  	  						directorio =  "/reports/"+nombreReporte+".jasper";
  	  						tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14B);
  	  			    		if( tabla!=null ){
  	  				    		descripcionFormato = tabla.getDescripcionTabla();
  	  				    	}
  	  			    		mapa.put(FiseConstants.PARAM_ANO_PRES_F14A, Long.parseLong(not.getAnioPres()));
  	  					   	mapa.put(FiseConstants.PARAM_DESC_MES_PRES_F14A, fiseUtil.getMapaMeses().get(Long.parseLong(not.getMesPres())));
  	  					   	mapa.put("USUARIO", themeDisplay.getUser().getLogin());
  	  					   	mapa.put("NOMBRE_FORMATO", descripcionFormato);
  	  					   	mapa.put("NRO_OBSERVACIONES", (listaObservaciones!=null && !listaObservaciones.isEmpty())?listaObservaciones.size():0);
  	  						mapa.put("DESC_EMPRESA", mapaEmpresa.get(not.getCodEmpresa()));
  	  					   	mapa.put("ETAPA", not.getEtapa()); 					   
  	  					    File reportFile14B = new File(session.getServletContext().getRealPath(directorio));
  	  	 		    	    byte[] bytes14B = null;
  	  	 		    	    bytes14B = JasperRunManager.runReportToPdf(reportFile14B.getPath(), mapa,
  	  	 			    		   new JRBeanCollectionDataSource(listaObservaciones));
  		  	 		    	if (bytes14B != null) {
  		   			    	   String nombre = FormatoUtil.nombreIndividualAnexoObs(not.getCodEmpresa(),
  		   			    			   Long.valueOf(not.getAnioPres()), Long.valueOf(not.getMesPres()), FiseConstants.NOMBRE_CONSOLIDADO_EMAIL);	   			    	 
  		   			    	   FileEntry archivo14B = fiseUtil.subirDocumentoBytes(request, bytes14B, "application/pdf", nombre);	   			    	   
  		   			    	   if( archivo14B!=null ){
  		   			    		   FileEntryJSP fileEntryJsp = new FileEntryJSP();
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
  	  						cargarListaObservaciones14C(formato14C.getListaDetalle14cDs());
  	  						nombreReporte ="validacion";
  	  						directorio =  "/reports/"+nombreReporte+".jasper";
  	  						tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14C);
  	  			    		if( tabla!=null ){
  	  				    		descripcionFormato = tabla.getDescripcionTabla();
  	  				    	}
  	  			    		mapa.put(FiseConstants.PARAM_ANO_PRES_F14A, Long.parseLong(not.getAnioPres()));
  	  					   	mapa.put(FiseConstants.PARAM_DESC_MES_PRES_F14A, fiseUtil.getMapaMeses().get(Long.parseLong(not.getMesPres())));
  	  					   	mapa.put("USUARIO", themeDisplay.getUser().getLogin());
  	  					   	mapa.put("NOMBRE_FORMATO", descripcionFormato);
  	  					   	mapa.put("NRO_OBSERVACIONES", (listaObservaciones!=null && !listaObservaciones.isEmpty())?listaObservaciones.size():0);
  	  						mapa.put("DESC_EMPRESA", mapaEmpresa.get(not.getCodEmpresa()));
  	  					   	mapa.put("ETAPA", not.getEtapa()); 					   
  	  					    File reportFile14C = new File(session.getServletContext().getRealPath(directorio));
  	  	 		    	    byte[] bytes14C = null;
  	  	 		    	    bytes14C = JasperRunManager.runReportToPdf(reportFile14C.getPath(), mapa,
  	  	 			    		   new JRBeanCollectionDataSource(listaObservaciones));
  		  	 		    	if (bytes14C != null) {
  		   			    	   String nombre = FormatoUtil.nombreIndividualAnexoObs(not.getCodEmpresa(),
  		   			    			   Long.valueOf(not.getAnioPres()), Long.valueOf(not.getMesPres()), FiseConstants.NOMBRE_CONSOLIDADO_EMAIL);	   			    	 
  		   			    	   FileEntry archivo14C = fiseUtil.subirDocumentoBytes(request, bytes14C, "application/pdf", nombre);	   			    	   
  		   			    	   if( archivo14C!=null ){
  		   			    		   FileEntryJSP fileEntryJsp = new FileEntryJSP();
  		   			    		   fileEntryJsp.setNombreArchivo(nombre);
  		   			    		   fileEntryJsp.setFileEntry(archivo14C);
  		   			    		   listaArchivo.add(fileEntryJsp);
  		   			    	   }
  		   			       }				    	
  	  					}	
  	  				}//fin del for de la lista 
  	  			    if(listaArchivo!=null && listaArchivo.size()>0 ){		    	  
  		    	      logger.info("Entrando a enviar email envio notificacion a:  "+
  	  			           themeDisplay.getUser().getEmailAddress());   		    	       
  		    	       respuestaEmail=fiseUtil.enviarMailsAdjuntoValidacion(
  		    	    		  request, 
  		    	    		  listaArchivo, 
  		    	    		  mapaEmpresa.get(codEmpresaLista), 
  		    	    		  n.getDescGrupoInf()!= null ? n.getDescGrupoInf():"--");
  		    	       logger.info("El envio de email fue correctamente al realizar notificacion."); 		    	       
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
  	  				jsonObj.put("Correo",msnId[1]);		
  				}else{
  					jsonObj.put("resultado", "EMAIL");	
  	  				jsonObj.put("Correo",msnId[1]);		
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

}
