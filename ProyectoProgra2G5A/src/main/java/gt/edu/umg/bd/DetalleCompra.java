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
@Table(name = "DetalleCompra", catalog = "DistribuidoraAgricola", schema = "dbo")
@NamedQueries({
    @NamedQuery(name = "DetalleCompra.findAll", query = "SELECT d FROM DetalleCompra d")})
public class DetalleCompra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "detalle_compra_id", nullable = false)
    private Integer detalleCompraId;
    @Basic(optional = false)
    @Column(name = "cantidad", nullable = false)
    private int cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;
    @Column(name = "subtotal", precision = 21, scale = 2)
    private BigDecimal subtotal;
    @JoinColumn(name = "compra_id", referencedColumnName = "compra_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Compras compraId;
    @JoinColumn(name = "producto_id", referencedColumnName = "producto_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Productos productoId;

    public DetalleCompra() {
    }

    public DetalleCompra(Integer detalleCompraId) {
        this.detalleCompraId = detalleCompraId;
    }

    public DetalleCompra(Integer detalleCompraId, int cantidad, BigDecimal precioUnitario) {
        this.detalleCompraId = detalleCompraId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public Integer getDetalleCompraId() {
        return detalleCompraId;
    }

    public void setDetalleCompraId(Integer detalleCompraId) {
        this.detalleCompraId = detalleCompraId;
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

    public Compras getCompraId() {
        return compraId;
    }

    public void setCompraId(Compras compraId) {
        this.compraId = compraId;
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
        hash += (detalleCompraId != null ? detalleCompraId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleCompra)) {
            return false;
        }
        DetalleCompra other = (DetalleCompra) object;
        if ((this.detalleCompraId == null && other.detalleCompraId != null) || (this.detalleCompraId != null && !this.detalleCompraId.equals(other.detalleCompraId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.edu.umg.bd.DetalleCompra[ detalleCompraId=" + detalleCompraId + " ]";
    }
    
}
