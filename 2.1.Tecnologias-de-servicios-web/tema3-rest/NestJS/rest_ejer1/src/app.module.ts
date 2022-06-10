import { Module } from '@nestjs/common';
import { ItemController } from './app.controller';
import { ItemService } from './app.service';

@Module({
  imports: [],
  controllers: [ItemController],
  providers: [ItemService]
})
export class AppModule {}
