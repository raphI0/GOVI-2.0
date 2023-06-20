import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { DemandeGOVI } from '../../model/demandeGOVI';
import { TypeFichierEnum } from '../../model/TypeFichierEnum';
import { AppelGenerationGoviService } from '../../service/appel-generation-govi.service';
import { FichierGOVI } from '../../model/FichierGOVI';

@Component({
  selector: 'app-stepper',
  templateUrl: './stepper.component.html',
  styleUrls: ['./stepper.component.css'],
})
export class StepperComponent {
  demandeGOVI: DemandeGOVI = new DemandeGOVI();
  isLoading = false;

  dateRegex = '^(0[1-9]|[12][0-9]|3[01])\\/(0[1-9]|1[012])\\/([0-9]{2})$';
  excelRegex = '.*\\.(xls|xlsx)$';
  txtRegex = '.*\\.txt$';

  step1FormGroup = new FormGroup({
    BHL1FormControl: new FormControl('', [
      Validators.required,
      Validators.pattern(this.excelRegex),
    ]),
    BHL2FormControl: new FormControl('', [
      Validators.required,
      Validators.pattern(this.excelRegex),
    ]),
    pacific1FormControl: new FormControl('', [
      Validators.required,
      Validators.pattern(this.txtRegex),
    ]),
    pacific2FormControl: new FormControl('', [
      Validators.required,
      Validators.pattern(this.txtRegex),
    ]),
    RATPFormControl: new FormControl('', [
      Validators.required,
      Validators.pattern(this.excelRegex),
    ]),
    DateFormControl: new FormControl(new Date(''), [
      Validators.required,
      Validators.pattern(this.excelRegex),
    ]),
  });

  constructor(private appelGenerationGoviService: AppelGenerationGoviService) {}
  updateDemandeGOVI() {
    let date = this.step1FormGroup.get('DateFormControl')?.value;
    if (date) {
      this.demandeGOVI.date = date;
    }
  }
  submit() {
    this.updateDemandeGOVI();
    this.appelAPI();
  }
  public appelAPI() {
    this.isLoading = true;
    this.appelGenerationGoviService
      .appelGenerationGovi(this.demandeGOVI)
      .subscribe((data) => {
        this.isLoading = false;
      });
  }
  onFileSelected(event: any, typeFichier: TypeFichierEnum) {
    if (event.target.files.length > 0) {
      let file = event.target.files[0];
      let error = this.updateFileFormControl(file.name, typeFichier);
      console.log(error);
      if (!error) {
        const formData = new FormData();
        formData.append('file', file, file.name);
        formData.append('typeFichier', typeFichier);
        this.demandeGOVIPartielle(formData);
      }
    }
  }
  updateFileFormControl(fileName: string, typeFichier: TypeFichierEnum) {
    switch (typeFichier) {
      case TypeFichierEnum.BHLJ1:
        this.step1FormGroup.get('BHL1FormControl')?.setValue(fileName);
        return this.step1FormGroup.get('BHL1FormControl')?.hasError('pattern');
        break;
      case TypeFichierEnum.BHLJ2:
        this.step1FormGroup.get('BHL2FormControl')?.setValue(fileName);
        return this.step1FormGroup.get('BHL2FormControl')?.hasError('pattern');
        break;
      case TypeFichierEnum.PACIFICJ1:
        this.step1FormGroup.get('pacific1FormControl')?.setValue(fileName);
        return this.step1FormGroup
          .get('pacific1FormControl')
          ?.hasError('pattern');
        break;
      case TypeFichierEnum.PACIFICJ2:
        this.step1FormGroup.get('pacific2FormControl')?.setValue(fileName);
        return this.step1FormGroup
          .get('pacific2FormControl')
          ?.hasError('pattern');
        break;
      case TypeFichierEnum.RATP:
        this.step1FormGroup.get('RATPFormControl')?.setValue(fileName);
        return this.step1FormGroup.get('RATPFormControl')?.hasError('pattern');
        break;
    }
  }
  demandeGOVIPartielle(formData: FormData) {
    this.appelGenerationGoviService
      .envoiDemandeGOVIPartielle(formData)
      .subscribe();
  }

  protected readonly TypeFichierEnum = TypeFichierEnum;
}
