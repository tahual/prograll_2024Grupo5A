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
public class ProductosJpaController implements Serializable {

    public ProductosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Productos productos) {
        if (productos.getDetalleVentaList() == null) {
            productos.setDetalleVentaList(new ArrayList<DetalleVenta>());
        }
        if (productos.getDetalleCompraList() == null) {
            productos.setDetalleCompraList(new ArrayList<DetalleCompra>());
        }
        if (productos.getInventarioList() == null) {
            productos.setInventarioList(new ArrayList<Inventario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<DetalleVenta> attachedDetalleVentaList = new ArrayList<DetalleVenta>();
            for (DetalleVenta detalleVentaListDetalleVentaToAttach : productos.getDetalleVentaList()) {
                detalleVentaListDetalleVentaToAttach = em.getReference(detalleVentaListDetalleVentaToAttach.getClass(), detalleVentaListDetalleVentaToAttach.getDetalleVentaId());
                attachedDetalleVentaList.add(detalleVentaListDetalleVentaToAttach);
            }
            productos.setDetalleVentaList(attachedDetalleVentaList);
            List<DetalleCompra> attachedDetalleCompraList = new ArrayList<DetalleCompra>();
            for (DetalleCompra detalleCompraListDetalleCompraToAttach : productos.getDetalleCompraList()) {
                detalleCompraListDetalleCompraToAttach = em.getReference(detalleCompraListDetalleCompraToAttach.getClass(), detalleCompraListDetalleCompraToAttach.getDetalleCompraId());
                attachedDetalleCompraList.add(detalleCompraListDetalleCompraToAttach);
            }
            productos.setDetalleCompraList(attachedDetalleCompraList);
            List<Inventario> attachedInventarioList = new ArrayList<Inventario>();
            for (Inventario inventarioListInventarioToAttach : productos.getInventarioList()) {
                inventarioListInventarioToAttach = em.getReference(inventarioListInventarioToAttach.getClass(), inventarioListInventarioToAttach.getInventarioId());
                attachedInventarioList.add(inventarioListInventarioToAttach);
            }
            productos.setInventarioList(attachedInventarioList);
            em.persist(productos);
            for (DetalleVenta detalleVentaListDetalleVenta : productos.getDetalleVentaList()) {
                Productos oldProductoIdOfDetalleVentaListDetalleVenta = detalleVentaListDetalleVenta.getProductoId();
                detalleVentaListDetalleVenta.setProductoId(productos);
                detalleVentaListDetalleVenta = em.merge(detalleVentaListDetalleVenta);
                if (oldProductoIdOfDetalleVentaListDetalleVenta != null) {
                    oldProductoIdOfDetalleVentaListDetalleVenta.getDetalleVentaList().remove(detalleVentaListDetalleVenta);
                    oldProductoIdOfDetalleVentaListDetalleVenta = em.merge(oldProductoIdOfDetalleVentaListDetalleVenta);
                }
            }
            for (DetalleCompra detalleCompraListDetalleCompra : productos.getDetalleCompraList()) {
                Productos oldProductoIdOfDetalleCompraListDetalleCompra = detalleCompraListDetalleCompra.getProductoId();
                detalleCompraListDetalleCompra.setProductoId(productos);
                detalleCompraListDetalleCompra = em.merge(detalleCompraListDetalleCompra);
                if (oldProductoIdOfDetalleCompraListDetalleCompra != null) {
                    oldProductoIdOfDetalleCompraListDetalleCompra.getDetalleCompraList().remove(detalleCompraListDetalleCompra);
                    oldProductoIdOfDetalleCompraListDetalleCompra = em.merge(oldProductoIdOfDetalleCompraListDetalleCompra);
                }
            }
            for (Inventario inventarioListInventario : productos.getInventarioList()) {
                Productos oldProductoIdOfInventarioListInventario = inventarioListInventario.getProductoId();
                inventarioListInventario.setProductoId(productos);
                inventarioListInventario = em.merge(inventarioListInventario);
                if (oldProductoIdOfInventarioListInventario != null) {
                    oldProductoIdOfInventarioListInventario.getInventarioList().remove(inventarioListInventario);
                    oldProductoIdOfInventarioListInventario = em.merge(oldProductoIdOfInventarioListInventario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Productos productos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Productos persistentProductos = em.find(Productos.class, productos.getProductoId());
            List<DetalleVenta> detalleVentaListOld = persistentProductos.getDetalleVentaList();
            List<DetalleVenta> detalleVentaListNew = productos.getDetalleVentaList();
            List<DetalleCompra> detalleCompraListOld = persistentProductos.getDetalleCompraList();
            List<DetalleCompra> detalleCompraListNew = productos.getDetalleCompraList();
            List<Inventario> inventarioListOld = persistentProductos.getInventarioList();
            List<Inventario> inventarioListNew = productos.getInventarioList();
            List<DetalleVenta> attachedDetalleVentaListNew = new ArrayList<DetalleVenta>();
            for (DetalleVenta detalleVentaListNewDetalleVentaToAttach : detalleVentaListNew) {
                detalleVentaListNewDetalleVentaToAttach = em.getReference(detalleVentaListNewDetalleVentaToAttach.getClass(), detalleVentaListNewDetalleVentaToAttach.getDetalleVentaId());
                attachedDetalleVentaListNew.add(detalleVentaListNewDetalleVentaToAttach);
            }
            detalleVentaListNew = attachedDetalleVentaListNew;
            productos.setDetalleVentaList(detalleVentaListNew);
            List<DetalleCompra> attachedDetalleCompraListNew = new ArrayList<DetalleCompra>();
            for (DetalleCompra detalleCompraListNewDetalleCompraToAttach : detalleCompraListNew) {
                detalleCompraListNewDetalleCompraToAttach = em.getReference(detalleCompraListNewDetalleCompraToAttach.getClass(), detalleCompraListNewDetalleCompraToAttach.getDetalleCompraId());
                attachedDetalleCompraListNew.add(detalleCompraListNewDetalleCompraToAttach);
            }
            detalleCompraListNew = attachedDetalleCompraListNew;
            productos.setDetalleCompraList(detalleCompraListNew);
            List<Inventario> attachedInventarioListNew = new ArrayList<Inventario>();
            for (Inventario inventarioListNewInventarioToAttach : inventarioListNew) {
                inventarioListNewInventarioToAttach = em.getReference(inventarioListNewInventarioToAttach.getClass(), inventarioListNewInventarioToAttach.getInventarioId());
                attachedInventarioListNew.add(inventarioListNewInventarioToAttach);
            }
            inventarioListNew = attachedInventarioListNew;
            productos.setInventarioList(inventarioListNew);
            productos = em.merge(productos);
            for (DetalleVenta detalleVentaListOldDetalleVenta : detalleVentaListOld) {
                if (!detalleVentaListNew.contains(detalleVentaListOldDetalleVenta)) {
                    detalleVentaListOldDetalleVenta.setProductoId(null);
                    detalleVentaListOldDetalleVenta = em.merge(detalleVentaListOldDetalleVenta);
                }
            }
            for (DetalleVenta detalleVentaListNewDetalleVenta : detalleVentaListNew) {
                if (!detalleVentaListOld.contains(detalleVentaListNewDetalleVenta)) {
                    Productos oldProductoIdOfDetalleVentaListNewDetalleVenta = detalleVentaListNewDetalleVenta.getProductoId();
                    detalleVentaListNewDetalleVenta.setProductoId(productos);
                    detalleVentaListNewDetalleVenta = em.merge(detalleVentaListNewDetalleVenta);
                    if (oldProductoIdOfDetalleVentaListNewDetalleVenta != null && !oldProductoIdOfDetalleVentaListNewDetalleVenta.equals(productos)) {
                        oldProductoIdOfDetalleVentaListNewDetalleVenta.getDetalleVentaList().remove(detalleVentaListNewDetalleVenta);
                        oldProductoIdOfDetalleVentaListNewDetalleVenta = em.merge(oldProductoIdOfDetalleVentaListNewDetalleVenta);
                    }
                }
            }
            for (DetalleCompra detalleCompraListOldDetalleCompra : detalleCompraListOld) {
                if (!detalleCompraListNew.contains(detalleCompraListOldDetalleCompra)) {
                    detalleCompraListOldDetalleCompra.setProductoId(null);
                    detalleCompraListOldDetalleCompra = em.merge(detalleCompraListOldDetalleCompra);
                }
            }
            for (DetalleCompra detalleCompraListNewDetalleCompra : detalleCompraListNew) {
                if (!detalleCompraListOld.contains(detalleCompraListNewDetalleCompra)) {
                    Productos oldProductoIdOfDetalleCompraListNewDetalleCompra = detalleCompraListNewDetalleCompra.getProductoId();
                    detalleCompraListNewDetalleCompra.setProductoId(productos);
                    detalleCompraListNewDetalleCompra = em.merge(detalleCompraListNewDetalleCompra);
                    if (oldProductoIdOfDetalleCompraListNewDetalleCompra != null && !oldProductoIdOfDetalleCompraListNewDetalleCompra.equals(productos)) {
                        oldProductoIdOfDetalleCompraListNewDetalleCompra.getDetalleCompraList().remove(detalleCompraListNewDetalleCompra);
                        oldProductoIdOfDetalleCompraListNewDetalleCompra = em.merge(oldProductoIdOfDetalleCompraListNewDetalleCompra);
                    }
                }
            }
            for (Inventario inventarioListOldInventario : inventarioListOld) {
                if (!inventarioListNew.contains(inventarioListOldInventario)) {
                    inventarioListOldInventario.setProductoId(null);
                    inventarioListOldInventario = em.merge(inventarioListOldInventario);
                }
            }
            for (Inventario inventarioListNewInventario : inventarioListNew) {
                if (!inventarioListOld.contains(inventarioListNewInventario)) {
                    Productos oldProductoIdOfInventarioListNewInventario = inventarioListNewInventario.getProductoId();
                    inventarioListNewInventario.setProductoId(productos);
                    inventarioListNewInventario = em.merge(inventarioListNewInventario);
                    if (oldProductoIdOfInventarioListNewInventario != null && !oldProductoIdOfInventarioListNewInventario.equals(productos)) {
                        oldProductoIdOfInventarioListNewInventario.getInventarioList().remove(inventarioListNewInventario);
                        oldProductoIdOfInventarioListNewInventario = em.merge(oldProductoIdOfInventarioListNewInventario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = productos.getProductoId();
                if (findProductos(id) == null) {
                    throw new NonexistentEntityException("The productos with id " + id + " no longer exists.");
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
            Productos productos;
            try {
                productos = em.getReference(Productos.class, id);
                productos.getProductoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productos with id " + id + " no longer exists.", enfe);
            }
            List<DetalleVenta> detalleVentaList = productos.getDetalleVentaList();
            for (DetalleVenta detalleVentaListDetalleVenta : detalleVentaList) {
                detalleVentaListDetalleVenta.setProductoId(null);
                detalleVentaListDetalleVenta = em.merge(detalleVentaListDetalleVenta);
            }
            List<DetalleCompra> detalleCompraList = productos.getDetalleCompraList();
            for (DetalleCompra detalleCompraListDetalleCompra : detalleCompraList) {
                detalleCompraListDetalleCompra.setProductoId(null);
                detalleCompraListDetalleCompra = em.merge(detalleCompraListDetalleCompra);
            }
            List<Inventario> inventarioList = productos.getInventarioList();
            for (Inventario inventarioListInventario : inventarioList) {
                inventarioListInventario.setProductoId(null);
                inventarioListInventario = em.merge(inventarioListInventario);
            }
            em.remove(productos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Productos> findProductosEntities() {
        return findProductosEntities(true, -1, -1);
    }

    public List<Productos> findProductosEntities(int maxResults, int firstResult) {
        return findProductosEntities(false, maxResults, firstResult);
    }

    private List<Productos> findProductosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Productos.class));
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

    public Productos findProductos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Productos.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Productos> rt = cq.from(Productos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
