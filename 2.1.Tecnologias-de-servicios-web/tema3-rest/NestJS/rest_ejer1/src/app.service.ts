import { Injectable } from '@nestjs/common';
import { Item } from './app.item';

@Injectable()
export class ItemService {

  items: Map<number, Item>;
  nextId: number;

  constructor() {
    this.items = new Map<number, Item>();
    this.nextId = 1;
    this.addItem(new Item("Leche", false));
    this.addItem(new Item("Pan", true));
  }

  addItem(item : Item) : Item {
    item.id = this.nextId;
    this.items.set(item.id, item);
    this.nextId++
    return item;
  }
  
  deleteItem(id: number) : Item{
    let item: Item = this.getItem(id)
    this.items.delete(id);
    return item;
  }
  
  getItems() : Item[] {
    return [...this.items.values()];
  }
  
  getItem(id: number) : Item {
    return this.items.get(id);
  }
  
  updateItem(id: number, item : Item) : Item {
    item.id  = id
    this.items.set(id, item);
    return item;
  }

}