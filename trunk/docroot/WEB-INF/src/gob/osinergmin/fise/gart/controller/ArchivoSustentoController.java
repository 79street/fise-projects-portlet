package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.ArchivoSustentoBean;
import gob.osinergmin.fise.bean.Formato12ACBean;
import gob.osinergmin.fise.bean.Formato12BCBean;
import gob.osinergmin.fise.bean.Formato12CCBean;
import gob.osinergmin.fise.bean.Formato12DCBean;
import gob.osinergmin.fise.bean.Formato13ACBean;
import gob.osinergmin.fise.bean.Formato13ADReportBean;
import gob.osinergmin.fise.bean.Formato14ACBean;
import gob.osinergmin.fise.bean.Formato14BCBean;
import gob.osinergmin.fise.bean.Formato14CBean;
import gob.osinergmin.fise.bean.Formato14CReportBean;
import gob.osinergmin.fise.bean.MensajeErrorBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.CfgTabla;
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
import gob.osinergmin.fise.gart.service.ArchivoSustentoService;
import gob.osinergmin.fise.gart.service.CfgTablaGartService;
import gob.osinergmin.fise.gart.service.FiseGrupoInformacionGartService;
import gob.osinergmin.fise.gart.service.FiseZonaBenefGartService;
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

import org.apache.log4j.Logger;
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
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.FileMimeTypeException;
import com.liferay.portlet.documentlibrary.FileSizeException;


@Controller("archivoSustentoController")
@RequestMapping("VIEW")
public class ArchivoSustentoController {
	
	Logger logger = Logger.getLogger(ArchivoSustentoController.class);
	
	@Autowired
	@Qualifier("archivoSustentoServiceImpl")
	private ArchivoSustentoService archivoSustentoService;
	
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
	
	
	@Autowired
	@Qualifier("fiseZonaBenefGartServiceImpl")
	private FiseZonaBenefGartService zonaBenefService;
	
	
	private Map<String, String> mapaEmpresa;
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
	
	
	@ModelAttribute("archivoSustentoBean")
    public ArchivoSustentoBean listArchivoSustentoBean() {
		ArchivoSustentoBean comman  = new ArchivoSustentoBean();
        return comman;	        
    }

	
	@RequestMapping
	public String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse,
			     @ModelAttribute("archivoSustentoBean")ArchivoSustentoBean a){
        try {
        	/***PARA MANEJAR CARGA DE ARCHIVOS DE SUSTENTO***/
        	PortletRequest pRequest = (PortletRequest)renderRequest.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
        	
        	String desEmpresa = (String)pRequest.getPortletSession().getAttribute("desEmpresa", PortletSession.APPLICATION_SCOPE);
    		String anioPresentacion = (String)pRequest.getPortletSession().getAttribute("anioPres", PortletSession.APPLICATION_SCOPE);
    		String mesPresentacion = (String)pRequest.getPortletSession().getAttribute("mesPres", PortletSession.APPLICATION_SCOPE);
    		String anioEjecucion = (String)pRequest.getPortletSession().getAttribute("anioEjec", PortletSession.APPLICATION_SCOPE);
    		String mesEjecucion = (String)pRequest.getPortletSession().getAttribute("mesEjec", PortletSession.APPLICATION_SCOPE);
    		String anoInicioVigencia = (String)pRequest.getPortletSession().getAttribute("anioIniVig", PortletSession.APPLICATION_SCOPE);
    		String anoFinVigencia = (String)pRequest.getPortletSession().getAttribute("anioFinVig", PortletSession.APPLICATION_SCOPE);
    		String etapa = (String)pRequest.getPortletSession().getAttribute("etapa", PortletSession.APPLICATION_SCOPE);
    		String formato = (String)pRequest.getPortletSession().getAttribute("formato", PortletSession.APPLICATION_SCOPE);
    		String correlativo = (String)pRequest.getPortletSession().getAttribute("correlativo", PortletSession.APPLICATION_SCOPE);
    		String flag = (String)pRequest.getPortletSession().getAttribute("flag", PortletSession.APPLICATION_SCOPE);
    		String msgError = (String)pRequest.getPortletSession().getAttribute("mensajeError", PortletSession.APPLICATION_SCOPE);
    		String msgInfo = (String)pRequest.getPortletSession().getAttribute("mensajeInfo", PortletSession.APPLICATION_SCOPE);
    		    		
    		String codEmpresa = (String)pRequest.getPortletSession().getAttribute("codEmpresaBusq", PortletSession.APPLICATION_SCOPE);
    		String grupoInf = (String)pRequest.getPortletSession().getAttribute("grupoInfBusq", PortletSession.APPLICATION_SCOPE);
    		String periocidad = (String)pRequest.getPortletSession().getAttribute("optionFormato", PortletSession.APPLICATION_SCOPE);
    		    		  		
    		a.setDesEmpresa(desEmpresa!=null?desEmpresa:"");
    		a.setAnioPres(anioPresentacion!=null?anioPresentacion:"");
    		a.setMesPres(mesPresentacion!=null?mesPresentacion:"");
    		a.setAnioEjec(anioEjecucion!=null?anioEjecucion:"");
    		a.setMesEjec(mesEjecucion!=null?mesEjecucion:"");
    		a.setAnioIniVig(anoInicioVigencia!=null?anoInicioVigencia:"");
    		a.setAnioFinVig(anoFinVigencia!=null?anoFinVigencia:"");
    		a.setEtapa(etapa!=null?etapa:"");
    		a.setFormato(formato!=null?formato:"");
    		a.setCorrelativo(correlativo!=null?correlativo:"");
    		a.setFlag(flag!=null?flag:"");   
    		a.setMensajeError(msgError!=null?msgError:"");
    		a.setMensajeInfo(msgInfo!=null?msgInfo:"");    		     	
        	
    		if(a.getListaEmpresas()==null){
    			logger.info("Lista de empresa es null:  " +renderRequest); 
    			a.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));
    		}     		
    		a.setAdmin(fiseUtil.esAdministrador(renderRequest));
    		
    		a.setListaGrupoInf(fiseGrupoInformacionService.listarGrupoInformacion(FiseConstants.MENSUAL,"")); 
    		
    		mapaEmpresa = fiseUtil.getMapaEmpresa();   		
    		
    		mapaSectorTipico = fiseUtil.getMapaSectorTipico();
    		
    		mapaEtapaEjecucion = fiseUtil.getMapaEtapaEjecucion();
    		
    		a.setCodEmpresaBusq(codEmpresa!=null?codEmpresa:""); 
    		a.setGrupoInfBusq(grupoInf!=null?grupoInf:""); 
    		a.setOptionFormato(periocidad!=null?periocidad:"");     		
    		
    		//limpiando los valores de sesion para la carga de archivos de sustento
    		pRequest.getPortletSession().setAttribute("desEmpresa", "", PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("anioPres", "", PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("mesPres", "", PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("anioEjec", "", PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("mesEjec", "", PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("anioIniVig", "", PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("anioFinVig", "", PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("etapa", "", PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("formato", "", PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("correlativo", "", PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("flag", "", PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("mensajeInfo", "", PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("mensajeError", "", PortletSession.APPLICATION_SCOPE);
    		
		    pRequest.getPortletSession().setAttribute("codEmpresaBusq", "", PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("grupoInfBusq", "", PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("optionFormato", "", PortletSession.APPLICATION_SCOPE);
    		
    		model.addAttribute("model", a);
    		
		} catch (Exception e) {
			logger.info("Ocurrio un errror al caragar la pagina de archivo de sustentos"); 
			e.printStackTrace();
		}		
		return "archivoSustento";
	}
	
	
	@ResourceMapping("busquedaFormatos")
  	public void busqueda(ResourceRequest request,ResourceResponse response,
  			 @ModelAttribute("archivoSustentoBean")ArchivoSustentoBean a){
		
		try{
			response.setContentType("application/json");
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);	
			PortletRequest pRequest = (PortletRequest) request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
			
			String codEmpresa = a.getCodEmpresaBusq();				
			String optionFormato = a.getOptionFormato();
			String idGrupoInf = a.getGrupoInfBusq();
			String flagBusq = a.getFlagBusq();
			String etapaBusq = a.getEtapaBusq();
			long idGrupo=0;
			
			String usuario = themeDisplay.getUser().getLogin();
		    String terminal = themeDisplay.getUser().getLoginIP();
		    
			String data ="";			
			logger.info("codigo empresa "+ codEmpresa);  			
  			logger.info("id Grupo inf "+ idGrupoInf);  	 			
  			logger.info("Option formato "+ optionFormato);
  			logger.info("flag busqueda "+ flagBusq);
  			
  			if(FormatoUtil.isNotBlank(a.getGrupoInfBusq())){ 
		    	idGrupo = new Long(idGrupoInf);
		    }
  			List<ArchivoSustentoBean> lista =archivoSustentoService.listarFiseArchivosCab(codEmpresa, idGrupo,
  					etapaBusq, usuario, terminal, flagBusq);
  			
  			logger.info("tamaño de la lista de formatos para agregar archivos de sustento..   :"+lista.size());
  			
  			List<ArchivoSustentoBean> listaFormatos = new ArrayList<ArchivoSustentoBean>();
  			
  			for(ArchivoSustentoBean not : lista){    				
  				not.setDesEmpresa(mapaEmpresa.get(not.getCodEmpresa()));
  				not.setDesMes(fiseUtil.getMapaMeses().get(Long.valueOf(not.getMesPres())));
  				if(!"00".equals(not.getMesEjec())){ 
  					not.setDesMesEje(fiseUtil.getMapaMeses().get(Long.valueOf(not.getMesEjec())));   		
  				}else{
  					not.setDesMesEje("---");
  				}
  				listaFormatos.add(not);
  			} 			
  			data = toStringListJSON(listaFormatos);
  			logger.info("arreglo json:"+data);
  			PrintWriter pw = response.getWriter();
  			pw.write(data);
  			pw.flush();
  			pw.close(); 
  			pRequest.getPortletSession().setAttribute("listaFormatosSustento", listaFormatos, PortletSession.APPLICATION_SCOPE);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}	
	
	private String toStringListJSON(List<ArchivoSustentoBean> lista) {
		Serializer serializer = new JsonSerializer();
		Object result = serializer.serialize(lista);
		String data = String.valueOf(result);
		return data;
	}	
		
	
	@ResourceMapping("cargarGrupoInformacion")
  	public void cargaGrupoInformacion(ModelMap model, ResourceRequest request,ResourceResponse response,
  			 @ModelAttribute("archivoSustentoBean")ArchivoSustentoBean a){
		try {			
  			response.setContentType("applicacion/json");
  			String tipoFormato = a.getOptionFormato();
  			
  			logger.info("Codigo grupo inf. para cargar grupo de infor.:  "+tipoFormato);
  			
  			List<FiseGrupoInformacion> listaGrupoInf = fiseGrupoInformacionService.listarGrupoInformacion(tipoFormato,"");
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
	
	
	
	@ResourceMapping("busquedaArchivosSustentoFormato")
  	public void busquedaArchivosSustento(ResourceRequest request,ResourceResponse response,
  			 @ModelAttribute("archivoSustentoBean")ArchivoSustentoBean a){
		
		try{
			response.setContentType("application/json");	
			
			String correl = a.getCorrelativo();	
			
			String data ="";			
			logger.info("codigo correlativo "+ correl); 
			
			long correlativo = 0;
			
			if(FormatoUtil.isNotBlank(correl)){ 
				correlativo = Long.valueOf(correl);
			}
			
  			List<ArchivoSustentoBean> lista =archivoSustentoService.listarArchivosSustentoFormato(correlativo);
  			
  			logger.info("tamaño de la lista de archivos de sustento..   :"+lista.size()); 			
  		
  			data = toStringListJSON(lista);
  			logger.info("arreglo json:"+data);
  			PrintWriter pw = response.getWriter();
  			pw.write(data);
  			pw.flush();
  			pw.close();   			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	
	
     /***Carga de archivos de sustento por cada formato***/
	
	@ActionMapping(params="action=cargar")
	public void cargarArchivoSustento(ActionRequest request,ActionResponse response,
			@ModelAttribute("archivoSustentoBean")ArchivoSustentoBean a){
		
		logger.info("--- cargar archivo de sustento");
				
		String  mensaje = "01"+"/"+""; //inicializamos con codigo 01 = error
		boolean valor = false;
		boolean valorExption = false;
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(request);
		PortletRequest pRequest = (PortletRequest) request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
		
		String flagCarga = uploadPortletRequest.getParameter("flagCarga");//indica si en nuevo o reemplazo
		
		
		String desEmpresa = uploadPortletRequest.getParameter("desEmpresaF");
		String anioPresF = uploadPortletRequest.getParameter("anioPresF");
		String mesPresF = uploadPortletRequest.getParameter("mesPresF");
		String anioEjecF = uploadPortletRequest.getParameter("anioEjecF");
		String mesEjecF = uploadPortletRequest.getParameter("mesEjecF");
		String anioIniVigF = uploadPortletRequest.getParameter("anioIniVigF");
		String anioFinVigF = uploadPortletRequest.getParameter("anioFinVigF");
		String estapaF = uploadPortletRequest.getParameter("estapaF");
		String formatoF = uploadPortletRequest.getParameter("formatoF");
		//para la busqueda inicial 
		String codEmpresaF = uploadPortletRequest.getParameter("codEmpresaF");
		String grupoInf = uploadPortletRequest.getParameter("grupoInforF");
		String periocidad = uploadPortletRequest.getParameter("periocidadF");
		
		
		//nuevo    	
    	String correlativoFormato =uploadPortletRequest.getParameter("correlativoF");   	
    	
    	logger.info("Flag de carga:  "+flagCarga);   	
    	
    	//variables solo cuando es reemplazo de archivo de sustento
    	String itemArchivo =uploadPortletRequest.getParameter("itemArchivo");
    	String correlativoArchivo =uploadPortletRequest.getParameter("correlativoArchivo");    	
    	
				
		if(flagCarga.equals(FiseConstants.FLAG_CARGA_ARCHIVO_NUEVO) &&
				FormatoUtil.isNotBlank(correlativoFormato)){		
			logger.info("correlativo formato:  "+correlativoFormato);   
			valor = true;
		}else if(flagCarga.equals(FiseConstants.FLAG_CARGA_ARCHIVO_ACTUALIZAR) &&
				FormatoUtil.isNotBlank(itemArchivo) &&
				FormatoUtil.isNotBlank(correlativoArchivo)){
			logger.info("item archivo:  "+itemArchivo);
			logger.info("correlativo archivo:  "+correlativoArchivo);
			valor = true;
		}			
		if(valor){
			FileEntry fileEntry=null;
			try{
				String user = themeDisplay.getUser().getLogin();
		    	String terminal = themeDisplay.getUser().getLoginIP();
		    	
				if(flagCarga.equals(FiseConstants.FLAG_CARGA_ARCHIVO_NUEVO)){				
					fileEntry = fiseUtil.subirArchivoSustento(request, uploadPortletRequest);				
					logger.info("Nombre del archivo:  "+fileEntry.getTitle()); 
					logger.info("Des del archivo:  "+fileEntry.getDescription()); 
					logger.info("Extension del archivo:  "+fileEntry.getExtension());					
					logger.info("ID FILEENTRY :  "+fileEntry.getFileEntryId());
					mensaje = grabarArchivoSustento(fileEntry.getTitle(), correlativoFormato, user,
							terminal,fileEntry.getFileEntryId());	
				}else if(flagCarga.equals(FiseConstants.FLAG_CARGA_ARCHIVO_ACTUALIZAR) ){
					fileEntry = fiseUtil.subirArchivoSustento(request, uploadPortletRequest);
					mensaje = actualizarArchivoSustento(fileEntry.getTitle(), itemArchivo,correlativoArchivo, 
							user, terminal,fileEntry.getFileEntryId());
				}
			}catch(FileMimeTypeException ex){
				//ex.printStackTrace();
				logger.info("Entro a l exception de mimi type");
				valorExption = true;
				mensaje  = "01"+"/"+"El archivo no tiene una extensión válida."; 
			}catch (FileSizeException size) {
				//e.printStackTrace();
				logger.info("Entro a l exception max size");
				valorExption = true;
				mensaje  = "01"+"/"+"El archivo pesa más de 4 MB"; 
			}catch (Exception e) {
				//e.printStackTrace();
				logger.info("Entro a l exception de general");
				if(!valorExption){
					mensaje  = "01"+"/"+"Ocurrio un error al subir archivo al contenedor de archivos del servidor"; 
				}	
			}
			
			String[] msnId = mensaje.split("/");	
		    
			if(("00").equals(msnId[0])){
				logger.info("Entrando a ok exitoso");
				pRequest.getPortletSession().setAttribute("desEmpresa", desEmpresa, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("anioPres", anioPresF, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("mesPres", mesPresF, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("anioEjec", anioEjecF, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("mesEjec", mesEjecF, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("anioIniVig", anioIniVigF, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("anioFinVig", anioFinVigF, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("etapa", estapaF, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("formato", formatoF, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("correlativo", correlativoFormato, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("flag", "", PortletSession.APPLICATION_SCOPE);
			    //para la busqueda inicial
			    pRequest.getPortletSession().setAttribute("codEmpresaBusq", codEmpresaF, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("grupoInfBusq", grupoInf, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("optionFormato", periocidad, PortletSession.APPLICATION_SCOPE);
			    
			    if(flagCarga.equals(FiseConstants.FLAG_CARGA_ARCHIVO_NUEVO)){
					pRequest.getPortletSession().setAttribute("mensajeInfo", msnId[1], PortletSession.APPLICATION_SCOPE);	
				}else if( flagCarga.equals(FiseConstants.FLAG_CARGA_ARCHIVO_ACTUALIZAR)){
					pRequest.getPortletSession().setAttribute("mensajeInfo", msnId[1], PortletSession.APPLICATION_SCOPE);
				}	
			}else{
				logger.info("Entrando a error");					
				pRequest.getPortletSession().setAttribute("desEmpresa", desEmpresa, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("anioPres", anioPresF, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("mesPres", mesPresF, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("anioEjec", anioEjecF, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("mesEjec", mesEjecF, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("anioIniVig", anioIniVigF, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("anioFinVig", anioFinVigF, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("etapa", estapaF, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("formato", formatoF, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("correlativo", correlativoFormato, PortletSession.APPLICATION_SCOPE);
			    //para la busqueda inicial
			    pRequest.getPortletSession().setAttribute("codEmpresaBusq", codEmpresaF, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("grupoInfBusq", grupoInf, PortletSession.APPLICATION_SCOPE);
			    pRequest.getPortletSession().setAttribute("optionFormato", periocidad, PortletSession.APPLICATION_SCOPE);
			    
			    if(flagCarga.equals(FiseConstants.FLAG_CARGA_ARCHIVO_NUEVO)){
			    	logger.info("Entrando a error nuevo ");
			    	pRequest.getPortletSession().setAttribute("flag", "E", PortletSession.APPLICATION_SCOPE);	
				}else if( flagCarga.equals(FiseConstants.FLAG_CARGA_ARCHIVO_ACTUALIZAR)){
					pRequest.getPortletSession().setAttribute("flag", " ", PortletSession.APPLICATION_SCOPE);	
				}			    
			    pRequest.getPortletSession().setAttribute("mensajeError", msnId[1], PortletSession.APPLICATION_SCOPE);
			}		
		}else{
			/***Entra solo cuando el correlativo del formato, item o correlativo de archivo son nulos*/
			logger.info("Entrando a correlito item nulo");
			pRequest.getPortletSession().setAttribute("desEmpresa", desEmpresa, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("anioPres", anioPresF, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("mesPres", mesPresF, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("anioEjec", anioEjecF, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("mesEjec", mesEjecF, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("anioIniVig", anioIniVigF, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("anioFinVig", anioFinVigF, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("etapa", estapaF, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("formato", formatoF, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("correlativo", correlativoFormato, PortletSession.APPLICATION_SCOPE);
		    //para la busqueda inicial
		    pRequest.getPortletSession().setAttribute("codEmpresaBusq", codEmpresaF, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("grupoInfBusq", grupoInf, PortletSession.APPLICATION_SCOPE);
		    pRequest.getPortletSession().setAttribute("optionFormato", periocidad, PortletSession.APPLICATION_SCOPE);
		    
		    if(flagCarga.equals(FiseConstants.FLAG_CARGA_ARCHIVO_NUEVO)){
		    	pRequest.getPortletSession().setAttribute("flag", "E", PortletSession.APPLICATION_SCOPE);	
			}else if( flagCarga.equals(FiseConstants.FLAG_CARGA_ARCHIVO_ACTUALIZAR)){
				pRequest.getPortletSession().setAttribute("flag", " ", PortletSession.APPLICATION_SCOPE);	
			}			    
		    pRequest.getPortletSession().setAttribute("mensajeError", "Por favor vuelva a intentar subir el archivo", PortletSession.APPLICATION_SCOPE);
		}			
	}		
	
	private String grabarArchivoSustento(String nombreArchivo,String correlativoF,
			String user,String terminal,long idFileEntry){
		String  mensaje  = "01"+"/"+""; 
		try {
			String valor = archivoSustentoService.guardarArchivoSustento(correlativoF,nombreArchivo,
					idFileEntry,user, terminal);
			if("1".equals(valor)){ 
				mensaje  = "00"+"/"+"El archivo de sustento fue subido satisfactoriamente"; 
			}else{
				mensaje  = "01"+"/"+"Ocurrio un error al grabar en detalle de archivo sustento"; 	
			}
		} catch (Exception e) {
			e.printStackTrace();
			mensaje  = "01"+"/"+"Ocurrio un error al grabar en detalle de archivo sustento"; 	
		}
		return mensaje;
	}
	
	
	private String actualizarArchivoSustento(String nombreArchivo,String itemArchivo,String correlativoArchivo,
			String user,String terminal,long idFileEntry){
		String  mensaje  = "01"+"/"+""; 
		try {
			String valor = archivoSustentoService.actualizarArchivoSustento(itemArchivo, correlativoArchivo,
					nombreArchivo,idFileEntry, user, terminal);
			if("1".equals(valor)){ 
				mensaje  = "00"+"/"+"El archivo de sustento fue reemplazado satisfactoriamente"; 
			}else{
				mensaje  = "01"+"/"+"Ocurrio un error al actualizar en detalle de archivo sustento"; 	
			}
		} catch (Exception e) {
			mensaje  = "01"+"/"+"Ocurrio un error al actualizar en detalle de archivo sustento"; 	
		}
		return mensaje;
	}
	
	
	@ResourceMapping("eliminarArchivosSustento")
	public void eliminarArchivoSustento(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("archivoSustentoBean")ArchivoSustentoBean a) { 	
		
		JSONObject jsonObj = new JSONObject();
		try {	
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);	
			a.setUsuario(themeDisplay.getUser().getLogin());
			a.setTerminal(themeDisplay.getUser().getLoginIP());	
			
			logger.info("Entrando a eliminar archivo de sustento"); 			
			logger.info("correlativo:  "+ a.getCorrArchivo()); 
			logger.info("item:  "+ a.getItemArchivo());   
			
			logger.info("Enviando el formulario al service"); 
			
			String valor = archivoSustentoService.eliminarArchivoSustento(a.getItemArchivo(), a.getCorrArchivo());
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
			logger.error("Error al eliminar el archivo de sustento: "+e.getMessage());
		} 	
	}
	
	
	@ResourceMapping("descargarArchivoSustento")
	public void descargarArchivoSustento(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("archivoSustentoBean")ArchivoSustentoBean a) {		
		try {					
			logger.info("Entrando a descargar archivo sustento"); 		
			JSONObject jsonObj = new JSONObject();	   
			logger.info("Id file entry :  "+a.getIdFileEntry()); 		 
			if(FormatoUtil.isNotBlank(a.getIdFileEntry())){ 
				String urlArchivo = fiseUtil.urlArchivoSustento(request, Long.valueOf(a.getIdFileEntry()));	 
				logger.info("RUTA URL DEL ARCHIVO  :  "+urlArchivo); 			   	   
			    jsonObj.put("resultado", "OK");	
			    jsonObj.put("url", urlArchivo);	
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
			logger.error("Error al descargar archivo sustento: "+e); 
			e.printStackTrace();
		}
    }			
	
	
	
	@ResourceMapping("verObservacionesFormatos")
	public void verObservacionesFormatos(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("archivoSustentoBean")ArchivoSustentoBean a) {		
		try {	
			JSONArray jsonArray = null;			
			logger.info("Entrando a ver observaciones de cada formato"); 			
			logger.info("Codigo empresa:  "+ a.getCodEmpresa()); 
			logger.info("anioPres:  "+ a.getAnioPres());	
			logger.info("mespres:  "+ a.getMesPres());	
			logger.info("etapa:  "+ a.getEtapa());	
			logger.info("anioEjec:  "+ a.getAnioEjec());	
			logger.info("mesEjec:  "+ a.getMesEjec());	
			logger.info("anioIniVig:  "+ a.getAnioIniVig());	
			logger.info("anioFinVig:  "+ a.getAnioFinVig());	
			logger.info("formato:  "+ a.getFormato());	
		
			if(FiseConstants.NOMBRE_FORMATO_12A.equals(a.getFormato())){ 
				jsonArray = new JSONArray();
				FiseFormato12ACPK pk = new FiseFormato12ACPK();
				pk.setCodEmpresa(a.getCodEmpresa());
				pk.setAnoPresentacion(new Long(a.getAnioPres()));
				pk.setMesPresentacion(new Long(a.getMesPres()));
				pk.setAnoEjecucionGasto(new Long(a.getAnioEjec()));
				pk.setMesEjecucionGasto(new Long(a.getMesEjec()));
				pk.setEtapa(a.getEtapa());  	  			        
				FiseFormato12AC formato12A = formatoService12A.obtenerFormato12ACByPK(pk);
				if(formato12A.getFiseFormato12ADs()!=null){
					cargarListaObservaciones12A(formato12A.getFiseFormato12ADs());	
				}else{
					listaObs12A = new ArrayList<MensajeErrorBean>();
				}					
				for (MensajeErrorBean error : listaObs12A) {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("id", error.getId());	
					jsonObj.put("descZonaBenef", error.getDescZonaBenef());
					jsonObj.put("codigo", error.getCodigo());			
					jsonObj.put("descripcion", error.getDescripcion());					
					jsonArray.put(jsonObj);		
				}

			}else if(FiseConstants.NOMBRE_FORMATO_12B.equals(a.getFormato())){ 
				jsonArray = new JSONArray();
				FiseFormato12BCPK pk=new FiseFormato12BCPK();
				pk.setCodEmpresa(a.getCodEmpresa());
				pk.setAnoPresentacion(new Integer(a.getAnioPres()));
				pk.setMesPresentacion(new Integer(a.getMesPres()));
				pk.setAnoEjecucionGasto(new Integer(a.getAnioEjec()));
				pk.setMesEjecucionGasto(new Integer(a.getMesEjec()));
				pk.setEtapa(a.getEtapa());  
				FiseFormato12BC formato12B =formatoService12B.getFormatoCabeceraById(pk);
				if(formato12B.getListaDetalle12BDs()!=null){
					cargarListaObservaciones12B(formato12B.getListaDetalle12BDs());
				}else{
					listaObs12B = new ArrayList<MensajeErrorBean>();	
				}				
				for (MensajeErrorBean error : listaObs12B) {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("id", error.getId());	
					jsonObj.put("descZonaBenef", error.getDescZonaBenef());
					jsonObj.put("codigo", error.getCodigo());			
					jsonObj.put("descripcion", error.getDescripcion());						
					jsonArray.put(jsonObj);		
				}

			}else if(FiseConstants.NOMBRE_FORMATO_12C.equals(a.getFormato())){ 
				jsonArray = new JSONArray();
				FiseFormato12CCPK pk = new FiseFormato12CCPK();
				pk.setCodEmpresa(a.getCodEmpresa());
				pk.setAnoPresentacion(new Long(a.getAnioPres()));
				pk.setMesPresentacion(new Long(a.getMesPres()));
				pk.setEtapa(a.getEtapa()); 
				FiseFormato12CC formato12C = formatoService12C.obtenerFormato12CCByPK(pk);
				if(formato12C.getFiseFormato12CDs()!=null){
					cargarListaObservaciones12C(formato12C.getFiseFormato12CDs());
				}else{
					listaObs12C = new ArrayList<MensajeErrorBean>();	
				}				
				for (MensajeErrorBean error : listaObs12C) {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("id", error.getId());
					jsonObj.put("nroItemEtapa", error.getNroItemEtapa());
					jsonObj.put("codigo", error.getCodigo());
					jsonObj.put("descripcion", error.getDescripcion());
					jsonObj.put("descEtapaEjecucion", error.getDescEtapaEjecucion());					
					jsonArray.put(jsonObj);
				}		        

			}else if(FiseConstants.NOMBRE_FORMATO_12D.equals(a.getFormato())){ 
				jsonArray = new JSONArray();
				FiseFormato12DCPK pk = new FiseFormato12DCPK();
				pk.setCodEmpresa(a.getCodEmpresa());
				pk.setAnoPresentacion(new Long(a.getAnioPres()));
				pk.setMesPresentacion(new Long(a.getMesPres()));
				pk.setEtapa(a.getEtapa()); 
				FiseFormato12DC formato12D = formatoService12D.obtenerFormato12DCByPK(pk);
				if(formato12D.getFiseFormato12DDs()!=null){
					cargarListaObservaciones12D(formato12D.getFiseFormato12DDs());	
				}else{
					listaObs12D = new ArrayList<MensajeErrorBean>();	
				}				
				for (MensajeErrorBean error : listaObs12D) {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("id", error.getId());
					jsonObj.put("nroItemEtapa", error.getNroItemEtapa());
					jsonObj.put("codigo", error.getCodigo());
					jsonObj.put("descripcion", error.getDescripcion());
					jsonObj.put("descEtapaEjecucion", error.getDescEtapaEjecucion());			
					jsonArray.put(jsonObj);
				}
				
			}else if(FiseConstants.NOMBRE_FORMATO_13A.equals(a.getFormato())){ 
				jsonArray = new JSONArray();
				FiseFormato13ACPK pk = new FiseFormato13ACPK();
				pk.setCodEmpresa(a.getCodEmpresa());
				pk.setAnoPresentacion(new Long(a.getAnioPres()));
				pk.setMesPresentacion(new Long(a.getMesPres()));
				pk.setEtapa(a.getEtapa());  	 
				FiseFormato13AC formato13A = formatoService13A.obtenerFormato13ACByPK(pk);
				if(formato13A.getFiseFormato13ADs()!=null){
					cargarListaObservaciones13A(formato13A.getFiseFormato13ADs());
				}else{
					listaObs13A = new ArrayList<MensajeErrorBean>();	
				}				
				for (MensajeErrorBean error : listaObs13A) {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("id", error.getId());
					jsonObj.put("descZonaBenef", error.getDescZonaBenef());
					jsonObj.put("codigo", error.getCodigo());
					jsonObj.put("descripcion", error.getDescripcion());
					jsonObj.put("descSectorTipico", error.getDescCodSectorTipico());					
					jsonArray.put(jsonObj);
				}

			}else if(FiseConstants.NOMBRE_FORMATO_14A.equals(a.getFormato())){ 
				jsonArray = new JSONArray();
				FiseFormato14ACPK pk = new FiseFormato14ACPK();
				pk.setCodEmpresa(a.getCodEmpresa());
				pk.setAnoPresentacion(new Long(a.getAnioPres()));
				pk.setMesPresentacion(new Long(a.getMesPres()));
				pk.setAnoInicioVigencia(new Long(a.getAnioIniVig()));
				pk.setAnoFinVigencia(new Long(a.getAnioFinVig()));
				pk.setEtapa(a.getEtapa());  				        
				FiseFormato14AC formato14A = formatoService14A.obtenerFormato14ACByPK(pk);
				if(formato14A.getFiseFormato14ADs()!=null){
					cargarListaObservaciones14A(formato14A.getFiseFormato14ADs());	
				}else{
					listaObs14A = new ArrayList<MensajeErrorBean>();
				}				
				for (MensajeErrorBean error : listaObs14A) {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("id", error.getId());	
					jsonObj.put("descZonaBenef", error.getDescZonaBenef());
					jsonObj.put("codigo", error.getCodigo());			
					jsonObj.put("descripcion", error.getDescripcion());					
					jsonArray.put(jsonObj);		
				}

			}else if(FiseConstants.NOMBRE_FORMATO_14B.equals(a.getFormato())){
				jsonArray = new JSONArray();
				FiseFormato14BCPK pk = new FiseFormato14BCPK();
				pk.setCodEmpresa(a.getCodEmpresa());
				pk.setAnoPresentacion(new Long(a.getAnioPres()));
				pk.setMesPresentacion(new Long(a.getMesPres()));
				pk.setAnoInicioVigencia(new Long(a.getAnioIniVig()));
				pk.setAnoFinVigencia(new Long(a.getAnioFinVig()));
				pk.setEtapa(a.getEtapa());  						        
				FiseFormato14BC formato14B = formatoService14B.obtenerFormato14BCByPK(pk);
				if(formato14B.getFiseFormato14BDs()!=null){
					cargarListaObservaciones14B(formato14B.getFiseFormato14BDs());	
				}else{
					listaObs14B = new ArrayList<MensajeErrorBean>();
				}					
				for (MensajeErrorBean error : listaObs14B) {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("id", error.getId());	
					jsonObj.put("descZonaBenef", error.getDescZonaBenef());
					jsonObj.put("codigo", error.getCodigo());			
					jsonObj.put("descripcion", error.getDescripcion());					
					jsonArray.put(jsonObj);		
				}

			}else if(FiseConstants.NOMBRE_FORMATO_14C.equals(a.getFormato())){
				jsonArray = new JSONArray();
				Formato14CBean f14C = new Formato14CBean();  					
				f14C.setCodEmpresa(a.getCodEmpresa());
				f14C.setAnioPres(a.getAnioPres());
				f14C.setMesPres(a.getMesPres());
				f14C.setAnoIniVigencia(a.getAnioIniVig());
				f14C.setAnoFinVigencia(a.getAnioFinVig());
				f14C.setEtapa(a.getEtapa());				
				FiseFormato14CC formato14C = formatoService14C.obtenerFiseFormato14CC(f14C);
				if(formato14C.getListaDetalle14cDs()!=null){
					cargarListaObservaciones14C(formato14C.getListaDetalle14cDs());
				}else{
					listaObs14C = new ArrayList<MensajeErrorBean>();	
				}				
				for (MensajeErrorBean error : listaObs14C) {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("id", error.getId());	
					jsonObj.put("descZonaBenef", error.getDescZonaBenef());
					jsonObj.put("codigo", error.getCodigo());			
					jsonObj.put("descripcion", error.getDescripcion());					
					jsonArray.put(jsonObj);		
				}		    
			}	   
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();		  
			logger.info(jsonArray.toString());
			pw.write(jsonArray.toString());
			pw.flush();
			pw.close();	    
		 } catch (Exception e) {
			logger.error("Error al ver observaciones del formato: "+e); 
			e.printStackTrace();
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
		}
    }	
	
	
	@ResourceMapping("verFormatosReporte")
	public void verFormatos(ModelMap model, ResourceRequest request,ResourceResponse response,
			@ModelAttribute("archivoSustentoBean")ArchivoSustentoBean a) {		
		try {	
			
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);	
			
			logger.info("Entrando a ver reporte de cada formato"); 			
			logger.info("Codigo empresa:  "+ a.getCodEmpresa()); 
			logger.info("anioPres:  "+ a.getAnioPres());	
			logger.info("mespres:  "+ a.getMesPres());	
			logger.info("etapa:  "+ a.getEtapa());	
			logger.info("anioEjec:  "+ a.getAnioEjec());	
			logger.info("mesEjec:  "+ a.getMesEjec());	
			logger.info("anioIniVig:  "+ a.getAnioIniVig());	
			logger.info("anioFinVig:  "+ a.getAnioFinVig());	
			logger.info("formato:  "+ a.getFormato());				
			
			String usuario = themeDisplay.getUser().getLogin();
		    String terminal = themeDisplay.getUser().getLoginIP();
		    String email =  themeDisplay.getUser().getEmailAddress(); 
		    Map<String, Object> mapa =null;	
		    String directorio =  "";	
		    String nombreReporte = "";
		    boolean valorReporte = false; 
		    String rutaImg = session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg");
		   
		    JSONObject jsonObj = new JSONObject();	   
		    
		    String tipoFormato = "FORMATO "+a.getFormato();
		    String tipoArchivo = "3";//PDF		   
		    session.setAttribute("tipoFormato",tipoFormato);
		    session.setAttribute("tipoArchivo",tipoArchivo);		
		
			if(FiseConstants.NOMBRE_FORMATO_12A.equals(a.getFormato())){ 
				nombreReporte = "formato12A";	  	  			       
				directorio =  "/reports/"+nombreReporte+".jasper";
				mapa = parametros12A(a.getCodEmpresa(), a.getAnioPres(), a.getMesPres(), 
						a.getAnioEjec(), a.getMesEjec(), a.getEtapa(), rutaImg, usuario, terminal, email);
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

			}else if(FiseConstants.NOMBRE_FORMATO_12B.equals(a.getFormato())){ 
				nombreReporte = "formato12B";		 		      
				directorio =  "/reports/"+nombreReporte+".jasper";
				mapa = parametros12B(a.getCodEmpresa(), a.getAnioPres(), a.getMesPres(), 
						a.getAnioEjec(), a.getMesEjec(), a.getEtapa(), rutaImg, usuario, terminal, email);
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

			}else if(FiseConstants.NOMBRE_FORMATO_12C.equals(a.getFormato())){
				nombreReporte = "formato12C";		  	  		    	
				directorio = "/reports/" + nombreReporte + ".jasper";
				mapa = parametros12C(a.getCodEmpresa(), a.getAnioPres(), a.getMesPres(), 
						a.getAnioEjec(), a.getMesEjec(), a.getEtapa(), rutaImg, usuario, terminal, email);	
				FiseFormato12CCPK pk = new FiseFormato12CCPK();
				pk.setCodEmpresa(a.getCodEmpresa());
				pk.setAnoPresentacion(new Long(a.getAnioPres()));
				pk.setMesPresentacion(new Long(a.getMesPres()));
				pk.setEtapa(a.getEtapa());
				FiseFormato12CC formato = formatoService12C.obtenerFormato12CCByPK(pk);	
				if(mapa!=null && formato!=null){				
					File reportFile = new File(session.getServletContext().getRealPath(directorio));
					byte[] bytes12C = null;
					if(formato.getFiseFormato12CDs()!=null){
						bytes12C = JasperRunManager.runReportToPdf(reportFile.getPath(), mapa, 
								new JRBeanCollectionDataSource(formato.getFiseFormato12CDs()));	
					}else{
						bytes12C = JasperRunManager.runReportToPdf(reportFile.getPath(), mapa, 
								new JREmptyDataSource());
					}					
					if (bytes12C != null) {				  	  		    		
						session.setAttribute("bytesFormato", bytes12C);
						valorReporte =true;
					}
				}

			}else if(FiseConstants.NOMBRE_FORMATO_12D.equals(a.getFormato())){ 
				nombreReporte = "formato12D";  	  		    	
				directorio = "/reports/" + nombreReporte + ".jasper";
				mapa = parametros12D(a.getCodEmpresa(), a.getAnioPres(), a.getMesPres(), 
						a.getAnioEjec(), a.getMesEjec(), a.getEtapa(), rutaImg, usuario, terminal, email);	
				FiseFormato12DCPK pk = new FiseFormato12DCPK();
				pk.setCodEmpresa(a.getCodEmpresa());
				pk.setAnoPresentacion(new Long(a.getAnioPres()));
				pk.setMesPresentacion(new Long(a.getMesPres()));
				pk.setEtapa(a.getEtapa());
				FiseFormato12DC formato = formatoService12D.obtenerFormato12DCByPK(pk);	
				if(mapa!=null && formato!=null){				
					File reportFile = new File(session.getServletContext().getRealPath(directorio));
					byte[] bytes12D = null;
					if(formato.getFiseFormato12DDs()!=null){
						bytes12D = JasperRunManager.runReportToPdf(reportFile.getPath(), mapa, 
								new JRBeanCollectionDataSource(formato.getFiseFormato12DDs()));	
					}else{
						bytes12D = JasperRunManager.runReportToPdf(reportFile.getPath(), mapa, 
								new JREmptyDataSource());
					}					
					if (bytes12D != null) {				  	  		    		
						session.setAttribute("bytesFormato", bytes12D);
						valorReporte =true;
					}
				}

			}else if(FiseConstants.NOMBRE_FORMATO_13A.equals(a.getFormato())){ 
				nombreReporte = "formato13A";	       				
				directorio = "/reports/" + nombreReporte + ".jasper";
				mapa = parametros13A(a.getCodEmpresa(), a.getAnioPres(), a.getMesPres(), a.getEtapa(), 
						rutaImg, usuario, terminal, email); 				
				if(mapa!=null){					
					File reportFile13A = new File(session.getServletContext().getRealPath(directorio));
					byte[] bytes13A = null;
					if(listaZonas13A!=null && listaZonas13A.size()>0){
						bytes13A = JasperRunManager.runReportToPdf(reportFile13A.getPath(), mapa, 
								new JRBeanCollectionDataSource(listaZonas13A));
					}else{						
						bytes13A = JasperRunManager.runReportToPdf(reportFile13A.getPath(), mapa, 
								new JREmptyDataSource());	
					}						       				
					if (bytes13A != null) {	        					
						session.setAttribute("bytesFormato", bytes13A);
						valorReporte =true;
					}
				}

			}else if(FiseConstants.NOMBRE_FORMATO_14A.equals(a.getFormato())){ 
				nombreReporte = "formato14A";	       				
				directorio = "/reports/" + nombreReporte + ".jasper";
				mapa = parametros14A(a.getCodEmpresa(), a.getAnioPres(), a.getMesPres(),  
						a.getAnioIniVig(), a.getAnioFinVig(),a.getEtapa(),rutaImg, usuario, terminal, email);            	 
				if(mapa!=null){            	    	
					File reportFile14A = new File(session.getServletContext().getRealPath(directorio));
					byte[] bytes14A = null;	       				  				
					bytes14A = JasperRunManager.runReportToPdf(reportFile14A.getPath(), mapa, new JREmptyDataSource());	       				
					if (bytes14A != null) {	        					
						session.setAttribute("bytesFormato", bytes14A);
						valorReporte =true;
					}
				}
            	    
			}else if(FiseConstants.NOMBRE_FORMATO_14B.equals(a.getFormato())){
				nombreReporte = "formato14B";	       				
				directorio = "/reports/" + nombreReporte + ".jasper";
				mapa = parametros14B(a.getCodEmpresa(), a.getAnioPres(), a.getMesPres(),  
						a.getAnioIniVig(), a.getAnioFinVig(),a.getEtapa(),rutaImg, usuario, terminal, email);
				if(mapa!=null){            		 
					File reportFile14B = new File(session.getServletContext().getRealPath(directorio));
					byte[] bytes14B = null;	       				  				
					bytes14B = JasperRunManager.runReportToPdf(reportFile14B.getPath(), mapa, new JREmptyDataSource());	       				
					if (bytes14B != null) {	        					
						session.setAttribute("bytesFormato", bytes14B);
						valorReporte =true;
					}
				}

			}else if(FiseConstants.NOMBRE_FORMATO_14C.equals(a.getFormato())){ 
				nombreReporte = "formato14C";	       				
				directorio = "/reports/" + nombreReporte + ".jasper";
				mapa = parametros14C(a.getCodEmpresa(), a.getAnioPres(), a.getMesPres(),  
						a.getAnioIniVig(), a.getAnioFinVig(),a.getEtapa(),rutaImg, usuario, terminal, email);           	 
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
				obs.setDescripcion(observacion.getFiseObservacion().getDescripcion());
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
				obs.setDescripcion(observacion.getFiseObservacion().getDescripcion());
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
				obs.setDescripcion(observacion.getFiseObservacion().getDescripcion());
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
				obs.setDescripcion(observacion.getFiseObservacion().getDescripcion());
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
				obs.setDescripcion(observacion.getFiseObservacion().getDescripcion());
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
				obs.setDescripcion(observacion.getFiseObservacion().getDescripcion());
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
				obs.setDescripcion(o.getFiseObservacion().getDescripcion());
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
	        		mapa.put("ANO_INICIO_VIGENCIA",formato.getAnoInicioVigenciaDetalle());
					mapa.put("ANO_FIN_VIGENCIA",formato.getAnoFinVigenciaDetalle());
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
	
	
	
	
	
}
