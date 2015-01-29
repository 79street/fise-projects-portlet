package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.Formato12A12BGeneric;
import gob.osinergmin.fise.bean.Formato12BCBean;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
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

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.exception.ConstraintViolationException;
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
import com.liferay.portal.kernel.util.WebKeys;
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

	private String nameEstado;
	private String nameGrupo;

	List<MensajeErrorBean> listaObservaciones;
	List<FisePeriodoEnvio> listaPeriodoEnvio;
	
	private Formato12BGartCommand formato12BBusqueda;

	@RequestMapping
	public String defaultView(ModelMap model, RenderRequest renderRequest, RenderResponse renderResponse, @ModelAttribute("formato12BGartCommand") Formato12BGartCommand command) {

		command.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));
		command.setListaMes(fiseUtil.getMapaMeses());
		
		
			System.out.println("volvio entrar::");
			if(formato12BBusqueda!=null && formato12BBusqueda.getAnioInicio()!=null){
				System.out.println("volvio entrar anio inicio::"+formato12BBusqueda.getAnioInicio());
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
				System.out.println("volvio entrar MES INICIO::"+formato12BBusqueda.getMesInicio());
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
            	System.out.println("comman difente de nulo");
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
			System.out.println("codEmpresa::" + command.getCodEmpresa());
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
	
		
	@ResourceMapping("loadCostoUnitario")
	public void loadCostoUnitario(ModelMap model, ResourceRequest request, ResourceResponse response, @ModelAttribute("formato12BGartCommand") Formato12BGartCommand command) {
		try {
			System.out.println("codEmpresa::" + command.getCodEmpresa());
			System.out.println("periodo::" + command.getPeridoDeclaracion());
			
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
			System.out.println(jsonArray.toString());
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
		System.out.println("en nuevo formato");
		String codEmpresa = renderRequest.getParameter("codEmpresa");
		String periodo = renderRequest.getParameter("periodoDeclaracion");
		String tipoOperacion = renderRequest.getParameter("tipoOperacion");
		String error = renderRequest.getParameter("error");

		String anio = renderRequest.getParameter("anoPresentacion");
		String mes = renderRequest.getParameter("mesPresentacion");
		String etapa = renderRequest.getParameter("etapa");
		String anioEjec = renderRequest.getParameter("anoEjecucionGasto");
		String mesEjec = renderRequest.getParameter("mesEjecucionGasto");

		System.out.println("anio::busqueda "+command.getAnioInicio());
		System.out.println("mes:: busqueda"+command.getMesInicio());
		System.out.println("etapa ::: busqueda"+command.getEtapaBusqueda());
		
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
			System.out.println("aquisito update");
			FiseFormato12BCPK id = new FiseFormato12BCPK();
			id.setAnoEjecucionGasto(anioEjec != null ? Integer.valueOf(anioEjec) : null);
			id.setAnoPresentacion(anio != null ? Integer.valueOf(anio) : null);
			id.setCodEmpresa(codEmpresa);
			id.setEtapa(etapa);
			id.setMesEjecucionGasto(mesEjec != null ? Integer.valueOf(mesEjec) : null);
			id.setMesPresentacion(mes != null ? Integer.valueOf(mes) : null);
			FiseFormato12BC cabeceraBean = formatoService.getFormatoCabeceraById(id);
			command = Formato12BGartCommand.toCommandCabecera(cabeceraBean,command);
			List<FiseFormato12BD> lstFiseDetalle = formatoService.getLstFormatoDetalle(id);
			command = Formato12BGartCommand.toCommandDetalle(lstFiseDetalle, command);
			
		} 
		command.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));
		command.setTipoOperacion((tipoOperacion != null && tipoOperacion.equalsIgnoreCase(String.valueOf(FiseConstants.UPDATE))) ? FiseConstants.UPDATE : FiseConstants.ADD);
		command.setListaMes(fiseUtil.getMapaMeses());
		
		
		model.addAttribute("formato12BGartCommand", command);
          formato12BBusqueda=new Formato12BGartCommand();
          formato12BBusqueda.setAnioInicio(command.getAnioInicio());
          formato12BBusqueda.setAnioFin(command.getAnioFin());
          formato12BBusqueda.setMesInicio(command.getMesInicio());
          formato12BBusqueda.setMesFin(command.getMesFin());
          formato12BBusqueda.setEtapaBusqueda(command.getEtapaBusqueda());
          formato12BBusqueda.setCodEmpresaBusqueda(command.getCodEmpresaBusqueda());
          
          System.out.println("ANIO INICIO CARGA NUEVO****"+command.getAnioInicio());
          System.out.println("MES INICIO CARGA NUEVO****"+command.getMesInicio());
        
		
		return "formato12BDetalle";
	}

	@ActionMapping(params = "action=uploadFile")
	public void uploadFile(ActionRequest request, ActionResponse response, @ModelAttribute("formato12BGartCommand") Formato12BGartCommand command) {
		System.out.println("aqui en upload controller");
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(request);
		
		String tipoaccion = uploadPortletRequest.getParameter("tipoOperacion");
		String typeFile = uploadPortletRequest.getParameter("typeFile");
		
		System.out.println("TIPO FILE ::"+typeFile);
		String codEmp = null;
		String periodo = null;
		
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
		System.out.println("tipoaccion::" + tipoaccion);
		System.out.println("codEmp::" + codEmp);
		System.out.println("periodo::" + periodo);
		
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
		response.setRenderParameter("anoEjecucionGasto", cabeceraPk.getAnoPresentacion() + "");
		response.setRenderParameter("mesEjecucionGasto", cabeceraPk.getMesPresentacion() + "");
		
		

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
						System.out.println("empresa::" + pk.getCodEmpresa() + "vs" + resultPk.getCodEmpresa());
						System.out.println("anio::" + pk.getAnoPresentacion() + "vs" + resultPk.getAnoPresentacion());
						System.out.println("mes::" + pk.getMesPresentacion() + "vs" + resultPk.getMesPresentacion());
						System.out.println("etapa::" + pk.getEtapa() + "vs" + resultPk.getEtapa());

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

								nameEstado = FiseConstants.ESTADO_FECHAENVIO_POR_ENVIAR;
								fise12BC = formatoService.saveFormatoCabecera(fise12BC);
								System.out.println("fise12BC" + fise12BC);
								if (fise12BC != null) {
									System.out.println("diferente de nulo");
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
							throw new Exception("La empresa y/o mes año no coinciden con el seleccionado");
						}
					}
				}/* else {
					throw new Exception("Libro vacio");
				}*/

			} else {
				throw new Exception("Archvivo vacio");
			}
		}catch(ConstraintViolationException c){
			c.printStackTrace();
			MensajeErrorBean msg = new MensajeErrorBean();
			msg.setId(cont);
			msg.setDescripcion("El formato ya existe");
			listaError.add(msg);
			
		} catch (DataIntegrityViolationException ex) {
			ex.printStackTrace();
			MensajeErrorBean msg = new MensajeErrorBean();
			msg.setId(cont);
			msg.setDescripcion(ex.toString());
			listaError.add(msg);
			
		} catch (Exception ex) {
			MensajeErrorBean msg = new MensajeErrorBean();
			msg.setId(cont);
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
							System.out.println("COD CAMPO::"+campo.getCodCampo());
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
					System.out.println("CANTIDAD CAMPOS ::"+listaCampoRead.size());
					BufferedReader br = new BufferedReader( new InputStreamReader(is));
					//List<String> listaDetalleTxt= new ArrayList<String>();
					String sCurrentLine;
					
					List<FiseFormato12BD> lstDetalle=new ArrayList<FiseFormato12BD>();
				
					while ((sCurrentLine = br.readLine()) != null) {
						System.out.println("total::"+sCurrentLine.length());
						System.out.println(sCurrentLine);
						int posInicial=0;
						String codEm=sCurrentLine.substring(posInicial, lenghtEmpresa);
						posInicial=posInicial+lenghtEmpresa;
						System.out.println("codEm ::"+codEm+"/"+posInicial+"/"+lenghtAnioPres);//0+4
						System.out.println("sCurrentLine ::"+sCurrentLine.length());//0+4
							
						String anioPres=sCurrentLine.substring(posInicial, posInicial+lenghtAnioPres);
						System.out.println("anioPres ::"+anioPres+"/"+posInicial);
						posInicial=posInicial+lenghtAnioPres;
						String mesPres=sCurrentLine.substring(posInicial, posInicial+lenghtMesPres);
						System.out.println("mesPres ::"+mesPres+"/"+posInicial);
						posInicial=posInicial+lenghtMesPres;
						String anioEjec=sCurrentLine.substring(posInicial, posInicial+lenghtAnioEjec);
						System.out.println("anioEjec ::"+anioEjec+"/"+posInicial);
						posInicial=posInicial+lenghtAnioEjec;
						String mesEjec=sCurrentLine.substring(posInicial, posInicial+lenghtMesEjec);
						System.out.println("mesEjec ::"+mesEjec+"/"+posInicial);
						posInicial=posInicial+lenghtMesEjec;
						String idzon=sCurrentLine.substring(posInicial, posInicial+lenghtZona);
						System.out.println("idzon ::"+idzon+"/"+posInicial);
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
							
							
						System.out.println("codEm**LG ::"+codEm);
						System.out.println("anioPres**LG ::"+anioPres);
						System.out.println("mesPres**LG ::"+mesPres);
						
						System.out.println("anioEjec**LG ::"+anioEjec);
						System.out.println("mesEjec**LG ::"+mesEjec);
						System.out.println("Zona**LG ::"+idzon);
						System.out.println("numValesImp**LG ::"+numValesImp);
						System.out.println("numValesRepar**LG ::"+numValesRepar);
						System.out.println("numValesEntrDE**LG ::"+numValesEntrDE);
						System.out.println("numValesFisico**LG ::"+numValesFisico);
						System.out.println("numValesDigital**LG ::"+numValesDigital);
						System.out.println("numValeAtenciones**LG ::"+numValeAtenciones);
						System.out.println("numValeGestion**LG ::"+numValeGestion);
						System.out.println("numValeDesplazamiento**LG ::"+numValeDesplazamiento);
						System.out.println("numValeActividad**LG ::"+numValeActividad);
						System.out.println("totalreconocer**LG ::"+totalreconocer);
						  
								 
						validarCampos(anioPres, "Año de Presentación", 3, 0);//validar vacio
						validarCampos(mesPres, "Mes de Presentación", 3, 0);//validar vacio
						validarCampos(anioEjec, "Año de Ejecución", 3, 0);//validar vacio
						validarCampos(mesEjec, "Mes de Ejecución", 3, 0);//validar vacio
						validarCampos(anioPres, "Año de Presentacion", 1, 4);//validar tamaño
						validarCampos(mesPres, "Mes de Presentación", 2, 0);//validar rango
						validarCampos(anioEjec, "Año de Ejecución", 1, 4);//validar tamaño
						validarCampos(mesEjec, "Mes de Ejecución", 2, 0);//validar rango
						
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
							
						   
						    
						if(pk.getCodEmpresa().trim().equalsIgnoreCase(codEm.trim()) &&  pk.getAnoPresentacion()== Integer.parseInt(anioPres) && pk.getMesPresentacion()== Integer.parseInt(mesPres)){
								
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
								   
								   
						}else{
							throw new Exception("La empresa y/o mes año no coinciden con el seleccionado");
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
						throw new Exception("Archivo vacio");
					}
					//---
				}else{
					throw new Exception("El nombre del archivo debe corresponder al periodo a declarar ");
				}

			} 
			
			
			
		}catch(ConstraintViolationException c){
			c.printStackTrace();
			MensajeErrorBean msg = new MensajeErrorBean();
			msg.setId(cont);
			msg.setDescripcion("El formato ya existe");
			listaError.add(msg);
			
		} catch (DataIntegrityViolationException ex) {
			ex.printStackTrace();
			MensajeErrorBean msg = new MensajeErrorBean();
			msg.setId(cont);
			msg.setDescripcion(ex.toString());
			listaError.add(msg);
			
		}catch (NumberFormatException ex) {
			MensajeErrorBean msg = new MensajeErrorBean();
			msg.setId(cont);
			msg.setDescripcion(ex.toString());
			listaError.add(msg);

		} catch (Exception ex) {
			MensajeErrorBean msg = new MensajeErrorBean();
			msg.setId(cont);
			msg.setDescripcion(ex.toString());
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

		String tipoOperacion = request.getParameter("tipoOperacion");
        System.out.println("en view formato tipo operacion::"+tipoOperacion+" /command::"+command.getTipoOperacion()+"-"+command.getCodEmpresa());
		System.out.println("command busqueda::"+command.getAnioInicio());
    
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
		command = Formato12BGartCommand.toCommandCabecera(cabeceraBean,command);
		List<FiseFormato12BD> lstFiseDetalle = formatoService.getLstFormatoDetalle(id);
		command = Formato12BGartCommand.toCommandDetalle(lstFiseDetalle, command);
		command.setTipoOperacion(tipoOperacion != null ? Integer.parseInt(tipoOperacion) : FiseConstants.VIEW);
		command.setListaEmpresas(fiseUtil.getEmpresaxUsuario(request));
		command.setListaMes(fiseUtil.getMapaMeses());
		model.addAttribute("formato12BGartCommand", command);
		
		 formato12BBusqueda=new Formato12BGartCommand();
         formato12BBusqueda.setAnioInicio(command.getAnioInicio());
         formato12BBusqueda.setAnioFin(command.getAnioFin());
         formato12BBusqueda.setMesInicio(command.getMesInicio());
         System.out.println("MES INICIO CARGA ****"+command.getAnioInicio());
         System.out.println("MES INICIO CARGA ****"+command.getMesInicio());
         System.out.println("EMORESA CARGADA CARGA ****"+command.getCodEmpresaBusqueda());
         formato12BBusqueda.setMesFin(command.getMesFin());
         formato12BBusqueda.setEtapaBusqueda(command.getEtapaBusqueda());
         formato12BBusqueda.setCodEmpresaBusqueda(command.getCodEmpresaBusqueda());
		

		return "formato12BDetalle";
	}

	@ResourceMapping("saveNuevoFormato")
	public void saveNuevoFormato(ModelMap model, ResourceRequest request, ResourceResponse response, @ModelAttribute("formato12BGartCommand") Formato12BGartCommand command) {
		String msg = "1";
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObj = new JSONObject();
		
		try {
			System.out.println("aqui save formato");
			System.out.println("empresa::" + command.getCodEmpresa());
			System.out.println("perido::" + command.getPeridoDeclaracion());
			System.out.println("empresaHiden::" + command.getCodEmpresaHidden());
			System.out.println("peridoHiden::" + command.getPeridoDeclaracionHidden());
			System.out.println("tipoOperacion::" + command.getTipoOperacion());
			System.out.println("año presentacion::" + command.getAnoPresentacion());
			System.out.println("requestEmp::" + request.getParameter("codEmpresaHidden"));
			System.out.println("requestPeriodo::" + request.getParameter("peridoDeclaracionHidden"));
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
				
				Formato12BGartCommand commandCabecera=Formato12BGartCommand.toCommandCabecera(result,command);
				
				if (commandCabecera != null) {
					result.setUsuarioActualizacion(command.getUsuarioActualizacion());
					result.setTerminalActualizacion(command.getTerminalActualizacion());
					result.setFechaActualizacion(new Date());
					formatoService.updateFormatoCabecera(result);
					System.out.println("antes de setear costo Total digitales:::"+command.getCostoTotalCanjeLiqValeDig());
					
					List<FiseFormato12BD> lstDetalle = Formato12BGartCommand.toBeanDetalle(command);
					if (lstDetalle != null && !lstDetalle.isEmpty()) {
						for (FiseFormato12BD dtll : lstDetalle) {
							System.out.println("costo Total digitales:::"+dtll.getCostoTotalCanjeLiqValeDig());
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
				}
				//command.setAnoEjecucionGasto(command.getAnoEjecucionGasto());
				//command.setMesEjecucionGasto(command.getMesEjecucionGasto());
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
					command.setDescEstado(FiseConstants.ESTADO_FECHAENVIO_POR_ENVIAR);
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

			//response.setContentType("text/plain");
			model.addAttribute("formato12BGartCommand", command);
			
			
			
				
		} catch (DataIntegrityViolationException e) {
			System.out.println("entor  constraint");
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
				System.out.println(jsonArray.toString());
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
		
		System.out.println("empresa::***"+request.getParameter("codEmpresa")+"***command**"+command.getCodEmpresa());
		System.out.println("mes::***"+request.getParameter("mesPresentacion")+"*****"+command.getMesPresentacion());
		System.out.println("anio::***"+request.getParameter("anoPresentacion")+"*****"+command.getAnoPresentacion());
		System.out.println("mesEjec::***"+request.getParameter("mesEjecucionGasto")+"*****"+command.getMesEjecucionGasto());
		System.out.println("anioEjec::***"+request.getParameter("anoEjecucionGasto")+"*****"+command.getAnoEjecucionGasto());
		System.out.println("etapa::***"+request.getParameter("etapa")+"*****"+command.getEtapa());
		System.out.println("eliminando....");
		
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
		     
		    
		    System.out.println("EMPRESA::"+command.getCodEmpresaHidden().trim());
		    System.out.println("PERIODOD::"+command.getPeridoDeclaracionHidden().trim());
		    System.out.println("MES PRESENTACION::"+command.getMesPresentacion());
		    System.out.println("ANO PRESENTACION::"+command.getAnoPresentacion());
		    System.out.println("DES MES::"+command.getDescMesEjec());
		    System.out.println("ETAPA::"+command.getEtapa());
		    
		 
		    
		    String nombreReporte = request.getParameter("nombreReporte").trim();
		    String nombreArchivo = request.getParameter("nombreArchivo").trim();
		    String tipoFormato = FiseConstants.TIPO_FORMATO_12B;
		    String tipoArchivo = request.getParameter("tipoReporte").trim();
		   
		    System.out.println("nombreReporte::"+nombreReporte);
		    System.out.println("nombreArchivo::"+nombreArchivo);
		    
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
	   
		System.out.println("EMPRESA::"+command.getCodEmpresaHidden().trim());
	    System.out.println("PERIODOD::"+command.getPeridoDeclaracionHidden().trim());
	    System.out.println("MES PRESENTACION::"+command.getMesPresentacion());
	    System.out.println("ANO PRESENTACION::"+command.getAnoPresentacion());
	    System.out.println("DES MES::"+command.getDescMesEjec());
	    System.out.println("ETAPA::"+command.getEtapa());
	    
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
		   	mapa.put(FiseConstants.PARAM_ETAPA, formato.getId().getEtapa());
		   	
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
		   
		    System.out.println("EMPRESA::"+command.getCodEmpresaHidden().trim());
		    System.out.println("PERIODOD::"+command.getPeridoDeclaracionHidden().trim());
		    System.out.println("MES PRESENTACION::"+command.getMesPresentacion());
		    System.out.println("ANO PRESENTACION::"+command.getAnoPresentacion());
		    System.out.println("DES MES::"+command.getDescMesEjec());
		    
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
						mapa.put(FiseConstants.PARAM_ETAPA, formato.getId().getEtapa());
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
