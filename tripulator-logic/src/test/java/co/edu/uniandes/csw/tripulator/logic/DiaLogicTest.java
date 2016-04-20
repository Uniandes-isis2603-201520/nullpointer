package co.edu.uniandes.csw.tripulator.logic;

import co.edu.uniandes.csw.tripulator.api.IDiaLogic;
import co.edu.uniandes.csw.tripulator.ejbs.DiaLogic;
import co.edu.uniandes.csw.tripulator.entities.DiaEntity;
import co.edu.uniandes.csw.tripulator.entities.EventoEntity;
import co.edu.uniandes.csw.tripulator.entities.ItinerarioEntity;
import co.edu.uniandes.csw.tripulator.entities.ViajeroEntity;
import co.edu.uniandes.csw.tripulator.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.tripulator.persistence.DiaPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
public class DiaLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private IDiaLogic diaLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private ViajeroEntity viajero;
    private ItinerarioEntity itinerario;

    private List<DiaEntity> data = new ArrayList<DiaEntity>();
    private List<EventoEntity> eventoData = new ArrayList<EventoEntity>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(DiaEntity.class.getPackage())
                .addPackage(DiaLogic.class.getPackage())
                .addPackage(IDiaLogic.class.getPackage())
                .addPackage(DiaPersistence.class.getPackage())
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
        em.createQuery("delete from DiaEntity").executeUpdate();
    }

    private void insertData() {
        
        viajero = factory.manufacturePojo(ViajeroEntity.class);
        itinerario = factory.manufacturePojo(ItinerarioEntity.class);
        
        for (int i=0; i<3; i++){
            EventoEntity evento = factory.manufacturePojo(EventoEntity.class);
            em.persist(evento);
            eventoData.add(evento);
        }
        
        for (int i = 0; i < 3; i++) {
            DiaEntity entity = factory.manufacturePojo(DiaEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                eventoData.get(i).setDias(data);
            }
        }
    }
    
    @Test
    public void createDiaTest() {
        DiaEntity expected = factory.manufacturePojo(DiaEntity.class);
        DiaEntity created = diaLogic.createDia(viajero.getId(), itinerario.getId(), expected);
        
        DiaEntity result = em.find(DiaEntity.class, created.getId());
        
        Assert.assertNotNull(result);
        Assert.assertEquals(expected.getId(), result.getId());
        Assert.assertEquals(expected.getDate(), result.getDate());
        Assert.assertEquals(expected.getCiudad(), result.getCiudad());
    }

    @Test
    public void getDiasTest() {
        List<DiaEntity> resultList = diaLogic.getDias(viajero.getId(), itinerario.getId());
        List<DiaEntity> expectedList = em.createQuery("SELECT u from DiaEntity u").getResultList();
        Assert.assertEquals(expectedList.size(), resultList.size());
        for (DiaEntity expected : expectedList) {
            boolean found = false;
            for (DiaEntity result : resultList) {
                if (result.getId().equals(expected.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }    
    }
    
    @Test
    public void getDiaTest() throws BusinessLogicException {
        DiaEntity result = diaLogic.getDia(viajero.getId(), itinerario.getId(), data.get(0).getId());

        DiaEntity expected = em.find(DiaEntity.class, data.get(0).getId());

        Assert.assertNotNull(expected);
        Assert.assertNotNull(result);
        Assert.assertEquals(expected.getId(), result.getId());
        Assert.assertEquals(expected.getDate(), result.getDate());
        Assert.assertEquals(expected.getCiudad(), result.getCiudad());
    }
    
    @Test
    public void deleteDiaTest() {
        DiaEntity entity = data.get(1);
        diaLogic.deleteDia(viajero.getId(), itinerario.getId(), entity.getId());
        DiaEntity expected = em.find(DiaEntity.class, entity.getId());
        Assert.assertNull(expected);
    }

    @Test
    public void updateDiaTest() {
        DiaEntity entity = data.get(0);
        DiaEntity expected = factory.manufacturePojo(DiaEntity.class);

        expected.setId(entity.getId());

        diaLogic.updateDia(viajero.getId(),itinerario.getId(),expected);

        DiaEntity resp = em.find(DiaEntity.class, entity.getId());

        Assert.assertNotNull(expected);
        Assert.assertEquals(expected.getId(), resp.getId());
        Assert.assertEquals(expected.getDate(), resp.getDate());
        Assert.assertEquals(expected.getCiudad(), resp.getCiudad());
    }
    
    @Test
    public void listEventosTest(){
        List<EventoEntity> list = diaLogic.getEventos(viajero.getId(), itinerario.getId(), data.get(0).getId());
        Assert.assertEquals(1, list.size());
    }
    
    @Test
    public void getEventoTest(){
        DiaEntity entity = data.get(0);
        EventoEntity eventoEntity = eventoData.get(0);
        EventoEntity response = diaLogic.getEvento(viajero.getId(), itinerario.getId(), entity.getId(), eventoEntity.getId());

        Assert.assertEquals(eventoEntity.getName(), response.getName());
        Assert.assertEquals(eventoEntity.getDescription(), response.getDescription());
        Assert.assertEquals(eventoEntity.getCiudad(), response.getCiudad());
        Assert.assertEquals(eventoEntity.getImage(), response.getImage());
        Assert.assertEquals(eventoEntity.getFechaInicio(), response.getFechaInicio());
        Assert.assertEquals(eventoEntity.getFechaFin(), response.getFechaFin());
        Assert.assertEquals(eventoEntity.getType(), response.getType());
    }
    
    @Test
    public void addEventoTest(){
        DiaEntity entity = data.get(0);
        EventoEntity eventoEntity = eventoData.get(1);
        EventoEntity response = diaLogic.addEvento(viajero.getId(), itinerario.getId(), entity.getId(), eventoEntity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(eventoEntity.getId(), response.getId());
    }
    
    @Test
    public void replaceEventosTest(){
            try {
            DiaEntity entity = data.get(0);
            List<EventoEntity> list = eventoData.subList(1, 3);
            diaLogic.replaceEventos(viajero.getId(), itinerario.getId(), entity.getId(), list);

            entity = diaLogic.getDia(viajero.getId(), itinerario.getId(), entity.getId());
            Assert.assertFalse(entity.getEventos().contains(eventoData.get(0)));
            Assert.assertTrue(entity.getEventos().contains(eventoData.get(1)));
            Assert.assertTrue(entity.getEventos().contains(eventoData.get(2)));
        } catch (BusinessLogicException ex) {
            Assert.fail(ex.getLocalizedMessage());
        }
    }
    
    @Test
    public void removeEventoTest(){
        diaLogic.removeEvento(viajero.getId(), itinerario.getId(), data.get(0).getId(), eventoData.get(0).getId());
        EventoEntity response = diaLogic.getEvento(viajero.getId(), itinerario.getId(), data.get(0).getId(),eventoData.get(0).getId());
        Assert.assertNull(response);
    }
}
