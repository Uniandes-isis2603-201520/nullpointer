/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.tripulator.persistence;

import co.edu.uniandes.csw.tripulator.entities.ComentarioEntity;
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
 * @author Daniel
 */

@RunWith(Arquillian.class)
public class ComentarioPersistenceTest {
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ComentarioEntity.class.getPackage())
                .addPackage(ComentarioPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Inject
    private ComentarioPersistence comentarioPersistence;

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
        em.createQuery("delete from ComentarioEntity").executeUpdate();
    }

    private List<ComentarioEntity> data = new ArrayList<>();

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ComentarioEntity entity = factory.manufacturePojo(ComentarioEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void getComentariosTest() {
        List<ComentarioEntity> list = comentarioPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (ComentarioEntity ent : list) {
            boolean found = false;
            for (ComentarioEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void getComentarioTest() {
        ComentarioEntity entity = data.get(0);
        ComentarioEntity newEntity = comentarioPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getEvento(), newEntity.getEvento());
        Assert.assertEquals(entity.getComment(), newEntity.getComment());
        Assert.assertEquals(entity.getStars(), newEntity.getStars());
        //Assert.assertEquals(entity.getUserPhoto(), newEntity.getUserPhoto());
        //Assert.assertEquals(entity.getUser(), newEntity.getUser());
        Assert.assertEquals(entity.getDate(), newEntity.getDate());
    }
    
    @Test
    public void deleteComentarioTest() {
        ComentarioEntity entity = data.get(0);
        comentarioPersistence.delete(entity.getId());
        ComentarioEntity deleted = em.find(ComentarioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    @Test
    public void updateComentarioTest() {
        ComentarioEntity entity = data.get(0);
        ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
        newEntity.setId(entity.getId());
        comentarioPersistence.update(newEntity);
        ComentarioEntity resp = em.find(ComentarioEntity.class, entity.getId());
        Assert.assertEquals(newEntity.getName(), resp.getName());
    }

}
