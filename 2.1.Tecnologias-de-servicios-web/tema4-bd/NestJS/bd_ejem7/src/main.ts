import { NestFactory } from '@nestjs/core';
import { Customer } from './app.customer';
import { AppModule } from './app.module';
import { CustomerService } from './app.service';


async function bootstrap() {
  
  const app = await NestFactory.create(AppModule);
  await app.listen(3000);

  const customerService : CustomerService = app.get(CustomerService);  

  const customer: Customer = new Customer('Jack', 'Bauer');

  await customerService.save(customer);

  console.log(customer);  
  
}

bootstrap();
