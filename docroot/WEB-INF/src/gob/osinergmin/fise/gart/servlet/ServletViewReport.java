package gob.osinergmin.fise.gart.servlet;

import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.gart.controller.Formato12AGartController;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

public class ServletViewReport extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	Log logger=LogFactoryUtil.getLog(Formato12AGartController.class);

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		HttpSession sesion = request.getSession();
		ServletOutputStream servletOutputStream = response.getOutputStream();
		
		int DEFAULT_BUFFER_SIZE = 1024;
		BufferedOutputStream output = null;
		
		String directorio = null;
		//directorio =  "/reports/formato12A.jasper";

		String nombreReporte = (String) sesion.getAttribute("nombreReporte");
		String nombreArchivo = (String) sesion.getAttribute("nombreArchivo");
		String tipoFormato = (String) sesion.getAttribute("tipoFormato");
		String tipoArchivo = (String) sesion.getAttribute("tipoArchivo");
		
		@SuppressWarnings("unchecked")
		Map<String, Object> parametros = (Map<String, Object>) sesion.getAttribute("mapa");
		List<?> lista = (List<?>)sesion.getAttribute("lista");
		
		if(parametros!=null){
			parametros.put("IMG",request.getSession().getServletContext().getRealPath("/reports/logoOSINERGMIN.jpg"));
			parametros.put(JRParameter.REPORT_LOCALE, Locale.US);
		}
		
		directorio =  "/reports/"+nombreReporte+".jasper";

		File reportFile = new File(getServletConfig().getServletContext().getRealPath(directorio));

		byte[] bytes = null;
		try {
			
			JRDataSource coleccion = new JREmptyDataSource();
			if( FiseConstants.TIPO_FORMATO_12A.equals(tipoFormato) ){
				coleccion = new JREmptyDataSource();
			}else if( FiseConstants.TIPO_FORMATO_12B.equals(tipoFormato) ){
				coleccion = new JREmptyDataSource();
			}else if( FiseConstants.TIPO_FORMATO_12C.equals(tipoFormato) ){
				if( lista!=null && lista.size()>0){
					coleccion = new JRBeanCollectionDataSource(lista);
				}else{
					coleccion = new JREmptyDataSource();
				}
			}else if( FiseConstants.TIPO_FORMATO_12D.equals(tipoFormato) ){
				if( lista!=null && lista.size()>0){
					coleccion = new JRBeanCollectionDataSource(lista);
				}else{
					coleccion = new JREmptyDataSource();
				}
			}else if( FiseConstants.TIPO_FORMATO_13A.equals(tipoFormato) ){
				if( lista!=null && lista.size()>0){
					coleccion = new JRBeanCollectionDataSource(lista);
				}else{
					coleccion = new JREmptyDataSource();
				}
			}else if( FiseConstants.TIPO_FORMATO_14A.equals(tipoFormato) ){
				coleccion = new JREmptyDataSource();
			}else if( FiseConstants.TIPO_FORMATO_14B.equals(tipoFormato) ){
				coleccion = new JREmptyDataSource();
			}else if( FiseConstants.TIPO_FORMATO_14C.equals(tipoFormato) ){
				//colocar si se va a enviar lista o vacio
			}
			//
			else if( FiseConstants.TIPO_FORMATO_ACTAENVIO.equals(tipoFormato) ){
				coleccion = new JREmptyDataSource();
			}
			//
			else if( FiseConstants.TIPO_FORMATO_VAL.equals(tipoFormato) ){
				coleccion = new JRBeanCollectionDataSource(lista);
			}
			else if( FiseConstants.TIPO_FORMATO_VAL_12C.equals(tipoFormato) ){
				coleccion = new JRBeanCollectionDataSource(lista);
			}
			else if( FiseConstants.TIPO_FORMATO_VAL_12D.equals(tipoFormato) ){
				coleccion = new JRBeanCollectionDataSource(lista);
			}
			//
			else if( FiseConstants.TIPO_FORMATO_VAL_13A.equals(tipoFormato) ){
				coleccion = new JRBeanCollectionDataSource(lista);
			}
			//
			else if( FiseConstants.TIPO_FORMATO_CUMPLIMIENTO.equals(tipoFormato) ){
				coleccion = new JRBeanCollectionDataSource(lista);
			}
			else if( FiseConstants.TIPO_FORMATO_RESUMEN_COSTOS.equals(tipoFormato) ){
				coleccion = new JRBeanCollectionDataSource(lista);
			}
			
			if( FiseConstants.FORMATO_EXPORT_PDF.equals(tipoArchivo) ){
				bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parametros, coleccion);
				
				if (bytes != null) {
					int size = bytes.length;
					response.reset();
					response.setBufferSize(DEFAULT_BUFFER_SIZE);
					response.setHeader("Content-Length", String.valueOf(size));
					response.setContentType("application/pdf");
					//response.setHeader("Content-Disposition", "attachment;filename=\"" + nombreArchivo + ".pdf" + "\"");
					response.setHeader("Content-Disposition", "inline;filename=\"" + nombreArchivo + ".pdf" + "\"");
					output = new BufferedOutputStream(servletOutputStream, DEFAULT_BUFFER_SIZE);
					output.write(bytes);
				}
			}else if( FiseConstants.FORMATO_EXPORT_XLS.equals(tipoArchivo) ){
				//EXPORTAR A EXCEL
				JasperPrint print = JasperFillManager.fillReport(reportFile.getPath(), parametros, coleccion);
				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				response.setHeader("Content-Disposition", "inline;filename=\"" + nombreArchivo + ".xlsx" + "\"");
				JRXlsxExporter exporter = new JRXlsxExporter();		
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.FALSE);
				exporter.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS, Boolean.FALSE);
				exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
				exporter.setParameter(JRXlsExporterParameter.IS_FONT_SIZE_FIX_ENABLED, Boolean.TRUE);
				exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
				exporter.exportReport();
			}
			//para exportar a excel solo con parametros y lista vacia =5
			else if( FiseConstants.FORMATO_EXPORT_XLS_SIN_LISTA.equals(tipoArchivo) ){
				//EXPORTAR A EXCEL
				JasperPrint print = JasperFillManager.fillReport(reportFile.getPath(), parametros, new JREmptyDataSource());
				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				response.setHeader("Content-Disposition", "inline;filename=\"" + nombreArchivo + ".xlsx" + "\"");
				JRXlsxExporter exporter = new JRXlsxExporter();		
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.FALSE);
				exporter.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS, Boolean.FALSE);
				exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
				exporter.setParameter(JRXlsExporterParameter.IS_FONT_SIZE_FIX_ENABLED, Boolean.TRUE);
				exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
				exporter.exportReport();
			}
			//
			else if(FiseConstants.FORMATO_EXPORT_ACTAENVIO.equals(tipoArchivo) ){
				byte[] bytes3 = (byte[])sesion.getAttribute("bytesActaEnvio");
				response.reset();
				response.setBufferSize(DEFAULT_BUFFER_SIZE);
				response.setHeader("Content-Length", String.valueOf(bytes3.length));
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", "inline;filename=\"" + "actaEnvio" + ".pdf" + "\"");
				output = new BufferedOutputStream(servletOutputStream, DEFAULT_BUFFER_SIZE);
				output.write(bytes3);
			}
			else if(FiseConstants.FORMATO_EXPORT_FORMATO_COMPLETO.equals(tipoArchivo) ){
				byte[] bytes3 = (byte[])sesion.getAttribute("bytesFormato");
				response.reset();
				response.setBufferSize(DEFAULT_BUFFER_SIZE);
				response.setHeader("Content-Length", String.valueOf(bytes3.length));
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", "inline;filename=\"" + tipoFormato + ".pdf" + "\"");
				output = new BufferedOutputStream(servletOutputStream, DEFAULT_BUFFER_SIZE);
				output.write(bytes3);
			}			
			servletOutputStream.flush();
			servletOutputStream.close();
		} catch (Exception e) {
			if (logger.isInfoEnabled())
				logger.info("Excepcion al exportar: " + e.getMessage(), e);
		}finally {
			if (output != null) {
				output.close();
			}
		}
	}	

	/**
	 * Handles the HTTP <code>GET</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// System.out.println("SERVLET GET EXPORTAR A EXCEL");
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// System.out.println("SERVLET POST EXPORTAR A EXCEL");
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 * 
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
