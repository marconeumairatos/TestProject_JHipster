import { IAntwort } from 'app/shared/model/antwort.model';

export interface IUmfrage {
  id?: number;
  name?: string;
  text?: string;
  status?: string;
  antworts?: IAntwort[];
}

export class Umfrage implements IUmfrage {
  constructor(public id?: number, public name?: string, public text?: string, public status?: string, public antworts?: IAntwort[]) {}
}
