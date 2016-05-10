/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.tripulator.persistence;

import co.edu.uniandes.csw.tripulator.entities.ItinerarioEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Antonio de la Vega
 */
@Stateless
public class ItinerarioPersistence {
    
        private static final Logger logger = Logger.getLogger(ItinerarioPersistence.class.getName());
        
        @PersistenceContext(unitName = "TripulatorPU")
        protected EntityManager em;
        
        public ItinerarioEntity create(ItinerarioEntity entity){
            logger.info("Creando un nuevo itinerario.");
            em.persist(entity);
            logger.info("Se creó un itinerario nuevo.");
            return entity;
        }
        
        public ItinerarioEntity update(ItinerarioEntity entity){
            logger.log(Level.INFO, "Actualizando itinerario con id={0}",entity.getId());
            return em.merge(entity);
        }
        
        public void delete(Long idItinerario){
            logger.log(Level.INFO, "Eliminando itinerario con id={0}",idItinerario);
            ItinerarioEntity entity = em.find(ItinerarioEntity.class, idItinerario);
            em.remove(entity);
        }
        
        public ItinerarioEntity find(Long idViajero, Long idItinerario){
            logger.log(Level.INFO, "Consultando itinerario con idViajero= {0} idItinerario= {1}", new Object[]{idViajero, idItinerario});
            TypedQuery<ItinerarioEntity> q = em.createQuery("select i from ItinerarioEntity i where (i.viajero.id = :idViajero) "
                + "and (i.id = :idItinerario)",ItinerarioEntity.class);
            q.setParameter("idViajero", idViajero);
            q.setParameter("idItinerario",idItinerario);
            return q.getSingleResult();
        }
        
        public List<ItinerarioEntity> findAll(Long idViajero){
            logger.info("Consultando todos los itinerarios.");
            TypedQuery<ItinerarioEntity> q = em.createQuery("select u from "
                    + "ItinerarioEntity u where (u.viajero.id = :idViajero)",
                    ItinerarioEntity.class);
            q.setParameter("idViajero", idViajero);
            return q.getResultList();
        }
}
