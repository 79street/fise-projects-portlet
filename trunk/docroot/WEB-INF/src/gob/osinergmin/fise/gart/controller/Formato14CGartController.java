package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.Formato14CBean;
import gob.osinergmin.fise.gart.service.Formato14CGartService;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

@Controller("formato14CGartController")
@RequestMapping("VIEW")
public class Formato14CGartController {
	
	Log logger=LogFactoryUtil.getLog(Formato14CGartController.class);
	
	@Autowired
	@Qualifier("formato14CGartServiceImpl")
	private Formato14CGartService formato14CGartService;
	
		
	@ModelAttribute("formato14CBean")
    public Formato14CBean listFiseFormato14CC() {
		Formato14CBean comman  = new Formato14CBean();
        return comman;	        
    }
	
	@RequestMapping
	public  String defaultView(){
		logger.info("--- defaultView");
		return "formato14C";
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

}
