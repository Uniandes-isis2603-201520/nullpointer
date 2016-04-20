package co.edu.uniandes.nullpointer.rest.tripulator.resources;
import co.edu.uniandes.csw.tripulator.api.IViajeroLogic;
import co.edu.uniandes.csw.tripulator.ejbs.ViajeroLogic;
import co.edu.uniandes.csw.tripulator.entities.ViajeroEntity;
import co.edu.uniandes.csw.tripulator.exceptions.BusinessLogicException;
import co.edu.uniandes.nullpointer.rest.tripulator.converters.ItinerarioConverter;
import co.edu.uniandes.nullpointer.rest.tripulator.converters.ViajeroConverter;
import co.edu.uniandes.nullpointer.rest.tripulator.dtos.ItinerarioDTO;
import co.edu.uniandes.nullpointer.rest.tripulator.dtos.ViajeroDTO;
import co.edu.uniandes.nullpointer.rest.tripulator.mocks.ViajeroLogicMock;
import co.edu.uniandes.nullpointer.rest.tripulator.exceptions.TripulatorLogicException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Juan Sebastian Cardona
 */
@Path("/viajeros")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ViajeroResource {

        private static final Logger logger = Logger.getLogger(ViajeroResource.class.getName());
        @Inject
	IViajeroLogic viajeroLogic;
    
    @GET
    public List<ViajeroDTO> getViajeros() throws TripulatorLogicException{
       return ViajeroConverter.listEntity2DTO(viajeroLogic.getViajeros());
    }
    
    /**
     * Obtiene un viajero
     * @param id identificador del viajero.
     * @return viajero encontrado.
     * @throws co.edu.uniandes.nullpointer.rest.tripulator.exceptions.TripulatorLogicException
     * TripulatorLogicException cuando el viajero no existe
     */
    @GET
    @Path("{id: \\d+}")
    public ViajeroDTO getViajero(@PathParam("id") Long id) throws TripulatorLogicException, BusinessLogicException{
        return ViajeroConverter.fullEntity2DTO(viajeroLogic.getViajero(id));
    }
    
        /**
     * Agrega un viajero
     * @param viajero viajerp a agregar
     * @return datos del viajero a agregar
     * @throws co.edu.uniandes.nullpointer.rest.tripulator.exceptions.TripulatorLogicException
     */
    @POST
    public ViajeroDTO createViajero(ViajeroDTO viajero) throws TripulatorLogicException {
         ViajeroEntity entity = ViajeroConverter.fullDTO2Entity(viajero);
        return ViajeroConverter.fullEntity2DTO(viajeroLogic.createViajero(entity));
    }
    
       
   
     
    @PUT
    @Path("{id: \\d+}")
    public ViajeroDTO updateViajero(@PathParam("id") Long id, ViajeroDTO dto) throws BusinessLogicException {
        ViajeroEntity entity = ViajeroConverter.fullDTO2Entity(dto);
        entity.setId(id);
        //ViajeroEntity oldEntity = viajeroLogic.getViajero(id);
        //entity.setItinerarios(oldEntity.getItinerarios());
        return ViajeroConverter.fullEntity2DTO(viajeroLogic.updateViajero(entity));
    }

    /**
     * Elimina un objeto de Itinerario de la base de datos.
     *
     * @param id Identificador del objeto a eliminar.
     * @generated
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteViajero(@PathParam("id") Long id) {
        viajeroLogic.deleteViajero(id);
    }

   
    /**
     * Obtiene una colección de objetos de ItinerarioDTO asociadas a un objeto de Viajero
     *
     * @param viajeroId Identificador del objeto de Viajero
     * @return Colección de objetos de ItinerarioDTO (representación basic) asociadas al objeto de Viajero
     * @generated
     *
    
    @GET
    @Path("{viajeroId: \\d+}/itinerarios")
    public List<ItinerarioDTO> listItinerarios(@PathParam("viajeroId") Long viajeroId) {
        return ItinerarioConverter.listEntity2DTO(viajeroLogic.getItinerarios(viajeroId));
    }

    /**
     * Obtiene un objeto de Itinerario asociada a un objeto de Viajero
     *
     * @param viajeroId Identificador del objeto de Viajero
     * @param itinerarioId Identificador del objeto de Itinerario
     * @return Objeto de ItinerarioDTO (representación full)
     * @generated
     *
    @GET
    @Path("{viajeroId: \\d+}/itinerarios/{itinerarioId: \\d+}")
    public ItinerarioDTO getItinerarios(@PathParam("viajeroId") Long viajeroId, @PathParam("itinerarioId") Long itinerarioId) {
        return ItinerarioConverter.fullEntity2DTO(viajeroLogic.getItinerario(viajeroId, itinerarioId));
    }

    /**
     * Asocia un Itinerario existente a un Viajero
     *
     * @param viajeroId Identificador del objeto de Viajero
     * @param itinerarioId Identificador del objeto de Itinerario
     * @return Instancia de ItinerarioDTO (representación full) que fue asociada a Viajero
     * @generated
     *
    @POST
    @Path("{viajeroId: \\d+}/itinerarios/{itinerarioId: \\d+}")
    public ItinerarioDTO addItinerarios(@PathParam("viajeroId") Long viajeroId, @PathParam("itinerarioId") Long itinerarioId) {
        try {
            return ItinerarioConverter.fullEntity2DTO(viajeroLogic.addItinerario(viajeroId, itinerarioId));
        } catch (BusinessLogicException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            throw new WebApplicationException(ex.getLocalizedMessage(), ex, Response.Status.BAD_REQUEST);
        }
    }

    /**
     * Remplaza los objetos de Itinerario asociadas a un objeto de Viajero
     *
     * @param viajeroId Identificador del objeto de Viajero
     * @param itinerarios Colección de objetos de ItinerarioDTO (representación minimum) a asociar a objeto de
     * Viajero
     * @return Nueva colección de ItinerarioDTO (representación basic) asociada al objeto de Viajero
     * @generated
     *
    @PUT
    @Path("{viajeroId: \\d+}/itinerarios")
    public List<ItinerarioDTO> replaceItinerarios(@PathParam("viajeroId") Long viajeroId, List<ItinerarioDTO> itinerarios) {
        try {
            return ItinerarioConverter.listEntity2DTO(viajeroLogic.replaceItinerarios(ItinerarioConverter.listDTO2Entity(itinerarios), viajeroId));
        } catch (BusinessLogicException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            throw new WebApplicationException(ex.getLocalizedMessage(), ex, Response.Status.BAD_REQUEST);
        }
    }

    /**
     * Desasocia un Itinerario existente de un Viajero existente
     *
     * @param viajeroId Identificador del objeto de Viajero
     * @param itinerarioId Identificador del objeto de Itinerario
     * @generated
     *
    @DELETE
    @Path("{viajeroId: \\d+}/itinerarios/{itinerarioId: \\d+}")
    public void removeItinerarios(@PathParam("viajeroId") Long viajeroId, @PathParam("itinerarioId") Long itinerarioId) {
        viajeroLogic.removeItinerario(viajeroId, itinerarioId);
    }
    */
}
