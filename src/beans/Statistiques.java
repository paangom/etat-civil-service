/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.DeclarationDeces;
import models.DeclarationMariage;
import models.DeclarationNaissance;
import models.DelivredPieces;
import models.Users;

import org.primefaces.model.chart.PieChartModel;

import services.ActeDecesServices;
import services.ActeMariageServices;
import services.ActeNaissanceServices;
import services.CentreServices;
import services.DelivredPieceService;
import services.PiecesAnnexesServices;
import services.UserServices;
import util.MyUtil;
import util.Tools;



/**
 *
 * @author sambasow
 */
public class Statistiques {
	

	public String recettes;
	
	private List<Users> user;
	private Date date1;
	
	private PieChartModel pieModel;
	private PieChartModel pieModelInstance;
	private PieChartModel statPieModel;
	private PieChartModel statPieModelInstance;
	
	private ActeNaissanceServices acteService = new ActeNaissanceServices();
    private ActeDecesServices deceService = new ActeDecesServices();
    private ActeMariageServices marService = new ActeMariageServices();
    private DelivredPieceService delService = new DelivredPieceService(); 
    private UserServices uService = new UserServices();
    private CentreServices cenServ = new CentreServices();
    private PiecesAnnexesServices pService = new PiecesAnnexesServices();
    private List<DeclarationNaissance> listDNV = new ArrayList<DeclarationNaissance>();
    private List<DeclarationNaissance> listDN = new ArrayList<DeclarationNaissance>();
    private List<DeclarationMariage> listDM = new ArrayList<DeclarationMariage>();
    private List<DeclarationMariage> listDMV = new ArrayList<DeclarationMariage>();
    private List<DeclarationDeces> listDD = new ArrayList<DeclarationDeces>();
    private List<DeclarationDeces> listDDV = new ArrayList<DeclarationDeces>();
    private List<DelivredPieces> listPiece = new ArrayList<DelivredPieces>();
    
   
    
    private int statDecN;
    private int statDecM;
    private int statDecD;
    
    private int statDecNV;
    private int statDecMV;
    private int statDecDV;
    private int statPDel ;
    
    private int coutume;
    private int nonExistence;
    private int nonInscription;
    private int vieIndiv;
    private int vieCollect;
    private int nonInscripM;
    private int residence;
    private int individualite;
    private int nonDNM;
    private int nonDNRM;
    private int celibat;
    private int nonDNS;
    private int vieIndivFam;
    private int vieCollectFam;
    private int permis;
    
    
    private String libele;
    
    private String statMois;
    private String statEnCours;
    private String selectAnnee;
    private String selectMois;
    private String selectJour;
    private List<String> jour;
    private List<String> annees;
    private List<String> mois;
   	
	
	public Statistiques() {
		super();
		user = uService.getAllUser();
		statEnCours = "STATISTIQUES DU "+Tools.getDayForDateTime(Tools.getCurrentDateDDMMYYYY())+" "
				 +Tools.getMoisLettre(Tools.getCurrentDateDDMMYYYY()).toUpperCase()+" "+Tools.getYearForDate(Tools.getCurrentDateDDMMYYYY());
		annees= new ArrayList<String>();
		mois= new ArrayList<String>();
		jour = new ArrayList<String>();
		mois.add("Janvier");
		mois.add("Fevrier");
		mois.add("Mars");
		mois.add("Avril");
		mois.add("Mai");
		mois.add("Juin");
		mois.add("Juillet");
		mois.add("Août");
		mois.add("Septembre");
		mois.add("Octobre");
		mois.add("Novembre");
		mois.add("Décembre");
		
		jour.add("01");
		jour.add("02");
		jour.add("03");
		jour.add("04");
		jour.add("05");
		jour.add("06");
		jour.add("07");
		jour.add("08");
		jour.add("09");
		jour.add("10");
		jour.add("11");
		jour.add("12");
		jour.add("13");
		jour.add("14");
		jour.add("15");
		jour.add("16");
		jour.add("17");
		jour.add("18");
		jour.add("19");
		jour.add("20");
		jour.add("21");
		jour.add("22");
		jour.add("23");
		jour.add("24");
		jour.add("25");
		jour.add("26");
		jour.add("27");
		jour.add("28");
		jour.add("29");
		jour.add("30");
		jour.add("31");
		
		int anneeCourant = Integer.parseInt(Tools.getCurrentDate().substring(6, 10));
		int a = Integer.parseInt(cenServ.getCentre().getDate_Install().substring(6, 10));
 		
 		for(int i = anneeCourant ; i >= a; i--){
 			annees.add(i+""); 
 		}
		
		createPieModel();
		 listDN = acteService.getAllDeclaration();
		 listDNV = acteService.getAllActe();
		 statDecN = listDN.size();
		 statDecNV = listDNV.size();
		 listDM = marService.getAllDecMariage();
		 listDMV = marService.getRegistreMariage();
		 statDecM = listDM.size();
		 statDecMV = listDMV.size();
		 statEnCours = "LES STATISTIQUES DU"+Tools.getDayForDate(Tools.getCurrentDateDDMMYYYY()+
				 " "+Tools.getMoisLettre(Tools.getCurrentDateDDMMYYYY())+
				 " "+Tools.getYearForDate(Tools.getCurrentDateDDMMYYYY()));
		 listDD = deceService.getAllDeclarationDece();
		 listDDV = deceService.getRegistreDece();
		 statDecD = listDD.size();
		 statDecDV = listDDV.size();
		 
		nonDNRM = delService.findByPiece(pService.findByCode(300)).size();
		individualite = delService.findByPiece(pService.findByCode(270)).size();
		residence = delService.findByPiece(pService.findByCode(250)).size();
		nonInscripM = delService.findByPiece(pService.findByCode(310)).size();
		vieCollect = delService.findByPiece(pService.findByCode(320)).size();
		vieIndiv = delService.findByPiece(pService.findByCode(230)).size();
		nonInscription = delService.findByPiece(pService.findByCode(220)).size();
		nonExistence = delService.findByPiece(pService.findByCode(240)).size();
		coutume = delService.findByPiece(pService.findByCode(200)).size();
		nonDNM = delService.findByPiece(pService.findByCode(290)).size();
		celibat = delService.findByPiece(pService.findByCode(260)).size();
		nonDNS = delService.findByPiece(pService.findByCode(280)).size();
		vieIndivFam = delService.findByPiece(pService.findByCode(340)).size();
		vieCollectFam = delService.findByPiece(pService.findByCode(330)).size();
		permis = delService.findByPiece(pService.findByCode(210)).size();
	}
	
	 private void createPieModel() {  
	        pieModel = new PieChartModel();  
	        
	        pieModel.set("Naissances", acteService.getAllActe().size());  
	        pieModel.set("Mariages", marService.getRegistreMariage().size());  
	        pieModel.set("Décès", deceService.getRegistreDece().size());
	       
	    }  
	  
	 private String dujour = null;
	 public void naissance() {
		 if(!"".equals(this.getSelectJour()) && !"".equals(this.getSelectMois()) && !"".equals(this.getSelectAnnee())) {
			 dujour = this.getSelectJour()+"/"+Tools.getDateInt(this.getSelectMois())+"/"+this.getSelectAnnee();
			 statEnCours = "STATISTIQUES DU "+this.getSelectJour()+" "+this.getSelectMois().toUpperCase()+" "+this.getSelectAnnee();
			 listDN = acteService.listInstanceByDate(dujour);
			 listDNV = acteService.listValidateByDate(dujour);
			 statDecN = listDN.size();
			 statDecNV = listDNV.size();
			 
		 }
		 else if("".equals(this.getSelectJour()) && !"".equals(this.getSelectMois()) && !"".equals(this.getSelectAnnee())) {
			 dujour = Tools.getDateInt(this.getSelectMois())+"/"+this.getSelectAnnee();
			 
			 statEnCours = "STATISTIQUES DE "+this.getSelectMois().toUpperCase()+" "+this.getSelectAnnee();
			 listDN = acteService.listInstanceByDate(dujour);
			 listDNV = acteService.listValidateByDate(dujour);
			 statDecN = listDN.size();
			 statDecNV = listDNV.size();
		 }
		 else if("".equals(this.getSelectJour()) && "".equals(this.getSelectMois()) && !"".equals(this.getSelectAnnee())) {
			 dujour = this.getSelectAnnee();
			 
			 statEnCours = "STATISTIQUES DE L'ANNEE "+this.getSelectAnnee();
			 listDN = acteService.listInstanceByDate(dujour);
			 listDNV = acteService.listValidateByDate(dujour);
			 statDecN = listDN.size();
			 statDecNV = listDNV.size();
		 }
		 else {
			 statEnCours = "STATISTIQUES DU "+Tools.getDayForDateTime(Tools.getCurrentDateDDMMYYYY())+" "
					 +Tools.getMoisLettre(Tools.getCurrentDateDDMMYYYY()).toUpperCase()+" "+Tools.getYearForDate(Tools.getCurrentDateDDMMYYYY());
			 listDN = acteService.listInstanceByDate(Tools.getCurrentDateDDMMYYYY());
			 listDNV = acteService.listValidateByDate(Tools.getCurrentDateDDMMYYYY());
			 statDecN = listDN.size();
			 statDecNV = listDNV.size();
		 }
		 
	 }
	 
	 public void mariage() {
		 if(!"".equals(this.getSelectJour()) && !"".equals(this.getSelectMois()) && !"".equals(this.getSelectAnnee())) {
			 dujour = this.getSelectJour()+"/"+Tools.getDateInt(this.getSelectMois())+"/"+this.getSelectAnnee();
			 statEnCours ="STATISTIQUES DU "+ this.getSelectJour()+" "+this.getSelectMois().toUpperCase()+" "+this.getSelectAnnee();
			 listDM = marService.listInstanceByDate(dujour);
			 listDMV = marService.listValidateByDate(dujour);
			 statDecM = listDM.size();
			 statDecMV = listDMV.size();
		 }
		 else if("".equals(this.getSelectJour()) && !"".equals(this.getSelectMois()) && !"".equals(this.getSelectAnnee())) {
			 dujour = Tools.getDateInt(this.getSelectMois())+"/"+this.getSelectAnnee();
			 
			 statEnCours = "STATISTIQUES DE "+this.getSelectMois().toUpperCase()+" "+this.getSelectAnnee();
			 listDM = marService.listInstanceByDate(dujour);
			 listDMV = marService.listValidateByDate(dujour);
			 statDecM = listDM.size();
			 statDecMV = listDMV.size();
		 }
		 else if("".equals(this.getSelectJour()) && "".equals(this.getSelectMois()) && !"".equals(this.getSelectAnnee())) {
			 dujour = this.getSelectAnnee();
			 
			 statEnCours ="STATISTIQUES DE L'ANNEE "+ this.getSelectAnnee();
			 listDM = marService.listInstanceByDate(dujour);
			 listDMV = marService.listValidateByDate(dujour);
			 statDecM = listDM.size();
			 statDecMV = listDMV.size();
		 }
		 else {
			 statEnCours ="STATISTIQUES DU "+Tools.getDayForDateTime(Tools.getCurrentDateDDMMYYYY())+" "
					 +Tools.getMoisLettre(Tools.getCurrentDateDDMMYYYY()).toUpperCase()+" "+Tools.getYearForDate(Tools.getCurrentDateDDMMYYYY());
			 listDM = marService.listInstanceByDate(Tools.getCurrentDateDDMMYYYY());
			 listDMV = marService.listValidateByDate(Tools.getCurrentDateDDMMYYYY());
			 statDecM = listDM.size();
			 statDecMV = listDMV.size();
		 }
		 
	 }
	 
	 public void deces() {
		 if(!"".equals(this.getSelectJour()) && !"".equals(this.getSelectMois()) && !"".equals(this.getSelectAnnee())) {
			 dujour = this.getSelectJour()+"/"+Tools.getDateInt(this.getSelectMois())+"/"+this.getSelectAnnee();
			 
			 statEnCours ="STATISTIQUES DU "+ this.getSelectJour()+" "+this.getSelectMois().toUpperCase()+" "+this.getSelectAnnee();
			 listDD = deceService.listInstanceByDate(dujour);
			 listDDV = deceService.listValidateByDate(dujour);
			 statDecD = listDD.size();
			 statDecDV = listDDV.size();
		 }
		 else if("".equals(this.getSelectJour()) && !"".equals(this.getSelectMois()) && !"".equals(this.getSelectAnnee())) {
			 dujour = Tools.getDateInt(this.getSelectMois())+"/"+this.getSelectAnnee();
			 
			 statEnCours = "STATISTIQUES DE "+this.getSelectMois().toUpperCase()+" "+this.getSelectAnnee();
			 listDD = deceService.listInstanceByDate(dujour);
			 listDDV = deceService.listValidateByDate(dujour);
			 statDecD = listDD.size();
			 statDecDV = listDDV.size();
		 }
		 else if("".equals(this.getSelectJour()) && "".equals(this.getSelectMois()) && !"".equals(this.getSelectAnnee())) {
			 dujour = this.getSelectAnnee();
			 
			 statEnCours = "STATISTIQUES DE L'ANNEE "+this.getSelectAnnee();
			 listDD = deceService.listInstanceByDate(dujour);
			 listDDV = deceService.listValidateByDate(dujour);
			 statDecD = listDD.size();
			 statDecDV = listDDV.size();
		 }
		 else {
			 statEnCours ="STATISTIQUES DU "+Tools.getDayForDateTime(Tools.getCurrentDateDDMMYYYY())+" "
					 +Tools.getMoisLettre(Tools.getCurrentDateDDMMYYYY()).toUpperCase()+" "+Tools.getYearForDate(Tools.getCurrentDateDDMMYYYY());
			 listDD = deceService.listInstanceByDate(Tools.getCurrentDateDDMMYYYY());
			 listDDV = deceService.listValidateByDate(Tools.getCurrentDateDDMMYYYY());
			 statDecD = listDD.size();
			 statDecDV = listDDV.size();
		 }
		 
			
	 }
	 
	 
	 public String naissanceAll() {
		 
		 return MyUtil.basePath() +"/statistiques/Naissance?faces-redirect=true";
	 }
	 
	 public String mariageAll() {
		 
		 return MyUtil.basePath() +"/statistiques/Mariage?faces-redirect=true";
	 }
	 
	 public String decesAll() {
		 
		 return MyUtil.basePath() +"/statistiques/Deces?faces-redirect=true";
	 }
	
	 
	 public void pieces() {
		 
		 if(!"".equals(this.getSelectJour()) && !"".equals(this.getSelectMois()) && !"".equals(this.getSelectAnnee())) {
			 dujour = this.getSelectJour()+"/"+Tools.getDateInt(this.getSelectMois())+"/"+this.getSelectAnnee();
			 
			 	statEnCours ="STATISTIQUES DU "+ this.getSelectJour()+" "+this.getSelectMois().toUpperCase()+" "+this.getSelectAnnee();
			 	nonDNRM = delService.nombreDePieces(pService.findByCode(300), dujour);
				individualite = delService.nombreDePieces(pService.findByCode(270), dujour);
				residence = delService.nombreDePieces(pService.findByCode(250), dujour);
				nonInscripM = delService.nombreDePieces(pService.findByCode(310), dujour);
				vieCollect = delService.nombreDePieces(pService.findByCode(320), dujour);
				vieIndiv = delService.nombreDePieces(pService.findByCode(230), dujour);
				nonInscription = delService.nombreDePieces(pService.findByCode(220), dujour);
				nonExistence = delService.nombreDePieces(pService.findByCode(240), dujour);
				coutume = delService.nombreDePieces(pService.findByCode(200), dujour);
				nonDNM = delService.nombreDePieces(pService.findByCode(290), dujour);
				celibat = delService.nombreDePieces(pService.findByCode(260), dujour);
				nonDNS = delService.nombreDePieces(pService.findByCode(280), dujour);
				vieIndivFam = delService.nombreDePieces(pService.findByCode(340), dujour);
				vieCollectFam = delService.nombreDePieces(pService.findByCode(330), dujour);
				permis = delService.nombreDePieces(pService.findByCode(210), dujour);
			 
		 }
		 else if("".equals(this.getSelectJour()) && !"".equals(this.getSelectMois()) && !"".equals(this.getSelectAnnee())) {
			 dujour = Tools.getDateInt(this.getSelectMois())+"/"+this.getSelectAnnee();
			 
			 statEnCours ="STATISTIQUES DE "+ this.getSelectMois().toUpperCase()+" "+this.getSelectAnnee();
			 	nonDNRM = delService.nombreDePieces(pService.findByCode(300), dujour);
				individualite = delService.nombreDePieces(pService.findByCode(270), dujour);
				residence = delService.nombreDePieces(pService.findByCode(250), dujour);
				nonInscripM = delService.nombreDePieces(pService.findByCode(310), dujour);
				vieCollect = delService.nombreDePieces(pService.findByCode(320), dujour);
				vieIndiv = delService.nombreDePieces(pService.findByCode(230), dujour);
				nonInscription = delService.nombreDePieces(pService.findByCode(220), dujour);
				nonExistence = delService.nombreDePieces(pService.findByCode(240), dujour);
				coutume = delService.nombreDePieces(pService.findByCode(200), dujour);
				nonDNM = delService.nombreDePieces(pService.findByCode(290), dujour);
				celibat = delService.nombreDePieces(pService.findByCode(260), dujour);
				nonDNS = delService.nombreDePieces(pService.findByCode(280), dujour);
				vieIndivFam = delService.nombreDePieces(pService.findByCode(340), dujour);
				vieCollectFam = delService.nombreDePieces(pService.findByCode(330), dujour);
				permis = delService.nombreDePieces(pService.findByCode(210), dujour);
		 }
		 else if("".equals(this.getSelectJour()) && "".equals(this.getSelectMois()) && !"".equals(this.getSelectAnnee())) {
			 dujour = this.getSelectAnnee();
			 
			 statEnCours = "STATISTIQUE DE L'ANNEE "+this.getSelectAnnee();
			 	nonDNRM = delService.nombreDePieces(pService.findByCode(300), dujour);
				individualite = delService.nombreDePieces(pService.findByCode(270), dujour);
				residence = delService.nombreDePieces(pService.findByCode(250), dujour);
				nonInscripM = delService.nombreDePieces(pService.findByCode(310), dujour);
				vieCollect = delService.nombreDePieces(pService.findByCode(320), dujour);
				vieIndiv = delService.nombreDePieces(pService.findByCode(230), dujour);
				nonInscription = delService.nombreDePieces(pService.findByCode(220), dujour);
				nonExistence = delService.nombreDePieces(pService.findByCode(240), dujour);
				coutume = delService.nombreDePieces(pService.findByCode(200), dujour);
				nonDNM = delService.nombreDePieces(pService.findByCode(290), dujour);
				celibat = delService.nombreDePieces(pService.findByCode(260), dujour);
				nonDNS = delService.nombreDePieces(pService.findByCode(280), dujour);
				vieIndivFam = delService.nombreDePieces(pService.findByCode(340), dujour);
				vieCollectFam = delService.nombreDePieces(pService.findByCode(330), dujour);
				permis = delService.nombreDePieces(pService.findByCode(210), dujour);
		 }
		 else {
			 listDD = deceService.listInstanceByDate(Tools.getCurrentDateDDMMYYYY());
			 listDDV = deceService.listValidateByDate(Tools.getCurrentDateDDMMYYYY());
			 statDecD = deceService.findInstanceByDate(Tools.getCurrentDateDDMMYYYY());
			 statDecDV = deceService.findValidateByDate(Tools.getCurrentDateDDMMYYYY());
		 }
		 
	 }
	 
	 public String piecesAll() {
		 
		 return MyUtil.basePath() +"/statistiques/Pieces?faces-redirect=true";
	 }
	 
	 
	/**
	 * @return the categoryModel
	 */
	public PieChartModel getPieModel() {
		return pieModel;
	}



	/**
	 * @return the pieModelInstance
	 */
	public PieChartModel getPieModelInstance() {
		return pieModelInstance;
	}

	
	/**
	 * @return the libele
	 */
	public String getLibele(int code) {
		libele = pService.findByCode(code).getLibelle();
		return libele;
	}

	/**
	 * @param libele the libele to set
	 */
	public void setLibele(String libele) {
		this.libele = libele;
	}

	/**
	 * @return the nonDNM
	 */
	public int getNonDNM() {
		
		return nonDNM;
	}


	/**
	 * @return the coutume
	 */
	public int getCoutume() {
		
		return coutume;
	}

	/**
	 * @return the nonExistence
	 */
	public int getNonExistence() {
		
		return nonExistence;
	}

	/**
	 * @return the nonInscription
	 */
	public int getNonInscription() {
		
		return nonInscription;
	}

	/**
	 * @return the vieIndiv
	 */
	public int getVieIndiv() {
		
		return vieIndiv;
	}

	/**
	 * @return the vieCollect
	 */
	public int getVieCollect() {
		
		return vieCollect;
	}

	/**
	 * @return the nonInscripM
	 */
	public int getNonInscripM() {
		
		return nonInscripM;
	}

	/**
	 * @return the residence
	 */
	public int getResidence() {
		
		return residence;
	}

	/**
	 * @return the individualite
	 */
	public int getIndividualite() {
		
		return individualite;
	}

	/**
	 * @return the nonDNRM
	 */
	public int getNonDNRM() {
		
		return nonDNRM;
	}


	/**
	 * @return the celibat
	 */
	public int getCelibat() {
		return celibat;
	}

	/**
	 * @param celibat the celibat to set
	 */
	public void setCelibat(int celibat) {
		this.celibat = celibat;
	}

	/**
	 * @return the nonDNS
	 */
	public int getNonDNS() {
		return nonDNS;
	}

	/**
	 * @param nonDNS the nonDNS to set
	 */
	public void setNonDNS(int nonDNS) {
		this.nonDNS = nonDNS;
	}

	/**
	 * @return the vieIndivFam
	 */
	public int getVieIndivFam() {
		return vieIndivFam;
	}

	/**
	 * @param vieIndivFam the vieIndivFam to set
	 */
	public void setVieIndivFam(int vieIndivFam) {
		this.vieIndivFam = vieIndivFam;
	}

	/**
	 * @return the vieCollectFam
	 */
	public int getVieCollectFam() {
		return vieCollectFam;
	}

	/**
	 * @param vieCollectFam the vieCollectFam to set
	 */
	public void setVieCollectFam(int vieCollectFam) {
		this.vieCollectFam = vieCollectFam;
	}

	/**
	 * @return the permis
	 */
	public int getPermis() {
		return permis;
	}

	/**
	 * @param permis the permis to set
	 */
	public void setPermis(int permis) {
		this.permis = permis;
	}

	/**
	 * @return the selectAnnee
	 */
	public String getSelectAnnee() {
		return selectAnnee;
	}

	/**
	 * @param selectAnnee the selectAnnee to set
	 */
	public void setSelectAnnee(String selectAnnee) {
		this.selectAnnee = selectAnnee;
	}

	/**
	 * @return the selectMois
	 */
	public String getSelectMois() {
		return selectMois;
	}

	/**
	 * @param selectMois the selectMois to set
	 */
	public void setSelectMois(String selectMois) {
		this.selectMois = selectMois;
	}

	/**
	 * @return the annees
	 */
	public List<String> getAnnees() {
		return annees;
	}

	/**
	 * @param annees the annees to set
	 */
	public void setAnnees(List<String> annees) {
		this.annees = annees;
	}

	/**
	 * @return the mois
	 */
	public List<String> getMois() {
		return mois;
	}

	/**
	 * @param mois the mois to set
	 */
	public void setMois(List<String> mois) {
		this.mois = mois;
	}

	/**
	 * @return the statDecN
	 */
	public int getStatDecN() {
		return statDecN;
	}

	/**
	 * @return the statDecM
	 */
	public int getStatDecM() {
		return statDecM;
	}

	/**
	 * @return the statDecD
	 */
	public int getStatDecD() {
		return statDecD;
	}

	/**
	 * @return the statDecNV
	 */
	public int getStatDecNV() {
		return statDecNV;
	}

	/**
	 * @return the statDecMV
	 */
	public int getStatDecMV() {
		return statDecMV;
	}

	/**
	 * @return the statDecDV
	 */
	public int getStatDecDV() {
		return statDecDV;
	}

	/**
	 * @return the statPDel
	 */
	public int getStatPDel() {
		return statPDel;
	}

	/**
	 * @return the statPieModelInstance
	 */
	public PieChartModel getStatPieModelInstance() {
		return statPieModelInstance;
	}

	/**
	 * @param statPieModelInstance the statPieModelInstance to set
	 */
	public void setStatPieModelInstance(PieChartModel statPieModelInstance) {
		this.statPieModelInstance = statPieModelInstance;
	}

	/**
	 * @return the statPieModel
	 */
	public PieChartModel getStatPieModel() {
		return statPieModel;
	}

	/**
	 * @param statPieModel the statPieModel to set
	 */
	public void setStatPieModel(PieChartModel statPieModel) {
		this.statPieModel = statPieModel;
	}

	/**
	 * @return the statMois
	 */
	public String getStatMois() {
		return statMois;
	}

	/**
	 * @param statMois the statMois to set
	 */
	public void setStatMois(String statMois) {
		this.statMois = statMois;
	}

	/**
	 * @return the statEnCours
	 */
	public String getStatEnCours() {
		return statEnCours;
	}

	/**
	 * @param statEnCours the statEnCours to set
	 */
	public void setStatEnCours(String statEnCours) {
		this.statEnCours = statEnCours;
	}


	/**
	 * @return the user
	 */
	public List<Users> getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(List<Users> user) {
		this.user = user;
	}
	
	
	public String statUsers(){
			return "/pages/statistiques/statUsers?faces-redirect=true";
	}

	/**
	 * @return the date1
	 */
	public Date getDate1() {
		
		return date1;
	}

	/**
	 * @param date1 the date1 to set
	 */
	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	/**
	 * @return the dujour
	 */
	public String getDujour() {
		return dujour;
	}

	/**
	 * @param dujour the dujour to set
	 */
	public void setDujour(String dujour) {
		this.dujour = dujour;
	}

	/**
	 * @return the listDNV
	 */
	public List<DeclarationNaissance> getListDNV() {
		return listDNV;
	}

	/**
	 * @param listDNV the listDNV to set
	 */
	public void setListDNV(List<DeclarationNaissance> listDNV) {
		this.listDNV = listDNV;
	}

	/**
	 * @return the listDN
	 */
	public List<DeclarationNaissance> getListDN() {
		return listDN;
	}

	/**
	 * @param listDN the listDN to set
	 */
	public void setListDN(List<DeclarationNaissance> listDN) {
		this.listDN = listDN;
	}

	/**
	 * @return the listDM
	 */
	public List<DeclarationMariage> getListDM() {
		return listDM;
	}

	/**
	 * @param listDM the listDM to set
	 */
	public void setListDM(List<DeclarationMariage> listDM) {
		this.listDM = listDM;
	}

	/**
	 * @return the listDMV
	 */
	public List<DeclarationMariage> getListDMV() {
		return listDMV;
	}

	/**
	 * @param listDMV the listDMV to set
	 */
	public void setListDMV(List<DeclarationMariage> listDMV) {
		this.listDMV = listDMV;
	}

	/**
	 * @return the lisDD
	 */
	public List<DeclarationDeces> getListDD() {
		return listDD;
	}

	/**
	 * @param lisDD the lisDD to set
	 */
	public void setListDD(List<DeclarationDeces> listDD) {
		this.listDD = listDD;
	}

	/**
	 * @return the listDDV
	 */
	public List<DeclarationDeces> getListDDV() {
		return listDDV;
	}

	/**
	 * @param listDDV the listDDV to set
	 */
	public void setListDDV(List<DeclarationDeces> listDDV) {
		this.listDDV = listDDV;
	}

	/**
	 * @return the selectJour
	 */
	public String getSelectJour() {
		return selectJour;
	}

	/**
	 * @param selectJour the selectJour to set
	 */
	public void setSelectJour(String selectJour) {
		this.selectJour = selectJour;
	}

	/**
	 * @return the jour
	 */
	public List<String> getJour() {
		return jour;
	}

	/**
	 * @param jour the jour to set
	 */
	public void setJour(List<String> jour) {
		this.jour = jour;
	}

	/**
	 * @return the listPiece
	 */
	public List<DelivredPieces> getListPiece() {
		
		return listPiece;
	}

	/**
	 * @param listPiece the listPiece to set
	 */
	public void setListPiece(List<DelivredPieces> listPiece) {
		this.listPiece = listPiece;
	}
	
	public void recette() {
		listPiece = new ArrayList<DelivredPieces>();
		if(!"".equals(this.getSelectJour()) && !"".equals(this.getSelectMois()) && !"".equals(this.getSelectAnnee())) {
			 dujour = this.getSelectJour()+"/"+Tools.getDateInt(this.getSelectMois())+"/"+this.getSelectAnnee();
			 statEnCours = "STATISTIQUES A LA DATE DU "+this.getSelectJour()+" "+this.getSelectMois().toUpperCase()+" "+this.getSelectAnnee();
			 listPiece = delService.findByDate(dujour);
			 
		 }
		 else if(!"".equals(this.getSelectMois()) && !"".equals(this.getSelectAnnee()) && "".equals(this.getSelectJour())) {
			 dujour = Tools.getDateInt(this.getSelectMois())+"/"+this.getSelectAnnee();
			 statEnCours = "STATISTIQUES A LA DATE DU MOIS DE "+this.getSelectMois().toUpperCase()+" DE L'ANNEE "+this.getSelectAnnee();
			 listPiece = delService.findByDate(dujour);
			 
		 }
		 else if(!"".equals(this.getSelectMois()) && "".equals(this.getSelectAnnee()) && !"".equals(this.getSelectJour())) {
			 dujour = this.getSelectJour()+"/"+Tools.getDateInt(this.getSelectMois());
			 statEnCours = "STATISTIQUES A LA DATE DU MOIS DE "+this.getSelectMois()+" DE CHAQUE ANNEE";
			 listPiece = delService.findByDate(dujour);
		 }
		 
		 else if("".equals(this.getSelectJour()) && "".equals(this.getSelectMois()) && !"".equals(this.getSelectAnnee())) {
			 dujour = this.getSelectAnnee();
			 statEnCours = "STAISTIQUES DE L'ANNEE "+this.getSelectAnnee();
			 listPiece = delService.findByDate(dujour);
		 }
		 else if("".equals(this.getSelectJour()) && !"".equals(this.getSelectMois()) && "".equals(this.getSelectAnnee())) {
			 dujour = Tools.getDateInt(this.getSelectMois());
			 statEnCours = "STATISTIQUES DU MOIS DE "+this.getSelectMois().toUpperCase()+" DE CHAQUE ANNEE";
			 listPiece = delService.findByDate(dujour);
		 }
		 else if(!"".equals(this.getSelectJour()) && "".equals(this.getSelectMois()) && "".equals(this.getSelectAnnee())) {
			 dujour = this.getSelectJour();
			 statEnCours = "STATISTIQUES DU "+this.getSelectJour()+ " DE CHAQUE MOIS";
			 listPiece = delService.findByDate(dujour);
		 }
		 else {
			 statEnCours = "STATISTIQUES DU "+Tools.getDayForDateTime(Tools.getCurrentDateDDMMYYYY())+" "
					 +Tools.getMoisLettre(Tools.getCurrentDateDDMMYYYY()).toUpperCase()+" "+Tools.getYearForDate(Tools.getCurrentDateDDMMYYYY());
			 listPiece = delService.findByDate(Tools.getCurrentDateDDMMYYYY());
			 
		 }
	}

	public String getRecettes() {
		selectAnnee = "";
		selectMois = "";
		selectJour = "";
		listPiece = new ArrayList<DelivredPieces>();
		delService = new DelivredPieceService();
		recettes = MyUtil.basePath()+"root/recettes.xhtml";
		listPiece = delService.findByDate(Tools.getCurrentDateDDMMYYYY());
		return recettes;
	}

	public void setRecettes(String recettes) {
		this.recettes = recettes;
	}
	
	
}
