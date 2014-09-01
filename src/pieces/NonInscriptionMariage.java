package pieces;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import services.DelivredPieceService;
import services.PiecesAnnexesServices;
import util.MyUtil;
import util.Tools;

public class NonInscriptionMariage {
	
	@SuppressWarnings("rawtypes")
	private Map parameter = new HashMap();
	JasperPrint jasperPrint;
	private PiecesAnnexesServices pService = new PiecesAnnexesServices();
	private DelivredPieceService dService = new DelivredPieceService();
	
	private String prenom_epoux;
	private String nom_epoux;
	private String lieu_naissance_epoux;
	private String prenom_pere_epoux;
	private String nom_pere_epoux;
	private String prenom_mere_epoux;
	private String nom_mere_epoux;
	private String profession_epoux;
	private String adresse_epoux;
	
	private String prenom_epouse;
	private String nom_epouse;
	private String lieu_naissance_epouse;
	private String prenom_pere_epouse;
	private String nom_pere_epouse;
	private String prenom_mere_epouse;
	private String nom_mere_epouse;
	private String profession_epouse;
	private String adresse_epouse;
	
	String lieuM;
	
	private Date dateNaissanceEpoux;
	private Date dateNaissanceEpouse;
	private Date dateMariage;
	
	
	public String getPrenom_epoux() {
		return prenom_epoux;
	}
	public void setPrenom_epoux(String prenom_epoux) {
		this.prenom_epoux = prenom_epoux;
	}
	public String getNom_epoux() {
		return nom_epoux;
	}
	public void setNom_epoux(String nom_epoux) {
		this.nom_epoux = nom_epoux;
	}
	
	public String getLieu_naissance_epoux() {
		return lieu_naissance_epoux;
	}
	public void setLieu_naissance_epoux(String lieu_naissance_epoux) {
		this.lieu_naissance_epoux = lieu_naissance_epoux;
	}
	public String getPrenom_pere_epoux() {
		return prenom_pere_epoux;
	}
	public void setPrenom_pere_epoux(String prenom_pere_epoux) {
		this.prenom_pere_epoux = prenom_pere_epoux;
	}
	public String getPrenom_mere_epoux() {
		return prenom_mere_epoux;
	}
	public void setPrenom_mere_epoux(String prenom_mere_epoux) {
		this.prenom_mere_epoux = prenom_mere_epoux;
	}
	public String getNom_mere_epoux() {
		return nom_mere_epoux;
	}
	public void setNom_mere_epoux(String nom_mere_epoux) {
		this.nom_mere_epoux = nom_mere_epoux;
	}
	public String getProfession_epoux() {
		return profession_epoux;
	}
	public void setProfession_epoux(String profession_epoux) {
		this.profession_epoux = profession_epoux;
	}
	public String getAdresse_epoux() {
		return adresse_epoux;
	}
	public void setAdresse_epoux(String adresse_epoux) {
		this.adresse_epoux = adresse_epoux;
	}
	public String getPrenom_epouse() {
		return prenom_epouse;
	}
	public void setPrenom_epouse(String prenom_epouse) {
		this.prenom_epouse = prenom_epouse;
	}
	public String getNom_epouse() {
		return nom_epouse;
	}
	public void setNom_epouse(String nom_epouse) {
		this.nom_epouse = nom_epouse;
	}
	
	public String getLieu_naissance_epouse() {
		return lieu_naissance_epouse;
	}
	public void setLieu_naissance_epouse(String lieu_naissance_epouse) {
		this.lieu_naissance_epouse = lieu_naissance_epouse;
	}
	public String getPrenom_pere_epouse() {
		return prenom_pere_epouse;
	}
	public void setPrenom_pere_epouse(String prenom_pere_epouse) {
		this.prenom_pere_epouse = prenom_pere_epouse;
	}
	public String getPrenom_mere_epouse() {
		return prenom_mere_epouse;
	}
	public void setPrenom_mere_epouse(String prenom_mere_epouse) {
		this.prenom_mere_epouse = prenom_mere_epouse;
	}
	public String getNom_mere_epouse() {
		return nom_mere_epouse;
	}
	public void setNom_mere_epouse(String nom_mere_epouse) {
		this.nom_mere_epouse = nom_mere_epouse;
	}
	public String getProfession_epouse() {
		return profession_epouse;
	}
	public void setProfession_epouse(String profession_epouse) {
		this.profession_epouse = profession_epouse;
	}
	public String getAdresse_epouse() {
		return adresse_epouse;
	}
	public void setAdresse_epouse(String adresse_epouse) {
		this.adresse_epouse = adresse_epouse;
	}
	
	
	/**
	 * @return the lieuM
	 */
	public String getLieuM() {
		return lieuM;
	}
	/**
	 * @param lieuM the lieuM to set
	 */
	public void setLieuM(String lieuM) {
		this.lieuM = lieuM;
	}
	public Date getDateNaissanceEpoux() {
		return dateNaissanceEpoux;
	}
	public void setDateNaissanceEpoux(Date dateNaissanceEpoux) {
		this.dateNaissanceEpoux = dateNaissanceEpoux;
	}
	public Date getDateNaissanceEpouse() {
		return dateNaissanceEpouse;
	}
	public void setDateNaissanceEpouse(Date dateNaissanceEpouse) {
		this.dateNaissanceEpouse = dateNaissanceEpouse;
	}
	public Date getDateMariage() {
		return dateMariage;
	}
	public void setDateMariage(Date dateMariage) {
		this.dateMariage = dateMariage;
	}
	public String getNom_pere_epoux() {
		return nom_pere_epoux;
	}
	public void setNom_pere_epoux(String nom_pere_epoux) {
		this.nom_pere_epoux = nom_pere_epoux;
	}
	public String getNom_pere_epouse() {
		return nom_pere_epouse;
	}
	public void setNom_pere_epouse(String nom_pere_epouse) {
		this.nom_pere_epouse = nom_pere_epouse;
	}
	
	@SuppressWarnings("unchecked")
	public void save() throws IOException, JRException{
		
		parameter.put("region", MyUtil.getUserLogged().getCentre().getCenterRegion());
		parameter.put("depart", MyUtil.getUserLogged().getCentre().getCenterDepartement());
		parameter.put("arrond", MyUtil.getUserLogged().getCentre().getCenterArrondissement());
		parameter.put("collect", MyUtil.getUserLogged().getCentre().getCenterCollectivite());
		parameter.put("commune", MyUtil.getUserLogged().getCentre().getCenterName());
		parameter.put("typeCentre", MyUtil.getUserLogged().getCentre().getCenterType());
		parameter.put("nomCentre", MyUtil.getUserLogged().getCentre().getCenterName());
		parameter.put("epoux", this.getPrenom_epoux()+
				" "+this.getNom_epoux());
		parameter.put("naissanceEpoux", Tools.getformatDate(Tools.formatDay(this.getDateNaissanceEpoux()))+
				" à "+this.getLieu_naissance_epoux());
		parameter.put("pereEpoux", this.getPrenom_pere_epoux()+
				" "+this.getNom_pere_epoux());
		parameter.put("mereEpoux", this.getPrenom_mere_epoux()+
				" "+this.getNom_mere_epoux());
		parameter.put("epouse", this.getPrenom_epouse()+
				" "+this.getNom_epouse());
		parameter.put("naissanceEpouse", Tools.getformatDate(Tools.formatDay(this.getDateNaissanceEpouse()))+
				" à "+this.getLieu_naissance_epouse());
		parameter.put("pereEpouse", this.getPrenom_pere_epouse()+
				" "+this.getNom_pere_epouse());
		parameter.put("mereEpouse", this.getPrenom_mere_epouse()+
				" "+this.getNom_mere_epouse());
		parameter.put("dateM", Tools.getformatDate(Tools.formatDay(this.getDateMariage())));
		parameter.put("lieuM", this.getLieuM());
		
		parameter.put("dateC", Tools.getCurrentDateDDMMYYYY());
		parameter.put("officier", MyUtil.getUserLogged().getUserPrenom()+" "+MyUtil.getUserLogged().getUserNom());
		
		FacesContext context = FacesContext.getCurrentInstance();

		String reportSource = context.getExternalContext().getRealPath("ActeNPDF/pieces/CertificatNonInscriptionMariage.jasper");

		try {
			if(dService.addPiece(pService.findByCode(310))){
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

}
