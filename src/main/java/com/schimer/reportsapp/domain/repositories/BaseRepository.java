package com.schimer.reportsapp.domain.repositories;

import com.schimer.reportsapp.infrastructure.hibernate.HibernateConfig;

import java.util.List;

public abstract class BaseRepository <T>{
    private  final Class<T> type;

    public BaseRepository(Class<T> type) {
        this.type = type;
    }

    public void save(T entity) {
        try(var session = HibernateConfig.getSessionFactory().openSession()) {
            var transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        }
    }

    public List<T> getAll() {
        try(var session = HibernateConfig.getSessionFactory().openSession()) {
            return  session.createQuery("FROM " + type.getName(), type).list();
        }
    }

    public void update(T entity) {
        try(var session = HibernateConfig.getSessionFactory().openSession()) {
            var transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        }
    }

    public void delete(T entity) {
        try(var session = HibernateConfig.getSessionFactory().openSession()) {
            var transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        }
    }

}
