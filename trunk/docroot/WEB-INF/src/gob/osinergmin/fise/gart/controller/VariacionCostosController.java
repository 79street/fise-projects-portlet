package gob.osinergmin.fise.gart.controller;

import gob.osinergmin.fise.bean.VariacionCostosBean;
import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.gart.service.CommonGartService;
import gob.osinergmin.fise.gart.service.FiseGrupoInformacionGartService;
import gob.osinergmin.fise.util.FormatoUtil;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

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

import com.liferay.portal.kernel.util.JavaConstants;

@Controller("variacionCostosController")
@RequestMapping("VIEW")
public class VariacionCostosController {
	
	Logger logger = Logger.getLogger(VariacionCostosController.class);
	
	@Autowired
	@Qualifier("fiseUtil")
	private FiseUtil fiseUtil;	
	
	@Autowired
	@Qualifier("fiseGrupoInformacionGartServiceImpl")
	private FiseGrupoInformacionGartService fiseGrupoInformacionService;
	
	@Autowired
	@Qualifier("commonGartServiceImpl")
	private CommonGartService commonService;
	
	@RequestMapping
	public String defaultView(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse, @ModelAttribute("variacionCostosBean")VariacionCostosBean bean){
        try {           	
    			
    		bean.setAdmin(fiseUtil.esAdministrador(renderRequest));
    		bean.setListaGrupoInfo(fiseGrupoInformacionService.listarGrupoInformacion(FiseConstants.BIENAL));
    		//vamos a cargar por defecto los conceptos para rural
    		
    		model.addAttribute("model", bean);
    		
		} catch (Exception e) {
			logger.info("Ocurrio un errror al caragar la pagina notificacion-validacion"); 
			e.printStackTrace();
		}		
		return "variacionCostos";
	}
	
	@ResourceMapping("cargarConceptos")
  	public void cargaConceptos(ModelMap model, ResourceRequest request,ResourceResponse response, @ModelAttribute("variacionCostosBean")VariacionCostosBean bean){
		try {			
  			response.setContentType("applicacion/json");
  			//String tipoFormato = n.getOptionFormato();
  			
  			String formato = bean.getFormatoBusq();
  			
  			//logger.info("Codigo grupo inf. para cargar grupo de infor.:  "+tipoFormato);
  			JSONArray jsonArray = new JSONArray();
  			
  			if(FiseConstants.TIPO_FORMATO_14A.equals(formato)){
  				
  				JSONObject jsonObj1 = new JSONObject();
  				jsonObj1.put("codigoItem", FiseConstants.CONCEPTO_EMP_VALUE);				
  				jsonObj1.put("descripcionItem", FiseConstants.CONCEPTO_EMP_DESCRIPCION);				
  				jsonArray.put(jsonObj1);
  				
  				JSONObject jsonObj2 = new JSONObject();
  				jsonObj2.put("codigoItem", FiseConstants.CONCEPTO_GLP_VALUE);				
  				jsonObj2.put("descripcionItem", FiseConstants.CONCEPTO_GLP_DESCRIPCION);				
  				jsonArray.put(jsonObj2);
  				
  			}else if(FiseConstants.TIPO_FORMATO_14B.equals(formato)){
  				
  				JSONObject jsonObj1 = new JSONObject();
  				jsonObj1.put("codigoItem", FiseConstants.CONCEPTO_COSTO_UNIT_IMP_VAL_VALUE);				
  				jsonObj1.put("descripcionItem", FiseConstants.CONCEPTO_COSTO_UNIT_IMP_VAL_DESCRIPCION);				
  				jsonArray.put(jsonObj1);
  				
  				JSONObject jsonObj2 = new JSONObject();
  				jsonObj2.put("codigoItem", FiseConstants.CONCEPTO_COSTO_UNIT_VAL_DOMIC_VALUE);				
  				jsonObj2.put("descripcionItem", FiseConstants.CONCEPTO_COSTO_UNIT_VAL_DOMIC_DESCRIPCION);				
  				jsonArray.put(jsonObj2);
  				
  				JSONObject jsonObj3 = new JSONObject();
  				jsonObj3.put("codigoItem", FiseConstants.CONCEPTO_COSTO_UNIT_VAL_DIS_ELE_VALUE);				
  				jsonObj3.put("descripcionItem", FiseConstants.CONCEPTO_COSTO_UNIT_VAL_DIS_ELE_DESCRIPCION);				
  				jsonArray.put(jsonObj3);
  				
  				JSONObject jsonObj4 = new JSONObject();
  				jsonObj4.put("codigoItem", FiseConstants.CONCEPTO_COSTO_UNIT_CANJ_VAL_FIS_VALUE);				
  				jsonObj4.put("descripcionItem", FiseConstants.CONCEPTO_COSTO_UNIT_CANJ_VAL_FIS_DESCRIPCION);				
  				jsonArray.put(jsonObj4);
  				
  				JSONObject jsonObj5 = new JSONObject();
  				jsonObj5.put("codigoItem", FiseConstants.CONCEPTO_COSTO_UNIT_CANJ_VAL_DIG_VALUE);				
  				jsonObj5.put("descripcionItem", FiseConstants.CONCEPTO_COSTO_UNIT_CANJ_VAL_DIG_DESCRIPCION);				
  				jsonArray.put(jsonObj5);
  				
  				JSONObject jsonObj6 = new JSONObject();
  				jsonObj6.put("codigoItem", FiseConstants.CONCEPTO_COSTO_UNIT_ATENCION_VALUE);				
  				jsonObj6.put("descripcionItem", FiseConstants.CONCEPTO_COSTO_UNIT_ATENCION_DESCRIPCION);				
  				jsonArray.put(jsonObj6);
  				
  				JSONObject jsonObj7 = new JSONObject();
  				jsonObj7.put("codigoItem", FiseConstants.CONCEPTO_COSTO_TOTAL_GEST_ADM_VALUE);				
  				jsonObj7.put("descripcionItem", FiseConstants.CONCEPTO_COSTO_TOTAL_GEST_ADM_DESCRIPCION);				
  				jsonArray.put(jsonObj7);
  				
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
	
	@RequestMapping(params="action=plot")
	public String cargarImagen(ModelMap model,RenderRequest renderRequest, RenderResponse renderResponse, @ModelAttribute("variacionCostosBean")VariacionCostosBean bean){
        try {           	
    			
        	long idGrupo=0;
        	
        	/*String idGrupoInfo = bean.getGrupoInfoBusq();				
			String formato = bean.getFormatoBusq();
			String zona = bean.getZonaBusq();
			String concepto = bean.getConceptoBusq();*/
        	
        	PortletRequest pRequest = (PortletRequest) renderRequest.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
        	
			String idGrupoInfo = bean.getGrupoInfoBusq();				
			String formato = bean.getFormatoBusq();
			String zona = bean.getZonaBusq();
			String concepto = bean.getConceptoBusq();
			
			if(FormatoUtil.isNotBlank(bean.getGrupoInfoBusq())){ 
		    	idGrupo = new Long(idGrupoInfo);
		    }
			
			String valorConceptoConcatenado = "";
			
			if( "R".equals(zona) ){
				if( FiseConstants.TIPO_FORMATO_14A.equals(formato) ){
					valorConceptoConcatenado = concepto+FiseConstants.SUFIJO_CONCEPTO_RURAL_F14A;
				}else if( FiseConstants.TIPO_FORMATO_14B.equals(formato) ){
					valorConceptoConcatenado = concepto+FiseConstants.SUFIJO_CONCEPTO_RURAL_F14B;
				}
			}else if( "P".equals(zona) ){
				if( FiseConstants.TIPO_FORMATO_14A.equals(formato) ){
					valorConceptoConcatenado = concepto+FiseConstants.SUFIJO_CONCEPTO_PROVINCIA_F14A;
				}else if( FiseConstants.TIPO_FORMATO_14B.equals(formato) ){
					valorConceptoConcatenado = concepto+FiseConstants.SUFIJO_CONCEPTO_PROVINCIA_F14B;
				}
			}else if( "L".equals(zona) ){
				if( FiseConstants.TIPO_FORMATO_14A.equals(formato) ){
					valorConceptoConcatenado = concepto+FiseConstants.SUFIJO_CONCEPTO_LIMA_F14A;
				}else if( FiseConstants.TIPO_FORMATO_14B.equals(formato) ){
					valorConceptoConcatenado = concepto+FiseConstants.SUFIJO_CONCEPTO_LIMA_F14B;
				}
			}
  			
  			List<VariacionCostosBean> listaCostos =commonService.obtenerVariacionCostosByGrupoinfoFormatoConceptofinal(idGrupo, formato, valorConceptoConcatenado);
  			
  			logger.info("tamaño de la lista notificacion   :"+listaCostos.size());
  			
  			//List<VariacionCostosBean> listaVariacionCostos = new ArrayList<VariacionCostosBean>();
  			
  			String listaValores = convertirListaValores(listaCostos);
  			
  			//VariacionJSON obj = new VariacionJSON();
  			pRequest.getPortletSession().setAttribute("cadenaValorVariacion", listaValores, PortletSession.APPLICATION_SCOPE);
  			
  			//obj.setCadenaVariacion(listaValores);
  			
  			model.addAttribute("cadenaValorVariacion", listaValores);
        	
        	
    		/*bean.setAdmin(fiseUtil.esAdministrador(renderRequest));
    		bean.setListaGrupoInfo(fiseGrupoInformacionService.listarGrupoInformacion(FiseConstants.BIENAL));
    		//vamos a cargar por defecto los conceptos para rural
    		
    		model.addAttribute("model", bean);*/
    		
		} catch (Exception e) {
			logger.info("Ocurrio un errror al caragar la pagina notificacion-validacion"); 
			e.printStackTrace();
		}		
		return "variacionCostos";
	}
	
	@ResourceMapping("generarGrafico")
  	public void generarGrafico(ModelMap model, ResourceRequest request,ResourceResponse response, @ModelAttribute("variacionCostosBean")VariacionCostosBean bean){
		
		try{
			response.setContentType("application/json");
			
			JSONObject jsonObj = new JSONObject();
			
			long idGrupo=0;
			String idGrupoInfo = bean.getGrupoInfoBusq();				
			String formato = bean.getFormatoBusq();
			String zona = bean.getZonaBusq();
			String concepto = bean.getConceptoBusq();
			
			if(FormatoUtil.isNotBlank(bean.getGrupoInfoBusq())){ 
		    	idGrupo = new Long(idGrupoInfo);
		    }
			
			String valorConceptoConcatenado = "";
			
			if( "R".equals(zona) ){
				if( FiseConstants.TIPO_FORMATO_14A.equals(formato) ){
					valorConceptoConcatenado = concepto+FiseConstants.SUFIJO_CONCEPTO_RURAL_F14A;
				}else if( FiseConstants.TIPO_FORMATO_14B.equals(formato) ){
					valorConceptoConcatenado = concepto+FiseConstants.SUFIJO_CONCEPTO_RURAL_F14B;
				}
			}else if( "P".equals(zona) ){
				if( FiseConstants.TIPO_FORMATO_14A.equals(formato) ){
					valorConceptoConcatenado = concepto+FiseConstants.SUFIJO_CONCEPTO_PROVINCIA_F14A;
				}else if( FiseConstants.TIPO_FORMATO_14B.equals(formato) ){
					valorConceptoConcatenado = concepto+FiseConstants.SUFIJO_CONCEPTO_PROVINCIA_F14B;
				}
			}else if( "L".equals(zona) ){
				if( FiseConstants.TIPO_FORMATO_14A.equals(formato) ){
					valorConceptoConcatenado = concepto+FiseConstants.SUFIJO_CONCEPTO_LIMA_F14A;
				}else if( FiseConstants.TIPO_FORMATO_14B.equals(formato) ){
					valorConceptoConcatenado = concepto+FiseConstants.SUFIJO_CONCEPTO_LIMA_F14B;
				}
			}
  			
  			List<VariacionCostosBean> listaCostos =commonService.obtenerVariacionCostosByGrupoinfoFormatoConceptofinal(idGrupo, formato, valorConceptoConcatenado);
  			
  			logger.info("tamaño de la lista notificacion   :"+listaCostos.size());
  			
  			//List<VariacionCostosBean> listaVariacionCostos = new ArrayList<VariacionCostosBean>();
  			
  			String listaValores = convertirListaValores(listaCostos);
  			
  			
  			//obj.setCadenaVariacion(listaValores);
  			
  			model.addAttribute("cadenaValorVariacion", listaValores);
  			
  			jsonObj.put("resultado", "OK");
  			
  			//listaValores = "[[[1,2],[3,4],[5,6]]]";
  			
  			jsonObj.put("cadena", listaValores);
  			jsonObj.put("promedio",promedio(listaCostos));
  			
  			PrintWriter pw = response.getWriter();
		    pw.write(jsonObj.toString());
		    pw.flush();
		    pw.close();
  			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}	
	
	public String convertirListaValores(List<VariacionCostosBean> lista){
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
			for (VariacionCostosBean var : lista) {
				if(primero){
					primero=false;
					cadena=cadena+corcheteInicio+apostrofe+var.getCodEmpresa()+apostrofe+coma+var.getValor()+corcheteFin;
				}else{
					cadena=cadena+coma+corcheteInicio+apostrofe+var.getCodEmpresa()+apostrofe+coma+var.getValor()+corcheteFin;
				}
			}
		}
		cadena = cadena + fin;
		return cadena;
	}
	
	public String promedio(List<VariacionCostosBean> lista){
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
	}
	
}
