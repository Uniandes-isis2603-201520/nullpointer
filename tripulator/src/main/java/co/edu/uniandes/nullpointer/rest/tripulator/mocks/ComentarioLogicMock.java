package co.edu.uniandes.nullpointer.rest.tripulator.mocks;

/**
 * Mock del recurso Comentario (Mock del servicio REST)
 *
 * @author Asistente
 */
import co.edu.uniandes.nullpointer.rest.tripulator.dtos.ComentarioDTO;
import co.edu.uniandes.nullpointer.rest.tripulator.exceptions.TripulatorLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 * Mock del recurso Comentario (Mock del servicio REST)
 */
@Named
@ApplicationScoped
public class ComentarioLogicMock {

    // objeto para presentar logs de las operaciones
    private final static Logger logger = Logger.getLogger(ComentarioLogicMock.class.getName());

    // listado de comentarios
    private static ArrayList<ComentarioDTO> comentarios;

    /**
     * Constructor. Crea los datos de ejemplo.
     */
    public ComentarioLogicMock() {

        if (comentarios == null) {
            comentarios = new ArrayList<>();

        }

        // indica que se muestren todos los mensajes
        logger.setLevel(Level.INFO);

        // muestra información 
        logger.info("Inicializa la lista de comentarios");
        logger.info("comentarios" + comentarios);
    }

    /**
     * Obtiene el listado de comentarios.
     *
     * @return lista de comentarios
     * @throws TripulatorLogicException cuando no existe la lista en memoria
     */
    public List<ComentarioDTO> getComentarios() throws TripulatorLogicException {
        if (comentarios == null) {
            logger.severe("Error interno: lista de comentarios no existe.");
            throw new TripulatorLogicException("Error interno: lista de comentarios no existe.");
        }

        logger.info("retornando todos los comentarios");
        return comentarios;
    }

    /**
     * Obtiene un comentario
     *
     * @param id identificador del comentario
     * @return comentario encontrado
     * @throws TripulatorLogicException cuando el comentario no existe
     */
    public ComentarioDTO getComentario(Long id) throws TripulatorLogicException {
        logger.info("recibiendo solicitud de comentario con id " + id);

        // busca el comentario con el id suministrado
        for (ComentarioDTO comentario : comentarios) {
            if (Objects.equals(comentario.getId(), id)) {
                logger.info("retornando comentario " + comentario);
                return comentario;
            }
        }

        // si no encuentra el comentario
        logger.severe("No existe comentario con ese id");
        throw new TripulatorLogicException("No existe comentario con ese id");
    }

    /**
     * Agrega un comentario a la lista.
     *
     * @param newComentario comentario a adicionar
     * @throws TripulatorLogicException cuando ya existe un comentario con el id
     * suministrado
     * @return comentario agregado
     */
    public ComentarioDTO createComentario(ComentarioDTO newComentario) throws TripulatorLogicException {
        logger.info("recibiendo solicitud de agregar comentario " + newComentario);

        // el nuevo comentario tiene id ?
        if (newComentario.getId() != null) {
            // busca el comentario con el id suministrado
            for (ComentarioDTO c : comentarios) {
                // si existe un comentario con ese id
                if (Objects.equals(c.getId(), newComentario.getId())) {
                    logger.severe("Ya existe un comentario con ese id");
                    throw new TripulatorLogicException("Ya existe un comentario con ese id");
                }
            }

            // el nuevo comentario no tiene id ? 
        } else {

            // genera un id para el comentario
            logger.info("Generando id paa la nuevo comentario");
            long newId = 1;
            for (ComentarioDTO c : comentarios) {
                if (newId <= c.getId()) {
                    newId = c.getId() + 1;
                }
            }
            newComentario.setId(newId);
        }

        // agrega un comentario
        logger.info("agregando comentario " + newComentario);
        comentarios.add(newComentario);
        return newComentario;
    }

    /**
     * Actualiza los datos de un comentario
     *
     * @param id identificador del comentario a modificar
     * @param nuevo comentario a modificar
     * @return datos del comentario modificado
     * @throws TripulatorLogicException cuando no existe un comentario con el id
     * suministrado
     */
    public ComentarioDTO updateComentario(Long id, ComentarioDTO nuevo) throws TripulatorLogicException {
        logger.info("recibiendo solictud de modificar comentario " + nuevo);

        // busca el comentario con el id suministrado
        for (ComentarioDTO e : comentarios) {
            if (Objects.equals(e.getId(), id)) {

                // modifica el comentario
                e.setId(nuevo.getId());
                e.setDate(nuevo.getDate());
                e.setComment(nuevo.getComment());
                e.setStars(nuevo.getStars());
                e.setUser(nuevo.getUser());
                e.setUserPhoto(nuevo.getUserPhoto());
                // retorna el comentario modificada
                logger.info("Modificando comentario " + e);
                return e;
            }
        }

        // no encontró el comentario con ese id ?
        logger.severe("No existe un comentario con ese id");
        throw new TripulatorLogicException("No existe un comentario con ese id");
    }

    /**
     * Elimina los datos de un comentario
     *
     * @param id identificador del comentario a eliminar
     * @throws TripulatorLogicException cuando no existe un comentario con el id
     * suministrado
     */
    public void deleteComentario(Long id) throws TripulatorLogicException {
        logger.info("recibiendo solictud de eliminar comentario con id " + id);

        // busca el comentario con el id suministrado
        for (ComentarioDTO e : comentarios) {
            if (Objects.equals(e.getId(), id)) {

                // elimina el comentario
                logger.info("eliminando comentario " + e);
                comentarios.remove(e);
                return;
            }
        }

        // no encontró el cometario con ese id ?
        logger.severe("No existe un comentario con ese id");
        throw new TripulatorLogicException("No existe un comentario con ese id");
    }
}
