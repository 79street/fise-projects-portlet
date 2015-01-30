package gob.osinergmin.fise.gart.xls;

import gob.osinergmin.fise.bean.FiseCargoFijoBean;
import gob.osinergmin.fise.bean.Formato13ADReportBean;
import gob.osinergmin.fise.bean.MensajeErrorBean;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.FiseFormato12AC;
import gob.osinergmin.fise.domain.FiseFormato12CC;
import gob.osinergmin.fise.domain.FiseFormato12CD;
import gob.osinergmin.fise.domain.FiseFormato12DC;
import gob.osinergmin.fise.domain.FiseFormato12DD;
import gob.osinergmin.fise.domain.FiseFormato13AC;
import gob.osinergmin.fise.domain.FiseFormato14AC;
import gob.osinergmin.fise.domain.FiseFormato14BC;
import gob.osinergmin.fise.domain.FiseFormato14CC;
import gob.osinergmin.fise.domain.FiseObservacion;
import gob.osinergmin.fise.domain.FisePeriodoEnvio;
import gob.osinergmin.fise.gart.command.Formato12BGartCommand;
import gob.osinergmin.fise.util.FechaUtil;
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
	
	//FORMATO 12C
	public HSSFSheet construirExcelFormato12C(List<?> listaFormato12C) {

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
		a4.setCellValue(new HSSFRichTextString("Etapa"));
		a4.setCellStyle(headerCellStyle);
		
		HSSFCell a6 = segundaFila.createCell(5);
		a6.setCellValue(new HSSFRichTextString("Grupo de información"));
		a6.setCellStyle(headerCellStyle);
		
		HSSFCell a7 = segundaFila.createCell(6);
		a7.setCellValue(new HSSFRichTextString("Estado"));
		a7.setCellStyle(headerCellStyle);

		if( listaFormato12C!=null && listaFormato12C.size()>0 ){
			for( int i=0;i<listaFormato12C.size();i++ ){
				
				FiseFormato12CC fiseFormato12C = (FiseFormato12CC) listaFormato12C.get(i);
				
				HSSFRow fila = _xlsSheet.createRow(i+1);
				HSSFCell ax1 = fila.createCell(1);
				ax1.setCellValue(new HSSFRichTextString(fiseFormato12C.getDescEmpresa()));
				ax1.setCellStyle(dateCellStyle);
				HSSFCell ax2 = fila.createCell(2);
				ax2.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato12C.getId().getAnoPresentacion())));
				ax2.setCellStyle(dateCellStyle);
				HSSFCell ax3 = fila.createCell(3);
				ax3.setCellValue(new HSSFRichTextString(fiseFormato12C.getDescMesPresentacion()));
				ax3.setCellStyle(dateCellStyle);
				HSSFCell ax4 = fila.createCell(4);
				ax4.setCellValue(new HSSFRichTextString(fiseFormato12C.getId().getEtapa()));
				ax4.setCellStyle(dateCellStyle);
				HSSFCell ax5 = fila.createCell(5);
				ax5.setCellValue(new HSSFRichTextString(fiseFormato12C.getDescGrupoInformacion()));
				ax5.setCellStyle(dateCellStyle);
				HSSFCell ax6 = fila.createCell(6);
				ax6.setCellValue(new HSSFRichTextString(fiseFormato12C.getDescEstado()));
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
	
	public HSSFSheet construirExcelFormato12CDImplementacion(List<?> listaFormato12CDImplementacion) {

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
		a1.setCellValue(new HSSFRichTextString("Año Ejec."));
		a1.setCellStyle(headerCellStyle);
		
		HSSFCell a2 = segundaFila.createCell(2);
		a2.setCellValue(new HSSFRichTextString("Mes Ejec."));
		a2.setCellStyle(headerCellStyle);
		
		HSSFCell a3 = segundaFila.createCell(3);
		a3.setCellValue(new HSSFRichTextString("Item"));
		a3.setCellStyle(headerCellStyle);
	
		HSSFCell a4 = segundaFila.createCell(4);
		a4.setCellValue(new HSSFRichTextString("Cod. Ubigeo Origen"));
		a4.setCellStyle(headerCellStyle);
		
		HSSFCell a5 = segundaFila.createCell(5);
		a5.setCellValue(new HSSFRichTextString("Localidad Origen"));
		a5.setCellStyle(headerCellStyle);
		
		HSSFCell a6 = segundaFila.createCell(6);
		a6.setCellValue(new HSSFRichTextString("Cod. Ubigeo Destino"));
		a6.setCellStyle(headerCellStyle);
		
		HSSFCell a7 = segundaFila.createCell(7);
		a7.setCellValue(new HSSFRichTextString("Localidad Destino"));
		a7.setCellStyle(headerCellStyle);
		
		HSSFCell a8 = segundaFila.createCell(8);
		a8.setCellValue(new HSSFRichTextString("Zona Benef."));
		a8.setCellStyle(headerCellStyle);
		
		HSSFCell a9 = segundaFila.createCell(9);
		a9.setCellValue(new HSSFRichTextString("Cta. Contable"));
		a9.setCellStyle(headerCellStyle);
		
		HSSFCell a10 = segundaFila.createCell(10);
		a10.setCellValue(new HSSFRichTextString("Actividad"));
		a10.setCellStyle(headerCellStyle);
		
		HSSFCell a11 = segundaFila.createCell(11);
		a11.setCellValue(new HSSFRichTextString("Tipo Doc."));
		a11.setCellStyle(headerCellStyle);
		
		HSSFCell a12 = segundaFila.createCell(12);
		a12.setCellValue(new HSSFRichTextString("RUC"));
		a12.setCellStyle(headerCellStyle);
		
		HSSFCell a13 = segundaFila.createCell(13);
		a13.setCellValue(new HSSFRichTextString("Serie Doc."));
		a13.setCellStyle(headerCellStyle);
		
		HSSFCell a14 = segundaFila.createCell(14);
		a14.setCellValue(new HSSFRichTextString("Nro. Doc."));
		a14.setCellStyle(headerCellStyle);
		
		HSSFCell a15 = segundaFila.createCell(15);
		a15.setCellValue(new HSSFRichTextString("Días"));
		a15.setCellStyle(headerCellStyle);
		
		HSSFCell a16 = segundaFila.createCell(16);
		a16.setCellValue(new HSSFRichTextString("Alimentación"));
		a16.setCellStyle(headerCellStyle);
		
		HSSFCell a17 = segundaFila.createCell(17);
		a17.setCellValue(new HSSFRichTextString("Alojamiento"));
		a17.setCellStyle(headerCellStyle);
		
		HSSFCell a18 = segundaFila.createCell(18);
		a18.setCellValue(new HSSFRichTextString("Movilidad"));
		a18.setCellStyle(headerCellStyle);
		
		int m=0;

		if( listaFormato12CDImplementacion!=null && listaFormato12CDImplementacion.size()>0 ){
			for( int i=0;i<listaFormato12CDImplementacion.size();i++ ){
				
				FiseFormato12CD fiseFormato12CD = (FiseFormato12CD) listaFormato12CDImplementacion.get(i);
				
				if( FiseConstants.ETAPA_EJECUCION_IMPLEMENTACION_COD == fiseFormato12CD.getId().getEtapaEjecucion() ){
					
					HSSFRow fila = _xlsSheet.createRow(m+1);
					HSSFCell ax1 = fila.createCell(1);
					ax1.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato12CD.getId().getAnoEjecucionGasto())));
					ax1.setCellStyle(dateCellStyle);
					HSSFCell ax2 = fila.createCell(2);
					ax2.setCellValue(new HSSFRichTextString(fiseFormato12CD.getDescMesEjecucion()));
					ax2.setCellStyle(dateCellStyle);
					HSSFCell ax3 = fila.createCell(3);
					ax3.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato12CD.getId().getNumeroItemEtapa())));
					ax3.setCellStyle(dateCellStyle);
					HSSFCell ax4 = fila.createCell(4);
					ax4.setCellValue(new HSSFRichTextString(fiseFormato12CD.getCodUbigeoOrigen()));
					ax4.setCellStyle(dateCellStyle);
					HSSFCell ax5 = fila.createCell(5);
					ax5.setCellValue(new HSSFRichTextString(fiseFormato12CD.getDescripcionLocalidadOrigen()));
					ax5.setCellStyle(dateCellStyle);
					HSSFCell ax6 = fila.createCell(6);
					ax6.setCellValue(new HSSFRichTextString(fiseFormato12CD.getCodUbigeoDestino()));
					ax6.setCellStyle(dateCellStyle);
					HSSFCell ax7 = fila.createCell(7);
					ax7.setCellValue(new HSSFRichTextString(fiseFormato12CD.getDescripcionLocalidadDestino()));
					ax7.setCellStyle(dateCellStyle);
					HSSFCell ax8 = fila.createCell(8);
					ax8.setCellValue(new HSSFRichTextString(fiseFormato12CD.getDescZonaBenef()));
					ax8.setCellStyle(dateCellStyle);
					HSSFCell ax9 = fila.createCell(9);
					ax9.setCellValue(new HSSFRichTextString(fiseFormato12CD.getCodigoCuentaContaEde()));
					ax9.setCellStyle(dateCellStyle);
					HSSFCell ax10 = fila.createCell(10);
					ax10.setCellValue(new HSSFRichTextString(fiseFormato12CD.getDescripcionActividad()));
					ax10.setCellStyle(dateCellStyle);
					HSSFCell ax11 = fila.createCell(11);
					ax11.setCellValue(new HSSFRichTextString(fiseFormato12CD.getIdTipDocRef()));
					ax11.setCellStyle(dateCellStyle);
					HSSFCell ax12 = fila.createCell(12);
					ax12.setCellValue(new HSSFRichTextString(fiseFormato12CD.getRucEmpresaEmiteDocRef()));
					ax12.setCellStyle(dateCellStyle);
					HSSFCell ax13 = fila.createCell(13);
					ax13.setCellValue(new HSSFRichTextString(fiseFormato12CD.getSerieDocumentoReferencia()));
					ax13.setCellStyle(dateCellStyle);
					HSSFCell ax14 = fila.createCell(14);
					ax14.setCellValue(new HSSFRichTextString(fiseFormato12CD.getNumeroDocumentoReferencia()));
					ax14.setCellStyle(dateCellStyle);
					HSSFCell ax15 = fila.createCell(15);
					ax15.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato12CD.getNumeroDias())));
					ax15.setCellStyle(dateCellStyle);
					HSSFCell ax16 = fila.createCell(16);
					ax16.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato12CD.getMontoAlimentacion())));
					ax16.setCellStyle(dateCellStyle);
					HSSFCell ax17 = fila.createCell(17);
					ax17.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato12CD.getMontoAlojamiento())));
					ax17.setCellStyle(dateCellStyle);
					HSSFCell ax18 = fila.createCell(18);
					ax18.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato12CD.getMontoMovilidad())));
					ax18.setCellStyle(dateCellStyle);
					
					m++;
				}
				
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
		_xlsSheet.autoSizeColumn((short) 16);
		_xlsSheet.autoSizeColumn((short) 17);
		_xlsSheet.autoSizeColumn((short) 18);
		
		return _xlsSheet;

	}
	
	public HSSFSheet construirExcelFormato12CDMensual(List<?> listaFormato12CDMensual) {

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
		a1.setCellValue(new HSSFRichTextString("Año Ejec."));
		a1.setCellStyle(headerCellStyle);
		
		HSSFCell a2 = segundaFila.createCell(2);
		a2.setCellValue(new HSSFRichTextString("Mes Ejec."));
		a2.setCellStyle(headerCellStyle);
		
		HSSFCell a3 = segundaFila.createCell(3);
		a3.setCellValue(new HSSFRichTextString("Item"));
		a3.setCellStyle(headerCellStyle);
	
		HSSFCell a4 = segundaFila.createCell(4);
		a4.setCellValue(new HSSFRichTextString("Cod. Ubigeo Origen"));
		a4.setCellStyle(headerCellStyle);
		
		HSSFCell a5 = segundaFila.createCell(5);
		a5.setCellValue(new HSSFRichTextString("Localidad Origen"));
		a5.setCellStyle(headerCellStyle);
		
		HSSFCell a6 = segundaFila.createCell(6);
		a6.setCellValue(new HSSFRichTextString("Cod. Ubigeo Destino"));
		a6.setCellStyle(headerCellStyle);
		
		HSSFCell a7 = segundaFila.createCell(7);
		a7.setCellValue(new HSSFRichTextString("Localidad Destino"));
		a7.setCellStyle(headerCellStyle);
		
		HSSFCell a8 = segundaFila.createCell(8);
		a8.setCellValue(new HSSFRichTextString("Zona Benef."));
		a8.setCellStyle(headerCellStyle);
		
		HSSFCell a9 = segundaFila.createCell(9);
		a9.setCellValue(new HSSFRichTextString("Cta. Contable"));
		a9.setCellStyle(headerCellStyle);
		
		HSSFCell a10 = segundaFila.createCell(10);
		a10.setCellValue(new HSSFRichTextString("Actividad"));
		a10.setCellStyle(headerCellStyle);
		
		HSSFCell a11 = segundaFila.createCell(11);
		a11.setCellValue(new HSSFRichTextString("Tipo Doc."));
		a11.setCellStyle(headerCellStyle);
		
		HSSFCell a12 = segundaFila.createCell(12);
		a12.setCellValue(new HSSFRichTextString("RUC"));
		a12.setCellStyle(headerCellStyle);
		
		HSSFCell a13 = segundaFila.createCell(13);
		a13.setCellValue(new HSSFRichTextString("Serie Doc."));
		a13.setCellStyle(headerCellStyle);
		
		HSSFCell a14 = segundaFila.createCell(14);
		a14.setCellValue(new HSSFRichTextString("Nro. Doc."));
		a14.setCellStyle(headerCellStyle);
		
		HSSFCell a15 = segundaFila.createCell(15);
		a15.setCellValue(new HSSFRichTextString("Días"));
		a15.setCellStyle(headerCellStyle);
		
		HSSFCell a16 = segundaFila.createCell(16);
		a16.setCellValue(new HSSFRichTextString("Alimentación"));
		a16.setCellStyle(headerCellStyle);
		
		HSSFCell a17 = segundaFila.createCell(17);
		a17.setCellValue(new HSSFRichTextString("Alojamiento"));
		a17.setCellStyle(headerCellStyle);
		
		HSSFCell a18 = segundaFila.createCell(18);
		a18.setCellValue(new HSSFRichTextString("Movilidad"));
		a18.setCellStyle(headerCellStyle);

		int m=0;
		
		if( listaFormato12CDMensual!=null && listaFormato12CDMensual.size()>0 ){
			for( int i=0;i<listaFormato12CDMensual.size();i++ ){
				
				FiseFormato12CD fiseFormato12CD = (FiseFormato12CD) listaFormato12CDMensual.get(i);
				
				if( FiseConstants.ETAPA_EJECUCION_OPERATIVA_COD == fiseFormato12CD.getId().getEtapaEjecucion() ){
					
					HSSFRow fila = _xlsSheet.createRow(m+1);
					HSSFCell ax1 = fila.createCell(1);
					ax1.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato12CD.getId().getAnoEjecucionGasto())));
					ax1.setCellStyle(dateCellStyle);
					HSSFCell ax2 = fila.createCell(2);
					ax2.setCellValue(new HSSFRichTextString(fiseFormato12CD.getDescMesEjecucion()));
					ax2.setCellStyle(dateCellStyle);
					HSSFCell ax3 = fila.createCell(3);
					ax3.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato12CD.getId().getNumeroItemEtapa())));
					ax3.setCellStyle(dateCellStyle);
					HSSFCell ax4 = fila.createCell(4);
					ax4.setCellValue(new HSSFRichTextString(fiseFormato12CD.getCodUbigeoOrigen()));
					ax4.setCellStyle(dateCellStyle);
					HSSFCell ax5 = fila.createCell(5);
					ax5.setCellValue(new HSSFRichTextString(fiseFormato12CD.getDescripcionLocalidadOrigen()));
					ax5.setCellStyle(dateCellStyle);
					HSSFCell ax6 = fila.createCell(6);
					ax6.setCellValue(new HSSFRichTextString(fiseFormato12CD.getCodUbigeoDestino()));
					ax6.setCellStyle(dateCellStyle);
					HSSFCell ax7 = fila.createCell(7);
					ax7.setCellValue(new HSSFRichTextString(fiseFormato12CD.getDescripcionLocalidadDestino()));
					ax7.setCellStyle(dateCellStyle);
					HSSFCell ax8 = fila.createCell(8);
					ax8.setCellValue(new HSSFRichTextString(fiseFormato12CD.getDescZonaBenef()));
					ax8.setCellStyle(dateCellStyle);
					HSSFCell ax9 = fila.createCell(9);
					ax9.setCellValue(new HSSFRichTextString(fiseFormato12CD.getCodigoCuentaContaEde()));
					ax9.setCellStyle(dateCellStyle);
					HSSFCell ax10 = fila.createCell(10);
					ax10.setCellValue(new HSSFRichTextString(fiseFormato12CD.getDescripcionActividad()));
					ax10.setCellStyle(dateCellStyle);
					HSSFCell ax11 = fila.createCell(11);
					ax11.setCellValue(new HSSFRichTextString(fiseFormato12CD.getIdTipDocRef()));
					ax11.setCellStyle(dateCellStyle);
					HSSFCell ax12 = fila.createCell(12);
					ax12.setCellValue(new HSSFRichTextString(fiseFormato12CD.getRucEmpresaEmiteDocRef()));
					ax12.setCellStyle(dateCellStyle);
					HSSFCell ax13 = fila.createCell(13);
					ax13.setCellValue(new HSSFRichTextString(fiseFormato12CD.getSerieDocumentoReferencia()));
					ax13.setCellStyle(dateCellStyle);
					HSSFCell ax14 = fila.createCell(14);
					ax14.setCellValue(new HSSFRichTextString(fiseFormato12CD.getNumeroDocumentoReferencia()));
					ax14.setCellStyle(dateCellStyle);
					HSSFCell ax15 = fila.createCell(15);
					ax15.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato12CD.getNumeroDias())));
					ax15.setCellStyle(dateCellStyle);
					HSSFCell ax16 = fila.createCell(16);
					ax16.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato12CD.getMontoAlimentacion())));
					ax16.setCellStyle(dateCellStyle);
					HSSFCell ax17 = fila.createCell(17);
					ax17.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato12CD.getMontoAlojamiento())));
					ax17.setCellStyle(dateCellStyle);
					HSSFCell ax18 = fila.createCell(18);
					ax18.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato12CD.getMontoMovilidad())));
					ax18.setCellStyle(dateCellStyle);
					
					m++;
				}
				
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
		_xlsSheet.autoSizeColumn((short) 16);
		_xlsSheet.autoSizeColumn((short) 17);
		_xlsSheet.autoSizeColumn((short) 18);
		
		return _xlsSheet;

	}
	
	
	//FORMATO 12D
	public HSSFSheet construirExcelFormato12D(List<?> listaFormato12D) {

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
		a4.setCellValue(new HSSFRichTextString("Etapa"));
		a4.setCellStyle(headerCellStyle);
		
		HSSFCell a6 = segundaFila.createCell(5);
		a6.setCellValue(new HSSFRichTextString("Grupo de información"));
		a6.setCellStyle(headerCellStyle);
		
		HSSFCell a7 = segundaFila.createCell(6);
		a7.setCellValue(new HSSFRichTextString("Estado"));
		a7.setCellStyle(headerCellStyle);

		if( listaFormato12D!=null && listaFormato12D.size()>0 ){
			for( int i=0;i<listaFormato12D.size();i++ ){
				
				FiseFormato12DC fiseFormato12D = (FiseFormato12DC) listaFormato12D.get(i);
				
				HSSFRow fila = _xlsSheet.createRow(i+1);
				HSSFCell ax1 = fila.createCell(1);
				ax1.setCellValue(new HSSFRichTextString(fiseFormato12D.getDescEmpresa()));
				ax1.setCellStyle(dateCellStyle);
				HSSFCell ax2 = fila.createCell(2);
				ax2.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato12D.getId().getAnoPresentacion())));
				ax2.setCellStyle(dateCellStyle);
				HSSFCell ax3 = fila.createCell(3);
				ax3.setCellValue(new HSSFRichTextString(fiseFormato12D.getDescMesPresentacion()));
				ax3.setCellStyle(dateCellStyle);
				HSSFCell ax4 = fila.createCell(4);
				ax4.setCellValue(new HSSFRichTextString(fiseFormato12D.getId().getEtapa()));
				ax4.setCellStyle(dateCellStyle);
				HSSFCell ax5 = fila.createCell(5);
				ax5.setCellValue(new HSSFRichTextString(fiseFormato12D.getDescGrupoInformacion()));
				ax5.setCellStyle(dateCellStyle);
				HSSFCell ax6 = fila.createCell(6);
				ax6.setCellValue(new HSSFRichTextString(fiseFormato12D.getDescEstado()));
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
	
	public HSSFSheet construirExcelFormato12DDImplementacion(List<?> listaFormato12DDImplementacion) {

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
		a1.setCellValue(new HSSFRichTextString("Año Ejec."));
		a1.setCellStyle(headerCellStyle);
		
		HSSFCell a2 = segundaFila.createCell(2);
		a2.setCellValue(new HSSFRichTextString("Mes Ejec."));
		a2.setCellStyle(headerCellStyle);
		
		HSSFCell a3 = segundaFila.createCell(3);
		a3.setCellValue(new HSSFRichTextString("Item"));
		a3.setCellStyle(headerCellStyle);
	
		HSSFCell a4 = segundaFila.createCell(4);
		a4.setCellValue(new HSSFRichTextString("Cod. Ubigeo"));
		a4.setCellStyle(headerCellStyle);
		
		HSSFCell a5 = segundaFila.createCell(5);
		a5.setCellValue(new HSSFRichTextString("Localidad"));
		a5.setCellStyle(headerCellStyle);
		
		HSSFCell a6 = segundaFila.createCell(6);
		a6.setCellValue(new HSSFRichTextString("Zona Benef."));
		a6.setCellStyle(headerCellStyle);
		
		HSSFCell a7 = segundaFila.createCell(7);
		a7.setCellValue(new HSSFRichTextString("Cta. Contable"));
		a7.setCellStyle(headerCellStyle);
		
		HSSFCell a8 = segundaFila.createCell(8);
		a8.setCellValue(new HSSFRichTextString("Gasto"));
		a8.setCellStyle(headerCellStyle);
		
		HSSFCell a9 = segundaFila.createCell(9);
		a9.setCellValue(new HSSFRichTextString("Tipo gasto"));
		a9.setCellStyle(headerCellStyle);
		
		HSSFCell a10 = segundaFila.createCell(10);
		a10.setCellValue(new HSSFRichTextString("Tipo Doc."));
		a10.setCellStyle(headerCellStyle);
		
		HSSFCell a11 = segundaFila.createCell(11);
		a11.setCellValue(new HSSFRichTextString("RUC"));
		a11.setCellStyle(headerCellStyle);
		
		HSSFCell a12 = segundaFila.createCell(12);
		a12.setCellValue(new HSSFRichTextString("Serie Doc."));
		a12.setCellStyle(headerCellStyle);
		
		HSSFCell a13 = segundaFila.createCell(13);
		a13.setCellValue(new HSSFRichTextString("Nro. Doc."));
		a13.setCellStyle(headerCellStyle);
		
		HSSFCell a14 = segundaFila.createCell(14);
		a14.setCellValue(new HSSFRichTextString("Fecha autorización"));
		a14.setCellStyle(headerCellStyle);
		
		HSSFCell a15 = segundaFila.createCell(15);
		a15.setCellValue(new HSSFRichTextString("Nro. Doc. autorización"));
		a15.setCellStyle(headerCellStyle);
		
		HSSFCell a16 = segundaFila.createCell(16);
		a16.setCellValue(new HSSFRichTextString("Cantidad"));
		a16.setCellStyle(headerCellStyle);
		
		HSSFCell a17 = segundaFila.createCell(17);
		a17.setCellValue(new HSSFRichTextString("Cto.unitario"));
		a17.setCellStyle(headerCellStyle);
		
		int m=0;

		if( listaFormato12DDImplementacion!=null && listaFormato12DDImplementacion.size()>0 ){
			for( int i=0;i<listaFormato12DDImplementacion.size();i++ ){
				
				FiseFormato12DD fiseFormato12DD = (FiseFormato12DD) listaFormato12DDImplementacion.get(i);
				
				if( FiseConstants.ETAPA_EJECUCION_IMPLEMENTACION_COD == fiseFormato12DD.getId().getEtapaEjecucion() ){
					
					HSSFRow fila = _xlsSheet.createRow(m+1);
					HSSFCell ax1 = fila.createCell(1);
					ax1.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato12DD.getId().getAnoEjecucionGasto())));
					ax1.setCellStyle(dateCellStyle);
					HSSFCell ax2 = fila.createCell(2);
					ax2.setCellValue(new HSSFRichTextString(fiseFormato12DD.getDescMesEjecucion()));
					ax2.setCellStyle(dateCellStyle);
					HSSFCell ax3 = fila.createCell(3);
					ax3.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato12DD.getId().getNumeroItemEtapa())));
					ax3.setCellStyle(dateCellStyle);
					HSSFCell ax4 = fila.createCell(4);
					ax4.setCellValue(new HSSFRichTextString(fiseFormato12DD.getCodUbigeo()));
					ax4.setCellStyle(dateCellStyle);
					HSSFCell ax5 = fila.createCell(5);
					ax5.setCellValue(new HSSFRichTextString(fiseFormato12DD.getDescripcionLocalidad()));
					ax5.setCellStyle(dateCellStyle);
					HSSFCell ax6 = fila.createCell(6);
					ax6.setCellValue(new HSSFRichTextString(fiseFormato12DD.getDescZonaBenef()));
					ax6.setCellStyle(dateCellStyle);
					HSSFCell ax7 = fila.createCell(7);
					ax7.setCellValue(new HSSFRichTextString(fiseFormato12DD.getCodigoCuentaContaEde()));
					ax7.setCellStyle(dateCellStyle);
					HSSFCell ax8 = fila.createCell(8);
					ax8.setCellValue(new HSSFRichTextString(fiseFormato12DD.getDescripcionGasto()));
					ax8.setCellStyle(dateCellStyle);
					HSSFCell ax9 = fila.createCell(9);
					ax9.setCellValue(new HSSFRichTextString(fiseFormato12DD.getIdTipGasto()));
					ax9.setCellStyle(dateCellStyle);
					HSSFCell ax10 = fila.createCell(10);
					ax10.setCellValue(new HSSFRichTextString(fiseFormato12DD.getIdTipDocRef()));
					ax10.setCellStyle(dateCellStyle);
					HSSFCell ax11 = fila.createCell(11);
					ax11.setCellValue(new HSSFRichTextString(fiseFormato12DD.getRucEmpresaEmiteDocRef()));
					ax11.setCellStyle(dateCellStyle);
					HSSFCell ax12 = fila.createCell(12);
					ax12.setCellValue(new HSSFRichTextString(fiseFormato12DD.getSerieDocumentoReferencia()));
					ax12.setCellStyle(dateCellStyle);
					HSSFCell ax13 = fila.createCell(13);
					ax13.setCellValue(new HSSFRichTextString(fiseFormato12DD.getNumeroDocumentoRefGasto()));
					ax13.setCellStyle(dateCellStyle);
					HSSFCell ax14 = fila.createCell(14);
					ax14.setCellValue(new HSSFRichTextString(FechaUtil.fecha_DD_MM_YYYY(fiseFormato12DD.getFechaAutorizacionGasto())));
					ax14.setCellStyle(dateCellStyle);
					HSSFCell ax15 = fila.createCell(15);
					ax15.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato12DD.getNumeroDocAutorizaGasto())));
					ax15.setCellStyle(dateCellStyle);
					HSSFCell ax16 = fila.createCell(16);
					ax16.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato12DD.getCantidad())));
					ax16.setCellStyle(dateCellStyle);
					HSSFCell ax17 = fila.createCell(17);
					ax17.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato12DD.getCostoUnitario())));
					ax17.setCellStyle(dateCellStyle);
					
					m++;
				}
				
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
		_xlsSheet.autoSizeColumn((short) 16);
		_xlsSheet.autoSizeColumn((short) 17);
		
		return _xlsSheet;

	}
	
	public HSSFSheet construirExcelFormato12DDMensual(List<?> listaFormato12DDMensual) {

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
		a1.setCellValue(new HSSFRichTextString("Año Ejec."));
		a1.setCellStyle(headerCellStyle);
		
		HSSFCell a2 = segundaFila.createCell(2);
		a2.setCellValue(new HSSFRichTextString("Mes Ejec."));
		a2.setCellStyle(headerCellStyle);
		
		HSSFCell a3 = segundaFila.createCell(3);
		a3.setCellValue(new HSSFRichTextString("Item"));
		a3.setCellStyle(headerCellStyle);
	
		HSSFCell a4 = segundaFila.createCell(4);
		a4.setCellValue(new HSSFRichTextString("Cod. Ubigeo"));
		a4.setCellStyle(headerCellStyle);
		
		HSSFCell a5 = segundaFila.createCell(5);
		a5.setCellValue(new HSSFRichTextString("Localidad"));
		a5.setCellStyle(headerCellStyle);
		
		HSSFCell a6 = segundaFila.createCell(6);
		a6.setCellValue(new HSSFRichTextString("Zona Benef."));
		a6.setCellStyle(headerCellStyle);
		
		HSSFCell a7 = segundaFila.createCell(7);
		a7.setCellValue(new HSSFRichTextString("Cta. Contable"));
		a7.setCellStyle(headerCellStyle);
		
		HSSFCell a8 = segundaFila.createCell(8);
		a8.setCellValue(new HSSFRichTextString("Gasto"));
		a8.setCellStyle(headerCellStyle);
		
		HSSFCell a9 = segundaFila.createCell(9);
		a9.setCellValue(new HSSFRichTextString("Tipo gasto"));
		a9.setCellStyle(headerCellStyle);
		
		HSSFCell a10 = segundaFila.createCell(10);
		a10.setCellValue(new HSSFRichTextString("Tipo Doc."));
		a10.setCellStyle(headerCellStyle);
		
		HSSFCell a11 = segundaFila.createCell(11);
		a11.setCellValue(new HSSFRichTextString("RUC"));
		a11.setCellStyle(headerCellStyle);
		
		HSSFCell a12 = segundaFila.createCell(12);
		a12.setCellValue(new HSSFRichTextString("Serie Doc."));
		a12.setCellStyle(headerCellStyle);
		
		HSSFCell a13 = segundaFila.createCell(13);
		a13.setCellValue(new HSSFRichTextString("Nro. Doc."));
		a13.setCellStyle(headerCellStyle);
		
		HSSFCell a14 = segundaFila.createCell(14);
		a14.setCellValue(new HSSFRichTextString("Fecha autorización"));
		a14.setCellStyle(headerCellStyle);
		
		HSSFCell a15 = segundaFila.createCell(15);
		a15.setCellValue(new HSSFRichTextString("Nro. Doc. autorización"));
		a15.setCellStyle(headerCellStyle);
		
		HSSFCell a16 = segundaFila.createCell(16);
		a16.setCellValue(new HSSFRichTextString("Cantidad"));
		a16.setCellStyle(headerCellStyle);
		
		HSSFCell a17 = segundaFila.createCell(17);
		a17.setCellValue(new HSSFRichTextString("Cto.unitario"));
		a17.setCellStyle(headerCellStyle);

		int m=0;
		
		if( listaFormato12DDMensual!=null && listaFormato12DDMensual.size()>0 ){
			for( int i=0;i<listaFormato12DDMensual.size();i++ ){
				
				FiseFormato12DD fiseFormato12DD = (FiseFormato12DD) listaFormato12DDMensual.get(i);
				
				if( FiseConstants.ETAPA_EJECUCION_OPERATIVA_COD == fiseFormato12DD.getId().getEtapaEjecucion() ){
					
					HSSFRow fila = _xlsSheet.createRow(m+1);
					HSSFCell ax1 = fila.createCell(1);
					ax1.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato12DD.getId().getAnoEjecucionGasto())));
					ax1.setCellStyle(dateCellStyle);
					HSSFCell ax2 = fila.createCell(2);
					ax2.setCellValue(new HSSFRichTextString(fiseFormato12DD.getDescMesEjecucion()));
					ax2.setCellStyle(dateCellStyle);
					HSSFCell ax3 = fila.createCell(3);
					ax3.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato12DD.getId().getNumeroItemEtapa())));
					ax3.setCellStyle(dateCellStyle);
					HSSFCell ax4 = fila.createCell(4);
					ax4.setCellValue(new HSSFRichTextString(fiseFormato12DD.getCodUbigeo()));
					ax4.setCellStyle(dateCellStyle);
					HSSFCell ax5 = fila.createCell(5);
					ax5.setCellValue(new HSSFRichTextString(fiseFormato12DD.getDescripcionLocalidad()));
					ax5.setCellStyle(dateCellStyle);
					HSSFCell ax6 = fila.createCell(6);
					ax6.setCellValue(new HSSFRichTextString(fiseFormato12DD.getDescZonaBenef()));
					ax6.setCellStyle(dateCellStyle);
					HSSFCell ax7 = fila.createCell(7);
					ax7.setCellValue(new HSSFRichTextString(fiseFormato12DD.getCodigoCuentaContaEde()));
					ax7.setCellStyle(dateCellStyle);
					HSSFCell ax8 = fila.createCell(8);
					ax8.setCellValue(new HSSFRichTextString(fiseFormato12DD.getDescripcionGasto()));
					ax8.setCellStyle(dateCellStyle);
					HSSFCell ax9 = fila.createCell(9);
					ax9.setCellValue(new HSSFRichTextString(fiseFormato12DD.getIdTipGasto()));
					ax9.setCellStyle(dateCellStyle);
					HSSFCell ax10 = fila.createCell(10);
					ax10.setCellValue(new HSSFRichTextString(fiseFormato12DD.getIdTipDocRef()));
					ax10.setCellStyle(dateCellStyle);
					HSSFCell ax11 = fila.createCell(11);
					ax11.setCellValue(new HSSFRichTextString(fiseFormato12DD.getRucEmpresaEmiteDocRef()));
					ax11.setCellStyle(dateCellStyle);
					HSSFCell ax12 = fila.createCell(12);
					ax12.setCellValue(new HSSFRichTextString(fiseFormato12DD.getSerieDocumentoReferencia()));
					ax12.setCellStyle(dateCellStyle);
					HSSFCell ax13 = fila.createCell(13);
					ax13.setCellValue(new HSSFRichTextString(fiseFormato12DD.getNumeroDocumentoRefGasto()));
					ax13.setCellStyle(dateCellStyle);
					HSSFCell ax14 = fila.createCell(14);
					ax14.setCellValue(new HSSFRichTextString(FechaUtil.fecha_DD_MM_YYYY(fiseFormato12DD.getFechaAutorizacionGasto())));
					ax14.setCellStyle(dateCellStyle);
					HSSFCell ax15 = fila.createCell(15);
					ax15.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato12DD.getNumeroDocAutorizaGasto())));
					ax15.setCellStyle(dateCellStyle);
					HSSFCell ax16 = fila.createCell(16);
					ax16.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato12DD.getCantidad())));
					ax16.setCellStyle(dateCellStyle);
					HSSFCell ax17 = fila.createCell(17);
					ax17.setCellValue(new HSSFRichTextString(String.valueOf(fiseFormato12DD.getCostoUnitario())));
					ax17.setCellStyle(dateCellStyle);
					
					m++;
				}
				
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
		_xlsSheet.autoSizeColumn((short) 16);
		_xlsSheet.autoSizeColumn((short) 17);
		
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
        String descGrupoInf = "---";
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
				if(fiseFormato14C.getFiseGrupoInformacion()!=null &&
						fiseFormato14C.getFiseGrupoInformacion().getDescripcion()!=null){
					descGrupoInf = fiseFormato14C.getFiseGrupoInformacion().getDescripcion();	
				}
				ax6.setCellValue(new HSSFRichTextString(descGrupoInf));				
				ax6.setCellStyle(dateCellStyle);
				HSSFCell ax7 = fila.createCell(7);
			//	ax7.setCellValue(new HSSFRichTextString(fiseFormato14C.getDescEstado()));
				ax7.setCellValue(new HSSFRichTextString((fiseFormato14C.getFechaEnvioDefinitivo()==null)?
						FiseConstants.ESTADO_POR_ENVIAR_F14C:FiseConstants.ESTADO_ENVIADO_F14C));
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
				ax9.setCellValue(new HSSFRichTextString(periodo.getEstado().equals("V")? "Vigente" :"Anulado"));
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
				ax13.setCellValue(new HSSFRichTextString(periodo.getFlagEnvioConObservaciones().equals("S")? "SI" :"NO"));
				ax13.setCellStyle(dateCellStyle);
				HSSFCell ax14 = fila.createCell(14);
				ax14.setCellValue(new HSSFRichTextString(periodo.getFlagMostrarAnoMesEjec().equals("S")? "SI" :"NO"));
				ax14.setCellStyle(dateCellStyle);
				HSSFCell ax15 = fila.createCell(15);
				String flagHabCosto = "Ambos";
				if(periodo.getFlagHabilitaCostosDIF14c().equals("I")){
					flagHabCosto = "Indirecto";
				}else if(periodo.getFlagHabilitaCostosDIF14c().equals("D")){
					flagHabCosto = "Directo";
				}
				ax15.setCellValue(new HSSFRichTextString(flagHabCosto));
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
	
	public HSSFSheet construirExcelMantenimientoObservacion(List<?> listaObs) {

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
		a1.setCellValue(new HSSFRichTextString("IdObservación"));
		a1.setCellStyle(headerCellStyle);
		
		HSSFCell a2 = segundaFila.createCell(2);
		a2.setCellValue(new HSSFRichTextString("Descripción"));
		a2.setCellStyle(headerCellStyle);

		if( listaObs!=null && listaObs.size()>0 ){
			for( int i=0;i<listaObs.size();i++ ){
				
				FiseObservacion obs = (FiseObservacion) listaObs.get(i);
				System.out.println("Entrando a llenar filas y celdas en el exel observacion"); 
				HSSFRow fila = _xlsSheet.createRow(i+1);
				
				HSSFCell ax1 = fila.createCell(1);
				ax1.setCellValue(new HSSFRichTextString(""+obs.getIdObservacion()));
				ax1.setCellStyle(dateCellStyle);
				HSSFCell ax2 = fila.createCell(2);
				ax2.setCellValue(new HSSFRichTextString(""+obs.getDescripcion()));
				ax2.setCellStyle(dateCellStyle);			
			}
		}
		/**
		 * longitud automatica de columnas
		 */
		_xlsSheet.autoSizeColumn((short) 1);
		_xlsSheet.autoSizeColumn((short) 2);	
		
		return _xlsSheet;
	}
	
	
	public HSSFSheet construirExcelCargosFijos(List<?> listaCargosFijos) {

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
		a2.setCellValue(new HSSFRichTextString("Año Reporte"));
		a2.setCellStyle(headerCellStyle);
		
		HSSFCell a3 = segundaFila.createCell(3);
		a3.setCellValue(new HSSFRichTextString("Mes Reporte"));
		a3.setCellStyle(headerCellStyle);
		
		HSSFCell a4 = segundaFila.createCell(4);
		a4.setCellValue(new HSSFRichTextString("N° Usu. Benef. Rural"));
		a4.setCellStyle(headerCellStyle);
		
		HSSFCell a5 = segundaFila.createCell(5);
		a5.setCellValue(new HSSFRichTextString("N° Usu. Benef. Urb. Provincias"));
		a5.setCellStyle(headerCellStyle);	
		
		HSSFCell a6 = segundaFila.createCell(6);
		a6.setCellValue(new HSSFRichTextString("N° Usu. Benef. Urb. Lima"));
		a6.setCellStyle(headerCellStyle);	
		
		HSSFCell a7 = segundaFila.createCell(7);
		a7.setCellValue(new HSSFRichTextString("N° Usu. Empad. Rural"));
		a7.setCellStyle(headerCellStyle);
		
		HSSFCell a8 = segundaFila.createCell(8);
		a8.setCellValue(new HSSFRichTextString("N° Usu. Empad. Urb. Provincias"));
		a8.setCellStyle(headerCellStyle);	
		
		HSSFCell a9 = segundaFila.createCell(9);
		a9.setCellValue(new HSSFRichTextString("N° Usu. Empad. Urb. Lima"));
		a9.setCellStyle(headerCellStyle);	
		
		HSSFCell a10 = segundaFila.createCell(10);
		a10.setCellValue(new HSSFRichTextString("N° Vales Físicos Emit. Rural"));
		a10.setCellStyle(headerCellStyle);
		
		HSSFCell a11 = segundaFila.createCell(11);
		a11.setCellValue(new HSSFRichTextString("N° Vales Físicos Emit. Urb. Provincias"));
		a11.setCellStyle(headerCellStyle);
		
		HSSFCell a12 = segundaFila.createCell(12);
		a12.setCellValue(new HSSFRichTextString("N° Vales Físicos Emit. Urb. Lima"));
		a12.setCellStyle(headerCellStyle);
		
		HSSFCell a13 = segundaFila.createCell(13);
		a13.setCellValue(new HSSFRichTextString("N° Vales Digitales Emit. Rural"));
		a13.setCellStyle(headerCellStyle);
		
		HSSFCell a14 = segundaFila.createCell(14);
		a14.setCellValue(new HSSFRichTextString("N° Vales Digitales Emit. Urb. Provincias"));
		a14.setCellStyle(headerCellStyle);	
		
		HSSFCell a15 = segundaFila.createCell(15);
		a15.setCellValue(new HSSFRichTextString("N° Vales Digitales Emit. Urb. Lima"));
		a15.setCellStyle(headerCellStyle);	
		
		HSSFCell a16 = segundaFila.createCell(16);
		a16.setCellValue(new HSSFRichTextString("N° Vales Físicos Cang. Rural"));
		a16.setCellStyle(headerCellStyle);
		
		HSSFCell a17 = segundaFila.createCell(17);
		a17.setCellValue(new HSSFRichTextString("N° Vales Físicos Cang. Urb. Provincias"));
		a17.setCellStyle(headerCellStyle);
		
		HSSFCell a18 = segundaFila.createCell(18);
		a18.setCellValue(new HSSFRichTextString("N° Vales Físicos Cang. Urb. Lima"));
		a18.setCellStyle(headerCellStyle);
		
		HSSFCell a19 = segundaFila.createCell(19);
		a19.setCellValue(new HSSFRichTextString("N° Vales Digitales Cang. Rural"));
		a19.setCellStyle(headerCellStyle);
		
		HSSFCell a20 = segundaFila.createCell(20);
		a20.setCellValue(new HSSFRichTextString("N° Vales Digitales Cang. Urb. Provincias"));
		a20.setCellStyle(headerCellStyle);
		
		HSSFCell a21 = segundaFila.createCell(21);
		a21.setCellValue(new HSSFRichTextString("N° Vales Digitales Cang. Urb. Lima"));
		a21.setCellStyle(headerCellStyle);
		
		HSSFCell a22 = segundaFila.createCell(22);
		a22.setCellValue(new HSSFRichTextString("N° Agentes Rural"));
		a22.setCellStyle(headerCellStyle);
		
		HSSFCell a23 = segundaFila.createCell(23);
		a23.setCellValue(new HSSFRichTextString("N° Agentes Urb. Provincias"));
		a23.setCellStyle(headerCellStyle);
		
		HSSFCell a24 = segundaFila.createCell(24);
		a24.setCellValue(new HSSFRichTextString("N° Agentes Urb. Lima"));
		a24.setCellStyle(headerCellStyle);
		
		HSSFCell a25 = segundaFila.createCell(25);
		a25.setCellValue(new HSSFRichTextString("Monto Cargo F. Mes Rural"));
		a25.setCellStyle(headerCellStyle);
		
		HSSFCell a26 = segundaFila.createCell(26);
		a26.setCellValue(new HSSFRichTextString("Monto Cargo F. Mes Urb. Provincias"));
		a26.setCellStyle(headerCellStyle);
		
		HSSFCell a27 = segundaFila.createCell(27);
		a27.setCellValue(new HSSFRichTextString("Monto Cargo F. Mes Urb. Lima"));
		a27.setCellStyle(headerCellStyle);		
		
		HSSFCell a28 = segundaFila.createCell(28);
		a28.setCellValue(new HSSFRichTextString("Monto Transf. Cange"));
		a28.setCellStyle(headerCellStyle);
		
		HSSFCell a29 = segundaFila.createCell(29);
		a29.setCellValue(new HSSFRichTextString("Fecha Informe de Sust."));
		a29.setCellStyle(headerCellStyle);
		
		HSSFCell a30 = segundaFila.createCell(30);
		a30.setCellValue(new HSSFRichTextString("N° Doc. de Sust."));
		a30.setCellStyle(headerCellStyle);
		
		HSSFCell a31 = segundaFila.createCell(31);
		a31.setCellValue(new HSSFRichTextString("Estado"));
		a31.setCellStyle(headerCellStyle);
		
		HSSFCell a32 = segundaFila.createCell(32);
		a32.setCellValue(new HSSFRichTextString("Fecha Recepción Inf."));
		a32.setCellStyle(headerCellStyle);
		
		HSSFCell a33 = segundaFila.createCell(33);
		a33.setCellValue(new HSSFRichTextString("N° Doc. Recepción Inf."));
		a33.setCellStyle(headerCellStyle);
		
		HSSFCell a34 = segundaFila.createCell(34);
		a34.setCellValue(new HSSFRichTextString("IGV"));
		a34.setCellStyle(headerCellStyle);
		
		HSSFCell a35 = segundaFila.createCell(35);
		a35.setCellValue(new HSSFRichTextString("Aplica IGV"));
		a35.setCellStyle(headerCellStyle);
		
		HSSFCell a36 = segundaFila.createCell(36);
		a36.setCellValue(new HSSFRichTextString("Glosa"));
		a36.setCellStyle(headerCellStyle);
		

		if(listaCargosFijos!=null && listaCargosFijos.size()>0 ){
			for( int i=0;i<listaCargosFijos.size();i++ ){
				
				FiseCargoFijoBean cargo = (FiseCargoFijoBean) listaCargosFijos.get(i);
				System.out.println("Entrando a llenar filas y celdas en el exel cargos fijos"); 
				HSSFRow fila = _xlsSheet.createRow(i+1);
				
				HSSFCell ax1 = fila.createCell(1);
				ax1.setCellValue(new HSSFRichTextString(""+cargo.getCodigoEmpresa()));
				ax1.setCellStyle(dateCellStyle);
				HSSFCell ax2 = fila.createCell(2);
				ax2.setCellValue(new HSSFRichTextString(""+cargo.getAnioReporte()));
				ax2.setCellStyle(dateCellStyle);
				HSSFCell ax3 = fila.createCell(3);
				ax3.setCellValue(new HSSFRichTextString(""+cargo.getDesMesRep()));
				ax3.setCellStyle(dateCellStyle);				
				HSSFCell ax4 = fila.createCell(4);
				ax4.setCellValue(new HSSFRichTextString(""+cargo.getNumUsuBenefR()));
				ax4.setCellStyle(dateCellStyle);				
				HSSFCell ax5 = fila.createCell(5);
				ax5.setCellValue(new HSSFRichTextString(""+cargo.getNumUsuBenefP()));
				ax5.setCellStyle(dateCellStyle);				
				HSSFCell ax6 = fila.createCell(6);
				ax6.setCellValue(new HSSFRichTextString(""+cargo.getNumUsuBenefL()));
				ax6.setCellStyle(dateCellStyle);				
				HSSFCell ax7 = fila.createCell(7);
				ax7.setCellValue(new HSSFRichTextString(""+cargo.getNumUsuEmpR()));
				ax7.setCellStyle(dateCellStyle);				
				HSSFCell ax8 = fila.createCell(8);
				ax8.setCellValue(new HSSFRichTextString(""+cargo.getNumUsuEmpP()));
				ax8.setCellStyle(dateCellStyle);				
				HSSFCell ax9 = fila.createCell(9);
				ax9.setCellValue(new HSSFRichTextString(""+cargo.getNumUsuEmpL()));
				ax9.setCellStyle(dateCellStyle);				
				HSSFCell ax10 = fila.createCell(10);
				ax10.setCellValue(new HSSFRichTextString(""+cargo.getNumValFEmiR()));
				ax10.setCellStyle(dateCellStyle);				
				HSSFCell ax11 = fila.createCell(11);
				ax11.setCellValue(new HSSFRichTextString(""+cargo.getNumValFEmiP()));
				ax11.setCellStyle(dateCellStyle);				
				HSSFCell ax12 = fila.createCell(12);
				ax12.setCellValue(new HSSFRichTextString(""+cargo.getNumValFEmiL()));
				ax12.setCellStyle(dateCellStyle);				
				HSSFCell ax13 = fila.createCell(13);
				ax13.setCellValue(new HSSFRichTextString(""+cargo.getNumValDEmiR()));
				ax13.setCellStyle(dateCellStyle);				
				HSSFCell ax14 = fila.createCell(14);
				ax14.setCellValue(new HSSFRichTextString(""+cargo.getNumValDEmiP()));
				ax14.setCellStyle(dateCellStyle);				
				HSSFCell ax15 = fila.createCell(15);
				ax15.setCellValue(new HSSFRichTextString(""+cargo.getNumValDEmiL()));
				ax15.setCellStyle(dateCellStyle);				
				HSSFCell ax16 = fila.createCell(16);
				ax16.setCellValue(new HSSFRichTextString(""+cargo.getNumValFCanR()));
				ax16.setCellStyle(dateCellStyle);				
				HSSFCell ax17 = fila.createCell(17);
				ax17.setCellValue(new HSSFRichTextString(""+cargo.getNumValFCanP()));
				ax17.setCellStyle(dateCellStyle);				
				HSSFCell ax18 = fila.createCell(18);
				ax18.setCellValue(new HSSFRichTextString(""+cargo.getNumValFCanL()));
				ax18.setCellStyle(dateCellStyle);				
				HSSFCell ax19 = fila.createCell(19);
				ax19.setCellValue(new HSSFRichTextString(""+cargo.getNumValDCanR()));
				ax19.setCellStyle(dateCellStyle);				
				HSSFCell ax20 = fila.createCell(20);
				ax20.setCellValue(new HSSFRichTextString(""+cargo.getNumValDCanP()));
				ax20.setCellStyle(dateCellStyle);				
				HSSFCell ax21 = fila.createCell(21);
				ax21.setCellValue(new HSSFRichTextString(""+cargo.getNumValDCanL()));
				ax21.setCellStyle(dateCellStyle);				
				HSSFCell ax22 = fila.createCell(22);
				ax22.setCellValue(new HSSFRichTextString(""+cargo.getNumAgenteR()));
				ax22.setCellStyle(dateCellStyle);				
				HSSFCell ax23 = fila.createCell(23);
				ax23.setCellValue(new HSSFRichTextString(""+cargo.getNumAgenteP()));
				ax23.setCellStyle(dateCellStyle);				
				HSSFCell ax24 = fila.createCell(24);
				ax24.setCellValue(new HSSFRichTextString(""+cargo.getNumAgenteL()));
				ax24.setCellStyle(dateCellStyle);				
				HSSFCell ax25 = fila.createCell(25);
				ax25.setCellValue(new HSSFRichTextString(""+cargo.getMontoMesR()));
				ax25.setCellStyle(dateCellStyle);				
				HSSFCell ax26 = fila.createCell(26);
				ax26.setCellValue(new HSSFRichTextString(""+cargo.getMontoMesP()));
				ax26.setCellStyle(dateCellStyle);				
				HSSFCell ax27 = fila.createCell(27);
				ax27.setCellValue(new HSSFRichTextString(""+cargo.getMontoMesL()));
				ax27.setCellStyle(dateCellStyle);			
				HSSFCell ax28 = fila.createCell(28);
				ax28.setCellValue(new HSSFRichTextString(""+cargo.getMontoCanje()));
				ax28.setCellStyle(dateCellStyle);			
				HSSFCell ax29 = fila.createCell(29);
				ax29.setCellValue(new HSSFRichTextString(cargo.getFechaSustento()));
				ax29.setCellStyle(dateCellStyle);
				HSSFCell ax30 = fila.createCell(30);
				ax30.setCellValue(new HSSFRichTextString(cargo.getNumDoc()));
				ax30.setCellStyle(dateCellStyle);
				HSSFCell ax31 = fila.createCell(31);
				ax31.setCellValue(new HSSFRichTextString(cargo.getDesEstado()));
				ax31.setCellStyle(dateCellStyle);	
				HSSFCell ax32 = fila.createCell(32);
				ax32.setCellValue(new HSSFRichTextString(cargo.getFechaRecepcion()));
				ax32.setCellStyle(dateCellStyle);	
				HSSFCell ax33 = fila.createCell(33);
				ax33.setCellValue(new HSSFRichTextString(cargo.getNumDocRecepcion()));
				ax33.setCellStyle(dateCellStyle);	
				HSSFCell ax34 = fila.createCell(34);
				ax34.setCellValue(new HSSFRichTextString(cargo.getIgv()));
				ax34.setCellStyle(dateCellStyle);	
				HSSFCell ax35 = fila.createCell(35);
				ax35.setCellValue(new HSSFRichTextString(cargo.getAplicaIgv()));
				ax35.setCellStyle(dateCellStyle);	
				HSSFCell ax36 = fila.createCell(36);
				ax36.setCellValue(new HSSFRichTextString(cargo.getGloza()));
				ax36.setCellStyle(dateCellStyle);	
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
		_xlsSheet.autoSizeColumn((short) 16);
		_xlsSheet.autoSizeColumn((short) 17);
		_xlsSheet.autoSizeColumn((short) 18);
		_xlsSheet.autoSizeColumn((short) 19);
		_xlsSheet.autoSizeColumn((short) 20);		
		_xlsSheet.autoSizeColumn((short) 21);
		_xlsSheet.autoSizeColumn((short) 22);
		_xlsSheet.autoSizeColumn((short) 23);	
		_xlsSheet.autoSizeColumn((short) 24);
		_xlsSheet.autoSizeColumn((short) 25);
		_xlsSheet.autoSizeColumn((short) 26);
		_xlsSheet.autoSizeColumn((short) 27);
		_xlsSheet.autoSizeColumn((short) 28);		
		_xlsSheet.autoSizeColumn((short) 29);
		_xlsSheet.autoSizeColumn((short) 30);
		_xlsSheet.autoSizeColumn((short) 31);		
		_xlsSheet.autoSizeColumn((short) 32);		
		_xlsSheet.autoSizeColumn((short) 33);
		_xlsSheet.autoSizeColumn((short) 34);
		_xlsSheet.autoSizeColumn((short) 35);
		_xlsSheet.autoSizeColumn((short) 36);
		
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
	
	//OBSERVACIONES FORMATO 12C y 12D
	public HSSFSheet construirExcelObservaciones12(List<?> listaObservacion) {

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
		a1.setCellValue(new HSSFRichTextString("Etapa ejecución"));
		a1.setCellStyle(headerCellStyle);
		
		HSSFCell a2 = segundaFila.createCell(2);
		a2.setCellValue(new HSSFRichTextString("Nro. item etapa"));
		a2.setCellStyle(headerCellStyle);
		
		HSSFCell a3 = segundaFila.createCell(3);
		a3.setCellValue(new HSSFRichTextString("Código"));
		a3.setCellStyle(headerCellStyle);
		
		HSSFCell a4 = segundaFila.createCell(4);
		a4.setCellValue(new HSSFRichTextString("Descripción"));
		a4.setCellStyle(headerCellStyle);

		if( listaObservacion!=null && listaObservacion.size()>0 ){
			for( int i=0;i<listaObservacion.size();i++ ){
				
				MensajeErrorBean observacion = (MensajeErrorBean) listaObservacion.get(i);
				
				HSSFRow fila = _xlsSheet.createRow(i+1);
				HSSFCell ax1 = fila.createCell(1);
				ax1.setCellValue(new HSSFRichTextString(observacion.getDescEtapaEjecucion()));
				ax1.setCellStyle(dateCellStyle);
				HSSFCell ax2 = fila.createCell(2);
				ax2.setCellValue(new HSSFRichTextString(String.valueOf(observacion.getNroItemEtapa())));
				ax2.setCellStyle(dateCellStyle);
				HSSFCell ax3 = fila.createCell(3);
				ax3.setCellValue(new HSSFRichTextString(String.valueOf(observacion.getCodigo())));
				ax3.setCellStyle(dateCellStyle);
				HSSFCell ax4 = fila.createCell(4);
				ax4.setCellValue(new HSSFRichTextString(observacion.getDescripcion()));
				ax4.setCellStyle(dateCellStyle);
			}
		}

		/**
		 * longitud automatica de columnas
		 */
		_xlsSheet.autoSizeColumn((short) 1);
		_xlsSheet.autoSizeColumn((short) 2);
		_xlsSheet.autoSizeColumn((short) 3);
		_xlsSheet.autoSizeColumn((short) 4);
		
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
		
		public HSSFSheet construirExcelFormato12B(List<?> listaFormato12B) {

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
			a2.setCellValue(new HSSFRichTextString("Año Presenetaciòn."));
			a2.setCellStyle(headerCellStyle);
			
			HSSFCell a3 = segundaFila.createCell(3);
			a3.setCellValue(new HSSFRichTextString("Mes Pressentación"));
			a3.setCellStyle(headerCellStyle);
		
			HSSFCell a4 = segundaFila.createCell(4);
			a4.setCellValue(new HSSFRichTextString("Grupo Información."));
			a4.setCellStyle(headerCellStyle);
			
			HSSFCell a5 = segundaFila.createCell(5);
			a5.setCellValue(new HSSFRichTextString("Estado"));
			a5.setCellStyle(headerCellStyle);
			
			HSSFCell a6 = segundaFila.createCell(6);
			a6.setCellValue(new HSSFRichTextString("Fecha de Envio"));
			a6.setCellStyle(headerCellStyle);

			if( listaFormato12B!=null && listaFormato12B.size()>0 ){
				for( int i=0;i<listaFormato12B.size();i++ ){
					
					Formato12BGartCommand command = (Formato12BGartCommand) listaFormato12B.get(i);
					
					HSSFRow fila = _xlsSheet.createRow(i+1);
					HSSFCell ax1 = fila.createCell(1);
					ax1.setCellValue(new HSSFRichTextString(command.getDescEmpresa()));
					ax1.setCellStyle(dateCellStyle);
					HSSFCell ax2 = fila.createCell(2);
					ax2.setCellValue(new HSSFRichTextString(command.getAnoPresentacion()!=null?command.getAnoPresentacion()+"":""));//anio Pres
					ax2.setCellStyle(dateCellStyle);
					HSSFCell ax3 = fila.createCell(3);
					ax3.setCellValue(new HSSFRichTextString(command.getDescMes()));//Mes pres
					ax3.setCellStyle(dateCellStyle);
					HSSFCell ax4 = fila.createCell(4);
					ax4.setCellValue(new HSSFRichTextString(command.getDescGrupo()));//grupo
					ax4.setCellStyle(dateCellStyle);
					HSSFCell ax5 = fila.createCell(5);
					ax5.setCellValue(new HSSFRichTextString(command.getDescEstado()));//estado
					ax5.setCellStyle(dateCellStyle);
					HSSFCell ax6 = fila.createCell(6);
					ax6.setCellValue(new HSSFRichTextString(command.getStrfechaEnvioDefinitivo()));//envio
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
		
	
	public HSSFSheet FillExcelSheet(XlsWorksheetConfig xlsWorksheetConfig, HSSFSheet xlsSheet, HSSFWorkbook wb) {
		_xlsSheet = xlsSheet;
		_wb = wb;
		//_xlsWorksheetConfig = xlsWorksheetConfig;

		for (XlsTableConfig xlsTableConfig : xlsWorksheetConfig.getTables()) {
			if( FiseConstants.TIPO_FORMATO_12A.equals(xlsTableConfig.getTipoFormato()) ){
				construirExcelFormato12A(xlsTableConfig.getLista());
			}else if( FiseConstants.TIPO_FORMATO_12B.equals(xlsTableConfig.getTipoFormato()) ){
				construirExcelFormato12B(xlsTableConfig.getLista());
			}else if( FiseConstants.TIPO_FORMATO_12C.equals(xlsTableConfig.getTipoFormato()) ){
				construirExcelFormato12C(xlsTableConfig.getLista());
			}else if( FiseConstants.TIPO_FORMATO_12CD_IMPLEMENTACION.equals(xlsTableConfig.getTipoFormato()) ){
				construirExcelFormato12CDImplementacion(xlsTableConfig.getLista());
			}else if( FiseConstants.TIPO_FORMATO_12CD_MENSUAL.equals(xlsTableConfig.getTipoFormato()) ){
				construirExcelFormato12CDMensual(xlsTableConfig.getLista());
			}else if( FiseConstants.TIPO_FORMATO_12D.equals(xlsTableConfig.getTipoFormato()) ){
				construirExcelFormato12D(xlsTableConfig.getLista());
			}else if( FiseConstants.TIPO_FORMATO_12DD_IMPLEMENTACION.equals(xlsTableConfig.getTipoFormato()) ){
				construirExcelFormato12DDImplementacion(xlsTableConfig.getLista());
			}else if( FiseConstants.TIPO_FORMATO_12DD_MENSUAL.equals(xlsTableConfig.getTipoFormato()) ){
				construirExcelFormato12DDMensual(xlsTableConfig.getLista());
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
			}else if( FiseConstants.OBSERVACIONES_EXPORT_EXEL.equals(xlsTableConfig.getTipoFormato()) ){
				construirExcelMantenimientoObservacion(xlsTableConfig.getLista());				
			}else if( FiseConstants.CARGO_FIJO_EXPORT_EXEL.equals(xlsTableConfig.getTipoFormato()) ){
				construirExcelCargosFijos(xlsTableConfig.getLista());				
			}
			//poner los otros formato
			//ponemos la lista de observaciones
			else if( FiseConstants.TIPO_FORMATO_VAL.equals(xlsTableConfig.getTipoFormato()) ){
				construirExcelObservaciones(xlsTableConfig.getLista());
			}else if( FiseConstants.TIPO_FORMATO_VAL_12C.equals(xlsTableConfig.getTipoFormato()) ){
				construirExcelObservaciones12(xlsTableConfig.getLista());
			}else if( FiseConstants.TIPO_FORMATO_VAL_12D.equals(xlsTableConfig.getTipoFormato()) ){
				construirExcelObservaciones12(xlsTableConfig.getLista());
			}
		}
		return _xlsSheet;
	}

}
