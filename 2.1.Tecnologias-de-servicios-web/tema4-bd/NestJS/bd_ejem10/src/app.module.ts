import { Module } from '@nestjs/common';
import { SequelizeModule } from '@nestjs/sequelize';
import { TypeOrmModule } from '@nestjs/typeorm';
import { PostController } from './app.controller';
import { Post } from './app.post';
import { PostService } from './app.service';

@Module({
  imports: [
    SequelizeModule.forRoot({
      dialect: 'mysql',
      host: 'localhost',
      port: 3306,
      username: 'root',
      password: 'password',
      database: 'posts',
      autoLoadModels: true,
      synchronize: true
    }),
    SequelizeModule.forFeature([Post])
  ],
  providers: [PostService],
  controllers: [PostController]
})
export class AppModule {}