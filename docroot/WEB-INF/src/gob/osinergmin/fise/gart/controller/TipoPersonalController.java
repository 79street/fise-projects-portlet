package gob.osinergmin.fise.gart.controller;



import gob.osinergmin.fise.bean.TipoPersonalBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.FiseTipPersonal;
import gob.osinergmin.fise.gart.service.FiseTipPersonalService;

import java.io.PrintWriter;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;


@Controller("tipoPersonalController")
@RequestMapping("VIEW")
public class TipoPersonalController {
	
	Logger logger = Logger.getLogger(TipoPersonalController.class);
	
	
	@Autowired
	@Qualifier("fiseTipPersonalServiceImpl")
	private FiseTipPersonalService fiseTipPersonalService;
	

	@Autowired
	@Qualifier("fiseUtil")
	private FiseUtil fiseUtil;
	
	
	
	
	
	@ModelAttribute("fipoPersonalBean")
    public TipoPersonalBean listTipoPersonalBean() {
		TipoPersonalBean comman  = new TipoPersonalBean();
        return comman;	        
    }

	
	@RequestMapping
	public String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse,
			     @ModelAttribute("tipoPersonalBean")TipoPersonalBean p){
        try {        	   		
    		p.setIdBusq(""); 
    		p.setDescripcion("");   		
    		model.addAttribute("model", p);    		
		} catch (Exception e) {
			logger.info("Ocurrio un errror al caragar la pagina observaciones"); 
			e.printStackTrace();
		}		
		return "fiseTipoPersonal";
	}
	
	@ResourceMapping("busquedaObservaciones")
  	public void busqueda(ResourceRequest request,ResourceResponse response,
  			@ModelAttribute("tipoPersonalBean")TipoPersonalBean p){
		
		try{
			HttpServletRequest req = PortalUtil.getHttpServletRequest(request);	        
	        HttpSession session = req.getSession();	  
	        
			response.setContentType("application/json");	       
	        	
			String data = "";
		    
			String id = p.getIdBusq(); 
			String descripcion = p.getDescripcionBusq();			
  			logger.info("id "+ id);  			
  			logger.info("descripcion "+ descripcion);  			
 		   
  			List<FiseTipPersonal> listaPersonal = fiseTipPersonalService.buscarFiseTipPersonal(id, descripcion); 	 			
  			logger.info("tamaño de la lista tipos de personal   :"+listaPersonal.size());   			 			
  			  			
  			fiseUtil.configuracionExportarExcel(session, FiseConstants.OBSERVACIONES_EXPORT_EXEL, 
  					"OBSERVACIONES", //title
  					"OBSERVACIONES", //nombre hoja
  					listaPersonal);
  			
  			
  			data = toStringListJSON(listaPersonal);  		
  			logger.info("arreglo json:"+data);
  			PrintWriter pw = response.getWriter();
  			pw.write(data);
  			pw.flush();
  			pw.close();
  			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	private String toStringListJSON(List<FiseTipPersonal> lista) {
		Serializer serializer = new JsonSerializer();
		Object result = serializer.serialize(lista);
		String data = String.valueOf(result);
		return data;
	}
	
	@ResourceMapping("nuevoRegistroObservacion")
	public void nuevoRegistroObservacion(ModelMap model,ResourceRequest request,ResourceResponse response,
			@ModelAttribute("tipoPersonalBean")TipoPersonalBean p) { 		
		try {	
			String id ="0";
			
			JSONObject jsonObj = new JSONObject();
			
			logger.info("entrando a nuevo registro de tipo de personal ");					
			
			id = fiseTipPersonalService.obtenerIdTipPersonal();
			
			jsonObj.put("id", id);			
			
			response.setContentType("application/json");
		    PrintWriter pw = response.getWriter();
		    pw.write(jsonObj.toString());
		    pw.flush();
		    pw.close();		
			logger.info("DATA CONVERTER JSON ID:  "+jsonObj); 
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	

	@ResourceMapping("grabarObservacion")
	public void grabarObservaciones(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("tipoPersonalBean")TipoPersonalBean p) { 
		JSONObject jsonObj = new JSONObject();
		try {		
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);				
			
			logger.info("Entrando a grabar un registro"); 			
			logger.info("ID:  "+ p.getId()); 
			logger.info("Descripcion:  "+ p.getDescripcion());				
				
			p.setUsuario(themeDisplay.getUser().getLogin());
			p.setTerminal(themeDisplay.getUser().getLoginIP());		
			
			logger.info("Enviando el formulario al service");		
			
			String valor = fiseTipPersonalService.insertarDatosFiseTipPersonal(p);
			logger.info("valor de la transaccion al insertar:  "+valor); 
			if(valor.equals("1")){ 
				jsonObj.put("resultado", "OK");				
			}else if(valor.equals("2")){ 
				jsonObj.put("resultado", "Duplicado");					
			}else{
				jsonObj.put("resultado", "Error");	
			}
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();
			pw.write(jsonObj.toString());
			pw.flush();
			pw.close();				
		} catch (Exception e) {
			e.printStackTrace();				
			logger.error("Error al guardar los datos en tipo de personal: "+e.getMessage());
		} 	
	}	
	
	
	@ResourceMapping("actualizarObservacion")
	public void actualizarObservaciones(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("tipoPersonalBean")TipoPersonalBean p) { 
		JSONObject jsonObj = new JSONObject();
		try {		
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);	
			
			p.setUsuario(themeDisplay.getUser().getLogin());
			p.setTerminal(themeDisplay.getUser().getLoginIP());	
			
			logger.info("Entrando a actualizar un registro"); 	
			logger.info("ID:  "+ p.getId()); 
			logger.info("Descripcion:  "+ p.getDescripcion());		
			
			String valor = fiseTipPersonalService.actualizarDatosFiseTipPersonal(p);
			logger.info("valor de la transaccion al actualizar:  "+valor); 
			if(!valor.equals("0")){ 
				jsonObj.put("resultado", "OK");				
			}else{
				jsonObj.put("resultado", "Error");	
			}
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();
			pw.write(jsonObj.toString());
			pw.flush();
			pw.close();				
		} catch (Exception e) {
			e.printStackTrace();				
			logger.error("Error al guardar los datos en tipo de personal: "+e.getMessage());
		} 	
	}
	
	
	@ResourceMapping("editarViewObservacion")
	public void editarObservacion(ModelMap model,ResourceRequest request,ResourceResponse response,
			@ModelAttribute("tipoPersonalBean")TipoPersonalBean p) { 		
		try {	
			String data;			
			logger.info("id para editar y visualizar "+p.getId());					
			
			p= fiseTipPersonalService.buscarFiseTipPersonalEditar(p.getId());
			
			data = toStringJSON(p);						
			response.setContentType("application/json");
		    PrintWriter pw = response.getWriter();
		    pw.write(data);
		    pw.flush();
		    pw.close();		
			logger.info("DATA CONVERTER JSON:  "+data); 
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private String toStringJSON(TipoPersonalBean p) {
		Serializer serializer = new JsonSerializer();
		Object result = serializer.serialize(p);
		String data = String.valueOf(result);
		return data;
	}
	
	@ResourceMapping("eliminarObservacion")
	public void eliminarObservacion(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("tipoPersonalBean")TipoPersonalBean p) { 	
		
		JSONObject jsonObj = new JSONObject();
		try {			
			logger.info("Entrando a eliminar un registro tipo de personal"); 			
			logger.info("id:  "+ p.getId());      			
			logger.info("Enviando el formulario al service"); 
			String valor = fiseTipPersonalService.eliminarDatosFiseTipPersonal(p.getId());
			if(valor.equals("1")){ 
				jsonObj.put("resultado", "OK");	   	
			}else{
				jsonObj.put("resultado", "Error");	
			}
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();
			pw.write(jsonObj.toString());
			pw.flush();
			pw.close();				
		} catch (Exception e) {
			e.printStackTrace();				
			logger.error("Error al eliminar los datos de tipo de personal: "+e.getMessage());
		}  	
		
	}		
	

}
