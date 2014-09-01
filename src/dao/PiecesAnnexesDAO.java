package dao;

import java.util.List;

import models.PiecesAnnexes;

public interface PiecesAnnexesDAO {
	
	public boolean createPieces(PiecesAnnexes p);
	
	public boolean updatePieces(int id, String prix);
	
	public void deletePieces(int p);
	
	public List<PiecesAnnexes> allPieces();
	
	public PiecesAnnexes findById(int id);
	
	public PiecesAnnexes findByCode(int code);

}
