import { IUmfrage } from 'app/shared/model/umfrage.model';

export interface IAntwort {
  id?: number;
  pollID?: number;
  text?: string;
  umfrage?: IUmfrage;
}

export class Antwort implements IAntwort {
  constructor(public id?: number, public pollID?: number, public text?: string, public umfrage?: IUmfrage) {}
}
