import { IUserAntwort } from 'app/shared/model/user-antwort.model';
import { IUmfrage } from 'app/shared/model/umfrage.model';

export interface IAntwort {
  id?: number;
  text?: string;
  userAntworts?: IUserAntwort[];
  umfrage?: IUmfrage;
}

export class Antwort implements IAntwort {
  constructor(public id?: number, public text?: string, public userAntworts?: IUserAntwort[], public umfrage?: IUmfrage) {}
}
