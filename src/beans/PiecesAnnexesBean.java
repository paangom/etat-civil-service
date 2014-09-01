package beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import models.PiecesAnnexes;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;

import services.PiecesAnnexesServices;
import util.Tools;

public class PiecesAnnexesBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PiecesAnnexesServices pService = new PiecesAnnexesServices();
	private PiecesAnnexes pieceTOAdd = null;
	private PiecesAnnexes pieceToConsult = null;
	private List<PiecesAnnexes> allPieces = null;
	
	public PiecesAnnexesBean(){
		if(this.pieceTOAdd == null){
			this.pieceTOAdd = new PiecesAnnexes();
		}
	}

	public PiecesAnnexes getPieceTOAdd() {
		return pieceTOAdd;
	}

	public void setPieceTOAdd(PiecesAnnexes pieceTOAdd) {
		this.pieceTOAdd = pieceTOAdd;
	}

	public PiecesAnnexes getPieceToConsult() {
		return pieceToConsult;
	}

	public void setPieceToConsult(PiecesAnnexes pieceToConsult) {
		this.pieceToConsult = pieceToConsult;
	}

	public List<PiecesAnnexes> getAllPieces() {
		allPieces = pService.allPieces();
		return allPieces;
	}

	public void setAllPieces(List<PiecesAnnexes> allPieces) {
		this.allPieces = allPieces;
	}
	
	@SuppressWarnings("static-access")
	public void savePieces(){
		if(this.pieceTOAdd.getLibelle() != null){
			if(this.pieceTOAdd.getPrix() == null)
				this.pieceTOAdd.setPrix("0");
			this.pieceTOAdd.setDate_creation(Tools.getCurrentDateTime());
			if(pService.createPiece(this.pieceTOAdd)){
				//allPieces = pService.allPieces();
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  "Le document "+this.pieceTOAdd.getLibelle()+" a été ajoutée avec succès!", null);
				FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
	        	
	        	context.getExternalContext().getFlash().setKeepMessages(true);
        	}
			else{
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "Impossible d'ajouter le document "+this.pieceTOAdd.getLibelle()+"!", null);
				FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
	        	
	        	context.getExternalContext().getFlash().setKeepMessages(true);
			}
        	
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,  "Aucun libelé n'est attribué pour ce document!", null);
			FacesContext context = FacesContext.getCurrentInstance();
            context.getCurrentInstance().addMessage(null, message);
        	
        	context.getExternalContext().getFlash().setKeepMessages(true);
		}
	}
	
	@SuppressWarnings("static-access")
	public void updatePieces(CellEditEvent event){
		Object oldValue = event.getOldValue();  
        Object newValue = event.getNewValue(); 
        
		if(newValue != null && !newValue.equals(oldValue) && newValue instanceof Integer) {
			if(pService.updatePieces((event.getRowIndex()+1), event.getNewValue().toString())){
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  "La mise à jour du prix du document s'est effectuée avec succès.", null);
				FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
	        	context.getExternalContext().getFlash().setKeepMessages(true);
			}
			else{
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "Le document n'a pas été modifié.", null);
				FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
	        	context.getExternalContext().getFlash().setKeepMessages(true);
			}
			
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "Aucun prix n'est attribué au document.", null);
			FacesContext context = FacesContext.getCurrentInstance();
            context.getCurrentInstance().addMessage(null, message);
        	context.getExternalContext().getFlash().setKeepMessages(true);
		}
		
		
	}
	
	@SuppressWarnings("static-access")
	public void deletePieces(){
		pService.deletePieces(this.pieceToConsult.getId());
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  "Le document "+this.pieceToConsult.getLibelle()+" a été supprimé avec succès!", null);
		FacesContext context = FacesContext.getCurrentInstance();
        context.getCurrentInstance().addMessage(null, message);
    	
    	context.getExternalContext().getFlash().setKeepMessages(true);
	}
	
	public void piecesAnnexes() {
		RequestContext.getCurrentInstance().openDialog("piecesAnnexes");
	}

}
