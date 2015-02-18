package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.ResumenCostoActividadBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
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
    		r.setListaGrupoInf(fiseGrupoInformacionService.listarGrupoInformacion(FiseConstants.BIENAL,"TODOS")); 
    		
    		//mapaEmpresa = fiseUtil.getMapaEmpresa();   		
    		
    		model.addAttribute("model", r);
    		
		} catch (Exception e) {
			logger.info("Ocurrio un errror al caragar la pagina de resumen de costos actividad"); 
			e.printStackTrace();
		}		
		return "resumenCostosActividad";
	}
	
	@ResourceMapping("verResumenCostoActividadF14AB")
	public void verResumenCostosActivF14AB(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("resumenCostoActividadBean")ResumenCostoActividadBean r) {		
		List<ResumenCostoActividadBean> listaF14AB =null;
		byte[] bytesF14A = null;
		try {	
			
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();	
			
			logger.info("Entrando a ver reporte de resumen costos F14A y F14B Actividades");
			
			long idGrupoInf = 0;
			if(FormatoUtil.isNotBlank(r.getGrupoInfBusq())){
				idGrupoInf = Long.valueOf(r.getGrupoInfBusq());
			}
			logger.info("codEmpresa:  "+r.getCodEmpresaBusq());
			//logger.info("lista empresas:  "+fiseUtil.getEmpresaxUsuario(request).size());
			logger.info("grupo inf:  "+idGrupoInf);
			logger.info("des grupo informacion:  "+r.getDesGrupoInf());
			
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
		    
		    String nombreReporte = "costosEstandarXEmpresa";   		    	
		    String directorio = "/reports/" + nombreReporte + ".jasper";
		    
		    //para enviar lista primero verifico que sea TODOS
		    if("TODOS".equals(r.getCodEmpresaBusq())){
		    	r.setListaEmpresas(fiseUtil.getEmpresaxUsuario(request));
		    }else{
		    	r.setListaEmpresas(null);
		    }
		    listaF14AB = resumenCostosService.buscarResumenCostoActividadF14AB(r.getCodEmpresaBusq(), idGrupoInf,r.getListaEmpresas());    
		    
		    if(listaF14AB!=null && listaF14AB.size()>0){
		    	File reportFile = new File(session.getServletContext().getRealPath(directorio));
		    	bytesF14A = JasperRunManager.runReportToPdf(reportFile.getPath(), mapa, 
		    			new JRBeanCollectionDataSource(listaF14AB));
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
			logger.error("Error al ver  resumen de costos F14A y F14B Actividades pdf: "+e); 
			e.printStackTrace();
		}finally{
			if(listaF14AB!=null){
				listaF14AB =null;
			}
			if(bytesF14A!=null){
				bytesF14A=null;
			}
		}
    }
	
	@ResourceMapping("verResumenCostoActividadF14ABExcel")
	public void verResumenF14ABActiExel(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("resumenCostoActividadBean")ResumenCostoActividadBean r) {		
		List<ResumenCostoActividadBean> listaF14AB =null;		
		try {	
			
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();	
			
			logger.info("Entrando a ver reporte de resumen costos F14A y F14B excel");
			
			long idGrupoInf = 0;
			if(FormatoUtil.isNotBlank(r.getGrupoInfBusq())){
				idGrupoInf = Long.valueOf(r.getGrupoInfBusq());
			}
			logger.info("codEmpresa:  "+r.getCodEmpresaBusq());
			logger.info("grupo inf:  "+idGrupoInf);
			logger.info("des grupo informacion:  "+r.getDesGrupoInf());
			
		    String rutaImg = session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg");
		    
		    Map<String, Object> mapa = new  HashMap<String, Object>();
		    mapa.put("IMG", rutaImg);
		    mapa.put("GRUPO_INFORMACION", r.getDesGrupoInf());
			mapa.put(JRParameter.REPORT_LOCALE, Locale.US);			  
		   
		    JSONObject jsonObj = new JSONObject();	   
		    
		    String tipoFormato = FiseConstants.TIPO_FORMATO_RESUMEN_COSTOS;
		    String tipoArchivo = "1";//exel		
		    String nombreReporte = "costosEstandarXEmpresa"; //nombre del jasper
		    String nombreArchivo ="RESUMEN_COSTO_ACTIVIDAD_F14AB";  
		    
		    //para enviar lista primero verifico que sea TODOS
		    if("TODOS".equals(r.getCodEmpresaBusq())){
		    	r.setListaEmpresas(fiseUtil.getEmpresaxUsuario(request));
		    }else{
		    	r.setListaEmpresas(null);
		    }		    
		    listaF14AB = resumenCostosService.buscarResumenCostoActividadF14AB(r.getCodEmpresaBusq(), idGrupoInf,r.getListaEmpresas());   
		    
		    if(listaF14AB!=null && listaF14AB.size()>0){
		    	session.setAttribute("tipoFormato",tipoFormato);
		    	session.setAttribute("tipoArchivo",tipoArchivo);
		    	session.setAttribute("nombreReporte",nombreReporte);
		    	session.setAttribute("nombreArchivo",nombreArchivo);
		    	session.setAttribute("lista", listaF14AB);
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
			logger.error("Error al ver  resumen de costos F14A y F14B Actividades exel: "+e); 
			e.printStackTrace();
		}finally{
			if(listaF14AB!=null){
				listaF14AB =null;
			}		
		}
    }
	
	
	
}
