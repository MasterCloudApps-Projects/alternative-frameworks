import { Injectable } from "@nestjs/common";
import { InjectModel } from "@nestjs/mongoose";
import { Model } from "mongoose";
import { Customer, CustomerDocument } from "./app.customer";
import { CustomerDTO } from "./app.customerdto";

@Injectable()
export class CustomerService {
  
  constructor(@InjectModel(Customer.name) private customerModel: Model<CustomerDocument>) {

  }

  async insertOne() {
    const createdCustomer = new this.customerModel(new CustomerDTO('Jack','Bauer'));
    return await createdCustomer.save();
  }

  async insertOneWithId() {
    const createdCustomer = new this.customerModel(new CustomerDTO('Jack','Bauer'));
    const customer = await createdCustomer.save();

    console.log('Customer inserted with id:', customer._id);

    return customer._id;

  }

  async insertMany() {
    const customer1 = new this.customerModel(new CustomerDTO('Jack', 'Bauer'))
    const customer2 = new this.customerModel(new CustomerDTO('Juan', 'Pérez'));

    await customer1.save()
    await customer2.save()

    console.log('Customers inserted');
  }

  async insertManyWithId() {

    const customer1 = new this.customerModel(new CustomerDTO('Jack', 'Bauer'))
    const customer2 = new this.customerModel(new CustomerDTO('Juan', 'Pérez'));
    
    const id1 = await customer1.save()
    const id2 = await customer2.save()

    console.log('Customers inserted with ids:', id1, id2);
  }
  
  async findCustomerWithQuery() {

    const result = await this.customerModel.find({ firstName: 'Juan' }).exec();

    console.log('Customers with firstName = "Juan":', result);
  }

  async findCustomerById(id) {

    const customer = await this.customerModel.findById(id);

    console.log('Customer with id:', customer);
  }

  async updateCustomerById(id) {

    await this.customerModel.updateOne({ _id: { $eq: id } }, { $set: { firstName: 'Pedro', age: 45 } });

    console.log('Updated customer with id:', id);
  }

  async updateCustomersByFirstName() {

    const { matchedCount: n } = await this.customerModel.updateMany(
      { firstName: 'Juan' },
      { $set: { firstName: 'John' } }
    );

    console.log(`Updated ${n} customers with name "Juan"`);
  }

  async deleteCustomerById(id) {

     await this.customerModel.findByIdAndDelete(id);

      console.log('Deleted customer with id:', id);
  }

  async deleteCustomersByFirstName() {

    const { deletedCount } = await this.customerModel.deleteMany({ firstName: 'John' });

    console.log(`Deleted ${deletedCount} customers with name "John"`);
  } 

}