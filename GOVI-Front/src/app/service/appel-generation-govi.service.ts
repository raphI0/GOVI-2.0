import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AppelGenerationGoviService {

  constructor(private httpClient: HttpClient) {
  }

  appelGenerationGovi(){
    let list = new Array<string>;
    const headers = { 'content-type': 'application/text'}
    list[0] = "fichier 1"
    list[1] = "fichier 2"
    return this.httpClient.post<string[]>("http://localhost:8080/generationGOVI", "list")
  }
}
