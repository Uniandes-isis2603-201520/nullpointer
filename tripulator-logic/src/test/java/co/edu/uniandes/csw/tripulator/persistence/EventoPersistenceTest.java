/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.tripulator.persistence;

import co.edu.uniandes.csw.tripulator.entities.EventoEntity;
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

/**
 *
 * @author Jose Daniel Fandiño
 */
@RunWith(Arquillian.class)
public class EventoPersistenceTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EventoEntity.class.getPackage())
                .addPackage(EventoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Inject
    private EventoPersistence eventoPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private final PodamFactory factory = new PodamFactoryImpl();

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
    }

    private List<EventoEntity> data = new ArrayList<>();

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void getEventosTest() {
        List<EventoEntity> list = eventoPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (EventoEntity ent : list) {
            boolean found = false;
            for (EventoEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void getEventoTest() {
        EventoEntity entity = data.get(0);
        EventoEntity newEntity = eventoPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getName(), newEntity.getName());
        Assert.assertEquals(entity.getDescription(), newEntity.getDescription());
        Assert.assertEquals(entity.getCiudad(), newEntity.getCiudad());
        Assert.assertEquals(entity.getImage(), newEntity.getImage());
        Assert.assertEquals(entity.getFechaInicio(), newEntity.getFechaInicio());
        Assert.assertEquals(entity.getFechaFin(), newEntity.getFechaFin());
        Assert.assertEquals(entity.getType(), newEntity.getType());
    }

}
