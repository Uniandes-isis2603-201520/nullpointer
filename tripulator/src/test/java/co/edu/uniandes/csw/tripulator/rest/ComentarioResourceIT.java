/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.tripulator.rest;

import co.edu.uniandes.nullpointer.rest.tripulator.adapter.DateAdapter;
import co.edu.uniandes.nullpointer.rest.tripulator.converters.ComentarioConverter;
import co.edu.uniandes.nullpointer.rest.tripulator.converters.TripulatorLogicExceptionMapper;
import co.edu.uniandes.nullpointer.rest.tripulator.dtos.ComentarioDTO;
import co.edu.uniandes.nullpointer.rest.tripulator.resources.ComentarioResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

/**
 *
 * @author Antonio de la Vega
 */
@RunWith(Arquillian.class)
public class ComentarioResourceIT {
    
    private final int OK = Response.Status.OK.getStatusCode();
    private final int CREATED = Response.Status.CREATED.getStatusCode();
    private final int NO_CONTENT = Response.Status.NO_CONTENT.getStatusCode();

    private final String comentarioPath = "/viajeros/1/comentarios";

    private final static List<ComentarioDTO> oraculo = new ArrayList<>();

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
                .addPackage(ComentarioResource.class.getPackage())
                .addPackage(ComentarioDTO.class.getPackage())
                .addPackage(ComentarioConverter.class.getPackage())
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

    private WebTarget createWebTarget() {
        return ClientBuilder.newClient().target(deploymentURL.toString()).path(apiPath);
    }

    @BeforeClass
    public static void setUp() {
        insertData();
    }

    public static void insertData() {
        for (int i = 0; i < 5; i++) {

            ComentarioDTO comentario = factory.manufacturePojo(ComentarioDTO.class);
            comentario.setId(i + 1L);

            oraculo.add(comentario);
        }
    }

    @Before
    public void setUpTest() {
        target = createWebTarget();
    }

    @Test
    @InSequence(1)
    public void createComentarioTest() throws IOException {
        ComentarioDTO comentario = oraculo.get(0);

        Response response = target.path(comentarioPath).request()
                .post(Entity.entity(comentario, MediaType.APPLICATION_JSON));

        ComentarioDTO comentarioTest = (ComentarioDTO) response.readEntity(ComentarioDTO.class);

        Assert.assertEquals(CREATED, response.getStatus());
        Assert.assertEquals(comentario.getComment(), comentarioTest.getComment());
    }

    @Test
    @InSequence(2)
    public void getComentarioById() {

        ComentarioDTO comentarioTest = target.path(comentarioPath)
                .path(oraculo.get(0).getId().toString())
                .request().get(ComentarioDTO.class);
        Assert.assertEquals(comentarioTest.getId(), oraculo.get(0).getId());
        Assert.assertEquals(comentarioTest.getComment(), oraculo.get(0).getComment());
    }

    @Test
    @InSequence(3)
    public void listComentarioTest() throws IOException {

        Response response = target.path(comentarioPath)
                .request().get();
        String listComentario = response.readEntity(String.class);
        List<ComentarioDTO> listComentarioTest = new ObjectMapper().readValue(listComentario, List.class);
        Assert.assertEquals(OK, response.getStatus());
        Assert.assertEquals(1, listComentarioTest.size());
    }

    @Test
    @InSequence(4)
    public void updateComentarioTest() throws IOException {

        ComentarioDTO comentario = oraculo.get(0);

        ComentarioDTO editorialChanged = factory.manufacturePojo(ComentarioDTO.class);
        comentario.setComment(editorialChanged.getComment());
        Response response = target.path(comentarioPath).path(comentario.getId().toString())
                .request().put(Entity.entity(comentario, MediaType.APPLICATION_JSON));
        ComentarioDTO comentarioTest = (ComentarioDTO) response.readEntity(ComentarioDTO.class);
        Assert.assertEquals(OK, response.getStatus());
        Assert.assertEquals(comentario.getComment(), comentarioTest.getComment());
    }

    @Test
    @InSequence(9)
    public void deleteComentarioTest() {

        ComentarioDTO comentario = oraculo.get(0);
        Response response = target.path(comentarioPath).path(comentario.getId().toString())
                .request().delete();
        Assert.assertEquals(NO_CONTENT, response.getStatus());
    }
}
