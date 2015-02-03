package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.Formato14BCBean;
import gob.osinergmin.fise.bean.Formato14BMensajeBean;
import gob.osinergmin.fise.bean.Formato14Generic;
import gob.osinergmin.fise.bean.MensajeErrorBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.CfgCampo;
import gob.osinergmin.fise.domain.CfgTabla;
import gob.osinergmin.fise.domain.FiseFormato14BC;
import gob.osinergmin.fise.domain.FiseFormato14BCPK;
import gob.osinergmin.fise.domain.FiseFormato14BD;
import gob.osinergmin.fise.domain.FiseFormato14BDOb;
import gob.osinergmin.fise.domain.FisePeriodoEnvio;
import gob.osinergmin.fise.gart.json.Formato14BGartJSON;
import gob.osinergmin.fise.gart.jsp.FileEntryJSP;
import gob.osinergmin.fise.gart.service.CfgCampoGartService;
import gob.osinergmin.fise.gart.service.CfgTablaGartService;
import gob.osinergmin.fise.gart.service.CommonGartService;
import gob.osinergmin.fise.gart.service.FisePeriodoEnvioGartService;
import gob.osinergmin.fise.gart.service.Formato14BGartService;
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
import java.util.Set;

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

@Controller("formato14BGartController")
@RequestMapping("VIEW")
public class Formato14BGartController {
	
private static final Logger logger = Logger.getLogger(Formato14BGartController.class);
	
	@Autowired
	@Qualifier("fiseUtil")
	FiseUtil fiseUtil;
	
	@Autowired
	@Qualifier("formato14BGartServiceImpl")
	Formato14BGartService formato14Service;
	@Autowired
	@Qualifier("fisePeriodoEnvioGartServiceImpl")
	FisePeriodoEnvioGartService periodoService;
	@Autowired
	@Qualifier("cfgTablaGartServiceImpl")
	CfgTablaGartService tablaService;
	@Autowired
	@Qualifier("cfgCampoGartServiceImpl")
	CfgCampoGartService campoService;
	@Autowired
	@Qualifier("commonGartServiceImpl")
	CommonGartService commonService;
	
	List<FiseFormato14BC> listaFormato;
	Map<String, String> mapaEmpresa;
	List<FisePeriodoEnvio> listaPeriodoEnvio;
	Map<String, String> mapaErrores;
	List<MensajeErrorBean> listaObservaciones;
	Map<Long, String> mapaZonaBenef;
	
	Formato14BCBean beanBusqueda;
	
	@SuppressWarnings("unchecked")
	@RequestMapping
	public String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse,@ModelAttribute("formato14BCBean")Formato14BCBean command){

		listaPeriodoEnvio = new ArrayList<FisePeriodoEnvio>();
		
		PortletRequest pRequest = (PortletRequest)renderRequest.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
		Formato14BGartJSON obj = new Formato14BGartJSON();
		
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
		
		obj.setCodEmpresa(codEmpresa!=null?codEmpresa:"");
		obj.setAnoPres(anioPresentacion!=null?anioPresentacion:"");
		obj.setMesPres(mesPresentacion!=null?mesPresentacion:"");
		obj.setAnoIniVig(anoInicioVigencia!=null?anoInicioVigencia:"");
		obj.setAnoFinVig(anoFinVigencia!=null?anoFinVigencia:"");
		obj.setEtapa(etapa!=null?etapa:"");
		obj.setMensajeError(msgError!=null?msgError:"");
		obj.setMensajeInfo(msgInfo!=null?msgInfo:"");
		obj.setEtapa(etapa!=null?etapa:"");
		obj.setFlag(flag!=null?flag:"");
		
		pRequest.getPortletSession().setAttribute("codEmpresa", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("anoPresentacion", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("mesPresentacion", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("anoInicioVigencia", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("anoFinVigencia", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("etapa", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("flag", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("mensajeError", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("listaError", null, PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("mensajeInformacion", "", PortletSession.APPLICATION_SCOPE);
		
		
		command.setListaMes(fiseUtil.getMapaMeses());
		
		if( beanBusqueda!=null && beanBusqueda.getCodEmpresaB()!=null ){
			command.setCodEmpresaB(beanBusqueda.getCodEmpresaB());
		}
		if( beanBusqueda!=null && beanBusqueda.getAnioDesde()!=null ){
			command.setAnioDesde(beanBusqueda.getAnioDesde());
		}else{
			command.setAnioDesde(fiseUtil.obtenerNroAnioFechaAnterior());
		}
		if( beanBusqueda!=null && beanBusqueda.getAnioHasta()!=null ){
			command.setAnioHasta(beanBusqueda.getAnioHasta());
		}else{
			command.setAnioHasta(fiseUtil.obtenerNroAnioFechaActual());
		}
		if( beanBusqueda!=null && beanBusqueda.getMesDesde()!=null ){
			command.setMesDesde(beanBusqueda.getMesDesde());
		}else{
			command.setMesDesde(fiseUtil.obtenerNroMesFechaAnterior());
		}
		if( beanBusqueda!=null && beanBusqueda.getMesHasta()!=null ){
			command.setMesHasta(beanBusqueda.getMesHasta());
		}else{
			command.setMesHasta(fiseUtil.obtenerNroMesFechaActual());
		}
		if( beanBusqueda!=null && beanBusqueda.getEtapaB()!=null ){
			command.setEtapaB(beanBusqueda.getEtapaB());
		}
		
		/*command.setAnioDesde(fiseUtil.obtenerNroAnioFechaActual());
		command.setMesDesde( String.valueOf(Integer.parseInt(fiseUtil.obtenerNroMesFechaActual())-1));
		command.setAnioHasta(fiseUtil.obtenerNroAnioFechaActual());
		command.setMesHasta(fiseUtil.obtenerNroMesFechaActual());
		command.setEtapaB(FiseConstants.ETAPA_SOLICITUD);*/
		
		//valores constantes para las empresas edelnor y luz del sur
		command.setCodEdelnor(FiseConstants.COD_EMPRESA_EDELNOR);
		command.setCodLuzSur(FiseConstants.COD_EMPRESA_LUZ_SUR);
		
		if(command.getListaEmpresas()==null){
			command.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));
		}
		command.setAdmin(fiseUtil.esAdministrador(renderRequest));
		mapaEmpresa = fiseUtil.getMapaEmpresa();
		
		mapaErrores = fiseUtil.getMapaErrores();
		mapaZonaBenef = fiseUtil.getMapaZonaBenef();
		
		if( listaError!=null && listaError.size()>0){
			model.addAttribute("listaError", listaError);
		}
		
		model.addAttribute("model", obj);
		
		return "formato14B";
	}
	
	@ResourceMapping("busqueda")
  	public void busqueda(ResourceRequest request,ResourceResponse response,@ModelAttribute("formato14BCBean")Formato14BCBean command){
		
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
  			
  			listaFormato = formato14Service.buscarFormato14BC(
  					(codEmpresa!=null&&codEmpresa!="")?FormatoUtil.rellenaDerecha(codEmpresa, ' ', 4):"", 
  					(anioDesde!=null&&anioDesde!="")?Long.parseLong(anioDesde):0, 
  					(mesDesde!=null&&mesDesde!="")?Long.parseLong(mesDesde):0, 
  					(anioHasta!=null&&anioHasta!="")?Long.parseLong(anioHasta):0, 
  					(mesHasta!=null&&mesHasta!="")?Long.parseLong(mesHasta):0, 
  					(etapa!=null&&etapa!="")?etapa:""
  					);
  			logger.info("arreglo lista:"+listaFormato);
  			for(FiseFormato14BC fiseFormato14BC : listaFormato){
  				fiseFormato14BC.setDescEmpresa(mapaEmpresa.get(fiseFormato14BC.getId().getCodEmpresa()));
  				fiseFormato14BC.setDescMesPresentacion(fiseUtil.getMapaMeses().get(fiseFormato14BC.getId().getMesPresentacion()));
  				
  				//grupo informacion y estado
  				if(fiseFormato14BC.getFiseGrupoInformacion()!=null && fiseFormato14BC.getFiseGrupoInformacion().getDescripcion()!=null){
  					fiseFormato14BC.setDescGrupoInformacion(fiseFormato14BC.getFiseGrupoInformacion().getDescripcion());
  				}else{
  					fiseFormato14BC.setDescGrupoInformacion(FiseConstants.BLANCO);
  				}
  				if(fiseFormato14BC.getFechaEnvioDefinitivo()!=null){
  					fiseFormato14BC.setDescEstado(FiseConstants.ESTADO_FECHAENVIO_ENVIADO);
  				}else{
  					fiseFormato14BC.setDescEstado(FiseConstants.ESTADO_FECHAENVIO_POR_ENVIAR);
  				}
  				
  				/**Obteniendo el flag de la operacion*/
  				String flagOper = commonService.obtenerEstadoProceso(fiseFormato14BC.getId().getCodEmpresa(),FiseConstants.TIPO_FORMATO_14B,fiseFormato14BC.getId().getAnoPresentacion(),
  						fiseFormato14BC.getId().getMesPresentacion(), fiseFormato14BC.getId().getEtapa());
  				logger.info("flag operacion:  "+flagOper);
  				
  				jsonArray.put(new Formato14BGartJSON().asJSONObject(fiseFormato14BC,"",flagOper));
  			}
  			
  		//valores busqueda
			beanBusqueda = new Formato14BCBean();
			beanBusqueda.setCodEmpresaB(command.getCodEmpresaB());
			beanBusqueda.setAnioDesde(command.getAnioDesde());
			beanBusqueda.setMesDesde(command.getMesDesde());
			beanBusqueda.setAnioHasta(command.getAnioHasta());
			beanBusqueda.setMesHasta(command.getMesHasta());
			beanBusqueda.setEtapaB(command.getEtapaB());
  			
  			fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_FORMATO_14B, FiseConstants.NOMBRE_EXCEL_FORMATO14B, FiseConstants.NOMBRE_HOJA_FORMATO14B, listaFormato);
  			
  			logger.info("arreglo json:"+jsonArray);
  			System.out.println("INFORMACION OBTENIDA :::"+jsonArray.toString());
  			PrintWriter pw = response.getWriter();
  			pw.write(jsonArray.toString());
  			pw.flush();
  			pw.close();
  			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	@ResourceMapping("crud")
	public void crud(ModelMap model, ResourceRequest request,ResourceResponse response, @ModelAttribute("formato14BCBean")Formato14BCBean command) {
 	
		try {
			JSONObject jsonObj = new JSONObject();
			String tipo = request.getParameter("tipo");   		 
			if(tipo.equals(FiseConstants.COD_GET)){//ver editar
				
				FiseFormato14BC formato = new FiseFormato14BC();
				FiseFormato14BCPK pk = new FiseFormato14BCPK();
				String codEmpresa = request.getParameter("codEmpresa");
				String anoPresentacion = request.getParameter("anoPresentacion");
				String mesPresentacion = request.getParameter("mesPresentacion");
				String anoIniVigencia = request.getParameter("anoIniVigencia");
				String anoFinVigencia = request.getParameter("anoFinVigencia");
				String etapa = request.getParameter("etapa");
				
				pk.setCodEmpresa(codEmpresa);
				pk.setAnoPresentacion(new Long(anoPresentacion));
		        pk.setMesPresentacion(new Long(mesPresentacion));
		        pk.setAnoInicioVigencia(new Long(anoIniVigencia));
		        pk.setAnoFinVigencia(new Long(anoFinVigencia));
		        pk.setEtapa(etapa);
		        
		        logger.info("codempresa "+codEmpresa);
		        logger.info("anopresent "+anoPresentacion);
		        logger.info("mespresent "+mesPresentacion);
		        logger.info("anoIniVigencia "+anoIniVigencia);
		        logger.info("anoFinVigencia "+anoFinVigencia);
		        logger.info("etapa "+etapa);
		        
		        formato = formato14Service.obtenerFormato14BCByPK(pk);
		        
		        Formato14BGartJSON obj = new Formato14BGartJSON();
		        String codigoPeriodoEnvio="";
		        String flagPeriodo="";
		        
		        if( formato != null ){
		        	//guardamos valores en sesion
					PortletRequest pRequest = (PortletRequest) request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
					pRequest.getPortletSession().setAttribute("codEmpresaEdit", formato.getId().getCodEmpresa(), PortletSession.APPLICATION_SCOPE);
				    pRequest.getPortletSession().setAttribute("anoPresentacionEdit", String.valueOf(formato.getId().getAnoPresentacion()), PortletSession.APPLICATION_SCOPE);
				    pRequest.getPortletSession().setAttribute("mesPresentacionEdit", String.valueOf(formato.getId().getMesPresentacion()), PortletSession.APPLICATION_SCOPE);
				    pRequest.getPortletSession().setAttribute("anoIniVigenciaEdit", String.valueOf(formato.getId().getAnoInicioVigencia()), PortletSession.APPLICATION_SCOPE);
				    pRequest.getPortletSession().setAttribute("anoFinVigenciaEdit", String.valueOf(formato.getId().getAnoFinVigencia()), PortletSession.APPLICATION_SCOPE);
				    pRequest.getPortletSession().setAttribute("etapaEdit", formato.getId().getEtapa(), PortletSession.APPLICATION_SCOPE);
				    
				    obj.setCodEmpresa(formato.getId().getCodEmpresa());
					obj.setAnoPres(String.valueOf(formato.getId().getAnoPresentacion()));
					obj.setMesPres(String.valueOf(formato.getId().getMesPresentacion()));
					obj.setAnoIniVig(String.valueOf(formato.getId().getAnoInicioVigencia()));
					obj.setAnoFinVig(String.valueOf(formato.getId().getAnoFinVigencia()));
					obj.setEtapa(formato.getId().getEtapa());
				    
					model.addAttribute("model", obj);
					
				    //setear la lista de periodo correspondiente al registro
				    listaPeriodoEnvio = periodoService.listarFisePeriodoEnvioMesAnioEtapa(codEmpresa, FiseConstants.NOMBRE_FORMATO_14B);
				    JSONArray jsonArray = new JSONArray();
		  			for (FisePeriodoEnvio periodo : listaPeriodoEnvio) {
		  				JSONObject jsonObj2 = new JSONObject();
						jsonObj2.put("codigoItem", periodo.getCodigoItem());				
						jsonObj2.put("descripcionItem", periodo.getDescripcionItem());					
						jsonArray.put(jsonObj2);		
					}
		  			jsonObj.put("periodoEnvio",jsonArray);
		  			codigoPeriodoEnvio =String.valueOf(formato.getId().getAnoPresentacion())+
		        			FormatoUtil.rellenaIzquierda(String.valueOf(formato.getId().getMesPresentacion()), '0', 2)+
		        			formato.getId().getEtapa();
		        }
		        
		        for (FisePeriodoEnvio p : listaPeriodoEnvio) {
					if( codigoPeriodoEnvio.equals(p.getCodigoItem()) ){
						flagPeriodo= p.getFlagPeriodoEjecucion();
						break;
					}
				}
		        
		        JSONObject jsonent = new Formato14BGartJSON().asJSONObject(formato,flagPeriodo,"");
		        logger.info("jsonformato:"+jsonent);
		        jsonObj.put("formato",jsonent);
				jsonObj.put("resultado", "OK");
				
			}else if(tipo.equals(FiseConstants.COD_SAVE)){ 
				try {
					
					String anoIniVigencia = request.getParameter("anoInicioVigencia");
					String anoFinVigencia = request.getParameter("anoFinVigencia");
					
					Formato14BCBean formulario = new Formato14BCBean();
					
					formulario.setCodigoEmpresa(command.getCodigoEmpresa());
					
					if( command.getPeriodoEnvio().length()>6 ){
						formulario.setAnioPresent(Long.parseLong(command.getPeriodoEnvio().substring(0, 4)));
						formulario.setMesPresent(Long.parseLong(command.getPeriodoEnvio().substring(4, 6)));
						formulario.setEtapa(command.getPeriodoEnvio().substring(6, command.getPeriodoEnvio().length()));
						/*if( "S".equals(command.getFlagPeriodoEjecucion()) ){
							formulario.setAnioInicioVigencia(command.getAnioInicioVigencia());
							formulario.setAnioFinVigencia(command.getAnioFinVigencia());
						}else{
							formulario.setAnioInicioVigencia(formulario.getAnioPresent());
							formulario.setAnioFinVigencia(formulario.getAnioPresent());
						}*/
					}
					formulario.setAnioInicioVigencia(Long.parseLong(anoIniVigencia));
					formulario.setAnioFinVigencia(Long.parseLong(anoFinVigencia));
					
					//RURAL
					formulario.setImpValDesctoEdeR(command.getImpValDesctoEdeR());
					formulario.setImpValDesctoNoEdeR(command.getImpValDesctoNoEdeR());
					formulario.setNroValesImpR(command.getNroValesImpR());
				
					formulario.setCostoTotalValDesctoR(command.getCostoTotalValDesctoR());
					formulario.setNroValesReptR(command.getNroValesReptR());
					
					formulario.setCostoTotalValOficR(command.getCostoTotalValOficR());
					formulario.setNroValesEntrR(command.getNroValesEntrR());
					
					formulario.setCostoEnvPadronR(command.getCostoEnvPadronR());
					formulario.setNroValesFisR(command.getNroValesFisR());
					
					formulario.setCostoUnitValesDigitR(command.getCostoUnitValesDigitR());
					
					formulario.setCostoAtenSolicR(command.getCostoAtenSolicR());
					formulario.setCostoAtenConsR(command.getCostoAtenConsR());
					formulario.setNroTotalAtenR(command.getNroTotalAtenR());
					
					formulario.setCostoPersonalR(command.getCostoPersonalR());
					formulario.setCapacAgentR(command.getCapacAgentR());
					formulario.setUtilMatOficR(command.getUtilMatOficR());
					
					//PROVINCIA
					
					formulario.setImpValDesctoEdeP(command.getImpValDesctoEdeP());
					formulario.setImpValDesctoNoEdeP(command.getImpValDesctoNoEdeP());
					formulario.setNroValesImpP(command.getNroValesImpP());
				
					formulario.setCostoTotalValDesctoP(command.getCostoTotalValDesctoP());
					formulario.setNroValesReptP(command.getNroValesReptP());
					
					formulario.setCostoTotalValOficP(command.getCostoTotalValOficP());
					formulario.setNroValesEntrP(command.getNroValesEntrP());
					
					formulario.setCostoEnvPadronP(command.getCostoEnvPadronP());
					formulario.setNroValesFisP(command.getNroValesFisP());
					
					formulario.setCostoUnitValesDigitP(command.getCostoUnitValesDigitP());
					
					formulario.setCostoAtenSolicP(command.getCostoAtenSolicP());
					formulario.setCostoAtenConsP(command.getCostoAtenConsP());
					formulario.setNroTotalAtenP(command.getNroTotalAtenP());
					
					formulario.setCostoPersonalP(command.getCostoPersonalP());
					formulario.setCapacAgentP(command.getCapacAgentP());
					formulario.setUtilMatOficP(command.getUtilMatOficP());
					
					//LIMA
					
					formulario.setImpValDesctoEdeL(command.getImpValDesctoEdeL());
					formulario.setImpValDesctoNoEdeL(command.getImpValDesctoNoEdeL());
					formulario.setNroValesImpL(command.getNroValesImpL());
				
					formulario.setCostoTotalValDesctoL(command.getCostoTotalValDesctoL());
					formulario.setNroValesReptL(command.getNroValesReptL());
					
					formulario.setCostoTotalValOficL(command.getCostoTotalValOficL());
					formulario.setNroValesEntrL(command.getNroValesEntrL());
					
					formulario.setCostoEnvPadronL(command.getCostoEnvPadronL());
					formulario.setNroValesFisL(command.getNroValesFisL());
					
					formulario.setCostoUnitValesDigitL(command.getCostoUnitValesDigitL());
					
					formulario.setCostoAtenSolicL(command.getCostoAtenSolicL());
					formulario.setCostoAtenConsL(command.getCostoAtenConsL());
					formulario.setNroTotalAtenL(command.getNroTotalAtenL());
					
					formulario.setCostoPersonalL(command.getCostoPersonalL());
					formulario.setCapacAgentL(command.getCapacAgentL());
					formulario.setUtilMatOficL(command.getUtilMatOficL());
					
					ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
					
					formulario.setUsuario(themeDisplay.getUser().getLogin());
					formulario.setTerminal(themeDisplay.getUser().getLoginIP());
					
					formato14Service.registrarFormato14BC(formulario);
					jsonObj.put("resultado", "OK");
	   				
				} catch (Exception e) {
					jsonObj.put("resultado", "Error");
					jsonObj.put("mensaje", e.getMessage());
					logger.error("Error al guardar los datos: "+e.getMessage());
				}   				   				
					 					 				
			}else if(tipo.equals(FiseConstants.COD_UPDATE)){
				logger.info("entro a modificar valores");
				
				try {
					
					FiseFormato14BC formato = new FiseFormato14BC();
					FiseFormato14BCPK pk = new FiseFormato14BCPK();
					String codEmpresa = request.getParameter("codEmpresa");
					String periodoEnvio = request.getParameter("periodoEnvio");
					//String anoPresentacion = request.getParameter("anoPresentacion");
					//String mesPresentacion = request.getParameter("mesPresentacion");
					/*String flagPeriodoEjecucion = request.getParameter("flagPeriodo");
					String anoInicioVigencia="";
					String anoFinVigencia="";
					if( "S".equals(flagPeriodoEjecucion) ){
						anoInicioVigencia = request.getParameter("anoInicioVigencia");
						anoFinVigencia = request.getParameter("anoFinVigencia");
					}*/
					String etapa = request.getParameter("etapa");
					
					String anoInicioVigencia = request.getParameter("anoInicioVigencia");
					String anoFinVigencia = request.getParameter("anoFinVigencia");
					
					
					logger.info("codempresa "+codEmpresa);
			        //logger.info("anopresent "+anoPresentacion);
			        //logger.info("mespresent "+mesPresentacion);
			        logger.info("anoInicioVigencia "+anoInicioVigencia);
			        logger.info("anoFinVigencia "+anoFinVigencia);
			        logger.info("etapa "+etapa);
					
			        
			        Formato14BCBean formulario = new Formato14BCBean();
			        
			      //RURAL
			        formulario.setImpValDesctoEdeR(command.getImpValDesctoEdeR());
					formulario.setImpValDesctoNoEdeR(command.getImpValDesctoNoEdeR());
					formulario.setNroValesImpR(command.getNroValesImpR());
					formulario.setCostoTotalValDesctoR(command.getCostoTotalValDesctoR());
					formulario.setNroValesReptR(command.getNroValesReptR());
					formulario.setCostoTotalValOficR(command.getCostoTotalValOficR());
					formulario.setNroValesEntrR(command.getNroValesEntrR());
					formulario.setCostoEnvPadronR(command.getCostoEnvPadronR());
					formulario.setNroValesFisR(command.getNroValesFisR());
					formulario.setCostoUnitValesDigitR(command.getCostoUnitValesDigitR());
					formulario.setCostoAtenSolicR(command.getCostoAtenSolicR());
					formulario.setCostoAtenConsR(command.getCostoAtenConsR());
					formulario.setNroTotalAtenR(command.getNroTotalAtenR());
					formulario.setCostoPersonalR(command.getCostoPersonalR());
					formulario.setCapacAgentR(command.getCapacAgentR());
					formulario.setUtilMatOficR(command.getUtilMatOficR());
					//PROVINCIA
					formulario.setImpValDesctoEdeP(command.getImpValDesctoEdeP());
					formulario.setImpValDesctoNoEdeP(command.getImpValDesctoNoEdeP());
					formulario.setNroValesImpP(command.getNroValesImpP());
					formulario.setCostoTotalValDesctoP(command.getCostoTotalValDesctoP());
					formulario.setNroValesReptP(command.getNroValesReptP());
					formulario.setCostoTotalValOficP(command.getCostoTotalValOficP());
					formulario.setNroValesEntrP(command.getNroValesEntrP());
					formulario.setCostoEnvPadronP(command.getCostoEnvPadronP());
					formulario.setNroValesFisP(command.getNroValesFisP());
					formulario.setCostoUnitValesDigitP(command.getCostoUnitValesDigitP());
					formulario.setCostoAtenSolicP(command.getCostoAtenSolicP());
					formulario.setCostoAtenConsP(command.getCostoAtenConsP());
					formulario.setNroTotalAtenP(command.getNroTotalAtenP());
					formulario.setCostoPersonalP(command.getCostoPersonalP());
					formulario.setCapacAgentP(command.getCapacAgentP());
					formulario.setUtilMatOficP(command.getUtilMatOficP());
					//LIMA
					formulario.setImpValDesctoEdeL(command.getImpValDesctoEdeL());
					formulario.setImpValDesctoNoEdeL(command.getImpValDesctoNoEdeL());
					formulario.setNroValesImpL(command.getNroValesImpL());
					formulario.setCostoTotalValDesctoL(command.getCostoTotalValDesctoL());
					formulario.setNroValesReptL(command.getNroValesReptL());
					formulario.setCostoTotalValOficL(command.getCostoTotalValOficL());
					formulario.setNroValesEntrL(command.getNroValesEntrL());
					formulario.setCostoEnvPadronL(command.getCostoEnvPadronL());
					formulario.setNroValesFisL(command.getNroValesFisL());
					formulario.setCostoUnitValesDigitL(command.getCostoUnitValesDigitL());
					formulario.setCostoAtenSolicL(command.getCostoAtenSolicL());
					formulario.setCostoAtenConsL(command.getCostoAtenConsL());
					formulario.setNroTotalAtenL(command.getNroTotalAtenL());
					formulario.setCostoPersonalL(command.getCostoPersonalL());
					formulario.setCapacAgentL(command.getCapacAgentL());
					formulario.setUtilMatOficL(command.getUtilMatOficL());
			        
			        formulario.setCodigoEmpresa(codEmpresa);
					
					if( periodoEnvio.length()>6 ){
						formulario.setAnioPresent(Long.parseLong(periodoEnvio.substring(0, 4)));
						formulario.setMesPresent(Long.parseLong(periodoEnvio.substring(4, 6)));
						formulario.setEtapa(periodoEnvio.substring(6, periodoEnvio.length()));
						/*if( "S".equals(flagPeriodoEjecucion) ){
							formulario.setAnioInicioVigencia(Long.parseLong(anoInicioVigencia));
							formulario.setAnioFinVigencia(Long.parseLong(anoFinVigencia));
						}else{
							formulario.setAnioInicioVigencia(formulario.getAnioPresent());
							formulario.setAnioFinVigencia(formulario.getAnioPresent());
						}*/
					}
					
					formulario.setAnioInicioVigencia(Long.parseLong(anoInicioVigencia));
					formulario.setAnioFinVigencia(Long.parseLong(anoFinVigencia));
					
					pk.setCodEmpresa(formulario.getCodigoEmpresa());
			        pk.setAnoPresentacion(formulario.getAnioPresent());
			        pk.setMesPresentacion(formulario.getMesPresent());
			        pk.setAnoInicioVigencia(formulario.getAnioInicioVigencia());
			        pk.setAnoFinVigencia(formulario.getAnioFinVigencia());
			        pk.setEtapa(formulario.getEtapa());
			        
			        formato = formato14Service.obtenerFormato14BCByPK(pk);
					logger.info("objeto "+formato);
					
					ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
					
					formulario.setUsuario(themeDisplay.getUser().getLogin());
					formulario.setTerminal(themeDisplay.getUser().getLoginIP());
					
			        formato14Service.modificarFormato14BC(formulario, formato);
					jsonObj.put("resultado", "OK"); 	
				} catch (Exception e) {
					jsonObj.put("resultado", "Error");
					jsonObj.put("mensaje", e.getMessage());
					System.out.println("Error al guardar la tabla fiseformato14B: "+e.getMessage());
				}
			}else if(tipo.equals(FiseConstants.COD_DELETE)){   				
				try {
					FiseFormato14BC formato = new FiseFormato14BC();
					FiseFormato14BCPK pk = new FiseFormato14BCPK();
					String codEmpresa = request.getParameter("codEmpresa");
					String anoPresentacion = request.getParameter("anoPresentacion");
					String mesPresentacion = request.getParameter("mesPresentacion");
					String anoInicioVigencia = request.getParameter("anoInicioVigencia");
					String anoFinVigencia = request.getParameter("anoFinVigencia");
					String etapa = request.getParameter("etapa");
					
					logger.info("valorp"+codEmpresa);
					logger.info("valorp"+anoPresentacion);
					logger.info("valorp"+mesPresentacion);
					logger.info("valorp"+anoInicioVigencia);
					logger.info("valorp"+anoFinVigencia);
					
					
					pk.setCodEmpresa(codEmpresa);
			        pk.setAnoPresentacion(new Long(anoPresentacion));
			        pk.setMesPresentacion(new Long(mesPresentacion));
			        pk.setAnoInicioVigencia(new Long(anoInicioVigencia));
			        pk.setAnoFinVigencia(new Long(anoFinVigencia));
			        pk.setEtapa(etapa);
					
			        formato = formato14Service.obtenerFormato14BCByPK(pk);
			        logger.info("valorpobjeto"+formato);
			        
			        //metodo delete
			        formato14Service.eliminarFormato14BC(formato);
			        jsonObj.put("resultado", "OK");
				} catch (Exception e) {
					jsonObj.put("resultado", "Error");
					jsonObj.put("mensaje", e.getMessage());
					System.out.println("Error al eliminar datos "+e.getMessage());
				}   				   				
				
			}
			response.setContentType("application/json");
		    PrintWriter pw = response.getWriter();
		    pw.write(jsonObj.toString());
		    pw.flush();
		    pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@ResourceMapping("cargaPeriodo")
  	public void cargaPeriodo(ModelMap model, ResourceRequest request,ResourceResponse response,@ModelAttribute("formato14BCBean")Formato14BCBean command){
		try {			
  			response.setContentType("applicacion/json");
  			String codEmpresa = command.getCodigoEmpresa();
  			//String periodoEnvio = command.getPeriodoEnvio();
  			//lo pongo en la lista porque no persiste las colecciones en el command
  			listaPeriodoEnvio = periodoService.listarFisePeriodoEnvioMesAnioEtapa(codEmpresa, FiseConstants.TIPO_FORMATO_14B);
  			command.setListaPeriodoEnvio(listaPeriodoEnvio);
  			
  			JSONArray jsonArray = new JSONArray();
  			for (FisePeriodoEnvio periodo : command.getListaPeriodoEnvio()) {
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
	
	@ResourceMapping("cargaFlagPeriodo")
  	public void cargaFlagPeriodo(ResourceRequest request,ResourceResponse response,@ModelAttribute("formato14BCBean")Formato14BCBean command){
		try {			
  			response.setContentType("applicacion/json");
  			String periodoEnvio = command.getPeriodoEnvio();
  			JSONObject jsonObj = new JSONObject();
  			for (FisePeriodoEnvio p : listaPeriodoEnvio) {
				if( periodoEnvio.equals(p.getCodigoItem()) ){
					jsonObj.put("flagPeriodoEjecucion", p.getFlagPeriodoEjecucion());
					//agreamos los campos de ano inicio y fin de vigencia
					jsonObj.put("anioInicioVigencia", p.getAnioInicioVig());
					jsonObj.put("anioFinVigencia", p.getAnioFinVig());
					break;
				}
			}
  			
  			if( periodoEnvio!=null && periodoEnvio.length()>6 ){
  				long idGrupo = commonService.obtenerIdGrupoInformacion(Long.parseLong(periodoEnvio.substring(0, 4)), Long.parseLong(periodoEnvio.substring(4, 6)), FiseConstants.BIENAL);
  				jsonObj.put("idGrupoInfo", idGrupo);
  			}else{
  				jsonObj.put("idGrupoInfo", 0);
  			}
  			
  			System.out.println(jsonObj.toString());
  			PrintWriter pw = response.getWriter();
  		    pw.write(jsonObj.toString());
  		    pw.flush();
  		    pw.close();							
  		}catch (Exception e) {
  			// TODO: handle exception
  			e.printStackTrace();
  		}
	}
	
	@ActionMapping(params="action=cargar")
	public void cargarDocumento(ActionRequest request,ActionResponse response,@ModelAttribute("formato14BCBean")Formato14BCBean command){
		
		logger.info("--- cargar documento");
		Formato14BMensajeBean formatoMensaje = new Formato14BMensajeBean();
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(request);
		
		String flagCarga = uploadPortletRequest.getParameter("flagCarga");
    	String codEmpresaNew = uploadPortletRequest.getParameter("codigoEmpresa");
    	String periodoEnvioPresNew = uploadPortletRequest.getParameter("periodoEnvio");
    	//String flagPeriodoEjecucion = uploadPortletRequest.getParameter("flagPeriodoEjecucion");
    	
    	String anioPresNew = "";
		String mesPresNew = "";
		String anioIniVigNew = "";
		String anioFinVigNew = "";
		String etapaNew = "";
		
		if(periodoEnvioPresNew!=null && periodoEnvioPresNew.length()>6){
    		anioPresNew = periodoEnvioPresNew.substring(0, 4);
    		mesPresNew = periodoEnvioPresNew.substring(4, 6);
    		etapaNew = periodoEnvioPresNew.substring(6, periodoEnvioPresNew.length());
    	
    		for (FisePeriodoEnvio p : listaPeriodoEnvio) {
    			if( periodoEnvioPresNew.equals(p.getCodigoItem()) ){
    				anioIniVigNew = p.getAnioInicioVig();
    				anioFinVigNew = p.getAnioFinVig();
    				break;
    			}
    		}
    		
		}
    	
		
		PortletRequest pRequest = (PortletRequest) request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
		
		String codEmpresaEdit = (String)pRequest.getPortletSession().getAttribute("codEmpresaEdit", PortletSession.APPLICATION_SCOPE);
		String anioPresEdit = (String)pRequest.getPortletSession().getAttribute("anoPresentacionEdit", PortletSession.APPLICATION_SCOPE);
		String mesPresEdit = (String)pRequest.getPortletSession().getAttribute("mesPresentacionEdit", PortletSession.APPLICATION_SCOPE);
		String anioIniVigEdit = (String)pRequest.getPortletSession().getAttribute("anoIniVigenciaEdit", PortletSession.APPLICATION_SCOPE);
		String anioFinVigEdit = (String)pRequest.getPortletSession().getAttribute("anoFinVigenciaEdit", PortletSession.APPLICATION_SCOPE);
		String etapaEdit = (String)pRequest.getPortletSession().getAttribute("etapaEdit", PortletSession.APPLICATION_SCOPE);
		
		FileEntry fileEntry=null;
		try{
		if( flagCarga.equals(FiseConstants.FLAG_CARGAEXCEL_FORMULARIONUEVO) ){
			fileEntry=fiseUtil.subirDocumento(request, uploadPortletRequest, FiseConstants.TIPOARCHIVO_XLS);
			formatoMensaje = readExcelFile(fileEntry, themeDisplay.getUser(), flagCarga, codEmpresaNew, anioPresNew, mesPresNew, anioIniVigNew, anioFinVigNew, etapaNew);
		}else if( flagCarga.equals(FiseConstants.FLAG_CARGAEXCEL_FORMULARIOMODIFICACION) ){
			fileEntry=fiseUtil.subirDocumento(request, uploadPortletRequest, FiseConstants.TIPOARCHIVO_XLS);
			formatoMensaje = readExcelFile(fileEntry, themeDisplay.getUser(), flagCarga, codEmpresaEdit, anioPresEdit, mesPresEdit, anioIniVigEdit, anioFinVigEdit, etapaEdit);
		}else if( flagCarga.equals(FiseConstants.FLAG_CARGATXT_FORMULARIONUEVO) ){
			fileEntry =fiseUtil.subirDocumento(request, uploadPortletRequest, FiseConstants.TIPOARCHIVO_TXT);
			formatoMensaje =	readTxtFile(fileEntry, uploadPortletRequest, themeDisplay.getUser(), flagCarga, codEmpresaNew, anioPresNew, mesPresNew, anioIniVigNew, anioFinVigNew, etapaNew);
		}else if( flagCarga.equals(FiseConstants.FLAG_CARGATXT_FORMULARIOMODIFICACION) ){
			fileEntry=fiseUtil.subirDocumento(request, uploadPortletRequest, FiseConstants.TIPOARCHIVO_TXT);
			formatoMensaje =	readTxtFile(fileEntry, uploadPortletRequest, themeDisplay.getUser(), flagCarga, codEmpresaEdit, anioPresEdit, mesPresEdit, anioIniVigEdit, anioFinVigEdit, etapaEdit);
		}
	}catch(FileMimeTypeException ex){
		
	}catch (Exception e) {
		// TODO: handle exception
	}
		
		if( formatoMensaje.getFiseFormato14BC()!=null ){
			String codEmpresa = formatoMensaje.getFiseFormato14BC().getId().getCodEmpresa();
			String anoPresentacion = String.valueOf(formatoMensaje.getFiseFormato14BC().getId().getAnoPresentacion());
			String mesPresentacion = String.valueOf(formatoMensaje.getFiseFormato14BC().getId().getMesPresentacion());
			String anoIniVigencia = String.valueOf(formatoMensaje.getFiseFormato14BC().getId().getAnoInicioVigencia());
			String anoFinVigencia = String.valueOf(formatoMensaje.getFiseFormato14BC().getId().getAnoFinVigencia());
			String etapa = String.valueOf(formatoMensaje.getFiseFormato14BC().getId().getEtapa());
			
			pRequest.getPortletSession().setAttribute("codEmpresa", codEmpresa, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("anoPresentacion", anoPresentacion, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("mesPresentacion", mesPresentacion, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("anoInicioVigencia", anoIniVigencia, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("anoFinVigencia", anoFinVigencia, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("etapa", etapa, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("flag", "", PortletSession.APPLICATION_SCOPE);//para control de mostrar formulario a ingresar
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
			    pRequest.getPortletSession().setAttribute("flag", "", PortletSession.APPLICATION_SCOPE);//para control de mostrar formulario a ingresar
			}
			
		}
		if(formatoMensaje.getListaMensajeError()!=null && formatoMensaje.getListaMensajeError().size()>0){
			pRequest.getPortletSession().setAttribute("listaError", formatoMensaje.getListaMensajeError(), PortletSession.APPLICATION_SCOPE);
			pRequest.getPortletSession().setAttribute("mensajeError", formatoMensaje.getMensajeError(), PortletSession.APPLICATION_SCOPE);
		}else{
			pRequest.getPortletSession().setAttribute("mensajeInformacion", "El Formato 14B se guardó satisfactoriamente", PortletSession.APPLICATION_SCOPE);
		}
		//limpiamos las variables en sesion utilizados para la edicion de archivos de carga
		pRequest.getPortletSession().setAttribute("codEmpresaEdit", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("anoPresentacionEdit", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("mesPresentacionEdit", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("anoEjecucionEdit", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("mesEjecucionEdit", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("etapaEdit", "", PortletSession.APPLICATION_SCOPE);
		
	}
	
public Formato14BMensajeBean readExcelFile(FileEntry archivo, User user, String flagCarga, String codEmpresa, String anioPres, String mesPres, String anioIniVigencia, String anioFinVigencia, String etapaEdit) {
		
		//---------------------
		//FLAG CARGA:
		//	1: para registros nuevos
		//	2: para registros modificados
		//---------------------
		Formato14BMensajeBean formatoMensaje = new Formato14BMensajeBean();
		
		InputStream is=null;
		FiseFormato14BC objeto = null;
		String sMsg = "";
		List<MensajeErrorBean> listaError = new ArrayList<MensajeErrorBean>();
		int cont = 0;
		
		boolean process = false;
		
		try {
			if (archivo != null) {
				HSSFWorkbook libro = null;
				try {
					is=archivo.getContentStream();
					libro = new HSSFWorkbook(is);//Se lee libro xls
				} catch (Exception e1) {
					logger.warn(mapaErrores.get(FiseConstants.COD_ERROR_F14B_2470));
					cont++;
					sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F14B_2470);
					throw new Exception(mapaErrores.get(FiseConstants.COD_ERROR_F14B_2470));
				}
				int nroHojaSelec=0;
				
				if (libro != null) {
					//el excel puede tener varias hojas, se tiene qie leer el total de hojas y encontrar la que necesitemos
					logger.info("nro de hojas:"+ libro.getNumberOfSheets());
					for (int sheetNro = 0; sheetNro < libro.getNumberOfSheets(); sheetNro++){
						logger.info("nombre de hoja "+sheetNro+":"+ libro.getSheetName(sheetNro));
						if( FiseConstants.NOMBRE_HOJA_FORMATO14B.equals(libro.getSheetName(sheetNro)) ){
							process = true;
							nroHojaSelec = sheetNro;
							break;
						}
					}
					logger.info("nro de hoja seleccionada "+nroHojaSelec);
					
					if(process){
						
						HSSFSheet hojaF14 = libro.getSheetAt(nroHojaSelec);
						//int nroFilas = hojaF12.getLastRowNum()+1;
						
						HSSFRow filaEmpresa = hojaF14.getRow(FiseConstants.NRO_FILA_CODEMPRESA_FORMATO14B);						
						HSSFRow filaAnioMes = hojaF14.getRow(FiseConstants.NRO_FILA_ANIOMES_FORMATO14B);	
						
						HSSFRow filaImpValesEde = hojaF14.getRow(FiseConstants.NRO_FILA_IMPVALESEDE_FORMATO14B);					
						HSSFRow filaImpValesNoEde = hojaF14.getRow(FiseConstants.NRO_FILA_IMPVALESNOEDE_FORMATO14B);					
						HSSFRow filaNroValesImp = hojaF14.getRow(FiseConstants.NRO_FILA_NROVALESIMP_FORMATO14B);				
						HSSFRow filaTotalRepValesDescto = hojaF14.getRow(FiseConstants.NRO_FILA_TOTALVALESDSCTO_FORMATO14B);	
						HSSFRow filaNroValesRep = hojaF14.getRow(FiseConstants.NRO_FILA_NROVALESREP_FORMATO14B);
						HSSFRow filaTotalRepValesOfic = hojaF14.getRow(FiseConstants.NRO_FILA_TOTALVALESENTR_FORMATO14B);
						HSSFRow filaNroValesEntr = hojaF14.getRow(FiseConstants.NRO_FILA_NROVALESENTR_FORMATO14B);
						HSSFRow filaTotalEnvPadron = hojaF14.getRow(FiseConstants.NRO_FILA_COSTOENVPADRON_FORMATO14B);
						HSSFRow filaNroValesFis = hojaF14.getRow(FiseConstants.NRO_FILA_NROVALESFIS_FORMATO14B);
						HSSFRow filaCostoUnitValesDigit = hojaF14.getRow(FiseConstants.NRO_FILA_COSTOUNITVALESDIGIT_FORMATO14B);
						HSSFRow filaCostoAtenSolic = hojaF14.getRow(FiseConstants.NRO_FILA_ATENSOLIC_FORMATO14B);
						HSSFRow filaCostoAtenConsul = hojaF14.getRow(FiseConstants.NRO_FILA_ATENCONSUL_FORMATO14B);
						HSSFRow filaNroAten = hojaF14.getRow(FiseConstants.NRO_FILA_NROTOTALATEN_FORMATO14B);
						HSSFRow filaCostoPers = hojaF14.getRow(FiseConstants.NRO_FILA_COSTOPERS_FORMATO14B);
						HSSFRow filaCapacAgent = hojaF14.getRow(FiseConstants.NRO_FILA_CAPACAGENT_FORMATO14B);
						HSSFRow filaUtilMatOfic = hojaF14.getRow(FiseConstants.NRO_FILA_UTILMATOFIC_FORMATO14B);	
						
						Formato14BCBean formulario = new Formato14BCBean();
						
						HSSFCell celdaEmpresa = filaEmpresa.getCell(FiseConstants.NRO_CELDA_EMPRESA_FORMATO14B);
						HSSFCell celdaAnio = filaAnioMes.getCell(FiseConstants.NRO_CELDA_ANIO_FORMATO14B);
						HSSFCell celdaMes = filaAnioMes.getCell(FiseConstants.NRO_CELDA_MES_FORMATO14B);
						
						HSSFCell celdaImpValesEdeR = filaImpValesEde.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO14B);					
						HSSFCell celdaImpValesNoEdeR = filaImpValesNoEde.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO14B);					
						HSSFCell celdaNroValesImpR = filaNroValesImp.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO14B);				
						HSSFCell celdaTotalRepValesDesctoR = filaTotalRepValesDescto.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO14B);	
						HSSFCell celdaNroValesRepR = filaNroValesRep.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO14B);
						HSSFCell celdaTotalRepValesOficR = filaTotalRepValesOfic.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO14B);
						HSSFCell celdaNroValesEntrR = filaNroValesEntr.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO14B);
						HSSFCell celdaTotalEnvPadronR = filaTotalEnvPadron.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO14B);
						HSSFCell celdaNroValesFisR = filaNroValesFis.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO14B);
						HSSFCell celdaCostoUnitValesDigitR = filaCostoUnitValesDigit.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO14B);
						HSSFCell celdaCostoAtenSolicR = filaCostoAtenSolic.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO14B);
						HSSFCell celdaCostoAtenConsulR = filaCostoAtenConsul.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO14B);
						HSSFCell celdaNroAtenR = filaNroAten.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO14B);
						HSSFCell celdaCostoPersR = filaCostoPers.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO14B);
						HSSFCell celdaCapacAgentR = filaCapacAgent.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO14B);
						HSSFCell celdaUtilMatOficR = filaUtilMatOfic.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO14B);	
						//PROVINCIA
						HSSFCell celdaImpValesEdeP = filaImpValesEde.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO14B);					
						HSSFCell celdaImpValesNoEdeP = filaImpValesNoEde.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO14B);					
						HSSFCell celdaNroValesImpP = filaNroValesImp.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO14B);				
						HSSFCell celdaTotalRepValesDesctoP = filaTotalRepValesDescto.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO14B);	
						HSSFCell celdaNroValesRepP = filaNroValesRep.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO14B);
						HSSFCell celdaTotalRepValesOficP = filaTotalRepValesOfic.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO14B);
						HSSFCell celdaNroValesEntrP = filaNroValesEntr.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO14B);
						HSSFCell celdaTotalEnvPadronP = filaTotalEnvPadron.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO14B);
						HSSFCell celdaNroValesFisP = filaNroValesFis.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO14B);
						HSSFCell celdaCostoUnitValesDigitP = filaCostoUnitValesDigit.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO14B);
						HSSFCell celdaCostoAtenSolicP = filaCostoAtenSolic.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO14B);
						HSSFCell celdaCostoAtenConsulP = filaCostoAtenConsul.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO14B);
						HSSFCell celdaNroAtenP = filaNroAten.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO14B);
						HSSFCell celdaCostoPersP = filaCostoPers.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO14B);
						HSSFCell celdaCapacAgentP = filaCapacAgent.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO14B);
						HSSFCell celdaUtilMatOficP = filaUtilMatOfic.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO14B);
						//LIMA
						HSSFCell celdaImpValesEdeL = filaImpValesEde.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO14B);					
						HSSFCell celdaImpValesNoEdeL = filaImpValesNoEde.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO14B);					
						HSSFCell celdaNroValesImpL = filaNroValesImp.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO14B);				
						HSSFCell celdaTotalRepValesDesctoL = filaTotalRepValesDescto.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO14B);	
						HSSFCell celdaNroValesRepL = filaNroValesRep.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO14B);
						HSSFCell celdaTotalRepValesOficL = filaTotalRepValesOfic.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO14B);
						HSSFCell celdaNroValesEntrL = filaNroValesEntr.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO14B);
						HSSFCell celdaTotalEnvPadronL = filaTotalEnvPadron.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO14B);
						HSSFCell celdaNroValesFisL = filaNroValesFis.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO14B);
						HSSFCell celdaCostoUnitValesDigitL = filaCostoUnitValesDigit.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO14B);
						HSSFCell celdaCostoAtenSolicL = filaCostoAtenSolic.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO14B);
						HSSFCell celdaCostoAtenConsulL = filaCostoAtenConsul.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO14B);
						HSSFCell celdaNroAtenL = filaNroAten.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO14B);
						HSSFCell celdaCostoPersL = filaCostoPers.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO14B);
						HSSFCell celdaCapacAgentL = filaCapacAgent.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO14B);
						HSSFCell celdaUtilMatOficL = filaUtilMatOfic.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO14B);
						
						
						//tipos
						if( HSSFCell.CELL_TYPE_STRING == celdaEmpresa.getCellType()  ){
							formulario.setCodigoEmpresa(celdaEmpresa.toString());
						}else if( HSSFCell.CELL_TYPE_FORMULA == celdaEmpresa.getCellType()  ){
							formulario.setCodigoEmpresa(celdaEmpresa.getRichStringCellValue().toString());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaEmpresa.getCellType()  ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2480);
						}else{
							formulario.setCodigoEmpresa("");
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2490);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaAnio.getCellType()  ){
							formulario.setAnioPresent(new Double(celdaAnio.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaAnio.getCellType()  ){
							formulario.setAnioPresent(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2500);
						}else{
							formulario.setAnioPresent(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2510);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaMes.getCellType()  ){
							formulario.setMesPresent(new Double(celdaMes.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaMes.getCellType()  ){
							formulario.setMesPresent(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2520);
						}else{
							formulario.setMesPresent(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2530);
						}
						//valores
						//RURAL
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpValesEdeR.getCellType()  ){
							formulario.setImpValDesctoEdeR(new BigDecimal(celdaImpValesEdeR.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpValesEdeR.getCellType()  ){
							formulario.setImpValDesctoEdeR(new BigDecimal(0.00));
						}else{
							formulario.setImpValDesctoEdeR(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2540);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpValesNoEdeR.getCellType()  ){
							formulario.setImpValDesctoNoEdeR(new BigDecimal(celdaImpValesNoEdeR.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpValesNoEdeR.getCellType()  ){
							formulario.setImpValDesctoNoEdeR(new BigDecimal(0.00));
						}else{
							formulario.setImpValDesctoNoEdeR(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2550);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroValesImpR.getCellType()  ){
							formulario.setNroValesImpR(new Double(celdaNroValesImpR.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroValesImpR.getCellType()  ){
							formulario.setNroValesImpR(0L);
						}else{
							formulario.setNroValesImpR(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2560);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaTotalRepValesDesctoR.getCellType()  ){
							formulario.setCostoTotalValDesctoR(new BigDecimal(celdaTotalRepValesDesctoR.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaTotalRepValesDesctoR.getCellType()  ){
							formulario.setCostoTotalValDesctoR(new BigDecimal(0.00));
						}else{
							formulario.setCostoTotalValDesctoR(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2570);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroValesRepR.getCellType()  ){
							formulario.setNroValesReptR(new Double(celdaNroValesRepR.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroValesRepR.getCellType()  ){
							formulario.setNroValesReptR(0L);
						}else{
							formulario.setNroValesReptR(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2580);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaTotalRepValesOficR.getCellType()  ){
							formulario.setCostoTotalValOficR(new BigDecimal(celdaTotalRepValesOficR.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaTotalRepValesOficR.getCellType()  ){
							formulario.setCostoTotalValOficR(new BigDecimal(0.00));
						}else{
							formulario.setCostoTotalValOficR(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2590);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroValesEntrR.getCellType()  ){
							formulario.setNroValesEntrR(new Double(celdaNroValesEntrR.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroValesEntrR.getCellType()  ){
							formulario.setNroValesEntrR(0L);
						}else{
							formulario.setNroValesEntrR(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2600);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaTotalEnvPadronR.getCellType()  ){
							formulario.setCostoEnvPadronR(new BigDecimal(celdaTotalEnvPadronR.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaTotalEnvPadronR.getCellType()  ){
							formulario.setCostoEnvPadronR(new BigDecimal(0.00));
						}else{
							formulario.setCostoEnvPadronR(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2610);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroValesFisR.getCellType()  ){
							formulario.setNroValesFisR(new Double(celdaNroValesFisR.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroValesFisR.getCellType()  ){
							formulario.setNroValesFisR(0L);
						}else{
							formulario.setNroValesFisR(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2620);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaCostoUnitValesDigitR.getCellType()  ){
							formulario.setCostoUnitValesDigitR(new BigDecimal(celdaCostoUnitValesDigitR.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaCostoUnitValesDigitR.getCellType()  ){
							formulario.setCostoUnitValesDigitR(new BigDecimal(0.00));
						}else{
							formulario.setCostoUnitValesDigitR(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2630);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaCostoAtenSolicR.getCellType()  ){
							formulario.setCostoAtenSolicR(new BigDecimal(celdaCostoAtenSolicR.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaCostoAtenSolicR.getCellType()  ){
							formulario.setCostoAtenSolicR(new BigDecimal(0.00));
						}else{
							formulario.setCostoAtenSolicR(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2640);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaCostoAtenConsulR.getCellType()  ){
							formulario.setCostoAtenConsR(new BigDecimal(celdaCostoAtenConsulR.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaCostoAtenConsulR.getCellType()  ){
							formulario.setCostoAtenConsR(new BigDecimal(0.00));
						}else{
							formulario.setCostoAtenConsR(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2650);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroAtenR.getCellType()  ){
							formulario.setNroTotalAtenR(new Double(celdaNroAtenR.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroAtenR.getCellType()  ){
							formulario.setNroTotalAtenR(0L);
						}else{
							formulario.setNroTotalAtenR(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2660);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaCostoPersR.getCellType()  ){
							formulario.setCostoPersonalR(new BigDecimal(celdaCostoPersR.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaCostoPersR.getCellType()  ){
							formulario.setCostoPersonalR(new BigDecimal(0.00));
						}else{
							formulario.setCostoPersonalR(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2670);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaCapacAgentR.getCellType()  ){
							formulario.setCapacAgentR(new BigDecimal(celdaCapacAgentR.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaCapacAgentR.getCellType()  ){
							formulario.setCapacAgentR(new BigDecimal(0.00));
						}else{
							formulario.setCapacAgentR(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2680);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaUtilMatOficR.getCellType()  ){
							formulario.setUtilMatOficR(new BigDecimal(celdaUtilMatOficR.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaUtilMatOficR.getCellType()  ){
							formulario.setUtilMatOficR(new BigDecimal(0.00));
						}else{
							formulario.setUtilMatOficR(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2690);
						}
						
						//PROVINCIA
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpValesEdeP.getCellType()  ){
							formulario.setImpValDesctoEdeP(new BigDecimal(celdaImpValesEdeP.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpValesEdeP.getCellType()  ){
							formulario.setImpValDesctoEdeP(new BigDecimal(0.00));
						}else{
							formulario.setImpValDesctoEdeP(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2700);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpValesNoEdeP.getCellType()  ){
							formulario.setImpValDesctoNoEdeP(new BigDecimal(celdaImpValesNoEdeP.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpValesNoEdeP.getCellType()  ){
							formulario.setImpValDesctoNoEdeP(new BigDecimal(0.00));
						}else{
							formulario.setImpValDesctoNoEdeP(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2710);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroValesImpP.getCellType()  ){
							formulario.setNroValesImpP(new Double(celdaNroValesImpP.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroValesImpP.getCellType()  ){
							formulario.setNroValesImpP(0L);
						}else{
							formulario.setNroValesImpP(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2720);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaTotalRepValesDesctoP.getCellType()  ){
							formulario.setCostoTotalValDesctoP(new BigDecimal(celdaTotalRepValesDesctoP.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaTotalRepValesDesctoP.getCellType()  ){
							formulario.setCostoTotalValDesctoP(new BigDecimal(0.00));
						}else{
							formulario.setCostoTotalValDesctoP(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2730);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroValesRepP.getCellType()  ){
							formulario.setNroValesReptP(new Double(celdaNroValesRepP.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroValesRepP.getCellType()  ){
							formulario.setNroValesReptP(0L);
						}else{
							formulario.setNroValesReptP(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2740);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaTotalRepValesOficP.getCellType()  ){
							formulario.setCostoTotalValOficP(new BigDecimal(celdaTotalRepValesOficP.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaTotalRepValesOficP.getCellType()  ){
							formulario.setCostoTotalValOficP(new BigDecimal(0.00));
						}else{
							formulario.setCostoTotalValOficP(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2750);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroValesEntrP.getCellType()  ){
							formulario.setNroValesEntrP(new Double(celdaNroValesEntrP.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroValesEntrP.getCellType()  ){
							formulario.setNroValesEntrP(0L);
						}else{
							formulario.setNroValesEntrP(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2760);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaTotalEnvPadronP.getCellType()  ){
							formulario.setCostoEnvPadronP(new BigDecimal(celdaTotalEnvPadronP.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaTotalEnvPadronP.getCellType()  ){
							formulario.setCostoEnvPadronP(new BigDecimal(0.00));
						}else{
							formulario.setCostoEnvPadronP(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2770);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroValesFisP.getCellType()  ){
							formulario.setNroValesFisP(new Double(celdaNroValesFisP.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroValesFisP.getCellType()  ){
							formulario.setNroValesFisP(0L);
						}else{
							formulario.setNroValesFisP(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2780);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaCostoUnitValesDigitP.getCellType()  ){
							formulario.setCostoUnitValesDigitP(new BigDecimal(celdaCostoUnitValesDigitP.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaCostoUnitValesDigitP.getCellType()  ){
							formulario.setCostoUnitValesDigitP(new BigDecimal(0.00));
						}else{
							formulario.setCostoUnitValesDigitP(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2790);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaCostoAtenSolicP.getCellType()  ){
							formulario.setCostoAtenSolicP(new BigDecimal(celdaCostoAtenSolicP.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaCostoAtenSolicP.getCellType()  ){
							formulario.setCostoAtenSolicP(new BigDecimal(0.00));
						}else{
							formulario.setCostoAtenSolicP(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2800);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaCostoAtenConsulP.getCellType()  ){
							formulario.setCostoAtenConsP(new BigDecimal(celdaCostoAtenConsulP.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaCostoAtenConsulP.getCellType()  ){
							formulario.setCostoAtenConsP(new BigDecimal(0.00));
						}else{
							formulario.setCostoAtenConsP(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2810);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroAtenP.getCellType()  ){
							formulario.setNroTotalAtenP(new Double(celdaNroAtenP.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroAtenP.getCellType()  ){
							formulario.setNroTotalAtenP(0L);
						}else{
							formulario.setNroTotalAtenP(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2820);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaCostoPersP.getCellType()  ){
							formulario.setCostoPersonalP(new BigDecimal(celdaCostoPersP.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaCostoPersP.getCellType()  ){
							formulario.setCostoPersonalP(new BigDecimal(0.00));
						}else{
							formulario.setCostoPersonalP(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2830);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaCapacAgentP.getCellType()  ){
							formulario.setCapacAgentP(new BigDecimal(celdaCapacAgentP.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaCapacAgentP.getCellType()  ){
							formulario.setCapacAgentP(new BigDecimal(0.00));
						}else{
							formulario.setCapacAgentP(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2840);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaUtilMatOficP.getCellType()  ){
							formulario.setUtilMatOficP(new BigDecimal(celdaUtilMatOficP.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaUtilMatOficP.getCellType()  ){
							formulario.setUtilMatOficP(new BigDecimal(0.00));
						}else{
							formulario.setUtilMatOficR(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2850);
						}
						
						if( FiseConstants.COD_EMPRESA_EDELNOR.equals(codEmpresa) || FiseConstants.COD_EMPRESA_LUZ_SUR.equals(codEmpresa) ){
							//LIMA
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpValesEdeL.getCellType()  ){
								formulario.setImpValDesctoEdeL(new BigDecimal(celdaImpValesEdeL.getNumericCellValue()));
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpValesEdeL.getCellType()  ){
								formulario.setImpValDesctoEdeL(new BigDecimal(0.00));
							}else{
								formulario.setImpValDesctoEdeL(new BigDecimal(0.00));
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2860);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpValesNoEdeL.getCellType()  ){
								formulario.setImpValDesctoNoEdeL(new BigDecimal(celdaImpValesNoEdeL.getNumericCellValue()));
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpValesNoEdeL.getCellType()  ){
								formulario.setImpValDesctoNoEdeL(new BigDecimal(0.00));
							}else{
								formulario.setImpValDesctoNoEdeL(new BigDecimal(0.00));
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2870);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroValesImpL.getCellType()  ){
								formulario.setNroValesImpL(new Double(celdaNroValesImpL.getNumericCellValue()).longValue());
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroValesImpL.getCellType()  ){
								formulario.setNroValesImpL(0L);
							}else{
								formulario.setNroValesImpL(0L);
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2880);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaTotalRepValesDesctoL.getCellType()  ){
								formulario.setCostoTotalValDesctoL(new BigDecimal(celdaTotalRepValesDesctoL.getNumericCellValue()));
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaTotalRepValesDesctoL.getCellType()  ){
								formulario.setCostoTotalValDesctoL(new BigDecimal(0.00));
							}else{
								formulario.setCostoTotalValDesctoL(new BigDecimal(0.00));
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2890);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroValesRepL.getCellType()  ){
								formulario.setNroValesReptL(new Double(celdaNroValesRepL.getNumericCellValue()).longValue());
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroValesRepL.getCellType()  ){
								formulario.setNroValesReptL(0L);
							}else{
								formulario.setNroValesReptL(0L);
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2900);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaTotalRepValesOficL.getCellType()  ){
								formulario.setCostoTotalValOficL(new BigDecimal(celdaTotalRepValesOficL.getNumericCellValue()));
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaTotalRepValesOficL.getCellType()  ){
								formulario.setCostoTotalValOficL(new BigDecimal(0.00));
							}else{
								formulario.setCostoTotalValOficL(new BigDecimal(0.00));
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2910);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroValesEntrL.getCellType()  ){
								formulario.setNroValesEntrL(new Double(celdaNroValesEntrL.getNumericCellValue()).longValue());
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroValesEntrL.getCellType()  ){
								formulario.setNroValesEntrL(0L);
							}else{
								formulario.setNroValesEntrL(0L);
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2920);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaTotalEnvPadronL.getCellType()  ){
								formulario.setCostoEnvPadronL(new BigDecimal(celdaTotalEnvPadronL.getNumericCellValue()));
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaTotalEnvPadronL.getCellType()  ){
								formulario.setCostoEnvPadronL(new BigDecimal(0.00));
							}else{
								formulario.setCostoEnvPadronL(new BigDecimal(0.00));
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2930);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroValesFisL.getCellType()  ){
								formulario.setNroValesFisL(new Double(celdaNroValesFisL.getNumericCellValue()).longValue());
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroValesFisL.getCellType()  ){
								formulario.setNroValesFisL(0L);
							}else{
								formulario.setNroValesFisL(0L);
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2940);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaCostoUnitValesDigitL.getCellType()  ){
								formulario.setCostoUnitValesDigitL(new BigDecimal(celdaCostoUnitValesDigitL.getNumericCellValue()));
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaCostoUnitValesDigitL.getCellType()  ){
								formulario.setCostoUnitValesDigitL(new BigDecimal(0.00));
							}else{
								formulario.setCostoUnitValesDigitL(new BigDecimal(0.00));
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2950);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaCostoAtenSolicL.getCellType()  ){
								formulario.setCostoAtenSolicL(new BigDecimal(celdaCostoAtenSolicL.getNumericCellValue()));
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaCostoAtenSolicL.getCellType()  ){
								formulario.setCostoAtenSolicL(new BigDecimal(0.00));
							}else{
								formulario.setCostoAtenSolicL(new BigDecimal(0.00));
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2960);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaCostoAtenConsulL.getCellType()  ){
								formulario.setCostoAtenConsL(new BigDecimal(celdaCostoAtenConsulL.getNumericCellValue()));
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaCostoAtenConsulL.getCellType()  ){
								formulario.setCostoAtenConsL(new BigDecimal(0.00));
							}else{
								formulario.setCostoAtenConsL(new BigDecimal(0.00));
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2970);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroAtenL.getCellType()  ){
								formulario.setNroTotalAtenL(new Double(celdaNroAtenL.getNumericCellValue()).longValue());
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroAtenL.getCellType()  ){
								formulario.setNroTotalAtenL(0L);
							}else{
								formulario.setNroTotalAtenL(0L);
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2980);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaCostoPersL.getCellType()  ){
								formulario.setCostoPersonalL(new BigDecimal(celdaCostoPersL.getNumericCellValue()));
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaCostoPersL.getCellType()  ){
								formulario.setCostoPersonalL(new BigDecimal(0.00));
							}else{
								formulario.setCostoPersonalL(new BigDecimal(0.00));
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_2990);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaCapacAgentL.getCellType()  ){
								formulario.setCapacAgentL(new BigDecimal(celdaCapacAgentL.getNumericCellValue()));
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaCapacAgentL.getCellType()  ){
								formulario.setCapacAgentL(new BigDecimal(0.00));
							}else{
								formulario.setCapacAgentL(new BigDecimal(0.00));
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_3000);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaUtilMatOficL.getCellType()  ){
								formulario.setUtilMatOficL(new BigDecimal(celdaUtilMatOficL.getNumericCellValue()));
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaUtilMatOficL.getCellType()  ){
								formulario.setUtilMatOficL(new BigDecimal(0.00));
							}else{
								formulario.setUtilMatOficL(new BigDecimal(0.00));
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_3010);
							}
						}

						
						//VALIDACIONES DE OBLIGATOREIDAD
						//validar que siempre que ingrese un valor en la comlumna si se ingreso otro valor
//						if( BigDecimal.ZERO.equals(formulario.getNroBenefEmpadR()) && !BigDecimal.ZERO.equals(formulario.getNroAgentR()) ){
//							cont++;
//							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_300);
//						}else if( BigDecimal.ZERO.equals(formulario.getNroAgentR()) && !BigDecimal.ZERO.equals(formulario.getNroBenefEmpadR()) ){
//							cont++;
//							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_310);
//						}
//						if( BigDecimal.ZERO.equals(formulario.getNroBenefEmpadP()) && !BigDecimal.ZERO.equals(formulario.getNroAgentP()) ){
//							cont++;
//							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_320);
//						}else if( BigDecimal.ZERO.equals(formulario.getNroAgentP()) && !BigDecimal.ZERO.equals(formulario.getNroBenefEmpadP()) ){
//							cont++;
//							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_330);
//						}
//						if( BigDecimal.ZERO.equals(formulario.getNroBenefEmpadL()) && !BigDecimal.ZERO.equals(formulario.getNroAgentL()) ){
//							cont++;
//							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_340);
//						}else if( BigDecimal.ZERO.equals(formulario.getNroAgentL()) && !BigDecimal.ZERO.equals(formulario.getNroBenefEmpadL()) ){
//							cont++;
//							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12_350);
//						}
						///
						
						/**verificar*/
						formulario.setAnioInicioVigencia(Long.parseLong(anioIniVigencia));
						formulario.setAnioFinVigencia(Long.parseLong(anioFinVigencia));
						/***/
						
						if( FiseConstants.BLANCO.equals(sMsg) ){
							
							//
							formulario.setUsuario(user.getLogin());
							formulario.setTerminal(user.getLoginIP());
							formulario.setTipoArchivo(FiseConstants.TIPOARCHIVO_XLS);
							formulario.setNombreArchivo(archivo.getTitle());
							//
							formulario.setEtapa(etapaEdit);
							
							if( codEmpresa.trim().equals(formulario.getCodigoEmpresa().trim()) &&
									anioPres.equals(String.valueOf(formulario.getAnioPresent())) &&
									Long.parseLong(mesPres) == formulario.getMesPresent() &&
									anioIniVigencia.equals(String.valueOf(formulario.getAnioInicioVigencia())) &&
									anioFinVigencia.equals(String.valueOf(formulario.getAnioFinVigencia()))
									){
								if( FiseConstants.FLAG_CARGAEXCEL_FORMULARIONUEVO.equals(flagCarga) ){
									objeto = formato14Service.registrarFormato14BC(formulario);
								}else if( FiseConstants.FLAG_CARGAEXCEL_FORMULARIOMODIFICACION.equals(flagCarga) ){
									FiseFormato14BC formatoModif = new FiseFormato14BC();
									FiseFormato14BCPK id = new FiseFormato14BCPK();
									id.setCodEmpresa(formulario.getCodigoEmpresa());
									id.setAnoPresentacion(formulario.getAnioPresent());
									id.setMesPresentacion(formulario.getMesPresent());
									id.setAnoInicioVigencia(formulario.getAnioInicioVigencia());
									id.setAnoFinVigencia(formulario.getAnioFinVigencia());
									id.setEtapa(formulario.getEtapa());
									formatoModif = formato14Service.obtenerFormato14BCByPK(id);
									objeto = formato14Service.modificarFormato14BC(formulario, formatoModif);
								}
								
							}else{
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_3020);
							}
						}
						
					}else{
						//--logger.warn(mapaErrores.get(FiseConstants.COD_ERROR_F12_20));
						cont++;
						sMsg = sMsg + "No se encuentra la hoja "+FiseConstants.NOMBRE_HOJA_FORMATO14B+" en el archivo cargado";
						throw new Exception("No se encuentra la hoja "+FiseConstants.NOMBRE_HOJA_FORMATO14B+" en el archivo cargado");
					}

				}
					
			}
			is.close();

		} catch (Exception e) {
			logger.error("Error al leer el archivo excel.",e);
			String error = e.getMessage();
			sMsg = sMsg+error;	        	
			cont++;
			MensajeErrorBean errorBean = new MensajeErrorBean();
			errorBean.setId(cont);
			errorBean.setDescripcion(error);
			listaError.add(errorBean);
		}finally{
			StreamUtil.cleanUp(is);
		}
		formatoMensaje.setMensajeError(sMsg);
		formatoMensaje.setFiseFormato14BC(objeto);
		if(listaError.size()>0)
			formatoMensaje.setListaMensajeError(listaError);
		
		return formatoMensaje;
	}

public Formato14BMensajeBean readTxtFile(FileEntry archivo, UploadPortletRequest uploadPortletRequest, User user, String flagCarga, String codEmpresaEdit, String anioPresEdit, String mesPresEdit, String anioIniVigEdit, String anioFinVigEdit, String etapaEdit) {
	
	//---------------------
	//FLAG CARGA:
	//	3: para registros nuevos
	//	4: para registros modificados
	//---------------------
	Formato14BMensajeBean formatoMensaje = new Formato14BMensajeBean();
	InputStream is=null;
	FiseFormato14BC objeto = null;
	List<MensajeErrorBean> listaError = new ArrayList<MensajeErrorBean>();
	String sMsg = "";
	int cont = 0;
	List<CfgCampo> listaCampo = null;
	try{
		CfgTabla tabla = new CfgTabla();
		tabla.setIdTabla(FiseConstants.ID_TABLA_FORMATO14B);
		listaCampo = campoService.listarCamposByTabla(tabla);
		if( listaCampo != null ){
			int longitudMaxima = campoService.longitudMaximaRegistro(listaCampo);
			int posicionCodEmpresa = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_COD_EMPRESA);
			int posicionAnioPresentacion = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_ANO_PRESENTACION);
			int posicionMesPresentacion = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_MES_PRESENTACION);
			int posicionAnioInicioVigencia = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_ANO_INICIO_VIGENCIA);
			int posicionAnioFinVigencia = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_ANO_FIN_VIGENCIA);
			int posicionZonaBenef = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_ZONA_BENEFICIARIO);
			
			int posicionImpValesEde = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_IMPRESION_VAL_DSCTO_CLI_DIS_EL_F14B);
			int posicionImpValesNoEde = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_IMPRE_VAL_DSCTO_CLI_NO_DIS_EL_F14B);
			int posicionTotalImp = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_COSTO_TOTAL_IMPRESION_F14B);
			int posicionNroValesImp = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_NUMERO_VALES_IMPRESO_F14B);
			int posicionCostoUnitImp = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_COSTO_UNITARIO_IMPRESION_VALES_F14B);
			int posicionTotalRepValesDescto = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_COSTO_REPARTO_VALES_DESCUENTO_F14B);
			int posicionNroValesRep = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_NUMERO_VALES_REPARTIDOS_F14B);
			int posicionCostoUnitValesRep = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_COSTO_UNIT_REPRTO_VALE_DOMICI_F14B);
			int posicionTotalRepValesOfic = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_COSTO_TOT_REPRTO_VAL_DIS_EL_F14B);
			int posicionNroValesEntr = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_NUMERO_VALES_ENTREGADOS_F14B);
			int posicionCostoUnitValesEntr = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_COSTO_UNIT_ENTREGA_VAL_DIS_EL_F14B);
			int posicionTotalEnvPadron = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_COSTO_ENVIAR_PADRON_VAL_CANJE_F14B);
			int posicionNroValesFis = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_NUMERO_VALES_FISICOS_EMITIDOS_F14B);
			int posicionCostoUnitValesFis = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_COSTO_UNIT_CANJE_LIQ_VAL_FISI_F14B);
			int posicionCostoUnitValesDigit = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_COSTO_UNIT_CANJE_VAL_DIGITAL_F14B);
			int posicionCostoAtenSolic = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_COSTO_ATENCION_SOLICITUDES_F14B);
			int posicionCostoAtenConsul = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_COSTO_ATENCION_CONSULTA_RECLA_F14B);
			int posicionTotalAtencion = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_COSTO_TOTAL_ATENCION_F14B);
			int posicionNroAten = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_NUMERO_TOTAL_ATENCION_F14B);
			int posicionCostoUnitAten = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_COSTO_UNITARIO_POR_ATENCION_F14B);
			int posicionCostoPers = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_COSTO_PERSONAL_F14B);
			int posicionCapacAgent = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_CAPACITACION_AGENTES_AUT_GLP_F14B);
			int posicionUtilMatOfic = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_UTILES_MATERIALES_OFICINA_F14B);
			//int posicionTotalGestion = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_COSTO_TOTAL_GESTION_ADMINISTRA_F14B);
			
			String sCurrentLine;
			is=uploadPortletRequest.getFileAsStream("archivoTxt");
			
			String nombreIdeal = FormatoUtil.nombreArchivoCargaTxt(Long.parseLong(anioPresEdit), Long.parseLong(mesPresEdit), codEmpresaEdit, FiseConstants.TIPO_FORMATO_14B);
			if( nombreIdeal.trim().equals(archivo.getDescription().trim()) ){
				
				int BUFFER_SIZE = 8192;
				BufferedReader br = new BufferedReader( new InputStreamReader(is),BUFFER_SIZE);
				List<String> listaDetalleTxt= new ArrayList<String>();
				sCurrentLine = br.readLine();
				while(sCurrentLine!=null){
					cont++;
					if( sCurrentLine.length()>0 ){
						if( sCurrentLine.length() == longitudMaxima ){
							listaDetalleTxt.add(sCurrentLine);
						}else{
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_3030);
						}
					}/*else{
						cont++;
						sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_3040);
					}*/
					sCurrentLine = br.readLine();
					if( cont>3 ){
						cont++;
						sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_3050);
						break;
					}
				}
				if(cont==0){
					cont++;
					sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_3040);
				}
				
				String key1,key2,key3,key4,key5="";//,key6="";
				if( listaDetalleTxt.size()>0 ){
					key1 = listaDetalleTxt.get(0).substring(0, posicionCodEmpresa).trim();
					key2 = listaDetalleTxt.get(0).substring(posicionCodEmpresa, posicionAnioPresentacion).trim();
					key3 = listaDetalleTxt.get(0).substring(posicionAnioPresentacion, posicionMesPresentacion).trim();
					key4 = listaDetalleTxt.get(0).substring(posicionMesPresentacion, posicionAnioInicioVigencia).trim();
					key5 = listaDetalleTxt.get(0).substring(posicionAnioInicioVigencia, posicionAnioFinVigencia).trim();
					boolean process = true;
					Set<String> zonaSet = new java.util.HashSet<String>();
					for (String s : listaDetalleTxt) {
						String codEmp = s.substring(0, posicionCodEmpresa).trim();
						String anioPres = s.substring(posicionCodEmpresa, posicionAnioPresentacion).trim();
						String mesPres = s.substring(posicionAnioPresentacion, posicionMesPresentacion) ;
						String anioIni = s.substring(posicionMesPresentacion, posicionAnioInicioVigencia) ;
						String anioFin = s.substring(posicionAnioInicioVigencia, posicionAnioFinVigencia) ;
						
						String zonaBenef = s.substring(posicionAnioFinVigencia, posicionZonaBenef).trim();
						if( key1.equals(codEmp) && key2.equals(anioPres) && key3.equals(mesPres) && key4.equals(anioIni) && key5.equals(anioFin) &&
								(FiseConstants.ZONABENEF_RURAL_COD_STRING.equals(zonaBenef) ||
								FiseConstants.ZONABENEF_PROVINCIA_COD_STRING.equals(zonaBenef) ||
								FiseConstants.ZONABENEF_LIMA_COD_STRING.equals(zonaBenef) )
								){
							if( zonaSet.contains(zonaBenef) ){
								process=false;
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_3060);
								break;
							}else{
								zonaSet.add(zonaBenef);
								process=true;
							}
						}else{
							process=false;
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_3070);
							break;
						}
					}
					if(process){
						
						Formato14BCBean formulario = new Formato14BCBean();
						//nuevamente recorremos la lista para armar los objetos
						formulario.setCodigoEmpresa(key1);
						formulario.setAnioPresent(Long.parseLong(key2));
						formulario.setMesPresent(Long.parseLong(key3));
						formulario.setAnioInicioVigencia(Long.parseLong(key4));
						formulario.setAnioFinVigencia(Long.parseLong(key5));

						if( codEmpresaEdit.trim().equals(formulario.getCodigoEmpresa().trim()) &&
								anioPresEdit.equals(String.valueOf(formulario.getAnioPresent())) &&
								Long.parseLong(mesPresEdit)==formulario.getMesPresent() &&
								anioIniVigEdit.equals(String.valueOf(formulario.getAnioInicioVigencia())) &&
								anioFinVigEdit.equals(String.valueOf(formulario.getAnioFinVigencia())) 
								){
							
							//
							for (String s : listaDetalleTxt) {
								String zonaBenef = s.substring(posicionAnioFinVigencia, posicionZonaBenef).trim();
								
								String impValesEde = s.substring(posicionZonaBenef, posicionImpValesEde).trim();
								String impValesNoEde = s.substring(posicionImpValesEde, posicionImpValesNoEde).trim();
								String nroValesImp = s.substring(posicionTotalImp, posicionNroValesImp).trim();
								String totalRepValesDescto = s.substring(posicionCostoUnitImp, posicionTotalRepValesDescto).trim();
								String nroValesRep = s.substring(posicionTotalRepValesDescto, posicionNroValesRep).trim();
								String totalRepValesOfic = s.substring(posicionCostoUnitValesRep, posicionTotalRepValesOfic).trim();
								String nroValesEntr = s.substring(posicionTotalRepValesOfic, posicionNroValesEntr).trim();
								String totalEnvPadron = s.substring(posicionCostoUnitValesEntr, posicionTotalEnvPadron).trim();
								String nroValesFis = s.substring(posicionTotalEnvPadron, posicionNroValesFis).trim();
								String costoUnitValesDigit = s.substring(posicionCostoUnitValesFis, posicionCostoUnitValesDigit).trim();
								String costoAtenSolic = s.substring(posicionCostoUnitValesDigit, posicionCostoAtenSolic).trim();
								String costoAtenConsul = s.substring(posicionCostoAtenSolic, posicionCostoAtenConsul).trim();
								String nroAten = s.substring(posicionTotalAtencion, posicionNroAten).trim();
								String costoPers = s.substring(posicionCostoUnitAten, posicionCostoPers).trim();
								String capacAgent = s.substring(posicionCostoPers, posicionCapacAgent).trim();
								String utilMatOfic = s.substring(posicionCapacAgent, posicionUtilMatOfic).trim();
								
								//validaciones de consistencia
								if( !FormatoUtil.validarCampoBigDecimalPositivoTxt(impValesEde) ){
									//el campo impValesEde no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_3100);
								}
								if( !FormatoUtil.validarCampoBigDecimalPositivoTxt(impValesNoEde) ){
									//el campo impValesNoEde no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_3110);
								}
								if( !FormatoUtil.validarCampoLongEnteroPositivoTxt(nroValesImp) ){
									//el campo nroValesImp no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_3120);
								}
								if( !FormatoUtil.validarCampoBigDecimalPositivoTxt(totalRepValesDescto) ){
									//el campo totalRepValesDescto no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_3130);
								}
								if( !FormatoUtil.validarCampoLongEnteroPositivoTxt(nroValesRep) ){
									//el campo nroValesRep no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_3140);
								}
								if( !FormatoUtil.validarCampoBigDecimalPositivoTxt(totalRepValesOfic) ){
									//el campo totalRepValesOfic no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_3150);
								}
								if( !FormatoUtil.validarCampoLongEnteroPositivoTxt(nroValesEntr) ){
									//el campo nroValesEntr no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_3160);
								}
								if( !FormatoUtil.validarCampoBigDecimalPositivoTxt(totalEnvPadron) ){
									//el campo totalEnvPadron no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_3170);
								}
								if( !FormatoUtil.validarCampoLongEnteroPositivoTxt(nroValesFis) ){
									//el campo nroValesFis no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_3180);
								}
								if( !FormatoUtil.validarCampoBigDecimalPositivoTxt(costoUnitValesDigit) ){
									//el campo costoUnitValesDigit no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_3190);
								}
								if( !FormatoUtil.validarCampoBigDecimalPositivoTxt(costoAtenSolic) ){
									//el campo costoAtenSolic no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_3200);
								}
								if( !FormatoUtil.validarCampoBigDecimalPositivoTxt(costoAtenConsul) ){
									//el campo costoAtenConsul no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_3210);
								}
								if( !FormatoUtil.validarCampoLongEnteroPositivoTxt(nroAten) ){
									//el campo nroAten no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_3220);
								}
								if( !FormatoUtil.validarCampoBigDecimalPositivoTxt(costoPers) ){
									//el campo costoPers no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_3230);
								}
								if( !FormatoUtil.validarCampoBigDecimalPositivoTxt(capacAgent) ){
									//el campo capacAgent no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_3240);
								}
								if( !FormatoUtil.validarCampoBigDecimalPositivoTxt(utilMatOfic) ){
									//el campo utilMatOfic no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_3250);
								}
								
								
								//
								if( FiseConstants.BLANCO.equals(sMsg) ){
									if( FiseConstants.ZONABENEF_RURAL_COD == Long.parseLong(zonaBenef) ){
										formulario.setImpValDesctoEdeR(new BigDecimal(impValesEde));
										formulario.setImpValDesctoNoEdeR(new BigDecimal(impValesNoEde));
										formulario.setNroValesImpR(Long.parseLong(nroValesImp));
										formulario.setCostoTotalValDesctoR(new BigDecimal(totalRepValesDescto));
										formulario.setNroValesReptR(Long.parseLong(nroValesRep));
										formulario.setCostoTotalValOficR(new BigDecimal(totalRepValesOfic));
										formulario.setNroValesEntrR(Long.parseLong(nroValesEntr));
										formulario.setCostoEnvPadronR(new BigDecimal(totalEnvPadron));
										formulario.setNroValesFisR(Long.parseLong(nroValesFis));
										formulario.setCostoUnitValesDigitR(new BigDecimal(costoUnitValesDigit));
										formulario.setCostoAtenSolicR(new BigDecimal(costoAtenSolic));
										formulario.setCostoAtenConsR(new BigDecimal(costoAtenConsul));
										formulario.setNroTotalAtenR(Long.parseLong(nroAten));
										formulario.setCostoPersonalR(new BigDecimal(costoPers));
										formulario.setCapacAgentR(new BigDecimal(capacAgent));
										formulario.setUtilMatOficR(new BigDecimal(utilMatOfic));
										
									}else if( FiseConstants.ZONABENEF_PROVINCIA_COD == Long.parseLong(zonaBenef) ){
										formulario.setImpValDesctoEdeP(new BigDecimal(impValesEde));
										formulario.setImpValDesctoNoEdeP(new BigDecimal(impValesNoEde));
										formulario.setNroValesImpP(Long.parseLong(nroValesImp));
										formulario.setCostoTotalValDesctoP(new BigDecimal(totalRepValesDescto));
										formulario.setNroValesReptP(Long.parseLong(nroValesRep));
										formulario.setCostoTotalValOficP(new BigDecimal(totalRepValesOfic));
										formulario.setNroValesEntrP(Long.parseLong(nroValesEntr));
										formulario.setCostoEnvPadronP(new BigDecimal(totalEnvPadron));
										formulario.setNroValesFisP(Long.parseLong(nroValesFis));
										formulario.setCostoUnitValesDigitP(new BigDecimal(costoUnitValesDigit));
										formulario.setCostoAtenSolicP(new BigDecimal(costoAtenSolic));
										formulario.setCostoAtenConsP(new BigDecimal(costoAtenConsul));
										formulario.setNroTotalAtenP(Long.parseLong(nroAten));
										formulario.setCostoPersonalP(new BigDecimal(costoPers));
										formulario.setCapacAgentP(new BigDecimal(capacAgent));
										formulario.setUtilMatOficP(new BigDecimal(utilMatOfic));
										
									}else if( FiseConstants.ZONABENEF_LIMA_COD == Long.parseLong(zonaBenef) ){
										
										if( FiseConstants.COD_EMPRESA_EDELNOR.equals(formulario.getCodigoEmpresa()) || FiseConstants.COD_EMPRESA_LUZ_SUR.equals(formulario.getCodigoEmpresa()) ){
											formulario.setImpValDesctoEdeL(new BigDecimal(impValesEde));
											formulario.setImpValDesctoNoEdeL(new BigDecimal(impValesNoEde));
											formulario.setNroValesImpL(Long.parseLong(nroValesImp));
											formulario.setCostoTotalValDesctoL(new BigDecimal(totalRepValesDescto));
											formulario.setNroValesReptL(Long.parseLong(nroValesRep));
											formulario.setCostoTotalValOficL(new BigDecimal(totalRepValesOfic));
											formulario.setNroValesEntrL(Long.parseLong(nroValesEntr));
											formulario.setCostoEnvPadronL(new BigDecimal(totalEnvPadron));
											formulario.setNroValesFisL(Long.parseLong(nroValesFis));
											formulario.setCostoUnitValesDigitL(new BigDecimal(costoUnitValesDigit));
											formulario.setCostoAtenSolicL(new BigDecimal(costoAtenSolic));
											formulario.setCostoAtenConsL(new BigDecimal(costoAtenConsul));
											formulario.setNroTotalAtenL(Long.parseLong(nroAten));
											formulario.setCostoPersonalL(new BigDecimal(costoPers));
											formulario.setCapacAgentL(new BigDecimal(capacAgent));
											formulario.setUtilMatOficL(new BigDecimal(utilMatOfic));
										}else{
											formulario.setImpValDesctoEdeL(new BigDecimal(0));
											formulario.setImpValDesctoNoEdeL(new BigDecimal(0));
											formulario.setNroValesImpL(0L);
											formulario.setCostoTotalValDesctoL(new BigDecimal(0));
											formulario.setNroValesReptL(0L);
											formulario.setCostoTotalValOficL(new BigDecimal(0));
											formulario.setNroValesEntrL(0L);
											formulario.setCostoEnvPadronL(new BigDecimal(0));
											formulario.setNroValesFisL(0L);
											formulario.setCostoUnitValesDigitL(new BigDecimal(0));
											formulario.setCostoAtenSolicL(new BigDecimal(0));
											formulario.setCostoAtenConsL(new BigDecimal(0));
											formulario.setNroTotalAtenL(0L);
											formulario.setCostoPersonalL(new BigDecimal(0));
											formulario.setCapacAgentL(new BigDecimal(0));
											formulario.setUtilMatOficL(new BigDecimal(0));
										}
										
										
									}
								}
								
								
							}
							//
							formulario.setUsuario(user.getLogin());
							formulario.setTerminal(user.getLoginIP());
							formulario.setTipoArchivo(FiseConstants.TIPOARCHIVO_TXT);
							formulario.setNombreArchivo(archivo.getTitle());
							//
							formulario.setEtapa(etapaEdit);
							
							if( FiseConstants.BLANCO.equals(sMsg) ){
								if( FiseConstants.FLAG_CARGATXT_FORMULARIONUEVO.equals(flagCarga) ){
									objeto = formato14Service.registrarFormato14BC(formulario);
								}else if( FiseConstants.FLAG_CARGATXT_FORMULARIOMODIFICACION.equals(flagCarga) ){
									FiseFormato14BC formatoModif = new FiseFormato14BC();
									FiseFormato14BCPK id = new FiseFormato14BCPK();
									id.setCodEmpresa(formulario.getCodigoEmpresa());
									id.setAnoPresentacion(formulario.getAnioPresent());
									id.setMesPresentacion(formulario.getMesPresent());
									id.setAnoInicioVigencia(formulario.getAnioInicioVigencia());
									id.setAnoFinVigencia(formulario.getAnioFinVigencia());
									id.setEtapa(formulario.getEtapa());
									formatoModif = formato14Service.obtenerFormato14BCByPK(id);
									objeto = formato14Service.modificarFormato14BC(formulario, formatoModif);
								}
							}
							
						}else{
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_3080);
						}
						
					}
				}
				is.close();
				
			}else{
				cont++;
				sMsg = sMsg + "El nombre del archivo debe corresponder al periodo a declarar ";
				throw new Exception("El nombre del archivo debe corresponder al periodo a declarar ");
			}
			
			
		}else{
			throw new Exception(mapaErrores.get(FiseConstants.COD_ERROR_F12_290));
		}
		
	}catch (Exception e) {			   
		  //refer.setCondicion(false);				  
		String error = e.getMessage();
		sMsg = sMsg+error;	        	
		System.out.println(error);
		cont++;
		MensajeErrorBean errorBean = new MensajeErrorBean();
		errorBean.setId(cont);
		errorBean.setDescripcion(error);
		listaError.add(errorBean);
		  //throw new Exception(error); 
		  			   
	  }
	//pRequest.getPortletSession().setAttribute("MensajeInformacion", sMsg, PortletSession.APPLICATION_SCOPE);
	formatoMensaje.setFiseFormato14BC(objeto);
	formatoMensaje.setMensajeError(sMsg);
	if(listaError.size()>0)
		formatoMensaje.setListaMensajeError(listaError);
	
	return formatoMensaje;
}



@ResourceMapping("reporte")
public void reporte(ResourceRequest request,ResourceResponse response, @ModelAttribute("formato14BCBean")Formato14BCBean command) {
	try {
		HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
        HttpSession session = httpRequest.getSession();
        
	    JSONArray jsonArray = new JSONArray();	
	    FiseFormato14BC formato = new FiseFormato14BC();
	    
	    Formato14BCBean bean = new Formato14BCBean();
	    
	    String codEmpresa = request.getParameter("codEmpresa").trim();
	    String periodoEnvio = request.getParameter("periodoEnvio").trim();
	    //String flagPeriodoEjecucion = command.getFlagPeriodoEjecucion();
	    String anoPresentacion = "";
	    String mesPresentacion = "";
	    String anoInicioVigencia = "";
	    String anoFinVigencia = "";
	    String etapa = "";
	    
	    String nombreReporte = request.getParameter("nombreReporte").trim();
	    String nombreArchivo = request.getParameter("nombreArchivo").trim();
	    String tipoFormato = FiseConstants.TIPO_FORMATO_14B;
	    String tipoArchivo = request.getParameter("tipoArchivo").trim();
	   
	    session.setAttribute("nombreReporte",nombreReporte);
	    session.setAttribute("nombreArchivo",nombreArchivo);
	    session.setAttribute("tipoFormato",tipoFormato);
	    session.setAttribute("tipoArchivo",tipoArchivo);

	    if( periodoEnvio.length()>6 ){
	    	anoPresentacion = periodoEnvio.substring(0, 4);
	    	mesPresentacion = periodoEnvio.substring(4, 6);
	    	etapa = periodoEnvio.substring(6, periodoEnvio.length());
	    	/*if( "S".equals(flagPeriodoEjecucion) ){
	    		anoInicioVigencia = request.getParameter("anoInicioVigencia");
				anoFinVigencia = request.getParameter("anoFinVigencia");
			}else{
				anoInicioVigencia = anoPresentacion;
				anoFinVigencia = anoPresentacion;
			}*/
	    }
	    
	    anoInicioVigencia = request.getParameter("anoInicioVigencia");
		anoFinVigencia = request.getParameter("anoFinVigencia");
	    
	    FiseFormato14BCPK pk = new FiseFormato14BCPK();
	    pk.setCodEmpresa(codEmpresa);
        pk.setAnoPresentacion(new Long(anoPresentacion));
        pk.setMesPresentacion(new Long(mesPresentacion));
        pk.setAnoInicioVigencia(new Long(anoInicioVigencia));
        pk.setAnoFinVigencia(new Long(anoFinVigencia));
        pk.setEtapa(etapa);

        formato = formato14Service.obtenerFormato14BCByPK(pk);
        if( formato!=null ){
        	//setamos los valores en el bean
        	bean = formato14Service.estructurarFormato14BBeanByFiseFormato14BC(formato);
        	bean.setDescEmpresa(mapaEmpresa.get(formato.getId().getCodEmpresa()));
        	bean.setDescMesPresentacion(fiseUtil.getMapaMeses().get(formato.getId().getMesPresentacion()));
        	//
        	session.setAttribute("mapa", formato14Service.mapearParametrosFormato14B(bean));
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

/**Validacion de Formato**/

@ResourceMapping("validacion")
	public void validacion(ModelMap model, ResourceRequest request,ResourceResponse response,@ModelAttribute("formato14BCBean")Formato14BCBean command) {
	//JSONObject jsonObj = new JSONObject();
	HttpServletRequest req = PortalUtil.getHttpServletRequest(request);	        
    HttpSession session = req.getSession();
	
	ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
	try {
		FiseFormato14BC formato = new FiseFormato14BC();
		
		JSONArray jsonArray = new JSONArray();
			
		String codEmpresa = request.getParameter("codEmpresa").trim();
	    String periodoEnvio = request.getParameter("periodoEnvio").trim();
	    //String flagPeriodoEjecucion = command.getFlagPeriodoEjecucion();
	    String anoPresentacion = "";
	    String mesPresentacion = "";
	    String anoInicioVigencia = "";
	    String anoFinVigencia = "";
	    String etapa = "";
	    
	    if( periodoEnvio.length()>6 ){
	    	anoPresentacion = periodoEnvio.substring(0, 4);
	    	mesPresentacion = periodoEnvio.substring(4, 6);
	    	etapa = periodoEnvio.substring(6, periodoEnvio.length());
	    	/*if( "S".equals(flagPeriodoEjecucion) ){
	    		anoInicioVigencia = request.getParameter("anoInicioVigencia");
				anoFinVigencia = request.getParameter("anoFinVigencia");
			}else{
				anoInicioVigencia = anoPresentacion;
				anoFinVigencia = anoPresentacion;
			}*/
	    }
	    anoInicioVigencia = request.getParameter("anoInicioVigencia");
		anoFinVigencia = request.getParameter("anoFinVigencia");
	    
	    FiseFormato14BCPK pk = new FiseFormato14BCPK();
		pk.setCodEmpresa(codEmpresa);
		pk.setAnoPresentacion(new Long(anoPresentacion));
        pk.setMesPresentacion(new Long(mesPresentacion));
        pk.setAnoInicioVigencia(new Long(anoInicioVigencia));
        pk.setAnoFinVigencia(new Long(anoFinVigencia));
        pk.setEtapa(etapa);
	        
	    formato = formato14Service.obtenerFormato14BCByPK(pk);
	    if( formato!=null ){
	    	//int cont=0;
	    	Formato14Generic formato14Generic = new Formato14Generic(formato);
	    	int i = commonService.validarFormatos_14(formato14Generic, FiseConstants.NOMBRE_FORMATO_14B, themeDisplay.getUser().getLogin(), themeDisplay.getUser().getLogin());
		    if(i==0){
		    	cargarListaObservaciones(formato.getFiseFormato14BDs());
		    	//model.addAttribute("listaObservaciones", listaObservaciones);
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
		    	fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_FORMATO_VAL, FiseConstants.NOMBRE_EXCEL_VALIDACION_F14B, FiseConstants.NOMBRE_HOJA_VALIDACION, listaObservaciones);
		    	
		    	//jsonObj.put("resultado", "OK");
		    }else{
		    	//jsonObj.put("resultado", "Error");
		    }
	    }
	    
	    response.setContentType("application/json");
	    PrintWriter pw = response.getWriter();
	    //pw.write(jsonObj.toString());
	    logger.info(jsonArray.toString());
	    pw.write(jsonArray.toString());
	    pw.flush();
	    pw.close();
	    
	} catch (Exception e) {
		e.printStackTrace();
	}
}

@ResourceMapping("reporteValidacion")
public void reporteValidacion(ResourceRequest request,ResourceResponse response,@ModelAttribute("formato14BCBean")Formato14BCBean command) {
	try {
		HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
        HttpSession session = httpRequest.getSession();
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        
	    JSONArray jsonArray = new JSONArray();	
	    
	    String nombreReporte = request.getParameter("nombreReporte").trim();
	    String nombreArchivo = request.getParameter("nombreArchivo").trim();
	    String tipoFormato = FiseConstants.TIPO_FORMATO_VAL;
	    String tipoArchivo = request.getParameter("tipoArchivo").trim();
	    
	    String anioInicioVigencia = command.getAnioInicioVigenciaHidden();
		String anioFinVigencia = command.getAnioFinVigenciaHidden();
	   
	    session.setAttribute("nombreReporte",nombreReporte);
	    session.setAttribute("nombreArchivo",nombreArchivo);
	    session.setAttribute("tipoFormato",tipoFormato);
	    session.setAttribute("tipoArchivo",tipoArchivo);
	    
	    if( listaObservaciones!=null ){
	    	session.setAttribute("lista", listaObservaciones);
	    }
        
	    //add
	    String codEmpresa = request.getParameter("codEmpresa");
	    String periodoEnvio = request.getParameter("periodoEnvio").trim();
	    String anoPresentacion = "";
	    String mesPresentacion = "";
	    String etapa = "";
	    if( periodoEnvio.length()>6 ){
	    	anoPresentacion = periodoEnvio.substring(0, 4);
	    	mesPresentacion = periodoEnvio.substring(4, 6);
	    	etapa = periodoEnvio.substring(6,periodoEnvio.length());
	    }
	    CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14B);
    	String descripcionFormato = "";
    	if( tabla!=null ){
    		descripcionFormato = tabla.getDescripcionTabla();
    	}
    	Map<String, Object> mapa = new HashMap<String, Object>();
	    mapa.put(FiseConstants.PARAM_IMG_LOGOTIPO, session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg"));
		mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
		mapa.put(FiseConstants.PARAM_ANO_PRES_F14A, Long.parseLong(anoPresentacion));
	   	mapa.put(FiseConstants.PARAM_DESC_MES_PRES_F14A, fiseUtil.getMapaMeses().get(Long.parseLong(mesPresentacion)));
	   	mapa.put(FiseConstants.PARAM_USUARIO, themeDisplay.getUser().getLogin());
		mapa.put(FiseConstants.PARAM_NOMBRE_FORMATO, descripcionFormato);
	   	mapa.put(FiseConstants.PARAM_NRO_OBSERVACIONES, (listaObservaciones!=null && !listaObservaciones.isEmpty())?listaObservaciones.size():0);
	   	
	   	mapa.put(FiseConstants.PARAM_ANO_INICIO_VIGENCIA, (anioInicioVigencia!=null && !anioInicioVigencia.equals("")) ?Long.parseLong(anioInicioVigencia):0);
		mapa.put(FiseConstants.PARAM_ANO_FIN_VIGENCIA, (anioFinVigencia!=null && !anioFinVigencia.equals(""))?Long.parseLong(anioFinVigencia):0);
	  //add
	   	mapa.put(FiseConstants.PARAM_DESC_EMPRESA, mapaEmpresa.get(FormatoUtil.rellenaDerecha(codEmpresa, ' ', 4)) );
	   	mapa.put(FiseConstants.PARAM_ETAPA, etapa);
	   	
	   	session.setAttribute("mapa", mapa);
	    //
	    
	    response.setContentType("application/json");
	    PrintWriter pw = response.getWriter();
	    pw.write(jsonArray.toString());
	    pw.flush();
	    pw.close();
	}catch (Exception e) {
		e.printStackTrace();
	}
}

@ResourceMapping("envioDefinitivo")
public void envioDefinitivo(ResourceRequest request,ResourceResponse response,@ModelAttribute("formato14BCBean")Formato14BCBean command) {
	 FiseFormato14BC formato =null;
	 //FiseFormato14BC formatoActualizar =null;	 
	try {		
		//List<byte[]> listaBytes = new ArrayList<byte[]>();		
		HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
        HttpSession session = httpRequest.getSession();
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        JSONObject jsonObj = new JSONObject();
	    //FileEntry archivo=null;
	    List<FileEntryJSP> listaArchivo = new ArrayList<FileEntryJSP>();    
	    
	    Formato14BCBean bean = new Formato14BCBean();
	    Map<String, Object> mapa = null;
	    String directorio = null;
	   //cambios		    
	    boolean valorFormato = false;
	    boolean valorActa = false;			
		String respuestaEmail ="";	
	    //OutputStream outputStream = response.getPortletOutputStream();
	    
	    String codEmpresa = request.getParameter("codEmpresa").trim();
	    String periodoEnvio = request.getParameter("periodoEnvio").trim();
	    //String flagPeriodoEjecucion = command.getFlagPeriodoEjecucion();
	    String anoPresentacion = "";
	    String mesPresentacion = "";
	    String anoInicioVigencia = "";
	    String anoFinVigencia = "";
	    String etapa = "";
	    
	    String nombreReporte = request.getParameter("nombreReporte").trim();
	    String nombreArchivo = request.getParameter("nombreArchivo").trim();
	    //String tipoFormato = FiseConstants.TIPO_FORMATO_14B;
	    //String tipoArchivo = request.getParameter("tipoArchivo").trim();

	    if( periodoEnvio.length()>6 ){
	    	anoPresentacion = periodoEnvio.substring(0, 4);
	    	mesPresentacion = periodoEnvio.substring(4, 6);
	    	etapa = periodoEnvio.substring(6, periodoEnvio.length());
	    	/*if( "S".equals(flagPeriodoEjecucion) ){
	    		anoInicioVigencia = request.getParameter("anoInicioVigencia");
				anoFinVigencia = request.getParameter("anoFinVigencia");
			}else{
				anoInicioVigencia = anoPresentacion;
				anoFinVigencia = anoPresentacion;
			}*/
	    }
	    anoInicioVigencia = request.getParameter("anoInicioVigencia");
		anoFinVigencia = request.getParameter("anoFinVigencia");
	    
	    FiseFormato14BCPK pk = new FiseFormato14BCPK();
	    pk.setCodEmpresa(codEmpresa);
        pk.setAnoPresentacion(new Long(anoPresentacion));
        pk.setMesPresentacion(new Long(mesPresentacion));
        pk.setAnoInicioVigencia(new Long(anoInicioVigencia));
        pk.setAnoFinVigencia(new Long(anoFinVigencia));
        pk.setEtapa(etapa);

        formato = formato14Service.obtenerFormato14BCByPK(pk);
        if( formato!=null ){
        	bean = formato14Service.estructurarFormato14BBeanByFiseFormato14BC(formato);
        	bean.setDescEmpresa(mapaEmpresa.get(formato.getId().getCodEmpresa()));
        	bean.setDescMesPresentacion(fiseUtil.getMapaMeses().get(formato.getId().getMesPresentacion()));
        	mapa = formato14Service.mapearParametrosFormato14B(bean);
        	
        	CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14B);
        	String descripcionFormato = "";
        	if( tabla!=null ){
        		descripcionFormato = tabla.getDescripcionTabla();
        	}
        	
        	Formato14Generic formato14Generic = new Formato14Generic(formato);
        	int i = commonService.validarFormatos_14(formato14Generic, FiseConstants.NOMBRE_FORMATO_14B, themeDisplay.getUser().getLogin(), themeDisplay.getUser().getLoginIP());
		    if(i==0){
		    	cargarListaObservaciones(formato.getFiseFormato14BDs());
		    }
		  //cambios flag observacion
		    String flagEnvioObs = "";
			logger.info("Periodo para comparar :" +periodoEnvio);
			List<FisePeriodoEnvio> listaPeriodoEnvio = periodoService.listarFisePeriodoEnvioMesAnioEtapa(codEmpresa, FiseConstants.NOMBRE_FORMATO_14B);
			for (FisePeriodoEnvio p : listaPeriodoEnvio) {
				if(periodoEnvio.equals(p.getCodigoItem()) ){
					 flagEnvioObs = p.getFlagEnvioConObservaciones();
					break;
				}
			}
			if(listaObservaciones!=null && listaObservaciones.size()>0 &&
		    		FiseConstants.PERIODO_CON_ENVIO_OBS_NO.equals(flagEnvioObs)){
		    	 jsonObj.put("resultado", "OBSERVACION");	
		    }else{
		    	if(mapa!=null){
	   		   		mapa.put(FiseConstants.PARAM_IMG_LOGOTIPO, session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg"));
					mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
					mapa.put(FiseConstants.PARAM_USUARIO, themeDisplay.getUser().getLogin());
					mapa.put(FiseConstants.PARAM_NOMBRE_FORMATO, descripcionFormato);
					mapa.put(FiseConstants.PARAM_FECHA_ENVIO, FechaUtil.obtenerFechaActual());
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
							FiseConstants.TIPO_FORMATO_14B, 
							formato.getId().getCodEmpresa(), 
							formato.getId().getAnoPresentacion(), 
							formato.getId().getMesPresentacion(), 
							formato.getId().getEtapa(), 
							FechaUtil.fecha_DD_MM_YYYY(FechaUtil.obtenerFechaActual()));
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
					mapa.put(FiseConstants.PARAM_ETAPA, formato.getId().getEtapa());
	    	   }
	    	   
	    	   
		        /**REPORTE FORMATO 14B*/
		       nombreReporte = "formato14B";
		       nombreArchivo = nombreReporte;
		       directorio =  "/reports/"+nombreReporte+".jasper";
		       File reportFile = new File(session.getServletContext().getRealPath(directorio));
		       byte[] bytes = null;
		       bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), mapa, new JREmptyDataSource());
		       if (bytes != null) {
		    	   //session.setAttribute("bytes1", bytes);
		    	   String nombre = FormatoUtil.nombreIndividualFormato(formato.getId().getCodEmpresa(), formato.getId().getAnoPresentacion(), formato.getId().getMesPresentacion(), FiseConstants.TIPO_FORMATO_14B);
		    	   //String nombre= nombreArchivo+FiseConstants.EXTENSIONARCHIVO_PDF;
		    	   FileEntry archivo = fiseUtil.subirDocumentoBytes(request, bytes, "application/pdf", nombre);
		    	   if( archivo!=null ){
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
		    	   //bytes2 = JasperRunManager.runReportToPdf(reportFile2.getPath(), null, new JRBeanCollectionDataSource(listaObservaciones));
			       if (bytes2 != null) {
			    	   //session.setAttribute("bytes2", bytes2);
			    	   String nombre = FormatoUtil.nombreIndividualAnexoObs(formato.getId().getCodEmpresa(), formato.getId().getAnoPresentacion(), formato.getId().getMesPresentacion(), FiseConstants.TIPO_FORMATO_14B);
			    	   //String nombre= nombreArchivo+FiseConstants.EXTENSIONARCHIVO_PDF;
			    	   FileEntry archivo2 = fiseUtil.subirDocumentoBytes(request, bytes2, "application/pdf", nombre);
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
		       if (bytes3 != null) {
		    	   session.setAttribute("bytesActaEnvio", bytes3);
		    	   String nombre = FormatoUtil.nombreIndividualActaRemision(formato.getId().getCodEmpresa(), formato.getId().getAnoPresentacion(), formato.getId().getMesPresentacion(), FiseConstants.TIPO_FORMATO_14B);
		    	   //String nombre= nombreArchivo+FiseConstants.EXTENSIONARCHIVO_PDF;
		    	   FileEntry archivo = fiseUtil.subirDocumentoBytes(request, bytes3, "application/pdf", nombre);
		    	   if( archivo!=null ){
		    		   FileEntryJSP fileEntryJsp = new FileEntryJSP();
		    		   fileEntryJsp.setNombreArchivo(nombre);
		    		   fileEntryJsp.setFileEntry(archivo);
		    		   listaArchivo.add(fileEntryJsp);
		    		   valorActa = true;
		    	   }
		       }	      
	    	   /*Formato14BCBean form = new Formato14BCBean();
	    	   form.setUsuario(themeDisplay.getUser().getLogin());
	    	   form.setTerminal(themeDisplay.getUser().getLoginIP()); */	   
	    	   /**actualizamos  la fecha de envio*/
	    	   String valorActuaizar = "0";
		       if(valorFormato && valorActa){
		    	   //formatoActualizar = formato14Service.modificarEnvioDefinitivoFormato14BC(form, formato);
		    	   valorActuaizar = formato14Service.modificarEnvioDefinitivoFormato14BC(
		    			    themeDisplay.getUser().getLogin(),
		    			    themeDisplay.getUser().getLoginIP(), formato);
		       }
		       if(valorActuaizar.equals("1")){
		    	   if( listaArchivo!=null && listaArchivo.size()>0 ){		    	
		    		   respuestaEmail = fiseUtil.enviarMailsAdjunto(
			    			   request,
			    			   listaArchivo, 
			    			   mapaEmpresa.get(formato.getId().getCodEmpresa()),
			    			   formato.getId().getAnoPresentacion(),
			    			   formato.getId().getMesPresentacion(),
			    			   FiseConstants.TIPO_FORMATO_14B,
			    			   descripcionFormato,
			    			   FiseConstants.FRECUENCIA_BIENAL_DESCRIPCION,
			    			   formato.getId().getAnoInicioVigencia(),
			    			   formato.getId().getAnoFinVigencia());
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
		    }  //fin del else flag obs NO 	   	       
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
		e.printStackTrace();
	}finally{		
		if(formato != null){
			formato = null;
		}
	}
}

public void cargarListaObservaciones(List<FiseFormato14BD> listaDetalle){
	int cont=0;
	listaObservaciones = new ArrayList<MensajeErrorBean>();
	for (FiseFormato14BD detalle : listaDetalle) {
		detalle.setFiseFormato14BDObs(formato14Service.listarFormato14BDObByFormato14BD(detalle));
		List<FiseFormato14BDOb> listaObser = formato14Service.listarFormato14BDObByFormato14BD(detalle);
		for (FiseFormato14BDOb observacion : listaObser) {
			cont++;
			MensajeErrorBean obs = new MensajeErrorBean();
			obs.setId(cont);
			obs.setDescZonaBenef(mapaZonaBenef.get(observacion.getId().getIdZonaBenef()));
			obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
			obs.setDescripcion(mapaErrores.get(observacion.getFiseObservacion().getIdObservacion()));
			listaObservaciones.add(obs);
		}
	}
}

@ResourceMapping("reporteEnvioDefinitivo")
public void reporteEnvioDefinitivo(ResourceRequest request,ResourceResponse response) {
	try {
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
		
	@ResourceMapping("reporteActaEnvioView")
	public void reporteActaEnvio(ResourceRequest request,ResourceResponse response) {
		try {
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
			HttpSession session = httpRequest.getSession();
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			JSONArray jsonArray = new JSONArray();	
			    
			FiseFormato14BC formato = new FiseFormato14BC();
			
			String tipoFormato = FiseConstants.TIPO_FORMATO_ACTAENVIO;
			String tipoArchivo = FiseConstants.FORMATO_EXPORT_PDF;
			
			CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14B);
			String descripcionFormato = "";
			if( tabla!=null ){
				descripcionFormato = tabla.getDescripcionTabla();
			}
			String codEmpresa = request.getParameter("codEmpresa").trim();
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
			anoFinVigencia = request.getParameter("anoFinVigencia");
			    
			String nombreReporte = "actaEnvio";
		    String nombreArchivo = nombreReporte;
			
			FiseFormato14BCPK pk = new FiseFormato14BCPK();
			pk.setCodEmpresa(codEmpresa);
			pk.setAnoPresentacion(new Long(anoPresentacion));
			pk.setMesPresentacion(new Long(mesPresentacion));
			pk.setAnoInicioVigencia(new Long(anoInicioVigencia));
			pk.setAnoFinVigencia(new Long(anoFinVigencia));
			pk.setEtapa(etapa);
			
			formato = formato14Service.obtenerFormato14BCByPK(pk);
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
						FiseConstants.TIPO_FORMATO_14B, 
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
