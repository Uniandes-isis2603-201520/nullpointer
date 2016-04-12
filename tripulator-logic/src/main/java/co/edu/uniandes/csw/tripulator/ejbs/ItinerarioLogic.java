/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.tripulator.ejbs;

import co.edu.uniandes.csw.tripulator.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.tripulator.api.IItinerarioLogic;
import co.edu.uniandes.csw.tripulator.entities.ItinerarioEntity;
import co.edu.uniandes.csw.tripulator.persistence.ItinerarioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Antonio de la Vega
 */
@Stateless
public class ItinerarioLogic implements IItinerarioLogic {

    private static final Logger logger = Logger.getLogger(ItinerarioLogic.class.getName());
    
    @Inject
    private ItinerarioPersistence itinerarioPersistence;
        
    @Override
    public List<ItinerarioEntity> getItinerarios(Long idViajero){
        logger.info("Consultando todos los itinerarios.");
        List<ItinerarioEntity> itinerarios = itinerarioPersistence.findAll();
        logger.info("Se terminó de consultar los itinerarios.");
        return itinerarios;
    }

    @Override
    public ItinerarioEntity getItinerario(Long idViajero, Long idItinerario) throws BusinessLogicException {
        logger.log(Level.INFO, "Inicia proceso de consultar itinerario con id={0}", idItinerario);
        ItinerarioEntity itinerario = itinerarioPersistence.find(idItinerario);
        if (itinerario == null) {
            logger.log(Level.SEVERE, "El itinerario con el id {0} no existe", idItinerario);
            throw new BusinessLogicException("El itinerario solicitado no existe");
        }
        logger.log(Level.INFO, "Termina proceso de consultar itinerario con id={0}", idItinerario);
        return itinerario;
    }

    @Override
    public ItinerarioEntity createItinerario(Long idViajero, ItinerarioEntity entity) {
        logger.info("Inicia proceso de creación de itinerario");
        itinerarioPersistence.create(entity);
        logger.info("Termina proceso de creación de itinerario");
        return entity;
    }

    @Override
    public ItinerarioEntity updateItinerario(Long idViajero, ItinerarioEntity entity) {
        logger.log(Level.INFO, "Inicia proceso de actualizar itinerario con id={0}", entity.getId());
        ItinerarioEntity newEntity = itinerarioPersistence.update(entity);
        logger.log(Level.INFO, "Termina proceso de actualizar itinerario con id={0}", entity.getId());
        return newEntity;
    }

    @Override
    public void deleteItinerario(Long idViajero, Long id) {
        logger.log(Level.INFO, "Inicia proceso de borrar itinerario con id={0}", id);
        itinerarioPersistence.delete(id);
        logger.log(Level.INFO, "Termina proceso de borrar itinerario con id={0}", id);
    }
    
}
