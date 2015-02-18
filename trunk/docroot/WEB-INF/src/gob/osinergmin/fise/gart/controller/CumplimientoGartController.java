package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.CumplimientoReportBean;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.FiseGrupoInformacion;
import gob.osinergmin.fise.gart.service.AdmEmpresaGartService;
import gob.osinergmin.fise.gart.service.FiseGrupoInformacionGartService;
import gob.osinergmin.fise.gart.service.FormatoCumplimientoService;
import gob.osinergmin.fise.util.FechaUtil;
import gob.osinergmin.fise.util.FormatoUtil;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.liferay.portal.util.PortalUtil;

@Controller("cumplimientoGartController")
@RequestMapping("VIEW")
public class CumplimientoGartController {
	
	Logger logger = Logger.getLogger(CumplimientoGartController.class);
	
	@Autowired
	AdmEmpresaGartService admEmpresaService;
	
	@Autowired
	FormatoCumplimientoService formatoCumplimientoService;
	
	@Autowired
	@Qualifier("fiseGrupoInformacionGartServiceImpl")
	private FiseGrupoInformacionGartService fiseGrupoInformacionService;
	
	private List<FiseGrupoInformacion> listaGrupoInf;
	
	private Map<Long,String> mapaMeses;
	
	@RequestMapping
	public String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse){	
		try {							
			mapaMeses = FechaUtil.cargarMapaMeses();			
			listaGrupoInf = fiseGrupoInformacionService.listarGrupoInformacion(FiseConstants.MENSUAL,"TODOS");
			model.addAttribute("listaGrupoInf", listaGrupoInf);
			
		} catch (Exception e) {
			logger.info("Error al cargar reporte cumplimiento mensual:  "+e.getMessage()); 
			e.printStackTrace();
		}
		return "cumplimiento";
	}

	@ResourceMapping("reporteMensual")
	public void reporte(SessionStatus status, ResourceRequest request,ResourceResponse response) {		
		FiseGrupoInformacion grupo = null;
		try {
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();
	        
	        JSONObject jsonObj = new JSONObject();	
		    
		    String idGrupoInf = request.getParameter("grupoInf").trim();
		    logger.info("Id grupo de informacion:   "+idGrupoInf); 
		    String etapa = request.getParameter("etapa").trim();
		    logger.info("Etapa:   "+etapa); 
		    String tipoArchivo = request.getParameter("tipoArchivo").trim();
		    logger.info("Tipo de archivo:   "+tipoArchivo); 
		    
		    session.setAttribute("tipoFormato", FiseConstants.TIPO_FORMATO_CUMPLIMIENTO);
		    session.setAttribute("tipoArchivo", tipoArchivo);		  
		    String nombre = "cumplimiento";
		    session.setAttribute("nombreArchivo", nombre);
		    session.setAttribute("nombreReporte", nombre);
		    
		    long anio = 0;
		    long mes = 0;
		  
		    if(FormatoUtil.isNotBlank(idGrupoInf)){ 
		    	 grupo =  fiseGrupoInformacionService.obtenerGrupoInf(Long.valueOf(idGrupoInf));
		    }
		   		    
		    if(grupo!=null){
		    	anio = grupo.getAnoPresentacion();
		    	mes = grupo.getMesPresentacion();  	
		    }
		    
		    List<CumplimientoReportBean> lista = formatoCumplimientoService.listarFormatoCumplimientoReportBean(anio,mes,etapa);
		    if(lista !=null && lista.size()>0){
		    	session.setAttribute("lista", lista);			    
			    Map<String, Object> mapa = new HashMap<String, Object>();
			    mapa.put(FiseConstants.PARAM_ANO_CUMPLI, anio);
			    mapa.put(FiseConstants.PARAM_MES_CUMPLI, mapaMeses.get(mes));
			    mapa.put(FiseConstants.PARAM_ETAPA_CUMPLI, etapa);
			    mapa.put("GRUPO_INFORMACION", grupo.getDescripcion());
			    session.setAttribute("mapa", mapa);	
			    jsonObj.put("resultado", "OK");	
		    }else{
		    	jsonObj.put("resultado", "VACIO");	
		    }	  
		    response.setContentType("application/json");
		    PrintWriter pw = response.getWriter();
		    pw.write(jsonObj.toString());
		    logger.info(jsonObj.toString());
		    pw.flush();
		    pw.close();		    
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(grupo!=null){
				grupo =null;
			}
		}	 
	}
 
}
