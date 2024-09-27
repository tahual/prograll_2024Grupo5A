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
public class DetalleVentaJpaController implements Serializable {

    public DetalleVentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleVenta detalleVenta) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Productos productoId = detalleVenta.getProductoId();
            if (productoId != null) {
                productoId = em.getReference(productoId.getClass(), productoId.getProductoId());
                detalleVenta.setProductoId(productoId);
            }
            Ventas ventaId = detalleVenta.getVentaId();
            if (ventaId != null) {
                ventaId = em.getReference(ventaId.getClass(), ventaId.getVentaId());
                detalleVenta.setVentaId(ventaId);
            }
            em.persist(detalleVenta);
            if (productoId != null) {
                productoId.getDetalleVentaList().add(detalleVenta);
                productoId = em.merge(productoId);
            }
            if (ventaId != null) {
                ventaId.getDetalleVentaList().add(detalleVenta);
                ventaId = em.merge(ventaId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleVenta detalleVenta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleVenta persistentDetalleVenta = em.find(DetalleVenta.class, detalleVenta.getDetalleVentaId());
            Productos productoIdOld = persistentDetalleVenta.getProductoId();
            Productos productoIdNew = detalleVenta.getProductoId();
            Ventas ventaIdOld = persistentDetalleVenta.getVentaId();
            Ventas ventaIdNew = detalleVenta.getVentaId();
            if (productoIdNew != null) {
                productoIdNew = em.getReference(productoIdNew.getClass(), productoIdNew.getProductoId());
                detalleVenta.setProductoId(productoIdNew);
            }
            if (ventaIdNew != null) {
                ventaIdNew = em.getReference(ventaIdNew.getClass(), ventaIdNew.getVentaId());
                detalleVenta.setVentaId(ventaIdNew);
            }
            detalleVenta = em.merge(detalleVenta);
            if (productoIdOld != null && !productoIdOld.equals(productoIdNew)) {
                productoIdOld.getDetalleVentaList().remove(detalleVenta);
                productoIdOld = em.merge(productoIdOld);
            }
            if (productoIdNew != null && !productoIdNew.equals(productoIdOld)) {
                productoIdNew.getDetalleVentaList().add(detalleVenta);
                productoIdNew = em.merge(productoIdNew);
            }
            if (ventaIdOld != null && !ventaIdOld.equals(ventaIdNew)) {
                ventaIdOld.getDetalleVentaList().remove(detalleVenta);
                ventaIdOld = em.merge(ventaIdOld);
            }
            if (ventaIdNew != null && !ventaIdNew.equals(ventaIdOld)) {
                ventaIdNew.getDetalleVentaList().add(detalleVenta);
                ventaIdNew = em.merge(ventaIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleVenta.getDetalleVentaId();
                if (findDetalleVenta(id) == null) {
                    throw new NonexistentEntityException("The detalleVenta with id " + id + " no longer exists.");
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
            DetalleVenta detalleVenta;
            try {
                detalleVenta = em.getReference(DetalleVenta.class, id);
                detalleVenta.getDetalleVentaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleVenta with id " + id + " no longer exists.", enfe);
            }
            Productos productoId = detalleVenta.getProductoId();
            if (productoId != null) {
                productoId.getDetalleVentaList().remove(detalleVenta);
                productoId = em.merge(productoId);
            }
            Ventas ventaId = detalleVenta.getVentaId();
            if (ventaId != null) {
                ventaId.getDetalleVentaList().remove(detalleVenta);
                ventaId = em.merge(ventaId);
            }
            em.remove(detalleVenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleVenta> findDetalleVentaEntities() {
        return findDetalleVentaEntities(true, -1, -1);
    }

    public List<DetalleVenta> findDetalleVentaEntities(int maxResults, int firstResult) {
        return findDetalleVentaEntities(false, maxResults, firstResult);
    }

    private List<DetalleVenta> findDetalleVentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleVenta.class));
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

    public DetalleVenta findDetalleVenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleVenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleVentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleVenta> rt = cq.from(DetalleVenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
