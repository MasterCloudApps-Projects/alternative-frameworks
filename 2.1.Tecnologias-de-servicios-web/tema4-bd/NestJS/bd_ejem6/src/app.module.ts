import { Module } from '@nestjs/common';
import { MongooseModule } from '@nestjs/mongoose';
import { PostController } from './app.controller';
import { Post, PostSchema } from './app.post';
import { PostService } from './app.service';


@Module({
  providers: [PostService],
  controllers: [PostController],
  imports: [MongooseModule.forRoot('mongodb://localhost:27017/customersDB'),
  MongooseModule.forFeature([{ name: Post.name, schema: PostSchema }])]
})
export class AppModule {}
