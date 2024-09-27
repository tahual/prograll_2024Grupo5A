/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.gt;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Marlon Cuco
 */
@Entity
@Table(name = "Usuarios", catalog = "Distribuidora Agricola", schema = "dbo", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"correo"})})
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u")})
public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "usuario_id", nullable = false)
    private Integer usuarioId;
    @Basic(optional = false)
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    @Basic(optional = false)
    @Column(name = "correo", nullable = false, length = 100)
    private String correo;
    @Basic(optional = false)
    @Lob
    @Column(name = "contrase\u00f1a", nullable = false)
    private byte[] contraseña;
    @OneToMany(mappedBy = "usuarioId", fetch = FetchType.LAZY)
    private List<Ventas> ventasList;
    @OneToMany(mappedBy = "usuarioId", fetch = FetchType.LAZY)
    private List<Compras> comprasList;
    @JoinColumn(name = "rol_id", referencedColumnName = "rol_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Roles rolId;
    @OneToMany(mappedBy = "usuarioId", fetch = FetchType.LAZY)
    private List<BitacoraAcceso> bitacoraAccesoList;

    public Usuarios() {
    }

    public Usuarios(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Usuarios(Integer usuarioId, String nombre, String correo, byte[] contraseña) {
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public byte[] getContraseña() {
        return contraseña;
    }

    public void setContraseña(byte[] contraseña) {
        this.contraseña = contraseña;
    }

    public List<Ventas> getVentasList() {
        return ventasList;
    }

    public void setVentasList(List<Ventas> ventasList) {
        this.ventasList = ventasList;
    }

    public List<Compras> getComprasList() {
        return comprasList;
    }

    public void setComprasList(List<Compras> comprasList) {
        this.comprasList = comprasList;
    }

    public Roles getRolId() {
        return rolId;
    }

    public void setRolId(Roles rolId) {
        this.rolId = rolId;
    }

    public List<BitacoraAcceso> getBitacoraAccesoList() {
        return bitacoraAccesoList;
    }

    public void setBitacoraAccesoList(List<BitacoraAcceso> bitacoraAccesoList) {
        this.bitacoraAccesoList = bitacoraAccesoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuarioId != null ? usuarioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.usuarioId == null && other.usuarioId != null) || (this.usuarioId != null && !this.usuarioId.equals(other.usuarioId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.edu.umg.gt.Usuarios[ usuarioId=" + usuarioId + " ]";
    }
    
}
