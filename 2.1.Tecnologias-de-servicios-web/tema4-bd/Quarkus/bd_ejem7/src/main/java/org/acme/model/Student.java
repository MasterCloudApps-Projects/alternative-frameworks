package org.acme.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Student {

	public interface BasicAtt {}
	public interface ProjectAtt {}
	
	@JsonView(BasicAtt.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@JsonView(BasicAtt.class)
	private String name;
	
	@JsonView(BasicAtt.class)
	private int year;

	@JsonView(ProjectAtt.class)
	@OneToOne(cascade = CascadeType.ALL)
	private Project project;

	protected Student() {
	}

	public Student(String name, int year) {
		super();
		this.name = name;
		this.year = year;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String author) {
		this.name = author;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int goals) {
		this.year = goals;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", name=" + name + ", goals=" + year + "]";
	}

}