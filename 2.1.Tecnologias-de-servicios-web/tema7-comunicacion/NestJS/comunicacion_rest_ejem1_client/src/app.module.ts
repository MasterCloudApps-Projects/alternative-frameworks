import { HttpModule } from '@nestjs/axios';
import { Module } from '@nestjs/common';
import { PostController } from './app.controller';
import { PostService } from './app.service';

@Module({
  imports: [HttpModule],
  controllers: [PostController],
  providers: [PostService]
})
export class AppModule {}
