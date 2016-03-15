package co.edu.uniandes.nullpointer.rest.tripulator.mocks;

/**
 * Mock del recurso Ciudades (Mock del servicio REST)
 *
 * @author Asistente
 */
import co.edu.uniandes.nullpointer.rest.tripulator.dtos.ViajeroDTO;
import co.edu.uniandes.nullpointer.rest.tripulator.exceptions.TripulatorLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 * Mock del recurso Ciudades (Mock del servicio REST)
 */
@Named
@ApplicationScoped
public class ViajeroLogicMock {

    // objeto para presentar logs de las operaciones
    private final static Logger logger = Logger.getLogger(ViajeroLogicMock.class.getName());

    // listado de viajeroes
    private static ArrayList<ViajeroDTO> viajeros;

    /**
     * Constructor. Crea los datos de ejemplo.
     */
    public ViajeroLogicMock() {

        if (viajeros == null) {
            viajeros = new ArrayList<>();
            ViajeroDTO add1 = new ViajeroDTO(1L, "Juan", "Cardona", "hola", "js.cardona12");
            ViajeroDTO add2 = new ViajeroDTO(1L, "Antonio", "De La Vega", "hola", "aj.dlv10");
            ViajeroDTO add3 = new ViajeroDTO(1L, "Felipe", "Quiroga", "hola", "jf.quitoga10");
            viajeros.add(add1);
            viajeros.add(add2);
            viajeros.add(add3);

        }

        // indica que se muestren todos los mensajes
        logger.setLevel(Level.INFO);

        // muestra información 
        logger.info("Inicializa la lista de viajeros");
        logger.info("viejaros" + viajeros);
    }

    /**
     * Obtiene el listado de viajeros.
     *
     * @return lista de viajeros
     * @throws TripulatorLogicException cuando no existe la lista en memoria
     */
    public List<ViajeroDTO> getViajeros() throws TripulatorLogicException {
        if (viajeros == null) {
            logger.severe("Error interno: lista de viajeros no existe.");
            throw new TripulatorLogicException("Error interno: lista de viajeros no existe.");
        }

        logger.info("retornando todos los viajeros");
        return viajeros;
    }

    /**
     * Obtiene un viajero
     *
     * @param id identificador de la viajero
     * @return viajero encontrado
     * @throws TripulatorLogicException cuando el viajero no existe
     */
    public ViajeroDTO getViajero(Long id) throws TripulatorLogicException {
        logger.info("recibiendo solicitud del viajro con id " + id);

        // busca la viajero con el id suministrado
        for (ViajeroDTO viajero : viajeros) {
            if (Objects.equals(viajero.getId(), id)) {
                logger.info("retornando viajero " + viajero);
                return viajero;
            }
        }

        // si no encuentra el viajero
        logger.severe("No existe viajero con ese id");
        throw new TripulatorLogicException("No existe viajero con ese id");
    }

    /**
     * Agrega un viajero a la lista.
     *
     * @param newViajero viajero a adicionar
     * @throws TripulatorLogicException cuando ya existe un viajero con el id
     * suministrado
     * @return viajero agregado
     */
    public ViajeroDTO createViajero(ViajeroDTO newViajero) throws TripulatorLogicException {
        logger.info("recibiendo solicitud de agregar viajero " + newViajero);

        // la nueva viajero tiene id ?
        if (newViajero.getId() != null) {
            // busca la viajero con el id suministrado
            for (ViajeroDTO viajero : viajeros) {
                // si existe un viajero con ese id
                if (Objects.equals(viajero.getId(), newViajero.getId())) {
                    logger.severe("Ya existe un viajero con ese id");
                    throw new TripulatorLogicException("Ya existe un viajero con ese id");
                }
            }

            // la nueva viajero no tiene id ? 
        } else {

            // genera un id para la viajero
            logger.info("Generando id para el nuevo viajero");
            long newId = 1;
            for (ViajeroDTO viajero : viajeros) {
                if (newId <= viajero.getId()) {
                    newId = viajero.getId() + 1;
                }
            }
            newViajero.setId(newId);
        }

        // agrega la viajero
        logger.info("agregando viajero " + newViajero);
        viajeros.add(newViajero);
        return newViajero;
    }

    /**
     * Actualiza los datos de un viajero
     *
     * @param id identificador de la viajero a modificar
     * @param viajero viajero a modificar
     * @return datos de la viajero modificada
     * @throws TripulatorLogicException cuando no existe un viajero con el id
     * suministrado
     */
    public ViajeroDTO updateViajero(Long id, ViajeroDTO updatedViajero) throws TripulatorLogicException {
        logger.info("recibiendo solictud de modificar viajero " + updatedViajero);

        // busca la viajero con el id suministrado
        for (ViajeroDTO viajero : viajeros) {
            if (Objects.equals(viajero.getId(), id)) {

                // modifica la viajero
                viajero.setId(updatedViajero.getId());
                viajero.setNombre(updatedViajero.getNombre());
                viajero.setApellido(updatedViajero.getApellido());
                viajero.setContraseña(updatedViajero.getContraseña());
                viajero.setUsuario(updatedViajero.getUsuario());

                // retorna la viajero modificada
                logger.info("Modificando viajero " + viajero);
                return viajero;
            }
        }

        // no encontró la viajero con ese id ?
        logger.severe("No existe un viajero con ese id");
        throw new TripulatorLogicException("No existe un viajero con ese id");
    }

    /**
     * Elimina los datos de un viajero
     *
     * @param id identificador de la viajero a eliminar
     * @throws TripulatorLogicException cuando no existe un viajero con el id
     * suministrado
     */
    public void deleteViajero(Long id) throws TripulatorLogicException {
        logger.info("recibiendo solictud de eliminar viajero con id " + id);

        // busca la viajero con el id suministrado
        for (ViajeroDTO viajero : viajeros) {
            if (Objects.equals(viajero.getId(), id)) {

                // elimina la viajero
                logger.info("eliminando viajero " + viajero);
                viajeros.remove(viajero);
                return;
            }
        }

        // no encontró la viajero con ese id ?
        logger.severe("No existe un viajero con ese id");
        throw new TripulatorLogicException("No existe un viajero con ese id");
    }
}
