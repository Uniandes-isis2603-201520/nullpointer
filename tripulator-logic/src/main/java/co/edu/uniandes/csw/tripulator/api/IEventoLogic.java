package co.edu.uniandes.csw.tripulator.api;
import co.edu.uniandes.csw.tripulator.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.tripulator.entities.ComentarioEntity;
import co.edu.uniandes.csw.tripulator.entities.EventoEntity;
import java.util.List;

public interface IEventoLogic {

    public List<EventoEntity> getEventos();

    public EventoEntity getEvento(Long id) throws BusinessLogicException;

    public List<ComentarioEntity> getComentarios(Long comentarioId);

    public ComentarioEntity addComentario(Long comentarioId, Long eventolId);

    public void removeComentario(Long comentarioId, Long eventoId);

}
