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

public class CertificatDeVieCollectif {
	
	private PiecesAnnexesServices pService = new PiecesAnnexesServices();
	private DelivredPieceService dService = new DelivredPieceService();
	JasperPrint jasperPrint;
	
	@SuppressWarnings("rawtypes")
	private Map parameter = new HashMap();
	
	private String centre;
	private String officier;
	private String date_C;
	
	private String prenom_P;
	private String nom_P;
	private String sexe_P;
	private String domicile_P;
	
	private String prenom_1;
	private String nom_1;
	private String lieu_1;
	
	private String prenom_2;
	private String nom_2;
	private String lieu_2;
	
	private String prenom_3;
	private String nom_3;
	private String lieu_3;
	
	private String prenom_4;
	private String nom_4;
	private String lieu_4;
	
	private String prenom_5;
	private String nom_5;
	private String lieu_5;
	
	private String prenom_6;
	private String nom_6;
	private String lieu_6;
	
	private String prenom_7;
	private String nom_7;
	private String lieu_7;
	
	private String prenom_8;
	private String nom_8;
	private String lieu_8;
	
	private String prenom_9;
	private String nom_9;
	private String lieu_9;
	
	private String prenom_10;
	private String nom_10;
	private String lieu_10;
	
	private String prenom_11;
	private String nom_11;
	private String lieu_11;
	
	private String prenom_12;
	private String nom_12;
	private String lieu_12;
	
	private String prenom_13;
	private String nom_13;
	private String lieu_13;
	
	private String prenom_14;
	private String nom_14;
	private String lieu_14;
	
	private String prenom_15;
	private String nom_15;
	private String lieu_15;
	
	private String prenom_16;
	private String nom_16;
	private String lieu_16;
	
	
	
	private Date date1;
	private Date date2;
	private Date date3;
	private Date date4;
	private Date date5;
	private Date date6;
	private Date date7;
	private Date date8;
	private Date date9;
	private Date date10;
	private Date date11;
	private Date date12;
	private Date date13;
	private Date date14;
	private Date date15;
	private Date date16;
	
	
	public String getCentre() {
		return centre;
	}
	public void setCentre(String centre) {
		this.centre = centre;
	}
	public String getOfficier() {
		return officier;
	}
	public void setOfficier(String officier) {
		this.officier = officier;
	}
	public String getDate_C() {
		return date_C;
	}
	public void setDate_C(String date_C) {
		this.date_C = date_C;
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
	public String getSexe_P() {
		return sexe_P;
	}
	public void setSexe_P(String sexe_P) {
		this.sexe_P = sexe_P;
	}
	public String getDomicile_P() {
		return domicile_P;
	}
	public void setDomicile_P(String domicile_P) {
		this.domicile_P = domicile_P;
	}
	public String getPrenom_1() {
		return prenom_1;
	}
	public void setPrenom_1(String prenom_1) {
		this.prenom_1 = prenom_1;
	}
	public String getNom_1() {
		return nom_1;
	}
	public void setNom_1(String nom_1) {
		this.nom_1 = nom_1;
	}
	
	public String getLieu_1() {
		return lieu_1;
	}
	public void setLieu_1(String lieu_1) {
		this.lieu_1 = lieu_1;
	}
	public String getPrenom_2() {
		return prenom_2;
	}
	public void setPrenom_2(String prenom_2) {
		this.prenom_2 = prenom_2;
	}
	public String getNom_2() {
		return nom_2;
	}
	public void setNom_2(String nom_2) {
		this.nom_2 = nom_2;
	}
	
	public String getLieu_2() {
		return lieu_2;
	}
	public void setLieu_2(String lieu_2) {
		this.lieu_2 = lieu_2;
	}
	public String getPrenom_3() {
		return prenom_3;
	}
	public void setPrenom_3(String prenom_3) {
		this.prenom_3 = prenom_3;
	}
	public String getNom_3() {
		return nom_3;
	}
	public void setNom_3(String nom_3) {
		this.nom_3 = nom_3;
	}
	
	public String getLieu_3() {
		return lieu_3;
	}
	public void setLieu_3(String lieu_3) {
		this.lieu_3 = lieu_3;
	}
	public String getPrenom_4() {
		return prenom_4;
	}
	public void setPrenom_4(String prenom_4) {
		this.prenom_4 = prenom_4;
	}
	public String getNom_4() {
		return nom_4;
	}
	public void setNom_4(String nom_4) {
		this.nom_4 = nom_4;
	}
	
	public String getLieu_4() {
		return lieu_4;
	}
	public void setLieu_4(String lieu_4) {
		this.lieu_4 = lieu_4;
	}
	public String getPrenom_5() {
		return prenom_5;
	}
	public void setPrenom_5(String prenom_5) {
		this.prenom_5 = prenom_5;
	}
	public String getNom_5() {
		return nom_5;
	}
	public void setNom_5(String nom_5) {
		this.nom_5 = nom_5;
	}
	
	public String getLieu_5() {
		return lieu_5;
	}
	public void setLieu_5(String lieu_5) {
		this.lieu_5 = lieu_5;
	}
	public String getPrenom_6() {
		return prenom_6;
	}
	public void setPrenom_6(String prenom_6) {
		this.prenom_6 = prenom_6;
	}
	public String getNom_6() {
		return nom_6;
	}
	public void setNom_6(String nom_6) {
		this.nom_6 = nom_6;
	}
	
	public String getLieu_6() {
		return lieu_6;
	}
	public void setLieu_6(String lieu_6) {
		this.lieu_6 = lieu_6;
	}
	public String getPrenom_7() {
		return prenom_7;
	}
	public void setPrenom_7(String prenom_7) {
		this.prenom_7 = prenom_7;
	}
	public String getNom_7() {
		return nom_7;
	}
	public void setNom_7(String nom_7) {
		this.nom_7 = nom_7;
	}
	
	public String getLieu_7() {
		return lieu_7;
	}
	public void setLieu_7(String lieu_7) {
		this.lieu_7 = lieu_7;
	}
	public String getPrenom_8() {
		return prenom_8;
	}
	public void setPrenom_8(String prenom_8) {
		this.prenom_8 = prenom_8;
	}
	public String getNom_8() {
		return nom_8;
	}
	public void setNom_8(String nom_8) {
		this.nom_8 = nom_8;
	}
	
	public String getLieu_8() {
		return lieu_8;
	}
	public void setLieu_8(String lieu_8) {
		this.lieu_8 = lieu_8;
	}
	public String getPrenom_9() {
		return prenom_9;
	}
	public void setPrenom_9(String prenom_9) {
		this.prenom_9 = prenom_9;
	}
	public String getNom_9() {
		return nom_9;
	}
	public void setNom_9(String nom_9) {
		this.nom_9 = nom_9;
	}
	
	public String getLieu_9() {
		return lieu_9;
	}
	public void setLieu_9(String lieu_9) {
		this.lieu_9 = lieu_9;
	}
	public String getPrenom_10() {
		return prenom_10;
	}
	public void setPrenom_10(String prenom_10) {
		this.prenom_10 = prenom_10;
	}
	public String getNom_10() {
		return nom_10;
	}
	public void setNom_10(String nom_10) {
		this.nom_10 = nom_10;
	}
	
	public String getLieu_10() {
		return lieu_10;
	}
	public void setLieu_10(String lieu_10) {
		this.lieu_10 = lieu_10;
	}
	public String getPrenom_11() {
		return prenom_11;
	}
	public void setPrenom_11(String prenom_11) {
		this.prenom_11 = prenom_11;
	}
	public String getNom_11() {
		return nom_11;
	}
	public void setNom_11(String nom_11) {
		this.nom_11 = nom_11;
	}
	
	public String getLieu_11() {
		return lieu_11;
	}
	public void setLieu_11(String lieu_11) {
		this.lieu_11 = lieu_11;
	}
	public String getPrenom_12() {
		return prenom_12;
	}
	public void setPrenom_12(String prenom_12) {
		this.prenom_12 = prenom_12;
	}
	public String getNom_12() {
		return nom_12;
	}
	public void setNom_12(String nom_12) {
		this.nom_12 = nom_12;
	}
	
	public String getLieu_12() {
		return lieu_12;
	}
	public void setLieu_12(String lieu_12) {
		this.lieu_12 = lieu_12;
	}
	public String getPrenom_13() {
		return prenom_13;
	}
	public void setPrenom_13(String prenom_13) {
		this.prenom_13 = prenom_13;
	}
	public String getNom_13() {
		return nom_13;
	}
	public void setNom_13(String nom_13) {
		this.nom_13 = nom_13;
	}
	
	public String getLieu_13() {
		return lieu_13;
	}
	public void setLieu_13(String lieu_13) {
		this.lieu_13 = lieu_13;
	}
	public String getPrenom_14() {
		return prenom_14;
	}
	public void setPrenom_14(String prenom_14) {
		this.prenom_14 = prenom_14;
	}
	public String getNom_14() {
		return nom_14;
	}
	public void setNom_14(String nom_14) {
		this.nom_14 = nom_14;
	}
	
	public String getLieu_14() {
		return lieu_14;
	}
	public void setLieu_14(String lieu_14) {
		this.lieu_14 = lieu_14;
	}
	public String getPrenom_15() {
		return prenom_15;
	}
	public void setPrenom_15(String prenom_15) {
		this.prenom_15 = prenom_15;
	}
	public String getNom_15() {
		return nom_15;
	}
	public void setNom_15(String nom_15) {
		this.nom_15 = nom_15;
	}
	
	public String getLieu_15() {
		return lieu_15;
	}
	public void setLieu_15(String lieu_15) {
		this.lieu_15 = lieu_15;
	}
	public String getPrenom_16() {
		return prenom_16;
	}
	public void setPrenom_16(String prenom_16) {
		this.prenom_16 = prenom_16;
	}
	public String getNom_16() {
		return nom_16;
	}
	public void setNom_16(String nom_16) {
		this.nom_16 = nom_16;
	}
	
	public String getLieu_16() {
		return lieu_16;
	}
	public void setLieu_16(String lieu_16) {
		this.lieu_16 = lieu_16;
	}
	
	public Date getDate1() {
		return date1;
	}
	public void setDate1(Date date1) {
		this.date1 = date1;
	}
	public Date getDate2() {
		return date2;
	}
	public void setDate2(Date date2) {
		this.date2 = date2;
	}
	public Date getDate3() {
		return date3;
	}
	public void setDate3(Date date3) {
		this.date3 = date3;
	}
	public Date getDate4() {
		return date4;
	}
	public void setDate4(Date date4) {
		this.date4 = date4;
	}
	public Date getDate5() {
		return date5;
	}
	public void setDate5(Date date5) {
		this.date5 = date5;
	}
	public Date getDate6() {
		return date6;
	}
	public void setDate6(Date date6) {
		this.date6 = date6;
	}
	public Date getDate7() {
		return date7;
	}
	public void setDate7(Date date7) {
		this.date7 = date7;
	}
	public Date getDate8() {
		return date8;
	}
	public void setDate8(Date date8) {
		this.date8 = date8;
	}
	public Date getDate9() {
		return date9;
	}
	public void setDate9(Date date9) {
		this.date9 = date9;
	}
	public Date getDate10() {
		return date10;
	}
	public void setDate10(Date date10) {
		this.date10 = date10;
	}
	public Date getDate11() {
		return date11;
	}
	public void setDate11(Date date11) {
		this.date11 = date11;
	}
	public Date getDate12() {
		return date12;
	}
	public void setDate12(Date date12) {
		this.date12 = date12;
	}
	public Date getDate13() {
		return date13;
	}
	public void setDate13(Date date13) {
		this.date13 = date13;
	}
	public Date getDate14() {
		return date14;
	}
	public void setDate14(Date date14) {
		this.date14 = date14;
	}
	public Date getDate15() {
		return date15;
	}
	public void setDate15(Date date15) {
		this.date15 = date15;
	}
	public Date getDate16() {
		return date16;
	}
	public void setDate16(Date date16) {
		this.date16 = date16;
	}
	
	
	@SuppressWarnings("unchecked")
	public void save() throws IOException, JRException {
		parameter.put("region", MyUtil.getUserLogged().getCentre().getCenterRegion());
		parameter.put("depart", MyUtil.getUserLogged().getCentre().getCenterDepartement());
		parameter.put("arrond", MyUtil.getUserLogged().getCentre().getCenterArrondissement());
		parameter.put("collect", MyUtil.getUserLogged().getCentre().getCenterCollectivite());
		parameter.put("commune", MyUtil.getUserLogged().getCentre().getCommune());
		parameter.put("typeCentre", MyUtil.getUserLogged().getCentre().getCenterType());
		parameter.put("nomCentre", MyUtil.getUserLogged().getCentre().getCenterName());
		parameter.put("officier", MyUtil.getUserLogged().getUserPrenom()+" "+MyUtil.getUserLogged().getUserNom());
		
		parameter.put("ligne1", this.getPrenom_1()+" "+this.getNom_1()+" né(e) le "+Tools.getformatDate(Tools.formatDay(this.getDate1()))+" à "+this.getLieu_1());
		
		if(!"".equals(this.getPrenom_2()))
			parameter.put("ligne2", this.getPrenom_2()+" "+this.getNom_2()+" né(e) le "+Tools.getformatDate(Tools.formatDay(this.getDate2()))+" à "+this.getLieu_2());
		else
			parameter.put("ligne2", "");
		
		if(!"".equals(this.getPrenom_3()))
			parameter.put("ligne3", this.getPrenom_3()+" "+this.getNom_3()+" né(e) le "+Tools.getformatDate(Tools.formatDay(this.getDate3()))+" à "+this.getLieu_3());
		else
			parameter.put("ligne3", "");
		if(!"".equals(this.getPrenom_4()))
			parameter.put("ligne4", this.getPrenom_4()+" "+this.getNom_4()+" né(e) le "+Tools.getformatDate(Tools.formatDay(this.getDate4()))+" à "+this.getLieu_4());
		else
			parameter.put("ligne4", "");
		if(!"".equals(this.getPrenom_5()))
			parameter.put("ligne5", this.getPrenom_5()+" "+this.getNom_5()+" né(e) le "+Tools.getformatDate(Tools.formatDay(this.getDate5()))+" à "+this.getLieu_5());
		else
			parameter.put("ligne5", "");
		if(!"".equals(this.getPrenom_5()))
			parameter.put("ligne6", this.getPrenom_6()+" "+this.getNom_6()+" né(e) le "+Tools.getformatDate(Tools.formatDay(this.getDate6()))+" à "+this.getLieu_6());
		else
			parameter.put("ligne6", "");
		if(!"".equals(this.getPrenom_7()))
			parameter.put("ligne7", this.getPrenom_7()+" "+this.getNom_7()+" né(e) le "+Tools.getformatDate(Tools.formatDay(this.getDate7()))+" à "+this.getLieu_7());
		else
			parameter.put("ligne7", "");
		if(!"".equals(this.getPrenom_8()))
			parameter.put("ligne8", this.getPrenom_8()+" "+this.getNom_8()+" né(e) le "+Tools.getformatDate(Tools.formatDay(this.getDate8()))+" à "+this.getLieu_8());
		else
			parameter.put("ligne8", "");
		if(!"".equals(this.getPrenom_9()))
			parameter.put("ligne9", this.getPrenom_9()+" "+this.getNom_9()+" né(e) le "+Tools.getformatDate(Tools.formatDay(this.getDate9()))+" à "+this.getLieu_9());
		else
			parameter.put("ligne9", "");
		if(!"".equals(this.getPrenom_10()))
			parameter.put("ligne10", this.getPrenom_10()+" "+this.getNom_10()+" né(e) le "+Tools.getformatDate(Tools.formatDay(this.getDate10()))+" à "+this.getLieu_10());
		else
			parameter.put("ligne10", "");
		if(!"".equals(this.getPrenom_11()))
			parameter.put("ligne11", this.getPrenom_11()+" "+this.getNom_11()+" né(e) le "+Tools.getformatDate(Tools.formatDay(this.getDate11()))+" à "+this.getLieu_11());
		else
			parameter.put("ligne11", "");
		if(!"".equals(this.getPrenom_12()))
			parameter.put("ligne12", this.getPrenom_12()+" "+this.getNom_12()+" né(e) le "+Tools.getformatDate(Tools.formatDay(this.getDate12()))+" à "+this.getLieu_12());
		else
			parameter.put("ligne12", "");
		if(!"".equals(this.getPrenom_13()))
			parameter.put("ligne13", this.getPrenom_13()+" "+this.getNom_13()+" né(e) le "+Tools.getformatDate(Tools.formatDay(this.getDate13()))+" à "+this.getLieu_13());
		else
			parameter.put("ligne13", "");
		if(!"".equals(this.getPrenom_14()))
			parameter.put("ligne14", this.getPrenom_14()+" "+this.getNom_14()+" né(e) le "+Tools.getformatDate(Tools.formatDay(this.getDate14()))+" à "+this.getLieu_14());
		else
			parameter.put("ligne14", "");
		if(!"".equals(this.getPrenom_15()))
			parameter.put("ligne15", this.getPrenom_15()+" "+this.getNom_15()+" né(e) le "+Tools.getformatDate(Tools.formatDay(this.getDate15()))+" à "+this.getLieu_15());
		else
			parameter.put("ligne15", "");
		if(!"".equals(this.getPrenom_16()))
			parameter.put("ligne16", this.getPrenom_16()+" "+this.getNom_16()+" né(e) le "+Tools.getformatDate(Tools.formatDay(this.getDate16()))+" à "+this.getLieu_16());
		else
			parameter.put("ligne16", "");
		
		parameter.put("pere", this.getPrenom_P()+" "+this.getNom_P());
		
		parameter.put("dateC", Tools.getCurrentDateDDMMYYYY());
		
		FacesContext context = FacesContext.getCurrentInstance();

		String reportSource = context.getExternalContext().getRealPath("ActeNPDF/pieces/CertificatVieCollectif.jasper");
	
		try {
			if(dService.addPiece(pService.findByCode(240))){
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
