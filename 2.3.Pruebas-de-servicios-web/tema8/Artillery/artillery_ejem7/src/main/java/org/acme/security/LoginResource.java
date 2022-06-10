package org.acme.security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.acme.user.User;
import org.acme.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.vertx.ext.web.handler.HttpException;


@Path("/")
public class LoginResource {

	private static final Logger log = LoggerFactory.getLogger(LoginResource.class);

	@Inject
	UserRepository userRepository;

	@Context
	HttpHeaders httpHeaders;

	@GET
	@Path("logIn")
	@Transactional
	public Response logIn() {		

		String [] authorization = getUserAndPasswordFromAuthHeader(httpHeaders);

		String username = authorization[0];
		String password = authorization[1];
		
		Optional<User> user = userRepository.find("username", username).firstResultOptional();

		if (user.isPresent()) {	
			User u = user.get();
			if (!u.isLogged()) {						
				if (BcryptUtil.matches(password, u.getPassword())) {
					u.setLooged(true);
					userRepository.persist(u);
					log.info("Logged as " + user.get().getUsername());
					return Response.ok().entity(u).build();
				}
				return Response.status(Status.UNAUTHORIZED).build();
			}
			return Response.ok().entity("User was logged before").build();			
		}
		return Response.status(Status.UNAUTHORIZED).build();
		
	}


	@GET
	@Path("/logOut")
	@Transactional
	public Response logOut() {

		String [] authorization = getUserAndPasswordFromAuthHeader(httpHeaders);

		String username = authorization[0];
		String password = authorization[1];

		Optional<User> user = userRepository.find("username", username).firstResultOptional();

		if (user.isPresent()) {
			User u = user.get();
			if (u.isLogged()) {					
				if (BcryptUtil.matches(password, u.getPassword())) {
					u.setLooged(false);
					userRepository.persist(u);
					log.info("Logged out");
					return Response.ok().entity("Logged out").build();
				}
				return Response.status(Status.UNAUTHORIZED).build();
			}
			return Response.ok().entity("User was logged out before").build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}


	private static String[] getUserAndPasswordFromAuthHeader(HttpHeaders httpHeaders) {

		List<String> basicAuthHeaderList = httpHeaders.getRequestHeader("Authorization");

		if (basicAuthHeaderList!= null && !basicAuthHeaderList.isEmpty()) {

			String authorization = basicAuthHeaderList.get(0);
	
			if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
				String base64Credentials = authorization.substring("Basic".length()).trim();
				byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
				String credentials = new String(credDecoded, StandardCharsets.UTF_8);
				return credentials.split(":", 2);
			}
		}
		throw new HttpException(Status.BAD_REQUEST.getStatusCode());
	}

}