/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.tripulator.logic;

import co.edu.uniandes.csw.tripulator.api.IFotoLogic;
import co.edu.uniandes.csw.tripulator.ejbs.FotoLogic;
import co.edu.uniandes.csw.tripulator.entities.ComentarioEntity;
import co.edu.uniandes.csw.tripulator.entities.FotoEntity;
import co.edu.uniandes.csw.tripulator.entities.FotoEntity;
import co.edu.uniandes.csw.tripulator.entities.ViajeroEntity;
import co.edu.uniandes.csw.tripulator.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.tripulator.persistence.FotoPersistence;
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
public class FotoLogicTest {
    
     private final PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private IFotoLogic fotoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private final List<FotoEntity> data = new ArrayList<>();
    
    private ViajeroEntity viajero;
    
    private FotoEntity itinerario;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(FotoEntity.class.getPackage())
                .addPackage(FotoLogic.class.getPackage())
                .addPackage(IFotoLogic.class.getPackage())
                .addPackage(FotoPersistence.class.getPackage())
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
        em.createQuery("delete from FotoEntity").executeUpdate();
    }

    private void insertData() {
        
        viajero = factory.manufacturePojo(ViajeroEntity.class);
        
        itinerario = factory.manufacturePojo(FotoEntity.class);

        for (int i = 0; i < 3; i++) {
            FotoEntity entity = factory.manufacturePojo(FotoEntity.class);
            em.persist(entity);
            data.add(entity);
            
        }
    }
   
    @Test
    public void createFotoTest() {
        FotoEntity expected = factory.manufacturePojo(FotoEntity.class);
        FotoEntity created = fotoLogic.createFoto(expected);

        FotoEntity result = em.find(FotoEntity.class, created.getId());

        Assert.assertNotNull(result);
        Assert.assertNotNull(result);
        Assert.assertEquals(expected.getId(), result.getId());
        Assert.assertEquals(expected.getSrc(), result.getSrc());

    }

    @Test
    public void getFotosTest() {
        List<FotoEntity> resultList = fotoLogic.getFotos(viajero.getId(), itinerario.getId());
        List<FotoEntity> expectedList = em.createQuery("SELECT u from FotoEntity u").getResultList();
        Assert.assertEquals(expectedList.size(), resultList.size());
        for (FotoEntity expected : expectedList) {
            boolean found = false;
            for (FotoEntity result : resultList) {
                if (result.getId().equals(expected.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
        @Test
    public void deleteFotoTest() {
        FotoEntity entity = data.get(1);
        fotoLogic.deleteFoto(entity.getId());
        FotoEntity expected = em.find(FotoEntity.class, entity.getId());
        Assert.assertNull(expected);
    }

}
