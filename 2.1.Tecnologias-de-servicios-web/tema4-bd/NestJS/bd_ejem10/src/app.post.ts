import { Model, Table, Column } from 'sequelize-typescript';

@Table
export class Post extends Model {

  id: number;

  @Column
  user: string;

  @Column
  title: string;

  @Column
  text: string;

}