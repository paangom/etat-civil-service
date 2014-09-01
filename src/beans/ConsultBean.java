/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import models.DeclarationDeces;
import models.DeclarationMariage;
import models.DeclarationNaissance;
import models.DelivredPieces;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import services.ActeDecesServices;
import services.ActeMariageServices;
import services.ActeNaissanceServices;
import services.CentreServices;
import services.DelivredPieceService;
import services.PiecesAnnexesServices;
import util.MyUtil;
import util.NombreEnLettre;
import util.Tools;

/**
 *
 * @author Papa NGOM
 */


public class ConsultBean implements Serializable {
    
    /**
	 * 
	 */
	JasperPrint jasperPrint;
	private static final long serialVersionUID = 1L;
	private DeclarationNaissance decToConsult;
    private DeclarationDeces decDCToConsult;
    private DeclarationMariage decMarToConsult;
    private ActeNaissanceServices acteService = new ActeNaissanceServices();
    private ActeDecesServices deceService = new ActeDecesServices();
    private ActeMariageServices marService = new ActeMariageServices();
    private DelivredPieceService delService = new DelivredPieceService(); 
    private CentreServices cenServ = new CentreServices();
    @SuppressWarnings("rawtypes")
	private Map parameter = new HashMap();
    
    private PiecesAnnexesServices pService = new PiecesAnnexesServices();
	private DelivredPieceService dService = new DelivredPieceService();
    
    private DeclarationNaissance filterNaissance;
    private DeclarationDeces filterDeces;
    private DeclarationMariage filterMariage;
    
    
    private List<DeclarationNaissance> registreCurrentYear;
    
    private List<DeclarationMariage> registreMariageCurrentYear;
    
    private List<DeclarationDeces> registreDecesCurrentYear;
    private List<DelivredPieces> pdByUser = null;
    private DelivredPieces pieceDel;
    
    private String mention;
    private String mentionN;
    private String mentionD;
 
    private String selectedAnnees;
    public String type;
    private List<String> annees;
    
    private int decN;
    private int decM;
    private int decD;
    
    private int decNBU;
    private int decMBU;
    private int decDBU;
    private int piecDel;
    private int pieceDelBU;
    private int population;
  
    /**
     * Creates a new instance of consultBean
     */
    public ConsultBean() {
    	 annees= new ArrayList<String>(); 
        int a = Integer.parseInt(cenServ.getCentre().getAnneeRegistre());
 		
 		for(int i = anneeCourant ; i >= a; i--){
 			annees.add(i+""); 
 		} 
 		
 				
    }
    
   

    public DeclarationNaissance getDecToConsult() {
        return decToConsult;
    }

    public DeclarationDeces getDecDCToConsult() {
        return decDCToConsult;
    }

    public DeclarationMariage getDecMarToConsult() {
        return decMarToConsult;
    }

    public void setDecMarToConsult(DeclarationMariage decMarToConsult) {
        this.decMarToConsult = decMarToConsult;
    }
    
    

    public void setDecDCToConsult(DeclarationDeces decDCToConsult) {
        this.decDCToConsult = decDCToConsult;
    }
    
    

    public void setDecToConsult(DeclarationNaissance decToConsult) {
        this.decToConsult = decToConsult;
    }
    
    public String viewDecclaration(){
        if(MyUtil.getProfil() != null)
        	return MyUtil.pathConsultationDeclaration()+"naissance?faces-redirect=true";
        else
        	return MyUtil.pathLogin();
    }
    
    public String updateRegistreN(){
        if(MyUtil.getProfil() != null)
        	return MyUtil.pathModificationActe()+"naissance?faces-redirect=true";
        else
        	return MyUtil.pathLogin();
    }
    
    public String updateRegistreM(){
        if(MyUtil.getProfil() != null)
        	return MyUtil.pathModificationActe()+"mariage?faces-redirect=true";
        else
        	return MyUtil.pathLogin();
    }
    
    public String updateRegistreD(){
        if(MyUtil.getProfil() != null)
        	return MyUtil.pathModificationActe()+"deces?faces-redirect=true";
        else
        	return MyUtil.pathLogin();
    }
    
    
    public String viewDecclarationDeces(){
        if(MyUtil.getProfil() != null)
        	return MyUtil.pathConsultationDeclaration()+"deces?faces-redirect=true";
        else
        	return MyUtil.pathLogin();
    }
    
    public String viewDeclarationMariage(){
    	if(MyUtil.getProfil() != null)
    		return MyUtil.pathConsultationDeclaration()+"mariage?faces-redirect=true";
    	else
    		return MyUtil.pathLogin();
    }
    
    public String viewActeNaissance(){
        if(MyUtil.getProfil() != null)
        	return MyUtil.pathConsultationActe()+"naissance?faces-redirect=true";
        else
        	return MyUtil.pathLogin();
    }
    
    public String viewActeDeces(){
        if(MyUtil.getProfil() != null)
        	return MyUtil.pathConsultationActe()+"deces?faces-redirect=true";
        else
        	return MyUtil.pathLogin();
    }
    
    public String viewActeMariage(){
        if(MyUtil.getProfil() != null){
        	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("decM", this.decMarToConsult);
        	return MyUtil.pathConsultationActe()+"mariage?faces-redirect=true";
        }
        else
        	return MyUtil.pathLogin();
    }
    
    @SuppressWarnings("static-access")
	public String validateDeclarationNaissance() {
        String route = "";
        if(MyUtil.getProfil() != null){
        	if(acteService.verifyValidate(this.decToConsult)){
		        this.decToConsult.setEtat("Valider");
		        this.decToConsult.setValidateurNaissance(MyUtil.getUserLogged());
		        this.decToConsult.setNumero_acte(acteService.numeroActe(this.decToConsult.getDate_creation().substring(6, 10)));
		        this.decToConsult.setDate_modification(Tools.getCurrentDateTime());
		        if (acteService.updateDeclarationNaissance(this.decToConsult)) {
		            route =   MyUtil.basePath() + "registre/naissance?faces-redirect=true";
		            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "La déclaration a été bien validée avec succès!", null);
		            FacesContext context = FacesContext.getCurrentInstance();
		            context.getCurrentInstance().addMessage(null, message);
		        	
		        	context.getExternalContext().getFlash().setKeepMessages(true);
		        }
		        else{
		            route =  MyUtil.basePath() + "/consultation/declaration/naissance?faces-redirect=true";
		            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "La déclaration n'a pas été validée!", null);
		            FacesContext context = FacesContext.getCurrentInstance();
		            context.getCurrentInstance().addMessage(null, message);
		        	
		        	context.getExternalContext().getFlash().setKeepMessages(true);
		        }
        	}
        	else{
        		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "La déclaration vient d'être validée!", null);
	            FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
	        	
	        	context.getExternalContext().getFlash().setKeepMessages(true);
        	}
        }
        else
        	route  = MyUtil.pathLogin();
        
        return  route;
    }
    
    @SuppressWarnings("static-access")
	public String validateDeclarationDeces() {
        String route = "";
        if(MyUtil.getProfil() != null){
        	if(deceService.verifyValidate(this.decDCToConsult)){
		        this.decDCToConsult.setEtat("Valider");
		        this.decDCToConsult.setNumero_acte(deceService.numeroActe(this.decDCToConsult.getDate_creation().substring(6, 10)));
		        this.decDCToConsult.setDate_modification(Tools.getCurrentDateTime());
		        this.decDCToConsult.setValidateurDeces(MyUtil.getUserLogged());
		        if (deceService.updateDeclarationDeces(decDCToConsult)) {
		        	
		        	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  "La déclaration de décès a été bien validé!", null);
		        	FacesContext context = FacesContext.getCurrentInstance();
		            context.getCurrentInstance().addMessage(null, message);
		        	
		        	context.getExternalContext().getFlash().setKeepMessages(true);
		            route =   MyUtil.basePath() + "registre/dece?faces-redirect=true";
		        }
		        else{
		        	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "La déclaration n'a pas pu être valider!", null);
		        	FacesContext context = FacesContext.getCurrentInstance();
		            context.getCurrentInstance().addMessage(null, message);
		        	
		        	context.getExternalContext().getFlash().setKeepMessages(true);
		        	route = MyUtil.basePath() +"/consultation/declaration/deces?faces-redirect=true";
		        
		        }
        	}
        	else{
        		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "La déclaration vient d'être validée!", null);
	            FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
	        	
	        	context.getExternalContext().getFlash().setKeepMessages(true);
        	}
        }
        else
        	route = MyUtil.pathLogin();
        return route;
    }
    
    @SuppressWarnings("static-access")
	public String validateDeclarationMariage() {
        String route = "";
        if(MyUtil.getProfil() != null){
        	if(marService.verifyValidate(this.decMarToConsult)){
		        this.decMarToConsult.setEtat("Valider");
		        this.decMarToConsult.setNumero_Acte(marService.numeroActe(this.decMarToConsult.getDate_creation().substring(6, 10)));
		        this.decMarToConsult.setDate_modification(Tools.getCurrentDateTime());
		        this.decMarToConsult.setValidateurMariage(MyUtil.getUserLogged());
		        if (marService.updateDeclarationMariage(this.decMarToConsult)) {
		        	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "La déclaration de mariage est validée avec succès!", null);
		        	FacesContext context = FacesContext.getCurrentInstance();
		            context.getCurrentInstance().addMessage(null, message);
		        	
		        	context.getExternalContext().getFlash().setKeepMessages(true);
		            route =   MyUtil.basePath() + "registre/mariage?faces-redirect=true";
		        }
		        else{
		        	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "La déclaration n'a pas pu être valider!", null);
		        	FacesContext context = FacesContext.getCurrentInstance();
		            context.getCurrentInstance().addMessage(null, message);
		        	
		        	context.getExternalContext().getFlash().setKeepMessages(true);
		        	route = MyUtil.basePath() +"/consultation/declaration/mariage?faces-redirect=true";
		        }
        	}
        	else{
        		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "La déclaration vient d'être validée!", null);
	            FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
	        	
	        	context.getExternalContext().getFlash().setKeepMessages(true);
        	}
        }
        else
        	route = MyUtil.pathLogin();
        return route;
    }

    @SuppressWarnings("static-access")
	public String rejectDeclarationNaissance() {
    	String route = "";
    	if(MyUtil.getProfil() != null){
        this.decToConsult.setEtat("Rejeter");
        this.decToConsult.setValidateurNaissance(MyUtil.getUserLogged());
        this.decToConsult.setDate_modification(Tools.getCurrentDateTime());
        if (acteService.updateDeclarationNaissance(this.decToConsult)) {
        	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "La déclaration de naissance est bien rejetée!", null);
        	FacesContext context = FacesContext.getCurrentInstance();
            context.getCurrentInstance().addMessage(null, message);
        	
        	context.getExternalContext().getFlash().setKeepMessages(true);
            route = MyUtil.basePath() +"/liste-declaration/naissance?faces-redirect=true";
        }
        else{
        	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "La déclaration n'a pas pu être rejeter!", null);
        	FacesContext context = FacesContext.getCurrentInstance();
            context.getCurrentInstance().addMessage(null, message);
        	
        	context.getExternalContext().getFlash().setKeepMessages(true);
            route = MyUtil.basePath() +"/consultation/declaration/naissance?faces-redirect=true";
        }
    	}
    	else
    		route =  MyUtil.pathLogin();
        //context.addCallbackParam("route", route);
        return route;
    }
    
    @SuppressWarnings("static-access")
	public String rejectDeclarationDece() {
        String route = "";
        if(MyUtil.getProfil() != null){
        this.decDCToConsult.setEtat("Rejeter");
        this.decDCToConsult.setDate_modification(Tools.getCurrentDateTime());
        this.decDCToConsult.setModificateurDeces(MyUtil.getUserLogged());
        if (deceService.updateDeclarationDeces(decDCToConsult)) {
        	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,"La déclaration de décès est bien rejetée!", null);
        	FacesContext context = FacesContext.getCurrentInstance();
            context.getCurrentInstance().addMessage(null, message);
        	
        	context.getExternalContext().getFlash().setKeepMessages(true);
            route = MyUtil.basePath() +"/liste-declaration/deces?faces-redirect=true";
        }
        else{
        	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "La déclaration n'a pas pu être rejeter!", null);
        	FacesContext context = FacesContext.getCurrentInstance();
            context.getCurrentInstance().addMessage(null, message);
        	
        	context.getExternalContext().getFlash().setKeepMessages(true);
            route = MyUtil.basePath() +"/consultation/declaration/deces?faces-redirect=true";
        }
        }
        else
        	route = MyUtil.pathLogin();
        //context.addCallbackParam("route", route);
        return route;
    }
    
    @SuppressWarnings("static-access")
	public String rejectDeclarationMariage() {
    	String route = "";
    	if(MyUtil.getProfil() != null){
        this.decMarToConsult.setEtat("Rejeter");
        this.decMarToConsult.setDate_modification(Tools.getCurrentDateTime());
        this.decMarToConsult.setModificateurMariage(MyUtil.getUserLogged());
        if (marService.updateDeclarationMariage(this.decMarToConsult)) {
        	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,  "La déclaration mariage est bien rejetée!", null);
        	FacesContext context = FacesContext.getCurrentInstance();
            context.getCurrentInstance().addMessage(null, message);
        	
        	context.getExternalContext().getFlash().setKeepMessages(true);
            route = MyUtil.basePath() +"/liste-declaration/mariage?faces-redirect=true";
        }
        else{
        	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "La déclaration n'a pas pu être rejeter!", null);
        	FacesContext context = FacesContext.getCurrentInstance();
            context.getCurrentInstance().addMessage(null, message);
        	
        	context.getExternalContext().getFlash().setKeepMessages(true);
            route = MyUtil.basePath() +"/consultation/declaration/mariage?faces-redirect=true";
        }
    	}
    	else
    		route = MyUtil.pathLogin();
            
        return route;
    }
    
    //Liste des méthodes pour effectuer les modifications des déclarations
    
    @SuppressWarnings("static-access")
	public String updateDeclarationNaissance() {
        String route = "";
        if(MyUtil.getProfil() != null){
        if("Jugement".equalsIgnoreCase(Tools.typeDeclarationNaissance(Tools.formatDay(this.decToConsult.getDate_naissanceE())))){
        	if("".equals(this.decToConsult.getNumero_jugement()) || "".equals(this.decToConsult.getDate_j()) || "".equals(this.decToConsult.getTribunal())){
        		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "Cette déclaration est un jugement. Remplir les champs correspondants!", null);
        		FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
        	}
        	else if(acteService.verifyNumeroJugement(this.decToConsult.getNumero_jugement(), Tools.formatDay(this.decToConsult.getDate_j()).substring(6, 10), this.decToConsult.getId())){
        		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "Ce numéro de jugement est déjà  attribué pour cette année.", null);
        		FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
        	}
        	else if((this.decToConsult.getDeclarant().equalsIgnoreCase("Autre")) && ("".equals(this.decToConsult.getDomicile_declarant()) || "".equals(this.decToConsult.getPrenom_declarant()) || "".equals(this.decToConsult.getNom_declarant()) || "".equals(this.decToConsult.getProfession_declarant()) || "".equals(this.decToConsult.getAdresse_declarant()) || "".equals(this.decToConsult.getNum_identification_declarant()))){
        		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,  "Veuillez saisir les informations du déclarant.", null);
        		FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
        	}
        	else{
        		this.decToConsult.setDate_naissance_enfant(Tools.formatDay(this.decToConsult.getDate_naissanceE()));
                this.decToConsult.setDate_naissance_mere(Tools.formatDay(this.decToConsult.getDate_naissanceM()));
                this.decToConsult.setDate_naissance_pere(Tools.formatDay(this.decToConsult.getDate_naissanceP()));
                this.decToConsult.setDate_jugement(Tools.formatDay(this.decToConsult.getDate_j()));
                
                this.decToConsult.setHeure_naissance_enfant(this.decToConsult.getHeure_naissanceE().toString().substring(11, 16));
                this.decToConsult.setType_declaration(Tools.typeDeclarationNaissance(Tools.formatDay(this.decToConsult.getDate_naissanceE())));
                
                
                if (acteService.updateActe(decToConsult)) {
                	route = MyUtil.basePath() +"/consultation/declaration/naissance?faces-redirect=true";
                	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  "La modification a été effectuée avec succès.", null);
                	FacesContext context = FacesContext.getCurrentInstance();
    	            context.getCurrentInstance().addMessage(null, message);
	            	
	            	context.getExternalContext().getFlash().setKeepMessages(true);
                }
                
                else{
                	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "Impossible de modifier la déclaration. Vérifiez toutes les informations saisies.", null);
                	FacesContext context = FacesContext.getCurrentInstance();
    	            context.getCurrentInstance().addMessage(null, message);
	            	
	            	context.getExternalContext().getFlash().setKeepMessages(true);
                }
                        	}
        	
        }
        else if((this.decToConsult.getDeclarant().equalsIgnoreCase("Autre")) && ("".equals(this.decToConsult.getDomicile_declarant()) || "".equals(this.decToConsult.getPrenom_declarant()) || "".equals(this.decToConsult.getNom_declarant()) || "".equals(this.decToConsult.getProfession_declarant()) || "".equals(this.decToConsult.getAdresse_declarant()) || "".equals(this.decToConsult.getNum_identification_declarant()))){
        	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,  "Veuillez saisir les informations du déclarant.", null);
        	FacesContext context = FacesContext.getCurrentInstance();
            context.getCurrentInstance().addMessage(null, message);
        	
        	context.getExternalContext().getFlash().setKeepMessages(true);
    	}
        else{
        	this.decToConsult.setDate_naissance_enfant(Tools.formatDay(this.decToConsult.getDate_naissanceE()));
            this.decToConsult.setDate_naissance_mere(Tools.formatDay(this.decToConsult.getDate_naissanceM()));
            this.decToConsult.setDate_naissance_pere(Tools.formatDay(this.decToConsult.getDate_naissanceP()));
            this.decToConsult.setDate_jugement(Tools.formatDay(this.decToConsult.getDate_j()));
            this.decToConsult.setHeure_naissance_enfant(this.decToConsult.getHeure_naissanceE().toString().substring(11, 16));
            this.decToConsult.setType_declaration(Tools.typeDeclarationNaissance(Tools.formatDay(this.decToConsult.getDate_naissanceE())));
            
            if (acteService.updateActe(decToConsult)) {
            	route = MyUtil.basePath() +"/consultation/declaration/naissance?faces-redirect=true";
            	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  "La modification a été effectuée avec succès.", null);
            	FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
            }
            else{
            	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "Impossible de modifier la déclaration. Vérifiez toutes les informations saisies.", null);
            	FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
            }
            
        }
        }
        else
        	route = MyUtil.pathLogin();
        return route;
    }
    
    @SuppressWarnings("static-access")
	public String updateDeclarationMariage() {
        String route = "";
        if(MyUtil.getProfil() != null){
        if("Jugement".equalsIgnoreCase(Tools.typeDeclarationMariage(Tools.formatDay(this.decMarToConsult.getDateMariage())))){
        	if("".equals(this.decMarToConsult.getNumero_Jugement()) || "".equals(this.decMarToConsult.getDateJugement()) || "".equals(this.decMarToConsult.getTribunal())){
        		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "Cette déclaration est un jugement. Remplir les champs correspondants!", null);
        		FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
        	}
        	else if(marService.verifyNumeroJugement(this.decMarToConsult.getNumero_Jugement(), this.decMarToConsult.getDateJugement().toString().substring(6, 10), this.decMarToConsult.getId())){
        		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "Ce numéro de jugement est déjà  attribué pour cette année.", null);
        		FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
        	}
        	else{
                this.decMarToConsult.setDate_Mariage(Tools.formatDay(this.decMarToConsult.getDateMariage()));
                this.decMarToConsult.setDate_Jugement(Tools.formatDay(this.decMarToConsult.getDateJugement()));
                this.decMarToConsult.setDate_Naissance_Epouse(Tools.formatDay(this.decMarToConsult.getDateNaissanceEpouse()));
                this.decMarToConsult.setDate_Naissance_Epoux(Tools.formatDay(this.decMarToConsult.getDateNaissanceMereEpoux()));
                this.decMarToConsult.setDate_Naissance_Mere_Epouse(Tools.formatDay(this.decMarToConsult.getDateNaissanceMereEpouse()));
                this.decMarToConsult.setDate_Naissance_Mere_Epoux(Tools.formatDay(this.decMarToConsult.getDateNaissanceMereEpoux()));
                this.decMarToConsult.setDate_Naissance_Pere_Epoux(Tools.formatDay(this.decMarToConsult.getDateNaissancePereEpoux()));
                this.decMarToConsult.setDate_Naissance_Pere_Epouse(Tools.formatDay(this.decMarToConsult.getDateNaissancePereEpouse()));
                this.decMarToConsult.setHeure_Mariage(this.decMarToConsult.getHeureMariage().toString().substring(11, 16));
                
                if (marService.updateDeclarationMariage(decMarToConsult)) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  "Les modifications ont été effectué avec succès.", null);
                    FacesContext context = FacesContext.getCurrentInstance();
    	            context.getCurrentInstance().addMessage(null, message);
	            	
	            	context.getExternalContext().getFlash().setKeepMessages(true);
                    route = MyUtil.basePath() +"/liste-declaration/mariage?faces-redirect=true";

                } 
                else {

                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "Les modifications n'ont pas été effecuté normalement.", null);
                    FacesContext context = FacesContext.getCurrentInstance();
    	            context.getCurrentInstance().addMessage(null, message);
	            	
	            	context.getExternalContext().getFlash().setKeepMessages(true);
                
                }
        	}
        }
        else{
            this.decMarToConsult.setDate_Mariage(Tools.formatDay(this.decMarToConsult.getDateMariage()));
            this.decMarToConsult.setDate_Jugement(Tools.formatDay(this.decMarToConsult.getDateJugement()));
            this.decMarToConsult.setDate_Naissance_Epouse(Tools.formatDay(this.decMarToConsult.getDateNaissanceEpouse()));
            this.decMarToConsult.setDate_Naissance_Epoux(Tools.formatDay(this.decMarToConsult.getDateNaissanceMereEpoux()));
            this.decMarToConsult.setDate_Naissance_Mere_Epouse(Tools.formatDay(this.decMarToConsult.getDateNaissanceMereEpouse()));
            this.decMarToConsult.setDate_Naissance_Mere_Epoux(Tools.formatDay(this.decMarToConsult.getDateNaissanceMereEpoux()));
            this.decMarToConsult.setDate_Naissance_Pere_Epoux(Tools.formatDay(this.decMarToConsult.getDateNaissancePereEpoux()));
            this.decMarToConsult.setDate_Naissance_Pere_Epouse(Tools.formatDay(this.decMarToConsult.getDateNaissancePereEpouse()));
            this.decMarToConsult.setHeure_Mariage(this.decMarToConsult.getHeureMariage().toString().substring(11, 16));
            
            if (marService.updateDeclarationMariage(decMarToConsult)) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  "Les modifications ont été effectué avec succès.", null);
                FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
                route = MyUtil.basePath() +"/liste-declaration/mariage?faces-redirect=true";

            } 
            else {

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "Les modifications n'ont pas été effecuté normalement.", null);
                FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
            
            }
        }
        }
        else
        	route = MyUtil.pathLogin();
                
        return route;
    }
    
    @SuppressWarnings("static-access")
	public String updateDeclarationDeces() {
        String route = "";
        if(MyUtil.getProfil() != null){
        if("Jugement".equalsIgnoreCase(Tools.typeDeclarationDeces(Tools.formatDay(this.decDCToConsult.getDate_d())))){
        	if("".equals(this.decDCToConsult.getNum_jugement()) || "".equals(this.decDCToConsult.getDate_j()) || "".equals(this.decDCToConsult.getTribunal())){
        		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "Cette déclaration est un jugement. Remplir les champs correspondants!", null);
        		FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
        	}
        	else if(deceService.verifyNumeroJugement(this.decDCToConsult.getNum_jugement(), Tools.formatDay(this.decDCToConsult.getDate_j()).substring(6, 10), this.decDCToConsult.getId())){
        		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "Ce numéro de jugement est déjé attribué pour cette année.", null);
        		FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
        	}
        	else if((this.decDCToConsult.getDegre_parente().equalsIgnoreCase("Autre")) && ("".equals(this.decDCToConsult.getAdresse_declarant()) || "".equals(this.decDCToConsult.getPrenom_declarant()) || "".equals(this.decDCToConsult.getNom_declarant()) || "".equals(this.decDCToConsult.getProfession_declarant()) || "".equals(this.decDCToConsult.getNum_identification_declarant()))){
        		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,  "Veuillez saisir les informations du déclarant.", null);
        		FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
        	}
        	else if((this.decDCToConsult.getDegre_parente().equalsIgnoreCase("Pére")) && ("".equals(this.decDCToConsult.getPrenom_pere()) || "".equals(this.decDCToConsult.getNom_pere()) || "".equals(this.decDCToConsult.getDomicile_pere()) || "".equals(this.decDCToConsult.getProfession_pere()))){
        		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,  "Veuillez saisir les informations du pére.", null);
        		FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
        	}
        	else if((this.decDCToConsult.getDegre_parente().equalsIgnoreCase("Mére")) && ("".equals(this.decDCToConsult.getPrenom_mere()) || "".equals(this.decDCToConsult.getNom_mere()) || "".equals(this.decDCToConsult.getDomicile_mere()) || "".equals(this.decDCToConsult.getProfession_mere()))){
        		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,  "Veuillez saisir les informations de la mére.", null);
        		FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
        	}
        	else{
        		this.decDCToConsult.setDate_deces(Tools.formatDay(this.decDCToConsult.getDate_d()));
                this.decDCToConsult.setDate_naissance_defunt(Tools.formatDay(this.decDCToConsult.getDate_naissanceDefunt()));
                this.decDCToConsult.setDate_jugement(Tools.formatDay(this.decDCToConsult.getDate_j()));
                this.decDCToConsult.setHeure_deces(this.decDCToConsult.getHeure_d().toString().substring(11, 16));
                
                
                if (deceService.updateDeclarationDeces(decDCToConsult)) {
                	
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  "La modification a été effectué avec succés!", null);
                    FacesContext context = FacesContext.getCurrentInstance();
    	            context.getCurrentInstance().addMessage(null, message);
	            	
	            	context.getExternalContext().getFlash().setKeepMessages(true);
                    route = MyUtil.basePath() +"/liste-declaration/deces?faces-redirect=true";

                } else {
                	
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "La modification n'a pas été effectué!", null);
                    FacesContext context = FacesContext.getCurrentInstance();
    	            context.getCurrentInstance().addMessage(null, message);
	            	
	            	context.getExternalContext().getFlash().setKeepMessages(true);
                }
        	}
        	
        }

    	else if((this.decDCToConsult.getDegre_parente().equalsIgnoreCase("Autre")) && ("".equals(this.decDCToConsult.getAdresse_declarant()) || "".equals(this.decDCToConsult.getPrenom_declarant()) || "".equals(this.decDCToConsult.getNom_declarant()) || "".equals(this.decDCToConsult.getProfession_declarant()) || "".equals(this.decDCToConsult.getNum_identification_declarant()))){
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,  "Veuillez saisir les informations du déclarant.", null);
    		FacesContext context = FacesContext.getCurrentInstance();
            context.getCurrentInstance().addMessage(null, message);
        	
        	context.getExternalContext().getFlash().setKeepMessages(true);
    	}
    	else if((this.decDCToConsult.getDegre_parente().equalsIgnoreCase("Pére")) && ("".equals(this.decDCToConsult.getPrenom_pere()) || "".equals(this.decDCToConsult.getNom_pere()) || "".equals(this.decDCToConsult.getDomicile_pere()) || "".equals(this.decDCToConsult.getProfession_pere()))){
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,  "Veuillez saisir les informations du pére.", null);
    		FacesContext context = FacesContext.getCurrentInstance();
            context.getCurrentInstance().addMessage(null, message);
        	
        	context.getExternalContext().getFlash().setKeepMessages(true);
    	}
    	else if((this.decDCToConsult.getDegre_parente().equalsIgnoreCase("Mére")) && ("".equals(this.decDCToConsult.getPrenom_mere()) || "".equals(this.decDCToConsult.getNom_mere()) || "".equals(this.decDCToConsult.getDomicile_mere()) || "".equals(this.decDCToConsult.getProfession_mere()))){
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,  "Veuillez saisir les informations de la mére.", null);
    		FacesContext context = FacesContext.getCurrentInstance();
            context.getCurrentInstance().addMessage(null, message);
        	
        	context.getExternalContext().getFlash().setKeepMessages(true);
    	}
        else{
        	this.decDCToConsult.setDate_deces(Tools.formatDay(this.decDCToConsult.getDate_d()));
            this.decDCToConsult.setDate_naissance_defunt(Tools.formatDay(this.decDCToConsult.getDate_naissanceDefunt()));
            this.decDCToConsult.setDate_jugement(Tools.formatDay(this.decDCToConsult.getDate_j()));
            this.decDCToConsult.setHeure_deces(this.decDCToConsult.getHeure_d().toString().substring(11, 16));
            
            
            if (deceService.updateDeclarationDeces(decDCToConsult)) {
            	
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  "La modification a été effectué avec succés!", null);
                FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
                route = MyUtil.basePath() +"/liste-declaration/deces?faces-redirect=true";

            } else {
            	
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "La modification n'a pas été effectué!", null);
                FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
            }
        }
        }
        else
        	route = MyUtil.pathLogin();
        return route;
    }
    
    public String updateDecNaissance(){
    	if(MyUtil.getProfil() != null)
    		return  MyUtil.pathModificationDeclaration()+"naissance?faces-redirect=true";
    	else
    		return MyUtil.pathLogin();
    }
    
    public String updateDecMariage(){
    	if(MyUtil.getProfil() != null)
    		return  MyUtil.pathModificationDeclaration()+"mariage?faces-redirect=true";
    	else
    		return MyUtil.pathLogin();
    }
    
    public String updateDecDeces(){
    	if(MyUtil.getProfil() != null)
    		return  MyUtil.pathModificationDeclaration()+"deces?faces-redirect=true";
    	else
    		return MyUtil.pathLogin();
    }
    
    //Fin de la liste des méthodes
    
    
    //Liste des méthodes pour les retours
    public String retourRegistreNaissance(){
    	if(MyUtil.getProfil() != null)
    		return MyUtil.basePath() +"/registre/naissance?faces-redirect=true";
    	else
    		return MyUtil.pathLogin();
    }
    
    public String retourRegistreMariage(){
    	if(MyUtil.getProfil() != null)
    		return MyUtil.basePath() +"/registre/mariage?faces-redirect=true";
    	else
    		return MyUtil.pathLogin();
    }
    
    public String retourRegistreDeces(){
    	if(MyUtil.getProfil() != null)
    		return MyUtil.basePath() +"/registre/dece?faces-redirect=true";
    	else
    		return MyUtil.pathLogin();
    }
    
    
    public String retourDeclarationNaissance(){
    	if(MyUtil.getProfil() != null)
    		return MyUtil.basePath() +"/liste-declaration/naissance?faces-redirect=true";
    	else
    		return MyUtil.pathLogin();
    }
    
    public String retourDeclarationMariage(){
    	if(MyUtil.getProfil() != null)
    		return MyUtil.basePath() +"/liste-declaration/mariage?faces-redirect=true";
    	else
    		return MyUtil.pathLogin();
    }
    
    public String retourDeclarationDeces(){
    	if(MyUtil.getProfil() != null)
    		return MyUtil.basePath() +"/liste-declaration/deces?faces-redirect=true";
    	else
    		return MyUtil.pathLogin();
    }
    
    
    
    
    
    @SuppressWarnings("unchecked")
  	public void printActeNaissance() throws JRException, IOException{
          String num=decToConsult.getNumero_acte();
  		char[] t=num.toCharArray();
  		String numero[] = {"0", "0", "0", "0", "0", "0"};
		int j= numero.length-1;
		for(int i = t.length-1; i >= 0; i--){
			numero[j] = Character.toString(t[i]);
			j--;
		}
		
		

		parameter.put("num1", numero[5]);
		parameter.put("num2", numero[4]);
		parameter.put("num3", numero[3]);
		parameter.put("num4", numero[2]);
		parameter.put("num5", numero[1]);
		parameter.put("num6", numero[0]);
  		
  		parameter.put("anneeChiffre", Tools.getYearForDateTime(decToConsult.getDate_creation()));
		parameter.put("numVolet", "");
		parameter.put("region", decToConsult.getCreateurNaissance().getCentre().getCenterRegion());
		parameter.put("departement", decToConsult.getCreateurNaissance().getCentre().getCenterDepartement());
		parameter.put("arrondissement", decToConsult.getCreateurNaissance().getCentre().getCenterArrondissement());
		parameter.put("commune", decToConsult.getCreateurNaissance().getCentre().getCenterCollectivite());
		parameter.put("type", decToConsult.getCreateurNaissance().getCentre().getCenterType());
		parameter.put("collectivite", decToConsult.getCreateurNaissance().getCentre().getCenterName());
		parameter.put("prenomEnfant", decToConsult.getPrenom_enfant());
		parameter.put("nomEnfant", decToConsult.getNom_enfant());
		parameter.put("sexeEnfant", decToConsult.getSexe());
		parameter.put("dateNaissEnfant",  Tools.getformatDate(decToConsult.getDate_naissance_enfant()));
		parameter.put("heureNaissEnfant", decToConsult.getHeure_naissance_enfant());
		parameter.put("lieuNaissEnfant", decToConsult.getLieu_naissance_enfant());
		parameter.put("formationSanitaire", decToConsult.getFormation_sanitaire());
		parameter.put("jourEnfant", Tools.getDayForDate(decToConsult.getDate_naissance_enfant()));
		parameter.put("moisEnfant", Tools.getMonthForDate(decToConsult.getDate_naissance_enfant()));
		parameter.put("anneeEnfant", Tools.getYearForDate(decToConsult.getDate_naissance_enfant()));
		parameter.put("prenomPere", decToConsult.getPrenom_pere());
		parameter.put("nomPere", decToConsult.getNom_pere());
		parameter.put("jourPere", Tools.getDayForDate(decToConsult.getDate_naissance_pere()));
		parameter.put("moisPere", Tools.getMonthForDate(decToConsult.getDate_naissance_pere()));
		parameter.put("anneePere", Tools.getYearForDate(decToConsult.getDate_naissance_pere()));
		parameter.put("dateNaissPere",  Tools.getformatDate(decToConsult.getDate_naissance_pere()));
		parameter.put("lieuNaissPere", decToConsult.getLieu_naissance_pere());
		parameter.put("professionPere", decToConsult.getProfession_pere());
		parameter.put("domicilePere", decToConsult.getDomicile_pere());
		parameter.put("prenomMere", decToConsult.getPrenom_mere());
		parameter.put("nomMere", decToConsult.getNom_mere());
		parameter.put("jourMere", Tools.getDayForDate(decToConsult.getDate_naissance_mere()));
		parameter.put("moisMere", Tools.getMonthForDate(decToConsult.getDate_naissance_mere()));
		parameter.put("anneeMere", Tools.getYearForDate(decToConsult.getDate_naissance_mere()));
		parameter.put("dateNaissMere", Tools.getformatDate(decToConsult.getDate_naissance_mere()));
		parameter.put("lieuNaissMere", decToConsult.getLieu_naissance_mere());
		parameter.put("professionMere", decToConsult.getProfession_mere());
		parameter.put("domicileMere", decToConsult.getDomicile_mere());
		if(decToConsult.getDeclarant().equalsIgnoreCase("Autre")) {	
		parameter.put("prenomDeclarant", decToConsult.getPrenom_declarant());
			parameter.put("nomDeclarant", decToConsult.getNom_declarant());
			parameter.put("professionDeclarant", decToConsult.getProfession_declarant());
			parameter.put("adresseDeclarant", decToConsult.getAdresse_declarant());
			parameter.put("cniDeclarant", decToConsult.getNum_identification_declarant());
			parameter.put("declarant", "");
		}
		else {
			parameter.put("prenomDeclarant", "");
			parameter.put("nomDeclarant", "");
			parameter.put("professionDeclarant", "");
			parameter.put("adresseDeclarant", "");
			parameter.put("cniDeclarant", "");
			parameter.put("declarant", decToConsult.getDeclarant());
		}
			
			
		
		if("".equals(decToConsult.getNumero_jugement()) || "".equals(this.decToConsult.getDate_jugement())){
			parameter.put("numJugement", "");
			parameter.put("dateJugement", "");
		}
		else{
			parameter.put("numJugement", decToConsult.getNumero_jugement());
			parameter.put("dateJugement", Tools.getformatDate(decToConsult.getDate_jugement()));

		}
		parameter.put("jourDeclaration", Tools.getDayForDateTime(decToConsult.getDate_creation()));
		parameter.put("moisDeclaration",  Tools.getMonthForDateTime(decToConsult.getDate_creation()));
		parameter.put("anneeDeclaration",  Tools.getYearForDateTime(decToConsult.getDate_creation()));
		parameter.put("dateDeclaration", Tools.getformatDate(decToConsult.getDate_creation()));
		parameter.put("dateInscrit", Tools.getCurrentDateDDMMYYYY());
		parameter.put("lieuInscrit", decToConsult.getCreateurNaissance().getCentre().getCenterName());
		if(decToConsult.getMention_marginale()==null){
			parameter.put("mentionsMarginales", "");
		}
		else{
			parameter.put("mentionsMarginales", decToConsult.getMention_marginale());
		}
		parameter.put("typeDeclaration", decToConsult.getType_declaration());
		
                  
		try {
			if(dService.addPiece(pService.findByCode(510))){       
		  		FacesContext context = FacesContext.getCurrentInstance();
		  		String reportSource = context.getExternalContext().getRealPath("ActeNPDF/acte/Acte.jasper");
		                 
		  		jasperPrint = JasperFillManager.fillReport(reportSource, parameter, new JREmptyDataSource());
		  		jasperPrint.setName("Acte de naissance "+this.decToConsult.getPrenom_enfant()+" "+this.decToConsult.getNom_enfant());
		  		HttpServletResponse httpServletResponse = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
		        servletOutputStream.close();
			}
			else{
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur d'ajout du document délivré!", null);
		        FacesContext.getCurrentInstance().addMessage(null, message);
			}
		} catch (JRException e) {
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur de chargement!", null);
	        FacesContext.getCurrentInstance().addMessage(null, message);
			e.printStackTrace();
		}
      }
    
    
    
    @SuppressWarnings("unchecked")
	public void printActeMariage() throws JRException, IOException{
    	parameter.put("annee", Tools.getYearForDateTime(decMarToConsult.getDate_creation()));
		parameter.put("anneeMariage", Tools.getYearForDate(decMarToConsult.getDate_Mariage()));
		parameter.put("anneeNaisEpouse", Tools.getYearForDate(decMarToConsult.getDate_Naissance_Epouse()));
		parameter.put("anneeNaisEpoux", Tools.getYearForDate(decMarToConsult.getDate_Naissance_Epoux()));
		parameter.put("anneeNaisMereEpouse", Tools.getYearForDate(decMarToConsult.getDate_Naissance_Mere_Epouse()));
		parameter.put("anneeNaisMereEpoux", Tools.getYearForDate(decMarToConsult.getDate_Naissance_Mere_Epoux()));
		parameter.put("anneeNaisPereEpouse", Tools.getYearForDate(decMarToConsult.getDate_Naissance_Pere_Epouse()));
		parameter.put("anneeNaisPereEpoux", Tools.getYearForDate(decMarToConsult.getDate_Naissance_Pere_Epoux()));
		parameter.put("arrondissement",decMarToConsult.getCreateurMariage().getCentre().getCenterArrondissement().toUpperCase());
		parameter.put("autoritePatternelle","");
		
		parameter.put("commune", decMarToConsult.getCreateurMariage().getCentre().getCenterCollectivite().toUpperCase());
		
		parameter.put("dateCreation",Tools.toHumanDate(decMarToConsult.getDate_creation()));
		parameter.put("dateJugement",decMarToConsult.getDate_Jugement());
		parameter.put("dateMariage",decMarToConsult.getDate_Mariage());
		parameter.put("dateMariageLettre",NombreEnLettre.convert(Tools.getDayForDateTime(decMarToConsult.getDate_creation()))+" "
				+Tools.getMoisLettre(decMarToConsult.getDate_Mariage())+" "
				+NombreEnLettre.convert(Tools.getYearForDate(decMarToConsult.getDate_Mariage())));
		parameter.put("dateNaissEpouse",decMarToConsult.getDate_Naissance_Epouse());
		parameter.put("dateNaissEpoux",decMarToConsult.getDate_Naissance_Epoux());
		parameter.put("dateLNMEpouse",decMarToConsult.getDate_Naissance_Mere_Epouse()+" à "+decMarToConsult.getLieu_Naissance_Mere_Epouse());
		parameter.put("dateLNMEpoux",decMarToConsult.getDate_Naissance_Mere_Epoux()+" à "+decMarToConsult.getLieu_Naissance_Mere_Epoux());
		parameter.put("dateLNPEpouse",decMarToConsult.getDate_Naissance_Pere_Epouse()+" à "+decMarToConsult.getLieu_Naissance_Pere_Epouse());
		parameter.put("dateLNPEpoux",decMarToConsult.getDate_Naissance_Pere_Epoux()+" à "+decMarToConsult.getLieu_Naissance_Pere_Epoux());
		parameter.put("departement",decMarToConsult.getCreateurMariage().getCentre().getCenterDepartement().toUpperCase());
		parameter.put("domicileEpouse",decMarToConsult.getDomicile_Epouse());
		parameter.put("domicileEpoux",decMarToConsult.getDomicile_Epoux());
		parameter.put("domicileMereEpouse",decMarToConsult.getDomicile_Mere_Epouse());
		parameter.put("domicileMereEpoux",decMarToConsult.getDomicile_Mere_Epoux());
		parameter.put("domicilePereEpouse",decMarToConsult.getDomicile_Pere_Epouse());
		parameter.put("domicilePereEpoux",decMarToConsult.getDomicile_Pere_Epoux());
		parameter.put("dot",decMarToConsult.getDot_Mariage());
		
		parameter.put("genre",decMarToConsult.getGenre_Mariage());
		
		parameter.put("heureMariage",decMarToConsult.getHeure_Mariage());
		
		parameter.put("infosOfficier",decMarToConsult.getValidateurMariage().getUserPrenom()+" "
				+decMarToConsult.getValidateurMariage().getUserNom());
		
		parameter.put("jourMariage",Tools.getDayForDate(decMarToConsult.getDate_Mariage()));
		parameter.put("jourNaisEpouse",Tools.getDayForDate(decMarToConsult.getDate_Naissance_Epouse()));
		parameter.put("jourNaisEpoux",Tools.getDayForDate(decMarToConsult.getDate_Naissance_Epoux()));
		parameter.put("jourNaisMereEpouse",Tools.getDayForDate(decMarToConsult.getDate_Naissance_Mere_Epouse()));
		parameter.put("jourNaisMereEpoux",Tools.getDayForDate(decMarToConsult.getDate_Naissance_Mere_Epoux()));
		parameter.put("jourNaisPereEpouse",Tools.getDayForDate(decMarToConsult.getDate_Naissance_Pere_Epouse()));
		parameter.put("jourNaisPereEpoux",Tools.getDayForDate(decMarToConsult.getDate_Naissance_Pere_Epoux()));
		
		parameter.put("lieuMariage",decMarToConsult.getLieu_Mariage());
		parameter.put("lieuNaissEpouse",decMarToConsult.getLieu_Naissance_Epouse());
		parameter.put("lieuNaissEpoux",decMarToConsult.getLieu_Naissance_Epoux());
		
		
		parameter.put("mentionMarginale",decMarToConsult.getMentions_Marginales());
		parameter.put("moisMariage",Tools.getMonthForDate(decMarToConsult.getDate_Mariage()));
		parameter.put("moisNaisEpouse",Tools.getMonthForDate(decMarToConsult.getDate_Naissance_Epouse()));
		parameter.put("moisNaisEpoux",Tools.getMonthForDate(decMarToConsult.getDate_Naissance_Epoux()));
		parameter.put("moisNaisMereEpouse",Tools.getMonthForDate(decMarToConsult.getDate_Naissance_Mere_Epouse()));
		parameter.put("moisNaisMereEpoux",Tools.getMonthForDate(decMarToConsult.getDate_Naissance_Mere_Epoux()));
		parameter.put("moisNaisPereEpouse",Tools.getMonthForDate(decMarToConsult.getDate_Naissance_Pere_Epouse()));
		parameter.put("moisNaisPereEpoux",Tools.getMonthForDate(decMarToConsult.getDate_Naissance_Pere_Epoux()));
		
		parameter.put("nomCentre",decMarToConsult.getCreateurMariage().getCentre().getCenterName().toUpperCase());
		parameter.put("nomEpouse",decMarToConsult.getNom_Epouse());
		parameter.put("nomEpoux",decMarToConsult.getNom_Epoux());
		parameter.put("nomMereEpouse",decMarToConsult.getNom_Mere_Epouse());
		parameter.put("nomMereEpoux",decMarToConsult.getNom_Mere_Epoux());
		parameter.put("nomPereEpouse",decMarToConsult.getNom_Pere_Epouse());
		parameter.put("nomPereEpoux",decMarToConsult.getNom_Pere_Epoux());
		String num=decMarToConsult.getNumero_Acte();
		char[] t=num.toCharArray();
		String numero[] = {"0", "0", "0", "0", "0", "0"};
		int j= numero.length-1;
		for(int i = t.length-1; i >= 0; i--){
			numero[j] = Character.toString(t[i]);
			j--;
		}
		
		

		parameter.put("num1", numero[5]);
		parameter.put("num2", numero[4]);
		parameter.put("num3", numero[3]);
		parameter.put("num4", numero[2]);
		parameter.put("num5", numero[1]);
		parameter.put("num6", numero[0]);
		
		parameter.put("numJugement", decMarToConsult.getNumero_Jugement());
		
		parameter.put("precedentConjoint", decMarToConsult.getPrecedent_Conjoint());
		parameter.put("prenomsEpouse", decMarToConsult.getPrenom_Epouse());
		parameter.put("prenomsEpoux", decMarToConsult.getPrenom_Epoux());
		parameter.put("prenomsMereEpouse", decMarToConsult.getPrenom_Mere_Epouse());
		parameter.put("prenomsMereEpoux", decMarToConsult.getPrenom_Mere_Epoux());
		parameter.put("prenomsPereEpouse", decMarToConsult.getPrenom_Pere_Epouse());
		parameter.put("prenomsPereEpoux", decMarToConsult.getPrenom_Pere_Epoux());
		parameter.put("professionEpouse", decMarToConsult.getProfession_Epouse());
		parameter.put("professionEpoux", decMarToConsult.getProfession_Epoux());
		parameter.put("professionMereEpouse", decMarToConsult.getProfession_Mere_Epouse());
		parameter.put("professionMereEpoux", decMarToConsult.getProfession_Mere_Epoux());
		parameter.put("professionPereEpouse", decMarToConsult.getProfession_Pere_Epouse());
		parameter.put("professionPereEpoux", decMarToConsult.getProfession_Pere_Epoux());
		
		parameter.put("regimeMatrimonial", decMarToConsult.getRegime_Mariage());
		parameter.put("region", decMarToConsult.getCreateurMariage().getCentre().getCenterRegion().toUpperCase());
		parameter.put("residenceEpouse", decMarToConsult.getResidence_Epouse());
		parameter.put("residenceEpoux", decMarToConsult.getResidence_Epoux());
		
		parameter.put("temoin1", decMarToConsult.getPrenom_Temoin1_Epoux()+" "
				+decMarToConsult.getNom_Temoin1_Epoux()+" "
				+decMarToConsult.getProfession_Temoin1_Epoux()+" "
				+decMarToConsult.getDomicile_Temoin1_Epoux()+" "
				+decMarToConsult.getCni_Temoin1_Epoux());
		parameter.put("temoin2", decMarToConsult.getPrenom_Temoin2_Epoux()+" "
				+decMarToConsult.getNom_Temoin2_Epoux()+" "
				+decMarToConsult.getProfession_Temoin2_Epoux()+" "
				+decMarToConsult.getDomicile_Temoin2_Epoux()+" "
				+decMarToConsult.getCni_Temoin2_Epoux());
		parameter.put("temoin3", decMarToConsult.getPrenom_Temoin1_Epouse()+" "
				+decMarToConsult.getNom_Temoin1_Epouse()+" "
				+decMarToConsult.getProfession_Temoin1_Epouse()+" "
				+decMarToConsult.getDomicile_Temoin1_Epouse()+" "
				+decMarToConsult.getCni_Temoin1_Epouse());
		parameter.put("temoin4", decMarToConsult.getPrenom_Temoin2_Epouse()+" "
				+decMarToConsult.getNom_Temoin2_Epouse()+" "
				+decMarToConsult.getProfession_Temoin2_Epouse()+" "
				+decMarToConsult.getDomicile_Temoin2_Epouse()+" "
				+decMarToConsult.getCni_Temoin2_Epouse());
		parameter.put("typeCentre", decMarToConsult.getCreateurMariage().getCentre().getCenterType());
		parameter.put("typeD", this.decMarToConsult.getType_Declaration());
		
		
        
        try {
			if(dService.addPiece(pService.findByCode(610))){       
				FacesContext context = FacesContext.getCurrentInstance();
				String reportSource = context.getExternalContext().getRealPath("ActeNPDF/acte/ActeMariage.jasper");

				jasperPrint = JasperFillManager.fillReport(reportSource, parameter, new JREmptyDataSource());
				jasperPrint.setName("Acte de mariage "+this.decMarToConsult.getPrenom_Epoux()+" "+this.decMarToConsult.getNom_Epoux()+" et "+this.decMarToConsult.getPrenom_Epouse()+" "+this.decMarToConsult.getNom_Epouse());
		  		HttpServletResponse httpServletResponse = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
		        servletOutputStream.close();
			}
			else{
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur d'ajout du document délivré!", null);
		        FacesContext.getCurrentInstance().addMessage(null, message);
			}
		} catch (JRException e) {
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur de chargement!", null);
	        FacesContext.getCurrentInstance().addMessage(null, message);
			e.printStackTrace();
		}
    }
    
    
    @SuppressWarnings("unchecked")
   	public void printActeDeces() throws JRException, IOException{

		String num=decDCToConsult.getNumero_acte();
		char[] t=num.toCharArray();
		String numero[] = {"0", "0", "0", "0", "0", "0"};
		int j= numero.length-1;
		for(int i = t.length-1; i >= 0; i--){
			numero[j] = Character.toString(t[i]);
			j--;
		}
		
		

		parameter.put("num1", numero[5]);
		parameter.put("num2", numero[4]);
		parameter.put("num3", numero[3]);
		parameter.put("num4", numero[2]);
		parameter.put("num5", numero[1]);
		parameter.put("num6", numero[0]);
		
		parameter.put("anneeChiffre", Tools.getYearForDateTime(decDCToConsult.getDate_creation()));
		parameter.put("numVolet", "");
		parameter.put("region", decDCToConsult.getCreateurDeces().getCentre().getCenterRegion().toUpperCase());
		parameter.put("departement", decDCToConsult.getCreateurDeces().getCentre().getCenterDepartement().toUpperCase());
		parameter.put("arrondissement", decDCToConsult.getCreateurDeces().getCentre().getCenterArrondissement().toUpperCase());
		parameter.put("commune", decDCToConsult.getCreateurDeces().getCentre().getCenterCollectivite().toUpperCase());
		parameter.put("type", decDCToConsult.getCreateurDeces().getCentre().getCenterType().toUpperCase());
		parameter.put("collectivite", decDCToConsult.getCreateurDeces().getCentre().getCenterName().toUpperCase());
		
		
		parameter.put("dateDeces", Tools.getformatDate(decDCToConsult.getDate_deces()));
		parameter.put("jourDeces", Tools.getDayForDate(decDCToConsult.getDate_deces()));
		parameter.put("moisDeces", Tools.getMonthForDate(decDCToConsult.getDate_deces()));
		parameter.put("anneeDeces", Tools.getYearForDate(decDCToConsult.getDate_deces()));
		parameter.put("heureDeces", decDCToConsult.getHeure_deces());
		parameter.put("lieuDeces", decDCToConsult.getLieu_deces());
		parameter.put("formationSanitaire", decDCToConsult.getFormation_sanitaire());
		
		
		
		//Informations du défunt
		parameter.put("prenomDefunt", decDCToConsult.getPrenom_defunt().toUpperCase());
		parameter.put("nomDefunt", decDCToConsult.getNom_defunt().toUpperCase());
		parameter.put("sexeDefunt", decDCToConsult.getSexe_defunt());
		parameter.put("dateNaissDefunt",  Tools.getformatDate(decDCToConsult.getDate_naissance_defunt()));
		parameter.put("lieuNaissDefunt", decDCToConsult.getLieu_naissance_defunt());
		parameter.put("jourEnfant", Tools.getDayForDate(decDCToConsult.getDate_naissance_defunt()));
		parameter.put("moisEnfant", Tools.getMonthForDate(decDCToConsult.getDate_naissance_defunt()));
		parameter.put("anneeEnfant", Tools.getYearForDate(decDCToConsult.getDate_naissance_defunt()));
		parameter.put("professionDefunt", decDCToConsult.getProfession_defunt());
		parameter.put("domicileDefunt", decDCToConsult.getDomicile_defunt());
		parameter.put("epouseDefunt", decDCToConsult.getMarie_a());
		
		
		//Information Pére du défunt
		parameter.put("prenomPere", decDCToConsult.getPrenom_pere());
		parameter.put("nomPere", decDCToConsult.getNom_pere());
		parameter.put("professionPere", decDCToConsult.getProfession_pere());
		parameter.put("domicilePere", decDCToConsult.getDomicile_pere());
		
		//Informations Mére du défunt
		parameter.put("prenomMere", decDCToConsult.getPrenom_mere());
		parameter.put("nomMere", decDCToConsult.getNom_mere());
		parameter.put("professionMere", decDCToConsult.getProfession_mere());
		parameter.put("domicileMere", decDCToConsult.getDomicile_mere());
		parameter.put("prenomDeclarant", decDCToConsult.getPrenom_declarant());
		parameter.put("nomDeclarant", decDCToConsult.getNom_declarant());
		parameter.put("professionDeclarant", decDCToConsult.getProfession_declarant());
		parameter.put("adresseDeclarant", decDCToConsult.getAdresse_declarant());
		parameter.put("degreParente", decDCToConsult.getDegre_parente());
		parameter.put("numJugement", decDCToConsult.getNum_jugement());
		parameter.put("dateJugement", decDCToConsult.getDate_jugement());
		parameter.put("jourDeclaration", Tools.getDayForDateTime(Tools.toHumanDate(decDCToConsult.getDate_creation())));
		parameter.put("moisDeclaration",  Tools.getMonthForDateTime(Tools.toHumanDate(decDCToConsult.getDate_creation())));
		parameter.put("anneeDeclaration",  Tools.getYearForDateTime(Tools.toHumanDate(decDCToConsult.getDate_creation())));
		parameter.put("dateDeclaration", Tools.getformatDate(Tools.toHumanDate(decDCToConsult.getDate_creation())));
		parameter.put("dateInscrit", Tools.getformatDate(Tools.getCurrentDateDDMMYYYY()));
		parameter.put("lieuInscrit", decDCToConsult.getCreateurDeces().getCentre().getCenterName());
		parameter.put("mentionsMarginales", decDCToConsult.getMention_marginale());
		parameter.put("typeDeclaration", decDCToConsult.getType_declaration());
		parameter.put("officier", decDCToConsult.getValidateurDeces().getUserPrenom()+" "
				+decDCToConsult.getValidateurDeces().getUserNom());
		
        try {
			if(dService.addPiece(pService.findByCode(710))){       
				FacesContext context = FacesContext.getCurrentInstance();
				String reportSource = context.getExternalContext().getRealPath("ActeNPDF/acte/ActeDeces.jasper");

				jasperPrint = JasperFillManager.fillReport(reportSource, parameter, new JREmptyDataSource());
				jasperPrint.setName("Acte de décès "+this.decDCToConsult.getPrenom_defunt()+" "+this.decDCToConsult.getNom_defunt());
		  		HttpServletResponse httpServletResponse = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
		        servletOutputStream.close();
			}
			else{
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur d'ajout du document délivré!", null);
		        FacesContext.getCurrentInstance().addMessage(null, message);
			}
		} catch (JRException e) {
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur de chargement!", null);
	        FacesContext.getCurrentInstance().addMessage(null, message);
			e.printStackTrace();
		}
    }
    
    
    @SuppressWarnings("unchecked")
	public void printExtraitNaissance() throws IOException, JRException {
		parameter.put("region","REGION  DE "+ decToConsult.getCreateurNaissance().getCentre().getCenterRegion().toUpperCase());
		parameter.put("depart", "DEPARTEMENT DE "+decToConsult.getCreateurNaissance().getCentre().getCenterDepartement().toUpperCase());
		
		if(!decToConsult.getCreateurNaissance().getCentre().getCenterArrondissement().equals(""))
			parameter.put("arrond", "ARRONDISSEMENT DE "+decToConsult.getCreateurNaissance().getCentre().getCenterArrondissement().toUpperCase());
		else
			parameter.put("arrond", "COMMUNE DE "+decToConsult.getCreateurNaissance().getCentre().getCommune().toUpperCase());
		parameter.put("typeCentre", decToConsult.getCreateurNaissance().getCentre().getCenterType().toUpperCase());
		parameter.put("nomCentre", decToConsult.getCreateurNaissance().getCentre().getCenterName().toUpperCase());
		parameter.put("anneeL",NombreEnLettre.convert(Tools.getYearForDateTime(decToConsult.getDate_creation())));
		parameter.put("annee",Tools.getYearForDateTime(decToConsult.getDate_creation()));
		parameter.put("numL", NombreEnLettre.convert(decToConsult.getNumero_acte()));
		parameter.put("num", decToConsult.getNumero_acte());
		String jour = NombreEnLettre.convert(Tools.getDayForDate(decToConsult.getDate_naissance_enfant()));
		if(jour.equalsIgnoreCase("un"))
			jour = "premier";
		parameter.put("dateNL", jour+
				" "+Tools.getMoisLettre(decToConsult.getDate_naissance_enfant())+
				" "+NombreEnLettre.convert(Tools.getYearForDate(decToConsult.getDate_naissance_enfant())));
		String heure = Tools.getHeures(decToConsult.getHeure_naissance_enfant())+"";
		String mn = Tools.getMinutes(decToConsult.getHeure_naissance_enfant())+"";
		if(Tools.getHeures(decToConsult.getHeure_naissance_enfant()) < 10)
			 heure = "0"+Tools.getHeures(decToConsult.getHeure_naissance_enfant());
		if(Tools.getMinutes(decToConsult.getHeure_naissance_enfant()) < 10)
			mn = "0"+Tools.getMinutes(decToConsult.getHeure_naissance_enfant());
		parameter.put("heure", heure);
		parameter.put("minute", mn);
		parameter.put("lieuN", decToConsult.getLieu_naissance_enfant().toUpperCase());
		parameter.put("sexe", decToConsult.getSexe());
		parameter.put("prenom", decToConsult.getPrenom_enfant().toUpperCase());
		parameter.put("nom",decToConsult.getNom_enfant().toUpperCase());
		parameter.put("prenomP", decToConsult.getPrenom_pere().toUpperCase());
		parameter.put("prenomM", decToConsult.getPrenom_mere().toUpperCase());
		parameter.put("nomM", decToConsult.getNom_mere().toUpperCase());
		if(decToConsult.getPays_naissance_enfanr() != null)
			parameter.put("pays", decToConsult.getPays_naissance_enfanr());
		else
			parameter.put("pays","");
		if("".equals(decToConsult.getNumero_jugement())){
			parameter.put("numJL","");
			parameter.put("numJ", "");
			parameter.put("dateI", "");
			parameter.put("anneeI", "");
			parameter.put("anneeJ", "");
			parameter.put("anneeJL", "");
			parameter.put("tribunal", "");
		}
		else{
			parameter.put("numJL", NombreEnLettre.convert(decToConsult.getNumero_jugement()));
			parameter.put("numJ", decToConsult.getNumero_jugement());
			parameter.put("dateI", decToConsult.getDate_creation().substring(0, 10));
			parameter.put("tribunal", decToConsult.getTribunal());
			parameter.put("anneeJL", NombreEnLettre.convert(Tools.getYearForDate(decToConsult.getDate_jugement())));
			parameter.put("anneeJ", Tools.getYearForDate(decToConsult.getDate_jugement()));
			parameter.put("anneeI", Tools.getYearForDateTime(decToConsult.getDate_creation()));

		}
		parameter.put("dateC", Tools.getCurrentDateDDMMYYYY());
		parameter.put("officier", decToConsult.getValidateurNaissance().getUserPrenom()+" "+decToConsult.getValidateurNaissance().getUserNom());
		if(decToConsult.getSexe().equalsIgnoreCase("Masculin"))
			parameter.put("sexeM", "M");
		else
			parameter.put("sexeM", "F");
		parameter.put("mention", decToConsult.getMention_marginale());
		
        
        try {
			if(dService.addPiece(pService.findByCode(520))){       
				FacesContext context = FacesContext.getCurrentInstance();

				String reportSource = context.getExternalContext().getRealPath("ActeNPDF/extrait/ExtraitNaissance.jasper");

				jasperPrint = JasperFillManager.fillReport(reportSource, parameter, new JREmptyDataSource());
				jasperPrint.setName("Extrait de naissance "+this.decToConsult.getPrenom_enfant()+" "+this.decToConsult.getNom_enfant());
		  		HttpServletResponse httpServletResponse = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
		        servletOutputStream.close();
			}
			else{
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur d'ajout du document délivré!", null);
		        FacesContext.getCurrentInstance().addMessage(null, message);
			}
		} catch (JRException e) {
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur de chargement!", null);
	        FacesContext.getCurrentInstance().addMessage(null, message);
			e.printStackTrace();
		}
	}
    
    
    @SuppressWarnings("unchecked")
	public void printExtraitDeces() throws IOException, JRException {
        
		parameter.put("region", decDCToConsult.getCreateurDeces().getCentre().getCenterRegion().toUpperCase());
   		parameter.put("departement", decDCToConsult.getCreateurDeces().getCentre().getCenterDepartement().toUpperCase());
   		if("".equals(decDCToConsult.getCreateurDeces().getCentre().getCenterArrondissement()))
			parameter.put("arrondissement", "ARRONDISSEMENT DE "+decToConsult.getCreateurNaissance().getCentre().getCenterArrondissement().toUpperCase());
		else
			parameter.put("arrondissement", "COMMUNE DE "+decDCToConsult.getCreateurDeces().getCentre().getCommune().toUpperCase());
   		parameter.put("typeCentre", decDCToConsult.getCreateurDeces().getCentre().getCenterType().toUpperCase());
   		parameter.put("nomCentre", decDCToConsult.getCreateurDeces().getCentre().getCenterName().toUpperCase());
   		parameter.put("numero", decDCToConsult.getNumero_acte());
   		parameter.put("annee", Tools.getYearForDateTime(decDCToConsult.getDate_creation()));
   		String jour = NombreEnLettre.convert(Tools.getDayForDate(decDCToConsult.getDate_deces()));
   		if(jour.equalsIgnoreCase("un"))
   			jour = "premier";
   		parameter.put("dateDecesEnLettre", jour+" "
   				+Tools.getMoisLettre(decDCToConsult.getDate_deces())+" "
   				+NombreEnLettre.convert(Tools.getYearForDate(decDCToConsult.getDate_deces())));
   		parameter.put("lieuDeces", decDCToConsult.getLieu_deces());
   		parameter.put("infosOfficier", decDCToConsult.getValidateurDeces().getUserPrenom()+" "
   				+decDCToConsult.getValidateurDeces().getUserNom());
   		jour = NombreEnLettre.convert(Tools.getDayForDateTime(decDCToConsult.getDate_creation()));
   		if(jour.equalsIgnoreCase("un"))
   			jour = "premier";
   		parameter.put("dateCreationEnLettre", jour+" "
   				+Tools.getMoisLettre(decDCToConsult.getDate_creation())+" "
   				+NombreEnLettre.convert(Tools.getYearForDateTime(decDCToConsult.getDate_creation())));
   		parameter.put("heureDeces", decDCToConsult.getHeure_deces().substring(0, 2));
   		parameter.put("minutesDeces", decDCToConsult.getHeure_deces().substring(3, 5));
   		parameter.put("defunt", decDCToConsult.getPrenom_defunt()+" "+decDCToConsult.getNom_defunt());
   		
   		if(decDCToConsult.getSexe_defunt().equalsIgnoreCase("masculin"))
   			parameter.put("sexeD","fils");
   		else
   			parameter.put("sexeD","fille");
   		
   		
   		parameter.put("dateND", decDCToConsult.getDate_naissance_defunt());
   		parameter.put("lieuND", decDCToConsult.getLieu_naissance_defunt());
   		parameter.put("pereD",decDCToConsult.getPrenom_pere()+" "+decDCToConsult.getNom_pere());
   		parameter.put("mereD",decDCToConsult.getPrenom_mere()+" "+decDCToConsult.getNom_mere());
   		parameter.put("anneeLettre", NombreEnLettre.convert(Tools.getYearForDateTime(decDCToConsult.getDate_creation())));
   		parameter.put("heureCreation", Tools.getCurrentDateTime().substring(11, 13));
   		parameter.put("minuteCreation", Tools.getCurrentDateTime().substring(14, 16));
   		parameter.put("dateCourant", Tools.getformatDate(Tools.getCurrentDateDDMMYYYY()));
   		
   		
        
        try {
			if(dService.addPiece(pService.findByCode(720))){       
				FacesContext context = FacesContext.getCurrentInstance();
				String reportSource = context.getExternalContext().getRealPath("ActeNPDF/extrait/Extrait_du_registre_acte_de_Deces.jasper");

				jasperPrint = JasperFillManager.fillReport(reportSource, parameter, new JREmptyDataSource());
		  		HttpServletResponse httpServletResponse = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
		        servletOutputStream.close();
			}
			else{
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur d'ajout du document délivré!", null);
		        FacesContext.getCurrentInstance().addMessage(null, message);
			}
		} catch (JRException e) {
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur de chargement!", null);
	        FacesContext.getCurrentInstance().addMessage(null, message);
			e.printStackTrace();
		}
	}
    
    @SuppressWarnings("unchecked")
   	public void printExtraitMariage() throws IOException, JRException {
       	
    	parameter.put("region","REGION DE "+ decMarToConsult.getCreateurMariage().getCentre().getCenterRegion().toUpperCase());
		parameter.put("depart", "DEPARTEMENT DE "+decMarToConsult.getCreateurMariage().getCentre().getCenterDepartement().toUpperCase());
		
		if(this.decMarToConsult.getCreateurMariage().getCentre().getCenterCollectivite().equalsIgnoreCase("Commune"))
			parameter.put("commune","COMMUNE DE "+ decMarToConsult.getCreateurMariage().getCentre().getCommune().toUpperCase());
		else
			parameter.put("commune", "COMMUNE D'ARRONDISSEMENT DE "+decMarToConsult.getCreateurMariage().getCentre().getCommune().toUpperCase());
		
		parameter.put("typeCentre", "CENTRE "+
				decMarToConsult.getCreateurMariage().getCentre().getCenterType().toUpperCase()
				+" DE "+
				this.decMarToConsult.getCreateurMariage().getCentre().getCenterName().toUpperCase());
		
		parameter.put("num", decMarToConsult.getNumero_Acte());
		parameter.put("annee", Tools.getYearForDateTime(decMarToConsult.getDate_creation()));
		parameter.put("numJ", decMarToConsult.getNumero_Jugement());
		parameter.put("dateJ", this.decMarToConsult.getDate_Jugement());
		parameter.put("tribunal", decMarToConsult.getTribunal());
		
		parameter.put("epoux", decMarToConsult.getPrenom_Epoux()+ " " + decMarToConsult.getNom_Epoux());
		parameter.put("profEpoux", decMarToConsult.getProfession_Epoux());
		parameter.put("dateNEpoux", decMarToConsult.getDate_Naissance_Epoux());
		parameter.put("lieuNEpoux", decMarToConsult.getLieu_Naissance_Epoux());
		parameter.put("pereEpoux", decMarToConsult.getPrenom_Pere_Epoux()+" "+decMarToConsult.getNom_Pere_Epoux());
		parameter.put("mereEpoux", decMarToConsult.getPrenom_Mere_Epoux()+" "+decMarToConsult.getNom_Mere_Epoux());
		
		parameter.put("epouse", decMarToConsult.getPrenom_Epouse()+ " " + decMarToConsult.getNom_Epouse());
		parameter.put("profEpouse", decMarToConsult.getProfession_Epouse());
		parameter.put("dateNEpouse", decMarToConsult.getDate_Naissance_Epouse());
		parameter.put("lieuNEpouse", decMarToConsult.getLieu_Naissance_Epouse());
		parameter.put("pereEpouse", decMarToConsult.getPrenom_Pere_Epouse()+" "+decMarToConsult.getNom_Pere_Epouse());
		parameter.put("mereEpouse", decMarToConsult.getPrenom_Mere_Epouse()+" "+decMarToConsult.getNom_Mere_Epouse());
		
		parameter.put("dateM", Tools.getformatDate(decMarToConsult.getDate_Mariage()));
		parameter.put("lieuM", decMarToConsult.getLieu_Mariage());
		parameter.put("genre", decMarToConsult.getGenre_Mariage());
		parameter.put("regime", decMarToConsult.getRegime_Mariage());
		parameter.put("officier", decMarToConsult.getValidateurMariage().getUserPrenom()+" "+decMarToConsult.getValidateurMariage().getUserNom());
		parameter.put("dateC", Tools.getCurrentDateDDMMYYYY());
		parameter.put("centre", decMarToConsult.getCreateurMariage().getCentre().getCenterType()
				+" de "+
				this.decMarToConsult.getCreateurMariage().getCentre().getCenterName());
		parameter.put("nomCentre", this.decMarToConsult.getCreateurMariage().getCentre().getCenterName());
		
		
        try {
			if(dService.addPiece(pService.findByCode(620))){       
				FacesContext context = FacesContext.getCurrentInstance();
				
				String reportSource = context.getExternalContext().getRealPath("ActeNPDF/extrait/ExtraitMariage.jasper");

				jasperPrint = JasperFillManager.fillReport(reportSource, parameter, new JREmptyDataSource());
				jasperPrint.setName("Extrait de mariage "+this.decMarToConsult.getPrenom_Epoux()+" "+this.decMarToConsult.getNom_Epoux()+" et "+this.decMarToConsult.getPrenom_Epouse()+" "+this.decMarToConsult.getNom_Epouse());
		  		HttpServletResponse httpServletResponse = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
		        servletOutputStream.close();
			}
			else{
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur d'ajout du document délivré!", null);
		        FacesContext.getCurrentInstance().addMessage(null, message);
			}
		} catch (JRException e) {
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur de chargement!", null);
	        FacesContext.getCurrentInstance().addMessage(null, message);
			e.printStackTrace();
		}
    }
    
    @SuppressWarnings("unchecked")
	public void printExtraitActeMariage() throws JRException, IOException {
    	parameter.put("region",decMarToConsult.getCreateurMariage().getCentre().getCenterRegion().toUpperCase());
		parameter.put("departement", decMarToConsult.getCreateurMariage().getCentre().getCenterDepartement().toUpperCase());
		parameter.put("arrondissement", decMarToConsult.getCreateurMariage().getCentre().getCenterArrondissement().toUpperCase());
		parameter.put("typeCentre", decMarToConsult.getCreateurMariage().getCentre().getCenterType().toUpperCase());
		parameter.put("nomCentre", this.decMarToConsult.getCreateurMariage().getCentre().getCenterName().toUpperCase());
		parameter.put("numero", decMarToConsult.getNumero_Acte());
		parameter.put("annee", Tools.getYearForDateTime(decMarToConsult.getDate_creation()));
		String jour = NombreEnLettre.convert(Tools.getDayForDate(this.decMarToConsult.getDate_Mariage()));
		if(jour.equalsIgnoreCase("un"))
			jour = "premier";
		parameter.put("dateMariageLettre", jour+" "
				+Tools.getMoisLettre(this.decMarToConsult.getDate_Mariage())+" "
				+NombreEnLettre.convert(Tools.getYearForDate(this.decMarToConsult.getDate_Mariage())));
		parameter.put("heures", Tools.getHeures(this.decMarToConsult.getHeure_Mariage())+"");
		parameter.put("minutes", Tools.getMinutes(this.decMarToConsult.getHeure_Mariage())+"");
		parameter.put("epoux", decMarToConsult.getPrenom_Epoux()+ " " + decMarToConsult.getNom_Epoux());
		parameter.put("profEpoux", decMarToConsult.getProfession_Epoux());
		parameter.put("dateNEpoux", decMarToConsult.getDate_Naissance_Epoux());
		parameter.put("lieuNEpoux", decMarToConsult.getLieu_Naissance_Epoux());
		parameter.put("ageEpoux", Tools.getAge(decMarToConsult.getDate_Naissance_Epoux())+" ans");
		parameter.put("domEpoux", decMarToConsult.getDomicile_Epoux());
		parameter.put("pereEpoux", decMarToConsult.getPrenom_Pere_Epoux()+" "+decMarToConsult.getNom_Pere_Epoux());
		parameter.put("mereEpoux", decMarToConsult.getPrenom_Mere_Epoux()+" "+decMarToConsult.getNom_Mere_Epoux());
		
		parameter.put("epouse", decMarToConsult.getPrenom_Epouse()+ " " + decMarToConsult.getNom_Epouse());
		parameter.put("profEpouse", decMarToConsult.getProfession_Epouse());
		parameter.put("dateNEpouse", decMarToConsult.getDate_Naissance_Epouse());
		parameter.put("lieuNEpouse", decMarToConsult.getLieu_Naissance_Epouse());
		parameter.put("ageEpouse", Tools.getAge(decMarToConsult.getDate_Naissance_Epouse())+" ans");
		parameter.put("domEpouse", decMarToConsult.getDomicile_Epouse());
		parameter.put("pereEpouse", decMarToConsult.getPrenom_Pere_Epouse()+" "+decMarToConsult.getNom_Pere_Epouse());
		parameter.put("mereEpouse", decMarToConsult.getPrenom_Mere_Epouse()+" "+decMarToConsult.getNom_Mere_Epouse());
		parameter.put("infosOfficier", decMarToConsult.getValidateurMariage().getUserPrenom()+" "+decMarToConsult.getValidateurMariage().getUserNom());
		parameter.put("dateCreation", Tools.getCurrentDateDDMMYYYY());
		if(decMarToConsult.getMentions_Marginales() != null)
			parameter.put("mentionsMarginales", decMarToConsult.getMentions_Marginales());
		else
			parameter.put("mentionsMarginales", "");
		parameter.put("mariage", "");
		parameter.put("reponse", "");
		
		
        try {
			if(dService.addPiece(pService.findByCode(720))){       
				FacesContext context = FacesContext.getCurrentInstance();
				
				String reportSource = context.getExternalContext().getRealPath("ActeNPDF/extrait/ExtraitActeMariage.jasper");

				jasperPrint = JasperFillManager.fillReport(reportSource, parameter, new JREmptyDataSource());
		  		HttpServletResponse httpServletResponse = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
		        servletOutputStream.close();
			}
			else{
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur d'ajout du document délivré!", null);
		        FacesContext.getCurrentInstance().addMessage(null, message);
			}
		} catch (JRException e) {
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur de chargement!", null);
	        FacesContext.getCurrentInstance().addMessage(null, message);
			e.printStackTrace();
		}
    
    }
    
    
    
	@SuppressWarnings("unchecked")
	public void printBulletinNaissance() throws IOException, JRException {
		parameter.put("region", "REGION DE "+decToConsult.getCreateurNaissance().getCentre().getCenterRegion().toUpperCase());
		parameter.put("depart", "DEPARTEMENT DE "+decToConsult.getCreateurNaissance().getCentre().getCenterDepartement().toUpperCase());
		if(decToConsult.getCreateurNaissance().getCentre().getCenterArrondissement() !=null)
			parameter.put("collect", "COMMUNE D'ARRONDISSEMENT DE "+decToConsult.getCreateurNaissance().getCentre().getCenterArrondissement().toUpperCase());
		else
			parameter.put("collect", "COMMUNE DE "+decToConsult.getCreateurNaissance().getCentre().getCommune().toUpperCase());
		
		parameter.put("typeCentre","CENTRE "+
				decToConsult.getCreateurNaissance().getCentre().getCenterType().toUpperCase()+
				" DE "+this.decToConsult.getCreateurNaissance().getCentre().getCenterName().toUpperCase());
		parameter.put("nomCentre", decToConsult.getCreateurNaissance().getCentre().getCenterName());
		parameter.put("num", decToConsult.getNumero_acte());
		parameter.put("annee", Tools.getYearForDateTime(this.decToConsult.getDate_creation()));
		parameter.put("heure", decToConsult.getHeure_naissance_enfant().substring(0,2));
		parameter.put("minute", decToConsult.getHeure_naissance_enfant().substring(3, 5));
		if(decToConsult.getSexe().equalsIgnoreCase("Masculin"))
			parameter.put("sexe", "Fils");
		else
			parameter.put("sexe", "Fille");
		
		String annee=NombreEnLettre.convert(Tools.getYearForDate(decToConsult.getDate_naissance_enfant()));
		String mois=Tools.getMoisLettre(decToConsult.getDate_naissance_enfant());
		String jour=NombreEnLettre.convert(Tools.getDayForDate(decToConsult.getDate_naissance_enfant()));
		
   		if(jour.equals("un"))
   			jour = "premier";
		parameter.put("dateNL", jour+" "+mois+" "+annee);
		
		parameter.put("lieuN", decToConsult.getLieu_naissance_enfant()+",");
		parameter.put("enfant", decToConsult.getPrenom_enfant());
		if(!"".equals(decToConsult.getPrenom_pere()) && !"".equals(decToConsult.getNom_pere())){
			parameter.put("pere", decToConsult.getPrenom_pere()+" "+decToConsult.getNom_pere());
		}
		else{
			parameter.put("pere", "");
		}
			parameter.put("mere", decToConsult.getPrenom_mere()+" "+decToConsult.getNom_mere());
		
		parameter.put("dateC",  Tools.getCurrentDateDDMMYYYY());
		parameter.put("officier",  this.decToConsult.getValidateurNaissance().getUserPrenom()+" "+this.decToConsult.getValidateurNaissance().getUserNom());

		
        try {
			if(dService.addPiece(pService.findByCode(530))){       
				FacesContext context = FacesContext.getCurrentInstance();
				
				String reportSource = context.getExternalContext().getRealPath("ActeNPDF/bulletin/BulletinNaissance.jasper");

				jasperPrint = JasperFillManager.fillReport(reportSource, parameter, new JREmptyDataSource());
		  		HttpServletResponse httpServletResponse = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
		        servletOutputStream.close();
			}
			else{
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur d'ajout du document délivré!", null);
		        FacesContext.getCurrentInstance().addMessage(null, message);
			}
		} catch (JRException e) {
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur de chargement!", null);
	        FacesContext.getCurrentInstance().addMessage(null, message);
			e.printStackTrace();
		}
	}
    
	
   	@SuppressWarnings("unchecked")
	public void printBulletinDeces() throws IOException, JRException {
   		parameter.put("region", "REGION DE "+decDCToConsult.getCreateurDeces().getCentre().getCenterRegion().toUpperCase());
   		parameter.put("depart", "DEPARTEMENT DE "+decDCToConsult.getCreateurDeces().getCentre().getCenterDepartement().toUpperCase());
   		
   		if(decDCToConsult.getCreateurDeces().getCentre().getCenterArrondissement() != null)
   			parameter.put("collect", "COMMUNE D'ARRONDISSEMENT DE "+decDCToConsult.getCreateurDeces().getCentre().getCenterArrondissement().toUpperCase());
   		else
   			parameter.put("collect", "COMMUNE DE "+decDCToConsult.getCreateurDeces().getCentre().getCommune().toUpperCase());
   		
   		parameter.put("typeCentre", "CENTRE "+decDCToConsult.getCreateurDeces().getCentre().getCenterType().toUpperCase()
   				+" DE "+decDCToConsult.getCreateurDeces().getCentre().getCenterName().toUpperCase());
   		parameter.put("nomCentre", decDCToConsult.getCreateurDeces().getCentre().getCenterName().toUpperCase());
   		parameter.put("num", decDCToConsult.getNumero_acte()+"");
   		parameter.put("annee", Tools.getYearForDateTime(this.decDCToConsult.getDate_creation()));
   		String jour = NombreEnLettre.convert(Tools.getDayForDate(decDCToConsult.getDate_deces()));
   		if(jour.equalsIgnoreCase("un"))
   			jour = "premier";
   		parameter.put("dateDL", jour+" "
   				+Tools.getMoisLettre(decDCToConsult.getDate_deces())+" "
   				+NombreEnLettre.convert(Tools.getYearForDate(decDCToConsult.getDate_deces())));
   		parameter.put("lieuD", decDCToConsult.getLieu_deces());
   		parameter.put("defunt", decDCToConsult.getPrenom_defunt()+" "+decDCToConsult.getNom_defunt());
   		parameter.put("age", Tools.getAge(decDCToConsult.getDate_naissance_defunt())+" ans");
   		
   		parameter.put("lieuN", decDCToConsult.getLieu_naissance_defunt());
   		parameter.put("dateN", Tools.getformatDate(decDCToConsult.getDate_naissance_defunt()));
   		
   		
   		parameter.put("pays", decDCToConsult.getPays_deces());
   		if(decDCToConsult.getSexe_defunt().equalsIgnoreCase("masculin"))
   			parameter.put("sexe", "Fils");
   		else
   			parameter.put("sexe", "Fille");
   		parameter.put("pere", decDCToConsult.getPrenom_pere()+" "+decDCToConsult.getNom_pere());
   		parameter.put("mere", decDCToConsult.getPrenom_mere()+" "+decDCToConsult.getNom_mere());
   		parameter.put("officier", decDCToConsult.getValidateurDeces().getUserPrenom()+" "+decDCToConsult.getValidateurDeces().getUserNom());
   		
   		parameter.put("dateC", Tools.getCurrentDateDDMMYYYY());
   		
   		
        try {
			if(dService.addPiece(pService.findByCode(730))){       
				FacesContext context = FacesContext.getCurrentInstance();
				String reportSource = context.getExternalContext().getRealPath("ActeNPDF/bulletin/BulletinDeces.jasper");
				
				jasperPrint = JasperFillManager.fillReport(reportSource, parameter, new JREmptyDataSource());
		  		HttpServletResponse httpServletResponse = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
		        servletOutputStream.close();
			}
			else{
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur d'ajout du document délivré!", null);
		        FacesContext.getCurrentInstance().addMessage(null, message);
			}
		} catch (JRException e) {
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur de chargement!", null);
	        FacesContext.getCurrentInstance().addMessage(null, message);
			e.printStackTrace();
		}
   	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws JRException
	 */
	@SuppressWarnings("unchecked")
	public void printCopieLitteraleNaissance() throws IOException, JRException {
		parameter.put("region", "REGION DE "+decToConsult.getCreateurNaissance().getCentre().getCenterRegion().toUpperCase());
		parameter.put("depart", "DEPARTEMENT DE "+decToConsult.getCreateurNaissance().getCentre().getCenterDepartement().toUpperCase());
		parameter.put("commune", decToConsult.getCreateurNaissance().getCentre().getCenterCollectivite().toUpperCase()+" DE "+
				decToConsult.getCreateurNaissance().getCentre().getCommune().toUpperCase());
		parameter.put("typeCentre", "CENTRE "+decToConsult.getCreateurNaissance().getCentre().getCenterType().toUpperCase()
				+" DE "+decToConsult.getCreateurNaissance().getCentre().getCenterName().toUpperCase());
		parameter.put("nomCentre", decToConsult.getCreateurNaissance().getCentre().getCenterName().toUpperCase());
		parameter.put("num", decToConsult.getNumero_acte());
		String jour = NombreEnLettre.convert(Tools.getDayForDate(decToConsult.getDate_naissance_enfant()));
		if(jour.equalsIgnoreCase("un"))
			jour = "premier";
		parameter.put("dateNL", jour+" "
				+Tools.getMoisLettre(decToConsult.getDate_naissance_enfant())+" "
				+NombreEnLettre.convert(Tools.getYearForDateTime(decToConsult.getDate_naissance_enfant())));
		parameter.put("anneeL",  NombreEnLettre.convert(Tools.getYearForDateTime(decToConsult.getDate_naissance_enfant())));
		parameter.put("annee",  Tools.getYearForDateTime(decToConsult.getDate_creation()));
		parameter.put("dateN", Tools.getformatDate(decToConsult.getDate_naissance_enfant()));
		parameter.put("prenom",  decToConsult.getPrenom_enfant());
		parameter.put("nom",  decToConsult.getNom_enfant());
		parameter.put("heure", decToConsult.getHeure_naissance_enfant().substring(0, 2));
		parameter.put("minute", decToConsult.getHeure_naissance_enfant().substring(3, 5));
		parameter.put("lieuN", decToConsult.getLieu_naissance_enfant());
		parameter.put("sexe", decToConsult.getSexe());
		if(decToConsult.getSexe().equalsIgnoreCase("Masculin"))
			parameter.put("sexe2", "Fils");
		else
			parameter.put("sexe2", "Fille");
		if(!"".equals(decToConsult.getPrenom_pere()) && !"".equals(decToConsult.getNom_pere())){
			parameter.put("pere", decToConsult.getPrenom_pere()+" "+decToConsult.getNom_pere()+"  né le "+decToConsult.getDate_naissance_pere());
			parameter.put("infosPere","à "+decToConsult.getLieu_naissance_pere()+", "
					+decToConsult.getProfession_pere()+" et domicilié à "+decToConsult.getDomicile_pere());
		}
		else{
			parameter.put("infosPere", "");
			parameter.put("pere", "");
		}
		if(!"".equals(decToConsult.getPrenom_mere()) && !"".equals(decToConsult.getNom_mere())){
			parameter.put("mere",  decToConsult.getPrenom_mere()+" "
					+decToConsult.getNom_mere()+"  née le "+decToConsult.getDate_naissance_mere());
			parameter.put("infosMere", "à "
					+decToConsult.getLieu_naissance_mere()+", "+decToConsult.getProfession_mere()+" et domiciliée à "
					+decToConsult.getDomicile_mere());
		}
		else{
			parameter.put("infosMere", "");
			parameter.put("mere", "");
		}

		if(decToConsult.getDeclarant().equalsIgnoreCase("Autre")){
			parameter.put("declarant", decToConsult.getPrenom_declarant()+" "+decToConsult.getNom_declarant());
			parameter.put("infosDeclarant"," de numéro de carte nationale d'identité "+decToConsult.getNum_identification_declarant());
		}
		else{
			parameter.put("declarant", decToConsult.getDeclarant());
			parameter.put("infosDeclarant", "");
		}
		if( decToConsult.getMention_marginale() !=null){
			parameter.put("mention", decToConsult.getMention_marginale());
		}
		else{
			parameter.put("mention", "");
		}
		parameter.put("centre", decToConsult.getCreateurNaissance().getCentre().getCenterType()+" de "+decToConsult.getCreateurNaissance().getCentre().getCenterName());
		parameter.put("officier", decToConsult.getValidateurNaissance().getUserPrenom()+" "+decToConsult.getValidateurNaissance().getUserNom());
		parameter.put("heureC", Tools.getCurrentDateTime().substring(11,13));
		parameter.put("minuteC", Tools.getCurrentDateTime().substring(14,16));
		jour = NombreEnLettre.convert(Tools.getDayForDate(Tools.getCurrentDate()));
		if(jour.equalsIgnoreCase("un"))
			jour = "premier";
		parameter.put("dateCL", jour+" "+Tools.getMoisLettre(Tools.getCurrentDate())+" "+NombreEnLettre.convert(Tools.getYearForDate(Tools.getCurrentDate())));

		
        
        try {
			if(dService.addPiece(pService.findByCode(540))){       
				FacesContext context = FacesContext.getCurrentInstance();

				String reportSource = context.getExternalContext().getRealPath("ActeNPDF/copie/CopieLitteraleNaissance.jasper");

				jasperPrint = JasperFillManager.fillReport(reportSource, parameter, new JREmptyDataSource());
		  		HttpServletResponse httpServletResponse = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
		        servletOutputStream.close();
			}
			else{
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur d'ajout du document délivré!", null);
		        FacesContext.getCurrentInstance().addMessage(null, message);
			}
		} catch (JRException e) {
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur de chargement!", null);
	        FacesContext.getCurrentInstance().addMessage(null, message);
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void printCopieLitteraleMariage() throws IOException, JRException {
		parameter.put("region","REGION DE "+ decMarToConsult.getCreateurMariage().getCentre().getCenterRegion().toUpperCase());
		parameter.put("depart", "DEPARTEMENT DE "+decMarToConsult.getCreateurMariage().getCentre().getCenterDepartement().toUpperCase());
		
		if(this.decMarToConsult.getCreateurMariage().getCentre().getCenterCollectivite().equalsIgnoreCase("Commune"))
			parameter.put("commune","COMMUNE DE "+ decMarToConsult.getCreateurMariage().getCentre().getCommune().toUpperCase());
		else
			parameter.put("commune", "COMMUNE D'ARRONDISSEMENT DE "+decMarToConsult.getCreateurMariage().getCentre().getCommune().toUpperCase());
		
		parameter.put("typeCentre", "CENTRE "+
				decMarToConsult.getCreateurMariage().getCentre().getCenterType().toUpperCase()
				+" DE "+
				this.decMarToConsult.getCreateurMariage().getCentre().getCenterName().toUpperCase());
		
		parameter.put("num", decMarToConsult.getNumero_Acte());
		parameter.put("annee", Tools.getYearForDateTime(decMarToConsult.getDate_creation()));
		String jour = NombreEnLettre.convert(Tools.getDayForDate(decMarToConsult.getDate_Mariage()));
		if(jour.equalsIgnoreCase("un"))
			jour = "premier";
		parameter.put("dateML", jour+" "
				+Tools.getMoisLettre(decMarToConsult.getDate_Mariage())+" "
				+NombreEnLettre.convert(Tools.getYearForDate(decMarToConsult.getDate_Mariage())));
		parameter.put("heureM",decMarToConsult.getHeure_Mariage().substring(0, 2));
		parameter.put("minuteM",decMarToConsult.getHeure_Mariage().substring(3, 5));
		parameter.put("nomOff", decMarToConsult.getValidateurMariage().getUserNom());
		parameter.put("prenomOff",decMarToConsult.getValidateurMariage().getUserPrenom());
		
		parameter.put("centre", decMarToConsult.getCreateurMariage().getCentre().getCenterType()
				+" de "+
				this.decMarToConsult.getCreateurMariage().getCentre().getCenterName());
		
		parameter.put("epoux", decMarToConsult.getPrenom_Epoux()+" "
				+decMarToConsult.getNom_Epoux());
		parameter.put("profEpoux", decMarToConsult.getProfession_Epoux());
		parameter.put("dateNEpoux", decMarToConsult.getDate_Naissance_Epoux());
		parameter.put("lieuNEpoux", decMarToConsult.getLieu_Naissance_Epoux());
		parameter.put("exEpouse", decMarToConsult.getPrecedent_Conjoint());
		parameter.put("domEpoux", decMarToConsult.getDomicile_Epoux());
		parameter.put("residEpoux", decMarToConsult.getResidence_Epoux());
		
		parameter.put("pereEpoux", decMarToConsult.getPrenom_Pere_Epoux()+" "+decMarToConsult.getNom_Pere_Epoux());
		parameter.put("profPEpoux", decMarToConsult.getProfession_Pere_Epoux());
		parameter.put("dateNPEpoux", decMarToConsult.getDate_Naissance_Pere_Epoux());
		parameter.put("lieuNPEpoux", decMarToConsult.getLieu_Naissance_Pere_Epoux());
		parameter.put("domPEpoux", decMarToConsult.getDomicile_Pere_Epoux());
		
		parameter.put("mereEpoux", decMarToConsult.getPrenom_Mere_Epoux()+" "+decMarToConsult.getNom_Mere_Epoux());
		parameter.put("profMEpoux", decMarToConsult.getProfession_Mere_Epoux());
		parameter.put("dateNMEpoux", decMarToConsult.getDate_Naissance_Mere_Epoux());
		parameter.put("lieuNMEpoux", decMarToConsult.getLieu_Naissance_Mere_Epoux());
		parameter.put("domMEpoux", decMarToConsult.getDomicile_Mere_Epoux());
		
		
		parameter.put("epouse", decMarToConsult.getPrenom_Epouse()+" "
				+decMarToConsult.getNom_Epouse());
		parameter.put("profEpouse", decMarToConsult.getProfession_Epouse());
		parameter.put("dateNEpouse", decMarToConsult.getDate_Naissance_Epouse());
		parameter.put("lieuNEpouse", decMarToConsult.getLieu_Naissance_Epouse());
		parameter.put("domEpouse", decMarToConsult.getDomicile_Epouse());
		parameter.put("residEpouse", decMarToConsult.getResidence_Epouse());
		parameter.put("exEpoux","");
		
		parameter.put("pereEpouse", decMarToConsult.getPrenom_Pere_Epouse()+" "
				+decMarToConsult.getNom_Pere_Epouse());
		parameter.put("profPEpouse", decMarToConsult.getProfession_Pere_Epouse());
		parameter.put("dateNPEpouse", decMarToConsult.getDate_Naissance_Pere_Epouse());
		parameter.put("lieuNPEpouse", decMarToConsult.getLieu_Naissance_Pere_Epouse());
		parameter.put("domPEpouse", decMarToConsult.getDomicile_Pere_Epouse());
		
		parameter.put("mereEpouse", decMarToConsult.getPrenom_Mere_Epouse()+" "+decMarToConsult.getNom_Mere_Epouse());
		parameter.put("profMEpouse", decMarToConsult.getProfession_Mere_Epouse());
		parameter.put("dateNMEpouse", decMarToConsult.getDate_Naissance_Mere_Epouse());
		parameter.put("lieuNMEpouse", decMarToConsult.getLieu_Naissance_Mere_Epouse());
		parameter.put("domMEpouse", decMarToConsult.getDomicile_Mere_Epouse());
		
		parameter.put("dot",decMarToConsult.getDot_Mariage());
		parameter.put("genre",decMarToConsult.getGenre_Mariage());
		parameter.put("regime",decMarToConsult.getRegime_Mariage());
		
		parameter.put("temoin1", decMarToConsult.getPrenom_Temoin1_Epoux()+" "+decMarToConsult.getNom_Temoin1_Epoux()+" - "+decMarToConsult.getProfession_Temoin1_Epoux()+" - "+decMarToConsult.getDomicile_Temoin1_Epoux()+" - "+decMarToConsult.getCni_Temoin1_Epoux());
		parameter.put("temoin2", decMarToConsult.getPrenom_Temoin1_Epouse()+" "+decMarToConsult.getNom_Temoin1_Epouse()+" - "+decMarToConsult.getProfession_Temoin1_Epouse()+" - "+decMarToConsult.getDomicile_Temoin1_Epouse()+" - "+decMarToConsult.getCni_Temoin1_Epouse());
		
		jour = NombreEnLettre.convert(Tools.getDayForDate(Tools.getCurrentDateDDMMYYYY()));
		if(jour.equalsIgnoreCase("un"))
			jour = "premier";
		parameter.put("dateCL", jour+" "
				+Tools.getMoisLettre(Tools.getCurrentDateDDMMYYYY())+" "
				+NombreEnLettre.convert(Tools.getYearForDate(Tools.getCurrentDateDDMMYYYY())));
		parameter.put("officier",decMarToConsult.getValidateurMariage().getUserPrenom()+" "
				+decMarToConsult.getValidateurMariage().getUserNom());
		
		
		
        
        try {
			if(dService.addPiece(pService.findByCode(620))){       
				FacesContext context = FacesContext.getCurrentInstance();
				String reportSource = context.getExternalContext().getRealPath("ActeNPDF/copie/CopieLitteraleMariage.jasper");

				jasperPrint = JasperFillManager.fillReport(reportSource, parameter, new JREmptyDataSource());
		  		HttpServletResponse httpServletResponse = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
		        servletOutputStream.close();
			}
			else{
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur d'ajout du document délivré!", null);
		        FacesContext.getCurrentInstance().addMessage(null, message);
			}
		} catch (JRException e) {
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur de chargement!", null);
	        FacesContext.getCurrentInstance().addMessage(null, message);
			e.printStackTrace();
		}
	}
	
	
  	@SuppressWarnings("unchecked")
	public void printCopieLitteraleDeces() throws IOException, JRException {
    
   		parameter.put("region", decDCToConsult.getCreateurDeces().getCentre().getCenterRegion().toUpperCase());
   		parameter.put("departement", decDCToConsult.getCreateurDeces().getCentre().getCenterDepartement().toUpperCase());
   		parameter.put("arrond", decDCToConsult.getCreateurDeces().getCentre().getCenterArrondissement().toUpperCase());
   		parameter.put("typecentre", decDCToConsult.getCreateurDeces().getCentre().getCenterType().toUpperCase());
   		parameter.put("nomcentre", decDCToConsult.getCreateurDeces().getCentre().getCenterName().toUpperCase());
   		parameter.put("numActe", decDCToConsult.getNumero_acte());
   		parameter.put("annee", Tools.getYearForDateTime(decDCToConsult.getDate_creation()));
   		parameter.put("dateDeces", Tools.getformatDate(decDCToConsult.getDate_deces()));
   		parameter.put("prenomDefunt", decDCToConsult.getPrenom_defunt());
   		parameter.put("nomDefunt", decDCToConsult.getNom_defunt());
   		String hh = Tools.getHeures(decDCToConsult.getHeure_deces())+"";
   		if(Tools.getHeures(decDCToConsult.getHeure_deces()) < 10)
   			hh = "0"+Tools.getHeures(decDCToConsult.getHeure_deces());
   		parameter.put("heures", hh);
   		parameter.put("sexe", decDCToConsult.getSexe_defunt());
   		parameter.put("lieuDeces", decDCToConsult.getLieu_deces());
   		parameter.put("mentions", decDCToConsult.getMention_marginale());
   		parameter.put("prenomAutorite", decDCToConsult.getValidateurDeces().getUserPrenom());
   		parameter.put("nomAutorite", decDCToConsult.getValidateurDeces().getUserNom());
   		parameter.put("officier", decDCToConsult.getValidateurDeces().getUserPrenom()+" "+decDCToConsult.getValidateurDeces().getUserNom());
   		parameter.put("heureCreation", Tools.getCurrentDateTime().substring(11, 13));
   		parameter.put("minuteCreation", Tools.getCurrentDateTime().substring(14, 16));
   		parameter.put("infosPere", decDCToConsult.getPrenom_pere()+" "+decDCToConsult.getNom_pere());
   		parameter.put("infosMere", decDCToConsult.getPrenom_mere()+" "+decDCToConsult.getNom_mere());
   		parameter.put("centre", decDCToConsult.getCreateurDeces().getCentre().getCenterType()+" de "+decDCToConsult.getCreateurDeces().getCentre().getCenterName());
   		parameter.put("declarant", decDCToConsult.getPrenom_declarant()+" "+decDCToConsult.getNom_declarant());
   		String mn = Tools.getMinutes(decDCToConsult.getHeure_deces())+"";
   		if(Tools.getMinutes(decDCToConsult.getHeure_deces()) < 10)
   			mn = "0"+Tools.getMinutes(decDCToConsult.getHeure_deces());
   		parameter.put("minutes", mn);
   		parameter.put("declarant2", "habitant à "+decDCToConsult.getAdresse_declarant()+", degrés de parenté :"+decDCToConsult.getDegre_parente());
   		if(decDCToConsult.getSexe_defunt().equalsIgnoreCase("masculin"))
   			parameter.put("genre", "Fils");
   		else
   			parameter.put("genre", "Fille");
   		String jour = NombreEnLettre.convert(Tools.getDayForDate(decDCToConsult.getDate_deces()));
   		if(jour.equals("un"))
   			jour = "premier";
   		parameter.put("dateDecesEnLettre", jour+" "
   				+Tools.getMoisLettre(decDCToConsult.getDate_deces())+" "
   				+NombreEnLettre.convert(Tools.getYearForDate(decDCToConsult.getDate_deces())));
   		jour =  NombreEnLettre.convert(Tools.getDayForDate(Tools.getCurrentDateDDMMYYYY()));
   		if(jour.equals("un"))
   			jour = "premier";
   		parameter.put("dateCreationEnLettre",jour+" "
   				+Tools.getMoisLettre(Tools.getCurrentDateDDMMYYYY())+" "
   				+NombreEnLettre.convert(Tools.getYearForDate(Tools.getCurrentDateDDMMYYYY())));
   		
   		
        
        try {
			if(dService.addPiece(pService.findByCode(740))){       

		   		FacesContext context = FacesContext.getCurrentInstance();
				String reportSource = context.getExternalContext().getRealPath("ActeNPDF/copie/CopieLitteraleDeces.jasper");

				jasperPrint = JasperFillManager.fillReport(reportSource, parameter, new JREmptyDataSource());
		  		HttpServletResponse httpServletResponse = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
		        servletOutputStream.close();
			}
			else{
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur d'ajout du document délivré!", null);
		        FacesContext.getCurrentInstance().addMessage(null, message);
			}
		} catch (JRException e) {
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur de chargement!", null);
	        FacesContext.getCurrentInstance().addMessage(null, message);
			e.printStackTrace();
		}
   	}

	public DeclarationNaissance getFilterNaissance() {
		return filterNaissance;
	}

	public void setFilterNaissance(DeclarationNaissance filterNaissance) {
		this.filterNaissance = filterNaissance;
	}

	public DeclarationDeces getFilterDeces() {
		return filterDeces;
	}

	public void setFilterDeces(DeclarationDeces filterDeces) {
		this.filterDeces = filterDeces;
	}

	public DeclarationMariage getFilterMariage() {
		return filterMariage;
	}

	public void setFilterMariage(DeclarationMariage filterMariage) {
		this.filterMariage = filterMariage;
	}
    
  
	public void registre(){
		   if("Tous".equalsIgnoreCase(this.selectedAnnees)){
			   registreCurrentYear = acteService.getAllActe();
		   }
		   else if(this.selectedAnnees != null && !"Tous".equalsIgnoreCase(this.selectedAnnees)){
			   registreCurrentYear = acteService.registresCurrentYear(this.selectedAnnees);
		   }
		   else{
			   registreCurrentYear = acteService.registresCurrentYear(Tools.getYearForDate(Tools.getCurrentDateDDMMYYYY()));
		   }
		
	   }
	
	
	public void registreMariage(){
		   if("Tous".equalsIgnoreCase(this.selectedAnnees)){
			   registreMariageCurrentYear = marService.getRegistreMariage();
		   }
		   else if(this.selectedAnnees != null && !"Tous".equalsIgnoreCase(this.selectedAnnees)){
			   registreMariageCurrentYear = marService.registreMariageCurrentYear(this.selectedAnnees);
		   }
		   else{
			   registreMariageCurrentYear= marService.registreMariageCurrentYear(Tools.getYearForDate(Tools.getCurrentDateDDMMYYYY()));
		   }
		  
		  
	   }
	
	
	public void registreDeces(){
	   if("Tous".equalsIgnoreCase(this.selectedAnnees)){
		   registreDecesCurrentYear = deceService.getRegistreDece();
	   }
	   else if(this.selectedAnnees != null && !"Tous".equalsIgnoreCase(this.selectedAnnees)){
		   registreDecesCurrentYear = deceService.getRegistreDeceByNum(this.selectedAnnees);
	   }
	   else{
		   registreDecesCurrentYear = deceService.getRegistreDeceByNum(Tools.getYearForDate(Tools.getCurrentDateDDMMYYYY()));
	   }
	   
	   
   }
	   

		public List<String> getAnnees() {
			return annees;
		}



	public void setAnnees(List<String> annees) {
		this.annees = annees;
	}



		/**
	 * @return the selectedAnnees
	 */
	public String getSelectedAnnees() {
		return selectedAnnees;
	}



	/**
	 * @param selectedAnnees the selectedAnnees to set
	 */
	public void setSelectedAnnees(String selectedAnnees) {
		this.selectedAnnees = selectedAnnees;
	}

		


		public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}


	
	/**
	 * @return the registreCurrentYear
	 */
	public List<DeclarationNaissance> getRegistreCurrentYear() {
		String year = Tools.getCurrentDate().substring(6, 10);
		registreCurrentYear = acteService.registresCurrentYear(year);
		return registreCurrentYear;
	}

	/**
	 * @param registreCurrentYear the registreCurrentYear to set
	 */
	public void setRegistreCurrentYear(List<DeclarationNaissance> registreCurrentYear) {
		this.registreCurrentYear = registreCurrentYear;
	}


	/**
	 * @return the registreMariageCurrentYear
	 */
	public List<DeclarationMariage> getRegistreMariageCurrentYear() {
		String year = Tools.getCurrentDate().substring(6, 10);
		registreMariageCurrentYear = marService.registreMariageCurrentYear(year);
		return registreMariageCurrentYear;
	}



	/**
	 * @param registreMariageCurrentYear the registreMariageCurrentYear to set
	 */
	public void setRegistreMariageCurrentYear(
			List<DeclarationMariage> registreMariageCurrentYear) {
		this.registreMariageCurrentYear = registreMariageCurrentYear;
	}

	/**
	 * @return the registreDecesCurrentYear
	 */
	public List<DeclarationDeces> getRegistreDecesCurrentYear() {
		String year = Tools.getCurrentDate().substring(6, 10);
		registreDecesCurrentYear = deceService.getRegistreDeceByNum(year);
		return registreDecesCurrentYear;
	}



	/**
	 * @param registreDecesCurrentYear the registreDecesCurrentYear to set
	 */
	public void setRegistreDecesCurrentYear(List<DeclarationDeces> registreDecesCurrentYear) {
		this.registreDecesCurrentYear = registreDecesCurrentYear;
	}
	
	/**
	 * @return the decN
	 */
	public int getDecN() {
		decN = acteService.getAllDeclaration().size();
		return decN;
	}



	/**
	 * @param decN the decN to set
	 */
	public void setDecN(int decN) {
		this.decN = decN;
	}
	
	public String listeDecN(){
		String route = "";
		if(MyUtil.getProfil() != null){
			route = MyUtil.basePath() +"/liste-declaration/naissance?faces-redirect=true";
		}
		else{
			route = "/pages/login?faces-redirect=true";
		}
		return route;
	}
	
	
	public String listeDecM(){
		String route = "";
		if(MyUtil.getProfil() != null){
			route = MyUtil.basePath() +"/liste-declaration/mariage?faces-redirect=true";
		}
		else{
			route = "/pages/login?faces-redirect=true";
		}
		return route;
	}
	
	
	public String listeDecD(){
		String route = "";
		if(MyUtil.getProfil() != null){
			route = MyUtil.basePath() +"/liste-declaration/deces?faces-redirect=true";
		}
		else{
			route = "/pages/login?faces-redirect=true";
		}
		return route;
	}
	
	public String listePieceDel(){
		String route = "";
		if(MyUtil.getProfil() != null){
			route = MyUtil.basePath() +"/piecesAnnexes/pieces_delivred_by_user?faces-redirect=true";
		}
		else{
			route = "/pages/login?faces-redirect=true";
		}
		return route;
	}

	/**
	 * @return the decD
	 */
	public int getDecD() {
		decD = deceService.getAllDeclarationDece().size();
		return decD;
	}



	/**
	 * @param decD the decD to set
	 */
	public void setDecD(int decD) {
		this.decD = decD;
	}


	/**
	 * @return the decM
	 */
	public int getDecM() {
		decM = marService.getAllDecMariage().size();
		return decM;
	}



	/**
	 * @param decM the decM to set
	 */
	public void setDecM(int decM) {
		this.decM = decM;
	}


	/**
	 * @return the decNBU
	 */
	public int getDecNBU() {
		if(MyUtil.getUserLogged() != null)
			decNBU = acteService.getAllDeclarationByUser(MyUtil.getUserLogged()).size();
		else
			decNBU = 0;
		return decNBU;
	}



	/**
	 * @param decNBU the decNBU to set
	 */
	public void setDecNBU(int decNBU) {
		this.decNBU = decNBU;
	}


	/**
	 * @return the decMBU
	 */
	public int getDecMBU() {
		if(MyUtil.getUserLogged() != null)
			decMBU = marService.getAllDecMaraigeByUser(MyUtil.getUserLogged()).size();
		else
			decMBU = 0;
		return decMBU;
	}



	/**
	 * @param decMBU the decMBU to set
	 */
	public void setDecMBU(int decMBU) {
		this.decMBU = decMBU;
	}


	/**
	 * @return the decDBU
	 */
	public int getDecDBU() {
		if(MyUtil.getUserLogged() != null)
			decDBU = deceService.getAllDeclarationDeceByUser(MyUtil.getUserLogged()).size();
		else
			decDBU = 0;
		return decDBU;
	}



	/**
	 * @param decDBU the decDBU to set
	 */
	public void setDecDBU(int decDBU) {
		this.decDBU = decDBU;
	}


	/**
	 * @return the piecDel
	 */
	public int getPiecDel() {
		piecDel = delService.getAllPiecesDel().size();
		return piecDel;
	}



	/**
	 * @param piecDel the piecDel to set
	 */
	public void setPiecDel(int piecDel) {
		this.piecDel = piecDel;
	}


	/**
	 * @return the pieceDelBU
	 */
	public int getPieceDelBU() {
		pieceDelBU = delService.getAllPieceBU(MyUtil.getUserLogged()).size();
		return pieceDelBU;
	}



	/**
	 * @param pieceDelBU the pieceDelBU to set
	 */
	public void setPieceDelBU(int pieceDelBU) {
		this.pieceDelBU = pieceDelBU;
	}


	/**
	 * @return the pdByUser
	 */
	public List<DelivredPieces> getPdByUser() {
		pdByUser = delService.getAllPiecesDel();
		return pdByUser;
	}



	/**
	 * @param pdByUser the pdByUser to set
	 */
	public void setPdByUser(List<DelivredPieces> pdByUser) {
		this.pdByUser = pdByUser;
	}


	/**
	 * @return the pieceDel
	 */
	public DelivredPieces getPieceDel() {
		return pieceDel;
	}



	/**
	 * @param pieceDel the pieceDel to set
	 */
	public void setPieceDel(DelivredPieces pieceDel) {
		this.pieceDel = pieceDel;
	}

	@SuppressWarnings("static-access")
	public void paiement(){
		this.pieceDel.setDate_paiement(Tools.getCurrentDateTime());
		this.pieceDel.setPaiement(true);
		this.pieceDel.setUsermodify(MyUtil.getUserLogged());
		if(delService.updatePiece(this.pieceDel)){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  "Dépot de paiement effecttué avec succès.", null);
			FacesContext context = FacesContext.getCurrentInstance();
            context.getCurrentInstance().addMessage(null, message);
        	
        	context.getExternalContext().getFlash().setKeepMessages(true);
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,  "Le dépot ne s'estt pas correctement effectué.", null);
			FacesContext context = FacesContext.getCurrentInstance();
            context.getCurrentInstance().addMessage(null, message);
        	
        	context.getExternalContext().getFlash().setKeepMessages(true);
		}
	}

	private int anneeCourant = Integer.parseInt(Tools.getCurrentDate().substring(6, 10));


	
	@SuppressWarnings("static-access")
	public String updateRegistreNaissance(){
		String route = "";
		System.out.println(this.decToConsult.getDate_j());
		if(MyUtil.getProfil() != null){
		if(acteService.verifyNumeroJugement(this.decToConsult.getNumero_jugement(), Tools.formatDay(this.decToConsult.getDate_j()).substring(6, 10))){
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "Ce numéro de jugement est déjà  attribué pour cette année.", null);
    		FacesContext context = FacesContext.getCurrentInstance();
            context.getCurrentInstance().addMessage(null, message);
        	
        	context.getExternalContext().getFlash().setKeepMessages(true);
    	}
    	else if((this.decToConsult.getDeclarant().equalsIgnoreCase("Autre")) && ("".equals(this.decToConsult.getDomicile_declarant()) || "".equals(this.decToConsult.getPrenom_declarant()) || "".equals(this.decToConsult.getNom_declarant()) || "".equals(this.decToConsult.getProfession_declarant()) || "".equals(this.decToConsult.getAdresse_declarant()) || "".equals(this.decToConsult.getNum_identification_declarant()))){
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,  "Veuillez saisir les informations du déclarant.", null);
    		FacesContext context = FacesContext.getCurrentInstance();
            context.getCurrentInstance().addMessage(null, message);
        	
        	context.getExternalContext().getFlash().setKeepMessages(true);
    	}
    	else{
    		this.decToConsult.setDate_naissance_enfant(Tools.formatDay(this.decToConsult.getDate_naissanceE()));
            this.decToConsult.setDate_naissance_mere(Tools.formatDay(this.decToConsult.getDate_naissanceM()));
            this.decToConsult.setDate_naissance_pere(Tools.formatDay(this.decToConsult.getDate_naissanceP()));
            this.decToConsult.setDate_jugement(Tools.formatDay(this.decToConsult.getDate_j()));
            
            this.decToConsult.setHeure_naissance_enfant(this.decToConsult.getHeure_naissanceE().toString().substring(11, 16));
            this.decToConsult.setType_declaration("Jugement");
            this.decToConsult.setDate_modification(Tools.getCurrentDateTime());
            this.decToConsult.setModificateurNaissance(MyUtil.getUserLogged());
            
            if (acteService.updateActe(decToConsult)) {
            	
            	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  "La modification a été effectuée avec succès.", null);
            	FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
            	route = MyUtil.basePath() +"/consultation/acte/naissance?faces-redirect=true";
            }
            
            else{
            	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "Impossible de modifier la déclaration. Vérifiez toutes les informations saisies.", null);
            	FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
            }
        }
	}
		else
			route = MyUtil.pathLogin();
		return route;
	}
	
	@SuppressWarnings("static-access")
	public String updateRegistreMariage(){
		String route = "";
		if(MyUtil.getProfil() != null){
			
			if(marService.verifyNumeroJugement(this.decMarToConsult.getNumero_Jugement(), this.decMarToConsult.getDateJugement().toString().substring(6, 10))){
        		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "Ce numéro de jugement est déjà  attribué pour cette année.", null);
        		FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
        	}
        	else{
                this.decMarToConsult.setDate_Mariage(Tools.formatDay(this.decMarToConsult.getDateMariage()));
                this.decMarToConsult.setDate_Jugement(Tools.formatDay(this.decMarToConsult.getDateJugement()));
                this.decMarToConsult.setDate_Naissance_Epouse(Tools.formatDay(this.decMarToConsult.getDateNaissanceEpouse()));
                this.decMarToConsult.setDate_Naissance_Epoux(Tools.formatDay(this.decMarToConsult.getDateNaissanceMereEpoux()));
                this.decMarToConsult.setDate_Naissance_Mere_Epouse(Tools.formatDay(this.decMarToConsult.getDateNaissanceMereEpouse()));
                this.decMarToConsult.setDate_Naissance_Mere_Epoux(Tools.formatDay(this.decMarToConsult.getDateNaissanceMereEpoux()));
                this.decMarToConsult.setDate_Naissance_Pere_Epoux(Tools.formatDay(this.decMarToConsult.getDateNaissancePereEpoux()));
                this.decMarToConsult.setDate_Naissance_Pere_Epouse(Tools.formatDay(this.decMarToConsult.getDateNaissancePereEpouse()));
                this.decMarToConsult.setHeure_Mariage(this.decMarToConsult.getHeureMariage().toString().substring(11, 16));
                this.decMarToConsult.setType_Declaration("Jugement");
                this.decMarToConsult.setDate_modification(Tools.getCurrentDateTime());
                this.decMarToConsult.setModificateurMariage(MyUtil.getUserLogged());
                
                if (marService.updateDeclarationMariage(decMarToConsult)) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  "Les modifications ont été effectué avec succès.", null);
                    FacesContext context = FacesContext.getCurrentInstance();
    	            context.getCurrentInstance().addMessage(null, message);
	            	
	            	context.getExternalContext().getFlash().setKeepMessages(true);
                    route = MyUtil.basePath() +"/consultation/acte/mariage?faces-redirect=true";

                } 
                else {

                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "Les modifications n'ont pas été effecuté normalement.", null);
                    FacesContext context = FacesContext.getCurrentInstance();
    	            context.getCurrentInstance().addMessage(null, message);
	            	
	            	context.getExternalContext().getFlash().setKeepMessages(true);
                
                }
        	}
			
		}
		else
			route = MyUtil.pathLogin();
		return route;
	}
	
	
	@SuppressWarnings("static-access")
	public String updateRegistreDeces(){
		String route = "";
		if(MyUtil.getProfil() != null){
			
			if(deceService.verifyNumeroJugement(this.decDCToConsult.getNum_jugement(), Tools.formatDay(this.decDCToConsult.getDate_j()).substring(6, 10))){
        		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "Ce numéro de jugement est déjé attribué pour cette année.", null);
        		FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
        	}
        	else if((this.decDCToConsult.getDegre_parente().equalsIgnoreCase("Autre")) && ("".equals(this.decDCToConsult.getAdresse_declarant()) || "".equals(this.decDCToConsult.getPrenom_declarant()) || "".equals(this.decDCToConsult.getNom_declarant()) || "".equals(this.decDCToConsult.getProfession_declarant()) || "".equals(this.decDCToConsult.getNum_identification_declarant()))){
        		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,  "Veuillez saisir les informations du déclarant.", null);
        		FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
        	}
        	else if((this.decDCToConsult.getDegre_parente().equalsIgnoreCase("Pére")) && ("".equals(this.decDCToConsult.getPrenom_pere()) || "".equals(this.decDCToConsult.getNom_pere()) || "".equals(this.decDCToConsult.getDomicile_pere()) || "".equals(this.decDCToConsult.getProfession_pere()))){
        		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,  "Veuillez saisir les informations du pére.", null);
        		FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
        	}
        	else if((this.decDCToConsult.getDegre_parente().equalsIgnoreCase("Mére")) && ("".equals(this.decDCToConsult.getPrenom_mere()) || "".equals(this.decDCToConsult.getNom_mere()) || "".equals(this.decDCToConsult.getDomicile_mere()) || "".equals(this.decDCToConsult.getProfession_mere()))){
        		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,  "Veuillez saisir les informations de la mére.", null);
        		FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
        	}
        	else{
        		this.decDCToConsult.setDate_deces(Tools.formatDay(this.decDCToConsult.getDate_d()));
                this.decDCToConsult.setDate_naissance_defunt(Tools.formatDay(this.decDCToConsult.getDate_naissanceDefunt()));
                this.decDCToConsult.setDate_jugement(Tools.formatDay(this.decDCToConsult.getDate_j()));
                this.decDCToConsult.setHeure_deces(this.decDCToConsult.getHeure_d().toString().substring(11, 16));
                this.decDCToConsult.setDate_modification(Tools.getCurrentDateTime());
                this.decDCToConsult.setType_declaration("Jugement");
                this.decDCToConsult.setModificateurDeces(MyUtil.getUserLogged());
                
                if (deceService.updateDeclarationDeces(decDCToConsult)) {
                	
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  "La modification a été effectué avec succés!", null);
                    FacesContext context = FacesContext.getCurrentInstance();
    	            context.getCurrentInstance().addMessage(null, message);
	            	
	            	context.getExternalContext().getFlash().setKeepMessages(true);
                    route = MyUtil.basePath() +"/consultation/acte/deces?faces-redirect=true";

                } else {
                	
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "La modification n'a pas été effectué!", null);
                    FacesContext context = FacesContext.getCurrentInstance();
    	            context.getCurrentInstance().addMessage(null, message);
	            	
	            	context.getExternalContext().getFlash().setKeepMessages(true);
                }
        	}
			
		}
		else
			route = MyUtil.pathLogin();
		return route;
	}
	
	@SuppressWarnings("static-access")
	public void mAJMMN(){
		if(MyUtil.getProfil() != null){
			if(this.decToConsult.getMention_marginale() != null)
				this.decToConsult.setMention_marginale(this.decToConsult.getMention_marginale()+" \n "+this.getMentionN());
			else
				this.decToConsult.setMention_marginale(this.getMentionN());
			if (acteService.updateActe(this.decToConsult)) {
            	
            	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  "Mise à jour de la mention marginale effectuée avec succés!", null);
            	FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
            	
            }
            
            else{
            	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "Mise à jour de la mention marginale non effectuée!", null);
            	FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
            }
		}
		else{
			
		}
	}
	
	@SuppressWarnings("static-access")
	public void mAJMMM(){
		if(MyUtil.getProfil() != null){
			if(this.decMarToConsult.getMentions_Marginales() != null)
				this.decMarToConsult.setMentions_Marginales(this.decMarToConsult.getMentions_Marginales()+" \n "+this.getMention());
			else
				this.decMarToConsult.setMentions_Marginales(this.getMention());
			
			if (marService.updateDeclarationMariage(decMarToConsult)) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  "Mise à jour de la mention marginale effectuée avec succés!", null);
                FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);

            } 
            else {

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "Mise à jour de la mention marginale non effectuée!", null);
                FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
            
            }
		}
		else{
			
		}
	}
	
	@SuppressWarnings("static-access")
	public void mAJMMD(){
		if(MyUtil.getProfil() != null){
			if(this.decDCToConsult.getMention_marginale() != null)
				this.decDCToConsult.setMention_marginale(this.decDCToConsult.getMention_marginale()+" \n "+this.getMentionD());
			else
				this.decDCToConsult.setMention_marginale(this.getMentionD());
			if (deceService.updateDeclarationDeces(decDCToConsult)) {
            	
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  "Mise à jour de la mention marginale effectuée avec succés!", null);
                FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);

            } else {
            	
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "Mise à jour de la mention marginale non effectuée!", null);
                FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
            }
		}
		else{
			
		}
	}



	/**
	 * @return the mention
	 */
	public String getMention() {
		return mention;
	}



	/**
	 * @param mention the mention to set
	 */
	public void setMention(String mention) {
		this.mention = mention;
	}



	/**
	 * @return the mentionN
	 */
	public String getMentionN() {
		return mentionN;
	}



	/**
	 * @param mentionN the mentionN to set
	 */
	public void setMentionN(String mentionN) {
		this.mentionN = mentionN;
	}



	/**
	 * @return the mentionD
	 */
	public String getMentionD() {
		return mentionD;
	}



	/**
	 * @param mentionD the mentionD to set
	 */
	public void setMentionD(String mentionD) {
		this.mentionD = mentionD;
	}
	
	public String mentionN(){
		if(MyUtil.getProfil() != null)
			return MyUtil.pathMAJMM()+"naissance?faces-redirect=true";
		else
			return MyUtil.pathLogin();
	}
	
	public String mentionM(){
		if(MyUtil.getProfil() != null)
			return MyUtil.pathMAJMM()+"mariage?faces-redirect=true";
		else
			return MyUtil.pathLogin();
	}
	
	public String mentionD(){
		if(MyUtil.getProfil() != null)
			return MyUtil.pathMAJMM()+"deces?faces-redirect=true";
		else
			return MyUtil.pathLogin();
	}



	/**
	 * @return the population
	 */
	public int getPopulation() {
		population = (acteService.getAllActe().size()) - (deceService.getRegistreDece().size());
		return population;
	}



	/**
	 * @param population the population to set
	 */
	public void setPopulation(int population) {
		this.population = population;
	}
	
	
}
