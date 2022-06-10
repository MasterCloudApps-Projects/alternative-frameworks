import { Injectable, Scope } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Customer } from './app.customer';

@Injectable()
export class CustomerService {

  constructor(@InjectRepository(Customer) private customerRepository: Repository<Customer>) {

  }

  async addCustomer(customer: Customer) {
    return await this.customerRepository.save(customer);
  }
  
  async getCustomers() {
    return await this.customerRepository.find();
  }

  async getCustomer(id: number) {
    return await this.customerRepository.findOneBy({ id });
  }

  async updateCustomer(id: number, customer: Customer) {
    return await this.customerRepository.update(id, customer);
  }

  async deleteCustomer(id: number) {
    return await this.customerRepository.delete(id);
  }
  
}

