import { Injectable } from "@nestjs/common";
import { InjectModel } from "@nestjs/mongoose";
import { Model } from "mongoose";
import { Customer, CustomerDocument } from "./app.customer";
import { CustomerDTO } from "./app.customerdto";

@Injectable()
export class CustomerService {
  
  constructor(@InjectModel(Customer.name) private customerModel: Model<CustomerDocument>) {

  }

  async addCustomer(customerDTO: CustomerDTO) {
    const createdCustomer = new this.customerModel(customerDTO);   
    return await createdCustomer.save();
  }

}