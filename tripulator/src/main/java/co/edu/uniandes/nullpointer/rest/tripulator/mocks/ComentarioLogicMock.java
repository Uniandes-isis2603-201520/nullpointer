package co.edu.uniandes.nullpointer.rest.tripulator.mocks;

/**
 * Mock del recurso Comentario (Mock del servicio REST)
 *
 * @author Asistente
 */
import co.edu.uniandes.nullpointer.rest.tripulator.dtos.ComentarioDTO;
import co.edu.uniandes.nullpointer.rest.tripulator.exceptions.TripulatorLogicException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Mock del recurso Comentario (Mock del servicio REST)
 */
@Named
@Singleton
public class ComentarioLogicMock {

    // objeto para presentar logs de las operaciones
    private final static Logger logger = Logger.getLogger(ComentarioLogicMock.class.getName());

    // listado de comentarios
    private static HashMap<Long, ArrayList<ComentarioDTO>> mapComentarios;

    /**
     * Constructor. Crea los datos de ejemplo.
     */
    public ComentarioLogicMock() {

        if (mapComentarios == null) {
            mapComentarios = new HashMap<>();
            ArrayList<ComentarioDTO> comentarios = new ArrayList<>();
            comentarios.add(new ComentarioDTO(1L, "User1", "photo1", 4, "comentario1", 1L));
            comentarios.add(new ComentarioDTO(1L, "User2", "photo2", 5, "comentario2", 1L));
            comentarios.add(new ComentarioDTO(1L, "User3", "photo3", 3, "comentario3", 1L));
            mapComentarios.put(1L, comentarios);
        }

        // indica que se muestren todos los mensajes
        logger.setLevel(Level.INFO);

        // muestra información 
        logger.info("Inicializa el hashmap");
        logger.log(Level.INFO, "mapComentarios{0}", mapComentarios);
    }

    /**
     * Obtiene el listado de comentarios.
     *
     * @return lista de comentarios
     * @throws TripulatorLogicException cuando no existe la lista en memoria
     */
    /**
     * Obtiene el listado de comentarios.
     *
     * @param idEvento
     * @return lista de comentarios
     * @throws TripulatorLogicException cuando no existe la lista en memoria
     */
    public List<ComentarioDTO> getComentarios(Long idEvento) throws TripulatorLogicException {
        if (mapComentarios == null) {
            logger.severe("Error interno: lista de comentarios no existe.");
            throw new TripulatorLogicException("Error interno: lista de comentarios no existe.");
        }

        logger.log(Level.INFO, "retornando todos los comentarios del usuario: {0}", idEvento);
        return mapComentarios.get(idEvento);
    }

    /**
     * Obtiene un comentario
     *
     * @param id identificador del comentario
     * @return comentario encontrado
     * @throws TripulatorLogicException cuando el comentario no existe
     */
    public ComentarioDTO getComentario(Long idEvento, Long id) throws TripulatorLogicException {
        logger.log(Level.INFO, "recibiendo solicitud de un comentario con id {0}", id);
        ArrayList<ComentarioDTO> comentarios = mapComentarios.get(idEvento);
        // busca el comentario con el id suministrado
        for (ComentarioDTO comentario : comentarios) {
            if (Objects.equals(comentario.getId(), id)) {
                logger.log(Level.INFO, "retornando comentario {0}", comentario);
                return comentario;
            }
        }

        // si no encuentra el comentario
        logger.severe("No existe un comentario con ese id");
        throw new TripulatorLogicException("No existe un comentario con ese id");
    }

    /**
     * Agrega un comentario a la lista.
     *
     * @param newComentario comentario a adicionar
     * @throws TripulatorLogicException cuando ya existe un comentario con el id
     * suministrado
     * @return comentario agregado
     */
    public ComentarioDTO createComentario(Long idEvento, ComentarioDTO newComentario) throws TripulatorLogicException {
        logger.log(Level.INFO, "recibiendo solicitud de agregar comentario {0}", newComentario);
        if (!mapComentarios.containsKey(idEvento)) {
            mapComentarios.put(idEvento, new ArrayList());
        }
        // el nuevo comentario tiene id ?
        if (newComentario.getId() != null) {
            // busca el comentario con el id suministrado
            ArrayList<ComentarioDTO> comentarios = mapComentarios.get(idEvento);
            for (ComentarioDTO comentario : comentarios) {
                // si existe un comentario con ese id
                if (Objects.equals(comentario.getId(), newComentario.getId())) {
                    logger.severe("Ya existe un comentario con ese id");
                    throw new TripulatorLogicException("Ya existe un comentario con ese id");
                }
            }

            // el nuevo comentario no tiene id ? 
        } else {

            // genera un id para el comentario
            logger.info("Generando el id para el nuevo comentario");
            long newId = 1;
            ArrayList<ComentarioDTO> comentarios = mapComentarios.get(idEvento);
            for (ComentarioDTO comentario : comentarios) {
                if (newId <= comentario.getId()) {
                    newId = comentario.getId() + 1;
                }
            }
            newComentario.setId(newId);
        }

        // agrega el comentario
        logger.log(Level.INFO, "agregando comentario {0}", newComentario);
        mapComentarios.get(idEvento).add(newComentario);
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
    public ComentarioDTO updateComentario(Long idEvento, Long id, ComentarioDTO nuevo) throws TripulatorLogicException {
        logger.info("recibiendo solictud de modificar comentario " + nuevo);
        ArrayList<ComentarioDTO> comentarios = mapComentarios.get(idEvento);
        // busca el comentario con el id suministrado
        for (ComentarioDTO e : comentarios) {
            if (Objects.equals(e.getId(), id)) {

                // modifica el comentario
                e.setDate(nuevo.getDate());
                e.setComment(nuevo.getComment());
                e.setStars(nuevo.getStars());
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
    public void deleteComentario(Long idEvento, Long id) throws TripulatorLogicException {
        logger.info("recibiendo solictud de eliminar comentario con id " + id);
        ArrayList<ComentarioDTO> comentarios = mapComentarios.get(idEvento);

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
