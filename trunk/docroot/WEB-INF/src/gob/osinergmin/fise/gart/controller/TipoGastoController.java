package gob.osinergmin.fise.gart.controller;



import gob.osinergmin.fise.bean.TipoGastoBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.FiseTipGasto;
import gob.osinergmin.fise.gart.service.FiseTipGastoGartService;
import gob.osinergmin.fise.util.FormatoUtil;

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


@Controller("tipoGastoController")
@RequestMapping("VIEW")
public class TipoGastoController {
	
	Logger logger = Logger.getLogger(TipoGastoController.class);
	
	
	@Autowired
	@Qualifier("fiseTipGastoGartServiceImpl")
	private FiseTipGastoGartService fiseTipGastoService;

	@Autowired
	@Qualifier("fiseUtil")
	private FiseUtil fiseUtil;
	
	
		
	
	@ModelAttribute("tipoGastoBean")
    public TipoGastoBean listFiseTipoGastoBean() {
		TipoGastoBean comman  = new TipoGastoBean();
        return comman;	        
    }

	
	@RequestMapping
	public String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse,
			     @ModelAttribute("tipoGastoBean")TipoGastoBean p){
        try {        	   		
    		p.setIdBusq(""); 
    		p.setDescripcion("");   		
    		model.addAttribute("model", p);    		
		} catch (Exception e) {
			logger.info("Ocurrio un errror al caragar la pagina de tipo de gastos"); 
			e.printStackTrace();
		}		
		return "fiseTipoGasto";
	}
	
	@ResourceMapping("busquedaTiposGasto")
  	public void busqueda(ResourceRequest request,ResourceResponse response,
  			@ModelAttribute("tipoGastoBean")TipoGastoBean p){
		
		try{
			HttpServletRequest req = PortalUtil.getHttpServletRequest(request);	        
	        HttpSession session = req.getSession();	  
	        
			response.setContentType("application/json");	       
	        	
			String data = "";
		    
			String id = p.getIdBusq(); 
			String descripcion = p.getDescripcionBusq();			
  			logger.info("id "+ id);  			
  			logger.info("descripcion "+ descripcion);  			
 		   
  			List<FiseTipGasto> listaGasto = fiseTipGastoService.buscarFiseTipGasto(id, descripcion); 	 			
  			logger.info("tamaño de la lista de tipos de gasto   :"+listaGasto.size());   			 			
  			  			
  			fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_GASTO_EXPORT_EXEL, 
  					"TIPO_GASTO", //title
  					"TIPO_GASTO", //nombre hoja
  					listaGasto);
  			
  			
  			data = toStringListJSON(listaGasto);  		
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
	
	private String toStringListJSON(List<FiseTipGasto> lista) {
		Serializer serializer = new JsonSerializer();
		Object result = serializer.serialize(lista);
		String data = String.valueOf(result);
		return data;
	}
	
	

	@ResourceMapping("grabarTipoGastos")
	public void grabarTiposGasto(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("tipoGastoBean")TipoGastoBean p) { 
		JSONObject jsonObj = new JSONObject();
		try {		
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);				
			
			logger.info("Entrando a grabar un registro"); 			
			logger.info("ID:  "+ p.getId()); 
			logger.info("Descripcion:  "+ p.getDescripcion());				
				
			p.setUsuario(themeDisplay.getUser().getLogin());
			p.setTerminal(themeDisplay.getUser().getLoginIP());		
			
			logger.info("Enviando el formulario al service");		
			
			String valor = fiseTipGastoService.insertarDatosFiseTipGasto(p);
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
			logger.error("Error al guardar los datos en tipos de gasto: "+e.getMessage());
		} 	
	}	
	
	
	@ResourceMapping("actualizarTipoGastos")
	public void actualizarTiposGasto(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("tipoGastoBean")TipoGastoBean p) { 
		JSONObject jsonObj = new JSONObject();
		try {		
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);	
			
			p.setUsuario(themeDisplay.getUser().getLogin());
			p.setTerminal(themeDisplay.getUser().getLoginIP());	
			
			logger.info("Entrando a actualizar un registro"); 	
			logger.info("ID:  "+ p.getId()); 
			logger.info("Descripcion:  "+ p.getDescripcion());		
			
			String valor = fiseTipGastoService.actualizarDatosFiseTipGasto(p);
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
			logger.error("Error al guardar los datos en tipos de gastos: "+e.getMessage());
		} 	
	}
	
	
	@ResourceMapping("editarViewTipoGasto")
	public void editarTiposGasto(ModelMap model,ResourceRequest request,ResourceResponse response,
			@ModelAttribute("tipoGastoBean")TipoGastoBean p) { 		
		try {	
			String data;			
			logger.info("id para editar y visualizar "+p.getId());	
			String codigoId = request.getParameter("codigo");
			if(FormatoUtil.isBlank(p.getId())){ 
				p.setId(codigoId); 
			}			
			p= fiseTipGastoService.buscarFiseTipGastoEditar(p.getId());
			
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
	
	private String toStringJSON(TipoGastoBean p) {
		Serializer serializer = new JsonSerializer();
		Object result = serializer.serialize(p);
		String data = String.valueOf(result);
		return data;
	}
	
	@ResourceMapping("eliminarTipoGasto")
	public void eliminarTiposGastos(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("tipoGastoBean")TipoGastoBean p) { 	
		
		JSONObject jsonObj = new JSONObject();
		try {			
			logger.info("Entrando a eliminar un registro tipo de gasto"); 			
			logger.info("id:  "+ p.getId());      			
			logger.info("Enviando el formulario al service"); 
			String valor = fiseTipGastoService.eliminarDatosFiseTipGasto(p.getId());
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
			logger.error("Error al eliminar los datos de tipos de gasto: "+e.getMessage());
		}  	
		
	}		
	

}
