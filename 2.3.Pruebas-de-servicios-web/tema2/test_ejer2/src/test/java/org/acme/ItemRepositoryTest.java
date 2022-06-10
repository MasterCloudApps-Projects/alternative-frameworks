package org.acme;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import static org.junit.jupiter.api.Assertions.assertEquals;


@QuarkusTest
public class ItemRepositoryTest {

    @Test
    public void testGetAllItemsTest() {
        
        ItemsRepository itemsRepository = new ItemsRepository();

        Collection<Item> items = itemsRepository.getAllItems();
        
        assertEquals(items.size(),1);
    }

    @Test
    public void testGetOneItemTest() {
        
        ItemsRepository itemsRepository = new ItemsRepository();

        Item item = itemsRepository.getItem(Long.valueOf(1));

        assertEquals(item.getDescription(),"Leche");
        
    }

    @Test
    public void testPostItemTest() {        

        ItemsRepository itemsRepository = new ItemsRepository();

        Item item = new Item();
        String itemDescription = "Pan";
        boolean itemCheked = false;
        item.setDescription(itemDescription);
        item.setChecked(itemCheked);

        Item postedItem = itemsRepository.postItem(item);
        
        assertEquals(postedItem.getDescription(), itemDescription);
        
    }

    @Test
    public void testPutItemTest() {

        ItemsRepository itemsRepository = new ItemsRepository();

        Item item = new Item();
        String itemDescription = "Leche merengada";
        boolean itemCheked = true;
        item.setDescription(itemDescription);
        item.setChecked(itemCheked);

        Item putItem = itemsRepository.putItem(Long.valueOf(1),item);

        assertEquals(putItem.getDescription(),itemDescription);
        
    }


    @Test
    public void testRemoveItemTest() {

        ItemsRepository itemsRepository = new ItemsRepository();

        itemsRepository.removeItem(Long.valueOf(1));
        
        Collection<Item> items = itemsRepository.getAllItems();
        
        assertEquals(items.size(),0);
        
    }

}