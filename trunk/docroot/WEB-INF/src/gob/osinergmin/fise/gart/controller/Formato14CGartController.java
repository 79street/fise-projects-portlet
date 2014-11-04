package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.Formato14CBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.FiseFormato14CC;
import gob.osinergmin.fise.gart.json.Formato14CJSON;
import gob.osinergmin.fise.gart.service.Formato14CGartService;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.PortalUtil;

@Controller("formato14CGartController")
@RequestMapping("VIEW")
public class Formato14CGartController {
	
	Log logger=LogFactoryUtil.getLog(Formato14CGartController.class);
	
	@Autowired
	@Qualifier("formato14CGartServiceImpl")
	private Formato14CGartService formato14CGartService;
	
	@Autowired
	@Qualifier("fiseUtil")
	FiseUtil fiseUtil;
	
	private Map<String, String> mapaEmpresa;	
	
	
	
	
	
	@ModelAttribute("formato14CBean")
    public Formato14CBean listFiseFormato14CC() {
		Formato14CBean comman  = new Formato14CBean();
        return comman;	        
    }
	
//	@RequestMapping
//	public  String defaultView(){
//		logger.info("--- defaultView");
//		return "formato14C";
//	}
	
	@RequestMapping
	public String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse,
			     @ModelAttribute("formato14CBean")Formato14CBean f){

		/*f.setListaMes(fiseUtil.getMapaMeses());
		f.setAnioDesde(fiseUtil.obtenerNroAnioFechaActual());
		f.setMesDesde( String.valueOf(Integer.parseInt(fiseUtil.obtenerNroMesFechaActual())-1));
		f.setAnioHasta(fiseUtil.obtenerNroAnioFechaActual());
		f.setMesHasta(fiseUtil.obtenerNroMesFechaActual());
		f.setEtapaBusq(FiseConstants.ETAPA_SOLICITUD); 
		
		if(f.getListaEmpresas()==null){
			f.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));
		}
		f.setAdmin(true);*/
		mapaEmpresa = fiseUtil.getMapaEmpresa();
		
		return "formato14C";
	}
	
	/*
	@ResourceMapping("busqueda")
  	public void busqueda(ResourceRequest request,ResourceResponse response,
  			@ModelAttribute("formato14CBean")Formato14CBean f){
		
		try{
			response.setContentType("application/json");	
				
			HttpServletRequest req = PortalUtil.getHttpServletRequest(request);	        
	        HttpSession session = req.getSession();
	        	
		    JSONArray jsonArray = new JSONArray();
		    
			String codEmpresa = f.getCodEmpresaBusq();	
			String etapa = f.getEtapaBusq();
			long anioDesde = 0;
			long anioHasta =0;
			long mesDesde = 0;			
			long mesHasta = 0;
			if(StringUtils.isNotBlank(f.getAnioDesde())){
				anioDesde = Long.valueOf(f.getAnioDesde());	
			}
			if(StringUtils.isNotBlank(f.getAnioHasta())){
				anioHasta = Long.valueOf(f.getAnioHasta());	
			}
			if(StringUtils.isNotBlank(f.getMesDesde())){
				mesDesde = Long.valueOf(f.getMesDesde());	
			}
			if(StringUtils.isNotBlank(f.getMesHasta())){
				mesHasta = Long.valueOf(f.getMesHasta());	
			}			
			logger.info("codigo empresa "+ codEmpresa);
  			logger.info("anio desde "+ anioDesde);
  			logger.info("mes desde "+ mesDesde);
  			logger.info("anio hasta "+ anioHasta);
  			logger.info("mes hasta "+ mesHasta);
  			logger.info("etapa "+ etapa);
  			logger.info("admin "+ f.isAdmin());
 		   
 			List<FiseFormato14CC> listaFormato14C = formato14CGartService.buscarFiseFormato14CC(codEmpresa, 
  					anioDesde, anioHasta, mesDesde, mesHasta, etapa);	
  			
  			logger.info("tamaño de la lista formato 14c   :"+listaFormato14C.size());
  			for(FiseFormato14CC formato14c : listaFormato14C){
  				formato14c.setDescEmpresa(mapaEmpresa.get(formato14c.getId().getCodEmpresa()));
  				formato14c.setDescMesPresentacion(fiseUtil.getMapaMeses().get(formato14c.getId().getMesPresentacion()));
  				jsonArray.put(new Formato14CJSON().asJSONObject(formato14c,""));
  			}
  			
  			fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_FORMATO_14C, FiseConstants.NOMBRE_EXCEL_FORMATO14C, FiseConstants.NOMBRE_HOJA_FORMATO14C, listaFormato14C);
  			
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
	
	@ActionMapping(params="action=guardar")
	public void registrarPersona(ActionRequest request,ActionResponse response,@ModelAttribute("formato14CBean") Formato14CBean f){
		try {
			logger.info("--- registrar formulario 14c");		
			logger.info("--- codigo empresa---"+f.getCodEmpresa()); 			
			formato14CGartService.insertarDatosFormato14C(f); 	
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	
	
	
	*/

}
