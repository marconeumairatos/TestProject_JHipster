import { IAntwortbyUser } from 'app/shared/model/antwortby-user.model';
import { IUmfrage } from 'app/shared/model/umfrage.model';

export interface IAntwort {
  id?: number;
  text?: string;
  antwortbyUsers?: IAntwortbyUser[];
  umfrage?: IUmfrage;
}

export class Antwort implements IAntwort {
  constructor(public id?: number, public text?: string, public antwortbyUsers?: IAntwortbyUser[], public umfrage?: IUmfrage) {}
}
