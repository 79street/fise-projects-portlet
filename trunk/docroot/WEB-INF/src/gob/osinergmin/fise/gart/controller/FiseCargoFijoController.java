package gob.osinergmin.fise.gart.controller;



import gob.osinergmin.fise.bean.FiseCargoFijoBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.domain.FiseMcargofijo;
import gob.osinergmin.fise.gart.service.FiseCargoFijoService;
import gob.osinergmin.fise.util.FormatoUtil;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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


@Controller("fiseCargoFijoController")
@RequestMapping("VIEW")
public class FiseCargoFijoController {
	
	Logger logger = Logger.getLogger(FiseCargoFijoController.class);
	
	
	@Autowired
	@Qualifier("fiseCargoFijoServiceImpl")
	private FiseCargoFijoService fiseCargoFijoService;

	@Autowired
	@Qualifier("fiseUtil")
	private FiseUtil fiseUtil;
	
	
	private Map<String, String> mapaEmpresa;
	
	
	
	@ModelAttribute("fiseCargoFijoBean")
    public FiseCargoFijoBean listFiseCargoFijoBean() {
		FiseCargoFijoBean comman  = new FiseCargoFijoBean();
        return comman;	        
    }

	
	@RequestMapping
	public String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse,
			     @ModelAttribute("fiseCargoFijoBean")FiseCargoFijoBean c){
        try {        	   		
        	if(c.getListaEmpresas()==null){
    			logger.info("Lista de empresa es null:  " +renderRequest); 
    			c.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));
    		}     		
    		c.setAdmin(fiseUtil.esAdministrador(renderRequest));
    		
    		mapaEmpresa = fiseUtil.getMapaEmpresa();
    		
    		c.setListaMes(fiseUtil.getMapaMeses());
    		c.setAnioRepBusq(fiseUtil.obtenerNroAnioFechaActual());
    		
    		model.addAttribute("model", c);   
    		
		} catch (Exception e) {
			logger.info("Ocurrio un errror al caragar la pagina cargos fijos"); 
			e.printStackTrace();
		}		
		return "cargoFijo";
	}	
	
	
	@ResourceMapping("busquedaCargosFijos")
  	public void busqueda(ResourceRequest request,ResourceResponse response,
  			@ModelAttribute("fiseCargoFijoBean")FiseCargoFijoBean c){
		
		try{
			response.setContentType("application/json");	       
	        	
			String data = "";
		    
			String codEmpresa =c.getCodEmpresaBusq(); 
			String anio = c.getAnioRepBusq();	
			String mes = c.getMesRepBusq();
			long mesRep = 0;
			long anioRep = 0;
			if(FormatoUtil.isNotBlank(c.getMesRepBusq())){ 
				mesRep =Long.valueOf(mes);
			}
			if(FormatoUtil.isNotBlank(c.getAnioRepBusq())){ 
				anioRep =Long.valueOf(anio);
			}
  			logger.info("codEmpresa "+ codEmpresa);  			
  			logger.info("anio "+ anio);  
  			logger.info("mes "+ mes);  
 		   
  			List<FiseMcargofijo> lista = fiseCargoFijoService.buscarFiseCargoFijo(codEmpresa,
  					anioRep ,mesRep );
  			
  			logger.info("tamaño de la lista cargo fijos   :"+lista.size());  
  			
  			List<FiseMcargofijo> listaCargoExel = new ArrayList<FiseMcargofijo>();
  			
  			for(FiseMcargofijo cargo : lista){  				
  				cargo.setDescEmpresa(mapaEmpresa.get(cargo.getId().getEmpcod()));
  				cargo.setDescMesPresentacion(fiseUtil.getMapaMeses().get(Long.valueOf(cargo.getId().getFmesrep()))); 
  				
  				if(1==cargo.getScficod()){ 
  					cargo.setDescEstado("Activo");			
  				}else{
  					cargo.setDescEstado("Inactivo");			
  				}		  								
  				listaCargoExel.add(cargo);
  			}   			
  			
  			/*fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_PERIODO_ENVIO, 
  					"PERIODOS DE ENVIO", //title
  					"PERIODO", //nombre hoja
  					listaPeridoEnvioExportExel);*/
  			
  			data = toStringListJSON(listaCargoExel);  		
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
	
	private String toStringListJSON(List<FiseMcargofijo> lista) {
		Serializer serializer = new JsonSerializer();
		Object result = serializer.serialize(lista);
		String data = String.valueOf(result);
		return data;
	}
	
	
	@ResourceMapping("grabarCargosFijos")
	public void grabarObservaciones(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("fiseCargoFijoBean")FiseCargoFijoBean c) { 
		JSONObject jsonObj = new JSONObject();
		try {		
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);				
			
			logger.info("Entrando a grabar un registro"); 			
			logger.info("codEmpresa:  "+ c.getCodEmpresa()); 
			logger.info("anio:  "+ c.getAnioRep());		
			logger.info("mes:  "+ c.getMesRep());
				
			c.setUsuario(themeDisplay.getUser().getLogin());
			c.setTerminal(themeDisplay.getUser().getLoginIP());		
			
			logger.info("Enviando el formulario al service");		
			
			String valor = fiseCargoFijoService.insertarDatosFiseCargoFijo(c);
			
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
			logger.error("Error al guardar los datos en cargos fijos: "+e.getMessage());
		} 	
	}	
	
	
	@ResourceMapping("actualizarCargosFijos")
	public void actualizarObservaciones(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("fiseCargoFijoBean")FiseCargoFijoBean c) { 
		JSONObject jsonObj = new JSONObject();
		try {		
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);	
			
			c.setUsuario(themeDisplay.getUser().getLogin());
			c.setTerminal(themeDisplay.getUser().getLoginIP());	
			
			logger.info("Entrando a actualizar un registro"); 	
			logger.info("codEmpresa:  "+ c.getCodEmpresa()); 
			logger.info("anio:  "+ c.getAnioRep());		
			logger.info("mes:  "+ c.getMesRep());
			
			String valor = fiseCargoFijoService.actualizarDatosFiseCargoFijo(c);
			
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
			logger.error("Error al guardar los datos en cargos fijos: "+e.getMessage());
		} 	
	}
	
	
	@ResourceMapping("editarViewCargosFijos")
	public void editarObservacion(ModelMap model,ResourceRequest request,ResourceResponse response,
			@ModelAttribute("fiseCargoFijoBean")FiseCargoFijoBean c) { 		
		try {	
			String data;			
			logger.info("entrando a editar o visualizar un registro");	
			
			logger.info("codEmpresa:  "+ c.getCodEmpresa()); 
			logger.info("anio:  "+ c.getAnioRep());		
			logger.info("mes:  "+ c.getMesRep());
			
			c= fiseCargoFijoService.buscarFiseCargoFijoEditar(c.getCodEmpresa(), c.getAnioRep(), c.getMesRep());
			
			data = toStringJSON(c);						
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
	
	private String toStringJSON(FiseCargoFijoBean c) {
		Serializer serializer = new JsonSerializer();
		Object result = serializer.serialize(c);
		String data = String.valueOf(result);
		return data;
	}
	
	@ResourceMapping("eliminarCargosFijos")
	public void eliminarObservacion(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("fiseCargoFijoBean")FiseCargoFijoBean c) { 	
		
		JSONObject jsonObj = new JSONObject();
		try {		
			
			logger.info("Entrando a eliminar un registro"); 	
			
            ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);	
			
			String usuario = themeDisplay.getUser().getLogin();
			String terminal = themeDisplay.getUser().getLoginIP();	
			
			
			logger.info("codEmpresa:  "+ c.getCodEmpresa()); 
			logger.info("anio:  "+ c.getAnioRep());		
			logger.info("mes:  "+ c.getMesRep());
			
			logger.info("Enviando el formulario al service"); 
			
			String valor = fiseCargoFijoService.eliminarDatosFiseCargoFijo(c.getCodEmpresa(),
					c.getAnioRep(), c.getMesRep(), usuario, terminal);
			
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
			logger.error("Error al eliminar los datos de cargos fijos: "+e.getMessage());
		}  	
		
	}		
	

}
