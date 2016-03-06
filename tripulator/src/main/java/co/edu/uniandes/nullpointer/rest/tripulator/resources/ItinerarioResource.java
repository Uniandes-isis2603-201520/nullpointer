/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.nullpointer.rest.tripulator.resources;

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
 * @author Antonio de la Vega
 */
@Path("/viajeros/:id/itinerarios")
public class ItinerarioResource {
    @GET
    public List<ItinerarioDTO> getItinerarios(){
        return null;
    }
    
    /**
     * Obtiene una ciudad
     * @param id identificador del itinerario.
     * @return itinerario encontrado.
     * @throws co.edu.uniandes.nullpointer.rest.tripulator.exceptions.
     * TripulatorLogicException cuando el itinerario no existe
     */
    @GET
    @Path("{id: \\d+}")
    public ItinerarioDTO getItinerario(@PathParam("id") Long id) throws TripulatorLogicException{
        return null;
    }
    
        /**
     * Agrega un itinerario
     * @param itinerario itinerario a agregar
     * @return datos del itinerario a agregar
     * @throws co.edu.uniandes.nullpointer.rest.tripulator.exceptions.TripulatorLogicException
     */
    @POST
    public ItinerarioDTO createItinerario(ItinerarioDTO itinerario) throws TripulatorLogicException {
        return null;
    }
    
    /**
     * Actualiza los datos de un itinerario.
     * @param id identificador del itinerario a modificar
     * @param itinerario itinerario a modificar
     * @return datos de la ciudad modificada 
     * @throws TripulatorLogicException cuando no existe una ciudad con el id suministrado
     */
    @PUT
    @Path("{id: \\d+}")
    public ItinerarioDTO updateItinerario(@PathParam("id") Long id, ItinerarioDTO itinerario) throws TripulatorLogicException {
        return null;
    }
    
    /**
     * Elimina los datos de una ciudad
     * @param id identificador del itinerario a eliminar
     * @throws TripulatorLogicException cuando no existe un itinerario con el id suministrado
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteItinerario(@PathParam("id") Long id) throws TripulatorLogicException {
    	//itinerarioLogic.deleteCity(id);
    }
}
