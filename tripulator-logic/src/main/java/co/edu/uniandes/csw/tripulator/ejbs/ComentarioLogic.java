/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.tripulator.ejbs;

import co.edu.uniandes.csw.tripulator.api.IComentarioLogic;
import co.edu.uniandes.csw.tripulator.entities.ComentarioEntity;
import co.edu.uniandes.csw.tripulator.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.tripulator.persistence.ComentarioPersistence;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Daniel Delgado
 */
@Stateless
public class ComentarioLogic implements IComentarioLogic {

    private static final Logger logger = Logger.getLogger(ComentarioLogic.class.getName());

    @Inject
    private ComentarioPersistence persistence;

    @Override
    public List<ComentarioEntity> getComentarios(Long idEvento) {
        logger.info("Inicia proceso de consultar todos los comentarios");
        List<ComentarioEntity> comentarios = persistence.findAll();
        logger.info("Termina proceso de consultar todos los comentarios");
        return comentarios;
    }

    @Override
    public ComentarioEntity getComentario(Long id) throws BusinessLogicException {
        logger.log(Level.INFO, "Inicia proceso de consultar comentario con id={0}", id);
        ComentarioEntity comentario = persistence.find(id);
        if (comentario == null) {
            logger.log(Level.SEVERE, "El comentario con el id {0} no existe", id);
            throw new BusinessLogicException("El comentario solicitado no existe");
        }
        logger.log(Level.INFO, "Termina proceso de consultar comentario con id={0}", id);
        return comentario;
    }

    @Override
    public ComentarioEntity createComentario(ComentarioEntity entity) throws BusinessLogicException {
        logger.info("Inicia proceso de creación de comentario");
        if (!validateComentario(entity.getComment())) {
            throw new BusinessLogicException("El comentario es inválido");
        }
        persistence.create(entity);
        logger.info("Termina proceso de creación de comentario");
        return entity;
    }
    
    @Override
    public ComentarioEntity updateComentario(Long IdEvento, Long id, ComentarioEntity entity) throws BusinessLogicException {
        logger.info("Inicia proceso de edicion de comentario");
        if (!validateComentario(entity.getComment())) {
            throw new BusinessLogicException("El comentario es inválido");
        }
        persistence.update(entity);
        logger.info("Termina proceso de update de comentario");
        return entity;
    }

    @Override
    public void deleteComentario(Long idEvento, Long id) {
        logger.log(Level.INFO, "Inicia proceso de borrar comentario con id={0}", id);
        persistence.delete(id);
        logger.log(Level.INFO, "Termina proceso de borrar comentario con id={0}", id);
    }
            
    private boolean validateComentario(String comentario) {
        if (comentario == null || comentario.isEmpty()) 
        {
            return false;
        }
        return true;
    }
}
