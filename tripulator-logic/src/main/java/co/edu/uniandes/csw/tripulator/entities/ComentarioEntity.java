/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
/**
 *
 * @author Daniel Delgado
 */

@Entity

public class ComentarioEntity extends BaseEntity implements Serializable{
    
    private String comment;
    private Long id;
    
    /**
     * @return Devuelve el comentario
     */
    public String getComentario() {
        return comment;
    }
    
    /**
     * @return Devuelve antes del id
     */
    public Long getID() {
        return id;
    }
    
        /**
     * @param id borra el comentario con el id espeificado
     * @return el comentario que borro
     */
    public String deleteComentario(int id) {
        return comment;
    }
}
