import { Entity, Column, PrimaryGeneratedColumn } from 'typeorm';

@Entity()
export class Customer {
  
  @PrimaryGeneratedColumn()
  id: number;

  @Column()
  firstName: string;

  @Column()
  lastName: string;

  constructor(firstName: string, lastName:string) {
    this.firstName = firstName;
    this.lastName = lastName;
}
  
}