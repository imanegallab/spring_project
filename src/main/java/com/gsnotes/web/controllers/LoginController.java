package com.gsnotes.web.controllers;

import java.awt.Desktop;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gsnotes.bo.Compte;
import com.gsnotes.bo.Element;
import com.gsnotes.bo.Enseignant;
import com.gsnotes.bo.Etudiant;
import com.gsnotes.bo.InscriptionAnnuelle;
import com.gsnotes.bo.InscriptionMatiere;
import com.gsnotes.bo.InscriptionModule;
import com.gsnotes.bo.Module;
import com.gsnotes.bo.Niveau;
import com.gsnotes.bo.UserPrincipal;
import com.gsnotes.bo.Utilisateur;
import com.gsnotes.exceptionhandlers.ExcelHandlerException;
import com.gsnotes.services.IElementService;
import com.gsnotes.services.IEnseignantService;
import com.gsnotes.services.IEtudiantService;
import com.gsnotes.services.IModuleService;
import com.gsnotes.services.INiveauService;
import com.gsnotes.services.IUtilisateurService;
import com.gsnotes.utils.export.ExcelHandler;
import com.gsnotes.web.importexcel.repository.ElementRepository;
import com.gsnotes.web.importexcel.repository.EtudiantRepository;
import com.gsnotes.web.importexcel.repository.InscriptionAnnuelleRepository;
import com.gsnotes.web.importexcel.repository.InscriptionMatiereRepository;
import com.gsnotes.web.importexcel.repository.InscriptionModulerepository;
import com.gsnotes.web.importexcel.repository.NiveauRepository;
import com.gsnotes.web.importexcel.repository.enseignantrepository;
import com.gsnotes.web.importexcel.repository.moduleRepository;
import com.gsnotes.web.importexcel.repository.utilisateurrepository;
import com.gsnotes.web.models.UserAndAccountInfos;



@Controller
public class LoginController {

	@Autowired
	private HttpSession httpSession;
	@Autowired
	private InscriptionModulerepository ModuleRepo;
	@Autowired
	private InscriptionMatiereRepository  MatiereRepo;
	@Autowired
	private InscriptionAnnuelleRepository ansrepo;
	@Autowired
	private IEnseignantService ensrepo;
	@Autowired
	private IModuleService modulerepo;
	@Autowired
	private IElementService elemrepo;
	@Autowired
	private INiveauService niveaurepo;
	@Autowired
	private IUtilisateurService userrepo;
	@Autowired
	private IEtudiantService etudrepo;
	@Autowired
	HttpSession Session;
	/**
	 * Récupère les données de l'utilisateur connecté du contexte de securité et le
	 * stocke dans un objet personnalisé à enregistrer dans la session http
	 * 
	 * @return
	 */
	private UserAndAccountInfos getUserAccount() {
		// On vérifie si les infors de l'utilisateur sont déjà dans la session
		UserAndAccountInfos userInfo = (UserAndAccountInfos) httpSession.getAttribute("userInfo");

		if (userInfo == null) {

			// On obtient l'objet representant le compte connecté (Objet UserPrincipal
			// implémentant UserDetails)
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			// On cast vers notre objet UserPrincipal
			Compte userAccount = ((UserPrincipal) principal).getUser();

			Utilisateur u = userAccount.getProprietaire();
			
			String roleName = userAccount.getRole().getNomRole();

			userInfo = new UserAndAccountInfos(u.getIdUtilisateur(), userAccount.getIdCompte(), userAccount.getLogin(),
					u.getNom(), u.getPrenom(), u.getEmail(), roleName);

			// On le stocke dans la session
			httpSession.setAttribute("userInfo", userInfo);
		}

		return userInfo;

	}

	@GetMapping("/showMyLoginPage")
	public String showLoginForm() {

		return "loginForm";
	}

	@GetMapping("/access-denied")
	public String showAccessDenied() {

		return "access-denied";

	}

	@GetMapping("/student/showHome")
	public String showStudentHomePage(Model m) {

		UserAndAccountInfos infoUser = getUserAccount();
		m.addAttribute("userInfos", infoUser);

		return "student/userHomePage";

	}

	@GetMapping("/prof/showHome")
	public String showProfHomePage(Model m) {

		UserAndAccountInfos infoUser = getUserAccount();
		m.addAttribute("userInfos", infoUser);
		return "prof/userHomePage";

	}
	@GetMapping("/prof/importation")
	public void importer(HttpServletResponse response) throws IOException  {
		
		response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=deliberation.xlsx");
        FileInputStream excelFile = new FileInputStream(new File("C:\\Users\\pc\\Desktop\\deliberation.xlsx"));
        IOUtils.copy(excelFile, response.getOutputStream());
		/* List<ArrayList<Object>> d=ExcelHandler.readFromExcel("C:\\Users\\pc\\Desktop\\deliberation.xlsx", 0);*/	
		/*return "prof/import";*/

	}
	

	@GetMapping("/cadreadmin/showHome")
	public String showCadreAdminHomePage(Model m) {

		UserAndAccountInfos infoUser = getUserAccount();
		m.addAttribute("userInfos", infoUser);
		return "cadreadmin/userHomePage";

	}

	@GetMapping("/biblio/showHome")
	public String showBiblioHomePage(Model m) {

		UserAndAccountInfos infoUser = getUserAccount();
		m.addAttribute("userInfos", infoUser);
		return "biblio/userHomePage";

	}

	@GetMapping("/admin/showAdminHome")
	public String showAdminHome(Model m) {

		UserAndAccountInfos infoUser = getUserAccount();
		m.addAttribute("userInfos", infoUser);
		return "admin/adminHome";

	}
	//@GetMapping("/prof/verify")
	public boolean verification() throws IOException{
		
			boolean bl=checkTheFormat();
			if(!bl) {
				return false;
			}
		try {
			String Alias=ExcelHandler.AliasNiveau("C:\\Users\\pc\\Downloads\\deliberation.xlsx", 0);
			System.out.println(Alias);
			List<Element>El=elemrepo.getAllElement();
			com.gsnotes.bo.Niveau n=niveaurepo.getbyAlias(Alias);
			List<InscriptionAnnuelle> listIA=getAllInscriptionAnnuelle();
			List<Etudiant>Etd=etudrepo.getAllEtudiant();
			 List<com.gsnotes.bo.Module> ListMod=modulerepo.getAllModule();
			ExcelHandler excelImporter=new ExcelHandler(El,n,Etd,listIA,ListMod);
			double d=excelImporter.readFromExcel("C:\\Users\\pc\\Downloads\\deliberation.xlsx", 0);
			
			if(d==0) {
				return false;
			}
			else {
				
				String mot=excelImporter.nomModule("C:\\Users\\pc\\Downloads\\deliberation.xlsx", 0);
				String nomENS=excelImporter.nomEnseignant("C:\\Users\\pc\\Downloads\\deliberation.xlsx", 0);
				//InscriptionAnnuelle i=getbyid(1);
				if(d!=2020) {
					return false;
				}				
				com.gsnotes.bo.Module m=modulerepo.getbytitre(mot);
				
				if(m==null) {
					return false;
				}
				//com.gsnotes.bo.Niveau n=getbyAlias("GI2");
				
				com.gsnotes.bo.Niveau idm=m.getNiveau();
				
				if(idm.getIdNiveau()!=n.getIdNiveau()) {
					return false;
				}
				
				com.gsnotes.bo.Utilisateur U=userrepo.getbyNom(nomENS);				
				Long id=U.getIdUtilisateur();
				com.gsnotes.bo.Enseignant ENS=ensrepo.getbyidenseignant(id);
				if(ENS==null) {
					return false;
				}
				if(ENS.getSpecialite().equals(mot)) {
					
				}
				else {
					return false;
				}
				
			}
		} catch (ExcelHandlerException e) {			
			
		}	
		
		return true;
		}
		
		

	
	@RequestMapping("/prof/import")
	
	public String importFromExcel() throws ExcelHandlerException, IOException {
		boolean b= verification();
		
		List<Element>El=elemrepo.getAllElement();
		List<Etudiant>Etd=etudrepo.getAllEtudiant();
		List<InscriptionAnnuelle> listIA=getAllInscriptionAnnuelle();
		String Alias=ExcelHandler.AliasNiveau("C:\\Users\\pc\\Downloads\\deliberation.xlsx", 0);
		com.gsnotes.bo.Niveau n=niveaurepo.getbyAlias(Alias);
		 List<com.gsnotes.bo.Module> ListMod=modulerepo.getAllModule();
		ExcelHandler excelImporter=new ExcelHandler(El,n,Etd,listIA,ListMod);
		
		
		List<InscriptionAnnuelle> listAnnuelle=excelImporter.importInscriptionAnnuelle("C:\\Users\\pc\\Downloads\\deliberation.xlsx", 0);
		System.out.println("la liste annuelle est "+listAnnuelle);
             int a=0;
		
		List<InscriptionAnnuelle> lista = new ArrayList<>();
		if(!b) {
			return "prof/error";
		}
		
		for(InscriptionAnnuelle IM:listAnnuelle) {
			
			InscriptionAnnuelle MI=getByniveauAndetudiant(IM.getNiveau(),IM.getEtudiant());
			
			if(MI!=null) {
				
				lista.add(MI);
				a++;
			}else {
				lista.add(IM);
			}
		
		}
		
		if(a!=0) {
			
			Session.setAttribute("listupdate2", lista);
			return "prof/autorisation";
		}
		else {
			ansrepo.saveAll(listAnnuelle);
		}
		
		
		
		//ansrepo.saveAll(listAnnuelle);
		
		return "prof/import";
	}
	@RequestMapping("/prof/importNote")
	public String importtomodulematiere() throws ExcelHandlerException, IOException {
		List<Element>El=elemrepo.getAllElement();
		List<Etudiant>Etd=etudrepo.getAllEtudiant();
		List<InscriptionAnnuelle> listIA=getAllInscriptionAnnuelle();
		String Alias=ExcelHandler.AliasNiveau("C:\\Users\\pc\\Downloads\\deliberation.xlsx", 0);
		com.gsnotes.bo.Niveau n=niveaurepo.getbyAlias(Alias);
		 List<com.gsnotes.bo.Module> ListMod=modulerepo.getAllModule();
		ExcelHandler excelImporter=new ExcelHandler(El,n,Etd,listIA,ListMod);
		
		
		List<InscriptionModule> listModule45=excelImporter.importInscriptionModule("C:\\Users\\pc\\Downloads\\deliberation.xlsx", 0);
		List<InscriptionMatiere> listMatiere=excelImporter.importInscriptionMatiere("C:\\Users\\pc\\Downloads\\deliberation.xlsx", 0);
		boolean b= verification();
		int a=0,c=0;
		if(!b) {
			return "prof/error";
		}
		List<InscriptionModule> lista = new ArrayList<>();
		List<InscriptionMatiere> listaM = new ArrayList<>();
		for(InscriptionModule IM:listModule45) {
			InscriptionModule MI=getByinscriptionAnnuelleAndmodule(IM.getInscriptionAnnuelle(),IM.getModule());
			if(MI!=null) {
				lista.add(MI);
				a++;
			}else {
				lista.add(IM);
			}
		
		}
		for(InscriptionMatiere IM:listMatiere) {
			InscriptionMatiere MI=getByinscriptionAnnuelleAndmatiere(IM.getInscriptionAnnuelle(),IM.getMatiere());
			if(MI!=null) {
				listaM.add(MI);
				c++;
			}else {
				listaM.add(IM);
			}
		
		}
		if(a!=0) {
			
			Session.setAttribute("listupdate", lista);
			Session.setAttribute("listupdate3", listaM);
			return "prof/autorisation";
		}
		else {
			ModuleRepo.saveAll(listModule45);
			MatiereRepo.saveAll(listMatiere);
		}
		
		
		return "prof/import";
	}
	

	@GetMapping("/prof/autoriser")
	public String Autorisation() throws ExcelHandlerException {
		List<InscriptionModule> listUpdate=(List<InscriptionModule>) Session.getAttribute("listupdate");		
		List<InscriptionAnnuelle> listupdateAnnuelle=(List<InscriptionAnnuelle>) Session.getAttribute("listupdate2");
		List<InscriptionMatiere> listUpdate3=(List<InscriptionMatiere>) Session.getAttribute("listupdate3");	
		List<Element>El=elemrepo.getAllElement();
		List<Etudiant>Etd=etudrepo.getAllEtudiant();
		List<InscriptionAnnuelle> listIA=getAllInscriptionAnnuelle();
		String Alias=ExcelHandler.AliasNiveau("C:\\Users\\pc\\Downloads\\deliberation.xlsx", 0);
		com.gsnotes.bo.Niveau n=niveaurepo.getbyAlias(Alias);
		 List<com.gsnotes.bo.Module> ListMod=modulerepo.getAllModule();
		ExcelHandler excelImporter=new ExcelHandler(El,n,Etd,listIA,ListMod);
		List<Double>listNote=excelImporter.importNoteModule("C:\\Users\\pc\\Downloads\\deliberation.xlsx", 0);
		List<Double>listNoteAnnuelle=excelImporter.importNoteAnnuelle("C:\\Users\\pc\\Downloads\\deliberation.xlsx", 0);
		List<Double>listNoteMatiere=excelImporter.importNoteMatiere("C:\\Users\\pc\\Downloads\\deliberation.xlsx", 0);
		if( listUpdate!=null) {
		for(int i=0;i<listUpdate.size();i++) {
			
			InscriptionModule M=listUpdate.get(i);
			M.setNoteFinale(listNote.get(i));
			ModuleRepo.save(M);
		}}
		if( listUpdate3!=null) {
			for(int i=0;i<listUpdate3.size();i++) {
				
				InscriptionMatiere M=listUpdate3.get(i);
				M.setNoteFinale(listNoteMatiere.get(i));
				MatiereRepo.save(M);
			}}
		
		if(listupdateAnnuelle!=null) {
		for(int i=0;i<listupdateAnnuelle.size();i++) {
			
			InscriptionAnnuelle M=listupdateAnnuelle.get(i);
			if(listNoteAnnuelle.get(i)<12) {
				M.setValidation("NV");
				M.setMention("passable");
			}
			else if(listNoteAnnuelle.get(i)>=12) {
				M.setValidation("V");
				M.setMention("tres bien");
			}
			ansrepo.save(M);
		}}
		
		return "prof/import";

	}
/*	public static void LauncheAssociatedAppForFile(String pFilePath) throws IOException {

		if (Desktop.isDesktopSupported()) {
			Desktop.getDesktop().open(new File(pFilePath));
		}

	}*/
	@GetMapping("prof/Annuler")
	public String Annuler() {

		return "prof/userHomePage";

	}
	
	public InscriptionAnnuelle getbyid(long id) {
		 Optional<InscriptionAnnuelle> IA=ansrepo.findById( (long) 1);
         if(IA.isPresent()) {
       	 return  IA.get();
        }
		return null;
		
	}
/*	public Enseignant getbyidenseignant(long id) {
		 Optional<Enseignant> IA=ensrepo.findById( id);
        if(IA.isPresent()) {
      	 return  IA.get();
       }
		return null;
		
	}*/
	/*public Element getbyidMatiere(long id) {
		 Optional<Element> IA=elemrepo.findById( id);
       if(IA.isPresent()) {
     	 return  IA.get();
      }
		return null;
		
	}
	*/
 /* public com.gsnotes.bo.Module getbytitre(String titre) {
	  com.gsnotes.bo.Module m=modulerepo.getByTitre(titre);
	return m;
	  
  }*/
 /* public com.gsnotes.bo.Niveau getbyAlias(String titre) {
	  com.gsnotes.bo.Niveau m=niveaurepo.getByAlias(titre);
	return m;
	  
  }*/
  /*public com.gsnotes.bo.Utilisateur getbyNom(String Nom) {
	  com.gsnotes.bo.Utilisateur m=userrepo.getByNom(Nom);
	return m;
	  
  }*/
  /*public com.gsnotes.bo.Etudiant getbyCne(String Cne) {
	  com.gsnotes.bo.Etudiant m=etudrepo.getByCne(Cne);
	return m;
	  
  }*/
 /* public List<com.gsnotes.bo.Module> getAll(){
	  return modulerepo.findAll();
  }*/
  public List<com.gsnotes.bo.InscriptionAnnuelle> getAllInscription(){
	  return ansrepo.findAll();
  }
 /* public List<Element> getAllElement(){
	  return elemrepo.findAll();  }*/
  /*public List<Etudiant> getAllEtudiant(){
	  return etudrepo.findAll();  }*/
  public List<InscriptionAnnuelle> getAllInscriptionAnnuelle(){
	  return ansrepo.findAll();
  }
 /* public List<com.gsnotes.bo.Module> getAllModule(){
	  return modulerepo.findAll();
  }*/
  public InscriptionModule getByinscriptionAnnuelleAndmodule(InscriptionAnnuelle etd,com.gsnotes.bo.Module module) {
	  return ModuleRepo.getByInscriptionAnnuelleAndModule(etd,module);
  }
  public InscriptionMatiere getByinscriptionAnnuelleAndmatiere(InscriptionAnnuelle etd,Element element) {
	  return MatiereRepo.getByInscriptionAnnuelleAndMatiere(etd,element);
  }
  public InscriptionAnnuelle getByniveauAndetudiant(Niveau nv,Etudiant etd) {
	  return ansrepo.getByNiveauAndEtudiant(nv,etd);
  }
  public Boolean checkTheFormat(){
		
		String[] extensions = {"xlsx","xlsm","xlsb","xltx","xltm","xls","xlt","xls","xml","xlam","xla","xlw","xlr"};
		Boolean endsWith = false;
		String fN ="C:\\Users\\pc\\Downloads\\deliberation.xlsx";
	    String extension = fN.substring(fN.lastIndexOf(".") + 1,fN.length());

		for(String e : extensions) {
		   
			if(e.equals(extension)) {
				endsWith = true;
				break;
			}
		}
		return endsWith;
	}
}
