import { Injectable, Scope } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Customer } from './app.customer';

@Injectable()
export class CustomerService {

  constructor(@InjectRepository(Customer) private customerRepository: Repository<Customer>) {

  }

  async save(customer: Customer) {
    return await this.customerRepository.save(customer);
  }
  
}