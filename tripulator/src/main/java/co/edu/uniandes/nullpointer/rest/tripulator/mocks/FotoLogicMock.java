/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.nullpointer.rest.tripulator.mocks;

import co.edu.uniandes.nullpointer.rest.tripulator.dtos.DiaDTO;
import co.edu.uniandes.nullpointer.rest.tripulator.dtos.FotoDTO;
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
 * @author JOSE QUIROGA
 */
/**
 * Mock del recurso Foto (Mock del servicio REST)
 */
@Named
@Singleton
public class FotoLogicMock {

    /**
     * Objeto para presentar logs de las operaciones
     */
    private final static Logger logger = Logger.getLogger(FotoLogicMock.class.getName());

    private static HashMap<Long, HashMap<Long, ArrayList<FotoDTO>>> mapViajeroItinerario; // Viajero a Mapa de Itinerarios-Dias

    /**
     * Constructor del mock
     */
    public FotoLogicMock() {
        if (mapViajeroItinerario == null) {
            mapViajeroItinerario = new HashMap<>();

            HashMap<Long, ArrayList<FotoDTO>> mapItinerarioDia = new HashMap<>();
            // Se mapea el id del viajero a un mapa que contiene el arreglo de dias de cada itinerario.
            mapViajeroItinerario.put(1L, mapItinerarioDia);

            // Se mapean 3 itinerarios con arreglos de fotos para el viajero con id 1.
            mapItinerarioDia.put(1L, new ArrayList<FotoDTO>());
            mapItinerarioDia.put(2L, new ArrayList<FotoDTO>());
            mapItinerarioDia.put(3L, new ArrayList<FotoDTO>());

            // Se agregan 4 fotos al itinerario con id 1 del viajero con id 1.
            mapItinerarioDia.get(1L).add(new FotoDTO(0L, "http://bootstrapbay.com/blog/wp-content/uploads/2014/05/stocksnap-free-stock-photos1.jpg"));
            mapItinerarioDia.get(1L).add(new FotoDTO(1L, "http://bootstrapbay.com/blog/wp-content/uploads/2014/05/unslpash-desert-road_uvsq5s.png"));
            mapItinerarioDia.get(1L).add(new FotoDTO(2L, "http://bootstrapbay.com/blog/wp-content/uploads/2014/05/yellow-taxi_vvvjao.png"));
            mapItinerarioDia.get(1L).add(new FotoDTO(3L, "http://bootstrapbay.com/blog/wp-content/uploads/2014/05/negative-space.jpg"));
            mapItinerarioDia.get(1L).add(new FotoDTO(4L, "http://bootstrapbay.com/blog/wp-content/uploads/2014/05/SplitShire_air_balloons_gma6ks.jpg"));
        }

        // indica que se muestren todos los mensajes
        logger.setLevel(Level.INFO);

        // muestra información 
        logger.info("Inicializa la lista de fotos");
        logger.log(Level.INFO, "Dia{0}", mapViajeroItinerario);
    }

    public List<FotoDTO> getFotos(Long idViajero, Long idItinerario) throws TripulatorLogicException {
        if (mapViajeroItinerario == null) {
            logger.severe("Error interno: lista de fotos no existe.");
            throw new TripulatorLogicException("Error interno: lista de fotos no existe.");
        }
        logger.info("retornando todos las fotos");
        return mapViajeroItinerario.get(idViajero).get(idItinerario);
    }

    public FotoDTO getFoto(Long idViajero, Long idItinerario, Long id) throws TripulatorLogicException {
        logger.log(Level.INFO, "recibiendo solicitud de una foto con id {0}", id);
        ArrayList<FotoDTO> fotos = mapViajeroItinerario.get(idViajero).get(idItinerario);
        // busca la foto con el id suministrado
        for (FotoDTO itinerario : fotos) {
            if (Objects.equals(itinerario.getId(), id)) {
                logger.log(Level.INFO, "retornando foto {0}", itinerario);
                return itinerario;
            }
        }

        // si no encuentra la foto
        logger.severe("No existe una foto con ese id");
        throw new TripulatorLogicException("No existe una foto con ese id");
    }

    public FotoDTO createFoto(Long idViajero, Long idItinerario, FotoDTO fotoNueva) throws TripulatorLogicException {
        logger.log(Level.INFO, "recibiendo solicitud de agregar foto {0}", fotoNueva);
        if(!mapViajeroItinerario.containsKey(idViajero))
            mapViajeroItinerario.put(idViajero, new HashMap());
        if(!mapViajeroItinerario.get(idViajero).containsKey(idItinerario))
            mapViajeroItinerario.get(idViajero).put(idItinerario,new ArrayList());
        // la nueva foto tiene id ?
        if (fotoNueva.getId() != null) {
            // busca la foto con el id suministrado
            ArrayList<FotoDTO> fotos = mapViajeroItinerario.get(idViajero).get(idItinerario);
            for (FotoDTO foto : fotos) {
                // si existe una foto con ese id
                if (Objects.equals(foto.getId(), fotoNueva.getId())) {
                    logger.severe("Ya existe una foto con ese id");
                    throw new TripulatorLogicException("Ya existe una foto con ese id");
                }
            }

        // la nueva foto tiene id? 
        } else {

            // genera un id para la foto
            logger.info("Generando el id para la nueva fotos");
            long newId = 1;
            ArrayList<FotoDTO> fotos = mapViajeroItinerario.get(idViajero).get(idItinerario);
            for (FotoDTO foto : fotos) {
                if (newId <= foto.getId()) {
                    newId = foto.getId() + 1;
                }
            }
            fotoNueva.setId(newId);
        }

        // agrega la foto
        logger.log(Level.INFO, "agregando foto {0}", fotoNueva);
        ArrayList<FotoDTO> fotos = mapViajeroItinerario.get(idViajero).get(idItinerario);
        fotos.add(fotoNueva);
        return fotoNueva;
    }

    public FotoDTO updateFoto(Long idViajero,Long idItinerario, Long id, FotoDTO nuevo) throws TripulatorLogicException {
        logger.info("Recibiendo solictud de modificar foto: " + nuevo);
        ArrayList<FotoDTO> fotos = mapViajeroItinerario.get(idViajero).get(idItinerario);

        for (FotoDTO e : fotos) {
            if (Objects.equals(e.getId(), id)) {

                e.setSrc(nuevo.getSrc());

                logger.info("Modificando foto " + e);
                return e;
            }
        }

        logger.severe("No existe un evento con ese id");
        throw new TripulatorLogicException("No existe un evento con ese id");
    }

    public void deleteFoto(Long idViajero, Long idItinerario, Long id) throws TripulatorLogicException {
        logger.info("Recibiendo solictud de eliminar foto con id " + id);
        ArrayList<FotoDTO> fotos = mapViajeroItinerario.get(idViajero).get(idItinerario);
        for (FotoDTO e : fotos) {
            if (Objects.equals(e.getId(), id)) {

                logger.info("eliminando foto " + e);
                fotos.remove(e);
                return;
            }
        }

        logger.severe("No existe una foto con ese id");
        throw new TripulatorLogicException("No existe una foto con ese id");
    }

}
