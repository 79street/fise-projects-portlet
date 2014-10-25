package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.gart.command.Formato13AGartCommand;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("formato13AGartController")
@RequestMapping("VIEW")
public class Formato13AGartController {

	@Autowired
	@Qualifier("fiseUtil")
	FiseUtil fiseUtil;

	@ModelAttribute("formato13AGartCommand")
	public Formato13AGartCommand inicializar(){
		Formato13AGartCommand command=new Formato13AGartCommand();
		command.setListaMes(fiseUtil.getMapaMeses());
		return command;
	}
	
	@RequestMapping
	public String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse,@ModelAttribute("formato13AGartCommand")Formato13AGartCommand command){
		//Cargamos el combo
		
		if(command.getListaEmpresas()==null){
			command.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));
		}
		command.setAdmin(fiseUtil.esAdministrador(renderRequest));
		
		return "formato13A";
	}
	
}
