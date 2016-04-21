/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.tripulator.rest;
import co.edu.uniandes.nullpointer.rest.tripulator.converters.ViajeroConverter;
import co.edu.uniandes.nullpointer.rest.tripulator.dtos.ItinerarioDTO;
import co.edu.uniandes.nullpointer.rest.tripulator.dtos.ViajeroDTO;
import co.edu.uniandes.nullpointer.rest.tripulator.resources.ViajeroResource;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@RunWith(Arquillian.class)
public class ViajeroResourceIT {

    private final int OK = Status.OK.getStatusCode();
    private final int CREATED = Status.CREATED.getStatusCode();
    private final int NO_CONTENT = Status.NO_CONTENT.getStatusCode();

    private final String viajeroPath = "/viajeros";
    private final static List<ViajeroDTO> oraculo = new ArrayList<>();
    private final static List<ItinerarioDTO> oraculoItinerarios = new ArrayList<>();

    private WebTarget target;
    private final String apiPath = "api";
    private static PodamFactory factory = new PodamFactoryImpl();

    @ArquillianResource
    private URL deploymentURL;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                // Se agrega la dependencia a la logica con el nombre groupid:artefactid:version (GAV)
                .addAsLibraries(Maven.resolver()
                         .resolve("co.edu.uniandes.tripulator:tripulator-logic:1.0-SNAPSHOT")
                        .withTransitivity().asFile())
                // Se agregan los compilados de los paquetes de servicios
                .addPackage(ViajeroResource.class.getPackage())
                .addPackage(ViajeroDTO.class.getPackage())
                .addPackage(ViajeroConverter.class.getPackage())
                // El archivo que contiene la configuracion a la base de datos.
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                // El archivo beans.xml es necesario para injeccion de dependencias.
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"))
                // El archivo web.xml es necesario para el despliegue de los servlets
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"));
    }

    @Before
    public void setUpTest() {
        target = ClientBuilder.newClient().target(deploymentURL.toString()).path(apiPath);
    }

    @BeforeClass
    public static void setUp() {
        insertData();
    }

    public static void insertData() {
        for (int i = 0; i < 5; i++) {
            ViajeroDTO viajero = factory.manufacturePojo(ViajeroDTO.class);

            viajero.setId(i + 1L);

            oraculo.add(viajero);
        }
    }

    @Test
    @InSequence(1)
    public void createViajeroTest() {
        ViajeroDTO viajero = oraculo.get(0);
        Response response = target.path(viajeroPath).request().post(Entity.entity(viajero, MediaType.APPLICATION_JSON));
        ViajeroDTO viajeroTest = (ViajeroDTO) response.readEntity(ViajeroDTO.class);

        Assert.assertEquals(viajero.getNombre(), viajeroTest.getNombre());
        Assert.assertEquals(viajero.getApellido(), viajeroTest.getApellido());
        Assert.assertEquals(viajero.getUsuario(), viajeroTest.getUsuario());
        Assert.assertEquals(viajero.getEmail(), viajeroTest.getEmail());
        Assert.assertEquals(viajero.getPassword(), viajeroTest.getPassword());
        
        Assert.assertEquals(OK, response.getStatus());
    }
    

    @Test
    @InSequence(2)
    public void getViajeroById() {
        ViajeroDTO viajeroTest = target.path(viajeroPath)
                .path(oraculo.get(0).getId().toString())
                .request().get(ViajeroDTO.class);
        Assert.assertEquals(viajeroTest.getId(), oraculo.get(0).getId());
        Assert.assertEquals(viajeroTest.getNombre(), oraculo.get(0).getNombre());
        Assert.assertEquals(viajeroTest.getApellido(), oraculo.get(0).getApellido());
        Assert.assertEquals(viajeroTest.getEmail(), oraculo.get(0).getEmail());
        Assert.assertEquals(viajeroTest.getUsuario(), oraculo.get(0).getUsuario());
        Assert.assertEquals(viajeroTest.getPassword(), oraculo.get(0).getPassword());

    }

    @Test
    @InSequence(3)
    public void listViajeroTest() {
        Response response = target.path(viajeroPath)
                .request().get();

        List<ViajeroDTO> listViajeroTest = response.readEntity(new GenericType<List<ViajeroDTO>>() {
        });
        Assert.assertEquals(OK, response.getStatus());
        Assert.assertEquals(1, listViajeroTest.size());
    }

    @Test
    @InSequence(4)
    public void updateViajeroTest() {
        ViajeroDTO viajero = oraculo.get(0);
        ViajeroDTO viajeroChanged = factory.manufacturePojo(ViajeroDTO.class);
        viajero.setNombre(viajeroChanged.getNombre());
        viajero.setApellido(viajeroChanged.getApellido());
        viajero.setEmail(viajeroChanged.getEmail());
        viajero.setUsuario(viajeroChanged.getUsuario());
        viajero.setPassword(viajeroChanged.getPassword());
        
        Response response = target.path(viajeroPath).path(viajero.getId().toString())
                .request().put(Entity.entity(viajero, MediaType.APPLICATION_JSON));
        ViajeroDTO viajeroTest = (ViajeroDTO) response.readEntity(ViajeroDTO.class);
        Assert.assertEquals(OK, response.getStatus());
        Assert.assertEquals(viajero.getNombre(), viajeroTest.getNombre());
        Assert.assertEquals(viajero.getApellido(), viajeroTest.getApellido());
        Assert.assertEquals(viajero.getUsuario(), viajeroTest.getUsuario());
        Assert.assertEquals(viajero.getEmail(), viajeroTest.getEmail());
        Assert.assertEquals(viajero.getPassword(), viajeroTest.getPassword());
    }

    @Test
    @InSequence(5)
    public void deleteViajeroTest() {
        ViajeroDTO viajero = oraculo.get(0);
        Response response = target.path(viajeroPath).path(viajero.getId().toString())
                .request().delete();
        Assert.assertEquals(NO_CONTENT, response.getStatus());
    }
/**
    @Test
    @InSequence(5)
    public void addItinerariosTest() {
        ItinerarioDTO itinerarios = oraculoItinerarios.get(0);
        ViajeroDTO viajero = oraculo.get(0);

        Response response = target.path("itinerarios")
                .request()
                .post(Entity.entity(itinerarios, MediaType.APPLICATION_JSON));

        Assert.assertEquals(response.getStatus(), CREATED);
        ItinerarioDTO itinerariosTest = (ItinerarioDTO) response.readEntity(ItinerarioDTO.class);
        Assert.assertEquals(itinerarios.getId(), itinerariosTest.getId());
        Assert.assertEquals(itinerarios.getName(), itinerariosTest.getName());
        Assert.assertEquals(itinerarios.getDescription(), itinerariosTest.getDescription());
        Assert.assertEquals(itinerarios.getIsbn(), itinerariosTest.getIsbn());
        Assert.assertEquals(itinerarios.getImage(), itinerariosTest.getImage());
        Assert.assertEquals(CREATED, response.getStatus());

        response = target.path(viajeroPath).path(viajero.getId().toString())
                .path(itinerariosPath).path(itinerarios.getId().toString())
                .request()
                .post(Entity.entity(itinerarios, MediaType.APPLICATION_JSON));

        itinerariosTest = (ItinerarioDTO) response.readEntity(ItinerarioDTO.class);
        Assert.assertEquals(OK, response.getStatus());
        Assert.assertEquals(itinerarios.getId(), itinerariosTest.getId());
    }

    @Test
    @InSequence(6)
    public void listItinerariosTest() {
        ViajeroDTO viajero = oraculo.get(0);

        Response response = target.path(viajeroPath)
                .path(viajero.getId().toString())
                .path(itinerariosPath)
                .request().get();

        List<ItinerarioDTO> itinerariosListTest = response.readEntity(new GenericType<List<ItinerarioDTO>>() {
        });
        Assert.assertEquals(OK, response.getStatus());
        Assert.assertEquals(1, itinerariosListTest.size());
    }

    @Test
    @InSequence(7)
    public void getItinerariosTest() {
        ItinerarioDTO itinerarios = oraculoItinerarios.get(0);
        ViajeroDTO viajero = oraculo.get(0);

        ItinerarioDTO itinerariosTest = target.path(viajeroPath)
                .path(viajero.getId().toString()).path(itinerariosPath)
                .path(itinerarios.getId().toString())
                .request().get(ItinerarioDTO.class);

        Assert.assertEquals(itinerarios.getId(), itinerariosTest.getId());
        Assert.assertEquals(itinerarios.getName(), itinerariosTest.getName());
        Assert.assertEquals(itinerarios.getDescription(), itinerariosTest.getDescription());
        Assert.assertEquals(itinerarios.getIsbn(), itinerariosTest.getIsbn());
        Assert.assertEquals(itinerarios.getImage(), itinerariosTest.getImage());
    }

    @Test
    @InSequence(8)
    public void removeItinerariosTest() {
        ItinerarioDTO itinerarios = oraculoItinerarios.get(0);
        ViajeroDTO viajero = oraculo.get(0);

        Response response = target.path(viajeroPath).path(viajero.getId().toString())
                .path(itinerariosPath).path(itinerarios.getId().toString())
                .request().delete();
        Assert.assertEquals(NO_CONTENT, response.getStatus());
    }
*/
}
