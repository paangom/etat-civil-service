package pieces;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JasperPrint;
import services.DelivredPieceService;
import services.PiecesAnnexesServices;


public class CertificatCelibat {
	
	@SuppressWarnings("rawtypes")
	private Map parameter = new HashMap();
	JasperPrint jasperPrint;
	
	private PiecesAnnexesServices pService = new PiecesAnnexesServices();
	private DelivredPieceService dService = new DelivredPieceService();
	
	private String prenom_T1;
	private String nom_T1;
	private String lieu_NT1;
	private Date dateNT1;
	private String adresse_T1;
	private String cni_T1;
	
	private String prenom_T2;
	private String nom_T2;
	private String lieu_NT2;
	private Date dateNT2;
	private String adresse_T2;
	private String cni_T2;
	
	private String prenom_Epouse;
	private String nom_Epouse;
	private String lieu_NEpouse;
	private Date dateNEpouse;
	private String domicile_Epouse;
	private String sexe;
	
	private String prenom_PEpouse;
	private String nom_PEpouse;
	private String prenom_MEpouse;
	private String nom_MEpouse;
	
	
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
	
	public String getLieu_NT1() {
		return lieu_NT1;
	}
	public void setLieu_NT1(String lieu_NT1) {
		this.lieu_NT1 = lieu_NT1;
	}
	public Date getDateNT1() {
		return dateNT1;
	}
	public void setDateNT1(Date dateNT1) {
		this.dateNT1 = dateNT1;
	}
	public String getAdresse_T1() {
		return adresse_T1;
	}
	public void setAdresse_T1(String adresse_T1) {
		this.adresse_T1 = adresse_T1;
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
	
	public String getLieu_NT2() {
		return lieu_NT2;
	}
	public void setLieu_NT2(String lieu_NT2) {
		this.lieu_NT2 = lieu_NT2;
	}
	public Date getDateNT2() {
		return dateNT2;
	}
	public void setDateNT2(Date dateNT2) {
		this.dateNT2 = dateNT2;
	}
	public String getAdresse_T2() {
		return adresse_T2;
	}
	public void setAdresse_T2(String adresse_T2) {
		this.adresse_T2 = adresse_T2;
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
	public String getDomicile_Epouse() {
		return domicile_Epouse;
	}
	public void setDomicile_Epouse(String domicile_Epouse) {
		this.domicile_Epouse = domicile_Epouse;
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
	
	@SuppressWarnings("unchecked")
	public void save(){
//		parameter.put("region", MyUtil.getUserLogged().getCentre().getCenterRegion());
//		parameter.put("depart", MyUtil.getUserLogged().getCentre().getCenterDepartement());
//		parameter.put("arrond", MyUtil.getUserLogged().getCentre().getCenterArrondissement());
//		parameter.put("centre", MyUtil.getUserLogged().getCentre().getCenterType());
//		parameter.put("nomcentre", MyUtil.getUserLogged().getCentre().getCenterName());
//		parameter.put("dateN", NombreEnLettre.convert(Tools.getDayForDate(Tools.formatDay(this.getDateN())))+" "
//				+Tools.getMoisLettre(Tools.formatDay(this.getDateN()))+" "
//				+NombreEnLettre.convert(Tools.getYearForDate(Tools.formatDay(this.getDateN()))));
//		parameter.put("personne", this.getPrenom()+" "+this.getNom());
//		parameter.put("pere", this.getPrenom_P()+" "+this.getNom_P());
//		parameter.put("mere", this.getPrenom_M()+" "+this.getNom_M());
//		parameter.put("coutume", this.getCoutume());
//		parameter.put("dateC", Tools.getCurrentDateDDMMYYYY());
//		parameter.put("officier", MyUtil.getUserLogged().getUserPrenom()+" "+MyUtil.getUserLogged().getUserNom());
//		
//		FacesContext context = FacesContext.getCurrentInstance();
//
//		String reportSource = context.getExternalContext().getRealPath("ActeNPDF/pieces/attestationCoutume.jrxml");
//
//		JasperDesign jasperDesign;
//		
//		try {
//			jasperDesign = JRXmlLoader.load(reportSource);
//			JasperReport jasperReport =JasperCompileManager.compileReport(jasperDesign);
//			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, new JREmptyDataSource());
//			
//			if(dService.addPiece(pService.findByCode(200))){
//			JasperViewer.viewReport(jasperPrint,false);
//			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Chargement effectu� avec succ�s!", null);
//	        FacesContext.getCurrentInstance().addMessage(null, message);
//			}
//			else{
//				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur d'ajout du document d�livr�!", null);
//		        FacesContext.getCurrentInstance().addMessage(null, message);
//			}
//	        
//		} catch (JRException e) {
//			
//			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur de chargement!", null);
//	        FacesContext.getCurrentInstance().addMessage(null, message);
//			e.printStackTrace();
//		}
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

}
