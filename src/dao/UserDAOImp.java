/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.util.List;

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
@SuppressWarnings("serial")
public class UserDAOImp implements UserDAO, Serializable {

    @Override
    public Users login(Users u) {

        Users model = this.findByUserName(u.getUserUserName());
        if (model != null) {
            if (!model.getUserPassword().equals(u.getUserPassword()) ) {
                model = null;
            }
        }
        return model;
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Users> findAll() {
        List<Users> list = null;
    	Transaction tx = null;
    	Session session = null;
        try{
        	session=HibernateUtil.getSessionFactory();
            tx=session.beginTransaction();
            Query q = session.createQuery("from Users ");
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
    public boolean create(Users u) {
        boolean flag = false;
        Transaction tx = null;
    	Session session = null;
        try{
        	session=HibernateUtil.getSessionFactory();
            tx=session.beginTransaction();
            tx.begin();
            session.save(u);
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
    public boolean update(Users u) {
        boolean flag = false;
        Transaction tx = null;
    	Session session = null;
        try{
        	session=HibernateUtil.getSessionFactory();
            tx=session.beginTransaction();
            Users a = this.getUserById(u.getUserId());
		
            // Si l'acte n'existe pas dans la BDD : sauvegarde
            if (a == null){
            	tx.begin();
                session.save(u);
                tx.commit();
                session.flush();
            	flag=true;
            }
            // Sinon on est dans le cas d'une modification de l'acte : merge
            else{
            	session.merge(u);
            	tx.commit();
            	flag=true;
            }
        }
        catch(HibernateException he){
            if(tx!=null)
            	tx.rollback();
        he.printStackTrace();
        }finally{
            if(session!=null)
            session.close();
        }
            
        return flag;
    }


    @Override
    public Users findByUserName(String uname) {
    	Users u = null;
    	Transaction tx = null;
    	Session session = null;
        try{
        	session=HibernateUtil.getSessionFactory();
            tx=session.beginTransaction();
    		Query q = session.createQuery("from Users u where u.userUserName = :userName")
    				.setString("userName", uname);

    		// Recuperation et renvoi du resultat unique
    		u = (Users) q.uniqueResult();
        }
        catch (HibernateException he) {
            if(tx!=null)
           	 tx.rollback();
            System.out.println(he);
        }catch(Exception e){
       	 e.printStackTrace();
        }finally{
       	 if(session!=null)
       		 session.close();
        }
    	return u;
    }

    @Override
    public Users getUserById(int id) {
    	Transaction tx = null;
    	Session session = null;
    	Users u = null;
        try{
        	session=HibernateUtil.getSessionFactory();
            tx=session.beginTransaction();
            // Requete de recuperation du User correspondant au userName donn�
            Query q = session.createQuery("from Users u where u.id = :id ")
				.setInteger("id", id);

            // Recuperation et renvoi du resultat unique
            u = (Users) q.uniqueResult();
        }
        catch (HibernateException he) {
            if(tx!=null)
           	 tx.rollback();
            System.out.println(he);
        }catch(Exception e){
       	 e.printStackTrace();
        }finally{
       	 if(session!=null)
       		 session.close();
        }
		return u;
    }

	@Override
	public boolean viderUsers() {
		
		 boolean flag = false;
		 Transaction tx = null;
	    	Session session = null;
	        try{
	        	session=HibernateUtil.getSessionFactory();
	            tx=session.beginTransaction();
	    		String hqlDelete = "truncate table Users";
	    		session.createSQLQuery( hqlDelete )
	                   .executeUpdate();
	                    tx.commit();
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
	public boolean deleteUser(int id) {
		
		boolean flag = false;
		Transaction tx = null;
    	Session session = null;
        try{
        	session=HibernateUtil.getSessionFactory();
            tx=session.beginTransaction();
            Users user = getUserById(id);
       
            session.merge(user);
            tx.commit();
            flag = true;
        }catch (HibernateException he) {
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
	public boolean reactiverUser(int id) {
		
		boolean flag = false;
		Transaction tx = null;
    	Session session = null;
        try{
        	session=HibernateUtil.getSessionFactory();
            tx=session.beginTransaction();
            Users user = findUserDel(id);
            user.setEtat(true);
        
            session.merge(user);
            tx.commit();
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
	public Users findUserDel(int id) {
		Users u = null;
		Transaction tx = null;
    	Session session = null;
        try{
        	session=HibernateUtil.getSessionFactory();
            tx=session.beginTransaction();
            // Requete de recuperation du User correspondant au userName donn�
            Query q = session.createQuery("from Users u where u.id = :id and u.etat = :etat")
				.setInteger("id", id).setBoolean("etat", false);

            // Recuperation et renvoi du resultat unique
            u = (Users) q.uniqueResult();
        }
        catch (HibernateException he) {
            if(tx!=null)
           	 tx.rollback();
            System.out.println(he);
        }catch(Exception e){
       	 e.printStackTrace();
        }finally{
       	 if(session!=null)
       		 session.close();
        }
        return u;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int lastUser() {
		int x = 0;
		Transaction tx = null;
    	Session session = null;
        try{
        	session=HibernateUtil.getSessionFactory();
            tx=session.beginTransaction();
            Query q = session.createQuery("from Users");

            List<Users> list = q.list();
            x = list.size()-1;
        }
        catch (HibernateException he) {
            if(tx!=null)
           	 tx.rollback();
            System.out.println(he);
        }catch(Exception e){
       	 e.printStackTrace();
        }finally{
       	 if(session!=null)
       		 session.close();
        }
		return x;
	}

}
