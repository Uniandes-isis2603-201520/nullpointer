/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.nullpointer.rest.tripulator.mocks;

import co.edu.uniandes.nullpointer.rest.tripulator.dtos.FotoDTO;
import co.edu.uniandes.nullpointer.rest.tripulator.exceptions.TripulatorLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * @author JOSE QUIROGA
 */

/**
 * Mock del recurso Foto (Mock del servicio REST)
 */
@Named
@Singleton
public class FotoLogicMock {
    
    /**
     * Objeto para presentar logs de las operaciones
     */
    private final static Logger logger = Logger.getLogger(FotoLogicMock.class.getName());

    /**
     * Listado de fotos
     */
    private static ArrayList<FotoDTO> fotos;
    
    /**
     * Constructor del mock
     */
    public FotoLogicMock()
    {
    
    }
    
   
    public List<FotoDTO> getFotos() throws TripulatorLogicException {
        if (fotos == null) {
            logger.severe("Error interno: lista de fotos no existe.");
            throw new TripulatorLogicException("Error interno: lista de fotos no existe.");
        }

        logger.info("Retornando todos las fotos.");
        return fotos;
    }

   
    public FotoDTO getFoto(Long id) throws TripulatorLogicException {
        logger.info("Recibiendo solicitud de foto con id: " + id);

        
        for (FotoDTO foto : fotos) {
            if (Objects.equals(foto.getId(), id)) {
                logger.info("Retornando foto " + foto);
                return foto;
            }
        }

        
        logger.severe("No existe foto con ese id.");
        throw new TripulatorLogicException("No existe foto con ese id.");
    }

 
    public FotoDTO createFoto(FotoDTO fotoNueva) throws TripulatorLogicException {
        logger.info("Recibiendo solicitud de agregar foto: " + fotoNueva);

        
        if (fotoNueva.getId() != null) {
            
            for (FotoDTO foto : fotos) {
               
                if (Objects.equals(foto.getId(), fotoNueva.getId())) {
                    logger.severe("Ya existe una foto con ese id");
                    throw new TripulatorLogicException("Ya existe una foto con ese id");
                }
            }

            
        } else {
            
            logger.info("Generando id nuevo");
            long newId = 1;
            for (FotoDTO e : fotos) {
                if (newId <= e.getId()) {
                    newId = e.getId() + 1;
                }
            }
            fotoNueva.setId(newId);
        }

        
        logger.info("Agregando foto " + fotoNueva);
        fotos.add(fotoNueva);
        return fotoNueva;
    }

    
    public FotoDTO updateFoto(Long id, FotoDTO nuevo) throws TripulatorLogicException {
        logger.info("Recibiendo solictud de modificar foto: " + nuevo);

        
        for (FotoDTO e : fotos) {
            if (Objects.equals(e.getId(), id)) {

                
                e.setId(nuevo.getId());

                
                logger.info("Modificando foto " + e);
                return e;
            }
        }

        
        logger.severe("No existe un evento con ese id");
        throw new TripulatorLogicException("No existe un evento con ese id");
    }

   
    public void deleteFoto(Long id) throws TripulatorLogicException {
        logger.info("Recibiendo solictud de eliminar foto con id " + id);

        
        for (FotoDTO e : fotos) {
            if (Objects.equals(e.getId(), id)) {

                
                logger.info("eliminando foto " + e);
                fotos.remove(e);
                return;
            }
        }

        
        logger.severe("No existe una foto con ese id");
        throw new TripulatorLogicException("No existe una foto con ese id");
    }
    
}
