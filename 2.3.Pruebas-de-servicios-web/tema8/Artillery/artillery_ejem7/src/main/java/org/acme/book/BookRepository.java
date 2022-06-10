package org.acme.book;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BookRepository implements io.quarkus.hibernate.orm.panache.PanacheRepository<Book> {

}