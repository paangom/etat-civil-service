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

public class CertificatNonMariageNonRemariage {
	
	JasperPrint jasperPrint;
	
	private String nomT1;
	private String prenomT1;
	private String nomT2;
	private String prenomT2;
	private String domT;
	private String prenomE;
	private String nomE;
	private String lieuNE;
	private String domicileE;
	private String nomD;
	private String prenomD;
	private String cni1, cni2;
	private Date dateNE;
	
	
	public String getNomT1() {
		return nomT1;
	}
	public void setNomT1(String nomT1) {
		this.nomT1 = nomT1;
	}
	public String getPrenomT1() {
		return prenomT1;
	}
	public void setPrenomT1(String prenomT1) {
		this.prenomT1 = prenomT1;
	}
	public String getNomT2() {
		return nomT2;
	}
	public void setNomT2(String nomT2) {
		this.nomT2 = nomT2;
	}
	public String getPrenomT2() {
		return prenomT2;
	}
	public void setPrenomT2(String prenomT2) {
		this.prenomT2 = prenomT2;
	}
	public String getDomT() {
		return domT;
	}
	public void setDomT(String domT) {
		this.domT = domT;
	}
	public String getLieuNE() {
		return lieuNE;
	}
	public void setLieuNE(String lieuNE) {
		this.lieuNE = lieuNE;
	}
	public String getDomicileE() {
		return domicileE;
	}
	public void setDomicileE(String domicileE) {
		this.domicileE = domicileE;
	}
	
	public String getPrenomE() {
		return prenomE;
	}
	public void setPrenomE(String prenomE) {
		this.prenomE = prenomE;
	}
	public String getNomE() {
		return nomE;
	}
	public void setNomE(String nomE) {
		this.nomE = nomE;
	}
	
	public String getNomD() {
		return nomD;
	}
	public void setNomD(String nomD) {
		this.nomD = nomD;
	}
	public String getPrenomD() {
		return prenomD;
	}
	public void setPrenomD(String prenomD) {
		this.prenomD = prenomD;
	}
	
	public Date getDateNE() {
		return dateNE;
	}
	public void setDateNE(Date dateNE) {
		this.dateNE = dateNE;
	}
	
	
	private PiecesAnnexesServices pService = new PiecesAnnexesServices();
	private DelivredPieceService dService = new DelivredPieceService();
	
	@SuppressWarnings("rawtypes")
	private Map parameter = new HashMap();
	
	@SuppressWarnings("unchecked")
	public void save() throws IOException{
		parameter.put("region", MyUtil.getUserLogged().getCentre().getCenterRegion());
		parameter.put("depart", MyUtil.getUserLogged().getCentre().getCenterDepartement());
		parameter.put("arrond", MyUtil.getUserLogged().getCentre().getCenterArrondissement());
		parameter.put("typeCentre", MyUtil.getUserLogged().getCentre().getCenterType());
		parameter.put("nomCentre", MyUtil.getUserLogged().getCentre().getCenterName());
		parameter.put("collect", MyUtil.getUserLogged().getCentre().getCenterCollectivite());
		parameter.put("commune", MyUtil.getUserLogged().getCentre().getCommune());
		parameter.put("officier", MyUtil.getUserLogged().getUserPrenom()+" "+MyUtil.getUserLogged().getUserNom());
		parameter.put("temoins1", this.getPrenomT1()+" "+this.getNomT1());
		parameter.put("temoins2", this.getPrenomT2()+" "+this.getNomT2());
		parameter.put("domicile", this.getDomT());
		parameter.put("epouse", this.getPrenomE()+" "+this.getNomE());
		parameter.put("dateNEpouse", Tools.formatDay(this.getDateNE()));
		parameter.put("lieuNEpouse", this.getLieuNE());
		parameter.put("domEpouse", this.getDomicileE());
		parameter.put("cni1", this.getCni1());
		parameter.put("cni2", this.getCni2());
		parameter.put("epoux", this.getPrenomD()+" "+this.getNomD());
//		parameter.put("dateD", Tools.getformatDate(Tools.formatDay(this.getDateD())));
//		parameter.put("lieuD", this.getLieuD());
//		parameter.put("lieuND", this.getLieuND());
//		parameter.put("dateND", Tools.getformatDate(Tools.formatDay(this.getDateND())));
//		parameter.put("nouveauEpoux", this.getPrenomNE()+" "+this.getNomNE());
//		parameter.put("lieuNNE", this.getLieuNNE());
//		parameter.put("dateNNE", Tools.getformatDate(Tools.formatDay(this.getDateNNE())));
		parameter.put("dateC", Tools.getCurrentDateDDMMYYYY());
		
		FacesContext context = FacesContext.getCurrentInstance();

		String reportSource = context.getExternalContext().getRealPath("ActeNPDF/pieces/CertificatNoNMariageNonRemariage.jasper");


		try {
			if(dService.addPiece(pService.findByCode(300))){
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
	public String getCni2() {
		return cni2;
	}
	public void setCni2(String cni2) {
		this.cni2 = cni2;
	}
	public String getCni1() {
		return cni1;
	}
	public void setCni1(String cni1) {
		this.cni1 = cni1;
	}
	

}
