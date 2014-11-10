package gob.osinergmin.fise.gart.controller;



import gob.osinergmin.fise.common.util.FiseUtil;

import gob.osinergmin.fise.gart.command.PeriodoEnvioCommand;



import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.PortalUtil;


@Controller("periodoEnvioController")
@RequestMapping("VIEW")
public class PeriodoEnvioController {
	
	Log logger=LogFactoryUtil.getLog(PeriodoEnvioController.class);

	@Autowired
	ServletContext context;
	
		
	@Autowired
	@Qualifier("fiseUtil")
	FiseUtil fiseUtil;

	
	@RequestMapping
	public  String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse,@ModelAttribute("periodoEnvioCommand")PeriodoEnvioCommand command){
		command.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));
		command.setListaMes(fiseUtil.getMapaMeses());
		command.setAnioInicio(fiseUtil.obtenerNroAnioFechaActual());
		command.setMesInicio( String.valueOf(Integer.parseInt(fiseUtil.obtenerNroMesFechaActual())-1));
		command.setAnioFin(fiseUtil.obtenerNroAnioFechaActual());
		command.setMesFin(fiseUtil.obtenerNroMesFechaActual());

		model.addAttribute("esAdministrador", fiseUtil.esAdministrador(renderRequest));
		
		logger.info("admin1.1:"+model.get("esAdministrador"));
		
	
		return "bandeja_periodo_envio";
	}
	
	
	@ResourceMapping("busqueda")
  	public void grid(ModelMap model,ResourceRequest request,ResourceResponse response,@ModelAttribute("periodoEnvioCommand")PeriodoEnvioCommand command){
		
		response.setContentType("application/json");	
		logger.info("admin2.1:"+model.get("esAdministrador"));
		HttpServletRequest req = PortalUtil.getHttpServletRequest(request);	        
        HttpSession session = req.getSession();
			
	    JSONArray jsonArray = new JSONArray();
			String codEmpresa = command.getCodEmpresa();
			String anioDesde = command.getAnioInicio();
			String mesDesde = command.getMesInicio();
			String anioHasta = command.getAnioFin();
			String mesHasta = command.getMesFin();
			String etapa = command.getEtapa();
			logger.info("valores "+ codEmpresa);
  			logger.info("valores "+ anioDesde);
  			logger.info("valores "+ mesDesde);
  			logger.info("valores "+ anioHasta);
  			logger.info("valores "+ mesHasta);
  			logger.info("valores "+ etapa);
	}
	
	
	

}
