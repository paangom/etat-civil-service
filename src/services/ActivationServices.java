/**
 * 
 */
package services;

import java.util.List;

import models.Activation;
import util.Factory;
import dao.ActivationDAO;

/**
 * @author Papangom
 *
 */
public class ActivationServices {
	
	private ActivationDAO activeDAO = Factory.getActivationDAO();
	
	public boolean enable(Activation a){
		return activeDAO.enable(a);
	}
	
	public List<Activation> getActiveAll(){
		return activeDAO.getActiveAll();
	}
	
	public Activation getActive(){
		return activeDAO.getActive();
	}
	
	public boolean update(Activation a){
		return activeDAO.update(a);
	}
	
	public boolean deseable(Activation a){
		return activeDAO.deseable(a);
	}
	
	

}
