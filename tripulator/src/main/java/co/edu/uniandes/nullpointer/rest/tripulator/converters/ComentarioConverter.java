/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.nullpointer.rest.tripulator.converters;

import co.edu.uniandes.csw.tripulator.entities.ComentarioEntity;
import co.edu.uniandes.nullpointer.rest.tripulator.dtos.ComentarioDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel Delgado
 */
public class ComentarioConverter {
    
    /**
     * Constructor privado para evitar la creación del constructor implícito de
     * Java
     *
     * @generated
     */
    private ComentarioConverter() {
    }

    /**
     * Realiza la conversión de ComentarioEntity a ComentarioDTO. Se invoca cuando otra
     * entidad tiene una referencia a ComentarioEntity. Entrega únicamente los
     * atributos proprios de la entidad.
     *
     * @param entity instancia de ComentarioEntity a convertir
     * @return instancia de ComentarioDTO con los datos recibidos por parámetro
     * @generated
     */
    public static ComentarioDTO refEntity2DTO(ComentarioEntity entity) {
        if (entity != null) {
            ComentarioDTO dto = new ComentarioDTO();
            dto.setId(entity.getId());
            dto.setUser(entity.getUser());
            dto.setIdEvento(entity.getIdEvento());
            dto.setComment(entity.getComentario());
            dto.setStars(entity.getStars());
            dto.setDate(entity.getDate());
            dto.setUserPhoto(entity.getUserPhoto());

            return dto;
        } else {
            return null;
        }
    }

    /**
     * Realiza la conversión de ComentarioDTO a ComentarioEntity Se invoca cuando otro DTO
     * tiene una referencia a ComentarioDTO Convierte únicamente el ID ya que es el
     * único atributo necesario para guardar la relación en la base de datos
     *
     * @param dto instancia de ComentarioDTO a convertir
     * @return instancia de ComentarioEntity con los datos recibidos por parámetro
     * @generated
     */
    public static ComentarioEntity refDTO2Entity(ComentarioDTO dto) {
        if (dto != null) {
            ComentarioEntity entity = new ComentarioEntity();
            entity.setId(dto.getId());

            return entity;
        } else {
            return null;
        }
    }

    /**
     * Convierte una instancia de ComentarioEntity a ComentarioDTO Se invoca cuando se desea
     * consultar la entidad y sus relaciones muchos a uno o uno a uno
     *
     * @param entity instancia de ComentarioEntity a convertir
     * @return Instancia de ComentarioDTO con los datos recibidos por parámetro
     * @generated
     */
    private static ComentarioDTO basicEntity2DTO(ComentarioEntity entity) {
        if (entity != null) {
            ComentarioDTO dto = new ComentarioDTO();
            dto.setId(entity.getId());
            dto.setUser(entity.getUser());
            dto.setIdEvento(entity.getIdEvento());
            dto.setComment(entity.getComentario());
            dto.setStars(entity.getStars());
            dto.setDate(entity.getDate());
            dto.setUserPhoto(entity.getUserPhoto());

            return dto;
        } else {
            return null;
        }
    }

    /**
     * Convierte una instancia de ComentarioDTO a ComentarioEntity Se invoca cuando se
     * necesita convertir una instancia de ComentarioDTO con los atributos propios de
     * la entidad y con las relaciones uno a uno o muchos a uno
     *
     * @param dto instancia de ComentarioDTO a convertir
     * @return Instancia de ComentarioEntity creada a partir de los datos de dto
     * @generated
     */
    private static ComentarioEntity basicDTO2Entity(ComentarioDTO dto) {
        if (dto != null) {
            ComentarioEntity entity = new ComentarioEntity();
            entity.setId(dto.getId());
            entity.setUser(dto.getUser());
            entity.setIdEvento(dto.getIdEvento());
            entity.setComment(dto.getComment());
            entity.setStars(dto.getStars());
            entity.setDate(dto.getDate());
            entity.setUserPhoto(dto.getUserPhoto());
            return entity;
        } else {
            return null;
        }
    }

    /**
     * Convierte instancias de ComentarioEntity a ComentarioDTO incluyendo sus relaciones
     * Uno a muchos y Muchos a muchos
     *
     * @param entity Instancia de ComentarioEntity a convertir
     * @return Instancia de ComentarioDTO con los datos recibidos por parámetro
     * @generated
     */
    public static ComentarioDTO fullEntity2DTO(ComentarioEntity entity) {
        if (entity != null) {
            ComentarioDTO dto = basicEntity2DTO(entity);
            return dto;
        } else {
            return null;
        }
    }

    /**
     * Convierte una instancia de ComentarioDTO a ComentarioEntity. Incluye todos los
     * atributos de ComentarioEntity.
     *
     * @param dto Instancia de ComentarioDTO a convertir
     * @return Instancia de ComentarioEntity con los datos recibidos por parámetro
     * @generated
     */
    public static ComentarioEntity fullDTO2Entity(ComentarioDTO dto) {
        if (dto != null) {
            ComentarioEntity entity = basicDTO2Entity(dto);
            return entity;
        } else {
            return null;
        }
    }

    /**
     * Convierte una colección de instancias de ComentarioEntity a ComentarioDTO. Para cada
     * instancia de ComentarioEntity en la lista, invoca basicEntity2DTO y añade el
     * nuevo ComentarioDTO a una nueva lista
     *
     * @param entities Colección de entidades a convertir
     * @return Collección de instancias de ComentarioDTO
     * @generated
     */
    public static List<ComentarioDTO> listEntity2DTO(List<ComentarioEntity> entities) {
        List<ComentarioDTO> dtos = new ArrayList<ComentarioDTO>();
        if (entities != null) {
            for (ComentarioEntity entity : entities) {
                dtos.add(basicEntity2DTO(entity));
            }
        }
        return dtos;
    }

    /**
     * Convierte una colección de instancias de ComentarioDTO a instancias de
     * ComentarioEntity Para cada instancia se invoca el método basicDTO2Entity
     *
     * @param dtos entities Colección de ComentarioDTO a convertir
     * @return Collección de instancias de ComentarioEntity
     * @generated
     */
    public static List<ComentarioEntity> listDTO2Entity(List<ComentarioDTO> dtos) {
        List<ComentarioEntity> entities = new ArrayList<ComentarioEntity>();
        if (dtos != null) {
            for (ComentarioDTO dto : dtos) {
                entities.add(basicDTO2Entity(dto));
            }
        }
        return entities;
    }
    
}
