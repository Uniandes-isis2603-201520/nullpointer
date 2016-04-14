/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.tripulator.persistence;

import co.edu.uniandes.csw.tripulator.entities.EventoEntity;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jd.fandino10
 */
public class EventoPersistence {

    private static final Logger logger = Logger.getLogger(EventoPersistence.class.getName());

    @PersistenceContext(unitName = "TripulatorPU")
    protected EntityManager em;

    public EventoEntity find(Long id) {
        logger.log(Level.INFO, "Consultando evento con id={0}", id);
        return em.find(EventoEntity.class, id);
    }

    public List<EventoEntity> findAll() {
        logger.info("Consultando todos los eventos");
        Query q = em.createQuery("select u from EventoEntity u");
        return q.getResultList();
    }
    /**
     * Metodo que da los eventos de una ciudad que empiezan antes de una fecha
     * @param ciudad El nombre de la ciudad
     * @param fecha La fecha limite del evento
     * @return
     */
    public List<EventoEntity> find(String ciudad, Date fecha) {
        logger.info("Consultando todos los eventos de "+ciudad+" antes de "+fecha);
        Query q = em.createQuery("select u from Evento Entity u where ciudad="+ciudad+" and fecha_inicio<="+fecha);
        return q.getResultList();
    }
}
