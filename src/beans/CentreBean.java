package beans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionListener;

import models.Activation;
import models.Centres;
import models.Licences;
import services.ActivationServices;
import services.CentreServices;
import services.LicencesServices;
import util.Tools;

public class CentreBean {

	private CentreServices cenServ = new CentreServices();
	private LicencesServices licService = new LicencesServices();
	private ActivationServices activeService = new ActivationServices();
    private Centres centreToAdd = null;
    private Centres centreToUpdate = null;
   // private Activation activation = null;
    //private Licences licence = null;
    private String mandat;
    private String code;
    private Centres centreToConsult = null;
    private boolean centre;
    private boolean etatLicence;
    private boolean alert;
    private int joursRestants;    
    
    /**
     * Creates a new instance of centreBean
     */
    public CentreBean() {
        if(centreToAdd == null){
            centreToAdd = new Centres();
        }
        centreToUpdate = cenServ.getCentre();
        centreToConsult = cenServ.getCentre();
       
    }
    
    

    public Centres getCentreToAdd() {
        return centreToAdd;
    }

    public Centres getCentreToUpdate() {
        
        return centreToUpdate;
    }

   public void configureCentre(ActionListener event){
       
   }
   
   @SuppressWarnings("static-access")
   public void updateConfigurationCentre(){
       		this.centreToUpdate.setAcces(Tools.formatDay(this.centreToUpdate.getAccesD()));
        if (cenServ.modifyCenter(this.centreToUpdate)) {
        	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Les informations du centre d'Etat Civil sont mises à jour", null);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getCurrentInstance().addMessage(null, message);
            context.getExternalContext().getFlash().setKeepMessages(true);
        }
        else{
        	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Echec de la mise à jour des informations du centre d'Etat Civil", null);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getCurrentInstance().addMessage(null, message);
            context.getExternalContext().getFlash().setKeepMessages(true);
        
       	}
       
   }
   
   @SuppressWarnings("static-access")
public String saveCentre(){
	   String route = "";
	   		this.centreToAdd.setDate_Install(Tools.getCurrentDateDDMMYYYY());
	   		this.centreToAdd.setAcces(Tools.formatDay(this.centreToAdd.getAccesD()));
	   		if(cenServ.createCenter(this.centreToAdd)){
			   FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Initialisation du centre effectué avec succès", null);
               FacesContext context = FacesContext.getCurrentInstance();
               context.getCurrentInstance().addMessage(null, message);
               context.getExternalContext().getFlash().setKeepMessages(true);
               route = "/pages/install/root?faces-redirect=true";
		   }
		   else{
			   FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Impossible de créer le centre. Veuillez vérifier les informations.", null);
               FacesContext context = FacesContext.getCurrentInstance();
               context.getCurrentInstance().addMessage(null, message);
           	
           	context.getExternalContext().getFlash().setKeepMessages(true);
		   }
	   
	   
	   return route;
	   
   }




/**
 * @return the mandat
 */
public String getMandat() {
	String deb = cenServ.getCentre().getAcces().substring(6);
	String fin = (Integer.parseInt(deb) + 5)+"";
	mandat = deb+" - "+fin;
	return mandat;
}



/**
 * @param mandat the mandat to set
 */
public void setMandat(String mandat) {
	this.mandat = mandat;
}



public Centres getCentreToConsult() {
	return centreToConsult;
}



public void setCentreToConsult(Centres centreToConsult) {
	this.centreToConsult = centreToConsult;
}



public boolean isCentre() {
	if(this.cenServ.getCentre() != null)
		   centre = true;
	   else
		   centre = false;
	return centre;
}



public void setCentre(boolean centre) {
	this.centre = centre;
}


public boolean isEtatLicence() {
	if(activeService.getActive() != null && (new Date().getTime() > activeService.getActive().getTps_Ecoule())){
		if(activeService.update(activeService.getActive())){
			if(activeService.getActive().getTps_Ecoule() < activeService.getActive().getTps_Expiration()){
				etatLicence = true;
			}
			else{
				activeService.deseable(activeService.getActive());
				etatLicence = false;
			}
		}
		else
			etatLicence = false;
		
	}
	else
		etatLicence = false;
	return etatLicence;
}



public void setEtatLicence(boolean etatLicence) {
	this.etatLicence = etatLicence;
}



public String getCode() {
	return code;
}



public void setCode(String code) {
	this.code = code;
}

	@SuppressWarnings("static-access")
	public String activation(){
		String route = "";
		String codeCentre = cenServ.getCentre().getCenterCode();
		String msg = "Le code d'activation que vous avez saisi est incorrect. Veuillez saisir le bon code.";
		if(!"".equals(this.getCode()) && this.getCode().length() == 71){
			String codeClair = Tools.plainText(this.getCode().trim());
			String sa1 = codeClair.substring(0, 8);
			String duree = codeClair.substring(8, 13);
			String service1 = codeClair.substring(13, 20);
			
			String etatcivil = codeClair.substring(28, 37);
			
			String dateDeb = codeClair.substring(20, 28);
			
			String dateFin = codeClair.substring(37, 45);
			
			String datDN = dateDeb.substring(0, 2)+"/"+dateDeb.substring(2, 4)+"/"+dateDeb.substring(4);

			String datFN = dateFin.substring(0, 2)+"/"+dateFin.substring(2, 4)+"/"+dateFin.substring(4);
			
			String service2 = codeClair.substring(45, 52);
			
			String age = codeClair.substring(52, 60);
			
			String cod = codeClair.substring(65);
			long date_deb = 0;
			long date_fin = 0;
			
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			try {
				 
				Date date1 = formatter.parse(datDN);
				Date date2 = formatter.parse(datFN);
				date_deb = date1.getTime();
				date_fin = date2.getTime();
		 
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
			long date_Courant = new Date().getTime();
			int dur = Integer.parseInt(duree);
			long dureeN = dur*24*3600*1000;
			
			if((sa1.equals("SYSCOWEB")) && (etatcivil.equals("ETATCIVIL")) && (cod.equals(codeCentre)) &&
					(service1.equals(service2)) && (service1.equals("SERVICE")) && (duree.length() == 5) &&
					(dateDeb.length() == 8) && (dateFin.length() == 8) && (age.length() == 8) &&
					(date_deb <= date_Courant)){
				Licences lic = new Licences(code, date_deb, dureeN, date_fin);
				
				if(licService.addLicence(lic)){
					Activation act = new Activation(new Date().getTime(), date_fin);
					if(activeService.enable(act)){
						msg = "Activation effectuée avec succès. Valable jusqu'au "+datFN;
						FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
		        		FacesContext context = FacesContext.getCurrentInstance();
		                context.getCurrentInstance().addMessage(null, message);
		            	
		            	context.getExternalContext().getFlash().setKeepMessages(true);
						route = "/login?faces-redirect=true";
					}
					else{
						msg = "Erreur d'activation, essaiyez à nouveau.";
						FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
		        		FacesContext context = FacesContext.getCurrentInstance();
		                context.getCurrentInstance().addMessage(null, message);
		            	
		            	context.getExternalContext().getFlash().setKeepMessages(true);
					}
				}
				else{
					msg = "Erreur d'activation, essaiyez à nouveau.";
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
	        		FacesContext context = FacesContext.getCurrentInstance();
	                context.getCurrentInstance().addMessage(null, message);
	            	
	            	context.getExternalContext().getFlash().setKeepMessages(true);
				}
			}
			else{
				msg = "Le code d'activation que vous avez saisi est incorrect";
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
	    		FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
	        	
	        	context.getExternalContext().getFlash().setKeepMessages(true);
			}
		}
		
		return route;
	}



	public boolean isAlert() {
		long sec = activeService.getActive().getTps_Expiration();
		Date fin = new Date(sec);
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String datefin = df.format(fin);
		int nbrJ = Tools.nbJours(Tools.getCurrentDateDDMMYYYY(), datefin);
		if(nbrJ <= 15){
			alert = true;
			joursRestants = nbrJ;
		}
		else{
			alert = false;
		}
		return alert;
	}



	public void setAlert(boolean alert) {
		this.alert = alert;
	}



	public int getJoursRestants() {
		return joursRestants;
	}



	public void setJoursRestants(int joursRestants) {
		this.joursRestants = joursRestants;
	}

}
