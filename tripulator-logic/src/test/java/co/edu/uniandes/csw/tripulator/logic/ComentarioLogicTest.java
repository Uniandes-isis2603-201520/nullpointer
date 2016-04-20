/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.tripulator.logic;

import co.edu.uniandes.csw.tripulator.api.IComentarioLogic;
import co.edu.uniandes.csw.tripulator.ejbs.ComentarioLogic;
import co.edu.uniandes.csw.tripulator.entities.ComentarioEntity;
import co.edu.uniandes.csw.tripulator.entities.EventoEntity;
import co.edu.uniandes.csw.tripulator.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.tripulator.persistence.ComentarioPersistence;
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
public class ComentarioLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private IComentarioLogic comentarioLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ComentarioEntity> data = new ArrayList<ComentarioEntity>();

    private EventoEntity evento;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ComentarioEntity.class.getPackage())
                .addPackage(ComentarioLogic.class.getPackage())
                .addPackage(IComentarioLogic.class.getPackage())
                .addPackage(ComentarioPersistence.class.getPackage())
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
    }

    private void insertData() {

        evento = factory.manufacturePojo(EventoEntity.class);

        for (int i = 0; i < 3; i++) {
            ComentarioEntity entity = factory.manufacturePojo(ComentarioEntity.class);
            em.persist(entity);
            data.add(entity);

        }
    }

    @Test
    public void getComentarioTest() {
        try {
            ComentarioEntity entity = data.get(0);
            ComentarioEntity resultEntity = comentarioLogic.getComentario(entity.getId());
            Assert.assertNotNull(resultEntity);
            Assert.assertEquals(entity.getId(), resultEntity.getId());
            Assert.assertEquals(entity.getStars(), resultEntity.getStars());
            Assert.assertEquals(entity.getComment(), resultEntity.getComment());
            Assert.assertEquals(entity.getDate(), resultEntity.getDate());
            Assert.assertEquals(entity.getUserPhoto(), resultEntity.getUserPhoto());
            //Assert.assertEquals(entity.getUser(), resultEntity.getUser());
            Assert.assertEquals(entity.getEvento(), resultEntity.getEvento());
        } catch (BusinessLogicException ex) {
            Assert.fail(ex.getLocalizedMessage());
        }
    }

    @Test
    public void getComentariosTest() {
        List<ComentarioEntity> list = comentarioLogic.getComentarios();
        Assert.assertEquals(data.size(), list.size());
        for (ComentarioEntity entity : list) {
            boolean found = false;
            for (ComentarioEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void createComentarioTest() {
        try {
            ComentarioEntity entity = factory.manufacturePojo(ComentarioEntity.class);
            ComentarioEntity result = comentarioLogic.createComentario(entity);

            ComentarioEntity resp = em.find(ComentarioEntity.class, result.getId());

            Assert.assertNotNull(result);
            Assert.assertEquals(entity.getId(), resp.getId());
            Assert.assertEquals(entity.getStars(), resp.getStars());
            Assert.assertEquals(entity.getComment(), resp.getComment());
            Assert.assertEquals(entity.getDate(), resp.getDate());
            Assert.assertEquals(entity.getUserPhoto(), resp.getUserPhoto());
            //Assert.assertEquals(entity.getUser(), resp.getUser());
            Assert.assertEquals(entity.getEvento(), resp.getEvento());
        } catch (BusinessLogicException ex) {
            Assert.fail(ex.getLocalizedMessage());
        }
    }

    @Test
    public void deleteComentarioTest() {
        ComentarioEntity entity = data.get(1);
        comentarioLogic.deleteComentario(entity.getId());
        ComentarioEntity deleted = em.find(ComentarioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    @Test
    public void updateComentarioTest() {
        try {
            ComentarioEntity entity = data.get(0);
            ComentarioEntity pojoEntity = factory.manufacturePojo(ComentarioEntity.class);
            pojoEntity.setId(entity.getId());

            comentarioLogic.updateComentario(pojoEntity);

            ComentarioEntity resp = em.find(ComentarioEntity.class, entity.getId());

            Assert.assertEquals(pojoEntity.getId(), resp.getId());
            Assert.assertEquals(pojoEntity.getStars(), resp.getStars());
            Assert.assertEquals(pojoEntity.getComment(), resp.getComment());
            Assert.assertEquals(pojoEntity.getDate(), resp.getDate());
            Assert.assertEquals(pojoEntity.getUserPhoto(), resp.getUserPhoto());
            //Assert.assertEquals(pojoEntity.getUser(), resp.getUser());
            Assert.assertEquals(pojoEntity.getEvento(), resp.getEvento());
        } catch (BusinessLogicException ex) {
            Assert.fail(ex.getLocalizedMessage());
        }
    }
}
