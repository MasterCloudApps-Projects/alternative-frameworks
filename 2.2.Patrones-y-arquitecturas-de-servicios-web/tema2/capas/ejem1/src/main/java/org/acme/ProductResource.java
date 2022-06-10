package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/")
public class ProductResource {

	ProductService productService;

	public ProductResource(ProductService productService){
		this.productService = productService;

	}
	
	@GET
	public Response findAll() {

		return Response.ok(productService.findAll()).build();
		
	}

	@GET
	@Path("/{id}")
	public Response find(@PathParam("id") long id) {

		return Response.ok(productService.findOne(id)).build();
		
	}

}
