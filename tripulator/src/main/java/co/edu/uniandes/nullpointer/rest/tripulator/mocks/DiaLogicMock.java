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
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author Nicolás
 */
@Named
@ApplicationScoped
public class DiaLogicMock {
       private final static Logger LOGGER = Logger.getLogger(EventoLogicMock.class.getName());
    
    private static ArrayList<DiaDTO> dias;
    
    public DiaLogicMock() {
        if (dias == null) {
            dias = new ArrayList<>();
            dias.add(new DiaDTO(0L, new Date(), new ArrayList(), 1));
            dias.add(new DiaDTO(1L, new Date(), new ArrayList(), 2));
            dias.add(new DiaDTO(2L, new Date(), new ArrayList(), 3));
            dias.add(new DiaDTO(3L, new Date(), new ArrayList(), 4));
        }

        // indica que se muestren todos los mensajes
        LOGGER.setLevel(Level.INFO);

        // muestra información 
        LOGGER.info("Inicializa la lista de dias");
        LOGGER.log(Level.INFO, "Dia{0}", dias);
    }
    
    public List<DiaDTO> getDias() throws TripulatorLogicException {
        if (dias == null) {
            LOGGER.severe("Error interno: lista de dias no existe.");
            throw new TripulatorLogicException("Error interno: lista de dias no existe.");
        }
        LOGGER.info("retornando todos los dias");
        return dias;
    }
    
    public DiaDTO getDia(Long id) throws TripulatorLogicException {
        LOGGER.log(Level.INFO, "recibiendo solicitud de un día con id {0}", id);

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
    
    public DiaDTO createDia(DiaDTO newDia) throws TripulatorLogicException {
        LOGGER.log(Level.INFO, "recibiendo solicitud de agregar día {0}", newDia);
        // el nuevo día tiene id ?
        if (newDia.getId() != null) {
            // busca el día con el id suministrado
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
            for (DiaDTO dia : dias) {
                if (newId <= dia.getId()) {
                    newId = dia.getId() + 1;
                }
            }
            newDia.setId(newId);
        }

        // agrega el día
        LOGGER.log(Level.INFO, "agregando día {0}", newDia);
        dias.add(newDia);
        return newDia;
    }
    
    public DiaDTO updateDia(Long id, DiaDTO updatedDia) throws TripulatorLogicException {
        LOGGER.log(Level.INFO, "recibiendo solictud de modificar un itinerario {0}", updatedDia);

        // busca el día con el id suministrado
        for (DiaDTO dia : dias) {
            if (Objects.equals(dia.getId(), id)) {

                // modifica el itinerario
                dia.setId(updatedDia.getId());
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


    public void deleteDia(Long id) throws TripulatorLogicException {
        LOGGER.log(Level.INFO, "recibiendo solictud de eliminar día con id {0}", id);

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
