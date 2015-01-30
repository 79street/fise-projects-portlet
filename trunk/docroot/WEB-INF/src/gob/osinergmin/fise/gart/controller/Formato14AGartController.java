package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.Formato14ACBean;
import gob.osinergmin.fise.bean.Formato14AMensajeBean;
import gob.osinergmin.fise.bean.Formato14Generic;
import gob.osinergmin.fise.bean.MensajeErrorBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.CfgCampo;
import gob.osinergmin.fise.domain.CfgTabla;
import gob.osinergmin.fise.domain.FiseFormato14AC;
import gob.osinergmin.fise.domain.FiseFormato14ACPK;
import gob.osinergmin.fise.domain.FiseFormato14AD;
import gob.osinergmin.fise.domain.FiseFormato14ADOb;
import gob.osinergmin.fise.domain.FisePeriodoEnvio;
import gob.osinergmin.fise.gart.json.Formato14AGartJSON;
import gob.osinergmin.fise.gart.jsp.FileEntryJSP;
import gob.osinergmin.fise.gart.service.CfgCampoGartService;
import gob.osinergmin.fise.gart.service.CfgTablaGartService;
import gob.osinergmin.fise.gart.service.CommonGartService;
import gob.osinergmin.fise.gart.service.FisePeriodoEnvioGartService;
import gob.osinergmin.fise.gart.service.Formato14AGartService;
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

@Controller("formato14AGartController")
@RequestMapping("VIEW")
public class Formato14AGartController {
	
private static final Logger logger = Logger.getLogger(Formato14AGartController.class);
	
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
	@Qualifier("cfgTablaGartServiceImpl")
	CfgTablaGartService tablaService;
	@Autowired
	@Qualifier("cfgCampoGartServiceImpl")
	CfgCampoGartService campoService;
	@Autowired
	@Qualifier("commonGartServiceImpl")
	CommonGartService commonService;
	
	List<FiseFormato14AC> listaFormato;
	Map<String, String> mapaEmpresa;
	List<FisePeriodoEnvio> listaPeriodoEnvio;
	Map<String, String> mapaErrores;
	List<MensajeErrorBean> listaObservaciones;
	Map<Long, String> mapaZonaBenef;
	
	Formato14ACBean beanBusqueda;
	
	@SuppressWarnings("unchecked")
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
  				
  				//grupo informacion y estado
  				if(fiseFormato14AC.getFiseGrupoInformacion()!=null && fiseFormato14AC.getFiseGrupoInformacion().getDescripcion()!=null){
  					fiseFormato14AC.setDescGrupoInformacion(fiseFormato14AC.getFiseGrupoInformacion().getDescripcion());
  				}else{
  					fiseFormato14AC.setDescGrupoInformacion(FiseConstants.BLANCO);
  				}
  				if(fiseFormato14AC.getFechaEnvioDefinitivo()!=null){
  					fiseFormato14AC.setDescEstado(FiseConstants.ESTADO_FECHAENVIO_ENVIADO);
  				}else{
  					fiseFormato14AC.setDescEstado(FiseConstants.ESTADO_FECHAENVIO_POR_ENVIAR);
  				}
  				
  				/**Obteniendo el flag de la operacion*/
  				String flagOper = commonService.obtenerEstadoProceso(fiseFormato14AC.getId().getCodEmpresa(),FiseConstants.TIPO_FORMATO_14A,fiseFormato14AC.getId().getAnoPresentacion(),
  						fiseFormato14AC.getId().getMesPresentacion(), fiseFormato14AC.getId().getEtapa());
  				logger.info("flag operacion:  "+flagOper);
  				
  				jsonArray.put(new Formato14AGartJSON().asJSONObject(fiseFormato14AC,"",flagOper));
  			}
  			
  			//valores busqueda
			beanBusqueda = new Formato14ACBean();
			beanBusqueda.setCodEmpresaB(command.getCodEmpresaB());
			beanBusqueda.setAnioDesde(command.getAnioDesde());
			beanBusqueda.setMesDesde(command.getMesDesde());
			beanBusqueda.setAnioHasta(command.getAnioHasta());
			beanBusqueda.setMesHasta(command.getMesHasta());
			beanBusqueda.setEtapaB(command.getEtapaB());
  			
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
				    listaPeriodoEnvio = periodoService.listarFisePeriodoEnvioMesAnioEtapa(codEmpresa, FiseConstants.NOMBRE_FORMATO_14A);
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
		        
		        JSONObject jsonent = new Formato14AGartJSON().asJSONObject(formato,flagPeriodo,"");
		        logger.info("jsonformato:"+jsonent);
		        jsonObj.put("formato",jsonent);
				jsonObj.put("resultado", "OK");
				
			}else if(tipo.equals(FiseConstants.COD_SAVE)){ 
				try {
					
					String anoIniVigencia = request.getParameter("anoInicioVigencia");
					String anoFinVigencia = request.getParameter("anoFinVigencia");
					
					Formato14ACBean formulario = new Formato14ACBean();
					
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
			        
			        formato = formato14Service.obtenerFormato14ACByPK(pk);
					logger.info("objeto "+formato);
					
					ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
					
					formulario.setUsuario(themeDisplay.getUser().getLogin());
					formulario.setTerminal(themeDisplay.getUser().getLoginIP());
					
			        formato14Service.modificarFormato14AC(formulario, formato);
					jsonObj.put("resultado", "OK"); 	
				} catch (Exception e) {
					jsonObj.put("resultado", "Error");
					jsonObj.put("mensaje", e.getMessage());
					System.out.println("Error al guardar la tabla fiseformato14A: "+e.getMessage());
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
  			listaPeriodoEnvio = periodoService.listarFisePeriodoEnvioMesAnioEtapa(codEmpresa, FiseConstants.TIPO_FORMATO_14A);
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
					//agreamos los campos de ano inicio y fin de vigencia
					jsonObj.put("anioInicioVigencia", p.getAnioInicioVig());
					jsonObj.put("anioFinVigencia", p.getAnioFinVig());
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
	public void cargarDocumento(ActionRequest request,ActionResponse response,@ModelAttribute("formato14ACBean")Formato14ACBean command){
		
		logger.info("--- cargar documento");
		Formato14AMensajeBean formatoMensaje = new Formato14AMensajeBean();
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
			pRequest.getPortletSession().setAttribute("mensajeInformacion", "El Formato 14A se guardó satisfactoriamente", PortletSession.APPLICATION_SCOPE);
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
		
		boolean process = false;
		
		try {
			if (archivo != null) {
				HSSFWorkbook libro = null;
				try {
					is=archivo.getContentStream();
					libro = new HSSFWorkbook(is);//Se lee libro xls
				} catch (Exception e1) {
					logger.warn(mapaErrores.get(FiseConstants.COD_ERROR_F14A_1620));
					cont++;
					sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F14A_1620);
					throw new Exception(mapaErrores.get(FiseConstants.COD_ERROR_F14A_1620));
				}
				int nroHojaSelec=0;
				
				if (libro != null) {
					//el excel puede tener varias hojas, se tiene qie leer el total de hojas y encontrar la que necesitemos
					logger.info("nro de hojas:"+ libro.getNumberOfSheets());
					for (int sheetNro = 0; sheetNro < libro.getNumberOfSheets(); sheetNro++){
						logger.info("nombre de hoja "+sheetNro+":"+ libro.getSheetName(sheetNro));
						if( FiseConstants.NOMBRE_HOJA_FORMATO14A.equals(libro.getSheetName(sheetNro)) ){
							process = true;
							nroHojaSelec = sheetNro;
							break;
						}
					}
					logger.info("nro de hoja seleccionada "+nroHojaSelec);
					
					if(process){
						
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
						if( HSSFCell.CELL_TYPE_STRING == celdaEmpresa.getCellType() ){
							formulario.setCodigoEmpresa(celdaEmpresa.toString());
						}else if( HSSFCell.CELL_TYPE_FORMULA == celdaEmpresa.getCellType()  ){
							formulario.setCodigoEmpresa(celdaEmpresa.getRichStringCellValue().toString());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaEmpresa.getCellType()  ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1630);
						}else{
							formulario.setCodigoEmpresa("");
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1640);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaAnio.getCellType()  ){
							formulario.setAnioPresent(new Double(celdaAnio.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaAnio.getCellType()  ){
							formulario.setAnioPresent(0);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1650);
						}else{
							formulario.setAnioPresent(0);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1660);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaMes.getCellType()  ){
							formulario.setMesPresent(new Double(celdaMes.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaMes.getCellType()  ){
							formulario.setMesPresent(0);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1670);
						}else{
							formulario.setMesPresent(0);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1680);
						}
						//valores
						//RURAL
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpEsqInvitacionR.getCellType()  ){
							formulario.setImprEsqInvitR(new BigDecimal(celdaImpEsqInvitacionR.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpEsqInvitacionR.getCellType()  ){
							formulario.setImprEsqInvitR(new BigDecimal(0.00));
						}else{
							formulario.setImprEsqInvitR(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1690);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpDeclJuradaR.getCellType()  ){
							formulario.setImprDeclaJuradaR(new BigDecimal(celdaImpDeclJuradaR.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpDeclJuradaR.getCellType()  ){
							formulario.setImprDeclaJuradaR(new BigDecimal(0.00));
						}else{
							formulario.setImprDeclaJuradaR(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1700);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpFichaVerifR.getCellType()  ){
							formulario.setImprFichaVerifR(new BigDecimal(celdaImpFichaVerifR.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpFichaVerifR.getCellType()  ){
							formulario.setImprFichaVerifR(new BigDecimal(0.00));
						}else{
							formulario.setImprFichaVerifR(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1710);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaRepEsqInvitacionR.getCellType()  ){
							formulario.setRepartoEsqInvitR(new BigDecimal(celdaRepEsqInvitacionR.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaRepEsqInvitacionR.getCellType()  ){
							formulario.setRepartoEsqInvitR(new BigDecimal(0.00));
						}else{
							formulario.setRepartoEsqInvitR(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1720);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaVerifInformacionR.getCellType()  ){
							formulario.setVerifInfoR(new BigDecimal(celdaVerifInformacionR.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaVerifInformacionR.getCellType()  ){
							formulario.setVerifInfoR(new BigDecimal(0.00));
						}else{
							formulario.setVerifInfoR(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1730);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaElabArchBeneficiarioR.getCellType()  ){
							formulario.setElabArchivoBenefR(new BigDecimal(celdaElabArchBeneficiarioR.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaElabArchBeneficiarioR.getCellType()  ){
							formulario.setElabArchivoBenefR(new BigDecimal(0.00));
						}else{
							formulario.setElabArchivoBenefR(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1740);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaDigitFichaBeneficiarioR.getCellType()  ){
							formulario.setDigitFichaBenefR(new BigDecimal(celdaDigitFichaBeneficiarioR.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaDigitFichaBeneficiarioR.getCellType()  ){
							formulario.setDigitFichaBenefR(new BigDecimal(0.00));
						}else{
							formulario.setDigitFichaBenefR(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1750);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpVolantesR.getCellType()  ){
							formulario.setImprVolantesR(new BigDecimal(celdaImpVolantesR.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpVolantesR.getCellType()  ){
							formulario.setImprVolantesR(new BigDecimal(0.00));
						}else{
							formulario.setImprVolantesR(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1760);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpAficheR.getCellType()  ){
							formulario.setImprAfichesR(new BigDecimal(celdaImpAficheR.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpAficheR.getCellType()  ){
							formulario.setImprAfichesR(new BigDecimal(0.00));
						}else{
							formulario.setImprAfichesR(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1770);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaRepFolletosR.getCellType()  ){
							formulario.setRepFolletosR(new BigDecimal(celdaRepFolletosR.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaRepFolletosR.getCellType()  ){
							formulario.setRepFolletosR(new BigDecimal(0.00));
						}else{
							formulario.setRepFolletosR(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1780);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaSpotPublicTvR.getCellType()  ){
							formulario.setSpotPublTvR(new BigDecimal(celdaSpotPublicTvR.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaSpotPublicTvR.getCellType()  ){
							formulario.setSpotPublTvR(new BigDecimal(0.00));
						}else{
							formulario.setSpotPublTvR(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1790);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaSpotPublicRadioR.getCellType()  ){
							formulario.setSpotPublRadioR(new BigDecimal(celdaSpotPublicRadioR.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaSpotPublicRadioR.getCellType()  ){
							formulario.setSpotPublRadioR(new BigDecimal(0.00));
						}else{
							formulario.setSpotPublRadioR(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1791);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroEmpadR.getCellType()  ){
							formulario.setNroBenefEmpadR(new Double(celdaNroEmpadR.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroEmpadR.getCellType()  ){
							formulario.setNroBenefEmpadR(0);
						}else{
							formulario.setNroBenefEmpadR(0);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1800);
						}
						//agentes
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaPromConveniosR.getCellType()  ){
							formulario.setPromConvAgentR(new BigDecimal(celdaPromConveniosR.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaPromConveniosR.getCellType()  ){
							formulario.setPromConvAgentR(new BigDecimal(0.00));
						}else{
							formulario.setPromConvAgentR(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1810);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaRegFirmaConveniosR.getCellType()  ){
							formulario.setRegConvAgentR(new BigDecimal(celdaRegFirmaConveniosR.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaRegFirmaConveniosR.getCellType()  ){
							formulario.setRegConvAgentR(new BigDecimal(0.00));
						}else{
							formulario.setRegConvAgentR(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1820);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpBanderolaR.getCellType()  ){
							formulario.setImpEntrBandR(new BigDecimal(celdaImpBanderolaR.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpBanderolaR.getCellType()  ){
							formulario.setImpEntrBandR(new BigDecimal(0.00));
						}else{
							formulario.setImpEntrBandR(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1830);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroAgentesR.getCellType()  ){
							formulario.setNroAgentR(new Double(celdaNroAgentesR.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroAgentesR.getCellType()  ){
							formulario.setNroAgentR(0);
						}else{
							formulario.setNroAgentR(0);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1840);
						}
						
						
						//PROVINCIA
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpEsqInvitacionP.getCellType()  ){
							formulario.setImprEsqInvitP(new BigDecimal(celdaImpEsqInvitacionP.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpEsqInvitacionP.getCellType()  ){
							formulario.setImprEsqInvitP(new BigDecimal(0.00));
						}else{
							formulario.setImprEsqInvitP(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1850);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpDeclJuradaP.getCellType()  ){
							formulario.setImprDeclaJuradaP(new BigDecimal(celdaImpDeclJuradaP.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpDeclJuradaP.getCellType()  ){
							formulario.setImprDeclaJuradaP(new BigDecimal(0.00));
						}else{
							formulario.setImprDeclaJuradaP(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1860);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpFichaVerifP.getCellType()  ){
							formulario.setImprFichaVerifP(new BigDecimal(celdaImpFichaVerifP.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpFichaVerifP.getCellType()  ){
							formulario.setImprFichaVerifP(new BigDecimal(0.00));
						}else{
							formulario.setImprFichaVerifP(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1870);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaRepEsqInvitacionP.getCellType()  ){
							formulario.setRepartoEsqInvitP(new BigDecimal(celdaRepEsqInvitacionP.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaRepEsqInvitacionP.getCellType()  ){
							formulario.setRepartoEsqInvitP(new BigDecimal(0.00));
						}else{
							formulario.setRepartoEsqInvitP(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1880);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaVerifInformacionP.getCellType()  ){
							formulario.setVerifInfoP(new BigDecimal(celdaVerifInformacionP.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaVerifInformacionP.getCellType()  ){
							formulario.setVerifInfoP(new BigDecimal(0.00));
						}else{
							formulario.setVerifInfoP(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1890);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaElabArchBeneficiarioP.getCellType()  ){
							formulario.setElabArchivoBenefP(new BigDecimal(celdaElabArchBeneficiarioP.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaElabArchBeneficiarioP.getCellType()  ){
							formulario.setElabArchivoBenefP(new BigDecimal(0.00));
						}else{
							formulario.setElabArchivoBenefP(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1900);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaDigitFichaBeneficiarioP.getCellType()  ){
							formulario.setDigitFichaBenefP(new BigDecimal(celdaDigitFichaBeneficiarioP.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaDigitFichaBeneficiarioP.getCellType()  ){
							formulario.setDigitFichaBenefP(new BigDecimal(0.00));
						}else{
							formulario.setDigitFichaBenefP(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1910);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpVolantesP.getCellType()  ){
							formulario.setImprVolantesP(new BigDecimal(celdaImpVolantesP.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpVolantesP.getCellType()  ){
							formulario.setImprVolantesP(new BigDecimal(0.00));
						}else{
							formulario.setImprVolantesP(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1920);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpAficheP.getCellType()  ){
							formulario.setImprAfichesP(new BigDecimal(celdaImpAficheP.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpAficheR.getCellType()  ){
							formulario.setImprAfichesP(new BigDecimal(0.00));
						}else{
							formulario.setImprAfichesP(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1930);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaRepFolletosP.getCellType()  ){
							formulario.setRepFolletosP(new BigDecimal(celdaRepFolletosP.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaRepFolletosP.getCellType()  ){
							formulario.setRepFolletosP(new BigDecimal(0.00));
						}else{
							formulario.setRepFolletosP(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1940);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaSpotPublicTvP.getCellType()  ){
							formulario.setSpotPublTvP(new BigDecimal(celdaSpotPublicTvP.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaSpotPublicTvP.getCellType()  ){
							formulario.setSpotPublTvP(new BigDecimal(0.00));
						}else{
							formulario.setSpotPublTvP(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1950);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaSpotPublicRadioP.getCellType()  ){
							formulario.setSpotPublRadioP(new BigDecimal(celdaSpotPublicRadioP.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaSpotPublicRadioP.getCellType()  ){
							formulario.setSpotPublRadioP(new BigDecimal(0.00));
						}else{
							formulario.setSpotPublRadioP(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1951);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroEmpadP.getCellType()  ){
							formulario.setNroBenefEmpadP(new Double(celdaNroEmpadP.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroEmpadP.getCellType()  ){
							formulario.setNroBenefEmpadP(0);
						}else{
							formulario.setNroBenefEmpadP(0);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1960);
						}
						//agentes
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaPromConveniosP.getCellType()  ){
							formulario.setPromConvAgentP(new BigDecimal(celdaPromConveniosP.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaPromConveniosP.getCellType()  ){
							formulario.setPromConvAgentP(new BigDecimal(0.00));
						}else{
							formulario.setPromConvAgentP(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1970);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaRegFirmaConveniosP.getCellType()  ){
							formulario.setRegConvAgentP(new BigDecimal(celdaRegFirmaConveniosP.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaRegFirmaConveniosP.getCellType()  ){
							formulario.setRegConvAgentP(new BigDecimal(0.00));
						}else{
							formulario.setRegConvAgentP(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1980);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpBanderolaP.getCellType()  ){
							formulario.setImpEntrBandP(new BigDecimal(celdaImpBanderolaP.getNumericCellValue()));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpBanderolaP.getCellType()  ){
							formulario.setImpEntrBandP(new BigDecimal(0.00));
						}else{
							formulario.setImpEntrBandP(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_1990);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroAgentesP.getCellType()  ){
							formulario.setNroAgentP(new Double(celdaNroAgentesP.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroAgentesP.getCellType()  ){
							formulario.setNroAgentP(0);
						}else{
							formulario.setNroAgentP(0);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2000);
						}
						
						if( FiseConstants.COD_EMPRESA_EDELNOR.equals(codEmpresa) || FiseConstants.COD_EMPRESA_LUZ_SUR.equals(codEmpresa) ){
							//LIMA
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpEsqInvitacionL.getCellType()  ){
								formulario.setImprEsqInvitL(new BigDecimal(celdaImpEsqInvitacionL.getNumericCellValue()));
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpEsqInvitacionL.getCellType()  ){
								formulario.setImprEsqInvitL(new BigDecimal(0.00));
							}else{
								formulario.setImprEsqInvitL(new BigDecimal(0.00));
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2010);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpDeclJuradaL.getCellType()  ){
								formulario.setImprDeclaJuradaL(new BigDecimal(celdaImpDeclJuradaL.getNumericCellValue()));
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpDeclJuradaL.getCellType()  ){
								formulario.setImprDeclaJuradaL(new BigDecimal(0.00));
							}else{
								formulario.setImprDeclaJuradaL(new BigDecimal(0.00));
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2020);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpFichaVerifL.getCellType()  ){
								formulario.setImprFichaVerifL(new BigDecimal(celdaImpFichaVerifL.getNumericCellValue()));
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpFichaVerifL.getCellType()  ){
								formulario.setImprFichaVerifL(new BigDecimal(0.00));
							}else{
								formulario.setImprFichaVerifL(new BigDecimal(0.00));
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2030);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaRepEsqInvitacionL.getCellType()  ){
								formulario.setRepartoEsqInvitL(new BigDecimal(celdaRepEsqInvitacionL.getNumericCellValue()));
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaRepEsqInvitacionL.getCellType()  ){
								formulario.setRepartoEsqInvitL(new BigDecimal(0.00));
							}else{
								formulario.setRepartoEsqInvitL(new BigDecimal(0.00));
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2040);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaVerifInformacionL.getCellType()  ){
								formulario.setVerifInfoL(new BigDecimal(celdaVerifInformacionL.getNumericCellValue()));
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaVerifInformacionL.getCellType()  ){
								formulario.setVerifInfoL(new BigDecimal(0.00));
							}else{
								formulario.setVerifInfoL(new BigDecimal(0.00));
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2050);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaElabArchBeneficiarioL.getCellType()  ){
								formulario.setElabArchivoBenefL(new BigDecimal(celdaElabArchBeneficiarioL.getNumericCellValue()));
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaElabArchBeneficiarioL.getCellType()  ){
								formulario.setElabArchivoBenefL(new BigDecimal(0.00));
							}else{
								formulario.setElabArchivoBenefL(new BigDecimal(0.00));
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2060);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaDigitFichaBeneficiarioL.getCellType()  ){
								formulario.setDigitFichaBenefL(new BigDecimal(celdaDigitFichaBeneficiarioL.getNumericCellValue()));
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaDigitFichaBeneficiarioL.getCellType()  ){
								formulario.setDigitFichaBenefL(new BigDecimal(0.00));
							}else{
								formulario.setDigitFichaBenefL(new BigDecimal(0.00));
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2070);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpVolantesL.getCellType()  ){
								formulario.setImprVolantesL(new BigDecimal(celdaImpVolantesL.getNumericCellValue()));
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpVolantesL.getCellType()  ){
								formulario.setImprVolantesL(new BigDecimal(0.00));
							}else{
								formulario.setImprVolantesL(new BigDecimal(0.00));
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2080);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpAficheL.getCellType()  ){
								formulario.setImprAfichesL(new BigDecimal(celdaImpAficheL.getNumericCellValue()));
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpAficheL.getCellType()  ){
								formulario.setImprAfichesL(new BigDecimal(0.00));
							}else{
								formulario.setImprAfichesL(new BigDecimal(0.00));
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2090);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaRepFolletosL.getCellType()  ){
								formulario.setRepFolletosL(new BigDecimal(celdaRepFolletosL.getNumericCellValue()));
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaRepFolletosL.getCellType()  ){
								formulario.setRepFolletosL(new BigDecimal(0.00));
							}else{
								formulario.setRepFolletosL(new BigDecimal(0.00));
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2100);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaSpotPublicTvL.getCellType()  ){
								formulario.setSpotPublTvL(new BigDecimal(celdaSpotPublicTvL.getNumericCellValue()));
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaSpotPublicTvL.getCellType()  ){
								formulario.setSpotPublTvL(new BigDecimal(0.00));
							}else{
								formulario.setSpotPublTvL(new BigDecimal(0.00));
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2110);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaSpotPublicRadioL.getCellType()  ){
								formulario.setSpotPublRadioL(new BigDecimal(celdaSpotPublicRadioL.getNumericCellValue()));
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaSpotPublicRadioL.getCellType()  ){
								formulario.setSpotPublRadioL(new BigDecimal(0.00));
							}else{
								formulario.setSpotPublRadioL(new BigDecimal(0.00));
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2111);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroEmpadL.getCellType()  ){
								formulario.setNroBenefEmpadL(new Double(celdaNroEmpadL.getNumericCellValue()).longValue());
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroEmpadL.getCellType()  ){
								formulario.setNroBenefEmpadL(0);
							}else{
								formulario.setNroBenefEmpadL(0);
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2120);
							}
							//agentes
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaPromConveniosL.getCellType()  ){
								formulario.setPromConvAgentL(new BigDecimal(celdaPromConveniosL.getNumericCellValue()));
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaPromConveniosL.getCellType()  ){
								formulario.setPromConvAgentL(new BigDecimal(0.00));
							}else{
								formulario.setPromConvAgentL(new BigDecimal(0.00));
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2130);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaRegFirmaConveniosL.getCellType()  ){
								formulario.setRegConvAgentL(new BigDecimal(celdaRegFirmaConveniosL.getNumericCellValue()));
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaRegFirmaConveniosL.getCellType()  ){
								formulario.setRegConvAgentL(new BigDecimal(0.00));
							}else{
								formulario.setRegConvAgentL(new BigDecimal(0.00));
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2140);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaImpBanderolaL.getCellType()  ){
								formulario.setImpEntrBandL(new BigDecimal(celdaImpBanderolaL.getNumericCellValue()));
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaImpBanderolaL.getCellType()  ){
								formulario.setImpEntrBandL(new BigDecimal(0.00));
							}else{
								formulario.setImpEntrBandL(new BigDecimal(0.00));
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2150);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroAgentesL.getCellType()  ){
								formulario.setNroAgentL(new Double(celdaNroAgentesL.getNumericCellValue()).longValue());
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroAgentesL.getCellType()  ){
								formulario.setNroAgentL(0);
							}else{
								formulario.setNroAgentL(0);
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2160);
							}
						}
						
						
						
						
						//VALIDACIONES DE OBLIGATOREIDAD
						//validar que siempre que ingrese un valor en la comlumna si se ingreso otro valor
						if( BigDecimal.ZERO.equals(formulario.getNroBenefEmpadR()) && !BigDecimal.ZERO.equals(formulario.getNroAgentR()) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2170);
						}else if( BigDecimal.ZERO.equals(formulario.getNroAgentR()) && !BigDecimal.ZERO.equals(formulario.getNroBenefEmpadR()) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2180);
						}
						if( BigDecimal.ZERO.equals(formulario.getNroBenefEmpadP()) && !BigDecimal.ZERO.equals(formulario.getNroAgentP()) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2190);
						}else if( BigDecimal.ZERO.equals(formulario.getNroAgentP()) && !BigDecimal.ZERO.equals(formulario.getNroBenefEmpadP()) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2200);
						}
						
						if( FiseConstants.COD_EMPRESA_EDELNOR.equals(codEmpresa) || FiseConstants.COD_EMPRESA_LUZ_SUR.equals(codEmpresa) ){
							if( BigDecimal.ZERO.equals(formulario.getNroBenefEmpadL()) && !BigDecimal.ZERO.equals(formulario.getNroAgentL()) ){
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2210);
							}else if( BigDecimal.ZERO.equals(formulario.getNroAgentL()) && !BigDecimal.ZERO.equals(formulario.getNroBenefEmpadL()) ){
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2220);
							}
						}
						
						
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
									objeto = formato14Service.registrarFormato14AC(formulario);
								}else if( FiseConstants.FLAG_CARGAEXCEL_FORMULARIOMODIFICACION.equals(flagCarga) ){
									FiseFormato14AC formatoModif = new FiseFormato14AC();
									FiseFormato14ACPK id = new FiseFormato14ACPK();
									id.setCodEmpresa(formulario.getCodigoEmpresa());
									id.setAnoPresentacion(formulario.getAnioPresent());
									id.setMesPresentacion(formulario.getMesPresent());
									id.setAnoInicioVigencia(formulario.getAnioInicioVigencia());
									id.setAnoFinVigencia(formulario.getAnioFinVigencia());
									id.setEtapa(formulario.getEtapa());
									formatoModif = formato14Service.obtenerFormato14ACByPK(id);
									objeto = formato14Service.modificarFormato14AC(formulario, formatoModif);
								}
								
							}else{
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2230);
							}
						}
						
					}else{
						//--logger.warn(mapaErrores.get(FiseConstants.COD_ERROR_F12_20));
						cont++;
						sMsg = sMsg + "No se encuentra la hoja "+FiseConstants.NOMBRE_HOJA_FORMATO14A+" en el archivo cargado";
						throw new Exception("No se encuentra la hoja "+FiseConstants.NOMBRE_HOJA_FORMATO14A+" en el archivo cargado");
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

public Formato14AMensajeBean readTxtFile(FileEntry archivo, UploadPortletRequest uploadPortletRequest, User user, String flagCarga, String codEmpresaEdit, String anioPresEdit, String mesPresEdit, String anioIniVigEdit, String anioFinVigEdit, String etapaEdit) {
	
	//---------------------
	//FLAG CARGA:
	//	3: para registros nuevos
	//	4: para registros modificados
	//---------------------
	Formato14AMensajeBean formatoMensaje = new Formato14AMensajeBean();
	InputStream is=null;
	FiseFormato14AC objeto = null;
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
			int posicionAnioInicioVigencia = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_ANO_INICIO_VIGENCIA);
			int posicionAnioFinVigencia = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_ANO_FIN_VIGENCIA);
			int posicionZonaBenef = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_ZONA_BENEFICIARIO);
			
			int posicionImpEsqInvit = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_IMPRESION_ESQUELA_INVITACION_F14A);
			int posicionImpDeclaJurada = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_IMPRESION_DECLARACION_JURADA_F14A);
			int posicionImpFichaVerif = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_IMPRESION_FICHAS_VERIFICACION_F14A);
			int posicionRepEsqInvit= campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_REPARTO_ESQUELA_INVITACION_F14A);
			int posicionVerifInfo = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_VERIFICACION_INFORMACION_F14A);
			int posicionElabArchivo = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_ELABORACION_ARCHIVO_BENEF_F14A);
			int posicionDigitFichas = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_DIGITACION_FICHA_BENEF_F14A);
			int posicionTotalEmpad = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_TOTAL_EMPADRONAMIENTO_F14A);
			
			int posicionImpVolantes = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_IMPRESION_VOLANTES_F14A);
			int posicionImpAfiches = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_IMPRESION_AFICHES_F14A);
			int posicionRepFolletos = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_REPARTO_FOLLETO_POTENCIA_BENEF_F14A);
			int posicionSpotPublicTv = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_SPOT_PUBLICITARIO_TV_F14A);
			int posicionSpotPublicRadio = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_SPOT_PUBLICITARIO_RADIO_F14A);
			int posicionTotalDifusion = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_TOTAL_DIFUSION_INICIO_PRG_FISE_F14A);
			
			int posicionNroBenef = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_NUMERO_BENEF_EMPADRO_MES_DIC_F14A);
			int posicionCostoUnitBenef = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_COSTO_UNITARIO_EMPADRONAMIENTO_F14A);
			
			int posicionPromConvenios = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_PROMOCION_CONVENIO_AG_AUT_GLP_F14A);
			int posicionRegFirmaConvenios = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_REGISTRO_FIRMA_CONV_AG_AUT_GLP_F14A);
			int posicionImpEntBanderla = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_IMPRESION_ENTREGA_BANDEROLA_F14A);
			
			int posicionTotalCostoGestion = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_TOTAL_COSTO_GESTION_RED_AG_GLP_F14A);
			int posicionNroAgentes = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_NUMERO_AGENTES_F14A);
			
			String sCurrentLine;
			is=uploadPortletRequest.getFileAsStream("archivoTxt");
			
			String nombreIdeal = FormatoUtil.nombreArchivoCargaTxt(Long.parseLong(anioPresEdit), Long.parseLong(mesPresEdit), codEmpresaEdit, FiseConstants.TIPO_FORMATO_14A);
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
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2240);
						}
					}/*else{
						cont++;
						sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2250);
					}*/
					sCurrentLine = br.readLine();
					if( cont>3 ){
						cont++;
						sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2260);
						break;
					}
				}
				if(cont==0){
					cont++;
					sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2250);
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
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2270);
								break;
							}else{
								zonaSet.add(zonaBenef);
								process=true;
							}
						}else{
							process=false;
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2280);
							break;
						}
					}
					if(process){
						
						Formato14ACBean formulario = new Formato14ACBean();
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
								
								String impEsqInvit = s.substring(posicionZonaBenef, posicionImpEsqInvit).trim();
								String impDeclJurada = s.substring(posicionImpEsqInvit, posicionImpDeclaJurada).trim();
								String impFichaVerif = s.substring(posicionImpDeclaJurada, posicionImpFichaVerif).trim();
								String repEsqInvit = s.substring(posicionImpFichaVerif, posicionRepEsqInvit).trim();
								String verifInfo = s.substring(posicionRepEsqInvit, posicionVerifInfo).trim();
								String elabArch = s.substring(posicionVerifInfo, posicionElabArchivo).trim();
								String digitFicha = s.substring(posicionElabArchivo, posicionDigitFichas).trim();
								String impVolantes = s.substring(posicionTotalEmpad, posicionImpVolantes).trim();
								String impAfiches = s.substring(posicionImpVolantes, posicionImpAfiches).trim();
								String repFolletos = s.substring(posicionImpAfiches, posicionRepFolletos).trim();
								String spotPublicTv = s.substring(posicionRepFolletos, posicionSpotPublicTv).trim();
								String spotPublicRad = s.substring(posicionSpotPublicTv, posicionSpotPublicRadio).trim();
								String nroBenef = s.substring(posicionTotalDifusion, posicionNroBenef).trim();
								String promConv = s.substring(posicionCostoUnitBenef, posicionPromConvenios).trim();
								String regConv = s.substring(posicionPromConvenios, posicionRegFirmaConvenios).trim();
								String impBander = s.substring(posicionRegFirmaConvenios, posicionImpEntBanderla).trim();
								String nroAgentes = s.substring(posicionTotalCostoGestion, posicionNroAgentes).trim();
								
								//validaciones de consistencia
								if( !FormatoUtil.validarCampoLongEnteroPositivoTxt(zonaBenef) ){
									//el campo Zona benef no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2300);
								}
								if( !FormatoUtil.validarCampoBigDecimalPositivoTxt(impEsqInvit) ){
									//el campo impEsqInvit no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2310);
								}
								if( !FormatoUtil.validarCampoBigDecimalPositivoTxt(impDeclJurada) ){
									//el campo impDeclJurada no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2320);
								}
								if( !FormatoUtil.validarCampoBigDecimalPositivoTxt(impFichaVerif) ){
									//el campo impFichaVerif no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2330);
								}
								if( !FormatoUtil.validarCampoBigDecimalPositivoTxt(repEsqInvit) ){
									//el campo repEsqInvit no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2340);
								}
								if( !FormatoUtil.validarCampoBigDecimalPositivoTxt(verifInfo) ){
									//el campo verifInfo no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2350);
								}
								if( !FormatoUtil.validarCampoBigDecimalPositivoTxt(elabArch) ){
									//el campo elabArch no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2360);
								}
								if( !FormatoUtil.validarCampoBigDecimalPositivoTxt(digitFicha) ){
									//el campo digitFicha no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2370);
								}
								if( !FormatoUtil.validarCampoBigDecimalPositivoTxt(impVolantes) ){
									//el campo impVolantes no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2380);
								}
								if( !FormatoUtil.validarCampoBigDecimalPositivoTxt(impAfiches) ){
									//el campo impAfiches no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2390);
								}
								if( !FormatoUtil.validarCampoBigDecimalPositivoTxt(repFolletos) ){
									//el campo repFolletos no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2400);
								}
								if( !FormatoUtil.validarCampoBigDecimalPositivoTxt(spotPublicTv) ){
									//el campo spotPublicTv no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2410);
								}
								if( !FormatoUtil.validarCampoBigDecimalPositivoTxt(spotPublicRad) ){
									//el campo spotPublicRad no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2411);
								}
								if( !FormatoUtil.validarCampoLongEnteroPositivoTxt(nroBenef) ){
									//el campo nroBenef no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2420);
								}
								if( !FormatoUtil.validarCampoBigDecimalPositivoTxt(promConv) ){
									//el campo promConv no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2430);
								}
								if( !FormatoUtil.validarCampoBigDecimalPositivoTxt(regConv) ){
									//el campo regConv no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2440);
								}
								if( !FormatoUtil.validarCampoBigDecimalPositivoTxt(impBander) ){
									//el campo impBander no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2450);
								}
								if( !FormatoUtil.validarCampoLongEnteroPositivoTxt(nroAgentes) ){
									//el campo nroAgentes no corresponde al tipo de dato correcto
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2460);
								}
								
								//
								if( FiseConstants.BLANCO.equals(sMsg) ){
									if( FiseConstants.ZONABENEF_RURAL_COD == Long.parseLong(zonaBenef) ){
										formulario.setImprEsqInvitR(new BigDecimal(impEsqInvit));
										formulario.setImprDeclaJuradaR(new BigDecimal(impDeclJurada));
										formulario.setImprFichaVerifR(new BigDecimal(impFichaVerif));
										formulario.setRepartoEsqInvitR(new BigDecimal(repEsqInvit));
										formulario.setVerifInfoR(new BigDecimal(verifInfo));
										formulario.setElabArchivoBenefR(new BigDecimal(elabArch));
										formulario.setDigitFichaBenefR(new BigDecimal(digitFicha));
										formulario.setImprVolantesR(new BigDecimal(impVolantes));
										formulario.setImprAfichesR(new BigDecimal(impAfiches));
										formulario.setRepFolletosR(new BigDecimal(repFolletos));
										formulario.setSpotPublTvR(new BigDecimal(spotPublicTv));
										formulario.setSpotPublRadioR(new BigDecimal(spotPublicRad));
										formulario.setNroBenefEmpadR(Long.parseLong(nroBenef));
										formulario.setPromConvAgentR(new BigDecimal(promConv));
										formulario.setRegConvAgentR(new BigDecimal(regConv));
										formulario.setImpEntrBandR(new BigDecimal(impBander));
										formulario.setNroAgentR(Long.parseLong(nroAgentes));
										
									}else if( FiseConstants.ZONABENEF_PROVINCIA_COD == Long.parseLong(zonaBenef) ){
										formulario.setImprEsqInvitP(new BigDecimal(impEsqInvit));
										formulario.setImprDeclaJuradaP(new BigDecimal(impDeclJurada));
										formulario.setImprFichaVerifP(new BigDecimal(impFichaVerif));
										formulario.setRepartoEsqInvitP(new BigDecimal(repEsqInvit));
										formulario.setVerifInfoP(new BigDecimal(verifInfo));
										formulario.setElabArchivoBenefP(new BigDecimal(elabArch));
										formulario.setDigitFichaBenefP(new BigDecimal(digitFicha));
										formulario.setImprVolantesP(new BigDecimal(impVolantes));
										formulario.setImprAfichesP(new BigDecimal(impAfiches));
										formulario.setRepFolletosP(new BigDecimal(repFolletos));
										formulario.setSpotPublTvP(new BigDecimal(spotPublicTv));
										formulario.setSpotPublRadioP(new BigDecimal(spotPublicRad));
										formulario.setNroBenefEmpadP(Long.parseLong(nroBenef));
										formulario.setPromConvAgentP(new BigDecimal(promConv));
										formulario.setRegConvAgentP(new BigDecimal(regConv));
										formulario.setImpEntrBandP(new BigDecimal(impBander));
										formulario.setNroAgentP(Long.parseLong(nroAgentes));
										
									}else if( FiseConstants.ZONABENEF_LIMA_COD == Long.parseLong(zonaBenef) ){
										if( FiseConstants.COD_EMPRESA_EDELNOR.equals(formulario.getCodigoEmpresa()) || FiseConstants.COD_EMPRESA_LUZ_SUR.equals(formulario.getCodigoEmpresa()) ){
											formulario.setImprEsqInvitL(new BigDecimal(impEsqInvit));
											formulario.setImprDeclaJuradaL(new BigDecimal(impDeclJurada));
											formulario.setImprFichaVerifL(new BigDecimal(impFichaVerif));
											formulario.setRepartoEsqInvitL(new BigDecimal(repEsqInvit));
											formulario.setVerifInfoL(new BigDecimal(verifInfo));
											formulario.setElabArchivoBenefL(new BigDecimal(elabArch));
											formulario.setDigitFichaBenefL(new BigDecimal(digitFicha));
											formulario.setImprVolantesL(new BigDecimal(impVolantes));
											formulario.setImprAfichesL(new BigDecimal(impAfiches));
											formulario.setRepFolletosL(new BigDecimal(repFolletos));
											formulario.setSpotPublTvL(new BigDecimal(spotPublicTv));
											formulario.setSpotPublRadioL(new BigDecimal(spotPublicRad));
											formulario.setNroBenefEmpadL(Long.parseLong(nroBenef));
											formulario.setPromConvAgentL(new BigDecimal(promConv));
											formulario.setRegConvAgentL(new BigDecimal(regConv));
											formulario.setImpEntrBandL(new BigDecimal(impBander));
											formulario.setNroAgentL(Long.parseLong(nroAgentes));
										}else{
											formulario.setImprEsqInvitL(new BigDecimal(0));
											formulario.setImprDeclaJuradaL(new BigDecimal(0));
											formulario.setImprFichaVerifL(new BigDecimal(0));
											formulario.setRepartoEsqInvitL(new BigDecimal(0));
											formulario.setVerifInfoL(new BigDecimal(0));
											formulario.setElabArchivoBenefL(new BigDecimal(0));
											formulario.setDigitFichaBenefL(new BigDecimal(0));
											formulario.setImprVolantesL(new BigDecimal(0));
											formulario.setImprAfichesL(new BigDecimal(0));
											formulario.setRepFolletosL(new BigDecimal(0));
											formulario.setSpotPublTvL(new BigDecimal(0));
											formulario.setSpotPublRadioL(new BigDecimal(0));
											formulario.setNroBenefEmpadL(0);
											formulario.setPromConvAgentL(new BigDecimal(0));
											formulario.setRegConvAgentL(new BigDecimal(0));
											formulario.setImpEntrBandL(new BigDecimal(0));
											formulario.setNroAgentL(0);
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
									objeto = formato14Service.registrarFormato14AC(formulario);
								}else if( FiseConstants.FLAG_CARGATXT_FORMULARIOMODIFICACION.equals(flagCarga) ){
									FiseFormato14AC formatoModif = new FiseFormato14AC();
									FiseFormato14ACPK id = new FiseFormato14ACPK();
									id.setCodEmpresa(formulario.getCodigoEmpresa());
									id.setAnoPresentacion(formulario.getAnioPresent());
									id.setMesPresentacion(formulario.getMesPresent());
									id.setAnoInicioVigencia(formulario.getAnioInicioVigencia());
									id.setAnoFinVigencia(formulario.getAnioFinVigencia());
									id.setEtapa(formulario.getEtapa());
									formatoModif = formato14Service.obtenerFormato14ACByPK(id);
									objeto = formato14Service.modificarFormato14AC(formulario, formatoModif);
								}
							}
							
						}else{
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14A_2290);
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
	formatoMensaje.setFiseFormato14AC(objeto);
	formatoMensaje.setMensajeError(sMsg);
	if(listaError.size()>0)
		formatoMensaje.setListaMensajeError(listaError);
	
	return formatoMensaje;
}

@ResourceMapping("reporte")
public void reporte(ResourceRequest request,ResourceResponse response, @ModelAttribute("formato14ACBean")Formato14ACBean command) {
	try {
		HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
        HttpSession session = httpRequest.getSession();
        
	    JSONArray jsonArray = new JSONArray();	
	    FiseFormato14AC formato = new FiseFormato14AC();
	    
	    Formato14ACBean bean = new Formato14ACBean();
	    
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
	    String tipoFormato = FiseConstants.TIPO_FORMATO_14A;
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
	    
	    FiseFormato14ACPK pk = new FiseFormato14ACPK();
	    pk.setCodEmpresa(codEmpresa);
        pk.setAnoPresentacion(new Long(anoPresentacion));
        pk.setMesPresentacion(new Long(mesPresentacion));
        pk.setAnoInicioVigencia(new Long(anoInicioVigencia));
        pk.setAnoFinVigencia(new Long(anoFinVigencia));
        pk.setEtapa(etapa);

        formato = formato14Service.obtenerFormato14ACByPK(pk);
        if( formato!=null ){
        	//setamos los valores en el bean
        	bean = formato14Service.estructurarFormato14ABeanByFiseFormato14AC(formato);
        	bean.setDescEmpresa(mapaEmpresa.get(formato.getId().getCodEmpresa()));
        	bean.setDescMesPresentacion(fiseUtil.getMapaMeses().get(formato.getId().getMesPresentacion()));
        	//
        	session.setAttribute("mapa", formato14Service.mapearParametrosFormato14A(bean));
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
	public void validacion(ModelMap model, ResourceRequest request,ResourceResponse response,@ModelAttribute("formato14ACBean")Formato14ACBean command) {
	//JSONObject jsonObj = new JSONObject();
	HttpServletRequest req = PortalUtil.getHttpServletRequest(request);	        
    HttpSession session = req.getSession();
	
	ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
	try {
		FiseFormato14AC formato = new FiseFormato14AC();
		
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
	    
	    FiseFormato14ACPK pk = new FiseFormato14ACPK();
		pk.setCodEmpresa(codEmpresa);
		pk.setAnoPresentacion(new Long(anoPresentacion));
        pk.setMesPresentacion(new Long(mesPresentacion));
        pk.setAnoInicioVigencia(new Long(anoInicioVigencia));
        pk.setAnoFinVigencia(new Long(anoFinVigencia));
        pk.setEtapa(etapa);
	        
	    formato = formato14Service.obtenerFormato14ACByPK(pk);
	    if( formato!=null ){
	    	//int cont=0;
	    	Formato14Generic formato14Generic = new Formato14Generic(formato);
	    	int i = commonService.validarFormatos_14(formato14Generic, FiseConstants.NOMBRE_FORMATO_14A, themeDisplay.getUser().getLogin(), themeDisplay.getUser().getLogin());
		    if(i==0){
		    	cargarListaObservaciones(formato.getFiseFormato14ADs());
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
		    	fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_FORMATO_VAL, FiseConstants.NOMBRE_EXCEL_VALIDACION_F14A, FiseConstants.NOMBRE_HOJA_VALIDACION, listaObservaciones);
		    	
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
public void reporteValidacion(ResourceRequest request,ResourceResponse response,@ModelAttribute("formato14ACBean")Formato14ACBean command) {
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
	    CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14A);
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
public void envioDefinitivo(ResourceRequest request,ResourceResponse response,@ModelAttribute("formato14ACBean")Formato14ACBean command) {
	//FiseFormato14AC formatoActualizar = null;
	FiseFormato14AC formato = null;
	try {		
		//List<byte[]> listaBytes = new ArrayList<byte[]>();		
		HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
        HttpSession session = httpRequest.getSession();
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        JSONObject jsonObj = new JSONObject(); 
	    //FileEntry archivo=null;
	    List<FileEntryJSP> listaArchivo = new ArrayList<FileEntryJSP>();    
	    
	    Formato14ACBean bean = new Formato14ACBean();
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
	    //String tipoFormato = FiseConstants.TIPO_FORMATO_12A;
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
	    
	    FiseFormato14ACPK pk = new FiseFormato14ACPK();
	    pk.setCodEmpresa(codEmpresa);
        pk.setAnoPresentacion(new Long(anoPresentacion));
        pk.setMesPresentacion(new Long(mesPresentacion));
        pk.setAnoInicioVigencia(new Long(anoInicioVigencia));
        pk.setAnoFinVigencia(new Long(anoFinVigencia));
        pk.setEtapa(etapa);

        formato = formato14Service.obtenerFormato14ACByPK(pk);
        if( formato!=null ){
        	bean = formato14Service.estructurarFormato14ABeanByFiseFormato14AC(formato);
        	bean.setDescEmpresa(mapaEmpresa.get(formato.getId().getCodEmpresa()));
        	bean.setDescMesPresentacion(fiseUtil.getMapaMeses().get(formato.getId().getMesPresentacion()));
        	mapa = formato14Service.mapearParametrosFormato14A(bean);
        	
        	CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14A);
        	String descripcionFormato = "";
        	if( tabla!=null ){
        		descripcionFormato = tabla.getDescripcionTabla();
        	}
        	
        	Formato14Generic formato14Generic = new Formato14Generic(formato);
        	int i = commonService.validarFormatos_14(formato14Generic, FiseConstants.NOMBRE_FORMATO_14A, themeDisplay.getUser().getLogin(), themeDisplay.getUser().getLoginIP());
        	if(i==0){
        		cargarListaObservaciones(formato.getFiseFormato14ADs());
        	} 
        	//cambios flag observacion
			logger.info("Periodo para comparar :" +periodoEnvio);
			String flagEnvioObs = "";
			List<FisePeriodoEnvio> listaPeriodoEnvio = periodoService.listarFisePeriodoEnvioMesAnioEtapa(codEmpresa, FiseConstants.NOMBRE_FORMATO_14A);
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
							FiseConstants.TIPO_FORMATO_14A, 
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
	    	   
		        /**REPORTE FORMATO 14A*/
		       nombreReporte = "formato14A";
		       nombreArchivo = nombreReporte;
		       directorio =  "/reports/"+nombreReporte+".jasper";
		       File reportFile = new File(session.getServletContext().getRealPath(directorio));
		       byte[] bytes = null;
		       bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), mapa, new JREmptyDataSource());
		       if (bytes != null) {
		    	   //session.setAttribute("bytes1", bytes);
		    	   String nombre = FormatoUtil.nombreIndividualFormato(formato.getId().getCodEmpresa(), formato.getId().getAnoPresentacion(), formato.getId().getMesPresentacion(), FiseConstants.TIPO_FORMATO_14A);
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
			    	   String nombre = FormatoUtil.nombreIndividualAnexoObs(formato.getId().getCodEmpresa(), formato.getId().getAnoPresentacion(), formato.getId().getMesPresentacion(), FiseConstants.TIPO_FORMATO_14A);
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
		    	   String nombre = FormatoUtil.nombreIndividualActaRemision(formato.getId().getCodEmpresa(), formato.getId().getAnoPresentacion(), formato.getId().getMesPresentacion(), FiseConstants.TIPO_FORMATO_14A);
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
	    	   /*Formato14ACBean form = new Formato14ACBean();
	    	   form.setUsuario(themeDisplay.getUser().getLogin());
	    	   form.setTerminal(themeDisplay.getUser().getLoginIP());*/
	    	   /**actualizamos  la fecha de envio*/
	    	   String valorActuaizar = "0";
		       if(valorFormato && valorActa){
		    	   //formatoActualizar = formato14Service.modificarEnvioDefinitivoFormato14AC(form, formato);
		    	   valorActuaizar = formato14Service.modificarEnvioDefinitivoFormato14AC(
		    			   themeDisplay.getUser().getLogin(),
		    			    themeDisplay.getUser().getLoginIP(), formato);
		       }
		       if(valorActuaizar.equals("1")){
		    	   if( listaArchivo!=null && listaArchivo.size()>0 ){		    	  
		    		   respuestaEmail= fiseUtil.enviarMailsAdjunto(
			    			   request,
			    			   listaArchivo, 
			    			   mapaEmpresa.get(formato.getId().getCodEmpresa()),
			    			   formato.getId().getAnoPresentacion(),
			    			   formato.getId().getMesPresentacion(),
			    			   FiseConstants.TIPO_FORMATO_14A,
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
		    }//fin del else flag obs NO   	   	       
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

public void cargarListaObservaciones(List<FiseFormato14AD> listaDetalle){
	int cont=0;
	listaObservaciones = new ArrayList<MensajeErrorBean>();
	for (FiseFormato14AD detalle : listaDetalle) {
		detalle.setFiseFormato14ADObs(formato14Service.listarFormato14ADObByFormato14AD(detalle));
		List<FiseFormato14ADOb> listaObser = formato14Service.listarFormato14ADObByFormato14AD(detalle);
		for (FiseFormato14ADOb observacion : listaObser) {
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
			    
			FiseFormato14AC formato = new FiseFormato14AC();
			
			String tipoFormato = FiseConstants.TIPO_FORMATO_ACTAENVIO;
			String tipoArchivo = FiseConstants.FORMATO_EXPORT_PDF;
			
			CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14A);
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
			
			FiseFormato14ACPK pk = new FiseFormato14ACPK();
			pk.setCodEmpresa(codEmpresa);
			pk.setAnoPresentacion(new Long(anoPresentacion));
			pk.setMesPresentacion(new Long(mesPresentacion));
			pk.setAnoInicioVigencia(new Long(anoInicioVigencia));
			pk.setAnoFinVigencia(new Long(anoFinVigencia));
			pk.setEtapa(etapa);
			
			formato = formato14Service.obtenerFormato14ACByPK(pk);
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
						FiseConstants.TIPO_FORMATO_14A, 
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
