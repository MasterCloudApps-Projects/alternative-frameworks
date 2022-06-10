package org.acme;

import io.quarkus.runtime.annotations.QuarkusMain;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import io.quarkus.runtime.Quarkus;

@QuarkusMain
@ApplicationScoped
public class Application {

    public static void main(String ... args) {
        Quarkus.run(args); 
    }

    @Produces
	public UserService userService() {
		return new UserService(10);
	}
}