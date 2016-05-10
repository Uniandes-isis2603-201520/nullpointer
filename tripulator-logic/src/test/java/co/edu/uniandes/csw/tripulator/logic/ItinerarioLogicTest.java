/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.tripulator.logic;

import co.edu.uniandes.csw.tripulator.api.IItinerarioLogic;
import co.edu.uniandes.csw.tripulator.ejbs.ItinerarioLogic;
import co.edu.uniandes.csw.tripulator.entities.DiaEntity;
import co.edu.uniandes.csw.tripulator.entities.FotoEntity;
import co.edu.uniandes.csw.tripulator.entities.ItinerarioEntity;
import co.edu.uniandes.csw.tripulator.entities.ViajeroEntity;
import co.edu.uniandes.csw.tripulator.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.tripulator.persistence.ItinerarioPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
 * @author Antonio de la Vega
 */
@RunWith(Arquillian.class)
public class ItinerarioLogicTest {

    private final PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private IItinerarioLogic itinerarioLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private final List<ItinerarioEntity> data = new ArrayList<>();

    private List<FotoEntity> fotoData = new ArrayList<>();

    private List<DiaEntity> diaData = new ArrayList<>();

    private ViajeroEntity viajero;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ItinerarioEntity.class.getPackage())
                .addPackage(ItinerarioLogic.class.getPackage())
                .addPackage(IItinerarioLogic.class.getPackage())
                .addPackage(ItinerarioPersistence.class.getPackage())
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
        em.createQuery("delete from DiaEntity").executeUpdate();
        em.createQuery("delete from ItinerarioEntity").executeUpdate();
        em.createQuery("delete from ViajeroEntity").executeUpdate();
    }

    private void insertData() {

        viajero = factory.manufacturePojo(ViajeroEntity.class);
        em.persist(viajero);

        for (int i = 0; i < 3; i++) {
            ItinerarioEntity entity = factory.manufacturePojo(ItinerarioEntity.class);
            entity.setViajero(viajero);
            em.persist(entity);
            data.add(entity);
        }

        for (int i = 0; i < 3; i++) {
            FotoEntity fotos = factory.manufacturePojo(FotoEntity.class);

            DiaEntity dias = factory.manufacturePojo(DiaEntity.class);

            if (i == 0) {
                fotos.setItinerario(data.get(i));
                dias.setItinerario(data.get(i));
            }

            em.persist(dias);
            diaData.add(dias);

            em.persist(fotos);
            fotoData.add(fotos);
        }
    }

    @Test
    public void createItinerarioTest() throws BusinessLogicException {
        ItinerarioEntity expected = factory.manufacturePojo(ItinerarioEntity.class);
        ItinerarioEntity created = itinerarioLogic.createItinerario(viajero.getId(), expected);

        ItinerarioEntity result = em.find(ItinerarioEntity.class, created.getId());

        Assert.assertNotNull(result);
        Assert.assertNotNull(result);
        Assert.assertEquals(expected.getId(), result.getId());
        Assert.assertEquals(expected.getName(), result.getName());
        Assert.assertEquals(expected.getFechaInicio(), result.getFechaInicio());
        Assert.assertEquals(expected.getFechaFin(), result.getFechaFin());
    }

    @Test
    public void getItinerariosTest() throws BusinessLogicException {
        List<ItinerarioEntity> resultList = itinerarioLogic.getItinerarios(viajero.getId());
        TypedQuery<ItinerarioEntity> q = em.createQuery("select u from "
                    + "ItinerarioEntity u where (u.viajero.id = :idViajero)",
                    ItinerarioEntity.class);
        q.setParameter("idViajero", viajero.getId());
        List<ItinerarioEntity> expectedList = q.getResultList();
        Assert.assertEquals(expectedList.size(), resultList.size());
        for (ItinerarioEntity expected : expectedList) {
            boolean found = false;
            for (ItinerarioEntity result : resultList) {
                if (result.getId().equals(expected.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void getItinerarioTest() throws BusinessLogicException {
        ItinerarioEntity result = itinerarioLogic.getItinerario(viajero.getId(), data.get(0).getId());

        ItinerarioEntity expected = em.find(ItinerarioEntity.class, data.get(0).getId());

        Assert.assertNotNull(expected);
        Assert.assertNotNull(result);
        Assert.assertEquals(expected.getId(), result.getId());
        Assert.assertEquals(expected.getName(), result.getName());
        Assert.assertEquals(expected.getFechaInicio(), result.getFechaInicio());
        Assert.assertEquals(expected.getFechaFin(), result.getFechaFin());
    }

    @Test
    public void deleteItinerarioTest() throws BusinessLogicException {
        ItinerarioEntity entity = data.get(1);
        itinerarioLogic.deleteItinerario(viajero.getId(), entity.getId());
        ItinerarioEntity expected = em.find(ItinerarioEntity.class, entity.getId());
        Assert.assertNull(expected);
    }

    @Test
    public void updateItinerarioTest() throws BusinessLogicException {
        ItinerarioEntity entity = data.get(0);
        ItinerarioEntity expected = factory.manufacturePojo(ItinerarioEntity.class);

        expected.setId(entity.getId());

        itinerarioLogic.updateItinerario(viajero.getId(), expected);

        ItinerarioEntity resp = em.find(ItinerarioEntity.class, entity.getId());

        Assert.assertNotNull(expected);
        Assert.assertEquals(expected.getId(), resp.getId());
        Assert.assertEquals(expected.getName(), resp.getName());
        Assert.assertEquals(expected.getFechaInicio(), resp.getFechaInicio());
        Assert.assertEquals(expected.getFechaFin(), resp.getFechaFin());
    }

    @Test
    public void getPhotoTest() {
        ItinerarioEntity entity = data.get(0);
        FotoEntity fotoEntity = fotoData.get(0);
        FotoEntity response = itinerarioLogic.getPhoto(viajero.getId(), entity.getId(), fotoEntity.getId());

        Assert.assertEquals(fotoEntity.getId(), response.getId());
        Assert.assertEquals(fotoEntity.getName(), response.getName());
        Assert.assertEquals(fotoEntity.getSrc(), response.getSrc());
    }

    @Test
    public void getDayTest() {
        ItinerarioEntity entity = data.get(0);
        DiaEntity diaEntity = diaData.get(0);
        DiaEntity response = itinerarioLogic.getDay(viajero.getId(), entity.getId(), diaEntity.getId());

        Assert.assertEquals(diaEntity.getId(), response.getId());
        Assert.assertEquals(diaEntity.getName(), response.getName());
        Assert.assertEquals(diaEntity.getDate(), response.getDate());
        Assert.assertEquals(diaEntity.getCiudad(), response.getCiudad());
    }

    @Test
    public void listPhotosTest() {
        List<FotoEntity> list = itinerarioLogic.getPhotos(viajero.getId(), data.get(0).getId());
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void listDaysTest() {
        List<DiaEntity> list = itinerarioLogic.getDays(viajero.getId(), data.get(0).getId());
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void addPhotosTest() {
        ItinerarioEntity entity = data.get(0);
        FotoEntity fotoEntity = fotoData.get(1);
        FotoEntity response = itinerarioLogic.addPhoto(viajero.getId(), entity.getId(), fotoEntity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(fotoEntity.getId(), response.getId());
    }

    @Test
    public void addDaysTest() {
        ItinerarioEntity entity = data.get(0);
        DiaEntity diaEntity = diaData.get(1);
        DiaEntity response = itinerarioLogic.addDay(viajero.getId(), entity.getId(), diaEntity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(diaEntity.getId(), response.getId());
    }

    @Test
    public void replacePhotosTest() {
        try {
            ItinerarioEntity entity = data.get(0);
            List<FotoEntity> list = fotoData.subList(1, 3);
            itinerarioLogic.replacePhotos(list, viajero.getId(), entity.getId());

            entity = itinerarioLogic.getItinerario(viajero.getId(), entity.getId());
            Assert.assertFalse(entity.getFotos().contains(fotoData.get(0)));
            Assert.assertTrue(entity.getFotos().contains(fotoData.get(1)));
            Assert.assertTrue(entity.getFotos().contains(fotoData.get(2)));
        } catch (BusinessLogicException ex) {
            Assert.fail(ex.getLocalizedMessage());
        }
    }

    @Test
    public void replaceDaysTest() {
        try {
            ItinerarioEntity entity = data.get(0);
            List<DiaEntity> list = diaData.subList(1, 3);
            itinerarioLogic.replaceDays(list, viajero.getId(), entity.getId());

            entity = itinerarioLogic.getItinerario(viajero.getId(), entity.getId());
            Assert.assertFalse(entity.getDias().contains(diaData.get(0)));
            Assert.assertTrue(entity.getDias().contains(diaData.get(1)));
            Assert.assertTrue(entity.getDias().contains(diaData.get(2)));
        } catch (BusinessLogicException ex) {
            Assert.fail(ex.getLocalizedMessage());
        }
    }

    @Test
    public void removePhotosTest() {
        itinerarioLogic.removePhoto(viajero.getId(), data.get(0).getId(), fotoData.get(0).getId());
        FotoEntity response = itinerarioLogic.getPhoto(viajero.getId(), data.get(0).getId(), fotoData.get(0).getId());
        Assert.assertNull(response);
    }
}
