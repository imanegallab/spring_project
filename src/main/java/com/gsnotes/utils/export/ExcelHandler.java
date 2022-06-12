package com.gsnotes.utils.export;

import org.apache.poi.ss.usermodel.Cell;


import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.gsnotes.bo.Element;
import com.gsnotes.bo.Etudiant;
import com.gsnotes.bo.InscriptionAnnuelle;
import com.gsnotes.bo.InscriptionMatiere;
import com.gsnotes.bo.InscriptionModule;
import com.gsnotes.bo.Niveau;
import com.gsnotes.exceptionhandlers.ExcelHandlerException;
import com.gsnotes.web.importexcel.repository.ElementRepository;
import com.gsnotes.web.importexcel.repository.InscriptionAnnuelleRepository;
import com.gsnotes.web.importexcel.repository.InscriptionModulerepository;
import com.gsnotes.web.importexcel.repository.NiveauRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class ExcelHandler {
    private static List<Element> ListElement=new ArrayList();
    private static List<Etudiant> ListEtudiant=new ArrayList();
    private static Niveau N;
    private static List<InscriptionAnnuelle> listIA=new ArrayList();
    private static List<com.gsnotes.bo.Module> ListMod=new ArrayList();
	private static final ExcelHandler instance = new ExcelHandler(ListElement,N,ListEtudiant,listIA,ListMod);
	

	public ExcelHandler(List<Element> ListElement,Niveau N,List<Etudiant> Etudiants,List<InscriptionAnnuelle>listIA,List<com.gsnotes.bo.Module> listMod2) {
		this.ListElement=ListElement;
		this.N=N;
		this.ListEtudiant=Etudiants;
		this.listIA=listIA;
		this.ListMod=listMod2;
	}

	
	public static final ExcelHandler getInstance() {
		return instance;
	}


	public static double readFromExcel(String pFileName, int pSheet) throws ExcelHandlerException {

		//List<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>();
		
		double ans =0;
       int a=0,b=0,c=0,d=0,f=0;
		double data = 0;
		try {
			Workbook workbook = null;
			try {
				FileInputStream excelFile = new FileInputStream(new File(pFileName));
				workbook = new XSSFWorkbook( excelFile);
				Sheet datatypeSheet = workbook.getSheetAt(pSheet);
				int rowNumber=0;
				Iterator<Row> iterator = datatypeSheet.iterator();
				String title=null,title2=null ;
				while (iterator.hasNext()) {

					ArrayList<Object> rowValues = new ArrayList<Object>();
                     
					Row currentRow = iterator.next();
					
					Iterator<Cell> cellIterator = currentRow.iterator();
					int a1 = 0;
                    int cid=0;
                    double m = 0,e=0;
                    double element = 0;
                    double Melement = 0,Mmodule=0;
                    
					while (cellIterator.hasNext()) {
					    	
                     
						Cell currentCell = cellIterator.next();
						if(rowNumber==0 && cid==1) {
							
							 ans=(currentCell.getNumericCellValue());
							
							
						}
					
						if(rowNumber==6 && cid==4) {
							 title=currentCell.getStringCellValue();	
							 
							
							}
						if(rowNumber==6 && cid==5) {
							 title2=currentCell.getStringCellValue();	
							 
							 
							 
							}
						 if(cid<9 && rowNumber>=8 ) {
							
						if (currentCell.getCellType() == CellType.NUMERIC  ) {
							
							 
							if(0<=currentCell.getNumericCellValue() &&currentCell.getNumericCellValue()<=20  )
							{
								 if(cid==4) {
									 //m=(int) currentCell.getNumericCellValue();
									
									 for(Element elm:ListElement) {
										 
									    if(title.equals(elm.getNom()) ) {
									    	m=(double) ((elm.getCurrentCoefficient())*(currentCell.getNumericCellValue()));									    	
									    }									 								 
								 }
								 }
								 if(cid==5) {
									 
									 //e=(m+(int) currentCell.getNumericCellValue());
									 
									 for(Element elm:ListElement) {										 
										    if("element2".equals(elm.getNom()) ) {
										    	e=m+(double) ((elm.getCurrentCoefficient())*(currentCell.getNumericCellValue()));	
										    	
										    }									 								 
									 }
								 
								 
								 }
								 if(cid==6) {
										Melement=( currentCell.getNumericCellValue());
										if(e!=Melement) {
											b+=1;
											
										}									
									 }
								
								 if(cid==8) {
										Mmodule=( currentCell.getNumericCellValue());
										if(Mmodule!=Melement) {
											c+=1;
										}
									 }
								rowValues.add(currentCell.getNumericCellValue());
							}
							
							else {
							a+=1;
							
							}
						}
						
						if (currentCell.getCellType() == CellType.STRING ) {
							
                           if(cid==7) {
							String val=(currentCell.getStringCellValue());
							if(Melement>=12 && val.equals("NV") ) {
								d+=1;
							}
							if(Melement<12 && val.equals("V") ) {
								d+=1;
							}
								
						} }

					 }
						
                        cid++;
                        }
					
                 if(a==0 && b==0 && c==0 && d==0 )
                  {
                	 
                	 data=ans;
                  }
                  else 
                  {data=0;}
					
			rowNumber++;

				}
			} finally {
				if (workbook != null) {
					workbook.close();
				}
			}
		} catch (Exception ex) {
			throw new ExcelHandlerException( ex);
		}
        
		return data;

	}
	public static String nomModule(String pFileName, int pSheet) throws ExcelHandlerException {

		//List<InscriptionModule> listModule=new ArrayList<>();
		
      String namemodule=null;
		try {
			Workbook workbook = null;
			try {
				FileInputStream excelFile = new FileInputStream(new File(pFileName));
				workbook = new XSSFWorkbook( excelFile);
				Sheet datatypeSheet = workbook.getSheetAt(pSheet);
				int rowNumber=0;
				Iterator<Row> iterator = datatypeSheet.iterator();

				while (iterator.hasNext()) {
					
                    Row currentRow = iterator.next();
					
					Iterator<Cell> cellIterator = currentRow.iterator();
					
                    int cid=0;
                   
					while (cellIterator.hasNext()) {
                      
						Cell currentCell = cellIterator.next();
						
						
						if(rowNumber==4 && cid==4) {
							 namemodule=(currentCell.getStringCellValue());
							 return namemodule;
							//rowValues.add(namemodule);
						}
						
                        cid++;
                        
                        }
					
				
					
			rowNumber++;
			
				}
			} finally {
				if (workbook != null) {
					workbook.close();
				}
			}
		} catch (Exception ex) {
			throw new ExcelHandlerException( ex);
		}
		return namemodule;
        
		
    
	}
	public static String AliasNiveau(String pFileName, int pSheet) throws ExcelHandlerException {

		//List<InscriptionModule> listModule=new ArrayList<>();
		
      String namemodule=null;
		try {
			Workbook workbook = null;
			try {
				FileInputStream excelFile = new FileInputStream(new File(pFileName));
				workbook = new XSSFWorkbook( excelFile);
				Sheet datatypeSheet = workbook.getSheetAt(pSheet);
				int rowNumber=0;
				Iterator<Row> iterator = datatypeSheet.iterator();

				while (iterator.hasNext()) {
					
                    Row currentRow = iterator.next();
					
					Iterator<Cell> cellIterator = currentRow.iterator();
					
                    int cid=0;
                   
					while (cellIterator.hasNext()) {
                      
						Cell currentCell = cellIterator.next();
						
						
						if(rowNumber==2 && cid==1) {
							 namemodule=(currentCell.getStringCellValue());
							 return namemodule;
							//rowValues.add(namemodule);
						}
						
                        cid++;
                        
                        }
					
				
					
			rowNumber++;
			
				}
			} finally {
				if (workbook != null) {
					workbook.close();
				}
			}
		} catch (Exception ex) {
			throw new ExcelHandlerException( ex);
		}
		return namemodule;
        
		
    
	}
	public static String nomEnseignant(String pFileName, int pSheet) throws ExcelHandlerException {

		//List<InscriptionModule> listModule=new ArrayList<>();
		
      String name=null;
		try {
			Workbook workbook = null;
			try {
				FileInputStream excelFile = new FileInputStream(new File(pFileName));
				workbook = new XSSFWorkbook( excelFile);
				Sheet datatypeSheet = workbook.getSheetAt(pSheet);
				int rowNumber=0;
				Iterator<Row> iterator = datatypeSheet.iterator();

				while (iterator.hasNext()) {
					
                    Row currentRow = iterator.next();
					
					Iterator<Cell> cellIterator = currentRow.iterator();
					
                    int cid=0;
                   
					while (cellIterator.hasNext()) {
                      
						Cell currentCell = cellIterator.next();
						
						
						if(rowNumber==5 && cid==4) {
							 name=(currentCell.getStringCellValue());
							 return name;
							//rowValues.add(namemodule);
						}
						
                        cid++;
                        
                        }
					
				
					
			rowNumber++;
			
				}
			} finally {
				if (workbook != null) {
					workbook.close();
				}
			}
		} catch (Exception ex) {
			throw new ExcelHandlerException( ex);
		}
		return name;
        
		
    
	}
	public static List<InscriptionModule> importData(String pFileName, int pSheet) throws ExcelHandlerException {

		List<InscriptionModule> listModule=new ArrayList<>();
		
      
		try {
			Workbook workbook = null;
			try {
				FileInputStream excelFile = new FileInputStream(new File(pFileName));
				workbook = new XSSFWorkbook( excelFile);
				Sheet datatypeSheet = workbook.getSheetAt(pSheet);
				int rowNumber=0;
				Iterator<Row> iterator = datatypeSheet.iterator();

				while (iterator.hasNext()) {
					InscriptionModule IM = null;
					ArrayList<Double> rowValues = new ArrayList<Double>();
                     
					Row currentRow = iterator.next();
					
					Iterator<Cell> cellIterator = currentRow.iterator();
					
                    int cid=0;
                   
					while (cellIterator.hasNext()) {
                      
						Cell currentCell = cellIterator.next();
						
						
						 if(cid<9 && rowNumber>=8) {
							 
							 if (currentCell.getCellType() == CellType.NUMERIC  ) {
								 if(cid==6) {
									  IM=new InscriptionModule();
										IM.setNoteFinale(currentCell.getNumericCellValue());									
									 }}
							 if (currentCell.getCellType() == CellType.STRING  ) {
                                 if(cid==7) {
							String val=(currentCell.getStringCellValue());
							     IM.setValidation(val);	
							     listModule.add(IM);
						} }
						
							
					 }
						
                        cid++;
                        
                        }
					
				
					
			rowNumber++;
			
				}
			} finally {
				if (workbook != null) {
					workbook.close();
				}
			}
		} catch (Exception ex) {
			throw new ExcelHandlerException( ex);
		}
        
		
     return listModule;
	}
	
	
	public static List<InscriptionMatiere> importDataElement(String pFileName, int pSheet) throws ExcelHandlerException {

		List<InscriptionMatiere> listModule=new ArrayList<>();
		
      
		try {
			Workbook workbook = null;
			try {
				FileInputStream excelFile = new FileInputStream(new File(pFileName));
				workbook = new XSSFWorkbook( excelFile);
				Sheet datatypeSheet = workbook.getSheetAt(pSheet);
				int rowNumber=0;
				Iterator<Row> iterator = datatypeSheet.iterator();

				while (iterator.hasNext()) {
					InscriptionMatiere IM1=null;
					InscriptionMatiere IM2=null;
					
					ArrayList<Double> rowValues = new ArrayList<Double>();
                     
					Row currentRow = iterator.next();
					
					Iterator<Cell> cellIterator = currentRow.iterator();
					
                    int cid=0;
                   
					while (cellIterator.hasNext()) {
                      
						Cell currentCell = cellIterator.next();
						
						
						 if(cid<9 && rowNumber>=8) {
							 if (currentCell.getCellType() == CellType.NUMERIC  ) {
								 
								 if(cid==4) {
									   IM1=new InscriptionMatiere();
										IM1.setNoteFinale(currentCell.getNumericCellValue());
									
										 listModule.add(IM1);
									 }
							
                                 if(cid==5) {
                                	 IM2=new InscriptionMatiere();
		                                IM2.setNoteFinale(currentCell.getNumericCellValue());
                                	 listModule.add(IM2);
						              
                                 }
							 }
							
							 
							 
					 }
						
                        cid++;
                        
                        }
					
				
					
			rowNumber++;
			
				}
			} finally {
				if (workbook != null) {
					workbook.close();
				}
			}
		} catch (Exception ex) {
			throw new ExcelHandlerException( ex);
		}
        
		
     return listModule;
	}
	public static List<String> importDataCne(String pFileName, int pSheet) throws ExcelHandlerException {

		List<String> data=new ArrayList<>();
		
      
		try {
			Workbook workbook = null;
			try {
				FileInputStream excelFile = new FileInputStream(new File(pFileName));
				workbook = new XSSFWorkbook( excelFile);
				Sheet datatypeSheet = workbook.getSheetAt(pSheet);
				int rowNumber=0;
				Iterator<Row> iterator = datatypeSheet.iterator();

				while (iterator.hasNext()) {
					
                    Row currentRow = iterator.next();
					
					Iterator<Cell> cellIterator = currentRow.iterator();
					
                    int cid=0;
                   
					while (cellIterator.hasNext()) {
                      
						Cell currentCell = cellIterator.next();
						
						
						if(rowNumber>=8 && cid==1) {
							 data.add(currentCell.getStringCellValue());
							
							//rowValues.add(namemodule);
						}						
                        cid++;
                        
                        }
					
				
					
			rowNumber++;
			
				}
			} finally {
				if (workbook != null) {
					workbook.close();
				}
			}
		} catch (Exception ex) {
			throw new ExcelHandlerException( ex);
		}
		return data;
        
		
    
	}
	public static List<InscriptionAnnuelle> importInscriptionAnnuelle(String pFileName, int pSheet) throws ExcelHandlerException {

		List<InscriptionAnnuelle> data=new ArrayList<>();
		
      
		try {
			Workbook workbook = null;
			try {
				FileInputStream excelFile = new FileInputStream(new File(pFileName));
				workbook = new XSSFWorkbook( excelFile);
				Sheet datatypeSheet = workbook.getSheetAt(pSheet);
				int rowNumber=0;
				Iterator<Row> iterator = datatypeSheet.iterator();
				int ans=0;
				while (iterator.hasNext()) {
					InscriptionAnnuelle IA1=new InscriptionAnnuelle();
					
					IA1.setNiveau(N);
					
					Etudiant e=null;
                    Row currentRow = iterator.next();
					
					Iterator<Cell> cellIterator = currentRow.iterator();
					
                    int cid=0;
                   
					while (cellIterator.hasNext()) {
						
						Cell currentCell = cellIterator.next();
						
						if(rowNumber==0 && cid==1) {
							ans=(int) currentCell.getNumericCellValue();
						}
						IA1.setAnnee(2020);
						if(rowNumber>=8) {
						if(cid==1) {
							// currentCell.getStringCellValue()
							 for(Etudiant etd:ListEtudiant) {
								 if(etd.getCne().equals(currentCell.getStringCellValue())) {
									 e=etd;
									 IA1.setEtudiant(e);
								 }
							 }
							
							//rowValues.add(namemodule);
						}		
						
						if( cid==9) {
							IA1.setRang((int) currentCell.getNumericCellValue());
						}
						
						if(cid==8) {
							double note=currentCell.getNumericCellValue();
							if(note<12) {
								IA1.setMention("passable");
								IA1.setValidation("NV");
							}
							else if(note>12 && note<16){
								IA1.setMention("Bien");
								IA1.setValidation("V");
							}
							else if(note>16) {
								IA1.setMention("tresBien");
								IA1.setValidation("V");
								
							}
						}
						
						
						
						
						data.add(IA1);
						}
						
                        cid++;
                        
                        }
					
				
					
			rowNumber++;
			
				}
			} finally {
				if (workbook != null) {
					workbook.close();
				}
			}
		} catch (Exception ex) {
			throw new ExcelHandlerException( ex);
		}
		return data;
        
		
    
	}
	public static List<InscriptionModule> importInscriptionModule(String pFileName, int pSheet) throws ExcelHandlerException {

		List<InscriptionModule> data=new ArrayList<>();
		
      
		try {
			Workbook workbook = null;
			try {
				FileInputStream excelFile = new FileInputStream(new File(pFileName));
				workbook = new XSSFWorkbook( excelFile);
				Sheet datatypeSheet = workbook.getSheetAt(pSheet);
				int rowNumber=0;
				Iterator<Row> iterator = datatypeSheet.iterator();
				com.gsnotes.bo.Module mdl=null;
				while (iterator.hasNext()) {
					InscriptionModule IA1=new InscriptionModule();	
					InscriptionAnnuelle A=null;
					Etudiant e=null;
					
                    Row currentRow = iterator.next();
					
					Iterator<Cell> cellIterator = currentRow.iterator();
					
                    int cid=0;
                   
					while (cellIterator.hasNext()) {
						
						Cell currentCell = cellIterator.next();
						if(rowNumber==4 && cid==4) {
							
							
							for(com.gsnotes.bo.Module mod:ListMod) {
								
								if(mod.getTitre().equals(currentCell.getStringCellValue())) {
									
									mdl=mod;
								}
							}
						}
						
						IA1.setModule(mdl);
						if(rowNumber>=8) {
						if(cid==1) {
							 
                   		 for(InscriptionAnnuelle IA:listIA) {
                   			//System.out.println("la valeur de l'etudiant est "+(IA.getEtudiant()));
										 if(IA.getEtudiant().getCne().equals(currentCell.getStringCellValue())) {
											
											IA1.setInscriptionAnnuelle(IA);
										 }
									 }
                   	
								 }
	
						
						if( cid==6) {
							IA1.setNoteFinale(currentCell.getNumericCellValue());
						}
						
						
						
						
						
						
						data.add(IA1);
						}
						
                        cid++;
                        
                        }
					
				
					
			rowNumber++;
			
				}
			} finally {
				if (workbook != null) {
					workbook.close();
				}
			}
		} catch (Exception ex) {
			throw new ExcelHandlerException( ex);
		}
		return data;
        
		
    
	}
	
	public static List<InscriptionMatiere> importInscriptionMatiere(String pFileName, int pSheet) throws ExcelHandlerException {

		List<InscriptionMatiere> data=new ArrayList<>();
		
      
		try {
			Workbook workbook = null;
			try {
				FileInputStream excelFile = new FileInputStream(new File(pFileName));
				workbook = new XSSFWorkbook( excelFile);
				Sheet datatypeSheet = workbook.getSheetAt(pSheet);
				int rowNumber=0;
				Iterator<Row> iterator = datatypeSheet.iterator();
				Element mdl=null;
				Element mdl2=null;
				while (iterator.hasNext()) {
					InscriptionMatiere IA1=new InscriptionMatiere();	
					InscriptionMatiere IA2=new InscriptionMatiere();	
					InscriptionAnnuelle A=null;
					Etudiant e=null;
					
                    Row currentRow = iterator.next();
					
					Iterator<Cell> cellIterator = currentRow.iterator();
					
                    int cid=0;
                   
					while (cellIterator.hasNext()) {
						
						Cell currentCell = cellIterator.next();
						if(rowNumber==6 && cid==4) {
							
							
							for(Element mod:ListElement) {
								
								if(mod.getNom().equals(currentCell.getStringCellValue())) {
									
									mdl=mod;
								}
							}
						}
                         if(rowNumber==6 && cid==5) {
							
							
							for(Element mod:ListElement) {
								
								if(mod.getNom().equals(currentCell.getStringCellValue())) {
									
									mdl2=mod;
								}
							}
						}
						
						IA1.setMatiere(mdl);
						
						IA2.setMatiere(mdl2);
						
						if(rowNumber>=8) {
						if(cid==1) {
							 
                   		 for(InscriptionAnnuelle IA:listIA) {
                   			//System.out.println("la valeur de l'etudiant est "+(IA.getEtudiant()));
										 if(IA.getEtudiant().getCne().equals(currentCell.getStringCellValue())) {
											
											IA1.setInscriptionAnnuelle(IA);
											IA2.setInscriptionAnnuelle(IA);
										 }
									 }
                   	
								 }
	
						
						if( cid==4) {
							IA1.setNoteFinale(currentCell.getNumericCellValue());
						}
						if( cid==5) {
							IA2.setNoteFinale(currentCell.getNumericCellValue());
						}
						
						
						
						
						
						
						data.add(IA1);
						data.add(IA2);
						}
						
                        cid++;
                        
                        }
					
				
					
			rowNumber++;
			
				}
			} finally {
				if (workbook != null) {
					workbook.close();
				}
			}
		} catch (Exception ex) {
			throw new ExcelHandlerException( ex);
		}
		return data;
        
		
    
	}
	
	public static List<Double> importNoteModule(String pFileName, int pSheet) throws ExcelHandlerException {

		List<Double> data=new ArrayList<>();
		
      
		try {
			Workbook workbook = null;
			try {
				FileInputStream excelFile = new FileInputStream(new File(pFileName));
				workbook = new XSSFWorkbook( excelFile);
				Sheet datatypeSheet = workbook.getSheetAt(pSheet);
				int rowNumber=0;
				Iterator<Row> iterator = datatypeSheet.iterator();
				
				while (iterator.hasNext()) {
					
					double Note=0;
                    Row currentRow = iterator.next();
					
					Iterator<Cell> cellIterator = currentRow.iterator();
					
                    int cid=0;
                   
					while (cellIterator.hasNext()) {
						
						Cell currentCell = cellIterator.next();
						
						if(rowNumber>=8) {
						
	
						
						if( cid==6) {
							
							 Note=currentCell.getNumericCellValue();
						}
						
						
						
						
						
						data.add( Note);
						
						}
						
                        cid++;
                        
                        }
					
				
					
			rowNumber++;
			
				}
			} finally {
				if (workbook != null) {
					workbook.close();
				}
			}
		} catch (Exception ex) {
			throw new ExcelHandlerException( ex);
		}
		return data;
        
		
    
	}
	
	public static List<Double> importNoteAnnuelle(String pFileName, int pSheet) throws ExcelHandlerException {

		List<Double> data=new ArrayList<>();
		
      
		try {
			Workbook workbook = null;
			try {
				FileInputStream excelFile = new FileInputStream(new File(pFileName));
				workbook = new XSSFWorkbook( excelFile);
				Sheet datatypeSheet = workbook.getSheetAt(pSheet);
				int rowNumber=0;
				Iterator<Row> iterator = datatypeSheet.iterator();
				com.gsnotes.bo.Module mdl=null;
				while (iterator.hasNext()) {
					
					double Note=0;
                    Row currentRow = iterator.next();
					
					Iterator<Cell> cellIterator = currentRow.iterator();
					
                    int cid=0;
                   
					while (cellIterator.hasNext()) {
						
						Cell currentCell = cellIterator.next();
						
						
						
						if(rowNumber>=8) {
						
	
						
						if( cid==8) {
							
							 Note=currentCell.getNumericCellValue();
							 
						}
						
						
						
						
						
						data.add( Note);
						
						}
						
                        cid++;
                        
                        }
					
				
					
			rowNumber++;
			
				}
			} finally {
				if (workbook != null) {
					workbook.close();
				}
			}
		} catch (Exception ex) {
			throw new ExcelHandlerException( ex);
		}
		return data;
        
		
    
	}
	public static List<Double> importNoteMatiere(String pFileName, int pSheet) throws ExcelHandlerException {

		List<Double> data=new ArrayList<>();
		
      
		try {
			Workbook workbook = null;
			try {
				FileInputStream excelFile = new FileInputStream(new File(pFileName));
				workbook = new XSSFWorkbook( excelFile);
				Sheet datatypeSheet = workbook.getSheetAt(pSheet);
				int rowNumber=0;
				Iterator<Row> iterator = datatypeSheet.iterator();
				
				while (iterator.hasNext()) {
					Double IA1=0.0;
					Double IA2=0.0;
					
					
                    Row currentRow = iterator.next();
					
					Iterator<Cell> cellIterator = currentRow.iterator();
					
                    int cid=0;
                   
					while (cellIterator.hasNext()) {
						
						Cell currentCell = cellIterator.next();
						
                         
						
						
						if(rowNumber>=8) {
						
	
						
						if( cid==4) {
							IA1=(currentCell.getNumericCellValue());
						}
						if( cid==5) {
							IA2=(currentCell.getNumericCellValue());
						}
						
						
						
						
						
						
						data.add(IA1);
						data.add(IA2);
						}
						
                        cid++;
                        
                        }
					
				
					
			rowNumber++;
			
				}
			} finally {
				if (workbook != null) {
					workbook.close();
				}
			}
		} catch (Exception ex) {
			throw new ExcelHandlerException( ex);
		}
		return data;
        
		
    
	}
}
