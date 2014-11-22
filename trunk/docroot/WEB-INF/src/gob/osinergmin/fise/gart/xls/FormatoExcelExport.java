package gob.osinergmin.fise.gart.xls;

import gob.osinergmin.fise.bean.Formato13ADReportBean;
import gob.osinergmin.fise.bean.MensajeErrorBean;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.FiseFormato12AC;
import gob.osinergmin.fise.domain.FiseFormato13AC;
import gob.osinergmin.fise.domain.FiseFormato13AD;
import gob.osinergmin.fise.domain.FiseFormato14AC;
import gob.osinergmin.fise.domain.FiseFormato14BC;
import gob.osinergmin.fise.domain.FiseFormato14CC;
import gob.osinergmin.fise.domain.FisePeriodoEnvio;
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
		
		HSSFCell a6 = segundaFila.createCell(6);
		a6.setCellValue(new HSSFRichTextString("Grupo Inf."));
		a6.setCellStyle(headerCellStyle);
		
		HSSFCell a7 = segundaFila.createCell(7);
		a7.setCellValue(new HSSFRichTextString("Estado"));
		a7.setCellStyle(headerCellStyle);

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
				HSSFCell ax6 = fila.createCell(6);
				ax6.setCellValue(new HSSFRichTextString(fiseFormato12A.getDescGrupoInformacion()));
				ax6.setCellStyle(dateCellStyle);
				HSSFCell ax7 = fila.createCell(7);
				ax7.setCellValue(new HSSFRichTextString(fiseFormato12A.getDescEstado()));
				ax7.setCellStyle(dateCellStyle);
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
		_xlsSheet.autoSizeColumn((short) 6);
		_xlsSheet.autoSizeColumn((short) 7);
		
		return _xlsSheet;

	}
	
	/***cambios euclides */
	public HSSFSheet construirExcelFormato14C(List<?> listaFormato14C) {

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
		a4.setCellValue(new HSSFRichTextString("Año Ini. Vig."));
		a4.setCellStyle(headerCellStyle);
		
		HSSFCell a5 = segundaFila.createCell(5);
		a5.setCellValue(new HSSFRichTextString("Año Fin Vig."));
		a5.setCellStyle(headerCellStyle);
		
		HSSFCell a6 = segundaFila.createCell(6);
		a6.setCellValue(new HSSFRichTextString("Grupo Inf."));
		a6.setCellStyle(headerCellStyle);
		
		HSSFCell a7 = segundaFila.createCell(7);
		a7.setCellValue(new HSSFRichTextString("Estado"));
		a7.setCellStyle(headerCellStyle);

		if( listaFormato14C!=null && listaFormato14C.size()>0 ){
			for( int i=0;i<listaFormato14C.size();i++ ){
				
				FiseFormato14CC fiseFormato14C = (FiseFormato14CC) listaFormato14C.get(i);
				
				HSSFRow fila = _xlsSheet.createRow(i+1);
				HSSFCell ax1 = fila.createCell(1);
				ax1.setCellValue(new HSSFRichTextString(fiseFormato14C.getDescEmpresa()));
				ax1.setCellStyle(dateCellStyle);
				HSSFCell ax2 = fila.createCell(2);
				ax2.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato14C.getId().getAnoPresentacion())));
				ax2.setCellStyle(dateCellStyle);
				HSSFCell ax3 = fila.createCell(3);
				ax3.setCellValue(new HSSFRichTextString(fiseFormato14C.getDescMesPresentacion()));
				ax3.setCellStyle(dateCellStyle);
				HSSFCell ax4 = fila.createCell(4);
				ax4.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato14C.getId().getAnoInicioVigencia())));
				ax4.setCellStyle(dateCellStyle);
				HSSFCell ax5 = fila.createCell(5);
				ax5.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato14C.getId().getAnoFinVigencia())));
				ax5.setCellStyle(dateCellStyle);
				HSSFCell ax6 = fila.createCell(6);
				ax6.setCellValue(new HSSFRichTextString(fiseFormato14C.getFiseGrupoInformacion().getDescripcion()));
				ax6.setCellStyle(dateCellStyle);
				HSSFCell ax7 = fila.createCell(7);
				ax7.setCellValue(new HSSFRichTextString(fiseFormato14C.getDescEstado()));
				ax7.setCellStyle(dateCellStyle);
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
		_xlsSheet.autoSizeColumn((short) 6);
		_xlsSheet.autoSizeColumn((short) 7);
		
		return _xlsSheet;

	}
	
	public HSSFSheet construirExcelPeriodoEnvio(List<?> listaPeriodoEnvio) {

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
		a1.setCellValue(new HSSFRichTextString("Secuencia"));
		a1.setCellStyle(headerCellStyle);
		
		HSSFCell a2 = segundaFila.createCell(2);
		a2.setCellValue(new HSSFRichTextString("Empresa"));
		a2.setCellStyle(headerCellStyle);
		
		HSSFCell a3 = segundaFila.createCell(3);
		a3.setCellValue(new HSSFRichTextString("Año Pres."));
		a3.setCellStyle(headerCellStyle);
		
		HSSFCell a4 = segundaFila.createCell(4);
		a4.setCellValue(new HSSFRichTextString("Mes. Pres."));
		a4.setCellStyle(headerCellStyle);
	
		HSSFCell a5 = segundaFila.createCell(5);
		a5.setCellValue(new HSSFRichTextString("Formato."));
		a5.setCellStyle(headerCellStyle);
		
		HSSFCell a6 = segundaFila.createCell(6);
		a6.setCellValue(new HSSFRichTextString("Etapa."));
		a6.setCellStyle(headerCellStyle);
		
		HSSFCell a7 = segundaFila.createCell(7);
		a7.setCellValue(new HSSFRichTextString("Desde."));
		a7.setCellStyle(headerCellStyle);
		
		HSSFCell a8 = segundaFila.createCell(8);
		a8.setCellValue(new HSSFRichTextString("Hasta."));
		a8.setCellStyle(headerCellStyle);
		
		HSSFCell a9 = segundaFila.createCell(9);
		a9.setCellValue(new HSSFRichTextString("Estado."));
		a9.setCellStyle(headerCellStyle);
		
		HSSFCell a10 = segundaFila.createCell(10);
		a10.setCellValue(new HSSFRichTextString("Dias Notif antes de Cierre."));
		a10.setCellStyle(headerCellStyle);
		
		HSSFCell a11 = segundaFila.createCell(11);
		a11.setCellValue(new HSSFRichTextString("Año Ini. Vig."));
		a11.setCellStyle(headerCellStyle);
		
		HSSFCell a12 = segundaFila.createCell(12);
		a12.setCellValue(new HSSFRichTextString("Año Fin Vig."));
		a12.setCellStyle(headerCellStyle);
		
		HSSFCell a13 = segundaFila.createCell(13);
		a13.setCellValue(new HSSFRichTextString("Envio Obs."));
		a13.setCellStyle(headerCellStyle);
		
		HSSFCell a14 = segundaFila.createCell(14);
		a14.setCellValue(new HSSFRichTextString("Mostrar Año y Mes"));
		a14.setCellStyle(headerCellStyle);
		
		HSSFCell a15 = segundaFila.createCell(15);
		a15.setCellValue(new HSSFRichTextString("Habilitado Costo"));
		a15.setCellStyle(headerCellStyle);

		if( listaPeriodoEnvio!=null && listaPeriodoEnvio.size()>0 ){
			for( int i=0;i<listaPeriodoEnvio.size();i++ ){
				
				FisePeriodoEnvio periodo = (FisePeriodoEnvio) listaPeriodoEnvio.get(i);
				System.out.println("Entrando a llenar filas y celdas en el exel periodo envio"); 
				HSSFRow fila = _xlsSheet.createRow(i+1);
				
				HSSFCell ax1 = fila.createCell(1);
				ax1.setCellValue(new HSSFRichTextString(""+periodo.getSecuencia()));
				ax1.setCellStyle(dateCellStyle);
				HSSFCell ax2 = fila.createCell(2);
				ax2.setCellValue(new HSSFRichTextString(""+periodo.getDescEmpresa()));
				ax2.setCellStyle(dateCellStyle);
				HSSFCell ax3 = fila.createCell(3);
				ax3.setCellValue(new HSSFRichTextString(""+periodo.getAnoPresentacion()));
				ax3.setCellStyle(dateCellStyle);
				HSSFCell ax4 = fila.createCell(4);
				ax4.setCellValue(new HSSFRichTextString(periodo.getDescMesPresentacion()));
				ax4.setCellStyle(dateCellStyle);
				HSSFCell ax5 = fila.createCell(5);
				ax5.setCellValue(new HSSFRichTextString(periodo.getFormato()));
				ax5.setCellStyle(dateCellStyle);
				HSSFCell ax6 = fila.createCell(6);
				ax6.setCellValue(new HSSFRichTextString(periodo.getEtapa()));
				ax6.setCellStyle(dateCellStyle);
				HSSFCell ax7 = fila.createCell(7);
				ax7.setCellValue(new HSSFRichTextString(""+periodo.getDesde()));
				ax7.setCellStyle(dateCellStyle);
				
				HSSFCell ax8 = fila.createCell(8);
				ax8.setCellValue(new HSSFRichTextString(""+periodo.getHasta()));
				ax8.setCellStyle(dateCellStyle);
				HSSFCell ax9 = fila.createCell(9);
				ax9.setCellValue(new HSSFRichTextString(periodo.getEstado()));
				ax9.setCellStyle(dateCellStyle);
				HSSFCell ax10 = fila.createCell(10);
				ax10.setCellValue(new HSSFRichTextString(""+periodo.getDiasNotificacionAntesCierre()));
				ax10.setCellStyle(dateCellStyle);
				HSSFCell ax11 = fila.createCell(11);
				ax11.setCellValue(new HSSFRichTextString(""+periodo.getAnoInicioVigencia()));
				ax11.setCellStyle(dateCellStyle);
				HSSFCell ax12 = fila.createCell(12);
				ax12.setCellValue(new HSSFRichTextString(""+periodo.getAnoFinVigencia()));
				ax12.setCellStyle(dateCellStyle);
				HSSFCell ax13 = fila.createCell(13);
				ax13.setCellValue(new HSSFRichTextString(periodo.getFlagEnvioConObservaciones()));
				ax13.setCellStyle(dateCellStyle);
				HSSFCell ax14 = fila.createCell(14);
				ax14.setCellValue(new HSSFRichTextString(periodo.getFlagMostrarAnoMesEjec()));
				ax14.setCellStyle(dateCellStyle);
				HSSFCell ax15 = fila.createCell(15);
				ax15.setCellValue(new HSSFRichTextString(periodo.getFlagHabilitaCostosDIF14c()));
				ax15.setCellStyle(dateCellStyle);
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
		_xlsSheet.autoSizeColumn((short) 6);
		_xlsSheet.autoSizeColumn((short) 7);
		_xlsSheet.autoSizeColumn((short) 8);
		_xlsSheet.autoSizeColumn((short) 9);
		_xlsSheet.autoSizeColumn((short) 10);
		_xlsSheet.autoSizeColumn((short) 11);
		_xlsSheet.autoSizeColumn((short) 12);
		_xlsSheet.autoSizeColumn((short) 13);
		_xlsSheet.autoSizeColumn((short) 14);
		_xlsSheet.autoSizeColumn((short) 15);
		
		return _xlsSheet;

	}
	
	/**fin de cambios euclides*/
	//OBSERVACIONES
	public HSSFSheet construirExcelObservaciones(List<?> listaObservacion) {

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
		
		HSSFCellStyle dateCellStyleCentrado = _wb.createCellStyle();
		dateCellStyleCentrado.cloneStyleFrom(dateCellStyleCentrado);
		dateCellStyleCentrado.setDataFormat((short) 15);
		dateCellStyleCentrado.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		//HSSFRow row = _xlsSheet.createRow(iRow);

		HSSFRow segundaFila = _xlsSheet.createRow(0);
		segundaFila.setHeightInPoints((2*_xlsSheet.getDefaultRowHeightInPoints()));

		HSSFCell a1 = segundaFila.createCell(1);
		a1.setCellValue(new HSSFRichTextString("Grupo Zona"));
		a1.setCellStyle(headerCellStyle);
		
		HSSFCell a2 = segundaFila.createCell(2);
		a2.setCellValue(new HSSFRichTextString("Código"));
		a2.setCellStyle(headerCellStyle);
		
		HSSFCell a3 = segundaFila.createCell(3);
		a3.setCellValue(new HSSFRichTextString("Descripción"));
		a3.setCellStyle(headerCellStyle);

		if( listaObservacion!=null && listaObservacion.size()>0 ){
			for( int i=0;i<listaObservacion.size();i++ ){
				
				MensajeErrorBean observacion = (MensajeErrorBean) listaObservacion.get(i);
				
				HSSFRow fila = _xlsSheet.createRow(i+1);
				HSSFCell ax1 = fila.createCell(1);
				ax1.setCellValue(new HSSFRichTextString(observacion.getDescZonaBenef()));
				ax1.setCellStyle(dateCellStyle);
				HSSFCell ax2 = fila.createCell(2);
				ax2.setCellValue(new HSSFRichTextString(String.valueOf(observacion.getCodigo())));
				ax2.setCellStyle(dateCellStyle);
				HSSFCell ax3 = fila.createCell(3);
				ax3.setCellValue(new HSSFRichTextString(observacion.getDescripcion()));
				ax3.setCellStyle(dateCellStyle);
			}
		}

		/**
		 * longitud automatica de columnas
		 */
		_xlsSheet.autoSizeColumn((short) 1);
		_xlsSheet.autoSizeColumn((short) 2);
		_xlsSheet.autoSizeColumn((short) 3);
		
		return _xlsSheet;

	}
	
	public HSSFSheet construirExcelFormato13A(List<?> listaFormato13A) {

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
		a4.setCellValue(new HSSFRichTextString("Etapa."));
		a4.setCellStyle(headerCellStyle);
		
		HSSFCell a5 = segundaFila.createCell(5);
		a5.setCellValue(new HSSFRichTextString("Grupo Información"));
		a5.setCellStyle(headerCellStyle);
		
		HSSFCell a6 = segundaFila.createCell(6);
		a6.setCellValue(new HSSFRichTextString("Estado"));
		a6.setCellStyle(headerCellStyle);
		
		if( listaFormato13A!=null && listaFormato13A.size()>0 ){
			for( int i=0;i<listaFormato13A.size();i++ ){
				
				FiseFormato13AC fiseFormato13A = (FiseFormato13AC) listaFormato13A.get(i);
				
				HSSFRow fila = _xlsSheet.createRow(i+1);
				HSSFCell ax1 = fila.createCell(1);
				ax1.setCellValue(new HSSFRichTextString(fiseFormato13A.getDescEmpresa()));
				ax1.setCellStyle(dateCellStyle);
				HSSFCell ax2 = fila.createCell(2);
				ax2.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato13A.getId().getAnoPresentacion())));
				ax2.setCellStyle(dateCellStyle);
				HSSFCell ax3 = fila.createCell(3);
				ax3.setCellValue(new HSSFRichTextString(fiseFormato13A.getDescMesPresentacion()));
				ax3.setCellStyle(dateCellStyle);
				HSSFCell ax4 = fila.createCell(4);
				ax4.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato13A.getId().getEtapa())));
				ax4.setCellStyle(dateCellStyle);
				HSSFCell ax5 = fila.createCell(5);
				ax5.setCellValue(new HSSFRichTextString(fiseFormato13A.getDescGrupoInformacion()));
				ax5.setCellStyle(dateCellStyle);
				HSSFCell ax6 = fila.createCell(6);
				ax6.setCellValue(new HSSFRichTextString(fiseFormato13A.getDescEstado()));
				ax6.setCellStyle(dateCellStyle);
				
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
		_xlsSheet.autoSizeColumn((short) 6);
		
		
		return _xlsSheet;

	}
	
	
	public HSSFSheet construirExcelFormato13AD(List<?> listaFormato13AD) {

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
		a1.setCellValue(new HSSFRichTextString("Año/Mes de Alta"));
		a1.setCellStyle(headerCellStyle);
		
		HSSFCell a2 = segundaFila.createCell(2);
		a2.setCellValue(new HSSFRichTextString("Codigo Ubigeo"));
		a2.setCellStyle(headerCellStyle);
		
		HSSFCell a3 = segundaFila.createCell(3);
		a3.setCellValue(new HSSFRichTextString("Localidad"));
		a3.setCellStyle(headerCellStyle);
	
		HSSFCell a4 = segundaFila.createCell(4);
		a4.setCellValue(new HSSFRichTextString("ST-1"));
		a4.setCellStyle(headerCellStyle);
		
		HSSFCell a5 = segundaFila.createCell(5);
		a5.setCellValue(new HSSFRichTextString("ST-2"));
		a5.setCellStyle(headerCellStyle);
		
		HSSFCell a6 = segundaFila.createCell(6);
		a6.setCellValue(new HSSFRichTextString("ST-3"));
		a6.setCellStyle(headerCellStyle);
		
		HSSFCell a7 = segundaFila.createCell(7);
		a7.setCellValue(new HSSFRichTextString("ST-4"));
		a7.setCellStyle(headerCellStyle);
		
		HSSFCell a8 = segundaFila.createCell(8);
		a8.setCellValue(new HSSFRichTextString("ST-5"));
		a8.setCellStyle(headerCellStyle);
		
		HSSFCell a9 = segundaFila.createCell(9);
		a9.setCellValue(new HSSFRichTextString("ST-6"));
		a9.setCellStyle(headerCellStyle);
		
		HSSFCell a10 = segundaFila.createCell(10);
		a10.setCellValue(new HSSFRichTextString("ST-SER"));
		a10.setCellStyle(headerCellStyle);
		
		HSSFCell a11 = segundaFila.createCell(11);
		a11.setCellValue(new HSSFRichTextString("ST-ESPECIAL"));
		a11.setCellStyle(headerCellStyle);
		
		HSSFCell a12 = segundaFila.createCell(12);
		a12.setCellValue(new HSSFRichTextString("TOTAL"));
		a12.setCellStyle(headerCellStyle);
		
		HSSFCell a13 = segundaFila.createCell(13);
		a13.setCellValue(new HSSFRichTextString("ZONA DE BENEFICIARIOS"));
		a13.setCellStyle(headerCellStyle);
		
		HSSFCell a14 = segundaFila.createCell(14);
		a14.setCellValue(new HSSFRichTextString("SEDE QUE ATIENDE"));
		a14.setCellStyle(headerCellStyle);
		
		
		if( listaFormato13AD!=null && listaFormato13AD.size()>0 ){
			
			for( int i=0;i<listaFormato13AD.size();i++ ){
				
				Formato13ADReportBean fiseFormato13AD = (Formato13ADReportBean) listaFormato13AD.get(i);
				
				HSSFRow fila = _xlsSheet.createRow(i+1);
				HSSFCell ax1 = fila.createCell(1);
				ax1.setCellValue(new HSSFRichTextString(fiseFormato13AD.getAnioAlta() +"-"+fiseFormato13AD.getMesAlta()));
				ax1.setCellStyle(dateCellStyle);
				HSSFCell ax2 = fila.createCell(2);
				ax2.setCellValue(new HSSFRichTextString(fiseFormato13AD.getCodUbigeo()));
				ax2.setCellStyle(dateCellStyle);
				HSSFCell ax3 = fila.createCell(3);
				ax3.setCellValue(new HSSFRichTextString(fiseFormato13AD.getDescLocalidad()));
				ax3.setCellStyle(dateCellStyle);
				
				HSSFCell ax4 = fila.createCell(4);
				ax4.setCellValue(new HSSFRichTextString(fiseFormato13AD.getNroBenefPoteSecTipico1()!=null?String.valueOf(fiseFormato13AD.getNroBenefPoteSecTipico1()):"0"));
				ax4.setCellStyle(dateCellStyle);
				
				HSSFCell ax5 = fila.createCell(5);
				ax5.setCellValue(new HSSFRichTextString(fiseFormato13AD.getNroBenefPoteSecTipico2()!=null?String.valueOf(fiseFormato13AD.getNroBenefPoteSecTipico2()):"0"));
				ax5.setCellStyle(dateCellStyle);
				
				HSSFCell ax6 = fila.createCell(6);
				ax6.setCellValue(new HSSFRichTextString(fiseFormato13AD.getNroBenefPoteSecTipico3()!=null?String.valueOf(fiseFormato13AD.getNroBenefPoteSecTipico3()):"0"));
				ax6.setCellStyle(dateCellStyle);
				
				HSSFCell ax7 = fila.createCell(7);
				ax7.setCellValue(new HSSFRichTextString(fiseFormato13AD.getNroBenefPoteSecTipico4()!=null?String.valueOf(fiseFormato13AD.getNroBenefPoteSecTipico4()):"0"));
				ax7.setCellStyle(dateCellStyle);
				
				HSSFCell ax8 = fila.createCell(8);
				ax8.setCellValue(new HSSFRichTextString(fiseFormato13AD.getNroBenefPoteSecTipico5()!=null?String.valueOf(fiseFormato13AD.getNroBenefPoteSecTipico5()):"0"));
				ax8.setCellStyle(dateCellStyle);
				
				HSSFCell ax9 = fila.createCell(9);
				ax9.setCellValue(new HSSFRichTextString(fiseFormato13AD.getNroBenefPoteSecTipico6()!=null?String.valueOf(fiseFormato13AD.getNroBenefPoteSecTipico6()):"0"));
				ax9.setCellStyle(dateCellStyle);
				
				HSSFCell ax10 = fila.createCell(10);
				ax10.setCellValue(new HSSFRichTextString(fiseFormato13AD.getNroBenefPoteSecTipico7()!=null?String.valueOf(fiseFormato13AD.getNroBenefPoteSecTipico7()):"0"));
				ax10.setCellStyle(dateCellStyle);
				
				HSSFCell ax11 = fila.createCell(11);
				ax11.setCellValue(new HSSFRichTextString(fiseFormato13AD.getNroBenefPoteSecTipico8()!=null?String.valueOf(fiseFormato13AD.getNroBenefPoteSecTipico8()):"0"));
				ax11.setCellStyle(dateCellStyle);
				
				
				double total=fiseFormato13AD.getNroBenefPoteSecTipico1()+
						fiseFormato13AD.getNroBenefPoteSecTipico2()+
						fiseFormato13AD.getNroBenefPoteSecTipico3()+
						fiseFormato13AD.getNroBenefPoteSecTipico4()+
						fiseFormato13AD.getNroBenefPoteSecTipico5()+
						fiseFormato13AD.getNroBenefPoteSecTipico6()+
						fiseFormato13AD.getNroBenefPoteSecTipico7()+
						fiseFormato13AD.getNroBenefPoteSecTipico8();
				
				HSSFCell ax12 = fila.createCell(12);
				ax12.setCellValue(new HSSFRichTextString(String.valueOf(total)));
				ax12.setCellStyle(dateCellStyle);
				
				HSSFCell ax13 = fila.createCell(13);
				ax13.setCellValue(new HSSFRichTextString(fiseFormato13AD.getDescZonaBenef()));
				ax13.setCellStyle(dateCellStyle);
				
				HSSFCell ax14 = fila.createCell(14);
				ax14.setCellValue(new HSSFRichTextString(fiseFormato13AD.getNombreSedeAtiende()));
				ax14.setCellStyle(dateCellStyle);
				
				
				
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
		_xlsSheet.autoSizeColumn((short) 6);
		
		_xlsSheet.autoSizeColumn((short) 7);
		_xlsSheet.autoSizeColumn((short) 8);
		_xlsSheet.autoSizeColumn((short) 9);
		_xlsSheet.autoSizeColumn((short) 10);
		_xlsSheet.autoSizeColumn((short) 11);
		_xlsSheet.autoSizeColumn((short) 12);
		
		_xlsSheet.autoSizeColumn((short) 13);
		_xlsSheet.autoSizeColumn((short) 14);
		
		
		
		return _xlsSheet;

	}
	
	
	//FORMATO 14A
	public HSSFSheet construirExcelFormato14A(List<?> listaFormato14A) {

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
		a4.setCellValue(new HSSFRichTextString("Año Ini. Vig."));
		a4.setCellStyle(headerCellStyle);
		
		HSSFCell a5 = segundaFila.createCell(5);
		a5.setCellValue(new HSSFRichTextString("Año Fin Vig."));
		a5.setCellStyle(headerCellStyle);
		
		HSSFCell a6 = segundaFila.createCell(6);
		a6.setCellValue(new HSSFRichTextString("Grupo Inf."));
		a6.setCellStyle(headerCellStyle);
		
		HSSFCell a7 = segundaFila.createCell(7);
		a7.setCellValue(new HSSFRichTextString("Estado"));
		a7.setCellStyle(headerCellStyle);

		if( listaFormato14A!=null && listaFormato14A.size()>0 ){
			for( int i=0;i<listaFormato14A.size();i++ ){
				
				FiseFormato14AC fiseFormato14A = (FiseFormato14AC) listaFormato14A.get(i);
				
				HSSFRow fila = _xlsSheet.createRow(i+1);
				HSSFCell ax1 = fila.createCell(1);
				ax1.setCellValue(new HSSFRichTextString(fiseFormato14A.getDescEmpresa()));
				ax1.setCellStyle(dateCellStyle);
				HSSFCell ax2 = fila.createCell(2);
				ax2.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato14A.getId().getAnoPresentacion())));
				ax2.setCellStyle(dateCellStyle);
				HSSFCell ax3 = fila.createCell(3);
				ax3.setCellValue(new HSSFRichTextString(fiseFormato14A.getDescMesPresentacion()));
				ax3.setCellStyle(dateCellStyle);
				HSSFCell ax4 = fila.createCell(4);
				ax4.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato14A.getId().getAnoInicioVigencia())));
				ax4.setCellStyle(dateCellStyle);
				HSSFCell ax5 = fila.createCell(5);
				ax5.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato14A.getId().getAnoFinVigencia())));
				ax5.setCellStyle(dateCellStyle);
				HSSFCell ax6 = fila.createCell(6);
				ax6.setCellValue(new HSSFRichTextString(fiseFormato14A.getDescGrupoInformacion()));
				ax6.setCellStyle(dateCellStyle);
				HSSFCell ax7 = fila.createCell(7);
				ax7.setCellValue(new HSSFRichTextString(fiseFormato14A.getDescEstado()));
				ax7.setCellStyle(dateCellStyle);
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
		_xlsSheet.autoSizeColumn((short) 6);
		_xlsSheet.autoSizeColumn((short) 7);
		
		return _xlsSheet;

	}
	
	//FORMATO 14A
		public HSSFSheet construirExcelFormato14B(List<?> listaFormato14B) {

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
			a4.setCellValue(new HSSFRichTextString("Año Ini. Vig."));
			a4.setCellStyle(headerCellStyle);
			
			HSSFCell a5 = segundaFila.createCell(5);
			a5.setCellValue(new HSSFRichTextString("Año Fin Vig."));
			a5.setCellStyle(headerCellStyle);
			
			HSSFCell a6 = segundaFila.createCell(6);
			a6.setCellValue(new HSSFRichTextString("Grupo Inf."));
			a6.setCellStyle(headerCellStyle);
			
			HSSFCell a7 = segundaFila.createCell(7);
			a7.setCellValue(new HSSFRichTextString("Estado"));
			a7.setCellStyle(headerCellStyle);

			if( listaFormato14B!=null && listaFormato14B.size()>0 ){
				for( int i=0;i<listaFormato14B.size();i++ ){
					
					FiseFormato14BC fiseFormato14B = (FiseFormato14BC) listaFormato14B.get(i);
					
					HSSFRow fila = _xlsSheet.createRow(i+1);
					HSSFCell ax1 = fila.createCell(1);
					ax1.setCellValue(new HSSFRichTextString(fiseFormato14B.getDescEmpresa()));
					ax1.setCellStyle(dateCellStyle);
					HSSFCell ax2 = fila.createCell(2);
					ax2.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato14B.getId().getAnoPresentacion())));
					ax2.setCellStyle(dateCellStyle);
					HSSFCell ax3 = fila.createCell(3);
					ax3.setCellValue(new HSSFRichTextString(fiseFormato14B.getDescMesPresentacion()));
					ax3.setCellStyle(dateCellStyle);
					HSSFCell ax4 = fila.createCell(4);
					ax4.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato14B.getId().getAnoInicioVigencia())));
					ax4.setCellStyle(dateCellStyle);
					HSSFCell ax5 = fila.createCell(5);
					ax5.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato14B.getId().getAnoFinVigencia())));
					ax5.setCellStyle(dateCellStyle);
					HSSFCell ax6 = fila.createCell(6);
					ax6.setCellValue(new HSSFRichTextString(fiseFormato14B.getDescGrupoInformacion()));
					ax6.setCellStyle(dateCellStyle);
					HSSFCell ax7 = fila.createCell(7);
					ax7.setCellValue(new HSSFRichTextString(fiseFormato14B.getDescEstado()));
					ax7.setCellStyle(dateCellStyle);
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
			_xlsSheet.autoSizeColumn((short) 6);
			_xlsSheet.autoSizeColumn((short) 7);
			
			return _xlsSheet;

		}
	//
	
	public HSSFSheet FillExcelSheet(XlsWorksheetConfig xlsWorksheetConfig, HSSFSheet xlsSheet, HSSFWorkbook wb) {
		_xlsSheet = xlsSheet;
		_wb = wb;
		//_xlsWorksheetConfig = xlsWorksheetConfig;

		for (XlsTableConfig xlsTableConfig : xlsWorksheetConfig.getTables()) {
			if( FiseConstants.TIPO_FORMATO_12A.equals(xlsTableConfig.getTipoFormato()) ){
				construirExcelFormato12A(xlsTableConfig.getLista());
			}else if( FiseConstants.TIPO_FORMATO_12B.equals(xlsTableConfig.getTipoFormato()) ){
				//construirExcelFormato12B(xlsTableConfig.getLista());
			}else if( FiseConstants.TIPO_FORMATO_12C.equals(xlsTableConfig.getTipoFormato()) ){
				//construirExcelFormato12C(xlsTableConfig.getLista());
			}else if( FiseConstants.TIPO_FORMATO_12D.equals(xlsTableConfig.getTipoFormato()) ){
				//construirExcelFormato12D(xlsTableConfig.getLista());
			}else if( FiseConstants.TIPO_FORMATO_13A.equals(xlsTableConfig.getTipoFormato()) ){
				construirExcelFormato13A(xlsTableConfig.getLista());
			}else if( FiseConstants.TIPO_FORMATO_13AD.equals(xlsTableConfig.getTipoFormato()) ){
				construirExcelFormato13AD(xlsTableConfig.getLista());
			}else if( FiseConstants.TIPO_FORMATO_14A.equals(xlsTableConfig.getTipoFormato()) ){
				construirExcelFormato14A(xlsTableConfig.getLista());
			}else if( FiseConstants.TIPO_FORMATO_14B.equals(xlsTableConfig.getTipoFormato()) ){
				construirExcelFormato14B(xlsTableConfig.getLista());
			}else if( FiseConstants.TIPO_FORMATO_14C.equals(xlsTableConfig.getTipoFormato()) ){
				construirExcelFormato14C(xlsTableConfig.getLista());				
			}else if( FiseConstants.TIPO_PERIODO_ENVIO.equals(xlsTableConfig.getTipoFormato()) ){
				construirExcelPeriodoEnvio(xlsTableConfig.getLista());				
			}
			//poner los otros formato
			//ponemos la lista de observaciones
			else if( FiseConstants.TIPO_FORMATO_VAL.equals(xlsTableConfig.getTipoFormato()) ){
				construirExcelObservaciones(xlsTableConfig.getLista());
			}
		}
		return _xlsSheet;
	}

}
