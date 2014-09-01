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
import util.NombreEnLettre;
import util.Tools;

public class Inhumations {
	
	private PiecesAnnexesServices pService = new PiecesAnnexesServices();
	private DelivredPieceService dService = new DelivredPieceService();
	
	@SuppressWarnings("rawtypes")
	private Map parameter = new HashMap();
	JasperPrint jasperPrint;
	
	private String nom;
	private String prenom;
	private String sexe;
	private String fs;
	private String cimetiere;
	private Date dateD;
	
	public Inhumations(){
		
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public void saveI() throws IOException, JRException {
		System.out.println(this.getNom()+" ff "+this.getPrenom());
		parameter.put("region", MyUtil.getUserLogged().getCentre().getCenterRegion());
		parameter.put("depart", MyUtil.getUserLogged().getCentre().getCenterDepartement());
		parameter.put("arrond", MyUtil.getUserLogged().getCentre().getCenterArrondissement());
		parameter.put("centre", MyUtil.getUserLogged().getCentre().getCenterType());
		parameter.put("nomCentre", MyUtil.getUserLogged().getCentre().getCenterName());
		parameter.put("cimetiere", this.getCimetiere());
		parameter.put("defunt", this.getPrenom()+" "+this.getNom());
		String jour = NombreEnLettre.convert(Tools.getDayForDate(Tools.formatDay(this.getDateD())));
		if(jour.equals("un"))
			jour = "premier";
		parameter.put("deces", jour+" "
				+Tools.getMoisLettre(Tools.formatDay(this.getDateD()))+" "
				+NombreEnLettre.convert(Tools.getYearForDate(Tools.formatDay(this.getDateD()))));
		parameter.put("annee", "");
		parameter.put("numActe", "");
		parameter.put("dateC", Tools.getCurrentDateDDMMYYYY());
		parameter.put("officier", MyUtil.getUserLogged().getUserPrenom()+" "+MyUtil.getUserLogged().getUserNom());
		
		try {
			if(dService.addPiece(pService.findByCode(210))){       
				FacesContext context = FacesContext.getCurrentInstance();
				String reportSource = context.getExternalContext().getRealPath("ActeNPDF/pieces/inhumation.jasper");

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
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}




	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}




	/**
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}




	/**
	 * @param prenom the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
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
	 * @return the cimetiere
	 */
	public String getCimetiere() {
		return cimetiere;
	}




	/**
	 * @param cimetiere the cimetiere to set
	 */
	public void setCimetiere(String cimetiere) {
		this.cimetiere = cimetiere;
	}




	/**
	 * @return the fs
	 */
	public String getFs() {
		return fs;
	}




	/**
	 * @param fs the fs to set
	 */
	public void setFs(String fs) {
		this.fs = fs;
	}




	/**
	 * @return the dateD
	 */
	public Date getDateD() {
		return dateD;
	}




	/**
	 * @param dateD the dateD to set
	 */
	public void setDateD(Date dateD) {
		this.dateD = dateD;
	}

}
