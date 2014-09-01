package pieces;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import models.DelivredPieces;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import services.DelivredPieceService;
import services.PiecesAnnexesServices;
import util.MyUtil;
import util.NombreEnLettre;
import util.Tools;

public class AttestationDeCoutume {
	
	JasperPrint jasperPrint;

	private PiecesAnnexesServices pService = new PiecesAnnexesServices();
	private DelivredPieceService dService = new DelivredPieceService();
	private DelivredPieces dPiece;
	
	private String nom;
	private String prenom;
	private String lieu_N;
	private String sexe;
	private String prenom_P;
	private String nom_P;
	private String prenom_M;
	private String nom_M;
	private String coutume;
	
	private Date dateN;
	private Date dateC;
	
	@SuppressWarnings("rawtypes")
	private Map parameter = new HashMap();
	
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	public String getLieu_N() {
		return lieu_N;
	}
	public void setLieu_N(String lieu_N) {
		this.lieu_N = lieu_N;
	}
	public String getSexe() {
		return sexe;
	}
	public void setSexe(String sexe) {
		this.sexe = sexe;
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
	public String getCoutume() {
		return coutume;
	}
	public void setCoutume(String coutume) {
		this.coutume = coutume;
	}
	
	public Date getDateN() {
		return dateN;
	}
	public void setDateN(Date dateN) {
		this.dateN = dateN;
	}
	public Date getDateC() {
		return dateC;
	}
	public void setDateC(Date dateC) {
		this.dateC = dateC;
	}
	
	@SuppressWarnings("unchecked")
	public void save() throws IOException {
		parameter.put("region", MyUtil.getUserLogged().getCentre().getCenterRegion());
		parameter.put("depart", MyUtil.getUserLogged().getCentre().getCenterDepartement());
		parameter.put("arrond", MyUtil.getUserLogged().getCentre().getCenterArrondissement());
		parameter.put("centre", MyUtil.getUserLogged().getCentre().getCenterType());
		parameter.put("nomcentre", MyUtil.getUserLogged().getCentre().getCenterName());
		String jour = NombreEnLettre.convert(Tools.getDayForDate(Tools.formatDay(this.getDateN())));
		if(jour.equals("un"))
			jour = "premier";
		parameter.put("dateN", jour+" "
				+Tools.getMoisLettre(Tools.formatDay(this.getDateN()))+" "
				+NombreEnLettre.convert(Tools.getYearForDate(Tools.formatDay(this.getDateN()))));
		parameter.put("personne", this.getPrenom()+" "+this.getNom());
		parameter.put("pere", this.getPrenom_P()+" "+this.getNom_P());
		parameter.put("mere", this.getPrenom_M()+" "+this.getNom_M());
		parameter.put("coutume", this.getCoutume());
		parameter.put("dateC", Tools.getCurrentDateDDMMYYYY());
		parameter.put("officier", MyUtil.getUserLogged().getUserPrenom()+" "+MyUtil.getUserLogged().getUserNom());
		
		FacesContext context = FacesContext.getCurrentInstance();

		String reportSource = context.getExternalContext().getRealPath("ActeNPDF/pieces/attestationCoutume.jasper");
		try {
			
			
			if(dService.addPiece(pService.findByCode(200))){
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

	/**
	 * @return the dPiece
	 */
	public DelivredPieces getdPiece() {
		return dPiece;
	}
	/**
	 * @param dPiece the dPiece to set
	 */
	public void setdPiece(DelivredPieces dPiece) {
		this.dPiece = dPiece;
	}
	
	
}
