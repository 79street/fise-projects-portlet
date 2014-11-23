package gob.osinergmin.fise.gart.controller;



import gob.osinergmin.fise.bean.FiseObservacionBean;
import gob.osinergmin.fise.domain.FiseObservacion;
import gob.osinergmin.fise.gart.service.FiseObservacionGartService;

import java.io.PrintWriter;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;


@Controller("fiseObservacionController")
@RequestMapping("VIEW")
public class FiseObservacionController {
	
	Log logger=LogFactoryUtil.getLog(FiseObservacionController.class);
	
	@Autowired
	@Qualifier("fiseObservacionGartServiceImpl")
	FiseObservacionGartService fiseObservacionGartService;

	/*@Autowired
	@Qualifier("fiseUtil")
	FiseUtil fiseUtil;*/
	
	
	
	
	
	@ModelAttribute("fiseObservacionBean")
    public FiseObservacionBean listFiseFiseObservacion() {
		FiseObservacionBean comman  = new FiseObservacionBean();
        return comman;	        
    }

	
	@RequestMapping
	public String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse,
			     @ModelAttribute("fiseObservacionBean")FiseObservacionBean p){
        try {        	   		
    		p.setIdBusq(""); 
    		p.setDescripcion("");   		
    		model.addAttribute("model", p);    		
		} catch (Exception e) {
			logger.info("Ocurrio un errror al caragar la pagina observaciones"); 
			e.printStackTrace();
		}		
		return "fiseObservacion";
	}
	
	@ResourceMapping("busquedaObservaciones")
  	public void busqueda(ResourceRequest request,ResourceResponse response,
  			@ModelAttribute("fiseObservacionBean")FiseObservacionBean p){
		
		try{
			response.setContentType("application/json");	       
	        	
			String data = "";
		    
			String id = p.getIdBusq(); 
			String descripcion = p.getDescripcionBusq();			
  			logger.info("id "+ id);  			
  			logger.info("descripcion "+ descripcion);  			
 		   
  			List<FiseObservacion> listaObs = fiseObservacionGartService.buscarFiseObservacion(id, descripcion); 	 			
  			logger.info("tamaño de la lista observaciones   :"+listaObs.size());   			 			
  			  			
  			/*fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_PERIODO_ENVIO, 
  					"PERIODOS DE ENVIO", //title
  					"PERIODO", //nombre hoja
  					listaPeridoEnvioExportExel);*/
  			data = toStringListJSON(listaObs);  		
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
	
	private String toStringListJSON(List<FiseObservacion> lista) {
		Serializer serializer = new JsonSerializer();
		Object result = serializer.serialize(lista);
		String data = String.valueOf(result);
		return data;
	}

	@ResourceMapping("grabarObservacion")
	public void grabarObservaciones(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("fiseObservacionBean")FiseObservacionBean p) { 
		JSONObject jsonObj = new JSONObject();
		try {		
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);				
			
			logger.info("Entrando a grabar un registro"); 			
			logger.info("ID:  "+ p.getId()); 
			logger.info("Descripcion:  "+ p.getDescripcion());				
				
			p.setUsuario(themeDisplay.getUser().getLogin());
			p.setTerminal(themeDisplay.getUser().getLoginIP());		
			
			logger.info("Enviando el formulario al service");		
			
			String valor = fiseObservacionGartService.insertarDatosFiseObservacion(p);
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
			logger.error("Error al guardar los datos en observacion: "+e.getMessage());
		} 	
	}	
	
	
	@ResourceMapping("actualizarObservacion")
	public void actualizarObservaciones(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("fiseObservacionBean")FiseObservacionBean p) { 
		JSONObject jsonObj = new JSONObject();
		try {		
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);	
			
			p.setUsuario(themeDisplay.getUser().getLogin());
			p.setTerminal(themeDisplay.getUser().getLoginIP());	
			
			logger.info("Entrando a actualizar un registro"); 	
			logger.info("ID:  "+ p.getId()); 
			logger.info("Descripcion:  "+ p.getDescripcion());		
			
			String valor = fiseObservacionGartService.actualizarDatosFiseObservacion(p);
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
			logger.error("Error al guardar los datos en observacion: "+e.getMessage());
		} 	
	}
	
	
	@ResourceMapping("editarViewObservacion")
	public void editarObservacion(ModelMap model,ResourceRequest request,ResourceResponse response,
			@ModelAttribute("fiseObservacionBean")FiseObservacionBean p) { 		
		try {	
			String data;			
			logger.info("id para editar y visualizar "+p.getId());					
			
			p= fiseObservacionGartService.buscarFiseObsEditar(p.getId());
			
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
	
	private String toStringJSON(FiseObservacionBean p) {
		Serializer serializer = new JsonSerializer();
		Object result = serializer.serialize(p);
		String data = String.valueOf(result);
		return data;
	}
	
	@ResourceMapping("eliminarObservacion")
	public void eliminarObservacion(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("fiseObservacionBean")FiseObservacionBean p) { 	
		
		JSONObject jsonObj = new JSONObject();
		try {			
			logger.info("Entrando a eliminar un registro observacion"); 			
			logger.info("id:  "+ p.getId());      			
			logger.info("Enviando el formulario al service"); 
			String valor = fiseObservacionGartService.eliminarDatosFiseObservacion(p.getId());
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
			logger.error("Error al eliminar los datos de observacion: "+e.getMessage());
		}  	
		
	}		
	

}
