package org.acme;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import redis.clients.jedis.Jedis;

@Testcontainers
public class RedisBackedCacheTest {
	
	private Jedis jedis;
	private RedisBackedCache cache;

    @Container
    public GenericContainer redis = new GenericContainer("redis:3.0.6").withExposedPorts(6379);
    
    @BeforeEach
    public void setUp() throws Exception {
    	jedis = new Jedis(redis.getContainerIpAddress(),redis.getMappedPort(6379));
    	cache = new RedisBackedCache(jedis, "test");
    }
    
    @Test
    public void test() {
    	cache.put("myKey", "myValue");
        assertEquals(cache.get("myKey", String.class).get(),"myValue");
    }
}