package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.CumplimientoReportBean;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.AdmEmpresa;
import gob.osinergmin.fise.domain.FisePeriodoEnvio;
import gob.osinergmin.fise.gart.service.AdmEmpresaGartService;
import gob.osinergmin.fise.gart.service.FisePeriodoEnvioGartService;
import gob.osinergmin.fise.gart.service.FormatoCumplimientoService;
import gob.osinergmin.fise.util.FechaUtil;

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
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.liferay.portal.util.PortalUtil;

@Controller("cumplimientoBienalGartController")
@RequestMapping("VIEW")
public class CumplimientoBienalGartController {
	
	Logger logger = Logger.getLogger(CumplimientoBienalGartController.class);
	
	@Autowired
	AdmEmpresaGartService admEmpresaService;
	
	@Autowired
	FormatoCumplimientoService formatoCumplimientoService;
	
	@Autowired
	FisePeriodoEnvioGartService fisePeriodoEnvioGartService;
	//private Periodo<Long,Long, String> Periodoenvio;
	
	private List<FisePeriodoEnvio> listaPeriodo;
	private Map<String, String> mapaEmpresa;
	private Map<Long,String> mapaMeses;
	
	@RequestMapping
	public String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse){
		
		System.out.println("prueba de portlet");
		
		//mapaMeses = FechaUtil.cargarMapaMeses();
		List<AdmEmpresa> listaEmpresa = admEmpresaService.listarAdmEmpresa();
		mapaEmpresa = new HashMap<String, String>();
		for (AdmEmpresa admEmpresa : listaEmpresa) {
			//logger.info("codEmpresa: "+admEmpresa.getCodEmpresa()+" desccortaempresa: "+admEmpresa.getDscCortaEmpresa());
			mapaEmpresa.put(admEmpresa.getCodEmpresa(), admEmpresa.getDscCortaEmpresa());
		}
				
		mapaMeses = FechaUtil.cargarMapaMeses();
		
		listaPeriodo = fisePeriodoEnvioGartService.listarFisePeriodoEnvioMesAnioEtapaCumplimiento(FiseConstants.FRECUENCIA_BIENAL_DESCRIPCION);
		model.addAttribute("listaPeriodo", listaPeriodo);
	
		return "cumplimientoBienal";
	}

	@ResourceMapping("reporte")
	public void reporte(SessionStatus status, ResourceRequest request,ResourceResponse response) {
		try {
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();
	        
		    JSONArray jsonArray = new JSONArray();	

		    String periodo = request.getParameter("periodo").trim();
		    String tipoArchivo = request.getParameter("tipoArchivo").trim();
		    
		    session.setAttribute("tipoFormato", FiseConstants.TIPO_FORMATO_CUMPLIMIENTO);
		    session.setAttribute("tipoArchivo", tipoArchivo);
		    //cambiar de acuerdo al reporte que estan probando
		   String nombre = "cumplimientoBienal";
		    session.setAttribute("nombreArchivo", nombre);
		    session.setAttribute("nombreReporte", nombre);
		    
		
		    /////////////////////// reporte de cumplimientio/////////////
		    String anio="";
		    String mes="";
		    String etapa="";
		    
		    if( periodo!=null && periodo.length()>6 ){
		    	anio=periodo.substring(0,4);
		    	mes=periodo.substring(4,6);
		    	etapa=periodo.substring(6,periodo.length());
		    }
		    
		    List<CumplimientoReportBean> lista = formatoCumplimientoService.listarFormatoCumplimientoReportBean(Long.parseLong(anio), Long.parseLong(mes), etapa);
		    
		    session.setAttribute("lista", lista);
		    
		    Map<String, Object> mapa = new HashMap<String, Object>();
		    mapa.put(FiseConstants.PARAM_ANO_CUMPLI, Long.parseLong(anio));
		    mapa.put(FiseConstants.PARAM_MES_CUMPLI, mapaMeses.get(Long.parseLong(mes)));
		    mapa.put(FiseConstants.PARAM_ETAPA_CUMPLI, etapa);
		    session.setAttribute("mapa", mapa);
		    //System.out.println("jhdhdhdh"+lista);
		  
		    response.setContentType("application/json");
		    PrintWriter pw = response.getWriter();
		    pw.write(jsonArray.toString());
		    pw.flush();
		    pw.close();
		    
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		 
		 
	}
 
}
