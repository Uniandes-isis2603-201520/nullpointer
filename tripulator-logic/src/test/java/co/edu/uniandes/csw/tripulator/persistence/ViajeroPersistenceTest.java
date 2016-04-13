/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.tripulator.persistence;

import co.edu.uniandes.csw.tripulator.entities.ViajeroEntity;
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
public class ViajeroPersistenceTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ViajeroEntity.class.getPackage())
                .addPackage(ViajeroPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Inject
    private ViajeroPersistence authorPersistence;

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
        em.createQuery("delete from ViajeroEntity").executeUpdate();
    }

    private List<ViajeroEntity> data = new ArrayList<>();

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ViajeroEntity entity = factory.manufacturePojo(ViajeroEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void createViajeroTest() {
        ViajeroEntity newEntity = factory.manufacturePojo(ViajeroEntity.class);
        ViajeroEntity result = authorPersistence.create(newEntity);

        Assert.assertNotNull(result);

        ViajeroEntity entity = em.find(ViajeroEntity.class, result.getId());

        Assert.assertEquals(newEntity.getName(), entity.getName());
    }

    @Test
    public void getViajerosTest() {
        List<ViajeroEntity> list = authorPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (ViajeroEntity ent : list) {
            boolean found = false;
            for (ViajeroEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void getViajeroTest() {
        ViajeroEntity entity = data.get(0);
        ViajeroEntity newEntity = authorPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getName(), newEntity.getName());
        Assert.assertEquals(entity.getApellido(), newEntity.getApellido());
        Assert.assertEquals(entity.getPassword(), newEntity.getPassword());
        Assert.assertEquals(entity.getEmail(), newEntity.getEmail());
        Assert.assertEquals(entity.getUsuario(), newEntity.getUsuario());
        
        
    }

    @Test
    public void deleteViajeroTest() {
        ViajeroEntity entity = data.get(0);
        authorPersistence.delete(entity.getId());
        ViajeroEntity deleted = em.find(ViajeroEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    @Test
    public void updateViajeroTest() {
        ViajeroEntity entity = data.get(0);
        ViajeroEntity newEntity = factory.manufacturePojo(ViajeroEntity.class);

        newEntity.setId(entity.getId());

        authorPersistence.update(newEntity);

        ViajeroEntity resp = em.find(ViajeroEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getName(), resp.getName());
    }
}
