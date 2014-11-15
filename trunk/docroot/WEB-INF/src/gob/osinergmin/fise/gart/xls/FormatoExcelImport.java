package gob.osinergmin.fise.gart.xls;

import gob.osinergmin.fise.bean.MensajeErrorBean;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.FiseFormato13AC;
import gob.osinergmin.fise.domain.FiseFormato13ACPK;
import gob.osinergmin.fise.domain.FiseFormato13AD;
import gob.osinergmin.fise.domain.FiseFormato13ADPK;






import java.util.ArrayList;
import java.util.List;






import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;



public class FormatoExcelImport {
	

	
	private static HSSFSheet readExcel(HSSFWorkbook libro){
		int nroHojaSelec=0;
		System.out.println("nro de hojas:"+ libro.getNumberOfSheets());
		for (int sheetNro = 0; sheetNro < libro.getNumberOfSheets(); sheetNro++){
			System.out.println("nombre de hoja "+sheetNro+":"+ libro.getSheetName(sheetNro));
			if(FiseConstants.NOMBRE_HOJA_FORMATO13A.equals(libro.getSheetName(sheetNro)) ){
				nroHojaSelec = sheetNro;
				break;
			}
		}
		System.out.println("nro de hoja seleccionada "+nroHojaSelec);
		HSSFSheet hojaF13A = libro.getSheetAt(nroHojaSelec);
		System.out.println("hojaF13A "+hojaF13A);
		
		//readSheet(hojaF13A);
		return hojaF13A;
		
		
	}
	
	
	public static FiseFormato13AC readSheetCabecera(HSSFWorkbook libro) throws Exception{
		HSSFSheet hojaF13A=readExcel(libro);
		FiseFormato13AC fise13C =new FiseFormato13AC();
		FiseFormato13ACPK pk=new FiseFormato13ACPK();
		for(int i=FiseConstants.ROW_INFO_EMPRESA;i<=FiseConstants.ROW_INFO_FECHA ;i++){
			HSSFRow row= hojaF13A.getRow(i);
			
			HSSFCell cellD = row.getCell(FiseConstants.COLUMN_D);
			HSSFCell cellE = row.getCell(FiseConstants.COLUMN_E);
			if(i==FiseConstants.ROW_INFO_EMPRESA){
				
				
				if(cellD.getCellType() == HSSFCell.CELL_TYPE_STRING && cellD.getCellType() != HSSFCell.CELL_TYPE_BLANK ){
					pk.setCodEmpresa(cellD.toString());
					 System.out.println("CODEMPRESA :::=> "+pk.getCodEmpresa());
				}else{
					throw new Exception("Distribuidora Eléctrica no valida ");
				}
				System.out.println("NOMEMPRESA :::=> "+cellE);
				if(cellE.getCellType() == HSSFCell.CELL_TYPE_STRING && cellE.getCellType() != HSSFCell.CELL_TYPE_BLANK){
					
					fise13C.setDescEmpresa(cellE.toString());
					 
				}
				System.out.println("NOMEMPRESA SALIO :::=> "+cellE);
				
			}else if(i==FiseConstants.ROW_INFO_FECHA){
					try{
                        pk.setAnoPresentacion((long)Double.parseDouble(cellD.toString()));
						pk.setMesPresentacion((long)Double.parseDouble(cellE.toString()));
						
					}catch(NumberFormatException n){
						throw new Exception("Año / mes no valido");
					}
					
			}
			
		}
		
			fise13C.setId(pk);
		
	  return fise13C;
		
	}
	
	
	public static List<FiseFormato13AD> getListDetalleSheet(HSSFWorkbook libro,FiseFormato13AC cabecera)throws Exception{
		HSSFSheet hojaF13A=readExcel(libro);
		
		int numRows=hojaF13A.getPhysicalNumberOfRows();
		
		List<FiseFormato13AD> lstDetalle=new ArrayList<FiseFormato13AD>();
		Integer codSector=0;
		for(int c=FiseConstants.COLUMN_E;c<=FiseConstants.COLUMN_L;c++ ){
			codSector++;
			for(int r=(FiseConstants.ROW_INIT_DETALLE+1);r<numRows ;r++){
				FiseFormato13AD fise13=new FiseFormato13AD();
			    FiseFormato13ADPK pk=new FiseFormato13ADPK();
				
				HSSFRow row= hojaF13A.getRow(r);
				
                if(c==FiseConstants.COLUMN_K ){
                	pk.setCodSectorTipico("SER");
				}else if(c==FiseConstants.COLUMN_L ){
                	pk.setCodSectorTipico("ESP");
				}else{
					pk.setCodSectorTipico(codSector.toString());
				}
				
            	System.out.println("SECTOR ==>"+pk.getCodSectorTipico());
            	
            	
				HSSFCell cellFecha = row.getCell(FiseConstants.COLUMN_B);
				HSSFCell cellUbigeo = row.getCell(FiseConstants.COLUMN_C);
				HSSFCell cellLocaliad = row.getCell(FiseConstants.COLUMN_D);
				HSSFCell cellBeneficiario = row.getCell(FiseConstants.COLUMN_N);
				HSSFCell cellAtiende = row.getCell(FiseConstants.COLUMN_O);
				HSSFCell cellValor = row.getCell(c);
				
				boolean isread=true;
				if(cellFecha.getCellType() == HSSFCell.CELL_TYPE_STRING && cellFecha.getCellType() != HSSFCell.CELL_TYPE_BLANK ){
					if(cellFecha.toString().equalsIgnoreCase("Total")){
						r=numRows;
						isread=false;
					}else{
						String fecha=cellFecha.toString().trim();
						long anio=Long.parseLong((fecha.substring(0,fecha.indexOf("-"))).trim());
						long mes=Long.parseLong((fecha.substring((fecha.indexOf("-")+1),fecha.length())).trim());
						
						fise13.setAnoAlta(anio);
						fise13.setMesAlta(mes);
						System.out.println("ANIO - MES ==>"+anio +"-"+mes);	
					}
					
					
				}
				
				if(isread){
					if(cellUbigeo.getCellType() == HSSFCell.CELL_TYPE_STRING && cellUbigeo.getCellType() != HSSFCell.CELL_TYPE_BLANK){
						pk.setCodUbigeo(cellUbigeo.toString());
						System.out.println("ubgeo  ==>"+pk.getCodUbigeo());
					}else{
						throw new Exception("ubigeo no valido en fila nro :"+(r+1));
					}
					
					
					if(cellLocaliad.getCellType() == HSSFCell.CELL_TYPE_STRING && cellLocaliad.getCellType() != HSSFCell.CELL_TYPE_BLANK){
						fise13.setDescripcionLocalidad(cellLocaliad.toString());
						System.out.println("localidad nombre  ==>"+fise13.getDescripcionLocalidad());
					}
					
					try{
						pk.setIdZonaBenef((long)Double.parseDouble(cellBeneficiario.toString()));
					}catch(NumberFormatException n){
						throw new Exception("Zona de beneficiarios no valido en fila nro :"+(r+1));
					}
					
					
					
					if(cellAtiende.getCellType() == HSSFCell.CELL_TYPE_STRING && cellAtiende.getCellType() != HSSFCell.CELL_TYPE_BLANK){
						fise13.setNombreSedeAtiende(cellAtiende.toString());
					}
					
					try{
						fise13.setNumeroBenefiPoteSectTipico((long)Double.parseDouble(cellValor.toString()));
						
					}catch(NumberFormatException n){
						new Exception("NumeroBeneficiario no valido en fila nro :"+(r+1));
					}
					
					
					
					pk.setAnoPresentacion(cabecera.getId().getAnoPresentacion());
					pk.setCodEmpresa(cabecera.getId().getCodEmpresa());
					pk.setMesPresentacion(cabecera.getId().getMesPresentacion());
					pk.setEtapa(cabecera.getId().getEtapa());
					
					fise13.setId(pk);
					lstDetalle.add(fise13);
				}
				
				
				
			}
			
		
		
		}
		
		System.out.println("lstDetalle::"+lstDetalle.size());
		return lstDetalle;
		
	}
	
	
	@SuppressWarnings("unused")
	private MensajeErrorBean getMensajeErrorBean(Integer cont){
		MensajeErrorBean error = new MensajeErrorBean();
		error.setId(cont);
		//error.setDescripcion(mapaErrores.get(FiseConstants.COD_ERROR_F12_30));
		return new MensajeErrorBean();
	}

}
