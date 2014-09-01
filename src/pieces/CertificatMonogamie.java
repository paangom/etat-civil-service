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

public class CertificatMonogamie {
	
	private PiecesAnnexesServices pService = new PiecesAnnexesServices();
	private DelivredPieceService dService = new DelivredPieceService();
	
	@SuppressWarnings("rawtypes")
	private Map parameter = new HashMap();
	JasperPrint jasperPrint;
	
	private String prenomD, nomD, lieuN, lieuD, nomE, prenomE;
	private Date dateN, dateD;
	public String getPrenomD() {
		return prenomD;
	}
	public void setPrenomD(String prenomD) {
		this.prenomD = prenomD;
	}
	public String getNomD() {
		return nomD;
	}
	public void setNomD(String nomD) {
		this.nomD = nomD;
	}
	public String getLieuN() {
		return lieuN;
	}
	public void setLieuN(String lieuN) {
		this.lieuN = lieuN;
	}
	public String getLieuD() {
		return lieuD;
	}
	public void setLieuD(String lieuD) {
		this.lieuD = lieuD;
	}
	public String getNomE() {
		return nomE;
	}
	public void setNomE(String nomE) {
		this.nomE = nomE;
	}
	public String getPrenomE() {
		return prenomE;
	}
	public void setPrenomE(String prenomE) {
		this.prenomE = prenomE;
	}
	public Date getDateN() {
		return dateN;
	}
	public void setDateN(Date dateN) {
		this.dateN = dateN;
	}
	public Date getDateD() {
		return dateD;
	}
	public void setDateD(Date dateD) {
		this.dateD = dateD;
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
		String jour = NombreEnLettre.convert(Tools.getDayForDate(Tools.getCurrentDateDDMMYYYY()));
		if(jour.equals("un"))
			jour = "premier";
		parameter.put("dateCL", jour+
				" "+Tools.getMoisLettre(Tools.getCurrentDateDDMMYYYY())+
				" "+NombreEnLettre.convert(Tools.getYearForDate(Tools.getCurrentDateDDMMYYYY())));
		parameter.put("defunt", this.getPrenomD()+" "+this.getNomD());
		parameter.put("dateN", Tools.getformatDate(Tools.formatDay(this.getDateN())));
		parameter.put("lieuN", this.getLieuN());
		parameter.put("dateD", Tools.getformatDate(Tools.formatDay(this.getDateD())));
		parameter.put("lieuD", this.getLieuD());
		parameter.put("epouse", this.getPrenomE()+" "+this.getNomE());
		parameter.put("dateC", Tools.getCurrentDateDDMMYYYY());
		parameter.put("officier", MyUtil.getUserLogged().getUserPrenom()+" "+MyUtil.getUserLogged().getUserNom());
		
		
		
		try {
			
			if(dService.addPiece(pService.findByCode(350))){
				FacesContext context = FacesContext.getCurrentInstance();
				String reportSource = context.getExternalContext().getRealPath("ActeNPDF/pieces/CertificatMonogamie.jasper");

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
