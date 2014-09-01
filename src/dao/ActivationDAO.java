/**
 * 
 */
package dao;

import java.util.List;

import models.Activation;

/**
 * @author Papangom
 *
 */
public interface ActivationDAO {
	
	public boolean enable(Activation a);
	
	public boolean deseable( Activation a);
	
	public boolean update(Activation a);
	
	public List<Activation> getActiveAll();
	
	public Activation getActive();

}
