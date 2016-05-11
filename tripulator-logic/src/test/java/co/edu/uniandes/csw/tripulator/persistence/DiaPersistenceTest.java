/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.tripulator.persistence;

import co.edu.uniandes.csw.tripulator.entities.DiaEntity;
import co.edu.uniandes.csw.tripulator.entities.ItinerarioEntity;
import co.edu.uniandes.csw.tripulator.entities.ViajeroEntity;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


/**
 *
 * @author Nicolás
 */
@RunWith(Arquillian.class)
public class DiaPersistenceTest {

    @Inject
    private DiaPersistence diaPersistence;

    @Inject
    UserTransaction utx;

    @PersistenceContext
    private EntityManager em;

    private final PodamFactory factory = new PodamFactoryImpl();
    
    private final List<DiaEntity> data = new ArrayList<>();
    
    private ViajeroEntity viajero;
    
    private ItinerarioEntity itinerario;
    
    @Deployment
    public static JavaArchive createDeployement() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(DiaEntity.class.getPackage())
                .addPackage(DiaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
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
        em.createQuery("delete from DiaEntity").executeUpdate();
        em.createQuery("delete from ItinerarioEntity").executeUpdate();
        em.createQuery("delete from ViajeroEntity").executeUpdate();
    }

    private void insertData() {
        viajero = factory.manufacturePojo(ViajeroEntity.class);
        em.persist(viajero);
        itinerario = factory.manufacturePojo(ItinerarioEntity.class);
        itinerario.setViajero(viajero);
        em.persist(itinerario);
        for (int i = 0; i < 3; i++) {
            DiaEntity entity = factory.manufacturePojo(DiaEntity.class);
            entity.setItinerario(itinerario);
            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void createDiaTest() {
        DiaEntity newEntity = factory.manufacturePojo(DiaEntity.class);
        DiaEntity result = diaPersistence.create(newEntity);

        Assert.assertNotNull(result);
        DiaEntity entity = em.find(DiaEntity.class, newEntity.getId());
        Assert.assertEquals(newEntity.getDate(), entity.getDate());
        Assert.assertEquals(newEntity.getCiudad(), entity.getCiudad());
        Assert.assertEquals(newEntity.getPais(), entity.getPais());
    }
    
    @Test
    public void getDiasTest() {
        List<DiaEntity> list = diaPersistence.findAll(itinerario.getId());
        Assert.assertEquals(data.size(), list.size());
        for (DiaEntity d : list) {
            boolean found = false;
            for (DiaEntity e : data){
                if (d.getId().equals(e.getId()))
                    found=true;
            }
            Assert.assertTrue(found);
        }
    }
    
    @Test
    public void getDiaTest() {
        DiaEntity entity = data.get(0);
        DiaEntity newEntity = diaPersistence.find(itinerario.getId(), entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getDate(), entity.getDate());
        Assert.assertEquals(newEntity.getCiudad(), entity.getCiudad());
        Assert.assertEquals(newEntity.getPais(), entity.getPais());
    }
    
    @Test
    public void deleteDiaTest() {
        DiaEntity entity = data.get(0);
        diaPersistence.delete(entity.getId());
        DiaEntity deleted = em.find(DiaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    @Test
    public void updateDiaTest() {
        DiaEntity entity = data.get(0);
        DiaEntity newEntity = factory.manufacturePojo(DiaEntity.class);
        newEntity.setId(entity.getId());

        diaPersistence.update(newEntity);

        DiaEntity resp = em.find(DiaEntity.class, entity.getId());

        Assert.assertEquals(resp.getDate(), newEntity.getDate());
        Assert.assertEquals(resp.getCiudad(), newEntity.getCiudad());
        Assert.assertEquals(resp.getPais(), newEntity.getPais());
    }
}