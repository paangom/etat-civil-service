/**
 * 
 */
package dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import models.Activation;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import util.HibernateUtil;

/**
 * @author Papangom
 *
 */
public class ActivationDAOImp implements ActivationDAO, Serializable{

	private static final long serialVersionUID = 1L;
	private Session session = null;
	
	@Override
	public boolean enable(Activation a) {
		boolean flag;
		List<Activation> list = null;
		list = this.getActiveAll();
		for(Activation ac:list){
			if(!this.deseable(ac)){
				flag = false;
				break;
			}
		}
		
        if(session == null)
            session = HibernateUtil.getSessionFactory();
        try {
       	 
            session.beginTransaction();
            session.save(a);
            session.beginTransaction().commit();
            flag = true;
        } catch (Exception e) {
            flag = false;
            session.beginTransaction().rollback();
        }

       return flag;
	}

	@Override
	public boolean deseable(Activation a) {
		
		boolean flag;
		if(session == null)
			session = HibernateUtil.getSessionFactory();
		try{
			session.beginTransaction();
			session.delete(a);
			flag = true;
			session.beginTransaction().commit();
			
		}
		catch(Exception e){
			flag = false;
			session.beginTransaction().rollback();
		}
		return flag;
	}

	@Override
	public boolean update(Activation a) {
		boolean flag;
		
		if (session == null)
			session = HibernateUtil.getSessionFactory();
		// Début de la transaction Hibernate
		try{
		Transaction tx = session.beginTransaction();

		a.setTps_Ecoule(new Date().getTime());
			session.merge(a);
			flag=true;
		tx.commit();
		}
		catch(Exception e){
			flag = false;
			session.beginTransaction().rollback();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Activation> getActiveAll() {
		
		session = HibernateUtil.getSessionFactory();
		session.beginTransaction();
		List<Activation> l = session.createQuery("from Activation a").list();
		return l;
	}

	@Override
	public Activation getActive() {
		
		session = HibernateUtil.getSessionFactory();
		session.beginTransaction();
		Query q = session.createQuery("from Activation c");
		Activation c = (Activation) q.uniqueResult();
		return c;
	}

}
