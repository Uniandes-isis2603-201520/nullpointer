/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.nullpointer.rest.tripulator.resources;

import co.edu.uniandes.nullpointer.rest.tripulator.dtos.EventoDTO;
import co.edu.uniandes.nullpointer.rest.tripulator.exceptions.TripulatorLogicException;
import co.edu.uniandes.nullpointer.rest.tripulator.mocks.EventoLogicMock;
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
 * @author jd.fandino10
 */

@Path("/eventos")
@Produces("application/json")
@RequestScoped
public class EventoResource {

    
	@Inject
	EventoLogicMock eventoLogic;

	/**
	 * Obtiene el listado de eventos. 
	 * @return lista de eventos
	 * @throws TripulatorLogicException excepción retornada por la lógica  
	 */
    @GET
    public List<EventoDTO> getEventos() throws TripulatorLogicException {
        return eventoLogic.getEventos();
    }

    /**
     * Obtiene un evento
     * @param id identificador de el evento
     * @return evento encontrado
     * @throws TripulatorLogicException cuando el evento no existe
     */
    @GET
    @Path("{id}")
    public EventoDTO getEvento(@PathParam("id") Long id) throws TripulatorLogicException {
        return eventoLogic.getEvento(id);
    }

    /**
     * Agrega un evento
     * @param Evento evento a agregar
     * @return datos de el evento a agregar
     * @throws TripulatorLogicException cuando ya existe un evento con el id suministrado
     */
    @POST
    public EventoDTO createEvento(EventoDTO Evento) throws TripulatorLogicException {
        return eventoLogic.createEvento(Evento);
    }

    /**
     * Actualiza los datos de un evento
     * @param id identificador de el evento a modificar
     * @param Evento evento a modificar
     * @return datos de el evento modificada 
     * @throws TripulatorLogicException cuando no existe un evento con el id suministrado
     */
    @PUT
    @Path("{id}")
    public EventoDTO updateEvento(@PathParam("id") Long id, EventoDTO Evento) throws TripulatorLogicException {
        return eventoLogic.updateEvento(id, Evento);
    }

    /**
     * Elimina los datos de un evento
     * @param id identificador de el evento a eliminar
     * @throws TripulatorLogicException cuando no existe un evento con el id suministrado
     */
    @DELETE
    @Path("{id}")
    public void deleteEvento(@PathParam("id") Long id) throws TripulatorLogicException {
    	eventoLogic.deleteEvento(id);
    }
}
