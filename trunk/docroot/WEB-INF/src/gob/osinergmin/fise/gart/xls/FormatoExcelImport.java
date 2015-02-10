package gob.osinergmin.fise.gart.xls;

import gob.osinergmin.fise.common.util.FiseUtil;
import gob.osinergmin.fise.constant.FiseConstants;
import gob.osinergmin.fise.domain.FiseFormato12BCPK;
import gob.osinergmin.fise.domain.FiseFormato12BD;
import gob.osinergmin.fise.domain.FiseFormato12BDPK;
import gob.osinergmin.fise.domain.FiseFormato13AC;
import gob.osinergmin.fise.domain.FiseFormato13ACPK;
import gob.osinergmin.fise.domain.FiseFormato13AD;
import gob.osinergmin.fise.domain.FiseFormato13ADPK;


import gob.osinergmin.fise.domain.FiseFormato14BD;
import gob.osinergmin.fise.util.FormatoUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;



public class FormatoExcelImport {
	
	@Autowired
	@Qualifier("fiseUtil")
	private static FiseUtil fiseUtil;
	
	private static HSSFSheet readExcel(HSSFWorkbook libro,String tipoHoja){
		int nroHojaSelec=0;
		
		for (int sheetNro = 0; sheetNro < libro.getNumberOfSheets(); sheetNro++){
			
			if(tipoHoja.equalsIgnoreCase(FiseConstants.NOMBRE_HOJA_FORMATO13A)){
				if(FiseConstants.NOMBRE_HOJA_FORMATO13A.equals(libro.getSheetName(sheetNro)) ){
					nroHojaSelec = sheetNro;
					break;
				}
			}else if(tipoHoja.equalsIgnoreCase(FiseConstants.NOMBRE_HOJA_FORMATO12B)){
				if(FiseConstants.NOMBRE_HOJA_FORMATO12B.equals(libro.getSheetName(sheetNro))){
					nroHojaSelec = sheetNro;
					break;
				}
			}
			
			
		}
		
		HSSFSheet hoja = libro.getSheetAt(nroHojaSelec);
		System.out.println("hoja nro ::"+nroHojaSelec);
		return hoja;
		
		
	}
	
	
	public static FiseFormato13AC readSheetCabecera(HSSFWorkbook libro) throws Exception{
		HSSFSheet hojaF13A=readExcel(libro,FiseConstants.NOMBRE_HOJA_FORMATO13A);
		FiseFormato13AC fise13C =new FiseFormato13AC();
		FiseFormato13ACPK pk=new FiseFormato13ACPK();
		for(int i=FiseConstants.ROW_INFO_EMPRESA;i<=FiseConstants.ROW_INFO_FECHA ;i++){
			HSSFRow row= hojaF13A.getRow(i);
			
			HSSFCell cellD = row.getCell(FiseConstants.COLUMN_D);
			HSSFCell cellE = row.getCell(FiseConstants.COLUMN_E);
			if(i==FiseConstants.ROW_INFO_EMPRESA){
				HSSFCell cellE_emp = row.getCell(FiseConstants.COLUMN_E);
				HSSFCell cellD_nom = row.getCell(FiseConstants.COLUMN_D);
				System.out.println("codempres :::=> "+cellE_emp);
				if(cellE_emp.getCellType() == HSSFCell.CELL_TYPE_FORMULA || (cellE_emp.getCellType() == HSSFCell.CELL_TYPE_STRING && cellE_emp.getCellType() != HSSFCell.CELL_TYPE_BLANK) ){
					if(cellE_emp.getCellType() == HSSFCell.CELL_TYPE_FORMULA){
						pk.setCodEmpresa(cellE_emp.getRichStringCellValue().getString());
						
					}else{
						pk.setCodEmpresa(cellE_emp.toString());
					}
					 System.out.println("CODEMPRESA :::=> "+pk.getCodEmpresa());
				}else{
					throw new Exception("Distribuidora Eléctrica no valida ");
				}
				System.out.println("NOMEMPRESA :::=> "+cellD);
				if(cellD_nom.getCellType() == HSSFCell.CELL_TYPE_STRING && cellD_nom.getCellType() != HSSFCell.CELL_TYPE_BLANK){
					fise13C.setDescEmpresa(cellD_nom.toString());
					 
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
		HSSFSheet hojaF13A=readExcel(libro,FiseConstants.NOMBRE_HOJA_FORMATO13A);
		
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
						System.out.println("fecha ==>"+fecha);	
						if(fecha!=null && fecha.length()>5){
							long anio=Long.parseLong((fecha.substring(0,fecha.indexOf("-"))).trim());
							long mes=Long.parseLong((fecha.substring((fecha.indexOf("-")+1),fecha.length())).trim());
							System.out.println("ANIO - MES ==>"+anio +"-"+mes);	
							if(anio<=cabecera.getId().getAnoPresentacion() ){
								if(anio < cabecera.getId().getAnoPresentacion()){
									fise13.setAnoAlta(anio);
									fise13.setMesAlta(mes);
								}else{
									if(mes <= cabecera.getId().getMesPresentacion()){
										fise13.setMesAlta(mes);
									}else{
										throw new Exception("El mes debe de alta debe ser menor o igual al mes de presentación error fila : B"+(r+1));
									}
									fise13.setAnoAlta(anio);
								}
							}else{
								throw new Exception("El año debe de alta debe ser menor o igual al año de presentación error fila : B"+(r+1));
							}
						}else{
							
							throw new Exception("El año/mes de alta no cumplen con el formato establecido (yyyy-mm) error fila : B"+(r+1));
						}
						
						
						
						
					}
					
					
				}else{
					throw new Exception("El año/mes de alta no cumplen con el formato establecido (yyyy-mm) error fila : B"+(r+1));
				}
				
				if(isread){
					if(cellUbigeo.getCellType() == HSSFCell.CELL_TYPE_STRING && cellUbigeo.getCellType() != HSSFCell.CELL_TYPE_BLANK){
						pk.setCodUbigeo(cellUbigeo.toString());
						System.out.println("ubgeo  ==>"+pk.getCodUbigeo());
					}else if( cellUbigeo.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
						String valor = "" + cellUbigeo.getNumericCellValue();
						pk.setCodUbigeo(FormatoUtil.eliminaDecimales(valor));
					}else{
						throw new Exception("ubigeo no valido en fila nro : C"+(r+1));
					}
					
					
					if(cellLocaliad.getCellType() == HSSFCell.CELL_TYPE_STRING && cellLocaliad.getCellType() != HSSFCell.CELL_TYPE_BLANK){
						fise13.setDescripcionLocalidad(cellLocaliad.toString());
						System.out.println("localidad nombre  ==>"+fise13.getDescripcionLocalidad());
					}
					
					
					/**CORREGIR ESTA VALIDACION**/
					if( cellBeneficiario.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
						pk.setIdZonaBenef(new Double(cellBeneficiario.getNumericCellValue()).longValue());
						System.out.println("zona beneficiario  ==>"+pk.getIdZonaBenef());
					}else{
						try{
							pk.setIdZonaBenef((long)Double.parseDouble(cellBeneficiario.toString()));
						}catch(NumberFormatException n){
							throw new Exception("Zona de beneficiarios no valido en fila nro :"+(r+1));
						}
					}
					
					
					
					
					
					if(cellAtiende.getCellType() == HSSFCell.CELL_TYPE_STRING && cellAtiende.getCellType() != HSSFCell.CELL_TYPE_BLANK){
						fise13.setNombreSedeAtiende(cellAtiende.toString());
					}
					
					try{
						
						if(cellValor.getCellType() != HSSFCell.CELL_TYPE_BLANK){
							fise13.setNumeroBenefiPoteSectTipico((long)Double.parseDouble(cellValor.toString()));
						}else{
							fise13.setNumeroBenefiPoteSectTipico(0L);
						}
						System.out.println("VALOR  ==>"+fise13.getNumeroBenefiPoteSectTipico());
						
					}catch(NumberFormatException n){
						throw new Exception("NumeroBeneficiario no valido en fila nro :"+(r+1));
					}catch(Exception n1){
						throw new Exception("NumeroBeneficiario no valido en fila nro :"+(r+1));
					}
					
					
					
					pk.setAnoPresentacion(cabecera.getId().getAnoPresentacion());
					pk.setCodEmpresa(cabecera.getId().getCodEmpresa());
					pk.setMesPresentacion(cabecera.getId().getMesPresentacion());
					pk.setEtapa(cabecera.getId().getEtapa());
					
					fise13.setId(pk);
					
					//validamos edelnor o luz del sur
				    boolean process = true;
					
					if( FiseConstants.ZONABENEF_LIMA == pk.getIdZonaBenef() ){
						if( FiseConstants.COD_EMPRESA_EDELNOR.equalsIgnoreCase(pk.getCodEmpresa().trim()) || FiseConstants.COD_EMPRESA_LUZ_SUR.equalsIgnoreCase(pk.getCodEmpresa().trim()) ){
							process = true;
						}else{
							process = false;
						}
					}else{
						process = true;
					}
					
					if(process){
						lstDetalle.add(fise13);
					}
					
					
				}
				
				
				
			}
			
		
		
		}
		
		System.out.println("lstDetalle::"+lstDetalle.size());
		return lstDetalle;
		
	}
	
	
	public static FiseFormato12BCPK readSheetCabecera12B(HSSFWorkbook libro) throws Exception{
		HSSFSheet hojaF12B=readExcel(libro,FiseConstants.NOMBRE_HOJA_FORMATO12B);
		FiseFormato12BCPK pk=new FiseFormato12BCPK();
		
		for(int i=FiseConstants.ROW_INFO_EMPRESA_12B;i<=FiseConstants.ROW_INFO_FECHA_12B ;i++){
			HSSFRow row= hojaF12B.getRow(i);
			
			
			HSSFCell cellE = row.getCell(FiseConstants.COLUMN_E);
			HSSFCell cellF = row.getCell(FiseConstants.COLUMN_F);
			
			if(i==FiseConstants.ROW_INFO_EMPRESA_12B){
				HSSFCell cellF_emp = row.getCell(FiseConstants.COLUMN_F);
				
				System.out.println("codempres :::=> "+cellF_emp);
				if(cellF_emp.getCellType() == HSSFCell.CELL_TYPE_FORMULA || (cellF_emp.getCellType() == HSSFCell.CELL_TYPE_STRING && cellF_emp.getCellType() != HSSFCell.CELL_TYPE_BLANK) ){
					if(cellF_emp.getCellType() == HSSFCell.CELL_TYPE_FORMULA){
						pk.setCodEmpresa(cellF_emp.getRichStringCellValue().getString());
						
					}else{
						pk.setCodEmpresa(cellF_emp.toString());
					}
					 System.out.println("CODEMPRESA :::=> "+pk.getCodEmpresa());
				}else{
					throw new Exception("Distribuidora Eléctrica no válida ");
				}
				
				
			}else if(i==FiseConstants.ROW_INFO_FECHA_12B){
					try{
						System.out.println("año :::=> "+cellE);
						System.out.println("MES :::=> "+cellF);
                        pk.setAnoPresentacion((int)Double.parseDouble(cellE.toString()));
						pk.setMesPresentacion((int)Double.parseDouble(cellF.toString()));
						
					}catch(NumberFormatException n){
						throw new Exception("Año / mes no válido");
					}
					
			}
			
		}
		
			
			
			return pk;
		
	}

	
	public static List<FiseFormato12BD> getListDetalleSheet12B(HSSFWorkbook libro,FiseFormato12BCPK pk,FiseUtil util)throws Exception{
		HSSFSheet hojaF12B=readExcel(libro,FiseConstants.NOMBRE_HOJA_FORMATO12B);
		System.out.println("leuyendo deatlle");
		//fiseUtil=new FiseUtil();
		List<FiseFormato12BD> lstDetalle=new ArrayList<FiseFormato12BD>();
		int numColumns=FiseConstants.COLUMN_H;
		
		if(pk.getCodEmpresa().trim().equalsIgnoreCase("EDLN") || pk.getCodEmpresa().trim().equalsIgnoreCase("LDS")){
			numColumns=FiseConstants.COLUMN_I;
		}
		for(int clnm=FiseConstants.COLUMN_G;clnm<=numColumns;clnm++){
			System.out.println("**********************COLUMNA NRO"+clnm+"*****************************");
			Integer idzona=FiseConstants.ZONA_RURAL;
			if(clnm==FiseConstants.COLUMN_H){
				idzona=FiseConstants.ZONA_PROVINCIA;
			}else if(clnm==FiseConstants.COLUMN_I){
				idzona=FiseConstants.ZONA_LIMA;
			}
			System.out.println("**********************COLUMNA NRO"+clnm+" / ZONA NRO "+idzona+"*****************************");
			FiseFormato12BD formato=new FiseFormato12BD();
			FiseFormato12BDPK dtlPk=new FiseFormato12BDPK();
			dtlPk.setAnoPresentacion(pk.getAnoPresentacion());
			dtlPk.setMesPresentacion(pk.getMesPresentacion());
			dtlPk.setAnoEjecucionGasto(pk.getAnoPresentacion());
			dtlPk.setMesEjecucionGasto(pk.getMesPresentacion());
			dtlPk.setCodEmpresa(pk.getCodEmpresa());
			dtlPk.setIdZonaBenef(idzona);
			formato.setId(dtlPk);
			
			FiseFormato14BD fise14D=util.getDetalle14BDbyEmpAnioEtapa(dtlPk.getCodEmpresa().trim(), dtlPk.getAnoPresentacion(),null, dtlPk.getIdZonaBenef(), FiseConstants.ETAPA_ESTABLECIDO);
			int numfilallenas=0;
			System.out.println("FISE 14D :::"+fise14D);
			
			for(int row=FiseConstants.ROW_INIT_DETALLE_12B;row<=FiseConstants.ROW_FIN_DETALLE_12B;row++){
				System.out.println("******************fila nro :"+row+ "********************************");
				 HSSFRow fila= hojaF12B.getRow(row);
				 HSSFCell cell = fila.getCell(clnm);
				 try{	
				 if(row==FiseConstants.ROW_INIT_DETALLE_12B){
					 System.out.println("iniio1"+cell);
					 if(cell.getCellType() != HSSFCell.CELL_TYPE_BLANK ){
						  formato.setNumeroValesImpreso((int)Double.parseDouble(cell.toString()));
						  formato.setCostoEstandarUnitValeImpre((fise14D!=null && fise14D.getCostoUnitarioImpresionVales()!=null)?fise14D.getCostoUnitarioImpresionVales():new BigDecimal(0.00));
						  formato.setCostoTotalImpresionVale(formato.getCostoEstandarUnitValeImpre().multiply(new BigDecimal(formato.getNumeroValesImpreso())));
						  formato.setCostoTotalImpresionVale(formato.getCostoTotalImpresionVale()!=null?formato.getCostoTotalImpresionVale().setScale(2, BigDecimal.ROUND_UP):new BigDecimal(0.00));
						  numfilallenas++;
					 }
					
				 } if(row==FiseConstants.ROW_INIT_VALES_DOM_12B){
					 System.out.println("iniio2"+cell);
					 if(cell.getCellType() != HSSFCell.CELL_TYPE_BLANK){
						  formato.setNumeroValesRepartidosDomi((int)Double.parseDouble(cell.toString()));
						  formato.setCostoEstandarUnitValeRepar((fise14D!=null && fise14D.getCostoUnitReprtoValeDomici()!=null)?fise14D.getCostoUnitReprtoValeDomici():new BigDecimal(0.00));
						  formato.setCostoTotalRepartoValesDomi(formato.getCostoEstandarUnitValeRepar().multiply(new BigDecimal(formato.getNumeroValesRepartidosDomi())));
						  formato.setCostoTotalRepartoValesDomi(formato.getCostoTotalRepartoValesDomi()!=null?formato.getCostoTotalRepartoValesDomi().setScale(2, BigDecimal.ROUND_UP):new BigDecimal(0.00));
						  numfilallenas++;
					 }
					
				 } if(row==FiseConstants.ROW_INIT_VALES_ELECT_12B){
					 System.out.println("iniio3"+cell);
					 if(cell.getCellType() != HSSFCell.CELL_TYPE_BLANK){
						  formato.setNumeroValesEntregadoDisEl((int)Double.parseDouble(cell.toString()));
						  formato.setCostoEstandarUnitValDisEl((fise14D!=null && fise14D.getCostoUnitEntregaValDisEl()!=null)?fise14D.getCostoUnitEntregaValDisEl():new BigDecimal(0.00));
						  formato.setCostoTotalEntregaValDisEl(formato.getCostoEstandarUnitValDisEl().multiply(new BigDecimal(formato.getNumeroValesEntregadoDisEl())));
						  formato.setCostoTotalEntregaValDisEl(formato.getCostoTotalEntregaValDisEl()!=null?formato.getCostoTotalEntregaValDisEl().setScale(2, BigDecimal.ROUND_UP):new BigDecimal(0.00));
						   numfilallenas++;
					 }
					
				 } if(row==FiseConstants.ROW_INIT_VALES_FISICO_12B){
					 System.out.println("iniio4"+cell);
					 if(cell.getCellType() != HSSFCell.CELL_TYPE_BLANK){
						  formato.setNumeroValesFisicosCanjeados((int)Double.parseDouble(cell.toString()));
						  formato.setCostoEstandarUnitValFiCan((fise14D!=null && fise14D.getCostoUnitCanjeLiqValFisi()!=null)?fise14D.getCostoUnitCanjeLiqValFisi():new BigDecimal(0.00));
						  formato.setCostoTotalCanjeLiqValeFis(formato.getCostoEstandarUnitValFiCan().multiply(new BigDecimal(formato.getNumeroValesFisicosCanjeados())));
						  formato.setCostoTotalCanjeLiqValeFis(formato.getCostoTotalCanjeLiqValeFis()!=null?formato.getCostoTotalCanjeLiqValeFis().setScale(2, BigDecimal.ROUND_UP):new BigDecimal(0.00));
						  numfilallenas++;
					 }
					 
				 } if(row==FiseConstants.ROW_INIT_VALES_DIGITAL_12B){
					 System.out.println("iniio5"+cell);
					 if(cell.getCellType() != HSSFCell.CELL_TYPE_BLANK){
						  formato.setNumeroValesDigitalCanjeados((int)Double.parseDouble(cell.toString()));
						  formato.setCostoEstandarUnitValDgCan((fise14D!=null && fise14D.getCostoUnitCanjeValDigital()!=null)?fise14D.getCostoUnitCanjeValDigital():new BigDecimal(0.00));
						  formato.setCostoTotalCanjeLiqValeDig(formato.getCostoEstandarUnitValDgCan().multiply(new BigDecimal(formato.getNumeroValesDigitalCanjeados())));
						  formato.setCostoTotalCanjeLiqValeDig(formato.getCostoTotalCanjeLiqValeDig()!=null?formato.getCostoTotalCanjeLiqValeDig().setScale(2, BigDecimal.ROUND_UP):new BigDecimal(0.00));
						  numfilallenas++;
					 }
					
				 } if(row==FiseConstants.ROW_INIT_ATENCION_12B){
					 System.out.println("iniio6"+cell);
					 if(cell.getCellType() != HSSFCell.CELL_TYPE_BLANK){
						  formato.setNumeroAtenciones((int)Double.parseDouble(cell.toString()));
						  formato.setCostoEstandarUnitAtencion((fise14D!=null && fise14D.getCostoUnitarioPorAtencion()!=null)?fise14D.getCostoUnitarioPorAtencion():new BigDecimal(0.00));
						  formato.setCostoTotalAtencionConsRecl(formato.getCostoEstandarUnitAtencion().multiply(new BigDecimal(formato.getNumeroAtenciones())));
						  formato.setCostoTotalAtencionConsRecl(formato.getCostoTotalAtencionConsRecl()!=null?formato.getCostoTotalAtencionConsRecl().setScale(2, BigDecimal.ROUND_UP):new BigDecimal(0.00));
						  
						  numfilallenas++;
					 }
					
				 } if(row==FiseConstants.ROW_INIT_GESTION_12B){
					 System.out.println("iniio7"+cell);
					 if(cell.getCellType() != HSSFCell.CELL_TYPE_BLANK){
						  formato.setTotalGestionAdministrativa(new BigDecimal(cell.toString()));
						  numfilallenas++;
					 }
					 
				 } if(row==FiseConstants.ROW_INIT_DESPLAZAMIENTO_12B){
					 System.out.println("iniio8"+cell);
					 if(cell.getCellType() != HSSFCell.CELL_TYPE_BLANK){
						  formato.setTotalDesplazamientoPersonal(new BigDecimal(cell.toString()));
						  numfilallenas++;
					 }
					 
				 } if(row==FiseConstants.ROW_FIN_DETALLE_12B){
					 System.out.println("iniio9"+cell);
					 if(cell.getCellType() != HSSFCell.CELL_TYPE_BLANK){
						  formato.setTotalActividadesExtraord(new BigDecimal(cell.toString()));
						  numfilallenas++;
					 }
					
				 }
				 
			}catch(NumberFormatException n){
				numfilallenas=0;
						throw new Exception("Formato no válido en fila nro :"+(row+1));
			}catch(Exception n1){
						throw new Exception("Formato no válido en fila nro :"+(row+1));
			}
			
			}
			
			if(numfilallenas>1){
				System.out.println("agrega a lista");
				lstDetalle.add(formato);
				
			}
			
		
		}
		return lstDetalle;
		
	}
}
