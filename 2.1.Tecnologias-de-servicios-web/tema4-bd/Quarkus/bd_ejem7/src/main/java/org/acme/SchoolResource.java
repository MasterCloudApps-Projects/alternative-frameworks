package org.acme;

import java.util.List;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.fasterxml.jackson.annotation.JsonView;

import io.quarkus.runtime.StartupEvent;

import org.acme.model.Project;
import org.acme.model.Student;

@Path("/")
public class SchoolResource {

	@Inject
	ProjectRepository projectRepository;
	
	@Inject
	StudentRepository studentRepository;

	@Transactional
	public void init(@Observes StartupEvent ev) {

		Student s1 = new Student("Pepe", 2010);
		s1.setProject(new Project("TFG1", 8));
		studentRepository.persist(s1);
		
		Student s2 = new Student("Juan", 2011);		
		studentRepository.persist(s2);		
	}

	interface StudentView extends Student.BasicAtt, Student.ProjectAtt, Project.BasicAtt {}
	
	@JsonView(StudentView.class)
	@GET
	@Path("/students/")
	public List<Student> getStudents() {
		return studentRepository.listAll();
	}
	
	@JsonView(StudentView.class)
	@GET
	@Path("/students/{id}")
	public Student getStudent(@PathParam("id") long id) {
		return studentRepository.findById(id);
	}
	
	interface ProjectView extends Project.BasicAtt, Project.StudentAtt, Student.BasicAtt {}
	
	@JsonView(ProjectView.class)
	@GET
	@Path("/projects/")
	public List<Project> getProjects() {
		return projectRepository.listAll();
	}
	
	@JsonView(ProjectView.class)
	@GET
	@Path("/projects/{id}")
	public Project getProject(@PathParam("id") long id) {
		return projectRepository.findById(id);
	}
	
	// Deleting a student delete its associated project
	@JsonView(StudentView.class)
	@DELETE
	@Path("/students/{id}")	
	@Transactional
	public Student deleteStudent(@PathParam("id") Long id) {
		Student student = studentRepository.findById(id);
		studentRepository.deleteById(id);
		return student;
	}
	
	// A project only can be deleted if it has no associated student. But if you try it, no error is thrown
	@JsonView(ProjectView.class)
	@DELETE
	@Path("/projects/{id}")	
	@Transactional
	public Project deleteProject(@PathParam("id") Long id) {
		Project project = projectRepository.findById(id);
		projectRepository.deleteById(id);
		return project;
	}
}
