package co.edu.uniandes.csw.tripulator.rest;

import co.edu.uniandes.nullpointer.rest.tripulator.adapter.DateAdapter;
import co.edu.uniandes.nullpointer.rest.tripulator.converters.EventoConverter;
import co.edu.uniandes.nullpointer.rest.tripulator.converters.ItinerarioConverter;
import co.edu.uniandes.nullpointer.rest.tripulator.converters.TripulatorLogicExceptionMapper;
import co.edu.uniandes.nullpointer.rest.tripulator.dtos.ComentarioDTO;
import co.edu.uniandes.nullpointer.rest.tripulator.dtos.DiaDTO;
import co.edu.uniandes.nullpointer.rest.tripulator.dtos.EventoDTO;
import co.edu.uniandes.nullpointer.rest.tripulator.dtos.ItinerarioDTO;
import co.edu.uniandes.nullpointer.rest.tripulator.resources.EventoResource;
import co.edu.uniandes.nullpointer.rest.tripulator.resources.ItinerarioResource;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
public class EventoResourceIT {

    private final int OK = Status.OK.getStatusCode();
    private final int CREATED = Status.CREATED.getStatusCode();
    private final int NO_CONTENT = Status.NO_CONTENT.getStatusCode();

    private final String eventoPath = "eventos";
    private final String diasPath = "dias";

    private final static List<EventoDTO> oraculo = new ArrayList<>();
    private final static List<DiaDTO> oraculoDias = new ArrayList<>();

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
                .addPackage(EventoResource.class.getPackage())
                .addPackage(EventoDTO.class.getPackage())
                .addPackage(EventoConverter.class.getPackage())
                .addPackage(TripulatorLogicExceptionMapper.class.getPackage())
                .addPackage(DateAdapter.class.getPackage())
                //.addPackage(CreatedFilter.class.getPackage())
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
            EventoDTO evento = factory.manufacturePojo(EventoDTO.class);
            evento.setId(i + 1L);
            List<ComentarioDTO> comentariosList = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                ComentarioDTO comentarios = factory.manufacturePojo(ComentarioDTO.class);
                comentarios.setId(i + 1L);
                comentariosList.add(comentarios);
            }

            evento.setComentarios(comentariosList);

            oraculo.add(evento);

            DiaDTO dias = factory.manufacturePojo(DiaDTO.class);
            dias.setId(i + 1L);
            oraculoDias.add(dias);
        }
    }

    @Test
    @InSequence(1)
    public void createEventoTest() {
        EventoDTO evento = oraculo.get(0);
        Response response = target.path(eventoPath).request()
                .post(Entity.entity(evento, MediaType.APPLICATION_JSON));
        EventoDTO eventoTest = (EventoDTO) response.readEntity(EventoDTO.class);

        Assert.assertEquals(OK, response.getStatus());

        Assert.assertEquals(evento.getId(), eventoTest.getId());
        Assert.assertEquals(evento.getTitle(), eventoTest.getTitle());
        Assert.assertEquals(evento.getDescription(), eventoTest.getDescription());
        Assert.assertEquals(evento.getCiudad(), eventoTest.getCiudad());
        Assert.assertEquals(evento.getImage(), eventoTest.getImage());
        Assert.assertEquals(evento.getStart(), eventoTest.getStart());
        Assert.assertEquals(evento.getEnd(), eventoTest.getEnd());
        Assert.assertEquals(evento.getType(), eventoTest.getType());
    }

    @Test
    @InSequence(2)
    public void getEventoById() {
        EventoDTO eventoTest = target.path(eventoPath)
                .path(oraculo.get(0).getId().toString())
                .request().get(EventoDTO.class);

        Assert.assertEquals(eventoTest.getId(), oraculo.get(0).getId());
        Assert.assertEquals(eventoTest.getTitle(), oraculo.get(0).getTitle());
        Assert.assertEquals(eventoTest.getDescription(), oraculo.get(0).getDescription());
        Assert.assertEquals(eventoTest.getCiudad(), oraculo.get(0).getCiudad());
        Assert.assertEquals(eventoTest.getImage(), oraculo.get(0).getImage());
        Assert.assertEquals(eventoTest.getStart(), oraculo.get(0).getStart());
        Assert.assertEquals(eventoTest.getEnd(), oraculo.get(0).getEnd());
        Assert.assertEquals(eventoTest.getType(), oraculo.get(0).getType());
    }

    @Test
    @InSequence(3)
    public void listEventoTest() {
        Response response = target.path(eventoPath)
                .request().get();

        List<EventoDTO> listEventoTest = response.readEntity(new GenericType<List<EventoDTO>>() {
        });
        Assert.assertEquals(OK, response.getStatus());
        Assert.assertEquals(1, listEventoTest.size());
    }

    @Test
    @InSequence(4)
    public void updateEventoTest() {
        EventoDTO evento = oraculo.get(0);
        EventoDTO eventoChanged = factory.manufacturePojo(EventoDTO.class);
        eventoChanged.setEnd(getMaxDate());
        evento.setTitle(eventoChanged.getTitle());
        evento.setDescription(eventoChanged.getDescription());
        evento.setImage(eventoChanged.getImage());
        evento.setStart(eventoChanged.getStart());
        evento.setCiudad(eventoChanged.getCiudad());
        evento.setType(eventoChanged.getType());
        Response response = target.path(eventoPath).path(evento.getId().toString())
                .request().put(Entity.entity(evento, MediaType.APPLICATION_JSON));
        EventoDTO eventoTest = (EventoDTO) response.readEntity(EventoDTO.class);
        Assert.assertEquals(OK, response.getStatus());

        Assert.assertEquals(evento.getId(), eventoTest.getId());
        Assert.assertEquals(evento.getTitle(), eventoTest.getTitle());
        Assert.assertEquals(evento.getDescription(), eventoTest.getDescription());
        Assert.assertEquals(evento.getCiudad(), eventoTest.getCiudad());
        Assert.assertEquals(evento.getImage(), eventoTest.getImage());
        Assert.assertEquals(evento.getStart(), eventoTest.getStart());
        Assert.assertEquals(evento.getEnd(), eventoTest.getEnd());
        Assert.assertEquals(evento.getType(), eventoTest.getType());
    }

    @Test
    @InSequence(9)
    public void deleteEventoTest() {
        EventoDTO evento = oraculo.get(0);
        Response response = target.path(eventoPath).path(evento.getId().toString())
                .request().delete();
        Assert.assertEquals(NO_CONTENT, response.getStatus());
    }

    @Test
    @InSequence(5)
    public void addDiasTest() {

        DiaDTO dias = oraculoDias.get(0);
        EventoDTO evento = oraculo.get(0);

        Response response = target.path("dias").request()
                .post(Entity.entity(dias, MediaType.APPLICATION_JSON));

        DiaDTO diasTest = (DiaDTO) response.readEntity(DiaDTO.class);

        Assert.assertEquals(dias.getId(), diasTest.getId());
        Assert.assertEquals(dias.getFecha(), diasTest.getFecha());
        Assert.assertEquals(dias.getCiudad(), diasTest.getCiudad());
        Assert.assertEquals(CREATED, response.getStatus());

        response = target.path(eventoPath).path(evento.getId().toString())
                .path(diasPath).path(dias.getId().toString()).request()
                .post(Entity.entity(dias, MediaType.APPLICATION_JSON));

        diasTest = (DiaDTO) response.readEntity(DiaDTO.class);
        Assert.assertEquals(OK, response.getStatus());
        Assert.assertEquals(dias.getId(), diasTest.getId());
    }

    @Test
    @InSequence(6)
    public void listDiasTest() {
        EventoDTO evento = oraculo.get(0);

        Response response = target.path(eventoPath)
                .path(evento.getId().toString())
                .path(diasPath)
                .request().get();

        List<DiaDTO> diasListTest = response.readEntity(new GenericType<List<DiaDTO>>() {
        });
        Assert.assertEquals(OK, response.getStatus());
        Assert.assertEquals(1, diasListTest.size());
    }

    @Test
    @InSequence(7)
    public void getDiasTest() {
        DiaDTO dias = oraculoDias.get(0);
        EventoDTO evento = oraculo.get(0);

        DiaDTO diasTest = target.path(eventoPath)
                .path(evento.getId().toString()).path(diasPath)
                .path(dias.getId().toString())
                .request().get(DiaDTO.class);

        Assert.assertEquals(dias.getId(), diasTest.getId());
        Assert.assertEquals(dias.getFecha(), diasTest.getFecha());
        Assert.assertEquals(dias.getCiudad(), diasTest.getCiudad());
    }

    @Test
    @InSequence(8)
    public void removeDiasTest() {
        DiaDTO dias = oraculoDias.get(0);
        EventoDTO evento = oraculo.get(0);

        Response response = target.path(eventoPath).path(evento.getId().toString())
                .path(diasPath).path(dias.getId().toString())
                .request().delete();
        Assert.assertEquals(NO_CONTENT, response.getStatus());
    }

    private static Date getMaxDate() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 9999);
        c.set(Calendar.DAY_OF_YEAR, c.getActualMaximum(Calendar.DAY_OF_YEAR));
        c.set(Calendar.HOUR_OF_DAY, c.getActualMinimum(Calendar.HOUR_OF_DAY));
        c.set(Calendar.MINUTE, c.getActualMinimum(Calendar.MINUTE));
        c.set(Calendar.SECOND, c.getActualMinimum(Calendar.SECOND));
        c.set(Calendar.MILLISECOND, c.getActualMinimum(Calendar.MILLISECOND));
        return c.getTime();
    }
}
