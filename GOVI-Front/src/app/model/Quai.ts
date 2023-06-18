import { Retournement } from './Retournement';

export class Quai {
  id: string;
  retournements: Retournement[];
  constructor(id: string, retournements: Retournement[]) {
    this.id = id;
    this.retournements = retournements;
  }
}
