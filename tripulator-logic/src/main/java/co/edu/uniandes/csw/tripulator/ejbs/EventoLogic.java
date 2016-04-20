package co.edu.uniandes.csw.tripulator.ejbs;

import co.edu.uniandes.csw.tripulator.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.tripulator.api.IEventoLogic;
import co.edu.uniandes.csw.tripulator.entities.DiaEntity;
import co.edu.uniandes.csw.tripulator.entities.EventoEntity;
import co.edu.uniandes.csw.tripulator.persistence.DiaPersistence;
import co.edu.uniandes.csw.tripulator.persistence.EventoPersistence;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class EventoLogic implements IEventoLogic {

    private static final Logger logger = Logger.getLogger(EventoLogic.class.getName());

    @Inject
    private EventoPersistence persistence;

    @Inject
    private DiaPersistence diaPersistence;

    @Override
    public List<EventoEntity> getEventos() {
        logger.info("Inicia proceso de consultar todos los eventos");
        List<EventoEntity> eventos = persistence.findAll();
        logger.info("Termina proceso de consultar todos los eventos");
        return eventos;
    }

    /**
     *
     * @param id
     * @return
     * @throws BusinessLogicException
     */
    @Override
    public List<EventoEntity> getEventosCiudadFecha(String ciudad, Date fecha) throws BusinessLogicException {
        logger.log(Level.INFO, "Inicia proceso de consultar eventos de " + ciudad + " en el dia " + fecha);
        List<EventoEntity> eventos = persistence.find(ciudad, fecha, getEndOfDay(fecha));
        if (eventos == null) {
            logger.log(Level.SEVERE, "No hay eventos para " + ciudad + " en el dia " + fecha);
            throw new BusinessLogicException("Los eventos solicitados no existen");
        }
        logger.log(Level.INFO, "Termina proceso de consultar eventos de " + ciudad + " antes de " + fecha);
        return eventos;
    }

    /**
     *
     * @param id
     * @return
     * @throws BusinessLogicException
     */
    @Override
    public EventoEntity getEvento(Long id) throws BusinessLogicException {
        logger.log(Level.INFO, "Inicia proceso de consultar evento con id={0}", id);
        EventoEntity evento = persistence.find(id);
        if (evento == null) {
            logger.log(Level.SEVERE, "El evento con el id {0} no existe", id);
            throw new BusinessLogicException("El evento solicitado no existe");
        }
        logger.log(Level.INFO, "Termina proceso de consultar evento con id={0}", id);
        return evento;
    }

    @Override
    public EventoEntity createEvento(EventoEntity entity) throws BusinessLogicException {
        logger.info("Inicia proceso de creación de evento");
        if (entity.getFechaInicio().after(entity.getFechaFin())) {
            throw new BusinessLogicException("La fecha de inicio no puede ser después de la fecha final");
        }
        persistence.create(entity);
        logger.info("Termina proceso de creación de evento");
        return entity;
    }

    @Override
    public EventoEntity updateEvento(EventoEntity entity) throws BusinessLogicException {
        logger.log(Level.INFO, "Inicia proceso de actualizar evento con id={0}", entity.getId());
        if (entity.getFechaInicio().after(entity.getFechaFin())) {
            throw new BusinessLogicException("La fecha de inicio debe ser anterior a la fecha final");
        }
        EventoEntity newEntity = persistence.update(entity);
        logger.log(Level.INFO, "Termina proceso de actualizar evento con id={0}", entity.getId());
        return newEntity;
    }

    @Override
    public void deleteEvento(Long id) {
        logger.log(Level.INFO, "Inicia proceso de borrar evento con id={0}", id);
        persistence.delete(id);
        logger.log(Level.INFO, "Termina proceso de borrar evento con id={0}", id);
    }

    @Override
    public List<DiaEntity> getDias(Long eventoId) throws Exception {
        return getEvento(eventoId).getDias();
    }

    @Override
    public DiaEntity getDia(Long eventoId, Long diaId) throws Exception {
        List<DiaEntity> dias = getEvento(eventoId).getDias();
        DiaEntity diaEntity = diaPersistence.find(diaId);
        if (diaEntity == null) {
            throw new IllegalArgumentException("El dia no existe");
        }
        int index = dias.indexOf(diaEntity);
        if (index >= 0) {
            return dias.get(index);
        }
        throw new IllegalArgumentException("El dia no está asociado al evento");
    }

    @Override
    public DiaEntity addDia(Long diaId, Long eventoId) throws BusinessLogicException {
        EventoEntity eventoEntity = getEvento(eventoId);
        DiaEntity diaEntity = diaPersistence.find(diaId);
        if (diaEntity == null) {
            throw new IllegalArgumentException("El dia no existe");
        }
        if (0 != compareDay(diaEntity.getDate(), eventoEntity.getFechaInicio())) {
            throw new BusinessLogicException("La fecha(solo el dia) del dia debe ser el mismo al del inicio del evento");
        }
        eventoEntity.getDias().add(diaEntity);
        return diaEntity;
    }

    @Override
    public void removeDia(Long diaId, Long eventoId) throws Exception {
        EventoEntity eventoEntity = getEvento(eventoId);
        DiaEntity diaEntity = diaPersistence.find(diaId);
        if (diaEntity == null) {
            throw new IllegalArgumentException("El dia no existe");
        }
        eventoEntity.getDias().remove(diaEntity);
    }

    @Override
    public List<DiaEntity> replaceDias(List<DiaEntity> dias, Long eventoId) throws BusinessLogicException {
        EventoEntity eventoEntity = getEvento(eventoId);
        eventoEntity.setDias(dias);
        for (DiaEntity dia : dias) {
            if (0 != compareDay(dia.getDate(), eventoEntity.getFechaInicio())) {
                throw new BusinessLogicException("La fecha(solo el dia) del dia debe ser el mismo al del inicio del evento");
            }
        }
        return eventoEntity.getDias();
    }

    /**
     * Metodo que compara solo que el dia de dos Dates sea igual (con año y mes)
     *
     * @param d1 Date a comparar
     * @param d2 Date a comparar
     * @return 0 si son iguales, -x si d2 es despues que d1, x si d1 es despues
     * de d2
     */
    public int compareDay(Date d1, Date d2) {
        if (d1.getYear() != d2.getYear()) {
            return d1.getYear() - d2.getYear();
        }
        if (d1.getMonth() != d2.getMonth()) {
            return d1.getMonth() - d2.getMonth();
        }
        return d1.getDate() - d2.getDate();
    }

    public Date getEndOfDay(Date fecha) {
        Calendar date = new GregorianCalendar();
        date.setTime(fecha);
        // reset hour, minutes, seconds and millis
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

// next day
        date.add(Calendar.DAY_OF_MONTH, 1);
        return date.getTime();
    }
}
