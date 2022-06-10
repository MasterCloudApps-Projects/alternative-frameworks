import { AppModule } from './app.module';
import puppeteer  from 'puppeteer';
import { NestFactory } from '@nestjs/core';
import { Test, TestingModule } from '@nestjs/testing';
import { NestExpressApplication } from '@nestjs/platform-express';

const BASE_URL: string = 'http://localhost:3000'
const INDEX = 'index.html';
const IMAGE_NAME : string = 'mastercloudapps.png';
const PATH_SEPARATOR : string = '/'
const GREETING : string = 'Hello ';
const INPUT : string = "input[name=userName]";
const SUBMIT : string = "input[type=submit]";

describe('Main (e2e)', () => {

    let appModule : AppModule;
  
    beforeEach(async () => {
      const app : TestingModule = await Test.createTestingModule({
        imports: [AppModule]
      }).compile();
  
      appModule = app.get<AppModule>(AppModule);
    });

  describe('Hello userName', () => {
    it('should return "Hello userName"', async () => {

      const app = await NestFactory.create<NestExpressApplication>(
        AppModule,
      );

      app.useStaticAssets('src/public');
      app.setBaseViewsDir('src/views');
      app.setViewEngine('hbs');
      
      await app.listen(3000);

      const browser = await puppeteer.launch({headless: false});
      const [page] = await browser.pages();

      const userName = "admin";
    
      await page.goto(BASE_URL + PATH_SEPARATOR + INDEX);  

      await page.focus(INPUT)
      await page.keyboard.type(userName)

      await page.focus(SUBMIT)
      await page.click(SUBMIT);

      const title = await page.$eval("body > h1", el => el.textContent);
      const [imageSrc] = await page.$$eval('img', anchors => [].map.call(anchors, img => img.src));

      await browser.close();

      expect(title).toBe(GREETING + userName);
      expect(imageSrc).toBe(BASE_URL + PATH_SEPARATOR + IMAGE_NAME);

      await app.close();
    });
  });
});
