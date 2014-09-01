package pieces;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import models.DeclarationMariage;
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

public class CertificatDivorce {

	private String person;
	private DeclarationMariage decM;
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
		parameter.put("num", NombreEnLettre.convert(this.decM.getNumero_Acte()));
		String jour = NombreEnLettre.convert(Tools.getDayForDateTime(this.decM.getDate_creation()));
		if(jour.equals("un"))
			jour = "premier";
		parameter.put("date", jour+
				" "+Tools.getMoisLettre(this.decM.getDate_creation())+
				" "+NombreEnLettre.convert(Tools.getYearForDateTime(this.decM.getDate_creation())));
		if(!"".equals(this.decM.getNumero_Jugement()))
			parameter.put("numJ", NombreEnLettre.convert(this.decM.getNumero_Jugement()));
		else
			parameter.put("numJ", "");
		if(!"".equals(this.decM.getDate_Jugement())){
			jour = NombreEnLettre.convert(Tools.getDayForDate(this.decM.getDate_Jugement()));
			if(jour.equals("un"))
				jour = "premier";
			parameter.put("dateJ", jour+
				" "+Tools.getMoisLettre(this.decM.getDate_Jugement())+
				" "+NombreEnLettre.convert(Tools.getYearForDate(this.decM.getDate_Jugement())));
		}
		else
			parameter.put("dateJ","");
		parameter.put("tribunal", this.decM.getTribunal());
		parameter.put("dateM", Tools.getformatDate(this.decM.getDate_Mariage()));
		parameter.put("lieuM", this.decM.getLieu_Mariage());
		parameter.put("epoux", this.decM.getPrenom_Epoux()+" "+this.decM.getNom_Epoux());
		parameter.put("dateNEpoux", this.decM.getDate_Naissance_Epoux());
		parameter.put("lieuNEpoux", this.decM.getLieu_Naissance_Epoux());
		parameter.put("epouse", this.decM.getPrenom_Epouse()+" "+this.decM.getNom_Epouse());
		parameter.put("dateNEpouse", this.decM.getDate_Naissance_Epouse());
		parameter.put("lieuNEpouse", this.decM.getLieu_Naissance_Epouse());
		parameter.put("concernant1", this.decM.getPrenom_Epoux()+" "+this.decM.getNom_Epoux());
		parameter.put("concernant2", this.decM.getPrenom_Epouse()+" "+this.decM.getNom_Epouse());
		jour = NombreEnLettre.convert(Tools.getDayForDate(this.decM.getDate_Mariage()));
		if(jour.equals("un"))
			jour = "premier";
		parameter.put("dateDivorce", jour+
				" "+Tools.getMoisLettre(this.decM.getDate_Mariage())+
				" "+NombreEnLettre.convert(Tools.getYearForDate(this.decM.getDate_Mariage())));		
		parameter.put("dateC", Tools.getCurrentDateDDMMYYYY());
		
		try {
			
			if(dService.addPiece(pService.findByCode(370))){
				FacesContext context = FacesContext.getCurrentInstance();
				String reportSource = context.getExternalContext().getRealPath("ActeNPDF/pieces/CertificatDivorce.jasper");

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

}
