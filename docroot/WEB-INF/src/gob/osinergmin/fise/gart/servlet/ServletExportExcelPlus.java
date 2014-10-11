package gob.osinergmin.fise.gart.servlet;

import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.gart.xls.FormatoExcelExport;
import gob.osinergmin.fise.xls.XlsWorkbookConfig;
import gob.osinergmin.fise.xls.XlsWorksheetConfig;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

@WebServlet("/ServletExportExcelPlus")
public class ServletExportExcelPlus extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		FormatoExcelExport formatoExcel = new FormatoExcelExport();
		
		String key = FiseConstants.KEY_CFG_EXCEL_EXPORT;
				
		HttpSession sesion = request.getSession();
		XlsWorkbookConfig xlsWorkbook = (XlsWorkbookConfig)sesion.getAttribute(key);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition","attachment;filename="+xlsWorkbook.getName()+".xls");
		HSSFWorkbook wb = new HSSFWorkbook(); // crea libro
		
		for (XlsWorksheetConfig xlsWorksheet : xlsWorkbook.getSheets()) {
			HSSFSheet sheet = wb.createSheet(xlsWorksheet.getName());// crea hoja			
			formatoExcel.FillExcelSheet(xlsWorksheet, sheet, wb);			
		}
		OutputStream out = response.getOutputStream();
		wb.write(out);
		out.close();
	}	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 	throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
