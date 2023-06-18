export class Releve {
  gare: string;
  nextMission: string;
  currentMission: string;
  constructor(gare: string, nextMission: string, currentMission: string) {
    this.gare = gare;
    this.nextMission = nextMission;
    this.currentMission = currentMission;
  }
}
