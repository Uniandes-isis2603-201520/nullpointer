package co.edu.uniandes.csw.tripulator.entities;

import co.edu.uniandes.csw.crud.spi.entity.BaseEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ViajeroEntity extends BaseEntity implements Serializable {

    private int id;
    private String nombre;
    private String apellido;
    private String email;
    private String usuario;
    private String password;
    

    @ManyToMany(mappedBy = "viajeros")
    //private List<ItinerarioEntity> itinerarios = new ArrayList<>();

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

   // public List<ItinerarioEntity> getItinerarios() {
   //     return itinerarios;
   // }

   // public void setItinerarios(List<ItinerarioEntity> itinerarios) {
   //     this.itinerarios = itinerarios;
   // }

}