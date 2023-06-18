export class Mission {
  codeMission: string;
  codeADC: string;
  gareDepart: string;
  gareArrivee: string;
  heureArrivee: Date;
  heureDepart: Date;

  constructor(
    codeMission: string,
    codeADC: string,
    gareDepart: string,
    gareArrivee: string,
    heureArrivee: Date,
    heureDepart: Date
  ) {
    this.codeMission = codeMission;
    this.codeADC = codeADC;
    this.gareDepart = gareDepart;
    this.gareArrivee = gareArrivee;
    this.heureArrivee = heureArrivee;
    this.heureDepart = heureDepart;
  }
}
