package co.edu.uniandes.csw.tripulator.logic;

import co.edu.uniandes.csw.tripulator.api.IViajeroLogic;
import co.edu.uniandes.csw.tripulator.ejbs.ViajeroLogic;
import co.edu.uniandes.csw.tripulator.entities.ViajeroEntity;
import co.edu.uniandes.csw.tripulator.entities.ItinerarioEntity;
import co.edu.uniandes.csw.tripulator.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.tripulator.persistence.ViajeroPersistence;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
public class ViajeroLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private IViajeroLogic viajeroLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ViajeroEntity> data = new ArrayList<ViajeroEntity>();

    private List<ItinerarioEntity> itinerariosData = new ArrayList<>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ViajeroEntity.class.getPackage())
                .addPackage(ViajeroLogic.class.getPackage())
                .addPackage(IViajeroLogic.class.getPackage())
                .addPackage(ViajeroPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Before
    public void configTest() {
        try {
            utx.begin();
            clearData();
//            insertData();
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
        em.createQuery("delete from ItinerarioEntity").executeUpdate();
        em.createQuery("delete from ViajeroEntity").executeUpdate();
    }
/*
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ItinerarioEntity itinerarios = factory.manufacturePojo(ItinerarioEntity.class);
            itinerarios.setPublishDate(getMaxDate());
            em.persist(itinerarios);
            itinerariosData.add(itinerarios);
        }

        for (int i = 0; i < 3; i++) {
            ViajeroEntity entity = factory.manufacturePojo(ViajeroEntity.class);

            em.persist(entity);
            data.add(entity);

            itinerariosData.get(0).getViajeros().add(entity);
        }
    }
    */

    @Test
    public void createViajeroTest() {
        ViajeroEntity expected = factory.manufacturePojo(ViajeroEntity.class);
        ViajeroEntity created = viajeroLogic.createViajero(expected);

        ViajeroEntity result = em.find(ViajeroEntity.class, created.getId());

        Assert.assertNotNull(result);
        Assert.assertNotNull(result);
        Assert.assertEquals(expected.getId(), result.getId());
        Assert.assertEquals(expected.getName(), result.getName());
        Assert.assertEquals(expected.getApellido(), result.getApellido());
        Assert.assertEquals(expected.getPassword(), result.getPassword());
        Assert.assertEquals(expected.getEmail(), result.getEmail());
        Assert.assertEquals(expected.getUsuario(), result.getUsuario());
    }

    @Test
    public void getViajerosTest() {
        List<ViajeroEntity> resultList = viajeroLogic.getViajeros();
        List<ViajeroEntity> expectedList = em.createQuery("SELECT u from ViajeroEntity u").getResultList();
        Assert.assertEquals(expectedList.size(), resultList.size());
        for (ViajeroEntity expected : expectedList) {
            boolean found = false;
            for (ViajeroEntity result : resultList) {
                if (result.getId().equals(expected.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void getViajeroTest() throws BusinessLogicException {
        ViajeroEntity result = viajeroLogic.getViajero(data.get(0).getId());

        ViajeroEntity expected = em.find(ViajeroEntity.class, data.get(0).getId());

        Assert.assertNotNull(expected);
        Assert.assertNotNull(result);
        Assert.assertEquals(expected.getId(), result.getId());
        Assert.assertEquals(expected.getName(), result.getName());
        Assert.assertEquals(expected.getApellido(), result.getApellido());
        Assert.assertEquals(expected.getPassword(), result.getPassword());
        Assert.assertEquals(expected.getEmail(), result.getEmail());
        Assert.assertEquals(expected.getUsuario(), result.getUsuario());
    }

    @Test
    public void deleteViajeroTest() {
        ViajeroEntity entity = data.get(1);
        viajeroLogic.deleteViajero(entity.getId());
        ViajeroEntity expected = em.find(ViajeroEntity.class, entity.getId());
        Assert.assertNull(expected);
    }

    @Test
    public void updateViajeroTest() {
        ViajeroEntity entity = data.get(0);
        ViajeroEntity expected = factory.manufacturePojo(ViajeroEntity.class);

        expected.setId(entity.getId());

        viajeroLogic.updateViajero(expected);

        ViajeroEntity resp = em.find(ViajeroEntity.class, entity.getId());

        Assert.assertNotNull(expected);
        Assert.assertEquals(expected.getId(), resp.getId());
        Assert.assertEquals(expected.getName(), resp.getName());
        Assert.assertEquals(expected.getApellido(), resp.getApellido());
        Assert.assertEquals(expected.getPassword(), resp.getPassword());
        Assert.assertEquals(expected.getEmail(), resp.getEmail());
        Assert.assertEquals(expected.getUsuario(), resp.getUsuario());
    }

    /**
    @Test
    public void getItinerarioTest() {
        ViajeroEntity entity = data.get(0);
        ItinerarioEntity bookEntity = itinerariosData.get(0);
        ItinerarioEntity response = viajeroLogic.getItinerario(entity.getId(), bookEntity.getId());

        ItinerarioEntity expected = getViajeroItinerario(entity.getId(), bookEntity.getId());

        Assert.assertNotNull(expected);
        Assert.assertNotNull(response);
        Assert.assertEquals(expected.getId(), response.getId());
        Assert.assertEquals(expected.getName(), response.getName());
        Assert.assertEquals(expected.getDescription(), response.getDescription());
        Assert.assertEquals(expected.getIsbn(), response.getIsbn());
        Assert.assertEquals(expected.getImage(), response.getImage());
    }

    @Test
    public void listItinerariosTest() {
        List<ItinerarioEntity> list = viajeroLogic.getItinerarios(data.get(0).getId());
        ViajeroEntity expected = em.find(ViajeroEntity.class, data.get(0).getId());

        Assert.assertNotNull(expected);
        Assert.assertEquals(expected.getItinerarios().size(), list.size());
    }

    @Test
    public void addItinerariosTest() {
        try {
            ViajeroEntity entity = data.get(0);
            ItinerarioEntity bookEntity = itinerariosData.get(1);
            ItinerarioEntity response = viajeroLogic.addItinerario(bookEntity.getId(), entity.getId());

            ItinerarioEntity expected = getViajeroItinerario(entity.getId(), bookEntity.getId());

            Assert.assertNotNull(expected);
            Assert.assertNotNull(response);
            Assert.assertEquals(expected.getId(), response.getId());
        } catch (BusinessLogicException ex) {
            Assert.fail(ex.getLocalizedMessage());
        }
    }

    @Test
    public void replaceItinerariosTest() {
        try {
            ViajeroEntity entity = data.get(0);
            List<ItinerarioEntity> list = itinerariosData.subList(1, 3);
            viajeroLogic.replaceItinerarios(list, entity.getId());

            ViajeroEntity expected = em.find(ViajeroEntity.class, entity.getId());

            Assert.assertNotNull(expected);
            Assert.assertFalse(expected.getItinerarios().contains(itinerariosData.get(0)));
            Assert.assertTrue(expected.getItinerarios().contains(itinerariosData.get(1)));
            Assert.assertTrue(expected.getItinerarios().contains(itinerariosData.get(2)));
        } catch (BusinessLogicException ex) {
            Assert.fail(ex.getLocalizedMessage());
        }
    }

    @Test(expected = NoResultException.class)
    public void removeItinerariosTest() {
        viajeroLogic.removeItinerario(itinerariosData.get(0).getId(), data.get(0).getId());
        getViajeroItinerario(data.get(0).getId(), itinerariosData.get(0).getId());
    }

    private Date getMaxDate() {
        Random r = new Random();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 9999);
        c.set(Calendar.DAY_OF_YEAR, c.getActualMaximum(Calendar.DAY_OF_YEAR));
        c.set(Calendar.HOUR_OF_DAY, c.getActualMinimum(Calendar.HOUR_OF_DAY));
        c.set(Calendar.MINUTE, c.getActualMinimum(Calendar.MINUTE));
        c.set(Calendar.SECOND, c.getActualMinimum(Calendar.SECOND));
        c.set(Calendar.MILLISECOND, c.getActualMinimum(Calendar.MILLISECOND));
        return c.getTime();
    }

    private ItinerarioEntity getViajeroItinerario(Long viajeroId, Long bookId) {
        Query q = em.createQuery("Select DISTINCT b from ViajeroEntity a join a.itinerarios b where a.id=:viajeroId and b.id = :bookId");
        q.setParameter("bookId", bookId);
        q.setParameter("viajeroId", viajeroId);

        return (ItinerarioEntity) q.getSingleResult();
    }
    
    **/
}
