/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.tripulator.entities;

import co.edu.uniandes.csw.crud.spi.entity.BaseEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author josef
 */
@Entity
public class FotoEntity extends BaseEntity implements Serializable{
    
    @ManyToOne
    @PodamExclude
    private ItinerarioEntity itinerario;
   
    private String src;
    
    public String getSrc()
    {
        return src;
    }
    
    public void setSrc(String src)
    {
        this.src=src;
    }
    
}
