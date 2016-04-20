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
    IItinerarioLogic itinerarioLogic;

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
        ItinerarioEntity itinerario = new ItinerarioEntity();
        itinerario.setId(itinerarioId);
        for(ItinerarioEntity iti : itinerarios)
        {
            if(iti.getId() == itinerario.getId())
            {
                return iti;
            }
        }
        return null;
    }

    @Override
    public ItinerarioEntity addItinerario(ItinerarioEntity itinerario, Long viajeroId) throws BusinessLogicException {
        ViajeroEntity viajero = getViajero(viajeroId);
        viajero.addItinerario(itinerario);
        return itinerarioPersistence.find(itinerario.getId());
    }

    @Override
    public void removeItinerario(Long itinerarioId, Long viajeroId)  {
        try {
            ItinerarioEntity itinerario = getItinerario(viajeroId, itinerarioId);
            ViajeroEntity viajero = getViajero(viajeroId);
            viajero.removeItinerario(itinerario);
            itinerarioPersistence.delete(itinerario.getId());
        } catch (BusinessLogicException ex) {
            Logger.getLogger(ViajeroLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
   }

    @Override
    public List<ItinerarioEntity> replaceItinerarios(List<ItinerarioEntity> itinerarios, Long viajeroId) throws BusinessLogicException {
        ViajeroEntity viajero = persistence.find(viajeroId);
        viajero.setItinerarios(itinerarios);
        return viajero.getItinerarios();
    }

    

   

}
