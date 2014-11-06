package gob.osinergmin.fise.common.util;

import gob.osinergmin.fise.bean.CorreoBean;
import gob.osinergmin.fise.bean.MensajeErrorBean;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.AdmEmpresa;
import gob.osinergmin.fise.domain.FiseObservacion;
import gob.osinergmin.fise.domain.FiseZonaBenef;
import gob.osinergmin.fise.gart.jsp.FileEntryJSP;
import gob.osinergmin.fise.gart.service.AdmEmpresaGartService;
import gob.osinergmin.fise.gart.service.CommonGartService;
import gob.osinergmin.fise.gart.service.FiseObservacionGartService;
import gob.osinergmin.fise.gart.service.FiseZonaBenefGartService;
import gob.osinergmin.fise.gart.service.Formato12AGartService;
import gob.osinergmin.fise.util.FechaUtil;
import gob.osinergmin.fise.xls.XlsTableConfig;
import gob.osinergmin.fise.xls.XlsWorkbookConfig;
import gob.osinergmin.fise.xls.XlsWorksheetConfig;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.portlet.PortletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Organization;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.bookmarks.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.FileMimeTypeException;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
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
	@Autowired
	@Qualifier("formato12AGartServiceImpl")
	Formato12AGartService formatoService;
	@Autowired
	@Qualifier("fiseObservacionGartServiceImpl")
	FiseObservacionGartService observacionService;
	@Autowired
	@Qualifier("fiseZonaBenefGartServiceImpl")
	FiseZonaBenefGartService zonaBenefService;
	@Autowired
	@Qualifier("commonGartServiceImpl")
	CommonGartService commonService;
	
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
	
	public Map<String, String> getMapaEmpresa(){
		Map<String, String> mapaEmpresa = new HashMap<String, String>();
		for (AdmEmpresa admEmpresa : admEmpresaService.listarAdmEmpresa()) {
			mapaEmpresa.put(admEmpresa.getCodEmpresa(), admEmpresa.getDscCortaEmpresa());
		}
		return mapaEmpresa;
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
	
	public void configuracionExportarExcel(HttpSession session, String tipoFormato, String nombreExcel, String nombreHoja, List<?> lista){
		XlsWorkbookConfig xlsWorkbookConfig = new XlsWorkbookConfig();
		xlsWorkbookConfig.setName(nombreExcel);
		List<XlsTableConfig> tables = new LinkedList<XlsTableConfig>();
		tables.add(new XlsTableConfig(lista, tipoFormato));
		List<XlsWorksheetConfig> sheets = new LinkedList<XlsWorksheetConfig>();
		sheets.add(new XlsWorksheetConfig(nombreHoja, tables));
		xlsWorkbookConfig.setSheets(sheets);
		session.setAttribute(FiseConstants.KEY_CFG_EXCEL_EXPORT,xlsWorkbookConfig);	
	}
	
	public FileEntry subirDocumento(PortletRequest request, UploadPortletRequest uploadPortletRequest, String tipoArchivo) {
		// TODO Auto-generated method stub
		FileEntry fileEntry=null;
		//--UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(request);
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		try {
			String[] mimeTypesXls = new String[]{"application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"};
			String[] mimeTypesTxt = new String[]{"text/plain"};
			String[] mimeTypes = new String[]{};
			long maxUploadFileSize =2097152;//bytes = 2MB
			DLFolder dlFolder = DLFolderLocalServiceUtil.getFolder(themeDisplay.getScopeGroupId(), 0, "FormatosDeclarados");
			
			if (dlFolder.getGroupId() != themeDisplay.getScopeGroupId()) {
				throw new NoSuchFolderException();
			}
			 
			String nameFileInput = null;
			if( FiseConstants.TIPOARCHIVO_XLS.equals(tipoArchivo) ){
				nameFileInput = "archivoExcel";
				mimeTypes = mimeTypesXls;
			}else if( FiseConstants.TIPOARCHIVO_TXT.equals(tipoArchivo) ){
				nameFileInput = "archivoTxt";
				mimeTypes = mimeTypesTxt;
			}else{
				throw new Exception("Archivo de formato diferente");
			}
			
			File file = uploadPortletRequest.getFile(nameFileInput);
			String mimeType = uploadPortletRequest.getContentType(nameFileInput);
			long size = uploadPortletRequest.getSize(nameFileInput);
			String sourceFileName = uploadPortletRequest.getFileName(nameFileInput);

			logger.info("MIME ARCHIVO:"+mimeType);
			if (Arrays.binarySearch(mimeTypes, mimeType) < 0) {
				throw new FileMimeTypeException(mimeType);
			}
			//solo para txt/verificar luego
			//if( FiseConstants.TIPOARCHIVO_XLS.equals(tipoArchivo) ){
				//String contenType=MimeTypesUtil.getContentType(file,nameFileInput);´
				//String contenType=MimeTypesUtil.getContentType(file);
				//logger.info("MIME CONTENT TYPE:"+contenType);
				//if (Arrays.binarySearch(mimeTypes, contenType) < 0) {
				//	throw new FileMimeTypeException(contenType);
				//}
			//}
						 
			logger.info("Size:"+size+" bytes");
			logger.info("Max Size:"+maxUploadFileSize+" bytes");
			
			if(size>maxUploadFileSize){
			 throw new FileSizeException(String.valueOf(maxUploadFileSize));
			}
			 
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
			String hoy=sdf.format(new Date());
			long userId=themeDisplay.getUserId();
			
			long repositoryId=dlFolder.getRepositoryId();
			long folderId=dlFolder.getFolderId();
			//String ext =FileUtil.getExtension(sourceFileName);
			//--String title = hoy+"-"+sourceFileName;
			int secuencia = commonService.obtenerSecuencia();
			String title = secuencia+FiseConstants.UNDERLINE+sourceFileName;
			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(), request);
			try {
				fileEntry = DLAppLocalServiceUtil.getFileEntry(dlFolder.getGroupId(), folderId, sourceFileName);
			} catch (NoSuchFileEntryException e) {
				logger.info("el archivo no existe en el folder del repositorio");
				fileEntry=DLAppLocalServiceUtil.addFileEntry(userId, repositoryId, folderId, sourceFileName, mimeType,title, "", "Subido el "+hoy, file, serviceContext);
			}
			DLAppLocalServiceUtil.updateFileEntry(fileEntry.getUserId(), fileEntry.getFileEntryId(),sourceFileName, mimeType, title, fileEntry.getDescription(), "Actualizo estado", true, file, serviceContext);
			logger.info("Archivo subido:"+sourceFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return fileEntry;
		
	}
	
	public FileEntry subirDocumentoBytes(PortletRequest request, byte[] bytes, String mimeType, String sourceFileName) {
		FileEntry fileEntry=null;
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		try {
			DLFolder dlFolder = DLFolderLocalServiceUtil.getFolder(themeDisplay.getScopeGroupId(), 0, "FormatosDeclarados");
					 
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
			String hoy=sdf.format(new Date());
			long userId=themeDisplay.getUserId();
			
			int secuencia = formatoService.obtenerSecuencia();
			String title = secuencia+FiseConstants.UNDERLINE+sourceFileName;
			ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(), request);
			try {
				fileEntry = DLAppLocalServiceUtil.getFileEntry(dlFolder.getGroupId(), dlFolder.getFolderId(), sourceFileName);
			} catch (NoSuchFileEntryException e) {
				fileEntry=DLAppLocalServiceUtil.addFileEntry(userId, dlFolder.getRepositoryId(), dlFolder.getFolderId(), sourceFileName, mimeType,title, "", "Subido el "+hoy, bytes, serviceContext);
			}
			DLAppLocalServiceUtil.updateFileEntry(fileEntry.getUserId(), fileEntry.getFileEntryId(),sourceFileName, mimeType, title, fileEntry.getDescription(), "Actualizo estado", true, bytes, serviceContext);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return fileEntry;
		
	}
	
	public Map<String, String> getMapaErrores(){
		List<FiseObservacion> listaFiseObservacion = observacionService.listarFiseObservacion();
		Map<String, String> mapaErrores = new HashMap<String, String>();
		for (FiseObservacion error : listaFiseObservacion) {
			logger.info("codError: "+error.getIdObservacion()+" descError: "+error.getDescripcion());
			mapaErrores.put(error.getIdObservacion(), error.getDescripcion());
		}
		return mapaErrores;
	}
	
	public Map<Long, String> getMapaZonaBenef(){
		List<FiseZonaBenef> listaZonaBenef = zonaBenefService.listarFiseZonaBenef();
		Map<Long, String> mapaZonaBenef = new HashMap<Long, String>();
		for (FiseZonaBenef zonaBenef : listaZonaBenef) {
			logger.info("zonaBenef: "+zonaBenef.getIdZonaBenef()+" zonadesc: "+zonaBenef.getDescripcion());
			mapaZonaBenef.put(zonaBenef.getIdZonaBenef(), zonaBenef.getDescripcion());
		}
		return mapaZonaBenef;
	}
	
	public void enviarMailAdjunto(PortletRequest request,List<FileEntryJSP> listaArchivo, String descripcionFormato) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		try {
			MailMessage mailMessage = new MailMessage();
			mailMessage.setHTMLFormat(true);
			
			String nombreUsuario=themeDisplay.getUser().getLogin();
			String correoR = PrefsPropsUtil.getString(PropsKeys.MAIL_SESSION_MAIL_SMTP_USER);
			String correoD = themeDisplay.getUser().getEmailAddress();
			
			List<CorreoBean> listaCorreoDestino = commonService.obtenerListaCorreosDestinatarios();
			
			mailMessage.setFrom(new InternetAddress(correoR));
			mailMessage.setSubject(" Notificación de Envío de Formato FISE");
			mailMessage.setTo(new InternetAddress(correoD));
			if( listaCorreoDestino!=null && !listaCorreoDestino.isEmpty() ){
				mailMessage.setCC(getArrayCorreoDestinatarios(listaCorreoDestino));
			}
			mailMessage.setBody("<html><head></head><body><p>Usuario "
					+ nombreUsuario + "<u></u><u></u></p><p><u></u>&nbsp;<u></u></p><p>Mediante el presente se le comunica que OSINERGMIN-GART ha recepcionado el "
					+ descripcionFormato + "<u></u><u></u></p><p><u></u><u></u></p><p>Se adjunta la constancia de envío, el formato y las observaciones<u></u><u></u></p>"
					+ "<p><u></u>&nbsp;<u></u></p><p>Atentamente,<u></u><u></u></p><p>Sistemas GART<u></u><u></u></p></body></html>");
			for (FileEntryJSP fej : listaArchivo) {
				mailMessage.addFileAttachment(FileUtil.createTempFile(fej.getFileEntry().getContentStream()), fej.getNombreArchivo());
			}
			MailServiceUtil.sendEmail(mailMessage);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public InternetAddress[] getArrayCorreoDestinatarios(List<CorreoBean> listaCorreo) throws Exception{
		InternetAddress[] arrayCorreo = new InternetAddress[listaCorreo.size()];
        for (int i = 0; i < listaCorreo.size(); i++){
        	arrayCorreo[i] = new InternetAddress(listaCorreo.get(i).getDireccionCorreo());
        }
        return arrayCorreo;
	}
	
	public String agregarErrorBeanConMensaje(String mensaje,Map<String,String> mapaError, List<MensajeErrorBean> listaError, int idError, String codigoError){
		mensaje = mensaje + mapaError.get(codigoError)+FiseConstants.SALTO_LINEA;
		MensajeErrorBean error = new MensajeErrorBean();
		error.setId(idError);
		error.setDescripcion(mapaError.get(codigoError));
		listaError.add(error);
		return mensaje;
	}
	
	public void agregarErrorBean(Map<String,String> mapaError, List<MensajeErrorBean> listaError, int idError, String codigoError){
		MensajeErrorBean error = new MensajeErrorBean();
		error.setId(idError);
		error.setDescripcion(mapaError.get(codigoError));
		listaError.add(error);
	}
	
}
