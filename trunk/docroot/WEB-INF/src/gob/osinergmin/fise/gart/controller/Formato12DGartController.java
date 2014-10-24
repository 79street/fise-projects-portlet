package gob.osinergmin.fise.gart.controller;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("formato12DGartController")
@RequestMapping("VIEW")
public class Formato12DGartController {
	
	@RequestMapping
	public String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse){
		
		return "formato12D";
	}

}
