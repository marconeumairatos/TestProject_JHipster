import { IUmfrage } from 'app/shared/model/umfrage.model';
import { IAntwort } from 'app/shared/model/antwort.model';

export interface IUserantwort {
  id?: number;
  user?: string;
  umfrage?: IUmfrage;
  antwort?: IAntwort;
}

export class Userantwort implements IUserantwort {
  constructor(public id?: number, public user?: string, public umfrage?: IUmfrage, public antwort?: IAntwort) {}
}
