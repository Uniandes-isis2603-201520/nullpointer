/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.tripulator.entities;

import co.edu.uniandes.csw.crud.api.podam.strategy.DateStrategy;
import co.edu.uniandes.csw.crud.spi.entity.BaseEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author Antonio de la Vega
 */
@Entity
public class ItinerarioEntity extends BaseEntity implements Serializable {
    @PodamStrategyValue(DateStrategy.class)
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    
    @PodamStrategyValue(DateStrategy.class)
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    
    @ManyToOne
    @PodamExclude
    private ViajeroEntity viajero;
    
    @OneToMany(mappedBy = "itinerario", cascade = CascadeType.ALL, orphanRemoval = true)
    @PodamExclude
    private List<DiaEntity> dias = new ArrayList<>();
    
    @OneToMany(mappedBy = "itinerario", cascade = CascadeType.ALL, orphanRemoval = true)
    @PodamExclude
    private List<FotoEntity> fotos = new ArrayList<>();

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the viajero
     */
    public ViajeroEntity getViajero() {
        return viajero;
    }

    /**
     * @param viajero the viajero to set
     */
    public void setViajero(ViajeroEntity viajero) {
        this.viajero = viajero;
    }

    /**
     * @return the dias
     */
    public List<DiaEntity> getDias() {
        return dias;
    }

    /**
     * @param dias the dias to set
     */
    public void setDias(List<DiaEntity> dias) {
        this.dias = dias;
    }

    /**
     * @return the fotos
     */
    public List<FotoEntity> getFotos() {
        return fotos;
    }

    /**
     * @param fotos the fotos to set
     */
    public void setFotos(List<FotoEntity> fotos) {
        this.fotos = fotos;
    }
    
}
