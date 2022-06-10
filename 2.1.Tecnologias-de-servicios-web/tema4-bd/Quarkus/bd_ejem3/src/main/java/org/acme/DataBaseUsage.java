package org.acme;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;

import io.quarkus.runtime.StartupEvent;


@ApplicationScoped
public class DataBaseUsage {

	@Inject
	CustomerRepository repository;

	@Transactional
	public void onStart(@Observes StartupEvent ev) {
		// save a couple of customers
		repository.persist(new Customer("Jack", "Bauer"));
		repository.persist(new Customer("Chloe", "O'Brian"));
		repository.persist(new Customer("Kim", "Bauer"));
		repository.persist(new Customer("David", "Palmer"));
		repository.persist(new Customer("Michelle", "Dessler"));

		// fetch all customers
		List<Customer> customers = repository.listAll();
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (Customer customer : customers) {
			System.out.println(customer);
		}
		System.out.println();

		// fetch an individual customer by ID
		Customer customer = repository.findById(1L);
		System.out.println("Customer found with findOne(1L):");
		System.out.println("--------------------------------");
		System.out.println(customer);
		System.out.println();

		// fetch customers by last name
		List<Customer> bauers = repository.find("lastName", "Bauer").list();
		System.out.println("Customer found with findByLastName('Bauer'):");
		System.out.println("--------------------------------------------");
		for (Customer bauer : bauers) {
			System.out.println(bauer);
		}

		// If delete entity instead of deleteById error "Removing a detached instance" is thrown
		repository.delete(bauers.get(0));
    }
	
}