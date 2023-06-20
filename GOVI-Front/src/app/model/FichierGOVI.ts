import { TypeFichierEnum } from './TypeFichierEnum';

export class FichierGOVI {
  typeFichier: TypeFichierEnum;
  fichier: FormData;
  constructor(typeFichier: TypeFichierEnum, fichier: FormData) {
    this.typeFichier = typeFichier;
    this.fichier = fichier;
  }
}
