/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.nullpointer.rest.tripulator.resources;

import co.edu.uniandes.nullpointer.rest.tripulator.dtos.ComentarioDTO;
import co.edu.uniandes.nullpointer.rest.tripulator.exceptions.TripulatorLogicException;
import co.edu.uniandes.nullpointer.rest.tripulator.mocks.ComentarioLogicMock;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 *
 * @author jd.fandino10
 */
@Path("/comentarios")
public class ComentarioResource {

    
	@Inject
	ComentarioLogicMock comentarioLogic;

	/**
	 * Obtiene el listado de comentarios. 
	 * @return lista de comentarios
	 * @throws TripulatorLogicException excepción retornada por la lógica  
	 */
    @GET
    public List<ComentarioDTO> getComentarios() throws TripulatorLogicException {
        return comentarioLogic.getComentarios();
    }

    /**
     * Obtiene un comentario
     * @param id identificador de el comentario
     * @return comentario encontrado
     * @throws TripulatorLogicException cuando el comentario no existe
     */
    @GET
    @Path("{id: \\d+}")
    public ComentarioDTO getComentario(@PathParam("id") Long id) throws TripulatorLogicException {
        return comentarioLogic.getComentario(id);
    }

    /**
     * Agrega un comentario
     * @param Comentario comentario a agregar
     * @return datos de el comentario a agregar
     * @throws TripulatorLogicException cuando ya existe un comentario con el id suministrado
     */
    @POST
    public ComentarioDTO createComentario(ComentarioDTO comentario) throws TripulatorLogicException {
        return comentarioLogic.createComentario(comentario);
    }

    /**
     * Actualiza los datos de un comentario
     * @param id identificador de el comentario a modificar
     * @param comentario comentario a modificar
     * @return datos de el comentario modificada 
     * @throws TripulatorLogicException cuando no existe un comentario con el id suministrado
     */
    @PUT
    @Path("{id: \\d+}")
    public ComentarioDTO updateComentario(@PathParam("id") Long id, ComentarioDTO comentario) throws TripulatorLogicException {
        return comentarioLogic.updateComentario(id, comentario);
    }

    /**
     * Elimina los datos de un comentario
     * @param id identificador de el comentario a eliminar
     * @throws TripulatorLogicException cuando no existe un comentario con el id suministrado
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteComentario(@PathParam("id") Long id) throws TripulatorLogicException {
    	comentarioLogic.deleteComentario(id);
    }
}
