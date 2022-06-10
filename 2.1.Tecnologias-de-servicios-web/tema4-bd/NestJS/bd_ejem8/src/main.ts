import { NestFactory } from '@nestjs/core';
import { Customer } from './app.customer';
import { AppModule } from './app.module';
import { CustomerService } from './app.service';


async function bootstrap() {
  
  const app = await NestFactory.create(AppModule);
  await app.listen(3000);

  const customerService : CustomerService = app.get(CustomerService);

  const customer: Customer = new Customer('Jack', 'Bauer');
  await customerService.addCustomer(customer);

  console.log("Customer inserted: ", customer);

  const c1 = await customerService.getCustomer(customer.id);

  console.log("get Customer: ", c1)

  const newCustomer: Customer = new Customer('Juan', 'Pérez');

  await customerService.updateCustomer(c1.id, newCustomer);

  console.log("get updated Customer: ",newCustomer);

  const updatedCustomer = await customerService.getCustomer(c1.id);

  console.log("get Customer: ",updatedCustomer);

  const customers = await customerService.getCustomers();

  console.log("get Customers: ", customers);

  customerService.deleteCustomer(updatedCustomer.id);

  console.log("deleted Customer: ", updatedCustomer);

  const allCustomers = await customerService.getCustomers();

  console.log("get Customers: ", customers);

  
}

bootstrap();
