/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.tripulator.ejbs;

import co.edu.uniandes.csw.tripulator.api.IDiaLogic;
import co.edu.uniandes.csw.tripulator.entities.DiaEntity;
import co.edu.uniandes.csw.tripulator.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.tripulator.persistence.DiaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Nicolás
 */
@Stateless
public class DiaLogic implements IDiaLogic {
    
    private static final Logger logger = Logger.getLogger(DiaLogic.class.getName());
    
    @Inject
    private DiaPersistence persistence;
    
    @Override
    public List<DiaEntity> getDias(Long idViajero, Long idItinerario) {
        logger.info("Inicia proceso de consultar todos los días del itinerario "+ idItinerario);
        List<DiaEntity> dias = persistence.findAll();
        logger.info("Termina proceso de consulta");
        return dias;
    }

    @Override
    public DiaEntity getDia(Long idViajero, Long idItinerario, Long id) throws BusinessLogicException {
        logger.log(Level.INFO, "Inicia proceso de consultar día con id={0}", id);
        DiaEntity dia = persistence.find(id);
        if (dia == null) {
            logger.log(Level.SEVERE, "El día con el id {0} no existe", id);
            throw new BusinessLogicException("El día solicitado no existe");
        }
        logger.log(Level.INFO, "Termina proceso de consultar día con id={0}", id);
        return dia;
    }

    @Override
    public DiaEntity createDia(Long idViajero, Long idItinerario, DiaEntity entity) {
        logger.info("Inicia proceso de creación de día");
        persistence.create(entity);
        logger.info("Termina proceso de creación de día");
        return entity;
    }

    @Override
    public DiaEntity updateDia(Long idViajero, Long idItinerario, DiaEntity entity) {
        logger.log(Level.INFO, "Inicia proceso de actualizar día con id={0}", entity.getId());
        DiaEntity newEntity = persistence.update(entity);
        logger.log(Level.INFO, "Termina proceso de actualizar día con id={0}", entity.getId());
        return newEntity;
    }

    @Override
    public void deleteDia(Long idViajero, Long idItinerario, Long id) {
        logger.log(Level.INFO, "Inicia proceso de borrar día con id={0}", id);
        persistence.delete(id);
        logger.log(Level.INFO, "Termina proceso de borrar día con id={0}", id);
    }
}
