/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.io.Serializable;
import java.util.List;

import models.DeclarationMariage;
import models.Search;
import models.Users;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import util.HibernateUtil;

/**
 *
 * @author sambasow
 */
public class ActeMariageDAOImp implements ActeMariageDAO, Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Session session = null;

    @Override
    public boolean saveDeclarationMariage(DeclarationMariage acte) {
        boolean flag;
        if(session == null)
            session = HibernateUtil.getSessionFactory();
        try {
            session.beginTransaction();
            session.save(acte);
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
    public List<DeclarationMariage> findDeclarationByIdUser(Users u) {
    	List<DeclarationMariage> l = null;
    
    	if (session == null)
			session = HibernateUtil.getSessionFactory();
		if(u != null){
			Query q = session.createQuery("from DeclarationMariage a where a.createurMariage= :createur_user and a.etat = :Instance")
					.setInteger("createur_user", u.getUserId()).setString("Instance", "Instance");
			l = q.list();
		}
		return l;
		
    }


    @SuppressWarnings("unchecked")
	@Override
    public List<DeclarationMariage> getAllDeclarationMariage() {
        List<DeclarationMariage> list = null;
        if (session == null)
			session = HibernateUtil.getSessionFactory();
	
		 Query q = session.createQuery("from DeclarationMariage d where d.etat = :etat").setString("etat", "Instance");
		 list = q.list();
		return list;
    }

    @Override
    public DeclarationMariage findById(int idActe) {
        DeclarationMariage model = null;
        if (session == null)
			session = HibernateUtil.getSessionFactory();
		Query q = session.createQuery("from DeclarationMariage a where a.id = :id")
				.setInteger("id", idActe);
		model = (DeclarationMariage) q.uniqueResult();
        return model;
    }


    @SuppressWarnings("unchecked")
	@Override
    public List<DeclarationMariage> getRegistreMariage() {
        List<DeclarationMariage> list = null;
        if (session == null)
			session = HibernateUtil.getSessionFactory();
		
		Query q = session.createQuery("from DeclarationMariage a where a.etat = :valider" )
				.setString("valider", "Valider");
		list = q.list();
		return list;
    }

    @Override
    public boolean deleteDeclarationMariage(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String numActe(String annee) {
    	if (session == null)
	    	session = HibernateUtil.getSessionFactory();
	    	Query q = session.createQuery(" select count(*) from DeclarationMariage a where a.date_creation like :annee and numero_acte != :num and etat = :valider ")
	    			.setString("annee", "%"+annee+"%").setString("num", "").setString("valider", "Valider");
	    	Long x = ((Long)q.uniqueResult())+1;
	    	return x.toString();
    }

    @Override
    public boolean updateRegistreMariage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String returnNumActe(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteActeMariage(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean jugement(String num, String annee) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean jugementDeclaration(String num, String annee, int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<DeclarationMariage> searchAM(Search s) {
        List<DeclarationMariage> list = null;
        if (session == null) {
            session = HibernateUtil.getSessionFactory();
        }
        if(!"".equals(s.getNumActe()) && !"".equals(s.getNumReg())){
        	Query q = session.createQuery("FROM DeclarationMariage n WHERE n.etat = :etat and n.numero_acte = :acte and n.date_creation like :date")
        			.setString("etat", "Valider").setString("acte", s.getNumActe()).setString("date", "%"+s.getNumReg()+"%");
    		list = q.list();
    	}
    	else if(!"".equals(s.getNumActe()) && "".equals(s.getNumReg())){
    		Query q = session.createQuery("FROM DeclarationMariage n WHERE n.etat = :etat and n.numero_acte = :acte")
        			.setString("etat", "Valider").setString("acte", s.getNumActe());
    		list = q.list();
    	}
    	else if(!"".equals(s.getNumReg()) && "".equals(s.getNumActe())){
    		Query q = session.createQuery("FROM DeclarationMariage n WHERE n.etat = :etat and n.date_creation like :date")
        			.setString("etat", "Valider").setString("date", "%"+s.getNumReg()+"%");
    		list = q.list();
    	}
        return list;
    }


	@Override
	public boolean updateDeclarationMariage(DeclarationMariage dec) {
		
		boolean flag=false;
		try {
			if (session == null)
				session = HibernateUtil.getSessionFactory();
			// D�but de la transaction Hibernate
			Transaction tx = session.beginTransaction();

			// Cherche s'il existe d�j� dans la BDD
			DeclarationMariage a = this.findById(dec.getId());

			// Si l'acte n'existe pas dans la BDD : sauvegarde
			if (a == null){
				session.save(dec);
				flag=true;
			}
			// Sinon on est dans le cas d'une modification de l'acte : merge
			else{
				session.merge(dec);
				flag = true;
			}
			

			// Fin de transaction : synchronisation du contexte de persistance
			tx.commit();
        } catch (Exception e) {
            session.beginTransaction().rollback();
            flag = false;
        }
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean verifyNumeroJugement(String num, String annee) {
		
				boolean flag=false;
				 List<DeclarationMariage> list = null;
			       if (session == null)
					session = HibernateUtil.getSessionFactory();
					
					Query q = session.createQuery("from DeclarationMariage a where a.date_Jugement like :dateJ and a.numero_Jugement = :num")
							.setString("dateJ", "%"+annee+"%").setString("num", num);
					list = q.list();
					if(list.size() > 0)
						flag=true;
					return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean verifyNumeroJugement(String num, String annee, Integer id) {
		
		boolean flag=false;
		 List<DeclarationMariage> list = null;
	       if (session == null)
			session = HibernateUtil.getSessionFactory();
			
			Query q = session.createQuery("from DeclarationMariage a where a.date_Jugement like :dateJ and a.numero_Jugement = :num and id != :id")
					.setString("dateJ", "%"+annee+"%").setString("num", num).setInteger("id", id);
			list = q.list();
			if(list.size() > 0)
				flag=true;
			return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeclarationMariage> registreMariageCurrentYear(String year) {
		
		List<DeclarationMariage> list = null;
        if (session == null)
			session = HibernateUtil.getSessionFactory();
		
		Query q = session.createQuery("from DeclarationMariage a where a.etat = :valider and date_creation like :annee" )
				.setString("valider", "Valider").setString("annee", "%"+year+"%");
		list = q.list();
		return list;
	}

   
	@Override
	public int findInstanceByDate(String date) {
		
		int x=0;
		if (session == null)
			session = HibernateUtil.getSessionFactory();
		
		Query q = session.createQuery("from DeclarationMariage a where a.date_creation like  :annee and etat = :etat")
				.setString("annee", "%"+date+"%").setString("etat", "Instance");
		x = q.list().size();
        return x;
	}

	@Override
	public int findValidateByDate(String date) {
		
		int x=0;
		if (session == null)
			session = HibernateUtil.getSessionFactory();
		
		Query q = session.createQuery("from DeclarationMariage a where a.date_creation like  :annee and etat = :etat")
				.setString("annee", "%"+date+"%").setString("etat", "Valider");
		x = q.list().size();
		
        return x;
	}

	@Override
	public boolean verifyValidate(DeclarationMariage dec) {
		
		DeclarationMariage model = null;
        if (session == null)
			session = HibernateUtil.getSessionFactory();
		Query q = session.createQuery("from DeclarationMariage a where a.id = :id and a.etat = :etat")
				.setInteger("id", dec.getId()).setString("etat", "Valider");
		model = (DeclarationMariage) q.uniqueResult();
        if(model == null)
        	return true;
        else
        	return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeclarationMariage> listInstanceByDate(String date) {
		
		if (session == null)
			session = HibernateUtil.getSessionFactory();
		List<DeclarationMariage> list = null;
		
			Query q = session.createQuery("from DeclarationMariage a where a.date_creation like  :annee and etat = :etat")
					.setString("annee", "%"+date+"%").setString("etat", "Instance");
			list = q.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeclarationMariage> listValidateByDate(String date) {
		
		if (session == null)
			session = HibernateUtil.getSessionFactory();
		List<DeclarationMariage> list = null;
		
			Query q = session.createQuery("from DeclarationMariage a where a.date_creation like  :annee and etat = :etat")
					.setString("annee", "%"+date+"%").setString("etat", "Valider");
			list = q.list();
		return list;
	}
 
}
