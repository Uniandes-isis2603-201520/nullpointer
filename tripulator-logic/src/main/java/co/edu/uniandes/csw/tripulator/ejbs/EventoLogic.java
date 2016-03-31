package co.edu.uniandes.csw.tripulator.ejbs;
import co.edu.uniandes.csw.tripulator.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.tripulator.api.IEventoLogic;
import co.edu.uniandes.csw.tripulator.entities.ComentarioEntity;
import co.edu.uniandes.csw.tripulator.entities.EventoEntity;
import co.edu.uniandes.csw.tripulator.persistence.ComentarioPersistence;
import co.edu.uniandes.csw.tripulator.persistence.EventoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class EventoLogic implements IEventoLogic {

    private static final Logger logger = Logger.getLogger(EventoLogic.class.getName());

    @Inject
    private EventoPersistence persistence;

    @Inject
    private ComentarioPersistence comentarioPersistence;

    @Override
    public List<EventoEntity> getEventos() {
        logger.info("Inicia proceso de consultar todos los eventos");
        List<EventoEntity> eventos = persistence.findAll();
        logger.info("Termina proceso de consultar todos los eventos");
        return eventos;
    }

    @Override
    public EventoEntity getEvento(Long id) throws BusinessLogicException {
        logger.log(Level.INFO, "Inicia proceso de consultar evento con id={0}", id);
        EventoEntity evento = persistence.find(id);
        if (evento == null) {
            logger.log(Level.SEVERE, "El evento con el id {0} no existe", id);
            throw new BusinessLogicException("El evento solicitado no existe");
        }
        logger.log(Level.INFO, "Termina proceso de consultar evento con id={0}", id);
        return evento;
    }

    @Override
    public List<ComentarioEntity> getComentarios(Long comentarioId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ComentarioEntity addComentario(Long comentarioId, Long eventoId) {
        EventoEntity eventoEntity = persistence.find(eventoId);
        ComentarioEntity comentarioEntity = comentarioPersistence.find(eventoId);
        comentarioEntity.setEvento(eventoEntity);
        return comentarioEntity;
    }

    @Override
    public void removeComentario(Long comentarioId, Long eventoId) {
        EventoEntity eventoEntity = persistence.find(eventoId);
        ComentarioEntity book = comentarioPersistence.find(comentarioId);
        book.setEvento(null);
        eventoEntity.getComentarios().remove(book);
    }

}
