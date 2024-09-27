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
@Table(name = "BitacoraAcceso", catalog = "Distribuidora Agricola", schema = "dbo")
@NamedQueries({
    @NamedQuery(name = "BitacoraAcceso.findAll", query = "SELECT b FROM BitacoraAcceso b")})
public class BitacoraAcceso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "bitacora_id", nullable = false)
    private Integer bitacoraId;
    @Column(name = "fecha_acceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAcceso;
    @Column(name = "accion", length = 100)
    private String accion;
    @JoinColumn(name = "usuario_id", referencedColumnName = "usuario_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuarios usuarioId;

    public BitacoraAcceso() {
    }

    public BitacoraAcceso(Integer bitacoraId) {
        this.bitacoraId = bitacoraId;
    }

    public Integer getBitacoraId() {
        return bitacoraId;
    }

    public void setBitacoraId(Integer bitacoraId) {
        this.bitacoraId = bitacoraId;
    }

    public Date getFechaAcceso() {
        return fechaAcceso;
    }

    public void setFechaAcceso(Date fechaAcceso) {
        this.fechaAcceso = fechaAcceso;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Usuarios getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuarios usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bitacoraId != null ? bitacoraId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BitacoraAcceso)) {
            return false;
        }
        BitacoraAcceso other = (BitacoraAcceso) object;
        if ((this.bitacoraId == null && other.bitacoraId != null) || (this.bitacoraId != null && !this.bitacoraId.equals(other.bitacoraId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gt.edu.umg.gt.BitacoraAcceso[ bitacoraId=" + bitacoraId + " ]";
    }
    
}
