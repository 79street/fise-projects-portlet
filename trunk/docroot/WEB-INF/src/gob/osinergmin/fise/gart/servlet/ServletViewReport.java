package gob.osinergmin.fise.gart.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JasperRunManager;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@WebServlet("/ServletViewReport")
public class ServletViewReport extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		HttpSession sesion = request.getSession();
		ServletOutputStream servletOutputStream = response.getOutputStream();
		
		String directorio = null;
		directorio =  "/reports/ActaRemision.jasper";

		String codEmpresa = null;
		codEmpresa = (String) sesion.getAttribute("cod_empresa");
		
		String idPeriodo = null;
		idPeriodo = (String) sesion.getAttribute("id_periodo");
		
		String codProceso = null;
		codProceso = (String) sesion.getAttribute("cod_proceso");


		File reportFile = new File(getServletConfig().getServletContext().getRealPath(directorio));

		byte[] bytes = null;
		try {
			HashMap<String, Object> mapJRParams = new HashMap<String, Object>();

			mapJRParams.put("COD_PROCESO",codProceso);
			mapJRParams.put("ID_PERIODO",idPeriodo);
			mapJRParams.put("COD_EMPRESA",codEmpresa);
			mapJRParams.put("IMG",request.getSession().getServletContext().getRealPath("/reports/logo.jpg"));
			mapJRParams.put("SUBREPORT_DIR",request.getSession().getServletContext().getRealPath("/reports/"));
			DataSource ds1 = (DataSource) springContext.getBean("dataSource");
			Connection cn = ds1.getConnection();
			bytes = JasperRunManager.runReportToPdf(reportFile.getPath(),mapJRParams,cn);
			
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			servletOutputStream.write(bytes, 0, bytes.length);
			servletOutputStream.flush();
			servletOutputStream.close();
		} catch (Exception e) {
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
			response.getOutputStream().print(stringWriter.toString());
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
