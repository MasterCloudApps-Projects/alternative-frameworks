import { Field, InputType, Int, ObjectType } from "@nestjs/graphql";

@ObjectType()
export class Post {

  @Field(() => Int, { name: 'id', nullable: true })
  id: number;

  @Field({ name: 'user', nullable: true })
  user: string;

  @Field({ name: 'title', nullable: true })
  title: string;


  @Field({ name: 'text', nullable: true })
  text: string;

  constructor(id: number, user: string, title:string, text:string) {
      this.id = id;
      this.user = user;
      this.title = title;
      this.text = text;
  }
}

@InputType()
export class PostInput {

  @Field({ name: 'user', nullable: true })
  user: string;

  @Field({ name: 'title', nullable: true })
  title: string;


  @Field({ name: 'text', nullable: true })
  text: string;

  constructor(user: string, title:string, text:string) {
      this.user = user;
      this.title = title;
      this.text = text;
  }
}