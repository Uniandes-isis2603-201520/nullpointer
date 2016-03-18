/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.nullpointer.rest.tripulator.mocks;

import co.edu.uniandes.nullpointer.rest.tripulator.dtos.DiaDTO;
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
 * @author Nicolás
 */
@Named
@Singleton
public class DiaLogicMock {
       private final static Logger LOGGER = Logger.getLogger(DiaLogicMock.class.getName());
        
    private static HashMap<Long, HashMap<Long,ArrayList<DiaDTO>>> mapViajeroItinerario; // Viajero a Mapa de Itinerarios-Dias
    
    public DiaLogicMock() {
        if (mapViajeroItinerario == null) {
            mapViajeroItinerario = new HashMap<>();
            
            HashMap<Long, ArrayList<DiaDTO>> mapItinerarioDia = new HashMap<>();
            // Se mapea el id del viajero a un mapa que contiene el arreglo de dias de cada itinerario.
            mapViajeroItinerario.put(1L, mapItinerarioDia);
            
            // Se mapean 3 itinerarios con arreglos de dias para el viajero con id 1.
            mapItinerarioDia.put(1L, new ArrayList<DiaDTO>());
            mapItinerarioDia.put(2L, new ArrayList<DiaDTO>());
            mapItinerarioDia.put(3L, new ArrayList<DiaDTO>());

            // Se agregan 4 dias al itinerario con id 1 del viajero con id 1.
            mapItinerarioDia.get(1L).add(new DiaDTO(0L, new Date(), new ArrayList(), 1L));
            mapItinerarioDia.get(1L).add(new DiaDTO(1L, new Date(), new ArrayList(), 2L));
            mapItinerarioDia.get(1L).add(new DiaDTO(2L, new Date(), new ArrayList(), 3L));
            mapItinerarioDia.get(1L).add(new DiaDTO(3L, new Date(), new ArrayList(), 4L));
        }

        // indica que se muestren todos los mensajes
        LOGGER.setLevel(Level.INFO);

        // muestra información 
        LOGGER.info("Inicializa la lista de dias");
        LOGGER.log(Level.INFO, "Dia{0}", mapViajeroItinerario);
    }
    
    public List<DiaDTO> getDias(Long idViajero, Long idItinerario) throws TripulatorLogicException {
        if (mapViajeroItinerario == null) {
            LOGGER.severe("Error interno: lista de dias no existe.");
            throw new TripulatorLogicException("Error interno: lista de dias no existe.");
        }
        LOGGER.info("retornando todos los dias");
        return mapViajeroItinerario.get(idViajero).get(idItinerario);
    }
    
    public DiaDTO getDia(Long idViajero, Long idItinerario, Long id) throws TripulatorLogicException {
        LOGGER.log(Level.INFO, "recibiendo solicitud de un día con id {0}", id);
        ArrayList<DiaDTO>dias = mapViajeroItinerario.get(idViajero).get(idItinerario);
        // busca el itinerario con el id suministrado
        for (DiaDTO itinerario : dias) {
            if (Objects.equals(itinerario.getId(), id)) {
                LOGGER.log(Level.INFO, "retornando itinerario {0}", itinerario);
                return itinerario;
            }
        }

        // si no encuentra el itinerario
        LOGGER.severe("No existe un itinerario con ese id");
        throw new TripulatorLogicException("No existe un itinerario con ese id");
    }
    
    public DiaDTO createDia(Long idViajero, Long idItinerario, DiaDTO newDia) throws TripulatorLogicException {
        LOGGER.log(Level.INFO, "recibiendo solicitud de agregar día {0}", newDia);
        if(!mapViajeroItinerario.containsKey(idViajero))
            mapViajeroItinerario.put(idViajero, new HashMap());
        if(!mapViajeroItinerario.get(idViajero).containsKey(idItinerario))
            mapViajeroItinerario.get(idViajero).put(idItinerario,new ArrayList());
        // el nuevo día tiene id ?
        if (newDia.getId() != null) {
            // busca el día con el id suministrado
            ArrayList<DiaDTO> dias = mapViajeroItinerario.get(idViajero).get(idItinerario);
            for (DiaDTO dia : dias) {
                // si existe un día con ese id
                if (Objects.equals(dia.getId(), newDia.getId())) {
                    LOGGER.severe("Ya existe un día con ese id");
                    throw new TripulatorLogicException("Ya existe un día con ese id");
                }
            }

        // el nuevo día no tiene id ? 
        } else {

            // genera un id para el día
            LOGGER.info("Generando el id para el nuevo itinerario");
            long newId = 1;
            ArrayList<DiaDTO> dias = mapViajeroItinerario.get(idViajero).get(idItinerario);
            for (DiaDTO dia : dias) {
                if (newId <= dia.getId()) {
                    newId = dia.getId() + 1;
                }
            }
            newDia.setId(newId);
        }

        // agrega el día
        LOGGER.log(Level.INFO, "agregando día {0}", newDia);
        ArrayList<DiaDTO> dias = mapViajeroItinerario.get(idViajero).get(idItinerario);
        dias.add(newDia);
        return newDia;
    }
    
    public DiaDTO updateDia(Long idViajero, Long idItinerario, Long id, DiaDTO updatedDia) throws TripulatorLogicException {
        LOGGER.log(Level.INFO, "recibiendo solictud de modificar un itinerario {0}", updatedDia);

        // busca el día con el id suministrado
        ArrayList<DiaDTO> dias = mapViajeroItinerario.get(idViajero).get(idItinerario);
        
        for (DiaDTO dia : dias) {
            if (Objects.equals(dia.getId(), id)) {

                // modifica el itinerario
                dia.setFecha(updatedDia.getFecha());
                dia.setIdCiudad(updatedDia.getIdCiudad());
                dia.setEventos(updatedDia.getEventos());

                // retorna el día modificado.
                LOGGER.log(Level.INFO, "Modificando día {0}", dia);
                return dia;
            }
        }

        // no encontró el día con ese id ?
        LOGGER.severe("No existe un día con ese id");
        throw new TripulatorLogicException("No existe un día con ese id");
    }


    public void deleteDia(Long idViajero, Long idItinerario, Long id) throws TripulatorLogicException {
        LOGGER.log(Level.INFO, "recibiendo solictud de eliminar día con id {0}", id);
        ArrayList<DiaDTO> dias = mapViajeroItinerario.get(idViajero).get(idItinerario);
        // busca el día con el id suministrado
        for (DiaDTO dia : dias) {
            if (Objects.equals(dia.getId(), id)) {

                // elimina el día
                LOGGER.log(Level.INFO, "eliminando itinerario {0}", dia);
                dias.remove(dia);
                return;
            }
        }

        // no encontró el día con ese id ?
        LOGGER.severe("No existe un día con ese id");
        throw new TripulatorLogicException("No existe un día con ese id");
    }
}
