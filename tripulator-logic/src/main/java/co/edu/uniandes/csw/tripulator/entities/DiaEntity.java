package co.edu.uniandes.csw.tripulator.entities;

import co.edu.uniandes.csw.crud.spi.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class DiaEntity extends BaseEntity implements Serializable {
    
    @Temporal(TemporalType.DATE)
    private Date fecha;
    
    private String ciudad;

    /**
     * @return the fecha
     */
    public Date getDate() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setDate(Date fecha) {
        this.fecha = fecha;
    }
    

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    /**
     * Faltan los atributos que marcan las relaciones.
     */
    
}
