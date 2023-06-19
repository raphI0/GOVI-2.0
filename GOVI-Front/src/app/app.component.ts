import { Component } from '@angular/core';
import {AppelGenerationGoviService} from "./service/appel-generation-govi.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'GOVI-Front';
  resultatAppel = "";

  constructor(private appelGenerationGoviService: AppelGenerationGoviService) {

  }


  public appelAPI(){
    this.appelGenerationGoviService.appelGenerationGovi().subscribe(data => {
      let result = data as string[];
      console.log(result)
      this.resultatAppel = result[0]
    });
  }
}
