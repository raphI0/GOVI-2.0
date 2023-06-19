export class DemandeGOVI {
  fichiers: { [key: string]: string };
  idGares: string[];
  date: Date;
  constructor() {
    this.fichiers = {};
    this.idGares = [];
    this.date = new Date();
  }
}
