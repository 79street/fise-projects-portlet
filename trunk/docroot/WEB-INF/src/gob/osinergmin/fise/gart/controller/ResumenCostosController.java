package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.ResumenCostoBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.FiseGrupoInformacion;
import gob.osinergmin.fise.gart.service.FiseGrupoInformacionGartService;

import java.io.File;
import java.io.PrintWriter;
import java.math.BigDecimal;
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

import com.liferay.portal.util.PortalUtil;


@Controller("resumenCostosController")
@RequestMapping("VIEW")
public class ResumenCostosController {
	
	Logger logger = Logger.getLogger(ResumenCostosController.class);
	
	
	@Autowired
	@Qualifier("fiseUtil")
	private FiseUtil fiseUtil;	
	
	@Autowired
	@Qualifier("fiseGrupoInformacionGartServiceImpl")
	private FiseGrupoInformacionGartService fiseGrupoInformacionService;
	
	
	//private Map<String, String> mapaEmpresa;	
	
	
	@ModelAttribute("resumenCostoBean")
    public ResumenCostoBean resumenCostoBean() {
		ResumenCostoBean comman  = new ResumenCostoBean();
        return comman;	        
    }

	
	@RequestMapping
	public String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse,
			     @ModelAttribute("resumenCostoBean")ResumenCostoBean r){
        try {           	
    		if(r.getListaEmpresas()==null){
    			logger.info("Lista de empresa es null:  " +renderRequest); 
    			r.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));
    		}     		
    		r.setAdmin(fiseUtil.esAdministrador(renderRequest));
    		
    		r.setListaGrupoInf(fiseGrupoInformacionService.listarGrupoInformacion(FiseConstants.MENSUAL)); 
    		
    		//mapaEmpresa = fiseUtil.getMapaEmpresa();   		
    		
    		model.addAttribute("model", r);
    		
		} catch (Exception e) {
			logger.info("Ocurrio un errror al caragar la pagina de resumen de costos"); 
			e.printStackTrace();
		}		
		return "resumenCostos";
	}	
	
	
	@ResourceMapping("cargarGrupoInformacion")
  	public void cargaGrupoInformacion(ModelMap model, ResourceRequest request,ResourceResponse response,
  			 @ModelAttribute("resumenCostoBean")ResumenCostoBean r){
		try {			
  			response.setContentType("applicacion/json");
  			String tipoFormato = r.getOptionFormato();
  			
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
	
	@ResourceMapping("verResumenCostoF14A")
	public void verResumenF14A(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("resumenCostoBean")ResumenCostoBean r) {		
		List<ResumenCostoBean> listaF14A =null;
		byte[] bytesF14A = null;
		try {	
			
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();	
			
			logger.info("Entrando a ver reporte de resumen costos F14A");
			
			logger.info("codEmpresa:  "+r.getCodEmpresaBusq());
			logger.info("grupo inf:  "+r.getGrupoInfBusq());
			logger.info("periocidad:  "+r.getOptionFormato());
			
		    String rutaImg = session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg");
		    
		    Map<String, Object> mapa = new  HashMap<String, Object>();
		    mapa.put("IMG", rutaImg);
			mapa.put(JRParameter.REPORT_LOCALE, Locale.US);			  
		   
		    JSONObject jsonObj = new JSONObject();	   
		    
		    String tipoFormato = "RESUMEN DE COSTOS F14A ";
		    String tipoArchivo = "3";//PDF		   
		    session.setAttribute("tipoFormato",tipoFormato);
		    session.setAttribute("tipoArchivo",tipoArchivo);	    
		    
		    String nombreReporte = "reporte14A";   		    	
		    String directorio = "/reports/" + nombreReporte + ".jasper";
		    
		    listaF14A = new ArrayList<ResumenCostoBean>();	
		    ResumenCostoBean bean = new ResumenCostoBean();
		    
		    bean.setDesEmpresa("LUZ DEL SUR");
		    bean.setPeriodo("ENERO - 2015");
		    bean.setEmpAprobadoR(new BigDecimal(12.50)); 
		    bean.setEmpAprobadoP(new BigDecimal(11.63)); 		    
		    bean.setEmpAprobadoL(new BigDecimal(13.55));  
		    bean.setEmpSolicitadoR(new BigDecimal(14.52)); 
		    bean.setEmpSolicitadoP(new BigDecimal(19.35)); 
		    bean.setEmpSolicitadoL(new BigDecimal(18.25)); 
		    bean.setGlpAprobadoR(new BigDecimal(12.50)); 
		    bean.setGlpAprobadoP(new BigDecimal(11.63)); 		    
		    bean.setGlpAprobadoL(new BigDecimal(13.55));  
		    bean.setGlpSolicitadoR(new BigDecimal(14.52)); 
		    bean.setGlpSolicitadoP(new BigDecimal(19.35)); 
		    bean.setGlpSolicitadoL(new BigDecimal(18.25)); 
		    listaF14A.add(bean);
		    listaF14A.add(bean);
		    
		    if(listaF14A!=null && listaF14A.size()>0){
		    	File reportFile = new File(session.getServletContext().getRealPath(directorio));
		    	bytesF14A = JasperRunManager.runReportToPdf(reportFile.getPath(), mapa, 
		    			new JRBeanCollectionDataSource(listaF14A));
		    	if (bytesF14A != null) {				  	  		    		
		    		session.setAttribute("bytesFormato", bytesF14A);
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
			logger.error("Error al ver  resumen de costos F14A: "+e); 
			e.printStackTrace();
		}finally{
			if(listaF14A!=null){
				listaF14A =null;
			}
			if(bytesF14A!=null){
				bytesF14A=null;
			}
		}
    }
	
	
	@ResourceMapping("verResumenCostoF12A")
	public void verResumenF12A(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("resumenCostoBean")ResumenCostoBean r) {		
		List<ResumenCostoBean> listaF12A =null;
		byte[] bytesF12A = null;
		try {	
			
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();
			
			logger.info("Entrando a ver reporte de resumen de costos de F12A"); 	
			logger.info("codEmpresa:  "+r.getCodEmpresaBusq());
			logger.info("grupo inf:  "+r.getGrupoInfBusq());
			logger.info("periocidad:  "+r.getOptionFormato());		    
		   		    
            String rutaImg = session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg");
		    
		    Map<String, Object> mapa = new  HashMap<String, Object>();
		    mapa.put("IMG", rutaImg);
		    mapa.put("TIPO_FORMATO", FiseConstants.NOMBRE_F12A);
			mapa.put(JRParameter.REPORT_LOCALE, Locale.US);			  
		   
		    JSONObject jsonObj = new JSONObject();	   
		    
		    String tipoFormato = "RESUMEN DE COSTOS F12A ";
		    String tipoArchivo = "3";//PDF		   
		    session.setAttribute("tipoFormato",tipoFormato);
		    session.setAttribute("tipoArchivo",tipoArchivo);	    
		    
		    String nombreReporte = "reporte12A_12B";   		    	
		    String directorio = "/reports/" + nombreReporte + ".jasper";
		    
		    listaF12A = new ArrayList<ResumenCostoBean>();	
		    ResumenCostoBean bean = new ResumenCostoBean();
		    
		    bean.setDesEmpresa("LUZ DEL SUR");
		    bean.setPeriodo("ENERO - 2015");
		    bean.setSolicitadoR(new BigDecimal(12.50)); 
		    bean.setSolicitadoP(new BigDecimal(11.63)); 		    
		    bean.setSolicitadoL(new BigDecimal(13.55));  
		    bean.setAprobadoR(new BigDecimal(14.52)); 
		    bean.setAprobadoP(new BigDecimal(19.35)); 
		    bean.setAprobadoL(new BigDecimal(18.25));		   
		    listaF12A.add(bean);	    
		    
		    if(listaF12A!=null && listaF12A.size()>0){
		    	File reportFile = new File(session.getServletContext().getRealPath(directorio));
		    	bytesF12A = JasperRunManager.runReportToPdf(reportFile.getPath(), mapa, 
		    			new JRBeanCollectionDataSource(listaF12A));
		    	if (bytesF12A != null) {				  	  		    		
		    		session.setAttribute("bytesFormato", bytesF12A);
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
			logger.error("Error al ver reporte de reseumen de costo de F12A: "+e); 
			e.printStackTrace();
		}finally{	
			if(listaF12A!=null){
				listaF12A =null;
			}
			if(bytesF12A!=null){
				bytesF12A=null;
			}
		}
    }
	
	@ResourceMapping("verResumenCostoF12B")
	public void verResumenF12B(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("resumenCostoBean")ResumenCostoBean r) {		
		List<ResumenCostoBean> listaF12B =null;
		byte[] bytesF12B = null;
		try {	
			
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();		    
		    
			logger.info("Entrando a ver reporte de resumen de costos de F12B"); 	
			logger.info("codEmpresa:  "+r.getCodEmpresaBusq());
			logger.info("grupo inf:  "+r.getGrupoInfBusq());
			logger.info("periocidad:  "+r.getOptionFormato());		    
		   		    
            String rutaImg = session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg");
		    
		    Map<String, Object> mapa = new  HashMap<String, Object>();
		    mapa.put("IMG", rutaImg);
		    mapa.put("TIPO_FORMATO", FiseConstants.NOMBRE_F12B);
			mapa.put(JRParameter.REPORT_LOCALE, Locale.US);			  
		   
		    JSONObject jsonObj = new JSONObject();	   
		    
		    String tipoFormato = "RESUMEN DE COSTOS F12A ";
		    String tipoArchivo = "3";//PDF		   
		    session.setAttribute("tipoFormato",tipoFormato);
		    session.setAttribute("tipoArchivo",tipoArchivo);	    
		    
		    String nombreReporte = "reporte12A_12B";   		    	
		    String directorio = "/reports/" + nombreReporte + ".jasper";
		    
		    listaF12B = new ArrayList<ResumenCostoBean>();	
		    ResumenCostoBean bean = new ResumenCostoBean();
		    
		    bean.setDesEmpresa("LUZ DEL SUR");
		    bean.setPeriodo("ENERO - 2015");
		    bean.setSolicitadoR(new BigDecimal(12.50)); 
		    bean.setSolicitadoP(new BigDecimal(11.63)); 		    
		    bean.setSolicitadoL(new BigDecimal(13.55));  
		    bean.setAprobadoR(new BigDecimal(14.52)); 
		    bean.setAprobadoP(new BigDecimal(19.35)); 
		    bean.setAprobadoL(new BigDecimal(18.25));		   
		    listaF12B.add(bean);	    
		    
		    if(listaF12B!=null && listaF12B.size()>0){
		    	File reportFile = new File(session.getServletContext().getRealPath(directorio));
		    	bytesF12B = JasperRunManager.runReportToPdf(reportFile.getPath(), mapa, 
		    			new JRBeanCollectionDataSource(listaF12B));
		    	if (bytesF12B != null) {				  	  		    		
		    		session.setAttribute("bytesFormato", bytesF12B);
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
			logger.error("Error al ver  formatos: "+e); 
			e.printStackTrace();
		}finally{
			if(listaF12B!=null){
				listaF12B =null;
			}
			if(bytesF12B!=null){
				bytesF12B=null;
			}
		}
    }
	
}
