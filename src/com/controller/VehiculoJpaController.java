/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.entities.Tipovehiculo;
import com.entities.Marca;
import com.entities.Vehiculo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Js
 */
public class VehiculoJpaController implements Serializable {

    public VehiculoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vehiculo vehiculo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipovehiculo idTipoFk = vehiculo.getIdTipoFk();
            if (idTipoFk != null) {
                idTipoFk = em.getReference(idTipoFk.getClass(), idTipoFk.getIdTipo());
                vehiculo.setIdTipoFk(idTipoFk);
            }
            Marca idMarcaFk = vehiculo.getIdMarcaFk();
            if (idMarcaFk != null) {
                idMarcaFk = em.getReference(idMarcaFk.getClass(), idMarcaFk.getIdMarca());
                vehiculo.setIdMarcaFk(idMarcaFk);
            }
            em.persist(vehiculo);
            if (idTipoFk != null) {
                idTipoFk.getVehiculoList().add(vehiculo);
                idTipoFk = em.merge(idTipoFk);
            }
            if (idMarcaFk != null) {
                idMarcaFk.getVehiculoList().add(vehiculo);
                idMarcaFk = em.merge(idMarcaFk);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vehiculo vehiculo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vehiculo persistentVehiculo = em.find(Vehiculo.class, vehiculo.getIdVehiculo());
            Tipovehiculo idTipoFkOld = persistentVehiculo.getIdTipoFk();
            Tipovehiculo idTipoFkNew = vehiculo.getIdTipoFk();
            Marca idMarcaFkOld = persistentVehiculo.getIdMarcaFk();
            Marca idMarcaFkNew = vehiculo.getIdMarcaFk();
            if (idTipoFkNew != null) {
                idTipoFkNew = em.getReference(idTipoFkNew.getClass(), idTipoFkNew.getIdTipo());
                vehiculo.setIdTipoFk(idTipoFkNew);
            }
            if (idMarcaFkNew != null) {
                idMarcaFkNew = em.getReference(idMarcaFkNew.getClass(), idMarcaFkNew.getIdMarca());
                vehiculo.setIdMarcaFk(idMarcaFkNew);
            }
            vehiculo = em.merge(vehiculo);
            if (idTipoFkOld != null && !idTipoFkOld.equals(idTipoFkNew)) {
                idTipoFkOld.getVehiculoList().remove(vehiculo);
                idTipoFkOld = em.merge(idTipoFkOld);
            }
            if (idTipoFkNew != null && !idTipoFkNew.equals(idTipoFkOld)) {
                idTipoFkNew.getVehiculoList().add(vehiculo);
                idTipoFkNew = em.merge(idTipoFkNew);
            }
            if (idMarcaFkOld != null && !idMarcaFkOld.equals(idMarcaFkNew)) {
                idMarcaFkOld.getVehiculoList().remove(vehiculo);
                idMarcaFkOld = em.merge(idMarcaFkOld);
            }
            if (idMarcaFkNew != null && !idMarcaFkNew.equals(idMarcaFkOld)) {
                idMarcaFkNew.getVehiculoList().add(vehiculo);
                idMarcaFkNew = em.merge(idMarcaFkNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = vehiculo.getIdVehiculo();
                if (findVehiculo(id) == null) {
                    throw new NonexistentEntityException("The vehiculo with id " + id + " no longer exists.");
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
            Vehiculo vehiculo;
            try {
                vehiculo = em.getReference(Vehiculo.class, id);
                vehiculo.getIdVehiculo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vehiculo with id " + id + " no longer exists.", enfe);
            }
            Tipovehiculo idTipoFk = vehiculo.getIdTipoFk();
            if (idTipoFk != null) {
                idTipoFk.getVehiculoList().remove(vehiculo);
                idTipoFk = em.merge(idTipoFk);
            }
            Marca idMarcaFk = vehiculo.getIdMarcaFk();
            if (idMarcaFk != null) {
                idMarcaFk.getVehiculoList().remove(vehiculo);
                idMarcaFk = em.merge(idMarcaFk);
            }
            em.remove(vehiculo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vehiculo> findVehiculoEntities() {
        return findVehiculoEntities(true, -1, -1);
    }

    public List<Vehiculo> findVehiculoEntities(int maxResults, int firstResult) {
        return findVehiculoEntities(false, maxResults, firstResult);
    }

    private List<Vehiculo> findVehiculoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vehiculo.class));
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

    public Vehiculo findVehiculo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vehiculo.class, id);
        } finally {
            em.close();
        }
    }

    public int getVehiculoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vehiculo> rt = cq.from(Vehiculo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
