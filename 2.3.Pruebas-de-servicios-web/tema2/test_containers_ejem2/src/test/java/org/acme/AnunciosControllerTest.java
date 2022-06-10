package org.acme;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

@QuarkusTest
public class AnunciosControllerTest {

    @Inject
    AnunciosRepository anunciosRepository;
	
    @Test
    public void getAnunciosTest() throws InterruptedException, ExecutionException {
        List<Anuncio> anuncios = anunciosRepository.listAll();
        assertEquals(anuncios.size(), 2);
    }

}