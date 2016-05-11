/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.tripulator.ejbs;

import co.edu.uniandes.csw.tripulator.api.IFotoLogic;
import co.edu.uniandes.csw.tripulator.api.IItinerarioLogic;
import co.edu.uniandes.csw.tripulator.entities.FotoEntity;
import co.edu.uniandes.csw.tripulator.entities.ItinerarioEntity;
import co.edu.uniandes.csw.tripulator.entities.ViajeroEntity;
import co.edu.uniandes.csw.tripulator.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.tripulator.persistence.FotoPersistence;
import co.edu.uniandes.csw.tripulator.persistence.ItinerarioPersistence;
import co.edu.uniandes.csw.tripulator.persistence.ViajeroPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author josef
 */
@Stateless
public class FotoLogic implements IFotoLogic{
    
    private static final Logger logger = Logger.getLogger(FotoLogic.class.getName());

    @Inject
    private FotoPersistence persistence;
    
    @Inject
    private IItinerarioLogic itiLogic;
    

    @Override
    public List<FotoEntity> getFotos(Long idViajero, Long idItinerario) {
        logger.info("Inicia proceso de consultar todas las fotos");
        List<FotoEntity> fotos = persistence.findAll(idViajero, idItinerario);
        logger.info("Termina proceso de consultar todas las fotos");
        return fotos;
    }


    @Override
    public FotoEntity createFoto(Long idViajero, Long idItinerario, FotoEntity entity) throws BusinessLogicException {
        logger.info("Inicia proceso de creación de foto");
        
        ItinerarioEntity e=itiLogic.getItinerario(idViajero, idItinerario);
        entity.setItinerario(e);
        entity=persistence.create(entity);
        logger.info("Termina proceso de creación de foto");
        return entity;
    }


    @Override
    public void deleteFoto(Long idViajero, Long idItinerario, Long id) throws BusinessLogicException  {
        logger.log(Level.INFO, "Inicia proceso de borrar foto con id={0}", id);
        persistence.delete(id);
        logger.log(Level.INFO, "Termina proceso de borrar foto con id={0}", id);
    }
    
}
 