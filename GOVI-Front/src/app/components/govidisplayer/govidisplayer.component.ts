import { Component } from '@angular/core';
import { Retournement } from '../../model/Retournement';
import { Mission } from '../../model/Mission';
import { Gare } from '../../model/Gare';
import { Quai } from '../../model/Quai';
import { Conducteur } from '../../model/Conducteur';

@Component({
  selector: 'app-govidisplayer',
  templateUrl: './govidisplayer.component.html',
  styleUrls: ['./govidisplayer.component.css'],
})
export class GOVIDisplayerComponent {
  gares: Gare[] = [];
  retournements: Retournement[] = [];
  dateGovi: Date = new Date();

  constructor() {
    let conducteur = new Conducteur('10MP', 'CDG', true, false, '#f00020', []);
    this.retournements[0] = new Retournement('ZOBMYSTERE', [], []);
    let mission1 = new Mission(
      'SOSI40',
      'CDG',
      'Mitry',
      new Date(),
      new Date(),
      '#000000',
      conducteur,
      [conducteur, conducteur, conducteur, conducteur]
    );
    let mission2 = new Mission(
      'KFAR02',
      'CDG',
      'Mitry',
      new Date(),
      new Date(),
      '#000000',
      new Conducteur('12SZ', 'CDG', false, true, '#f00020', []),
      []
    );
    mission1.heureArrivee.setHours(2);
    mission1.heureArrivee.setMinutes(0);
    mission1.heureDepart.setHours(5);
    mission1.heureDepart.setMinutes(0);
    mission2.heureArrivee.setHours(3);
    mission2.heureArrivee.setMinutes(5);
    mission2.heureDepart.setHours(4);
    mission2.heureDepart.setMinutes(0);
    this.retournements[0].missionsArrivee[0] = mission1;
    this.retournements[0].missionsDepart[0] = mission1;
    this.retournements[0].missionsArrivee[1] = mission2;
    this.retournements[0].missionsDepart[1] = mission2;

    let quais = [];
    quais[0] = new Quai('VZ', this.retournements);
    quais[1] = new Quai('VA', this.retournements);
    quais[2] = new Quai('VB', this.retournements);
    let quaisMitry = [];
    quaisMitry[0] = new Quai('SAS3', this.retournements);
    quaisMitry[1] = new Quai('VULVAX', this.retournements);
    quaisMitry[2] = new Quai('DOUBLEPENE', this.retournements);
    quaisMitry[3] = new Quai('DOUBLEPENE', this.retournements);
    this.gares[0] = new Gare('MITRY', quaisMitry);
    this.gares[1] = new Gare('CDG', quais);
    this.gares[2] = new Gare('StREMY', quais);
    this.gares[3] = new Gare('StREMY', quais);
    this.gares[4] = new Gare('StREMY', quais);
  }

  calculatePositionX(date: Date): number {
    // Obtient l'heure et les minutes
    let hour = date.getHours();
    let minutes = date.getMinutes();
    // Si c'est une donnée du jour suivant
    if (date.getDay() - this.dateGovi.getDay() == 1) {
      hour += 24;
    }
    // Effectuez vos calculs pour déterminer la position x souhaitée en fonction de l'heure
    // Par exemple, multipliez l'heure par 50 pour obtenir une valeur de position arbitraire
    return this.calculateHourPositionX(hour, minutes);
  }

  calculateHourPositionX(hour: number, minutes: number): number {
    return (hour + minutes / 60) * 500;
  }
  getHour(hour: number) {
    if (hour >= 24) {
      return hour - 24;
    } else {
      return hour;
    }
  }

  getHeurePremiereMissionArrivee(retournement: Retournement) {
    return retournement.missionsArrivee[0].heureArrivee;
  }
  getHeurePremiereMissionDepart(retournement: Retournement) {
    return retournement.missionsDepart[0].heureDepart;
  }
  dureeRetournement(retournement: Retournement) {
    return (
      this.calculatePositionX(
        this.getHeurePremiereMissionDepart(retournement)
      ) -
      this.calculatePositionX(this.getHeurePremiereMissionArrivee(retournement))
    );
  }

  getADCCode(conducteur: Conducteur) {
    if (conducteur.isPS) {
      return conducteur.codeADC + '+FS';
    } else if (conducteur.isFS) {
      return conducteur.codeADC + '+PS';
    } else return conducteur.codeADC;
  }

  iterateArray(length: number): number[] {
    return Array.from({ length }, (_, index) => index);
  }

  protected readonly console = console;
}
