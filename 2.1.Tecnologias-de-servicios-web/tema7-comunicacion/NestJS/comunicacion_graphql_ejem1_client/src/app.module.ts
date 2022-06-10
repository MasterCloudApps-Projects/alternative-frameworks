import { GraphQLRequestModule } from '@golevelup/nestjs-graphql-request';
import { Module } from '@nestjs/common';
import { PostController } from './app.controller';
import { PostService } from './app.service';

@Module({
  imports: [
    GraphQLRequestModule.forRoot(GraphQLRequestModule, {
      endpoint: 'http://localhost:3000/graphql',
      options: {
        headers: {
          'content-type': 'application/json'
        },
      },
    }),
  ],
  providers: [PostService],
  controllers: [PostController]
})
export class AppModule {}