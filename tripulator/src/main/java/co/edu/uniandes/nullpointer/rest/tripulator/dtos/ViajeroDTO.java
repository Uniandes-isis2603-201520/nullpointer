/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.nullpointer.rest.tripulator.dtos;
/**
 *
 * @author Juan Sebastian Cardona
 */
public class ViajeroDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String contrasena;
    private String usuario;
    // itinerarios : arreglo de ids y nombre de los itinerarios//

   
    /**
     * Constructor con parámetros.
     *
     * @param id identificador de un Viajero
     * @param nombre nombre de un Viajero
     * @param apellido apellido de un Viajero
     * @param contraseña contraseña de un Viajero
     * @param usuario usuario de un Viajero
     */
    public ViajeroDTO(){
        
    }
    
    public ViajeroDTO(Long id, String nombre, String apellido, String contrasena, String usuario) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contrasena = contrasena;
        this.usuario = usuario;
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
     * @return el nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return el apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * @return el contraseña
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * @return el usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param nombre el nombre a poner
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @param apellido el apellido a poner
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * @param contraseña la contraseña a poner
     */
    public void setContraseña(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * @param usuario el usuairo a poner
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Convierte el objeto a una cadena
     * @return 
     */
    @Override
    public String toString() {
        return "{ id : " + id + ", nombre : \"" + nombre + "\" "
                + ", apellido : \"" + apellido + "\" "
                + ", contrasena : \"" +  contrasena + "\" "
                + ", usuario : \"" + usuario + "\" " + "}";

    }
}
