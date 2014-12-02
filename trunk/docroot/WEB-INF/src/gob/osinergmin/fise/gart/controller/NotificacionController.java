package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.Formato12C12D13Generic;
import gob.osinergmin.fise.bean.Formato14CBean;
import gob.osinergmin.fise.bean.Formato14Generic;
import gob.osinergmin.fise.bean.MensajeErrorBean;
import gob.osinergmin.fise.bean.NotificacionBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
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
import gob.osinergmin.fise.gart.service.CommonGartService;
import gob.osinergmin.fise.gart.service.FiseGrupoInformacionGartService;
import gob.osinergmin.fise.gart.service.Formato12AGartService;
import gob.osinergmin.fise.gart.service.Formato13AGartService;
import gob.osinergmin.fise.gart.service.Formato14AGartService;
import gob.osinergmin.fise.gart.service.Formato14BGartService;
import gob.osinergmin.fise.gart.service.Formato14CGartService;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
	
	
	
	private Map<String, String> mapaEmpresa;	
	private List<MensajeErrorBean> listaObservaciones;
	private Map<String, String> mapaErrores;
	private Map<String, String> mapaSectorTipico;
	
	
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
    		
    		n.setListaGrupoInf(fiseGrupoInformacionService.listarGrupoInformacion(FiseConstants.MENSUAL)); 
    		
    		mapaEmpresa = fiseUtil.getMapaEmpresa();
    		
    		mapaErrores = fiseUtil.getMapaErrores();
    		
    		mapaSectorTipico = fiseUtil.getMapaSectorTipico();
    		
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
			
			String codEmpresa = n.getCodEmpresaBusq();				
			String optionFormato = n.getOptionFormato();
			String idgrupoInf = n.getGrupoInfBusq();
			String etapa = n.getEtapaBusq();
		    
			String data ="";			
			logger.info("codigo empresa "+ codEmpresa);  			
  			logger.info("id Grupo inf "+ idgrupoInf);  	 			
  			logger.info("Option formato "+ optionFormato);
  			logger.info("etapa "+ etapa);
  			
  			
  			List<NotificacionBean> lista =commonService.buscarNotificacion(codEmpresa, 
  					optionFormato, etapa,new Long(idgrupoInf));
  			
  			logger.info("tamaño de la lista notificacion   :"+lista.size());
  			
  			List<NotificacionBean> listaReenvio = new ArrayList<NotificacionBean>();
  			
  			for(NotificacionBean not : lista){  				
  				not.setDesEmpresa(mapaEmpresa.get(not.getCodEmpresa()));
  				not.setDesMes(fiseUtil.getMapaMeses().get(Long.valueOf(not.getMesPres())));
  				if(!"00".equals(not.getMesEjec())){ 
  					not.setDesMesEje(fiseUtil.getMapaMeses().get(Long.valueOf(not.getMesEjec())));   		
  				}else{
  					not.setDesMesEje("---");
  				}
  				listaReenvio.add(not);
  			} 			
  			data = toStringListJSON(listaReenvio);
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
  			 @ModelAttribute("notificacionBean")NotificacionBean n){
		
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
  			
  			
  			List<NotificacionBean> lista =commonService.buscarNotificacion(codEmpresa, 
  					optionFormato, etapa, new Long(idgrupoInf));
  			
  			logger.info("tamaño de la lista notificacion   :"+lista.size());
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
  	  		        		themeDisplay.getUser().getLogin(), themeDisplay.getUser().getLogin());
  	  		        if(i!=0){
  	  		          valor = false;
  	  		          break;
  	  		        } 	  		      
  				}else if(FiseConstants.NOMBRE_FORMATO_12B.equals(not.getFormato())){ 
  					
  				}else if(FiseConstants.NOMBRE_FORMATO_12C.equals(not.getFormato())){ 
  					
  				}else if(FiseConstants.NOMBRE_FORMATO_12D.equals(not.getFormato())){ 
  					
  				}else if(FiseConstants.NOMBRE_FORMATO_13A.equals(not.getFormato())){ 
  					FiseFormato13ACPK pk = new FiseFormato13ACPK();
  					pk.setCodEmpresa(not.getCodEmpresa());
  	  				pk.setAnoPresentacion(new Long(not.getAnioPres()));
  	  		        pk.setMesPresentacion(new Long(not.getMesPres()));
  	  		        pk.setEtapa(not.getEtapa());  	 
  	  		        FiseFormato13AC formato13A = formatoService13A.obtenerFormato13ACByPK(pk);	
  	  		        Formato12C12D13Generic formato13Generic = new Formato12C12D13Generic(formato13A);
  	  		       int i = commonService.validarFormatos_12C12D13A(formato13Generic, FiseConstants.NOMBRE_FORMATO_13A,
  	  		    		   themeDisplay.getUser().getLogin(), themeDisplay.getUser().getLogin());
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
  		    			   themeDisplay.getUser().getLogin(), themeDisplay.getUser().getLogin());
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
  		    	    		themeDisplay.getUser().getLogin(), themeDisplay.getUser().getLogin());
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
  			    			themeDisplay.getUser().getLogin(), themeDisplay.getUser().getLogin());
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
  			 @ModelAttribute("notificacionBean")NotificacionBean n){
		
		HttpServletRequest req = PortalUtil.getHttpServletRequest(request);
		HttpSession session = req.getSession();
		
		try{
			response.setContentType("application/json");
			
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

			}else if(FiseConstants.NOMBRE_FORMATO_12C.equals(n.getFormato())){ 

			}else if(FiseConstants.NOMBRE_FORMATO_12D.equals(n.getFormato())){ 

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
	
	

}
