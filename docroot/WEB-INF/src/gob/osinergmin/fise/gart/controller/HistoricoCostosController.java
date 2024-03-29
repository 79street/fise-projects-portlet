package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.HistoricoCostosBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.gart.service.CommonGartService;
import gob.osinergmin.fise.gart.service.FiseGrupoInformacionGartService;
import gob.osinergmin.fise.util.FechaUtil;
import gob.osinergmin.fise.util.FormatoUtil;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.liferay.portal.util.PortalUtil;

@Controller("historicoCostosController")
@RequestMapping("VIEW")
public class HistoricoCostosController {

Logger logger = Logger.getLogger(HistoricoCostosController.class);
	
	@Autowired
	@Qualifier("fiseUtil")
	private FiseUtil fiseUtil;	
	
	@Autowired
	@Qualifier("fiseGrupoInformacionGartServiceImpl")
	private FiseGrupoInformacionGartService fiseGrupoInformacionService;
	
	@Autowired
	@Qualifier("commonGartServiceImpl")
	private CommonGartService commonService;
	
	private Map<String, String> mapaEmpresa;	
	
	@RequestMapping
	public String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse, @ModelAttribute("historicoCostosBean")HistoricoCostosBean bean){
        try {           	
    			
    		bean.setAdmin(fiseUtil.esAdministrador(renderRequest));
    		bean.setListaEmpresas(fiseUtil.getEmpresaxUsuario(renderRequest));
    		mapaEmpresa = fiseUtil.getMapaEmpresa();
    		
    		model.addAttribute("model", bean);
    		
		} catch (Exception e) {
			logger.info("Ocurrio un errror al caragar la pagina notificacion-validacion"); 
			e.printStackTrace();
		}		
		return "historicoCostos";
	}
	
	
	@ResourceMapping("generarGrafico")
  	public void generarGrafico(ModelMap model, ResourceRequest request,ResourceResponse response, @ModelAttribute("historicoCostosBean")HistoricoCostosBean bean){
		
		try{
			HttpServletRequest req = PortalUtil.getHttpServletRequest(request);	        
	        HttpSession session = req.getSession();
			
			response.setContentType("application/json");
			
			JSONObject jsonObj = new JSONObject();
			
			String codEmpresa = bean.getCodEmpresaBusq();				
			String formato = bean.getFormatoBusq();
			
  			List<HistoricoCostosBean> listaCostos =commonService.obtenerHistoricoCostosByCodempresaFormato(codEmpresa, formato);
  			logger.info("tamaño de la lista notificacion   :"+listaCostos.size());
  			
  			String msgTitulo1 = tituloPlot1(formato);
  			String msgTitulo2 = tituloPlot2(codEmpresa);
  			String msgTitulo3 = tituloPlot3(formato);
  			
  			if( listaCostos!=null && listaCostos.size()>0 ){
  				
  				for (HistoricoCostosBean var : listaCostos) {
					var.setTitulo1(msgTitulo1);
					var.setTitulo2(msgTitulo2);
					var.setTitulo3(msgTitulo3);
					var.setFormato(formato);
				}
  				
  				fiseUtil.configuracionExportarExcel(session, FiseConstants.TIPO_FORMATO_HISTORICO, FiseConstants.NOMBRE_EXCEL_HISTORICO, FiseConstants.NOMBRE_HOJA_HISTORICO, listaCostos);
  			}
  			
  			String listaValores = convertirListaValores(listaCostos);
  			model.addAttribute("cadenaValorVariacion", listaValores);
  			
  			jsonObj.put("resultado", "OK");
  			jsonObj.put("cadena", listaValores);
  			
  			String mensajeTitulo = "";
  			mensajeTitulo = tituloPlot(codEmpresa, formato);
  			String mensajeTituloY = "";
  			mensajeTituloY = tituloEjeYPlot(formato);
  			
  			jsonObj.put("titulo",mensajeTitulo);
  			jsonObj.put("tituloEjeY",mensajeTituloY);
  			
  			//anexamos el titulo en diferentes lineas
  			jsonObj.put("titulo1",msgTitulo1);
  			jsonObj.put("titulo2",msgTitulo2);
  			jsonObj.put("titulo3",msgTitulo3);
  			
  			PrintWriter pw = response.getWriter();
		    pw.write(jsonObj.toString());
		    pw.flush();
		    pw.close();
  			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}	
	
	/*private String toStringListJSON(List<VariacionCostosBean> lista) {
		Serializer serializer = new JsonSerializer();
		Object result = serializer.serialize(lista);
		String data = String.valueOf(result);
		return data;
	}*/
	
	public String convertirListaValores(List<HistoricoCostosBean> lista){
		String cadena="";
		String inicio="[[";
		String fin="]]";
		String coma=",";
		String apostrofe="'";
		String corcheteInicio="[";
		String corcheteFin="]";
		cadena = cadena + inicio;
		boolean primero = true;
		if(lista!=null && lista.size()>0){
			for (HistoricoCostosBean hist : lista) {
				String cociente = "";
				if( BigDecimal.ZERO.equals(new BigDecimal(hist.getNroBeneficiarios())) ){
					cociente = hist.getValor();
				}else{
					cociente = new BigDecimal(hist.getValor()).divide(new BigDecimal(hist.getNroBeneficiarios()), 2, RoundingMode.HALF_UP).toString();
				}
				
				if(primero){
					primero=false;
					//cadena=cadena+corcheteInicio+apostrofe+hist.getPeriodo()+apostrofe+coma+cociente+corcheteFin;
					cadena=cadena+corcheteInicio+apostrofe+descripcionPeriodo(hist.getPeriodo())+apostrofe+coma+cociente+corcheteFin;
				}else{
					//cadena=cadena+coma+corcheteInicio+apostrofe+hist.getPeriodo()+apostrofe+coma+cociente+corcheteFin;
					cadena=cadena+coma+corcheteInicio+apostrofe+descripcionPeriodo(hist.getPeriodo())+apostrofe+coma+cociente+corcheteFin;
				}
			}
		}
		cadena = cadena + fin;
		return cadena;
	}
	
	public String tituloPlot(String codEmpresa, String formato){
		String titulo = "";
		
		String tituloF12A = "FORMATO FISE-12A: Remisión de Gastos Operativos - Implementación";
		String tituloF12B = "FORMATO FISE-12B: Remisión de Gastos Operativos - Mensual";
		
		if(FiseConstants.TIPO_FORMATO_12A.equals(formato)){
			titulo = titulo + tituloF12A;
		}else if(FiseConstants.TIPO_FORMATO_12B.equals(formato)){
			titulo = titulo + tituloF12B;
		}
		
		String titulo1="<br/> Histórico de Costos: ";
		titulo = titulo + titulo1;
		
		if( "NAC".equals(codEmpresa) ){
			titulo = titulo + "NACIONAL";
		}else{
			titulo = titulo + mapaEmpresa.get(FormatoUtil.rellenaDerecha(codEmpresa, ' ', 4));
		}
		if(FiseConstants.TIPO_FORMATO_12A.equals(formato)){
			titulo = titulo + "<br/> Costo Unitario de Implementación";
		}else if(FiseConstants.TIPO_FORMATO_12B.equals(formato)){
			titulo = titulo + "<br/> Costo Promedio de Operación";
		}
		return titulo;
	}
	
	public String tituloPlot1(String formato){
		String titulo = "";
		
		String tituloF12A = "FORMATO FISE-12A: Remisión de Gastos Operativos - Implementación";
		String tituloF12B = "FORMATO FISE-12B: Remisión de Gastos Operativos - Mensual";
		
		if(FiseConstants.TIPO_FORMATO_12A.equals(formato)){
			titulo = titulo + tituloF12A;
		}else if(FiseConstants.TIPO_FORMATO_12B.equals(formato)){
			titulo = titulo + tituloF12B;
		}
		
		return titulo;
	}
	
	public String tituloPlot2(String codEmpresa){
		String titulo = "";
		
		String titulo1="Histórico de Costos: ";
		titulo = titulo + titulo1;
		
		if( "NAC".equals(codEmpresa) ){
			titulo = titulo + "NACIONAL";
		}else{
			titulo = titulo + mapaEmpresa.get(FormatoUtil.rellenaDerecha(codEmpresa, ' ', 4));
		}

		return titulo;
	}
	
	public String tituloPlot3(String formato){
		String titulo = "";
		
		if(FiseConstants.TIPO_FORMATO_12A.equals(formato)){
			titulo = titulo + "Costo Unitario de Implementación";
		}else if(FiseConstants.TIPO_FORMATO_12B.equals(formato)){
			titulo = titulo + "Costo Promedio de Operación";
		}
		return titulo;
	}
	
	public String tituloEjeYPlot(String formato){
		String titulo = "";
		if(FiseConstants.TIPO_FORMATO_12A.equals(formato)){
			titulo = titulo + "Costo Unitario";
		}else if(FiseConstants.TIPO_FORMATO_12B.equals(formato)){
			titulo = titulo + "Costo Promedio";
		}
		return titulo;
	}
	
	public String descripcionPeriodo(String valorPeriodo){
		String periodo = "";
		if(valorPeriodo.length()==7){
			periodo = periodo + FechaUtil.mesLetras(valorPeriodo.substring(5,7))+"-"+valorPeriodo.substring(2,4);
		}
		return periodo;
	}
	
	/*public String promedio(List<VariacionCostosBean> lista){
		String promedio = "";
		BigDecimal suma = new BigDecimal(0);
		
		if(lista!=null && lista.size()>0){
			for (VariacionCostosBean var : lista) {
				suma = suma.add(new BigDecimal(var.getValor()));
			}
			promedio = suma.divide(new BigDecimal(lista.size()),2,RoundingMode.HALF_UP).toString();
		}
		if( "".equals(promedio) ){
			promedio = "0";
		}
		return promedio;
	}*/
	
}
