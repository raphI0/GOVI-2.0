import { Mission } from './Mission';

export class Retournement {
  idQuai: string;
  missionsArrivee: Mission[];
  missionsDepart: Mission[];
  constructor(
    idQuai: string,
    missionsArrivee: Mission[],
    missionsDepart: Mission[]
  ) {
    this.idQuai = idQuai;
    this.missionsArrivee = missionsArrivee;
    this.missionsDepart = missionsDepart;
  }
}
