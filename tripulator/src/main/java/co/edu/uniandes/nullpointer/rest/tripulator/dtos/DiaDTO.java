/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.nullpointer.rest.tripulator.dtos;

import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nicolás
 */
@XmlRootElement
public class DiaDTO {

    private Long id;
    private Date fecha;
    private List<EventoDTO> eventos;
    private String ciudad;
    private String pais;

    public DiaDTO() {
    }

    public DiaDTO(Long id, Date fecha, List<EventoDTO> eventos, String ciudad, String pais) {
        super();
        this.id = id;
        this.fecha = fecha;
        this.eventos = eventos;
        this.ciudad = ciudad;
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

    public List<EventoDTO> getEventos() {
        return eventos;
    }

    public void setEventos(List<EventoDTO> eventos) {
        this.eventos = eventos;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
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
    
    /**
     * Convierte el objeto a una cadena
     */
    @Override
    public String toString() {
        return "{ \n id : " + id + ",\n fecha : \"" + fecha + "\" "
                + ",\n eventos : \"" + eventos + "\" "
                + ",\n pais : \"" + pais + "\" "
                + ",\n ciudad : \"" + ciudad + "\" ,\n" + "}";

    }
}
