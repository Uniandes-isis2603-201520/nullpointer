/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.tripulator.persistence;

import co.edu.uniandes.csw.tripulator.entities.DiaEntity;
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

    @PersistenceContext
    private EntityManager em;

    private final PodamFactory factory = new PodamFactoryImpl();

    @Inject
    UserTransaction utx;

    public DiaPersistenceTest() {
    }

    @Deployment
    public static JavaArchive createDeployement() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(DiaEntity.class.getPackage())
                .addPackage(DiaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    private void clearData() {
        em.createQuery("delete from DiaEntity").executeUpdate();
    }

    List<DiaEntity> data = new ArrayList<>();

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            DiaEntity entity = factory.manufacturePojo(DiaEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    @Before
    public void configTest() {
        try {
            utx.begin();
            clearData();
            insertData();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
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
    }
    
    @Test
    public void getDiasTest() {
        List<DiaEntity> list = diaPersistence.findAll();
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
        DiaEntity newEntity = diaPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getDate(), entity.getDate());
        Assert.assertEquals(newEntity.getCiudad(), entity.getCiudad());
    }
    
    @Test
    public void deleteDiaTest() {
        DiaEntity entity = data.get(0);
        diaPersistence.delete(entity.getId());
        DiaEntity deleted = em.find(DiaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    @Test
    public void updateBookTest() {
        DiaEntity entity = data.get(0);
        DiaEntity newEntity = factory.manufacturePojo(DiaEntity.class);
        newEntity.setId(entity.getId());

        diaPersistence.update(newEntity);

        DiaEntity resp = em.find(DiaEntity.class, entity.getId());

        Assert.assertEquals(resp.getDate(), newEntity.getDate());
        Assert.assertEquals(resp.getCiudad(), newEntity.getCiudad());
    }
    
}