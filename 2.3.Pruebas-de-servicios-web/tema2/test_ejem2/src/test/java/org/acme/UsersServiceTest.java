package org.acme;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class UsersServiceTest {
	
	@Test
	public void getUsersTest() throws Exception {
		 
	    UsersService service = new UsersService();
	    
	    List<User> users = service.getUsers();
	    
	    assertEquals(users.size(),1);
		assertEquals(users.get(0).getName(),"Pepe");
		
	}
}
