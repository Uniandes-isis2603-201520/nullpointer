/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.nullpointer.rest.tripulator.resources;

import co.edu.uniandes.nullpointer.rest.tripulator.dtos.ItinerarioDTO;
import co.edu.uniandes.nullpointer.rest.tripulator.exceptions.TripulatorLogicException;
import co.edu.uniandes.nullpointer.rest.tripulator.mocks.ItinerarioLogicMock;
import java.util.List;
import javax.inject.Inject;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Antonio de la Vega
 */
@Path("/viajeros/:id/itinerarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItinerarioResource {
    
    @Inject
    ItinerarioLogicMock itinerarioLogic;
    
    /**
     * Devuelve todos los itinerarios.
     * @return itinerarios
     * @throws TripulatorLogicException 
     */
    @GET
    public List<ItinerarioDTO> getItinerarios() throws TripulatorLogicException{
        return itinerarioLogic.getItinerarios();
    }
    
    /**
     * Obtiene un itinerario
     * @param id identificador del itinerario.
     * @return itinerario encontrado.
     * TripulatorLogicException cuando el itinerario no existe
     * @throws co.edu.uniandes.nullpointer.rest.tripulator.exceptions.TripulatorLogicException
     */
    @GET
    @Path("{id: \\d+}")
    public ItinerarioDTO getItinerario(@PathParam("id") Long id) throws TripulatorLogicException{
        return itinerarioLogic.getItinerario(id);
    }
    
        /**
     * Agrega un itinerario
     * @param itinerario itinerario a agregar
     * @return datos del itinerario a agregar
     * @throws co.edu.uniandes.nullpointer.rest.tripulator.exceptions.TripulatorLogicException
     */
    @POST
    public ItinerarioDTO createItinerario(ItinerarioDTO itinerario) throws TripulatorLogicException {
        return itinerarioLogic.createItinerario(itinerario);
    }
    
    /**
     * Actualiza los datos de un itinerario.
     * @param id identificador del itinerario a modificar
     * @param itinerario itinerario a modificar
     * @return datos de la viajero modificada 
     * @throws TripulatorLogicException cuando no existe un itinerario con el id suministrado
     */
    @PUT
    @Path("{id: \\d+}")
    public ItinerarioDTO updateItinerario(@PathParam("id") Long id, ItinerarioDTO itinerario) throws TripulatorLogicException {
        return itinerarioLogic.updateItinerario(id, itinerario);
    }
    
    /**
     * Elimina los datos de un itinerario
     * @param id identificador del itinerario a eliminar
     * @throws TripulatorLogicException cuando no existe un itinerario con el id suministrado
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteItinerario(@PathParam("id") Long id) throws TripulatorLogicException {
    	itinerarioLogic.deleteCity(id);
    }
}
