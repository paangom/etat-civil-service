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

public class NonInscription {
	
	private PiecesAnnexesServices pService = new PiecesAnnexesServices();
	private DelivredPieceService dService = new DelivredPieceService();
	
	@SuppressWarnings("rawtypes")
	private Map parameter = new HashMap();
	JasperPrint jasperPrint;
	
	private String prenom;
	private String nom;
	private String type;
	private String lieu_naissance;
	private String prenom_pere;
	private String nom_pere;
	private String prenom_mere;
	private String nom_mere;
	private String profession;
	private String sexe;
	private Date dateNaissance;
	
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getLieu_naissance() {
		return lieu_naissance;
	}
	
	public void setLieu_naissance(String lieu_naissance) {
		this.lieu_naissance = lieu_naissance;
	}
	
	public String getPrenom_pere() {
		return prenom_pere;
	}
	
	public void setPrenom_pere(String prenom_pere) {
		this.prenom_pere = prenom_pere;
	}
	
	public String getNom_pere() {
		return nom_pere;
	}
	
	public void setNom_pere(String nom_pere) {
		this.nom_pere = nom_pere;
	}
	
	public String getPrenom_mere() {
		return prenom_mere;
	}
	
	public void setPrenom_mere(String prenom_mere) {
		this.prenom_mere = prenom_mere;
	}
	
	public String getNom_mere() {
		return nom_mere;
	}
	
	public void setNom_mere(String nom_mere) {
		this.nom_mere = nom_mere;
	}
	
	public String getProfession() {
		return profession;
	}
	
	public void setProfession(String profession) {
		this.profession = profession;
	}
	
	public String getSexe() {
		return sexe;
	}
	
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}
	
	public Date getDateNaissance() {
		return dateNaissance;
	}
	
	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
	
	
	@SuppressWarnings("unchecked")
	public void save() throws IOException{
        parameter.put("region",MyUtil.getUserLogged().getCentre().getCenterRegion());
		parameter.put("depart", MyUtil.getUserLogged().getCentre().getCenterDepartement());
		parameter.put("arrond", MyUtil.getUserLogged().getCentre().getCenterArrondissement());
		parameter.put("centre", "CENTRE "+MyUtil.getUserLogged().getCentre().getCenterType().toUpperCase()+" DE "+MyUtil.getUserLogged().getCentre().getCenterName().toUpperCase());
		parameter.put("nomCentre", MyUtil.getUserLogged().getCentre().getCenterName());
		parameter.put("commune", MyUtil.getUserLogged().getCentre().getCenterCollectivite().toUpperCase()+" DE "+MyUtil.getUserLogged().getCentre().getCommune().toUpperCase());
		parameter.put("typeCentre", MyUtil.getUserLogged().getCentre().getCenterType());
		parameter.put("personne", this.getPrenom()+" "+this.getNom());
		parameter.put("pere", this.getPrenom_pere()+" "+this.getNom_pere());
		parameter.put("mere", this.getPrenom_mere()+" "+this.getNom_mere());
		parameter.put("type", this.getType());
		parameter.put("profession", this.getProfession());
		parameter.put("dateC", Tools.getCurrentDateDDMMYYYY());
		parameter.put("officier", MyUtil.getUserLogged().getUserPrenom()+" "+MyUtil.getUserLogged().getUserNom());
		
		FacesContext context = FacesContext.getCurrentInstance();

		String reportSource = context.getExternalContext().getRealPath("ActeNPDF/pieces/certificatNonInscription.jasper");


		
		try {
			
			if(dService.addPiece(pService.findByCode(220))){
				jasperPrint = JasperFillManager.fillReport(reportSource, parameter, new JREmptyDataSource());
		  		HttpServletResponse httpServletResponse = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
		        servletOutputStream.close();
			}
			else{
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur d'ajout du document d�livr�!", null);
		        FacesContext.getCurrentInstance().addMessage(null, message);
			}
		} catch (JRException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur de chargement!", null);
	        FacesContext.getCurrentInstance().addMessage(null, message);
			e.printStackTrace();
		}
	}
	

}
