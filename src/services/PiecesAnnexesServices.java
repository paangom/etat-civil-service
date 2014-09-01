package services;

import java.util.List;

import models.PiecesAnnexes;
import util.Factory;
import dao.PiecesAnnexesDAO;

public class PiecesAnnexesServices {
	
	private PiecesAnnexesDAO pieceDAO = Factory.getPiecesAnnexesDAO();
	
	public List<PiecesAnnexes> allPieces(){
		return pieceDAO.allPieces();
	}
	
	public PiecesAnnexes findById(int idPiece) {
		return pieceDAO.findById(idPiece);
	}
	
	public boolean createPiece(PiecesAnnexes p){
		return pieceDAO.createPieces(p);
	}
	
	public boolean updatePieces(int id, String prix){
		return pieceDAO.updatePieces(id, prix);
	}
	
	public void deletePieces(int id){
		pieceDAO.deletePieces(id);
	}
	
	public PiecesAnnexes findByCode(int code){
		return pieceDAO.findByCode(code);
	}

}
