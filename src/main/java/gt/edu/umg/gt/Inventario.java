/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.gt;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Marlon Cuco
 */
@Entity
@Table(name = "Inventario", catalog = "Distribuidora Agricola", schema = "dbo")
@NamedQueries({
    @NamedQuery(name = "Inventario.findAll", query = "SELECT i FROM Inventario i")})
public class Inventario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "inventario_id", nullable = false)
    private Integer inventarioId;
    @Column(name = "cantidad")
    private Integer cantidad;
    @Column(name = "fecha_ultima_actualizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaUltimaActualizacion;
    @JoinColumn(name = "producto_id", referencedColumnName = "producto_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Productos productoId;

    public Inventario() {
    }

    public Inventario(Integer inventarioId) {
        this.inventarioId = inventarioId;
    }

    public Integer getInventarioId() {
        return inventarioId;
    }

    public void setInventarioId(Integer inventarioId) {
        this.inventarioId = inventarioId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFechaUltimaActualizacion() {
        return fechaUltimaActualizacion;
    }

    public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
        this.fechaUltimaActualizacion = fechaUltimaActualizacion;
    }

    public Productos getProductoId() {
        return productoId;
    }

    public void setProductoId(Productos productoId) {
        this.productoId = productoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (inventarioId != null ? inventarioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inventario)) {
            return false;
        }
        Inventario other = (Inventario) object;
        if ((this.inventarioId == null && other.inventarioId != null) || (this.inventarioId != null && !this.inventarioId.equals(other.inventarioId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.edu.umg.gt.Inventario[ inventarioId=" + inventarioId + " ]";
    }
    
}
