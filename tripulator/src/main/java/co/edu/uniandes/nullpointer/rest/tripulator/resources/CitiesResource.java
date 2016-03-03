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
 * @author Juan Sebastian Cardona
 */
@Path("/viajeros")
public class ViajeroResource {
    @GET
    public List<ViajeroDTO> getViajeros(){
        return null;
    }
    
    /**
     * Obtiene un viajero
     * @param id identificador del viajero.
     * @return viajero encontrado.
     * @throws co.edu.uniandes.nullpointer.rest.tripulator.exceptions.
     * TripulatorLogicException cuando el viajero no existe
     */
    @GET
    @Path("{id: \\d+}")
    public ViajeroDTO getItinerario(@PathParam("id") Long id) throws TripulatorLogicException{
        return null;
    }
    
        /**
     * Agrega un viajero
     * @param viajero viajerp a agregar
     * @return datos del viajero a agregar
     * @throws co.edu.uniandes.nullpointer.rest.tripulator.exceptions.TripulatorLogicException
     */
    @POST
    public ViajeroDTO createItinerario(ViajeroDTO viajero) throws TripulatorLogicException {
        return null;
    }
    
    /**
     * Actualiza los datos de un viajero.
     * @param id identificador del viajero a modificar
     * @param viajero viajero a modificar
     * @return datos del viajerp modificado
     * @throws TripulatorLogicException cuando no existe un viajero con el id suministrado
     */
    @PUT
    @Path("{id: \\d+}")
    public ViajeroDTO updateViajero(@PathParam("id") Long id, ViajeroDTO viajero) throws TripulatorLogicException {
        return null;
    }
    
    /**
     * Elimina los datos de un viajero
     * @param id identificador del viajero a eliminar
     * @throws TripulatorLogicException cuando no existe un viajero con el id suministrado
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteViajero(@PathParam("id") Long id) throws TripulatorLogicException {
    	//itinerarioLogic.deleteCity(id);
    }
}
