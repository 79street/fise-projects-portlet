package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.AutorizarReenvioBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.gart.service.CommonGartService;
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


@Controller("autorizarReenvioController")
@RequestMapping("VIEW")
public class AutorizarReenvioController {
	
	Logger logger = Logger.getLogger(AutorizarReenvioController.class);
	
	@Autowired
	@Qualifier("commonGartServiceImpl")
	private CommonGartService commonService;
	
	@Autowired
	@Qualifier("fiseUtil")
	private FiseUtil fiseUtil;
	
	
	private Map<String, String> mapaEmpresa;	
	
	
	
	@ModelAttribute("autorizarReenvioBean")
    public AutorizarReenvioBean listReenvioBean() {
		AutorizarReenvioBean comman  = new AutorizarReenvioBean();
        return comman;	        
    }

	
	@RequestMapping
	public String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse,
			     @ModelAttribute("autorizarReenvioBean")AutorizarReenvioBean r){
        try {        	
        	r.setListaMes(fiseUtil.getMapaMeses());
    		r.setAnioPresBusq(fiseUtil.obtenerNroAnioFechaAnterior());
    		r.setMesPresBusq(fiseUtil.obtenerNroMesFechaAnterior());     		
    		r.setEtapaBusq(FiseConstants.ETAPA_SOLICITUD); 
    		r.setFormatoBusq("F12A"); 		
    		
    		if(r.getListaEmpresas()==null){
    			logger.info("Lista de empresa es null:  " +renderRequest); 
    			r.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));
    		}     		
    		r.setAdmin(fiseUtil.esAdministrador(renderRequest));
    		
    		mapaEmpresa = fiseUtil.getMapaEmpresa();  	   		
    		
    		model.addAttribute("model", r);
    		
		} catch (Exception e) {
			logger.info("Ocurrio un errror al caragar la pagina autorizar reenvio"); 
			e.printStackTrace();
		}		
		return "autorizar_reenvio";
	}
	
	
	@ResourceMapping("busquedaReenvio")
  	public void busqueda(ResourceRequest request,ResourceResponse response,
  			@ModelAttribute("autorizarReenvioBean")AutorizarReenvioBean r){
		
		try{
			response.setContentType("application/json");
			
			String codEmpresa = r.getCodEmpresaBusq();	 
			String etapa = r.getEtapaBusq();
			String formato = r.getFormatoBusq();
			String anioPres = r.getAnioPresBusq();
			String mesPres ="01";
			if(FormatoUtil.isNotBlank(r.getMesPresBusq())){ 
				 mesPres = r.getMesPresBusq();	
			}			
			String data ="";
			
			logger.info("codigo empresa "+ codEmpresa);
  			logger.info("anio pres "+ anioPres);
  			logger.info("mes pres "+ mesPres);  			
  			logger.info("etapa "+ etapa);
  			logger.info("formato "+ formato);
 		   
  			List<AutorizarReenvioBean> lista = commonService.buscarFormatoReenvio(codEmpresa, anioPres,
  					mesPres, formato, etapa);
  			
  			logger.info("tamaño de la lista formato reenvio   :"+lista.size());
  			
  			List<AutorizarReenvioBean> listaReenvio = new ArrayList<AutorizarReenvioBean>();
  			
  			for(AutorizarReenvioBean re : lista){  				
  				re.setDesEmpresa(mapaEmpresa.get(re.getCodEmpresa()));
  				re.setDesMes(fiseUtil.getMapaMeses().get(Long.valueOf(re.getMesPres())));   				  								
  				listaReenvio.add(re);
  			} 			
  			data = toStringListJSON(listaReenvio);
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
	
	private String toStringListJSON(List<AutorizarReenvioBean> lista) {
		Serializer serializer = new JsonSerializer();
		Object result = serializer.serialize(lista);
		String data = String.valueOf(result);
		return data;
	}
	
	
	@ResourceMapping("autorizarReenvioDefinitivo")
  	public void actualizarReenvio(ResourceRequest request,ResourceResponse response,
  			@ModelAttribute("autorizarReenvioBean")AutorizarReenvioBean r){
		
		JSONObject jsonObj = new JSONObject();
		try {		
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);				
			
			logger.info("Entrando a actualizar reenvio"); 				
			logger.info("Codigo empresa:  "+ r.getCodEmpresa()); 
			logger.info("anio pres:  "+ r.getAnioPres());	
			logger.info("mes pres:  "+ r.getMesPres());
			logger.info("formato:  "+ r.getFormato());	
			logger.info("etapa:  "+ r.getEtapa());	
			logger.info("anio ejec:  "+ r.getAnioEjec());
			logger.info("mes ejec:  "+ r.getMesEjec());
			logger.info("anio ini vige:  "+ r.getAnioIniVig());
			logger.info("anio fin vige:  "+ r.getAnioFinVig());	
			
			r.setUsuario(themeDisplay.getUser().getLogin());
			r.setTerminal(themeDisplay.getUser().getLoginIP());		
			
			String valor = commonService.actualizarFormatoReenvio(r);
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
			logger.error("Error al guardar los datos en periodo envio: "+e.getMessage());
		} 	
	}
	

}
