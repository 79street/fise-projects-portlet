package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.Formato14CBean;
import gob.osinergmin.fise.bean.Formato14CReportBean;
import gob.osinergmin.fise.bean.Formato14Generic;
import gob.osinergmin.fise.bean.MensajeErrorBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.CfgTabla;
import gob.osinergmin.fise.domain.FiseFormato14CC;
import gob.osinergmin.fise.domain.FiseFormato14CD;
import gob.osinergmin.fise.domain.FiseFormato14CDOb;
import gob.osinergmin.fise.domain.FisePeriodoEnvio;
import gob.osinergmin.fise.gart.json.Formato14CJSON;
import gob.osinergmin.fise.gart.jsp.FileEntryJSP;
import gob.osinergmin.fise.gart.service.CfgTablaGartService;
import gob.osinergmin.fise.gart.service.CommonGartService;
import gob.osinergmin.fise.gart.service.FisePeriodoEnvioGartService;
import gob.osinergmin.fise.gart.service.Formato14CGartService;
import gob.osinergmin.fise.util.FechaUtil;
import gob.osinergmin.fise.util.FormatoUtil;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
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
import com.liferay.portal.kernel.repository.model.FileEntry;
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
	
	@Autowired
	@Qualifier("commonGartServiceImpl")
	CommonGartService commonService;
	
	@Autowired
	@Qualifier("cfgTablaGartServiceImpl")
	CfgTablaGartService tablaService;
	
	
	List<FisePeriodoEnvio> listaPeriodoEnvio;
	
	Map<String, String> mapaEmpresa;	
	
	Map<String, String> mapaErrores;
	List<MensajeErrorBean> listaObservaciones;
	Map<Long, String> mapaZonaBenef;
	
	
	@ModelAttribute("formato14CBean")
    public Formato14CBean listFiseFormato14CC() {
		Formato14CBean comman  = new Formato14CBean();
        return comman;	        
    }
	
	
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
    		mapaErrores = fiseUtil.getMapaErrores();
    		mapaZonaBenef = fiseUtil.getMapaZonaBenef();
    		
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
  				logger.info("Seteando del array de json"); 
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
	
	@ResourceMapping("editarViewF14C")
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
	
	@ResourceMapping("eliminarF14C")
	public void eliminarF14C(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("formato14CBean")Formato14CBean f) { 	
		
		JSONObject jsonObj = new JSONObject();
		try {				
			logger.info("Entrando a eliminar un registro en el fomato 14C"); 			
			logger.info("Codigo empresa:  "+ f.getCodEmpresa()); 
			logger.info("anio prese:  "+ f.getAnioPres()); 
			logger.info("mes prese:  "+ f.getMesPres()); 
			logger.info("anio inicio vigencia:  "+ f.getAnioInicioVig());
			logger.info("anio fin vigencia:  "+ f.getAnioFinVig());	
			logger.info("etapa:  "+ f.getEtapa());
     			
			logger.info("Enviando el formulario al service"); 
			String valor = formato14CGartService.eliminarDatosFormato14C(f);
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
			logger.error("Error al eliminar los datos en el formato 14C: "+e.getMessage());
		}  	
		
	}		

	@ResourceMapping("cargaPeriodoF14C")
  	public void cargaPeriodo(ModelMap model, ResourceRequest request,ResourceResponse response,
  			@ModelAttribute("formato14CBean")Formato14CBean f){
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
	
	
	@ResourceMapping("reporteF14C")
	public void reporte(ResourceRequest request,ResourceResponse response,
			@ModelAttribute("formato14CBean")Formato14CBean f) {
		try {
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();
	        
		    JSONArray jsonArray = new JSONArray();	  
		    Formato14CReportBean bean = null;   	    
		    
		    logger.info("Entrando a reportes del fomato 14C"); 			
			logger.info("Codigo empresa:  "+ f.getCodEmpresa()); 
			logger.info("perido de envio:  "+ f.getPeriodoEnvio());	
			
			String nombreReporte = request.getParameter("nombreReporte").trim();
		    String nombreArchivo = request.getParameter("nombreArchivo").trim();
		    String tipoFormato = FiseConstants.TIPO_FORMATO_14C;
		    String tipoArchivo = request.getParameter("tipoArchivo").trim();
		    
		    //  String flagPeriodoEjecucion = f.getFlagPeriodoEjecucion();
		    
		    session.setAttribute("nombreReporte",nombreReporte);
		    session.setAttribute("nombreArchivo",nombreArchivo);
		    session.setAttribute("tipoFormato",tipoFormato);
		    session.setAttribute("tipoArchivo",tipoArchivo);
		    
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
			
			FiseFormato14CC formato = formato14CGartService.obtenerFiseFormato14CC(f);
	        
	        if( formato!=null ){
	        	//setamos los valores en el bean
	        	bean = formato14CGartService.estructurarFormato14CBeanByFiseFormato14C(formato);
	        	bean.setDescEmpresa(mapaEmpresa.get(formato.getId().getCodEmpresa()));
	        	bean.setDescMesPresentacion(fiseUtil.getMapaMeses().get(formato.getId().getMesPresentacion()));		        	
	           	session.setAttribute("mapa", formato14CGartService.mapearParametrosFormato14C(bean));
	        }	        
		    response.setContentType("application/json");
		    PrintWriter pw = response.getWriter();
		    pw.write(jsonArray.toString());
		    pw.flush();
		    pw.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@ResourceMapping("validacionF14C")
	public void validacion(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("formato14CBean")Formato14CBean f) {
	
	HttpServletRequest req = PortalUtil.getHttpServletRequest(request);	        
    HttpSession session = req.getSession();
	
	ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
	try {	
		JSONArray jsonArray = new JSONArray();			
		logger.info("Entrando a validadar un registro en el fomato 14C"); 			
		logger.info("Codigo empresa:  "+ f.getCodEmpresa()); 
		logger.info("perido de envio:  "+ f.getPeriodoEnvio());		

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
		
		FiseFormato14CC formato = formato14CGartService.obtenerFiseFormato14CC(f);
	    logger.info("Objeto formato 14C: "+formato); 
	    if( formato!=null ){	    
	    	Formato14Generic formato14Generic = new Formato14Generic(formato);
	    	int i = commonService.validarFormatos_14(formato14Generic, FiseConstants.NOMBRE_FORMATO_14C,
	    			themeDisplay.getUser().getLogin(), themeDisplay.getUser().getLogin());
		    if(i==0){
		    	logger.info("Enviando la lista de detalle:  "+formato.getListaDetalle14cDs().size()); 
		    	cargarListaObservaciones(formato.getListaDetalle14cDs());
		    	logger.info("lista de observaciones cargado correctamente"); 
		    	for (MensajeErrorBean error : listaObservaciones) {
	  				JSONObject jsonObj = new JSONObject();
					jsonObj.put("id", error.getId());	
					jsonObj.put("descZonaBenef", error.getDescZonaBenef());
					jsonObj.put("codigo", error.getCodigo());			
					jsonObj.put("descripcion", error.getDescripcion());	
					//agregar los valores
					jsonArray.put(jsonObj);		
				}		    			
		    	//**exportar excel
		    	fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_FORMATO_VAL,
		    			FiseConstants.NOMBRE_EXCEL_VALIDACION_F14C,
		    			FiseConstants.NOMBRE_HOJA_VALIDACION, listaObservaciones);		    	
		    	//jsonObj.put("resultado", "OK");
		    }else{
		    	//jsonObj.put("resultado", "Error");
		    }
	    }	    
		    response.setContentType("application/json");
		    PrintWriter pw = response.getWriter();		  
		    logger.info(jsonArray.toString());
		    pw.write(jsonArray.toString());
		    pw.flush();
		    pw.close();
	    
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	private void cargarListaObservaciones(List<FiseFormato14CD> listaDetalle) throws Exception{
		int cont=0;
		listaObservaciones = new ArrayList<MensajeErrorBean>();
		for (FiseFormato14CD detalle : listaDetalle) {			
			List<FiseFormato14CDOb> listaObser = formato14CGartService.listaObservacionesF14C(detalle);
			logger.info("Tamaño de lista de observaciones:  "+listaObser.size()); 
			for (FiseFormato14CDOb o : listaObser) {			
				cont++;
				MensajeErrorBean obs = new MensajeErrorBean();
				obs.setId(cont);				
				obs.setDescZonaBenef(mapaZonaBenef.get(o.getId().getIdZonaBenef()));
				obs.setCodigo(o.getFiseObservacion().getIdObservacion());
				obs.setDescripcion(mapaErrores.get(o.getFiseObservacion().getIdObservacion()));
				listaObservaciones.add(obs);							
			}
		}
	}	
	
	@ResourceMapping("reporteValidacionF14C")
	public void reporteValidacion(ResourceRequest request,ResourceResponse response) {
		try {
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();
	        
		    JSONArray jsonArray = new JSONArray();	
		    
		    String nombreReporte = request.getParameter("nombreReporte").trim();
		    String nombreArchivo = request.getParameter("nombreArchivo").trim();
		    String tipoFormato = FiseConstants.TIPO_FORMATO_VAL;
		    String tipoArchivo = request.getParameter("tipoArchivo").trim();
		   
		    session.setAttribute("nombreReporte",nombreReporte);
		    session.setAttribute("nombreArchivo",nombreArchivo);
		    session.setAttribute("tipoFormato",tipoFormato);
		    session.setAttribute("tipoArchivo",tipoArchivo);
	
		    if( listaObservaciones!=null ){
		    	session.setAttribute("lista", listaObservaciones);
		    }
	        
		    response.setContentType("application/json");
		    PrintWriter pw = response.getWriter();
		    pw.write(jsonArray.toString());
		    pw.flush();
		    pw.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@ResourceMapping("envioDefinitivoF14C")
	public void envioDefinitivo(ResourceRequest request,ResourceResponse response,
			@ModelAttribute("formato14CBean")Formato14CBean f) {
		try {
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();
	        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		  
	        JSONArray jsonArray = new JSONArray();	
		  
		    List<FileEntryJSP> listaArchivo = new ArrayList<FileEntryJSP>(); 	 
		    
		    Formato14CReportBean bean = new Formato14CReportBean();
		    
		    Map<String, Object> mapa = null;
		    String directorio = null;	  
		    
		    String nombreReporte = request.getParameter("nombreReporte").trim();
		    String nombreArchivo = request.getParameter("nombreArchivo").trim();
		  

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
		    f.setUsuario(themeDisplay.getUser().getLogin());
			f.setTerminal(themeDisplay.getUser().getLoginIP());	
			
		    FiseFormato14CC formato = formato14CGartService.obtenerFiseFormato14CC(f);
		    logger.info("Objeto formato 14C: "+formato);
		    
	        if( formato!=null ){       
			    bean = formato14CGartService.estructurarFormato14CBeanByFiseFormato14C(formato);
	        	bean.setDescEmpresa(mapaEmpresa.get(formato.getId().getCodEmpresa()));
	        	bean.setDescMesPresentacion(fiseUtil.getMapaMeses().get(formato.getId().getMesPresentacion()));
	        	mapa = formato14CGartService.mapearParametrosFormato14C(bean);
	        	if(mapa!=null){
	        		mapa.put("IMG", session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg"));
	        		mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
	        		//verificar si ponerlo aca o no
	        		mapa.put("USUARIO", themeDisplay.getUser().getLogin());
	        		mapa.put("NOMBRE_FORMATO", FiseConstants.NOMBRE_FORMATO_14C);
	 			}
	        	Formato14Generic formato14Generic = new Formato14Generic(formato);
	        	int i = commonService.validarFormatos_14(formato14Generic, FiseConstants.NOMBRE_FORMATO_14C,
	        			themeDisplay.getUser().getLogin(), themeDisplay.getUser().getLoginIP());
			    if(i==0){
			    	logger.info("entrando a cargar las observaciones de envio."); 
			    	cargarListaObservaciones(formato.getListaDetalle14cDs());
			    	logger.info("Cargo correctamente las observaciones de envio."); 
			    } 
		        /**REPORTE FORMATO 14C*/
		       nombreReporte = "formato14C";
		       nombreArchivo = nombreReporte;
		       directorio =  "/reports/"+nombreReporte+".jasper";
		       File reportFile = new File(session.getServletContext().getRealPath(directorio));
		       byte[] bytes = null;
		       bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), mapa, new JREmptyDataSource());
		       logger.info("Tamaño del arreglo de bytes del formato 14C envio defi."+bytes.length); 
		       if (bytes != null) {
		    	   String nombre= nombreArchivo+FiseConstants.EXTENSIONARCHIVO_PDF;
		    	   logger.info("subiendo archivo al repositorio del formato 14c"); 
		    	   FileEntry archivo = fiseUtil.subirDocumentoBytes(request, bytes, "application/pdf", nombre);
		    	   logger.info("Archivo subido correctamente  formato 14c envio."+archivo); 
		    	   if( archivo!=null ){
		    		   FileEntryJSP fileEntryJsp = new FileEntryJSP();
		    		   fileEntryJsp.setNombreArchivo(nombre);
		    		   fileEntryJsp.setFileEntry(archivo);
		    		   listaArchivo.add(fileEntryJsp);
		    	   }
		       }
		       /**REPORTE OBSERVACIONES*/
		       if( listaObservaciones!=null && listaObservaciones.size()>0 ){
		    	   nombreReporte = "validacion";
		    	   nombreArchivo = nombreReporte;
			       directorio =  "/reports/"+nombreReporte+".jasper";
			       File reportFile2 = new File(session.getServletContext().getRealPath(directorio));
		    	   byte[] bytes2 = null;
			       bytes2 = JasperRunManager.runReportToPdf(reportFile2.getPath(), mapa, new JRBeanCollectionDataSource(listaObservaciones));
			       logger.info("Tamaño del arreglo de bytes del observaciones envio defi."+bytes2.length); 
			       if (bytes != null) {
			    	   String nombre= nombreArchivo+FiseConstants.EXTENSIONARCHIVO_PDF;
			    	   logger.info("subiendo archivo al repositorio del observaciones envio defi.");
			    	   FileEntry archivo2 = fiseUtil.subirDocumentoBytes(request, bytes2, "application/pdf", nombre);
			    	   logger.info("Archivo subido correctamente  observaciones envio."+archivo2); 
			    	   if( archivo2!=null ){
			    		   FileEntryJSP fileEntryJsp = new FileEntryJSP();
			    		   fileEntryJsp.setNombreArchivo(nombre);
			    		   fileEntryJsp.setFileEntry(archivo2);
			    		   listaArchivo.add(fileEntryJsp);
			    	   }
			       }
		       }
		       /**REPORTE ACTA DE ENVIO*/
		       nombreReporte = "actaEnvio";
		       nombreArchivo = nombreReporte;
		       directorio =  "/reports/"+nombreReporte+".jasper";
		       File reportFile3 = new File(session.getServletContext().getRealPath(directorio));
		       byte[] bytes3 = null;
		       bytes3 = JasperRunManager.runReportToPdf(reportFile3.getPath(), mapa, new JREmptyDataSource());
		       logger.info("Tamaño del arreglo de bytes del acta de envio defi."+bytes3.length); 
		       if (bytes3 != null) {
		    	   String nombre= nombreArchivo+FiseConstants.EXTENSIONARCHIVO_PDF;
		    	   logger.info("subiendo archivo al repositorio del acta de envio defi.");
		    	   FileEntry archivo3 = fiseUtil.subirDocumentoBytes(request, bytes3, "application/pdf", nombre);
		    	   logger.info("Archivo subido correctamente  del acta de envio."+archivo3); 
		    	   if( archivo3!=null ){
		    		   FileEntryJSP fileEntryJsp = new FileEntryJSP();
		    		   fileEntryJsp.setNombreArchivo(nombre);
		    		   fileEntryJsp.setFileEntry(archivo3);
		    		   listaArchivo.add(fileEntryJsp);
		    	   }
		       }
		       //
		       if( listaArchivo!=null && listaArchivo.size()>0 ){
		    	   //obtener e nombre del formato
		    	   CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14C);
		    	   String descripcionFormato = "";
		    	   if( tabla!=null ){
		    		   descripcionFormato = tabla.getDescripcionTabla();
		    	   }
		    	   logger.info("Entrando a enviar email envio defi."); 
		    	   fiseUtil.enviarMailAdjunto(request,listaArchivo,descripcionFormato);
		    	   logger.info("El envio de email fue correctamente envio defi."); 
		    	   /**actualizamos  la fecha de envio*/
		    	   formato14CGartService.actualizarDatosEnvioFormato14C(f);
		       }
	        }
	       response.setContentType("application/json");
	       PrintWriter pw = response.getWriter();
		   pw.write(jsonArray.toString());
		   pw.flush();
		   pw.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	 }
	
	
	
}
