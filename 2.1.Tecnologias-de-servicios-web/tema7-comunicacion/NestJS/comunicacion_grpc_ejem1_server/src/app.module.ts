import { Module } from '@nestjs/common';
import { HelloController } from './app.controller';

@Module({
  controllers: [HelloController]
})
export class AppModule {}