package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.Formato14CBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.FiseFormato14AC;
import gob.osinergmin.fise.domain.FiseFormato14ACPK;
import gob.osinergmin.fise.domain.FiseFormato14CC;
import gob.osinergmin.fise.domain.FisePeriodoEnvio;
import gob.osinergmin.fise.gart.json.Formato14AGartJSON;
import gob.osinergmin.fise.gart.json.Formato14CJSON;
import gob.osinergmin.fise.gart.service.FisePeriodoEnvioGartService;
import gob.osinergmin.fise.gart.service.Formato14CGartService;
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
import com.liferay.portal.kernel.util.JavaConstants;
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
	
	
	@ResourceMapping("busqueda")
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
	
	@ResourceMapping("crud")
	public void crud(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("formato14CBean")Formato14CBean f) { 	
		try {
			JSONObject jsonObj = new JSONObject();
			String tipo = "SAVE";//request.getParameter("tipo");   		 
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
		        
		       // formato = formato14Service.obtenerFormato14ACByPK(pk);
		        
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
				    listaPeriodoEnvio = periodoService.listarFisePeriodoEnvioMesAnioEtapa(codEmpresa, FiseConstants.NOMBRE_FORMATO_14C);
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
					logger.info("Entrando a grabar un nuevo registro en el fomato 14C"); 			
					logger.info("Codigo empresa:  "+ f.getCodEmpresa()); 
					logger.info("perido de envio:  "+ f.getPeriodoEnvio());	
					
					if( f.getPeriodoEnvio().length()>6 ){
						f.setAnioPres(f.getPeriodoEnvio().substring(0, 4));
						f.setMesPres(f.getPeriodoEnvio().substring(4, 6));
						f.setEtapa(f.getPeriodoEnvio().substring(6, f.getPeriodoEnvio().length()));
						
						
						//f.setAnioInicioVig("2009");
						//f.setAnioFinVig("2014");
						
						/*if( "S".equals(f.getFlagPeriodoEjecucion()) ){
							f.setAnioInicioVigencia(f.getAnioInicioVigencia());
							f.setAnioFinVigencia(f.getAnioFinVigencia());
						}else{
							f.setAnioInicioVigencia(f.getAnioPresent());
							f.setAnioFinVigencia(f.getAnioPresent());
						}*/					
					}					
					ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);					
					//f.setUsuario(themeDisplay.getUser().getLogin());
					//f.setTerminal(themeDisplay.getUser().getLoginIP());		
					logger.info("Enviando el formulario al service"); 
					formato14CGartService.insertarDatosFormato14C(f);
					jsonObj.put("resultado", "OK");	   				
				} catch (Exception e) {
					jsonObj.put("resultado", "Error");
					jsonObj.put("mensaje", e.getMessage());
					logger.error("Error al guardar los datos en el formato 14C: "+e.getMessage());
				}   				   				
					 					 				
			}else if(tipo.equals(FiseConstants.COD_UPDATE)){
				logger.info("entro a modificar valores en el formato 14C");				
				try {
					
					//FiseFormato14AC formato = new FiseFormato14AC();
					//FiseFormato14ACPK pk = new FiseFormato14ACPK();
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
					
			        
			      //  Formato14ACBean formulario = new Formato14ACBean();
			        
			      //RURAL
					//f.setTotalEmpadR(f.getTotalEmpadR());
					/*f.setImprEsqInvitR(f.getImprEsqInvitR());
					f.setImprDeclaJuradaR(f.getImprDeclaJuradaR());
					f.setImprFichaVerifR(f.getImprFichaVerifR());
					f.setRepartoEsqInvitR(f.getRepartoEsqInvitR());
					f.setVerifInfoR(f.getVerifInfoR());
					f.setElabArchivoBenefR(f.getElabArchivoBenefR());
					f.setDigitFichaBenefR(f.getDigitFichaBenefR());
					//f.setTotalDifIniProgR(f.getTotalDifIniProgR());
					f.setImprVolantesR(f.getImprVolantesR());
					f.setImprAfichesR(f.getImprAfichesR());
					f.setRepFolletosR(f.getRepFolletosR());
					f.setSpotPublTvR(f.getSpotPublTvR());
					f.setSpotPublRadioR(f.getSpotPublRadioR());
					f.setNroBenefEmpadR(f.getNroBenefEmpadR());
					//f.setCostoUnitEmpadR(f.getCostoUnitEmpadR());
					//
					//f.setTotalCostoAgentR(f.getTotalCostoAgentR());
					f.setPromConvAgentR(f.getPromConvAgentR());
					f.setRegConvAgentR(f.getRegConvAgentR());
					f.setImpEntrBandR(f.getImpEntrBandR());
					f.setNroAgentR(f.getNroAgentR());
					//f.setCostoUnitAgentR(f.getCostoUnitAgentR());
					//PROVINCIA
					//f.setTotalEmpadP(f.getTotalEmpadP());
					f.setImprEsqInvitP(f.getImprEsqInvitP());
					f.setImprDeclaJuradaP(f.getImprDeclaJuradaP());
					f.setImprFichaVerifP(f.getImprFichaVerifP());
					f.setRepartoEsqInvitP(f.getRepartoEsqInvitP());
					f.setVerifInfoP(f.getVerifInfoP());
					f.setElabArchivoBenefP(f.getElabArchivoBenefP());
					f.setDigitFichaBenefP(f.getDigitFichaBenefP());
					//f.setTotalDifIniProgP(f.getTotalDifIniProgP());
					f.setImprVolantesP(f.getImprVolantesP());
					f.setImprAfichesP(f.getImprAfichesP());
					f.setRepFolletosP(f.getRepFolletosP());
					f.setSpotPublTvP(f.getSpotPublTvP());
					f.setSpotPublRadioP(f.getSpotPublRadioP());
					f.setNroBenefEmpadP(f.getNroBenefEmpadP());
					//f.setCostoUnitEmpadP(f.getCostoUnitEmpadP());
					//
					//f.setTotalCostoAgentP(f.getTotalCostoAgentP());
					f.setPromConvAgentP(f.getPromConvAgentP());
					f.setRegConvAgentP(f.getRegConvAgentP());
					f.setImpEntrBandP(f.getImpEntrBandP());
					f.setNroAgentP(f.getNroAgentP());
					//f.setCostoUnitAgentP(f.getCostoUnitAgentP());
					//LIMA
					//f.setTotalEmpadL(f.getTotalEmpadL());
					f.setImprEsqInvitL(f.getImprEsqInvitL());
					f.setImprDeclaJuradaL(f.getImprDeclaJuradaL());
					f.setImprFichaVerifL(f.getImprFichaVerifL());
					f.setRepartoEsqInvitL(f.getRepartoEsqInvitL());
					f.setVerifInfoL(f.getVerifInfoL());
					f.setElabArchivoBenefL(f.getElabArchivoBenefL());
					f.setDigitFichaBenefL(f.getDigitFichaBenefL());
					//f.setTotalDifIniProgL(f.getTotalDifIniProgL());
					f.setImprVolantesL(f.getImprVolantesL());
					f.setImprAfichesL(f.getImprAfichesL());
					f.setRepFolletosL(f.getRepFolletosL());
					f.setSpotPublTvL(f.getSpotPublTvL());
					f.setSpotPublRadioL(f.getSpotPublRadioL());
					f.setNroBenefEmpadL(f.getNroBenefEmpadL());
					//f.setCostoUnitEmpadL(f.getCostoUnitEmpadL());
					//
					//f.setTotalCostoAgentL(f.getTotalCostoAgentL());
					f.setPromConvAgentL(f.getPromConvAgentL());
					f.setRegConvAgentL(f.getRegConvAgentL());
					f.setImpEntrBandL(f.getImpEntrBandL());
					f.setNroAgentL(f.getNroAgentL());
					//f.setCostoUnitAgentR(f.getCostoUnitAgentR());
			        
			        f.setCodigoEmpresa(codEmpresa);*/
					
					if( periodoEnvio.length()>6 ){
						f.setAnioPres(periodoEnvio.substring(0, 4));
						f.setMesPres(periodoEnvio.substring(4, 6));
						f.setEtapa(periodoEnvio.substring(6, periodoEnvio.length()));
						
						/*if( "S".equals(flagPeriodoEjecucion) ){
							f.setAnioInicioVigencia(Long.parseLong(anoInicioVigencia));
							f.setAnioFinVigencia(Long.parseLong(anoFinVigencia));
						}else{
							f.setAnioInicioVigencia(f.getAnioPresent());
							f.setAnioFinVigencia(f.getAnioPresent());
						}*/
					}
					
				/*	pk.setCodEmpresa(f.getCodigoEmpresa());
			        pk.setAnoPresentacion(f.getAnioPresent());
			        pk.setMesPresentacion(f.getMesPresent());
			        pk.setAnoInicioVigencia(f.getAnioInicioVigencia());
			        pk.setAnoFinVigencia(f.getAnioFinVigencia());
			        pk.setEtapa(f.getEtapa());
			        
			        formato = formato14Service.obtenerFormato14ACByPK(pk);
					logger.info("objeto "+formato);
			        formato14Service.modificarFormato14AC(formulario, formato);*/
					jsonObj.put("resultado", "OK"); 	
				} catch (Exception e) {
					jsonObj.put("resultado", "Error");
					jsonObj.put("mensaje", e.getMessage());
					System.out.println("Error al actualizar datos en la tabla fiseformato14C: "+e.getMessage());
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
					
			      //  formato = formato14Service.obtenerFormato14ACByPK(pk);
			        logger.info("valorpobjeto"+formato);
			        
			        //metodo delete
			       // formato14Service.eliminarFormato14AC(formato);
			        jsonObj.put("resultado", "OK");
				} catch (Exception e) {
					jsonObj.put("resultado", "Error");
					jsonObj.put("mensaje", e.getMessage());
					System.out.println("Error al eliminar datos del formato 14C "+e.getMessage());
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
  	public void cargaPeriodo(ModelMap model, ResourceRequest request,ResourceResponse response,@ModelAttribute("formato14CBean")Formato14CBean f){
		try {			
  			response.setContentType("applicacion/json");
  			String codEmpresa = f.getCodEmpresa();
  			logger.info("Codigo Empresa para cargar el periodo:  "+codEmpresa);
  			//String periodoEnvio = f.getPeriodoEnvio();
  			//lo pongo en la lista porque no persiste las colecciones en el command
  			listaPeriodoEnvio = periodoService.listarFisePeriodoEnvioMesAnioEtapa(codEmpresa, FiseConstants.TIPO_FORMATO_12C);
  			f.setListaPeriodoEnvio(listaPeriodoEnvio);
  			
  			JSONArray jsonArray = new JSONArray();
  			for (FisePeriodoEnvio periodo : f.getListaPeriodoEnvio()) {
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
