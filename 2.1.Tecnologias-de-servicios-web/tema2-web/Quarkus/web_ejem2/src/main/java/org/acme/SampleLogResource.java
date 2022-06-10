package org.acme;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.inject.Inject;

@Path("/page_log")
public class SampleLogResource {

	private Logger log = LoggerFactory.getLogger(SampleLogResource.class);

	@Inject
    Template page; 
	
	@GET
	public TemplateInstance page() {
		
		log.trace("A TRACE Message");
        log.debug("A DEBUG Message");
        log.info("An INFO Message");
        log.warn("A WARN Message");
        log.error("An ERROR Message");
		
		return page.instance();
	}	
}
