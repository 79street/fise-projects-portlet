package gob.osinergmin.fise.common.util;

import gob.osinergmin.fise.bean.CorreoBean;
import gob.osinergmin.fise.bean.MensajeErrorBean;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.AdmEmpresa;
import gob.osinergmin.fise.domain.AdmUbigeo;
import gob.osinergmin.fise.domain.FiseFormato14BD;
import gob.osinergmin.fise.domain.FiseObservacion;
import gob.osinergmin.fise.domain.FiseTipDocRef;
import gob.osinergmin.fise.domain.FiseTipGasto;
import gob.osinergmin.fise.domain.FiseZonaBenef;
import gob.osinergmin.fise.gart.jsp.FileEntryJSP;
import gob.osinergmin.fise.gart.service.AdmEmpresaGartService;
import gob.osinergmin.fise.gart.service.AdmUbigeoGartService;
import gob.osinergmin.fise.gart.service.CommonGartService;
import gob.osinergmin.fise.gart.service.FiseObservacionGartService;
import gob.osinergmin.fise.gart.service.FiseTipDocRefGartService;
import gob.osinergmin.fise.gart.service.FiseTipGastoGartService;
import gob.osinergmin.fise.gart.service.FiseZonaBenefGartService;
import gob.osinergmin.fise.gart.service.Formato12AGartService;
import gob.osinergmin.fise.gart.service.Formato14BGartService;
import gob.osinergmin.fise.util.FechaUtil;
import gob.osinergmin.fise.util.FormatoUtil;
import gob.osinergmin.fise.xls.XlsTableConfig;
import gob.osinergmin.fise.xls.XlsWorkbookConfig;
import gob.osinergmin.fise.xls.XlsWorksheetConfig;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.portlet.PortletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.liferay.mail.service.MailServiceUtil;
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
	private static final Logger logger = Logger.getLogger(FiseUtil.class);
	
	public static final String FORMATO_DDMMYYYY ="dd/MM/yyyy";
	
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
	@Autowired
	@Qualifier("admUbigeoGartServiceImpl")
	AdmUbigeoGartService admUbigeoService;
	@Autowired
	@Qualifier("fiseTipDocRefGartServiceImpl")
	FiseTipDocRefGartService tipDocRefGartService;
	@Autowired
	@Qualifier("fiseTipGastoGartServiceImpl")
	FiseTipGastoGartService tipGastoGartService;
	
	@Autowired
	@Qualifier("formato14BGartServiceImpl")
    Formato14BGartService formatoService14B;
	
	public List<AdmEmpresa> getEmpresaxUsuario(PortletRequest request){
		 List<AdmEmpresa> listaEmpresas=new ArrayList<AdmEmpresa>();
		try {
			ThemeDisplay theme = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			String cadenaEmpresas ="";
			boolean bAdministrador =esAdministradorEmpresa(request);
			
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
		
		System.out.println("listaEmpresas::>"+listaEmpresas.size());
		
		return listaEmpresas;
	}
	
	public Map<String, String> getMapaEmpresa(){
		Map<String, String> mapaEmpresa = new HashMap<String, String>();
		for (AdmEmpresa admEmpresa : admEmpresaService.listarAdmEmpresa()) {
			mapaEmpresa.put(admEmpresa.getCodEmpresa(), admEmpresa.getDscCortaEmpresa());
		}
		return mapaEmpresa;
	}
	
	public static String descripcionPeriodo(Long mes,Long anio,String etapa){
		try{
		      
			  String [] monthNames = {"Ene","Feb","Mar","Abr","May","Jun","Jul","Ago","Sep","Oct","Nov","Dic"};
			   String descripcionPeriodo=""+monthNames[mes.intValue()-1]+"-"+anio+"/"+etapa;
			  return descripcionPeriodo;
		
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}
	
	public static String descripcionMes(Integer mes){
		try{
		      
			  String [] monthNames = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
			   String descripcionMes=""+monthNames[mes.intValue()-1];
			  return descripcionMes;
		
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
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
	
	public String obtenerNroAnioFechaAnterior(){
		int anioFechaActual = Integer.parseInt(FechaUtil.obtenerNroAnioFechaActual());
		int mesFechaActual = Integer.parseInt(FechaUtil.obtenerNroMesFechaActual());
		if( mesFechaActual == 1 ){
			return String.valueOf(anioFechaActual-1);
		}else{
			return String.valueOf(anioFechaActual);
		}
	}
	public String obtenerNroMesFechaAnterior(){
		int mesFechaActual = Integer.parseInt(FechaUtil.obtenerNroMesFechaActual());
		if( mesFechaActual == 1 ){
			return String.valueOf(Calendar.DECEMBER+1);//diciembre
		}else{
			return String.valueOf(mesFechaActual-1);
		}
	}
	
	public boolean esAdministradorEmpresa(PortletRequest request){
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
	
	public boolean esAdministrador(PortletRequest request){
		try {
			ThemeDisplay theme = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			return commonService.esAdministradorFise(theme.getUser().getLogin());
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
	

	public void configuracionExportarExcelImplementacionMensual(HttpSession session, String tipoFormato, List<?> lista){
		session.setAttribute(FiseConstants.TIPO_FORMATO_EXCEL_EXPORT, tipoFormato);
		session.setAttribute(FiseConstants.LISTA_FORMATO_EXCEL_EXPORT, lista);
	}
	
	public FileEntry subirDocumentoTxt(PortletRequest request, UploadPortletRequest uploadPortletRequest, String tipoArchivo) throws FileMimeTypeException,Exception{

		// TODO Auto-generated method stub
		FileEntry fileEntry=null;
		//--UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(request);
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		System.out.println("subirDocumento::"+request+":::uploadPortletRequest::"+uploadPortletRequest);
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
			 if( FiseConstants.TIPOARCHIVO_TXT.equals(tipoArchivo) ){
				System.out.println("tipo txt");
				nameFileInput = "archivoExcel";
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
				System.out.println(mimeType);
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
	
	public FileEntry subirDocumento(PortletRequest request, UploadPortletRequest uploadPortletRequest, String tipoArchivo) throws FileMimeTypeException,Exception{

		// TODO Auto-generated method stub
		FileEntry fileEntry=null;
		//--UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(request);
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		System.out.println("subirDocumento::"+request+":::uploadPortletRequest::"+uploadPortletRequest);
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
				System.out.println("tipo xls");
				nameFileInput = "archivoExcel";
				mimeTypes = mimeTypesXls;
			}else if( FiseConstants.TIPOARCHIVO_TXT.equals(tipoArchivo) ){
				System.out.println("tipo txt");
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
				System.out.println(mimeType);
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
	
	public Map<String, String> getMapTipoDocumento(){
		List<FiseTipDocRef> listaTipoDocumento = tipDocRefGartService.listarFiseTipDocRef();
		Map<String, String> mapaTipoDoc = new HashMap<String, String>();
		for (FiseTipDocRef tipoDoc : listaTipoDocumento) {
			logger.info("idTipDocRef: "+tipoDoc.getIdTipDocRef()+" descripcion: "+tipoDoc.getDescripcion());
			mapaTipoDoc.put(tipoDoc.getIdTipDocRef(), tipoDoc.getDescripcion());
		}
		return mapaTipoDoc;
	}
	
	/*public void enviarMailAdjunto(PortletRequest request,List<FileEntryJSP> listaArchivo, String descripcionFormato) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		try {
			MailMessage mailMessage = new MailMessage();
			mailMessage.setHTMLFormat(true);
			
			String nombreUsuario=themeDisplay.getUser().getLogin();
			String correoR = PrefsPropsUtil.getString(PropsKeys.MAIL_SESSION_MAIL_SMTP_USER);
			String correoD = themeDisplay.getUser().getEmailAddress();
			//String correoR = "informacion@sphere.com.pe";
			//String correoD = "edwin.heredia@sphere.com.pe";
			logger.info("correo remitente: "+correoR);
			logger.info("correo destinatario: "+correoD);
			
			List<CorreoBean> listaCorreoDestino = commonService.obtenerListaCorreosDestinatarios();
			
			if( !FiseConstants.BLANCO.equals(correoR) && !FiseConstants.BLANCO.equals(correoD) ){
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
			}else{
				throw new AddressException("No esta configurado el correo de Remitente o Destinatario");
			}

		} catch (Exception e) {
			e.printStackTrace();
			//throw e;
		}
	}*/
	
	public String  enviarMailsAdjunto(PortletRequest request,List<FileEntryJSP> listaArchivo,
			String descEmpresa,	Long anoPresentacion, Long mesPresentacion, 
			String tipoFormato, String descripcionFormato, String frecuencia,Long anoIniVigencia, Long anoFinVigencia) throws Exception {		
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);		
		
		String admin = enviarMailAdjuntoAdministrador(themeDisplay, listaArchivo, descEmpresa, 
				anoPresentacion, mesPresentacion, tipoFormato, descripcionFormato, frecuencia, anoIniVigencia, anoFinVigencia);
		
		String[] msnIdAdmin = admin.split("/");
		
		if(FiseConstants.PROCESO_ENVIO_EMAIL_OK.equals(msnIdAdmin[0])){
			String user = enviarMailAdjuntoUsuario(themeDisplay, listaArchivo, descEmpresa, 
					anoPresentacion, mesPresentacion, tipoFormato, descripcionFormato, frecuencia, anoIniVigencia, anoFinVigencia);
			String[] msnIdUser = user.split("/");
			if(FiseConstants.PROCESO_ENVIO_EMAIL_OK.equals(msnIdUser[0])){
				String correos = msnIdAdmin[1] + " y " + msnIdUser[1].toString();
				String valor = FiseConstants.PROCESO_ENVIO_EMAIL_OK+"/"+correos;
				return valor;
			}else{
				return user;
			}		 
		}else{
			return admin;
		}
	}
	
	private String enviarMailAdjuntoAdministrador(ThemeDisplay themeDisplay,List<FileEntryJSP> listaArchivo, 
			String descEmpresa, Long anoPresentacion, Long mesPresentacion, 
			String tipoFormato, String descripcionFormato, String frecuencia,Long anoIniVigencia, Long anoFinVigencia) throws Exception {
		String valor =FiseConstants.PROCESO_ENVIO_EMAIL_ERROR+"/"+"";
		try {
			MailMessage mailMessage = new MailMessage();
			mailMessage.setHTMLFormat(true);
			
			String nombreUsuario = themeDisplay.getUser().getFullName();
			String periodoEnvio = ""+anoPresentacion+"-"+mesPresentacion;
			String descripcionCosto ="";
			String descripcionPeriodo="";
			if( FiseConstants.FRECUENCIA_BIENAL_DESCRIPCION.equals(frecuencia) ){
				descripcionCosto = "Costos Est&aacute;ndares";
				descripcionPeriodo = ""+anoIniVigencia+"-"+anoFinVigencia+" declarado el ";
			}else if( FiseConstants.FRECUENCIA_MENSUAL_DESCRIPCION.equals(frecuencia) ){
				descripcionCosto = "Gastos Operativos";
			}
			
			String correoR = PrefsPropsUtil.getString(PropsKeys.MAIL_SESSION_MAIL_SMTP_USER);
			
			String correoD = themeDisplay.getUser().getEmailAddress();
			logger.info("correo remitente: "+correoR);
			logger.info("correo a copiar: "+correoD);
			String correoMostrar = "";
			//
			List<CorreoBean> listaCorreoDestino = commonService.obtenerListaCorreosDestinatarios();
			//validamos que tanto el correo del from ni del to deben estar vacios
			if( !FiseConstants.BLANCO.equals(correoR) && (listaCorreoDestino!=null && !listaCorreoDestino.isEmpty()) ){
				mailMessage.setFrom(new InternetAddress(correoR));
				mailMessage.setSubject("Notificación de envío de formato para el administrador del FISE (GART-DDE)");
				mailMessage.setTo(getArrayCorreoDestinatarios(listaCorreoDestino));
				if( !FiseConstants.BLANCO.equals(correoD) ){
					mailMessage.setCC(new InternetAddress(correoD));
				}
				mailMessage.setBody("<html><head></head><body><p>Estimado(a) "
						+ nombreUsuario + "<u></u><u></u></p><p><u></u>&nbsp;<u></u></p><p>Mediante el presente se le comunica que la empresa "
						+ descEmpresa + " ha cumplido con enviar informaci&oacute;n para el periodo " 
						+ descripcionPeriodo 
						+ periodoEnvio + " del "
						+ descripcionFormato + ".<u></u><u></u></p><p><u></u>&nbsp;<u></u></p><p>Se adjunta Acta de Remisi&oacute;n de Informaci&oacute;n de "
						+ descripcionCosto	+ ", Formato "
						+ tipoFormato + " y Resultados de Validaci&oacute;n (Observaciones).<u></u><u></u></p><p><u></u>&nbsp;<u></u></p><p>Cordialmente,<u></u><u></u></p><p>Sistemas GART<u></u><u></u></p></body></html>");
				for (FileEntryJSP fej : listaArchivo) {
					mailMessage.addFileAttachment(FileUtil.createTempFile(fej.getFileEntry().getContentStream()), fej.getNombreArchivo());
				}
				MailServiceUtil.sendEmail(mailMessage);
				for(CorreoBean c:listaCorreoDestino){
					if(FormatoUtil.isNotBlank(correoMostrar)){
						correoMostrar = correoMostrar +","+ c.getDireccionCorreo();		
					}else{
						correoMostrar = c.getDireccionCorreo();	
					}					
				}
		        valor =FiseConstants.PROCESO_ENVIO_EMAIL_OK+"/"+correoMostrar;
			}else{				
				valor =FiseConstants.PROCESO_ENVIO_EMAIL_ERROR+"/"+"No esta configurado el correo de Remitente o Destinatario";
			}
		} catch (Exception e) {
			valor =FiseConstants.PROCESO_ENVIO_EMAIL_ERROR+"/"+"Ocurrió un error al enviar e-mail a los Administradores de la Dist. Eléctrica.";
			e.printStackTrace();			
		}
		return valor;
	}
	
	private String  enviarMailAdjuntoUsuario(ThemeDisplay themeDisplay,List<FileEntryJSP> listaArchivo, 
			String descEmpresa, Long anoPresentacion, Long mesPresentacion, String tipoFormato,
			String descripcionFormato, String frecuencia,Long anoIniVigencia, Long anoFinVigencia) throws Exception {
		String valor =FiseConstants.PROCESO_ENVIO_EMAIL_ERROR+"/"+"";
		try {
			MailMessage mailMessage = new MailMessage();
			mailMessage.setHTMLFormat(true);
			
			String nombreUsuario = themeDisplay.getUser().getFullName();
			String periodoEnvio = ""+anoPresentacion+"-"+mesPresentacion;
			String descripcionCosto ="";
			String descripcionPeriodo="";
			if( FiseConstants.FRECUENCIA_BIENAL_DESCRIPCION.equals(frecuencia) ){
				descripcionCosto = "Costos Est&aacute;ndares";
				descripcionPeriodo = ""+anoIniVigencia+"-"+anoFinVigencia+" declarado el ";
			}else if( FiseConstants.FRECUENCIA_MENSUAL_DESCRIPCION.equals(frecuencia) ){
				descripcionCosto = "Gastos Operativos";
			}
			
			String correoR = PrefsPropsUtil.getString(PropsKeys.MAIL_SESSION_MAIL_SMTP_USER);
			String correoD = themeDisplay.getUser().getEmailAddress();
			logger.info("correo remitente: "+correoR);
			logger.info("correo destinatario: "+correoD);
			
			List<CorreoBean> listaCorreoDestino = commonService.obtenerListaCorreosDestinatarios();
			
			if( !FiseConstants.BLANCO.equals(correoR) && !FiseConstants.BLANCO.equals(correoD) ){
				mailMessage.setFrom(new InternetAddress(correoR));
				mailMessage.setSubject("Notificación de envío de formato para el usuario de la Distribuidora Eléctrica");
				mailMessage.setTo(new InternetAddress(correoD));
				if( listaCorreoDestino!=null && !listaCorreoDestino.isEmpty() ){
					mailMessage.setCC(getArrayCorreoDestinatarios(listaCorreoDestino));
				}
				mailMessage.setBody("<html><head></head><body><p>Estimado(a) "
						+ nombreUsuario + "<u></u><u></u></p><p>Empresa: "
						+ descEmpresa + "<u></u><u></u></p><p><u></u>&nbsp;<u></u></p><p>Mediante el presente se le comunica que su representada ha cumplido con enviar informaci&oacute;n para el periodo "
						+ descripcionPeriodo 
						+ periodoEnvio + " del "
						+ descripcionFormato + ".<u></u><u></u></p><p><u></u>&nbsp;<u></u></p><p>Se adjunta Acta de Remisi&oacute;n de Informaci&oacute;n de "
						+ descripcionCosto	+ ", Formato "
						+ tipoFormato + " y Resultados de Validaci&oacute;n (Observaciones).<u></u><u></u></p><p><u></u>&nbsp;<u></u></p>"
						+ "<p>Recomendamos tener en cuenta aquellos formatos faltantes para que, de acuerdo a sus necesidades, lo registren y env&iacute;en a la brevedad. As&iacute; mismo una vez enviado todos los formatos, cerrar el proceso de env&iacute;o.<u></u><u></u></p>"
						+ "<p><u></u>&nbsp;<u></u></p><p>Si tiene alg&uacute;n inconveniente para registrar y enviar los formatos establecidos, comun&iacute;quese con nosotros, escribi&eacute;ndonos un correo al: sistemasgart@osinergmin.gob.pe, mdamas@osinergmin.gob.pe y jguillermo@osinergmin.gob.pe.<u></u><u></u></p>"
						+ "<p><u></u>&nbsp;<u></u></p><p>Cordialmente,<u></u><u></u></p><p>Sistemas GART<u></u><u></u></p></body></html>");
				for (FileEntryJSP fej : listaArchivo) {
					mailMessage.addFileAttachment(FileUtil.createTempFile(fej.getFileEntry().getContentStream()), fej.getNombreArchivo());
				}
				MailServiceUtil.sendEmail(mailMessage);
				valor =FiseConstants.PROCESO_ENVIO_EMAIL_OK+"/"+correoD;
			}else{
				valor =FiseConstants.PROCESO_ENVIO_EMAIL_ERROR+"/"+"No esta configurado el correo de Remitente o Destinatario";
			}
		} catch (Exception e) {
			valor =FiseConstants.PROCESO_ENVIO_EMAIL_ERROR+"/"+"Ocurrió un error al enviar e-mail a los Usuarios de la Dist. Eléctrica.";
			e.printStackTrace();		
		}
		return valor;
	}
	
	public String enviarMailsAdjuntoValidacion(PortletRequest request,List<FileEntryJSP> listaArchivo,
			String descEmpresa,String descGrupoInf,String codEmpresa) throws Exception {		
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);		
		
		return enviarMailAdjuntoUsuarioValidacion(themeDisplay, listaArchivo, descEmpresa,descGrupoInf,codEmpresa);		
	}
	
	private String enviarMailAdjuntoUsuarioValidacion(ThemeDisplay themeDisplay,List<FileEntryJSP> listaArchivo, 
			String descEmpresa,String descGrupoInf,String codEmpresa) throws Exception {
		String valor =FiseConstants.PROCESO_ENVIO_EMAIL_ERROR+"/"+"";
		try {
			MailMessage mailMessage = new MailMessage();
			mailMessage.setHTMLFormat(true);	
			
			String nombreUsuario = themeDisplay.getUser().getFullName();		
			
			String correoR = PrefsPropsUtil.getString(PropsKeys.MAIL_SESSION_MAIL_SMTP_USER);
			String correoD = themeDisplay.getUser().getEmailAddress();
			logger.info("correo remitente: "+correoR);
			logger.info("correo destinatario: "+correoD);
			String correoMostrar = "";
			List<CorreoBean> listaCorreoDestino = commonService.obtenerListaCorreosDestinatariosByEmpresa(codEmpresa);	
			if(!FiseConstants.BLANCO.equals(correoR) && !FiseConstants.BLANCO.equals(correoD) &&
					listaCorreoDestino!=null && !listaCorreoDestino.isEmpty()){
				mailMessage.setFrom(new InternetAddress(correoR));
				mailMessage.setSubject("Notificación de envío de observaciones para el usuario de la Distribuidora Eléctrica");
				mailMessage.setTo(getArrayCorreoDestinatarios(listaCorreoDestino));			
				if( !FiseConstants.BLANCO.equals(correoD) ){
					mailMessage.setCC(new InternetAddress(correoD));
				}
				mailMessage.setBody("<html><head></head><body><p>Estimado(a) "
						+ nombreUsuario + "<u></u><u></u></p><p>Empresa: "
						+ descEmpresa + "<u></u><u></u></p><p><u></u>&nbsp;<u></u></p><p>Mediante el presente se le env&iacute;a adjunto las observaciones de los formatos reportados correspondiente al Grupo de Informaci&oacute;n "
						+ descGrupoInf + ". "
						+ "" + "<u></u><u></u></p><p><u></u>&nbsp;<u></u></p>"
						+ "<p>Para subsanar las obsevaciones debe ingresar al sistema y corregir los datos para la etapa LEVANTAMIENTO DE OBSERVACIONES (LEV.OBS).<u></u><u></u></p>"
						+ "<p><u></u>&nbsp;<u></u></p><p>Si tiene alg&uacute;n inconveniente, comun&iacute;quese con nosotros, escribi&eacute;ndonos un correo a: sistemasgart@osinergmin.gob.pe, mdamas@osinergmin.gob.pe y jguillermo@osinergmin.gob.pe.<u></u><u></u></p>"
						+ "<p><u></u>&nbsp;<u></u></p><p>Cordialmente,<u></u><u></u></p><p>Sistemas GART<u></u><u></u></p></body></html>");
				for (FileEntryJSP fej : listaArchivo) {
					mailMessage.addFileAttachment(FileUtil.createTempFile(fej.getFileEntry().getContentStream()), fej.getNombreArchivo());
				}
				MailServiceUtil.sendEmail(mailMessage);
				for(CorreoBean c:listaCorreoDestino){
					if(FormatoUtil.isNotBlank(correoMostrar)){
						correoMostrar = correoMostrar +","+ c.getDireccionCorreo();		
					}else{
						correoMostrar = c.getDireccionCorreo();	
					}					
			    }
				valor =FiseConstants.PROCESO_ENVIO_EMAIL_OK+"/"+correoMostrar;
			}else{
				valor =FiseConstants.PROCESO_ENVIO_EMAIL_ERROR+"/"+"No esta configurado el correo de Remitente o Destinatario";
				//throw new AddressException("No esta configurado el correo de Remitente o Destinatario");
			}          
		} catch (Exception e) {
			valor =FiseConstants.PROCESO_ENVIO_EMAIL_ERROR+"/"+"Ocurrió un error al enviar e-mail a los Usuarios de la Dist. Eléctrica.";
			e.printStackTrace();		
		}
		return valor;
	}
	
	public String enviarMailsAdjuntoEnvioGeneral(PortletRequest request,List<FileEntryJSP> listaArchivo,
			String descEmpresa,String descGrupoInf, String frecuencia) throws Exception {
		
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);		
		
		String admin= enviarMailAdjuntoAdmEnvioGeneral(themeDisplay, listaArchivo, descEmpresa, 
				descGrupoInf, frecuencia);
		String[] msnIdAdmin = admin.split("/");
		if(FiseConstants.PROCESO_ENVIO_EMAIL_OK.equals(msnIdAdmin[0])){
			return enviarMailAdjuntoUsuEnvioGeneral(themeDisplay, listaArchivo, descEmpresa, 
					descGrupoInf, frecuencia);
		}else{
			return admin;
		}		
	}
	
	private String enviarMailAdjuntoAdmEnvioGeneral(ThemeDisplay themeDisplay,List<FileEntryJSP> listaArchivo, 
			String descEmpresa, String descGrupoInf, String frecuencia) throws Exception {
		String valor =FiseConstants.PROCESO_ENVIO_EMAIL_ERROR+"/"+"";
		try {
			MailMessage mailMessage = new MailMessage();
			mailMessage.setHTMLFormat(true);
			
			String nombreUsuario = themeDisplay.getUser().getFullName();
			
			String descripcionCosto ="";
			if( FiseConstants.FRECUENCIA_BIENAL_DESCRIPCION.equals(frecuencia) ){
				descripcionCosto = "Costos Est&aacute;ndares";
			}else if( FiseConstants.FRECUENCIA_MENSUAL_DESCRIPCION.equals(frecuencia) ){
				descripcionCosto = "Gastos Operativos";
			}
			
			String correoR = PrefsPropsUtil.getString(PropsKeys.MAIL_SESSION_MAIL_SMTP_USER);			
			String correoD = themeDisplay.getUser().getEmailAddress();
			
			logger.info("correo remitente: "+correoR);
			logger.info("correo a copiar: "+correoD);			
			String correoMostrar = "";
			//
			List<CorreoBean> listaCorreoDestino = commonService.obtenerListaCorreosDestinatarios();
			//validamos que tanto el correo del from ni del to deben estar vacios
			if( !FiseConstants.BLANCO.equals(correoR) && (listaCorreoDestino!=null && !listaCorreoDestino.isEmpty()) ){
				mailMessage.setFrom(new InternetAddress(correoR));
				mailMessage.setSubject("Notificación de envío de formatos para el administrador del FISE (GART-DDE)");
				mailMessage.setTo(getArrayCorreoDestinatarios(listaCorreoDestino));
				if( !FiseConstants.BLANCO.equals(correoD) ){
					mailMessage.setCC(new InternetAddress(correoD));
				}
				mailMessage.setBody("<html><head></head><body><p>Estimado(a) "
						+ nombreUsuario + "<u></u><u></u></p><p><u></u>&nbsp;<u></u></p><p>Mediante el presente se le comunica que la empresa "
						+ descEmpresa + " ha cumplido con enviar informaci&oacute;n, correspondiente al Grupo de Informaci&oacute;n "						
						+ descGrupoInf + ".<u></u><u></u></p><p><u></u>&nbsp;<u></u></p><p>Se adjunta Acta de Remisi&oacute;n de Informaci&oacute;n de "
						+ descripcionCosto	+ ", Formatos "
						+"y Resultados de Validaciones (Observaciones).<u></u><u></u></p><p><u></u>&nbsp;<u></u></p><p>Cordialmente,<u></u><u></u></p><p>Sistemas GART<u></u><u></u></p></body></html>");
				for (FileEntryJSP fej : listaArchivo) {
					mailMessage.addFileAttachment(FileUtil.createTempFile(fej.getFileEntry().getContentStream()), fej.getNombreArchivo());
				}
				MailServiceUtil.sendEmail(mailMessage);	
				for(CorreoBean c:listaCorreoDestino){
					if(FormatoUtil.isNotBlank(correoMostrar)){
						correoMostrar = correoMostrar +","+ c.getDireccionCorreo();		
					}else{
						correoMostrar = c.getDireccionCorreo();	
					}					
				}
				valor =FiseConstants.PROCESO_ENVIO_EMAIL_OK+"/"+correoMostrar;
			}else{
				valor =FiseConstants.PROCESO_ENVIO_EMAIL_ERROR+"/"+"No esta configurado el correo de Remitente o Destinatario";				
			}
		} catch (Exception e) {
			valor =FiseConstants.PROCESO_ENVIO_EMAIL_ERROR+"/"+"Ocurrió un error al enviar e-mail a los Administradores de la Dist. Eléctrica.";
			e.printStackTrace();			
		}
		return valor;
	}
	
	private String enviarMailAdjuntoUsuEnvioGeneral(ThemeDisplay themeDisplay,List<FileEntryJSP> listaArchivo, 
			String descEmpresa, String descGrupoInf, String frecuencia) throws Exception {
		String valor =FiseConstants.PROCESO_ENVIO_EMAIL_ERROR+"/"+"";
		try {
			MailMessage mailMessage = new MailMessage();
			mailMessage.setHTMLFormat(true);
			
			String nombreUsuario = themeDisplay.getUser().getFullName();		
			
			String descripcionCosto ="";
			if( FiseConstants.FRECUENCIA_BIENAL_DESCRIPCION.equals(frecuencia) ){
				descripcionCosto = "Costos Est&aacute;ndares";
			}else if( FiseConstants.FRECUENCIA_MENSUAL_DESCRIPCION.equals(frecuencia) ){
				descripcionCosto = "Gastos Operativos";
			}
			
			String correoR = PrefsPropsUtil.getString(PropsKeys.MAIL_SESSION_MAIL_SMTP_USER);
			String correoD = themeDisplay.getUser().getEmailAddress();
			
			logger.info("correo remitente: "+correoR);
			logger.info("correo destinatario: "+correoD);
			
			List<CorreoBean> listaCorreoDestino = commonService.obtenerListaCorreosDestinatarios();
			
			if( !FiseConstants.BLANCO.equals(correoR) && !FiseConstants.BLANCO.equals(correoD) ){
				mailMessage.setFrom(new InternetAddress(correoR));
				mailMessage.setSubject("Notificación de envío de formatos para el usuario de la Distribuidora Eléctrica");
				mailMessage.setTo(new InternetAddress(correoD));
				if( listaCorreoDestino!=null && !listaCorreoDestino.isEmpty() ){
					mailMessage.setCC(getArrayCorreoDestinatarios(listaCorreoDestino));
				}
				mailMessage.setBody("<html><head></head><body><p>Estimado(a) "
						+ nombreUsuario + "<u></u><u></u></p><p>Empresa: "
						+ descEmpresa + "<u></u><u></u></p><p><u></u>&nbsp;<u></u></p><p>Mediante el presente se le comunica que su representada ha cumplido con enviar informaci&oacute;n correspondiente al Grupo de Informaci&oacute;n "
						+ descGrupoInf + ""
					    + ".<u></u><u></u></p><p><u></u>&nbsp;<u></u></p><p>Se adjunta Acta de Remisi&oacute;n de Informaci&oacute;n de "
					    + descripcionCosto	+ ", Formato "
					    + " y Resultados de Validaciones (Observaciones).<u></u><u></u></p><p><u></u>&nbsp;<u></u></p>"
						+ "<p>Si tiene alg&uacute;n inconveniente para registrar y enviar los formatos establecidos, comun&iacute;quese con nosotros, escribi&eacute;ndonos un correo a: sistemasgart@osinergmin.gob.pe, mdamas@osinergmin.gob.pe y jguillermo@osinergmin.gob.pe.<u></u><u></u></p>"
						+ "<p><u></u>&nbsp;<u></u></p><p>Cordialmente,<u></u><u></u></p><p>Sistemas GART<u></u><u></u></p></body></html>");
				for (FileEntryJSP fej : listaArchivo) {
					mailMessage.addFileAttachment(FileUtil.createTempFile(fej.getFileEntry().getContentStream()), fej.getNombreArchivo());
				}
				MailServiceUtil.sendEmail(mailMessage);	
				valor =FiseConstants.PROCESO_ENVIO_EMAIL_OK+"/"+correoD;
			}else{
				valor =FiseConstants.PROCESO_ENVIO_EMAIL_ERROR+"/"+"No esta configurado el correo de Remitente o Destinatario";				
			}
		} catch (Exception e) {
			valor =FiseConstants.PROCESO_ENVIO_EMAIL_ERROR+"/"+"Ocurrió un error al enviar e-mail a los Usuarios de la Dist. Eléctrica.";
			e.printStackTrace();			
		}
		return valor;
	}	
	
	public InternetAddress[] getArrayCorreoDestinatarios(List<CorreoBean> listaCorreo) throws Exception{
		InternetAddress[] arrayCorreo = new InternetAddress[listaCorreo.size()];
        for (int i = 0; i < listaCorreo.size(); i++){
        	arrayCorreo[i] = new InternetAddress(listaCorreo.get(i).getDireccionCorreo());
        }
        return arrayCorreo;
	}
	
	public String agregarErrorBeanConMensaje(String mensaje,Map<String,String> mapaError, 
			List<MensajeErrorBean> listaError, int idError, String codigoError){
		mensaje = mensaje + mapaError.get(codigoError)+FiseConstants.SALTO_LINEA;
		MensajeErrorBean error = new MensajeErrorBean();
		error.setId(idError);
		error.setDescripcion(mapaError.get(codigoError));
		listaError.add(error);
		return mensaje;
	}
	
	public String agregarErrorBeanConMensajeEnFila(String mensaje,Map<String,String> mapaError, 
			List<MensajeErrorBean> listaError, int idError, String codigoError,int nroFila){
		mensaje = mensaje + mapaError.get(codigoError)+FiseConstants.SALTO_LINEA;
		MensajeErrorBean error = new MensajeErrorBean();
		error.setId(idError);
		error.setDescripcion(mapaError.get(codigoError)+" en fila "+nroFila);
		listaError.add(error);
		return mensaje;
	}
	
	public void agregarErrorBean(Map<String,String> mapaError, List<MensajeErrorBean> listaError,
			int idError, String codigoError){
		MensajeErrorBean error = new MensajeErrorBean();
		error.setId(idError);
		error.setDescripcion(mapaError.get(codigoError));
		listaError.add(error);
	}
	
	public List<AdmUbigeo> listaDepartamentos(){
		return admUbigeoService.listarDepartamentos();
	}
	
	public List<AdmUbigeo> listaProvincias(String codDepartamento){
		return admUbigeoService.listarProvincias(codDepartamento);
	}
	
	public List<AdmUbigeo> listaDistritos(String codProvincia){
		return admUbigeoService.listarDistritos(codProvincia);
	}
	
	public Map<String, String> getMapaSectorTipico(){
		Map<String, String> mapaSectorTipico = new HashMap<String, String>();
		mapaSectorTipico.put(FormatoUtil.rellenaDerecha(FiseConstants.SECTOR_TIPICO_1_COD, ' ', 3), FiseConstants.SECTOR_TIPICO_1_DESC);
		mapaSectorTipico.put(FormatoUtil.rellenaDerecha(FiseConstants.SECTOR_TIPICO_2_COD, ' ', 3), FiseConstants.SECTOR_TIPICO_2_DESC);
		mapaSectorTipico.put(FormatoUtil.rellenaDerecha(FiseConstants.SECTOR_TIPICO_3_COD, ' ', 3), FiseConstants.SECTOR_TIPICO_3_DESC);
		mapaSectorTipico.put(FormatoUtil.rellenaDerecha(FiseConstants.SECTOR_TIPICO_4_COD, ' ', 3), FiseConstants.SECTOR_TIPICO_4_DESC);
		mapaSectorTipico.put(FormatoUtil.rellenaDerecha(FiseConstants.SECTOR_TIPICO_5_COD, ' ', 3), FiseConstants.SECTOR_TIPICO_5_DESC);
		mapaSectorTipico.put(FormatoUtil.rellenaDerecha(FiseConstants.SECTOR_TIPICO_6_COD, ' ', 3), FiseConstants.SECTOR_TIPICO_6_DESC);
		mapaSectorTipico.put(FormatoUtil.rellenaDerecha(FiseConstants.SECTOR_TIPICO_SER_COD, ' ', 3), FiseConstants.SECTOR_TIPICO_SER_DESC);
		mapaSectorTipico.put(FormatoUtil.rellenaDerecha(FiseConstants.SECTOR_TIPICO_ESP_COD, ' ', 3), FiseConstants.SECTOR_TIPICO_ESP_DESC);
		return mapaSectorTipico;
	}
	

	public static Date toDateFormat(String stringFecha, String formato) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(formato);
			Date fecha = sdf.parse(stringFecha);
			return fecha;
		} catch (Exception e) {
			return null;
		}
	}

	public static String toStringFormat(Date dateFecha, String formato) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(formato);
			String fecha = sdf.format(dateFecha);
			return fecha;
		} catch (Exception e) {
			return "";
		}
	}
	
	public  FiseFormato14BD getDetalle14BDbyEmpAnioEtapa(String codempresa,Integer anio,Integer mes,Integer zona,String etp){
		try {
			System.out.println("service 14B::"+formatoService14B);
			FiseFormato14BD fise14D=formatoService14B.getCostoUnitarioByEmpAnioZona(codempresa, anio,mes, zona, etp);
			return fise14D;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public  List<FiseFormato14BD> getLstCostoUnitario(String codempresa,Integer anio,Integer mes,Integer zona,String etp){
		try {
			System.out.println("service 14B::"+formatoService14B);
			List<FiseFormato14BD> lstResult=formatoService14B.getLstCostoUnitarioByEmpAnio(codempresa, anio,mes, zona, etp);
			return lstResult;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	public Map<Long, String> getMapaEtapaEjecucion(){
		Map<Long, String> mapaEtapaEjecucion = new HashMap<Long, String>();
		mapaEtapaEjecucion.put(FiseConstants.ETAPA_EJECUCION_IMPLEMENTACION_COD, FiseConstants.ETAPA_EJECUCION_IMPLEMENTACION_DESC);
		mapaEtapaEjecucion.put(FiseConstants.ETAPA_EJECUCION_OPERATIVA_COD, FiseConstants.ETAPA_EJECUCION_OPERATIVA_DESC);
		return mapaEtapaEjecucion;
	}
	
	public Map<String, String> getMapTipoGasto(){
		List<FiseTipGasto> listaTipoGasto = tipGastoGartService.listarFiseTipGasto();
		Map<String, String> mapaTipoGasto = new HashMap<String, String>();
		for (FiseTipGasto tipoDoc : listaTipoGasto) {
			logger.info("idTipGasto: "+tipoDoc.getIdTipGasto()+" descripcion: "+tipoDoc.getDescripcion());
			mapaTipoGasto.put(tipoDoc.getIdTipGasto(), tipoDoc.getDescripcion());
		}
		return mapaTipoGasto;
	}
	
	public Map<String, String> getMapaUbigeo(){
		List<AdmUbigeo> listaUbigeo = admUbigeoService.listarAdmUbigeo();
		Map<String, String> mapaUbigeo = new HashMap<String, String>();
		for (AdmUbigeo ubigeo : listaUbigeo) {
			logger.info("codUbigeo: "+ubigeo.getCodUbigeo()+" descripcion: "+ubigeo.getNomUbigeo());
			mapaUbigeo.put(ubigeo.getCodUbigeo(), ubigeo.getNomUbigeo());
		}
		return mapaUbigeo;
	}

}
