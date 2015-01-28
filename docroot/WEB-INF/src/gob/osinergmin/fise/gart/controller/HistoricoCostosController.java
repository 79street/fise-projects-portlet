package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.HistoricoCostosBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.gart.service.CommonGartService;
import gob.osinergmin.fise.gart.service.FiseGrupoInformacionGartService;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

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
			response.setContentType("application/json");
			
			JSONObject jsonObj = new JSONObject();
			
			String codEmpresa = bean.getCodEmpresaBusq();				
			String formato = bean.getFormatoBusq();
			
  			List<HistoricoCostosBean> listaCostos =commonService.obtenerHistoricoCostosByCodempresaFormato(codEmpresa, formato);
  			logger.info("tama�o de la lista notificacion   :"+listaCostos.size());
  			String listaValores = convertirListaValores(listaCostos);
  			model.addAttribute("cadenaValorVariacion", listaValores);
  			
  			
  			jsonObj.put("resultado", "OK");
  			
  			jsonObj.put("cadena", listaValores);
  			jsonObj.put("titulo",mapaEmpresa.get(codEmpresa));
  			
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
		//String apostrofe="'";
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
					cadena=cadena+corcheteInicio+hist.getPeriodo()+coma+cociente+corcheteFin;
				}else{
					cadena=cadena+coma+corcheteInicio+hist.getPeriodo()+coma+cociente+corcheteFin;
				}
			}
		}
		cadena = cadena + fin;
		return cadena;
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
