/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import dao.ActeDecesDAO;
import dao.ActeDecesDAOImp;
import dao.ActeMariageDAO;
import dao.ActeMariageDAOImp;
import dao.ActeNaissanceDAO;
import dao.ActeNaissanceDAOImp;
import dao.ActivationDAO;
import dao.ActivationDAOImp;
import dao.CentreDAO;
import dao.CentreDAOImp;
import dao.LicenceDAOImp;
import dao.LicencesDAO;
import dao.PieceDelivredDAO;
import dao.PieceDelivredDAOImp;
import dao.PiecesAnnexesDAO;
import dao.PiecesAnnexesDAOImp;
import dao.UserDAO;
import dao.UserDAOImp;


/**
 *
 * @author sambasow
 */
public class Factory {
	
	public static PieceDelivredDAO getPieceDelivredDAO(){
		return new PieceDelivredDAOImp();
	}

	public static UserDAO getUserUserDAO() {
		return new UserDAOImp();
	}

	public static CentreDAO getCentreDAO() {
		return new CentreDAOImp();
	}
	

    public static ActeNaissanceDAO getActeNaissanceDAO(){
        return new ActeNaissanceDAOImp();
    }
    
    public static ActeMariageDAO getActeMariageDAO(){
        return new ActeMariageDAOImp();
    }
    
    public static ActeDecesDAO getActeDecesDAO(){
        return new ActeDecesDAOImp();
    }
    
    
    public static PiecesAnnexesDAO getPiecesAnnexesDAO(){
        return new PiecesAnnexesDAOImp();
    }
    
    public static ActivationDAO getActivationDAO(){
    	return new ActivationDAOImp();
    }
    
    public static LicencesDAO getLicencesDAO(){
    	return new LicenceDAOImp();
    }
   
}