package org.acme.service;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.acme.model.Student;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "http://localhost:8082/students")
@ApplicationScoped
public interface StudentService {
   
    @GET
    public Response getStudents();

    @GET
    @Path("/{id}")
    public Response getStudent(@PathParam("id") String id);

    @POST
    public Response postStudent(Student student);

    @PUT
    public Response putStudent(Student student);

    @DELETE
    @Path("/{id}")
    public Response deleteStudent(@PathParam("id") String id);

}