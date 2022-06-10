import { NestFactory } from '@nestjs/core';
import { CustomerDTO } from './app.customerdto';
import { AppModule } from './app.module';
import { CustomerService } from './app.service';

async function bootstrap() {
  const app = await NestFactory.create(AppModule);
  await app.listen(3000);
  
  const customerService : CustomerService = app.get(CustomerService);
  const customer = await customerService.addCustomer(new CustomerDTO('Jack','Bauer'));

  console.log(customer);

}

bootstrap();
