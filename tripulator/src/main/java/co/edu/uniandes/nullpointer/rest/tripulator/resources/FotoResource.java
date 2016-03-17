/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.nullpointer.rest.tripulator.resources;

import co.edu.uniandes.nullpointer.rest.tripulator.dtos.FotoDTO;
import co.edu.uniandes.nullpointer.rest.tripulator.exceptions.TripulatorLogicException;
import co.edu.uniandes.nullpointer.rest.tripulator.mocks.FotoLogicMock;
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
 * @author Jose Quiroga
 */
@Path("/viajeros/:id/itinerarios/:id/fotos")
@Produces("application/json")
@RequestScoped
public class FotoResource {
    
    @Inject
	FotoLogicMock fotoLogic;
    
    @GET
    public List<FotoDTO> getFotos() throws TripulatorLogicException{
        return fotoLogic.getFotos();
    }
    
    /**
     * Obtiene una foto
     * @param id identificador de la foto.
     * @return foto encontrada.
     * @throws co.edu.uniandes.nullpointer.rest.tripulator.exceptions.
     * TripulatorLogicException cuando la foto no existe
     */
    @GET
    @Path("{id: \\d+}")
    public FotoDTO getFoto(@PathParam("id") Long id) throws TripulatorLogicException{
        return fotoLogic.getFoto(id);
    }
    
        /**
     * Agrega fotos
     * @param foto foto a agregar
     * @return datos de la foto a agregar
     * @throws co.edu.uniandes.nullpointer.rest.tripulator.exceptions.TripulatorLogicException
     */
    @POST
    public FotoDTO createFotos(FotoDTO foto) throws TripulatorLogicException {
        return fotoLogic.createFoto(foto);
    }
    
    
    /**
     * Elimina los datos de una foto
     * @param id identificador de la foto a eliminar
     * @throws TripulatorLogicException cuando no existe un itinerario con el id suministrado
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteFoto(@PathParam("id") Long id) throws TripulatorLogicException {
        fotoLogic.deleteFoto(id);
    	
    }
}
