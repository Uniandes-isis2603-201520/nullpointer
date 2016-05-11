/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.tripulator.ejbs;

import co.edu.uniandes.csw.tripulator.api.IDiaLogic;
import co.edu.uniandes.csw.tripulator.api.IFotoLogic;
import co.edu.uniandes.csw.tripulator.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.tripulator.api.IItinerarioLogic;
import co.edu.uniandes.csw.tripulator.api.IViajeroLogic;
import co.edu.uniandes.csw.tripulator.entities.DiaEntity;
import co.edu.uniandes.csw.tripulator.entities.FotoEntity;
import co.edu.uniandes.csw.tripulator.entities.ItinerarioEntity;
import co.edu.uniandes.csw.tripulator.entities.ViajeroEntity;
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
    private IViajeroLogic viajeroLogic;
    
    @Inject
    private ItinerarioPersistence itinerarioPersistence;
    
    @Inject
    private IFotoLogic fotoLogic;
    
    @Inject
    private IDiaLogic diaLogic;
        
    @Override
    public List<ItinerarioEntity> getItinerarios(Long idViajero) throws BusinessLogicException{
        logger.info("Consultando todos los itinerarios.");
        ViajeroEntity viajero = viajeroLogic.getViajero(idViajero);
        logger.info("Se terminó de consultar los itinerarios.");
        return viajero.getItinerarios();
    }

    @Override
    public ItinerarioEntity getItinerario(Long idViajero, Long idItinerario) throws BusinessLogicException {
        logger.log(Level.INFO, "Inicia proceso de consultar itinerario con id={0}", idItinerario);
        ItinerarioEntity itinerario = itinerarioPersistence.find(idViajero, idItinerario);
        if (itinerario == null) {
            logger.log(Level.SEVERE, "El itinerario con el id {0} no existe", idItinerario);
            throw new BusinessLogicException("El itinerario solicitado no existe");
        }
        logger.log(Level.INFO, "Termina proceso de consultar itinerario con id={0}", idItinerario);
        return itinerario;
    }

    @Override
    public ItinerarioEntity createItinerario(Long idViajero, ItinerarioEntity entity)throws BusinessLogicException {
        ViajeroEntity viajero = viajeroLogic.getViajero(idViajero);
        if (viajero == null) {
            throw new IllegalArgumentException("El viajero no existe");
        }
        logger.info("Inicia proceso de creación de itinerario");
        logger.info("Se encontró un viajero con id: " + viajero.getId());
        entity.setViajero(viajero);
        entity = itinerarioPersistence.create(entity);
        logger.info("Termina proceso de creación de itinerario");
        return entity;
    }

    @Override
    public ItinerarioEntity updateItinerario(Long idViajero, ItinerarioEntity entity)throws BusinessLogicException {
        logger.log(Level.INFO, "Inicia proceso de actualizar itinerario con id={0}", entity.getId());
        ViajeroEntity viajero = viajeroLogic.getViajero(idViajero);
        entity.setViajero(viajero);
        ItinerarioEntity newEntity = itinerarioPersistence.update(entity);
        logger.log(Level.INFO, "Termina proceso de actualizar itinerario con id={0}", entity.getId());
        return newEntity;
    }

    @Override
    public void deleteItinerario(Long idViajero, Long idItinerario)throws BusinessLogicException {
        logger.log(Level.INFO, "Inicia proceso de borrar itinerario con id={0}", idItinerario);
        ItinerarioEntity viejo = getItinerario(idViajero, idItinerario);
        itinerarioPersistence.delete(viejo.getId());
        logger.log(Level.INFO, "Termina proceso de borrar itinerario con id={0}", idItinerario);
    }
    
    @Override
    public List<FotoEntity> getPhotos(Long idViajero, Long itinerarioId){
        return itinerarioPersistence.find(idViajero, itinerarioId).getFotos();
    }
    
    @Override
    public List<DiaEntity> getDays(Long idViajero, Long itinerarioId){
        return itinerarioPersistence.find(idViajero, itinerarioId).getDias();
    }
    
    @Override
    public FotoEntity getPhoto(Long idViajero, Long itinerarioId, Long photoId) {
        List<FotoEntity> fotos = itinerarioPersistence.find(idViajero, itinerarioId).getFotos();
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
        List<DiaEntity> dias = itinerarioPersistence.find(idViajero, itinerarioId).getDias();
        DiaEntity diaEntity = new DiaEntity();
        diaEntity.setId(dayId);
        int index = dias.indexOf(diaEntity);
        if (index >= 0) {
            return dias.get(index);
        }
        return null;
    }
    
    @Override
    public List<FotoEntity> replacePhotos(List<FotoEntity> photos,Long idViajero, Long itinerarioId) throws BusinessLogicException{
        List<FotoEntity> fotosViejas = fotoLogic.getFotos(idViajero, itinerarioId);
        for(FotoEntity foto : fotosViejas){
            fotoLogic.deleteFoto(idViajero, itinerarioId, foto.getId());
        }
        
        for(FotoEntity fotosNuevas : photos){
            fotoLogic.createFoto(idViajero, itinerarioId, fotosNuevas);
        }
        return photos;
    }
    
    @Override
    public List<DiaEntity> replaceDays(List<DiaEntity> dias,Long idViajero, Long itinerarioId) throws BusinessLogicException {
        List<DiaEntity> diasViejos = diaLogic.getDias(idViajero, itinerarioId);
        for(DiaEntity dia : diasViejos){
            diaLogic.deleteDia(idViajero, itinerarioId, dia.getId());
        }
        
        for(DiaEntity diaNuevo : dias){
            diaLogic.createDia(idViajero, itinerarioId, diaNuevo);
        }
        return dias;
    }
    
    @Override
    public void removePhoto(Long viajeroId, Long itinerarioId, Long photoId) throws BusinessLogicException {
        fotoLogic.deleteFoto(viajeroId, itinerarioId, photoId);
    }
    
    @Override
    public void removeDay(Long viajeroId, Long itinerarioId, Long diaId) throws BusinessLogicException {
        diaLogic.deleteDia(viajeroId, itinerarioId, diaId);
    }
    
    @Override
    public FotoEntity addPhoto(Long viajeroId, Long itinerarioId, FotoEntity foto) throws BusinessLogicException {
        fotoLogic.createFoto(viajeroId, itinerarioId, foto);
        return foto;
    }
    
    @Override
    public DiaEntity addDay(Long viajeroId, Long itinerarioId, DiaEntity dia) throws BusinessLogicException {
        diaLogic.createDia(viajeroId, itinerarioId, dia);
        return dia;
    }
}
