/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.nullpointer.rest.tripulator.resources;

import co.edu.uniandes.nullpointer.rest.tripulator.datos.DiaDTO;
import co.edu.uniandes.nullpointer.rest.tripulator.exceptions.TripulatorLogicException;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 *
 * @author Nicolás Gómez G.
 */
@Path("/days")
public class DiaResource {
    
    /**
     * Obtiene todos los días.
     * @return days
     */
    @GET
    public List<DiaDTO> getDias(){
        return null;
    }
    
    /**
     * Obtiene un día específico
     * @param id id del día que se quiere.
     * @return día con la fecha dada.
     * @throws co.edu.uniandes.nullpointer.rest.tripulator.exceptions.
     * TripulatorLogicException cuando el itinerario no existe
     */
    @GET
    @Path("{id: \\d+}")
    public DiaDTO getDia(@PathParam("id") Long id) throws TripulatorLogicException{
        return null;
    }
    
        /**
     * Agrega un día
     * @param day día a agregar
     * @return null
     * @throws co.edu.uniandes.nullpointer.rest.tripulator.exceptions.TripulatorLogicException
     */
    @POST
    public DiaDTO createDia(DiaDTO dia) throws TripulatorLogicException {
        return null;
    }
    
    /**
     * Actualiza los datos de un día.
     * @param id identificador del día a modificar
     * @param itinerario itinerario a modificar
     * @return datos resultantes
     * @throws TripulatorLogicException cuando no existe un día con el id suministrado
     */
    @PUT
    @Path("{id: \\d+}")
    public DiaDTO updateDia(@PathParam("id") Long id, DiaDTO dia) throws TripulatorLogicException {
        return null;
    }
    
    /**
     * Elimina los datos de un día
     * @param id identificador del día a eliminar
     * @throws TripulatorLogicException cuando no existe un día con el id suministrado
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteDia(@PathParam("id") Long id) throws TripulatorLogicException {
  
    }
}
