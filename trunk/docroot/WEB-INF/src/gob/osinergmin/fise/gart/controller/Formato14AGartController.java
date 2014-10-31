package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.Formato14ACBean;
import gob.osinergmin.fise.bean.MensajeErrorBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.FiseFormato14AC;
import gob.osinergmin.fise.domain.FiseFormato14ACPK;
import gob.osinergmin.fise.domain.FisePeriodoEnvio;
import gob.osinergmin.fise.gart.json.Formato12AGartJSON;
import gob.osinergmin.fise.gart.json.Formato14AGartJSON;
import gob.osinergmin.fise.gart.service.FisePeriodoEnvioGartService;
import gob.osinergmin.fise.gart.service.Formato14AGartService;
import gob.osinergmin.fise.util.FormatoUtil;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.WebKeys;
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
	
	List<FiseFormato14AC> listaFormato;
	Map<String, String> mapaEmpresa;
	List<FisePeriodoEnvio> listaPeriodoEnvio;
	
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
		
}
