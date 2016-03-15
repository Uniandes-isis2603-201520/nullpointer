package co.edu.uniandes.nullpointer.rest.tripulator.mocks;

/**
 * Mock del recurso Evento (Mock del servicio REST)
 *
 * @author Asistente
 */
import co.edu.uniandes.nullpointer.rest.tripulator.dtos.EventoDTO;
import co.edu.uniandes.nullpointer.rest.tripulator.exceptions.TripulatorLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 * Mock del recurso Evento (Mock del servicio REST)
 */
@Named
@ApplicationScoped
public class EventoLogicMock {

    // objeto para presentar logs de las operaciones
    private final static Logger logger = Logger.getLogger(EventoLogicMock.class.getName());

    // listado de eventos
    private static ArrayList<EventoDTO> eventos;

    /**
     * Constructor. Crea los datos de ejemplo.
     */
    public EventoLogicMock() {

        if (eventos == null) {
            eventos = new ArrayList<>();
            eventos.add(new EventoDTO(new Long(1), "Concierto",
                    "http://www.shock.co/sites/default/files/content_files/2015_11/article/los-rolling-stone-en-colombia.jpg",
                    EventoDTO.tipoE, new Long("1457640000000"), new Long("1457650800000"), "Un gran concierto que no te puedes perder"));
            eventos.add(new EventoDTO(new Long(2), "Monserrate",
                    "http://stellamaris.com.co/images/monserrate.jpg",
                    EventoDTO.tipoS, null, null, "Un hermoso sitio que no debes perder"));
            eventos.add(new EventoDTO(new Long(3), "La Mambanegra - Carpa Cabaret",
                    "https://res.cloudinary.com/civico/image/upload/c_fill,f_auto,fl_lossy,h_368,w_1122/v1456532162/entity/event_image/file/206/000/56d0eabf2f41f384b6000206.jpg",
                    EventoDTO.tipoE, new Long("1457640000000"), new Long("1457650800000"), "Liderada por el mítico Callegüeso, La Mambanegra es una poderosa orquesta de música latina. Su veneno tiene como sustancia la salsa dura y callejera, integrada a elementos del funk y el hip-hop.\n" +
"\n" +
"Este concierto hace parte del cartel internacional del Festival de teatro."));

        }

        // indica que se muestren todos los mensajes
        logger.setLevel(Level.INFO);

        // muestra información 
        logger.info("Inicializa la lista de eventos");
        logger.info("eventos" + eventos);
    }

    /**
     * Obtiene el listado de eventos.
     *
     * @return lista de eventos
     * @throws TripulatorLogicException cuando no existe la lista en memoria
     */
    public List<EventoDTO> getEventos() throws TripulatorLogicException {
        if (eventos == null) {
            logger.severe("Error interno: lista de eventos no existe.");
            throw new TripulatorLogicException("Error interno: lista de eventos no existe.");
        }

        logger.info("retornando todos los eventos");
        return eventos;
    }

    /**
     * Obtiene un evento
     *
     * @param id identificador del evento
     * @return evento encontrada
     * @throws EventoLogicException cuando el evento no existe
     */
    public EventoDTO getEvento(Long id) throws TripulatorLogicException {
        logger.info("recibiendo solicitud de evento con id " + id);

        // busca el evento con el id suministrado
        for (EventoDTO evento : eventos) {
            if (Objects.equals(evento.getId(), id)) {
                logger.info("retornando evento " + evento);
                return evento;
            }
        }

        // si no encuentra el evento
        logger.severe("No existe evento con ese id");
        throw new TripulatorLogicException("No existe evento con ese id");
    }

    /**
     * Agrega un evento a la lista.
     *
     * @param newEvento evento a adicionar
     * @throws TripulatorLogicException cuando ya existe un evento con el id
     * suministrado
     * @return evento agregada
     */
    public EventoDTO createEvento(EventoDTO newEvento) throws TripulatorLogicException {
        logger.info("recibiendo solicitud de agregar evento " + newEvento);

        // el nuevo evento tiene id ?
        if (newEvento.getId() != null) {
            // busca el evento con el id suministrado
            for (EventoDTO city : eventos) {
                // si existe un evento con ese id
                if (Objects.equals(city.getId(), newEvento.getId())) {
                    logger.severe("Ya existe un evento con ese id");
                    throw new TripulatorLogicException("Ya existe un evento con ese id");
                }
            }

            // el nuevo evento no tiene id ? 
        } else {

            // genera un id para el evento
            logger.info("Generando id para el nuevo evento");
            long newId = 1;
            for (EventoDTO e : eventos) {
                if (newId <= e.getId()) {
                    newId = e.getId() + 1;
                }
            }
            newEvento.setId(newId);
        }

        // agrega el evento
        logger.info("agregando evento " + newEvento);
        eventos.add(newEvento);
        return newEvento;
    }

    /**
     * Actualiza los datos de un evento
     *
     * @param id identificador del evento a modificar
     * @param nuevo evento a modificar
     * @return datos del evento modificado
     * @throws TripulatorLogicException cuando no existe un evento con el id
     * suministrado
     */
    public EventoDTO updateEvento(Long id, EventoDTO nuevo) throws TripulatorLogicException {
        logger.info("recibiendo solictud de modificar evento " + nuevo);

        // busca el evento con el id suministrado
        for (EventoDTO e : eventos) {
            if (Objects.equals(e.getId(), id)) {

                // modifica el evento
                e.setId(nuevo.getId());
                e.setDescription(nuevo.getDescription());
                e.setEnd(nuevo.getEnd());
                e.setStart(nuevo.getStart());
                e.setImage(nuevo.getImage());
                e.setTitle(nuevo.getTitle());
                e.setType(nuevo.getType());
                // retorna el evento modificada
                logger.info("Modificando evento " + e);
                return e;
            }
        }

        // no encontró el evento con ese id ?
        logger.severe("No existe un evento con ese id");
        throw new TripulatorLogicException("No existe un evento con ese id");
    }

    /**
     * Elimina los datos de un evento
     *
     * @param id identificador del evento a eliminar
     * @throws TripulatorLogicException cuando no existe un evento con el id
     * suministrado
     */
    public void deleteEvento(Long id) throws TripulatorLogicException {
        logger.info("recibiendo solictud de eliminar evento con id " + id);

        // busca el evento con el id suministrado
        for (EventoDTO e : eventos) {
            if (Objects.equals(e.getId(), id)) {

                // elimina el evento
                logger.info("eliminando evento " + e);
                eventos.remove(e);
                return;
            }
        }

        // no encontró el evento con ese id ?
        logger.severe("No existe un evento con ese id");
        throw new TripulatorLogicException("No existe un evento con ese id");
    }
}
