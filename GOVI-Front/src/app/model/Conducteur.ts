import { Releve } from './Releve';

export class Conducteur {
  codeADC: string;
  residence: string;
  isPS: boolean;
  isFS: boolean;
  couleur: string;
  releves: Releve[];

  constructor(
    codeADC: string,
    residence: string,
    isPS: boolean,
    isFS: boolean,
    couleur: string,
    releves: Releve[]
  ) {
    this.couleur = couleur;
    this.isFS = isFS;
    this.isPS = isPS;
    this.codeADC = codeADC;
    this.residence = residence;
    this.releves = releves;
  }
}
