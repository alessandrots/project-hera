package com.outline.org.app.rest;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NegocioExceptionMapper implements ExceptionMapper<RestException> {

	@Override
	public Response toResponse(RestException exception) {
		StringBuilder sb = new StringBuilder();
		sb.append("{\"temErro\":\"");
		sb.append(exception.getMessage());
		sb.append("\"}");

		Map<String, String> parameters = new HashMap<String, String>(1);
		parameters.put("charset", "iso-8859-1");
		MediaType mediaType = new MediaType("application", "json", parameters);

		return Response.status(Status.OK).entity(sb.toString()).type(mediaType).build();
	}

}
