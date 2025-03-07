import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { DemandeGOVI } from '../../model/demandeGOVI';
import { TypeFichierEnum } from '../../model/TypeFichierEnum';
import { AppelGenerationGoviService } from '../../service/appel-generation-govi.service';
import { FichierGOVI } from '../../model/FichierGOVI';
import { Gare } from '../../model/Gare';
import { Router, RouterOutlet } from '@angular/router';
import * as moment from 'moment-timezone';

@Component({
  selector: 'app-stepper',
  templateUrl: './stepper.component.html',
  styleUrls: ['./stepper.component.css'],
})
export class StepperComponent {
  demandeGOVI: DemandeGOVI = new DemandeGOVI();
  fichiersEnCoursDeLecture: number = 0;
  isLoading = false;

  dateRegex =
    '^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$';
  excelRegex = '.*\\.(xls|xlsx)$';
  txtRegex = '.*\\.txt$';

  BHL1FormControl: FormControl = new FormControl('', [
    Validators.required,
    Validators.pattern(this.excelRegex),
  ]);
  BHL2FormControl = new FormControl('', [Validators.pattern(this.excelRegex)]);

  pacific1FormControl = new FormControl('', [
    Validators.required,
    Validators.pattern(this.txtRegex),
  ]);
  pacific2FormControl = new FormControl('', [
    Validators.pattern(this.txtRegex),
  ]);

  RATPFormControl = new FormControl('', [Validators.pattern(this.excelRegex)]);

  DateFormControl = new FormControl(
    new Date().toLocaleDateString('fr-FR', {
      day: '2-digit',
      month: '2-digit',
      year: '2-digit',
    }),
    [Validators.required, Validators.pattern(this.dateRegex)]
  );

  step1FormGroup = new FormGroup({
    BHL1FormControl: this.BHL1FormControl,
    BHL2FormControl: this.BHL2FormControl,
    pacific1FormControl: this.pacific1FormControl,
    pacific2FormControl: this.pacific2FormControl,
    RATPFormControl: this.RATPFormControl,
  });

  step2FormGroup = new FormGroup({ DateFormControl: this.DateFormControl });

  constructor(
    private appelGenerationGoviService: AppelGenerationGoviService,
    private router: Router
  ) {}

  // La date est récupéré et mise à UTC+2 avec moment.js, pour unifier le format avec le back et au format JJ/MM/AA
  updateDemandeGOVI() {
    let date = this.step2FormGroup.get('DateFormControl')?.value;
    if (date) {
      console.log(moment(date, 'DD/MM/YY').toDate());
      this.demandeGOVI.date = moment(date, 'DD/MM/YY').toDate();
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
        this.appelGenerationGoviService.gares = data;
        this.router.navigateByUrl('afficheurGOVI');
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
    this.fichiersEnCoursDeLecture++;
    console.log(this.fichiersEnCoursDeLecture);
    this.appelGenerationGoviService
      .envoiDemandeGOVIPartielle(formData)
      .subscribe((data) => {
        this.fichiersEnCoursDeLecture--;
        console.log(this.fichiersEnCoursDeLecture);
      });
  }

  fichierEnCoursDeLecture() {
    return this.fichiersEnCoursDeLecture > 0;
  }

  protected readonly TypeFichierEnum = TypeFichierEnum;
}
