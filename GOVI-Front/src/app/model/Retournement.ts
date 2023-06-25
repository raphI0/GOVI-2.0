import { Mission } from './Mission';

export class Retournement {
  couleur: string;
  missionsArrivee: Mission[];
  missionsDepart: Mission[];
  constructor(
    couleur: string,
    missionsArrivee: Mission[],
    missionsDepart: Mission[]
  ) {
    this.couleur = couleur;
    this.missionsArrivee = missionsArrivee;
    this.missionsDepart = missionsDepart;
  }
}
