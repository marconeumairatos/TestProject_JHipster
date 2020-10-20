import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IUserantwort } from 'app/shared/model/userantwort.model';

type EntityResponseType = HttpResponse<IUserantwort>;
type EntityArrayResponseType = HttpResponse<IUserantwort[]>;

@Injectable({ providedIn: 'root' })
export class UserantwortService {
  public resourceUrl = SERVER_API_URL + 'api/userantworts';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/userantworts';

  constructor(protected http: HttpClient) {}

  create(userantwort: IUserantwort): Observable<EntityResponseType> {
    return this.http.post<IUserantwort>(this.resourceUrl, userantwort, { observe: 'response' });
  }

  update(userantwort: IUserantwort): Observable<EntityResponseType> {
    return this.http.put<IUserantwort>(this.resourceUrl, userantwort, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserantwort>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserantwort[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserantwort[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
