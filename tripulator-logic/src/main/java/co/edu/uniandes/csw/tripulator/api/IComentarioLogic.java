/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.tripulator.api;

import co.edu.uniandes.csw.tripulator.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.tripulator.entities.ComentarioEntity;
import java.util.List;

/**
 *
 * @author Daniel
 */
public interface IComentarioLogic {

    public List<ComentarioEntity> getComentarios();

    public ComentarioEntity getComentario(Long id) throws BusinessLogicException;

    public ComentarioEntity createComentario(ComentarioEntity entity) throws BusinessLogicException;
    
    public ComentarioEntity updateComentario(ComentarioEntity entity) throws BusinessLogicException;
    
    public void deleteComentario(Long id);

}
