import { Conducteur } from './Conducteur';

export class Mission {
  codeMission: string;
  gareDepart: string;
  gareArrivee: string;
  heureArrivee: Date;
  heureDepart: Date;
  couleurEnum: string;
  conducteurTrain: Conducteur;
  conducteursEVLoc: Conducteur[];

  constructor(
    codeMission: string,
    gareDepart: string,
    gareArrivee: string,
    heureArrivee: Date,
    heureDepart: Date,
    couleurEnum: string,
    conducteurTrain: Conducteur,
    conducteursEVLoc: Conducteur[]
  ) {
    this.codeMission = codeMission;
    this.gareDepart = gareDepart;
    this.gareArrivee = gareArrivee;
    this.heureArrivee = heureArrivee;
    this.heureDepart = heureDepart;
    this.couleurEnum = couleurEnum;
    this.conducteurTrain = conducteurTrain;
    this.conducteursEVLoc = conducteursEVLoc;
  }
}
