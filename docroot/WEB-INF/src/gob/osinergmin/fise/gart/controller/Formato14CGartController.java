package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.Formato14AMensajeBean;
import gob.osinergmin.fise.bean.Formato14CBean;
import gob.osinergmin.fise.bean.Formato14CReportBean;
import gob.osinergmin.fise.bean.Formato14Generic;
import gob.osinergmin.fise.bean.MensajeErrorBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.CfgCampo;
import gob.osinergmin.fise.domain.CfgTabla;
import gob.osinergmin.fise.domain.FiseFormato14CC;
import gob.osinergmin.fise.domain.FiseFormato14CD;
import gob.osinergmin.fise.domain.FiseFormato14CDOb;
import gob.osinergmin.fise.domain.FisePeriodoEnvio;
import gob.osinergmin.fise.gart.json.Formato14CJSON;
import gob.osinergmin.fise.gart.jsp.FileEntryJSP;
import gob.osinergmin.fise.gart.service.CfgCampoGartService;
import gob.osinergmin.fise.gart.service.CfgTablaGartService;
import gob.osinergmin.fise.gart.service.CommonGartService;
import gob.osinergmin.fise.gart.service.FisePeriodoEnvioGartService;
import gob.osinergmin.fise.gart.service.Formato14CGartService;
import gob.osinergmin.fise.util.FechaUtil;
import gob.osinergmin.fise.util.FormatoUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
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
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.FileMimeTypeException;

@Controller("formato14CGartController")
@RequestMapping("VIEW")
public class Formato14CGartController {
	
	private static final Logger logger = Logger.getLogger(Formato14CGartController.class);
	
	@Autowired
	@Qualifier("formato14CGartServiceImpl")
	private Formato14CGartService formato14CGartService;
	
	@Autowired
	@Qualifier("fiseUtil")
	private FiseUtil fiseUtil;
	
	@Autowired
	@Qualifier("fisePeriodoEnvioGartServiceImpl")
	private FisePeriodoEnvioGartService periodoService;
	
	@Autowired
	@Qualifier("commonGartServiceImpl")
	private CommonGartService commonService;
	
	@Autowired
	@Qualifier("cfgTablaGartServiceImpl")
	private CfgTablaGartService tablaService;
	
	
	@Autowired
	@Qualifier("cfgCampoGartServiceImpl")
	private CfgCampoGartService campoService;
	
		
	private List<FisePeriodoEnvio> listaPeriodoEnvio;
	
	private Map<String, String> mapaEmpresa;	
	
	private Map<String, String> mapaErrores;
	private List<MensajeErrorBean> listaObservaciones;
	private Map<Long, String> mapaZonaBenef;
	
	
	@ModelAttribute("formato14CBean")
    public Formato14CBean listFiseFormato14CC() {
		Formato14CBean comman  = new Formato14CBean();
        return comman;	        
    }
	
	@SuppressWarnings("unchecked")
	@RequestMapping
	public String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse,
			     @ModelAttribute("formato14CBean")Formato14CBean f){
        try {
        	/***PARA MANEJAR CARGA DE EXEL  Y TEXT***/
        	PortletRequest pRequest = (PortletRequest)renderRequest.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
        	
        	String codEmpresa = (String)pRequest.getPortletSession().getAttribute("codEmpresa", PortletSession.APPLICATION_SCOPE);
    		String anioPresentacion = (String)pRequest.getPortletSession().getAttribute("anoPresentacion", PortletSession.APPLICATION_SCOPE);
    		String mesPresentacion = (String)pRequest.getPortletSession().getAttribute("mesPresentacion", PortletSession.APPLICATION_SCOPE);
    		String anoInicioVigencia = (String)pRequest.getPortletSession().getAttribute("anoInicioVigencia", PortletSession.APPLICATION_SCOPE);
    		String anoFinVigencia = (String)pRequest.getPortletSession().getAttribute("anoFinVigencia", PortletSession.APPLICATION_SCOPE);
    		String etapa = (String)pRequest.getPortletSession().getAttribute("etapa", PortletSession.APPLICATION_SCOPE);
    		String flag = (String)pRequest.getPortletSession().getAttribute("flag", PortletSession.APPLICATION_SCOPE);
    		String msgError = (String)pRequest.getPortletSession().getAttribute("mensajeError", PortletSession.APPLICATION_SCOPE);
    		List<MensajeErrorBean> listaError = (List<MensajeErrorBean>)pRequest.getPortletSession().getAttribute("listaError", PortletSession.APPLICATION_SCOPE);
    		String msgInfo = (String)pRequest.getPortletSession().getAttribute("mensajeInformacion", PortletSession.APPLICATION_SCOPE);
    		
    		f.setCodEmpresa(codEmpresa!=null?codEmpresa:"");
    		f.setAnioPres(anioPresentacion!=null?anioPresentacion:"");
    		f.setMesPres(mesPresentacion!=null?mesPresentacion:"");
    		f.setAnoIniVigencia(anoInicioVigencia!=null?anoInicioVigencia:"");
    		f.setAnoFinVigencia(anoFinVigencia!=null?anoFinVigencia:"");
    		f.setEtapa(etapa!=null?etapa:"");
    		f.setMensajeError(msgError!=null?msgError:"");
    		f.setMensajeInfo(msgInfo!=null?msgInfo:"");
    		f.setEtapa(etapa!=null?etapa:"");
    		f.setFlag(flag!=null?flag:"");
    		
    		//valores constantes para las empresas edelnor y luz del sur
    		f.setCodEdelnor(FiseConstants.COD_EMPRESA_EDELNOR);
    		f.setCodLuzSur(FiseConstants.COD_EMPRESA_LUZ_SUR);
    		
    		pRequest.getPortletSession().setAttribute("codEmpresa", "", PortletSession.APPLICATION_SCOPE);
    	    pRequest.getPortletSession().setAttribute("anoPresentacion", "", PortletSession.APPLICATION_SCOPE);
    	    pRequest.getPortletSession().setAttribute("mesPresentacion", "", PortletSession.APPLICATION_SCOPE);
    	    pRequest.getPortletSession().setAttribute("anoInicioVigencia", "", PortletSession.APPLICATION_SCOPE);
    	    pRequest.getPortletSession().setAttribute("anoFinVigencia", "", PortletSession.APPLICATION_SCOPE);
    	    pRequest.getPortletSession().setAttribute("etapa", "", PortletSession.APPLICATION_SCOPE);
    	    pRequest.getPortletSession().setAttribute("mensajeError", "", PortletSession.APPLICATION_SCOPE);
    	    pRequest.getPortletSession().setAttribute("listaError", null, PortletSession.APPLICATION_SCOPE);
    	    pRequest.getPortletSession().setAttribute("mensajeInformacion", "", PortletSession.APPLICATION_SCOPE);
    	    pRequest.getPortletSession().setAttribute("flag", "", PortletSession.APPLICATION_SCOPE);
        	
    	    if( listaError!=null && listaError.size()>0){
    			model.addAttribute("listaError", listaError);
    		}       	
        	
        	listaPeriodoEnvio = new ArrayList<FisePeriodoEnvio>();
        	
        	f.setListaMes(fiseUtil.getMapaMeses());
    		f.setAnioDesde(fiseUtil.obtenerNroAnioFechaAnterior());
    		
    		f.setMesDesde(fiseUtil.obtenerNroMesFechaAnterior());
    		
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
    		
    		model.addAttribute("model", f);
    		
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
			long fechaDesde=0;
  			long fechaHasta=0;
  			
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
			
			fechaDesde = anioDesde * 100 + mesDesde;
  			fechaHasta = anioHasta * 100 + mesHasta;  	
  			
			logger.info("codigo empresa "+ codEmpresa);
  			logger.info("anio desde "+ anioDesde);
  			logger.info("mes desde "+ mesDesde);
  			logger.info("anio hasta "+ anioHasta);
  			logger.info("mes hasta "+ mesHasta);
  			logger.info("fecha desde "+ fechaDesde);
  			logger.info("fecha hasta "+ fechaHasta); 			
  			logger.info("etapa "+ etapa);
  			logger.info("admin "+ f.isAdmin()); 			
 		   
 			List<FiseFormato14CC> listaFormato14C = formato14CGartService.buscarFiseFormato14CC(codEmpresa, 
 					fechaDesde, fechaHasta, etapa);	
 			List<FiseFormato14CC> listaFormatoExporExcel= new ArrayList<FiseFormato14CC>();
  			logger.info("tamaño de la lista formato 14c   :"+listaFormato14C.size());
  			for(FiseFormato14CC formato14c : listaFormato14C){ 				
  				formato14c.setDescEmpresa(mapaEmpresa.get(formato14c.getId().getCodEmpresa()));
  				formato14c.setDescMesPresentacion(fiseUtil.getMapaMeses().get(formato14c.getId().getMesPresentacion())); 			
  				
  				/**Obteniendo el flag de la operacion*/
  				String flagOper = commonService.obtenerEstadoProceso(formato14c.getId().getCodEmpresa(),
  						FiseConstants.TIPO_FORMATO_14C,formato14c.getId().getAnoPresentacion(),
  						formato14c.getId().getMesPresentacion(), formato14c.getId().getEtapa());
  				logger.info("flag operacion:  "+flagOper);
  				
  				jsonArray.put(new Formato14CJSON().asJSONObject(formato14c,flagOper));
  				
  				/*if(formato14c.getFechaEnvioDefinitivo()!=null){
  					formato14c.setDescEstado(FiseConstants.ESTADO_ENVIADO_F14C);  						
  				}else{
  					formato14c.setDescEstado(FiseConstants.ESTADO_POR_ENVIAR_F14C);  				
  				}   */				
  				listaFormatoExporExcel.add(formato14c);
  			}
  			
  			fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_FORMATO_14C, 
  					FiseConstants.NOMBRE_EXCEL_FORMATO14C,//title 
  					FiseConstants.NOMBRE_HOJA_FORMATO14C, //nombre hoja
  					listaFormatoExporExcel);
  			
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
			String anoIniVigencia = request.getParameter("anoIniVigencia");
			String anoFinVigencia = request.getParameter("anoFinVigencia");
			logger.info("anoIniVigencia "+anoIniVigencia);
			logger.info("anoFinVigencia "+anoFinVigencia);
			logger.info("etapa "+f.getEtapa());	
			
			String codigoPeriodoEnvio =String.valueOf(f.getAnioPres())+
        			FormatoUtil.rellenaIzquierda(String.valueOf(f.getMesPres()), '0', 2)+
        			f.getEtapa();
			logger.info("periodo compuesto "+codigoPeriodoEnvio);	
			
			f= formato14CGartService.buscarFormato14CEditar(f.getCodEmpresa(),
					f.getAnioPres(), f.getMesPres(), f.getAnoIniVigencia(), f.getAnoFinVigencia(), f.getEtapa());
			
			f.setPeriodoEnvio(codigoPeriodoEnvio);	
			
			String mesLetras = FechaUtil.mesLetras(f.getMesPres());			
			String desPeriodoEnvio = mesLetras+"-"+f.getAnioPres()+" / "+f.getEtapa();
			
			f.setDesperiodoEnvio(desPeriodoEnvio);
			/**Para verificar el flag costo del registro seleccionado*/
			List<FisePeriodoEnvio> listaPeriodo =null;
			if(listaPeriodoEnvio.size()==0){
			    listaPeriodo = periodoService.listarFisePeriodoEnvioMesAnioEtapa(f.getCodEmpresa(), 
					FiseConstants.TIPO_FORMATO_14C);
			    listaPeriodoEnvio =listaPeriodo;
			}		
			logger.info("Periodo envio al editar un registro:  "+codigoPeriodoEnvio); 
			logger.info("tamaño de la lista periodo al editar o visualizar :  "+listaPeriodoEnvio.size()); 				
			for (FisePeriodoEnvio p : listaPeriodoEnvio) {
				logger.info("periodo codigo:  "+p.getCodigoItem()); 
				if(f.getPeriodoEnvio().equals(p.getCodigoItem()) ){
				    f.setFlagCosto(p.getFlagHabilitaCostos());				
					break;
				}
			}			
			logger.info("Flag costo al editar un registro:  "+f.getFlagCosto()); 
			/********Guardando en sesion la clave primaria del formato para la carga de exel******/
			PortletRequest pRequest = (PortletRequest) request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
			pRequest.getPortletSession().setAttribute("codEmpresaEdit", f.getCodEmpresa(), PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("anoPresentacionEdit", f.getAnioPres(), PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("mesPresentacionEdit", f.getMesPres(), PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("anoIniVigenciaEdit",f.getAnoIniVigencia(), PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("anoFinVigenciaEdit", f.getAnoFinVigencia(), PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("etapaEdit", f.getEtapa(), PortletSession.APPLICATION_SCOPE);
						
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
			logger.info("anio inicio vigencia:  "+ f.getAnoIniVigencia());
			logger.info("anio fin vigencia:  "+ f.getAnoFinVigencia());	
			logger.info("nombre de sede:  "+ f.getNombreSede());	
			logger.info("cantidad total rural:  "+ f.getNumRural());
			logger.info("flag costo:  "+ f.getFlagCosto());

			if( f.getPeriodoEnvio().length()>6 ){
				f.setAnioPres(f.getPeriodoEnvio().substring(0, 4));
				f.setMesPres(f.getPeriodoEnvio().substring(4, 6));
				f.setEtapa(f.getPeriodoEnvio().substring(6, f.getPeriodoEnvio().length()));
				
				if(FormatoUtil.isBlank(f.getAnoIniVigencia()) && FormatoUtil.isBlank(f.getAnoFinVigencia())){
					logger.info("Tamanio de la lista periodo al grabar nuevo: "+listaPeriodoEnvio.size()); 
					for (FisePeriodoEnvio p : listaPeriodoEnvio) {
						if(f.getPeriodoEnvio().equals(p.getCodigoItem()) ){					
							f.setAnoIniVigencia(p.getAnioInicioVig());
							f.setAnoIniVigencia(p.getAnioFinVig());
							break;
						}
					}
				}			
				/*List<FisePeriodoEnvio> listaPeriodo = periodoService.listarFisePeriodoEnvioMesAnioEtapa(f.getCodEmpresa(), 
	  					FiseConstants.TIPO_FORMATO_14C);*/
				logger.info("tamaño de la lista periodo al grabar nuevo:  "+listaPeriodoEnvio.size()); 				
				for (FisePeriodoEnvio p : listaPeriodoEnvio) {
					logger.info("periodo codigo:  "+p.getCodigoItem()); 
					if(f.getPeriodoEnvio().equals(p.getCodigoItem()) ){
					    f.setFlagCosto(p.getFlagHabilitaCostos());				
						break;
					}
				}
			}
			logger.info("flag costo al momento de grabar:  "+f.getFlagCosto()); 
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
			logger.info("anio inicio vigencia:  "+ f.getAnoIniVigencia());
			logger.info("anio fin vigencia:  "+ f.getAnoFinVigencia());	
			logger.info("nombre de sede:  "+ f.getNombreSede());	
			logger.info("cantidad total rural:  "+ f.getNumRural());
			logger.info("flag costo:  "+ f.getFlagCosto());			

			if( f.getPeriodoEnvio().length()>6 ){
				f.setAnioPres(f.getPeriodoEnvio().substring(0, 4));
				f.setMesPres(f.getPeriodoEnvio().substring(4, 6));
				f.setEtapa(f.getPeriodoEnvio().substring(6, f.getPeriodoEnvio().length()));				
				
				if(FormatoUtil.isBlank(f.getAnoIniVigencia()) && FormatoUtil.isBlank(f.getAnoFinVigencia())){
					logger.info("Tamanio de la lista periodo al actualizar : "+listaPeriodoEnvio.size()); 
					for (FisePeriodoEnvio p : listaPeriodoEnvio) {
						if(f.getPeriodoEnvio().equals(p.getCodigoItem()) ){					
							f.setAnoIniVigencia(p.getAnioInicioVig());
							f.setAnoIniVigencia(p.getAnioFinVig());
							break;
						}
					}
				}			
				/*List<FisePeriodoEnvio> listaPeriodo = periodoService.listarFisePeriodoEnvioMesAnioEtapa(f.getCodEmpresa(), 
	  					FiseConstants.TIPO_FORMATO_14C);
				logger.info("tamaño de la lista periodo:  "+listaPeriodo.size()); */
				logger.info("Periodo al momento de actualizar un registro:  "+f.getPeriodoEnvio()); 
				logger.info("tamaño de la lista periodo al actualizar :  "+listaPeriodoEnvio.size()); 				
				for (FisePeriodoEnvio p : listaPeriodoEnvio) {
					logger.info("periodo codigo:  "+p.getCodigoItem()); 
					if(f.getPeriodoEnvio().equals(p.getCodigoItem()) ){
					    f.setFlagCosto(p.getFlagHabilitaCostos());				
						break;
					}
				}
			}
			logger.info("flag costo al momento de actualizar:  "+f.getFlagCosto()); 
			
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
			logger.info("anio inicio vigencia:  "+ f.getAnoIniVigencia());
			logger.info("anio fin vigencia:  "+ f.getAnoFinVigencia());	
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
  			listaPeriodoEnvio = periodoService.listarFisePeriodoEnvioMesAnioEtapa(codEmpresa, 
  					FiseConstants.TIPO_FORMATO_14C);  		
  			logger.info("Tamaño de lista de periodo de envio:  "+listaPeriodoEnvio.size()); 
  			JSONArray jsonArray = new JSONArray();
  			for (FisePeriodoEnvio periodo : listaPeriodoEnvio) {
  				JSONObject jsonObj = new JSONObject();
				jsonObj.put("codigoItem", periodo.getCodigoItem());				
				jsonObj.put("descripcionItem", periodo.getDescripcionItem());			
				jsonObj.put("flagPeriodoEjecucion", periodo.getFlagPeriodoEjecucion());	
				jsonObj.put("flagCosto", periodo.getFlagHabilitaCostos());
				logger.info("Flag habilita costos al momento de cargar el periodo mes anio y etapa: "
				+periodo.getFlagHabilitaCostos());
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
	
	@ResourceMapping("cargaFlagPeriodoF14C")
  	public void cargaFlagPeriodo(ResourceRequest request,ResourceResponse response,
  			@ModelAttribute("formato14CBean")Formato14CBean f){
		try {			
  			response.setContentType("applicacion/json");
  			String periodoEnvio = f.getPeriodoEnvio();
  			logger.info("Flag periodo formato controller: "+f.getPeriodoEnvio()); 
  			JSONObject jsonObj = new JSONObject();
  			for (FisePeriodoEnvio p : listaPeriodoEnvio) {
				if(periodoEnvio.equals(p.getCodigoItem()) ){
					jsonObj.put("flagPeriodoEjecucion", p.getFlagPeriodoEjecucion());
					jsonObj.put("flagCosto", p.getFlagHabilitaCostos());
					//agreamos los campos de ano inicio y fin de vigencia
					jsonObj.put("anoIniVigencia", p.getAnioInicioVig());
					jsonObj.put("anoFinVigencia", p.getAnioFinVig());
					break;
				}
			}
  			System.out.println(jsonObj.toString());
  			PrintWriter pw = response.getWriter();
  		    pw.write(jsonObj.toString());
  		    pw.flush();
  		    pw.close();							
  		}catch (Exception e) {  		
  			e.printStackTrace();
  		}
	}
	
	/***Carga de archivos***/
	
	@ActionMapping(params="action=cargar")
	public void cargarDocumento(ActionRequest request,ActionResponse response,
			@ModelAttribute("formato14CBean")Formato14CBean f){
		
		logger.info("--- cargar documento");
				
		Formato14AMensajeBean formatoMensaje = new Formato14AMensajeBean();
		
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(request);
		
		String flagCarga = uploadPortletRequest.getParameter("flagCarga");
    	String codEmpresaNew = uploadPortletRequest.getParameter("codEmpresa");
    	String periodoEnvioPresNew = uploadPortletRequest.getParameter("periodoEnvio");    	
    	String flagCosto = uploadPortletRequest.getParameter("flagCosto");
    	logger.info("Flag de carga:  "+flagCarga);
    	logger.info("Flag costo:  "+flagCosto);
    	
    	String anioPresNew = "";
		String mesPresNew = "";		
		String etapaNew = ""; 
		String anioIniVigNew ="";
		String anioFinVigNew="";  
		
		anioIniVigNew =uploadPortletRequest.getParameter("anoIniVigencia");    	
		anioFinVigNew =uploadPortletRequest.getParameter("anoFinVigencia");	
		
		
		if(FormatoUtil.isBlank(anioIniVigNew) && FormatoUtil.isBlank(anioFinVigNew)){
			logger.info("Tamanio de la lista periodo al cargar archivo: "+listaPeriodoEnvio.size()); 
			for (FisePeriodoEnvio p : listaPeriodoEnvio) {
				if(periodoEnvioPresNew.equals(p.getCodigoItem()) ){					
					anioIniVigNew= p.getAnioInicioVig();
					anioFinVigNew =  p.getAnioFinVig();
					break;
				}
			}
		}
		logger.info("Anio inicio Vigencia para nuevo:  "+anioIniVigNew);
		logger.info("Anio fin Vigencia para nuevo:  "+anioFinVigNew);
		
		if(periodoEnvioPresNew!=null && periodoEnvioPresNew.length()>6){
    		anioPresNew = periodoEnvioPresNew.substring(0, 4);
    		mesPresNew = periodoEnvioPresNew.substring(4, 6);
    		etapaNew = periodoEnvioPresNew.substring(6, periodoEnvioPresNew.length());    			
    	}    	
		
		PortletRequest pRequest = (PortletRequest) request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
		/*****Recuperando las varibales en sesion la momento de editar un regsitro********/
		String codEmpresaEdit = (String)pRequest.getPortletSession().getAttribute("codEmpresaEdit", PortletSession.APPLICATION_SCOPE);
		String anioPresEdit = (String)pRequest.getPortletSession().getAttribute("anoPresentacionEdit", PortletSession.APPLICATION_SCOPE);
		String mesPresEdit = (String)pRequest.getPortletSession().getAttribute("mesPresentacionEdit", PortletSession.APPLICATION_SCOPE);
		String anioIniVigEdit = (String)pRequest.getPortletSession().getAttribute("anoIniVigenciaEdit", PortletSession.APPLICATION_SCOPE);
		String anioFinVigEdit = (String)pRequest.getPortletSession().getAttribute("anoFinVigenciaEdit", PortletSession.APPLICATION_SCOPE);
		String etapaEdit = (String)pRequest.getPortletSession().getAttribute("etapaEdit", PortletSession.APPLICATION_SCOPE);
		
		FileEntry fileEntry=null;
		try{
		if( flagCarga.equals(FiseConstants.FLAG_CARGAEXCEL_FORMULARIONUEVO) ){
			logger.info("Empesando a subir exel registro nuevo."); 
			fileEntry=fiseUtil.subirDocumento(request, uploadPortletRequest, FiseConstants.TIPOARCHIVO_XLS);
			logger.info("El archivo exel registro nuevo fue subido correctamente."); 
			formatoMensaje = readExcelFile(fileEntry, themeDisplay.getUser(), flagCarga, 
					codEmpresaNew, anioPresNew, mesPresNew, anioIniVigNew, anioFinVigNew, etapaNew,flagCosto);
			logger.info("Valor de retorno al registrar los datos leidos: "+formatoMensaje.getValor()); 
		}else if( flagCarga.equals(FiseConstants.FLAG_CARGAEXCEL_FORMULARIOMODIFICACION) ){
			fileEntry=fiseUtil.subirDocumento(request, uploadPortletRequest, FiseConstants.TIPOARCHIVO_XLS);
			formatoMensaje = readExcelFile(fileEntry, themeDisplay.getUser(), flagCarga, 
					codEmpresaEdit, anioPresEdit, mesPresEdit, anioIniVigEdit, anioFinVigEdit, etapaEdit,flagCosto);
		}else if( flagCarga.equals(FiseConstants.FLAG_CARGATXT_FORMULARIONUEVO) ){
			fileEntry =fiseUtil.subirDocumento(request, uploadPortletRequest, FiseConstants.TIPOARCHIVO_TXT);
			formatoMensaje = readTxtFile(fileEntry, uploadPortletRequest, themeDisplay.getUser(), 
				flagCarga, codEmpresaNew, anioPresNew, mesPresNew, anioIniVigNew, anioFinVigNew, etapaNew,flagCosto);
		}else if( flagCarga.equals(FiseConstants.FLAG_CARGATXT_FORMULARIOMODIFICACION) ){
			fileEntry=fiseUtil.subirDocumento(request, uploadPortletRequest, FiseConstants.TIPOARCHIVO_TXT);
			formatoMensaje = readTxtFile(fileEntry, uploadPortletRequest, themeDisplay.getUser(), 
					flagCarga, codEmpresaEdit, anioPresEdit, mesPresEdit, anioIniVigEdit, anioFinVigEdit, etapaEdit,flagCosto);
		}
	}catch(FileMimeTypeException ex){
		
	}catch (Exception e) {
		// TODO: handle exception
	}
		
		if(("1").equals(formatoMensaje.getValor())){
			String codEmpresa = formatoMensaje.getFormato14CBean().getCodEmpresa();
			String anoPresentacion = String.valueOf(formatoMensaje.getFormato14CBean().getAnioPres());
			String mesPresentacion = String.valueOf(formatoMensaje.getFormato14CBean().getMesPres());
			String anoIniVigencia = String.valueOf(formatoMensaje.getFormato14CBean().getAnoIniVigencia());
			String anoFinVigencia = String.valueOf(formatoMensaje.getFormato14CBean().getAnoFinVigencia());
			String etapa = String.valueOf(formatoMensaje.getFormato14CBean().getEtapa());
			
			pRequest.getPortletSession().setAttribute("codEmpresa", codEmpresa, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("anoPresentacion", anoPresentacion, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("mesPresentacion", mesPresentacion, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("anoInicioVigencia", anoIniVigencia, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("anoFinVigencia", anoFinVigencia, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("etapa", etapa, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("flag", "", PortletSession.APPLICATION_SCOPE);
		}else{
			if( flagCarga.equals(FiseConstants.FLAG_CARGAEXCEL_FORMULARIONUEVO) || flagCarga.equals(FiseConstants.FLAG_CARGATXT_FORMULARIONUEVO) ){
				pRequest.getPortletSession().setAttribute("codEmpresa", codEmpresaNew, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("anoPresentacion", anioPresNew, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("mesPresentacion", mesPresNew, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("anoInicioVigencia", anioIniVigNew, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("anoFinVigencia", anioFinVigNew, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("etapa", etapaNew, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("flag", "N", PortletSession.APPLICATION_SCOPE);//para control de mostrar formulario a ingresar
			}else if( flagCarga.equals(FiseConstants.FLAG_CARGAEXCEL_FORMULARIOMODIFICACION) || flagCarga.equals(FiseConstants.FLAG_CARGATXT_FORMULARIOMODIFICACION) ){
				pRequest.getPortletSession().setAttribute("codEmpresa", codEmpresaEdit, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("anoPresentacion", anioPresEdit, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("mesPresentacion", mesPresEdit, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("anoInicioVigencia", anioIniVigEdit, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("anoFinVigencia", anioFinVigEdit, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("etapa", etapaEdit, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("flag", "", PortletSession.APPLICATION_SCOPE);
			}
			
		}
		if(formatoMensaje.getListaMensajeError()!=null && formatoMensaje.getListaMensajeError().size()>0){
			pRequest.getPortletSession().setAttribute("listaError", formatoMensaje.getListaMensajeError(), PortletSession.APPLICATION_SCOPE);
			pRequest.getPortletSession().setAttribute("mensajeError", formatoMensaje.getMensajeError(), PortletSession.APPLICATION_SCOPE);
		}else{
			pRequest.getPortletSession().setAttribute("mensajeInformacion", "Datos guardados satisfactoriamente.", PortletSession.APPLICATION_SCOPE);
		}
		/***limpiamos las variables en sesion utilizados para la edicion de archivos de carga**/
		pRequest.getPortletSession().setAttribute("codEmpresaEdit", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("anoPresentacionEdit", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("mesPresentacionEdit", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("anoEjecucionEdit", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("mesEjecucionEdit", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("etapaEdit", "", PortletSession.APPLICATION_SCOPE);
		
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
		    
		  // String flagPeriodoEjecucion = f.getFlagPeriodoEjecucion();
		    
		    session.setAttribute("nombreReporte",nombreReporte);
		    session.setAttribute("nombreArchivo",nombreArchivo);
		    session.setAttribute("tipoFormato",tipoFormato);
		    session.setAttribute("tipoArchivo",tipoArchivo);
		    
			if(f.getPeriodoEnvio().length()>6){
				f.setAnioPres(f.getPeriodoEnvio().substring(0, 4));
				f.setMesPres(f.getPeriodoEnvio().substring(4, 6));
				f.setEtapa(f.getPeriodoEnvio().substring(6, f.getPeriodoEnvio().length()));
               
				if(FormatoUtil.isBlank(f.getAnoIniVigencia()) && FormatoUtil.isBlank(f.getAnoFinVigencia())){
					logger.info("Tamanio de la lista periodo al reporte: "+listaPeriodoEnvio.size()); 
					for (FisePeriodoEnvio p : listaPeriodoEnvio) {
						if(f.getPeriodoEnvio().equals(p.getCodigoItem()) ){					
							f.setAnoIniVigencia(p.getAnioInicioVig());
							f.setAnoIniVigencia(p.getAnioFinVig());
							break;
						}
					}
				}		
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
    
    PortletRequest pRequest = (PortletRequest) request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
    
	ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
	try {	
		JSONArray jsonArray = new JSONArray();			
		logger.info("Entrando a validadar un registro en el fomato 14C"); 			
		logger.info("Codigo empresa:  "+ f.getCodEmpresa()); 
		logger.info("perido de envio:  "+ f.getPeriodoEnvio());		

		if(f.getPeriodoEnvio().length()>6 ){
			f.setAnioPres(f.getPeriodoEnvio().substring(0, 4));
			f.setMesPres(f.getPeriodoEnvio().substring(4, 6));
			f.setEtapa(f.getPeriodoEnvio().substring(6, f.getPeriodoEnvio().length()));

			if(FormatoUtil.isBlank(f.getAnoIniVigencia()) && FormatoUtil.isBlank(f.getAnoFinVigencia())){
				logger.info("Tamanio de la lista periodo al validar: "+listaPeriodoEnvio.size()); 
				for (FisePeriodoEnvio p : listaPeriodoEnvio) {
					if(f.getPeriodoEnvio().equals(p.getCodigoItem())){					
						f.setAnoIniVigencia(p.getAnioInicioVig());
						f.setAnoIniVigencia(p.getAnioFinVig());
						break;
					}
				}
			}		
		}	
		logger.info("Anio inicio figencia al validar: "+f.getAnoIniVigencia());
		logger.info("Anio fin figencia al validar: "+f.getAnoFinVigencia()); 
		
		FiseFormato14CC formato = formato14CGartService.obtenerFiseFormato14CC(f);
	    logger.info("Objeto formato 14C: "+formato); 
	    if( formato!=null ){	    
	    	Formato14Generic formato14Generic = new Formato14Generic(formato);
	    	int i = commonService.validarFormatos_14(formato14Generic, FiseConstants.NOMBRE_FORMATO_14C,
	    			themeDisplay.getUser().getLogin(), themeDisplay.getUser().getLoginIP());
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
	    pRequest.getPortletSession().setAttribute("periodoEnvioReporteValidacion", f.getPeriodoEnvio(), PortletSession.APPLICATION_SCOPE);
	    response.setContentType("application/json");
	    PrintWriter pw = response.getWriter();		  
	    logger.info(jsonArray.toString());
	    pw.write(jsonArray.toString());
	    pw.flush();
	    pw.close();
	    
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
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
	public void reporteValidacion(ResourceRequest request,ResourceResponse response,@ModelAttribute("formato14CBean")Formato14CBean f) {
		try {
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();
	        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
	        
	        logger.info("Codigo empresa:  "+ f.getCodEmpresa());	        
			logger.info("perido de envio:  "+ f.getPeriodoEnvio());
	        
	        PortletRequest pRequest = (PortletRequest) request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
	        
		    JSONArray jsonArray = new JSONArray();	
		    
		    String nombreReporte = request.getParameter("nombreReporte").trim();
		    String nombreArchivo = request.getParameter("nombreArchivo").trim();
		    String tipoFormato = FiseConstants.TIPO_FORMATO_VAL;
		    String tipoArchivo = request.getParameter("tipoArchivo").trim();	    
		    
		    String anioInicioVigencia = f.getAnioInicioVigenciaHidden();
			String anioFinVigencia = f.getAnioFinVigenciaHidden();
		   
		    session.setAttribute("nombreReporte",nombreReporte);
		    session.setAttribute("nombreArchivo",nombreArchivo);
		    session.setAttribute("tipoFormato",tipoFormato);
		    session.setAttribute("tipoArchivo",tipoArchivo);		    
	
		    if( listaObservaciones!=null ){
		    	session.setAttribute("lista", listaObservaciones);
		    }
	        
		    //add
		    String periodoEnvio = (String)pRequest.getPortletSession().getAttribute("periodoEnvioReporteValidacion", 
		    		PortletSession.APPLICATION_SCOPE);		 	   
		    logger.info("Periodo envio en el reporte de validacion:  "+periodoEnvio); 
		    String anoPresentacion = "";
		    String mesPresentacion = "";
		    if(periodoEnvio.length()>6 ){
		    	anoPresentacion = periodoEnvio.substring(0, 4);
		    	mesPresentacion = periodoEnvio.substring(4, 6);
		    	f.setEtapa(periodoEnvio.substring(6, periodoEnvio.length()));
		    }
		    CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14C);
	    	String descripcionFormato = "";
	    	if( tabla!=null ){
	    		descripcionFormato = tabla.getDescripcionTabla();
	    	}
		    Map<String, Object> mapa = new HashMap<String, Object>();
	    	mapa.put(FiseConstants.PARAM_IMG_LOGOTIPO, session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg"));
		   	mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
		   	mapa.put(FiseConstants.PARAM_ANO_PRESENTACION, Long.parseLong(anoPresentacion));
		   	mapa.put(FiseConstants.PARAM_DESC_MES_PRESENTACION, fiseUtil.getMapaMeses().get(Long.parseLong(mesPresentacion)));
		   	mapa.put(FiseConstants.PARAM_USUARIO, themeDisplay.getUser().getLogin());
		   	mapa.put(FiseConstants.PARAM_NOMBRE_FORMATO, descripcionFormato);
		   	mapa.put(FiseConstants.PARAM_NRO_OBSERVACIONES, (listaObservaciones!=null && !listaObservaciones.isEmpty())?listaObservaciones.size():0);
		   	mapa.put(FiseConstants.PARAM_DESC_EMPRESA, mapaEmpresa.get(f.getCodEmpresa().length()==3?f.getCodEmpresa()+" ": f.getCodEmpresa()));
		   	mapa.put(FiseConstants.PARAM_ETAPA, f.getEtapa());  	 	
		   	
		   	mapa.put(FiseConstants.PARAM_ANO_INICIO_VIGENCIA, (anioInicioVigencia!=null && !anioInicioVigencia.equals("")) ?Long.parseLong(anioInicioVigencia):0);
			mapa.put(FiseConstants.PARAM_ANO_FIN_VIGENCIA, (anioFinVigencia!=null && !anioFinVigencia.equals(""))?Long.parseLong(anioFinVigencia):0);
		   	
		   	session.setAttribute("mapa", mapa);		   
		   	
		    response.setContentType("application/json");
		    PrintWriter pw = response.getWriter();
		    pw.write(jsonArray.toString());
		    pw.flush();
		    pw.close();
		    pRequest.getPortletSession().setAttribute("periodoEnvioReporteValidacion","", PortletSession.APPLICATION_SCOPE);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@ResourceMapping("envioDefinitivoF14C")
	public void envioDefinitivo(ResourceRequest request,ResourceResponse response,
			@ModelAttribute("formato14CBean")Formato14CBean f) {
		 FiseFormato14CC formato = null;
		 Formato14CReportBean bean = new Formato14CReportBean();
		 Map<String, Object> mapa = null;
		try {
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();
	        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		  
	        JSONObject jsonObj = new JSONObject(); 
		  
		    List<FileEntryJSP> listaArchivo = new ArrayList<FileEntryJSP>(); 	    
		    
		   
		    String directorio = null;
		    //cambios
		    String respuestaEmail = "";
		    boolean valorFormato = false;
		    boolean valorActa = false;
		    
		    String nombreReporte = request.getParameter("nombreReporte").trim();
		    String nombreArchivo = request.getParameter("nombreArchivo").trim();
		    logger.info("Nombre del archivo: "+nombreArchivo); 

		    if( f.getPeriodoEnvio().length()>6 ){
				f.setAnioPres(f.getPeriodoEnvio().substring(0, 4));
				f.setMesPres(f.getPeriodoEnvio().substring(4, 6));
				f.setEtapa(f.getPeriodoEnvio().substring(6, f.getPeriodoEnvio().length()));
				
				if(FormatoUtil.isBlank(f.getAnoIniVigencia()) && FormatoUtil.isBlank(f.getAnoFinVigencia())){
					logger.info("Tamanio de la lista periodo al envio definitivo: "+listaPeriodoEnvio.size()); 
					for (FisePeriodoEnvio p : listaPeriodoEnvio) {
						if(f.getPeriodoEnvio().equals(p.getCodigoItem()) ){					
							f.setAnoIniVigencia(p.getAnioInicioVig());
							f.setAnoIniVigencia(p.getAnioFinVig());
							break;
						}
					}
				}	
			}	
		    f.setUsuario(themeDisplay.getUser().getLogin());
			f.setTerminal(themeDisplay.getUser().getLoginIP());	
			
		    formato = formato14CGartService.obtenerFiseFormato14CC(f);
		    logger.info("Objeto formato 14C: "+formato);
		    
	        if(formato!=null ){       
			    bean = formato14CGartService.estructurarFormato14CBeanByFiseFormato14C(formato);
	        	bean.setDescEmpresa(mapaEmpresa.get(formato.getId().getCodEmpresa()));
	        	bean.setDescMesPresentacion(fiseUtil.getMapaMeses().get(formato.getId().getMesPresentacion()));
	        	mapa = formato14CGartService.mapearParametrosFormato14C(bean);
	        	
	        	CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14C);
	        	String descripcionFormato = "";
	        	if( tabla!=null ){
	        		descripcionFormato = tabla.getDescripcionTabla();
	        	}         	
	        	if(mapa!=null){	        	
	        		mapa.put("IMG", session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg"));
	        		mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
	        		//verificar si ponerlo aca o no
	        		mapa.put("USUARIO", themeDisplay.getUser().getLogin());
	        		mapa.put("NOMBRE_FORMATO", descripcionFormato);
	        		mapa.put("FECHA_ENVIO", FechaUtil.obtenerFechaActual());
	        		mapa.put("CORREO", themeDisplay.getUser().getEmailAddress());
	        		mapa.put("NRO_OBSERVACIONES", (listaObservaciones!=null && !listaObservaciones.isEmpty())?listaObservaciones.size():0);
	        		mapa.put("MSG_OBSERVACIONES", (listaObservaciones!=null && !listaObservaciones.isEmpty())?FiseConstants.MSG_OBSERVACION_REPORTE_LLENO:FiseConstants.MSG_OBSERVACION_REPORTE_VACIO);
	        		 //add para acta envio
	     		    mapa.put("ANO_INICIO_VIGENCIA", formato.getId().getAnoInicioVigencia());
	     		    mapa.put("ANO_FIN_VIGENCIA", formato.getId().getAnoFinVigencia());
	     		    mapa.put("FECHA_REGISTRO", formato.getFechaCreacion());
	     		    mapa.put("USUARIO_REGISTRO", formato.getUsuarioCreacion());
	     		   //prueba de envio definitivo
	     		   String dirCheckedImage = session.getServletContext().getRealPath("/reports/checked.jpg");
	     		   String dirUncheckedImage = session.getServletContext().getRealPath("/reports/unchecked.jpg");
	     		   mapa.put("CHECKED", dirCheckedImage);
	     		   mapa.put("UNCHECKED", dirUncheckedImage);
	     		   boolean cumplePlazo = false;
	     		   cumplePlazo = commonService.fechaEnvioCumplePlazo(
	     				   FiseConstants.TIPO_FORMATO_14C, 
	     				   formato.getId().getCodEmpresa(), 
	     				   formato.getId().getAnoPresentacion(), 
	     				   formato.getId().getMesPresentacion(), 
	     				   formato.getId().getEtapa(), 
	     				   FechaUtil.fecha_DD_MM_YYYY(FechaUtil.obtenerFechaActual()));
	     		   if( cumplePlazo ){
	     			   mapa.put("CHECKED_CUMPLEPLAZO", dirCheckedImage);
	     	   	   }else{
	     			   mapa.put("CHECKED_CUMPLEPLAZO", dirUncheckedImage);
	     	   	   }
	     		  if( listaObservaciones!=null && !listaObservaciones.isEmpty() ){
						mapa.put(FiseConstants.PARAM_CHECKED_OBSERVACION, dirCheckedImage);
					}else{
						mapa.put(FiseConstants.PARAM_CHECKED_OBSERVACION, dirUncheckedImage);
					}
	     		   mapa.put("ETAPA", formato.getId().getEtapa());
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
		    	   String nombre = FormatoUtil.nombreIndividualFormato(formato.getId().getCodEmpresa(), formato.getId().getAnoPresentacion(), formato.getId().getMesPresentacion(), FiseConstants.TIPO_FORMATO_14C);
		    	   logger.info("subiendo archivo al repositorio del formato 14c"); 
		    	   FileEntry archivo = fiseUtil.subirDocumentoBytes(request, bytes, "application/pdf", nombre);
		    	   logger.info("Archivo subido correctamente  formato 14c envio."+archivo); 
		    	   if(archivo!=null ){
		    		   FileEntryJSP fileEntryJsp = new FileEntryJSP();
		    		   fileEntryJsp.setNombreArchivo(nombre);
		    		   fileEntryJsp.setFileEntry(archivo);
		    		   listaArchivo.add(fileEntryJsp);
		    		   valorFormato = true;
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
			    	   String nombre = FormatoUtil.nombreIndividualAnexoObs(formato.getId().getCodEmpresa(), formato.getId().getAnoPresentacion(), formato.getId().getMesPresentacion(), FiseConstants.TIPO_FORMATO_14C);
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
		    	   session.setAttribute("bytesActaEnvio", bytes3);
		    	   String nombre = FormatoUtil.nombreIndividualActaRemision(formato.getId().getCodEmpresa(), formato.getId().getAnoPresentacion(), formato.getId().getMesPresentacion(), FiseConstants.TIPO_FORMATO_14C);
		    	   logger.info("subiendo archivo al repositorio del acta de envio defi.");
		    	   FileEntry archivo3 = fiseUtil.subirDocumentoBytes(request, bytes3, "application/pdf", nombre);
		    	   logger.info("Archivo subido correctamente  del acta de envio."+archivo3); 
		    	   if( archivo3!=null ){
		    		   FileEntryJSP fileEntryJsp = new FileEntryJSP();
		    		   fileEntryJsp.setNombreArchivo(nombre);
		    		   fileEntryJsp.setFileEntry(archivo3);
		    		   listaArchivo.add(fileEntryJsp);
		    		   valorActa = true;
		    	   }
		       }
		       /**actualizamos  la fecha de envio*/
		       String valorActuaizar = "0";
		       if(valorFormato && valorActa){
		    	   valorActuaizar = formato14CGartService.actualizarDatosEnvioFormato14C(f);   
		       }
	    	   if(valorActuaizar.equals("1")){
	    		   if(listaArchivo!=null && listaArchivo.size()>0){ 	 	    	  
			    	   logger.info("Entrando a enviar email envio defi."); 
			    	   respuestaEmail = fiseUtil.enviarMailsAdjunto(
			    			   request,
			    			   listaArchivo, 
			    			   mapaEmpresa.get(formato.getId().getCodEmpresa()),
			    			   formato.getId().getAnoPresentacion(),
			    			   formato.getId().getMesPresentacion(),
			    			   FiseConstants.TIPO_FORMATO_14C,
			    			   descripcionFormato,
			    			   FiseConstants.FRECUENCIA_BIENAL_DESCRIPCION,
			    			   formato.getId().getAnoInicioVigencia(),
			    			   formato.getId().getAnoFinVigencia());
			    	   logger.info("El envio de email fue correctamente envio defi.");			    	   
			       }
	    		   String[] msnId = respuestaEmail.split("/");
	    		   if(FiseConstants.PROCESO_ENVIO_EMAIL_OK.equals(msnId[0])){
	    			   jsonObj.put("resultado", "OK");	
	    			   jsonObj.put("correo",msnId[1]);		
	    		   }else{
	    			   jsonObj.put("resultado", "EMAIL");//error al enviar al email	
	    			   jsonObj.put("correo",msnId[1]);		
	    		   }	 		
	    	   }else{
	    		   jsonObj.put("resultado", "ERROR");//ocurrio un error al actualizar fecha envio o al formar
	    		   //los reportes del formato o del acta de envio
	    	   }		       
	        }else{
	        	 jsonObj.put("resultado", "ERROR"); //formato null  	
	        }
	       response.setContentType("application/json");
	       logger.info("arreglo json:"+jsonObj);
	       PrintWriter pw = response.getWriter();
	       pw.write(jsonObj.toString());
	       pw.flush();
	       pw.close();
		}catch (Exception e) {
			logger.info("Error al realizar el envio del formato 14c : "+e); 
			e.printStackTrace();
		}finally{
			if(formato != null){
				formato = null;
			}
			if(bean != null){
				bean = null;
			}
			if(mapa != null){
				mapa = null;
			}
		}
	 }
	
	@ResourceMapping("reporteEnvioDefinitivoF14C")
	public void reporteEnvioDefinitivo(ResourceRequest request,ResourceResponse response) {
		try {
			logger.info("Entrando al reporte de envio definitivo"); 
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();
	        
		    JSONArray jsonArray = new JSONArray();	
		    
		    String tipoFormato = FiseConstants.TIPO_FORMATO_ACTAENVIO;
		    String tipoArchivo = FiseConstants.FORMATO_EXPORT_ACTAENVIO;
		   
		    session.setAttribute("tipoFormato",tipoFormato);
		    session.setAttribute("tipoArchivo",tipoArchivo);
	        
		    response.setContentType("application/json");
		    PrintWriter pw = response.getWriter();
		    pw.write(jsonArray.toString());
		    pw.flush();
		    pw.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	
	/****Metodo para leer archivo de exel*****/	
    private Formato14AMensajeBean readExcelFile(FileEntry archivo, User user,
		String flagCarga, String codEmpresa,String anioPres, 
		String mesPres, String anioIniVigencia, 
		String anioFinVigencia, String etapa,String flagCosto) {	
		//---------------------
		//FLAG CARGA:
		//	1: para registros nuevos
		//	2: para registros modificados
		//---------------------
		Formato14AMensajeBean formatoMensaje = new Formato14AMensajeBean();
		
		InputStream is=null;	
		String sMsg = "";
		String valor="";
		List<MensajeErrorBean> listaError = new ArrayList<MensajeErrorBean>();
		int cont = 0;
		Formato14CBean bean = new Formato14CBean();		
		
		boolean process = false;
		
		try {
			if (archivo != null) {
				HSSFWorkbook libro = null;
				try {
					is=archivo.getContentStream();
					libro = new HSSFWorkbook(is);//Se lee libro xls
				} catch (Exception e1) {
					logger.info("Error al leer el archivo"); 					
					cont++;
					sMsg="ERROR";
					mapaErrores.get(FiseConstants.COD_ERROR_F12_20);//error al leer el archivo exel
					throw new Exception(mapaErrores.get(FiseConstants.COD_ERROR_F12_20));
				}
				int nroHojaSelec=0;
				
				if (libro != null) {
					//el excel puede tener varias hojas, se tiene qie leer el total de hojas y encontrar la que necesitemos
					logger.info("nro de hojas:"+ libro.getNumberOfSheets());
					for (int sheetNro = 0; sheetNro < libro.getNumberOfSheets(); sheetNro++){
						logger.info("nombre de hoja "+sheetNro+":"+ libro.getSheetName(sheetNro));
						if( FiseConstants.NOMBRE_HOJA_FORMATO14C.equals(libro.getSheetName(sheetNro)) ){
							process = true;
							nroHojaSelec = sheetNro;
							break;
						}
					}					
					logger.info("nro de hoja seleccionada "+nroHojaSelec);
					
					if(process){

						HSSFSheet hojaF14 = libro.getSheetAt(nroHojaSelec);				
						
						HSSFRow filaCodEmprea = hojaF14.getRow(FiseConstants.NRO_FILA_CODEMPRESA_F14C);	
						logger.info("Fila Codigo empresa:  "+filaCodEmprea.getRowNum());
						
						HSSFRow filaAnioMes = hojaF14.getRow(FiseConstants.NRO_FILA_ANIO_MES_F14C);									
						HSSFRow filaSede = hojaF14.getRow(FiseConstants.NRO_FILA_SEDE_F14C);					
						HSSFRow filaTotalBenef = hojaF14.getRow(FiseConstants.NRO_FILA_NROTOTAL_BENEFICIARIOS_F14C);					
						HSSFRow filaCostoPromedio = hojaF14.getRow(FiseConstants.NRO_FILA_COSTO_PROMEDIO_F14C);	
						
						HSSFRow filaCostoCoord = hojaF14.getRow(FiseConstants.NRO_FILA_COSTO_COORD_F14C);	
						HSSFRow filaCostoSupe = hojaF14.getRow(FiseConstants.NRO_FILA_COSTO_SUPE_F14C);
						HSSFRow filaCostoGest = hojaF14.getRow(FiseConstants.NRO_FILA_COSTO_GEST_F14C);						
						HSSFRow filaCostoAsist = hojaF14.getRow(FiseConstants.NRO_FILA_COSTO_ASIST_F14C);					
						
						/*********OBTENIENDO VALORES DE CADA CELDA Y SETEANDO AL BEAN PRA DESPUES VALIDAR*********/		
									
						HSSFCell celdaCodEmpresa = filaCodEmprea.getCell(FiseConstants.NRO_CELDA_CODEMPRESA_F14C);
						logger.info("columna Codigo empresa:  "+celdaCodEmpresa.getColumnIndex());
						//bean.setCodEmpresa(String.valueOf(celdaCodEmpresa));  
						bean.setCodEmpresa(String.valueOf(celdaCodEmpresa.getRichStringCellValue()));  
						
						logger.info("columna Codigo empresa:  "+celdaCodEmpresa.getRichStringCellValue());
						
						HSSFCell celdaAnio = filaAnioMes.getCell(FiseConstants.NRO_CELDA_ANIO_F14C);
						bean.setAnioPres(String.valueOf(celdaAnio)); 
						
						HSSFCell celdaMes = filaAnioMes.getCell(FiseConstants.NRO_CELDA_MES_F14C);	
						bean.setMesPres(String.valueOf(celdaMes)); 
						
						HSSFCell celdaSede = filaSede.getCell(FiseConstants.NRO_CELDA_SEDE_F14C);
						bean.setNombreSede(String.valueOf(celdaSede));
						
						
						HSSFCell celdaNroBenefR = filaTotalBenef.getCell(FiseConstants.NRO_CELDA_NUM_BENEF_RURAL_F14C);
						bean.setNumRural(String.valueOf(celdaNroBenefR));  
								
						HSSFCell celdaNroBenefP = filaTotalBenef.getCell(FiseConstants.NRO_CELDA_NUM_BENEF_PROV_F14C);					
						bean.setNumUrbProv(String.valueOf(celdaNroBenefP));  
						
						HSSFCell celdaNroBenefL = filaTotalBenef.getCell(FiseConstants.NRO_CELDA_NUM_BENEF_LIMA_F14C);					
						
						
						HSSFCell celdaCostPromR = filaCostoPromedio.getCell(FiseConstants.NRO_CELDA_COSTOPROM_RURAL_F14C);
						bean.setCostoPromRural(String.valueOf(celdaCostPromR));  
						
						HSSFCell celdaCostPromP = filaCostoPromedio.getCell(FiseConstants.NRO_CELDA_COSTOPROM_PROV_F14C);
						bean.setCostoPromUrbProv(String.valueOf(celdaCostPromP));  
						
						HSSFCell celdaCostPromL = filaCostoPromedio.getCell(FiseConstants.NRO_CELDA_COSTOPROM_LIMA_F14C);
										
						//RURAL				
						HSSFCell celdaCantDRCoord = filaCostoCoord.getCell(FiseConstants.NRO_CELDA_CANTDR_COORD_F14C);					
						HSSFCell celdaCostDRCoord = filaCostoCoord.getCell(FiseConstants.NRO_CELDA_COSTDR_COORD_F14C);				
						HSSFCell celdaCantIRCoord = filaCostoCoord.getCell(FiseConstants.NRO_CELDA_CANTIR_COORD_F14C);					
						HSSFCell celdaCostIRCoord = filaCostoCoord.getCell(FiseConstants.NRO_CELDA_COSTIR_COORD_F14C);					
						
						HSSFCell celdaCantDRSupe = filaCostoSupe.getCell(FiseConstants.NRO_CELDA_CANTDR_SUPE_F14C);					
						HSSFCell celdaCostDRSupe = filaCostoSupe.getCell(FiseConstants.NRO_CELDA_COSTDR_SUPE_F14C);					
						HSSFCell celdaCantIRSupe = filaCostoSupe.getCell(FiseConstants.NRO_CELDA_CANTIR_SUPE_F14C);						
						HSSFCell celdaCostIRSupe = filaCostoSupe.getCell(FiseConstants.NRO_CELDA_COSTIR_SUPE_F14C);					
						
						HSSFCell celdaCantDRGest = filaCostoGest.getCell(FiseConstants.NRO_CELDA_CANTDR_GEST_F14C);				
						HSSFCell celdaCostDRGest = filaCostoGest.getCell(FiseConstants.NRO_CELDA_COSTDR_GEST_F14C);						
						HSSFCell celdaCantIRGest = filaCostoGest.getCell(FiseConstants.NRO_CELDA_CANTIR_GEST_F14C);					
						HSSFCell celdaCostIRGest = filaCostoGest.getCell(FiseConstants.NRO_CELDA_COSTIR_GEST_F14C);	
						
						HSSFCell celdaCantDRAsist = filaCostoAsist.getCell(FiseConstants.NRO_CELDA_CANTDR_ASIST_F14C);				
						HSSFCell celdaCostDRAsist = filaCostoAsist.getCell(FiseConstants.NRO_CELDA_COSTDR_ASIST_F14C);					
						HSSFCell celdaCantIRAsist = filaCostoAsist.getCell(FiseConstants.NRO_CELDA_CANTIR_ASIST_F14C);					
						HSSFCell celdaCostIRAsist = filaCostoAsist.getCell(FiseConstants.NRO_CELDA_COSTIR_ASIST_F14C);				
						
						//PROVINCIA				
						HSSFCell celdaCantDPCoord = filaCostoCoord.getCell(FiseConstants.NRO_CELDA_CANTDP_COORD_F14C);					
						HSSFCell celdaCostDPCoord = filaCostoCoord.getCell(FiseConstants.NRO_CELDA_COSTDP_COORD_F14C);					
						HSSFCell celdaCantIPCoord = filaCostoCoord.getCell(FiseConstants.NRO_CELDA_CANTIP_COORD_F14C);					
						HSSFCell celdaCostIPCoord = filaCostoCoord.getCell(FiseConstants.NRO_CELDA_COSTIP_COORD_F14C);	
						
						HSSFCell celdaCantDPSupe = filaCostoSupe.getCell(FiseConstants.NRO_CELDA_CANTDP_SUPE_F14C);						
						HSSFCell celdaCostDPSupe = filaCostoSupe.getCell(FiseConstants.NRO_CELDA_COSTDP_SUPE_F14C);					
						HSSFCell celdaCantIPSupe = filaCostoSupe.getCell(FiseConstants.NRO_CELDA_CANTIP_SUPE_F14C);					
						HSSFCell celdaCostIPSupe = filaCostoSupe.getCell(FiseConstants.NRO_CELDA_COSTIP_SUPE_F14C);					
						
						HSSFCell celdaCantDPGest = filaCostoGest.getCell(FiseConstants.NRO_CELDA_CANTDP_GEST_F14C);					
						HSSFCell celdaCostDPGest = filaCostoGest.getCell(FiseConstants.NRO_CELDA_COSTDP_GEST_F14C);				
						HSSFCell celdaCantIPGest = filaCostoGest.getCell(FiseConstants.NRO_CELDA_CANTIP_GEST_F14C);						
						HSSFCell celdaCostIPGest = filaCostoGest.getCell(FiseConstants.NRO_CELDA_COSTIP_GEST_F14C);	
						
						HSSFCell celdaCantDPAsist = filaCostoAsist.getCell(FiseConstants.NRO_CELDA_CANTDP_ASIST_F14C);					
						HSSFCell celdaCostDPAsist = filaCostoAsist.getCell(FiseConstants.NRO_CELDA_COSTDP_ASIST_F14C);					
						HSSFCell celdaCantIPAsist = filaCostoAsist.getCell(FiseConstants.NRO_CELDA_CANTIP_ASIST_F14C);					
						HSSFCell celdaCostIPAsist = filaCostoAsist.getCell(FiseConstants.NRO_CELDA_COSTIP_ASIST_F14C);
						
						//LIMA
						HSSFCell celdaCantDLCoord = filaCostoCoord.getCell(FiseConstants.NRO_CELDA_CANTDL_COORD_F14C);					
						HSSFCell celdaCostDLCoord = filaCostoCoord.getCell(FiseConstants.NRO_CELDA_COSTDL_COORD_F14C);					
						HSSFCell celdaCantILCoord = filaCostoCoord.getCell(FiseConstants.NRO_CELDA_CANTIL_COORD_F14C);					
						HSSFCell celdaCostILCoord = filaCostoCoord.getCell(FiseConstants.NRO_CELDA_COSTIL_COORD_F14C);
						
						HSSFCell celdaCantDLSupe = filaCostoSupe.getCell(FiseConstants.NRO_CELDA_CANTDL_SUPE_F14C);					
						HSSFCell celdaCostDLSupe = filaCostoSupe.getCell(FiseConstants.NRO_CELDA_COSTDL_SUPE_F14C);					
						HSSFCell celdaCantILSupe = filaCostoSupe.getCell(FiseConstants.NRO_CELDA_CANTIL_SUPE_F14C);					
						HSSFCell celdaCostILSupe = filaCostoSupe.getCell(FiseConstants.NRO_CELDA_COSTIL_SUPE_F14C);
						
						HSSFCell celdaCantDLGest = filaCostoGest.getCell(FiseConstants.NRO_CELDA_CANTDL_GEST_F14C);					
						HSSFCell celdaCostDLGest = filaCostoGest.getCell(FiseConstants.NRO_CELDA_COSTDL_GEST_F14C);					
						HSSFCell celdaCantILGest = filaCostoGest.getCell(FiseConstants.NRO_CELDA_CANTIL_GEST_F14C);					
						HSSFCell celdaCostILGest = filaCostoGest.getCell(FiseConstants.NRO_CELDA_COSTIL_GEST_F14C);
						
						HSSFCell celdaCantDLAsist = filaCostoAsist.getCell(FiseConstants.NRO_CELDA_CANTDL_ASIST_F14C);					
						HSSFCell celdaCostDLAsist = filaCostoAsist.getCell(FiseConstants.NRO_CELDA_COSTDL_ASIST_F14C);					
						HSSFCell celdaCantILAsist = filaCostoAsist.getCell(FiseConstants.NRO_CELDA_CANTIL_ASIST_F14C);					
						HSSFCell celdaCostILAsist = filaCostoAsist.getCell(FiseConstants.NRO_CELDA_COSTIL_ASIST_F14C);
						
						/********INICIO VALIDACION DE CAMPOS*/	
						/********************CABECERA********************/						
						if(FormatoUtil.isNotBlank(bean.getCodEmpresa())&&bean.getCodEmpresa().trim().length()>4){						
							cont++;
							sMsg="ERROR";
							fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
									listaError, cont, FiseConstants.COD_ERROR_F12_40);				
						}else if(FormatoUtil.isBlank(bean.getCodEmpresa())){					
							cont++;						
							sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
									listaError, cont, FiseConstants.COD_ERROR_F12_30);
						}	
						
						if(FormatoUtil.isBlank(bean.getAnioPres())){					
							cont++;						
							sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
									listaError, cont, FiseConstants.COD_ERROR_F12_50);
						}else if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getAnioPres())){						
							cont++;
							sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
									listaError, cont, FiseConstants.COD_ERROR_F12_60);		
						}else{
							BigDecimal anioParse = new BigDecimal(bean.getAnioPres().trim());
							bean.setAnioPres(String.valueOf(anioParse.setScale(0)));   
							if(bean.getAnioPres().length()!=4){
								cont++;
								sMsg="ERROR";
										fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F12_60);	
							}
						}
						
						if(FormatoUtil.isBlank(bean.getMesPres())){						
							cont++;						
							sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
									listaError, cont, FiseConstants.COD_ERROR_F12_70);
						}else if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getMesPres())){ 						
							cont++;
							sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
									  listaError, cont, FiseConstants.COD_ERROR_F12_80);		
						}else{
							BigDecimal mesParse = new BigDecimal(bean.getMesPres().trim());
							bean.setMesPres(String.valueOf(mesParse.setScale(0)));
							if(bean.getMesPres().length()>2){
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
								    listaError, cont, FiseConstants.COD_ERROR_F12_80);			
							}
						}					
						
						/*if(FormatoUtil.isBlank(bean.getNombreSede())){					
							cont++;						
							sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
									listaError, cont, FiseConstants.COD_ERROR_F14C_360);
						}*/
							
						/*if(FormatoUtil.isBlank(bean.getNumRural())){					
							cont++;						
							sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
									listaError, cont, FiseConstants.COD_ERROR_F14C_380);
						}else */
						if(FormatoUtil.isNotBlank(bean.getNumRural()) &&
								!FormatoUtil.validarCampoBigDecimalTxt(bean.getNumRural())){ 						
							cont++;
							sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
									listaError, cont, FiseConstants.COD_ERROR_F14C_390);
						}else if(FormatoUtil.isNotBlank(bean.getNumRural())){
							BigDecimal numR = new BigDecimal(bean.getNumRural());
							bean.setNumRural(String.valueOf(numR.setScale(0)));	
						}
						
						/*if(FormatoUtil.isBlank(bean.getNumUrbProv())){					
							cont++;						
							sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
									listaError, cont, FiseConstants.COD_ERROR_F14C_400);
						}else */
						if(FormatoUtil.isNotBlank(bean.getNumUrbProv()) &&
								!FormatoUtil.validarCampoBigDecimalTxt(bean.getNumUrbProv())){ 						
							cont++;
							sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
									listaError, cont, FiseConstants.COD_ERROR_F14C_410);
						}else if(FormatoUtil.isNotBlank(bean.getNumUrbProv())){
							BigDecimal numP = new BigDecimal(bean.getNumUrbProv());
							bean.setNumUrbProv(String.valueOf(numP.setScale(0)));	
						}				
							
						
						/**habilitados LIMA*/
						if(FiseConstants.COD_EMPRESA_EDELNOR.equals(codEmpresa) || 
								FiseConstants.COD_EMPRESA_LUZ_SUR.equals(codEmpresa))
						{
							bean.setNumUrbLima(String.valueOf(celdaNroBenefL));	
							/*if(FormatoUtil.isBlank(bean.getNumUrbLima())){					
								cont++;	
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_420);
							}else */
						    if(FormatoUtil.isNotBlank(bean.getNumUrbLima())&&
						    		!FormatoUtil.validarCampoBigDecimalTxt(bean.getNumUrbLima())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_430);
							}else if(FormatoUtil.isNotBlank(bean.getNumUrbLima())){
								BigDecimal numL = new BigDecimal(bean.getNumUrbLima());
								bean.setNumUrbLima(String.valueOf(numL.setScale(0)));	
							}
							
							bean.setCostoPromUrbLima(String.valueOf(celdaCostPromL));		
							/*if(FormatoUtil.isBlank(bean.getCostoPromUrbLima())){					
								cont++;	
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_480);
							}else*/
							if(FormatoUtil.isNotBlank(bean.getCostoPromUrbLima())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostoPromUrbLima())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_490);
							}					
						}//fin de habilitados lima
						
						bean.setCostoPromRural(String.valueOf(celdaCostPromR));						
						/*if(FormatoUtil.isBlank(bean.getCostoPromRural())){					
							cont++;	
							sMsg="ERROR";
							fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
									listaError, cont, FiseConstants.COD_ERROR_F14C_440);
						}else*/
						if(FormatoUtil.isNotBlank(bean.getCostoPromRural()) &&
								!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostoPromRural())){ 						
							cont++;
							sMsg="ERROR";
							fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
									listaError, cont, FiseConstants.COD_ERROR_F14C_450);
						}	
						
						bean.setCostoPromUrbProv(String.valueOf(celdaCostPromP));			
						/*if(FormatoUtil.isBlank(bean.getCostoPromUrbProv())){					
							cont++;	
							sMsg="ERROR";
							fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
									listaError, cont, FiseConstants.COD_ERROR_F14C_460);
						}else */
						if(FormatoUtil.isNotBlank(bean.getCostoPromUrbProv()) &&
								!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostoPromUrbProv())){ 						
							cont++;
							sMsg="ERROR";
							fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
									listaError, cont, FiseConstants.COD_ERROR_F14C_470);
						}					
									
						/*****DETALLE - RURAL****/	
						
						if(FiseConstants.COSTO_DIRECTO_F14C.equals(flagCosto))
						{ 
							/*RURAL**/
							/**COORDINADOR*/
							bean.setCanDRCoord(String.valueOf(celdaCantDRCoord));
							/*if(FormatoUtil.isNotBlank(bean.getCanDRCoord())){					
								cont++;	
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_500);
							}else */
							if(FormatoUtil.isNotBlank(bean.getCanDRCoord()) &&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDRCoord())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_510);
							}	
							
							bean.setCostDRCoord(String.valueOf(celdaCostDRCoord));
							/*if(FormatoUtil.isNotBlank(bean.getCostDRCoord())){					
								cont++;	
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_520);
							}else */
							if(FormatoUtil.isNotBlank(bean.getCostDRCoord()) &&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDRCoord())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_530);
							}						
							/*****SUPERVISOR*****/		
							bean.setCanDRSupe(String.valueOf(celdaCantDRSupe));
							/*if(FormatoUtil.isNotBlank(bean.getCanDRSupe())){							
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_580);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCanDRSupe())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDRSupe())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_590);
							}	
							
							bean.setCostDRSupe(String.valueOf(celdaCostDRSupe));
							/*if(FormatoUtil.isNotBlank(bean.getCostDRSupe())){					
								cont++;		
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_600);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCostDRSupe()) &&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDRSupe())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_610);
							}					
							/*******GESTOR*******/
							bean.setCanDRGest(String.valueOf(celdaCantDRGest));
							/*if(FormatoUtil.isNotBlank(bean.getCanDRGest())){							
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_660);
							}else */if(FormatoUtil.isNotBlank(bean.getCanDRGest()) &&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDRGest())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_670);
							}
							
							bean.setCostDRGest(String.valueOf(celdaCostDRGest));
							/*if(FormatoUtil.isNotBlank(bean.getCostDRGest())){					
								cont++;	
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_680);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCostDRGest())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDRGest())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_690);
							}					
							/*******ASISTENTE*******/
							bean.setCanDRAsist(String.valueOf(celdaCantDRAsist));
							/*if(FormatoUtil.isNotBlank(bean.getCanDRAsist())){						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_740);
							}else */if(FormatoUtil.isNotBlank(bean.getCanDRAsist()) &&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDRAsist())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_750);
							}
							
							bean.setCostDRAsist(String.valueOf(celdaCostDRAsist));
							/*if(FormatoUtil.isNotBlank(bean.getCostDRAsist())){					
								cont++;		
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_760);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCostDRAsist())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDRAsist())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_770);
							}						
							/*PROVINCIA**/
							/**COORDINADOR*/
							bean.setCanDPCoord(String.valueOf(celdaCantDPCoord));
							/*if(FormatoUtil.isNotBlank(bean.getCanDPCoord())){					
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_820);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCanDPCoord()) &&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDPCoord())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_830);
							}
							
							bean.setCostDPCoord(String.valueOf(celdaCostDPCoord));
							/*if(FormatoUtil.isNotBlank(bean.getCostDPCoord())){					
								cont++;	
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_840);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCostDPCoord()) &&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDPCoord())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_850);
							}					
							/*****SUPERVISOR*****/	
							bean.setCanDPSupe(String.valueOf(celdaCantDPSupe));
							/*if(FormatoUtil.isNotBlank(bean.getCanDPSupe())){					
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_900);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCanDPSupe())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDPSupe())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_910);
							}	
							
							bean.setCostDPSupe(String.valueOf(celdaCostDPSupe));
							/*if(FormatoUtil.isNotBlank(bean.getCostDPSupe())){					
								cont++;	
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_920);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCostDPSupe()) &&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDPSupe())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_930);
							}						
							/*******GESTOR*******/
							bean.setCanDPGest(String.valueOf(celdaCantDPGest));
							/*if(FormatoUtil.isNotBlank(bean.getCanDPGest())){					
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_980);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCanDPGest()) &&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDPGest())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_990);
							}	
							
							bean.setCostDPGest(String.valueOf(celdaCostDPGest));
							/*if(FormatoUtil.isNotBlank(bean.getCostDPGest())){					
								cont++;	
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_1000);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCostDPGest()) &&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDPGest())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_1010);
							}				
							/*******ASISTENTE*******/
							bean.setCanDPAsist(String.valueOf(celdaCantDPAsist));
							/*if(FormatoUtil.isNotBlank(bean.getCanDPAsist())){							
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_1060);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCanDPAsist()) &&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDPAsist())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_1070);
							}	
							
							bean.setCostDPAsist(String.valueOf(celdaCostDPAsist));
							/*if(FormatoUtil.isNotBlank(bean.getCostDPAsist())){					
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_1080);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCostDPAsist()) &&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDPAsist())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_1090);
							}						
							/**Habilitar LIMA*/
							if(FiseConstants.COD_EMPRESA_EDELNOR.equals(codEmpresa) || 
									FiseConstants.COD_EMPRESA_LUZ_SUR.equals(codEmpresa))
							{
								/**COORDINADOR*/
								bean.setCanDLCoord(String.valueOf(celdaCantDLCoord));
								/*if(FormatoUtil.isNotBlank(bean.getCanDLCoord())){					
									cont++;	
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1140);
								}else*/ if(FormatoUtil.isNotBlank(bean.getCanDLCoord())&&
										!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDLCoord())){ 						
									cont++;
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1150);
								}
								
								bean.setCostDLCoord(String.valueOf(celdaCostDLCoord));
								/*if(FormatoUtil.isNotBlank(bean.getCostDLCoord())){					
									cont++;	
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1160);
								}else*/ if(FormatoUtil.isNotBlank(bean.getCostDLCoord())&&
										!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDLCoord())){ 						
									cont++;
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1170);
								}								
								/*****SUPERVISOR*****/	
								bean.setCanDLSupe(String.valueOf(celdaCantDLSupe));
								/*if(FormatoUtil.isNotBlank(bean.getCanDLSupe())){					
									cont++;	
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1220);
								}else*/ if(FormatoUtil.isNotBlank(bean.getCanDLSupe())&&
										!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDLSupe())){ 						
									cont++;
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1230);
								}						
										
								bean.setCostDLSupe(String.valueOf(celdaCostDLSupe));
								/*if(FormatoUtil.isNotBlank(bean.getCostDLSupe())){					
									cont++;	
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1240);
								}else*/ if(FormatoUtil.isNotBlank(bean.getCostDLSupe())&&
										!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDLSupe())){ 						
									cont++;
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1250);
								}							
								/*******GESTOR*******/
								bean.setCanDLGest(String.valueOf(celdaCantDLGest));
								/*if(FormatoUtil.isNotBlank(bean.getCanDLGest())){					
									cont++;	
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1300);
								}else*/ if(FormatoUtil.isNotBlank(bean.getCanDLGest())&&
										!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDLGest())){ 						
									cont++;
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1310);
								}
								
								bean.setCostDLGest(String.valueOf(celdaCostDLGest));
								/*if(FormatoUtil.isNotBlank(bean.getCostDLGest())){					
									cont++;	
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1320);
								}else*/ if(FormatoUtil.isNotBlank(bean.getCostDLGest())&&
										!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDLGest())){ 						
									cont++;
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1330);
								}							
								/*******ASISTENTE*******/
								bean.setCanDLAsist(String.valueOf(celdaCantDLAsist));
								/*if(FormatoUtil.isNotBlank(bean.getCanDLAsist())){					
									cont++;	
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1380);
								}else*/ if(FormatoUtil.isNotBlank(bean.getCanDLAsist())&&
										!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDLAsist())){ 						
									cont++;
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1390);
								}
								
								bean.setCostDLAsist(String.valueOf(celdaCostDLAsist));
								/*if(FormatoUtil.isNotBlank(bean.getCostDLAsist())){					
									cont++;	
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1400);
								}else*/ if(FormatoUtil.isNotBlank(bean.getCostDLAsist())&&
										!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDLAsist())){ 						
									cont++;
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1410);
								}				
							}					
						}else if(FiseConstants.COSTO_INDIRECTO_F14C.equals(flagCosto)){
							/*RURAL**/
							/**COORDINADOR*/
							bean.setCanIRCoord(String.valueOf(celdaCantIRCoord));
							/*if(FormatoUtil.isNotBlank(bean.getCanIRCoord())){					
								cont++;	
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_540);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCanIRCoord())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanIRCoord())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_550);
							}	
							
							bean.setCostIRCoord(String.valueOf(celdaCostIRCoord));
							/*if(FormatoUtil.isNotBlank(bean.getCostIRCoord())){					
								cont++;		
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_560);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCostIRCoord())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostIRCoord())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_570);
							}						
							/*****SUPERVISOR*****/		
							bean.setCanIRSupe(String.valueOf(celdaCantIRSupe));
							/*if(FormatoUtil.isNotBlank(bean.getCanIRSupe())){							
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_620);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCanIRSupe()) &&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanIRSupe())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_630);
							}	
							
							bean.setCostIRSupe(String.valueOf(celdaCostIRSupe));
							/*if(FormatoUtil.isNotBlank(bean.getCostIRSupe())){					
								cont++;	
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_640);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCostIRSupe())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostIRSupe())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_650);
							}					
							/*******GESTOR*******/
							bean.setCanIRGest(String.valueOf(celdaCantIRGest));
							/*if(FormatoUtil.isNotBlank(bean.getCanIRGest())){							
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_700);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCanIRGest())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanIRGest())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_710);
							}
							
							bean.setCostIRGest(String.valueOf(celdaCostIRGest));
							/*if(FormatoUtil.isNotBlank(bean.getCostIRGest())){					
								cont++;	
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_720);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCostIRGest())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostIRGest())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_730);
							}					
							/*******ASISTENTE*******/
							bean.setCanIRAsist(String.valueOf(celdaCantIRAsist));
							/*if(FormatoUtil.isNotBlank(bean.getCanIRAsist())){						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_780);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCanIRAsist())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanIRAsist())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_790);
							}
							
							bean.setCostIRAsist(String.valueOf(celdaCostIRAsist));
							/*if(FormatoUtil.isNotBlank(bean.getCostIRAsist())){					
								cont++;	
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_800);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCostIRAsist())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostIRAsist())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_810);
							}						
							/*PROVINCIA**/
							/**COORDINADOR*/
							bean.setCanIPCoord(String.valueOf(celdaCantIPCoord));
							/*if(FormatoUtil.isNotBlank(bean.getCanIPCoord())){					
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_860);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCanIPCoord())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanIPCoord())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_870);
							}
							
							bean.setCostIPCoord(String.valueOf(celdaCostIPCoord));
							/*if(FormatoUtil.isNotBlank(bean.getCostIPCoord())){					
								cont++;	
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_880);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCostIPCoord())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostIPCoord())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_890);
							}					
							/*****SUPERVISOR*****/	
							bean.setCanIPSupe(String.valueOf(celdaCantIPSupe));
							/*if(FormatoUtil.isNotBlank(bean.getCanIPSupe())){					
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_940);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCanIPSupe())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanIPSupe())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_950);
							}	
							
							bean.setCostIPSupe(String.valueOf(celdaCostIPSupe));
							/*if(FormatoUtil.isNotBlank(bean.getCostIPSupe())){					
								cont++;		
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_960);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCostIPSupe())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostIPSupe())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_970);
							}						
							/*******GESTOR*******/
							bean.setCanIPGest(String.valueOf(celdaCantIPGest));
							/*if(FormatoUtil.isNotBlank(bean.getCanIPGest())){					
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_1020);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCanIPGest())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanIPGest())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_1030);
							}	
							
							bean.setCostIPGest(String.valueOf(celdaCostIPGest));
							/*if(FormatoUtil.isNotBlank(bean.getCostIPGest())){					
								cont++;		
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_1040);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCostIPGest())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostIPGest())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_1050);
							}				
							/*******ASISTENTE*******/
							bean.setCanIPAsist(String.valueOf(celdaCantIPAsist));
							/*if(FormatoUtil.isNotBlank(bean.getCanIPAsist())){							
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_1110);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCanIPAsist())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanIPAsist())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_1530);
							}	
							
							bean.setCostIPAsist(String.valueOf(celdaCostIPAsist));
							/*if(FormatoUtil.isNotBlank(bean.getCostIPAsist())){					
								cont++;	
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_1120);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCostIPAsist())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostIPAsist())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_1130);
							}				
							
							/**Habilitar LIMA*/
							if(FiseConstants.COD_EMPRESA_EDELNOR.equals(codEmpresa) || 
									FiseConstants.COD_EMPRESA_LUZ_SUR.equals(codEmpresa))
							{
								/**COORDINADOR*/
								bean.setCanILCoord(String.valueOf(celdaCantILCoord));
								/*if(FormatoUtil.isNotBlank(bean.getCanILCoord())){					
									cont++;	
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1180);
								}else*/ if(FormatoUtil.isNotBlank(bean.getCanILCoord())&&
										!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanILCoord())){ 						
									cont++;
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1190);
								}
								
								bean.setCostILCoord(String.valueOf(celdaCostILCoord));
								/*if(FormatoUtil.isNotBlank(bean.getCostILCoord())){					
									cont++;	
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1200);
								}else*/ if(FormatoUtil.isNotBlank(bean.getCostILCoord())&&
										!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostILCoord())){ 						
									cont++;
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1210);
								}								
								/*****SUPERVISOR*****/	
								bean.setCanILSupe(String.valueOf(celdaCantILSupe));
								/*if(FormatoUtil.isNotBlank(bean.getCanILSupe())){					
									cont++;		
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1260);
								}else*/ if(FormatoUtil.isNotBlank(bean.getCanILSupe())&&
										!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanILSupe())){ 						
									cont++;
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1270);
								}						
										
								bean.setCostILSupe(String.valueOf(celdaCostILSupe));
								/*if(FormatoUtil.isNotBlank(bean.getCostILSupe())){					
									cont++;	
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1280);
								}else*/ if(FormatoUtil.isNotBlank(bean.getCostILSupe())&&
										!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostILSupe())){ 						
									cont++;
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1290);
								}							
								/*******GESTOR*******/
								bean.setCanILGest(String.valueOf(celdaCantILGest));
								/*if(FormatoUtil.isNotBlank(bean.getCanILGest())){					
									cont++;		
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1340);
								}else*/ if(FormatoUtil.isNotBlank(bean.getCanILGest())&&
										!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanILGest())){ 						
									cont++;
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1350);
								}
								
								bean.setCostILGest(String.valueOf(celdaCostILGest));
								/*if(FormatoUtil.isNotBlank(bean.getCostILGest())){					
									cont++;	
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1360);
								}else*/ if(FormatoUtil.isNotBlank(bean.getCostILGest())&&
										!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostILGest())){ 						
									cont++;
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1370);
								}							
								/*******ASISTENTE*******/
								bean.setCanILAsist(String.valueOf(celdaCantILAsist));
								/*if(FormatoUtil.isNotBlank(bean.getCanILAsist())){					
									cont++;	
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1420);
								}else*/ if(FormatoUtil.isNotBlank(bean.getCanILAsist())&&
										!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanILAsist())){ 						
									cont++;
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1430);
								}
								
								bean.setCostILAsist(String.valueOf(celdaCostILAsist));
								/*if(FormatoUtil.isNotBlank(bean.getCostILAsist())){					
									cont++;	
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1440);
								}else*/ if(FormatoUtil.isNotBlank(bean.getCostILAsist())&&
										!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostILAsist())){ 						
									cont++;
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1450);
								}				
							}						
						}else
						{
							/******AMBOS DIRECTO e INDIRECTO************/						
							/****************COSTO DIRECTO*************************/
							/*RURAL**/
							/**COORDINADOR*/
							bean.setCanDRCoord(String.valueOf(celdaCantDRCoord));
							/*if(FormatoUtil.isNotBlank(bean.getCanDRCoord())){					
								cont++;	
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_500);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCanDRCoord())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDRCoord())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_510);
							}	
							
							bean.setCostDRCoord(String.valueOf(celdaCostDRCoord));
							/*if(FormatoUtil.isNotBlank(bean.getCostDRCoord())){					
								cont++;	
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_520);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCostDRCoord())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDRCoord())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_530);
							}						
							/*****SUPERVISOR*****/		
							bean.setCanDRSupe(String.valueOf(celdaCantDRSupe));
							/*if(FormatoUtil.isNotBlank(bean.getCanDRSupe())){							
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_580);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCanDRSupe())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDRSupe())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_590);
							}	
							
							bean.setCostDRSupe(String.valueOf(celdaCostDRSupe));
							/*if(FormatoUtil.isNotBlank(bean.getCostDRSupe())){					
								cont++;	
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_600);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCostDRSupe())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDRSupe())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_610);
							}					
							/*******GESTOR*******/
							bean.setCanDRGest(String.valueOf(celdaCantDRGest));
							/*if(FormatoUtil.isNotBlank(bean.getCanDRGest())){							
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_660);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCanDRGest())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDRGest())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_670);
							}
							
							bean.setCostDRGest(String.valueOf(celdaCostDRGest));
							/*if(FormatoUtil.isNotBlank(bean.getCostDRGest())){					
								cont++;	
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_680);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCostDRGest())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDRGest())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_690);
							}					
							/*******ASISTENTE*******/
							bean.setCanDRAsist(String.valueOf(celdaCantDRAsist));
							/*if(FormatoUtil.isNotBlank(bean.getCanDRAsist())){						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_740);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCanDRAsist())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDRAsist())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_750);
							}
							
							bean.setCostDRAsist(String.valueOf(celdaCostDRAsist));
							/*if(FormatoUtil.isNotBlank(bean.getCostDRAsist())){					
								cont++;	
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_760);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCostDRAsist())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDRAsist())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_770);
							}						
							/*PROVINCIA**/
							/**COORDINADOR*/
							bean.setCanDPCoord(String.valueOf(celdaCantDPCoord));
							/*if(FormatoUtil.isNotBlank(bean.getCanDPCoord())){					
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_820);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCanDPCoord())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDPCoord())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_830);
							}
							
							bean.setCostDPCoord(String.valueOf(celdaCostDPCoord));
							/*if(FormatoUtil.isNotBlank(bean.getCostDPCoord())){					
								cont++;	
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_840);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCostDPCoord())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDPCoord())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_850);
							}					
							/*****SUPERVISOR*****/	
							bean.setCanDPSupe(String.valueOf(celdaCantDPSupe));
							/*if(FormatoUtil.isNotBlank(bean.getCanDPSupe())){					
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_900);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCanDPSupe())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDPSupe())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_910);
							}	
							
							bean.setCostDPSupe(String.valueOf(celdaCostDPSupe));
							/*if(FormatoUtil.isNotBlank(bean.getCostDPSupe())){					
								cont++;	
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_920);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCostDPSupe())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDPSupe())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_930);
							}						
							/*******GESTOR*******/
							bean.setCanDPGest(String.valueOf(celdaCantDPGest));
							/*if(FormatoUtil.isNotBlank(bean.getCanDPGest())){					
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_980);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCanDPGest())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDPGest())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_990);
							}	
							
							bean.setCostDPGest(String.valueOf(celdaCostDPGest));
							/*if(FormatoUtil.isNotBlank(bean.getCostDPGest())){					
								cont++;	
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_1000);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCostDPGest())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDPGest())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_1010);
							}				
							/*******ASISTENTE*******/
							bean.setCanDPAsist(String.valueOf(celdaCantDPAsist));
							/*if(FormatoUtil.isNotBlank(bean.getCanDPAsist())){							
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_1060);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCanDPAsist())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDPAsist())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_1070);
							}	
							
							bean.setCostDPAsist(String.valueOf(celdaCostDPAsist));
							/*if(FormatoUtil.isNotBlank(bean.getCostDPAsist())){					
								cont++;	
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_1080);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCostDPAsist())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDPAsist())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_1090);
							}					
							/**********************INDIRECTO***************/
							/**COORDINADOR*/
							bean.setCanIRCoord(String.valueOf(celdaCantIRCoord));
							/*if(FormatoUtil.isNotBlank(bean.getCanIRCoord())){					
								cont++;	
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_540);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCanIRCoord())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanIRCoord())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_550);
							}	
							
							bean.setCostIRCoord(String.valueOf(celdaCostIRCoord));
							/*if(FormatoUtil.isNotBlank(bean.getCostIRCoord())){					
								cont++;	
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_560);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCostIRCoord())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostIRCoord())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_570);
							}						
							/*****SUPERVISOR*****/		
							bean.setCanIRSupe(String.valueOf(celdaCantIRSupe));
							/*if(FormatoUtil.isNotBlank(bean.getCanIRSupe())){							
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_620);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCanIRSupe())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanIRSupe())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_630);
							}	
							
							bean.setCostIRSupe(String.valueOf(celdaCostIRSupe));
							/*if(FormatoUtil.isNotBlank(bean.getCostIRSupe())){					
								cont++;	
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_640);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCostIRSupe())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostIRSupe())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_650);
							}					
							/*******GESTOR*******/
							bean.setCanIRGest(String.valueOf(celdaCantIRGest));
							/*if(FormatoUtil.isNotBlank(bean.getCanIRGest())){							
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_700);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCanIRGest())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanIRGest())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_710);
							}
							
							bean.setCostIRGest(String.valueOf(celdaCostIRGest));
							/*if(FormatoUtil.isNotBlank(bean.getCostIRGest())){					
								cont++;	
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_720);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCostIRGest())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostIRGest())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_730);
							}					
							/*******ASISTENTE*******/
							bean.setCanIRAsist(String.valueOf(celdaCantIRAsist));
							/*if(FormatoUtil.isNotBlank(bean.getCanIRAsist())){						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_780);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCanIRAsist())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanIRAsist())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_790);
							}
							
							bean.setCostIRAsist(String.valueOf(celdaCostIRAsist));
							/*if(FormatoUtil.isNotBlank(bean.getCostIRAsist())){					
								cont++;	
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_800);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCostIRAsist())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostIRAsist())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_810);
							}						
							/*PROVINCIA**/
							/**COORDINADOR*/
							bean.setCanIPCoord(String.valueOf(celdaCantIPCoord));
							/*if(FormatoUtil.isNotBlank(bean.getCanIPCoord())){					
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_860);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCanIPCoord())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanIPCoord())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_870);
							}
							
							bean.setCostIPCoord(String.valueOf(celdaCostIPCoord));
							/*if(FormatoUtil.isNotBlank(bean.getCostIPCoord())){					
								cont++;	
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_880);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCostIPCoord())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostIPCoord())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_890);
							}					
							/*****SUPERVISOR*****/	
							bean.setCanIPSupe(String.valueOf(celdaCantIPSupe));
							/*if(FormatoUtil.isNotBlank(bean.getCanIPSupe())){					
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_940);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCanIPSupe())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanIPSupe())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_950);
							}	
							
							bean.setCostIPSupe(String.valueOf(celdaCostIPSupe));
							/*if(FormatoUtil.isNotBlank(bean.getCostIPSupe())){					
								cont++;	
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_960);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCostIPSupe())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostIPSupe())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_970);
							}						
							/*******GESTOR*******/
							bean.setCanIPGest(String.valueOf(celdaCantIPGest));
							/*if(FormatoUtil.isNotBlank(bean.getCanIPGest())){					
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_1020);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCanIPGest())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanIPGest())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_1030);
							}	
							
							bean.setCostIPGest(String.valueOf(celdaCostIPGest));
							/*if(FormatoUtil.isNotBlank(bean.getCostIPGest())){					
								cont++;	
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_1040);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCostIPGest())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostIPGest())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_1050);
							}				
							/*******ASISTENTE*******/
							bean.setCanIPAsist(String.valueOf(celdaCantIPAsist));
							/*if(FormatoUtil.isNotBlank(bean.getCanIPAsist())){							
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_1110);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCanIPAsist())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanIPAsist())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_1530);
							}	
							
							bean.setCostIPAsist(String.valueOf(celdaCostIPAsist));
							/*if(FormatoUtil.isNotBlank(bean.getCostIPAsist())){					
								cont++;	
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_1120);
							}else*/ if(FormatoUtil.isNotBlank(bean.getCostIPAsist())&&
									!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostIPAsist())){ 						
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F14C_1130);
							}				
							
							/**Habilita LIMA**/
							if(FiseConstants.COD_EMPRESA_EDELNOR.equals(codEmpresa) || 
									FiseConstants.COD_EMPRESA_LUZ_SUR.equals(codEmpresa))
							{
								/*********COSTO DIRECTO********/
								/**COORDINADOR*/
								bean.setCanDLCoord(String.valueOf(celdaCantDLCoord));
								/*if(FormatoUtil.isNotBlank(bean.getCanDLCoord())){					
									cont++;	
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1140);
								}else*/ if(FormatoUtil.isNotBlank(bean.getCanDLCoord())&&
										!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDLCoord())){ 						
									cont++;
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1150);
								}
								
								bean.setCostDLCoord(String.valueOf(celdaCostDLCoord));
								/*if(FormatoUtil.isNotBlank(bean.getCostDLCoord())){					
									cont++;	
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1160);
								}else*/ if(FormatoUtil.isNotBlank(bean.getCostDLCoord())&&
										!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDLCoord())){ 						
									cont++;
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1170);
								}								
								/*****SUPERVISOR*****/	
								bean.setCanDLSupe(String.valueOf(celdaCantDLSupe));
								/*if(FormatoUtil.isNotBlank(bean.getCanDLSupe())){					
									cont++;
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1220);
								}else*/ if(FormatoUtil.isNotBlank(bean.getCanDLSupe())&&
										!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDLSupe())){ 						
									cont++;
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1230);
								}						
										
								bean.setCostDLSupe(String.valueOf(celdaCostDLSupe));
								/*if(FormatoUtil.isNotBlank(bean.getCostDLSupe())){					
									cont++;	
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1240);
								}else*/ if(FormatoUtil.isNotBlank(bean.getCostDLSupe())&&
										!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDLSupe())){ 						
									cont++;
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1250);
								}							
								/*******GESTOR*******/
								bean.setCanDLGest(String.valueOf(celdaCantDLGest));
								/*if(FormatoUtil.isNotBlank(bean.getCanDLGest())){					
									cont++;	
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1300);
								}else*/ if(FormatoUtil.isNotBlank(bean.getCanDLGest())&&
										!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDLGest())){ 						
									cont++;
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1310);
								}
								
								bean.setCostDLGest(String.valueOf(celdaCostDLGest));
								/*if(FormatoUtil.isNotBlank(bean.getCostDLGest())){					
									cont++;	
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1320);
								}else*/ if(FormatoUtil.isNotBlank(bean.getCostDLGest())&&
										!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDLGest())){ 						
									cont++;
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1330);
								}							
								/*******ASISTENTE*******/
								bean.setCanDLAsist(String.valueOf(celdaCantDLAsist));
								/*if(FormatoUtil.isNotBlank(bean.getCanDLAsist())){					
									cont++;		
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1380);
								}else*/ if(FormatoUtil.isNotBlank(bean.getCanDLAsist())&&
										!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDLAsist())){ 						
									cont++;
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1390);
								}
								
								bean.setCostDLAsist(String.valueOf(celdaCostDLAsist));
								/*if(FormatoUtil.isNotBlank(bean.getCostDLAsist())){					
									cont++;	
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1400);
								}else*/ if(FormatoUtil.isNotBlank(bean.getCostDLAsist())&&
										!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDLAsist())){ 						
									cont++;
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1410);
								}	
								/*****COSTO INDIRECTO****/
								/**COORDINADOR*/
								bean.setCanILCoord(String.valueOf(celdaCantILCoord));
								/*if(FormatoUtil.isNotBlank(bean.getCanILCoord())){					
									cont++;	
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1180);
								}else*/ if(FormatoUtil.isNotBlank(bean.getCanILCoord())&&
										!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanILCoord())){ 						
									cont++;
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1190);
								}
								
								bean.setCostILCoord(String.valueOf(celdaCostILCoord));
								/*if(FormatoUtil.isNotBlank(bean.getCostILCoord())){					
									cont++;	
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1200);
								}else*/ if(FormatoUtil.isNotBlank(bean.getCostILCoord())&&
										!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostILCoord())){ 						
									cont++;
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1210);
								}								
								/*****SUPERVISOR*****/	
								bean.setCanILSupe(String.valueOf(celdaCantILSupe));
								/*if(FormatoUtil.isNotBlank(bean.getCanILSupe())){					
									cont++;	
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1260);
								}else*/ if(FormatoUtil.isNotBlank(bean.getCanILSupe())&&
										!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanILSupe())){ 						
									cont++;
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1270);
								}						
										
								bean.setCostILSupe(String.valueOf(celdaCostILSupe));
								/*if(FormatoUtil.isNotBlank(bean.getCostILSupe())){					
									cont++;	
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1280);
								}else*/ if(FormatoUtil.isNotBlank(bean.getCostILSupe())&&
										!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostILSupe())){ 						
									cont++;
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1290);
								}							
								/*******GESTOR*******/
								bean.setCanILGest(String.valueOf(celdaCantILGest));
								/*if(FormatoUtil.isNotBlank(bean.getCanILGest())){					
									cont++;	
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1340);
								}else*/ if(FormatoUtil.isNotBlank(bean.getCanILGest())&&
										!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanILGest())){ 						
									cont++;
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1340);
								}
								
								bean.setCostILGest(String.valueOf(celdaCostILGest));
								/*if(FormatoUtil.isNotBlank(bean.getCostILGest())){					
									cont++;	
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1350);
								}else*/ if(FormatoUtil.isNotBlank(bean.getCostILGest())&&
										!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostILGest())){ 						
									cont++;
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1360);
								}							
								/*******ASISTENTE*******/
								bean.setCanILAsist(String.valueOf(celdaCantILAsist));
								/*if(FormatoUtil.isNotBlank(bean.getCanILAsist())){					
									cont++;		
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1420);
								}else*/ if(FormatoUtil.isNotBlank(bean.getCanILAsist())&&
										!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanILAsist())){ 						
									cont++;
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1430);
								}
								
								bean.setCostILAsist(String.valueOf(celdaCostILAsist));
								/*if(FormatoUtil.isNotBlank(bean.getCostILAsist())){					
									cont++;	
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1440);
								}else*/ if(FormatoUtil.isNotBlank(bean.getCostILAsist())&&
										!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostILAsist())){ 						
									cont++;
									sMsg="ERROR";
									fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
											listaError, cont, FiseConstants.COD_ERROR_F14C_1450);
								}							
							}					
						}//fin de ambos D e I								
						/***FIN DE VALIDACION****/				
					    logger.info("Valor de del mesaje pasando la validacion:  "+sMsg); 
						if("".equals(sMsg) ){	
							logger.info("Entro a verificar cero errores de validacion"); 
							bean.setUsuario(user.getLogin());
							bean.setTerminal(user.getLoginIP());						
							bean.setNombreExel(archivo.getTitle());	
							logger.info("Cod empresa del for: "+codEmpresa);
							logger.info("anio pres del for: "+anioPres);
							int mesForm = Integer.valueOf(mesPres);
							logger.info("mes pres del for: "+mesForm);
							logger.info("anio inicio del for: "+anioIniVigencia);
							logger.info("anio fin del for: "+anioFinVigencia);
							
							logger.info("Cod empresa del bean: "+bean.getCodEmpresa());
							logger.info("anio pres del bean: "+bean.getAnioPres());
							
							logger.info("anio inicio del bean: "+bean.getAnioPres());
							logger.info("anio fin del bean: "+bean.getAnioPres());
							int mesBean = Integer.valueOf(bean.getMesPres());						
							logger.info("mes pres del bean: "+mesBean);
							
							if(codEmpresa.equals(bean.getCodEmpresa()) && 
									anioPres.equals(bean.getAnioPres()) &&	
									mesForm==mesBean)
							{
								logger.info("Entro a verificar datos si coincide la Pk"); 
								if( FiseConstants.FLAG_CARGAEXCEL_FORMULARIONUEVO.equals(flagCarga) ){
									bean.setCodEmpresa(codEmpresa);
									bean.setAnioPres(anioPres);
									bean.setMesPres(mesPres);
									bean.setAnoIniVigencia(anioIniVigencia);
									bean.setAnoFinVigencia(anioFinVigencia); 
									bean.setEtapa(etapa); 
									bean.setFlagCosto(flagCosto); 
									logger.info("Entro agrabar nuevo registro"); 
									valor=formato14CGartService.insertarDatosFormato14C(bean);		
									logger.info("valor del  agrabar nuevo registro: "+valor); 
								}else if( FiseConstants.FLAG_CARGAEXCEL_FORMULARIOMODIFICACION.equals(flagCarga) ){
									bean.setCodEmpresa(codEmpresa);
									bean.setAnioPres(anioPres);
									bean.setMesPres(mesPres);
									bean.setAnoIniVigencia(anioIniVigencia);
									bean.setAnoFinVigencia(anioFinVigencia); 
									bean.setEtapa(etapa); 
									bean.setFlagCosto(flagCosto); 
									logger.info("Entro actualizar nuevo registro"); 
									valor =formato14CGartService.actualizarDatosFormato14C(bean);
									logger.info("valor del  actualizar nuevo registro: "+valor); 
								}
							}else{
								logger.info("Entro a verificar datos si coincide la Pk no coincide--"); 
								cont++;
								sMsg="ERROR";
								fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
										listaError, cont, FiseConstants.COD_ERROR_F12_210);//error codigo pk no coinciden
							}
						}
					}else{
						cont++;
						sMsg = sMsg + "No se encuentra la hoja "+FiseConstants.NOMBRE_HOJA_FORMATO14C+" en el archivo cargado";
					}//else hoja no existe con el nobre				
				}//fin del if libro			
			}
			is.close();
			if("2".equals(valor)){ 
				cont++;
				sMsg="ERROR";
				fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
						listaError, cont, FiseConstants.COD_ERROR_F14C_1540);//error codigo ya esta registrado 	
			}else if("0".equals(valor)){
				cont++;
				sMsg="ERROR";
				fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
						listaError, cont, FiseConstants.COD_ERROR_F14C_1550);//error al grabar		
			}
		} catch (Exception e) {
			logger.error("Error al leer el archivo excel del F14C.",e);
			String error = e.getMessage();
			sMsg="ERROR";       	
			cont++;
			MensajeErrorBean errorBean = new MensajeErrorBean();
			errorBean.setId(cont);
			errorBean.setDescripcion(error);
			listaError.add(errorBean);
		}finally{
			StreamUtil.cleanUp(is);
		}
		formatoMensaje.setMensajeError(sMsg);
		formatoMensaje.setFormato14CBean(bean); 
		formatoMensaje.setValor(valor); 
		if(listaError.size()>0){
			formatoMensaje.setListaMensajeError(listaError);
		}
		return formatoMensaje;
	}  
    
    
    private Formato14AMensajeBean readTxtFile(FileEntry archivo, 
    		UploadPortletRequest uploadPortletRequest, User user, 
    		String flagCarga, String codigoEmpresa, String anioPresentacion, 
    		String mesPresentacion, String anioIniVigencia, String anioFinVigencia, 
    		String etapa,String flagCosto) {
      	//FLAG CARGA:
    	//	3: para registros nuevos
    	//	4: para registros modificados
    	//---------------------
    	Formato14AMensajeBean formatoMensaje = new Formato14AMensajeBean();
    	
    	InputStream is=null;    
    	List<MensajeErrorBean> listaError = new ArrayList<MensajeErrorBean>();    	
    	String sMsg = "";
    	int cont = 0;    	
    	List<CfgCampo> listaCampo = null;
    	Formato14CBean bean = new Formato14CBean();	
    	String valor = "";
    	try{
    		CfgTabla tabla = new CfgTabla();
    		tabla.setIdTabla(FiseConstants.ID_TABLA_FORMATO14C);
    		listaCampo = campoService.listarCamposByTabla(tabla);
    		if( listaCampo != null ){
    			int longitudMaxima = campoService.longitudMaximaRegistro(listaCampo);
    			logger.info("Longitud maxima:  "+longitudMaxima); 
    			int posicionCodEmpresa = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOM_COD_EMPRESA_F14C);
    			logger.info("posicion final codempresa:  "+posicionCodEmpresa);
    			int posicionAnioPresentacion = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOM_ANO_PRESENTACION_F14C);
    			logger.info("posicion final anio pres:  "+posicionAnioPresentacion);
    			int posicionMesPresentacion = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOM_MES_PRESENTACION_F14C);
    			logger.info("posicion final posicionMesPresentacion:  "+posicionMesPresentacion);
    			int posicionAnioInicioVigencia = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOM_ANIOINICIO_VIGE_F14C);
    			logger.info("posicion final posicionAnioInicioVigencia:  "+posicionAnioInicioVigencia);
    			int posicionAnioFinVigencia = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOM_ANIOFIN_VIGE_F14C);
    			logger.info("posicion final posicionAnioFinVigencia:  "+posicionAnioFinVigencia);
    			int posicionZonaBenef = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOM_IDZONA_BENEF_F14C);
    			logger.info("posicion final posicionZonaBenef:  "+posicionZonaBenef);
    			int posicionPersonalBenef = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOM_IDTIPO_PERSONAL_F14C);
    			logger.info("posicion final posicionPersonalBenef:  "+posicionPersonalBenef);
    			int posicionSede = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOM_NOMBRE_SEDE_F14C);
    			logger.info("posicion final posicionSede:  "+posicionSede);
    			int posicionNroTotalBenef = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOM_NRO_TOTAL_BENEF_F14C);
    			logger.info("posicion final posicionNroTotalBenef:  "+posicionNroTotalBenef);
    			int posicionNroItemTipoGestCosto = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOM_NRO_ITEM_TIPO_GESTION_F14C);
    			logger.info("posicion final posicionNroItemTipoGestCosto:  "+posicionNroItemTipoGestCosto);
    			int posicionIdTipoCostoGest = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOM_ID_TIPO_COSTO_GESTION_F14C);
    			logger.info("posicion final posicionIdTipoCostoGest:  "+posicionIdTipoCostoGest);
    			int posicionCantidad = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOM_CANTIDAD_F14C);
    			logger.info("posicion final posicionCantidad:  "+posicionCantidad);
    			int posicionCostoUnitario = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOM_COSTO_UNITARIO_F14C);
    			logger.info("posicion final posicionCostoUnitario:  "+posicionCostoUnitario);
    			int posicionCostoTotalZona = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOM_COSTO_TOTAL_ZONA_F14C);
    			logger.info("posicion final posicionCostoTotalZona:  "+posicionCostoTotalZona);
    			int posicionCostoTotalTipPer = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOM_COSTO_TOTAL_TIPO_PERSONA_F14C);
    			logger.info("posicion final posicionCostoTotalTipPer:  "+posicionCostoTotalTipPer);  			
    			
    			String sCurrentLine;
    			is=uploadPortletRequest.getFileAsStream("archivoTxt");
    			int BUFFER_SIZE = 8192;
    			BufferedReader br = new BufferedReader( new InputStreamReader(is),BUFFER_SIZE);
    			List<String> listaDetalleTxt= new ArrayList<String>();
    			sCurrentLine = br.readLine();
    			logger.info("sCurrentLine :  "+sCurrentLine.length());
    			while(sCurrentLine!=null){
    				logger.info("CONTADOR EN PANTALLA.");
    				cont++;
    				if( sCurrentLine.length()>0 ){
    					if( sCurrentLine.length() == longitudMaxima){
    						listaDetalleTxt.add(sCurrentLine);
    					}else{
    						cont++;
    						sMsg="ERROR";    
    					    fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
    								listaError, cont, FiseConstants.COD_ERROR_F12_220);//error datos incompletos
    					}
    				}else{
    					cont++;
    					sMsg="ERROR";     
    						fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
    							listaError, cont, FiseConstants.COD_ERROR_F12_230);//error archivo sin datos
    				}
    				sCurrentLine = br.readLine();
    				if( cont>24 ){
    					cont++;
    					sMsg="ERROR";    
    						fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
    							listaError, cont, FiseConstants.COD_ERROR_F14C_1460);//no debe pasar las 24 lineas 
    					break;
    				}
    			}
    			logger.info("TOTAL DE FILAS DEL TEXTO:  "+listaDetalleTxt.size()); 
    			
    			boolean process = false;
    			String key1 ="";
    			String key2="";
    			String key3="";
    			String key4="";
    			String codEmpresaRural="";//cod_empresa+aniopresentacion+mespresentacion+zonarual+sede
    			String codEmpresaProv="";//cod_empresa+aniopresentacion+mespresentacion+zonaProv+sede
    			String codEmpresaLima="";//cod_empresa+aniopresentacion+mespresentacion+zonalima+sede
    			
    			if(listaDetalleTxt.size()>15 && listaDetalleTxt.size()<=24){ 
    				process = true;
    				key1 = listaDetalleTxt.get(0).substring(0, posicionCodEmpresa).trim();
    				key2 = listaDetalleTxt.get(0).substring(posicionCodEmpresa, posicionAnioPresentacion).trim();
    				key3 = listaDetalleTxt.get(0).substring(posicionAnioPresentacion, posicionMesPresentacion).trim();
    				key4 = listaDetalleTxt.get(0).substring(posicionAnioFinVigencia, posicionSede).trim();
    				logger.info("Codigo key1:   "+key1); //codigo empresa
    				logger.info("Codigo key2:   "+key2); //anio presentacion 
    				logger.info("Codigo key3:   "+key3); //mes presentacion
    				logger.info("Codigo key4:   "+key4); //sede    			
    				for (String s : listaDetalleTxt) {
        				String codEmp = s.substring(0, posicionCodEmpresa).trim();
    					String anioPres = s.substring(posicionCodEmpresa, posicionAnioPresentacion).trim();
    					String mesPres = s.substring(posicionAnioPresentacion, posicionMesPresentacion) ;
    					String zonaBenef = s.substring(posicionSede, posicionZonaBenef).trim();
    					String sede = s.substring(posicionAnioFinVigencia, posicionSede).trim();
    					logger.info("codigo empresa:   "+codEmp); 
        				logger.info("anio pres:   "+anioPres); 
        				logger.info("mes pres:   "+mesPres); 
        				logger.info("sede:   "+sede);
        				logger.info("zona:   "+zonaBenef);
    					if(key1.equals(codEmp) && 
    							key2.equals(anioPres) && 
    							key3.equals(mesPres) &&
    							key4.equals(sede) &&
    							FiseConstants.ZONABENEF_RURAL_COD_STRING.equals(zonaBenef))
    					{
    						codEmpresaRural=key1+key2+key3+zonaBenef+key4;						
    					}else if(key1.equals(codEmp) && 
    							key2.equals(anioPres) && 
    							key3.equals(mesPres) &&
    							key4.equals(sede) &&							
    							FiseConstants.ZONABENEF_PROVINCIA_COD_STRING.equals(zonaBenef))
    					{
    						codEmpresaProv=key1+key2+key3+zonaBenef+key4;						
    					}else if(key1.equals(codEmp) && 
    							key2.equals(anioPres) && 
    							key3.equals(mesPres) &&
    							key4.equals(sede) &&							
    							FiseConstants.ZONABENEF_LIMA_COD_STRING.equals(zonaBenef))
    					{
    						codEmpresaLima=key1+key2+key3+zonaBenef+key4;						
    					}else{ 
    						process = false;
    						cont++;
    						sMsg="ERROR";    
    						fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
    								listaError, cont, FiseConstants.COD_ERROR_F14C_1470);
    						break;
    					}
        			}//fin del for  
        			
        			logger.info("Codigo muestra codEmpresaRural:   "+codEmpresaRural); 
        			logger.info("Codigo muestra codEmpresaProv:   "+codEmpresaProv); 
        			logger.info("Codigo muestra codEmpresaLima:   "+codEmpresaLima); 
    			}    			
    			//empesando asignar valores al bean
    			if(process){
    			  for (String s : listaDetalleTxt) {
    				String codEmp = s.substring(0, posicionCodEmpresa).trim();
  					String anioPres = s.substring(posicionCodEmpresa, posicionAnioPresentacion).trim();
  					String mesPres = s.substring(posicionAnioPresentacion, posicionMesPresentacion) ;
  					String zonaBenef = s.substring(posicionSede, posicionZonaBenef).trim();
  					String sede = s.substring(posicionAnioFinVigencia, posicionSede).trim(); 
  					
    				String codigoMuestraZonal =codEmp+anioPres+mesPres+zonaBenef+sede; 
    				logger.info("Codigo muestra zona para setear al bean: "+codigoMuestraZonal);
    				
    				String idTipoCostGestion =s.substring(posicionNroItemTipoGestCosto, posicionIdTipoCostoGest).trim();  
    			    String idTipoPersonal = s.substring(posicionIdTipoCostoGest, posicionPersonalBenef).trim();
    			   
    			    String codigoMuestraTipPersona =idTipoCostGestion+idTipoPersonal; 
    			    logger.info("Codigo muestra persona para setear al bean: "+codigoMuestraTipPersona);
    			    
    			    String nroTotalBenef = s.substring(posicionZonaBenef, posicionNroTotalBenef).trim();
    			    logger.info("nroTotalBenef: "+nroTotalBenef);
    			    String cantidad = s.substring(posicionPersonalBenef, posicionCantidad).trim();
    			    logger.info("cantidad: "+cantidad);
    			    String costo = s.substring(posicionCantidad, posicionCostoUnitario).trim();
    			    logger.info("costo: "+costo);
    			    String costoProemdio = s.substring(posicionCostoTotalZona, posicionCostoTotalTipPer).trim();
    			    logger.info("costoProemdio: "+costoProemdio);
    			    String costoPromedioRural = listaDetalleTxt.get(0).substring(posicionCostoTotalZona, posicionCostoTotalTipPer).trim();
    			    logger.info("costoPromedioRural: "+costoPromedioRural);
    			    String costoPromedioProv = listaDetalleTxt.get(8).substring(posicionCostoTotalZona, posicionCostoTotalTipPer).trim();
    			    logger.info("costoPromedioProv: "+costoPromedioProv);
    			    
    			    String costoPromedioLima  ="0.0";
    			    if(FiseConstants.COD_EMPRESA_EDELNOR.equals(codigoEmpresa) || 
							FiseConstants.COD_EMPRESA_LUZ_SUR.equals(codigoEmpresa))
					{
    			        costoPromedioLima = listaDetalleTxt.get(16).substring(posicionCostoTotalZona, posicionCostoTotalTipPer).trim();
        			   
					}
    			    logger.info("costoPromedioLima: "+costoPromedioLima);
    				
    			    /**seteando valores al bean de la cabecera**/
    				bean.setCodEmpresa(key1); 
    				bean.setAnioPres(key2);
    				bean.setMesPres(key3);
    				//bean.setAnoIniVigencia(key2);
    				//bean.setAnoFinVigencia(key2);
    				bean.setEtapa(etapa); 
    				bean.setNombreSede(key4); 
    			    /**ZONA RURAL*/
    			    if(codEmpresaRural.equals(codigoMuestraZonal)){ 
    			    	if(FiseConstants.COSTODIERCTO_COORD_F14C.equals(codigoMuestraTipPersona)){ 
    			    		bean.setNumRural(nroTotalBenef); //cabecera
    			    		bean.setCanDRCoord(cantidad);
    			    		bean.setCostDRCoord(costo);
    			    		bean.setCostoPromRural(costoPromedioRural);//cabecera
    			    	}else if(FiseConstants.COSTODIRECTO_SUPE_F14C.equals(codigoMuestraTipPersona)){
    			    		//bean.setNumRural(nroTotalBenef); 
    			    		bean.setCanDRSupe(cantidad);
    			    		bean.setCostDRSupe(costo);
    			    		//bean.setCostoPromRural(costoPromedioRural);
    			    	}else if(FiseConstants.COSTODIRECTO_GEST_F14C.equals(codigoMuestraTipPersona)){
    			    		//bean.setNumRural(nroTotalBenef); 
    			    		bean.setCanDRGest(cantidad);
    			    		bean.setCostDRGest(costo);
    			    		//bean.setCostoPromRural(costoPromedioRural);
    			    	}else if(FiseConstants.COSTODIRECTO_ASIST_F14C.equals(codigoMuestraTipPersona)){
    			    		//bean.setNumRural(nroTotalBenef); 
    			    		bean.setCanDRAsist(cantidad);
    			    		bean.setCostDRAsist(costo);
    			    		//bean.setCostoPromRural(costoPromedioRural);
    			    	}else if(FiseConstants.COSTOINDIRECTO_COORD_F14C.equals(codigoMuestraTipPersona)){
    			    		//bean.setNumRural(nroTotalBenef); 
    			    		bean.setCanIRCoord(cantidad);
    			    		bean.setCostIRCoord(costo);
    			    		//bean.setCostoPromRural(costoPromedioRural);
    			    	}else if(FiseConstants.COSTOINDIRECTO_SUPE_F14C.equals(codigoMuestraTipPersona)){
    			    		//bean.setNumRural(nroTotalBenef); 
    			    		bean.setCanIRSupe(cantidad);
    			    		bean.setCostIRSupe(costo);
    			    		//bean.setCostoPromRural(costoPromedioRural);
    			    	}else if(FiseConstants.COSTOINDIRECTO_GEST_F14C.equals(codigoMuestraTipPersona)){
    			    		//bean.setNumRural(nroTotalBenef); 
    			    		bean.setCanIRGest(cantidad);
    			    		bean.setCostIRGest(costo);
    			    		//bean.setCostoPromRural(costoPromedioRural);
    			    	}else if(FiseConstants.COSTOINDIRECTO_ASIST_F14C.equals(codigoMuestraTipPersona)){
    			    		//bean.setNumRural(nroTotalBenef); 
    			    		bean.setCanIRAsist(cantidad);
    			    		bean.setCostIRAsist(costo);
    			    		//bean.setCostoPromRural(costoPromedioRural);
    			    	}else{
    			    	   //no coinciden ningun tipo de personal
    			    		cont++;
    			    		sMsg="ERROR";    
    						fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
    								listaError, cont, FiseConstants.COD_ERROR_F14C_1480);
    						break;
    			    	}   			    	
    			    }
    			    /**PROVINCIAS*/
    			    else if(codEmpresaProv.equals(codigoMuestraZonal)){
    			    	if(FiseConstants.COSTODIERCTO_COORD_F14C.equals(codigoMuestraTipPersona)){ 
    			    		bean.setNumUrbProv(nroTotalBenef); //cabecera
    			    		bean.setCanDPCoord(cantidad);
    			    		bean.setCostDPCoord(costo);
    			    		bean.setCostoPromUrbProv(costoPromedioProv);//cabecera
    			    	}else if(FiseConstants.COSTODIRECTO_SUPE_F14C.equals(codigoMuestraTipPersona)){
    			    		//bean.setNumRural(nroTotalBenef); 
    			    		bean.setCanDPSupe(cantidad);
    			    		bean.setCostDPSupe(costo);
    			    		//bean.setCostoPromRural(costoPromedioRural);
    			    	}else if(FiseConstants.COSTODIRECTO_GEST_F14C.equals(codigoMuestraTipPersona)){
    			    		//bean.setNumRural(nroTotalBenef); 
    			    		bean.setCanDPGest(cantidad);
    			    		bean.setCostDPGest(costo);
    			    		//bean.setCostoPromRural(costoPromedioRural);
    			    	}else if(FiseConstants.COSTODIRECTO_ASIST_F14C.equals(codigoMuestraTipPersona)){
    			    		//bean.setNumRural(nroTotalBenef); 
    			    		bean.setCanDPAsist(cantidad);
    			    		bean.setCostDPAsist(costo);
    			    		//bean.setCostoPromRural(costoPromedioRural);
    			    	}else if(FiseConstants.COSTOINDIRECTO_COORD_F14C.equals(codigoMuestraTipPersona)){
    			    		//bean.setNumRural(nroTotalBenef); 
    			    		bean.setCanIPCoord(cantidad);
    			    		bean.setCostIPCoord(costo);
    			    		//bean.setCostoPromRural(costoPromedioRural);
    			    	}else if(FiseConstants.COSTOINDIRECTO_SUPE_F14C.equals(codigoMuestraTipPersona)){
    			    		//bean.setNumRural(nroTotalBenef); 
    			    		bean.setCanIPSupe(cantidad);
    			    		bean.setCostIPSupe(costo);
    			    		//bean.setCostoPromRural(costoPromedioRural);
    			    	}else if(FiseConstants.COSTOINDIRECTO_GEST_F14C.equals(codigoMuestraTipPersona)){
    			    		//bean.setNumRural(nroTotalBenef); 
    			    		bean.setCanIPGest(cantidad);
    			    		bean.setCostIPGest(costo);
    			    		//bean.setCostoPromRural(costoPromedioRural);
    			    	}else if(FiseConstants.COSTOINDIRECTO_ASIST_F14C.equals(codigoMuestraTipPersona)){
    			    		//bean.setNumRural(nroTotalBenef); 
    			    		bean.setCanIPAsist(cantidad);
    			    		bean.setCostIPAsist(costo);
    			    		//bean.setCostoPromRural(costoPromedioRural);
    			    	}else{
    			    	   //no coinciden ningun tipo de personal
    			    		cont++;
    			    		sMsg="ERROR";    
    						fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
    								listaError, cont, FiseConstants.COD_ERROR_F14C_1490);
    						break;
    			    	}    				    	
    			    }
    			    /*****LIMA******/
    			    else if(codEmpresaLima.equals(codigoMuestraZonal)){
    			    	if(FiseConstants.COSTODIERCTO_COORD_F14C.equals(codigoMuestraTipPersona)){ 
    			    		bean.setNumUrbLima(nroTotalBenef); //cabecera
    			    		bean.setCanDLCoord(cantidad);
    			    		bean.setCostDLCoord(costo);
    			    		bean.setCostoPromUrbLima(costoPromedioLima);//cabecera
    			    	}else if(FiseConstants.COSTODIRECTO_SUPE_F14C.equals(codigoMuestraTipPersona)){
    			    		//bean.setNumRural(nroTotalBenef); 
    			    		bean.setCanDLSupe(cantidad);
    			    		bean.setCostDLSupe(costo);
    			    		//bean.setCostoPromRural(costoPromedioRural);
    			    	}else if(FiseConstants.COSTODIRECTO_GEST_F14C.equals(codigoMuestraTipPersona)){
    			    		//bean.setNumRural(nroTotalBenef); 
    			    		bean.setCanDLGest(cantidad);
    			    		bean.setCostDLGest(costo);
    			    		//bean.setCostoPromRural(costoPromedioRural);
    			    	}else if(FiseConstants.COSTODIRECTO_ASIST_F14C.equals(codigoMuestraTipPersona)){
    			    		//bean.setNumRural(nroTotalBenef); 
    			    		bean.setCanDLAsist(cantidad);
    			    		bean.setCostDLAsist(costo);
    			    		//bean.setCostoPromRural(costoPromedioRural);
    			    	}else if(FiseConstants.COSTOINDIRECTO_COORD_F14C.equals(codigoMuestraTipPersona)){
    			    		//bean.setNumRural(nroTotalBenef); 
    			    		bean.setCanILCoord(cantidad);
    			    		bean.setCostILCoord(costo);
    			    		//bean.setCostoPromRural(costoPromedioRural);
    			    	}else if(FiseConstants.COSTOINDIRECTO_SUPE_F14C.equals(codigoMuestraTipPersona)){
    			    		//bean.setNumRural(nroTotalBenef); 
    			    		bean.setCanILSupe(cantidad);
    			    		bean.setCostILSupe(costo);
    			    		//bean.setCostoPromRural(costoPromedioRural);
    			    	}else if(FiseConstants.COSTOINDIRECTO_GEST_F14C.equals(codigoMuestraTipPersona)){
    			    		//bean.setNumRural(nroTotalBenef); 
    			    		bean.setCanILGest(cantidad);
    			    		bean.setCostILGest(costo);
    			    		//bean.setCostoPromRural(costoPromedioRural);
    			    	}else if(FiseConstants.COSTOINDIRECTO_ASIST_F14C.equals(codigoMuestraTipPersona)){
    			    		//bean.setNumRural(nroTotalBenef); 
    			    		bean.setCanILAsist(cantidad);
    			    		bean.setCostILAsist(costo);
    			    		//bean.setCostoPromRural(costoPromedioRural);
    			    	}else{
    			    	   //no coinciden ningun tipo de personal
    			    		cont++;
    			    		sMsg="ERROR";    
    						fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
    								listaError, cont, FiseConstants.COD_ERROR_F14C_1500);
    						break;
    			    	}   			    	
    			    }else{
    			    	cont++;
    			    	sMsg="ERROR";    
						fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
								listaError, cont, FiseConstants.COD_ERROR_F14C_1510);
						break;
    			    }    			    
    			  }//fin del for del recorrido de la lista seteando valores    			  
    			}//fin del if process = true
    			
    		   /*****validaciones de consistencia***/
    			if(process && FiseConstants.BLANCO.equals(sMsg)){
    				/**CABECERA**/
    				if(!FormatoUtil.validarCampoLongTxt(bean.getAnioPres())){    				
    					cont++;
    					sMsg="ERROR";    
    					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
    							listaError, cont, FiseConstants.COD_ERROR_F12_60);
    				}
    				if(!FormatoUtil.validarCampoLongTxt(bean.getMesPres())){    				
    					cont++;
    					sMsg="ERROR";    
    					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
    							listaError, cont, FiseConstants.COD_ERROR_F12_80);
    				}
    				if(!FormatoUtil.validarCampoLongTxt(bean.getNumRural())){    				
    					cont++;
    					sMsg="ERROR";    
    					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
    							listaError, cont, FiseConstants.COD_ERROR_F14C_390);
    				}
    				if(!FormatoUtil.validarCampoLongTxt(bean.getNumUrbProv())){    				
    					cont++;
    					sMsg="ERROR";    
    					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
    							listaError, cont, FiseConstants.COD_ERROR_F14C_410);
    				}
    				/**Habilita Lima*/
    				if(FiseConstants.COD_EMPRESA_EDELNOR.equals(codigoEmpresa) || 
							FiseConstants.COD_EMPRESA_LUZ_SUR.equals(codigoEmpresa))
					{
    					if(!FormatoUtil.validarCampoLongTxt(bean.getNumUrbLima())){    				
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_430);
        				}
    					if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostoPromUrbLima()) ){    				
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_490);
        				}
					} //fin habilita lima   				
    				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCostoPromRural()) ){    				
    					cont++;
    					sMsg="ERROR";    
    					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
    							listaError, cont, FiseConstants.COD_ERROR_F14C_450);
    				}
    				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostoPromUrbProv()) ){    				
    					cont++;
    					sMsg="ERROR";    
    					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
    							listaError, cont, FiseConstants.COD_ERROR_F14C_470);
    				}       				
    				
    				///VERIFICACION DE COSTO DIRECTO O INDIRECTO
    				if(FiseConstants.COSTO_DIRECTO_F14C.equals(flagCosto)){
    					/*****RURAL*****/     
    					if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDRCoord())){    					
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_510);
        				}
        				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDRSupe())){   				
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_590);
        				}
        				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDRGest())){    				
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_670);
        				}
        				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDRAsist())){    					
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_750);
        				}
        				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDRCoord()) ){    				
        					cont++;
        					sMsg="ERROR";    
        					 fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_530);
        				}
        				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDRSupe()) ){    				
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_610);
        				}
        				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDRGest()) ){    					
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_690);
        				}
        				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDRAsist()) ){    					
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_770);
        				}
        				/*****PROVINCIA*****/    
        				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDPCoord())){    				
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_830);
        				}
        				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDPSupe())){    				
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_910);
        				}
        				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDPGest())){    				
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_990);
        				}
        				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDPAsist())){    					
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_1070);
        				}
        				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDPCoord()) ){    			
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_850);
        				}
        				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDPSupe()) ){    				
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_930);
        				}
        				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDPGest()) ){    			
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_1010);
        				}
        				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDPAsist()) ){    			
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_1090);
        				}
        				/**Habilita LIMA*/
        				if(FiseConstants.COD_EMPRESA_EDELNOR.equals(codigoEmpresa) || 
    							FiseConstants.COD_EMPRESA_LUZ_SUR.equals(codigoEmpresa))
    					{
        					if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDLCoord())){    		
            					cont++;
            					sMsg="ERROR";    
            					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
            							listaError, cont, FiseConstants.COD_ERROR_F14C_1150);
            				}
            				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDLSupe())){    				
            					cont++;
            					sMsg="ERROR";    
            					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
            							listaError, cont, FiseConstants.COD_ERROR_F14C_1230);
            				}
            				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDLGest())){    			
            					cont++;
            					sMsg="ERROR";    
            					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
            							listaError, cont, FiseConstants.COD_ERROR_F14C_1310);
            				}
            				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDLAsist())){    			
            					cont++;
            					sMsg="ERROR";    
            					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
            							listaError, cont, FiseConstants.COD_ERROR_F14C_1390);
            				}
            				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDLCoord()) ){    					
            					cont++;
            					sMsg="ERROR";    
            					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
            							listaError, cont, FiseConstants.COD_ERROR_F14C_1170);
            				}
            				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDLSupe()) ){    				
            					cont++;
            					sMsg="ERROR";    
            					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
            							listaError, cont, FiseConstants.COD_ERROR_F14C_1250);
            				}
            				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDLGest()) ){    				
            					cont++;
            					sMsg="ERROR";    
            					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
            							listaError, cont, FiseConstants.COD_ERROR_F14C_1330);
            				}
            				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDLAsist()) ){    				
            					cont++;
            					sMsg="ERROR";    
            					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores,
            							listaError, cont, FiseConstants.COD_ERROR_F14C_1410);
            				}       					
    					}       				
    				}else if(FiseConstants.COSTO_INDIRECTO_F14C.equals(flagCosto)){
    					/*****RURAL*****/     
    					if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanIRCoord())){    					
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_550);
        				}
        				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanIRSupe())){    					
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_630);
        				}
        				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanIRGest())){    					
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_710);
        				}
        				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanIRAsist())){    					
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_790);
        				}  
        				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostIRCoord()) ){    				
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_570);
        				}
        				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostIRSupe()) ){    					
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_650);
        				}
        				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostIRGest()) ){    				
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_730);
        				}
        				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostIRAsist()) ){    					
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_810);
        				}    					
    					/*****PROVINCIA*****/ 
        				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanIPCoord())){    				
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_870);
        				}
        				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanIPSupe())){    				
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_950);
        				}
        				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanIPGest())){    			
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_1030);
        				}
        				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanIPAsist())){    			
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_1530);
        				}    	
        				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostIPCoord()) ){    				
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_890);
        				}
        				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostIPSupe()) ){    		
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_970);
        				}
        				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostIPGest()) ){    			
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_1050);
        				}
        				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostIPAsist()) ){    			
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_1130);
        				}
        				/**Habilita LIMA*/
        				if(FiseConstants.COD_EMPRESA_EDELNOR.equals(codigoEmpresa) || 
    							FiseConstants.COD_EMPRESA_LUZ_SUR.equals(codigoEmpresa))
    					{
        					if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanILCoord())){    				
            					cont++;
            					sMsg="ERROR";    
            					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
            							listaError, cont, FiseConstants.COD_ERROR_F14C_1190);
            				}
            				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanILSupe())){    			
            					cont++;
            					sMsg="ERROR";    
            					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
            							listaError, cont, FiseConstants.COD_ERROR_F14C_1270);
            				}
            				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanILGest())){    			
            					cont++;
            					sMsg="ERROR";    
            					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
            							listaError, cont, FiseConstants.COD_ERROR_F14C_1350);
            				}
            				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanILAsist())){    				
            					cont++;
            					sMsg="ERROR";    
            					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
            							listaError, cont, FiseConstants.COD_ERROR_F14C_1430);
            				}	
            				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostILCoord()) ){    				
            					cont++;
            					sMsg="ERROR";    
            					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
            							listaError, cont, FiseConstants.COD_ERROR_F14C_1210);
            				}
            				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostILSupe()) ){    			
            					cont++;
            					sMsg="ERROR";    
            					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
            							listaError, cont, FiseConstants.COD_ERROR_F14C_1290);
            				}
            				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostILGest()) ){    				
            					cont++;
            					sMsg="ERROR";    
            					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
            							listaError, cont, FiseConstants.COD_ERROR_F14C_1370);
            				}
            				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostILAsist()) ){    				
            					cont++;
            					sMsg="ERROR";    
            					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
            							listaError, cont, FiseConstants.COD_ERROR_F14C_1450);
            				}         					
    					}     					
    				}else{
    					/************COSTO DIRECTO E INDIRECTO***********/
    					/*****RURAL*****/     				
        				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDRCoord())){    					
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_510);
        				}
        				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDRSupe())){   				
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_590);
        				}
        				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDRGest())){    				
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_670);
        				}
        				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDRAsist())){    					
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_750);
        				}
        				
        				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanIRCoord())){    					
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_550);
        				}
        				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanIRSupe())){    					
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_630);
        				}
        				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanIRGest())){    					
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_710);
        				}
        				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanIRAsist())){    					
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_790);
        				}    				

        				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDRCoord()) ){    				
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_530);
        				}
        				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDRSupe()) ){    				
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_610);
        				}
        				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDRGest()) ){    					
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_690);
        				}
        				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDRAsist()) ){    					
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_770);
        				}
        				
        				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostIRCoord()) ){    				
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_570);
        				}
        				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostIRSupe()) ){    					
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_650);
        				}
        				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostIRGest()) ){    				
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_730);
        				}
        				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostIRAsist()) ){    					
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_810);
        				}
        				
        				/*******PROVINCIAS******/
        				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDPCoord())){    				
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_830);
        				}
        				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDPSupe())){    				
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_910);
        				}
        				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDPGest())){    				
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_990);
        				}
        				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDPAsist())){    					
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_1070);
        				}
        				
        				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanIPCoord())){    				
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_870);
        				}
        				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanIPSupe())){    				
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_950);
        				}
        				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanIPGest())){    			
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_1030);
        				}
        				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanIPAsist())){    			
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_1530);
        				}    				

        				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDPCoord()) ){    			
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_850);
        				}
        				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDPSupe()) ){    				
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_930);
        				}
        				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDPGest()) ){    			
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_1010);
        				}
        				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDPAsist()) ){    			
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_1090);
        				}
        				
        				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostIPCoord()) ){    				
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_890);
        				}
        				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostIPSupe()) ){    		
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_970);
        				}
        				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostIPGest()) ){    			
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_1050);
        				}
        				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostIPAsist()) ){    			
        					cont++;
        					sMsg="ERROR";    
        					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
        							listaError, cont, FiseConstants.COD_ERROR_F14C_1130);
        				}
        				/**Habilita LIMA*/
        				if(FiseConstants.COD_EMPRESA_EDELNOR.equals(codigoEmpresa) || 
    							FiseConstants.COD_EMPRESA_LUZ_SUR.equals(codigoEmpresa))
    					{
        					/**LIMA***/
            				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDLCoord())){    		
            					cont++;
            					sMsg="ERROR";    
            					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
            							listaError, cont, FiseConstants.COD_ERROR_F14C_1150);
            				}
            				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDLSupe())){    				
            					cont++;
            					sMsg="ERROR";    
            					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
            							listaError, cont, FiseConstants.COD_ERROR_F14C_1230);
            				}
            				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDLGest())){    			
            					cont++;
            					sMsg="ERROR";    
            					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
            							listaError, cont, FiseConstants.COD_ERROR_F14C_1310);
            				}
            				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanDLAsist())){    			
            					cont++;
            					sMsg="ERROR";    
            					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
            							listaError, cont, FiseConstants.COD_ERROR_F14C_1390);
            				}
            				
            				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanILCoord())){    				
            					cont++;
            					sMsg="ERROR";    
            					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
            							listaError, cont, FiseConstants.COD_ERROR_F14C_1190);
            				}
            				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanILSupe())){    			
            					cont++;
            					sMsg="ERROR";    
            					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
            							listaError, cont, FiseConstants.COD_ERROR_F14C_1270);
            				}
            				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanILGest())){    			
            					cont++;
            					sMsg="ERROR";    
            					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
            							listaError, cont, FiseConstants.COD_ERROR_F14C_1350);
            				}
            				if(!FormatoUtil.validarCampoBigDecimalTxt(bean.getCanILAsist())){    				
            					cont++;
            					sMsg="ERROR";    
            					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
            							listaError, cont, FiseConstants.COD_ERROR_F14C_1430);
            				}        				

            				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDLCoord()) ){    					
            					cont++;
            					sMsg="ERROR";    
            					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
            							listaError, cont, FiseConstants.COD_ERROR_F14C_1170);
            				}
            				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDLSupe()) ){    				
            					cont++;
            					sMsg="ERROR";    
            					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
            							listaError, cont, FiseConstants.COD_ERROR_F14C_1250);
            				}
            				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDLGest()) ){    				
            					cont++;
            					sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
            							listaError, cont, FiseConstants.COD_ERROR_F14C_1330);
            				}
            				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostDLAsist()) ){    				
            					cont++;
            					sMsg="ERROR";    
            					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores,
            							listaError, cont, FiseConstants.COD_ERROR_F14C_1410);
            				}
            				
            				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostILCoord()) ){    				
            					cont++;
            					sMsg="ERROR";    
            					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
            							listaError, cont, FiseConstants.COD_ERROR_F14C_1210);
            				}
            				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostILSupe()) ){    			
            					cont++;
            					sMsg="ERROR";    
            					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
            							listaError, cont, FiseConstants.COD_ERROR_F14C_1290);
            				}
            				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostILGest()) ){    				
            					cont++;
            					sMsg="ERROR";    
            					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
            							listaError, cont, FiseConstants.COD_ERROR_F14C_1370);
            				}
            				if( !FormatoUtil.validarCampoBigDecimalTxt(bean.getCostILAsist()) ){    				
            					cont++;
            					sMsg="ERROR";    
            					fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
            							listaError, cont, FiseConstants.COD_ERROR_F14C_1450);
            				}   					
    					}        					
    				}//fin del else de ambos   							
    			}  //fin de validacion 			
				
				logger.info("Valor de del mesaje pasando la validacion:  "+sMsg); 
				if(FiseConstants.BLANCO.equals(sMsg) ){	
					logger.info("Entro a verificar cero errores de validacion"); 
					bean.setUsuario(user.getLogin());
					bean.setTerminal(user.getLoginIP());						
					bean.setNombreText(archivo.getTitle());	
					logger.info("Cod empresa del for: "+codigoEmpresa);
					logger.info("anio pres del for: "+anioPresentacion);
					int mesForm = Integer.valueOf(mesPresentacion);
					logger.info("mes pres del for: "+mesForm);
					logger.info("anio inicio del for: "+anioIniVigencia);
					logger.info("anio fin del for: "+anioFinVigencia);
					
					logger.info("Cod empresa del bean: "+bean.getCodEmpresa());
					logger.info("anio pres del bean: "+bean.getAnioPres());					
					logger.info("anio inicio del bean: "+bean.getAnioPres());
					logger.info("anio fin del bean: "+bean.getAnioPres());
					int mesBean = Integer.valueOf(bean.getMesPres());						
					logger.info("mes pres del bean: "+mesBean);
					
					if(codigoEmpresa.equals(bean.getCodEmpresa()) && 
							anioPresentacion.equals(bean.getAnioPres()) &&	
							mesForm==mesBean)
					{
						logger.info("Entro a verificar datos si coincide la Pk"); 
						if( FiseConstants.FLAG_CARGATXT_FORMULARIONUEVO.equals(flagCarga) ){
							bean.setCodEmpresa(codigoEmpresa);
							bean.setAnioPres(anioPresentacion);
							bean.setMesPres(mesPresentacion);
							bean.setAnoIniVigencia(anioIniVigencia);
							bean.setAnoFinVigencia(anioFinVigencia); 
							bean.setEtapa(etapa); 
							bean.setFlagCosto(flagCosto); 
							logger.info("Entro agrabar nuevo registro"); 
							valor=formato14CGartService.insertarDatosFormato14C(bean);		
							logger.info("valor del  agrabar nuevo registro: "+valor); 
						}else if( FiseConstants.FLAG_CARGATXT_FORMULARIOMODIFICACION.equals(flagCarga) ){
							bean.setCodEmpresa(codigoEmpresa);
							bean.setAnioPres(anioPresentacion);
							bean.setMesPres(mesPresentacion);
							bean.setAnoIniVigencia(anioIniVigencia);
							bean.setAnoFinVigencia(anioFinVigencia); 
							bean.setEtapa(etapa); 
							bean.setFlagCosto(flagCosto); 
							logger.info("Entro actualizar nuevo registro"); 
							valor =formato14CGartService.actualizarDatosFormato14C(bean);
							logger.info("valor del  actualizar nuevo registro: "+valor); 
						}
					}else{
						logger.info("Entro a verificar datos la Pk no coincide--"); 
						cont++;
						sMsg="ERROR";    
						sMsg = sMsg +fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
								listaError, cont, FiseConstants.COD_ERROR_F12_210);
					}
				}			
    			is.close();
    			if("2".equals(valor)){ 
    				cont++;
    				sMsg="ERROR";
    				fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
    						listaError, cont, FiseConstants.COD_ERROR_F14C_1540);//error codigo ya esta registrado 	
    			}else if("0".equals(valor)){
    				cont++;
    				sMsg="ERROR";
    				fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
    						listaError, cont, FiseConstants.COD_ERROR_F14C_1550);//error al grabar		
    			}    			
    		}else{
    			//no hay campos o error al consultar a la tabla de campos
    			logger.info("No hay los campos configurados en la tabla campos--"); 
				cont++;
				sMsg="ERROR";    
				sMsg = sMsg +fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, 
						listaError, cont, FiseConstants.COD_ERROR_F14C_1520);
		   }    		
    	}catch (Exception e) {	  			  
    		String error = e.getMessage();
    		sMsg = sMsg+error;	        	
    		logger.info(error);
    		cont++;
    		MensajeErrorBean errorBean = new MensajeErrorBean();
    		errorBean.setId(cont);
    		errorBean.setDescripcion(error);
    		listaError.add(errorBean);  		  			   
    	}    	
    	formatoMensaje.setFormato14CBean(bean);
    	formatoMensaje.setValor(valor); 
    	formatoMensaje.setMensajeError(sMsg);
    	if(listaError.size()>0){
    		formatoMensaje.setListaMensajeError(listaError);	
    	}   	
    	return formatoMensaje;
    }
    
    @ResourceMapping("reporteActaEnvioView")
	public void reporteActaEnvio(ResourceRequest request,ResourceResponse response, @ModelAttribute("formato14CBean")Formato14CBean f) {
		try {
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
			HttpSession session = httpRequest.getSession();
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			JSONArray jsonArray = new JSONArray();	
			
			String tipoFormato = FiseConstants.TIPO_FORMATO_ACTAENVIO;
			String tipoArchivo = FiseConstants.FORMATO_EXPORT_PDF;
			
			CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14C);
			String descripcionFormato = "";
			if( tabla!=null ){
				descripcionFormato = tabla.getDescripcionTabla();
			}
			/*String codEmpresa = request.getParameter("codEmpresa").trim();
			String periodoEnvio = request.getParameter("periodoEnvio").trim();
			String anoPresentacion = "";
			String mesPresentacion = "";
			String anoInicioVigencia = "";
			String anoFinVigencia = "";
			String etapa = "";
			
			if( periodoEnvio.length()>6 ){
				anoPresentacion = periodoEnvio.substring(0, 4);
				mesPresentacion = periodoEnvio.substring(4, 6);
				etapa = periodoEnvio.substring(6, periodoEnvio.length());
			}
			
			anoInicioVigencia = request.getParameter("anoInicioVigencia");
			anoFinVigencia = request.getParameter("anoFinVigencia");*/
			    
			String nombreReporte = "actaEnvio";
		    String nombreArchivo = nombreReporte;

		    if( f.getPeriodoEnvio().length()>6 ){
				f.setAnioPres(f.getPeriodoEnvio().substring(0, 4));
				f.setMesPres(f.getPeriodoEnvio().substring(4, 6));
				f.setEtapa(f.getPeriodoEnvio().substring(6, f.getPeriodoEnvio().length()));
				
				if(FormatoUtil.isBlank(f.getAnoIniVigencia()) && FormatoUtil.isBlank(f.getAnoFinVigencia())){
					logger.info("Tamanio de la lista periodo al envio definitivo: "+listaPeriodoEnvio.size()); 
					for (FisePeriodoEnvio p : listaPeriodoEnvio) {
						if(f.getPeriodoEnvio().equals(p.getCodigoItem()) ){					
							f.setAnoIniVigencia(p.getAnioInicioVig());
							f.setAnoIniVigencia(p.getAnioFinVig());
							break;
						}
					}
				}	
			}	
		    FiseFormato14CC formato = formato14CGartService.obtenerFiseFormato14CC(f);
			
			if( formato!=null ){
				Map<String, Object> mapa = new HashMap<String, Object>();
				mapa.put(FiseConstants.PARAM_IMG_LOGOTIPO, session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg"));
				mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
				mapa.put(FiseConstants.PARAM_USUARIO, themeDisplay.getUser().getLogin());
				mapa.put(FiseConstants.PARAM_NOMBRE_FORMATO, descripcionFormato);
				mapa.put(FiseConstants.PARAM_FECHA_ENVIO, formato.getFechaEnvioDefinitivo());
				mapa.put(FiseConstants.PARAM_NRO_OBSERVACIONES, (listaObservaciones!=null && !listaObservaciones.isEmpty())?listaObservaciones.size():0);
				mapa.put(FiseConstants.PARAM_MSG_OBSERVACIONES, (listaObservaciones!=null && !listaObservaciones.isEmpty())?FiseConstants.MSG_OBSERVACION_REPORTE_LLENO:FiseConstants.MSG_OBSERVACION_REPORTE_VACIO);
				mapa.put(FiseConstants.PARAM_ANO_INICIO_VIGENCIA, formato.getId().getAnoInicioVigencia());
				mapa.put(FiseConstants.PARAM_ANO_FIN_VIGENCIA, formato.getId().getAnoFinVigencia());
				mapa.put(FiseConstants.PARAM_FECHA_REGISTRO, formato.getFechaCreacion());
				mapa.put(FiseConstants.PARAM_USUARIO_REGISTRO, formato.getUsuarioCreacion());
				String dirCheckedImage = session.getServletContext().getRealPath("/reports/checked.jpg");
				String dirUncheckedImage = session.getServletContext().getRealPath("/reports/unchecked.jpg");
				mapa.put(FiseConstants.PARAM_IMG_CHECKED, dirCheckedImage);
				mapa.put(FiseConstants.PARAM_IMG_UNCHECKED, dirUncheckedImage);
				boolean cumplePlazo = false;
				cumplePlazo = commonService.fechaEnvioCumplePlazo(
						FiseConstants.TIPO_FORMATO_14C, 
						formato.getId().getCodEmpresa(), 
						formato.getId().getAnoPresentacion(), 
						formato.getId().getMesPresentacion(), 
						formato.getId().getEtapa(), 
						FechaUtil.fecha_DD_MM_YYYY(formato.getFechaEnvioDefinitivo()));
				if( cumplePlazo ){
					mapa.put(FiseConstants.PARAM_CHECKED_CUMPLEPLAZO, dirCheckedImage);
				}else{
					mapa.put(FiseConstants.PARAM_CHECKED_CUMPLEPLAZO, dirUncheckedImage);
				}
				if( listaObservaciones!=null && !listaObservaciones.isEmpty() ){
					mapa.put(FiseConstants.PARAM_CHECKED_OBSERVACION, dirCheckedImage);
				}else{
					mapa.put(FiseConstants.PARAM_CHECKED_OBSERVACION, dirUncheckedImage);
				}
				mapa.put(FiseConstants.PARAM_DESC_EMPRESA, mapaEmpresa.get(formato.getId().getCodEmpresa()));
				mapa.put(FiseConstants.PARAM_ANO_PRESENTACION, formato.getId().getAnoPresentacion());
				mapa.put(FiseConstants.PARAM_DESC_MES_PRESENTACION, fiseUtil.getMapaMeses().get(formato.getId().getMesPresentacion()));
				mapa.put(FiseConstants.PARAM_ETAPA, formato.getId().getEtapa());
				
				session.setAttribute("mapa", mapa);
			}
			session.setAttribute("nombreReporte",nombreReporte);
		    session.setAttribute("nombreArchivo",nombreArchivo);
			session.setAttribute("tipoFormato",tipoFormato);
			session.setAttribute("tipoArchivo",tipoArchivo);
				
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
