/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import models.Users;
import services.ActivationServices;
import services.UserServices;

/**
 *
 * @author sambasow
 */
public class MyUtil {
	
	private static boolean licence;
	private ActivationServices activeService = new ActivationServices();

    public static Users getUserLogged() {
    	Users user = null;
    	UserServices uServices = new UserServices();
	    String userName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login");
	    user = uServices.findByUsername(userName);
    	return user;
    }

    public static String getUserName() {
    	Users u = getUserLogged();
    	return u.getUserUserName();
    }
    
    public static String getProfil(){
    	if(getUserLogged() != null)
	    	return getUserLogged().getUserProfil();
	    else
	    	return null;
    }
    
    public static int getUserId() {
    	Users u = getUserLogged();
	    return u.getUserId();
    }

    public static String baseUrl() {
    	return "/";
    }
    
    public static String basePathLogin() {
    	return "/"+getApplicationUri()+"/";
    }

    public static String basePath() {
    	String baseUrl = "/faces/pages/";
	    return baseUrl;
    	
    }
    
    public static String basePathAdmin() {
    	String baseUrl = "/faces/pages/configuration/";
	    return baseUrl;
    }
    
    public static String pathLogin(){
    	return "/login?faces-redirect=true";
    }
    
    public static String pathDeclaration() {
    	return basePath()+"declaration/";
    }
    
    public static String pathConsultationDeclaration() {
    	return basePath()+"consultation/declaration/";
    }
    
    public static String pathConsultationActe() {
    	return basePath()+"consultation/acte/";
    }
    
    public static String pathModificationDeclaration() {
    	return basePath()+"modification/declaration/";
    }
    
    public static String pathModificationActe() {
    	return basePath()+"modification/acte/";
    }
    
    public static String pathMAJMM() {
    	return basePath()+"modification/mentions/";
    }
    
    public static String pathRegistre() {
    	return "/faces/pages/registre/";
    }
    
    public static String getApplicationUri() {
    	try {
	  	    FacesContext ctxt = FacesContext.getCurrentInstance();
	  	    ExternalContext ext = ctxt.getExternalContext();
	  	    URI uri = new URI(ext.getRequestScheme(),
	  	          null, ext.getRequestServerName(), ext.getRequestServerPort(),
	  	          ext.getRequestContextPath(), null, null);
	  	    String t[]= uri.toString().split("/");
	  	    return t[t.length-1];
	    	} catch (URISyntaxException e) {
	    		throw new FacesException(e);
	    	}
    }

	public boolean isLicence() {
		
		if(activeService.getActive() != null && (new Date().getTime() > activeService.getActive().getTps_Ecoule())){
			if(activeService.update(activeService.getActive())){
				if(activeService.getActive().getTps_Ecoule() < activeService.getActive().getTps_Expiration()){
					licence = true;
				}
				else{
					activeService.deseable(activeService.getActive());
					licence = false;
				}
			}
			else
				licence = false;
			
		}
		else
			licence = false;
		
		return licence;
	}

	@SuppressWarnings("static-access")
	public void setLicence(boolean licence) {
		this.licence = licence;
	}
}
