package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.ResumenCostoActividadBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.FiseGrupoInformacion;
import gob.osinergmin.fise.gart.service.FiseGrupoInformacionGartService;
import gob.osinergmin.fise.gart.service.ResumenCostosService;
import gob.osinergmin.fise.util.FormatoUtil;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperRunManager;

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

import com.liferay.portal.util.PortalUtil;


@Controller("resumenCostosActividadController")
@RequestMapping("VIEW")
public class ResumenCostosActividadController {
	
	Logger logger = Logger.getLogger(ResumenCostosActividadController.class);
	
	
	@Autowired
	@Qualifier("fiseUtil")
	private FiseUtil fiseUtil;	
	
	@Autowired
	@Qualifier("fiseGrupoInformacionGartServiceImpl")
	private FiseGrupoInformacionGartService fiseGrupoInformacionService;
	
	
	@Autowired
	@Qualifier("resumenCostosServiceImpl")
	private ResumenCostosService resumenCostosService;
	
	
	
	@ModelAttribute("resumenCostoActividadBean")
    public ResumenCostoActividadBean resumenCostoActividadBean() {
		ResumenCostoActividadBean comman  = new ResumenCostoActividadBean();
        return comman;	        
    }

	
	@RequestMapping
	public String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse,
			     @ModelAttribute("resumenCostoActividadBean")ResumenCostoActividadBean r){
        try {           	
    		if(r.getListaEmpresas()==null){
    			logger.info("Lista de empresa es null:  " +renderRequest); 
    			r.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));
    		}     		
    		r.setAdmin(fiseUtil.esAdministrador(renderRequest));
    		//TODOS = que filtre grupo de informacion activos e inactivos
    		r.setListaGrupoInf(fiseGrupoInformacionService.listarGrupoInformacion(FiseConstants.MENSUAL,"TODOS")); 
    		
    		//mapaEmpresa = fiseUtil.getMapaEmpresa();   		
    		
    		model.addAttribute("model", r);
    		
		} catch (Exception e) {
			logger.info("Ocurrio un errror al caragar la pagina de resumen de costos actividad"); 
			e.printStackTrace();
		}		
		return "resumenCostosActividad";
	}	
	
	
	@ResourceMapping("cargarGrupoInformacion")
  	public void cargaGrupoInformacion(ModelMap model, ResourceRequest request,ResourceResponse response,
  			 @ModelAttribute("resumenCostoActividadBean")ResumenCostoActividadBean r){
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
	
	@ResourceMapping("verResumenCostoActividadF14AB")
	public void verResumenCostosActividadF14AB(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("resumenCostoActividadBean")ResumenCostoActividadBean r) {		
		Map<String, Object> mapa = null;
		byte[] bytesF14AB = null;
		try {	
			
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();	
			
			logger.info("Entrando a ver reporte de resumen costos F14A");
			
			long idGrupoInf = 0;
			if(FormatoUtil.isNotBlank(r.getGrupoInfBusq())){
				idGrupoInf = Long.valueOf(r.getGrupoInfBusq());
			}
			logger.info("codEmpresa:  "+r.getCodEmpresaBusq());
			logger.info("grupo inf:  "+idGrupoInf);
			logger.info("periocidad:  "+r.getOptionFormato());
			
		    String rutaImg = session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg");	
		    
		    JSONObject jsonObj = new JSONObject();	   
		    
		    mapa = resumenCostosService.buscarResumenCostoActividadF14AB(r.getCodEmpresaBusq(), idGrupoInf);
		    
		    if(mapa!=null && mapa.size()>0){
		    	
		    mapa.put("IMG", rutaImg);
			mapa.put(JRParameter.REPORT_LOCALE, Locale.US);	    
		    
		    String tipoFormato = "RESUMEN DE COSTOS POR ACTIVIDAD F14AB ";
		    String tipoArchivo = "3";//PDF		   
		    session.setAttribute("tipoFormato",tipoFormato);
		    session.setAttribute("tipoArchivo",tipoArchivo);	    
		    
		    String nombreReporte = "costosEstandarActividad";   		    	
		    String directorio = "/reports/" + nombreReporte + ".jasper"; 
		   
		    File reportFile = new File(session.getServletContext().getRealPath(directorio));
		    bytesF14AB = JasperRunManager.runReportToPdf(reportFile.getPath(), mapa, 
		    		new JREmptyDataSource());		    	
			    if (bytesF14AB != null) {				  	  		    		
			    	session.setAttribute("bytesFormato", bytesF14AB);
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
			logger.error("Error al ver resumen de costos actividad F14AB: "+e); 
			e.printStackTrace();
		}finally{
			if(mapa!=null){
				mapa =null;
			}
			if(bytesF14AB!=null){
				bytesF14AB=null;
			}
		}
    }
	
	
	@ResourceMapping("verResumenCostoActividadF14ABExcel")
	public void verResumenCostosActividadF14ABExel(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("resumenCostoActividadBean")ResumenCostoActividadBean r) {		
		Map<String, Object> mapa = null;			
		try {	
			
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();	
			
			logger.info("Entrando a ver reporte de resumen costos F14A excel");
			
			long idGrupoInf = 0;
			if(FormatoUtil.isNotBlank(r.getGrupoInfBusq())){
				idGrupoInf = Long.valueOf(r.getGrupoInfBusq());
			}
			logger.info("codEmpresa:  "+r.getCodEmpresaBusq());
			logger.info("grupo inf:  "+idGrupoInf);
			logger.info("periocidad:  "+r.getOptionFormato());
			
			 JSONObject jsonObj = new JSONObject();	   
			 
		    String rutaImg = session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg");
		    
		    mapa = resumenCostosService.buscarResumenCostoActividadF14AB(r.getCodEmpresaBusq(), idGrupoInf);		    
		    
		    if(mapa!=null && mapa.size()>0){
		    	
		    mapa.put("IMG", rutaImg);
			mapa.put(JRParameter.REPORT_LOCALE, Locale.US); 
		   
		    
		    String tipoFormato = FiseConstants.TIPO_FORMATO_RESUMEN_COSTOS;
		    String tipoArchivo = "5";//exel solo con parametros y 		
		    String nombreReporte = "costosEstandarActividad"; //nombre del jasper
		    String nombreArchivo ="RESUMEN_COSTO_ACTIVIDAD_F14AB";       
		    
		    session.setAttribute("tipoFormato",tipoFormato);
		    session.setAttribute("tipoArchivo",tipoArchivo);
		    session.setAttribute("nombreReporte",nombreReporte);
		    session.setAttribute("nombreArchivo",nombreArchivo);
		    session.setAttribute("lista", null);
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
			logger.error("Error al ver  resumen de costos Actividad F14AB exel: "+e); 
			e.printStackTrace();
		}finally{
			if(mapa!=null){
				mapa =null;
			}		
		}
    }
	
	
	
}
