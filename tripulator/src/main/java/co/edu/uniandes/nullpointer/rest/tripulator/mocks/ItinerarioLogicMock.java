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
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Antonio de la Vega
 */
public class ItinerarioLogicMock {
    
    private final static Logger LOGGER = Logger.getLogger(EventoLogicMock.class.getName());
    
    private static ArrayList<ItinerarioDTO> itinerarios;
    
    public ItinerarioLogicMock() {
        if (itinerarios == null) {
            itinerarios = new ArrayList<>();
            itinerarios.add(new ItinerarioDTO(1L, "Dubai", new Date(), new Date(),
                    new ArrayList(), new ArrayList(), new ArrayList()));
            itinerarios.add(new ItinerarioDTO(1L, "London", new Date(), new Date(),
                    new ArrayList(), new ArrayList(), new ArrayList()));
            itinerarios.add(new ItinerarioDTO(1L, "Eurotrip", new Date(), new Date(),
                    new ArrayList(), new ArrayList(), new ArrayList()));
        }

        // indica que se muestren todos los mensajes
        LOGGER.setLevel(Level.INFO);

        // muestra información 
        LOGGER.info("Inicializa la lista de itinerarios");
        LOGGER.log(Level.INFO, "itinerarios{0}", itinerarios);
    }
    
    public List<ItinerarioDTO> getItinerarios() throws TripulatorLogicException {
        if (itinerarios == null) {
            LOGGER.severe("Error interno: lista de itinerarios no existe.");
            throw new TripulatorLogicException("Error interno: lista de itinerarios no existe.");
        }
        
        LOGGER.info("retornando todos los itinerarios");
        return itinerarios;
    }
    
    public ItinerarioDTO getItinerario(Long id) throws TripulatorLogicException {
        LOGGER.log(Level.INFO, "recibiendo solicitud de un itinerario con id {0}", id);

        // busca el itinerario con el id suministrado
        for (ItinerarioDTO itinerario : itinerarios) {
            if (Objects.equals(itinerario.getId(), id)) {
                LOGGER.log(Level.INFO, "retornando itinerario {0}", itinerario);
                return itinerario;
            }
        }

        // si no encuentra el itinerario
        LOGGER.severe("No existe un itinerario con ese id");
        throw new TripulatorLogicException("No existe un itinerario con ese id");
    }
    
    public ItinerarioDTO createItinerario(ItinerarioDTO newItinerario) throws TripulatorLogicException {
        LOGGER.log(Level.INFO, "recibiendo solicitud de agregar ciudad {0}", newItinerario);

        // el nuevo itinerario tiene id ?
        if (newItinerario.getId() != null) {
            // busca el itinerario con el id suministrado
            for (ItinerarioDTO itinerario : itinerarios) {
                // si existe un itinerario con ese id
                if (Objects.equals(itinerario.getId(), newItinerario.getId())) {
                    LOGGER.severe("Ya existe un itinerario con ese id");
                    throw new TripulatorLogicException("Ya existe un itinerario con ese id");
                }
            }

            // el nuevo itinerario no tiene id ? 
        } else {

            // genera un id para el itinerario
            LOGGER.info("Generando el id para el nuevo itinerario");
            long newId = 1;
            for (ItinerarioDTO itinerario : itinerarios) {
                if (newId <= itinerario.getId()) {
                    newId = itinerario.getId() + 1;
                }
            }
            newItinerario.setId(newId);
        }

        // agrega el itinerario
        LOGGER.log(Level.INFO, "agregando itinerario {0}", newItinerario);
        itinerarios.add(newItinerario);
        return newItinerario;
    }
    
    public ItinerarioDTO updateItinerario(Long id, ItinerarioDTO updatedItinerario) throws TripulatorLogicException {
        LOGGER.log(Level.INFO, "recibiendo solictud de modificar un itinerario {0}", updatedItinerario);

        // busca el itinerario con el id suministrado
        for (ItinerarioDTO itinerario : itinerarios) {
            if (Objects.equals(itinerario.getId(), id)) {

                // modifica el itinerario
                itinerario.setId(updatedItinerario.getId());
                itinerario.setNombre(updatedItinerario.getNombre());
                itinerario.setFechaFin(updatedItinerario.getFechaFin());
                itinerario.setFechaInicio(updatedItinerario.getFechaInicio());
                itinerario.setMapa(updatedItinerario.getMapa());
                itinerario.setMultimedia(updatedItinerario.getMultimedia());
                itinerario.setPlanDias(updatedItinerario.getPlanDias());

                // retorna el itinerario modificado.
                LOGGER.log(Level.INFO, "Modificando itinerario {0}", itinerario);
                return itinerario;
            }
        }

        // no encontró el itineario con ese id ?
        LOGGER.severe("No existe un itinerario con ese id");
        throw new TripulatorLogicException("No existe un itinerario con ese id");
    }


    public void deleteCity(Long id) throws TripulatorLogicException {
        LOGGER.log(Level.INFO, "recibiendo solictud de eliminar itinerario con id {0}", id);

        // busca el itinerario con el id suministrado
        for (ItinerarioDTO itinerario : itinerarios) {
            if (Objects.equals(itinerario.getId(), id)) {

                // elimina el itinerario
                LOGGER.log(Level.INFO, "eliminando itinerario {0}", itinerario);
                itinerarios.remove(itinerario);
                return;
            }
        }

        // no encontró el itinerario con ese id ?
        LOGGER.severe("No existe un itinerario con ese id");
        throw new TripulatorLogicException("No existe un itinerario con ese id");
    }
}
