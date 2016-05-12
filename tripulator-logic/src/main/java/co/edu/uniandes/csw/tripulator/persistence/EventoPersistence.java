/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.tripulator.persistence;

import co.edu.uniandes.csw.tripulator.entities.EventoEntity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jd.fandino10
 */
@Stateless
public class EventoPersistence {

    private static final Logger LOGGER = Logger.getLogger(EventoPersistence.class.getName());

    @PersistenceContext(unitName = "TripulatorPU")
    protected EntityManager em;

    public EventoEntity find(Long id) {
        LOGGER.log(Level.INFO, "Consultando evento con id={0}", id);
        return em.find(EventoEntity.class, id);
    }

    public List<EventoEntity> findAll() {
        LOGGER.info("Consultando todos los eventos");
        Query q = em.createQuery("select u from EventoEntity u");
        return q.getResultList();
    }
    /**
     * Metodo que da los eventos de una ciudad que empiezan antes de una fecha
     * @param ciudad El nombre de la ciudad
     * @param fecha La fecha limite del evento
     * @return
     */
    public List<EventoEntity> find(String ciudad, Date fecha, Date fechaLimite) {
        String query = "select u from EventoEntity u where u.ciudad=:ciudad and u.fechaInicio between :fecha"+
               " and :fechaLimite";
        try{
        LOGGER.log(Level.INFO, "Consultando todos los eventos de {0} despues de {1}", new Object[]{ciudad, fecha});
        Query q = em.createQuery(query);
        q.setParameter("ciudad", ciudad);
        q.setParameter("fecha", fecha);
        q.setParameter("fechaLimite", fechaLimite);
        LOGGER.log(Level.INFO, "TERMINA LA CONSULTA DE eventos de {0} antes de {1}", new Object[]{ciudad, fecha});
        return q.getResultList();
        }catch(Exception e){
            LOGGER.log(Level.WARNING, e.getMessage());
            throw e;
        }
    }

    public EventoEntity create(EventoEntity entity) {
        LOGGER.info("Creando un evento nuevo");
        em.persist(entity);
        LOGGER.info("Evento creado");
        return entity;
    }

    public EventoEntity update(EventoEntity entity) {
        LOGGER.log(Level.INFO, "Actualizando evento con id={0}", entity.getId());
        return em.merge(entity);
    }

    public void delete(Long id) {
        LOGGER.log(Level.INFO, "Borrando evento con id={0}", id);
        EventoEntity entity = em.find(EventoEntity.class, id);
        em.remove(entity);
    }
}
