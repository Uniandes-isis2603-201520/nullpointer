/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.tripulator.persistence;

import co.edu.uniandes.csw.tripulator.entities.FotoEntity;
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
 * @author josef
 */
@RunWith(Arquillian.class)
public class FotoPersistenceTest {
    
     @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(FotoEntity.class.getPackage())
                .addPackage(FotoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Inject
    private FotoPersistence fotoPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private final PodamFactory factory = new PodamFactoryImpl();

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
        em.createQuery("delete from FotoEntity").executeUpdate();
    }

    private List<FotoEntity> data = new ArrayList<>();

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            FotoEntity entity = factory.manufacturePojo(FotoEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void createFotoTest() {
        FotoEntity newEntity = factory.manufacturePojo(FotoEntity.class);
        FotoEntity result = fotoPersistence.create(newEntity);

        Assert.assertNotNull(result);

        FotoEntity entity = em.find(FotoEntity.class, result.getId());

        Assert.assertEquals(newEntity.getName(), entity.getName());
    }

    @Test
    public void getFotosTest() {
        List<FotoEntity> list = fotoPersistence.findAll(1L,1L);
        Assert.assertEquals(data.size(), list.size());
        for (FotoEntity ent : list) {
            boolean found = false;
            for (FotoEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void deleteFotoTest() {
        FotoEntity entity = data.get(0);
        fotoPersistence.delete(entity.getId());
        FotoEntity deleted = em.find(FotoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }


    
}
