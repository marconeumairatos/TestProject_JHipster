import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IAntwortbyUser } from 'app/shared/model/antwortby-user.model';

type EntityResponseType = HttpResponse<IAntwortbyUser>;
type EntityArrayResponseType = HttpResponse<IAntwortbyUser[]>;

@Injectable({ providedIn: 'root' })
export class AntwortbyUserService {
  public resourceUrl = SERVER_API_URL + 'api/antwortby-users';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/antwortby-users';

  constructor(protected http: HttpClient) {}

  create(antwortbyUser: IAntwortbyUser): Observable<EntityResponseType> {
    return this.http.post<IAntwortbyUser>(this.resourceUrl, antwortbyUser, { observe: 'response' });
  }

  update(antwortbyUser: IAntwortbyUser): Observable<EntityResponseType> {
    return this.http.put<IAntwortbyUser>(this.resourceUrl, antwortbyUser, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAntwortbyUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAntwortbyUser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAntwortbyUser[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
