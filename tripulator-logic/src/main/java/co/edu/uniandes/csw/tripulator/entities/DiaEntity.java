package co.edu.uniandes.csw.tripulator.entities;

import co.edu.uniandes.csw.crud.api.podam.strategy.DateStrategy;
import co.edu.uniandes.csw.crud.spi.entity.BaseEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

@Entity
public class DiaEntity extends BaseEntity implements Serializable {
    
    @ManyToOne
    @PodamExclude
    private ItinerarioEntity itinerario;
    
    @ManyToMany(mappedBy = "dias")
    @PodamExclude
    private List<EventoEntity> eventos = new ArrayList();
    
    @PodamStrategyValue(DateStrategy.class)
    @Temporal(TemporalType.DATE)
    private Date fecha;
    
    private String ciudad;
    
    private String pais;

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
    
    public List<EventoEntity> getEventos() {
        return eventos;
    }

    public void setEventos(List<EventoEntity> eventos) {
        this.eventos = eventos;
    }
    
    public ItinerarioEntity getItinerario() {
        return itinerario;
    }

    public void setItinerario(ItinerarioEntity itinerario) {
        this.itinerario = itinerario;
    }

    /**
     * @return the pais
     */
    public String getPais() {
        return pais;
    }

    /**
     * @param pais the pais to set
     */
    public void setPais(String pais) {
        this.pais = pais;
    }
}
