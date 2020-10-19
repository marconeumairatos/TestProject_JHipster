import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IAntwort } from 'app/shared/model/antwort.model';

type EntityResponseType = HttpResponse<IAntwort>;
type EntityArrayResponseType = HttpResponse<IAntwort[]>;

@Injectable({ providedIn: 'root' })
export class AntwortService {
  public resourceUrl = SERVER_API_URL + 'api/antworts';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/antworts';
  public ressourceUrlABU = SERVER_API_URL + '/antwortsbyumfrage{id}';

  constructor(protected http: HttpClient) {}

  create(antwort: IAntwort): Observable<EntityResponseType> {
    return this.http.post<IAntwort>(this.resourceUrl, antwort, { observe: 'response' });
  }

  update(antwort: IAntwort): Observable<EntityResponseType> {
    return this.http.put<IAntwort>(this.resourceUrl, antwort, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAntwort>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAntwort[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAntwort[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  findbyUmfrageId(): Observable<EntityResponseType> {
    return this.http.get<IAntwort>(`${this.ressourceUrlABU}`, { observe: 'response' });
  }
}
