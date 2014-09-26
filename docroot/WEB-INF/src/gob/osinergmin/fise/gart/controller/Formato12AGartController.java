package gob.osinergmin.fise.gart.controller;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

@Controller
@RequestMapping("VIEW")
public class Formato12AGartController {
	
	Log logger=LogFactoryUtil.getLog(Formato12AGartController.class);

	@RequestMapping
	public  String defaultView(ModelMap model){
		logger.info("--- defaultView");
		/*cargaInicial();
		model.addAttribute("listaMes", listaMes);
		model.addAttribute("listaEmpresa", listaEmpresa);
		model.addAttribute("listaZonaBenef", listaZonaBenef);*/
		return "formato12A";
	}
	
	@ActionMapping(params="action=buscarFormato")
	public void registrarPersona(ActionRequest request,ActionResponse response, ModelMap model){
		listaFormato=formato12ACService.listarFormato();
		model.addAttribute("listaFormato", listaFormato);
		logger.info(listaFormato);
	}
	
}
