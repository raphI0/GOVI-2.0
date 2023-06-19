import { Mission } from './Mission';
import { Conducteur } from './Conducteur';

export class MissionSV extends Mission {
  nextMissionRame: string;
  constructor(
    codeMission: string,
    gareDepart: string,
    gareArrivee: string,
    heureArrivee: Date,
    heureDepart: Date,
    couleurEnum: string,
    conducteurTrain: Conducteur,
    conducteursEVLoc: Conducteur[],
    nextMissionRame: string
  ) {
    super(
      codeMission,
      gareDepart,
      gareArrivee,
      heureArrivee,
      heureDepart,
      couleurEnum,
      conducteurTrain,
      conducteursEVLoc
    );
    this.nextMissionRame = nextMissionRame;
  }
}
