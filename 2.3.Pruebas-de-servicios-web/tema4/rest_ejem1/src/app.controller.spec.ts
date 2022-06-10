import { Test, TestingModule } from '@nestjs/testing';
import { PostController } from './app.controller';

describe('AppController', () => {
  let postController: PostController;

  beforeEach(async () => {
    const app: TestingModule = await Test.createTestingModule({
      controllers: [PostController]
    }).compile();

    postController = app.get<PostController>(PostController);
  });

  describe('root', () => {
    it('should return "Post!"', () => {
      expect(postController.getPost()).toStrictEqual({ 
        id: 1,
        user: "Pepe",
        title: "Vendo moto",
        text: "Barata, barata"
      });
    });
  });
});