/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.nullpointer.rest.tripulator.resources;

import co.edu.uniandes.csw.tripulator.api.IDiaLogic;
import co.edu.uniandes.csw.tripulator.entities.DiaEntity;
import co.edu.uniandes.csw.tripulator.exceptions.BusinessLogicException;
import co.edu.uniandes.nullpointer.rest.tripulator.converters.DiaConverter;
import co.edu.uniandes.nullpointer.rest.tripulator.dtos.DiaDTO;
import co.edu.uniandes.nullpointer.rest.tripulator.exceptions.TripulatorLogicException;
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
 * @author Nicolás Gómez G.
 */
@Path("/viajeros/{idViajero}/itinerarios/{idItinerario}/dias")
@Produces("application/json")
@RequestScoped
public class DiaResource {
    
    @Inject 
    IDiaLogic diaLogic;
    
    /**
     * 
     * @param idViajero
     * @param idItinerario
     * @return
     * @throws TripulatorLogicException 
     */
    @GET
    public List<DiaDTO> getDias(@PathParam("idViajero") Long idViajero,
            @PathParam("idItinerario") Long idItinerario) throws TripulatorLogicException, BusinessLogicException {
        return DiaConverter.listEntity2DTO(diaLogic.getDias(idViajero,idItinerario));
    }
    
    /**
     * Obtiene un día específico
     * @param id id del día que se quiere.
     * @param idItinerario id del itinerario que contiene el día que se quiere.
     * @param idViajero id del viajero que tiene el itinerario que contiene el día que se quiere.
     * @return día con el id dado.
     * @throws co.edu.uniandes.nullpointer.rest.tripulator.exceptions.TripulatorLogicException cuando el día no existe
     */
    @GET
    @Path("{id: \\d+}")
    public DiaDTO getDia(@PathParam("idViajero") Long idViajero,
            @PathParam("idItinerario") Long idItinerario,
            @PathParam("id") Long id) throws TripulatorLogicException, BusinessLogicException{
        return DiaConverter.fullEntity2DTO(diaLogic.getDia(idViajero, idItinerario, id));
    }
    
      /**
     * Agrega un día
     * @param idViajero id del viajero que tiene el itinerario al que se le quiere agregar un día.
     * @param idItinerario id del itinerario al que se le quiere agregar un día.
     * @param dia día a agregar
     * @return null
     * @throws co.edu.uniandes.nullpointer.rest.tripulator.exceptions.TripulatorLogicException
     */
    @POST
    public DiaDTO createDia(@PathParam("idViajero") Long idViajero,
            @PathParam("idItinerario") Long idItinerario,
            DiaDTO dia) throws TripulatorLogicException, BusinessLogicException {
        return DiaConverter.fullEntity2DTO(diaLogic.createDia(idViajero, idItinerario, DiaConverter.fullDTO2Entity(dia)));
    }
    
    /**
     * Actualiza los datos de un día.
     * @param idViajero id del viajero que tiene el itinerario que contiene el día que se quiere modificar.
     * @param idItinerario id del itinerario que contiene el día que se quiere modificar.
     * @param id identificador del día a modificar
     * @param dia dia a modificar
     * @return datos resultantes
     * @throws TripulatorLogicException cuando no existe un día con el id suministrado
     */
    @PUT
    @Path("{id: \\d+}")
    public DiaDTO updateDia(@PathParam("idViajero") Long idViajero,
            @PathParam("idItinerario") Long idItinerario,
            @PathParam("id") Long id, DiaDTO dia) throws TripulatorLogicException, BusinessLogicException {
        DiaEntity converted = DiaConverter.fullDTO2Entity(dia);
        return DiaConverter.fullEntity2DTO(diaLogic.updateDia(idViajero, idItinerario, converted));
    }
    
    /**
     * Elimina los datos de un día
     * @param idViajero id del viajero que tiene el itinerario que contiene el día a eliminar.
     * @param idItinerario id del itinerario que contiene el día a eliminar.
     * @param id identificador del día a eliminar
     * @throws TripulatorLogicException cuando no existe un día con el id suministrado
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteDia(@PathParam("idViajero") Long idViajero,
            @PathParam("idItinerario") Long idItinerario,
            @PathParam("id") Long id) throws TripulatorLogicException, BusinessLogicException {
        diaLogic.deleteDia(idViajero, idItinerario, id);
    }
}
