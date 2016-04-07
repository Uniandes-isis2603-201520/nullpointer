/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.tripulator.api;

import java.util.List;
import co.edu.uniandes.csw.tripulator.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.tripulator.entities.FotoEntity;

/**
 *
 * @author josef
 */
public interface IFotoLogic {
    
    public List<FotoEntity> getFotos();

    public FotoEntity getFoto(Long id) throws BusinessLogicException;

    public FotoEntity createFoto(FotoEntity entity);

    public void deleteFoto(Long id);
    
}
