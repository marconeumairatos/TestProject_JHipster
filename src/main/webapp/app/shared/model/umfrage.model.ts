import { IAntwort } from 'app/shared/model/antwort.model';
import { IUserAntwort } from 'app/shared/model/user-antwort.model';

export interface IUmfrage {
  id?: number;
  name?: string;
  text?: string;
  status?: string;
  antworts?: IAntwort[];
  userAntworts?: IUserAntwort[];
}

export class Umfrage implements IUmfrage {
  constructor(
    public id?: number,
    public name?: string,
    public text?: string,
    public status?: string,
    public antworts?: IAntwort[],
    public Userantworts?: IUserAntwort[]
  ) {}
}
