export interface IUserAntwort {
  id?: number;
  userID?: number;
}

export class UserAntwort implements IUserAntwort {
  constructor(public id?: number, public userID?: number) {}
}
