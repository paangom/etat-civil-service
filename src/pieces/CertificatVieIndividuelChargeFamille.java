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

public class CertificatVieIndividuelChargeFamille {
	
	private PiecesAnnexesServices pService = new PiecesAnnexesServices();
	private DelivredPieceService dService = new DelivredPieceService();
	
	@SuppressWarnings("rawtypes")
	private Map parameter = new HashMap();
	JasperPrint jasperPrint;
	
	private String num, annee, prenom, nom, lieuN, doc, dom, prenom_P, nom_P, prenom_M, nom_M, prenom_C, nom_C;
	private Date date, dateN;
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getAnnee() {
		return annee;
	}
	public void setAnnee(String annee) {
		this.annee = annee;
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
	public String getLieuN() {
		return lieuN;
	}
	public void setLieuN(String lieuN) {
		this.lieuN = lieuN;
	}
	public String getDom() {
		return dom;
	}
	public void setDom(String dom) {
		this.dom = dom;
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
	public String getPrenom_C() {
		return prenom_C;
	}
	public void setPrenom_C(String prenom_C) {
		this.prenom_C = prenom_C;
	}
	public String getNom_C() {
		return nom_C;
	}
	public void setNom_C(String nom_C) {
		this.nom_C = nom_C;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getDateN() {
		return dateN;
	}
	public void setDateN(Date dateN) {
		this.dateN = dateN;
	}
	
	public String getDoc() {
		return doc;
	}
	public void setDoc(String doc) {
		this.doc = doc;
	}
	@SuppressWarnings("unchecked")
	public void save() throws IOException{
		
		parameter.put("region", MyUtil.getUserLogged().getCentre().getCenterRegion());
		parameter.put("depart", MyUtil.getUserLogged().getCentre().getCenterDepartement());
		parameter.put("arrond", MyUtil.getUserLogged().getCentre().getCenterArrondissement());
		parameter.put("typeCentre", MyUtil.getUserLogged().getCentre().getCenterType());
		parameter.put("nomCentre", MyUtil.getUserLogged().getCentre().getCenterName());
		parameter.put("collect", MyUtil.getUserLogged().getCentre().getCenterCollectivite());
		parameter.put("commune", MyUtil.getUserLogged().getCentre().getCommune());
		parameter.put("dom", this.getDom());
		parameter.put("personne", this.getPrenom()+" "+this.getNom());
		parameter.put("dateN", Tools.getformatDate(Tools.formatDay(this.getDateN())));
		parameter.put("lieuN", this.getLieuN());
		parameter.put("num", this.getNum());
		parameter.put("annee", this.getAnnee());
		parameter.put("document", this.getDoc());
		parameter.put("date", this.getDate());
		parameter.put("charge", this.getPrenom_C()+" "+this.getNom_C());
		parameter.put("pere", this.getPrenom_P()+" "+this.getNom_P());
		parameter.put("mere", this.getPrenom_M()+" "+this.getNom_M());
		parameter.put("dateC", Tools.getCurrentDateDDMMYYYY());
		parameter.put("officier", MyUtil.getUserLogged().getUserPrenom()+" "+MyUtil.getUserLogged().getUserNom());
		
		
		
		try {
			
			if(dService.addPiece(pService.findByCode(340))){
				FacesContext context = FacesContext.getCurrentInstance();
				String reportSource = context.getExternalContext().getRealPath("ActeNPDF/pieces/CertificatVieIndividuelChargeFamille.jasper");

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
