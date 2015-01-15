package gob.osinergmin.fise.gart.controller;



import gob.osinergmin.fise.bean.PeriodoEnvioBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.FisePeriodoEnvio;
import gob.osinergmin.fise.gart.service.FisePeriodoEnvioGartService;
import gob.osinergmin.fise.util.FechaUtil;
import gob.osinergmin.fise.util.FormatoUtil;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
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

import org.apache.commons.lang.StringUtils;
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


@Controller("periodoEnvioController")
@RequestMapping("VIEW")
public class PeriodoEnvioController {
	
	private static final Logger logger = Logger.getLogger(PeriodoEnvioController.class);
	
	@Autowired
	@Qualifier("fisePeriodoEnvioGartServiceImpl")
	private FisePeriodoEnvioGartService fisePeriodoEnvioGartService;

	@Autowired
	@Qualifier("fiseUtil")
	private FiseUtil fiseUtil;
	
	private Map<String, String> mapaEmpresa;
	
	
	
	@ModelAttribute("periodoEnvioBean")
    public PeriodoEnvioBean listFisePeriodoEnvio() {
		PeriodoEnvioBean comman  = new PeriodoEnvioBean();
        return comman;	        
    }

	
	@RequestMapping
	public String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse,
			     @ModelAttribute("periodoEnvioBean")PeriodoEnvioBean p){
        try {        	
        	p.setListaMes(fiseUtil.getMapaMeses());
    		p.setAnioDesde(fiseUtil.obtenerNroAnioFechaAnterior());
    		p.setMesDesde(fiseUtil.obtenerNroMesFechaAnterior());    		
    		p.setEtapaBusq(FiseConstants.ETAPA_SOLICITUD); 
    		p.setFormatoBusq("F12A"); 
    		p.setFlagEnvioBusq("Si"); 
    		p.setEstadoBusq("V"); 
    		
    		
    		if(p.getListaEmpresas()==null){
    			logger.info("Lista de empresa es null:  " +renderRequest); 
    			p.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));
    		}
    		
    		p.setAdmin(fiseUtil.esAdministrador(renderRequest));
    		
    		mapaEmpresa = fiseUtil.getMapaEmpresa();  	   		
    		
    		model.addAttribute("model", p);
    		
		} catch (Exception e) {
			logger.info("Ocurrio un errror al caragar la pagina del periodo envio"); 
			e.printStackTrace();
		}		
		return "bandeja_periodo_envio";
	}
	
	@ResourceMapping("busquedaPeriodoEnvio")
  	public void busqueda(ResourceRequest request,ResourceResponse response,
  			@ModelAttribute("periodoEnvioBean")PeriodoEnvioBean p){
		
		try{
			response.setContentType("application/json");	
				
			HttpServletRequest req = PortalUtil.getHttpServletRequest(request);	        
	        HttpSession session = req.getSession();	   
		    
			String codEmpresa = p.getCodEmpresaBusq();	 
			String etapa = p.getEtapaBusq();
			String formato = p.getFormatoBusq();
			String estado = p.getEstadoBusq();
			String flagEnvio = p.getFlagEnvioBusq();
			Date fechaActual= FechaUtil.obtenerFechaActual();
			
			int anioPres = 0;			
			int mesPres = 0;		
			
			String data="";
			
			if(StringUtils.isNotBlank(p.getAnioDesde())){
				anioPres = Integer.valueOf(p.getAnioDesde());	
			}			
			if(StringUtils.isNotBlank(p.getMesDesde())){
				mesPres = Integer.valueOf(p.getMesDesde());	
			}					
			logger.info("codigo empresa "+ codEmpresa);
  			logger.info("anio pres "+ anioPres);
  			logger.info("mes pres "+ mesPres);  			
  			logger.info("etapa "+ etapa);
  			logger.info("admin "+ p.isAdmin());
 		   
 			List<FisePeriodoEnvio> listaPeridoEnvio = fisePeriodoEnvioGartService.buscarFisePeriodoEnvio(codEmpresa, anioPres, 
 					mesPres, formato, etapa, flagEnvio, estado, fechaActual); 	 			
  			logger.info("tamaño de la lista formato periodo envio   :"+listaPeridoEnvio.size());
  			
  			List<FisePeriodoEnvio> listaPeridoEnvioExportExel = new ArrayList<FisePeriodoEnvio>();
  			
  			for(FisePeriodoEnvio periodo : listaPeridoEnvio){  				
  				periodo.setDescEmpresa(mapaEmpresa.get(periodo.getCodEmpresa()));
  				periodo.setDescMesPresentacion(fiseUtil.getMapaMeses().get(Long.valueOf(periodo.getMesPresentacion()))); 
  				
  				if("V".equals(periodo.getEstado())){ 
  					periodo.setDescEstado("Vigente");			
  				}else{
  					periodo.setDescEstado("Anulado");			
  				}		  								
  				listaPeridoEnvioExportExel.add(periodo);
  			}
  			
  			fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_PERIODO_ENVIO, 
  					"PERIODOS DE ENVIO", //title
  					"PERIODO", //nombre hoja
  					listaPeridoEnvioExportExel);
  			data = toStringListJSON(listaPeridoEnvioExportExel);
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
	
	private String toStringListJSON(List<FisePeriodoEnvio> lista) {
		Serializer serializer = new JsonSerializer();
		Object result = serializer.serialize(lista);
		String data = String.valueOf(result);
		return data;
	}

	@ResourceMapping("grabarPeriodoEnvio")
	public void grabarPeriodoEnvio(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("periodoEnvioBean")PeriodoEnvioBean p) { 
		JSONObject jsonObj = new JSONObject();
		try {		
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);				
			
			logger.info("Entrando a grabar un registro"); 			
			logger.info("Codigo empresa:  "+ p.getCodEmpresa()); 
			logger.info("anio pres:  "+ p.getAnioPres());	
			logger.info("mes pres:  "+ p.getMesPres());
			logger.info("formato:  "+ p.getFormato());	
			logger.info("etapa:  "+ p.getEtapa());	
			logger.info("estado:  "+ p.getEstado());
			logger.info("desde:  "+ p.getDesde());
			logger.info("hasta:  "+ p.getHasta());
			logger.info("dias:  "+ p.getDiasNotifCierre());
			logger.info("flag envio obs:  "+ p.getFlagEnvioObs());
			logger.info("flag mostrar anio mes:  "+ p.getFlagAnioMesEjec());
			logger.info("flag costo:  "+ p.getFlagHabCostos());
			logger.info("anio ini vige:  "+ p.getAnoIniVigencia());
			logger.info("anio fin vige:  "+ p.getAnoFinVigencia());	
			
			logger.info("fecha ampli:  "+ p.getFechaAmpl());	
			String fechaDesde = p.getDesde()+ " " +FechaUtil.getHoraActual();
			String fechaHasta = p.getHasta()+ " " +FechaUtil.getHoraActual();	
			
			p.setUsuario(themeDisplay.getUser().getLogin());
			p.setTerminal(themeDisplay.getUser().getLoginIP());		
			p.setDesde(fechaDesde); 
			p.setHasta(fechaHasta);		
			
			if(FechaUtil.fechaMayor(p.getDesde(),p.getHasta())){
				
				if(FormatoUtil.isNotBlank(p.getHasta()) && FormatoUtil.isNotBlank(p.getFechaAmpl())){
					
					if(FechaUtil.fechaMayor(p.getHasta(),p.getFechaAmpl())){
						String fechaAmpl = p.getFechaAmpl() + " " +FechaUtil.getHoraActual();
						p.setFechaAmpl(fechaAmpl); 	
						
						String valor = fisePeriodoEnvioGartService.insertarDatosFisePeriodoEnvio(p);
						logger.info("valor de la transaccion al insertar:  "+valor); 
						if(!valor.equals("0")){ 
							jsonObj.put("resultado", "OK");	
							jsonObj.put("secuencia", valor);	
						}else{
							jsonObj.put("resultado", "Error");	
						}					
					}else{
						jsonObj.put("resultado", "Mayor");		
					}		
				}else{				
					String valor = fisePeriodoEnvioGartService.insertarDatosFisePeriodoEnvio(p);
					logger.info("valor de la transaccion al insertar:  "+valor); 
					if(!valor.equals("0")){ 
						jsonObj.put("resultado", "OK");	
						jsonObj.put("secuencia", valor);	
					}else{
						jsonObj.put("resultado", "Error");	
					}					
				}
			}else{
				jsonObj.put("resultado", "FECHA");	//fecha desde es mayor a fecha hasta
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
	
	
	@ResourceMapping("actualizarPeriodoEnvio")
	public void actualizarPeriodoEnvio(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("periodoEnvioBean")PeriodoEnvioBean p) { 
		JSONObject jsonObj = new JSONObject();
		try {		
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);				
			
			logger.info("Entrando a actualizar un registro"); 	
			logger.info("secuencial:  "+ p.getSecuencial());
			logger.info("Codigo empresa:  "+ p.getCodEmpresa()); 
			logger.info("anio pres:  "+ p.getAnioPres());	
			logger.info("mes pres:  "+ p.getMesPres());
			logger.info("formato:  "+ p.getFormato());	
			logger.info("etapa:  "+ p.getEtapa());	
			logger.info("estado:  "+ p.getEstado());
			logger.info("desde:  "+ p.getDesde());
			logger.info("hasta:  "+ p.getHasta());
			logger.info("dias:  "+ p.getDiasNotifCierre());
			logger.info("flag envio obs:  "+ p.getFlagEnvioObs());
			logger.info("flag mostrar anio mes:  "+ p.getFlagAnioMesEjec());
			logger.info("flag costo:  "+ p.getFlagHabCostos());
			logger.info("anio ini vige:  "+ p.getAnoIniVigencia());
			logger.info("anio fin vige:  "+ p.getAnoFinVigencia());		
			
			logger.info("fecha ampli:  "+ p.getFechaAmpl());	
			
			String fechaDesde = p.getDesde()+ " " +FechaUtil.getHoraActual();
			String fechaHasta = p.getHasta()+ " " +FechaUtil.getHoraActual();
			
			p.setDesde(fechaDesde); 
			p.setHasta(fechaHasta);	
			
			p.setUsuario(themeDisplay.getUser().getLogin());
			p.setTerminal(themeDisplay.getUser().getLoginIP());	
			
			if(FormatoUtil.isNotBlank(p.getHasta()) && FormatoUtil.isNotBlank(p.getFechaAmpl())){
				if(FechaUtil.fechaMayor(p.getHasta(),p.getFechaAmpl())){
					String fechaAmpl = p.getFechaAmpl() + " " +FechaUtil.getHoraActual();
					p.setFechaAmpl(fechaAmpl);			
					String valor = fisePeriodoEnvioGartService.actualizarDatosFisePeriodoEnvio(p);
					logger.info("valor de la transaccion al actualizar:  "+valor); 
					if(!valor.equals("0")){ 
						jsonObj.put("resultado", "OK");	
						jsonObj.put("secuencia", valor);	
					}else{
						jsonObj.put("resultado", "Error");	
					}			
				}else{
					jsonObj.put("resultado", "Mayor");		
				}		
			}else{				
				String valor = fisePeriodoEnvioGartService.actualizarDatosFisePeriodoEnvio(p);
				logger.info("valor de la transaccion al actualizar:  "+valor); 
				if(!valor.equals("0")){ 
					jsonObj.put("resultado", "OK");	
					jsonObj.put("secuencia", valor);	
				}else{
					jsonObj.put("resultado", "Error");	
				}				
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
	
	
	@ResourceMapping("editarViewPeriodoEnvio")
	public void editarPeriodoEnvio(ModelMap model,ResourceRequest request,ResourceResponse response,
			@ModelAttribute("periodoEnvioBean")PeriodoEnvioBean p) { 		
		try {	
			String data;			
			logger.info("secuencial para editar y visualizar "+p.getSecuencial());					
			
			p= fisePeriodoEnvioGartService.buscarFisePeriodoEnvioEditar(p.getSecuencial());
			
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
	
	private String toStringJSON(PeriodoEnvioBean p) {
		Serializer serializer = new JsonSerializer();
		Object result = serializer.serialize(p);
		String data = String.valueOf(result);
		return data;
	}
	
	@ResourceMapping("eliminarPeriodoEnvio")
	public void eliminarPeriodoEnvio(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("periodoEnvioBean")PeriodoEnvioBean p) { 	
		
		JSONObject jsonObj = new JSONObject();
		try {	
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			
			logger.info("Entrando a eliminar un registro periodo envio"); 			
			logger.info("Secuencia:  "+ p.getSecuencial()); 
			
			String user= themeDisplay.getUser().getLogin();
			String terminal = themeDisplay.getUser().getLoginIP();	
     			
			logger.info("Enviando el formulario al service"); 
			String valor = fisePeriodoEnvioGartService.eliminarDatosFisePeriodoEnvio(p.getSecuencial(),user,terminal); 
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
			logger.error("Error al eliminar los datos de periodo envio: "+e.getMessage());
		}  	
		
	}		
	

}
