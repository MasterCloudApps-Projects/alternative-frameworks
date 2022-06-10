import { HttpStatus } from '@nestjs/common';
import { INestApplication } from '@nestjs/common';
import { Test } from '@nestjs/testing';
import { AppModule } from './app.module';
import { Post } from './app.post';
import { Repository } from 'typeorm';
import * as request from 'supertest';


describe('AppController', () => {
  let app : INestApplication;

  const post : Post = new Post("Pepe", "Vendo moto", "Barata, barata");
  const updatePost : Post = new Post("Juan", "Compro coche", "Pago bien");
  
  const postJSON = { 
    id: 1,
    user: "Pepe",
    title: "Vendo moto",
    text: "Barata, barata"
  }

  const updatePostJSON = { 
    id: 1,
    user: "Juan",
    title: "Compro coche",
    text: "Pago bien"
  }

  let postRespository = { save: (post: Post) => postJSON, find: () => [postJSON], findOneBy: (id: number) => postJSON,
    update: (id: number, post: Post) => updatePostJSON, delete: (id: number) => updatePostJSON };

  beforeEach(async () => {
      const moduleRef = await Test.createTestingModule({
        imports: [AppModule],
      })
        .overrideProvider(Repository<Post>)
        .useValue(postRespository)
        .compile();

      app = moduleRef.createNestApplication();
      await app.init();
  });

  it(`POST /posts`, () => {
    return request(app.getHttpServer())
      .post('/posts')
      .send(post)
      .expect(HttpStatus.CREATED)
      .then((res) => expect(res.body).toStrictEqual(postJSON));
    });

  it(`GET /posts`, () => {
    return request(app.getHttpServer())
      .get('/posts')
      .expect(HttpStatus.OK)
      .then((res) => expect(res.body).toStrictEqual([postJSON]));
    });

  it(`GET /posts/1`, () => {
    return request(app.getHttpServer())
      .get('/posts/1')
      .expect(HttpStatus.OK)
      .then((res) => expect(res.body).toStrictEqual(postJSON));
    });

  it(`PUT /posts/1`, () => {
    return request(app.getHttpServer())
      .put('/posts/1')
      .send(updatePost)
      .expect(HttpStatus.OK)
      .then((res) => expect(res.body).toStrictEqual(updatePostJSON));
    });

  it(`DELETE /posts/1`, () => {    
    return request(app.getHttpServer())
      .delete('/posts/1')
      .expect(HttpStatus.OK)
      .then((res) => expect(res.body).toStrictEqual(updatePostJSON));
    });

  afterAll( async () => {
    await app.close();
  });
});