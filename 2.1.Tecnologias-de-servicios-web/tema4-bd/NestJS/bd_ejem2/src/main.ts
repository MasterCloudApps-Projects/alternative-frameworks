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

  console.log("Connected to Mongo");

  return { db, client }
}

function getCustomerCollection (db: mongoDB.Db) {
  return db.collection("customers");
}


async function insertOne(customers: mongoDB.Collection) {

  await customers.insertOne(new Customer('Jack', 'Bauer'));

  console.log('Customer inserted');
}

async function insertOneWithId(customers: mongoDB.Collection) {

  const { insertedId } = await customers.insertOne(new Customer('Jack', 'Bauer'));

  console.log('Customer inserted with id:', insertedId);

  return insertedId;
}

async function insertMany(customers: mongoDB.Collection) {

  await customers.insertMany([new Customer('Jack', 'Bauer'), new Customer('Juan', 'Pérez')]);

  console.log('Customers inserted');
}

async function insertManyWithId(customers: mongoDB.Collection) {

  const { insertedIds } = await customers.insertMany([new Customer('Jack', 'Bauer'), new Customer('Juan', 'Pérez')]);

  console.log('Customers inserted with ids:', insertedIds);
}

async function findCustomerWithQuery(customers: mongoDB.Collection) {

  const result = await customers.find({ firstName: 'Juan' }).toArray();

  console.log('Customers with firstName = "Juan":', result);
}

async function findCustomerById(customers: mongoDB.Collection, id: mongoDB.ObjectId) {

  const customer = await customers.findOne({ _id: id });

  console.log('Customer with id:', customer);
}

async function updateCustomerById(customers: mongoDB.Collection, id: mongoDB.ObjectId) {

  await customers.updateOne(
      { _id: id },
      { $set: { firstName: 'Pedro', age: 45 } }
  );

  console.log('Updated customer with id:', id);
}

async function updateCustomersByFirstName(customers: mongoDB.Collection) {

  const { matchedCount } = await customers.updateMany(
      { firstName: 'Juan' },
      { $set: { firstName: 'John' } }
  );

  console.log(`Updated ${matchedCount} customers with name "Juan"`);
}

async function deleteCustomerById(customers: mongoDB.Collection, id: mongoDB.ObjectId) {

  await customers.deleteOne({ _id: id });

  console.log('Deleted customer with id:', id);
}

async function deleteCustomersByFirstName(customers: mongoDB.Collection) {

  const { deletedCount } = await customers.deleteMany({ firstName: 'John' });

  console.log(`Deleted ${deletedCount} customers with name "John"`);
}

async function bootstrap() {
  const { db, client } =  await connectToDatabase();
  const customers: mongoDB.Collection = getCustomerCollection(db);

  await insertOne(customers);
  const id: mongoDB.ObjectId = await insertOneWithId(customers);
  await insertMany(customers);
  await insertManyWithId(customers);
  await findCustomerWithQuery(customers);
  await findCustomerById(customers,id);
  await updateCustomerById(customers,id);
  await updateCustomersByFirstName(customers);
  await deleteCustomerById(customers,id);
  await deleteCustomersByFirstName(customers);

  client.close()

  console.log("Connection closed");

  const app = await NestFactory.create(AppModule);
  await app.listen(3000);

}

bootstrap();
