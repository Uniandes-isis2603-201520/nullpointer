package co.edu.uniandes.csw.tripulator.ejbs;

import co.edu.uniandes.csw.tripulator.api.IItinerarioLogic;
import co.edu.uniandes.csw.tripulator.entities.ViajeroEntity;
import co.edu.uniandes.csw.tripulator.entities.ItinerarioEntity;
import co.edu.uniandes.csw.tripulator.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.tripulator.persistence.ViajeroPersistence;
import co.edu.uniandes.csw.tripulator.persistence.ItinerarioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import co.edu.uniandes.csw.tripulator.api.IViajeroLogic;


@Stateless
public class ViajeroLogic implements IViajeroLogic {

    private static final Logger logger = Logger.getLogger(ViajeroLogic.class.getName());

    @Inject
    private ViajeroPersistence persistence;

    @Inject
    private ItinerarioPersistence itinerarioPersistence;

    @Override
    public List<ViajeroEntity> getViajeros() {
        logger.info("Inicia proceso de consultar todos los viajeroes");
        List<ViajeroEntity> viajeros = persistence.findAll();
        logger.info("Termina proceso de consultar todos los viajeroes");
        return viajeros;
    }

    @Override
    public ViajeroEntity getViajero(Long id) throws BusinessLogicException {
        logger.log(Level.INFO, "Inicia proceso de consultar viajero con id={0}", id);
        ViajeroEntity viajero = persistence.find(id);
        if (viajero == null) {
            logger.log(Level.SEVERE, "El viajero con el id {0} no existe", id);
            throw new BusinessLogicException("El viajero solicitado no existe");
        }
        logger.log(Level.INFO, "Termina proceso de consultar viajero con id={0}", id);
        return viajero;
    }

    @Override
    public ViajeroEntity createViajero(ViajeroEntity entity) {
        logger.info("Inicia proceso de creación de viajero");
        persistence.create(entity);
        logger.info("Termina proceso de creación de viajero");
        return entity;
    }

    @Override
    public ViajeroEntity updateViajero(ViajeroEntity entity) {
        logger.log(Level.INFO, "Inicia proceso de actualizar viajero con id={0}", entity.getId());
        ViajeroEntity newEntity = persistence.update(entity);
        logger.log(Level.INFO, "Termina proceso de actualizar viajero con id={0}", entity.getId());
        return newEntity;
    }

    @Override
    public void deleteViajero(Long id) {
        logger.log(Level.INFO, "Inicia proceso de borrar viajero con id={0}", id);
        persistence.delete(id);
        logger.log(Level.INFO, "Termina proceso de borrar viajero con id={0}", id);
    }
    
    @Override
    public List<ItinerarioEntity> getItinerarios(Long viajeroId) {
        return persistence.find(viajeroId).getItinerarios();
    }
    
     @Override
    public ItinerarioEntity getItinerario(Long viajeroId, Long itinerarioId) {
         List<ItinerarioEntity> itinerarios = persistence.find(viajeroId).getItinerarios();
        ItinerarioEntity itinerarioEntity = new ItinerarioEntity();
        itinerarioEntity.setId(itinerarioId);
        int index = itinerarios.indexOf(itinerarioEntity);
        if (index >= 0) {
            return itinerarios.get(index);
        }
        return null;
    }

    @Override
    public ItinerarioEntity addItinerario(ItinerarioEntity itinerario, Long viajeroId) throws BusinessLogicException {
        ViajeroEntity e = getViajero(viajeroId);
        itinerario.setViajero(e);
        e.addItinerario(itinerario);
        itinerario = itinerarioPersistence.create(itinerario);
        return itinerario;
    }

    @Override
    public void removeItinerario(Long itinerarioId, Long viajeroId)  {
      
            itinerarioPersistence.delete(itinerarioId);
       
   }

    @Override
    public List<ItinerarioEntity> replaceItinerarios(List<ItinerarioEntity> itinerarios, Long viajeroId) throws BusinessLogicException {
        ViajeroEntity viajero = getViajero(viajeroId);
        List<ItinerarioEntity> itinerariosViejos = itinerarioPersistence.findAll(viajeroId);
        for (ItinerarioEntity itinerario : itinerariosViejos) {
            itinerarioPersistence.delete(itinerario.getId());
            viajero.removeItinerario(itinerario);
            
        }

       
        for (ItinerarioEntity itinerarioNuevo : itinerarios) {
            viajero.addItinerario(itinerarioNuevo);
            itinerarioNuevo.setViajero(viajero);
            itinerarioNuevo = itinerarioPersistence.create(itinerarioNuevo);
        }
        return itinerarios;
    }

    

   

}
