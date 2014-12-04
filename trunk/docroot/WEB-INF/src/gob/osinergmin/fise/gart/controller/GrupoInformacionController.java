package gob.osinergmin.fise.gart.controller;



import gob.osinergmin.fise.bean.GrupoInformacionBean;
import gob.osinergmin.fise.domain.FiseGrupoInformacion;
import gob.osinergmin.fise.gart.service.FiseGrupoInformacionGartService;

import java.io.PrintWriter;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

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


@Controller("grupoInformacionController")
@RequestMapping("VIEW")
public class GrupoInformacionController {
	
	Logger logger = Logger.getLogger(GrupoInformacionController.class);
	
	
	@Autowired
	@Qualifier("fiseGrupoInformacionGartServiceImpl")
	FiseGrupoInformacionGartService grupoInformacionService;

		
	
	@ModelAttribute("grupoInformacionBean")
    public GrupoInformacionBean listFiseGrupoInformacion() {
		GrupoInformacionBean comman  = new GrupoInformacionBean();
        return comman;	        
    }

	
	@RequestMapping
	public String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse,
			     @ModelAttribute("grupoInformacionBean")GrupoInformacionBean g){
        try {	
        	g.setIdGrupoInf(""); 
    		g.setDescripcion("");   		
    		model.addAttribute("model", g);    		
		} catch (Exception e) {
			logger.info("Ocurrio un errror al caragar la pagina grupo de informacion"); 
			e.printStackTrace();
		}		
		return "grupoInformacion";
	}
	
	@ResourceMapping("busquedaGrupoInformacion")
  	public void busqueda(ResourceRequest request,ResourceResponse response,
  			@ModelAttribute("grupoInformacionBean")GrupoInformacionBean g){
		
		try{
			response.setContentType("application/json");	       
	        	
			String data = "";		
			String descripcion = g.getDescripcionBusq();
			String tipo = g.getTipoBusq();
			String estado = g.getEstadoBusq();  				
  			logger.info("descripcion "+ descripcion);  
  			logger.info("tipo "+ tipo);  		
  			logger.info("estado "+ estado);  		
 		   
  			List<FiseGrupoInformacion> listaGrupoInf = grupoInformacionService.buscarGrupoInformacion(descripcion,
  					tipo, Integer.valueOf(estado)); 	 			
  			logger.info("tamaño de la lista grupo informacion   :"+listaGrupoInf.size());   			 			
  			  			
  			/*fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_PERIODO_ENVIO, 
  					"PERIODOS DE ENVIO", //title
  					"PERIODO", //nombre hoja
  					listaPeridoEnvioExportExel);*/
  			
  			data = toStringListJSON(listaGrupoInf);  		
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
	
	private String toStringListJSON(List<FiseGrupoInformacion> lista) {
		Serializer serializer = new JsonSerializer();
		Object result = serializer.serialize(lista);
		String data = String.valueOf(result);
		return data;
	}

	@ResourceMapping("grabarGrupoInformacion")
	public void grabarGrupoInf(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("grupoInformacionBean")GrupoInformacionBean g) { 
		JSONObject jsonObj = new JSONObject();
		try {		
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);				
			
			logger.info("Entrando a grabar un registro"); 			
			logger.info("Descripcion:  "+ g.getDescripcion());
			logger.info("tipo:  "+ g.getTipo());
			logger.info("estado:  "+ g.getEstado());
			
			g.setUsuario(themeDisplay.getUser().getLogin());
			g.setTerminal(themeDisplay.getUser().getLoginIP());		
			
			logger.info("Enviando el formulario al service");		
			
			String valor = grupoInformacionService.insertarDatosGrupoInf(g);
			logger.info("valor de la transaccion al insertar:  "+valor); 
			if(!valor.equals("0")){ 
				jsonObj.put("resultado", "OK");	
				jsonObj.put("IdGrupoInf", valor);	
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
			logger.error("Error al guardar los datos en grupo informacion: "+e.getMessage());
		} 	
	}	
	
	
	@ResourceMapping("actualizarGrupoInformacion")
	public void actualizarGrupoInf(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("grupoInformacionBean")GrupoInformacionBean g) { 
		JSONObject jsonObj = new JSONObject();
		try {		
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);	
			
			g.setUsuario(themeDisplay.getUser().getLogin());
			g.setTerminal(themeDisplay.getUser().getLoginIP());	
			
			logger.info("Entrando a actualizar un registro"); 
			logger.info("Id:  "+ g.getIdGrupoInf());
			logger.info("Descripcion:  "+ g.getDescripcion());
			logger.info("tipo:  "+ g.getTipo());
			logger.info("estado:  "+ g.getEstado());
			
			String valor = grupoInformacionService.actualizarDatosGrupoInf(g);
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
			logger.error("Error al actualizar los datos en grupo informacion: "+e.getMessage());
		} 	
	}
	
	
	@ResourceMapping("editarViewGrupoInformacion")
	public void editarGrupoInf(ModelMap model,ResourceRequest request,ResourceResponse response,
			@ModelAttribute("grupoInformacionBean")GrupoInformacionBean g) { 		
		try {	
			String data;			
			logger.info("id para editar y visualizar "+g.getIdGrupoInf());					
			
			g= grupoInformacionService.buscarGrupoInfEditar(Long.valueOf(g.getIdGrupoInf())); 
			
			data = toStringJSON(g);						
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
	
	private String toStringJSON(GrupoInformacionBean g) {
		Serializer serializer = new JsonSerializer();
		Object result = serializer.serialize(g);
		String data = String.valueOf(result);
		return data;
	}
	
	@ResourceMapping("eliminarGrupoInformacion")
	public void eliminarGrupoInf(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("grupoInformacionBean")GrupoInformacionBean g) { 	
		
		JSONObject jsonObj = new JSONObject();
		try {
            ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);				
			logger.info("Entrando a eliminar un registro grupo inf."); 			
			logger.info("id:  "+ g.getIdGrupoInf());      			
			logger.info("Enviando el formulario al service"); 
			String valor = grupoInformacionService.eliminarDatosGrupoInf(Long.valueOf(g.getIdGrupoInf()),
					themeDisplay.getUser().getLogin(),themeDisplay.getUser().getLoginIP()); 
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
			logger.error("Error al eliminar los datos de grupo inf.: "+e.getMessage());
		}	
	}		
	

}
