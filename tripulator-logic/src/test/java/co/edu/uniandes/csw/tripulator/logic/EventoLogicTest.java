package co.edu.uniandes.csw.tripulator.logic;

import co.edu.uniandes.csw.tripulator.api.IEventoLogic;
import co.edu.uniandes.csw.tripulator.ejbs.EventoLogic;
import co.edu.uniandes.csw.tripulator.entities.ComentarioEntity;
import co.edu.uniandes.csw.tripulator.entities.DiaEntity;
import co.edu.uniandes.csw.tripulator.entities.EventoEntity;
import co.edu.uniandes.csw.tripulator.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.tripulator.persistence.EventoPersistence;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@RunWith(Arquillian.class)
public class EventoLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private IEventoLogic eventoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<EventoEntity> data = new ArrayList<EventoEntity>();

    private List<DiaEntity> diasData = new ArrayList<>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EventoEntity.class.getPackage())
                .addPackage(EventoLogic.class.getPackage())
                .addPackage(IEventoLogic.class.getPackage())
                .addPackage(EventoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Before
    public void configTest() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
            System.out.println("ERROR VA A HACER ROLLBACK");
                utx.rollback();
            System.out.println("HIZO ROLLBACK");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void clearData() {
        em.createQuery("delete from ComentarioEntity").executeUpdate();
        em.createQuery("delete from DiaEntity").executeUpdate();
        em.createQuery("delete from EventoEntity").executeUpdate();
    }

    private void insertData() throws Exception{
        for (int i = 0; i < 3; i++) {
            DiaEntity dia = factory.manufacturePojo(DiaEntity.class);
            System.out.println(dia.getCiudad());
            em.persist(dia);
            diasData.add(dia);
        }
        String infoData="";
        for (int i = 0; i < 3; i++) {
            EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
            if(entity.getFechaInicio().after(entity.getFechaFin())){
                Date temp = entity.getFechaInicio();
                entity.setFechaInicio(entity.getFechaFin());
                entity.setFechaFin(temp);
            }
            for (ComentarioEntity item : entity.getComentarios()) {
                item.setEvento(entity);
            }

            entity.getDias().add(diasData.get(0));
            infoData+=" Entity: "+entity.getId()+" , "+entity.getName();
            //em.persist(entity);
            eventoLogic.createEvento(entity);
            data.add(entity);
        }
        System.out.println(infoData);
    }

    @Test
    public void getEventoTest() {
        try {
            EventoEntity entity = data.get(0);
            EventoEntity resultEntity = eventoLogic.getEvento(entity.getId());
            Assert.assertNotNull(resultEntity);
            Assert.assertEquals(entity.getId(), resultEntity.getId());
            Assert.assertEquals(entity.getName(), resultEntity.getName());
            Assert.assertEquals(entity.getDescription(), resultEntity.getDescription());
            Assert.assertEquals(entity.getCiudad(), resultEntity.getCiudad());
            Assert.assertEquals(entity.getImage(), resultEntity.getImage());
            Assert.assertEquals(entity.getFechaInicio(), resultEntity.getFechaInicio());
            Assert.assertEquals(entity.getFechaFin(), resultEntity.getFechaFin());
            Assert.assertEquals(entity.getType(), resultEntity.getType());
        } catch (BusinessLogicException ex) {
            Assert.fail(ex.getLocalizedMessage());
        }
    }

    @Test
    public void getEventosTest() {
        List<EventoEntity> list = eventoLogic.getEventos();
        String r ="";
        for (EventoEntity entity : list) {
            boolean found = false;
            r+=("Enity: "+entity.getId()+" , "+entity.getName()+" \n");
            for (EventoEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            //Assert.assertTrue(found);
        }
        Assert.assertEquals(r,data.size(), list.size());
    }

    @Test
    public void getEventosCiudadFecha() {
        try {
            System.out.println("VA A EMPEZAR GETEVENTOSCIUDADFECHA");
            List<EventoEntity> eventos = eventoLogic.getEventos();
            System.out.println("OBTIENE LISTA DE TODOS LOS EVENTOS");
            for (EventoEntity entity : eventos) {
                List<EventoEntity> list = eventoLogic.getEventosCiudadFecha(entity.getCiudad(), entity.getFechaInicio());
            System.out.println("OBTIENE LISTA DE TODOS LOS EVENTOS DE "+entity.getCiudad()+" CON FECHA "+entity.getFechaInicio());
                boolean esta = false;
                Date endOfDay = eventoLogic.getEndOfDay(entity.getFechaInicio());
                for (EventoEntity actual : list) {

                    if (actual.getFechaInicio().before(entity.getFechaInicio()) || actual.getFechaInicio().after(endOfDay)) {
                        Assert.fail("La lista de eventos con fecha " + entity.getFechaInicio() + " no es correcta,"
                                + " pues contiene un evento con fecha " + actual.getFechaInicio());
                    }
                    Assert.assertEquals("La lista obtenida contiene un evento de ciudad diferente a la buscada.",
                            entity.getCiudad(), actual.getCiudad());
                    if (Objects.equals(entity.getId(), actual.getId())) {
                        esta = true;
                    }
                }
                Assert.assertTrue("La lista de eventos segun ciudad y fecha no contiene un evento que cumple las condiciones", esta);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void createEventoTest() {
        try {
            EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
            if(entity.getFechaInicio().after(entity.getFechaFin())){
                Date temp = entity.getFechaInicio();
                entity.setFechaInicio(entity.getFechaFin());
                entity.setFechaFin(temp);
            }
            EventoEntity result = eventoLogic.createEvento(entity);

            EventoEntity resp = em.find(EventoEntity.class, result.getId());

            Assert.assertNotNull(resp);
            Assert.assertEquals(entity.getId(), resp.getId());
            Assert.assertEquals(entity.getName(), resp.getName());
            Assert.assertEquals(entity.getDescription(), resp.getDescription());
            Assert.assertEquals(entity.getCiudad(), resp.getCiudad());
            Assert.assertEquals(entity.getImage(), resp.getImage());
            Assert.assertEquals(entity.getFechaInicio(), resp.getFechaInicio());
            Assert.assertEquals(entity.getFechaFin(), resp.getFechaFin());
            Assert.assertEquals(entity.getType(), resp.getType());
        } catch (BusinessLogicException ex) {
            Assert.fail(ex.getLocalizedMessage());
        }
    }

    @Test
    public void deleteEventoTest() {
        EventoEntity entity = data.get(1);
        eventoLogic.deleteEvento(entity.getId());
        EventoEntity deleted = em.find(EventoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    @Test
    public void updateEventoTest() {
        try {
            EventoEntity entity = data.get(1);
            EventoEntity pojoEntity = factory.manufacturePojo(EventoEntity.class);
            pojoEntity.setId(entity.getId());
            if(pojoEntity.getFechaInicio().after(pojoEntity.getFechaFin())){
                Date temp = pojoEntity.getFechaInicio();
                pojoEntity.setFechaInicio(pojoEntity.getFechaFin());
                pojoEntity.setFechaFin(temp);
            }
            eventoLogic.updateEvento(pojoEntity);
            EventoEntity resp = eventoLogic.getEvento(entity.getId());
            //EventoEntity resp = em.find(EventoEntity.class, entity.getId());

            Assert.assertNotNull("La respuesta no deberia ser nulla", resp);
            Assert.assertEquals(pojoEntity.getId(), resp.getId());
            Assert.assertEquals(pojoEntity.getName(), resp.getName());
            Assert.assertEquals(pojoEntity.getDescription(), resp.getDescription());
            Assert.assertEquals(pojoEntity.getCiudad(), resp.getCiudad());
            Assert.assertEquals(pojoEntity.getImage(), resp.getImage());
            Assert.assertEquals(pojoEntity.getFechaInicio(), resp.getFechaInicio());
            Assert.assertEquals(pojoEntity.getFechaFin(), resp.getFechaFin());
            Assert.assertEquals(pojoEntity.getType(), resp.getType());
        } catch (BusinessLogicException ex) {
            Assert.fail(ex.getLocalizedMessage());
        }
    }

    @Test
    public void listDatesTest() {
        try {
            List<DiaEntity> list = eventoLogic.getDias(data.get(0).getId());
            EventoEntity expected = em.find(EventoEntity.class, data.get(0).getId());

            Assert.assertNotNull(expected);
            Assert.assertEquals(expected.getDias().size(), list.size());
        } catch (Exception e) {
            Assert.fail(e.getLocalizedMessage());
        }
    }

    private DiaEntity getEventoDia(Long eventoId, Long diaId) {
        Query q = em.createQuery("Select DISTINCT d from EventoEntity e join e.dias d where e.id = :eventoId and d.id=:diaId");
        q.setParameter("eventoId", eventoId);
        q.setParameter("diaId", diaId);

        return (DiaEntity) q.getSingleResult();
    }
}
