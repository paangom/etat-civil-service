/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.util.List;

import models.DeclarationDeces;
import models.Search;
import models.Users;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import util.HibernateUtil;

/**
 *
 * @author sambasow
 */
public class ActeDecesDAOImp implements ActeDecesDAO, Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Override
    public boolean SaveDeclaration(DeclarationDeces acte) {
    	Transaction tx = null;
    	Session session = null;
    	 boolean flag = false;
         try {
        	 session = HibernateUtil.getSessionFactory();
        	 tx=session.getTransaction();
        	 tx.begin();
             session.save(acte);
             tx.commit();
             session.flush();
             flag = true;
         } catch (HibernateException he) {
             if(tx!=null)
            	 tx.rollback();
             System.out.println(he);
         }catch(Exception e){
        	 e.printStackTrace();
         }finally{
        	 if(session!=null)
        		 session.close();
         }

        return flag;
    }

    @Override
    public DeclarationDeces findById(int idActe) {
    	Transaction tx = null;
    	Session session = null;
        DeclarationDeces model = null;
        try{
        	session=HibernateUtil.getSessionFactory();
            tx=session.beginTransaction();
            Query q = session.createQuery("from DeclarationDeces a where a.id= :id")
    				.setInteger("id", idActe);
    		model = (DeclarationDeces) q.uniqueResult();
        }
        catch(HibernateException he){
            if(tx!=null)
            	tx.rollback();
            he.printStackTrace();
        }
        finally{
            if(session!=null)
            session.close();
          }
        
        return model;
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<DeclarationDeces> getRegistre(String numRegistre) {
    	Transaction tx = null;
    	Session session = null;
        List<DeclarationDeces> list = null;
        try{
        	session=HibernateUtil.getSessionFactory();
            tx=session.beginTransaction();
            Query q = session.createQuery("from DeclarationDeces a where a.etat= :etat and date_creation like :date")
				.setString("etat", "Valider").setString("date", "%"+numRegistre+"%");
            list = q.list();
        }
        catch(HibernateException he){
            if(tx!=null)
            	tx.rollback();
            he.printStackTrace();
        }
        finally{
            if(session!=null)
            session.close();
        }
            return list;
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<DeclarationDeces> getAllDeclaration() {
    	Transaction tx = null;
    	Session session = null;
        List<DeclarationDeces> list = null;
        try{
        	session=HibernateUtil.getSessionFactory();
            tx=session.beginTransaction();
            Query q = session.createQuery("from DeclarationDeces a where a.etat = :etat")
				.setString("etat", "Instance");
            list = q.list();
        }
		catch(HibernateException he){
            if(tx!=null)
            	tx.rollback();
            he.printStackTrace();
        }
        finally{
            if(session!=null)
            session.close();
          }
		return list;
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<DeclarationDeces> getAllDeclarationByUser(Users u) {
    	Transaction tx = null;
    	Session session = null;
        List<DeclarationDeces> list = null;
        try{
        	session=HibernateUtil.getSessionFactory();
            tx=session.beginTransaction();
            Query q = session.createQuery("from DeclarationDeces a where a.createurDeces = :createur_user and a.etat = :Instance")
				.setInteger("createur_user", u.getUserId()).setString("Instance", "Instance");
            list = q.list();
        }
        catch(HibernateException he){
            if(tx!=null)
            	tx.rollback();
            he.printStackTrace();
        }
        finally{
            if(session!=null)
            session.close();
        }
		return list;
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<DeclarationDeces> getAllDeclarationRejeterByUser(Users u) {
    	Transaction tx = null;
    	Session session = null;
        List<DeclarationDeces> list = null;
        try{
        	session=HibernateUtil.getSessionFactory();
            tx=session.beginTransaction();
            Query q = session.createQuery("from DeclarationActeDece a where a.createurDeces = :createur_user and a.etat = :Instance")
				.setInteger("createur_user", u.getUserId()).setString("Instance", "Rejeter");
            list = q.list();
    	}
	    catch(HibernateException he){
	        if(tx!=null)
	        	tx.rollback();
	        he.printStackTrace();
	    }
	    finally{
	        if(session!=null)
	        session.close();
	      }
		return list;
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<DeclarationDeces> getAllDeclarationValider() {
    	Transaction tx = null;
    	Session session = null;
        List<DeclarationDeces> list = null;
        try{
        	session=HibernateUtil.getSessionFactory();
            tx=session.beginTransaction();
            Query q = session.createQuery("from DeclarationDeces a where a.etat= :etat")
				.setString("etat", "valider");
            list = q.list();
        }
        catch(HibernateException he){
	        if(tx!=null)
	        	tx.rollback();
	        he.printStackTrace();
	    }
	    finally{
	        if(session!=null)
	        session.close();
	      }
        return list;
    }



   

    @Override
    public String numActe(String annee) {
    	Transaction tx = null;
    	Session session = null;
    	Long x = null ;
    	try{
        	session=HibernateUtil.getSessionFactory();
            tx=session.beginTransaction();
            Query q = session.createQuery(" select count(*) from DeclarationDeces a where a.date_creation like :annee and numero_acte != :num and etat = :valider ")
    			.setString("annee", "%"+annee+"%").setString("num", "").setString("valider", "Valider");
            x = ((Long)q.uniqueResult())+1;
    	}
	    catch(HibernateException he){
	        if(tx!=null)
	        	tx.rollback();
	        he.printStackTrace();
	    }
	    finally{
	        if(session!=null)
	        session.close();
	      }
    	return x.toString();
    }

    @Override
    public boolean updateDeclaration(DeclarationDeces acte) {
    	Transaction tx = null;
    	Session session = null;
    	boolean flag=false;
    	try{
        	session=HibernateUtil.getSessionFactory();
            tx=session.beginTransaction();
            DeclarationDeces a = this.findById(acte.getId());

    		if (a == null){
    				session.save(acte);
    				flag=true;
    				tx.commit();
    		}
    		// Sinon on est dans le cas d'une modification de l'acte : merge
    		else{
    			session.merge(acte);
    			flag=true;
    			tx.commit();
    		}
    	}
    	catch(HibernateException he){
    	    if(tx!=null)
    	    	tx.rollback();
    	    he.printStackTrace();
    	}
    	finally{
    	    if(session!=null)
    	    	session.close();
    		}
   		return flag;
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<DeclarationDeces> searchAD(Search s) {
    	Transaction tx = null;
    	Session session = null;
        List<DeclarationDeces> list = null;
        try{
        	session=HibernateUtil.getSessionFactory();
            tx=session.beginTransaction();
            if(!"".equals(s.getNumActe()) && !"".equals(s.getNumReg())){
            	Query q = session.createQuery("FROM DeclarationDeces n WHERE n.etat = :etat and n.numero_acte = :acte and n.date_creation like :date")
        			.setString("etat", "Valider").setString("acte", s.getNumActe()).setString("date", "%"+s.getNumReg()+"%");
        		list = q.list();
            }
            else if(!"".equals(s.getNumActe()) && "".equals(s.getNumReg())){
            	Query q = session.createQuery("FROM DeclarationDeces n WHERE n.etat = :etat and n.numero_acte = :acte")
            			.setString("etat", "Valider").setString("acte", s.getNumActe());
            	list = q.list();
            }
            else if(!"".equals(s.getNumReg()) && "".equals(s.getNumActe())){
            	Query q = session.createQuery("FROM DeclarationDeces n WHERE n.etat = :etat and n.date_creation like :date")
        			.setString("etat", "Valider").setString("date", "%"+s.getNumReg()+"%");
            	list = q.list();
            }
        }
        catch(HibernateException he){
	        if(tx!=null)
	        	tx.rollback();
	        he.printStackTrace();
	    }
	    finally{
	        if(session!=null)
	        session.close();
	    }
        return list;
    }

	@SuppressWarnings("unchecked")
	@Override
	public boolean verifyNumeroJugement(String num, String annee) {
		Transaction tx = null;
    	Session session = null;
		boolean flag=false;
		List<DeclarationDeces> list = null;
		try{
        	session=HibernateUtil.getSessionFactory();
            tx=session.beginTransaction();			
			Query q = session.createQuery("from DeclarationDeces a where a.date_jugement like :dateJ and a.num_jugement = :num")
					.setString("dateJ", "%"+annee+"%").setString("num", num);
			list = q.list();
			if(list.size() > 0)
				flag=true;
		}
		catch(HibernateException he){
	        if(tx!=null)
	        	tx.rollback();
	        he.printStackTrace();
	    }
	    finally{
	        if(session!=null)
	        session.close();
	    }
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean verifyNumeroJugement(String num, String annee, Integer id) {
		Transaction tx = null;
    	Session session = null;
		boolean flag=false;
		List<DeclarationDeces> list = null;
		try{
        	session=HibernateUtil.getSessionFactory();
            tx=session.beginTransaction();	
			Query q = session.createQuery("from DeclarationDeces a where a.date_jugement like :dateJ and a.num_jugement = :num and id != :id")
					.setString("dateJ", "%"+annee+"%").setString("num", num).setInteger("id", id);
			list = q.list();
			if(list.size() > 0)
				flag=true;
		}
		catch(HibernateException he){
	        if(tx!=null)
	        	tx.rollback();
	        he.printStackTrace();
	    }
	    finally{
	        if(session!=null)
	        session.close();
	    }
		return flag;
	}

	@Override
	public int findInstanceByDate(String date) {
		Transaction tx = null;
    	Session session = null;
		int x=0;
		try{
        	session=HibernateUtil.getSessionFactory();
            tx=session.beginTransaction();
            Query q = session.createQuery("from DeclarationDeces a where a.date_creation like  :annee and etat = :etat")
				.setString("annee", "%"+date+"%").setString("etat", "Instance");
            x = q.list().size();
		}
		catch(HibernateException he){
	        if(tx!=null)
	        	tx.rollback();
	        he.printStackTrace();
	    }
	    finally{
	        if(session!=null)
	        session.close();
	    }
        return x;
	}

	@Override
	public int findValidateByDate(String date) {
		Transaction tx = null;
    	Session session = null;
		int x=0;
		try{
        	session=HibernateUtil.getSessionFactory();
            tx=session.beginTransaction();
            Query q = session.createQuery("from DeclarationDeces a where a.date_creation like  :annee and etat = :etat")
				.setString("annee", "%"+date+"%").setString("etat", "Valider");
            x = q.list().size();
		}
		catch(HibernateException he){
	        if(tx!=null)
	        	tx.rollback();
	        he.printStackTrace();
	    }
	    finally{
	        if(session!=null)
	        session.close();
	    }
        return x;
	}

	@Override
	public boolean verifyValidate(DeclarationDeces dec) {
		Transaction tx = null;
    	Session session = null;
		 DeclarationDeces model = null;
		 try{
	        	session=HibernateUtil.getSessionFactory();
	            tx=session.beginTransaction();
	            Query q = session.createQuery("from DeclarationDeces a where a.id= :id and a.etat = :etat")
					.setInteger("id", dec.getId()).setString("etat", "Valider");
	            model = (DeclarationDeces) q.uniqueResult();
		 }
		 catch(HibernateException he){
		        if(tx!=null)
		        	tx.rollback();
		        he.printStackTrace();
		    }
		    finally{
		        if(session!=null)
		        session.close();
		    }
	            if(model == null)
	            	return true;
	            else
	            	return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeclarationDeces> listInstanceByDate(String date) {
		Transaction tx = null;
    	Session session = null;
    	List<DeclarationDeces> list = null;
    	try{
        	session=HibernateUtil.getSessionFactory();
            tx=session.beginTransaction();
            Query q = session.createQuery("from DeclarationDeces a where a.date_creation like  :annee and etat = :etat")
					.setString("annee", "%"+date+"%").setString("etat", "Instance");
			list = q.list();
    	}
    	catch(HibernateException he){
	        if(tx!=null)
	        	tx.rollback();
	        he.printStackTrace();
	    }
	    finally{
	        if(session!=null)
	        session.close();
	    }
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeclarationDeces> listValidateByDate(String date) {
		Transaction tx = null;
    	Session session = null;
    	List<DeclarationDeces> list = null;
    	try{
        	session=HibernateUtil.getSessionFactory();
            tx=session.beginTransaction();
            Query q = session.createQuery("from DeclarationDeces a where a.date_creation like  :annee and etat = :etat")
					.setString("annee", "%"+date+"%").setString("etat", "Valider");
			list = q.list();
    	}
    	catch(HibernateException he){
	        if(tx!=null)
	        	tx.rollback();
	        he.printStackTrace();
	    }
	    finally{
	        if(session!=null)
	        session.close();
	    }
		return list;
	}

}
