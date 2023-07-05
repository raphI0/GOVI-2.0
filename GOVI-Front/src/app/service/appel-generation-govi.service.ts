import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DemandeGOVI } from '../model/demandeGOVI';
import { Gare } from '../model/Gare';
import { FichierGOVI } from '../model/FichierGOVI';
import { Form } from '@angular/forms';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AppelGenerationGoviService {
  // La liste de gares qu'enverra le back
  public gares!: Gare[];
  constructor(
    private httpClient: HttpClient // On injecte httpClient pour appeler le back
  ) {}

  /**
   * Appel pour génération du GOVI
   * @param demandeGOVI
   */
  appelGenerationGovi(demandeGOVI: DemandeGOVI) {
    console.log(demandeGOVI);
    return this.httpClient.post<Gare[]>(
      environment.url.api.base + environment.url.api.generationGovi,
      demandeGOVI
    );
  }

  /**
   * Appel pour uniquement charger un fichier
   * @param fichierGOVI
   */
  envoiDemandeGOVIPartielle(fichierGOVI: FormData) {
    console.log(fichierGOVI.get('file'));
    return this.httpClient.post(
      environment.url.api.base + environment.url.api.envoiFichierGovi,
      fichierGOVI
    );
  }
}
