package org.acme;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import io.quarkus.runtime.StartupEvent;


@ApplicationScoped
public class DataBaseUsage {

	@Inject
    EntityManager em; 

	@SuppressWarnings("unchecked") // getResultList does not use Java Generics
	@Transactional
	public void onStart(@Observes StartupEvent ev) {
		// save a couple of customers
		em.persist(new Customer("Jack", "Bauer"));
		em.persist(new Customer("Chloe", "O'Brian"));
		em.persist(new Customer("Kim", "Bauer"));
		em.persist(new Customer("David", "Palmer"));
		em.persist(new Customer("Michelle", "Dessler"));

		// fetch all customers
		List<Customer> customers = em.createQuery("SELECT c FROM Customer c").getResultList();
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (Customer customer : customers) {
			System.out.println(customer);
		}
		System.out.println();

		// fetch an individual customer by ID
		Customer customer = em.find(Customer.class, 1L);
		System.out.println("Customer found with findOne(1L):");
		System.out.println("--------------------------------");
		System.out.println(customer);
		System.out.println();

		// fetch customers by last name
		List<Customer> bauers = em.createQuery("SELECT c FROM Customer c WHERE c.lastName = 'Bauer'").getResultList();
		System.out.println("Customer found with findByLastName('Bauer'):");
		System.out.println("--------------------------------------------");
		for (Customer bauer : bauers) {
			System.out.println(bauer);
		}

		em.remove(bauers.get(0));
    }
	
}