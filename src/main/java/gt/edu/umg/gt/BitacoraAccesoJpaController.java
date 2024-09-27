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
public class BitacoraAccesoJpaController implements Serializable {

    public BitacoraAccesoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BitacoraAcceso bitacoraAcceso) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios usuarioId = bitacoraAcceso.getUsuarioId();
            if (usuarioId != null) {
                usuarioId = em.getReference(usuarioId.getClass(), usuarioId.getUsuarioId());
                bitacoraAcceso.setUsuarioId(usuarioId);
            }
            em.persist(bitacoraAcceso);
            if (usuarioId != null) {
                usuarioId.getBitacoraAccesoList().add(bitacoraAcceso);
                usuarioId = em.merge(usuarioId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BitacoraAcceso bitacoraAcceso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BitacoraAcceso persistentBitacoraAcceso = em.find(BitacoraAcceso.class, bitacoraAcceso.getBitacoraId());
            Usuarios usuarioIdOld = persistentBitacoraAcceso.getUsuarioId();
            Usuarios usuarioIdNew = bitacoraAcceso.getUsuarioId();
            if (usuarioIdNew != null) {
                usuarioIdNew = em.getReference(usuarioIdNew.getClass(), usuarioIdNew.getUsuarioId());
                bitacoraAcceso.setUsuarioId(usuarioIdNew);
            }
            bitacoraAcceso = em.merge(bitacoraAcceso);
            if (usuarioIdOld != null && !usuarioIdOld.equals(usuarioIdNew)) {
                usuarioIdOld.getBitacoraAccesoList().remove(bitacoraAcceso);
                usuarioIdOld = em.merge(usuarioIdOld);
            }
            if (usuarioIdNew != null && !usuarioIdNew.equals(usuarioIdOld)) {
                usuarioIdNew.getBitacoraAccesoList().add(bitacoraAcceso);
                usuarioIdNew = em.merge(usuarioIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bitacoraAcceso.getBitacoraId();
                if (findBitacoraAcceso(id) == null) {
                    throw new NonexistentEntityException("The bitacoraAcceso with id " + id + " no longer exists.");
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
            BitacoraAcceso bitacoraAcceso;
            try {
                bitacoraAcceso = em.getReference(BitacoraAcceso.class, id);
                bitacoraAcceso.getBitacoraId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bitacoraAcceso with id " + id + " no longer exists.", enfe);
            }
            Usuarios usuarioId = bitacoraAcceso.getUsuarioId();
            if (usuarioId != null) {
                usuarioId.getBitacoraAccesoList().remove(bitacoraAcceso);
                usuarioId = em.merge(usuarioId);
            }
            em.remove(bitacoraAcceso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BitacoraAcceso> findBitacoraAccesoEntities() {
        return findBitacoraAccesoEntities(true, -1, -1);
    }

    public List<BitacoraAcceso> findBitacoraAccesoEntities(int maxResults, int firstResult) {
        return findBitacoraAccesoEntities(false, maxResults, firstResult);
    }

    private List<BitacoraAcceso> findBitacoraAccesoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BitacoraAcceso.class));
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

    public BitacoraAcceso findBitacoraAcceso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BitacoraAcceso.class, id);
        } finally {
            em.close();
        }
    }

    public int getBitacoraAccesoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BitacoraAcceso> rt = cq.from(BitacoraAcceso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
