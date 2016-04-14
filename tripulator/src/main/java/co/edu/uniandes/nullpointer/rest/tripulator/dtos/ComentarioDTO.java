/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.nullpointer.rest.tripulator.dtos;

import java.util.Date;

/**
 *
 * @author Jose Daniel
 */
public class ComentarioDTO {
    
    private Long id;//Long para el identificador del comentario
    private String userPhoto; //String de la ruta de la foto del usuario
    private int stars;  //int de la cantidad de estrellas de la calificacion
    private Date date;  //Long de la fecha del comentario
    private String comment;   //String del comentario
    /**
     * Constructor por defecto
     */
    public ComentarioDTO() {
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
    public ComentarioDTO(Long id, String user, String userPhoto, int stars, String comment, Long id_evento) {
        super();
        this.id = id;
        this.userPhoto = userPhoto;
        this.stars = stars;
        this.date = new Date();
        this.comment = comment;
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
     * @return the userPhoto
     */
    public String getUserPhoto() {
        return userPhoto;
    }

    /**
     * @param image the userPhoto to set
     */
    public void setUserPhoto(String image) {
        this.userPhoto = image;
    }

    /**
     * @return the stars
     */
    public int getStars() {
        return stars;
    }

    /**
     * @param stars the stars to set
     */
    public void setStars(int stars) {
        this.stars = stars;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Convierte el objeto a una cadena
     */
    @Override
    public String toString() {
        return "{id:" + id + ",\n userPhoto:'" + userPhoto
                +"', \n stars:" + stars + ",\n date:" + date + ",\n comment:'" + comment + "'}";
    }

}
