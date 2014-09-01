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

public class CertificatNonMariageNonDivorce {
	
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
	private String domicile_T;
	
	private String prenom_Epouse;
	private String nom_Epouse;
	private String date_NEpouse;
	private String lieu_NEpouse;
	private Date dateNEpouse;
	private String domicile_Epouse;
	
	private String prenom_Epoux;
	private String nom_Epoux;
	private String date_DEpoux;
	private String lieuNEpoux;
	private String lieuD;
	private String annee;
	private Date dateNEpoux;
	private Date dateDEpoux;
	
	private String numero_acteD;

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

	public String getDomicile_T() {
		return domicile_T;
	}

	public void setDomicile_T(String domicile_T) {
		this.domicile_T = domicile_T;
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

	public String getDate_NEpouse() {
		return date_NEpouse;
	}

	public void setDate_NEpouse(String date_NEpouse) {
		this.date_NEpouse = date_NEpouse;
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

	public String getDomicile_Epouse() {
		return domicile_Epouse;
	}

	public void setDomicile_Epouse(String domicile_Epouse) {
		this.domicile_Epouse = domicile_Epouse;
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

	public String getDate_DEpoux() {
		return date_DEpoux;
	}

	public void setDate_DEpoux(String date_DEpoux) {
		this.date_DEpoux = date_DEpoux;
	}

	public Date getDateDEpoux() {
		return dateDEpoux;
	}

	public void setDateDEpoux(Date dateDEpoux) {
		this.dateDEpoux = dateDEpoux;
	}

	public String getNumero_acteD() {
		return numero_acteD;
	}

	public void setNumero_acteD(String numero_acteD) {
		this.numero_acteD = numero_acteD;
	}
	
	/**
	 * @return the lieuNEpoux
	 */
	public String getLieuNEpoux() {
		return lieuNEpoux;
	}

	/**
	 * @param lieuNEpoux the lieuNEpoux to set
	 */
	public void setLieuNEpoux(String lieuNEpoux) {
		this.lieuNEpoux = lieuNEpoux;
	}

	/**
	 * @return the dateNEpoux
	 */
	public Date getDateNEpoux() {
		return dateNEpoux;
	}

	/**
	 * @param dateNEpoux the dateNEpoux to set
	 */
	public void setDateNEpoux(Date dateNEpoux) {
		this.dateNEpoux = dateNEpoux;
	}

	/**
	 * @return the lieuD
	 */
	public String getLieuD() {
		return lieuD;
	}

	/**
	 * @param lieuD the lieuD to set
	 */
	public void setLieuD(String lieuD) {
		this.lieuD = lieuD;
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
		parameter.put("domicile", this.getDomicile_T());
		parameter.put("personne", this.getPrenom_Epouse()+
				" "+this.getNom_Epouse());
		parameter.put("dateNE", Tools.getformatDate(Tools.formatDay(this.getDateNEpouse())));
		parameter.put("lieuNE", this.getLieu_NEpouse());
		parameter.put("domicileE", this.getDomicile_Epouse());
		parameter.put("defuntEpoux", this.getPrenom_Epoux()+
				" "+this.getNom_Epoux());
		parameter.put("dateNEpoux", Tools.getformatDate(Tools.formatDay(this.getDateNEpoux())));
		parameter.put("lieuNEpoux", this.getLieuNEpoux());
		parameter.put("dateD", Tools.getformatDate(Tools.formatDay(this.getDateDEpoux())));
		parameter.put("lieuD", this.getLieuD());
		if(this.getAnnee() != null && this.getNumero_acteD() != null){
			parameter.put("numActeD", this.getNumero_acteD());
			parameter.put("annee", this.getAnnee());
		}
		else{
			parameter.put("numActeD", "");
			parameter.put("annee", "");
		}
		
		parameter.put("dateC", Tools.getCurrentDateDDMMYYYY());
		parameter.put("officier", MyUtil.getUserLogged().getUserPrenom()+" "+MyUtil.getUserLogged().getUserNom());
		
		FacesContext context = FacesContext.getCurrentInstance();

		String reportSource = context.getExternalContext().getRealPath("ActeNPDF/pieces/CertificatNonMariageNonDivorce.jasper");


		try {
			if(dService.addPiece(pService.findByCode(290))){
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
