package gob.osinergmin.fise.common.util;

import gob.osinergmin.fise.domain.AdmEmpresa;
import gob.osinergmin.fise.gart.service.AdmEmpresaGartService;
import gob.osinergmin.fise.util.FechaUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Organization;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.expando.model.ExpandoTableConstants;
import com.liferay.portlet.expando.model.ExpandoValueModel;
import com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil;

public class FiseUtil {

	private static final String cod_proceso = "FISE";    		
	private static final String cod_funcion = "REMISION";
	private static final Log logger=LogFactoryUtil.getLog(FiseUtil.class);
	
	@Autowired
	@Qualifier("admEmpresaGartServiceImpl")
	AdmEmpresaGartService admEmpresaService;
	
	
	public List<AdmEmpresa> getEmpresaxUsuario(PortletRequest request){
		 List<AdmEmpresa> listaEmpresas=new ArrayList<AdmEmpresa>();
		try {
			ThemeDisplay theme = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			String cadenaEmpresas ="";
			boolean bAdministrador =esAdministrador(request);
			
			List<Organization> lstOrgUser = OrganizationLocalServiceUtil.getUserOrganizations(theme.getUserId());
			for (Organization organization : lstOrgUser) {
				String codigo=null;
				ExpandoValueModel dato = ExpandoValueLocalServiceUtil.getValue(organization.getCompanyId(), Organization.class.getName(), ExpandoTableConstants.DEFAULT_TABLE_NAME, "cod_empresa", organization.getOrganizationId());
				if (dato != null){
					codigo=dato.getData();
				}	
				if(codigo == null)
					continue;
				if(codigo.trim().equals(""))
					continue;
				if(cadenaEmpresas.trim().equals(""))									
					cadenaEmpresas = "'"+codigo.trim()+"'";
				else
					cadenaEmpresas = cadenaEmpresas+",'"+codigo.trim()+"'";
			}
			
			if(cadenaEmpresas.trim().equals(""))
				cadenaEmpresas="'XXX'";
			
			logger.info("es administrador"+bAdministrador);
			if(bAdministrador){
				listaEmpresas = admEmpresaService.getEmpresaFise(cod_proceso,cod_funcion,"");
			}else{
				listaEmpresas = admEmpresaService.getEmpresaFise(cod_proceso,cod_funcion,cadenaEmpresas);
			}
				
			
		} catch (Exception e) {
			logger.error("Error al obtener empresas por Rol:",e);
		}
		
		return listaEmpresas;
	}
	
	public Map<Long,String> getMapaMeses(){
		return FechaUtil.cargarMapaMeses();
	}
	
	public String obtenerNroAnioFechaActual(){
		return FechaUtil.obtenerNroAnioFechaActual();
	}
	public String obtenerNroMesFechaActual(){
		return FechaUtil.obtenerNroMesFechaActual();
	}
	
	public boolean esAdministrador(PortletRequest request){
		try {
			ThemeDisplay theme = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			PermissionChecker permissionChecker = PermissionCheckerFactoryUtil.create(theme.getUser(), false);
			long groupId = theme.getScopeGroupId();
			String name = theme.getPortletDisplay().getRootPortletId();
			String primKey = theme.getPortletDisplay().getResourcePK();
			String actionAdministrador = "ADMINISTRADOR";
			return permissionChecker.hasPermission(groupId, name, primKey, actionAdministrador);
		} catch (Exception e) {
			logger.error("Error al verificar usuario administrador:",e);
			return false;
		}
	}
}
