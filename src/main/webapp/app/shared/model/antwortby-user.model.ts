import { IAntwort } from 'app/shared/model/antwort.model';

export interface IAntwortbyUser {
  id?: number;
  userID?: number;
  antwort?: IAntwort;
}

export class AntwortbyUser implements IAntwortbyUser {
  constructor(public id?: number, public userID?: number, public antwort?: IAntwort) {}
}
