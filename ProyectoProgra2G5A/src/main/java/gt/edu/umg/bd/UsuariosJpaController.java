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
public class UsuariosJpaController implements Serializable {

    public UsuariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuarios usuarios) {
        if (usuarios.getVentasList() == null) {
            usuarios.setVentasList(new ArrayList<Ventas>());
        }
        if (usuarios.getComprasList() == null) {
            usuarios.setComprasList(new ArrayList<Compras>());
        }
        if (usuarios.getBitacoraAccesoList() == null) {
            usuarios.setBitacoraAccesoList(new ArrayList<BitacoraAcceso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Roles rolId = usuarios.getRolId();
            if (rolId != null) {
                rolId = em.getReference(rolId.getClass(), rolId.getRolId());
                usuarios.setRolId(rolId);
            }
            List<Ventas> attachedVentasList = new ArrayList<Ventas>();
            for (Ventas ventasListVentasToAttach : usuarios.getVentasList()) {
                ventasListVentasToAttach = em.getReference(ventasListVentasToAttach.getClass(), ventasListVentasToAttach.getVentaId());
                attachedVentasList.add(ventasListVentasToAttach);
            }
            usuarios.setVentasList(attachedVentasList);
            List<Compras> attachedComprasList = new ArrayList<Compras>();
            for (Compras comprasListComprasToAttach : usuarios.getComprasList()) {
                comprasListComprasToAttach = em.getReference(comprasListComprasToAttach.getClass(), comprasListComprasToAttach.getCompraId());
                attachedComprasList.add(comprasListComprasToAttach);
            }
            usuarios.setComprasList(attachedComprasList);
            List<BitacoraAcceso> attachedBitacoraAccesoList = new ArrayList<BitacoraAcceso>();
            for (BitacoraAcceso bitacoraAccesoListBitacoraAccesoToAttach : usuarios.getBitacoraAccesoList()) {
                bitacoraAccesoListBitacoraAccesoToAttach = em.getReference(bitacoraAccesoListBitacoraAccesoToAttach.getClass(), bitacoraAccesoListBitacoraAccesoToAttach.getBitacoraId());
                attachedBitacoraAccesoList.add(bitacoraAccesoListBitacoraAccesoToAttach);
            }
            usuarios.setBitacoraAccesoList(attachedBitacoraAccesoList);
            em.persist(usuarios);
            if (rolId != null) {
                rolId.getUsuariosList().add(usuarios);
                rolId = em.merge(rolId);
            }
            for (Ventas ventasListVentas : usuarios.getVentasList()) {
                Usuarios oldUsuarioIdOfVentasListVentas = ventasListVentas.getUsuarioId();
                ventasListVentas.setUsuarioId(usuarios);
                ventasListVentas = em.merge(ventasListVentas);
                if (oldUsuarioIdOfVentasListVentas != null) {
                    oldUsuarioIdOfVentasListVentas.getVentasList().remove(ventasListVentas);
                    oldUsuarioIdOfVentasListVentas = em.merge(oldUsuarioIdOfVentasListVentas);
                }
            }
            for (Compras comprasListCompras : usuarios.getComprasList()) {
                Usuarios oldUsuarioIdOfComprasListCompras = comprasListCompras.getUsuarioId();
                comprasListCompras.setUsuarioId(usuarios);
                comprasListCompras = em.merge(comprasListCompras);
                if (oldUsuarioIdOfComprasListCompras != null) {
                    oldUsuarioIdOfComprasListCompras.getComprasList().remove(comprasListCompras);
                    oldUsuarioIdOfComprasListCompras = em.merge(oldUsuarioIdOfComprasListCompras);
                }
            }
            for (BitacoraAcceso bitacoraAccesoListBitacoraAcceso : usuarios.getBitacoraAccesoList()) {
                Usuarios oldUsuarioIdOfBitacoraAccesoListBitacoraAcceso = bitacoraAccesoListBitacoraAcceso.getUsuarioId();
                bitacoraAccesoListBitacoraAcceso.setUsuarioId(usuarios);
                bitacoraAccesoListBitacoraAcceso = em.merge(bitacoraAccesoListBitacoraAcceso);
                if (oldUsuarioIdOfBitacoraAccesoListBitacoraAcceso != null) {
                    oldUsuarioIdOfBitacoraAccesoListBitacoraAcceso.getBitacoraAccesoList().remove(bitacoraAccesoListBitacoraAcceso);
                    oldUsuarioIdOfBitacoraAccesoListBitacoraAcceso = em.merge(oldUsuarioIdOfBitacoraAccesoListBitacoraAcceso);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuarios usuarios) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios persistentUsuarios = em.find(Usuarios.class, usuarios.getUsuarioId());
            Roles rolIdOld = persistentUsuarios.getRolId();
            Roles rolIdNew = usuarios.getRolId();
            List<Ventas> ventasListOld = persistentUsuarios.getVentasList();
            List<Ventas> ventasListNew = usuarios.getVentasList();
            List<Compras> comprasListOld = persistentUsuarios.getComprasList();
            List<Compras> comprasListNew = usuarios.getComprasList();
            List<BitacoraAcceso> bitacoraAccesoListOld = persistentUsuarios.getBitacoraAccesoList();
            List<BitacoraAcceso> bitacoraAccesoListNew = usuarios.getBitacoraAccesoList();
            if (rolIdNew != null) {
                rolIdNew = em.getReference(rolIdNew.getClass(), rolIdNew.getRolId());
                usuarios.setRolId(rolIdNew);
            }
            List<Ventas> attachedVentasListNew = new ArrayList<Ventas>();
            for (Ventas ventasListNewVentasToAttach : ventasListNew) {
                ventasListNewVentasToAttach = em.getReference(ventasListNewVentasToAttach.getClass(), ventasListNewVentasToAttach.getVentaId());
                attachedVentasListNew.add(ventasListNewVentasToAttach);
            }
            ventasListNew = attachedVentasListNew;
            usuarios.setVentasList(ventasListNew);
            List<Compras> attachedComprasListNew = new ArrayList<Compras>();
            for (Compras comprasListNewComprasToAttach : comprasListNew) {
                comprasListNewComprasToAttach = em.getReference(comprasListNewComprasToAttach.getClass(), comprasListNewComprasToAttach.getCompraId());
                attachedComprasListNew.add(comprasListNewComprasToAttach);
            }
            comprasListNew = attachedComprasListNew;
            usuarios.setComprasList(comprasListNew);
            List<BitacoraAcceso> attachedBitacoraAccesoListNew = new ArrayList<BitacoraAcceso>();
            for (BitacoraAcceso bitacoraAccesoListNewBitacoraAccesoToAttach : bitacoraAccesoListNew) {
                bitacoraAccesoListNewBitacoraAccesoToAttach = em.getReference(bitacoraAccesoListNewBitacoraAccesoToAttach.getClass(), bitacoraAccesoListNewBitacoraAccesoToAttach.getBitacoraId());
                attachedBitacoraAccesoListNew.add(bitacoraAccesoListNewBitacoraAccesoToAttach);
            }
            bitacoraAccesoListNew = attachedBitacoraAccesoListNew;
            usuarios.setBitacoraAccesoList(bitacoraAccesoListNew);
            usuarios = em.merge(usuarios);
            if (rolIdOld != null && !rolIdOld.equals(rolIdNew)) {
                rolIdOld.getUsuariosList().remove(usuarios);
                rolIdOld = em.merge(rolIdOld);
            }
            if (rolIdNew != null && !rolIdNew.equals(rolIdOld)) {
                rolIdNew.getUsuariosList().add(usuarios);
                rolIdNew = em.merge(rolIdNew);
            }
            for (Ventas ventasListOldVentas : ventasListOld) {
                if (!ventasListNew.contains(ventasListOldVentas)) {
                    ventasListOldVentas.setUsuarioId(null);
                    ventasListOldVentas = em.merge(ventasListOldVentas);
                }
            }
            for (Ventas ventasListNewVentas : ventasListNew) {
                if (!ventasListOld.contains(ventasListNewVentas)) {
                    Usuarios oldUsuarioIdOfVentasListNewVentas = ventasListNewVentas.getUsuarioId();
                    ventasListNewVentas.setUsuarioId(usuarios);
                    ventasListNewVentas = em.merge(ventasListNewVentas);
                    if (oldUsuarioIdOfVentasListNewVentas != null && !oldUsuarioIdOfVentasListNewVentas.equals(usuarios)) {
                        oldUsuarioIdOfVentasListNewVentas.getVentasList().remove(ventasListNewVentas);
                        oldUsuarioIdOfVentasListNewVentas = em.merge(oldUsuarioIdOfVentasListNewVentas);
                    }
                }
            }
            for (Compras comprasListOldCompras : comprasListOld) {
                if (!comprasListNew.contains(comprasListOldCompras)) {
                    comprasListOldCompras.setUsuarioId(null);
                    comprasListOldCompras = em.merge(comprasListOldCompras);
                }
            }
            for (Compras comprasListNewCompras : comprasListNew) {
                if (!comprasListOld.contains(comprasListNewCompras)) {
                    Usuarios oldUsuarioIdOfComprasListNewCompras = comprasListNewCompras.getUsuarioId();
                    comprasListNewCompras.setUsuarioId(usuarios);
                    comprasListNewCompras = em.merge(comprasListNewCompras);
                    if (oldUsuarioIdOfComprasListNewCompras != null && !oldUsuarioIdOfComprasListNewCompras.equals(usuarios)) {
                        oldUsuarioIdOfComprasListNewCompras.getComprasList().remove(comprasListNewCompras);
                        oldUsuarioIdOfComprasListNewCompras = em.merge(oldUsuarioIdOfComprasListNewCompras);
                    }
                }
            }
            for (BitacoraAcceso bitacoraAccesoListOldBitacoraAcceso : bitacoraAccesoListOld) {
                if (!bitacoraAccesoListNew.contains(bitacoraAccesoListOldBitacoraAcceso)) {
                    bitacoraAccesoListOldBitacoraAcceso.setUsuarioId(null);
                    bitacoraAccesoListOldBitacoraAcceso = em.merge(bitacoraAccesoListOldBitacoraAcceso);
                }
            }
            for (BitacoraAcceso bitacoraAccesoListNewBitacoraAcceso : bitacoraAccesoListNew) {
                if (!bitacoraAccesoListOld.contains(bitacoraAccesoListNewBitacoraAcceso)) {
                    Usuarios oldUsuarioIdOfBitacoraAccesoListNewBitacoraAcceso = bitacoraAccesoListNewBitacoraAcceso.getUsuarioId();
                    bitacoraAccesoListNewBitacoraAcceso.setUsuarioId(usuarios);
                    bitacoraAccesoListNewBitacoraAcceso = em.merge(bitacoraAccesoListNewBitacoraAcceso);
                    if (oldUsuarioIdOfBitacoraAccesoListNewBitacoraAcceso != null && !oldUsuarioIdOfBitacoraAccesoListNewBitacoraAcceso.equals(usuarios)) {
                        oldUsuarioIdOfBitacoraAccesoListNewBitacoraAcceso.getBitacoraAccesoList().remove(bitacoraAccesoListNewBitacoraAcceso);
                        oldUsuarioIdOfBitacoraAccesoListNewBitacoraAcceso = em.merge(oldUsuarioIdOfBitacoraAccesoListNewBitacoraAcceso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuarios.getUsuarioId();
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
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
            Usuarios usuarios;
            try {
                usuarios = em.getReference(Usuarios.class, id);
                usuarios.getUsuarioId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.", enfe);
            }
            Roles rolId = usuarios.getRolId();
            if (rolId != null) {
                rolId.getUsuariosList().remove(usuarios);
                rolId = em.merge(rolId);
            }
            List<Ventas> ventasList = usuarios.getVentasList();
            for (Ventas ventasListVentas : ventasList) {
                ventasListVentas.setUsuarioId(null);
                ventasListVentas = em.merge(ventasListVentas);
            }
            List<Compras> comprasList = usuarios.getComprasList();
            for (Compras comprasListCompras : comprasList) {
                comprasListCompras.setUsuarioId(null);
                comprasListCompras = em.merge(comprasListCompras);
            }
            List<BitacoraAcceso> bitacoraAccesoList = usuarios.getBitacoraAccesoList();
            for (BitacoraAcceso bitacoraAccesoListBitacoraAcceso : bitacoraAccesoList) {
                bitacoraAccesoListBitacoraAcceso.setUsuarioId(null);
                bitacoraAccesoListBitacoraAcceso = em.merge(bitacoraAccesoListBitacoraAcceso);
            }
            em.remove(usuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
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

    public Usuarios findUsuarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
