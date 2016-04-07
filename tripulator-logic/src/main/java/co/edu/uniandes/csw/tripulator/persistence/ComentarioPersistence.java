/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.tripulator.persistence;

import co.edu.uniandes.csw.tripulator.entities.ComentarioEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Daniel Delgado
 */
@Stateless
public class ComentarioPersistence {

    private static final Logger logger = Logger.getLogger(ComentarioPersistence.class.getName());

    @PersistenceContext(unitName = "TripulatorPU")
    protected EntityManager em;

    public ComentarioEntity find(Long id) {
        logger.log(Level.INFO, "Consultando comentario con id={0}", id);
        return em.find(ComentarioEntity.class, id);
    }

    public List<ComentarioEntity> findAll() {
        logger.info("Consultando todos los comentarios");
        Query q = em.createQuery("select u from ComentarioEntity u");
        return q.getResultList();
    }

    public ComentarioEntity create(ComentarioEntity entity) {
        logger.info("Creando un comentario nuevo");
        em.persist(entity);
        logger.info("Comentario creado");
        return entity;
    }
/**
    public ComentarioEntity update(ComentarioEntity entity) {
        logger.log(Level.INFO, "Actualizando comentario con id={0}", entity.getId());
        return em.merge(entity);
    }
*/
    /**@param id id del comentario a borrar*/
    public void delete(Long id) {
        logger.log(Level.INFO, "Borrando comentario con id={0}", id);
        ComentarioEntity entity = em.find(ComentarioEntity.class, id);
        em.remove(entity);
    }
}
