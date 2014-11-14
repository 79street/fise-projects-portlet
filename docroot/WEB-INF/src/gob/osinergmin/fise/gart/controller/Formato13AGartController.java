package gob.osinergmin.fise.gart.controller;



import gob.osinergmin.fise.bean.Formato13ADReportBean;
import gob.osinergmin.fise.bean.MensajeErrorBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.AdmUbigeo;
import gob.osinergmin.fise.domain.FiseFormato13AC;
import gob.osinergmin.fise.domain.FiseFormato13ACPK;
import gob.osinergmin.fise.domain.FiseFormato13AD;
import gob.osinergmin.fise.domain.FiseFormato13ADPK;
import gob.osinergmin.fise.domain.FisePeriodoEnvio;
import gob.osinergmin.fise.gart.command.Formato13AGartCommand;
import gob.osinergmin.fise.gart.json.Formato13AGartJSON;
import gob.osinergmin.fise.gart.service.FisePeriodoEnvioGartService;
import gob.osinergmin.fise.gart.service.Formato13AGartService;
import gob.osinergmin.fise.gart.xls.FormatoExcelImport;
import gob.osinergmin.fise.util.FechaUtil;
import gob.osinergmin.fise.util.FormatoUtil;
import gob.osinergmin.fise.xls.XlsTableConfig;
import gob.osinergmin.fise.xls.XlsWorkbookConfig;
import gob.osinergmin.fise.xls.XlsWorksheetConfig;

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
			//@RequestParam(value = "crud", required = false,defaultValue="CREATE")String crud,
			@ModelAttribute("formato13AGartCommand")Formato13AGartCommand command){
		 System.out.println("aqui en nuevoFormato");
		// System.out.println("CRUD:"+crud);
		command.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));
		command.setReadOnly(false);
		model.addAttribute("crud", CRUD_CREATE);
		model.addAttribute("readonly", "false");
		
		
		mapaErrores = fiseUtil.getMapaErrores();
		
		
		return "formato13ACRUD";
	}
	
	@ActionMapping(params="action=guardarDetalle")
	public void guardarDetalleFormato(ModelMap model,ActionRequest request,ActionResponse response,
			@RequestParam("crud")String crud,@ModelAttribute("formato13AGartCommand")Formato13AGartCommand command){
			
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		
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
		
		String anioPresentacion="";
		String mesPresentacion="";
		String etapa="";
		if(periodoDeclaracion!=null && periodoDeclaracion.length()>6){
			anioPresentacion=periodoDeclaracion.substring(0,4);
			mesPresentacion=periodoDeclaracion.substring(4,6);
			etapa=periodoDeclaracion.substring(6);
		}
		
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
		
		//seteamos los valores obtenidos
		command.setAnioPresentacion(anioPresentacion);
		command.setMesPresentacion(mesPresentacion);
		command.setEtapa(etapa);
		//
		
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
			//validar si es nuevo o modificacion
			if(CRUD_CREATE.equals(crud)){
				//create
				FiseFormato13AC formato13 = new FiseFormato13AC();
				formato13.setId(pkCabecera);
				List<FiseFormato13AD> listaDetalle = new ArrayList<FiseFormato13AD>();
				//se agregará varaiables de sector tipico para cada tipo de grupo beneficiario
				
				agregarSectorTipico(themeDisplay, FiseConstants.SECTOR_TIPICO_1_COD, command, listaDetalle);
				agregarSectorTipico(themeDisplay, FiseConstants.SECTOR_TIPICO_2_COD, command, listaDetalle);
				agregarSectorTipico(themeDisplay, FiseConstants.SECTOR_TIPICO_3_COD, command, listaDetalle);
				agregarSectorTipico(themeDisplay, FiseConstants.SECTOR_TIPICO_4_COD, command, listaDetalle);
				agregarSectorTipico(themeDisplay, FiseConstants.SECTOR_TIPICO_5_COD, command, listaDetalle);
				agregarSectorTipico(themeDisplay, FiseConstants.SECTOR_TIPICO_6_COD, command, listaDetalle);
				agregarSectorTipico(themeDisplay, FiseConstants.SECTOR_TIPICO_SER_COD, command, listaDetalle);
				agregarSectorTipico(themeDisplay, FiseConstants.SECTOR_TIPICO_ESP_COD, command, listaDetalle);
				
				agregarFormato13Detalle(listaDetalle);
			}else if (CRUD_UPDATE.equals(crud)) {
				//update
				
				if( cab.getFiseFormato13ADs()!=null && !cab.getFiseFormato13ADs().isEmpty() ){
					for (FiseFormato13AD d : cab.getFiseFormato13ADs()) {
						
						if( Long.parseLong(command.getIdZonaBenef())==d.getId().getIdZonaBenef() ){
							if( FiseConstants.SECTOR_TIPICO_1_COD.equals(d.getId().getCodSectorTipico()) ){
								modificarSectorTipico(themeDisplay, FiseConstants.SECTOR_TIPICO_1_COD, command, d);
							}else if( FiseConstants.SECTOR_TIPICO_2_COD.equals(d.getId().getCodSectorTipico()) ){
								modificarSectorTipico(themeDisplay, FiseConstants.SECTOR_TIPICO_2_COD, command, d);
							}else if( FiseConstants.SECTOR_TIPICO_3_COD.equals(d.getId().getCodSectorTipico()) ){
								modificarSectorTipico(themeDisplay, FiseConstants.SECTOR_TIPICO_3_COD, command, d);
							}else if( FiseConstants.SECTOR_TIPICO_4_COD.equals(d.getId().getCodSectorTipico()) ){
								modificarSectorTipico(themeDisplay, FiseConstants.SECTOR_TIPICO_4_COD, command, d);
							}else if( FiseConstants.SECTOR_TIPICO_5_COD.equals(d.getId().getCodSectorTipico()) ){
								modificarSectorTipico(themeDisplay, FiseConstants.SECTOR_TIPICO_5_COD, command, d);
							}else if( FiseConstants.SECTOR_TIPICO_6_COD.equals(d.getId().getCodSectorTipico()) ){
								modificarSectorTipico(themeDisplay, FiseConstants.SECTOR_TIPICO_6_COD, command, d);
							}else if( FiseConstants.SECTOR_TIPICO_SER_COD.equals(d.getId().getCodSectorTipico()) ){
								modificarSectorTipico(themeDisplay, FiseConstants.SECTOR_TIPICO_SER_COD, command, d);
							}else if( FiseConstants.SECTOR_TIPICO_ESP_COD.equals(d.getId().getCodSectorTipico()) ){
								modificarSectorTipico(themeDisplay, FiseConstants.SECTOR_TIPICO_ESP_COD, command, d);
							}
						}
					
					}
					modificarFormato13Detalle(cab.getFiseFormato13ADs());
				}

			}
			
		}else{
			//no se valida si es nulo la cabecera
		}
		
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
			System.out.println("busquedaDetalle");
			response.setContentType("application/json");	
			logger.info("admin2.1:"+model.get("esAdministrador"));
			HttpServletRequest req = PortalUtil.getHttpServletRequest(request);	        
	        HttpSession session = req.getSession();
	        List<Formato13ADReportBean> listaFormato;	
		    JSONArray jsonArray = new JSONArray();
		    Map<String, String> mapaEmpresa = fiseUtil.getMapaEmpresa();
		    
		    String tipo=request.getParameter("tipo");
		    System.out.println("tipooo::>"+request.getParameter("tipo"));
		    String codEmpresa = "";
			String periodoDeclaracion="";
			
		    if(tipo!=null && (tipo.equalsIgnoreCase("0") || tipo.equalsIgnoreCase("1"))){
		    	
		    	System.out.println("codEmpresa::>"+request.getParameter("codEmpresa"));
				System.out.println("anoPresentacion::>"+request.getParameter("anoPresentacion"));
				
		    	codEmpresa =request.getParameter("codEmpresa");
		    	periodoDeclaracion=request.getParameter("anoPresentacion")+request.getParameter("mesPresentacion")+request.getParameter("etapa");
		    
		    	command.setCodEmpresa(codEmpresa);
				command.setAnioPresentacion(request.getParameter("anoPresentacion"));
				command.setMesPresentacion(request.getParameter("mesPresentacion"));
				command.setEtapa(request.getParameter("etapa"));
				command.setPeridoDeclaracion(periodoDeclaracion);
				
				String anioPresentacion="";
				String mesPresentacion="";
				String etapa="";
				logger.info("valores "+ codEmpresa);
	  			logger.info("valores "+ periodoDeclaracion);
	  			
	  			if(periodoDeclaracion!=null && periodoDeclaracion.length()>6){
	  				anioPresentacion=periodoDeclaracion.substring(0,4);
	  				mesPresentacion=periodoDeclaracion.substring(4,6);
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
	  			fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_FORMATO_13A, FiseConstants.NOMBRE_EXCEL_FORMATO13A, FiseConstants.NOMBRE_HOJA_FORMATO13A, listaFormato);
		    
		    }else{
		    	 codEmpresa = command.getCodEmpresa();
				 periodoDeclaracion=command.getPeridoDeclaracion();
		    }

		    
  			logger.info("arreglo json:"+jsonArray);
  			PrintWriter pw = response.getWriter();
  			pw.write(jsonArray.toString());
  			pw.flush();
  			pw.close();
  			
  			
  			model.addAttribute("formato13AGartCommand", command);
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
		System.out.println("command UPLOAD::"+command.getCodEmpresa());
		System.out.println("command UPLOAD::"+command.getPeridoDeclaracion());
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
						cabecera.getId().setEtapa("SOLICITUD");
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
	
	@RequestMapping(params="action=view")
	public String viewFormato(ModelMap model, RenderRequest request,RenderResponse response, @ModelAttribute("formato13AGartCommand")Formato13AGartCommand command) {
	  
		try {
		
			String tipo = request.getParameter("tipo"); 
			String codEmp = request.getParameter("codEmpresa"); 
			String anio = request.getParameter("anoPresentacion"); 
			String mes = request.getParameter("mesPresentacion"); 
			String etapa = request.getParameter("etapa"); 
			System.out.println("etapa::>"+etapa);
			command.setReadOnly(true);
			command.setCodEmpresa(codEmp);
			command.setAnioPresentacion(anio);
			command.setMesPresentacion(mes);
			command.setEtapa(etapa);
			command.setListaEmpresas(fiseUtil.getEmpresaxUsuario(request));
			command.setListaPeriodo(periodoService.listarFisePeriodoEnvioMesAnioEtapa(codEmp, FiseConstants.NOMBRE_FORMATO_13A));
			command.setPeridoDeclaracion(""+anio+""+mes+""+etapa);
			System.out.println("setPeridoDeclaracion::>"+command.getPeridoDeclaracion());
			if(tipo !=null && tipo.equalsIgnoreCase("0")){
				model.addAttribute("crud", CRUD_READ);
				model.addAttribute("readonly", "true");
			}if(tipo !=null && tipo.equalsIgnoreCase("1")){
				model.addAttribute("crud", CRUD_UPDATE);
				model.addAttribute("readonly", "false");
			}
			
			model.addAttribute("formato13AGartCommand", command);
			
			
			
			
		} catch (Exception e) {
			System.out.println("entro error view");
			e.printStackTrace();
		}
		return "formato13ACRUD";
	}
	
	/**metodos utiles*/
	
	public void agregarFormato13Detalle(List<FiseFormato13AD> listaDetalle){
		if( listaDetalle != null && listaDetalle.size()>0 ){
			for (FiseFormato13AD detalle : listaDetalle) {
				formatoService.savedetalle(detalle);
			}
		}
	}
	
	public void modificarFormato13Detalle(List<FiseFormato13AD> listaDetalle){
		if( listaDetalle != null && listaDetalle.size()>0 ){
			for (FiseFormato13AD detalle : listaDetalle) {
				formatoService.updatedetalle(detalle);
			}
		}
	}
	
	/*public void agregarFormato13(ThemeDisplay themeDisplay, FiseFormato13AC formato, List<FiseFormato13AD> listaDetalle){
		
		Date hoy = FechaUtil.obtenerFechaActual();
		try{
			formato.setUsuarioActualizacion(themeDisplay.getUser().getLogin());
			formato.setTerminalActualizacion(themeDisplay.getUser().getLoginIP());
			formato.setFechaActualizacion(hoy);
			formato.setUsuarioCreacion(themeDisplay.getUser().getLogin());
			formato.setTerminalCreacion(themeDisplay.getUser().getLoginIP());
			formato.setFechaCreacion(hoy);
			//verificar si es necesario realizar esta validacion
			//boolean existe = false;
			//existe = formatoService.existeFormato14AC(formato);
			//if(existe){
			//	throw new Exception("Ya existe un registro con la misma clave.");
			//}else{
				formatoService.savecabecera(formato);
			//}
			//add
			for (FiseFormato13AD detalle : listaDetalle) {
				formatoService.savedetalle(detalle);
			}
			if( listaDetalle != null && listaDetalle.size()>0 ){
				formato.setFiseFormato13ADs(listaDetalle);
			}
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void modificarFormato13(ThemeDisplay themeDisplay, FiseFormato13AC formato){
		
		Date hoy = FechaUtil.obtenerFechaActual();
		try{
			formato.setUsuarioActualizacion(themeDisplay.getUser().getLogin());
			formato.setTerminalActualizacion(themeDisplay.getUser().getLoginIP());
			formato.setFechaActualizacion(hoy);
			
			formatoService.updatecabecera(formato);
			
			if( formato.getFiseFormato13ADs() != null && formato.getFiseFormato13ADs().size()>0 ){
				for (FiseFormato13AD detalle : formato.getFiseFormato13ADs()) {
					formatoService.updatedetalle(detalle);
				}
			}
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}*/
	
	public void agregarSectorTipico(ThemeDisplay themeDisplay, String sectorTipico, Formato13AGartCommand command, List<FiseFormato13AD> listaDetalle ){
		
		Date hoy = FechaUtil.obtenerFechaActual();
		
		try{
		
			FiseFormato13AD detalle = new FiseFormato13AD();
			FiseFormato13ADPK pk = new FiseFormato13ADPK();
			pk.setCodEmpresa(command.getCodEmpresa());
			pk.setAnoPresentacion(Long.parseLong(command.getAnioPresentacion()));
			pk.setMesPresentacion(Long.parseLong(command.getMesPresentacion()));
			pk.setEtapa(command.getEtapa());
			pk.setCodUbigeo(command.getCodDistrito());
			pk.setCodSectorTipico(sectorTipico);
			pk.setIdZonaBenef(Long.parseLong(command.getIdZonaBenef()));
			detalle.setId(pk);
			detalle.setAnoAlta(Long.parseLong(command.getAnioAlta()));
			detalle.setMesAlta(Long.parseLong(command.getMesAlta()));
			//luego verificar de donde se obtendra los valores de ano e inicio de vigencia
			detalle.setAnoInicioVigencia(Long.parseLong(command.getAnioAlta()));
			detalle.setAnoFinVigencia(Long.parseLong(command.getAnioAlta()));
			//
			detalle.setDescripcionLocalidad(command.getLocalidad());
			detalle.setNombreSedeAtiende(command.getNombreSede());
			if( FiseConstants.SECTOR_TIPICO_1_COD.equals(sectorTipico) ){
				detalle.setNumeroBenefiPoteSectTipico(Long.parseLong(command.getSt1()));
			}else if( FiseConstants.SECTOR_TIPICO_2_COD.equals(sectorTipico) ){
				detalle.setNumeroBenefiPoteSectTipico(Long.parseLong(command.getSt2()));
			}else if( FiseConstants.SECTOR_TIPICO_3_COD.equals(sectorTipico) ){
				detalle.setNumeroBenefiPoteSectTipico(Long.parseLong(command.getSt3()));
			}else if( FiseConstants.SECTOR_TIPICO_4_COD.equals(sectorTipico) ){
				detalle.setNumeroBenefiPoteSectTipico(Long.parseLong(command.getSt4()));
			}else if( FiseConstants.SECTOR_TIPICO_5_COD.equals(sectorTipico) ){
				detalle.setNumeroBenefiPoteSectTipico(Long.parseLong(command.getSt5()));
			}else if( FiseConstants.SECTOR_TIPICO_6_COD.equals(sectorTipico) ){
				detalle.setNumeroBenefiPoteSectTipico(Long.parseLong(command.getSt6()));
			}else if( FiseConstants.SECTOR_TIPICO_SER_COD.equals(sectorTipico) ){
				detalle.setNumeroBenefiPoteSectTipico(Long.parseLong(command.getStser()));
			}else if( FiseConstants.SECTOR_TIPICO_ESP_COD.equals(sectorTipico) ){
				detalle.setNumeroBenefiPoteSectTipico(Long.parseLong(command.getStesp()));
			}
			detalle.setUsuarioCreacion(themeDisplay.getUser().getLogin());
			detalle.setTerminalCreacion(themeDisplay.getUser().getLoginIP());
			detalle.setFechaCreacion(hoy);
			detalle.setUsuarioActualizacion(themeDisplay.getUser().getLogin());
			detalle.setTerminalActualizacion(themeDisplay.getUser().getLoginIP());
			detalle.setFechaActualizacion(hoy);
			listaDetalle.add(detalle);
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	public void modificarSectorTipico(ThemeDisplay themeDisplay, String sectorTipico, Formato13AGartCommand command, FiseFormato13AD detalle){
	
		Date hoy = FechaUtil.obtenerFechaActual();
		
		try{
			//verificar que campos son editables en la vista de modificacion
			detalle.setAnoAlta(Long.parseLong(command.getAnioAlta()));
			detalle.setMesAlta(Long.parseLong(command.getMesAlta()));
			//luego verificar de donde se obtendra los valores de ano e inicio de vigencia
			detalle.setAnoInicioVigencia(Long.parseLong(command.getAnioAlta()));
			detalle.setAnoFinVigencia(Long.parseLong(command.getAnioAlta()));
			//
			detalle.setDescripcionLocalidad(command.getLocalidad());
			detalle.setNombreSedeAtiende(command.getNombreSede());
			if( FiseConstants.SECTOR_TIPICO_1_COD.equals(sectorTipico) ){
				detalle.setNumeroBenefiPoteSectTipico(Long.parseLong(command.getSt1()));
			}else if( FiseConstants.SECTOR_TIPICO_2_COD.equals(sectorTipico) ){
				detalle.setNumeroBenefiPoteSectTipico(Long.parseLong(command.getSt2()));
			}else if( FiseConstants.SECTOR_TIPICO_3_COD.equals(sectorTipico) ){
				detalle.setNumeroBenefiPoteSectTipico(Long.parseLong(command.getSt3()));
			}else if( FiseConstants.SECTOR_TIPICO_4_COD.equals(sectorTipico) ){
				detalle.setNumeroBenefiPoteSectTipico(Long.parseLong(command.getSt4()));
			}else if( FiseConstants.SECTOR_TIPICO_5_COD.equals(sectorTipico) ){
				detalle.setNumeroBenefiPoteSectTipico(Long.parseLong(command.getSt5()));
			}else if( FiseConstants.SECTOR_TIPICO_6_COD.equals(sectorTipico) ){
				detalle.setNumeroBenefiPoteSectTipico(Long.parseLong(command.getSt6()));
			}else if( FiseConstants.SECTOR_TIPICO_SER_COD.equals(sectorTipico) ){
				detalle.setNumeroBenefiPoteSectTipico(Long.parseLong(command.getStser()));
			}else if( FiseConstants.SECTOR_TIPICO_ESP_COD.equals(sectorTipico) ){
				detalle.setNumeroBenefiPoteSectTipico(Long.parseLong(command.getStesp()));
			}
			detalle.setUsuarioActualizacion(themeDisplay.getUser().getLogin());
			detalle.setTerminalActualizacion(themeDisplay.getUser().getLoginIP());
			detalle.setFechaActualizacion(hoy);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
}
