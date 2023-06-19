import { Component } from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {DemandeGOVI} from "../../model/demandeGOVI";

@Component({
  selector: 'app-stepper',
  templateUrl: './stepper.component.html',
  styleUrls: ['./stepper.component.css']
})
export class StepperComponent {
  demandeGOVI: DemandeGOVI = new DemandeGOVI();

  dateRegex = "^(0[1-9]|[12][0-9]|3[01])\\/(0[1-9]|1[012])\\/([0-9]{2})$\n";

  step1FormGroup = new FormGroup({
    BHL1FormControl: new FormControl('', Validators.required),
    BHL2FormControl: new FormControl('', Validators.required),
    pacific1FormControl: new FormControl('', Validators.required),
    pacific2FormControl: new FormControl('', Validators.required),
    RATPFormControl: new FormControl('', Validators.required),
    DateFormControl: new FormControl(new Date(""), Validators.required),
  });

  constructor() {
  }
  submit(){
    let BHL1 = this.step1FormGroup.get('BHL1FormControl')?.value;
    let BHL2 = this.step1FormGroup.get('BHL2FormControl')?.value;
    let pacific1 = this.step1FormGroup.get('pacific1FormControl')?.value;
    let pacific2 = this.step1FormGroup.get('pacific2FormControl')?.value;
    let RATP = this.step1FormGroup.get('RATPFormControl')?.value;
    let date = this.step1FormGroup.get('DateFormControl')?.value;
    if(BHL1) {
      this.demandeGOVI.fichiers["BHL1"] = BHL1;
    }
    if(BHL2) {
      this.demandeGOVI.fichiers["BHL2"] = BHL2;
    }
    if(pacific1){
      this.demandeGOVI.fichiers["pacific1"] = pacific1;
    }
    if(pacific2){
      this.demandeGOVI.fichiers["pacific2"] = pacific2;
    }
    if(RATP){
      this.demandeGOVI.fichiers["RATP"] = RATP;
    }
    if(date){
      this.demandeGOVI.date = date;
    }
    console.log(this.demandeGOVI)
  }
}
