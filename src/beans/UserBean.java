/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import models.Centres;
import models.DelivredPieces;
import models.Users;

import org.apache.commons.codec.digest.DigestUtils;
import org.primefaces.context.RequestContext;

import services.CentreServices;
import services.DelivredPieceService;
import services.UserServices;
import util.MyUtil;
import util.Tools;


public class UserBean implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Users userConnect = null;
    private Users userToCreate = null;
    private List<DelivredPieces> piecesDelNonVal=null;
    private List<DelivredPieces> piecesDelAll=null;
    private List<DelivredPieces> piecesDelVal=null;
    private List<Users> usersCon ;
    private List<Users> users = null;
    private Centres c = null;
    private CentreServices cService = new CentreServices();
    private Users selectedUser = null;
    private UserServices uService = new UserServices();
    private DelivredPieceService uPiece = new DelivredPieceService();
    private boolean connected;
    private boolean profilCon;
    private int paiement;
    private String ancienUserName;
    /**
     * Creates a new instance of userBean
     */

	public UserBean() {
        if(userToCreate == null){
            userToCreate = new Users();
        }
       
    }

    public Users getUserConnect() {
        userConnect = MyUtil.getUserLogged();
        return userConnect;
    }

    public List<Users> getUsers() {
        users = uService.getAllUser();
        return users;
    }
    
    
    

    public Centres getC() {
    	c = cService.getCentre();
		return c;
	}

	public void setC(Centres c) {
		this.c = c;
	}

	public Users getSelectedUser() {
        return selectedUser;
    }

    public Users getUserToCreate() {
        return userToCreate;
    }
    

    public void setSelectedUser(Users selectedUser) {
        this.selectedUser = selectedUser;
    }
    
    
    
    @SuppressWarnings({ "unused", "static-access" })
	public void updateProfil() {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String route = MyUtil.basePathLogin() + "login.xhtml";
        FacesMessage msg;
        boolean loggedIn;
        if(uService.findByUsername(this.ancienUserName) == null){
        	msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Le nom d'utilisateur "+this.getAncienUserName()+" n'est pas reconnu. Merci de saisir le bon nom.",null);
        	FacesContext context1 = FacesContext.getCurrentInstance();
            context1.getCurrentInstance().addMessage(null, msg);
        }
        else {
        	if(uService.findByUsername(this.userToCreate.getUserUserName()) == null){
        		this.userConnect.setUserUserName(this.userToCreate.getUserUserName());
        		this.userConnect.setUserPassword(DigestUtils.md5Hex(this.userToCreate.getUserPassword()));
        		if (this.uService.updateUser(this.userConnect)) {
                    loggedIn = false;
                    FacesContext contexts = FacesContext.getCurrentInstance();
                    HttpSession session = (HttpSession) contexts.getExternalContext().getSession(false);
                    session.invalidate();
                    context.addCallbackParam("isLogin", false);
                    //context.addCallbackParam("route", route);
                    String url = MyUtil.basePathLogin();
                    doRedirect(url);
                }
                else{
                    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erreur de de modification de votre profil",null);
                    FacesContext context1 = FacesContext.getCurrentInstance();
        	        context1.getCurrentInstance().addMessage(null, msg);
                 	
                 	context1.getExternalContext().getFlash().setKeepMessages(true);
                       
                }
        	}
        }
        
        
        
    }
    
    private void doRedirect(String url) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().redirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("static-access")
	public void btnCreateUser(){
        //UserDAO userDao = new UserDAOImp();
        String msg;
        
        this.userToCreate.setUserUserName(this.userToCreate.getUserPrenom().toLowerCase()+uService.lastUser());
        this.userToCreate.setUserPassword("passer");
        this.userToCreate.setEtat(true);
        this.userToCreate.setModify(false);
        this.userToCreate.setCentre(cService.getCentre());
        String mdp=this.userToCreate.getUserPassword();
        this.userToCreate.setUserPassword(DigestUtils.md5Hex(this.userToCreate.getUserPassword()));
        if(uService.findByUsername(this.userToCreate.getUserUserName()) == null){
	        if(uService.addUser(this.userToCreate)){
	            msg = "Merci de bien noter les param�tres de connexion sont Login: "+this.userToCreate.getUserUserName()+" et Mot de passe: "+mdp;
	            this.userToCreate = new Users();
	            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO ,msg, null);
	            FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
	        	
	        	context.getExternalContext().getFlash().setKeepMessages(true);
	        }else{
	            msg = "Erreur de cr�ation de l'utilisateur. Certaines informations existent d�j� (e-mail, cni)";
	             this.userToCreate = new Users();
	            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
	            FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
	        	
	        	context.getExternalContext().getFlash().setKeepMessages(true);
	        }
        }
        else{
        	 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ce nom d'utilisateur existe d�ja!", null);
	            FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
	        	
	        	context.getExternalContext().getFlash().setKeepMessages(true);
        }
        
    }
    
    @SuppressWarnings("static-access")
	public String saveFirstUser(){
    	String route = "";
    	//if(this.uService.viderUsers()){
    		this.userToCreate.setCentre(cService.getCentre());
    		this.userToCreate.setEtat(true);
    		this.userToCreate.setUserProfil("root");
    		this.userToCreate.setModify(true);
    		String mdp = this.userToCreate.getUserPassword();
    		String log = this.userToCreate.getUserUserName();
    		this.userToCreate.setUserPassword(DigestUtils.md5Hex(this.userToCreate.getUserPassword()));
    		if(uService.addUser(this.userToCreate)){
    			this.userToCreate = new Users();
                route = "/login?faces-redirect=true";
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Le super utilisateur est cr�e. Nom d'utilisateur: "+log+" Mot de passe: "+mdp, null);
                FacesContext context = FacesContext.getCurrentInstance();
                context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
            	FacesContext context1 = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) context1.getExternalContext().getSession(false);
                session.invalidate();
            }else{
            	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "Impossible de cr�er cet utilisateur.", null);
                FacesContext context = FacesContext.getCurrentInstance();
                context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
                
            }

    	return route;
    }
    
    @SuppressWarnings("static-access")
	public void btnUpdateUser(){
        
        String msg;
        String mdp=this.selectedUser.getUserPassword();
        this.selectedUser.setUserPassword(DigestUtils.md5Hex(this.selectedUser.getUserPassword()));
        if(uService.findByUsername(this.userToCreate.getUserUserName()) == null){
	        if(uService.updateUser(selectedUser)){
	            msg = "Votre nouveau login est: "+selectedUser.getUserUserName()+" et votre mot de passe est: "+mdp;
	            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  msg, null);
	            FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
	        	
	        	context.getExternalContext().getFlash().setKeepMessages(true);
	        }else{
	            msg = "Impossible de faire la modification. V�rifier les informations saisies.";
	            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  msg, null);
	            FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
	        	
	        	context.getExternalContext().getFlash().setKeepMessages(true);
	        }
        }
        else{
        	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ce nom d'utilisateur existe d�ja!", null);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getCurrentInstance().addMessage(null, message);
        	
        	context.getExternalContext().getFlash().setKeepMessages(true);
        }
        
        
    }
    
    
    @SuppressWarnings("static-access")
	public void updateUser(){
        //this.selectedUser = (User) this.users.getRowData();
        String msg;
        if(uService.findByUsername(this.userConnect.getUserUserName()) != null) {
        	msg = "La modification a �chou�, ce nom d'utilisateur existe d�j�!";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  msg, null);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getCurrentInstance().addMessage(null, message);
        }
        else {
        	this.userConnect.setUserPassword(DigestUtils.md5Hex(this.userConnect.getUserPassword()));
        	 if(uService.updateUser(this.userConnect)){
                 msg = "La modification de l'utilisateur s'est correctement pass�e.";
                 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  msg, null);
                 FacesContext context = FacesContext.getCurrentInstance();
                 context.getCurrentInstance().addMessage(null, message);
             	
             	context.getExternalContext().getFlash().setKeepMessages(true);
             }else{
                 msg = "Erreur de modification de l'utilisateur";
                 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  msg, null);
                 FacesContext context = FacesContext.getCurrentInstance();
                 context.getCurrentInstance().addMessage(null, message);
             	
             	context.getExternalContext().getFlash().setKeepMessages(true);
             }
        }
       
        
        
    }
    
    @SuppressWarnings("static-access")
	public void btnDeleteUser(){
    	String msg;
    	
        if(this.selectedUser.isEtat()){
        	this.selectedUser.setEtat(false);
        	
        	if(uService.deleteUser(this.selectedUser)){
        		msg = "L'utilisateur "+this.selectedUser.getUserPrenom()+" "+this.selectedUser.getUserNom()+" est d�sactiv� avec succ�s.";
            }else{
                msg = "Erreur de d�sactivation de "+this.selectedUser.getUserPrenom()+" "+this.selectedUser.getUserNom();
            }
        }
        else{
        	this.selectedUser.setEtat(true);
        	this.selectedUser.setModify(false);
        	this.selectedUser.setUserPassword(DigestUtils.md5Hex("passer"));
        	if(uService.deleteUser(this.selectedUser)){
                msg = "L'utilisateur "+this.selectedUser.getUserPrenom()+" "+this.selectedUser.getUserNom()+" est r�activ� avec le login: "+this.selectedUser.getUserUserName()+" et mot de passe: passer.";
            }else{
                msg = "Erreur de r�activation de "+this.selectedUser.getUserPrenom()+" "+this.selectedUser.getUserNom();
            }
        }
        
        //this.selectedUser.setId(3);
        
        
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  msg, null);
        FacesContext context = FacesContext.getCurrentInstance();
        context.getCurrentInstance().addMessage(null, message);
    	
    	context.getExternalContext().getFlash().setKeepMessages(true);
    }

	/**
	 * @return the usersCon
	 */
	@SuppressWarnings("rawtypes")
	public List<Users> getUsersCon() {
		 FacesContext facesContext = FacesContext.getCurrentInstance(); 
	        Map m = facesContext.getExternalContext().getSessionMap();
	        Iterator iterator = m.keySet().iterator();
	        List<Users> mnU = new ArrayList<Users>();
	        while(iterator.hasNext()){
	        	  Object key   = iterator.next();
	        	  Object value = m.get(key);
	        	  if(uService.findByUsername(value.toString()) != null)
	        		  mnU.add(uService.findByUsername((String) value));
	        	}
	        Set<Users> mySet = new HashSet<Users>(mnU);
	        usersCon = new ArrayList<Users>(mySet);
		return usersCon;
	}

	/**
	 * @param usersCon the usersCon to set
	 */
	public void setUsersCon(List<Users> usersCon) {
		this.usersCon = usersCon;
	}

	/**
	 * @return the connected
	 */
	public boolean isConnected() {
		if(connected == true)
			connected = false;
			else if(connected == false)
				connected = true;
			else
				connected = true;
		return connected;
	}

	/**
	 * @param connected the connected to set
	 */
	public void setConnected(boolean connected) {
		this.connected = connected;
	}


	/**
	 * @return the paiement
	 */
	public int getPaiement() {
		int somme = 0;
		for(int i=0; i<piecesDelNonVal.size(); i++) {
			somme = somme + Integer.parseInt(piecesDelNonVal.get(i).getPiecedelivred().getPrix());
		}
		paiement = somme;
		return paiement;
	}

	/**
	 * @param paiement the paiement to set
	 */
	public void setPaiement(int paiement) {
		this.paiement = paiement;
	}
	
	@SuppressWarnings("static-access")
	public void payer() {
		if(MyUtil.getProfil().equals("root")) {
			int x= 0;
			for(int i=0; i < piecesDelNonVal.size(); i++) {
				piecesDelNonVal.get(i).setPaiement(true);
				piecesDelNonVal.get(i).setDate_paiement(Tools.getCurrentDateTime());
				if(uPiece.updatePiece(piecesDelNonVal.get(i))) {
					x++;
				}
			}
			if(x==piecesDelNonVal.size()) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  "Paiement effectu� avec succ�s.", null);
		        FacesContext context = FacesContext.getCurrentInstance();
		        context.getCurrentInstance().addMessage(null, message);
		    	
		    	context.getExternalContext().getFlash().setKeepMessages(true);
			
			}
			else {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,  "Le paiement de certains pi�ces n'a pas �t� effectu� normalement..", null);
		        FacesContext context = FacesContext.getCurrentInstance();
		        context.getCurrentInstance().addMessage(null, message);
		    	
		    	context.getExternalContext().getFlash().setKeepMessages(true);
			
			}
		}
		else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "D�sol�! Vous �tes pas autoris� � effectuer cette t�che.", null);
	        FacesContext context = FacesContext.getCurrentInstance();
	        context.getCurrentInstance().addMessage(null, message);
	    	
	    	context.getExternalContext().getFlash().setKeepMessages(true);
		}
	}

	/**
	 * @return the ancienUserName
	 */
	public String getAncienUserName() {
		return ancienUserName;
	}

	/**
	 * @param ancienUserName the ancienUserName to set
	 */
	public void setAncienUserName(String ancienUserName) {
		this.ancienUserName = ancienUserName;
	}

	public List<DelivredPieces> getPiecesDelNonVal() {
		piecesDelNonVal = uPiece.findPieceNonValiderByUser(this.selectedUser);
		return piecesDelNonVal;
	}

	public void setPiecesDelNonVal(List<DelivredPieces> piecesDelNonVal) {
		this.piecesDelNonVal = piecesDelNonVal;
	}

	public List<DelivredPieces> getPiecesDelAll() {
		piecesDelAll = uPiece.getAllPieceBU(this.selectedUser);
		return piecesDelAll;
	}

	public void setPiecesDelAll(List<DelivredPieces> piecesDelAll) {
		this.piecesDelAll = piecesDelAll;
	}

	public List<DelivredPieces> getPiecesDelVal() {
		piecesDelVal = uPiece.findPieceValiderByUser(this.selectedUser);
		return piecesDelVal;
	}

	public void setPiecesDelVal(List<DelivredPieces> piecesDelVal) {
		this.piecesDelVal = piecesDelVal;
	}

	public boolean isProfilCon() {
		if(MyUtil.getProfil().equals("root"))
			profilCon = true;
		else
			profilCon = false;
		return profilCon;
	}

	public void setProfilCon(boolean profilCon) {
		this.profilCon = profilCon;
	}
    
}
