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

    public ItinerarioEntity addItinerario(Long bookId, Long authorId) throws BusinessLogicException;

    public void removeItinerario(Long bookId, Long authorId);

    public List<ItinerarioEntity> replaceItinerarios(List<ItinerarioEntity> books, Long authorId) throws BusinessLogicException;

    public List<ItinerarioEntity> getItinerarios(Long authorId);

    public ItinerarioEntity getItinerario(Long authorId, Long bookId);
}
