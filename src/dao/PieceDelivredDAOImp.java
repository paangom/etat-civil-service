/**
 * 
 */
package dao;

import java.io.Serializable;
import java.util.List;

import models.DelivredPieces;
import models.PiecesAnnexes;
import models.Users;

import org.hibernate.Query;
import org.hibernate.Session;

import util.HibernateUtil;
import util.MyUtil;
import util.Tools;

/**
 * @author admin
 *
 */
public class PieceDelivredDAOImp implements PieceDelivredDAO, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Session session = null;

	@Override
	public boolean addDelivred(PiecesAnnexes p) {
		
		boolean flag;
		DelivredPieces d = new DelivredPieces();
        if(session == null)
            session = HibernateUtil.getSessionFactory();
        try {
        	
        	d.setPiecedelivred(p);
        	d.setDate_delivre(Tools.getCurrentDateTime());
        	d.setPaiement(false);
        	d.setUserdelivred(MyUtil.getUserLogged());
            session.beginTransaction();
            session.save(d);
            session.beginTransaction().commit();
            flag = true;
        } catch (Exception e) {
            flag = false;
            session.beginTransaction().rollback();
        }

       return flag;
	}

	@Override
	public boolean updateDelivred(DelivredPieces d) {
		
		boolean flag;
        if(session == null)
            session = HibernateUtil.getSessionFactory();
        try {
        	session.merge(d);
        	session.beginTransaction().commit();
            flag = true;
        } catch (Exception e) {
            flag = false;
            session.beginTransaction().rollback();
        }

       return flag;
	}

	@Override
	public boolean deleteDelivred(DelivredPieces d) {
		

		boolean flag;
        if(session == null)
            session = HibernateUtil.getSessionFactory();
        try {
       	 
            session.beginTransaction();
            session.delete(d);
            session.beginTransaction().commit();
            flag = true;
        } catch (Exception e) {
            flag = false;
            session.beginTransaction().rollback();
        }

       return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DelivredPieces> findByUser(Users u) {
		
		List<DelivredPieces> model = null;
		 if (session == null)
				session = HibernateUtil.getSessionFactory();
			Query q = session.createQuery("from DelivredPieces a where a.userdelivred = :id ")
					.setParameter("id", u);
			
			model = q.list();
			
			return model;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DelivredPieces> findByPiece(PiecesAnnexes p) {
		
		List<DelivredPieces> model = null;
		 if (session == null)
				session = HibernateUtil.getSessionFactory();
			Query q = session.createQuery("from DelivredPieces a where a.piecedelivred = :id")
					.setParameter("id", p);
			
			model = q.list();
			
			return model;
	}

	@Override
	public boolean updateByUser(Users u) {
		
		boolean flag=true;
		List<DelivredPieces> l = this.findByUser(u);
		for(DelivredPieces list:l){
			list.setDate_paiement(Tools.getCurrentDateTime());
			list.setPaiement(true);
			if(!this.updateDelivred(list))
				flag = false;
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DelivredPieces> findAll() {
		
		List<DelivredPieces> model = null;
		
		if (session == null)
			session = HibernateUtil.getSessionFactory();
		
		Query q = session.createQuery("from DelivredPieces");
		model = q.list();
		return model;
	}

	@Override
	public int nombreDePiece(PiecesAnnexes p, String date) {
		
		int model=0;
		if (session == null)
			session = HibernateUtil.getSessionFactory();
		Query q = session.createQuery("from DelivredPieces a where a.piecedelivred = :id and date_delivre like :dates")
				.setParameter("id", p).setString("dates", "%"+date+"%");
		
		model = q.list().size();
		
		return model;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DelivredPieces> findByDate(String date) {
		
		List<DelivredPieces> model = null;
		if (session == null)
			session = HibernateUtil.getSessionFactory();
		Query q = session.createQuery("from DelivredPieces a where a.date_delivre like :dates")
				.setString("dates", "%"+date+"%");
		
		model = q.list();
		return model;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DelivredPieces> findNonValidateByUser(Users u) {
		
		List<DelivredPieces> model = null;
		 if (session == null)
				session = HibernateUtil.getSessionFactory();
			Query q = session.createQuery("from DelivredPieces a where a.userdelivred = :id and a.paiement = :paiement")
					.setParameter("id", u).setBoolean("paiement", false);
			
			model = q.list();
			
			return model;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DelivredPieces> findValidateByUser(Users u) {
		
		List<DelivredPieces> model = null;
		 if (session == null)
				session = HibernateUtil.getSessionFactory();
			Query q = session.createQuery("from DelivredPieces a where a.userdelivred = :id and a.paiement = :paiement")
					.setParameter("id", u).setBoolean("paiement", true);
			
			model = q.list();
			
			return model;
	}

}
