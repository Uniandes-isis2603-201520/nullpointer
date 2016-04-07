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
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author Antonio de la Vega
 */
@Path("/viajeros/{idViajero}/itinerarios")
@Produces("application/json")
@RequestScoped
public class ItinerarioResource {
    
    @Inject
    ItinerarioLogic itinerarioLogic;
    
    /**
     * Devuelve todos los itinerarios.
     * @param idViajero
     * @return itinerarios
     * @throws TripulatorLogicException 
     */
    @GET
    public List<ItinerarioDTO> getItinerarios(@PathParam("idViajero") Long idViajero) throws TripulatorLogicException{
        return itinerarioLogic.getItinerarios(idViajero);
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
    public ItinerarioDTO getItinerario(@PathParam("idViajero") Long idViajero,
            @PathParam("id") Long id) throws TripulatorLogicException{
        return itinerarioLogic.getItinerario(idViajero, id);
    }
    
        /**
     * Agrega un itinerario
     * @param itinerario itinerario a agregar
     * @return datos del itinerario a agregar
     * @throws co.edu.uniandes.nullpointer.rest.tripulator.exceptions.TripulatorLogicException
     */
    @POST
    public ItinerarioDTO createItinerario(@PathParam("idViajero") Long idViajero,
            ItinerarioDTO itinerario) throws TripulatorLogicException {
        return itinerarioLogic.createItinerario(idViajero,itinerario);
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
    public ItinerarioDTO updateItinerario(@PathParam("idViajero") Long idViajero,
            @PathParam("id") Long id, ItinerarioDTO itinerario) throws TripulatorLogicException {
        return itinerarioLogic.updateItinerario(idViajero, id, itinerario);
    }
    
    /**
     * Elimina los datos de un itinerario
     * @param id identificador del itinerario a eliminar
     * @throws TripulatorLogicException cuando no existe un itinerario con el id suministrado
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteItinerario(@PathParam("idViajero") Long idViajero,
            @PathParam("id") Long id) throws TripulatorLogicException {
    	itinerarioLogic.deleteItinerario(idViajero,id);
    }
}
