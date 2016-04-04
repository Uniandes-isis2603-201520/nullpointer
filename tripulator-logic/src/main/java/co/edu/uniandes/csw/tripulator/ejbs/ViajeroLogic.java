package co.edu.uniandes.csw.tripulator.ejbs;

//import co.edu.uniandes.csw.tripulator.api.IItinearioLogic;
import co.edu.uniandes.csw.tripulator.entities.ViajeroEntity;
//import co.edu.uniandes.csw.tripulator.entities.ItinerarioEntity;
import co.edu.uniandes.csw.tripulator.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.tripulator.persistence.ViajeroPersistence;
//import co.edu.uniandes.csw.tripulator.persistence.ItinerarioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import co.edu.uniandes.csw.tripulator.api.IViajeroLogic;

@Stateless
public class ViajeroLogic implements IViajeroLogic {

    private static final Logger logger = Logger.getLogger(ViajeroLogic.class.getName());

    @Inject
    private ViajeroPersistence persistence;

    @Inject
   // IItinerarioLogic itinerarioLogic;

    //@Inject
   // private ItinerarioPersistence itinerarioPersistence;

    @Override
    public List<ViajeroEntity> getViajeros() {
        logger.info("Inicia proceso de consultar todos los autores");
        List<ViajeroEntity> authors = persistence.findAll();
        logger.info("Termina proceso de consultar todos los autores");
        return authors;
    }

    @Override
    public ViajeroEntity getViajero(Long id) throws BusinessLogicException {
        logger.log(Level.INFO, "Inicia proceso de consultar autor con id={0}", id);
        ViajeroEntity author = persistence.find(id);
        if (author == null) {
            logger.log(Level.SEVERE, "El autor con el id {0} no existe", id);
            throw new BusinessLogicException("El autor solicitado no existe");
        }
        logger.log(Level.INFO, "Termina proceso de consultar autor con id={0}", id);
        return author;
    }

    @Override
    public ViajeroEntity createViajero(ViajeroEntity entity) {
        logger.info("Inicia proceso de creación de autor");
        persistence.create(entity);
        logger.info("Termina proceso de creación de autor");
        return entity;
    }

    @Override
    public ViajeroEntity updateViajero(ViajeroEntity entity) {
        logger.log(Level.INFO, "Inicia proceso de actualizar autor con id={0}", entity.getId());
        ViajeroEntity newEntity = persistence.update(entity);
        logger.log(Level.INFO, "Termina proceso de actualizar autor con id={0}", entity.getId());
        return newEntity;
    }

    @Override
    public void deleteViajero(Long id) {
        logger.log(Level.INFO, "Inicia proceso de borrar autor con id={0}", id);
        persistence.delete(id);
        logger.log(Level.INFO, "Termina proceso de borrar autor con id={0}", id);
    }

    //@Override
    //public ItinerarioEntity addItinerario(Long itinerarioId, Long authorId) throws BusinessLogicException {
    //    itinerarioLogic.addViajero(authorId, itinerarioId);
    //    return itinerarioPersistence.find(itinerarioId);
    //}

   /* @Override
    public void removeItinerario(Long itinerarioId, Long authorId) {
        itinerarioLogic.removeViajero(authorId, itinerarioId);
   }*/

    /*@Override
    public List<ItinerarioEntity> replaceItinerarios(List<ItinerarioEntity> itinerarios, Long authorId) throws BusinessLogicException {
        List<ItinerarioEntity> itinerarioList = itinerarioPersistence.findAll();
        ViajeroEntity author = persistence.find(authorId);
        for (ItinerarioEntity itinerario : itinerarioList) {
            if (itinerarios.contains(itinerario)) {
                if (!itinerario.getViajeros().contains(author)) {
                    itinerarioLogic.addViajero(authorId, itinerario.getId());
                }
            } else {
                itinerarioLogic.removeViajero(authorId, itinerario.getId());
            }
        }
        author.setItinerarios(itinerarios);
        return author.getItinerarios();
    }

    @Override
    public List<ItinerarioEntity> getItinerarios(Long authorId) {
        return persistence.find(authorId).getItinerarios();
    }

    @Override
    public ItinerarioEntity getItinerario(Long authorId, Long itinerarioId) {
        List<ItinerarioEntity> itinerarios = persistence.find(authorId).getItinerarios();
        ItinerarioEntity itinerario = new ItinerarioEntity();
        itinerario.setId(itinerarioId);
        int index = itinerarios.indexOf(itinerario);
        if (index >= 0) {
            return itinerarios.get(index);
        }
        return null;
    }
*/
}
