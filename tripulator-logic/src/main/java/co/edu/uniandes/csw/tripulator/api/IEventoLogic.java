package co.edu.uniandes.csw.tripulator.api;
import co.edu.uniandes.csw.tripulator.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.tripulator.entities.DiaEntity;
import co.edu.uniandes.csw.tripulator.entities.EventoEntity;
import java.util.Date;
import java.util.List;

public interface IEventoLogic {

    public List<EventoEntity> getEventos();

    public List<EventoEntity> getEventosCiudadFecha(String ciudad, Date fecha) throws BusinessLogicException;

    public EventoEntity getEvento(Long id) throws BusinessLogicException;

    public EventoEntity createEvento(EventoEntity entity) throws BusinessLogicException;

    public EventoEntity updateEvento(EventoEntity entity) throws BusinessLogicException;

    public void deleteEvento(Long id);

    public List<DiaEntity> getDias(Long eventoId) throws Exception;

    public Date getEndOfDay(Date fechaInicio);

}
