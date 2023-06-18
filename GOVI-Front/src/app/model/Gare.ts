import { Quai } from './Quai';

export class Gare {
  id: string;
  quais: Quai[];

  constructor(id: string, quais: Quai[]) {
    this.id = id;
    this.quais = quais;
  }
}
