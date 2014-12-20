package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.EnvioDefinitivoBean;
import gob.osinergmin.fise.bean.Formato12A12BGeneric;
import gob.osinergmin.fise.bean.Formato12ACBean;
import gob.osinergmin.fise.bean.Formato12BCBean;
import gob.osinergmin.fise.bean.Formato12C12D13Generic;
import gob.osinergmin.fise.bean.Formato12CCBean;
import gob.osinergmin.fise.bean.Formato12DCBean;
import gob.osinergmin.fise.bean.Formato13ACBean;
import gob.osinergmin.fise.bean.Formato13ADReportBean;
import gob.osinergmin.fise.bean.Formato14ACBean;
import gob.osinergmin.fise.bean.Formato14BCBean;
import gob.osinergmin.fise.bean.Formato14CBean;
import gob.osinergmin.fise.bean.Formato14CReportBean;
import gob.osinergmin.fise.bean.Formato14Generic;
import gob.osinergmin.fise.bean.MensajeErrorBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.CfgTabla;
import gob.osinergmin.fise.domain.FiseControlEnvioPorGrupo;
import gob.osinergmin.fise.domain.FiseFormato12AC;
import gob.osinergmin.fise.domain.FiseFormato12ACPK;
import gob.osinergmin.fise.domain.FiseFormato12AD;
import gob.osinergmin.fise.domain.FiseFormato12ADOb;
import gob.osinergmin.fise.domain.FiseFormato12BC;
import gob.osinergmin.fise.domain.FiseFormato12BCPK;
import gob.osinergmin.fise.domain.FiseFormato12BD;
import gob.osinergmin.fise.domain.FiseFormato12BDOb;
import gob.osinergmin.fise.domain.FiseFormato12CC;
import gob.osinergmin.fise.domain.FiseFormato12CCPK;
import gob.osinergmin.fise.domain.FiseFormato12CD;
import gob.osinergmin.fise.domain.FiseFormato12CDOb;
import gob.osinergmin.fise.domain.FiseFormato12DC;
import gob.osinergmin.fise.domain.FiseFormato12DCPK;
import gob.osinergmin.fise.domain.FiseFormato12DD;
import gob.osinergmin.fise.domain.FiseFormato12DDOb;
import gob.osinergmin.fise.domain.FiseFormato13AC;
import gob.osinergmin.fise.domain.FiseFormato13ACPK;
import gob.osinergmin.fise.domain.FiseFormato13AD;
import gob.osinergmin.fise.domain.FiseFormato13ADOb;
import gob.osinergmin.fise.domain.FiseFormato14AC;
import gob.osinergmin.fise.domain.FiseFormato14ACPK;
import gob.osinergmin.fise.domain.FiseFormato14AD;
import gob.osinergmin.fise.domain.FiseFormato14ADOb;
import gob.osinergmin.fise.domain.FiseFormato14BC;
import gob.osinergmin.fise.domain.FiseFormato14BCPK;
import gob.osinergmin.fise.domain.FiseFormato14BD;
import gob.osinergmin.fise.domain.FiseFormato14BDOb;
import gob.osinergmin.fise.domain.FiseFormato14CC;
import gob.osinergmin.fise.domain.FiseFormato14CD;
import gob.osinergmin.fise.domain.FiseFormato14CDOb;
import gob.osinergmin.fise.domain.FiseGrupoInformacion;
import gob.osinergmin.fise.gart.jsp.FileEntryJSP;
import gob.osinergmin.fise.gart.service.CfgTablaGartService;
import gob.osinergmin.fise.gart.service.CommonGartService;
import gob.osinergmin.fise.gart.service.FiseGrupoInformacionGartService;
import gob.osinergmin.fise.gart.service.Formato12AGartService;
import gob.osinergmin.fise.gart.service.Formato12BGartService;
import gob.osinergmin.fise.gart.service.Formato12CGartService;
import gob.osinergmin.fise.gart.service.Formato12DGartService;
import gob.osinergmin.fise.gart.service.Formato13AGartService;
import gob.osinergmin.fise.gart.service.Formato14AGartService;
import gob.osinergmin.fise.gart.service.Formato14BGartService;
import gob.osinergmin.fise.gart.service.Formato14CGartService;
import gob.osinergmin.fise.util.FechaUtil;
import gob.osinergmin.fise.util.FormatoUtil;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;


@Controller("envioDefinitivoController")
@RequestMapping("VIEW")
public class EnvioDefinitivoController {
	
	Logger logger = Logger.getLogger(EnvioDefinitivoController.class);
	
	@Autowired
	@Qualifier("commonGartServiceImpl")
	private CommonGartService commonService;
	
	@Autowired
	@Qualifier("fiseUtil")
	private FiseUtil fiseUtil;	
	
	@Autowired
	@Qualifier("fiseGrupoInformacionGartServiceImpl")
	private FiseGrupoInformacionGartService fiseGrupoInformacionService;
	
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
	
	
	@Autowired
	@Qualifier("cfgTablaGartServiceImpl")
	private CfgTablaGartService tablaService;
	
	
	
	private Map<String, String> mapaEmpresa;
	private Map<String, String> mapaErrores;
	private Map<String, String> mapaSectorTipico;
	private Map<Long, String> mapaEtapaEjecucion;
	
	
	private List<MensajeErrorBean> listaObs12A;
	private List<MensajeErrorBean> listaObs12B;
	private List<MensajeErrorBean> listaObs12C;
	private List<MensajeErrorBean> listaObs12D;
	private List<MensajeErrorBean> listaObs13A;
	private List<MensajeErrorBean> listaObs14A;
	private List<MensajeErrorBean> listaObs14B;
	private List<MensajeErrorBean> listaObs14C;	
	
	private List<Formato13ADReportBean> listaZonas13A;
	
	
	@ModelAttribute("envioDefinitivoBean")
    public EnvioDefinitivoBean listEnvioDefinitivoBean() {
		EnvioDefinitivoBean comman  = new EnvioDefinitivoBean();
        return comman;	        
    }

	
	@RequestMapping
	public String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse,
			     @ModelAttribute("envioDefinitivoBean")EnvioDefinitivoBean n){
        try {           	
    		if(n.getListaEmpresas()==null){
    			logger.info("Lista de empresa es null:  " +renderRequest); 
    			n.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));
    		}     		
    		n.setAdmin(fiseUtil.esAdministrador(renderRequest));
    		
    		n.setListaGrupoInf(fiseGrupoInformacionService.listarGrupoInformacion(FiseConstants.MENSUAL)); 
    		
    		mapaEmpresa = fiseUtil.getMapaEmpresa();
    		
    		mapaErrores = fiseUtil.getMapaErrores();
    		
    		mapaSectorTipico = fiseUtil.getMapaSectorTipico();
    		
    		mapaEtapaEjecucion = fiseUtil.getMapaEtapaEjecucion();
    		
    		model.addAttribute("model", n);
    		
		} catch (Exception e) {
			logger.info("Ocurrio un errror al caragar la pagina notificacion-validacion"); 
			e.printStackTrace();
		}		
		return "envioDefinitivo";
	}
	
	
	@ResourceMapping("busquedaEnvioDefinitivo")
  	public void busqueda(ResourceRequest request,ResourceResponse response,
  			 @ModelAttribute("envioDefinitivoBean")EnvioDefinitivoBean n){
		
		try{
			response.setContentType("application/json");
			
			PortletRequest pRequest = (PortletRequest) request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
			
			long idGrupo=0;
			String codEmpresa = n.getCodEmpresaBusq();				
			String optionFormato = n.getOptionFormato();
			String idgrupoInf = n.getGrupoInfBusq();
			String etapa = n.getEtapaBusq();
		    if(FormatoUtil.isNotBlank(n.getGrupoInfBusq())){ 
		    	idGrupo = new Long(idgrupoInf);
		    }
			String data ="";			
			logger.info("codigo empresa "+ codEmpresa);  			
  			logger.info("id Grupo inf "+ idgrupoInf);  	 			
  			logger.info("Option formato "+ optionFormato);
  			logger.info("etapa "+ etapa);
  			
  			
  			List<EnvioDefinitivoBean> lista =commonService.buscarEnvioDefinitivo(codEmpresa, 
  					optionFormato, etapa,idGrupo );		
  			
  			logger.info("tamaño de la lista envio Defin..   :"+lista.size());
  			
  			List<EnvioDefinitivoBean> listaEnvio = new ArrayList<EnvioDefinitivoBean>();
  			
  			for(EnvioDefinitivoBean not : lista){    				
  				not.setDesEmpresa(mapaEmpresa.get(not.getCodEmpresa()));
  				not.setDesMes(fiseUtil.getMapaMeses().get(Long.valueOf(not.getMesPres())));
  				if(!"00".equals(not.getMesEjec())){ 
  					not.setDesMesEje(fiseUtil.getMapaMeses().get(Long.valueOf(not.getMesEjec())));   		
  				}else{
  					not.setDesMesEje("---");
  				}
  				listaEnvio.add(not);
  			} 			
  			data = toStringListJSON(listaEnvio);
  			logger.info("arreglo json:"+data);
  			PrintWriter pw = response.getWriter();
  			pw.write(data);
  			pw.flush();
  			pw.close(); 
  			pRequest.getPortletSession().setAttribute("listaEnvioDefinitivo", listaEnvio, PortletSession.APPLICATION_SCOPE);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}	
	
	private String toStringListJSON(List<EnvioDefinitivoBean> lista) {
		Serializer serializer = new JsonSerializer();
		Object result = serializer.serialize(lista);
		String data = String.valueOf(result);
		return data;
	}
	
	@ResourceMapping("cargarGrupoInformacion")
  	public void cargaPeriodo(ModelMap model, ResourceRequest request,ResourceResponse response,
  			 @ModelAttribute("envioDefinitivoBean")EnvioDefinitivoBean n){
		try {			
  			response.setContentType("applicacion/json");
  			String tipoFormato = n.getOptionFormato();
  			
  			logger.info("Codigo grupo inf. para cargar grupo de infor.:  "+tipoFormato);
  			
  			List<FiseGrupoInformacion> listaGrupoInf = fiseGrupoInformacionService.listarGrupoInformacion(tipoFormato);
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
	
		
	/*****PROCSEAR LA VALIDACIONES DE OBSERVACIONES POR PERIODICIDAD*******/
  	public boolean procesarValidacionObs(List<EnvioDefinitivoBean> lista,String user,String terminal){	
  		boolean valor = true;
  		try{ 			
  			logger.info("tamaño de la lista envio general para procesar validacion :"+lista.size());
  			for(EnvioDefinitivoBean not:lista){
  				
  				if(FiseConstants.NOMBRE_FORMATO_12A.equals(not.getFormato())){ 
  					FiseFormato12ACPK pk = new FiseFormato12ACPK();
  	  				pk.setCodEmpresa(not.getCodEmpresa());
  	  				pk.setAnoPresentacion(new Long(not.getAnioPres()));
  	  		        pk.setMesPresentacion(new Long(not.getMesPres()));
  	  		        pk.setAnoEjecucionGasto(new Long(not.getAnioEjec()));
  	  		        pk.setMesEjecucionGasto(new Long(not.getMesEjec()));
  	  		        pk.setEtapa(not.getEtapa());  	  			        
  	  		        FiseFormato12AC formato12A = formatoService12A.obtenerFormato12ACByPK(pk);
  	  		        int i = formatoService12A.validarFormato12A(formato12A, FiseConstants.NOMBRE_FORMATO_12A,
  	  		        	user, terminal);
  	  		        if(i!=0){
  	  		          valor = false;
  	  		          break;
  	  		        }
  	  		        cargarListaObservaciones12A(formato12A.getFiseFormato12ADs());	
  	  		        
  				}else if(FiseConstants.NOMBRE_FORMATO_12B.equals(not.getFormato())){ 
  					FiseFormato12BCPK pk=new FiseFormato12BCPK();
  					pk.setCodEmpresa(not.getCodEmpresa());
  	  				pk.setAnoPresentacion(new Integer(not.getAnioPres()));
  	  		        pk.setMesPresentacion(new Integer(not.getMesPres()));
  	  		        pk.setAnoEjecucionGasto(new Integer(not.getAnioEjec()));
  	  		        pk.setMesEjecucionGasto(new Integer(not.getMesEjec()));
  	  		        pk.setEtapa(not.getEtapa());  
  	  		        FiseFormato12BC formato12B =formatoService12B.getFormatoCabeceraById(pk);  	  		       
  	  		       logger.info("Tamaño de la lista detalle   "+formato12B.getListaDetalle12BDs());
  	  		        Formato12A12BGeneric formato12Generic = new Formato12A12BGeneric(formato12B);
	        	    int i = commonService.validarFormatos_12A12B(formato12Generic, 
	        	    		FiseConstants.NOMBRE_FORMATO_12B, 
	        	    		user, terminal);
	        	    if(i!=0){
	        	    	valor = false;
	        	    	break;
	        	    }
	        	    cargarListaObservaciones12B(formato12B.getListaDetalle12BDs());
	        	    
  				}else if(FiseConstants.NOMBRE_FORMATO_12C.equals(not.getFormato())){ 
  					FiseFormato12CCPK pk = new FiseFormato12CCPK();
  					pk.setCodEmpresa(not.getCodEmpresa());
  					pk.setAnoPresentacion(new Long(not.getAnioPres()));
  	  		        pk.setMesPresentacion(new Long(not.getMesPres()));
  	  		        pk.setEtapa(not.getEtapa()); 
  	  		        FiseFormato12CC formato12C = formatoService12C.obtenerFormato12CCByPK(pk);
  	  		        Formato12C12D13Generic formato13Generic = new Formato12C12D13Generic(formato12C);
				    int i = commonService.validarFormatos_12C12D13A(formato13Generic, FiseConstants.NOMBRE_FORMATO_12C,
				    		user, terminal);
				    if(i!=0){
				    	valor = false;
				    	break;
		  		    } 
				    cargarListaObservaciones12C(formato12C.getFiseFormato12CDs());
				    
  				}else if(FiseConstants.NOMBRE_FORMATO_12D.equals(not.getFormato())){ 
  					FiseFormato12DCPK pk = new FiseFormato12DCPK();
  					pk.setCodEmpresa(not.getCodEmpresa());
  					pk.setAnoPresentacion(new Long(not.getAnioPres()));
  	  		        pk.setMesPresentacion(new Long(not.getMesPres()));
  	  		        pk.setEtapa(not.getEtapa()); 
  					FiseFormato12DC formato12D = formatoService12D.obtenerFormato12DCByPK(pk);
  					Formato12C12D13Generic formato12Generic = new Formato12C12D13Generic(formato12D);
  					int i = commonService.validarFormatos_12C12D13A(formato12Generic, FiseConstants.NOMBRE_FORMATO_12D,
  							user, terminal);
  					if (i != 0) {
  						valor = false;
				    	break;	
  					}  					
  					cargarListaObservaciones12D(formato12D.getFiseFormato12DDs());
  					
  				}else if(FiseConstants.NOMBRE_FORMATO_13A.equals(not.getFormato())){ 
  					FiseFormato13ACPK pk = new FiseFormato13ACPK();
  					pk.setCodEmpresa(not.getCodEmpresa());
  	  				pk.setAnoPresentacion(new Long(not.getAnioPres()));
  	  		        pk.setMesPresentacion(new Long(not.getMesPres()));
  	  		        pk.setEtapa(not.getEtapa());  	 
  	  		        FiseFormato13AC formato13A = formatoService13A.obtenerFormato13ACByPK(pk);	
  	  		        Formato12C12D13Generic formato13Generic = new Formato12C12D13Generic(formato13A);
  	  		       int i = commonService.validarFormatos_12C12D13A(formato13Generic, FiseConstants.NOMBRE_FORMATO_13A,
  	  		    		user, terminal);
  	  		       if(i!=0){
	  		          valor = false;
	  		          break;
	  		        } 
  	  		        cargarListaObservaciones13A(formato13A.getFiseFormato13ADs());
  	  		        
  				}else if(FiseConstants.NOMBRE_FORMATO_14A.equals(not.getFormato())){ 
  					FiseFormato14ACPK pk = new FiseFormato14ACPK();
  					pk.setCodEmpresa(not.getCodEmpresa());
  	  				pk.setAnoPresentacion(new Long(not.getAnioPres()));
  	  		        pk.setMesPresentacion(new Long(not.getMesPres()));
  			        pk.setAnoInicioVigencia(new Long(not.getAnioIniVig()));
  			        pk.setAnoFinVigencia(new Long(not.getAnioFinVig()));
  			        pk.setEtapa(not.getEtapa());  				        
  			        FiseFormato14AC formato14A = formatoService14A.obtenerFormato14ACByPK(pk);	
  			        Formato14Generic formato14Generic = new Formato14Generic(formato14A);
  		    	   int i = commonService.validarFormatos_14(formato14Generic, FiseConstants.NOMBRE_FORMATO_14A,
  		    			 user, terminal);
  		    	   if(i!=0){
	  		          valor = false;
	  		          break;
	  		       }
  		    	   cargarListaObservaciones14A(formato14A.getFiseFormato14ADs());
  		    	   
  				}else if(FiseConstants.NOMBRE_FORMATO_14B.equals(not.getFormato())){ 
  					FiseFormato14BCPK pk = new FiseFormato14BCPK();
  					pk.setCodEmpresa(not.getCodEmpresa());
  	  				pk.setAnoPresentacion(new Long(not.getAnioPres()));
  	  		        pk.setMesPresentacion(new Long(not.getMesPres()));
  			        pk.setAnoInicioVigencia(new Long(not.getAnioIniVig()));
  			        pk.setAnoFinVigencia(new Long(not.getAnioFinVig()));
  			        pk.setEtapa(not.getEtapa());  						        
  			        FiseFormato14BC formato14B = formatoService14B.obtenerFormato14BCByPK(pk);	
  			        Formato14Generic formato14Generic = new Formato14Generic(formato14B);
  		    	    int i = commonService.validarFormatos_14(formato14Generic, FiseConstants.NOMBRE_FORMATO_14B,
  		    	    		user, terminal);
  		    	   if(i!=0){
	  		          valor = false;
	  		          break;
	  		       }
  		    	   cargarListaObservaciones14B(formato14B.getFiseFormato14BDs());	
  		    	   
  				}else if(FiseConstants.NOMBRE_FORMATO_14C.equals(not.getFormato())){ 
  					Formato14CBean f14C = new Formato14CBean();  					
  					f14C.setCodEmpresa(not.getCodEmpresa());
  					f14C.setAnioPres(not.getAnioPres());
  					f14C.setMesPres(not.getMesPres());
  					f14C.setAnoIniVigencia(not.getAnioIniVig());
  					f14C.setAnoFinVigencia(not.getAnioFinVig());
  					f14C.setEtapa(not.getEtapa());
  					logger.info(" anio ini vig  "+f14C.getAnoIniVigencia()+ "   "+not.getAnioIniVig());
  					logger.info(" anio fin vig  "+f14C.getAnoFinVigencia()+ "   "+not.getAnioFinVig());
  					FiseFormato14CC formato14C = formatoService14C.obtenerFiseFormato14CC(f14C);
  					Formato14Generic formato14Generic = new Formato14Generic(formato14C);
  			    	int i = commonService.validarFormatos_14(formato14Generic, FiseConstants.NOMBRE_FORMATO_14C,
  			    			user, terminal);
  			    	if(i!=0){
  			    		valor = false;
  			    		break;
  			    	}
  			    	cargarListaObservaciones14C(formato14C.getListaDetalle14cDs());
  				}				
  			}//fin del for de la lista	 				
		} catch (Exception e) {
			valor = false;
			e.printStackTrace();
			logger.error(e.getMessage());
		}
  		return valor;
	}
  	
  	/*****LLENAR OBSERVACIONES FORMATOS MENSUALES*******/
  	private void cargarListaObservaciones12A(List<FiseFormato12AD> listaDetalle){
		int cont=0;
		listaObs12A = new ArrayList<MensajeErrorBean>();
		for (FiseFormato12AD detalle : listaDetalle) {
			detalle.setFiseFormato12ADObs(formatoService12A.listarFormato12ADObByFormato12AD(detalle));
			List<FiseFormato12ADOb> listaObser = formatoService12A.listarFormato12ADObByFormato12AD(detalle);
			for (FiseFormato12ADOb observacion : listaObser) {
				cont++;
				MensajeErrorBean obs = new MensajeErrorBean();
				obs.setId(cont);
				obs.setDescZonaBenef(fiseUtil.getMapaZonaBenef().get(observacion.getId().getIdZonaBenef()));
				obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
				obs.setDescripcion(mapaErrores.get(observacion.getFiseObservacion().getIdObservacion()));
				//obs.setDescCodSectorTipico(mapaSectorTipico.get(observacion.getId().getCodSectorTipico()));
				listaObs12A.add(obs);
			}
		}
	}
  	
  	private void cargarListaObservaciones12B(List<FiseFormato12BD> listaDetalle){
		int cont=0;
		listaObs12B = new ArrayList<MensajeErrorBean>();
		//logger.info("Tamaño de la lista detalle de B:   "+listaDetalle.size()); 
		for (FiseFormato12BD detalle : listaDetalle) {
			detalle.setFiseFormato12BDObs(formatoService12B.getLstFormatoObs(detalle));			
			List<FiseFormato12BDOb> listaObser = formatoService12B.getLstFormatoObs(detalle); 
			for (FiseFormato12BDOb observacion : listaObser) {
				cont++;
				MensajeErrorBean obs = new MensajeErrorBean();
				obs.setId(cont);
				obs.setDescZonaBenef(observacion.getId()!=null?
						(observacion.getId().getIdZonaBenef()==FiseConstants.ZONA_RURAL?FiseConstants.ZONABENEF_RURAL_DESC:
						(observacion.getId().getIdZonaBenef()==FiseConstants.ZONA_LIMA?FiseConstants.ZONABENEF_LIMA_DESC:
						(observacion.getId().getIdZonaBenef()==FiseConstants.ZONA_PROVINCIA?FiseConstants.ZONABENEF_PROVINCIA_DESC:""))
						):"");
				obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());				
				obs.setDescripcion(observacion.getFiseObservacion().getDescripcion());				
				listaObs12B.add(obs);
			}
		}
	}
  	
  	private void cargarListaObservaciones12C(List<FiseFormato12CD> listaDetalle){
		int cont=0;
		listaObs12C = new ArrayList<MensajeErrorBean>();
		for (FiseFormato12CD detalle : listaDetalle) {
			List<FiseFormato12CDOb> listaObser = formatoService12C.listarFormato12CDObByFormato12CD(detalle);
			for (FiseFormato12CDOb observacion : listaObser) {
				cont++;
				MensajeErrorBean obs = new MensajeErrorBean();
				obs.setId(cont);
				obs.setNroItemEtapa(observacion.getId().getNumeroItemEtapa());
				obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
				obs.setDescripcion(mapaErrores.get(observacion.getFiseObservacion().getIdObservacion()));
				obs.setDescEtapaEjecucion(mapaEtapaEjecucion.get(observacion.getId().getEtapaEjecucion()));
				listaObs12C.add(obs);
			}
		}
	}
  	
  	public void cargarListaObservaciones12D(List<FiseFormato12DD> listaDetalle) {
		int cont = 0;
		listaObs12D = new ArrayList<MensajeErrorBean>();
		for (FiseFormato12DD detalle : listaDetalle) {
			List<FiseFormato12DDOb> listaObser = formatoService12D.listarFormato12DDObByFormato12DD(detalle);
			for (FiseFormato12DDOb observacion : listaObser) {
				cont++;
				MensajeErrorBean obs = new MensajeErrorBean();
				obs.setId(cont);
				obs.setNroItemEtapa(observacion.getId().getNumeroItemEtapa());
				obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
				obs.setDescripcion(mapaErrores.get(observacion.getFiseObservacion().getIdObservacion()));
				obs.setDescEtapaEjecucion(mapaEtapaEjecucion.get(observacion.getId().getEtapaEjecucion()));
				listaObs12D.add(obs);
			}
		}
	}
	
  	
  	/*****LLENAR OBSERVACIONES FORMATOS BIENALES*******/
	private void cargarListaObservaciones13A(List<FiseFormato13AD> listaDetalle) {
		int cont = 0;
		listaObs13A = new ArrayList<MensajeErrorBean>();
		for (FiseFormato13AD detalle : listaDetalle) {
			List<FiseFormato13ADOb> listaObser = formatoService13A.listarFormato13ADObByFormato13AD(detalle);
			for (FiseFormato13ADOb observacion : listaObser) {
				cont++;
				MensajeErrorBean obs = new MensajeErrorBean();
				obs.setId(cont);
				obs.setDescZonaBenef(fiseUtil.getMapaZonaBenef().get(observacion.getId().getIdZonaBenef()));
				obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
				obs.setDescripcion(mapaErrores.get(observacion.getFiseObservacion().getIdObservacion()));
				obs.setDescCodSectorTipico(mapaSectorTipico.get(observacion.getId().getCodSectorTipico()));
				listaObs13A.add(obs);
			}
		}
	}
	
	private void cargarListaObservaciones14A(List<FiseFormato14AD> listaDetalle){
		int cont=0;
		listaObs14A = new ArrayList<MensajeErrorBean>();
		for (FiseFormato14AD detalle : listaDetalle) {
			detalle.setFiseFormato14ADObs(formatoService14A.listarFormato14ADObByFormato14AD(detalle));
			List<FiseFormato14ADOb> listaObser = formatoService14A.listarFormato14ADObByFormato14AD(detalle);
			for (FiseFormato14ADOb observacion : listaObser) {
				cont++;
				MensajeErrorBean obs = new MensajeErrorBean();
				obs.setId(cont);
				obs.setDescZonaBenef(fiseUtil.getMapaZonaBenef().get(observacion.getId().getIdZonaBenef()));
				obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
				obs.setDescripcion(mapaErrores.get(observacion.getFiseObservacion().getIdObservacion()));
				listaObs14A.add(obs);
			}
		}
	}
	
	private void cargarListaObservaciones14B(List<FiseFormato14BD> listaDetalle){
		int cont=0;
		listaObs14B = new ArrayList<MensajeErrorBean>();
		for (FiseFormato14BD detalle : listaDetalle) {
			detalle.setFiseFormato14BDObs(formatoService14B.listarFormato14BDObByFormato14BD(detalle));
			List<FiseFormato14BDOb> listaObser = formatoService14B.listarFormato14BDObByFormato14BD(detalle);
			for (FiseFormato14BDOb observacion : listaObser) {
				cont++;
				MensajeErrorBean obs = new MensajeErrorBean();
				obs.setId(cont);
				obs.setDescZonaBenef(fiseUtil.getMapaZonaBenef().get(observacion.getId().getIdZonaBenef()));
				obs.setCodigo(observacion.getFiseObservacion().getIdObservacion());
				obs.setDescripcion(mapaErrores.get(observacion.getFiseObservacion().getIdObservacion()));
				listaObs14B.add(obs);
			}
		}
	}
	
	private void cargarListaObservaciones14C(List<FiseFormato14CD> listaDetalle) throws Exception{
		int cont=0;
		listaObs14C = new ArrayList<MensajeErrorBean>();
		for (FiseFormato14CD detalle : listaDetalle) {			
			List<FiseFormato14CDOb> listaObser = formatoService14C.listaObservacionesF14C(detalle);
			logger.info("Tamaño de lista de observaciones:  "+listaObser.size()); 
			for (FiseFormato14CDOb o : listaObser) {			
				cont++;
				MensajeErrorBean obs = new MensajeErrorBean();
				obs.setId(cont);
				obs.setDescZonaBenef(fiseUtil.getMapaZonaBenef().get(o.getId().getIdZonaBenef()));
				//obs.setDescZonaBenef(mapaZonaBenef.get(o.getId().getIdZonaBenef()));
				obs.setCodigo(o.getFiseObservacion().getIdObservacion());
				obs.setDescripcion(mapaErrores.get(o.getFiseObservacion().getIdObservacion()));
				listaObs14C.add(obs);							
			}
		}
	}	
	
	/*****LLENAR MAP DE PARAMETROS FORMATOS MENSUALES*******/	
	private Map<String, Object> parametros12A(String codEmpresa,String anioPres,String mesPres,
			String anioEjec,String mesEjec,String etapa,
			String rutaImg,String usuario,String terminal,String email){
		
		Map<String, Object> mapa = null;		
		FiseFormato12AC formato =null;
		Formato12ACBean bean =null;
		FiseFormato12ACPK pk =null;
		try {
			pk = new FiseFormato12ACPK();
		    pk.setCodEmpresa(codEmpresa);
	        pk.setAnoPresentacion(new Long(anioPres));
	        pk.setMesPresentacion(new Long(mesPres));
	        pk.setAnoEjecucionGasto(new Long(anioEjec));
	        pk.setMesEjecucionGasto(new Long(mesEjec));
	        pk.setEtapa(etapa);

	        formato = formatoService12A.obtenerFormato12ACByPK(pk);		   
		    if( formato!=null ){  	    	
		    	bean = formatoService12A.estructurarFormato12ABeanByFiseFormato12AC(formato);
	        	bean.setDescEmpresa(mapaEmpresa.get(formato.getId().getCodEmpresa()));
	        	bean.setDescMesPresentacion(fiseUtil.getMapaMeses().get(formato.getId().getMesPresentacion()));
	        	mapa = formatoService12A.mapearParametrosFormato12A(bean);
	        	CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12A);
	        	String descripcionFormato = "";
	        	if( tabla!=null ){
	        		descripcionFormato = tabla.getDescripcionTabla();
	        	}         	
	        	if(mapa!=null){	        	
	        		mapa.put("IMG", rutaImg);
	        		mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
	        		//verificar si ponerlo aca o no
	        		mapa.put("USUARIO", usuario);
	        		mapa.put("NOMBRE_FORMATO", descripcionFormato);
	        		mapa.put("FECHA_ENVIO", formato.getFechaEnvioDefinitivo());
	        		mapa.put("CORREO", email);
	        		mapa.put("NRO_OBSERVACIONES", (listaObs12A!=null && !listaObs12A.isEmpty())?listaObs12A.size():0);
	        		mapa.put("MSG_OBSERVACIONES", (listaObs12A!=null && !listaObs12A.isEmpty())?FiseConstants.MSG_OBSERVACION_REPORTE_LLENO:FiseConstants.MSG_OBSERVACION_REPORTE_VACIO);	        		
	     		    mapa.put("FECHA_REGISTRO", formato.getFechaCreacion());
	     		    mapa.put("USUARIO_REGISTRO", formato.getUsuarioCreacion());
	     		    mapa.put("ETAPA", formato.getId().getEtapa());
	 			}//fin if map !=null    	
		    }//fin del if formato !=null			
		} catch (Exception e) {
		   logger.error("Error al llenar parametros del formato 12A "+e);
		   e.printStackTrace();
		}finally{
		 if(pk!=null){
			 pk=null;
		 }
		 if(formato!=null){
			 formato=null; 
		 }
		 if(bean!=null){
			 bean=null; 
		 }
		}
		return mapa;
	}
	
	private Map<String, Object> parametros12B(String codEmpresa,String anioPres,String mesPres,
			String anioEjec,String mesEjec,String etapa,
			String rutaImg,String usuario,String terminal,String email){
		
		Map<String, Object> mapa = null;		
		FiseFormato12BC formato =null;
		Formato12BCBean bean =null;
		FiseFormato12BCPK pk =null;
		try {
			pk = new FiseFormato12BCPK();
		    pk.setCodEmpresa(codEmpresa);
	        pk.setAnoPresentacion(new Integer(anioPres));
	        pk.setMesPresentacion(new Integer(mesPres));
	        pk.setAnoEjecucionGasto(new Integer(anioEjec));
	        pk.setMesEjecucionGasto(new Integer(mesEjec));
	        pk.setEtapa(etapa);

	        formato = formatoService12B.getFormatoCabeceraById(pk);
		    if( formato!=null ){  	    	
		    	bean = formatoService12B.estructurarFormato12BBeanByFiseFormato12BC(formato);
		    	bean.setDescEmpresa(formato.getAdmEmpresa().getDscCortaEmpresa());
	        	bean.setDescMesPresentacion(FiseUtil.descripcionMes(formato.getId().getMesPresentacion()));
	        	bean.setDescMesEjecucion(FiseUtil.descripcionMes(formato.getId().getMesEjecucionGasto()));
	        	mapa = formatoService12B.mapearParametrosFormato12B(bean);
	        	
	        	CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12B);
	        	String descripcionFormato = "";
	        	if( tabla!=null ){
	        		descripcionFormato = tabla.getDescripcionTabla();
	        	}
	        	if(mapa!=null){	        	
	        		mapa.put("IMG", rutaImg);
	        		mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
	        		//verificar si ponerlo aca o no
	        		mapa.put("USUARIO", usuario);
	        		mapa.put("NOMBRE_FORMATO", descripcionFormato);
	        		mapa.put("FECHA_ENVIO", formato.getFechaEnvioDefinitivo());
	        		mapa.put("CORREO", email);
	        		mapa.put("NRO_OBSERVACIONES", (listaObs12B!=null && !listaObs12B.isEmpty())?listaObs12B.size():0);
	        		mapa.put("MSG_OBSERVACIONES", (listaObs12B!=null && !listaObs12B.isEmpty())?FiseConstants.MSG_OBSERVACION_REPORTE_LLENO:FiseConstants.MSG_OBSERVACION_REPORTE_VACIO);	        		
	     		    mapa.put("FECHA_REGISTRO", formato.getFechaCreacion());
	     		    mapa.put("USUARIO_REGISTRO", formato.getUsuarioCreacion());
	     		    mapa.put("ETAPA", formato.getId().getEtapa());
	 			}//fin if map !=null    	
		    }//fin del if formato !=null			
		} catch (Exception e) {
		   logger.error("Error al llenar parametros del formato 12B "+e);
		   e.printStackTrace();
		}finally{
		 if(pk!=null){
			 pk=null;
		 }
		 if(formato!=null){
			 formato=null; 
		 }
		 if(bean!=null){
			 bean=null; 
		 }
		}
		return mapa;
	}
	
	private Map<String, Object> parametros12C(String codEmpresa,String anioPres,String mesPres,
			String anioEjec,String mesEjec,String etapa,
			String rutaImg,String usuario,String terminal,String email){
		
		Map<String, Object> mapa = null;		
		FiseFormato12CC formato =null;
		Formato12CCBean bean =null;
		FiseFormato12CCPK pk =null;
		try {
			pk = new FiseFormato12CCPK();
		    pk.setCodEmpresa(codEmpresa);
	        pk.setAnoPresentacion(new Long(anioPres));
	        pk.setMesPresentacion(new Long(mesPres));	        
	        pk.setEtapa(etapa);	        
			formato = formatoService12C.obtenerFormato12CCByPK(pk);	   
		    if( formato!=null ){  	    	
		    	bean = formatoService12C.estructurarFormato12CBeanByFiseFormato12CC(formato);
				bean.setDescEmpresa(fiseUtil.getMapaEmpresa().get(formato.getId().getCodEmpresa()));
				bean.setDescMesPresentacion(fiseUtil.getMapaMeses().get(formato.getId().getMesPresentacion()));
				mapa = formatoService12C.mapearParametrosFormato12C(bean);

				CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12C);
				String descripcionFormato = "";
				if (tabla != null) {
					descripcionFormato = tabla.getDescripcionTabla();
				}
	        	if(mapa!=null){	        	
	        		mapa.put("IMG", rutaImg);
	        		mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
	        		//verificar si ponerlo aca o no
	        		mapa.put("USUARIO", usuario);
	        		mapa.put("NOMBRE_FORMATO", descripcionFormato);
	        		mapa.put("FECHA_ENVIO", formato.getFechaEnvioDefinitivo());
	        		mapa.put("CORREO", email);
	        		mapa.put("NRO_OBSERVACIONES", (listaObs12C!=null && !listaObs12C.isEmpty())?listaObs12C.size():0);
	        		mapa.put("MSG_OBSERVACIONES", (listaObs12C!=null && !listaObs12C.isEmpty())?FiseConstants.MSG_OBSERVACION_REPORTE_LLENO:FiseConstants.MSG_OBSERVACION_REPORTE_VACIO);	        		
	     		    mapa.put("FECHA_REGISTRO", formato.getFechaCreacion());
	     		    mapa.put("USUARIO_REGISTRO", formato.getUsuarioCreacion());
	     		    mapa.put("ETAPA", formato.getId().getEtapa());
	 			}//fin if map !=null    	
		    }//fin del if formato !=null			
		} catch (Exception e) {
		   logger.error("Error al llenar parametros del formato 12C "+e);
		   e.printStackTrace();
		}finally{
		 if(pk!=null){
			 pk=null;
		 }
		 if(formato!=null){
			 formato=null; 
		 }
		 if(bean!=null){
			 bean=null; 
		 }
		}
		return mapa;
	}
	
	private Map<String, Object> parametros12D(String codEmpresa,String anioPres,String mesPres,
			String anioEjec,String mesEjec,String etapa,
			String rutaImg,String usuario,String terminal,String email){
		
		Map<String, Object> mapa = null;		
		FiseFormato12DC formato =null;
		Formato12DCBean bean =null;
		FiseFormato12DCPK pk =null;
		try {
			pk = new FiseFormato12DCPK();
		    pk.setCodEmpresa(codEmpresa);
	        pk.setAnoPresentacion(new Long(anioPres));
	        pk.setMesPresentacion(new Long(mesPres));	        
	        pk.setEtapa(etapa);		       
			formato = formatoService12D.obtenerFormato12DCByPK(pk);
		    if( formato!=null ){  	    
				bean = formatoService12D.estructurarFormato12DBeanByFiseFormato12DC(formato);
				bean.setDescEmpresa(fiseUtil.getMapaEmpresa().get(formato.getId().getCodEmpresa()));
				bean.setDescMesPresentacion(fiseUtil.getMapaMeses().get(formato.getId().getMesPresentacion()));
				mapa = formatoService12D.mapearParametrosFormato12D(bean);
				CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12D);
				String descripcionFormato = "";
				if (tabla != null) {
					descripcionFormato = tabla.getDescripcionTabla();
				}			
	        	if(mapa!=null){	        	
	        		mapa.put("IMG", rutaImg);
	        		mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
	        		//verificar si ponerlo aca o no
	        		mapa.put("USUARIO", usuario);
	        		mapa.put("NOMBRE_FORMATO", descripcionFormato);
	        		mapa.put("FECHA_ENVIO", formato.getFechaEnvioDefinitivo());
	        		mapa.put("CORREO", email);
	        		mapa.put("NRO_OBSERVACIONES", (listaObs12D!=null && !listaObs12D.isEmpty())?listaObs12D.size():0);
	        		mapa.put("MSG_OBSERVACIONES", (listaObs12D!=null && !listaObs12D.isEmpty())?FiseConstants.MSG_OBSERVACION_REPORTE_LLENO:FiseConstants.MSG_OBSERVACION_REPORTE_VACIO);	        		
	     		    mapa.put("FECHA_REGISTRO", formato.getFechaCreacion());
	     		    mapa.put("USUARIO_REGISTRO", formato.getUsuarioCreacion());
	     		    mapa.put("ETAPA", formato.getId().getEtapa());
	 			}//fin if map !=null    	
		    }//fin del if formato !=null			
		} catch (Exception e) {
		   logger.error("Error al llenar parametros del formato 12D "+e);
		   e.printStackTrace();
		}finally{
		 if(pk!=null){
			 pk=null;
		 }
		 if(formato!=null){
			 formato=null; 
		 }
		 if(bean!=null){
			 bean=null; 
		 }
		}
		return mapa;
	}
	
	
	/*****LLENAR MAP DE PARAMETROS FORMATOS BIENALES*******/	
	private Map<String, Object> parametros13A(String codEmpresa,String anioPres,String mesPres,
			String etapa, String rutaImg,String usuario,
			String terminal,String email){
		
		Map<String, Object> mapa = null;
		FiseFormato13ACPK pk =null;
		FiseFormato13AC formato =null;
		Formato13ACBean bean =null;
		try {
			pk = new FiseFormato13ACPK();
			pk.setCodEmpresa(codEmpresa);
			pk.setAnoPresentacion(new Long(anioPres));
			pk.setMesPresentacion(new Long(mesPres));
			pk.setEtapa(etapa);
			formato = formatoService13A.obtenerFormato13ACByPK(pk);
		    if( formato!=null ){  
		    	/****Cargamos la lista de zonas *****/
		    	listaZonas13A = formatoService13A.listarLocalidadesPorZonasBenefFormato13ADByFormato13AC(formato);		    	
		    	bean = formatoService13A.estructurarFormato13ABeanByFiseFormato13AC(formato);
	        	bean.setDescEmpresa(mapaEmpresa.get(formato.getId().getCodEmpresa()));
	        	bean.setDescMesPresentacion(fiseUtil.getMapaMeses().get(formato.getId().getMesPresentacion()));
	        	mapa = formatoService13A.mapearParametrosFormato13A(bean);
	        	CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO13A);
	        	String descripcionFormato = "";
	        	if( tabla!=null ){
	        		descripcionFormato = tabla.getDescripcionTabla();
	        	}         	
	        	if(mapa!=null){	        	
	        		mapa.put("IMG", rutaImg);
	        		mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
	        		//verificar si ponerlo aca o no
	        		mapa.put("USUARIO", usuario);
	        		mapa.put("NOMBRE_FORMATO", descripcionFormato);
	        		mapa.put("FECHA_ENVIO", formato.getFechaEnvioDefinitivo());
	        		mapa.put("CORREO", email);
	        		mapa.put("NRO_OBSERVACIONES", (listaObs13A!=null && !listaObs13A.isEmpty())?listaObs13A.size():0);
	        		mapa.put("MSG_OBSERVACIONES", (listaObs13A!=null && !listaObs13A.isEmpty())?FiseConstants.MSG_OBSERVACION_REPORTE_LLENO:FiseConstants.MSG_OBSERVACION_REPORTE_VACIO);
	        		 //add para acta envio
	        		mapa.put("ANO_INICIO_VIGENCIA", formato.getAnoInicioVigenciaDetalle());
					mapa.put("ANO_FIN_VIGENCIA", formato.getAnoFinVigenciaDetalle());
	     		    mapa.put("FECHA_REGISTRO", formato.getFechaCreacion());
	     		    mapa.put("USUARIO_REGISTRO", formato.getUsuarioCreacion());	     		 
	     		    mapa.put("ETAPA", formato.getId().getEtapa());
	 			}//fin if map !=null    	
		    }//fin del if formato !=null			
		} catch (Exception e) {
		   logger.error("Error al llenar parametros del formato 13A "+e);
		   e.printStackTrace();
		}finally{
		 if(pk!=null){
			 pk=null;
		 }
		 if(formato!=null){
			 formato=null; 
		 }
		 if(bean!=null){
			 bean=null; 
		 }
		}
		return mapa;
	}
	
	private Map<String, Object> parametros14A(String codEmpresa,String anioPres,String mesPres,
			String anioIniVig,String anioFinVig,String etapa,
			String rutaImg,String usuario,String terminal,String email){
		
		Map<String, Object> mapa = null;
		FiseFormato14ACPK pk =null;
		FiseFormato14AC formato =null;
		Formato14ACBean bean =null;
		try {
			pk = new FiseFormato14ACPK();
		    pk.setCodEmpresa(codEmpresa);
	        pk.setAnoPresentacion(new Long(anioPres));
	        pk.setMesPresentacion(new Long(mesPres));
	        pk.setAnoInicioVigencia(new Long(anioIniVig));
	        pk.setAnoFinVigencia(new Long(anioFinVig));
	        pk.setEtapa(etapa);
	        formato = formatoService14A.obtenerFormato14ACByPK(pk);
		    if( formato!=null ){  	    	
		    	bean = formatoService14A.estructurarFormato14ABeanByFiseFormato14AC(formato);
	        	bean.setDescEmpresa(mapaEmpresa.get(formato.getId().getCodEmpresa()));
	        	bean.setDescMesPresentacion(fiseUtil.getMapaMeses().get(formato.getId().getMesPresentacion()));
	        	mapa = formatoService14A.mapearParametrosFormato14A(bean);
	        	CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14A);
	        	String descripcionFormato = "";
	        	if( tabla!=null ){
	        		descripcionFormato = tabla.getDescripcionTabla();
	        	}         	
	        	if(mapa!=null){	        	
	        		mapa.put("IMG", rutaImg);
	        		mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
	        		//verificar si ponerlo aca o no
	        		mapa.put("USUARIO", usuario);
	        		mapa.put("NOMBRE_FORMATO", descripcionFormato);
	        		mapa.put("FECHA_ENVIO", formato.getFechaEnvioDefinitivo());
	        		mapa.put("CORREO", email);
	        		mapa.put("NRO_OBSERVACIONES", (listaObs14A!=null && !listaObs14A.isEmpty())?listaObs14A.size():0);
	        		mapa.put("MSG_OBSERVACIONES", (listaObs14A!=null && !listaObs14A.isEmpty())?FiseConstants.MSG_OBSERVACION_REPORTE_LLENO:FiseConstants.MSG_OBSERVACION_REPORTE_VACIO);
	        		 //add para acta envio
	     		    mapa.put("ANO_INICIO_VIGENCIA", formato.getId().getAnoInicioVigencia());
	     		    mapa.put("ANO_FIN_VIGENCIA", formato.getId().getAnoFinVigencia());
	     		    mapa.put("FECHA_REGISTRO", formato.getFechaCreacion());
	     		    mapa.put("USUARIO_REGISTRO", formato.getUsuarioCreacion());	     		   
	     		    mapa.put("ETAPA", formato.getId().getEtapa());
	 			}//fin if map !=null    	
		    }//fin del if formato !=null			
		} catch (Exception e) {
		   logger.error("Error al llenar parametros del formato 14A "+e);
		   e.printStackTrace();
		}finally{
		 if(pk!=null){
			 pk=null;
		 }
		 if(formato!=null){
			 formato=null; 
		 }
		 if(bean!=null){
			 bean=null; 
		 }
		}
		return mapa;
	}
	
	private Map<String, Object> parametros14B(String codEmpresa,String anioPres,String mesPres,
			String anioIniVig,String anioFinVig,String etapa,
			String rutaImg,String usuario,String terminal,String email){
		
		Map<String, Object> mapa = null;
		FiseFormato14BCPK pk =null;
		FiseFormato14BC formato =null;
		Formato14BCBean bean =null;
		try {
			pk = new FiseFormato14BCPK();
		    pk.setCodEmpresa(codEmpresa);
	        pk.setAnoPresentacion(new Long(anioPres));
	        pk.setMesPresentacion(new Long(mesPres));
	        pk.setAnoInicioVigencia(new Long(anioIniVig));
	        pk.setAnoFinVigencia(new Long(anioFinVig));
	        pk.setEtapa(etapa);
	        formato = formatoService14B.obtenerFormato14BCByPK(pk);
		    if( formato!=null ){  	    	
		    	bean = formatoService14B.estructurarFormato14BBeanByFiseFormato14BC(formato);
	        	bean.setDescEmpresa(mapaEmpresa.get(formato.getId().getCodEmpresa()));
	        	bean.setDescMesPresentacion(fiseUtil.getMapaMeses().get(formato.getId().getMesPresentacion()));
	        	mapa = formatoService14B.mapearParametrosFormato14B(bean);	        	
	        	CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14B);
	        	String descripcionFormato = "";
	        	if( tabla!=null ){
	        		descripcionFormato = tabla.getDescripcionTabla();
	        	}         	
	        	if(mapa!=null){	        	
	        		mapa.put("IMG", rutaImg);
	        		mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
	        		//verificar si ponerlo aca o no
	        		mapa.put("USUARIO", usuario);
	        		mapa.put("NOMBRE_FORMATO", descripcionFormato);
	        		mapa.put("FECHA_ENVIO", formato.getFechaEnvioDefinitivo());
	        		mapa.put("CORREO", email);
	        		mapa.put("NRO_OBSERVACIONES", (listaObs14B!=null && !listaObs14B.isEmpty())?listaObs14B.size():0);
	        		mapa.put("MSG_OBSERVACIONES", (listaObs14B!=null && !listaObs14B.isEmpty())?FiseConstants.MSG_OBSERVACION_REPORTE_LLENO:FiseConstants.MSG_OBSERVACION_REPORTE_VACIO);
	        		 //add para acta envio
	     		    mapa.put("ANO_INICIO_VIGENCIA", formato.getId().getAnoInicioVigencia());
	     		    mapa.put("ANO_FIN_VIGENCIA", formato.getId().getAnoFinVigencia());
	     		    mapa.put("FECHA_REGISTRO", formato.getFechaCreacion());
	     		    mapa.put("USUARIO_REGISTRO", formato.getUsuarioCreacion());	     		  
	     		    mapa.put("ETAPA", formato.getId().getEtapa());
	 			}//fin if map !=null    	
		    }//fin del if formato !=null			
		} catch (Exception e) {
		   logger.error("Error al llenar parametros del formato 14B "+e);
		   e.printStackTrace();
		}finally{
		 if(pk!=null){
			 pk=null;
		 }
		 if(formato!=null){
			 formato=null; 
		 }
		 if(bean!=null){
			 bean=null; 
		 }
		}
		return mapa;
	}
	
	private Map<String, Object> parametros14C(String codEmpresa,String anioPres,String mesPres,
			String anioIniVig,String anioFinVig,String etapa,
			String rutaImg,String usuario,String terminal,String email){
		
		Map<String, Object> mapa = null;
		Formato14CBean f =null;
		FiseFormato14CC formato =null;
		Formato14CReportBean bean =null;
		try {
		    f= new Formato14CBean();
			f.setCodEmpresa(codEmpresa); 
			f.setAnioPres(anioPres);
			f.setMesPres(mesPres);
			f.setEtapa(etapa);
			f.setAnoIniVigencia(anioIniVig);
			f.setAnoFinVigencia(anioFinVig);
			logger.info(" anio ini vig1  "+f.getAnoIniVigencia()+ "   "+anioIniVig);
		    logger.info(" anio fin vig1  "+f.getAnoFinVigencia()+ "   "+anioFinVig);
		    formato = formatoService14C.obtenerFiseFormato14CC(f);
		    if( formato!=null ){  	    	
		    	bean = formatoService14C.estructurarFormato14CBeanByFiseFormato14C(formato);
	        	bean.setDescEmpresa(mapaEmpresa.get(formato.getId().getCodEmpresa()));
	        	bean.setDescMesPresentacion(fiseUtil.getMapaMeses().get(formato.getId().getMesPresentacion()));
	        	mapa = formatoService14C.mapearParametrosFormato14C(bean);
	        	CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO14C);
	        	String descripcionFormato = "";
	        	if( tabla!=null ){
	        		descripcionFormato = tabla.getDescripcionTabla();
	        	}         	
	        	if(mapa!=null){	        	
	        		mapa.put("IMG", rutaImg);
	        		mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
	        		//verificar si ponerlo aca o no
	        		mapa.put("USUARIO", usuario);
	        		mapa.put("NOMBRE_FORMATO", descripcionFormato);
	        		mapa.put("FECHA_ENVIO", formato.getFechaEnvioDefinitivo());
	        		mapa.put("CORREO", email);
	        		mapa.put("NRO_OBSERVACIONES", (listaObs14C!=null && !listaObs14C.isEmpty())?listaObs14C.size():0);
	        		mapa.put("MSG_OBSERVACIONES", (listaObs14C!=null && !listaObs14C.isEmpty())?FiseConstants.MSG_OBSERVACION_REPORTE_LLENO:FiseConstants.MSG_OBSERVACION_REPORTE_VACIO);
	        		 //add para acta envio
	     		    mapa.put("ANO_INICIO_VIGENCIA", formato.getId().getAnoInicioVigencia());
	     		    mapa.put("ANO_FIN_VIGENCIA", formato.getId().getAnoFinVigencia());
	     		    mapa.put("FECHA_REGISTRO", formato.getFechaCreacion());
	     		    mapa.put("USUARIO_REGISTRO", formato.getUsuarioCreacion());	     		   
	     		    mapa.put("ETAPA", formato.getId().getEtapa());
	 			}//fin if map !=null    	
		    }//fin del if formato !=null			
		} catch (Exception e) {
		   logger.error("Error al llenar parametros del formato 14C "+e);
		   e.printStackTrace();
		}finally{
		 if(f!=null){
			 f=null;
		 }
		 if(formato!=null){
			 formato=null; 
		 }
		 if(bean!=null){
			 bean=null; 
		 }
		}
		return mapa;
	}
	
	private Map<String, Object> parametrosActaMensual(List<EnvioDefinitivoBean> lista,
			String rutaImg,String rutaCheck,String rutaUncheck,String usuario,
			String grupoInf,String etapa,String rutaImgRelleno){		
		Map<String, Object> mapa = new  HashMap<String, Object>();	
		try {			
			mapa.put("IMG", rutaImg);
			mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
			mapa.put("USUARIO", usuario);
			mapa.put("FECHA_ENVIO", FechaUtil.obtenerFechaActual());
			mapa.put("ETAPA", etapa); 
			mapa.put("GRUPO_INFORMACION", grupoInf);  
			String dirCheckedImage = rutaCheck;
			String dirUncheckedImage = rutaUncheck;	
			String formatos = "";
			//FORMATO 12A
			mapa.put("NAMEF12A", ""); 	     		  
			mapa.put("UNCHECKED12A", rutaImgRelleno);     		  
			mapa.put("FFIRMADO12A", "");  			  
			mapa.put("NAMEPLAZO12A", "");  			  
			mapa.put("NAMEOBS12A", "");  			  
			mapa.put("CHECKED12A",rutaImgRelleno);  			  
			mapa.put("NAMEOTROS12A", "");			
			mapa.put("CHECKED_CUMPLE_PLAZO_12A", rutaImgRelleno);
			mapa.put("CHECKED_OBSERVACION_12A", rutaImgRelleno);
			//FORMATO 12B
			mapa.put("NAMEF12B", ""); 	     		  
			mapa.put("UNCHECKED12B", rutaImgRelleno);     		  
			mapa.put("FFIRMADO12B", "");  			  
			mapa.put("NAMEPLAZO12B", "");  			  
			mapa.put("NAMEOBS12B", "");  			  
			mapa.put("CHECKED12B",rutaImgRelleno);  			  
			mapa.put("NAMEOTROS12B", "");			
			mapa.put("CHECKED_CUMPLE_PLAZO_12B", rutaImgRelleno);
			mapa.put("CHECKED_OBSERVACION_12B", rutaImgRelleno);
			//FORMATO 12C
			mapa.put("NAMEF12C", ""); 	     		  
			mapa.put("UNCHECKED12C", rutaImgRelleno);     		  
			mapa.put("FFIRMADO12C", "");  			  
			mapa.put("NAMEPLAZO12C", "");  			  
			mapa.put("NAMEOBS12C", "");  			  
			mapa.put("CHECKED12C",rutaImgRelleno);  			  
			mapa.put("NAMEOTROS12C", "");			
			mapa.put("CHECKED_CUMPLE_PLAZO_12C", rutaImgRelleno);
			mapa.put("CHECKED_OBSERVACION_12C", rutaImgRelleno);
			//FORMATO 12D
			mapa.put("NAMEF12D", ""); 	     		  
			mapa.put("UNCHECKED12D", rutaImgRelleno);     		  
			mapa.put("FFIRMADO12D", "");  			  
			mapa.put("NAMEPLAZO12D", "");  			  
			mapa.put("NAMEOBS12D", "");  			  
			mapa.put("CHECKED12D",rutaImgRelleno);  			  
			mapa.put("NAMEOTROS12D", "");			
			mapa.put("CHECKED_CUMPLE_PLAZO_12D", rutaImgRelleno);
			mapa.put("CHECKED_OBSERVACION_12D", rutaImgRelleno);
			
           for(EnvioDefinitivoBean not:lista){  
        	   mapa.put("DESC_EMPRESA", mapaEmpresa.get(not.getCodEmpresa()));
  				if(FiseConstants.NOMBRE_FORMATO_12A.equals(not.getFormato())){ 
  					FiseFormato12ACPK pk = new FiseFormato12ACPK();
  	  				pk.setCodEmpresa(not.getCodEmpresa());
  	  				pk.setAnoPresentacion(new Long(not.getAnioPres()));
  	  		        pk.setMesPresentacion(new Long(not.getMesPres()));
  	  		        pk.setAnoEjecucionGasto(new Long(not.getAnioEjec()));
  	  		        pk.setMesEjecucionGasto(new Long(not.getMesEjec()));
  	  		        pk.setEtapa(not.getEtapa());  	  			        
  	  		        FiseFormato12AC formato12A = formatoService12A.obtenerFormato12ACByPK(pk);
  	  		        boolean cumplePlazo = false;
  	  		        cumplePlazo = commonService.fechaEnvioCumplePlazo(
  	  		        		FiseConstants.TIPO_FORMATO_12A, 
  	  		        		formato12A.getId().getCodEmpresa(), 
  	  		        		formato12A.getId().getAnoPresentacion(), 
  	  		        		formato12A.getId().getMesPresentacion(), 
  	  		        		formato12A.getId().getEtapa(), 
  	  		        		FechaUtil.fecha_DD_MM_YYYY(formato12A.getFechaEnvioDefinitivo()));
  	  		        if(cumplePlazo ){
  	  		        	mapa.put("CHECKED_CUMPLE_PLAZO_12A", dirCheckedImage);//dirUncheckedImage
  	  		        }else{
  	  		        	mapa.put("CHECKED_CUMPLE_PLAZO_12A", dirUncheckedImage);//dirCheckedImage
  	  		        }
  	  		        if(listaObs12A!=null && !listaObs12A.isEmpty() ){
  	  		        	mapa.put("CHECKED_OBSERVACION_12A", dirUncheckedImage);
  	  		        }else{
  	  		        	mapa.put("CHECKED_OBSERVACION_12A", dirCheckedImage);
  	  		        }     		 
  	  		        formatos = formatos+" FISE 12A ";
  	  		        mapa.put("NAMEF12A", "FISE 12A"); 	     		  
  	  		        mapa.put("UNCHECKED12A", dirUncheckedImage);     		  
  	  		        mapa.put("FFIRMADO12A", "Formato Firmado(3)");  			  
  	  		        mapa.put("NAMEPLAZO12A", "Cumple Plazos");  			  
  	  		        mapa.put("NAMEOBS12A", "Con Observaciones");  			  
  	  		        mapa.put("CHECKED12A",dirCheckedImage);  			  
  	  		        mapa.put("NAMEOTROS12A", "Otros: ___________"); 
  	  		        
  				}else if(FiseConstants.NOMBRE_FORMATO_12B.equals(not.getFormato())){ 
  					 FiseFormato12BCPK pk=new FiseFormato12BCPK();
  					pk.setCodEmpresa(not.getCodEmpresa());
  	  				pk.setAnoPresentacion(new Integer(not.getAnioPres()));
  	  		        pk.setMesPresentacion(new Integer(not.getMesPres()));
  	  		        pk.setAnoEjecucionGasto(new Integer(not.getAnioEjec()));
  	  		        pk.setMesEjecucionGasto(new Integer(not.getMesEjec()));
  	  		        pk.setEtapa(not.getEtapa()); 
  	  		        FiseFormato12BC formato12B = formatoService12B.getFormatoCabeceraById(pk);
  	  		        boolean cumplePlazo = false;
  	  		        cumplePlazo = commonService.fechaEnvioCumplePlazo(
  	  		        		FiseConstants.TIPO_FORMATO_12B, 
  	  		        	    formato12B.getId().getCodEmpresa(), 
  	  		                formato12B.getId().getAnoPresentacion().longValue(), 
  	  		                formato12B.getId().getMesPresentacion().longValue(), 
  	  		                formato12B.getId().getEtapa(), 
  	  		        		FechaUtil.fecha_DD_MM_YYYY(formato12B.getFechaEnvioDefinitivo()));
  	  		        if(cumplePlazo ){
  	  		        	mapa.put("CHECKED_CUMPLE_PLAZO_12B", dirCheckedImage);
  	  		        }else{
  	  		        	mapa.put("CHECKED_CUMPLE_PLAZO_12B", dirUncheckedImage);
  	  		        }
  	  		        if(listaObs12B!=null && !listaObs12B.isEmpty() ){
  	  		        	mapa.put("CHECKED_OBSERVACION_12B", dirUncheckedImage);
  	  		        }else{
  	  		        	mapa.put("CHECKED_OBSERVACION_12B", dirCheckedImage);
  	  		        }     		 
  	  		        formatos = formatos+" FISE 12B ";
  	  		        mapa.put("NAMEF12B", "FISE 12B"); 	     		  
  	  		        mapa.put("UNCHECKED12B", dirUncheckedImage);     		  
  	  		        mapa.put("FFIRMADO12B", "Formato Firmado(3)");  			  
  	  		        mapa.put("NAMEPLAZO12B", "Cumple Plazos");  			  
  	  		        mapa.put("NAMEOBS12B", "Con Observaciones");  			  
  	  		        mapa.put("CHECKED12B",dirCheckedImage);  			  
  	  		        mapa.put("NAMEOTROS12B", "Otros: ___________");
  					
  				}else if(FiseConstants.NOMBRE_FORMATO_12C.equals(not.getFormato())){ 
  					FiseFormato12CCPK pk = new FiseFormato12CCPK();
  					pk.setCodEmpresa(not.getCodEmpresa());
  	  				pk.setAnoPresentacion(new Long(not.getAnioPres()));
  	  		        pk.setMesPresentacion(new Long(not.getMesPres()));  	  		       
  	  		        pk.setEtapa(not.getEtapa());
  	  		        FiseFormato12CC formato12C = formatoService12C.obtenerFormato12CCByPK(pk);
  	  		        boolean cumplePlazo = false; 	  		       
  	  		        cumplePlazo = commonService.fechaEnvioCumplePlazo(
	  		        		FiseConstants.TIPO_FORMATO_12C, 
	  		        		formato12C.getId().getCodEmpresa(), 
	  		        		formato12C.getId().getAnoPresentacion(), 
	  		        		formato12C.getId().getMesPresentacion(), 
	  		        		formato12C.getId().getEtapa(), 
	  		        		FechaUtil.fecha_DD_MM_YYYY(formato12C.getFechaEnvioDefinitivo()));
  	  		        if(cumplePlazo ){
  	  		        	mapa.put("CHECKED_CUMPLE_PLAZO_12C", dirCheckedImage);
  	  		        }else{
  	  		        	mapa.put("CHECKED_CUMPLE_PLAZO_12C", dirUncheckedImage);
  	  		        }
  	  		        if(listaObs12C!=null && !listaObs12C.isEmpty() ){
  	  		        	mapa.put("CHECKED_OBSERVACION_12C", dirUncheckedImage);
  	  		        }else{
  	  		        	mapa.put("CHECKED_OBSERVACION_12C", dirCheckedImage);
  	  		        }     		 
  	  		        formatos = formatos+" FISE 12C ";
  	  		        mapa.put("NAMEF12C", "FISE 12C"); 	     		  
  	  		        mapa.put("UNCHECKED12C", dirUncheckedImage);     		  
  	  		        mapa.put("FFIRMADO12C", "Formato Firmado(3)");  			  
  	  		        mapa.put("NAMEPLAZO12C", "Cumple Plazos");  			  
  	  		        mapa.put("NAMEOBS12C", "Con Observaciones");  			  
  	  		        mapa.put("CHECKED12C",dirCheckedImage);  			  
  	  		        mapa.put("NAMEOTROS12C", "Otros: ___________");  	
  	  		        
  				}else if(FiseConstants.NOMBRE_FORMATO_12D.equals(not.getFormato())){ 
  					FiseFormato12DCPK pk = new FiseFormato12DCPK();
  					pk.setCodEmpresa(not.getCodEmpresa());
  	  				pk.setAnoPresentacion(new Long(not.getAnioPres()));
  	  		        pk.setMesPresentacion(new Long(not.getMesPres()));  	  		       
  	  		        pk.setEtapa(not.getEtapa());
  	  		        FiseFormato12DC formato12D = formatoService12D.obtenerFormato12DCByPK(pk);
  	  		        boolean cumplePlazo = false; 	  		       
  	  		        cumplePlazo = commonService.fechaEnvioCumplePlazo(
	  		        		FiseConstants.TIPO_FORMATO_12D, 
	  		        		formato12D.getId().getCodEmpresa(), 
	  		        		formato12D.getId().getAnoPresentacion(), 
	  		        		formato12D.getId().getMesPresentacion(), 
	  		        		formato12D.getId().getEtapa(), 
	  		        		FechaUtil.fecha_DD_MM_YYYY(formato12D.getFechaEnvioDefinitivo()));
  	  		        if(cumplePlazo ){
  	  		        	mapa.put("CHECKED_CUMPLE_PLAZO_12D", dirCheckedImage);
  	  		        }else{
  	  		        	mapa.put("CHECKED_CUMPLE_PLAZO_12D", dirUncheckedImage);
  	  		        }
  	  		        if(listaObs12D!=null && !listaObs12D.isEmpty() ){
  	  		        	mapa.put("CHECKED_OBSERVACION_12D", dirUncheckedImage);
  	  		        }else{
  	  		        	mapa.put("CHECKED_OBSERVACION_12D", dirCheckedImage);
  	  		        }     		 
  	  		        formatos = formatos+" FISE 12D ";
  	  		        mapa.put("NAMEF12D", "FISE 12D"); 	     		  
  	  		        mapa.put("UNCHECKED12D", dirUncheckedImage);     		  
  	  		        mapa.put("FFIRMADO12D", "Formato Firmado(3)");  			  
  	  		        mapa.put("NAMEPLAZO12D", "Cumple Plazos");  			  
  	  		        mapa.put("NAMEOBS12D", "Con Observaciones");  			  
  	  		        mapa.put("CHECKED12D",dirCheckedImage);  			  
  	  		        mapa.put("NAMEOTROS12D", "Otros: ___________");   					
  				}	    
  			}//fin del for de la lista	
           mapa.put("FORMATOS", formatos); 			  		
		} catch (Exception e) {
		   logger.error("Error al llenar parametros del formato mensual "+e);
		   e.printStackTrace();
		}		
		return mapa;
	}	
	
	private Map<String, Object> parametrosActaBienal(List<EnvioDefinitivoBean> lista,
			String rutaImg,String rutaCheck,String rutaUncheck,String usuario,
			String grupoInf,String etapa,String rutaImgRelleno){		
		Map<String, Object> mapa = new  HashMap<String, Object>();	
		try {			
			mapa.put("IMG", rutaImg);
			mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
			mapa.put("USUARIO", usuario);
			mapa.put("FECHA_ENVIO", FechaUtil.obtenerFechaActual());
			mapa.put("ETAPA", etapa); 
			mapa.put("GRUPO_INFORMACION", grupoInf);
			//mapa.put("NRO_OBSERVACIONES", (listaObservaciones!=null && !listaObservaciones.isEmpty())?listaObservaciones.size():0);
    		//mapa.put("MSG_OBSERVACIONES", (listaObservaciones!=null && !listaObservaciones.isEmpty())?FiseConstants.MSG_OBSERVACION_REPORTE_LLENO:FiseConstants.MSG_OBSERVACION_REPORTE_VACIO);
			mapa.put("NRO_OBSERVACIONES", "");
    		mapa.put("MSG_OBSERVACIONES","");
			String dirCheckedImage = rutaCheck;
			String dirUncheckedImage = rutaUncheck;	
			String formatos = "";
			//FORMATO 13A
			mapa.put("NAMEF13A", ""); 	     		  
			mapa.put("UNCHECKED13A", rutaImgRelleno);     		  
			mapa.put("FFIRMADO13A", "");
			mapa.put("FSUSTENTO13A", "");
			mapa.put("NAMEPLAZO13A", "");  			  
			mapa.put("NAMEOBS13A", "");  			  
			mapa.put("CHECKED13A",rutaImgRelleno);  			  
			mapa.put("NAMEOTROS13A", "");			
			mapa.put("CHECKED_CUMPLE_PLAZO_13A", rutaImgRelleno);
			mapa.put("CHECKED_OBSERVACION_13A", rutaImgRelleno);
			//FORMATO 14A
			mapa.put("NAMEF14A", ""); 	     		  
			mapa.put("UNCHECKED14A", rutaImgRelleno);     		  
			mapa.put("FFIRMADO14A", "");
			mapa.put("FSUSTENTO14A", "");
			mapa.put("NAMEPLAZO14A", "");  			  
			mapa.put("NAMEOBS14A", "");  			  
			mapa.put("CHECKED14A",rutaImgRelleno);  			  
			mapa.put("NAMEOTROS14A", "");			
			mapa.put("CHECKED_CUMPLE_PLAZO_14A", rutaImgRelleno);
			mapa.put("CHECKED_OBSERVACION_14A", rutaImgRelleno);
			//FORMATO 14B
			mapa.put("NAMEF14B", ""); 	     		  
			mapa.put("UNCHECKED14B", rutaImgRelleno);     		  
			mapa.put("FFIRMADO14B", ""); 
			mapa.put("FSUSTENTO14B", "");
			mapa.put("NAMEPLAZO14B", "");  			  
			mapa.put("NAMEOBS14B", "");  			  
			mapa.put("CHECKED14B",rutaImgRelleno);  			  
			mapa.put("NAMEOTROS14B", "");			
			mapa.put("CHECKED_CUMPLE_PLAZO_14B", rutaImgRelleno);
			mapa.put("CHECKED_OBSERVACION_14B", rutaImgRelleno);
			//FORMATO 14C
			mapa.put("NAMEF14C", ""); 	     		  
			mapa.put("UNCHECKED14C", rutaImgRelleno);     		  
			mapa.put("FFIRMADO14C", "");  
			mapa.put("FSUSTENTO14C", "");
			mapa.put("NAMEPLAZO14C", "");  			  
			mapa.put("NAMEOBS14C", "");  			  
			mapa.put("CHECKED14C",rutaImgRelleno);  			  
			mapa.put("NAMEOTROS14C", "");			
			mapa.put("CHECKED_CUMPLE_PLAZO_14C", rutaImgRelleno);
			mapa.put("CHECKED_OBSERVACION_14C", rutaImgRelleno);
			
           for(EnvioDefinitivoBean not:lista){
        	   mapa.put("DESC_EMPRESA", mapaEmpresa.get(not.getCodEmpresa()));
  				if(FiseConstants.NOMBRE_FORMATO_13A.equals(not.getFormato())){ 
  					FiseFormato13ACPK pk = new FiseFormato13ACPK();
  					pk.setCodEmpresa(not.getCodEmpresa());
  	  				pk.setAnoPresentacion(new Long(not.getAnioPres()));
  	  		        pk.setMesPresentacion(new Long(not.getMesPres()));
  	  		        pk.setEtapa(not.getEtapa());  	 
  	  		        FiseFormato13AC formato13A = formatoService13A.obtenerFormato13ACByPK(pk);
  	  		        boolean cumplePlazo = false;
  	  		        cumplePlazo = commonService.fechaEnvioCumplePlazo(
  	  		        		FiseConstants.TIPO_FORMATO_13A, 
  	  		        		formato13A.getId().getCodEmpresa(), 
  	  		        		formato13A.getId().getAnoPresentacion(), 
  	  		        		formato13A.getId().getMesPresentacion(), 
  	  		        		formato13A.getId().getEtapa(), 
  	  		        		FechaUtil.fecha_DD_MM_YYYY(formato13A.getFechaEnvioDefinitivo()));
  	  		        if(cumplePlazo ){
  	  		           logger.info("cumple plazo true 13A: "+cumplePlazo); 
  	  		        	mapa.put("CHECKED_CUMPLE_PLAZO_13A", dirCheckedImage);
  	  		        }else{
  	  		        	mapa.put("CHECKED_CUMPLE_PLAZO_13A", dirUncheckedImage);
  	  		        }
  	  		        if(listaObs13A!=null && !listaObs13A.isEmpty() ){
  	  		        	mapa.put("CHECKED_OBSERVACION_13A", dirUncheckedImage);
  	  		        }else{
  	  		        	mapa.put("CHECKED_OBSERVACION_13A", dirCheckedImage);
  	  		        }     		 
  	  		        formatos = formatos+" FISE 13A ";
  	  		        mapa.put("NAMEF13A", "FISE 13A"); 	     		  
  	  		        mapa.put("UNCHECKED13A", dirUncheckedImage);     		  
  	  		        mapa.put("FFIRMADO13A", "Formato Firmado(1)");
  	  		        mapa.put("FSUSTENTO13A", "Presenta Sustentos(1)");  	  		        
  	  		        mapa.put("NAMEPLAZO13A", "Cumple Plazos");  			  
  	  		        mapa.put("NAMEOBS13A", "Sin Observaciones");  			  
  	  		        mapa.put("CHECKED13A",dirCheckedImage);  			  
  	  		        mapa.put("NAMEOTROS13A", "Otros: ___________");  
  	  		       
  				}else if(FiseConstants.NOMBRE_FORMATO_14A.equals(not.getFormato())){ 
  					FiseFormato14ACPK pk = new FiseFormato14ACPK();
  					pk.setCodEmpresa(not.getCodEmpresa());
  	  				pk.setAnoPresentacion(new Long(not.getAnioPres()));
  	  		        pk.setMesPresentacion(new Long(not.getMesPres()));
  			        pk.setAnoInicioVigencia(new Long(not.getAnioIniVig()));
  			        pk.setAnoFinVigencia(new Long(not.getAnioFinVig()));
  			        pk.setEtapa(not.getEtapa());  				        
  			        FiseFormato14AC formato14A = formatoService14A.obtenerFormato14ACByPK(pk);
  			        boolean cumplePlazo = false;
  			        cumplePlazo = commonService.fechaEnvioCumplePlazo(
  			        		FiseConstants.TIPO_FORMATO_14A, 
  			        		formato14A.getId().getCodEmpresa(), 
  			        		formato14A.getId().getAnoPresentacion(), 
  			        		formato14A.getId().getMesPresentacion(), 
  			        		formato14A.getId().getEtapa(), 
  			        		FechaUtil.fecha_DD_MM_YYYY(formato14A.getFechaEnvioDefinitivo()));
  			        if(cumplePlazo ){
  			        	logger.info("cumple plazo true 14A: "+cumplePlazo); 
  			        	mapa.put("CHECKED_CUMPLE_PLAZO_14A", dirCheckedImage);
  			        }else{
  			        	mapa.put("CHECKED_CUMPLE_PLAZO_14A", dirUncheckedImage);
  			        }
  			        if(listaObs14A!=null && !listaObs14A.isEmpty() ){
  			        	mapa.put("CHECKED_OBSERVACION_14A", dirUncheckedImage);
  			        }else{
  			        	mapa.put("CHECKED_OBSERVACION_14A", dirCheckedImage);
  			        }     		 
  			        formatos = formatos+" FISE 14A ";
  			        mapa.put("NAMEF14A", "FISE 14A"); 	     		  
  			        mapa.put("UNCHECKED14A", dirUncheckedImage);     		  
  			        mapa.put("FFIRMADO14A", "Formato Firmado(1)");
  			        mapa.put("FSUSTENTO14A", "Presenta Sustentos(1)");  	  		        
  			        mapa.put("NAMEPLAZO14A", "Cumple Plazos");  			  
  			        mapa.put("NAMEOBS14A", "Sin Observaciones");  			  
  			        mapa.put("CHECKED14A",dirCheckedImage);  			  
  			        mapa.put("NAMEOTROS14A", "Otros: ___________"); 
  			       
  				}else if(FiseConstants.NOMBRE_FORMATO_14B.equals(not.getFormato())){ 
  					FiseFormato14BCPK pk = new FiseFormato14BCPK();
  					pk.setCodEmpresa(not.getCodEmpresa());
  	  				pk.setAnoPresentacion(new Long(not.getAnioPres()));
  	  		        pk.setMesPresentacion(new Long(not.getMesPres()));
  			        pk.setAnoInicioVigencia(new Long(not.getAnioIniVig()));
  			        pk.setAnoFinVigencia(new Long(not.getAnioFinVig()));
  			        pk.setEtapa(not.getEtapa());  						        
  			        FiseFormato14BC formato14B = formatoService14B.obtenerFormato14BCByPK(pk);
  			        boolean cumplePlazo = false;
			        cumplePlazo = commonService.fechaEnvioCumplePlazo(
			        		FiseConstants.TIPO_FORMATO_14B, 
			        		formato14B.getId().getCodEmpresa(), 
			        		formato14B.getId().getAnoPresentacion(), 
			        		formato14B.getId().getMesPresentacion(), 
			        		formato14B.getId().getEtapa(), 
			        		FechaUtil.fecha_DD_MM_YYYY(formato14B.getFechaEnvioDefinitivo()));
			        if(cumplePlazo ){
			        	logger.info("cumple plazo true 14B: "+cumplePlazo); 
			        	mapa.put("CHECKED_CUMPLE_PLAZO_14B", dirCheckedImage);
			        }else{
			        	mapa.put("CHECKED_CUMPLE_PLAZO_14B", dirUncheckedImage);
			        }
			        if(listaObs14B!=null && !listaObs14B.isEmpty() ){
			        	mapa.put("CHECKED_OBSERVACION_14B", dirUncheckedImage);
			        }else{
			        	mapa.put("CHECKED_OBSERVACION_14B", dirCheckedImage);
			        }     		 
			        formatos = formatos+" FISE 14B ";
			        mapa.put("NAMEF14B", "FISE 14B"); 	     		  
			        mapa.put("UNCHECKED14B", dirUncheckedImage);     		  
			        mapa.put("FFIRMADO14B", "Formato Firmado(1)");
			        mapa.put("FSUSTENTO14B", "Presenta Sustentos(1)");  	  		        
			        mapa.put("NAMEPLAZO14B", "Cumple Plazos");  			  
			        mapa.put("NAMEOBS14B", "Sin Observaciones");  			  
			        mapa.put("CHECKED14B",dirCheckedImage);  			  
			        mapa.put("NAMEOTROS14B", "Otros: ___________");
  			       
  				}else if(FiseConstants.NOMBRE_FORMATO_14C.equals(not.getFormato())){ 
  					Formato14CBean f14C = new Formato14CBean();  					
  					f14C.setCodEmpresa(not.getCodEmpresa());
  					f14C.setAnioPres(not.getAnioPres());
  					f14C.setMesPres(not.getMesPres());
  					f14C.setAnoIniVigencia(not.getAnioIniVig());
  					f14C.setAnoFinVigencia(not.getAnioFinVig());
  					f14C.setEtapa(not.getEtapa());  					
  					FiseFormato14CC formato14C = formatoService14C.obtenerFiseFormato14CC(f14C);
  					boolean cumplePlazo = false;
 			        cumplePlazo = commonService.fechaEnvioCumplePlazo(
 			        		FiseConstants.TIPO_FORMATO_14C, 
 			        		formato14C.getId().getCodEmpresa(), 
 			        		formato14C.getId().getAnoPresentacion(), 
 			        		formato14C.getId().getMesPresentacion(), 
 			        		formato14C.getId().getEtapa(), 
 			        		FechaUtil.fecha_DD_MM_YYYY(formato14C.getFechaEnvioDefinitivo()));
 			        if(cumplePlazo ){
 			        	logger.info("cumple plazo true 14C: "+cumplePlazo); 
 			        	mapa.put("CHECKED_CUMPLE_PLAZO_14C", dirCheckedImage);
 			        }else{
 			        	mapa.put("CHECKED_CUMPLE_PLAZO_14C", dirUncheckedImage);
 			        }
 			        if(listaObs14C!=null && !listaObs14C.isEmpty() ){
 			        	mapa.put("CHECKED_OBSERVACION_14C", dirUncheckedImage);
 			        }else{
 			        	mapa.put("CHECKED_OBSERVACION_14C", dirCheckedImage);
 			        }     		 
 			        formatos = formatos+" FISE 14C ";
 			        mapa.put("NAMEF14C", "FISE 14C"); 	     		  
 			        mapa.put("UNCHECKED14C", dirUncheckedImage);     		  
 			        mapa.put("FFIRMADO14C", "Formato Firmado(1)");
 			        mapa.put("FSUSTENTO14C", "Presenta Sustentos(1)");  	  		        
 			        mapa.put("NAMEPLAZO14C", "Cumple Plazos");  			  
 			        mapa.put("NAMEOBS14C", "Sin Observaciones");  			  
 			        mapa.put("CHECKED14C",dirCheckedImage);  			  
 			        mapa.put("NAMEOTROS14C", "Otros: ___________");
  					
  				}  				    
  			}//fin del for de la lista	
           mapa.put("FORMATOS", formatos); 			  		
		} catch (Exception e) {
		   logger.error("Error al llenar parametros del formato bienal "+e);
		   e.printStackTrace();
		}		
		return mapa;
	}	
	
	
	@SuppressWarnings("unchecked")
	@ResourceMapping("envioDefinitivoGeneral")
  	public void envioDefGeneral(ResourceRequest request,ResourceResponse response,
  			 @ModelAttribute("envioDefinitivoBean")EnvioDefinitivoBean n){
		
		try{
			logger.info("ingresando a envio def. general ");  
			
			response.setContentType("application/json");			
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);	
			PortletRequest pRequest = (PortletRequest) request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
			
			JSONObject jsonObj = new JSONObject(); 
			List<FileEntryJSP> listaArchivo = new ArrayList<FileEntryJSP>();		
			String directorio =  null;	
			String nombreReporte = "";	
			String mensajeCerrado="";
			//String respuestaEmail ="";		
			
			String usuario = themeDisplay.getUser().getLogin();
		    String terminal = themeDisplay.getUser().getLoginIP();
		    String email =  themeDisplay.getUser().getEmailAddress(); 
		    
		    String rutaImg = session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg");
		    String rutaImgCheck =session.getServletContext().getRealPath("/reports/checked.jpg");
		    String rutaImgUncheck =session.getServletContext().getRealPath("/reports/unchecked.jpg");
		    String rutaImgRelleno =session.getServletContext().getRealPath("/reports/relleno.jpg");
		    
			//boolean valor = false;
			boolean estadoCerrado = true;
			Map<String, Object> mapa =null;	
			boolean valorEmail = false;
			boolean valorValidacion = false;
			boolean actaEnvio = false;
			boolean actualizar = false;
			String f12A = "0";
			String f12B = "0";
			String f12C = "0";
			String f12D = "0";
			String f13A = "0";
			String f14A = "0";
			String f14B = "0";
			String f14C = "0";			
			String codEmpresa = n.getCodEmpresaBusq();				
			String optionFormato = n.getOptionFormato();
			String idgrupoInf = n.getGrupoInfBusq();
			String etapa = n.getEtapaBusq();    			
			logger.info("codigo empresa "+ codEmpresa);  			
  			logger.info("id Grupo inf "+ idgrupoInf);  	 			
  			logger.info("Option formato "+ optionFormato);
  			logger.info("etapa "+ etapa);	
  			logger.info("Desc Grupo inf.  "+n.getDescGrupoInf()); 
  			
  			
  			List<EnvioDefinitivoBean> lista = (List<EnvioDefinitivoBean>)pRequest.getPortletSession().getAttribute("listaEnvioDefinitivo", 
		    		PortletSession.APPLICATION_SCOPE);
  			
  			if(lista!=null){ 
  				/****Verificamos si este grupo ya ha sido enviado********/
  				FiseControlEnvioPorGrupo fiseGrupo = commonService.obtenerFiseControlEnvioByPK(codEmpresa,
  						etapa, new Long(idgrupoInf), optionFormato);
  				if(fiseGrupo==null){ 	  				
  	  				/****primero verificamos si existe algun estado cerrado en la lista****/
  	  				for(EnvioDefinitivoBean envio:lista){
  	  	  				if("CERRADO".equals(envio.getEstado())){
  	  	  					mensajeCerrado="El formato "+envio.getFormato()+" de "+envio.getMesPres()+" de "+envio.getAnioPres()+
  	  	  							" se encuentra fuera de plazo. No se puede enviar el conjunto";
  	  	  				    estadoCerrado = false;
  	  	  					break;
  	  	  				}				
  	  	  			}  				
  	  				if(estadoCerrado){  				
  	  	  				if(FiseConstants.MENSUAL.equals(optionFormato)){ 
  	  	  				/**PERIODO MENSUAL*/
  		  	  			   for(EnvioDefinitivoBean m:lista){
  		  	  				    /****Procesamos las observaciones***/
  		  	  				   valorValidacion=procesarValidacionObs(lista,usuario,terminal);	
  			  	  			    
  		  	  				   /******FORMATO 12A**************/
  			  	  			   if(FiseConstants.NOMBRE_FORMATO_12A.equals(m.getFormato())&&valorValidacion){
  			  	  				  /*****Obtenemos los parametros del map*******/
  			  	  			      mapa = parametros12A(codEmpresa, m.getAnioPres(), m.getMesPres(), 
  			  	  			    		   m.getAnioEjec(), m.getMesEjec(), etapa, rutaImg, usuario, terminal, email);		  	  			     
  			  	  				/*REPORTE FORMATO 12A*/
  			  	  			      if(mapa!=null){
  			  	  			       nombreReporte = "formato12A";	  	  			       
  			  	  			       directorio =  "/reports/"+nombreReporte+".jasper";
  			  	  			       File reportFile12A = new File(session.getServletContext().getRealPath(directorio));
  			  	  			       byte[] bytes12A = null;		  	  			       
  			  	  			       bytes12A = JasperRunManager.runReportToPdf(reportFile12A.getPath(), mapa, new JREmptyDataSource());
  			  	  			       if (bytes12A != null) {	  	  			    	 
  			  	  			    	   String nombre = FormatoUtil.nombreIndividualFormato(m.getCodEmpresa(),
  			  	  			    			Long.valueOf(m.getAnioPres()),Long.valueOf(m.getMesPres()),
  			  	  			    			   FiseConstants.NOMBRE_CONSOLIDADO_EMAIL);  	  			    	  
  			  	  			    	   FileEntry archivo12A = fiseUtil.subirDocumentoBytes(request, bytes12A, "application/pdf", nombre);
  			  	  			    	   if( archivo12A!=null ){
  			  	  			    		   FileEntryJSP fileEntryJsp = new FileEntryJSP();
  			  	  			    		   fileEntryJsp.setNombreArchivo(nombre);
  			  	  			    		   fileEntryJsp.setFileEntry(archivo12A);
  			  	  			    		   listaArchivo.add(fileEntryJsp);
  			  	  			    	   }
  			  	  			       }
  			  	  			      /*REPORTE OBSERVACIONES 12A*/
  				  	  		       if(listaObs12A!=null && listaObs12A.size()>0 ){
  				  	  		    	   nombreReporte = "validacion";		  	  		    	 
  				  	  			       directorio =  "/reports/"+nombreReporte+".jasper";
  				  	  			       File reportFile12AObs = new File(session.getServletContext().getRealPath(directorio));
  				  	  		    	   byte[] bytes12AObs = null;		  	  			      
  				  	  		    	   bytes12AObs = JasperRunManager.runReportToPdf(reportFile12AObs.getPath(), 
  				  	  		    			   mapa, new JRBeanCollectionDataSource(listaObs12A));		  	  		    	 
  				  	  			       if (bytes12AObs != null) {	  			    	   
  				  	  			    	   String nombre = FormatoUtil.nombreIndividualAnexoObs(m.getCodEmpresa(),
  				  	  			    			Long.valueOf(m.getAnioPres()),Long.valueOf(m.getMesPres()),
  				  	  			    			   FiseConstants.NOMBRE_CONSOLIDADO_EMAIL); 
  				  	  			    	   FileEntry archivo12AObs = fiseUtil.subirDocumentoBytes(request, bytes12AObs, "application/pdf", nombre);
  				  	  			    	   if( bytes12AObs!=null ){
  				  	  			    		   FileEntryJSP fileEntryJsp = new FileEntryJSP();
  				  	  			    		   fileEntryJsp.setNombreArchivo(nombre);
  				  	  			    		   fileEntryJsp.setFileEntry(archivo12AObs);
  				  	  			    		   listaArchivo.add(fileEntryJsp);
  				  	  			    	   }
  				  	  			       }
  				  	  		       }
  				  	  		        f12A = "1";
  			  	  			      }//fin if mapa ! null		  	  			     	  		   
  			  	  			   }  	  				   			
  		  	  				/******FORMATO 12B**************/
  			  	  			if(FiseConstants.NOMBRE_FORMATO_12B.equals(m.getFormato())&&valorValidacion){
  			  	  				mapa = parametros12B(codEmpresa, m.getAnioPres(), m.getMesPres(),
  			  	  						m.getAnioEjec(), m.getMesEjec(),  etapa, rutaImg, usuario, terminal, email);
  			  	  				if(mapa!=null){
  			  	  					nombreReporte = "formato12B";		 		      
  			  	  					directorio =  "/reports/"+nombreReporte+".jasper";
  			  	  					File reportFile12B = new File(session.getServletContext().getRealPath(directorio));
  			  	  					byte[] bytes12B = null;
  			  	  				    bytes12B = JasperRunManager.runReportToPdf(reportFile12B.getPath(), 
  			  	  							mapa, new JREmptyDataSource());
  			  	  					if (bytes12B != null) {		  	  					
  			  	  						String nombre = FormatoUtil.nombreIndividualFormato(m.getCodEmpresa(),
  			  	  								Long.valueOf(m.getAnioPres()),Long.valueOf(m.getMesPres()),
  			  	  								FiseConstants.NOMBRE_CONSOLIDADO_EMAIL); 
  			  	  						FileEntry archivo12B = fiseUtil.subirDocumentoBytes(request, bytes12B, "application/pdf", nombre);
  			  	  						if(archivo12B!=null ){
  			  	  							FileEntryJSP fileEntryJsp = new FileEntryJSP();
  			  	  							fileEntryJsp.setNombreArchivo(nombre);
  			  	  							fileEntryJsp.setFileEntry(archivo12B);
  			  	  							listaArchivo.add(fileEntryJsp);
  			  	  						}
  			  	  					}
  			  	  					/**REPORTE OBSERVACIONES*/
  			  	  					if(listaObs12B!=null && listaObs12B.size()>0 ){
  			  	  						nombreReporte = "validacion";		  	  					
  			  	  						directorio =  "/reports/"+nombreReporte+".jasper";
  			  	  						File reportFile12BObs = new File(session.getServletContext().getRealPath(directorio));
  			  	  						byte[] bytes12BObs = null;
  			  	  						bytes12BObs = JasperRunManager.runReportToPdf(reportFile12BObs.getPath(), 
  			  	  								mapa, new JRBeanCollectionDataSource(listaObs12B));		  	  					
  			  	  						if (bytes12BObs != null) {		  	  						
  			  	  							String nombre = FormatoUtil.nombreIndividualAnexoObs(m.getCodEmpresa(),
  			  	  									Long.valueOf(m.getAnioPres()),Long.valueOf(m.getMesPres()),
  			  	  									FiseConstants.NOMBRE_CONSOLIDADO_EMAIL); 
  			  	  							FileEntry archivo12BObs = fiseUtil.subirDocumentoBytes(request, bytes12BObs, "application/pdf", nombre);
  			  	  							if(archivo12BObs!=null ){
  			  	  								FileEntryJSP fileEntryJsp = new FileEntryJSP();
  			  	  								fileEntryJsp.setNombreArchivo(nombre);
  			  	  								fileEntryJsp.setFileEntry(archivo12BObs);
  			  	  								listaArchivo.add(fileEntryJsp);
  			  	  							}
  			  	  						}
  			  	  					}
  			  	  				  f12B = "1";
  			  	  				}//fin if mapa ! null			  	  				
  			  	  			}
  		  	  				/******FORMATO 12C**************/
  			  	  		    if(FiseConstants.NOMBRE_FORMATO_12C.equals(m.getFormato())&&valorValidacion){
  			  	  		    	mapa = parametros12C(codEmpresa, m.getAnioPres(), m.getMesPres(),
  			  	  		    			m.getAnioEjec(), m.getMesEjec(),  etapa, rutaImg, usuario, terminal, email);	
  			  	  		    	
  			  	  		    	FiseFormato12CCPK pk = new FiseFormato12CCPK();
  			  	  		    	pk.setCodEmpresa(codEmpresa);
  			  	  		    	pk.setAnoPresentacion(new Long(m.getAnioPres()));
  			  	  		    	pk.setMesPresentacion(new Long(m.getMesPres()));
  			  	  		    	pk.setEtapa(etapa);
  			  	  		        FiseFormato12CC formato = formatoService12C.obtenerFormato12CCByPK(pk);	
  			  	  		        if(mapa!=null && formato!=null){
  			  	  		        	nombreReporte = "formato12C";		  	  		    	
  			  	  		        	directorio = "/reports/" + nombreReporte + ".jasper";
  			  	  		        	File reportFile = new File(session.getServletContext().getRealPath(directorio));
  			  	  		        	byte[] bytes12C = null;
  			  	  		        	bytes12C = JasperRunManager.runReportToPdf(reportFile.getPath(), mapa, 
  			  	  		        			new JRBeanCollectionDataSource(formato.getFiseFormato12CDs()));
  			  	  		        	if (bytes12C != null) {				  	  		    		
  			  	  		        		String nombre = FormatoUtil.nombreIndividualFormato(m.getCodEmpresa(),
  			  	  		        				Long.valueOf(m.getAnioPres()),Long.valueOf(m.getMesPres()),
  			  	  		        				FiseConstants.NOMBRE_CONSOLIDADO_EMAIL);  	  	
  			  	  		        		FileEntry archivo12C = fiseUtil.subirDocumentoBytes(request, bytes12C, "application/pdf", nombre);
  			  	  		        		if (archivo12C != null) {
  			  	  		        			FileEntryJSP fileEntryJsp = new FileEntryJSP();
  			  	  		        			fileEntryJsp.setNombreArchivo(nombre);
  			  	  		        			fileEntryJsp.setFileEntry(archivo12C);
  			  	  		        			listaArchivo.add(fileEntryJsp);
  			  	  		        		}
  			  	  		        	}
  			  	  		        	/** REPORTE OBSERVACIONES */
  			  	  		        	if (listaObs12C != null && listaObs12C.size() > 0) {
  			  	  		        		nombreReporte = "validacion12";		  	  		    		
  			  	  		        		directorio = "/reports/" + nombreReporte + ".jasper";
  			  	  		        		File reportFile12CObs = new File(session.getServletContext().getRealPath(directorio));
  			  	  		        		byte[] bytes12CObs = null;
  			  	  		        		bytes12CObs = JasperRunManager.runReportToPdf(reportFile12CObs.getPath(), mapa, 
  			  	  		        				new JRBeanCollectionDataSource(listaObs12C));
  			  	  		        		if (bytes12CObs != null) {	  	  		    			
  			  	  		        			String nombre = FormatoUtil.nombreIndividualAnexoObs(m.getCodEmpresa(),
  			  	  		        					Long.valueOf(m.getAnioPres()),Long.valueOf(m.getMesPres()),
  			  	  		        					FiseConstants.NOMBRE_CONSOLIDADO_EMAIL); 
  			  	  		        			FileEntry archivo12CObs = fiseUtil.subirDocumentoBytes(request, bytes12CObs, "application/pdf", nombre);
  			  	  		        			if (archivo12CObs != null) {
  			  	  		        				FileEntryJSP fileEntryJsp = new FileEntryJSP();
  			  	  		        				fileEntryJsp.setNombreArchivo(nombre);
  			  	  		        				fileEntryJsp.setFileEntry(archivo12CObs);
  			  	  		        				listaArchivo.add(fileEntryJsp);
  			  	  		        			}
  			  	  		        		}
  			  	  		        	}
  			  	  		            f12C = "1";
  			  	  		        }//fin del if map!=null		  	  		    	
  		  	  			    }	
  		  	  			    /******FORMATO 12D**************/
  			  	  		   if(FiseConstants.NOMBRE_FORMATO_12D.equals(m.getFormato())&&valorValidacion){
  			  	  			   mapa = parametros12D(codEmpresa, m.getAnioPres(), m.getMesPres(),
  			  	  					   m.getAnioEjec(), m.getMesEjec(),  etapa, rutaImg, usuario, terminal, email);	

  			  	  			   FiseFormato12DCPK pk = new FiseFormato12DCPK();
  			  	  			   pk.setCodEmpresa(codEmpresa);
  			  	  			   pk.setAnoPresentacion(new Long(m.getAnioPres()));
  			  	  			   pk.setMesPresentacion(new Long(m.getMesPres()));
  			  	  			   pk.setEtapa(etapa);
  			  	  			   FiseFormato12DC formato = formatoService12D.obtenerFormato12DCByPK(pk);
  			  	  			   if(mapa!=null && formato!=null){
  			  	  				   nombreReporte = "formato12D";	  	  		    	
  			  	  				   directorio = "/reports/" + nombreReporte + ".jasper";
  			  	  				   File reportFile = new File(session.getServletContext().getRealPath(directorio));
  			  	  				   byte[] bytes12D = null;
  			  	  				   bytes12D = JasperRunManager.runReportToPdf(reportFile.getPath(), mapa, 
  			  	  						   new JRBeanCollectionDataSource(formato.getFiseFormato12DDs()));
  			  	  				   if (bytes12D != null) {				  	  		    		
  			  	  					   String nombre = FormatoUtil.nombreIndividualFormato(m.getCodEmpresa(),
  			  	  							   Long.valueOf(m.getAnioPres()),Long.valueOf(m.getMesPres()),
  			  	  							   FiseConstants.NOMBRE_CONSOLIDADO_EMAIL);  	  	
  			  	  					   FileEntry archivo12D = fiseUtil.subirDocumentoBytes(request, bytes12D, "application/pdf", nombre);
  			  	  					   if (archivo12D != null) {
  			  	  						   FileEntryJSP fileEntryJsp = new FileEntryJSP();
  			  	  						   fileEntryJsp.setNombreArchivo(nombre);
  			  	  						   fileEntryJsp.setFileEntry(archivo12D);
  			  	  						   listaArchivo.add(fileEntryJsp);
  			  	  					   }
  			  	  				   }
  			  	  				   /** REPORTE OBSERVACIONES */
  			  	  				   if (listaObs12D != null && listaObs12D.size() > 0) {
  			  	  					   nombreReporte = "validacion12";		  	  		    		
  			  	  					   directorio = "/reports/" + nombreReporte + ".jasper";
  			  	  					   File reportFile12DObs = new File(session.getServletContext().getRealPath(directorio));
  			  	  					   byte[] bytes12DObs = null;
  			  	  					   bytes12DObs = JasperRunManager.runReportToPdf(reportFile12DObs.getPath(), mapa, 
  			  	  							   new JRBeanCollectionDataSource(listaObs12D));
  			  	  					   if (bytes12DObs != null) {	  	  		    			
  			  	  						   String nombre = FormatoUtil.nombreIndividualAnexoObs(m.getCodEmpresa(),
  			  	  								   Long.valueOf(m.getAnioPres()),Long.valueOf(m.getMesPres()),
  			  	  								   FiseConstants.NOMBRE_CONSOLIDADO_EMAIL); 
  			  	  						   FileEntry archivo12DObs = fiseUtil.subirDocumentoBytes(request, bytes12DObs, "application/pdf", nombre);
  			  	  						   if (archivo12DObs != null) {
  			  	  							   FileEntryJSP fileEntryJsp = new FileEntryJSP();
  			  	  							   fileEntryJsp.setNombreArchivo(nombre);
  			  	  							   fileEntryJsp.setFileEntry(archivo12DObs);
  			  	  							   listaArchivo.add(fileEntryJsp);
  			  	  						   }
  			  	  					   }
  			  	  				   }
  			  	  				   f12D = "1";
  			  	  			   }//fin del if map!=null			  	  				
  		  	  			   }		  	  		       
  		  	  			 }/*fin del for para mensual*/	  	  				
  		  	  			 /**********ACTA CONSOLIDADO MENSUAL*********/ 
  		  	  			   mapa = parametrosActaMensual(lista, rutaImg, 
  		  	  					   rutaImgCheck, rutaImgUncheck, usuario, n.getDescGrupoInf(), etapa,rutaImgRelleno);	  	  			 
  		  	  			   if(mapa!=null){
  		  	  				nombreReporte = "gastosMensual";	  	  			 
  			  	  			   directorio =  "/reports/"+nombreReporte+".jasper";
  			  	  			   File reportFileActa = new File(session.getServletContext().getRealPath(directorio));
  			  	  			   byte[] bytesActa = null;
  			  	  			   bytesActa = JasperRunManager.runReportToPdf(reportFileActa.getPath(), mapa, new JREmptyDataSource());
  			  	  			   if (bytesActa != null) {
  			  	  				   session.setAttribute("bytesActaEnvio", bytesActa);	  	  				  
  			  	  				   String nombre = FormatoUtil.nombreIndividualActaRemisionGeneral(codEmpresa,	  	  			    			
  			  	  						   FiseConstants.NOMBRE_CONSOLIDADO_EMAIL); 	    		    	   
  			  	  				   FileEntry archivoActa = fiseUtil.subirDocumentoBytes(request, bytesActa, "application/pdf", nombre);
  			  	  				   if(archivoActa!=null ){
  			  	  					   FileEntryJSP fileEntryJsp = new FileEntryJSP();
  			  	  					   fileEntryJsp.setNombreArchivo(nombre);
  			  	  					   fileEntryJsp.setFileEntry(archivoActa);
  			  	  					   listaArchivo.add(fileEntryJsp);
  			  	  				   }
  			  	  			   }
  			  	  			   actaEnvio = true;
  		  	  			   }//fin if mapa!null	  	  			   
  		  	  				
  	  	  				}else if(FiseConstants.BIENAL.equals(optionFormato)){
  	  	  				/**PERIODO BIENAL*/
  		                   for(EnvioDefinitivoBean b:lista){
  		                	  /****Procesamos las observaciones***/
  		                	  valorValidacion= procesarValidacionObs(lista,usuario,terminal);
  			  	  			  
  		                      /******FORMATO 13A**************/
  		                	  if(FiseConstants.NOMBRE_FORMATO_13A.equals(b.getFormato())&&valorValidacion){
  		                		   /*****Obtenemos los parametros del map*******/
  			  	  			        mapa = parametros13A(codEmpresa, b.getAnioPres(), b.getMesPres(), etapa, 
  			  	  			    		   rutaImg, usuario, terminal, email); 
  			  	  			       /* REPORTE FORMATO 13A */
  			  	  			       if(mapa!=null){
  			  	  			    	nombreReporte = "formato13A";	       				
  				       				directorio = "/reports/" + nombreReporte + ".jasper";
  				       				File reportFile13A = new File(session.getServletContext().getRealPath(directorio));
  				       				byte[] bytes13A = null;	       				  				
  				       				bytes13A = JasperRunManager.runReportToPdf(reportFile13A.getPath(), mapa, new JRBeanCollectionDataSource(listaZonas13A));	       				
  				       				if (bytes13A != null) {	        					
  				       				    String nombre = FormatoUtil.nombreIndividualFormato(b.getCodEmpresa(),
  				  	  			    			Long.valueOf(b.getAnioPres()),Long.valueOf(b.getMesPres()),
  				  	  			    			   FiseConstants.NOMBRE_CONSOLIDADO_EMAIL);  
  				       					FileEntry archivo13A = fiseUtil.subirDocumentoBytes(request, bytes13A, "application/pdf", nombre);
  				       					if (archivo13A != null) {
  				       						FileEntryJSP fileEntryJsp = new FileEntryJSP();
  				       						fileEntryJsp.setNombreArchivo(nombre);
  				       						fileEntryJsp.setFileEntry(archivo13A);
  				       						listaArchivo.add(fileEntryJsp);
  				       					}
  				       				}
  				       				/*REPORTE OBSERVACIONES */
  				       				if (listaObs13A != null && listaObs13A.size() > 0) {
  				       					nombreReporte = "validacion13";	       					
  				       					directorio = "/reports/" + nombreReporte + ".jasper";
  				       					File reportFile13AObs = new File(session.getServletContext().getRealPath(directorio));
  				       					byte[] bytes13Obs = null;
  				       					bytes13Obs = JasperRunManager.runReportToPdf(reportFile13AObs.getPath(), mapa, new JRBeanCollectionDataSource(listaObs13A));
  				       					if (bytes13Obs != null) {		  						
  				       					    String nombre = FormatoUtil.nombreIndividualAnexoObs(b.getCodEmpresa(),
  					  	  			    			Long.valueOf(b.getAnioPres()),Long.valueOf(b.getMesPres()),
  					  	  			    			FiseConstants.NOMBRE_CONSOLIDADO_EMAIL); 
  				       						FileEntry archivo13Obs = fiseUtil.subirDocumentoBytes(request, bytes13Obs, "application/pdf", nombre);
  				       						if (archivo13Obs != null) {
  				       							FileEntryJSP fileEntryJsp = new FileEntryJSP();
  				       							fileEntryJsp.setNombreArchivo(nombre);
  				       							fileEntryJsp.setFileEntry(archivo13Obs);
  				       							listaArchivo.add(fileEntryJsp);
  				       						}
  				       					}
  				       				   f13A = "1";
  				       				}//fin del if map !null   
  			  	  			       }
  				       				
  		                	  }                	
  			                  /******FORMATO 14A**************/
  			                  if(FiseConstants.NOMBRE_FORMATO_14A.equals(b.getFormato())&&valorValidacion){
  			                	 /*****Obtenemos los parametros del map*******/
  			                	  mapa = parametros14A(codEmpresa, b.getAnioPres(), b.getMesPres(),  
  			  	  			    		   b.getAnioIniVig(), b.getAnioFinVig(),etapa,rutaImg, usuario, terminal, email);
  			                	  /* REPORTE FORMATO 14A */
  			                	    if(mapa!=null){
  			                	    	nombreReporte = "formato14A";	       				
  					       				directorio = "/reports/" + nombreReporte + ".jasper";
  					       				File reportFile14A = new File(session.getServletContext().getRealPath(directorio));
  					       				byte[] bytes14A = null;	       				  				
  					       				bytes14A = JasperRunManager.runReportToPdf(reportFile14A.getPath(), mapa, new JREmptyDataSource());	       				
  					       				if (bytes14A != null) {	        					
  					       				    String nombre = FormatoUtil.nombreIndividualFormato(b.getCodEmpresa(),
  					  	  			    			Long.valueOf(b.getAnioPres()),Long.valueOf(b.getMesPres()),
  					  	  			    			   FiseConstants.NOMBRE_CONSOLIDADO_EMAIL);  
  					       					FileEntry archivo14A = fiseUtil.subirDocumentoBytes(request, bytes14A, "application/pdf", nombre);
  					       					if (archivo14A != null) {
  					       						FileEntryJSP fileEntryJsp = new FileEntryJSP();
  					       						fileEntryJsp.setNombreArchivo(nombre);
  					       						fileEntryJsp.setFileEntry(archivo14A);
  					       						listaArchivo.add(fileEntryJsp);
  					       					}
  					       				}
  					       				/*REPORTE OBSERVACIONES */
  					       				if (listaObs14A != null && listaObs14A.size() > 0) {
  					       					nombreReporte = "validacion";	       					
  					       					directorio = "/reports/" + nombreReporte + ".jasper";
  					       					File reportFile14AObs = new File(session.getServletContext().getRealPath(directorio));
  					       					byte[] bytes14AObs = null;
  					       					bytes14AObs = JasperRunManager.runReportToPdf(reportFile14AObs.getPath(), mapa, new JRBeanCollectionDataSource(listaObs14A));
  					       					if (bytes14AObs != null) {		  						
  					       					    String nombre = FormatoUtil.nombreIndividualAnexoObs(b.getCodEmpresa(),
  						  	  			    			Long.valueOf(b.getAnioPres()),Long.valueOf(b.getMesPres()),
  						  	  			    			FiseConstants.NOMBRE_CONSOLIDADO_EMAIL); 
  					       						FileEntry archivo14AObs = fiseUtil.subirDocumentoBytes(request, bytes14AObs, "application/pdf", nombre);
  					       						if (archivo14AObs != null) {
  					       							FileEntryJSP fileEntryJsp = new FileEntryJSP();
  					       							fileEntryJsp.setNombreArchivo(nombre);
  					       							fileEntryJsp.setFileEntry(archivo14AObs);
  					       							listaArchivo.add(fileEntryJsp);
  					       						}
  					       					}
  					       					f14A = "1";
  					       				}//fin del if map ! null	
  			                	    }
  				       				
  			                  }
  			                  /******FORMATO 14B**************/
  			                  if(FiseConstants.NOMBRE_FORMATO_14B.equals(b.getFormato())&&valorValidacion){
  				                	 /*****Obtenemos los parametros del map*******/
  				                	  mapa = parametros14B(codEmpresa, b.getAnioPres(), b.getMesPres(),  
  				  	  			    		   b.getAnioIniVig(), b.getAnioFinVig(),etapa, rutaImg, usuario, terminal, email);
  				                	  /* REPORTE FORMATO 14B */
  				                	  if(mapa!=null){
  				                		  nombreReporte = "formato14B";	       				
  						       				directorio = "/reports/" + nombreReporte + ".jasper";
  						       				File reportFile14B = new File(session.getServletContext().getRealPath(directorio));
  						       				byte[] bytes14B = null;	       				  				
  						       				bytes14B = JasperRunManager.runReportToPdf(reportFile14B.getPath(), mapa, new JREmptyDataSource());	       				
  						       				if (bytes14B != null) {	        					
  						       				    String nombre = FormatoUtil.nombreIndividualFormato(b.getCodEmpresa(),
  						  	  			    			Long.valueOf(b.getAnioPres()),Long.valueOf(b.getMesPres()),
  						  	  			    			   FiseConstants.NOMBRE_CONSOLIDADO_EMAIL);  
  						       					FileEntry archivo14B = fiseUtil.subirDocumentoBytes(request, bytes14B, "application/pdf", nombre);
  						       					if (archivo14B != null) {
  						       						FileEntryJSP fileEntryJsp = new FileEntryJSP();
  						       						fileEntryJsp.setNombreArchivo(nombre);
  						       						fileEntryJsp.setFileEntry(archivo14B);
  						       						listaArchivo.add(fileEntryJsp);
  						       					}
  						       				}
  						       				/*REPORTE OBSERVACIONES */
  						       				if (listaObs14B != null && listaObs14B.size() > 0) {
  						       					nombreReporte = "validacion";	       					
  						       					directorio = "/reports/" + nombreReporte + ".jasper";
  						       					File reportFile14BObs = new File(session.getServletContext().getRealPath(directorio));
  						       					byte[] bytes14BObs = null;
  						       					bytes14BObs = JasperRunManager.runReportToPdf(reportFile14BObs.getPath(), mapa, new JRBeanCollectionDataSource(listaObs14B));
  						       					if (bytes14BObs != null) {		  						
  						       					    String nombre = FormatoUtil.nombreIndividualAnexoObs(b.getCodEmpresa(),
  							  	  			    			Long.valueOf(b.getAnioPres()),Long.valueOf(b.getMesPres()),
  							  	  			    			FiseConstants.NOMBRE_CONSOLIDADO_EMAIL); 
  						       						FileEntry archivo14BObs = fiseUtil.subirDocumentoBytes(request, bytes14BObs, "application/pdf", nombre);
  						       						if (archivo14BObs != null) {
  						       							FileEntryJSP fileEntryJsp = new FileEntryJSP();
  						       							fileEntryJsp.setNombreArchivo(nombre);
  						       							fileEntryJsp.setFileEntry(archivo14BObs);
  						       							listaArchivo.add(fileEntryJsp);
  						       						}
  						       					}
  						       				}
  						       				f14B = "1";
  				                	  }//fin del if map!null				       				
  				                  }
  			                  /******FORMATO 14C**************/                	   
  			                  if(FiseConstants.NOMBRE_FORMATO_14C.equals(b.getFormato())&&valorValidacion){
  				                	 /*****Obtenemos los parametros del map*******/
  				                	  mapa = parametros14C(codEmpresa, b.getAnioPres(), b.getMesPres(),  
  				  	  			    		   b.getAnioIniVig(), b.getAnioFinVig(), etapa,rutaImg,usuario, terminal, email);
  				                	  /* REPORTE FORMATO 14C */
  				                	   if(mapa!=null){
  				                		   nombreReporte = "formato14C";	       				
  						       				directorio = "/reports/" + nombreReporte + ".jasper";
  						       				File reportFile14C = new File(session.getServletContext().getRealPath(directorio));
  						       				byte[] bytes14C = null;	       				  				
  						       				bytes14C = JasperRunManager.runReportToPdf(reportFile14C.getPath(), mapa, new JREmptyDataSource());	       				
  						       				if (bytes14C != null) {	        					
  						       				    String nombre = FormatoUtil.nombreIndividualFormato(b.getCodEmpresa(),
  						  	  			    			Long.valueOf(b.getAnioPres()),Long.valueOf(b.getMesPres()),
  						  	  			    			   FiseConstants.NOMBRE_CONSOLIDADO_EMAIL);  
  						       					FileEntry archivo14C = fiseUtil.subirDocumentoBytes(request, bytes14C, "application/pdf", nombre);
  						       					if (archivo14C != null) {
  						       						FileEntryJSP fileEntryJsp = new FileEntryJSP();
  						       						fileEntryJsp.setNombreArchivo(nombre);
  						       						fileEntryJsp.setFileEntry(archivo14C);
  						       						listaArchivo.add(fileEntryJsp);
  						       					}
  						       				}
  						       				/*REPORTE OBSERVACIONES */
  						       				if (listaObs14C != null && listaObs14C.size() > 0) {
  						       					nombreReporte = "validacion";	       					
  						       					directorio = "/reports/" + nombreReporte + ".jasper";
  						       					File reportFile14CObs = new File(session.getServletContext().getRealPath(directorio));
  						       					byte[] bytes14CObs = null;
  						       					bytes14CObs = JasperRunManager.runReportToPdf(reportFile14CObs.getPath(), mapa, new JRBeanCollectionDataSource(listaObs14C));
  						       					if (bytes14CObs != null) {		  						
  						       					    String nombre = FormatoUtil.nombreIndividualAnexoObs(b.getCodEmpresa(),
  							  	  			    			Long.valueOf(b.getAnioPres()),Long.valueOf(b.getMesPres()),
  							  	  			    			FiseConstants.NOMBRE_CONSOLIDADO_EMAIL); 
  						       						FileEntry archivo14CObs = fiseUtil.subirDocumentoBytes(request, bytes14CObs, "application/pdf", nombre);
  						       						if (archivo14CObs != null) {
  						       							FileEntryJSP fileEntryJsp = new FileEntryJSP();
  						       							fileEntryJsp.setNombreArchivo(nombre);
  						       							fileEntryJsp.setFileEntry(archivo14CObs);
  						       							listaArchivo.add(fileEntryJsp);
  						       						}
  						       					}
  						       				} 
  						       				f14C = "1";
  				                	   }//fin del if map !null				       				
  				                  }	                  
  		  	  				}/*fin del for para bienal*/ 	                   
  		                /**********ACTA CONSOLIDADO BIENAL*********/ 
  		                   mapa = parametrosActaBienal(lista, rutaImg, 
  		  	  					   rutaImgCheck, rutaImgUncheck, usuario, n.getDescGrupoInf(), etapa,rutaImgRelleno);
  		  	  			   if(mapa!=null){
  		  	  				nombreReporte = "actaEnvioCosto";	    		     
  			    		       directorio =  "/reports/"+nombreReporte+".jasper";
  			    		       File reportFileActa = new File(session.getServletContext().getRealPath(directorio));
  			    		       byte[] bytesActa = null;
  			    		       bytesActa = JasperRunManager.runReportToPdf(reportFileActa.getPath(), mapa, new JREmptyDataSource());	    		      
  			    		       if (bytesActa != null) {
  			    		    	   session.setAttribute("bytesActaEnvio", bytesActa);	    		    	  
  			    		    	   String nombre = FormatoUtil.nombreIndividualActaRemisionGeneral(codEmpresa,	  	  			    			
  			  	  			    			FiseConstants.NOMBRE_CONSOLIDADO_EMAIL); 	    		    	   
  			    		    	   FileEntry archivoActa = fiseUtil.subirDocumentoBytes(request, bytesActa, "application/pdf", nombre);	    		    	   
  			    		    	   if( archivoActa!=null ){
  			    		    		   FileEntryJSP fileEntryJsp = new FileEntryJSP();
  			    		    		   fileEntryJsp.setNombreArchivo(nombre);
  			    		    		   fileEntryJsp.setFileEntry(archivoActa);
  			    		    		   listaArchivo.add(fileEntryJsp);
  			    		    	   }
  			    		       }
  			    		       actaEnvio = true;
  		  	  			   }//fin if mapa!null
  		  	  			   
  	  	  			   }//fin del if formatos bienales y Mensuales  	  			 
  	  	  			 /**********ECTUALIZACION DE FECHA ENVIO*********/
  	  	  				Map<String, Object> paramts = new HashMap<String, Object>();				
  	  	  				paramts.put("listaEnvio", lista);
  	  	  				paramts.put("f12A", f12A);				
  	  	  				paramts.put("f12B", f12B);	
  	  	  				paramts.put("f12C", f12C);	
  	  	  				paramts.put("f12D", f12D);	
  	  	  				paramts.put("f13A", f13A);	
  	  	  				paramts.put("f14A", f14A);	
  	  	  				paramts.put("f14B", f14B);	
  	  	  				paramts.put("f14C", f14C);
  	  	  				paramts.put("usuario", usuario);
  	  	  				paramts.put("terminal", terminal);  	  				
  	  	  				paramts.put("codEmpresa", codEmpresa);	
  	  	  				paramts.put("etapa", etapa);
  	  	  				paramts.put("periocidad", optionFormato);
  	  	  				paramts.put("idGrupo", idgrupoInf);  	  			
  	  	  				actualizar = commonService.actualizarFechaEnvioGeneral(paramts);
  	  	  				if(actualizar){
  	  	  					/**********ENVIO DE EMAIL*********/
  	  	  					logger.info("Valor del acta envio:  "+actaEnvio ); 
  	  	  					if(listaArchivo!=null && listaArchivo.size()>0 && actaEnvio){		    	  
  	  	  						logger.info("Entrando a enviar email envio general."); 
  	  	  					    String codEmpreCompleta = FormatoUtil.rellenaDerecha(codEmpresa, ' ', 4);
  	  	  						valorEmail = fiseUtil.enviarMailsAdjuntoEnvioGeneral(
  	  	  								request,
  	  	  								listaArchivo, 
  	  	  								mapaEmpresa.get(codEmpreCompleta),		    			  
  	  	  								n.getDescGrupoInf());
  	  	  						logger.info("El envio de email fue correctamente envio general.");	    	  
  	  	  					}
  	  	  					logger.info("Valor del envio de email:  "+valorEmail); 
  	  	  					if(valorEmail){
  	  	  						jsonObj.put("resultado", "OK"); 
  	  	  					}else{
  	  	  						jsonObj.put("resultado", "EMAIL");
  	  	  						jsonObj.put("mensaje", "Error al enviar el email del consolidado "); 
  	  	  					}
  	  	  				}else{
  	  	  					jsonObj.put("resultado", "ERROR");//ocurrio un error al actualizar fecha envio
  	  	  				}	  			 
  	  	  			}else{
  	  	  				jsonObj.put("resultado", "CERRADO");
  	  	  				jsonObj.put("mensaje", mensajeCerrado);
  	  	  			}//fin del proceso de lista estado cerrado  
  				}else{
  					jsonObj.put("resultado", "ENVIADO");
  				}//fin del grupo ya esta enviado	  				
  			}else{
  				jsonObj.put("resultado", "NO_DATOS");	
  			} //fin del proceso lista no tiene datos
  			
  			logger.info("arreglo json:"+jsonObj);
  			PrintWriter pw = response.getWriter();
  			pw.write(jsonObj.toString());
  			pw.flush();
  			pw.close();
  			pRequest.getPortletSession().setAttribute("listaEnvioDefinitivo", null, PortletSession.APPLICATION_SCOPE);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{
			if(listaObs12A!=null){
				listaObs12A=null;	
			}
			if(listaObs12B!=null){
				listaObs12B=null;	
			}
			if(listaObs12C!=null){
				listaObs12C=null;	
			}
			if(listaObs12D!=null){
				listaObs12D=null;	
			}
			if(listaObs13A!=null){
				listaObs13A=null;	
			}
			if(listaObs14A!=null){
				listaObs14A=null;	
			}
			if(listaObs14B!=null){
				listaObs14B=null;	
			}
			if(listaObs14C!=null){
				listaObs14C=null;	
			}
			if(listaZonas13A!=null){
				listaZonas13A=null;	
			}			
		}
	}
	
	@ResourceMapping("reporteEnvioDefinitivo")
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
	
	
	@ResourceMapping("verFormatosReporteEnvio")
	public void verFormatos(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("envioDefinitivoBean")EnvioDefinitivoBean n) {		
		try {	
			
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);	
			
			logger.info("Entrando a ver reporte de cada formato"); 			
			logger.info("Codigo empresa:  "+ n.getCodEmpresa()); 
			logger.info("anioPres:  "+ n.getAnioPres());	
			logger.info("mespres:  "+ n.getMesPres());	
			logger.info("etapa:  "+ n.getEtapa());	
			logger.info("anioEjec:  "+ n.getAnioEjec());	
			logger.info("mesEjec:  "+ n.getMesEjec());	
			logger.info("anioIniVig:  "+ n.getAnioIniVig());	
			logger.info("anioFinVig:  "+ n.getAnioFinVig());	
			logger.info("formato:  "+ n.getFormato());				
			
			String usuario = themeDisplay.getUser().getLogin();
		    String terminal = themeDisplay.getUser().getLoginIP();
		    String email =  themeDisplay.getUser().getEmailAddress(); 
		    Map<String, Object> mapa =null;	
		    String directorio =  "";	
		    String nombreReporte = "";
		    boolean valorReporte = false; 
		    String rutaImg = session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg");
		   
		    JSONObject jsonObj = new JSONObject();	   
		    
		    String tipoFormato = "FORMATO "+n.getFormato();
		    String tipoArchivo = "3";//PDF		   
		    session.setAttribute("tipoFormato",tipoFormato);
		    session.setAttribute("tipoArchivo",tipoArchivo);		
		
			if(FiseConstants.NOMBRE_FORMATO_12A.equals(n.getFormato())){ 
				nombreReporte = "formato12A";	  	  			       
				directorio =  "/reports/"+nombreReporte+".jasper";
				mapa = parametros12A(n.getCodEmpresa(), n.getAnioPres(), n.getMesPres(), 
						n.getAnioEjec(), n.getMesEjec(), n.getEtapa(), rutaImg, usuario, terminal, email);
				if(mapa!=null){
					logger.info("Map diferente de null"); 
					File reportFile12A = new File(session.getServletContext().getRealPath(directorio));
					logger.info("file diferente de null"); 
					byte[] bytes12A = null;		  	  			       
					bytes12A = JasperRunManager.runReportToPdf(reportFile12A.getPath(), mapa, new JREmptyDataSource());
					logger.info("Tamaño del arreglo de bytes>>>>>"+bytes12A.length); 
					if (bytes12A != null) {	  	  			    	 
						session.setAttribute("bytesFormato", bytes12A);
						valorReporte =true;
					}
				}

			}else if(FiseConstants.NOMBRE_FORMATO_12B.equals(n.getFormato())){ 
				nombreReporte = "formato12B";		 		      
				directorio =  "/reports/"+nombreReporte+".jasper";
				mapa = parametros12B(n.getCodEmpresa(), n.getAnioPres(), n.getMesPres(), 
						n.getAnioEjec(), n.getMesEjec(), n.getEtapa(), rutaImg, usuario, terminal, email);
				if(mapa!=null){	  					
					File reportFile12B = new File(session.getServletContext().getRealPath(directorio));
					byte[] bytes12B = null;
					bytes12B = JasperRunManager.runReportToPdf(reportFile12B.getPath(), 
							mapa, new JREmptyDataSource());
					if (bytes12B != null) {		  	  					
						session.setAttribute("bytesFormato", bytes12B);
						valorReporte =true;
					}
				}

			}else if(FiseConstants.NOMBRE_FORMATO_12C.equals(n.getFormato())){
				nombreReporte = "formato12C";		  	  		    	
				directorio = "/reports/" + nombreReporte + ".jasper";
				mapa = parametros12C(n.getCodEmpresa(), n.getAnioPres(), n.getMesPres(), 
						n.getAnioEjec(), n.getMesEjec(), n.getEtapa(), rutaImg, usuario, terminal, email);	
				FiseFormato12CCPK pk = new FiseFormato12CCPK();
				pk.setCodEmpresa(n.getCodEmpresa());
				pk.setAnoPresentacion(new Long(n.getAnioPres()));
				pk.setMesPresentacion(new Long(n.getMesPres()));
				pk.setEtapa(n.getEtapa());
				FiseFormato12CC formato = formatoService12C.obtenerFormato12CCByPK(pk);	
				if(mapa!=null && formato!=null){				
					File reportFile = new File(session.getServletContext().getRealPath(directorio));
					byte[] bytes12C = null;
					bytes12C = JasperRunManager.runReportToPdf(reportFile.getPath(), mapa, 
							new JRBeanCollectionDataSource(formato.getFiseFormato12CDs()));
					if (bytes12C != null) {				  	  		    		
						session.setAttribute("bytesFormato", bytes12C);
						valorReporte =true;
					}
				}

			}else if(FiseConstants.NOMBRE_FORMATO_12D.equals(n.getFormato())){ 
				nombreReporte = "formato12D";  	  		    	
				directorio = "/reports/" + nombreReporte + ".jasper";
				mapa = parametros12D(n.getCodEmpresa(), n.getAnioPres(), n.getMesPres(), 
						n.getAnioEjec(), n.getMesEjec(), n.getEtapa(), rutaImg, usuario, terminal, email);	
				FiseFormato12DCPK pk = new FiseFormato12DCPK();
				pk.setCodEmpresa(n.getCodEmpresa());
				pk.setAnoPresentacion(new Long(n.getAnioPres()));
				pk.setMesPresentacion(new Long(n.getMesPres()));
				pk.setEtapa(n.getEtapa());
				FiseFormato12DC formato = formatoService12D.obtenerFormato12DCByPK(pk);	
				if(mapa!=null && formato!=null){				
					File reportFile = new File(session.getServletContext().getRealPath(directorio));
					byte[] bytes12D = null;
					bytes12D = JasperRunManager.runReportToPdf(reportFile.getPath(), mapa, 
							new JRBeanCollectionDataSource(formato.getFiseFormato12DDs()));
					if (bytes12D != null) {				  	  		    		
						session.setAttribute("bytesFormato", bytes12D);
						valorReporte =true;
					}
				}

			}else if(FiseConstants.NOMBRE_FORMATO_13A.equals(n.getFormato())){ 
				nombreReporte = "formato13A";	       				
				directorio = "/reports/" + nombreReporte + ".jasper";
				mapa = parametros13A(n.getCodEmpresa(), n.getAnioPres(), n.getMesPres(), n.getEtapa(), 
						rutaImg, usuario, terminal, email); 				
				if(mapa!=null){					
					File reportFile13A = new File(session.getServletContext().getRealPath(directorio));
					byte[] bytes13A = null;	       				  				
					bytes13A = JasperRunManager.runReportToPdf(reportFile13A.getPath(), mapa, 
							new JRBeanCollectionDataSource(listaZonas13A));	       				
					if (bytes13A != null) {	        					
						session.setAttribute("bytesFormato", bytes13A);
						valorReporte =true;
					}
				}

			}else if(FiseConstants.NOMBRE_FORMATO_14A.equals(n.getFormato())){ 
				nombreReporte = "formato14A";	       				
				directorio = "/reports/" + nombreReporte + ".jasper";
				mapa = parametros14A(n.getCodEmpresa(), n.getAnioPres(), n.getMesPres(),  
						n.getAnioIniVig(), n.getAnioFinVig(),n.getEtapa(),rutaImg, usuario, terminal, email);            	 
				if(mapa!=null){            	    	
					File reportFile14A = new File(session.getServletContext().getRealPath(directorio));
					byte[] bytes14A = null;	       				  				
					bytes14A = JasperRunManager.runReportToPdf(reportFile14A.getPath(), mapa, new JREmptyDataSource());	       				
					if (bytes14A != null) {	        					
						session.setAttribute("bytesFormato", bytes14A);
						valorReporte =true;
					}
				}
            	    
			}else if(FiseConstants.NOMBRE_FORMATO_14B.equals(n.getFormato())){
				nombreReporte = "formato14B";	       				
				directorio = "/reports/" + nombreReporte + ".jasper";
				mapa = parametros14B(n.getCodEmpresa(), n.getAnioPres(), n.getMesPres(),  
						n.getAnioIniVig(), n.getAnioFinVig(),n.getEtapa(),rutaImg, usuario, terminal, email);
				if(mapa!=null){            		 
					File reportFile14B = new File(session.getServletContext().getRealPath(directorio));
					byte[] bytes14B = null;	       				  				
					bytes14B = JasperRunManager.runReportToPdf(reportFile14B.getPath(), mapa, new JREmptyDataSource());	       				
					if (bytes14B != null) {	        					
						session.setAttribute("bytesFormato", bytes14B);
						valorReporte =true;
					}
				}

			}else if(FiseConstants.NOMBRE_FORMATO_14C.equals(n.getFormato())){ 
				nombreReporte = "formato14C";	       				
				directorio = "/reports/" + nombreReporte + ".jasper";
				mapa = parametros14C(n.getCodEmpresa(), n.getAnioPres(), n.getMesPres(),  
						n.getAnioIniVig(), n.getAnioFinVig(),n.getEtapa(),rutaImg, usuario, terminal, email);           	 
				if(mapa!=null){           		 
					File reportFile14C = new File(session.getServletContext().getRealPath(directorio));
					byte[] bytes14C = null;	       				  				
					bytes14C = JasperRunManager.runReportToPdf(reportFile14C.getPath(), mapa, new JREmptyDataSource());	       				
					if (bytes14C != null) {	        					
						session.setAttribute("bytesFormato", bytes14C);
						valorReporte =true;
					}
				}           	   
			}
			if(valorReporte){
				jsonObj.put("resultado", "OK");	   		
			}else{
				jsonObj.put("resultado", "ERROR");	   	
			}
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();		  
			logger.info(jsonObj.toString());
			pw.write(jsonObj.toString());
			pw.flush();
			pw.close();	    
		 } catch (Exception e) {
			logger.error("Error al ver  formatos: "+e); 
			e.printStackTrace();
		}finally{			
			if(listaZonas13A!=null){
				listaZonas13A=null;	
			}						
		}
    }			
	
	

}
