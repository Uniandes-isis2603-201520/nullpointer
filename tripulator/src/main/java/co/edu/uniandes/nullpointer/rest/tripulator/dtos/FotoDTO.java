/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.nullpointer.rest.tripulator.dtos;

/**
 *
 * @author JOSE QUIROGA
 */
public class FotoDTO {
    
    /**
     * ID de la foto
     */
    private Long id;
    
    /**
     * Ruta de la imagen
     */
    private String src;
    
    
    /**
     * Constructor
     * @param id de la foto
     * @param src de la foto
     */
    public FotoDTO(Long id, String src)
    {
        super();
        
        this.id=id;
        
        this.src=src;
        
    }
    
    /**
     * Constructor vacio
     */
    public FotoDTO(){}

    /**
     * Getter id
     * @return 
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter id
     * @param id nuevo de foto
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter src
     * @return src de la foto
     */
    public String getSrc() {
        return src;
    }

    /**
     * Setter src
     * @param src nuevo de foto
     */
    public void setSrc(String src) {
        this.src = src;
    }
    
    
        /**
     * Convierte el objeto a una cadena
     * @return 
     */
    @Override
    public String toString() {
        return "{ id : " + id + ", src : \"" + src + "\" " + "}";
    }
}
