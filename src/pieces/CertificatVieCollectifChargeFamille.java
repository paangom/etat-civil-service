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

public class CertificatVieCollectifChargeFamille {
	
	private PiecesAnnexesServices pService = new PiecesAnnexesServices();
	private DelivredPieceService dService = new DelivredPieceService();

	JasperPrint jasperPrint;
	
	@SuppressWarnings("rawtypes")
	private Map parameter = new HashMap();
	
	private String prenom, nom, titre, doc, prenom1, nom1, lieu1,prenom2, nom2, lieu2,
		prenom3, nom3, lieu3, prenom4, nom4, lieu4, prenom5, nom5, lieu5, prenom6, 
		nom6, lieu6, prenom7, nom7, lieu7, prenom8, nom8, lieu8, prenom9, nom9, lieu9, degre, dom, prenom_P, nom_P;

	private Date date, date1, date2, date3, date4, date5, date6, date7, date8, date9;

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

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDoc() {
		return doc;
	}

	public void setDoc(String doc) {
		this.doc = doc;
	}

	
	public String getDegre() {
		return degre;
	}

	public void setDegre(String degre) {
		this.degre = degre;
	}

	public String getDom() {
		return dom;
	}

	public void setDom(String dom) {
		this.dom = dom;
	}


	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
	public String getPrenom1() {
		return prenom1;
	}

	public void setPrenom1(String prenom1) {
		this.prenom1 = prenom1;
	}

	public String getNom1() {
		return nom1;
	}

	public void setNom1(String nom1) {
		this.nom1 = nom1;
	}

	public String getLieu1() {
		return lieu1;
	}

	public void setLieu1(String lieu1) {
		this.lieu1 = lieu1;
	}

	public String getPrenom2() {
		return prenom2;
	}

	public void setPrenom2(String prenom2) {
		this.prenom2 = prenom2;
	}

	public String getNom2() {
		return nom2;
	}

	public void setNom2(String nom2) {
		this.nom2 = nom2;
	}

	public String getLieu2() {
		return lieu2;
	}

	public void setLieu2(String lieu2) {
		this.lieu2 = lieu2;
	}

	public String getPrenom3() {
		return prenom3;
	}

	public void setPrenom3(String prenom3) {
		this.prenom3 = prenom3;
	}

	public String getNom3() {
		return nom3;
	}

	public void setNom3(String nom3) {
		this.nom3 = nom3;
	}

	public String getLieu3() {
		return lieu3;
	}

	public void setLieu3(String lieu3) {
		this.lieu3 = lieu3;
	}

	public String getPrenom4() {
		return prenom4;
	}

	public void setPrenom4(String prenom4) {
		this.prenom4 = prenom4;
	}

	public String getNom4() {
		return nom4;
	}

	public void setNom4(String nom4) {
		this.nom4 = nom4;
	}

	public String getLieu4() {
		return lieu4;
	}

	public void setLieu4(String lieu4) {
		this.lieu4 = lieu4;
	}

	public String getPrenom5() {
		return prenom5;
	}

	public void setPrenom5(String prenom5) {
		this.prenom5 = prenom5;
	}

	public String getNom5() {
		return nom5;
	}

	public void setNom5(String nom5) {
		this.nom5 = nom5;
	}

	public String getLieu5() {
		return lieu5;
	}

	public void setLieu5(String lieu5) {
		this.lieu5 = lieu5;
	}

	public String getPrenom6() {
		return prenom6;
	}

	public void setPrenom6(String prenom6) {
		this.prenom6 = prenom6;
	}

	public String getNom6() {
		return nom6;
	}

	public void setNom6(String nom6) {
		this.nom6 = nom6;
	}

	public String getLieu6() {
		return lieu6;
	}

	public void setLieu6(String lieu6) {
		this.lieu6 = lieu6;
	}

	public String getPrenom7() {
		return prenom7;
	}

	public void setPrenom7(String prenom7) {
		this.prenom7 = prenom7;
	}

	public String getNom7() {
		return nom7;
	}

	public void setNom7(String nom7) {
		this.nom7 = nom7;
	}

	public String getLieu7() {
		return lieu7;
	}

	public void setLieu7(String lieu7) {
		this.lieu7 = lieu7;
	}

	public String getPrenom8() {
		return prenom8;
	}

	public void setPrenom8(String prenom8) {
		this.prenom8 = prenom8;
	}

	public String getNom8() {
		return nom8;
	}

	public void setNom8(String nom8) {
		this.nom8 = nom8;
	}

	public String getLieu8() {
		return lieu8;
	}

	public void setLieu8(String lieu8) {
		this.lieu8 = lieu8;
	}

	public String getPrenom9() {
		return prenom9;
	}

	public void setPrenom9(String prenom9) {
		this.prenom9 = prenom9;
	}

	public String getNom9() {
		return nom9;
	}

	public void setNom9(String nom9) {
		this.nom9 = nom9;
	}

	public String getLieu9() {
		return lieu9;
	}

	public void setLieu9(String lieu9) {
		this.lieu9 = lieu9;
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

	@SuppressWarnings("unchecked")
	public void save() throws IOException{
		
		parameter.put("region", MyUtil.getUserLogged().getCentre().getCenterRegion());
		parameter.put("depart", MyUtil.getUserLogged().getCentre().getCenterDepartement());
		parameter.put("arrond", MyUtil.getUserLogged().getCentre().getCenterArrondissement());
		parameter.put("collect", MyUtil.getUserLogged().getCentre().getCenterCollectivite());
		parameter.put("commune", MyUtil.getUserLogged().getCentre().getCommune());
		parameter.put("typeCentre", MyUtil.getUserLogged().getCentre().getCenterType());
		parameter.put("nomCentre", MyUtil.getUserLogged().getCentre().getCenterName());
		parameter.put("officier", MyUtil.getUserLogged().getUserPrenom()+" "+MyUtil.getUserLogged().getUserNom());
		
		parameter.put("prenom_T",this.getPrenom()+" "+this.getNom()+" "+this.getTitre());
		parameter.put("document", this.getDoc());
		parameter.put("date",Tools.getformatDate(Tools.formatDay(this.getDate())));
		parameter.put("ligne1",this.getPrenom1()+" "+this.getNom1()+" né(e) le "+Tools.formatDay(this.getDate1())+" à "+this.getLieu1());
		
		if(!"".equals(this.getPrenom2()) && !"".equals(this.getNom2()) && !"".equals(this.getLieu2()))
			parameter.put("ligne2",this.getPrenom2()+" "+this.getNom2()+" né(e) le "+Tools.formatDay(this.getDate2())+" à "+this.getLieu2());
		else
			parameter.put("ligne2", "");
		
		if(!"".equals(this.getPrenom3()) && !"".equals(this.getNom3()) && !"".equals(this.getLieu3()))
			parameter.put("ligne3",this.getPrenom3()+" "+this.getNom3()+" né(e) le "+Tools.formatDay(this.getDate3())+" à "+this.getLieu3());
		else
			parameter.put("ligne3", "");
		
		if(!"".equals(this.getPrenom4()) && !"".equals(this.getNom4()) && !"".equals(this.getLieu4()))
			parameter.put("ligne4",this.getPrenom4()+" "+this.getNom4()+" né(e) le "+Tools.formatDay(this.getDate4())+" à "+this.getLieu4());
		else
			parameter.put("ligne4", "");
		
		if(!"".equals(this.getPrenom5()) && !"".equals(this.getNom5()) && !"".equals(this.getLieu5()))
			parameter.put("ligne5",this.getPrenom5()+" "+this.getNom5()+" né(e) le "+Tools.formatDay(this.getDate5())+" à "+this.getLieu5());
		else
			parameter.put("ligne5", "");
		
		if(!"".equals(this.getPrenom6()) && !"".equals(this.getNom6()) && !"".equals(this.getLieu6()))
			parameter.put("ligne6",this.getPrenom6()+" "+this.getNom6()+" né(e) le "+Tools.formatDay(this.getDate6())+" à "+this.getLieu6());
		else
			parameter.put("ligne6", "");
		
		if(!"".equals(this.getPrenom7()) && !"".equals(this.getNom7()) && !"".equals(this.getLieu7()))
			parameter.put("ligne7",this.getPrenom7()+" "+this.getNom7()+" né(e) le "+Tools.formatDay(this.getDate7())+" à "+this.getLieu7());
		else
			parameter.put("ligne7", "");
		
		
		if(!"".equals(this.getPrenom8()) && !"".equals(this.getNom8()) && !"".equals(this.getLieu8()))
			parameter.put("ligne8",this.getPrenom8()+" "+this.getNom8()+" né(e) le "+Tools.formatDay(this.getDate8())+" à "+this.getLieu8());
		else
			parameter.put("ligne8", "");
		
		if(!"".equals(this.getPrenom9()) && !"".equals(this.getNom9()) && !"".equals(this.getLieu9()))
			parameter.put("ligne9",this.getPrenom9()+" "+this.getNom9()+" né(e) le "+Tools.formatDay(this.getDate9())+" à "+this.getLieu9());
		else
			parameter.put("ligne9", "");
		
		parameter.put("degre", this.getDegre());
		parameter.put("domicile", this.getDom());
		parameter.put("parent", this.getPrenom_P()+" "+this.getNom_P());
		parameter.put("dateC", Tools.getCurrentDateDDMMYYYY());
		
		FacesContext context = FacesContext.getCurrentInstance();

		String reportSource = context.getExternalContext().getRealPath("ActeNPDF/pieces/CertificatVieCollectifChargeFamille.jasper");
	
		try {
			if(dService.addPiece(pService.findByCode(330))){
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
	
}
