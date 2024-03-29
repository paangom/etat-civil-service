package models;
// Generated 24 mars 2014 10:24:07 by Hibernate Tools 3.6.0


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Users generated by hbm2java
 */
@Entity
@Table(name="users")
public class Users  implements java.io.Serializable {


	private static final long serialVersionUID = -731468984861123772L;
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
    private Integer userId;
	
	@Column(name = "Adresse", columnDefinition="varchar(80) default '' ", nullable=true)
    private String userAddress;
	
	@Column(name = "Nom", columnDefinition="varchar(40) default '' ", nullable=true)
    private String userNom;
	
	@Column(name = "Sexe", columnDefinition="varchar(20) default '' ", nullable=true)
    private String sexe;
	
	@Column(name = "Courriel", columnDefinition="varchar(90) default '' ", nullable=true, unique = true)
    private String userMail;
	
	@Column(name = "Telephone", columnDefinition="varchar(20) default '' ", nullable=true)
    private String userNumTel;
	
	@Column(name = "Password", columnDefinition="varchar(200) default '' ", nullable=false)
    private String userPassword;
	
	@Column(name = "Prenom", columnDefinition="varchar(80) default '' ", nullable=false)
    private String userPrenom;
	
	@Column(name = "Profil", columnDefinition="varchar(30) default '' ", nullable=false)
    private String userProfil;
	
	@Column(name = "pseudo", columnDefinition="varchar(40) default '' ", nullable=false, unique = true)
    private String userUserName;

	@Column(name = "cni", columnDefinition="varchar(20) default '' ", nullable=false, unique = true)
    private String cni;
	
	@Column(name = "etat", columnDefinition="boolean default true", nullable=false)
    private boolean etat;
	
	@Column(name = "modify", columnDefinition="boolean default false", nullable=false)
    private boolean modify;
	

	

	@ManyToOne
	(
			cascade={CascadeType.PERSIST, CascadeType.MERGE}
	)
	@JoinColumn(name="centre_Id")
    private Centres centre;
	
	@OneToMany(mappedBy="createurDeces")
	private List<DeclarationDeces> createurDeces;
	
	@OneToMany(mappedBy="modificateurDeces")
	private List<DeclarationDeces> modificateurDeces;
	
	@OneToMany(mappedBy="validateurDeces")
	private List<DeclarationDeces> validateurDeces;
	
	@OneToMany(mappedBy="createurMariage")
	private List<DeclarationMariage> createurMariage;
	
	@OneToMany(mappedBy="modificateurMariage")
	private List<DeclarationMariage> modificateurMariage;
	
	@OneToMany(mappedBy="validateurMariage")
	private List<DeclarationMariage> validateurMariage;
	
	@OneToMany(mappedBy="createurNaissance")
	private List<DeclarationNaissance> createurNaissance;
	
	@OneToMany(mappedBy="modificateurNaissance")
	private List<DeclarationNaissance> modificateurNaissance;
	
	@OneToMany(mappedBy="validateurNaissance")
	private List<DeclarationNaissance> validateurNaissance;

	
	@OneToMany(mappedBy="userdelivred")
	private List<DelivredPieces> userdelivred;
	
	@OneToMany(mappedBy="usermodify")
	private List<DelivredPieces> usermodify;
	

    public Users() {
    }

	
    public Users(String userUserName) {
        this.userUserName = userUserName;
    }
    public Users(Centres centres, String userAddress, String userNom, String userMail, String userNumTel, 
    		String userPassword, String userPrenom, String userProfil, String userUserName, boolean modify) {
       this.centre = centres;
       this.userAddress = userAddress;
       this.userNom = userNom;
       this.userMail = userMail;
       this.userNumTel = userNumTel;
       this.userPassword = userPassword;
       this.userPrenom = userPrenom;
       this.userProfil = userProfil;
       this.userUserName = userUserName;
       this.modify = modify;
    }


	public Integer getUserId() {
		return userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	public Centres getCentre() {
		return centre;
	}


	public void setCentre(Centres centres) {
		this.centre = centres;
	}


	public String getUserAddress() {
		return userAddress;
	}


	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}


	public String getUserNom() {
		return userNom;
	}


	public void setUserNom(String userNom) {
		this.userNom = userNom;
	}

	public String getUserNumTel() {
		return userNumTel;
	}


	public void setUserNumTel(String userNumTel) {
		this.userNumTel = userNumTel;
	}


	public String getUserPassword() {
		return userPassword;
	}


	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}


	public String getUserPrenom() {
		return userPrenom;
	}


	public void setUserPrenom(String userPrenom) {
		this.userPrenom = userPrenom;
	}


	public String getUserProfil() {
		return userProfil;
	}


	public void setUserProfil(String userProfil) {
		this.userProfil = userProfil;
	}


	public String getUserUserName() {
		return userUserName;
	}


	public void setUserUserName(String userUserName) {
		this.userUserName = userUserName;
	}


	public List<DeclarationDeces> getCreateurDeces() {
		return createurDeces;
	}


	public void setCreateurDeces(List<DeclarationDeces> createurDeces) {
		this.createurDeces = createurDeces;
	}


	public List<DeclarationDeces> getModificateurDeces() {
		return modificateurDeces;
	}


	public void setModificateurDeces(List<DeclarationDeces> modificateurDeces) {
		this.modificateurDeces = modificateurDeces;
	}


	public List<DeclarationDeces> getValidateurDeces() {
		return validateurDeces;
	}


	public void setValidateurDeces(List<DeclarationDeces> validateurDeces) {
		this.validateurDeces = validateurDeces;
	}


	public List<DeclarationMariage> getCreateurMariage() {
		return createurMariage;
	}


	public void setCreateurMariage(List<DeclarationMariage> createurMariage) {
		this.createurMariage = createurMariage;
	}


	public List<DeclarationMariage> getModificateurMariage() {
		return modificateurMariage;
	}


	public void setModificateurMariage(List<DeclarationMariage> modificateurMariage) {
		this.modificateurMariage = modificateurMariage;
	}


	public List<DeclarationMariage> getValidateurMariage() {
		return validateurMariage;
	}


	public void setValidateurMariage(List<DeclarationMariage> validateurMariage) {
		this.validateurMariage = validateurMariage;
	}


	public List<DeclarationNaissance> getCreateurNaissance() {
		return createurNaissance;
	}


	public void setCreateurNaissance(List<DeclarationNaissance> createurNaissance) {
		this.createurNaissance = createurNaissance;
	}


	public List<DeclarationNaissance> getModificateurNaissance() {
		return modificateurNaissance;
	}


	public void setModificateurNaissance(
			List<DeclarationNaissance> modificateurNaissance) {
		this.modificateurNaissance = modificateurNaissance;
	}


	public List<DeclarationNaissance> getValidateurNaissance() {
		return validateurNaissance;
	}


	public void setValidateurNaissance(List<DeclarationNaissance> validateurNaissance) {
		this.validateurNaissance = validateurNaissance;
	}


	public String getSexe() {
		return sexe;
	}


	public void setSexe(String sexe) {
		this.sexe = sexe;
	}


	public boolean isModify() {
		return modify;
	}


	public void setModify(boolean modify) {
		this.modify = modify;
	}


	public String getCni() {
		return cni;
	}


	public void setCni(String cni) {
		this.cni = cni;
	}

	/**
	 * @return the userdelivred
	 */
	public List<DelivredPieces> getUserdelivred() {
		return userdelivred;
	}


	/**
	 * @param userdelivred the userdelivred to set
	 */
	public void setUserdelivred(List<DelivredPieces> userdelivred) {
		this.userdelivred = userdelivred;
	}


	/**
	 * @return the etat
	 */
	public boolean isEtat() {
		return etat;
	}


	/**
	 * @param etat the etat to set
	 */
	public void setEtat(boolean etat) {
		this.etat = etat;
	}


	public String getUserMail() {
		return userMail;
	}


	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}
	
	
     
}


