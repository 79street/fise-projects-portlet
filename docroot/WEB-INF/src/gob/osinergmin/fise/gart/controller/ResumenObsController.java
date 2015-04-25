package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.ResumenObsBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.CfgTabla;
import gob.osinergmin.fise.domain.FiseFormato12AC;
import gob.osinergmin.fise.domain.FiseFormato12AD;
import gob.osinergmin.fise.domain.FiseFormato12ADOb;
import gob.osinergmin.fise.domain.FiseFormato12BC;
import gob.osinergmin.fise.domain.FiseFormato12BD;
import gob.osinergmin.fise.domain.FiseFormato12BDOb;
import gob.osinergmin.fise.domain.FiseFormato12CC;
import gob.osinergmin.fise.domain.FiseFormato12CD;
import gob.osinergmin.fise.domain.FiseFormato12CDOb;
import gob.osinergmin.fise.domain.FiseFormato12DC;
import gob.osinergmin.fise.domain.FiseFormato12DD;
import gob.osinergmin.fise.domain.FiseFormato12DDOb;
import gob.osinergmin.fise.domain.FiseFormato13AC;
import gob.osinergmin.fise.domain.FiseFormato13AD;
import gob.osinergmin.fise.domain.FiseFormato13ADOb;
import gob.osinergmin.fise.domain.FiseFormato14AC;
import gob.osinergmin.fise.domain.FiseFormato14AD;
import gob.osinergmin.fise.domain.FiseFormato14ADOb;
import gob.osinergmin.fise.domain.FiseFormato14BC;
import gob.osinergmin.fise.domain.FiseFormato14BD;
import gob.osinergmin.fise.domain.FiseFormato14BDOb;
import gob.osinergmin.fise.domain.FiseFormato14CC;
import gob.osinergmin.fise.domain.FiseFormato14CD;
import gob.osinergmin.fise.domain.FiseFormato14CDOb;
import gob.osinergmin.fise.domain.FiseGrupoInformacion;
import gob.osinergmin.fise.gart.service.CfgTablaGartService;
import gob.osinergmin.fise.gart.service.FiseGrupoInformacionGartService;
import gob.osinergmin.fise.gart.service.Formato12AGartService;
import gob.osinergmin.fise.gart.service.Formato12BGartService;
import gob.osinergmin.fise.gart.service.Formato12CGartService;
import gob.osinergmin.fise.gart.service.Formato12DGartService;
import gob.osinergmin.fise.gart.service.Formato13AGartService;
import gob.osinergmin.fise.gart.service.Formato14AGartService;
import gob.osinergmin.fise.gart.service.Formato14BGartService;
import gob.osinergmin.fise.gart.service.Formato14CGartService;
import gob.osinergmin.fise.util.FormatoUtil;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.json.JSONArray;
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


@Controller("resumenObsController")
@RequestMapping("VIEW")
public class ResumenObsController {
	
	Logger logger = Logger.getLogger(ResumenObsController.class);
	
	
	@Autowired
	@Qualifier("fiseUtil")
	private FiseUtil fiseUtil;	
	
	@Autowired
	@Qualifier("fiseGrupoInformacionGartServiceImpl")
	private FiseGrupoInformacionGartService fiseGrupoInformacionService;
	
	@Autowired
	@Qualifier("cfgTablaGartServiceImpl")
	private CfgTablaGartService tablaService;
	
	
	@Autowired
	@Qualifier("formato12AGartServiceImpl")
	private Formato12AGartService formatoService12A;
	
	@Autowired
	@Qualifier("formato12BGartServiceImpl")
	private Formato12BGartService formatoService12B;
	
	@Autowired
	@Qualifier("formato12CGartServiceImpl")
	private Formato12CGartService formatoService12C;
	
	@Autowired
	@Qualifier("formato12DGartServiceImpl")
	private Formato12DGartService formatoService12D;
	
	@Autowired
	@Qualifier("formato13AGartServiceImpl")
	private Formato13AGartService formatoService13A;
	
	@Autowired
	@Qualifier("formato14AGartServiceImpl")
	private Formato14AGartService formatoService14A;
	
	@Autowired
	@Qualifier("formato14BGartServiceImpl")
	private Formato14BGartService formatoService14B;
	
	@Autowired
	@Qualifier("formato14CGartServiceImpl")
	private Formato14CGartService formatoService14C;
	
	
	
	private Map<String, String> mapaEmpresa;	
	private Map<Long, String> mapaEtapaEjecucion;	
	private Map<String, String> mapaSectorTipico;	
	//private Map<String, String> mapaEtapa;
	
	
	@ModelAttribute("resumenObsBean")
    public ResumenObsBean resumenObsBean() {
		ResumenObsBean comman  = new ResumenObsBean();
        return comman;	        
    }

	
	@RequestMapping
	public String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse,
			     @ModelAttribute("resumenObsBean")ResumenObsBean r){
        try {           	
    		if(r.getListaEmpresas()==null){
    			logger.info("Lista de empresa es null:  " +renderRequest); 
    			r.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));
    		}     		
    		r.setAdmin(fiseUtil.esAdministrador(renderRequest));
    		//TODOS = que filtre grupo de informacion activos e inactivos
    		r.setListaGrupoInf(fiseGrupoInformacionService.listarGrupoInformacion(FiseConstants.MENSUAL,"TODOS")); 
    		
    		mapaEmpresa = fiseUtil.getMapaEmpresa();   
    		mapaEtapaEjecucion = fiseUtil.getMapaEtapaEjecucion();
            mapaSectorTipico = fiseUtil.getMapaSectorTipico();    		    		
    		//mapaEtapa = fiseUtil.getMapaEtapa();
    		
    		model.addAttribute("model", r);
    		
		} catch (Exception e) {
			logger.info("Ocurrio un errror al caragar la pagina de resumen de Observaciones"); 
			e.printStackTrace();
		}		
		return "resumenObservaciones";
	}	
	
	
	@ResourceMapping("cargarGrupoInformacion")
  	public void cargaGrupoInformacion(ModelMap model, ResourceRequest request,ResourceResponse response,
  			 @ModelAttribute("resumenObsBean")ResumenObsBean r){
		try {			
  			response.setContentType("applicacion/json");
  			String tipoFormato = r.getOptionFormato();
  			
  			logger.info("Codigo grupo inf. para cargar grupo de infor.:  "+tipoFormato);
  			//TODOS = que filtre activos e inactivos
  			List<FiseGrupoInformacion> listaGrupoInf = fiseGrupoInformacionService.listarGrupoInformacion(tipoFormato,"TODOS");
  			logger.info("Tamaño de lista de grupo inf:  "+listaGrupoInf.size()); 
  			JSONArray jsonArray = new JSONArray();
  			for (FiseGrupoInformacion grupo : listaGrupoInf) {
  				JSONObject jsonObj = new JSONObject();
				jsonObj.put("codigoItem", grupo.getIdGrupoInformacion());				
				jsonObj.put("descripcionItem", grupo.getDescripcion());				
				jsonArray.put(jsonObj);		
			}  			
  		    PrintWriter pw = response.getWriter();
  		    logger.info(jsonArray.toString());
  		    pw.write(jsonArray.toString());
  		    pw.flush();
  		    pw.close();							
  		}catch (Exception e) {  		
  			e.printStackTrace();
  		}
	}		
	
	/**Reporte de observaciones para mesnuales****/
	
	
	@ResourceMapping("verResumenObsMensualesPdf")
	public void verResumenObsMensualesPdf(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("resumenObsBean")ResumenObsBean r) {
		
		List<ResumenObsBean> listaF12 =new ArrayList<ResumenObsBean>();
		
		byte[] bytesF12 = null;
		
		try {	
			
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();	
			
			logger.info("Entrando a ver reporte de resumen obs mensuales PDF");
			
			long idGrupoInf = 0;
			if(FormatoUtil.isNotBlank(r.getGrupoInfBusq())){
				idGrupoInf = Long.valueOf(r.getGrupoInfBusq());
			}
			logger.info("codEmpresa:  "+r.getCodEmpresaBusq());
			logger.info("grupo inf:  "+idGrupoInf);
			logger.info("desc grupo inf:  "+r.getDesGrupoInf());			
			logger.info("etapa:  "+r.getEtapaMBusq());
			logger.info("grupo inf:  "+idGrupoInf);
			logger.info("formato mensual:  "+r.getOptionMensual());
			if(FormatoUtil.isNotBlank(r.getCodEmpresaBusq()) && "TODOS".equals(r.getCodEmpresaBusq())){ 
			   r.setCodEmpresaBusq("");	
			}			
		    String rutaImg = session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg");
		    ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);	
		    JSONObject jsonObj = new JSONObject();	 
		    Map<String, Object> mapa = new  HashMap<String, Object>();
		    mapa.put("IMG", rutaImg);		   
			mapa.put(JRParameter.REPORT_LOCALE, Locale.US);			  
			String usuario = themeDisplay.getUser().getLogin();
			String desEmpresa = "";
			String desMes = "";
			String anioPres = "";
			String etapa = "";
			String nombreFormato= "";
			String codEmpresa = "";
			
			 String nombreReporte = "";   
			
		    if(FiseConstants.NOMBRE_FORMATO_12A.equals(r.getOptionMensual())){ 	    	
		    	List<FiseFormato12AC> lista = formatoService12A.buscarFormato12AReporteObs(r.getCodEmpresaBusq(), 
		    			idGrupoInf, r.getEtapaMBusq());	
		    	logger.info("Tamanio de la lista f12A cabecera:   "+lista.size()); 
		    	
		    	nombreReporte = "validacionReporte";
		    	
		    	for(FiseFormato12AC f: lista){
		    		codEmpresa = f.getId().getCodEmpresa();
		    		desEmpresa = mapaEmpresa.get(f.getId().getCodEmpresa());
		    		desMes = fiseUtil.getMapaMeses().get(f.getId().getMesPresentacion());
		    		anioPres = ""+f.getId().getAnoPresentacion();
		    		etapa = f.getId().getEtapa();
		    		CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12A);		        	
		        	if( tabla!=null ){
		        		nombreFormato = tabla.getDescripcionTabla();
		        	}     		
		    		
		    		for (FiseFormato12AD detalle : f.getFiseFormato12ADs()) {
		    			detalle.setFiseFormato12ADObs(formatoService12A.listarFormato12ADObByFormato12AD(detalle));
		    			List<FiseFormato12ADOb> listaObser = formatoService12A.listarFormato12ADObByFormato12AD(detalle);
		    			for (FiseFormato12ADOb observacion : listaObser) {		    				
		    				ResumenObsBean obs = new ResumenObsBean();	
		    				obs.setDesEmpresa(desEmpresa); 
		    				obs.setCodEmpresa(codEmpresa);
		    				logger.info("Empresa:  "+obs.getDesEmpresa());
		    	        	obs.setPeriodo(anioPres+"/"+desMes); 
		    	        	logger.info("Periodo:  "+obs.getPeriodo());
		    				obs.setFormato(nombreFormato);
		    				logger.info("Formato:  "+obs.getFormato());
		    				obs.setEtapa(etapa);	
		    				obs.setUsuario(usuario); 
		    	        	obs.setDescZonaBenef(fiseUtil.getMapaZonaBenef().get(observacion.getId().getIdZonaBenef()));
		    				obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
		    				obs.setDescripcion(observacion.getFiseObservacion().getDescripcion());		    				
		    				listaF12.add(obs);	    				
		    			}
		    		}		    		
		    	}//fin del for lista cabecera    	
		    }else if(FiseConstants.NOMBRE_FORMATO_12B.equals(r.getOptionMensual())){ 	    	
		    	List<FiseFormato12BC> lista = formatoService12B.buscarFormato12BCReporteObs(r.getCodEmpresaBusq(), 
		    			idGrupoInf, r.getEtapaMBusq());		    	
		    	logger.info("Tamanio de la lista f12B cabecera:   "+lista.size());
		    	
		    	nombreReporte = "validacionReporte";
		    	
		    	for(FiseFormato12BC f: lista){
		    		codEmpresa = f.getId().getCodEmpresa();
		    		desEmpresa = mapaEmpresa.get(f.getId().getCodEmpresa());
		    		desMes = fiseUtil.getMapaMeses().get(f.getId().getMesPresentacion());
		    		anioPres = ""+f.getId().getAnoPresentacion();
		    		etapa = f.getId().getEtapa();
		    		CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12B);		        	
		        	if( tabla!=null ){
		        		nombreFormato = tabla.getDescripcionTabla();
		        	}
		        	
		    		for (FiseFormato12BD detalle : f.getListaDetalle12BDs()) {
		    			detalle.setFiseFormato12BDObs(formatoService12B.getLstFormatoObs(detalle));			
		    			List<FiseFormato12BDOb> listaObser = formatoService12B.getLstFormatoObs(detalle);
		    			for (FiseFormato12BDOb observacion : listaObser) {		    				
		    				ResumenObsBean obs = new ResumenObsBean();	
		    				obs.setDesEmpresa(desEmpresa); 
		    				obs.setCodEmpresa(codEmpresa);
		    				logger.info("Empresa:  "+obs.getDesEmpresa());
		    	        	obs.setPeriodo(anioPres+"/"+desMes); 
		    	        	logger.info("Periodo:  "+obs.getPeriodo());
		    				obs.setFormato(nombreFormato);
		    				logger.info("Formato:  "+obs.getFormato());
		    				obs.setEtapa(etapa);	
		    				obs.setUsuario(usuario); 
		    	        	obs.setDescZonaBenef(fiseUtil.getMapaZonaBenef().get(observacion.getId().getIdZonaBenef()));
		    				obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
		    				obs.setDescripcion(observacion.getFiseObservacion().getDescripcion());		    			
		    				listaF12.add(obs);
		    			}
		    		}		    		
		    	}//fin del for lista cabecera    	
		    }else if(FiseConstants.NOMBRE_FORMATO_12C.equals(r.getOptionMensual())){ 	    	
		    	List<FiseFormato12CC> lista = formatoService12C.buscarFormato12CReporteObs(r.getCodEmpresaBusq(), 
		    			idGrupoInf, r.getEtapaMBusq());		    	
		    	logger.info("Tamanio de la lista f12C cabecera:   "+lista.size()); 
		    	
		    	nombreReporte = "validacion12Reporte";
		    	
		    	for(FiseFormato12CC f: lista){
		    		codEmpresa = f.getId().getCodEmpresa();
		    		desEmpresa = mapaEmpresa.get(f.getId().getCodEmpresa());
		    		desMes = fiseUtil.getMapaMeses().get(f.getId().getMesPresentacion());
		    		anioPres = ""+f.getId().getAnoPresentacion();
		    		etapa = f.getId().getEtapa();
		    		CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12C);		        	
		        	if( tabla!=null ){
		        		nombreFormato = tabla.getDescripcionTabla();
		        	}
		        	
		    		for (FiseFormato12CD detalle : f.getFiseFormato12CDs()) {
		    			List<FiseFormato12CDOb> listaObser = formatoService12C.listarFormato12CDObByFormato12CD(detalle);
		    			for (FiseFormato12CDOb observacion : listaObser) {		    				
		    				ResumenObsBean obs = new ResumenObsBean();	
		    				obs.setDesEmpresa(desEmpresa); 
		    				obs.setCodEmpresa(codEmpresa);
		    				logger.info("Empresa:  "+obs.getDesEmpresa());
		    	        	obs.setPeriodo(anioPres+"/"+desMes); 
		    	        	logger.info("Periodo:  "+obs.getPeriodo());
		    				obs.setFormato(nombreFormato);
		    				logger.info("Formato:  "+obs.getFormato());
		    				obs.setEtapa(etapa);	
		    				obs.setUsuario(usuario); 
		    				obs.setNroItemEtapa(""+observacion.getId().getNumeroItemEtapa());
		    				obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
		    				obs.setDescripcion(observacion.getFiseObservacion().getDescripcion());
		    				obs.setDescEtapaEjecucion(mapaEtapaEjecucion.get(observacion.getId().getEtapaEjecucion()));
		    				listaF12.add(obs);
		    			}
		    		}		    		
		    	}//fin del for lista cabecera    	
		    }else if(FiseConstants.NOMBRE_FORMATO_12D.equals(r.getOptionMensual())){ 	    	
		    	List<FiseFormato12DC> lista = formatoService12D.buscarFormato12DReporteObs(r.getCodEmpresaBusq(), 
		    			idGrupoInf, r.getEtapaMBusq());		    	
		    	logger.info("Tamanio de la lista f12D cabecera:   "+lista.size()); 
		    	
		    	nombreReporte = "validacion12Reporte";
		    	
		    	for(FiseFormato12DC f: lista){
		    		codEmpresa = f.getId().getCodEmpresa();
		    		desEmpresa = mapaEmpresa.get(f.getId().getCodEmpresa());
		    		desMes = fiseUtil.getMapaMeses().get(f.getId().getMesPresentacion());
		    		anioPres = ""+f.getId().getAnoPresentacion();
		    		etapa = f.getId().getEtapa();
		    		CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12D);		        	
		        	if( tabla!=null ){
		        		nombreFormato = tabla.getDescripcionTabla();
		        	}
		        	
		    		for (FiseFormato12DD detalle : f.getFiseFormato12DDs()) {
		    			List<FiseFormato12DDOb> listaObser = formatoService12D.listarFormato12DDObByFormato12DD(detalle);
		    			for (FiseFormato12DDOb observacion : listaObser) {		    				
		    				ResumenObsBean obs = new ResumenObsBean();	
		    				obs.setDesEmpresa(desEmpresa); 
		    				obs.setCodEmpresa(codEmpresa);
		    				logger.info("Empresa:  "+obs.getDesEmpresa());
		    	        	obs.setPeriodo(anioPres+"/"+desMes); 
		    	        	logger.info("Periodo:  "+obs.getPeriodo());
		    				obs.setFormato(nombreFormato);
		    				logger.info("Formato:  "+obs.getFormato());
		    				obs.setEtapa(etapa);	
		    				obs.setUsuario(usuario); 
		    				obs.setNroItemEtapa(""+observacion.getId().getNumeroItemEtapa());
		    				obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
		    				obs.setDescripcion(observacion.getFiseObservacion().getDescripcion());
		    				obs.setDescEtapaEjecucion(mapaEtapaEjecucion.get(observacion.getId().getEtapaEjecucion()));
		    				listaF12.add(obs);
		    			}
		    		}		    		
		    	}//fin del for lista cabecera    	
		    }	    
		    
		    String tipoFormato = "REPORTE DE OBSERVACIONES MENSUALES ";
		    String tipoArchivo = "3";//PDF		   
		    session.setAttribute("tipoFormato",tipoFormato);
		    session.setAttribute("tipoArchivo",tipoArchivo);	   		    	
		    String directorio = "/reports/" + nombreReporte + ".jasper";
		      
		    
		    if(listaF12!=null && listaF12.size()>0){
		    	File reportFile = new File(session.getServletContext().getRealPath(directorio));
		    	bytesF12 = JasperRunManager.runReportToPdf(reportFile.getPath(), mapa, 
		    			new JRBeanCollectionDataSource(listaF12));
		    	if (bytesF12 != null) {				  	  		    		
		    		session.setAttribute("bytesFormato", bytesF12);
		    		jsonObj.put("resultado", "OK");	   	
		    	}else{
		    		jsonObj.put("resultado", "ERROR");	   
		    	}
		    }else{
		    	jsonObj.put("resultado", "VACIO");	   
		    }		
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();		  
			logger.info(jsonObj.toString());
			pw.write(jsonObj.toString());
			pw.flush();
			pw.close();	    
		 } catch (Exception e) {
			logger.error("Error al ver  resumen de obs. mensuales pdf: "+e); 
			e.printStackTrace();
		}finally{
			if(listaF12!=null){
				listaF12 =null;
			}
			if(bytesF12!=null){
				bytesF12=null;
			}
		}
    }
	
	
	@ResourceMapping("verResumenObsMensualesExcel")
	public void verResumenObsMensualesExcel(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("resumenObsBean")ResumenObsBean r) {		
		
		List<ResumenObsBean> listaF12 =new ArrayList<ResumenObsBean>();	
		
		try {	
			
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();	
			
			logger.info("Entrando a ver reporte de resumen obs. mensuales excel");
			
			long idGrupoInf = 0;
			if(FormatoUtil.isNotBlank(r.getGrupoInfBusq())){
				idGrupoInf = Long.valueOf(r.getGrupoInfBusq());
			}
			logger.info("codEmpresa:  "+r.getCodEmpresaBusq());
			logger.info("grupo inf:  "+idGrupoInf);
			logger.info("desc grupo inf:  "+r.getDesGrupoInf());			
			logger.info("etapa:  "+r.getEtapaMBusq());
			logger.info("grupo inf:  "+idGrupoInf);
			logger.info("formato mensual:  "+r.getOptionMensual());
			
			 String rutaImg = session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg");
			 ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);	
			 JSONObject jsonObj = new JSONObject();	 
			 Map<String, Object> mapa = new  HashMap<String, Object>();
			 mapa.put("IMG", rutaImg);		   
			 mapa.put(JRParameter.REPORT_LOCALE, Locale.US);			  
			 String usuario = themeDisplay.getUser().getLogin();
			 String desEmpresa = "";
			 String desMes = "";
			 String anioPres = "";
			 String etapa = "";
			 String nombreFormato= "";
			 String codEmpresa = "";

			 String nombreReporte = ""; 
			 
			 if(FiseConstants.NOMBRE_FORMATO_12A.equals(r.getOptionMensual())){ 	    	
				 List<FiseFormato12AC> lista = formatoService12A.buscarFormato12AReporteObs(r.getCodEmpresaBusq(), 
						 idGrupoInf, r.getEtapaMBusq());	
				 logger.info("Tamanio de la lista f12A cabecera:   "+lista.size()); 

				 nombreReporte = "validacionReporte";

				 for(FiseFormato12AC f: lista){
					 codEmpresa = f.getId().getCodEmpresa();
					 desEmpresa = mapaEmpresa.get(f.getId().getCodEmpresa());
					 desMes = fiseUtil.getMapaMeses().get(f.getId().getMesPresentacion());
					 anioPres = ""+f.getId().getAnoPresentacion();
					 etapa = f.getId().getEtapa();
					 CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12A);		        	
					 if( tabla!=null ){
						 nombreFormato = tabla.getDescripcionTabla();
					 }     		

					 for (FiseFormato12AD detalle : f.getFiseFormato12ADs()) {
						 detalle.setFiseFormato12ADObs(formatoService12A.listarFormato12ADObByFormato12AD(detalle));
						 List<FiseFormato12ADOb> listaObser = formatoService12A.listarFormato12ADObByFormato12AD(detalle);
						 for (FiseFormato12ADOb observacion : listaObser) {		    				
							 ResumenObsBean obs = new ResumenObsBean();	
							 obs.setDesEmpresa(desEmpresa); 
							 obs.setCodEmpresa(codEmpresa);
							 logger.info("Empresa:  "+obs.getDesEmpresa());
							 obs.setPeriodo(anioPres+"/"+desMes); 
							 logger.info("Periodo:  "+obs.getPeriodo());
							 obs.setFormato(nombreFormato);
							 logger.info("Formato:  "+obs.getFormato());
							 obs.setEtapa(etapa);	
							 obs.setUsuario(usuario); 
							 obs.setDescZonaBenef(fiseUtil.getMapaZonaBenef().get(observacion.getId().getIdZonaBenef()));
							 obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
							 obs.setDescripcion(observacion.getFiseObservacion().getDescripcion());		    			
							 listaF12.add(obs);
						 }
					 }		    		
				 }//fin del for lista cabecera    	
			 }else if(FiseConstants.NOMBRE_FORMATO_12B.equals(r.getOptionMensual())){ 	    	
				 List<FiseFormato12BC> lista = formatoService12B.buscarFormato12BCReporteObs(r.getCodEmpresaBusq(), 
						 idGrupoInf, r.getEtapaMBusq());		    	
				 logger.info("Tamanio de la lista f12B cabecera:   "+lista.size());

				 nombreReporte = "validacionReporte";

				 for(FiseFormato12BC f: lista){
					 codEmpresa = f.getId().getCodEmpresa();
					 desEmpresa = mapaEmpresa.get(f.getId().getCodEmpresa());
					 desMes = fiseUtil.getMapaMeses().get(f.getId().getMesPresentacion());
					 anioPres = ""+f.getId().getAnoPresentacion();
					 etapa = f.getId().getEtapa();
					 CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12B);		        	
					 if( tabla!=null ){
						 nombreFormato = tabla.getDescripcionTabla();
					 }

					 for (FiseFormato12BD detalle : f.getListaDetalle12BDs()) {
						 detalle.setFiseFormato12BDObs(formatoService12B.getLstFormatoObs(detalle));			
						 List<FiseFormato12BDOb> listaObser = formatoService12B.getLstFormatoObs(detalle);
						 for (FiseFormato12BDOb observacion : listaObser) {		    				
							 ResumenObsBean obs = new ResumenObsBean();	
							 obs.setDesEmpresa(desEmpresa); 
							 obs.setCodEmpresa(codEmpresa);
							 logger.info("Empresa:  "+obs.getDesEmpresa());
							 obs.setPeriodo(anioPres+"/"+desMes); 
							 logger.info("Periodo:  "+obs.getPeriodo());
							 obs.setFormato(nombreFormato);
							 logger.info("Formato:  "+obs.getFormato());
							 obs.setEtapa(etapa);	
							 obs.setUsuario(usuario); 
							 obs.setDescZonaBenef(fiseUtil.getMapaZonaBenef().get(observacion.getId().getIdZonaBenef()));
							 obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
							 obs.setDescripcion(observacion.getFiseObservacion().getDescripcion());		    			
							 listaF12.add(obs);
						 }
					 }		    		
				 }//fin del for lista cabecera    	
			 }else if(FiseConstants.NOMBRE_FORMATO_12C.equals(r.getOptionMensual())){ 	    	
				 List<FiseFormato12CC> lista = formatoService12C.buscarFormato12CReporteObs(r.getCodEmpresaBusq(), 
						 idGrupoInf, r.getEtapaMBusq());		    	
				 logger.info("Tamanio de la lista f12C cabecera:   "+lista.size()); 

				 nombreReporte = "validacion12Reporte";

				 for(FiseFormato12CC f: lista){
					 codEmpresa = f.getId().getCodEmpresa();
					 desEmpresa = mapaEmpresa.get(f.getId().getCodEmpresa());
					 desMes = fiseUtil.getMapaMeses().get(f.getId().getMesPresentacion());
					 anioPres = ""+f.getId().getAnoPresentacion();
					 etapa = f.getId().getEtapa();
					 CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12C);		        	
					 if( tabla!=null ){
						 nombreFormato = tabla.getDescripcionTabla();
					 }

					 for (FiseFormato12CD detalle : f.getFiseFormato12CDs()) {
						 List<FiseFormato12CDOb> listaObser = formatoService12C.listarFormato12CDObByFormato12CD(detalle);
						 for (FiseFormato12CDOb observacion : listaObser) {		    				
							 ResumenObsBean obs = new ResumenObsBean();	
							 obs.setDesEmpresa(desEmpresa); 
							 obs.setCodEmpresa(codEmpresa);
							 logger.info("Empresa:  "+obs.getDesEmpresa());
							 obs.setPeriodo(anioPres+"/"+desMes); 
							 logger.info("Periodo:  "+obs.getPeriodo());
							 obs.setFormato(nombreFormato);
							 logger.info("Formato:  "+obs.getFormato());
							 obs.setEtapa(etapa);	
							 obs.setUsuario(usuario); 
							 obs.setNroItemEtapa(""+observacion.getId().getNumeroItemEtapa());
							 obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
							 obs.setDescripcion(observacion.getFiseObservacion().getDescripcion());
							 obs.setDescEtapaEjecucion(mapaEtapaEjecucion.get(observacion.getId().getEtapaEjecucion()));
							 listaF12.add(obs);
						 }
					 }		    		
				 }//fin del for lista cabecera    	
			 }else if(FiseConstants.NOMBRE_FORMATO_12D.equals(r.getOptionMensual())){ 	    	
				 List<FiseFormato12DC> lista = formatoService12D.buscarFormato12DReporteObs(r.getCodEmpresaBusq(), 
						 idGrupoInf, r.getEtapaMBusq());		    	
				 logger.info("Tamanio de la lista f12D cabecera:   "+lista.size()); 

				 nombreReporte = "validacion12Reporte";

				 for(FiseFormato12DC f: lista){
					 codEmpresa = f.getId().getCodEmpresa();
					 desEmpresa = mapaEmpresa.get(f.getId().getCodEmpresa());
					 desMes = fiseUtil.getMapaMeses().get(f.getId().getMesPresentacion());
					 anioPres = ""+f.getId().getAnoPresentacion();
					 etapa = f.getId().getEtapa();
					 CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12D);		        	
					 if( tabla!=null ){
						 nombreFormato = tabla.getDescripcionTabla();
					 }

					 for (FiseFormato12DD detalle : f.getFiseFormato12DDs()) {
						 List<FiseFormato12DDOb> listaObser = formatoService12D.listarFormato12DDObByFormato12DD(detalle);
						 for (FiseFormato12DDOb observacion : listaObser) {		    				
							 ResumenObsBean obs = new ResumenObsBean();	
							 obs.setDesEmpresa(desEmpresa); 
							 obs.setCodEmpresa(codEmpresa);
							 logger.info("Empresa:  "+obs.getDesEmpresa());
							 obs.setPeriodo(anioPres+"/"+desMes); 
							 logger.info("Periodo:  "+obs.getPeriodo());
							 obs.setFormato(nombreFormato);
							 logger.info("Formato:  "+obs.getFormato());
							 obs.setEtapa(etapa);	
							 obs.setUsuario(usuario); 
							 obs.setNroItemEtapa(""+observacion.getId().getNumeroItemEtapa());
							 obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
							 obs.setDescripcion(observacion.getFiseObservacion().getDescripcion());
							 obs.setDescEtapaEjecucion(mapaEtapaEjecucion.get(observacion.getId().getEtapaEjecucion()));
							 listaF12.add(obs);
						 }
					 }		    		
				 }//fin del for lista cabecera    	
			 }	    			 
		    
			 String tipoFormato = FiseConstants.TIPO_FORMATO_REPORTE_OBS;
			 String tipoArchivo = "1";//exel				  
			 String nombreArchivo ="REPORTE_OBS_MENSUALES";   	    
		      
		    
		    if(listaF12!=null && listaF12.size()>0){
		    	session.setAttribute("tipoFormato",tipoFormato);
		    	session.setAttribute("tipoArchivo",tipoArchivo);
		    	session.setAttribute("nombreReporte",nombreReporte);
		    	session.setAttribute("nombreArchivo",nombreArchivo);
		    	session.setAttribute("lista", listaF12);
		    	session.setAttribute("mapa", mapa);
		    	jsonObj.put("resultado", "OK");	 
		    }else{
		    	jsonObj.put("resultado", "VACIO");	   
		    }		
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();		  
			logger.info(jsonObj.toString());
			pw.write(jsonObj.toString());
			pw.flush();
			pw.close();	    
		 } catch (Exception e) {
			logger.error("Error al ver  resumen de obs. mensuales exel: "+e); 
			e.printStackTrace();
		}finally{
			if(listaF12!=null){
				listaF12 =null;
			}		
		}
    }
	
	/**Reporte de observaciones para bienales****/
	
	@ResourceMapping("verResumenObsBienalesPdf")
	public void verResumenObsBienalesPdf(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("resumenObsBean")ResumenObsBean r) {
		
		List<ResumenObsBean> listaF14 =new ArrayList<ResumenObsBean>();
		
		byte[] bytesF14 = null;
		
		try {	
			
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();	
			
			logger.info("Entrando a ver reporte de resumen obs bienales PDF");
			
			long idGrupoInf = 0;
			if(FormatoUtil.isNotBlank(r.getGrupoInfBusq())){
				idGrupoInf = Long.valueOf(r.getGrupoInfBusq());
			}
			logger.info("codEmpresa:  "+r.getCodEmpresaBusq());
			logger.info("grupo inf:  "+idGrupoInf);
			logger.info("desc grupo inf:  "+r.getDesGrupoInf());			
			logger.info("etapa:  "+r.getEtapaBBusq());
			logger.info("grupo inf:  "+idGrupoInf);
			logger.info("formato bienal:  "+r.getOptionBienal());
			if(FormatoUtil.isNotBlank(r.getCodEmpresaBusq()) && "TODOS".equals(r.getCodEmpresaBusq())){ 
			   r.setCodEmpresaBusq("");	
			}			
		    String rutaImg = session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg");
		    ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);	
		    JSONObject jsonObj = new JSONObject();	 
		    Map<String, Object> mapa = new  HashMap<String, Object>();
		    mapa.put("IMG", rutaImg);		   
			mapa.put(JRParameter.REPORT_LOCALE, Locale.US);			  
			String usuario = themeDisplay.getUser().getLogin();
			String desEmpresa = "";
			String desMes = "";
			String anioPres = "";
			String etapa = "";
			String nombreFormato= "";
			String codEmpresa = "";
			String anioIniVig= "";
			String anioFinVig= "";
			
			 String nombreReporte = "";   
			
		    if(FiseConstants.NOMBRE_FORMATO_13A.equals(r.getOptionBienal())){ 	    	
		    	List<FiseFormato13AC> lista = formatoService13A.buscarFormato13AReporteObs(r.getCodEmpresaBusq(), 
		    			idGrupoInf, r.getEtapaBBusq());	
		    	logger.info("Tamanio de la lista f13A cabecera:   "+lista.size()); 
		    	
		    	nombreReporte = "validacion13Reporte";
		    	
		    	for(FiseFormato13AC f: lista){
		    		codEmpresa = f.getId().getCodEmpresa();
		    		desEmpresa = mapaEmpresa.get(f.getId().getCodEmpresa());
		    		desMes = fiseUtil.getMapaMeses().get(f.getId().getMesPresentacion());
		    		anioPres = ""+f.getId().getAnoPresentacion();
		    		etapa = f.getId().getEtapa();		    		
		    		CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO13A);		        	
		        	if( tabla!=null ){
		        		nombreFormato = tabla.getDescripcionTabla();
		        	}     		
		    		
		    		for (FiseFormato13AD detalle : f.getFiseFormato13ADs()) {
		    			anioIniVig = detalle.getAnoInicioVigencia()==null?" ":String.valueOf(detalle.getAnoInicioVigencia());
		    			anioFinVig = detalle.getAnoFinVigencia()==null?" ":String.valueOf(detalle.getAnoFinVigencia());
		    			List<FiseFormato13ADOb> listaObser = formatoService13A.listarFormato13ADObByFormato13AD(detalle);
		    			for (FiseFormato13ADOb observacion : listaObser) {		    				
		    				ResumenObsBean obs = new ResumenObsBean();	
		    				obs.setDesEmpresa(desEmpresa); 
		    				obs.setCodEmpresa(codEmpresa);
		    				logger.info("Empresa:  "+obs.getDesEmpresa());
		    	        	obs.setPeriodo(anioPres+"/"+desMes); 
		    	        	logger.info("Periodo:  "+obs.getPeriodo());
		    				obs.setFormato(nombreFormato);
		    				logger.info("Formato:  "+obs.getFormato());
		    				obs.setEtapa(etapa);	
		    				obs.setUsuario(usuario); 
		    				obs.setAnioInicioVig(anioIniVig);
		    				obs.setAnioFinVig(anioFinVig); 
		    	        	obs.setDescZonaBenef(fiseUtil.getMapaZonaBenef().get(observacion.getId().getIdZonaBenef()));
		    				obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
		    				obs.setDescripcion(observacion.getFiseObservacion().getDescripcion());		    				
		    				obs.setDescCodSectorTipico(mapaSectorTipico.get(observacion.getId().getCodSectorTipico()));
		    				listaF14.add(obs);	    				
		    			}
		    		}		    		
		    	}//fin del for lista cabecera    	
		    }else if(FiseConstants.NOMBRE_FORMATO_14A.equals(r.getOptionBienal())){ 	    	
		    	List<FiseFormato14AC> lista = formatoService14A.buscarFormato14AReporteObs(r.getCodEmpresaBusq(), 
		    			idGrupoInf, r.getEtapaBBusq());		    	
		    	logger.info("Tamanio de la lista f14A cabecera:   "+lista.size());
		    	
		    	nombreReporte = "validacionReporte";
		    	
		    	for(FiseFormato14AC f: lista){
		    		codEmpresa = f.getId().getCodEmpresa();
		    		desEmpresa = mapaEmpresa.get(f.getId().getCodEmpresa());
		    		desMes = fiseUtil.getMapaMeses().get(f.getId().getMesPresentacion());
		    		anioPres = ""+f.getId().getAnoPresentacion();
		    		etapa = f.getId().getEtapa();
		    		CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14A);		        	
		        	if( tabla!=null ){
		        		nombreFormato = tabla.getDescripcionTabla();
		        	}
		        	
		    		for (FiseFormato14AD detalle : f.getFiseFormato14ADs()) {
		    			detalle.setFiseFormato14ADObs(formatoService14A.listarFormato14ADObByFormato14AD(detalle));
		    			List<FiseFormato14ADOb> listaObser = formatoService14A.listarFormato14ADObByFormato14AD(detalle);
		    			for (FiseFormato14ADOb observacion : listaObser) {		    				
		    				ResumenObsBean obs = new ResumenObsBean();	
		    				obs.setDesEmpresa(desEmpresa); 
		    				obs.setCodEmpresa(codEmpresa);
		    				logger.info("Empresa:  "+obs.getDesEmpresa());
		    	        	obs.setPeriodo(anioPres+"/"+desMes); 
		    	        	logger.info("Periodo:  "+obs.getPeriodo());
		    				obs.setFormato(nombreFormato);
		    				logger.info("Formato:  "+obs.getFormato());
		    				obs.setEtapa(etapa);	
		    				obs.setUsuario(usuario); 
		    	        	obs.setDescZonaBenef(fiseUtil.getMapaZonaBenef().get(observacion.getId().getIdZonaBenef()));
		    				obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
		    				obs.setDescripcion(observacion.getFiseObservacion().getDescripcion());		    			
		    				listaF14.add(obs);
		    			}
		    		}		    		
		    	}//fin del for lista cabecera    	
		    }else if(FiseConstants.NOMBRE_FORMATO_14B.equals(r.getOptionBienal())){ 	    	
		    	List<FiseFormato14BC> lista = formatoService14B.buscarFormato14BReporteObs(r.getCodEmpresaBusq(), 
		    			idGrupoInf, r.getEtapaBBusq());		    	
		    	logger.info("Tamanio de la lista f14B cabecera:   "+lista.size()); 
		    	
		    	nombreReporte = "validacionReporte";
		    	
		    	for(FiseFormato14BC f: lista){
		    		codEmpresa = f.getId().getCodEmpresa();
		    		desEmpresa = mapaEmpresa.get(f.getId().getCodEmpresa());
		    		desMes = fiseUtil.getMapaMeses().get(f.getId().getMesPresentacion());
		    		anioPres = ""+f.getId().getAnoPresentacion();
		    		etapa = f.getId().getEtapa();
		    		CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14B);		        	
		        	if( tabla!=null ){
		        		nombreFormato = tabla.getDescripcionTabla();
		        	}
		        	
		    		for (FiseFormato14BD detalle : f.getFiseFormato14BDs()) {
		    			detalle.setFiseFormato14BDObs(formatoService14B.listarFormato14BDObByFormato14BD(detalle));
		    			List<FiseFormato14BDOb> listaObser = formatoService14B.listarFormato14BDObByFormato14BD(detalle);
		    			for (FiseFormato14BDOb observacion : listaObser) {		    				
		    				ResumenObsBean obs = new ResumenObsBean();	
		    				obs.setDesEmpresa(desEmpresa); 
		    				obs.setCodEmpresa(codEmpresa);
		    				logger.info("Empresa:  "+obs.getDesEmpresa());
		    	        	obs.setPeriodo(anioPres+"/"+desMes); 
		    	        	logger.info("Periodo:  "+obs.getPeriodo());
		    				obs.setFormato(nombreFormato);
		    				logger.info("Formato:  "+obs.getFormato());
		    				obs.setEtapa(etapa);	
		    				obs.setUsuario(usuario); 		    				
		    				obs.setDescZonaBenef(fiseUtil.getMapaZonaBenef().get(observacion.getId().getIdZonaBenef()));
		    				obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
		    				obs.setDescripcion(observacion.getFiseObservacion().getDescripcion());	
		    				listaF14.add(obs);
		    			}
		    		}		    		
		    	}//fin del for lista cabecera    	
		    }else if(FiseConstants.NOMBRE_FORMATO_14C.equals(r.getOptionBienal())){ 	    	
		    	List<FiseFormato14CC> lista = formatoService14C.buscarFormato14CReporteObs(r.getCodEmpresaBusq(), 
		    			idGrupoInf, r.getEtapaBBusq());		    	
		    	logger.info("Tamanio de la lista f14C cabecera:   "+lista.size()); 
		    	
		    	nombreReporte = "validacionReporte";
		    	
		    	for(FiseFormato14CC f: lista){
		    		codEmpresa = f.getId().getCodEmpresa();
		    		desEmpresa = mapaEmpresa.get(f.getId().getCodEmpresa());
		    		desMes = fiseUtil.getMapaMeses().get(f.getId().getMesPresentacion());
		    		anioPres = ""+f.getId().getAnoPresentacion();
		    		etapa = f.getId().getEtapa();
		    		CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14C);		        	
		        	if( tabla!=null ){
		        		nombreFormato = tabla.getDescripcionTabla();
		        	}
		        	
		    		for (FiseFormato14CD detalle : f.getListaDetalle14cDs()) {
		    			List<FiseFormato14CDOb> listaObser = formatoService14C.listaObservacionesF14C(detalle);
		    			for (FiseFormato14CDOb observacion : listaObser) {		    				
		    				ResumenObsBean obs = new ResumenObsBean();	
		    				obs.setDesEmpresa(desEmpresa); 
		    				obs.setCodEmpresa(codEmpresa);
		    				logger.info("Empresa:  "+obs.getDesEmpresa());
		    	        	obs.setPeriodo(anioPres+"/"+desMes); 
		    	        	logger.info("Periodo:  "+obs.getPeriodo());
		    				obs.setFormato(nombreFormato);
		    				logger.info("Formato:  "+obs.getFormato());
		    				obs.setEtapa(etapa);	
		    				obs.setUsuario(usuario); 		    				
		    				obs.setDescZonaBenef(fiseUtil.getMapaZonaBenef().get(observacion.getId().getIdZonaBenef()));
		    				obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
		    				obs.setDescripcion(observacion.getFiseObservacion().getDescripcion());	
		    				listaF14.add(obs);
		    			}
		    		}		    		
		    	}//fin del for lista cabecera    	
		    }	    
		    
		    String tipoFormato = "REPORTE DE OBSERVACIONES BIENALES ";
		    String tipoArchivo = "3";//PDF		   
		    session.setAttribute("tipoFormato",tipoFormato);
		    session.setAttribute("tipoArchivo",tipoArchivo);	   		    	
		    String directorio = "/reports/" + nombreReporte + ".jasper";
		      
		    
		    if(listaF14!=null && listaF14.size()>0){
		    	File reportFile = new File(session.getServletContext().getRealPath(directorio));
		    	bytesF14 = JasperRunManager.runReportToPdf(reportFile.getPath(), mapa, 
		    			new JRBeanCollectionDataSource(listaF14));
		    	if (bytesF14 != null) {				  	  		    		
		    		session.setAttribute("bytesFormato", bytesF14);
		    		jsonObj.put("resultado", "OK");	   	
		    	}else{
		    		jsonObj.put("resultado", "ERROR");	   
		    	}
		    }else{
		    	jsonObj.put("resultado", "VACIO");	   
		    }		
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();		  
			logger.info(jsonObj.toString());
			pw.write(jsonObj.toString());
			pw.flush();
			pw.close();	    
		 } catch (Exception e) {
			logger.error("Error al ver  resumen de obs. bienales pdf: "+e); 
			e.printStackTrace();
		}finally{
			if(listaF14!=null){
				listaF14 =null;
			}
			if(bytesF14!=null){
				bytesF14=null;
			}
		}
    }
	
	
	@ResourceMapping("verResumenObsBienalesExcel")
	public void verResumenObsBienalesExcel(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("resumenObsBean")ResumenObsBean r) {		
		
		List<ResumenObsBean> listaF14 =new ArrayList<ResumenObsBean>();	
		
		try {	
			
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();	
			
			logger.info("Entrando a ver reporte de resumen obs. bienales excel");
			
			long idGrupoInf = 0;
			if(FormatoUtil.isNotBlank(r.getGrupoInfBusq())){
				idGrupoInf = Long.valueOf(r.getGrupoInfBusq());
			}
			logger.info("codEmpresa:  "+r.getCodEmpresaBusq());
			logger.info("grupo inf:  "+idGrupoInf);
			logger.info("desc grupo inf:  "+r.getDesGrupoInf());			
			logger.info("etapa:  "+r.getEtapaMBusq());
			logger.info("grupo inf:  "+idGrupoInf);
			logger.info("formato mensual:  "+r.getOptionMensual());
			
			 String rutaImg = session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg");
			 ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);	
			 JSONObject jsonObj = new JSONObject();	 
			 Map<String, Object> mapa = new  HashMap<String, Object>();
			 mapa.put("IMG", rutaImg);		   
			 mapa.put(JRParameter.REPORT_LOCALE, Locale.US);			  
			 String usuario = themeDisplay.getUser().getLogin();
			 String desEmpresa = "";
			 String desMes = "";
			 String anioPres = "";
			 String etapa = "";
			 String nombreFormato= "";
			 String codEmpresa = "";
			 String anioIniVig = "";
			 String anioFinVig = "";

			 String nombreReporte = ""; 
			 
			 if(FiseConstants.NOMBRE_FORMATO_13A.equals(r.getOptionMensual())){ 	    	
				 List<FiseFormato13AC> lista = formatoService13A.buscarFormato13AReporteObs(r.getCodEmpresaBusq(), 
						 idGrupoInf, r.getEtapaMBusq());	
				 logger.info("Tamanio de la lista f13A cabecera:   "+lista.size()); 

				 nombreReporte = "validacion13Reporte";

				 for(FiseFormato13AC f: lista){
					 codEmpresa = f.getId().getCodEmpresa();
					 desEmpresa = mapaEmpresa.get(f.getId().getCodEmpresa());
					 desMes = fiseUtil.getMapaMeses().get(f.getId().getMesPresentacion());
					 anioPres = ""+f.getId().getAnoPresentacion();
					 etapa = f.getId().getEtapa();		    		
					 CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO13A);		        	
					 if( tabla!=null ){
						 nombreFormato = tabla.getDescripcionTabla();
					 }     		

					 for (FiseFormato13AD detalle : f.getFiseFormato13ADs()) {
						 anioIniVig = detalle.getAnoInicioVigencia()==null?" ":String.valueOf(detalle.getAnoInicioVigencia());
						 anioFinVig = detalle.getAnoFinVigencia()==null?" ":String.valueOf(detalle.getAnoFinVigencia());
						 List<FiseFormato13ADOb> listaObser = formatoService13A.listarFormato13ADObByFormato13AD(detalle);
						 for (FiseFormato13ADOb observacion : listaObser) {		    				
							 ResumenObsBean obs = new ResumenObsBean();	
							 obs.setDesEmpresa(desEmpresa); 
							 obs.setCodEmpresa(codEmpresa);
							 logger.info("Empresa:  "+obs.getDesEmpresa());
							 obs.setPeriodo(anioPres+"/"+desMes); 
							 logger.info("Periodo:  "+obs.getPeriodo());
							 obs.setFormato(nombreFormato);
							 logger.info("Formato:  "+obs.getFormato());
							 obs.setEtapa(etapa);	
							 obs.setUsuario(usuario); 
							 obs.setAnioInicioVig(anioIniVig);
							 obs.setAnioFinVig(anioFinVig); 
							 obs.setDescZonaBenef(fiseUtil.getMapaZonaBenef().get(observacion.getId().getIdZonaBenef()));
							 obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
							 obs.setDescripcion(observacion.getFiseObservacion().getDescripcion());		    				
							 obs.setDescCodSectorTipico(mapaSectorTipico.get(observacion.getId().getCodSectorTipico()));
							 listaF14.add(obs);	    				
						 }
					 }		    		
				 }//fin del for lista cabecera    	
			 }else if(FiseConstants.NOMBRE_FORMATO_14A.equals(r.getOptionMensual())){ 	    	
				 List<FiseFormato14AC> lista = formatoService14A.buscarFormato14AReporteObs(r.getCodEmpresaBusq(), 
						 idGrupoInf, r.getEtapaMBusq());		    	
				 logger.info("Tamanio de la lista f14A cabecera:   "+lista.size());

				 nombreReporte = "validacionReporte";

				 for(FiseFormato14AC f: lista){
					 codEmpresa = f.getId().getCodEmpresa();
					 desEmpresa = mapaEmpresa.get(f.getId().getCodEmpresa());
					 desMes = fiseUtil.getMapaMeses().get(f.getId().getMesPresentacion());
					 anioPres = ""+f.getId().getAnoPresentacion();
					 etapa = f.getId().getEtapa();
					 CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14A);		        	
					 if( tabla!=null ){
						 nombreFormato = tabla.getDescripcionTabla();
					 }

					 for (FiseFormato14AD detalle : f.getFiseFormato14ADs()) {
						 detalle.setFiseFormato14ADObs(formatoService14A.listarFormato14ADObByFormato14AD(detalle));
						 List<FiseFormato14ADOb> listaObser = formatoService14A.listarFormato14ADObByFormato14AD(detalle);
						 for (FiseFormato14ADOb observacion : listaObser) {		    				
							 ResumenObsBean obs = new ResumenObsBean();	
							 obs.setDesEmpresa(desEmpresa); 
							 obs.setCodEmpresa(codEmpresa);
							 logger.info("Empresa:  "+obs.getDesEmpresa());
							 obs.setPeriodo(anioPres+"/"+desMes); 
							 logger.info("Periodo:  "+obs.getPeriodo());
							 obs.setFormato(nombreFormato);
							 logger.info("Formato:  "+obs.getFormato());
							 obs.setEtapa(etapa);	
							 obs.setUsuario(usuario); 
							 obs.setDescZonaBenef(fiseUtil.getMapaZonaBenef().get(observacion.getId().getIdZonaBenef()));
							 obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
							 obs.setDescripcion(observacion.getFiseObservacion().getDescripcion());		    			
							 listaF14.add(obs);
						 }
					 }		    		
				 }//fin del for lista cabecera    	
			 }else if(FiseConstants.NOMBRE_FORMATO_14B.equals(r.getOptionMensual())){ 	    	
				 List<FiseFormato14BC> lista = formatoService14B.buscarFormato14BReporteObs(r.getCodEmpresaBusq(), 
						 idGrupoInf, r.getEtapaMBusq());		    	
				 logger.info("Tamanio de la lista f14B cabecera:   "+lista.size()); 

				 nombreReporte = "validacionReporte";

				 for(FiseFormato14BC f: lista){
					 codEmpresa = f.getId().getCodEmpresa();
					 desEmpresa = mapaEmpresa.get(f.getId().getCodEmpresa());
					 desMes = fiseUtil.getMapaMeses().get(f.getId().getMesPresentacion());
					 anioPres = ""+f.getId().getAnoPresentacion();
					 etapa = f.getId().getEtapa();
					 CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14B);		        	
					 if( tabla!=null ){
						 nombreFormato = tabla.getDescripcionTabla();
					 }

					 for (FiseFormato14BD detalle : f.getFiseFormato14BDs()) {
						 detalle.setFiseFormato14BDObs(formatoService14B.listarFormato14BDObByFormato14BD(detalle));
						 List<FiseFormato14BDOb> listaObser = formatoService14B.listarFormato14BDObByFormato14BD(detalle);
						 for (FiseFormato14BDOb observacion : listaObser) {		    				
							 ResumenObsBean obs = new ResumenObsBean();	
							 obs.setDesEmpresa(desEmpresa); 
							 obs.setCodEmpresa(codEmpresa);
							 logger.info("Empresa:  "+obs.getDesEmpresa());
							 obs.setPeriodo(anioPres+"/"+desMes); 
							 logger.info("Periodo:  "+obs.getPeriodo());
							 obs.setFormato(nombreFormato);
							 logger.info("Formato:  "+obs.getFormato());
							 obs.setEtapa(etapa);	
							 obs.setUsuario(usuario); 		    				
							 obs.setDescZonaBenef(fiseUtil.getMapaZonaBenef().get(observacion.getId().getIdZonaBenef()));
							 obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
							 obs.setDescripcion(observacion.getFiseObservacion().getDescripcion());	
							 listaF14.add(obs);
						 }
					 }		    		
				 }//fin del for lista cabecera    	
			 }else if(FiseConstants.NOMBRE_FORMATO_14C.equals(r.getOptionMensual())){ 	    	
				 List<FiseFormato14CC> lista = formatoService14C.buscarFormato14CReporteObs(r.getCodEmpresaBusq(), 
						 idGrupoInf, r.getEtapaMBusq());		    	
				 logger.info("Tamanio de la lista f14C cabecera:   "+lista.size()); 

				 nombreReporte = "validacionReporte";

				 for(FiseFormato14CC f: lista){
					 codEmpresa = f.getId().getCodEmpresa();
					 desEmpresa = mapaEmpresa.get(f.getId().getCodEmpresa());
					 desMes = fiseUtil.getMapaMeses().get(f.getId().getMesPresentacion());
					 anioPres = ""+f.getId().getAnoPresentacion();
					 etapa = f.getId().getEtapa();
					 CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14C);		        	
					 if( tabla!=null ){
						 nombreFormato = tabla.getDescripcionTabla();
					 }

					 for (FiseFormato14CD detalle : f.getListaDetalle14cDs()) {
						 List<FiseFormato14CDOb> listaObser = formatoService14C.listaObservacionesF14C(detalle);
						 for (FiseFormato14CDOb observacion : listaObser) {		    				
							 ResumenObsBean obs = new ResumenObsBean();	
							 obs.setDesEmpresa(desEmpresa); 
							 obs.setCodEmpresa(codEmpresa);
							 logger.info("Empresa:  "+obs.getDesEmpresa());
							 obs.setPeriodo(anioPres+"/"+desMes); 
							 logger.info("Periodo:  "+obs.getPeriodo());
							 obs.setFormato(nombreFormato);
							 logger.info("Formato:  "+obs.getFormato());
							 obs.setEtapa(etapa);	
							 obs.setUsuario(usuario); 		    				
							 obs.setDescZonaBenef(fiseUtil.getMapaZonaBenef().get(observacion.getId().getIdZonaBenef()));
							 obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
							 obs.setDescripcion(observacion.getFiseObservacion().getDescripcion());	
							 listaF14.add(obs);
						 }
					 }		    		
				 }//fin del for lista cabecera    	
			 }	    

			 String tipoFormato = FiseConstants.TIPO_FORMATO_REPORTE_OBS;
			 String tipoArchivo = "1";//exel				  
			 String nombreArchivo ="REPORTE_OBS_BIENALES";   	    
		      
		    
		    if(listaF14!=null && listaF14.size()>0){
		    	session.setAttribute("tipoFormato",tipoFormato);
		    	session.setAttribute("tipoArchivo",tipoArchivo);
		    	session.setAttribute("nombreReporte",nombreReporte);
		    	session.setAttribute("nombreArchivo",nombreArchivo);
		    	session.setAttribute("lista", listaF14);
		    	session.setAttribute("mapa", mapa);
		    	jsonObj.put("resultado", "OK");	 
		    }else{
		    	jsonObj.put("resultado", "VACIO");	   
		    }		
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();		  
			logger.info(jsonObj.toString());
			pw.write(jsonObj.toString());
			pw.flush();
			pw.close();	    
		 } catch (Exception e) {
			logger.error("Error al ver  resumen de obs. bienales exel: "+e); 
			e.printStackTrace();
		}finally{
			if(listaF14!=null){
				listaF14 =null;
			}		
		}
    }	
	
	
	
	
	
}
