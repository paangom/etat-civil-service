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

public class CertificatNonDivorceNonSeparation {
	
	@SuppressWarnings("rawtypes")
	private Map parameter = new HashMap();
	JasperPrint jasperPrint;
	
	private PiecesAnnexesServices pService = new PiecesAnnexesServices();
	private DelivredPieceService dService = new DelivredPieceService();

	private String prenom_T1;
	private String nom_T1;
	private String cni_T1;
	private String prenom_T2;
	private String nom_T2;
	private String cni_T2;
	
	private String prenom_Epouse;
	private String nom_Epouse;
	private String lieu_NEpouse;
	private Date dateNEpouse;
	private String prenom_PEpouse;
	private String nom_PEpouse;
	private String prenom_MEpouse;
	private String nom_MEpouse;
	
	private String prenom_Epoux;
	private String nom_Epoux;
	private String lieu_NEpoux;
	private Date dateNEpoux;
	private String prenom_PEpoux;
	private String nom_PEpoux;
	private String prenom_MEpoux;
	private String nom_MEpoux;
	private Date dateDEpoux;
	private String lieu_DEpoux;
	
	private String numActeM;
	private String annee;
	
	
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
	public String getPrenom_Epouse() {
		return prenom_Epouse;
	}
	public void setPrenom_Epouse(String prenom_Epouse) {
		this.prenom_Epouse = prenom_Epouse;
	}
	public String getNom_Epouse() {
		return nom_Epouse;
	}
	public void setNom_Epouse(String nom_Epouse) {
		this.nom_Epouse = nom_Epouse;
	}
	
	public String getLieu_NEpouse() {
		return lieu_NEpouse;
	}
	public void setLieu_NEpouse(String lieu_NEpouse) {
		this.lieu_NEpouse = lieu_NEpouse;
	}
	public Date getDateNEpouse() {
		return dateNEpouse;
	}
	public void setDateNEpouse(Date dateNEpouse) {
		this.dateNEpouse = dateNEpouse;
	}
	public String getPrenom_PEpouse() {
		return prenom_PEpouse;
	}
	public void setPrenom_PEpouse(String prenom_PEpouse) {
		this.prenom_PEpouse = prenom_PEpouse;
	}
	public String getNom_PEpouse() {
		return nom_PEpouse;
	}
	public void setNom_PEpouse(String nom_PEpouse) {
		this.nom_PEpouse = nom_PEpouse;
	}
	public String getPrenom_MEpouse() {
		return prenom_MEpouse;
	}
	public void setPrenom_MEpouse(String prenom_MEpouse) {
		this.prenom_MEpouse = prenom_MEpouse;
	}
	public String getNom_MEpouse() {
		return nom_MEpouse;
	}
	public void setNom_MEpouse(String nom_MEpouse) {
		this.nom_MEpouse = nom_MEpouse;
	}
	public String getPrenom_Epoux() {
		return prenom_Epoux;
	}
	public void setPrenom_Epoux(String prenom_Epoux) {
		this.prenom_Epoux = prenom_Epoux;
	}
	public String getNom_Epoux() {
		return nom_Epoux;
	}
	public void setNom_Epoux(String nom_Epoux) {
		this.nom_Epoux = nom_Epoux;
	}
	
	public String getLieu_NEpoux() {
		return lieu_NEpoux;
	}
	public void setLieu_NEpoux(String lieu_NEpoux) {
		this.lieu_NEpoux = lieu_NEpoux;
	}
	public Date getDateNEpoux() {
		return dateNEpoux;
	}
	public void setDateNEpoux(Date dateNEpoux) {
		this.dateNEpoux = dateNEpoux;
	}
	public String getPrenom_PEpoux() {
		return prenom_PEpoux;
	}
	public void setPrenom_PEpoux(String prenom_PEpoux) {
		this.prenom_PEpoux = prenom_PEpoux;
	}
	public String getNom_PEpoux() {
		return nom_PEpoux;
	}
	public void setNom_PEpoux(String nom_PEpoux) {
		this.nom_PEpoux = nom_PEpoux;
	}
	public String getPrenom_MEpoux() {
		return prenom_MEpoux;
	}
	public void setPrenom_MEpoux(String prenom_MEpoux) {
		this.prenom_MEpoux = prenom_MEpoux;
	}
	public String getNom_MEpoux() {
		return nom_MEpoux;
	}
	public void setNom_MEpoux(String nom_MEpoux) {
		this.nom_MEpoux = nom_MEpoux;
	}
	
	public Date getDateDEpoux() {
		return dateDEpoux;
	}
	public void setDateDEpoux(Date dateDEpoux) {
		this.dateDEpoux = dateDEpoux;
	}
	public String getLieu_DEpoux() {
		return lieu_DEpoux;
	}
	public void setLieu_DEpoux(String lieu_DEpoux) {
		this.lieu_DEpoux = lieu_DEpoux;
	}
	
	/**
	 * @return the numActeM
	 */
	public String getNumActeM() {
		return numActeM;
	}
	/**
	 * @param numActeM the numActeM to set
	 */
	public void setNumActeM(String numActeM) {
		this.numActeM = numActeM;
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
	@SuppressWarnings("unchecked")
	public void save() throws IOException, JRException{
		
		parameter.put("region", MyUtil.getUserLogged().getCentre().getCenterRegion());
		parameter.put("depart", MyUtil.getUserLogged().getCentre().getCenterDepartement());
		parameter.put("arrond", MyUtil.getUserLogged().getCentre().getCenterArrondissement());
		parameter.put("collect", MyUtil.getUserLogged().getCentre().getCenterCollectivite());
		parameter.put("commune", MyUtil.getUserLogged().getCentre().getCommune());
		parameter.put("typeCentre", MyUtil.getUserLogged().getCentre().getCenterType());
		parameter.put("nomCentre", MyUtil.getUserLogged().getCentre().getCenterName());
		parameter.put("temoin1", this.getPrenom_T1()+
				" "+this.getNom_T1());
		parameter.put("temoin2", this.getPrenom_T2()+
				" "+this.getNom_T2());
		parameter.put("epouse", this.getPrenom_Epouse()+
				" "+this.getNom_Epouse());
		parameter.put("dateNE", Tools.formatDay(this.getDateNEpouse()));
		parameter.put("lieuNE", this.getLieu_NEpouse());
		parameter.put("pereE", this.getPrenom_PEpouse()+
				" "+this.getNom_PEpouse());
		parameter.put("mereE", this.getPrenom_MEpouse()+
				" "+this.getNom_MEpouse());
		parameter.put("defunt", this.getPrenom_Epoux()+
				" "+this.getNom_Epoux());
		parameter.put("dateND", Tools.formatDay(this.getDateNEpoux()));
		parameter.put("lieuND", this.getLieu_NEpoux());
		parameter.put("pereD", this.getPrenom_PEpoux()+
				" "+this.getNom_PEpoux());
		parameter.put("mereD", this.getPrenom_MEpoux()+
				" "+this.getNom_Epoux());
		parameter.put("dateD", Tools.formatDay(this.getDateDEpoux()));
		parameter.put("lieuD", this.getLieu_DEpoux());
		
		if(this.getNumActeM() != null && this.getAnnee() != null){
			parameter.put("numActeM", this.getNumActeM());
			parameter.put("annee", this.getAnnee());
		}
		else{
			parameter.put("numActeM", "");
			parameter.put("annee", "");
		}
		
		parameter.put("dateC", Tools.getCurrentDateDDMMYYYY());
		parameter.put("officier", MyUtil.getUserLogged().getUserPrenom()+" "+MyUtil.getUserLogged().getUserNom());
		
		FacesContext context = FacesContext.getCurrentInstance();

		String reportSource = context.getExternalContext().getRealPath("ActeNPDF/pieces/CertificatNonDivorceNonSeparationCorps.jasper");


		try {
			if(dService.addPiece(pService.findByCode(280))){
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
