/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.nullpointer.rest.tripulator.converters;

import co.edu.uniandes.csw.tripulator.entities.DiaEntity;
import co.edu.uniandes.nullpointer.rest.tripulator.dtos.DiaDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nicolás
 */
public class DiaConverter {
    /**
     * Constructor privado para evitar la creación del constructor implícito de
     * Java
     * @generated
     */
    private DiaConverter() {
    }
    
    /**
     * Realiza la conversión de DiaENtity a DiaDTO. Se invoca cuando otra
     * entidad tiene una referencia a DiaEntity. Entrega únicamente los
     * atributos proprios de la entidad.
     *
     * @param entity instancia de DiaEntity a convertir
     * @return instancia de DiaDTO con los datos recibidos por parámetro
     * @generated
     */
    public static DiaDTO refEntity2DTO(DiaEntity entity) {
        if (entity != null) {
            DiaDTO dto = new DiaDTO();
            dto.setId(entity.getId());
            dto.setFecha(entity.getDate());
            dto.setCiudad(entity.getCiudad());
            dto.setPais(entity.getPais());
            return dto;
        } 
        else {
            return null;
        }
    }
    
    /**
     * Realiza la conversión de DiaDTO a DiaEntity. Se invoca cuando otro DTO
     * tiene una referencia a DiaDTO Convierte únicamente el ID ya que es el
     * único atributo necesario para guardar la relación en la base de datos
     *
     * @param dto instancia de DiaDTO a convertir
     * @return instancia de DiaEntity con los datos recibidos por parámetro
     * @generated
     */
    public static DiaEntity refDTO2Entity(DiaDTO dto) {
        if (dto != null) {
            DiaEntity entity = new DiaEntity();
            entity.setId(dto.getId());

            return entity;
        } 
        else {
            return null;
        }
    }

    /**
     * Convierte una instancia de DiaEntity a DiaDTO. Se invoca cuando se desea
     * consultar la entidad y sus relaciones muchos a uno o uno a uno
     *
     * @param entity instancia de DiaEntity a convertir
     * @return Instancia de DiaDTO con los datos recibidos por parámetro
     * @generated
     */
    private static DiaDTO basicEntity2DTO(DiaEntity entity) {
        if (entity != null) {
            DiaDTO dto = new DiaDTO();
            dto.setId(entity.getId());
            dto.setFecha(entity.getDate());
            dto.setCiudad(entity.getCiudad());
            dto.setPais(entity.getPais());

            return dto;
        } else {
            return null;
        }
    }
    
     /**
     * Convierte una instancia de DiaDTO a DiaEntity Se invoca cuando se
     * necesita convertir una instancia de DiaDTO con los atributos propios de
     * la entidad y con las relaciones uno a uno o muchos a uno
     *
     * @param dto instancia de DiaDTO a convertir
     * @return Instancia de DiaEntity creada a partir de los datos de dto
     * @generated
     */
    private static DiaEntity basicDTO2Entity(DiaDTO dto) {
        if (dto != null) {
            DiaEntity entity = new DiaEntity();
            entity.setId(dto.getId());
            entity.setDate(dto.getFecha());
            entity.setCiudad(dto.getCiudad());
            entity.setPais(dto.getPais());
            return entity;
        } else {
            return null;
        }
    }

    /**
     * Convierte instancias de DiaEntity a DiaDTO incluyendo sus relaciones
     * Uno a muchos y Muchos a muchos
     *
     * @param entity Instancia de DiaEntity a convertir
     * @return Instancia de DiaDTO con los datos recibidos por parámetro
     * @generated
     */
    public static DiaDTO fullEntity2DTO(DiaEntity entity) {
        if (entity != null) {
            DiaDTO dto = basicEntity2DTO(entity);
            return dto;
        } else {
            return null;
        }
    }

    /**
     * Convierte una instancia de DiaDTO a DiaEntity. Incluye todos los
     * atributos de DiaEntity.
     *
     * @param dto Instancia de DiaDTO a convertir
     * @return Instancia de DiaEntity con los datos recibidos por parámetro
     * @generated
     */
    public static DiaEntity fullDTO2Entity(DiaDTO dto) {
        if (dto != null) {
            DiaEntity entity = basicDTO2Entity(dto);
            return entity;
        } else {
            return null;
        }
    }

    /**
     * Convierte una colección de instancias de DiaEntity a DiaDTO. Para cada
     * instancia de DiaEntity en la lista, invoca basicEntity2DTO y añade el
     * nuevo DiaDTO a una nueva lista
     *
     * @param entities Colección de entidades a convertir
     * @return Collección de instancias de DiaDTO
     * @generated
     */
    public static List<DiaDTO> listEntity2DTO(List<DiaEntity> entities) {
        List<DiaDTO> dtos = new ArrayList<>();
        if (entities != null) {
            for (DiaEntity entity : entities) {
                dtos.add(basicEntity2DTO(entity));
            }
        }
        return dtos;
    }

    /**
     * Convierte una colección de instancias de DiaDTO a instancias de
     * DiaEntity Para cada instancia se invoca el método basicDTO2Entity
     *
     * @param dtos entities Colección de DiaDTO a convertir
     * @return Collección de instancias de DiaEntity
     * @generated
     */
    public static List<DiaEntity> listDTO2Entity(List<DiaDTO> dtos) {
        List<DiaEntity> entities = new ArrayList<>();
        if (dtos != null) {
            for (DiaDTO dto : dtos) {
                entities.add(basicDTO2Entity(dto));
            }
        }
        return entities;
    }

}
