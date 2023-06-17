import { Component } from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-stepper',
  templateUrl: './stepper.component.html',
  styleUrls: ['./stepper.component.css']
})
export class StepperComponent {
  step1FormGroup = new FormGroup({
    formControl1: new FormControl('', Validators.required),
    formControl2: new FormControl('', Validators.required),
    formControl3: new FormControl('', Validators.required),
    formControl4: new FormControl('', Validators.required),
  });
}
