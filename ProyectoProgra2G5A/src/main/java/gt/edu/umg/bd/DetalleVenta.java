/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.edu.umg.bd;

import java.io.Serializable;
import java.math.BigDecimal;
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

/**
 *
 * @author Marlon Cuco
 */
@Entity
@Table(name = "DetalleVenta", catalog = "DistribuidoraAgricola", schema = "dbo")
@NamedQueries({
    @NamedQuery(name = "DetalleVenta.findAll", query = "SELECT d FROM DetalleVenta d")})
public class DetalleVenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "detalle_venta_id", nullable = false)
    private Integer detalleVentaId;
    @Basic(optional = false)
    @Column(name = "cantidad", nullable = false)
    private int cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;
    @Column(name = "subtotal", precision = 21, scale = 2)
    private BigDecimal subtotal;
    @JoinColumn(name = "producto_id", referencedColumnName = "producto_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Productos productoId;
    @JoinColumn(name = "venta_id", referencedColumnName = "venta_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Ventas ventaId;

    public DetalleVenta() {
    }

    public DetalleVenta(Integer detalleVentaId) {
        this.detalleVentaId = detalleVentaId;
    }

    public DetalleVenta(Integer detalleVentaId, int cantidad, BigDecimal precioUnitario) {
        this.detalleVentaId = detalleVentaId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public Integer getDetalleVentaId() {
        return detalleVentaId;
    }

    public void setDetalleVentaId(Integer detalleVentaId) {
        this.detalleVentaId = detalleVentaId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Productos getProductoId() {
        return productoId;
    }

    public void setProductoId(Productos productoId) {
        this.productoId = productoId;
    }

    public Ventas getVentaId() {
        return ventaId;
    }

    public void setVentaId(Ventas ventaId) {
        this.ventaId = ventaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (detalleVentaId != null ? detalleVentaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleVenta)) {
            return false;
        }
        DetalleVenta other = (DetalleVenta) object;
        if ((this.detalleVentaId == null && other.detalleVentaId != null) || (this.detalleVentaId != null && !this.detalleVentaId.equals(other.detalleVentaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.edu.umg.bd.DetalleVenta[ detalleVentaId=" + detalleVentaId + " ]";
    }
    
}
