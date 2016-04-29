/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.nullpointer.rest.tripulator.dtos;

import co.edu.uniandes.csw.crud.api.podam.strategy.DateStrategy;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author Jose Daniel
 */
@XmlRootElement
public class EventoDTO {

    public static final String tipoE ="Evento";
    public static final String tipoS ="Sitio";

    private Long id;//Long para el identificador del evento
    private String title; //String del nombre del evento
    private String image; //String de la ruta de la imagen del evento
    private String type;  //String del tipo del evento (sitio o evento)
    @PodamStrategyValue(DateStrategy.class)
    private Date start;  //Long de la fecha de inicio
    @PodamStrategyValue(DateStrategy.class)
    private Date end;    //Long de la fecha final
    private String description;   //String de la descripcion
    private String ciudad;

    @PodamExclude
    private List<ComentarioDTO> comentarios = new ArrayList<>();  //Arreglo de comentarios

    /**
     * Constructor por defecto
     */
    public EventoDTO() {
    }

    /**
     * Constructor con parámetros.
     *
     * @param id identificador del evento
     * @param title nombre del evento
     * @param image ruta de la imagen del evento
     * @param type tipo del evento
     * @param start long defecha inicio del evento
     * @param end long de fecha fin del evento
     * @param description descripcion del evento
     */
    public EventoDTO(Long id, String title, String image, String type, Date start,
            Date end, String description) {
        super();
        this.id = id;
        this.title = title;
        this.image = image;
        this.type = type;
        this.start = start;
        this.end = end;
        this.description = description;
    }

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
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the start
     */
    public Date getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * @return the end
     */
    public Date getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(Date end) {
        this.end = end;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
     /**
     * @return la ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * @param ciudad la nueva ciudad
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * @return la ciudad
     */
    public List<ComentarioDTO> getComentarios() {
        return comentarios;
    }

    /**
     * @param comentario la nuevo comentario
     */
    public void setComentarios(List<ComentarioDTO> comentarios) {
        this.comentarios = comentarios;
    }

    /**
     * Convierte el objeto a una cadena
     */
    @Override
    public String toString() {
        return "{id:" + id + ",\n title:'" + title + "',\n image:'" + image
                +"', \n type:'" + type + "',\n start:" + start + ",\n  end:"
                + end + ",\n description:'" + description + "',\n ciudad:'"+ciudad+"' }";
    }

}
