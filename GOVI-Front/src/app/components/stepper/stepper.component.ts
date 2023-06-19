import { Component } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { DemandeGOVI } from '../../model/demandeGOVI';
import { TypeFichierEnum } from '../../model/TypeFichierEnum';

@Component({
  selector: 'app-stepper',
  templateUrl: './stepper.component.html',
  styleUrls: ['./stepper.component.css'],
})
export class StepperComponent {
  demandeGOVI: DemandeGOVI = new DemandeGOVI();
  isLoading: boolean;

  dateRegex = '^(0[1-9]|[12][0-9]|3[01])\\/(0[1-9]|1[012])\\/([0-9]{2})$\n';

  step1FormGroup = new FormGroup({
    BHL1FormControl: new FormControl('', Validators.required),
    BHL2FormControl: new FormControl('', Validators.required),
    pacific1FormControl: new FormControl('', Validators.required),
    pacific2FormControl: new FormControl('', Validators.required),
    RATPFormControl: new FormControl('', Validators.required),
    DateFormControl: new FormControl(new Date(''), Validators.required),
  });

  constructor() {}
  submit() {
    let BHLJ1 = this.step1FormGroup.get('BHL1FormControl')?.value;
    let BHLJ2 = this.step1FormGroup.get('BHL2FormControl')?.value;
    let pacificJ1 = this.step1FormGroup.get('pacific1FormControl')?.value;
    let pacificJ2 = this.step1FormGroup.get('pacific2FormControl')?.value;
    let RATP = this.step1FormGroup.get('RATPFormControl')?.value;
    let date = this.step1FormGroup.get('DateFormControl')?.value;
    if (BHLJ1) {
      this.demandeGOVI.fichiers[TypeFichierEnum.BHLJ1] = BHLJ1;
    }
    if (BHLJ2) {
      this.demandeGOVI.fichiers[TypeFichierEnum.BHLJ2] = BHLJ2;
    }
    if (pacificJ1) {
      this.demandeGOVI.fichiers[TypeFichierEnum.PACIFICJ1] = pacificJ1;
    }
    if (pacificJ2) {
      this.demandeGOVI.fichiers[TypeFichierEnum.PACIFICJ2] = pacificJ2;
    }
    if (RATP) {
      this.demandeGOVI.fichiers[TypeFichierEnum.RATP] = RATP;
    }
    if (date) {
      this.demandeGOVI.date = date;
    }
    this.appelAPI();
  }
  public appelAPI() {
    this.appelGenerationGoviService
      .appelGenerationGovi(this.demandeGOVI)
      .subscribe((data) => {
        let result = data as string[];
        console.log(result);
        this.resultatAppel = result[0];
      });
  }
}
