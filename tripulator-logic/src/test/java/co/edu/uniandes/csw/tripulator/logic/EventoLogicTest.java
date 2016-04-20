package co.edu.uniandes.csw.tripulator.logic;

import co.edu.uniandes.csw.tripulator.api.IEventoLogic;
import co.edu.uniandes.csw.tripulator.ejbs.EventoLogic;
import co.edu.uniandes.csw.tripulator.entities.ComentarioEntity;
import co.edu.uniandes.csw.tripulator.entities.DiaEntity;
import co.edu.uniandes.csw.tripulator.entities.EventoEntity;
import co.edu.uniandes.csw.tripulator.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.tripulator.persistence.EventoPersistence;
import java.util.ArrayList;
import java.util.List;
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
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void clearData() {
        em.createQuery("delete from EventoEntity").executeUpdate();
        em.createQuery("delete from ComentarioEntity").executeUpdate();
        em.createQuery("delete from DiaEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            DiaEntity dia = factory.manufacturePojo(DiaEntity.class);
            System.out.println(dia.getCiudad());
            em.persist(dia);
            diasData.add(dia);
        }

        for (int i = 0; i < 3; i++) {
            EventoEntity entity = factory.manufacturePojo(EventoEntity.class);

            for (ComentarioEntity item : entity.getComentarios()) {
                item.setEvento(entity);
            }

            entity.getDias().add(diasData.get(0));
            em.persist(entity);
            data.add(entity);
        }
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
        Assert.assertEquals(data.size(), list.size());
        for (EventoEntity entity : list) {
            boolean found = false;
            for (EventoEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void createEventoTest() {
        try {
            EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
            EventoEntity result = eventoLogic.createEvento(entity);

            EventoEntity resp = em.find(EventoEntity.class, result.getId());

            Assert.assertNotNull(result);
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
            EventoEntity entity = data.get(0);
            EventoEntity pojoEntity = factory.manufacturePojo(EventoEntity.class);
            pojoEntity.setId(entity.getId());

            eventoLogic.updateEvento(pojoEntity);

            EventoEntity resp = em.find(EventoEntity.class, entity.getId());

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
    public void getDateTest() {
        try{
        EventoEntity entity = data.get(0);
        DiaEntity authorEntity = diasData.get(0);
        DiaEntity response = eventoLogic.getDia(entity.getId(), authorEntity.getId());

        DiaEntity expected = getEventoDia(entity.getId(), authorEntity.getId());

        Assert.assertNotNull(expected);
        Assert.assertNotNull(response);
        Assert.assertEquals(expected.getId(), response.getId());
        Assert.assertEquals(expected.getName(), response.getName());
        Assert.assertEquals(expected.getBirthDate(), response.getBirthDate());
        }catch(Exception e){
            Assert.fail(e.getLocalizedMessage());
        }
    }

    @Test
    public void listDatesTest() {
        try{
        List<DiaEntity> list = eventoLogic.getDias(data.get(0).getId());
        EventoEntity expected = em.find(EventoEntity.class, data.get(0).getId());

        Assert.assertNotNull(expected);
        Assert.assertEquals(expected.getDias().size(), list.size());
        }catch(Exception e){
            Assert.fail(e.getLocalizedMessage());
        }
    }

    private DiaEntity getEventoDia(Long eventoId, Long diaId) {
        Query q = em.createQuery("Select DISTINCT a from EventoEntity e join e.dias d where e.id = :eventoId and d.id=:diaId");
        q.setParameter("bookId", eventoId);
        q.setParameter("authorId", diaId);

        return (DiaEntity) q.getSingleResult();
    }
}
