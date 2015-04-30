package gob.osinergmin.fise.gart.controller;



import gob.osinergmin.fise.bean.TipDocReferenciaBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.FiseTipDocRef;
import gob.osinergmin.fise.gart.service.FiseTipDocRefGartService;
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


@Controller("tipoDocReferenciaController")
@RequestMapping("VIEW")
public class TipoDocReferenciaController {
	
	Logger logger = Logger.getLogger(TipoDocReferenciaController.class);
	
	
	@Autowired
	@Qualifier("fiseTipDocRefGartServiceImpl")
	private FiseTipDocRefGartService fiseTipDocRefService;

	@Autowired
	@Qualifier("fiseUtil")
	private FiseUtil fiseUtil;
	
	
	@ModelAttribute("tipDocReferenciaBean")
    public TipDocReferenciaBean listFiseTipDocReferencia() {
		TipDocReferenciaBean comman  = new TipDocReferenciaBean();
        return comman;	        
    }

	
	@RequestMapping
	public String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse,
			     @ModelAttribute("tipDocReferenciaBean")TipDocReferenciaBean p){
        try {        	   		
    		p.setIdBusq(""); 
    		p.setDescripcion("");   		
    		model.addAttribute("model", p);    		
		} catch (Exception e) {
			logger.info("Ocurrio un errror al cargar la pagina tipo de doc de referencia"); 
			e.printStackTrace();
		}		
		return "fiseTipoDocReferencia";
	}
	
	@ResourceMapping("busquedaTipoDocReferencia")
  	public void busqueda(ResourceRequest request,ResourceResponse response,
  			@ModelAttribute("tipDocReferenciaBean")TipDocReferenciaBean p){
		
		try{
			HttpServletRequest req = PortalUtil.getHttpServletRequest(request);	        
	        HttpSession session = req.getSession();	  
	        
			response.setContentType("application/json");	       
	        	
			String data = "";
		    
			String id = p.getIdBusq(); 
			String descripcion = p.getDescripcionBusq();			
  			logger.info("id "+ id);  			
  			logger.info("descripcion "+ descripcion);  			
 		   
  			List<FiseTipDocRef> listaDocRef = fiseTipDocRefService.buscarFiseTipDocRef(id, descripcion); 	 			
  			logger.info("tamaño de la lista tipo de doc de referencia   :"+listaDocRef.size());   			 			
  			  			
  			fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_DOC_REFERENCIA_EXPORT_EXEL, 
  					"TIPO_DOC_REFERENCIA", //title
  					"TIPO_DOC_REFERENCIA", //nombre hoja
  					listaDocRef);
  			
  			
  			data = toStringListJSON(listaDocRef);  		
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
	
	private String toStringListJSON(List<FiseTipDocRef> lista) {
		Serializer serializer = new JsonSerializer();
		Object result = serializer.serialize(lista);
		String data = String.valueOf(result);
		return data;
	}
	
	@ResourceMapping("grabarTipoDocReferencia")
	public void grabarObservaciones(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("tipDocReferenciaBean")TipDocReferenciaBean p) { 
		JSONObject jsonObj = new JSONObject();
		try {		
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);				
			
			logger.info("Entrando a grabar un registro"); 			
			logger.info("ID:  "+ p.getId()); 
			logger.info("Descripcion:  "+ p.getDescripcion());				
				
			p.setUsuario(themeDisplay.getUser().getLogin());
			p.setTerminal(themeDisplay.getUser().getLoginIP());		
			
			logger.info("Enviando el formulario al service");		
			
			String valor = fiseTipDocRefService.insertarDatosFiseTipDocRef(p);
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
			logger.error("Error al guardar los datos en tipo de doc de referencia: "+e.getMessage());
		} 	
	}	
	
	
	@ResourceMapping("actualizarTipoDocReferencia")
	public void actualizarObservaciones(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("tipDocReferenciaBean")TipDocReferenciaBean p) { 
		JSONObject jsonObj = new JSONObject();
		try {		
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);	
			
			p.setUsuario(themeDisplay.getUser().getLogin());
			p.setTerminal(themeDisplay.getUser().getLoginIP());	
			
			logger.info("Entrando a actualizar un registro"); 	
			logger.info("ID:  "+ p.getId()); 
			logger.info("Descripcion:  "+ p.getDescripcion());		
			
			String valor = fiseTipDocRefService.actualizarDatosFiseTipDocRef(p);
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
			logger.error("Error al guardar los datos en tipo de doc de referencia: "+e.getMessage());
		} 	
	}
	
	
	@ResourceMapping("editarViewTipoDocReferencia")
	public void editarObservacion(ModelMap model,ResourceRequest request,ResourceResponse response,
			@ModelAttribute("tipDocReferenciaBean")TipDocReferenciaBean p) { 		
		try {	
			String data;		
			
			String codigoId = request.getParameter("codigo");
			if(FormatoUtil.isBlank(p.getId())){ 
				p.setId(codigoId); 
			}
			logger.info("id para editar y visualizar "+p.getId());					
			
			p= fiseTipDocRefService.buscarFiseTipDocRefEditar(p.getId());
			
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
	
	private String toStringJSON(TipDocReferenciaBean p) {
		Serializer serializer = new JsonSerializer();
		Object result = serializer.serialize(p);
		String data = String.valueOf(result);
		return data;
	}
	
	@ResourceMapping("eliminarTipoDocReferencia")
	public void eliminarObservacion(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("tipDocReferenciaBean")TipDocReferenciaBean p) { 	
		
		JSONObject jsonObj = new JSONObject();
		try {			
			logger.info("Entrando a eliminar un registro de tipo doc de referencia"); 			
			logger.info("id:  "+ p.getId());      			
			logger.info("Enviando el formulario al service"); 
			String valor = fiseTipDocRefService.eliminarDatosFiseObservacion(p.getId());
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
			logger.error("Error al eliminar los datos de tipo doc de referencia: "+e.getMessage());
		}  	
		
	}		
	

}
