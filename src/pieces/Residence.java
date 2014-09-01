package pieces;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
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

public class Residence {
	
	private PiecesAnnexesServices pService = new PiecesAnnexesServices();
	private DelivredPieceService dService = new DelivredPieceService();
	
	@SuppressWarnings("rawtypes")
	private Map parameter = new HashMap();
	JasperPrint jasperPrint;
	
	private String prenom;
	private String nom;
	private String lieu_naissance;
	private String sexe;
	private String lieu_residence;
	private String motif;
	private String quartier;
	
	private Date dateNaissance;
	private Date dateResidence;
	
	
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
	public String getLieu_naissance() {
		return lieu_naissance;
	}
	public void setLieu_naissance(String lieu_naissance) {
		this.lieu_naissance = lieu_naissance;
	}


	public String getSexe() {
		return sexe;
	}
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}
	
	public String getLieu_residence() {
		return lieu_residence;
	}
	public void setLieu_residence(String lieu_residence) {
		this.lieu_residence = lieu_residence;
	}
	public Date getDateNaissance() {
		return dateNaissance;
	}
	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
	public Date getDateResidence() {
		return dateResidence;
	}
	public void setDateResidence(Date dateResidence) {
		this.dateResidence = dateResidence;
	}
	
	/**
	 * @return the motif
	 */
	public String getMotif() {
		return motif;
	}
	/**
	 * @param motif the motif to set
	 */
	public void setMotif(String motif) {
		this.motif = motif;
	}
	/**
	 * @return the quartier
	 */
	public String getQuartier() {
		return quartier;
	}
	/**
	 * @param quartier the quartier to set
	 */
	public void setQuartier(String quartier) {
		this.quartier = quartier;
	}
	@SuppressWarnings({ "unchecked" })
	public void save(ActionEvent actionEvent) throws JRException, IOException {
		parameter.put("region", "REGION DE "+MyUtil.getUserLogged().getCentre().getCenterRegion());
		parameter.put("depart", "DEPARTEMENT DE "+MyUtil.getUserLogged().getCentre().getCenterDepartement());
		parameter.put("arrond", "ARRONDISSEMENT DE "+MyUtil.getUserLogged().getCentre().getCenterArrondissement());
		parameter.put("centre", "CENTRE "+MyUtil.getUserLogged().getCentre().getCenterType()+" DE "+MyUtil.getUserLogged().getCentre().getCenterName());
		parameter.put("nomcentre", MyUtil.getUserLogged().getCentre().getCenterName());
		parameter.put("commune", MyUtil.getUserLogged().getCentre().getCenterCollectivite()+" de "+MyUtil.getUserLogged().getCentre().getCenterName());
		parameter.put("motif", this.getMotif());
		parameter.put("personne", this.getPrenom()+" "+this.getNom());
		parameter.put("dateN", Tools.getformatDate(Tools.formatDay(this.getDateNaissance())));
		parameter.put("lieuN", this.getLieu_naissance());
		parameter.put("residence", this.getLieu_residence());
		parameter.put("quartier", this.getQuartier());
		parameter.put("dateR",  Tools.getformatDate(Tools.formatDay(this.getDateResidence())));
		parameter.put("dateC", Tools.getCurrentDateDDMMYYYY());
		parameter.put("officier", MyUtil.getUserLogged().getUserPrenom()+" "+MyUtil.getUserLogged().getUserNom());
		
		
		if(dService.addPiece(pService.findByCode(250))){	
			FacesContext context = FacesContext.getCurrentInstance();
			String reportSource = context.getExternalContext().getRealPath("ActeNPDF/pieces/residence.jasper");
			jasperPrint = JasperFillManager.fillReport(reportSource, parameter, new JREmptyDataSource());
		    HttpServletResponse httpServletResponse = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		    ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		    JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur d'ajout du document d�livr�!", null);
		    FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
	}
	
	

}
