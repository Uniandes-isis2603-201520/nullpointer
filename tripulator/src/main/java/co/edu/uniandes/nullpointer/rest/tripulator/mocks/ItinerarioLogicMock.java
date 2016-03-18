/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.nullpointer.rest.tripulator.mocks;

import co.edu.uniandes.nullpointer.rest.tripulator.dtos.ItinerarioDTO;
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
 *
 * @author Antonio de la Vega
 */
@Named
@Singleton
public class ItinerarioLogicMock {

    private final static Logger logger = Logger.getLogger(ItinerarioLogicMock.class.getName());

    private static HashMap<Long, ArrayList<ItinerarioDTO>> mapItinerarios;

    public ItinerarioLogicMock() {
        if (mapItinerarios == null) {
            mapItinerarios = new HashMap<>();
            ArrayList<ItinerarioDTO> itinerarios = new ArrayList<>();
            itinerarios.add(new ItinerarioDTO(1L, "Dubai", new Date(), new Date(),
                    new ArrayList(), new ArrayList(), new ArrayList()));
            itinerarios.add(new ItinerarioDTO(2L, "London", new Date(), new Date(),
                    new ArrayList(), new ArrayList(), new ArrayList()));
            itinerarios.add(new ItinerarioDTO(3L, "Eurotrip", new Date(), new Date(),
                    new ArrayList(), new ArrayList(), new ArrayList()));
            mapItinerarios.put(1L, itinerarios);
        }

        // indica que se muestren todos los mensajes
        logger.setLevel(Level.INFO);

        // muestra información 
        logger.info("Inicializa el hashmap");
        logger.log(Level.INFO, "mapItinerarios{0}", mapItinerarios);
    }

    public List<ItinerarioDTO> getItinerarios(Long idUsuario) throws TripulatorLogicException {
        if (mapItinerarios == null) {
            logger.severe("Error interno: lista de itinerarios no existe.");
            throw new TripulatorLogicException("Error interno: lista de itinerarios no existe.");
        }

        logger.log(Level.INFO, "retornando todos los itinerarios del usuario: {0}", idUsuario);
        return mapItinerarios.get(idUsuario);
    }

    public ItinerarioDTO getItinerario(Long idUsuario, Long id) throws TripulatorLogicException {
        logger.log(Level.INFO, "recibiendo solicitud de un itinerario con id {0}", id);
        ArrayList<ItinerarioDTO> itinerarios = mapItinerarios.get(idUsuario);
        // busca el itinerario con el id suministrado
        for (ItinerarioDTO itinerario : itinerarios) {
            if (Objects.equals(itinerario.getId(), id)) {
                logger.log(Level.INFO, "retornando itinerario {0}", itinerario);
                return itinerario;
            }
        }

        // si no encuentra el itinerario
        logger.severe("No existe un itinerario con ese id");
        throw new TripulatorLogicException("No existe un itinerario con ese id");
    }

    public ItinerarioDTO createItinerario(Long idUsuario, ItinerarioDTO newItinerario) throws TripulatorLogicException {
        logger.log(Level.INFO, "recibiendo solicitud de agregar itinerario {0}", newItinerario);
        if (!mapItinerarios.containsKey(idUsuario)) {
            mapItinerarios.put(idUsuario, new ArrayList());
        }
        // el nuevo itinerario tiene id ?
        if (newItinerario.getId() != null) {
            // busca el itinerario con el id suministrado
            ArrayList<ItinerarioDTO> itinerarios = mapItinerarios.get(idUsuario);
            for (ItinerarioDTO itinerario : itinerarios) {
                // si existe un itinerario con ese id
                if (Objects.equals(itinerario.getId(), newItinerario.getId())) {
                    logger.severe("Ya existe un itinerario con ese id");
                    throw new TripulatorLogicException("Ya existe un itinerario con ese id");
                }
            }

            // el nuevo itinerario no tiene id ? 
        } else {

            // genera un id para el itinerario
            logger.info("Generando el id para el nuevo itinerario");
            long newId = 1;
            ArrayList<ItinerarioDTO> itinerarios = mapItinerarios.get(idUsuario);
            for (ItinerarioDTO itinerario : itinerarios) {
                if (newId <= itinerario.getId()) {
                    newId = itinerario.getId() + 1;
                }
            }
            newItinerario.setId(newId);
        }

        // agrega el itinerario
        logger.log(Level.INFO, "agregando itinerario {0}", newItinerario);
        mapItinerarios.get(idUsuario).add(newItinerario);
        return newItinerario;
    }

    public ItinerarioDTO updateItinerario(Long idUsuario,
            Long id, ItinerarioDTO updatedItinerario) throws TripulatorLogicException {
        logger.log(Level.INFO, "recibiendo solictud de modificar un itinerario {0}", updatedItinerario);
        ArrayList<ItinerarioDTO> itinerarios = mapItinerarios.get(idUsuario);
        // busca el itinerario con el id suministrado
        for (ItinerarioDTO itinerario : itinerarios) {
            if (Objects.equals(itinerario.getId(), id)) {

                // modifica el itinerario
                itinerario.setNombre(updatedItinerario.getNombre());
                itinerario.setFechaFin(updatedItinerario.getFechaFin());
                itinerario.setFechaInicio(updatedItinerario.getFechaInicio());
                itinerario.setMapa(updatedItinerario.getMapa());
                itinerario.setMultimedia(updatedItinerario.getMultimedia());
                itinerario.setPlanDias(updatedItinerario.getPlanDias());

                // retorna el itinerario modificado.
                logger.log(Level.INFO, "Modificando itinerario {0}", itinerario);
                return itinerario;
            }
        }

        // no encontró el itineario con ese id ?
        logger.severe("No existe un itinerario con ese id");
        throw new TripulatorLogicException("No existe un itinerario con ese id");
    }

    public void deleteItinerario(Long idUsuario, Long id) throws TripulatorLogicException {
        logger.log(Level.INFO, "recibiendo solictud de eliminar itinerario con id {0}", id);
        ArrayList<ItinerarioDTO> itinerarios = mapItinerarios.get(idUsuario);
        // busca el itinerario con el id suministrado
        for (ItinerarioDTO itinerario : itinerarios) {
            if (Objects.equals(itinerario.getId(), id)) {

                // elimina el itinerario
                logger.log(Level.INFO, "eliminando itinerario {0}", itinerario);
                itinerarios.remove(itinerario);
                return;
            }
        }

        // no encontró el itinerario con ese id ?
        logger.severe("No existe un itinerario con ese id");
        throw new TripulatorLogicException("No existe un itinerario con ese id");
    }
}
