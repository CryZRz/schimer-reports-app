package com.schimer.reportsapp.domain.repositories;

import com.schimer.reportsapp.infrastructure.hibernate.HibernateConfig;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import java.util.List;

public abstract class BaseRepository <T>{
    private  final Class<T> type;

    public BaseRepository(Class<T> type) {
        this.type = type;
    }

    public T save(T entity) {
        Transaction transaction = null;
        try(var session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        }catch (HibernateException ex){
            throw ex;
        }
    }

    public List<T> getAll() {
        try(var session = HibernateConfig.getSessionFactory().openSession()) {
            return  session.createQuery("FROM " + type.getName(), type).list();
        }
    }

    public T update(T entity) {
        Transaction transaction = null;
        try(var session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
            return entity;
        }catch (HibernateException ex){
            if(transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            throw ex;
        }
    }

    public void delete(T entity) {
        Transaction transaction = null;
        try(var session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        }catch (HibernateException ex){
            if(transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            throw ex;
        }
    }

}
