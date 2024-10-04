/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.bd;

import gt.edu.umg.bd.exceptions.NonexistentEntityException;
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
public class DetalleCompraJpaController implements Serializable {

    public DetalleCompraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleCompra detalleCompra) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Compras compraId = detalleCompra.getCompraId();
            if (compraId != null) {
                compraId = em.getReference(compraId.getClass(), compraId.getCompraId());
                detalleCompra.setCompraId(compraId);
            }
            Productos productoId = detalleCompra.getProductoId();
            if (productoId != null) {
                productoId = em.getReference(productoId.getClass(), productoId.getProductoId());
                detalleCompra.setProductoId(productoId);
            }
            em.persist(detalleCompra);
            if (compraId != null) {
                compraId.getDetalleCompraList().add(detalleCompra);
                compraId = em.merge(compraId);
            }
            if (productoId != null) {
                productoId.getDetalleCompraList().add(detalleCompra);
                productoId = em.merge(productoId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleCompra detalleCompra) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleCompra persistentDetalleCompra = em.find(DetalleCompra.class, detalleCompra.getDetalleCompraId());
            Compras compraIdOld = persistentDetalleCompra.getCompraId();
            Compras compraIdNew = detalleCompra.getCompraId();
            Productos productoIdOld = persistentDetalleCompra.getProductoId();
            Productos productoIdNew = detalleCompra.getProductoId();
            if (compraIdNew != null) {
                compraIdNew = em.getReference(compraIdNew.getClass(), compraIdNew.getCompraId());
                detalleCompra.setCompraId(compraIdNew);
            }
            if (productoIdNew != null) {
                productoIdNew = em.getReference(productoIdNew.getClass(), productoIdNew.getProductoId());
                detalleCompra.setProductoId(productoIdNew);
            }
            detalleCompra = em.merge(detalleCompra);
            if (compraIdOld != null && !compraIdOld.equals(compraIdNew)) {
                compraIdOld.getDetalleCompraList().remove(detalleCompra);
                compraIdOld = em.merge(compraIdOld);
            }
            if (compraIdNew != null && !compraIdNew.equals(compraIdOld)) {
                compraIdNew.getDetalleCompraList().add(detalleCompra);
                compraIdNew = em.merge(compraIdNew);
            }
            if (productoIdOld != null && !productoIdOld.equals(productoIdNew)) {
                productoIdOld.getDetalleCompraList().remove(detalleCompra);
                productoIdOld = em.merge(productoIdOld);
            }
            if (productoIdNew != null && !productoIdNew.equals(productoIdOld)) {
                productoIdNew.getDetalleCompraList().add(detalleCompra);
                productoIdNew = em.merge(productoIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleCompra.getDetalleCompraId();
                if (findDetalleCompra(id) == null) {
                    throw new NonexistentEntityException("The detalleCompra with id " + id + " no longer exists.");
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
            DetalleCompra detalleCompra;
            try {
                detalleCompra = em.getReference(DetalleCompra.class, id);
                detalleCompra.getDetalleCompraId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleCompra with id " + id + " no longer exists.", enfe);
            }
            Compras compraId = detalleCompra.getCompraId();
            if (compraId != null) {
                compraId.getDetalleCompraList().remove(detalleCompra);
                compraId = em.merge(compraId);
            }
            Productos productoId = detalleCompra.getProductoId();
            if (productoId != null) {
                productoId.getDetalleCompraList().remove(detalleCompra);
                productoId = em.merge(productoId);
            }
            em.remove(detalleCompra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleCompra> findDetalleCompraEntities() {
        return findDetalleCompraEntities(true, -1, -1);
    }

    public List<DetalleCompra> findDetalleCompraEntities(int maxResults, int firstResult) {
        return findDetalleCompraEntities(false, maxResults, firstResult);
    }

    private List<DetalleCompra> findDetalleCompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleCompra.class));
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

    public DetalleCompra findDetalleCompra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleCompra.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleCompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleCompra> rt = cq.from(DetalleCompra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
