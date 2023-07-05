import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-loadingbar',
  templateUrl: './loadingbar.component.html',
  styleUrls: ['./loadingbar.component.css'],
})
export class LoadingbarComponent {
  @Input() isLoading!: boolean;

  trainImages = ['assets/trainB.svg', 'assets/trainB2.svg'];
  currentTrainImage = this.randomTrainImage();

  randomTrainImage(): string {
    return this.trainImages[
      Math.floor(Math.random() * this.trainImages.length)
    ];
  }
  changeTrainImage(): void {
    this.currentTrainImage = this.randomTrainImage();
  }
}
