package gob.osinergmin.fise.gart.controller;



import gob.osinergmin.fise.bean.Formato14ACBean;
import gob.osinergmin.fise.bean.MensajeErrorBean;
import gob.osinergmin.fise.bean.Formato13ADReportBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.dao.FiseZonaBenefDao;
import gob.osinergmin.fise.domain.AdmUbigeo;
import gob.osinergmin.fise.domain.FiseFormato13AC;
import gob.osinergmin.fise.domain.FiseFormato13ACPK;
import gob.osinergmin.fise.domain.FiseFormato13AD;
import gob.osinergmin.fise.domain.FisePeriodoEnvio;
import gob.osinergmin.fise.gart.command.Formato13AGartCommand;
import gob.osinergmin.fise.gart.json.Formato13AGartJSON;
import gob.osinergmin.fise.gart.service.FisePeriodoEnvioGartService;
import gob.osinergmin.fise.gart.service.FiseZonaBenefGartService;
import gob.osinergmin.fise.gart.service.Formato13AGartService;
import gob.osinergmin.fise.gart.xls.FormatoExcelImport;
import gob.osinergmin.fise.util.FormatoUtil;
import gob.osinergmin.fise.xls.XlsTableConfig;
import gob.osinergmin.fise.xls.XlsWorkbookConfig;
import gob.osinergmin.fise.xls.XlsWorksheetConfig;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

@SessionAttributes({"esAdministrador"})
@Controller("formato13AGartController")
@RequestMapping("VIEW")
public class Formato13AGartController {

	private static final Log logger=LogFactoryUtil.getLog(Formato13AGartController.class);
	private static final String CRUD_CREATE="CREATE";
	private static final String CRUD_UPDATE="UPDATE";
	private static final String CRUD_DELETE="DELETE";
	private static final String CRUD_READ="READ";
	
	@Autowired
	@Qualifier("fiseUtil")
	private FiseUtil fiseUtil;

	@Autowired
	@Qualifier("formato13AGartServiceImpl")
	private Formato13AGartService formatoService;
	
	@Autowired
	@Qualifier("fisePeriodoEnvioGartServiceImpl")
	FisePeriodoEnvioGartService periodoService;
	
	Map<String, String> mapaErrores;
	
	@RequestMapping
	public String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse,@ModelAttribute("formato13AGartCommand")Formato13AGartCommand command){


		command.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));
		command.setListaMes(fiseUtil.getMapaMeses());
		command.setAnioInicio(fiseUtil.obtenerNroAnioFechaActual());
		command.setMesInicio( String.valueOf(Integer.parseInt(fiseUtil.obtenerNroMesFechaActual())-1));
		command.setAnioFin(fiseUtil.obtenerNroAnioFechaActual());
		command.setMesFin(fiseUtil.obtenerNroMesFechaActual());

		model.addAttribute("esAdministrador", fiseUtil.esAdministrador(renderRequest));
		
		logger.info("admin1.1:"+model.get("esAdministrador"));
		
		return "formato13AInicio";
	}
	
	@ResourceMapping("busqueda")
  	public void grid(ModelMap model,ResourceRequest request,ResourceResponse response,@ModelAttribute("formato13AGartCommand")Formato13AGartCommand command){
		
		try {
			response.setContentType("application/json");	
			logger.info("admin2.1:"+model.get("esAdministrador"));
			HttpServletRequest req = PortalUtil.getHttpServletRequest(request);	        
	        HttpSession session = req.getSession();
	        List<FiseFormato13AC> listaFormato;	
		    JSONArray jsonArray = new JSONArray();
		    Map<String, String> mapaEmpresa = fiseUtil.getMapaEmpresa();
		    Map<Long,String> listaMes=fiseUtil.getMapaMeses();
		    
				String codEmpresa = command.getCodEmpresa();
				String anioDesde = command.getAnioInicio();
				String mesDesde = command.getMesInicio();
				String anioHasta = command.getAnioFin();
				String mesHasta = command.getMesFin();
				String etapa = command.getEtapa();
				logger.info("valores "+ codEmpresa);
	  			logger.info("valores "+ anioDesde);
	  			logger.info("valores "+ mesDesde);
	  			logger.info("valores "+ anioHasta);
	  			logger.info("valores "+ mesHasta);
	  			logger.info("valores "+ etapa);
	  			
	  			listaFormato = formatoService.buscarFormato13AC(
	  					codEmpresa!=""?FormatoUtil.rellenaDerecha(codEmpresa, ' ', 4):"", 
	  					anioDesde!=""?Long.parseLong(anioDesde):0, 
	  					mesDesde!=""?Long.parseLong(mesDesde):0, 
	  					anioHasta!=""?Long.parseLong(anioHasta):0, 
	  					mesHasta!=""?Long.parseLong(mesHasta):0, 
	  					etapa);
	  			
	  			logger.info("arreglo lista:"+listaFormato);
	  			for(FiseFormato13AC fiseFormato13AC : listaFormato){
	  				//seteamos la descripcion de la empresa
	  				logger.info("empresa "+mapaEmpresa.get(fiseFormato13AC.getId().getCodEmpresa()));
	  				fiseFormato13AC.setDescEmpresa(mapaEmpresa.get(fiseFormato13AC.getId().getCodEmpresa()));
	  				fiseFormato13AC.setDescMesPresentacion(listaMes.get(fiseFormato13AC.getId().getMesPresentacion()));
					jsonArray.put(new Formato13AGartJSON().asJSONObject(fiseFormato13AC));
					
	  			}
	  			
	  		//************************************************************************
				//Generamos la configuración de la exportación a Excel
				//************************************************************************
	  			XlsWorkbookConfig xlsWorkbookConfig = new XlsWorkbookConfig();
				xlsWorkbookConfig.setName(FiseConstants.NOMBRE_EXCEL_FORMATO13A);
				List<XlsTableConfig> tables = new LinkedList<XlsTableConfig>();
				tables.add(new XlsTableConfig(listaFormato,FiseConstants.TIPO_FORMATO_13A));
				List<XlsWorksheetConfig> sheets = new LinkedList<XlsWorksheetConfig>();
				sheets.add(new XlsWorksheetConfig(FiseConstants.NOMBRE_HOJA_FORMATO13A,tables));
				xlsWorkbookConfig.setSheets(sheets);
				session.setAttribute(FiseConstants.KEY_CFG_EXCEL_EXPORT,xlsWorkbookConfig);	
			    
	  			logger.info("arreglo json:"+jsonArray);
	  			PrintWriter pw = response.getWriter();
	  			pw.write(jsonArray.toString());
	  			pw.flush();
	  			pw.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@RequestMapping(params="action=nuevo")
	public String nuevoFormato(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse,
			@RequestParam(value = "crud", required = false,defaultValue="CREATE")String crud,@ModelAttribute("formato13AGartCommand")Formato13AGartCommand command){
		 System.out.println("aqui en nuevoFormato");
		 System.out.println("CRUD:"+crud);
		command.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));
		model.addAttribute("crud", crud);
		model.addAttribute("readonly", "false");
		
		if(CRUD_READ.equals(crud)){
			model.addAttribute("readonly", "true");
		}
		
		mapaErrores = fiseUtil.getMapaErrores();
		
		
		return "formato13ACRUD";
	}
	
	@ActionMapping(params="action=guardarDetalle")
	public void guardarDetalleFormato(ModelMap model,ActionRequest request,ActionResponse response,@ModelAttribute("formato13AGartCommand")Formato13AGartCommand command){
		String codEmpresa = command.getCodEmpresa();
		String periodoDeclaracion=command.getPeridoDeclaracion();
		String anioAlta=command.getAnioAlta();
		String mesAlta=command.getMesAlta();
		String codUbigeo=command.getCodDistrito();
		String localidad=command.getLocalidad();
		String st1=command.getSt1();
		String st2=command.getSt2();
		String st3=command.getSt3();
		String st4=command.getSt4();
		String st5=command.getSt5();
		String st6=command.getSt6();
		String stserv=command.getStser();
		String stesp=command.getStesp();
		String idZonaBenef=command.getIdZonaBenef();
		String sedeAtencion=command.getNombreSede();
		
		logger.info("valores detalle "+ codEmpresa);
		logger.info("valores detalle"+ periodoDeclaracion);
		logger.info("valores anioAlta"+ anioAlta);
		logger.info("valores mesAlta"+ mesAlta);
		logger.info("valores codUbigeo"+ codUbigeo);
		logger.info("valores localidad"+ localidad);
		logger.info("valores st1"+ st1);
		logger.info("valores st2"+ st2);
		logger.info("valores st3"+ st3);
		logger.info("valores st4"+ st4);
		logger.info("valores st5"+ st5);
		logger.info("valores st6"+ st6);
		logger.info("valores stserv"+ stserv);
		logger.info("valores stesp"+ stesp);
		logger.info("valores idZonaBenef"+ idZonaBenef);
		logger.info("valores sedeAtencion"+ sedeAtencion);
		
		response.setRenderParameter("crud", "VIEW");
		response.setRenderParameter("action", "detalle");
		response.setRenderParameter("codEmpresa",codEmpresa);
		response.setRenderParameter("periodoDeclaracion",periodoDeclaracion);
	}
	
	@ActionMapping(params="action=nuevoDetalle")
	public void nuevoDetalleFormato(ModelMap model,ActionRequest request,ActionResponse response,@ModelAttribute("formato13AGartCommand")Formato13AGartCommand command){
		String codEmpresa = command.getCodEmpresa();
		String periodoDeclaracion=command.getPeridoDeclaracion();
		String anioPresentacion="";
		String mesPresentacion="";
		String etapa="";
		logger.info("valores detalle "+ codEmpresa);
		logger.info("valores detalle"+ periodoDeclaracion);
		
		if(periodoDeclaracion!=null && periodoDeclaracion.length()>6){
			anioPresentacion=periodoDeclaracion.substring(0,4);
			mesPresentacion=periodoDeclaracion.substring(4,6);
			etapa=periodoDeclaracion.substring(6);
		}
		logger.info("Cabecera codEmpresa:"+codEmpresa);
		logger.info("Cabecera anioPresentacion:"+anioPresentacion);
		logger.info("Cabecera mesPresentacion:"+mesPresentacion);
		logger.info("Cabecera etapa:"+etapa);
		
		//Registramos la cabecera 
		FiseFormato13ACPK pkCabecera=new FiseFormato13ACPK();
		pkCabecera.setCodEmpresa(codEmpresa!=""?FormatoUtil.rellenaDerecha(codEmpresa, ' ', 4):"");
		pkCabecera.setAnoPresentacion(Long.parseLong(anioPresentacion));
		pkCabecera.setMesPresentacion(Long.parseLong(mesPresentacion));
		pkCabecera.setEtapa(etapa);
		
		FiseFormato13AC cabecera=new FiseFormato13AC();
		cabecera.setId(pkCabecera);
		
		//Primero buscamos si existe una cabecera
		FiseFormato13AC cab=formatoService.obtenerFormato13ACByPK(pkCabecera);
		
		if(cab!=null){
			//update
			
		}else{
			//create
		}
		
		response.setRenderParameter("crud", CRUD_CREATE);
		response.setRenderParameter("action", "detalle");
		response.setRenderParameter("codEmpresa",codEmpresa);
		response.setRenderParameter("periodoDeclaracion",periodoDeclaracion);
	}
	
	@RequestMapping(params="action=detalle")
	public String detalle(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse,
			@RequestParam("crud")String crud,@RequestParam("codEmpresa")String codEmpresa,
			@RequestParam("periodoDeclaracion")String periodoDeclaracion,@ModelAttribute("formato13AGartCommand")Formato13AGartCommand command){
		
		logger.info("valores requestparameter"+ crud);
		logger.info("valores periodoDeclaracion"+ periodoDeclaracion);
		logger.info("valores codEmpresa"+ codEmpresa);

		String anioPresentacion="";
		String mesPresentacion="";
		String etapa="";

		if(periodoDeclaracion!=null && periodoDeclaracion.length()>6){
			anioPresentacion=periodoDeclaracion.substring(0,4);
			mesPresentacion=periodoDeclaracion.substring(4,6);
			etapa=periodoDeclaracion.substring(6);
		}
		//Cabecera	
		Map<String, String> mapaEmpresa = fiseUtil.getMapaEmpresa();
		command.setCodEmpresa(codEmpresa);
		command.setPeridoDeclaracion(periodoDeclaracion);
		command.setDescEmpresa(mapaEmpresa.get(codEmpresa));
		command.setAnioPresentacion(anioPresentacion);
		command.setMesPresentacion(mesPresentacion);
		command.setEtapa(etapa);
		
		//
		command.setListaMes(fiseUtil.getMapaMeses());
		command.setListaZonasBenef(fiseUtil.getMapaZonaBenef());
		command.setListaDepartamentos(fiseUtil.listaDepartamentos());
		model.addAttribute("readonly", "false");
		
		if(CRUD_READ.equals(crud)){
			//Es lectura
			model.addAttribute("readonly", "true");
			logger.info("LECTURA DETALLE");
			FiseFormato13AC cabecera=new FiseFormato13AC();
			cabecera.setId(new FiseFormato13ACPK());
			cabecera.getId().setCodEmpresa(codEmpresa!=""?FormatoUtil.rellenaDerecha(codEmpresa, ' ', 4):"");
			cabecera.getId().setAnoPresentacion(anioPresentacion!=""?Long.parseLong(anioPresentacion):0);
			cabecera.getId().setMesPresentacion(mesPresentacion!=""?Long.parseLong(mesPresentacion):0);
			cabecera.getId().setEtapa(etapa);
  			
			List<Formato13ADReportBean>  detalle = formatoService.listarLocalidadesPorZonasBenefFormato13ADByFormato13AC(cabecera);
  			
  			logger.info("arreglo lista:"+detalle.size());
  			for(Formato13ADReportBean fiseFormato13AD : detalle){
  				//seteamos la descripcion de la empresa
  				command.setAnioAlta(String.valueOf(fiseFormato13AD.getAnioAlta()));
  				command.setMesAlta(String.valueOf(fiseFormato13AD.getMesAlta()));
  				String ubigeo=fiseFormato13AD.getCodUbigeo();
  				if(StringUtils.isNotBlank(ubigeo)){
  					command.setCodDepartamento(ubigeo.substring(0, 2).concat("0000")); 
  					command.setCodProvincia(ubigeo.substring(0, 4).concat("00"));
  					command.setCodDistrito(ubigeo);
  				}
  				
  				command.setLocalidad(fiseFormato13AD.getDescLocalidad());
  				command.setSt1(String.valueOf(fiseFormato13AD.getNroBenefPoteSecTipico1()));
  				command.setSt2(String.valueOf(fiseFormato13AD.getNroBenefPoteSecTipico2()));
  				command.setSt3(String.valueOf(fiseFormato13AD.getNroBenefPoteSecTipico3()));
  				command.setSt4(String.valueOf(fiseFormato13AD.getNroBenefPoteSecTipico4()));
  				command.setSt5(String.valueOf(fiseFormato13AD.getNroBenefPoteSecTipico5()));
  				command.setSt6(String.valueOf(fiseFormato13AD.getNroBenefPoteSecTipico6()));
  				command.setStser(String.valueOf(fiseFormato13AD.getNroBenefPoteSecTipico7()));
  				command.setStesp(String.valueOf(fiseFormato13AD.getNroBenefPoteSecTipico8()));
  				long total=fiseFormato13AD.getNroBenefPoteSecTipico1()+fiseFormato13AD.getNroBenefPoteSecTipico2()+
  						fiseFormato13AD.getNroBenefPoteSecTipico3()+fiseFormato13AD.getNroBenefPoteSecTipico4()+
  						fiseFormato13AD.getNroBenefPoteSecTipico5()+fiseFormato13AD.getNroBenefPoteSecTipico6()+
  						fiseFormato13AD.getNroBenefPoteSecTipico7()+fiseFormato13AD.getNroBenefPoteSecTipico8();
  				command.setTotal(String.valueOf(total));
  				command.setIdZonaBenef(String.valueOf(fiseFormato13AD.getIdZonaBenef()));
  				command.setNombreSede(fiseFormato13AD.getNombreSedeAtiende());
  			}
		}
		
		model.addAttribute("crud",crud);

		
		return "formato13ACRUDDetalle";
	}
	
	@ResourceMapping("cargaPeriodoDeclaracion")
  	public void cargaPeriodoDeclaracion(ModelMap model, SessionStatus status, ResourceRequest request,ResourceResponse response,
  			@RequestParam("codEmpresa")String codEmpresa){
		try {			
  			logger.info("cargaPeriodoDeclaracion");
  			logger.debug("-->cargaPeriodoDeclaracion");
			response.setContentType("applicacion/json");
  			
  			List<FisePeriodoEnvio> listaPeriodoEnvio = periodoService.listarFisePeriodoEnvioMesAnioEtapa(codEmpresa, "F13A"/*FiseConstants.NOMBRE_FORMATO_13A*/);
  			
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
  		    pw.write(jsonArray.toString());
  		    pw.flush();
  		    pw.close();							
  		}catch (Exception e) {
  			// TODO: handle exception
  			e.printStackTrace();
  		}
	}
	
	@ResourceMapping("provincias")
  	public void cargarProvincias(ModelMap model, SessionStatus status, ResourceRequest request,ResourceResponse response,
  			@RequestParam("codDepartamento")String codDepartamento){
		try {			
  			logger.info("cargarProvincias");
  			logger.debug("-->cargarProvincias");
			response.setContentType("applicacion/json");
			if (codDepartamento!= null && !codDepartamento.equals("")){
				codDepartamento = codDepartamento.substring(0, 2);	
			}
  			List<AdmUbigeo> provincias=fiseUtil.listaProvincias(codDepartamento);
  			
  			JSONArray jsonArray = new JSONArray();
  			
			
			//Temp
			JSONObject seleccioneItem = new JSONObject();
  			seleccioneItem.put("codigoItem", "");				
  			seleccioneItem.put("descripcionItem","--Seleccione--");			
			jsonArray.put(seleccioneItem);
			
			
  			for (AdmUbigeo provincia : provincias) {
  				JSONObject jsonObj = new JSONObject();
				jsonObj.put("codigoItem", provincia.getCodUbigeo());				
				jsonObj.put("descripcionItem",provincia.getNomUbigeo());			
				jsonArray.put(jsonObj);		
			}
  			
  		    PrintWriter pw = response.getWriter();
  		    pw.write(jsonArray.toString());
  		    pw.flush();
  		    pw.close();							
  		}catch (Exception e) {
  			// TODO: handle exception
  			e.printStackTrace();
  		}
	}
	
	@ResourceMapping("distritos")
  	public void cargaDistritos(ModelMap model, SessionStatus status, ResourceRequest request,ResourceResponse response,
  			@RequestParam("codProvincia")String codProvincia){
		try {			
  			logger.info("cargaDistritos");
  			logger.debug("-->cargaDistritos");
			response.setContentType("applicacion/json");
			if (codProvincia != null && !codProvincia.equals("")){
				codProvincia = codProvincia.substring(0, 4);
			}
					
			List<AdmUbigeo> distritos=fiseUtil.listaDistritos(codProvincia);
			JSONArray jsonArray = new JSONArray();
			
			
			//Temp
			JSONObject seleccioneItem = new JSONObject();
  			seleccioneItem.put("codigoItem", "");				
  			seleccioneItem.put("descripcionItem","--Seleccione--");			
			jsonArray.put(seleccioneItem);
			
			
  			for (AdmUbigeo distrito : distritos) {
  				JSONObject jsonObj = new JSONObject();
  				jsonObj.put("codigoItem", distrito.getCodUbigeo());				
				jsonObj.put("descripcionItem",distrito.getNomUbigeo());		
				//agregar los valores
				jsonArray.put(jsonObj);		
			}
  			
  		    PrintWriter pw = response.getWriter();
  		    pw.write(jsonArray.toString());
  		    pw.flush();
  		    pw.close();							
  		}catch (Exception e) {
  			// TODO: handle exception
  			e.printStackTrace();
  		}
	}
	
	@ResourceMapping("busquedaDetalle")
  	public void gridDetalle(ModelMap model,ResourceRequest request,ResourceResponse response,@ModelAttribute("formato13AGartCommand")Formato13AGartCommand command){
		
		try {
			response.setContentType("application/json");	
			logger.info("admin2.1:"+model.get("esAdministrador"));
			HttpServletRequest req = PortalUtil.getHttpServletRequest(request);	        
	        HttpSession session = req.getSession();
	        List<Formato13ADReportBean> listaFormato;	
		    JSONArray jsonArray = new JSONArray();
		    Map<String, String> mapaEmpresa = fiseUtil.getMapaEmpresa();
		    
				String codEmpresa = command.getCodEmpresa();
				String periodoDeclaracion=command.getPeridoDeclaracion();
				String anioPresentacion="";
				String mesPresentacion="";
				String etapa="";
				logger.info("valores "+ codEmpresa);
	  			logger.info("valores "+ periodoDeclaracion);

	  			
	  			if(periodoDeclaracion!=null && periodoDeclaracion.length()>6){
	  				int maximo=periodoDeclaracion.length();
	  				//"hamburger".substring(4, 8) returns "urge"
	  				 //"smiles".substring(1, 5) returns "mile"

	  				anioPresentacion=periodoDeclaracion.substring(0,4);
	  				mesPresentacion=periodoDeclaracion.substring(4,6);
	  				
	  				 //"unhappy".substring(2) returns "happy"
	  				 //"Harbison".substring(3) returns "bison"
	  				 //"emptiness".substring(9) returns "" (an empty string)

	  				etapa=periodoDeclaracion.substring(6);
	  			}
	  			FiseFormato13AC formato13AC=new FiseFormato13AC();
	  			formato13AC.setId(new FiseFormato13ACPK());
	  			formato13AC.getId().setCodEmpresa(codEmpresa!=""?FormatoUtil.rellenaDerecha(codEmpresa, ' ', 4):"");
	  			formato13AC.getId().setAnoPresentacion(anioPresentacion!=""?Long.parseLong(anioPresentacion):0);
	  			formato13AC.getId().setMesPresentacion(mesPresentacion!=""?Long.parseLong(mesPresentacion):0);
	  			formato13AC.getId().setEtapa(etapa);
	  			
	  			if(anioPresentacion.equalsIgnoreCase("")|| mesPresentacion.equalsIgnoreCase("")|| etapa.equalsIgnoreCase("")){
	  				throw new Exception("Error al buscar detalle: Empresa no seleccionada");
	  			}
	  			
	  			listaFormato = formatoService.listarLocalidadesPorZonasBenefFormato13ADByFormato13AC(formato13AC);
	  			
	  			logger.info("arreglo lista:"+listaFormato);
	  			for(Formato13ADReportBean fiseFormato13AD : listaFormato){
	  				//seteamos la descripcion de la empresa
	  				logger.info("empresa "+mapaEmpresa.get(formato13AC.getId().getCodEmpresa()));
					jsonArray.put(new Formato13AGartJSON().asJSONObject(fiseFormato13AD,formato13AC));
					
	  			}
	  			
	  		//************************************************************************
				//Generamos la configuración de la exportación a Excel
				//************************************************************************
	  			XlsWorkbookConfig xlsWorkbookConfig = new XlsWorkbookConfig();
				xlsWorkbookConfig.setName(FiseConstants.NOMBRE_EXCEL_FORMATO13A);
				List<XlsTableConfig> tables = new LinkedList<XlsTableConfig>();
				tables.add(new XlsTableConfig(listaFormato,FiseConstants.TIPO_FORMATO_13A));
				List<XlsWorksheetConfig> sheets = new LinkedList<XlsWorksheetConfig>();
				sheets.add(new XlsWorksheetConfig(FiseConstants.NOMBRE_HOJA_FORMATO13A,tables));
				xlsWorkbookConfig.setSheets(sheets);
				session.setAttribute(FiseConstants.KEY_CFG_EXCEL_EXPORT,xlsWorkbookConfig);	
			    
	  			logger.info("arreglo json:"+jsonArray);
	  			PrintWriter pw = response.getWriter();
	  			pw.write(jsonArray.toString());
	  			pw.flush();
	  			pw.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}


	
	@RequestMapping(params="action=uploadFile")
	public void cargarDocumento(ActionRequest request,ActionResponse response,@ModelAttribute("formato13AGartCommand")Formato13AGartCommand command){
	  System.out.println("aqui en upload controller");
	  Formato13AGartCommand formatoMensaje = new Formato13AGartCommand();
		
	  ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(request);
	//	String codEmpresaNew = uploadPortletRequest.getParameter("codigoEmpresa");
	  
		FileEntry fileEntry=null;
		System.out.println("request::"+request+"/"+uploadPortletRequest);
		fileEntry=fiseUtil.subirDocumento(request, uploadPortletRequest, FiseConstants.TIPOARCHIVO_XLS);
		formatoMensaje = readExcelFile(fileEntry, themeDisplay);
		
		
	
		
	
		
	}

	public Formato13AGartCommand readExcelFile(FileEntry archivo, ThemeDisplay themeDisplay) {
	
		Formato13AGartCommand formatoMensaje = new Formato13AGartCommand();
		InputStream is=null;
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
			
			
			if (libro != null) {
				FiseFormato13AC cabecera=null;
				try {
					 cabecera=FormatoExcelImport.readSheetCabecera(libro);
					 if(cabecera!=null ){
						cabecera.getId().setEtapa("Solicitud");
						cabecera.setNombreArchivoExcel(archivo.getTitle());
						cabecera.setUsuarioCreacion(themeDisplay.getUser().getLogin());
						cabecera.setTerminalCreacion(themeDisplay.getUser().getLoginIP());
						cabecera.setFechaCreacion(new Date());
						cabecera=formatoService.savecabecera(cabecera);
						
					 }
					 
				} catch (Exception e1) {
					e1.printStackTrace();
					//logger.warn(mapaErrores.get(FiseConstants.COD_ERROR_F12_20));
					//cont++;
					//sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_20);
					//throw new Exception(mapaErrores.get(FiseConstants.COD_ERROR_F12_20));
					cabecera=null;
					System.out.println("entro en !!!"+e1);
				}
				System.out.println("cabecera es :::=>"+cabecera);
				if(cabecera!=null){
					List<FiseFormato13AD> lstDetalle=null;
					try {
						lstDetalle=FormatoExcelImport.getListDetalleSheet(libro,cabecera);
						System.out.println("******* INICIO DETALLE****");
						
						if(lstDetalle !=null && !lstDetalle.isEmpty()){
							for(FiseFormato13AD detalle:lstDetalle){
								detalle.setUsuarioCreacion(themeDisplay.getUser().getLogin());
								detalle.setTerminalCreacion(themeDisplay.getUser().getLoginIP());
								detalle.setFechaCreacion(new Date());
								System.out.println("******* INICIO FILA NRO ****");
								System.out.println("SECTOR ==>"+detalle.getId().getCodSectorTipico());
								System.out.println("ANIO/MES ==>"+detalle.getAnoAlta()+""+detalle.getMesAlta());
								System.out.println("UBIGEO ==>"+detalle.getId().getCodUbigeo());
								System.out.println("BENEFICIARIO ==>"+detalle.getId().getIdZonaBenef());
								System.out.println("SEDE ==>"+detalle.getNombreSedeAtiende());
								System.out.println("VALOR ==>"+detalle.getNumeroBenefiPoteSectTipico());
								System.out.println("******* FIN FILA****");
							    formatoService.savedetalle(detalle);	
							  
							}
							
						}
						
						System.out.println("******* FIN DETALLE****");
						
					} catch (Exception e1) {
						logger.warn(mapaErrores.get(FiseConstants.COD_ERROR_F12_20));
						cont++;
						sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12_20);
						throw new Exception(mapaErrores.get(FiseConstants.COD_ERROR_F12_20));
					}
				}
				
				
			}
			
		}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return formatoMensaje;
	}
	
	@ResourceMapping("getData")
	public void getData(ModelMap model, ResourceRequest request,ResourceResponse response, @ModelAttribute("formato13AGartCommand")Formato13AGartCommand command) {
	  
		
		try {
			JSONObject jsonObj = new JSONObject();
			String tipo = request.getParameter("tipo"); 
			System.out.println("getData tipo::>"+tipo);
			jsonObj.put("resultado", "OK");
			response.setContentType("application/json");
		    PrintWriter pw = response.getWriter();
		    pw.write(jsonObj.toString());
		    pw.flush();
		    pw.close();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
