import { HttpStatus } from '@nestjs/common';
import { INestApplication } from '@nestjs/common';
import { Test } from '@nestjs/testing';
import { AppModule } from './app.module';
import { PostDTO } from './app.post';
import { PostService } from './app.service';
import * as request from 'supertest';


describe('AppController', () => {
  let app : INestApplication;

  const post : PostDTO = new PostDTO("Pepe", "Vendo moto", "Barata, barata");
  const updatePost : PostDTO = new PostDTO("Juan", "Compro coche", "Pago bien");
  
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

  let postService = { addPost: (post: PostDTO) => postJSON, getPost: (id: number) => postJSON, getPosts: () => [postJSON],
    updatePost: (id: number, post: PostDTO) => updatePostJSON, deletePost: (id: number) => updatePostJSON };

  beforeEach(async () => {
      const moduleRef = await Test.createTestingModule({
        imports: [AppModule],
      })
        .overrideProvider(PostService)
        .useValue(postService)
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

  afterAll(async () => {
    await app.close();
  });
});