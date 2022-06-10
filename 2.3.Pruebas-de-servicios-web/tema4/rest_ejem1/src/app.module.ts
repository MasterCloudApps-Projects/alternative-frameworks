import { Module } from '@nestjs/common';
import { PostController } from './app.controller';

@Module({
  imports: [],
  controllers: [PostController]
})
export class AppModule {}
