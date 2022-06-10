import * as mongoDB from "mongodb";
import * as dotenv from "dotenv";

export function connectToDatabase () {

  dotenv.config();
  const client: mongoDB.MongoClient = new mongoDB.MongoClient("mongodb://localhost:27017");
  client.connect();
     
  const db: mongoDB.Db = client.db("posts");  

  console.log("Connected to Mongo");

  return db;
}

export function getPostsCollection (db: mongoDB.Db) {
  return db.collection("posts");
}