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

public class CertificatIndividualite {
	
	@SuppressWarnings("rawtypes")
	private Map parameter = new HashMap();
	JasperPrint jasperPrint;
	
	private PiecesAnnexesServices pService = new PiecesAnnexesServices();
	private DelivredPieceService dService = new DelivredPieceService();

	private String prenom_T1;
	private String nom_T1;
	private String cni_T1;
	private String adresse_T1;
	
	private String prenom_T2;
	private String nom_T2;
	private String cni_T2;
	private String adresse_T2;
	
	private String domicile_T;
	
	private String prenom;
	private String nom;
	private String date_N;
	private Date dateN;
	private String lieu_N;
	private String sexe;
	private String adresse;
	
	private String prenom_connu;
	private String nom_connu;
	private String profession;
	
	
	public String getPrenom_T1() {
		return prenom_T1;
	}
	public void setPrenom_T1(String prenom_T1) {
		this.prenom_T1 = prenom_T1;
	}
	public String getNom_T1() {
		return nom_T1;
	}
	public void setNom_T1(String nom_T1) {
		this.nom_T1 = nom_T1;
	}
	public String getCni_T1() {
		return cni_T1;
	}
	public void setCni_T1(String cni_T1) {
		this.cni_T1 = cni_T1;
	}
	public String getAdresse_T1() {
		return adresse_T1;
	}
	public void setAdresse_T1(String adresse_T1) {
		this.adresse_T1 = adresse_T1;
	}
	public String getPrenom_T2() {
		return prenom_T2;
	}
	public void setPrenom_T2(String prenom_T2) {
		this.prenom_T2 = prenom_T2;
	}
	public String getNom_T2() {
		return nom_T2;
	}
	public void setNom_T2(String nom_T2) {
		this.nom_T2 = nom_T2;
	}
	public String getCni_T2() {
		return cni_T2;
	}
	public void setCni_T2(String cni_T2) {
		this.cni_T2 = cni_T2;
	}
	public String getAdresse_T2() {
		return adresse_T2;
	}
	public void setAdresse_T2(String adresse_T2) {
		this.adresse_T2 = adresse_T2;
	}
	public String getDomicile_T() {
		return domicile_T;
	}
	public void setDomicile_T(String domicile_T) {
		this.domicile_T = domicile_T;
	}
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
	public String getDate_N() {
		return date_N;
	}
	public void setDate_N(String date_N) {
		this.date_N = date_N;
	}
	public Date getDateN() {
		return dateN;
	}
	public void setDateN(Date dateN) {
		this.dateN = dateN;
	}
	public String getLieu_N() {
		return lieu_N;
	}
	public void setLieu_N(String lieu_N) {
		this.lieu_N = lieu_N;
	}
	public String getPrenom_connu() {
		return prenom_connu;
	}
	public void setPrenom_connu(String prenom_connu) {
		this.prenom_connu = prenom_connu;
	}
	public String getNom_connu() {
		return nom_connu;
	}
	public void setNom_connu(String nom_connu) {
		this.nom_connu = nom_connu;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
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
	/**
	 * @return the adresse
	 */
	public String getAdresse() {
		return adresse;
	}
	/**
	 * @param adresse the adresse to set
	 */
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	

	@SuppressWarnings("unchecked")
	public void save() throws IOException{
		
		parameter.put("region", MyUtil.getUserLogged().getCentre().getCenterRegion());
		parameter.put("depart", MyUtil.getUserLogged().getCentre().getCenterDepartement());
		parameter.put("arrond", MyUtil.getUserLogged().getCentre().getCenterArrondissement());
		parameter.put("collect", MyUtil.getUserLogged().getCentre().getCenterCollectivite());
		parameter.put("commune", MyUtil.getUserLogged().getCentre().getCommune());
		parameter.put("typeCentre", MyUtil.getUserLogged().getCentre().getCenterType());
		parameter.put("nomCentre", MyUtil.getUserLogged().getCentre().getCenterName());
		parameter.put("temoin1", this.getPrenom_T1()+
				" "+this.getNom_T1()+
				" cni "+this.getCni_T1());
		parameter.put("temoin2", this.getPrenom_T2()+
				" "+this.getNom_T2()+
				" cni "+this.getCni_T2());
		parameter.put("adresse1", this.getAdresse_T1());
		parameter.put("adresse2", this.getAdresse_T2());
		parameter.put("cni1", this.getCni_T1());
		parameter.put("cni2", this.getCni_T2());
		parameter.put("domicile", this.getDomicile_T());
		parameter.put("personne", this.getPrenom()+
				" "+this.getNom());
		
		
		parameter.put("dateN", Tools.getformatDate(Tools.formatDay(this.getDateN())));
		parameter.put("lieuN", this.getLieu_N());
		parameter.put("nomConnu", this.getPrenom_connu()+" "+this.getNom_connu());
		parameter.put("profession", this.getProfession());
		parameter.put("dateC", Tools.getCurrentDateDDMMYYYY());
		parameter.put("officier", MyUtil.getUserLogged().getUserPrenom()+" "+MyUtil.getUserLogged().getUserNom());
		
		FacesContext context = FacesContext.getCurrentInstance();

		String reportSource = context.getExternalContext().getRealPath("ActeNPDF/pieces/CertificatIndividualite.jasper");


		try {
			if(dService.addPiece(pService.findByCode(270))){
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
