/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.nullpointer.rest.tripulator.resources;

import co.edu.uniandes.csw.tripulator.api.IDiaLogic;
import co.edu.uniandes.csw.tripulator.api.IEventoLogic;
import co.edu.uniandes.csw.tripulator.ejbs.DiaLogic;
import co.edu.uniandes.csw.tripulator.entities.DiaEntity;
import co.edu.uniandes.csw.tripulator.entities.EventoEntity;
import co.edu.uniandes.csw.tripulator.exceptions.BusinessLogicException;
import co.edu.uniandes.nullpointer.rest.tripulator.converters.EventoConverter;
import co.edu.uniandes.nullpointer.rest.tripulator.dtos.EventoDTO;
import co.edu.uniandes.nullpointer.rest.tripulator.exceptions.TripulatorLogicException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author jd.fandino10
 */

@Path("/viajeros/{idViajero}/itinerarios/{idItinerario}/dias/{idDia}/eventos")
@Produces("application/json")
@RequestScoped
public class EventoResource {


    private static final Logger logger = Logger.getLogger(EventoResource.class.getName());

	@Inject
	IEventoLogic eventoLogic;

        @Inject
        IDiaLogic diaLogic;
	/**
	 * Obtiene el listado de eventos.
	 * @return lista de eventos
	 * @throws TripulatorLogicException excepción retornada por la lógica
	 */
    @GET
    @Path("all")
    public List<EventoDTO> getEventos() throws TripulatorLogicException {
        return EventoConverter.listEntity2DTO(eventoLogic.getEventos());
    }

    /**
     * Obtiene un evento
     * @param id identificador de el evento
     * @return evento encontrado
     * @throws TripulatorLogicException cuando el evento no existe
     */
    @GET
    @Path("{id}/get")
    public EventoDTO getEvento(@PathParam("id") Long id) throws TripulatorLogicException, BusinessLogicException {
        return EventoConverter.fullEntity2DTO(eventoLogic.getEvento(id));
    }

    /**
     * Obtiene un evento segun fecha y ciudad
     * @param idV id del viajero
     * @param idI id del itinerario
     * @param idD id del dia
     * @return evento encontrado
     * @throws TripulatorLogicException cuando el evento no existe
     * @throws co.edu.uniandes.csw.tripulator.exceptions.BusinessLogicException
     */
    @GET
    public List<EventoDTO> getEventoCiudadFecha(@PathParam("idViajero") Long idV,
                                                @PathParam("idItinerario") Long idI,
                                                @PathParam("idDia") Long idD) throws TripulatorLogicException, BusinessLogicException {
        DiaEntity d = diaLogic.getDia(idV, idI, idD);
        try{
        Date dia = d.getDate();
        String ciudad = d.getCiudad();
        List<EventoEntity> dtos = eventoLogic.getEventosCiudadFecha(ciudad, dia);
        return EventoConverter.listEntity2DTO(dtos);
        }catch(Exception e){
            throw new TripulatorLogicException(e.getMessage());
        }
    }

    /**
     * Agrega un evento
     * @param Evento evento a agregar
     * @return datos de el evento a agregar
     * @throws TripulatorLogicException cuando ya existe un evento con el id suministrado
     */
    @POST
    public EventoDTO createEvento(EventoDTO dto) throws TripulatorLogicException {
        logger.info("Se ejecuta método createEvento");
        EventoEntity entity = EventoConverter.fullDTO2Entity(dto);
        EventoEntity newEntity;
        try {
            newEntity = eventoLogic.createEvento(entity);
        } catch (BusinessLogicException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            throw new WebApplicationException(ex.getLocalizedMessage(), ex, Response.Status.BAD_REQUEST);
        }
        return EventoConverter.fullEntity2DTO(newEntity);
    }

    /**
     * Actualiza los datos de un evento
     * @param id identificador de el evento a modificar
     * @param Evento evento a modificar
     * @return datos de el evento modificada
     * @throws TripulatorLogicException cuando no existe un evento con el id suministrado
     */
    @PUT
    @Path("{id}/update")
    public EventoDTO updateEvento(@PathParam("id") Long id, EventoDTO dto) throws TripulatorLogicException, Exception {
        logger.log(Level.INFO, "Se ejecuta método updateEvento con id={0}", id);
        EventoEntity entity = EventoConverter.fullDTO2Entity(dto);
        entity.setId(id);
        EventoEntity oldEntity = eventoLogic.getEvento(id);
        try {
            EventoEntity savedEvento = eventoLogic.updateEvento(entity);
            return EventoConverter.fullEntity2DTO(savedEvento);
        } catch (BusinessLogicException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            throw new WebApplicationException(ex.getLocalizedMessage(), ex, Response.Status.BAD_REQUEST);
        }
    }

    /**
     * Elimina los datos de un evento
     * @param id identificador de el evento a eliminar
     * @throws TripulatorLogicException cuando no existe un evento con el id suministrado
     */
    @DELETE
    @Path("{id}/delete")
    public void deleteEvento(@PathParam("id") Long id) throws TripulatorLogicException {
    	eventoLogic.deleteEvento(id);
    }
}
