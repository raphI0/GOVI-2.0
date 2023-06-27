import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { StepperComponent } from './components/stepper/stepper.component';
import { GOVIDisplayerComponent } from './components/govidisplayer/govidisplayer.component';

/**
 * Les routes du Routeur
 * Exemple :
 * En faisant router.navigateByUrl('afficheurGOVI')
 * On tombe sur le component GOVIDisplayer
 */
const routes: Routes = [
  { path: '', component: StepperComponent },
  { path: 'afficheurGOVI', component: GOVIDisplayerComponent },
];

@NgModule({
  declarations: [],
  imports: [CommonModule, RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
