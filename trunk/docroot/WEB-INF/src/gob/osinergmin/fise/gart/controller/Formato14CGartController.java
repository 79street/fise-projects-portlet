package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.Formato14CBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.FiseFormato14CC;
import gob.osinergmin.fise.domain.FisePeriodoEnvio;
import gob.osinergmin.fise.gart.json.Formato14CJSON;
import gob.osinergmin.fise.gart.service.FisePeriodoEnvioGartService;
import gob.osinergmin.fise.gart.service.Formato14CGartService;
import gob.osinergmin.fise.util.FechaUtil;
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

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
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
import com.liferay.portal.util.PortalUtil;

@Controller("formato14CGartController")
@RequestMapping("VIEW")
public class Formato14CGartController {
	
	Log logger=LogFactoryUtil.getLog(Formato14CGartController.class);
	
	@Autowired
	@Qualifier("formato14CGartServiceImpl")
	private Formato14CGartService formato14CGartService;
	
	@Autowired
	@Qualifier("fiseUtil")
	FiseUtil fiseUtil;
	
	@Autowired
	@Qualifier("fisePeriodoEnvioGartServiceImpl")
	FisePeriodoEnvioGartService periodoService;
	
	
	List<FisePeriodoEnvio> listaPeriodoEnvio;
	
	private Map<String, String> mapaEmpresa;	
	
	
	
	@ModelAttribute("formato14CBean")
    public Formato14CBean listFiseFormato14CC() {
		Formato14CBean comman  = new Formato14CBean();
        return comman;	        
    }
	
//	@RequestMapping
//	public  String defaultView(){
//		logger.info("--- defaultView");
//		return "formato14C";
//	}
	
	@RequestMapping
	public String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse,
			     @ModelAttribute("formato14CBean")Formato14CBean f){
        try {
        	listaPeriodoEnvio = new ArrayList<FisePeriodoEnvio>();
        	
        	f.setListaMes(fiseUtil.getMapaMeses());
    		f.setAnioDesde(fiseUtil.obtenerNroAnioFechaActual());
    		f.setMesDesde( String.valueOf(Integer.parseInt(fiseUtil.obtenerNroMesFechaActual())-1));
    		f.setAnioHasta(fiseUtil.obtenerNroAnioFechaActual());
    		f.setMesHasta(fiseUtil.obtenerNroMesFechaActual());
    		f.setEtapaBusq(FiseConstants.ETAPA_SOLICITUD); 
    		
    		if(f.getListaEmpresas()==null){
    			logger.info("Lista de empresa es null:  " +renderRequest); 
    			f.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));
    		}
    		f.setAdmin(fiseUtil.esAdministrador(renderRequest));
    		mapaEmpresa = fiseUtil.getMapaEmpresa();  			
    		
		} catch (Exception e) {
			logger.info("Ocurrio un errror al caragar la pagina del formato 14C"); 
			e.printStackTrace();
		}		
		return "formato14C";
	}
	
	
	@ResourceMapping("busquedaF14C")
  	public void busqueda(ResourceRequest request,ResourceResponse response,
  			@ModelAttribute("formato14CBean")Formato14CBean f){
		
		try{
			response.setContentType("application/json");	
				
			HttpServletRequest req = PortalUtil.getHttpServletRequest(request);	        
	        HttpSession session = req.getSession();
	        	
		    JSONArray jsonArray = new JSONArray();
		    
			String codEmpresa = f.getCodEmpresaBusq();	 
			String etapa = f.getEtapaBusq();
			long anioDesde = 0;
			long anioHasta =0;
			long mesDesde = 0;			
			long mesHasta = 0;
			if(StringUtils.isNotBlank(f.getAnioDesde())){
				anioDesde = Long.valueOf(f.getAnioDesde());	
			}
			if(StringUtils.isNotBlank(f.getAnioHasta())){
				anioHasta = Long.valueOf(f.getAnioHasta());	
			}
			if(StringUtils.isNotBlank(f.getMesDesde())){
				mesDesde = Long.valueOf(f.getMesDesde());	
			}
			if(StringUtils.isNotBlank(f.getMesHasta())){
				mesHasta = Long.valueOf(f.getMesHasta());	
			}			
			logger.info("codigo empresa "+ codEmpresa);
  			logger.info("anio desde "+ anioDesde);
  			logger.info("mes desde "+ mesDesde);
  			logger.info("anio hasta "+ anioHasta);
  			logger.info("mes hasta "+ mesHasta);
  			logger.info("etapa "+ etapa);
  			logger.info("admin "+ f.isAdmin());
 		   
 			List<FiseFormato14CC> listaFormato14C = formato14CGartService.buscarFiseFormato14CC(codEmpresa, 
  					anioDesde, anioHasta, mesDesde, mesHasta, etapa);	
  			
  			logger.info("tamaño de la lista formato 14c   :"+listaFormato14C.size());
  			for(FiseFormato14CC formato14c : listaFormato14C){
  				formato14c.setDescEmpresa(mapaEmpresa.get(formato14c.getId().getCodEmpresa()));
  				formato14c.setDescMesPresentacion(fiseUtil.getMapaMeses().get(formato14c.getId().getMesPresentacion()));
  				jsonArray.put(new Formato14CJSON().asJSONObject(formato14c,""));
  			}
  			
  			fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_FORMATO_14C, FiseConstants.NOMBRE_EXCEL_FORMATO14C, FiseConstants.NOMBRE_HOJA_FORMATO14C, listaFormato14C);
  			
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
	
	@ResourceMapping("editarF14C")
	public void editar(ModelMap model,ResourceRequest request,ResourceResponse response,
			@ModelAttribute("formato14CBean")Formato14CBean f) { 		
		try {	
			String data;			
			logger.info("codempresa "+f.getCodEmpresa());
			logger.info("anopresent "+f.getAnioPres());
			logger.info("mespresent "+f.getMesPres());
			logger.info("anoIniVigencia "+f.getAnioInicioVig());
			logger.info("anoFinVigencia "+f.getAnioFinVig());
			logger.info("etapa "+f.getEtapa());
			
			//Formato14CBean formato = new Formato14CBean();
			
			f= formato14CGartService.buscarFormato14CEditar(f.getCodEmpresa(),
					f.getAnioPres(), f.getMesPres(), f.getAnioInicioVig(), f.getAnioFinVig(), f.getEtapa())	;	
			
			String codigoPeriodoEnvio =String.valueOf(f.getAnioPres())+
        			FormatoUtil.rellenaIzquierda(String.valueOf(f.getMesPres()), '0', 2)+
        			f.getEtapa();
			f.setPeriodoEnvio(codigoPeriodoEnvio);
			
			String mesLetras = FechaUtil.mesLetras(f.getMesPres());			
			String desPeriodoEnvio = mesLetras+"-"+f.getAnioPres()+" / "+f.getEtapa();
			
			f.setDesperiodoEnvio(desPeriodoEnvio); 
			
			data = toStringJSON(f);						
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
	
	private String toStringJSON(Formato14CBean f) {
		Serializer serializer = new JsonSerializer();
		Object result = serializer.serialize(f);
		String data = String.valueOf(result);
		return data;
	}
	
	@ResourceMapping("grabarF14C")
	public void grabarF14C(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("formato14CBean")Formato14CBean f) { 
		JSONObject jsonObj = new JSONObject();
		try {				
			logger.info("Entrando a grabar un registro en el fomato 14C"); 			
			logger.info("Codigo empresa:  "+ f.getCodEmpresa()); 
			logger.info("perido de envio:  "+ f.getPeriodoEnvio());	
			logger.info("anio inicio vigencia:  "+ f.getAnioInicioVig());
			logger.info("anio fin vigencia:  "+ f.getAnioFinVig());	
			logger.info("nombre de sede:  "+ f.getNombreSede());	
			logger.info("cantidad total rural:  "+ f.getNumRural());	

			if( f.getPeriodoEnvio().length()>6 ){
				f.setAnioPres(f.getPeriodoEnvio().substring(0, 4));
				f.setMesPres(f.getPeriodoEnvio().substring(4, 6));
				f.setEtapa(f.getPeriodoEnvio().substring(6, f.getPeriodoEnvio().length()));

				/*if( "S".equals(f.getFlagPeriodoEjecucion()) ){
							f.setAnioInicioVigencia(f.getAnioInicioVigencia());
							f.setAnioFinVigencia(f.getAnioFinVigencia());
						}else{
							f.setAnioInicioVigencia(f.getAnioPresent());
							f.setAnioFinVigencia(f.getAnioPresent());
						}*/	
				f.setAnioInicioVig("2009");
				f.setAnioFinVig("2014");
			}					
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);					
			f.setUsuario(themeDisplay.getUser().getLogin());
			f.setTerminal(themeDisplay.getUser().getLoginIP());		
			logger.info("Enviando el formulario al service"); 
			
			String valor = formato14CGartService.insertarDatosFormato14C(f);
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
			logger.error("Error al guardar los datos en el formato 14C: "+e.getMessage());
		} 	
	}	
		
	@ResourceMapping("actualizarF14C")
	public void actualizarF14C(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("formato14CBean")Formato14CBean f) { 	
		
		JSONObject jsonObj = new JSONObject();
		try {				
			logger.info("Entrando a actualizar un registro en el fomato 14C"); 			
			logger.info("Codigo empresa:  "+ f.getCodEmpresa()); 
			logger.info("perido de envio:  "+ f.getPeriodoEnvio());	
			logger.info("anio inicio vigencia:  "+ f.getAnioInicioVig());
			logger.info("anio fin vigencia:  "+ f.getAnioFinVig());	
			logger.info("nombre de sede:  "+ f.getNombreSede());	
			logger.info("cantidad total rural:  "+ f.getNumRural());	

			if( f.getPeriodoEnvio().length()>6 ){
				f.setAnioPres(f.getPeriodoEnvio().substring(0, 4));
				f.setMesPres(f.getPeriodoEnvio().substring(4, 6));
				f.setEtapa(f.getPeriodoEnvio().substring(6, f.getPeriodoEnvio().length()));

				/*if( "S".equals(f.getFlagPeriodoEjecucion()) ){
							f.setAnioInicioVigencia(f.getAnioInicioVigencia());
							f.setAnioFinVigencia(f.getAnioFinVigencia());
						}else{
							f.setAnioInicioVigencia(f.getAnioPresent());
							f.setAnioFinVigencia(f.getAnioPresent());
						}*/	
				f.setAnioInicioVig("2009");
				f.setAnioFinVig("2014");
			}					
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);					
			f.setUsuario(themeDisplay.getUser().getLogin());
			f.setTerminal(themeDisplay.getUser().getLoginIP());		
			logger.info("Enviando el formulario al service"); 
			String valor = formato14CGartService.actualizarDatosFormato14C(f);
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
			logger.error("Error al actualizar los datos en el formato 14C: "+e.getMessage());
		}  	
		
	}	

	@ResourceMapping("cargaPeriodoF14C")
  	public void cargaPeriodo(ModelMap model, ResourceRequest request,ResourceResponse response,@ModelAttribute("formato14CBean")Formato14CBean f){
		try {			
  			response.setContentType("applicacion/json");
  			String codEmpresa = f.getCodEmpresa();
  			logger.info("Codigo Empresa para cargar el periodo:  "+codEmpresa);
  			//String periodoEnvio = f.getPeriodoEnvio();
  			//lo pongo en la lista porque no persiste las colecciones en el command
  			listaPeriodoEnvio = periodoService.listarFisePeriodoEnvioMesAnioEtapa(codEmpresa, FiseConstants.TIPO_FORMATO_12C);
  			//f.setListaPeriodoEnvio(listaPeriodoEnvio); 
  			logger.info("Tamaño de lista de periodo de envio:  "+listaPeriodoEnvio.size()); 
  			JSONArray jsonArray = new JSONArray();
  			for (FisePeriodoEnvio periodo : listaPeriodoEnvio) {
  				JSONObject jsonObj = new JSONObject();
				jsonObj.put("codigoItem", periodo.getCodigoItem());				
				jsonObj.put("descripcionItem", periodo.getDescripcionItem());			
				jsonObj.put("flagPeriodoEjecucion", periodo.getFlagPeriodoEjecucion());	
				//agregar los valores
				jsonArray.put(jsonObj);		
			}
  			
  		    PrintWriter pw = response.getWriter();
  		    logger.info(jsonArray.toString());
  		    pw.write(jsonArray.toString());
  		    pw.flush();
  		    pw.close();							
  		}catch (Exception e) {
  			// TODO: handle exception
  			e.printStackTrace();
  		}
	}
	
	
	

}
