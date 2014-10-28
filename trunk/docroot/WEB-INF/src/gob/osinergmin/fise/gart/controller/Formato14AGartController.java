package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.Formato14ACBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.FiseFormato14AC;
import gob.osinergmin.fise.gart.json.Formato14AGartJSON;
import gob.osinergmin.fise.gart.service.Formato14AGartService;
import gob.osinergmin.fise.util.FormatoUtil;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.PortalUtil;

@Controller("formato14AGartController")
@RequestMapping("VIEW")
public class Formato14AGartController {
	
private static final Log logger=LogFactoryUtil.getLog(Formato14AGartController.class);
	
	@Autowired
	@Qualifier("fiseUtil")
	FiseUtil fiseUtil;
	
	@Autowired
	@Qualifier("formato14AGartServiceImpl")
	Formato14AGartService formato14Service;
	
	List<FiseFormato14AC> listaFormato;
	Map<String, String> mapaEmpresa;

	/*@ModelAttribute("formato14ACBean")
	public Formato14ACBean inicializar(){
		Formato14ACBean command=new Formato14ACBean();
		command.setListaMes(fiseUtil.getMapaMeses());
		command.setAnioDesde(fiseUtil.obtenerNroAnioFechaActual());
		command.setMesDesde( String.valueOf(Integer.parseInt(fiseUtil.obtenerNroMesFechaActual())-1));
		command.setAnioHasta(fiseUtil.obtenerNroAnioFechaActual());
		command.setMesHasta(fiseUtil.obtenerNroMesFechaActual());
		command.setEtapaB(FiseConstants.ETAPA_SOLICITUD);
		logger.info("INICIALIZACION formato14ACBean");
		return command;
	}*/
	
	@RequestMapping
	public String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse,@ModelAttribute("formato14ACBean")Formato14ACBean command){

		command.setListaMes(fiseUtil.getMapaMeses());
		command.setAnioDesde(fiseUtil.obtenerNroAnioFechaActual());
		command.setMesDesde( String.valueOf(Integer.parseInt(fiseUtil.obtenerNroMesFechaActual())-1));
		command.setAnioHasta(fiseUtil.obtenerNroAnioFechaActual());
		command.setMesHasta(fiseUtil.obtenerNroMesFechaActual());
		command.setEtapaB(FiseConstants.ETAPA_SOLICITUD);
		
		if(command.getListaEmpresas()==null){
			command.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));
		}
		command.setAdmin(fiseUtil.esAdministrador(renderRequest));
		mapaEmpresa = fiseUtil.getMapaEmpresa();
		
		return "formato14A";
	}
	
	@ResourceMapping("busqueda")
  	public void busqueda(ResourceRequest request,ResourceResponse response,@ModelAttribute("formato14ACBean")Formato14ACBean command){
		
		try{
			response.setContentType("application/json");	
				
			HttpServletRequest req = PortalUtil.getHttpServletRequest(request);	        
	        HttpSession session = req.getSession();
	        	
		    JSONArray jsonArray = new JSONArray();
			String codEmpresa = command.getCodEmpresaB();
			String anioDesde = command.getAnioDesde();
			String mesDesde = command.getMesDesde();
			String anioHasta = command.getAnioHasta();
			String mesHasta = command.getMesHasta();
			String etapa = command.getEtapaB();
			logger.info("valores "+ codEmpresa);
  			logger.info("valores "+ anioDesde);
  			logger.info("valores "+ mesDesde);
  			logger.info("valores "+ anioHasta);
  			logger.info("valores "+ mesHasta);
  			logger.info("valores "+ etapa);
  			logger.info("admin "+ command.isAdmin());
  			
  			listaFormato = formato14Service.buscarFormato14AC(
  					(codEmpresa!=null&&codEmpresa!="")?FormatoUtil.rellenaDerecha(codEmpresa, ' ', 4):"", 
  					(anioDesde!=null&&anioDesde!="")?Long.parseLong(anioDesde):0, 
  					(mesDesde!=null&&mesDesde!="")?Long.parseLong(mesDesde):0, 
  					(anioHasta!=null&&anioHasta!="")?Long.parseLong(anioHasta):0, 
  					(mesHasta!=null&&mesHasta!="")?Long.parseLong(mesHasta):0, 
  					(etapa!=null&&etapa!="")?etapa:""
  					);
  			logger.info("arreglo lista:"+listaFormato);
  			for(FiseFormato14AC fiseFormato14AC : listaFormato){
  				fiseFormato14AC.setDescEmpresa(mapaEmpresa.get(fiseFormato14AC.getId().getCodEmpresa()));
  				fiseFormato14AC.setDescMesPresentacion(fiseUtil.getMapaMeses().get(fiseFormato14AC.getId().getMesPresentacion()));
  				jsonArray.put(new Formato14AGartJSON().asJSONObject(fiseFormato14AC,""));
  			}
  			
  			fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_FORMATO_14A, FiseConstants.NOMBRE_EXCEL_FORMATO14A, FiseConstants.NOMBRE_HOJA_FORMATO14A, listaFormato);
  			
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
	
	//metodo crear nuevo
	@ResourceMapping("formularioNuevo")
  	public void formularioNuevo(ResourceRequest request,ResourceResponse response,@ModelAttribute("formato14ACBean")Formato14ACBean command){
		
	}
		
}
