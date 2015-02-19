package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.ResumenCostoBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.FiseGrupoInformacion;
import gob.osinergmin.fise.gart.service.FiseGrupoInformacionGartService;
import gob.osinergmin.fise.gart.service.ResumenCostosService;
import gob.osinergmin.fise.util.FormatoUtil;

import java.io.File;
import java.io.PrintWriter;
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
	
	
	@Autowired
	@Qualifier("resumenCostosServiceImpl")
	private ResumenCostosService resumenCostosService;
	
	
	
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
    		//TODOS = que filtre grupo de informacion activos e inactivos
    		r.setListaGrupoInf(fiseGrupoInformacionService.listarGrupoInformacion(FiseConstants.MENSUAL,"TODOS")); 
    		
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
	
	@ResourceMapping("verResumenCostoF14A")
	public void verResumenF14A(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("resumenCostoBean")ResumenCostoBean r) {		
		List<ResumenCostoBean> listaF14A =null;
		byte[] bytesF14A = null;
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
		    
		    Map<String, Object> mapa = new  HashMap<String, Object>();
		    mapa.put("IMG", rutaImg);
		    mapa.put("GRUPO_INFORMACION", r.getDesGrupoInf());
			mapa.put(JRParameter.REPORT_LOCALE, Locale.US);			  
		   
		    JSONObject jsonObj = new JSONObject();	   
		    
		    String tipoFormato = "RESUMEN DE COSTOS F14A ";
		    String tipoArchivo = "3";//PDF		   
		    session.setAttribute("tipoFormato",tipoFormato);
		    session.setAttribute("tipoArchivo",tipoArchivo);	    
		    
		    String nombreReporte = "resumenCostos14A";   		    	
		    String directorio = "/reports/" + nombreReporte + ".jasper";
		    
		    listaF14A = resumenCostosService.buscarResumenCostoF14A(r.getCodEmpresaBusq(), idGrupoInf);	    
		    
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
	
	
	@ResourceMapping("verResumenCostoF14AExcel")
	public void verResumenF14AExel(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("resumenCostoBean")ResumenCostoBean r) {		
		List<ResumenCostoBean> listaF14A =null;		
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
		    
		    listaF14A = resumenCostosService.buscarResumenCostoF14A(r.getCodEmpresaBusq(), idGrupoInf);	    
		    
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
			logger.error("Error al ver  resumen de costos F14A exel: "+e); 
			e.printStackTrace();
		}finally{
			if(listaF14A!=null){
				listaF14A =null;
			}		
		}
    }
	
	
	@ResourceMapping("verResumenCostoF14B")
	public void verResumenF14B(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("resumenCostoBean")ResumenCostoBean r) {		
		List<ResumenCostoBean> listaF14B =null;
		byte[] bytesF14B = null;
		try {	
			
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();	
			
			logger.info("Entrando a ver reporte de resumen costos F14B");
			
			long idGrupoInf = 0;
			if(FormatoUtil.isNotBlank(r.getGrupoInfBusq())){
				idGrupoInf = Long.valueOf(r.getGrupoInfBusq());
			}
			logger.info("codEmpresa:  "+r.getCodEmpresaBusq());
			logger.info("grupo inf:  "+idGrupoInf);
			logger.info("periocidad:  "+r.getOptionFormato());
			logger.info("zona:  "+r.getOptionZona());	
			
		    String rutaImg = session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg");
		    
		    Map<String, Object> mapa = new  HashMap<String, Object>();
		    mapa.put("IMG", rutaImg);
		    mapa.put("GRUPO_INFORMACION", r.getDesGrupoInf());
			mapa.put(JRParameter.REPORT_LOCALE, Locale.US);			  
		   
		    JSONObject jsonObj = new JSONObject();	   
		    
		    String tipoFormato = "RESUMEN DE COSTOS F14B ";
		    String tipoArchivo = "3";//PDF		   
		    session.setAttribute("tipoFormato",tipoFormato);
		    session.setAttribute("tipoArchivo",tipoArchivo);	    
		    
		    String nombreReporte = "";  
		    if(r.getOptionZona()!=null && r.getOptionZona().equals("RURAL")){ 
		    	nombreReporte = "resumenCostos14B_Rural";  
		    }else if(r.getOptionZona()!=null && r.getOptionZona().equals("PROVINCIA")){
		    	nombreReporte = "resumenCostos14B_Prov";  
		    }else if(r.getOptionZona()!=null && r.getOptionZona().equals("LIMA")){
		    	nombreReporte = "resumenCostos14B_Lima";  	
		    }
		    String directorio = "/reports/" + nombreReporte + ".jasper";
		    
		    listaF14B = resumenCostosService.buscarResumenCostoF14B(r.getCodEmpresaBusq(), idGrupoInf);	    
		    
		    if(listaF14B!=null && listaF14B.size()>0 && FormatoUtil.isNotBlank(nombreReporte)){ 
		    	File reportFile = new File(session.getServletContext().getRealPath(directorio));
		    	bytesF14B = JasperRunManager.runReportToPdf(reportFile.getPath(), mapa, 
		    			new JRBeanCollectionDataSource(listaF14B));
		    	if (bytesF14B != null) {				  	  		    		
		    		session.setAttribute("bytesFormato", bytesF14B);
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
			logger.error("Error al ver  resumen de costos F14B: "+e); 
			e.printStackTrace();
		}finally{
			if(listaF14B!=null){
				listaF14B =null;
			}
			if(bytesF14B!=null){
				bytesF14B=null;
			}
		}
    }
	
	
	@ResourceMapping("verResumenCostoF14BExcel")
	public void verResumenF14BExcel(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("resumenCostoBean")ResumenCostoBean r) {		
		List<ResumenCostoBean> listaF14B =null;		
		try {	
			
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();	
			
			logger.info("Entrando a ver reporte de resumen costos F14B Excel");
			
			long idGrupoInf = 0;
			if(FormatoUtil.isNotBlank(r.getGrupoInfBusq())){
				idGrupoInf = Long.valueOf(r.getGrupoInfBusq());
			}
			logger.info("codEmpresa:  "+r.getCodEmpresaBusq());
			logger.info("grupo inf:  "+idGrupoInf);
			logger.info("periocidad:  "+r.getOptionFormato());
			logger.info("zona:  "+r.getOptionZona());	
			
		    String rutaImg = session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg");
		    
		    Map<String, Object> mapa = new  HashMap<String, Object>();
		    mapa.put("IMG", rutaImg);
		    mapa.put("GRUPO_INFORMACION", r.getDesGrupoInf());
			mapa.put(JRParameter.REPORT_LOCALE, Locale.US);			  
		   
		    JSONObject jsonObj = new JSONObject();	   
		    
		    String tipoFormato = FiseConstants.TIPO_FORMATO_RESUMEN_COSTOS;
		    String tipoArchivo = "1";//exel	   
		    String nombreArchivo ="RESUMEN_COSTO_F14B"; 
		    
		    String nombreReporte = "";  
		    if(r.getOptionZona()!=null && r.getOptionZona().equals("RURAL")){ 
		    	nombreReporte = "resumenCostos14B_Rural_Excel";  
		    }else if(r.getOptionZona()!=null && r.getOptionZona().equals("PROVINCIA")){
		    	nombreReporte = "resumenCostos14B_Prov_Excel";  
		    }else if(r.getOptionZona()!=null && r.getOptionZona().equals("LIMA")){
		    	nombreReporte = "resumenCostos14B_Lima_Excel";  	
		    }
		    
		    listaF14B = resumenCostosService.buscarResumenCostoF14B(r.getCodEmpresaBusq(), idGrupoInf);	    
		    
		    if(listaF14B!=null && listaF14B.size()>0 && FormatoUtil.isNotBlank(nombreReporte)){
		    	session.setAttribute("tipoFormato",tipoFormato);
		    	session.setAttribute("tipoArchivo",tipoArchivo);
		    	session.setAttribute("nombreReporte",nombreReporte);
		    	session.setAttribute("nombreArchivo",nombreArchivo);
		    	session.setAttribute("lista", listaF14B);
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
			logger.error("Error al ver  resumen de costos F14B Excel: "+e); 
			e.printStackTrace();
		}finally{
			if(listaF14B!=null){
				listaF14B =null;
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
			

			long idGrupoInf = 0;
			if(FormatoUtil.isNotBlank(r.getGrupoInfBusq())){
				idGrupoInf = Long.valueOf(r.getGrupoInfBusq());
			}
			logger.info("codEmpresa:  "+r.getCodEmpresaBusq());
			logger.info("grupo inf:  "+idGrupoInf);			
			logger.info("periocidad:  "+r.getOptionFormato());		    
		   		    
            String rutaImg = session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg");
		    
		    Map<String, Object> mapa = new  HashMap<String, Object>();
		    mapa.put("IMG", rutaImg);
		    mapa.put("TIPO_FORMATO", FiseConstants.NOMBRE_F12A);
		    mapa.put("GRUPO_INFORMACION", r.getDesGrupoInf());
			mapa.put(JRParameter.REPORT_LOCALE, Locale.US);			  
		   
		    JSONObject jsonObj = new JSONObject();	   
		    
		    String tipoFormato = "RESUMEN DE COSTOS F12A ";
		    String tipoArchivo = "3";//PDF		   
		    session.setAttribute("tipoFormato",tipoFormato);
		    session.setAttribute("tipoArchivo",tipoArchivo);	    
		    
		    String nombreReporte = "resumenCostos12A_12B";   		    	
		    String directorio = "/reports/" + nombreReporte + ".jasper";
		    
		    listaF12A = resumenCostosService.buscarResumenCostoF12AB(r.getCodEmpresaBusq(), idGrupoInf, "F12A");     
		    
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
	
	
	@ResourceMapping("verResumenCostoF12AExcel")
	public void verResumenF12AExcel(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("resumenCostoBean")ResumenCostoBean r) {		
		List<ResumenCostoBean> listaF12A =null;	
		try {	
			
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();
			
			logger.info("Entrando a ver reporte de resumen de costos de F12A Excel"); 
			

			long idGrupoInf = 0;
			if(FormatoUtil.isNotBlank(r.getGrupoInfBusq())){
				idGrupoInf = Long.valueOf(r.getGrupoInfBusq());
			}
			logger.info("codEmpresa:  "+r.getCodEmpresaBusq());
			logger.info("grupo inf:  "+idGrupoInf);			
			logger.info("periocidad:  "+r.getOptionFormato());		    
		   		    
            String rutaImg = session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg");
		    
		    Map<String, Object> mapa = new  HashMap<String, Object>();
		    mapa.put("IMG", rutaImg);
		    mapa.put("TIPO_FORMATO", FiseConstants.NOMBRE_F12A);
		    mapa.put("GRUPO_INFORMACION", r.getDesGrupoInf());
			mapa.put(JRParameter.REPORT_LOCALE, Locale.US);			  
		   
		    JSONObject jsonObj = new JSONObject();	       
		    
		    String tipoFormato = FiseConstants.TIPO_FORMATO_RESUMEN_COSTOS;
		    String tipoArchivo = "1";//exel		
		    String nombreReporte = "resumenCostos12A_12B_Excel";
		    String nombreArchivo ="RESUMEN_COSTO_F12A";     
		    
		    listaF12A = resumenCostosService.buscarResumenCostoF12AB(r.getCodEmpresaBusq(), idGrupoInf, "F12A"); 
		    
		    if(listaF12A!=null && listaF12A.size()>0){
		    	session.setAttribute("tipoFormato",tipoFormato);
		    	session.setAttribute("tipoArchivo",tipoArchivo);
		    	session.setAttribute("nombreReporte",nombreReporte);
		    	session.setAttribute("nombreArchivo",nombreArchivo);
		    	session.setAttribute("lista", listaF12A);
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
			logger.error("Error al ver reporte de reseumen de costo de F12A Excel: "+e); 
			e.printStackTrace();
		}finally{	
			if(listaF12A!=null){
				listaF12A =null;
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
			
			
			long idGrupoInf = 0;
			if(FormatoUtil.isNotBlank(r.getGrupoInfBusq())){
				idGrupoInf = Long.valueOf(r.getGrupoInfBusq());
			}
			logger.info("codEmpresa:  "+r.getCodEmpresaBusq());
			logger.info("grupo inf:  "+idGrupoInf);		
			logger.info("periocidad:  "+r.getOptionFormato());			
		   		    
            String rutaImg = session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg");
		    
		    Map<String, Object> mapa = new  HashMap<String, Object>();
		    mapa.put("IMG", rutaImg);
		    mapa.put("TIPO_FORMATO", FiseConstants.NOMBRE_F12B);
		    mapa.put("GRUPO_INFORMACION", r.getDesGrupoInf());
			mapa.put(JRParameter.REPORT_LOCALE, Locale.US);			  
		   
		    JSONObject jsonObj = new JSONObject();	   
		    
		    String tipoFormato = "RESUMEN DE COSTOS F12A ";
		    String tipoArchivo = "3";//PDF		   
		    session.setAttribute("tipoFormato",tipoFormato);
		    session.setAttribute("tipoArchivo",tipoArchivo);	    
		    
		    String nombreReporte = "resumenCostos12A_12B";   		    	
		    String directorio = "/reports/" + nombreReporte + ".jasper";
		    
		    listaF12B = resumenCostosService.buscarResumenCostoF12AB(r.getCodEmpresaBusq(), idGrupoInf, "F12B"); 
		    
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
			logger.error("Error al ver el reporte de resumen de costos F12B:  "+e); 
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
	
	
	@ResourceMapping("verResumenCostoF12BExcel")
	public void verResumenF12BExcel(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("resumenCostoBean")ResumenCostoBean r) {		
		List<ResumenCostoBean> listaF12B =null;		
		try {	
			
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();		    
		    
			logger.info("Entrando a ver reporte de resumen de costos de F12B Excel"); 	
			
			
			long idGrupoInf = 0;
			if(FormatoUtil.isNotBlank(r.getGrupoInfBusq())){
				idGrupoInf = Long.valueOf(r.getGrupoInfBusq());
			}
			logger.info("codEmpresa:  "+r.getCodEmpresaBusq());
			logger.info("grupo inf:  "+idGrupoInf);		
			logger.info("periocidad:  "+r.getOptionFormato());			
		   		    
            String rutaImg = session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg");
		    
		    Map<String, Object> mapa = new  HashMap<String, Object>();
		    mapa.put("IMG", rutaImg);
		    mapa.put("TIPO_FORMATO", FiseConstants.NOMBRE_F12B);
		    mapa.put("GRUPO_INFORMACION", r.getDesGrupoInf());
			mapa.put(JRParameter.REPORT_LOCALE, Locale.US);			  
		   
		    JSONObject jsonObj = new JSONObject();    
		    
		    String tipoFormato = FiseConstants.TIPO_FORMATO_RESUMEN_COSTOS;
		    String tipoArchivo = "1";//exel		
		    String nombreReporte = "resumenCostos12A_12B_Excel";
		    String nombreArchivo ="RESUMEN_COSTO_F12B";     
		    
		    listaF12B = resumenCostosService.buscarResumenCostoF12AB(r.getCodEmpresaBusq(), idGrupoInf, "F12B"); 
		    
		    if(listaF12B!=null && listaF12B.size()>0){
		    	session.setAttribute("tipoFormato",tipoFormato);
		    	session.setAttribute("tipoArchivo",tipoArchivo);
		    	session.setAttribute("nombreReporte",nombreReporte);
		    	session.setAttribute("nombreArchivo",nombreArchivo);
		    	session.setAttribute("lista", listaF12B);
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
			logger.error("Error al ver reporte resumen costos 12B Excel: "+e); 
			e.printStackTrace();
		}finally{
			if(listaF12B!=null){
				listaF12B =null;
			}		
		}
    }
	
	/**Metodos para ver reportes de resumen de costos comparativos formatos 14A y 14B*/
	
	@ResourceMapping("verResumenCostoComF14AB")
	public void verResumenCostoCompF14AB(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("resumenCostoBean")ResumenCostoBean r) {		
		List<ResumenCostoBean> listaResumen =null;
		byte[] bytesLista = null;
		try {	
			
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();		    
		    
			logger.info("Entrando a ver reporte de resumen de costos comparativos de F14A y F14B"); 	
			
			long idGrupoInf = 0;
			long idZona = 0;
			String formato = "";
			String desZona = "";
			
			if(FormatoUtil.isNotBlank(r.getGrupoInfBusq())){
				idGrupoInf = Long.valueOf(r.getGrupoInfBusq());
			}
			if(FormatoUtil.isNotBlank(r.getOptionZona()) && 
					"RURAL".equals(r.getOptionZona())){
				idZona = FiseConstants.ZONABENEF_RURAL;
				desZona = "Zona Rural";
			}else if(FormatoUtil.isNotBlank(r.getOptionZona()) && 
					"PROVINCIA".equals(r.getOptionZona())){
				idZona = FiseConstants.ZONABENEF_PROVINCIA;
				desZona = "Zona Urbano Provincias";
			}else if(FormatoUtil.isNotBlank(r.getOptionZona()) && 
					"LIMA".equals(r.getOptionZona())){
				idZona = FiseConstants.ZONABENEF_LIMA_COD;
				desZona = "Zona Urbano Lima";
			}
			logger.info("codEmpresa:  "+r.getCodEmpresaBusq());
			logger.info("grupo inf:  "+idGrupoInf);		
			logger.info("periocidad:  "+r.getOptionFormato());	
			logger.info("formato:  "+r.getOptionBienal());//formato seleccionado de los bienales
			logger.info("zona:  "+r.getOptionZona());//zona
		   		    
            String rutaImg = session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg");
		    
		    Map<String, Object> mapa = new  HashMap<String, Object>();
		    mapa.put("IMG", rutaImg);
		    mapa.put("GRUPO_INFORMACION", r.getDesGrupoInf());
		    if("F14A_COMP".equals(r.getOptionBienal())){
		    	 mapa.put("FORMATO", FiseConstants.NOMBRE_F14A_COMP+ " - " +desZona);
		    	 formato ="F14A";		    	 
		    }else if("F14B_COMP".equals(r.getOptionBienal())){
		    	 mapa.put("FORMATO", FiseConstants.NOMBRE_F14B_COMP+ " - " +desZona);
		    	 formato ="F14B";		    	
		    }			   
			mapa.put(JRParameter.REPORT_LOCALE, Locale.US);			  
		   
		    JSONObject jsonObj = new JSONObject();	   
		    
		    String tipoFormato = FiseConstants.TIPO_FORMATO_RESUMEN_COSTOS_COMP;
		    String tipoArchivo = "3";//PDF		   
		    session.setAttribute("tipoFormato",tipoFormato);
		    session.setAttribute("tipoArchivo",tipoArchivo);	    
		    
		    String nombreReporte = "resumenCostos14AB_Comp";   		    	
		    String directorio = "/reports/" + nombreReporte + ".jasper";   
		    
		    listaResumen = resumenCostosService.buscarResumenCostoCompF14AB(r.getCodEmpresaBusq(),
		    		idGrupoInf, idZona, formato);
		    
		    if(listaResumen!=null && listaResumen.size()>0){
		    	File reportFile = new File(session.getServletContext().getRealPath(directorio));
		    	bytesLista = JasperRunManager.runReportToPdf(reportFile.getPath(), mapa, 
		    			new JRBeanCollectionDataSource(listaResumen));
		    	if (bytesLista != null) {				  	  		    		
		    		session.setAttribute("bytesFormato", bytesLista);
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
			logger.error("Error al ver el reporte de resumen de costos F12B:  "+e); 
			e.printStackTrace();
		}finally{
			if(listaResumen!=null){
				listaResumen =null;
			}
			if(bytesLista!=null){
				bytesLista=null;
			}
		}
    }
	
	
	@ResourceMapping("verResumenCostoCompF14ABExcel")
	public void verResumenCostoCompF14ABExcel(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("resumenCostoBean")ResumenCostoBean r) {		
		List<ResumenCostoBean> listaResumen =null;		
		try {	
			
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();		    
		    
			logger.info("Entrando a ver reporte de resumen de costos comparativos de F1A y F14B Excel"); 	
			
			
			long idGrupoInf = 0;
			long idZona = 0;
			String formato = "";
			String nombreArchivo= "";
			String desZona = "";
			
			if(FormatoUtil.isNotBlank(r.getGrupoInfBusq())){
				idGrupoInf = Long.valueOf(r.getGrupoInfBusq());
			}
			if(FormatoUtil.isNotBlank(r.getOptionZona()) && 
					"RURAL".equals(r.getOptionZona())){
				idZona = FiseConstants.ZONABENEF_RURAL;
				desZona = "Zona Rural";
			}else if(FormatoUtil.isNotBlank(r.getOptionZona()) && 
					"PROVINCIA".equals(r.getOptionZona())){
				idZona = FiseConstants.ZONABENEF_PROVINCIA;
				desZona = "Zona Urbano Provincias";
			}else if(FormatoUtil.isNotBlank(r.getOptionZona()) && 
					"LIMA".equals(r.getOptionZona())){
				idZona = FiseConstants.ZONABENEF_LIMA_COD;
				desZona = "Zona Urbano Lima";
			}
			logger.info("codEmpresa:  "+r.getCodEmpresaBusq());
			logger.info("grupo inf:  "+idGrupoInf);		
			logger.info("periocidad:  "+r.getOptionFormato());	
			logger.info("formato:  "+r.getOptionBienal());//formato seleccionado de los bienales
			logger.info("zona:  "+r.getOptionZona());//zona
		   		    
            String rutaImg = session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg");
		    
		    Map<String, Object> mapa = new  HashMap<String, Object>();
		    mapa.put("IMG", rutaImg);
		    mapa.put("GRUPO_INFORMACION", r.getDesGrupoInf());
		    if("F14A_COMP".equals(r.getOptionBienal())){
		    	 mapa.put("FORMATO", FiseConstants.NOMBRE_F14A_COMP+ " - " +desZona);
		    	 formato ="F14A";
		    	 nombreArchivo ="RESUMEN_COSTO_COMP_F12A";  
		    }else if("F14B_COMP".equals(r.getOptionBienal())){
		    	 mapa.put("FORMATO", FiseConstants.NOMBRE_F14B_COMP+ " - " +desZona);
		    	 formato ="F14B";
		    	 nombreArchivo ="RESUMEN_COSTO_COMP_F12B";  
		    }	   
			mapa.put(JRParameter.REPORT_LOCALE, Locale.US);			  
		   
		    JSONObject jsonObj = new JSONObject();    
		    
		    String tipoFormato = FiseConstants.TIPO_FORMATO_RESUMEN_COSTOS;
		    String tipoArchivo = "1";//exel		
		    String nombreReporte = "resumenCostos14AB_Comp_Excel";        
		    
		    listaResumen = resumenCostosService.buscarResumenCostoCompF14AB(r.getCodEmpresaBusq(),
		    		idGrupoInf, idZona, formato);
		    
		    logger.info("tamaño de la lista de resumen de costos comparativos 14A y 14B exel:  "+listaResumen.size()); 
		    
		    if(listaResumen!=null && listaResumen.size()>0){
		    	session.setAttribute("tipoFormato",tipoFormato);
		    	session.setAttribute("tipoArchivo",tipoArchivo);
		    	session.setAttribute("nombreReporte",nombreReporte);
		    	session.setAttribute("nombreArchivo",nombreArchivo);
		    	session.setAttribute("lista", listaResumen);
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
			logger.error("Error al ver reporte resumen costos comparativos 14A y 14B Excel: "+e); 
			e.printStackTrace();
		}finally{
			if(listaResumen!=null){
				listaResumen =null;
			}		
		}
    }
	
	
	
}
