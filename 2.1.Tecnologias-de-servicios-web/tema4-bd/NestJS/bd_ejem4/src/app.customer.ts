import { Prop, Schema, SchemaFactory } from "@nestjs/mongoose";
import { HydratedDocument } from "mongoose";

@Schema()
export class Customer {

  @Prop()
  firstName: string;

  @Prop()
  lastName: string;

}

export const CustomerSchema = SchemaFactory.createForClass(Customer);
export type CustomerDocument = HydratedDocument<Customer>;