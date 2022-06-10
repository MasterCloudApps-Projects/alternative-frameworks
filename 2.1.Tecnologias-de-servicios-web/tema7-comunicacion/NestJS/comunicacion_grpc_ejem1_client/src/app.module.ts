import { Module } from '@nestjs/common';
import { ClientsModule } from '@nestjs/microservices';
import { Transport } from '@nestjs/microservices';
import { GreetingController } from './app.controller';
import { GreetingService } from './app.service';

@Module({
  imports: [
    ClientsModule.register([
      {
        name: 'hello',
        transport: Transport.GRPC,
        options: {
          package: 'hello',
          protoPath: 'src/protos/hello.proto',
        },
      },
    ])
  ],
  providers: [GreetingService],
  controllers: [GreetingController]
})
export class AppModule {}