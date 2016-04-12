package co.edu.uniandes.nullpointer.rest.tripulator.converters;

import co.edu.uniandes.csw.tripulator.entities.ViajeroEntity;
import co.edu.uniandes.nullpointer.rest.tripulator.dtos.ViajeroDTO;
import java.util.ArrayList;
import java.util.List;

public abstract class ViajeroConverter {

    /**
     * Constructor privado para evitar la creación del constructor implícito de Java
     * @generated
     */
    private ViajeroConverter() {
    }

    /**
     * Realiza la conversión de ViajeroEntity a ViajeroDTO.
     * Se invoca cuando otra entidad tiene una referencia a ViajeroEntity.
     * Entrega únicamente los atributos proprios de la entidad.
     *
     * @param entity instancia de ViajeroEntity a convertir
     * @return instancia de ViajeroDTO con los datos recibidos por parámetro
     * @generated
     */
    public static ViajeroDTO refEntity2DTO(ViajeroEntity entity) {
        if (entity != null) {
            ViajeroDTO dto = new ViajeroDTO();
            dto.setId(entity.getId());
            dto.setNombre(entity.getName());
            dto.setApellido(entity.getApellido());
            dto.setEmail(entity.getEmail());
            dto.setPassword(entity.getPassword());
            dto.setUsuario(entity.getUsuario());

            return dto;
        } else {
            return null;
        }
    }

    /**
     * Realiza la conversión de ViajeroDTO a ViajeroEntity Se invoca cuando otro DTO
     * tiene una referencia a ViajeroDTO Convierte únicamente el ID ya que es el
     * único atributo necesario para guardar la relación en la base de datos
     *
     * @param dto instancia de ViajeroDTO a convertir
     * @return instancia de ViajeroEntity con los datos recibidos por parámetro
     * @generated
     */
    public static ViajeroEntity refDTO2Entity(ViajeroDTO dto) {
        if (dto != null) {
            ViajeroEntity entity = new ViajeroEntity();
            entity.setId(dto.getId());

            return entity;
        } else {
            return null;
        }
    }

    /**
     * Convierte una instancia de ViajeroEntity a ViajeroDTO Se invoca cuando se desea
     * consultar la entidad y sus relaciones muchos a uno o uno a uno
     *
     * @param entity instancia de ViajeroEntity a convertir
     * @return Instancia de ViajeroDTO con los datos recibidos por parámetro
     * @generated
     */
    private static ViajeroDTO basicEntity2DTO(ViajeroEntity entity) {
        if (entity != null) {
            ViajeroDTO dto = new ViajeroDTO();
            dto.setId(entity.getId());
            dto.setNombre(entity.getName());
            dto.setApellido(entity.getApellido());
            dto.setEmail(entity.getEmail());
            dto.setPassword(entity.getPassword());
            dto.setUsuario(entity.getUsuario());
         

            return dto;
        } else {
            return null;
        }
    }

    /**
     * Convierte una instancia de ViajeroDTO a ViajeroEntity Se invoca cuando se
     * necesita convertir una instancia de ViajeroDTO con los atributos propios de
     * la entidad y con las relaciones uno a uno o muchos a uno
     *
     * @param dto instancia de ViajeroDTO a convertir
     * @return Instancia de ViajeroEntity creada a partir de los datos de dto
     * @generated
     */
    private static ViajeroEntity basicDTO2Entity(ViajeroDTO dto) {
        if (dto != null) {
            ViajeroEntity entity = new ViajeroEntity();
            entity.setId(dto.getId());
            entity.setName(dto.getNombre());
            entity.setApellido(dto.getApellido());
            entity.setEmail(dto.getEmail());
            entity.setPassword(dto.getPassword());
            entity.setUsuario(dto.getUsuario());
            

            return entity;
        } else {
            return null;
        }
    }

    /**
     * Convierte instancias de ViajeroEntity a ViajeroDTO incluyendo sus relaciones
     * Uno a muchos y Muchos a muchos
     *
     * @param entity Instancia de ViajeroEntity a convertir
     * @return Instancia de ViajeroDTO con los datos recibidos por parámetro
     * @generated
     */
    public static ViajeroDTO fullEntity2DTO(ViajeroEntity entity) {
        if (entity != null) {
            ViajeroDTO dto = basicEntity2DTO(entity);
            return dto;
        } else {
            return null;
        }
    }

    /**
     * Convierte una instancia de ViajeroDTO a ViajeroEntity.
     * Incluye todos los atributos de ViajeroEntity.
     *
     * @param dto Instancia de ViajeroDTO a convertir
     * @return Instancia de ViajeroEntity con los datos recibidos por parámetro
     * @generated
     */
    public static ViajeroEntity fullDTO2Entity(ViajeroDTO dto) {
        if (dto != null) {
            ViajeroEntity entity = basicDTO2Entity(dto);
            return entity;
        } else {
            return null;
        }
    }

    /**
     * Convierte una colección de instancias de ViajeroEntity a ViajeroDTO. Para cada
     * instancia de ViajeroEntity en la lista, invoca basicEntity2DTO y añade el
     * nuevo ViajeroDTO a una nueva lista
     *
     * @param entities Colección de entidades a convertir
     * @return Collección de instancias de ViajeroDTO
     * @generated
     */
    public static List<ViajeroDTO> listEntity2DTO(List<ViajeroEntity> entities) {
        List<ViajeroDTO> dtos = new ArrayList<ViajeroDTO>();
        if (entities != null) {
            for (ViajeroEntity entity : entities) {
                dtos.add(basicEntity2DTO(entity));
            }
        }
        return dtos;
    }

    /**
     * Convierte una colección de instancias de ViajeroDTO a instancias de
     * ViajeroEntity Para cada instancia se invoca el método basicDTO2Entity
     *
     * @param dtos entities Colección de ViajeroDTO a convertir
     * @return Collección de instancias de ViajeroEntity
     * @generated
     */
    public static List<ViajeroEntity> listDTO2Entity(List<ViajeroDTO> dtos) {
        List<ViajeroEntity> entities = new ArrayList<ViajeroEntity>();
        if (dtos != null) {
            for (ViajeroDTO dto : dtos) {
                entities.add(basicDTO2Entity(dto));
            }
        }
        return entities;
    }
}
