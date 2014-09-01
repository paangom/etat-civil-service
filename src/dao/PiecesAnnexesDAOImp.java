package dao;

import java.util.List;

import models.PiecesAnnexes;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import util.HibernateUtil;



public class PiecesAnnexesDAOImp implements PiecesAnnexesDAO{
	
	private Session session = null;

	@Override
	public boolean createPieces(PiecesAnnexes p) {
		
		boolean flag;
        if(session == null)
            session = HibernateUtil.getSessionFactory();
        try {
       	 
            session.beginTransaction();
            session.save(p);
            session.beginTransaction().commit();
            flag = true;
        } catch (Exception e) {
            flag = false;
            session.beginTransaction().rollback();
        }

       return flag;
	}

	@Override
	public boolean updatePieces(int id, String prix) {
		
		boolean flag;
        if(session == null)
            session = HibernateUtil.getSessionFactory();
        try {
        	PiecesAnnexes p = findById(id);
        	if(p != null){
        		p.setPrix(prix);
        	}
            session.beginTransaction();
            session.merge(p);
            session.beginTransaction().commit();
            flag = true;
        } catch (Exception e) {
            flag = false;
            session.beginTransaction().rollback();
        }

       return flag;
		
	}

	@Override
	public void deletePieces(int p) {
		
		// Recupération d'une session
		PiecesAnnexes piece=null;
				if (session == null)
					session = HibernateUtil.getSessionFactory();
				
				// Debut de la transaction
				Transaction tx = session.beginTransaction();
				
				piece = (PiecesAnnexes) session.get(PiecesAnnexes.class, p);
				session.delete(piece);
				
				// Fin de la transaction
				tx.commit();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PiecesAnnexes> allPieces() {
		
		if (session == null)
			session = HibernateUtil.getSessionFactory();
	
		 Query q = session.createQuery("from PiecesAnnexes");
		 List<PiecesAnnexes> l = q.list();
		 
		return l;
	}


	@Override
	public PiecesAnnexes findById(int id) {
		
		if (session == null)
			session = HibernateUtil.getSessionFactory();
		Query q = session.createQuery("from PiecesAnnexes c where c.id= :code")
				.setInteger("code", id);
		PiecesAnnexes c = (PiecesAnnexes) q.uniqueResult();
		return c;
	}

	@Override
	public PiecesAnnexes findByCode(int code) {
		
		if (session == null)
			session = HibernateUtil.getSessionFactory();
		Query q = session.createQuery("from PiecesAnnexes c where c.code = :code")
				.setInteger("code", code);
		PiecesAnnexes c = (PiecesAnnexes) q.uniqueResult();
		return c;
	}

}
