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
    public List<EventoEntity> find(String ciudad, Date fecha, Date fechaLimite) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String query = "select u from EventoEntity u where u.ciudad=:ciudad and u.fechaInicio between :fecha"+
               " and :fechaLimite";
        try{
        logger.info("Consultando todos los eventos de "+ciudad+" despues de "+fecha);
        Query q = em.createQuery(query);
        q.setParameter("ciudad", ciudad);
        q.setParameter("fecha", fecha);
        q.setParameter("fechaLimite", fechaLimite);
        logger.info("TERMINA LA CONSULTA DE eventos de "+ciudad+" antes de "+fecha);
        return q.getResultList();
        }catch(Exception e){
            System.out.println("ERROR: "+e.getMessage());
            System.out.println("SQL: "+query);
            throw e;
        }
    }

    public EventoEntity create(EventoEntity entity) {
        logger.info("Creando un evento nuevo");
        em.persist(entity);
        logger.info("Evento creado");
        return entity;
    }

    public EventoEntity update(EventoEntity entity) {
        logger.log(Level.INFO, "Actualizando evento con id={0}", entity.getId());
        return em.merge(entity);
    }

    public void delete(Long id) {
        logger.log(Level.INFO, "Borrando evento con id={0}", id);
        EventoEntity entity = em.find(EventoEntity.class, id);
        em.remove(entity);
    }
}
