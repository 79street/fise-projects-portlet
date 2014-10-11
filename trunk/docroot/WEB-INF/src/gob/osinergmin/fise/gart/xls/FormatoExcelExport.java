package gob.osinergmin.fise.gart.xls;

import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.FiseFormato12AC;
import gob.osinergmin.fise.xls.XlsTableConfig;
import gob.osinergmin.fise.xls.XlsWorksheetConfig;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class FormatoExcelExport {
	
	private HSSFSheet _xlsSheet;
	private HSSFWorkbook _wb;
	//private XlsWorksheetConfig _xlsWorksheetConfig;
	
	public HSSFSheet construirExcelFormato12A(List<?> listaFormato12A) {

		HSSFFont fuenteH = _wb.createFont();
		fuenteH.setFontHeightInPoints((short) 10);
		fuenteH.setFontName(HSSFFont.FONT_ARIAL);
		fuenteH.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		// Creamos el objto HHSSFeclStyle que aplicara para el estilo a la celda
		HSSFCellStyle headerCellStyle = _wb.createCellStyle();
		headerCellStyle.setWrapText(false);
		headerCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		headerCellStyle.setFont(fuenteH);

		// Definimos los bordes de las celdas
		headerCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		headerCellStyle.setBottomBorderColor((short) 8);
		headerCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		headerCellStyle.setLeftBorderColor((short) 8);
		headerCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		headerCellStyle.setRightBorderColor((short) 8);
		headerCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		headerCellStyle.setTopBorderColor((short) 8);

		// Establecemos el tipo de sombreado de nuestra celda
		headerCellStyle.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
		headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		// Para el contenido general
		HSSFFont fuenteC = _wb.createFont();
		fuenteC.setFontHeightInPoints((short) 9);
		fuenteC.setFontName(HSSFFont.FONT_ARIAL);
		fuenteC.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

		// Luego creamos el objeto que se encargara de aplicar el estilo a la
		// celda
		HSSFCellStyle dataCellStyle = _wb.createCellStyle();
		dataCellStyle.setFont(fuenteC);

		// Tambien, podemos establecer bordes...
		dataCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		dataCellStyle.setBottomBorderColor((short) 8);
		dataCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		dataCellStyle.setLeftBorderColor((short) 8);
		dataCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		dataCellStyle.setRightBorderColor((short) 8);
		dataCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		dataCellStyle.setTopBorderColor((short) 8);

		HSSFCellStyle dateCellStyle = _wb.createCellStyle();
		dateCellStyle.cloneStyleFrom(dataCellStyle);
		dateCellStyle.setDataFormat((short) 15);

		//HSSFRow row = _xlsSheet.createRow(iRow);

		HSSFRow segundaFila = _xlsSheet.createRow(0);
		segundaFila.setHeightInPoints((2*_xlsSheet.getDefaultRowHeightInPoints()));

		HSSFCell a1 = segundaFila.createCell(1);
		a1.setCellValue(new HSSFRichTextString("Empresa"));
		a1.setCellStyle(headerCellStyle);
		
		HSSFCell a2 = segundaFila.createCell(2);
		a2.setCellValue(new HSSFRichTextString("Año Pres."));
		a2.setCellStyle(headerCellStyle);
		
		HSSFCell a3 = segundaFila.createCell(3);
		a3.setCellValue(new HSSFRichTextString("Mes. Pres."));
		a3.setCellStyle(headerCellStyle);
	
		HSSFCell a4 = segundaFila.createCell(4);
		a4.setCellValue(new HSSFRichTextString("Año Ejec."));
		a4.setCellStyle(headerCellStyle);
		
		HSSFCell a5 = segundaFila.createCell(5);
		a5.setCellValue(new HSSFRichTextString("Mes Ejec."));
		a5.setCellStyle(headerCellStyle);

		if( listaFormato12A!=null && listaFormato12A.size()>0 ){
			for( int i=0;i<listaFormato12A.size();i++ ){
				
				FiseFormato12AC fiseFormato12A = (FiseFormato12AC) listaFormato12A.get(i);
				
				HSSFRow fila = _xlsSheet.createRow(i+1);
				HSSFCell ax1 = fila.createCell(1);
				ax1.setCellValue(new HSSFRichTextString(fiseFormato12A.getDescEmpresa()));
				ax1.setCellStyle(dateCellStyle);
				HSSFCell ax2 = fila.createCell(2);
				ax2.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato12A.getId().getAnoPresentacion())));
				ax2.setCellStyle(dateCellStyle);
				HSSFCell ax3 = fila.createCell(3);
				ax3.setCellValue(new HSSFRichTextString(fiseFormato12A.getDescMesPresentacion()));
				ax3.setCellStyle(dateCellStyle);
				HSSFCell ax4 = fila.createCell(4);
				ax4.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato12A.getId().getAnoEjecucionGasto())));
				ax4.setCellStyle(dateCellStyle);
				HSSFCell ax5 = fila.createCell(5);
				ax5.setCellValue(new HSSFRichTextString(fiseFormato12A.getDescMesEjecucion()));
				ax5.setCellStyle(dateCellStyle);
			}
		}

		/**
		 * longitud automatica de columnas
		 */
		_xlsSheet.autoSizeColumn((short) 1);
		_xlsSheet.autoSizeColumn((short) 2);
		_xlsSheet.autoSizeColumn((short) 3);
		_xlsSheet.autoSizeColumn((short) 4);
		_xlsSheet.autoSizeColumn((short) 5);
		
		return _xlsSheet;

	}
	
	public HSSFSheet FillExcelSheet(XlsWorksheetConfig xlsWorksheetConfig, HSSFSheet xlsSheet, HSSFWorkbook wb) {
		_xlsSheet = xlsSheet;
		_wb = wb;
		//_xlsWorksheetConfig = xlsWorksheetConfig;

		for (XlsTableConfig xlsTableConfig : xlsWorksheetConfig.getTables()) {
			if( FiseConstants.TIPO_FORMATO_12.equals(xlsTableConfig.getTipoFormato()) ){
				construirExcelFormato12A(xlsTableConfig.getLista());
			}
			//poner los otros formato
		}
		return _xlsSheet;
	}

}
