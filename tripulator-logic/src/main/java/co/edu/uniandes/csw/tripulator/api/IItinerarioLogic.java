/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.tripulator.api;

import co.edu.uniandes.csw.tripulator.entities.DiaEntity;
import co.edu.uniandes.csw.tripulator.entities.FotoEntity;
import co.edu.uniandes.csw.tripulator.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.tripulator.entities.ItinerarioEntity;
import java.util.List;

/**
 *
 * @author Antonio de la Vega
 */
public interface IItinerarioLogic {
    
    public List<ItinerarioEntity> getItinerarios(Long idViajero)throws BusinessLogicException;
    
    public ItinerarioEntity getItinerario(Long idViajero, Long idItinerario)throws BusinessLogicException;
    
    public ItinerarioEntity createItinerario(Long idViajero, ItinerarioEntity entity)throws BusinessLogicException; 
    
    public ItinerarioEntity updateItinerario(Long idViajero, ItinerarioEntity entity)throws BusinessLogicException;
    
    public void deleteItinerario(Long idViajero, Long idItinerario) throws BusinessLogicException;
    
    public List<DiaEntity> replaceDays(List<DiaEntity> dias,Long idViajero, Long itinerarioId) throws BusinessLogicException;
    
    public List<FotoEntity> replacePhotos(List<FotoEntity> photos,Long idViajero, Long itinerarioId) throws BusinessLogicException;
    
    public DiaEntity getDay(Long idViajero, Long itinerarioId, Long dayId);
    
    public FotoEntity getPhoto(Long idViajero, Long itinerarioId, Long photoId);
    
    public List<DiaEntity> getDays(Long idViajero, Long itinerarioId);
    
    public List<FotoEntity> getPhotos(Long idViajero, Long itinerarioId);
    
    public void removePhoto(Long viajeroId, Long itinerarioId, Long photoId)throws BusinessLogicException;
    
    public void removeDay(Long viajeroId, Long itinerarioId, Long diaId) throws BusinessLogicException;
    
    public DiaEntity addDay(Long viajeroId, Long itinerarioId, DiaEntity dia) throws BusinessLogicException;
    
    public FotoEntity addPhoto(Long viajeroId, Long itinerarioId, FotoEntity foto) throws BusinessLogicException;
}
