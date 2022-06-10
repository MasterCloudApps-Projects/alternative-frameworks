import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import { CustomerService } from './app.service';


async function bootstrap() {
  const app = await NestFactory.create(AppModule);
  await app.listen(3000);

  const customerService : CustomerService = app.get(CustomerService);

  await customerService.insertOne();
  const id = await customerService.insertOneWithId();
  await customerService.insertMany();
  await customerService.insertManyWithId();
  await customerService.findCustomerWithQuery();
  await customerService.findCustomerById(id);
  await customerService.updateCustomerById(id);
  await customerService.updateCustomersByFirstName();
  await customerService.deleteCustomerById(id);
  await customerService.deleteCustomersByFirstName();

}

bootstrap();
