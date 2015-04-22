package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.ResumenObsBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.CfgTabla;
import gob.osinergmin.fise.domain.FiseFormato12AC;
import gob.osinergmin.fise.domain.FiseFormato12AD;
import gob.osinergmin.fise.domain.FiseFormato12ADOb;
import gob.osinergmin.fise.domain.FiseGrupoInformacion;
import gob.osinergmin.fise.gart.service.CfgTablaGartService;
import gob.osinergmin.fise.gart.service.FiseGrupoInformacionGartService;
import gob.osinergmin.fise.gart.service.Formato12AGartService;
import gob.osinergmin.fise.util.FormatoUtil;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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


@Controller("resumenObsController")
@RequestMapping("VIEW")
public class ResumenObsController {
	
	Logger logger = Logger.getLogger(ResumenObsController.class);
	
	
	@Autowired
	@Qualifier("fiseUtil")
	private FiseUtil fiseUtil;	
	
	@Autowired
	@Qualifier("fiseGrupoInformacionGartServiceImpl")
	private FiseGrupoInformacionGartService fiseGrupoInformacionService;
	
	@Autowired
	@Qualifier("cfgTablaGartServiceImpl")
	private CfgTablaGartService tablaService;
	
	
	@Autowired
	@Qualifier("formato12AGartServiceImpl")
	private Formato12AGartService formatoService12A;
	
	
	
	private Map<String, String> mapaEmpresa;	
	/*private Map<String, String> mapaSectorTipico;
	private Map<Long, String> mapaEtapaEjecucion;	
	private Map<String, String> mapaEtapa;*/
	
	
	@ModelAttribute("resumenObsBean")
    public ResumenObsBean resumenObsBean() {
		ResumenObsBean comman  = new ResumenObsBean();
        return comman;	        
    }

	
	@RequestMapping
	public String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse,
			     @ModelAttribute("resumenObsBean")ResumenObsBean r){
        try {           	
    		if(r.getListaEmpresas()==null){
    			logger.info("Lista de empresa es null:  " +renderRequest); 
    			r.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));
    		}     		
    		r.setAdmin(fiseUtil.esAdministrador(renderRequest));
    		//TODOS = que filtre grupo de informacion activos e inactivos
    		r.setListaGrupoInf(fiseGrupoInformacionService.listarGrupoInformacion(FiseConstants.MENSUAL,"TODOS")); 
    		
    		mapaEmpresa = fiseUtil.getMapaEmpresa();   
         /*   mapaSectorTipico = fiseUtil.getMapaSectorTipico();    		
    		mapaEtapaEjecucion = fiseUtil.getMapaEtapaEjecucion();    		
    		mapaEtapa = fiseUtil.getMapaEtapa();*/
    		
    		model.addAttribute("model", r);
    		
		} catch (Exception e) {
			logger.info("Ocurrio un errror al caragar la pagina de resumen de Observaciones"); 
			e.printStackTrace();
		}		
		return "resumenObservaciones";
	}	
	
	
	@ResourceMapping("cargarGrupoInformacion")
  	public void cargaGrupoInformacion(ModelMap model, ResourceRequest request,ResourceResponse response,
  			 @ModelAttribute("resumenObsBean")ResumenObsBean r){
		try {			
  			response.setContentType("applicacion/json");
  			String tipoFormato = r.getOptionFormato();
  			
  			logger.info("Codigo grupo inf. para cargar grupo de infor.:  "+tipoFormato);
  			//TODOS = que filtre activos e inactivos
  			List<FiseGrupoInformacion> listaGrupoInf = fiseGrupoInformacionService.listarGrupoInformacion(tipoFormato,"TODOS");
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
	
	
	
	
	@ResourceMapping("verResumenObsMensualesPdf")
	public void verResumenObsMensualesPdf(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("resumenObsBean")ResumenObsBean r) {
		
		List<ResumenObsBean> listaF12 =new ArrayList<ResumenObsBean>();
		
		byte[] bytesF12 = null;
		
		try {	
			
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();	
			
			logger.info("Entrando a ver reporte de resumen obs mensuales PDF");
			
			long idGrupoInf = 0;
			if(FormatoUtil.isNotBlank(r.getGrupoInfBusq())){
				idGrupoInf = Long.valueOf(r.getGrupoInfBusq());
			}
			logger.info("codEmpresa:  "+r.getCodEmpresaBusq());
			logger.info("grupo inf:  "+idGrupoInf);
			logger.info("desc grupo inf:  "+r.getDesGrupoInf());			
			logger.info("etapa:  "+r.getEtapaMBusq());
			logger.info("grupo inf:  "+idGrupoInf);
			logger.info("formato mensual:  "+r.getOptionMensual());
			if(FormatoUtil.isNotBlank(r.getCodEmpresaBusq()) && "TODOS".equals(r.getCodEmpresaBusq())){ 
			   r.setCodEmpresaBusq("");	
			}			
		    String rutaImg = session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg");
		    ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);	
		    JSONObject jsonObj = new JSONObject();	 
		    Map<String, Object> mapa = new  HashMap<String, Object>();
		    mapa.put("IMG", rutaImg);		   
			mapa.put(JRParameter.REPORT_LOCALE, Locale.US);			  
			String usuario = themeDisplay.getUser().getLogin();
			String desEmpresa = "";
			String desMes = "";
			String anioPres = "";
			String etapa = "";
			String nombreFormato= "";
			String codEmpresa = "";
			
		    if("F12A".equals(r.getOptionMensual())){ 	    	
		    	List<FiseFormato12AC> lista = formatoService12A.buscarFormato12AReporteObs(r.getCodEmpresaBusq(), 
		    			idGrupoInf, r.getEtapaMBusq());	
		    	logger.info("Tamanio de la lista f12A cabecera:   "+lista.size()); 
		    	
		    	for(FiseFormato12AC f: lista){
		    		codEmpresa = f.getId().getCodEmpresa();
		    		desEmpresa = mapaEmpresa.get(f.getId().getCodEmpresa());
		    		desMes = fiseUtil.getMapaMeses().get(f.getId().getMesPresentacion());
		    		anioPres = ""+f.getId().getAnoPresentacion();
		    		etapa = f.getId().getEtapa();
		    		CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12A);		        	
		        	if( tabla!=null ){
		        		nombreFormato = tabla.getDescripcionTabla();
		        	}     		
		    		
		    		for (FiseFormato12AD detalle : f.getFiseFormato12ADs()) {
		    			detalle.setFiseFormato12ADObs(formatoService12A.listarFormato12ADObByFormato12AD(detalle));
		    			List<FiseFormato12ADOb> listaObser = formatoService12A.listarFormato12ADObByFormato12AD(detalle);
		    			for (FiseFormato12ADOb observacion : listaObser) {		    				
		    				ResumenObsBean obs = new ResumenObsBean();	
		    				obs.setDesEmpresa(desEmpresa); 
		    				obs.setCodEmpresa(codEmpresa);
		    				logger.info("Empresa:  "+obs.getDesEmpresa());
		    	        	obs.setPeriodo(anioPres+"/"+desMes); 
		    	        	logger.info("Periodo:  "+obs.getPeriodo());
		    				obs.setFormato(nombreFormato);
		    				logger.info("Formato:  "+obs.getFormato());
		    				obs.setEtapa(etapa);	
		    				obs.setUsuario(usuario); 
		    	        	obs.setDescZonaBenef(fiseUtil.getMapaZonaBenef().get(observacion.getId().getIdZonaBenef()));
		    				obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
		    				obs.setDescripcion(observacion.getFiseObservacion().getDescripcion());		    			
		    				listaF12.add(obs);
		    			}
		    		}		    		
		    	}//fin del for lista cabecera    	
		    }   
		    
		    
		    
		    String tipoFormato = "RESUMEN DE OBSERVACIONES MENSUALES ";
		    String tipoArchivo = "3";//PDF		   
		    session.setAttribute("tipoFormato",tipoFormato);
		    session.setAttribute("tipoArchivo",tipoArchivo);	    
		    
		    String nombreReporte = "validacionReporte";   		    	
		    String directorio = "/reports/" + nombreReporte + ".jasper";
		    
		   // listaF14A = resumenCostosService.buscarResumenCostoF14A(r.getCodEmpresaBusq(), idGrupoInf);	    
		    
		    if(listaF12!=null && listaF12.size()>0){
		    	File reportFile = new File(session.getServletContext().getRealPath(directorio));
		    	bytesF12 = JasperRunManager.runReportToPdf(reportFile.getPath(), mapa, 
		    			new JRBeanCollectionDataSource(listaF12));
		    	if (bytesF12 != null) {				  	  		    		
		    		session.setAttribute("bytesFormato", bytesF12);
		    		jsonObj.put("resultado", "OK");	   	
		    	}else{
		    		jsonObj.put("resultado", "ERROR");	   
		    	}
		    }else{
		    	jsonObj.put("resultado", "VACIO");	   
		    }		
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();		  
			logger.info(jsonObj.toString());
			pw.write(jsonObj.toString());
			pw.flush();
			pw.close();	    
		 } catch (Exception e) {
			logger.error("Error al ver  resumen de obs. mensuales pdf: "+e); 
			e.printStackTrace();
		}finally{
			if(listaF12!=null){
				listaF12 =null;
			}
			if(bytesF12!=null){
				bytesF12=null;
			}
		}
    }
	
	
	@ResourceMapping("verResumenObsMensualesExcel")
	public void verResumenObsMensualesExcel(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("resumenObsBean")ResumenObsBean r) {		
		
		List<ResumenObsBean> listaF14A =new ArrayList<ResumenObsBean>();	
		
		try {	
			
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();	
			
			logger.info("Entrando a ver reporte de resumen obs. mensuales excel");
			
			long idGrupoInf = 0;
			if(FormatoUtil.isNotBlank(r.getGrupoInfBusq())){
				idGrupoInf = Long.valueOf(r.getGrupoInfBusq());
			}
			logger.info("codEmpresa:  "+r.getCodEmpresaBusq());
			logger.info("grupo inf:  "+idGrupoInf);
			logger.info("desc grupo inf:  "+r.getDesGrupoInf());			
			logger.info("etapa:  "+r.getEtapaMBusq());
			logger.info("grupo inf:  "+idGrupoInf);
			logger.info("formato mensual:  "+r.getOptionMensual());
			
		    String rutaImg = session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg");
		    
		    Map<String, Object> mapa = new  HashMap<String, Object>();
		    mapa.put("IMG", rutaImg);
		    mapa.put("GRUPO_INFORMACION", r.getDesGrupoInf());
			mapa.put(JRParameter.REPORT_LOCALE, Locale.US);			  
		   
		    JSONObject jsonObj = new JSONObject();	   
		    
		    String tipoFormato = FiseConstants.TIPO_FORMATO_RESUMEN_COSTOS;
		    String tipoArchivo = "1";//exel		
		    String nombreReporte = "resumenCostos14A_Excel";
		    String nombreArchivo ="RESUMEN_COSTO_F14A";     
		    
		    //listaF14A = resumenCostosService.buscarResumenCostoF14A(r.getCodEmpresaBusq(), idGrupoInf);	    
		    
		    if(listaF14A!=null && listaF14A.size()>0){
		    	session.setAttribute("tipoFormato",tipoFormato);
		    	session.setAttribute("tipoArchivo",tipoArchivo);
		    	session.setAttribute("nombreReporte",nombreReporte);
		    	session.setAttribute("nombreArchivo",nombreArchivo);
		    	session.setAttribute("lista", listaF14A);
		    	session.setAttribute("mapa", mapa);
		    	jsonObj.put("resultado", "OK");	 
		    }else{
		    	jsonObj.put("resultado", "VACIO");	   
		    }		
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();		  
			logger.info(jsonObj.toString());
			pw.write(jsonObj.toString());
			pw.flush();
			pw.close();	    
		 } catch (Exception e) {
			logger.error("Error al ver  resumen de obs. mensuales exel: "+e); 
			e.printStackTrace();
		}finally{
			if(listaF14A!=null){
				listaF14A =null;
			}		
		}
    }	
	
	
	
}
