import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { PostController } from './app.controller';
import { Post } from './app.post';
import { PostService } from './app.service';

@Module({
  imports: [
    TypeOrmModule.forRoot({
      type: 'mysql',
      host: 'localhost',
      port: 3306,
      username: 'root',
      password: 'password',
      database: 'posts',
      entities: [Post],
      synchronize: true,
    }),
    TypeOrmModule.forFeature([Post])
  ],
  providers: [PostService],
  controllers: [PostController]
})
export class AppModule {}