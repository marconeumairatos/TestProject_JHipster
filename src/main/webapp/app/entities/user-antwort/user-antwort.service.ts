import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IUserAntwort } from 'app/shared/model/user-antwort.model';

type EntityResponseType = HttpResponse<IUserAntwort>;
type EntityArrayResponseType = HttpResponse<IUserAntwort[]>;

@Injectable({ providedIn: 'root' })
export class UserAntwortService {
  public resourceUrl = SERVER_API_URL + 'api/user-antworts';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/user-antworts';

  constructor(protected http: HttpClient) {}

  create(userAntwort: IUserAntwort): Observable<EntityResponseType> {
    return this.http.post<IUserAntwort>(this.resourceUrl, userAntwort, { observe: 'response' });
  }

  update(userAntwort: IUserAntwort): Observable<EntityResponseType> {
    return this.http.put<IUserAntwort>(this.resourceUrl, userAntwort, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserAntwort>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserAntwort[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserAntwort[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
