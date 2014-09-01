package pieces;

import java.io.IOException;
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
import models.DeclarationMariage;
import services.DelivredPieceService;
import services.PiecesAnnexesServices;
import util.MyUtil;
import util.NombreEnLettre;
import util.Tools;

public class CertificatMariageConstate {
	
	private DeclarationMariage decM;
	private String person;
	private String coutume;
	
	private PiecesAnnexesServices pService = new PiecesAnnexesServices();
	private DelivredPieceService dService = new DelivredPieceService();
	
	@SuppressWarnings("rawtypes")
	private Map parameter = new HashMap();
	JasperPrint jasperPrint;
	
	@SuppressWarnings("unchecked")
	public void save() throws IOException{
		decM = (DeclarationMariage) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("decM");
		
		parameter.put("region", MyUtil.getUserLogged().getCentre().getCenterRegion());
		parameter.put("depart", MyUtil.getUserLogged().getCentre().getCenterDepartement());
		parameter.put("arrond", MyUtil.getUserLogged().getCentre().getCenterArrondissement());
		parameter.put("collect", MyUtil.getUserLogged().getCentre().getCenterCollectivite());
		parameter.put("commune", MyUtil.getUserLogged().getCentre().getCommune());
		parameter.put("typeCentre", MyUtil.getUserLogged().getCentre().getCenterType());
		parameter.put("nomCentre", MyUtil.getUserLogged().getCentre().getCenterName());
		parameter.put("officier", MyUtil.getUserLogged().getUserPrenom()+" "+MyUtil.getUserLogged().getUserNom());
		parameter.put("num", this.decM.getNumero_Acte());
		parameter.put("annee", Tools.getYearForDateTime(this.decM.getDate_creation()));
		if(person.equals("Epoux")){
			parameter.put("personne", this.decM.getPrenom_Epoux()+" "+this.decM.getNom_Epoux());
			parameter.put("dateN", this.decM.getDate_Naissance_Epoux());
			parameter.put("lieuN", this.decM.getLieu_Naissance_Epoux());
			parameter.put("sexe", "Fils");
			parameter.put("pere", this.decM.getPrenom_Pere_Epoux()+" "+this.decM.getNom_Pere_Epoux());
			parameter.put("mere", this.decM.getPrenom_Mere_Epoux()+" "+this.decM.getNom_Mere_Epoux());
			
		}
		else{
			parameter.put("personne", this.decM.getPrenom_Epouse()+" "+this.decM.getNom_Epouse());
			parameter.put("dateN", this.decM.getDate_Naissance_Epouse());
			parameter.put("lieuN", this.decM.getLieu_Naissance_Epouse());
			parameter.put("sexe", "Fille");
			parameter.put("pere", this.decM.getPrenom_Pere_Epouse()+" "+this.decM.getNom_Pere_Epouse());
			parameter.put("mere", this.decM.getPrenom_Mere_Epouse()+" "+this.decM.getNom_Mere_Epouse());
			
		}
		
		parameter.put("coutume", this.getCoutume());
		String jour = NombreEnLettre.convert(Tools.getDayForDate(this.decM.getDate_Mariage()));
		if(jour.equals("un"))
			jour = "premier";
		parameter.put("dateM", jour+
				" "+Tools.getMoisLettre(this.decM.getDate_Mariage())+
				" "+NombreEnLettre.convert(Tools.getYearForDate(this.decM.getDate_Mariage())));
		jour = NombreEnLettre.convert(Tools.getDayForDateTime(this.decM.getDate_creation()));
		if(jour.equals("un"))
			jour = "premier";
		parameter.put("dateD", jour+
				" "+Tools.getMoisLettre(this.decM.getDate_creation())+
				" "+NombreEnLettre.convert(Tools.getYearForDateTime(this.decM.getDate_creation())));
		parameter.put("type", this.decM.getGenre_Mariage());
		parameter.put("dot", this.decM.getDot_Mariage()+" F CFA");
		parameter.put("regime", this.decM.getRegime_Mariage());
		parameter.put("dateC", Tools.getCurrentDateDDMMYYYY());
		
try {
			
			if(dService.addPiece(pService.findByCode(360))){
				FacesContext context = FacesContext.getCurrentInstance();
				String reportSource = context.getExternalContext().getRealPath("ActeNPDF/pieces/CertificatMariageConstate.jasper");

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

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getCoutume() {
		return coutume;
	}

	public void setCoutume(String coutume) {
		this.coutume = coutume;
	}

}
