package org.acme.resource; 

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.acme.model.Student;
import org.jboss.resteasy.spi.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path("/students")
public class StudentResource {

    private static final Logger log = LoggerFactory.getLogger(StudentResource.class);

    @Context
    HttpRequest request;

    @POST
    @Transactional
    public Response createStudent(Student student) throws URISyntaxException {
        log.info("SAVE STUDENT {}", student);
        Student.persist(student);
        URI location = new URI(request.getUri().getRequestUri() + String.valueOf(student.getId()));

        return Response.created(location).entity(student).build();
    }

    @GET
    public Response getStudents() {
        log.info("FIND ALL");
        return Response.ok().entity(Student.listAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response getStudent(@PathParam("id") String id) {
        log.info("GET STUDENT BY ID {}", id);
        return Response.ok().entity(Student.findById(id)).build();
    }

    @PUT
    @Transactional
    public Response updateStudent(Student student) {
        log.info("UPDATE STUDENT {}", student);
        return Response.ok().entity(Student.getEntityManager().merge(student)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteStudent(@PathParam("id") String id) {
        log.info("DELETE STUDENT BY ID {}", id);
        Student.deleteById(id);
        return Response.status(Status.NO_CONTENT).build();
    }
}
