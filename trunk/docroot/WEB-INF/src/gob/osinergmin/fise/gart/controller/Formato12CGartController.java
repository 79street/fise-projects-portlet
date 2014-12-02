package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.Formato12CCBean;
import gob.osinergmin.fise.bean.MensajeErrorBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.AdmUbigeo;
import gob.osinergmin.fise.domain.FiseFormato12CC;
import gob.osinergmin.fise.domain.FiseFormato12CCPK;
import gob.osinergmin.fise.domain.FiseFormato12CD;
import gob.osinergmin.fise.domain.FisePeriodoEnvio;
import gob.osinergmin.fise.gart.json.Formato12CGartJSON;
import gob.osinergmin.fise.gart.service.CfgCampoGartService;
import gob.osinergmin.fise.gart.service.CfgTablaGartService;
import gob.osinergmin.fise.gart.service.CommonGartService;
import gob.osinergmin.fise.gart.service.FisePeriodoEnvioGartService;
import gob.osinergmin.fise.gart.service.Formato12CGartService;
import gob.osinergmin.fise.util.FormatoUtil;

import java.io.PrintWriter;
import java.util.ArrayList;
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

import org.apache.log4j.Logger;
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

import com.liferay.portal.util.PortalUtil;

@SessionAttributes({ "esAdministrador" })
@Controller("formato12CGartController")
@RequestMapping("VIEW")
public class Formato12CGartController {
	
	Logger logger = Logger.getLogger(Formato12CGartController.class);
	
	private static final String CRUD_CREATE = "CREATE";
	private static final String CRUD_UPDATE = "UPDATE";
	private static final String CRUD_READ = "READ";
	private static final String CRUD_READ_CREATEUPDATE = "READCREATEUPDATE";

	@Autowired
	@Qualifier("fiseUtil")
	private FiseUtil fiseUtil;

	@Autowired
	@Qualifier("formato12CGartServiceImpl")
	Formato12CGartService formatoService;
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

	List<MensajeErrorBean> listaObservaciones;
	Map<String, String> mapaErrores;
	Map<String, String> mapaSectorTipico;
	Map<String, String> mapaEmpresa;
	Map<Long, String> mapaMeses;
	Map<Long, String> mapaZonaBenef;
	Map<Long, String> mapaEtapaEjecucion;
	Map<String, String> mapaTipoDocumento;
	
	List<FisePeriodoEnvio> listaPeriodoEnvio;

	@RequestMapping
	public String defaultView(ModelMap model, RenderRequest renderRequest, RenderResponse renderResponse, @ModelAttribute("formato12CCBean") Formato12CCBean bean) {

		listaPeriodoEnvio = new ArrayList<FisePeriodoEnvio>();
		
		bean.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));
		bean.setListaMes(fiseUtil.getMapaMeses());
		bean.setAnioDesde(fiseUtil.obtenerNroAnioFechaActual());
		bean.setMesDesde(String.valueOf(Integer.parseInt(fiseUtil.obtenerNroMesFechaActual()) - 1));
		bean.setAnioHasta(fiseUtil.obtenerNroAnioFechaActual());
		bean.setMesHasta(fiseUtil.obtenerNroMesFechaActual());
		
		model.addAttribute("esAdministrador", fiseUtil.esAdministrador(renderRequest));

		mapaErrores = fiseUtil.getMapaErrores();
		mapaSectorTipico = fiseUtil.getMapaSectorTipico();
		mapaEmpresa = fiseUtil.getMapaEmpresa();
		mapaMeses = fiseUtil.getMapaMeses();
		mapaZonaBenef = fiseUtil.getMapaZonaBenef();
		mapaEtapaEjecucion = fiseUtil.getMapaEtapaEjecucion();

		return "formato12CInicio";
	}

	@ResourceMapping("busqueda")
	public void grid(ModelMap model, ResourceRequest request, ResourceResponse response, @ModelAttribute("formato12CCBean") Formato12CCBean bean) {

		try {
			response.setContentType("application/json");
			
			HttpServletRequest req = PortalUtil.getHttpServletRequest(request);
			HttpSession session = req.getSession();
			List<FiseFormato12CC> listaFormato;
			JSONArray jsonArray = new JSONArray();

			String codEmpresa = bean.getCodEmpresaB();
			String anioDesde = bean.getAnioDesde();
			String mesDesde = bean.getMesDesde();
			String anioHasta = bean.getAnioHasta();
			String mesHasta = bean.getMesHasta();
			String etapa = bean.getEtapaB();

			listaFormato = formatoService.buscarFormato12CC(
					(codEmpresa!=null&&codEmpresa!="")?FormatoUtil.rellenaDerecha(codEmpresa, ' ', 4):"", 
		  					(anioDesde!=null&&anioDesde!="")?Long.parseLong(anioDesde):0, 
		  					(mesDesde!=null&&mesDesde!="")?Long.parseLong(mesDesde):0, 
		  					(anioHasta!=null&&anioHasta!="")?Long.parseLong(anioHasta):0, 
		  					(mesHasta!=null&&mesHasta!="")?Long.parseLong(mesHasta):0, 
		  					(etapa!=null&&etapa!="")?etapa:""
		  					);

			for (FiseFormato12CC formato : listaFormato) {
				formato.setDescEmpresa(mapaEmpresa.get(formato.getId().getCodEmpresa()));
				formato.setDescMesPresentacion(fiseUtil.getMapaMeses().get(formato.getId().getMesPresentacion()));
  				
  				//grupo informacion y estado
  				if(formato.getFiseGrupoInformacion()!=null && formato.getFiseGrupoInformacion().getDescripcion()!=null){
  					formato.setDescGrupoInformacion(formato.getFiseGrupoInformacion().getDescripcion());
  				}else{
  					formato.setDescGrupoInformacion(FiseConstants.BLANCO);
  				}
  				if(formato.getFechaEnvioDefinitivo()!=null){
  					formato.setDescEstado(FiseConstants.ESTADO_FECHAENVIO_ENVIADO);
  				}else{
  					formato.setDescEstado(FiseConstants.ESTADO_FECHAENVIO_POR_ENVIAR);
  				}
  				
  				/**Obteniendo el flag de la operacion*/
  				String flagOper = commonService.obtenerEstadoProceso(formato.getId().getCodEmpresa(),FiseConstants.TIPO_FORMATO_12C,formato.getId().getAnoPresentacion(),
  						formato.getId().getMesPresentacion(), formato.getId().getEtapa());
  				logger.info("flag operacion:  "+flagOper);
  				
  				jsonArray.put(new Formato12CGartJSON().asJSONObject(formato,"",flagOper));
			}

			fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_FORMATO_12C, FiseConstants.NOMBRE_EXCEL_FORMATO12C, FiseConstants.NOMBRE_HOJA_FORMATO12C, listaFormato);

			logger.info("arreglo json:" + jsonArray);
			PrintWriter pw = response.getWriter();
			pw.write(jsonArray.toString());
			pw.flush();
			pw.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@RequestMapping(params = "action=nuevo")
	public String nuevoFormato(ModelMap model, RenderRequest renderRequest, RenderResponse renderResponse, @ModelAttribute("formato12CCBean") Formato12CCBean bean) {
		System.out.println("aqui en nuevoFormato");

		String codEmpresa = renderRequest.getParameter("codEmpresa");
		String periodo = renderRequest.getParameter("periodoDeclaracion");
		String read = renderRequest.getParameter("readonly");
		String tipoOperacion = renderRequest.getParameter("tipoOperacion");
		//String descPeriodo = renderRequest.getParameter("descripcionPeriodo");
		//String error = renderRequest.getParameter("error");

		//String codEmpresaHidden = renderRequest.getParameter("codEmpresaHidden");
		//String descPeriodoHidden = renderRequest.getParameter("descripcionPeriodoHidden");
		
		String descGrupoInformacion = renderRequest.getParameter("descGrupoInformacion");
		String descEstado = renderRequest.getParameter("descestado");
		
		//String anioInicioVigencia = renderRequest.getParameter("anioInicioVigencia");
		//String anioFinVigencia = renderRequest.getParameter("anioFinVigencia");
		
		

		System.out.println("codEmpresa::>" + codEmpresa);
		System.out.println("periodo::>" + periodo);

		/*if (error != null) {
			model.addAttribute("error", error);
		}*/

		if (codEmpresa != null) {
			bean.setCodigoEmpresa(codEmpresa);
		}
		//if (periodo != null) {
		//	bean.setPeridoDeclaracion(periodo);
		//}
		if (descGrupoInformacion != null) {
			bean.setDescGrupoInformacion(descGrupoInformacion);
		}
		if (descEstado != null) {
			bean.setDescEstado(descEstado);
		}
		

		bean.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));

		bean.setReadOnly(read != null ? Boolean.parseBoolean(read) : false);
		//bean.setTipoOperacion((tipoOperacion != null && tipoOperacion.equalsIgnoreCase(String.valueOf(FiseConstants.UPDATE))) ? FiseConstants.UPDATE : FiseConstants.ADD);

		model.addAttribute("crud", (tipoOperacion != null && tipoOperacion.equalsIgnoreCase(String.valueOf(FiseConstants.UPDATE))) ? CRUD_UPDATE : CRUD_CREATE);
		model.addAttribute("readonly", "false");

		return "formato12CCRUD";
	}
	
	@RequestMapping(params = "action=viewedit")
	public String viewEditFormato(ModelMap model, RenderRequest request, RenderResponse response, @ModelAttribute("formato12CCBean") Formato12CCBean bean) {

		try {

			String tipo = request.getParameter("tipo");
			String codEmpresa = request.getParameter("codEmpresa");
			String anio = request.getParameter("anioPresentacion");
			String mes = request.getParameter("mesPresentacion");
			String etapa = request.getParameter("etapa");
			
			String desGrupoInformacion = request.getParameter("descGrupoInformacion");
			String descEstado = request.getParameter("descEstado");

			bean.setReadOnly(true);
			bean.setCodigoEmpresa(codEmpresa.trim());
			bean.setAnioPresentacion(Long.parseLong(anio));
			bean.setMesPresentacion(Long.parseLong(mes));
			bean.setEtapa(etapa);
			bean.setListaEmpresas(fiseUtil.getEmpresaxUsuario(request));
			bean.setListaPeriodo(periodoService.listarFisePeriodoEnvioMesAnioEtapa(codEmpresa, FiseConstants.NOMBRE_FORMATO_12C));
			//bean.setPeridoDeclaracion("" + anio + "" + mes + "" + etapa);

			//bean.setDescripcionPeriodo(FiseUtil.descripcionPeriodo(Long.parseLong(bean.getMesPresentacion()), Long.parseLong(bean.getAnioPresentacion()), bean.getEtapa()));
			//bean.setCodEmpresaHidden(bean.getCodEmpresa());
			//bean.setDescripcionPeriodoHidden(bean.getPeridoDeclaracion());
			
			bean.setDescGrupoInformacion(desGrupoInformacion!=null?desGrupoInformacion:"");
			bean.setDescEstado(descEstado!=null?descEstado:"");

			if (tipo != null && tipo.equalsIgnoreCase(String.valueOf(FiseConstants.VIEW))) {
				model.addAttribute("crud", CRUD_READ);
				model.addAttribute("readonly", "true");

			}
			if (tipo != null && tipo.equalsIgnoreCase(String.valueOf(FiseConstants.UPDATE))) {
				model.addAttribute("crud", CRUD_UPDATE);
				model.addAttribute("readonly", "false");
				//bean.setTipoOperacion(FiseConstants.UPDATE);
			}

			model.addAttribute("formato12CCBean", bean);

		} catch (Exception e) {
			System.out.println("entro error view");
			e.printStackTrace();
		}
		return "formato12CCRUD";
	}
	
	@ResourceMapping("busquedaDetalle")
	public void gridDetalle(ModelMap model, ResourceRequest request, ResourceResponse response, @ModelAttribute("formato12CCBean") Formato12CCBean bean) {

		try {
			System.out.println("busquedaDetalle");

			response.setContentType("application/json");
			
			HttpServletRequest req = PortalUtil.getHttpServletRequest(request);
			HttpSession session = req.getSession();
			List<FiseFormato12CD> listaFormato;
			
			listaFormato = new ArrayList<FiseFormato12CD>();
			
			JSONObject jsonObj = new JSONObject();
			
			JSONArray jsonArray = new JSONArray();
			
			JSONArray jsonArrayImplementacion = new JSONArray();
			JSONArray jsonArrayOperativa = new JSONArray();
			
			String tipo = request.getParameter("tipo");
			
			String codEmpresa = "";
			//String periodoDeclaracion = "";

			if (tipo != null && (tipo.equalsIgnoreCase("0") || tipo.equalsIgnoreCase("1"))) {

				System.out.println("codEmpresa::>" + request.getParameter("codEmpresa"));
				System.out.println("anoPresentacion::>" + request.getParameter("anioPresentacion"));

				codEmpresa = request.getParameter("codEmpresa");
				//periodoDeclaracion = request.getParameter("anioPresentacion") + request.getParameter("mesPresentacion") + request.getParameter("etapa");

				String anioPresentacion = request.getParameter("anioPresentacion");
				String mesPresentacion = request.getParameter("mesPresentacion");
				String etapa = request.getParameter("etapa");
				
				bean.setCodigoEmpresa(codEmpresa);
				bean.setAnioPresentacion(Long.parseLong(anioPresentacion));
				bean.setMesPresentacion(Long.parseLong(mesPresentacion));
				bean.setEtapa(etapa);
				//bean.setPeriodoEnvio(periodoDeclaracion);

				FiseFormato12CC formato12CC = new FiseFormato12CC();
				
				FiseFormato12CCPK pk = new FiseFormato12CCPK();
				pk.setCodEmpresa(codEmpresa);
				pk.setAnoPresentacion(Long.parseLong(anioPresentacion));
				pk.setMesPresentacion(Long.parseLong(mesPresentacion));
				pk.setEtapa(etapa);
				
				formato12CC = formatoService.obtenerFormato12CCByPK(pk);
				
				if( formato12CC != null ){
					listaFormato = formatoService.listarFormato12CDByFormato12CC(formato12CC);
					for (FiseFormato12CD formato : listaFormato) {
						//setear algunas descripciones
						formato.setDescMesEjecucion(mapaMeses.get(formato.getId().getMesEjecucionGasto()));
						formato.setDescEtapaEjecucion(mapaEtapaEjecucion.get(formato.getId().getEtapaEjecucion()));
						formato.setDescZonaBenef(mapaZonaBenef.get(formato.getIdZonaBenef()));
						
						//jsonArray.put(new Formato12CGartJSON().asJSONObject(formato));
						
						//jsonArray.put(new Formato12CGartJSON().asJSONObject(formato));
						
						if( FiseConstants.ETAPA_EJECUCION_IMPLEMENTACION_COD == formato.getId().getEtapaEjecucion() ){
							jsonArrayImplementacion.put(new Formato12CGartJSON().asJSONObject(formato));
						}else if( FiseConstants.ETAPA_EJECUCION_OPERATIVA_COD == formato.getId().getEtapaEjecucion() ){
							jsonArrayOperativa.put(new Formato12CGartJSON().asJSONObject(formato));
						}
						
					}
				}
				
			}

			fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_FORMATO_12C, FiseConstants.NOMBRE_EXCEL_FORMATO12C, FiseConstants.NOMBRE_HOJA_FORMATO12C, listaFormato);
			
			logger.info("arreglo jsonarray:" + jsonArray);
			logger.info("arreglo jsonimplemtacion:" + jsonArrayImplementacion);
			logger.info("arreglo jsonoperativa:" + jsonArrayOperativa);
			logger.info("arreglo json:" + jsonObj);
			
			//guardamos en el jsobobj
			jsonObj.put("implementacion", jsonArrayImplementacion);
			jsonObj.put("operativa", jsonArrayOperativa);
			
			PrintWriter pw = response.getWriter();
			//pw.write(jsonArray.toString());
			pw.write(jsonObj.toString());
			//pw.write(jsonArrayImplementacion.toString());
			//pw.write(jsonArrayOperativa.toString());
			
			pw.flush();
			pw.close();

			model.addAttribute("formato12CCBean", bean);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/*@ResourceMapping("cargaPeriodoDeclaracion")
	public void cargaPeriodoDeclaracion(ModelMap model, SessionStatus status, ResourceRequest request, ResourceResponse response, 
			@RequestParam("codEmpresa") String codEmpresa, @ModelAttribute("formato12CCBean") Formato12CCBean bean) {
		try {
			logger.info("cargaPeriodoDeclaracion");
			logger.debug("-->cargaPeriodoDeclaracion");
			response.setContentType("applicacion/json");

			List<FisePeriodoEnvio> listaPeriodoEnvio = periodoService.listarFisePeriodoEnvioMesAnioEtapa(codEmpresa, FiseConstants.TIPO_FORMATO_12C);

			JSONArray jsonArray = new JSONArray();
			for (FisePeriodoEnvio periodo : listaPeriodoEnvio) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("codigoItem", periodo.getCodigoItem());
				jsonObj.put("descripcionItem", periodo.getDescripcionItem());
				jsonObj.put("flagPeriodoEjecucion", periodo.getFlagPeriodoEjecucion());
				// agregar los valores
				jsonArray.put(jsonObj);
			}

			PrintWriter pw = response.getWriter();
			pw.write(jsonArray.toString());
			pw.flush();
			pw.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}*/
	
	@ResourceMapping("cargaPeriodo")
  	public void cargaPeriodo(ModelMap model, ResourceRequest request,ResourceResponse response,@ModelAttribute("formato12CCBean") Formato12CCBean bean){
		try {			
  			response.setContentType("applicacion/json");
  			String codEmpresa = bean.getCodigoEmpresa();
  			
  			listaPeriodoEnvio = periodoService.listarFisePeriodoEnvioMesAnioEtapa(codEmpresa, FiseConstants.TIPO_FORMATO_12C);
  			bean.setListaPeriodoEnvio(listaPeriodoEnvio);
  			
  			JSONArray jsonArray = new JSONArray();
  			for (FisePeriodoEnvio periodo : bean.getListaPeriodoEnvio()) {
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
  	public void cargaFlagPeriodo(ResourceRequest request,ResourceResponse response,@ModelAttribute("formato12CCBean") Formato12CCBean bean){
		try {			
  			response.setContentType("applicacion/json");
  			String periodoEnvio = bean.getPeriodoEnvio();
  			JSONObject jsonObj = new JSONObject();
  			for (FisePeriodoEnvio p : listaPeriodoEnvio) {
				if( periodoEnvio.equals(p.getCodigoItem()) ){
					jsonObj.put("flagPeriodoEjecucion", p.getFlagPeriodoEjecucion());
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
	
	@ActionMapping(params = "action=nuevoDetalle")
	public void nuevoDetalleFormato(ModelMap model, ActionRequest request, ActionResponse response, @ModelAttribute("formato12CCBean") Formato12CCBean bean) {
		String codEmpresa = bean.getCodigoEmpresa();
		//String periodoDeclaracion = command.getPeridoDeclaracion();
		String periodoDeclaracion = bean.getPeriodoEnvio();//se obtiene el valor del periodo guardado de el campo descripcionPeriodoHidden(valorPeriodoHidden), probar los demas flujos
		
		String anioPresentacion = "";
		String mesPresentacion = "";
		String etapa = "";
		logger.info("valores detalle " + codEmpresa);
		logger.info("valores detalle" + periodoDeclaracion);

		if (periodoDeclaracion != null && periodoDeclaracion.length() > 6) {
			anioPresentacion = periodoDeclaracion.substring(0, 4);
			mesPresentacion = periodoDeclaracion.substring(4, 6);
			etapa = periodoDeclaracion.substring(6);
		}
		logger.info("Cabecera codEmpresa:" + codEmpresa);
		logger.info("Cabecera anioPresentacion:" + anioPresentacion);
		logger.info("Cabecera mesPresentacion:" + mesPresentacion);
		logger.info("Cabecera etapa:" + etapa);

		// Registramos la cabecera
		FiseFormato12CCPK pk = new FiseFormato12CCPK();
		pk.setCodEmpresa(codEmpresa);
		pk.setAnoPresentacion(Long.parseLong(anioPresentacion));
		pk.setMesPresentacion(Long.parseLong(mesPresentacion));
		pk.setEtapa(etapa);

		FiseFormato12CC formato = new FiseFormato12CC();
		formato.setId(pk);

		// Primero buscamos si existe una cabecera
		formato = formatoService.obtenerFormato12CCByPK(pk);

		if (formato != null) {
			// update

		} else {
			// create
		}

		response.setRenderParameter("crud", CRUD_CREATE);
		response.setRenderParameter("action", "detalle");
		response.setRenderParameter("codigoEmpresaDetalle", codEmpresa);
		response.setRenderParameter("periodoEnvioDetalle", periodoDeclaracion);
	}

	@RequestMapping(params = "action=detalle")
	public String detalle(ModelMap model, RenderRequest renderRequest, RenderResponse renderResponse, @RequestParam("crud") String crud, 
			@RequestParam("codigoEmpresaDetalle") String codEmpresa, @RequestParam("periodoEnvioDetalle") String periodoDeclaracion, 
			@ModelAttribute("formato12CCBean") Formato12CCBean bean) {

		//String codEmpresa = bean.getCodigoEmpresa();
		//String  periodoDeclaracion = bean.getPeriodoEnvio();
		
		
		logger.info("valores requestparameter" + crud);
		logger.info("valores periodoDeclaracion" + periodoDeclaracion);
		logger.info("valores codEmpresa" + codEmpresa);

		String anioPresentacion = "";
		String mesPresentacion = "";
		String etapa = "";

		if (periodoDeclaracion != null && periodoDeclaracion.length() > 6) {
			anioPresentacion = periodoDeclaracion.substring(0, 4);
			mesPresentacion = periodoDeclaracion.substring(4, 6);
			etapa = periodoDeclaracion.substring(6);
		}
		// Cabecera
		
		bean.setCodigoEmpresa(codEmpresa);
		bean.setPeriodoEnvio(periodoDeclaracion);
		bean.setDescEmpresa(mapaEmpresa.get(FormatoUtil.rellenaDerecha(codEmpresa, ' ', 4)));
		bean.setAnioEjecucion(Long.parseLong(anioPresentacion));
		bean.setMesEjecucion(Long.parseLong(mesPresentacion));
		bean.setEtapa(etapa);

		//
		bean.setListaMes(fiseUtil.getMapaMeses());
		bean.setListaZonaBenef(fiseUtil.getMapaZonaBenef());
		bean.setListaDepartamentos(fiseUtil.listaDepartamentos());
		bean.setListaEtapaEjecucion(fiseUtil.getMapaEtapaEjecucion());
		bean.setListaTipoDocumento(fiseUtil.getMapTipoDocumento());
		
		bean.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));
		bean.setListaPeriodoEnvio(listaPeriodoEnvio);
		
		
		model.addAttribute("readonly", "false");

		// cargamos el ano y fin de vigencia
		//----List<FisePeriodoEnvio> listaPeriodoEnvio = periodoService.listarFisePeriodoEnvioMesAnioEtapa(codEmpresa, FiseConstants.TIPO_FORMATO_13A);
		for (FisePeriodoEnvio periodo : listaPeriodoEnvio) {
			if (periodoDeclaracion.equals(periodo.getCodigoItem())) {
				// verificamos el flag de periodo de ejecucion
				if ("S".equals(periodo.getFlagPeriodoEjecucion())) {
					model.addAttribute("readonlyFlagPeriodo", "false");
				} else {
					model.addAttribute("readonlyFlagPeriodo", "true");
				}
				break;
			}
		}

		if (CRUD_READ.equals(crud) || CRUD_READ_CREATEUPDATE.equals(crud) || CRUD_UPDATE.equals(crud)) {

			if (CRUD_READ.equals(crud) || CRUD_READ_CREATEUPDATE.equals(crud)) {
				// Es lectura
				model.addAttribute("readonly", "true");
				model.addAttribute("readonlyFlagPeriodo", "true");
				model.addAttribute("readonlyEdit", "true");
			} else if (CRUD_UPDATE.equals(crud)) {
				model.addAttribute("readonly", "true");
				model.addAttribute("readonlyEdit", "false");
			}

			logger.info("LECTURA DETALLE");
			FiseFormato12CC formato = new FiseFormato12CC();
			
			FiseFormato12CCPK pk = new FiseFormato12CCPK();
			pk.setCodEmpresa(codEmpresa);
			pk.setAnoPresentacion(Long.parseLong(anioPresentacion));
			pk.setMesPresentacion(Long.parseLong(mesPresentacion));
			pk.setEtapa(etapa);
			
			formato = formatoService.obtenerFormato12CCByPK(pk);
			
			if( formato != null ){
				
				List<FiseFormato12CD> listaDetalle = formatoService.listarFormato12CDByFormato12CC(formato);
				
				for (FiseFormato12CD detalle : listaDetalle) {
					//verificamos el codigo  de etapa ejecucion
					if( bean.getEtapaEjecucion() == detalle.getId().getEtapaEjecucion() ){
						bean.setAnioEjecucion(detalle.getId().getAnoEjecucionGasto());
						bean.setMesEjecucion(detalle.getId().getMesEjecucionGasto());
						//item
						bean.setCodUbigeoOrigen(detalle.getCodUbigeoOrigen());
						bean.setLocalidadOrigen(detalle.getDescripcionLocalidadOrigen());
						bean.setCodUbigeoDestino(detalle.getCodUbigeoDestino());
						bean.setLocalidadDestino(detalle.getDescripcionLocalidadDestino());
						bean.setZonaBenef(detalle.getIdZonaBenef());
						bean.setCodCuentaContable(detalle.getCodigoCuentaContaEde());
						bean.setActividad(detalle.getDescripcionActividad());
						bean.setTipoDocumento(detalle.getIdTipDocRef());
						bean.setRucEmpresa(detalle.getRucEmpresaEmiteDocRef());
						bean.setSerieDocumento(detalle.getSerieDocumentoReferencia());
						bean.setNroDocumento(detalle.getNumeroDocumentoReferencia());
						bean.setNroDias(detalle.getNumeroDias());
						bean.setMontoAlimentacion(detalle.getMontoAlimentacion());
						bean.setMontoAlojamiento(detalle.getMontoAlojamiento());
						bean.setMontoMovilidad(detalle.getMontoMovilidad());
						bean.setTotalGeneral(detalle.getTotalGeneral());
						
					}
					
					break;
				}
				
			}
			
		} else if (CRUD_CREATE.equals(crud)) {
			model.addAttribute("readonlyEdit", "false");

			/*bean.setSt1(FiseConstants.CERO);
			bean.setSt2(FiseConstants.CERO);
			bean.setSt3(FiseConstants.CERO);
			bean.setSt4(FiseConstants.CERO);
			bean.setSt5(FiseConstants.CERO);
			bean.setSt6(FiseConstants.CERO);
			bean.setStser(FiseConstants.CERO);
			bean.setStesp(FiseConstants.CERO);
			bean.setTotal(FiseConstants.CERO);*/
		}

		model.addAttribute("crud", crud);

		return "formato12CCRUDDetalle";
	}
	
	@ResourceMapping("provincias")
	public void cargarProvincias(ModelMap model, SessionStatus status, ResourceRequest request, ResourceResponse response, @RequestParam("codDepartamento") String codDepartamento) {
		try {
			logger.info("cargarProvincias");
			logger.debug("-->cargarProvincias");
			response.setContentType("applicacion/json");
			if (codDepartamento != null && !codDepartamento.equals("")) {
				codDepartamento = codDepartamento.substring(0, 2);
			}
			List<AdmUbigeo> provincias = fiseUtil.listaProvincias(codDepartamento);

			JSONArray jsonArray = new JSONArray();

			// Temp
			JSONObject seleccioneItem = new JSONObject();
			seleccioneItem.put("codigoItem", "");
			seleccioneItem.put("descripcionItem", "--Seleccione--");
			jsonArray.put(seleccioneItem);

			for (AdmUbigeo provincia : provincias) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("codigoItem", provincia.getCodUbigeo());
				jsonObj.put("descripcionItem", provincia.getNomUbigeo());
				jsonArray.put(jsonObj);
			}

			PrintWriter pw = response.getWriter();
			pw.write(jsonArray.toString());
			pw.flush();
			pw.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@ResourceMapping("distritos")
	public void cargaDistritos(ModelMap model, SessionStatus status, ResourceRequest request, ResourceResponse response, @RequestParam("codProvincia") String codProvincia) {
		try {
			logger.info("cargaDistritos");
			logger.debug("-->cargaDistritos");
			response.setContentType("applicacion/json");
			if (codProvincia != null && !codProvincia.equals("")) {
				codProvincia = codProvincia.substring(0, 4);
			}

			List<AdmUbigeo> distritos = fiseUtil.listaDistritos(codProvincia);
			JSONArray jsonArray = new JSONArray();

			// Temp
			JSONObject seleccioneItem = new JSONObject();
			seleccioneItem.put("codigoItem", "");
			seleccioneItem.put("descripcionItem", "--Seleccione--");
			jsonArray.put(seleccioneItem);

			for (AdmUbigeo distrito : distritos) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("codigoItem", distrito.getCodUbigeo());
				jsonObj.put("descripcionItem", distrito.getNomUbigeo());
				// agregar los valores
				jsonArray.put(jsonObj);
			}

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
