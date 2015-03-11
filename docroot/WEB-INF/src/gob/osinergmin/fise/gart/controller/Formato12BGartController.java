package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.Formato12A12BGeneric;
import gob.osinergmin.fise.bean.Formato12BCBean;
import gob.osinergmin.fise.bean.Formato12BMensajeBean;
import gob.osinergmin.fise.bean.MensajeErrorBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.CfgCampo;
import gob.osinergmin.fise.domain.CfgTabla;
import gob.osinergmin.fise.domain.FiseFormato12BC;
import gob.osinergmin.fise.domain.FiseFormato12BCPK;
import gob.osinergmin.fise.domain.FiseFormato12BD;
import gob.osinergmin.fise.domain.FiseFormato12BDOb;
import gob.osinergmin.fise.domain.FiseFormato12BDPK;
import gob.osinergmin.fise.domain.FiseFormato14BD;
import gob.osinergmin.fise.domain.FiseGrupoInformacion;
import gob.osinergmin.fise.domain.FisePeriodoEnvio;
import gob.osinergmin.fise.gart.command.Formato12BGartCommand;
import gob.osinergmin.fise.gart.jsp.FileEntryJSP;
import gob.osinergmin.fise.gart.service.CfgCampoGartService;
import gob.osinergmin.fise.gart.service.CfgTablaGartService;
import gob.osinergmin.fise.gart.service.CommonGartService;
import gob.osinergmin.fise.gart.service.FisePeriodoEnvioGartService;
import gob.osinergmin.fise.gart.service.Formato12BGartService;
import gob.osinergmin.fise.gart.service.Formato14BGartService;
import gob.osinergmin.fise.gart.xls.FormatoExcelImport;
import gob.osinergmin.fise.util.FechaUtil;
import gob.osinergmin.fise.util.FormatoUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
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
import com.liferay.portlet.documentlibrary.FileMimeTypeException;

@SessionAttributes({ "esAdministrador" })
@Controller("formato12BGartController")
@RequestMapping("VIEW")
public class Formato12BGartController {

	private static final Log logger = LogFactoryUtil.getLog(Formato12BGartController.class);

	@Autowired
	@Qualifier("fiseUtil")
	private FiseUtil fiseUtil;

	@Autowired
	@Qualifier("formato12BGartServiceImpl")
	private Formato12BGartService formatoService;

	@Autowired
	@Qualifier("fisePeriodoEnvioGartServiceImpl")
	FisePeriodoEnvioGartService periodoService;

	@Autowired
	@Qualifier("commonGartServiceImpl")
	CommonGartService commonService;
	
	@Autowired
	@Qualifier("cfgTablaGartServiceImpl")
	CfgTablaGartService tablaService;
	
	@Autowired
	@Qualifier("cfgCampoGartServiceImpl")
	CfgCampoGartService campoService;
	
	@Autowired
	@Qualifier("formato14BGartServiceImpl")
	Formato14BGartService formato14Service;

	private String nameEstado;
	private String nameGrupo;

	List<MensajeErrorBean> listaObservaciones;
	List<FisePeriodoEnvio> listaPeriodoEnvio;
	
	Map<String, String> mapaErrores;
	Map<String, String> mapaEtapa;
	
	private Formato12BGartCommand formato12BBusqueda;

	@RequestMapping
	public String defaultView(ModelMap model, RenderRequest renderRequest, RenderResponse renderResponse, @ModelAttribute("formato12BGartCommand") Formato12BGartCommand command) {

		command.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));
		command.setListaMes(fiseUtil.getMapaMeses());
		
		
			logger.info("volvio entrar::");
			if(formato12BBusqueda!=null && formato12BBusqueda.getAnioInicio()!=null){
				logger.info("volvio entrar anio inicio::"+formato12BBusqueda.getAnioInicio());
				command.setAnioInicio(formato12BBusqueda.getAnioInicio());
			}else{
				command.setAnioInicio(fiseUtil.obtenerNroAnioFechaAnterior() != null ? Integer.parseInt(fiseUtil.obtenerNroAnioFechaAnterior()) : null);
			}
			if(formato12BBusqueda!=null && formato12BBusqueda.getAnioFin()!=null){
				command.setAnioFin(formato12BBusqueda.getAnioFin());
			}else{
				command.setAnioFin(fiseUtil.obtenerNroAnioFechaActual() != null ? Integer.parseInt(fiseUtil.obtenerNroAnioFechaActual()) : null);
				
			}
			if(formato12BBusqueda!=null ){
				logger.info("volvio entrar MES INICIO::"+formato12BBusqueda.getMesInicio());
				command.setMesInicio(formato12BBusqueda.getMesInicio());
				command.setMesFin(formato12BBusqueda.getMesFin());
			}else{
				command.setMesInicio(fiseUtil.obtenerNroMesFechaAnterior() != null ? (Integer.parseInt(fiseUtil.obtenerNroMesFechaAnterior())) : null);
				command.setMesFin(fiseUtil.obtenerNroMesFechaActual() != null ? (Integer.parseInt(fiseUtil.obtenerNroMesFechaActual())) : null);
			}
			
			if(formato12BBusqueda!=null && formato12BBusqueda.getEtapaBusqueda()!=null){
				command.setEtapaBusqueda(formato12BBusqueda.getEtapaBusqueda());
			}
			if(formato12BBusqueda!=null && formato12BBusqueda.getCodEmpresaBusqueda()!=null){
				command.setCodEmpresaBusqueda(formato12BBusqueda.getCodEmpresaBusqueda());
			}
		
		
		model.addAttribute("esAdministrador", fiseUtil.esAdministrador(renderRequest));
		
		mapaErrores = fiseUtil.getMapaErrores();
		mapaEtapa = fiseUtil.getMapaEtapa();

		logger.info("admin1.1 busqueda:" + model.get("esAdministrador"));

		return "formato12BInicio";
	}

	@ResourceMapping("searchFormats")
	public void grid(ResourceRequest request, ResourceResponse response, @ModelAttribute("formato12BGartCommand") Formato12BGartCommand command) {

		try {
			JSONArray jsonArray = null;
			HttpServletRequest req = PortalUtil.getHttpServletRequest(request);
			HttpSession session = req.getSession();
            String codEmp=command.getCodEmpresaBusqueda()!=null?command.getCodEmpresaBusqueda().trim():null;
            
            
            if(command!=null){
            	logger.info("comman difente de nulo");
            }
			List<FiseFormato12BC> lstFise = formatoService.getLstFormatoCabecera(codEmp, command.getAnioInicio(), command.getMesInicio(), command.getAnioFin(), command.getMesFin(), command.getEtapaBusqueda());

			if (lstFise != null && !lstFise.isEmpty()) {
				List<Formato12BGartCommand> lstCommand = Formato12BGartCommand.toListCommandCabecera(lstFise);
				fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_FORMATO_12B, FiseConstants.NOMBRE_EXCEL_FORMATO12B, FiseConstants.NOMBRE_HOJA_FORMATO12B, lstCommand);
				jsonArray = Formato12BGartCommand.toListJSONCabecera(lstFise, commonService);
				
			}
			
			  formato12BBusqueda=new Formato12BGartCommand();
	          formato12BBusqueda.setAnioInicio(command.getAnioInicio());
	          formato12BBusqueda.setAnioFin(command.getAnioFin());
	          formato12BBusqueda.setMesInicio(command.getMesInicio());
	          formato12BBusqueda.setMesFin(command.getMesFin());
	          formato12BBusqueda.setEtapaBusqueda(command.getEtapaBusqueda());
	          formato12BBusqueda.setCodEmpresaBusqueda(command.getCodEmpresaBusqueda());
			
			PrintWriter pw = response.getWriter();
			pw.write(jsonArray != null ? jsonArray.toString():(new JSONArray()).toString());
			pw.flush();
			pw.close();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {

		}

	}

	@ResourceMapping("loadPeriodoDeclaracion")
	public void loadPeriodoDeclaracion(ModelMap model, ResourceRequest request, ResourceResponse response, @ModelAttribute("formato12BGartCommand") Formato12BGartCommand command) {
		try {
			logger.info("codEmpresa::" + command.getCodEmpresa());
			List<FisePeriodoEnvio> listaPeriodoEnvio = periodoService.listarFisePeriodoEnvioMesAnioEtapa(command.getCodEmpresa().trim(), FiseConstants.NOMBRE_FORMATO_12B);
			JSONArray jsonArray = new JSONArray();
			for (FisePeriodoEnvio periodo : listaPeriodoEnvio) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("codigoItem", periodo.getCodigoItem());
				jsonObj.put("descripcionItem", periodo.getDescripcionItem());
				jsonObj.put("flagPeriodoEjecucion", periodo.getFlagPeriodoEjecucion());
				jsonArray.put(jsonObj);
			}

			PrintWriter pw = response.getWriter();
			pw.write(jsonArray.toString());
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@ResourceMapping("loadGrupoInformacion")
  	public void cargaFlagPeriodo(ResourceRequest request,ResourceResponse response,@ModelAttribute("formato12BGartCommand") Formato12BGartCommand command){
		try {			
  			response.setContentType("applicacion/json");
  			String periodoEnvio = command.getPeridoDeclaracion();
  			JSONObject jsonObj = new JSONObject();
  			
  			String anoPresentacion = "";
  			String mesPresentacion = "";
  			String anoEjecucion = "";
  			String mesEjecucion = "";
  			
  			if( periodoEnvio!=null && periodoEnvio.length()>6 ){
  				anoPresentacion = periodoEnvio.substring(0, 4);
  				mesPresentacion = periodoEnvio.substring(4,6);
  			}
  			
  			String flagPeriodoEjecucion = "";
  			List<FisePeriodoEnvio> listaPeriodoEnvio = periodoService.listarFisePeriodoEnvioMesAnioEtapa(command.getCodEmpresa().trim(), FiseConstants.NOMBRE_FORMATO_12B);
  			
  			if( periodoEnvio!=null ){
  				for (FisePeriodoEnvio p : listaPeriodoEnvio) {
  					if( periodoEnvio.equals(p.getCodigoItem()) ){
  						jsonObj.put("flagPeriodoEjecucion", p.getFlagPeriodoEjecucion());
  						flagPeriodoEjecucion = p.getFlagPeriodoEjecucion();
  						break;
  					}
  				}
  			}
  			
			anoEjecucion = anoPresentacion;
			mesEjecucion = mesPresentacion;
  			
  			long anoP =0;
  			long mesP =0;
  			long anoE =0;
  			long mesE =0;
  			
  			if(FormatoUtil.isNotBlank(anoPresentacion)){ 
  				anoP = Long.valueOf(anoPresentacion);
  			}
  			if(FormatoUtil.isNotBlank(mesPresentacion)){ 
  				mesP = Long.valueOf(mesPresentacion);
  			}
  			if(FormatoUtil.isNotBlank(anoEjecucion)){ 
  				anoE = Long.valueOf(anoEjecucion);
  			}
  			if(FormatoUtil.isNotBlank(mesEjecucion)){ 
  				mesE = Long.valueOf(mesEjecucion);
  			}
  			
  			boolean ultimaEtapaFormato = fiseUtil.bloquearFormatoXEtapa(FiseConstants.TIPO_FORMATO_12B,command.getCodEmpresa(),anoP,mesP,anoE,mesE,0,0);
  			if(ultimaEtapaFormato){
  				jsonObj.put("etapaFinal","SI");
  			}else{
  				jsonObj.put("etapaFinal","NO");
  			}
  			
  			if( periodoEnvio!=null && periodoEnvio.length()>6 ){
  				long idGrupo = commonService.obtenerIdGrupoInformacion(Long.parseLong(periodoEnvio.substring(0, 4)), Long.parseLong(periodoEnvio.substring(4, 6)), FiseConstants.MENSUAL);
  				jsonObj.put("idGrupoInfo", idGrupo);
  			}else{
  				jsonObj.put("idGrupoInfo", 0);
  			}
  			
  			logger.info(jsonObj.toString());
  			PrintWriter pw = response.getWriter();
  		    pw.write(jsonObj.toString());
  		    pw.flush();
  		    pw.close();							
  		}catch (Exception e) {
  			// TODO: handle exception
  			e.printStackTrace();
  		}
	}
	
	//para la edicion del anio y mes de ejecucion
	@ResourceMapping("loadGrupoInformacionEdit")
  	public void cargaFlagPeriodoEdit(ResourceRequest request,ResourceResponse response,@ModelAttribute("formato12BGartCommand") Formato12BGartCommand command){
		try {			
  			response.setContentType("applicacion/json");
  			String periodoEnvio = command.getPeridoDeclaracion();
  			JSONObject jsonObj = new JSONObject();
  			
  			String anoPresentacion = "";
  			String mesPresentacion = "";
  			String anoEjecucion = "";
  			String mesEjecucion = "";
  			
  			if( periodoEnvio!=null && periodoEnvio.length()>6 ){
  				anoPresentacion = periodoEnvio.substring(0, 4);
  				mesPresentacion = periodoEnvio.substring(4,6);
  			}
  			
  			String flagPeriodoEjecucion = "";
  			List<FisePeriodoEnvio> listaPeriodoEnvio = periodoService.listarFisePeriodoEnvioMesAnioEtapa(command.getCodEmpresa().trim(), FiseConstants.NOMBRE_FORMATO_12B);
  			
  			for (FisePeriodoEnvio p : listaPeriodoEnvio) {
				if( periodoEnvio.equals(p.getCodigoItem()) ){
					jsonObj.put("flagPeriodoEjecucion", p.getFlagPeriodoEjecucion());
					flagPeriodoEjecucion = p.getFlagPeriodoEjecucion();
					break;
				}
			}
  			
  			if( "S".equals(flagPeriodoEjecucion) ){
	    		anoEjecucion = ""+command.getAnoEjecucionGasto();
				mesEjecucion = ""+command.getMesEjecucionGasto();
				if(command.getAnoEjecucionGasto()==null){
					anoEjecucion = anoPresentacion;
				}
				if(command.getMesEjecucionGasto()==null){
					mesEjecucion = mesPresentacion;
				}
			}else{
				anoEjecucion = anoPresentacion;
				mesEjecucion = mesPresentacion;
			}
  			
  			long anoP =0;
  			long mesP =0;
  			long anoE =0;
  			long mesE =0;
  			
  			if(FormatoUtil.isNotBlank(anoPresentacion)){ 
  				anoP = Long.valueOf(anoPresentacion);
  			}
  			if(FormatoUtil.isNotBlank(mesPresentacion)){ 
  				mesP = Long.valueOf(mesPresentacion);
  			}
  			if(FormatoUtil.isNotBlank(anoEjecucion)){ 
  				anoE = Long.valueOf(anoEjecucion);
  			}
  			if(FormatoUtil.isNotBlank(mesEjecucion)){ 
  				mesE = Long.valueOf(mesEjecucion);
  			}
  			
  			boolean ultimaEtapaFormato = fiseUtil.bloquearFormatoXEtapa(FiseConstants.TIPO_FORMATO_12B,command.getCodEmpresa(),anoP,mesP,anoE,mesE,0,0);
  			if(ultimaEtapaFormato){
  				jsonObj.put("etapaFinal","SI");
  			}else{
  				jsonObj.put("etapaFinal","NO");
  			}
  			
  			if( periodoEnvio!=null && periodoEnvio.length()>6 ){
  				long idGrupo = commonService.obtenerIdGrupoInformacion(Long.parseLong(periodoEnvio.substring(0, 4)), Long.parseLong(periodoEnvio.substring(4, 6)), FiseConstants.MENSUAL);
  				jsonObj.put("idGrupoInfo", idGrupo);
  			}else{
  				jsonObj.put("idGrupoInfo", 0);
  			}
  			
  			logger.info(jsonObj.toString());
  			PrintWriter pw = response.getWriter();
  		    pw.write(jsonObj.toString());
  		    pw.flush();
  		    pw.close();							
  		}catch (Exception e) {
  			// TODO: handle exception
  			e.printStackTrace();
  		}
	}
	
		
	@ResourceMapping("loadCostoUnitario")
	public void loadCostoUnitario(ModelMap model, ResourceRequest request, ResourceResponse response, @ModelAttribute("formato12BGartCommand") Formato12BGartCommand command) {
		try {
			logger.info("codEmpresa::" + command.getCodEmpresa());
			logger.info("periodo::" + command.getPeridoDeclaracion());
			
			if (command != null && (command.getPeridoDeclaracion() !=null && command.getPeridoDeclaracion().length() > 6)) {
				command.setAnoPresentacion(Integer.parseInt(command.getPeridoDeclaracion().substring(0, 4)));
				command.setMesPresentacion(Integer.parseInt(command.getPeridoDeclaracion().substring(4, 6)));
				command.setEtapa(command.getPeridoDeclaracion().substring(6, command.getPeridoDeclaracion().length()));
			}
			List<FiseFormato14BD> lstfise14D=fiseUtil.getLstCostoUnitario(command.getCodEmpresa(), command.getAnoPresentacion(),null, null, FiseConstants.ETAPA_ESTABLECIDO);
			//List<FiseFormato14BD> lstfise14D=fiseUtil.obtenerFormato14BDVigente(command.getCodEmpresa(), command.getAnoPresentacion().longValue(),command.getIdZonaBenef().longValue());
			
			
			JSONArray jsonArray = new JSONArray();
			if(lstfise14D!=null && !lstfise14D.isEmpty()){
				for (FiseFormato14BD fise14D : lstfise14D) {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("codEmpresa", command.getCodEmpresa());
					jsonObj.put("idZonaBenef", fise14D.getId().getIdZonaBenef());
					jsonObj.put("costoUnitarioImpresionVales", fise14D.getCostoUnitarioImpresionVales()!=null?fise14D.getCostoUnitarioImpresionVales().setScale(2, BigDecimal.ROUND_DOWN):0);
					jsonObj.put("costoUnitReprtoValeDomici", fise14D.getCostoUnitReprtoValeDomici()!=null?fise14D.getCostoUnitReprtoValeDomici().setScale(2, BigDecimal.ROUND_DOWN):0);
					jsonObj.put("costoUnitEntregaValDisEl", fise14D.getCostoUnitEntregaValDisEl()!=null?fise14D.getCostoUnitEntregaValDisEl().setScale(2, BigDecimal.ROUND_DOWN):0);
					jsonObj.put("costoUnitCanjeLiqValFisi", fise14D.getCostoUnitCanjeLiqValFisi()!=null?fise14D.getCostoUnitCanjeLiqValFisi().setScale(2, BigDecimal.ROUND_DOWN):0);
					jsonObj.put("costoUnitCanjeValDigital", fise14D.getCostoUnitCanjeValDigital()!=null?fise14D.getCostoUnitCanjeValDigital().setScale(2, BigDecimal.ROUND_DOWN):0);
					jsonObj.put("costoUnitarioPorAtencion", fise14D.getCostoUnitarioPorAtencion()!=null?fise14D.getCostoUnitarioPorAtencion().setScale(2, BigDecimal.ROUND_DOWN):0);
					
					jsonArray.put(jsonObj);
				}
			}
			logger.info(jsonArray.toString());
            PrintWriter pw = response.getWriter();
			pw.write(jsonArray.toString());
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(params = "action=newFormato")
	public String newFormato(ModelMap model, RenderRequest renderRequest, RenderResponse renderResponse, @ModelAttribute("formato12BGartCommand") Formato12BGartCommand command) {
		
		PortletRequest pRequest = (PortletRequest)renderRequest.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
		
		logger.info("en nuevo formato");
		String codEmpresa = renderRequest.getParameter("codEmpresa");
		String periodo = renderRequest.getParameter("periodoDeclaracion");
		String tipoOperacion = renderRequest.getParameter("tipoOperacion");
		String error = renderRequest.getParameter("error");

		String anio = renderRequest.getParameter("anoPresentacion");
		String mes = renderRequest.getParameter("mesPresentacion");
		String etapa = renderRequest.getParameter("etapa");
		String anioEjec = renderRequest.getParameter("anoEjecucionGasto");
		String mesEjec = renderRequest.getParameter("mesEjecucionGasto");
		
		String msg = renderRequest.getParameter("msgTrans");

		logger.info("anio::busqueda "+command.getAnioInicio());
		logger.info("mes:: busqueda"+command.getMesInicio());
		logger.info("etapa ::: busqueda"+command.getEtapaBusqueda());
		
		List<MensajeErrorBean> listaError = (List<MensajeErrorBean>)pRequest.getPortletSession().getAttribute("listaError", PortletSession.APPLICATION_SCOPE);
		
		if (error != null) {
			model.addAttribute("error", error);
		}

		if (codEmpresa != null) {
			command.setCodEmpresa(codEmpresa);
		}else{//cuando es nuevo, cargamos la empresa seleccionada en la busqueda
			command.setCodEmpresa(command.getCodEmpresaBusqueda());
		}
		if (periodo != null) {
			command.setPeridoDeclaracion(periodo);
		}
		if (anio != null) {
			command.setAnoPresentacion(anio != null ? Integer.valueOf(anio) : null);
		}
		if (mes != null) {
			command.setMesPresentacion(mes != null ? Integer.valueOf(mes) : null);
		}
		if (etapa != null) {
			command.setEtapa(etapa);
		}
		if (anioEjec != null) {
			command.setAnoEjecucionGasto(anioEjec != null ? Integer.valueOf(anioEjec) : null);
		}
		if (mesEjec != null) {
			command.setMesEjecucionGasto(mesEjec != null ? Integer.valueOf(mesEjec) : null);
		}

		command.setTipoOperacion((tipoOperacion != null && tipoOperacion.equalsIgnoreCase(String.valueOf(FiseConstants.UPDATE))) ? FiseConstants.UPDATE : FiseConstants.ADD);

		if (command.getTipoOperacion().intValue() == FiseConstants.UPDATE) {
			logger.info("aquisito update");
			FiseFormato12BCPK id = new FiseFormato12BCPK();
			id.setAnoEjecucionGasto(anioEjec != null ? Integer.valueOf(anioEjec) : null);
			id.setAnoPresentacion(anio != null ? Integer.valueOf(anio) : null);
			id.setCodEmpresa(codEmpresa);
			id.setEtapa(etapa);
			id.setMesEjecucionGasto(mesEjec != null ? Integer.valueOf(mesEjec) : null);
			id.setMesPresentacion(mes != null ? Integer.valueOf(mes) : null);
			FiseFormato12BC cabeceraBean = formatoService.getFormatoCabeceraById(id);
			
			//
			String flagOper = commonService.obtenerEstadoProceso(id.getCodEmpresa(),FiseConstants.TIPO_FORMATO_12B,id.getAnoPresentacion(),id.getMesPresentacion(),id.getEtapa());
			command.setDescEstado(flagOper);
			
			command = Formato12BGartCommand.toCommandCabecera(cabeceraBean,command);
			List<FiseFormato12BD> lstFiseDetalle = formatoService.getLstFormatoDetalle(id);
			command = Formato12BGartCommand.toCommandDetalle(lstFiseDetalle, command);
			
		} 
		command.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));
		command.setTipoOperacion((tipoOperacion != null && tipoOperacion.equalsIgnoreCase(String.valueOf(FiseConstants.UPDATE))) ? FiseConstants.UPDATE : FiseConstants.ADD);
		command.setListaMes(fiseUtil.getMapaMeses());
		
		
		if( listaError!=null && listaError.size()>0){
			model.addAttribute("listaError", listaError);
		}
		model.addAttribute("msgTrans", msg);
		
		model.addAttribute("formato12BGartCommand", command);
          formato12BBusqueda=new Formato12BGartCommand();
          formato12BBusqueda.setAnioInicio(command.getAnioInicio());
          formato12BBusqueda.setAnioFin(command.getAnioFin());
          formato12BBusqueda.setMesInicio(command.getMesInicio());
          formato12BBusqueda.setMesFin(command.getMesFin());
          formato12BBusqueda.setEtapaBusqueda(command.getEtapaBusqueda());
          formato12BBusqueda.setCodEmpresaBusqueda(command.getCodEmpresaBusqueda());
          
          logger.info("ANIO INICIO CARGA NUEVO****"+command.getAnioInicio());
          logger.info("MES INICIO CARGA NUEVO****"+command.getMesInicio());
        
		
		return "formato12BDetalle";
	}

	/******/
	@ActionMapping(params="action=uploadFile")
	public void cargarDocumento(ActionRequest request,ActionResponse response,@ModelAttribute("formato12BCBean")Formato12BCBean bean){
		
		logger.info("--- cargar documento");
		Formato12BMensajeBean formatoMensaje = new Formato12BMensajeBean();
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(request);
		
		//String flagCarga = uploadPortletRequest.getParameter("flagCarga");
    	//String codEmpresaNew = uploadPortletRequest.getParameter("codigoEmpresa");
    	//String periodoEnvioPresNew = uploadPortletRequest.getParameter("periodoEnvio");
    	//String flagPeriodoEjecucion = uploadPortletRequest.getParameter("flagPeriodoEjecucion");
    	
    	String codEmpresaNew = "";
    	String periodoEnvioPresNew = "";
    	String anioPresNew = "";
		String mesPresNew = "";
		String etapaNew = "";
		
		String codEmpresaEdit = "";
    	String periodoEnvioPresEdit = "";
    	String anioPresEdit = "";
		String mesPresEdit = "";
		String etapaEdit = "";
    	
    	String tipoOperacion = uploadPortletRequest.getParameter("tipoOperacion");
		String typeFile = uploadPortletRequest.getParameter("typeFile");
		
		String anoEjecucion = "";//uploadPortletRequest.getParameter("txtanoEjecucionGasto");
		String mesEjecucion = "";//uploadPortletRequest.getParameter("txtmesEjecucionGasto");
		
		String flagPeriodoEjecucion = uploadPortletRequest.getParameter("flagPeriodoEjecucion");
		
		//recuperamos la pk en session solo cuando actualizo la carga de excel o text
        PortletRequest pRequest = (PortletRequest) request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
		
		 codEmpresaEdit = (String)pRequest.getPortletSession().getAttribute("codEmpresaEdit", PortletSession.APPLICATION_SCOPE);
		 anioPresEdit = (String)pRequest.getPortletSession().getAttribute("anoPresentacionEdit", PortletSession.APPLICATION_SCOPE);
		 mesPresEdit = (String)pRequest.getPortletSession().getAttribute("mesPresentacionEdit", PortletSession.APPLICATION_SCOPE);
		 String anioEjecEdit = (String)pRequest.getPortletSession().getAttribute("anoEjecucionEdit", PortletSession.APPLICATION_SCOPE);
		 String mesEjecEdit = (String)pRequest.getPortletSession().getAttribute("mesEjecucionEdit", PortletSession.APPLICATION_SCOPE);
		 etapaEdit = (String)pRequest.getPortletSession().getAttribute("etapaEdit", PortletSession.APPLICATION_SCOPE);
		
		String periodo = "";
		
		if(tipoOperacion != null && tipoOperacion.equalsIgnoreCase(String.valueOf(FiseConstants.UPDATE))){
			//codEmpresaEdit = uploadPortletRequest.getParameter("codEmpresaHidden");
			/*periodoEnvioPresEdit = uploadPortletRequest.getParameter("peridoDeclaracionHidden");
			if(periodoEnvioPresEdit!=null && periodoEnvioPresEdit.length()>6){
	    		anioPresEdit = periodoEnvioPresEdit.substring(0, 4);
	    		mesPresEdit = periodoEnvioPresEdit.substring(4, 6);
	    		etapaEdit = periodoEnvioPresEdit.substring(6, periodoEnvioPresEdit.length());
			}*/
			periodo = periodoEnvioPresEdit;
			bean.setCodigoEmpresa(codEmpresaEdit);
		}else{
			codEmpresaNew = uploadPortletRequest.getParameter("codEmpresa");
			periodoEnvioPresNew = uploadPortletRequest.getParameter("peridoDeclaracion");
			if(periodoEnvioPresNew!=null && periodoEnvioPresNew.length()>6){
	    		anioPresNew = periodoEnvioPresNew.substring(0, 4);
	    		mesPresNew = periodoEnvioPresNew.substring(4, 6);
	    		etapaNew = periodoEnvioPresNew.substring(6, periodoEnvioPresNew.length());
	    		if( "S".equals(flagPeriodoEjecucion) ){
	    			anoEjecucion = uploadPortletRequest.getParameter("anoEjecucionGasto");
	    			mesEjecucion = uploadPortletRequest.getParameter("mesEjecucionGasto");
				}else{
					anoEjecucion = anioPresNew;
					mesEjecucion = mesPresNew;
				}
			}
			periodo = periodoEnvioPresNew;
			bean.setCodigoEmpresa(codEmpresaNew);
		}
		
		FileEntry fileEntry=null;
		try{
		if( tipoOperacion.equals(""+FiseConstants.ADD) && typeFile.trim().equalsIgnoreCase(FiseConstants.TYPE_FILE_XLS+"") ){
			fileEntry=fiseUtil.subirDocumento(request, uploadPortletRequest, FiseConstants.TIPOARCHIVO_XLS);			
			formatoMensaje = readExcelFileNew(fileEntry, themeDisplay.getUser(), tipoOperacion, codEmpresaNew, anioPresNew, mesPresNew, anoEjecucion, mesEjecucion, etapaNew);
		}else if( tipoOperacion.equals(""+FiseConstants.UPDATE) && typeFile.trim().equalsIgnoreCase(FiseConstants.TYPE_FILE_XLS+"") ){
			fileEntry=fiseUtil.subirDocumento(request, uploadPortletRequest, FiseConstants.TIPOARCHIVO_XLS);
			formatoMensaje = readExcelFileNew(fileEntry, themeDisplay.getUser(), tipoOperacion, codEmpresaEdit, anioPresEdit, mesPresEdit, anioEjecEdit, mesEjecEdit, etapaEdit);
		}else if( tipoOperacion.equals(""+FiseConstants.ADD) && typeFile.trim().equalsIgnoreCase(FiseConstants.TYPE_FILE_TXT+"") ){
			fileEntry =fiseUtil.subirDocumentoTxt(request, uploadPortletRequest, FiseConstants.TIPOARCHIVO_TXT);			
			formatoMensaje =	readTxtFileNew(fileEntry, uploadPortletRequest, themeDisplay.getUser(), tipoOperacion, codEmpresaNew, anioPresNew, mesPresNew, anoEjecucion, mesEjecucion, etapaNew);
		}else if( tipoOperacion.equals(""+FiseConstants.UPDATE) && typeFile.trim().equalsIgnoreCase(FiseConstants.TYPE_FILE_TXT+"") ){
			fileEntry=fiseUtil.subirDocumentoTxt(request, uploadPortletRequest, FiseConstants.TIPOARCHIVO_TXT);
			formatoMensaje =	readTxtFileNew(fileEntry, uploadPortletRequest, themeDisplay.getUser(), tipoOperacion, codEmpresaEdit, anioPresEdit, mesPresEdit, anioEjecEdit, mesEjecEdit, etapaEdit);
		}
		}catch(FileMimeTypeException ex){
			
		}catch (Exception e) {			
		}
		
		//**************************
		
		if( formatoMensaje.getFiseFormato12BC()!=null ){
			
			response.setRenderParameter("action", "viewFormato");
			response.setRenderParameter("msgTrans", "OK");
			
			response.setRenderParameter("tipoOperacion", FiseConstants.UPDATE+"");

			response.setRenderParameter("codEmpresa", formatoMensaje.getFiseFormato12BC().getId().getCodEmpresa());
			response.setRenderParameter("anoPresentacion", ""+formatoMensaje.getFiseFormato12BC().getId().getAnoPresentacion());
			response.setRenderParameter("mesPresentacion", ""+formatoMensaje.getFiseFormato12BC().getId().getMesPresentacion());
			response.setRenderParameter("etapa", formatoMensaje.getFiseFormato12BC().getId().getEtapa());
			response.setRenderParameter("anoEjecucionGasto", ""+formatoMensaje.getFiseFormato12BC().getId().getAnoEjecucionGasto());
			response.setRenderParameter("mesEjecucionGasto", ""+formatoMensaje.getFiseFormato12BC().getId().getMesEjecucionGasto());
		}else{
			
			response.setRenderParameter("msgTrans", "ERROR");
			
			if((""+FiseConstants.ADD).equals(tipoOperacion)){
		
				response.setRenderParameter("action", "newFormato");
				response.setRenderParameter("tipoOperacion", FiseConstants.ADD+"");
				response.setRenderParameter("codEmpresa", codEmpresaNew);
				response.setRenderParameter("anoPresentacion", anioPresNew);
				response.setRenderParameter("mesPresentacion", mesPresNew);
				response.setRenderParameter("etapa", etapaNew);
				response.setRenderParameter("anoEjecucionGasto", anoEjecucion);
				response.setRenderParameter("mesEjecucionGasto", mesEjecucion);
			
			}else if((""+FiseConstants.UPDATE).equals(tipoOperacion)){
				
				response.setRenderParameter("action", "viewFormato");
				response.setRenderParameter("tipoOperacion", FiseConstants.UPDATE+"");
				response.setRenderParameter("codEmpresa", codEmpresaEdit);
				response.setRenderParameter("anoPresentacion", anioPresEdit);
				response.setRenderParameter("mesPresentacion", mesPresEdit);
				response.setRenderParameter("etapa", etapaEdit);
				response.setRenderParameter("anoEjecucionGasto", anioEjecEdit);
				response.setRenderParameter("mesEjecucionGasto", mesEjecEdit);
			}
			
			if(formatoMensaje.getListaMensajeError()!=null && formatoMensaje.getListaMensajeError().size()>0){
				pRequest.getPortletSession().setAttribute("listaError", formatoMensaje.getListaMensajeError(), PortletSession.APPLICATION_SCOPE);
			}
		}
		//limpiamos las variables en sesion utilizados para la edicion de archivos de carga
		pRequest.getPortletSession().setAttribute("codEmpresaEdit", "", PortletSession.APPLICATION_SCOPE);
		pRequest.getPortletSession().setAttribute("anoPresentacionEdit", "", PortletSession.APPLICATION_SCOPE);
		pRequest.getPortletSession().setAttribute("mesPresentacionEdit", "", PortletSession.APPLICATION_SCOPE);
		pRequest.getPortletSession().setAttribute("anoEjecucionEdit", "", PortletSession.APPLICATION_SCOPE);
		pRequest.getPortletSession().setAttribute("mesEjecucionEdit", "", PortletSession.APPLICATION_SCOPE);
		pRequest.getPortletSession().setAttribute("etapaEdit", "", PortletSession.APPLICATION_SCOPE);
		response.setRenderParameter("codEmpresaHidden", bean.getCodigoEmpresa());
		response.setRenderParameter("peridoDeclaracion", periodo);
		response.setRenderParameter("peridoDeclaracionHidden", periodo);
		
	}
	
	public Formato12BMensajeBean readExcelFileNew(FileEntry archivo, User user, String tipoOperacion, String codEmpresa, String anioPres, String mesPres, String anioIniVigencia, String anioFinVigencia, String etapaEdit) {
		
		//---------------------
		//FLAG CARGA:
		//	1: para registros nuevos
		//	2: para registros modificados
		//---------------------
		Formato12BMensajeBean formatoMensaje = new Formato12BMensajeBean();
		
		InputStream is=null;
		FiseFormato12BC objeto = null;
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
					logger.warn(mapaErrores.get(FiseConstants.COD_ERROR_F12B_3672));
					cont++;
					sMsg = sMsg + mapaErrores.get(FiseConstants.COD_ERROR_F12B_3672);
					throw new Exception(mapaErrores.get(FiseConstants.COD_ERROR_F12B_3672));
				}
				int nroHojaSelec=0;
				
				if (libro != null) {
					//el excel puede tener varias hojas, se tiene qie leer el total de hojas y encontrar la que necesitemos
					logger.info("nro de hojas:"+ libro.getNumberOfSheets());
					for (int sheetNro = 0; sheetNro < libro.getNumberOfSheets(); sheetNro++){
						logger.info("nombre de hoja "+sheetNro+":"+ libro.getSheetName(sheetNro));
						if( FiseConstants.NOMBRE_HOJA_FORMATO12B.equals(libro.getSheetName(sheetNro)) ){
							process = true;
							nroHojaSelec = sheetNro;
							break;
						}
					}
					logger.info("nro de hoja seleccionada "+nroHojaSelec);
					
					if(process){
						
						HSSFSheet hojaF12 = libro.getSheetAt(nroHojaSelec);
						//int nroFilas = hojaF12.getLastRowNum()+1;
						
						HSSFRow filaEmpresa = hojaF12.getRow(FiseConstants.NRO_FILA_CODEMPRESA_FORMATO12B);						
						HSSFRow filaAnioMes = hojaF12.getRow(FiseConstants.NRO_FILA_ANIOMES_FORMATO12B);	
						
						HSSFRow filaNroValesImp = hojaF12.getRow(FiseConstants.NRO_FILA_NROVALESIMP_FORMATO12B);				
						HSSFRow filaNroValesRep = hojaF12.getRow(FiseConstants.NRO_FILA_NROVALESREP_FORMATO12B);
						HSSFRow filaNroValesEntr = hojaF12.getRow(FiseConstants.NRO_FILA_NROVALESENTR_FORMATO12B);
						HSSFRow filaNroValesFis = hojaF12.getRow(FiseConstants.NRO_FILA_NROVALESFIS_FORMATO12B);
						HSSFRow filaNroValesDigit = hojaF12.getRow(FiseConstants.NRO_FILA_NROVALESDIGIT_FORMATO12B);
						HSSFRow filaNroAten = hojaF12.getRow(FiseConstants.NRO_FILA_NROTOTALATEN_FORMATO12B);
						HSSFRow filaGestAdm = hojaF12.getRow(FiseConstants.NRO_FILA_GESTADM_FORMATO12B);
						HSSFRow filaDesplPers = hojaF12.getRow(FiseConstants.NRO_FILA_DESPLPERSON_FORMATO12B);
						HSSFRow filaActivExtr = hojaF12.getRow(FiseConstants.NRO_FILA_ACTIVEXTR_FORMATO12B);	
						
						Formato12BCBean formulario = new Formato12BCBean();
						
						HSSFCell celdaEmpresa = filaEmpresa.getCell(FiseConstants.NRO_CELDA_EMPRESA_FORMATO12B);
						HSSFCell celdaAnio = filaAnioMes.getCell(FiseConstants.NRO_CELDA_ANIO_FORMATO12B);
						HSSFCell celdaMes = filaAnioMes.getCell(FiseConstants.NRO_CELDA_MES_FORMATO12B);
						//RURAL
						HSSFCell celdaNroValesImpR = filaNroValesImp.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO12B);				
						HSSFCell celdaNroValesRepR = filaNroValesRep.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO12B);
						HSSFCell celdaNroValesEntrR = filaNroValesEntr.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO12B);
						HSSFCell celdaNroValesFisR = filaNroValesFis.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO12B);
						HSSFCell celdaNroValesDigitR = filaNroValesDigit.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO12B);
						HSSFCell celdaNroAtenR = filaNroAten.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO12B);
						HSSFCell celdaGestAdmR = filaGestAdm.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO12B);
						HSSFCell celdaDesplPersR = filaDesplPers.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO12B);
						HSSFCell celdaActivExtrR = filaActivExtr.getCell(FiseConstants.NRO_CELDA_RURAL_FORMATO12B);	
						//PROVINCIA
						HSSFCell celdaNroValesImpP = filaNroValesImp.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO12B);				
						HSSFCell celdaNroValesRepP = filaNroValesRep.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO12B);
						HSSFCell celdaNroValesEntrP = filaNroValesEntr.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO12B);
						HSSFCell celdaNroValesFisP = filaNroValesFis.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO12B);
						HSSFCell celdaNroValesDigitP = filaNroValesDigit.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO12B);
						HSSFCell celdaNroAtenP = filaNroAten.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO12B);
						HSSFCell celdaGestAdmP = filaGestAdm.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO12B);
						HSSFCell celdaDesplPersP = filaDesplPers.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO12B);
						HSSFCell celdaActivExtrP = filaActivExtr.getCell(FiseConstants.NRO_CELDA_PROVINCIA_FORMATO12B);	
						//LIMA
						HSSFCell celdaNroValesImpL = filaNroValesImp.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO12B);				
						HSSFCell celdaNroValesRepL = filaNroValesRep.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO12B);
						HSSFCell celdaNroValesEntrL = filaNroValesEntr.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO12B);
						HSSFCell celdaNroValesFisL = filaNroValesFis.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO12B);
						HSSFCell celdaNroValesDigitL = filaNroValesDigit.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO12B);
						HSSFCell celdaNroAtenL = filaNroAten.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO12B);
						HSSFCell celdaGestAdmL = filaGestAdm.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO12B);
						HSSFCell celdaDesplPersL = filaDesplPers.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO12B);
						HSSFCell celdaActivExtrL = filaActivExtr.getCell(FiseConstants.NRO_CELDA_LIMA_FORMATO12B);	
						
						
						//tipos
						if( HSSFCell.CELL_TYPE_STRING == celdaEmpresa.getCellType()  ){
							formulario.setCodigoEmpresa(celdaEmpresa.toString());
						}else if( HSSFCell.CELL_TYPE_FORMULA == celdaEmpresa.getCellType()  ){
							formulario.setCodigoEmpresa(celdaEmpresa.getRichStringCellValue().toString());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaEmpresa.getCellType()  ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3673);
						}else{
							formulario.setCodigoEmpresa("");
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3674);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaAnio.getCellType()  ){
							formulario.setAnioPresent(new Double(celdaAnio.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaAnio.getCellType()  ){
							formulario.setAnioPresent(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3675);
						}else{
							formulario.setAnioPresent(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3676);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaMes.getCellType()  ){
							formulario.setMesPresent(new Double(celdaMes.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaMes.getCellType()  ){
							formulario.setMesPresent(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3677);
						}else{
							formulario.setMesPresent(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3678);
						}
						//valores
						//RURAL
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroValesImpR.getCellType()  ){
							formulario.setNroValeImpR(new Double(celdaNroValesImpR.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroValesImpR.getCellType()  ){
							formulario.setNroValeImpR(0L);
						}else{
							formulario.setNroValeImpR(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3679);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroValesRepR.getCellType()  ){
							formulario.setNroValReparDomicR(new Double(celdaNroValesRepR.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroValesRepR.getCellType()  ){
							formulario.setNroValReparDomicR(0L);
						}else{
							formulario.setNroValReparDomicR(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3680);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroValesEntrR.getCellType()  ){
							formulario.setNroValEntDisElR(new Double(celdaNroValesEntrR.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroValesEntrR.getCellType()  ){
							formulario.setNroValEntDisElR(0L);
						}else{
							formulario.setNroValEntDisElR(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3681);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroValesFisR.getCellType()  ){
							formulario.setNroValFisiCanjR(new Double(celdaNroValesFisR.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroValesFisR.getCellType()  ){
							formulario.setNroValFisiCanjR(0L);
						}else{
							formulario.setNroValFisiCanjR(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3682);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroValesDigitR.getCellType()  ){
							formulario.setNroValDigitCanjR(new Double(celdaNroValesDigitR.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroValesDigitR.getCellType()  ){
							formulario.setNroValDigitCanjR(0L);
						}else{
							formulario.setNroValDigitCanjR(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3683);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroAtenR.getCellType()  ){
							formulario.setNroAtencionesR(new Double(celdaNroAtenR.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroAtenR.getCellType()  ){
							formulario.setNroAtencionesR(0L);
						}else{
							formulario.setNroAtencionesR(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3684);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaGestAdmR.getCellType()  ){
							formulario.setGestionAdmR(new BigDecimal(celdaGestAdmR.getNumericCellValue()).setScale(2, RoundingMode.HALF_UP));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaGestAdmR.getCellType()  ){
							formulario.setGestionAdmR(new BigDecimal(0.00));
						}else{
							formulario.setGestionAdmR(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3685);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaDesplPersR.getCellType()  ){
							formulario.setDesplPersonalR(new BigDecimal(celdaDesplPersR.getNumericCellValue()).setScale(2, RoundingMode.HALF_UP));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaDesplPersR.getCellType()  ){
							formulario.setDesplPersonalR(new BigDecimal(0.00));
						}else{
							formulario.setDesplPersonalR(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3686);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaActivExtrR.getCellType()  ){
							formulario.setActivExtraordR(new BigDecimal(celdaActivExtrR.getNumericCellValue()).setScale(2, RoundingMode.HALF_UP));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaActivExtrR.getCellType()  ){
							formulario.setActivExtraordR(new BigDecimal(0.00));
						}else{
							formulario.setActivExtraordR(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3687);
						}
						
						//PROVINCIA
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroValesImpP.getCellType()  ){
							formulario.setNroValeImpP(new Double(celdaNroValesImpP.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroValesImpP.getCellType()  ){
							formulario.setNroValeImpP(0L);
						}else{
							formulario.setNroValeImpP(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3688);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroValesRepP.getCellType()  ){
							formulario.setNroValReparDomicP(new Double(celdaNroValesRepP.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroValesRepP.getCellType()  ){
							formulario.setNroValReparDomicP(0L);
						}else{
							formulario.setNroValReparDomicP(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3689);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroValesEntrP.getCellType()  ){
							formulario.setNroValEntDisElP(new Double(celdaNroValesEntrP.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroValesEntrP.getCellType()  ){
							formulario.setNroValEntDisElP(0L);
						}else{
							formulario.setNroValEntDisElP(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3690);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroValesFisP.getCellType()  ){
							formulario.setNroValFisiCanjP(new Double(celdaNroValesFisP.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroValesFisP.getCellType()  ){
							formulario.setNroValFisiCanjP(0L);
						}else{
							formulario.setNroValFisiCanjP(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3691);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroValesDigitP.getCellType()  ){
							formulario.setNroValDigitCanjP(new Double(celdaNroValesDigitP.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroValesDigitP.getCellType()  ){
							formulario.setNroValDigitCanjP(0L);
						}else{
							formulario.setNroValDigitCanjP(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3692);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroAtenP.getCellType()  ){
							formulario.setNroAtencionesP(new Double(celdaNroAtenP.getNumericCellValue()).longValue());
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroAtenP.getCellType()  ){
							formulario.setNroAtencionesP(0L);
						}else{
							formulario.setNroAtencionesP(0L);
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3693);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaGestAdmP.getCellType()  ){
							formulario.setGestionAdmP(new BigDecimal(celdaGestAdmP.getNumericCellValue()).setScale(2, RoundingMode.HALF_UP));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaGestAdmP.getCellType()  ){
							formulario.setGestionAdmP(new BigDecimal(0.00));
						}else{
							formulario.setGestionAdmP(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3694);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaDesplPersP.getCellType()  ){
							formulario.setDesplPersonalP(new BigDecimal(celdaDesplPersP.getNumericCellValue()).setScale(2, RoundingMode.HALF_UP));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaDesplPersP.getCellType()  ){
							formulario.setDesplPersonalP(new BigDecimal(0.00));
						}else{
							formulario.setDesplPersonalP(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3695);
						}
						if( HSSFCell.CELL_TYPE_NUMERIC == celdaActivExtrP.getCellType()  ){
							formulario.setActivExtraordP(new BigDecimal(celdaActivExtrP.getNumericCellValue()).setScale(2, RoundingMode.HALF_UP));
						}else if( HSSFCell.CELL_TYPE_BLANK == celdaActivExtrP.getCellType()  ){
							formulario.setActivExtraordP(new BigDecimal(0.00));
						}else{
							formulario.setActivExtraordP(new BigDecimal(0.00));
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3696);
						}
						
						if( FiseConstants.COD_EMPRESA_EDELNOR.equals(codEmpresa) || FiseConstants.COD_EMPRESA_LUZ_SUR.equals(codEmpresa) ){
							//LIMA
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroValesImpL.getCellType()  ){
								formulario.setNroValeImpL(new Double(celdaNroValesImpL.getNumericCellValue()).longValue());
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroValesImpL.getCellType()  ){
								formulario.setNroValeImpL(0L);
							}else{
								formulario.setNroValeImpL(0L);
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3697);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroValesRepL.getCellType()  ){
								formulario.setNroValReparDomicL(new Double(celdaNroValesRepL.getNumericCellValue()).longValue());
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroValesRepL.getCellType()  ){
								formulario.setNroValReparDomicL(0L);
							}else{
								formulario.setNroValReparDomicL(0L);
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3698);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroValesEntrL.getCellType()  ){
								formulario.setNroValEntDisElL(new Double(celdaNroValesEntrL.getNumericCellValue()).longValue());
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroValesEntrL.getCellType()  ){
								formulario.setNroValEntDisElL(0L);
							}else{
								formulario.setNroValEntDisElL(0L);
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3699);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroValesFisL.getCellType()  ){
								formulario.setNroValFisiCanjL(new Double(celdaNroValesFisL.getNumericCellValue()).longValue());
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroValesFisL.getCellType()  ){
								formulario.setNroValFisiCanjL(0L);
							}else{
								formulario.setNroValFisiCanjL(0L);
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3700);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroValesDigitL.getCellType()  ){
								formulario.setNroValDigitCanjL(new Double(celdaNroValesDigitL.getNumericCellValue()).longValue());
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroValesDigitL.getCellType()  ){
								formulario.setNroValDigitCanjL(0L);
							}else{
								formulario.setNroValDigitCanjL(0L);
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3701);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaNroAtenL.getCellType()  ){
								formulario.setNroAtencionesL(new Double(celdaNroAtenL.getNumericCellValue()).longValue());
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaNroAtenL.getCellType()  ){
								formulario.setNroAtencionesL(0L);
							}else{
								formulario.setNroAtencionesL(0L);
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3702);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaGestAdmL.getCellType()  ){
								formulario.setGestionAdmL(new BigDecimal(celdaGestAdmL.getNumericCellValue()).setScale(2, RoundingMode.HALF_UP));
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaGestAdmL.getCellType()  ){
								formulario.setGestionAdmL(new BigDecimal(0.00));
							}else{
								formulario.setGestionAdmL(new BigDecimal(0.00));
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3703);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaDesplPersL.getCellType()  ){
								formulario.setDesplPersonalL(new BigDecimal(celdaDesplPersL.getNumericCellValue()).setScale(2, RoundingMode.HALF_UP));
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaDesplPersL.getCellType()  ){
								formulario.setDesplPersonalL(new BigDecimal(0.00));
							}else{
								formulario.setDesplPersonalL(new BigDecimal(0.00));
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3704);
							}
							if( HSSFCell.CELL_TYPE_NUMERIC == celdaActivExtrL.getCellType()  ){
								formulario.setActivExtraordL(new BigDecimal(celdaActivExtrL.getNumericCellValue()).setScale(2, RoundingMode.HALF_UP));
							}else if( HSSFCell.CELL_TYPE_BLANK == celdaActivExtrL.getCellType()  ){
								formulario.setActivExtraordL(new BigDecimal(0.00));
							}else{
								formulario.setActivExtraordL(new BigDecimal(0.00));
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3705);
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
						
						
						/**validacion de estructura de campos*/
						//COD EMPRESA - 4
						if( !FormatoUtil.validaCampoString(formulario.getCodigoEmpresa(),4) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3674);
						}
						//ANO PRESENTACION - 4 
						if( !FormatoUtil.validaCampoNumeroEntero(formulario.getAnioPresent(),4) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3676);
						}
						//MES PRESENTACION - 2
						if( !FormatoUtil.validaCampoNumeroEntero(formulario.getMesPresent(),2) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3678);
						}
						
						//----RURAL
						
						//NRO VALES IMPR - 10
						if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroValeImpR(),10) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3679);
						}
						//NRO VALES REPART - 10
						if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroValReparDomicR(),10) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3680);
						}
						//NO VALES ENTR - 10
						if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroValEntDisElR(),10) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3681);
						}
						//NRO VALES FISICOS - 10
						if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroValFisiCanjR(),10) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3682);
						}
						//NRO VALES DIGIT - 10
						if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroValDigitCanjR(),10) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3683);
						}
						//NRO ATENCIONES - 10
						if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroAtencionesR(),10) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3684);
						}
						//GEST ADM - 18,2
						if( !FormatoUtil.validaCampoNumeroDecimal(formulario.getGestionAdmR(),18,2) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3685);
						}
						//DESPL PERSO - 18,2
						if( !FormatoUtil.validaCampoNumeroDecimal(formulario.getDesplPersonalR(),18,2) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3686);
						}
						//ACTIV EXTRAORD - 18,2
						if( !FormatoUtil.validaCampoNumeroDecimal(formulario.getActivExtraordR(),18,2) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3687);
						}
						//-----PROVINCIA
						
						//NRO VALES IMPR - 10
						if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroValeImpP(),10) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3688);
						}
						//NRO VALES REPART - 10
						if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroValReparDomicP(),10) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3689);
						}
						//NO VALES ENTR - 10
						if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroValEntDisElP(),10) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3690);
						}
						//NRO VALES FISICOS - 10
						if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroValFisiCanjP(),10) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3691);
						}
						//NRO VALES DIGIT - 10
						if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroValDigitCanjP(),10) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3692);
						}
						//NRO ATENCIONES - 10
						if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroAtencionesP(),10) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3693);
						}
						//GEST ADM - 18,2
						if( !FormatoUtil.validaCampoNumeroDecimal(formulario.getGestionAdmP(),18,2) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3694);
						}
						//DESPL PERSO - 18,2
						if( !FormatoUtil.validaCampoNumeroDecimal(formulario.getDesplPersonalP(),18,2) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3695);
						}
						//ACTIV EXTRAORD - 18,2
						if( !FormatoUtil.validaCampoNumeroDecimal(formulario.getActivExtraordP(),18,2) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3696);
						}
						
						//----LIMA
						
						//NRO VALES IMPR - 10
						if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroValeImpL(),10) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3697);
						}
						//NRO VALES REPART - 10
						if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroValReparDomicL(),10) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3698);
						}
						//NO VALES ENTR - 10
						if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroValEntDisElL(),10) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3699);
						}
						//NRO VALES FISICOS - 10
						if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroValFisiCanjL(),10) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3700);
						}
						//NRO VALES DIGIT - 10
						if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroValDigitCanjL(),10) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3701);
						}
						//NRO ATENCIONES - 10
						if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroAtencionesL(),10) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3702);
						}
						//GEST ADM - 18,2
						if( !FormatoUtil.validaCampoNumeroDecimal(formulario.getGestionAdmL(),18,2) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3703);
						}
						//DESPL PERSO - 18,2
						if( !FormatoUtil.validaCampoNumeroDecimal(formulario.getDesplPersonalL(),18,2) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3704);
						}
						//ACTIV EXTRAORD - 18,2
						if( !FormatoUtil.validaCampoNumeroDecimal(formulario.getActivExtraordL(),18,2) ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3705);
						}
						
						
						/***/
						
						/**verificar*/
						formulario.setAnioEjecuc(formulario.getAnioPresent());
						formulario.setMesEjecuc(formulario.getMesPresent());
						/***/
						
						if( FiseConstants.BLANCO.equals(sMsg) ){
							
							
							//obtenemos los costos unitarios del formato padre
							FiseFormato14BD detalleRuralPadre = null;
				  			FiseFormato14BD detalleProvinciaPadre = null;
				  			FiseFormato14BD detalleLimaPadre = null;
				  			
				  			detalleRuralPadre = formato14Service.obtenerFormato14BDVigente(formulario.getCodigoEmpresa(), formulario.getAnioPresent(), FiseConstants.ZONABENEF_RURAL_COD);
				  			if( detalleRuralPadre!=null ){
				  				formulario.setCostoUnitValImpR(detalleRuralPadre.getCostoUnitarioImpresionVales());
				  				formulario.setCostoUnitValReparDomicR(detalleRuralPadre.getCostoUnitReprtoValeDomici());
				  				formulario.setCostoUnitValEntDisElR(detalleRuralPadre.getCostoUnitEntregaValDisEl());
				  				formulario.setCostoUnitValFisiCanjR(detalleRuralPadre.getCostoUnitCanjeLiqValFisi());
				  				formulario.setCostoUnitValDigitCanjR(detalleRuralPadre.getCostoUnitCanjeValDigital());
				  				formulario.setCostoUnitAtencionesR(detalleRuralPadre.getCostoUnitarioPorAtencion());
				  			}else{
				  				formulario.setCostoUnitValImpR(new BigDecimal(0.00));
				  				formulario.setCostoUnitValReparDomicR(new BigDecimal(0.00));
				  				formulario.setCostoUnitValEntDisElR(new BigDecimal(0.00));
				  				formulario.setCostoUnitValFisiCanjR(new BigDecimal(0.00));
				  				formulario.setCostoUnitValDigitCanjR(new BigDecimal(0.00));
				  				formulario.setCostoUnitAtencionesR(new BigDecimal(0.00));
				  			}
				  			detalleProvinciaPadre = formato14Service.obtenerFormato14BDVigente(formulario.getCodigoEmpresa(), formulario.getAnioPresent(), FiseConstants.ZONABENEF_PROVINCIA_COD);
				  			if( detalleProvinciaPadre!=null ){
				  				formulario.setCostoUnitValImpP(detalleProvinciaPadre.getCostoUnitarioImpresionVales());
				  				formulario.setCostoUnitValReparDomicP(detalleProvinciaPadre.getCostoUnitReprtoValeDomici());
				  				formulario.setCostoUnitValEntDisElP(detalleProvinciaPadre.getCostoUnitEntregaValDisEl());
				  				formulario.setCostoUnitValFisiCanjP(detalleProvinciaPadre.getCostoUnitCanjeLiqValFisi());
				  				formulario.setCostoUnitValDigitCanjP(detalleProvinciaPadre.getCostoUnitCanjeValDigital());
				  				formulario.setCostoUnitAtencionesP(detalleProvinciaPadre.getCostoUnitarioPorAtencion());
				  			}else{
				  				formulario.setCostoUnitValImpP(new BigDecimal(0.00));
				  				formulario.setCostoUnitValReparDomicP(new BigDecimal(0.00));
				  				formulario.setCostoUnitValEntDisElP(new BigDecimal(0.00));
				  				formulario.setCostoUnitValFisiCanjP(new BigDecimal(0.00));
				  				formulario.setCostoUnitValDigitCanjP(new BigDecimal(0.00));
				  				formulario.setCostoUnitAtencionesP(new BigDecimal(0.00));
				  			}
				  			detalleLimaPadre = formato14Service.obtenerFormato14BDVigente(formulario.getCodigoEmpresa(), formulario.getAnioPresent(), FiseConstants.ZONABENEF_LIMA_COD);
				  			if( detalleLimaPadre!=null ){
				  				formulario.setCostoUnitValImpL(detalleLimaPadre.getCostoUnitarioImpresionVales());
				  				formulario.setCostoUnitValReparDomicL(detalleLimaPadre.getCostoUnitReprtoValeDomici());
				  				formulario.setCostoUnitValEntDisElL(detalleLimaPadre.getCostoUnitEntregaValDisEl());
				  				formulario.setCostoUnitValFisiCanjL(detalleLimaPadre.getCostoUnitCanjeLiqValFisi());
				  				formulario.setCostoUnitValDigitCanjL(detalleLimaPadre.getCostoUnitCanjeValDigital());
				  				formulario.setCostoUnitAtencionesL(detalleLimaPadre.getCostoUnitarioPorAtencion());
				  			}else{
				  				formulario.setCostoUnitValImpL(new BigDecimal(0.00));
				  				formulario.setCostoUnitValReparDomicL(new BigDecimal(0.00));
				  				formulario.setCostoUnitValEntDisElL(new BigDecimal(0.00));
				  				formulario.setCostoUnitValFisiCanjL(new BigDecimal(0.00));
				  				formulario.setCostoUnitValDigitCanjL(new BigDecimal(0.00));
				  				formulario.setCostoUnitAtencionesL(new BigDecimal(0.00));
				  			}
							//
							
							//
							formulario.setUsuario(user.getLogin());
							formulario.setTerminal(user.getLoginIP());
							formulario.setTipoArchivo(FiseConstants.TIPOARCHIVO_XLS);
							formulario.setNombreArchivo(archivo.getTitle());
							//
							formulario.setEtapa(etapaEdit);
							
							if(codEmpresa.trim().equals(formulario.getCodigoEmpresa().trim()) &&
									anioPres.equals(String.valueOf(formulario.getAnioPresent())) &&
									Long.parseLong(mesPres) == formulario.getMesPresent() 
									){
								
								if( (""+FiseConstants.ADD).equals(tipoOperacion) ){
									objeto = formatoService.registrarFormato12BC(formulario);
								}else if( (""+FiseConstants.UPDATE).equals(tipoOperacion) ){
									FiseFormato12BC formatoModif = new FiseFormato12BC();
									FiseFormato12BCPK id = new FiseFormato12BCPK();
									id.setCodEmpresa(formulario.getCodigoEmpresa());
									id.setAnoPresentacion((int)formulario.getAnioPresent());
									id.setMesPresentacion((int)formulario.getMesPresent());
									id.setAnoEjecucionGasto(id.getAnoPresentacion());
									id.setMesEjecucionGasto(id.getMesPresentacion());
									id.setEtapa(formulario.getEtapa());
									formatoModif = formatoService.getFormatoCabeceraById(id);
									objeto = formatoService.modificarFormato12BC(formulario, formatoModif);
								}
								
							}else{
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3706);
							}
						}
						
					}else{
						//--logger.warn(mapaErrores.get(FiseConstants.COD_ERROR_F12_20));
						cont++;
						sMsg = sMsg + "No se encuentra la hoja "+FiseConstants.NOMBRE_HOJA_FORMATO12B+" en el archivo cargado";
						throw new Exception("No se encuentra la hoja "+FiseConstants.NOMBRE_HOJA_FORMATO12B+" en el archivo cargado");
					}

				}
					
			}
			is.close();

		} catch (Exception e) {
			logger.error("Error al leer el archivo excel.",e);
			String error = e.getMessage();
			if( FiseConstants.BLANCO.equals(error.trim()) ){
				error = mapaErrores.get(FiseConstants.COD_ERROR_3633);
			}
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
		formatoMensaje.setFiseFormato12BC(objeto);
		if(listaError.size()>0)
			formatoMensaje.setListaMensajeError(listaError);
		
		return formatoMensaje;
	}
	
	public Formato12BMensajeBean readTxtFileNew(FileEntry archivo, UploadPortletRequest uploadPortletRequest, User user, String tipoOperacion, String codEmpresaEdit, String anioPresEdit, String mesPresEdit, String anioEjecucion, String mesEjecucion, String etapaEdit) {
		
		//---------------------
		//FLAG CARGA:
		//	3: para registros nuevos
		//	4: para registros modificados
		//---------------------
		Formato12BMensajeBean formatoMensaje = new Formato12BMensajeBean();
		InputStream is=null;
		FiseFormato12BC objeto = null;
		List<MensajeErrorBean> listaError = new ArrayList<MensajeErrorBean>();
		String sMsg = "";
		int cont = 0;
		List<CfgCampo> listaCampo = null;
		try{
			CfgTabla tabla = new CfgTabla();
			tabla.setIdTabla(FiseConstants.ID_TABLA_FORMATO12B);
			listaCampo = campoService.listarCamposByTabla(tabla);
			if( listaCampo != null ){
				int longitudMaxima = campoService.longitudMaximaRegistro(listaCampo);
				int posicionCodEmpresa = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_COD_EMPRESA);
				int posicionAnioPresentacion = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_ANO_PRESENTACION);
				int posicionMesPresentacion = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_MES_PRESENTACION);
				int posicionAnioEjecucion = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_ANO_EJECUCION);
				int posicionMesEjecucion = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_MES_EJECUCION);
				int posicionZonaBenef = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_ZONA_BENEFICIARIO);
				
				int posicionNroValesImp = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_NUMERO_VALES_IMPRESO_F12B);
				int posicionTotalImpVales = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_COSTO_TOTAL_IMPRESION_VALE_F12B);
				int posicionNroValesRep = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_NUMERO_VALES_REPARTIDOS_DOMI_F12B);
				int posicionTotalRepVales = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_COSTO_TOTAL_REPARTO_VALES_DOMI_F12B);
				int posicionNroValesEntr = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_NUMERO_VALES_ENTREGADO_DIS_EL_F12B);
				int posicionTotalEntrVales = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_COSTO_TOTAL_ENTREGA_VAL_DIS_EL_F12B);
				int posicionNroValesFis = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_NUMERO_VALES_FISICOS_CANJEADOS_F12B);
				int posicionTotalCanjeValeFis = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_COSTO_TOTAL_CANJE_LIQ_VALE_FIS_F12B);
				int posicionNroValesDig = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_NUMERO_VALES_DIGITAL_CANJEADOS_F12B);
				int posicionTotalCanjeValeDig = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_COSTO_TOTAL_CANJE_LIQ_VALE_DIG_F12B);
				int posicionNroAten = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_NUMERO_ATENCIONES_F12B);
				int posicionTotalAten = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_COSTO_TOTAL_ATENCION_CONS_RECL_F12B);
				int posicionTotalGestAdm = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_TOTAL_GESTION_ADMINISTRATIVA_F12B);
				int posicionTotalDesplPers = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_TOTAL_DESPLAZAMIENTO_PERSONAL_F12B);
				int posicionTotalActivExtr = campoService.obtenerPosicionFinalCampo(listaCampo, FiseConstants.NOMBRE_TOTAL_ACTIVIDADES_EXTRAORD_F12B);
				
				String sCurrentLine;
				is=uploadPortletRequest.getFileAsStream("archivoExcel");
				
				String nombreIdeal = FormatoUtil.nombreArchivoCargaTxt(Long.parseLong(anioPresEdit), Long.parseLong(mesPresEdit), codEmpresaEdit, FiseConstants.TIPO_FORMATO_12B);
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
								sMsg = fiseUtil.agregarErrorBeanConMensajeEnFila(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3707, cont);
							}
						}/*else{
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F14B_3040);
						}*/
						sCurrentLine = br.readLine();
						if( cont>3 ){
							cont++;
							sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3721);
							break;
						}
					}
					if(cont==0){
						cont++;
						sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3709);
					}
					
					String key1,key2,key3,key4,key5="";//,key6="";
					if( listaDetalleTxt.size()>0 ){
						key1 = listaDetalleTxt.get(0).substring(0, posicionCodEmpresa).trim();
						key2 = listaDetalleTxt.get(0).substring(posicionCodEmpresa, posicionAnioPresentacion).trim();
						key3 = listaDetalleTxt.get(0).substring(posicionAnioPresentacion, posicionMesPresentacion).trim();
						key4 = listaDetalleTxt.get(0).substring(posicionMesPresentacion, posicionAnioEjecucion).trim();
						key5 = listaDetalleTxt.get(0).substring(posicionAnioEjecucion, posicionMesEjecucion).trim();
						boolean process = true;
						Set<String> zonaSet = new java.util.HashSet<String>();
						for (String s : listaDetalleTxt) {
							String codEmp = s.substring(0, posicionCodEmpresa).trim();
							String anioPres = s.substring(posicionCodEmpresa, posicionAnioPresentacion).trim();
							String mesPres = s.substring(posicionAnioPresentacion, posicionMesPresentacion) ;
							String anioEje = s.substring(posicionMesPresentacion, posicionAnioEjecucion) ;
							String mesEje = s.substring(posicionAnioEjecucion, posicionMesEjecucion) ;
							
							String zonaBenef = s.substring(posicionMesEjecucion, posicionZonaBenef).trim();
							
							if( key1.equals(codEmp) && key2.equals(anioPres) && key3.equals(mesPres) && key4.equals(anioEje) && key5.equals(mesEje) ){
								
								if( FiseConstants.ZONABENEF_RURAL_COD_STRING.equals(zonaBenef) ||
										FiseConstants.ZONABENEF_PROVINCIA_COD_STRING.equals(zonaBenef) ||
										FiseConstants.ZONABENEF_LIMA_COD_STRING.equals(zonaBenef) 
										){
									if( zonaSet.contains(zonaBenef) ){
										process=false;
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3708);
										break;
									}else{
										zonaSet.add(zonaBenef);
										process=true;
									}
								}else{
									process=false;
									cont++;
									sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3710);
									break;
								}
								
							}else{
								process=false;
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3722);
								break;
							}
							
						}
						if(process){
							
							Formato12BCBean formulario = new Formato12BCBean();
							//nuevamente recorremos la lista para armar los objetos
							formulario.setCodigoEmpresa(key1);
							formulario.setAnioPresent(Long.parseLong(key2));
							formulario.setMesPresent(Long.parseLong(key3));
							formulario.setAnioEjecuc(Long.parseLong(key4));
							formulario.setMesEjecuc(Long.parseLong(key5));
							logger.error("key 1:  "+key1);
							logger.error("key 2:  "+key2);
							logger.error("key 3:  "+key3);
							logger.error("key 4:  "+key4);
							logger.error("key 5:  "+key5);
							logger.error("empresa  :  "+codEmpresaEdit);
							logger.error("anio pres:  "+anioPresEdit);
							logger.error("mes pres:  "+mesPresEdit);							
							logger.error("anio ejec:  "+anioEjecucion);
							logger.error("mes ejec:  "+mesEjecucion);
							if(codEmpresaEdit.trim().equals(key1.trim()) &&
									anioPresEdit.equals(key2) &&
									Integer.parseInt(mesPresEdit)==Integer.parseInt(key3) && 
									anioEjecucion.equals(key4) &&
									Integer.parseInt(mesEjecucion)==Integer.parseInt(key5) 
									){		
								
								for (String s : listaDetalleTxt) {
									String zonaBenef = s.substring(posicionMesEjecucion, posicionZonaBenef).trim();
									
									String nroValesImp = s.substring(posicionZonaBenef, posicionNroValesImp).trim();
									String nroValesRep = s.substring(posicionTotalImpVales, posicionNroValesRep).trim();
									String nroValesEntr = s.substring(posicionTotalRepVales, posicionNroValesEntr).trim();
									String nroValesFis = s.substring(posicionTotalEntrVales, posicionNroValesFis).trim();
									String nroValesDig = s.substring(posicionTotalCanjeValeFis, posicionNroValesDig).trim();
									String nroAten = s.substring(posicionTotalCanjeValeDig, posicionNroAten).trim();
									String totalGestAdm = s.substring(posicionTotalAten, posicionTotalGestAdm).trim();
									String totalDesplPers = s.substring(posicionTotalGestAdm, posicionTotalDesplPers).trim();
									String totalActivExtr = s.substring(posicionTotalDesplPers, posicionTotalActivExtr).trim();
									
									//validaciones de consistencia
									
									if( !FormatoUtil.validarCampoLongEnteroPositivoTxt(nroValesImp) ){
										//el campo nroValesImp no corresponde al tipo de dato correcto
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3711);
									}
									if( !FormatoUtil.validarCampoLongEnteroPositivoTxt(nroValesRep) ){
										//el campo nroValesRep no corresponde al tipo de dato correcto
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3712);
									}
									if( !FormatoUtil.validarCampoLongEnteroPositivoTxt(nroValesEntr) ){
										//el campo nroValesEntr no corresponde al tipo de dato correcto
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3713);
									}
									if( !FormatoUtil.validarCampoLongEnteroPositivoTxt(nroValesFis) ){
										//el campo nroValesFis no corresponde al tipo de dato correcto
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3714);
									}
									if( !FormatoUtil.validarCampoLongEnteroPositivoTxt(nroValesDig) ){
										//el campo nroValesFis no corresponde al tipo de dato correcto
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3715);
									}
									if( !FormatoUtil.validarCampoLongEnteroPositivoTxt(nroAten) ){
										//el campo nroAten no corresponde al tipo de dato correcto
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3716);
									}
									if( !FormatoUtil.validarCampoBigDecimalPositivoTxt(totalGestAdm) ){
										//el campo utilMatOfic no corresponde al tipo de dato correcto
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3717);
									}
									if( !FormatoUtil.validarCampoBigDecimalPositivoTxt(totalDesplPers) ){
										//el campo utilMatOfic no corresponde al tipo de dato correcto
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3718);
									}
									if( !FormatoUtil.validarCampoBigDecimalPositivoTxt(totalActivExtr) ){
										//el campo utilMatOfic no corresponde al tipo de dato correcto
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3719);
									}
									
									
									//
									if( FiseConstants.BLANCO.equals(sMsg) ){
										
							  			FiseFormato14BD detalle = formato14Service.obtenerFormato14BDVigente(key1, Long.parseLong(key2), Long.parseLong(zonaBenef));
							  			
										if( FiseConstants.ZONABENEF_RURAL_COD == Long.parseLong(zonaBenef) ){
											formulario.setNroValeImpR(Long.parseLong(nroValesImp));
											formulario.setNroValReparDomicR(Long.parseLong(nroValesRep));
											formulario.setNroValEntDisElR(Long.parseLong(nroValesEntr));
											formulario.setNroValFisiCanjR(Long.parseLong(nroValesFis));
											formulario.setNroValDigitCanjR(Long.parseLong(nroValesFis));
											formulario.setNroAtencionesR(Long.parseLong(nroAten));
											formulario.setGestionAdmR(new BigDecimal(totalGestAdm));
											formulario.setDesplPersonalR(new BigDecimal(totalDesplPers));
											formulario.setActivExtraordR(new BigDecimal(totalActivExtr));
											
											if( detalle!=null ){
								  				formulario.setCostoUnitValImpR(detalle.getCostoUnitarioImpresionVales());
								  				formulario.setCostoUnitValReparDomicR(detalle.getCostoUnitReprtoValeDomici());
								  				formulario.setCostoUnitValEntDisElR(detalle.getCostoUnitEntregaValDisEl());
								  				formulario.setCostoUnitValFisiCanjR(detalle.getCostoUnitCanjeLiqValFisi());
								  				formulario.setCostoUnitValDigitCanjR(detalle.getCostoUnitCanjeValDigital());
								  				formulario.setCostoUnitAtencionesR(detalle.getCostoUnitarioPorAtencion());
								  			}else{
								  				formulario.setCostoUnitValImpR(new BigDecimal(0.00));
								  				formulario.setCostoUnitValReparDomicR(new BigDecimal(0.00));
								  				formulario.setCostoUnitValEntDisElR(new BigDecimal(0.00));
								  				formulario.setCostoUnitValFisiCanjR(new BigDecimal(0.00));
								  				formulario.setCostoUnitValDigitCanjR(new BigDecimal(0.00));
								  				formulario.setCostoUnitAtencionesR(new BigDecimal(0.00));
								  			}
										}else if( FiseConstants.ZONABENEF_PROVINCIA_COD == Long.parseLong(zonaBenef) ){
											formulario.setNroValeImpP(Long.parseLong(nroValesImp));
											formulario.setNroValReparDomicP(Long.parseLong(nroValesRep));
											formulario.setNroValEntDisElP(Long.parseLong(nroValesEntr));
											formulario.setNroValFisiCanjP(Long.parseLong(nroValesFis));
											formulario.setNroValDigitCanjP(Long.parseLong(nroValesFis));
											formulario.setNroAtencionesP(Long.parseLong(nroAten));
											formulario.setGestionAdmP(new BigDecimal(totalGestAdm));
											formulario.setDesplPersonalP(new BigDecimal(totalDesplPers));
											formulario.setActivExtraordP(new BigDecimal(totalActivExtr));
											
											if( detalle!=null ){
								  				formulario.setCostoUnitValImpP(detalle.getCostoUnitarioImpresionVales());
								  				formulario.setCostoUnitValReparDomicP(detalle.getCostoUnitReprtoValeDomici());
								  				formulario.setCostoUnitValEntDisElP(detalle.getCostoUnitEntregaValDisEl());
								  				formulario.setCostoUnitValFisiCanjP(detalle.getCostoUnitCanjeLiqValFisi());
								  				formulario.setCostoUnitValDigitCanjP(detalle.getCostoUnitCanjeValDigital());
								  				formulario.setCostoUnitAtencionesP(detalle.getCostoUnitarioPorAtencion());
								  			}else{
								  				formulario.setCostoUnitValImpP(new BigDecimal(0.00));
								  				formulario.setCostoUnitValReparDomicP(new BigDecimal(0.00));
								  				formulario.setCostoUnitValEntDisElP(new BigDecimal(0.00));
								  				formulario.setCostoUnitValFisiCanjP(new BigDecimal(0.00));
								  				formulario.setCostoUnitValDigitCanjP(new BigDecimal(0.00));
								  				formulario.setCostoUnitAtencionesP(new BigDecimal(0.00));
								  			}
										}else if( FiseConstants.ZONABENEF_LIMA_COD == Long.parseLong(zonaBenef) ){
											
											if( FiseConstants.COD_EMPRESA_EDELNOR.equals(formulario.getCodigoEmpresa()) || FiseConstants.COD_EMPRESA_LUZ_SUR.equals(formulario.getCodigoEmpresa()) ){
												formulario.setNroValeImpL(Long.parseLong(nroValesImp));
												formulario.setNroValReparDomicL(Long.parseLong(nroValesRep));
												formulario.setNroValEntDisElL(Long.parseLong(nroValesEntr));
												formulario.setNroValFisiCanjL(Long.parseLong(nroValesFis));
												formulario.setNroValDigitCanjL(Long.parseLong(nroValesFis));
												formulario.setNroAtencionesL(Long.parseLong(nroAten));
												formulario.setGestionAdmL(new BigDecimal(totalGestAdm));
												formulario.setDesplPersonalL(new BigDecimal(totalDesplPers));
												formulario.setActivExtraordL(new BigDecimal(totalActivExtr));
												
												if( detalle!=null ){
									  				formulario.setCostoUnitValImpL(detalle.getCostoUnitarioImpresionVales());
									  				formulario.setCostoUnitValReparDomicL(detalle.getCostoUnitReprtoValeDomici());
									  				formulario.setCostoUnitValEntDisElL(detalle.getCostoUnitEntregaValDisEl());
									  				formulario.setCostoUnitValFisiCanjL(detalle.getCostoUnitCanjeLiqValFisi());
									  				formulario.setCostoUnitValDigitCanjL(detalle.getCostoUnitCanjeValDigital());
									  				formulario.setCostoUnitAtencionesL(detalle.getCostoUnitarioPorAtencion());
									  			}else{
									  				formulario.setCostoUnitValImpL(new BigDecimal(0.00));
									  				formulario.setCostoUnitValReparDomicL(new BigDecimal(0.00));
									  				formulario.setCostoUnitValEntDisElL(new BigDecimal(0.00));
									  				formulario.setCostoUnitValFisiCanjL(new BigDecimal(0.00));
									  				formulario.setCostoUnitValDigitCanjL(new BigDecimal(0.00));
									  				formulario.setCostoUnitAtencionesL(new BigDecimal(0.00));
									  			}
											}else{
												formulario.setNroValeImpL(0L);
												formulario.setNroValReparDomicL(0L);
												formulario.setNroValEntDisElL(0L);
												formulario.setNroValFisiCanjL(0L);
												formulario.setNroValDigitCanjL(0L);
												formulario.setNroAtencionesL(0L);
												formulario.setGestionAdmL(new BigDecimal(0));
												formulario.setDesplPersonalL(new BigDecimal(0));
												formulario.setActivExtraordL(new BigDecimal(0));
												
												formulario.setCostoUnitValImpL(new BigDecimal(0.00));
								  				formulario.setCostoUnitValReparDomicL(new BigDecimal(0.00));
								  				formulario.setCostoUnitValEntDisElL(new BigDecimal(0.00));
								  				formulario.setCostoUnitValFisiCanjL(new BigDecimal(0.00));
								  				formulario.setCostoUnitValDigitCanjL(new BigDecimal(0.00));
								  				formulario.setCostoUnitAtencionesL(new BigDecimal(0.00));
											}
											
											
										}
									}
									
									/**validacion de estructura*/
									
									//----RURAL
									
									//NRO VALES IMPR - 10
									if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroValeImpR(),10) ){
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3711);
									}
									//NRO VALES REPART - 10
									if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroValReparDomicR(),10) ){
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3712);
									}
									//NO VALES ENTR - 10
									if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroValEntDisElR(),10) ){
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3713);
									}
									//NRO VALES FISICOS - 10
									if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroValFisiCanjR(),10) ){
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3714);
									}
									//NRO VALES DIGITALES - 10
									if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroValDigitCanjR(),10) ){
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3715);
									}
									//NRO ATENCIONES - 10
									if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroAtencionesR(),10) ){
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3716);
									}
									//GEST ADM - 18,2
									if( !FormatoUtil.validaCampoNumeroDecimal(formulario.getGestionAdmR(),18,2) ){
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3717);
									}
									//DESPL PERSONAL - 18,2
									if( !FormatoUtil.validaCampoNumeroDecimal(formulario.getDesplPersonalR(),18,2) ){
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3718);
									}
									//ACTIV EXTR - 18,2
									if( !FormatoUtil.validaCampoNumeroDecimal(formulario.getActivExtraordR(),18,2) ){
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3719);
									}
									
									//-----PROVINCIA
									
									//NRO VALES IMPR - 10
									if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroValeImpP(),10) ){
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3711);
									}
									//NRO VALES REPART - 10
									if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroValReparDomicP(),10) ){
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3712);
									}
									//NO VALES ENTR - 10
									if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroValEntDisElP(),10) ){
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3713);
									}
									//NRO VALES FISICOS - 10
									if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroValFisiCanjP(),10) ){
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3714);
									}
									//NRO VALES DIGITALES - 10
									if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroValDigitCanjP(),10) ){
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3715);
									}
									//NRO ATENCIONES - 10
									if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroAtencionesP(),10) ){
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3716);
									}
									//GEST ADM - 18,2
									if( !FormatoUtil.validaCampoNumeroDecimal(formulario.getGestionAdmP(),18,2) ){
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3717);
									}
									//DESPL PERSONAL - 18,2
									if( !FormatoUtil.validaCampoNumeroDecimal(formulario.getDesplPersonalP(),18,2) ){
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3718);
									}
									//ACTIV EXTR - 18,2
									if( !FormatoUtil.validaCampoNumeroDecimal(formulario.getActivExtraordP(),18,2) ){
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3719);
									}
									
									//----LIMA
									
									//NRO VALES IMPR - 10
									if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroValeImpL(),10) ){
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3711);
									}
									//NRO VALES REPART - 10
									if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroValReparDomicL(),10) ){
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3712);
									}
									//NO VALES ENTR - 10
									if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroValEntDisElL(),10) ){
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3713);
									}
									//NRO VALES FISICOS - 10
									if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroValFisiCanjL(),10) ){
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3714);
									}
									//NRO VALES DIGITALES - 10
									if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroValDigitCanjL(),10) ){
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3715);
									}
									//NRO ATENCIONES - 10
									if( !FormatoUtil.validaCampoNumeroEntero(formulario.getNroAtencionesL(),10) ){
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3716);
									}
									//GEST ADM - 18,2
									if( !FormatoUtil.validaCampoNumeroDecimal(formulario.getGestionAdmL(),18,2) ){
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3717);
									}
									//DESPL PERSONAL - 18,2
									if( !FormatoUtil.validaCampoNumeroDecimal(formulario.getDesplPersonalL(),18,2) ){
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3718);
									}
									//ACTIV EXTR - 18,2
									if( !FormatoUtil.validaCampoNumeroDecimal(formulario.getActivExtraordL(),18,2) ){
										cont++;
										sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3719);
									}
									
									/***/
									
								}
								//
								formulario.setUsuario(user.getLogin());
								formulario.setTerminal(user.getLoginIP());
								formulario.setTipoArchivo(FiseConstants.TIPOARCHIVO_TXT);
								formulario.setNombreArchivo(archivo.getTitle());
								//
								formulario.setEtapa(etapaEdit);
								
								if( FiseConstants.BLANCO.equals(sMsg) ){
									if( (""+FiseConstants.ADD).equals(tipoOperacion) ){
										objeto = formatoService.registrarFormato12BC(formulario);
									}else if( (""+FiseConstants.UPDATE).equals(tipoOperacion) ){
										FiseFormato12BC formatoModif = new FiseFormato12BC();
										FiseFormato12BCPK id = new FiseFormato12BCPK();
										id.setCodEmpresa(formulario.getCodigoEmpresa());
										id.setAnoPresentacion((int)formulario.getAnioPresent());
										id.setMesPresentacion((int)formulario.getMesPresent());
										id.setAnoEjecucionGasto((int)formulario.getAnioEjecuc());
										id.setMesEjecucionGasto((int)formulario.getMesEjecuc());
										id.setEtapa(formulario.getEtapa());
										formatoModif = formatoService.getFormatoCabeceraById(id);
										objeto = formatoService.modificarFormato12BC(formulario, formatoModif);
									}
								}
								
							}else{
								cont++;
								sMsg = fiseUtil.agregarErrorBeanConMensaje(sMsg, mapaErrores, listaError, cont, FiseConstants.COD_ERROR_F12B_3720);
							}
							
						}
					}else{
						cont++;
						sMsg = sMsg + "El archivo cargado debe contener información para el Formato 12B ";
						throw new Exception("El archivo cargado debe contener información para el Formato 12B ");
					}
					is.close();
					
				}else{
					cont++;
					sMsg = sMsg + "El nombre del archivo debe corresponder al periodo a declarar ";
					throw new Exception("El nombre del archivo debe corresponder al periodo a declarar ");
				}
				
				
			}else{
				throw new Exception(mapaErrores.get(FiseConstants.COD_ERROR_3666));
			}
			
		}catch (Exception e) {			   
			  //refer.setCondicion(false);				  
			String error = e.getMessage();
			if( FiseConstants.BLANCO.equals(error.trim()) ){
				error = mapaErrores.get(FiseConstants.COD_ERROR_3633);
			}
			sMsg = sMsg+error;	        	
			logger.info(error);
			cont++;
			MensajeErrorBean errorBean = new MensajeErrorBean();
			errorBean.setId(cont);
			errorBean.setDescripcion(error);
			listaError.add(errorBean);
			  //throw new Exception(error); 
			  			   
		  }
		//pRequest.getPortletSession().setAttribute("MensajeInformacion", sMsg, PortletSession.APPLICATION_SCOPE);
		formatoMensaje.setFiseFormato12BC(objeto);
		formatoMensaje.setMensajeError(sMsg);
		if(listaError.size()>0)
			formatoMensaje.setListaMensajeError(listaError);
		
		return formatoMensaje;
	}
	
	/*****/
	
	@ActionMapping(params = "action=uploadFile2")
	public void uploadFile(ActionRequest request, ActionResponse response, @ModelAttribute("formato12BGartCommand") Formato12BGartCommand command) {
		logger.info("aqui en upload controller");
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(request);
		
		String tipoaccion = uploadPortletRequest.getParameter("tipoOperacion");
		String typeFile = uploadPortletRequest.getParameter("typeFile");
		
		logger.info("TIPO FILE ::"+typeFile);
		String codEmp = null;
		String periodo = null;
		
		String anoEjecucion = uploadPortletRequest.getParameter("anoEjecucionGasto");
		String mesEjecucion = uploadPortletRequest.getParameter("mesEjecucionGasto");
		
		if(tipoaccion != null && tipoaccion.equalsIgnoreCase(String.valueOf(FiseConstants.UPDATE))){
			 codEmp = uploadPortletRequest.getParameter("codEmpresaHidden");
			 periodo = uploadPortletRequest.getParameter("peridoDeclaracionHidden");
			
		}else{
			 codEmp = uploadPortletRequest.getParameter("codEmpresa");
			 periodo = uploadPortletRequest.getParameter("peridoDeclaracion");
		}
		
		
		
		
		FiseFormato12BCPK cabeceraPk = new FiseFormato12BCPK();
		cabeceraPk.setCodEmpresa(codEmp);
		if (periodo != null && periodo.length() > 6) {
			cabeceraPk.setAnoPresentacion(Integer.parseInt(periodo.substring(0, 4)));
			try{
				cabeceraPk.setMesPresentacion(Integer.parseInt(periodo.substring(4, 6)));
				cabeceraPk.setEtapa(periodo.substring(6, periodo.length()));
			}catch(NumberFormatException e){
				cabeceraPk.setMesPresentacion(Integer.parseInt(periodo.substring(4, 5)));
				cabeceraPk.setEtapa(periodo.substring(5, periodo.length()));
			}
			
			
			command.setPeridoDeclaracion(periodo);
			command.setPeridoDeclaracion(FiseUtil.descripcionPeriodo(cabeceraPk.getMesPresentacion().longValue(), cabeceraPk.getAnoPresentacion().longValue(), cabeceraPk.getEtapa()));

		}
		cabeceraPk.setAnoEjecucionGasto(Integer.parseInt(anoEjecucion));
		cabeceraPk.setMesEjecucionGasto(Integer.parseInt(mesEjecucion));
		
		logger.info("tipoaccion::" + tipoaccion);
		logger.info("codEmp::" + codEmp);
		logger.info("periodo::" + periodo);
		
		List<MensajeErrorBean> lstErrores=new ArrayList<MensajeErrorBean>();
		
			try{
				if(typeFile.trim().equalsIgnoreCase(FiseConstants.TYPE_FILE_XLS+"")){
					FileEntry fileEntry = fiseUtil.subirDocumento(request, uploadPortletRequest, FiseConstants.TIPOARCHIVO_XLS);
					lstErrores = readExcelFile(fileEntry, themeDisplay, cabeceraPk, tipoaccion, fiseUtil);
				}else if(typeFile.trim().equalsIgnoreCase(FiseConstants.TYPE_FILE_TXT+"")){
					FileEntry fileEntry = fiseUtil.subirDocumentoTxt(request, uploadPortletRequest, FiseConstants.TIPOARCHIVO_TXT);
					lstErrores = readTxtFile(fileEntry, themeDisplay, cabeceraPk, tipoaccion, fiseUtil);
			   }
				
				
			}catch(FileMimeTypeException ex){
				ex.printStackTrace();
				MensajeErrorBean msg = new MensajeErrorBean();
				msg.setId(1);
				msg.setDescripcion("Debe seleccionar una archivo");
				lstErrores.add(msg);
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		
		
		if (lstErrores != null && !lstErrores.isEmpty()) {
			if (tipoaccion != null && tipoaccion.equalsIgnoreCase(String.valueOf(FiseConstants.UPDATE))) {
				response.setRenderParameter("tipoOperacion", String.valueOf(FiseConstants.UPDATE));
				response.setRenderParameter("action", "viewFormato");

			} else {
				response.setRenderParameter("tipoOperacion", String.valueOf(FiseConstants.ADD));
				response.setRenderParameter("action", "newFormato");
			}
			response.setRenderParameter("error", lstErrores.get(0).getDescripcion().substring(lstErrores.get(0).getDescripcion().indexOf(":")+1 , lstErrores.get(0).getDescripcion().length()));
		} else {
			response.setRenderParameter("tipoOperacion", String.valueOf(FiseConstants.UPDATE));
			response.setRenderParameter("error", "El Formato 12B se guardó satisfactoriamente");
			response.setRenderParameter("action", "viewFormato");
		}
		

		
		response.setRenderParameter("codEmpresa", codEmp);
		response.setRenderParameter("codEmpresaHidden", codEmp);
		response.setRenderParameter("peridoDeclaracion", periodo);
		response.setRenderParameter("peridoDeclaracionHidden", periodo);
		response.setRenderParameter("anoPresentacion", cabeceraPk.getAnoPresentacion() + "");
		response.setRenderParameter("mesPresentacion", cabeceraPk.getMesPresentacion() + "");
		response.setRenderParameter("etapa", cabeceraPk.getEtapa());
		response.setRenderParameter("anoEjecucionGasto", cabeceraPk.getAnoEjecucionGasto() + "");
		response.setRenderParameter("mesEjecucionGasto", cabeceraPk.getMesEjecucionGasto() + "");
		
		

	}

	
	public List<MensajeErrorBean> readExcelFile(FileEntry archivo, ThemeDisplay themeDisplay, FiseFormato12BCPK pk, String tipoOperacion, FiseUtil util) {
		InputStream is = null;
		List<MensajeErrorBean> listaError = new ArrayList<MensajeErrorBean>();
		int cont = 0;
		try {

			if (archivo != null) {
				is = archivo.getContentStream();
				HSSFWorkbook libro = new HSSFWorkbook(is);
				if (libro != null) {
					FiseFormato12BCPK resultPk = FormatoExcelImport.readSheetCabecera12B(libro);
					List<FiseFormato12BD> lstDetalle = FormatoExcelImport.getListDetalleSheet12B(libro, resultPk, util);
					if (resultPk != null) {
						logger.info("empresa::" + pk.getCodEmpresa() + "vs" + resultPk.getCodEmpresa());
						logger.info("anio::" + pk.getAnoPresentacion() + "vs" + resultPk.getAnoPresentacion());
						logger.info("mes::" + pk.getMesPresentacion() + "vs" + resultPk.getMesPresentacion());
						logger.info("etapa::" + pk.getEtapa() + "vs" + resultPk.getEtapa());

						if (pk.getCodEmpresa().trim().equalsIgnoreCase(resultPk.getCodEmpresa().trim()) && pk.getAnoPresentacion().intValue() == resultPk.getAnoPresentacion().intValue() && pk.getMesPresentacion().intValue() == resultPk.getMesPresentacion().intValue()) {

							FiseFormato12BC fise12BC = new FiseFormato12BC();
							resultPk.setEtapa(pk.getEtapa());
							resultPk.setAnoEjecucionGasto(resultPk.getAnoPresentacion());
							resultPk.setMesEjecucionGasto(resultPk.getMesPresentacion());
							fise12BC.setId(resultPk);
							fise12BC.setNombreArchivoExcel(archivo.getTitle());
							if (tipoOperacion != null && tipoOperacion.equalsIgnoreCase(String.valueOf(FiseConstants.UPDATE))) {
								fise12BC = formatoService.getFormatoCabeceraById(fise12BC.getId());
								fise12BC.setUsuarioActualizacion(themeDisplay.getUser().getLogin());
								fise12BC.setTerminalActualizacion(themeDisplay.getUser().getLoginIP());
								fise12BC.setFechaActualizacion(new Date());
								Integer upDate = formatoService.updateFormatoCabecera(fise12BC);
								if (upDate != null && upDate > 0) {
									if (lstDetalle != null && !lstDetalle.isEmpty()) {
										for (FiseFormato12BD detalle : lstDetalle) {
											detalle.getId().setEtapa(pk.getEtapa());
											detalle.setUsuarioCreacion(fise12BC.getUsuarioCreacion());
											detalle.setTerminalCreacion(fise12BC.getTerminalCreacion());
											 detalle.setFechaCreacion(fise12BC.getFechaCreacion());
											detalle.setUsuarioActualizacion(themeDisplay.getUser().getLogin());
											detalle.setTerminalActualizacion(themeDisplay.getUser().getLoginIP());
											 detalle.setFechaActualizacion(new Date());
											//formatoService.deleteFormatoObs(detalle.getId().getCodEmpresa().trim(), detalle.getId().getAnoPresentacion(), detalle.getId().getMesPresentacion(),detalle.getId().getEtapa(),detalle.getId().getAnoEjecucionGasto(), detalle.getId().getMesEjecucionGasto(),detalle.getId().getIdZonaBenef(),null);
											formatoService.updateFormatoDetalle(detalle); 
											
										}
									}
									
								}
								
							
								
							} else {

								fise12BC.setUsuarioCreacion(themeDisplay.getUser().getLogin());
								fise12BC.setTerminalCreacion(themeDisplay.getUser().getLoginIP());
								fise12BC.setFechaCreacion(new Date());
								FiseGrupoInformacion grupoInfo = null;
								long idGrupoInf = commonService.obtenerIdGrupoInformacion(fise12BC.getId().getAnoPresentacion().longValue(), fise12BC.getId().getMesPresentacion().longValue(),FiseConstants.MENSUAL.trim());
								if (idGrupoInf != 0) {
									grupoInfo = commonService.obtenerFiseGrupoInformacionByPK(idGrupoInf);
									nameGrupo = grupoInfo.getDescripcion();
								}
								fise12BC.setFiseGrupoInformacion(grupoInfo);

								//nameEstado = FiseConstants.ESTADO_FECHAENVIO_POR_ENVIAR;
								nameEstado = "Abierto";
								fise12BC = formatoService.saveFormatoCabecera(fise12BC);
								logger.info("fise12BC" + fise12BC);
								if (fise12BC != null) {
									logger.info("diferente de nulo");
									if (lstDetalle != null && !lstDetalle.isEmpty()) {
									
										for (FiseFormato12BD detalle : lstDetalle) {
											detalle.getId().setEtapa(pk.getEtapa());
											detalle.setUsuarioCreacion(themeDisplay.getUser().getLogin());
											detalle.setTerminalCreacion(themeDisplay.getUser().getLoginIP());
											detalle.setFechaCreacion(new Date());
											formatoService.saveFormatoDetalle(detalle);
											

										}
									}
								}

							}

							

						} else {
							throw new Exception("La Distribuidora Eléctrica y/o el Año Mes no coinciden con lo seleccionado");
						}
					}
				}/* else {
					throw new Exception("Libro vacio");
				}*/

			} else {
				throw new Exception("El archivo cargado no contiene datos para ser procesado");
			}
		}/*catch(ConstraintViolationException c){
			c.printStackTrace();
			MensajeErrorBean msg = new MensajeErrorBean();
			msg.setId(cont);
			msg.setDescripcion("El formato ya existe");
			listaError.add(msg);
			
		}*/ catch (DataIntegrityViolationException ex) {
			ex.printStackTrace();
			MensajeErrorBean msg = new MensajeErrorBean();
			msg.setId(cont);
			msg.setDescripcion("El Formato ya existe para la Distribuidora Eléctrica y Periodo a Declarar");
			listaError.add(msg);
			
		} catch (Exception ex) {
			MensajeErrorBean msg = new MensajeErrorBean();
			msg.setId(cont);
			//msg.setDescripcion("Se produjo un error al guardar los datos del Formato 12B");
			msg.setDescripcion(ex.toString());
			listaError.add(msg);

		}

		return listaError;
	}


	public List<MensajeErrorBean> readTxtFile(FileEntry archivo, ThemeDisplay themeDisplay, FiseFormato12BCPK pk, String tipoOperacion, FiseUtil util) {
		InputStream is = null;
		List<MensajeErrorBean> listaError = new ArrayList<MensajeErrorBean>();
		int cont = 0;
		try {

			if (archivo != null) {
				is = archivo.getContentStream();
				CfgTabla tabla = new CfgTabla();
				tabla.setIdTabla(FiseConstants.ID_TABLA_FORMATO12B);
				List<CfgCampo> listaCampo = campoService.listarCamposByTabla(tabla);
				List<CfgCampo> listaCampoRead=new ArrayList<CfgCampo>();
				int lenghtEmpresa=0;
				int lenghtAnioPres=0;
				int lenghtMesPres=0;
				int lenghtAnioEjec=0;
				int lenghtMesEjec=0;
				int lenghtZona=0;
				
				int lenghtValesImp=0;
				int lenghtValeRepartido=0;
				int lenghtValeEntrDE=0;
				int lenghtValeFisico=0;
				int lenghtValeDigital=0;
				int lenghtAtenciones=0;
				int lenghtGestion=0;
				int lenghtDesplazamiento=0;
				int lenghtActividades=0;
				int lenghtReconocer=0;
				
				int lenghtCostoValesImp=0;
				int lenghtCostoValeRepartido=0;
				int lenghtCostoValeEntrDE=0;
				int lenghtCostoValeFisico=0;
				int lenghtCostoValeDigital=0;
				int lenghtCostoAtenciones=0;
				int lenghtTotalValesImp=0;
				int lenghtTotalValeRepartido=0;
				int lenghtTotalValeEntrDE=0;
				int lenghtTotalValeFisico=0;
				int lenghtTotalValeDigital=0;
				int lenghtTotalAtenciones=0;
				
				String nombreIdeal = FormatoUtil.nombreArchivoCargaTxt(Long.parseLong(pk.getAnoPresentacion().toString()), Long.parseLong(pk.getMesPresentacion().toString()), pk.getCodEmpresa(), FiseConstants.TIPO_FORMATO_12B);
				if( nombreIdeal.trim().equals(archivo.getDescription().trim()) ){
					//---
					if(listaCampo!=null && !listaCampo.isEmpty()){
						
						for(CfgCampo campo:listaCampo){
							boolean isAdd=false;
							logger.info("COD CAMPO::"+campo.getCodCampo());
							if(campo.getCodCampo().trim().equalsIgnoreCase(FiseConstants.CAMPO_COD_EMPRESA.trim())){
								isAdd=true;
								lenghtEmpresa=campo.getLongitud().intValue();
							}else if(campo.getCodCampo().trim().equalsIgnoreCase(FiseConstants.CAMPO_ANO_PRESENTACION.trim())){
								isAdd=true;
								lenghtAnioPres=campo.getLongitud().intValue();
							}else if(campo.getCodCampo().trim().equalsIgnoreCase(FiseConstants.CAMPO_MES_PRESENTACION.trim())){
								isAdd=true;
								lenghtMesPres=campo.getLongitud().intValue();
							}else if(campo.getCodCampo().trim().equalsIgnoreCase( FiseConstants.CAMPO_ANO_EJECUCION_GASTO.trim())){
								isAdd=true;
								lenghtAnioEjec=campo.getLongitud().intValue();
							}else if(campo.getCodCampo().trim().equalsIgnoreCase( FiseConstants.CAMPO_MES_EJECUCION_GASTO.trim())){
								isAdd=true;
								lenghtMesEjec=campo.getLongitud().intValue();
							}else if(campo.getCodCampo().trim().equalsIgnoreCase(FiseConstants.CAMPO_ID_ZONA_BENEF.trim())){
								isAdd=true;
								lenghtZona=campo.getLongitud().intValue();
							}else if(campo.getCodCampo().trim().equalsIgnoreCase( FiseConstants.CAMPO_NUMERO_VALES_IMPRESO.trim())){
								isAdd=true;
								lenghtValesImp=campo.getLongitud().intValue();
							}else if(campo.getCodCampo().trim().equalsIgnoreCase( FiseConstants.CAMPO_NUMERO_VALES_REPARTIDOS_DOMI.trim())){
								isAdd=true;
								lenghtValeRepartido=campo.getLongitud().intValue();
							}else if(campo.getCodCampo().trim().equalsIgnoreCase( FiseConstants.CAMPO_NUMERO_VALES_ENTREGADO_DIS_EL.trim())){
								isAdd=true;
								lenghtValeEntrDE=campo.getLongitud().intValue();
							}else if(campo.getCodCampo().trim().equalsIgnoreCase( FiseConstants.CAMPO_NUMERO_VALES_FISICOS_CANJEADOS.trim())){
								isAdd=true;
								lenghtValeFisico=campo.getLongitud().intValue();
							}else if(campo.getCodCampo().trim().equalsIgnoreCase( FiseConstants.CAMPO_NUMERO_VALES_DIGITAL_CANJEADOS.trim())){
								isAdd=true;
								lenghtValeDigital=campo.getLongitud().intValue();
							}else if(campo.getCodCampo().trim().equalsIgnoreCase( FiseConstants.CAMPO_NUMERO_ATENCIONES.trim())){
								isAdd=true;
								lenghtAtenciones=campo.getLongitud().intValue();
							}else if(campo.getCodCampo().trim().equalsIgnoreCase( FiseConstants.CAMPO_TOTAL_GESTION_ADMINISTRATIVA.trim())){
								isAdd=true;
								lenghtGestion=campo.getLongitud().intValue();
							}else if(campo.getCodCampo().trim().equalsIgnoreCase( FiseConstants.CAMPO_TOTAL_DESPLAZAMIENTO_PERSONAL.trim())){
								isAdd=true;
								lenghtDesplazamiento=campo.getLongitud().intValue();
							}else if(campo.getCodCampo().trim().equalsIgnoreCase( FiseConstants.CAMPO_TOTAL_ACTIVIDADES_EXTRAORD.trim())){
								isAdd=true;
								lenghtActividades=campo.getLongitud().intValue();
							}else if(campo.getCodCampo().trim().equalsIgnoreCase( FiseConstants.CAMPO_TOTAL_RECONOCER.trim())){
								isAdd=true;
								lenghtReconocer=campo.getLongitud().intValue();
								//************************COSTOS*************************//
							}else if(campo.getCodCampo().trim().equalsIgnoreCase(FiseConstants.CAMPO_COSTO_ESTANDAR_UNIT_VALE_IMPRE.trim())){
								isAdd=false;
								lenghtCostoValesImp=campo.getLongitud().intValue();
							}else if(campo.getCodCampo().trim().equalsIgnoreCase(FiseConstants.CAMPO_COSTO_TOTAL_IMPRESION_VALE.trim())){
								isAdd=false;
								lenghtTotalValesImp=campo.getLongitud().intValue();
							}else if(campo.getCodCampo().trim().equalsIgnoreCase(FiseConstants.CAMPO_COSTO_ESTANDAR_UNIT_VALE_REPAR.trim())){
								isAdd=false;
								lenghtCostoValeRepartido=campo.getLongitud().intValue();
							}else if(campo.getCodCampo().trim().equalsIgnoreCase(FiseConstants.CAMPO_COSTO_TOTAL_REPARTO_VALES_DOMI.trim())){
								isAdd=false;
								lenghtTotalValeRepartido=campo.getLongitud().intValue();
							}else if(campo.getCodCampo().trim().equalsIgnoreCase(FiseConstants.CAMPO_COSTO_ESTANDAR_UNIT_VAL_DIS_EL.trim())){
								isAdd=false;
								lenghtCostoValeEntrDE=campo.getLongitud().intValue();
							}else if(campo.getCodCampo().trim().equalsIgnoreCase(FiseConstants.CAMPO_COSTO_TOTAL_ENTREGA_VAL_DIS_EL.trim())){
								isAdd=false;
								lenghtTotalValeEntrDE=campo.getLongitud().intValue();
							}else if(campo.getCodCampo().trim().equalsIgnoreCase(FiseConstants.CAMPO_COSTO_ESTANDAR_UNIT_VAL_FI_CAN.trim())){
								isAdd=false;
								lenghtCostoValeFisico=campo.getLongitud().intValue();
							}else if(campo.getCodCampo().trim().equalsIgnoreCase(FiseConstants.CAMPO_COSTO_TOTAL_CANJE_LIQ_VALE_FIS.trim())){
								isAdd=false;
								lenghtTotalValeFisico=campo.getLongitud().intValue();
							}else if(campo.getCodCampo().trim().equalsIgnoreCase(FiseConstants.CAMPO_COSTO_ESTANDAR_UNIT_VAL_DG_CAN.trim())){
								isAdd=false;
								lenghtCostoValeDigital=campo.getLongitud().intValue();
							}else if(campo.getCodCampo().trim().equalsIgnoreCase(FiseConstants.CAMPO_COSTO_TOTAL_CANJE_LIQ_VALE_DIG.trim())){
								isAdd=false;
								lenghtTotalValeDigital=campo.getLongitud().intValue();
							}else if(campo.getCodCampo().trim().equalsIgnoreCase(FiseConstants.CAMPO_COSTO_ESTANDAR_UNIT_ATENCION.trim())){
								isAdd=false;
								lenghtCostoAtenciones=campo.getLongitud().intValue();
							}else if(campo.getCodCampo().trim().equalsIgnoreCase(FiseConstants.CAMPO_COSTO_TOTAL_ATENCION_CONS_RECL.trim())){
								isAdd=false;
								lenghtTotalAtenciones=campo.getLongitud().intValue();
							}
							
							if(isAdd){
								listaCampoRead.add(campo);
							}
							
						}
					}
					logger.info("CANTIDAD CAMPOS ::"+listaCampoRead.size());
					BufferedReader br = new BufferedReader( new InputStreamReader(is));
					//List<String> listaDetalleTxt= new ArrayList<String>();
					String sCurrentLine;
					
					List<FiseFormato12BD> lstDetalle=new ArrayList<FiseFormato12BD>();
				
					while ((sCurrentLine = br.readLine()) != null) {
						logger.info("total::"+sCurrentLine.length());
						logger.info(sCurrentLine);
						int posInicial=0;
						String codEm=sCurrentLine.substring(posInicial, lenghtEmpresa);
						posInicial=posInicial+lenghtEmpresa;
						logger.info("codEm ::"+codEm+"/"+posInicial+"/"+lenghtAnioPres);//0+4
						logger.info("sCurrentLine ::"+sCurrentLine.length());//0+4
							
						String anioPres=sCurrentLine.substring(posInicial, posInicial+lenghtAnioPres);
						logger.info("anioPres ::"+anioPres+"/"+posInicial);
						posInicial=posInicial+lenghtAnioPres;
						String mesPres=sCurrentLine.substring(posInicial, posInicial+lenghtMesPres);
						logger.info("mesPres ::"+mesPres+"/"+posInicial);
						posInicial=posInicial+lenghtMesPres;
						String anioEjec=sCurrentLine.substring(posInicial, posInicial+lenghtAnioEjec);
						logger.info("anioEjec ::"+anioEjec+"/"+posInicial);
						posInicial=posInicial+lenghtAnioEjec;
						String mesEjec=sCurrentLine.substring(posInicial, posInicial+lenghtMesEjec);
						logger.info("mesEjec ::"+mesEjec+"/"+posInicial);
						posInicial=posInicial+lenghtMesEjec;
						String idzon=sCurrentLine.substring(posInicial, posInicial+lenghtZona);
						logger.info("idzon ::"+idzon+"/"+posInicial);
						posInicial=posInicial+lenghtZona;
							
							
						String numValesImp=sCurrentLine.substring(posInicial,posInicial+ lenghtValesImp);
						posInicial=posInicial+lenghtValesImp+lenghtCostoValesImp+lenghtTotalValesImp;
						String numValesRepar=sCurrentLine.substring(posInicial, posInicial+lenghtValeRepartido);
						posInicial=posInicial+lenghtValeRepartido+lenghtCostoValeRepartido+lenghtTotalValeRepartido;
						String numValesEntrDE=sCurrentLine.substring(posInicial, posInicial+lenghtValeEntrDE);
						posInicial=posInicial+lenghtValeEntrDE+lenghtCostoValeEntrDE+lenghtTotalValeEntrDE;
						String numValesFisico=sCurrentLine.substring(posInicial, posInicial+lenghtValeFisico);
						posInicial=posInicial+lenghtValeFisico+lenghtCostoValeFisico+lenghtTotalValeFisico;
						String numValesDigital=sCurrentLine.substring(posInicial, posInicial+lenghtValeDigital);
						posInicial=posInicial+lenghtValeDigital+lenghtCostoValeDigital+lenghtTotalValeDigital;
						String numValeAtenciones=sCurrentLine.substring(posInicial, posInicial+lenghtAtenciones);
						posInicial=posInicial+lenghtAtenciones+lenghtCostoAtenciones+lenghtTotalAtenciones;
						String numValeGestion=sCurrentLine.substring(posInicial, posInicial+lenghtGestion);
						posInicial=posInicial+lenghtGestion;
						String numValeDesplazamiento=sCurrentLine.substring(posInicial, posInicial+lenghtDesplazamiento);
						posInicial=posInicial+lenghtDesplazamiento;
						String numValeActividad=sCurrentLine.substring(posInicial, posInicial+lenghtActividades);
						posInicial=posInicial+lenghtActividades;
						String totalreconocer=sCurrentLine.substring(posInicial, posInicial+lenghtReconocer);
							
							
						logger.info("codEm**LG ::"+codEm);
						logger.info("anioPres**LG ::"+anioPres);
						logger.info("mesPres**LG ::"+mesPres);
						
						logger.info("anioEjec**LG ::"+anioEjec);
						logger.info("mesEjec**LG ::"+mesEjec);
						logger.info("Zona**LG ::"+idzon);
						logger.info("numValesImp**LG ::"+numValesImp);
						logger.info("numValesRepar**LG ::"+numValesRepar);
						logger.info("numValesEntrDE**LG ::"+numValesEntrDE);
						logger.info("numValesFisico**LG ::"+numValesFisico);
						logger.info("numValesDigital**LG ::"+numValesDigital);
						logger.info("numValeAtenciones**LG ::"+numValeAtenciones);
						logger.info("numValeGestion**LG ::"+numValeGestion);
						logger.info("numValeDesplazamiento**LG ::"+numValeDesplazamiento);
						logger.info("numValeActividad**LG ::"+numValeActividad);
						logger.info("totalreconocer**LG ::"+totalreconocer);
						  
								 
						validarCampos(anioPres, "Año de Presentación", 3, 0);//validar vacio
						validarCampos(mesPres, "Mes de Presentación", 3, 0);//validar vacio
						validarCampos(anioEjec, "Año de Ejecución", 3, 0);//validar vacio
						validarCampos(mesEjec, "Mes de Ejecución", 3, 0);//validar vacio
						validarCampos(anioPres, "Año de Presentacion", 1, 4);//validar tamaño
						validarCampos(mesPres, "Mes de Presentación", 2, 0);//validar rango
						validarCampos(anioEjec, "Año de Ejecución", 1, 4);//validar tamaño
						validarCampos(mesEjec, "Mes de Ejecución", 2, 0);//validar rango
						
						/**validacion adicional*/
						/*if( (Long.parseLong(anioEjec)*100+Long.parseLong(mesEjec))>(Long.parseLong(anioPres)*100+Long.parseLong(mesPres)) ){
							throw new Exception("El Periodo de Ejecución no puede ser mayor al Periodo a Declarar");
						}*/
						
						
						validarCampos(numValesImp, "Número de vales Impreso", 4, 0);//number formart
						validarCampos(numValesRepar, "Número de vales a Domicilio", 4, 0);//number formart
						validarCampos(numValesEntrDE, "Número de vales de Distribuidora", 4, 0);//number formart
						validarCampos(numValesFisico, "Número de vales Fisicos", 4, 0);//number formart
						validarCampos(numValesDigital, "Número de vales Digitales", 4, 0);//number formart
						validarCampos(numValeAtenciones, "Número Atenciones ", 4, 0);//number formart
						validarCampos(numValeGestion, "Gestión administrativa", 5, 0);//number formart
						validarCampos(numValeDesplazamiento, "Desplazamiento personal", 5, 0);//number formart
						validarCampos(numValeActividad, "Actividades extraordinarias", 5, 0);//number formart
						validarCampos(totalreconocer, "Total a reconocer", 5, 0);//number formart
							
						   
						    
						if(pk.getCodEmpresa().trim().equalsIgnoreCase(codEm.trim()) &&  pk.getAnoPresentacion()== Integer.parseInt(anioPres) && pk.getMesPresentacion()== Integer.parseInt(mesPres)
								&& pk.getAnoEjecucionGasto()==Integer.parseInt(anioEjec) && pk.getMesEjecucionGasto()==Integer.parseInt(mesEjec)
								){
							
							boolean process = true;
								
							if( FiseConstants.ZONABENEF_LIMA_COD_STRING.equals(idzon.trim()) ){
								if( FiseConstants.COD_EMPRESA_EDELNOR.equalsIgnoreCase(pk.getCodEmpresa().trim()) || FiseConstants.COD_EMPRESA_LUZ_SUR.equalsIgnoreCase(pk.getCodEmpresa().trim()) ){
									process = true;
								}else{
									process = false;
								}
							}else{
								process = true;
							}
					
							if(process){
								FiseFormato12BDPK pkDetalle=new FiseFormato12BDPK();
								FiseFormato12BD detalle=new FiseFormato12BD();
								pkDetalle.setCodEmpresa(codEm);
								pkDetalle.setAnoEjecucionGasto(Integer.parseInt(anioEjec));
								pkDetalle.setAnoPresentacion(Integer.parseInt(anioEjec));
								pkDetalle.setEtapa(pk.getEtapa());
								pkDetalle.setIdZonaBenef(Integer.parseInt(idzon));
								pkDetalle.setMesEjecucionGasto(Integer.parseInt(mesEjec));
								pkDetalle.setMesPresentacion(Integer.parseInt(mesPres));
									   
								FiseFormato14BD fise14D=util.getDetalle14BDbyEmpAnioEtapa(pkDetalle.getCodEmpresa().trim(), pkDetalle.getAnoPresentacion(),null, pkDetalle.getIdZonaBenef(), FiseConstants.ETAPA_ESTABLECIDO);
								// formato.setCostoEstandarUnitValeImpre((fise14D!=null && fise14D.getCostoUnitarioImpresionVales()!=null)?fise14D.getCostoUnitarioImpresionVales():new BigDecimal(0.00));
								detalle.setNumeroValesImpreso(numValesImp.trim()!=null && !numValesImp.trim().isEmpty()?Integer.parseInt(numValesImp.trim()):0);
								detalle.setCostoEstandarUnitValeImpre(fise14D!=null && fise14D.getCostoUnitarioImpresionVales()!=null?fise14D.getCostoUnitarioImpresionVales():new BigDecimal(0));
								detalle.setCostoTotalImpresionVale(detalle.getCostoEstandarUnitValeImpre().multiply(new BigDecimal(detalle.getNumeroValesImpreso())).setScale(2, BigDecimal.ROUND_DOWN));
										  
								detalle.setNumeroValesRepartidosDomi(numValesRepar.trim()!=null && !numValesRepar.trim().isEmpty()?Integer.parseInt(numValesRepar.trim()):0);
								detalle.setCostoEstandarUnitValeRepar(fise14D!=null && fise14D.getCostoUnitReprtoValeDomici()!=null?fise14D.getCostoUnitReprtoValeDomici():new BigDecimal(0));
								detalle.setCostoTotalRepartoValesDomi(detalle.getCostoEstandarUnitValeRepar().multiply(new BigDecimal(detalle.getNumeroValesRepartidosDomi())).setScale(2, BigDecimal.ROUND_DOWN));
										  
								detalle.setNumeroValesEntregadoDisEl(numValesEntrDE.trim()!=null && !numValesEntrDE.trim().isEmpty()?Integer.parseInt(numValesEntrDE.trim()):0);
								detalle.setCostoEstandarUnitValDisEl(fise14D!=null && fise14D.getCostoUnitEntregaValDisEl()!=null?fise14D.getCostoUnitEntregaValDisEl():new BigDecimal(0));
								detalle.setCostoTotalEntregaValDisEl(detalle.getCostoEstandarUnitValDisEl().multiply(new BigDecimal(detalle.getNumeroValesEntregadoDisEl()) ).setScale(2, BigDecimal.ROUND_DOWN));
								  
								detalle.setNumeroValesFisicosCanjeados(numValesFisico.trim()!=null && !numValesFisico.trim().isEmpty()?Integer.parseInt(numValesFisico.trim()):0);
								detalle.setCostoEstandarUnitValFiCan(fise14D!=null && fise14D.getCostoUnitCanjeLiqValFisi()!=null?fise14D.getCostoUnitCanjeLiqValFisi():new BigDecimal(0));
								detalle.setCostoTotalCanjeLiqValeFis(detalle.getCostoEstandarUnitValFiCan().multiply(new BigDecimal(detalle.getNumeroValesFisicosCanjeados()) ).setScale(2, BigDecimal.ROUND_DOWN));
										  
								detalle.setNumeroValesDigitalCanjeados(numValesDigital.trim()!=null && !numValesDigital.trim().isEmpty()?Integer.parseInt(numValesDigital.trim()):0);
								detalle.setCostoEstandarUnitValDgCan(fise14D!=null && fise14D.getCostoUnitCanjeValDigital()!=null?fise14D.getCostoUnitCanjeValDigital():new BigDecimal(0));
								detalle.setCostoTotalCanjeLiqValeDig(detalle.getCostoEstandarUnitValDgCan().multiply(new BigDecimal(detalle.getNumeroValesDigitalCanjeados()) ).setScale(2, BigDecimal.ROUND_DOWN));
								  
								detalle.setNumeroAtenciones(numValeAtenciones.trim()!=null && !numValeAtenciones.trim().isEmpty()?Integer.parseInt(numValeAtenciones.trim()):0);
								detalle.setCostoEstandarUnitAtencion(fise14D!=null && fise14D.getCostoUnitarioPorAtencion()!=null?fise14D.getCostoUnitarioPorAtencion():new BigDecimal(0).setScale(2, BigDecimal.ROUND_DOWN));
								detalle.setCostoTotalAtencionConsRecl(detalle.getCostoEstandarUnitAtencion().multiply(new BigDecimal(detalle.getNumeroAtenciones()) ));
										  
								detalle.setTotalGestionAdministrativa(numValeGestion.trim()!=null && !numValeGestion.trim().isEmpty()?new BigDecimal(numValeGestion.trim()).setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0) );
								detalle.setTotalDesplazamientoPersonal(numValeDesplazamiento.trim()!=null && !numValeDesplazamiento.trim().isEmpty()?new BigDecimal(numValeDesplazamiento.trim()).setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
								detalle.setTotalActividadesExtraord(numValeActividad.trim()!=null && !numValeActividad.trim().isEmpty()?new BigDecimal(numValeActividad.trim()).setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
										  
								detalle.setTotalReconocer(totalreconocer.trim()!=null && !totalreconocer.trim().isEmpty()?new BigDecimal(totalreconocer.trim()).setScale(2, BigDecimal.ROUND_DOWN):new BigDecimal(0));
									   
										  
										  
								detalle.setId(pkDetalle);
								lstDetalle.add(detalle);
							}

								   
						}else{
							//El archivo cargado no corresponde a la Distribuidora Eléctrica, Mes o Año de Presentación del registro del formulario
							throw new Exception("El archivo cargado no corresponde a la Distribuidora Eléctrica, Año de Presentación, Mes de Presentación, Año de Ejecución o Mes de Ejecución del registro del formulario");
						}
							
					}
					if(lstDetalle!=null && !lstDetalle.isEmpty()){
						FiseFormato12BCPK pkCabecera=new FiseFormato12BCPK();
						FiseFormato12BC cabecera=new FiseFormato12BC();
						 
						pkCabecera.setCodEmpresa(lstDetalle.get(0).getId().getCodEmpresa());
						pkCabecera.setAnoEjecucionGasto(lstDetalle.get(0).getId().getAnoEjecucionGasto());
						pkCabecera.setAnoPresentacion(lstDetalle.get(0).getId().getAnoPresentacion());
						pkCabecera.setEtapa(lstDetalle.get(0).getId().getEtapa());
						pkCabecera.setMesEjecucionGasto(lstDetalle.get(0).getId().getMesEjecucionGasto());
						pkCabecera.setMesPresentacion(lstDetalle.get(0).getId().getMesPresentacion());
						cabecera.setId(pkCabecera);
						cabecera.setNombreArchivoTexto(archivo.getTitle());
						FiseGrupoInformacion grupoInfo = null;
						long idGrupoInf = commonService.obtenerIdGrupoInformacion(cabecera.getId().getAnoPresentacion().longValue(), cabecera.getId().getMesPresentacion().longValue(),FiseConstants.MENSUAL.trim());
						if (idGrupoInf != 0) {
							grupoInfo = commonService.obtenerFiseGrupoInformacionByPK(idGrupoInf);
							nameGrupo = grupoInfo.getDescripcion();
						}
						cabecera.setFiseGrupoInformacion(grupoInfo);
						 
						 
						
						if(tipoOperacion.equalsIgnoreCase(FiseConstants.ADD+"")){
							cabecera.setUsuarioCreacion(themeDisplay.getUser().getLogin());
							cabecera.setTerminalCreacion(themeDisplay.getUser().getLoginIP());
							cabecera.setFechaCreacion(new Date());
							 
							cabecera=formatoService.saveFormatoCabecera(cabecera);
							 
							for(FiseFormato12BD detalle:lstDetalle){
								detalle.setUsuarioCreacion(themeDisplay.getUser().getLogin());
								detalle.setTerminalCreacion(themeDisplay.getUser().getLoginIP());
								detalle.setFechaCreacion(new Date());
								formatoService.saveFormatoDetalle(detalle);
							}
							 
						}else if(tipoOperacion.equalsIgnoreCase(FiseConstants.UPDATE+"")){
							 
							cabecera.setUsuarioActualizacion(themeDisplay.getUser().getLogin());
							cabecera.setTerminalActualizacion(themeDisplay.getUser().getLoginIP());
							cabecera.setFechaActualizacion(new Date());
							 
	                        formatoService.updateFormatoCabecera(cabecera);
							 
							for(FiseFormato12BD detalle:lstDetalle){
								detalle.setUsuarioActualizacion(themeDisplay.getUser().getLogin());
								detalle.setTerminalActualizacion(themeDisplay.getUser().getLoginIP());
								detalle.setFechaActualizacion(new Date());
								formatoService.updateFormatoDetalle(detalle);
							}
						}
						 
					}else{
						throw new Exception("El archivo cargado no contiene datos para ser procesado");
					}
					//---
				}else{
					throw new Exception("El nombre del archivo debe corresponder al periodo a declarar ");
				}

			} 
			
			
			
		}/*catch(ConstraintViolationException c){
			c.printStackTrace();
			MensajeErrorBean msg = new MensajeErrorBean();
			msg.setId(cont);
			msg.setDescripcion("El formato ya existe");
			listaError.add(msg);
			
		} */catch (DataIntegrityViolationException ex) {
			ex.printStackTrace();
			MensajeErrorBean msg = new MensajeErrorBean();
			msg.setId(cont);
			//msg.setDescripcion(ex.toString());
			msg.setDescripcion("El Formato ya existe para la Distribuidora Eléctrica y Periodo a Declarar");
			listaError.add(msg);
			
		}catch (NumberFormatException ex) {
			MensajeErrorBean msg = new MensajeErrorBean();
			msg.setId(cont);
			//msg.setDescripcion(ex.toString());
			msg.setDescripcion("El formato no es válido");
			listaError.add(msg);

		} catch (Exception ex) {
			MensajeErrorBean msg = new MensajeErrorBean();
			msg.setId(cont);
			msg.setDescripcion(ex.toString());
			//msg.setDescripcion("Se produjo un error al guardar los datos del Formato 12B");
			listaError.add(msg);

		}

		return listaError;
	
	}
	
	private void validarCampos(String valor,String nameCampo,int tipo,int length)throws NumberFormatException,Exception{
		
		if(tipo == 1){//longitud d campo
			if(valor.trim().length()<length){
				throw new Exception("Formato no válido para el campo "+nameCampo);
			}
		}else if(tipo == 2){//rango mes
			if(Integer.parseInt(valor.trim())==0 || Integer.parseInt(valor.trim())>12){
				throw new Exception("Rango no válido para "+nameCampo);
			}
		}else if(tipo == 3){//campo vacio
			if(valor.length()==0){
				throw new Exception("El campo "+nameCampo+ "no acepta valores vacios");
			}
		}else if(tipo == 4){
			try{
				Integer.parseInt(valor.trim());
			}catch(Exception e){
				throw new NumberFormatException("Formato no válido para el campo "+nameCampo);
			}
			
		}else if(tipo == 5){
			try{
				Double.parseDouble(valor.trim());
			}catch(Exception e){
				throw new NumberFormatException("Formato no válido para el campo "+nameCampo);
			}
			
		}
	}
	
	@RequestMapping(params = "action=viewFormato")
	public String viewFormato(ModelMap model, RenderRequest request, RenderResponse response, @ModelAttribute("formato12BGartCommand") Formato12BGartCommand command) {

		PortletRequest pRequest = (PortletRequest)request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);

		String tipoOperacion = request.getParameter("tipoOperacion");
        logger.info("en view formato tipo operacion::"+tipoOperacion+" /command::"+command.getTipoOperacion()+"-"+command.getCodEmpresa());
		logger.info("command busqueda::"+command.getAnioInicio());
    
		String codEmpresa = request.getParameter("codEmpresa");
		String periodo = request.getParameter("periodoDeclaracion");
		String codEmpresaHidden = request.getParameter("codEmpresaHidden");
		String periodoHidden = request.getParameter("peridoDeclaracionHidden");
		String error = request.getParameter("error");

		String anio = request.getParameter("anoPresentacion");
		String mes = request.getParameter("mesPresentacion");
		String etapa = request.getParameter("etapa");
		String anioEjec = request.getParameter("anoEjecucionGasto");
		String mesEjec = request.getParameter("mesEjecucionGasto");
		
		String msg = request.getParameter("msgTrans");
		
		List<MensajeErrorBean> listaError = (List<MensajeErrorBean>)pRequest.getPortletSession().getAttribute("listaError", PortletSession.APPLICATION_SCOPE);

		if (error != null) {
			model.addAttribute("error", error);
		}

		if (codEmpresa != null) {
			command.setCodEmpresa(codEmpresa);
			
		}
		if (codEmpresaHidden != null) {
			command.setCodEmpresaHidden(codEmpresaHidden);
		}
		if (periodo != null) {
			command.setPeridoDeclaracion(periodo);
			
		}
		if (periodoHidden != null) {
			command.setPeridoDeclaracionHidden(periodoHidden);
		}
		if (anio != null) {
			command.setAnoPresentacion(anio != null ? Integer.valueOf(anio) : null);
		}
		if (mes != null) {
			command.setMesPresentacion(mes != null ? Integer.valueOf(mes) : null);
		}
		if (etapa != null) {
			command.setEtapa(etapa);
		}
		if (anioEjec != null) {
			command.setAnoEjecucionGasto(anioEjec != null ? Integer.valueOf(anioEjec) : null);
		}
		if (mesEjec != null) {
			command.setMesEjecucionGasto(mesEjec != null ? Integer.valueOf(mesEjec) : null);
		}
        
        
        FiseFormato12BCPK id = new FiseFormato12BCPK();
		id.setAnoEjecucionGasto(command.getAnoEjecucionGasto() != null ? Integer.valueOf(command.getAnoEjecucionGasto()) : null);
		id.setAnoPresentacion(command.getAnoPresentacion() != null ? Integer.valueOf(command.getAnoPresentacion()) : null);
		id.setCodEmpresa(command.getCodEmpresa());
		id.setEtapa(command.getEtapa());
		id.setMesEjecucionGasto(command.getMesEjecucionGasto());
		id.setMesPresentacion(command.getMesPresentacion() != null ? Integer.valueOf(command.getMesPresentacion()) : null);
		FiseFormato12BC cabeceraBean = formatoService.getFormatoCabeceraById(id);
		
		String flagOper = commonService.obtenerEstadoProceso(id.getCodEmpresa(),FiseConstants.TIPO_FORMATO_12B,id.getAnoPresentacion(),id.getMesPresentacion(),id.getEtapa());
		command.setDescEstado(flagOper);
		
		command = Formato12BGartCommand.toCommandCabecera(cabeceraBean,command);
		List<FiseFormato12BD> lstFiseDetalle = formatoService.getLstFormatoDetalle(id);
		command = Formato12BGartCommand.toCommandDetalle(lstFiseDetalle, command);
		command.setTipoOperacion(tipoOperacion != null ? Integer.parseInt(tipoOperacion) : FiseConstants.VIEW);
		command.setListaEmpresas(fiseUtil.getEmpresaxUsuario(request));
		command.setListaMes(fiseUtil.getMapaMeses());
		
		if( listaError!=null && listaError.size()>0){
			model.addAttribute("listaError", listaError);
		}
		model.addAttribute("msgTrans", msg);
		
		model.addAttribute("formato12BGartCommand", command);
		
		 formato12BBusqueda=new Formato12BGartCommand();
         formato12BBusqueda.setAnioInicio(command.getAnioInicio());
         formato12BBusqueda.setAnioFin(command.getAnioFin());
         formato12BBusqueda.setMesInicio(command.getMesInicio());
         logger.info("MES INICIO CARGA ****"+command.getAnioInicio());
         logger.info("MES INICIO CARGA ****"+command.getMesInicio());
         logger.info("EMORESA CARGADA CARGA ****"+command.getCodEmpresaBusqueda());
         formato12BBusqueda.setMesFin(command.getMesFin());
         formato12BBusqueda.setEtapaBusqueda(command.getEtapaBusqueda());
         formato12BBusqueda.setCodEmpresaBusqueda(command.getCodEmpresaBusqueda());
         
		 //guardamos en sesion para cuando editamos para cargar un text o excel lo tenga la pk        
         pRequest.getPortletSession().setAttribute("codEmpresaEdit", cabeceraBean.getId().getCodEmpresa(), PortletSession.APPLICATION_SCOPE);
         pRequest.getPortletSession().setAttribute("anoPresentacionEdit", String.valueOf(cabeceraBean.getId().getAnoPresentacion()), PortletSession.APPLICATION_SCOPE);
         pRequest.getPortletSession().setAttribute("mesPresentacionEdit", String.valueOf(cabeceraBean.getId().getMesPresentacion()), PortletSession.APPLICATION_SCOPE);
         pRequest.getPortletSession().setAttribute("anoEjecucionEdit", String.valueOf(cabeceraBean.getId().getAnoEjecucionGasto()), PortletSession.APPLICATION_SCOPE);
         pRequest.getPortletSession().setAttribute("mesEjecucionEdit", String.valueOf(cabeceraBean.getId().getMesEjecucionGasto()), PortletSession.APPLICATION_SCOPE);
         pRequest.getPortletSession().setAttribute("etapaEdit", cabeceraBean.getId().getEtapa(), PortletSession.APPLICATION_SCOPE);

		return "formato12BDetalle";
	}

	@ResourceMapping("saveNuevoFormato")
	public void saveNuevoFormato(ModelMap model, ResourceRequest request, ResourceResponse response, @ModelAttribute("formato12BGartCommand") Formato12BGartCommand command) {
		String msg = "1";
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObj = new JSONObject();
		
		try {
			logger.info("aqui save formato");
			logger.info("empresa::" + command.getCodEmpresa());
			logger.info("perido:" + command.getPeridoDeclaracion());
			logger.info("empresaHiden::" + command.getCodEmpresaHidden());
			logger.info("peridoHiden::" + command.getPeridoDeclaracionHidden());
			logger.info("tipoOperacion::" + command.getTipoOperacion());
			logger.info("año presentacion::" + command.getAnoPresentacion());
			logger.info("requestEmp::" + request.getParameter("codEmpresaHidden"));
			logger.info("requestPeriodo::" + request.getParameter("peridoDeclaracionHidden"));
			logger.info("flag periodo ejecucion del bean :  "+command.getFlagPeriodoEjecucion());		
			
			Integer anoEjecucion=0;
			Integer mesEjecucion=0;
			if( "S".equals(command.getFlagPeriodoEjecucion()) ){
				anoEjecucion = command.getAnoEjecucionGasto();
				mesEjecucion = command.getMesEjecucionGasto();
			}
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

			
			FiseFormato12BCPK pkCbcr = new FiseFormato12BCPK();
			FiseFormato12BC result = null;

			if (command.getTipoOperacion() != null && command.getTipoOperacion().intValue() == FiseConstants.UPDATE) {
				command.setCodEmpresa(command.getCodEmpresaHidden().trim());
				command.setUsuarioActualizacion(themeDisplay.getUser().getLogin());
				command.setTerminalActualizacion(themeDisplay.getUser().getLoginIP());
				command.setFechaActualizacion(new Date());
				
				pkCbcr.setCodEmpresa(command.getCodEmpresaHidden().trim());
				pkCbcr.setAnoEjecucionGasto(command.getAnoEjecucionGasto());
				pkCbcr.setMesEjecucionGasto(command.getMesEjecucionGasto());
				pkCbcr.setAnoPresentacion(command.getAnoPresentacion());
				pkCbcr.setMesPresentacion(command.getMesPresentacion());
				pkCbcr.setEtapa(command.getEtapa());
				result = formatoService.getFormatoCabeceraById(pkCbcr);
				
				String flagOper = commonService.obtenerEstadoProceso(pkCbcr.getCodEmpresa(),FiseConstants.TIPO_FORMATO_12B,pkCbcr.getAnoPresentacion(),pkCbcr.getMesPresentacion(),pkCbcr.getEtapa());
				command.setDescEstado(flagOper);
				
				Formato12BGartCommand commandCabecera=Formato12BGartCommand.toCommandCabecera(result,command);
				
				if (commandCabecera != null) {
					result.setUsuarioActualizacion(command.getUsuarioActualizacion());
					result.setTerminalActualizacion(command.getTerminalActualizacion());
					result.setFechaActualizacion(new Date());
					formatoService.updateFormatoCabecera(result);
					logger.info("antes de setear costo Total digitales:::"+command.getCostoTotalCanjeLiqValeDig());
					
					List<FiseFormato12BD> lstDetalle = Formato12BGartCommand.toBeanDetalle(command);
					if (lstDetalle != null && !lstDetalle.isEmpty()) {
						for (FiseFormato12BD dtll : lstDetalle) {
							logger.info("costo Total digitales:::"+dtll.getCostoTotalCanjeLiqValeDig());
							dtll.setUsuarioCreacion(result.getUsuarioCreacion());
							 dtll.setTerminalCreacion(result.getTerminalCreacion());
							 dtll.setFechaCreacion(result.getFechaCreacion());
							dtll.setUsuarioActualizacion(themeDisplay.getUser().getLogin());
							 dtll.setTerminalActualizacion(themeDisplay.getUser().getLoginIP());
							 dtll.setFechaActualizacion(new Date());
							//formatoService.deleteFormatoObs(dtll.getId().getCodEmpresa().trim(), dtll.getId().getAnoPresentacion(), dtll.getId().getMesPresentacion(),dtll.getId().getEtapa(),dtll.getId().getAnoEjecucionGasto(), dtll.getId().getMesEjecucionGasto(),dtll.getId().getIdZonaBenef(),null);
							formatoService.updateFormatoDetalle(dtll);  
							 //dtll = formatoService.saveFormatoDetalle(dtll);
						}
					}
				}				
			} else {
				if (command != null && command.getPeridoDeclaracion().length() > 6) {
					command.setAnoPresentacion(Integer.parseInt(command.getPeridoDeclaracion().substring(0, 4)));
					command.setMesPresentacion(Integer.parseInt(command.getPeridoDeclaracion().substring(4, 6)));
					command.setEtapa(command.getPeridoDeclaracion().substring(6, command.getPeridoDeclaracion().length()));
					if( "S".equals(command.getFlagPeriodoEjecucion()) ){
						command.setAnoEjecucionGasto(anoEjecucion);
						command.setMesEjecucionGasto(mesEjecucion);
					}else{
						command.setAnoEjecucionGasto(command.getAnoPresentacion());
						command.setMesEjecucionGasto(command.getMesPresentacion());
					}
				}		
				command.setUsuarioCreacion(themeDisplay.getUser().getLogin());
				command.setTerminalCreacion(themeDisplay.getUser().getLoginIP());
				command.setFechaCreacion(new Date());

				FiseGrupoInformacion grupoInfo = null;
				long idGrupoInf = commonService.obtenerIdGrupoInformacion(command.getAnoPresentacion().longValue(), command.getMesPresentacion().longValue(),FiseConstants.MENSUAL.trim());
				if (idGrupoInf != 0) {
					grupoInfo = commonService.obtenerFiseGrupoInformacionByPK(idGrupoInf);
					command.setDescGrupo(grupoInfo.getDescripcion());
				}
				command.setIdGrupoInf((int) idGrupoInf);
                 
				result = formatoService.saveFormatoCabecera(Formato12BGartCommand.toBeanCabecera(command));

				if (result != null) {					
					command.setDescEstado("Abierto");
					List<FiseFormato12BD> lstDetalle = Formato12BGartCommand.toBeanDetalle(command);
					if (lstDetalle != null && !lstDetalle.isEmpty()) {
						for (FiseFormato12BD dtll : lstDetalle) {
							dtll.setUsuarioCreacion(result.getUsuarioCreacion());
							dtll.setTerminalCreacion(result.getTerminalCreacion());
							dtll.setFechaCreacion(result.getFechaCreacion());
							dtll = formatoService.saveFormatoDetalle(dtll);
						}
					}			
				}
				
				command.setTipoOperacion(FiseConstants.UPDATE);
				jsonObj.put("codEmpresaHidden", command.getCodEmpresa());
				jsonObj.put("peridoDeclaracionHidden", command.getPeridoDeclaracion());
				jsonObj.put("descGrupo", command.getDescGrupo());
				jsonObj.put("descEstado", command.getDescEstado());
				jsonObj.put("anoEjecucionGasto", command.getAnoEjecucionGasto());
				jsonObj.put("mesEjecucionGasto", command.getMesEjecucionGasto());
				jsonObj.put("mesPresentacion", command.getMesPresentacion());
				jsonObj.put("anoPresentacion", command.getAnoPresentacion());
				jsonObj.put("etapa", command.getEtapa());
				
			}		
			model.addAttribute("formato12BGartCommand", command);				
		} catch (DataIntegrityViolationException e) {
			logger.info("entor  constraint");
			msg = "-1";
			e.printStackTrace();
		} catch (PortalException e) {
			msg = "-2";
			e.printStackTrace();
		} catch (SystemException e) {
			msg = "-3";
			e.printStackTrace();
		} catch (Exception e) {
             msg = "-4";
            e.printStackTrace();
		} finally {
			try {
				jsonObj.put("msg", msg);
				jsonArray.put(jsonObj);
				logger.info(jsonArray.toString());
				response.setContentType("application/json");
				PrintWriter pw = response.getWriter();
				pw.write(jsonArray.toString());
				pw.flush();
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

	}
	
	@ResourceMapping("deleteFormato")
	public void deleteFormato(ModelMap model, ResourceRequest request, ResourceResponse response,
			  @ModelAttribute("formato12BGartCommand") Formato12BGartCommand command) {
		
		logger.info("empresa::***"+request.getParameter("codEmpresa")+"***command**"+command.getCodEmpresa());
		logger.info("mes::***"+request.getParameter("mesPresentacion")+"*****"+command.getMesPresentacion());
		logger.info("anio::***"+request.getParameter("anoPresentacion")+"*****"+command.getAnoPresentacion());
		logger.info("mesEjec::***"+request.getParameter("mesEjecucionGasto")+"*****"+command.getMesEjecucionGasto());
		logger.info("anioEjec::***"+request.getParameter("anoEjecucionGasto")+"*****"+command.getAnoEjecucionGasto());
		logger.info("etapa::***"+request.getParameter("etapa")+"*****"+command.getEtapa());
		logger.info("eliminando....");
		
		FiseFormato12BCPK id = new FiseFormato12BCPK();
		id.setAnoEjecucionGasto(command.getAnoEjecucionGasto() != null ? Integer.valueOf(command.getAnoEjecucionGasto()) : null);
		id.setAnoPresentacion(command.getAnoPresentacion() != null ? Integer.valueOf(command.getAnoPresentacion()) : null);
		id.setCodEmpresa(command.getCodEmpresa());
		id.setEtapa(command.getEtapa());
		id.setMesEjecucionGasto(command.getMesEjecucionGasto());
		id.setMesPresentacion(command.getMesPresentacion() != null ? Integer.valueOf(command.getMesPresentacion()) : null);
		FiseFormato12BC cabeceraBean = formatoService.getFormatoCabeceraById(id);
	
		
		
		String msj="1";
		try {
			 formatoService.deleteFormatoObs(cabeceraBean.getId().getCodEmpresa(), 
						cabeceraBean.getId().getAnoPresentacion(), cabeceraBean.getId().getMesPresentacion(), 
						cabeceraBean.getId().getEtapa(), cabeceraBean.getId().getAnoEjecucionGasto(), 
						cabeceraBean.getId().getMesEjecucionGasto(), null,null);
			 formatoService.deleteFormatoDetalle(cabeceraBean.getId().getCodEmpresa(), 
					cabeceraBean.getId().getAnoPresentacion(), cabeceraBean.getId().getMesPresentacion(), 
					cabeceraBean.getId().getEtapa(), cabeceraBean.getId().getAnoEjecucionGasto(), 
					cabeceraBean.getId().getMesEjecucionGasto(), null);
			 formatoService.deleteFormatoCabecera(id);
			
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			msj="-1";
		} catch (Exception e) {
			e.printStackTrace();
			msj="-2";
		}finally{
			PrintWriter pw;
			try {
				pw = response.getWriter();
				pw.write(msj);
				pw.flush();
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		
	
	}
	
	@ResourceMapping("showReporte")
	public void showReporte(ResourceRequest request,ResourceResponse response, @ModelAttribute("formato12BGartCommand")Formato12BGartCommand command) {
		try {
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();
	        
		    JSONArray jsonArray = new JSONArray();	
		     
		    
		    logger.info("EMPRESA::"+command.getCodEmpresaHidden().trim());
		    logger.info("PERIODOD::"+command.getPeridoDeclaracionHidden().trim());
		    logger.info("MES PRESENTACION::"+command.getMesPresentacion());
		    logger.info("ANO PRESENTACION::"+command.getAnoPresentacion());
		    logger.info("DES MES::"+command.getDescMesEjec());
		    logger.info("ETAPA::"+command.getEtapa());
		    
		 
		    
		    String nombreReporte = request.getParameter("nombreReporte").trim();
		    String nombreArchivo = request.getParameter("nombreArchivo").trim();
		    String tipoFormato = FiseConstants.TIPO_FORMATO_12B;
		    String tipoArchivo = request.getParameter("tipoReporte").trim();
		   
		    logger.info("nombreReporte::"+nombreReporte);
		    logger.info("nombreArchivo::"+nombreArchivo);
		    
		    session.setAttribute("nombreReporte",nombreReporte);
		    session.setAttribute("nombreArchivo",nombreArchivo);
		    session.setAttribute("tipoFormato",tipoFormato);
		    session.setAttribute("tipoArchivo",tipoArchivo);

		    
		
		    FiseFormato12BCPK pk=new FiseFormato12BCPK();
		    pk.setCodEmpresa(command.getCodEmpresaHidden());
		    pk.setAnoPresentacion(command.getAnoPresentacion());
		    pk.setMesPresentacion(command.getMesPresentacion());
		    pk.setAnoEjecucionGasto(command.getAnoEjecucionGasto());
		    pk.setMesEjecucionGasto(command.getMesEjecucionGasto());
		    pk.setEtapa(command.getEtapa());
		    
		    FiseFormato12BC formato=formatoService.getFormatoCabeceraById(pk);
		    if(formato!=null){
		    	List<FiseFormato12BD> detalle=formatoService.getLstFormatoDetalle(pk);
		    	if(detalle!=null && !detalle.isEmpty()){
		    		formato.setFiseFormato12BDs(detalle);
		    	}
		    	Formato12BCBean bean=formatoService.estructurarFormato12BBeanByFiseFormato12BC(formato);
		    	bean.setDescEmpresa(formato.getAdmEmpresa().getDscCortaEmpresa());
	        	bean.setDescMesPresentacion(FiseUtil.descripcionMes(formato.getId().getMesPresentacion()));
	        	bean.setDescMesEjecucion(FiseUtil.descripcionMes(formato.getId().getMesEjecucionGasto()));
	        	session.setAttribute("mapa", formatoService.mapearParametrosFormato12B(bean));
		    	
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


	@ResourceMapping("showValidacion")
	public void showValidacion(ModelMap model, ResourceRequest request,ResourceResponse response,@ModelAttribute("formato12BGartCommand")Formato12BGartCommand command) {
	
	HttpServletRequest req = PortalUtil.getHttpServletRequest(request);	        
    HttpSession session = req.getSession();
	
	ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
	try {
		   JSONArray jsonArray = new JSONArray();	
	   
		logger.info("EMPRESA::"+command.getCodEmpresaHidden().trim());
	    logger.info("PERIODOD::"+command.getPeridoDeclaracionHidden().trim());
	    logger.info("MES PRESENTACION::"+command.getMesPresentacion());
	    logger.info("ANO PRESENTACION::"+command.getAnoPresentacion());
	    logger.info("DES MES::"+command.getDescMesEjec());
	    logger.info("ETAPA::"+command.getEtapa());
	    
	   FiseFormato12BCPK pk=new FiseFormato12BCPK();
	    pk.setCodEmpresa(command.getCodEmpresaHidden());
	    pk.setAnoPresentacion(command.getAnoPresentacion());
	    pk.setMesPresentacion(command.getMesPresentacion());
	    pk.setAnoEjecucionGasto(command.getAnoEjecucionGasto());
	    pk.setMesEjecucionGasto(command.getMesEjecucionGasto());
	    pk.setEtapa(command.getEtapa());
	    
	    FiseFormato12BC formato=formatoService.getFormatoCabeceraById(pk);
	    
	    if( formato!=null ){
	    	List<FiseFormato12BD> lst=formatoService.getLstFormatoDetalle(formato.getId());
	    	if(lst!=null && !lst.isEmpty()){
	    		formato.setFiseFormato12BDs(lst);
	    	}
	    	Formato12A12BGeneric formato12Generic = new Formato12A12BGeneric(formato);
	    	int i = commonService.validarFormatos_12A12B(formato12Generic, FiseConstants.NOMBRE_FORMATO_12B, themeDisplay.getUser().getLogin(), themeDisplay.getUser().getLogin());
		    if(i==0){
		    	cargarListaObservaciones(formato.getFiseFormato12BDs());
		    	
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
		    	fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_FORMATO_VAL, FiseConstants.NOMBRE_EXCEL_VALIDACION_F12B, FiseConstants.NOMBRE_HOJA_VALIDACION, listaObservaciones);
		    	
		    	
		    }else{
		    	
		    }
	    }
	    
	    response.setContentType("application/json");
	    PrintWriter pw = response.getWriter();
	    logger.info(jsonArray.toString());
	    pw.write(jsonArray.toString());
	    pw.flush();
	    pw.close();
	    
	} catch (Exception e) {
		e.printStackTrace();
	}
}
	
	
	@ResourceMapping("showReporteValidacion")
	public void showReporteValidacion(ResourceRequest request,ResourceResponse response,@ModelAttribute("formato12BGartCommand")Formato12BGartCommand command) {
		try {
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();
	        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
	        
		    JSONArray jsonArray = new JSONArray();	
		    
		    String nombreReporte = request.getParameter("nombreReporte").trim();
		    String nombreArchivo = request.getParameter("nombreArchivo").trim();
		    String tipoFormato = FiseConstants.TIPO_FORMATO_VAL;
		    String tipoArchivo = request.getParameter("tipoArchivo").trim();
		   
		    session.setAttribute("nombreReporte",nombreReporte);
		    session.setAttribute("nombreArchivo",nombreArchivo);
		    session.setAttribute("tipoFormato",tipoFormato);
		    session.setAttribute("tipoArchivo",tipoArchivo);
		    
		    if( listaObservaciones!=null ){
		    	session.setAttribute("lista", listaObservaciones);
		    }
	        
		    //add
		    FiseFormato12BCPK pk=new FiseFormato12BCPK();
		    pk.setCodEmpresa(command.getCodEmpresaHidden());
		    pk.setAnoPresentacion(command.getAnoPresentacion());
		    pk.setMesPresentacion(command.getMesPresentacion());
		    pk.setAnoEjecucionGasto(command.getAnoEjecucionGasto());
		    pk.setMesEjecucionGasto(command.getMesEjecucionGasto());
		    pk.setEtapa(command.getEtapa());
		    FiseFormato12BC formato=formatoService.getFormatoCabeceraById(pk);
		    
		    CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12B);
	    	String descripcionFormato = "";
	    	if( tabla!=null ){
	    		descripcionFormato = tabla.getDescripcionTabla();
	    	}
	    	Map<String, Object> mapa = new HashMap<String, Object>();
		    mapa.put(FiseConstants.PARAM_IMG_LOGOTIPO, session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg"));
			mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
			mapa.put(FiseConstants.PARAM_ANO_PRES_F12B, command.getAnoPresentacion().longValue());
		   	mapa.put(FiseConstants.PARAM_DESC_MES_PRES_F12B,FiseUtil.descripcionMes(command.getMesPresentacion()) );
		   	mapa.put(FiseConstants.PARAM_USUARIO, themeDisplay.getUser().getLogin());
			mapa.put(FiseConstants.PARAM_NOMBRE_FORMATO, descripcionFormato);
		   	mapa.put(FiseConstants.PARAM_NRO_OBSERVACIONES, (listaObservaciones!=null && !listaObservaciones.isEmpty())?listaObservaciones.size():0);
		  //add
		   	mapa.put(FiseConstants.PARAM_DESC_EMPRESA,formato.getAdmEmpresa().getDscCortaEmpresa() );
		   	mapa.put(FiseConstants.PARAM_ETAPA, mapaEtapa.get(formato.getId().getEtapa()));
		   	
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
	public void envioDefinitivo(ResourceRequest request,ResourceResponse response,
			@ModelAttribute("formato12BGartCommand")Formato12BGartCommand command) {
		
		FiseFormato12BC formato = null;
		try {		
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
	        HttpSession session = httpRequest.getSession();
	        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
	        JSONObject jsonObj = new JSONObject(); 
		    //FileEntry archivo=null;
		    List<FileEntryJSP> listaArchivo = new ArrayList<FileEntryJSP>(); 	   
		    
		    Formato12BCBean bean = new Formato12BCBean();
		    Map<String, Object> mapa = null;
		    String directorio = null;
		   
          //cambios		    
		    boolean valorFormato = false;
		    boolean valorActa = false;			
			String respuestaEmail ="";	
		   
		    logger.info("EMPRESA::"+command.getCodEmpresaHidden().trim());
		    logger.info("PERIODOD::"+command.getPeridoDeclaracionHidden().trim());
		    logger.info("MES PRESENTACION::"+command.getMesPresentacion());
		    logger.info("ANO PRESENTACION::"+command.getAnoPresentacion());
		    logger.info("DES MES::"+command.getDescMesEjec());
		    
		   FiseFormato12BCPK pk=new FiseFormato12BCPK();
		   pk.setCodEmpresa(command.getCodEmpresaHidden());
		   pk.setAnoPresentacion(command.getAnoPresentacion());
		   pk.setMesPresentacion(command.getMesPresentacion());
		   pk.setAnoEjecucionGasto(command.getAnoEjecucionGasto());
		   pk.setMesEjecucionGasto(command.getMesEjecucionGasto());
		   pk.setEtapa(command.getEtapa());
		    
		    formato=formatoService.getFormatoCabeceraById(pk);
	        if( formato!=null ){
	        	List<FiseFormato12BD> lst=formatoService.getLstFormatoDetalle(formato.getId());
		    	if(lst!=null && !lst.isEmpty()){
		    		formato.setFiseFormato12BDs(lst);
		    	}
	        	bean = formatoService.estructurarFormato12BBeanByFiseFormato12BC(formato);
	        	bean.setDescEmpresa(formato.getAdmEmpresa().getDscCortaEmpresa());
	        	bean.setDescMesPresentacion(FiseUtil.descripcionMes(formato.getId().getMesPresentacion()));
	        	bean.setDescMesEjecucion(FiseUtil.descripcionMes(formato.getId().getMesEjecucionGasto()));
	        	mapa = formatoService.mapearParametrosFormato12B(bean);
	        		
	        	CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12B);
	        	String descripcionFormato = "";
	        	if( tabla!=null ){
	        		descripcionFormato = tabla.getDescripcionTabla();
	        	}
	        	
	        	Formato12A12BGeneric formato12Generic = new Formato12A12BGeneric(formato);
	        	int i = commonService.validarFormatos_12A12B(formato12Generic, FiseConstants.NOMBRE_FORMATO_12B, themeDisplay.getUser().getLogin(), themeDisplay.getUser().getLoginIP());
			    if(i==0){
			    	cargarListaObservaciones(formato.getFiseFormato12BDs());
			    } 	
			  //cambios flag observacion
			    String flagEnvioObs = "";
			    String periodoEnvio = command.getPeridoDeclaracionHidden().trim();
				logger.info("Periodo para comparar :" +periodoEnvio);
				List<FisePeriodoEnvio> listaPeriodoEnvio = periodoService.listarFisePeriodoEnvioMesAnioEtapa(command.getCodEmpresaHidden().trim(), FiseConstants.NOMBRE_FORMATO_12B);
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
						mapa.put(FiseConstants.PARAM_ANO_EJEC_F12B_R, (long)formato.getId().getAnoEjecucionGasto());
						mapa.put(FiseConstants.PARAM_DESC_MES_EJEC_F12B,FiseUtil.descripcionMes(formato.getId().getMesEjecucionGasto()));
						mapa.put(FiseConstants.PARAM_FECHA_REGISTRO, formato.getFechaCreacion());
						mapa.put(FiseConstants.PARAM_USUARIO_REGISTRO, formato.getUsuarioCreacion());
						String dirCheckedImage = session.getServletContext().getRealPath("/reports/checked.jpg");
						String dirUncheckedImage = session.getServletContext().getRealPath("/reports/unchecked.jpg");
						mapa.put(FiseConstants.PARAM_IMG_CHECKED, dirCheckedImage);
						mapa.put(FiseConstants.PARAM_IMG_UNCHECKED, dirUncheckedImage);
						boolean cumplePlazo = false;
						cumplePlazo = commonService.fechaEnvioCumplePlazo(
								FiseConstants.TIPO_FORMATO_12B, 
								formato.getId().getCodEmpresa(), 
								formato.getId().getAnoPresentacion().longValue(), 
								formato.getId().getMesPresentacion().longValue(), 
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
						mapa.put(FiseConstants.PARAM_ETAPA, mapaEtapa.get(formato.getId().getEtapa()));
		    	   }
		    	   
		    	   
			        /**REPORTE FORMATO 12B*/
		    	   String nombreReporte = request.getParameter("nombreReporte").trim();
				   String nombreArchivo = request.getParameter("nombreArchivo").trim();
				 
			       nombreReporte = "formato12B";
			       nombreArchivo = nombreReporte;
			       directorio =  "/reports/"+nombreReporte+".jasper";
			       File reportFile = new File(session.getServletContext().getRealPath(directorio));
			       byte[] bytes = null;
			       bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), mapa, new JREmptyDataSource());
			       if (bytes != null) {
			    	   //session.setAttribute("bytes1", bytes);
			    	   String nombre = FormatoUtil.nombreIndividualFormato(formato.getId().getCodEmpresa(), formato.getId().getAnoPresentacion().longValue(), formato.getId().getMesPresentacion().longValue(), FiseConstants.TIPO_FORMATO_12B);
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
				    	   String nombre = FormatoUtil.nombreIndividualAnexoObs(formato.getId().getCodEmpresa(), formato.getId().getAnoPresentacion().longValue(), formato.getId().getMesPresentacion().longValue(), FiseConstants.TIPO_FORMATO_12B);
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
			       nombreReporte = "gastoMensualIndividual";
			       nombreArchivo = nombreReporte;
			       directorio =  "/reports/"+nombreReporte+".jasper";
			       File reportFile3 = new File(session.getServletContext().getRealPath(directorio));
			       byte[] bytes3 = null;
			       bytes3 = JasperRunManager.runReportToPdf(reportFile3.getPath(), mapa, new JREmptyDataSource());
			       if (bytes3 != null) {
			    	   session.setAttribute("bytesActaEnvio", bytes3);
			    	   String nombre = FormatoUtil.nombreIndividualActaRemision(formato.getId().getCodEmpresa(), formato.getId().getAnoPresentacion().longValue(), formato.getId().getMesPresentacion().longValue(), FiseConstants.TIPO_FORMATO_12B);
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
			       //guardamos la fecha de envio, en este momento porque necesitamos la fecha de envio para mandar al reporte
		    	  /* Formato12BCBean form = new Formato12BCBean();
		    	   form.setUsuario(themeDisplay.getUser().getLogin());
		    	   form.setTerminal(themeDisplay.getUser().getLoginIP());*/
		    	   /**actualizamos  la fecha de envio*/	
		    	   String valorActuaizar = "0";
			       if(valorFormato && valorActa){
			    	   //formatoActualizar =formatoService.modificarEnvioDefinitivoFormato12BC(form, formato);
			    	   valorActuaizar = formatoService.modificarEnvioDefinitivoFormato12BC(
			    			   themeDisplay.getUser().getLogin(), 
			    			   themeDisplay.getUser().getLoginIP(),formato);
			       }
			       if(valorActuaizar.equals("1")){
			    	   if( listaArchivo!=null && listaArchivo.size()>0 ){			    	   
			    		   respuestaEmail = fiseUtil.enviarMailsAdjunto(
				    			   request,
				    			   listaArchivo, 
				    			   formato.getAdmEmpresa().getDscCortaEmpresa(),
				    			   formato.getId().getAnoPresentacion().longValue(),
				    			   formato.getId().getMesPresentacion().longValue(),
				    			   FiseConstants.TIPO_FORMATO_12B,
				    			   descripcionFormato,FiseConstants.MENSUAL,
				    			   null,
				    			   null);
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
			    }//fin del else flag obsevacion NO    	   	       
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

	
	@ResourceMapping("showReporteActaEnvio")
	public void showReporteActaEnvio(ResourceRequest request,ResourceResponse response,@ModelAttribute("formato12BGartCommand")Formato12BGartCommand command) {
		try {
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
			HttpSession session = httpRequest.getSession();
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			JSONArray jsonArray = new JSONArray();	
			    
			
			
			String tipoFormato = FiseConstants.TIPO_FORMATO_ACTAENVIO;
			String tipoArchivo = FiseConstants.FORMATO_EXPORT_PDF;
			
			CfgTabla tabla = tablaService.obtenerCfgTablaByPK(FiseConstants.ID_TABLA_FORMATO12B);
			String descripcionFormato = "";
			if( tabla!=null ){
				descripcionFormato = tabla.getDescripcionTabla();
			}
			
			    
			String nombreReporte = "gastoMensualIndividual";
		    String nombreArchivo = nombreReporte;
			

		    FiseFormato12BCPK pk=new FiseFormato12BCPK();
		    pk.setCodEmpresa(command.getCodEmpresaHidden());
		    pk.setAnoPresentacion(command.getAnoPresentacion());
		    pk.setMesPresentacion(command.getMesPresentacion());
		    pk.setAnoEjecucionGasto(command.getAnoEjecucionGasto());
		    pk.setMesEjecucionGasto(command.getMesEjecucionGasto());
		    pk.setEtapa(command.getEtapa());
		    
		    FiseFormato12BC formato=formatoService.getFormatoCabeceraById(pk);
			
			if( formato!=null ){
				List<FiseFormato12BD> detalle=formatoService.getLstFormatoDetalle(pk);
		    	if(detalle!=null && !detalle.isEmpty()){
		    		formato.setFiseFormato12BDs(detalle);
		    	}
				Map<String, Object> mapa = new HashMap<String, Object>();
				mapa.put(FiseConstants.PARAM_IMG_LOGOTIPO, session.getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg"));
				mapa.put(JRParameter.REPORT_LOCALE, Locale.US);
				mapa.put(FiseConstants.PARAM_USUARIO, themeDisplay.getUser().getLogin());
				mapa.put(FiseConstants.PARAM_NOMBRE_FORMATO, descripcionFormato);
				mapa.put(FiseConstants.PARAM_FECHA_ENVIO, formato.getFechaEnvioDefinitivo());
				mapa.put(FiseConstants.PARAM_NRO_OBSERVACIONES, (listaObservaciones!=null && !listaObservaciones.isEmpty())?listaObservaciones.size():0);
				mapa.put(FiseConstants.PARAM_MSG_OBSERVACIONES, (listaObservaciones!=null && !listaObservaciones.isEmpty())?FiseConstants.MSG_OBSERVACION_REPORTE_LLENO:FiseConstants.MSG_OBSERVACION_REPORTE_VACIO);
				mapa.put(FiseConstants.PARAM_ANO_EJEC_F12B_R, (long)formato.getId().getAnoEjecucionGasto());
				mapa.put(FiseConstants.PARAM_DESC_MES_EJEC_F12B, FiseUtil.descripcionMes(formato.getId().getMesEjecucionGasto()));
				
				//mapa.put(FiseConstants.PARAM_ANO_INICIO_VIGENCIA, (long)formato.getId().getAnoEjecucionGasto());
			//	mapa.put(FiseConstants.PARAM_ANO_FIN_VIGENCIA,(long) formato.getId().getMesEjecucionGasto());
				mapa.put(FiseConstants.PARAM_FECHA_REGISTRO, formato.getFechaCreacion());
				mapa.put(FiseConstants.PARAM_USUARIO_REGISTRO, formato.getUsuarioCreacion());
				String dirCheckedImage = session.getServletContext().getRealPath("/reports/checked.jpg");
				String dirUncheckedImage = session.getServletContext().getRealPath("/reports/unchecked.jpg");
				mapa.put(FiseConstants.PARAM_IMG_CHECKED, dirCheckedImage);
				mapa.put(FiseConstants.PARAM_IMG_UNCHECKED, dirUncheckedImage);
				boolean cumplePlazo = false;
				cumplePlazo = commonService.fechaEnvioCumplePlazo(
						FiseConstants.TIPO_FORMATO_12B, 
						formato.getId().getCodEmpresa(), 
						formato.getId().getAnoPresentacion().longValue(), 
						formato.getId().getMesPresentacion().longValue(), 
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
				mapa.put(FiseConstants.PARAM_DESC_EMPRESA, formato.getAdmEmpresa().getDscCortaEmpresa());
				mapa.put(FiseConstants.PARAM_ANO_PRESENTACION, (long)formato.getId().getAnoPresentacion());
				mapa.put(FiseConstants.PARAM_DESC_MES_PRESENTACION, FiseUtil.descripcionMes(formato.getId().getMesPresentacion()));
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

	@ResourceMapping("showRptEnvioDefinitivo")
	public void showRptEnvioDefinitivo(ResourceRequest request,ResourceResponse response,@ModelAttribute("formato12BGartCommand")Formato12BGartCommand command) {
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

	
	public void cargarListaObservaciones(List<FiseFormato12BD> listaDetalle){
		int cont=0;
		listaObservaciones = new ArrayList<MensajeErrorBean>();
		for (FiseFormato12BD detalle : listaDetalle) {
			detalle.setFiseFormato12BDObs(formatoService.getLstFormatoObs(detalle));
			
			List<FiseFormato12BDOb> listaObser = formatoService.getLstFormatoObs(detalle); //formato14Service.listarFormato14BDObByFormato14BD(detalle);
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
				//obs.setDescripcion(mapaErrores.get(observacion.getFiseObservacion().getIdObservacion()));
				obs.setDescripcion(observacion.getFiseObservacion().getDescripcion());
				
				listaObservaciones.add(obs);
			}
		}
	}

}
