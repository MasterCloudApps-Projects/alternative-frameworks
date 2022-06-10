package org.acme;

import java.util.List;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.acme.model.Project;
import org.acme.model.Student;

import io.quarkus.runtime.StartupEvent;


@Path("/")
public class SchoolResource {

	@Inject
	ProjectRepository projectRepository;
	
	@Inject
	StudentRepository studentRepository;

	@Transactional
	public void init(@Observes StartupEvent ev) {

		Project p1 = new Project("TFG1", 8);
		projectRepository.persist(p1);
		
		Student s1 = new Student("Pepe", 2010);
		s1.setProject(p1);
		
		Student s2 = new Student("Juan", 2011);
		
		studentRepository.persist(s1);
		studentRepository.persist(s2);	
	}

	@GET
	@Path("students/")
	public List<Student> getStudents() {
		return studentRepository.listAll();
	}
	
	@GET
	@Path("projects/")
	public List<Project> getProjects() {
		return projectRepository.listAll();
	}
	
	//Deleting a student doesn't delete her associated project
	@DELETE
	@Path("students/{id}")
	@Transactional
	public Student deleteStudent(@PathParam("id") Long id) {
		Student student = studentRepository.findById(id);
		studentRepository.deleteById(id);
		return student;
	}
	
	//A project only can be deleted if it has no associated student.
	@DELETE
	@Path("projects/{id}")
	@Transactional
	public Project deleteProject(@PathParam("id")  Long id) {
		Project project = projectRepository.findById(id);
		projectRepository.deleteById(id);
		return project;
	}
}
