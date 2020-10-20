export interface IUserantwort {
  id?: number;
  userID?: number;
}

export class Userantwort implements IUserantwort {
  constructor(public id?: number, public userID?: number) {}
}
