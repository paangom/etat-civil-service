package dao;

import java.util.List;

import models.DelivredPieces;
import models.PiecesAnnexes;
import models.Users;

public interface PieceDelivredDAO {

	public boolean addDelivred(PiecesAnnexes p);
	
	public boolean updateDelivred(DelivredPieces d);
	
	public boolean deleteDelivred(DelivredPieces d);
	
	public List<DelivredPieces> findByUser(Users u);
	
	public List<DelivredPieces> findByPiece(PiecesAnnexes p);
	
	public boolean updateByUser(Users u);
	
	public List<DelivredPieces> findAll();
	
	public int nombreDePiece(PiecesAnnexes p, String date);
	
	public List<DelivredPieces> findByDate(String date);
	
	public List<DelivredPieces> findNonValidateByUser(Users u);
	
	public List<DelivredPieces> findValidateByUser(Users u);
	
	
}
