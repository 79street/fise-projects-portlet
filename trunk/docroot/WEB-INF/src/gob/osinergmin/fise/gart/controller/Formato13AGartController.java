package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.Formato13ADReportBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.FiseFormato13AC;
import gob.osinergmin.fise.domain.FiseFormato13ACPK;
import gob.osinergmin.fise.domain.FisePeriodoEnvio;
import gob.osinergmin.fise.gart.command.Formato13AGartCommand;
import gob.osinergmin.fise.gart.json.Formato13AGartJSON;
import gob.osinergmin.fise.gart.service.FisePeriodoEnvioGartService;
import gob.osinergmin.fise.gart.service.Formato13AGartService;
import gob.osinergmin.fise.util.FormatoUtil;
import gob.osinergmin.fise.xls.XlsTableConfig;
import gob.osinergmin.fise.xls.XlsWorkbookConfig;
import gob.osinergmin.fise.xls.XlsWorksheetConfig;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.PortalUtil;

@SessionAttributes({"esAdministrador"})
@Controller("formato13AGartController")
@RequestMapping("VIEW")
public class Formato13AGartController {

	private static final Log logger=LogFactoryUtil.getLog(Formato13AGartController.class);
	
	@Autowired
	@Qualifier("fiseUtil")
	private FiseUtil fiseUtil;

	@Autowired
	@Qualifier("formato13AGartServiceImpl")
	private Formato13AGartService formatoService;
	
	@Autowired
	@Qualifier("fisePeriodoEnvioGartServiceImpl")
	FisePeriodoEnvioGartService periodoService;
	
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
	public String nuevoFormato(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse,@ModelAttribute("formato13AGartCommand")Formato13AGartCommand command){
		
		command.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));
		model.addAttribute("crud", "CREATE");
		return "formato13ACRUD";
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
		    Map<Long,String> listaMes=fiseUtil.getMapaMeses();
		    
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
}
