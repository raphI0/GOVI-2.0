import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DemandeGOVI } from '../model/demandeGOVI';

@Injectable({
  providedIn: 'root',
})
export class AppelGenerationGoviService {
  constructor(private httpClient: HttpClient) {}

  appelGenerationGovi(demandeGOVI: DemandeGOVI) {
    return this.httpClient.post<string[]>(
      'http://localhost:8080/generationGOVI',
      demandeGOVI
    );
  }
}
