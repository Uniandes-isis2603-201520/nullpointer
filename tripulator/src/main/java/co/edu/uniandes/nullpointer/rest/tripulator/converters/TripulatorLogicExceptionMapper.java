package co.edu.uniandes.nullpointer.rest.tripulator.converters;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import co.edu.uniandes.nullpointer.rest.tripulator.exceptions.TripulatorLogicException;

/**
 * Convertidor de Excepciones TripulatorLogicException a mensajes REST.
 */
@Provider
public class TripulatorLogicExceptionMapper implements ExceptionMapper<TripulatorLogicException> {

	/**
	 * Generador de una respuesta a partir de una excepción
	 * @param ex excecpión a convertir a una respuesta REST
	 */
	@Override
	public Response toResponse(TripulatorLogicException ex) {
		// retorna una respuesta
		return Response
				.status(Response.Status.NOT_FOUND)	// estado HTTP 404
				.entity(ex.getMessage())			// mensaje adicional
				.type("text/plain")
				.build();
	}

}
