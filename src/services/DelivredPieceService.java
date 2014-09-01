/**
 * 
 */
package services;

import java.util.List;

import models.DelivredPieces;
import models.PiecesAnnexes;
import models.Users;
import util.Factory;
import dao.PieceDelivredDAO;

/**
 * @author admin
 *
 */
public class DelivredPieceService {

	private PieceDelivredDAO daoPiece = Factory.getPieceDelivredDAO();
	
	public boolean addPiece(PiecesAnnexes p){
		return daoPiece.addDelivred(p);
	}
	
	public List<DelivredPieces> getAllPiecesDel(){
		return daoPiece.findAll();
	}
	
	public List<DelivredPieces> getAllPieceBU(Users u){
		return daoPiece.findByUser(u);
	}
	
	public boolean updatePiece(DelivredPieces p){
		return daoPiece.updateDelivred(p);
	}
	
	public int nombreDePieces(PiecesAnnexes p, String date){
		return daoPiece.nombreDePiece(p, date);
	}
	
	public List<DelivredPieces> findByPiece(PiecesAnnexes p){
		return daoPiece.findByPiece(p);
	}
	
	public List<DelivredPieces> findByDate(String date){
		return daoPiece.findByDate(date);
	}
	
	public List<DelivredPieces> findPieceNonValiderByUser(Users u){
		return daoPiece.findNonValidateByUser(u);
	}
	
	public List<DelivredPieces> findPieceValiderByUser(Users u){
		return daoPiece.findValidateByUser(u);
	}
}
