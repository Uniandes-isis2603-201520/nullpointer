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
    String ciudad;
    private String pais;
    
    public DiaDTO(){
    }

    public DiaDTO(Long id, Date fecha, ArrayList eventos, String idCiudad, String pais) {
        super();
        this.id = id;
        this.fecha = fecha;
        this.eventos = eventos;
        this.ciudad = idCiudad;
        this.pais = pais;
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

    public String getCiudad() {
        return ciudad;
    }
    
    public void setCiudad(String idCiudad) {
        this.ciudad = idCiudad;
    }   
    
    /**
     * Convierte el objeto a una cadena
     * @return 
     */
    @Override
    public String toString() {
        return "{ id : " + id + ", fecha : \"" + fecha + "\" "
                + ", eventos : \"" + eventos + "\" "
                + ", idCiudad : \"" +  ciudad + "\" " + "}";

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
