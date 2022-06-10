import { Module } from '@nestjs/common';
import { MongooseModule } from '@nestjs/mongoose';
import { Customer, CustomerSchema } from './app.customer';
import { CustomerService } from './app.service';

@Module({
  providers: [CustomerService],
  imports: [MongooseModule.forRoot('mongodb://localhost:27017/customersDB'),
  MongooseModule.forFeature([{ name: Customer.name, schema: CustomerSchema }])]
})
export class AppModule {}
