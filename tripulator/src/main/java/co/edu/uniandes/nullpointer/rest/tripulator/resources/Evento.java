/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.nullpointer.rest.tripulator.resources;

import co.edu.uniandes.nullpointer.rest.tripulator.dtos.EventoDTO;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author jd.fandino10
 */
@Path("eventos")
public class Evento {

    @GET
    public List<EventoDTO> getEventos(){
        return null;
    }
}
