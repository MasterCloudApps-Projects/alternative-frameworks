package org.acme.resource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.acme.service.StudentService;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/consumer")
public class ConsumerResource {

    private static final Logger log = LoggerFactory.getLogger(ConsumerResource.class);

    @RestClient
    @Inject
    StudentService studentService;

    @Path("/test")
    @GET
    public Response testIntegration() {
        try {
            return studentService.getStudent("1");
        } catch (Exception ex) {
            log.error("It's broken --> {}", ex.getMessage());
            throw ex;
        }
    }
}
