import { Module } from '@nestjs/common';
import { GreetingController } from './app.controller';

@Module({
  imports: [],
  controllers: [GreetingController]
})
export class AppModule {}
