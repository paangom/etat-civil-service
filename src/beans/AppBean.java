package beans;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import models.Users;
import services.CentreServices;
import util.MyUtil;


public class AppBean {
	
	private Users userLogging;
	private CentreServices cService = new CentreServices();
	
	
	public AppBean(){
	}
	
	public String getBaseUrl(){
		return MyUtil.baseUrl();
    }

	public String getBasePath(){
		return MyUtil.basePath();
		
    }

	/**
	 * @param userLogging the userLogging to set
	 */
	public void setUserLogging(Users userLogging) {
		this.userLogging = userLogging;
	}

	/**
	 * @return the userLogging
	 */
	public Users getUserLogging() {
		return userLogging;
	}
	
	public void configure(ComponentSystemEvent event){
		String url = "";
		if(cService.getAllCentre() != null)
			url = "/login?faces-redirect=true";
		doRedirect(url);
	}
	
 	 /**
 	 * Method for redirecting a request
 	 * @param url
 	 */
 	 private void doRedirect(String url){
 	  try {
 	   FacesContext context=FacesContext.getCurrentInstance();
 	   context.getExternalContext().redirect(url);
 	  } catch (IOException e) {
 	   e.printStackTrace();
 	  }
 	 }
  
}
