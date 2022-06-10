package org.acme;

import javax.enterprise.context.ApplicationScoped;

import org.acme.model.Student;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class StudentRepository implements PanacheRepository<Student> {
	
}