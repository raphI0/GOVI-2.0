import { Quai } from './Quai';

export class Gare {
  nom: string;
  alias: string;
  quais: Quai[];

  constructor(id: string, alias: string, quais: Quai[]) {
    this.nom = id;
    this.alias = alias;
    this.quais = quais;
  }
}
