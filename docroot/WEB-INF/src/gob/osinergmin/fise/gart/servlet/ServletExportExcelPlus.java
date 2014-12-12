package gob.osinergmin.fise.gart.servlet;

import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.gart.xls.FormatoExcelExport;
import gob.osinergmin.fise.xls.XlsWorkbookConfig;
import gob.osinergmin.fise.xls.XlsWorksheetConfig;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

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
		
		FiseUtil fiseUtil = new FiseUtil();
		
		String etapaEjec = "";
		
		String key = FiseConstants.KEY_CFG_EXCEL_EXPORT;
	
		HttpSession sesion = request.getSession();
		
		String tipoFormato = (String)sesion.getAttribute(FiseConstants.TIPO_FORMATO_EXCEL_EXPORT);
		String listaFormatoExcel = FiseConstants.LISTA_FORMATO_EXCEL_EXPORT;
		
		if( FiseConstants.TIPO_FORMATO_12CD.equals(tipoFormato) ){
			etapaEjec = request.getParameter("etapaEjec");
			List<?> lista = (List<?>)sesion.getAttribute(listaFormatoExcel);
			if( FiseConstants.ETAPAEJECUCION_IMPLEMENTACION_EXCEL_EXPORT.equals(etapaEjec) ){
				fiseUtil.configuracionExportarExcel(sesion, FiseConstants.TIPO_FORMATO_12CD_IMPLEMENTACION, FiseConstants.NOMBRE_EXCEL_FORMATO12CD_IMPLEMENTACION, FiseConstants.NOMBRE_HOJA_FORMATO12CD_IMPLEMENTACION, lista);
			}else if( FiseConstants.ETAPAEJECUCION_MENSUAL_EXCEL_EXPORT.equals(etapaEjec) ){
				fiseUtil.configuracionExportarExcel(sesion, FiseConstants.TIPO_FORMATO_12CD_MENSUAL, FiseConstants.NOMBRE_EXCEL_FORMATO12CD_MENSUAL, FiseConstants.NOMBRE_HOJA_FORMATO12CD_MENSUAL, lista);
			}
		}else if( FiseConstants.TIPO_FORMATO_12DD.equals(tipoFormato) ){
			etapaEjec = request.getParameter("etapaEjec");
			List<?> lista = (List<?>)sesion.getAttribute(listaFormatoExcel);
			if( FiseConstants.ETAPAEJECUCION_IMPLEMENTACION_EXCEL_EXPORT.equals(etapaEjec) ){
				fiseUtil.configuracionExportarExcel(sesion, FiseConstants.TIPO_FORMATO_12DD_IMPLEMENTACION, FiseConstants.NOMBRE_EXCEL_FORMATO12DD_IMPLEMENTACION, FiseConstants.NOMBRE_HOJA_FORMATO12DD_IMPLEMENTACION, lista);
			}else if( FiseConstants.ETAPAEJECUCION_MENSUAL_EXCEL_EXPORT.equals(etapaEjec) ){
				fiseUtil.configuracionExportarExcel(sesion, FiseConstants.TIPO_FORMATO_12DD_MENSUAL, FiseConstants.NOMBRE_EXCEL_FORMATO12DD_MENSUAL, FiseConstants.NOMBRE_HOJA_FORMATO12DD_MENSUAL, lista);
			}
		}
		
		//HttpSession sesion = request.getSession();
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
