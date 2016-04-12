/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.tripulator.api;

import java.util.List;
import co.edu.uniandes.csw.tripulator.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.tripulator.entities.FotoEntity;

/**
 *
 * @author josef
 */
public interface IFotoLogic {
    
    public List<FotoEntity> getFotos(Long idViajero, Long idItinerario);

    public FotoEntity getFoto(Long idViajero, Long idItinerario, Long id) throws BusinessLogicException;

    public FotoEntity createFoto(Long idViajero, Long idItinerario, FotoEntity entity);

    public void deleteFoto(Long idViajero, Long idItineario, Long id);
    
}
