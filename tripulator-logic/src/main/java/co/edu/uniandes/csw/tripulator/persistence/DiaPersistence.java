/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.tripulator.persistence;

import co.edu.uniandes.csw.tripulator.entities.DiaEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Nicolás
 */
@Stateless
public class DiaPersistence {

    private static final Logger logger = Logger.getLogger(DiaPersistence.class.getName());

    @PersistenceContext(unitName = "TripulatorPU")
    protected EntityManager em;

    public DiaEntity create(DiaEntity entity) {
        logger.info("Creando un día nuevo");
        em.persist(entity);
        logger.info("Día creado");
        return entity;
    }

    public DiaEntity update(DiaEntity entity) {
        logger.log(Level.INFO, "Actualizando día con id={0}", entity.getId());
        return em.merge(entity);
    }

    public void delete(Long id) {
        logger.log(Level.INFO, "Borrando día con id={0}", id);
        DiaEntity entity = em.find(DiaEntity.class, id);
        em.remove(entity);
    }

    public DiaEntity find(Long idItinerario, Long idDia) {
        logger.log(Level.INFO, "Consultando día con id={0}", idDia);
        TypedQuery<DiaEntity> q = em.createQuery("select u from "
                + "DiaEntity u where (u.itinerario.id = :idItinerario) and (u.id = :idDia)",
                DiaEntity.class);
        q.setParameter("idItinerario", idItinerario);
        q.setParameter("idDia", idDia);
        return q.getSingleResult();
    }

    public List<DiaEntity> findAll(Long idItinerario) {
        logger.info("Consultando todos los dias.");
        TypedQuery<DiaEntity> q = em.createQuery("select u from "
                + "DiaEntity u where (u.itinerario.id = :idItinerario)",
                DiaEntity.class);
        q.setParameter("idItinerario", idItinerario);
        return q.getResultList();
    }
}
