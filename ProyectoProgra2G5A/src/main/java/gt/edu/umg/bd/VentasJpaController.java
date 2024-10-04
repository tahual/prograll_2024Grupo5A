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
public class VentasJpaController implements Serializable {

    public VentasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ventas ventas) {
        if (ventas.getDetalleVentaList() == null) {
            ventas.setDetalleVentaList(new ArrayList<DetalleVenta>());
        }
        if (ventas.getFacturasList() == null) {
            ventas.setFacturasList(new ArrayList<Facturas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios usuarioId = ventas.getUsuarioId();
            if (usuarioId != null) {
                usuarioId = em.getReference(usuarioId.getClass(), usuarioId.getUsuarioId());
                ventas.setUsuarioId(usuarioId);
            }
            List<DetalleVenta> attachedDetalleVentaList = new ArrayList<DetalleVenta>();
            for (DetalleVenta detalleVentaListDetalleVentaToAttach : ventas.getDetalleVentaList()) {
                detalleVentaListDetalleVentaToAttach = em.getReference(detalleVentaListDetalleVentaToAttach.getClass(), detalleVentaListDetalleVentaToAttach.getDetalleVentaId());
                attachedDetalleVentaList.add(detalleVentaListDetalleVentaToAttach);
            }
            ventas.setDetalleVentaList(attachedDetalleVentaList);
            List<Facturas> attachedFacturasList = new ArrayList<Facturas>();
            for (Facturas facturasListFacturasToAttach : ventas.getFacturasList()) {
                facturasListFacturasToAttach = em.getReference(facturasListFacturasToAttach.getClass(), facturasListFacturasToAttach.getFacturaId());
                attachedFacturasList.add(facturasListFacturasToAttach);
            }
            ventas.setFacturasList(attachedFacturasList);
            em.persist(ventas);
            if (usuarioId != null) {
                usuarioId.getVentasList().add(ventas);
                usuarioId = em.merge(usuarioId);
            }
            for (DetalleVenta detalleVentaListDetalleVenta : ventas.getDetalleVentaList()) {
                Ventas oldVentaIdOfDetalleVentaListDetalleVenta = detalleVentaListDetalleVenta.getVentaId();
                detalleVentaListDetalleVenta.setVentaId(ventas);
                detalleVentaListDetalleVenta = em.merge(detalleVentaListDetalleVenta);
                if (oldVentaIdOfDetalleVentaListDetalleVenta != null) {
                    oldVentaIdOfDetalleVentaListDetalleVenta.getDetalleVentaList().remove(detalleVentaListDetalleVenta);
                    oldVentaIdOfDetalleVentaListDetalleVenta = em.merge(oldVentaIdOfDetalleVentaListDetalleVenta);
                }
            }
            for (Facturas facturasListFacturas : ventas.getFacturasList()) {
                Ventas oldVentaIdOfFacturasListFacturas = facturasListFacturas.getVentaId();
                facturasListFacturas.setVentaId(ventas);
                facturasListFacturas = em.merge(facturasListFacturas);
                if (oldVentaIdOfFacturasListFacturas != null) {
                    oldVentaIdOfFacturasListFacturas.getFacturasList().remove(facturasListFacturas);
                    oldVentaIdOfFacturasListFacturas = em.merge(oldVentaIdOfFacturasListFacturas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ventas ventas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ventas persistentVentas = em.find(Ventas.class, ventas.getVentaId());
            Usuarios usuarioIdOld = persistentVentas.getUsuarioId();
            Usuarios usuarioIdNew = ventas.getUsuarioId();
            List<DetalleVenta> detalleVentaListOld = persistentVentas.getDetalleVentaList();
            List<DetalleVenta> detalleVentaListNew = ventas.getDetalleVentaList();
            List<Facturas> facturasListOld = persistentVentas.getFacturasList();
            List<Facturas> facturasListNew = ventas.getFacturasList();
            if (usuarioIdNew != null) {
                usuarioIdNew = em.getReference(usuarioIdNew.getClass(), usuarioIdNew.getUsuarioId());
                ventas.setUsuarioId(usuarioIdNew);
            }
            List<DetalleVenta> attachedDetalleVentaListNew = new ArrayList<DetalleVenta>();
            for (DetalleVenta detalleVentaListNewDetalleVentaToAttach : detalleVentaListNew) {
                detalleVentaListNewDetalleVentaToAttach = em.getReference(detalleVentaListNewDetalleVentaToAttach.getClass(), detalleVentaListNewDetalleVentaToAttach.getDetalleVentaId());
                attachedDetalleVentaListNew.add(detalleVentaListNewDetalleVentaToAttach);
            }
            detalleVentaListNew = attachedDetalleVentaListNew;
            ventas.setDetalleVentaList(detalleVentaListNew);
            List<Facturas> attachedFacturasListNew = new ArrayList<Facturas>();
            for (Facturas facturasListNewFacturasToAttach : facturasListNew) {
                facturasListNewFacturasToAttach = em.getReference(facturasListNewFacturasToAttach.getClass(), facturasListNewFacturasToAttach.getFacturaId());
                attachedFacturasListNew.add(facturasListNewFacturasToAttach);
            }
            facturasListNew = attachedFacturasListNew;
            ventas.setFacturasList(facturasListNew);
            ventas = em.merge(ventas);
            if (usuarioIdOld != null && !usuarioIdOld.equals(usuarioIdNew)) {
                usuarioIdOld.getVentasList().remove(ventas);
                usuarioIdOld = em.merge(usuarioIdOld);
            }
            if (usuarioIdNew != null && !usuarioIdNew.equals(usuarioIdOld)) {
                usuarioIdNew.getVentasList().add(ventas);
                usuarioIdNew = em.merge(usuarioIdNew);
            }
            for (DetalleVenta detalleVentaListOldDetalleVenta : detalleVentaListOld) {
                if (!detalleVentaListNew.contains(detalleVentaListOldDetalleVenta)) {
                    detalleVentaListOldDetalleVenta.setVentaId(null);
                    detalleVentaListOldDetalleVenta = em.merge(detalleVentaListOldDetalleVenta);
                }
            }
            for (DetalleVenta detalleVentaListNewDetalleVenta : detalleVentaListNew) {
                if (!detalleVentaListOld.contains(detalleVentaListNewDetalleVenta)) {
                    Ventas oldVentaIdOfDetalleVentaListNewDetalleVenta = detalleVentaListNewDetalleVenta.getVentaId();
                    detalleVentaListNewDetalleVenta.setVentaId(ventas);
                    detalleVentaListNewDetalleVenta = em.merge(detalleVentaListNewDetalleVenta);
                    if (oldVentaIdOfDetalleVentaListNewDetalleVenta != null && !oldVentaIdOfDetalleVentaListNewDetalleVenta.equals(ventas)) {
                        oldVentaIdOfDetalleVentaListNewDetalleVenta.getDetalleVentaList().remove(detalleVentaListNewDetalleVenta);
                        oldVentaIdOfDetalleVentaListNewDetalleVenta = em.merge(oldVentaIdOfDetalleVentaListNewDetalleVenta);
                    }
                }
            }
            for (Facturas facturasListOldFacturas : facturasListOld) {
                if (!facturasListNew.contains(facturasListOldFacturas)) {
                    facturasListOldFacturas.setVentaId(null);
                    facturasListOldFacturas = em.merge(facturasListOldFacturas);
                }
            }
            for (Facturas facturasListNewFacturas : facturasListNew) {
                if (!facturasListOld.contains(facturasListNewFacturas)) {
                    Ventas oldVentaIdOfFacturasListNewFacturas = facturasListNewFacturas.getVentaId();
                    facturasListNewFacturas.setVentaId(ventas);
                    facturasListNewFacturas = em.merge(facturasListNewFacturas);
                    if (oldVentaIdOfFacturasListNewFacturas != null && !oldVentaIdOfFacturasListNewFacturas.equals(ventas)) {
                        oldVentaIdOfFacturasListNewFacturas.getFacturasList().remove(facturasListNewFacturas);
                        oldVentaIdOfFacturasListNewFacturas = em.merge(oldVentaIdOfFacturasListNewFacturas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ventas.getVentaId();
                if (findVentas(id) == null) {
                    throw new NonexistentEntityException("The ventas with id " + id + " no longer exists.");
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
            Ventas ventas;
            try {
                ventas = em.getReference(Ventas.class, id);
                ventas.getVentaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ventas with id " + id + " no longer exists.", enfe);
            }
            Usuarios usuarioId = ventas.getUsuarioId();
            if (usuarioId != null) {
                usuarioId.getVentasList().remove(ventas);
                usuarioId = em.merge(usuarioId);
            }
            List<DetalleVenta> detalleVentaList = ventas.getDetalleVentaList();
            for (DetalleVenta detalleVentaListDetalleVenta : detalleVentaList) {
                detalleVentaListDetalleVenta.setVentaId(null);
                detalleVentaListDetalleVenta = em.merge(detalleVentaListDetalleVenta);
            }
            List<Facturas> facturasList = ventas.getFacturasList();
            for (Facturas facturasListFacturas : facturasList) {
                facturasListFacturas.setVentaId(null);
                facturasListFacturas = em.merge(facturasListFacturas);
            }
            em.remove(ventas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ventas> findVentasEntities() {
        return findVentasEntities(true, -1, -1);
    }

    public List<Ventas> findVentasEntities(int maxResults, int firstResult) {
        return findVentasEntities(false, maxResults, firstResult);
    }

    private List<Ventas> findVentasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ventas.class));
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

    public Ventas findVentas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ventas.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ventas> rt = cq.from(Ventas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
