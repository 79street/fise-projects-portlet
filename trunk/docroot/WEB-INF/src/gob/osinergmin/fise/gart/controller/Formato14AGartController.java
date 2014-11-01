package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.Formato12ACBean;
import gob.osinergmin.fise.bean.Formato14ACBean;
import gob.osinergmin.fise.bean.Formato14AMensajeBean;
import gob.osinergmin.fise.bean.MensajeErrorBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.CfgCampo;
import gob.osinergmin.fise.domain.CfgTabla;
import gob.osinergmin.fise.domain.FiseFormato12AC;
import gob.osinergmin.fise.domain.FiseFormato12ACPK;
import gob.osinergmin.fise.domain.FiseFormato14AC;
import gob.osinergmin.fise.domain.FiseFormato14ACPK;
import gob.osinergmin.fise.domain.FiseFormato14AD;
import gob.osinergmin.fise.domain.FisePeriodoEnvio;
import gob.osinergmin.fise.gart.json.Formato14AGartJSON;
import gob.osinergmin.fise.gart.service.CfgCampoGartService;
import gob.osinergmin.fise.gart.service.FisePeriodoEnvioGartService;
import gob.osinergmin.fise.gart.service.Formato14AGartService;
import gob.osinergmin.fise.util.FormatoUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
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
	@Autowired
	@Qualifier("fisePeriodoEnvioGartServiceImpl")
	FisePeriodoEnvioGartService periodoService;
	@Autowired
	@Qualifier("cfgCampoGartServiceImpl")
	CfgCampoGartService campoService;
	
	List<FiseFormato14AC> listaFormato;
	Map<String, String> mapaEmpresa;
	List<FisePeriodoEnvio> listaPeriodoEnvio;
	Map<String, String> mapaErrores;
	
	@RequestMapping
	public String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse,@ModelAttribute("formato14ACBean")Formato14ACBean command){

		listaPeriodoEnvio = new ArrayList<FisePeriodoEnvio>();
		
		PortletRequest pRequest = (PortletRequest)renderRequest.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
		Formato14AGartJSON obj = new Formato14AGartJSON();
		
		String codEmpresa = (String)pRequest.getPortletSession().getAttribute("codEmpresa", PortletSession.APPLICATION_SCOPE);
		String anioPresentacion = (String)pRequest.getPortletSession().getAttribute("anoPresentacion", PortletSession.APPLICATION_SCOPE);
		String mesPresentacion = (String)pRequest.getPortletSession().getAttribute("mesPresentacion", PortletSession.APPLICATION_SCOPE);
		String anoInicioVigencia = (String)pRequest.getPortletSession().getAttribute("anoInicioVigencia", PortletSession.APPLICATION_SCOPE);
		String anoFinVigencia = (String)pRequest.getPortletSession().getAttribute("anoFinVigencia", PortletSession.APPLICATION_SCOPE);
		String etapa = (String)pRequest.getPortletSession().getAttribute("etapa", PortletSession.APPLICATION_SCOPE);
		String flag = (String)pRequest.getPortletSession().getAttribute("flag", PortletSession.APPLICATION_SCOPE);
		String msgError = (String)pRequest.getPortletSession().getAttribute("mensajeError", PortletSession.APPLICATION_SCOPE);
		//List<MensajeErrorBean> listaError = (List<MensajeErrorBean>)pRequest.getPortletSession().getAttribute("listaError", PortletSession.APPLICATION_SCOPE);
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
	    pRequest.getPortletSession().setAttribute("mensajeError", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("listaError", null, PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("mensajeInformacion", "", PortletSession.APPLICATION_SCOPE);
		
		
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
		
		mapaErrores = fiseUtil.getMapaErrores();
		
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
	
	@ResourceMapping("crud")
	public void crud(ModelMap model, ResourceRequest request,ResourceResponse response, @ModelAttribute("formato14ACBean")Formato14ACBean command) {
 	
		try {
			JSONObject jsonObj = new JSONObject();
			String tipo = request.getParameter("tipo");   		 
			if(tipo.equals(FiseConstants.COD_GET)){//ver editar
				
				FiseFormato14AC formato = new FiseFormato14AC();
				FiseFormato14ACPK pk = new FiseFormato14ACPK();
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
		        
		        formato = formato14Service.obtenerFormato14ACByPK(pk);
		        
		        Formato14AGartJSON obj = new Formato14AGartJSON();
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
				    listaPeriodoEnvio = periodoService.listarFisePeriodoEnvioMesAnioEtapa(codEmpresa, FiseConstants.NOMBRE_FORMATO_12A);
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
		        
		        JSONObject jsonent = new Formato14AGartJSON().asJSONObject(formato,flagPeriodo);
		        logger.info("jsonformato:"+jsonent);
		        jsonObj.put("formato",jsonent);
				jsonObj.put("resultado", "OK");
				
			}else if(tipo.equals(FiseConstants.COD_SAVE)){ 
				try {
					
					Formato14ACBean formulario = new Formato14ACBean();
					
					formulario.setCodigoEmpresa(command.getCodigoEmpresa());
					
					if( command.getPeriodoEnvio().length()>6 ){
						formulario.setAnioPresent(Long.parseLong(command.getPeriodoEnvio().substring(0, 4)));
						formulario.setMesPresent(Long.parseLong(command.getPeriodoEnvio().substring(4, 6)));
						formulario.setEtapa(command.getPeriodoEnvio().substring(6, command.getPeriodoEnvio().length()));
						if( "S".equals(command.getFlagPeriodoEjecucion()) ){
							formulario.setAnioInicioVigencia(command.getAnioInicioVigencia());
							formulario.setAnioFinVigencia(command.getAnioFinVigencia());
						}else{
							formulario.setAnioInicioVigencia(formulario.getAnioPresent());
							formulario.setAnioFinVigencia(formulario.getAnioPresent());
						}
					
					}
					//RURAL
					//formulario.setTotalEmpadR(command.getTotalEmpadR());
					formulario.setImprEsqInvitR(command.getImprEsqInvitR());
					formulario.setImprDeclaJuradaR(command.getImprDeclaJuradaR());
					formulario.setImprFichaVerifR(command.getImprFichaVerifR());
					formulario.setRepartoEsqInvitR(command.getRepartoEsqInvitR());
					formulario.setVerifInfoR(command.getVerifInfoR());
					formulario.setElabArchivoBenefR(command.getElabArchivoBenefR());
					formulario.setDigitFichaBenefR(command.getDigitFichaBenefR());
					//formulario.setTotalDifIniProgR(command.getTotalDifIniProgR());
					formulario.setImprVolantesR(command.getImprVolantesR());
					formulario.setImprAfichesR(command.getImprAfichesR());
					formulario.setRepFolletosR(command.getRepFolletosR());
					formulario.setSpotPublTvR(command.getSpotPublTvR());
					formulario.setSpotPublRadioR(command.getSpotPublRadioR());
					formulario.setNroBenefEmpadR(command.getNroBenefEmpadR());
					//formulario.setCostoUnitEmpadR(command.getCostoUnitEmpadR());
					//
					//formulario.setTotalCostoAgentR(command.getTotalCostoAgentR());
					formulario.setPromConvAgentR(command.getPromConvAgentR());
					formulario.setRegConvAgentR(command.getRegConvAgentR());
					formulario.setImpEntrBandR(command.getImpEntrBandR());
					formulario.setNroAgentR(command.getNroAgentR());
					//formulario.setCostoUnitAgentR(command.getCostoUnitAgentR());
					//PROVINCIA
					//formulario.setTotalEmpadP(command.getTotalEmpadP());
					formulario.setImprEsqInvitP(command.getImprEsqInvitP());
					formulario.setImprDeclaJuradaP(command.getImprDeclaJuradaP());
					formulario.setImprFichaVerifP(command.getImprFichaVerifP());
					formulario.setRepartoEsqInvitP(command.getRepartoEsqInvitP());
					formulario.setVerifInfoP(command.getVerifInfoP());
					formulario.setElabArchivoBenefP(command.getElabArchivoBenefP());
					formulario.setDigitFichaBenefP(command.getDigitFichaBenefP());
					//formulario.setTotalDifIniProgP(command.getTotalDifIniProgP());
					formulario.setImprVolantesP(command.getImprVolantesP());
					formulario.setImprAfichesP(command.getImprAfichesP());
					formulario.setRepFolletosP(command.getRepFolletosP());
					formulario.setSpotPublTvP(command.getSpotPublTvP());
					formulario.setSpotPublRadioP(command.getSpotPublRadioP());
					formulario.setNroBenefEmpadP(command.getNroBenefEmpadP());
					//formulario.setCostoUnitEmpadP(command.getCostoUnitEmpadP());
					//
					//formulario.setTotalCostoAgentP(command.getTotalCostoAgentP());
					formulario.setPromConvAgentP(command.getPromConvAgentP());
					formulario.setRegConvAgentP(command.getRegConvAgentP());
					formulario.setImpEntrBandP(command.getImpEntrBandP());
					formulario.setNroAgentP(command.getNroAgentP());
					//formulario.setCostoUnitAgentP(command.getCostoUnitAgentP());
					//LIMA
					//formulario.setTotalEmpadL(command.getTotalEmpadL());
					formulario.setImprEsqInvitL(command.getImprEsqInvitL());
					formulario.setImprDeclaJuradaL(command.getImprDeclaJuradaL());
					formulario.setImprFichaVerifL(command.getImprFichaVerifL());
					formulario.setRepartoEsqInvitL(command.getRepartoEsqInvitL());
					formulario.setVerifInfoL(command.getVerifInfoL());
					formulario.setElabArchivoBenefL(command.getElabArchivoBenefL());
					formulario.setDigitFichaBenefL(command.getDigitFichaBenefL());
					//formulario.setTotalDifIniProgL(command.getTotalDifIniProgL());
					formulario.setImprVolantesL(command.getImprVolantesL());
					formulario.setImprAfichesL(command.getImprAfichesL());
					formulario.setRepFolletosL(command.getRepFolletosL());
					formulario.setSpotPublTvL(command.getSpotPublTvL());
					formulario.setSpotPublRadioL(command.getSpotPublRadioL());
					formulario.setNroBenefEmpadL(command.getNroBenefEmpadL());
					//formulario.setCostoUnitEmpadL(command.getCostoUnitEmpadL());
					//
					//formulario.setTotalCostoAgentL(command.getTotalCostoAgentL());
					formulario.setPromConvAgentL(command.getPromConvAgentL());
					formulario.setRegConvAgentL(command.getRegConvAgentL());
					formulario.setImpEntrBandL(command.getImpEntrBandL());
					formulario.setNroAgentL(command.getNroAgentL());
					//formulario.setCostoUnitAgentR(command.getCostoUnitAgentR());
					
					ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
					
					formulario.setUsuario(themeDisplay.getUser().getLogin());
					formulario.setTerminal(themeDisplay.getUser().getLoginIP());
					
					formato14Service.registrarFormato14AC(formulario);
					jsonObj.put("resultado", "OK");
	   				
				} catch (Exception e) {
					jsonObj.put("resultado", "Error");
					jsonObj.put("mensaje", e.getMessage());
					logger.error("Error al guardar los datos: "+e.getMessage());
				}   				   				
					 					 				
			}else if(tipo.equals(FiseConstants.COD_UPDATE)){
				logger.info("entro a modificar valores");
				
				try {
					
					FiseFormato14AC formato = new FiseFormato14AC();
					FiseFormato14ACPK pk = new FiseFormato14ACPK();
					String codEmpresa = request.getParameter("codEmpresa");
					String periodoEnvio = request.getParameter("periodoEnvio");
					//String anoPresentacion = request.getParameter("anoPresentacion");
					//String mesPresentacion = request.getParameter("mesPresentacion");
					String flagPeriodoEjecucion = request.getParameter("flagPeriodo");
					String anoInicioVigencia="";
					String anoFinVigencia="";
					if( "S".equals(flagPeriodoEjecucion) ){
						anoInicioVigencia = request.getParameter("anoInicioVigencia");
						anoFinVigencia = request.getParameter("anoFinVigencia");
					}
					String etapa = request.getParameter("etapa");
					
					logger.info("codempresa "+codEmpresa);
			        //logger.info("anopresent "+anoPresentacion);
			        //logger.info("mespresent "+mesPresentacion);
			        logger.info("anoInicioVigencia "+anoInicioVigencia);
			        logger.info("anoFinVigencia "+anoFinVigencia);
			        logger.info("etapa "+etapa);
					
			        
			        Formato14ACBean formulario = new Formato14ACBean();
			        
			      //RURAL
					//formulario.setTotalEmpadR(command.getTotalEmpadR());
					formulario.setImprEsqInvitR(command.getImprEsqInvitR());
					formulario.setImprDeclaJuradaR(command.getImprDeclaJuradaR());
					formulario.setImprFichaVerifR(command.getImprFichaVerifR());
					formulario.setRepartoEsqInvitR(command.getRepartoEsqInvitR());
					formulario.setVerifInfoR(command.getVerifInfoR());
					formulario.setElabArchivoBenefR(command.getElabArchivoBenefR());
					formulario.setDigitFichaBenefR(command.getDigitFichaBenefR());
					//formulario.setTotalDifIniProgR(command.getTotalDifIniProgR());
					formulario.setImprVolantesR(command.getImprVolantesR());
					formulario.setImprAfichesR(command.getImprAfichesR());
					formulario.setRepFolletosR(command.getRepFolletosR());
					formulario.setSpotPublTvR(command.getSpotPublTvR());
					formulario.setSpotPublRadioR(command.getSpotPublRadioR());
					formulario.setNroBenefEmpadR(command.getNroBenefEmpadR());
					//formulario.setCostoUnitEmpadR(command.getCostoUnitEmpadR());
					//
					//formulario.setTotalCostoAgentR(command.getTotalCostoAgentR());
					formulario.setPromConvAgentR(command.getPromConvAgentR());
					formulario.setRegConvAgentR(command.getRegConvAgentR());
					formulario.setImpEntrBandR(command.getImpEntrBandR());
					formulario.setNroAgentR(command.getNroAgentR());
					//formulario.setCostoUnitAgentR(command.getCostoUnitAgentR());
					//PROVINCIA
					//formulario.setTotalEmpadP(command.getTotalEmpadP());
					formulario.setImprEsqInvitP(command.getImprEsqInvitP());
					formulario.setImprDeclaJuradaP(command.getImprDeclaJuradaP());
					formulario.setImprFichaVerifP(command.getImprFichaVerifP());
					formulario.setRepartoEsqInvitP(command.getRepartoEsqInvitP());
					formulario.setVerifInfoP(command.getVerifInfoP());
					formulario.setElabArchivoBenefP(command.getElabArchivoBenefP());
					formulario.setDigitFichaBenefP(command.getDigitFichaBenefP());
					//formulario.setTotalDifIniProgP(command.getTotalDifIniProgP());
					formulario.setImprVolantesP(command.getImprVolantesP());
					formulario.setImprAfichesP(command.getImprAfichesP());
					formulario.setRepFolletosP(command.getRepFolletosP());
					formulario.setSpotPublTvP(command.getSpotPublTvP());
					formulario.setSpotPublRadioP(command.getSpotPublRadioP());
					formulario.setNroBenefEmpadP(command.getNroBenefEmpadP());
					//formulario.setCostoUnitEmpadP(command.getCostoUnitEmpadP());
					//
					//formulario.setTotalCostoAgentP(command.getTotalCostoAgentP());
					formulario.setPromConvAgentP(command.getPromConvAgentP());
					formulario.setRegConvAgentP(command.getRegConvAgentP());
					formulario.setImpEntrBandP(command.getImpEntrBandP());
					formulario.setNroAgentP(command.getNroAgentP());
					//formulario.setCostoUnitAgentP(command.getCostoUnitAgentP());
					//LIMA
					//formulario.setTotalEmpadL(command.getTotalEmpadL());
					formulario.setImprEsqInvitL(command.getImprEsqInvitL());
					formulario.setImprDeclaJuradaL(command.getImprDeclaJuradaL());
					formulario.setImprFichaVerifL(command.getImprFichaVerifL());
					formulario.setRepartoEsqInvitL(command.getRepartoEsqInvitL());
					formulario.setVerifInfoL(command.getVerifInfoL());
					formulario.setElabArchivoBenefL(command.getElabArchivoBenefL());
					formulario.setDigitFichaBenefL(command.getDigitFichaBenefL());
					//formulario.setTotalDifIniProgL(command.getTotalDifIniProgL());
					formulario.setImprVolantesL(command.getImprVolantesL());
					formulario.setImprAfichesL(command.getImprAfichesL());
					formulario.setRepFolletosL(command.getRepFolletosL());
					formulario.setSpotPublTvL(command.getSpotPublTvL());
					formulario.setSpotPublRadioL(command.getSpotPublRadioL());
					formulario.setNroBenefEmpadL(command.getNroBenefEmpadL());
					//formulario.setCostoUnitEmpadL(command.getCostoUnitEmpadL());
					//
					//formulario.setTotalCostoAgentL(command.getTotalCostoAgentL());
					formulario.setPromConvAgentL(command.getPromConvAgentL());
					formulario.setRegConvAgentL(command.getRegConvAgentL());
					formulario.setImpEntrBandL(command.getImpEntrBandL());
					formulario.setNroAgentL(command.getNroAgentL());
					//formulario.setCostoUnitAgentR(command.getCostoUnitAgentR());
			        
			        formulario.setCodigoEmpresa(codEmpresa);
					
					if( periodoEnvio.length()>6 ){
						formulario.setAnioPresent(Long.parseLong(periodoEnvio.substring(0, 4)));
						formulario.setMesPresent(Long.parseLong(periodoEnvio.substring(4, 6)));
						formulario.setEtapa(periodoEnvio.substring(6, periodoEnvio.length()));
						if( "S".equals(flagPeriodoEjecucion) ){
							formulario.setAnioInicioVigencia(Long.parseLong(anoInicioVigencia));
							formulario.setAnioFinVigencia(Long.parseLong(anoFinVigencia));
						}else{
							formulario.setAnioInicioVigencia(formulario.getAnioPresent());
							formulario.setAnioFinVigencia(formulario.getAnioPresent());
						}
					}
					
					pk.setCodEmpresa(formulario.getCodigoEmpresa());
			        pk.setAnoPresentacion(formulario.getAnioPresent());
			        pk.setMesPresentacion(formulario.getMesPresent());
			        pk.setAnoInicioVigencia(formulario.getAnioInicioVigencia());
			        pk.setAnoFinVigencia(formulario.getAnioFinVigencia());
			        pk.setEtapa(formulario.getEtapa());
			        
			        formato = formato14Service.obtenerFormato14ACByPK(pk);
					logger.info("objeto "+formato);
			        formato14Service.modificarFormato14AC(formulario, formato);
					jsonObj.put("resultado", "OK"); 	
				} catch (Exception e) {
					jsonObj.put("resultado", "Error");
					jsonObj.put("mensaje", e.getMessage());
					System.out.println("Error al guardar la tabla fiseformato12A: "+e.getMessage());
				}
			}else if(tipo.equals(FiseConstants.COD_DELETE)){   				
				try {
					FiseFormato14AC formato = new FiseFormato14AC();
					FiseFormato14ACPK pk = new FiseFormato14ACPK();
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
					
			        formato = formato14Service.obtenerFormato14ACByPK(pk);
			        logger.info("valorpobjeto"+formato);
			        
			        //metodo delete
			        formato14Service.eliminarFormato14AC(formato);
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
  	public void cargaPeriodo(ModelMap model, ResourceRequest request,ResourceResponse response,@ModelAttribute("formato14ACBean")Formato14ACBean command){
		try {			
  			response.setContentType("applicacion/json");
  			String codEmpresa = command.getCodigoEmpresa();
  			//String periodoEnvio = command.getPeriodoEnvio();
  			//lo pongo en la lista porque no persiste las colecciones en el command
  			listaPeriodoEnvio = periodoService.listarFisePeriodoEnvioMesAnioEtapa(codEmpresa, FiseConstants.TIPO_FORMATO_12A);
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
  	public void cargaFlagPeriodo(ResourceRequest request,ResourceResponse response,@ModelAttribute("formato14ACBean")Formato14ACBean command){
		try {			
  			response.setContentType("applicacion/json");
  			String periodoEnvio = command.getPeriodoEnvio();
  			JSONObject jsonObj = new JSONObject();
  			for (FisePeriodoEnvio p : listaPeriodoEnvio) {
				if( periodoEnvio.equals(p.getCodigoItem()) ){
					jsonObj.put("flagPeriodoEjecucion", p.getFlagPeriodoEjecucion());
					break;
				}
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
	public void cargarDocumento(ActionRequest request,ActionResponse response){
		
		logger.info("--- cargar documento");
		Formato14AMensajeBean formatoMensaje = new Formato14AMensajeBean();
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(request);
		
		String flagCarga = uploadPortletRequest.getParameter("flagCarga");
    	String codEmpresaNew = uploadPortletRequest.getParameter("s_empresa");
    	String periodoEnvioPresNew = uploadPortletRequest.getParameter("s_periodoenvio_present");
    	String flagPeriodoEjecucion = uploadPortletRequest.getParameter("flagPeriodoEjecucion");
    	
    	String anioPresNew = "";
		String mesPresNew = "";
		String anioIniVigNew = "";
		String anioFinVigNew = "";
		String etapaNew = "";
		
		if(periodoEnvioPresNew!=null && periodoEnvioPresNew.length()>6){
    		anioPresNew = periodoEnvioPresNew.substring(0, 4);
    		mesPresNew = periodoEnvioPresNew.substring(4, 6);
    		etapaNew = periodoEnvioPresNew.substring(6, periodoEnvioPresNew.length());
    		if( "S".equals(flagPeriodoEjecucion) ){
    			anioIniVigNew = uploadPortletRequest.getParameter("i_anioejecuc");
    			anioFinVigNew = uploadPortletRequest.getParameter("s_mes_ejecuc");
			}else{
				anioIniVigNew = anioPresNew;
				anioFinVigNew = anioPresNew;
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
		
		if( formatoMensaje.getFiseFormato14AC()!=null ){
			String codEmpresa = formatoMensaje.getFiseFormato14AC().getId().getCodEmpresa();
			String anoPresentacion = String.valueOf(formatoMensaje.getFiseFormato14AC().getId().getAnoPresentacion());
			String mesPresentacion = String.valueOf(formatoMensaje.getFiseFormato14AC().getId().getMesPresentacion());
			String anoIniVigencia = String.valueOf(formatoMensaje.getFiseFormato14AC().getId().getAnoInicioVigencia());
			String anoFinVigencia = String.valueOf(formatoMensaje.getFiseFormato14AC().getId().getAnoFinVigencia());
			String etapa = String.valueOf(formatoMensaje.getFiseFormato14AC().getId().getEtapa());
			
			pRequest.getPortletSession().setAttribute("codEmpresa", codEmpresa, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("anoPresentacion", anoPresentacion, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("mesPresentacion", mesPresentacion, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("anoInicioVigencia", anoIniVigencia, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("anoFinVigencia", anoFinVigencia, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("etapa", etapa, PortletSession.APPLICATION_SCOPE);
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
			}
			
		}
		if(formatoMensaje.getListaMensajeError()!=null && formatoMensaje.getListaMensajeError().size()>0){
			pRequest.getPortletSession().setAttribute("listaError", formatoMensaje.getListaMensajeError(), PortletSession.APPLICATION_SCOPE);
			pRequest.getPortletSession().setAttribute("mensajeError", formatoMensaje.getMensajeError(), PortletSession.APPLICATION_SCOPE);
		}else{
			pRequest.getPortletSession().setAttribute("mensajeInformacion", "Datos guardados satisfactoriamente.", PortletSession.APPLICATION_SCOPE);
		}
		//limpiamos las variables en sesion utilizados para la edicion de archivos de carga
		pRequest.getPortletSession().setAttribute("codEmpresaEdit", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("anoPresentacionEdit", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("mesPresentacionEdit", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("anoEjecucionEdit", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("mesEjecucionEdit", "", PortletSession.APPLICATION_SCOPE);
	    pRequest.getPortletSession().setAttribute("etapaEdit", "", PortletSession.APPLICATION_SCOPE);
		
	}
	
public Formato14AMensajeBean readExcelFile(FileEntry archivo, User user, String flagCarga, String codEmpresa, 	String anioPres, String mesPres, String anioIniVigencia, String anioFinVigencia, String etapaEdit) {
		
		//---------------------
		//FLAG CARGA:
		//	1: para registros nuevos
		//	2: para registros modificados
		//---------------------
		Formato14AMensajeBean formatoMensaje = new Formato14AMensajeBean();
		
		InputStream is=null;
		FiseFormato14AC objeto = null;
		String sMsg = "";
		List<MensajeErrorBean> listaError = new ArrayList<MensajeErrorBean>();
		int cont = 0;
		
		try {
			if (archivo != null) {
				HSSFWorkbook libro = null;
				try {
					is=archivo.getContentStream();
					libro = new HSSFWorkbook(is);//Se lee libro xls
				} catch (Exception e1) {
					logger.warn(mapaErrores.get(FiseConstants.COD_ERROR_F12_20));
					cont++;
					sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_20);
					throw new Exception(mapaErrores.get(FiseConstants.COD_ERROR_F12_20));
				}
				int nroHojaSelec=0;
				
				if (libro != null) {
					//el excel puede tener varias hojas, se tiene qie leer el total de hojas y encontrar la que necesitemos
					logger.info("nro de hojas:"+ libro.getNumberOfSheets());
					for (int sheetNro = 0; sheetNro < libro.getNumberOfSheets(); sheetNro++){
						logger.info("nombre de hoja "+sheetNro+":"+ libro.getSheetName(sheetNro));
						if( FiseConstants.NOMBRE_HOJA_FORMATO14A.equals(libro.getSheetName(sheetNro)) ){
							nroHojaSelec = sheetNro;
							break;
						}
					}
					logger.info("nro de hoja seleccionada "+nroHojaSelec);
					HSSFSheet hojaF14 = libro.getSheetAt(nroHojaSelec);
					//int nroFilas = hojaF12.getLastRowNum()+1;
					
					HSSFRow filaEmpresa = hojaF14.getRow(FiseConstants.NRO_FILA_CODEMPRESA_FORMATO14A);						
					HSSFRow filaAnioMes = hojaF14.getRow(FiseConstants.NRO_FILA_ANIOMES_FORMATO14A);	
					
					HSSFRow filaImpEsqInvitacion = hojaF14.getRow(FiseConstants.NRO_FILA_IMPESQINV_FORMATO14A);					
					HSSFRow filaImpDeclJurada = hojaF14.getRow(FiseConstants.NRO_FILA_IMPDECLJURADA_FORMATO14A);					
					HSSFRow filaImpFichaVerif = hojaF14.getRow(FiseConstants.NRO_FILA_IMPFICHAVERIF_FORMATO14A);				
					HSSFRow filaRepEsqInvitacion = hojaF14.getRow(FiseConstants.NRO_FILA_REPESQINV_FORMATO14A);	
					HSSFRow filaVerifInformacion = hojaF14.getRow(FiseConstants.NRO_FILA_VERIFINFO_FORMATO14A);
					HSSFRow filaElabArchBeneficiario = hojaF14.getRow(FiseConstants.NRO_FILA_ELABARCHBENEF_FORMATO14A);
					HSSFRow filaDigitFichaBeneficiario = hojaF14.getRow(FiseConstants.NRO_FILA_DIGITFICHABENEF_FORMATO14A);
					HSSFRow filaImpVolantes = hojaF14.getRow(FiseConstants.NRO_FILA_IMPVOLANTES_FORMATO14A);
					HSSFRow filaImpAfiche = hojaF14.getRow(FiseConstants.NRO_FILA_IMPAFICHES_FORMATO14A);
					HSSFRow filaRepFolletos = hojaF14.getRow(FiseConstants.NRO_FILA_REPFOLLETOS_FORMATO14A);
					HSSFRow filaSpotPublicTv = hojaF14.getRow(FiseConstants.NRO_FILA_SPOTPUBLICTV_FORMATO14A);
					HSSFRow filaSpotPublicRadio = hojaF14.getRow(FiseConstants.NRO_FILA_SPOTPUBLICRADIO_FORMATO14A);
					HSSFRow filaNroEmpad = hojaF14.getRow(FiseConstants.NRO_FILA_EMPAD_FORMATO14A);
					HSSFRow filaPromConvenios = hojaF14.getRow(FiseConstants.NRO_FILA_PROMCONVENIOS_FORMATO14A);
					HSSFRow filaRegFirmaConvenios = hojaF14.getRow(FiseConstants.NRO_FILA_REGFIRMACONV_FORMATO14A);
					HSSFRow filaImpBanderola = hojaF14.getRow(FiseConstants.NRO_FILA_IMPENTBANDER_FORMATO14A);	
					HSSFRow filaNroAgentes = hojaF14.getRow(FiseConstants.NRO_FILA_AGENTES_FORMATO14A);
					
					Formato14ACBean formulario = new Formato14ACBean();
					
					HSSFCell celdaEmpresa = filaEmpresa.getCell(FiseConstants.NRO_CELDA_EMPRESA_FORMATO14A);
					HSSFCell celdaAnio = filaAnioMes.getCell(FiseConstants.NRO_CELDA_ANIO_FORMATO14A);
					HSSFCell celdaMes = filaAnioMes.getCell(FiseConstants.NRO_CELDA_MES_FORMATO14A);
					
					HSSFCell celdaImpEsqInvitacionR = filaImpEsqInvitacion.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO14A);
					HSSFCell celdaImpDeclJuradaR = filaImpDeclJurada.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO14A);
					HSSFCell celdaImpFichaVerifR = filaImpFichaVerif.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO14A);
					HSSFCell celdaRepEsqInvitacionR = filaRepEsqInvitacion.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO14A);
					HSSFCell celdaVerifInformacionR = filaVerifInformacion.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO14A);
					HSSFCell celdaElabArchBeneficiarioR = filaElabArchBeneficiario.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO14A);
					HSSFCell celdaDigitFichaBeneficiarioR = filaDigitFichaBeneficiario.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO14A);
					HSSFCell celdaImpVolantesR = filaImpVolantes.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO14A);
					HSSFCell celdaImpAficheR = filaImpAfiche.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO14A);
					HSSFCell celdaRepFolletosR = filaRepFolletos.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO14A);
					HSSFCell celdaSpotPublicTvR = filaSpotPublicTv.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO14A);
					HSSFCell celdaSpotPublicRadioR = filaSpotPublicRadio.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO14A);
					HSSFCell celdaNroEmpadR = filaNroEmpad.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO14A);
					HSSFCell celdaPromConveniosR = filaPromConvenios.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO14A);
					HSSFCell celdaRegFirmaConveniosR = filaRegFirmaConvenios.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO14A);
					HSSFCell celdaImpBanderolaR = filaImpBanderola.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO14A);
					HSSFCell celdaNroAgentesR = filaNroAgentes.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO14A);
					//PROVINCIA
					HSSFCell celdaImpEsqInvitacionP = filaImpEsqInvitacion.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO14A);
					HSSFCell celdaImpDeclJuradaP = filaImpDeclJurada.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO14A);
					HSSFCell celdaImpFichaVerifP = filaImpFichaVerif.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO14A);
					HSSFCell celdaRepEsqInvitacionP = filaRepEsqInvitacion.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO14A);
					HSSFCell celdaVerifInformacionP = filaVerifInformacion.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO14A);
					HSSFCell celdaElabArchBeneficiarioP = filaElabArchBeneficiario.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO14A);
					HSSFCell celdaDigitFichaBeneficiarioP = filaDigitFichaBeneficiario.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO14A);
					HSSFCell celdaImpVolantesP = filaImpVolantes.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO14A);
					HSSFCell celdaImpAficheP = filaImpAfiche.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO14A);
					HSSFCell celdaRepFolletosP = filaRepFolletos.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO14A);
					HSSFCell celdaSpotPublicTvP = filaSpotPublicTv.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO14A);
					HSSFCell celdaSpotPublicRadioP = filaSpotPublicRadio.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO14A);
					HSSFCell celdaNroEmpadP = filaNroEmpad.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO14A);
					HSSFCell celdaPromConveniosP = filaPromConvenios.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO14A);
					HSSFCell celdaRegFirmaConveniosP = filaRegFirmaConvenios.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO14A);
					HSSFCell celdaImpBanderolaP = filaImpBanderola.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO14A);
					HSSFCell celdaNroAgentesP = filaNroAgentes.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO14A);
					//LIMA
					HSSFCell celdaImpEsqInvitacionL = filaImpEsqInvitacion.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO14A);
					HSSFCell celdaImpDeclJuradaL = filaImpDeclJurada.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO14A);
					HSSFCell celdaImpFichaVerifL = filaImpFichaVerif.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO14A);
					HSSFCell celdaRepEsqInvitacionL = filaRepEsqInvitacion.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO14A);
					HSSFCell celdaVerifInformacionL = filaVerifInformacion.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO14A);
					HSSFCell celdaElabArchBeneficiarioL = filaElabArchBeneficiario.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO14A);
					HSSFCell celdaDigitFichaBeneficiarioL = filaDigitFichaBeneficiario.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO14A);
					HSSFCell celdaImpVolantesL = filaImpVolantes.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO14A);
					HSSFCell celdaImpAficheL = filaImpAfiche.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO14A);
					HSSFCell celdaRepFolletosL = filaRepFolletos.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO14A);
					HSSFCell celdaSpotPublicTvL = filaSpotPublicTv.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO14A);
					HSSFCell celdaSpotPublicRadioL = filaSpotPublicRadio.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO14A);
					HSSFCell celdaNroEmpadL = filaNroEmpad.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO14A);
					HSSFCell celdaPromConveniosL = filaPromConvenios.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO14A);
					HSSFCell celdaRegFirmaConveniosL = filaRegFirmaConvenios.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO14A);
					HSSFCell celdaImpBanderolaL = filaImpBanderola.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO14A);
					HSSFCell celdaNroAgentesL = filaNroAgentes.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO14A);
					
					
					//tipos
					if( HSSFCell.CELL_TYPE_STRING == celdaEmpresa.getCellType()  ){
						formulario.setCodigoEmpresa(celdaEmpresa.toString());
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaEmpresa.getCellType()  ){
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_30)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_30));
						listaError.add(error);
					}else{
						formulario.setCodigoEmpresa("");
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_40)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_40));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_STRING == celdaAnio.getCellType()  ){
						formulario.setAnioPresent(Long.parseLong(celdaAnio.toString()));
						formulario.setAnioInicioVigencia(Long.parseLong(celdaAnio.toString()));
						formulario.setAnioFinVigencia(Long.parseLong(celdaAnio.toString()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaAnio.getCellType()  ){
						formulario.setAnioPresent(0);
						formulario.setAnioInicioVigencia(0);
						formulario.setAnioFinVigencia(0);
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_50)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_50));
						listaError.add(error);
					}else{
						formulario.setAnioPresent(0);
						formulario.setAnioInicioVigencia(0);
						formulario.setAnioFinVigencia(0);
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_60)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaEmpresa.get(FiseConstants.COD_ERROR_F12_60));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_STRING == celdaMes.getCellType()  ){
						formulario.setMesPresent(Long.parseLong(celdaMes.toString()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaMes.getCellType()  ){
						formulario.setMesPresent(0);
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_70)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_70));
						listaError.add(error);
					}else{
						formulario.setMesPresent(0);
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_80)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_80));
						listaError.add(error);
					}
					//valores
					//RURAL
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpEsqInvitacionR.getCellType()  ){
						formulario.setImprEsqInvitR(new BigDecimal(celdaImpEsqInvitacionR.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpEsqInvitacionR.getCellType()  ){
						formulario.setImprEsqInvitR(new BigDecimal(0.00));
					}else{
						formulario.setImprEsqInvitR(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpDeclJuradaR.getCellType()  ){
						formulario.setImprDeclaJuradaR(new BigDecimal(celdaImpDeclJuradaR.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpDeclJuradaR.getCellType()  ){
						formulario.setImprDeclaJuradaR(new BigDecimal(0.00));
					}else{
						formulario.setImprDeclaJuradaR(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpFichaVerifR.getCellType()  ){
						formulario.setImprAfichesR(new BigDecimal(celdaImpFichaVerifR.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpFichaVerifR.getCellType()  ){
						formulario.setImprAfichesR(new BigDecimal(0.00));
					}else{
						formulario.setImprAfichesR(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaRepEsqInvitacionR.getCellType()  ){
						formulario.setRepartoEsqInvitR(new BigDecimal(celdaRepEsqInvitacionR.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaRepEsqInvitacionR.getCellType()  ){
						formulario.setRepartoEsqInvitR(new BigDecimal(0.00));
					}else{
						formulario.setRepartoEsqInvitR(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaVerifInformacionR.getCellType()  ){
						formulario.setVerifInfoR(new BigDecimal(celdaVerifInformacionR.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaVerifInformacionR.getCellType()  ){
						formulario.setVerifInfoR(new BigDecimal(0.00));
					}else{
						formulario.setVerifInfoR(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaElabArchBeneficiarioR.getCellType()  ){
						formulario.setElabArchivoBenefR(new BigDecimal(celdaElabArchBeneficiarioR.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaElabArchBeneficiarioR.getCellType()  ){
						formulario.setElabArchivoBenefR(new BigDecimal(0.00));
					}else{
						formulario.setElabArchivoBenefR(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaDigitFichaBeneficiarioR.getCellType()  ){
						formulario.setDigitFichaBenefR(new BigDecimal(celdaDigitFichaBeneficiarioR.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaDigitFichaBeneficiarioR.getCellType()  ){
						formulario.setDigitFichaBenefR(new BigDecimal(0.00));
					}else{
						formulario.setDigitFichaBenefR(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpVolantesR.getCellType()  ){
						formulario.setImprVolantesR(new BigDecimal(celdaImpVolantesR.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpVolantesR.getCellType()  ){
						formulario.setImprVolantesR(new BigDecimal(0.00));
					}else{
						formulario.setImprVolantesR(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpAficheR.getCellType()  ){
						formulario.setImprAfichesR(new BigDecimal(celdaImpAficheR.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpAficheR.getCellType()  ){
						formulario.setImprAfichesR(new BigDecimal(0.00));
					}else{
						formulario.setImprAfichesR(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaRepFolletosR.getCellType()  ){
						formulario.setRepFolletosR(new BigDecimal(celdaRepFolletosR.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaRepFolletosR.getCellType()  ){
						formulario.setRepFolletosR(new BigDecimal(0.00));
					}else{
						formulario.setRepFolletosR(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaSpotPublicTvR.getCellType()  ){
						formulario.setSpotPublTvR(new BigDecimal(celdaSpotPublicTvR.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaSpotPublicTvR.getCellType()  ){
						formulario.setSpotPublTvR(new BigDecimal(0.00));
					}else{
						formulario.setSpotPublTvR(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaSpotPublicRadioR.getCellType()  ){
						formulario.setSpotPublRadioR(new BigDecimal(celdaSpotPublicRadioR.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaSpotPublicRadioR.getCellType()  ){
						formulario.setSpotPublRadioR(new BigDecimal(0.00));
					}else{
						formulario.setSpotPublRadioR(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroEmpadR.getCellType()  ){
						formulario.setNroBenefEmpadR(new Double(celdaNroEmpadR.getNumericCellValue()).longValue());
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroEmpadR.getCellType()  ){
						formulario.setNroBenefEmpadR(0);
					}else{
						formulario.setNroBenefEmpadR(0);
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_90)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_90));
						listaError.add(error);
					}
					//PROVINCIA
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpEsqInvitacionP.getCellType()  ){
						formulario.setImprEsqInvitP(new BigDecimal(celdaImpEsqInvitacionP.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpEsqInvitacionP.getCellType()  ){
						formulario.setImprEsqInvitP(new BigDecimal(0.00));
					}else{
						formulario.setImprEsqInvitP(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpDeclJuradaP.getCellType()  ){
						formulario.setImprDeclaJuradaP(new BigDecimal(celdaImpDeclJuradaP.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpDeclJuradaP.getCellType()  ){
						formulario.setImprDeclaJuradaP(new BigDecimal(0.00));
					}else{
						formulario.setImprDeclaJuradaP(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpFichaVerifP.getCellType()  ){
						formulario.setImprAfichesP(new BigDecimal(celdaImpFichaVerifP.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpFichaVerifP.getCellType()  ){
						formulario.setImprAfichesP(new BigDecimal(0.00));
					}else{
						formulario.setImprAfichesP(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaRepEsqInvitacionP.getCellType()  ){
						formulario.setRepartoEsqInvitP(new BigDecimal(celdaRepEsqInvitacionP.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaRepEsqInvitacionP.getCellType()  ){
						formulario.setRepartoEsqInvitP(new BigDecimal(0.00));
					}else{
						formulario.setRepartoEsqInvitP(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaVerifInformacionP.getCellType()  ){
						formulario.setVerifInfoP(new BigDecimal(celdaVerifInformacionP.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaVerifInformacionP.getCellType()  ){
						formulario.setVerifInfoP(new BigDecimal(0.00));
					}else{
						formulario.setVerifInfoP(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaElabArchBeneficiarioP.getCellType()  ){
						formulario.setElabArchivoBenefP(new BigDecimal(celdaElabArchBeneficiarioP.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaElabArchBeneficiarioP.getCellType()  ){
						formulario.setElabArchivoBenefP(new BigDecimal(0.00));
					}else{
						formulario.setElabArchivoBenefP(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaDigitFichaBeneficiarioP.getCellType()  ){
						formulario.setDigitFichaBenefP(new BigDecimal(celdaDigitFichaBeneficiarioP.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaDigitFichaBeneficiarioP.getCellType()  ){
						formulario.setDigitFichaBenefP(new BigDecimal(0.00));
					}else{
						formulario.setDigitFichaBenefP(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpVolantesP.getCellType()  ){
						formulario.setImprVolantesP(new BigDecimal(celdaImpVolantesP.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpVolantesP.getCellType()  ){
						formulario.setImprVolantesP(new BigDecimal(0.00));
					}else{
						formulario.setImprVolantesP(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpAficheP.getCellType()  ){
						formulario.setImprAfichesP(new BigDecimal(celdaImpAficheP.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpAficheR.getCellType()  ){
						formulario.setImprAfichesP(new BigDecimal(0.00));
					}else{
						formulario.setImprAfichesP(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaRepFolletosP.getCellType()  ){
						formulario.setRepFolletosP(new BigDecimal(celdaRepFolletosP.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaRepFolletosP.getCellType()  ){
						formulario.setRepFolletosP(new BigDecimal(0.00));
					}else{
						formulario.setRepFolletosP(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaSpotPublicTvP.getCellType()  ){
						formulario.setSpotPublTvP(new BigDecimal(celdaSpotPublicTvP.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaSpotPublicTvP.getCellType()  ){
						formulario.setSpotPublTvP(new BigDecimal(0.00));
					}else{
						formulario.setSpotPublTvP(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaSpotPublicRadioP.getCellType()  ){
						formulario.setSpotPublRadioP(new BigDecimal(celdaSpotPublicRadioP.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaSpotPublicRadioP.getCellType()  ){
						formulario.setSpotPublRadioP(new BigDecimal(0.00));
					}else{
						formulario.setSpotPublRadioP(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroEmpadP.getCellType()  ){
						formulario.setNroBenefEmpadP(new Double(celdaNroEmpadP.getNumericCellValue()).longValue());
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroEmpadP.getCellType()  ){
						formulario.setNroBenefEmpadP(0);
					}else{
						formulario.setNroBenefEmpadP(0);
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_90)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_90));
						listaError.add(error);
					}
					//LIMA
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpEsqInvitacionP.getCellType()  ){
						formulario.setImprEsqInvitL(new BigDecimal(celdaImpEsqInvitacionP.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpEsqInvitacionP.getCellType()  ){
						formulario.setImprEsqInvitL(new BigDecimal(0.00));
					}else{
						formulario.setImprEsqInvitL(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpDeclJuradaP.getCellType()  ){
						formulario.setImprDeclaJuradaL(new BigDecimal(celdaImpDeclJuradaP.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpDeclJuradaP.getCellType()  ){
						formulario.setImprDeclaJuradaL(new BigDecimal(0.00));
					}else{
						formulario.setImprDeclaJuradaL(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpFichaVerifP.getCellType()  ){
						formulario.setImprAfichesL(new BigDecimal(celdaImpFichaVerifP.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpFichaVerifP.getCellType()  ){
						formulario.setImprAfichesL(new BigDecimal(0.00));
					}else{
						formulario.setImprAfichesL(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaRepEsqInvitacionP.getCellType()  ){
						formulario.setRepartoEsqInvitL(new BigDecimal(celdaRepEsqInvitacionP.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaRepEsqInvitacionP.getCellType()  ){
						formulario.setRepartoEsqInvitL(new BigDecimal(0.00));
					}else{
						formulario.setRepartoEsqInvitL(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaVerifInformacionP.getCellType()  ){
						formulario.setVerifInfoL(new BigDecimal(celdaVerifInformacionP.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaVerifInformacionP.getCellType()  ){
						formulario.setVerifInfoL(new BigDecimal(0.00));
					}else{
						formulario.setVerifInfoL(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaElabArchBeneficiarioP.getCellType()  ){
						formulario.setElabArchivoBenefL(new BigDecimal(celdaElabArchBeneficiarioP.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaElabArchBeneficiarioP.getCellType()  ){
						formulario.setElabArchivoBenefL(new BigDecimal(0.00));
					}else{
						formulario.setElabArchivoBenefL(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaDigitFichaBeneficiarioP.getCellType()  ){
						formulario.setDigitFichaBenefL(new BigDecimal(celdaDigitFichaBeneficiarioP.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaDigitFichaBeneficiarioP.getCellType()  ){
						formulario.setDigitFichaBenefL(new BigDecimal(0.00));
					}else{
						formulario.setDigitFichaBenefL(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpVolantesP.getCellType()  ){
						formulario.setImprVolantesL(new BigDecimal(celdaImpVolantesP.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpVolantesP.getCellType()  ){
						formulario.setImprVolantesL(new BigDecimal(0.00));
					}else{
						formulario.setImprVolantesL(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpAficheP.getCellType()  ){
						formulario.setImprAfichesL(new BigDecimal(celdaImpAficheP.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpAficheP.getCellType()  ){
						formulario.setImprAfichesL(new BigDecimal(0.00));
					}else{
						formulario.setImprAfichesL(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaRepFolletosP.getCellType()  ){
						formulario.setRepFolletosL(new BigDecimal(celdaRepFolletosP.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaRepFolletosP.getCellType()  ){
						formulario.setRepFolletosL(new BigDecimal(0.00));
					}else{
						formulario.setRepFolletosL(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaSpotPublicTvP.getCellType()  ){
						formulario.setSpotPublTvL(new BigDecimal(celdaSpotPublicTvP.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaSpotPublicTvP.getCellType()  ){
						formulario.setSpotPublTvL(new BigDecimal(0.00));
					}else{
						formulario.setSpotPublTvL(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaSpotPublicRadioP.getCellType()  ){
						formulario.setSpotPublRadioL(new BigDecimal(celdaSpotPublicRadioP.getNumericCellValue()));
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaSpotPublicRadioP.getCellType()  ){
						formulario.setSpotPublRadioL(new BigDecimal(0.00));
					}else{
						formulario.setSpotPublRadioL(new BigDecimal(0.00));
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_150)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_150));
						listaError.add(error);
					}
					if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroEmpadP.getCellType()  ){
						formulario.setNroBenefEmpadL(new Double(celdaNroEmpadP.getNumericCellValue()).longValue());
					}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroEmpadP.getCellType()  ){
						formulario.setNroBenefEmpadL(0);
					}else{
						formulario.setNroBenefEmpadL(0);
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_90)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_90));
						listaError.add(error);
					}
					//VALIDACIONES DE OBLIGATOREIDAD
					//validar que siempre que ingrese un valor en la comlumna si se ingreso otro valor
					if( BigDecimal.ZERO.equals(formulario.getNroBenefEmpadR()) && !BigDecimal.ZERO.equals(formulario.getNroAgentR()) ){
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_300)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_300));
						listaError.add(error);
					}else if( BigDecimal.ZERO.equals(formulario.getNroAgentR()) && !BigDecimal.ZERO.equals(formulario.getNroBenefEmpadR()) ){
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_310)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_310));
						listaError.add(error);
					}
					if( BigDecimal.ZERO.equals(formulario.getNroBenefEmpadP()) && !BigDecimal.ZERO.equals(formulario.getNroAgentP()) ){
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_320)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_320));
						listaError.add(error);
					}else if( BigDecimal.ZERO.equals(formulario.getNroAgentP()) && !BigDecimal.ZERO.equals(formulario.getNroBenefEmpadP()) ){
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_330)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_330));
						listaError.add(error);
					}
					if( BigDecimal.ZERO.equals(formulario.getNroBenefEmpadL()) && !BigDecimal.ZERO.equals(formulario.getNroAgentL()) ){
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_340)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_340));
						listaError.add(error);
					}else if( BigDecimal.ZERO.equals(formulario.getNroAgentL()) && !BigDecimal.ZERO.equals(formulario.getNroBenefEmpadL()) ){
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_350)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_350));
						listaError.add(error);
					}
					///
					
					if( FiseConstants.BLANCO.equals(sMsg) ){
						
						//
						formulario.setUsuario(user.getLogin());
						formulario.setTerminal(user.getLoginIP());
						formulario.setTipoArchivo(FiseConstants.TIPOARCHIVO_XLS);
						formulario.setNombreArchivo(archivo.getTitle());
						//
						if( codEmpresa.equals(formulario.getCodigoEmpresa()) &&
								anioPres.equals(String.valueOf(formulario.getAnioPresent())) &&
								Long.parseLong(mesPres) == formulario.getMesPresent() &&
								anioIniVigencia.equals(String.valueOf(formulario.getAnioPresent())) &&
								anioFinVigencia.equals(String.valueOf(formulario.getAnioPresent()))
								){
							if( FiseConstants.FLAG_CARGAEXCEL_FORMULARIONUEVO.equals(flagCarga) ){
								objeto = formato14Service.registrarFormato14AC(formulario);
							}else if( FiseConstants.FLAG_CARGAEXCEL_FORMULARIOMODIFICACION.equals(flagCarga) ){
								FiseFormato14AC formatoModif = new FiseFormato14AC();
								FiseFormato14ACPK id = new FiseFormato14ACPK();
								id.setCodEmpresa(formulario.getCodigoEmpresa());
								id.setAnoPresentacion(formulario.getAnioPresent());
								id.setMesPresentacion(formulario.getMesPresent());
								id.setAnoInicioVigencia(formulario.getAnioPresent());
								id.setAnoFinVigencia(formulario.getAnioPresent());
								id.setEtapa(FiseConstants.ETAPA_SOLICITUD);
								formatoModif = formato14Service.obtenerFormato14ACByPK(id);
								objeto = formato14Service.modificarFormato14AC(formulario, formatoModif);
							}
							
						}else{
							sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_210)+FiseConstants.SALTO_LINEA;
							cont++;
							MensajeErrorBean error = new MensajeErrorBean();
							error.setId(cont);
							error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_210));
							listaError.add(error);
						}
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
		formatoMensaje.setFiseFormato14AC(objeto);
		if(listaError.size()>0)
			formatoMensaje.setListaMensajeError(listaError);
		
		return formatoMensaje;
	}

public Formato14AMensajeBean readTxtFile(FileEntry archivo, UploadPortletRequest uploadPortletRequest, User user, String flagCarga, String codEmpresaEdit, String anioPresEdit, String mesPresEdit, String anioEjecEdit, String mesEjecEdit, String etapaEdit) {
	
	//---------------------
	//FLAG CARGA:
	//	3: para registros nuevos
	//	4: para registros modificados
	//---------------------
	Formato14AMensajeBean formatoMensaje = new Formato14AMensajeBean();
	InputStream is=null;
	FiseFormato12AC objeto = null;
	List<MensajeErrorBean> listaError = new ArrayList<MensajeErrorBean>();
	String sMsg = "";
	int cont = 0;
	List<CfgCampo> listaCampo = null;
	try{
		CfgTabla tabla = new CfgTabla();
		tabla.setIdTabla(FiseConstants.ID_TABLA_FORMATO14A);
		listaCampo = campoService.listarCamposByTabla(tabla);
		if( listaCampo != null ){
			int longitudMaxima = campoService.longitudMaximaRegistro(listaCampo);
			int posicionCodEmpresa = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_COD_EMPRESA);
			int posicionAnioPresentacion = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_ANO_PRESENTACION);
			int posicionMesPresentacion = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_MES_PRESENTACION);
			int posicionMesEjecucion = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_MES_EJECUCION);
			int posicionZonaBenef = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_ZONA_BENEFICIARIO);
			int posicionNroEmpad = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_NRO_EMPADRONADOS);
			int posicionTotalEmpad = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_TOTAL_EMPADRONADOS);
			int posicionNroAgent = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_NRO_AGENTES_AUTOR);
			int posicionTotalAgent = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_TOTAL_AGENTES);
			int posicionDesplPersonal = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_DESPLAZ_PERSONAL);
			int posicionActivExtraord = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_ACTIVID_EXTRAORD);
			
			String sCurrentLine;
			is=uploadPortletRequest.getFileAsStream("archivoTxt");
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
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_220)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_220));
						listaError.add(error);
					}
				}else{
					sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_230)+FiseConstants.SALTO_LINEA;
					cont++;
					MensajeErrorBean error = new MensajeErrorBean();
					error.setId(cont);
					error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_230));
					listaError.add(error);
				}
				sCurrentLine = br.readLine();
				if( cont>3 ){
					sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_240)+FiseConstants.SALTO_LINEA;
					cont++;
					MensajeErrorBean error = new MensajeErrorBean();
					error.setId(cont);
					error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_240));
					listaError.add(error);
					break;
				}
			}
			
			String key1,key2,key3="";//,key4,key5,key6="";
			if( listaDetalleTxt.size()>0 ){
				key1 = listaDetalleTxt.get(0).substring(0, posicionCodEmpresa).trim();
				key2 = listaDetalleTxt.get(0).substring(posicionCodEmpresa, posicionAnioPresentacion).trim();
				key3 = listaDetalleTxt.get(0).substring(posicionAnioPresentacion, posicionMesPresentacion).trim();
				//key4 = listaDetalleTxt.get(0).substring(FiseConstants.POSICION_MES_PRESENTACION, FiseConstants.POSICION_ANIO_EJECUCION) ;
				//key5 = listaDetalleTxt.get(0).substring(FiseConstants.POSICION_ANIO_EJECUCION, FiseConstants.POSICION_MES_EJECUCION) ;
				//key6 = listaDetalleTxt.get(0).substring(FiseConstants.POSICION_MES_EJECUCION, FiseConstants.POSICION_ZONA_BENEFICIARIO) ;
				boolean process = true;
				Set<String> zonaSet = new java.util.HashSet<String>();
				for (String s : listaDetalleTxt) {
					String codEmp = s.substring(0, posicionCodEmpresa).trim();
					String anioPres = s.substring(posicionCodEmpresa, posicionAnioPresentacion).trim();
					String mesPres = s.substring(posicionAnioPresentacion, posicionMesPresentacion) ;
					//String anioEje = s.substring(FiseConstants.POSICION_MES_PRESENTACION, FiseConstants.POSICION_ANIO_EJECUCION);
					//String mesEje = s.substring(FiseConstants.POSICION_ANIO_EJECUCION, FiseConstants.POSICION_MES_EJECUCION);
					String zonaBenef = s.substring(posicionMesEjecucion, posicionZonaBenef).trim();
					if( key1.equals(codEmp) && key2.equals(anioPres) && key3.equals(mesPres) &&
							(FiseConstants.ZONABENEF_RURAL_COD == Long.parseLong(zonaBenef) ||
							FiseConstants.ZONABENEF_PROVINCIA_COD == Long.parseLong(zonaBenef) ||
							FiseConstants.ZONABENEF_LIMA_COD == Long.parseLong(zonaBenef) )
							){
						if( zonaSet.contains(zonaBenef) ){
							sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_280)+FiseConstants.SALTO_LINEA;
							process=false;
							cont++;
							MensajeErrorBean error = new MensajeErrorBean();
							error.setId(cont);
							error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_280));
							listaError.add(error);
							break;
						}else{
							zonaSet.add(zonaBenef);
							process=true;
						}
					}else{
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_260)+FiseConstants.SALTO_LINEA;
						process=false;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_260));
						listaError.add(error);
						break;
					}
				}
				if(process){
					Formato12ACBean formulario = new Formato12ACBean();
					//nuevamente recorremos la lista para armar los objetos
					formulario.setCodigoEmpresa(key1);
					formulario.setAnioPresent(Long.parseLong(key2));
					formulario.setMesPresent(Long.parseLong(key3));
					formulario.setAnioEjecuc(Long.parseLong(key2));
					formulario.setMesEjecuc(Long.parseLong(key3));

					if( codEmpresaEdit.equals(formulario.getCodigoEmpresa()) &&
							anioPresEdit.equals(String.valueOf(formulario.getAnioPresent())) &&
							Long.parseLong(mesPresEdit)==formulario.getMesPresent() &&
							anioEjecEdit.equals(String.valueOf(formulario.getAnioPresent())) &&
							Long.parseLong(mesEjecEdit)==formulario.getMesPresent() 
							){
						
						//
						for (String s : listaDetalleTxt) {
							String zonaBenef = s.substring(posicionMesEjecucion, posicionZonaBenef).trim();
							String nroEmpad=s.substring(posicionZonaBenef, posicionNroEmpad).trim();
							String nroAgent=s.substring(posicionTotalEmpad, posicionNroAgent).trim();
							String desplazPersonal=s.substring(posicionTotalAgent, posicionDesplPersonal).trim();
							String activExtraord=s.substring(posicionDesplPersonal, posicionActivExtraord).trim();
							FiseFormato14AD detalle = formato14Service.obtenerFormato14ADVigente(key1, Long.parseLong(key2), Long.parseLong(zonaBenef));
							if( FiseConstants.ZONABENEF_RURAL_COD == Long.parseLong(zonaBenef) ){
								formulario.setNroEmpadR(Long.parseLong(nroEmpad));
								formulario.setNroAgentR(Long.parseLong(nroAgent));
								formulario.setDesplPersonalR(new BigDecimal(desplazPersonal));
								formulario.setActivExtraordR(new BigDecimal(activExtraord));
								if(detalle!=null){
									formulario.setCostoUnitEmpadR(detalle.getCostoUnitarioEmpadronamiento());
									formulario.setCostoUnitAgentR(detalle.getCostoUntitarioAgenteGlp());
								}else{
									formulario.setCostoUnitEmpadR(new BigDecimal(0.00));
									formulario.setCostoUnitAgentR(new BigDecimal(0.00));
								}
							}else if( FiseConstants.ZONABENEF_PROVINCIA_COD == Long.parseLong(zonaBenef) ){
								formulario.setNroEmpadP(Long.parseLong(nroEmpad));
								formulario.setNroAgentP(Long.parseLong(nroAgent));
								formulario.setDesplPersonalP(new BigDecimal(desplazPersonal));
								formulario.setActivExtraordP(new BigDecimal(activExtraord));
								if(detalle!=null){
									formulario.setCostoUnitEmpadP(detalle.getCostoUnitarioEmpadronamiento());
									formulario.setCostoUnitAgentP(detalle.getCostoUntitarioAgenteGlp());
								}else{
									formulario.setCostoUnitEmpadP(new BigDecimal(0.00));
									formulario.setCostoUnitAgentP(new BigDecimal(0.00));
								}
							}else if( FiseConstants.ZONABENEF_LIMA_COD == Long.parseLong(zonaBenef) ){
								formulario.setNroEmpadL(Long.parseLong(nroEmpad));
								formulario.setNroAgentL(Long.parseLong(nroAgent));
								formulario.setDesplPersonalL(new BigDecimal(desplazPersonal));
								formulario.setActivExtraordL(new BigDecimal(activExtraord));
								if(detalle!=null){
									formulario.setCostoUnitEmpadL(detalle.getCostoUnitarioEmpadronamiento());
									formulario.setCostoUnitAgentL(detalle.getCostoUntitarioAgenteGlp());
								}else{
									formulario.setCostoUnitEmpadL(new BigDecimal(0.00));
									formulario.setCostoUnitAgentL(new BigDecimal(0.00));
								}
							}
						}
						//
						formulario.setUsuario(user.getLogin());
						formulario.setTerminal(user.getLoginIP());
						formulario.setTipoArchivo(FiseConstants.TIPOARCHIVO_TXT);
						formulario.setNombreArchivo(archivo.getTitle());
						//
						
						if( FiseConstants.FLAG_CARGATXT_FORMULARIONUEVO.equals(flagCarga) ){
							//--objeto = formato14Service.registrarFormato12AC(formulario);
						}else if( FiseConstants.FLAG_CARGATXT_FORMULARIOMODIFICACION.equals(flagCarga) ){
							FiseFormato12AC formatoModif = new FiseFormato12AC();
							FiseFormato12ACPK id = new FiseFormato12ACPK();
							id.setCodEmpresa(formulario.getCodigoEmpresa());
							id.setAnoPresentacion(formulario.getAnioPresent());
							id.setMesPresentacion(formulario.getMesPresent());
							id.setAnoEjecucionGasto(formulario.getAnioPresent());
							id.setMesEjecucionGasto(formulario.getMesPresent());
							id.setEtapa(FiseConstants.ETAPA_SOLICITUD);
							//--formatoModif = formatoService.obtenerFormato12ACByPK(id);
							//--objeto = formatoService.modificarFormato12AC(formulario, formatoModif);
						}
					}else{
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_270)+FiseConstants.SALTO_LINEA;
						cont++;
						MensajeErrorBean error = new MensajeErrorBean();
						error.setId(cont);
						error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_270));
						listaError.add(error);
					}
					
				}/*else{
					sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_10)+FiseConstants.SALTO_LINEA;
					cont++;
					MensajeErrorBean error = new MensajeErrorBean();
					error.setId(cont);
					error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_10));
					listaError.add(error);
				}*/
			}
			is.close();
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
	//-----formatoMensaje.setFiseFormato14AC(objeto);
	formatoMensaje.setMensajeError(sMsg);
	if(listaError.size()>0)
		formatoMensaje.setListaMensajeError(listaError);
	
	return formatoMensaje;
}
		
}
