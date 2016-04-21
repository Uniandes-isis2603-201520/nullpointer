/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.tripulator.ejbs;

import co.edu.uniandes.csw.tripulator.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.tripulator.api.IItinerarioLogic;
import co.edu.uniandes.csw.tripulator.entities.DiaEntity;
import co.edu.uniandes.csw.tripulator.entities.FotoEntity;
import co.edu.uniandes.csw.tripulator.entities.ItinerarioEntity;
import co.edu.uniandes.csw.tripulator.persistence.DiaPersistence;
import co.edu.uniandes.csw.tripulator.persistence.FotoPersistence;
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
    
    @Inject
    private FotoPersistence fotoPersistence;
    
    @Inject
    private DiaPersistence diaPersistence;
        
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
    
    @Override
    public List<FotoEntity> getPhotos(Long idViajero, Long itinerarioId){
        return itinerarioPersistence.find(itinerarioId).getFotos();
    }
    
    @Override
    public List<DiaEntity> getDays(Long idViajero, Long itinerarioId){
        return itinerarioPersistence.find(itinerarioId).getDias();
    }
    
    @Override
    public FotoEntity getPhoto(Long idViajero, Long itinerarioId, Long photoId) {
        List<FotoEntity> fotos = itinerarioPersistence.find(itinerarioId).getFotos();
        FotoEntity fotoEntity = new FotoEntity();
        fotoEntity.setId(photoId);
        int index = fotos.indexOf(fotoEntity);
        if (index >= 0) {
            return fotos.get(index);
        }
        return null;
    }
    
    @Override
    public DiaEntity getDay(Long idViajero, Long itinerarioId, Long dayId) {
        List<DiaEntity> dias = itinerarioPersistence.find(itinerarioId).getDias();
        DiaEntity diaEntity = new DiaEntity();
        diaEntity.setId(dayId);
        int index = dias.indexOf(diaEntity);
        if (index >= 0) {
            return dias.get(index);
        }
        return null;
    }
    
    @Override
    public List<FotoEntity> replacePhotos(List<FotoEntity> photos,Long idViajero, Long itinerarioId) {
        ItinerarioEntity itinerario = itinerarioPersistence.find(itinerarioId);
        List<FotoEntity> photoList = fotoPersistence.findAll(idViajero, itinerarioId);
        for (FotoEntity photo : photoList) {
            if (photos.contains(photo)) {
                photo.setItinerario(itinerario);
            } else if (photo.getItinerario() != null && photo.getItinerario().equals(itinerario)) {
                photo.setItinerario(null);
            }
        }
        return photos;
    }
    
    @Override
    public List<DiaEntity> replaceDays(List<DiaEntity> dias,Long idViajero, Long itinerarioId) {
        ItinerarioEntity itinerario = itinerarioPersistence.find(itinerarioId);
        List<DiaEntity> diaList = diaPersistence.findAll();
        for (DiaEntity dia : diaList) {
            if (dias.contains(dia)) {
                dia.setItinerario(itinerario);
            } else if (dia.getItinerario() != null && dia.getItinerario().equals(itinerario)) {
                dia.setItinerario(null);
            }
        }
        return dias;
    }
}
