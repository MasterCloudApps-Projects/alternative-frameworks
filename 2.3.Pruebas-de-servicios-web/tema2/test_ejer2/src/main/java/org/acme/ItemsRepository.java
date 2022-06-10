package org.acme;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemsRepository {

    private Map<Long, Item> items = new ConcurrentHashMap<>();
	private AtomicLong lastId = new AtomicLong();

	public ItemsRepository(){
		long id = lastId.incrementAndGet();
		Item item = new Item();
		item.setDescription("Leche");
		item.setChecked(true);
		items.put(id, item);
    }
    
    public Collection<Item> getAllItems(){
        return items.values();
    }

    public Item getItem(Long id){
        return items.get(id);
    }

    public Item postItem(Item item){
        long id = lastId.incrementAndGet();
		item.setId(id);
		items.put(id, item);
		return item;
    }

    public Item putItem(Long id, Item itemActualizado){
        Item item = items.get(id);

		if (item != null) {
			itemActualizado.setId(id);
			items.put(id, itemActualizado);
			return itemActualizado;
		} else {
			return null;
		}
    }

    public Item removeItem(Long id){
        Item item = items.remove(id);
        return item;
    }

}