import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import { Customer } from './app.customer';
import * as mongoDB from "mongodb";
import * as dotenv from "dotenv";

async function connectToDatabase () {

  dotenv.config();
  const client: mongoDB.MongoClient = new mongoDB.MongoClient("mongodb://localhost:27017");
  await client.connect();
     
  const db: mongoDB.Db = client.db("customersDB");
  const customerCollection: mongoDB.Collection = db.collection("customers");
 
  console.log("Connected to Mongo");

  await customerCollection.insertOne(new Customer('Jack', 'Bauer'))

  console.log("Customer inserted");

  const c: Customer = JSON.parse(JSON.stringify(await customerCollection.findOne()));
  
  console.log(c)

  client.close();

  console.log("Connection closed");

}

async function bootstrap() {
  await connectToDatabase();
  
  const app = await NestFactory.create(AppModule);
  await app.listen(3000);
  
}

bootstrap();
