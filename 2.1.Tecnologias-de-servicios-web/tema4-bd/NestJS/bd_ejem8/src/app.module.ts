import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Customer } from './app.customer';
import { CustomerService } from './app.service';

@Module({
  imports: [
    TypeOrmModule.forRoot({
      type: 'mysql',
      host: 'localhost',
      port: 3306,
      username: 'root',
      password: 'password',
      database: 'customers',
      entities: [Customer],
      synchronize: true,
    }),
    TypeOrmModule.forFeature([Customer])
  ],
  providers: [CustomerService],
})
export class AppModule {}