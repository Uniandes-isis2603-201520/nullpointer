/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.nullpointer.rest.tripulator.dtos;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Nicolás
 */
public class DiaDTO {
    
    Long id;
    Date fecha;
    ArrayList eventos;
    Long idCiudad;

    public DiaDTO(Long id, Date fecha, ArrayList eventos, long idCiudad) {
        this.id = id;
        this.fecha = fecha;
        this.eventos = eventos;
        this.idCiudad = idCiudad;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public ArrayList getEventos() {
        return eventos;
    }
    
    public Object getEvento(int pos) {
        return eventos.get(pos);
    }

    public void setEventos(ArrayList eventos) {
        this.eventos = eventos;
    }

    public Long getIdCiudad() {
        return idCiudad;
    }
    
    public void setIdCiudad(Long idCiudad) {
        this.idCiudad = idCiudad;
    }   
}
