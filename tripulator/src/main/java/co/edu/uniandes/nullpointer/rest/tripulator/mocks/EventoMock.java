package co.edu.uniandes.nullpointer.rest.tripulator.mocks;

/**
 * Mock del recurso Evento (Mock del servicio REST)
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
public class EventoMock {
	
	// objeto para presentar logs de las operaciones
	private final static Logger logger = Logger.getLogger(EventoMock.class.getName());
	
	// listado de eventos
    private static ArrayList<EventoDTO> eventos;

    /**
     * Constructor. Crea los datos de ejemplo.
     */
    public EventoMock() {

    	if (eventos == null) {
            eventos = new ArrayList<>();
            eventos.add(new EventoDTO());
            eventos.add(new EventoDTO());
            eventos.add(new EventoDTO());
        }
        
    	// indica que se muestren todos los mensajes
    	logger.setLevel(Level.INFO);
    	
    	// muestra información 
    	logger.info("Inicializa la lista de eventos");
    	logger.info("eventos" + eventos );
    }    
    
	/**
	 * Obtiene el listado de eventos. 
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
     * @param id identificador del evento
     * @return evento encontrada
     * @throws EventoLogicException cuando la ciudad no existe
     */
    public EventoDTO getEvento(Long id) throws TripulatorLogicException {
    	logger.info("recibiendo solicitud de evento con id " + id);
    	
    	// busca la ciudad con el id suministrado
        for (EventoDTO evento : eventos) {
            if (Objects.equals(evento.getId(), id)){
            	logger.info("retornando evento " + evento);
                return evento;
            }
        }
        
        // si no encuentra la ciudad
        logger.severe("No existe evento con ese id");
        throw new TripulatorLogicException("No existe evento con ese id");
    }

    /**
     * Agrega un evento a la lista.
     * @param newEvento ciudad a adicionar
     * @throws TripulatorLogicException cuando ya existe una ciudad con el id suministrado
     * @return ciudad agregada
     */
    public EventoDTO createEvento(EventoDTO newEvento) throws TripulatorLogicException {
    	logger.info("recibiendo solicitud de agregar evento " + newEvento);
    	
    	// la nueva ciudad tiene id ?
    	if ( newEvento.getId() != null ) {
	    	// busca la ciudad con el id suministrado
	        for (EventoDTO city : eventos) {
	        	// si existe una ciudad con ese id
	            if (Objects.equals(city.getId(), newEvento.getId())){
	            	logger.severe("Ya existe un evento con ese id");
	                throw new TripulatorLogicException("Ya existe un evento con ese id");
	            }
	        }
	        
	    // la nueva ciudad no tiene id ? 
    	} else {

    		// genera un id para la ciudad
    		logger.info("Generando id paa la nueva ciudad");
    		long newId = 1;
	        for (EventoDTO e : eventos) {
	            if (newId <= e.getId()){
	                newId =  e.getId() + 1;
	            }
	        }
	        newEvento.setId(newId);
    	}
    	
        // agrega la ciudad
    	logger.info("agregando evento " + newEvento);
        eventos.add(newEvento);
        return newEvento;
    }

    /**
     * Actualiza los datos de un evento
     * @param id identificador del evento a modificar
     * @param evento evento a modificar
     * @return datos del evento modificado
     * @throws TripulatorLogicException cuando no existe una ciudad con el id suministrado
     */
    public EventoDTO updateEvento(Long id, EventoDTO nuevo) throws TripulatorLogicException {
    	logger.info("recibiendo solictud de modificar evento " + nuevo);
    	
    	// busca la ciudad con el id suministrado
        for (EventoDTO e : eventos) {
            if (Objects.equals(e.getId(), id)) {
            	
            	// modifica el evento
            	e.setId(nuevo.getId());
                e.setName(nuevo.getName());
                
                // retorna la ciudad modificada
            	logger.info("Modificando ciudad " + e);
                return e;
            }
        }
        
        // no encontró la ciudad con ese id ?
        logger.severe("No existe un evento con ese id");
        throw new TripulatorLogicException("No existe un evento con ese id");
    }

    /**
     * Elimina los datos de un evento
     * @param id identificador del evento a eliminar
     * @throws TripulatorLogicException cuando no existe un evento con el id suministrado
     */
    public void deleteEvento(Long id) throws TripulatorLogicException {
    	logger.info("recibiendo solictud de eliminar evento con id " + id);
    	
    	// busca la ciudad con el id suministrado
        for (EventoDTO e : eventos) {
            if (Objects.equals(e.getId(), id)) {
            	
            	// elimina la ciudad
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
