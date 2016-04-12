/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.nullpointer.rest.tripulator.converters;

import co.edu.uniandes.csw.tripulator.entities.FotoEntity;
import co.edu.uniandes.nullpointer.rest.tripulator.dtos.FotoDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author josef
 */
public class FotoConverter {
    
    /**
     * Constructor privado para evitar la creación del constructor implícito de
     * Java
     *
     * @generated
     */
    private FotoConverter() {
    }

    /**
     * Realiza la conversión de FotoEntity a FotoDTO. Se invoca cuando otra
     * entidad tiene una referencia a FotoEntity. Entrega únicamente los
     * atributos proprios de la entidad.
     *
     * @param entity instancia de FotoEntity a convertir
     * @return instancia de FotoDTO con los datos recibidos por parámetro
     * @generated
     */
    public static FotoDTO refEntity2DTO(FotoEntity entity) {
        if (entity != null) {
            FotoDTO dto = new FotoDTO();
            dto.setId(entity.getId());
            dto.setSrc(entity.getSrc());

            return dto;
        } else {
            return null;
        }
    }

    /**
     * Realiza la conversión de FotoDTO a FotoEntity Se invoca cuando otro DTO
     * tiene una referencia a FotoDTO Convierte únicamente el ID ya que es el
     * único atributo necesario para guardar la relación en la base de datos
     *
     * @param dto instancia de FotoDTO a convertir
     * @return instancia de FotoEntity con los datos recibidos por parámetro
     * @generated
     */
    public static FotoEntity refDTO2Entity(FotoDTO dto) {
        if (dto != null) {
            FotoEntity entity = new FotoEntity();
            entity.setId(dto.getId());

            return entity;
        } else {
            return null;
        }
    }

    /**
     * Convierte una instancia de FotoEntity a FotoDTO Se invoca cuando se desea
     * consultar la entidad y sus relaciones muchos a uno o uno a uno
     *
     * @param entity instancia de FotoEntity a convertir
     * @return Instancia de FotoDTO con los datos recibidos por parámetro
     * @generated
     */
    private static FotoDTO basicEntity2DTO(FotoEntity entity) {
        if (entity != null) {
            FotoDTO dto = new FotoDTO();
            dto.setId(entity.getId());
            dto.setSrc(entity.getSrc());

            return dto;
        } else {
            return null;
        }
    }

    /**
     * Convierte una instancia de FotoDTO a FotoEntity Se invoca cuando se
     * necesita convertir una instancia de FotoDTO con los atributos propios de
     * la entidad y con las relaciones uno a uno o muchos a uno
     *
     * @param dto instancia de FotoDTO a convertir
     * @return Instancia de FotoEntity creada a partir de los datos de dto
     * @generated
     */
    private static FotoEntity basicDTO2Entity(FotoDTO dto) {
        if (dto != null) {
            FotoEntity entity = new FotoEntity();
            entity.setId(dto.getId());
            entity.setSrc(dto.getSrc());
            return entity;
        } else {
            return null;
        }
    }

    /**
     * Convierte instancias de FotoEntity a FotoDTO incluyendo sus relaciones
     * Uno a muchos y Muchos a muchos
     *
     * @param entity Instancia de FotoEntity a convertir
     * @return Instancia de FotoDTO con los datos recibidos por parámetro
     * @generated
     */
    public static FotoDTO fullEntity2DTO(FotoEntity entity) {
        if (entity != null) {
            FotoDTO dto = basicEntity2DTO(entity);
            return dto;
        } else {
            return null;
        }
    }

    /**
     * Convierte una instancia de FotoDTO a FotoEntity. Incluye todos los
     * atributos de FotoEntity.
     *
     * @param dto Instancia de FotoDTO a convertir
     * @return Instancia de FotoEntity con los datos recibidos por parámetro
     * @generated
     */
    public static FotoEntity fullDTO2Entity(FotoDTO dto) {
        if (dto != null) {
            FotoEntity entity = basicDTO2Entity(dto);
            return entity;
        } else {
            return null;
        }
    }

    /**
     * Convierte una colección de instancias de FotoEntity a FotoDTO. Para cada
     * instancia de FotoEntity en la lista, invoca basicEntity2DTO y añade el
     * nuevo FotoDTO a una nueva lista
     *
     * @param entities Colección de entidades a convertir
     * @return Collección de instancias de FotoDTO
     * @generated
     */
    public static List<FotoDTO> listEntity2DTO(List<FotoEntity> entities) {
        List<FotoDTO> dtos = new ArrayList<FotoDTO>();
        if (entities != null) {
            for (FotoEntity entity : entities) {
                dtos.add(basicEntity2DTO(entity));
            }
        }
        return dtos;
    }

    /**
     * Convierte una colección de instancias de FotoDTO a instancias de
     * FotoEntity Para cada instancia se invoca el método basicDTO2Entity
     *
     * @param dtos entities Colección de FotoDTO a convertir
     * @return Collección de instancias de FotoEntity
     * @generated
     */
    public static List<FotoEntity> listDTO2Entity(List<FotoDTO> dtos) {
        List<FotoEntity> entities = new ArrayList<FotoEntity>();
        if (dtos != null) {
            for (FotoDTO dto : dtos) {
                entities.add(basicDTO2Entity(dto));
            }
        }
        return entities;
    }
    
}
