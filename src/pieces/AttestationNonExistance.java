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

public class AttestationNonExistance {
	
	
	private PiecesAnnexesServices pService = new PiecesAnnexesServices();
	private DelivredPieceService dService = new DelivredPieceService();
	
	@SuppressWarnings("rawtypes")
	private Map parameter = new HashMap();
	JasperPrint jasperPrint;

	
	private String prenom;
	private String nom;
	private String numero;
	private String annee;
	private String lieu_N;
	private String prenom_P;
	private String nom_P;
	private String prenom_M;
	private String nom_M;
	private String sexe;
	
	private Date dateN;
	
	
	
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
	
	public String getLieu_N() {
		return lieu_N;
	}
	public void setLieu_N(String lieu_N) {
		this.lieu_N = lieu_N;
	}
	
	public String getPrenom_P() {
		return prenom_P;
	}
	public void setPrenom_P(String prenom_P) {
		this.prenom_P = prenom_P;
	}
	public String getNom_P() {
		return nom_P;
	}
	public void setNom_P(String nom_P) {
		this.nom_P = nom_P;
	}
	
	public String getPrenom_M() {
		return prenom_M;
	}
	public void setPrenom_M(String prenom_M) {
		this.prenom_M = prenom_M;
	}
	public String getNom_M() {
		return nom_M;
	}
	public void setNom_M(String nom_M) {
		this.nom_M = nom_M;
	}
	

	public Date getDateN() {
		return dateN;
	}
	public void setDateN(Date dateN) {
		this.dateN = dateN;
	}
	
	
	/**
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}
	/**
	 * @param numero the numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}
	/**
	 * @return the annee
	 */
	public String getAnnee() {
		return annee;
	}
	/**
	 * @param annee the annee to set
	 */
	public void setAnnee(String annee) {
		this.annee = annee;
	}
	/**
	 * @return the sexe
	 */
	public String getSexe() {
		return sexe;
	}
	/**
	 * @param sexe the sexe to set
	 */
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}
	@SuppressWarnings("unchecked")
	public void save() throws IOException, JRException {
		parameter.put("region", MyUtil.getUserLogged().getCentre().getCenterRegion());
		parameter.put("depart", MyUtil.getUserLogged().getCentre().getCenterDepartement());
		parameter.put("arrond", MyUtil.getUserLogged().getCentre().getCenterArrondissement());
		parameter.put("centre", MyUtil.getUserLogged().getCentre().getCenterType());
		parameter.put("nomcentre", MyUtil.getUserLogged().getCentre().getCenterName());
		parameter.put("numero", this.getNumero());
		parameter.put("pere", this.getPrenom_P()+" "+this.getNom_P());
		parameter.put("personne", this.getPrenom()+" "+this.getNom());
		parameter.put("dateN", Tools.getformatDate(Tools.formatDay(this.getDateN())));
		parameter.put("lieuN", this.getLieu_N());
		parameter.put("mere", this.getPrenom_M()+" "+this.getNom_M());
		parameter.put("annee", this.getAnnee());
		parameter.put("dateC", Tools.getCurrentDateDDMMYYYY());
		parameter.put("officier", MyUtil.getUserLogged().getUserPrenom()+" "+MyUtil.getUserLogged().getUserNom());
		
		FacesContext context = FacesContext.getCurrentInstance();

		String reportSource = context.getExternalContext().getRealPath("ActeNPDF/pieces/certificatNonExistance.jrxml");

		try {
			if(dService.addPiece(pService.findByCode(240))){
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
