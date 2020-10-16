import { IUmfrage } from 'app/shared/model/umfrage.model';
import { IAntwort } from 'app/shared/model/antwort.model';

export interface IUserAntwort {
  id?: number;
  user?: string;
  umfrage?: IUmfrage;
  antwort?: IAntwort;
}

export class UserAntwort implements IUserAntwort {
  constructor(public id?: number, public user?: string, public umfrage?: IUmfrage, public antwort?: IAntwort) {}
}
