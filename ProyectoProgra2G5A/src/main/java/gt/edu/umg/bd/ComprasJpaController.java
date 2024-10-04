/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.bd;

import gt.edu.umg.bd.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Marlon Cuco
 */
public class ComprasJpaController implements Serializable {

    public ComprasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Compras compras) {
        if (compras.getDetalleCompraList() == null) {
            compras.setDetalleCompraList(new ArrayList<DetalleCompra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios usuarioId = compras.getUsuarioId();
            if (usuarioId != null) {
                usuarioId = em.getReference(usuarioId.getClass(), usuarioId.getUsuarioId());
                compras.setUsuarioId(usuarioId);
            }
            List<DetalleCompra> attachedDetalleCompraList = new ArrayList<DetalleCompra>();
            for (DetalleCompra detalleCompraListDetalleCompraToAttach : compras.getDetalleCompraList()) {
                detalleCompraListDetalleCompraToAttach = em.getReference(detalleCompraListDetalleCompraToAttach.getClass(), detalleCompraListDetalleCompraToAttach.getDetalleCompraId());
                attachedDetalleCompraList.add(detalleCompraListDetalleCompraToAttach);
            }
            compras.setDetalleCompraList(attachedDetalleCompraList);
            em.persist(compras);
            if (usuarioId != null) {
                usuarioId.getComprasList().add(compras);
                usuarioId = em.merge(usuarioId);
            }
            for (DetalleCompra detalleCompraListDetalleCompra : compras.getDetalleCompraList()) {
                Compras oldCompraIdOfDetalleCompraListDetalleCompra = detalleCompraListDetalleCompra.getCompraId();
                detalleCompraListDetalleCompra.setCompraId(compras);
                detalleCompraListDetalleCompra = em.merge(detalleCompraListDetalleCompra);
                if (oldCompraIdOfDetalleCompraListDetalleCompra != null) {
                    oldCompraIdOfDetalleCompraListDetalleCompra.getDetalleCompraList().remove(detalleCompraListDetalleCompra);
                    oldCompraIdOfDetalleCompraListDetalleCompra = em.merge(oldCompraIdOfDetalleCompraListDetalleCompra);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Compras compras) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Compras persistentCompras = em.find(Compras.class, compras.getCompraId());
            Usuarios usuarioIdOld = persistentCompras.getUsuarioId();
            Usuarios usuarioIdNew = compras.getUsuarioId();
            List<DetalleCompra> detalleCompraListOld = persistentCompras.getDetalleCompraList();
            List<DetalleCompra> detalleCompraListNew = compras.getDetalleCompraList();
            if (usuarioIdNew != null) {
                usuarioIdNew = em.getReference(usuarioIdNew.getClass(), usuarioIdNew.getUsuarioId());
                compras.setUsuarioId(usuarioIdNew);
            }
            List<DetalleCompra> attachedDetalleCompraListNew = new ArrayList<DetalleCompra>();
            for (DetalleCompra detalleCompraListNewDetalleCompraToAttach : detalleCompraListNew) {
                detalleCompraListNewDetalleCompraToAttach = em.getReference(detalleCompraListNewDetalleCompraToAttach.getClass(), detalleCompraListNewDetalleCompraToAttach.getDetalleCompraId());
                attachedDetalleCompraListNew.add(detalleCompraListNewDetalleCompraToAttach);
            }
            detalleCompraListNew = attachedDetalleCompraListNew;
            compras.setDetalleCompraList(detalleCompraListNew);
            compras = em.merge(compras);
            if (usuarioIdOld != null && !usuarioIdOld.equals(usuarioIdNew)) {
                usuarioIdOld.getComprasList().remove(compras);
                usuarioIdOld = em.merge(usuarioIdOld);
            }
            if (usuarioIdNew != null && !usuarioIdNew.equals(usuarioIdOld)) {
                usuarioIdNew.getComprasList().add(compras);
                usuarioIdNew = em.merge(usuarioIdNew);
            }
            for (DetalleCompra detalleCompraListOldDetalleCompra : detalleCompraListOld) {
                if (!detalleCompraListNew.contains(detalleCompraListOldDetalleCompra)) {
                    detalleCompraListOldDetalleCompra.setCompraId(null);
                    detalleCompraListOldDetalleCompra = em.merge(detalleCompraListOldDetalleCompra);
                }
            }
            for (DetalleCompra detalleCompraListNewDetalleCompra : detalleCompraListNew) {
                if (!detalleCompraListOld.contains(detalleCompraListNewDetalleCompra)) {
                    Compras oldCompraIdOfDetalleCompraListNewDetalleCompra = detalleCompraListNewDetalleCompra.getCompraId();
                    detalleCompraListNewDetalleCompra.setCompraId(compras);
                    detalleCompraListNewDetalleCompra = em.merge(detalleCompraListNewDetalleCompra);
                    if (oldCompraIdOfDetalleCompraListNewDetalleCompra != null && !oldCompraIdOfDetalleCompraListNewDetalleCompra.equals(compras)) {
                        oldCompraIdOfDetalleCompraListNewDetalleCompra.getDetalleCompraList().remove(detalleCompraListNewDetalleCompra);
                        oldCompraIdOfDetalleCompraListNewDetalleCompra = em.merge(oldCompraIdOfDetalleCompraListNewDetalleCompra);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = compras.getCompraId();
                if (findCompras(id) == null) {
                    throw new NonexistentEntityException("The compras with id " + id + " no longer exists.");
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
            Compras compras;
            try {
                compras = em.getReference(Compras.class, id);
                compras.getCompraId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The compras with id " + id + " no longer exists.", enfe);
            }
            Usuarios usuarioId = compras.getUsuarioId();
            if (usuarioId != null) {
                usuarioId.getComprasList().remove(compras);
                usuarioId = em.merge(usuarioId);
            }
            List<DetalleCompra> detalleCompraList = compras.getDetalleCompraList();
            for (DetalleCompra detalleCompraListDetalleCompra : detalleCompraList) {
                detalleCompraListDetalleCompra.setCompraId(null);
                detalleCompraListDetalleCompra = em.merge(detalleCompraListDetalleCompra);
            }
            em.remove(compras);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Compras> findComprasEntities() {
        return findComprasEntities(true, -1, -1);
    }

    public List<Compras> findComprasEntities(int maxResults, int firstResult) {
        return findComprasEntities(false, maxResults, firstResult);
    }

    private List<Compras> findComprasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Compras.class));
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

    public Compras findCompras(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Compras.class, id);
        } finally {
            em.close();
        }
    }

    public int getComprasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Compras> rt = cq.from(Compras.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
