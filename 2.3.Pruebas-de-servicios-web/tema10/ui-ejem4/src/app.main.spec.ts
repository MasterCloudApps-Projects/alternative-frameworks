import { AppModule } from './app.module';
import puppeteer  from 'puppeteer';
import { NestFactory } from '@nestjs/core';
import { Test, TestingModule } from '@nestjs/testing';
import { NestExpressApplication } from '@nestjs/platform-express';

const BASE_URL: string = 'http://localhost:3000'
const NEW_POST = "/new_post.html";
const POST = "/post/";
const DELETE = "/delete";
const BACK = "/";
const USER_INPUT : string = "input[name=user]";
const TITLE_INPUT : string = "input[name=title]";
const TEXT_INPUT : string = "textarea[name=text]";
const SUBMIT : string = "input[type=submit]";
const MAIN_HREF_TEXTS = [ 'Pepe Vendo moto', 'Juan Compro coche', 'New Post' ]

const id = "3";
const user = "Admin";
const title = "Vendo Web";
const text = "Barata y Sencilla";
const HREF_TEXTS_AFTER_ADD_POST = [ 'Pepe Vendo moto', 'Juan Compro coche', user + " " + title, 'New Post' ]

async function getmatchingHref(page, text : string) {
  const href = await page.$$eval('a', anchors => [].map.call(anchors, a => a.href));
  const [ matchingHref ] = href.filter(e => e === text);
  return matchingHref;
}


describe('Main (e2e)', () => {

    let appModule : AppModule;
  
    beforeEach(async () => {
      const app : TestingModule = await Test.createTestingModule({
        imports: [AppModule]
      }).compile();
  
      appModule = app.get<AppModule>(AppModule);
    });

  describe('Add, Get, Delete Posts', () => {
    it('should return "Posts"', async () => {

      const app = await NestFactory.create<NestExpressApplication>(
        AppModule,
      );

      app.useStaticAssets('src/public');
      app.setBaseViewsDir('src/views');
      app.setViewEngine('hbs');
      
      await app.listen(3000);

      const browser = await puppeteer.launch({headless: false});
      const [page] = await browser.pages();

      await page.goto(BASE_URL)

      let checkPosts = (arr, target) => target.every(v => arr.includes(v));

      const hrefTextsBefore = await page.$$eval('a', anchors => [].map.call(anchors, a => a.text));
      expect(checkPosts(hrefTextsBefore, MAIN_HREF_TEXTS)).toBe(true);

      const newHref = await getmatchingHref(page, BASE_URL + NEW_POST);
      await page.goto(newHref);

      await page.focus(USER_INPUT)
      await page.keyboard.type(user);

      new Promise(r => setTimeout(r, 500));

      await page.focus(TITLE_INPUT)
      await page.keyboard.type(title);

      new Promise(r => setTimeout(r, 500));

      await page.focus(TEXT_INPUT)
      await page.keyboard.type(text);

      new Promise(r => setTimeout(r, 500));

      await page.focus(SUBMIT)
      await page.click(SUBMIT);

      const backHref = await getmatchingHref(page, BASE_URL + BACK);
      await page.goto(backHref);

      const hrefTextsAfterAddPost = await page.$$eval('a', anchors => [].map.call(anchors, a => a.text));
      expect(checkPosts(hrefTextsAfterAddPost, HREF_TEXTS_AFTER_ADD_POST)).toBe(true);    

      const getHref = await getmatchingHref(page, BASE_URL + POST + id);
      await page.goto(getHref);

      const deleteHref = await getmatchingHref(page, BASE_URL + POST + id + DELETE);
      await page.goto(deleteHref);

      const backAgainHref = await getmatchingHref(page, BASE_URL + BACK);
      await page.goto(backAgainHref);

      const hrefTextsAfter = await page.$$eval('a', anchors => [].map.call(anchors, a => a.text));
      expect(checkPosts(hrefTextsAfter, MAIN_HREF_TEXTS)).toBe(true);

      await browser.close();
           
      await app.close();
    });
  });
});
