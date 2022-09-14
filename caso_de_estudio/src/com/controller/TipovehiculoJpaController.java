/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import com.entities.Tipovehiculo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.entities.Vehiculo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Js
 */
public class TipovehiculoJpaController implements Serializable {

    public TipovehiculoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipovehiculo tipovehiculo) {
        if (tipovehiculo.getVehiculoList() == null) {
            tipovehiculo.setVehiculoList(new ArrayList<Vehiculo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Vehiculo> attachedVehiculoList = new ArrayList<Vehiculo>();
            for (Vehiculo vehiculoListVehiculoToAttach : tipovehiculo.getVehiculoList()) {
                vehiculoListVehiculoToAttach = em.getReference(vehiculoListVehiculoToAttach.getClass(), vehiculoListVehiculoToAttach.getIdVehiculo());
                attachedVehiculoList.add(vehiculoListVehiculoToAttach);
            }
            tipovehiculo.setVehiculoList(attachedVehiculoList);
            em.persist(tipovehiculo);
            for (Vehiculo vehiculoListVehiculo : tipovehiculo.getVehiculoList()) {
                Tipovehiculo oldIdTipoFkOfVehiculoListVehiculo = vehiculoListVehiculo.getIdTipoFk();
                vehiculoListVehiculo.setIdTipoFk(tipovehiculo);
                vehiculoListVehiculo = em.merge(vehiculoListVehiculo);
                if (oldIdTipoFkOfVehiculoListVehiculo != null) {
                    oldIdTipoFkOfVehiculoListVehiculo.getVehiculoList().remove(vehiculoListVehiculo);
                    oldIdTipoFkOfVehiculoListVehiculo = em.merge(oldIdTipoFkOfVehiculoListVehiculo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipovehiculo tipovehiculo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipovehiculo persistentTipovehiculo = em.find(Tipovehiculo.class, tipovehiculo.getIdTipo());
            List<Vehiculo> vehiculoListOld = persistentTipovehiculo.getVehiculoList();
            List<Vehiculo> vehiculoListNew = tipovehiculo.getVehiculoList();
            List<Vehiculo> attachedVehiculoListNew = new ArrayList<Vehiculo>();
            for (Vehiculo vehiculoListNewVehiculoToAttach : vehiculoListNew) {
                vehiculoListNewVehiculoToAttach = em.getReference(vehiculoListNewVehiculoToAttach.getClass(), vehiculoListNewVehiculoToAttach.getIdVehiculo());
                attachedVehiculoListNew.add(vehiculoListNewVehiculoToAttach);
            }
            vehiculoListNew = attachedVehiculoListNew;
            tipovehiculo.setVehiculoList(vehiculoListNew);
            tipovehiculo = em.merge(tipovehiculo);
            for (Vehiculo vehiculoListOldVehiculo : vehiculoListOld) {
                if (!vehiculoListNew.contains(vehiculoListOldVehiculo)) {
                    vehiculoListOldVehiculo.setIdTipoFk(null);
                    vehiculoListOldVehiculo = em.merge(vehiculoListOldVehiculo);
                }
            }
            for (Vehiculo vehiculoListNewVehiculo : vehiculoListNew) {
                if (!vehiculoListOld.contains(vehiculoListNewVehiculo)) {
                    Tipovehiculo oldIdTipoFkOfVehiculoListNewVehiculo = vehiculoListNewVehiculo.getIdTipoFk();
                    vehiculoListNewVehiculo.setIdTipoFk(tipovehiculo);
                    vehiculoListNewVehiculo = em.merge(vehiculoListNewVehiculo);
                    if (oldIdTipoFkOfVehiculoListNewVehiculo != null && !oldIdTipoFkOfVehiculoListNewVehiculo.equals(tipovehiculo)) {
                        oldIdTipoFkOfVehiculoListNewVehiculo.getVehiculoList().remove(vehiculoListNewVehiculo);
                        oldIdTipoFkOfVehiculoListNewVehiculo = em.merge(oldIdTipoFkOfVehiculoListNewVehiculo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipovehiculo.getIdTipo();
                if (findTipovehiculo(id) == null) {
                    throw new NonexistentEntityException("The tipovehiculo with id " + id + " no longer exists.");
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
            Tipovehiculo tipovehiculo;
            try {
                tipovehiculo = em.getReference(Tipovehiculo.class, id);
                tipovehiculo.getIdTipo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipovehiculo with id " + id + " no longer exists.", enfe);
            }
            List<Vehiculo> vehiculoList = tipovehiculo.getVehiculoList();
            for (Vehiculo vehiculoListVehiculo : vehiculoList) {
                vehiculoListVehiculo.setIdTipoFk(null);
                vehiculoListVehiculo = em.merge(vehiculoListVehiculo);
            }
            em.remove(tipovehiculo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipovehiculo> findTipovehiculoEntities() {
        return findTipovehiculoEntities(true, -1, -1);
    }

    public List<Tipovehiculo> findTipovehiculoEntities(int maxResults, int firstResult) {
        return findTipovehiculoEntities(false, maxResults, firstResult);
    }

    private List<Tipovehiculo> findTipovehiculoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipovehiculo.class));
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

    public Tipovehiculo findTipovehiculo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipovehiculo.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipovehiculoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipovehiculo> rt = cq.from(Tipovehiculo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
