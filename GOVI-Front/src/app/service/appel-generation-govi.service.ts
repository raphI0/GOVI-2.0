import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DemandeGOVI } from '../model/demandeGOVI';
import { Gare } from '../model/Gare';
import { FichierGOVI } from '../model/FichierGOVI';
import { Form } from '@angular/forms';

@Injectable({
  providedIn: 'root',
})
export class AppelGenerationGoviService {
  constructor(private httpClient: HttpClient) {}

  appelGenerationGovi(demandeGOVI: DemandeGOVI) {
    return this.httpClient.post<Gare[]>(
      'http://localhost:8080/generationGOVI',
      demandeGOVI
    );
  }

  envoiDemandeGOVIPartielle(fichierGOVI: FormData) {
    return this.httpClient.post(
      'http://localhost:8080/generationGOVI/file',
      fichierGOVI
    );
  }
}
