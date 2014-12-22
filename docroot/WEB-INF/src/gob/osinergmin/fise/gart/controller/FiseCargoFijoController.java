package gob.osinergmin.fise.gart.controller;



import gob.osinergmin.fise.bean.FiseCargoFijoBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
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
			HttpServletRequest req = PortalUtil.getHttpServletRequest(request);	        
	        HttpSession session = req.getSession();	 
	        
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
 		   
  			List<FiseCargoFijoBean> lista = fiseCargoFijoService.buscarFiseCargoFijo(codEmpresa,
  					anioRep ,mesRep );
  			
  			logger.info("tamaño de la lista cargo fijos   :"+lista.size());  
  			
  			List<FiseCargoFijoBean> listaCargoExel = new ArrayList<FiseCargoFijoBean>();
  			
  			
  			for(FiseCargoFijoBean cargo : lista){ 	
  				String codEmpreCompleta = FormatoUtil.rellenaDerecha(cargo.getCodigoEmpresa(), ' ', 4);
  				cargo.setDesEmpresa(mapaEmpresa.get(codEmpreCompleta)); 
  				cargo.setDesMesRep(fiseUtil.getMapaMeses().get(Long.valueOf(cargo.getMesReporte()))); 				
  				listaCargoExel.add(cargo);
  			}   			
  			
  		    fiseUtil.configuracionExportarExcel(session, FiseConstants.CARGO_FIJO_EXPORT_EXEL, 
  					"CARGOS FIJOS", //title
  					"CARGO FIJO", //nombre hoja
  					listaCargoExel);
  			
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
	
	private String toStringListJSON(List<FiseCargoFijoBean> lista) {
		Serializer serializer = new JsonSerializer();
		Object result = serializer.serialize(lista);
		String data = String.valueOf(result);
		return data;
	}
	
	
	@ResourceMapping("grabarCargosFijos")
	public void grabarCargosFijos(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("fiseCargoFijoBean")FiseCargoFijoBean c) { 
		JSONObject jsonObj = new JSONObject();
		try {		
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);				
			
			logger.info("Entrando a grabar un registro"); 
			
			String mes = request.getParameter("codigoMes");
			String anio = request.getParameter("anioRep");
			String empresa = request.getParameter("codigoEmp");
			c.setCodigoEmpresa(empresa);
			c.setAnioReporte(anio);
			c.setMesReporte(mes); 
			
			logger.info("codEmpresa:  "+ c.getCodigoEmpresa()); 
			logger.info("anio:  "+ c.getAnioReporte());		
			logger.info("mes:  "+ c.getMesReporte());
			logger.info("numero de agentes:  "+ c.getNumAgente());
		
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
	public void actualizarCargosFijos(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("fiseCargoFijoBean")FiseCargoFijoBean c) { 
		JSONObject jsonObj = new JSONObject();
		try {		
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);	
			
			c.setUsuario(themeDisplay.getUser().getLogin());
			c.setTerminal(themeDisplay.getUser().getLoginIP());	
			
			logger.info("Entrando a actualizar un registro"); 	
			
			String mes = request.getParameter("codigoMes");
			String anio = request.getParameter("anioRep");
			String empresa = request.getParameter("codigoEmp");
			c.setCodigoEmpresa(empresa);
			c.setAnioReporte(anio);
			c.setMesReporte(mes); 
			
			logger.info("codEmpresa:  "+ c.getCodigoEmpresa()); 
			logger.info("anio:  "+ c.getAnioReporte());		
			logger.info("mes:  "+ c.getMesReporte());
			logger.info("numero de agentes:  "+ c.getNumAgente());
			
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
	public void editarCargosFijos(ModelMap model,ResourceRequest request,ResourceResponse response,
			@ModelAttribute("fiseCargoFijoBean")FiseCargoFijoBean c) { 		
		try {	
			String data;			
			logger.info("entrando a editar o visualizar un registro");	
					
			String mes = request.getParameter("codigoMes");
			String anio = request.getParameter("anioRep");
			String empresa = request.getParameter("codigoEmp");
			c.setCodigoEmpresa(empresa);
			c.setAnioReporte(anio);
			c.setMesReporte(mes); 
			
			logger.info("codEmpresa:  "+ c.getCodigoEmpresa()); 
			logger.info("anio:  "+ c.getAnioReporte());		
			logger.info("mes:  "+ c.getMesReporte());		
			
			c= fiseCargoFijoService.buscarFiseCargoFijoEditar(c.getCodigoEmpresa(), c.getAnioReporte(), c.getMesReporte());
			 
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
			
			String mes = request.getParameter("codigoMes");
			String anio = request.getParameter("anioRep");
			String empresa = request.getParameter("codigoEmp");
			c.setCodigoEmpresa(empresa);
			c.setAnioReporte(anio);
			c.setMesReporte(mes); 
			
			logger.info("codEmpresa:  "+ c.getCodigoEmpresa()); 
			logger.info("anio:  "+ c.getAnioReporte());		
			logger.info("mes:  "+ c.getMesReporte());
			
			logger.info("Enviando el formulario al service"); 
			
			String valor = fiseCargoFijoService.eliminarDatosFiseCargoFijo(c.getCodigoEmpresa(),
					c.getAnioReporte(), c.getMesReporte(), usuario, terminal);
			
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
