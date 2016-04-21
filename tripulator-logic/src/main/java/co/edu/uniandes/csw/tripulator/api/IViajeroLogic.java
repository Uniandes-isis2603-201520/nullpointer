package co.edu.uniandes.csw.tripulator.api;

import co.edu.uniandes.csw.tripulator.entities.ViajeroEntity;
import co.edu.uniandes.csw.tripulator.entities.ItinerarioEntity;
import co.edu.uniandes.csw.tripulator.exceptions.BusinessLogicException;
import java.util.List;

public interface IViajeroLogic {

    public List<ViajeroEntity> getViajeros();

    public ViajeroEntity getViajero(Long id) throws BusinessLogicException;

    public ViajeroEntity createViajero(ViajeroEntity entity);

    public ViajeroEntity updateViajero(ViajeroEntity entity);

    public void deleteViajero(Long id);

    public ItinerarioEntity addItinerario(ItinerarioEntity itinerario, Long viajeroId)throws BusinessLogicException;

    public void removeItinerario(Long itinerarioId, Long viajeroId);

    public List<ItinerarioEntity> replaceItinerarios(List<ItinerarioEntity> itinerarios, Long viajeroId) throws BusinessLogicException;

    public List<ItinerarioEntity> getItinerarios(Long viajeroId);

    public ItinerarioEntity getItinerario(Long itinerarioId, Long viajeroId);
}
