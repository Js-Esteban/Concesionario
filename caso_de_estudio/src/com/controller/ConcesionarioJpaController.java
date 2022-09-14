/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import com.entities.Concesionario;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Js
 */
public class ConcesionarioJpaController implements Serializable {

    public ConcesionarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public ConcesionarioJpaController() {
        this.emf = Persistence.createEntityManagerFactory("caso_de_estudioPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Concesionario concesionario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(concesionario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Concesionario concesionario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            concesionario = em.merge(concesionario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = concesionario.getIdConcecionario();
                if (findConcesionario(id) == null) {
                    throw new NonexistentEntityException("The concesionario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Concesionario concesionario;
            try {
                concesionario = em.getReference(Concesionario.class, id);
                concesionario.getIdConcecionario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The concesionario with id " + id + " no longer exists.", enfe);
            }
            em.remove(concesionario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Concesionario> findConcesionarioEntities() {
        return findConcesionarioEntities(true, -1, -1);
    }

    public List<Concesionario> findConcesionarioEntities(int maxResults, int firstResult) {
        return findConcesionarioEntities(false, maxResults, firstResult);
    }

    private List<Concesionario> findConcesionarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Concesionario.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Concesionario findConcesionario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Concesionario.class, id);
        } finally {
            em.close();
        }
    }

    public int getConcesionarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Concesionario> rt = cq.from(Concesionario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
