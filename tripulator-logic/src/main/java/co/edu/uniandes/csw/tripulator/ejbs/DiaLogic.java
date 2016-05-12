/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.tripulator.ejbs;

import co.edu.uniandes.csw.tripulator.api.IDiaLogic;
import co.edu.uniandes.csw.tripulator.api.IItinerarioLogic;
import co.edu.uniandes.csw.tripulator.entities.DiaEntity;
import co.edu.uniandes.csw.tripulator.entities.EventoEntity;
import co.edu.uniandes.csw.tripulator.entities.ItinerarioEntity;
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
    
    @Inject
    private IItinerarioLogic itinerarioLogic;
    
    @Inject EventoPersistence eventoPersistence;
    
    @Override
    public List<DiaEntity> getDias(Long idViajero, Long idItinerario) throws BusinessLogicException{
        logger.info("Inicia proceso de consultar todos los días del itinerario "+ idItinerario);
        ItinerarioEntity itinerario = itinerarioLogic.getItinerario(idViajero, idItinerario);
        logger.info("Termina proceso de consulta de todos los días del itinerario " + idItinerario);
        return itinerario.getDias();
    }

    @Override
    public DiaEntity getDia(Long idViajero, Long idItinerario, Long id) throws BusinessLogicException {
        logger.log(Level.INFO, "Inicia proceso de consultar día con id={0}", id);
        DiaEntity dia = persistence.find(idItinerario, id);
        if (dia == null) {
            logger.log(Level.SEVERE, "El día con el id {0} no existe", id);
            throw new BusinessLogicException("El día solicitado no existe");
        }
        logger.log(Level.INFO, "Termina proceso de consultar día con id={0}", id);
        return dia;
    }

    @Override
    public DiaEntity createDia(Long idViajero, Long idItinerario, DiaEntity entity) throws BusinessLogicException {
        ItinerarioEntity itinerario = itinerarioLogic.getItinerario(idViajero, idItinerario);
        logger.info("Inicia proceso de creación de día con id " + entity.getId());
        entity.setItinerario(itinerario);
        persistence.create(entity);
        logger.info("Termina proceso de creación de día id: " + entity.getId());
        return entity;
    }

    @Override
    public DiaEntity updateDia(Long idViajero, Long idItinerario, DiaEntity entity) throws BusinessLogicException{
        logger.log(Level.INFO, "Inicia proceso de actualizar día con id={0}", entity.getId());
        ItinerarioEntity itinerario = itinerarioLogic.getItinerario(idViajero, idItinerario);
        entity.setItinerario(itinerario);
        DiaEntity newEntity = persistence.update(entity);
        logger.log(Level.INFO, "Termina proceso de actualizar día con id={0}", entity.getId());
        return newEntity;
    }

    @Override
    public void deleteDia(Long idViajero, Long idItinerario, Long id) throws BusinessLogicException{
        logger.log(Level.INFO, "Inicia proceso de borrar día con id={0}", id);
        DiaEntity viejo = getDia(idViajero, idItinerario, id);
        persistence.delete(viejo.getId());
        logger.log(Level.INFO, "Termina proceso de borrar día con id={0}", id);
    }

    @Override
    public List<EventoEntity> getEventos(Long idViajero, Long idItinerario, Long idDia) {
        return persistence.find(idItinerario, idDia).getEventos();
    }

    @Override
    public EventoEntity getEvento(Long idViajero, Long idItinerario, Long idDia, Long idEvento) {
        List<EventoEntity> eventos = persistence.find(idItinerario, idDia).getEventos();
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
        DiaEntity diaEntity = persistence.find(idItinerario, idDia);
        EventoEntity eventoEntity = eventoPersistence.find(idEvento);
        List<DiaEntity> dias = eventoEntity.getDias();
        dias.add(diaEntity);
        eventoEntity.setDias(dias);
        return eventoEntity;
    }

    @Override
    public void removeEvento(Long idViajero, Long idItinerario, Long idDia, Long idEvento) {
        DiaEntity diaEntity = persistence.find(idItinerario, idDia);
        EventoEntity evento = eventoPersistence.find(idEvento);
        List<DiaEntity> dias = evento.getDias();
        dias.remove(diaEntity);
        evento.setDias(dias);
        diaEntity.getEventos().remove(evento);
    }

    @Override
    public List<EventoEntity> replaceEventos(Long idViajero, Long idItinerario, Long idDia, List<EventoEntity>eventos) {
        DiaEntity dia = persistence.find(idItinerario, idDia);
        dia.setEventos(eventos);
        persistence.update(dia);
        List<EventoEntity> listaEventos = eventoPersistence.findAll();
        for (EventoEntity evento : listaEventos) {
            if (eventos.contains(evento)) {
                    List<DiaEntity> dias = evento.getDias();
                    dias.add(dia);
                    evento.setDias(dias);
                    eventoPersistence.update(evento);
            } else if (!evento.getDias().isEmpty() && evento.getDias().contains(dia)) {
                evento.setDias(null);
            }
        }
        return eventos;
    }
}
