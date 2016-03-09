package co.edu.uniandes.nullpointer.rest.tripulator.resources;

import co.edu.uniandes.nullpointer.rest.tripulator.datos.SitioDTO;
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
 * @author Daniel Delgado
 */
@Path("/sitios")
public class SitioResource {
    @GET
    public List<SitioDTO> getSitios(){
        return null;
    }
    
    /**
     * Obtiene un sitio
     * @param id identificador del sitio.
     * @return sitio encontrado.
     * @throws co.edu.uniandes.nullpointer.rest.tripulator.exceptions.
     * TripulatorLogicException cuando el sitio no existe
     */
    @GET
    @Path("{id: \\d+}")
    public SitioDTO getItinerario(@PathParam("id") Long id) throws TripulatorLogicException{
        return null;
    }
    
        /**
     * Agrega un sitio
     * @param sitio viajerp a agregar
     * @return datos del sitio a agregar
     * @throws co.edu.uniandes.nullpointer.rest.tripulator.exceptions.TripulatorLogicException
     */
    @POST
    public SitioDTO createItinerario(SitioDTO sitio) throws TripulatorLogicException {
        return null;
    }
    
    /**
     * Actualiza los datos de un sitio.
     * @param id identificador del sitio a modificar
     * @param Sitio sitio a modificar
     * @return datos del viajerp modificado
     * @throws TripulatorLogicException cuando no existe un sitio con el id suministrado
     */
    @PUT
    @Path("{id: \\d+}")
    public SitioDTO updateSitio(@PathParam("id") Long id, SitioDTO sitio) throws TripulatorLogicException {
        return null;
    }
    
    /**
     * Elimina los datos de un sitio
     * @param id identificador del sitio a eliminar
     * @throws TripulatorLogicException cuando no existe un sitio con el id suministrado
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteSitio(@PathParam("id") Long id) throws TripulatorLogicException {
    	//itinerarioLogic.deleteCity(id);
    }
}
