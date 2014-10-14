package gob.osinergmin.fise.gart.servlet;

import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.gart.controller.Formato12AGartController;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperRunManager;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

@WebServlet("/ServletViewReport")
public class ServletViewReport extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	Log logger=LogFactoryUtil.getLog(Formato12AGartController.class);

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		HttpSession sesion = request.getSession();
		ServletOutputStream servletOutputStream = response.getOutputStream();
		
		int DEFAULT_BUFFER_SIZE = 1024;
		BufferedOutputStream output = null;
		
		String directorio = null;
		directorio =  "/reports/report1.jasper";

		String nombreReporte = (String) sesion.getAttribute("nombreReporte");
		String nombreArchivo = (String) sesion.getAttribute("nombreArchivo");
		String tipoFormato = (String) sesion.getAttribute("tipoFormato");
		Map<String, Object> parametros = (Map<String, Object>) sesion.getAttribute("mapa");

		File reportFile = new File(getServletConfig().getServletContext().getRealPath(directorio));

		byte[] bytes = null;
		try {
			
			//se obtendra la lista como parametro
			//Map<String, Object> parametros = new HashMap<String, Object>();
			//List<FiseFormato12AD> listaFormatoDetalle = null;
			String ruta= null;
			
			//nombreReporte
			if( FiseConstants.TIPO_FORMATO_12.equals(tipoFormato) ){
				bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parametros, new JREmptyDataSource());
			}
			
			
			if (bytes != null) {
				int size = bytes.length;
				//String nombreArchivo = "Lista";

				response.reset();
				response.setBufferSize(DEFAULT_BUFFER_SIZE);
				response.setHeader("Content-Length", String.valueOf(size));
				response.setHeader("Content-Disposition", "inline;filename=\"" + nombreArchivo + ".pdf" + "\"");
				response.setHeader("Content-Type", "application/pdf");
				output = new BufferedOutputStream(response.getOutputStream(),
						DEFAULT_BUFFER_SIZE);
				output.write(bytes);
			}
			
			//response.setContentType("application/pdf");
			//response.setContentLength(bytes.length);
			servletOutputStream.write(bytes, 0, bytes.length);
			servletOutputStream.flush();
			servletOutputStream.close();
		} catch (Exception e) {
			if (logger.isInfoEnabled())
				logger.info("Excepcion al exportar a pdf : " + e.getMessage(), e);
		}finally {
			if (output != null) {
				output.close();
			}
		}
	}

	// <editor-fold defaultstate="collapsed"
	// desc="mÃ©todos doGet y doPost creados por NetBeans">
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
