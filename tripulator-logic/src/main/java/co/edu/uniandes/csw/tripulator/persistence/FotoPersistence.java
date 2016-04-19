/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.tripulator.persistence;

import co.edu.uniandes.csw.tripulator.entities.EventoEntity;
import co.edu.uniandes.csw.tripulator.entities.FotoEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author josef
 */
@Stateless
public class FotoPersistence {
    
    private static final Logger logger = Logger.getLogger(FotoPersistence.class.getName());

    @PersistenceContext(unitName = "TripulatorPU")
    protected EntityManager em;

    public List<FotoEntity> findAll(Long idViajero, Long idItinerario) {
        logger.info("Consultando todos las fotos");
        Query q = em.createQuery("select u from FotoEntity u");
        return q.getResultList();
    }
    
       public FotoEntity create(Long idViajero, Long idItinerario, FotoEntity entity) {
        logger.info("Creando una foto nueva");
        em.persist(entity);
        logger.info("Foto creada");
        return entity;
    }
       
       
    public void delete(Long idViajero, Long idItinerario,Long id) {
        logger.log(Level.INFO, "Borrando foto con id={0}", id);
        FotoEntity entity = em.find(FotoEntity.class, id);
        em.remove(entity);
    }
    
}
