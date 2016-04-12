/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.nullpointer.rest.tripulator.resources;

import co.edu.uniandes.csw.tripulator.ejbs.ComentarioLogic;
import co.edu.uniandes.nullpointer.rest.tripulator.dtos.ComentarioDTO;
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
 * @author da.delgado11
 */
@Path("/eventos/{idEvento}/comentarios")
@Produces("application/json")
@RequestScoped
public class ComentarioResource {

    
	@Inject
	ComentarioLogic comentarioLogic;

	/**
	 * Obtiene el listado de comentarios. 
	 * @return lista de comentarios
	 * @throws TripulatorLogicException excepción retornada por la lógica  
	 */
    @GET
    public List<ComentarioDTO> getComentarios(@PathParam("idEvento")Long idEvento) throws TripulatorLogicException {
        return comentarioLogic.getComentarios(idEvento);
    }

    /**
     * Obtiene un comentario
     * @param id identificador de el comentario
     * @return comentario encontrado
     * @throws TripulatorLogicException cuando el comentario no existe
     */
    @GET
    @Path("{id}")
    public ComentarioDTO getComentario(@PathParam("idEvento")Long idEvento,
            @PathParam("id") Long id) throws TripulatorLogicException {
        return comentarioLogic.getComentario(idEvento,id);
    }

    /**
     * Agrega un comentario
     * @param Comentario comentario a agregar
     * @return datos de el comentario a agregar
     * @throws TripulatorLogicException cuando ya existe un comentario con el id suministrado
     */
    @POST
    public ComentarioDTO createComentario(@PathParam("idEvento")Long idEvento
            ,ComentarioDTO comentario) throws TripulatorLogicException {
        return comentarioLogic.createComentario(idEvento, comentario);
    }

    /**
     * Actualiza los datos de un comentario
     * @param id identificador de el comentario a modificar
     * @param comentario comentario a modificar
     * @return datos de el comentario modificada 
     * @throws TripulatorLogicException cuando no existe un comentario con el id suministrado
     */
    @PUT
    @Path("{id}")
    public ComentarioDTO updateComentario(@PathParam("idEvento")Long idEvento,
            @PathParam("id") Long id, ComentarioDTO comentario) throws TripulatorLogicException {
        return comentarioLogic.updateComentario(idEvento, id, comentario);
    }

    /**
     * Elimina los datos de un comentario
     * @param id identificador de el comentario a eliminar
     * @throws TripulatorLogicException cuando no existe un comentario con el id suministrado
     */
    @DELETE
    @Path("{id}")
    public void deleteComentario(@PathParam("idEvento")Long idEvento,
            @PathParam("id") Long id) throws TripulatorLogicException {
    	comentarioLogic.deleteComentario(idEvento, id);
    }
}
