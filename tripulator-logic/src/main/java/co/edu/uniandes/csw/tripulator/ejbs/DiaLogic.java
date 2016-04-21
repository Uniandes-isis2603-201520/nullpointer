/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.tripulator.ejbs;

import co.edu.uniandes.csw.tripulator.api.IDiaLogic;
import co.edu.uniandes.csw.tripulator.entities.DiaEntity;
import co.edu.uniandes.csw.tripulator.entities.EventoEntity;
import co.edu.uniandes.csw.tripulator.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.tripulator.persistence.DiaPersistence;
import co.edu.uniandes.csw.tripulator.persistence.EventoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Nicolás
 */
@Stateless
public class DiaLogic implements IDiaLogic {
    
    private static final Logger logger = Logger.getLogger(DiaLogic.class.getName());
    
    @Inject
    private DiaPersistence persistence;
    
    @Inject EventoPersistence eventoPersistence;
    
    @Override
    public List<DiaEntity> getDias(Long idViajero, Long idItinerario) {
        logger.info("Inicia proceso de consultar todos los días del itinerario "+ idItinerario);
        List<DiaEntity> dias = persistence.findAll();
        logger.info("Termina proceso de consulta");
        return dias;
    }

    @Override
    public DiaEntity getDia(Long idViajero, Long idItinerario, Long id) throws BusinessLogicException {
        logger.log(Level.INFO, "Inicia proceso de consultar día con id={0}", id);
        DiaEntity dia = persistence.find(id);
        if (dia == null) {
            logger.log(Level.SEVERE, "El día con el id {0} no existe", id);
            throw new BusinessLogicException("El día solicitado no existe");
        }
        logger.log(Level.INFO, "Termina proceso de consultar día con id={0}", id);
        return dia;
    }

    @Override
    public DiaEntity createDia(Long idViajero, Long idItinerario, DiaEntity entity) {
        logger.info("Inicia proceso de creación de día");
        persistence.create(entity);
        logger.info("Termina proceso de creación de día");
        return entity;
    }

    @Override
    public DiaEntity updateDia(Long idViajero, Long idItinerario, DiaEntity entity) {
        logger.log(Level.INFO, "Inicia proceso de actualizar día con id={0}", entity.getId());
        DiaEntity newEntity = persistence.update(entity);
        logger.log(Level.INFO, "Termina proceso de actualizar día con id={0}", entity.getId());
        return newEntity;
    }

    @Override
    public void deleteDia(Long idViajero, Long idItinerario, Long id) {
        logger.log(Level.INFO, "Inicia proceso de borrar día con id={0}", id);
        persistence.delete(id);
        logger.log(Level.INFO, "Termina proceso de borrar día con id={0}", id);
    }

    @Override
    public List<EventoEntity> getEventos(Long idViajero, Long idItinerario, Long idDia) {
        return persistence.find(idDia).getEventos();
    }

    @Override
    public EventoEntity getEvento(Long idViajero, Long idItinerario, Long idDia, Long idEvento) {
        List<EventoEntity> eventos = persistence.find(idDia).getEventos();
        EventoEntity eventoEntity = new EventoEntity();
        eventoEntity.setId(idEvento);
        int index = eventos.indexOf(eventoEntity);
        if (index >= 0) {
            return eventos.get(index);
        }
        return null;
    }

    @Override
    public EventoEntity addEvento(Long idViajero, Long idItinerario, Long idDia, Long idEvento) {
        DiaEntity diaEntity = persistence.find(idDia);
        EventoEntity eventoEntity = eventoPersistence.find(idEvento);
        List<DiaEntity> dias = eventoEntity.getDias();
        dias.add(diaEntity);
        eventoEntity.setDias(dias);
        return eventoEntity;
    }

    @Override
    public void removeEvento(Long idViajero, Long idItinerario, Long idDia, Long idEvento) {
        DiaEntity diaEntity = persistence.find(idDia);
        EventoEntity evento = eventoPersistence.find(idEvento);
        List<DiaEntity> dias = evento.getDias();
        dias.remove(diaEntity);
        evento.setDias(dias);
        diaEntity.getEventos().remove(evento);
    }

    @Override
    public List<EventoEntity> replaceEventos(Long idViajero, Long idItinerario, Long idDia, List<EventoEntity>eventos) {
        DiaEntity dia = persistence.find(idDia);
        List<EventoEntity> listaEventos = eventoPersistence.findAll();
        for (EventoEntity evento : listaEventos) {
            if (eventos.contains(evento)) {
                    List<DiaEntity> dias = evento.getDias();
                    dias.add(dia);
                    evento.setDias(dias);
            } else if (!evento.getDias().isEmpty() && evento.getDias().contains(dia)) {
                evento.setDias(null);
            }
        }
        return eventos;
    }
}
