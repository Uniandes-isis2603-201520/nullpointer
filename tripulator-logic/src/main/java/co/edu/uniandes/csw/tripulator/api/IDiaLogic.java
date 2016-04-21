package co.edu.uniandes.csw.tripulator.api;
import co.edu.uniandes.csw.tripulator.entities.DiaEntity;
import co.edu.uniandes.csw.tripulator.entities.EventoEntity;
import co.edu.uniandes.csw.tripulator.exceptions.BusinessLogicException;
import java.util.List;

public interface IDiaLogic {

    public List<DiaEntity> getDias(Long idViajero, Long idItinerario);

    public DiaEntity getDia(Long idViajero, Long idItinerario, Long idDia) throws BusinessLogicException;

    public DiaEntity createDia(Long idViajero, Long idItinerario, DiaEntity dia);

    public DiaEntity updateDia(Long idViajero, Long idItinerario, DiaEntity dia);

    public void deleteDia(Long idViajero, Long idItinerario, Long idDia);
    
    public List<EventoEntity> getEventos(Long idViajero, Long idItinerario, Long idDia);
    
    public EventoEntity getEvento(Long idViajero, Long idItinerario, Long idDia, Long idEvento);
    
    public EventoEntity addEvento(Long idViajero, Long idItinerario, Long idDia, Long idEvento);
    
    public void removeEvento(Long idViajero, Long idItinerario, Long idDia, Long idEvento);
   
    public List<EventoEntity> replaceEventos(Long idViajero, Long idItinerario, Long idDia, List<EventoEntity>eventos);
}
