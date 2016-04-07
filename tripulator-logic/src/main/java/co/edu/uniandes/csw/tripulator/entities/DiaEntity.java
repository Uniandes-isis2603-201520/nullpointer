package co.edu.uniandes.csw.tripulator.entities;

import co.edu.uniandes.csw.crud.spi.entity.BaseEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class DiaEntity extends BaseEntity implements Serializable {
    
    private Long id;
    
    @Temporal(TemporalType.DATE)
    private Date fecha;
    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the fecha
     */
    public Date getPublishDate() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setPublishDate(Date fecha) {
        this.fecha = fecha;
    }
}
