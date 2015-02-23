package gob.osinergmin.fise.gart.controller;



import gob.osinergmin.fise.bean.FiseParametroBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.FiseParametro;
import gob.osinergmin.fise.gart.json.ParametroJSON;
import gob.osinergmin.fise.gart.service.FiseParametroGartService;

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
import org.json.JSONArray;
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


@Controller("fiseParametrosController")
@RequestMapping("VIEW")
public class FiseParametrosController {
	
	Logger logger = Logger.getLogger(FiseParametrosController.class);
	
	
	@Autowired
	@Qualifier("fiseParametroGartServiceImpl")
	FiseParametroGartService fiseParametroGartService;

	@Autowired
	@Qualifier("fiseUtil")
	private FiseUtil fiseUtil;
	
	
	
	
	
	@ModelAttribute("fiseParametroBean")
    public FiseParametroBean listFiseFiseParametro() {
		FiseParametroBean comman  = new FiseParametroBean();
        return comman;	        
    }

	
	@RequestMapping
	public String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse,
			     @ModelAttribute("fiseParametroBean")FiseParametroBean p){
        try {        	   		
    		p.setCodigoBusq(""); 
    		p.setNombreBusq("");   		
    		model.addAttribute("model", p);    		
		} catch (Exception e) {
			logger.info("Ocurrio un errror al caragar la pagina Parametros"); 
			e.printStackTrace();
		}		
		return "fiseParametros";
	}
	
	@ResourceMapping("busquedaParametros")
  	public void busqueda(ResourceRequest request,ResourceResponse response,
  			@ModelAttribute("fiseParametroBean")FiseParametroBean p){
		
		try{
			HttpServletRequest req = PortalUtil.getHttpServletRequest(request);	        
	        HttpSession session = req.getSession();	  
	        
	        JSONArray jsonArray = new JSONArray();
	        
			response.setContentType("application/json");	       
	        	
			//--String data = "";
		    
			String codigo = p.getCodigoBusq(); 
			String nombre = p.getNombreBusq();			
  			logger.info("codigo "+ codigo);  			
  			logger.info("nombre "+ nombre);  			
 		   
  			List<FiseParametro> listaParam = fiseParametroGartService.buscarFiseParametro(codigo, nombre); 	 			
  			logger.info("tamaño de la lista Parametros   :"+listaParam.size());   			 			
  			
  			for (FiseParametro param : listaParam) {
  				jsonArray.put(new ParametroJSON().asJSONObject(param));
			}
  			
  			fiseUtil.configuracionExportarExcel(session, FiseConstants.OBSERVACIONES_EXPORT_EXEL, 
  					"Parametros", //title
  					"Parametros", //nombre hoja
  					listaParam);
  			
  			
  			
  			
  			//--data = toStringListJSON(listaParam);  		
  			
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
	
	/*private String toStringListJSON(List<FiseParametro> lista) {
		Serializer serializer = new JsonSerializer();
		Object result = serializer.serialize(lista);
		String data = String.valueOf(result);
		return data;
	}*/
	
	/*@ResourceMapping("nuevoRegistroParametro")
	public void nuevoRegistroParametro(ModelMap model,ResourceRequest request,ResourceResponse response,
			@ModelAttribute("fiseParametroBean")FiseParametroBean p) { 		
		try {	
			String codigo ="0";
			
			JSONObject jsonObj = new JSONObject();
			
			logger.info("entrando a nuevo registro de Parametro ");					
			
			codigo = fiseParametroGartService.obtenerIdParametro();
			
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
	}*/
	

	@ResourceMapping("grabarParametro")
	public void grabarParametros(ModelMap model, ResourceRequest request,ResourceResponse response, @ModelAttribute("fiseParametroBean")FiseParametroBean p) { 
		JSONObject jsonObj = new JSONObject();
		try {		
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);				
			
			logger.info("Entrando a grabar un registro"); 			
			logger.info("codigo:  "+ p.getCodigo()); 
			logger.info("nombre:  "+ p.getNombre());				
				
			String codigo = request.getParameter("codigo");
			String nombre = request.getParameter("nombre");
			String valorParametro = request.getParameter("valor");
			String orden = request.getParameter("orden");
			
			p.setCodigo(codigo);
			p.setNombre(nombre);
			p.setValor(valorParametro);
			p.setOrden(orden);
			
			p.setUsuario(themeDisplay.getUser().getLogin());
			p.setTerminal(themeDisplay.getUser().getLoginIP());		
			
			logger.info("Enviando el formulario al service");		
			
			String valor = fiseParametroGartService.insertarDatosFiseParametro(p);
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
			logger.error("Error al guardar los datos en Parametro: "+e.getMessage());
		} 	
	}	
	
	
	@ResourceMapping("actualizarParametro")
	public void actualizarParametros(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("fiseParametroBean")FiseParametroBean p) { 
		JSONObject jsonObj = new JSONObject();
		try {		
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);	
			
			p.setUsuario(themeDisplay.getUser().getLogin());
			p.setTerminal(themeDisplay.getUser().getLoginIP());	
			
			logger.info("Entrando a actualizar un registro"); 	
			logger.info("codigo:  "+ p.getCodigo()); 
			logger.info("nombre:  "+ p.getNombre());		
			
			String codigo = request.getParameter("codigo");
			String nombre = request.getParameter("nombre");
			String valorParametro = request.getParameter("valor");
			String orden = request.getParameter("orden");
			
			//p.setCodigo(codigo);
			p.setNombre(nombre);
			p.setValor(valorParametro);
			p.setOrden(orden);
			
			String valor = fiseParametroGartService.actualizarDatosFiseParametro(p);
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
			logger.error("Error al guardar los datos en Parametro: "+e.getMessage());
		} 	
	}
	
	
	@ResourceMapping("editarViewParametro")
	public void editarParametro(ModelMap model,ResourceRequest request,ResourceResponse response,
			@ModelAttribute("fiseParametroBean")FiseParametroBean p) { 		
		try {	
			String data;			
			logger.info("codigo para editar y visualizar "+p.getCodigo());					
			
			String codigo = request.getParameter("codigo");
			p.setCodigo(codigo);
			
			p= fiseParametroGartService.buscarFiseObsEditar(p.getCodigo());
			
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
	
	private String toStringJSON(FiseParametroBean p) {
		Serializer serializer = new JsonSerializer();
		Object result = serializer.serialize(p);
		String data = String.valueOf(result);
		return data;
	}
	
	@ResourceMapping("eliminarParametro")
	public void eliminarParametro(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("fiseParametroBean")FiseParametroBean p) { 	
		
		JSONObject jsonObj = new JSONObject();
		try {			
			logger.info("Entrando a eliminar un registro Parametro"); 			
			logger.info("codigo:  "+ p.getCodigo());      
			
			String codigo = request.getParameter("codigo");
			p.setCodigo(codigo);
			
			logger.info("Enviando el formulario al service"); 
			String valor = fiseParametroGartService.eliminarDatosFiseParametro(p.getCodigo());
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
			logger.error("Error al eliminar los datos de Parametro: "+e.getMessage());
		}  	
		
	}		
	

}
