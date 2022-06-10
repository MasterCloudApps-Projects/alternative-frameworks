import { Entity, Column, PrimaryGeneratedColumn } from 'typeorm';

@Entity()
export class Post {

  @PrimaryGeneratedColumn()
  id: number;

  @Column()
  user: string;

  @Column()
  title: string;

  @Column()
  text: string;

  constructor(user: string, title:string, text:string) {
    this.user = user;
    this.title = title;
    this.text=text;
  }

}