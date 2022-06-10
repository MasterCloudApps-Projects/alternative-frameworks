import { Controller, Req, Body, Res, Get, Post, Put, Delete, Param, HttpStatus, HttpCode } from '@nestjs/common';
import { ItemService } from './app.service';
import { Item } from './app.item';

@Controller()
export class ItemController {
  
  constructor(private readonly itemService: ItemService) {

  }

  @Get('/items')
  getItems() {
    return this.itemService.getItems();
  }

  @Post('/items')
  @HttpCode(HttpStatus.CREATED)
  addItem(@Req() request, @Body() item: Item, @Res() response) {
    item = this.itemService.addItem(item)
    const location: string = request.protocol + '://' + request.get('Host') + request.originalUrl + '/' + item.id
    return response.location(location).send(item);
  }

  @Get('/items/:id')
  getItem(@Param('id') id: string, @Res() response) {
    const item: Item = this.itemService.getItem(parseInt(id));
    if (item) {
      return response.status(HttpStatus.OK).send(item);
    }
    else {
      return response.status(HttpStatus.NOT_FOUND).send();
    }
  }

  @Put('/items/:id')
  updateItem(@Param('id') id: string, @Body() item: Item, @Res() response) {
    const auxItem: Item = this.itemService.getItem(parseInt(id));
    if (auxItem) {
      return response.status(HttpStatus.OK).send(this.itemService.updateItem(parseInt(id), item));
    }
    else {
      return response.status(HttpStatus.NOT_FOUND).send();
    }
  }

  @Delete('/items/:id')
  deleteItem(@Param('id') id: string, @Res() response) {
    const item: Item = this.itemService.getItem(parseInt(id));
    if (item) {
      return response.status(HttpStatus.OK).send(this.itemService.deleteItem(parseInt(id)));
    }
    else {
      return response.status(HttpStatus.NOT_FOUND).send();
    }
  }
}
