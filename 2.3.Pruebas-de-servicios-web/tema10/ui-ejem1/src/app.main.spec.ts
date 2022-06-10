import { AppModule } from './app.module';
import puppeteer  from 'puppeteer';
import { NestFactory } from '@nestjs/core';
import { Test, TestingModule } from '@nestjs/testing';

const BASE_URL: string = 'http://localhost:3000';

describe('Main (e2e)', () => {

    let appModule : AppModule;
  
    beforeEach(async () => {
      const app : TestingModule = await Test.createTestingModule({
        imports: [AppModule]
      }).compile();
  
      appModule = app.get<AppModule>(AppModule);
    });

  describe('Hello World', () => {
    it('should return "Hello World!"', async () => {

      const app = await NestFactory.create(AppModule);
      await app.listen(3000);

      const browser = await puppeteer.launch({headless: false});
      const [page] = await browser.pages();
    
      const response = await page.goto(BASE_URL);  
      const text = await response.text();

      await browser.close();

      expect(text).toBe('Hello World!');

      await app.close();
    });
  });
});
