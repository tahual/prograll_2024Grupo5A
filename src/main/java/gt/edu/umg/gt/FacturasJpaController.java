/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.gt;

import gt.edu.umg.gt.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Marlon Cuco
 */
public class FacturasJpaController implements Serializable {

    public FacturasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Facturas facturas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ventas ventaId = facturas.getVentaId();
            if (ventaId != null) {
                ventaId = em.getReference(ventaId.getClass(), ventaId.getVentaId());
                facturas.setVentaId(ventaId);
            }
            em.persist(facturas);
            if (ventaId != null) {
                ventaId.getFacturasList().add(facturas);
                ventaId = em.merge(ventaId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Facturas facturas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Facturas persistentFacturas = em.find(Facturas.class, facturas.getFacturaId());
            Ventas ventaIdOld = persistentFacturas.getVentaId();
            Ventas ventaIdNew = facturas.getVentaId();
            if (ventaIdNew != null) {
                ventaIdNew = em.getReference(ventaIdNew.getClass(), ventaIdNew.getVentaId());
                facturas.setVentaId(ventaIdNew);
            }
            facturas = em.merge(facturas);
            if (ventaIdOld != null && !ventaIdOld.equals(ventaIdNew)) {
                ventaIdOld.getFacturasList().remove(facturas);
                ventaIdOld = em.merge(ventaIdOld);
            }
            if (ventaIdNew != null && !ventaIdNew.equals(ventaIdOld)) {
                ventaIdNew.getFacturasList().add(facturas);
                ventaIdNew = em.merge(ventaIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = facturas.getFacturaId();
                if (findFacturas(id) == null) {
                    throw new NonexistentEntityException("The facturas with id " + id + " no longer exists.");
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
            Facturas facturas;
            try {
                facturas = em.getReference(Facturas.class, id);
                facturas.getFacturaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The facturas with id " + id + " no longer exists.", enfe);
            }
            Ventas ventaId = facturas.getVentaId();
            if (ventaId != null) {
                ventaId.getFacturasList().remove(facturas);
                ventaId = em.merge(ventaId);
            }
            em.remove(facturas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Facturas> findFacturasEntities() {
        return findFacturasEntities(true, -1, -1);
    }

    public List<Facturas> findFacturasEntities(int maxResults, int firstResult) {
        return findFacturasEntities(false, maxResults, firstResult);
    }

    private List<Facturas> findFacturasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Facturas.class));
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

    public Facturas findFacturas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Facturas.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Facturas> rt = cq.from(Facturas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
