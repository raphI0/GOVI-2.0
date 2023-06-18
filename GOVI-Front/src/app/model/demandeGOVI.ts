export class DemandeGOVI{
  fichiers: {[key: string]: string};
  gares: string[];
  date: Date;
  constructor() {
    this.fichiers= {};
    this.gares=[];
    this.date= new Date;
  }

}
